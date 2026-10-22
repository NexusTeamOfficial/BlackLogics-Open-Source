package com.nexusteam.internal.editor.blockparser.utils;

import com.nexusteam.internal.editor.blockparser.model.ParsedBlock;
import com.nexusteam.internal.editor.blockparser.result.BlockConversionResult;

import java.util.List;

public class BlockParserHelper {

    private static CodeToBlockParser parser = new CodeToBlockParser();

    public static BlockConversionResult convert(String javaCode) {
        return parser.parse(javaCode);
    }

    public static boolean canFullyConvert(String javaCode) {
        BlockConversionResult result = parser.parse(javaCode);
        return result.getStatus() == BlockConversionResult.STATUS_SUCCESS;
    }

    public static List getSourceBlocks(String javaCode) {
        BlockConversionResult result = parser.parse(javaCode);
        return result.getUnmatchedSourceBlocks();
    }

    public static String prettyPrint(BlockConversionResult result) {
        if (result == null) {
            return "null";
        }

        StringBuffer sb = new StringBuffer();
        sb.append("=== Code to Block Result ===\n");
        sb.append("Status     : ").append(result.getStatus()).append("\n");
        sb.append("Message    : ").append(result.getMessage()).append("\n");
        sb.append("Total      : ").append(result.getTotalBlocksGenerated()).append("\n");
        sb.append("Matched    : ").append(result.getMatchedBlocks()).append("\n");
        sb.append("Source     : ").append(result.getSourceBlocks()).append("\n");
        sb.append("Time       : ").append(result.getConversionTimeMs()).append(" ms\n");
        sb.append("----------------------------\n");

        printBlocks(sb, result.getRootBlocks(), 0);
        return sb.toString();
    }

    private static void printBlocks(StringBuffer sb, List blocks, int indent) {
        if (blocks == null) {
            return;
        }
        StringBuffer pad = new StringBuffer();
        for (int i = 0; i < indent; i++) {
            pad.append("  ");
        }

        for (int i = 0; i < blocks.size(); i++) {
            ParsedBlock block = (ParsedBlock) blocks.get(i);
            sb.append(pad);
            sb.append("▸ ");
            sb.append(block.getDisplayName());
            sb.append(" [");
            sb.append(block.getType());
            sb.append("]");
            if (block.isMatched()) {
                sb.append(" ✓");
            } else {
                sb.append(" (source)");
            }
            sb.append("\n");

            if (block.getParameters() != null && block.getParameters().size() > 0) {
                sb.append(pad);
                sb.append("   params: ");
                sb.append(block.getParameters().toString());
                sb.append("\n");
            }

            if (block.getChildren() != null && block.getChildren().size() > 0) {
                printBlocks(sb, block.getChildren(), indent + 1);
            }
            if (block.getNextBlocks() != null && block.getNextBlocks().size() > 0) {
                printBlocks(sb, block.getNextBlocks(), indent);
            }
        }
    }

    public static CodeToBlockParser getParser() {
        return parser;
    }
}
