
package mod.agus.jcoderz.dx.cf.code;

import mod.agus.jcoderz.dex.DexFormat;
import mod.agus.jcoderz.dx.dex.DexOptions;
import mod.agus.jcoderz.dx.rop.code.LocalItem;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstFieldRef;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;
import mod.agus.jcoderz.dx.rop.cst.CstInterfaceMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstInvokeDynamic;
import mod.agus.jcoderz.dx.rop.cst.CstMethodHandle;
import mod.agus.jcoderz.dx.rop.cst.CstMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstProtoRef;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Prototype;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.Hex;

import java.util.ArrayList;

public class Simulator {
    private static final String LOCAL_MISMATCH_ERROR =
        "This is symptomatic of .class transformation tools that ignore " +
        "local variable information.";

    private final Machine machine;

    private final BytecodeArray code;

    private ConcreteMethod method;

    private final LocalVariableList localVariables;

    private final SimVisitor visitor;

    private final mod.agus.jcoderz.dx.dex.DexOptions dexOptions;

    public Simulator(Machine machine, ConcreteMethod method, DexOptions dexOptions) {
        if (machine == null) {
            throw new NullPointerException("machine == null");
        }

        if (method == null) {
            throw new NullPointerException("method == null");
        }

        if (dexOptions == null) {
            throw new NullPointerException("dexOptions == null");
        }

        this.machine = machine;
        this.code = method.getCode();
        this.method = method;
        this.localVariables = method.getLocalVariables();
        this.visitor = new SimVisitor();
        this.dexOptions = dexOptions;


        if (method.isDefaultOrStaticInterfaceMethod()) {
            checkInterfaceMethodDeclaration(method);
        }
    }

    public void simulate(ByteBlock bb, Frame frame) {
        int end = bb.getEnd();

        visitor.setFrame(frame);

        try {
            for (int off = bb.getStart(); off < end; ) {
                int length = code.parseInstruction(off, visitor);
                visitor.setPreviousOffset(off);
                off += length;
            }
        } catch (SimException ex) {
            frame.annotate(ex);
            throw ex;
        }
    }

    public int simulate(int offset, Frame frame) {
        visitor.setFrame(frame);
        return code.parseInstruction(offset, visitor);
    }

    private static SimException illegalTos() {
        return new SimException("stack mismatch: illegal " +
                "top-of-stack for opcode");
    }

    private static mod.agus.jcoderz.dx.rop.type.Type requiredArrayTypeFor(mod.agus.jcoderz.dx.rop.type.Type impliedType,
                                                                          mod.agus.jcoderz.dx.rop.type.Type foundArrayType) {
        if (foundArrayType == mod.agus.jcoderz.dx.rop.type.Type.KNOWN_NULL) {
            return impliedType.isReference()
                ? mod.agus.jcoderz.dx.rop.type.Type.KNOWN_NULL
                : impliedType.getArrayType();
        }

        if ((impliedType == mod.agus.jcoderz.dx.rop.type.Type.OBJECT)
                && foundArrayType.isArray()
                && foundArrayType.getComponentType().isReference()) {
            return foundArrayType;
        }

        if ((impliedType == mod.agus.jcoderz.dx.rop.type.Type.BYTE)
                && (foundArrayType == mod.agus.jcoderz.dx.rop.type.Type.BOOLEAN_ARRAY)) {
            return mod.agus.jcoderz.dx.rop.type.Type.BOOLEAN_ARRAY;
        }

        return impliedType.getArrayType();
    }

    private class SimVisitor implements BytecodeArray.Visitor {
        private final Machine machine;

        private Frame frame;

        private int previousOffset;

        public SimVisitor() {
            this.machine = Simulator.this.machine;
            this.frame = null;
        }

        public void setFrame(Frame frame) {
            if (frame == null) {
                throw new NullPointerException("frame == null");
            }

            this.frame = frame;
        }

        @Override
        public void visitInvalid(int opcode, int offset, int length) {
            throw new SimException("invalid opcode " + Hex.u1(opcode));
        }

