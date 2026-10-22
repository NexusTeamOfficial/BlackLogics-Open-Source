
package mod.agus.jcoderz.dex;

import mod.agus.jcoderz.dex.util.ExceptionWithContext;

public class DexException extends ExceptionWithContext {
    public DexException(String message) {
        super(message);
    }

    public DexException(Throwable cause) {
        super(cause);
    }
}
