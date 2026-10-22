
package mod.agus.jcoderz.dx.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;

public final class TwoColumnOutput {
    private final Writer out;

    private final int leftWidth;

    private final StringBuffer leftBuf;

    private final StringBuffer rightBuf;

    private final mod.agus.jcoderz.dx.util.IndentingWriter leftColumn;

    private final mod.agus.jcoderz.dx.util.IndentingWriter rightColumn;

    public static String toString(String s1, int width1, String spacer,
                                  String s2, int width2) {
        int len1 = s1.length();
        int len2 = s2.length();

        StringWriter sw = new StringWriter((len1 + len2) * 3);
        TwoColumnOutput twoOut =
            new TwoColumnOutput(sw, width1, width2, spacer);

        try {
            twoOut.getLeft().write(s1);
            twoOut.getRight().write(s2);
        } catch (IOException ex) {
            throw new RuntimeException("shouldn't happen", ex);
        }

        twoOut.flush();
        return sw.toString();
    }

    public TwoColumnOutput(Writer out, int leftWidth, int rightWidth,
                           String spacer) {
        if (out == null) {
            throw new NullPointerException("out == null");
        }

        if (leftWidth < 1) {
            throw new IllegalArgumentException("leftWidth < 1");
        }

        if (rightWidth < 1) {
            throw new IllegalArgumentException("rightWidth < 1");
        }

        if (spacer == null) {
            throw new NullPointerException("spacer == null");
        }

        StringWriter leftWriter = new StringWriter(1000);
        StringWriter rightWriter = new StringWriter(1000);

        this.out = out;
        this.leftWidth = leftWidth;
        this.leftBuf = leftWriter.getBuffer();
        this.rightBuf = rightWriter.getBuffer();
        this.leftColumn = new mod.agus.jcoderz.dx.util.IndentingWriter(leftWriter, leftWidth);
        this.rightColumn =
            new IndentingWriter(rightWriter, rightWidth, spacer);
    }

    public TwoColumnOutput(OutputStream out, int leftWidth, int rightWidth,
                           String spacer) {
        this(new OutputStreamWriter(out), leftWidth, rightWidth, spacer);
    }

    public Writer getLeft() {
        return leftColumn;
    }

    public Writer getRight() {
        return rightColumn;
    }

    public void flush() {
        try {
            appendNewlineIfNecessary(leftBuf, leftColumn);
            appendNewlineIfNecessary(rightBuf, rightColumn);
            outputFullLines();
            flushLeft();
            flushRight();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void outputFullLines() throws IOException {
        for (;;) {
            int leftLen = leftBuf.indexOf("\n");
            if (leftLen < 0) {
                return;
            }

            int rightLen = rightBuf.indexOf("\n");
            if (rightLen < 0) {
                return;
            }

            if (leftLen != 0) {
                out.write(leftBuf.substring(0, leftLen));
            }

            if (rightLen != 0) {
                writeSpaces(out, leftWidth - leftLen);
                out.write(rightBuf.substring(0, rightLen));
            }

            out.write('\n');

            leftBuf.delete(0, leftLen + 1);
            rightBuf.delete(0, rightLen + 1);
        }
    }

    private void flushLeft() throws IOException {
        appendNewlineIfNecessary(leftBuf, leftColumn);

        while (leftBuf.length() != 0) {
            rightColumn.write('\n');
            outputFullLines();
        }
    }

    private void flushRight() throws IOException {
        appendNewlineIfNecessary(rightBuf, rightColumn);

        while (rightBuf.length() != 0) {
            leftColumn.write('\n');
            outputFullLines();
        }
    }

    private static void appendNewlineIfNecessary(StringBuffer buf,
                                                 Writer out)
            throws IOException {
        int len = buf.length();

        if ((len != 0) && (buf.charAt(len - 1) != '\n')) {
            out.write('\n');
        }
    }

    private static void writeSpaces(Writer out, int amt) throws IOException {
        while (amt > 0) {
            out.write(' ');
            amt--;
        }
    }
}
