package com.nexusteam.internal.editor.blockparser;

import com.nexusteam.internal.beans.BlockBean;
import com.nexusteam.internal.editor.blockparser.model.BlockDefinition;
import com.nexusteam.internal.editor.blockparser.result.BlockConversionResult;
import com.nexusteam.internal.editor.blockparser.utils.BlockDefinitionRegistry;
import com.nexusteam.internal.editor.blockparser.utils.BlockParserHelper;
import com.nexusteam.internal.editor.blockparser.utils.CodeToBlockParser;
import com.nexusteam.internal.editor.blockparser.utils.KqOpcodeSource;
import android.content.Context;

import java.util.List;

public final class CodeToBlockManager {

    private CodeToBlockManager() {
    }

    public static void init(Context context) {
        KqOpcodeSource.init(context);
    }

    public static int getKqOpcodeCount() {
        return KqOpcodeSource.size();
    }

    public static List convertToBlockBeans(String javaCode) {
        return CodeToBlockIntegrator.convertCodeToBlockBeans(javaCode);
    }

    public static BlockConversionResult convert(String javaCode) {
        return BlockParserHelper.convert(javaCode);
    }

    public static BlockBean createSourceBlock(String code) {
        return CodeToBlockIntegrator.createSourceBlockBean(code);
    }

    public static void registerCustomBlock(BlockDefinition definition) {
        BlockDefinitionRegistry.getInstance().registerCustom(definition);
    }

    public static void registerCustomBlock(String opcode, String name, String category, String codePattern) {
        BlockDefinition def = new BlockDefinition(opcode, name, category);
        def.setCustom(true);
        def.addCodePattern(codePattern);
        registerCustomBlock(def);
    }

    public static int getRegisteredCount() {
        return BlockDefinitionRegistry.getInstance().getTotalCount();
    }

    public static List getAllRegisteredBlocks() {
        return BlockDefinitionRegistry.getInstance().getAllBlocks();
    }

    public static void clearCustomBlocks() {
        BlockDefinitionRegistry.getInstance().clearCustomBlocks();
    }

    public static String debug(String javaCode) {
        return BlockParserHelper.prettyPrint(BlockParserHelper.convert(javaCode));
    }

    public static CodeToBlockParser getParser() {
        return BlockParserHelper.getParser();
    }
}
