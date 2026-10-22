
package mod.agus.jcoderz.dx.ssa;

import mod.agus.jcoderz.dx.util.Hex;
import mod.agus.jcoderz.dx.util.IntList;
import mod.agus.jcoderz.dx.util.IntSet;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mod.agus.jcoderz.dx.rop.code.BasicBlock;
import mod.agus.jcoderz.dx.rop.code.BasicBlockList;
import mod.agus.jcoderz.dx.rop.code.Insn;
import mod.agus.jcoderz.dx.rop.code.InsnList;
import mod.agus.jcoderz.dx.rop.code.PlainInsn;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.Rop;
import mod.agus.jcoderz.dx.rop.code.RopMethod;
import mod.agus.jcoderz.dx.rop.code.Rops;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;

public final class SsaBasicBlock {
    public static final Comparator<SsaBasicBlock> LABEL_COMPARATOR =
        new LabelComparator();

    private final ArrayList<SsaInsn> insns;

    private BitSet predecessors;

    private BitSet successors;

    private IntList successorList;

    private int primarySuccessor = -1;

    private final int ropLabel;

    private final SsaMethod parent;

    private final int index;

    private final ArrayList<SsaBasicBlock> domChildren;

    private int movesFromPhisAtEnd = 0;

    private int movesFromPhisAtBeginning = 0;

    private IntSet liveIn;

    private IntSet liveOut;

    public SsaBasicBlock(final int basicBlockIndex, final int ropLabel,
            final SsaMethod parent) {
        this.parent = parent;
        this.index = basicBlockIndex;
        this.insns = new ArrayList<SsaInsn>();
        this.ropLabel = ropLabel;

        this.predecessors = new BitSet(parent.getBlocks().size());
        this.successors = new BitSet(parent.getBlocks().size());
        this.successorList = new IntList();

        domChildren = new ArrayList<SsaBasicBlock>();
    }

    public static SsaBasicBlock newFromRop(RopMethod rmeth,
                                           int basicBlockIndex, final SsaMethod parent) {
        BasicBlockList ropBlocks = rmeth.getBlocks();
        BasicBlock bb = ropBlocks.get(basicBlockIndex);
        SsaBasicBlock result =
            new SsaBasicBlock(basicBlockIndex, bb.getLabel(), parent);
        InsnList ropInsns = bb.getInsns();

        result.insns.ensureCapacity(ropInsns.size());

        for (int i = 0, sz = ropInsns.size() ; i < sz ; i++) {
            result.insns.add(new NormalSsaInsn (ropInsns.get(i), result));
        }

        result.predecessors = SsaMethod.bitSetFromLabelList(
                ropBlocks,
                rmeth.labelToPredecessors(bb.getLabel()));

        result.successors
                = SsaMethod.bitSetFromLabelList(ropBlocks, bb.getSuccessors());

        result.successorList
                = SsaMethod.indexListFromLabelList(ropBlocks,
                    bb.getSuccessors());

        if (result.successorList.size() != 0) {
            int primarySuccessor = bb.getPrimarySuccessor();

            result.primarySuccessor = (primarySuccessor < 0)
                    ? -1 : ropBlocks.indexOfLabel(primarySuccessor);
        }

        return result;
    }

    public void addDomChild(SsaBasicBlock child) {
        domChildren.add(child);
    }

    public ArrayList<SsaBasicBlock> getDomChildren() {
        return domChildren;
    }

    public void addPhiInsnForReg(int reg) {
        insns.add(0, new PhiInsn(reg, this));
    }

    public void addPhiInsnForReg(mod.agus.jcoderz.dx.rop.code.RegisterSpec resultSpec) {
        insns.add(0, new PhiInsn(resultSpec, this));
    }

    public void addInsnToHead(mod.agus.jcoderz.dx.rop.code.Insn insn) {
        SsaInsn newInsn = SsaInsn.makeFromRop(insn, this);
        insns.add(getCountPhiInsns(), newInsn);
        parent.onInsnAdded(newInsn);
    }

