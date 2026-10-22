
package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dx.rop.cst.CstFieldRef;

public final class FieldIdItem extends MemberIdItem {
    public FieldIdItem(mod.agus.jcoderz.dx.rop.cst.CstFieldRef field) {
        super(field);
    }

    @Override
    public ItemType itemType() {
        return ItemType.TYPE_FIELD_ID_ITEM;
    }

    @Override
    public void addContents(DexFile file) {
        super.addContents(file);

        mod.agus.jcoderz.dx.dex.file.TypeIdsSection typeIds = file.getTypeIds();
        typeIds.intern(getFieldRef().getType());
    }

    public mod.agus.jcoderz.dx.rop.cst.CstFieldRef getFieldRef() {
        return (CstFieldRef) getRef();
    }

    @Override
    protected int getTypoidIdx(DexFile file) {
        TypeIdsSection typeIds = file.getTypeIds();
        return typeIds.indexOf(getFieldRef().getType());
    }

    @Override
    protected String getTypoidName() {
        return "type_idx";
    }
}
