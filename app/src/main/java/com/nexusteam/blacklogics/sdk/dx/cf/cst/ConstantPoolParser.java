
package mod.agus.jcoderz.dx.cf.cst;

import static mod.agus.jcoderz.dx.cf.cst.ConstantTags.CONSTANT_Class;
import static mod.agus.jcoderz.dx.cf.cst.ConstantTags.CONSTANT_Double;
import static mod.agus.jcoderz.dx.cf.cst.ConstantTags.CONSTANT_Fieldref;
import static mod.agus.jcoderz.dx.cf.cst.ConstantTags.CONSTANT_Float;
import static mod.agus.jcoderz.dx.cf.cst.ConstantTags.CONSTANT_Integer;
import static mod.agus.jcoderz.dx.cf.cst.ConstantTags.CONSTANT_InterfaceMethodref;
import static mod.agus.jcoderz.dx.cf.cst.ConstantTags.CONSTANT_InvokeDynamic;
import static mod.agus.jcoderz.dx.cf.cst.ConstantTags.CONSTANT_Long;
import static mod.agus.jcoderz.dx.cf.cst.ConstantTags.CONSTANT_MethodHandle;
import static mod.agus.jcoderz.dx.cf.cst.ConstantTags.CONSTANT_MethodType;
import static mod.agus.jcoderz.dx.cf.cst.ConstantTags.CONSTANT_Methodref;
import static mod.agus.jcoderz.dx.cf.cst.ConstantTags.CONSTANT_NameAndType;
import static mod.agus.jcoderz.dx.cf.cst.ConstantTags.CONSTANT_String;
import static mod.agus.jcoderz.dx.cf.cst.ConstantTags.CONSTANT_Utf8;

import mod.agus.jcoderz.dx.cf.iface.ParseException;
import mod.agus.jcoderz.dx.cf.iface.ParseObserver;

import java.util.BitSet;

import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstDouble;
import mod.agus.jcoderz.dx.rop.cst.CstFieldRef;
import mod.agus.jcoderz.dx.rop.cst.CstFloat;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;
import mod.agus.jcoderz.dx.rop.cst.CstInterfaceMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstInvokeDynamic;
import mod.agus.jcoderz.dx.rop.cst.CstLong;
import mod.agus.jcoderz.dx.rop.cst.CstMethodHandle;
import mod.agus.jcoderz.dx.rop.cst.CstMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstNat;
import mod.agus.jcoderz.dx.rop.cst.CstProtoRef;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.cst.StdConstantPool;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.ByteArray;
import mod.agus.jcoderz.dx.util.Hex;

public final class ConstantPoolParser {
    private final mod.agus.jcoderz.dx.util.ByteArray bytes;

    private final mod.agus.jcoderz.dx.rop.cst.StdConstantPool pool;

    private final int[] offsets;

    private int endOffset;

    private ParseObserver observer;

    public ConstantPoolParser(mod.agus.jcoderz.dx.util.ByteArray bytes) {
        int size = bytes.getUnsignedShort(8);

        this.bytes = bytes;
        this.pool = new mod.agus.jcoderz.dx.rop.cst.StdConstantPool(size);
        this.offsets = new int[size];
        this.endOffset = -1;
    }

    public void setObserver(ParseObserver observer) {
        this.observer = observer;
    }

    public int getEndOffset() {
        parseIfNecessary();
        return endOffset;
    }

    public StdConstantPool getPool() {
        parseIfNecessary();
        return pool;
    }

    private void parseIfNecessary() {
        if (endOffset < 0) {
            parse();
        }
    }

    private void parse() {
        determineOffsets();

        if (observer != null) {
            observer.parsed(bytes, 8, 2,
                            "constant_pool_count: " + mod.agus.jcoderz.dx.util.Hex.u2(offsets.length));
            observer.parsed(bytes, 10, 0, "\nconstant_pool:");
            observer.changeIndent(1);
        }

        BitSet wasUtf8 = new BitSet(offsets.length);

        for (int i = 1; i < offsets.length; i++) {
            int offset = offsets[i];
            if ((offset != 0) && (pool.getOrNull(i) == null)) {
                parse0(i, wasUtf8);
            }
        }

        if (observer != null) {
            for (int i = 1; i < offsets.length; i++) {
                mod.agus.jcoderz.dx.rop.cst.Constant cst = pool.getOrNull(i);
                if (cst == null) {
                    continue;
                }
                int offset = offsets[i];
                int nextOffset = endOffset;
                for (int j = i + 1; j < offsets.length; j++) {
                    int off = offsets[j];
                    if (off != 0) {
                        nextOffset = off;
                        break;
                    }
                }
                String human = wasUtf8.get(i)
                        ? mod.agus.jcoderz.dx.util.Hex.u2(i) + ": utf8{\"" + cst.toHuman() + "\"}"
                        : mod.agus.jcoderz.dx.util.Hex.u2(i) + ": " + cst.toString();
                observer.parsed(bytes, offset, nextOffset - offset, human);
            }

            observer.changeIndent(-1);
            observer.parsed(bytes, endOffset, 0, "end constant_pool");
        }
    }

