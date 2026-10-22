
package mod.agus.jcoderz.dx.rop.type;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class Prototype implements Comparable<Prototype> {
    private static final ConcurrentMap<String, Prototype> internTable =
            new ConcurrentHashMap<>(10_000, 0.75f);

    private final String descriptor;

    private final mod.agus.jcoderz.dx.rop.type.Type returnType;

    private final mod.agus.jcoderz.dx.rop.type.StdTypeList parameterTypes;

    private mod.agus.jcoderz.dx.rop.type.StdTypeList parameterFrameTypes;

    public static Prototype intern(String descriptor) {
        if (descriptor == null) {
            throw new NullPointerException("descriptor == null");
        }

        Prototype result = internTable.get(descriptor);
        if (result != null) {
            return result;
        }

        result = fromDescriptor(descriptor);
        return putIntern(result);
    }

    public static Prototype fromDescriptor(String descriptor) {
        Prototype result = internTable.get(descriptor);
        if (result != null) {
            return result;
        }

        mod.agus.jcoderz.dx.rop.type.Type[] params = makeParameterArray(descriptor);
        int paramCount = 0;
        int at = 1;

        for (;;) {
            int startAt = at;
            char c = descriptor.charAt(at);
            if (c == ')') {
                at++;
                break;
            }


            while (c == '[') {
                at++;
                c = descriptor.charAt(at);
            }

            if (c == 'L') {

                int endAt = descriptor.indexOf(';', at);
                if (endAt == -1) {
                    throw new IllegalArgumentException("bad descriptor");
                }
                at = endAt + 1;
            } else {
                at++;
            }

            params[paramCount] =
                mod.agus.jcoderz.dx.rop.type.Type.intern(descriptor.substring(startAt, at));
            paramCount++;
        }

        mod.agus.jcoderz.dx.rop.type.Type returnType = mod.agus.jcoderz.dx.rop.type.Type.internReturnType(descriptor.substring(at));
        mod.agus.jcoderz.dx.rop.type.StdTypeList parameterTypes = new mod.agus.jcoderz.dx.rop.type.StdTypeList(paramCount);

        for (int i = 0; i < paramCount; i++) {
            parameterTypes.set(i, params[i]);
        }

        return new Prototype(descriptor, returnType, parameterTypes);
    }

    public static void clearInternTable() {
        internTable.clear();
    }

    private static mod.agus.jcoderz.dx.rop.type.Type[] makeParameterArray(String descriptor) {
        int length = descriptor.length();

        if (descriptor.charAt(0) != '(') {
            throw new IllegalArgumentException("bad descriptor");
        }

        int closeAt = 0;
        int maxParams = 0;
        for (int i = 1; i < length; i++) {
            char c = descriptor.charAt(i);
            if (c == ')') {
                closeAt = i;
                break;
            }
            if ((c >= 'A') && (c <= 'Z')) {
                maxParams++;
            }
        }

        if ((closeAt == 0) || (closeAt == (length - 1))) {
            throw new IllegalArgumentException("bad descriptor");
        }

        if (descriptor.indexOf(')', closeAt + 1) != -1) {
            throw new IllegalArgumentException("bad descriptor");
        }

        return new mod.agus.jcoderz.dx.rop.type.Type[maxParams];
    }

    public static Prototype intern(String descriptor, mod.agus.jcoderz.dx.rop.type.Type definer,
            boolean isStatic, boolean isInit) {
        Prototype base = intern(descriptor);

        if (isStatic) {
            return base;
        }

        if (isInit) {
            definer = definer.asUninitialized(Integer.MAX_VALUE);
        }

        return base.withFirstParameter(definer);
    }

    public static Prototype internInts(mod.agus.jcoderz.dx.rop.type.Type returnType, int count) {


        StringBuilder sb = new StringBuilder(100);

        sb.append('(');

        for (int i = 0; i < count; i++) {
            sb.append('I');
        }

        sb.append(')');
        sb.append(returnType.getDescriptor());


        return intern(sb.toString());
    }

    private Prototype(String descriptor, mod.agus.jcoderz.dx.rop.type.Type returnType,
            mod.agus.jcoderz.dx.rop.type.StdTypeList parameterTypes) {
        if (descriptor == null) {
            throw new NullPointerException("descriptor == null");
        }

        if (returnType == null) {
            throw new NullPointerException("returnType == null");
        }

        if (parameterTypes == null) {
            throw new NullPointerException("parameterTypes == null");
        }

        this.descriptor = descriptor;
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
        this.parameterFrameTypes = null;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Prototype)) {
            return false;
        }

        return descriptor.equals(((Prototype) other).descriptor);
    }

    @Override
    public int hashCode() {
        return descriptor.hashCode();
    }

    @Override
    public int compareTo(Prototype other) {
        if (this == other) {
            return 0;
        }


        int result = returnType.compareTo(other.returnType);

        if (result != 0) {
            return result;
        }

        int thisSize = parameterTypes.size();
        int otherSize = other.parameterTypes.size();
        int size = Math.min(thisSize, otherSize);

        for (int i = 0; i < size; i++) {
            mod.agus.jcoderz.dx.rop.type.Type thisType = parameterTypes.get(i);
            mod.agus.jcoderz.dx.rop.type.Type otherType = other.parameterTypes.get(i);

            result = thisType.compareTo(otherType);

            if (result != 0) {
                return result;
            }
        }

        if (thisSize < otherSize) {
            return -1;
        } else if (thisSize > otherSize) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return descriptor;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public mod.agus.jcoderz.dx.rop.type.Type getReturnType() {
        return returnType;
    }

    public mod.agus.jcoderz.dx.rop.type.StdTypeList getParameterTypes() {
        return parameterTypes;
    }

    public mod.agus.jcoderz.dx.rop.type.StdTypeList getParameterFrameTypes() {
        if (parameterFrameTypes == null) {
            int sz = parameterTypes.size();
            mod.agus.jcoderz.dx.rop.type.StdTypeList list = new mod.agus.jcoderz.dx.rop.type.StdTypeList(sz);
            boolean any = false;
            for (int i = 0; i < sz; i++) {
                mod.agus.jcoderz.dx.rop.type.Type one = parameterTypes.get(i);
                if (one.isIntlike()) {
                    any = true;
                    one = mod.agus.jcoderz.dx.rop.type.Type.INT;
                }
                list.set(i, one);
            }
            parameterFrameTypes = any ? list : parameterTypes;
        }

        return parameterFrameTypes;
    }

    public Prototype withFirstParameter(Type param) {
        String newDesc = "(" + param.getDescriptor() + descriptor.substring(1);
        StdTypeList newParams = parameterTypes.withFirst(param);

        newParams.setImmutable();

        Prototype result =
            new Prototype(newDesc, returnType, newParams);

        return putIntern(result);
    }

    private static Prototype putIntern(Prototype desc) {
        Prototype result = internTable.putIfAbsent(desc.getDescriptor(), desc);
        return result != null ? result : desc;
    }
}
