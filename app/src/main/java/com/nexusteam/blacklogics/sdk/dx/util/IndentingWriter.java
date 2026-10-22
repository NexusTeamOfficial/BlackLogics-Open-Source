
package mod.agus.jcoderz.dx.util;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

public final class IndentingWriter extends FilterWriter {
    private final String prefix;

    private final int width;

    private final int maxIndent;

    private int column;

    private boolean collectingIndent;

    private int indent;

    public IndentingWriter(Writer out, int width, String prefix) {
        super(out);

        if (out == null) {
            throw new NullPointerException("out == null");
        }

        if (width < 0) {
            throw new IllegalArgumentException("width < 0");
        }

        if (prefix == null) {
            throw new NullPointerException("prefix == null");
        }

        this.width = (width != 0) ? width : Integer.MAX_VALUE;
        this.maxIndent = width >> 1;
        this.prefix = (prefix.length() == 0) ? null : prefix;

        bol();
    }

    public IndentingWriter(Writer out, int width) {
        this(out, width, "");
    }

    @Override
    public void write(int c) throws IOException {
        synchronized (lock) {
            if (collectingIndent) {
                if (c == ' ') {
                    indent++;
                    if (indent >= maxIndent) {
                        indent = maxIndent;
                        collectingIndent = false;
                    }
                } else {
                    collectingIndent = false;
                }
            }

            if ((column == width) && (c != '\n')) {
                out.write('\n');
                column = 0;
            }

            if (column == 0) {
                if (prefix != null) {
                    out.write(prefix);
                }

                if (!collectingIndent) {
                    for (int i = 0; i < indent; i++) {
                        out.write(' ');
                    }
                    column = indent;
                }
            }

            out.write(c);

            if (c == '\n') {
                bol();
            } else {
                column++;
            }
        }
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        synchronized (lock) {
            while (len > 0) {
                write(cbuf[off]);
                off++;
                len--;
            }
        }
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        synchronized (lock) {
            while (len > 0) {
                write(str.charAt(off));
                off++;
                len--;
            }
        }
    }

    private void bol() {
        column = 0;
        collectingIndent = (maxIndent != 0);
        indent = 0;
    }
}
