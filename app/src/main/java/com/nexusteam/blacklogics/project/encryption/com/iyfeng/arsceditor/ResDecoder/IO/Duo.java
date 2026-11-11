
package com.iyfeng.arsceditor.ResDecoder.IO;

public class Duo<T1, T2> {
    public final T1 m1;
    public final T2 m2;

    public Duo(T1 t1, T2 t2) {
        this.m1 = t1;
        this.m2 = t2;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        @SuppressWarnings("unchecked")
        final Duo<T1, T2> other = (Duo<T1, T2>) obj;
        if (this.m1 != other.m1 && (this.m1 == null || !this.m1.equals(other.m1))) {
            return false;
        }
        if (this.m2 != other.m2 && (this.m2 == null || !this.m2.equals(other.m2))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + (this.m1 != null ? this.m1.hashCode() : 0);
        hash = 71 * hash + (this.m2 != null ? this.m2.hashCode() : 0);
        return hash;
    }
}
