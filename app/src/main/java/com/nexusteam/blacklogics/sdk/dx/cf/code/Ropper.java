
package mod.agus.jcoderz.dx.cf.code;

import mod.agus.jcoderz.dx.cf.iface.MethodList;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import mod.agus.jcoderz.dx.dex.DexOptions;
import mod.agus.jcoderz.dx.rop.code.AccessFlags;
import mod.agus.jcoderz.dx.rop.code.BasicBlock;
import mod.agus.jcoderz.dx.rop.code.BasicBlockList;
import mod.agus.jcoderz.dx.rop.code.Insn;
import mod.agus.jcoderz.dx.rop.code.InsnList;
import mod.agus.jcoderz.dx.rop.code.PlainCstInsn;
import mod.agus.jcoderz.dx.rop.code.PlainInsn;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.Rop;
import mod.agus.jcoderz.dx.rop.code.RopMethod;
import mod.agus.jcoderz.dx.rop.code.Rops;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.rop.code.ThrowingCstInsn;
import mod.agus.jcoderz.dx.rop.code.ThrowingInsn;
import mod.agus.jcoderz.dx.rop.code.TranslationAdvice;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Prototype;
import mod.agus.jcoderz.dx.rop.type.StdTypeList;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeList;
import mod.agus.jcoderz.dx.util.Bits;
import mod.agus.jcoderz.dx.util.Hex;
import mod.agus.jcoderz.dx.util.IntList;

public final class Ropper {
    private static final int PARAM_ASSIGNMENT = -1;

    private static final int RETURN = -2;

    private static final int SYNCH_RETURN = -3;

    private static final int SYNCH_SETUP_1 = -4;

    private static final int SYNCH_SETUP_2 = -5;

    private static final int SYNCH_CATCH_1 = -6;

    private static final int SYNCH_CATCH_2 = -7;

    private static final int SPECIAL_LABEL_COUNT = 7;

    private final ConcreteMethod method;

    private final ByteBlockList blocks;

    private final int maxLocals;

    private final int maxLabel;

    private final RopperMachine machine;

    private final mod.agus.jcoderz.dx.cf.code.Simulator sim;

    private final Frame[] startFrames;

    private final ArrayList<mod.agus.jcoderz.dx.rop.code.BasicBlock> result;

    private final ArrayList<mod.agus.jcoderz.dx.util.IntList> resultSubroutines;

    private final CatchInfo[] catchInfos;

    private boolean synchNeedsExceptionHandler;

    private final Subroutine[] subroutines;

    private boolean hasSubroutines;

    private final ExceptionSetupLabelAllocator exceptionSetupLabelAllocator;

    private class CatchInfo {
        private final Map<mod.agus.jcoderz.dx.rop.type.Type, ExceptionHandlerSetup> setups =
                new HashMap<mod.agus.jcoderz.dx.rop.type.Type, ExceptionHandlerSetup>();

        ExceptionHandlerSetup getSetup(mod.agus.jcoderz.dx.rop.type.Type caughtType) {
            ExceptionHandlerSetup handler = setups.get(caughtType);
            if (handler == null) {
                int handlerSetupLabel = exceptionSetupLabelAllocator.getNextLabel();
                handler = new ExceptionHandlerSetup(caughtType, handlerSetupLabel);
                setups.put(caughtType, handler);
            }
            return handler;
        }

       Collection<ExceptionHandlerSetup> getSetups() {
            return setups.values();
        }
    }

    private static class ExceptionHandlerSetup {
        private mod.agus.jcoderz.dx.rop.type.Type caughtType;
        private int label;

        ExceptionHandlerSetup(mod.agus.jcoderz.dx.rop.type.Type caughtType, int label) {
            this.caughtType = caughtType;
            this.label = label;
        }

        mod.agus.jcoderz.dx.rop.type.Type getCaughtType() {
            return caughtType;
        }

        public int getLabel() {
            return label;
        }
    }

    private class Subroutine {
        private BitSet callerBlocks;
        private BitSet retBlocks;
        private int startBlock;

        Subroutine(int startBlock) {
            this.startBlock = startBlock;
            retBlocks = new BitSet(maxLabel);
            callerBlocks = new BitSet(maxLabel);
            hasSubroutines = true;
        }

        Subroutine(int startBlock, int retBlock) {
            this(startBlock);
            addRetBlock(retBlock);
        }

        int getStartBlock() {
            return startBlock;
        }

        void addRetBlock(int retBlock) {
            retBlocks.set(retBlock);
        }

        void addCallerBlock(int label) {
            callerBlocks.set(label);
        }

        mod.agus.jcoderz.dx.util.IntList getSuccessors() {
            mod.agus.jcoderz.dx.util.IntList successors = new mod.agus.jcoderz.dx.util.IntList(callerBlocks.size());


            for (int label = callerBlocks.nextSetBit(0); label >= 0;
                 label = callerBlocks.nextSetBit(label+1)) {
                mod.agus.jcoderz.dx.rop.code.BasicBlock subCaller = labelToBlock(label);
                successors.add(subCaller.getSuccessors().get(0));
            }

            successors.setImmutable();

            return successors;
        }

        void mergeToSuccessors(Frame frame, int[] workSet) {
            for (int label = callerBlocks.nextSetBit(0); label >= 0;
                 label = callerBlocks.nextSetBit(label+1)) {
                mod.agus.jcoderz.dx.rop.code.BasicBlock subCaller = labelToBlock(label);
                int succLabel = subCaller.getSuccessors().get(0);

                Frame subFrame = frame.subFrameForLabel(startBlock, label);

                if (subFrame != null) {
                    mergeAndWorkAsNecessary(succLabel, -1, null,
                            subFrame, workSet);
                } else {
                    mod.agus.jcoderz.dx.util.Bits.set(workSet, label);
                }
            }
        }
    }

