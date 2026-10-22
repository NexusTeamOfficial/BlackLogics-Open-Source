
package mod.agus.jcoderz.dx.dex.code;

import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.util.Hex;

public final class MultiCstInsn extends FixedSizeInsn {
    private static final int NOT_SET = -1;

    private final mod.agus.jcoderz.dx.rop.cst.Constant[] constants;

    private final int[] index;

    private int classIndex;

    public MultiCstInsn(mod.agus.jcoderz.dx.dex.code.Dop opcode, mod.agus.jcoderz.dx.rop.code.SourcePosition position,
                        mod.agus.jcoderz.dx.rop.code.RegisterSpecList registers, mod.agus.jcoderz.dx.rop.cst.Constant[] constants) {
        super(opcode, position, registers);

        if (constants == null) {
            throw new NullPointerException("constants == null");
        }

        this.constants = constants;
        this.index = new int[constants.length];
        for (int i = 0; i < this.index.length; ++i) {
            if (constants[i] == null) {
                throw new NullPointerException("constants[i] == null");
            }
            this.index[i] = NOT_SET;
        }
        this.classIndex = NOT_SET;
    }

    private MultiCstInsn(mod.agus.jcoderz.dx.dex.code.Dop opcode, SourcePosition position,
                         mod.agus.jcoderz.dx.rop.code.RegisterSpecList registers, mod.agus.jcoderz.dx.rop.cst.Constant[] constants, int[] index,
                         int classIndex) {
        super(opcode, position, registers);
        this.constants = constants;
        this.index = index;
        this.classIndex = classIndex;
    }

    @Override
    public mod.agus.jcoderz.dx.dex.code.DalvInsn withOpcode(Dop opcode) {
        return new MultiCstInsn(opcode, getPosition(), getRegisters(),
                this.constants, this.index, this.classIndex);
    }

    @Override
    public DalvInsn withRegisters(RegisterSpecList registers) {
        return new MultiCstInsn(getOpcode(), getPosition(), registers,
                this.constants, this.index, this.classIndex);
    }

    public int getNumberOfConstants() {
        return constants.length;
    }

    public Constant getConstant(int position) {
        return constants[position];
    }

    public int getIndex(int position) {
        if (!hasIndex(position)) {
            throw new IllegalStateException("index not yet set for constant "
                + position + " value = " + constants[position]);
        }

        return index[position];
    }

    public boolean hasIndex(int position) {
        return (index[position] != NOT_SET);
    }

    public void setIndex(int position, int index) {
        if (index < 0) {
            throw new IllegalArgumentException("index < 0");
        }

        if (hasIndex(position)) {
            throw new IllegalStateException("index already set");
        }

        this.index[position] = index;
    }

    public int getClassIndex() {
        if (!hasClassIndex()) {
            throw new IllegalStateException("class index not yet set");
        }

        return classIndex;
    }

    public boolean hasClassIndex() {
        return (classIndex != NOT_SET);
    }

    public void setClassIndex(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("index < 0");
        }

        if (hasClassIndex()) {
            throw new IllegalStateException("class index already set");
        }

        this.classIndex = index;
    }

    @Override
    protected String argString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < constants.length; ++i) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(constants[i].toHuman());
        }
        return sb.toString();
    }

    @Override
    public String cstString() {
        return argString();
    }

    @Override
    public String cstComment() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < constants.length; ++i) {
            if (!hasIndex(i)) {
                return "";
            }

            if (i > 0) {
                sb.append(", ");
            }
            sb.append(getConstant(i).typeName());
            sb.append('@');

            final int currentIndex = getIndex(i);
            if (currentIndex < 65536) {
                sb.append(mod.agus.jcoderz.dx.util.Hex.u2(currentIndex));
            } else {
                sb.append(Hex.u4(currentIndex));
            }
        }
        return sb.toString();
    }
}
