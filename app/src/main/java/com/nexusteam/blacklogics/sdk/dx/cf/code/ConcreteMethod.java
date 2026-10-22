
package mod.agus.jcoderz.dx.cf.code;

import mod.agus.jcoderz.dx.cf.attrib.AttCode;
import mod.agus.jcoderz.dx.cf.attrib.AttLineNumberTable;
import mod.agus.jcoderz.dx.cf.attrib.AttLocalVariableTable;
import mod.agus.jcoderz.dx.cf.attrib.AttLocalVariableTypeTable;
import mod.agus.jcoderz.dx.cf.iface.AttributeList;
import mod.agus.jcoderz.dx.cf.iface.ClassFile;
import mod.agus.jcoderz.dx.cf.iface.Method;
import mod.agus.jcoderz.dx.rop.code.AccessFlags;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.rop.cst.CstNat;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Prototype;

public final class ConcreteMethod implements Method {
    private final Method method;

    private final ClassFile classFile;

    private final AttCode attCode;

    private final LineNumberList lineNumbers;

    private final LocalVariableList localVariables;

    public ConcreteMethod(Method method, ClassFile classFile,
            boolean keepLines, boolean keepLocals) {
        this.method = method;
        this.classFile = classFile;

        AttributeList attribs = method.getAttributes();
        this.attCode = (AttCode) attribs.findFirst(AttCode.ATTRIBUTE_NAME);

        AttributeList codeAttribs = attCode.getAttributes();

        LineNumberList lnl = LineNumberList.EMPTY;
        if (keepLines) {
            for (AttLineNumberTable lnt = (AttLineNumberTable)
                     codeAttribs.findFirst(AttLineNumberTable.ATTRIBUTE_NAME);
                 lnt != null;
                 lnt = (AttLineNumberTable) codeAttribs.findNext(lnt)) {
                lnl = LineNumberList.concat(lnl, lnt.getLineNumbers());
            }
        }
        this.lineNumbers = lnl;

        LocalVariableList lvl = LocalVariableList.EMPTY;
        if (keepLocals) {
            for (AttLocalVariableTable lvt = (AttLocalVariableTable)
                     codeAttribs.findFirst(
                             AttLocalVariableTable.ATTRIBUTE_NAME);
                 lvt != null;
                 lvt = (AttLocalVariableTable) codeAttribs.findNext(lvt)) {

                lvl = LocalVariableList.concat(lvl, lvt.getLocalVariables());
            }

            LocalVariableList typeList = LocalVariableList.EMPTY;
            for (AttLocalVariableTypeTable lvtt = (AttLocalVariableTypeTable)
                     codeAttribs.findFirst(
                             AttLocalVariableTypeTable.ATTRIBUTE_NAME);
                 lvtt != null;
                 lvtt = (AttLocalVariableTypeTable) codeAttribs.findNext(lvtt)) {
                typeList = LocalVariableList.concat(typeList, lvtt.getLocalVariables());
            }

            if (typeList.size() != 0) {

                lvl = LocalVariableList.mergeDescriptorsAndSignatures(lvl, typeList);
            }
        }
        this.localVariables = lvl;
    }


    public mod.agus.jcoderz.dx.rop.cst.CstString getSourceFile() {
        return classFile.getSourceFile();
    }

    public final boolean isDefaultOrStaticInterfaceMethod() {
        return (classFile.getAccessFlags() & mod.agus.jcoderz.dx.rop.code.AccessFlags.ACC_INTERFACE) != 0
            && !getNat().isClassInit();
    }

    public final boolean isStaticMethod() {
        return (getAccessFlags() & AccessFlags.ACC_STATIC) != 0;
    }

    @Override
    public CstNat getNat() {
        return method.getNat();
    }

    @Override
    public mod.agus.jcoderz.dx.rop.cst.CstString getName() {
        return method.getName();
    }

    @Override
    public CstString getDescriptor() {
        return method.getDescriptor();
    }

    @Override
    public int getAccessFlags() {
        return method.getAccessFlags();
    }

    @Override
    public AttributeList getAttributes() {
        return method.getAttributes();
    }

    @Override
    public CstType getDefiningClass() {
        return method.getDefiningClass();
    }

    @Override
    public Prototype getEffectiveDescriptor() {
        return method.getEffectiveDescriptor();
    }

    public int getMaxStack() {
        return attCode.getMaxStack();
    }

    public int getMaxLocals() {
        return attCode.getMaxLocals();
    }

    public BytecodeArray getCode() {
        return attCode.getCode();
    }

    public ByteCatchList getCatches() {
        return attCode.getCatches();
    }

    public LineNumberList getLineNumbers() {
        return lineNumbers;
    }

    public LocalVariableList getLocalVariables() {
        return localVariables;
    }

    public mod.agus.jcoderz.dx.rop.code.SourcePosition makeSourcePosistion(int offset) {
        return new SourcePosition(getSourceFile(), offset,
                                  lineNumbers.pcToLine(offset));
    }
}
