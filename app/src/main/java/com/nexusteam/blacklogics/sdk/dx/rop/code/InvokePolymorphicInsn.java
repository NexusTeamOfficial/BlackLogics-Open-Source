package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeList;
import mod.agus.jcoderz.dx.rop.cst.CstMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstNat;
import mod.agus.jcoderz.dx.rop.cst.CstProtoRef;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;

public class InvokePolymorphicInsn extends mod.agus.jcoderz.dx.rop.code.Insn {
    private static final mod.agus.jcoderz.dx.rop.cst.CstString DEFAULT_DESCRIPTOR =
            new mod.agus.jcoderz.dx.rop.cst.CstString("([Ljava/lang/Object;)Ljava/lang/Object;");

    private static final mod.agus.jcoderz.dx.rop.cst.CstString VARHANDLE_SET_DESCRIPTOR =
            new mod.agus.jcoderz.dx.rop.cst.CstString("([Ljava/lang/Object;)V");

    private static final mod.agus.jcoderz.dx.rop.cst.CstString VARHANDLE_COMPARE_AND_SET_DESCRIPTOR =
            new mod.agus.jcoderz.dx.rop.cst.CstString("([Ljava/lang/Object;)Z");

    private final TypeList catches;

    private final mod.agus.jcoderz.dx.rop.cst.CstMethodRef callSiteMethod;

    private final mod.agus.jcoderz.dx.rop.cst.CstMethodRef polymorphicMethod;

    private final mod.agus.jcoderz.dx.rop.cst.CstProtoRef callSiteProto;

    public InvokePolymorphicInsn(mod.agus.jcoderz.dx.rop.code.Rop opcode, SourcePosition position, mod.agus.jcoderz.dx.rop.code.RegisterSpecList sources, TypeList catches,
                                 mod.agus.jcoderz.dx.rop.cst.CstMethodRef callSiteMethod) {
        super(opcode, position, null, sources);

        if (opcode.getBranchingness() != Rop.BRANCH_THROW) {
            throw new IllegalArgumentException("opcode with invalid branchingness: " + opcode.getBranchingness());
        }

        if (catches == null) {
            throw new NullPointerException("catches == null");
        }
        this.catches = catches;

        if (callSiteMethod == null) {
            throw new NullPointerException("callSiteMethod == null");
        } else if (!callSiteMethod.isSignaturePolymorphic()) {
            throw new IllegalArgumentException("callSiteMethod is not signature polymorphic");
        }

        this.callSiteMethod = callSiteMethod;
        this.polymorphicMethod = makePolymorphicMethod(callSiteMethod);
        this.callSiteProto = makeCallSiteProto(callSiteMethod);
    }

    @Override
    public TypeList getCatches() {
        return this.catches;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitInvokePolymorphicInsn(this);
    }

    @Override
    public mod.agus.jcoderz.dx.rop.code.Insn withAddedCatch(Type type) {
        return new InvokePolymorphicInsn(getOpcode(), getPosition(),
                getSources(), catches.withAddedType(type), getCallSiteMethod());
    }

    @Override
    public mod.agus.jcoderz.dx.rop.code.Insn withRegisterOffset(int delta) {
        return new InvokePolymorphicInsn(getOpcode(), getPosition(),
                getSources().withOffset(delta),
                catches, getCallSiteMethod());
    }

    @Override
    public Insn withNewRegisters(RegisterSpec result, RegisterSpecList sources) {
        return new InvokePolymorphicInsn(getOpcode(), getPosition(),
                sources, catches, getCallSiteMethod());
    }

    public mod.agus.jcoderz.dx.rop.cst.CstMethodRef getCallSiteMethod() {
        return callSiteMethod;
    }

    public mod.agus.jcoderz.dx.rop.cst.CstMethodRef getPolymorphicMethod() {
        return polymorphicMethod;
    }

    public mod.agus.jcoderz.dx.rop.cst.CstProtoRef getCallSiteProto() {
        return callSiteProto;
    }

    @Override
    public String getInlineString() {
        return getPolymorphicMethod().toString() + " " +
            getCallSiteProto().toString() + " " +
            ThrowingInsn.toCatchString(catches);
    }

    private static mod.agus.jcoderz.dx.rop.cst.CstMethodRef makePolymorphicMethod(final mod.agus.jcoderz.dx.rop.cst.CstMethodRef callSiteMethod) {
        mod.agus.jcoderz.dx.rop.cst.CstType definingClass= callSiteMethod.getDefiningClass();
        CstString cstMethodName = callSiteMethod.getNat().getName();
        String methodName = callSiteMethod.getNat().getName().getString();

        if (definingClass.equals(mod.agus.jcoderz.dx.rop.cst.CstType.METHOD_HANDLE)) {
            if (methodName.equals("invoke") || methodName.equals("invokeExact")) {
                mod.agus.jcoderz.dx.rop.cst.CstNat cstNat = new mod.agus.jcoderz.dx.rop.cst.CstNat(cstMethodName, DEFAULT_DESCRIPTOR);
                return new mod.agus.jcoderz.dx.rop.cst.CstMethodRef(definingClass, cstNat);
            }
        }

        if (definingClass.equals(CstType.VAR_HANDLE)) {
            switch (methodName) {
                case "compareAndExchange":
                case "compareAndExchangeAcquire":
                case "compareAndExchangeRelease":
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
                {
                    mod.agus.jcoderz.dx.rop.cst.CstNat cstNat = new mod.agus.jcoderz.dx.rop.cst.CstNat(cstMethodName, DEFAULT_DESCRIPTOR);
                    return new mod.agus.jcoderz.dx.rop.cst.CstMethodRef(definingClass, cstNat);
                }
                case "set":
                case "setOpaque":
                case "setRelease":
                case "setVolatile":
                {
                    mod.agus.jcoderz.dx.rop.cst.CstNat cstNat = new mod.agus.jcoderz.dx.rop.cst.CstNat(cstMethodName, VARHANDLE_SET_DESCRIPTOR);
                    return new mod.agus.jcoderz.dx.rop.cst.CstMethodRef(definingClass, cstNat);
                }
                case "compareAndSet":
                case "weakCompareAndSet":
                case "weakCompareAndSetAcquire":
                case "weakCompareAndSetPlain":
                case "weakCompareAndSetRelease":
                {
                    mod.agus.jcoderz.dx.rop.cst.CstNat cstNat = new CstNat(cstMethodName, VARHANDLE_COMPARE_AND_SET_DESCRIPTOR);
                    return new mod.agus.jcoderz.dx.rop.cst.CstMethodRef(definingClass, cstNat);
                }
                default:
                    break;
            }
        }
        throw new IllegalArgumentException("Unknown signature polymorphic method: " +
                                           callSiteMethod.toHuman());
    }

    private static mod.agus.jcoderz.dx.rop.cst.CstProtoRef makeCallSiteProto(final CstMethodRef callSiteMethod) {
        return new CstProtoRef(callSiteMethod.getPrototype(true));
    }
}
