
package mod.agus.jcoderz.dx.cf.code;

import java.util.ArrayList;

import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstInvokeDynamic;
import mod.agus.jcoderz.dx.rop.cst.CstMemberRef;
import mod.agus.jcoderz.dx.rop.cst.CstMethodHandle;
import mod.agus.jcoderz.dx.rop.cst.CstProtoRef;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.Bits;
import mod.agus.jcoderz.dx.util.IntList;

public final class BasicBlocker implements mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor {
    private final mod.agus.jcoderz.dx.cf.code.ConcreteMethod method;

    private final int[] workSet;

    private final int[] liveSet;

    private final int[] blockSet;

    private final mod.agus.jcoderz.dx.util.IntList[] targetLists;

    private final mod.agus.jcoderz.dx.cf.code.ByteCatchList[] catchLists;

    private int previousOffset;

    public static ByteBlockList identifyBlocks(mod.agus.jcoderz.dx.cf.code.ConcreteMethod method) {
        BasicBlocker bb = new BasicBlocker(method);

        bb.doit();
        return bb.getBlockList();
    }

    private BasicBlocker(ConcreteMethod method) {
        if (method == null) {
            throw new NullPointerException("method == null");
        }

        this.method = method;

        int sz = method.getCode().size() + 1;

        workSet = mod.agus.jcoderz.dx.util.Bits.makeBitSet(sz);
        liveSet = mod.agus.jcoderz.dx.util.Bits.makeBitSet(sz);
        blockSet = mod.agus.jcoderz.dx.util.Bits.makeBitSet(sz);
        targetLists = new mod.agus.jcoderz.dx.util.IntList[sz];
        catchLists = new mod.agus.jcoderz.dx.cf.code.ByteCatchList[sz];
        previousOffset = -1;
    }


    @Override
    public void visitInvalid(int opcode, int offset, int length) {
        visitCommon(offset, length, true);
    }

