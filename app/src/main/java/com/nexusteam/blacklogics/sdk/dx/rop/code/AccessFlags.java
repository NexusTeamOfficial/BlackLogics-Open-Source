
package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.util.Hex;

public final class AccessFlags {
    public static final int ACC_PUBLIC = 0x0001;

    public static final int ACC_PRIVATE = 0x0002;

    public static final int ACC_PROTECTED = 0x0004;

    public static final int ACC_STATIC = 0x0008;

    public static final int ACC_FINAL = 0x0010;

    public static final int ACC_SYNCHRONIZED = 0x0020;

    public static final int ACC_SUPER = 0x0020;

    public static final int ACC_VOLATILE = 0x0040;

    public static final int ACC_BRIDGE = 0x0040;

    public static final int ACC_TRANSIENT = 0x0080;

    public static final int ACC_VARARGS = 0x0080;

    public static final int ACC_NATIVE = 0x0100;

    public static final int ACC_INTERFACE = 0x0200;

    public static final int ACC_ABSTRACT = 0x0400;

    public static final int ACC_STRICT = 0x0800;

    public static final int ACC_SYNTHETIC = 0x1000;

    public static final int ACC_ANNOTATION = 0x2000;

    public static final int ACC_ENUM = 0x4000;

    public static final int ACC_CONSTRUCTOR = 0x10000;

    public static final int ACC_DECLARED_SYNCHRONIZED = 0x20000;

    public static final int CLASS_FLAGS =
        ACC_PUBLIC | ACC_FINAL | ACC_SUPER | ACC_INTERFACE | ACC_ABSTRACT |
        ACC_SYNTHETIC | ACC_ANNOTATION | ACC_ENUM;

    public static final int INNER_CLASS_FLAGS =
        ACC_PUBLIC | ACC_PRIVATE | ACC_PROTECTED | ACC_STATIC | ACC_FINAL |
        ACC_INTERFACE | ACC_ABSTRACT | ACC_SYNTHETIC | ACC_ANNOTATION |
        ACC_ENUM;

    public static final int FIELD_FLAGS =
        ACC_PUBLIC | ACC_PRIVATE | ACC_PROTECTED | ACC_STATIC | ACC_FINAL |
        ACC_VOLATILE | ACC_TRANSIENT | ACC_SYNTHETIC | ACC_ENUM;

    public static final int METHOD_FLAGS =
        ACC_PUBLIC | ACC_PRIVATE | ACC_PROTECTED | ACC_STATIC | ACC_FINAL |
        ACC_SYNCHRONIZED | ACC_BRIDGE | ACC_VARARGS | ACC_NATIVE |
        ACC_ABSTRACT | ACC_STRICT | ACC_SYNTHETIC | ACC_CONSTRUCTOR |
        ACC_DECLARED_SYNCHRONIZED;

    private static final int CONV_CLASS = 1;

    private static final int CONV_FIELD = 2;

    private static final int CONV_METHOD = 3;

    private AccessFlags() {

    }

    public static String classString(int flags) {
        return humanHelper(flags, CLASS_FLAGS, CONV_CLASS);
    }

    public static String innerClassString(int flags) {
        return humanHelper(flags, INNER_CLASS_FLAGS, CONV_CLASS);
    }

    public static String fieldString(int flags) {
        return humanHelper(flags, FIELD_FLAGS, CONV_FIELD);
    }

    public static String methodString(int flags) {
        return humanHelper(flags, METHOD_FLAGS, CONV_METHOD);
    }

    public static boolean isPublic(int flags) {
        return (flags & ACC_PUBLIC) != 0;
    }

    public static boolean isProtected(int flags) {
        return (flags & ACC_PROTECTED) != 0;
    }

    public static boolean isPrivate(int flags) {
        return (flags & ACC_PRIVATE) != 0;
    }

    public static boolean isStatic(int flags) {
        return (flags & ACC_STATIC) != 0;
    }

    public static boolean isConstructor(int flags) {
        return (flags & ACC_CONSTRUCTOR) != 0;
    }

    public static boolean isInterface(int flags) {
        return (flags & ACC_INTERFACE) != 0;
    }

    public static boolean isSynchronized(int flags) {
        return (flags & ACC_SYNCHRONIZED) != 0;
    }

    public static boolean isAbstract(int flags) {
        return (flags & ACC_ABSTRACT) != 0;
    }

    public static boolean isNative(int flags) {
        return (flags & ACC_NATIVE) != 0;
    }

    public static boolean isAnnotation(int flags) {
        return (flags & ACC_ANNOTATION) != 0;
    }

    public static boolean isDeclaredSynchronized(int flags) {
        return (flags & ACC_DECLARED_SYNCHRONIZED) != 0;
    }

    public static boolean isEnum(int flags) {
        return (flags & ACC_ENUM) != 0;
    }

    private static String humanHelper(int flags, int mask, int what) {
        StringBuilder sb = new StringBuilder(80);
        int extra = flags & ~mask;

        flags &= mask;

        if ((flags & ACC_PUBLIC) != 0) {
            sb.append("|public");
        }
        if ((flags & ACC_PRIVATE) != 0) {
            sb.append("|private");
        }
        if ((flags & ACC_PROTECTED) != 0) {
            sb.append("|protected");
        }
        if ((flags & ACC_STATIC) != 0) {
            sb.append("|static");
        }
        if ((flags & ACC_FINAL) != 0) {
            sb.append("|final");
        }
        if ((flags & ACC_SYNCHRONIZED) != 0) {
            if (what == CONV_CLASS) {
                sb.append("|super");
            } else {
                sb.append("|synchronized");
            }
        }
        if ((flags & ACC_VOLATILE) != 0) {
            if (what == CONV_METHOD) {
                sb.append("|bridge");
            } else {
                sb.append("|volatile");
            }
        }
        if ((flags & ACC_TRANSIENT) != 0) {
            if (what == CONV_METHOD) {
                sb.append("|varargs");
            } else {
                sb.append("|transient");
            }
        }
        if ((flags & ACC_NATIVE) != 0) {
            sb.append("|native");
        }
        if ((flags & ACC_INTERFACE) != 0) {
            sb.append("|interface");
        }
        if ((flags & ACC_ABSTRACT) != 0) {
            sb.append("|abstract");
        }
        if ((flags & ACC_STRICT) != 0) {
            sb.append("|strictfp");
        }
        if ((flags & ACC_SYNTHETIC) != 0) {
            sb.append("|synthetic");
        }
        if ((flags & ACC_ANNOTATION) != 0) {
            sb.append("|annotation");
        }
        if ((flags & ACC_ENUM) != 0) {
            sb.append("|enum");
        }
        if ((flags & ACC_CONSTRUCTOR) != 0) {
            sb.append("|constructor");
        }
        if ((flags & ACC_DECLARED_SYNCHRONIZED) != 0) {
            sb.append("|declared_synchronized");
        }

        if ((extra != 0) || (sb.length() == 0)) {
            sb.append('|');
            sb.append(Hex.u2(extra));
        }

        return sb.substring(1);
    }
}