    public static mod.agus.jcoderz.dx.rop.code.RopMethod convert(ConcreteMethod method,
                                                                 mod.agus.jcoderz.dx.rop.code.TranslationAdvice advice, MethodList methods, mod.agus.jcoderz.dx.dex.DexOptions dexOptions) {
        try {
            Ropper r = new Ropper(method, advice, methods, dexOptions);
            r.doit();
            return r.getRopMethod();
        } catch (SimException ex) {
            ex.addContext("...while working on method " +
                          method.getNat().toHuman());
            throw ex;
        }
    }

    private Ropper(ConcreteMethod method, TranslationAdvice advice, MethodList methods,
                   DexOptions dexOptions) {
        if (method == null) {
            throw new NullPointerException("method == null");
        }

        if (advice == null) {
            throw new NullPointerException("advice == null");
        }

        this.method = method;
        this.blocks = BasicBlocker.identifyBlocks(method);
        this.maxLabel = blocks.getMaxLabel();
        this.maxLocals = method.getMaxLocals();
        this.machine = new RopperMachine(this, method, advice, methods);
        this.sim = new Simulator(machine, method, dexOptions);
        this.startFrames = new Frame[maxLabel];
        this.subroutines = new Subroutine[maxLabel];

        this.result = new ArrayList<mod.agus.jcoderz.dx.rop.code.BasicBlock>(blocks.size() * 2 + 10);
        this.resultSubroutines =
            new ArrayList<mod.agus.jcoderz.dx.util.IntList>(blocks.size() * 2 + 10);

        this.catchInfos = new CatchInfo[maxLabel];
        this.synchNeedsExceptionHandler = false;

        startFrames[0] = new Frame(maxLocals, method.getMaxStack());
        exceptionSetupLabelAllocator = new ExceptionSetupLabelAllocator();
    }

     int getFirstTempStackReg() {
        int regCount = getNormalRegCount();
        return isSynchronized() ? regCount + 1 : regCount;
    }

    private int getSpecialLabel(int label) {
        return maxLabel + method.getCatches().size() + ~label;
    }

    private int getMinimumUnreservedLabel() {

        return maxLabel + method.getCatches().size() + SPECIAL_LABEL_COUNT;
    }

    private int getAvailableLabel() {
        int candidate = getMinimumUnreservedLabel();

        for (mod.agus.jcoderz.dx.rop.code.BasicBlock bb : result) {
            int label = bb.getLabel();
            if (label >= candidate) {
                candidate = label + 1;
            }
        }

        return candidate;
    }

    private boolean isSynchronized() {
        int accessFlags = method.getAccessFlags();
        return (accessFlags & mod.agus.jcoderz.dx.rop.code.AccessFlags.ACC_SYNCHRONIZED) != 0;
    }

    private boolean isStatic() {
        int accessFlags = method.getAccessFlags();
        return (accessFlags & AccessFlags.ACC_STATIC) != 0;
    }

    private int getNormalRegCount() {
        return maxLocals + method.getMaxStack();
    }

    private mod.agus.jcoderz.dx.rop.code.RegisterSpec getSynchReg() {
        int reg = getNormalRegCount();
        return mod.agus.jcoderz.dx.rop.code.RegisterSpec.make((reg < 1) ? 1 : reg, mod.agus.jcoderz.dx.rop.type.Type.OBJECT);
    }

    private int labelToResultIndex(int label) {
        int sz = result.size();
        for (int i = 0; i < sz; i++) {
            mod.agus.jcoderz.dx.rop.code.BasicBlock one = result.get(i);
            if (one.getLabel() == label) {
                return i;
            }
        }

        return -1;
    }

    private mod.agus.jcoderz.dx.rop.code.BasicBlock labelToBlock(int label) {
        int idx = labelToResultIndex(label);

        if (idx < 0) {
            throw new IllegalArgumentException("no such label " +
                    mod.agus.jcoderz.dx.util.Hex.u2(label));
        }

        return result.get(idx);
    }

    private void addBlock(mod.agus.jcoderz.dx.rop.code.BasicBlock block, mod.agus.jcoderz.dx.util.IntList subroutines) {
        if (block == null) {
            throw new NullPointerException("block == null");
        }

        result.add(block);
        subroutines.throwIfMutable();
        resultSubroutines.add(subroutines);
    }

    private boolean addOrReplaceBlock(mod.agus.jcoderz.dx.rop.code.BasicBlock block, mod.agus.jcoderz.dx.util.IntList subroutines) {
        if (block == null) {
            throw new NullPointerException("block == null");
        }

        int idx = labelToResultIndex(block.getLabel());
        boolean ret;

        if (idx < 0) {
            ret = false;
        } else {
            removeBlockAndSpecialSuccessors(idx);
            ret = true;
        }

        result.add(block);
        subroutines.throwIfMutable();
        resultSubroutines.add(subroutines);
        return ret;
    }

    private boolean addOrReplaceBlockNoDelete(mod.agus.jcoderz.dx.rop.code.BasicBlock block,
                                              mod.agus.jcoderz.dx.util.IntList subroutines) {
        if (block == null) {
            throw new NullPointerException("block == null");
        }

        int idx = labelToResultIndex(block.getLabel());
        boolean ret;

        if (idx < 0) {
            ret = false;
        } else {
            result.remove(idx);
            resultSubroutines.remove(idx);
            ret = true;
        }

        result.add(block);
        subroutines.throwIfMutable();
        resultSubroutines.add(subroutines);
        return ret;
    }