    @Override
    public void visitNoArgs(int opcode, int offset, int length, mod.agus.jcoderz.dx.rop.type.Type type) {
        switch (opcode) {
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IRETURN:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.RETURN: {
                visitCommon(offset, length, false);
                targetLists[offset] = mod.agus.jcoderz.dx.util.IntList.EMPTY;
                break;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.ATHROW: {
                visitCommon(offset, length, false);
                visitThrowing(offset, length, false);
                break;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IALOAD:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.LALOAD:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.FALOAD:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.DALOAD:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.AALOAD:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.BALOAD:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.CALOAD:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.SALOAD:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IASTORE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.LASTORE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.FASTORE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.DASTORE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.AASTORE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.BASTORE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.CASTORE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.SASTORE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.ARRAYLENGTH:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.MONITORENTER:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.MONITOREXIT: {
                visitCommon(offset, length, true);
                visitThrowing(offset, length, true);
                break;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IDIV:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IREM: {
                visitCommon(offset, length, true);
                if ((type == mod.agus.jcoderz.dx.rop.type.Type.INT) || (type == mod.agus.jcoderz.dx.rop.type.Type.LONG)) {
                    visitThrowing(offset, length, true);
                }
                break;
            }
            default: {
                visitCommon(offset, length, true);
                break;
            }
        }
    }

    @Override
    public void visitLocal(int opcode, int offset, int length,
                           int idx, Type type, int value) {
        if (opcode == mod.agus.jcoderz.dx.cf.code.ByteOps.RET) {
            visitCommon(offset, length, false);
            targetLists[offset] = mod.agus.jcoderz.dx.util.IntList.EMPTY;
        } else {
            visitCommon(offset, length, true);
        }
    }

    @Override
    public void visitConstant(int opcode, int offset, int length,
                              mod.agus.jcoderz.dx.rop.cst.Constant cst, int value) {
        visitCommon(offset, length, true);

        if (cst instanceof CstMemberRef || cst instanceof mod.agus.jcoderz.dx.rop.cst.CstType ||
            cst instanceof CstString || cst instanceof CstInvokeDynamic ||
            cst instanceof CstMethodHandle || cst instanceof CstProtoRef) {
            visitThrowing(offset, length, true);
        }
    }

    @Override
    public void visitBranch(int opcode, int offset, int length,
            int target) {
        switch (opcode) {
            case mod.agus.jcoderz.dx.cf.code.ByteOps.GOTO: {
                visitCommon(offset, length, false);
                targetLists[offset] = mod.agus.jcoderz.dx.util.IntList.makeImmutable(target);
                break;
            }
            case ByteOps.JSR: {
                addWorkIfNecessary(offset, true);

            }
            default: {
                int next = offset + length;
                visitCommon(offset, length, true);
                addWorkIfNecessary(next, true);
                targetLists[offset] = mod.agus.jcoderz.dx.util.IntList.makeImmutable(next, target);
                break;
            }
        }

        addWorkIfNecessary(target, true);
    }

    @Override
    public void visitSwitch(int opcode, int offset, int length,
                            SwitchList cases, int padding) {
        visitCommon(offset, length, false);
        addWorkIfNecessary(cases.getDefaultTarget(), true);

        int sz = cases.size();
        for (int i = 0; i < sz; i++) {
            addWorkIfNecessary(cases.getTarget(i), true);
        }

        targetLists[offset] = cases.getTargets();
    }

    @Override
    public void visitNewarray(int offset, int length, CstType type,
            ArrayList<Constant> intVals) {
        visitCommon(offset, length, true);
        visitThrowing(offset, length, true);
    }

    private ByteBlockList getBlockList() {
        mod.agus.jcoderz.dx.cf.code.BytecodeArray bytes = method.getCode();
        mod.agus.jcoderz.dx.cf.code.ByteBlock[] bbs = new mod.agus.jcoderz.dx.cf.code.ByteBlock[bytes.size()];
        int count = 0;

        for (int at = 0, next; ; at = next) {
            next = mod.agus.jcoderz.dx.util.Bits.findFirst(blockSet, at + 1);
            if (next < 0) {
                break;
            }

            if (mod.agus.jcoderz.dx.util.Bits.get(liveSet, at)) {
                mod.agus.jcoderz.dx.util.IntList targets = null;
                int targetsAt = -1;
                mod.agus.jcoderz.dx.cf.code.ByteCatchList blockCatches;

                for (int i = next - 1; i >= at; i--) {
                    targets = targetLists[i];
                    if (targets != null) {
                        targetsAt = i;
                        break;
                    }
                }

                if (targets == null) {
                    targets = IntList.makeImmutable(next);
                    blockCatches = mod.agus.jcoderz.dx.cf.code.ByteCatchList.EMPTY;
                } else {
                    blockCatches = catchLists[targetsAt];
                    if (blockCatches == null) {
                        blockCatches = mod.agus.jcoderz.dx.cf.code.ByteCatchList.EMPTY;
                    }
                }

                bbs[count] =
                    new ByteBlock(at, at, next, targets, blockCatches);
                count++;
            }
        }

        ByteBlockList result = new ByteBlockList(count);
        for (int i = 0; i < count; i++) {
            result.set(i, bbs[i]);
        }

        return result;
    }

    private void doit() {
        BytecodeArray bytes = method.getCode();
        mod.agus.jcoderz.dx.cf.code.ByteCatchList catches = method.getCatches();
        int catchSz = catches.size();

        mod.agus.jcoderz.dx.util.Bits.set(workSet, 0);
        mod.agus.jcoderz.dx.util.Bits.set(blockSet, 0);

        while (!mod.agus.jcoderz.dx.util.Bits.isEmpty(workSet)) {
            try {
                bytes.processWorkSet(workSet, this);
            } catch (IllegalArgumentException ex) {

                throw new SimException("flow of control falls off " +
                                       "end of method",
                                       ex);
            }

            for (int i = 0; i < catchSz; i++) {
                mod.agus.jcoderz.dx.cf.code.ByteCatchList.Item item = catches.get(i);
                int start = item.getStartPc();
                int end = item.getEndPc();
                if (mod.agus.jcoderz.dx.util.Bits.anyInRange(liveSet, start, end)) {
                    mod.agus.jcoderz.dx.util.Bits.set(blockSet, start);
                    mod.agus.jcoderz.dx.util.Bits.set(blockSet, end);
                    addWorkIfNecessary(item.getHandlerPc(), true);
                }
            }
        }
    }

    private void addWorkIfNecessary(int offset, boolean blockStart) {
        if (!mod.agus.jcoderz.dx.util.Bits.get(liveSet, offset)) {
            mod.agus.jcoderz.dx.util.Bits.set(workSet, offset);
        }

        if (blockStart) {
            mod.agus.jcoderz.dx.util.Bits.set(blockSet, offset);
        }
    }

    private void visitCommon(int offset, int length, boolean nextIsLive) {
        mod.agus.jcoderz.dx.util.Bits.set(liveSet, offset);

        if (nextIsLive) {
            addWorkIfNecessary(offset + length, false);
        } else {
            Bits.set(blockSet, offset + length);
        }
    }

    private void visitThrowing(int offset, int length, boolean nextIsLive) {
        int next = offset + length;

        if (nextIsLive) {
            addWorkIfNecessary(next, true);
        }

        ByteCatchList catches = method.getCatches().listFor(offset);
        catchLists[offset] = catches;
        targetLists[offset] = catches.toTargetList(nextIsLive ? next : -1);
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
