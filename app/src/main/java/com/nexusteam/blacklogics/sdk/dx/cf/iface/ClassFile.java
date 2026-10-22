
package mod.agus.jcoderz.dx.cf.iface;

import mod.agus.jcoderz.dx.cf.code.BootstrapMethodsList;
import mod.agus.jcoderz.dx.rop.cst.ConstantPool;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.TypeList;

public interface ClassFile extends HasAttribute {
    public int getMagic();

    public int getMinorVersion();

    public int getMajorVersion();

    public int getAccessFlags();

    public mod.agus.jcoderz.dx.rop.cst.CstType getThisClass();

    public CstType getSuperclass();

    public ConstantPool getConstantPool();

    public TypeList getInterfaces();

    public FieldList getFields();

    public MethodList getMethods();

    @Override
    public AttributeList getAttributes();

    public BootstrapMethodsList getBootstrapMethods();

    public CstString getSourceFile();
}
