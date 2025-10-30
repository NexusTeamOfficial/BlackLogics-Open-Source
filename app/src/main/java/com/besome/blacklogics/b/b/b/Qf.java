package b.b.b;

import android.util.Base64;
// import com.besome.blacklogics.FileUtil; // यह इम्पोर्ट आपके मूल कोड में था पर इस्तेमाल नहीं हुआ, इसे हटा दिया गया है
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

public class Qf {
	
	public static String projectPath = "";
	// private static final char[] PASSWORD = "NexusTeam2025".toCharArray(); // एन्क्रिप्शन हटाने के कारण PASSWORD की आवश्यकता नहीं है
	
	// ========== PATH ==========
	public static void setPath(String path) {
		Qf.projectPath = path;
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
			
			// Base64 डिकोडिंग
			byte[] decodedBytes = Base64.decode(fileData, Base64.DEFAULT);
			String json = new String(decodedBytes, StandardCharsets.UTF_8);
			
			Type type = new TypeToken<Map<String, Object>>() {}.getType();
			Map<String, Object> allData = new Gson().fromJson(json, type);
			
			return allData == null ? new HashMap<>() : allData;
		} catch (Exception e) {
			e.printStackTrace();
			return new HashMap<>();
		}
	}
	
	private static void saveAll(Map<String, Object> allData) {
		try {
			String json = new GsonBuilder().setPrettyPrinting().create().toJson(allData);
			byte[] jsonBytes = json.getBytes(StandardCharsets.UTF_8);
			
			// Base64 एन्कोडिंग
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
	
	// ==================== COMPONENT LOGIC ====================
	public static void saveComponentLogic(String activityName, String componentName, String fieldName) {
		Map<String, Object> all = loadAll();
		Map<String, List<HashMap<String, String>>> components =
		(Map<String, List<HashMap<String, String>>>) all.getOrDefault("components", new HashMap<>());
		
		List<HashMap<String, String>> list = components.getOrDefault(activityName, new ArrayList<>());
		HashMap<String, String> map = new HashMap<>();
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
	
	
	// ==================== VARIABLE LOGIC ====================
	public static void saveVariable(String activityName, String typeName, String varName) {
		Map<String, Object> all = loadAll();
		Map<String, List<HashMap<String, String>>> variables =
		(Map<String, List<HashMap<String, String>>>) all.getOrDefault("variables", new HashMap<>());
		
		List<HashMap<String, String>> list = variables.getOrDefault(activityName, new ArrayList<>());
		HashMap<String, String> data = new HashMap<>();
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
		
		// Copy only the maps that don't match the variable to remove
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
		
		// Update the map
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
	
	
	// ==================== BLOCK LOGIC ====================
	public static void saveBlockLogicForWidget(String activityName, String widgetId, String logic) {
		Map<String, Object> all = loadAll();
		Map<String, Map<String, String>> blocks =
		(Map<String, Map<String, String>>) all.getOrDefault("blocks", new HashMap<>());
		
		Map<String, String> act = blocks.getOrDefault(activityName, new HashMap<>());
		act.put(widgetId, logic);
		blocks.put(activityName, act);
		
		all.put("blocks", blocks);
		saveAll(all); 
	}
	
	public static String getBlockLogicForWidget(String activityName, String widgetId) {
		Map<String, Object> all = loadAll();
		Map<String, Map<String, String>> blocks =
		(Map<String, Map<String, String>>) all.getOrDefault("blocks", new HashMap<>());
		Map<String, String> act = blocks.get(activityName);
		if (act == null) return "";
		return act.getOrDefault(widgetId, "");
	}
	
	public static boolean isBlockLogicAvailable(String activityName, String widgetId) {
		Map<String, Object> all = loadAll();
		Map<String, Map<String, String>> blocks =
		(Map<String, Map<String, String>>) all.getOrDefault("blocks", new HashMap<>());
		Map<String, String> act = blocks.get(activityName);
		return act != null && act.containsKey(widgetId) && !act.get(widgetId).isEmpty();
	}
	
	// ==================== FUNCTION LOGIC ====================
	public static void addFunction(String activityName, String functionName, String returnType, List<HashMap<String, String>> params) {
		Map<String, Object> all = loadAll();
		Map<String, List<HashMap<String, Object>>> functions =
		(Map<String, List<HashMap<String, Object>>>) all.getOrDefault("functions", new HashMap<>());
		
		List<HashMap<String, Object>> list = functions.getOrDefault(activityName, new ArrayList<>());
		boolean found = false;
		
		for (HashMap<String, Object> fn : list) {
			if (fn.get("functionName").equals(functionName)) {
				fn.put("returnType", returnType);
				fn.put("parameters", params);
				found = true;
				break;
			}
		}
		
		if (!found) {
			HashMap<String, Object> newFn = new HashMap<>();
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
		Map<String, List<HashMap<String, Object>>> functions =
		(Map<String, List<HashMap<String, Object>>>) all.getOrDefault("functions", new HashMap<>());
		return functions.getOrDefault(activityName, new ArrayList<>());
	}
	
	// ==================== COMPONENT EVENTS ====================
	public static Map<String, Map<String, String>> loadComponentEvents(String activityName) {
		Map<String, Map<String, String>> events = new LinkedHashMap<>();
		Map<String, Object> all = loadAll();
		Map<String, Map<String, String>> blocks =
		(Map<String, Map<String, String>>) all.getOrDefault("blocks", new HashMap<>());
		Map<String, String> activityBlocks = blocks.get(activityName);
		if (activityBlocks == null) return events;
		
		for (Map.Entry<String, String> entry : activityBlocks.entrySet()) {
			String widgetId = entry.getKey();
			String logic = entry.getValue();
			if (!widgetId.isEmpty() && logic != null && !logic.isEmpty()) {
				// Split multiple events for same component
				Map<String, String> evMap = new LinkedHashMap<>();
				evMap.put("default", logic); // default single event
				events.put(widgetId, evMap);
			}
		}
		return events;
	}
	
	// ==================== LIFECYCLE EVENTS ====================
	public static Map<String, String> getLifecycleEventsLogic(String activityName) {
		Map<String, String> lifecycleEvents = new LinkedHashMap<>();
		Map<String, Object> all = loadAll();
		Map<String, Map<String, String>> blocks =
		(Map<String, Map<String, String>>) all.getOrDefault("blocks", new HashMap<>());
		Map<String, String> activityBlocks = blocks.get(activityName);
		if (activityBlocks == null) return lifecycleEvents;
		
		for (Map.Entry<String, String> entry : activityBlocks.entrySet()) {
			String methodName = entry.getKey().trim();
			if (!methodName.isEmpty() && methodName.startsWith("on")) {
				lifecycleEvents.put(methodName, entry.getValue() != null ? entry.getValue() : "");
			}
		}
		return lifecycleEvents;
	}
	
	// ==================== WIDGET CLICK LISTENERS ====================
	public static Map<String, String> getWidgetClickListeners(String activityName) {
		Map<String, String> widgetEvents = new LinkedHashMap<>();
		Map<String, Object> all = loadAll();
		Map<String, Map<String, String>> blocks =
		(Map<String, Map<String, String>>) all.getOrDefault("blocks", new HashMap<>());
		Map<String, String> activityBlocks = blocks.get(activityName);
		if (activityBlocks != null) widgetEvents.putAll(activityBlocks);
		return widgetEvents;
	}
	
	// ==================== GET SPECIFIC BLOCK LOGIC ====================
	public static String getBlockLogicForEvent(String activityName, String widgetId, String eventName) {
		Map<String, Object> all = loadAll();
		Map<String, Map<String, String>> blocks =
		(Map<String, Map<String, String>>) all.getOrDefault("blocks", new HashMap<>());
		Map<String, String> activityBlocks = blocks.get(activityName);
		if (activityBlocks != null && activityBlocks.containsKey(widgetId)) {
			return activityBlocks.get(widgetId);
		}
		return "";
	}
	
	public static String getBlockLogic(String activityName, String widgetId) {
		return getBlockLogicForWidget(activityName, widgetId);
	}
	
	public static List<HashMap<String, String>> loadComponentFromName(String activityName, String componentName) {
		List<HashMap<String, String>> matched = new ArrayList<>();
		Map<String, Object> all = loadAll();
		Object componentsObj = all.get("components");
		
		// 1. Check if "components" is a Map (The top level)
		if (!(componentsObj instanceof Map)) return matched;
		
		Map<String, Object> components = (Map<String, Object>) componentsObj;
		Object listObj = components.get(activityName);
		
		// 2. Check if activity data is a List (The inner list of components)
		if (!(listObj instanceof List)) return matched;
		
		List<?> rawList = (List<?>) listObj;
		
		// 3. Iterate and check/cast each inner Map (The component data)
		for (Object o : rawList) {
			if (o instanceof Map) {
				Map<?, ?> rawCompMap = (Map<?, ?>) o;
				
				// Build the clean Map<String, String>
				HashMap<String, String> cleanComp = new HashMap<>();
				for (Map.Entry<?, ?> entry : rawCompMap.entrySet()) {
					cleanComp.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
				}
				
				// Now, perform the filtering logic
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
		Map<String, Map<String, String>> blocks =
		(Map<String, Map<String, String>>) loadAll().getOrDefault("blocks", new HashMap<>());
		Map<String, String> act = blocks.get(activityName);
		if (act != null) return act.getOrDefault("", "");
		return "";
	}
	
	public static String getBlockLogicForEvent(String activityName, String widgetId) {
		return getBlockLogicForWidget(activityName, widgetId);
	}
	
	public static void saveComponentEventLogic(String activityName, String componentName, String fieldName, String eventName, String logic) {
		Map<String, Object> all = loadAll();
		Map<String, List<HashMap<String, Object>>> components =
		(Map<String, List<HashMap<String, Object>>>) all.getOrDefault("components", new HashMap<>());
		
		List<HashMap<String, Object>> list = components.getOrDefault(activityName, new ArrayList<>());
		boolean found = false;
		
		for (HashMap<String, Object> comp : list) {
			if (comp.get("fieldName").equals(fieldName)) {
				Map<String, String> events = (Map<String, String>) comp.getOrDefault("events", new HashMap<>());
				events.put(eventName, logic);
				comp.put("events", events);
				found = true;
				break;
			}
		}
		
		if (!found) {
			HashMap<String, Object> newComp = new HashMap<>();
			newComp.put("fieldName", fieldName);
			newComp.put("componentName", componentName);
			Map<String, String> events = new HashMap<>();
			events.put(eventName, logic);
			newComp.put("events", events);
			list.add(newComp);
		}
		
		components.put(activityName, list);
		all.put("components", components);
		saveAll(all); 
	}
	public static String loadComponentEventLogic(String activityName, String fieldName, String eventName) {
		Map<String, Object> all = loadAll();
		Map<String, List<HashMap<String, Object>>> components =
		(Map<String, List<HashMap<String, Object>>>) all.getOrDefault("components", new HashMap<>());
		
		List<HashMap<String, Object>> list = components.getOrDefault(activityName, new ArrayList<>());
		for (HashMap<String, Object> comp : list) {
			if (comp.get("fieldName").equals(fieldName)) {
				Map<String, String> events = (Map<String, String>) comp.get("events");
				if (events != null && events.containsKey(eventName)) {
					return events.get(eventName);
				}
			}
		}
		return "";
	}
	
	public static List<HashMap<String, String>> loadIntentComponents(String activityName) {
		return loadComponentFromName(activityName, "Intent");
	}
	
	
}