    private void determineOffsets() {
        int at = 10;
        int lastCategory;

        for (int i = 1; i < offsets.length; i += lastCategory) {
            offsets[i] = at;
            int tag = bytes.getUnsignedByte(at);
            try {
                switch (tag) {
                    case CONSTANT_Integer:
                    case CONSTANT_Float:
                    case CONSTANT_Fieldref:
                    case CONSTANT_Methodref:
                    case CONSTANT_InterfaceMethodref:
                    case CONSTANT_NameAndType: {
                        lastCategory = 1;
                        at += 5;
                        break;
                    }
                    case CONSTANT_Long:
                    case CONSTANT_Double: {
                        lastCategory = 2;
                        at += 9;
                        break;
                    }
                    case CONSTANT_Class:
                    case CONSTANT_String: {
                        lastCategory = 1;
                        at += 3;
                        break;
                    }
                    case CONSTANT_Utf8: {
                        lastCategory = 1;
                        at += bytes.getUnsignedShort(at + 1) + 3;
                        break;
                    }
                    case CONSTANT_MethodHandle: {
                        lastCategory = 1;
                        at += 4;
                        break;
                    }
                    case CONSTANT_MethodType: {
                        lastCategory = 1;
                        at += 3;
                        break;
                    }
                    case CONSTANT_InvokeDynamic: {
                        lastCategory = 1;
                        at += 5;
                        break;
                    }
                    default: {
                        throw new ParseException("unknown tag byte: " + mod.agus.jcoderz.dx.util.Hex.u1(tag));
                    }
                }
            } catch (ParseException ex) {
                ex.addContext("...while preparsing cst " + mod.agus.jcoderz.dx.util.Hex.u2(i) + " at offset " + mod.agus.jcoderz.dx.util.Hex.u4(at));
                throw ex;
            }
        }

        endOffset = at;
    }

