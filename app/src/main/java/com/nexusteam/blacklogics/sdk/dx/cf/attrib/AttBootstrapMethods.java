package mod.agus.jcoderz.dx.cf.attrib;

import mod.agus.jcoderz.dx.cf.code.BootstrapMethodsList;

public class AttBootstrapMethods extends BaseAttribute {
    public static final String ATTRIBUTE_NAME = "BootstrapMethods";

    private static final int ATTRIBUTE_HEADER_BYTES = 8;
    private static final int BOOTSTRAP_METHOD_BYTES = 4;
    private static final int BOOTSTRAP_ARGUMENT_BYTES = 2;

    private final mod.agus.jcoderz.dx.cf.code.BootstrapMethodsList bootstrapMethods;

    private final int byteLength;

    public AttBootstrapMethods(mod.agus.jcoderz.dx.cf.code.BootstrapMethodsList bootstrapMethods) {
        super(ATTRIBUTE_NAME);
        this.bootstrapMethods = bootstrapMethods;

        int bytes = ATTRIBUTE_HEADER_BYTES + bootstrapMethods.size() * BOOTSTRAP_METHOD_BYTES;
        for (int i = 0; i < bootstrapMethods.size(); ++i) {
            int numberOfArguments = bootstrapMethods.get(i).getBootstrapMethodArguments().size();
            bytes += numberOfArguments * BOOTSTRAP_ARGUMENT_BYTES;
        }
        this.byteLength = bytes;
    }

    @Override
    public int byteLength() {
        return byteLength;
    }

    public BootstrapMethodsList getBootstrapMethods() {
        return bootstrapMethods;
    }
}
