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
	protected Project project; // Add project field to access project data
	
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
	
	public File getAndroidJarFile() {
		File check = new File(ApplicationLoader.applicationContext.getFilesDir() + "/temp/android.jar");
		
		if (check.exists()) {
			return check;
		}
		
		Decompress.unzipFromAssets(ApplicationLoader.applicationContext, "android.jar.zip", check.getParentFile().getAbsolutePath());
		
		return check;
	}
	
	public void enableAndroidX() throws CompilerException, IOException {
    if (project == null) {
        throw new CompilerException("Project not set for compiler");
    }

    onProgressUpdate("Enabling AndroidX support...");

    // Define paths
    String assetsAndroidXPath = "androidx";
    String destinationPath = ApplicationLoader.applicationContext.getFilesDir() + "/temp/androidx";
    File destinationDir = new File(destinationPath);

    // Create destination directory if it doesn't exist
    if (!destinationDir.exists() && !destinationDir.mkdirs()) {
        throw new CompilerException("Failed to create AndroidX directory: " + destinationPath);
    }

    // Unzip AndroidX libraries from assets
    try {
        Decompress.unzipFromAssets(ApplicationLoader.applicationContext, assetsAndroidXPath + ".zip", destinationPath);
        project.getLogger().d("Compiler", "Extracted AndroidX libraries to: " + destinationPath);
    } catch (Exception e) {
        project.getLogger().e("Compiler", "Failed to extract AndroidX libraries: " + e.getMessage());
        throw new CompilerException("Failed to extract AndroidX libraries: " + e.getMessage());
    }

    // Create Library objects for AndroidX libraries
    List<Library> androidxLibraries = Library.fromFile(destinationDir);
    if (androidxLibraries.isEmpty()) {
        project.getLogger().w("Compiler", "No AndroidX libraries found in: " + destinationPath);
        return;
    }

    // Add AndroidX libraries to the existing project libraries without overwriting
    List<Library> projectLibraries = project.getLibraries();
    if (projectLibraries == null) {
        projectLibraries = new ArrayList<>();
        project.setLibraries(projectLibraries); // Initialize if null
    }
    // Add only new AndroidX libraries, avoiding duplicates
    for (Library androidxLibrary : androidxLibraries) {
        if (!projectLibraries.contains(androidxLibrary)) { // Check for duplicates
            projectLibraries.add(androidxLibrary);
        }
    }

    project.getLogger().d("Compiler", "Added " + androidxLibraries.size() + " AndroidX libraries to project");
    onProgressUpdate("AndroidX libraries and their resources enabled successfully");
}
}
