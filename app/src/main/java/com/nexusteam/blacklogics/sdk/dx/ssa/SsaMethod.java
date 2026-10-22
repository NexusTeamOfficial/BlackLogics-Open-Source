
package mod.agus.jcoderz.dx.ssa;

import mod.agus.jcoderz.dx.util.IntList;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import mod.agus.jcoderz.dx.rop.code.BasicBlockList;
import mod.agus.jcoderz.dx.rop.code.Insn;
import mod.agus.jcoderz.dx.rop.code.PlainInsn;
import mod.agus.jcoderz.dx.rop.code.RegOps;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.Rop;
import mod.agus.jcoderz.dx.rop.code.RopMethod;
import mod.agus.jcoderz.dx.rop.code.Rops;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;

public final class SsaMethod {
    private ArrayList<mod.agus.jcoderz.dx.ssa.SsaBasicBlock> blocks;

    private int entryBlockIndex;

    private int exitBlockIndex;

    private int registerCount;

    private int spareRegisterBase;

    private int borrowedSpareRegisters;

    private int maxLabel;

    private final int paramWidth;

    private final boolean isStatic;

    private mod.agus.jcoderz.dx.ssa.SsaInsn[] definitionList;

    private ArrayList<mod.agus.jcoderz.dx.ssa.SsaInsn>[] useList;

    private List<mod.agus.jcoderz.dx.ssa.SsaInsn>[] unmodifiableUseList;

    private boolean backMode;

    public static SsaMethod newFromRopMethod(mod.agus.jcoderz.dx.rop.code.RopMethod ropMethod,
                                             int paramWidth, boolean isStatic) {
        SsaMethod result = new SsaMethod(ropMethod, paramWidth, isStatic);

        result.convertRopToSsaBlocks(ropMethod);

        return result;
    }

    private SsaMethod(mod.agus.jcoderz.dx.rop.code.RopMethod ropMethod, int paramWidth, boolean isStatic) {
        this.paramWidth = paramWidth;
        this.isStatic = isStatic;
        this.backMode = false;
        this.maxLabel = ropMethod.getBlocks().getMaxLabel();
        this.registerCount = ropMethod.getBlocks().getRegCount();
        this.spareRegisterBase = registerCount;
    }

    static BitSet bitSetFromLabelList(mod.agus.jcoderz.dx.rop.code.BasicBlockList blocks,
                                      IntList labelList) {
        BitSet result = new BitSet(blocks.size());

        for (int i = 0, sz = labelList.size(); i < sz; i++) {
            result.set(blocks.indexOfLabel(labelList.get(i)));
        }

        return result;
    }

    public static IntList indexListFromLabelList(mod.agus.jcoderz.dx.rop.code.BasicBlockList ropBlocks,
                                                 IntList labelList) {

        IntList result = new IntList(labelList.size());

        for (int i = 0, sz = labelList.size(); i < sz; i++) {
            result.add(ropBlocks.indexOfLabel(labelList.get(i)));
        }

        return result;
    }

    private void convertRopToSsaBlocks(RopMethod rmeth) {
        BasicBlockList ropBlocks = rmeth.getBlocks();
        int sz = ropBlocks.size();

        blocks = new ArrayList<mod.agus.jcoderz.dx.ssa.SsaBasicBlock>(sz + 2);

        for (int i = 0; i < sz; i++) {
            mod.agus.jcoderz.dx.ssa.SsaBasicBlock sbb = mod.agus.jcoderz.dx.ssa.SsaBasicBlock.newFromRop(rmeth, i, this);
            blocks.add(sbb);
        }


        int origEntryBlockIndex = rmeth.getBlocks()
                .indexOfLabel(rmeth.getFirstLabel());

        mod.agus.jcoderz.dx.ssa.SsaBasicBlock entryBlock
                = blocks.get(origEntryBlockIndex).insertNewPredecessor();

        entryBlockIndex = entryBlock.getIndex();
        exitBlockIndex = -1;
    }

