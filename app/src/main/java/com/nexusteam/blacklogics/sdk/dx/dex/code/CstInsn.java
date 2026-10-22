
package mod.agus.jcoderz.dx.dex.code;

import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.util.Hex;

public final class CstInsn extends FixedSizeInsn {
    private final mod.agus.jcoderz.dx.rop.cst.Constant constant;

    private int index;

    private int classIndex;

    public CstInsn(mod.agus.jcoderz.dx.dex.code.Dop opcode, SourcePosition position,
                   mod.agus.jcoderz.dx.rop.code.RegisterSpecList registers, mod.agus.jcoderz.dx.rop.cst.Constant constant) {
        super(opcode, position, registers);

        if (constant == null) {
            throw new NullPointerException("constant == null");
        }

        this.constant = constant;
        this.index = -1;
        this.classIndex = -1;
    }

    @Override
    public mod.agus.jcoderz.dx.dex.code.DalvInsn withOpcode(Dop opcode) {
        CstInsn result =
            new CstInsn(opcode, getPosition(), getRegisters(), constant);

        if (index >= 0) {
            result.setIndex(index);
        }

        if (classIndex >= 0) {
            result.setClassIndex(classIndex);
        }

        return result;
    }

    @Override
    public DalvInsn withRegisters(RegisterSpecList registers) {
        CstInsn result =
            new CstInsn(getOpcode(), getPosition(), registers, constant);

        if (index >= 0) {
            result.setIndex(index);
        }

        if (classIndex >= 0) {
            result.setClassIndex(classIndex);
        }

        return result;
    }

    public Constant getConstant() {
        return constant;
    }

    public int getIndex() {
        if (index < 0) {
            throw new IllegalStateException("index not yet set for " + constant);
        }

        return index;
    }

    public boolean hasIndex() {
        return (index >= 0);
    }

    public void setIndex(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("index < 0");
        }

        if (this.index >= 0) {
            throw new IllegalStateException("index already set");
        }

        this.index = index;
    }

    public int getClassIndex() {
        if (classIndex < 0) {
            throw new IllegalStateException("class index not yet set");
        }

        return classIndex;
    }

    public boolean hasClassIndex() {
        return (classIndex >= 0);
    }

    public void setClassIndex(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("index < 0");
        }

        if (this.classIndex >= 0) {
            throw new IllegalStateException("class index already set");
        }

        this.classIndex = index;
    }

    @Override
    protected String argString() {
        return constant.toHuman();
    }

    @Override
    public String cstString() {
        if (constant instanceof mod.agus.jcoderz.dx.rop.cst.CstString) {
            return ((CstString) constant).toQuoted();
        }
        return constant.toHuman();
    }

    @Override
    public String cstComment() {
        if (!hasIndex()) {
            return "";
        }

        StringBuilder sb = new StringBuilder(20);
        sb.append(getConstant().typeName());
        sb.append('@');

        if (index < 65536) {
            sb.append(mod.agus.jcoderz.dx.util.Hex.u2(index));
        } else {
            sb.append(Hex.u4(index));
        }

        return sb.toString();
    }
}