        @Override
        public void visitNoArgs(int opcode, int offset, int length,
                mod.agus.jcoderz.dx.rop.type.Type type) {
            switch (opcode) {
                case ByteOps.NOP: {
                    machine.clearArgs();
                    break;
                }
                case ByteOps.INEG: {
                    machine.popArgs(frame, type);
                    break;
                }
                case ByteOps.I2L:
                case ByteOps.I2F:
                case ByteOps.I2D:
                case ByteOps.I2B:
                case ByteOps.I2C:
                case ByteOps.I2S: {
                    machine.popArgs(frame, mod.agus.jcoderz.dx.rop.type.Type.INT);
                    break;
                }
                case ByteOps.L2I:
                case ByteOps.L2F:
                case ByteOps.L2D: {
                    machine.popArgs(frame, mod.agus.jcoderz.dx.rop.type.Type.LONG);
                    break;
                }
                case ByteOps.F2I:
                case ByteOps.F2L:
                case ByteOps.F2D: {
                    machine.popArgs(frame, mod.agus.jcoderz.dx.rop.type.Type.FLOAT);
                    break;
                }
                case ByteOps.D2I:
                case ByteOps.D2L:
                case ByteOps.D2F: {
                    machine.popArgs(frame, mod.agus.jcoderz.dx.rop.type.Type.DOUBLE);
                    break;
                }
                case ByteOps.RETURN: {
                    machine.clearArgs();
                    checkReturnType(mod.agus.jcoderz.dx.rop.type.Type.VOID);
                    break;
                }
                case ByteOps.IRETURN: {
                    mod.agus.jcoderz.dx.rop.type.Type checkType = type;
                    if (type == mod.agus.jcoderz.dx.rop.type.Type.OBJECT) {
                        checkType = frame.getStack().peekType(0);
                    }
                    machine.popArgs(frame, type);
                    checkReturnType(checkType);
                    break;
                }
                case ByteOps.POP: {
                    mod.agus.jcoderz.dx.rop.type.Type peekType = frame.getStack().peekType(0);
                    if (peekType.isCategory2()) {
                        throw illegalTos();
                    }
                    machine.popArgs(frame, 1);
                    break;
                }
                case ByteOps.ARRAYLENGTH: {
                    mod.agus.jcoderz.dx.rop.type.Type arrayType = frame.getStack().peekType(0);
                    if (!arrayType.isArrayOrKnownNull()) {
                        fail("type mismatch: expected array type but encountered " +
                             arrayType.toHuman());
                    }
                    machine.popArgs(frame, mod.agus.jcoderz.dx.rop.type.Type.OBJECT);
                    break;
                }
                case ByteOps.ATHROW:
                case ByteOps.MONITORENTER:
                case ByteOps.MONITOREXIT: {
                    machine.popArgs(frame, mod.agus.jcoderz.dx.rop.type.Type.OBJECT);
                    break;
                }
                case ByteOps.IALOAD: {
                    mod.agus.jcoderz.dx.rop.type.Type foundArrayType = frame.getStack().peekType(1);
                    mod.agus.jcoderz.dx.rop.type.Type requiredArrayType =
                        requiredArrayTypeFor(type, foundArrayType);


                    type = (requiredArrayType == mod.agus.jcoderz.dx.rop.type.Type.KNOWN_NULL)
                        ? mod.agus.jcoderz.dx.rop.type.Type.KNOWN_NULL
                        : requiredArrayType.getComponentType();

                    machine.popArgs(frame, requiredArrayType, mod.agus.jcoderz.dx.rop.type.Type.INT);
                    break;
                }
                case ByteOps.IADD:
                case ByteOps.ISUB:
                case ByteOps.IMUL:
                case ByteOps.IDIV:
                case ByteOps.IREM:
                case ByteOps.IAND:
                case ByteOps.IOR:
                case ByteOps.IXOR: {
                    machine.popArgs(frame, type, type);
                    break;
                }
                case ByteOps.ISHL:
                case ByteOps.ISHR:
                case ByteOps.IUSHR: {
                    machine.popArgs(frame, type, mod.agus.jcoderz.dx.rop.type.Type.INT);
                    break;
                }
                case ByteOps.LCMP: {
                    machine.popArgs(frame, mod.agus.jcoderz.dx.rop.type.Type.LONG, mod.agus.jcoderz.dx.rop.type.Type.LONG);
                    break;
                }
                case ByteOps.FCMPL:
                case ByteOps.FCMPG: {
                    machine.popArgs(frame, mod.agus.jcoderz.dx.rop.type.Type.FLOAT, mod.agus.jcoderz.dx.rop.type.Type.FLOAT);
                    break;
                }
                case ByteOps.DCMPL:
                case ByteOps.DCMPG: {
                    machine.popArgs(frame, mod.agus.jcoderz.dx.rop.type.Type.DOUBLE, mod.agus.jcoderz.dx.rop.type.Type.DOUBLE);
                    break;
                }
                case ByteOps.IASTORE: {
                    ExecutionStack stack = frame.getStack();
                    int peekDepth = type.isCategory1() ? 2 : 3;
                    mod.agus.jcoderz.dx.rop.type.Type foundArrayType = stack.peekType(peekDepth);
                    boolean foundArrayLocal = stack.peekLocal(peekDepth);

                    mod.agus.jcoderz.dx.rop.type.Type requiredArrayType =
                        requiredArrayTypeFor(type, foundArrayType);

                    if (foundArrayLocal) {
                        type = (requiredArrayType == mod.agus.jcoderz.dx.rop.type.Type.KNOWN_NULL)
                            ? mod.agus.jcoderz.dx.rop.type.Type.KNOWN_NULL
                            : requiredArrayType.getComponentType();
                    }

                    machine.popArgs(frame, requiredArrayType, mod.agus.jcoderz.dx.rop.type.Type.INT, type);
                    break;
                }
                case ByteOps.POP2:
                case ByteOps.DUP2: {
                    ExecutionStack stack = frame.getStack();
                    int pattern;

                    if (stack.peekType(0).isCategory2()) {

                        machine.popArgs(frame, 1);
                        pattern = 0x11;
                    } else if (stack.peekType(1).isCategory1()) {

                        machine.popArgs(frame, 2);
                        pattern = 0x2121;
                    } else {
                        throw illegalTos();
                    }

                    if (opcode == ByteOps.DUP2) {
                        machine.auxIntArg(pattern);
                    }
                    break;
                }
                case ByteOps.DUP: {
                    mod.agus.jcoderz.dx.rop.type.Type peekType = frame.getStack().peekType(0);

                    if (peekType.isCategory2()) {
                        throw illegalTos();
                    }

                    machine.popArgs(frame, 1);
                    machine.auxIntArg(0x11);
                    break;
                }
                case ByteOps.DUP_X1: {
                    ExecutionStack stack = frame.getStack();

                    if (!(stack.peekType(0).isCategory1() &&
                          stack.peekType(1).isCategory1())) {
                        throw illegalTos();
                    }

                    machine.popArgs(frame, 2);
                    machine.auxIntArg(0x212);
                    break;
                }
                case ByteOps.DUP_X2: {
                    ExecutionStack stack = frame.getStack();

                    if (stack.peekType(0).isCategory2()) {
                        throw illegalTos();
                    }

                    if (stack.peekType(1).isCategory2()) {

                        machine.popArgs(frame, 2);
                        machine.auxIntArg(0x212);
                    } else if (stack.peekType(2).isCategory1()) {

                        machine.popArgs(frame, 3);
                        machine.auxIntArg(0x3213);
                    } else {
                        throw illegalTos();
                    }
                    break;
                }
                case ByteOps.DUP2_X1: {
                    ExecutionStack stack = frame.getStack();

                    if (stack.peekType(0).isCategory2()) {

                        if (stack.peekType(2).isCategory2()) {
                            throw illegalTos();
                        }
                        machine.popArgs(frame, 2);
                        machine.auxIntArg(0x212);
                    } else {

                        if (stack.peekType(1).isCategory2() ||
                            stack.peekType(2).isCategory2()) {
                            throw illegalTos();
                        }
                        machine.popArgs(frame, 3);
                        machine.auxIntArg(0x32132);
                    }
                    break;
                }
                case ByteOps.DUP2_X2: {
                    ExecutionStack stack = frame.getStack();

                    if (stack.peekType(0).isCategory2()) {
                        if (stack.peekType(2).isCategory2()) {

                            machine.popArgs(frame, 2);
                            machine.auxIntArg(0x212);
                        } else if (stack.peekType(3).isCategory1()) {

                            machine.popArgs(frame, 3);
                            machine.auxIntArg(0x3213);
                        } else {
                            throw illegalTos();
                        }
                    } else if (stack.peekType(1).isCategory1()) {
                        if (stack.peekType(2).isCategory2()) {

                            machine.popArgs(frame, 3);
                            machine.auxIntArg(0x32132);
                        } else if (stack.peekType(3).isCategory1()) {

                            machine.popArgs(frame, 4);
                            machine.auxIntArg(0x432143);
                        } else {
                            throw illegalTos();
                        }
                    } else {
                        throw illegalTos();
                    }
                    break;
                }
                case ByteOps.SWAP: {
                    ExecutionStack stack = frame.getStack();

                    if (!(stack.peekType(0).isCategory1() &&
                          stack.peekType(1).isCategory1())) {
                        throw illegalTos();
                    }

                    machine.popArgs(frame, 2);
                    machine.auxIntArg(0x12);
                    break;
                }
                default: {
                    visitInvalid(opcode, offset, length);
                    return;
                }
            }

            machine.auxType(type);
            machine.run(frame, offset, opcode);
        }