     void makeExitBlock() {
        if (exitBlockIndex >= 0) {
            throw new RuntimeException("must be called at most once");
        }

        exitBlockIndex = blocks.size();
        mod.agus.jcoderz.dx.ssa.SsaBasicBlock exitBlock
                = new mod.agus.jcoderz.dx.ssa.SsaBasicBlock(exitBlockIndex, maxLabel++, this);

        blocks.add(exitBlock);

        for (mod.agus.jcoderz.dx.ssa.SsaBasicBlock block : blocks) {
            block.exitBlockFixup(exitBlock);
        }

        if (exitBlock.getPredecessors().cardinality() == 0) {

            blocks.remove(exitBlockIndex);
            exitBlockIndex = -1;
            maxLabel--;
        }
    }

    private static mod.agus.jcoderz.dx.ssa.SsaInsn getGoto(mod.agus.jcoderz.dx.ssa.SsaBasicBlock block) {
        return new NormalSsaInsn (
                new mod.agus.jcoderz.dx.rop.code.PlainInsn(mod.agus.jcoderz.dx.rop.code.Rops.GOTO, mod.agus.jcoderz.dx.rop.code.SourcePosition.NO_INFO,
                    null, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY), block);
    }

    public mod.agus.jcoderz.dx.ssa.SsaBasicBlock makeNewGotoBlock() {
        int newIndex = blocks.size();
        mod.agus.jcoderz.dx.ssa.SsaBasicBlock newBlock = new mod.agus.jcoderz.dx.ssa.SsaBasicBlock(newIndex, maxLabel++, this);

        newBlock.getInsns().add(getGoto(newBlock));
        blocks.add(newBlock);

        return newBlock;
    }

    public int getEntryBlockIndex() {
        return entryBlockIndex;
    }

    public mod.agus.jcoderz.dx.ssa.SsaBasicBlock getEntryBlock() {
        return blocks.get(entryBlockIndex);
    }

    public int getExitBlockIndex() {
        return exitBlockIndex;
    }

    public mod.agus.jcoderz.dx.ssa.SsaBasicBlock getExitBlock() {
        return exitBlockIndex < 0 ? null : blocks.get(exitBlockIndex);
    }

    public int blockIndexToRopLabel(int bi) {
        if (bi < 0) {
            return -1;
        }
        return blocks.get(bi).getRopLabel();
    }

    public int getRegCount() {
        return registerCount;
    }

