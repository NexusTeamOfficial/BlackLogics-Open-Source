package com.nexusteam.internal;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SketchwareViewBridge {
	
	private static final String TAG       = "SketchwareViewBridge";
	private static final String AES_KEY   = "sketchwaresecure";
	private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
	private static final String CHARSET   = "UTF-8";
	private static final String OUT_FILE  = "root_layout.json";
	private static final String JSON_VER  = "1.0";
	
	private static final Handler mainHandler = new Handler(Looper.getMainLooper());
	
	
	public interface Callback {
		void onSuccess(String outputDirPath);
		void onError(Exception e);
	}
	
	
	public static void convert(
	final String viewFilePath,
	final String outputDirPath,
	final Callback callback) {
		
		new Thread(new Runnable() {
			public void run() {
				try {
					convertSync(viewFilePath, outputDirPath);
					mainHandler.post(new Runnable() {
						public void run() {
							callback.onSuccess(outputDirPath);
						}
					});
				} catch (final Exception e) {
					Log.e(TAG, "convert failed", e);
					mainHandler.post(new Runnable() {
						public void run() {
							callback.onError(e);
						}
					});
				}
			}
		}).start();
	}
	
	public static void convertSync(String viewFilePath, String outputDirPath) throws Exception {
		byte[] encrypted = readFile(viewFilePath);
		String raw = decrypt(encrypted);
		
		Map<String, List<String>> sections = splitSections(raw);
		
		JSONArray rootArray = new JSONArray();
		
		for (Map.Entry<String, List<String>> entry : sections.entrySet()) {
			String sectionKey      = entry.getKey();
			List<String> jsonLines = entry.getValue();
			
			List<JSONObject> rawNodes = new ArrayList<JSONObject>();
			for (int i = 0; i < jsonLines.size(); i++) {
				String line = jsonLines.get(i).trim();
				if (line.startsWith("{")) {
					try {
						rawNodes.add(new JSONObject(line));
					} catch (Exception e) {
						Log.w(TAG, "Skipping malformed view line: " + line);
					}
				}
			}
			
			String activityName = toActivityName(sectionKey);
			
			String xml = buildXml(rawNodes, activityName);
			
			JSONArray nestedData = buildNestedDataArray(rawNodes);
			
			JSONObject layoutData = new JSONObject();
			layoutData.put("version", JSON_VER);
			layoutData.put("data", nestedData);
			
			JSONObject layoutEntry = new JSONObject();
			layoutEntry.put("name",       activityName);
			layoutEntry.put("xml",        xml);
			layoutEntry.put("layoutData", layoutData);
			
			rootArray.put(layoutEntry);
		}
		
		File outDir = new File(outputDirPath);
		if (!outDir.exists()) outDir.mkdirs();
		
		String outPath = outputDirPath + File.separator + OUT_FILE;
		writeFile(outPath, rootArray.toString(2).getBytes(CHARSET));
		
		Log.d(TAG, "root_layout.json written → " + outPath
		+ " (" + rootArray.length() + " layouts)");
	}
	
	
	private static Map<String, List<String>> splitSections(String raw) {
		Map<String, List<String>> result = new LinkedHashMap<String, List<String>>();
		String currentKey = null;
		List<String> currentList = null;
		
		String[] lines = raw.split("\n");
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
	
	
	private static String buildXml(List<JSONObject> nodes, String layoutName) {
		if (nodes.isEmpty()) return "";
		
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		
		Map<String, List<JSONObject>> childMap = buildChildMap(nodes);
		
		List<JSONObject> roots = childMap.get("root");
		if (roots != null) {
			for (int i = 0; i < roots.size(); i++) {
				appendXmlNode(roots.get(i), childMap, sb, 0);
			}
		}
		
		return sb.toString();
	}
	
	private static Map<String, List<JSONObject>> buildChildMap(List<JSONObject> nodes) {
		Map<String, List<JSONObject>> childMap =
		new LinkedHashMap<String, List<JSONObject>>();
		
		for (int i = 0; i < nodes.size(); i++) {
			String id = nodes.get(i).optString("id", "");
			if (!childMap.containsKey(id)) {
				childMap.put(id, new ArrayList<JSONObject>());
			}
		}
		
		for (int i = 0; i < nodes.size(); i++) {
			JSONObject n = nodes.get(i);
			String parent = n.optString("parent", "root");
			if (!childMap.containsKey(parent)) {
				childMap.put(parent, new ArrayList<JSONObject>());
			}
			childMap.get(parent).add(n);
		}
		
		for (Map.Entry<String, List<JSONObject>> e : childMap.entrySet()) {
			List<JSONObject> children = e.getValue();
			for (int i = 0; i < children.size() - 1; i++) {
				for (int j = 0; j < children.size() - 1 - i; j++) {
					int idxA = children.get(j).optInt("index", 0);
					int idxB = children.get(j + 1).optInt("index", 0);
					if (idxA > idxB) {
						JSONObject tmp = children.get(j);
						children.set(j, children.get(j + 1));
						children.set(j + 1, tmp);
					}
				}
			}
		}
		
		return childMap;
	}
	
	private static void appendXmlNode(
	JSONObject node,
	Map<String, List<JSONObject>> childMap,
	StringBuilder sb,
	int depth) {
		
		String indent    = repeat("    ", depth);
		String tag       = resolveTag(node);
		String id        = node.optString("id", "");
		JSONObject layout = node.optJSONObject("layout");
		JSONObject text   = node.optJSONObject("text");
		JSONObject image  = node.optJSONObject("image");
		
		sb.append(indent).append("<").append(tag).append("\n");
		
		if (depth == 0) {
			sb.append(indent).append("    xmlns:android=\"http://schemas.android.com/apk/res/android\"\n");
			sb.append(indent).append("    xmlns:app=\"http://schemas.android.com/apk/res-auto\"\n");
		}
		
		if (!id.isEmpty()) {
			sb.append(indent).append("    android:id=\"@+id/").append(id).append("\"\n");
		}
		
		if (layout != null) {
			sb.append(indent).append("    android:layout_width=\"")
			.append(dimenToString(layout.optInt("width", -2))).append("\"\n");
			sb.append(indent).append("    android:layout_height=\"")
			.append(dimenToString(layout.optInt("height", -2))).append("\"\n");
			
			int bg = (int) layout.optDouble("backgroundColor", 16777215);
			if (bg != 16777215) {
				sb.append(indent).append("    android:background=\"")
				.append(intToHex(bg)).append("\"\n");
			}
			
			int padL = layout.optInt("paddingLeft",   0);
			int padR = layout.optInt("paddingRight",  0);
			int padT = layout.optInt("paddingTop",    0);
			int padB = layout.optInt("paddingBottom", 0);
			if (padL > 0) sb.append(indent).append("    android:paddingLeft=\"").append(padL).append("dp\"\n");
			if (padR > 0) sb.append(indent).append("    android:paddingRight=\"").append(padR).append("dp\"\n");
			if (padT > 0) sb.append(indent).append("    android:paddingTop=\"").append(padT).append("dp\"\n");
			if (padB > 0) sb.append(indent).append("    android:paddingBottom=\"").append(padB).append("dp\"\n");
			
			int marL = layout.optInt("marginLeft",   0);
			int marR = layout.optInt("marginRight",  0);
			int marT = layout.optInt("marginTop",    0);
			int marB = layout.optInt("marginBottom", 0);
			if (marL > 0) sb.append(indent).append("    android:layout_marginLeft=\"").append(marL).append("dp\"\n");
			if (marR > 0) sb.append(indent).append("    android:layout_marginRight=\"").append(marR).append("dp\"\n");
			if (marT > 0) sb.append(indent).append("    android:layout_marginTop=\"").append(marT).append("dp\"\n");
			if (marB > 0) sb.append(indent).append("    android:layout_marginBottom=\"").append(marB).append("dp\"\n");
			
			int gravity = layout.optInt("gravity", 0);
			if (gravity != 0) {
				sb.append(indent).append("    android:gravity=\"")
				.append(gravityToString(gravity)).append("\"\n");
			}
			
			int layoutGravity = layout.optInt("layoutGravity", 0);
			if (layoutGravity != 0) {
				sb.append(indent).append("    android:layout_gravity=\"")
				.append(gravityToString(layoutGravity)).append("\"\n");
			}
			
			int orientation = layout.optInt("orientation", -1);
			if (orientation == 0) sb.append(indent).append("    android:orientation=\"horizontal\"\n");
			if (orientation == 1) sb.append(indent).append("    android:orientation=\"vertical\"\n");
			
			float weight = (float) layout.optDouble("weight", 0);
			if (weight > 0) sb.append(indent).append("    android:layout_weight=\"").append(weight).append("\"\n");
		}
		
		if (text != null) {
			String txt = text.optString("text", "");
			if (!txt.isEmpty()) sb.append(indent).append("    android:text=\"").append(escapeXml(txt)).append("\"\n");
			
			String hint = text.optString("hint", "");
			if (!hint.isEmpty()) sb.append(indent).append("    android:hint=\"").append(escapeXml(hint)).append("\"\n");
			
			int textSize = text.optInt("textSize", 12);
			sb.append(indent).append("    android:textSize=\"").append(textSize).append("sp\"\n");
			
			int textColor = (int) text.optDouble("textColor", 16777215);
			if (textColor != 16777215) {
				sb.append(indent).append("    android:textColor=\"").append(intToHex(textColor)).append("\"\n");
			}
			
			int textType = text.optInt("textType", 0);
			if (textType == 1) sb.append(indent).append("    android:textStyle=\"bold\"\n");
			else if (textType == 2) sb.append(indent).append("    android:textStyle=\"italic\"\n");
			else if (textType == 3) sb.append(indent).append("    android:textStyle=\"bold|italic\"\n");
			
			int singleLine = text.optInt("singleLine", 0);
			if (singleLine == 1) sb.append(indent).append("    android:singleLine=\"true\"\n");
		}
		
		if (image != null) {
			String scaleType = image.optString("scaleType", "");
			if (!scaleType.isEmpty() && !scaleType.equals("CENTER")) {
				sb.append(indent).append("    android:scaleType=\"").append(scaleType.toLowerCase()).append("\"\n");
			}
		}
		
		if (node.optInt("clickable", 1) == 0) sb.append(indent).append("    android:clickable=\"false\"\n");
		if (node.optInt("enabled",   1) == 0) sb.append(indent).append("    android:enabled=\"false\"\n");
		
		double alpha = node.optDouble("alpha", 1.0);
		if (alpha != 1.0) sb.append(indent).append("    android:alpha=\"").append(alpha).append("\"\n");
		
		String customView = node.optString("customView", "");
		if (!customView.isEmpty()) {
			sb.append(indent).append("    app:customView=\"").append(customView).append("\"\n");
		}
		
		List<JSONObject> children = childMap.get(id);
		if (children != null && !children.isEmpty()) {
			sb.append(indent).append(">\n");
			for (int i = 0; i < children.size(); i++) {
				appendXmlNode(children.get(i), childMap, sb, depth + 1);
			}
			sb.append(indent).append("</").append(tag).append(">\n");
		} else {
			sb.append(indent).append("/>\n");
		}
	}
	
	
	private static JSONArray buildNestedDataArray(List<JSONObject> nodes) throws Exception {
		Map<String, List<JSONObject>> childMap = buildChildMap(nodes);
		
		Map<String, JSONObject> nodeById = new LinkedHashMap<String, JSONObject>();
		for (int i = 0; i < nodes.size(); i++) {
			JSONObject n = nodes.get(i);
			nodeById.put(n.optString("id", ""), n);
		}
		
		List<JSONObject> roots = childMap.get("root");
		if (roots == null) return new JSONArray();
		
		JSONArray result = new JSONArray();
		for (int i = 0; i < roots.size(); i++) {
			JSONObject node = buildNestedNode(roots.get(i), childMap);
			if (node != null) result.put(node);
		}
		return result;
	}
	
	private static JSONObject buildNestedNode(
	JSONObject raw,
	Map<String, List<JSONObject>> childMap) throws Exception {
		
		String id  = raw.optString("id", "");
		String tag = resolveTag(raw);
		
		JSONObject node = new JSONObject();
		node.put("id",  id);
		node.put("tag", tag);
		
		JSONObject attrs  = new JSONObject();
		JSONObject layout = raw.optJSONObject("layout");
		JSONObject text   = raw.optJSONObject("text");
		JSONObject image  = raw.optJSONObject("image");
		
		if (layout != null) {
			attrs.put("android:layout_width",  dimenToString(layout.optInt("width",  -2)));
			attrs.put("android:layout_height", dimenToString(layout.optInt("height", -2)));
			
			int bg = (int) layout.optDouble("backgroundColor", 16777215);
			if (bg != 16777215) attrs.put("android:background", intToHex(bg));
			
			int padL = layout.optInt("paddingLeft",   0);
			int padR = layout.optInt("paddingRight",  0);
			int padT = layout.optInt("paddingTop",    0);
			int padB = layout.optInt("paddingBottom", 0);
			if (padL > 0) attrs.put("android:paddingLeft",   padL + "dp");
			if (padR > 0) attrs.put("android:paddingRight",  padR + "dp");
			if (padT > 0) attrs.put("android:paddingTop",    padT + "dp");
			if (padB > 0) attrs.put("android:paddingBottom", padB + "dp");
			
			int marL = layout.optInt("marginLeft",   0);
			int marR = layout.optInt("marginRight",  0);
			int marT = layout.optInt("marginTop",    0);
			int marB = layout.optInt("marginBottom", 0);
			if (marL > 0) attrs.put("android:layout_marginLeft",   marL + "dp");
			if (marR > 0) attrs.put("android:layout_marginRight",  marR + "dp");
			if (marT > 0) attrs.put("android:layout_marginTop",    marT + "dp");
			if (marB > 0) attrs.put("android:layout_marginBottom", marB + "dp");
			
			int gravity = layout.optInt("gravity", 0);
			if (gravity != 0) attrs.put("android:gravity", gravityToString(gravity));
			
			int layoutGravity = layout.optInt("layoutGravity", 0);
			if (layoutGravity != 0) attrs.put("android:layout_gravity", gravityToString(layoutGravity));
			
			int orientation = layout.optInt("orientation", -1);
			if (orientation == 0) attrs.put("android:orientation", "horizontal");
			if (orientation == 1) attrs.put("android:orientation", "vertical");
			
			float weight = (float) layout.optDouble("weight", 0);
			if (weight > 0) attrs.put("android:layout_weight", String.valueOf(weight));
		}
		
		if (text != null) {
			String txt = text.optString("text", "");
			if (!txt.isEmpty()) attrs.put("android:text", txt);
			
			String hint = text.optString("hint", "");
			if (!hint.isEmpty()) attrs.put("android:hint", hint);
			
			int textSize = text.optInt("textSize", 12);
			attrs.put("android:textSize", textSize + "sp");
			
			int textColor = (int) text.optDouble("textColor", 16777215);
			if (textColor != 16777215) attrs.put("android:textColor", intToHex(textColor));
			
			int textType = text.optInt("textType", 0);
			if (textType == 1) attrs.put("android:textStyle", "bold");
			else if (textType == 2) attrs.put("android:textStyle", "italic");
			else if (textType == 3) attrs.put("android:textStyle", "bold|italic");
			
			if (text.optInt("singleLine", 0) == 1) attrs.put("android:singleLine", "true");
		}
		
		if (image != null) {
			String scaleType = image.optString("scaleType", "");
			if (!scaleType.isEmpty() && !scaleType.equals("CENTER")) {
				attrs.put("android:scaleType", scaleType.toLowerCase());
			}
		}
		
		if (raw.optInt("clickable", 1) == 0) attrs.put("android:clickable", "false");
		if (raw.optInt("enabled",   1) == 0) attrs.put("android:enabled",   "false");
		
		double alpha = raw.optDouble("alpha", 1.0);
		if (alpha != 1.0) attrs.put("android:alpha", String.valueOf(alpha));
		
		String customView = raw.optString("customView", "");
		if (!customView.isEmpty()) attrs.put("app:customView", customView);
		
		node.put("attributes",           attrs);
		node.put("isPlaceholder",        false);
		node.put("isPlaceholderWebView", false);
		
		JSONArray childrenArray = new JSONArray();
		List<JSONObject> children = childMap.get(id);
		if (children != null) {
			for (int i = 0; i < children.size(); i++) {
				JSONObject childNode = buildNestedNode(children.get(i), childMap);
				if (childNode != null) childrenArray.put(childNode);
			}
		}
		node.put("children", childrenArray);
		
		return node;
	}
	
	
	private static String resolveTag(JSONObject node) {
		int type = node.optInt("type", 0);
		String convert = node.optString("convert", "");
		if (!convert.isEmpty()) return convert;
		return ViewNodeModel.typeToName(type);
	}
	
	private static String toActivityName(String sectionName) {
		if (sectionName == null || sectionName.isEmpty()) return "MainActivity";
		
		String base = sectionName.replace(".xml_", "_").replace(".xml", "");
		
		String[] parts = base.split("_");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < parts.length; i++) {
			String part = parts[i].trim();
			if (!part.isEmpty()) {
				sb.append(Character.toUpperCase(part.charAt(0)));
				if (part.length() > 1) sb.append(part.substring(1));
			}
		}
		
		String result = sb.toString();
		if (!result.endsWith("Activity")) {
			result = result + "Activity";
		}
		return result;
	}
	
	private static String gravityToString(int gravity) {
		switch (gravity) {
			case 0x01: return "left";
			case 0x05: return "right";
			case 0x30: return "top";
			case 0x50: return "bottom";
			case 0x11: return "center_horizontal";
			case 0x12: return "center_vertical";
			case 0x17: return "fill_horizontal";
			case 0x77: return "fill_vertical";
			case 0x10: return "center";
			case 19:   return "left|top";
			case 21:   return "right|top";
			case 51:   return "left|bottom";
			case 53:   return "right|bottom";
			default:   return buildGravityString(gravity);
		}
	}
	
	private static String buildGravityString(int gravity) {
		boolean left    = (gravity & 0x03) == 0x01;
		boolean right   = (gravity & 0x03) == 0x05;
		boolean top     = (gravity & 0x30) == 0x30;
		boolean bottom  = (gravity & 0x30) == 0x50;
		boolean centerH = (gravity & 0x07) == 0x01;
		boolean centerV = (gravity & 0x70) == 0x10;
		
		if (centerH && centerV) return "center";
		if (centerH) return "center_horizontal";
		if (centerV) return "center_vertical";
		if (left  && top)    return "left|top";
		if (right && top)    return "right|top";
		if (left  && bottom) return "left|bottom";
		if (right && bottom) return "right|bottom";
		if (top)    return "top";
		if (bottom) return "bottom";
		if (left)   return "left";
		if (right)  return "right";
		return "center";
	}
	
	private static String dimenToString(int val) {
		if (val == -1) return "match_parent";
		if (val == -2) return "wrap_content";
		return val + "dp";
	}
	
	private static String intToHex(int color) {
		return String.format("#%08X", color);
	}
	
	private static String escapeXml(String s) {
		if (s == null) return "";
		return s.replace("&", "&amp;")
		.replace("<", "&lt;")
		.replace(">", "&gt;")
		.replace("\"", "&quot;");
	}
	
	private static String repeat(String s, int times) {
		if (times <= 0) return "";
		StringBuilder sb = new StringBuilder(s.length() * times);
		for (int i = 0; i < times; i++) sb.append(s);
		return sb.toString();
	}
	
	
	private static String decrypt(byte[] encrypted) throws Exception {
		byte[] keyBytes = AES_KEY.getBytes(CHARSET);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
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
	
	private static void writeFile(String path, byte[] data) throws Exception {
		File file = new File(path);
		if (file.getParentFile() != null) file.getParentFile().mkdirs();
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(data);
		fos.flush();
		fos.close();
	}
}
