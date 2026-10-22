
package mod.agus.jcoderz.dex;

import mod.agus.jcoderz.dex.util.Unsigned;

public class CallSiteId implements Comparable<CallSiteId> {

    private final Dex dex;
    private final int offset;

    public CallSiteId(Dex dex, int offset) {
        this.dex = dex;
        this.offset = offset;
    }

    @Override
    public int compareTo(CallSiteId o) {
        return Unsigned.compare(offset, o.offset);
    }

    public int getCallSiteOffset() {
        return offset;
    }

    public void writeTo(Dex.Section out) {
        out.writeInt(offset);
    }

    @Override
    public String toString() {
        if (dex == null) {
            return String.valueOf(offset);
        }
        return dex.protoIds().get(offset).toString();
    }
}
