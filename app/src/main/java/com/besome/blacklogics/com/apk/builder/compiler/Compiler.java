package com.tyron.compiler;

import com.apk.builder.ApplicationLoader;
import com.apk.builder.util.Decompress;
import com.tyron.compiler.exception.CompilerException;
import com.apk.builder.model.Project;
import com.apk.builder.model.Library;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Compiler {
	
	public interface OnProgressUpdateListener {
		void onProgressUpdate(String... update);
	}
	
	protected OnProgressUpdateListener listener;
	protected Project project;
	
	public void setProject(Project project) {
		this.project = project;
	}
	
	public void setProgressListener(OnProgressUpdateListener listener) {
		this.listener = listener;
	}
	
	public void onProgressUpdate(String... update) {
		if (listener != null) {
			listener.onProgressUpdate(update);
		}
	}
	
	abstract public void prepare() throws CompilerException;
	
	abstract public void run() throws CompilerException, IOException;
	
	public File getAndroidJarFile() throws CompilerException {
		File check = new File(ApplicationLoader.applicationContext.getFilesDir() + "/temp/android.jar");
		
		if (check.exists()) {
			return check;
		}
		
		// Verify if android.jar.zip exists in assets
		try {
			Decompress.unzipFromAssets(ApplicationLoader.applicationContext, "android.jar.zip", check.getParentFile().getAbsolutePath());
			if (!check.exists()) {
				throw new CompilerException("Failed to unzip android.jar: File not found at " + check.getAbsolutePath());
			}
			project.getLogger().d("Compiler", "Extracted android.jar to: " + check.getAbsolutePath());
		} catch (Exception e) {
			project.getLogger().e("Compiler", "Failed to extract android.jar: " + e.getMessage());
			throw new CompilerException("Failed to extract android.jar: " + e.getMessage());
		}
		
		return check;
	}
	
	public void enableAndroidX() throws CompilerException, IOException {
		if (project == null) {
			throw new CompilerException("Project not set for compiler");
		}

		onProgressUpdate("Enabling AndroidX support...");

		// Define paths
		String assetsAndroidXPath = "androidx.zip";
		String destinationPath = ApplicationLoader.applicationContext.getFilesDir() + "/temp/androidx";
		File destinationDir = new File(destinationPath);

		// Create destination directory
		if (!destinationDir.exists()) {
			if (!destinationDir.mkdirs()) {
				project.getLogger().e("Compiler", "Failed to create AndroidX directory: " + destinationPath);
				throw new CompilerException("Failed to create AndroidX directory: " + destinationPath);
			}
			project.getLogger().d("Compiler", "Created AndroidX directory: " + destinationPath);
		}

		// Verify if androidx.zip exists in assets
		try {
			// Check if asset file exists (requires API level 23+ for AssetManager.list())
			// If you need to support lower APIs, consider using a try-catch as below
			Decompress.unzipFromAssets(ApplicationLoader.applicationContext, assetsAndroidXPath, destinationPath);
			project.getLogger().d("Compiler", "Extracted AndroidX libraries to: " + destinationPath);
		} catch (Exception e) {
			project.getLogger().e("Compiler", "Failed to extract AndroidX libraries: " + e.getMessage());
			throw new CompilerException("Failed to extract AndroidX libraries: " + e.getMessage());
		}

		// Verify extracted files
		if (!destinationDir.exists() || destinationDir.listFiles() == null || destinationDir.listFiles().length == 0) {
			project.getLogger().e("Compiler", "No files found in AndroidX directory: " + destinationPath);
			throw new CompilerException("No files extracted to AndroidX directory: " + destinationPath);
		}

		// Create Library objects for AndroidX libraries
		List<Library> androidxLibraries = Library.fromFile(destinationDir);
		if (androidxLibraries.isEmpty()) {
			project.getLogger().w("Compiler", "No AndroidX libraries found in: " + destinationPath);
			return; // Allow continuation even if no libraries are found
		}

		// Add AndroidX libraries to project
		List<Library> projectLibraries = project.getLibraries();
		if (projectLibraries == null) {
			projectLibraries = new ArrayList<>();
			project.setLibraries(projectLibraries);
		}
		int addedCount = 0;
		for (Library androidxLibrary : androidxLibraries) {
			if (!projectLibraries.contains(androidxLibrary)) {
				projectLibraries.add(androidxLibrary);
				addedCount++;
			}
		}

		project.getLogger().d("Compiler", "Added " + addedCount + " AndroidX libraries to project");
		onProgressUpdate("AndroidX libraries enabled successfully");
	}
}