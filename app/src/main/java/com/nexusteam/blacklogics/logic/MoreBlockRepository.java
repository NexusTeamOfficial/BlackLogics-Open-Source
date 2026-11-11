package com.nexusteam.blacklogics.logic;

import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nexusteam.blacklogics.generator.source.model.MoreBlock;
import com.nexusteam.blacklogics.generator.source.model.BlockParameter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoreBlockRepository {
    
    private static MoreBlockRepository instance;
    private Gson gson;
    private Map<String, List<MoreBlock>> cache;
    
    private MoreBlockRepository() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        cache = new HashMap<String, List<MoreBlock>>();
    }
    
    public static MoreBlockRepository getInstance() {
        if (instance == null) {
            instance = new MoreBlockRepository();
        }
        return instance;
    }
    
    private File getBlocksDir(String projectId) {
        File dir = new File("/storage/emulated/0" 
                + "/.blacklogics/projects/" + projectId + "/moreblocks/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }
    
    private File getBlockFile(String projectId, String activityName) {
        return new File(getBlocksDir(projectId), activityName + "_blocks.json");
    }
    
    public void saveMoreBlocks(String projectId, String activityName, List<MoreBlock> blocks) {
        FileWriter writer = null;
        try {
            File blockFile = getBlockFile(projectId, activityName);
            

            writer = new FileWriter(blockFile);
            gson.toJson(blocks, writer);
            

            String cacheKey = projectId + ":" + activityName;
            cache.put(cacheKey, new ArrayList<MoreBlock>(blocks));
            
            Log.d("MoreBlockRepo", "Saved " + blocks.size() + " blocks for " + activityName);
            
        } catch (Exception e) {
            Log.e("MoreBlockRepo", "Error saving blocks", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {

                }
            }
        }
    }
    
    public List<MoreBlock> loadMoreBlocks(String projectId, String activityName) {

        String cacheKey = projectId + ":" + activityName;
        if (cache.containsKey(cacheKey)) {
            Log.d("MoreBlockRepo", "Loading from cache: " + activityName);
            return new ArrayList<MoreBlock>(cache.get(cacheKey));
        }
        
        FileReader reader = null;
        try {
            File blockFile = getBlockFile(projectId, activityName);
            
            if (blockFile.exists()) {
                reader = new FileReader(blockFile);
                Type listType = new TypeToken<ArrayList<MoreBlock>>(){}.getType();
                List<MoreBlock> blocks = gson.fromJson(reader, listType);
                
                if (blocks == null) {
                    blocks = new ArrayList<MoreBlock>();
                }
                

                cache.put(cacheKey, new ArrayList<MoreBlock>(blocks));
                
                Log.d("MoreBlockRepo", "Loaded " + blocks.size() + " blocks from file for " + activityName);
                return blocks;
            } else {
                Log.d("MoreBlockRepo", "No block file found for " + activityName);
            }
        } catch (Exception e) {
            Log.e("MoreBlockRepo", "Error loading blocks", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {

                }
            }
        }
        
        return new ArrayList<MoreBlock>();
    }
    
    public void addMoreBlock(String projectId, String activityName, MoreBlock block) {
        List<MoreBlock> blocks = loadMoreBlocks(projectId, activityName);
        blocks.add(block);
        saveMoreBlocks(projectId, activityName, blocks);
        Log.d("MoreBlockRepo", "Added block: " + block.getBlockName() + " to " + activityName);
    }
    
    public void addMoreBlocks(String projectId, String activityName, List<MoreBlock> newBlocks) {
        List<MoreBlock> blocks = loadMoreBlocks(projectId, activityName);
        blocks.addAll(newBlocks);
        saveMoreBlocks(projectId, activityName, blocks);
    }
    
    public void removeMoreBlock(String projectId, String activityName, String blockName) {
        List<MoreBlock> blocks = loadMoreBlocks(projectId, activityName);
        

        List<MoreBlock> toRemove = new ArrayList<MoreBlock>();
        for (MoreBlock block : blocks) {
            if (block.getBlockName().equals(blockName)) {
                toRemove.add(block);
            }
        }
        blocks.removeAll(toRemove);
        
        saveMoreBlocks(projectId, activityName, blocks);
        Log.d("MoreBlockRepo", "Removed block: " + blockName + " from " + activityName);
    }
    
    public MoreBlock getMoreBlock(String projectId, String activityName, String blockName) {
        List<MoreBlock> blocks = loadMoreBlocks(projectId, activityName);
        
        for (MoreBlock block : blocks) {
            if (block.getBlockName().equals(blockName)) {
                return block;
            }
        }
        
        return null;
    }
    
    public void updateBlockLogic(String projectId, String activityName, String blockName, String logic) {
        List<MoreBlock> blocks = loadMoreBlocks(projectId, activityName);
        boolean updated = false;
        
        for (MoreBlock block : blocks) {
            if (block.getBlockName().equals(blockName)) {
                block.setBlockLogic(logic);
                updated = true;
                break;
            }
        }
        
        if (updated) {
            saveMoreBlocks(projectId, activityName, blocks);
            Log.d("MoreBlockRepo", "Updated logic for block: " + blockName);
        }
    }
    
    public void updateBlockParameters(String projectId, String activityName, String blockName, List<BlockParameter> parameters) {
        List<MoreBlock> blocks = loadMoreBlocks(projectId, activityName);
        boolean updated = false;
        
        for (MoreBlock block : blocks) {
            if (block.getBlockName().equals(blockName)) {
                block.setParameters(parameters);
                updated = true;
                break;
            }
        }
        
        if (updated) {
            saveMoreBlocks(projectId, activityName, blocks);
        }
    }
    
    public void updateMoreBlock(String projectId, String activityName, MoreBlock updatedBlock) {
        List<MoreBlock> blocks = loadMoreBlocks(projectId, activityName);
        
        for (int i = 0; i < blocks.size(); i++) {
            MoreBlock block = blocks.get(i);
            if (block.getBlockName().equals(updatedBlock.getBlockName())) {
                blocks.set(i, updatedBlock);
                break;
            }
        }
        
        saveMoreBlocks(projectId, activityName, blocks);
    }
    
    public boolean blockExists(String projectId, String activityName, String blockName) {
        List<MoreBlock> blocks = loadMoreBlocks(projectId, activityName);
        
        for (MoreBlock block : blocks) {
            if (block.getBlockName().equals(blockName)) {
                return true;
            }
        }
        
        return false;
    }
    
    public List<String> getBlockNames(String projectId, String activityName) {
        List<MoreBlock> blocks = loadMoreBlocks(projectId, activityName);
        List<String> names = new ArrayList<String>();
        
        for (MoreBlock block : blocks) {
            names.add(block.getBlockName());
        }
        
        return names;
    }
    
    public List<BlockParameter> parseBlockSpec(String blockSpec) {
        List<BlockParameter> params = new ArrayList<BlockParameter>();
        
        if (blockSpec == null || blockSpec.isEmpty()) {
            return params;
        }
        
        String[] parts = blockSpec.split(" ");
        int numberCount = 0;
        int stringCount = 0;
        int booleanCount = 0;
        int doubleCount = 0;
        
        for (String part : parts) {
            if (part.startsWith("%")) {
                String type = part.substring(1);
                String paramName = "";
                String paramType = "";
                
                if ("s".equals(type) || "t".equals(type)) {
                    stringCount++;
                    paramType = "String";
                    paramName = "text" + stringCount;
                } else if ("n".equals(type)) {
                    numberCount++;
                    paramType = "int";
                    paramName = "number" + numberCount;
                } else if ("b".equals(type)) {
                    booleanCount++;
                    paramType = "boolean";
                    paramName = "flag" + booleanCount;
                } else if ("d".equals(type)) {
                    doubleCount++;
                    paramType = "double";
                    paramName = "value" + doubleCount;
                } else if ("f".equals(type)) {
                    doubleCount++;
                    paramType = "float";
                    paramName = "float" + doubleCount;
                } else if ("l".equals(type)) {
                    numberCount++;
                    paramType = "long";
                    paramName = "long" + numberCount;
                } else {
                    stringCount++;
                    paramType = "String";
                    paramName = "input" + stringCount;
                }
                
                BlockParameter param = new BlockParameter(paramName, paramType);
                

                if ("String".equals(paramType)) {
                    param.setDefaultValue("\"\"");
                } else if ("int".equals(paramType) || "long".equals(paramType)) {
                    param.setDefaultValue("0");
                } else if ("boolean".equals(paramType)) {
                    param.setDefaultValue("false");
                } else if ("double".equals(paramType)) {
                    param.setDefaultValue("0.0");
                } else if ("float".equals(paramType)) {
                    param.setDefaultValue("0.0f");
                }
                
                params.add(param);
            }
        }
        
        return params;
    }
    
    public List<BlockParameter> parseBlockSpecWithNames(String blockSpec, List<String> customNames) {
        List<BlockParameter> params = parseBlockSpec(blockSpec);
        

        if (customNames != null && customNames.size() == params.size()) {
            for (int i = 0; i < params.size(); i++) {
                params.get(i).setName(customNames.get(i));
            }
        }
        
        return params;
    }
    
    public int countBlocks(String projectId, String activityName) {
        return loadMoreBlocks(projectId, activityName).size();
    }
    
    public void deleteAllBlocks(String projectId, String activityName) {
        File blockFile = getBlockFile(projectId, activityName);
        if (blockFile.exists()) {
            blockFile.delete();
        }
        

        String cacheKey = projectId + ":" + activityName;
        cache.remove(cacheKey);
        
        Log.d("MoreBlockRepo", "Deleted all blocks for " + activityName);
    }
    
    public void clearCache(String projectId) {

        List<String> keysToRemove = new ArrayList<String>();
        
        for (Map.Entry<String, List<MoreBlock>> entry : cache.entrySet()) {
            String key = entry.getKey();
            if (key.startsWith(projectId + ":")) {
                keysToRemove.add(key);
            }
        }
        
        for (String key : keysToRemove) {
            cache.remove(key);
        }
        
        Log.d("MoreBlockRepo", "Cleared cache for project: " + projectId);
    }
    
    public void clearAllCache() {
        cache.clear();
        Log.d("MoreBlockRepo", "Cleared all cache");
    }
    
    public String exportBlocksToJson(String projectId, String activityName) {
        List<MoreBlock> blocks = loadMoreBlocks(projectId, activityName);
        return gson.toJson(blocks);
    }
    
    public void importBlocksFromJson(String projectId, String activityName, String json) {
        try {
            Type listType = new TypeToken<ArrayList<MoreBlock>>(){}.getType();
            List<MoreBlock> blocks = gson.fromJson(json, listType);
            
            if (blocks != null) {
                saveMoreBlocks(projectId, activityName, blocks);
                Log.d("MoreBlockRepo", "Imported " + blocks.size() + " blocks for " + activityName);
            }
        } catch (Exception e) {
            Log.e("MoreBlockRepo", "Error importing blocks", e);
        }
    }
    
    public boolean hasBlocksFile(String projectId, String activityName) {
        File blockFile = getBlockFile(projectId, activityName);
        return blockFile.exists();
    }
    
    public long getLastModified(String projectId, String activityName) {
        File blockFile = getBlockFile(projectId, activityName);
        if (blockFile.exists()) {
            return blockFile.lastModified();
        }
        return 0;
    }
    
    public boolean isValidBlockName(String blockName) {
        if (blockName == null || blockName.isEmpty()) {
            return false;
        }
        

        return blockName.matches("^[a-zA-Z][a-zA-Z0-9_]*$");
    }
    
    public String suggestBlockName(String blockSpec) {
        if (blockSpec == null || blockSpec.isEmpty()) {
            return "newBlock";
        }
        

        String[] parts = blockSpec.split(" ");
        String firstWord = parts[0].toLowerCase();
        

        String suggested = firstWord.replaceAll("[^a-zA-Z0-9]", "");
        
        if (suggested.isEmpty()) {
            return "newBlock";
        }
        

        if (!Character.isJavaIdentifierStart(suggested.charAt(0))) {
            suggested = "block" + suggested;
        }
        
        return suggested;
    }
}