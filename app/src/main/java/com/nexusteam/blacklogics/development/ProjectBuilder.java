package com.nexusteam.blacklogics.development;

import com.nexusteam.blacklogics.generator.source.ResourceManager;
import com.nexusteam.blacklogics.manager.GradleConfigManager;
import com.nexusteam.blacklogics.manager.ProjectConfigManager;
import android.content.Context;
import com.nexusteam.blacklogics.FileUtil;
import com.nexusteam.blacklogics.generator.source.AndroidManifestGenerator;
import com.nexusteam.blacklogics.generator.source.JavaCodeBuilder;
import com.nexusteam.blacklogics.generator.source.SourceCodeGenerator;
import com.nexusteam.blacklogics.file.AssetCopyUtil;
import com.nexusteam.blacklogics.file.FileExtractor;
import com.nexusteam.blacklogics.generator.source.model.ActivityStructureRegistry;
import com.nexusteam.blacklogics.logic.ProjectLogicRepository;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import a.a.a.Lx;

public class ProjectBuilder {
	private ResourceManager resourceManager;
	private AndroidManifestGenerator manifestManager;
	private GradleConfigManager gradleManager;
	private ProjectConfigManager projectManager;
	private SourceCodeGenerator dataManager;
	private Complex complex;
	private String sc_id;
	private String basePath;
	private String projectPath;
	private String packageName;
	private Context context;
	
	public ProjectBuilder(Context context, Complex complex, String basePath, String sc_id) {
		this.context = context;
		this.complex = complex;
		this.basePath = basePath;
		this.sc_id = sc_id;
		
		this.resourceManager = new ResourceManager();
		this.resourceManager.load(context, sc_id);
		
		this.manifestManager = new AndroidManifestGenerator();
		this.manifestManager.load(context, sc_id);
		
		this.gradleManager = new GradleConfigManager();
		this.gradleManager.load(context, sc_id);
		
		
		this.projectManager = new ProjectConfigManager();
		this.projectManager.load(context, sc_id);
		
		this.packageName = projectManager.getPackageName();
		String packagePath = packageName.replace('.', '/');
		
		this.projectPath = basePath + "/.blacklogics/mysc/" + sc_id + "/";
		
		
		this.dataManager = new SourceCodeGenerator(context, sc_id);
		this.dataManager.setXmlOutPut(projectPath + "app/src/main/res/layout/");
		this.dataManager.setOutPut(projectPath + "app/src/main/java/" + packagePath + "/");
	}
	
	public void setContext(Context context) {
		this.context = context;
	}
	
	public void buildProject() {
		createDirectories();
		extractManifest();
		generateDebug();
		dataManager.extractAllJavaCodes();
		extractJavaFiles();
		dataManager.extractAndSaveAllXmlCodes();
		extractGradleFiles();
		extractProGuardRules();
		extractResources();
	}
	
	private void createDirectories() {
		String packagePath = packageName.replace('.', '/');
		
		String[] directories = {
			"app/",
			"app/src/",
			"app/src/main/",
			"app/src/main/java/" + packagePath + "/",
			"app/src/main/res/",
			"app/src/main/res/drawable/",
			"app/src/main/res/layout/",
			"app/src/main/res/values/",
			"app/src/main/res/values-v21/",
			"app/src/main/res/mipmap/",
			"app/src/main/assets/",
			"app/libs/",
			"gradle/"
		};
		
		for (String dir : directories) {
			File directory = new File(projectPath + dir);
			if (!directory.exists()) {
				directory.mkdirs();
			}
		}
	}
	
	private void extractManifest() {
		manifestManager.extractManifest(sc_id, projectPath + "app/src/main/");
	}
	
