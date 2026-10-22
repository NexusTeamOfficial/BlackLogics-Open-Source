
package mod.agus.jcoderz.dx.cf.code;

import java.util.ArrayList;

import mod.agus.jcoderz.dx.rop.code.LocalItem;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.type.Prototype;
import mod.agus.jcoderz.dx.rop.type.StdTypeList;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeBearer;

public abstract class BaseMachine implements Machine {
    private final mod.agus.jcoderz.dx.rop.type.Prototype prototype;

    private mod.agus.jcoderz.dx.rop.type.TypeBearer[] args;

    private int argCount;

    private mod.agus.jcoderz.dx.rop.type.Type auxType;

    private int auxInt;

    private mod.agus.jcoderz.dx.rop.cst.Constant auxCst;

    private int auxTarget;

    private mod.agus.jcoderz.dx.cf.code.SwitchList auxCases;

    private ArrayList<mod.agus.jcoderz.dx.rop.cst.Constant> auxInitValues;

    private int localIndex;

    private boolean localInfo;

    private mod.agus.jcoderz.dx.rop.code.RegisterSpec localTarget;

    private mod.agus.jcoderz.dx.rop.type.TypeBearer[] results;

    private int resultCount;

    public BaseMachine(mod.agus.jcoderz.dx.rop.type.Prototype prototype) {
        if (prototype == null) {
            throw new NullPointerException("prototype == null");
        }

        this.prototype = prototype;
        args = new mod.agus.jcoderz.dx.rop.type.TypeBearer[10];
        results = new mod.agus.jcoderz.dx.rop.type.TypeBearer[6];
        clearArgs();
    }

    @Override
    public mod.agus.jcoderz.dx.rop.type.Prototype getPrototype() {
        return prototype;
    }

    @Override
    public final void clearArgs() {
        argCount = 0;
        auxType = null;
        auxInt = 0;
        auxCst = null;
        auxTarget = 0;
        auxCases = null;
        auxInitValues = null;
        localIndex = -1;
        localInfo = false;
        localTarget = null;
        resultCount = -1;
    }

    @Override
    public final void popArgs(mod.agus.jcoderz.dx.cf.code.Frame frame, int count) {
        mod.agus.jcoderz.dx.cf.code.ExecutionStack stack = frame.getStack();

        clearArgs();

        if (count > args.length) {

            args = new mod.agus.jcoderz.dx.rop.type.TypeBearer[count + 10];
        }

        for (int i = count - 1; i >= 0; i--) {
            args[i] = stack.pop();
        }

        argCount = count;
    }

    @Override
    public void popArgs(mod.agus.jcoderz.dx.cf.code.Frame frame, Prototype prototype) {
        StdTypeList types = prototype.getParameterTypes();
        int size = types.size();


        popArgs(frame, size);



        for (int i = 0; i < size; i++) {
            if (! mod.agus.jcoderz.dx.cf.code.Merger.isPossiblyAssignableFrom(types.getType(i), args[i])) {
                throw new SimException("at stack depth " + (size - 1 - i) +
                        ", expected type " + types.getType(i).toHuman() +
                        " but found " + args[i].getType().toHuman());
            }
        }
    }

    @Override
    public final void popArgs(mod.agus.jcoderz.dx.cf.code.Frame frame, mod.agus.jcoderz.dx.rop.type.Type type) {

        popArgs(frame, 1);


        if (! mod.agus.jcoderz.dx.cf.code.Merger.isPossiblyAssignableFrom(type, args[0])) {
            throw new SimException("expected type " + type.toHuman() +
                    " but found " + args[0].getType().toHuman());
        }
    }

    @Override
    public final void popArgs(mod.agus.jcoderz.dx.cf.code.Frame frame, mod.agus.jcoderz.dx.rop.type.Type type1, mod.agus.jcoderz.dx.rop.type.Type type2) {

        popArgs(frame, 2);



        if (! mod.agus.jcoderz.dx.cf.code.Merger.isPossiblyAssignableFrom(type1, args[0])) {
            throw new SimException("expected type " + type1.toHuman() +
                    " but found " + args[0].getType().toHuman());
        }

        if (! mod.agus.jcoderz.dx.cf.code.Merger.isPossiblyAssignableFrom(type2, args[1])) {
            throw new SimException("expected type " + type2.toHuman() +
                    " but found " + args[1].getType().toHuman());
        }
    }

    @Override
    public final void popArgs(mod.agus.jcoderz.dx.cf.code.Frame frame, mod.agus.jcoderz.dx.rop.type.Type type1, mod.agus.jcoderz.dx.rop.type.Type type2,
                              mod.agus.jcoderz.dx.rop.type.Type type3) {

        popArgs(frame, 3);



        if (! mod.agus.jcoderz.dx.cf.code.Merger.isPossiblyAssignableFrom(type1, args[0])) {
            throw new SimException("expected type " + type1.toHuman() +
                    " but found " + args[0].getType().toHuman());
        }

        if (! mod.agus.jcoderz.dx.cf.code.Merger.isPossiblyAssignableFrom(type2, args[1])) {
            throw new SimException("expected type " + type2.toHuman() +
                    " but found " + args[1].getType().toHuman());
        }

        if (! mod.agus.jcoderz.dx.cf.code.Merger.isPossiblyAssignableFrom(type3, args[2])) {
            throw new SimException("expected type " + type3.toHuman() +
                    " but found " + args[2].getType().toHuman());
        }
    }

    @Override
    public final void localArg(mod.agus.jcoderz.dx.cf.code.Frame frame, int idx) {
        clearArgs();
        args[0] = frame.getLocals().get(idx);
        argCount = 1;
        localIndex = idx;
    }

