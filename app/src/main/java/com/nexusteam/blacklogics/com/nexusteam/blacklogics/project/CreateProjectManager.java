package com.nexusteam.blacklogics.project;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;

import com.nexusteam.blacklogics.development.Complex;
import com.nexusteam.blacklogics.activityfile.ActivitySyncBridge;
import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.blacklogics.security.crypto.FileEncryptionUtil;
import com.nexusteam.internal.os.layouteditor.util.TheBlockLogicsUtil;

import org.json.JSONObject;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import a.a.a.*;
import com.nexusteam.blacklogics.generator.source.ResourceManager;
import com.nexusteam.blacklogics.generator.source.AndroidManifestGenerator;
import com.nexusteam.blacklogics.manager.ProjectSettingsManager;
import com.nexusteam.blacklogics.generator.source.model.ActivityStructureRegistry;
import com.nexusteam.blacklogics.manager.ProjectConfigManager;
import com.nexusteam.blacklogics.manager.GradleConfigManager;

public class CreateProjectManager {
	
	private final Context context;
	private final char[] cryptoPassword;
	private int selectedColorAccent = Color.parseColor("#FF4081");
	private int selectedColorPrimary = Color.parseColor("#3F51B5");
	private int selectedColorPrimaryDark = Color.parseColor("#303F9F");
	private int selectedColorPrimaryHighlight = Color.parseColor("#FF9800");
	private int selectedColorPrimaryNormal = Color.parseColor("#2196F3");
	private ResourceManager resourceManager;
	
	public CreateProjectManager(Context context, char[] cryptoPassword) {
		this.context = context;
		this.cryptoPassword = cryptoPassword;
		initializeResourceManager();
	}
	
	public void setVariables(int selectedColorAccent, int selectedColorPrimary, int selectedColorPrimaryDark, int selectedColorPrimaryHighlight, int selectedColorPrimaryNormal) {
		this.selectedColorAccent = selectedColorAccent;
		this.selectedColorPrimary = selectedColorPrimary;
		this.selectedColorPrimaryDark = selectedColorPrimaryDark;
		this.selectedColorPrimaryHighlight = selectedColorPrimaryHighlight;
		this.selectedColorPrimaryNormal = selectedColorPrimaryNormal;
		
		updateResourceColors();
	}
	
	
	public File createOrUpdateProject(
	boolean isUpdate,
	File originalProjectDir,
	String scId,
	String projectName,
	String packageName,
	String appName,
	int versionCode,
	String versionName,
	Map<String, String> mainActivityAttrs,
	JSONObject extraConfig
	) throws Exception {
		
		File projectDir = isUpdate
		? originalProjectDir
		: new File(TheBlockLogicsUtil.mysc + scId);
		
		if (!isUpdate && !projectDir.exists()) {
			projectDir.mkdirs();
		}
		
		boolean androidXEnabled = false;
		if (extraConfig != null && extraConfig.has("androidX")) {
			androidXEnabled = extraConfig.optBoolean("androidX", false);
		} else if (isUpdate) {
			Complex existingComplex = new Complex();
			existingComplex.setC(context);
			existingComplex.setId(scId);
			androidXEnabled = existingComplex.getAndroidXEnable();
		}
		
		ProjectConfigManager projectManager = new ProjectConfigManager();
		
		if (isUpdate) {
			projectManager.load(context, scId);
		}
		
		int minSdk = 26;
		int targetSdk = 34;
		if (extraConfig != null) {
			minSdk = extraConfig.optInt("minSdk", 26);
			targetSdk = extraConfig.optInt("targetSdk", 34);
		}
		if (minSdk < 14) minSdk = 14;
		if (targetSdk < minSdk) targetSdk = minSdk;

		projectManager
		.setProjectId(scId)
		.setPackageName(packageName)
		.setProjectName(projectName)
		.setScName(projectName)
		.setMinSdk(minSdk)
		.setTargetSdk(targetSdk)
		.setVersionCode(versionCode)
		.setVersionName(versionName);
		
		projectManager.save(context);
		
		AndroidManifestGenerator manifest = new AndroidManifestGenerator();
		if (isUpdate) {
			manifest.load(context, scId);
		}
		
		if (resourceManager != null) {
            resourceManager.setAppName(appName);
			resourceManager.setAndroidX(androidXEnabled);
			resourceManager.save(context, scId);
		}
		
		ProjectSettingsManager updateSettings = new ProjectSettingsManager(scId);
		if (isUpdate) {
			updateSettings.load(context);
		}
		String applicationClassName = updateSettings.getApplicationClassName();
		
		manifest.setPackageName(packageName)
		.setAppName(appName)
		.setThemeColor("AppTheme")
		.setAppIcon("@mipmap/icon")
		.setAndroidX(androidXEnabled)
		.setApplicationClassName(applicationClassName)
		.addActivity(".MainActivity", mainActivityAttrs)
		.addLauncherActivity(".MainActivity")
		.save(context, scId);
		
		manifest.extractManifest(scId, projectDir.getAbsolutePath() + "/app/src/main");
		
		
		if (!isUpdate) {
			new ActivityStructureRegistry()
			.addActivity("MainActivity", "main")
			.save(context, scId);
		}
		
		updateSettings
		.setMinSdk(minSdk)
		.setTargetSdk(targetSdk)
		.setResourceEncrypt(false)
		.save(context);
		
		
		new GradleConfigManager()
		.setApplicationId(packageName)
		.setMinSdk(minSdk)
		.setTargetSdk(targetSdk)
		.setCompileSdk(targetSdk)
		.setVersionCode(versionCode)
		.setVersionName(versionName)
		.save(context, scId);
		
		Complex complex = new Complex();
		complex.setC(context);
		complex.setId(scId);
		complex.setAcName("MainActivity");
		complex.setXName("main");
		complex.setPkgName(packageName);
		complex.setProjectName(projectName);
		complex.enableAndroidX(androidXEnabled);

		boolean toolbar = true;
		boolean drawer = false;
		boolean fab = false;
		boolean statusBar = true;
		if (extraConfig != null) {
			toolbar = extraConfig.optBoolean("toolbar", true);
			drawer = extraConfig.optBoolean("drawer", false);
			fab = extraConfig.optBoolean("fab", false);
			statusBar = extraConfig.optBoolean("statusBar", true);
		}

		int options = 0;
		if (toolbar) options |= ProjectFileBean.OPTION_ACTIVITY_TOOLBAR;
		if (!statusBar) options |= ProjectFileBean.OPTION_ACTIVITY_FULLSCREEN;
		if (drawer) options |= ProjectFileBean.OPTION_ACTIVITY_DRAWER;
		if (fab) options |= ProjectFileBean.OPTION_ACTIVITY_FAB;

		try {
			ProjectFileBean mainBean = new ProjectFileBean(0, "main");
			mainBean.options = options;
			mainBean.orientation = 2;
			ActivitySyncBridge.syncNewOrUpdatedActivity(context, scId, complex, mainBean);
		} catch (Throwable t) {
			android.util.Log.e("CreateProjectManager", "ActivitySyncBridge failed", t);
			try {
				complex.enableToolBar("MainActivity", androidXEnabled, toolbar);
				complex.addActivity("MainActivity", "main", fab, true, drawer);
			} catch (Throwable ignore) {}
			try {
				com.nexusteam.blacklogics.sdk.util.ActivityConfig ac =
						new com.nexusteam.blacklogics.sdk.util.ActivityConfig();
				ac.init(context, scId);
				ac.setActivityConfig("MainActivity", statusBar, toolbar, drawer, fab,
						"Activity", "Both", "main");
				ac.save(context, scId);
			} catch (Throwable ignore) {}
		}

		try {
			int templateType = extraConfig != null ? extraConfig.optInt("templateType", 1) : 1;
			com.nexusteam.blacklogics.generator.source.TemplateLayoutWriter.seedLayoutStore(
					context, scId, templateType);
		} catch (Throwable t) {
			android.util.Log.w("CreateProjectManager", "layoutstore seed skip", t);
		}
		
		saveEncryptedConfig(projectDir, extraConfig);
		
		return projectDir;
	}
	
