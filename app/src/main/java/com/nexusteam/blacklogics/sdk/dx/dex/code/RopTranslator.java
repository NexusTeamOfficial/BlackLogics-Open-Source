
package mod.agus.jcoderz.dx.dex.code;

import java.util.ArrayList;

import mod.agus.jcoderz.dx.dex.DexOptions;
import mod.agus.jcoderz.dx.io.Opcodes;
import mod.agus.jcoderz.dx.rop.code.BasicBlock;
import mod.agus.jcoderz.dx.rop.code.BasicBlockList;
import mod.agus.jcoderz.dx.rop.code.FillArrayDataInsn;
import mod.agus.jcoderz.dx.rop.code.Insn;
import mod.agus.jcoderz.dx.rop.code.InvokePolymorphicInsn;
import mod.agus.jcoderz.dx.rop.code.LocalVariableInfo;
import mod.agus.jcoderz.dx.rop.code.PlainCstInsn;
import mod.agus.jcoderz.dx.rop.code.PlainInsn;
import mod.agus.jcoderz.dx.rop.code.RegOps;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecSet;
import mod.agus.jcoderz.dx.rop.code.Rop;
import mod.agus.jcoderz.dx.rop.code.RopMethod;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.rop.code.SwitchInsn;
import mod.agus.jcoderz.dx.rop.code.ThrowingCstInsn;
import mod.agus.jcoderz.dx.rop.code.ThrowingInsn;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;
import mod.agus.jcoderz.dx.util.Bits;
import mod.agus.jcoderz.dx.util.IntList;

public final class RopTranslator {
    private final mod.agus.jcoderz.dx.dex.DexOptions dexOptions;

    private final mod.agus.jcoderz.dx.rop.code.RopMethod method;

    private final int positionInfo;

    private final mod.agus.jcoderz.dx.rop.code.LocalVariableInfo locals;

    private final BlockAddresses addresses;

    private final OutputCollector output;

    private final TranslationVisitor translationVisitor;

    private final int regCount;

    private int[] order;

    private final int paramSize;

    private final boolean paramsAreInOrder;

    public static DalvCode translate(mod.agus.jcoderz.dx.rop.code.RopMethod method, int positionInfo,
                                     mod.agus.jcoderz.dx.rop.code.LocalVariableInfo locals, int paramSize, mod.agus.jcoderz.dx.dex.DexOptions dexOptions) {
        RopTranslator translator =
            new RopTranslator(method, positionInfo, locals, paramSize, dexOptions);
        return translator.translateAndGetResult();
    }

    private RopTranslator(mod.agus.jcoderz.dx.rop.code.RopMethod method, int positionInfo, mod.agus.jcoderz.dx.rop.code.LocalVariableInfo locals,
                          int paramSize, DexOptions dexOptions) {
        this.dexOptions = dexOptions;
        this.method = method;
        this.positionInfo = positionInfo;
        this.locals = locals;
        this.addresses = new BlockAddresses(method);
        this.paramSize = paramSize;
        this.order = null;
        this.paramsAreInOrder = calculateParamsAreInOrder(method, paramSize);

        mod.agus.jcoderz.dx.rop.code.BasicBlockList blocks = method.getBlocks();
        int bsz = blocks.size();

        int maxInsns = (bsz * 3) + blocks.getInstructionCount();

        if (locals != null) {
            maxInsns += bsz + locals.getAssignmentCount();
        }

        this.regCount = blocks.getRegCount()
                + (paramsAreInOrder ? 0 : this.paramSize);

        this.output = new OutputCollector(dexOptions, maxInsns, bsz * 3, regCount, paramSize);

        if (locals != null) {
            this.translationVisitor =
                new LocalVariableAwareTranslationVisitor(output, locals);
        } else {
            this.translationVisitor = new TranslationVisitor(output);
        }
    }

