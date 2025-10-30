/*
* Copyright (C) 2025 NexusTeam & SmartIndiaGaming.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
* Developed by NexusTeam & SmartIndiaGaming.
* © 2025™ All rights reserved.
*/
package com.besome.blacklogics.development;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import b.b.b.ar;
import b.b.b.aq;
import b.b.b.gq;
import b.b.b.yg;

import com.besome.blacklogics.DesignActivity;
import com.besome.blacklogics.FileUtil;
import com.besome.blacklogics.R;
import com.besome.blacklogics.ViewEditorFragmentActivity;
import com.besome.blacklogics.WidgetAttributesManager;
import com.besome.blacklogics.beans.ProjectActivityBean;
import com.besome.blacklogics.file.AssetCopyUtil;
import com.besome.blacklogics.model.DesignDataManager;
import com.shapun.layouteditor.ViewEditor.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap; // For attributes handling in formatNode
import org.w3c.dom.Node; // For Node object in formatNode
import org.w3c.dom.NodeList; //
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


/**
* Nexus Open Source Project since 2025© Complex
* Manages Android project data, including logic, Java/XML code, and build configurations.
* Stores data in a JSON structure with Base64 encoding for persistence.
*/

public class Complex {
	
	public HashMap<String, String> activityLogicMap = new HashMap<>();
	
	public String sc_id = "601"; 
	public String s = "MainActivity";
	public String scName = "BlackLogicsOpenSourceProject";
	
	public Spinner javaSpinner;
	public Spinner xmlSpinner;
	
	public Context context;
	public ProjectActivityBean currentActivityBean;
	
	public JSONObject activityLogicStorage; // Storing arguments in JSON
	public HashMap<String, String> runtimeLogicCache = new HashMap<>(); // In-memory cache (better name of activityLogicMap)
	public JSONObject projectData; // Complete JSON data
	public HashMap<String, String> cachedLogic; // Runtime logic cache
	
	public List<String> items = new ArrayList<>();
	
	public List<String> xmlItems = new ArrayList<>(); // XML file names
	public List<String> javaItems = new ArrayList<>(); // Java activity names
	public Map<String, String> xmlToJavaMap = new HashMap<>(); // XML to Java mapping
	public Map<String, String> javaToXmlMap = new HashMap<>(); // Java to XML mapping
	public String lastSelectedXml = ""; // Track last selected XML
	public String lastSelectedJava = ""; // Track last selected Java
	
	// New fields for extra resources and Java files
	private HashMap<String, String> extraResources = new HashMap<>(); // Map of resource path to content
	private HashMap<String, String> extraJavaFiles = new HashMap<>(); // Map of Java file path to content
	
	// New interfaces for export progress tracking
	public interface OnExportProgress {
		void onProgress(int progress, String message);
	}
	
	public interface OnExportFailed {
		void onFailed(String errorMessage);
	}
	
	public interface OnExportSuccess {
		void onSuccess(String zipFilePath);
	}
	
