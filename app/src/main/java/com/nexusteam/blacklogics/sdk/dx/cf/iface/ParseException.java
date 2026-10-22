
package mod.agus.jcoderz.dx.cf.iface;

import mod.agus.jcoderz.dex.util.ExceptionWithContext;

public class ParseException
        extends ExceptionWithContext {
    public ParseException(String message) {
        super(message);
    }

    public ParseException(Throwable cause) {
        super(cause);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
