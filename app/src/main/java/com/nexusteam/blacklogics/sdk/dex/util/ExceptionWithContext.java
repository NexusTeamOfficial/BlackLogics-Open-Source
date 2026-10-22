
package mod.agus.jcoderz.dex.util;

import java.io.PrintStream;
import java.io.PrintWriter;

public class ExceptionWithContext extends RuntimeException {
    private StringBuffer context;

    public static ExceptionWithContext withContext(Throwable ex, String str) {
        ExceptionWithContext ewc;

        if (ex instanceof ExceptionWithContext) {
            ewc = (ExceptionWithContext) ex;
        } else {
            ewc = new ExceptionWithContext(ex);
        }

        ewc.addContext(str);
        return ewc;
    }

    public ExceptionWithContext(String message) {
        this(message, null);
    }

    public ExceptionWithContext(Throwable cause) {
        this(null, cause);
    }

    public ExceptionWithContext(String message, Throwable cause) {
        super((message != null) ? message :
              (cause != null) ? cause.getMessage() : null,
              cause);

        if (cause instanceof ExceptionWithContext) {
            String ctx = ((ExceptionWithContext) cause).context.toString();
            context = new StringBuffer(ctx.length() + 200);
            context.append(ctx);
        } else {
            context = new StringBuffer(200);
        }
    }

    @Override
    public void printStackTrace(PrintStream out) {
        super.printStackTrace(out);
        out.println(context);
    }

    @Override
    public void printStackTrace(PrintWriter out) {
        super.printStackTrace(out);
        out.println(context);
    }

    public void addContext(String str) {
        if (str == null) {
            throw new NullPointerException("str == null");
        }

        context.append(str);
        if (!str.endsWith("\n")) {
            context.append('\n');
        }
    }

    public String getContext() {
        return context.toString();
    }

    public void printContext(PrintStream out) {
        out.println(getMessage());
        out.print(context);
    }

    public void printContext(PrintWriter out) {
        out.println(getMessage());
        out.print(context);
    }
}
