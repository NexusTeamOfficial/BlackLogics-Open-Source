
package mod.agus.jcoderz.dx.cf.iface;

public interface AttributeList {
    public boolean isMutable();

    public int size();

    public Attribute get(int n);

    public int byteLength();

    public Attribute findFirst(String name);

    public Attribute findNext(Attribute attrib);
}
