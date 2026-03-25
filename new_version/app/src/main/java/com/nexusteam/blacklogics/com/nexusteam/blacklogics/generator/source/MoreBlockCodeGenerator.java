package com.nexusteam.blacklogics.generator.source;

import com.nexusteam.blacklogics.logic.MoreBlockRepository;
import com.nexusteam.blacklogics.generator.source.model.MoreBlock;
import com.nexusteam.blacklogics.generator.source.model.BlockParameter;

import java.util.List;
import java.util.ArrayList;

public class MoreBlockCodeGenerator {
    
    private static MoreBlockRepository moreBlockRepo = MoreBlockRepository.getInstance();
    
    /**
     * Generate Java code for all more blocks in an activity
     */
    public static void generateMoreBlockMethods(StringBuilder javaCode, String projectId, String activityName) {
        List<MoreBlock> blocks = moreBlockRepo.loadMoreBlocks(projectId, activityName);
        
        if (blocks == null || blocks.isEmpty()) {
            return;
        }
        
        javaCode.append("\n    // ========== MORE BLOCKS ==========\n\n");
        
        for (MoreBlock block : blocks) {
            generateMoreBlockMethod(javaCode, block);
        }
    }
    
    /**
     * Generate a single more block method
     */
    private static void generateMoreBlockMethod(StringBuilder javaCode, MoreBlock block) {
        String methodName = block.getBlockName();
        String blockSpec = block.getBlockSpec();
        List<BlockParameter> parameters = block.getParameters();
        String blockLogic = block.getBlockLogic();
        

        javaCode.append("    public void ").append(methodName).append("(");
        

        if (parameters != null && !parameters.isEmpty()) {
            List<String> paramStrings = new ArrayList<>();
            for (BlockParameter param : parameters) {
                paramStrings.add(param.getType() + " " + "_" + param.getName());
            }
            javaCode.append(String.join(", ", paramStrings));
        }
        
        javaCode.append(") {\n");
        

        if (blockLogic != null && !blockLogic.isEmpty()) {

            String[] logicLines = blockLogic.split("\n");
            for (String line : logicLines) {
                javaCode.append("        ").append(line).append("\n");
            }
        } else {


        }
        
        javaCode.append("    }\n\n");
    }
    
    /**
     * Generate method calls for more blocks (if needed in other places)
     */
    public static void generateMoreBlockCalls(StringBuilder javaCode, String projectId, String activityName, List<String> blockNames) {
        if (blockNames == null || blockNames.isEmpty()) {
            return;
        }
        
        for (String blockName : blockNames) {
            MoreBlock block = moreBlockRepo.getMoreBlock(projectId, activityName, blockName);
            if (block != null) {
                generateMoreBlockCall(javaCode, block);
            }
        }
    }
    
    /**
     * Generate a single method call
     */
    private static void generateMoreBlockCall(StringBuilder javaCode, MoreBlock block) {
        javaCode.append("        ").append(block.getBlockName()).append("(");
        
        List<BlockParameter> parameters = block.getParameters();
        if (parameters != null && !parameters.isEmpty()) {
            List<String> paramDefaults = new ArrayList<>();
            for (BlockParameter param : parameters) {
                paramDefaults.add(param.getDefaultValue());
            }
            javaCode.append(String.join(", ", paramDefaults));
        }
        
        javaCode.append(");\n");
    }
    
    /**
     * Check if activity has any more blocks
     */
    public static boolean hasMoreBlocks(String projectId, String activityName) {
        return moreBlockRepo.countBlocks(projectId, activityName) > 0;
    }
    
    /**
     * Get all more block names
     */
    public static List<String> getMoreBlockNames(String projectId, String activityName) {
        return moreBlockRepo.getBlockNames(projectId, activityName);
    }
}