    private static boolean calculateParamsAreInOrder(RopMethod method,
                                                     final int paramSize) {
        final boolean[] paramsAreInOrder = { true };
        final int initialRegCount = method.getBlocks().getRegCount();

        method.getBlocks().forEachInsn(new mod.agus.jcoderz.dx.rop.code.Insn.BaseVisitor() {
            @Override
            public void visitPlainCstInsn(mod.agus.jcoderz.dx.rop.code.PlainCstInsn insn) {
                if (insn.getOpcode().getOpcode()== mod.agus.jcoderz.dx.rop.code.RegOps.MOVE_PARAM) {
                    int param =
                        ((mod.agus.jcoderz.dx.rop.cst.CstInteger) insn.getConstant()).getValue();

                    paramsAreInOrder[0] = paramsAreInOrder[0]
                            && ((initialRegCount - paramSize + param)
                                == insn.getResult().getReg());
                }
            }
        });

        return paramsAreInOrder[0];
    }

    private DalvCode translateAndGetResult() {
        pickOrder();
        outputInstructions();

        StdCatchBuilder catches =
            new StdCatchBuilder(method, order, addresses);

        return new DalvCode(positionInfo, output.getFinisher(), catches);
    }

    private void outputInstructions() {
        mod.agus.jcoderz.dx.rop.code.BasicBlockList blocks = method.getBlocks();
        int[] order = this.order;
        int len = order.length;


        for (int i = 0; i < len; i++) {
            int nextI = i + 1;
            int nextLabel = (nextI == order.length) ? -1 : order[nextI];
            outputBlock(blocks.labelToBlock(order[i]), nextLabel);
        }
    }

    private void outputBlock(mod.agus.jcoderz.dx.rop.code.BasicBlock block, int nextLabel) {

        CodeAddress startAddress = addresses.getStart(block);
        output.add(startAddress);


        if (locals != null) {
            RegisterSpecSet starts = locals.getStarts(block);
            output.add(new LocalSnapshot(startAddress.getPosition(),
                                         starts));
        }

        translationVisitor.setBlock(block, addresses.getLast(block));
        block.getInsns().forEach(translationVisitor);


        output.add(addresses.getEnd(block));



        int succ = block.getPrimarySuccessor();
        mod.agus.jcoderz.dx.rop.code.Insn lastInsn = block.getLastInsn();


        if ((succ >= 0) && (succ != nextLabel)) {
            mod.agus.jcoderz.dx.rop.code.Rop lastRop = lastInsn.getOpcode();
            if ((lastRop.getBranchingness() == mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_IF) &&
                    (block.getSecondarySuccessor() == nextLabel)) {
                output.reverseBranch(1, addresses.getStart(succ));
            } else {
                TargetInsn insn =
                    new TargetInsn(Dops.GOTO, lastInsn.getPosition(),
                            mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY,
                            addresses.getStart(succ));
                output.add(insn);
            }
        }
    }

