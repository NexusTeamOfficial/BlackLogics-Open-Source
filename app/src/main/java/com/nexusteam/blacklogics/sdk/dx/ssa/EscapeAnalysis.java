
package mod.agus.jcoderz.dx.ssa;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;

import mod.agus.jcoderz.dx.rop.code.Exceptions;
import mod.agus.jcoderz.dx.rop.code.FillArrayDataInsn;
import mod.agus.jcoderz.dx.rop.code.Insn;
import mod.agus.jcoderz.dx.rop.code.PlainCstInsn;
import mod.agus.jcoderz.dx.rop.code.PlainInsn;
import mod.agus.jcoderz.dx.rop.code.RegOps;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.Rop;
import mod.agus.jcoderz.dx.rop.code.Rops;
import mod.agus.jcoderz.dx.rop.code.ThrowingCstInsn;
import mod.agus.jcoderz.dx.rop.code.ThrowingInsn;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstLiteralBits;
import mod.agus.jcoderz.dx.rop.cst.CstMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstNat;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.cst.TypedConstant;
import mod.agus.jcoderz.dx.rop.cst.Zeroes;
import mod.agus.jcoderz.dx.rop.type.StdTypeList;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeBearer;

public class EscapeAnalysis {
    static class EscapeSet {
        BitSet regSet;
        EscapeState escape;
        ArrayList<EscapeSet> childSets;
        ArrayList<EscapeSet> parentSets;
        boolean replaceableArray;

        EscapeSet(int reg, int size, EscapeState escState) {
            regSet = new BitSet(size);
            regSet.set(reg);
            escape = escState;
            childSets = new ArrayList<EscapeSet>();
            parentSets = new ArrayList<EscapeSet>();
            replaceableArray = false;
        }
    }

    public enum EscapeState {
        TOP, NONE, METHOD, INTER, GLOBAL
    }

    private final mod.agus.jcoderz.dx.ssa.SsaMethod ssaMeth;
    private final int regCount;
    private final ArrayList<EscapeSet> latticeValues;

    private EscapeAnalysis(mod.agus.jcoderz.dx.ssa.SsaMethod ssaMeth) {
        this.ssaMeth = ssaMeth;
        this.regCount = ssaMeth.getRegCount();
        this.latticeValues = new ArrayList<EscapeSet>();
    }

    private int findSetIndex(mod.agus.jcoderz.dx.rop.code.RegisterSpec reg) {
        int i;
        for (i = 0; i < latticeValues.size(); i++) {
            EscapeSet e = latticeValues.get(i);
            if (e.regSet.get(reg.getReg())) {
                return i;
            }
        }
        return i;
    }

    private mod.agus.jcoderz.dx.ssa.SsaInsn getInsnForMove(mod.agus.jcoderz.dx.ssa.SsaInsn moveInsn) {
        int pred = moveInsn.getBlock().getPredecessors().nextSetBit(0);
        ArrayList<mod.agus.jcoderz.dx.ssa.SsaInsn> predInsns = ssaMeth.getBlocks().get(pred).getInsns();
        return predInsns.get(predInsns.size()-1);
    }

    private mod.agus.jcoderz.dx.ssa.SsaInsn getMoveForInsn(mod.agus.jcoderz.dx.ssa.SsaInsn insn) {
        int succ = insn.getBlock().getSuccessors().nextSetBit(0);
        ArrayList<mod.agus.jcoderz.dx.ssa.SsaInsn> succInsns = ssaMeth.getBlocks().get(succ).getInsns();
        return succInsns.get(0);
    }

    private void addEdge(EscapeSet parentSet, EscapeSet childSet) {
        if (!childSet.parentSets.contains(parentSet)) {
            childSet.parentSets.add(parentSet);
        }
        if (!parentSet.childSets.contains(childSet)) {
            parentSet.childSets.add(childSet);
        }
    }

    private void replaceNode(EscapeSet newNode, EscapeSet oldNode) {
        for (EscapeSet e : oldNode.parentSets) {
            e.childSets.remove(oldNode);
            e.childSets.add(newNode);
            newNode.parentSets.add(e);
        }
        for (EscapeSet e : oldNode.childSets) {
            e.parentSets.remove(oldNode);
            e.parentSets.add(newNode);
            newNode.childSets.add(e);
        }
    }

