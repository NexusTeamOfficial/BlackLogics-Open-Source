
package mod.agus.jcoderz.dx.dex.code.form;

import mod.agus.jcoderz.dx.dex.code.DalvInsn;
import mod.agus.jcoderz.dx.dex.code.InsnFormat;
import mod.agus.jcoderz.dx.dex.code.SimpleInsn;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import java.util.BitSet;

import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;

public final class Form12x extends InsnFormat {
    public static final InsnFormat THE_ONE = new Form12x();

    private Form12x() {

    }

    @Override
    public String insnArgString(DalvInsn insn) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList regs = insn.getRegisters();
        int sz = regs.size();


        return regs.get(sz - 2).regString() + ", " +
            regs.get(sz - 1).regString();
    }

    @Override
    public String insnCommentString(DalvInsn insn, boolean noteIndices) {

        return "";
    }

    @Override
    public int codeSize() {
        return 1;
    }

    @Override
    public boolean isCompatible(DalvInsn insn) {
        if (!(insn instanceof SimpleInsn)) {
            return false;
        }

        mod.agus.jcoderz.dx.rop.code.RegisterSpecList regs = insn.getRegisters();
        mod.agus.jcoderz.dx.rop.code.RegisterSpec rs1;
        RegisterSpec rs2;

        switch (regs.size()) {
            case 2: {
                rs1 = regs.get(0);
                rs2 = regs.get(1);
                break;
            }
            case 3: {
                rs1 = regs.get(1);
                rs2 = regs.get(2);
                if (rs1.getReg() != regs.get(0).getReg()) {
                    return false;
                }
                break;
            }
            default: {
                return false;
            }
        }

        return unsignedFitsInNibble(rs1.getReg()) &&
            unsignedFitsInNibble(rs2.getReg());
    }

    @Override
    public BitSet compatibleRegs(DalvInsn insn) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList regs = insn.getRegisters();
        BitSet bits = new BitSet(2);
        int r0 = regs.get(0).getReg();
        int r1 = regs.get(1).getReg();

        switch (regs.size()) {
          case 2: {
            bits.set(0, unsignedFitsInNibble(r0));
            bits.set(1, unsignedFitsInNibble(r1));
            break;
          }
          case 3: {
            if (r0 != r1) {
                bits.set(0, false);
                bits.set(1, false);
            } else {
                boolean dstRegComp = unsignedFitsInNibble(r1);
                bits.set(0, dstRegComp);
                bits.set(1, dstRegComp);
            }

            bits.set(2, unsignedFitsInNibble(regs.get(2).getReg()));
            break;
          }
          default: {
            throw new AssertionError();
          }
        }

        return bits;
    }

    @Override
    public void writeTo(AnnotatedOutput out, DalvInsn insn) {
        RegisterSpecList regs = insn.getRegisters();
        int sz = regs.size();


        write(out, opcodeUnit(insn,
                              makeByte(regs.get(sz - 2).getReg(),
                                       regs.get(sz - 1).getReg())));
    }
}
