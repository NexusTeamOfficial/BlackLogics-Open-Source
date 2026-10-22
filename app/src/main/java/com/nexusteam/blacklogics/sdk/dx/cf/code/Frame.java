
package mod.agus.jcoderz.dx.cf.code;

import mod.agus.jcoderz.dex.util.ExceptionWithContext;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.StdTypeList;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.IntList;

public final class Frame {
    private final mod.agus.jcoderz.dx.cf.code.LocalsArray locals;

    private final mod.agus.jcoderz.dx.cf.code.ExecutionStack stack;

    private final mod.agus.jcoderz.dx.util.IntList subroutines;

    private Frame(mod.agus.jcoderz.dx.cf.code.LocalsArray locals, mod.agus.jcoderz.dx.cf.code.ExecutionStack stack) {
        this(locals, stack, mod.agus.jcoderz.dx.util.IntList.EMPTY);
    }

    private Frame(mod.agus.jcoderz.dx.cf.code.LocalsArray locals,
                  mod.agus.jcoderz.dx.cf.code.ExecutionStack stack, mod.agus.jcoderz.dx.util.IntList subroutines) {
        if (locals == null) {
            throw new NullPointerException("locals == null");
        }

        if (stack == null) {
            throw new NullPointerException("stack == null");
        }

        subroutines.throwIfMutable();

        this.locals = locals;
        this.stack = stack;
        this.subroutines = subroutines;
    }

    public Frame(int maxLocals, int maxStack) {
        this(new OneLocalsArray(maxLocals), new mod.agus.jcoderz.dx.cf.code.ExecutionStack(maxStack));
    }

    public Frame copy() {
        return new Frame(locals.copy(), stack.copy(), subroutines);
    }

    public void setImmutable() {
        locals.setImmutable();
        stack.setImmutable();

    }

    public void makeInitialized(mod.agus.jcoderz.dx.rop.type.Type type) {
        locals.makeInitialized(type);
        stack.makeInitialized(type);
    }

    public mod.agus.jcoderz.dx.cf.code.LocalsArray getLocals() {
        return locals;
    }

    public mod.agus.jcoderz.dx.cf.code.ExecutionStack getStack() {
        return stack;
    }

    public mod.agus.jcoderz.dx.util.IntList getSubroutines() {
        return subroutines;
    }

    public void initializeWithParameters(StdTypeList params) {
        int at = 0;
        int sz = params.size();

        for (int i = 0; i < sz; i++) {
             Type one = params.get(i);
             locals.set(at, one);
             at += one.getCategory();
        }
    }

    public Frame subFrameForLabel(int startLabel, int subLabel) {
        mod.agus.jcoderz.dx.cf.code.LocalsArray subLocals = null;

        if (locals instanceof LocalsArraySet) {
            subLocals = ((LocalsArraySet)locals).subArrayForLabel(subLabel);
        }

        mod.agus.jcoderz.dx.util.IntList newSubroutines;
        try {
            newSubroutines = subroutines.mutableCopy();

            if (newSubroutines.pop() != startLabel) {
                throw new RuntimeException("returning from invalid subroutine");
            }
            newSubroutines.setImmutable();
        } catch (IndexOutOfBoundsException ex) {
            throw new RuntimeException("returning from invalid subroutine");
        } catch (NullPointerException ex) {
            throw new NullPointerException("can't return from non-subroutine");
        }

        return (subLocals == null) ? null
                : new Frame(subLocals, stack, newSubroutines);
    }

    public Frame mergeWith(Frame other) {
        mod.agus.jcoderz.dx.cf.code.LocalsArray resultLocals;
        mod.agus.jcoderz.dx.cf.code.ExecutionStack resultStack;
        mod.agus.jcoderz.dx.util.IntList resultSubroutines;

        resultLocals = getLocals().merge(other.getLocals());
        resultStack = getStack().merge(other.getStack());
        resultSubroutines = mergeSubroutineLists(other.subroutines);

        resultLocals = adjustLocalsForSubroutines(
                resultLocals, resultSubroutines);

        if ((resultLocals == getLocals())
                && (resultStack == getStack())
                && subroutines == resultSubroutines) {
            return this;
        }

        return new Frame(resultLocals, resultStack, resultSubroutines);
    }

