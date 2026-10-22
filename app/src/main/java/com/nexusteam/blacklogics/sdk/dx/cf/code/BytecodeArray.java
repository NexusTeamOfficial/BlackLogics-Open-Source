
package mod.agus.jcoderz.dx.cf.code;

import java.util.ArrayList;

import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.ConstantPool;
import mod.agus.jcoderz.dx.rop.cst.CstDouble;
import mod.agus.jcoderz.dx.rop.cst.CstFloat;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;
import mod.agus.jcoderz.dx.rop.cst.CstInvokeDynamic;
import mod.agus.jcoderz.dx.rop.cst.CstKnownNull;
import mod.agus.jcoderz.dx.rop.cst.CstLiteralBits;
import mod.agus.jcoderz.dx.rop.cst.CstLong;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.Bits;
import mod.agus.jcoderz.dx.util.ByteArray;
import mod.agus.jcoderz.dx.util.Hex;

public final class BytecodeArray {
    public static final Visitor EMPTY_VISITOR = new BaseVisitor();

    private final mod.agus.jcoderz.dx.util.ByteArray bytes;

    private final mod.agus.jcoderz.dx.rop.cst.ConstantPool pool;

    public BytecodeArray(mod.agus.jcoderz.dx.util.ByteArray bytes, ConstantPool pool) {
        if (bytes == null) {
            throw new NullPointerException("bytes == null");
        }

        if (pool == null) {
            throw new NullPointerException("pool == null");
        }

        this.bytes = bytes;
        this.pool = pool;
    }

    public ByteArray getBytes() {
        return bytes;
    }

    public int size() {
        return bytes.size();
    }

    public int byteLength() {
        return 4 + bytes.size();
    }

    public void forEach(Visitor visitor) {
        int sz = bytes.size();
        int at = 0;

        while (at < sz) {
            at += parseInstruction(at, visitor);
        }
    }

    public int[] getInstructionOffsets() {
        int sz = bytes.size();
        int[] result = mod.agus.jcoderz.dx.util.Bits.makeBitSet(sz);
        int at = 0;

        while (at < sz) {
            mod.agus.jcoderz.dx.util.Bits.set(result, at, true);
            int length = parseInstruction(at, null);
            at += length;
        }

        return result;
    }

    public void processWorkSet(int[] workSet, Visitor visitor) {
        if (visitor == null) {
            throw new NullPointerException("visitor == null");
        }

        for (;;) {
            int offset = mod.agus.jcoderz.dx.util.Bits.findFirst(workSet, 0);
            if (offset < 0) {
                break;
            }
            Bits.clear(workSet, offset);
            parseInstruction(offset, visitor);
            visitor.setPreviousOffset(offset);
        }
    }

