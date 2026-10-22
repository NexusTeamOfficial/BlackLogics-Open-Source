
package mod.agus.jcoderz.dx.ssa;

import java.util.ArrayList;
import java.util.List;

import mod.agus.jcoderz.dx.rop.code.Insn;
import mod.agus.jcoderz.dx.rop.code.PlainCstInsn;
import mod.agus.jcoderz.dx.rop.code.PlainInsn;
import mod.agus.jcoderz.dx.rop.code.RegOps;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.Rop;
import mod.agus.jcoderz.dx.rop.code.Rops;
import mod.agus.jcoderz.dx.rop.code.TranslationAdvice;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstLiteralBits;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeBearer;

public class LiteralOpUpgrader {

    private final mod.agus.jcoderz.dx.ssa.SsaMethod ssaMeth;

    public static void process(mod.agus.jcoderz.dx.ssa.SsaMethod ssaMethod) {
        LiteralOpUpgrader dc;

        dc = new LiteralOpUpgrader(ssaMethod);

        dc.run();
    }

    private LiteralOpUpgrader(SsaMethod ssaMethod) {
        this.ssaMeth = ssaMethod;
    }

    private static boolean isConstIntZeroOrKnownNull(mod.agus.jcoderz.dx.rop.code.RegisterSpec spec) {
        mod.agus.jcoderz.dx.rop.type.TypeBearer tb = spec.getTypeBearer();
        if (tb instanceof mod.agus.jcoderz.dx.rop.cst.CstLiteralBits) {
            mod.agus.jcoderz.dx.rop.cst.CstLiteralBits clb = (CstLiteralBits) tb;
            return (clb.getLongBits() == 0);
        }
        return false;
    }

    private void run() {
        final TranslationAdvice advice = Optimizer.getAdvice();

        ssaMeth.forEachInsn(new mod.agus.jcoderz.dx.ssa.SsaInsn.Visitor() {
            @Override
            public void visitMoveInsn(mod.agus.jcoderz.dx.ssa.NormalSsaInsn insn) {

            }

            @Override
            public void visitPhiInsn(PhiInsn insn) {

            }

            @Override
            public void visitNonMoveInsn(mod.agus.jcoderz.dx.ssa.NormalSsaInsn insn) {

                mod.agus.jcoderz.dx.rop.code.Insn originalRopInsn = insn.getOriginalRopInsn();
                mod.agus.jcoderz.dx.rop.code.Rop opcode = originalRopInsn.getOpcode();
                mod.agus.jcoderz.dx.rop.code.RegisterSpecList sources = insn.getSources();


                if (tryReplacingWithConstant(insn)) return;

                if (sources.size() != 2 ) {

                    return;
                }

                if (opcode.getBranchingness() == mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_IF) {
                    if (isConstIntZeroOrKnownNull(sources.get(0))) {
                        replacePlainInsn(insn, sources.withoutFirst(),
                              mod.agus.jcoderz.dx.rop.code.RegOps.flippedIfOpcode(opcode.getOpcode()), null);
                    } else if (isConstIntZeroOrKnownNull(sources.get(1))) {
                        replacePlainInsn(insn, sources.withoutLast(),
                              opcode.getOpcode(), null);
                    }
                } else if (advice.hasConstantOperation(
                        opcode, sources.get(0), sources.get(1))) {
                    insn.upgradeToLiteral();
                } else  if (opcode.isCommutative()
                        && advice.hasConstantOperation(
                        opcode, sources.get(1), sources.get(0))) {

                    insn.setNewSources(
                            mod.agus.jcoderz.dx.rop.code.RegisterSpecList.make(
                                    sources.get(1), sources.get(0)));

                    insn.upgradeToLiteral();
                }
            }
        });
    }

    private boolean tryReplacingWithConstant(mod.agus.jcoderz.dx.ssa.NormalSsaInsn insn) {
        mod.agus.jcoderz.dx.rop.code.Insn originalRopInsn = insn.getOriginalRopInsn();
        mod.agus.jcoderz.dx.rop.code.Rop opcode = originalRopInsn.getOpcode();
        RegisterSpec result = insn.getResult();

        if (result != null && !ssaMeth.isRegALocal(result) &&
                opcode.getOpcode() != mod.agus.jcoderz.dx.rop.code.RegOps.CONST) {
            TypeBearer type = insn.getResult().getTypeBearer();
            if (type.isConstant() && type.getBasicType() == Type.BT_INT) {

                replacePlainInsn(insn, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY,
                        mod.agus.jcoderz.dx.rop.code.RegOps.CONST, (mod.agus.jcoderz.dx.rop.cst.Constant) type);


                if (opcode.getOpcode() == mod.agus.jcoderz.dx.rop.code.RegOps.MOVE_RESULT_PSEUDO) {
                    int pred = insn.getBlock().getPredecessors().nextSetBit(0);
                    ArrayList<mod.agus.jcoderz.dx.ssa.SsaInsn> predInsns =
                            ssaMeth.getBlocks().get(pred).getInsns();
                    mod.agus.jcoderz.dx.ssa.NormalSsaInsn sourceInsn =
                            (mod.agus.jcoderz.dx.ssa.NormalSsaInsn) predInsns.get(predInsns.size()-1);
                    replacePlainInsn(sourceInsn, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY,
                            mod.agus.jcoderz.dx.rop.code.RegOps.GOTO, null);
                }
                return true;
            }
        }
        return false;
    }

    private void replacePlainInsn(mod.agus.jcoderz.dx.ssa.NormalSsaInsn insn,
                                  RegisterSpecList newSources, int newOpcode, Constant cst) {

        mod.agus.jcoderz.dx.rop.code.Insn originalRopInsn = insn.getOriginalRopInsn();
        Rop newRop = Rops.ropFor(newOpcode, insn.getResult(), newSources, cst);
        Insn newRopInsn;
        if (cst == null) {
            newRopInsn = new PlainInsn(newRop, originalRopInsn.getPosition(),
                    insn.getResult(), newSources);
        } else {
            newRopInsn = new PlainCstInsn(newRop, originalRopInsn.getPosition(),
                    insn.getResult(), newSources, cst);
        }
        mod.agus.jcoderz.dx.ssa.NormalSsaInsn newInsn = new NormalSsaInsn(newRopInsn, insn.getBlock());

        List<SsaInsn> insns = insn.getBlock().getInsns();

        ssaMeth.onInsnRemoved(insn);
        insns.set(insns.lastIndexOf(insn), newInsn);
        ssaMeth.onInsnAdded(newInsn);
    }
}
