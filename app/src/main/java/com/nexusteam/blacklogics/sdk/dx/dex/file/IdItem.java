
package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dx.rop.cst.CstType;

public abstract class IdItem extends IndexedItem {
    private final mod.agus.jcoderz.dx.rop.cst.CstType type;

    public IdItem(mod.agus.jcoderz.dx.rop.cst.CstType type) {
        if (type == null) {
            throw new NullPointerException("type == null");
        }

        this.type = type;
    }

    @Override
    public void addContents(DexFile file) {
        TypeIdsSection typeIds = file.getTypeIds();
        typeIds.intern(type);
    }

    public final CstType getDefiningClass() {
        return type;
    }
}