    private void pickOrder() {
        BasicBlockList blocks = method.getBlocks();
        int sz = blocks.size();
        int maxLabel = blocks.getMaxLabel();
        int[] workSet = mod.agus.jcoderz.dx.util.Bits.makeBitSet(maxLabel);
        int[] tracebackSet = mod.agus.jcoderz.dx.util.Bits.makeBitSet(maxLabel);

        for (int i = 0; i < sz; i++) {
            mod.agus.jcoderz.dx.rop.code.BasicBlock one = blocks.get(i);
            mod.agus.jcoderz.dx.util.Bits.set(workSet, one.getLabel());
        }

        int[] order = new int[sz];
        int at = 0;

        for (int label = method.getFirstLabel();
             label != -1;
             label = mod.agus.jcoderz.dx.util.Bits.findFirst(workSet, 0)) {

            traceBack:
            for (;;) {
                mod.agus.jcoderz.dx.util.IntList preds = method.labelToPredecessors(label);
                int psz = preds.size();

                for (int i = 0; i < psz; i++) {
                    int predLabel = preds.get(i);

                    if (mod.agus.jcoderz.dx.util.Bits.get(tracebackSet, predLabel)) {
                        break;
                    }

                    if (!mod.agus.jcoderz.dx.util.Bits.get(workSet, predLabel)) {

                        continue;
                    }

                    mod.agus.jcoderz.dx.rop.code.BasicBlock pred = blocks.labelToBlock(predLabel);
                    if (pred.getPrimarySuccessor() == label) {

                        label = predLabel;
                        mod.agus.jcoderz.dx.util.Bits.set(tracebackSet, label);
                        continue traceBack;
                    }
                }


                break;
            }

            while (label != -1) {
                mod.agus.jcoderz.dx.util.Bits.clear(workSet, label);
                mod.agus.jcoderz.dx.util.Bits.clear(tracebackSet, label);
                order[at] = label;
                at++;

                mod.agus.jcoderz.dx.rop.code.BasicBlock one = blocks.labelToBlock(label);
                mod.agus.jcoderz.dx.rop.code.BasicBlock preferredBlock = blocks.preferredSuccessorOf(one);

                if (preferredBlock == null) {
                    break;
                }

                int preferred = preferredBlock.getLabel();
                int primary = one.getPrimarySuccessor();

                if (mod.agus.jcoderz.dx.util.Bits.get(workSet, preferred)) {
                    label = preferred;
                } else if ((primary != preferred) && (primary >= 0)
                        && mod.agus.jcoderz.dx.util.Bits.get(workSet, primary)) {
                    label = primary;
                } else {
                    mod.agus.jcoderz.dx.util.IntList successors = one.getSuccessors();
                    int ssz = successors.size();
                    label = -1;
                    for (int i = 0; i < ssz; i++) {
                        int candidate = successors.get(i);
                        if (Bits.get(workSet, candidate)) {
                            label = candidate;
                            break;
                        }
                    }
                }
            }
        }

        if (at != sz) {

            throw new RuntimeException("shouldn't happen");
        }

        this.order = order;
    }

    private static mod.agus.jcoderz.dx.rop.code.RegisterSpecList getRegs(mod.agus.jcoderz.dx.rop.code.Insn insn) {
        return getRegs(insn, insn.getResult());
    }

    private static mod.agus.jcoderz.dx.rop.code.RegisterSpecList getRegs(mod.agus.jcoderz.dx.rop.code.Insn insn,
                                                                         mod.agus.jcoderz.dx.rop.code.RegisterSpec resultReg) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList regs = insn.getSources();

        if (insn.getOpcode().isCommutative()
                && (regs.size() == 2)
                && (resultReg.getReg() == regs.get(1).getReg())) {


            regs = mod.agus.jcoderz.dx.rop.code.RegisterSpecList.make(regs.get(1), regs.get(0));
        }

        if (resultReg == null) {
            return regs;
        }