    private void removeBlockAndSpecialSuccessors(int idx) {
        int minLabel = getMinimumUnreservedLabel();
        mod.agus.jcoderz.dx.rop.code.BasicBlock block = result.get(idx);
        mod.agus.jcoderz.dx.util.IntList successors = block.getSuccessors();
        int sz = successors.size();

        result.remove(idx);
        resultSubroutines.remove(idx);

        for (int i = 0; i < sz; i++) {
            int label = successors.get(i);
            if (label >= minLabel) {
                idx = labelToResultIndex(label);
                if (idx < 0) {
                    throw new RuntimeException("Invalid label "
                            + mod.agus.jcoderz.dx.util.Hex.u2(label));
                }
                removeBlockAndSpecialSuccessors(idx);
            }
        }
    }

    private mod.agus.jcoderz.dx.rop.code.RopMethod getRopMethod() {



        int sz = result.size();
        mod.agus.jcoderz.dx.rop.code.BasicBlockList bbl = new BasicBlockList(sz);
        for (int i = 0; i < sz; i++) {
            bbl.set(i, result.get(i));
        }
        bbl.setImmutable();



        return new RopMethod(bbl, getSpecialLabel(PARAM_ASSIGNMENT));
    }

    private void doit() {
        int[] workSet = mod.agus.jcoderz.dx.util.Bits.makeBitSet(maxLabel);

        mod.agus.jcoderz.dx.util.Bits.set(workSet, 0);
        addSetupBlocks();
        setFirstFrame();

        for (;;) {
            int offset = mod.agus.jcoderz.dx.util.Bits.findFirst(workSet, 0);
            if (offset < 0) {
                break;
            }
            mod.agus.jcoderz.dx.util.Bits.clear(workSet, offset);
            ByteBlock block = blocks.labelToBlock(offset);
            Frame frame = startFrames[offset];
            try {
                processBlock(block, frame, workSet);
            } catch (SimException ex) {
                ex.addContext("...while working on block " + mod.agus.jcoderz.dx.util.Hex.u2(offset));
                throw ex;
            }
        }

        addReturnBlock();
        addSynchExceptionHandlerBlock();
        addExceptionSetupBlocks();

        if (hasSubroutines) {

            inlineSubroutines();
        }
    }

    private void setFirstFrame() {
        mod.agus.jcoderz.dx.rop.type.Prototype desc = method.getEffectiveDescriptor();
        startFrames[0].initializeWithParameters(desc.getParameterTypes());
        startFrames[0].setImmutable();
    }

