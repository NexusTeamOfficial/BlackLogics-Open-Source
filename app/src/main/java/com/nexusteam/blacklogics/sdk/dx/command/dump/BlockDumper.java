
package mod.agus.jcoderz.dx.command.dump;

import mod.agus.jcoderz.dx.cf.code.BasicBlocker;
import mod.agus.jcoderz.dx.cf.code.ByteBlock;
import mod.agus.jcoderz.dx.cf.code.ByteBlockList;
import mod.agus.jcoderz.dx.cf.code.ByteCatchList;
import mod.agus.jcoderz.dx.cf.code.BytecodeArray;
import mod.agus.jcoderz.dx.cf.code.ConcreteMethod;
import mod.agus.jcoderz.dx.cf.code.Ropper;
import mod.agus.jcoderz.dx.cf.direct.CodeObserver;
import mod.agus.jcoderz.dx.cf.direct.DirectClassFile;
import mod.agus.jcoderz.dx.cf.direct.StdAttributeFactory;
import mod.agus.jcoderz.dx.cf.iface.Member;
import mod.agus.jcoderz.dx.cf.iface.Method;
import mod.agus.jcoderz.dx.ssa.Optimizer;
import mod.agus.jcoderz.dx.util.ByteArray;
import mod.agus.jcoderz.dx.util.Hex;
import mod.agus.jcoderz.dx.util.IntList;
import java.io.PrintStream;

import mod.agus.jcoderz.dx.rop.code.AccessFlags;
import mod.agus.jcoderz.dx.rop.code.BasicBlock;
import mod.agus.jcoderz.dx.rop.code.BasicBlockList;
import mod.agus.jcoderz.dx.rop.code.DexTranslationAdvice;
import mod.agus.jcoderz.dx.rop.code.Insn;
import mod.agus.jcoderz.dx.rop.code.InsnList;
import mod.agus.jcoderz.dx.rop.code.RopMethod;
import mod.agus.jcoderz.dx.rop.code.TranslationAdvice;
import mod.agus.jcoderz.dx.rop.cst.CstType;