    public static void process(SsaMethod ssaMethod) {
        new EscapeAnalysis(ssaMethod).run();
    }

    private void processInsn(mod.agus.jcoderz.dx.ssa.SsaInsn insn) {
        int op = insn.getOpcode().getOpcode();
        mod.agus.jcoderz.dx.rop.code.RegisterSpec result = insn.getResult();
        EscapeSet escSet;


        if (op == mod.agus.jcoderz.dx.rop.code.RegOps.MOVE_RESULT_PSEUDO &&
                result.getTypeBearer().getBasicType() == mod.agus.jcoderz.dx.rop.type.Type.BT_OBJECT) {

            escSet = processMoveResultPseudoInsn(insn);
            processRegister(result, escSet);
        } else if (op == mod.agus.jcoderz.dx.rop.code.RegOps.MOVE_PARAM &&
                      result.getTypeBearer().getBasicType() == mod.agus.jcoderz.dx.rop.type.Type.BT_OBJECT) {

            escSet = new EscapeSet(result.getReg(), regCount, EscapeState.NONE);
            latticeValues.add(escSet);
            processRegister(result, escSet);
        } else if (op == mod.agus.jcoderz.dx.rop.code.RegOps.MOVE_RESULT &&
                result.getTypeBearer().getBasicType() == mod.agus.jcoderz.dx.rop.type.Type.BT_OBJECT) {

            escSet = new EscapeSet(result.getReg(), regCount, EscapeState.NONE);
            latticeValues.add(escSet);
            processRegister(result, escSet);
        }
    }

    private EscapeSet processMoveResultPseudoInsn(mod.agus.jcoderz.dx.ssa.SsaInsn insn) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpec result = insn.getResult();
        mod.agus.jcoderz.dx.ssa.SsaInsn prevSsaInsn = getInsnForMove(insn);
        int prevOpcode = prevSsaInsn.getOpcode().getOpcode();
        EscapeSet escSet;
        mod.agus.jcoderz.dx.rop.code.RegisterSpec prevSource;

        switch(prevOpcode) {

            case mod.agus.jcoderz.dx.rop.code.RegOps.NEW_INSTANCE:
            case mod.agus.jcoderz.dx.rop.code.RegOps.CONST:
                escSet = new EscapeSet(result.getReg(), regCount,
                                           EscapeState.NONE);
                break;

            case mod.agus.jcoderz.dx.rop.code.RegOps.NEW_ARRAY:
            case mod.agus.jcoderz.dx.rop.code.RegOps.FILLED_NEW_ARRAY:
                prevSource = prevSsaInsn.getSources().get(0);
                if (prevSource.getTypeBearer().isConstant()) {

                    escSet = new EscapeSet(result.getReg(), regCount,
                                               EscapeState.NONE);
                    escSet.replaceableArray = true;
                } else {

                    escSet = new EscapeSet(result.getReg(), regCount,
                                               EscapeState.GLOBAL);
                }
                break;

            case mod.agus.jcoderz.dx.rop.code.RegOps.GET_STATIC:
                escSet = new EscapeSet(result.getReg(), regCount,
                                           EscapeState.GLOBAL);
                break;

            case mod.agus.jcoderz.dx.rop.code.RegOps.CHECK_CAST:
            case mod.agus.jcoderz.dx.rop.code.RegOps.GET_FIELD:
            case mod.agus.jcoderz.dx.rop.code.RegOps.AGET:
                prevSource = prevSsaInsn.getSources().get(0);
                int setIndex = findSetIndex(prevSource);


                if (setIndex != latticeValues.size()) {
                    escSet = latticeValues.get(setIndex);
                    escSet.regSet.set(result.getReg());
                    return escSet;
                }


                if (prevSource.getType() == mod.agus.jcoderz.dx.rop.type.Type.KNOWN_NULL) {
                    escSet = new EscapeSet(result.getReg(), regCount,
                                               EscapeState.NONE);
               } else {
                    escSet = new EscapeSet(result.getReg(), regCount,
                                               EscapeState.GLOBAL);
                }
                break;
            default:
                return null;
        }