	public String getStoragePath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath() + "/.blacklogics/data/" + sc_id + "/data";
	}
	
	public JSONObject jsonData;
	
	public Complex() {
		loadJson();
	}
	/*
public Complex(Context context) {
this.context = context;
loadJson();
}
*/	
	
	public void setC(Context context) {
		this.context = context;
	}
	
	public void setActivityName(String s) {
		this.s = s;
		tryLoadData();
	}
	
	/************************************
* ViewItem class (top-level static) *
************************************/	
	public static class ViewItem {
		public String xmlName;  // XML file name (e.g., "activity_main")
		public String xmlFileName;  // Full XML file name (e.g., "activity_main.xml")
		public String javaName;  // Java file name (e.g., "MainActivity")
		public String javaFileName;  // Full Java file name (e.g., "MainActivity.java")
		
		public ViewItem(String xmlName, String xmlFileName, String javaName, String javaFileName) {
			this.xmlName = xmlName;
			this.xmlFileName = xmlFileName;
			this.javaName = javaName;
			this.javaFileName = javaFileName;
		}
		
		public String getXmlName() {
			return xmlName;
		}
		
		public String getXmlFileName() {
			return xmlFileName;
		}
		
		public String getJavaName() {
			return javaName;
		}
		
		public String getJavaFileName() {
			return javaFileName;
		}
	}
	
	// Add this as a top-level interface in the Complex class
	public interface OnViewItemClickListener {
		void onItemClick(ViewItem item);
	}
	
	public interface OnViewPreviewClickListener {
		void onPreviewClick(ViewItem item);
	}
	
	/**
* Listener for long press events on view items
*/	
	public interface OnViewItemLongClickListener {
		void onItemLongClick(ViewItem item, int position);
	}
	
	/**
* Listener for long press events on custom view items
*/	
	public interface OnCustomViewLongClickListener {
		void onCustomViewLongClick(String viewName, int position);
	}
	
	/**
* Interface to handle data synchronization in Activities
*/	
	public interface SyncData {
		// Called when data is refreshed successfully
		void onDataRefreshed();
		
		// Called when data refresh fails
		void onRefreshFailed(String errorMessage);
	}
	
	/**
* Method to trigger data refresh and notify listener
* @param listener The SyncData listener to notify on refresh events
*/	
	public void refreshDataWithListener(SyncData listener) {
		try {
			// Reload JSON data from disk
			loadJson();
			
			// Clear existing caches
			runtimeLogicCache.clear();
			javaItems.clear();
			xmlItems.clear();
			xmlToJavaMap.clear();
			javaToXmlMap.clear();
			extraResources.clear();
			extraJavaFiles.clear();
			
			// Refresh logic from activityLogicStorage
			if (activityLogicStorage != null) {
				Iterator<String> keys = activityLogicStorage.keys();
				while (keys.hasNext()) {
					String encodedKey = keys.next();
					runtimeLogicCache.put(decodeData(encodedKey), decodeData(activityLogicStorage.getString(encodedKey)));
				}
			}
			
			// Refresh meta data (acName, xName, permissions, etc.)
			JSONObject meta = jsonData.optJSONObject("meta");
			if (meta != null) {
				// Refresh activity and XML names
				JSONArray acArray = meta.optJSONArray("acName");
				JSONArray xArray = meta.optJSONArray("xName");
				if (acArray != null && xArray != null) {
					int len = Math.min(acArray.length(), xArray.length());
					for (int i = 0; i < len; i++) {
						String javaName = decodeData(acArray.getString(i));
						String xmlName = decodeData(xArray.getString(i));
						javaItems.add(javaName);
						xmlItems.add(xmlName);
						xmlToJavaMap.put(xmlName, javaName);
						javaToXmlMap.put(javaName, xmlName);
					}
				}
				
				// Refresh permissions
				JSONArray permissionArray = meta.optJSONArray("permissions");
				if (permissionArray != null) {
					List<String> permissions = new ArrayList<>();
					for (int i = 0; i < permissionArray.length(); i++) {
						permissions.add(decodeData(permissionArray.getString(i)));
					}
					// Update runtime permissions list if maintained
					// Example: permissionsList = permissions; (if you maintain a permissions list)
				}
				
				// Refresh other meta data (toolbar, fab, startup, etc.)
				JSONObject toolbarData = meta.optJSONObject("toolbar");
				JSONObject fabData = meta.optJSONObject("fab");
				JSONObject startupData = meta.optJSONObject("startup");
				// Add logic to handle these if needed (e.g., update UI or internal state)
			}
			
			// Refresh XML and Java data
			JSONObject xmlData = jsonData.optJSONObject("xml");
			if (xmlData != null) {
				Iterator<String> keys = xmlData.keys();
				while (keys.hasNext()) {
					String encodedKey = keys.next();
					String xmlName = decodeData(encodedKey);
					xmlItems.add(xmlName); // Ensure XML items are updated
				}
			}
			
			JSONObject javaData = jsonData.optJSONObject("java");
			if (javaData != null) {
				Iterator<String> keys = javaData.keys();
				while (keys.hasNext()) {
					String encodedKey = keys.next();
					String javaName = decodeData(encodedKey).replace(".java", "");
					javaItems.add(javaName); // Ensure Java items are updated
				}
			}
			
			// Refresh extra resources and Java files
			String resPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/.blacklogics/data/" + sc_id + "/files/resource";
			String javaPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/.blacklogics/data/" + sc_id + "/files/java";
			setExtraResources(resPath);
			setExtraJavaFiles(javaPath);
			
			// Refresh UI components
			if (xmlSpinner != null) setXmlAdapter(xmlSpinner);
			if (javaSpinner != null) setJavaAdapter(javaSpinner);
			updateFragmentState();
			
			// Notify listener of successful refresh
			if (listener != null) {
				listener.onDataRefreshed();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (listener != null) {
				listener.onRefreshFailed("Failed to refresh data: " + e.getMessage());
			}
		}
	}
	
	public void setId(String id) {
		this.sc_id = id;
		loadJson();
		tryLoadData();
	}
	
	/** GZIP + Base64 save */
	public void saveJson() {
		try {
			JSONObject logicObj = new JSONObject();
			for (Map.Entry<String, String> entry : runtimeLogicCache.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				if (key == null || value == null || key.isEmpty() || value.isEmpty()) continue;
				
				String encodedKey = Base64.encodeToString(key.getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP);
				// GZIP compress the value first
				byte[] compressedValue = gzipCompress(value);
				String encodedValue = Base64.encodeToString(compressedValue, Base64.NO_WRAP);
				
				logicObj.put(encodedKey, encodedValue);
			}
			jsonData.put("logic", logicObj);
			activityLogicStorage = logicObj;
			
			File file = new File(getStoragePath());
			file.getParentFile().mkdirs();
			try (FileWriter writer = new FileWriter(file)) {
				writer.write(jsonData.toString(2));
			}
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
	}
	
	/** GZIP + Base64 load */
	public void loadJson() {
		jsonData = new JSONObject();
		activityLogicStorage = new JSONObject();
		runtimeLogicCache.clear();
		
		File file = new File(getStoragePath());
		if (!file.exists()) return;
		
		try (FileReader reader = new FileReader(file)) {
			StringBuilder content = new StringBuilder();
			char[] buffer = new char[8192];
			int charsRead;
			while ((charsRead = reader.read(buffer)) != -1) content.append(buffer, 0, charsRead);
			
			if (content.length() == 0) return;
			
			jsonData = new JSONObject(content.toString());
			activityLogicStorage = jsonData.optJSONObject("logic");
			if (activityLogicStorage != null) {
				Iterator<String> keys = activityLogicStorage.keys();
				while (keys.hasNext()) {
					String encodedKey = keys.next();
					String encodedValue = activityLogicStorage.getString(encodedKey);
					String key = new String(Base64.decode(encodedKey, Base64.NO_WRAP), StandardCharsets.UTF_8);
					
					// Base64 decode + GZIP decompress
					byte[] compressedValue = Base64.decode(encodedValue, Base64.NO_WRAP);
					String value = gzipDecompress(compressedValue);
					
					runtimeLogicCache.put(key, value);
				}
			}
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
	}
	
	/** GZIP compress string */
	private byte[] gzipCompress(String str) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (GZIPOutputStream gzip = new GZIPOutputStream(baos)) {
			gzip.write(str.getBytes(StandardCharsets.UTF_8));
		}
		return baos.toByteArray();
	}
	
	/** GZIP decompress bytes to string */
	private String gzipDecompress(byte[] compressed) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(compressed);
		try (GZIPInputStream gzip = new GZIPInputStream(bais);
		InputStreamReader reader = new InputStreamReader(gzip, StandardCharsets.UTF_8);
		BufferedReader in = new BufferedReader(reader)) {
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = in.readLine()) != null) sb.append(line).append("\n");
			if (sb.length() > 0) sb.setLength(sb.length() - 1); // remove last newline
			return sb.toString();
		}
	}
	
	/** Encode plain text to Base64 (normal) */
	public String encodeData(String input) {
		if (input == null || input.isEmpty()) return "";
		try {
			return Base64.encodeToString(input.getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/** Decode Base64 to plain text */
	public String decodeData(String encoded) {
		if (encoded == null || encoded.isEmpty()) return "";
		try {
			return new String(Base64.decode(encoded, Base64.NO_WRAP), StandardCharsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
	/**
* Validates if a string is valid Base64.
* @param str The string to validate
* @return true if valid Base64, false otherwise
*/	
	private boolean isValidBase64(String str) {
		if (str == null || str.isEmpty()) {
			return false;
		}
		if (str.length() % 4 != 0) {
			Log.w("Complex", "isValidBase64: String length not divisible by 4");
			return false;
		}
		return str.matches("^[A-Za-z0-9+/=]+$");
	}
	
	
	public void setLogic(String logic, String activityName) {
	}
	
	public String getLogicData(String activityName) {
		return "";
	}
	
	
	public void injectLogicToActivity(String activityName) {
	}
	
	
	public void setXmlCode(String xmlName, String xmlCode) {
		try {
			// Fallback to old JSON system if activity not found
			JSONObject xmlData = jsonData.optJSONObject("xml");
			if (xmlData == null) xmlData = new JSONObject();
			
			String encodedName = encodeData(xmlName);
			
			// Handle null or empty xmlCode
			if (xmlCode == null || xmlCode.isEmpty()) {
				xmlCode = "<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
				"    android:layout_width=\"match_parent\"\n" +
				"    android:layout_height=\"match_parent\"\n" +
				"    android:orientation=\"vertical\"\n" +
				"    android:background=\"#FFFFFF\">\n\n" +
				"</LinearLayout>";
			}
			
			String encodedData = encodeData(xmlCode);
			xmlData.put(encodedName, encodedData);
			jsonData.put("xml", xmlData);
			saveJson();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void setJavaCode(String javaName, String javaCode) {
		try {
			
			try {
				JSONObject javaData = jsonData.optJSONObject("java");
				if (javaData == null) javaData = new JSONObject();
				
				String encodedName = encodeData(javaName);
				String encodedData = encodeData(javaCode);
				
				javaData.put(encodedName, encodedData);
				jsonData.put("java", javaData);
				saveJson();
			} catch (JSONException ex) {
				ex.printStackTrace();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			// Fallback to old JSON system if needed
		}
	}
	
	public void extractAllXmlCodes(String path) {
		if (context instanceof DesignActivity) {
			DesignActivity designActivity = (DesignActivity) context;
			syncAllSavedXmlFromViewEditor(designActivity.projectPath);
		}
		
		try {
			JSONObject xmlData = jsonData.optJSONObject("xml");
			File folder = new File(path);
			File abcd = new File(FileUtil.getExternalStorageDir() + "/.blacklogics/data/" + sc_id + "/files/resource");
			folder.mkdirs();
			
			// Old meta system — directly extract all XMLs from JSON
			if (xmlData != null) {
				Iterator<String> keys = xmlData.keys();
				while (keys.hasNext()) {
					String encodedName = keys.next();
					String decodedName = decodeData(encodedName);
					
					String encodedData = xmlData.getString(encodedName);
					String decodedData = decodeData(encodedData);
					
					File file = new File(folder, decodedName + ".xml");
					File existingFile = new File(abcd, decodedName + ".xml");
					if (existingFile.exists()) continue;
					writeFile(file, decodedData);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// Original extract method remains unchanged
	public void extractAllJavaCodes(String path) {
		try {
			//first step to extract all logics and variable external codes 
			generateAllJavaCodes();
			
			// Fallback to old JSON system for any remaining activities
			JSONObject javaData = jsonData.optJSONObject("java");
			JSONObject logicData = jsonData.optJSONObject("logic");
			
			if (javaData != null) {
				File folder = new File(path);
				File abcd = new File(FileUtil.getExternalStorageDir() + "/.blacklogics/data/" + sc_id + "/files/java");
				folder.mkdirs();
				Iterator<String> keys = javaData.keys();
				while (keys.hasNext()) {
					String encodedName = keys.next();
					String decodedName = decodeData(encodedName);
					String originalCode = decodeData(javaData.getString(encodedName));
					
					String activityName = decodedName.replace(".java", "");
					
					//	// Skip if already processed by ProjectActivityBean
					//	if (activities.contains(activityName)) continue;
					
					// Check logic in JSON instead of activityLogicMap
					if (logicData != null && logicData.has(encodeData(activityName))) {
						String logic = decodeData(logicData.getString(encodeData(activityName)));
						
						// Inject the logic into the code only if it doesn't already contain the logic
						String modifiedCode = injectLogic(originalCode, logic, activityName);
						
						File file = new File(folder, decodedName + ".java");
						File existingFile = new File(abcd, decodedName + ".java");
						
						if (existingFile.exists()) continue;
						
						writeFile(file, modifiedCode);
					} else {
						File file = new File(folder, decodedName + ".java");
						writeFile(file, originalCode);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String injectLogic(String javaCode, String logic, String activityName) {
		return "";
	}
	
	
	
	// Helper method to convert camelCase to snake_case
	public String camelToSnakeCase(String input) {
		if (input == null || input.isEmpty()) {
			return input;
		}
		StringBuilder result = new StringBuilder();
		char[] chars = input.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (Character.isUpperCase(c)) {
				if (i > 0 && Character.isLowerCase(chars[i - 1])) {
					result.append('_');
				}
				result.append(Character.toLowerCase(c));
			} else {
				result.append(c);
			}
		}
		return result.toString();
	}
	
	public void writeFile(File file, String content) throws IOException {
		FileWriter writer = new FileWriter(file);
		writer.write(content);
		writer.close();
	}
	
	public String generateRandomName() {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		Random random = new Random();
		int length = 4 + random.nextInt(3);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(chars.charAt(random.nextInt(chars.length())));
		}
		return sb.toString();
	}
	
	public void setProGuardRules(String proGuardRules) {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null) metaData = new JSONObject();
			
			// Default ProGuard rules if not provided
			if (proGuardRules == null || proGuardRules.trim().isEmpty()) {
				proGuardRules =
				"-keep public class * {\n" +
				"    public protected *;\n" +
				"}\n\n" +
				"-keepclassmembers class * {\n" +
				"    public *;\n" +
				"}\n\n" +
				"-dontwarn android.support.**\n" +
				"-dontwarn androidx.**\n" +
				"-keepattributes Exceptions,InnerClasses";
			}
			
			metaData.put("proguard", encodeData(proGuardRules));
			jsonData.put("meta", metaData);
			saveJson();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	// Set Gradle Build File
	public void setGradleBuild(String gradleBuild) {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null) metaData = new JSONObject();
			
			if (gradleBuild == null || gradleBuild.trim().isEmpty()) {
				gradleBuild =
				"apply plugin: 'com.android.application'\n\n" +
				"android {\n" +
				"    compileSdkVersion 34\n" +
				"    defaultConfig {\n" +
				"        applicationId \"" + getPkgName() + "\"\n" +
				"        minSdkVersion 21\n" +
				"        targetSdkVersion 34\n" +
				"        versionCode 1\n" +
				"        versionName \"1.0\"\n" +
				"    }\n" +
				"    buildTypes {\n" +
				"        release {\n" +
				"            minifyEnabled false\n" +
				"            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'\n" +
				"        }\n" +
				"    }\n" +
				"}\n\n" +
				"dependencies {\n" +
				"    implementation 'androidx.appcompat:appcompat:1.6.1'\n" +
				"    implementation 'androidx.core:core:1.12.0'\n" +
				"    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'\n" +
				"    implementation 'com.google.android.material:material:1.12.0'\n" +
				"}\n";
			}
			
			metaData.put("gradle", encodeData(gradleBuild));
			jsonData.put("meta", metaData);
			saveJson();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void setGradleBuildConfig(String gradleBuildConfig) {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null) metaData = new JSONObject();
			
			// Default Gradle Configurations if empty
			if (gradleBuildConfig == null || gradleBuildConfig.trim().isEmpty()) {
				gradleBuildConfig =
				"android {\n" +
				"    compileSdkVersion 34\n" +
				"    defaultConfig {\n" +
				"        applicationId \"" + getPkgName() + "\"\n" +
				"        minSdkVersion 21\n" +
				"        targetSdkVersion 34\n" +
				"    }\n" +
				"}";
			}
			
			metaData.put("configGradle", encodeData(gradleBuildConfig));
			jsonData.put("meta", metaData);
			saveJson();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void extractProGuardRules(String path) {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null || !metaData.has("proguard")) return;
			
			String encodedData = metaData.getString("proguard");
			String decodedData = decodeData(encodedData);
			
			File file = new File(path, "proguard-rules.pro");
			writeFile(file, decodedData);
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void extractGradleBuild(String path) {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null || !metaData.has("gradle")) return;
			
			String encodedData = metaData.getString("gradle");
			String decodedData = decodeData(encodedData);
			
			File file = new File(path, "build.gradle");
			writeFile(file, decodedData);
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void extractGradleBuildConfig(String path) {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null || !metaData.has("configGradle")) return;
			
			String encodedData = metaData.getString("configGradle");
			String decodedData = decodeData(encodedData);
			
			File file = new File(path, "build.gradle");
			writeFile(file, decodedData);
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}
	
	// Set and Get Package Name
	public void setPkgName(String pkgName) {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null) metaData = new JSONObject();
			
			metaData.put("pkgName", encodeData(pkgName));
			jsonData.put("meta", metaData);
			saveJson();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String getPkgName() {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null || !metaData.has("pkgName")) return "base.application";
			
			return decodeData(metaData.getString("pkgName"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "base.application";
	}
	
	public void setScName(String pkgName) {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null) metaData = new JSONObject();
			
			metaData.put("scName", encodeData(pkgName));
			jsonData.put("meta", metaData);
			saveJson();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String getScName() {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null || !metaData.has("scName")) return "BlackLogicsOpenSourceProject";
			
			return decodeData(metaData.getString("scName"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "BlackLogicsOpenSourceProject";
	}
	
	public void setProjectName(String pkgName) {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null) metaData = new JSONObject();
			
			metaData.put("scName", encodeData(pkgName));
			jsonData.put("meta", metaData);
			saveJson();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String getProjectName() {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null || !metaData.has("scName")) return "base.application";
			
			return decodeData(metaData.getString("scName"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "base.application";
	}
	
	// Set and Get Activity Name
	
	public void setAcName(String acName) {
		try {
			JSONObject temp = new JSONObject(jsonData.toString());
			
			JSONObject metaData = temp.optJSONObject("meta");
			if (metaData == null) metaData = new JSONObject();
			
			JSONArray acNameArray = metaData.optJSONArray("acName");
			if (acNameArray == null) acNameArray = new JSONArray();
			
			acNameArray.put(encodeData(acName)); // Add new name
			metaData.put("acName", acNameArray); // Save array
			temp.put("meta", metaData);
			
			jsonData = temp;
			saveJson();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	public String getAcName() {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null || !metaData.has("acName")) return "MainActivity";
			
			JSONArray acNameArray = metaData.getJSONArray("acName");
			if (acNameArray.length() == 0) return "MainActivity";
			
			return decodeData(acNameArray.getString(acNameArray.length() - 1)); // Return latest
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "MainActivity";
	}
	
	
	// Set and Get XML Activity Name
	public void setXName(String xName) {
		try {
			JSONObject temp = new JSONObject(jsonData.toString());
			
			JSONObject metaData = temp.optJSONObject("meta");
			if (metaData == null) metaData = new JSONObject();
			
			JSONArray xNameArray = metaData.optJSONArray("xName");
			if (xNameArray == null) xNameArray = new JSONArray();
			
			xNameArray.put(encodeData(xName)); // Add new entry
			metaData.put("xName", xNameArray); // Update meta
			temp.put("meta", metaData);
			
			jsonData = temp;
			saveJson();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	public String getXName() {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null || !metaData.has("xName")) return "main.xml";
			
			JSONArray xNameArray = metaData.getJSONArray("xName");
			if (xNameArray.length() == 0) return "main.xml";
			
			return decodeData(xNameArray.getString(xNameArray.length() - 1)); // Return latest
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "main.xml";
	}
	
	/**
* Set adapter for the Spinner with acName and xName
* @param spinner The standard Android Spinner to populate
* @param context Context used for ArrayAdapter
*/	
	public void setSpinnerAdapter(Spinner spinner) {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null || !metaData.has("acName") || !metaData.has("xName")) return;
			
			String acName = decodeData(metaData.getString("acName"));
			String xName = decodeData(metaData.getString("xName"));
			
			List<String> items = Arrays.asList(acName, xName);
			
			ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, items);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(adapter);
			
			// Set default selection
			if (!items.isEmpty()) {
				spinner.setSelection(0);
			}
			
			spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				@Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					String selectedItem = items.get(position);
					// Handle selected item if needed
				}
				
				@Override public void onNothingSelected(AdapterView<?> parent) {}
			});
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
* Sets the manifest code into jsonData, encodes in Base64.
* Prevents duplicates and keeps pretty XML.
*/	
	public void setManifest(String manifestCode) {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null) metaData = new JSONObject();
			
			if (manifestCode == null || manifestCode.trim().isEmpty()) {
				// Default manifest if null/empty
				manifestCode =
				"<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
				"<manifest xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
				"    package=\"" + getPkgName() + "\">\n\n" +
				"    <application\n" +
				"        android:allowBackup=\"true\"\n" +
				"        android:icon=\"@mipmap/icon\"\n" +
				"        android:label=\"" + getProjectName() + "\"\n" +
				"        android:theme=\"@style/AppTheme\"\n" +
				"        android:name=\".BlackApplication\">\n\n" +
				"        <activity android:name=\"." + getAcName() + "\">\n" +
				"            <intent-filter>\n" +
				"                <action android:name=\"android.intent.action.MAIN\" />\n" +
				"                <category android:name=\"android.intent.category.LAUNCHER\" />\n" +
				"            </intent-filter>\n" +
				"        </activity>\n\n" +
				"    </application>\n" +
				"</manifest>";
			}
			
			// 1️⃣ Merge user-controlled permissions
			String mergedManifest = mergePermissionsIntoManifest(manifestCode);
			
			// 2️⃣ Remove duplicate <activity> entries (regex remove)
			mergedManifest = mergedManifest.replaceAll(
			"(?s)<activity\\s+android:name=\"\\.[^\"]+\".*?</activity>\\s*", ""
			);
			
			// 3️⃣ Pretty-print XML
			mergedManifest = prettyPrintXML(mergedManifest);
			
			// 4️⃣ Encode normally in Base64
			String encoded = Base64.encodeToString(
			mergedManifest.getBytes(StandardCharsets.UTF_8),
			Base64.NO_WRAP
			);
			
			metaData.put("manifest", encoded);
			jsonData.put("meta", metaData);
			
			saveJson();
			
		} catch (JSONException e) {
			Log.e("setManifest", "Error setting manifest: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	
	public String getManifest() {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null || !metaData.has("manifest")) {
				// Return basic manifest with permissions if no manifest exists
				return mergePermissionsIntoManifest(
				"<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
				"<manifest xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
				"    package=\"" + getPkgName() + "\">\n\n" +
				"    <application\n" +
				"        android:allowBackup=\"true\"\n" +
				"        android:label=\"" + getProjectName() + "\">\n\n" +
				"        <activity android:name=\"." + getAcName() + "\">\n" +
				"            <intent-filter>\n" +
				"                <action android:name=\"android.intent.action.MAIN\" />\n" +
				"                <category android:name=\"android.intent.category.LAUNCHER\" />\n" +
				"            </intent-filter>\n" +
				"        </activity>\n\n" +
				"    </application>\n" +
				"</manifest>"
				);
			}
			
			String manifest = decodeData(metaData.getString("manifest"));
			return mergePermissionsIntoManifest(manifest);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
* Adds an activity to the manifest if it doesn't already exist.
* @param activityName The name of the activity to add
*/	
	public void addActivityToManifest(String activityName) {
		try {
			String manifest = getManifest();
			if (!manifest.contains("</application>")) {
				Log.w("addActivityToManifest", "No </application> tag found in manifest");
				return;
			}
			
			// Check if activity already exists using isActivityExist
			if (isActivityExist(activityName)) {
				Log.d("addActivityToManifest", "Activity " + activityName + " already exists, skipping addition");
				return; // No need to add if it already exists
			}
			
			// Remove any stale entries (just in case)
			removeActivityFromManifest(activityName);
			
			JSONObject toolbarInfo = getToolbarInfo(activityName);
			boolean hasToolbar = toolbarInfo != null && toolbarInfo.optBoolean("enabled", true);
			boolean useAndroidX = toolbarInfo != null ? toolbarInfo.optBoolean("androidX", getAndroidXEnable()) : getAndroidXEnable();
			
			String theme;
			if (activityName.equals("DebugActivity")) {
				theme = "@style/DebugTheme";
			} else if (hasToolbar) {
				theme = useAndroidX ? "@style/Theme.AppCompat.DayNight.DarkActionBar" : "@style/AppTheme";
			} else {
				theme = useAndroidX ? "@style/Theme.AppCompat.NoActionBar" : "@style/AppTheme.NoActionBar";
			}
			
			String newActivity =
			"        <activity\n" +
			"            android:name=\"." + activityName + "\"\n" +
			"            android:exported=\"" + (activityName.equals("MainActivity") ? "true" : "false") + "\"\n" +
			"            android:theme=\"" + theme + "\"\n" +
			"            android:label=\"" + activityName + "\" />\n";
			
			manifest = manifest.replace("</application>", newActivity + "</application>");
			setManifest(prettyPrintXML(manifest));
			
			
		} catch (Exception e) {
			Log.e("addActivityToManifest", "Error adding activity: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	
	// Extract Manifest
	public void extractManifest(String path) {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null || !metaData.has("manifest")) return;
			
			String decodedData = decodeData(metaData.getString("manifest"));
			// Merge permissions before writing
			decodedData = mergePermissionsIntoManifest(decodedData);
			
			File file = new File(path, "AndroidManifest.xml");
			writeFile(file, decodedData);
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}
	
	// Add these methods to the Complex class
	
	// Set Gradle Settings (settings.gradle)
	public void settingsGradle(String gradleSettings) {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null) metaData = new JSONObject();
			
			if (gradleSettings == null || gradleSettings.trim().isEmpty()) {
				gradleSettings = "include ':app'";
			}
			
			metaData.put("gradleSettings", encodeData(gradleSettings));
			jsonData.put("meta", metaData);
			saveJson();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	// Get Gradle Settings
	public String getSettingsGradle() {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null || !metaData.has("gradleSettings")) return "include ':app'";
			
			return decodeData(metaData.getString("gradleSettings"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "include ':app'";
	}
	
	// Extract Gradle Settings
	public void extractGradleSettings(String path) {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null || !metaData.has("gradleSettings")) return;
			
			String decodedData = decodeData(metaData.getString("gradleSettings"));
			File file = new File(path, "settings.gradle");
			writeFile(file, decodedData);
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}
	
	// Set String Resources
	public void setStringResources(String stringResources) {
		try {
			JSONObject resData = jsonData.optJSONObject("res");
			if (resData == null) resData = new JSONObject();
			
			if (stringResources == null || stringResources.trim().isEmpty()) {
				stringResources = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
				"<resources>\n" +
				"    <string name=\"app_name\">" + getProjectName() + "</string>\n" +
				"    <string name=\"hello_world\">Hello world!</string>\n" +
				"</resources>";
			}
			
			resData.put("strings", encodeData(stringResources));
			jsonData.put("res", resData);
			saveJson();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	// Get String Resources
	public String getStringResources() {
		try {
			JSONObject resData = jsonData.optJSONObject("res");
			if (resData == null || !resData.has("strings")) {
				return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
				"<resources>\n" +
				"    <string name=\"app_name\">" + getProjectName() + "</string>\n" +
				"</resources>";
			}
			
			return decodeData(resData.getString("strings"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public String getStyleResources() {
		try {
			JSONObject resData = jsonData.optJSONObject("res");
			if (resData == null || !resData.has("styles")) {
				return "";
			}
			
			return decodeData(resData.getString("styles"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	// Set Color Resources
	public void setColorResources(String colorResources) {
		try {
			JSONObject resData = jsonData.optJSONObject("res");
			if (resData == null) resData = new JSONObject();
			
			if (colorResources == null || colorResources.trim().isEmpty()) {
				colorResources = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
				"<resources>\n" +
				"    <color name=\"colorPrimary\">#3F51B5</color>\n" +
				"    <color name=\"colorPrimaryDark\">#303F9F</color>\n" +
				"    <color name=\"colorAccent\">#FF4081</color>\n" +
				"</resources>";
			}
			
			resData.put("colors", encodeData(colorResources));
			jsonData.put("res", resData);
			saveJson();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void setColorResources(int colorPrimary, int colorPrimaryDark, int colorAccent, int colorPrimaryHighlight, int colorPrimaryNormal) {
		try {
			JSONObject resData = jsonData.optJSONObject("res");
			if (resData == null) resData = new JSONObject();
			
			String colorXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
			"<resources>\n" +
			"    <color name=\"colorPrimary\">#" + Integer.toHexString(colorPrimary).substring(2) + "</color>\n" +
			"    <color name=\"colorPrimaryDark\">#" + Integer.toHexString(colorPrimaryDark).substring(2) + "</color>\n" +
			"    <color name=\"colorAccent\">#" + Integer.toHexString(colorAccent).substring(2) + "</color>\n" +
			"    <color name=\"colorPrimaryHighlight\">#" + Integer.toHexString(colorPrimaryHighlight).substring(2) + "</color>\n" +
			"    <color name=\"colorPrimaryNormal\">#" + Integer.toHexString(colorPrimaryNormal).substring(2) + "</color>\n" +
			"</resources>";
			
			resData.put("colors", encodeData(colorXml));
			jsonData.put("res", resData);
			saveJson();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	// Set Style Resources
	public void setStyleResources(String styleResources) {
		try {
			JSONObject resData = jsonData.optJSONObject("res");
			if (resData == null) resData = new JSONObject();
			
			boolean needsNoActionBar = false;
			boolean needsDebugTheme = false;
			
			// Check if any activity has toolbar disabled
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData != null && metaData.has("toolbar")) {
				JSONObject toolbarObject = metaData.getJSONObject("toolbar");
				Iterator<String> keys = toolbarObject.keys();
				while (keys.hasNext()) {
					String encodedActivityName = keys.next();
					JSONObject entry = toolbarObject.getJSONObject(encodedActivityName);
					if (!entry.optBoolean("enabled", true)) {
						needsNoActionBar = true;
						break;
					}
				}
			}
			
			// Check if DebugActivity exists in acName array
			if (metaData != null && metaData.has("acName")) {
				JSONArray acNameArray = metaData.getJSONArray("acName");
				for (int i = 0; i < acNameArray.length(); i++) {
					if ("DebugActivity".equals(decodeData(acNameArray.getString(i)))) {
						needsDebugTheme = true;
						break;
					}
				}
			}
			
			StringBuilder styles = new StringBuilder();
			styles.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n")
			.append("<resources>\n")
			.append("    <style name=\"AppTheme\" parent=\"")
			.append(getAndroidXEnable() ? "Theme.AppCompat.Light" : "android:Theme.Material.Light")
			.append("\">\n")
			.append("        <item name=\"android:colorPrimary\">@android:color/holo_blue_light</item>\n")
			.append("        <item name=\"android:colorPrimaryDark\">@android:color/holo_blue_dark</item>\n")
			.append("        <item name=\"android:colorAccent\">@android:color/holo_green_light</item>\n")
			.append("    </style>\n");
			
			styles.append("    <style name=\"AppTheme.NoActionBar\" parent=\"android:Theme.Material.Light.NoActionBar\">\n")
			.append("        <item name=\"android:colorPrimary\">@android:color/holo_blue_light</item>\n")
			.append("        <item name=\"android:colorPrimaryDark\">@android:color/holo_blue_dark</item>\n")
			.append("        <item name=\"android:colorAccent\">@android:color/holo_green_light</item>\n")
			.append("    </style>\n");
			
			styles.append("    <style name=\"DebugTheme\" parent=\"")
			.append(getAndroidXEnable() ? "Theme.AppCompat.Dialog" : "android:Theme.Material.Dialog")
			.append("\">\n")
			.append("        <item name=\"android:windowBackground\">@android:color/white</item>\n")
			.append("        <item name=\"android:colorPrimary\">@android:color/holo_red_light</item>\n")
			.append("        <item name=\"android:colorAccent\">@android:color/holo_red_dark</item>\n")
			.append("        <item name=\"android:textColorPrimary\">@android:color/black</item>\n")
			//	.append("        <item name=\"android:alertDialogStyle\">@style/AlertDialog.AppCompat</item>\n")
			.append("    </style>\n");   
			
			// Add NoActionBar style if needed and AndroidX is not enabled
			if (needsNoActionBar && !getAndroidXEnable()) {
				styles.append("    <style name=\"AppTheme.NoActionBar\" parent=\"android:Theme.Material.Light.NoActionBar\">\n")
				.append("        <item name=\"android:colorPrimary\">@android:color/holo_blue_light</item>\n")
				.append("        <item name=\"android:colorPrimaryDark\">@android:color/holo_blue_dark</item>\n")
				.append("        <item name=\"android:colorAccent\">@android:color/holo_green_light</item>\n")
				.append("    </style>\n");
			}
			
			// Add DebugTheme style if DebugActivity exists
			if (needsDebugTheme) {
				styles.append("    <style name=\"DebugTheme\" parent=\"")
				.append(getAndroidXEnable() ? "Theme.AppCompat.Dialog" : "android:Theme.Material.Dialog")
				.append("\">\n")
				.append("        <item name=\"android:windowBackground\">@android:color/white</item>\n")
				.append("        <item name=\"android:colorPrimary\">@android:color/holo_red_light</item>\n")
				.append("        <item name=\"android:colorAccent\">@android:color/holo_red_dark</item>\n")
				.append("        <item name=\"android:textColorPrimary\">@android:color/black</item>\n")
				.append("        <item name=\"android:alertDialogStyle\">@style/AlertDialog.AppCompat</item>\n")
				.append("    </style>\n");
			}
			
			styles.append("</resources>");
			
			// Use provided styleResources if not empty, otherwise use constructed styles
			styleResources = (styleResources == null || styleResources.trim().isEmpty()) 
			? styles.toString() 
			: styleResources;
			
			// Append DebugTheme to existing styleResources if needed and not already present
			if (needsDebugTheme && !styleResources.contains("DebugTheme")) {
				styleResources = styleResources.replace("</resources>",
				styles.toString().replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>", "")
				.replace("</resources>", "") +
				"</resources>");
			}
			
			resData.put("styles", encodeData(styleResources));
			jsonData.put("res", resData);
			saveJson();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	// Set Style Resources with dynamic colors
	public void setStyleResources(String styleResources,
	int colorPrimary,
	int colorPrimaryDark,
	int colorAccent,
	int colorPrimaryHighlight,
	int colorPrimaryNormal) {
		try {
			JSONObject resData = jsonData.optJSONObject("res");
			if (resData == null) resData = new JSONObject();
			
			boolean needsNoActionBar = false;
			boolean needsDebugTheme = false;
			
			// Check toolbar settings for NoActionBar requirement
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData != null && metaData.has("toolbar")) {
				JSONObject toolbarObject = metaData.getJSONObject("toolbar");
				Iterator<String> keys = toolbarObject.keys();
				while (keys.hasNext()) {
					String encodedActivityName = keys.next();
					JSONObject entry = toolbarObject.getJSONObject(encodedActivityName);
					if (!entry.optBoolean("enabled", true)) {
						needsNoActionBar = true;
						break;
					}
				}
			}
			
			// Check if DebugActivity exists
			if (metaData != null && metaData.has("acName")) {
				JSONArray acNameArray = metaData.getJSONArray("acName");
				for (int i = 0; i < acNameArray.length(); i++) {
					if ("DebugActivity".equals(decodeData(acNameArray.getString(i)))) {
						needsDebugTheme = true;
						break;
					}
				}
			}
			
			// Convert colors to hex strings
			String primaryColor = String.format("#%06X", (0xFFFFFF & colorPrimary));
			String primaryDarkColor = String.format("#%06X", (0xFFFFFF & colorPrimaryDark));
			String accentColor = String.format("#%06X", (0xFFFFFF & colorAccent));
			String highlightColor = String.format("#%06X", (0xFFFFFF & colorPrimaryHighlight));
			String normalColor = String.format("#%06X", (0xFFFFFF & colorPrimaryNormal));
			
			// Build styles XML
			StringBuilder styles = new StringBuilder();
			styles.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n");
			
			boolean useAndroidX = getAndroidXEnable();
			
			// Base AppTheme
			styles.append("    <style name=\"AppTheme\" parent=\"")
			.append(useAndroidX ? "Theme.AppCompat.Light" : "android:Theme.Material.Light")
			.append("\">\n")
			.append("        <item name=\"").append(useAndroidX ? "colorPrimary" : "android:colorPrimary")
			.append("\">").append(primaryColor).append("</item>\n")
			.append("        <item name=\"").append(useAndroidX ? "colorPrimaryDark" : "android:colorPrimaryDark")
			.append("\">").append(primaryDarkColor).append("</item>\n")
			.append("        <item name=\"").append(useAndroidX ? "colorAccent" : "android:colorAccent")
			.append("\">").append(accentColor).append("</item>\n")
			// custom colors - framework me direct attr nahi hote, fallback karna padega
			.append("        <item name=\"android:colorForeground\">").append(highlightColor).append("</item>\n")
			.append("        <item name=\"android:colorBackground\">").append(normalColor).append("</item>\n")
			.append("    </style>\n");
			
			// NoActionBar theme
			styles.append("    <style name=\"AppTheme.NoActionBar\" parent=\"")
			.append(useAndroidX ? "Theme.AppCompat.Light.NoActionBar" : "android:Theme.Material.Light.NoActionBar")
			.append("\">\n")
			.append("        <item name=\"").append(useAndroidX ? "colorPrimary" : "android:colorPrimary")
			.append("\">").append(primaryColor).append("</item>\n")
			.append("        <item name=\"").append(useAndroidX ? "colorPrimaryDark" : "android:colorPrimaryDark")
			.append("\">").append(primaryDarkColor).append("</item>\n")
			.append("        <item name=\"").append(useAndroidX ? "colorAccent" : "android:colorAccent")
			.append("\">").append(accentColor).append("</item>\n")
			.append("    </style>\n");
			
			// DebugTheme
			styles.append("    <style name=\"DebugTheme\" parent=\"")
			.append(useAndroidX ? "Theme.AppCompat.Dialog" : "android:Theme.Material.Dialog")
			.append("\">\n")
			.append("        <item name=\"android:windowBackground\">@android:color/white</item>\n")
			.append("        <item name=\"").append(useAndroidX ? "colorPrimary" : "android:colorPrimary")
			.append("\">").append(primaryColor).append("</item>\n")
			.append("        <item name=\"").append(useAndroidX ? "colorAccent" : "android:colorAccent")
			.append("\">").append(accentColor).append("</item>\n")
			.append("        <item name=\"android:textColorPrimary\">@android:color/black</item>\n")
			.append("        <item name=\"android:alertDialogTheme\">")
			.append(useAndroidX ? "@style/ThemeOverlay.AppCompat.Dialog.Alert"
			: "@android:style/Theme.Material.Dialog.Alert")
			.append("</item>\n")
			.append("    </style>\n");
			
			styles.append("</resources>");
			
			// Use provided styleResources if not empty
			styleResources = (styleResources == null || styleResources.trim().isEmpty())
			? styles.toString()
			: styleResources;
			
			resData.put("styles", encodeData(styleResources));
			jsonData.put("res", resData);
			saveJson();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	// Set Dimension Resources
	public void setDimensionResources(String dimensionResources) {
		try {
			JSONObject resData = jsonData.optJSONObject("res");
			if (resData == null) resData = new JSONObject();
			
			if (dimensionResources == null || dimensionResources.trim().isEmpty()) {
				dimensionResources = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
				"<resources>\n" +
				"    <dimen name=\"activity_horizontal_margin\">16dp</dimen>\n" +
				"    <dimen name=\"activity_vertical_margin\">16dp</dimen>\n" +
				"</resources>";
			}
			
			resData.put("dimens", encodeData(dimensionResources));
			jsonData.put("res", resData);
			saveJson();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	// Set Values-v21 Resources (for API 21+ specific attributes)
	public void setValuesV21Resources(String valuesV21Resources) {
		try {
			JSONObject resData = jsonData.optJSONObject("res");
			if (resData == null) resData = new JSONObject();
			
			if (valuesV21Resources == null || valuesV21Resources.trim().isEmpty()) {
				valuesV21Resources = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
				"<resources>\n" +
				"    <style name=\"AppTheme\" parent=\"android:Theme.Material.Light\">\n" +
				"        <item name=\"android:windowDrawsSystemBarBackgrounds\">true</item>\n" +
				"        <item name=\"android:statusBarColor\">@android:color/darker_gray</item>\n" +
				"    </style>\n" +
				"</resources>";
			}
			
			resData.put("values_v21", encodeData(valuesV21Resources));
			jsonData.put("res", resData);
			saveJson();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	// Extract all resources to res directory
	public void extractAllResources(String resPath) {
		try {
			JSONObject resData = jsonData.optJSONObject("res");
			if (resData == null) return;
			
			// Create directories
			new File(resPath + "/values").mkdirs();
			new File(resPath + "/values-v21").mkdirs();
			
			// Extract strings.xml
			if (resData.has("strings")) {
				String content = decodeData(resData.getString("strings"));
				writeFile(new File(resPath + "/values", "strings.xml"), content);
			}
			
			// Extract colors.xml
			if (resData.has("colors")) {
				String content = decodeData(resData.getString("colors"));
				writeFile(new File(resPath + "/values", "colors.xml"), content);
			}
			
			// Extract styles.xml
			if (resData.has("styles")) {
				String content = decodeData(resData.getString("styles"));
				writeFile(new File(resPath + "/values", "styles.xml"), content);
			}
			
			// Extract dimens.xml
			if (resData.has("dimens")) {
				String content = decodeData(resData.getString("dimens"));
				writeFile(new File(resPath + "/values", "dimens.xml"), content);
			}
			
			// Extract values-v21.xml
			if (resData.has("values_v21")) {
				String content = decodeData(resData.getString("values_v21"));
				writeFile(new File(resPath + "/values-v21", "styles.xml"), content);
			}
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setXmlAdapter(final Spinner spinner) {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null || !metaData.has("xName") || !metaData.has("acName")) return;
			
			JSONArray xNameArray = metaData.getJSONArray("xName");
			JSONArray acNameArray = metaData.getJSONArray("acName");
			
			xmlItems.clear();
			javaItems.clear();
			xmlToJavaMap.clear();
			javaToXmlMap.clear();
			
			int maxLength = Math.min(xNameArray.length(), acNameArray.length());
			for (int i = 0; i < maxLength; i++) {
				String xmlFileName = decodeData(xNameArray.getString(i));
				String javaFileName = decodeData(acNameArray.getString(i));
				xmlItems.add(xmlFileName);
				javaItems.add(javaFileName);
				xmlToJavaMap.put(xmlFileName, javaFileName);
				javaToXmlMap.put(javaFileName, xmlFileName);
			}
			
			if (spinner != null && spinner.getContext() != null) {
				ArrayAdapter<String> adapter = new ArrayAdapter<>(spinner.getContext(), android.R.layout.simple_spinner_item, xmlItems);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner.setAdapter(adapter);
				
				spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
						lastSelectedXml = xmlItems.get(position);
						lastSelectedJava = xmlToJavaMap.getOrDefault(lastSelectedXml, "");
						updateFragmentState();
					}
					
					@Override
					public void onNothingSelected(AdapterView<?> parent) {}
				});
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
* Set Java adapter for the custom spinner
* @param spinner The CustomSpinner to populate with Java activity names
*/	
	public void setJavaAdapter(Spinner spinner) {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null || !metaData.has("xName") || !metaData.has("acName")) return;
			
			if (javaItems.isEmpty() || xmlItems.isEmpty()) {
				JSONArray xNameArray = metaData.getJSONArray("xName");
				JSONArray acNameArray = metaData.getJSONArray("acName");
				
				xmlItems.clear();
				javaItems.clear();
				xmlToJavaMap.clear();
				javaToXmlMap.clear();
				
				int maxLength = Math.min(xNameArray.length(), acNameArray.length());
				for (int i = 0; i < maxLength; i++) {
					String xmlFileName = decodeData(xNameArray.getString(i));
					String javaFileName = decodeData(acNameArray.getString(i));
					xmlItems.add(xmlFileName);
					javaItems.add(javaFileName);
					xmlToJavaMap.put(xmlFileName, javaFileName);
					javaToXmlMap.put(javaFileName, xmlFileName);
				}
			}
			if (spinner != null && spinner.getContext() != null) {
				ArrayAdapter<String> adapter = new ArrayAdapter<>(spinner.getContext(), android.R.layout.simple_spinner_item, javaItems);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner.setAdapter(adapter);
				
				spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
						lastSelectedJava = javaItems.get(position);
						lastSelectedXml = javaToXmlMap.getOrDefault(lastSelectedJava, "");
						updateFragmentState();
					}
					@Override public void onNothingSelected(AdapterView<?> parent) {}
				});
				
			}	
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	private void updateSpinner(Spinner spinner, List<String> items) {
		if (spinner == null) return;
		
		ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, items);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (items == xmlItems && position < xmlItems.size()) {
					lastSelectedXml = xmlItems.get(position);
					lastSelectedJava = xmlToJavaMap.getOrDefault(lastSelectedXml, "");
				} else if (items == javaItems && position < javaItems.size()) {
					lastSelectedJava = javaItems.get(position);
					lastSelectedXml = javaToXmlMap.getOrDefault(lastSelectedJava, "");
				}
				updateFragmentState();
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				lastSelectedXml = "";
				lastSelectedJava = "";
				updateFragmentState();
			}
		});
		
		// Restore last selection if possible
		if (items == xmlItems && !lastSelectedXml.isEmpty() && xmlItems.contains(lastSelectedXml)) {
			spinner.setSelection(xmlItems.indexOf(lastSelectedXml));
		} else if (items == javaItems && !lastSelectedJava.isEmpty() && javaItems.contains(lastSelectedJava)) {
			spinner.setSelection(javaItems.indexOf(lastSelectedJava));
		}
	}
	
	/**
* Update fragment state based on selected XML and Java names
*/	
	public void updateFragmentState() {
		if (xmlSpinner != null && xmlSpinner.getContext() instanceof DesignActivity) {
			DesignActivity activity = (DesignActivity) xmlSpinner.getContext();
			if (activity.saveView()) {
				// Saved successfully
			}
			
			// Update layout name only if XML is valid
			if (!lastSelectedXml.isEmpty()) {
				ViewEditorFragmentActivity.layoutName = lastSelectedXml.replace(".xml", "");
				activity.currentActivityBean.setLayoutName(lastSelectedXml);
			}
			
			// Update activity name only if Java is valid
			if (!lastSelectedJava.isEmpty()) {
				currentActivityBean.setActivityName(lastSelectedJava);
				DesignDataManager.setJavaName(lastSelectedJava);
				activity.currentActivityBean.setActivityName(lastSelectedJava);
				activity.setCurrentProjectScreen(lastSelectedJava);
			}
			
			// Refresh UI
			if (ViewEditorFragmentActivity.ll != null) {
				ViewEditorFragmentActivity.ll.invalidate();
			}
			xmlSpinner.invalidate();
		}
	}
	
	/*
public void setOnSpinnerItemSelectedListener(AdapterView.OnItemSelectedListener listener) {
if (javaSpinner != null) {
javaSpinner.setOnItemSelectedListener(listener);
}
if (xmlSpinner != null) {
xmlSpinner.setOnItemSelectedListener(listener);
}
}
*/	
	
	public String getAllXml() {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null || !metaData.has("xName")) return "main";  // Return empty string instead of nothing
			
			JSONArray xNameArray = metaData.getJSONArray("xName");
			List<String> items = new ArrayList<>();
			
			for (int i = 0; i < xNameArray.length(); i++) {
				items.add(decodeData(xNameArray.getString(i)));
			}
			
			// Convert List<String> to a single String (join with commas or whatever delimiter you prefer)
			return String.join(",", items);
		} catch (JSONException e) {
			e.printStackTrace();
			return "";  // Return empty string in case of error
		}
	}
	
	public String getAllJavaActivity() {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null || !metaData.has("acName")) return "";  // Return empty string instead of nothing
			
			JSONArray xNameArray = metaData.getJSONArray("acName");
			List<String> items = new ArrayList<>();
			
			for (int i = 0; i < xNameArray.length(); i++) {
				items.add(decodeData(xNameArray.getString(i)));
			}
			
			// Convert List<String> to a single String (join with commas or whatever delimiter you prefer)
			return String.join(",", items);
		} catch (JSONException e) {
			e.printStackTrace();
			return "";  // Return empty string in case of error
		}
	}
	
	/**
* Merge permissions into manifest XML
* @param manifest The original manifest XML
* @return Manifest XML with permissions added
*/	
	public String mergePermissionsIntoManifest(String manifest) {
		ArrayList<String> permissions = getPermissions();
		if (permissions.isEmpty()) return manifest;
		
		// Find the manifest tag position
		int manifestTagEnd = manifest.indexOf(">", manifest.indexOf("<manifest")) + 1;
		if (manifestTagEnd <= 0) return manifest;
		
		StringBuilder mergedManifest = new StringBuilder();
		mergedManifest.append(manifest.substring(0, manifestTagEnd));
		
		// Add permissions
		for (String permission : permissions) {
			mergedManifest.append("\n    <uses-permission android:name=\"")
			.append(permission)
			.append("\" />");
		}
		
		// Add the rest of the manifest
		mergedManifest.append(manifest.substring(manifestTagEnd));
		
		return mergedManifest.toString();
	}
	
	
	
	
	/**
* Read permissions from external JSON file and add to storage
* @param filePath Full path to JSON file (e.g. "/storage/emulated/0/permissions.json")
*/	
	public void addPermissionsFromJsonFile(String filePath) {
		try {
			File jsonFile = new File(filePath);
			if (!jsonFile.exists()) {
				//System.out.println("Permission file not found: " + filePath);
				return;
			}
			
			String jsonContent = readFile(jsonFile);
			JSONArray permissionsArray = new JSONArray(jsonContent);
			
			ArrayList<String> newPermissions = new ArrayList<>();
			for (int i = 0; i < permissionsArray.length(); i++) {
				newPermissions.add(permissionsArray.getString(i));
			}
			
			// Merge with existing permissions
			ArrayList<String> currentPermissions = getPermissions();
			for (String permission : newPermissions) {
				if (!currentPermissions.contains(permission)) {
					currentPermissions.add(permission);
				}
			}
			
			setPermissions(currentPermissions);
			//System.out.println("Added " + newPermissions.size() + " permissions from file");
			
		} catch (Exception e) {
			//System.out.println("Error reading permissions file: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
* Example JSON file format expected at /storage/emulated/0/permissions.json:
* [
*     "android.permission.ACCESS_COARSE_LOCATION",
*     "android.permission.ACCESS_CHECKIN_PROPERTIES"
* ]
*/	
	
	
	// Add these methods to the Complex class
	
	/**
* Set permissions list in JSON storage
* @param permissions ArrayList of permission strings (e.g. "android.permission.INTERNET")
*/	
	public void setPermissions(ArrayList<String> permissions) {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null) metaData = new JSONObject();
			
			JSONArray permissionsArray = new JSONArray();
			for (String permission : permissions) {
				permissionsArray.put(encodeData(permission));
			}
			
			metaData.put("permissions", permissionsArray);
			jsonData.put("meta", metaData);
			saveJson();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
* Get stored permissions list
* @return ArrayList of permission strings
*/	
	public ArrayList<String> getPermissions() {
		ArrayList<String> permissions = new ArrayList<>();
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null || !metaData.has("permissions")) return permissions;
			
			JSONArray permissionsArray = metaData.getJSONArray("permissions");
			for (int i = 0; i < permissionsArray.length(); i++) {
				permissions.add(decodeData(permissionsArray.getString(i)));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return permissions;
	}
	
	/**
* Add a single permission to storage
* @param permission Permission string to add
*/	
	public void addPermission(String permission) {
		ArrayList<String> currentPermissions = getPermissions();
		if (!currentPermissions.contains(permission)) {
			currentPermissions.add(permission);
			setPermissions(currentPermissions);
		}
	}
	
	/**
* Remove a permission from storage
* @param permission Permission string to remove
*/	
	public void removePermission(String permission) {
		ArrayList<String> currentPermissions = getPermissions();
		if (currentPermissions.contains(permission)) {
			currentPermissions.remove(permission);
			setPermissions(currentPermissions);
		}
	}
	
	/**
* Check if a Java activity file exists in storage
* @param activityName Name of the activity to check (e.g., "MainActivity")
* @return boolean indicating if the activity exists
*/	
	public boolean isJavaActivityAvailable(String activityName) {
		try {
			JSONObject javaData = jsonData.optJSONObject("java");
			if (javaData == null) return false;
			
			String encodedName = encodeData(activityName + ".java");
			return javaData.has(encodedName);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
* The rails icon set in the manifest.
*
* @param iconName the name of the icon (e.g., "ic_launcher", "@mipmap/ic_launcher", or a full path like "res/mipmap/ic_launcher.png")
*/	
	public void setManifestIcon(String iconName) {
		try {
			String manifest = getManifest();
			if (manifest == null || manifest.isEmpty()) return;
			
			// Convert the icon path to the standard format (e.g., "@mipmap/ic_launcher")
			String formattedIconPath = formatIconPath(iconName);
			
			// Find and update the <application> tag with the icon
			if (manifest.contains("android:icon")) {
				// Update existing icon
				manifest = manifest.replaceAll(
				"android:icon=\"[^\"]+\"",
				"android:icon=\"" + formattedIconPath + "\""
				);
			} else {
				// Add new icon
				manifest = manifest.replace(
				"<application",
				"<application android:icon=\"" + formattedIconPath + "\""
				);
			}
			
			setManifest(manifest);
		} catch (Exception e) {
			// Log the specific error for debugging
			////System.err.println("Error setting manifest icon: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
* Converts the icon name to the correct format (e.g., "ic_launcher" → "@mipmap/ic_launcher")
*/	
	public String formatIconPath(String iconName) {
		if (iconName == null || iconName.trim().isEmpty()) {
			return "@mipmap/icon"; // Default
		}
		
		// If the iconName is already in the correct format, return it
		if (iconName.startsWith("@mipmap/") || iconName.startsWith("@drawable/")) {
			return iconName;
		}
		
		// Handle full paths (e.g., "/storage/emulated/0/icon.png" or "res/mipmap/ic_launcher.png")
		String resourceName = iconName;
		if (iconName.contains("/")) {
			// Extract the file name without extension
			resourceName = iconName.substring(iconName.lastIndexOf("/") + 1);
			resourceName = resourceName.replaceFirst("\\.[^.]+$", ""); // Remove file extension (e.g., .png)
		}
		
		// Remove any remaining extension if present (redundant but safe)
		if (resourceName.contains(".")) {
			resourceName = resourceName.substring(0, resourceName.lastIndexOf("."));
		}
		
		// Ensure the resource name is not empty
		if (resourceName.isEmpty()) {
			return "@mipmap/icon"; // Fallback to default if the extracted name is empty
		}
		
		// Return the formatted resource name
		return "@mipmap/" + resourceName;
	}
	
	/**
* Adds activities from a JSON file to the manifest
* @param filePath Full path to JSON file (e.g. "/storage/emulated/0/activities.json")
*/	
	public void addActivitiesFromJsonFile(String filePath) {
		try {
			File jsonFile = new File(filePath);
			if (!jsonFile.exists()) {
				//System.out.println("Activities file not found: " + filePath);
				return;
			}
			
			String jsonContent = readFile(jsonFile);
			JSONArray jsonArray = new JSONArray(jsonContent);
			String manifest = getManifest();
			
			int appClosePos = manifest.lastIndexOf("</application>");
			if (appClosePos == -1) return;
			
			StringBuilder newManifest = new StringBuilder();
			newManifest.append(manifest.substring(0, appClosePos));
			
			String packageName = getPkgName();
			
			for (int i = 0; i < jsonArray.length(); i++) {
				String activityName = jsonArray.getString(i);
				String activityPath = activityName.contains(".") ? activityName : "." + activityName;
				
				if (!manifest.contains("android:name=\"" + activityPath + "\"") && 
				!manifest.contains("android:name=\"" + packageName + "." + activityName + "\"")) {
					newManifest.append("\n        <activity android:name=\"")
					.append(activityPath)
					.append("\" />");
				}
			}
			
			newManifest.append(manifest.substring(appClosePos));
			setManifest(newManifest.toString());
			//System.out.println("Added " + jsonArray.length() + " activities from file");
			
		} catch (Exception e) {
			//System.out.println("Error reading activities file: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	
	/**
* The activity's manifest is an advanced XML generator (with default attributes).
*/	
	public String generateActivityEntry(String activityPath) {
		return "\n        <activity\n" +
		"            android:name=\"" + activityPath + "\"\n" +
		"            android:screenOrientation=\"portrait\"\n" +
		"            android:configChanges=\"orientation|screenSize|keyboardHidden|smallestScreenSize|screenLayout\"\n" +
		"            android:hardwareAccelerated=\"true\"\n" +
		"            android:supportsPictureInPicture=\"true\" />";
	}
	
	/**
* Adds services from a JSON file to the manifest
* @param filePath Full path to JSON file (e.g. "/storage/emulated/0/services.json")
*/	
	public void addServicesFromJsonFile(String filePath) {
		try {
			File jsonFile = new File(filePath);
			if (!jsonFile.exists()) {
				//System.out.println("Services file not found: " + filePath);
				return;
			}
			
			String jsonContent = readFile(jsonFile);
			JSONArray jsonArray = new JSONArray(jsonContent);
			String manifest = getManifest();
			
			int appClosePos = manifest.lastIndexOf("</application>");
			if (appClosePos == -1) return;
			
			StringBuilder newManifest = new StringBuilder();
			newManifest.append(manifest.substring(0, appClosePos));
			
			String packageName = getPkgName();
			
			for (int i = 0; i < jsonArray.length(); i++) {
				String serviceName = jsonArray.getString(i);
				String servicePath = serviceName.contains(".") ? serviceName : "." + serviceName;
				
				if (!manifest.contains("android:name=\"" + servicePath + "\"") && 
				!manifest.contains("android:name=\"" + packageName + "." + serviceName + "\"")) {
					newManifest.append("\n        <service android:name=\"")
					.append(servicePath)
					.append("\" />");
				}
			}
			
			newManifest.append(manifest.substring(appClosePos));
			setManifest(newManifest.toString());
			//System.out.println("Added " + jsonArray.length() + " services from file");
			
		} catch (Exception e) {
			//System.out.println("Error reading services file: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
* Generates the service's Many Festive Adventures XML (with default attributes).
*/	
	public String generateServiceEntry(String servicePath) {
		return "\n        <service\n" +
		"            android:name=\"" + servicePath + "\"\n" +
		"            android:enabled=\"true\"\n" +
		"            android:exported=\"false\" />";
	}
	
	// Add these methods to the Complex class
	
	
	/**
* Set custom view name (both XML and Java) with default XML code
* @param viewName Name of the custom view (e.g. "CustomButton")
*/	
	public void setCustomViewName(String viewName) {
		try {
			JSONObject temp = new JSONObject(jsonData.toString());
			JSONObject metaData = temp.optJSONObject("meta");
			if (metaData == null) metaData = new JSONObject();
			
			// Store in customViews
			JSONArray customViewArray = metaData.optJSONArray("customViews");
			if (customViewArray == null) customViewArray = new JSONArray();
			
			String encodedViewName = encodeData(viewName);
			if (!arrayContains(customViewArray, encodedViewName)) {
				customViewArray.put(encodedViewName);
			}
			
			metaData.put("customViews", customViewArray);
			
			// Store in xName and create default XML
			JSONArray xNameArray = metaData.optJSONArray("xName");
			if (xNameArray == null) xNameArray = new JSONArray();
			
			String xmlFileName = viewName.toLowerCase() + ".xml";
			String encodedXmlName = encodeData(xmlFileName);
			if (!arrayContains(xNameArray, encodedXmlName)) {
				xNameArray.put(encodedXmlName);
				//	metaData.put("xName", xNameArray);
				
				// Set default XML code
				String defaultXml = "<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
				"    android:layout_width=\"match_parent\"\n" +
				"    android:layout_height=\"match_parent\"\n" +
				"    android:orientation=\"vertical\">\n\n" +
				"</LinearLayout>";
				setXmlCode(xmlFileName, defaultXml);
			}
			
			temp.put("meta", metaData);
			jsonData = temp;
			saveJson();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
* Get all custom view names
* @return List of custom view names
*/	
	public List<String> getCustomViewNames() {
		List<String> customViews = new ArrayList<>();
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null || !metaData.has("customViews")) return customViews;
			
			JSONArray customViewArray = metaData.getJSONArray("customViews");
			for (int i = 0; i < customViewArray.length(); i++) {
				customViews.add(decodeData(customViewArray.getString(i)));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return customViews;
	}
	
	/********************************
* CustomViewAdapter static class *
********************************/	
	public static class CustomViewAdapter extends RecyclerView.Adapter<CustomViewAdapter.ViewHolder> {
		public List<String> customViewNames; // Already public
		public OnItemClickListener clickListener;
		public OnCustomViewLongClickListener longClickListener;
		
		public interface OnItemClickListener {
			void onItemClick(String viewName);
		}
		
		public CustomViewAdapter(List<String> customViewNames, OnItemClickListener clickListener) {
			this.customViewNames = (customViewNames != null) ? new ArrayList<>(customViewNames) : new ArrayList<>();
			this.clickListener = clickListener;
		}
		
		public void setOnCustomViewLongClickListener(OnCustomViewLongClickListener longClickListener) {
			this.longClickListener = longClickListener;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(parent.getContext())
			.inflate(R.layout.custom_view_selector, parent, false);
			return new ViewHolder(view);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder holder, int position) {
			if (position < 0 || position >= customViewNames.size()) return; // Safety check
			String viewName = customViewNames.get(position);
			holder.layoutName.setText(viewName);
			holder.itemView.setOnClickListener(v -> {
				if (clickListener != null) {
					clickListener.onItemClick(viewName);
				}
			});
			
			holder.itemView.setOnLongClickListener(v -> {
				if (longClickListener != null) {
					longClickListener.onCustomViewLongClick(viewName, position);
					return true;
				}
				return false;
			});
		}
		
		@Override
		public int getItemCount() {
			return customViewNames != null ? customViewNames.size() : 0;
		}
		
		public static class ViewHolder extends RecyclerView.ViewHolder {
			public TextView layoutName;
			
			public ViewHolder(View itemView) {
				super(itemView);
				layoutName = itemView.findViewById(R.id.layout_name);
			}
		}
	}
	
	/**
* Set up RecyclerView with custom view names
* @param recyclerView The RecyclerView to configure
* @param listener Click listener for items
*/	
	public void setupCustomViewRecycler(RecyclerView recyclerView, CustomViewAdapter.OnItemClickListener listener) {
		List<String> customViews = getCustomViewNames();
		CustomViewAdapter adapter = new CustomViewAdapter(customViews, listener);
		recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
		recyclerView.setAdapter(adapter);
	}
	
	/**
* Remove a custom view by name
* @param viewName Name of the custom view to remove
*/	
	public void removeCustomView(String viewName) {
		try {
			JSONObject temp = new JSONObject(jsonData.toString());
			JSONObject metaData = temp.optJSONObject("meta");
			if (metaData == null) return;
			
			// Remove from customViews
			JSONArray customViewArray = metaData.optJSONArray("customViews");
			if (customViewArray != null) {
				JSONArray newArray = new JSONArray();
				String encodedViewName = encodeData(viewName);
				for (int i = 0; i < customViewArray.length(); i++) {
					if (!customViewArray.getString(i).equals(encodedViewName)) {
						newArray.put(customViewArray.getString(i));
					}
				}
				metaData.put("customViews", newArray);
			}
			
			// Remove from xName and XML data
			JSONArray xNameArray = metaData.optJSONArray("xName");
			if (xNameArray != null) {
				JSONArray newXNameArray = new JSONArray();
				String xmlFileName = viewName.toLowerCase() + ".xml";
				String encodedXmlName = encodeData(xmlFileName);
				for (int i = 0; i < xNameArray.length(); i++) {
					if (!xNameArray.getString(i).equals(encodedXmlName)) {
						newXNameArray.put(xNameArray.getString(i));
					}
				}
				metaData.put("xName", newXNameArray);
				
				// Remove XML code
				JSONObject xmlData = jsonData.optJSONObject("xml");
				if (xmlData != null) {
					xmlData.remove(encodedXmlName);
					jsonData.put("xml", xmlData);
				}
			}
			
			// Remove Java code if exists
			JSONObject javaData = jsonData.optJSONObject("java");
			if (javaData != null) {
				String javaFileName = viewName + ".java";
				javaData.remove(encodeData(javaFileName));
				jsonData.put("java", javaData);
			}
			
			temp.put("meta", metaData);
			jsonData = temp;
			saveJson();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
* Check if a custom view exists
* @param viewName Name to check
* @return true if exists, false otherwise
*/	
	public boolean hasCustomView(String viewName) {
		return getCustomViewNames().contains(viewName);
	}
	
	/**
* Get all custom views as comma-separated string
*/	
	public String getAllCustomViews() {
		List<String> views = getCustomViewNames();
		return String.join(",", views);
	}
	
	
	
	/********************************
* ViewAdapter static class *
********************************/	
	public static class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder> {
		public List<ViewItem> items; // Already public
		public OnViewItemClickListener clickListener;
		public OnViewItemLongClickListener longClickListener;
		public OnViewPreviewClickListener previewClickListener;
		
		public ViewAdapter(List<ViewItem> items, OnViewItemClickListener clickListener) {
			this.items = (items != null) ? new ArrayList<>(items) : new ArrayList<>();
			this.clickListener = clickListener;
		}
		
		public void setOnViewItemLongClickListener(OnViewItemLongClickListener longClickListener) {
			this.longClickListener = longClickListener;
		}
		
		public void setOnViewPreviewClickListener(OnViewPreviewClickListener previewClickListener) {
			this.previewClickListener = previewClickListener;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(parent.getContext())
			.inflate(R.layout.view_selector, parent, false);
			return new ViewHolder(view);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder holder, int position) {
			if (position < 0 || position >= items.size()) return; // Safety check
			ViewItem item = items.get(position);
			// Show XML name in layoutName
			holder.layoutName.setText(item.getXmlName());
			// Show Java file name in layoutClass
			holder.layoutClass.setText(item.getJavaFileName());
			
			holder.itemView.setOnClickListener(v -> {
				if (clickListener != null) {
					clickListener.onItemClick(item);
				}
			});
			
			holder.itemView.setOnLongClickListener(v -> {
				if (longClickListener != null) {
					longClickListener.onItemLongClick(item, position);
					return true;
				}
				return false;
			});
			
			holder.preview.setOnClickListener(v -> {
				if (DesignActivity.abc != null) {
					DesignActivity.abc.yq(true, item.getJavaFileName());
				}
			});
		}
		
		@Override
		public int getItemCount() {
			return items != null ? items.size() : 0;
		}
		
		public static class ViewHolder extends RecyclerView.ViewHolder {
			public ImageView preview;
			public ImageView editIcon;
			public TextView layoutName;
			public TextView layoutClass;
			
			public ViewHolder(View itemView) {
				super(itemView);
				preview = itemView.findViewById(R.id.preview);
				editIcon = itemView.findViewById(R.id.edit_icon);
				layoutName = itemView.findViewById(R.id.layout_name);
				layoutClass = itemView.findViewById(R.id.layout_class);
			}
		}
	}
	
	/********************************
* setupViewAdapter method *
********************************/	
	public void setupViewAdapter(RecyclerView recyclerView, OnViewItemClickListener listener) {
		List<ViewItem> items = new ArrayList<>();
		
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null) return;
			
			// Get XML and Java file names
			JSONArray xNameArray = metaData.has("xName") ? metaData.getJSONArray("xName") : new JSONArray();
			JSONArray acNameArray = metaData.has("acName") ? metaData.getJSONArray("acName") : new JSONArray();
			
			// Create pairs of XML and Java files
			// Assuming XML and Java files correspond by index or name
			int maxLength = Math.min(xNameArray.length(), acNameArray.length());
			for (int i = 0; i < maxLength; i++) {
				String xmlName = decodeData(xNameArray.getString(i));
				String javaName = decodeData(acNameArray.getString(i));
				
				items.add(new ViewItem(
				xmlName.replace(".xml", ""), // Clean XML name
				xmlName,                    // Full XML file name
				javaName.replace(".java", ""), // Clean Java name
				javaName                   // Full Java file name
				));
			}
			
			// If there are unpaired XML files
			for (int i = maxLength; i < xNameArray.length(); i++) {
				String xmlName = decodeData(xNameArray.getString(i));
				items.add(new ViewItem(
				xmlName.replace(".xml", ""),
				xmlName,
				"", // No Java name
				""  // No Java file name
				));
			}
			
			// If there are unpaired Java files
			for (int i = maxLength; i < acNameArray.length(); i++) {
				String javaName = decodeData(acNameArray.getString(i));
				items.add(new ViewItem(
				"", // No XML name
				"", // No XML file name
				javaName.replace(".java", ""),
				javaName
				));
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		// Sort items alphabetically by XML name, then Java name
		Collections.sort(items, (a, b) -> {
			String aName = a.getXmlName().isEmpty() ? a.getJavaName() : a.getXmlName();
			String bName = b.getXmlName().isEmpty() ? b.getJavaName() : b.getXmlName();
			return aName.compareToIgnoreCase(bName);
		});
		
		ViewAdapter adapter = new ViewAdapter(items, listener);
		recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
		recyclerView.setAdapter(adapter);
	}
	
	
	/**
* Refresh the RecyclerView data with updated view items
* @param recyclerView The RecyclerView to refresh
*/	
	public void refreshViewAdapter(RecyclerView recyclerView) {
		if (recyclerView.getAdapter() instanceof ViewAdapter) {
			ViewAdapter adapter = (ViewAdapter) recyclerView.getAdapter();
			List<ViewItem> newItems = new ArrayList<>();
			
			try {
				JSONObject metaData = jsonData.optJSONObject("meta");
				if (metaData == null) return;
				
				// Get XML and Java file names
				JSONArray xNameArray = metaData.has("xName") ? metaData.getJSONArray("xName") : new JSONArray();
				JSONArray acNameArray = metaData.has("acName") ? metaData.getJSONArray("acName") : new JSONArray();
				
				// Create pairs of XML and Java files
				int maxLength = Math.min(xNameArray.length(), acNameArray.length());
				for (int i = 0; i < maxLength; i++) {
					String xmlName = decodeData(xNameArray.getString(i));
					String javaName = decodeData(acNameArray.getString(i));
					
					newItems.add(new ViewItem(
					xmlName.replace(".xml", ""), // Clean XML name
					xmlName,                    // Full XML file name
					javaName.replace(".java", ""), // Clean Java name
					javaName                   // Full Java file name
					));
				}
				
				// If there are unpaired XML files
				for (int i = maxLength; i < xNameArray.length(); i++) {
					String xmlName = decodeData(xNameArray.getString(i));
					newItems.add(new ViewItem(
					xmlName.replace(".xml", ""),
					xmlName,
					"", // No Java name
					""  // No Java file name
					));
				}
				
				// If there are unpaired Java files
				for (int i = maxLength; i < acNameArray.length(); i++) {
					String javaName = decodeData(acNameArray.getString(i));
					newItems.add(new ViewItem(
					"", // No XML name
					"", // No XML file name
					javaName.replace(".java", ""),
					javaName
					));
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			// Sort items alphabetically by XML name, then Java name
			Collections.sort(newItems, (a, b) -> {
				String aName = a.getXmlName().isEmpty() ? a.getJavaName() : a.getXmlName();
				String bName = b.getXmlName().isEmpty() ? b.getJavaName() : b.getXmlName();
				return aName.compareToIgnoreCase(bName);
			});
			
			adapter.items = newItems;
			adapter.notifyDataSetChanged();
		}
	}
	
	/**
* Retrieves Java code content for a given file name.
* @param javaName The Java file name (e.g., "MainActivity.java")
* @return The decoded Java code or an empty string if not found
*/	
	public String getJavaContent(String javaName) {
		if (javaName.endsWith(".java")) {
			javaName = javaName.replace(".java", "");
		}
		try {
			JSONObject javaData = jsonData.optJSONObject("java");
			if (javaData == null) return "";
			
			String encodedKey = encodeData(javaName);
			if (javaData.has(encodedKey)) {
				return decodeData(javaData.getString(encodedKey));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	/**
* Retrieves XML code content for a given file name.
* @param xmlName The XML file name (e.g., "activity_main.xml")
* @return The decoded XML code or an empty string if not found
*/	
	public String getXmlContent(String xmlName) {
		if (xmlName.endsWith(".xml")) {
			xmlName = xmlName.replace(".xml", "");
		}
		try {
			JSONObject xmlData = jsonData.optJSONObject("xml");
			if (xmlData == null) return "";
			
			String encodedKey = encodeData(xmlName);
			if (xmlData.has(encodedKey)) {
				return decodeData(xmlData.getString(encodedKey));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	/**
* Helper method to check if a Java file exists in storage
* @param javaFileName The Java file name to check
* @return true if exists, false otherwise
*/	
	public boolean hasJavaFile(String javaFileName) {
		if (javaFileName == null || javaFileName.trim().isEmpty()) {
			return false;
		}
		
		try {
			if (!javaFileName.endsWith(".java")) {
				javaFileName = javaFileName + ".java";
			}
			
			JSONObject javaData = jsonData.optJSONObject("java");
			if (javaData == null) return false;
			
			return javaData.has(encodeData(javaFileName));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
* Helper method to check if an XML file exists in storage
* @param xmlFileName The XML file name to check
* @return true if exists, false otherwise
*/	
	public boolean hasXmlFile(String xmlFileName) {
		if (xmlFileName == null || xmlFileName.trim().isEmpty()) {
			return false;
		}
		
		try {
			if (!xmlFileName.endsWith(".xml")) {
				xmlFileName = xmlFileName + ".xml";
			}
			
			JSONObject xmlData = jsonData.optJSONObject("xml");
			if (xmlData == null) return false;
			
			return xmlData.has(encodeData(xmlFileName));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
* Removes all XML and Java view entries from storage
*/	
	public void removeAllViews() {
		try {
			JSONObject temp = new JSONObject(jsonData.toString());
			JSONObject metaData = temp.optJSONObject("meta");
			if (metaData == null) metaData = new JSONObject();
			
			metaData.put("xName", new JSONArray());
			metaData.put("acName", new JSONArray());
			temp.put("meta", metaData);
			
			// Clear XML and Java data
			jsonData.put("xml", new JSONObject());
			jsonData.put("java", new JSONObject());
			jsonData.put("logic", new JSONObject());
			
			// Clear activities from manifest
			String manifest = getManifest();
			manifest = manifest.replaceAll(
			"(?s)\\s*<activity[^>]*>.*?</activity>",
			""
			);
			setManifest(manifest);
			
			jsonData = temp;
			saveJson();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
* Removes all custom view entries from storage
*/	
	public void removeAllCustomViews() {
		try {
			JSONObject temp = new JSONObject(jsonData.toString());
			JSONObject metaData = temp.optJSONObject("meta");
			if (metaData == null) metaData = new JSONObject();
			
			JSONArray customViewArray = metaData.optJSONArray("customViews");
			if (customViewArray == null) return;
			
			// Remove associated XML files
			JSONObject xmlData = jsonData.optJSONObject("xml");
			if (xmlData == null) xmlData = new JSONObject();
			
			JSONArray xNameArray = metaData.optJSONArray("xName");
			if (xNameArray == null) xNameArray = new JSONArray();
			JSONArray newXNameArray = new JSONArray();
			
			// Keep non-custom view XMLs
			for (int i = 0; i < xNameArray.length(); i++) {
				String xmlName = decodeData(xNameArray.getString(i));
				boolean isCustomView = false;
				for (int j = 0; j < customViewArray.length(); j++) {
					String customViewName = decodeData(customViewArray.getString(j));
					if (xmlName.equals(customViewName.toLowerCase() + ".xml")) {
						isCustomView = true;
						break;
					}
				}
				if (!isCustomView) {
					newXNameArray.put(xNameArray.getString(i));
				}
			}
			
			// Remove custom view XML and Java
			for (int i = 0; i < customViewArray.length(); i++) {
				String customViewName = decodeData(customViewArray.getString(i));
				String xmlFileName = customViewName.toLowerCase() + ".xml";
				String javaFileName = customViewName + ".java";
				
				xmlData.remove(encodeData(xmlFileName));
				JSONObject javaData = jsonData.optJSONObject("java");
				if (javaData != null) {
					javaData.remove(encodeData(javaFileName));
					jsonData.put("java", javaData);
				}
			}
			
			metaData.put("customViews", new JSONArray());
			metaData.put("xName", newXNameArray);
			temp.put("meta", metaData);
			jsonData.put("xml", xmlData);
			
			jsonData = temp;
			saveJson();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String readFile(File file) throws IOException {
		StringBuilder content = new StringBuilder();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		while ((line = reader.readLine()) != null) {
			content.append(line).append("\n");
		}
		reader.close();
		return content.toString().trim();
	}
	
	/**
* Remove a view by XML and Java names
* @param xmlName XML file name (e.g. "activity_main.xml")
* @param javaName Java file name (e.g. "MainActivity")
*/	
	public void removeView(String xmlName, String javaName) {
		try {
			JSONObject temp = new JSONObject(jsonData.toString());
			JSONObject metaData = temp.optJSONObject("meta");
			if (metaData == null) return;
			
			// Remove XML
			if (xmlName != null && !xmlName.isEmpty()) {
				JSONArray xNameArray = metaData.optJSONArray("xName");
				if (xNameArray != null) {
					JSONArray newXNameArray = new JSONArray();
					String encodedXmlName = encodeData(xmlName);
					for (int i = 0; i < xNameArray.length(); i++) {
						if (!xNameArray.getString(i).equals(encodedXmlName)) {
							newXNameArray.put(xNameArray.getString(i));
						}
					}
					metaData.put("xName", newXNameArray);
					
					JSONObject xmlData = jsonData.optJSONObject("xml");
					if (xmlData != null) {
						xmlData.remove(encodedXmlName);
						jsonData.put("xml", xmlData);
					}
				}
			}
			
			// Remove Java
			if (javaName != null && !javaName.isEmpty()) {
				JSONArray acNameArray = metaData.optJSONArray("acName");
				if (acNameArray != null) {
					JSONArray newAcNameArray = new JSONArray();
					String encodedJavaName = encodeData(javaName);
					for (int i = 0; i < acNameArray.length(); i++) {
						if (!acNameArray.getString(i).equals(encodedJavaName)) {
							newAcNameArray.put(acNameArray.getString(i));
						}
					}
					metaData.put("acName", newAcNameArray);
					
					JSONObject javaData = jsonData.optJSONObject("java");
					if (javaData != null) {
						javaData.remove(encodeData(javaName + ".java"));
						jsonData.put("java", javaData);
					}
					
					// Remove logic if exists
					JSONObject logicData = jsonData.optJSONObject("logic");
					if (logicData != null) {
						logicData.remove(encodeData(javaName));
						jsonData.put("logic", logicData);
					}
				}
				
				// Update manifest
				String manifest = getManifest();
				String activityEntry = "android:name=\"." + javaName + "\"";
				if (manifest.contains(activityEntry)) {
					manifest = manifest.replaceAll(
					"(?s)\\s*<activity[^>]*" + activityEntry + "[^>]*>.*?</activity>",
					""
					);
					setManifest(manifest);
				}
			}
			
			temp.put("meta", metaData);
			jsonData = temp;
			saveJson();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	/**
* Helper method to check if JSONArray contains a value
*/	
	public boolean arrayContains(JSONArray array, String value) {
		try {
			for (int i = 0; i < array.length(); i++) {
				if (array.getString(i).equals(value)) {
					return true;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void removeXmlName(String xmlName) {
		try {
			JSONObject temp = new JSONObject(jsonData.toString());
			JSONObject metaData = temp.optJSONObject("meta");
			if (metaData == null) return;
			
			JSONArray xNameArray = metaData.optJSONArray("xName");
			if (xNameArray != null) {
				JSONArray newArray = new JSONArray();
				String encodedName = encodeData(xmlName);
				for (int i = 0; i < xNameArray.length(); i++) {
					if (!xNameArray.getString(i).equals(encodedName)) {
						newArray.put(xNameArray.getString(i));
					}
				}
				metaData.put("xName", newArray);
				temp.put("meta", metaData);
				jsonData = temp;
				saveJson();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void removeJavaName(String javaName) {
		try {
			JSONObject temp = new JSONObject(jsonData.toString());
			JSONObject metaData = temp.optJSONObject("meta");
			if (metaData == null) return;
			
			JSONArray acNameArray = metaData.optJSONArray("acName");
			if (acNameArray != null) {
				JSONArray newArray = new JSONArray();
				String encodedName = encodeData(javaName);
				for (int i = 0; i < acNameArray.length(); i++) {
					if (!acNameArray.getString(i).equals(encodedName)) {
						newArray.put(acNameArray.getString(i));
					}
				}
				metaData.put("acName", newArray);
				temp.put("meta", metaData);
				jsonData = temp;
				saveJson();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void setCode(String activityName, String javaCode, String logicCode) {
		try {
			JSONObject javaData = jsonData.optJSONObject("java");
			JSONObject logicData = jsonData.optJSONObject("logic");
			if (javaData == null) javaData = new JSONObject();
			if (logicData == null) logicData = new JSONObject();
			
			// Inject initializeLogic
			String modifiedCode = injectLogic(javaCode, logicCode, activityName);
			
			// Store Java code
			String encodedJavaName = encodeData(activityName + ".java");
			javaData.put(encodedJavaName, encodeData(modifiedCode));
			jsonData.put("java", javaData);
			
			// Store Logic too
			String encodedLogicName = encodeData(activityName);
			logicData.put(encodedLogicName, encodeData(logicCode));
			jsonData.put("logic", logicData);
			
			saveJson();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
* Get all activity names from meta.acName for use in UI lists.
* @return List of activity names (without .java extension)
*/	
	public List<String> getActivityNames() {
		List<String> activityNames = new ArrayList<>();
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null || !metaData.has("acName")) return activityNames;
			
			JSONArray acNameArray = metaData.getJSONArray("acName");
			for (int i = 0; i < acNameArray.length(); i++) {
				String encodedName = acNameArray.getString(i);
				String decodedName = decodeData(encodedName);
				// Remove .java extension if present
				if (decodedName.endsWith(".java")) {
					decodedName = decodedName.substring(0, decodedName.indexOf(".java"));
				}
				activityNames.add(decodedName);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return activityNames;
	}
	
	
	/**
* Associates an onClick listener logic with a specific widget in the given activity.
* Supports all clickable widgets without any widget declarations.
* @param activityName The name of the activity (e.g., "MainActivity")
* @param widgetId The ID of the widget (e.g., "button1")
* @param logic The core Java code to execute when the widget is clicked
*/	
	public void addWidgetOnClickListener(String activityName, String widgetId, String logic) {
		try {
			// Initialize or retrieve widget listeners storage
			JSONObject widgetListeners = jsonData.optJSONObject("widgetListeners");
			if (widgetListeners == null) {
				widgetListeners = new JSONObject();
				jsonData.put("widgetListeners", widgetListeners);
			}
			
			// Initialize or retrieve listeners for the specific activity
			JSONObject activityListeners = widgetListeners.optJSONObject(encodeData(activityName));
			if (activityListeners == null) {
				activityListeners = new JSONObject();
				widgetListeners.put(encodeData(activityName), activityListeners);
			}
			
			// Store the widget's onClick logic
			activityListeners.put(encodeData(widgetId), encodeData(logic));
			saveJson();
			
			// Re-inject all logic (main and widget listeners) into the activity
			String existingLogic = getLogicData(activityName);
			if (!existingLogic.isEmpty()) {
				injectLogicToActivity(activityName);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
* Associates an onLongClick listener logic with a specific widget in the given activity.
* @param activityName The name of the activity (e.g., "MainActivity")
* @param widgetId The ID of the widget (e.g., "button1")
* @param logic The core Java code to execute when the widget is long-clicked
*/	
	public void addWidgetOnLongClickListener(String activityName, String widgetId, String logic) {
		try {
			JSONObject widgetListeners = jsonData.optJSONObject("widgetListeners");
			if (widgetListeners == null) {
				widgetListeners = new JSONObject();
				jsonData.put("widgetListeners", widgetListeners);
			}
			
			JSONObject activityListeners = widgetListeners.optJSONObject(encodeData(activityName));
			if (activityListeners == null) {
				activityListeners = new JSONObject();
				widgetListeners.put(encodeData(activityName), activityListeners);
			}
			
			activityListeners.put(encodeData(widgetId + "_onLongClick"), encodeData(logic));
			saveJson();
			
			String existingLogic = getLogicData(activityName);
			if (!existingLogic.isEmpty()) {
				injectLogicToActivity(activityName);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
* Associates an onTouch listener logic with a specific widget in the given activity.
* @param activityName The name of the activity (e.g., "MainActivity")
* @param widgetId The ID of the widget (e.g., "button1")
* @param logic The core Java code to execute when the widget is touched
*/	
	public void addWidgetOnTouchListener(String activityName, String widgetId, String logic) {
		try {
			JSONObject widgetListeners = jsonData.optJSONObject("widgetListeners");
			if (widgetListeners == null) {
				widgetListeners = new JSONObject();
				jsonData.put("widgetListeners", widgetListeners);
			}
			
			JSONObject activityListeners = widgetListeners.optJSONObject(encodeData(activityName));
			if (activityListeners == null) {
				activityListeners = new JSONObject();
				widgetListeners.put(encodeData(activityName), activityListeners);
			}
			
			activityListeners.put(encodeData(widgetId + "_onTouch"), encodeData(logic));
			saveJson();
			
			String existingLogic = getLogicData(activityName);
			if (!existingLogic.isEmpty()) {
				injectLogicToActivity(activityName);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
* Retrieves the complete Java code for the specified activity, including initializeLogic and widget click, long click, and touch listeners.
* @param activityName The name of the activity (e.g., "MainActivity")
* @return The complete Java code as a String, or empty string if not found
*/	
	public String getJavaCode(String activityName) {
   	return "";
	}
	
	/**
* Stores selected widgets and their event types in JSON for a given activity.
* @param activityName The name of the activity (e.g., "MainActivity")
* @param widgetIds List of selected widget IDs
* @param eventType The selected event type (onClick, onLongClick, onTouch)
*/	
	public void storeWidgetEventSelections(String activityName, List<String> widgetIds, String eventType) {
	}
	
	/**
* Retrieves stored widget event selections for a given activity.
* @param activityName The name of the activity
* @return Map of widget IDs to their assigned event types
*/	
	public Map<String, String> getWidgetEventSelections(String activityName) {
		Map<String, String> selections = new HashMap<>();
		try {
			JSONObject widgetEventSelections = jsonData.optJSONObject("widgetEventSelections");
			if (widgetEventSelections != null) {
				JSONObject activitySelections = widgetEventSelections.optJSONObject(encodeData(activityName));
				if (activitySelections != null) {
					Iterator<String> keys = activitySelections.keys();
					while (keys.hasNext()) {
						String encodedWidgetId = keys.next();
						String widgetId = decodeData(encodedWidgetId);
						String eventType = decodeData(activitySelections.getString(encodedWidgetId));
						selections.put(widgetId, eventType);
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return selections;
	}
	
	/*
* Add this method to the Complex.java file
*/	
	public void extractAllLogicsFromJson(String jsonFilePath) {
		try {
			File jsonFile = new File(jsonFilePath);
			if (!jsonFile.exists()) {
				////System.out.println("Logic JSON file not found: " + jsonFilePath);
				return;
			}
			
			String jsonContent = readFile(jsonFile);
			JSONArray logicArray = new JSONArray(jsonContent);
			
			for (int i = 0; i < logicArray.length(); i++) {
				JSONObject logicObject = logicArray.getJSONObject(i);
				String activityName = logicObject.getString("activityName");
				String eventName = logicObject.getString("eventName");
				String sourceCode = logicObject.getString("sourceCode");
				
				if ("initializeLogic".equals(eventName)) {
					setLogic(sourceCode, activityName);
				}
			}
			
			// //System.out.println("Extracted and applied " + logicArray.length() + " logic entries from JSON");
		} catch (Exception e) {
			////System.out.println("Error processing logic JSON file: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
* Updates the project name across all relevant configurations.
* @param newProjectName The new project name to set
*/	
	public void updateProjectName(String newProjectName) {
		if (newProjectName == null || newProjectName.trim().isEmpty()) {
			return; // Prevent setting empty or null project name
		}
		try {
			// Update project name in JSON meta
			setProjectName(newProjectName);
			
			// Update manifest with new app label
			String manifest = getManifest();
			if (!manifest.isEmpty()) {
				manifest = manifest.replaceAll(
				"android:label=\"[^\"]+\"",
				"android:label=\"" + newProjectName + "\""
				);
				setManifest(manifest);
			}
			
			// Update string resources with new app_name
			String stringResources = getStringResources();
			if (!stringResources.isEmpty()) {
				stringResources = stringResources.replaceAll(
				"<string name=\"app_name\">[^<]+</string>",
				"<string name=\"app_name\">" + newProjectName + "</string>"
				);
				setStringResources(stringResources);
			}
			
			// Save all changes
			saveJson();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
* Updates the package name across all relevant configurations.
* @param newPackageName The new package name to set
*/	
	public void updatePackageName(String newPackageName) {
		if (newPackageName == null || newPackageName.trim().isEmpty() || !newPackageName.matches("^[a-zA-Z][a-zA-Z0-9_]*(\\.[a-zA-Z][a-zA-Z0-9_]*)+$")) {
			return; // Prevent invalid package names
		}
		try {
			// Store old package name for reference
			String oldPackageName = getPkgName();
			
			// Update package name in JSON meta
			setPkgName(newPackageName);
			
			// Update manifest with new package name
			String manifest = getManifest();
			if (!manifest.isEmpty()) {
				manifest = manifest.replaceAll(
				"package=\"[^\"]+\"",
				"package=\"" + newPackageName + "\""
				);
				// Update activity names to use new package
				manifest = manifest.replaceAll(
				"android:name=\"\\." + oldPackageName + "\\.",
				"android:name=\"." + newPackageName + "."
				);
				setManifest(manifest);
			}
			
			// Update Gradle build file with new applicationId
			String gradleBuild = getGradleBuild();
			if (!gradleBuild.isEmpty()) {
				gradleBuild = gradleBuild.replaceAll(
				"applicationId\\s*\"[^\"]+\"",
				"applicationId \"" + newPackageName + "\""
				);
				setGradleBuild(gradleBuild);
			}
			
			// Update Gradle build config with new applicationId
			String gradleBuildConfig = getGradleBuildConfig();
			if (!gradleBuildConfig.isEmpty()) {
				gradleBuildConfig = gradleBuildConfig.replaceAll(
				"applicationId\\s*\"[^\"]+\"",
				"applicationId \"" + newPackageName + "\""
				);
				setGradleBuildConfig(gradleBuildConfig);
			}
			
			// Save all changes
			saveJson();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
* Retrieves the Gradle build file content.
* @return The Gradle build file content as a String
*/	
	public String getGradleBuild() {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null || !metaData.has("gradle")) {
				return "";
			}
			return decodeData(metaData.getString("gradle"));
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
* Retrieves the Gradle build configuration content.
* @return The Gradle build configuration content as a String
*/	
	public String getGradleBuildConfig() {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null || !metaData.has("configGradle")) {
				return "";
			}
			return decodeData(metaData.getString("configGradle"));
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
* Retrieves a combined list of all Java activity names and XML file names.
* @return List of all Java and XML names
*/	
	public List<String> getAllJavaAndXmlNames() {
		List<String> allNames = new ArrayList<>();
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null) return allNames;
			
			// Get Java activity names
			JSONArray acNameArray = metaData.has("acName") ? metaData.getJSONArray("acName") : new JSONArray();
			for (int i = 0; i < acNameArray.length(); i++) {
				String javaName = decodeData(acNameArray.getString(i));
				if (!javaName.isEmpty()) {
					allNames.add(javaName + ".java");
				}
			}
			
			// Get XML file names
			JSONArray xNameArray = metaData.has("xName") ? metaData.getJSONArray("xName") : new JSONArray();
			for (int i = 0; i < xNameArray.length(); i++) {
				String xmlName = decodeData(xNameArray.getString(i));
				if (!xmlName.isEmpty()) {
					allNames.add(xmlName);
				}
			}
			
			// Sort the list alphabetically
			Collections.sort(allNames, String.CASE_INSENSITIVE_ORDER);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return allNames;
	}
	
	public void enableAndroidX(boolean enable) {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null) metaData = new JSONObject();
			
			metaData.put("androidXEnabled", enable);
			jsonData.put("meta", metaData);
			saveJson();
			
			if (enable) {
				migrateToAndroidX();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public boolean getAndroidXEnable() {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData != null && metaData.has("androidXEnabled")) {
				return metaData.getBoolean("androidXEnabled");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		// default if not set
		return false;
	}
	
	public void enableFab(String activityName) {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null) metaData = new JSONObject();
			
			JSONObject fabObject = metaData.optJSONObject("fab");
			if (fabObject == null) fabObject = new JSONObject();
			
			fabObject.put(encodeData(activityName), true);
			metaData.put("fab", fabObject);
			jsonData.put("meta", metaData);
			saveJson();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public void enableFab(String activityName, boolean enable) {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null) metaData = new JSONObject();
			
			JSONObject fabObject = metaData.optJSONObject("fab");
			if (fabObject == null) fabObject = new JSONObject();
			
			fabObject.put(encodeData(activityName), enable);
			metaData.put("fab", fabObject);
			jsonData.put("meta", metaData);
			saveJson();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public boolean getEnableFabBoolean(String activityName) {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null || !metaData.has("fab")) return false;
			
			JSONObject fabObject = metaData.getJSONObject("fab");
			return fabObject.optBoolean(encodeData(activityName), false);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String getEnableFabString(String activityName) {
		return String.valueOf(getEnableFabBoolean(activityName));
	}
	
	public void enableToolBar(String activityName, boolean useAndroidX) {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null) metaData = new JSONObject();
			
			JSONObject toolbarObject = metaData.optJSONObject("toolbar");
			if (toolbarObject == null) toolbarObject = new JSONObject();
			
			JSONObject entry = new JSONObject();
			entry.put("enabled", true);
			entry.put("androidX", useAndroidX);
			
			toolbarObject.put(encodeData(activityName), entry);
			metaData.put("toolbar", toolbarObject);
			jsonData.put("meta", metaData);
			saveJson();
			
			// Update manifest with appropriate theme
			String manifest = getManifest();
			String themeLine;
			if (true) {
				themeLine = useAndroidX ?
				"android:theme=\"@style/Theme.MaterialComponents.DayNight.DarkActionBar\"" :
				"android:theme=\"@style/AppTheme\"";
			} else {
				themeLine = useAndroidX ?
				"android:theme=\"@style/Theme.AppCompat.NoActionBar\"" :
				"android:theme=\"@style/AppTheme.NoActionBar\"";
			}
			
			String activityEntry = "<activity[^>]*android:name=\"\\." + activityName + "\"";
			if (manifest.contains(activityEntry)) {
				if (manifest.contains("android:theme=\"")) {
					manifest = manifest.replaceAll(
					"(" + activityEntry + "[^>]*?)android:theme=\"[^\"]+\"",
					"$1" + themeLine
					);
				} else {
					manifest = manifest.replaceFirst(
					activityEntry,
					"$0 " + themeLine
					);
				}
			} else {
				String newActivity = "        <activity android:name=\"." + activityName + "\" " + themeLine + " />";
				manifest = manifest.replace("</application>", newActivity + "\n    </application>");
			}
			
			setManifest(manifest);
			
			// Update style resources to ensure NoActionBar style is included if needed
			setStyleResources(getStyleResources());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public void enableToolBar(String activityName, boolean useAndroidX, boolean enable) {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null) metaData = new JSONObject();
			
			JSONObject toolbarObject = metaData.optJSONObject("toolbar");
			if (toolbarObject == null) toolbarObject = new JSONObject();
			
			JSONObject entry = new JSONObject();
			entry.put("enabled", enable);
			entry.put("androidX", useAndroidX);
			
			toolbarObject.put(encodeData(activityName), entry);
			metaData.put("toolbar", toolbarObject);
			jsonData.put("meta", metaData);
			saveJson();
			
			// Update manifest with appropriate theme
			String manifest = getManifest();
			String themeLine;
			if (enable) {
				themeLine = useAndroidX ?
				"android:theme=\"@style/Theme.MaterialComponents.DayNight.DarkActionBar\"" :
				"android:theme=\"@style/AppTheme\"";
			} else {
				themeLine = useAndroidX ?
				"android:theme=\"@style/Theme.AppCompat.NoActionBar\"" :
				"android:theme=\"@style/AppTheme.NoActionBar\"";
			}
			
			String activityEntry = "<activity[^>]*android:name=\"\\." + activityName + "\"";
			if (manifest.contains(activityEntry)) {
				if (manifest.contains("android:theme=\"")) {
					manifest = manifest.replaceAll(
					"(" + activityEntry + "[^>]*?)android:theme=\"[^\"]+\"",
					"$1" + themeLine
					);
				} else {
					manifest = manifest.replaceFirst(
					activityEntry,
					"$0 " + themeLine
					);
				}
			} else {
				String newActivity = "        <activity android:name=\"." + activityName + "\" " + themeLine + " />";
				manifest = manifest.replace("</application>", newActivity + "\n    </application>");
			}
			
			setManifest(manifest);
			
			// Update style resources to ensure NoActionBar style is included if needed
			setStyleResources(getStyleResources());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	private void updateManifestToolbarSupport(String activityName, boolean useAndroidX) {
		try {
			String manifest = getManifest();
			
			// Prepare theme with or without AndroidX toolbar
			String themeLine;
			if (useAndroidX) {
				themeLine = "android:theme=\"@style/Theme.MaterialComponents.DayNight.DarkActionBar\"";
			} else {
				themeLine = "android:theme=\"@style/AppTheme\"";
			}
			
			String activityEntry = "<activity android:name=\"." + activityName + "\"";
			
			if (manifest.contains(activityEntry)) {
				String updatedActivity = activityEntry;
				// Add or update theme line
				if (manifest.contains(themeLine)) return; // already present
				
				if (manifest.contains("android:theme=\"")) {
					manifest = manifest.replaceAll(
					"(\\<activity[^>]*android:name=\\\"\\." + activityName + "\\\"[^>]*?)android:theme=\\\"[^\"]+\\\"",
					"$1" + themeLine
					);
				} else {
					manifest = manifest.replace(activityEntry, activityEntry + " " + themeLine);
				}
			} else {
				// If not present, add the activity with theme
				String newActivity = "        <activity android:name=\"." + activityName + "\" " + themeLine + " />";
				manifest = manifest.replace("</application>", newActivity + "\n    </application>");
			}
			
			setManifest(manifest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public JSONObject getToolbarInfo(String activityName) {
		JSONObject metaData = jsonData.optJSONObject("meta");
		if (metaData == null) return null;
		
		JSONObject toolbarObject = metaData.optJSONObject("toolbar");
		if (toolbarObject == null) return null;
		
		return toolbarObject.optJSONObject(encodeData(activityName));
	}
	
	public void disableToolBar(String activityName) {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null) return;
			
			JSONObject toolbarObject = metaData.optJSONObject("toolbar");
			if (toolbarObject != null) {
				toolbarObject.remove(encodeData(activityName));
				metaData.put("toolbar", toolbarObject);
				jsonData.put("meta", metaData);
				saveJson();
			}
			
			// Update manifest to use NoActionBar theme if AndroidX is not enabled
			String manifest = getManifest();
			String activityEntry = "<activity[^>]*android:name=\"\\." + activityName + "\"";
			String themeLine = getAndroidXEnable() ?
			"android:theme=\"@style/Theme.AppCompat.NoActionBar\"" :
			"android:theme=\"@style/AppTheme.NoActionBar\"";
			
			if (manifest.contains(activityEntry)) {
				if (manifest.contains("android:theme=\"")) {
					manifest = manifest.replaceAll(
					"(" + activityEntry + "[^>]*?)android:theme=\"[^\"]+\"",
					"$1" + themeLine
					);
				} else {
					manifest = manifest.replaceFirst(
					activityEntry,
					"$0 " + themeLine
					);
				}
				setManifest(manifest);
			}
			
			// Update style resources to ensure NoActionBar style is included
			setStyleResources(getStyleResources());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	private void removeToolbarThemeFromManifest(String activityName) {
		try {
			String manifest = getManifest();
			String pattern = "(<activity[^>]*android:name=\"\\." + activityName + "\"[^>]*?)\\s+android:theme=\"[^\"]+\"";
			manifest = manifest.replaceAll(pattern, "$1"); // Remove theme attribute only
			setManifest(manifest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void disableFab(String activityName) {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null) return;
			
			JSONObject fabObject = metaData.optJSONObject("fab");
			if (fabObject != null) {
				fabObject.remove(encodeData(activityName));
				metaData.put("fab", fabObject);
				jsonData.put("meta", metaData);
				saveJson();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	// Boolean variant
	public boolean isFabEnabled(String activityName) {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null || !metaData.has("fab")) return false;
			JSONObject fabObject = metaData.getJSONObject("fab");
			return fabObject.optBoolean(encodeData(activityName), false);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// String variant
	public String isFabEnabledString(String activityName) {
		return String.valueOf(isFabEnabled(activityName));
	}
	public boolean isToolbarEnabled(String activityName) {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null || !metaData.has("toolbar")) return false;
			JSONObject toolbarObject = metaData.getJSONObject("toolbar");
			JSONObject entry = toolbarObject.optJSONObject(encodeData(activityName));
			return entry != null && entry.optBoolean("enabled", false);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void addActivity(String javaName, String xmlName, boolean enableFab, boolean enableKeyboard, boolean enableDrawer) {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null) metaData = new JSONObject();
			
			JSONArray acNameArray = metaData.optJSONArray("acName");
			if (acNameArray == null) acNameArray = new JSONArray();
			acNameArray.put(encodeData(javaName));
			metaData.put("acName", acNameArray);
			
			JSONArray xNameArray = metaData.optJSONArray("xName");
			if (xNameArray == null) xNameArray = new JSONArray();
			xNameArray.put(encodeData(xmlName));
			metaData.put("xName", xNameArray);
			
			JSONObject features = metaData.optJSONObject("features");
			if (features == null) features = new JSONObject();
			
			JSONObject flags = new JSONObject();
			flags.put("fab", enableFab);
			flags.put("keyboard", enableKeyboard);
			flags.put("drawer", enableDrawer);
			
			features.put(encodeData(javaName), flags);
			metaData.put("features", features);
			
			jsonData.put("meta", metaData);
			saveJson();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	/*
public boolean isFabEnabled(String javaName) {
return getFeatureFlag(javaName, "fab");
}*/	
	
	public boolean isDrawerEnabled(String javaName) {
		return getFeatureFlag(javaName, "drawer");
	}
	
	public boolean isKeyboardEnabled(String javaName) {
		return getFeatureFlag(javaName, "keyboard");
	}
	
	private boolean getFeatureFlag(String javaName, String flagKey) {
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData == null) return false;
			
			JSONObject features = metaData.optJSONObject("features");
			if (features == null) return false;
			
			JSONObject flags = features.optJSONObject(encodeData(javaName));
			if (flags == null) return false;
			
			return flags.optBoolean(flagKey, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
* Retrieves the properties of an activity from the JSON data.
* @param activityName The name of the activity (e.g., "MainActivity").
* @return A JSONObject containing the activity's properties, or null if not found.
* @throws JSONException If JSON parsing fails.
*/	
	public JSONObject getActivityData(String activityName) throws JSONException {
		JSONObject metaData = jsonData.optJSONObject("meta");
		if (metaData == null) {
			return null;
		}
		
		// Check toolbar settings
		JSONObject toolbarObject = metaData.optJSONObject("toolbar");
		boolean hasToolbar = true;
		boolean useAndroidX = getAndroidXEnable();
		if (toolbarObject != null) {
			JSONObject toolbarEntry = toolbarObject.optJSONObject(encodeData(activityName));
			if (toolbarEntry != null) {
				hasToolbar = toolbarEntry.optBoolean("enabled", true);
				useAndroidX = toolbarEntry.optBoolean("androidX", useAndroidX);
			}
		}
		
		// Check other activity properties (e.g., FAB, drawer, type, orientation)
		JSONObject activityData = metaData.optJSONObject(encodeData(activityName));
		if (activityData == null) {
			activityData = new JSONObject();
		}
		
		// Populate default values if not present
		activityData.put("toolbar", hasToolbar);
		activityData.put("androidX", useAndroidX);
		activityData.put("fab", activityData.optBoolean("fab", false));
		activityData.put("drawer", activityData.optBoolean("drawer", false));
		activityData.put("statusBar", activityData.optBoolean("statusBar", true));
		activityData.put("type", activityData.optString("type", "Activity"));
		activityData.put("orientation", activityData.optString("orientation", "Both"));
		
		// Get XML name from xName or derive it
		String xmlName = getXName(activityName);
		if (xmlName == null || xmlName.isEmpty()) {
			xmlName = "activity_" + activityName.toLowerCase().replaceAll("[^a-z0-9]", "_") + ".xml";
		}
		activityData.put("xmlName", xmlName);
		
		return activityData;
	}
	
	/**
* Updates an existing activity's properties.
* @param oldName The original activity name.
* @param newName The new activity name (may be the same as oldName).
* @param xmlName The XML file name for the activity.
* @param hasFab Whether the FAB is enabled.
* @param hasToolbar Whether the toolbar is enabled.
* @param useAndroidX Whether AndroidX is enabled.
* @param hasDrawer Whether the navigation drawer is enabled.
* @param type The activity type (Activity, Fragment, DialogFragment).
* @param orientation The orientation (Portrait, Landscape, Both).
* @param hasStatusBar Whether the status bar is enabled.
* @throws JSONException If JSON manipulation fails.
*/	
	public void updateActivity(
	String oldActivityName,
	String newJavaName,
	String newXmlName,
	boolean hasFab,
	boolean hasToolbar,
	boolean androidX,
	boolean hasDrawer,
	String type,
	String orientation,
	boolean hasStatusBar
	) throws JSONException {
		// pura JSON lo
		JSONObject activities = jsonData.optJSONObject("activities");
		if (activities == null) {
			throw new JSONException("No activities data found");
		}
		
		// purana data lo
		JSONObject activityData = activities.optJSONObject(oldActivityName);
		if (activityData == null) {
			throw new JSONException("Activity not found: " + oldActivityName);
		}
		
		// agar naam change hua hai to purana remove karo
		if (!oldActivityName.equals(newJavaName)) {
			activities.remove(oldActivityName);
		}
		
		// update fields
		activityData.put("xmlName", newXmlName);
		activityData.put("fab", hasFab);
		activityData.put("toolbar", hasToolbar);
		activityData.put("drawer", hasDrawer);
		activityData.put("statusBar", hasStatusBar);
		activityData.put("type", type);
		activityData.put("orientation", orientation);
		
		// naye naam pe wapas daalo
		activities.put(newJavaName, activityData);
		
		// manifest update
		removeActivityFromManifest(oldActivityName);
		addActivityToManifest(newJavaName);
	}
	
	
	
	private void removeActivityFromManifest(String activityName) {
		String manifest = getManifest();
		String activityEntry = "(?s)<activity\\s+[^>]*android:name\\s*=\\s*\"\\." + Pattern.quote(activityName) + "\"[^>]*>[\\s\\S]*?</activity>|<activity\\s+[^>]*android:name\\s*=\\s*\"\\." + Pattern.quote(activityName) + "\"[^>]*\\/>";
		manifest = manifest.replaceAll(activityEntry, "");
		setManifest(prettyPrintXML(manifest)); // Pretty-print for readability
	}
	
	/**
* Placeholder for getXName (retrieve XML name for an activity).
* Implement based on your JSON structure if needed.
*/	
	private String getXName(String activityName) {
		try {
			JSONObject resData = jsonData.optJSONObject("res");
			if (resData != null) {
				String xmlData = resData.optString("xml", "");
				String[] xmlEntries = xmlData.split(",");
				for (String entry : xmlEntries) {
					if (entry.contains(activityName.toLowerCase())) {
						return entry.trim();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public void setLauncherActivity(String activityName) {
		try {
			String manifest = getManifest();
			if (manifest.isEmpty() || !manifest.contains("</application>")) return;
			
			if (!hasDuplicateLauncher()) {
				
				// Remove existing launcher intent-filters
				String updatedManifest = manifest.replaceAll(
				"(?s)(\\s*<activity[^>]*>.*?)<intent-filter>\\s*" +
				"<action android:name=\"android.intent.action.MAIN\"\\s*/>\\s*" +
				"<category android:name=\"android.intent.category.LAUNCHER\"\\s*/>\\s*" +
				"</intent-filter>(.*?)</activity>",
				"$1$2</activity>"
				);
				
				// Remove duplicates first
				removeDuplicateActivities();
				
				// Find or add the target activity
				String activityEntry = "<activity[^>]*android:name=\"\\." + Pattern.quote(activityName) + "\"[^>]*>";
				Pattern activityPattern = Pattern.compile(activityEntry, Pattern.CASE_INSENSITIVE);
				Matcher activityMatcher = activityPattern.matcher(updatedManifest);
				
				if (activityMatcher.find()) {
					String fullActivityPattern = "(<activity[^>]*android:name=\"\\." + Pattern.quote(activityName) + "\"[^>]*>)(.*?)(</activity>)";
					Pattern fullPattern = Pattern.compile(fullActivityPattern, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
					Matcher fullMatcher = fullPattern.matcher(updatedManifest);
					
					if (fullMatcher.find()) {
						String activityStart = fullMatcher.group(1);
						String activityContent = fullMatcher.group(2);
						String activityEnd = fullMatcher.group(3);
						
						String newActivityContent = activityStart +
						activityContent +
						"\n            <intent-filter>\n" +
						"                <action android:name=\"android.intent.action.MAIN\" />\n" +
						"                <category android:name=\"android.intent.category.LAUNCHER\" />\n" +
						"            </intent-filter>\n" +
						activityEnd;
						
						updatedManifest = fullMatcher.replaceFirst(Matcher.quoteReplacement(newActivityContent));
					}
				} else {
					addActivityToManifest(activityName); // Add if not exists
					updatedManifest = getManifest(); // Refresh manifest
					String fullActivityPattern = "(<activity[^>]*android:name=\"\\." + Pattern.quote(activityName) + "\"[^>]*>)(.*?)(</activity>)";
					Pattern fullPattern = Pattern.compile(fullActivityPattern, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
					Matcher fullMatcher = fullPattern.matcher(updatedManifest);
					
					if (fullMatcher.find()) {
						String activityStart = fullMatcher.group(1);
						String activityContent = fullMatcher.group(2);
						String activityEnd = fullMatcher.group(3);
						
						String newActivityContent = activityStart +
						activityContent +
						"\n            <intent-filter>\n" +
						"                <action android:name=\"android.intent.action.MAIN\" />\n" +
						"                <category android:name=\"android.intent.category.LAUNCHER\" />\n" +
						"            </intent-filter>\n" +
						activityEnd;
						
						updatedManifest = fullMatcher.replaceFirst(Matcher.quoteReplacement(newActivityContent));
					}
				}
				
				setManifest(prettyPrintXML(updatedManifest));
				validateAndFixManifest(); // Ensure no duplicates
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Method to set extra resources (supports directories)
	public void setExtraResources(String resPath) {
		File resFile = new File(resPath);
		if (resFile.exists() && resFile.isDirectory()) {
			addAllFilesToMap(resFile, "res", extraResources);
		}
	}
	public void setExtraJavaFiles(String javaPath) {
		File javaFile = new File(javaPath);
		if (javaFile.exists() && javaFile.isDirectory()) {
			addAllFilesToMap(javaFile, "src", extraJavaFiles);
		}
	}
	private void addAllFilesToMap(File baseDir, String rootPrefix, HashMap<String, String> map) {
		File[] files = baseDir.listFiles();
		if (files == null) return;
		
		for (File file : files) {
			if (file.isDirectory()) {
				addAllFilesToMap(file, rootPrefix + "/" + file.getName(), map);
			} else {
				try {
					String relativePath = rootPrefix + "/" + getRelativePath(baseDir, file);
					map.put(relativePath, file.getAbsolutePath()); // ✅ Path save kar
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private String getRelativePath(File baseDir, File file) {
		String basePath = baseDir.getAbsolutePath();
		String filePath = file.getAbsolutePath();
		if (filePath.startsWith(basePath)) {
			return filePath.substring(basePath.length() + 1).replace("\\", "/");
		}
		return file.getName();
	}
	
	
	// Utility method to copy files or directories
	public void copyFile(String sourcePath, String destinationPath) throws IOException {
		if (sourcePath == null || sourcePath.trim().isEmpty()) {
			throw new IOException("Source path cannot be null or empty");
		}
		File source = new File(sourcePath);
		if (!source.exists()) {
			throw new IOException("Source does not exist: " + sourcePath);
		}
		File destination = new File(destinationPath);
		destination.getParentFile().mkdirs();
		if (source.isFile()) {
			// Copy single file
			Files.copy(source.toPath(), destination.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
		} else if (source.isDirectory()) {
			// Copy directory recursively
			Path sourceDirPath = source.toPath();
			Path destDirPath = destination.toPath();
			Files.walk(sourceDirPath)
			.forEach(sourceFile -> {
				try {
					Path relativePath = sourceDirPath.relativize(sourceFile);
					Path destFile = destDirPath.resolve(relativePath);
					if (Files.isDirectory(sourceFile)) {
						Files.createDirectories(destFile);
					} else {
						Files.copy(sourceFile, destFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
					}
				} catch (IOException e) {
					throw new RuntimeException("Failed to copy file: " + sourceFile, e);
				}
			});
		}
	}
	
	// Modified exportProject method to handle directories
	public void exportProject(
	String exportDir,
	String resFolderPath,
	String javaFolderPath,
	OnExportProgress progressListener,
	OnExportFailed failureListener,
	OnExportSuccess successListener
	) throws IOException {
		String exportPath = exportDir;
		
		if (progressListener != null) {
			progressListener.onProgress(0, "Initializing export...");
		}
		
		// Create a temporary directory for the project structure
		String tempDirPath = exportPath + File.separator + getProjectName();
		File tempDir = new File(tempDirPath);
		tempDir.mkdirs();
		
		String projectName = getProjectName().replaceAll("[^a-zA-Z0-9]", "_");
		String rootPath = tempDirPath + File.separator + projectName;
		String appPath = rootPath + File.separator + "app";
		String mainPath = appPath + File.separator + "src" + File.separator + "main";
		String javaPath = mainPath + File.separator + "java" + File.separator + getPkgName().replace(".", File.separator);
		String resPath = mainPath + File.separator + "res";
		String layoutPath = resPath + File.separator + "layout";
		String valuesPath = resPath + File.separator + "values";
		String valuesV21Path = resPath + File.separator + "values-v21";
		
		// Create directories
		new File(javaPath).mkdirs();
		new File(layoutPath).mkdirs();
		new File(valuesPath).mkdirs();
		new File(valuesV21Path).mkdirs();
		new File(appPath).mkdirs();
		
		try {
			int progress = 0;
			int totalSteps = 10 + extraResources.size() + extraJavaFiles.size();
			
			if (progressListener != null) {
				progressListener.onProgress(++progress * 100 / totalSteps, "Exporting Java files...");
			}
			//extractAllJavaCodes(javaPath);
            yg javaGenerator = new yg(context, sc_id);
            javaGenerator.setOutPut(javaPath);
            javaGenerator.extractAllJavaCodes();
			
			if (progressListener != null) {
				progressListener.onProgress(++progress * 100 / totalSteps, "Exporting extra Java files...");
			}
			for (Map.Entry<String, String> entry : extraJavaFiles.entrySet()) {
				String relativePath = entry.getKey();
				String sourcePath = entry.getValue();
				String destPath = mainPath + File.separator + "java" + File.separator + relativePath.replace("/", File.separator);
				copyFile(sourcePath, destPath);
				if (progressListener != null) {
					progressListener.onProgress(++progress * 100 / totalSteps, "Exported Java file: " + relativePath);
				}
			}
			
			if (progressListener != null) {
				progressListener.onProgress(++progress * 100 / totalSteps, "Exporting XML layouts...");
			}
            ar xmlRaw = new ar();
            xmlRaw.extractResources(sc_id, valuesPath);
			//extractAllXmlCodes(layoutPath);
            
            yg codeManager = new yg(context, sc_id);
            codeManager.setXmlOutPut(layoutPath);
            codeManager.extractAndSaveAllXmlCodes();
			
			if (progressListener != null) {
				progressListener.onProgress(++progress * 100 / totalSteps, "Exporting manifest...");
			}
            aq manifestManager = new aq();
            manifestManager.extractManifest(sc_id, mainPath);
			//extractManifest(mainPath);
			
			if (progressListener != null) {
				progressListener.onProgress(++progress * 100 / totalSteps, "Exporting resources...");
			}
			if (jsonData.has("res")) {
				JSONObject resData = jsonData.getJSONObject("res");
				if (resData.has("values_v21")) {
					writeFile(new File(valuesV21Path, "styles.xml"), decodeData(resData.getString("values_v21")));
				}
			}
			
			if (progressListener != null) {
				progressListener.onProgress(++progress * 100 / totalSteps, "Exporting extra resources...");
			}
			for (Map.Entry<String, String> entry : extraResources.entrySet()) {
				String relativePath = entry.getKey();
				String sourcePath = entry.getValue(); // ✅ Yeh ab actual path hoga
				String destPath = mainPath + File.separator + relativePath.replace("/", File.separator);
				copyFile(sourcePath, destPath); // ✅ Ab sahi chalega
			}
			
			
			if (progressListener != null) {
				progressListener.onProgress(++progress * 100 / totalSteps, "Exporting Gradle files...");
			}
            gq gradleManager = new gq();
            gradleManager.extractGradleFiles(sc_id, appPath);
			//extractGradleBuild(appPath);
			extractGradleSettings(rootPath);
			//extractGradleBuildConfig(appPath);
			extractProGuardRules(appPath);
			
			addAllFilesToMap(new File(resFolderPath), "res", extraResources);
			addAllFilesToMap(new File(javaFolderPath), "src", extraJavaFiles);
			
			for (Map.Entry<String, String> entry : extraResources.entrySet()) {
				File out = new File(exportDir, entry.getKey());
				out.getParentFile().mkdirs();
				writeFile(out, entry.getValue());
			}
			
			for (Map.Entry<String, String> entry : extraJavaFiles.entrySet()) {
				File out = new File(exportDir, entry.getKey());
				out.getParentFile().mkdirs();
				writeFile(out, entry.getValue());
			}
			
			if (progressListener != null) {
				progressListener.onProgress(++progress * 100 / totalSteps, "Creating gradle.properties...");
			}
			String gradleProperties = "org.gradle.jvmargs=-Xmx1536m\n" +
			"android.useAndroidX=" + getAndroidXEnable() + "\n" +
			"android.enableJetifier=" + getAndroidXEnable();
			writeFile(new File(rootPath, "gradle.properties"), gradleProperties);
			
			if (progressListener != null) {
				progressListener.onProgress(++progress * 100 / totalSteps, "Creating settings.gradle...");
			}
			String settingsGradleContent = getSettingsGradle();
			if (settingsGradleContent.isEmpty()) {
				settingsGradleContent = "include ':app'";
			}
			writeFile(new File(rootPath, "settings.gradle"), settingsGradleContent);
			
			if (progressListener != null) {
				progressListener.onProgress(++progress * 100 / totalSteps, "Creating root build.gradle...");
			}
			String rootBuildGradle =
			"buildscript {\n" +
			"    repositories {\n" +
			"        google()\n" +
			"        mavenCentral()\n" +
			"    }\n" +
			"    dependencies {\n" +
			"        classpath 'com.android.tools.build:gradle:8.1.0'\n" +
			"    }\n" +
			"}\n\n" +
			"allprojects {\n" +
			"    repositories {\n" +
			"        google()\n" +
			"        mavenCentral()\n" +
			"    }\n" +
			"}\n\n" +
			"task clean(type: Delete) {\n" +
			"    delete rootProject.buildDir\n" +
			"}";
			writeFile(new File(rootPath, "build.gradle"), rootBuildGradle);
			
			if (progressListener != null) {
				progressListener.onProgress(++progress * 100 / totalSteps, "Creating .gitignore...");
			}
			String gitignore =
			"*.iml\n" +
			".gradle\n" +
			"/local.properties\n" +
			"/.idea/\n" +
			"/build/\n" +
			"/captures/";
			writeFile(new File(rootPath, ".gitignore"), gitignore);
			
			if (progressListener != null) {
				progressListener.onProgress(95, "Creating ZIP file...");
			}
			String zipFilePath = exportPath + File.separator + projectName + ".zip";
			createZip(rootPath, zipFilePath);
			
			if (progressListener != null) {
				progressListener.onProgress(100, "Export completed successfully.");
			}
			if (successListener != null) {
				successListener.onSuccess(zipFilePath);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			if (failureListener != null) {
				failureListener.onFailed("Export failed: " + e.getMessage());
			}
			throw new IOException("Failed to export project: " + e.getMessage(), e);
		} finally {
			deleteDirectory(tempDir);
		}
	}
	
	
	
	private void createZip(String sourceDirPath, String zipFilePath) throws IOException {
		FileOutputStream fos = new FileOutputStream(zipFilePath);
		ZipOutputStream zos = new ZipOutputStream(fos);
		File sourceDir = new File(sourceDirPath);
		
		addDirectoryToZip(sourceDir, sourceDir.getName(), zos);
		
		zos.close();
		fos.close();
	}
	
	private void addDirectoryToZip(File directory, String basePath, ZipOutputStream zos) throws IOException {
		File[] files = directory.listFiles();
		if (files == null) return;
		
		byte[] buffer = new byte[1024];
		
		for (File file : files) {
			if (file.isDirectory()) {
				addDirectoryToZip(file, basePath + "/" + file.getName(), zos);
				continue;
			}
			
			FileInputStream fis = new FileInputStream(file);
			String zipEntryName = basePath + "/" + file.getName();
			zos.putNextEntry(new ZipEntry(zipEntryName));
			
			int length;
			while ((length = fis.read(buffer)) > 0) {
				zos.write(buffer, 0, length);
			}
			
			zos.closeEntry();
			fis.close();
		}
	}
	
	private void deleteDirectory(File directory) {
		if (directory.isDirectory()) {
			File[] files = directory.listFiles();
			if (files != null) {
				for (File file : files) {
					deleteDirectory(file);
				}
			}
		}
		directory.delete();
	}
	
	public void addDebugActivity() {
	}
	
	public void addBlackApplication() {
	}
	
	public void addBlackLogger() {
	}
	
	public void registerApplicationClass(String applicationClassName) {
		try {
			String manifest = getManifest();
			if (manifest.isEmpty() || !manifest.contains("<application")) {
				// Create a basic manifest if none exists
				manifest = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
				"<manifest xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
				"    package=\"" + getPkgName() + "\">\n\n" +
				"    <application\n" +
				"        android:allowBackup=\"true\"\n" +
				"        android:label=\"" + getProjectName() + "\">\n" +
				"    </application>\n" +
				"</manifest>";
			}
			
			// Check if application tag already has android:name
			String applicationTagPattern = "<application[^>]*>";
			Pattern pattern = Pattern.compile(applicationTagPattern);
			Matcher matcher = pattern.matcher(manifest);
			
			if (matcher.find()) {
				String applicationTag = matcher.group(0);
				if (applicationTag.contains("android:name=\"")) {
					// Update existing android:name
					manifest = manifest.replaceAll(
					"(<application[^>]*android:name=\")[^\"]+(\")",
					"$1." + applicationClassName + "$2"
					);
				} else {
					// Add android:name attribute
					manifest = manifest.replace(
					applicationTag,
					applicationTag.replace(">", " android:name=\"." + applicationClassName + "\">")
					);
				}
			} else {
				// If no application tag, add one
				manifest = manifest.replace(
				"</manifest>",
				"    <application android:name=\"." + applicationClassName + "\"\n" +
				"        android:allowBackup=\"true\"\n" +
				"        android:label=\"" + getProjectName() + "\">\n" +
				"    </application>\n</manifest>"
				);
			}
			
			setManifest(manifest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void refreshData() {
		loadJson(); // Load from disk
		
		runtimeLogicCache.clear();
		javaItems.clear();
		xmlItems.clear();
		xmlToJavaMap.clear();
		javaToXmlMap.clear();
		
		try {
			// Refresh logic
			if (activityLogicStorage != null) {
				Iterator<String> keys = activityLogicStorage.keys();
				while (keys.hasNext()) {
					String encodedKey = keys.next();
					runtimeLogicCache.put(decodeData(encodedKey), decodeData(activityLogicStorage.getString(encodedKey)));
				}
			}
			
			JSONObject meta = jsonData.optJSONObject("meta");
			if (meta != null) {
				// Refresh acName and xName
				JSONArray acArray = meta.optJSONArray("acName");
				JSONArray xArray = meta.optJSONArray("xName");
				if (acArray != null && xArray != null) {
					int len = Math.min(acArray.length(), xArray.length());
					for (int i = 0; i < len; i++) {
						String javaName = decodeData(acArray.getString(i));
						String xmlName = decodeData(xArray.getString(i));
						javaItems.add(javaName);
						xmlItems.add(xmlName);
						xmlToJavaMap.put(xmlName, javaName);
						javaToXmlMap.put(javaName, xmlName);
					}
				}
				
				// Optional: Reload androidX
				boolean androidX = meta.optBoolean("androidX", false);
				// Use androidX variable as needed...
				
				// Optional: Reload toolbar, fab, startup
				JSONObject toolbarData = meta.optJSONObject("toolbar");
				JSONObject fabData = meta.optJSONObject("fab");
				JSONObject startupData = meta.optJSONObject("startup");
				// Use as needed...
				
				// Permissions refresh (if needed)
				JSONArray permissionArray = meta.optJSONArray("permissions");
				// You can repopulate runtime permissions list if you maintain one
			}
			
			// Optional: Refresh manifest, gradle, res, etc.
			String manifest = getManifest();
			String gradle = getGradleBuild();
			String settings = getSettingsGradle();
			String strings = getStringResources();
			String styles = getStyleResources();
			
			// Refresh spinners and UI
			if (xmlSpinner != null) setXmlAdapter(xmlSpinner);
			if (javaSpinner != null) setJavaAdapter(javaSpinner);
			updateFragmentState();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	// Add helper method to find activity by XML name
	private String findActivityByXmlName(String xmlName) {
		try {
			// Check old JSON system as fallback
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData != null && metaData.has("acName") && metaData.has("xName")) {
				JSONArray acNameArray = metaData.getJSONArray("acName");
				JSONArray xNameArray = metaData.getJSONArray("xName");
				
				for (int i = 0; i < Math.min(acNameArray.length(), xNameArray.length()); i++) {
					String storedXmlName = decodeData(xNameArray.getString(i));
					if (xmlName.equals(storedXmlName)) {
						return decodeData(acNameArray.getString(i)).replace(".java", "");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// Add helper method to get XML name for activity
	private String getXmlNameForActivity(String activityName) {
		try {
			// Fallback to old JSON system
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData != null && metaData.has("acName") && metaData.has("xName")) {
				JSONArray acNameArray = metaData.getJSONArray("acName");
				JSONArray xNameArray = metaData.getJSONArray("xName");
				
				for (int i = 0; i < Math.min(acNameArray.length(), xNameArray.length()); i++) {
					String storedActivityName = decodeData(acNameArray.getString(i)).replace(".java", "");
					if (activityName.equals(storedActivityName)) {
						return decodeData(xNameArray.getString(i)).replace(".xml", "");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return activityName;
	}
	
	public void dataMathodLoad() {
		currentActivityBean = new ProjectActivityBean(
		s, // activityName
		"",         // layoutName
		"", // packageName
		true,           // isMainActivity
		sc_id,
		""     // projectName
		);
	}
	
	private void tryLoadData() {
		if (s != null && sc_id != null) {
			dataMathodLoad();
		}
	}
	
	/**
* Correct way to update project - SAME sc_id rakhte huye
*/	
	public boolean updateProject(String newProjectName, String newPackageName) {
		try {
			// ✅ SAME sc_id use karo, naya nahi banayo
			String currentScId = this.sc_id;
			
			// Validate inputs
			if (newProjectName == null || newProjectName.trim().isEmpty()) {
				//Toast.makeText(context, "Project name cannot be empty", Toast.LENGTH_SHORT).show();
				return false;
			}
			
			if (newPackageName == null || newPackageName.trim().isEmpty()) {
				//Toast.makeText(context, "Package name cannot be empty", Toast.LENGTH_SHORT).show();
				return false;
			}
			
			// ✅ Existing project data ko hi update karo
			updateProjectName(newProjectName);
			updatePackageName(newPackageName);
			
			// ✅ Manifest update karo
			updateManifestForRename(newProjectName, newPackageName);
			
			// ✅ Save all changes
			saveJson();
			
			//Toast.makeText(context, "Project updated successfully!", Toast.LENGTH_SHORT).show();
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			// Toast.makeText(context, "Error updating project: " + e.getMessage(), Toast.LENGTH_SHORT).show();
			return false;
		}
	}
	
	/**
* Manifest ko bhi update karo new name/package ke saath
*/	
	private void updateManifestForRename(String newProjectName, String newPackageName) {
		try {
			String manifest = getManifest();
			
			// Update package name in manifest
			if (manifest.contains("package=\"")) {
				String oldPackage = getPkgName();
				manifest = manifest.replace(
				"package=\"" + oldPackage + "\"", 
				"package=\"" + newPackageName + "\""
				);
			}
			
			// Update app label in manifest
			if (manifest.contains("android:label=\"")) {
				String oldName = getProjectName();
				manifest = manifest.replace(
				"android:label=\"" + oldName + "\"",
				"android:label=\"" + newProjectName + "\""
				);
			}
			
			setManifest(manifest);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
* Deletes the physical XML and Java files for a view.
* @param xmlName The XML name (e.g., "activity_main")
* @param javaName The Java name (e.g., "MainActivity")
*/	
	public void deleteViewFiles(String xmlName, String javaName) {
		try {
			// Delete Java file
			File javaFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + 
			"/.blacklogics/data/" + sc_id + "/files/java/" + javaName + ".java");
			if (javaFile.exists()) {
				javaFile.delete();
			}
			
			// Remove from JSON
			JSONObject xmlData = jsonData.optJSONObject("xml");
			if (xmlData != null) {
				xmlData.remove(encodeData(xmlName));
				jsonData.put("xml", xmlData);
			}
			
			JSONObject javaData = jsonData.optJSONObject("java");
			if (javaData != null) {
				javaData.remove(encodeData(javaName + ".java"));
				jsonData.put("java", javaData);
			}
			
			saveJson();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
* Deletes the physical file for a custom view and removes it from JSON.
* @param viewName The name of the custom view
*/	
	public void deleteCustomViewFile(String viewName) {
		try {
			// Remove from JSON
			JSONObject xmlData = jsonData.optJSONObject("xml");
			if (xmlData != null) {
				xmlData.remove(encodeData(viewName));
				jsonData.put("xml", xmlData);
			}
			
			saveJson();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 1. Sab XML names (xName) निकालने का method
	public List<String> getAllXNames() {
		List<String> names = new ArrayList<>();
		try {
			JSONObject metaData = jsonData.optJSONObject("meta");
			if (metaData != null && metaData.has("xName")) {
				JSONArray xNameArray = metaData.getJSONArray("xName");
				for (int i = 0; i < xNameArray.length(); i++) {
					names.add(decodeData(xNameArray.getString(i)));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return names;
	}
	
	// 2. JSON → XML में बदलने वाला recursive helper
	private String generateXmlFromJson(JSONObject view, int indent) {
		return "";
	}
	
	// 3. ProjectLayoutData से सारे layouts का XML generate करना
	public Map<String, String> generateAllXmlFromLayouts(Context context) {
		Map<String, String> xmlMap = new HashMap<>();
		return xmlMap;
	}
	
	// 4. extractAllXmlCodes → सब XMLs को files में save करना
	public void extractAllXmlCodes(Context context, File outDir) {
		Map<String, String> xmls = generateAllXmlFromLayouts(context);
		for (Map.Entry<String, String> entry : xmls.entrySet()) {
			File outFile = new File(outDir, entry.getKey());
			FileUtil.writeFile(outFile.getAbsolutePath(), entry.getValue());
		}
	}
	
	/**
* Generates Java code for all activities by iterating over acName and xName arrays
* in the JSON meta data, calling DesignActivity.generateJavaCode for each activity-layout pair.
*/	
	public void generateAllJavaCodes() {
	}
	
	/**
* Clears all runtime caches and temporary data
* Useful for freeing memory and resetting state
*/	
	public void clearCache() {
		try {
			// Clear runtime logic cache
			if (runtimeLogicCache != null) {
				runtimeLogicCache.clear();
			}
			
			// Clear activity logic map
			if (activityLogicMap != null) {
				activityLogicMap.clear();
			}
			
			// Clear cached logic
			if (cachedLogic != null) {
				cachedLogic.clear();
			}
			
			// Clear lists
			if (items != null) {
				items.clear();
			}
			
			if (xmlItems != null) {
				xmlItems.clear();
			}
			
			if (javaItems != null) {
				javaItems.clear();
			}
			
			// Clear maps
			if (xmlToJavaMap != null) {
				xmlToJavaMap.clear();
			}
			
			if (javaToXmlMap != null) {
				javaToXmlMap.clear();
			}
			
			// Clear extra resources and Java files
			if (extraResources != null) {
				extraResources.clear();
			}
			
			if (extraJavaFiles != null) {
				extraJavaFiles.clear();
			}
			
			// Reset selection trackers
			lastSelectedXml = "";
			lastSelectedJava = "";
			
			// Clear spinner references
			javaSpinner = null;
			xmlSpinner = null;
			
			// Clear context reference
			context = null;
			
			// Clear current activity bean
			currentActivityBean = null;
			
			//System.out.println("All caches cleared successfully");
			
		} catch (Exception e) {
			//System.err.println("Error clearing cache: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
* Comprehensive cleanup method that clears cache and performs additional cleanup tasks
* Use this when the Complex instance is no longer needed or when switching projects
*/	
	public void cleanup() {
		try {
			// First clear all caches
			clearCache();
			
			// Additional cleanup tasks
			
			// Clear JSON data references (but don't delete the actual files)
			if (jsonData != null) {
				// We don't set to null as it might be needed, but we can clear specific sections
				// if they're taking too much memory
				if (jsonData.has("logic")) {
					// Clear logic cache from JSON but keep the structure
					JSONObject logicData = jsonData.optJSONObject("logic");
					if (logicData != null) {
						Iterator<String> keys = logicData.keys();
						while (keys.hasNext()) {
							keys.next();
							// We keep the data but you could remove it here if needed
						}
					}
				}
			}
			
			// Clear activity logic storage reference
			activityLogicStorage = null;
			
			// Clear project data reference
			projectData = null;
			
			// Force garbage collection (suggestion only, JVM will decide)
			System.gc();
			
			//System.out.println("Cleanup completed successfully");
			
		} catch (Exception e) {
			//System.err.println("Error during cleanup: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
* Light cleanup - clears only the most memory-intensive caches
* Use this for frequent cleanup during normal operation
*/	
	public void lightCleanup() {
		try {
			// Clear only the largest caches
			if (runtimeLogicCache != null) {
				runtimeLogicCache.clear();
			}
			
			if (extraResources != null) {
				extraResources.clear();
			}
			
			if (extraJavaFiles != null) {
				extraJavaFiles.clear();
			}
			
			// Clear lists
			if (items != null) {
				items.clear();
			}
			
			//System.out.println("Light cleanup completed");
			
		} catch (Exception e) {
			//System.err.println("Error during light cleanup: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
* Project-specific cleanup - clears data related to current project
* Use this when switching between projects
*/	
	public void cleanupProjectData() {
		try {
			// Clear project-specific data
			if (runtimeLogicCache != null) {
				runtimeLogicCache.clear();
			}
			
			if (activityLogicMap != null) {
				activityLogicMap.clear();
			}
			
			// Clear project lists and mappings
			if (xmlItems != null) {
				xmlItems.clear();
			}
			
			if (javaItems != null) {
				javaItems.clear();
			}
			
			if (xmlToJavaMap != null) {
				xmlToJavaMap.clear();
			}
			
			if (javaToXmlMap != null) {
				javaToXmlMap.clear();
			}
			
			// Reset current activity
			s = "MainActivity";
			currentActivityBean = null;
			
			// Reset selection trackers
			lastSelectedXml = "";
			lastSelectedJava = "";
			
			//System.out.println("Project data cleaned up");
			
		} catch (Exception e) {
			//System.err.println("Error cleaning up project data: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private String getProjectPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath() + "/.blacklogics/data/" + sc_id;
	}
	
	// New method to generate virtual XML from WidgetAttributesManager attributes
	public String getXmlAndExtract(String activityName) {
		WidgetAttributesManager am = new WidgetAttributesManager(context, getProjectPath(), getScName(), sc_id, getPkgName());
		Map<String, Map<String, String>> widgets = am.getWidgetsForActivity(activityName);
		if (widgets == null || widgets.isEmpty()) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		sb.append("<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n");
		sb.append("    android:layout_width=\"match_parent\"\n");
		sb.append("    android:layout_height=\"match_parent\"\n");
		sb.append("    android:orientation=\"vertical\">\n\n");
		
		for (Map.Entry<String, Map<String, String>> widgetEntry : widgets.entrySet()) {
			String widgetId = widgetEntry.getKey();
			Map<String, String> attrs = widgetEntry.getValue();
			String type = attrs.get("type");
			if (type == null) continue;
			
			sb.append("    <").append(type);
			sb.append(" android:id=\"@+id/").append(widgetId).append("\"");
			
			for (Map.Entry<String, String> attrEntry : attrs.entrySet()) {
				String attrName = attrEntry.getKey();
				if (attrName.equals("type")) continue;
				String attrValue = attrEntry.getValue();
				sb.append("\n        android:").append(attrName).append("=\"").append(attrValue).append("\"");
			}
			
			sb.append(" />\n\n");
		}
		
		sb.append("</LinearLayout>\n");
		return sb.toString();
	}
	
	/**
* Removes duplicate activity entries from the manifest, merging attributes logically.
* Ensures the output is pretty-printed for readability.
*/	
	public void removeDuplicateActivities() {
	}
	
	/**
* Validates the manifest and fixes common issues like duplicate activities and multiple launchers.
* Ensures the output is pretty-printed and valid.
*/	
	public void validateAndFixManifest() {
		try {
			String manifest = getManifest();
			if (manifest == null || manifest.trim().isEmpty()) {
				Log.w("ManifestFix", "Manifest is empty or null, initializing default");
				setManifest(createDefaultManifest());
				return;
			}
			
			// Check for valid XML structure
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			try {
				builder.parse(new InputSource(new StringReader(manifest)));
			} catch (Exception e) {
				Log.w("ManifestFix", "Invalid XML structure, resetting to default manifest");
				setManifest(createDefaultManifest());
				return;
			}
			
			// Remove duplicate activities
			removeDuplicateActivities();
			manifest = getManifest();
			
			// Ensure only one launcher activity
			Pattern launcherPattern = Pattern.compile(
			"<category\\s+android:name\\s*=\\s*\"android.intent.category.LAUNCHER\"\\s*/>",
			Pattern.CASE_INSENSITIVE
			);
			Matcher launcherMatcher = launcherPattern.matcher(manifest);
			int launcherCount = 0;
			while (launcherMatcher.find()) {
				launcherCount++;
			}
			
			if (launcherCount > 1) {
				Log.w("ManifestFix", "Multiple launcher activities found: " + launcherCount + ", setting MainActivity as launcher");
				setLauncherActivity("MainActivity");
				manifest = getManifest();
			} else if (launcherCount == 0) {
				Log.w("ManifestFix", "No launcher activity found, setting MainActivity as launcher");
				setLauncherActivity("MainActivity");
				manifest = getManifest();
			}
			
			// Ensure android:exported is set for activities with intent-filters (Android 12+ requirement)
			Pattern activityPattern = Pattern.compile(
			"(<activity\\s+[^>]*>)(.*?)(</activity>|<activity\\s+[^>]*/>)",
			Pattern.DOTALL | Pattern.CASE_INSENSITIVE
			);
			Matcher activityMatcher = activityPattern.matcher(manifest);
			StringBuilder updatedManifest = new StringBuilder();
			
			int lastEnd = 0;
			while (activityMatcher.find()) {
				String activityStart = activityMatcher.group(1);
				String activityContent = activityMatcher.group(2);
				String activityEnd = activityMatcher.group(3);
				
				// Check if activity has intent-filter
				boolean hasIntentFilter = activityContent.contains("<intent-filter");
				if (hasIntentFilter && !activityStart.contains("android:exported")) {
					Log.d("ManifestFix", "Adding android:exported to activity with intent-filter");
					activityStart = activityStart.replace("<activity", "<activity android:exported=\"true\"");
				}
				
				updatedManifest.append(manifest.substring(lastEnd, activityMatcher.start()));
				updatedManifest.append(activityStart).append(activityContent).append(activityEnd);
				lastEnd = activityMatcher.end();
			}
			updatedManifest.append(manifest.substring(lastEnd));
			
			// Pretty-print the final manifest
			String finalManifest = prettyPrintXML(updatedManifest.toString());
			setManifest(finalManifest);
			Log.d("ManifestFix", "Manifest validated and fixed successfully");
			
		} catch (Exception e) {
			Log.e("ManifestFix", "Error validating manifest: " + e.getMessage());
			e.printStackTrace();
			// Fallback to default manifest
			setManifest(createDefaultManifest());
		}
	}
	
	/**
* Creates a default manifest if the current one is invalid or empty.
* @return A default manifest string
*/	
	private String createDefaultManifest() {
		String defaultManifest =
		"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
		"<manifest xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
		"    package=\"" + getPkgName() + "\">\n" +
		"    <application\n" +
		"        android:allowBackup=\"true\"\n" +
		"        android:icon=\"@mipmap/icon\"\n" +
		"        android:label=\"" + getProjectName() + "\"\n" +
		"        android:theme=\"@style/AppTheme\"\n" +
		"        android:name=\".BlackApplication\">\n" +
		"        <activity\n" +
		"            android:name=\".MainActivity\"\n" +
		"            android:exported=\"true\"\n" +
		"            android:theme=\"@style/AppTheme\"\n" +
		"            android:label=\"MainActivity\">\n" +
		"            <intent-filter>\n" +
		"                <action android:name=\"android.intent.action.MAIN\" />\n" +
		"                <category android:name=\"android.intent.category.LAUNCHER\" />\n" +
		"            </intent-filter>\n" +
		"        </activity>\n" +
		"        <activity\n" +
		"            android:name=\".DebugActivity\"\n" +
		"            android:theme=\"@style/DebugTheme\"\n" +
		"            android:exported=\"true\" />\n" +
		"    </application>\n" +
		"</manifest>";
		return prettyPrintXML(defaultManifest);
	}
	/**
* Pretty prints an XML string with consistent, clean indentation (4 spaces).
* Ensures elements are properly aligned one below the other, attributes are sorted,
* and no extra blank lines or whitespace remain.
* @param xml The input XML string to format
* @return The formatted XML string, or the original string if formatting fails
*/	
	public String prettyPrintXML(String xml) {
	  return "";
	}
	
	/**
* Helper method to recursively format DOM nodes with proper indentation.
* @param node The current DOM node
* @param level The indentation level (number of 4-space indents)
* @param sb The StringBuilder to append formatted XML to
*/	
	private void formatNode(Node node, int level, StringBuilder sb) {
	}
	
	/**
* Escapes special characters in XML attribute values or text content.
* @param value The string to escape
* @return Escaped string
*/	
	private String escapeXml(String value) {
		if (value == null) return "";
		return value.replace("&", "&amp;")
		.replace("<", "&lt;")
		.replace(">", "&gt;")
		.replace("\"", "&quot;")
		.replace("'", "&apos;");
	}
	
	private boolean hasActivityInManifest(String activityName) {
		String manifest = getManifest();
		return manifest != null && 
		manifest.contains("android:name=\"." + activityName + "\"");
	}
	/**
* Checks if there are multiple launcher activities in the manifest
* @return true if multiple launchers found, false otherwise
*/	
	public boolean hasDuplicateLauncher() {
		try {
			String manifest = getManifest();
			if (manifest == null || manifest.trim().isEmpty()) {
				return false;
			}
			
			Pattern launcherPattern = Pattern.compile(
			"<category\\s+android:name\\s*=\\s*\"android.intent.category.LAUNCHER\"\\s*/>",
			Pattern.CASE_INSENSITIVE
			);
			
			Matcher matcher = launcherPattern.matcher(manifest);
			int launcherCount = 0;
			while (matcher.find()) {
				launcherCount++;
			}
			
			return launcherCount > 1;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
* Checks if an activity exists in the manifest by its name.
* @param activityName The name of the activity to check (e.g., "MainActivity")
* @return true if the activity exists in the manifest, false otherwise
*/	
	public boolean isActivityExist(String activityName) {
		try {
			String manifest = getManifest();
			if (manifest == null || manifest.trim().isEmpty()) {
				return false;
			}
			
			// Use regex to find activity with given android:name
			String patternString = "<activity\\s+[^>]*android:name\\s*=\\s*\"\\." + Pattern.quote(activityName) + "\"";
			Pattern activityPattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
			Matcher matcher = activityPattern.matcher(manifest);
			
			return matcher.find();
		} catch (Exception e) {
			Log.e("isActivityExist", "Error checking activity existence: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	/**
* Migrates an existing project to AndroidX - updates styles, themes, dependencies and manifest
*/	
	public void migrateToAndroidX() {
	}
	/**
* Updates manifest activity themes to AndroidX equivalents
*/	
	private void migrateManifestThemesToAndroidX() {
	
	}
	
	/**
* Updates all styles and themes to use AndroidX equivalents
*/	
	private void migrateStylesToAndroidX() {
		
	}
	public void syncAllSavedXmlFromViewEditor(String viewEditorSavePath) {
	}
	
	// **LAYOUT LIST SE ACTIVITY KA XML CODE DHUNDHNA**
	private String findXmlCodeForActivity(ArrayList<HashMap<String, Object>> layoutList, String activityName, String xmlFileName) {
		return null;
	}
	
	// **CHECK KARO KI LAYOUT NAME ACTIVITY NAME SE MATCH KARTA HAI**
	private boolean doesLayoutMatchActivity(String layoutName, String activityName) {
		return false;
	}
	
	// **CHECK KARO KI LAYOUT NAME XML FILE NAME SE MATCH KARTA HAI**
	private boolean doesLayoutMatchXmlFile(String layoutName, String xmlFileName) {
		if (layoutName == null || xmlFileName == null) return false;
		
		String cleanLayout = layoutName.replace(".xml", "").trim().toLowerCase();
		String cleanXmlFile = xmlFileName.replace(".xml", "").trim().toLowerCase();
		
		return cleanLayout.equals(cleanXmlFile);
	}
	
	// **ACTIVITY NAME SE XML FILE NAME GENERATE KARNA**
	private String generateXmlFileNameFromActivity(String activityName) {
	 return "";
	}
	
	
}
