
package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dex.SizeOf;
import mod.agus.jcoderz.dx.rop.cst.CstMemberRef;
import mod.agus.jcoderz.dx.rop.cst.CstNat;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public abstract class MemberIdItem extends IdItem {
    private final CstMemberRef cst;

    public MemberIdItem(CstMemberRef cst) {
        super(cst.getDefiningClass());

        this.cst = cst;
    }

    @Override
    public int writeSize() {
        return SizeOf.MEMBER_ID_ITEM;
    }

    @Override
    public void addContents(mod.agus.jcoderz.dx.dex.file.DexFile file) {
        super.addContents(file);

        mod.agus.jcoderz.dx.dex.file.StringIdsSection stringIds = file.getStringIds();
        stringIds.intern(getRef().getNat().getName());
    }

    @Override
    public final void writeTo(mod.agus.jcoderz.dx.dex.file.DexFile file, AnnotatedOutput out) {
        TypeIdsSection typeIds = file.getTypeIds();
        StringIdsSection stringIds = file.getStringIds();
        CstNat nat = cst.getNat();
        int classIdx = typeIds.indexOf(getDefiningClass());
        int nameIdx = stringIds.indexOf(nat.getName());
        int typoidIdx = getTypoidIdx(file);

        if (out.annotates()) {
            out.annotate(0, indexString() + ' ' + cst.toHuman());
            out.annotate(2, "  class_idx: " + Hex.u2(classIdx));
            out.annotate(2, String.format("  %-10s %s", getTypoidName() + ':',
                            Hex.u2(typoidIdx)));
            out.annotate(4, "  name_idx:  " + Hex.u4(nameIdx));
        }

        out.writeShort(classIdx);
        out.writeShort(typoidIdx);
        out.writeInt(nameIdx);
    }

    protected abstract int getTypoidIdx(DexFile file);

    protected abstract String getTypoidName();

    public final CstMemberRef getRef() {
        return cst;
    }
}
