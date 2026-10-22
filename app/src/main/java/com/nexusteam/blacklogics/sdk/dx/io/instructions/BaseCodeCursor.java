
package mod.agus.jcoderz.dx.io.instructions;

public abstract class BaseCodeCursor implements CodeCursor {
    private final AddressMap baseAddressMap;

    private int cursor;

    public BaseCodeCursor() {
        this.baseAddressMap = new AddressMap();
        this.cursor = 0;
    }

    @Override
    public final int cursor() {
        return cursor;
    }

    @Override
    public final int baseAddressForCursor() {
        int mapped = baseAddressMap.get(cursor);
        return (mapped >= 0) ? mapped : cursor;
    }

    @Override
    public final void setBaseAddress(int targetAddress, int baseAddress) {
        baseAddressMap.put(targetAddress, baseAddress);
    }

    protected final void advance(int amount) {
        cursor += amount;
    }
}
