package com.nexusteam.blacklogics.file;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BlockMaker {
    private static final String TAG = "BlockMaker";
    private final File blockFile;
    
    public BlockMaker(String filePath) {
        this.blockFile = new File(filePath);
        ensureFileExists();
    }
    
    private void ensureFileExists() {
        try {
            if (!blockFile.exists()) {
                blockFile.getParentFile().mkdirs();
                blockFile.createNewFile();
                writeJsonArray(new JSONArray());
            }
        } catch (IOException e) {
            Log.e(TAG, "Error creating block file: " + e.getMessage());
        }
    }
    
    private JSONArray readJsonArray() {
        try (BufferedReader reader = new BufferedReader(new FileReader(blockFile))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            return new JSONArray(builder.toString().isEmpty() ? "[]" : builder.toString());
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error reading blocks: " + e.getMessage());
            return new JSONArray();
        }
    }
    
    private void writeJsonArray(JSONArray array) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(blockFile))) {
            writer.write(array.toString(4));
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error writing JSON: " + e.getMessage());
        }
    }
    
    
    public void addBlock(JSONObject block) {
        JSONArray array = readJsonArray();
        array.put(block);
        writeJsonArray(array);
    }
    
    public void updateBlock(String code, JSONObject updatedBlock) {
        JSONArray array = readJsonArray();
        boolean updated = false;
        
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject block = array.getJSONObject(i);
                if (block.getString("code").equals(code)) {
                    array.put(i, updatedBlock);
                    updated = true;
                    break;
                }
            } catch (JSONException e) {
                Log.e(TAG, "Error updating block: " + e.getMessage());
            }
        }
        
        if (updated) {
            writeJsonArray(array);
        }
    }
    
    public JSONObject getBlockByCode(String code) {
        JSONArray array = readJsonArray();
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject block = array.getJSONObject(i);
                if (block.getString("code").equals(code)) {
                    return block;
                }
            } catch (JSONException e) {
                Log.e(TAG, "Error finding block: " + e.getMessage());
            }
        }
        return null;
    }
    
    public List<JSONObject> getAllBlocks() {
        List<JSONObject> blocks = new ArrayList<>();
        JSONArray array = readJsonArray();
        for (int i = 0; i < array.length(); i++) {
            try {
                blocks.add(array.getJSONObject(i));
            } catch (JSONException e) {
                Log.e(TAG, "Error loading blocks: " + e.getMessage());
            }
        }
        return blocks;
    }
    
    public void deleteBlock(String code) {
        JSONArray array = readJsonArray();
        JSONArray newArray = new JSONArray();
        
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject block = array.getJSONObject(i);
                if (!block.getString("code").equals(code)) {
                    newArray.put(block);
                }
            } catch (JSONException e) {
                Log.e(TAG, "Error deleting block: " + e.getMessage());
            }
        }
        
        writeJsonArray(newArray);
    }
}
