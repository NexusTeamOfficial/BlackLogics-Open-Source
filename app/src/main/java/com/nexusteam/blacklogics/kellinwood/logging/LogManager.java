package kellinwood.logging;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class LogManager {

    static LoggerFactory factory = new DeaultLoggerFactory();
    
    static Map<String,Logger> loggers = new TreeMap<String,Logger>();

    static Map<String,LogWriter> logWriters = new HashMap<String,LogWriter>();

    public static final String DEFAULT_WRITER = "DEFAULT";
    static {
        logWriters.put(DEFAULT_WRITER, new StreamWriter(System.out));
    }

    static String overrideCategory = null;

    public static void addLogWriter( String id, LogWriter logWriter) {
        logWriters.put(id, logWriter);
    }

    public static void setLoggerFactory( LoggerFactory f) {
        factory = f;
    }

    public static void overrideCategory( String category) {
        overrideCategory = category;
    }

    public static boolean isCategoryOverridden() {
        return overrideCategory != null;
    }

    public static String getCategory( String category) {
        if (overrideCategory != null) return overrideCategory;
        else return category;
    }

    public static Logger getLogger(String category) {
        
        Logger logger = loggers.get( category);
        if (logger == null) {
            logger = factory.getLogger(category);
            loggers.put( category, logger);
        }
        return logger;
    }

    public static Logger getLogger( Class clazz)
    {
        return getLogger(clazz.getName());
    }

    public static void write( String level, String category, String message, Throwable t) {
        for (LogWriter writer : logWriters.values()) {
            if (writer != null) writer.write( level, category, message, t);
        }
    }

    public static void main(String[] args) {
        Logger log = getLogger(LogManager.class);

        log.info("It works!");
    }
}
