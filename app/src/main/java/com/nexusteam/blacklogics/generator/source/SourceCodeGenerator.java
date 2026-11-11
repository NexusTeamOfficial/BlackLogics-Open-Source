package com.nexusteam.blacklogics.generator.source;

import android.content.Context;
import android.util.Log;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nexusteam.blacklogics.design.AppCompatInjectionReader;
import com.nexusteam.blacklogics.generator.source.AndroidManifestGenerator;
import com.nexusteam.blacklogics.generator.source.model.ActivityStructureRegistry;
import com.nexusteam.blacklogics.generator.source.model.ActivityType;
import com.nexusteam.blacklogics.generator.source.model.ActivityTypeRegistry;
import com.nexusteam.blacklogics.logic.ProjectLogicRepository;
import com.nexusteam.blacklogics.security.crypto.FileEncryptionUtil;
import com.nexusteam.blacklogics.editor.layout.model.LayoutData;
import com.nexusteam.blacklogics.editor.layout.LayoutLoader;
import com.nexusteam.blacklogics.development.Complex;
import com.nexusteam.blacklogics.sdk.utils.AttributeUtils;
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
	
	private static final String DEFAULT_LAYOUT_XML =
	"<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
	"<LinearLayout\n" +
	"    xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
	"    xmlns:app=\"http://schemas.android.com/apk/res-auto\"\n" +
	"    xmlns:tools=\"http://schemas.android.com/tools\"\n" +
	"    android:layout_width=\"match_parent\"\n" +
	"    android:layout_height=\"match_parent\"\n" +
	"    android:orientation=\"vertical\" />";
	
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
			
			int skippedCount = 0;
			
			for (int i = 0; i < activities.size(); i++) {
				ActivityStructureRegistry.ViewItem activity = activities.get(i);
				String activityName = activity.getJavaName(); 
				String layoutName = activity.getXmlName();   
				
				if (isJavaFileAlreadyAvailable(activityName)) {
					System.out.println("⏭️ Skipped (already exists in files/java): " + activityName);
					skippedCount++;
					continue;
				}
				
				System.out.println("Extracting code for: " + activityName);
				
				String javaCode = generateCompleteJavaCode(activityName, layoutName);
				saveActivityCode(activityName, javaCode);
				allJavaCodes.put(activityName, javaCode);
			}
			
			saveAllCodes(allJavaCodes);
			System.out.println("✅ Successfully extracted " + allJavaCodes.size() + " activities! (skipped: " + skippedCount + ")");
			
		} catch (Exception e) {
			System.err.println("❌ Error extracting codes: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	private boolean isJavaFileAlreadyAvailable(String activityName) {
		if (activityName == null || activityName.trim().isEmpty()) return false;
		
		String name = activityName.trim();
		if (name.endsWith(".java")) {
			name = name.substring(0, name.length() - 5);
		}
		
		File javaDir = new File(projectPath + "/files/java");
		if (!javaDir.exists() || !javaDir.isDirectory()) return false;
		
		return findJavaFile(javaDir, name + ".java");
	}
	
	private boolean findJavaFile(File dir, String fileName) {
		File[] children = dir.listFiles();
		if (children == null) return false;
		
		for (File child : children) {
			if (child.isDirectory()) {
				if (findJavaFile(child, fileName)) return true;
			} else if (child.getName().equals(fileName) && child.length() > 0) {
				return true;
			}
		}
		return false;
	}
	
	
	public String generateCompleteJavaCode(String activityName, String layoutName) {
		StringBuilder javaCode = new StringBuilder();
		
		com.nexusteam.blacklogics.generator.source.model.ActivityConfig config =
				activityTypeRegistry.getActivityConfig(activityName);
		ActivityType activityType = ActivityType.ACTIVITY;
		if (config != null) {
			activityType = config.getType();
		}
		
		// ----- package -----
		javaCode.append("package ").append(extractPackageName()).append(";\n\n");
		
		// ----- imports -----
		JavaCodeImporter.addRequiredImports(javaCode, activityName, projectPath);
		JavaCodeImporter.addAndroidXImports(javaCode, c);
		addImportsSection(javaCode, activityName);
		JavaCodeBuilder.addCustomComponentImports(javaCode, activityName);
		
		if (activityType != null && activityType.isFragment()) {
			FragmentCodeBuilder.addFragmentImports(javaCode, activityType);
		}
		
		sortAndDeduplicateImports(javaCode);
		
		// ----- class declaration -----
		if (activityType != null && activityType.isFragment()) {
			javaCode.append("public class ").append(activityName)
					.append(" extends ").append(activityType.getGeneratedExtends())
					.append(" {\n\n");
		} else if (c != null && c.getAndroidXEnable()) {
			javaCode.append("public class ").append(activityName)
					.append(" extends AppCompatActivity {\n\n");
		} else {
			javaCode.append("public class ").append(activityName)
					.append(" extends Activity {\n\n");
		}
		
		// Attribute runtime setup helper reference
		com.nexusteam.blacklogics.generator.source.model.GenerationAttribute genAttrs =
				com.nexusteam.blacklogics.generator.source.model.GenerationAttribute.from(config);
		
		if (activityType != null && activityType.isFragment()) {
			// ========== FRAGMENT PATH ==========
			JavaCodeBuilder.addFieldsSection(javaCode, activityName, projectPath, layoutPath);
			
			FragmentCodeBuilder.addOnCreateViewAndOnViewCreated(
					javaCode, activityName, layoutName, activityType);
			
			// Runtime attribute setup is injected at the start of initialize via attrs;
			// also emit early in onViewCreated path by prepending to initializeLogic if needed.
			
			JavaCodeBuilder.addInitializeMethod(javaCode, activityName, projectPath, layoutPath);
			JavaCodeBuilder.addInitializeLogicMethod(javaCode, activityName, projectPath);
			
			// Inject attribute runtime setup into initializeLogic is done by
			// GenerationAttribute.emitRuntimeSetup when called from a small hook;
			// emit here as a dedicated private method the fragment can call.
			emitAttributeSetupMethod(javaCode, genAttrs, true);
			
			FragmentCodeBuilder.addFragmentLifecycleOverrides(
					javaCode, activityName, projectPath, layoutPath, activityType);
			
			if (activityType.isDialogFragment()) {
				FragmentCodeBuilder.addFragmentDialogOverrides(
						javaCode, activityName, activityType);
			}
			
			JavaCodeBuilder.addCustomFunctions(javaCode, activityName, projectPath, sc_id);
			JavaCodeBuilder.addMoreBlocksCode(javaCode, activityName, sc_id);
			JavaCodeBuilder.addUtilityMethods(javaCode, projectPath);
			
		} else {
			// ========== ACTIVITY PATH ==========
			JavaCodeBuilder.addFieldsSection(javaCode, activityName, projectPath, layoutPath);
			JavaCodeBuilder.addOnCreateMethod(javaCode, activityName, layoutName);
			JavaCodeBuilder.addInitializeMethod(javaCode, activityName, projectPath, layoutPath);
			JavaCodeBuilder.addInitializeLogicMethod(javaCode, activityName, projectPath);
			
			emitAttributeSetupMethod(javaCode, genAttrs, false);
			
			JavaCodeBuilder.getBlockLogicForLifecycleEvent(javaCode, activityName, projectPath);
			JavaCodeBuilder.addOnActivityResultMethod(javaCode, activityName);
			JavaCodeBuilder.addCustomFunctions(javaCode, activityName, projectPath, sc_id);
			JavaCodeBuilder.addMoreBlocksCode(javaCode, activityName, sc_id);
			JavaCodeBuilder.addUtilityMethods(javaCode, projectPath);
		}
		
		javaCode.append("}\n");
		
		String formatted = JavaCodeBuilder.formatJavaCode(javaCode.toString());
		return BlCommandProcessor.apply(formatted);
	}
	
	/** Emits private void _applyGenerationAttributes() from GenerationAttribute. */
	private void emitAttributeSetupMethod(StringBuilder javaCode,
			com.nexusteam.blacklogics.generator.source.model.GenerationAttribute attrs,
			boolean isFragment) {
		if (attrs == null) return;
		javaCode.append("    private void _applyGenerationAttributes() {\n");
		String ctx = isFragment ? "requireContext()" : "this";
		attrs.emitRuntimeSetup(javaCode, "        ", ctx, isFragment);
		javaCode.append("    }\n\n");
	}
	
	
	public void extractAndSaveAllXmlCodes() {
		try {
			List<ActivityStructureRegistry.ViewItem> activities = activityManager.getActivities();
			
			Map<String, String> allLayouts = LayoutLoader.getAllLayoutXmls(layoutPath);
			if (allLayouts.isEmpty()) {
				System.err.println("⚠️ No saved layouts found in layoutstore, using default XML for all activities: " + layoutPath);
			}
			
			new File(xmlOutPut).mkdirs();
			int savedCount = 0;
			
			Set<String> handledEntryNames = new HashSet<>();
			
			for (int i = 0; i < activities.size(); i++) {
				ActivityStructureRegistry.ViewItem activity = activities.get(i);
				String activityName = activity.getJavaName();
				String layoutName   = activity.getXmlName();
				
				String xml = allLayouts.get(activityName);
				if (xml == null || xml.trim().isEmpty()) {
					xml = allLayouts.get(layoutName);
				}
				if ((xml == null || xml.trim().isEmpty()) && layoutName != null) {
					String layKey = layoutName;
					if (layKey.endsWith(".xml")) layKey = layKey.substring(0, layKey.length() - 4);
					xml = allLayouts.get(layKey);
					if (xml == null) xml = allLayouts.get(layKey.toLowerCase());
				}
				
				if (xml != null && !xml.trim().isEmpty()) {
					String compileReadyXml = stripInternalOnlyAttributes(xml);
					compileReadyXml = injectChromeIntoLayoutXml(activityName, compileReadyXml);
					saveXmlCode(layoutName, compileReadyXml);
					savedCount++;
				} else {
					String fallback = injectChromeIntoLayoutXml(activityName, DEFAULT_LAYOUT_XML);
					saveXmlCode(layoutName, fallback);
					savedCount++;
				}
				markHandled(handledEntryNames, activityName);
				markHandled(handledEntryNames, layoutName);
			}
			
			for (Map.Entry<String, String> entry : allLayouts.entrySet()) {
				String entryName = entry.getKey();
				if (entryName == null || entryName.trim().isEmpty()) continue;
				if (isHandledName(handledEntryNames, entryName)) continue;
				
				String xml = entry.getValue();
				if (xml != null && !xml.trim().isEmpty()) {
					String targetLayoutName = entryName.toLowerCase();
					if (targetLayoutName.endsWith(".xml")) {
						targetLayoutName = targetLayoutName.substring(0, targetLayoutName.length() - 4);
					}
					String compileReadyXml = stripInternalOnlyAttributes(xml);
					if (isCustomOrDrawerEntryName(entryName)) {
						compileReadyXml = stripActivityChromeIfPresent(compileReadyXml);
					}
					compileReadyXml = ensureRootXmlns(compileReadyXml);
					saveXmlCode(targetLayoutName, compileReadyXml);
					savedCount++;
				}
			}
			
			System.out.println("✅ Extracted " + savedCount + " XML layouts");
			
		} catch (Exception e) {
			System.err.println("❌ XML extraction failed: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void markHandled(Set<String> handled, String name) {
		if (name == null || name.trim().isEmpty()) return;
		String n = name.trim();
		handled.add(n);
		handled.add(n.toLowerCase());
		if (n.endsWith(".xml")) {
			String base = n.substring(0, n.length() - 4);
			handled.add(base);
			handled.add(base.toLowerCase());
		} else {
			handled.add(n + ".xml");
			handled.add(n.toLowerCase() + ".xml");
		}
		if (n.endsWith("Activity") && n.length() > 8) {
			String base = n.substring(0, n.length() - 8);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < base.length(); i++) {
				char c = base.charAt(i);
				if (Character.isUpperCase(c) && i > 0) sb.append('_');
				sb.append(Character.toLowerCase(c));
			}
			String xmlBase = sb.toString();
			if (xmlBase.length() > 0) {
				handled.add(xmlBase);
				handled.add(xmlBase + ".xml");
			}
		}
	}
	
	private boolean isHandledName(Set<String> handled, String name) {
		if (name == null) return false;
		String n = name.trim();
		if (handled.contains(n) || handled.contains(n.toLowerCase())) return true;
		if (n.endsWith(".xml")) {
			String base = n.substring(0, n.length() - 4);
			return handled.contains(base) || handled.contains(base.toLowerCase());
		}
		return handled.contains(n + ".xml") || handled.contains(n.toLowerCase() + ".xml");
	}
	
	private boolean isCustomOrDrawerEntryName(String entryName) {
		if (entryName == null) return false;
		String n = entryName.trim();
		if (n.endsWith(".xml")) n = n.substring(0, n.length() - 4);
		String lower = n.toLowerCase();
		if (lower.startsWith("_drawer_") || lower.contains("_drawer_")) return true;
		if (n.endsWith("Activity") || n.endsWith("activity")) return false;
		return true;
	}
	
	private String stripInternalOnlyAttributes(String xml) {
		if (xml == null || xml.isEmpty()) return xml;
		String result = xml;
		result = result.replaceAll("\\s*app:customView\\s*=\\s*\"[^\"]*\"", "");
		result = result.replaceAll("\\s*app:convertedWidget\\s*=\\s*\"[^\"]*\"", "");
		return result;
	}
	
	private String stripActivityChromeIfPresent(String xml) {
		if (xml == null || xml.isEmpty()) return xml;
		if (!xml.contains("android:id=\"@+id/_coordinator\"")
		&& !xml.contains("android:id=\"@+id/_app_bar\"")
		&& !xml.contains("android:id=\"@+id/_toolbar\"")) {
			return ensureRootXmlns(xml);
		}
		try {
			String marker = "app:layout_behavior=\"@string/appbar_scrolling_view_behavior\"";
			int behaviorIdx = xml.indexOf(marker);
			if (behaviorIdx >= 0) {
				int tagStart = xml.lastIndexOf('<', behaviorIdx);
				if (tagStart >= 0) {
					int openEnd = xml.indexOf('>', behaviorIdx);
					if (openEnd > 0) {
						String afterOpen = xml.substring(openEnd + 1);
						int lastLinClose = afterOpen.lastIndexOf("</LinearLayout>");
						int coordClose = afterOpen.lastIndexOf("</androidx.coordinatorlayout.widget.CoordinatorLayout>");
						if (lastLinClose > 0 && (coordClose < 0 || lastLinClose < coordClose)) {
							String inner = xml.substring(tagStart, openEnd + 1 + lastLinClose + "</LinearLayout>".length());
							inner = inner.replaceAll("\\s*app:layout_behavior\\s*=\\s*\"[^\"]*\"", "");
							return ensureRootXmlns(inner);
						}
					}
				}
			}
		} catch (Throwable t) {
			System.err.println("stripActivityChromeIfPresent failed: " + t.getMessage());
		}
		return ensureRootXmlns(xml);
	}
	
	private String ensureRootXmlns(String xml) {
		if (xml == null || xml.isEmpty()) return xml;
		String result = xml.trim();
		boolean hadDecl = result.startsWith("<?xml");
		String decl = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
		String body = result;
		if (hadDecl) {
			int gt = body.indexOf("?>");
			if (gt >= 0) body = body.substring(gt + 2).trim();
		}
		int tagOpen = body.indexOf('<');
		if (tagOpen < 0) return xml;
		int tagClose = body.indexOf('>', tagOpen);
		if (tagClose < 0) return xml;
		String openTag = body.substring(tagOpen, tagClose);
		boolean selfClosing = openTag.endsWith("/");
		if (selfClosing) openTag = openTag.substring(0, openTag.length() - 1).trim();
		
		if (!openTag.contains("xmlns:android")) {
			openTag = openTag + "\n    xmlns:android=\"http://schemas.android.com/apk/res/android\"";
		}
		if (!openTag.contains("xmlns:app")) {
			openTag = openTag + "\n    xmlns:app=\"http://schemas.android.com/apk/res-auto\"";
		}
		if (!openTag.contains("xmlns:tools")) {
			openTag = openTag + "\n    xmlns:tools=\"http://schemas.android.com/tools\"";
		}
		String newOpen = openTag + (selfClosing ? " />" : ">");
		String rest = body.substring(tagClose + 1);
		return decl + newOpen + rest;
	}
	
	private String injectChromeIntoLayoutXml(String activityName, String xml) {
		if (xml == null || xml.isEmpty() || activityName == null) return xml;
		boolean fabOn = false;
		boolean drawerOn = false;
		boolean toolbarOn = false;
		boolean toolbarResolved = false;
		boolean useAndroidX = true;
		try {
			com.nexusteam.blacklogics.development.Complex cx =
			new com.nexusteam.blacklogics.development.Complex();
			cx.setId(sc_id);
			String name = activityName;
			if (name.endsWith(".java")) name = name.substring(0, name.length() - 5);
			fabOn = cx.getEnableFabBoolean(name);
			drawerOn = cx.isDrawerEnabled(name);
			try { useAndroidX = cx.getAndroidXEnable(); } catch (Throwable ignore) {}
			try {
				com.nexusteam.internal.ly ly = com.nexusteam.internal.ma.b(sc_id);
				if (ly != null) {
					java.util.ArrayList list = ly.a();
					if (list != null) {
						for (Object o : list) {
							if (o instanceof com.nexusteam.internal.beans.ProjectFileBean) {
								com.nexusteam.internal.beans.ProjectFileBean p =
								(com.nexusteam.internal.beans.ProjectFileBean) o;
								String j = p.getJavaName();
								if (j != null && (j.equals(activityName) || j.equals(name + ".java")
								|| (j.endsWith(".java") && j.substring(0, j.length() - 5).equals(name)))) {
									toolbarOn = p.hasActivityOption(
									com.nexusteam.internal.beans.ProjectFileBean.OPTION_ACTIVITY_TOOLBAR);
									toolbarResolved = true;
									break;
								}
							}
						}
					}
				}
			} catch (Throwable ignore) {}
			if (!toolbarResolved && useAndroidX) {
				toolbarOn = true;
			}
		} catch (Throwable t) {
			Log.w("CodeGenerator", "chrome flags", t);
		}
		
		String result = xml;
		
		if (toolbarOn && useAndroidX
		&& !result.contains("android:id=\"@+id/_coordinator\"")
		&& !result.contains("android:id=\"@+id/_toolbar\"")) {
			String body = result;
			if (body.startsWith("<?xml")) {
				int gt = body.indexOf("?>");
				if (gt >= 0) body = body.substring(gt + 2).trim();
			}
			result = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
			+ "<androidx.coordinatorlayout.widget.CoordinatorLayout\n"
			+ "    xmlns:android=\"http://schemas.android.com/apk/res/android\"\n"
			+ "    xmlns:app=\"http://schemas.android.com/apk/res-auto\"\n"
			+ "    xmlns:tools=\"http://schemas.android.com/tools\"\n"
			+ "    android:id=\"@+id/_coordinator\"\n"
			+ "    android:layout_width=\"match_parent\"\n"
			+ "    android:layout_height=\"match_parent\">\n"
			+ "    <com.google.android.material.appbar.AppBarLayout\n"
			+ "        android:id=\"@+id/_app_bar\"\n"
			+ "        android:layout_width=\"match_parent\"\n"
			+ "        android:layout_height=\"wrap_content\"\n"
			+ "        android:theme=\"@style/AppTheme.AppBarOverlay\">\n"
			+ "        <androidx.appcompat.widget.Toolbar\n"
			+ "            android:id=\"@+id/_toolbar\"\n"
			+ "            android:layout_width=\"match_parent\"\n"
			+ "            android:layout_height=\"?attr/actionBarSize\"\n"
			+ "            android:background=\"?attr/colorPrimary\"\n"
			+ "            app:popupTheme=\"@style/AppTheme.PopupOverlay\" />\n"
			+ "    </com.google.android.material.appbar.AppBarLayout>\n"
			+ body + "\n"
			+ "</androidx.coordinatorlayout.widget.CoordinatorLayout>\n";
		}
		
		if (!fabOn && !drawerOn && !(toolbarOn && useAndroidX)) return result;
		
		if (fabOn && !result.contains("android:id=\"@+id/_fab\"") && !result.contains("android:id=\"@id/_fab\"")) {
			String fabXml =
			"\n    <com.google.android.material.floatingactionbutton.FloatingActionButton\n" +
			"        android:id=\"@+id/_fab\"\n" +
			"        android:layout_width=\"wrap_content\"\n" +
			"        android:layout_height=\"wrap_content\"\n" +
			"        android:layout_gravity=\"bottom|end\"\n" +
			"        android:layout_margin=\"16dp\"\n" +
			"        android:src=\"@android:drawable/ic_input_add\"\n" +
			"        app:backgroundTint=\"#212121\"\n" +
			"        app:tint=\"#FFFFFF\"\n" +
			"        app:elevation=\"6dp\" />\n";
			int lastClose = result.lastIndexOf("</");
			if (lastClose > 0) {
				result = result.substring(0, lastClose) + fabXml + result.substring(lastClose);
			}
		}
		
		if (drawerOn && !result.contains("android:id=\"@+id/_drawer\"") && !result.contains("DrawerLayout")) {
			String drawerRes = resolveDrawerLayoutResName(activityName);
			String drawerSide =
			"    <LinearLayout\n" +
			"        android:id=\"@+id/_nav_view\"\n" +
			"        android:layout_width=\"240dp\"\n" +
			"        android:layout_height=\"match_parent\"\n" +
			"        android:layout_gravity=\"start\"\n" +
			"        android:orientation=\"vertical\"\n" +
			"        android:background=\"#FFFFFF\">\n" +
			"        <include\n" +
			"            layout=\"@layout/" + drawerRes + "\"\n" +
			"            android:id=\"@+id/" + drawerRes + "\"\n" +
			"            android:layout_width=\"match_parent\"\n" +
			"            android:layout_height=\"match_parent\" />\n" +
			"    </LinearLayout>\n";
			String body = result;
			if (body.startsWith("<?xml")) {
				int gt = body.indexOf("?>");
				if (gt >= 0) body = body.substring(gt + 2).trim();
			}
			result = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
			"<androidx.drawerlayout.widget.DrawerLayout\n" +
			"    xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
			"    xmlns:app=\"http://schemas.android.com/apk/res-auto\"\n" +
			"    xmlns:tools=\"http://schemas.android.com/tools\"\n" +
			"    android:id=\"@+id/_drawer\"\n" +
			"    android:layout_width=\"match_parent\"\n" +
			"    android:layout_height=\"match_parent\">\n\n" +
			body + "\n\n" +
			drawerSide +
			"</androidx.drawerlayout.widget.DrawerLayout>\n";
		}
		
		return applyAppCompatInjection(activityName, result);
	}
	
	private String resolveDrawerLayoutResName(String activityName) {
		if (activityName == null || activityName.trim().isEmpty()) return "_drawer_main";
		String n = activityName.trim();
		if (n.endsWith(".java")) n = n.substring(0, n.length() - 5);
		if (n.endsWith(".xml")) n = n.substring(0, n.length() - 4);
		if (n.startsWith("_drawer_")) return n.toLowerCase();
		if (n.endsWith("Activity") && n.length() > 8) {
			String base = n.substring(0, n.length() - 8);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < base.length(); i++) {
				char c = base.charAt(i);
				if (Character.isUpperCase(c) && i > 0) sb.append('_');
				sb.append(Character.toLowerCase(c));
			}
			n = sb.length() > 0 ? sb.toString() : "main";
		} else {
			n = n.toLowerCase();
		}
		if (n.isEmpty()) n = "main";
		return "_drawer_" + n;
	}
	
	public String getLayoutXml(String activityName) {
		if (activityName == null || activityName.trim().isEmpty()) return null;
		
		try {
			String xml = LayoutLoader.getLayoutXml(activityName, layoutPath);
			return (xml != null && !xml.trim().isEmpty()) ? xml : DEFAULT_LAYOUT_XML;
		} catch (Exception e) {
			Log.e("CodeGenerator", "Failed to load layout XML", e);
		}
		
		return DEFAULT_LAYOUT_XML;
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
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try { writer.close(); } catch (IOException e) { e.printStackTrace(); }
			}
		}
	}
	
	private static void sortAndDeduplicateImports(StringBuilder javaCode) {
		String code = javaCode.toString();
		
		String[] lines = code.split("\n", -1);
		
		int i = 0;
		StringBuilder header = new StringBuilder();
		Set<String> importSet = new LinkedHashSet<String>();
		
		while (i < lines.length) {
			String line = lines[i];
			if (line.trim().startsWith("package ")) {
				header.append(line).append("\n");
				i++;
				break;
			} else if (line.trim().isEmpty()) {
				header.append(line).append("\n");
				i++;
			} else {
				break;
			}
		}
		
		int importsEnd = i;
		while (importsEnd < lines.length) {
			String trimmed = lines[importsEnd].trim();
			if (trimmed.startsWith("import ")) {
				importSet.add(trimmed);
				importsEnd++;
			} else if (trimmed.isEmpty()) {
				importsEnd++;
			} else {
				break;
			}
		}
		
		List<String> sortedImports = new ArrayList<String>(importSet);
		Collections.sort(sortedImports);
		
		StringBuilder rebuilt = new StringBuilder();
		rebuilt.append(header);
		rebuilt.append("\n");
		for (String imp : sortedImports) {
			rebuilt.append(imp).append("\n");
		}
		rebuilt.append("\n");
		
		for (int j = importsEnd; j < lines.length; j++) {
			rebuilt.append(lines[j]);
			if (j < lines.length - 1) rebuilt.append("\n");
		}
		
		javaCode.setLength(0);
		javaCode.append(rebuilt);
	}
	
	public static void addImportsSection(StringBuilder javaCode, String activityName) {
		String imports = ProjectLogicRepository.getImports(activityName);
		
		if (imports != null && !imports.isEmpty()) {
			Set<String> importSet = new LinkedHashSet<String>();
			
			String[] importLines = imports.split(";");
			
			for (String importLine : importLines) {
				String trimmed = importLine.trim();
				
				if (!trimmed.isEmpty()) {
					if (!trimmed.startsWith("import ")) {
						trimmed = "import " + trimmed;
					}
					if (!trimmed.endsWith(";")) {
						trimmed = trimmed + ";";
					}
					
					importSet.add(trimmed);
				}
			}
			
			List<String> sortedImports = new ArrayList<String>(importSet);
			Collections.sort(sortedImports);
			
			StringBuilder importBlock = new StringBuilder();
			
			for (String imp : sortedImports) {
				importBlock.append(imp).append("\n");
			}
			
			importBlock.append("\n");
			
			javaCode.append(importBlock.toString());
		}
	}
	
	public void generateRecyclerViewAdapter(StringBuilder j, String activityName, String recyclerViewId, String itemLayoutName) {
		String layoutPath = this.layoutPath;
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
	
	// ===== ADD near other private helpers =====
	private String applyAppCompatInjection(String activityName, String xml) {
		if (xml == null || activityName == null) return xml;
		
		// Try the xml/layout name first, then the activity name — both are used as file keys
		AppCompatInjectionReader inj = AppCompatInjectionReader.load(sc_id, activityName);
		if (inj == null) {
			String n = activityName;
			if (n.endsWith(".java")) n = n.substring(0, n.length() - 5);
			inj = AppCompatInjectionReader.load(sc_id, n);
		}
		if (inj == null || inj.isEmpty()) return xml;
		
		// Overlay attrs on the chrome nodes we know the ids of
		String result = xml;
		
		// toolbar
		result = overlay(result, inj.attrs("toolbar"), "@+id/_toolbar");
		result = overlay(result, inj.attrs("appbarlayout"), "@+id/_app_bar");
		result = overlay(result, inj.attrs("coordinatorlayout"), "@+id/_coordinator");
		result = overlay(result, inj.attrs("fab"), "@+id/_fab");
		result = overlay(result, inj.attrs("drawerlayout"), "@+id/_drawer");
		result = overlay(result, inj.attrs("navigationdrawer"), "@+id/_nav_view");
		result = overlay(result, inj.attrs("collapsingtoolbarlayout"), "@+id/_collapsing");
		result = overlay(result, inj.attrs("tablayout"), "@+id/_tabs");
		result = overlay(result, inj.attrs("bottomnavigationview"), "@+id/_bottom_nav");
		result = overlay(result, inj.attrs("nestedscrollview"), "@+id/_nested_scroll");
		
		// Types without a fixed id: rewrite attrs directly on matching tag names
		result = overlayByTag(result, inj.attrs("toolbar"), "androidx.appcompat.widget.Toolbar");
		result = overlayByTag(result, inj.attrs("appbarlayout"), "com.google.android.material.appbar.AppBarLayout");
		result = overlayByTag(result, inj.attrs("coordinatorlayout"),
		"androidx.coordinatorlayout.widget.CoordinatorLayout");
		result = overlayByTag(result, inj.attrs("fab"),
		"com.google.android.material.floatingactionbutton.FloatingActionButton");
		result = overlayByTag(result, inj.attrs("drawerlayout"), "androidx.drawerlayout.widget.DrawerLayout");
		result = overlayByTag(result, inj.attrs("collapsingtoolbarlayout"),
		"com.google.android.material.appbar.CollapsingToolbarLayout");
		result = overlayByTag(result, inj.attrs("tablayout"), "com.google.android.material.tabs.TabLayout");
		result = overlayByTag(result, inj.attrs("bottomnavigationview"),
		"com.google.android.material.bottomnavigation.BottomNavigationView");
		result = overlayByTag(result, inj.attrs("nestedscrollview"),
		"androidx.core.widget.NestedScrollView");
		
		return result;
	}
	
	/**
* Find `android:id="<id>"` on a start tag and merge attrs (JSON wins over existing).
*/	
	private String overlay(String xml, Map<String, String> attrs, String id) {
		if (attrs == null || attrs.isEmpty() || !xml.contains(id)) return xml;
		int idIdx = xml.indexOf(id);
		int tagStart = xml.lastIndexOf('<', idIdx);
		int tagEnd = xml.indexOf('>', idIdx);
		if (tagStart < 0 || tagEnd < 0) return xml;
		String openTag = xml.substring(tagStart, tagEnd + 1);
		String merged = mergeAttrs(openTag, attrs);
		return xml.substring(0, tagStart) + merged + xml.substring(tagEnd + 1);
	}
	
	/**
* Find every start tag with the given name and merge attrs into all of them.
*/	
	private String overlayByTag(String xml, Map<String, String> attrs, String tagName) {
		if (attrs == null || attrs.isEmpty() || tagName == null) return xml;
		String result = xml;
		String open = "<" + tagName;
		int from = 0;
		while (true) {
			int start = result.indexOf(open, from);
			if (start < 0) break;
			int end = result.indexOf('>', start);
			if (end < 0) break;
			// make sure it's a start tag, not </... or a prefix match
			char prev = start > 0 ? result.charAt(start - 1) : ' ';
			char next = start + open.length() < result.length() ? result.charAt(start + open.length()) : ' ';
			if (prev == '<' || prev == '/') { from = start + 1; continue; }
			if (next != ' ' && next != '/' && next != '>' && next != '\n') { from = start + 1; continue; }
			String openTag = result.substring(start, end + 1);
			String merged = mergeAttrs(openTag, attrs);
			result = result.substring(0, start) + merged + result.substring(end + 1);
			from = start + merged.length();
		}
		return result;
	}
	
	/** Merge `attrs` into an open tag; existing attributes with the same name are replaced. */
	private String mergeAttrs(String openTag, Map<String, String> attrs) {
		boolean selfClose = openTag.endsWith("/>");
		String tag = selfClose ? openTag.substring(0, openTag.length() - 2) : openTag.substring(0, openTag.length() - 1);
		for (Map.Entry<String, String> e : attrs.entrySet()) {
			String name = e.getKey();
			String value = e.getValue();
			if (name.startsWith("xmlns:") || value == null || value.isEmpty()) continue;
			String escaped = value.replace("&", "&amp;").replace("\"", "&quot;");
			// try to replace existing attr
			java.util.regex.Matcher m = java.util.regex.Pattern
			.compile("\\s+" + java.util.regex.Pattern.quote(name) + "\\s*=\\s*\"[^\"]*\"")
			.matcher(tag);
			if (m.find()) {
				tag = m.replaceFirst(" " + name + "=\"" + escaped + "\"");
			} else {
				tag = tag + "\n    " + name + "=\"" + escaped + "\"";
			}
		}
		return tag + (selfClose ? " />" : ">");
	}
}
