
package mod.agus.jcoderz.dx.cf.iface;

import mod.agus.jcoderz.dx.util.ByteArray;

public interface ParseObserver {
    public void changeIndent(int indentDelta);

    public void startParsingMember(mod.agus.jcoderz.dx.util.ByteArray bytes, int offset, String name,
                                   String descriptor);

    public void endParsingMember(mod.agus.jcoderz.dx.util.ByteArray bytes, int offset, String name,
                                 String descriptor, Member member);

    public void parsed(ByteArray bytes, int offset, int len, String human);
}
