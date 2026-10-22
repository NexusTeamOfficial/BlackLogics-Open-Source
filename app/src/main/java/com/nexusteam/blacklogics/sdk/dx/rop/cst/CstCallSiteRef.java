package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.rop.type.Prototype;
import mod.agus.jcoderz.dx.rop.type.Type;

public class CstCallSiteRef extends Constant {

    private final CstInvokeDynamic invokeDynamic;

    private final int id;

    CstCallSiteRef(CstInvokeDynamic invokeDynamic, int id) {
        if (invokeDynamic == null) {
            throw new NullPointerException("invokeDynamic == null");
        }
        this.invokeDynamic = invokeDynamic;
        this.id = id;
    }

    @Override
    public boolean isCategory2() {
        return false;
    }

    @Override
    public String typeName() {
        return "CallSiteRef";
    }

    @Override
    protected int compareTo0(Constant other) {
        CstCallSiteRef o = (CstCallSiteRef) other;
        int result = invokeDynamic.compareTo(o.invokeDynamic);
        if (result != 0) {
            return result;
        }
        return Integer.compare(id, o.id);
    }

    @Override
    public String toHuman() {
        return getCallSite().toHuman();
    }

    @Override
    public String toString() {
        return getCallSite().toString();
    }

    public Prototype getPrototype() {
        return invokeDynamic.getPrototype();
    }

    public Type getReturnType() {
        return invokeDynamic.getReturnType();
    }

    public CstCallSite getCallSite() {
        return invokeDynamic.getCallSite();
    }
}