    public int getParamWidth() {
        return paramWidth;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public int borrowSpareRegister(int category) {
        int result = spareRegisterBase + borrowedSpareRegisters;

        borrowedSpareRegisters += category;
        registerCount = Math.max(registerCount, result + category);

        return result;
    }

    public void returnSpareRegisters() {
        borrowedSpareRegisters = 0;
    }

    public ArrayList<mod.agus.jcoderz.dx.ssa.SsaBasicBlock> getBlocks() {
        return blocks;
    }

    public BitSet computeReachability() {
        final int size = blocks.size();
        BitSet reachableUnvisited = new BitSet(size);
        BitSet reachableVisited = new BitSet(size);

        reachableUnvisited.set(getEntryBlock().getIndex());

        int index;
        while ((index = reachableUnvisited.nextSetBit(0)) != -1) {
            reachableVisited.set(index);
            reachableUnvisited.or(blocks.get(index).getSuccessors());
            reachableUnvisited.andNot(reachableVisited);
        }

        return reachableVisited;
    }

    public void mapRegisters(RegisterMapper mapper) {
        for (mod.agus.jcoderz.dx.ssa.SsaBasicBlock block : getBlocks()) {
            for (mod.agus.jcoderz.dx.ssa.SsaInsn insn : block.getInsns()) {
                insn.mapRegisters(mapper);
            }
        }

        registerCount = mapper.getNewRegisterCount();
        spareRegisterBase = registerCount;
    }

    public mod.agus.jcoderz.dx.ssa.SsaInsn getDefinitionForRegister(int reg) {
        if (backMode) {
            throw new RuntimeException("No def list in back mode");
        }

        if (definitionList != null) {
            return definitionList[reg];
        }

        definitionList = new mod.agus.jcoderz.dx.ssa.SsaInsn[getRegCount()];

        forEachInsn(new mod.agus.jcoderz.dx.ssa.SsaInsn.Visitor() {
            @Override
            public void visitMoveInsn (NormalSsaInsn insn) {
                definitionList[insn.getResult().getReg()] = insn;
            }
            @Override
            public void visitPhiInsn (PhiInsn phi) {
                definitionList[phi.getResult().getReg()] = phi;
            }
            @Override
            public void visitNonMoveInsn (NormalSsaInsn insn) {
                mod.agus.jcoderz.dx.rop.code.RegisterSpec result = insn.getResult();
                if (result != null) {
                    definitionList[insn.getResult().getReg()] = insn;
                }
            }
        });

        return definitionList[reg];
    }

    private void buildUseList() {
        if (backMode) {
            throw new RuntimeException("No use list in back mode");
        }

        useList = new ArrayList[registerCount];

        for (int i = 0; i < registerCount; i++) {
            useList[i] = new ArrayList();
        }

        forEachInsn(new mod.agus.jcoderz.dx.ssa.SsaInsn.Visitor() {
            @Override
            public void visitMoveInsn (NormalSsaInsn insn) {
                addToUses(insn);
            }
            @Override
            public void visitPhiInsn (PhiInsn phi) {
                addToUses(phi);
            }
            @Override
            public void visitNonMoveInsn (NormalSsaInsn insn) {
                addToUses(insn);
            }
            private void addToUses(mod.agus.jcoderz.dx.ssa.SsaInsn insn) {
                mod.agus.jcoderz.dx.rop.code.RegisterSpecList rl = insn.getSources();
                int sz = rl.size();

                for (int i = 0; i < sz; i++) {
                    useList[rl.get(i).getReg()].add(insn);
                }
            }
        });

        unmodifiableUseList = new List[registerCount];

        for (int i = 0; i < registerCount; i++) {
            unmodifiableUseList[i] = Collections.unmodifiableList(useList[i]);
        }
    }

     void onSourceChanged(mod.agus.jcoderz.dx.ssa.SsaInsn insn,
                                     mod.agus.jcoderz.dx.rop.code.RegisterSpec oldSource, mod.agus.jcoderz.dx.rop.code.RegisterSpec newSource) {
        if (useList == null) return;

        if (oldSource != null) {
            int reg = oldSource.getReg();
            useList[reg].remove(insn);
        }

        int reg = newSource.getReg();
        if (useList.length <= reg) {
            useList = null;
            return;
        }
        useList[reg].add(insn);
    }

     void onSourcesChanged(mod.agus.jcoderz.dx.ssa.SsaInsn insn,
                                      mod.agus.jcoderz.dx.rop.code.RegisterSpecList oldSources) {
        if (useList == null) return;

        if (oldSources != null) {
            removeFromUseList(insn, oldSources);
        }

        mod.agus.jcoderz.dx.rop.code.RegisterSpecList sources = insn.getSources();
        int szNew = sources.size();

        for (int i = 0; i < szNew; i++) {
            int reg = sources.get(i).getReg();
            useList[reg].add(insn);
        }
    }

    private void removeFromUseList(mod.agus.jcoderz.dx.ssa.SsaInsn insn, mod.agus.jcoderz.dx.rop.code.RegisterSpecList oldSources) {
        if (oldSources == null) {
            return;
        }

        int szNew = oldSources.size();
        for (int i = 0; i < szNew; i++) {
            if (!useList[oldSources.get(i).getReg()].remove(insn)) {
                throw new RuntimeException("use not found");
            }
        }
    }

     void onInsnAdded(mod.agus.jcoderz.dx.ssa.SsaInsn insn) {
        onSourcesChanged(insn, null);
        updateOneDefinition(insn, null);
    }

     void onInsnRemoved(mod.agus.jcoderz.dx.ssa.SsaInsn insn) {
        if (useList != null) {
            removeFromUseList(insn, insn.getSources());
        }

        mod.agus.jcoderz.dx.rop.code.RegisterSpec resultReg = insn.getResult();
        if (definitionList != null && resultReg != null) {
            definitionList[resultReg.getReg()] = null;
        }
    }

    public void onInsnsChanged() {

        definitionList = null;


        useList = null;
        unmodifiableUseList = null;
    }

     void updateOneDefinition(mod.agus.jcoderz.dx.ssa.SsaInsn insn,
                                         mod.agus.jcoderz.dx.rop.code.RegisterSpec oldResult) {
        if (definitionList == null) return;

        if (oldResult != null) {
            int reg = oldResult.getReg();
            definitionList[reg] = null;
        }

        mod.agus.jcoderz.dx.rop.code.RegisterSpec resultReg = insn.getResult();

        if (resultReg != null) {
            int reg = resultReg.getReg();

            if (definitionList[reg] != null) {
                throw new RuntimeException("Duplicate add of insn");
            } else {
                definitionList[resultReg.getReg()] = insn;
            }
        }
    }

    public List<mod.agus.jcoderz.dx.ssa.SsaInsn> getUseListForRegister(int reg) {

        if (unmodifiableUseList == null) {
            buildUseList();
        }

        return unmodifiableUseList[reg];
    }

    public ArrayList<mod.agus.jcoderz.dx.ssa.SsaInsn>[] getUseListCopy() {
        if (useList == null) {
            buildUseList();
        }

        ArrayList<mod.agus.jcoderz.dx.ssa.SsaInsn>[] useListCopy
                = (ArrayList<mod.agus.jcoderz.dx.ssa.SsaInsn>[])(new ArrayList[registerCount]);

        for (int i = 0; i < registerCount; i++) {
            useListCopy[i] = (ArrayList<mod.agus.jcoderz.dx.ssa.SsaInsn>)(new ArrayList(useList[i]));
        }

        return useListCopy;
    }

    public boolean isRegALocal(RegisterSpec spec) {
        mod.agus.jcoderz.dx.ssa.SsaInsn defn = getDefinitionForRegister(spec.getReg());

        if (defn == null) {

            return false;
        }


        if (defn.getLocalAssignment() != null) return true;


        for (mod.agus.jcoderz.dx.ssa.SsaInsn use : getUseListForRegister(spec.getReg())) {
            mod.agus.jcoderz.dx.rop.code.Insn insn = use.getOriginalRopInsn();

            if (insn != null
                    && insn.getOpcode().getOpcode() == RegOps.MARK_LOCAL) {
                return true;
            }
        }

        return false;
    }

     void setNewRegCount(int newRegCount) {
        registerCount = newRegCount;
        spareRegisterBase = registerCount;
        onInsnsChanged();
    }

    public int makeNewSsaReg() {
        int reg = registerCount++;
        spareRegisterBase = registerCount;
        onInsnsChanged();
        return reg;
    }

    public void forEachInsn(mod.agus.jcoderz.dx.ssa.SsaInsn.Visitor visitor) {
        for (mod.agus.jcoderz.dx.ssa.SsaBasicBlock block : blocks) {
            block.forEachInsn(visitor);
        }
    }

    public void forEachPhiInsn(PhiInsn.Visitor v) {
        for (mod.agus.jcoderz.dx.ssa.SsaBasicBlock block : blocks) {
            block.forEachPhiInsn(v);
        }
    }


    public void forEachBlockDepthFirst(boolean reverse,
            mod.agus.jcoderz.dx.ssa.SsaBasicBlock.Visitor v) {
        BitSet visited = new BitSet(blocks.size());


        Stack<mod.agus.jcoderz.dx.ssa.SsaBasicBlock> stack = new Stack<mod.agus.jcoderz.dx.ssa.SsaBasicBlock>();

        mod.agus.jcoderz.dx.ssa.SsaBasicBlock rootBlock = reverse ? getExitBlock() : getEntryBlock();

        if (rootBlock == null) {

            return;
        }

        stack.add(null);
        stack.add(rootBlock);

        while (stack.size() > 0) {
            mod.agus.jcoderz.dx.ssa.SsaBasicBlock cur = stack.pop();
            mod.agus.jcoderz.dx.ssa.SsaBasicBlock parent = stack.pop();

            if (!visited.get(cur.getIndex())) {
                BitSet children
                    = reverse ? cur.getPredecessors() : cur.getSuccessors();
                for (int i = children.nextSetBit(0); i >= 0
                        ; i = children.nextSetBit(i + 1)) {
                    stack.add(cur);
                    stack.add(blocks.get(i));
                }
                visited.set(cur.getIndex());
                v.visitBlock(cur, parent);
            }
        }
    }

    public void forEachBlockDepthFirstDom(mod.agus.jcoderz.dx.ssa.SsaBasicBlock.Visitor v) {
        BitSet visited = new BitSet(getBlocks().size());
        Stack<mod.agus.jcoderz.dx.ssa.SsaBasicBlock> stack = new Stack<mod.agus.jcoderz.dx.ssa.SsaBasicBlock>();

        stack.add(getEntryBlock());

        while (stack.size() > 0) {
            mod.agus.jcoderz.dx.ssa.SsaBasicBlock cur = stack.pop();
            ArrayList<mod.agus.jcoderz.dx.ssa.SsaBasicBlock> curDomChildren = cur.getDomChildren();

            if (!visited.get(cur.getIndex())) {

                for (int i = curDomChildren.size() - 1; i >= 0; i--) {
                    mod.agus.jcoderz.dx.ssa.SsaBasicBlock child = curDomChildren.get(i);
                    stack.add(child);
                }
                visited.set(cur.getIndex());
                v.visitBlock(cur, null);
            }
        }
    }

    public void deleteInsns(Set<mod.agus.jcoderz.dx.ssa.SsaInsn> deletedInsns) {
        for (mod.agus.jcoderz.dx.ssa.SsaInsn deletedInsn : deletedInsns) {
            SsaBasicBlock block = deletedInsn.getBlock();
            ArrayList<mod.agus.jcoderz.dx.ssa.SsaInsn> insns = block.getInsns();

            for (int i = insns.size() - 1; i >= 0; i--) {
                mod.agus.jcoderz.dx.ssa.SsaInsn insn = insns.get(i);

                if (deletedInsn == insn) {
                    onInsnRemoved(insn);
                    insns.remove(i);
                    break;
                }
            }



            int insnsSz = insns.size();
            mod.agus.jcoderz.dx.ssa.SsaInsn lastInsn = (insnsSz == 0) ? null : insns.get(insnsSz - 1);

            if (block != getExitBlock() && (insnsSz == 0
                    || lastInsn.getOriginalRopInsn() == null
                    || lastInsn.getOriginalRopInsn().getOpcode()
                        .getBranchingness() == Rop.BRANCH_NONE)) {


                Insn gotoInsn = new PlainInsn(Rops.GOTO,
                        SourcePosition.NO_INFO, null, RegisterSpecList.EMPTY);
                insns.add(SsaInsn.makeFromRop(gotoInsn, block));


                BitSet succs = block.getSuccessors();
                for (int i = succs.nextSetBit(0); i >= 0;
                         i = succs.nextSetBit(i + 1)) {
                    if (i != block.getPrimarySuccessorIndex()) {
                        block.removeSuccessor(i);
                    }
                }
            }
        }
    }

    public void setBackMode() {
        backMode = true;
        useList = null;
        definitionList = null;
    }
}
