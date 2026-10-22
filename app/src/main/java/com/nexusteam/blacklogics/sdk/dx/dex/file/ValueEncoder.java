
package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dex.EncodedValueCodec;
import mod.agus.jcoderz.dx.rop.annotation.Annotation;
import mod.agus.jcoderz.dx.rop.annotation.NameValuePair;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstAnnotation;
import mod.agus.jcoderz.dx.rop.cst.CstArray;
import mod.agus.jcoderz.dx.rop.cst.CstBoolean;
import mod.agus.jcoderz.dx.rop.cst.CstByte;
import mod.agus.jcoderz.dx.rop.cst.CstChar;
import mod.agus.jcoderz.dx.rop.cst.CstDouble;
import mod.agus.jcoderz.dx.rop.cst.CstEnumRef;
import mod.agus.jcoderz.dx.rop.cst.CstFieldRef;
import mod.agus.jcoderz.dx.rop.cst.CstFloat;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;
import mod.agus.jcoderz.dx.rop.cst.CstKnownNull;
import mod.agus.jcoderz.dx.rop.cst.CstLiteralBits;
import mod.agus.jcoderz.dx.rop.cst.CstLong;
import mod.agus.jcoderz.dx.rop.cst.CstMethodHandle;
import mod.agus.jcoderz.dx.rop.cst.CstMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstProtoRef;
import mod.agus.jcoderz.dx.rop.cst.CstShort;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

import java.util.Collection;

public final class ValueEncoder {
    private static final int VALUE_BYTE = 0x00;

    private static final int VALUE_SHORT = 0x02;

    private static final int VALUE_CHAR = 0x03;

    private static final int VALUE_INT = 0x04;

    private static final int VALUE_LONG = 0x06;

    private static final int VALUE_FLOAT = 0x10;

    private static final int VALUE_DOUBLE = 0x11;

    private static final int VALUE_METHOD_TYPE = 0x15;

    private static final int VALUE_METHOD_HANDLE = 0x16;

    private static final int VALUE_STRING = 0x17;

    private static final int VALUE_TYPE = 0x18;

    private static final int VALUE_FIELD = 0x19;

    private static final int VALUE_METHOD = 0x1a;

    private static final int VALUE_ENUM = 0x1b;

    private static final int VALUE_ARRAY = 0x1c;

    private static final int VALUE_ANNOTATION = 0x1d;

    private static final int VALUE_NULL = 0x1e;

    private static final int VALUE_BOOLEAN = 0x1f;

    private final DexFile file;

    private final mod.agus.jcoderz.dx.util.AnnotatedOutput out;

    public ValueEncoder(DexFile file, AnnotatedOutput out) {
        if (file == null) {
            throw new NullPointerException("file == null");
        }

        if (out == null) {
            throw new NullPointerException("out == null");
        }

        this.file = file;
        this.out = out;
    }

