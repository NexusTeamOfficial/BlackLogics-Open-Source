
package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dex.DexFormat;
import mod.agus.jcoderz.dex.SizeOf;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public final class HeaderItem extends IndexedItem {
    public HeaderItem() {

    }

    @Override
    public ItemType itemType() {
        return ItemType.TYPE_HEADER_ITEM;
    }

    @Override
    public int writeSize() {
        return SizeOf.HEADER_ITEM;
    }

    @Override
    public void addContents(DexFile file) {

    }

    @Override
    public void writeTo(DexFile file, AnnotatedOutput out) {
        int mapOff = file.getMap().getFileOffset();
        Section firstDataSection = file.getFirstDataSection();
        Section lastDataSection = file.getLastDataSection();
        int dataOff = firstDataSection.getFileOffset();
        int dataSize = lastDataSection.getFileOffset() +
            lastDataSection.writeSize() - dataOff;

        String magic = file.getDexOptions().getMagic();

        if (out.annotates()) {
            out.annotate(8, "magic: " + new CstString(magic).toQuoted());
            out.annotate(4, "checksum");
            out.annotate(20, "signature");
            out.annotate(4, "file_size:       " +
                         mod.agus.jcoderz.dx.util.Hex.u4(file.getFileSize()));
            out.annotate(4, "header_size:     " + mod.agus.jcoderz.dx.util.Hex.u4(SizeOf.HEADER_ITEM));
            out.annotate(4, "endian_tag:      " + mod.agus.jcoderz.dx.util.Hex.u4(DexFormat.ENDIAN_TAG));
            out.annotate(4, "link_size:       0");
            out.annotate(4, "link_off:        0");
            out.annotate(4, "map_off:         " + mod.agus.jcoderz.dx.util.Hex.u4(mapOff));
        }


        for (int i = 0; i < 8; i++) {
            out.writeByte(magic.charAt(i));
        }


        out.writeZeroes(24);

        out.writeInt(file.getFileSize());
        out.writeInt(SizeOf.HEADER_ITEM);
        out.writeInt(DexFormat.ENDIAN_TAG);

        out.writeZeroes(8);

        out.writeInt(mapOff);


        file.getStringIds().writeHeaderPart(out);
        file.getTypeIds().writeHeaderPart(out);
        file.getProtoIds().writeHeaderPart(out);
        file.getFieldIds().writeHeaderPart(out);
        file.getMethodIds().writeHeaderPart(out);
        file.getClassDefs().writeHeaderPart(out);

        if (out.annotates()) {
            out.annotate(4, "data_size:       " + mod.agus.jcoderz.dx.util.Hex.u4(dataSize));
            out.annotate(4, "data_off:        " + Hex.u4(dataOff));
        }

        out.writeInt(dataSize);
        out.writeInt(dataOff);
    }
}
