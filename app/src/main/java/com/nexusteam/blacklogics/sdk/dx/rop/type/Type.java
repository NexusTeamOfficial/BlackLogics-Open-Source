
package mod.agus.jcoderz.dx.rop.type;

import mod.agus.jcoderz.dx.util.Hex;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class Type implements TypeBearer, Comparable<Type> {
    private static final ConcurrentMap<String, Type> internTable =
            new ConcurrentHashMap<>(10_000, 0.75f);


    public static final int BT_VOID = 0;

    public static final int BT_BOOLEAN = 1;

    public static final int BT_BYTE = 2;

    public static final int BT_CHAR = 3;

    public static final int BT_DOUBLE = 4;

    public static final int BT_FLOAT = 5;

    public static final int BT_INT = 6;

    public static final int BT_LONG = 7;

    public static final int BT_SHORT = 8;

    public static final int BT_OBJECT = 9;

    public static final int BT_ADDR = 10;

    public static final int BT_COUNT = 11;

    public static final Type BOOLEAN = new Type("Z", BT_BOOLEAN);

    public static final Type BYTE = new Type("B", BT_BYTE);

    public static final Type CHAR = new Type("C", BT_CHAR);

    public static final Type DOUBLE = new Type("D", BT_DOUBLE);

    public static final Type FLOAT = new Type("F", BT_FLOAT);

    public static final Type INT = new Type("I", BT_INT);

    public static final Type LONG = new Type("J", BT_LONG);

    public static final Type SHORT = new Type("S", BT_SHORT);

    public static final Type VOID = new Type("V", BT_VOID);

    public static final Type KNOWN_NULL = new Type("<null>", BT_OBJECT);

    public static final Type RETURN_ADDRESS = new Type("<addr>", BT_ADDR);


    public static final Type ANNOTATION =
        new Type("Ljava/lang/annotation/Annotation;", BT_OBJECT);

    public static final Type CLASS = new Type("Ljava/lang/Class;", BT_OBJECT);

    public static final Type CLONEABLE = new Type("Ljava/lang/Cloneable;", BT_OBJECT);

    public static final Type METHOD_HANDLE = new Type("Ljava/lang/invoke/MethodHandle;", BT_OBJECT);

    public static final Type METHOD_TYPE = new Type("Ljava/lang/invoke/MethodType;", BT_OBJECT);

    public static final Type VAR_HANDLE = new Type("Ljava/lang/invoke/VarHandle;", BT_OBJECT);

    public static final Type OBJECT = new Type("Ljava/lang/Object;", BT_OBJECT);

    public static final Type SERIALIZABLE = new Type("Ljava/io/Serializable;", BT_OBJECT);

    public static final Type STRING = new Type("Ljava/lang/String;", BT_OBJECT);

    public static final Type THROWABLE = new Type("Ljava/lang/Throwable;", BT_OBJECT);

    public static final Type BOOLEAN_CLASS = new Type("Ljava/lang/Boolean;", BT_OBJECT);

    public static final Type BYTE_CLASS = new Type("Ljava/lang/Byte;", BT_OBJECT);

    public static final Type CHARACTER_CLASS = new Type("Ljava/lang/Character;", BT_OBJECT);

    public static final Type DOUBLE_CLASS = new Type("Ljava/lang/Double;", BT_OBJECT);

    public static final Type FLOAT_CLASS = new Type("Ljava/lang/Float;", BT_OBJECT);

    public static final Type INTEGER_CLASS = new Type("Ljava/lang/Integer;", BT_OBJECT);

    public static final Type LONG_CLASS = new Type("Ljava/lang/Long;", BT_OBJECT);

    public static final Type SHORT_CLASS = new Type("Ljava/lang/Short;", BT_OBJECT);

    public static final Type VOID_CLASS = new Type("Ljava/lang/Void;", BT_OBJECT);

    public static final Type BOOLEAN_ARRAY = new Type("[" + BOOLEAN.descriptor, BT_OBJECT);

    public static final Type BYTE_ARRAY = new Type("[" + BYTE.descriptor, BT_OBJECT);

    public static final Type CHAR_ARRAY = new Type("[" + CHAR.descriptor, BT_OBJECT);

    public static final Type DOUBLE_ARRAY = new Type("[" + DOUBLE.descriptor, BT_OBJECT);

    public static final Type FLOAT_ARRAY = new Type("[" + FLOAT.descriptor, BT_OBJECT);;

    public static final Type INT_ARRAY = new Type("[" + INT.descriptor, BT_OBJECT);

    public static final Type LONG_ARRAY = new Type("[" + LONG.descriptor, BT_OBJECT);

    public static final Type OBJECT_ARRAY = new Type("[" + OBJECT.descriptor, BT_OBJECT);

    public static final Type SHORT_ARRAY = new Type("[" + SHORT.descriptor, BT_OBJECT);

    static {
        initInterns();
    }

    private static void initInterns() {
        putIntern(BOOLEAN);
        putIntern(BYTE);
        putIntern(CHAR);
        putIntern(DOUBLE);
        putIntern(FLOAT);
        putIntern(INT);
        putIntern(LONG);
        putIntern(SHORT);

        putIntern(ANNOTATION);
        putIntern(CLASS);
        putIntern(CLONEABLE);
        putIntern(METHOD_HANDLE);
        putIntern(VAR_HANDLE);
        putIntern(OBJECT);
        putIntern(SERIALIZABLE);
        putIntern(STRING);
        putIntern(THROWABLE);
        putIntern(BOOLEAN_CLASS);
        putIntern(BYTE_CLASS);
        putIntern(CHARACTER_CLASS);
        putIntern(DOUBLE_CLASS);
        putIntern(FLOAT_CLASS);
        putIntern(INTEGER_CLASS);
        putIntern(LONG_CLASS);
        putIntern(SHORT_CLASS);
        putIntern(VOID_CLASS);


        putIntern(BOOLEAN_ARRAY);
        putIntern(BYTE_ARRAY);
        putIntern(CHAR_ARRAY);
        putIntern(DOUBLE_ARRAY);
        putIntern(FLOAT_ARRAY);
        putIntern(INT_ARRAY);
        putIntern(LONG_ARRAY);
        putIntern(OBJECT_ARRAY);
        putIntern(SHORT_ARRAY);
    }


    private final String descriptor;

    private final int basicType;

    private final int newAt;

    private String className;

    private Type arrayType;

    private Type componentType;

    private Type initializedType;

    public static Type intern(String descriptor) {
        Type result = internTable.get(descriptor);

        if (result != null) {
            return result;
        }

        char firstChar;
        try {
            firstChar = descriptor.charAt(0);
        } catch (IndexOutOfBoundsException ex) {

            throw new IllegalArgumentException("descriptor is empty");
        } catch (NullPointerException ex) {

            throw new NullPointerException("descriptor == null");
        }

        if (firstChar == '[') {
            result = intern(descriptor.substring(1));
            return result.getArrayType();
        }


        int length = descriptor.length();
        if ((firstChar != 'L') ||
            (descriptor.charAt(length - 1) != ';')) {
            throw new IllegalArgumentException("bad descriptor: " + descriptor);
        }


        int limit = (length - 1);
        for (int i = 1; i < limit; i++) {
            char c = descriptor.charAt(i);
            switch (c) {
                case '[':
                case ';':
                case '.':
                case '(':
                case ')': {
                    throw new IllegalArgumentException("bad descriptor: " + descriptor);
                }
                case '/': {
                    if ((i == 1) ||
                        (i == (length - 1)) ||
                        (descriptor.charAt(i - 1) == '/')) {
                        throw new IllegalArgumentException("bad descriptor: " + descriptor);
                    }
                    break;
                }
            }
        }

        result = new Type(descriptor, BT_OBJECT);
        return putIntern(result);
    }

    public static Type internReturnType(String descriptor) {
        try {
            if (descriptor.equals("V")) {

                return VOID;
            }
        } catch (NullPointerException ex) {

            throw new NullPointerException("descriptor == null");
        }

        return intern(descriptor);
    }

    public static Type internClassName(String name) {
        if (name == null) {
            throw new NullPointerException("name == null");
        }

        if (name.startsWith("[")) {
            return intern(name);
        }

        return intern('L' + name + ';');
    }

    private Type(String descriptor, int basicType, int newAt) {
        if (descriptor == null) {
            throw new NullPointerException("descriptor == null");
        }

        if ((basicType < 0) || (basicType >= BT_COUNT)) {
            throw new IllegalArgumentException("bad basicType");
        }

        if (newAt < -1) {
            throw new IllegalArgumentException("newAt < -1");
        }

        this.descriptor = descriptor;
        this.basicType = basicType;
        this.newAt = newAt;
        this.arrayType = null;
        this.componentType = null;
        this.initializedType = null;
    }

    private Type(String descriptor, int basicType) {
        this(descriptor, basicType, -1);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Type)) {
            return false;
        }

        return descriptor.equals(((Type) other).descriptor);
    }

    @Override
    public int hashCode() {
        return descriptor.hashCode();
    }

    @Override
    public int compareTo(Type other) {
        return descriptor.compareTo(other.descriptor);
    }

    @Override
    public String toString() {
        return descriptor;
    }

    @Override
    public String toHuman() {
        switch (basicType) {
            case BT_VOID:    return "void";
            case BT_BOOLEAN: return "boolean";
            case BT_BYTE:    return "byte";
            case BT_CHAR:    return "char";
            case BT_DOUBLE:  return "double";
            case BT_FLOAT:   return "float";
            case BT_INT:     return "int";
            case BT_LONG:    return "long";
            case BT_SHORT:   return "short";
            case BT_OBJECT:  break;
            default:         return descriptor;
        }

        if (isArray()) {
            return getComponentType().toHuman() + "[]";
        }


        return getClassName().replace("/", ".");
    }

    @Override
    public Type getType() {
        return this;
    }

    @Override
    public Type getFrameType() {
        switch (basicType) {
            case BT_BOOLEAN:
            case BT_BYTE:
            case BT_CHAR:
            case BT_INT:
            case BT_SHORT: {
                return INT;
            }
        }

        return this;
    }

    @Override
    public int getBasicType() {
        return basicType;
    }

    @Override
    public int getBasicFrameType() {
        switch (basicType) {
            case BT_BOOLEAN:
            case BT_BYTE:
            case BT_CHAR:
            case BT_INT:
            case BT_SHORT: {
                return BT_INT;
            }
        }

        return basicType;
    }

    @Override
    public boolean isConstant() {
        return false;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public String getClassName() {
        if (className == null) {
            if (!isReference()) {
                throw new IllegalArgumentException("not an object type: " +
                                                   descriptor);
            }

            if (descriptor.charAt(0) == '[') {
                className = descriptor;
            } else {
                className = descriptor.substring(1, descriptor.length() - 1);
            }
        }

        return className;
    }

    public int getCategory() {
        switch (basicType) {
            case BT_LONG:
            case BT_DOUBLE: {
                return 2;
            }
        }

        return 1;
    }

    public boolean isCategory1() {
        switch (basicType) {
            case BT_LONG:
            case BT_DOUBLE: {
                return false;
            }
        }

        return true;
    }

    public boolean isCategory2() {
        switch (basicType) {
            case BT_LONG:
            case BT_DOUBLE: {
                return true;
            }
        }

        return false;
    }

    public boolean isIntlike() {
        switch (basicType) {
            case BT_BOOLEAN:
            case BT_BYTE:
            case BT_CHAR:
            case BT_INT:
            case BT_SHORT: {
                return true;
            }
        }

        return false;
    }

    public boolean isPrimitive() {
        switch (basicType) {
            case BT_BOOLEAN:
            case BT_BYTE:
            case BT_CHAR:
            case BT_DOUBLE:
            case BT_FLOAT:
            case BT_INT:
            case BT_LONG:
            case BT_SHORT:
            case BT_VOID: {
                return true;
            }
        }

        return false;
    }

    public boolean isReference() {
        return (basicType == BT_OBJECT);
    }

    public boolean isArray() {
        return (descriptor.charAt(0) == '[');
    }

    public boolean isArrayOrKnownNull() {
        return isArray() || equals(KNOWN_NULL);
    }

    public boolean isUninitialized() {
        return (newAt >= 0);
    }

    public int getNewAt() {
        return newAt;
    }

    public Type getInitializedType() {
        if (initializedType == null) {
            throw new IllegalArgumentException("initialized type: " +
                                               descriptor);
        }

        return initializedType;
    }

    public Type getArrayType() {
        if (arrayType == null) {
            arrayType = putIntern(new Type('[' + descriptor, BT_OBJECT));
        }

        return arrayType;
    }

    public Type getComponentType() {
        if (componentType == null) {
            if (descriptor.charAt(0) != '[') {
                throw new IllegalArgumentException("not an array type: " +
                                                   descriptor);
            }
            componentType = intern(descriptor.substring(1));
        }

        return componentType;
    }

    public Type asUninitialized(int newAt) {
        if (newAt < 0) {
            throw new IllegalArgumentException("newAt < 0");
        }

        if (!isReference()) {
            throw new IllegalArgumentException("not a reference type: " +
                                               descriptor);
        }

        if (isUninitialized()) {
            throw new IllegalArgumentException("already uninitialized: " +
                                               descriptor);
        }

        String newDesc = 'N' + Hex.u2(newAt) + descriptor;
        Type result = new Type(newDesc, BT_OBJECT, newAt);
        result.initializedType = this;
        return putIntern(result);
    }

    private static Type putIntern(Type type) {
        Type result = internTable.putIfAbsent(type.getDescriptor(), type);
        return result != null ? result : type;
    }

    public static void clearInternTable() {
        internTable.clear();
        initInterns();
    }
}
