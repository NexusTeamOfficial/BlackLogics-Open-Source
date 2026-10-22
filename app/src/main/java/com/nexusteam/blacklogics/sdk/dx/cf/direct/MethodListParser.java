
package mod.agus.jcoderz.dx.cf.direct;

import mod.agus.jcoderz.dx.cf.iface.AttributeList;
import mod.agus.jcoderz.dx.cf.iface.Member;
import mod.agus.jcoderz.dx.cf.iface.StdMethod;
import mod.agus.jcoderz.dx.cf.iface.StdMethodList;
import mod.agus.jcoderz.dx.rop.code.AccessFlags;
import mod.agus.jcoderz.dx.rop.cst.CstNat;
import mod.agus.jcoderz.dx.rop.cst.CstType;

final  class MethodListParser extends mod.agus.jcoderz.dx.cf.direct.MemberListParser {
    final private mod.agus.jcoderz.dx.cf.iface.StdMethodList methods;

    public MethodListParser(DirectClassFile cf, CstType definer,
            int offset, AttributeFactory attributeFactory) {
        super(cf, definer, offset, attributeFactory);
        methods = new mod.agus.jcoderz.dx.cf.iface.StdMethodList(getCount());
    }

    public StdMethodList getList() {
        parseIfNecessary();
        return methods;
    }

    @Override
    protected String humanName() {
        return "method";
    }

    @Override
    protected String humanAccessFlags(int accessFlags) {
        return AccessFlags.methodString(accessFlags);
    }

    @Override
    protected int getAttributeContext() {
        return AttributeFactory.CTX_METHOD;
    }

    @Override
    protected Member set(int n, int accessFlags, CstNat nat,
                         AttributeList attributes) {
        mod.agus.jcoderz.dx.cf.iface.StdMethod meth =
            new StdMethod(getDefiner(), accessFlags, nat, attributes);

        methods.set(n, meth);
        return meth;
    }
}
