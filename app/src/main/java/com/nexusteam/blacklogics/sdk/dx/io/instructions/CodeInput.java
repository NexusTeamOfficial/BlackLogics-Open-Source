
package mod.agus.jcoderz.dx.io.instructions;

import java.io.EOFException;

public interface CodeInput extends CodeCursor {
    public boolean hasMore();

    public int read() throws EOFException;

    public int readInt() throws EOFException;

    public long readLong() throws EOFException;
}