	private void extractJavaFiles() {
		try {
			String packagePath = packageName.replace('.', File.separatorChar);
			
			File logicFile = new File(basePath + "/.blacklogics/data/" + sc_id + "/root_logic");
			if (logicFile.isFile() && logicFile.length() > 0) {
				complex.extractAllLogicsFromJson(logicFile.getAbsolutePath());
			}
			
			extractComponentSupportFiles();
			
			String javaOutputPath = projectPath + "app/src/main/java/" + packagePath + File.separator;
			complex.extractAllJavaCodes(javaOutputPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void extractComponentSupportFiles() {
		try {
			boolean usesRequestNetwork = false;
			boolean usesBluetoothConnect = false;
			
			ActivityStructureRegistry activityRegistry = new ActivityStructureRegistry();
			activityRegistry.load(context, sc_id);
			
			List<ActivityStructureRegistry.ViewItem> activities = activityRegistry.getActivities();
			
			for (int i = 0; i < activities.size(); i++) {
				String activityName = activities.get(i).getJavaName();
				
				List<HashMap<String, String>> components = ProjectLogicRepository.loadComponentLogic(activityName);
				if (components == null) continue;
				
				for (HashMap<String, String> comp : components) {
					String type = comp.get("componentName");
					
					if ("RequestNetwork".equals(type)) {
						usesRequestNetwork = true;
					} else if ("BluetoothConnect".equals(type)) {
						usesBluetoothConnect = true;
					}
				}
				
				if (usesRequestNetwork && usesBluetoothConnect) {
					break;
				}
			}
			
			String packagePath = packageName.replace('.', File.separatorChar);
			String javaOutputPath = projectPath + "app/src/main/java/" + packagePath + File.separator;
			
			File outputDir = new File(javaOutputPath);
			if (!outputDir.exists()) {
				outputDir.mkdirs();
			}
			
			if (usesRequestNetwork) {
				writeGeneratedFile(javaOutputPath + "RequestNetwork.java", Lx.h(packageName));
				writeGeneratedFile(javaOutputPath + "RequestNetworkController.java", Lx.g(packageName));
			}
			
			if (usesBluetoothConnect) {
				writeGeneratedFile(javaOutputPath + "BluetoothConnect.java", Lx.b(packageName));
				writeGeneratedFile(javaOutputPath + "BluetoothController.java", Lx.c(packageName));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void writeGeneratedFile(String filePath, String content) {
		FileWriter writer = null;
		try {
			File file = new File(filePath);
			writer = new FileWriter(file);
			writer.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void extractXmlFiles() {
		complex.extractAllXmlCodes(projectPath + "app/src/main/res/layout/");
	}
	
	private void extractGradleFiles() {
		
		gradleManager.extractGradleFiles(sc_id, projectPath);
		complex.extractGradleBuildConfig(projectPath + "app/");
		complex.extractGradleSettings(projectPath);
	}
	
	private void extractProGuardRules() {
		complex.extractProGuardRules(projectPath + "app/");
	}
	
	private void extractResources() {
		complex.extractAllResources(projectPath + "app/src/main/res/");
		copyAssets();
		applyToolbarChromeFlag();
		resourceManager.extractResources(sc_id, projectPath + "app/src/main/res/");
		if ("include ':app'".equals(complex.getSettingsGradle())) {
			complex.settingsGradle(null);
		}
	}

	private void applyToolbarChromeFlag() {
		try {
			JavaCodeBuilder.setCurrentProject(sc_id, context);

			ActivityStructureRegistry activityRegistry = new ActivityStructureRegistry();
			activityRegistry.load(context, sc_id);

			List<ActivityStructureRegistry.ViewItem> activities = activityRegistry.getActivities();
			List<String> activityNames = new ArrayList<String>();
			if (activities != null) {
				for (int i = 0; i < activities.size(); i++) {
					activityNames.add(activities.get(i).getJavaName());
				}
			}

			boolean hasChrome = JavaCodeBuilder.anyActivityHasCustomToolbarChrome(activityNames);
			resourceManager.setHasCustomToolbarChrome(hasChrome);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void copyAssets() {
		String iconSourcePath = basePath + "/.blacklogics/resources/icons/" + sc_id + "/icon.png";
		File iconSourceFile = new File(iconSourcePath);
		
		String iconDestPath = projectPath + "app/src/main/res/mipmap/icon.png";
		File iconDestFile = new File(iconDestPath);
		
		if (iconSourceFile.exists()) {
			try {
				java.io.FileInputStream fis = new java.io.FileInputStream(iconSourceFile);
				java.io.FileOutputStream fos = new java.io.FileOutputStream(iconDestFile);
				byte[] buffer = new byte[1024];
				int length;
				while ((length = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, length);
				}
				fis.close();
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
				AssetCopyUtil.copyAssetsToExternalStorage(context, "icon", ".blacklogics/mysc/" + sc_id + "/app/src/main/res/mipmap/");
			}
		} else {
			AssetCopyUtil.copyAssetsToExternalStorage(context, "icon", ".blacklogics/mysc/" + sc_id + "/app/src/main/res/mipmap/");
		}
		
		AssetCopyUtil.copyAssetsToExternalStorage(context, "default_image", ".blacklogics/mysc/" + sc_id + "/app/src/main/res/mipmap/");
	}
	
	public void generateDebug() {
		String packagePath = packageName.replace('.', '/');
		String outputPath = projectPath + "app/src/main/java/" + packagePath + File.separator;
		
		FileExtractor extractor = new FileExtractor(context, packageName);
		extractor.extractAndReplace(outputPath);
	}
	
}
