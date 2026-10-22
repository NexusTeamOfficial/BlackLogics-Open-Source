
package mod.agus.jcoderz.dx.dex.code;

import mod.agus.jcoderz.dex.util.ExceptionWithContext;
import mod.agus.jcoderz.dx.io.Opcodes;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstBaseMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstCallSiteRef;
import mod.agus.jcoderz.dx.rop.cst.CstProtoRef;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.FixedSizeList;
import mod.agus.jcoderz.dx.util.IndentingWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public final class DalvInsnList extends FixedSizeList {

    private final int regCount;

    public static DalvInsnList makeImmutable(ArrayList<mod.agus.jcoderz.dx.dex.code.DalvInsn> list,
            int regCount) {
        int size = list.size();
        DalvInsnList result = new DalvInsnList(size, regCount);

        for (int i = 0; i < size; i++) {
            result.set(i, list.get(i));
        }

        result.setImmutable();
        return result;
    }

    public DalvInsnList(int size, int regCount) {
        super(size);
        this.regCount = regCount;
    }

    public mod.agus.jcoderz.dx.dex.code.DalvInsn get(int n) {
        return (mod.agus.jcoderz.dx.dex.code.DalvInsn) get0(n);
    }

    public void set(int n, mod.agus.jcoderz.dx.dex.code.DalvInsn insn) {
        set0(n, insn);
    }

    public int codeSize() {
        int sz = size();

        if (sz == 0) {
            return 0;
        }

        mod.agus.jcoderz.dx.dex.code.DalvInsn last = get(sz - 1);
        return last.getNextAddress();
    }

    public void writeTo(AnnotatedOutput out) {
        int startCursor = out.getCursor();
        int sz = size();

        if (out.annotates()) {
            boolean verbose = out.isVerbose();

            for (int i = 0; i < sz; i++) {
                mod.agus.jcoderz.dx.dex.code.DalvInsn insn = (mod.agus.jcoderz.dx.dex.code.DalvInsn) get0(i);
                int codeBytes = insn.codeSize() * 2;
                String s;

                if ((codeBytes != 0) || verbose) {
                    s = insn.listingString("  ", out.getAnnotationWidth(),
                            true);
                } else {
                    s = null;
                }

                if (s != null) {
                    out.annotate(codeBytes, s);
                } else if (codeBytes != 0) {
                    out.annotate(codeBytes, "");
                }
            }
        }

        for (int i = 0; i < sz; i++) {
            mod.agus.jcoderz.dx.dex.code.DalvInsn insn = (mod.agus.jcoderz.dx.dex.code.DalvInsn) get0(i);
            try {
                insn.writeTo(out);
            } catch (RuntimeException ex) {
                throw ExceptionWithContext.withContext(ex,
                        "...while writing " + insn);
            }
        }


        int written = (out.getCursor() - startCursor) / 2;
        if (written != codeSize()) {
            throw new RuntimeException("write length mismatch; expected " +
                    codeSize() + " but actually wrote " + written);
        }
    }

    public int getRegistersSize() {
        return regCount;
    }

    public int getOutsSize() {
        int sz = size();
        int result = 0;

        for (int i = 0; i < sz; i++) {
            mod.agus.jcoderz.dx.dex.code.DalvInsn insn = (mod.agus.jcoderz.dx.dex.code.DalvInsn) get0(i);
            int count = 0;

            if (insn instanceof mod.agus.jcoderz.dx.dex.code.CstInsn) {
                Constant cst = ((CstInsn) insn).getConstant();
                if (cst instanceof mod.agus.jcoderz.dx.rop.cst.CstBaseMethodRef) {
                    mod.agus.jcoderz.dx.rop.cst.CstBaseMethodRef methodRef = (CstBaseMethodRef) cst;
                    boolean isStatic =
                        (insn.getOpcode().getFamily() == mod.agus.jcoderz.dx.io.Opcodes.INVOKE_STATIC);
                    count = methodRef.getParameterWordCount(isStatic);
                } else if (cst instanceof mod.agus.jcoderz.dx.rop.cst.CstCallSiteRef) {
                    mod.agus.jcoderz.dx.rop.cst.CstCallSiteRef invokeDynamicRef = (CstCallSiteRef) cst;
                    count = invokeDynamicRef.getPrototype().getParameterTypes().getWordCount();
                }
            } else if (insn instanceof MultiCstInsn) {
                if (insn.getOpcode().getFamily() != Opcodes.INVOKE_POLYMORPHIC) {
                    throw new RuntimeException("Expecting invoke-polymorphic");
                }
                MultiCstInsn mci = (MultiCstInsn) insn;





                mod.agus.jcoderz.dx.rop.cst.CstProtoRef proto = (CstProtoRef) mci.getConstant(1);
                count = proto.getPrototype().getParameterTypes().getWordCount();
                count = count + 1;
            } else {
                continue;
            }

            if (count > result) {
                result = count;
            }
        }

        return result;
    }

    public void debugPrint(Writer out, String prefix, boolean verbose) {
        mod.agus.jcoderz.dx.util.IndentingWriter iw = new IndentingWriter(out, 0, prefix);
        int sz = size();

        try {
            for (int i = 0; i < sz; i++) {
                mod.agus.jcoderz.dx.dex.code.DalvInsn insn = (DalvInsn) get0(i);
                String s;

                if ((insn.codeSize() != 0) || verbose) {
                    s = insn.listingString("", 0, verbose);
                } else {
                    s = null;
                }

                if (s != null) {
                    iw.write(s);
                }
            }

            iw.flush();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void debugPrint(OutputStream out, String prefix, boolean verbose) {
        Writer w = new OutputStreamWriter(out);
        debugPrint(w, prefix, verbose);

        try {
            w.flush();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
