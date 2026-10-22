
package mod.agus.jcoderz.dx.cf.attrib;

import mod.agus.jcoderz.dx.cf.code.LocalVariableList;
import mod.agus.jcoderz.dx.util.MutabilityException;

public abstract class BaseLocalVariables extends BaseAttribute {
    private final mod.agus.jcoderz.dx.cf.code.LocalVariableList localVariables;

    public BaseLocalVariables(String name,
            mod.agus.jcoderz.dx.cf.code.LocalVariableList localVariables) {
        super(name);

        try {
            if (localVariables.isMutable()) {
                throw new MutabilityException("localVariables.isMutable()");
            }
        } catch (NullPointerException ex) {

            throw new NullPointerException("localVariables == null");
        }

        this.localVariables = localVariables;
    }

    @Override
    public final int byteLength() {
        return 8 + localVariables.size() * 10;
    }

    public final LocalVariableList getLocalVariables() {
        return localVariables;
    }
}
