
package mod.agus.jcoderz.dx.io.instructions;

public final class SparseSwitchPayloadDecodedInstruction
        extends mod.agus.jcoderz.dx.io.instructions.DecodedInstruction {
    private final int[] keys;

    private final int[] targets;

    public SparseSwitchPayloadDecodedInstruction(InstructionCodec format,
                                                 int opcode, int[] keys, int[] targets) {
        super(format, opcode, 0, null, 0, 0L);

        if (keys.length != targets.length) {
            throw new IllegalArgumentException("keys/targets length mismatch");
        }

        this.keys = keys;
        this.targets = targets;
    }

    @Override
    public int getRegisterCount() {
        return 0;
    }

    public int[] getKeys() {
        return keys;
    }

    public int[] getTargets() {
        return targets;
    }

    @Override
    public DecodedInstruction withIndex(int newIndex) {
        throw new UnsupportedOperationException("no index in instruction");
    }
}
