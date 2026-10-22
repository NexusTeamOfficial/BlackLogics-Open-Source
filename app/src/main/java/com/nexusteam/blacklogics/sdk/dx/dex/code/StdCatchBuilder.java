
package mod.agus.jcoderz.dx.dex.code;

import java.util.ArrayList;
import java.util.HashSet;

import mod.agus.jcoderz.dx.rop.code.BasicBlock;
import mod.agus.jcoderz.dx.rop.code.BasicBlockList;
import mod.agus.jcoderz.dx.rop.code.RopMethod;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeList;
import mod.agus.jcoderz.dx.util.IntList;

public final class StdCatchBuilder implements CatchBuilder {
    private static final int MAX_CATCH_RANGE = 65535;

    private final mod.agus.jcoderz.dx.rop.code.RopMethod method;

    private final int[] order;

    private final mod.agus.jcoderz.dx.dex.code.BlockAddresses addresses;

    public StdCatchBuilder(mod.agus.jcoderz.dx.rop.code.RopMethod method, int[] order,
                           mod.agus.jcoderz.dx.dex.code.BlockAddresses addresses) {
        if (method == null) {
            throw new NullPointerException("method == null");
        }

        if (order == null) {
            throw new NullPointerException("order == null");
        }

        if (addresses == null) {
            throw new NullPointerException("addresses == null");
        }

        this.method = method;
        this.order = order;
        this.addresses = addresses;
    }

    @Override
    public mod.agus.jcoderz.dx.dex.code.CatchTable build() {
        return build(method, order, addresses);
    }

