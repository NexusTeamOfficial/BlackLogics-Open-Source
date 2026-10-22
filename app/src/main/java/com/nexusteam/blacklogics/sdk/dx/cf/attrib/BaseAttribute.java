
package mod.agus.jcoderz.dx.cf.attrib;

import mod.agus.jcoderz.dx.cf.iface.Attribute;

public abstract class BaseAttribute implements Attribute {
    private final String name;

    public BaseAttribute(String name) {
        if (name == null) {
            throw new NullPointerException("name == null");
        }

        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
