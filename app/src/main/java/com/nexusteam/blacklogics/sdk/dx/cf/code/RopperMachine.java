
package mod.agus.jcoderz.dx.cf.code;

import mod.agus.jcoderz.dx.cf.iface.Method;
import mod.agus.jcoderz.dx.cf.iface.MethodList;

import java.util.ArrayList;

import mod.agus.jcoderz.dx.rop.code.AccessFlags;
import mod.agus.jcoderz.dx.rop.code.FillArrayDataInsn;
import mod.agus.jcoderz.dx.rop.code.Insn;
import mod.agus.jcoderz.dx.rop.code.InvokePolymorphicInsn;
import mod.agus.jcoderz.dx.rop.code.PlainCstInsn;
import mod.agus.jcoderz.dx.rop.code.PlainInsn;
import mod.agus.jcoderz.dx.rop.code.RegOps;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.Rop;
import mod.agus.jcoderz.dx.rop.code.Rops;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.rop.code.SwitchInsn;
import mod.agus.jcoderz.dx.rop.code.ThrowingCstInsn;
import mod.agus.jcoderz.dx.rop.code.ThrowingInsn;
import mod.agus.jcoderz.dx.rop.code.TranslationAdvice;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstCallSiteRef;
import mod.agus.jcoderz.dx.rop.cst.CstFieldRef;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;
import mod.agus.jcoderz.dx.rop.cst.CstMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstNat;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeBearer;
import mod.agus.jcoderz.dx.rop.type.TypeList;
import mod.agus.jcoderz.dx.util.IntList;

 final class RopperMachine extends ValueAwareMachine {
    private static final mod.agus.jcoderz.dx.rop.cst.CstType ARRAY_REFLECT_TYPE =
        new mod.agus.jcoderz.dx.rop.cst.CstType(mod.agus.jcoderz.dx.rop.type.Type.internClassName("java/lang/reflect/Array"));

    private static final mod.agus.jcoderz.dx.rop.cst.CstMethodRef MULTIANEWARRAY_METHOD =
        new mod.agus.jcoderz.dx.rop.cst.CstMethodRef(ARRAY_REFLECT_TYPE,
                         new CstNat(new mod.agus.jcoderz.dx.rop.cst.CstString("newInstance"),
                                    new CstString("(Ljava/lang/Class;[I)" +
                                                "Ljava/lang/Object;")));

    private final mod.agus.jcoderz.dx.cf.code.Ropper ropper;

    private final ConcreteMethod method;

    private final MethodList methods;

    private final mod.agus.jcoderz.dx.rop.code.TranslationAdvice advice;

    private final int maxLocals;

    private final ArrayList<mod.agus.jcoderz.dx.rop.code.Insn> insns;

    private mod.agus.jcoderz.dx.rop.type.TypeList catches;

    private boolean catchesUsed;

    private boolean returns;

    private int primarySuccessorIndex;

    private int extraBlockCount;

    private boolean hasJsr;

    private boolean blockCanThrow;


    private ReturnAddress returnAddress;

    private mod.agus.jcoderz.dx.rop.code.Rop returnOp;

    private mod.agus.jcoderz.dx.rop.code.SourcePosition returnPosition;

    public RopperMachine(Ropper ropper, ConcreteMethod method,
                         TranslationAdvice advice, MethodList methods) {
        super(method.getEffectiveDescriptor());

        if (methods == null) {
            throw new NullPointerException("methods == null");
        }

        if (ropper == null) {
            throw new NullPointerException("ropper == null");
        }

        if (advice == null) {
            throw new NullPointerException("advice == null");
        }

        this.ropper = ropper;
        this.method = method;
        this.methods = methods;
        this.advice = advice;
        this.maxLocals = method.getMaxLocals();
        this.insns = new ArrayList<mod.agus.jcoderz.dx.rop.code.Insn>(25);
        this.catches = null;
        this.catchesUsed = false;
        this.returns = false;
        this.primarySuccessorIndex = -1;
        this.extraBlockCount = 0;
        this.blockCanThrow = false;
        this.returnOp = null;
        this.returnPosition = null;
    }

    public ArrayList<mod.agus.jcoderz.dx.rop.code.Insn> getInsns() {
        return insns;
    }

    public mod.agus.jcoderz.dx.rop.code.Rop getReturnOp() {
        return returnOp;
    }

    public mod.agus.jcoderz.dx.rop.code.SourcePosition getReturnPosition() {
        return returnPosition;
    }

    public void startBlock(mod.agus.jcoderz.dx.rop.type.TypeList catches) {
        this.catches = catches;

        insns.clear();
        catchesUsed = false;
        returns = false;
        primarySuccessorIndex = 0;
        extraBlockCount = 0;
        blockCanThrow = false;
        hasJsr = false;
        returnAddress = null;
    }

    public boolean wereCatchesUsed() {
        return catchesUsed;
    }

    public boolean returns() {
        return returns;
    }

    public int getPrimarySuccessorIndex() {
        return primarySuccessorIndex;
    }

    public int getExtraBlockCount() {
        return extraBlockCount;
    }

    public boolean canThrow() {
        return blockCanThrow;
    }

    public boolean hasJsr() {
        return hasJsr;
    }

    public boolean hasRet() {
        return returnAddress != null;
    }

    public ReturnAddress getReturnAddress() {
        return returnAddress;
    }

    @Override
    public void run(Frame frame, int offset, int opcode) {
        int stackPointer = maxLocals + frame.getStack().size();


        mod.agus.jcoderz.dx.rop.code.RegisterSpecList sources = getSources(opcode, stackPointer);
        int sourceCount = sources.size();

        super.run(frame, offset, opcode);

        mod.agus.jcoderz.dx.rop.code.SourcePosition pos = method.makeSourcePosistion(offset);
        mod.agus.jcoderz.dx.rop.code.RegisterSpec localTarget = getLocalTarget(opcode == mod.agus.jcoderz.dx.cf.code.ByteOps.ISTORE);
        int destCount = resultCount();
        mod.agus.jcoderz.dx.rop.code.RegisterSpec dest;

        if (destCount == 0) {
            dest = null;
            switch (opcode) {
                case mod.agus.jcoderz.dx.cf.code.ByteOps.POP:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.POP2: {

                    return;
                }
            }
        } else if (localTarget != null) {
            dest = localTarget;
        } else if (destCount == 1) {
            dest = mod.agus.jcoderz.dx.rop.code.RegisterSpec.make(stackPointer, result(0));
        } else {
            int scratchAt = ropper.getFirstTempStackReg();
            mod.agus.jcoderz.dx.rop.code.RegisterSpec[] scratchRegs = new mod.agus.jcoderz.dx.rop.code.RegisterSpec[sourceCount];

            for (int i = 0; i < sourceCount; i++) {
                mod.agus.jcoderz.dx.rop.code.RegisterSpec src = sources.get(i);
                mod.agus.jcoderz.dx.rop.type.TypeBearer type = src.getTypeBearer();
                mod.agus.jcoderz.dx.rop.code.RegisterSpec scratch = src.withReg(scratchAt);
                insns.add(new mod.agus.jcoderz.dx.rop.code.PlainInsn(mod.agus.jcoderz.dx.rop.code.Rops.opMove(type), pos, scratch, src));
                scratchRegs[i] = scratch;
                scratchAt += src.getCategory();
            }

            for (int pattern = getAuxInt(); pattern != 0; pattern >>= 4) {
                int which = (pattern & 0x0f) - 1;
                mod.agus.jcoderz.dx.rop.code.RegisterSpec scratch = scratchRegs[which];
                mod.agus.jcoderz.dx.rop.type.TypeBearer type = scratch.getTypeBearer();
                insns.add(new mod.agus.jcoderz.dx.rop.code.PlainInsn(mod.agus.jcoderz.dx.rop.code.Rops.opMove(type), pos,
                                        scratch.withReg(stackPointer),
                                        scratch));
                stackPointer += type.getType().getCategory();
            }
            return;
        }

        mod.agus.jcoderz.dx.rop.type.TypeBearer destType = (dest != null) ? dest : mod.agus.jcoderz.dx.rop.type.Type.VOID;
        mod.agus.jcoderz.dx.rop.cst.Constant cst = getAuxCst();
        int ropOpcode;
        mod.agus.jcoderz.dx.rop.code.Rop rop;
        mod.agus.jcoderz.dx.rop.code.Insn insn;

        if (opcode == mod.agus.jcoderz.dx.cf.code.ByteOps.MULTIANEWARRAY) {
            blockCanThrow = true;



            extraBlockCount = 6;

            mod.agus.jcoderz.dx.rop.code.RegisterSpec dimsReg =
                mod.agus.jcoderz.dx.rop.code.RegisterSpec.make(dest.getNextReg(), mod.agus.jcoderz.dx.rop.type.Type.INT_ARRAY);
            rop = mod.agus.jcoderz.dx.rop.code.Rops.opFilledNewArray(mod.agus.jcoderz.dx.rop.type.Type.INT_ARRAY, sourceCount);
            insn = new mod.agus.jcoderz.dx.rop.code.ThrowingCstInsn(rop, pos, sources, catches,
                    mod.agus.jcoderz.dx.rop.cst.CstType.INT_ARRAY);
            insns.add(insn);


            rop = mod.agus.jcoderz.dx.rop.code.Rops.opMoveResult(mod.agus.jcoderz.dx.rop.type.Type.INT_ARRAY);
            insn = new mod.agus.jcoderz.dx.rop.code.PlainInsn(rop, pos, dimsReg, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY);
            insns.add(insn);


            mod.agus.jcoderz.dx.rop.type.Type componentType = ((mod.agus.jcoderz.dx.rop.cst.CstType) cst).getClassType();
            for (int i = 0; i < sourceCount; i++) {
                componentType = componentType.getComponentType();
            }

            mod.agus.jcoderz.dx.rop.code.RegisterSpec classReg =
                mod.agus.jcoderz.dx.rop.code.RegisterSpec.make(dest.getReg(), mod.agus.jcoderz.dx.rop.type.Type.CLASS);

            if (componentType.isPrimitive()) {
                mod.agus.jcoderz.dx.rop.cst.CstFieldRef typeField =
                    CstFieldRef.forPrimitiveType(componentType);
                insn = new mod.agus.jcoderz.dx.rop.code.ThrowingCstInsn(mod.agus.jcoderz.dx.rop.code.Rops.GET_STATIC_OBJECT, pos,
                                           mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY,
                                           catches, typeField);
            } else {
                insn = new mod.agus.jcoderz.dx.rop.code.ThrowingCstInsn(mod.agus.jcoderz.dx.rop.code.Rops.CONST_OBJECT, pos,
                                           mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY, catches,
                                           new mod.agus.jcoderz.dx.rop.cst.CstType(componentType));
            }

            insns.add(insn);


            rop = mod.agus.jcoderz.dx.rop.code.Rops.opMoveResultPseudo(classReg.getType());
            insn = new mod.agus.jcoderz.dx.rop.code.PlainInsn(rop, pos, classReg, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY);
            insns.add(insn);


            mod.agus.jcoderz.dx.rop.code.RegisterSpec objectReg =
                mod.agus.jcoderz.dx.rop.code.RegisterSpec.make(dest.getReg(), mod.agus.jcoderz.dx.rop.type.Type.OBJECT);

            insn = new mod.agus.jcoderz.dx.rop.code.ThrowingCstInsn(
                    mod.agus.jcoderz.dx.rop.code.Rops.opInvokeStatic(MULTIANEWARRAY_METHOD.getPrototype()),
                    pos, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.make(classReg, dimsReg),
                    catches, MULTIANEWARRAY_METHOD);
            insns.add(insn);


            rop = mod.agus.jcoderz.dx.rop.code.Rops.opMoveResult(MULTIANEWARRAY_METHOD.getPrototype()
                    .getReturnType());
            insn = new mod.agus.jcoderz.dx.rop.code.PlainInsn(rop, pos, objectReg, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY);
            insns.add(insn);


            opcode = mod.agus.jcoderz.dx.cf.code.ByteOps.CHECKCAST;
            sources = mod.agus.jcoderz.dx.rop.code.RegisterSpecList.make(objectReg);
        } else if (opcode == mod.agus.jcoderz.dx.cf.code.ByteOps.JSR) {

            hasJsr = true;
            return;
        } else if (opcode == mod.agus.jcoderz.dx.cf.code.ByteOps.RET) {
            try {
                returnAddress = (ReturnAddress)arg(0);
            } catch (ClassCastException ex) {
                throw new RuntimeException(
                        "Argument to RET was not a ReturnAddress", ex);
            }

            return;
        }

        ropOpcode = jopToRopOpcode(opcode, cst);
        rop = mod.agus.jcoderz.dx.rop.code.Rops.ropFor(ropOpcode, destType, sources, cst);

        mod.agus.jcoderz.dx.rop.code.Insn moveResult = null;
        if (dest != null && rop.isCallLike()) {
            extraBlockCount++;

            Type returnType;
            if (rop.getOpcode() == mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_CUSTOM) {
                returnType = ((CstCallSiteRef) cst).getReturnType();
            } else {
                returnType = ((mod.agus.jcoderz.dx.rop.cst.CstMethodRef) cst).getPrototype().getReturnType();
            }
            moveResult = new mod.agus.jcoderz.dx.rop.code.PlainInsn(mod.agus.jcoderz.dx.rop.code.Rops.opMoveResult(returnType),
                                       pos, dest, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY);

            dest = null;
        } else if (dest != null && rop.canThrow()) {
            extraBlockCount++;

            moveResult = new mod.agus.jcoderz.dx.rop.code.PlainInsn(
                    mod.agus.jcoderz.dx.rop.code.Rops.opMoveResultPseudo(dest.getTypeBearer()),
                    pos, dest, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY);

            dest = null;
        }
        if (ropOpcode == mod.agus.jcoderz.dx.rop.code.RegOps.NEW_ARRAY) {
            cst = CstType.intern(rop.getResult());
        } else if ((cst == null) && (sourceCount == 2)) {
            mod.agus.jcoderz.dx.rop.type.TypeBearer firstType = sources.get(0).getTypeBearer();
            mod.agus.jcoderz.dx.rop.type.TypeBearer lastType = sources.get(1).getTypeBearer();

            if ((lastType.isConstant() || firstType.isConstant()) &&
                 advice.hasConstantOperation(rop, sources.get(0),
                                             sources.get(1))) {

                if (lastType.isConstant()) {
                    cst = (mod.agus.jcoderz.dx.rop.cst.Constant) lastType;
                    sources = sources.withoutLast();


                    if (rop.getOpcode() == mod.agus.jcoderz.dx.rop.code.RegOps.SUB) {
                        ropOpcode = mod.agus.jcoderz.dx.rop.code.RegOps.ADD;
                        mod.agus.jcoderz.dx.rop.cst.CstInteger cstInt = (mod.agus.jcoderz.dx.rop.cst.CstInteger) lastType;
                        cst = CstInteger.make(-cstInt.getValue());
                    }
                } else {
                    cst = (mod.agus.jcoderz.dx.rop.cst.Constant) firstType;
                    sources = sources.withoutFirst();
                }

                rop = mod.agus.jcoderz.dx.rop.code.Rops.ropFor(ropOpcode, destType, sources, cst);
            }
        }

        SwitchList cases = getAuxCases();
        ArrayList<mod.agus.jcoderz.dx.rop.cst.Constant> initValues = getInitValues();
        boolean canThrow = rop.canThrow();

        blockCanThrow |= canThrow;

        if (cases != null) {
            if (cases.size() == 0) {

                insn = new mod.agus.jcoderz.dx.rop.code.PlainInsn(mod.agus.jcoderz.dx.rop.code.Rops.GOTO, pos, null,
                                     mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY);
                primarySuccessorIndex = 0;
            } else {
                IntList values = cases.getValues();
                insn = new SwitchInsn(rop, pos, dest, sources, values);
                primarySuccessorIndex = values.size();
            }
        } else if (ropOpcode == mod.agus.jcoderz.dx.rop.code.RegOps.RETURN) {
            if (sources.size() != 0) {
                mod.agus.jcoderz.dx.rop.code.RegisterSpec source = sources.get(0);
                TypeBearer type = source.getTypeBearer();
                if (source.getReg() != 0) {
                    insns.add(new mod.agus.jcoderz.dx.rop.code.PlainInsn(mod.agus.jcoderz.dx.rop.code.Rops.opMove(type), pos,
                                            mod.agus.jcoderz.dx.rop.code.RegisterSpec.make(0, type),
                                            source));
                }
            }
            insn = new mod.agus.jcoderz.dx.rop.code.PlainInsn(mod.agus.jcoderz.dx.rop.code.Rops.GOTO, pos, null, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY);
            primarySuccessorIndex = 0;
            updateReturnOp(rop, pos);
            returns = true;
        } else if (cst != null) {
            if (canThrow) {
                if (rop.getOpcode() == mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_POLYMORPHIC) {
                    insn = makeInvokePolymorphicInsn(rop, pos, sources, catches, cst);
                } else {
                    insn = new ThrowingCstInsn(rop, pos, sources, catches, cst);
                }
                catchesUsed = true;
                primarySuccessorIndex = catches.size();
            } else {
                insn = new PlainCstInsn(rop, pos, dest, sources, cst);
            }
        } else if (canThrow) {
            insn = new ThrowingInsn(rop, pos, sources, catches);
            catchesUsed = true;
            if (opcode == mod.agus.jcoderz.dx.cf.code.ByteOps.ATHROW) {
                primarySuccessorIndex = -1;
            } else {
                primarySuccessorIndex = catches.size();
            }
        } else {
            insn = new PlainInsn(rop, pos, dest, sources);
        }

        insns.add(insn);

        if (moveResult != null) {
            insns.add(moveResult);
        }

        if (initValues != null) {
            extraBlockCount++;
            insn = new FillArrayDataInsn(Rops.FILL_ARRAY_DATA, pos,
                    mod.agus.jcoderz.dx.rop.code.RegisterSpecList.make(moveResult.getResult()), initValues,
                    cst);
            insns.add(insn);
        }
    }

    private mod.agus.jcoderz.dx.rop.code.RegisterSpecList getSources(int opcode, int stackPointer) {
        int count = argCount();

        if (count == 0) {

            return mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY;
        }

        int localIndex = getLocalIndex();
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList sources;

        if (localIndex >= 0) {

            sources = new mod.agus.jcoderz.dx.rop.code.RegisterSpecList(1);
            sources.set(0, mod.agus.jcoderz.dx.rop.code.RegisterSpec.make(localIndex, arg(0)));
        } else {
            sources = new mod.agus.jcoderz.dx.rop.code.RegisterSpecList(count);
            int regAt = stackPointer;
            for (int i = 0; i < count; i++) {
                mod.agus.jcoderz.dx.rop.code.RegisterSpec spec = mod.agus.jcoderz.dx.rop.code.RegisterSpec.make(regAt, arg(i));
                sources.set(i, spec);
                regAt += spec.getCategory();
            }

            switch (opcode) {
                case mod.agus.jcoderz.dx.cf.code.ByteOps.IASTORE: {
                    if (count != 3) {
                        throw new RuntimeException("shouldn't happen");
                    }
                    mod.agus.jcoderz.dx.rop.code.RegisterSpec array = sources.get(0);
                    mod.agus.jcoderz.dx.rop.code.RegisterSpec index = sources.get(1);
                    mod.agus.jcoderz.dx.rop.code.RegisterSpec value = sources.get(2);
                    sources.set(0, value);
                    sources.set(1, array);
                    sources.set(2, index);
                    break;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.PUTFIELD: {
                    if (count != 2) {
                        throw new RuntimeException("shouldn't happen");
                    }
                    mod.agus.jcoderz.dx.rop.code.RegisterSpec obj = sources.get(0);
                    RegisterSpec value = sources.get(1);
                    sources.set(0, value);
                    sources.set(1, obj);
                    break;
                }
            }
        }

        sources.setImmutable();
        return sources;
    }

    private void updateReturnOp(mod.agus.jcoderz.dx.rop.code.Rop op, mod.agus.jcoderz.dx.rop.code.SourcePosition pos) {
        if (op == null) {
            throw new NullPointerException("op == null");
        }

        if (pos == null) {
            throw new NullPointerException("pos == null");
        }

        if (returnOp == null) {
            returnOp = op;
            returnPosition = pos;
        } else {
            if (returnOp != op) {
                throw new SimException("return op mismatch: " + op + ", " +
                                       returnOp);
            }

            if (pos.getLine() > returnPosition.getLine()) {

                returnPosition = pos;
            }
        }
    }

    private int jopToRopOpcode(int jop, mod.agus.jcoderz.dx.rop.cst.Constant cst) {
        switch (jop) {
            case mod.agus.jcoderz.dx.cf.code.ByteOps.POP:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.POP2:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.DUP:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.DUP_X1:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.DUP_X2:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.DUP2:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.DUP2_X1:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.DUP2_X2:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.SWAP:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.JSR:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.RET:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.MULTIANEWARRAY: {

                break;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.NOP: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.NOP;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.LDC:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.LDC2_W: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.CONST;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.ILOAD:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.ISTORE: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.MOVE;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IALOAD: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.AGET;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IASTORE: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.APUT;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IADD:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IINC: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.ADD;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.ISUB: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.SUB;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IMUL: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.MUL;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IDIV: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.DIV;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IREM: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.REM;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.INEG: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.NEG;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.ISHL: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.SHL;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.ISHR: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.SHR;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IUSHR: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.USHR;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IAND: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.AND;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IOR: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.OR;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IXOR: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.XOR;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.I2L:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.I2F:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.I2D:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.L2I:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.L2F:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.L2D:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.F2I:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.F2L:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.F2D:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.D2I:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.D2L:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.D2F: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.CONV;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.I2B: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.TO_BYTE;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.I2C: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.TO_CHAR;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.I2S: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.TO_SHORT;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.LCMP:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.FCMPL:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.DCMPL: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.CMPL;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.FCMPG:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.DCMPG: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.CMPG;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IFEQ:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IF_ICMPEQ:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IF_ACMPEQ:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IFNULL: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.IF_EQ;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IFNE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IF_ICMPNE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IF_ACMPNE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IFNONNULL: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.IF_NE;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IFLT:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IF_ICMPLT: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.IF_LT;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IFGE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IF_ICMPGE: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.IF_GE;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IFGT:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IF_ICMPGT: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.IF_GT;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IFLE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IF_ICMPLE: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.IF_LE;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.GOTO: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.GOTO;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.LOOKUPSWITCH: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.SWITCH;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IRETURN:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.RETURN: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.RETURN;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.GETSTATIC: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.GET_STATIC;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.PUTSTATIC: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.PUT_STATIC;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.GETFIELD: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.GET_FIELD;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.PUTFIELD: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.PUT_FIELD;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.INVOKEVIRTUAL: {
                mod.agus.jcoderz.dx.rop.cst.CstMethodRef ref = (mod.agus.jcoderz.dx.rop.cst.CstMethodRef) cst;



















                if (ref.getDefiningClass().equals(method.getDefiningClass())) {
                    for (int i = 0; i < methods.size(); ++i) {
                        final Method m = methods.get(i);
                        if (AccessFlags.isPrivate(m.getAccessFlags()) &&
                                ref.getNat().equals(m.getNat())) {
                            return mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_DIRECT;
                        }
                    }
                }



                if (ref.isSignaturePolymorphic()) {
                    return mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_POLYMORPHIC;
                }
                return mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_VIRTUAL;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.INVOKESPECIAL: {
                mod.agus.jcoderz.dx.rop.cst.CstMethodRef ref = (mod.agus.jcoderz.dx.rop.cst.CstMethodRef) cst;
                if (ref.isInstanceInit() ||
                    (ref.getDefiningClass().equals(method.getDefiningClass()))) {
                    return mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_DIRECT;
                }
                return mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_SUPER;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.INVOKESTATIC: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_STATIC;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.INVOKEINTERFACE: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_INTERFACE;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.INVOKEDYNAMIC: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_CUSTOM;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.NEW: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.NEW_INSTANCE;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.NEWARRAY:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.ANEWARRAY: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.NEW_ARRAY;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.ARRAYLENGTH: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.ARRAY_LENGTH;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.ATHROW: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.THROW;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.CHECKCAST: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.CHECK_CAST;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.INSTANCEOF: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.INSTANCE_OF;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.MONITORENTER: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.MONITOR_ENTER;
            }
            case ByteOps.MONITOREXIT: {
                return RegOps.MONITOR_EXIT;
            }
        }

        throw new RuntimeException("shouldn't happen");
    }

    private Insn makeInvokePolymorphicInsn(Rop rop, SourcePosition pos, RegisterSpecList sources,
                                           TypeList catches, Constant cst) {
        mod.agus.jcoderz.dx.rop.cst.CstMethodRef cstMethodRef = (CstMethodRef) cst;
        return new InvokePolymorphicInsn(rop, pos, sources, catches, cstMethodRef);
    }
}