    @Override
    public final void localInfo(boolean local) {
        localInfo = local;
    }

    @Override
    public final void auxType(mod.agus.jcoderz.dx.rop.type.Type type) {
        auxType = type;
    }

    @Override
    public final void auxIntArg(int value) {
        auxInt = value;
    }

    @Override
    public final void auxCstArg(mod.agus.jcoderz.dx.rop.cst.Constant cst) {
        if (cst == null) {
            throw new NullPointerException("cst == null");
        }

        auxCst = cst;
    }

    @Override
    public final void auxTargetArg(int target) {
        auxTarget = target;
    }

    @Override
    public final void auxSwitchArg(mod.agus.jcoderz.dx.cf.code.SwitchList cases) {
        if (cases == null) {
            throw new NullPointerException("cases == null");
        }

        auxCases = cases;
    }

    @Override
    public final void auxInitValues(ArrayList<mod.agus.jcoderz.dx.rop.cst.Constant> initValues) {
        auxInitValues = initValues;
    }

    @Override
    public final void localTarget(int idx, mod.agus.jcoderz.dx.rop.type.Type type, LocalItem local) {
        localTarget = mod.agus.jcoderz.dx.rop.code.RegisterSpec.makeLocalOptional(idx, type, local);
    }

    protected final int argCount() {
        return argCount;
    }

    protected final int argWidth() {
        int result = 0;

        for (int i = 0; i < argCount; i++) {
            result += args[i].getType().getCategory();
        }

        return result;
    }

    protected final mod.agus.jcoderz.dx.rop.type.TypeBearer arg(int n) {
        if (n >= argCount) {
            throw new IllegalArgumentException("n >= argCount");
        }

        try {
            return args[n];
        } catch (ArrayIndexOutOfBoundsException ex) {

            throw new IllegalArgumentException("n < 0");
        }
    }

    protected final mod.agus.jcoderz.dx.rop.type.Type getAuxType() {
        return auxType;
    }

    protected final int getAuxInt() {
        return auxInt;
    }

    protected final mod.agus.jcoderz.dx.rop.cst.Constant getAuxCst() {
        return auxCst;
    }

    protected final int getAuxTarget() {
        return auxTarget;
    }

    protected final SwitchList getAuxCases() {
        return auxCases;
    }

    protected final ArrayList<Constant> getInitValues() {
        return auxInitValues;
    }
    protected final int getLocalIndex() {
        return localIndex;
    }

    protected final boolean getLocalInfo() {
        return localInfo;
    }

    protected final RegisterSpec getLocalTarget(boolean isMove) {
        if (localTarget == null) {
            return null;
        }

        if (resultCount != 1) {
            throw new SimException("local target with " +
                    ((resultCount == 0) ? "no" : "multiple") + " results");
        }

        mod.agus.jcoderz.dx.rop.type.TypeBearer result = results[0];
        mod.agus.jcoderz.dx.rop.type.Type resultType = result.getType();
        mod.agus.jcoderz.dx.rop.type.Type localType = localTarget.getType();

        if (resultType == localType) {
            if (isMove) {
                return localTarget.withType(result);
            } else {
                return localTarget;
            }
        }

        if (! Merger.isPossiblyAssignableFrom(localType, resultType)) {

            throwLocalMismatch(resultType, localType);
            return null;
        }

        if (localType == Type.OBJECT) {
            localTarget = localTarget.withType(result);
        }

        return localTarget;
    }

    protected final void clearResult() {
        resultCount = 0;
    }

    protected final void setResult(mod.agus.jcoderz.dx.rop.type.TypeBearer result) {
        if (result == null) {
            throw new NullPointerException("result == null");
        }

        results[0] = result;
        resultCount = 1;
    }

    protected final void addResult(mod.agus.jcoderz.dx.rop.type.TypeBearer result) {
        if (result == null) {
            throw new NullPointerException("result == null");
        }

        results[resultCount] = result;
        resultCount++;
    }

    protected final int resultCount() {
        if (resultCount < 0) {
            throw new SimException("results never set");
        }

        return resultCount;
    }

    protected final int resultWidth() {
        int width = 0;

        for (int i = 0; i < resultCount; i++) {
            width += results[i].getType().getCategory();
        }

        return width;
    }

    protected final mod.agus.jcoderz.dx.rop.type.TypeBearer result(int n) {
        if (n >= resultCount) {
            throw new IllegalArgumentException("n >= resultCount");
        }

        try {
            return results[n];
        } catch (ArrayIndexOutOfBoundsException ex) {

            throw new IllegalArgumentException("n < 0");
        }
    }

    protected final void storeResults(Frame frame) {
        if (resultCount < 0) {
            throw new SimException("results never set");
        }

        if (resultCount == 0) {

            return;
        }

        if (localTarget != null) {
            frame.getLocals().set(getLocalTarget(false));
        } else {
            ExecutionStack stack = frame.getStack();
            for (int i = 0; i < resultCount; i++) {
                if (localInfo) {
                    stack.setLocal();
                }
                stack.push(results[i]);
            }
        }
    }

    public static void throwLocalMismatch(mod.agus.jcoderz.dx.rop.type.TypeBearer found,
                                          TypeBearer local) {
        throw new SimException("local variable type mismatch: " +
                "attempt to set or access a value of type " +
                found.toHuman() +
                " using a local variable of type " +
                local.toHuman() +
                ". This is symptomatic of .class transformation tools " +
                "that ignore local variable information.");
    }
}
