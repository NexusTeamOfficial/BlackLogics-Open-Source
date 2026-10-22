package kellinwood.logging;

public class NullLoggerFactory implements LoggerFactory {

    static Logger logger = new Logger() {

        public void debug(String message) {
        }

        public void debug(String message, Throwable t) {
        }

        public void error(String message) {
        }

        public void error(String message, Throwable t) {
        }

        public void info(String message) {
        }

        public void info(String message, Throwable t) {
        }

        public boolean isDebugEnabled() {
            return false;
        }

        public boolean isErrorEnabled() {
            return false;
        }

        public boolean isInfoEnabled() {
            return false;
        }

        public boolean isWarnEnabled() {
            return false;
        }

        public void warn(String message) {
        }

        public void warn(String message, Throwable t) {
        }
        
    };
    

    public Logger getLogger(String category) {
        return logger;
    }

}