    public void writeConstant(mod.agus.jcoderz.dx.rop.cst.Constant cst) {
        int type = constantToValueType(cst);
        int arg;

        switch (type) {
            case VALUE_BYTE:
            case VALUE_SHORT:
            case VALUE_INT:
            case VALUE_LONG: {
                long value = ((mod.agus.jcoderz.dx.rop.cst.CstLiteralBits) cst).getLongBits();
                EncodedValueCodec.writeSignedIntegralValue(out, type, value);
                break;
            }
            case VALUE_CHAR: {
                long value = ((CstLiteralBits) cst).getLongBits();
                EncodedValueCodec.writeUnsignedIntegralValue(out, type, value);
                break;
            }
            case VALUE_FLOAT: {

                long value = ((mod.agus.jcoderz.dx.rop.cst.CstFloat) cst).getLongBits() << 32;
                EncodedValueCodec.writeRightZeroExtendedValue(out, type, value);
                break;
            }
            case VALUE_DOUBLE: {
                long value = ((mod.agus.jcoderz.dx.rop.cst.CstDouble) cst).getLongBits();
                EncodedValueCodec.writeRightZeroExtendedValue(out, type, value);
                break;
            }
            case VALUE_METHOD_TYPE: {
                int index = file.getProtoIds().indexOf(((mod.agus.jcoderz.dx.rop.cst.CstProtoRef) cst).getPrototype());
                EncodedValueCodec.writeUnsignedIntegralValue(out, type, (long) index);
                break;
            }
            case VALUE_METHOD_HANDLE: {
                int index = file.getMethodHandles().indexOf((mod.agus.jcoderz.dx.rop.cst.CstMethodHandle) cst);
                EncodedValueCodec.writeUnsignedIntegralValue(out, type, (long) index);
                break;
            }
            case VALUE_STRING: {
                int index = file.getStringIds().indexOf((mod.agus.jcoderz.dx.rop.cst.CstString) cst);
                EncodedValueCodec.writeUnsignedIntegralValue(out, type, (long) index);
                break;
            }
            case VALUE_TYPE: {
                int index = file.getTypeIds().indexOf((mod.agus.jcoderz.dx.rop.cst.CstType) cst);
                EncodedValueCodec.writeUnsignedIntegralValue(out, type, (long) index);
                break;
            }
            case VALUE_FIELD: {
                int index = file.getFieldIds().indexOf((mod.agus.jcoderz.dx.rop.cst.CstFieldRef) cst);
                EncodedValueCodec.writeUnsignedIntegralValue(out, type, (long) index);
                break;
            }
            case VALUE_METHOD: {
                int index = file.getMethodIds().indexOf((mod.agus.jcoderz.dx.rop.cst.CstMethodRef) cst);
                EncodedValueCodec.writeUnsignedIntegralValue(out, type, (long) index);
                break;
            }
            case VALUE_ENUM: {
                mod.agus.jcoderz.dx.rop.cst.CstFieldRef fieldRef = ((mod.agus.jcoderz.dx.rop.cst.CstEnumRef) cst).getFieldRef();
                int index = file.getFieldIds().indexOf(fieldRef);
                EncodedValueCodec.writeUnsignedIntegralValue(out, type, (long) index);
                break;
            }
            case VALUE_ARRAY: {
                out.writeByte(type);
                writeArray((mod.agus.jcoderz.dx.rop.cst.CstArray) cst, false);
                break;
            }
            case VALUE_ANNOTATION: {
                out.writeByte(type);
                writeAnnotation(((mod.agus.jcoderz.dx.rop.cst.CstAnnotation) cst).getAnnotation(),
                        false);
                break;
            }
            case VALUE_NULL: {
                out.writeByte(type);
                break;
            }
            case VALUE_BOOLEAN: {
                int value = ((mod.agus.jcoderz.dx.rop.cst.CstBoolean) cst).getIntBits();
                out.writeByte(type | (value << 5));
                break;
            }
            default: {
                throw new RuntimeException("Shouldn't happen");
            }
        }
    }

    private static int constantToValueType(mod.agus.jcoderz.dx.rop.cst.Constant cst) {
        if (cst instanceof CstByte) {
            return VALUE_BYTE;
        } else if (cst instanceof CstShort) {
            return VALUE_SHORT;
        } else if (cst instanceof CstChar) {
            return VALUE_CHAR;
        } else if (cst instanceof CstInteger) {
            return VALUE_INT;
        } else if (cst instanceof CstLong) {
            return VALUE_LONG;
        } else if (cst instanceof CstFloat) {
            return VALUE_FLOAT;
        } else if (cst instanceof CstDouble) {
            return VALUE_DOUBLE;
        } else if (cst instanceof CstProtoRef) {
            return VALUE_METHOD_TYPE;
        } else if (cst instanceof CstMethodHandle) {
           return VALUE_METHOD_HANDLE;
        } else if (cst instanceof mod.agus.jcoderz.dx.rop.cst.CstString) {
            return VALUE_STRING;
        } else if (cst instanceof mod.agus.jcoderz.dx.rop.cst.CstType) {
            return VALUE_TYPE;
        } else if (cst instanceof CstFieldRef) {
            return VALUE_FIELD;
        } else if (cst instanceof CstMethodRef) {
            return VALUE_METHOD;
        } else if (cst instanceof CstEnumRef) {
            return VALUE_ENUM;
        } else if (cst instanceof mod.agus.jcoderz.dx.rop.cst.CstArray) {
            return VALUE_ARRAY;
        } else if (cst instanceof mod.agus.jcoderz.dx.rop.cst.CstAnnotation) {
            return VALUE_ANNOTATION;
        } else if (cst instanceof CstKnownNull) {
            return VALUE_NULL;
        } else if (cst instanceof CstBoolean) {
            return VALUE_BOOLEAN;
        } else {
            throw new RuntimeException("Shouldn't happen");
        }
    }