        latticeValues.add(escSet);
        return escSet;
    }

    private void processRegister(mod.agus.jcoderz.dx.rop.code.RegisterSpec result, EscapeSet escSet) {
        ArrayList<mod.agus.jcoderz.dx.rop.code.RegisterSpec> regWorklist = new ArrayList<mod.agus.jcoderz.dx.rop.code.RegisterSpec>();
        regWorklist.add(result);


        while (!regWorklist.isEmpty()) {
            int listSize = regWorklist.size() - 1;
            mod.agus.jcoderz.dx.rop.code.RegisterSpec def = regWorklist.remove(listSize);
            List<mod.agus.jcoderz.dx.ssa.SsaInsn> useList = ssaMeth.getUseListForRegister(def.getReg());


            for (mod.agus.jcoderz.dx.ssa.SsaInsn use : useList) {
                mod.agus.jcoderz.dx.rop.code.Rop useOpcode = use.getOpcode();

                if (useOpcode == null) {

                    processPhiUse(use, escSet, regWorklist);
                } else {

                    processUse(def, use, escSet, regWorklist);
                }
            }
        }
    }

    private void processPhiUse(mod.agus.jcoderz.dx.ssa.SsaInsn use, EscapeSet escSet,
                               ArrayList<mod.agus.jcoderz.dx.rop.code.RegisterSpec> regWorklist) {
        int setIndex = findSetIndex(use.getResult());
        if (setIndex != latticeValues.size()) {

            EscapeSet mergeSet = latticeValues.get(setIndex);
            if (mergeSet != escSet) {

                escSet.replaceableArray = false;
                escSet.regSet.or(mergeSet.regSet);
                if (escSet.escape.compareTo(mergeSet.escape) < 0) {
                    escSet.escape = mergeSet.escape;
                }
                replaceNode(escSet, mergeSet);
                latticeValues.remove(setIndex);
            }
        } else {

            escSet.regSet.set(use.getResult().getReg());
            regWorklist.add(use.getResult());
        }
    }

    private void processUse(mod.agus.jcoderz.dx.rop.code.RegisterSpec def, mod.agus.jcoderz.dx.ssa.SsaInsn use, EscapeSet escSet,
                            ArrayList<mod.agus.jcoderz.dx.rop.code.RegisterSpec> regWorklist) {
        int useOpcode = use.getOpcode().getOpcode();
        switch (useOpcode) {
            case mod.agus.jcoderz.dx.rop.code.RegOps.MOVE:

                escSet.regSet.set(use.getResult().getReg());
                regWorklist.add(use.getResult());
                break;
            case mod.agus.jcoderz.dx.rop.code.RegOps.IF_EQ:
            case mod.agus.jcoderz.dx.rop.code.RegOps.IF_NE:
            case mod.agus.jcoderz.dx.rop.code.RegOps.CHECK_CAST:

                if (escSet.escape.compareTo(EscapeState.METHOD) < 0) {
                    escSet.escape = EscapeState.METHOD;
                }
                break;
            case mod.agus.jcoderz.dx.rop.code.RegOps.APUT:

                mod.agus.jcoderz.dx.rop.code.RegisterSpec putIndex = use.getSources().get(2);
                if (!putIndex.getTypeBearer().isConstant()) {

                    escSet.replaceableArray = false;
                }

            case mod.agus.jcoderz.dx.rop.code.RegOps.PUT_FIELD:

                mod.agus.jcoderz.dx.rop.code.RegisterSpec putValue = use.getSources().get(0);
                if (putValue.getTypeBearer().getBasicType() != mod.agus.jcoderz.dx.rop.type.Type.BT_OBJECT) {
                    break;
                }
                escSet.replaceableArray = false;


                mod.agus.jcoderz.dx.rop.code.RegisterSpecList sources = use.getSources();
                if (sources.get(0).getReg() == def.getReg()) {
                    int setIndex = findSetIndex(sources.get(1));
                    if (setIndex != latticeValues.size()) {
                        EscapeSet parentSet = latticeValues.get(setIndex);
                        addEdge(parentSet, escSet);
                        if (escSet.escape.compareTo(parentSet.escape) < 0) {
                            escSet.escape = parentSet.escape;
                        }
                    }
                } else {
                    int setIndex = findSetIndex(sources.get(0));
                    if (setIndex != latticeValues.size()) {
                        EscapeSet childSet = latticeValues.get(setIndex);
                        addEdge(escSet, childSet);
                        if (childSet.escape.compareTo(escSet.escape) < 0) {
                            childSet.escape = escSet.escape;
                        }
                    }
                }
                break;
            case mod.agus.jcoderz.dx.rop.code.RegOps.AGET:

                mod.agus.jcoderz.dx.rop.code.RegisterSpec getIndex = use.getSources().get(1);
                if (!getIndex.getTypeBearer().isConstant()) {

                    escSet.replaceableArray = false;
                }
                break;
            case mod.agus.jcoderz.dx.rop.code.RegOps.PUT_STATIC:

                escSet.escape = EscapeState.GLOBAL;
                break;
            case mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_STATIC:
            case mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_VIRTUAL:
            case mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_SUPER:
            case mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_DIRECT:
            case mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_INTERFACE:
            case mod.agus.jcoderz.dx.rop.code.RegOps.RETURN:
            case mod.agus.jcoderz.dx.rop.code.RegOps.THROW:

                escSet.escape = EscapeState.INTER;
                break;
            default:
                break;
        }
    }

    private void scalarReplacement() {

        for (EscapeSet escSet : latticeValues) {
            if (!escSet.replaceableArray || escSet.escape != EscapeState.NONE) {
                continue;
            }


            int e = escSet.regSet.nextSetBit(0);
            mod.agus.jcoderz.dx.ssa.SsaInsn def = ssaMeth.getDefinitionForRegister(e);
            mod.agus.jcoderz.dx.ssa.SsaInsn prev = getInsnForMove(def);


            mod.agus.jcoderz.dx.rop.type.TypeBearer lengthReg = prev.getSources().get(0).getTypeBearer();
            int length = ((mod.agus.jcoderz.dx.rop.cst.CstLiteralBits) lengthReg).getIntBits();
            ArrayList<mod.agus.jcoderz.dx.rop.code.RegisterSpec> newRegs =
                new ArrayList<mod.agus.jcoderz.dx.rop.code.RegisterSpec>(length);
            HashSet<mod.agus.jcoderz.dx.ssa.SsaInsn> deletedInsns = new HashSet<mod.agus.jcoderz.dx.ssa.SsaInsn>();


            replaceDef(def, prev, length, newRegs);


            deletedInsns.add(prev);
            deletedInsns.add(def);


            List<mod.agus.jcoderz.dx.ssa.SsaInsn> useList = ssaMeth.getUseListForRegister(e);
            for (mod.agus.jcoderz.dx.ssa.SsaInsn use : useList) {

                replaceUse(use, prev, newRegs, deletedInsns);
                deletedInsns.add(use);
            }


            ssaMeth.deleteInsns(deletedInsns);
            ssaMeth.onInsnsChanged();


            SsaConverter.updateSsaMethod(ssaMeth, regCount);


            movePropagate();
        }
    }

    private void replaceDef(mod.agus.jcoderz.dx.ssa.SsaInsn def, mod.agus.jcoderz.dx.ssa.SsaInsn prev, int length,
                            ArrayList<mod.agus.jcoderz.dx.rop.code.RegisterSpec> newRegs) {
        Type resultType = def.getResult().getType();


        for (int i = 0; i < length; i++) {
            mod.agus.jcoderz.dx.rop.cst.Constant newZero = Zeroes.zeroFor(resultType.getComponentType());
            mod.agus.jcoderz.dx.rop.cst.TypedConstant typedZero = (TypedConstant) newZero;
            mod.agus.jcoderz.dx.rop.code.RegisterSpec newReg =
                mod.agus.jcoderz.dx.rop.code.RegisterSpec.make(ssaMeth.makeNewSsaReg(), typedZero);
            newRegs.add(newReg);
            insertPlainInsnBefore(def, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY, newReg,
                                      mod.agus.jcoderz.dx.rop.code.RegOps.CONST, newZero);
        }
    }

    private void replaceUse(mod.agus.jcoderz.dx.ssa.SsaInsn use, mod.agus.jcoderz.dx.ssa.SsaInsn prev,
                            ArrayList<mod.agus.jcoderz.dx.rop.code.RegisterSpec> newRegs,
                            HashSet<mod.agus.jcoderz.dx.ssa.SsaInsn> deletedInsns) {
        int index;
        int length = newRegs.size();
        mod.agus.jcoderz.dx.ssa.SsaInsn next;
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList sources;
        mod.agus.jcoderz.dx.rop.code.RegisterSpec source, result;
        mod.agus.jcoderz.dx.rop.cst.CstLiteralBits indexReg;

        switch (use.getOpcode().getOpcode()) {
            case mod.agus.jcoderz.dx.rop.code.RegOps.AGET:

                next = getMoveForInsn(use);
                sources = use.getSources();
                indexReg = ((mod.agus.jcoderz.dx.rop.cst.CstLiteralBits) sources.get(1).getTypeBearer());
                index = indexReg.getIntBits();
                if (index < length) {
                    source = newRegs.get(index);
                    result = source.withReg(next.getResult().getReg());
                    insertPlainInsnBefore(next, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.make(source),
                                              result, mod.agus.jcoderz.dx.rop.code.RegOps.MOVE, null);
                } else {

                    insertExceptionThrow(next, sources.get(1), deletedInsns);
                    deletedInsns.add(next.getBlock().getInsns().get(2));
                }
                deletedInsns.add(next);
                break;
            case mod.agus.jcoderz.dx.rop.code.RegOps.APUT:

                sources = use.getSources();
                indexReg = ((CstLiteralBits) sources.get(2).getTypeBearer());
                index = indexReg.getIntBits();
                if (index < length) {
                    source = sources.get(0);
                    result = source.withReg(newRegs.get(index).getReg());
                    insertPlainInsnBefore(use, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.make(source),
                                              result, mod.agus.jcoderz.dx.rop.code.RegOps.MOVE, null);

                    newRegs.set(index, result.withSimpleType());
                } else {

                    insertExceptionThrow(use, sources.get(2), deletedInsns);
                }
                break;
            case mod.agus.jcoderz.dx.rop.code.RegOps.ARRAY_LENGTH:

                mod.agus.jcoderz.dx.rop.type.TypeBearer lengthReg = prev.getSources().get(0).getTypeBearer();

                next = getMoveForInsn(use);
                insertPlainInsnBefore(next, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY,
                                          next.getResult(), mod.agus.jcoderz.dx.rop.code.RegOps.CONST,
                                          (mod.agus.jcoderz.dx.rop.cst.Constant) lengthReg);
                deletedInsns.add(next);
                break;
            case mod.agus.jcoderz.dx.rop.code.RegOps.MARK_LOCAL:

                break;
            case mod.agus.jcoderz.dx.rop.code.RegOps.FILL_ARRAY_DATA:

                mod.agus.jcoderz.dx.rop.code.Insn ropUse = use.getOriginalRopInsn();
                mod.agus.jcoderz.dx.rop.code.FillArrayDataInsn fill = (FillArrayDataInsn) ropUse;
                ArrayList<mod.agus.jcoderz.dx.rop.cst.Constant> constList = fill.getInitValues();
                for (int i = 0; i < length; i++) {
                    mod.agus.jcoderz.dx.rop.code.RegisterSpec newFill =
                        mod.agus.jcoderz.dx.rop.code.RegisterSpec.make(newRegs.get(i).getReg(),
                                              (TypeBearer) constList.get(i));
                    insertPlainInsnBefore(use, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY, newFill,
                                              mod.agus.jcoderz.dx.rop.code.RegOps.CONST, constList.get(i));

                    newRegs.set(i, newFill);
                }
                break;
            default:
        }
    }

    private void movePropagate() {
        for (int i = 0; i < ssaMeth.getRegCount(); i++) {
            mod.agus.jcoderz.dx.ssa.SsaInsn insn = ssaMeth.getDefinitionForRegister(i);


            if (insn == null || insn.getOpcode() == null ||
                insn.getOpcode().getOpcode() != mod.agus.jcoderz.dx.rop.code.RegOps.MOVE) {
                continue;
            }

            final ArrayList<mod.agus.jcoderz.dx.ssa.SsaInsn>[] useList = ssaMeth.getUseListCopy();
            final mod.agus.jcoderz.dx.rop.code.RegisterSpec source = insn.getSources().get(0);
            final mod.agus.jcoderz.dx.rop.code.RegisterSpec result = insn.getResult();


            if (source.getReg() < regCount && result.getReg() < regCount) {
                continue;
            }


            mod.agus.jcoderz.dx.ssa.RegisterMapper mapper = new RegisterMapper() {
                @Override
                public int getNewRegisterCount() {
                    return ssaMeth.getRegCount();
                }

                @Override
                public mod.agus.jcoderz.dx.rop.code.RegisterSpec map(mod.agus.jcoderz.dx.rop.code.RegisterSpec registerSpec) {
                    if (registerSpec.getReg() == result.getReg()) {
                        return source;
                    }

                    return registerSpec;
                }
            };


            for (mod.agus.jcoderz.dx.ssa.SsaInsn use : useList[result.getReg()]) {
                use.mapSourceRegisters(mapper);
            }
        }
    }

    private void run() {
        ssaMeth.forEachBlockDepthFirstDom(new mod.agus.jcoderz.dx.ssa.SsaBasicBlock.Visitor() {
            @Override
            public void visitBlock (mod.agus.jcoderz.dx.ssa.SsaBasicBlock block,
                                    mod.agus.jcoderz.dx.ssa.SsaBasicBlock unused) {
                block.forEachInsn(new mod.agus.jcoderz.dx.ssa.SsaInsn.Visitor() {
                    @Override
                    public void visitMoveInsn(NormalSsaInsn insn) {

                    }

                    @Override
                    public void visitPhiInsn(PhiInsn insn) {

                    }

                    @Override
                    public void visitNonMoveInsn(NormalSsaInsn insn) {
                        processInsn(insn);
                    }
                });
            }
        });


        for (EscapeSet e : latticeValues) {
            if (e.escape != EscapeState.NONE) {
                for (EscapeSet field : e.childSets) {
                    if (e.escape.compareTo(field.escape) > 0) {
                        field.escape = e.escape;
                    }
                }
            }
        }


        scalarReplacement();
    }

    private void insertExceptionThrow(mod.agus.jcoderz.dx.ssa.SsaInsn insn, mod.agus.jcoderz.dx.rop.code.RegisterSpec index,
                                      HashSet<mod.agus.jcoderz.dx.ssa.SsaInsn> deletedInsns) {

        mod.agus.jcoderz.dx.rop.cst.CstType exception =
            new CstType(Exceptions.TYPE_ArrayIndexOutOfBoundsException);
        insertThrowingInsnBefore(insn, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY, null,
                                     mod.agus.jcoderz.dx.rop.code.RegOps.NEW_INSTANCE, exception);


        mod.agus.jcoderz.dx.ssa.SsaBasicBlock currBlock = insn.getBlock();
        mod.agus.jcoderz.dx.ssa.SsaBasicBlock newBlock =
            currBlock.insertNewSuccessor(currBlock.getPrimarySuccessor());
        mod.agus.jcoderz.dx.ssa.SsaInsn newInsn = newBlock.getInsns().get(0);
        mod.agus.jcoderz.dx.rop.code.RegisterSpec newReg =
            mod.agus.jcoderz.dx.rop.code.RegisterSpec.make(ssaMeth.makeNewSsaReg(), exception);
        insertPlainInsnBefore(newInsn, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY, newReg,
                                  mod.agus.jcoderz.dx.rop.code.RegOps.MOVE_RESULT_PSEUDO, null);


        mod.agus.jcoderz.dx.ssa.SsaBasicBlock newBlock2 =
            newBlock.insertNewSuccessor(newBlock.getPrimarySuccessor());
        mod.agus.jcoderz.dx.ssa.SsaInsn newInsn2 = newBlock2.getInsns().get(0);
        mod.agus.jcoderz.dx.rop.cst.CstNat newNat = new CstNat(new mod.agus.jcoderz.dx.rop.cst.CstString("<init>"), new CstString("(I)V"));
        mod.agus.jcoderz.dx.rop.cst.CstMethodRef newRef = new CstMethodRef(exception, newNat);
        insertThrowingInsnBefore(newInsn2, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.make(newReg, index),
                                     null, mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_DIRECT, newRef);
        deletedInsns.add(newInsn2);


        SsaBasicBlock newBlock3 =
            newBlock2.insertNewSuccessor(newBlock2.getPrimarySuccessor());
        mod.agus.jcoderz.dx.ssa.SsaInsn newInsn3 = newBlock3.getInsns().get(0);
        insertThrowingInsnBefore(newInsn3, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.make(newReg), null,
                                     mod.agus.jcoderz.dx.rop.code.RegOps.THROW, null);
        newBlock3.replaceSuccessor(newBlock3.getPrimarySuccessorIndex(),
                                       ssaMeth.getExitBlock().getIndex());
        deletedInsns.add(newInsn3);
    }

    private void insertPlainInsnBefore(mod.agus.jcoderz.dx.ssa.SsaInsn insn,
                                       mod.agus.jcoderz.dx.rop.code.RegisterSpecList newSources, mod.agus.jcoderz.dx.rop.code.RegisterSpec newResult, int newOpcode,
                                       mod.agus.jcoderz.dx.rop.cst.Constant cst) {

        mod.agus.jcoderz.dx.rop.code.Insn originalRopInsn = insn.getOriginalRopInsn();
        mod.agus.jcoderz.dx.rop.code.Rop newRop;
        if (newOpcode == RegOps.MOVE_RESULT_PSEUDO) {
            newRop = mod.agus.jcoderz.dx.rop.code.Rops.opMoveResultPseudo(newResult.getType());
        } else {
            newRop = mod.agus.jcoderz.dx.rop.code.Rops.ropFor(newOpcode, newResult, newSources, cst);
        }

        mod.agus.jcoderz.dx.rop.code.Insn newRopInsn;
        if (cst == null) {
            newRopInsn = new PlainInsn(newRop,
                    originalRopInsn.getPosition(), newResult, newSources);
        } else {
            newRopInsn = new PlainCstInsn(newRop,
                originalRopInsn.getPosition(), newResult, newSources, cst);
        }

        NormalSsaInsn newInsn = new NormalSsaInsn(newRopInsn, insn.getBlock());
        List<mod.agus.jcoderz.dx.ssa.SsaInsn> insns = insn.getBlock().getInsns();

        insns.add(insns.lastIndexOf(insn), newInsn);
        ssaMeth.onInsnAdded(newInsn);
    }

    private void insertThrowingInsnBefore(mod.agus.jcoderz.dx.ssa.SsaInsn insn,
                                          RegisterSpecList newSources, RegisterSpec newResult, int newOpcode,
                                          Constant cst) {

        mod.agus.jcoderz.dx.rop.code.Insn origRopInsn = insn.getOriginalRopInsn();
        Rop newRop = Rops.ropFor(newOpcode, newResult, newSources, cst);
        Insn newRopInsn;
        if (cst == null) {
            newRopInsn = new ThrowingInsn(newRop,
                origRopInsn.getPosition(), newSources, mod.agus.jcoderz.dx.rop.type.StdTypeList.EMPTY);
        } else {
            newRopInsn = new ThrowingCstInsn(newRop,
                origRopInsn.getPosition(), newSources, StdTypeList.EMPTY, cst);
        }

        NormalSsaInsn newInsn = new NormalSsaInsn(newRopInsn, insn.getBlock());
        List<SsaInsn> insns = insn.getBlock().getInsns();

        insns.add(insns.lastIndexOf(insn), newInsn);
        ssaMeth.onInsnAdded(newInsn);
    }
}
