package com.nexusteam.blacklogics.generator.source;

import android.content.Context;
import android.util.Log;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nexusteam.blacklogics.generator.source.AndroidManifestGenerator;
import com.nexusteam.blacklogics.generator.source.model.ActivityStructureRegistry;
import com.nexusteam.blacklogics.generator.source.model.ActivityType;
import com.nexusteam.blacklogics.generator.source.model.ActivityTypeRegistry;
import com.nexusteam.blacklogics.logic.ProjectLogicRepository;
import com.nexusteam.blacklogics.security.crypto.FileEncryptionUtil;
import com.nexusteam.blacklogics.editor.layout.model.LayoutData;
import com.besome.blacklogics.development.Complex;
import com.shapun.layouteditor.utils.AttributeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import android.util.Base64;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class SourceCodeGenerator {
	
	private AndroidManifestGenerator packageManager;
	private ActivityStructureRegistry activityManager;
	private ActivityTypeRegistry activityTypeRegistry;
	private String sc_id;
	private String projectPath;
	private String output;
	private String xmlOutPut;
	private Gson gson;
	private Complex c;
	
	private final Set<String> LIFECYCLE_METHODS;
	private final Set<String> SYSTEM_CALLBACKS_WITH_SUPER;
	
	private static final char[] ENC_PASSWORD = "blacklogic_layout_secure".toCharArray();
	private String cachedLayoutJson = null;
	private long cacheLastModified = -1;
	private String layoutPath = "";
	private static final char[] ENCRYPT_PASSWORD = "NexusTeamStrongKey!".toCharArray();
	
	public SourceCodeGenerator(Context context, String sc_id) {
		this.packageManager = new AndroidManifestGenerator();
		this.activityManager = new ActivityStructureRegistry();
		this.activityTypeRegistry = ActivityTypeRegistry.getInstance();
		this.sc_id = sc_id;
		this.c = new Complex();
		this.c.setId(sc_id);
		this.projectPath = "/storage/emulated/0" + "/.blacklogics/data/" + sc_id;
		this.gson = new GsonBuilder().setPrettyPrinting().create();
		this.activityManager.load(context, sc_id);
		this.activityTypeRegistry.load(context, sc_id);
		this.packageManager.load(context, sc_id);
		ProjectLogicRepository.setPath(projectPath);
		String projectDataDir = com.nexusteam.internal.fe.d(sc_id);
		this.layoutPath = projectDataDir;
		
		JavaCodeBuilder.setCurrentProject(sc_id, context);
		
		this.LIFECYCLE_METHODS = new HashSet<String>();
		this.LIFECYCLE_METHODS.add("onCreate");
		this.LIFECYCLE_METHODS.add("onStart");
		this.LIFECYCLE_METHODS.add("onResume");
		this.LIFECYCLE_METHODS.add("onPause");
		this.LIFECYCLE_METHODS.add("onStop");
		this.LIFECYCLE_METHODS.add("onDestroy");
		this.LIFECYCLE_METHODS.add("onBackPressed");
		this.LIFECYCLE_METHODS.add("onActivityResult");
		this.LIFECYCLE_METHODS.add("onRequestPermissionsResult");
		this.LIFECYCLE_METHODS.add("onConfigurationChanged");
		this.LIFECYCLE_METHODS.add("onSaveInstanceState");
		this.LIFECYCLE_METHODS.add("onRestoreInstanceState");
		this.LIFECYCLE_METHODS.add("onRestart");
		this.LIFECYCLE_METHODS.add("onNewIntent");
		this.LIFECYCLE_METHODS.add("onWindowFocusChanged");
		
		this.SYSTEM_CALLBACKS_WITH_SUPER = new HashSet<String>();
		this.SYSTEM_CALLBACKS_WITH_SUPER.add("onCreate");
		this.SYSTEM_CALLBACKS_WITH_SUPER.add("onStart");
		this.SYSTEM_CALLBACKS_WITH_SUPER.add("onResume");
		this.SYSTEM_CALLBACKS_WITH_SUPER.add("onPause");
		this.SYSTEM_CALLBACKS_WITH_SUPER.add("onStop");
		this.SYSTEM_CALLBACKS_WITH_SUPER.add("onDestroy");
		this.SYSTEM_CALLBACKS_WITH_SUPER.add("onSaveInstanceState");
		this.SYSTEM_CALLBACKS_WITH_SUPER.add("onRestoreInstanceState");
		this.SYSTEM_CALLBACKS_WITH_SUPER.add("onRestart");
		this.SYSTEM_CALLBACKS_WITH_SUPER.add("onActivityResult");
		this.SYSTEM_CALLBACKS_WITH_SUPER.add("onRequestPermissionsResult");
		this.SYSTEM_CALLBACKS_WITH_SUPER.add("onConfigurationChanged");
	}
	
	public void setOutPut(String output) {
		this.output = output;
	}
	
	public void setXmlOutPut(String xmlOutPut) {
		this.xmlOutPut = xmlOutPut;
	}
	
	
	public void extractAllJavaCodes() {
		ProjectLogicRepository.resetCache();
		try {
			ProjectLogicRepository.resetCache();
			
			List<ActivityStructureRegistry.ViewItem> activities = activityManager.getActivities();
			Map<String, String> allJavaCodes = new LinkedHashMap<String, String>();
			
			for (int i = 0; i < activities.size(); i++) {
				ActivityStructureRegistry.ViewItem activity = activities.get(i);
				String activityName = activity.getJavaName(); 
				String layoutName = activity.getXmlName();   
				
				System.out.println("Extracting code for: " + activityName);
				
				String javaCode = generateCompleteJavaCode(activityName, layoutName);
				saveActivityCode(activityName, javaCode);
				allJavaCodes.put(activityName, javaCode);
			}
			
			saveAllCodes(allJavaCodes);
			System.out.println("✅ Successfully extracted " + allJavaCodes.size() + " activities!");
			
		} catch (Exception e) {
			System.err.println("❌ Error extracting codes: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public String generateCompleteJavaCode(String activityName, String layoutName) {
		StringBuilder javaCode = new StringBuilder();
		
		
		com.nexusteam.blacklogics.generator.source.model.ActivityConfig config = activityTypeRegistry.getActivityConfig(activityName);
		ActivityType activityType = ActivityType.ACTIVITY;
		if (config != null) {
			activityType = config.getType();
		}
		
		
		javaCode.append("package ").append(extractPackageName()).append(";\n\n");
		
		
		JavaCodeImporter.addRequiredImports(javaCode, activityName, projectPath);
		JavaCodeImporter.addAndroidXImports(javaCode, c);
		addImportsSection(javaCode, activityName);
		
		
		if (activityType != null) {
			
			if (activityType == ActivityType.FRAGMENT_ACTIVITY) {
				javaCode.append("public class ").append(activityName).append(" extends Fragment {\n\n");
			}
			else if (activityType == ActivityType.DIALOG_FRAGMENT) {
				javaCode.append("public class ").append(activityName).append(" extends DialogFragment {\n\n");
			}
			else if (activityType == ActivityType.APP_COMPAT_DIALOG_FRAGMENT) {
				javaCode.append("public class ").append(activityName).append(" extends AppCompatDialogFragment {\n\n");
			}
			else if (activityType == ActivityType.BOTTOM_SHEET_DIALOG_FRAGMENT) {
				javaCode.append("public class ").append(activityName).append(" extends BottomSheetDialogFragment {\n\n");
			}
			else if (activityType == ActivityType.LIST_FRAGMENT) {
				javaCode.append("public class ").append(activityName).append(" extends ListFragment {\n\n");
			}
			else if (activityType == ActivityType.PREFERENCE_FRAGMENT) {
				javaCode.append("public class ").append(activityName).append(" extends PreferenceFragmentCompat {\n\n");
			}
			
			else {
				if (c != null && c.getAndroidXEnable()) {
					javaCode.append("public class ").append(activityName).append(" extends AppCompatActivity {\n\n");
				} else {
					javaCode.append("public class ").append(activityName).append(" extends Activity {\n\n");
				}
			}
		} else {
			
			if (c != null && c.getAndroidXEnable()) {
				javaCode.append("public class ").append(activityName).append(" extends AppCompatActivity {\n\n");
			} else {
				javaCode.append("public class ").append(activityName).append(" extends Activity {\n\n");
			}
		}
		
		
		JavaCodeBuilder.addFieldsSection(javaCode, activityName, projectPath, layoutPath);
		JavaCodeBuilder.addOnCreateMethod(javaCode, activityName, layoutName);
		JavaCodeBuilder.addInitializeMethod(javaCode, activityName, projectPath, layoutPath);
		JavaCodeBuilder.addInitializeLogicMethod(javaCode, activityName, projectPath);
		JavaCodeBuilder.getBlockLogicForLifecycleEvent(javaCode, activityName, projectPath);
		JavaCodeBuilder.addCustomFunctions(javaCode, activityName, projectPath, sc_id);
		JavaCodeBuilder.addMoreBlocksCode(javaCode, activityName, sc_id);
		JavaCodeBuilder.addUtilityMethods(javaCode, projectPath);
		
		javaCode.append("}\n");
		return javaCode.toString();
	}
	
	
	public void extractAndSaveAllXmlCodes() {
		System.out.println("Starting ENCRYPTED BINARY XML extraction...");
		
		try {
			List<ActivityStructureRegistry.ViewItem> activities = activityManager.getActivities();
			
			File binFile = new File(layoutPath + "/layout.bin");
			if (!binFile.exists()) {
				System.err.println("❌ layout.bin not found");
				return;
			}
			
			ArrayList<LayoutData> layouts = readAllLayoutsBinary(binFile);
			if (layouts == null) {
				System.err.println("❌ No layouts found");
				return;
			}
			
			new File(xmlOutPut).mkdirs();
			int savedCount = 0;
			
			for (int i = 0; i < activities.size(); i++) {
				ActivityStructureRegistry.ViewItem activity = activities.get(i);
				String activityName = activity.getJavaName();
				String layoutName = activity.getXmlName();
				
				boolean found = false;
				
				for (int j = 0; j < layouts.size(); j++) {
					LayoutData data = layouts.get(j);
					
					if (activityName.equals(data.name)) {
						saveXmlCode(layoutName, data.xml);
						savedCount++;
						found = true;
						break;
					}
				}
				
				if (!found) {
					String emptyXml =
					"<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
					"    android:layout_width=\"match_parent\"\n" +
					"    android:layout_height=\"match_parent\"\n" +
					"    android:orientation=\"vertical\">\n\n" +
					"</LinearLayout>";
					
					saveXmlCode(layoutName, emptyXml);
					savedCount++;
				}
			}
			
			System.out.println("✅ Extracted " + savedCount + " XML layouts to: " + xmlOutPut);
			
		} catch (Exception e) {
			System.err.println("❌ Binary XML extraction failed");
			e.printStackTrace();
		}
	}
	
	public String getLayoutXml(String layoutName) {
		if (layoutName == null || layoutName.trim().isEmpty()) return null;
		
		try {
			File binFile = new File(layoutPath + "/layout.bin");
			if (!binFile.exists()) return null;
			
			ArrayList<LayoutData> layouts = readAllLayoutsBinary(binFile);
			if (layouts == null) return null;
			
			for (int i = 0; i < layouts.size(); i++) {
				LayoutData d = layouts.get(i);
				if (layoutName.equals(d.name)) {
					return d.xml;
				}
			}
		} catch (Exception e) {
			Log.e("CodeGenerator", "Failed to load layout XML", e);
		}
		
		return null;
	}
	
	
	private String extractPackageName() {
		try {
			return packageManager.getPackageName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "com.example.app";
	}
	
	
	private String readEncryptedJson(File file) throws Exception {
		long modified = file.lastModified();
		
		if (cachedLayoutJson != null && cacheLastModified == modified) {
			return cachedLayoutJson;
		}
		
		byte[] decrypted = FileEncryptionUtil.decryptFromFile(file, ENC_PASSWORD);
		String json = new String(decrypted, "UTF-8");
		
		cachedLayoutJson = json;
		cacheLastModified = modified;
		
		return json;
	}
	
	
	@SuppressWarnings("unchecked")
	private ArrayList<LayoutData> readAllLayoutsBinary(File file) throws Exception {
		if (!file.exists()) return null;
		
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			Object obj = ois.readObject();
			if (obj instanceof ArrayList) {
				return (ArrayList<LayoutData>) obj;
			} else {
				return new ArrayList<LayoutData>();
			}
		} finally {
			if (ois != null) {
				try { ois.close(); } catch (IOException e) { e.printStackTrace(); }
			}
			if (fis != null) {
				try { fis.close(); } catch (IOException e) { e.printStackTrace(); }
			}
		}
	}
	
	
	private String decodeUnicodeEscapes(String input) {
		StringBuilder output = new StringBuilder();
		int i = 0;
		while (i < input.length()) {
			if (input.charAt(i) == '\\' && i + 5 < input.length() && input.charAt(i + 1) == 'u') {
				try {
					String hex = input.substring(i + 2, i + 6);
					char unicodeChar = (char) Integer.parseInt(hex, 16);
					output.append(unicodeChar);
					i += 6;
				} catch (NumberFormatException e) {
					output.append(input.charAt(i));
					i++;
				}
			} else {
				output.append(input.charAt(i));
				i++;
			}
		}
		return output.toString();
	}
	
	
	private void saveActivityCode(String activityName, String javaCode) {
		FileWriter writer = null;
		try {
			String outputDir = output;
			new File(outputDir).mkdirs();
			
			String filePath = outputDir + activityName + ".java";
			writer = new FileWriter(filePath);
			writer.write(javaCode);
			writer.flush();
			System.out.println("✅ Saved: " + filePath);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try { writer.close(); } catch (IOException e) { e.printStackTrace(); }
			}
		}
	}
	
	
	private void saveXmlCode(String layoutName, String xmlCode) {
		FileWriter writer = null;
		try {
			String outputDir = xmlOutPut;
			new File(outputDir).mkdirs();
			
			String filePath = outputDir + layoutName + ".xml";
			writer = new FileWriter(filePath);
			writer.write(xmlCode);
			writer.flush();
			System.out.println("✅ Saved XML: " + filePath);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try { writer.close(); } catch (IOException e) { e.printStackTrace(); }
			}
		}
	}
	
	
	private void saveAllCodes(Map<String, String> allJavaCodes) {
		FileWriter writer = null;
		try {
			String outputPath = projectPath + "/all_extracted_codes.json";
			String json = gson.toJson(allJavaCodes);
			
			writer = new FileWriter(outputPath);
			writer.write(json);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try { writer.close(); } catch (IOException e) { e.printStackTrace(); }
			}
		}
	}
	public static void addImportsSection(StringBuilder javaCode, String activityName) {
		String imports = ProjectLogicRepository.getImports(activityName);
		
		if (imports != null && !imports.isEmpty()) {
			Set<String> importSet = new LinkedHashSet<String>(); // ✅ no duplicate + order maintain
			
			String[] importLines = imports.split("\n");
			
			for (String importLine : importLines) {
				String trimmed = importLine.trim();
				
				if (!trimmed.isEmpty()) {
					
					// Fix format
					if (!trimmed.startsWith("import ")) {
						trimmed = "import " + trimmed;
					}
					if (!trimmed.endsWith(";")) {
						trimmed = trimmed + ";";
					}
					
					importSet.add(trimmed);
				}
			}
			
			// ✅ Insert at top (not append)
			StringBuilder importBlock = new StringBuilder();
			
			for (String imp : importSet) {
				importBlock.append(imp).append("\n");
			}
			
			importBlock.append("\n");
			
			javaCode.insert(0, importBlock.toString());
		}
	}
	
	// SourceCodeGenerator.java mein add karo
	public void generateRecyclerViewAdapter(StringBuilder j, String activityName, String recyclerViewId, String itemLayoutName) {
		String layoutPath = this.layoutPath; // already defined in SourceCodeGenerator
		
		// JavaCodeBuilder mein method call karo
		JavaCodeBuilder.addRecyclerViewAdapter(j, recyclerViewId, itemLayoutName, layoutPath);
	}
	
	
	public ActivityType getActivityType(String activityName) {
		com.nexusteam.blacklogics.generator.source.model.ActivityConfig config = activityTypeRegistry.getActivityConfig(activityName);
		if (config != null) {
			return config.getType();
		}
		return ActivityType.ACTIVITY;
	}
	
	
	public void registerActivityType(String activityName, ActivityType type, String layoutName) {
		activityTypeRegistry.registerActivity(activityName, type, layoutName);
	}
}