public class BlockDumper
        extends BaseDumper {
    private final boolean rop;

    protected DirectClassFile classFile;

    protected boolean suppressDump;

    private boolean first;

    private final boolean optimize;

    public static void dump(byte[] bytes, PrintStream out,
            String filePath, boolean rop, Args args) {
        BlockDumper bd = new BlockDumper(bytes, out, filePath,
                rop, args);
        bd.dump();
    }

    BlockDumper(byte[] bytes, PrintStream out, String filePath,
            boolean rop, Args args) {
        super(bytes, out, filePath, args);

        this.rop = rop;
        this.classFile = null;
        this.suppressDump = true;
        this.first = true;
        this.optimize = args.optimize;
    }

    public void dump() {
        byte[] bytes = getBytes();
        ByteArray ba = new ByteArray(bytes);

        classFile = new DirectClassFile(ba, getFilePath(), getStrictParse());
        classFile.setAttributeFactory(StdAttributeFactory.THE_ONE);
        classFile.getMagic();


        DirectClassFile liveCf =
            new DirectClassFile(ba, getFilePath(), getStrictParse());
        liveCf.setAttributeFactory(StdAttributeFactory.THE_ONE);
        liveCf.setObserver(this);
        liveCf.getMagic();
    }

    @Override
    public void changeIndent(int indentDelta) {
        if (!suppressDump) {
            super.changeIndent(indentDelta);
        }
    }

    @Override
    public void parsed(ByteArray bytes, int offset, int len, String human) {
        if (!suppressDump) {
            super.parsed(bytes, offset, len, human);
        }
    }

    protected boolean shouldDumpMethod(String name) {
        return args.method == null || args.method.equals(name);
    }

    @Override
    public void startParsingMember(ByteArray bytes, int offset, String name,
            String descriptor) {
        if (descriptor.indexOf('(') < 0) {

            return;
        }

        if (!shouldDumpMethod(name)) {
            return;
        }

        suppressDump = false;

        if (first) {
            first = false;
        } else {
            parsed(bytes, offset, 0, "\n");
        }

        parsed(bytes, offset, 0, "method " + name + " " + descriptor);
        suppressDump = true;
    }

    @Override
    public void endParsingMember(ByteArray bytes, int offset, String name,
            String descriptor, Member member) {
        if (!(member instanceof Method)) {
            return;
        }

        if (!shouldDumpMethod(name)) {
            return;
        }

        if ((member.getAccessFlags() & (mod.agus.jcoderz.dx.rop.code.AccessFlags.ACC_ABSTRACT |
                mod.agus.jcoderz.dx.rop.code.AccessFlags.ACC_NATIVE)) != 0) {
            return;
        }

        ConcreteMethod meth =
            new ConcreteMethod((Method) member, classFile, true, true);

        if (rop) {
            ropDump(meth);
        } else {
            regularDump(meth);
        }
    }

    private void regularDump(ConcreteMethod meth) {
        BytecodeArray code = meth.getCode();
        ByteArray bytes = code.getBytes();
        ByteBlockList list = BasicBlocker.identifyBlocks(meth);
        int sz = list.size();
        CodeObserver codeObserver = new CodeObserver(bytes, BlockDumper.this);

        suppressDump = false;

        int byteAt = 0;
        for (int i = 0; i < sz; i++) {
            ByteBlock bb = list.get(i);
            int start = bb.getStart();
            int end = bb.getEnd();

            if (byteAt < start) {
                parsed(bytes, byteAt, start - byteAt,
                       "dead code " + Hex.u2(byteAt) + ".." + Hex.u2(start));
            }

            parsed(bytes, start, 0,
                    "block " + Hex.u2(bb.getLabel()) + ": " +
                    Hex.u2(start) + ".." + Hex.u2(end));
            changeIndent(1);

            int len;
            for (int j = start; j < end; j += len) {
                len = code.parseInstruction(j, codeObserver);
                codeObserver.setPreviousOffset(j);
            }

            IntList successors = bb.getSuccessors();
            int ssz = successors.size();
            if (ssz == 0) {
                parsed(bytes, end, 0, "returns");
            } else {
                for (int j = 0; j < ssz; j++) {
                    int succ = successors.get(j);
                    parsed(bytes, end, 0, "next " + Hex.u2(succ));
                }
            }

            ByteCatchList catches = bb.getCatches();
            int csz = catches.size();
            for (int j = 0; j < csz; j++) {
                ByteCatchList.Item one = catches.get(j);
                mod.agus.jcoderz.dx.rop.cst.CstType exceptionClass = one.getExceptionClass();
                parsed(bytes, end, 0,
                       "catch " +
                       ((exceptionClass == CstType.OBJECT) ? "<any>" :
                        exceptionClass.toHuman()) + " -> " +
                       Hex.u2(one.getHandlerPc()));
            }

            changeIndent(-1);
            byteAt = end;
        }

        int end = bytes.size();
        if (byteAt < end) {
            parsed(bytes, byteAt, end - byteAt,
                    "dead code " + Hex.u2(byteAt) + ".." + Hex.u2(end));
        }

        suppressDump = true;
    }

    private void ropDump(ConcreteMethod meth) {
        TranslationAdvice advice = DexTranslationAdvice.THE_ONE;
        BytecodeArray code = meth.getCode();
        ByteArray bytes = code.getBytes();
        RopMethod rmeth = Ropper.convert(meth, advice, classFile.getMethods(), dexOptions);
        StringBuilder sb = new StringBuilder(2000);

        if (optimize) {
            boolean isStatic = AccessFlags.isStatic(meth.getAccessFlags());
            int paramWidth = computeParamWidth(meth, isStatic);
            rmeth =
                Optimizer.optimize(rmeth, paramWidth, isStatic, true, advice);
        }

        BasicBlockList blocks = rmeth.getBlocks();
        int[] order = blocks.getLabelsInOrder();

        sb.append("first " + Hex.u2(rmeth.getFirstLabel()) + "\n");

        for (int label : order) {
            BasicBlock bb = blocks.get(blocks.indexOfLabel(label));
            sb.append("block ");
            sb.append(Hex.u2(label));
            sb.append("\n");

            IntList preds = rmeth.labelToPredecessors(label);
            int psz = preds.size();
            for (int i = 0; i < psz; i++) {
                sb.append("  pred ");
                sb.append(Hex.u2(preds.get(i)));
                sb.append("\n");
            }

            InsnList il = bb.getInsns();
            int ilsz = il.size();
            for (int i = 0; i < ilsz; i++) {
                Insn one = il.get(i);
                sb.append("  ");
                sb.append(il.get(i).toHuman());
                sb.append("\n");
            }

            IntList successors = bb.getSuccessors();
            int ssz = successors.size();
            if (ssz == 0) {
                sb.append("  returns\n");
            } else {
                int primary = bb.getPrimarySuccessor();
                for (int i = 0; i < ssz; i++) {
                    int succ = successors.get(i);
                    sb.append("  next ");
                    sb.append(Hex.u2(succ));

                    if ((ssz != 1) && (succ == primary)) {
                        sb.append(" *");
                    }

                    sb.append("\n");
                }
            }
        }

        suppressDump = false;
        parsed(bytes, 0, bytes.size(), sb.toString());
        suppressDump = true;
    }
}