    private void processBlock(ByteBlock block, Frame frame, int[] workSet) {

        mod.agus.jcoderz.dx.cf.code.ByteCatchList catches = block.getCatches();
        machine.startBlock(catches.toRopCatchList());

        frame = frame.copy();
        sim.simulate(block, frame);
        frame.setImmutable();

        int extraBlockCount = machine.getExtraBlockCount();
        ArrayList<mod.agus.jcoderz.dx.rop.code.Insn> insns = machine.getInsns();
        int insnSz = insns.size();


        int catchSz = catches.size();
        mod.agus.jcoderz.dx.util.IntList successors = block.getSuccessors();

        int startSuccessorIndex;

        Subroutine calledSubroutine = null;
        if (machine.hasJsr()) {
            startSuccessorIndex = 1;

            int subroutineLabel = successors.get(1);

            if (subroutines[subroutineLabel] == null) {
                subroutines[subroutineLabel] =
                    new Subroutine (subroutineLabel);
            }

            subroutines[subroutineLabel].addCallerBlock(block.getLabel());

            calledSubroutine = subroutines[subroutineLabel];
        } else if (machine.hasRet()) {

            ReturnAddress ra = machine.getReturnAddress();
            int subroutineLabel = ra.getSubroutineAddress();

            if (subroutines[subroutineLabel] == null) {
                subroutines[subroutineLabel]
                        = new Subroutine (subroutineLabel, block.getLabel());
            } else {
                subroutines[subroutineLabel].addRetBlock(block.getLabel());
            }

            successors = subroutines[subroutineLabel].getSuccessors();
            subroutines[subroutineLabel]
                    .mergeToSuccessors(frame, workSet);

            startSuccessorIndex = successors.size();
        } else if (machine.wereCatchesUsed()) {
            startSuccessorIndex = catchSz;
        } else {
            startSuccessorIndex = 0;
        }

        int succSz = successors.size();
        for (int i = startSuccessorIndex; i < succSz;
             i++) {
            int succ = successors.get(i);
            try {
                mergeAndWorkAsNecessary(succ, block.getLabel(),
                        calledSubroutine, frame, workSet);
            } catch (SimException ex) {
                ex.addContext("...while merging to block " + mod.agus.jcoderz.dx.util.Hex.u2(succ));
                throw ex;
            }
        }

        if ((succSz == 0) && machine.returns()) {
            successors = mod.agus.jcoderz.dx.util.IntList.makeImmutable(getSpecialLabel(RETURN));
            succSz = 1;
        }

        int primarySucc;

        if (succSz == 0) {
            primarySucc = -1;
        } else {
            primarySucc = machine.getPrimarySuccessorIndex();
            if (primarySucc >= 0) {
                primarySucc = successors.get(primarySucc);
            }
        }

        boolean synch = isSynchronized() && machine.canThrow();

        if (synch || (catchSz != 0)) {
            boolean catchesAny = false;
            mod.agus.jcoderz.dx.util.IntList newSucc = new mod.agus.jcoderz.dx.util.IntList(succSz);
            for (int i = 0; i < catchSz; i++) {
                ByteCatchList.Item one = catches.get(i);
                mod.agus.jcoderz.dx.rop.cst.CstType exceptionClass = one.getExceptionClass();
                int targ = one.getHandlerPc();

                catchesAny |= (exceptionClass == CstType.OBJECT);

                Frame f = frame.makeExceptionHandlerStartFrame(exceptionClass);

                try {
                    mergeAndWorkAsNecessary(targ, block.getLabel(),
                            null, f, workSet);
                } catch (SimException ex) {
                    ex.addContext("...while merging exception to block " +
                                  mod.agus.jcoderz.dx.util.Hex.u2(targ));
                    throw ex;
                }

                CatchInfo handlers = catchInfos[targ];
                if (handlers == null) {
                    handlers = new CatchInfo();
                    catchInfos[targ] = handlers;
                }
                ExceptionHandlerSetup handler = handlers.getSetup(exceptionClass.getClassType());

                newSucc.add(handler.getLabel());
            }

            if (synch && !catchesAny) {
                newSucc.add(getSpecialLabel(SYNCH_CATCH_1));
                synchNeedsExceptionHandler = true;

                for (int i = insnSz - extraBlockCount - 1; i < insnSz; i++) {
                    mod.agus.jcoderz.dx.rop.code.Insn insn = insns.get(i);
                    if (insn.canThrow()) {
                        insn = insn.withAddedCatch(mod.agus.jcoderz.dx.rop.type.Type.OBJECT);
                        insns.set(i, insn);
                    }
                }
            }

            if (primarySucc >= 0) {
                newSucc.add(primarySucc);
            }

            newSucc.setImmutable();
            successors = newSucc;
        }



        int primarySuccListIndex = successors.indexOf(primarySucc);

        for (; extraBlockCount > 0; extraBlockCount--) {
            mod.agus.jcoderz.dx.rop.code.Insn extraInsn = insns.get(--insnSz);
            boolean needsGoto
                    = extraInsn.getOpcode().getBranchingness()
                        == mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_NONE;
            mod.agus.jcoderz.dx.rop.code.InsnList il = new mod.agus.jcoderz.dx.rop.code.InsnList(needsGoto ? 2 : 1);
            mod.agus.jcoderz.dx.util.IntList extraBlockSuccessors = successors;

            il.set(0, extraInsn);

            if (needsGoto) {
                il.set(1, new mod.agus.jcoderz.dx.rop.code.PlainInsn(mod.agus.jcoderz.dx.rop.code.Rops.GOTO,
                        extraInsn.getPosition(), null,
                        mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY));
                extraBlockSuccessors = mod.agus.jcoderz.dx.util.IntList.makeImmutable(primarySucc);
            }
            il.setImmutable();

            int label = getAvailableLabel();
            mod.agus.jcoderz.dx.rop.code.BasicBlock bb = new mod.agus.jcoderz.dx.rop.code.BasicBlock(label, il, extraBlockSuccessors,
                    primarySucc);

            addBlock(bb, frame.getSubroutines());

            successors = successors.mutableCopy();
            successors.set(primarySuccListIndex, label);
            successors.setImmutable();
            primarySucc = label;
        }

        mod.agus.jcoderz.dx.rop.code.Insn lastInsn = (insnSz == 0) ? null : insns.get(insnSz - 1);

        if ((lastInsn == null) ||
            (lastInsn.getOpcode().getBranchingness() == mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_NONE)) {
            mod.agus.jcoderz.dx.rop.code.SourcePosition pos = (lastInsn == null) ? mod.agus.jcoderz.dx.rop.code.SourcePosition.NO_INFO :
                lastInsn.getPosition();
            insns.add(new mod.agus.jcoderz.dx.rop.code.PlainInsn(mod.agus.jcoderz.dx.rop.code.Rops.GOTO, pos, null,
                                    mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY));
            insnSz++;
        }


        mod.agus.jcoderz.dx.rop.code.InsnList il = new mod.agus.jcoderz.dx.rop.code.InsnList(insnSz);
        for (int i = 0; i < insnSz; i++) {
            il.set(i, insns.get(i));
        }
        il.setImmutable();

        mod.agus.jcoderz.dx.rop.code.BasicBlock bb =
            new mod.agus.jcoderz.dx.rop.code.BasicBlock(block.getLabel(), il, successors, primarySucc);
        addOrReplaceBlock(bb, frame.getSubroutines());
    }

    private void mergeAndWorkAsNecessary(int label, int pred,
            Subroutine calledSubroutine, Frame frame, int[] workSet) {
        Frame existing = startFrames[label];
        Frame merged;

        if (existing != null) {
            if (calledSubroutine != null) {
                merged = existing.mergeWithSubroutineCaller(frame,
                        calledSubroutine.getStartBlock(), pred);
            } else {
                merged = existing.mergeWith(frame);
            }
            if (merged != existing) {
                startFrames[label] = merged;
                mod.agus.jcoderz.dx.util.Bits.set(workSet, label);
            }
        } else {

            if (calledSubroutine != null) {
                startFrames[label]
                        = frame.makeNewSubroutineStartFrame(label, pred);
            } else {
                startFrames[label] = frame;
            }
            Bits.set(workSet, label);
        }
    }

