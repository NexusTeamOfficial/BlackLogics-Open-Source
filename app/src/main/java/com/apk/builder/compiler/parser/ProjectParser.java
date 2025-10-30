package com.besome.blacklogics.parser;

import com.apk.builder.model.Project;
import com.apk.builder.logger.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProjectParser {
	private static Project project;
	
	public ProjectParser(Project project) {
		this.project = project; // this.project refers to the static variable
	}
	
	public static Project parseProjectFromJson(String jsonString, File projectDir) throws JSONException {
		project = new Project();
		// project.setLogger(new Logger());
		HashMap<String, Object> pathMap = new HashMap<>();
		
		if (jsonString == null || jsonString.isEmpty()) {
			project.getLogger().w("ProjectParser", "Empty or null JSON string");
			return project;
		}
		
		JSONObject jsonObject = new JSONObject(jsonString);
		project.getLogger().d("ProjectParser", "Parsing JSON: " + jsonString);
		
		// Parse manifest file
		String manifestPath = "";
		Object manifestObj = jsonObject.opt("manifestFile");
		if (manifestObj instanceof JSONObject) {
			manifestPath = ((JSONObject) manifestObj).optString("path", "");
			project.getLogger().d("ProjectParser", "Manifest path from nested object: " + manifestPath);
		} else {
			manifestPath = jsonObject.optString("manifestFile", "");
			project.getLogger().d("ProjectParser", "Manifest path from string: " + manifestPath);
		}
		
		if (!manifestPath.isEmpty()) {
			File manifestFile = new File(manifestPath);
			if (!manifestFile.isAbsolute() && projectDir != null) {
				manifestFile = new File(projectDir, manifestPath);
				project.getLogger().d("ProjectParser", "Resolved relative manifest path: " + manifestFile.getAbsolutePath());
			}
			if (manifestFile.exists() && manifestFile.isFile() && manifestFile.canRead()) {
				project.setManifestFile(manifestFile);
				pathMap.put("manifestFile", manifestFile.getAbsolutePath());
				project.getLogger().d("ProjectParser", "Manifest file set: " + manifestFile.getAbsolutePath());
			} else {
				project.getLogger().e("ProjectParser", "Manifest file not found or inaccessible: " + manifestFile.getAbsolutePath());
				// Instead of throwing, set a default or continue
				// Example: Use a default manifest in projectDir
				File defaultManifest = new File(projectDir, "app/src/main/AndroidManifest.xml");
				if (defaultManifest.exists() && defaultManifest.isFile() && defaultManifest.canRead()) {
					project.setManifestFile(defaultManifest);
					pathMap.put("manifestFile", defaultManifest.getAbsolutePath());
					project.getLogger().w("ProjectParser", "Using default manifest: " + defaultManifest.getAbsolutePath());
				} else {
					project.getLogger().e("ProjectParser", "No valid manifest found, build may fail in AAPT2");
					// Optionally throw if manifest is critical
					// throw new JSONException("Invalid manifest file: " + manifestFile.getAbsolutePath());
				}
			}
		} else {
			project.getLogger().e("ProjectParser", "No manifest file specified in JSON");
			// Try default manifest
			File defaultManifest = new File(projectDir, "app/src/main/AndroidManifest.xml");
			if (defaultManifest.exists() && defaultManifest.isFile() && defaultManifest.canRead()) {
				project.setManifestFile(defaultManifest);
				pathMap.put("manifestFile", defaultManifest.getAbsolutePath());
				project.getLogger().w("ProjectParser", "Using default manifest: " + defaultManifest.getAbsolutePath());
			} else {
				project.getLogger().e("ProjectParser", "No default manifest found, build may fail in AAPT2");
			}
		}
		
		// Parse resource files
		List<File> resourceFiles = new ArrayList<>();
		JSONArray resourceArray = jsonObject.optJSONArray("resourceFiles");
		if (resourceArray != null) {
			for (int i = 0; i < resourceArray.length(); i++) {
				String path = resourceArray.optString(i, "");
				if (!path.isEmpty()) {
					File resDir = new File(path);
					if (!resDir.isAbsolute() && projectDir != null) {
						resDir = new File(projectDir, path);
						project.getLogger().d("ProjectParser", "Resolved relative resource path: " + resDir.getAbsolutePath());
					}
					if (resDir.exists() && resDir.isDirectory()) {
						resourceFiles.add(resDir);
						pathMap.put("resourceFile_" + i, resDir.getAbsolutePath());
						project.getLogger().d("ProjectParser", "Added resource directory: " + resDir.getAbsolutePath());
					} else {
						project.getLogger().w("ProjectParser", "Invalid resource directory: " + resDir.getAbsolutePath());
					}
				}
			}
		//////////	project.setResourceFiles(resourceFiles);
		}
		
		// Parse Java files
		List<File> javaFiles = new ArrayList<>();
		JSONArray javaArray = jsonObject.optJSONArray("javaFiles");
		if (javaArray != null) {
			for (int i = 0; i < javaArray.length(); i++) {
				String path = javaArray.optString(i, "");
				if (!path.isEmpty()) {
					File javaDir = new File(path);
					if (!javaDir.isAbsolute() && projectDir != null) {
						javaDir = new File(projectDir, path);
						project.getLogger().d("ProjectParser", "Resolved relative Java path: " + javaDir.getAbsolutePath());
					}
					if (javaDir.exists() && javaDir.isDirectory()) {
						javaFiles.add(javaDir);
						pathMap.put("javaFile_" + i, javaDir.getAbsolutePath());
						project.getLogger().d("ProjectParser", "Added Java directory: " + javaDir.getAbsolutePath());
					} else {
						project.getLogger().w("ProjectParser", "Invalid Java directory: " + javaDir.getAbsolutePath());
					}
				}
			}
			project.setJavaFiles(javaFiles);
		}
		
		// Parse assets file
		String assetsFile = jsonObject.optString("assetsFile", "");
		if (!assetsFile.isEmpty()) {
			File assetsDir = new File(assetsFile);
			if (!assetsDir.isAbsolute() && projectDir != null) {
				assetsDir = new File(projectDir, assetsFile);
				project.getLogger().d("ProjectParser", "Resolved relative assets path: " + assetsDir.getAbsolutePath());
			}
			if (assetsDir.exists() && assetsDir.isDirectory()) {
				project.setAssetsFile(assetsDir);
				pathMap.put("assetsFile", assetsDir.getAbsolutePath());
				project.getLogger().d("ProjectParser", "Set assets directory: " + assetsDir.getAbsolutePath());
			} else {
				project.getLogger().w("ProjectParser", "Invalid assets directory: " + assetsDir.getAbsolutePath());
			}
		}
		
		// Parse proguard file
		String proguardFile = jsonObject.optString("proguardFile", "");
		if (!proguardFile.isEmpty()) {
			File proguard = new File(proguardFile);
			if (!proguard.isAbsolute() && projectDir != null) {
				proguard = new File(projectDir, proguardFile);
				project.getLogger().d("ProjectParser", "Resolved relative proguard path: " + proguard.getAbsolutePath());
			}
			if (proguard.exists() && proguard.isFile()) {
				project.setProguardFile(proguard);
				pathMap.put("proguardFile", proguard.getAbsolutePath());
				project.getLogger().d("ProjectParser", "Set proguard file: " + proguard.getAbsolutePath());
			} else {
				project.getLogger().w("ProjectParser", "Invalid proguard file: " + proguard.getAbsolutePath());
			}
		}
		
		project.getLogger().d("ProjectParser", "Parsed paths: " + pathMap);
		return project;
	}
}