    public void replaceLastInsn(Insn insn) {
        if (insn.getOpcode().getBranchingness() == Rop.BRANCH_NONE) {
            throw new IllegalArgumentException("last insn must branch");
        }

        SsaInsn oldInsn = insns.get(insns.size() - 1);
        SsaInsn newInsn = SsaInsn.makeFromRop(insn, this);

        insns.set(insns.size() - 1, newInsn);

        parent.onInsnRemoved(oldInsn);
        parent.onInsnAdded(newInsn);
    }

    public void forEachPhiInsn(PhiInsn.Visitor v) {
        int sz = insns.size();

        for (int i = 0; i < sz; i++) {
            SsaInsn insn = insns.get(i);
            if (insn instanceof PhiInsn) {
                v.visitPhiInsn((PhiInsn) insn);
            } else {
                break;
            }
        }
    }

    public void removeAllPhiInsns() {

        insns.subList(0, getCountPhiInsns()).clear();
    }

    private int getCountPhiInsns() {
        int countPhiInsns;

        int sz = insns.size();
        for (countPhiInsns = 0; countPhiInsns < sz; countPhiInsns++) {
            SsaInsn insn = insns.get(countPhiInsns);
            if (!(insn instanceof PhiInsn)) {
                break;
            }
        }

        return countPhiInsns;
    }

    public ArrayList<SsaInsn> getInsns() {
        return insns;
    }

    public List<SsaInsn> getPhiInsns() {
        return insns.subList(0, getCountPhiInsns());
    }

    public int getIndex() {
        return index;
    }

    public int getRopLabel() {
        return ropLabel;
    }

    public String getRopLabelString() {
        return Hex.u2(ropLabel);
    }

    public BitSet getPredecessors() {
        return predecessors;
    }

    public BitSet getSuccessors() {
        return successors;
    }

    public IntList getSuccessorList() {
        return successorList;
    }

    public int getPrimarySuccessorIndex() {
        return primarySuccessor;
    }

    public int getPrimarySuccessorRopLabel() {
        return parent.blockIndexToRopLabel(primarySuccessor);
    }

    public SsaBasicBlock getPrimarySuccessor() {
        if (primarySuccessor < 0) {
            return null;
        } else {
            return parent.getBlocks().get(primarySuccessor);
        }
    }

    public IntList getRopLabelSuccessorList() {
        IntList result = new IntList(successorList.size());

        int sz = successorList.size();

        for (int i = 0; i < sz; i++) {
            result.add(parent.blockIndexToRopLabel(successorList.get(i)));
        }
        return result;
    }

    public SsaMethod getParent() {
        return parent;
    }

    public SsaBasicBlock insertNewPredecessor() {
        SsaBasicBlock newPred = parent.makeNewGotoBlock();


        newPred.predecessors = predecessors;
        newPred.successors.set(index) ;
        newPred.successorList.add(index);
        newPred.primarySuccessor = index;



        predecessors = new BitSet(parent.getBlocks().size());
        predecessors.set(newPred.index);


        for (int i = newPred.predecessors.nextSetBit(0); i >= 0;
                i = newPred.predecessors.nextSetBit(i + 1)) {

            SsaBasicBlock predBlock = parent.getBlocks().get(i);

            predBlock.replaceSuccessor(index, newPred.index);
        }

        return newPred;
    }

