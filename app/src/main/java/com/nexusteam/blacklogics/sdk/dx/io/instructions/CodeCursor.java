
package mod.agus.jcoderz.dx.io.instructions;

public interface CodeCursor {
    public int cursor();

    public int baseAddressForCursor();

    public void setBaseAddress(int targetAddress, int baseAddress);
}
