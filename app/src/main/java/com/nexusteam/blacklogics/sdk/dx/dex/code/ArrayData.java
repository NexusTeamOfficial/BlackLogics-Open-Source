
package mod.agus.jcoderz.dx.dex.code;

import java.util.ArrayList;

import mod.agus.jcoderz.dx.io.Opcodes;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstLiteral32;
import mod.agus.jcoderz.dx.rop.cst.CstLiteral64;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public final class ArrayData extends VariableSizeInsn {
    private final mod.agus.jcoderz.dx.dex.code.CodeAddress user;

    private final ArrayList<mod.agus.jcoderz.dx.rop.cst.Constant> values;

    private final mod.agus.jcoderz.dx.rop.cst.Constant arrayType;

    private final int elemWidth;

    private final int initLength;

    public ArrayData(SourcePosition position, CodeAddress user,
                     ArrayList<mod.agus.jcoderz.dx.rop.cst.Constant> values,
                     mod.agus.jcoderz.dx.rop.cst.Constant arrayType) {
        super(position, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY);

        if (user == null) {
            throw new NullPointerException("user == null");
        }

        if (values == null) {
            throw new NullPointerException("values == null");
        }

        int sz = values.size();

        if (sz <= 0) {
            throw new IllegalArgumentException("Illegal number of init values");
        }

        this.arrayType = arrayType;

        if (arrayType == mod.agus.jcoderz.dx.rop.cst.CstType.BYTE_ARRAY ||
                arrayType == mod.agus.jcoderz.dx.rop.cst.CstType.BOOLEAN_ARRAY) {
            elemWidth = 1;
        } else if (arrayType == mod.agus.jcoderz.dx.rop.cst.CstType.SHORT_ARRAY ||
                arrayType == mod.agus.jcoderz.dx.rop.cst.CstType.CHAR_ARRAY) {
            elemWidth = 2;
        } else if (arrayType == mod.agus.jcoderz.dx.rop.cst.CstType.INT_ARRAY ||
                arrayType == mod.agus.jcoderz.dx.rop.cst.CstType.FLOAT_ARRAY) {
            elemWidth = 4;
        } else if (arrayType == mod.agus.jcoderz.dx.rop.cst.CstType.LONG_ARRAY ||
                arrayType == CstType.DOUBLE_ARRAY) {
            elemWidth = 8;
        } else {
            throw new IllegalArgumentException("Unexpected constant type");
        }
        this.user = user;
        this.values = values;
        initLength = values.size();
    }

    @Override
    public int codeSize() {
        int sz = initLength;

        return 4 + ((sz * elemWidth) + 1) / 2;
    }

    @Override
    public void writeTo(AnnotatedOutput out) {
        int sz = values.size();

        out.writeShort(Opcodes.FILL_ARRAY_DATA_PAYLOAD);
        out.writeShort(elemWidth);
        out.writeInt(initLength);



        switch (elemWidth) {
            case 1: {
                for (int i = 0; i < sz; i++) {
                    mod.agus.jcoderz.dx.rop.cst.Constant cst = values.get(i);
                    out.writeByte((byte) ((mod.agus.jcoderz.dx.rop.cst.CstLiteral32) cst).getIntBits());
                }
                break;
            }
            case 2: {
                for (int i = 0; i < sz; i++) {
                    mod.agus.jcoderz.dx.rop.cst.Constant cst = values.get(i);
                    out.writeShort((short) ((mod.agus.jcoderz.dx.rop.cst.CstLiteral32) cst).getIntBits());
                }
                break;
            }
            case 4: {
                for (int i = 0; i < sz; i++) {
                    mod.agus.jcoderz.dx.rop.cst.Constant cst = values.get(i);
                    out.writeInt(((CstLiteral32) cst).getIntBits());
                }
                break;
            }
            case 8: {
                for (int i = 0; i < sz; i++) {
                    Constant cst = values.get(i);
                    out.writeLong(((CstLiteral64) cst).getLongBits());
                }
                break;
            }
            default:
                break;
        }


        if (elemWidth == 1 && (sz % 2 != 0)) {
            out.writeByte(0x00);
        }
    }

    @Override
    public DalvInsn withRegisters(RegisterSpecList registers) {
        return new ArrayData(getPosition(), user, values, arrayType);
    }

    @Override
    protected String argString() {
        StringBuilder sb = new StringBuilder(100);

        int sz = values.size();
        for (int i = 0; i < sz; i++) {
            sb.append("\n    ");
            sb.append(i);
            sb.append(": ");
            sb.append(values.get(i).toHuman());
        }

        return sb.toString();
    }

    @Override
    protected String listingString0(boolean noteIndices) {
        int baseAddress = user.getAddress();
        StringBuilder sb = new StringBuilder(100);
        int sz = values.size();

        sb.append("fill-array-data-payload // for fill-array-data @ ");
        sb.append(Hex.u2(baseAddress));

        for (int i = 0; i < sz; i++) {
            sb.append("\n  ");
            sb.append(i);
            sb.append(": ");
            sb.append(values.get(i).toHuman());
        }

        return sb.toString();
    }
}