    public SsaBasicBlock insertNewSuccessor(SsaBasicBlock other) {
        SsaBasicBlock newSucc = parent.makeNewGotoBlock();

        if (!successors.get(other.index)) {
            throw new RuntimeException("Block " + other.getRopLabelString()
                    + " not successor of " + getRopLabelString());
        }


        newSucc.predecessors.set(this.index);
        newSucc.successors.set(other.index) ;
        newSucc.successorList.add(other.index);
        newSucc.primarySuccessor = other.index;


        for (int i = successorList.size() - 1 ;  i >= 0; i--) {
            if (successorList.get(i) == other.index) {
                successorList.set(i, newSucc.index);
            }
        }

        if (primarySuccessor == other.index) {
            primarySuccessor = newSucc.index;
        }
        successors.clear(other.index);
        successors.set(newSucc.index);


        other.predecessors.set(newSucc.index);
        other.predecessors.set(index, successors.get(other.index));

        return newSucc;
    }

    public void replaceSuccessor(int oldIndex, int newIndex) {
        if (oldIndex == newIndex) {
            return;
        }


        successors.set(newIndex);

        if (primarySuccessor == oldIndex) {
            primarySuccessor = newIndex;
        }

        for (int i = successorList.size() - 1 ;  i >= 0; i--) {
            if (successorList.get(i) == oldIndex) {
                successorList.set(i, newIndex);
            }
        }

        successors.clear(oldIndex);


        parent.getBlocks().get(newIndex).predecessors.set(index);


        parent.getBlocks().get(oldIndex).predecessors.clear(index);
    }

    public void removeSuccessor(int oldIndex) {
        int removeIndex = 0;

        for (int i = successorList.size() - 1; i >= 0; i--) {
            if (successorList.get(i) == oldIndex) {
                removeIndex = i;
            } else {
                primarySuccessor = successorList.get(i);
            }
        }

        successorList.removeIndex(removeIndex);
        successors.clear(oldIndex);
        parent.getBlocks().get(oldIndex).predecessors.clear(index);
    }

    public void exitBlockFixup(SsaBasicBlock exitBlock) {
        if (this == exitBlock) {
            return;
        }

        if (successorList.size() == 0) {
            successors.set(exitBlock.index);
            successorList.add(exitBlock.index);
            primarySuccessor = exitBlock.index;
            exitBlock.predecessors.set(this.index);
        }
    }

    public void addMoveToEnd(mod.agus.jcoderz.dx.rop.code.RegisterSpec result, mod.agus.jcoderz.dx.rop.code.RegisterSpec source) {
        if (successors.cardinality() > 1) {
            throw new IllegalStateException("Inserting a move to a block with multiple successors");
        }

        if (result.getReg() == source.getReg()) {

            return;
        }

        NormalSsaInsn lastInsn;
        lastInsn = (NormalSsaInsn)insns.get(insns.size()-1);

        if (lastInsn.getResult() != null || lastInsn.getSources().size() > 0) {

            for (int i = successors.nextSetBit(0)
                    ; i >= 0
                    ; i = successors.nextSetBit(i + 1)) {

                SsaBasicBlock succ;

                succ = parent.getBlocks().get(i);
                succ.addMoveToBeginning(result, source);
            }
        } else {
            mod.agus.jcoderz.dx.rop.code.RegisterSpecList sources = mod.agus.jcoderz.dx.rop.code.RegisterSpecList.make(source);
            NormalSsaInsn toAdd = new NormalSsaInsn(
                    new mod.agus.jcoderz.dx.rop.code.PlainInsn(mod.agus.jcoderz.dx.rop.code.Rops.opMove(result.getType()),
                            mod.agus.jcoderz.dx.rop.code.SourcePosition.NO_INFO, result, sources), this);

            insns.add(insns.size() - 1, toAdd);

            movesFromPhisAtEnd++;
        }
    }

    public void addMoveToBeginning (mod.agus.jcoderz.dx.rop.code.RegisterSpec result, mod.agus.jcoderz.dx.rop.code.RegisterSpec source) {
        if (result.getReg() == source.getReg()) {

            return;
        }

        mod.agus.jcoderz.dx.rop.code.RegisterSpecList sources = mod.agus.jcoderz.dx.rop.code.RegisterSpecList.make(source);
        NormalSsaInsn toAdd = new NormalSsaInsn(
                new mod.agus.jcoderz.dx.rop.code.PlainInsn(mod.agus.jcoderz.dx.rop.code.Rops.opMove(result.getType()),
                        mod.agus.jcoderz.dx.rop.code.SourcePosition.NO_INFO, result, sources), this);

        insns.add(getCountPhiInsns(), toAdd);
        movesFromPhisAtBeginning++;
    }

