
package mod.agus.jcoderz.dx.ssa;

import java.util.HashSet;
import java.util.List;

import mod.agus.jcoderz.dx.rop.code.CstInsn;
import mod.agus.jcoderz.dx.rop.code.LocalItem;
import mod.agus.jcoderz.dx.rop.code.RegOps;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;

public class MoveParamCombiner {

    private final mod.agus.jcoderz.dx.ssa.SsaMethod ssaMeth;

    public static void process(mod.agus.jcoderz.dx.ssa.SsaMethod ssaMethod) {
        new MoveParamCombiner(ssaMethod).run();
    }

    private MoveParamCombiner(SsaMethod ssaMeth) {
        this.ssaMeth = ssaMeth;
    }

    private void run() {

        final mod.agus.jcoderz.dx.rop.code.RegisterSpec[] paramSpecs
                = new mod.agus.jcoderz.dx.rop.code.RegisterSpec[ssaMeth.getParamWidth()];


        final HashSet<mod.agus.jcoderz.dx.ssa.SsaInsn> deletedInsns = new HashSet();

        ssaMeth.forEachInsn(new mod.agus.jcoderz.dx.ssa.SsaInsn.Visitor() {
            @Override
            public void visitMoveInsn (NormalSsaInsn insn) {
            }
            @Override
            public void visitPhiInsn (PhiInsn phi) {
            }
            @Override
            public void visitNonMoveInsn (NormalSsaInsn insn) {
                if (insn.getOpcode().getOpcode() != RegOps.MOVE_PARAM) {
                    return;
                }

                int param = getParamIndex(insn);

                if (paramSpecs[param] == null) {
                    paramSpecs[param] = insn.getResult();
                } else {
                    final mod.agus.jcoderz.dx.rop.code.RegisterSpec specA = paramSpecs[param];
                    final mod.agus.jcoderz.dx.rop.code.RegisterSpec specB = insn.getResult();
                    mod.agus.jcoderz.dx.rop.code.LocalItem localA = specA.getLocalItem();
                    mod.agus.jcoderz.dx.rop.code.LocalItem localB = specB.getLocalItem();
                    LocalItem newLocal;


                    if (localA == null) {
                        newLocal = localB;
                    } else if (localB == null) {
                        newLocal = localA;
                    } else if (localA.equals(localB)) {
                        newLocal = localA;
                    } else {
                        return;
                    }

                    ssaMeth.getDefinitionForRegister(specA.getReg())
                            .setResultLocal(newLocal);


                    mod.agus.jcoderz.dx.ssa.RegisterMapper mapper = new RegisterMapper() {
                        @Override
                        public int getNewRegisterCount() {
                            return ssaMeth.getRegCount();
                        }

                        @Override
                        public mod.agus.jcoderz.dx.rop.code.RegisterSpec map(RegisterSpec registerSpec) {
                            if (registerSpec.getReg() == specB.getReg()) {
                                return specA;
                            }

                            return registerSpec;
                        }
                    };

                    List<mod.agus.jcoderz.dx.ssa.SsaInsn> uses
                            = ssaMeth.getUseListForRegister(specB.getReg());


                    for (int i = uses.size() - 1; i >= 0; i--) {
                        SsaInsn use = uses.get(i);
                        use.mapSourceRegisters(mapper);
                    }

                    deletedInsns.add(insn);
                }

            }
        });

        ssaMeth.deleteInsns(deletedInsns);
    }

    private int getParamIndex(NormalSsaInsn insn) {
        mod.agus.jcoderz.dx.rop.code.CstInsn cstInsn = (CstInsn)(insn.getOriginalRopInsn());

        int param = ((CstInteger)cstInsn.getConstant()).getValue();
        return param;
    }

}
