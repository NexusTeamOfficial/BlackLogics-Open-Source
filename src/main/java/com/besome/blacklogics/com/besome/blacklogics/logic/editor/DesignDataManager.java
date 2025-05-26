package com.besome.blacklogics.logic.editor;

import android.content.Context;
import android.util.Pair;
import android.os.Environment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DesignDataManager {
	public static String sc_id;
	public static final int VAR_TYPE_BOOLEAN = 0;
	public static final int VAR_TYPE_INT = 1;
	public static final int VAR_TYPE_STRING = 2;
	public static boolean isInitialized = true;
	protected static Map<String, HashMap<String, ArrayList<BlockBean>>> mapBlocks;
	//  protected static Map<String, ArrayList<ComponentBean>> mapComponents;
	protected static Map<String, ArrayList<BlockBean>> mapCopiedBlocks;
	//   protected static Map<String, ArrayList<EventBean>> mapEvents;
	protected static Map<String, ArrayList<Pair<String, String>>> mapFunctions;
	protected static Map<String, ArrayList<Pair<Integer, String>>> mapLists;
	protected static Map<String, ArrayList<Pair<Integer, String>>> mapVariables;
	//   protected static Map<String, ArrayList<ViewBean>> mapViews;
	public static SharedPreferenceUtil prefLogic;
	// public static SharedPreferenceUtil prefView;
	
	public static void setSc(String sc_id) {
		DesignDataManager.sc_id = sc_id;
	}
	
	
	public static void addFunction(String str, String str2, String str3) {
		Pair pair = new Pair(str2, str3);
		if (!mapFunctions.containsKey(str)) {
			mapFunctions.put(str, new ArrayList());
		}
		((ArrayList) mapFunctions.get(str)).add(pair);
		
		saveBlocksToFile(null, "data", "", null, sc_id);
		
	}
	
	public static void addList(String str, int i, String str2) {
		Pair pair = new Pair(Integer.valueOf(i), str2);
		if (!mapLists.containsKey(str)) {
			mapLists.put(str, new ArrayList());
		}
		((ArrayList) mapLists.get(str)).add(pair);
	}
	
	public static void addVariable(String str, int i, String str2) {
		Pair pair = new Pair(Integer.valueOf(i), str2);
		if (!mapVariables.containsKey(str)) {
			mapVariables.put(str, new ArrayList());
		}
		((ArrayList) mapVariables.get(str)).add(pair);
		
		saveBlocksToFile(null, "data", "", null, sc_id);
	}
	/*
	public static void addView(String str, ViewBean viewBean) {
	if (!mapViews.containsKey(str)) {
	mapViews.put(str, new ArrayList());
	}
	((ArrayList) mapViews.get(str)).add(viewBean);
	}
	*/
	public static void clearClipboard(String str) {
		if (mapCopiedBlocks.containsKey(str) && ((ArrayList) mapCopiedBlocks.get(str)) != null) {
			((ArrayList) mapCopiedBlocks.get(str)).clear();
		}
	}
	
	public static void copyBlocks(String str, ArrayList<Block> arrayList) {
		clearClipboard(str);
		ArrayList arrayList2 = new ArrayList();
		Iterator it = arrayList.iterator();
		while (it.hasNext()) {
			Block block = (Block) it.next();
			BlockBean blockBean = new BlockBean();
			BlockBean bean = block.getBean();
			blockBean.copy(bean);
			Object[] objArr = new Object[VAR_TYPE_INT];
			objArr[VAR_TYPE_BOOLEAN] = Integer.valueOf(bean.id);
			blockBean.id = String.format("99%06d", objArr);
			if (bean.subStack1 > 0) {
				blockBean.subStack1 = bean.subStack1 + 99000000;
			}
			if (bean.subStack2 > 0) {
				blockBean.subStack2 = bean.subStack2 + 99000000;
			}
			if (bean.nextBlock > 0) {
				blockBean.nextBlock = bean.nextBlock + 99000000;
			}
			blockBean.parameters.clear();
			Iterator it2 = bean.parameters.iterator();
			while (it2.hasNext()) {
				String str2 = (String) it2.next();
				if (str2.length() <= VAR_TYPE_INT || str2.charAt(VAR_TYPE_BOOLEAN) != '@') {
					blockBean.parameters.add(str2);
				} else {
					Object[] objArr2 = new Object[VAR_TYPE_INT];
					objArr2[VAR_TYPE_BOOLEAN] = Integer.valueOf(str2.substring(VAR_TYPE_INT));
					blockBean.parameters.add('@' + String.format("99%06d", objArr2));
				}
			}
			arrayList2.add(blockBean);
		}
		mapCopiedBlocks.put(str, arrayList2);
	}
	
	public static Map<String, ArrayList<BlockBean>> getAllBlocks(String str) {
		return !mapBlocks.containsKey(str) ? new HashMap() : (Map) mapBlocks.get(str);
	}
	
	public static ArrayList<String> getAllLists(String str) {
		ArrayList<String> arrayList = new ArrayList();
		if (mapLists.containsKey(str)) {
			ArrayList arrayList2 = (ArrayList) mapLists.get(str);
			if (arrayList2 != null) {
				Iterator it = arrayList2.iterator();
				while (it.hasNext()) {
					arrayList.add(((Pair<Integer, String>) it.next()).second);
				}
			}
		}
		return arrayList;
	}
	
	public static ArrayList<String> getAllNamesForValid(String str) {
		ArrayList<String> arrayList = new ArrayList();
		Iterator it = getVariables(str).iterator();
		while (it.hasNext()) {
			arrayList.add(((Pair<Integer, String>) it.next()).second);
		}
		it = getLists(str).iterator();
		while (it.hasNext()) {
			arrayList.add(((Pair<Integer, String>) it.next()).second);
		}
		it = getFunctions(str).iterator();
		while (it.hasNext()) {
			arrayList.add(((Pair<String, String>) it.next()).first);
		}
		/*   it = getAllViews(ProjectFileManager.getXmlNameFromJava(str)).iterator();
		while (it.hasNext()) {
		arrayList.add(SourceUtil.getVariableNameFromId(((ViewBean) it.next()).id));
		}
		it = getComponents(str).iterator();
		while (it.hasNext()) {
		arrayList.add(((ComponentBean) it.next()).componentId);
		}*/
		return arrayList;
	}
	
	
	
	public static ArrayList<BlockBean> getBlocks(String str, String str2) {
		if (!mapBlocks.containsKey(str)) {
			return new ArrayList();
		}
		Map map = (Map) mapBlocks.get(str);
		return map == null ? new ArrayList() : !map.containsKey(str2) ? new ArrayList() : (ArrayList) map.get(str2);
	}
	
	public static ArrayList<BlockBean> getClipboard(String str) {
		return !mapCopiedBlocks.containsKey(str) ? new ArrayList() : (ArrayList) mapCopiedBlocks.get(str);
	}
	
	public static String getFunctionSpec(String str, String str2) {
		if (!mapFunctions.containsKey(str)) {
			return "";
		}
		ArrayList arrayList = (ArrayList) mapFunctions.get(str);
		if (arrayList == null) {
			return "";
		}
		Iterator it = arrayList.iterator();
		while (it.hasNext()) {
			Pair pair = (Pair) it.next();
			if (((String) pair.first).equals(str2)) {
				return (String) pair.second;
			}
		}
		return "";
	}
	
	public static ArrayList<Pair<String, String>> getFunctions(String str) {
		return mapFunctions.containsKey(str) ? (ArrayList) mapFunctions.get(str) : new ArrayList();
	}
	
	public static ArrayList<Pair<Integer, String>> getLists(String str) {
		return mapLists.containsKey(str) ? (ArrayList) mapLists.get(str) : new ArrayList();
	}
	
	public static ArrayList<String> getListsByType(String str, int i) {
		ArrayList<String> arrayList = new ArrayList();
		if (mapLists.containsKey(str)) {
			ArrayList arrayList2 = (ArrayList) mapLists.get(str);
			if (arrayList2 != null) {
				Iterator it = arrayList2.iterator();
				while (it.hasNext()) {
					Pair<Integer, String> pair = (Pair<Integer, String>) it.next();
					if (((Integer) pair.first).intValue() == i) {
						arrayList.add(pair.second);
					}
				}
			}
		}
		return arrayList;
	}
	
	
	public static ArrayList<Pair<Integer, String>> getVariables(String str) {
		return mapVariables.containsKey(str) ? (ArrayList) mapVariables.get(str) : new ArrayList();
	}
	
	public static ArrayList<String> getVariablesByType(String str, int i) {
		ArrayList<String> arrayList = new ArrayList();
		Iterator it = ((ArrayList) mapVariables.get(str)).iterator();
		while (it.hasNext()) {
			Pair<Integer, String> pair = (Pair<Integer, String>) it.next();
			if (((Integer) pair.first).intValue() == i) {
				arrayList.add(pair.second);
			}
		}
		return arrayList;
	}
	
	
	
	protected static void initMaps() {
		/*     if (mapViews != null) {
		mapViews.clear();
		}*/
		if (mapBlocks != null) {
			mapBlocks.clear();
		}
		if (mapVariables != null) {
			mapVariables.clear();
		}
		if (mapLists != null) {
			mapLists.clear();
		}
		/*    if (mapComponents != null) {
		mapComponents.clear();
		}
		if (mapEvents != null) {
		mapEvents.clear();
		}*/
		//  mapViews = new HashMap();
		mapBlocks = new HashMap();
		mapVariables = new HashMap();
		mapLists = new HashMap();
		mapFunctions = new HashMap();
		//      mapComponents = new HashMap();
		//    mapEvents = new HashMap();
		mapCopiedBlocks = new HashMap();
	}
	
	public static void initialize(Context context, String str) {
		initMaps();
		//    prefView = new SharedPreferenceUtil(context, "P19");
		prefLogic = new SharedPreferenceUtil(context, "P20");
		isInitialized = true;
		
		loadBlocksFromFile(context, "data", "", sc_id);
	}
	
	public static boolean isExistClipboard(String str) {
		if (!mapCopiedBlocks.containsKey(str) || mapCopiedBlocks.get(str) == null) {
			return false;
		}
		return ((ArrayList) mapCopiedBlocks.get(str)).size() > 0;
	}
	
	public static boolean isExistDefinedBlock(String str, String str2) {
		HashMap<String, ArrayList<BlockBean>> map = mapBlocks.get(str);
		if (map == null) {
			return false;
		}
		for (Entry entry : map.entrySet()) {
			if (!((String) entry.getKey()).equals(str2 + "_" + "moreBlock")) {
				Iterator it = ((ArrayList) entry.getValue()).iterator();
				while (it.hasNext()) {
					BlockBean blockBean = (BlockBean) it.next();
					if (blockBean.opCode.equals("definedFunc")) {
						int indexOf = blockBean.spec.indexOf(" ");
						if ((indexOf > 0 ? blockBean.spec.substring(VAR_TYPE_BOOLEAN, indexOf) : blockBean.spec).equals(str2)) {
							return true;
						}
					}
				}
				continue;
			}
		}
		return false;
	}
	
	public static boolean isExistListBlock(String str, String str2, String str3) {
		HashMap<String, ArrayList<BlockBean>> map = mapBlocks.get(str);
		if (map == null) {
			return false;
		}
		for (Entry entry : map.entrySet()) {
			if (!((String) entry.getKey()).equals(str3)) {
				Iterator it = ((ArrayList) entry.getValue()).iterator();
				while (it.hasNext()) {
					BlockBean blockBean = (BlockBean) it.next();
					String str4 = blockBean.opCode;
					int i = -1;
					switch (str4.hashCode()) {
						case -1384861688:
						if (str4.equals("getAtListInt")) {
							i = 6;
							break;
						}
						break;
						case -1384851894:
						if (str4.equals("getAtListStr")) {
							i = 7;
							break;
						}
						break;
						case -1271141237:
						if (str4.equals("clearList")) {
							i = 3;
							break;
						}
						break;
						case -329562760:
						if (str4.equals("insertListInt")) {
							i = 11;
							break;
						}
						break;
						case -329552966:
						if (str4.equals("insertListStr")) {
							i = 12;
							break;
						}
						break;
						case -96313603:
						if (str4.equals("containListInt")) {
							i = VAR_TYPE_INT;
							break;
						}
						break;
						case -96303809:
						if (str4.equals("containListStr")) {
							i = VAR_TYPE_STRING;
							break;
						}
						break;
						case 762282303:
						if (str4.equals("indexListInt")) {
							i = 8;
							break;
						}
						break;
						case 762292097:
						if (str4.equals("indexListStr")) {
							i = 9;
							break;
						}
						break;
						case 1160674468:
						if (str4.equals("lengthList")) {
							i = VAR_TYPE_BOOLEAN;
							break;
						}
						break;
						case 1764351209:
						if (str4.equals("deleteList")) {
							i = 10;
							break;
						}
						break;
						case 2090179216:
						if (str4.equals("addListInt")) {
							i = 4;
							break;
						}
						break;
						case 2090189010:
						if (str4.equals("addListStr")) {
							i = 5;
							break;
						}
						break;
					}
					switch (i) {
						case VAR_TYPE_BOOLEAN /*0*/:
						case VAR_TYPE_INT /*1*/:
						case VAR_TYPE_STRING /*2*/:
						case 3:
						if (!((String) blockBean.parameters.get(VAR_TYPE_BOOLEAN)).equals(str2)) {
							break;
						}
						return true;
						case 4:
						case 5:
						case 6:
						case 7:
						case 8:
						case 9:
						case 10:
						if (!((String) blockBean.parameters.get(VAR_TYPE_INT)).equals(str2)) {
							break;
						}
						return true;
						case 11:
						case 12:
						if (!((String) blockBean.parameters.get(VAR_TYPE_STRING)).equals(str2)) {
							break;
						}
						return true;
						default:
						break;
					}
				}
				continue;
			}
		}
		return false;
	}
	
	public static boolean isExistVariableBlock(String str, String str2, String str3) {
		HashMap<String, ArrayList<BlockBean>> map = mapBlocks.get(str);
		if (map == null) {
			return false;
		}
		for (Entry entry : map.entrySet()) {
			if (!((String) entry.getKey()).equals(str3)) {
				Iterator it = ((ArrayList) entry.getValue()).iterator();
				while (it.hasNext()) {
					BlockBean blockBean = (BlockBean) it.next();
					String str4 = blockBean.opCode;
					int i = -1;
					switch (str4.hashCode()) {
						case -1920517885:
						if (str4.equals("setVarBoolean")) {
							i = VAR_TYPE_INT;
							break;
						}
						break;
						case -1377080719:
						if (str4.equals("decreaseInt")) {
							i = 5;
							break;
						}
						break;
						case -1249347599:
						if (str4.equals("getVar")) {
							i = VAR_TYPE_BOOLEAN;
							break;
						}
						break;
						case 657721930:
						if (str4.equals("setVarInt")) {
							i = VAR_TYPE_STRING;
							break;
						}
						break;
						case 754442829:
						if (str4.equals("increaseInt")) {
							i = 4;
							break;
						}
						break;
						case 845089750:
						if (str4.equals("setVarString")) {
							i = 3;
							break;
						}
						break;
					}
					switch (i) {
						case VAR_TYPE_BOOLEAN /*0*/:
						if (!blockBean.spec.equals(str2)) {
							break;
						}
						return true;
						case VAR_TYPE_INT /*1*/:
						case VAR_TYPE_STRING /*2*/:
						case 3:
						case 4:
						case 5:
						if (!((String) blockBean.parameters.get(VAR_TYPE_BOOLEAN)).equals(str2)) {
							break;
						}
						return true;
						default:
						break;
					}
				}
				continue;
			}
		}
		return false;
	}
	
	public static void loadSavedLogic() {
		HashMap<String, Object> readState = prefLogic.readState();
		if (readState != null && readState.size() > 0) {
			for (Entry entry : readState.entrySet()) {
				String str = (String) entry.getKey();
				int indexOf = str.indexOf("_");
				if (indexOf >= 0) {
					String substring = str.substring(VAR_TYPE_BOOLEAN, indexOf);
					String substring2 = str.substring(indexOf + VAR_TYPE_INT);
					String str2 = (String) entry.getValue();
					if (str2 != null && str2.length() > 0) {
						int i = -1;
						switch (substring2.hashCode()) {
							case -1291329255:
							if (substring2.equals("events")) {
								i = 4;
								break;
							}
							break;
							case -447446250:
							if (substring2.equals("components")) {
								i = 3;
								break;
							}
							break;
							case 116519:
							if (substring2.equals("var")) {
								i = VAR_TYPE_BOOLEAN;
								break;
							}
							break;
							case 3154628:
							if (substring2.equals("func")) {
								i = VAR_TYPE_STRING;
								break;
							}
							break;
							case 3322014:
							if (substring2.equals("list")) {
								i = VAR_TYPE_INT;
								break;
							}
							break;
						}
						switch (i) {
							case VAR_TYPE_BOOLEAN /*0*/:
							mapVariables.put(substring, parseVariableString(str2));
							break;
							case VAR_TYPE_INT /*1*/:
							mapLists.put(substring, parseListString(str2));
							break;
							case VAR_TYPE_STRING /*2*/:
							mapFunctions.put(substring, parseFunctionString(str2));
							break;
							case 3:
							//     mapComponents.put(substring, parseJsonToComponentArray(str2));
							break;
							case 4:
							//          mapEvents.put(substring, parseJsonToEventArray(str2));
							break;
							default:
							if (!mapBlocks.containsKey(substring)) {
								mapBlocks.put(substring, new HashMap());
							}
							((HashMap) mapBlocks.get(substring)).put(substring2, parseJsonToBlockArray(str2));
							break;
						}
					}
				}
			}
		}
	}
	
	
	
	public static ArrayList<Pair<String, String>> parseFunctionString(String str) {
		ArrayList<Pair<String, String>> arrayList = new ArrayList();
		while (true) {
			int indexOf = str.indexOf("\n");
			if (indexOf > 0) {
				String trim = str.substring(VAR_TYPE_BOOLEAN, indexOf).trim();
				if (trim.length() > 0 && trim.indexOf(":") >= 0) {
					arrayList.add(new Pair(trim.substring(VAR_TYPE_BOOLEAN, trim.indexOf(":")), trim.substring(trim.indexOf(":") + VAR_TYPE_INT)));
					if (indexOf >= str.length() - 1) {
						break;
					}
					str = str.substring(indexOf + VAR_TYPE_INT);
				} else {
					str = str.substring(indexOf + VAR_TYPE_INT);
				}
			} else {
				break;
			}
		}
		return arrayList;
	}
	
	protected static ArrayList<BlockBean> parseJsonToBlockArray(String str) {
		Gson gson = new Gson();
		ArrayList<BlockBean> arrayList = new ArrayList();
		while (str != null && str.length() > 0) {
			int indexOf = str.indexOf("\n");
			if (indexOf <= 0 || str.charAt(VAR_TYPE_BOOLEAN) != '{') {
				break;
			}
			arrayList.add(gson.fromJson(str.substring(VAR_TYPE_BOOLEAN, indexOf).trim(), BlockBean.class));
			if (indexOf >= str.length() - 1) {
				break;
			}
			str = str.substring(indexOf + VAR_TYPE_INT);
		}
		return arrayList;
	}
	
	
	public static ArrayList<Pair<Integer, String>> parseListString(String str) {
		ArrayList<Pair<Integer, String>> arrayList = new ArrayList();
		while (true) {
			int indexOf = str.indexOf("\n");
			if (indexOf > 0) {
				String trim = str.substring(VAR_TYPE_BOOLEAN, indexOf).trim();
				if (trim.length() > 0 && trim.indexOf(":") >= 0) {
					String substring = trim.substring(VAR_TYPE_BOOLEAN, trim.indexOf(":"));
					arrayList.add(new Pair(Integer.valueOf(substring), trim.substring(trim.indexOf(":") + VAR_TYPE_INT)));
					if (indexOf >= str.length() - 1) {
						break;
					}
					str = str.substring(indexOf + VAR_TYPE_INT);
				} else {
					str = str.substring(indexOf + VAR_TYPE_INT);
				}
			} else {
				break;
			}
		}
		return arrayList;
	}
	
	public static ArrayList<Pair<Integer, String>> parseVariableString(String str) {
		ArrayList<Pair<Integer, String>> arrayList = new ArrayList();
		while (true) {
			int indexOf = str.indexOf("\n");
			if (indexOf > 0) {
				String trim = str.substring(VAR_TYPE_BOOLEAN, indexOf).trim();
				if (trim.length() > 0 && trim.indexOf(":") >= 0) {
					String substring = trim.substring(VAR_TYPE_BOOLEAN, trim.indexOf(":"));
					arrayList.add(new Pair(Integer.valueOf(substring), trim.substring(trim.indexOf(":") + VAR_TYPE_INT)));
					if (indexOf >= str.length() - 1) {
						break;
					}
					str = str.substring(indexOf + VAR_TYPE_INT);
				} else {
					str = str.substring(indexOf + VAR_TYPE_INT);
				}
			} else {
				break;
			}
		}
		return arrayList;
	}
	
	
	public static void removeBlocks(String str, String str2) {
		if (mapBlocks.containsKey(str)) {
			Map map = (Map) mapBlocks.get(str);
			if (map != null && map.containsKey(str2)) {
				map.remove(str2);
			}
		}
	}
	
	public static void removeFunction(String str, String str2) {
		if (mapFunctions.containsKey(str)) {
			ArrayList arrayList = (ArrayList) mapFunctions.get(str);
			if (arrayList != null) {
				Iterator it = arrayList.iterator();
				while (it.hasNext()) {
					Pair pair = (Pair) it.next();
					if (((String) pair.first).equals(str2)) {
						arrayList.remove(pair);
						break;
					}
				}
				if (((HashMap) mapBlocks.get(str)).containsKey(str2 + "_" + "moreBlock")) {
					((HashMap) mapBlocks.get(str)).remove(str2 + "_" + "moreBlock");
				}
			}
		}
	}
	
	public static void removeList(String str, String str2) {
		if (mapLists.containsKey(str)) {
			ArrayList arrayList = (ArrayList) mapLists.get(str);
			if (arrayList != null) {
				Iterator it = arrayList.iterator();
				while (it.hasNext()) {
					Pair pair = (Pair) it.next();
					if (((String) pair.second).equals(str2)) {
						arrayList.remove(pair);
						return;
					}
				}
			}
		}
	}
	
	public static void removeVariable(String str, String str2) {
		if (mapVariables.containsKey(str)) {
			ArrayList arrayList = (ArrayList) mapVariables.get(str);
			if (arrayList != null) {
				Iterator it = arrayList.iterator();
				while (it.hasNext()) {
					Pair pair = (Pair) it.next();
					if (((String) pair.second).equals(str2)) {
						arrayList.remove(pair);
						return;
					}
				}
			}
		}
	}
	
	
	public static void setBlocks(String str, String str2, ArrayList<BlockBean> arrayList) {
		if (!mapBlocks.containsKey(str)) {
			mapBlocks.put(str, new HashMap());
		}
		((Map) mapBlocks.get(str)).put(str2, arrayList);
	}
	
	public static String serializeBlocksToJson(ArrayList<BlockBean> blocks) {
		Gson gson = new Gson();
		return gson.toJson(blocks);
	}
	
	// Deserialize JSON to blocks
	public static ArrayList<BlockBean> deserializeBlocksFromJson(String json) {
		Gson gson = new Gson();
		if (json == null || json.isEmpty()) {
			return new ArrayList<>();
		}
		return gson.fromJson(json, new TypeToken<ArrayList<BlockBean>>(){}.getType());
	}
	
	// Serialize event map to JSON
	// Serialize mapBlocks to JSON
	public static String serializeEventMapToJson(Map<String, HashMap<String, ArrayList<BlockBean>>> eventMap) {
		Gson gson = new Gson();
		return gson.toJson(eventMap);
	}
	
	// Deserialize JSON to mapBlocks
	public static Map<String, HashMap<String, ArrayList<BlockBean>>> deserializeEventMapFromJson(String json) {
		Gson gson = new Gson();
		if (json == null || json.isEmpty()) {
			return new HashMap<>();
		}
		return gson.fromJson(json, new TypeToken<Map<String, HashMap<String, ArrayList<BlockBean>>>>(){}.getType());
	}
	
	// Save blocks to a single file as Base64-encoded JSON with event names as keys
	public static void saveBlocksToFile(Context context, String filename, String key, ArrayList<BlockBean> blocks, String sc_id) {
		try {
			// Define the single file for all data
			File dir = new File(Environment.getExternalStorageDirectory() + "/.blacklogics/data/" + sc_id);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File file = new File(dir, filename);
			
			// Load existing data from the single file
			Map<String, Object> dataMap = new HashMap<>();
			if (file.exists()) {
				FileInputStream fis = new FileInputStream(file);
				byte[] data = new byte[(int) file.length()];
				fis.read(data);
				fis.close();
				String base64 = new String(data);
				String json = new String(Base64.getDecoder().decode(base64), StandardCharsets.UTF_8);
				dataMap = new Gson().fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
			}
			
			// Update or initialize the blocks map
			Map<String, HashMap<String, ArrayList<BlockBean>>> eventMap = dataMap.containsKey("blocks")
			? deserializeEventMapFromJson(new Gson().toJson(dataMap.get("blocks")))
			: new HashMap<>();
			
			// Update the nested HashMap for the given sc_id
			if (!eventMap.containsKey(sc_id)) {
				eventMap.put(sc_id, new HashMap<>());
			}
			eventMap.get(sc_id).put(key, blocks);
			
			// Store all maps in the dataMap
			dataMap.put("blocks", eventMap);
			dataMap.put("variables", mapVariables);
			dataMap.put("functions", mapFunctions); 
			
			// Serialize the entire data map to JSON
			String json = new Gson().toJson(dataMap);
			String base64 = Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(base64.getBytes());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Load blocks from the single file by event name
	public static ArrayList<BlockBean> loadBlocksFromFile(Context context, String filename, String key, String sc_id) {
		try {
			File file = new File(Environment.getExternalStorageDirectory() + "/.blacklogics/data/" + sc_id, filename);
			if (!file.exists()) {
				return new ArrayList<>();
			}
			FileInputStream fis = new FileInputStream(file);
			byte[] data = new byte[(int) file.length()];
			fis.read(data);
			fis.close();
			String base64 = new String(data);
			String json = new String(Base64.getDecoder().decode(base64), StandardCharsets.UTF_8);
			
			// Deserialize the entire data map
			Map<String, Object> dataMap = new Gson().fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
			
			// Load blocks
			mapBlocks = dataMap.containsKey("blocks")
			? deserializeEventMapFromJson(new Gson().toJson(dataMap.get("blocks")))
			: new HashMap<>();
			
			// Load variables
			mapVariables = dataMap.containsKey("variables")
			? deserializeVariableMapFromJson(new Gson().toJson(dataMap.get("variables")))
			: new HashMap<>();
			
			// Load functions
			mapFunctions = dataMap.containsKey("functions")
			? deserializeFunctionMapFromJson(new Gson().toJson(dataMap.get("functions")))
			: new HashMap<>();
			
			// Return the blocks for the specified sc_id and key
			return mapBlocks.containsKey(sc_id) && mapBlocks.get(sc_id).containsKey(key)
			? mapBlocks.get(sc_id).get(key)
			: new ArrayList<>();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	public static String serializeFunctionMapToJson(Map<String, ArrayList<Pair<String, String>>> functionMap) {
		Gson gson = new Gson();
		return gson.toJson(functionMap);
	}
	
	public static Map<String, ArrayList<Pair<String, String>>> deserializeFunctionMapFromJson(String json) {
		Gson gson = new Gson();
		if (json == null || json.isEmpty()) {
			return new HashMap<>();
		}
		return gson.fromJson(json, new TypeToken<Map<String, ArrayList<Pair<String, String>>>>(){}.getType());
	}
	
	public static String serializeVariableMapToJson(Map<String, ArrayList<Pair<Integer, String>>> variableMap) {
		Gson gson = new Gson();
		return gson.toJson(variableMap);
	}
	
	public static Map<String, ArrayList<Pair<Integer, String>>> deserializeVariableMapFromJson(String json) {
		Gson gson = new Gson();
		if (json == null || json.isEmpty()) {
			return new HashMap<>();
		}
		return gson.fromJson(json, new TypeToken<Map<String, ArrayList<Pair<Integer, String>>>>(){}.getType());
	}
}
