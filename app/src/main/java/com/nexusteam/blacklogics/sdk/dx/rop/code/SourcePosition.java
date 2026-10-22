
package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.util.Hex;
import mod.agus.jcoderz.dx.rop.cst.CstString;

public final class SourcePosition {
    public static final SourcePosition NO_INFO =
        new SourcePosition(null, -1, -1);

    private final mod.agus.jcoderz.dx.rop.cst.CstString sourceFile;

    private final int address;

    private final int line;

    public SourcePosition(mod.agus.jcoderz.dx.rop.cst.CstString sourceFile, int address, int line) {
        if (address < -1) {
            throw new IllegalArgumentException("address < -1");
        }

        if (line < -1) {
            throw new IllegalArgumentException("line < -1");
        }

        this.sourceFile = sourceFile;
        this.address = address;
        this.line = line;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(50);

        if (sourceFile != null) {
            sb.append(sourceFile.toHuman());
            sb.append(":");
        }

        if (line >= 0) {
            sb.append(line);
        }

        sb.append('@');

        if (address < 0) {
            sb.append("????");
        } else {
            sb.append(Hex.u2(address));
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof SourcePosition)) {
            return false;
        }

        if (this == other) {
            return true;
        }

        SourcePosition pos = (SourcePosition) other;

        return (address == pos.address) && sameLineAndFile(pos);
    }

    @Override
    public int hashCode() {
        return sourceFile.hashCode() + address + line;
    }

    public boolean sameLine(SourcePosition other) {
        return (line == other.line);
    }

    public boolean sameLineAndFile(SourcePosition other) {
        return (line == other.line) &&
            ((sourceFile == other.sourceFile) ||
             ((sourceFile != null) && sourceFile.equals(other.sourceFile)));
    }

    public CstString getSourceFile() {
        return sourceFile;
    }

    public int getAddress() {
        return address;
    }

    public int getLine() {
        return line;
    }
}
