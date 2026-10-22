
package mod.agus.jcoderz.dx.rop.type;

public interface TypeList {
    public boolean isMutable();

    public int size();

    public mod.agus.jcoderz.dx.rop.type.Type getType(int n);

    public int getWordCount();

    public TypeList withAddedType(Type type);
}
