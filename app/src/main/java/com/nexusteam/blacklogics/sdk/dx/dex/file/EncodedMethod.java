
package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dex.Leb128;
import mod.agus.jcoderz.dx.dex.code.DalvCode;
import mod.agus.jcoderz.dx.rop.code.AccessFlags;
import mod.agus.jcoderz.dx.rop.cst.CstMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.type.TypeList;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

import java.io.PrintWriter;

public final class EncodedMethod extends EncodedMember
        implements Comparable<EncodedMethod> {
    private final mod.agus.jcoderz.dx.rop.cst.CstMethodRef method;

    private final CodeItem code;

    public EncodedMethod(mod.agus.jcoderz.dx.rop.cst.CstMethodRef method, int accessFlags,
                         DalvCode code, TypeList throwsList) {
        super(accessFlags);

        if (method == null) {
            throw new NullPointerException("method == null");
        }

        this.method = method;

        if (code == null) {
            this.code = null;
        } else {
            boolean isStatic = (accessFlags & mod.agus.jcoderz.dx.rop.code.AccessFlags.ACC_STATIC) != 0;
            this.code = new CodeItem(method, code, isStatic, throwsList);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (! (other instanceof EncodedMethod)) {
            return false;
        }

        return compareTo((EncodedMethod) other) == 0;
    }

    @Override
    public int compareTo(EncodedMethod other) {
        return method.compareTo(other.method);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(100);

        sb.append(getClass().getName());
        sb.append('{');
        sb.append(mod.agus.jcoderz.dx.util.Hex.u2(getAccessFlags()));
        sb.append(' ');
        sb.append(method);

        if (code != null) {
            sb.append(' ');
            sb.append(code);
        }

        sb.append('}');

        return sb.toString();
    }

    @Override
    public void addContents(DexFile file) {
        MethodIdsSection methodIds = file.getMethodIds();
        MixedItemSection wordData = file.getWordData();

        methodIds.intern(method);

        if (code != null) {
            wordData.add(code);
        }
    }

    @Override
    public final String toHuman() {
        return method.toHuman();
    }

    @Override
    public final CstString getName() {
        return method.getNat().getName();
    }

    @Override
    public void debugPrint(PrintWriter out, boolean verbose) {
        if (code == null) {
            out.println(getRef().toHuman() + ": abstract or native");
        } else {
            code.debugPrint(out, "  ", verbose);
        }
    }

    public final CstMethodRef getRef() {
        return method;
    }

    @Override
    public int encode(DexFile file, AnnotatedOutput out,
            int lastIndex, int dumpSeq) {
        int methodIdx = file.getMethodIds().indexOf(method);
        int diff = methodIdx - lastIndex;
        int accessFlags = getAccessFlags();
        int codeOff = OffsettedItem.getAbsoluteOffsetOr0(code);
        boolean hasCode = (codeOff != 0);
        boolean shouldHaveCode = (accessFlags &
                (mod.agus.jcoderz.dx.rop.code.AccessFlags.ACC_ABSTRACT | mod.agus.jcoderz.dx.rop.code.AccessFlags.ACC_NATIVE)) == 0;

        if (hasCode != shouldHaveCode) {
            throw new UnsupportedOperationException(
                    "code vs. access_flags mismatch");
        }

        if (out.annotates()) {
            out.annotate(0, String.format("  [%x] %s", dumpSeq,
                            method.toHuman()));
            out.annotate(Leb128.unsignedLeb128Size(diff),
                    "    method_idx:   " + mod.agus.jcoderz.dx.util.Hex.u4(methodIdx));
            out.annotate(Leb128.unsignedLeb128Size(accessFlags),
                    "    access_flags: " +
                    AccessFlags.methodString(accessFlags));
            out.annotate(Leb128.unsignedLeb128Size(codeOff),
                    "    code_off:     " + Hex.u4(codeOff));
        }

        out.writeUleb128(diff);
        out.writeUleb128(accessFlags);
        out.writeUleb128(codeOff);

        return methodIdx;
    }
}
