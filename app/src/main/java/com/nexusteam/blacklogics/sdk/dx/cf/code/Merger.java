
package mod.agus.jcoderz.dx.cf.code;

import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeBearer;
import mod.agus.jcoderz.dx.util.Hex;

public final class Merger {
    private Merger() {

    }

    public static mod.agus.jcoderz.dx.cf.code.OneLocalsArray mergeLocals(mod.agus.jcoderz.dx.cf.code.OneLocalsArray locals1,
                                                                         mod.agus.jcoderz.dx.cf.code.OneLocalsArray locals2) {
        if (locals1 == locals2) {

            return locals1;
        }

        int sz = locals1.getMaxLocals();
        OneLocalsArray result = null;

        if (locals2.getMaxLocals() != sz) {
            throw new SimException("mismatched maxLocals values");
        }

        for (int i = 0; i < sz; i++) {
            mod.agus.jcoderz.dx.rop.type.TypeBearer tb1 = locals1.getOrNull(i);
            mod.agus.jcoderz.dx.rop.type.TypeBearer tb2 = locals2.getOrNull(i);
            mod.agus.jcoderz.dx.rop.type.TypeBearer resultType = mergeType(tb1, tb2);
            if (resultType != tb1) {
                if (result == null) {
                    result = locals1.copy();
                }

                if (resultType == null) {
                    result.invalidate(i);
                } else {
                    result.set(i, resultType);
                }
            }
        }

        if (result == null) {
            return locals1;
        }

        result.setImmutable();
        return result;
    }

    public static mod.agus.jcoderz.dx.cf.code.ExecutionStack mergeStack(mod.agus.jcoderz.dx.cf.code.ExecutionStack stack1,
                                                                        mod.agus.jcoderz.dx.cf.code.ExecutionStack stack2) {
        if (stack1 == stack2) {

            return stack1;
        }

        int sz = stack1.size();
        ExecutionStack result = null;

        if (stack2.size() != sz) {
            throw new SimException("mismatched stack depths");
        }

        for (int i = 0; i < sz; i++) {
            mod.agus.jcoderz.dx.rop.type.TypeBearer tb1 = stack1.peek(i);
            mod.agus.jcoderz.dx.rop.type.TypeBearer tb2 = stack2.peek(i);
            mod.agus.jcoderz.dx.rop.type.TypeBearer resultType = mergeType(tb1, tb2);
            if (resultType != tb1) {
                if (result == null) {
                    result = stack1.copy();
                }

                try {
                    if (resultType == null) {
                        throw new SimException("incompatible: " + tb1 + ", " +
                                               tb2);
                    } else {
                        result.change(i, resultType);
                    }
                } catch (SimException ex) {
                    ex.addContext("...while merging stack[" + Hex.u2(i) + "]");
                    throw ex;
                }
            }
        }

        if (result == null) {
            return stack1;
        }

        result.setImmutable();
        return result;
    }

    public static mod.agus.jcoderz.dx.rop.type.TypeBearer mergeType(mod.agus.jcoderz.dx.rop.type.TypeBearer ft1, mod.agus.jcoderz.dx.rop.type.TypeBearer ft2) {
        if ((ft1 == null) || ft1.equals(ft2)) {
            return ft1;
        } else if (ft2 == null) {
            return null;
        } else {
            mod.agus.jcoderz.dx.rop.type.Type type1 = ft1.getType();
            mod.agus.jcoderz.dx.rop.type.Type type2 = ft2.getType();

            if (type1 == type2) {
                return type1;
            } else if (type1.isReference() && type2.isReference()) {
                if (type1 == mod.agus.jcoderz.dx.rop.type.Type.KNOWN_NULL) {
                    return type2;
                } else if (type2 == mod.agus.jcoderz.dx.rop.type.Type.KNOWN_NULL) {
                    return type1;
                } else if (type1.isArray() && type2.isArray()) {
                    mod.agus.jcoderz.dx.rop.type.TypeBearer componentUnion =
                        mergeType(type1.getComponentType(),
                                type2.getComponentType());
                    if (componentUnion == null) {
                        return mod.agus.jcoderz.dx.rop.type.Type.OBJECT;
                    }
                    return ((mod.agus.jcoderz.dx.rop.type.Type) componentUnion).getArrayType();
                } else {
                    return mod.agus.jcoderz.dx.rop.type.Type.OBJECT;
                }
            } else if (type1.isIntlike() && type2.isIntlike()) {
                return mod.agus.jcoderz.dx.rop.type.Type.INT;
            } else {
                return null;
            }
        }
    }

    public static boolean isPossiblyAssignableFrom(mod.agus.jcoderz.dx.rop.type.TypeBearer supertypeBearer,
                                                   TypeBearer subtypeBearer) {
        mod.agus.jcoderz.dx.rop.type.Type supertype = supertypeBearer.getType();
        mod.agus.jcoderz.dx.rop.type.Type subtype = subtypeBearer.getType();

        if (supertype.equals(subtype)) {

            return true;
        }

        int superBt = supertype.getBasicType();
        int subBt = subtype.getBasicType();



        if (superBt == mod.agus.jcoderz.dx.rop.type.Type.BT_ADDR) {
            supertype = mod.agus.jcoderz.dx.rop.type.Type.OBJECT;
            superBt = mod.agus.jcoderz.dx.rop.type.Type.BT_OBJECT;
        }

        if (subBt == mod.agus.jcoderz.dx.rop.type.Type.BT_ADDR) {
            subtype = mod.agus.jcoderz.dx.rop.type.Type.OBJECT;
            subBt = mod.agus.jcoderz.dx.rop.type.Type.BT_OBJECT;
        }

        if ((superBt != mod.agus.jcoderz.dx.rop.type.Type.BT_OBJECT) || (subBt != mod.agus.jcoderz.dx.rop.type.Type.BT_OBJECT)) {
            return supertype.isIntlike() && subtype.isIntlike();
        }



        if (supertype == mod.agus.jcoderz.dx.rop.type.Type.KNOWN_NULL) {
            return false;
        } else if (subtype == mod.agus.jcoderz.dx.rop.type.Type.KNOWN_NULL) {
            return true;
        } else if (supertype == mod.agus.jcoderz.dx.rop.type.Type.OBJECT) {
            return true;
        } else if (supertype.isArray()) {

            if (! subtype.isArray()) {

                return false;
            }

            do {
                supertype = supertype.getComponentType();
                subtype = subtype.getComponentType();
            } while (supertype.isArray() && subtype.isArray());

            return isPossiblyAssignableFrom(supertype, subtype);
        } else if (subtype.isArray()) {
            return (supertype == mod.agus.jcoderz.dx.rop.type.Type.SERIALIZABLE) ||
                (supertype == Type.CLONEABLE);
        } else {
            return true;
        }
    }
}
