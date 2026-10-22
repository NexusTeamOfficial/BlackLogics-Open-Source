package com.nexusteam.blacklogics.sdk.util;

import android.os.Build;
import android.content.Context;
import android.widget.Toast;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import android.content.pm.PackageManager;
import android.Manifest;

import com.google.gson.*;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BlockWrite {
    private static final String TAG = "BlockWrite";
    private static final String FILE_PATH = "/storage/emulated/0/.blacklogics/resources/block/My Block/block.json";
    private Context context;
    private BlockWriteCallback callback;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public interface BlockWriteCallback {
        void onSuccess(String message);
        void onError(String error);
    }

    public BlockWrite(Context context) {
        this.context = context;
    }

    public BlockWrite(Context context, BlockWriteCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void writeMathBlocks() {
        if (Build.VERSION.SDK_INT >= 33) {
            handleAndroid13Plus();
        } else {
            handleLegacyAndroid();
        }
    }

    private void handleAndroid13Plus() {
        if (checkPermissions()) {
            executeWriteOperation();
        } else {
            requestPermissions();
        }
    }

    private void handleLegacyAndroid() {
        if (checkLegacyPermissions()) {
            executeWriteOperation();
        } else {
            if (callback != null) {
                callback.onError("Storage permission required");
            }
        }
    }

    private boolean checkPermissions() {
        if (Build.VERSION.SDK_INT >= 33) {
            return true;
        }
        return checkLegacyPermissions();
    }

    private boolean checkLegacyPermissions() {
        return ActivityCompat.checkSelfPermission(context, 
            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        if (callback != null) {
            callback.onError("Please grant storage permissions first");
        }
    }

    private void executeWriteOperation() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .serializeNulls()
                        .create();

                    File file = new File(FILE_PATH);
                    
                    handleFileWithJava7(file, gson);

                } catch (Exception e) {
                    Log.e(TAG, "Error writing blocks", e);
                    if (callback != null) {
                        callback.onError("Error: " + e.getMessage());
                    }
                }
            }
        });
    }

    private void handleFileWithJava7(File file, Gson gson) throws IOException {
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        JsonArray blocksArray;

        if (!file.exists()) {
            blocksArray = createDefaultBlocks();
            writeJsonToFile(file, gson.toJson(blocksArray));
            
            if (callback != null) {
                callback.onSuccess("New file created with " + blocksArray.size() + " blocks at: " + file.getAbsolutePath());
            }
        } else {
            String content = readFileAsString(file);
            blocksArray = gson.fromJson(content, JsonArray.class);

            if (blocksArray == null) {
                blocksArray = new JsonArray();
            }

            if (!hasPalette5(blocksArray)) {
                int addedCount = addMissingBlocks(blocksArray);
                if (addedCount > 0) {
                    writeJsonToFile(file, gson.toJson(blocksArray));
                    
                    if (callback != null) {
                        callback.onSuccess("Added " + addedCount + " new blocks (palette 4)");
                    }
                } else {
                    if (callback != null) {
                        callback.onSuccess("All blocks already exist (palette 4)");
                    }
                }
            } else {
                if (callback != null) {
                    callback.onSuccess("Palette 5 blocks found - no changes needed");
                }
            }
        }
    }

    private String readFileAsString(File file) throws IOException {
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "Error closing reader", e);
                }
            }
        }
        
        return content.toString();
    }

    private void writeJsonToFile(File file, String jsonString) throws IOException {
        FileWriter writer = null;
        
        try {
            writer = new FileWriter(file);
            writer.write(jsonString);
            writer.flush();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    Log.e(TAG, "Error closing writer", e);
                }
            }
        }
    }

    private boolean hasPalette5(JsonArray blocksArray) {
        for (int i = 0; i < blocksArray.size(); i++) {
            JsonElement element = blocksArray.get(i);
            if (element.isJsonObject()) {
                JsonObject block = element.getAsJsonObject();
                if (block.has("palette")) {
                    JsonElement paletteElem = block.get("palette");
                    if (paletteElem.isJsonPrimitive()) {
                        String palette = paletteElem.getAsString();
                        if ("5".equals(palette)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private int addMissingBlocks(JsonArray blocksArray) {
        Set<String> existingNames = new HashSet<String>();
        for (int i = 0; i < blocksArray.size(); i++) {
            JsonElement element = blocksArray.get(i);
            if (element.isJsonObject()) {
                JsonObject block = element.getAsJsonObject();
                if (block.has("name")) {
                    existingNames.add(block.get("name").getAsString());
                }
            }
        }

        int addedCount = 0;
        JsonArray defaultBlocks = createDefaultBlocks();

        for (int i = 0; i < defaultBlocks.size(); i++) {
            JsonElement element = defaultBlocks.get(i);
            if (element.isJsonObject()) {
                JsonObject block = element.getAsJsonObject();
                String name = block.get("name").getAsString();
                
                if (!existingNames.contains(name)) {
                    blocksArray.add(block);
                    addedCount++;
                }
            }
        }

        return addedCount;
    }

    private JsonArray createDefaultBlocks() {
        JsonArray blocksArray = new JsonArray();

        blocksArray.add(createBlock("Math.PI", "pi", "d", "PI (π)", "4", "#4CAF50"));
        blocksArray.add(createBlock("Math.E", "e", "d", "E (Euler number)", "4", "#4CAF50"));

        blocksArray.add(createBlock("Math.abs(%1$s)", "absolute", "d", "absolute value of %d", "4", "#4CAF50"));
        blocksArray.add(createBlock("Math.sqrt(%1$s)", "sqrt", "d", "square root of %d", "4", "#4CAF50"));
        blocksArray.add(createBlock("Math.cbrt(%1$s)", "cbrt", "d", "cube root of %d", "4", "#4CAF50"));

        blocksArray.add(createBlock("Math.pow(%1$s,%2$s)", "power", "d", "%d to the %d power", "4", "#4CAF50"));
        blocksArray.add(createBlock("Math.exp(%1$s)", "exp", "d", "e power %d", "4", "#4CAF50"));

        blocksArray.add(createBlock("Math.log(%1$s)", "log", "d", "natural log of %d", "4", "#4CAF50"));
        blocksArray.add(createBlock("Math.log10(%1$s)", "log10", "d", "log base 10 of %d", "4", "#4CAF50"));

        blocksArray.add(createBlock("Math.round(%1$s)", "round", "d", "round %d", "4", "#4CAF50"));
        blocksArray.add(createBlock("Math.ceil(%1$s)", "ceil", "d", "ceil %d", "4", "#4CAF50"));
        blocksArray.add(createBlock("Math.floor(%1$s)", "floor", "d", "floor %d", "4", "#4CAF50"));

        blocksArray.add(createBlock("Math.min(%1$s,%2$s)", "min", "d", "minimum of %d and %d", "4", "#4CAF50"));
        blocksArray.add(createBlock("Math.max(%1$s,%2$s)", "max", "d", "maximum of %d and %d", "4", "#4CAF50"));

        blocksArray.add(createBlock("Math.sin(%1$s)", "sin", "d", "sin %d", "4", "#4CAF50"));
        blocksArray.add(createBlock("Math.cos(%1$s)", "cos", "d", "cos %d", "4", "#4CAF50"));
        blocksArray.add(createBlock("Math.tan(%1$s)", "tan", "d", "tan %d", "4", "#4CAF50"));
        blocksArray.add(createBlock("Math.asin(%1$s)", "asin", "d", "arcsin %d", "4", "#4CAF50"));
        blocksArray.add(createBlock("Math.acos(%1$s)", "acos", "d", "arccos %d", "4", "#4CAF50"));
        blocksArray.add(createBlock("Math.atan(%1$s)", "atan", "d", "arctan %d", "4", "#4CAF50"));

        blocksArray.add(createBlock("Math.toRadians(%1$s)", "toRadians", "d", "convert %d degrees to radians", "4", "#4CAF50"));
        blocksArray.add(createBlock("Math.toDegrees(%1$s)", "toDegrees", "d", "convert %d radians to degrees", "4", "#4CAF50"));

        blocksArray.add(createBlock("Math.random()", "random_double", "d", "random number (0 to 1)", "4", "#4CAF50"));
        blocksArray.add(createBlock("new java.util.Random().nextInt((int)%1$s)", "random_int", "d", "random integer below %d", "4", "#4CAF50"));

        blocksArray.add(createBlock("(%1$s % %2$s)", "mod", "d", "%d mod %d", "4", "#4CAF50"));

        blocksArray.add(createBlock("Math.signum(%1$s)", "sign", "d", "sign of %d", "4", "#4CAF50"));

        blocksArray.add(createBlock("(%1$s > %2$s)", "greater", "b", "%d > %d", "4", "#4CAF50"));
        blocksArray.add(createBlock("(%1$s < %2$s)", "less", "b", "%d < %d", "4", "#4CAF50"));
        blocksArray.add(createBlock("(%1$s == %2$s)", "equal", "b", "%d = %d", "4", "#4CAF50"));

        blocksArray.add(createBlock("(%1$s % 2 == 0)", "is_even", "b", "%d is even", "4", "#4CAF50"));
        blocksArray.add(createBlock("(%1$s % 2 != 0)", "is_odd", "b", "%d is odd", "4", "#4CAF50"));

        return blocksArray;
    }
    
    private JsonObject createBlock(String code, String name, String type, String spec, String palette, String color) {
        JsonObject block = new JsonObject();
        block.addProperty("code", code);
        block.addProperty("name", name);
        block.addProperty("type", type);
        block.addProperty("spec", spec);
        block.addProperty("palette", palette);
        block.addProperty("color", color);
        return block;
    }

    public void shutdown() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
}
