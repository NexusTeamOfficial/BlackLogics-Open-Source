
package mod.agus.jcoderz.dx.rop.cst;

public interface ConstantPool {
    public int size();

    public Constant get(int n);

    public Constant get0Ok(int n);

    public Constant getOrNull(int n);

    public Constant[] getEntries();
}
