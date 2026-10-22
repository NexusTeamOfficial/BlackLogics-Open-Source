
package mod.agus.jcoderz.dx.dex.file;

import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

import mod.agus.jcoderz.dx.dex.code.CatchHandlerList;
import mod.agus.jcoderz.dx.dex.code.CatchTable;
import mod.agus.jcoderz.dx.dex.code.DalvCode;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.ByteArrayAnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public final class CatchStructs {
    private static final int TRY_ITEM_WRITE_SIZE = 4 + (2 * 2);

    private final mod.agus.jcoderz.dx.dex.code.DalvCode code;

    private mod.agus.jcoderz.dx.dex.code.CatchTable table;

    private byte[] encodedHandlers;

    private int encodedHandlerHeaderSize;

    private TreeMap<mod.agus.jcoderz.dx.dex.code.CatchHandlerList, Integer> handlerOffsets;

    public CatchStructs(DalvCode code) {
        this.code = code;
        this.table = null;
        this.encodedHandlers = null;
        this.encodedHandlerHeaderSize = 0;
        this.handlerOffsets = null;
    }

    private void finishProcessingIfNecessary() {
        if (table == null) {
            table = code.getCatches();
        }
    }

    public int triesSize() {
        finishProcessingIfNecessary();
        return table.size();
    }

    public void debugPrint(PrintWriter out, String prefix) {
        annotateEntries(prefix, out, null);
    }

    public void encode(DexFile file) {
        finishProcessingIfNecessary();

        TypeIdsSection typeIds = file.getTypeIds();
        int size = table.size();

        handlerOffsets = new TreeMap<mod.agus.jcoderz.dx.dex.code.CatchHandlerList, Integer>();

        for (int i = 0; i < size; i++) {
            handlerOffsets.put(table.get(i).getHandlers(), null);
        }

        if (handlerOffsets.size() > 65535) {
            throw new UnsupportedOperationException(
                    "too many catch handlers");
        }

        mod.agus.jcoderz.dx.util.ByteArrayAnnotatedOutput out = new ByteArrayAnnotatedOutput();


        encodedHandlerHeaderSize =
            out.writeUleb128(handlerOffsets.size());


        for (Map.Entry<mod.agus.jcoderz.dx.dex.code.CatchHandlerList, Integer> mapping :
                 handlerOffsets.entrySet()) {
            mod.agus.jcoderz.dx.dex.code.CatchHandlerList list = mapping.getKey();
            int listSize = list.size();
            boolean catchesAll = list.catchesAll();


            mapping.setValue(out.getCursor());

            if (catchesAll) {

                out.writeSleb128(-(listSize - 1));
                listSize--;
            } else {
                out.writeSleb128(listSize);
            }

            for (int i = 0; i < listSize; i++) {
                mod.agus.jcoderz.dx.dex.code.CatchHandlerList.Entry entry = list.get(i);
                out.writeUleb128(
                        typeIds.indexOf(entry.getExceptionType()));
                out.writeUleb128(entry.getHandler());
            }

            if (catchesAll) {
                out.writeUleb128(list.get(listSize).getHandler());
            }
        }

        encodedHandlers = out.toByteArray();
    }

    public int writeSize() {
        return (triesSize() * TRY_ITEM_WRITE_SIZE) +
                + encodedHandlers.length;
    }

    public void writeTo(DexFile file, mod.agus.jcoderz.dx.util.AnnotatedOutput out) {
        finishProcessingIfNecessary();

        if (out.annotates()) {
            annotateEntries("  ", null, out);
        }

        int tableSize = table.size();
        for (int i = 0; i < tableSize; i++) {
            mod.agus.jcoderz.dx.dex.code.CatchTable.Entry one = table.get(i);
            int start = one.getStart();
            int end = one.getEnd();
            int insnCount = end - start;

            if (insnCount >= 65536) {
                throw new UnsupportedOperationException(
                        "bogus exception range: " + mod.agus.jcoderz.dx.util.Hex.u4(start) + ".." +
                        mod.agus.jcoderz.dx.util.Hex.u4(end));
            }

            out.writeInt(start);
            out.writeShort(insnCount);
            out.writeShort(handlerOffsets.get(one.getHandlers()));
        }

        out.write(encodedHandlers);
    }

    private void annotateEntries(String prefix, PrintWriter printTo,
            mod.agus.jcoderz.dx.util.AnnotatedOutput annotateTo) {
        finishProcessingIfNecessary();

        boolean consume = (annotateTo != null);
        int amt1 = consume ? 6 : 0;
        int amt2 = consume ? 2 : 0;
        int size = table.size();
        String subPrefix = prefix + "  ";

        if (consume) {
            annotateTo.annotate(0, prefix + "tries:");
        } else {
            printTo.println(prefix + "tries:");
        }

        for (int i = 0; i < size; i++) {
            CatchTable.Entry entry = table.get(i);
            mod.agus.jcoderz.dx.dex.code.CatchHandlerList handlers = entry.getHandlers();
            String s1 = subPrefix + "try " + mod.agus.jcoderz.dx.util.Hex.u2or4(entry.getStart())
                + ".." + mod.agus.jcoderz.dx.util.Hex.u2or4(entry.getEnd());
            String s2 = handlers.toHuman(subPrefix, "");

            if (consume) {
                annotateTo.annotate(amt1, s1);
                annotateTo.annotate(amt2, s2);
            } else {
                printTo.println(s1);
                printTo.println(s2);
            }
        }

        if (! consume) {

            return;
        }

        annotateTo.annotate(0, prefix + "handlers:");
        annotateTo.annotate(encodedHandlerHeaderSize,
                subPrefix + "size: " + mod.agus.jcoderz.dx.util.Hex.u2(handlerOffsets.size()));

        int lastOffset = 0;
        mod.agus.jcoderz.dx.dex.code.CatchHandlerList lastList = null;

        for (Map.Entry<mod.agus.jcoderz.dx.dex.code.CatchHandlerList, Integer> mapping :
                 handlerOffsets.entrySet()) {
            mod.agus.jcoderz.dx.dex.code.CatchHandlerList list = mapping.getKey();
            int offset = mapping.getValue();

            if (lastList != null) {
                annotateAndConsumeHandlers(lastList, lastOffset,
                        offset - lastOffset, subPrefix, printTo, annotateTo);
            }

            lastList = list;
            lastOffset = offset;
        }

        annotateAndConsumeHandlers(lastList, lastOffset,
                encodedHandlers.length - lastOffset,
                subPrefix, printTo, annotateTo);
    }

    private static void annotateAndConsumeHandlers(CatchHandlerList handlers,
                                                   int offset, int size, String prefix, PrintWriter printTo,
                                                   AnnotatedOutput annotateTo) {
        String s = handlers.toHuman(prefix, Hex.u2(offset) + ": ");

        if (printTo != null) {
            printTo.println(s);
        }

        annotateTo.annotate(size, s);
    }
}