    private void addSetupBlocks() {
        LocalVariableList localVariables = method.getLocalVariables();
        mod.agus.jcoderz.dx.rop.code.SourcePosition pos = method.makeSourcePosistion(0);
        Prototype desc = method.getEffectiveDescriptor();
        mod.agus.jcoderz.dx.rop.type.StdTypeList params = desc.getParameterTypes();
        int sz = params.size();
        mod.agus.jcoderz.dx.rop.code.InsnList insns = new mod.agus.jcoderz.dx.rop.code.InsnList(sz + 1);
        int at = 0;

        for (int i = 0; i < sz; i++) {
            mod.agus.jcoderz.dx.rop.type.Type one = params.get(i);
            LocalVariableList.Item local =
                localVariables.pcAndIndexToLocal(0, at);
            mod.agus.jcoderz.dx.rop.code.RegisterSpec result = (local == null) ?
                mod.agus.jcoderz.dx.rop.code.RegisterSpec.make(at, one) :
                mod.agus.jcoderz.dx.rop.code.RegisterSpec.makeLocalOptional(at, one, local.getLocalItem());

            mod.agus.jcoderz.dx.rop.code.Insn insn = new mod.agus.jcoderz.dx.rop.code.PlainCstInsn(mod.agus.jcoderz.dx.rop.code.Rops.opMoveParam(one), pos, result,
                                         mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY,
                                         mod.agus.jcoderz.dx.rop.cst.CstInteger.make(at));
            insns.set(i, insn);
            at += one.getCategory();
        }

        insns.set(sz, new mod.agus.jcoderz.dx.rop.code.PlainInsn(mod.agus.jcoderz.dx.rop.code.Rops.GOTO, pos, null,
                                    mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY));
        insns.setImmutable();

        boolean synch = isSynchronized();
        int label = synch ? getSpecialLabel(SYNCH_SETUP_1) : 0;
        mod.agus.jcoderz.dx.rop.code.BasicBlock bb =
            new mod.agus.jcoderz.dx.rop.code.BasicBlock(getSpecialLabel(PARAM_ASSIGNMENT), insns,
                           mod.agus.jcoderz.dx.util.IntList.makeImmutable(label), label);
        addBlock(bb, mod.agus.jcoderz.dx.util.IntList.EMPTY);

        if (synch) {
            mod.agus.jcoderz.dx.rop.code.RegisterSpec synchReg = getSynchReg();
            mod.agus.jcoderz.dx.rop.code.Insn insn;
            if (isStatic()) {
                insn = new ThrowingCstInsn(mod.agus.jcoderz.dx.rop.code.Rops.CONST_OBJECT, pos,
                                           mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY,
                                           mod.agus.jcoderz.dx.rop.type.StdTypeList.EMPTY,
                                           method.getDefiningClass());
                insns = new mod.agus.jcoderz.dx.rop.code.InsnList(1);
                insns.set(0, insn);
            } else {
                insns = new mod.agus.jcoderz.dx.rop.code.InsnList(2);
                insn = new PlainCstInsn(mod.agus.jcoderz.dx.rop.code.Rops.MOVE_PARAM_OBJECT, pos,
                                        synchReg, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY,
                                        CstInteger.VALUE_0);
                insns.set(0, insn);
                insns.set(1, new mod.agus.jcoderz.dx.rop.code.PlainInsn(mod.agus.jcoderz.dx.rop.code.Rops.GOTO, pos, null,
                                           mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY));
            }

            int label2 = getSpecialLabel(SYNCH_SETUP_2);
            insns.setImmutable();
            bb = new mod.agus.jcoderz.dx.rop.code.BasicBlock(label, insns,
                                mod.agus.jcoderz.dx.util.IntList.makeImmutable(label2), label2);
            addBlock(bb, mod.agus.jcoderz.dx.util.IntList.EMPTY);

            insns = new mod.agus.jcoderz.dx.rop.code.InsnList(isStatic() ? 2 : 1);

            if (isStatic()) {
                insns.set(0, new mod.agus.jcoderz.dx.rop.code.PlainInsn(mod.agus.jcoderz.dx.rop.code.Rops.opMoveResultPseudo(synchReg),
                        pos, synchReg, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY));
            }

            insn = new mod.agus.jcoderz.dx.rop.code.ThrowingInsn(mod.agus.jcoderz.dx.rop.code.Rops.MONITOR_ENTER, pos,
                                    mod.agus.jcoderz.dx.rop.code.RegisterSpecList.make(synchReg),
                                    mod.agus.jcoderz.dx.rop.type.StdTypeList.EMPTY);
            insns.set(isStatic() ? 1 :0, insn);
            insns.setImmutable();
            bb = new mod.agus.jcoderz.dx.rop.code.BasicBlock(label2, insns, mod.agus.jcoderz.dx.util.IntList.makeImmutable(0), 0);
            addBlock(bb, mod.agus.jcoderz.dx.util.IntList.EMPTY);
        }
    }

