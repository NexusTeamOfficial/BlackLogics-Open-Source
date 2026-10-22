
package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dx.rop.cst.Constant;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class HeaderSection extends UniformItemSection {
    private final List<mod.agus.jcoderz.dx.dex.file.HeaderItem> list;

    public HeaderSection(DexFile file) {
        super(null, file, 4);

        mod.agus.jcoderz.dx.dex.file.HeaderItem item = new HeaderItem();
        item.setIndex(0);

        this.list = Collections.singletonList(item);
    }

    @Override
    public IndexedItem get(Constant cst) {
        return null;
    }

    @Override
    public Collection<? extends Item> items() {
        return list;
    }

    @Override
    protected void orderItems() {

    }
}