    @Override
    public boolean hasAnyCatches() {
        mod.agus.jcoderz.dx.rop.code.BasicBlockList blocks = method.getBlocks();
        int size = blocks.size();

        for (int i = 0; i < size; i++) {
            mod.agus.jcoderz.dx.rop.code.BasicBlock block = blocks.get(i);
            mod.agus.jcoderz.dx.rop.type.TypeList catches = block.getLastInsn().getCatches();
            if (catches.size() != 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public HashSet<mod.agus.jcoderz.dx.rop.type.Type> getCatchTypes() {
        HashSet<mod.agus.jcoderz.dx.rop.type.Type> result = new HashSet<mod.agus.jcoderz.dx.rop.type.Type>(20);
        mod.agus.jcoderz.dx.rop.code.BasicBlockList blocks = method.getBlocks();
        int size = blocks.size();

        for (int i = 0; i < size; i++) {
            mod.agus.jcoderz.dx.rop.code.BasicBlock block = blocks.get(i);
            mod.agus.jcoderz.dx.rop.type.TypeList catches = block.getLastInsn().getCatches();
            int catchSize = catches.size();

            for (int j = 0; j < catchSize; j++) {
                result.add(catches.getType(j));
            }
        }

        return result;
    }

    public static mod.agus.jcoderz.dx.dex.code.CatchTable build(RopMethod method, int[] order,
                                                                mod.agus.jcoderz.dx.dex.code.BlockAddresses addresses) {
        int len = order.length;
        BasicBlockList blocks = method.getBlocks();
        ArrayList<mod.agus.jcoderz.dx.dex.code.CatchTable.Entry> resultList =
            new ArrayList<mod.agus.jcoderz.dx.dex.code.CatchTable.Entry>(len);
        mod.agus.jcoderz.dx.dex.code.CatchHandlerList currentHandlers = mod.agus.jcoderz.dx.dex.code.CatchHandlerList.EMPTY;
        mod.agus.jcoderz.dx.rop.code.BasicBlock currentStartBlock = null;
        mod.agus.jcoderz.dx.rop.code.BasicBlock currentEndBlock = null;

        for (int i = 0; i < len; i++) {
            mod.agus.jcoderz.dx.rop.code.BasicBlock block = blocks.labelToBlock(order[i]);

            if (!block.canThrow()) {
                continue;
            }

            mod.agus.jcoderz.dx.dex.code.CatchHandlerList handlers = handlersFor(block, addresses);

            if (currentHandlers.size() == 0) {

                currentStartBlock = block;
                currentEndBlock = block;
                currentHandlers = handlers;
                continue;
            }

            if (currentHandlers.equals(handlers)
                    && rangeIsValid(currentStartBlock, block, addresses)) {
                currentEndBlock = block;
                continue;
            }

            if (currentHandlers.size() != 0) {
                mod.agus.jcoderz.dx.dex.code.CatchTable.Entry entry =
                    makeEntry(currentStartBlock, currentEndBlock,
                            currentHandlers, addresses);
                resultList.add(entry);
            }

            currentStartBlock = block;
            currentEndBlock = block;
            currentHandlers = handlers;
        }

        if (currentHandlers.size() != 0) {

            mod.agus.jcoderz.dx.dex.code.CatchTable.Entry entry =
                makeEntry(currentStartBlock, currentEndBlock,
                        currentHandlers, addresses);
            resultList.add(entry);
        }



        int resultSz = resultList.size();

        if (resultSz == 0) {
            return mod.agus.jcoderz.dx.dex.code.CatchTable.EMPTY;
        }

        mod.agus.jcoderz.dx.dex.code.CatchTable result = new mod.agus.jcoderz.dx.dex.code.CatchTable(resultSz);

        for (int i = 0; i < resultSz; i++) {
            result.set(i, resultList.get(i));
        }

        result.setImmutable();
        return result;
    }

    private static mod.agus.jcoderz.dx.dex.code.CatchHandlerList handlersFor(mod.agus.jcoderz.dx.rop.code.BasicBlock block,
                                                                             mod.agus.jcoderz.dx.dex.code.BlockAddresses addresses) {
        IntList successors = block.getSuccessors();
        int succSize = successors.size();
        int primary = block.getPrimarySuccessor();
        TypeList catches = block.getLastInsn().getCatches();
        int catchSize = catches.size();

        if (catchSize == 0) {
            return mod.agus.jcoderz.dx.dex.code.CatchHandlerList.EMPTY;
        }

        if (((primary == -1) && (succSize != catchSize))
                || ((primary != -1) &&
                        ((succSize != (catchSize + 1))
                                || (primary != successors.get(catchSize))))) {
            throw new RuntimeException(
                    "shouldn't happen: weird successors list");
        }

        for (int i = 0; i < catchSize; i++) {
            mod.agus.jcoderz.dx.rop.type.Type type = catches.getType(i);
            if (type.equals(Type.OBJECT)) {
                catchSize = i + 1;
                break;
            }
        }

        mod.agus.jcoderz.dx.dex.code.CatchHandlerList result = new mod.agus.jcoderz.dx.dex.code.CatchHandlerList(catchSize);

        for (int i = 0; i < catchSize; i++) {
            mod.agus.jcoderz.dx.rop.cst.CstType oneType = new CstType(catches.getType(i));
            mod.agus.jcoderz.dx.dex.code.CodeAddress oneHandler = addresses.getStart(successors.get(i));
            result.set(i, oneType, oneHandler.getAddress());
        }

        result.setImmutable();
        return result;
    }

    private static mod.agus.jcoderz.dx.dex.code.CatchTable.Entry makeEntry(mod.agus.jcoderz.dx.rop.code.BasicBlock start,
                                                                           mod.agus.jcoderz.dx.rop.code.BasicBlock end, CatchHandlerList handlers,
                                                                           mod.agus.jcoderz.dx.dex.code.BlockAddresses addresses) {
        mod.agus.jcoderz.dx.dex.code.CodeAddress startAddress = addresses.getLast(start);


        CodeAddress endAddress = addresses.getEnd(end);

        return new CatchTable.Entry(startAddress.getAddress(),
                endAddress.getAddress(), handlers);
    }

    private static boolean rangeIsValid(mod.agus.jcoderz.dx.rop.code.BasicBlock start, BasicBlock end,
                                        BlockAddresses addresses) {
        if (start == null) {
            throw new NullPointerException("start == null");
        }

        if (end == null) {
            throw new NullPointerException("end == null");
        }


        int startAddress = addresses.getLast(start).getAddress();
        int endAddress = addresses.getEnd(end).getAddress();

        return (endAddress - startAddress) <= MAX_CATCH_RANGE;
    }
}