    private void addReturnBlock() {
        Rop returnOp = machine.getReturnOp();

        if (returnOp == null) {
            return;
        }

        mod.agus.jcoderz.dx.rop.code.SourcePosition returnPos = machine.getReturnPosition();
        int label = getSpecialLabel(RETURN);

        if (isSynchronized()) {
            mod.agus.jcoderz.dx.rop.code.InsnList insns = new mod.agus.jcoderz.dx.rop.code.InsnList(1);
            mod.agus.jcoderz.dx.rop.code.Insn insn = new mod.agus.jcoderz.dx.rop.code.ThrowingInsn(mod.agus.jcoderz.dx.rop.code.Rops.MONITOR_EXIT, returnPos,
                                         mod.agus.jcoderz.dx.rop.code.RegisterSpecList.make(getSynchReg()),
                                         mod.agus.jcoderz.dx.rop.type.StdTypeList.EMPTY);
            insns.set(0, insn);
            insns.setImmutable();

            int nextLabel = getSpecialLabel(SYNCH_RETURN);
            mod.agus.jcoderz.dx.rop.code.BasicBlock bb =
                new mod.agus.jcoderz.dx.rop.code.BasicBlock(label, insns,
                               mod.agus.jcoderz.dx.util.IntList.makeImmutable(nextLabel), nextLabel);
            addBlock(bb, mod.agus.jcoderz.dx.util.IntList.EMPTY);

            label = nextLabel;
        }

        mod.agus.jcoderz.dx.rop.code.InsnList insns = new mod.agus.jcoderz.dx.rop.code.InsnList(1);
        TypeList sourceTypes = returnOp.getSources();
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList sources;

        if (sourceTypes.size() == 0) {
            sources = mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY;
        } else {
            mod.agus.jcoderz.dx.rop.code.RegisterSpec source = mod.agus.jcoderz.dx.rop.code.RegisterSpec.make(0, sourceTypes.getType(0));
            sources = mod.agus.jcoderz.dx.rop.code.RegisterSpecList.make(source);
        }

        mod.agus.jcoderz.dx.rop.code.Insn insn = new mod.agus.jcoderz.dx.rop.code.PlainInsn(returnOp, returnPos, null, sources);
        insns.set(0, insn);
        insns.setImmutable();

        mod.agus.jcoderz.dx.rop.code.BasicBlock bb = new mod.agus.jcoderz.dx.rop.code.BasicBlock(label, insns, mod.agus.jcoderz.dx.util.IntList.EMPTY, -1);
        addBlock(bb, mod.agus.jcoderz.dx.util.IntList.EMPTY);
    }

    private void addSynchExceptionHandlerBlock() {
        if (!synchNeedsExceptionHandler) {
            return;
        }

        mod.agus.jcoderz.dx.rop.code.SourcePosition pos = method.makeSourcePosistion(0);
        mod.agus.jcoderz.dx.rop.code.RegisterSpec exReg = mod.agus.jcoderz.dx.rop.code.RegisterSpec.make(0, mod.agus.jcoderz.dx.rop.type.Type.THROWABLE);
        mod.agus.jcoderz.dx.rop.code.BasicBlock bb;
        mod.agus.jcoderz.dx.rop.code.Insn insn;

        mod.agus.jcoderz.dx.rop.code.InsnList insns = new mod.agus.jcoderz.dx.rop.code.InsnList(2);
        insn = new mod.agus.jcoderz.dx.rop.code.PlainInsn(mod.agus.jcoderz.dx.rop.code.Rops.opMoveException(Type.THROWABLE), pos,
                             exReg, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY);
        insns.set(0, insn);
        insn = new mod.agus.jcoderz.dx.rop.code.ThrowingInsn(mod.agus.jcoderz.dx.rop.code.Rops.MONITOR_EXIT, pos,
                                mod.agus.jcoderz.dx.rop.code.RegisterSpecList.make(getSynchReg()),
                                mod.agus.jcoderz.dx.rop.type.StdTypeList.EMPTY);
        insns.set(1, insn);
        insns.setImmutable();

        int label2 = getSpecialLabel(SYNCH_CATCH_2);
        bb = new mod.agus.jcoderz.dx.rop.code.BasicBlock(getSpecialLabel(SYNCH_CATCH_1), insns,
                            mod.agus.jcoderz.dx.util.IntList.makeImmutable(label2), label2);
        addBlock(bb, mod.agus.jcoderz.dx.util.IntList.EMPTY);

        insns = new mod.agus.jcoderz.dx.rop.code.InsnList(1);
        insn = new ThrowingInsn(mod.agus.jcoderz.dx.rop.code.Rops.THROW, pos,
                                mod.agus.jcoderz.dx.rop.code.RegisterSpecList.make(exReg),
                                StdTypeList.EMPTY);
        insns.set(0, insn);
        insns.setImmutable();

        bb = new mod.agus.jcoderz.dx.rop.code.BasicBlock(label2, insns, mod.agus.jcoderz.dx.util.IntList.EMPTY, -1);
        addBlock(bb, mod.agus.jcoderz.dx.util.IntList.EMPTY);
    }

    private void addExceptionSetupBlocks() {

        int len = catchInfos.length;
        for (int i = 0; i < len; i++) {
            CatchInfo catches = catchInfos[i];
            if (catches != null) {
                for (ExceptionHandlerSetup one : catches.getSetups()) {
                    mod.agus.jcoderz.dx.rop.code.Insn proto = labelToBlock(i).getFirstInsn();
                    SourcePosition pos = proto.getPosition();
                    mod.agus.jcoderz.dx.rop.code.InsnList il = new mod.agus.jcoderz.dx.rop.code.InsnList(2);

                    mod.agus.jcoderz.dx.rop.code.Insn insn = new mod.agus.jcoderz.dx.rop.code.PlainInsn(mod.agus.jcoderz.dx.rop.code.Rops.opMoveException(one.getCaughtType()),
                            pos,
                            RegisterSpec.make(maxLocals, one.getCaughtType()),
                            mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY);
                    il.set(0, insn);

                    insn = new PlainInsn(mod.agus.jcoderz.dx.rop.code.Rops.GOTO, pos, null,
                            RegisterSpecList.EMPTY);
                    il.set(1, insn);
                    il.setImmutable();

                    mod.agus.jcoderz.dx.rop.code.BasicBlock bb = new mod.agus.jcoderz.dx.rop.code.BasicBlock(one.getLabel(),
                            il,
                            mod.agus.jcoderz.dx.util.IntList.makeImmutable(i),
                            i);
                    addBlock(bb, startFrames[i].getSubroutines());
                }
            }
        }
    }