    private mod.agus.jcoderz.dx.rop.cst.Constant parse0(int idx, BitSet wasUtf8) {
        mod.agus.jcoderz.dx.rop.cst.Constant cst = pool.getOrNull(idx);
        if (cst != null) {
            return cst;
        }

        int at = offsets[idx];

        try {
            int tag = bytes.getUnsignedByte(at);
            switch (tag) {
                case CONSTANT_Utf8: {
                    cst = parseUtf8(at);
                    wasUtf8.set(idx);
                    break;
                }
                case CONSTANT_Integer: {
                    int value = bytes.getInt(at + 1);
                    cst = CstInteger.make(value);
                    break;
                }
                case CONSTANT_Float: {
                    int bits = bytes.getInt(at + 1);
                    cst = CstFloat.make(bits);
                    break;
                }
                case CONSTANT_Long: {
                    long value = bytes.getLong(at + 1);
                    cst = CstLong.make(value);
                    break;
                }
                case CONSTANT_Double: {
                    long bits = bytes.getLong(at + 1);
                    cst = CstDouble.make(bits);
                    break;
                }
                case CONSTANT_Class: {
                    int nameIndex = bytes.getUnsignedShort(at + 1);
                    mod.agus.jcoderz.dx.rop.cst.CstString name = (mod.agus.jcoderz.dx.rop.cst.CstString) parse0(nameIndex, wasUtf8);
                    cst = new mod.agus.jcoderz.dx.rop.cst.CstType(Type.internClassName(name.getString()));
                    break;
                }
                case CONSTANT_String: {
                    int stringIndex = bytes.getUnsignedShort(at + 1);
                    cst = parse0(stringIndex, wasUtf8);
                    break;
                }
                case CONSTANT_Fieldref: {
                    int classIndex = bytes.getUnsignedShort(at + 1);
                    mod.agus.jcoderz.dx.rop.cst.CstType type = (mod.agus.jcoderz.dx.rop.cst.CstType) parse0(classIndex, wasUtf8);
                    int natIndex = bytes.getUnsignedShort(at + 3);
                    mod.agus.jcoderz.dx.rop.cst.CstNat nat = (mod.agus.jcoderz.dx.rop.cst.CstNat) parse0(natIndex, wasUtf8);
                    cst = new mod.agus.jcoderz.dx.rop.cst.CstFieldRef(type, nat);
                    break;
                }
                case CONSTANT_Methodref: {
                    int classIndex = bytes.getUnsignedShort(at + 1);
                    mod.agus.jcoderz.dx.rop.cst.CstType type = (mod.agus.jcoderz.dx.rop.cst.CstType) parse0(classIndex, wasUtf8);
                    int natIndex = bytes.getUnsignedShort(at + 3);
                    mod.agus.jcoderz.dx.rop.cst.CstNat nat = (mod.agus.jcoderz.dx.rop.cst.CstNat) parse0(natIndex, wasUtf8);
                    cst = new mod.agus.jcoderz.dx.rop.cst.CstMethodRef(type, nat);
                    break;
                }
                case CONSTANT_InterfaceMethodref: {
                    int classIndex = bytes.getUnsignedShort(at + 1);
                    mod.agus.jcoderz.dx.rop.cst.CstType type = (CstType) parse0(classIndex, wasUtf8);
                    int natIndex = bytes.getUnsignedShort(at + 3);
                    mod.agus.jcoderz.dx.rop.cst.CstNat nat = (mod.agus.jcoderz.dx.rop.cst.CstNat) parse0(natIndex, wasUtf8);
                    cst = new mod.agus.jcoderz.dx.rop.cst.CstInterfaceMethodRef(type, nat);
                    break;
                }
                case CONSTANT_NameAndType: {
                    int nameIndex = bytes.getUnsignedShort(at + 1);
                    mod.agus.jcoderz.dx.rop.cst.CstString name = (mod.agus.jcoderz.dx.rop.cst.CstString) parse0(nameIndex, wasUtf8);
                    int descriptorIndex = bytes.getUnsignedShort(at + 3);
                    mod.agus.jcoderz.dx.rop.cst.CstString descriptor = (mod.agus.jcoderz.dx.rop.cst.CstString) parse0(descriptorIndex, wasUtf8);
                    cst = new mod.agus.jcoderz.dx.rop.cst.CstNat(name, descriptor);
                    break;
                }
                case CONSTANT_MethodHandle: {
                    final int kind = bytes.getUnsignedByte(at + 1);
                    final int constantIndex = bytes.getUnsignedShort(at + 2);
                    final Constant ref;
                    switch (kind) {
                        case mod.agus.jcoderz.dx.cf.cst.MethodHandleKind.REF_getField:
                        case mod.agus.jcoderz.dx.cf.cst.MethodHandleKind.REF_getStatic:
                        case mod.agus.jcoderz.dx.cf.cst.MethodHandleKind.REF_putField:
                        case mod.agus.jcoderz.dx.cf.cst.MethodHandleKind.REF_putStatic:
                            ref = (CstFieldRef) parse0(constantIndex, wasUtf8);
                            break;
                        case mod.agus.jcoderz.dx.cf.cst.MethodHandleKind.REF_invokeVirtual:
                        case mod.agus.jcoderz.dx.cf.cst.MethodHandleKind.REF_newInvokeSpecial:
                            ref = (mod.agus.jcoderz.dx.rop.cst.CstMethodRef) parse0(constantIndex, wasUtf8);
                            break;
                        case mod.agus.jcoderz.dx.cf.cst.MethodHandleKind.REF_invokeStatic:
                        case mod.agus.jcoderz.dx.cf.cst.MethodHandleKind.REF_invokeSpecial:
                            ref = parse0(constantIndex, wasUtf8);
                            if (!(ref instanceof CstMethodRef
                                || ref instanceof mod.agus.jcoderz.dx.rop.cst.CstInterfaceMethodRef)) {
                              throw new ParseException(
                                  "Unsupported ref constant type for MethodHandle "
                                  + ref.getClass());
                            }
                            break;
                        case mod.agus.jcoderz.dx.cf.cst.MethodHandleKind.REF_invokeInterface:
                            ref = (CstInterfaceMethodRef) parse0(constantIndex, wasUtf8);
                            break;
                        default:
                            throw new ParseException("Unsupported MethodHandle kind: " + kind);
                    }

                    final int methodHandleType = getMethodHandleTypeForKind(kind);
                    cst = mod.agus.jcoderz.dx.rop.cst.CstMethodHandle.make(methodHandleType, ref);
                    break;
                }
                case CONSTANT_MethodType: {
                    int descriptorIndex = bytes.getUnsignedShort(at + 1);
                    mod.agus.jcoderz.dx.rop.cst.CstString descriptor = (mod.agus.jcoderz.dx.rop.cst.CstString) parse0(descriptorIndex, wasUtf8);
                    cst = CstProtoRef.make(descriptor);
                    break;
                }
                case CONSTANT_InvokeDynamic: {
                    int bootstrapMethodIndex = bytes.getUnsignedShort(at + 1);
                    int natIndex = bytes.getUnsignedShort(at + 3);
                    mod.agus.jcoderz.dx.rop.cst.CstNat nat = (CstNat) parse0(natIndex, wasUtf8);
                    cst = CstInvokeDynamic.make(bootstrapMethodIndex, nat);
                    break;
                }
                default: {
                    throw new ParseException("unknown tag byte: " + mod.agus.jcoderz.dx.util.Hex.u1(tag));
                }
            }
        } catch (ParseException ex) {
            ex.addContext("...while parsing cst " + mod.agus.jcoderz.dx.util.Hex.u2(idx) +
                          " at offset " + mod.agus.jcoderz.dx.util.Hex.u4(at));
            throw ex;
        } catch (RuntimeException ex) {
            ParseException pe = new ParseException(ex);
            pe.addContext("...while parsing cst " + mod.agus.jcoderz.dx.util.Hex.u2(idx) +
                          " at offset " + Hex.u4(at));
            throw pe;
        }

        pool.set(idx, cst);
        return cst;
    }

