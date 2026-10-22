package com.nexusteam.internal.editor.blockparser;

import com.nexusteam.internal.beans.BlockBean;
import com.nexusteam.internal.editor.blockparser.model.ParsedBlock;
import com.nexusteam.internal.editor.blockparser.result.BlockConversionResult;
import com.nexusteam.internal.editor.blockparser.utils.BlockBeanConverter;
import com.nexusteam.internal.editor.blockparser.utils.BlockDefinitionRegistry;
import com.nexusteam.internal.editor.blockparser.utils.BlockParserHelper;

import java.util.ArrayList;
import java.util.List;

public final class CodeToBlockIntegrator {

    private CodeToBlockIntegrator() {
    }

    public static List convertCodeToBlockBeans(String javaCode) {
        BlockConversionResult result = BlockParserHelper.convert(javaCode);
        if (!result.isSuccessful()) {
            return new ArrayList();
        }

        BlockBeanConverter converter = new BlockBeanConverter();
        return converter.convertToBlockBeans(result.getRootBlocks());
    }

    public static BlockConversionResult convert(String javaCode) {
        return BlockParserHelper.convert(javaCode);
    }

    public static BlockBean createSourceBlockBean(String code) {
        BlockBean bean = new BlockBean();
        bean.id = "1";
        bean.opCode = "addSourceDirectly";
        bean.spec = "add source directly";
        bean.type = " ";
        bean.typeName = "";
        bean.color = 0xFF5D4037;
        bean.parameters = new ArrayList();
        bean.parameters.add(code == null ? "" : code);
        bean.subStack1 = -1;
        bean.subStack2 = -1;
        bean.nextBlock = -1;
        bean.isCustomBlock = false;
        return bean;
    }

    public static int getRegisteredBlockCount() {
        return BlockDefinitionRegistry.getInstance().getTotalCount();
    }

    public static String debug(String javaCode) {
        return BlockParserHelper.prettyPrint(BlockParserHelper.convert(javaCode));
    }
}
