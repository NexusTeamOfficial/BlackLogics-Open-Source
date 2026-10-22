
package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.rop.type.Prototype;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeBearer;

public abstract class CstBaseMethodRef
        extends CstMemberRef {
    private final Prototype prototype;

    private Prototype instancePrototype;

     CstBaseMethodRef(CstType definingClass, CstNat nat) {
        super(definingClass, nat);

        String descriptor = getNat().getDescriptor().getString();
        if (isSignaturePolymorphic()) {





            this.prototype = Prototype.fromDescriptor(descriptor);
        } else {
            this.prototype = Prototype.intern(descriptor);
        }
        this.instancePrototype = null;
    }

    public final Prototype getPrototype() {
        return prototype;
    }

    public final Prototype getPrototype(boolean isStatic) {
        if (isStatic) {
            return prototype;
        } else {
            if (instancePrototype == null) {
                Type thisType = getDefiningClass().getClassType();
                instancePrototype = prototype.withFirstParameter(thisType);
            }
            return instancePrototype;
        }
    }

    @Override
    protected final int compareTo0(Constant other) {
        int cmp = super.compareTo0(other);

        if (cmp != 0) {
            return cmp;
        }

        CstBaseMethodRef otherMethod = (CstBaseMethodRef) other;
        return prototype.compareTo(otherMethod.prototype);
    }

    @Override
    public final Type getType() {
        return prototype.getReturnType();
    }

    public final int getParameterWordCount(boolean isStatic) {
        return getPrototype(isStatic).getParameterTypes().getWordCount();
    }

    public final boolean isInstanceInit() {
        return getNat().isInstanceInit();
    }

    public final boolean isClassInit() {
        return getNat().isClassInit();
    }

    public final boolean isSignaturePolymorphic() {
        CstType definingClass = getDefiningClass();
        if (definingClass.equals(CstType.METHOD_HANDLE)) {
            switch (getNat().getName().getString()) {
                case "invoke":
                case "invokeExact":
                    return true;
            }
        } else if (definingClass.equals(CstType.VAR_HANDLE)) {
            switch (getNat().getName().getString()) {
                case "compareAndExchange":
                case "compareAndExchangeAcquire":
                case "compareAndExchangeRelease":
                case "compareAndSet":
                case "get":
                case "getAcquire":
                case "getAndAdd":
                case "getAndAddAcquire":
                case "getAndAddRelease":
                case "getAndBitwiseAnd":
                case "getAndBitwiseAndAcquire":
                case "getAndBitwiseAndRelease":
                case "getAndBitwiseOr":
                case "getAndBitwiseOrAcquire":
                case "getAndBitwiseOrRelease":
                case "getAndBitwiseXor":
                case "getAndBitwiseXorAcquire":
                case "getAndBitwiseXorRelease":
                case "getAndSet":
                case "getAndSetAcquire":
                case "getAndSetRelease":
                case "getOpaque":
                case "getVolatile":
                case "set":
                case "setOpaque":
                case "setRelease":
                case "setVolatile":
                case "weakCompareAndSet":
                case "weakCompareAndSetAcquire":
                case "weakCompareAndSetPlain":
                case "weakCompareAndSetRelease":
                    return true;
            }
        }
        return false;
    }
}