        private void checkReturnType(mod.agus.jcoderz.dx.rop.type.Type encountered) {
            mod.agus.jcoderz.dx.rop.type.Type returnType = machine.getPrototype().getReturnType();

            if (!Merger.isPossiblyAssignableFrom(returnType, encountered)) {
                fail("return type mismatch: prototype " +
                     "indicates " + returnType.toHuman() +
                     ", but encountered type " + encountered.toHuman());
            }
        }

        @Override
        public void visitLocal(int opcode, int offset, int length,
                               int idx, mod.agus.jcoderz.dx.rop.type.Type type, int value) {
            int localOffset =
                (opcode == ByteOps.ISTORE) ? (offset + length) : offset;
            LocalVariableList.Item local =
                localVariables.pcAndIndexToLocal(localOffset, idx);
            mod.agus.jcoderz.dx.rop.type.Type localType;

            if (local != null) {
                localType = local.getType();
                if (localType.getBasicFrameType() !=
                        type.getBasicFrameType()) {

                    local = null;
                    localType = type;
                }
            } else {
                localType = type;
            }

            switch (opcode) {
                case ByteOps.ILOAD:
                case ByteOps.RET: {
                    machine.localArg(frame, idx);
                    machine.localInfo(local != null);
                    machine.auxType(type);
                    break;
                }
                case ByteOps.ISTORE: {
                    mod.agus.jcoderz.dx.rop.code.LocalItem item
                            = (local == null) ? null : local.getLocalItem();
                    machine.popArgs(frame, type);
                    machine.auxType(type);
                    machine.localTarget(idx, localType, item);
                    break;
                }
                case ByteOps.IINC: {
                    LocalItem item
                            = (local == null) ? null : local.getLocalItem();
                    machine.localArg(frame, idx);
                    machine.localTarget(idx, localType, item);
                    machine.auxType(type);
                    machine.auxIntArg(value);
                    machine.auxCstArg(CstInteger.make(value));
                    break;
                }
                default: {
                    visitInvalid(opcode, offset, length);
                    return;
                }
            }

            machine.run(frame, offset, opcode);
        }

