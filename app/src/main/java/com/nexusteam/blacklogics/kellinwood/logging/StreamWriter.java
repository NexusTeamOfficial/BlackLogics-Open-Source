package kellinwood.logging;

import java.io.PrintStream;

public class StreamWriter extends AbstractLogWriter {


    private final PrintStream stream;

    public StreamWriter(PrintStream stream)
    {
        this.stream = stream;
    }

    @Override
    public void writeImpl(String level, String category, String message, Throwable t) {
        stream.println( format(level, category, message));
        if (t != null) t.printStackTrace(stream);
    }
}
