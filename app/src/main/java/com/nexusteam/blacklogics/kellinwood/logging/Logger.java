package kellinwood.logging;

public abstract class Logger {

    public static final String ERROR = "ERROR";
    public static final String WARN = "WARN";
    public static final String INFO = "INFO";
    public static final String DEBUG = "DEBUG";

    public abstract boolean isErrorEnabled();
    public abstract void error( String message);
    public abstract void error( String message, Throwable t);


    public abstract boolean isWarnEnabled();
    public abstract void warn( String message);
    public abstract void warn( String message, Throwable t);

    public abstract boolean isInfoEnabled();
    public abstract void info( String message);
    public abstract void info( String message, Throwable t);

    public abstract boolean isDebugEnabled();
    public abstract void debug( String message);
    public abstract void debug( String message, Throwable t);


    public void error( Object message) { error( toStringOrNull(message) ); }
    public void error( Object message, Throwable t) { error (toStringOrNull(message), t); }

    public void warn( Object message) { warn( toStringOrNull(message) );}
    public void warn( Object message, Throwable t) { warn(toStringOrNull(message), t);}

    public void info( Object message) { info( toStringOrNull(message) );}
    public void info( Object message, Throwable t) { info( toStringOrNull(message), t);}

    public void debug( Object message) { debug( toStringOrNull(message) ); }
    public void debug( Object message, Throwable t) { debug( toStringOrNull(message), t); }

    public static Logger getLogger( String name) {
        return LogManager.getLogger(name);
    }
    public static Logger getLogger( Class clazz) {
        return LogManager.getLogger(clazz);
    }

    private String toStringOrNull(Object message) {
        return (message != null) ? message.toString() : null;
    }
}