    private static void setRegsUsed (BitSet regsUsed, mod.agus.jcoderz.dx.rop.code.RegisterSpec rs) {
        regsUsed.set(rs.getReg());
        if (rs.getCategory() > 1) {
            regsUsed.set(rs.getReg() + 1);
        }
    }

    private static boolean checkRegUsed (BitSet regsUsed, mod.agus.jcoderz.dx.rop.code.RegisterSpec rs) {
        int reg = rs.getReg();
        int category = rs.getCategory();

        return regsUsed.get(reg)
                || (category == 2 ? regsUsed.get(reg + 1) : false);
    }

    private void scheduleUseBeforeAssigned(List<SsaInsn> toSchedule) {
        BitSet regsUsedAsSources = new BitSet(parent.getRegCount());


        BitSet regsUsedAsResults = new BitSet(parent.getRegCount());

        int sz = toSchedule.size();

        int insertPlace = 0;

        while (insertPlace < sz) {
            int oldInsertPlace = insertPlace;


            for (int i = insertPlace; i < sz; i++) {
                setRegsUsed(regsUsedAsSources,
                        toSchedule.get(i).getSources().get(0));

                setRegsUsed(regsUsedAsResults,
                        toSchedule.get(i).getResult());
            }

            for (int i = insertPlace; i <sz; i++) {
                SsaInsn insn = toSchedule.get(i);

                if (!checkRegUsed(regsUsedAsSources, insn.getResult())) {
                    Collections.swap(toSchedule, i, insertPlace++);
                }
            }

            if (oldInsertPlace == insertPlace) {

                SsaInsn insnToSplit = null;


                for (int i = insertPlace; i < sz; i++) {
                    SsaInsn insn = toSchedule.get(i);
                    if (checkRegUsed(regsUsedAsSources, insn.getResult())
                            && checkRegUsed(regsUsedAsResults,
                                insn.getSources().get(0))) {

                        insnToSplit = insn;
                        Collections.swap(toSchedule, insertPlace, i);
                        break;
                    }
                }



                mod.agus.jcoderz.dx.rop.code.RegisterSpec result = insnToSplit.getResult();
                mod.agus.jcoderz.dx.rop.code.RegisterSpec tempSpec = result.withReg(
                        parent.borrowSpareRegister(result.getCategory()));

                NormalSsaInsn toAdd = new NormalSsaInsn(
                        new mod.agus.jcoderz.dx.rop.code.PlainInsn(mod.agus.jcoderz.dx.rop.code.Rops.opMove(result.getType()),
                                mod.agus.jcoderz.dx.rop.code.SourcePosition.NO_INFO,
                                tempSpec,
                                insnToSplit.getSources()), this);

                toSchedule.add(insertPlace++, toAdd);

                mod.agus.jcoderz.dx.rop.code.RegisterSpecList newSources = mod.agus.jcoderz.dx.rop.code.RegisterSpecList.make(tempSpec);

                NormalSsaInsn toReplace = new NormalSsaInsn(
                        new mod.agus.jcoderz.dx.rop.code.PlainInsn(mod.agus.jcoderz.dx.rop.code.Rops.opMove(result.getType()),
                                mod.agus.jcoderz.dx.rop.code.SourcePosition.NO_INFO,
                                result,
                                newSources), this);

                toSchedule.set(insertPlace, toReplace);


                sz = toSchedule.size();
            }

            regsUsedAsSources.clear();
            regsUsedAsResults.clear();
        }
    }

