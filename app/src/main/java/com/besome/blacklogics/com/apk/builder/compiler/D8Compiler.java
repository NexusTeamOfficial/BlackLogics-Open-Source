package com.tyron.compiler;

import com.apk.builder.ApplicationLoader;
import com.apk.builder.model.Project;
import com.apk.builder.model.Library;
import com.tyron.compiler.exception.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import com.android.tools.r8.D8;

public class D8Compiler extends Compiler {
	
	private static final String TAG = "D8";
	private final Project mProject;
	private List<String> classpath;
	
	public D8Compiler(Project project) {
		this.mProject = project;
		setProject(project); // Set project for parent Compiler class
		try {
			// Explicitly enable AndroidX in constructor
			enableAndroidX();
		} catch (Exception e) {
			mProject.getLogger().e(TAG, "Failed to enable AndroidX: " + e.getMessage());
		}
	}
	
	@Override
	public void prepare() throws CompilerException {
		mProject.getLogger().d(TAG, "Preparing D8 compiler");
		System.gc();
		Runtime.getRuntime().gc();
		mProject.getLogger().d(TAG, "Heap free before prepare: " +
				(Runtime.getRuntime().freeMemory() / (1024 * 1024)) + "MB");
		
		// Validate working directory
		File workingDir = mProject.getWorkingDirectory();
		if (workingDir == null || !workingDir.exists() || !workingDir.isDirectory()) {
			throw new CompilerException("Invalid working directory for D8: " + (workingDir != null ? workingDir.getAbsolutePath() : "null"));
		}
		
		// Validate AndroidX libraries
		String androidxPath = ApplicationLoader.applicationContext.getFilesDir() + "/temp/androidx"; // Changed to /temp/androidx to match Compiler.java
		File androidxDir = new File(androidxPath);
		if (androidxDir.exists() && androidxDir.isDirectory()) {
			for (Library library : mProject.getLibraries()) {
				if (library.getName().contains("androidx")) {
					File classJar = library.getClassJarFile();
					if (!classJar.exists()) {
						mProject.getLogger().e(TAG, "AndroidX classes.jar missing: " + classJar.getAbsolutePath());
						throw new CompilerException("AndroidX classes.jar missing: " + library.getName());
					}
					if (library.requiresResourceFile() && !library.getResourcesFile().exists()) {
						mProject.getLogger().w(TAG, "AndroidX resources missing for: " + library.getName());
					}
				}
			}
		} else {
			mProject.getLogger().w(TAG, "AndroidX directory not found: " + androidxPath);
		}
	}
	
	@Override
	public void run() throws CompilerException {
		onProgressUpdate("D8 > Compiling to DEX...");
		mProject.getLogger().d(TAG, "Running D8 compilation...");
		mProject.getLogger().d(TAG, "Heap free before run: " +
				(Runtime.getRuntime().freeMemory() / (1024 * 1024)) + "MB");
		
		List<String> args = new ArrayList<>();
		args.add("--release");
		args.add("--min-api");
		args.add(String.valueOf(mProject.getMinSdk()));
		args.add("--lib");
		args.add(getAndroidJarFile().getAbsolutePath());
		
		// Output directory
		File dexOutput = new File(mProject.getOutputFile(), "bin/dex");
		if (!dexOutput.exists() && !dexOutput.mkdirs()) {
			throw new CompilerException("Failed to create DEX output dir: " + dexOutput.getAbsolutePath());
		}
		args.add("--output");
		args.add(dexOutput.getAbsolutePath());
		
		// Add project class files
		List<File> classes = getClassFiles(new File(mProject.getOutputFile(), "bin/classes"));
		mProject.getLogger().d(TAG, "Found " + classes.size() + " class files");
		for (File file : classes) {
			args.add(file.getAbsolutePath());
		}
		
		// Add libraries (JARs)
		Set<String> addedFiles = new HashSet<>();
		for (Library library : mProject.getLibraries()) {
			File jar = library.getClassJarFile();
			if (jar.exists() && addedFiles.add(jar.getAbsolutePath())) {
				args.add(jar.getAbsolutePath());
				mProject.getLogger().d(TAG, "Added library jar: " + jar.getAbsolutePath());
			}
		}
		
		// Add classpath
		args.addAll(classpath());
		
		mProject.getLogger().d(TAG, "D8 command: " + args);
		try {
			D8.main(args.toArray(new String[0]));
		} catch (Exception e) {
			mProject.getLogger().e(TAG, "D8 compilation failed: " + e.getMessage());
			throw new CompilerException("D8 compilation failed: " + e.getMessage());
		}
		
		// Merge dex if needed
		onProgressUpdate("D8 > Merging dex files...");
		List<File> dexFiles = getDexFiles();
		if (!dexFiles.isEmpty()) {
			List<String> mergeArgs = new ArrayList<>();
			for (File file : dexFiles) {
				mergeArgs.add(file.getAbsolutePath());
			}
			mergeArgs.add("--output");
			mergeArgs.add(dexOutput.getAbsolutePath());
			try {
				D8.main(mergeArgs.toArray(new String[0]));
			} catch (Exception e) {
				mProject.getLogger().e(TAG, "DEX merging failed: " + e.getMessage());
				throw new CompilerException("DEX merging failed: " + e.getMessage());
			}
		}
		mProject.getLogger().d(TAG, "D8 compilation completed");
	}
	
	public List<File> getDexFiles() {
		List<File> files = new ArrayList<>();
		File dexDir = new File(mProject.getOutputFile(), "bin/dex");
		File[] dexArr = dexDir.listFiles();
		if (dexArr != null) {
			for (File file : dexArr) {
				if (file.getName().endsWith(".dex")) {
					files.add(file);
				}
			}
		}
		return files;
	}
	
	private List<File> getClassFiles(File dir) {
		List<File> files = new ArrayList<>();
		if (dir.exists() && dir.isDirectory()) {
			File[] fileArr = dir.listFiles();
			if (fileArr != null) {
				for (File file : fileArr) {
					if (file.isDirectory()) {
						files.addAll(getClassFiles(file));
					} else if (file.getName().endsWith(".class")) {
						files.add(file);
					}
				}
			}
		}
		return files;
	}
	
	private void dexLibrary(Library library) throws Exception {
		List<String> args = new ArrayList<>();
		File classJar = library.getClassJarFile();
		if (!classJar.exists()) {
			throw new CompilerException("Classes.jar missing for library: " + library.getName());
		}
		args.add(classJar.getAbsolutePath());
		args.add("--release");
		args.add("--min-api");
		args.add(String.valueOf(mProject.getMinSdk()));
		args.add("--lib");
		args.add(getAndroidJarFile().getAbsolutePath());
		args.add("--output");
		args.add(library.getPath().getAbsolutePath());
		args.addAll(classpath());
		
		try {
			D8.main(args.toArray(new String[0]));
		} catch (Exception e) {
			mProject.getLogger().e(TAG, "Failed to dex library " + library.getName() + ": " + e.getMessage());
			throw new CompilerException("Failed to dex library " + library.getName() + ": " + e.getMessage());
		}
	}
	
	public List<String> classpath() {
		if (classpath != null) {
			return classpath;
		}
		classpath = new ArrayList<>();
		Set<String> added = new HashSet<>();
		for (Library library : mProject.getLibraries()) {
			File classJar = library.getClassJarFile();
			if (classJar.exists() && added.add(classJar.getAbsolutePath())) {
				classpath.add("--classpath");
				classpath.add(classJar.getAbsolutePath());
			}
		}
		return classpath;
	}
}