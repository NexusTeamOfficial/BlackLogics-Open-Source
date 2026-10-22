
package mod.agus.jcoderz.dx.ssa;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;

public final class Dominators {
    private final boolean postdom;

    private final mod.agus.jcoderz.dx.ssa.SsaMethod meth;

    private final ArrayList<mod.agus.jcoderz.dx.ssa.SsaBasicBlock> blocks;

    private final DFSInfo[] info;

    private final ArrayList<mod.agus.jcoderz.dx.ssa.SsaBasicBlock> vertex;

    private final mod.agus.jcoderz.dx.ssa.DomFront.DomInfo domInfos[];

    private Dominators(mod.agus.jcoderz.dx.ssa.SsaMethod meth, mod.agus.jcoderz.dx.ssa.DomFront.DomInfo[] domInfos,
                       boolean postdom) {
        this.meth = meth;
        this.domInfos = domInfos;
        this.postdom = postdom;
        this.blocks = meth.getBlocks();
        this.info = new DFSInfo[blocks.size() + 2];
        this.vertex = new ArrayList<mod.agus.jcoderz.dx.ssa.SsaBasicBlock>();
    }

    public static Dominators make(SsaMethod meth, DomFront.DomInfo[] domInfos,
                                  boolean postdom) {
        Dominators result = new Dominators(meth, domInfos, postdom);

        result.run();
        return result;
    }

    private BitSet getSuccs(mod.agus.jcoderz.dx.ssa.SsaBasicBlock block) {
        if (postdom) {
            return block.getPredecessors();
        } else {
            return block.getSuccessors();
        }
    }

    private BitSet getPreds(mod.agus.jcoderz.dx.ssa.SsaBasicBlock block) {
        if (postdom) {
            return block.getSuccessors();
        } else {
            return block.getPredecessors();
        }
    }

    private void compress(mod.agus.jcoderz.dx.ssa.SsaBasicBlock in) {
        DFSInfo bbInfo = info[in.getIndex()];
        DFSInfo ancestorbbInfo = info[bbInfo.ancestor.getIndex()];

        if (ancestorbbInfo.ancestor != null) {
            ArrayList<mod.agus.jcoderz.dx.ssa.SsaBasicBlock> worklist = new ArrayList<mod.agus.jcoderz.dx.ssa.SsaBasicBlock>();
            HashSet<mod.agus.jcoderz.dx.ssa.SsaBasicBlock> visited = new HashSet<mod.agus.jcoderz.dx.ssa.SsaBasicBlock>();
            worklist.add(in);

            while (!worklist.isEmpty()) {
                int wsize = worklist.size();
                mod.agus.jcoderz.dx.ssa.SsaBasicBlock v = worklist.get(wsize - 1);
                DFSInfo vbbInfo = info[v.getIndex()];
                mod.agus.jcoderz.dx.ssa.SsaBasicBlock vAncestor = vbbInfo.ancestor;
                DFSInfo vabbInfo = info[vAncestor.getIndex()];


                if (visited.add(vAncestor) && vabbInfo.ancestor != null) {
                    worklist.add(vAncestor);
                    continue;
                }
                worklist.remove(wsize - 1);


                if (vabbInfo.ancestor == null) {
                    continue;
                }
                mod.agus.jcoderz.dx.ssa.SsaBasicBlock vAncestorRep = vabbInfo.rep;
                mod.agus.jcoderz.dx.ssa.SsaBasicBlock vRep = vbbInfo.rep;
                if (info[vAncestorRep.getIndex()].semidom
                        < info[vRep.getIndex()].semidom) {
                    vbbInfo.rep = vAncestorRep;
                }
                vbbInfo.ancestor = vabbInfo.ancestor;
            }
        }
    }