    public void addLiveOut (int regV) {
        if (liveOut == null) {
            liveOut = SetFactory.makeLivenessSet(parent.getRegCount());
        }

        liveOut.add(regV);
    }

    public void addLiveIn (int regV) {
        if (liveIn == null) {
            liveIn = SetFactory.makeLivenessSet(parent.getRegCount());
        }

        liveIn.add(regV);
    }

    public IntSet getLiveInRegs() {
        if (liveIn == null) {
            liveIn = SetFactory.makeLivenessSet(parent.getRegCount());
        }
        return liveIn;
    }

    public IntSet getLiveOutRegs() {
        if (liveOut == null) {
            liveOut = SetFactory.makeLivenessSet(parent.getRegCount());
        }
        return liveOut;
    }

    public boolean isExitBlock() {
        return index == parent.getExitBlockIndex();
    }

    public void scheduleMovesFromPhis() {
        if (movesFromPhisAtBeginning > 1) {
            List<SsaInsn> toSchedule;

            toSchedule = insns.subList(0, movesFromPhisAtBeginning);

            scheduleUseBeforeAssigned(toSchedule);

            SsaInsn firstNonPhiMoveInsn = insns.get(movesFromPhisAtBeginning);

            if (firstNonPhiMoveInsn.isMoveException()) {
                if (true) {
                    throw new RuntimeException(
                            "Unexpected: moves from "
                                    +"phis before move-exception");
                } else {
                    boolean moveExceptionInterferes = false;

                    int moveExceptionResult
                            = firstNonPhiMoveInsn.getResult().getReg();

                    for (SsaInsn insn : toSchedule) {
                        if (insn.isResultReg(moveExceptionResult)
                                || insn.isRegASource(moveExceptionResult)) {
                            moveExceptionInterferes = true;
                            break;
                        }
                    }

                    if (!moveExceptionInterferes) {

                        insns.remove(movesFromPhisAtBeginning);
                        insns.add(0, firstNonPhiMoveInsn);
                    } else {
                        mod.agus.jcoderz.dx.rop.code.RegisterSpec originalResultSpec
                            = firstNonPhiMoveInsn.getResult();
                        int spareRegister = parent.borrowSpareRegister(
                                originalResultSpec.getCategory());


                        firstNonPhiMoveInsn.changeResultReg(spareRegister);
                        RegisterSpec tempSpec =
                            firstNonPhiMoveInsn.getResult();

                        insns.add(0, firstNonPhiMoveInsn);



                        NormalSsaInsn toAdd = new NormalSsaInsn(
                                new PlainInsn(
                                        Rops.opMove(tempSpec.getType()),
                                        SourcePosition.NO_INFO,
                                        originalResultSpec,
                                        RegisterSpecList.make(tempSpec)),
                                this);


                        insns.set(movesFromPhisAtBeginning + 1, toAdd);
                    }
                }
            }
        }

        if (movesFromPhisAtEnd > 1) {
            scheduleUseBeforeAssigned(
                    insns.subList(insns.size() - movesFromPhisAtEnd - 1,
                                insns.size() - 1));
        }


        parent.returnSpareRegisters();

    }

    public void forEachInsn(SsaInsn.Visitor visitor) {


        int len = insns.size();
        for (int i = 0; i < len; i++) {
            insns.get(i).accept(visitor);
        }
    }

    @Override
    public String toString() {
        return "{" + index + ":" + Hex.u2(ropLabel) + '}';
    }

    public interface Visitor {
        void visitBlock (SsaBasicBlock v, SsaBasicBlock parent);
    }

    public static final class LabelComparator
            implements Comparator<SsaBasicBlock> {
        @Override
        public int compare(SsaBasicBlock b1, SsaBasicBlock b2) {
            int label1 = b1.ropLabel;
            int label2 = b2.ropLabel;

            if (label1 < label2) {
                return -1;
            } else if (label1 > label2) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
