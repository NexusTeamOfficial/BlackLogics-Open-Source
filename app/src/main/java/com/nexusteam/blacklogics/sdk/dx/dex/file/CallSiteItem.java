package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dx.rop.cst.CstCallSite;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.ByteArrayAnnotatedOutput;

public final class CallSiteItem extends OffsettedItem {

    private final mod.agus.jcoderz.dx.rop.cst.CstCallSite value;

    private byte[] encodedForm;

    public CallSiteItem(mod.agus.jcoderz.dx.rop.cst.CstCallSite value) {
        super(1, writeSize(value));

        this.value = value;
    }

    private static int writeSize(CstCallSite value) {
        return -1;
    }

    @Override
    protected void place0(Section addedTo, int offset) {


        mod.agus.jcoderz.dx.util.ByteArrayAnnotatedOutput out = new ByteArrayAnnotatedOutput();
        ValueEncoder encoder = new ValueEncoder(addedTo.getFile(), out);

        encoder.writeArray(value, true);
        encodedForm = out.toByteArray();
        setWriteSize(encodedForm.length);
    }

    @Override
    public String toHuman() {
        return value.toHuman();
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    protected void writeTo0(DexFile file, AnnotatedOutput out) {
        if (out.annotates()) {
            out.annotate(0, offsetString() + " call site");
            ValueEncoder encoder = new ValueEncoder(file, out);
            encoder.writeArray(value, true);
        } else {
            out.write(encodedForm);
        }
    }

    @Override
    public ItemType itemType() {



        return ItemType.TYPE_ENCODED_ARRAY_ITEM;
    }

    @Override
    public void addContents(DexFile file) {
        ValueEncoder.addContents(file, value);
    }
}
