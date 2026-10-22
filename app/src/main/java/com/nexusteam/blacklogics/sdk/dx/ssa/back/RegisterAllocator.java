
package mod.agus.jcoderz.dx.ssa.back;

import mod.agus.jcoderz.dx.ssa.NormalSsaInsn;
import mod.agus.jcoderz.dx.ssa.RegisterMapper;
import mod.agus.jcoderz.dx.ssa.SsaBasicBlock;
import mod.agus.jcoderz.dx.ssa.SsaInsn;
import mod.agus.jcoderz.dx.ssa.SsaMethod;
import mod.agus.jcoderz.dx.util.IntIterator;
import mod.agus.jcoderz.dx.util.IntSet;
import java.util.ArrayList;

import mod.agus.jcoderz.dx.rop.code.PlainInsn;
import mod.agus.jcoderz.dx.rop.code.RegOps;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.Rops;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;

public abstract class RegisterAllocator {
    protected final SsaMethod ssaMeth;

    protected final mod.agus.jcoderz.dx.ssa.back.InterferenceGraph interference;

    public RegisterAllocator(SsaMethod ssaMeth,
            InterferenceGraph interference) {
        this.ssaMeth = ssaMeth;
        this.interference = interference;
    }

    public abstract boolean wantsParamsMovedHigh();

    public abstract RegisterMapper allocateRegisters();

    protected final int getCategoryForSsaReg(int reg) {
        SsaInsn definition = ssaMeth.getDefinitionForRegister(reg);

        if (definition == null) {

            return 1;
        } else {
            return definition.getResult().getCategory();
        }
    }

    protected final mod.agus.jcoderz.dx.rop.code.RegisterSpec getDefinitionSpecForSsaReg(int reg) {
        SsaInsn definition = ssaMeth.getDefinitionForRegister(reg);

        return definition == null ? null : definition.getResult();
    }

    protected boolean isDefinitionMoveParam(int reg) {
        SsaInsn defInsn = ssaMeth.getDefinitionForRegister(reg);

        if (defInsn instanceof NormalSsaInsn) {
            NormalSsaInsn ndefInsn = (NormalSsaInsn) defInsn;

            return ndefInsn.getOpcode().getOpcode() == RegOps.MOVE_PARAM;
        }

        return false;
    }

    protected final mod.agus.jcoderz.dx.rop.code.RegisterSpec insertMoveBefore(SsaInsn insn,
                                                                               mod.agus.jcoderz.dx.rop.code.RegisterSpec reg) {
        SsaBasicBlock block = insn.getBlock();
        ArrayList<SsaInsn> insns = block.getInsns();
        int insnIndex = insns.indexOf(insn);

        if (insnIndex < 0) {
            throw new IllegalArgumentException (
                    "specified insn is not in this block");
        }

        if (insnIndex != insns.size() - 1) {
            throw new IllegalArgumentException(
                    "Adding move here not supported:" + insn.toHuman());
        }



        mod.agus.jcoderz.dx.rop.code.RegisterSpec newRegSpec = RegisterSpec.make(ssaMeth.makeNewSsaReg(),
                reg.getTypeBearer());

        SsaInsn toAdd = SsaInsn.makeFromRop(
                new PlainInsn(Rops.opMove(newRegSpec.getType()),
                        SourcePosition.NO_INFO, newRegSpec,
                        mod.agus.jcoderz.dx.rop.code.RegisterSpecList.make(reg)), block);

        insns.add(insnIndex, toAdd);

        int newReg = newRegSpec.getReg();


        IntSet liveOut = block.getLiveOutRegs();
        IntIterator liveOutIter = liveOut.iterator();

        while (liveOutIter.hasNext()) {
            interference.add(newReg, liveOutIter.next());
        }


        RegisterSpecList sources = insn.getSources();
        int szSources = sources.size();

        for (int i = 0; i < szSources; i++) {
            interference.add(newReg, sources.get(i).getReg());
        }

        ssaMeth.onInsnsChanged();

        return newRegSpec;
    }
}
