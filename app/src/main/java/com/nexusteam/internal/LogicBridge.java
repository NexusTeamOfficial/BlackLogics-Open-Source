package com.nexusteam.blacklogics.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;

import com.nexusteam.internal.BlockStorage;
import com.nexusteam.internal.beans.BlockBean;
import com.nexusteam.internal.model.ComponentData;
import com.nexusteam.internal.model.VariableData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class LogicBridge {

    private static final String TAG     = "LogicBridge";
    private static final String AES_KEY = "sketchwaresecure";
    private static final String CHARSET = "UTF-8";
    private static final String ALGO    = "AES/CBC/PKCS5Padding";

    private static final int VAR_BOOLEAN  = 0;
    private static final int VAR_INT      = 1;
    private static final int VAR_STRING   = 2;
    private static final int VAR_MAP      = 3;
    private static final int VAR_LIST_INT = 4;
    private static final int VAR_LIST_STR = 5;
    private static final int VAR_LIST_MAP = 6;

    private static final Handler mainHandler = new Handler(Looper.getMainLooper());


    public interface Callback {
        void onSuccess(int totalBlocks);
        void onError(String reason);
    }


    public static void importLogic(
            final Context context,
            final String logicPath,
            final String newScId,
            final Callback callback) {

        new Thread(new Runnable() {
            public void run() {
                try {
                    final int total = importLogicSync(context, logicPath, newScId);
                    mainHandler.post(new Runnable() {
                        public void run() {
                            callback.onSuccess(total);
                        }
                    });
                } catch (final Exception e) {
                    Log.e(TAG, "importLogic failed", e);
                    mainHandler.post(new Runnable() {
                        public void run() {
                            callback.onError(e.getMessage() != null
                                    ? e.getMessage() : "Unknown error");
                        }
                    });
                }
            }
        }).start();
    }

    public static int importLogicSync(
            Context context,
            String logicPath,
            String newScId) throws Exception {

        byte[] encrypted = readFile(logicPath);
        String raw = decrypt(encrypted);

        HashMap<String, ArrayList<String>> sections = splitSections(raw);

        BlockStorage.setScId(newScId);

        Object[] existing = BlockStorage.readAll();
        HashMap<String, ArrayList<BlockBean>> allBlocks =
                (HashMap<String, ArrayList<BlockBean>>) existing[0];
        HashMap<String, ArrayList<Pair<String, String>>> allFunctions =
                (HashMap<String, ArrayList<Pair<String, String>>>) existing[1];
        HashMap<String, ArrayList<ComponentData>> allComponents =
                (HashMap<String, ArrayList<ComponentData>>) existing[2];
        HashMap<String, ArrayList<VariableData>> allVariables =
                (HashMap<String, ArrayList<VariableData>>) existing[3];

        int totalBlocks = 0;

        for (String sectionKey : sections.keySet()) {
            ArrayList<String> lines = sections.get(sectionKey);

            if (sectionKey.endsWith("_var")) {
                String actKey = sectionKey.replace("_var", "");
                ArrayList<VariableData> varList =
                        parseVariables(lines);
                if (!varList.isEmpty()) {
                    allVariables.put(actKey, varList);
                    Log.d(TAG, "Variables for " + actKey + ": " + varList.size());
                }

            } else if (sectionKey.endsWith("_com")) {
                String actKey = sectionKey.replace("_com", "");
                ArrayList<ComponentData> compList =
                        parseComponents(lines);
                if (!compList.isEmpty()) {
                    allComponents.put(actKey, compList);
                    Log.d(TAG, "Components for " + actKey + ": " + compList.size());
                }

            } else if (sectionKey.endsWith("_func")) {
                String actKey = sectionKey.replace("_func", "");
                ArrayList<Pair<String, String>> funcList =
                        parseFunctions(lines);
                if (!funcList.isEmpty()) {
                    allFunctions.put(actKey, funcList);
                    Log.d(TAG, "Functions for " + actKey + ": " + funcList.size());
                }

            } else {
                ArrayList<BlockBean> blockList = parseBlocks(lines);
                if (!blockList.isEmpty()) {
                    allBlocks.put(sectionKey, blockList);
                    totalBlocks += blockList.size();
                    Log.d(TAG, "Blocks [" + sectionKey + "]: " + blockList.size());
                }
            }
        }

        BlockStorage.writeAll(allBlocks, allFunctions, allComponents, allVariables);
        Log.d(TAG, "Logic import complete — total blocks: " + totalBlocks);

        return totalBlocks;
    }


    private static HashMap<String, ArrayList<String>> splitSections(String raw) {
        HashMap<String, ArrayList<String>> result =
                new HashMap<String, ArrayList<String>>();

        String[] lines = raw.split("\n");
        String currentKey = null;
        ArrayList<String> currentList = null;

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) continue;

            if (line.startsWith("@")) {
                if (currentKey != null && currentList != null) {
                    result.put(currentKey, currentList);
                }
                currentKey  = line.substring(1).trim();
                currentList = new ArrayList<String>();
            } else if (currentList != null) {
                currentList.add(line);
            }
        }

        if (currentKey != null && currentList != null) {
            result.put(currentKey, currentList);
        }

        return result;
    }


    private static ArrayList<BlockBean> parseBlocks(ArrayList<String> lines) {
        ArrayList<BlockBean> list = new ArrayList<BlockBean>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (!line.startsWith("{")) continue;

            try {
                JSONObject json = new JSONObject(line);

                BlockBean block = new BlockBean();
                block.color      = json.optInt("color", -1);
                block.id = String.valueOf(json.optInt("id", i));
                block.nextBlock  = json.optInt("nextBlock", -1);
                block.opCode     = json.optString("opCode", "");
                block.spec       = json.optString("spec", "");
                block.type       = json.optString("type", "");
                block.typeName   = json.optString("typeName", "");
                block.subStack1  = json.optInt("subStack1", -1);
                block.subStack2  = json.optInt("subStack2", -1);

                JSONArray paramsArr = json.optJSONArray("parameters");
                if (paramsArr != null) {
                    ArrayList<String> params = new ArrayList<String>();
                    for (int p = 0; p < paramsArr.length(); p++) {
                        params.add(paramsArr.optString(p, ""));
                    }
                    block.parameters = params;
                } else {
                    block.parameters = new ArrayList<String>();
                }

                list.add(block);

            } catch (Exception e) {
                Log.w(TAG, "Skipping malformed block: " + line);
            }
        }

        return list;
    }


    private static ArrayList<VariableData> parseVariables(ArrayList<String> lines) {
        ArrayList<VariableData> list = new ArrayList<VariableData>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (!line.startsWith("{")) continue;

            try {
                JSONObject json = new JSONObject(line);
                String name = json.optString("name", "");
                int type    = json.optInt("type", VAR_STRING);
                if (!name.isEmpty()) {
                    list.add(new VariableData(type, name, ""));
                }
            } catch (Exception e) {
                Log.w(TAG, "Skipping malformed variable: " + line);
            }
        }

        return list;
    }


    private static ArrayList<ComponentData> parseComponents(ArrayList<String> lines) {
        ArrayList<ComponentData> list = new ArrayList<ComponentData>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (!line.startsWith("{")) continue;

            try {
                JSONObject json = new JSONObject(line);
                int type        = json.optInt("type", 0);
                String mainData = json.optString("componentId", "");
                String extra    = json.optString("param1", "");

                if (!mainData.isEmpty()) {
                    list.add(new ComponentData(type, mainData, extra));
                }
            } catch (Exception e) {
                Log.w(TAG, "Skipping malformed component: " + line);
            }
        }

        return list;
    }


    private static ArrayList<Pair<String, String>> parseFunctions(ArrayList<String> lines) {
        ArrayList<Pair<String, String>> list =
                new ArrayList<Pair<String, String>>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (!line.startsWith("{")) continue;

            try {
                JSONObject json = new JSONObject(line);
                String name = json.optString("name", "");
                String spec = json.optString("spec", name);
                if (!name.isEmpty()) {
                    list.add(new Pair<String, String>(name, spec));
                }
            } catch (Exception e) {
                Log.w(TAG, "Skipping malformed function: " + line);
            }
        }

        return list;
    }


    private static String decrypt(byte[] encrypted) throws Exception {
        byte[] keyBytes = AES_KEY.getBytes(CHARSET);
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.DECRYPT_MODE,
                new SecretKeySpec(keyBytes, "AES"),
                new IvParameterSpec(keyBytes));
        return new String(cipher.doFinal(encrypted), CHARSET);
    }


    private static byte[] readFile(String path) throws Exception {
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        return data;
    }
}