    public int parseInstruction(int offset, Visitor visitor) {
        if (visitor == null) {
            visitor = EMPTY_VISITOR;
        }

        try {
            int opcode = bytes.getUnsignedByte(offset);
            int info = mod.agus.jcoderz.dx.cf.code.ByteOps.opInfo(opcode);
            int fmt = info & mod.agus.jcoderz.dx.cf.code.ByteOps.FMT_MASK;

            switch (opcode) {
                case mod.agus.jcoderz.dx.cf.code.ByteOps.NOP: {
                    visitor.visitNoArgs(opcode, offset, 1, mod.agus.jcoderz.dx.rop.type.Type.VOID);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ACONST_NULL: {
                    visitor.visitConstant(mod.agus.jcoderz.dx.cf.code.ByteOps.LDC, offset, 1,
                                          CstKnownNull.THE_ONE, 0);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ICONST_M1: {
                    visitor.visitConstant(mod.agus.jcoderz.dx.cf.code.ByteOps.LDC, offset, 1,
                                          mod.agus.jcoderz.dx.rop.cst.CstInteger.VALUE_M1, -1);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ICONST_0: {
                    visitor.visitConstant(mod.agus.jcoderz.dx.cf.code.ByteOps.LDC, offset, 1,
                                          mod.agus.jcoderz.dx.rop.cst.CstInteger.VALUE_0, 0);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ICONST_1: {
                    visitor.visitConstant(mod.agus.jcoderz.dx.cf.code.ByteOps.LDC, offset, 1,
                                          mod.agus.jcoderz.dx.rop.cst.CstInteger.VALUE_1, 1);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ICONST_2: {
                    visitor.visitConstant(mod.agus.jcoderz.dx.cf.code.ByteOps.LDC, offset, 1,
                                          mod.agus.jcoderz.dx.rop.cst.CstInteger.VALUE_2, 2);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ICONST_3: {
                    visitor.visitConstant(mod.agus.jcoderz.dx.cf.code.ByteOps.LDC, offset, 1,
                                          mod.agus.jcoderz.dx.rop.cst.CstInteger.VALUE_3, 3);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ICONST_4: {
                    visitor.visitConstant(mod.agus.jcoderz.dx.cf.code.ByteOps.LDC, offset, 1,
                                          mod.agus.jcoderz.dx.rop.cst.CstInteger.VALUE_4, 4);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ICONST_5:  {
                    visitor.visitConstant(mod.agus.jcoderz.dx.cf.code.ByteOps.LDC, offset, 1,
                                          mod.agus.jcoderz.dx.rop.cst.CstInteger.VALUE_5, 5);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.LCONST_0: {
                    visitor.visitConstant(mod.agus.jcoderz.dx.cf.code.ByteOps.LDC, offset, 1,
                                          mod.agus.jcoderz.dx.rop.cst.CstLong.VALUE_0, 0);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.LCONST_1: {
                    visitor.visitConstant(mod.agus.jcoderz.dx.cf.code.ByteOps.LDC, offset, 1,
                                          CstLong.VALUE_1, 0);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.FCONST_0: {
                    visitor.visitConstant(mod.agus.jcoderz.dx.cf.code.ByteOps.LDC, offset, 1,
                                          mod.agus.jcoderz.dx.rop.cst.CstFloat.VALUE_0, 0);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.FCONST_1: {
                    visitor.visitConstant(mod.agus.jcoderz.dx.cf.code.ByteOps.LDC, offset, 1,
                                          mod.agus.jcoderz.dx.rop.cst.CstFloat.VALUE_1, 0);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.FCONST_2:  {
                    visitor.visitConstant(mod.agus.jcoderz.dx.cf.code.ByteOps.LDC, offset, 1,
                                          CstFloat.VALUE_2, 0);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.DCONST_0: {
                    visitor.visitConstant(mod.agus.jcoderz.dx.cf.code.ByteOps.LDC, offset, 1,
                                          mod.agus.jcoderz.dx.rop.cst.CstDouble.VALUE_0, 0);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.DCONST_1: {
                    visitor.visitConstant(mod.agus.jcoderz.dx.cf.code.ByteOps.LDC, offset, 1,
                                          CstDouble.VALUE_1, 0);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.BIPUSH: {
                    int value = bytes.getByte(offset + 1);
                    visitor.visitConstant(mod.agus.jcoderz.dx.cf.code.ByteOps.LDC, offset, 2,
                                          mod.agus.jcoderz.dx.rop.cst.CstInteger.make(value), value);
                    return 2;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.SIPUSH: {
                    int value = bytes.getShort(offset + 1);
                    visitor.visitConstant(mod.agus.jcoderz.dx.cf.code.ByteOps.LDC, offset, 3,
                                          mod.agus.jcoderz.dx.rop.cst.CstInteger.make(value), value);
                    return 3;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.LDC: {
                    int idx = bytes.getUnsignedByte(offset + 1);
                    mod.agus.jcoderz.dx.rop.cst.Constant cst = pool.get(idx);
                    int value = (cst instanceof mod.agus.jcoderz.dx.rop.cst.CstInteger) ?
                        ((mod.agus.jcoderz.dx.rop.cst.CstInteger) cst).getValue() : 0;
                    visitor.visitConstant(mod.agus.jcoderz.dx.cf.code.ByteOps.LDC, offset, 2, cst, value);
                    return 2;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.LDC_W: {
                    int idx = bytes.getUnsignedShort(offset + 1);
                    mod.agus.jcoderz.dx.rop.cst.Constant cst = pool.get(idx);
                    int value = (cst instanceof mod.agus.jcoderz.dx.rop.cst.CstInteger) ?
                        ((mod.agus.jcoderz.dx.rop.cst.CstInteger) cst).getValue() : 0;
                    visitor.visitConstant(mod.agus.jcoderz.dx.cf.code.ByteOps.LDC, offset, 3, cst, value);
                    return 3;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.LDC2_W: {
                    int idx = bytes.getUnsignedShort(offset + 1);
                    mod.agus.jcoderz.dx.rop.cst.Constant cst = pool.get(idx);
                    visitor.visitConstant(mod.agus.jcoderz.dx.cf.code.ByteOps.LDC2_W, offset, 3, cst, 0);
                    return 3;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ILOAD: {
                    int idx = bytes.getUnsignedByte(offset + 1);
                    visitor.visitLocal(mod.agus.jcoderz.dx.cf.code.ByteOps.ILOAD, offset, 2, idx,
                                       mod.agus.jcoderz.dx.rop.type.Type.INT, 0);
                    return 2;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.LLOAD: {
                    int idx = bytes.getUnsignedByte(offset + 1);
                    visitor.visitLocal(mod.agus.jcoderz.dx.cf.code.ByteOps.ILOAD, offset, 2, idx,
                                       mod.agus.jcoderz.dx.rop.type.Type.LONG, 0);
                    return 2;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.FLOAD: {
                    int idx = bytes.getUnsignedByte(offset + 1);
                    visitor.visitLocal(mod.agus.jcoderz.dx.cf.code.ByteOps.ILOAD, offset, 2, idx,
                                       mod.agus.jcoderz.dx.rop.type.Type.FLOAT, 0);
                    return 2;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.DLOAD: {
                    int idx = bytes.getUnsignedByte(offset + 1);
                    visitor.visitLocal(mod.agus.jcoderz.dx.cf.code.ByteOps.ILOAD, offset, 2, idx,
                                       mod.agus.jcoderz.dx.rop.type.Type.DOUBLE, 0);
                    return 2;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ALOAD: {
                    int idx = bytes.getUnsignedByte(offset + 1);
                    visitor.visitLocal(mod.agus.jcoderz.dx.cf.code.ByteOps.ILOAD, offset, 2, idx,
                                       mod.agus.jcoderz.dx.rop.type.Type.OBJECT, 0);
                    return 2;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ILOAD_0:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ILOAD_1:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ILOAD_2:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ILOAD_3: {
                    int idx = opcode - mod.agus.jcoderz.dx.cf.code.ByteOps.ILOAD_0;
                    visitor.visitLocal(mod.agus.jcoderz.dx.cf.code.ByteOps.ILOAD, offset, 1, idx,
                                       mod.agus.jcoderz.dx.rop.type.Type.INT, 0);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.LLOAD_0:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.LLOAD_1:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.LLOAD_2:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.LLOAD_3: {
                    int idx = opcode - mod.agus.jcoderz.dx.cf.code.ByteOps.LLOAD_0;
                    visitor.visitLocal(mod.agus.jcoderz.dx.cf.code.ByteOps.ILOAD, offset, 1, idx,
                                       mod.agus.jcoderz.dx.rop.type.Type.LONG, 0);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.FLOAD_0:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.FLOAD_1:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.FLOAD_2:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.FLOAD_3: {
                    int idx = opcode - mod.agus.jcoderz.dx.cf.code.ByteOps.FLOAD_0;
                    visitor.visitLocal(mod.agus.jcoderz.dx.cf.code.ByteOps.ILOAD, offset, 1, idx,
                                       mod.agus.jcoderz.dx.rop.type.Type.FLOAT, 0);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.DLOAD_0:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.DLOAD_1:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.DLOAD_2:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.DLOAD_3: {
                    int idx = opcode - mod.agus.jcoderz.dx.cf.code.ByteOps.DLOAD_0;
                    visitor.visitLocal(mod.agus.jcoderz.dx.cf.code.ByteOps.ILOAD, offset, 1, idx,
                                       mod.agus.jcoderz.dx.rop.type.Type.DOUBLE, 0);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ALOAD_0:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ALOAD_1:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ALOAD_2:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ALOAD_3: {
                    int idx = opcode - mod.agus.jcoderz.dx.cf.code.ByteOps.ALOAD_0;
                    visitor.visitLocal(mod.agus.jcoderz.dx.cf.code.ByteOps.ILOAD, offset, 1, idx,
                                       mod.agus.jcoderz.dx.rop.type.Type.OBJECT, 0);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.IALOAD: {
                    visitor.visitNoArgs(mod.agus.jcoderz.dx.cf.code.ByteOps.IALOAD, offset, 1, mod.agus.jcoderz.dx.rop.type.Type.INT);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.LALOAD: {
                    visitor.visitNoArgs(mod.agus.jcoderz.dx.cf.code.ByteOps.IALOAD, offset, 1, mod.agus.jcoderz.dx.rop.type.Type.LONG);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.FALOAD: {
                    visitor.visitNoArgs(mod.agus.jcoderz.dx.cf.code.ByteOps.IALOAD, offset, 1,
                                        mod.agus.jcoderz.dx.rop.type.Type.FLOAT);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.DALOAD: {
                    visitor.visitNoArgs(mod.agus.jcoderz.dx.cf.code.ByteOps.IALOAD, offset, 1,
                                        mod.agus.jcoderz.dx.rop.type.Type.DOUBLE);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.AALOAD: {
                    visitor.visitNoArgs(mod.agus.jcoderz.dx.cf.code.ByteOps.IALOAD, offset, 1,
                                        mod.agus.jcoderz.dx.rop.type.Type.OBJECT);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.BALOAD: {
                    visitor.visitNoArgs(mod.agus.jcoderz.dx.cf.code.ByteOps.IALOAD, offset, 1, mod.agus.jcoderz.dx.rop.type.Type.BYTE);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.CALOAD: {
                    visitor.visitNoArgs(mod.agus.jcoderz.dx.cf.code.ByteOps.IALOAD, offset, 1, mod.agus.jcoderz.dx.rop.type.Type.CHAR);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.SALOAD: {
                    visitor.visitNoArgs(mod.agus.jcoderz.dx.cf.code.ByteOps.IALOAD, offset, 1,
                                        mod.agus.jcoderz.dx.rop.type.Type.SHORT);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ISTORE: {
                    int idx = bytes.getUnsignedByte(offset + 1);
                    visitor.visitLocal(mod.agus.jcoderz.dx.cf.code.ByteOps.ISTORE, offset, 2, idx,
                                       mod.agus.jcoderz.dx.rop.type.Type.INT, 0);
                    return 2;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.LSTORE: {
                    int idx = bytes.getUnsignedByte(offset + 1);
                    visitor.visitLocal(mod.agus.jcoderz.dx.cf.code.ByteOps.ISTORE, offset, 2, idx,
                                       mod.agus.jcoderz.dx.rop.type.Type.LONG, 0);
                    return 2;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.FSTORE: {
                    int idx = bytes.getUnsignedByte(offset + 1);
                    visitor.visitLocal(mod.agus.jcoderz.dx.cf.code.ByteOps.ISTORE, offset, 2, idx,
                                       mod.agus.jcoderz.dx.rop.type.Type.FLOAT, 0);
                    return 2;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.DSTORE: {
                    int idx = bytes.getUnsignedByte(offset + 1);
                    visitor.visitLocal(mod.agus.jcoderz.dx.cf.code.ByteOps.ISTORE, offset, 2, idx,
                                       mod.agus.jcoderz.dx.rop.type.Type.DOUBLE, 0);
                    return 2;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ASTORE: {
                    int idx = bytes.getUnsignedByte(offset + 1);
                    visitor.visitLocal(mod.agus.jcoderz.dx.cf.code.ByteOps.ISTORE, offset, 2, idx,
                                       mod.agus.jcoderz.dx.rop.type.Type.OBJECT, 0);
                    return 2;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ISTORE_0:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ISTORE_1:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ISTORE_2:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ISTORE_3: {
                    int idx = opcode - mod.agus.jcoderz.dx.cf.code.ByteOps.ISTORE_0;
                    visitor.visitLocal(mod.agus.jcoderz.dx.cf.code.ByteOps.ISTORE, offset, 1, idx,
                                       mod.agus.jcoderz.dx.rop.type.Type.INT, 0);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.LSTORE_0:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.LSTORE_1:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.LSTORE_2:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.LSTORE_3: {
                    int idx = opcode - mod.agus.jcoderz.dx.cf.code.ByteOps.LSTORE_0;
                    visitor.visitLocal(mod.agus.jcoderz.dx.cf.code.ByteOps.ISTORE, offset, 1, idx,
                                       mod.agus.jcoderz.dx.rop.type.Type.LONG, 0);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.FSTORE_0:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.FSTORE_1:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.FSTORE_2:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.FSTORE_3: {
                    int idx = opcode - mod.agus.jcoderz.dx.cf.code.ByteOps.FSTORE_0;
                    visitor.visitLocal(mod.agus.jcoderz.dx.cf.code.ByteOps.ISTORE, offset, 1, idx,
                                       mod.agus.jcoderz.dx.rop.type.Type.FLOAT, 0);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.DSTORE_0:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.DSTORE_1:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.DSTORE_2:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.DSTORE_3: {
                    int idx = opcode - mod.agus.jcoderz.dx.cf.code.ByteOps.DSTORE_0;
                    visitor.visitLocal(mod.agus.jcoderz.dx.cf.code.ByteOps.ISTORE, offset, 1, idx,
                                       mod.agus.jcoderz.dx.rop.type.Type.DOUBLE, 0);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ASTORE_0:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ASTORE_1:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ASTORE_2:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ASTORE_3: {
                    int idx = opcode - mod.agus.jcoderz.dx.cf.code.ByteOps.ASTORE_0;
                    visitor.visitLocal(mod.agus.jcoderz.dx.cf.code.ByteOps.ISTORE, offset, 1, idx,
                                       mod.agus.jcoderz.dx.rop.type.Type.OBJECT, 0);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.IASTORE: {
                    visitor.visitNoArgs(mod.agus.jcoderz.dx.cf.code.ByteOps.IASTORE, offset, 1, mod.agus.jcoderz.dx.rop.type.Type.INT);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.LASTORE: {
                    visitor.visitNoArgs(mod.agus.jcoderz.dx.cf.code.ByteOps.IASTORE, offset, 1,
                                        mod.agus.jcoderz.dx.rop.type.Type.LONG);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.FASTORE: {
                    visitor.visitNoArgs(mod.agus.jcoderz.dx.cf.code.ByteOps.IASTORE, offset, 1,
                                        mod.agus.jcoderz.dx.rop.type.Type.FLOAT);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.DASTORE: {
                    visitor.visitNoArgs(mod.agus.jcoderz.dx.cf.code.ByteOps.IASTORE, offset, 1,
                                        mod.agus.jcoderz.dx.rop.type.Type.DOUBLE);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.AASTORE: {
                    visitor.visitNoArgs(mod.agus.jcoderz.dx.cf.code.ByteOps.IASTORE, offset, 1,
                                        mod.agus.jcoderz.dx.rop.type.Type.OBJECT);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.BASTORE: {
                    visitor.visitNoArgs(mod.agus.jcoderz.dx.cf.code.ByteOps.IASTORE, offset, 1,
                                        mod.agus.jcoderz.dx.rop.type.Type.BYTE);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.CASTORE: {
                    visitor.visitNoArgs(mod.agus.jcoderz.dx.cf.code.ByteOps.IASTORE, offset, 1,
                                        mod.agus.jcoderz.dx.rop.type.Type.CHAR);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.SASTORE: {
                    visitor.visitNoArgs(mod.agus.jcoderz.dx.cf.code.ByteOps.IASTORE, offset, 1,
                                        mod.agus.jcoderz.dx.rop.type.Type.SHORT);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.POP:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.POP2:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.DUP:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.DUP_X1:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.DUP_X2:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.DUP2:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.DUP2_X1:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.DUP2_X2:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.SWAP: {
                    visitor.visitNoArgs(opcode, offset, 1, mod.agus.jcoderz.dx.rop.type.Type.VOID);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.IADD:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ISUB:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.IMUL:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.IDIV:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.IREM:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.INEG:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ISHL:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ISHR:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.IUSHR:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.IAND:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.IOR:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.IXOR: {
                    visitor.visitNoArgs(opcode, offset, 1, mod.agus.jcoderz.dx.rop.type.Type.INT);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.LADD:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.LSUB:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.LMUL:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.LDIV:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.LREM:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.LNEG:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.LSHL:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.LSHR:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.LUSHR:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.LAND:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.LOR:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.LXOR: {
                    visitor.visitNoArgs(opcode - 1, offset, 1, mod.agus.jcoderz.dx.rop.type.Type.LONG);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.FADD:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.FSUB:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.FMUL:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.FDIV:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.FREM:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.FNEG: {
                    visitor.visitNoArgs(opcode - 2, offset, 1, mod.agus.jcoderz.dx.rop.type.Type.FLOAT);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.DADD:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.DSUB:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.DMUL:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.DDIV:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.DREM:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.DNEG: {
                    visitor.visitNoArgs(opcode - 3, offset, 1, mod.agus.jcoderz.dx.rop.type.Type.DOUBLE);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.IINC: {
                    int idx = bytes.getUnsignedByte(offset + 1);
                    int value = bytes.getByte(offset + 2);
                    visitor.visitLocal(opcode, offset, 3, idx,
                                       mod.agus.jcoderz.dx.rop.type.Type.INT, value);
                    return 3;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.I2L:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.F2L:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.D2L: {
                    visitor.visitNoArgs(opcode, offset, 1, mod.agus.jcoderz.dx.rop.type.Type.LONG);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.I2F:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.L2F:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.D2F: {
                    visitor.visitNoArgs(opcode, offset, 1, mod.agus.jcoderz.dx.rop.type.Type.FLOAT);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.I2D:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.L2D:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.F2D: {
                    visitor.visitNoArgs(opcode, offset, 1, mod.agus.jcoderz.dx.rop.type.Type.DOUBLE);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.L2I:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.F2I:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.D2I:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.I2B:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.I2C:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.I2S:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.LCMP:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.FCMPL:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.FCMPG:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.DCMPL:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.DCMPG:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ARRAYLENGTH: {
                    visitor.visitNoArgs(opcode, offset, 1, mod.agus.jcoderz.dx.rop.type.Type.INT);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.IFEQ:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.IFNE:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.IFLT:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.IFGE:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.IFGT:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.IFLE:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.IF_ICMPEQ:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.IF_ICMPNE:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.IF_ICMPLT:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.IF_ICMPGE:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.IF_ICMPGT:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.IF_ICMPLE:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.IF_ACMPEQ:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.IF_ACMPNE:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.GOTO:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.JSR:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.IFNULL:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.IFNONNULL: {
                    int target = offset + bytes.getShort(offset + 1);
                    visitor.visitBranch(opcode, offset, 3, target);
                    return 3;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.RET: {
                    int idx = bytes.getUnsignedByte(offset + 1);
                    visitor.visitLocal(opcode, offset, 2, idx,
                                       mod.agus.jcoderz.dx.rop.type.Type.RETURN_ADDRESS, 0);
                    return 2;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.TABLESWITCH: {
                    return parseTableswitch(offset, visitor);
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.LOOKUPSWITCH: {
                    return parseLookupswitch(offset, visitor);
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.IRETURN: {
                    visitor.visitNoArgs(mod.agus.jcoderz.dx.cf.code.ByteOps.IRETURN, offset, 1, mod.agus.jcoderz.dx.rop.type.Type.INT);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.LRETURN: {
                    visitor.visitNoArgs(mod.agus.jcoderz.dx.cf.code.ByteOps.IRETURN, offset, 1,
                                        mod.agus.jcoderz.dx.rop.type.Type.LONG);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.FRETURN: {
                    visitor.visitNoArgs(mod.agus.jcoderz.dx.cf.code.ByteOps.IRETURN, offset, 1,
                                        mod.agus.jcoderz.dx.rop.type.Type.FLOAT);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.DRETURN: {
                    visitor.visitNoArgs(mod.agus.jcoderz.dx.cf.code.ByteOps.IRETURN, offset, 1,
                                        mod.agus.jcoderz.dx.rop.type.Type.DOUBLE);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ARETURN: {
                    visitor.visitNoArgs(mod.agus.jcoderz.dx.cf.code.ByteOps.IRETURN, offset, 1,
                                        mod.agus.jcoderz.dx.rop.type.Type.OBJECT);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.RETURN:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ATHROW:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.MONITORENTER:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.MONITOREXIT: {
                    visitor.visitNoArgs(opcode, offset, 1, mod.agus.jcoderz.dx.rop.type.Type.VOID);
                    return 1;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.GETSTATIC:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.PUTSTATIC:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.GETFIELD:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.PUTFIELD:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.INVOKEVIRTUAL:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.INVOKESPECIAL:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.INVOKESTATIC:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.NEW:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.ANEWARRAY:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.CHECKCAST:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.INSTANCEOF: {
                    int idx = bytes.getUnsignedShort(offset + 1);
                    mod.agus.jcoderz.dx.rop.cst.Constant cst = pool.get(idx);
                    visitor.visitConstant(opcode, offset, 3, cst, 0);
                    return 3;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.INVOKEINTERFACE: {
                    int idx = bytes.getUnsignedShort(offset + 1);
                    int count = bytes.getUnsignedByte(offset + 3);
                    int expectZero = bytes.getUnsignedByte(offset + 4);
                    mod.agus.jcoderz.dx.rop.cst.Constant cst = pool.get(idx);
                    visitor.visitConstant(opcode, offset, 5, cst,
                                          count | (expectZero << 8));
                    return 5;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.INVOKEDYNAMIC: {
                    int idx = bytes.getUnsignedShort(offset + 1);

                    mod.agus.jcoderz.dx.rop.cst.CstInvokeDynamic cstInvokeDynamic = (CstInvokeDynamic) pool.get(idx);
                    visitor.visitConstant(opcode, offset, 5, cstInvokeDynamic, 0);
                    return 5;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.NEWARRAY: {
                    return parseNewarray(offset, visitor);
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.WIDE: {
                    return parseWide(offset, visitor);
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.MULTIANEWARRAY: {
                    int idx = bytes.getUnsignedShort(offset + 1);
                    int dimensions = bytes.getUnsignedByte(offset + 3);
                    mod.agus.jcoderz.dx.rop.cst.Constant cst = pool.get(idx);
                    visitor.visitConstant(opcode, offset, 4, cst, dimensions);
                    return 4;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.GOTO_W:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.JSR_W: {
                    int target = offset + bytes.getInt(offset + 1);
                    int newop =
                        (opcode == mod.agus.jcoderz.dx.cf.code.ByteOps.GOTO_W) ? mod.agus.jcoderz.dx.cf.code.ByteOps.GOTO :
                        mod.agus.jcoderz.dx.cf.code.ByteOps.JSR;
                    visitor.visitBranch(newop, offset, 5, target);
                    return 5;
                }
                default: {
                    visitor.visitInvalid(opcode, offset, 1);
                    return 1;
                }
            }
        } catch (SimException ex) {
            ex.addContext("...at bytecode offset " + mod.agus.jcoderz.dx.util.Hex.u4(offset));
            throw ex;
        } catch (RuntimeException ex) {
            SimException se = new SimException(ex);
            se.addContext("...at bytecode offset " + mod.agus.jcoderz.dx.util.Hex.u4(offset));
            throw se;
        }
    }

    private int parseTableswitch(int offset, Visitor visitor) {
        int at = (offset + 4) & ~3;


        int padding = 0;
        for (int i = offset + 1; i < at; i++) {
            padding = (padding << 8) | bytes.getUnsignedByte(i);
        }

        int defaultTarget = offset + bytes.getInt(at);
        int low = bytes.getInt(at + 4);
        int high = bytes.getInt(at + 8);
        int count = high - low + 1;
        at += 12;

        if (low > high) {
            throw new SimException("low / high inversion");
        }

        mod.agus.jcoderz.dx.cf.code.SwitchList cases = new mod.agus.jcoderz.dx.cf.code.SwitchList(count);
        for (int i = 0; i < count; i++) {
            int target = offset + bytes.getInt(at);
            at += 4;
            cases.add(low + i, target);
        }
        cases.setDefaultTarget(defaultTarget);
        cases.removeSuperfluousDefaults();
        cases.setImmutable();

        int length = at - offset;
        visitor.visitSwitch(mod.agus.jcoderz.dx.cf.code.ByteOps.LOOKUPSWITCH, offset, length, cases,
                            padding);

        return length;
    }

    private int parseLookupswitch(int offset, Visitor visitor) {
        int at = (offset + 4) & ~3;


        int padding = 0;
        for (int i = offset + 1; i < at; i++) {
            padding = (padding << 8) | bytes.getUnsignedByte(i);
        }

        int defaultTarget = offset + bytes.getInt(at);
        int npairs = bytes.getInt(at + 4);
        at += 8;

        mod.agus.jcoderz.dx.cf.code.SwitchList cases = new mod.agus.jcoderz.dx.cf.code.SwitchList(npairs);
        for (int i = 0; i < npairs; i++) {
            int match = bytes.getInt(at);
            int target = offset + bytes.getInt(at + 4);
            at += 8;
            cases.add(match, target);
        }
        cases.setDefaultTarget(defaultTarget);
        cases.removeSuperfluousDefaults();
        cases.setImmutable();

        int length = at - offset;
        visitor.visitSwitch(mod.agus.jcoderz.dx.cf.code.ByteOps.LOOKUPSWITCH, offset, length, cases,
                            padding);

        return length;
    }

    private int parseNewarray(int offset, Visitor visitor) {
        int value = bytes.getUnsignedByte(offset + 1);
        mod.agus.jcoderz.dx.rop.cst.CstType type;
        switch (value) {
            case mod.agus.jcoderz.dx.cf.code.ByteOps.NEWARRAY_BOOLEAN: {
                type = mod.agus.jcoderz.dx.rop.cst.CstType.BOOLEAN_ARRAY;
                break;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.NEWARRAY_CHAR: {
                type = mod.agus.jcoderz.dx.rop.cst.CstType.CHAR_ARRAY;
                break;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.NEWARRAY_DOUBLE: {
                type = mod.agus.jcoderz.dx.rop.cst.CstType.DOUBLE_ARRAY;
                break;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.NEWARRAY_FLOAT: {
                type = mod.agus.jcoderz.dx.rop.cst.CstType.FLOAT_ARRAY;
                break;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.NEWARRAY_BYTE: {
                type = mod.agus.jcoderz.dx.rop.cst.CstType.BYTE_ARRAY;
                break;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.NEWARRAY_SHORT: {
                type = mod.agus.jcoderz.dx.rop.cst.CstType.SHORT_ARRAY;
                break;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.NEWARRAY_INT: {
                type = mod.agus.jcoderz.dx.rop.cst.CstType.INT_ARRAY;
                break;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.NEWARRAY_LONG: {
                type = mod.agus.jcoderz.dx.rop.cst.CstType.LONG_ARRAY;
                break;
            }
            default: {
                throw new SimException("bad newarray code " +
                        Hex.u1(value));
            }
        }


        int previousOffset = visitor.getPreviousOffset();
        ConstantParserVisitor constantVisitor = new ConstantParserVisitor();
        int arrayLength = 0;

        if (previousOffset >= 0) {
            parseInstruction(previousOffset, constantVisitor);
            if (constantVisitor.cst instanceof mod.agus.jcoderz.dx.rop.cst.CstInteger &&
                    constantVisitor.length + previousOffset == offset) {
                arrayLength = constantVisitor.value;

            }
        }

        int nInit = 0;
        int curOffset = offset+2;
        int lastOffset = curOffset;
        ArrayList<mod.agus.jcoderz.dx.rop.cst.Constant> initVals = new ArrayList<mod.agus.jcoderz.dx.rop.cst.Constant>();

        if (arrayLength != 0) {
            while (true) {
                boolean punt = false;


                int nextByte = bytes.getUnsignedByte(curOffset++);
                if (nextByte != mod.agus.jcoderz.dx.cf.code.ByteOps.DUP)
                    break;

                parseInstruction(curOffset, constantVisitor);
                if (constantVisitor.length == 0 ||
                        !(constantVisitor.cst instanceof CstInteger) ||
                        constantVisitor.value != nInit)
                    break;


                curOffset += constantVisitor.length;

                parseInstruction(curOffset, constantVisitor);
                if (constantVisitor.length == 0 ||
                        !(constantVisitor.cst instanceof CstLiteralBits))
                    break;

                curOffset += constantVisitor.length;
                initVals.add(constantVisitor.cst);

                nextByte = bytes.getUnsignedByte(curOffset++);

                switch (value) {
                    case mod.agus.jcoderz.dx.cf.code.ByteOps.NEWARRAY_BYTE:
                    case mod.agus.jcoderz.dx.cf.code.ByteOps.NEWARRAY_BOOLEAN: {
                        if (nextByte != mod.agus.jcoderz.dx.cf.code.ByteOps.BASTORE) {
                            punt = true;
                        }
                        break;
                    }
                    case mod.agus.jcoderz.dx.cf.code.ByteOps.NEWARRAY_CHAR: {
                        if (nextByte != mod.agus.jcoderz.dx.cf.code.ByteOps.CASTORE) {
                            punt = true;
                        }
                        break;
                    }
                    case mod.agus.jcoderz.dx.cf.code.ByteOps.NEWARRAY_DOUBLE: {
                        if (nextByte != mod.agus.jcoderz.dx.cf.code.ByteOps.DASTORE) {
                            punt = true;
                        }
                        break;
                    }
                    case mod.agus.jcoderz.dx.cf.code.ByteOps.NEWARRAY_FLOAT: {
                        if (nextByte != mod.agus.jcoderz.dx.cf.code.ByteOps.FASTORE) {
                            punt = true;
                        }
                        break;
                    }
                    case mod.agus.jcoderz.dx.cf.code.ByteOps.NEWARRAY_SHORT: {
                        if (nextByte != mod.agus.jcoderz.dx.cf.code.ByteOps.SASTORE) {
                            punt = true;
                        }
                        break;
                    }
                    case mod.agus.jcoderz.dx.cf.code.ByteOps.NEWARRAY_INT: {
                        if (nextByte != mod.agus.jcoderz.dx.cf.code.ByteOps.IASTORE) {
                            punt = true;
                        }
                        break;
                    }
                    case mod.agus.jcoderz.dx.cf.code.ByteOps.NEWARRAY_LONG: {
                        if (nextByte != mod.agus.jcoderz.dx.cf.code.ByteOps.LASTORE) {
                            punt = true;
                        }
                        break;
                    }
                    default:
                        punt = true;
                        break;
                }
                if (punt) {
                    break;
                }
                lastOffset = curOffset;
                nInit++;
            }
        }

        if (nInit < 2 || nInit != arrayLength) {
            visitor.visitNewarray(offset, 2, type, null);
            return 2;
        } else {
            visitor.visitNewarray(offset, lastOffset - offset, type, initVals);
            return lastOffset - offset;
        }
     }


    private int parseWide(int offset, Visitor visitor) {
        int opcode = bytes.getUnsignedByte(offset + 1);
        int idx = bytes.getUnsignedShort(offset + 2);
        switch (opcode) {
            case mod.agus.jcoderz.dx.cf.code.ByteOps.ILOAD: {
                visitor.visitLocal(mod.agus.jcoderz.dx.cf.code.ByteOps.ILOAD, offset, 4, idx,
                                   mod.agus.jcoderz.dx.rop.type.Type.INT, 0);
                return 4;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.LLOAD: {
                visitor.visitLocal(mod.agus.jcoderz.dx.cf.code.ByteOps.ILOAD, offset, 4, idx,
                                   mod.agus.jcoderz.dx.rop.type.Type.LONG, 0);
                return 4;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.FLOAD: {
                visitor.visitLocal(mod.agus.jcoderz.dx.cf.code.ByteOps.ILOAD, offset, 4, idx,
                                   mod.agus.jcoderz.dx.rop.type.Type.FLOAT, 0);
                return 4;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.DLOAD: {
                visitor.visitLocal(mod.agus.jcoderz.dx.cf.code.ByteOps.ILOAD, offset, 4, idx,
                                   mod.agus.jcoderz.dx.rop.type.Type.DOUBLE, 0);
                return 4;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.ALOAD: {
                visitor.visitLocal(mod.agus.jcoderz.dx.cf.code.ByteOps.ILOAD, offset, 4, idx,
                                   mod.agus.jcoderz.dx.rop.type.Type.OBJECT, 0);
                return 4;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.ISTORE: {
                visitor.visitLocal(mod.agus.jcoderz.dx.cf.code.ByteOps.ISTORE, offset, 4, idx,
                                   mod.agus.jcoderz.dx.rop.type.Type.INT, 0);
                return 4;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.LSTORE: {
                visitor.visitLocal(mod.agus.jcoderz.dx.cf.code.ByteOps.ISTORE, offset, 4, idx,
                                   mod.agus.jcoderz.dx.rop.type.Type.LONG, 0);
                return 4;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.FSTORE: {
                visitor.visitLocal(mod.agus.jcoderz.dx.cf.code.ByteOps.ISTORE, offset, 4, idx,
                                   mod.agus.jcoderz.dx.rop.type.Type.FLOAT, 0);
                return 4;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.DSTORE: {
                visitor.visitLocal(mod.agus.jcoderz.dx.cf.code.ByteOps.ISTORE, offset, 4, idx,
                                   mod.agus.jcoderz.dx.rop.type.Type.DOUBLE, 0);
                return 4;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.ASTORE: {
                visitor.visitLocal(mod.agus.jcoderz.dx.cf.code.ByteOps.ISTORE, offset, 4, idx,
                                   mod.agus.jcoderz.dx.rop.type.Type.OBJECT, 0);
                return 4;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.RET: {
                visitor.visitLocal(opcode, offset, 4, idx,
                                   mod.agus.jcoderz.dx.rop.type.Type.RETURN_ADDRESS, 0);
                return 4;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IINC: {
                int value = bytes.getShort(offset + 4);
                visitor.visitLocal(opcode, offset, 6, idx,
                                   mod.agus.jcoderz.dx.rop.type.Type.INT, value);
                return 6;
            }
            default: {
                visitor.visitInvalid(ByteOps.WIDE, offset, 1);
                return 1;
            }
        }
    }

    public interface Visitor {
        public void visitInvalid(int opcode, int offset, int length);

        public void visitNoArgs(int opcode, int offset, int length,
                mod.agus.jcoderz.dx.rop.type.Type type);

        public void visitLocal(int opcode, int offset, int length,
                               int idx, mod.agus.jcoderz.dx.rop.type.Type type, int value);

        public void visitConstant(int opcode, int offset, int length,
                                  mod.agus.jcoderz.dx.rop.cst.Constant cst, int value);

        public void visitBranch(int opcode, int offset, int length,
                int target);

        public void visitSwitch(int opcode, int offset, int length,
                                mod.agus.jcoderz.dx.cf.code.SwitchList cases, int padding);

        public void visitNewarray(int offset, int length, mod.agus.jcoderz.dx.rop.cst.CstType type,
                ArrayList<mod.agus.jcoderz.dx.rop.cst.Constant> initVals);

        public void setPreviousOffset(int offset);

        public int getPreviousOffset();
    }

    public static class BaseVisitor implements Visitor {

        private int previousOffset;

        BaseVisitor() {
            previousOffset = -1;
        }

        @Override
        public void visitInvalid(int opcode, int offset, int length) {

        }

        @Override
        public void visitNoArgs(int opcode, int offset, int length,
                mod.agus.jcoderz.dx.rop.type.Type type) {

        }

        @Override
        public void visitLocal(int opcode, int offset, int length,
                               int idx, mod.agus.jcoderz.dx.rop.type.Type type, int value) {

        }

        @Override
        public void visitConstant(int opcode, int offset, int length,
                                  mod.agus.jcoderz.dx.rop.cst.Constant cst, int value) {

        }

        @Override
        public void visitBranch(int opcode, int offset, int length,
                int target) {

        }

        @Override
        public void visitSwitch(int opcode, int offset, int length,
                                mod.agus.jcoderz.dx.cf.code.SwitchList cases, int padding) {

        }

        @Override
        public void visitNewarray(int offset, int length, mod.agus.jcoderz.dx.rop.cst.CstType type,
                ArrayList<mod.agus.jcoderz.dx.rop.cst.Constant> initValues) {

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

    class ConstantParserVisitor extends BaseVisitor {
        mod.agus.jcoderz.dx.rop.cst.Constant cst;
        int length;
        int value;

        ConstantParserVisitor() {
        }

        private void clear() {
            length = 0;
        }

        @Override
        public void visitInvalid(int opcode, int offset, int length) {
            clear();
        }

        @Override
        public void visitNoArgs(int opcode, int offset, int length,
                mod.agus.jcoderz.dx.rop.type.Type type) {
            clear();
        }

        @Override
        public void visitLocal(int opcode, int offset, int length,
                               int idx, Type type, int value) {
            clear();
        }

        @Override
        public void visitConstant(int opcode, int offset, int length,
                                  mod.agus.jcoderz.dx.rop.cst.Constant cst, int value) {
            this.cst = cst;
            this.length = length;
            this.value = value;
        }

        @Override
        public void visitBranch(int opcode, int offset, int length,
                int target) {
            clear();
        }

        @Override
        public void visitSwitch(int opcode, int offset, int length,
                                SwitchList cases, int padding) {
            clear();
        }

        @Override
        public void visitNewarray(int offset, int length, CstType type,
                ArrayList<Constant> initVals) {
            clear();
        }

        @Override
        public void setPreviousOffset(int offset) {

        }

        @Override
        public int getPreviousOffset() {

            return -1;
        }
    }
}