        @Override
        public void visitConstant(int opcode, int offset, int length,
                                  mod.agus.jcoderz.dx.rop.cst.Constant cst, int value) {
            switch (opcode) {
                case ByteOps.ANEWARRAY: {
                    machine.popArgs(frame, mod.agus.jcoderz.dx.rop.type.Type.INT);
                    break;
                }
                case ByteOps.PUTSTATIC: {
                    mod.agus.jcoderz.dx.rop.type.Type fieldType = ((mod.agus.jcoderz.dx.rop.cst.CstFieldRef) cst).getType();
                    machine.popArgs(frame, fieldType);
                    break;
                }
                case ByteOps.GETFIELD:
                case ByteOps.CHECKCAST:
                case ByteOps.INSTANCEOF: {
                    machine.popArgs(frame, mod.agus.jcoderz.dx.rop.type.Type.OBJECT);
                    break;
                }
                case ByteOps.PUTFIELD: {
                    mod.agus.jcoderz.dx.rop.type.Type fieldType = ((CstFieldRef) cst).getType();
                    machine.popArgs(frame, mod.agus.jcoderz.dx.rop.type.Type.OBJECT, fieldType);
                    break;
                }
                case ByteOps.INVOKEINTERFACE:
                case ByteOps.INVOKEVIRTUAL:
                case ByteOps.INVOKESPECIAL:
                case ByteOps.INVOKESTATIC: {
                    if (cst instanceof mod.agus.jcoderz.dx.rop.cst.CstInterfaceMethodRef) {
                        cst = ((CstInterfaceMethodRef) cst).toMethodRef();
                        checkInvokeInterfaceSupported(opcode, (mod.agus.jcoderz.dx.rop.cst.CstMethodRef) cst);
                    }

                    if (cst instanceof mod.agus.jcoderz.dx.rop.cst.CstMethodRef) {
                        mod.agus.jcoderz.dx.rop.cst.CstMethodRef methodRef = (mod.agus.jcoderz.dx.rop.cst.CstMethodRef) cst;
                        if (methodRef.isSignaturePolymorphic()) {
                            checkInvokeSignaturePolymorphic(opcode);
                        }
                    }

                    boolean staticMethod = (opcode == ByteOps.INVOKESTATIC);
                    mod.agus.jcoderz.dx.rop.type.Prototype prototype
                        = ((mod.agus.jcoderz.dx.rop.cst.CstMethodRef) cst).getPrototype(staticMethod);
                    machine.popArgs(frame, prototype);
                    break;
                }
                case ByteOps.INVOKEDYNAMIC: {
                    checkInvokeDynamicSupported(opcode);
                    mod.agus.jcoderz.dx.rop.cst.CstInvokeDynamic invokeDynamicRef = (CstInvokeDynamic) cst;
                    mod.agus.jcoderz.dx.rop.type.Prototype prototype = invokeDynamicRef.getPrototype();
                    machine.popArgs(frame, prototype);


                    cst = invokeDynamicRef.addReference();
                    break;
                }
                case ByteOps.MULTIANEWARRAY: {
                    mod.agus.jcoderz.dx.rop.type.Prototype prototype =
                        Prototype.internInts(mod.agus.jcoderz.dx.rop.type.Type.VOID, value);
                    machine.popArgs(frame, prototype);
                    break;
                }
                case ByteOps.LDC:
                case ByteOps.LDC_W: {
                    if ((cst instanceof CstMethodHandle || cst instanceof CstProtoRef)) {
                        checkConstMethodHandleSupported(cst);
                    }
                    machine.clearArgs();
                    break;
                }
                default: {
                    machine.clearArgs();
                    break;
                }
            }

            machine.auxIntArg(value);
            machine.auxCstArg(cst);
            machine.run(frame, offset, opcode);
        }

