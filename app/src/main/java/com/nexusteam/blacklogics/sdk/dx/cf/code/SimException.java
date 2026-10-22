
package mod.agus.jcoderz.dx.cf.code;

import mod.agus.jcoderz.dex.util.ExceptionWithContext;

public class SimException
        extends ExceptionWithContext {
    public SimException(String message) {
        super(message);
    }

    public SimException(Throwable cause) {
        super(cause);
    }

    public SimException(String message, Throwable cause) {
        super(message, cause);
    }
}
