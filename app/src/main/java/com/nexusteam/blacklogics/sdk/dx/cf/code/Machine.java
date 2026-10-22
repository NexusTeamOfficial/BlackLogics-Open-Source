
package mod.agus.jcoderz.dx.cf.code;

import java.util.ArrayList;

import mod.agus.jcoderz.dx.rop.code.LocalItem;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.type.Prototype;
import mod.agus.jcoderz.dx.rop.type.Type;

public interface Machine {
    public mod.agus.jcoderz.dx.rop.type.Prototype getPrototype();

    public void clearArgs();

    public void popArgs(Frame frame, int count);

    public void popArgs(Frame frame, Prototype prototype);

    public void popArgs(Frame frame, mod.agus.jcoderz.dx.rop.type.Type type);

    public void popArgs(Frame frame, mod.agus.jcoderz.dx.rop.type.Type type1, mod.agus.jcoderz.dx.rop.type.Type type2);

    public void popArgs(Frame frame, mod.agus.jcoderz.dx.rop.type.Type type1, mod.agus.jcoderz.dx.rop.type.Type type2, mod.agus.jcoderz.dx.rop.type.Type type3);

    public void localArg(Frame frame, int idx);

    public void localInfo(boolean local);

    public void auxType(mod.agus.jcoderz.dx.rop.type.Type type);

    public void auxIntArg(int value);

    public void auxCstArg(mod.agus.jcoderz.dx.rop.cst.Constant cst);

    public void auxTargetArg(int target);

    public void auxSwitchArg(SwitchList cases);

    public void auxInitValues(ArrayList<Constant> initValues);

    public void localTarget(int idx, Type type, LocalItem local);

    public void run(Frame frame, int offset, int opcode);
}