    private mod.agus.jcoderz.dx.ssa.SsaBasicBlock eval(mod.agus.jcoderz.dx.ssa.SsaBasicBlock v) {
        DFSInfo bbInfo = info[v.getIndex()];

        if (bbInfo.ancestor == null) {
            return v;
        }

        compress(v);
        return bbInfo.rep;
    }

    private void run() {
        mod.agus.jcoderz.dx.ssa.SsaBasicBlock root = postdom
                ? meth.getExitBlock() : meth.getEntryBlock();

        if (root != null) {
            vertex.add(root);
            domInfos[root.getIndex()].idom = root.getIndex();
        }


        DfsWalker walker = new DfsWalker();
        meth.forEachBlockDepthFirst(postdom, walker);


        int dfsMax = vertex.size() - 1;


        for (int i = dfsMax; i >= 2; --i) {
            mod.agus.jcoderz.dx.ssa.SsaBasicBlock w = vertex.get(i);
            DFSInfo wInfo = info[w.getIndex()];

            BitSet preds = getPreds(w);
            for (int j = preds.nextSetBit(0);
                 j >= 0;
                 j = preds.nextSetBit(j + 1)) {
                mod.agus.jcoderz.dx.ssa.SsaBasicBlock predBlock = blocks.get(j);
                DFSInfo predInfo = info[predBlock.getIndex()];

                if (predInfo != null) {
                    int predSemidom = info[eval(predBlock).getIndex()].semidom;
                    if (predSemidom < wInfo.semidom) {
                        wInfo.semidom = predSemidom;
                    }
                }
            }
            info[vertex.get(wInfo.semidom).getIndex()].bucket.add(w);

            wInfo.ancestor = wInfo.parent;


            ArrayList<mod.agus.jcoderz.dx.ssa.SsaBasicBlock> wParentBucket;
            wParentBucket = info[wInfo.parent.getIndex()].bucket;

            while (!wParentBucket.isEmpty()) {
                int lastItem = wParentBucket.size() - 1;
                mod.agus.jcoderz.dx.ssa.SsaBasicBlock last = wParentBucket.remove(lastItem);
                mod.agus.jcoderz.dx.ssa.SsaBasicBlock U = eval(last);
                if (info[U.getIndex()].semidom
                        < info[last.getIndex()].semidom) {
                    domInfos[last.getIndex()].idom = U.getIndex();
                } else {
                    domInfos[last.getIndex()].idom = wInfo.parent.getIndex();
                }
            }
        }


        for (int i =  2; i <= dfsMax; ++i) {
            mod.agus.jcoderz.dx.ssa.SsaBasicBlock w = vertex.get(i);
            if (domInfos[w.getIndex()].idom
                    != vertex.get(info[w.getIndex()].semidom).getIndex()) {
                domInfos[w.getIndex()].idom
                        = domInfos[domInfos[w.getIndex()].idom].idom;
            }
        }
    }

    private class DfsWalker implements mod.agus.jcoderz.dx.ssa.SsaBasicBlock.Visitor {
        private int dfsNum = 0;

        @Override
        public void visitBlock(mod.agus.jcoderz.dx.ssa.SsaBasicBlock v, mod.agus.jcoderz.dx.ssa.SsaBasicBlock parent) {
            DFSInfo bbInfo = new DFSInfo();
            bbInfo.semidom = ++dfsNum;
            bbInfo.rep = v;
            bbInfo.parent = parent;
            vertex.add(v);
            info[v.getIndex()] = bbInfo;
        }
    }

    private static final class DFSInfo {
        public int semidom;
        public mod.agus.jcoderz.dx.ssa.SsaBasicBlock parent;

        public mod.agus.jcoderz.dx.ssa.SsaBasicBlock rep;

        public mod.agus.jcoderz.dx.ssa.SsaBasicBlock ancestor;
        public ArrayList<mod.agus.jcoderz.dx.ssa.SsaBasicBlock> bucket;

        public DFSInfo() {
            bucket = new ArrayList<SsaBasicBlock>();
        }
    }
}
