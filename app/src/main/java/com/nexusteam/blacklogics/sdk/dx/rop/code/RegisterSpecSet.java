
package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.util.MutabilityControl;

public final class RegisterSpecSet
        extends MutabilityControl {
    public static final RegisterSpecSet EMPTY = new RegisterSpecSet(0);

    private final RegisterSpec[] specs;

    private int size;

    public RegisterSpecSet(int maxSize) {
        super(maxSize != 0);

        this.specs = new RegisterSpec[maxSize];
        this.size = 0;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof RegisterSpecSet)) {
            return false;
        }

        RegisterSpecSet otherSet = (RegisterSpecSet) other;
        RegisterSpec[] otherSpecs = otherSet.specs;
        int len = specs.length;

        if ((len != otherSpecs.length) || (size() != otherSet.size())) {
            return false;
        }

        for (int i = 0; i < len; i++) {
            RegisterSpec s1 = specs[i];
            RegisterSpec s2 = otherSpecs[i];

            if (s1 == s2) {
                continue;
            }

            if ((s1 == null) || !s1.equals(s2)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int len = specs.length;
        int hash = 0;

        for (int i = 0; i < len; i++) {
            RegisterSpec spec = specs[i];
            int oneHash = (spec == null) ? 0 : spec.hashCode();
            hash = (hash * 31) + oneHash;
        }

        return hash;
    }

    @Override
    public String toString() {
        int len = specs.length;
        StringBuilder sb = new StringBuilder(len * 25);

        sb.append('{');

        boolean any = false;
        for (int i = 0; i < len; i++) {
            RegisterSpec spec = specs[i];
            if (spec != null) {
                if (any) {
                    sb.append(", ");
                } else {
                    any = true;
                }
                sb.append(spec);
            }
        }

        sb.append('}');
        return sb.toString();
    }

    public int getMaxSize() {
        return specs.length;
    }

    public int size() {
        int result = size;

        if (result < 0) {
            int len = specs.length;

            result = 0;
            for (int i = 0; i < len; i++) {
                if (specs[i] != null) {
                    result++;
                }
            }

            size = result;
        }

        return result;
    }

    public RegisterSpec get(int reg) {
        try {
            return specs[reg];
        } catch (ArrayIndexOutOfBoundsException ex) {

            throw new IllegalArgumentException("bogus reg");
        }
    }

    public RegisterSpec get(RegisterSpec spec) {
        return get(spec.getReg());
    }

    public RegisterSpec findMatchingLocal(RegisterSpec spec) {
        int length = specs.length;

        for (int reg = 0; reg < length; reg++) {
            RegisterSpec s = specs[reg];

            if (s == null) {
                continue;
            }

            if (spec.matchesVariable(s)) {
                return s;
            }
        }

        return null;
    }

    public RegisterSpec localItemToSpec(LocalItem local) {
        int length = specs.length;

        for (int reg = 0; reg < length; reg++) {
            RegisterSpec spec = specs[reg];

            if ((spec != null) && local.equals(spec.getLocalItem())) {
                return spec;
            }
        }

        return null;
    }

    public void remove(RegisterSpec toRemove) {
        try {
            specs[toRemove.getReg()] = null;
            size = -1;
        } catch (ArrayIndexOutOfBoundsException ex) {

            throw new IllegalArgumentException("bogus reg");
        }
    }

    public void put(RegisterSpec spec) {
        throwIfImmutable();

        if (spec == null) {
            throw new NullPointerException("spec == null");
        }

        size = -1;

        try {
            int reg = spec.getReg();
            specs[reg] = spec;

            if (reg > 0) {
                int prevReg = reg - 1;
                RegisterSpec prevSpec = specs[prevReg];
                if ((prevSpec != null) && (prevSpec.getCategory() == 2)) {
                    specs[prevReg] = null;
                }
            }

            if (spec.getCategory() == 2) {
                specs[reg + 1] = null;
            }
        } catch (ArrayIndexOutOfBoundsException ex) {

            throw new IllegalArgumentException("spec.getReg() out of range");
        }
    }

    public void putAll(RegisterSpecSet set) {
        int max = set.getMaxSize();

        for (int i = 0; i < max; i++) {
            RegisterSpec spec = set.get(i);
            if (spec != null) {
                put(spec);
            }
        }
    }

    public void intersect(RegisterSpecSet other, boolean localPrimary) {
        throwIfImmutable();

        RegisterSpec[] otherSpecs = other.specs;
        int thisLen = specs.length;
        int len = Math.min(thisLen, otherSpecs.length);

        size = -1;

        for (int i = 0; i < len; i++) {
            RegisterSpec spec = specs[i];

            if (spec == null) {
                continue;
            }

            RegisterSpec intersection =
                spec.intersect(otherSpecs[i], localPrimary);
            if (intersection != spec) {
                specs[i] = intersection;
            }
        }

        for (int i = len; i < thisLen; i++) {
            specs[i] = null;
        }
    }

    public RegisterSpecSet withOffset(int delta) {
        int len = specs.length;
        RegisterSpecSet result = new RegisterSpecSet(len + delta);

        for (int i = 0; i < len; i++) {
            RegisterSpec spec = specs[i];
            if (spec != null) {
                result.put(spec.withOffset(delta));
            }
        }

        result.size = size;

        if (isImmutable()) {
            result.setImmutable();
        }

        return result;
    }

    public RegisterSpecSet mutableCopy() {
        int len = specs.length;
        RegisterSpecSet copy = new RegisterSpecSet(len);

        for (int i = 0; i < len; i++) {
            RegisterSpec spec = specs[i];
            if (spec != null) {
                copy.put(spec);
            }
        }

        copy.size = size;

        return copy;
    }
}
