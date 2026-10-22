package mod.agus.jcoderz.dx.cf.code;

import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstDouble;
import mod.agus.jcoderz.dx.rop.cst.CstFloat;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;
import mod.agus.jcoderz.dx.rop.cst.CstLong;
import mod.agus.jcoderz.dx.rop.cst.CstMethodHandle;
import mod.agus.jcoderz.dx.rop.cst.CstProtoRef;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.util.FixedSizeList;

public class BootstrapMethodArgumentsList extends FixedSizeList {
    public BootstrapMethodArgumentsList(int count) {
        super(count);
    }

    public mod.agus.jcoderz.dx.rop.cst.Constant get(int n) {
        return (mod.agus.jcoderz.dx.rop.cst.Constant) get0(n);
    }

    public void set(int n, Constant cst) {

        if (cst instanceof CstString ||
            cst instanceof CstType ||
            cst instanceof CstInteger ||
            cst instanceof CstLong ||
            cst instanceof CstFloat ||
            cst instanceof CstDouble ||
            cst instanceof CstMethodHandle ||
            cst instanceof CstProtoRef) {
            set0(n, cst);
        } else {
            Class<?> klass = cst.getClass();
            throw new IllegalArgumentException("bad type for bootstrap argument: " + klass);
        }
    }
}
