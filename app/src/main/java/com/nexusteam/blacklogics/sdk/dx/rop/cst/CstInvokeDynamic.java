
package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.rop.type.Prototype;
import mod.agus.jcoderz.dx.rop.type.Type;
import java.util.ArrayList;
import java.util.List;

public final class CstInvokeDynamic extends Constant {

    private final int bootstrapMethodIndex;

    private final CstNat nat;

    private final Prototype prototype;

    private mod.agus.jcoderz.dx.rop.cst.CstType declaringClass;

    private mod.agus.jcoderz.dx.rop.cst.CstCallSite callSite;

    private final List<mod.agus.jcoderz.dx.rop.cst.CstCallSiteRef> references;

    public static CstInvokeDynamic make(int bootstrapMethodIndex, CstNat nat) {
        return new CstInvokeDynamic(bootstrapMethodIndex, nat);
    }

    private CstInvokeDynamic(int bootstrapMethodIndex, CstNat nat) {
        this.bootstrapMethodIndex = bootstrapMethodIndex;
        this.nat = nat;
        this.prototype = Prototype.fromDescriptor(nat.getDescriptor().toHuman());
        this.references = new ArrayList<>();
    }

    public mod.agus.jcoderz.dx.rop.cst.CstCallSiteRef addReference() {
        mod.agus.jcoderz.dx.rop.cst.CstCallSiteRef ref = new mod.agus.jcoderz.dx.rop.cst.CstCallSiteRef(this, references.size());
        references.add(ref);
        return ref;
    }

    public List<CstCallSiteRef> getReferences() {
        return references;
    }

    @Override
    public String toString() {
        return toHuman();
    }

    @Override
    public String typeName() {
        return "InvokeDynamic";
    }

    @Override
    public String toHuman() {
        String klass = (declaringClass != null) ? declaringClass.toHuman() : "Unknown";
        return "InvokeDynamic(" + klass + ":" + bootstrapMethodIndex + ", " + nat.toHuman() + ")";
    }

    @Override
    public boolean isCategory2() {
        return false;
    }

    @Override
    protected int compareTo0(Constant other) {
        CstInvokeDynamic otherInvoke = (CstInvokeDynamic) other;
        int result = Integer.compare(bootstrapMethodIndex, otherInvoke.getBootstrapMethodIndex());
        if (result != 0) {
            return result;
        }

        result = nat.compareTo(otherInvoke.getNat());
        if (result != 0) {
            return result;
        }

        result = declaringClass.compareTo(otherInvoke.getDeclaringClass());
        if (result != 0) {
            return result;
        }

        return callSite.compareTo(otherInvoke.getCallSite());
    }

    public int getBootstrapMethodIndex() {
        return bootstrapMethodIndex;
    }

    public CstNat getNat() {
        return nat;
    }

    public Prototype getPrototype() {
        return prototype;
    }

    public Type getReturnType() {
        return prototype.getReturnType();
    }

    public void setDeclaringClass(mod.agus.jcoderz.dx.rop.cst.CstType declaringClass) {
        if (this.declaringClass != null) {
            throw new IllegalArgumentException("already added declaring class");
        } else if (declaringClass == null) {
            throw new NullPointerException("declaringClass == null");
        }
        this.declaringClass = declaringClass;
    }

    public CstType getDeclaringClass() {
        return declaringClass;
    }

    public void setCallSite(mod.agus.jcoderz.dx.rop.cst.CstCallSite callSite) {
        if (this.callSite != null) {
            throw new IllegalArgumentException("already added call site");
        } else if (callSite == null) {
            throw new NullPointerException("callSite == null");
        }
        this.callSite = callSite;
    }

    public CstCallSite getCallSite() {
        return callSite;
    }
}
