
package mod.agus.jcoderz.dx.ssa;

import mod.agus.jcoderz.dx.util.IntSet;
import java.util.ArrayList;
import java.util.BitSet;

public class DomFront {
    private static final boolean DEBUG = false;

    private final SsaMethod meth;

    private final ArrayList<SsaBasicBlock> nodes;

    private final DomInfo[] domInfos;

    public static class DomInfo {
        public IntSet dominanceFrontiers;

        public int idom = -1;
    }

    public DomFront(SsaMethod meth) {
        this.meth = meth;
        nodes = meth.getBlocks();

        int szNodes = nodes.size();
        domInfos = new DomInfo[szNodes];

        for (int i = 0; i < szNodes; i++) {
            domInfos[i] = new DomInfo();
        }
    }

    public DomInfo[] run() {
        int szNodes = nodes.size();

        if (DEBUG) {
            for (int i = 0; i < szNodes; i++) {
                SsaBasicBlock node = nodes.get(i);
                System.out.println("pred[" + i + "]: "
                        + node.getPredecessors());
            }
        }

        Dominators methDom = Dominators.make(meth, domInfos, false);

        if (DEBUG) {
            for (int i = 0; i < szNodes; i++) {
                DomInfo info = domInfos[i];
                System.out.println("idom[" + i + "]: "
                        + info.idom);
            }
        }

        buildDomTree();

        if (DEBUG) {
            debugPrintDomChildren();
        }

        for (int i = 0; i < szNodes; i++) {
            domInfos[i].dominanceFrontiers
                    = SetFactory.makeDomFrontSet(szNodes);
        }

        calcDomFronts();

        if (DEBUG) {
            for (int i = 0; i < szNodes; i++) {
                System.out.println("df[" + i + "]: "
                        + domInfos[i].dominanceFrontiers);
            }
        }

        return domInfos;
    }

    private void debugPrintDomChildren() {
        int szNodes = nodes.size();

        for (int i = 0; i < szNodes; i++) {
            SsaBasicBlock node = nodes.get(i);
            StringBuffer sb = new StringBuffer();

            sb.append('{');
            boolean comma = false;
            for (SsaBasicBlock child : node.getDomChildren()) {
                if (comma) {
                    sb.append(',');
                }
                sb.append(child);
                comma = true;
            }
            sb.append('}');

            System.out.println("domChildren[" + node + "]: "
                    + sb);
        }
    }

    private void buildDomTree() {
        int szNodes = nodes.size();

        for (int i = 0; i < szNodes; i++) {
            DomInfo info = domInfos[i];

            if (info.idom == -1) continue;

            SsaBasicBlock domParent = nodes.get(info.idom);
            domParent.addDomChild(nodes.get(i));
        }
    }

    private void calcDomFronts() {
        int szNodes = nodes.size();

        for (int b = 0; b < szNodes; b++) {
            SsaBasicBlock nb = nodes.get(b);
            DomInfo nbInfo = domInfos[b];
            BitSet pred = nb.getPredecessors();

            if (pred.cardinality() > 1) {
                for (int i = pred.nextSetBit(0); i >= 0;
                     i = pred.nextSetBit(i + 1)) {

                    for (int runnerIndex = i;
                         runnerIndex != nbInfo.idom; ) {
                        if (runnerIndex == -1) break;

                        DomInfo runnerInfo = domInfos[runnerIndex];

                        if (runnerInfo.dominanceFrontiers.has(b)) {
                            break;
                        }


                        runnerInfo.dominanceFrontiers.add(b);
                        runnerIndex = runnerInfo.idom;
                    }
                }
            }
        }
    }
}
