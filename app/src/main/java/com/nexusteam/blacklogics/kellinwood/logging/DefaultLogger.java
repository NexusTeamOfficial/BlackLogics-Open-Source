package kellinwood.logging;

public class DefaultLogger extends Logger
{

    protected String category;
    
    public DefaultLogger(String category) {
        this.category = category;
    }

    public String getCategory() {
        return LogManager.getCategory(category);
    }

    public void debug(String message, Throwable t) {
        write( DEBUG, getCategory(), message, t);
    }

    public void debug(String message) {
        write( DEBUG, getCategory(), message, null);
    }

    public void error(String message, Throwable t) {
        write( ERROR, getCategory(), message, t);
    }

    public void error(String message) {
        write( ERROR, getCategory(), message, null);
    }

    public void info(String message, Throwable t) {
        write( INFO, getCategory(), message, t);
    }

    public void info(String message) {
        write( INFO, getCategory(), message, null);
    }

    public void warn(String message, Throwable t) {
        write( WARN, getCategory(), message, t);
    }

    public void warn(String message) {
        write( WARN, getCategory(), message, null);
    }

    public boolean isDebugEnabled() {
        return true;
    }

    public boolean isErrorEnabled() {
        return true;
    }

    public boolean isInfoEnabled() {
        return true;
    }

    public boolean isWarnEnabled() {
        return true;
    }

    protected void write( String level, String category, String message, Throwable t) {
        LogManager.write(level, category, message, t);
    }
}