    public void writeArray(mod.agus.jcoderz.dx.rop.cst.CstArray array, boolean topLevel) {
        boolean annotates = topLevel && out.annotates();
        mod.agus.jcoderz.dx.rop.cst.CstArray.List list = ((mod.agus.jcoderz.dx.rop.cst.CstArray) array).getList();
        int size = list.size();

        if (annotates) {
            out.annotate("  size: " + mod.agus.jcoderz.dx.util.Hex.u4(size));
        }

        out.writeUleb128(size);

        for (int i = 0; i < size; i++) {
            mod.agus.jcoderz.dx.rop.cst.Constant cst = list.get(i);
            if (annotates) {
                out.annotate("  [" + Integer.toHexString(i) + "] " +
                        constantToHuman(cst));
            }
            writeConstant(cst);
        }

        if (annotates) {
            out.endAnnotation();
        }
    }

    public void writeAnnotation(mod.agus.jcoderz.dx.rop.annotation.Annotation annotation, boolean topLevel) {
        boolean annotates = topLevel && out.annotates();
        mod.agus.jcoderz.dx.dex.file.StringIdsSection stringIds = file.getStringIds();
        TypeIdsSection typeIds = file.getTypeIds();

        CstType type = annotation.getType();
        int typeIdx = typeIds.indexOf(type);

        if (annotates) {
            out.annotate("  type_idx: " + mod.agus.jcoderz.dx.util.Hex.u4(typeIdx) + " // " +
                    type.toHuman());
        }

        out.writeUleb128(typeIds.indexOf(annotation.getType()));

        Collection<mod.agus.jcoderz.dx.rop.annotation.NameValuePair> pairs = annotation.getNameValuePairs();
        int size = pairs.size();

        if (annotates) {
            out.annotate("  size: " + mod.agus.jcoderz.dx.util.Hex.u4(size));
        }

        out.writeUleb128(size);

        int at = 0;
        for (mod.agus.jcoderz.dx.rop.annotation.NameValuePair pair : pairs) {
            CstString name = pair.getName();
            int nameIdx = stringIds.indexOf(name);
            mod.agus.jcoderz.dx.rop.cst.Constant value = pair.getValue();

            if (annotates) {
                out.annotate(0, "  elements[" + at + "]:");
                at++;
                out.annotate("    name_idx: " + Hex.u4(nameIdx) + " // " +
                        name.toHuman());
            }

            out.writeUleb128(nameIdx);

            if (annotates) {
                out.annotate("    value: " + constantToHuman(value));
            }

            writeConstant(value);
        }

        if (annotates) {
            out.endAnnotation();
        }
    }

    public static String constantToHuman(mod.agus.jcoderz.dx.rop.cst.Constant cst) {
        int type = constantToValueType(cst);

        if (type == VALUE_NULL) {
            return "null";
        }

        StringBuilder sb = new StringBuilder();

        sb.append(cst.typeName());
        sb.append(' ');
        sb.append(cst.toHuman());

        return sb.toString();
    }

    public static void addContents(DexFile file, mod.agus.jcoderz.dx.rop.annotation.Annotation annotation) {
        TypeIdsSection typeIds = file.getTypeIds();
        StringIdsSection stringIds = file.getStringIds();

        typeIds.intern(annotation.getType());

        for (NameValuePair pair : annotation.getNameValuePairs()) {
            stringIds.intern(pair.getName());
            addContents(file, pair.getValue());
        }
    }

    public static void addContents(DexFile file, Constant cst) {
        if (cst instanceof mod.agus.jcoderz.dx.rop.cst.CstAnnotation) {
            addContents(file, ((CstAnnotation) cst).getAnnotation());
        } else if (cst instanceof mod.agus.jcoderz.dx.rop.cst.CstArray) {
            mod.agus.jcoderz.dx.rop.cst.CstArray.List list = ((CstArray) cst).getList();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                addContents(file, list.get(i));
            }
        } else {
            file.internIfAppropriate(cst);
        }
    }
}
