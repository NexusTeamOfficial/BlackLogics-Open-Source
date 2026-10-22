
package mod.agus.jcoderz.dx.rop.cst;

public abstract class CstMemberRef extends TypedConstant {
    private final CstType definingClass;

    private final CstNat nat;

     CstMemberRef(CstType definingClass, CstNat nat) {
        if (definingClass == null) {
            throw new NullPointerException("definingClass == null");
        }

        if (nat == null) {
            throw new NullPointerException("nat == null");
        }

        this.definingClass = definingClass;
        this.nat = nat;
    }

    @Override
    public final boolean equals(Object other) {
        if ((other == null) || (getClass() != other.getClass())) {
            return false;
        }

        CstMemberRef otherRef = (CstMemberRef) other;
        return definingClass.equals(otherRef.definingClass) &&
            nat.equals(otherRef.nat);
    }

    @Override
    public final int hashCode() {
        return (definingClass.hashCode() * 31) ^ nat.hashCode();
    }

    @Override
    protected int compareTo0(Constant other) {
        CstMemberRef otherMember = (CstMemberRef) other;
        int cmp = definingClass.compareTo(otherMember.definingClass);

        if (cmp != 0) {
            return cmp;
        }

        CstString thisName = nat.getName();
        CstString otherName = otherMember.nat.getName();

        return thisName.compareTo(otherName);
    }

    @Override
    public final String toString() {
        return typeName() + '{' + toHuman() + '}';
    }

    @Override
    public final boolean isCategory2() {
        return false;
    }

    @Override
    public final String toHuman() {
        return definingClass.toHuman() + '.' + nat.toHuman();
    }

    public final CstType getDefiningClass() {
        return definingClass;
    }

    public final CstNat getNat() {
        return nat;
    }
}
