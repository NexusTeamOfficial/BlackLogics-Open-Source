
package mod.agus.jcoderz.dx.cf.code;

import mod.agus.jcoderz.dex.util.ExceptionWithContext;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeBearer;
import mod.agus.jcoderz.dx.util.Hex;
import mod.agus.jcoderz.dx.util.MutabilityControl;

public final class ExecutionStack extends MutabilityControl {
    private final mod.agus.jcoderz.dx.rop.type.TypeBearer[] stack;

    private final boolean[] local;
    private int stackPtr;

    public ExecutionStack(int maxStack) {
        super(maxStack != 0);
        stack = new mod.agus.jcoderz.dx.rop.type.TypeBearer[maxStack];
        local = new boolean[maxStack];
        stackPtr = 0;
    }

    public ExecutionStack copy() {
        ExecutionStack result = new ExecutionStack(stack.length);

        System.arraycopy(stack, 0, result.stack, 0, stack.length);
        System.arraycopy(local, 0, result.local, 0, local.length);
        result.stackPtr = stackPtr;

        return result;
    }

    public void annotate(ExceptionWithContext ex) {
        int limit = stackPtr - 1;

        for (int i = 0; i <= limit; i++) {
            String idx = (i == limit) ? "top0" : Hex.u2(limit - i);

            ex.addContext("stack[" + idx + "]: " +
                          stackElementString(stack[i]));
        }
    }

    public void makeInitialized(mod.agus.jcoderz.dx.rop.type.Type type) {
        if (stackPtr == 0) {

            return;
        }

        throwIfImmutable();

        mod.agus.jcoderz.dx.rop.type.Type initializedType = type.getInitializedType();

        for (int i = 0; i < stackPtr; i++) {
            if (stack[i] == type) {
                stack[i] = initializedType;
            }
        }
    }

    public int getMaxStack() {
        return stack.length;
    }

    public int size() {
        return stackPtr;
    }

    public void clear() {
        throwIfImmutable();

        for (int i = 0; i < stackPtr; i++) {
            stack[i] = null;
            local[i] = false;
        }

        stackPtr = 0;
    }

    public void push(mod.agus.jcoderz.dx.rop.type.TypeBearer type) {
        throwIfImmutable();

        int category;

        try {
            type = type.getFrameType();
            category = type.getType().getCategory();
        } catch (NullPointerException ex) {

            throw new NullPointerException("type == null");
        }

        if ((stackPtr + category) > stack.length) {
            throwSimException("overflow");
            return;
        }

        if (category == 2) {
            stack[stackPtr] = null;
            stackPtr++;
        }

        stack[stackPtr] = type;
        stackPtr++;
    }

    public void setLocal() {
        throwIfImmutable();

        local[stackPtr] = true;
    }

    public mod.agus.jcoderz.dx.rop.type.TypeBearer peek(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n < 0");
        }

        if (n >= stackPtr) {
            return throwSimException("underflow");
        }

        return stack[stackPtr - n - 1];
    }

    public boolean peekLocal(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n < 0");
        }

        if (n >= stackPtr) {
            throw new SimException("stack: underflow");
        }

        return local[stackPtr - n - 1];
    }

    public Type peekType(int n) {
        return peek(n).getType();
    }

    public mod.agus.jcoderz.dx.rop.type.TypeBearer pop() {
        throwIfImmutable();

        mod.agus.jcoderz.dx.rop.type.TypeBearer result = peek(0);

        stack[stackPtr - 1] = null;
        local[stackPtr - 1] = false;
        stackPtr -= result.getType().getCategory();

        return result;
    }

    public void change(int n, mod.agus.jcoderz.dx.rop.type.TypeBearer type) {
        throwIfImmutable();

        try {
            type = type.getFrameType();
        } catch (NullPointerException ex) {

            throw new NullPointerException("type == null");
        }

        int idx = stackPtr - n - 1;
        mod.agus.jcoderz.dx.rop.type.TypeBearer orig = stack[idx];

        if ((orig == null) ||
            (orig.getType().getCategory() != type.getType().getCategory())) {
            throwSimException("incompatible substitution: " +
                              stackElementString(orig) + " -> " +
                              stackElementString(type));
        }

        stack[idx] = type;
    }

    public ExecutionStack merge(ExecutionStack other) {
        try {
            return Merger.mergeStack(this, other);
        } catch (SimException ex) {
            ex.addContext("underlay stack:");
            this.annotate(ex);
            ex.addContext("overlay stack:");
            other.annotate(ex);
            throw ex;
        }
    }

    private static String stackElementString(mod.agus.jcoderz.dx.rop.type.TypeBearer type) {
        if (type == null) {
            return "<invalid>";
        }

        return type.toString();
    }

    private static TypeBearer throwSimException(String msg) {
        throw new SimException("stack: " + msg);
    }
}
