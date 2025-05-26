package com.apk.builder.model;

import com.apk.builder.logger.Logger;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class Project {
	private File mOutputFile;
	private List<File> mResourcesFiles = new ArrayList<>();
	private List<File> mJavaFiles = new ArrayList<>();
	private File mManifestFile;
	private File mAssetsFile;
	private List<Library> mLibraries;
	private List<File> dexFiles = new ArrayList<>();
	private Logger mLogger;
	private BuildSettings mBuildSettings;
	
	public Project() {
		mBuildSettings = new BuildSettings();
	}
	
	public List<File> getResourcesFiles() {
		if (mLogger != null) {
			mLogger.d("Project", "Returning resource files: " + mResourcesFiles);
		}
		return mResourcesFiles;
	}
	
	public void setResourcesFiles(List<File> files) {
		mResourcesFiles = new ArrayList<>(files); // Ensure a new list to avoid external modifications
		if (mLogger != null) {
			mLogger.d("Project", "Set resource files: " + mResourcesFiles);
			for (File file : mResourcesFiles) {
				if (!file.exists()) {
					mLogger.w("Project", "Resource file does not exist: " + file.getAbsolutePath());
				}
			}
		}
		addLibraryResources(); // Ensure library resources are included after setting resources
	}
	
	public List<File> getJavaFiles() {
		return mJavaFiles;
	}
	
	public void setJavaFiles(List<File> files) {
		mJavaFiles = files;
	}
	
	public void setJavaFile(File file) {
		mJavaFiles.clear();
		mJavaFiles.add(file);
	}
	
	public File getOutputFile() {
		return mOutputFile;
	}
	
	public void setOutputFile(File file) {
		mOutputFile = file;
	}
	
	public File getManifestFile() {
		return mManifestFile;
	}
	
	public void setManifestFile(File file) {
		mManifestFile = file;
	}
	
	public File getAssetsFile() {
		return mAssetsFile;
	}
	
	public void setAssetsFile(File file) {
		mAssetsFile = file;
	}
	
	public List<Library> getLibraries() {
		return mLibraries;
	}
	
	public void setLibraries(List<Library> libraries) {
		mLibraries = libraries;
		addLibraryResources(); // Add library resources when libraries are set
	}
	
	public int getMinSdk() {
		return mBuildSettings.getMinSdk();
	}
	
	public void setMinSdk(int sdk) {
		mBuildSettings.setMinSdk(sdk);
	}
	
	public int getTargetSdk() {
		return mBuildSettings.getTargetSdk();
	}
	
	public void setTargetSdk(int sdk) {
		mBuildSettings.setTargetSdk(sdk);
	}
	
	public int getVersionCode() {
		return mBuildSettings.getVersionCode();
	}
	
	public void setVersionCode(int code) {
		mBuildSettings.setVersionCode(code);
	}
	
	public String getVersionName() {
		return mBuildSettings.getVersionName();
	}
	
	public void setVersionName(String str) {
		mBuildSettings.setVersionName(str);
	}
	
	public Logger getLogger() {
		return mLogger;
	}
	
	public void setLogger(Logger logger) {
		mLogger = logger;
	}
	
	public String getProjectName() {
		return mBuildSettings.getProjectName();
	}
	
	public void setProjectName(String name) {
		mBuildSettings.setProjectName(name);
	}
	
	public String getProguardFile() {
		return mBuildSettings.getProguardFile() != null ? mBuildSettings.getProguardFile().getAbsolutePath() : null;
	}
	
	public void setProguardFile(File proguard) {
		mBuildSettings.setProguardFile(proguard);
	}
	
	public BuildSettings getBuildSettings() {
		return mBuildSettings;
	}
	
	public void setBuildSettings(BuildSettings settings) {
		this.mBuildSettings = settings;
	}
	
	public List<File> getDexFiles() {
		return dexFiles;
	}
	
	public void setDexFiles(List<File> dexFiles) {
		this.dexFiles = dexFiles;
	}
	
	// New method to add library resources to mResourcesFiles
	private void addLibraryResources() {
		if (mLibraries != null) {
			for (Library library : mLibraries) {
				if (library.requiresResourceFile()) {
					File resFile = library.getResourcesFile();
					if (resFile != null && resFile.exists() && !mResourcesFiles.contains(resFile)) {
						mResourcesFiles.add(resFile);
						if (mLogger != null) {
							mLogger.d("Project", "Added library resource: " + resFile.getAbsolutePath());
						}
					} else if (mLogger != null) {
						mLogger.w("Project", "Library resource not found or already included: " + library.getName());
					}
				}
			}
		}
	}
}
