package kellinwood.logging;

public class DeaultLoggerFactory implements LoggerFactory {

    public Logger getLogger(String category) {
        return new DefaultLogger(category);
    }
}
