
package mod.agus.jcoderz.dx.merge;

import mod.agus.jcoderz.dex.ClassDef;
import mod.agus.jcoderz.dex.Dex;
import mod.agus.jcoderz.dex.DexException;
import java.util.Comparator;

final class SortableType {
    public static final Comparator<SortableType> NULLS_LAST_ORDER = new Comparator<SortableType>() {
        @Override
        public int compare(SortableType a, SortableType b) {
            if (a == b) {
                return 0;
            }
            if (b == null) {
                return -1;
            }
            if (a == null) {
                return 1;
            }
            if (a.depth != b.depth) {
                return a.depth - b.depth;
            }
            return a.getTypeIndex() - b.getTypeIndex();
        }
    };

    private final Dex dex;
    private final mod.agus.jcoderz.dx.merge.IndexMap indexMap;
    private final ClassDef classDef;
    private int depth = -1;

    public SortableType(Dex dex, mod.agus.jcoderz.dx.merge.IndexMap indexMap, ClassDef classDef) {
        this.dex = dex;
        this.indexMap = indexMap;
        this.classDef = classDef;
    }

    public Dex getDex() {
        return dex;
    }

    public IndexMap getIndexMap() {
        return indexMap;
    }

    public ClassDef getClassDef() {
        return classDef;
    }

    public int getTypeIndex() {
        return classDef.getTypeIndex();
    }

    public boolean tryAssignDepth(SortableType[] types) {
        int max;
        if (classDef.getSupertypeIndex() == ClassDef.NO_INDEX) {
            max = 0;
        } else if (classDef.getSupertypeIndex() == classDef.getTypeIndex()) {

            throw new DexException("Class with type index " + classDef.getTypeIndex()
                    + " extends itself");
        } else {
            SortableType sortableSupertype = types[classDef.getSupertypeIndex()];
            if (sortableSupertype == null) {
                max = 1;
            } else if (sortableSupertype.depth == -1) {
                return false;
            } else {
                max = sortableSupertype.depth;
            }
        }

        for (short interfaceIndex : classDef.getInterfaces()) {
            SortableType implemented = types[interfaceIndex];
            if (implemented == null) {
                max = Math.max(max, 1);
            } else if (implemented.depth == -1) {
                return false;
            } else {
                max = Math.max(max, implemented.depth);
            }
        }

        depth = max + 1;
        return true;
    }

    public boolean isDepthAssigned() {
        return depth != -1;
    }
}