    private boolean isSubroutineCaller(mod.agus.jcoderz.dx.rop.code.BasicBlock bb) {
        mod.agus.jcoderz.dx.util.IntList successors = bb.getSuccessors();
        if (successors.size() < 2) return false;

        int subLabel = successors.get(1);

        return (subLabel < subroutines.length)
                && (subroutines[subLabel] != null);
    }

    private void inlineSubroutines() {
        final mod.agus.jcoderz.dx.util.IntList reachableSubroutineCallerLabels = new mod.agus.jcoderz.dx.util.IntList(4);

        forEachNonSubBlockDepthFirst(0, new mod.agus.jcoderz.dx.rop.code.BasicBlock.Visitor() {
            @Override
            public void visitBlock(mod.agus.jcoderz.dx.rop.code.BasicBlock b) {
                if (isSubroutineCaller(b)) {
                    reachableSubroutineCallerLabels.add(b.getLabel());
                }
            }
        });

        int largestAllocedLabel = getAvailableLabel();
        ArrayList<mod.agus.jcoderz.dx.util.IntList> labelToSubroutines
                = new ArrayList<mod.agus.jcoderz.dx.util.IntList>(largestAllocedLabel);
        for (int i = 0; i < largestAllocedLabel; i++) {
            labelToSubroutines.add(null);
        }

        for (int i = 0; i < result.size(); i++) {
            mod.agus.jcoderz.dx.rop.code.BasicBlock b = result.get(i);
            if (b == null) {
                continue;
            }
            mod.agus.jcoderz.dx.util.IntList subroutineList = resultSubroutines.get(i);
            labelToSubroutines.set(b.getLabel(), subroutineList);
        }

        int sz = reachableSubroutineCallerLabels.size();
        for (int i = 0 ; i < sz ; i++) {
            int label = reachableSubroutineCallerLabels.get(i);
            new SubroutineInliner(
                    new LabelAllocator(getAvailableLabel()),
                    labelToSubroutines)
                    .inlineSubroutineCalledFrom(labelToBlock(label));
        }


        deleteUnreachableBlocks();
    }

    private void deleteUnreachableBlocks() {
        final mod.agus.jcoderz.dx.util.IntList reachableLabels = new mod.agus.jcoderz.dx.util.IntList(result.size());


        resultSubroutines.clear();

        forEachNonSubBlockDepthFirst(getSpecialLabel(PARAM_ASSIGNMENT),
                new mod.agus.jcoderz.dx.rop.code.BasicBlock.Visitor() {

            @Override
            public void visitBlock(mod.agus.jcoderz.dx.rop.code.BasicBlock b) {
                reachableLabels.add(b.getLabel());
            }
        });

        reachableLabels.sort();

        for (int i = result.size() - 1 ; i >= 0 ; i--) {
            if (reachableLabels.indexOf(result.get(i).getLabel()) < 0) {
                result.remove(i);


            }
        }
    }

    private static class LabelAllocator {
        int nextAvailableLabel;

        LabelAllocator(int startLabel) {
            nextAvailableLabel = startLabel;
        }

        int getNextLabel() {
            return nextAvailableLabel++;
        }
    }

    private class ExceptionSetupLabelAllocator extends LabelAllocator {
        int maxSetupLabel;

        ExceptionSetupLabelAllocator() {
            super(maxLabel);
            maxSetupLabel = maxLabel + method.getCatches().size();
        }

        @Override
        int getNextLabel() {
            if (nextAvailableLabel >= maxSetupLabel) {
                throw new IndexOutOfBoundsException();
            }
            return nextAvailableLabel ++;
        }
    }

    private class SubroutineInliner {
        private final HashMap<Integer, Integer> origLabelToCopiedLabel;

        private final BitSet workList;

        private int subroutineStart;

        private int subroutineSuccessor;

        private final LabelAllocator labelAllocator;

        private final ArrayList<mod.agus.jcoderz.dx.util.IntList> labelToSubroutines;

        SubroutineInliner(final LabelAllocator labelAllocator,
                ArrayList<mod.agus.jcoderz.dx.util.IntList> labelToSubroutines) {
            origLabelToCopiedLabel = new HashMap<Integer, Integer>();

            workList = new BitSet(maxLabel);

            this.labelAllocator = labelAllocator;
            this.labelToSubroutines = labelToSubroutines;
        }

        void inlineSubroutineCalledFrom(final mod.agus.jcoderz.dx.rop.code.BasicBlock b) {
            subroutineSuccessor = b.getSuccessors().get(0);
            subroutineStart = b.getSuccessors().get(1);

            int newSubStartLabel = mapOrAllocateLabel(subroutineStart);

            for (int label = workList.nextSetBit(0); label >= 0;
                 label = workList.nextSetBit(0)) {
                workList.clear(label);
                int newLabel = origLabelToCopiedLabel.get(label);

                copyBlock(label, newLabel);

                if (isSubroutineCaller(labelToBlock(label))) {
                    new SubroutineInliner(labelAllocator, labelToSubroutines)
                        .inlineSubroutineCalledFrom(labelToBlock(newLabel));
                }
            }


            addOrReplaceBlockNoDelete(
                new mod.agus.jcoderz.dx.rop.code.BasicBlock(b.getLabel(), b.getInsns(),
                    mod.agus.jcoderz.dx.util.IntList.makeImmutable (newSubStartLabel),
                            newSubStartLabel),
                labelToSubroutines.get(b.getLabel()));
        }

