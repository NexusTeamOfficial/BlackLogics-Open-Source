package kellinwood.logging;

public interface LogWriter {

    void write(String level, String category, String message, Throwable t);
}
