
package mod.agus.jcoderz.dx.ssa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import mod.agus.jcoderz.dx.rop.code.LocalItem;
import mod.agus.jcoderz.dx.rop.code.PlainCstInsn;
import mod.agus.jcoderz.dx.rop.code.PlainInsn;
import mod.agus.jcoderz.dx.rop.code.RegOps;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.Rop;
import mod.agus.jcoderz.dx.rop.code.Rops;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.rop.code.ThrowingCstInsn;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.TypedConstant;
import mod.agus.jcoderz.dx.rop.type.StdTypeList;
import mod.agus.jcoderz.dx.rop.type.TypeBearer;

public class ConstCollector {
    private static final int MAX_COLLECTED_CONSTANTS = 5;

    private static final boolean COLLECT_STRINGS = false;

    private static final boolean COLLECT_ONE_LOCAL = false;

    private final SsaMethod ssaMeth;

    public static void process(SsaMethod ssaMethod) {
        ConstCollector cc = new ConstCollector(ssaMethod);
        cc.run();
    }

    private ConstCollector(SsaMethod ssaMethod) {
        this.ssaMeth = ssaMethod;
    }

    private void run() {
        int regSz = ssaMeth.getRegCount();

        ArrayList<mod.agus.jcoderz.dx.rop.cst.TypedConstant> constantList
                = getConstsSortedByCountUse();

        int toCollect = Math.min(constantList.size(), MAX_COLLECTED_CONSTANTS);

        mod.agus.jcoderz.dx.ssa.SsaBasicBlock start = ssaMeth.getEntryBlock();


        HashMap<mod.agus.jcoderz.dx.rop.cst.TypedConstant, mod.agus.jcoderz.dx.rop.code.RegisterSpec> newRegs
                = new HashMap<mod.agus.jcoderz.dx.rop.cst.TypedConstant, mod.agus.jcoderz.dx.rop.code.RegisterSpec> (toCollect);

        for (int i = 0; i < toCollect; i++) {
            mod.agus.jcoderz.dx.rop.cst.TypedConstant cst = constantList.get(i);
            mod.agus.jcoderz.dx.rop.code.RegisterSpec result
                    = mod.agus.jcoderz.dx.rop.code.RegisterSpec.make(ssaMeth.makeNewSsaReg(), cst);

            mod.agus.jcoderz.dx.rop.code.Rop constRop = mod.agus.jcoderz.dx.rop.code.Rops.opConst(cst);

            if (constRop.getBranchingness() == Rop.BRANCH_NONE) {
                start.addInsnToHead(
                        new PlainCstInsn(mod.agus.jcoderz.dx.rop.code.Rops.opConst(cst),
                                mod.agus.jcoderz.dx.rop.code.SourcePosition.NO_INFO, result,
                                mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY, cst));
            } else {

                mod.agus.jcoderz.dx.ssa.SsaBasicBlock entryBlock = ssaMeth.getEntryBlock();
                mod.agus.jcoderz.dx.ssa.SsaBasicBlock successorBlock
                        = entryBlock.getPrimarySuccessor();


                mod.agus.jcoderz.dx.ssa.SsaBasicBlock constBlock
                        = entryBlock.insertNewSuccessor(successorBlock);

                constBlock.replaceLastInsn(
                        new ThrowingCstInsn(constRop, mod.agus.jcoderz.dx.rop.code.SourcePosition.NO_INFO,
                                mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY,
                                StdTypeList.EMPTY, cst));



                SsaBasicBlock resultBlock
                        = constBlock.insertNewSuccessor(successorBlock);
                mod.agus.jcoderz.dx.rop.code.PlainInsn insn
                    = new mod.agus.jcoderz.dx.rop.code.PlainInsn(
                            mod.agus.jcoderz.dx.rop.code.Rops.opMoveResultPseudo(result.getTypeBearer()),
                            mod.agus.jcoderz.dx.rop.code.SourcePosition.NO_INFO,
                            result, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY);

                resultBlock.addInsnToHead(insn);
            }

            newRegs.put(cst, result);
        }

        updateConstUses(newRegs, regSz);
    }

