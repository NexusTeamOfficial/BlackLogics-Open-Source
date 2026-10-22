
package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.rop.type.Type;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class CstType extends TypedConstant {

    private static final ConcurrentMap<Type, CstType> interns =
            new ConcurrentHashMap<>(1_000, 0.75f);

    public static final CstType OBJECT = new CstType(Type.OBJECT);

    public static final CstType BOOLEAN = new CstType(Type.BOOLEAN_CLASS);

    public static final CstType BYTE = new CstType(Type.BYTE_CLASS);

    public static final CstType CHARACTER = new CstType(Type.CHARACTER_CLASS);

    public static final CstType DOUBLE = new CstType(Type.DOUBLE_CLASS);

    public static final CstType FLOAT = new CstType(Type.FLOAT_CLASS);

    public static final CstType LONG = new CstType(Type.LONG_CLASS);

    public static final CstType INTEGER = new CstType(Type.INTEGER_CLASS);

    public static final CstType SHORT = new CstType(Type.SHORT_CLASS);

    public static final CstType VOID = new CstType(Type.VOID_CLASS);

    public static final CstType BOOLEAN_ARRAY = new CstType(Type.BOOLEAN_ARRAY);

    public static final CstType BYTE_ARRAY = new CstType(Type.BYTE_ARRAY);

    public static final CstType CHAR_ARRAY = new CstType(Type.CHAR_ARRAY);

    public static final CstType DOUBLE_ARRAY = new CstType(Type.DOUBLE_ARRAY);

    public static final CstType FLOAT_ARRAY = new CstType(Type.FLOAT_ARRAY);

    public static final CstType LONG_ARRAY = new CstType(Type.LONG_ARRAY);

    public static final CstType INT_ARRAY = new CstType(Type.INT_ARRAY);

    public static final CstType SHORT_ARRAY = new CstType(Type.SHORT_ARRAY);

    public static final CstType METHOD_HANDLE = new CstType(Type.METHOD_HANDLE);

    public static final CstType VAR_HANDLE = new CstType(Type.VAR_HANDLE);

    static {
        initInterns();
    }

    private static void initInterns() {
        internInitial(OBJECT);
        internInitial(BOOLEAN);
        internInitial(BYTE);
        internInitial(CHARACTER);
        internInitial(DOUBLE);
        internInitial(FLOAT);
        internInitial(LONG);
        internInitial(INTEGER);
        internInitial(SHORT);
        internInitial(VOID);
        internInitial(BOOLEAN_ARRAY);
        internInitial(BYTE_ARRAY);
        internInitial(CHAR_ARRAY);
        internInitial(DOUBLE_ARRAY);
        internInitial(FLOAT_ARRAY);
        internInitial(LONG_ARRAY);
        internInitial(INT_ARRAY);
        internInitial(SHORT_ARRAY);
        internInitial(METHOD_HANDLE);
    }

    private static void internInitial(CstType cst) {
        if (interns.putIfAbsent(cst.getClassType(), cst) != null) {
            throw new IllegalStateException("Attempted re-init of " + cst);
        }
    }


    private final Type type;

    private CstString descriptor;

    public static CstType forBoxedPrimitiveType(Type primitiveType) {
        switch (primitiveType.getBasicType()) {
            case Type.BT_BOOLEAN: return BOOLEAN;
            case Type.BT_BYTE:    return BYTE;
            case Type.BT_CHAR:    return CHARACTER;
            case Type.BT_DOUBLE:  return DOUBLE;
            case Type.BT_FLOAT:   return FLOAT;
            case Type.BT_INT:     return INTEGER;
            case Type.BT_LONG:    return LONG;
            case Type.BT_SHORT:   return SHORT;
            case Type.BT_VOID:    return VOID;
        }

        throw new IllegalArgumentException("not primitive: " + primitiveType);
    }

    public static CstType intern(Type type) {
        CstType cst = new CstType(type);
        CstType result = interns.putIfAbsent(type, cst);
        return result != null ? result : cst;
    }

    public CstType(Type type) {
        if (type == null) {
            throw new NullPointerException("type == null");
        }

        if (type == Type.KNOWN_NULL) {
            throw new UnsupportedOperationException(
                    "KNOWN_NULL is not representable");
        }

        this.type = type;
        this.descriptor = null;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof CstType)) {
            return false;
        }

        return type == ((CstType) other).type;
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    protected int compareTo0(Constant other) {
        String thisDescriptor = type.getDescriptor();
        String otherDescriptor = ((CstType) other).type.getDescriptor();
        return thisDescriptor.compareTo(otherDescriptor);
    }

    @Override
    public String toString() {
        return "type{" + toHuman() + '}';
    }

    @Override
    public Type getType() {
        return Type.CLASS;
    }

    @Override
    public String typeName() {
        return "type";
    }

    @Override
    public boolean isCategory2() {
        return false;
    }

    @Override
    public String toHuman() {
        return type.toHuman();
    }

    public Type getClassType() {
        return type;
    }

    public CstString getDescriptor() {
        if (descriptor == null) {
            descriptor = new CstString(type.getDescriptor());
        }

        return descriptor;
    }

    public String getPackageName() {

        String descriptor = getDescriptor().getString();
        int lastSlash = descriptor.lastIndexOf('/');
        int lastLeftSquare = descriptor.lastIndexOf('[');
        if (lastSlash == -1) {
            return "default";
        } else {

            return descriptor.substring(lastLeftSquare + 2, lastSlash).replace('/', '.');
        }
    }

    public static void clearInternTable() {
        interns.clear();
        initInterns();
    }

}