        @Override
        public void visitBranch(int opcode, int offset, int length,
                int target) {
            switch (opcode) {
                case ByteOps.IFEQ:
                case ByteOps.IFNE:
                case ByteOps.IFLT:
                case ByteOps.IFGE:
                case ByteOps.IFGT:
                case ByteOps.IFLE: {
                    machine.popArgs(frame, mod.agus.jcoderz.dx.rop.type.Type.INT);
                    break;
                }
                case ByteOps.IFNULL:
                case ByteOps.IFNONNULL: {
                    machine.popArgs(frame, mod.agus.jcoderz.dx.rop.type.Type.OBJECT);
                    break;
                }
                case ByteOps.IF_ICMPEQ:
                case ByteOps.IF_ICMPNE:
                case ByteOps.IF_ICMPLT:
                case ByteOps.IF_ICMPGE:
                case ByteOps.IF_ICMPGT:
                case ByteOps.IF_ICMPLE: {
                    machine.popArgs(frame, mod.agus.jcoderz.dx.rop.type.Type.INT, mod.agus.jcoderz.dx.rop.type.Type.INT);
                    break;
                }
                case ByteOps.IF_ACMPEQ:
                case ByteOps.IF_ACMPNE: {
                    machine.popArgs(frame, mod.agus.jcoderz.dx.rop.type.Type.OBJECT, mod.agus.jcoderz.dx.rop.type.Type.OBJECT);
                    break;
                }
                case ByteOps.GOTO:
                case ByteOps.JSR:
                case ByteOps.GOTO_W:
                case ByteOps.JSR_W: {
                    machine.clearArgs();
                    break;
                }
                default: {
                    visitInvalid(opcode, offset, length);
                    return;
                }
            }

            machine.auxTargetArg(target);
            machine.run(frame, offset, opcode);
        }

        @Override
        public void visitSwitch(int opcode, int offset, int length,
                SwitchList cases, int padding) {
            machine.popArgs(frame, mod.agus.jcoderz.dx.rop.type.Type.INT);
            machine.auxIntArg(padding);
            machine.auxSwitchArg(cases);
            machine.run(frame, offset, opcode);
        }

        @Override
        public void visitNewarray(int offset, int length, CstType type,
                ArrayList<mod.agus.jcoderz.dx.rop.cst.Constant> initValues) {
            machine.popArgs(frame, Type.INT);
            machine.auxInitValues(initValues);
            machine.auxCstArg(type);
            machine.run(frame, offset, ByteOps.NEWARRAY);
        }

