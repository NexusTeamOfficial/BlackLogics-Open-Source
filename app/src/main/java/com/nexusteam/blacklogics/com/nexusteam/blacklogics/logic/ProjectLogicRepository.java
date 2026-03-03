package com.nexusteam.blacklogics.logic;

import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ProjectLogicRepository {
    
    public static String projectPath = "";

    

    public static void setPath(String path) {
        ProjectLogicRepository.projectPath = path;
    }
    
    private static File getMainFile() {
        return new File(projectPath + "/project_logic.bin");
    }
    
    private static Map<String, Object> loadAll() {
        File file = getMainFile();
        if (!file.exists()) {
            return new HashMap<>();
        }
        
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] fileData = new byte[(int) file.length()];
            fis.read(fileData);
            

            byte[] decodedBytes = Base64.decode(fileData, Base64.DEFAULT);
            String json = new String(decodedBytes, StandardCharsets.UTF_8);
            
            Type type = new TypeToken<Map<String, Object>>() {}.getType();
            Map<String, Object> allData = new Gson().fromJson(json, type);
            
            return allData == null ? new HashMap<String, Object>() : (Map<String, Object>) allData;
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
    
    private static void saveAll(Map<String, Object> allData) {
        try {
            String json = new GsonBuilder().setPrettyPrinting().create().toJson(allData);
            byte[] jsonBytes = json.getBytes(StandardCharsets.UTF_8);
            

            byte[] encodedBytes = Base64.encode(jsonBytes, Base64.DEFAULT);
            
            File file = getMainFile();
            
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(encodedBytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void resetCache() {
    }
    
    public static void flushCache() {
    }
    

    public static void saveComponentLogic(String activityName,
    String componentName,
    String fieldName) {
        
        Map<String, Object> all = loadAll();
        
        Map<String, List<HashMap<String, String>>> components;
        if (all.containsKey("components")) {
            components = (Map<String, List<HashMap<String, String>>>) all.get("components");
        } else {
            components = new HashMap<String, List<HashMap<String, String>>>();
        }
        
        List<HashMap<String, String>> list;
        if (components.containsKey(activityName)) {
            list = components.get(activityName);
        } else {
            list = new ArrayList<HashMap<String, String>>();
        }
        
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("componentName", componentName);
        map.put("fieldName", fieldName);
        
        list.add(map);
        components.put(activityName, list);
        all.put("components", components);
        
        saveAll(all);
    }
    
    
    public static List<HashMap<String, String>> loadComponentLogic(String activityName) {
        Map<String, Object> all = loadAll();
        Object componentsObj = all.get("components");
        if (!(componentsObj instanceof Map)) return new ArrayList<>();
        
        Map<String, Object> components = (Map<String, Object>) componentsObj;
        Object listObj = components.get(activityName);
        if (!(listObj instanceof List)) return new ArrayList<>();
        
        List<?> rawList = (List<?>) listObj;
        List<HashMap<String, String>> result = new ArrayList<>();
        for (Object o : rawList) {
            if (o instanceof Map) {
                Map<?, ?> rawMap = (Map<?, ?>) o;
                HashMap<String, String> map = new HashMap<>();
                for (Map.Entry<?, ?> entry : rawMap.entrySet()) {
                    map.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
                }
                result.add(map);
            }
        }
        return result;
    }
    
    public static void removeComponentLogic(String activityName, String componentName, String fieldName) {
        Map<String, Object> all = loadAll();
        
        Object componentsObj = all.get("components");
        if (!(componentsObj instanceof Map)) return;
        Map<String, Object> components = (Map<String, Object>) componentsObj;
        
        Object listObj = components.get(activityName);
        if (!(listObj instanceof List)) return;
        List<?> rawList = (List<?>) listObj;
        
        List<HashMap<String, String>> newList = new ArrayList<>();
        for (Object o : rawList) {
            if (o instanceof Map) {
                Map<?, ?> comp = (Map<?, ?>) o;
                String cName = String.valueOf(comp.get("componentName"));
                String fName = String.valueOf(comp.get("fieldName"));
                if (!componentName.equals(cName) || !fieldName.equals(fName)) {
                    HashMap<String, String> safeMap = new HashMap<>();
                    for (Map.Entry<?, ?> entry : comp.entrySet()) {
                        safeMap.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
                    }
                    newList.add(safeMap);
                }
            }
        }
        
        if (newList.isEmpty()) {
            components.remove(activityName);
        } else {
            components.put(activityName, newList);
        }
        
        all.put("components", components);
        saveAll(all);
    }
    
    

    public static void saveVariable(String activityName,
    String typeName,
    String varName) {
        
        Map<String, Object> all = loadAll();
        
        Map<String, List<HashMap<String, String>>> variables;
        if (all.containsKey("variables")) {
            variables = (Map<String, List<HashMap<String, String>>>) all.get("variables");
        } else {
            variables = new HashMap<String, List<HashMap<String, String>>>();
        }
        
        List<HashMap<String, String>> list;
        if (variables.containsKey(activityName)) {
            list = variables.get(activityName);
        } else {
            list = new ArrayList<HashMap<String, String>>();
        }
        
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("varTypeName", typeName);
        data.put("varName", varName);
        
        list.add(data);
        variables.put(activityName, list);
        all.put("variables", variables);
        
        saveAll(all);
    }
    
    
    public static List<HashMap<String, String>> loadVariableLogic(String activityName) {
        Map<String, Object> all = loadAll();
        Object varsObj = all.get("variables");
        if (!(varsObj instanceof Map)) return new ArrayList<>();
        
        Map<String, Object> rawMap = (Map<String, Object>) varsObj;
        Object listObj = rawMap.get(activityName);
        if (!(listObj instanceof List)) return new ArrayList<>();
        
        List<?> rawList = (List<?>) listObj;
        List<HashMap<String, String>> result = new ArrayList<>();
        for (Object o : rawList) {
            if (o instanceof Map) {
                Map<?, ?> m = (Map<?, ?>) o;
                HashMap<String, String> clean = new HashMap<>();
                for (Map.Entry<?, ?> e : m.entrySet()) {
                    clean.put(String.valueOf(e.getKey()), String.valueOf(e.getValue()));
                }
                result.add(clean);
            }
        }
        return result;
    }
    
    public static void removeVariable(String activityName, String typeName, String varName) {
        Map<String, Object> all = loadAll();
        Object varsObj = all.get("variables");
        if (!(varsObj instanceof Map)) return; // nothing to remove
        
        Map<?, ?> rawVariables = (Map<?, ?>) varsObj;
        Object listObj = rawVariables.get(activityName);
        if (!(listObj instanceof List)) return; // no variables for this activity
        
        List<?> rawList = (List<?>) listObj;
        List<HashMap<String, String>> cleanedList = new ArrayList<>();
        

        for (Object o : rawList) {
            if (o instanceof Map) {
                Map<?, ?> m = (Map<?, ?>) o;
                String vType = String.valueOf(m.get("varTypeName"));
                String vName = String.valueOf(m.get("varName"));
                if (!typeName.equals(vType) || !varName.equals(vName)) {
                    HashMap<String, String> clean = new HashMap<>();
                    for (Map.Entry<?, ?> e : m.entrySet()) {
                        clean.put(String.valueOf(e.getKey()), String.valueOf(e.getValue()));
                    }
                    cleanedList.add(clean);
                }
            }
        }
        

        Map<String, Object> newVariables = new HashMap<>(rawVariables.size());
        for (Map.Entry<?, ?> e : rawVariables.entrySet()) {
            String key = String.valueOf(e.getKey());
            if (!key.equals(activityName)) {
                newVariables.put(key, e.getValue());
            }
        }
        
        if (!cleanedList.isEmpty()) {
            newVariables.put(activityName, cleanedList);
        }
        
        all.put("variables", newVariables);
        saveAll(all);
    }
    
    

    public static void saveBlockLogicForWidget(String activityName,
    String widgetId,
    String logic) {
        
        Map<String, Object> all = loadAll();
        
        Map<String, Map<String, String>> blocks;
        if (all.containsKey("blocks")) {
            blocks = (Map<String, Map<String, String>>) all.get("blocks");
        } else {
            blocks = new HashMap<String, Map<String, String>>();
        }
        
        Map<String, String> act;
        if (blocks.containsKey(activityName)) {
            act = blocks.get(activityName);
        } else {
            act = new HashMap<String, String>();
        }
        
        act.put(widgetId, logic);
        blocks.put(activityName, act);
        all.put("blocks", blocks);
        
        saveAll(all);
    }
    
    public static String getBlockLogicForWidget(String activityName,
    String widgetId) {
        
        Map<String, Object> all = loadAll();
        
        Map<String, Map<String, String>> blocks;
        if (all.containsKey("blocks")) {
            blocks = (Map<String, Map<String, String>>) all.get("blocks");
        } else {
            return "";
        }
        
        Map<String, String> act = blocks.get(activityName);
        if (act == null) return "";
        
        String logic = act.get(widgetId);
        return logic != null ? logic : "";
    }
    
    
    public static boolean isBlockLogicAvailable(String activityName,
    String widgetId) {
        
        Map<String, Object> all = loadAll();
        
        Map<String, Map<String, String>> blocks;
        if (!all.containsKey("blocks")) return false;
        
        blocks = (Map<String, Map<String, String>>) all.get("blocks");
        
        Map<String, String> act = blocks.get(activityName);
        if (act == null) return false;
        
        String logic = act.get(widgetId);
        return logic != null && logic.length() > 0;
    }
    
    public static void addFunction(
    String activityName,
    String functionName,
    String returnType,
    List<HashMap<String, String>> params
    ) {
        Map<String, Object> all = loadAll();
        

        Map<String, List<HashMap<String, Object>>> functions =
        new HashMap<String, List<HashMap<String, Object>>>();
        
        Object rawFn = all.get("functions");
        
        if (rawFn instanceof Map) {
            Map<?, ?> rawMap = (Map<?, ?>) rawFn;
            for (Map.Entry<?, ?> e : rawMap.entrySet()) {
                
                String act = String.valueOf(e.getKey());
                List<HashMap<String, Object>> fnList =
                new ArrayList<HashMap<String, Object>>();
                
                if (e.getValue() instanceof List) {
                    for (Object o : (List<?>) e.getValue()) {
                        if (o instanceof Map) {
                            HashMap<String, Object> clean =
                            new HashMap<String, Object>();
                            
                            for (Map.Entry<?, ?> m :
                            ((Map<?, ?>) o).entrySet()) {
                                clean.put(
                                String.valueOf(m.getKey()),
                                m.getValue()
                                );
                            }
                            fnList.add(clean);
                        }
                    }
                }
                functions.put(act, fnList);
            }
        }
        

        List<HashMap<String, Object>> list;
        if (functions.containsKey(activityName)) {
            list = functions.get(activityName);
        } else {
            list = new ArrayList<HashMap<String, Object>>();
        }
        
        boolean found = false;
        for (HashMap<String, Object> fn : list) {
            Object name = fn.get("functionName");
            if (functionName.equals(name)) {
                fn.put("returnType", returnType);
                fn.put("parameters", params);
                found = true;
                break;
            }
        }
        
        if (!found) {
            HashMap<String, Object> newFn =
            new HashMap<String, Object>();
            newFn.put("functionName", functionName);
            newFn.put("returnType", returnType);
            newFn.put("parameters", params);
            list.add(newFn);
        }
        
        functions.put(activityName, list);
        all.put("functions", functions);
        saveAll(all);
    }
    
    
    public static List<HashMap<String, Object>> loadFunctions(String activityName) {
        Map<String, Object> all = loadAll();
        
        Object rawFn = all.get("functions");
        if (!(rawFn instanceof Map)) return new ArrayList<>();
        
        Map<?, ?> fnMap = (Map<?, ?>) rawFn;
        Object rawList = fnMap.get(activityName);
        if (!(rawList instanceof List)) return new ArrayList<>();
        
        List<?> list = (List<?>) rawList;
        List<HashMap<String, Object>> result = new ArrayList<>();
        
        for (Object o : list) {
            if (o instanceof Map) {
                Map<?, ?> raw = (Map<?, ?>) o;
                HashMap<String, Object> clean = new HashMap<>();
                
                for (Map.Entry<?, ?> e : raw.entrySet()) {
                    Object val = e.getValue();
                    
                    if ("parameters".equals(String.valueOf(e.getKey()))
                    && val instanceof List) {
                        
                        List<HashMap<String, String>> params = new ArrayList<>();
                        
                        for (Object p : (List<?>) val) {
                            if (p instanceof Map) {
                                HashMap<String, String> cleanParam = new HashMap<>();
                                for (Map.Entry<?, ?> pe : ((Map<?, ?>) p).entrySet()) {
                                    cleanParam.put(
                                    String.valueOf(pe.getKey()),
                                    String.valueOf(pe.getValue())
                                    );
                                }
                                params.add(cleanParam);
                            }
                        }
                        clean.put("parameters", params);
                        
                    } else {
                        clean.put(String.valueOf(e.getKey()), val);
                    }
                }
                result.add(clean);
            }
        }
        return result;
    }
    
    

    public static Map<String, Map<String, String>> loadComponentEvents(String activityName) {
        
        Map<String, Map<String, String>> events =
        new LinkedHashMap<String, Map<String, String>>();
        
        Map<String, Object> all = loadAll();
        
        Map<String, Map<String, String>> blocks;
        if (all.containsKey("blocks")) {
            blocks = (Map<String, Map<String, String>>) all.get("blocks");
        } else {
            return events;
        }
        
        Map<String, String> activityBlocks = blocks.get(activityName);
        if (activityBlocks == null) return events;
        
        for (Map.Entry<String, String> entry : activityBlocks.entrySet()) {
            String widgetId = entry.getKey();
            String logic = entry.getValue();
            
            if (widgetId != null && widgetId.length() > 0
            && logic != null && logic.length() > 0) {
                
                Map<String, String> evMap =
                new LinkedHashMap<String, String>();
                evMap.put("default", logic);
                
                events.put(widgetId, evMap);
            }
        }
        return events;
    }
    
    

    public static Map<String, String> getLifecycleEventsLogic(String activityName) {
        
        Map<String, String> lifecycleEvents =
        new LinkedHashMap<String, String>();
        
        Map<String, Object> all = loadAll();
        
        if (!all.containsKey("blocks")) return lifecycleEvents;
        
        Map<String, Map<String, String>> blocks =
        (Map<String, Map<String, String>>) all.get("blocks");
        
        Map<String, String> activityBlocks = blocks.get(activityName);
        if (activityBlocks == null) return lifecycleEvents;
        
        for (Map.Entry<String, String> entry : activityBlocks.entrySet()) {
            String methodName = entry.getKey();
            String logic = entry.getValue();
            
            if (methodName != null) {
                methodName = methodName.trim();
                if (methodName.length() > 0 && methodName.startsWith("on")) {
                    lifecycleEvents.put(
                    methodName,
                    logic != null ? logic : ""
                    );
                }
            }
        }
        return lifecycleEvents;
    }
    

    public static Map<String, String> getWidgetClickListeners(String activityName) {
        
        Map<String, String> widgetEvents =
        new LinkedHashMap<String, String>();
        
        Map<String, Object> all = loadAll();
        
        if (!all.containsKey("blocks")) return widgetEvents;
        
        Map<String, Map<String, String>> blocks =
        (Map<String, Map<String, String>>) all.get("blocks");
        
        Map<String, String> activityBlocks = blocks.get(activityName);
        if (activityBlocks != null) {
            widgetEvents.putAll(activityBlocks);
        }
        return widgetEvents;
    }
    
    

    public static String getBlockLogicForEvent(String activityName,
    String widgetId,
    String eventName) {
        
        Map<String, Object> all = loadAll();
        
        if (!all.containsKey("blocks")) return "";
        
        Map<String, Map<String, String>> blocks =
        (Map<String, Map<String, String>>) all.get("blocks");
        
        Map<String, String> activityBlocks = blocks.get(activityName);
        if (activityBlocks == null) return "";
        
        String logic = activityBlocks.get(widgetId);
        return logic != null ? logic : "";
    }
    
    
    public static String getBlockLogic(String activityName, String widgetId) {
        return getBlockLogicForWidget(activityName, widgetId);
    }
    
    public static List<HashMap<String, String>> loadComponentFromName(String activityName, String componentName) {
        List<HashMap<String, String>> matched = new ArrayList<>();
        Map<String, Object> all = loadAll();
        Object componentsObj = all.get("components");
        

        if (!(componentsObj instanceof Map)) return matched;
        
        Map<String, Object> components = (Map<String, Object>) componentsObj;
        Object listObj = components.get(activityName);
        

        if (!(listObj instanceof List)) return matched;
        
        List<?> rawList = (List<?>) listObj;
        

        for (Object o : rawList) {
            if (o instanceof Map) {
                Map<?, ?> rawCompMap = (Map<?, ?>) o;
                

                HashMap<String, String> cleanComp = new HashMap<>();
                for (Map.Entry<?, ?> entry : rawCompMap.entrySet()) {
                    cleanComp.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
                }
                

                if (componentName.equals(cleanComp.get("componentName"))) {
                    matched.add(cleanComp);
                }
            }
        }
        return matched;
    }
    
    public static void saveBlockLogicForEvent(String activityName, String widgetId, String logic) {
        saveBlockLogicForWidget(activityName, widgetId, logic);
    }
    
    public static String getBlockLogics(String activityName) {
        
        Map<String, Object> all = loadAll();
        
        if (!all.containsKey("blocks")) return "";
        
        Map<String, Map<String, String>> blocks =
        (Map<String, Map<String, String>>) all.get("blocks");
        
        Map<String, String> act = blocks.get(activityName);
        if (act == null) return "";
        
        String logic = act.get("");
        return logic != null ? logic : "";
    }
    
    public static String getBlockLogicForEvent(String activityName, String widgetId) {
        return getBlockLogicForWidget(activityName, widgetId);
    }
    
    public static void saveComponentEventLogic(String activityName,
    String componentName,
    String fieldName,
    String eventName,
    String logic) {
        
        Map<String, Object> all = loadAll();
        
        Map<String, List<HashMap<String, Object>>> components;
        if (all.containsKey("components")) {
            components =
            (Map<String, List<HashMap<String, Object>>>) all.get("components");
        } else {
            components =
            new HashMap<String, List<HashMap<String, Object>>>();
        }
        
        List<HashMap<String, Object>> list;
        if (components.containsKey(activityName)) {
            list = components.get(activityName);
        } else {
            list = new ArrayList<HashMap<String, Object>>();
        }
        
        boolean found = false;
        
        for (HashMap<String, Object> comp : list) {
            Object f = comp.get("fieldName");
            if (f != null && f.equals(fieldName)) {
                
                Map<String, String> events;
                if (comp.containsKey("events")
                && comp.get("events") instanceof Map) {
                    events = (Map<String, String>) comp.get("events");
                } else {
                    events = new HashMap<String, String>();
                }
                
                events.put(eventName, logic);
                comp.put("events", events);
                found = true;
                break;
            }
        }
        
        if (!found) {
            HashMap<String, Object> newComp =
            new HashMap<String, Object>();
            
            newComp.put("fieldName", fieldName);
            newComp.put("componentName", componentName);
            
            Map<String, String> events =
            new HashMap<String, String>();
            events.put(eventName, logic);
            
            newComp.put("events", events);
            list.add(newComp);
        }
        
        components.put(activityName, list);
        all.put("components", components);
        saveAll(all);
    }
    
    public static String loadComponentEventLogic(String activityName,
    String fieldName,
    String eventName) {
        
        Map<String, Object> all = loadAll();
        
        if (!all.containsKey("components")) return "";
        
        Map<String, List<HashMap<String, Object>>> components =
        (Map<String, List<HashMap<String, Object>>>) all.get("components");
        
        List<HashMap<String, Object>> list = components.get(activityName);
        if (list == null) return "";
        
        for (HashMap<String, Object> comp : list) {
            Object f = comp.get("fieldName");
            if (f != null && f.equals(fieldName)) {
                
                Object ev = comp.get("events");
                if (ev instanceof Map) {
                    Map<String, String> events =
                    (Map<String, String>) ev;
                    
                    String logic = events.get(eventName);
                    return logic != null ? logic : "";
                }
            }
        }
        return "";
    }
    
    
    public static List<HashMap<String, String>> loadIntentComponents(String activityName) {
        return loadComponentFromName(activityName, "Intent");
    }
    
    
}
