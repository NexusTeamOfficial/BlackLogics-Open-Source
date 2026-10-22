
package mod.agus.jcoderz.dx.io.instructions;

import java.util.HashMap;

public final class AddressMap {
    private final HashMap<Integer,Integer> map;

    public AddressMap() {
        map = new HashMap<Integer,Integer>();
    }

    public int get(int keyAddress) {
        Integer value = map.get(keyAddress);
        return (value == null) ? -1 : value;
    }

    public void put(int keyAddress, int valueAddress) {
        map.put(keyAddress, valueAddress);
    }
}