        return regs.withFirst(resultReg);
    }

    private class TranslationVisitor implements mod.agus.jcoderz.dx.rop.code.Insn.Visitor {
        private final OutputCollector output;

        private mod.agus.jcoderz.dx.rop.code.BasicBlock block;

        private CodeAddress lastAddress;

        public TranslationVisitor(OutputCollector output) {
            this.output = output;
        }

        public void setBlock(BasicBlock block, CodeAddress lastAddress) {
            this.block = block;
            this.lastAddress = lastAddress;
        }

        @Override
        public void visitPlainInsn(mod.agus.jcoderz.dx.rop.code.PlainInsn insn) {
            mod.agus.jcoderz.dx.rop.code.Rop rop = insn.getOpcode();
            if (rop.getOpcode() == mod.agus.jcoderz.dx.rop.code.RegOps.MARK_LOCAL) {
                return;
            }
            if (rop.getOpcode() == mod.agus.jcoderz.dx.rop.code.RegOps.MOVE_RESULT_PSEUDO) {

                return;
            }

            mod.agus.jcoderz.dx.rop.code.SourcePosition pos = insn.getPosition();
            Dop opcode = RopToDop.dopFor(insn);
            DalvInsn di;

            switch (rop.getBranchingness()) {
                case mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_NONE:
                case mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_RETURN:
                case mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_THROW: {
                    di = new SimpleInsn(opcode, pos, getRegs(insn));
                    break;
                }
                case mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_GOTO: {
                    return;
                }
                case mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_IF: {
                    int target = block.getSuccessors().get(1);
                    di = new TargetInsn(opcode, pos, getRegs(insn),
                                        addresses.getStart(target));
                    break;
                }
                default: {
                    throw new RuntimeException("shouldn't happen");
                }
            }

            addOutput(di);
        }

        @Override
        public void visitPlainCstInsn(mod.agus.jcoderz.dx.rop.code.PlainCstInsn insn) {
            mod.agus.jcoderz.dx.rop.code.SourcePosition pos = insn.getPosition();
            Dop opcode = RopToDop.dopFor(insn);
            mod.agus.jcoderz.dx.rop.code.Rop rop = insn.getOpcode();
            int ropOpcode = rop.getOpcode();
            DalvInsn di;

            if (rop.getBranchingness() != mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_NONE) {
                throw new RuntimeException("shouldn't happen");
            }

            if (ropOpcode == mod.agus.jcoderz.dx.rop.code.RegOps.MOVE_PARAM) {
                if (!paramsAreInOrder) {

                    mod.agus.jcoderz.dx.rop.code.RegisterSpec dest = insn.getResult();
                    int param =
                        ((CstInteger) insn.getConstant()).getValue();
                    mod.agus.jcoderz.dx.rop.code.RegisterSpec source =
                        mod.agus.jcoderz.dx.rop.code.RegisterSpec.make(regCount - paramSize + param,
                                dest.getType());
                    di = new SimpleInsn(opcode, pos,
                                        mod.agus.jcoderz.dx.rop.code.RegisterSpecList.make(dest, source));
                    addOutput(di);
                }
            } else {

                mod.agus.jcoderz.dx.rop.code.RegisterSpecList regs = getRegs(insn);
                di = new CstInsn(opcode, pos, regs, insn.getConstant());
                addOutput(di);
            }
        }

        @Override
        public void visitSwitchInsn(mod.agus.jcoderz.dx.rop.code.SwitchInsn insn) {
            mod.agus.jcoderz.dx.rop.code.SourcePosition pos = insn.getPosition();
            mod.agus.jcoderz.dx.util.IntList cases = insn.getCases();
            IntList successors = block.getSuccessors();
            int casesSz = cases.size();
            int succSz = successors.size();
            int primarySuccessor = block.getPrimarySuccessor();

            if ((casesSz != (succSz - 1)) ||
                (primarySuccessor != successors.get(casesSz))) {
                throw new RuntimeException("shouldn't happen");
            }

            CodeAddress[] switchTargets = new CodeAddress[casesSz];

            for (int i = 0; i < casesSz; i++) {
                int label = successors.get(i);
                switchTargets[i] = addresses.getStart(label);
            }

            CodeAddress dataAddress = new CodeAddress(pos);

            CodeAddress switchAddress =
                new CodeAddress(lastAddress.getPosition(), true);
            SwitchData dataInsn =
                new SwitchData(pos, switchAddress, cases, switchTargets);
            Dop opcode = dataInsn.isPacked() ?
                Dops.PACKED_SWITCH : Dops.SPARSE_SWITCH;
            TargetInsn switchInsn =
                new TargetInsn(opcode, pos, getRegs(insn), dataAddress);

            addOutput(switchAddress);
            addOutput(switchInsn);

            addOutputSuffix(new mod.agus.jcoderz.dx.dex.code.OddSpacer(pos));
            addOutputSuffix(dataAddress);
            addOutputSuffix(dataInsn);
        }

        private mod.agus.jcoderz.dx.rop.code.RegisterSpec getNextMoveResultPseudo()
        {
            int label = block.getPrimarySuccessor();

            if (label < 0) {
                return null;
            }

            mod.agus.jcoderz.dx.rop.code.Insn insn
                    = method.getBlocks().labelToBlock(label).getInsns().get(0);

            if (insn.getOpcode().getOpcode() != mod.agus.jcoderz.dx.rop.code.RegOps.MOVE_RESULT_PSEUDO) {
                return null;
            } else {
                return insn.getResult();
            }
        }

        @Override
        public void visitInvokePolymorphicInsn(InvokePolymorphicInsn insn) {
            mod.agus.jcoderz.dx.rop.code.SourcePosition pos = insn.getPosition();
            Dop opcode = RopToDop.dopFor(insn);
            mod.agus.jcoderz.dx.rop.code.Rop rop = insn.getOpcode();

            if (rop.getBranchingness() != mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_THROW) {
                throw new RuntimeException("Expected BRANCH_THROW got " + rop.getBranchingness());
            } else if (!rop.isCallLike()) {
                throw new RuntimeException("Expected call-like operation");
            }

            addOutput(lastAddress);

            mod.agus.jcoderz.dx.rop.code.RegisterSpecList regs = insn.getSources();
            mod.agus.jcoderz.dx.rop.cst.Constant[] constants = new mod.agus.jcoderz.dx.rop.cst.Constant[] {
                insn.getPolymorphicMethod(),
                insn.getCallSiteProto()
                };
            DalvInsn di = new MultiCstInsn(opcode, pos, regs, constants);

            addOutput(di);
        }

        @Override
        public void visitThrowingCstInsn(mod.agus.jcoderz.dx.rop.code.ThrowingCstInsn insn) {
            mod.agus.jcoderz.dx.rop.code.SourcePosition pos = insn.getPosition();
            Dop opcode = RopToDop.dopFor(insn);
            mod.agus.jcoderz.dx.rop.code.Rop rop = insn.getOpcode();
            mod.agus.jcoderz.dx.rop.cst.Constant cst = insn.getConstant();

            if (rop.getBranchingness() != mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_THROW) {
                throw new RuntimeException("Expected BRANCH_THROW got " + rop.getBranchingness());
            }

            addOutput(lastAddress);

            if (rop.isCallLike()) {
                mod.agus.jcoderz.dx.rop.code.RegisterSpecList regs = insn.getSources();
                DalvInsn di = new CstInsn(opcode, pos, regs, cst);

                addOutput(di);
            } else {
                mod.agus.jcoderz.dx.rop.code.RegisterSpec realResult = getNextMoveResultPseudo();

                mod.agus.jcoderz.dx.rop.code.RegisterSpecList regs = getRegs(insn, realResult);
                DalvInsn di;

                boolean hasResult = opcode.hasResult()
                        || (rop.getOpcode() == mod.agus.jcoderz.dx.rop.code.RegOps.CHECK_CAST);

                if (hasResult != (realResult != null)) {
                    throw new RuntimeException(
                            "Insn with result/move-result-pseudo mismatch " +
                            insn);
                }

                if ((rop.getOpcode() == RegOps.NEW_ARRAY) &&
                    (opcode.getOpcode() != mod.agus.jcoderz.dx.io.Opcodes.NEW_ARRAY)) {
                    di = new SimpleInsn(opcode, pos, regs);
                } else {
                    di = new CstInsn(opcode, pos, regs, cst);
                }







                DalvInsn previousDi = getPrevNonSpecialInsn();
                if (opcode.getOpcode() == mod.agus.jcoderz.dx.io.Opcodes.INSTANCE_OF && previousDi != null) {
                    int prevOpcode = previousDi.getOpcode().getOpcode();
                    if (prevOpcode == mod.agus.jcoderz.dx.io.Opcodes.MOVE_OBJECT
                        || prevOpcode == mod.agus.jcoderz.dx.io.Opcodes.MOVE_OBJECT_FROM16
                        || prevOpcode == mod.agus.jcoderz.dx.io.Opcodes.MOVE_OBJECT_16) {
                        if (di.getRegisters().size() > 0 && previousDi.getRegisters().size() > 1
                            && (di.getRegisters().get(0).getReg()
                                    == previousDi.getRegisters().get(1).getReg())) {
                            DalvInsn nopDi = new SimpleInsn(Dops.NOP, pos, RegisterSpecList.EMPTY);
                            addOutput(nopDi);
                        }
                    }
                }
                addOutput(di);
            }
        }

        @Override
        public void visitThrowingInsn(mod.agus.jcoderz.dx.rop.code.ThrowingInsn insn) {
            mod.agus.jcoderz.dx.rop.code.SourcePosition pos = insn.getPosition();
            Dop opcode = RopToDop.dopFor(insn);
            mod.agus.jcoderz.dx.rop.code.Rop rop = insn.getOpcode();
            mod.agus.jcoderz.dx.rop.code.RegisterSpec realResult;

            if (rop.getBranchingness() != mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_THROW) {
                throw new RuntimeException("shouldn't happen");
            }

            realResult = getNextMoveResultPseudo();

            if (opcode.hasResult() != (realResult != null)) {
                throw new RuntimeException(
                        "Insn with result/move-result-pseudo mismatch" + insn);
            }

            addOutput(lastAddress);

            DalvInsn di = new SimpleInsn(opcode, pos,
                    getRegs(insn, realResult));

            addOutput(di);
        }

        @Override
        public void visitFillArrayDataInsn(FillArrayDataInsn insn) {
            SourcePosition pos = insn.getPosition();
            mod.agus.jcoderz.dx.rop.cst.Constant cst = insn.getConstant();
            ArrayList<Constant> values = insn.getInitValues();
            mod.agus.jcoderz.dx.rop.code.Rop rop = insn.getOpcode();

            if (rop.getBranchingness() != Rop.BRANCH_NONE) {
                throw new RuntimeException("shouldn't happen");
            }
            CodeAddress dataAddress = new CodeAddress(pos);
            ArrayData dataInsn =
                new ArrayData(pos, lastAddress, values, cst);

            TargetInsn fillArrayDataInsn =
                new TargetInsn(Dops.FILL_ARRAY_DATA, pos, getRegs(insn),
                        dataAddress);

            addOutput(lastAddress);
            addOutput(fillArrayDataInsn);

            addOutputSuffix(new OddSpacer(pos));
            addOutputSuffix(dataAddress);
            addOutputSuffix(dataInsn);
        }

        protected void addOutput(DalvInsn insn) {
            output.add(insn);
        }

        protected DalvInsn getPrevNonSpecialInsn() {
            for (int i = output.size() - 1; i >= 0; --i) {
                DalvInsn insn = output.get(i);
                if (insn.getOpcode().getOpcode() != Opcodes.SPECIAL_FORMAT) {
                    return insn;
                }
            }
            return null;
        }

        protected void addOutputSuffix(DalvInsn insn) {
            output.addSuffix(insn);
        }
    }

    private class LocalVariableAwareTranslationVisitor
            extends TranslationVisitor {
        private final mod.agus.jcoderz.dx.rop.code.LocalVariableInfo locals;

        public LocalVariableAwareTranslationVisitor(OutputCollector output,
                                                    LocalVariableInfo locals) {
            super(output);
            this.locals = locals;
        }

        @Override
        public void visitPlainInsn(PlainInsn insn) {
            super.visitPlainInsn(insn);
            addIntroductionIfNecessary(insn);
        }

        @Override
        public void visitPlainCstInsn(PlainCstInsn insn) {
            super.visitPlainCstInsn(insn);
            addIntroductionIfNecessary(insn);
        }

        @Override
        public void visitSwitchInsn(SwitchInsn insn) {
            super.visitSwitchInsn(insn);
            addIntroductionIfNecessary(insn);
        }

        @Override
        public void visitThrowingCstInsn(ThrowingCstInsn insn) {
            super.visitThrowingCstInsn(insn);
            addIntroductionIfNecessary(insn);
        }

        @Override
        public void visitThrowingInsn(ThrowingInsn insn) {
            super.visitThrowingInsn(insn);
            addIntroductionIfNecessary(insn);
        }

        public void addIntroductionIfNecessary(Insn insn) {
            RegisterSpec spec = locals.getAssignment(insn);

            if (spec != null) {
                addOutput(new LocalStart(insn.getPosition(), spec));
            }
        }
    }
}
