package com.besome.blacklogics.development;

import android.content.Context;
import com.besome.blacklogics.FileUtil;
import com.besome.blacklogics.file.AssetCopyUtil;
import java.io.File;
import android.os.Environment;

public class ProjectBuilder {
	private Complex complex;
	private String sc_id;
	private String basePath;
	private String projectPath;
	private String packageName;
    private Context context;
	
	public ProjectBuilder(Complex complex, String basePath, String sc_id) {
		this.complex = complex;
		this.projectPath = basePath + "/.blacklogics/mysc/" + sc_id + "/";
		this.packageName = complex.getPkgName();
		this.sc_id = sc_id;
		this.basePath = basePath;
	}
    
    public void setContext(Context context) {
        this.context = context;
    }
	
	public void buildProject() {
		createDirectories();
		extractManifest();
		extractJavaFiles();
		extractXmlFiles();
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
		// Check if basePath or sc_id are null or empty
		if (basePath == null || basePath.isEmpty() || sc_id == null || sc_id.isEmpty()) {
			//System.err.println("Error: basePath or sc_id is null or empty");
			return;
		}
		
		// Construct base directory path once
		String dataDir = basePath + "/.blacklogics/data/" + sc_id + "/";
		
		// Handle permissions file
		File permissionFile = new File(dataDir + "permission");
		if (permissionFile.exists() && permissionFile.length() > 0) {
			try {
				complex.addPermissionsFromJsonFile(permissionFile.getAbsolutePath());
			} catch (Exception e) {
				//System.err.println("Error reading permissions file: " + e.getMessage());
			}
		}
		
		// Handle java/activities file
		File javaFile = new File(dataDir + "java");
		if (javaFile.exists() && javaFile.length() > 0) {
			try {
				complex.addActivitiesFromJsonFile(javaFile.getAbsolutePath());
			} catch (Exception e) {
				//System.err.println("Error reading activities file: " + e.getMessage());
			}
		}
		
		// Handle services file
		File serviceFile = new File(dataDir + "service");
		if (serviceFile.exists() && serviceFile.length() > 0) {
			try {
				complex.addServicesFromJsonFile(serviceFile.getAbsolutePath());
			} catch (Exception e) {
				//System.err.println("Error reading services file: " + e.getMessage());
			}
		}
		
		// Check projectPath before manifest extraction
		if (projectPath != null && !projectPath.isEmpty()) {
			try {
				complex.extractManifest(projectPath + "app/src/main/");
			} catch (Exception e) {
				//System.err.println("Error extracting manifest: " + e.getMessage());
			}
		} else {
			//System.err.println("Error: projectPath is null or empty");
		}
	}
	
	private void extractJavaFiles() {
		try {
			// Convert package name to path (e.g., com.example.app -> com/example/app)
			String packagePath = packageName.replace('.', File.separatorChar);
			
			// Check for logic JSON file
			File logicFile = new File(basePath, ".blacklogics/data/" + sc_id + "/root_logic");
			if (logicFile.isFile() && logicFile.length() > 0) {
				complex.extractAllLogicsFromJson(logicFile.getAbsolutePath());
			}
			
			// Extract Java codes to project path
			String javaOutputPath = projectPath + "app/src/main/java/" + packagePath + File.separator;
			complex.extractAllJavaCodes(javaOutputPath);
		} catch (Exception e) {
			//System.err.println("Error extracting Java files: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void extractXmlFiles() {
		complex.extractAllXmlCodes(projectPath + "app/src/main/res/layout/");
	}
	
	private void extractGradleFiles() {
		complex.extractGradleBuild(projectPath);
		complex.extractGradleBuildConfig(projectPath + "app/");
		complex.extractGradleSettings(projectPath);
	}
	
	private void extractProGuardRules() {
		complex.extractProGuardRules(projectPath + "app/");
	}
	
	private void extractResources() {
		complex.extractAllResources(projectPath + "app/src/main/res/");
        copyAssets();
		
		if (complex.getStringResources().isEmpty()) {
			complex.setStringResources(null);
		}
		if (complex.getSettingsGradle().equals("include ':app'")) {
			complex.settingsGradle(null);
		}
	}
	
	private void copyAssets() {
        if (!FileUtil.isExistFile(basePath + "/.blacklogics/mysc/" + sc_id + "/app/src/main/res/mipmap/icon")) {
            AssetCopyUtil.copyAssetsToExternalStorage(context, "icon", ".blacklogics/mysc/" + sc_id + "/app/src/main/res/mipmap/");
        }    
        AssetCopyUtil.copyAssetsToExternalStorage(context, "default_image", ".blacklogics/mysc/" + sc_id + "/app/src/main/res/mipmap/");
	}
	
}