    private ArrayList<mod.agus.jcoderz.dx.rop.cst.TypedConstant> getConstsSortedByCountUse() {
        int regSz = ssaMeth.getRegCount();

        final HashMap<mod.agus.jcoderz.dx.rop.cst.TypedConstant, Integer> countUses
                = new HashMap<mod.agus.jcoderz.dx.rop.cst.TypedConstant, Integer>();

        final HashSet<mod.agus.jcoderz.dx.rop.cst.TypedConstant> usedByLocal
                = new HashSet<mod.agus.jcoderz.dx.rop.cst.TypedConstant>();


        for (int i = 0; i < regSz; i++) {
            mod.agus.jcoderz.dx.ssa.SsaInsn insn = ssaMeth.getDefinitionForRegister(i);

            if (insn == null || insn.getOpcode() == null) continue;

            mod.agus.jcoderz.dx.rop.code.RegisterSpec result = insn.getResult();
            mod.agus.jcoderz.dx.rop.type.TypeBearer typeBearer = result.getTypeBearer();

            if (!typeBearer.isConstant()) continue;

            mod.agus.jcoderz.dx.rop.cst.TypedConstant cst = (mod.agus.jcoderz.dx.rop.cst.TypedConstant) typeBearer;


            if (insn.getOpcode().getOpcode() == RegOps.MOVE_RESULT_PSEUDO) {
                int pred = insn.getBlock().getPredecessors().nextSetBit(0);
                ArrayList<mod.agus.jcoderz.dx.ssa.SsaInsn> predInsns;
                predInsns = ssaMeth.getBlocks().get(pred).getInsns();
                insn = predInsns.get(predInsns.size()-1);
            }

            if (insn.canThrow()) {
                if (!(cst instanceof CstString) || !COLLECT_STRINGS) {
                    continue;
                }
                if (insn.getBlock().getSuccessors().cardinality() > 1) {
                    continue;
                }
            }

            if (ssaMeth.isRegALocal(result)) {
                if (!COLLECT_ONE_LOCAL) {
                    continue;
                } else {
                    if (usedByLocal.contains(cst)) {

                        continue;
                    } else {
                        usedByLocal.add(cst);
                    }
                }
            }

            Integer has = countUses.get(cst);
            if (has == null) {
                countUses.put(cst, 1);
            } else {
                countUses.put(cst, has + 1);
            }
        }


        ArrayList<mod.agus.jcoderz.dx.rop.cst.TypedConstant> constantList = new ArrayList<mod.agus.jcoderz.dx.rop.cst.TypedConstant>();
        for (Map.Entry<mod.agus.jcoderz.dx.rop.cst.TypedConstant, Integer> entry : countUses.entrySet()) {
            if (entry.getValue() > 1) {
                constantList.add(entry.getKey());
            }
        }


        Collections.sort(constantList, new Comparator<mod.agus.jcoderz.dx.rop.cst.Constant>() {
            @Override
            public int compare(mod.agus.jcoderz.dx.rop.cst.Constant a, Constant b) {
                int ret;
                ret = countUses.get(b) - countUses.get(a);

                if (ret == 0) {
                    ret = a.compareTo(b);
                }

                return ret;
            }

            @Override
            public boolean equals (Object obj) {
                return obj == this;
            }
        });

        return constantList;
    }

    private void fixLocalAssignment(mod.agus.jcoderz.dx.rop.code.RegisterSpec origReg,
                                    mod.agus.jcoderz.dx.rop.code.RegisterSpec newReg) {
        for (mod.agus.jcoderz.dx.ssa.SsaInsn use : ssaMeth.getUseListForRegister(origReg.getReg())) {
            mod.agus.jcoderz.dx.rop.code.RegisterSpec localAssignment = use.getLocalAssignment();
            if (localAssignment == null) {
                continue;
            }

            if (use.getResult() == null) {
                continue;
            }

            LocalItem local = localAssignment.getLocalItem();


            use.setResultLocal(null);


            newReg = newReg.withLocalItem(local);

            mod.agus.jcoderz.dx.ssa.SsaInsn newInsn
                    = mod.agus.jcoderz.dx.ssa.SsaInsn.makeFromRop(
                        new PlainInsn(Rops.opMarkLocal(newReg),
                        SourcePosition.NO_INFO, null,
                                RegisterSpecList.make(newReg)),
                    use.getBlock());

            ArrayList<mod.agus.jcoderz.dx.ssa.SsaInsn> insns = use.getBlock().getInsns();

            insns.add(insns.indexOf(use) + 1, newInsn);
        }
    }

    private void updateConstUses(HashMap<mod.agus.jcoderz.dx.rop.cst.TypedConstant, mod.agus.jcoderz.dx.rop.code.RegisterSpec> newRegs,
            int origRegCount) {

        final HashSet<mod.agus.jcoderz.dx.rop.cst.TypedConstant> usedByLocal
                = new HashSet<mod.agus.jcoderz.dx.rop.cst.TypedConstant>();

        final ArrayList<mod.agus.jcoderz.dx.ssa.SsaInsn>[] useList = ssaMeth.getUseListCopy();

        for (int i = 0; i < origRegCount; i++) {
            mod.agus.jcoderz.dx.ssa.SsaInsn insn = ssaMeth.getDefinitionForRegister(i);

            if (insn == null) {
                continue;
            }

            final mod.agus.jcoderz.dx.rop.code.RegisterSpec origReg = insn.getResult();
            TypeBearer typeBearer = insn.getResult().getTypeBearer();

            if (!typeBearer.isConstant()) continue;

            mod.agus.jcoderz.dx.rop.cst.TypedConstant cst = (TypedConstant) typeBearer;
            final mod.agus.jcoderz.dx.rop.code.RegisterSpec newReg = newRegs.get(cst);

            if (newReg == null) {
                continue;
            }

            if (ssaMeth.isRegALocal(origReg)) {
                if (!COLLECT_ONE_LOCAL) {
                    continue;
                } else {
                    if (usedByLocal.contains(cst)) {
                        continue;
                    } else {
                        usedByLocal.add(cst);
                        fixLocalAssignment(origReg, newRegs.get(cst));
                    }
                }
            }


            mod.agus.jcoderz.dx.ssa.RegisterMapper mapper = new RegisterMapper() {
                @Override
                public int getNewRegisterCount() {
                    return ssaMeth.getRegCount();
                }

                @Override
                public mod.agus.jcoderz.dx.rop.code.RegisterSpec map(RegisterSpec registerSpec) {
                    if (registerSpec.getReg() == origReg.getReg()) {
                        return newReg.withLocalItem(
                                registerSpec.getLocalItem());
                    }

                    return registerSpec;
                }
            };

            for (SsaInsn use : useList[origReg.getReg()]) {
                if (use.canThrow()
                        && use.getBlock().getSuccessors().cardinality() > 1) {
                    continue;
                }
                use.mapSourceRegisters(mapper);
            }
        }
    }
}