	private void initializeResourceManager() {
		resourceManager = new ResourceManager().setAndroidX(false);
		
		
		updateResourceColors();
		
		
		resourceManager.setStyle("AppTheme", "@android:style/Theme.Material.Light");
		resourceManager.setStyle("FullScreen", "@android:style/Theme.Material.Light.NoActionBar.Fullscreen");
		resourceManager.setStyle("NoActionBar", "@android:style/Theme.Material.Light.NoActionBar");
		resourceManager.setStyle("NoStatusBar", "AppTheme");
		
		resourceManager.setDimen("app_margin", "16dp");
		resourceManager.setDimen("button_corner", "8dp");
		resourceManager.setDimen("text_size", "14sp");
	}
	
	private void updateResourceColors() {
		if (resourceManager != null) {
			resourceManager.setColor("colorPrimary", String.format("#%06X", (0xFFFFFF & selectedColorPrimary)));
			resourceManager.setColor("colorPrimaryDark", String.format("#%06X", (0xFFFFFF & selectedColorPrimaryDark)));
			resourceManager.setColor("colorAccent", String.format("#%06X", (0xFFFFFF & selectedColorAccent)));
			resourceManager.setColor("colorControlHighlight", String.format("#%06X", (0xFFFFFF & selectedColorPrimaryHighlight)));
			resourceManager.setColor("colorControlNormal", String.format("#%06X", (0xFFFFFF & selectedColorPrimaryNormal)));
			resourceManager.setColor("colorBackground", "#F5F5F5");
		}
	}
	
	private void saveEncryptedConfig(File projectDir, JSONObject json) throws Exception {
		if (json == null) return;
		
		File outFile = new File(projectDir, "config.enc");
		
		java.io.FileOutputStream fos = new java.io.FileOutputStream(outFile);
		fos.write(json.toString().getBytes(StandardCharsets.UTF_8));
		fos.flush();
		fos.close();
	}
	public JSONObject loadEncryptedConfig(File projectDir) throws Exception {
		File file = new File(projectDir, "config.enc");
		if (!file.exists()) return null;
		
		java.io.FileInputStream fis = new java.io.FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		fis.read(data);
		fis.close();
		
		return new JSONObject(new String(data, StandardCharsets.UTF_8));
	}
	
}