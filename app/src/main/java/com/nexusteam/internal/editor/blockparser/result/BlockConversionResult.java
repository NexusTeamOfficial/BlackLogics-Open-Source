package com.nexusteam.internal.editor.blockparser.result;

import com.nexusteam.internal.editor.blockparser.model.ParsedBlock;

import java.util.ArrayList;
import java.util.List;

public class BlockConversionResult {

    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_PARTIAL = 2;
    public static final int STATUS_FAILED = 3;
    public static final int STATUS_EMPTY = 4;

    private int status;
    private String message;
    private List rootBlocks;
    private List unmatchedSourceBlocks;
    private int totalBlocksGenerated;
    private int matchedBlocks;
    private int sourceBlocks;
    private long conversionTimeMs;
    private String originalCode;

    public BlockConversionResult() {
        this.rootBlocks = new ArrayList();
        this.unmatchedSourceBlocks = new ArrayList();
        this.status = STATUS_FAILED;
        this.message = "";
        this.totalBlocksGenerated = 0;
        this.matchedBlocks = 0;
        this.sourceBlocks = 0;
        this.conversionTimeMs = 0;
    }

    public static BlockConversionResult success(List blocks) {
        BlockConversionResult r = new BlockConversionResult();
        r.status = STATUS_SUCCESS;
        r.rootBlocks = blocks == null ? new ArrayList() : blocks;
        r.message = "Conversion successful";
        r.calculateStats();
        return r;
    }

    public static BlockConversionResult partial(List blocks, String message) {
        BlockConversionResult r = new BlockConversionResult();
        r.status = STATUS_PARTIAL;
        r.rootBlocks = blocks == null ? new ArrayList() : blocks;
        r.message = message == null ? "Partial conversion" : message;
        r.calculateStats();
        return r;
    }

    public static BlockConversionResult failed(String message) {
        BlockConversionResult r = new BlockConversionResult();
        r.status = STATUS_FAILED;
        r.message = message == null ? "Conversion failed" : message;
        return r;
    }

    public static BlockConversionResult empty() {
        BlockConversionResult r = new BlockConversionResult();
        r.status = STATUS_EMPTY;
        r.message = "No code provided";
        return r;
    }

    private void calculateStats() {
        totalBlocksGenerated = 0;
        matchedBlocks = 0;
        sourceBlocks = 0;
        unmatchedSourceBlocks.clear();
        countBlocks(rootBlocks);
    }

    private void countBlocks(List blocks) {
        if (blocks == null) {
            return;
        }
        for (int i = 0; i < blocks.size(); i++) {
            ParsedBlock block = (ParsedBlock) blocks.get(i);
            totalBlocksGenerated++;
            if (block.isMatched()) {
                matchedBlocks++;
            } else if (block.isSourceBlock()) {
                sourceBlocks++;
                unmatchedSourceBlocks.add(block);
            }
            countBlocks(block.getChildren());
            countBlocks(block.getNextBlocks());
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List getRootBlocks() {
        return rootBlocks;
    }

    public void setRootBlocks(List rootBlocks) {
        this.rootBlocks = rootBlocks == null ? new ArrayList() : rootBlocks;
        calculateStats();
    }

    public List getUnmatchedSourceBlocks() {
        return unmatchedSourceBlocks;
    }

    public int getTotalBlocksGenerated() {
        return totalBlocksGenerated;
    }

    public int getMatchedBlocks() {
        return matchedBlocks;
    }

    public int getSourceBlocks() {
        return sourceBlocks;
    }

    public long getConversionTimeMs() {
        return conversionTimeMs;
    }

    public void setConversionTimeMs(long conversionTimeMs) {
        this.conversionTimeMs = conversionTimeMs;
    }

    public String getOriginalCode() {
        return originalCode;
    }

    public void setOriginalCode(String originalCode) {
        this.originalCode = originalCode;
    }

    public boolean isSuccessful() {
        return status == STATUS_SUCCESS || status == STATUS_PARTIAL;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("BlockConversionResult{status=");
        sb.append(status);
        sb.append(", total=");
        sb.append(totalBlocksGenerated);
        sb.append(", matched=");
        sb.append(matchedBlocks);
        sb.append(", source=");
        sb.append(sourceBlocks);
        sb.append(", time=");
        sb.append(conversionTimeMs);
        sb.append("ms}");
        return sb.toString();
    }
}