    private mod.agus.jcoderz.dx.rop.cst.CstString parseUtf8(int at) {
        int length = bytes.getUnsignedShort(at + 1);

        at += 3;

        ByteArray ubytes = bytes.slice(at, at + length);

        try {
            return new CstString(ubytes);
        } catch (IllegalArgumentException ex) {

            throw new ParseException(ex);
        }
    }

    private static int getMethodHandleTypeForKind(int kind) {
        switch (kind) {
            case mod.agus.jcoderz.dx.cf.cst.MethodHandleKind.REF_getField:
                return mod.agus.jcoderz.dx.rop.cst.CstMethodHandle.METHOD_HANDLE_TYPE_INSTANCE_GET;
            case mod.agus.jcoderz.dx.cf.cst.MethodHandleKind.REF_getStatic:
                return mod.agus.jcoderz.dx.rop.cst.CstMethodHandle.METHOD_HANDLE_TYPE_STATIC_GET;
            case mod.agus.jcoderz.dx.cf.cst.MethodHandleKind.REF_putField:
                return mod.agus.jcoderz.dx.rop.cst.CstMethodHandle.METHOD_HANDLE_TYPE_INSTANCE_PUT;
            case mod.agus.jcoderz.dx.cf.cst.MethodHandleKind.REF_putStatic:
                return mod.agus.jcoderz.dx.rop.cst.CstMethodHandle.METHOD_HANDLE_TYPE_STATIC_PUT;
            case mod.agus.jcoderz.dx.cf.cst.MethodHandleKind.REF_invokeVirtual:
                return mod.agus.jcoderz.dx.rop.cst.CstMethodHandle.METHOD_HANDLE_TYPE_INVOKE_INSTANCE;
            case mod.agus.jcoderz.dx.cf.cst.MethodHandleKind.REF_invokeStatic:
                return mod.agus.jcoderz.dx.rop.cst.CstMethodHandle.METHOD_HANDLE_TYPE_INVOKE_STATIC;
            case mod.agus.jcoderz.dx.cf.cst.MethodHandleKind.REF_invokeSpecial:
                return mod.agus.jcoderz.dx.rop.cst.CstMethodHandle.METHOD_HANDLE_TYPE_INVOKE_DIRECT;
            case mod.agus.jcoderz.dx.cf.cst.MethodHandleKind.REF_newInvokeSpecial:
                return mod.agus.jcoderz.dx.rop.cst.CstMethodHandle.METHOD_HANDLE_TYPE_INVOKE_CONSTRUCTOR;
            case MethodHandleKind.REF_invokeInterface:
                return CstMethodHandle.METHOD_HANDLE_TYPE_INVOKE_INTERFACE;
        }
        throw new IllegalArgumentException("invalid kind: " + kind);
    }
}