    private mod.agus.jcoderz.dx.util.IntList mergeSubroutineLists(mod.agus.jcoderz.dx.util.IntList otherSubroutines) {
        if (subroutines.equals(otherSubroutines)) {
            return subroutines;
        }

        mod.agus.jcoderz.dx.util.IntList resultSubroutines = new mod.agus.jcoderz.dx.util.IntList();

        int szSubroutines = subroutines.size();
        int szOthers = otherSubroutines.size();
        for (int i = 0; i < szSubroutines && i < szOthers
                && (subroutines.get(i) == otherSubroutines.get(i)); i++) {
            resultSubroutines.add(i);
        }

        resultSubroutines.setImmutable();

        return resultSubroutines;
    }

    private static mod.agus.jcoderz.dx.cf.code.LocalsArray adjustLocalsForSubroutines(
            mod.agus.jcoderz.dx.cf.code.LocalsArray locals, mod.agus.jcoderz.dx.util.IntList subroutines) {
        if (! (locals instanceof LocalsArraySet)) {

            return locals;
        }

        LocalsArraySet laSet = (LocalsArraySet)locals;

        if (subroutines.size() == 0) {

            return laSet.getPrimary();
        }

        return laSet;
    }

    public Frame mergeWithSubroutineCaller(Frame other, int subLabel,
            int predLabel) {
        LocalsArray resultLocals;
        mod.agus.jcoderz.dx.cf.code.ExecutionStack resultStack;

        resultLocals = getLocals().mergeWithSubroutineCaller(
                other.getLocals(), predLabel);
        resultStack = getStack().merge(other.getStack());

        mod.agus.jcoderz.dx.util.IntList newOtherSubroutines = other.subroutines.mutableCopy();
        newOtherSubroutines.add(subLabel);
        newOtherSubroutines.setImmutable();

        if ((resultLocals == getLocals())
                && (resultStack == getStack())
                && subroutines.equals(newOtherSubroutines)) {
            return this;
        }

        mod.agus.jcoderz.dx.util.IntList resultSubroutines;

        if (subroutines.equals(newOtherSubroutines)) {
            resultSubroutines = subroutines;
        } else {
            mod.agus.jcoderz.dx.util.IntList nonResultSubroutines;

            if (subroutines.size() > newOtherSubroutines.size()) {
                resultSubroutines = subroutines;
                nonResultSubroutines = newOtherSubroutines;
            } else {
                resultSubroutines = newOtherSubroutines;
                nonResultSubroutines = subroutines;
            }

            int szResult = resultSubroutines.size();
            int szNonResult = nonResultSubroutines.size();

            for (int i = szNonResult - 1; i >=0; i-- ) {
                if (nonResultSubroutines.get(i)
                        != resultSubroutines.get(
                        i + (szResult - szNonResult))) {
                    throw new
                            RuntimeException("Incompatible merged subroutines");
                }
            }

        }

        return new Frame(resultLocals, resultStack, resultSubroutines);
    }

    public Frame makeNewSubroutineStartFrame(int subLabel, int callerLabel) {
        mod.agus.jcoderz.dx.util.IntList newSubroutines = subroutines.mutableCopy();
        newSubroutines.add(subLabel);
        Frame newFrame = new Frame(locals.getPrimary(), stack,
                IntList.makeImmutable(subLabel));
        return newFrame.mergeWithSubroutineCaller(this, subLabel, callerLabel);
    }

    public Frame makeExceptionHandlerStartFrame(CstType exceptionClass) {
        ExecutionStack newStack = getStack().copy();

        newStack.clear();
        newStack.push(exceptionClass);

        return new Frame(getLocals(), newStack, subroutines);
    }

    public void annotate(ExceptionWithContext ex) {
        locals.annotate(ex);
        stack.annotate(ex);
    }
}
