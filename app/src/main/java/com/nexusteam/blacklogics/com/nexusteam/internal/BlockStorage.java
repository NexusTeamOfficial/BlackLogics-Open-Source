package com.nexusteam.internal;

import android.util.Log;
import android.util.Pair;
import com.nexusteam.internal.beans.BlockBean;
import com.nexusteam.internal.model.ComponentData;
import com.nexusteam.internal.model.VariableData;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class BlockStorage {
    
    private static final String BASE = "/storage/emulated/0/.blacklogics/data/";
    private static final String FILE = "project.dat";
    private static final int VERSION = 4;
    private static String sc_id;
    private static final byte[] MASK_KEY = "NEXUS_PRIVATE_KEY_123".getBytes();
    
    private static File file() {
        File d = new File(BASE + sc_id);
        if (!d.exists()) d.mkdirs();
        return new File(d, FILE);
    }
    
    private static byte[] applyMask(byte[] data) {
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = (byte) (data[i] ^ MASK_KEY[i % MASK_KEY.length]);
        }
        return result;
    }
    
    public static void setScId(String sc) {
        BlockStorage.sc_id = sc;
    }
    
    public static void writeAll(
    HashMap<String, ArrayList<BlockBean>> blocks,
    HashMap<String, ArrayList<Pair<String, String>>> functions,
    HashMap<String, ArrayList<ComponentData>> components,
    HashMap<String, ArrayList<VariableData>> variables) {
        
        ByteArrayOutputStream bos = null;
        DataOutputStream out = null;
        FileOutputStream fos = null;
        
        try {
            bos = new ByteArrayOutputStream();
            out = new DataOutputStream(bos);
            
            out.writeInt(VERSION);
            

            out.writeInt(blocks.size());
            Iterator<Map.Entry<String, ArrayList<BlockBean>>> blockIter = blocks.entrySet().iterator();
            while (blockIter.hasNext()) {
                Map.Entry<String, ArrayList<BlockBean>> entry = blockIter.next();
                out.writeUTF(entry.getKey());
                ArrayList<BlockBean> list = entry.getValue();
                out.writeInt(list == null ? 0 : list.size());
                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        BlockBean b = list.get(i);
                        b.writeToStream(out); 
                    }
                }
            }
            

            out.writeInt(functions.size());
            Iterator<Map.Entry<String, ArrayList<Pair<String, String>>>> funcIter = functions.entrySet().iterator();
            while (funcIter.hasNext()) {
                Map.Entry<String, ArrayList<Pair<String, String>>> entry = funcIter.next();
                out.writeUTF(entry.getKey());
                ArrayList<Pair<String, String>> list = entry.getValue();
                out.writeInt(list == null ? 0 : list.size());
                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        Pair<String, String> p = list.get(i);
                        out.writeUTF(p.first);
                        out.writeUTF(p.second);
                    }
                }
            }
            

            out.writeInt(components.size());
            Iterator<Map.Entry<String, ArrayList<ComponentData>>> compIter =
            components.entrySet().iterator();
            
            while (compIter.hasNext()) {
                
                Map.Entry<String, ArrayList<ComponentData>> entry = compIter.next();
                out.writeUTF(entry.getKey());
                
                ArrayList<ComponentData> list = entry.getValue();
                out.writeInt(list == null ? 0 : list.size());
                
                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        ComponentData c = list.get(i);
                        out.writeInt(c.type);
                        out.writeUTF(c.mainData != null ? c.mainData : "");
                        out.writeUTF(c.extraData != null ? c.extraData : "");
                    }
                }
            }
            
            

            out.writeInt(variables.size());
            Iterator<Map.Entry<String, ArrayList<VariableData>>> varIter = variables.entrySet().iterator();
            while (varIter.hasNext()) {
                Map.Entry<String, ArrayList<VariableData>> entry = varIter.next();
                out.writeUTF(entry.getKey());
                ArrayList<VariableData> list = entry.getValue();
                out.writeInt(list == null ? 0 : list.size());
                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        VariableData v = list.get(i);
                        out.writeInt(v.type);
                        out.writeUTF(v.name);
                        out.writeUTF(v.defaultValue != null ? v.defaultValue : "");
                    }
                }
            }
            
            out.flush();
            
            byte[] encryptedBytes = applyMask(bos.toByteArray());
            fos = new FileOutputStream(file());
            fos.write(encryptedBytes);
            
        } catch (Exception e) {
            Log.e("BlockStorage", "WRITE ERROR", e);
        } finally {
            try {
                if (out != null) out.close();
                if (bos != null) bos.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static Object[] readAll() {
        HashMap<String, ArrayList<BlockBean>> blocks = new HashMap<String, ArrayList<BlockBean>>();
        HashMap<String, ArrayList<Pair<String, String>>> functions = new HashMap<String, ArrayList<Pair<String, String>>>();
        HashMap<String, ArrayList<ComponentData>> components =
        new HashMap<String, ArrayList<ComponentData>>();
        HashMap<String, ArrayList<VariableData>> variables = new HashMap<String, ArrayList<VariableData>>();
        
        File f = file();
        if (!f.exists()) {
            return new Object[]{blocks, functions, components, variables};
        }
        
        FileInputStream fis = null;
        DataInputStream in = null;
        
        try {
            byte[] fileData = new byte[(int) f.length()];
            fis = new FileInputStream(f);
            fis.read(fileData);
            
            byte[] decryptedBytes = applyMask(fileData);
            
            in = new DataInputStream(new ByteArrayInputStream(decryptedBytes));
            int version = in.readInt();
            

            int bc = in.readInt();
            for (int i = 0; i < bc; i++) {
                String key = in.readUTF();
                int size = in.readInt();
                ArrayList<BlockBean> list = new ArrayList<BlockBean>();
                for (int j = 0; j < size; j++) {
                    BlockBean b = new BlockBean();
                    b.readFromStream(in); 
                    list.add(b);
                }
                blocks.put(key, list);
            }
            

            int fc = in.readInt();
            for (int i = 0; i < fc; i++) {
                String key = in.readUTF();
                int size = in.readInt();
                ArrayList<Pair<String, String>> list = new ArrayList<Pair<String, String>>();
                for (int j = 0; j < size; j++) {
                    list.add(new Pair<String, String>(in.readUTF(), in.readUTF()));
                }
                functions.put(key, list);
            }
            

            if (version >= 2) {
                
                int cc = in.readInt();
                
                for (int i = 0; i < cc; i++) {
                    
                    String key = in.readUTF();
                    int size = in.readInt();
                    
                    ArrayList<ComponentData> list =
                    new ArrayList<ComponentData>();
                    
                    for (int j = 0; j < size; j++) {
                        
                        int type = in.readInt();
                        String mainData = in.readUTF();
                        String extraData = in.readUTF();
                        
                        list.add(new ComponentData(type, mainData, extraData));
                    }
                    
                    components.put(key, list);
                }
            }
            
            

            if (version >= 3) {
                int vc = in.readInt();
                for (int i = 0; i < vc; i++) {
                    String key = in.readUTF();
                    int size = in.readInt();
                    ArrayList<VariableData> list = new ArrayList<VariableData>();
                    for (int j = 0; j < size; j++) {
                        int type = in.readInt();
                        String name = in.readUTF();
                        String defaultValue = in.readUTF();
                        list.add(new VariableData(type, name, defaultValue));
                    }
                    variables.put(key, list);
                }
            }
            
        } catch (Exception e) {
            Log.e("BlockStorage", "READ ERROR (File might be corrupted)", e);
        } finally {
            try {
                if (in != null) in.close();
                if (fis != null) fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return new Object[]{blocks, functions, components, variables};
    }
    
    
    public static void saveVariables(String activityName, ArrayList<VariableData> variableList) {
        Object[] o = readAll();
        HashMap<String, ArrayList<BlockBean>> blocks = (HashMap<String, ArrayList<BlockBean>>) o[0];
        HashMap<String, ArrayList<Pair<String, String>>> functions = (HashMap<String, ArrayList<Pair<String, String>>>) o[1];
        HashMap<String, ArrayList<ComponentData>> components =
        (HashMap<String, ArrayList<ComponentData>>) o[2];
        HashMap<String, ArrayList<VariableData>> variables = (HashMap<String, ArrayList<VariableData>>) o[3];
        
        variables.put(activityName, variableList);
        writeAll(blocks, functions, components, variables);
    }
    
    public static void addVariable(String activityName, VariableData variable) {
        Object[] o = readAll();
        HashMap<String, ArrayList<BlockBean>> blocks = (HashMap<String, ArrayList<BlockBean>>) o[0];
        HashMap<String, ArrayList<Pair<String, String>>> functions = (HashMap<String, ArrayList<Pair<String, String>>>) o[1];
        HashMap<String, ArrayList<ComponentData>> components =
        (HashMap<String, ArrayList<ComponentData>>) o[2];
        HashMap<String, ArrayList<VariableData>> variables = (HashMap<String, ArrayList<VariableData>>) o[3];
        
        ArrayList<VariableData> list = variables.get(activityName);
        if (list == null) {
            list = new ArrayList<VariableData>();
        }
        

        for (int i = 0; i < list.size(); i++) {
            VariableData v = list.get(i);
            if (v.name.equals(variable.name) && v.type == variable.type) {
                return;
            }
        }
        
        list.add(variable);
        variables.put(activityName, list);
        writeAll(blocks, functions, components, variables);
    }
    
    public static void removeVariable(String activityName, int type, String name) {
        Object[] o = readAll();
        HashMap<String, ArrayList<BlockBean>> blocks = (HashMap<String, ArrayList<BlockBean>>) o[0];
        HashMap<String, ArrayList<Pair<String, String>>> functions = (HashMap<String, ArrayList<Pair<String, String>>>) o[1];
        HashMap<String, ArrayList<ComponentData>> components =
        (HashMap<String, ArrayList<ComponentData>>) o[2];
        HashMap<String, ArrayList<VariableData>> variables = (HashMap<String, ArrayList<VariableData>>) o[3];
        
        ArrayList<VariableData> list = variables.get(activityName);
        if (list != null) {
            ArrayList<VariableData> newList = new ArrayList<VariableData>();
            for (int i = 0; i < list.size(); i++) {
                VariableData v = list.get(i);
                if (!(v.type == type && v.name.equals(name))) {
                    newList.add(v);
                }
            }
            variables.put(activityName, newList);
            writeAll(blocks, functions, components, variables);
        }
    }
    
    public static ArrayList<VariableData> loadVariables(String activityName) {
        Object[] o = readAll();
        HashMap<String, ArrayList<VariableData>> variables = (HashMap<String, ArrayList<VariableData>>) o[3];
        ArrayList<VariableData> list = variables.get(activityName);
        return list == null ? new ArrayList<VariableData>() : list;
    }
    
    public static void save(String key, ArrayList<BlockBean> list) {
        Object[] o = readAll();
        HashMap<String, ArrayList<BlockBean>> blocks = (HashMap<String, ArrayList<BlockBean>>) o[0];
        HashMap<String, ArrayList<Pair<String, String>>> functions = (HashMap<String, ArrayList<Pair<String, String>>>) o[1];
        HashMap<String, ArrayList<ComponentData>> components =
        (HashMap<String, ArrayList<ComponentData>>) o[2];
        HashMap<String, ArrayList<VariableData>> variables = (HashMap<String, ArrayList<VariableData>>) o[3];
        blocks.put(key, list);
        writeAll(blocks, functions, components, variables);
    }
    
    public static ArrayList<BlockBean> load(String key) {
        Object[] o = readAll();
        HashMap<String, ArrayList<BlockBean>> blocks = (HashMap<String, ArrayList<BlockBean>>) o[0];
        ArrayList<BlockBean> list = blocks.get(key);
        return list == null ? new ArrayList<BlockBean>() : list;
    }
    
    public static void saveFunctions(String key, ArrayList<Pair<String, String>> list) {
        Object[] o = readAll();
        HashMap<String, ArrayList<BlockBean>> blocks = (HashMap<String, ArrayList<BlockBean>>) o[0];
        HashMap<String, ArrayList<Pair<String, String>>> functions = (HashMap<String, ArrayList<Pair<String, String>>>) o[1];
        HashMap<String, ArrayList<ComponentData>> components =
        (HashMap<String, ArrayList<ComponentData>>) o[2];
        HashMap<String, ArrayList<VariableData>> variables = (HashMap<String, ArrayList<VariableData>>) o[3];
        functions.put(key, list);
        writeAll(blocks, functions, components, variables);
    }
    
    public static ArrayList<Pair<String, String>> loadFunctions(String key) {
        Object[] o = readAll();
        HashMap<String, ArrayList<Pair<String, String>>> functions = (HashMap<String, ArrayList<Pair<String, String>>>) o[1];
        ArrayList<Pair<String, String>> list = functions.get(key);
        return list == null ? new ArrayList<Pair<String, String>>() : list;
    }
    
    public static void saveComponent(String key, ComponentData data) {
        if (data == null) return;
        Object[] o = readAll();
        
        HashMap<String, ArrayList<BlockBean>> blocks =
        (HashMap<String, ArrayList<BlockBean>>) o[0];
        
        HashMap<String, ArrayList<Pair<String, String>>> functions =
        (HashMap<String, ArrayList<Pair<String, String>>>) o[1];
        
        HashMap<String, ArrayList<ComponentData>> components =
        (HashMap<String, ArrayList<ComponentData>>) o[2];
        
        HashMap<String, ArrayList<VariableData>> variables =
        (HashMap<String, ArrayList<VariableData>>) o[3];
        
        ArrayList<ComponentData> list = components.get(key);
        if (list == null) {
            list = new ArrayList<ComponentData>();
        } else {
            list = new ArrayList<ComponentData>(list);
        }
        
        boolean replaced = false;
        if (data.mainData != null && data.mainData.length() > 0) {
            for (int i = 0; i < list.size(); i++) {
                ComponentData existing = list.get(i);
                if (existing != null && data.mainData.equals(existing.mainData)) {
                    list.set(i, data);
                    replaced = true;
                    break;
                }
            }
        }
        if (!replaced) {
            list.add(data);
        }
        
        components.put(key, list);
        
        writeAll(blocks, functions, components, variables);
        
    }
    
    public static void addComponent(String activityName, ComponentData data) {
        if (data == null) return;
        Object[] o = readAll();
        
        HashMap<String, ArrayList<BlockBean>> blocks =
        (HashMap<String, ArrayList<BlockBean>>) o[0];
        
        HashMap<String, ArrayList<Pair<String, String>>> functions =
        (HashMap<String, ArrayList<Pair<String, String>>>) o[1];
        
        HashMap<String, ArrayList<ComponentData>> components =
        (HashMap<String, ArrayList<ComponentData>>) o[2];
        
        HashMap<String, ArrayList<VariableData>> variables =
        (HashMap<String, ArrayList<VariableData>>) o[3];
        
        ArrayList<ComponentData> list = components.get(activityName);
        
        if (list == null) {
            list = new ArrayList<ComponentData>();
        } else {
            list = new ArrayList<ComponentData>(list);
        }
        
        if (data.mainData != null && data.mainData.length() > 0) {
            for (int i = list.size() - 1; i >= 0; i--) {
                ComponentData existing = list.get(i);
                if (existing != null && data.mainData.equals(existing.mainData)) {
                    list.remove(i);
                }
            }
        }
        list.add(data);
        
        components.put(activityName, list);
        
        writeAll(blocks, functions, components, variables);
    }
    
    public static void saveComponents(final String activityName,
    final ArrayList<ComponentData> list) {
        
        Object[] o = readAll();
        
        HashMap<String, ArrayList<BlockBean>> blocks =
        (HashMap<String, ArrayList<BlockBean>>) o[0];
        
        HashMap<String, ArrayList<Pair<String, String>>> functions =
        (HashMap<String, ArrayList<Pair<String, String>>>) o[1];
        
        HashMap<String, ArrayList<ComponentData>> components =
        (HashMap<String, ArrayList<ComponentData>>) o[2];
        
        HashMap<String, ArrayList<VariableData>> variables =
        (HashMap<String, ArrayList<VariableData>>) o[3];
        
        components.put(activityName, list);
        
        writeAll(blocks, functions, components, variables);
    }
    
    
    public static ArrayList<ComponentData> loadComponents(String activityName) {
        
        Object[] o = readAll();
        
        HashMap<String, ArrayList<ComponentData>> components =
        (HashMap<String, ArrayList<ComponentData>>) o[2];
        
        ArrayList<ComponentData> list = components.get(activityName);
        
        if (list == null) {
            return new ArrayList<ComponentData>();
        }
        
        return list;
    }
    
    
    public static ComponentData loadComponent(String key) {
        ArrayList<ComponentData> list = loadComponents(key);
        
        if (list.size() > 0) {
            return list.get(0);
        }
        
        return new ComponentData(0, "", "");
        
    }
    
    public static void clearComponents(String activityName) {
        Object[] o = readAll();
        
        HashMap<String, ArrayList<BlockBean>> blocks =
            (HashMap<String, ArrayList<BlockBean>>) o[0];
        
        HashMap<String, ArrayList<Pair<String, String>>> functions =
            (HashMap<String, ArrayList<Pair<String, String>>>) o[1];
        
        HashMap<String, ArrayList<ComponentData>> components =
            (HashMap<String, ArrayList<ComponentData>>) o[2];
        
        HashMap<String, ArrayList<VariableData>> variables =
            (HashMap<String, ArrayList<VariableData>>) o[3];
        
        components.put(activityName, new ArrayList<ComponentData>());
        
        writeAll(blocks, functions, components, variables);
    }

    
    public static void saveComponentEvent(String componentId, String eventName) {
        Object[] o = readAll();
        
        HashMap<String, ArrayList<BlockBean>> blocks =
            (HashMap<String, ArrayList<BlockBean>>) o[0];
        
        HashMap<String, ArrayList<Pair<String, String>>> functions =
            (HashMap<String, ArrayList<Pair<String, String>>>) o[1];
        
        HashMap<String, ArrayList<ComponentData>> components =
            (HashMap<String, ArrayList<ComponentData>>) o[2];
        
        HashMap<String, ArrayList<VariableData>> variables =
            (HashMap<String, ArrayList<VariableData>>) o[3];
        
        String eventsKey = "component_events_" + componentId;
        ArrayList<Pair<String, String>> eventsList = functions.get(eventsKey);
        
        if (eventsList == null) {
            eventsList = new ArrayList<Pair<String, String>>();
        }
        
        boolean exists = false;
        for (int i = 0; i < eventsList.size(); i++) {
            Pair<String, String> p = eventsList.get(i);
            if (p.second.equals(eventName)) {
                exists = true;
                break;
            }
        }
        
        if (!exists) {
            eventsList.add(new Pair<String, String>("event", eventName));
            functions.put(eventsKey, eventsList);
            writeAll(blocks, functions, components, variables);
        }
    }
    
    public static ArrayList<String> loadComponentEvents(String componentId) {
        Object[] o = readAll();
        
        HashMap<String, ArrayList<Pair<String, String>>> functions =
            (HashMap<String, ArrayList<Pair<String, String>>>) o[1];
        
        String eventsKey = "component_events_" + componentId;
        ArrayList<Pair<String, String>> eventsList = functions.get(eventsKey);
        
        ArrayList<String> result = new ArrayList<String>();
        
        if (eventsList != null) {
            for (int i = 0; i < eventsList.size(); i++) {
                Pair<String, String> p = eventsList.get(i);
                result.add(p.second);
            }
        }
        
        return result;
    }
    
    public static void removeComponent(String activityName, String componentId) {
        Object[] o = readAll();
        
        HashMap<String, ArrayList<BlockBean>> blocks =
            (HashMap<String, ArrayList<BlockBean>>) o[0];
        
        HashMap<String, ArrayList<Pair<String, String>>> functions =
            (HashMap<String, ArrayList<Pair<String, String>>>) o[1];
        
        HashMap<String, ArrayList<ComponentData>> components =
            (HashMap<String, ArrayList<ComponentData>>) o[2];
        
        HashMap<String, ArrayList<VariableData>> variables =
            (HashMap<String, ArrayList<VariableData>>) o[3];
        
        ArrayList<ComponentData> list = components.get(activityName);
        
        if (list != null) {
            ArrayList<ComponentData> newList = new ArrayList<ComponentData>();
            
            for (int i = 0; i < list.size(); i++) {
                ComponentData data = list.get(i);
                
                if (!data.mainData.equals(componentId)) {
                    newList.add(data);
                }
            }
            
            components.put(activityName, newList);
            writeAll(blocks, functions, components, variables);
            
            String eventsKey = "component_events_" + componentId;
            functions.remove(eventsKey);
            
            writeAll(blocks, functions, components, variables);
        }
    }
    
    public static void updateComponent(String activityName, ComponentData data) {
        Object[] o = readAll();
        
        HashMap<String, ArrayList<BlockBean>> blocks =
            (HashMap<String, ArrayList<BlockBean>>) o[0];
        
        HashMap<String, ArrayList<Pair<String, String>>> functions =
            (HashMap<String, ArrayList<Pair<String, String>>>) o[1];
        
        HashMap<String, ArrayList<ComponentData>> components =
            (HashMap<String, ArrayList<ComponentData>>) o[2];
        
        HashMap<String, ArrayList<VariableData>> variables =
            (HashMap<String, ArrayList<VariableData>>) o[3];
        
        ArrayList<ComponentData> list = components.get(activityName);
        
        if (list == null) {
            list = new ArrayList<ComponentData>();
        }
        
        boolean found = false;
        for (int i = 0; i < list.size(); i++) {
            ComponentData existing = list.get(i);
            if (existing.mainData.equals(data.mainData)) {
                list.set(i, data);
                found = true;
                break;
            }
        }
        
        if (!found) {
            list.add(data);
        }
        
        components.put(activityName, list);
        writeAll(blocks, functions, components, variables);
    }
    
    public static ComponentData getComponent(String activityName, String componentId) {
        ArrayList<ComponentData> list = loadComponents(activityName);
        
        for (int i = 0; i < list.size(); i++) {
            ComponentData data = list.get(i);
            if (data.mainData.equals(componentId)) {
                return data;
            }
        }
        
        return null;
    }
    
    public static boolean componentExists(String activityName, String componentId) {
        return getComponent(activityName, componentId) != null;
    }
    
    public static ArrayList<ComponentData> getComponentsByType(String activityName, int type) {
        ArrayList<ComponentData> list = loadComponents(activityName);
        ArrayList<ComponentData> result = new ArrayList<ComponentData>();
        
        for (int i = 0; i < list.size(); i++) {
            ComponentData data = list.get(i);
            if (data.type == type) {
                result.add(data);
            }
        }
        
        return result;
    }
    
    public static int getComponentCount(String activityName) {
        ArrayList<ComponentData> list = loadComponents(activityName);
        return list.size();
    }


    public static void replaceComponents(String activityName, ArrayList<ComponentData> newList) {
        Object[] o = readAll();
        
        HashMap<String, ArrayList<BlockBean>> blocks =
            (HashMap<String, ArrayList<BlockBean>>) o[0];
        
        HashMap<String, ArrayList<Pair<String, String>>> functions =
            (HashMap<String, ArrayList<Pair<String, String>>>) o[1];
        
        HashMap<String, ArrayList<ComponentData>> components =
            (HashMap<String, ArrayList<ComponentData>>) o[2];
        
        HashMap<String, ArrayList<VariableData>> variables =
            (HashMap<String, ArrayList<VariableData>>) o[3];
        
        components.put(activityName, newList != null ? newList : new ArrayList<ComponentData>());
        
        writeAll(blocks, functions, components, variables);
        
        Log.d("BlockStorage", "Replaced components for: " + activityName + " (count: " + 
            (newList != null ? newList.size() : 0) + ")");
    }

    public static void syncComponentsFromMaCache(String activityName, ArrayList<ComponentData> maComponents) {
        if (maComponents == null) {
            clearComponents(activityName);
            return;
        }
        
        ArrayList<ComponentData> current = loadComponents(activityName);
        
        HashMap<String, ComponentData> currentMap = new HashMap<String, ComponentData>();
        for (ComponentData data : current) {
            currentMap.put(data.mainData, data);
        }
        
        boolean hasChanges = false;
        
        for (ComponentData data : current) {
            boolean stillExists = false;
            for (ComponentData maData : maComponents) {
                if (maData.mainData.equals(data.mainData)) {
                    stillExists = true;
                    break;
                }
            }
            if (!stillExists) {
                hasChanges = true;
                break;
            }
        }
        
        if (!hasChanges) {
            for (ComponentData maData : maComponents) {
                if (!currentMap.containsKey(maData.mainData)) {
                    hasChanges = true;
                    break;
                }
            }
        }
        
        if (hasChanges) {
            replaceComponents(activityName, maComponents);
            Log.d("BlockStorage", "Synced components from ma cache for: " + activityName);
        } else {
            Log.d("BlockStorage", "No changes needed for: " + activityName);
        }
    }

    public static ArrayList<String> getActivityNamesWithComponents() {
        Object[] o = readAll();
        HashMap<String, ArrayList<ComponentData>> components =
            (HashMap<String, ArrayList<ComponentData>>) o[2];
        
        ArrayList<String> result = new ArrayList<String>();
        
        for (Map.Entry<String, ArrayList<ComponentData>> entry : components.entrySet()) {
            if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                result.add(entry.getKey());
            }
        }
        
        return result;
    }

    public static HashMap<String, Integer> getComponentCounts() {
        Object[] o = readAll();
        HashMap<String, ArrayList<ComponentData>> components =
            (HashMap<String, ArrayList<ComponentData>>) o[2];
        
        HashMap<String, Integer> result = new HashMap<String, Integer>();
        
        for (Map.Entry<String, ArrayList<ComponentData>> entry : components.entrySet()) {
            int count = entry.getValue() != null ? entry.getValue().size() : 0;
            result.put(entry.getKey(), count);
        }
        
        return result;
    }

    public static boolean hasAnyComponents() {
        Object[] o = readAll();
        HashMap<String, ArrayList<ComponentData>> components =
            (HashMap<String, ArrayList<ComponentData>>) o[2];
        
        for (ArrayList<ComponentData> list : components.values()) {
            if (list != null && !list.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static void debugPrintComponents() {
        Object[] o = readAll();
        HashMap<String, ArrayList<ComponentData>> components =
            (HashMap<String, ArrayList<ComponentData>>) o[2];
        
        Log.d("BlockStorage", "=== COMPONENT DEBUG ===");
        Log.d("BlockStorage", "Total activities: " + components.size());
        
        for (Map.Entry<String, ArrayList<ComponentData>> entry : components.entrySet()) {
            String activity = entry.getKey();
            ArrayList<ComponentData> list = entry.getValue();
            Log.d("BlockStorage", "Activity: " + activity + " -> " + 
                (list != null ? list.size() : 0) + " components");
            
            if (list != null) {
                for (ComponentData data : list) {
                    Log.d("BlockStorage", "  - " + data.mainData + 
                        " (type: " + data.type + ", extra: " + data.extraData + ")");
                }
            }
        }
        Log.d("BlockStorage", "=== END DEBUG ===");
    }
}