        @Override
        public void setPreviousOffset(int offset) {
            previousOffset = offset;
        }

        @Override
        public int getPreviousOffset() {
            return previousOffset;
        }
    }

    private void checkConstMethodHandleSupported(Constant cst) throws SimException {
        if (!dexOptions.apiIsSupported(DexFormat.API_CONST_METHOD_HANDLE)) {
            fail(String.format("invalid constant type %s requires --min-sdk-version >= %d " +
                               "(currently %d)",
                               cst.typeName(), DexFormat.API_CONST_METHOD_HANDLE,
                               dexOptions.minSdkVersion));
        }
    }

    private void checkInvokeDynamicSupported(int opcode) throws SimException {
        if (!dexOptions.apiIsSupported(DexFormat.API_METHOD_HANDLES)) {
            fail(String.format("invalid opcode %02x - invokedynamic requires " +
                               "--min-sdk-version >= %d (currently %d)",
                               opcode, DexFormat.API_METHOD_HANDLES, dexOptions.minSdkVersion));
        }
    }

    private void checkInvokeInterfaceSupported(final int opcode, CstMethodRef callee) {
        if (opcode == ByteOps.INVOKEINTERFACE) {

            return;
        }

        if (dexOptions.apiIsSupported(DexFormat.API_INVOKE_INTERFACE_METHODS)) {


            return;
        }
























        boolean softFail = dexOptions.allowAllInterfaceMethodInvokes;
        if (opcode == ByteOps.INVOKESTATIC) {
            softFail &= dexOptions.apiIsSupported(DexFormat.API_INVOKE_STATIC_INTERFACE_METHODS);
        } else {
            assert (opcode == ByteOps.INVOKESPECIAL) || (opcode == ByteOps.INVOKEVIRTUAL);
        }




        String invokeKind = (opcode == ByteOps.INVOKESTATIC) ? "static" : "default";
        if (softFail) {


            String reason =
                    String.format(
                        "invoking a %s interface method %s.%s strictly requires " +
                        "--min-sdk-version >= %d (experimental at current API level %d)",
                        invokeKind, callee.getDefiningClass().toHuman(), callee.getNat().toHuman(),
                        DexFormat.API_INVOKE_INTERFACE_METHODS, dexOptions.minSdkVersion);
            warn(reason);
        } else {
            String reason =
                    String.format(
                        "invoking a %s interface method %s.%s strictly requires " +
                        "--min-sdk-version >= %d (blocked at current API level %d)",
                    invokeKind, callee.getDefiningClass().toHuman(), callee.getNat().toHuman(),
                    DexFormat.API_INVOKE_INTERFACE_METHODS, dexOptions.minSdkVersion);
            fail(reason);
        }
    }

    private void checkInterfaceMethodDeclaration(ConcreteMethod declaredMethod) {
        if (!dexOptions.apiIsSupported(DexFormat.API_DEFINE_INTERFACE_METHODS)) {
            String reason
                = String.format(
                    "defining a %s interface method requires --min-sdk-version >= %d (currently %d)"
                    + " for interface methods: %s.%s",
                    declaredMethod.isStaticMethod() ? "static" : "default",
                    DexFormat.API_DEFINE_INTERFACE_METHODS, dexOptions.minSdkVersion,
                    declaredMethod.getDefiningClass().toHuman(), declaredMethod.getNat().toHuman());
            warn(reason);
        }
    }

    private void checkInvokeSignaturePolymorphic(final int opcode) {
        if (!dexOptions.apiIsSupported(DexFormat.API_METHOD_HANDLES)) {
            fail(String.format(
                "invoking a signature-polymorphic requires --min-sdk-version >= %d (currently %d)",
                DexFormat.API_METHOD_HANDLES, dexOptions.minSdkVersion));
        } else if (opcode != ByteOps.INVOKEVIRTUAL) {
            fail("Unsupported signature polymorphic invocation (" + ByteOps.opName(opcode) + ")");
        }
    }

    private void fail(String reason) {
        String message = String.format("ERROR in %s.%s: %s", method.getDefiningClass().toHuman(),
                                       method.getNat().toHuman(), reason);
        throw new SimException(message);
    }

    private void warn(String reason) {
        String warning = String.format("WARNING in %s.%s: %s", method.getDefiningClass().toHuman(),
                                       method.getNat().toHuman(), reason);
        dexOptions.err.println(warning);
    }
}