        private void copyBlock(int origLabel, int newLabel) {

            mod.agus.jcoderz.dx.rop.code.BasicBlock origBlock = labelToBlock(origLabel);

            final mod.agus.jcoderz.dx.util.IntList origSuccessors = origBlock.getSuccessors();
            mod.agus.jcoderz.dx.util.IntList successors;
            int primarySuccessor = -1;
            Subroutine subroutine;

            if (isSubroutineCaller(origBlock)) {

                successors = mod.agus.jcoderz.dx.util.IntList.makeImmutable(
                        mapOrAllocateLabel(origSuccessors.get(0)),
                        origSuccessors.get(1));

            } else if (null
                    != (subroutine = subroutineFromRetBlock(origLabel))) {


                if (subroutine.startBlock != subroutineStart) {
                    throw new RuntimeException (
                            "ret instruction returns to label "
                            + mod.agus.jcoderz.dx.util.Hex.u2 (subroutine.startBlock)
                            + " expected: " + Hex.u2(subroutineStart));
                }

                successors = mod.agus.jcoderz.dx.util.IntList.makeImmutable(subroutineSuccessor);
                primarySuccessor = subroutineSuccessor;
            } else {


                int origPrimary = origBlock.getPrimarySuccessor();
                int sz = origSuccessors.size();

                successors = new mod.agus.jcoderz.dx.util.IntList(sz);

                for (int i = 0 ; i < sz ; i++) {
                    int origSuccLabel = origSuccessors.get(i);
                    int newSuccLabel =  mapOrAllocateLabel(origSuccLabel);

                    successors.add(newSuccLabel);

                    if (origPrimary == origSuccLabel) {
                        primarySuccessor = newSuccLabel;
                    }
                }

                successors.setImmutable();
            }

            addBlock (
                new mod.agus.jcoderz.dx.rop.code.BasicBlock(newLabel,
                    filterMoveReturnAddressInsns(origBlock.getInsns()),
                    successors, primarySuccessor),
                    labelToSubroutines.get(newLabel));
        }

        private boolean involvedInSubroutine(int label, int subroutineStart) {
            mod.agus.jcoderz.dx.util.IntList subroutinesList = labelToSubroutines.get(label);
            return (subroutinesList != null && subroutinesList.size() > 0
                    && subroutinesList.top() == subroutineStart);
        }

        private int mapOrAllocateLabel(int origLabel) {
            int resultLabel;
            Integer mappedLabel = origLabelToCopiedLabel.get(origLabel);

            if (mappedLabel != null) {
                resultLabel = mappedLabel;
            } else if (!involvedInSubroutine(origLabel,subroutineStart)) {
                resultLabel = origLabel;
            } else {
                resultLabel = labelAllocator.getNextLabel();
                workList.set(origLabel);
                origLabelToCopiedLabel.put(origLabel, resultLabel);


                while (labelToSubroutines.size() <= resultLabel) {
                    labelToSubroutines.add(null);
                }
                labelToSubroutines.set(resultLabel,
                        labelToSubroutines.get(origLabel));
            }

            return resultLabel;
        }
    }

    private Subroutine subroutineFromRetBlock(int label) {
        for (int i = subroutines.length - 1 ; i >= 0 ; i--) {
            if (subroutines[i] != null) {
                Subroutine subroutine = subroutines[i];

                if (subroutine.retBlocks.get(label)) {
                    return subroutine;
                }
            }
        }

        return null;
    }


    private mod.agus.jcoderz.dx.rop.code.InsnList filterMoveReturnAddressInsns(mod.agus.jcoderz.dx.rop.code.InsnList insns) {
        int sz;
        int newSz = 0;


        sz = insns.size();
        for (int i = 0; i < sz; i++) {
            if (insns.get(i).getOpcode() != mod.agus.jcoderz.dx.rop.code.Rops.MOVE_RETURN_ADDRESS) {
                newSz++;
            }
        }

        if (newSz == sz) {
            return insns;
        }


        mod.agus.jcoderz.dx.rop.code.InsnList newInsns = new InsnList(newSz);

        int newIndex = 0;
        for (int i = 0; i < sz; i++) {
            Insn insn = insns.get(i);
            if (insn.getOpcode() != Rops.MOVE_RETURN_ADDRESS) {
                newInsns.set(newIndex++, insn);
            }
        }

        newInsns.setImmutable();
        return newInsns;
    }

    private void forEachNonSubBlockDepthFirst(int firstLabel,
            mod.agus.jcoderz.dx.rop.code.BasicBlock.Visitor v) {
        forEachNonSubBlockDepthFirst0(labelToBlock(firstLabel),
                v, new BitSet(maxLabel));
    }

    private void forEachNonSubBlockDepthFirst0(
            mod.agus.jcoderz.dx.rop.code.BasicBlock next, BasicBlock.Visitor v, BitSet visited) {
        v.visitBlock(next);
        visited.set(next.getLabel());

        IntList successors = next.getSuccessors();
        int sz = successors.size();

        for (int i = 0; i < sz; i++) {
            int succ = successors.get(i);

            if (visited.get(succ)) {
                continue;
            }

            if (isSubroutineCaller(next) && i > 0) {

                continue;
            }

            int idx = labelToResultIndex(succ);
            if (idx >= 0) {
                forEachNonSubBlockDepthFirst0(result.get(idx), v, visited);
            }
        }
    }
}
