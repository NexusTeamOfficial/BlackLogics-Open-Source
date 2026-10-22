
package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.rop.type.Type;

public final class CstMethodHandle extends TypedConstant {

    public static final int METHOD_HANDLE_TYPE_STATIC_PUT = 0;
    public static final int METHOD_HANDLE_TYPE_STATIC_GET = 1;
    public static final int METHOD_HANDLE_TYPE_INSTANCE_PUT = 2;
    public static final int METHOD_HANDLE_TYPE_INSTANCE_GET = 3;

    public static final int METHOD_HANDLE_TYPE_INVOKE_STATIC = 4;
    public static final int METHOD_HANDLE_TYPE_INVOKE_INSTANCE = 5;
    public static final int METHOD_HANDLE_TYPE_INVOKE_CONSTRUCTOR = 6;
    public static final int METHOD_HANDLE_TYPE_INVOKE_DIRECT = 7;
    public static final int METHOD_HANDLE_TYPE_INVOKE_INTERFACE = 8;

    private static final String [] TYPE_NAMES = {
        "static-put", "static-get", "instance-put", "instance-get",
        "invoke-static", "invoke-instance", "invoke-constructor", "invoke-direct",
        "invoke-interface"
    };

    private final int type;

    private final Constant ref;

    public static CstMethodHandle make(int type, Constant ref) {
        if (isAccessor(type)) {
            if (!(ref instanceof CstFieldRef)) {
                throw new IllegalArgumentException("ref has wrong type: " + ref.getClass());
            }
        } else if (isInvocation(type)) {
            if (!(ref instanceof CstBaseMethodRef)) {
                throw new IllegalArgumentException("ref has wrong type: " + ref.getClass());
            }
        } else {
            throw new IllegalArgumentException("type is out of range: " + type);
        }
        return new CstMethodHandle(type, ref);
    }

    private CstMethodHandle(int type, Constant ref) {
        this.type = type;
        this.ref = ref;
    }

    public Constant getRef() {
        return ref;
    }

    public int getMethodHandleType() {
        return type;
    }

    public static boolean isAccessor(int type) {
        switch (type) {
            case METHOD_HANDLE_TYPE_STATIC_PUT:
            case METHOD_HANDLE_TYPE_STATIC_GET:
            case METHOD_HANDLE_TYPE_INSTANCE_PUT:
            case METHOD_HANDLE_TYPE_INSTANCE_GET:
                return true;
            default:
                return false;
        }
    }

    public boolean isAccessor() {
        return isAccessor(type);
    }

    public static boolean isInvocation(int type) {
        switch (type) {
            case METHOD_HANDLE_TYPE_INVOKE_STATIC:
            case METHOD_HANDLE_TYPE_INVOKE_INSTANCE:
            case METHOD_HANDLE_TYPE_INVOKE_CONSTRUCTOR:
            case METHOD_HANDLE_TYPE_INVOKE_DIRECT:
            case METHOD_HANDLE_TYPE_INVOKE_INTERFACE:
                return true;
            default:
                return false;
        }
    }

    public boolean isInvocation() {
        return isInvocation(type);
    }

    public static String getMethodHandleTypeName(final int type) {
        return TYPE_NAMES[type];
    }

    @Override
    public boolean isCategory2() {
        return false;
    }

    @Override
    protected int compareTo0(Constant other) {
        CstMethodHandle otherHandle = (CstMethodHandle) other;
        if (getMethodHandleType() == otherHandle.getMethodHandleType()) {
            return getRef().compareTo(otherHandle.getRef());
        } else {
            return Integer.compare(getMethodHandleType(), otherHandle.getMethodHandleType());
        }
    }

    @Override
    public String toString() {
        return "method-handle{" + toHuman() + "}";
    }

    @Override
    public String typeName() {
        return "method handle";
    }

    @Override
    public String toHuman() {
        return getMethodHandleTypeName(type)+ "," + ref.toString();
    }

    @Override
    public Type getType() {
        return Type.METHOD_HANDLE;
    }
}
