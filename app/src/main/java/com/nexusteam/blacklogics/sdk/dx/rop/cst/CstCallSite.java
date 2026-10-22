
package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.cf.code.BootstrapMethodArgumentsList;
import mod.agus.jcoderz.dx.rop.type.Prototype;

public final class CstCallSite extends CstArray {
    public static CstCallSite make(CstMethodHandle bootstrapHandle, CstNat nat,
                                   BootstrapMethodArgumentsList optionalArguments) {
        if (bootstrapHandle == null) {
            throw new NullPointerException("bootstrapMethodHandle == null");
        } else if (nat == null) {
            throw new NullPointerException("nat == null");
        }

        List list = new List(3 + optionalArguments.size());
        list.set(0, bootstrapHandle);
        list.set(1, nat.getName());
        list.set(2, new CstProtoRef(Prototype.fromDescriptor(nat.getDescriptor().getString())));
        if (optionalArguments != null) {
            for (int i = 0; i < optionalArguments.size(); ++i) {
                list.set(i + 3, optionalArguments.get(i));
            }
        }
        list.setImmutable();
        return new CstCallSite(list);
    }

    private CstCallSite(List list) {
        super(list);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof CstCallSite) {
            return getList().equals(((CstCallSite) other).getList());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return getList().hashCode();
    }

    @Override
    protected int compareTo0(Constant other) {
        return getList().compareTo(((CstCallSite) other).getList());
    }

    @Override
    public String toString() {
        return getList().toString("call site{", ", ", "}");
    }

    @Override
    public String typeName() {
        return "call site";
    }

    @Override
    public boolean isCategory2() {
        return false;
    }

    @Override
    public String toHuman() {
        return getList().toHuman("{", ", ", "}");
    }
}
