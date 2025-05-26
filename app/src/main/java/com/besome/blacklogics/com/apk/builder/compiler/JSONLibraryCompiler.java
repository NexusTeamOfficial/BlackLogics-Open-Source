package com.tyron.compiler;

import com.apk.builder.model.Project;
import com.apk.builder.model.Library;
import com.tyron.compiler.exception.CompilerException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A compiler that processes libraries specified in JSON format for inclusion in an Android project build.
 * <p>
 * This class is part of the open-source Nexus project developed by NexusTeam. It parses a JSON array
 * containing library information (such as dex paths, jar paths, and resource paths) and integrates
 * these libraries into the compilation process using the existing AAPT2, ECJ, and D8 compilers.
 * </p>
 * 
 * @author NexusTeam
 * @version 1.0
 * @since April 10, 2025
 * @see Compiler
 * @see Project
 * @see Library
 */
public class JSONLibraryCompiler extends Compiler {
    
    private static final String TAG = "JSONLibraryCompiler";
    
    private Project mProject;
    private String jsonInput;
    private List<Library> jsonLibraries;
    
    /**
     * Constructs a new JSONLibraryCompiler instance.
     * 
     * @param project The project to which the JSON libraries will be added
     * @param jsonInput A JSON string containing an array of library specifications
     */
    public JSONLibraryCompiler(Project project, String jsonInput) {
        this.mProject = project;
        this.jsonInput = jsonInput;
        this.jsonLibraries = new ArrayList<>();
    }
    
    /**
     * Prepares the compiler by parsing the JSON input and creating Library objects.
     * <p>
     * This method validates the JSON structure, extracts library details, and adds them to the project's
     * library list. It uses the base path from provided file paths and leverages the Library class's
     * helper methods for file discovery.
     * </p>
     * 
     * @throws CompilerException if the JSON parsing fails or if the input is malformed
     */
    @Override
    public void prepare() throws CompilerException {
        try {
            // Parse JSON input
            JSONArray librariesArray = new JSONArray(jsonInput);
            jsonLibraries.clear();
            
            for (int i = 0; i < librariesArray.length(); i++) {
                JSONObject libObj = librariesArray.getJSONObject(i);
                
                String name = libObj.getString("name");
                String dexPath = libObj.optString("dexPath", null);
                String jarPath = libObj.optString("jarPath", null);
                String manifestPath = libObj.optString("manifestPath", null);
                String packageName = libObj.optString("packageName", null);
                String resPath = libObj.optString("resPath", null);
                
                // Determine base path from provided paths
                String basePath = null;
                if (jarPath != null && !jarPath.isEmpty() && new File(jarPath).exists()) {
                    basePath = new File(jarPath).getParent();
                } else if (dexPath != null && !dexPath.isEmpty() && new File(dexPath).exists()) {
                    basePath = new File(dexPath).getParent();
                } else if (resPath != null && !resPath.isEmpty() && new File(resPath).exists()) {
                    basePath = new File(resPath).getParent();
                } else if (manifestPath != null && !manifestPath.isEmpty() && new File(manifestPath).exists()) {
                    basePath = new File(manifestPath).getParent();
                }
                
                // Create Library object
                Library library = new Library(basePath != null ? basePath : "/tmp/" + name);
                
                // Log explicit paths
                if (dexPath != null && !dexPath.isEmpty()) {
                    File dexFile = new File(dexPath);
                    if (dexFile.exists()) {
                        mProject.getLogger().d(TAG, "Using explicit dex file: " + dexPath);
                    } else {
                        mProject.getLogger().w(TAG, "Dex file not found: " + dexPath);
                    }
                }
                
                if (jarPath != null && !jarPath.isEmpty()) {
                    File jarFile = new File(jarPath);
                    if (jarFile.exists()) {
                        mProject.getLogger().d(TAG, "Using explicit jar file: " + jarPath);
                    } else {
                        mProject.getLogger().w(TAG, "Jar file not found: " + jarPath);
                    }
                }
                
                if (manifestPath != null && !manifestPath.isEmpty()) {
                    File manifestFile = new File(manifestPath);
                    if (manifestFile.exists()) {
                        mProject.getLogger().d(TAG, "Using explicit manifest: " + manifestPath);
                    } else {
                        mProject.getLogger().w(TAG, "Manifest file not found: " + manifestPath);
                    }
                }
                
                if (resPath != null && !resPath.isEmpty()) {
                    File resFile = new File(resPath);
                    if (resFile.exists()) {
                        mProject.getLogger().d(TAG, "Using explicit resources: " + resPath);
                    } else {
                        mProject.getLogger().w(TAG, "Resource path not found: " + resPath);
                    }
                }
                
                if (packageName != null && !packageName.isEmpty()) {
                    mProject.getLogger().d(TAG, "Using explicit package name: " + packageName);
                } else {
                    String extractedPackage = library.getPackageName();
                    if (extractedPackage != null) {
                        mProject.getLogger().d(TAG, "Extracted package name: " + extractedPackage);
                    }
                }
                
                if (validateLibrary(library)) {
                    jsonLibraries.add(library);
                    mProject.getLogger().d(TAG, "Successfully added JSON library: " + name);
                } else {
                    mProject.getLogger().w(TAG, "Skipping invalid library: " + name);
                }
            }
            
            // Add to project libraries
            if (!jsonLibraries.isEmpty()) {
                List<Library> existingLibraries = mProject.getLibraries();
                if (existingLibraries == null) {
                    mProject.setLibraries(new ArrayList<>());
                }
                mProject.getLibraries().addAll(jsonLibraries);
                mProject.getLogger().d(TAG, "Added " + jsonLibraries.size() + " JSON libraries to project");
            }
            
        } catch (JSONException e) {
            throw new CompilerException("Failed to parse JSON library data: " + e.getMessage());
        }
    }
    
    /**
     * Executes the compilation process for the JSON-specified libraries.
     * <p>
     * This method conditionally runs the AAPT2, ECJ, and D8 compilers based on the presence of
     * resources, jar files, or dex files in the libraries. It integrates with the existing Nexus
     * project compilation pipeline.
     * </p>
     * 
     * @throws CompilerException if any compilation step fails
     * @throws IOException if file operations fail during compilation
     */
    @Override
    public void run() throws CompilerException, IOException {
        onProgressUpdate("Processing JSON libraries...");
        mProject.getLogger().d(TAG, "Starting JSON libraries compilation...");
        
        if (jsonLibraries.isEmpty()) {
            mProject.getLogger().w(TAG, "No valid JSON libraries to compile");
            return;
        }
        
        // Compile resources
        boolean hasResources = jsonLibraries.stream().anyMatch(Library::requiresResourceFile);
        if (hasResources) {
            AAPT2Compiler aapt2Compiler = new AAPT2Compiler(mProject);
            aapt2Compiler.setProgressListener(this::onProgressUpdate);
            aapt2Compiler.prepare();
            aapt2Compiler.run();
        }
        
        // Compile Java files
        if (jsonLibraries.stream().anyMatch(lib -> lib.getClassJarFile().exists())) {
            ECJCompiler ecjCompiler = new ECJCompiler(mProject);
            ecjCompiler.setProgressListener(this::onProgressUpdate);
            ecjCompiler.prepare();
            ecjCompiler.run();
        }
        
        // Process dex and jars
        if (jsonLibraries.stream().anyMatch(lib -> !lib.getDexFiles().isEmpty() || lib.getClassJarFile().exists())) {
            D8Compiler d8Compiler = new D8Compiler(mProject);
            d8Compiler.setProgressListener(this::onProgressUpdate);
            d8Compiler.prepare();
            d8Compiler.run();
        }
        
        mProject.getLogger().d(TAG, "JSON libraries compilation completed");
    }
    
    /**
     * Returns the list of libraries parsed from the JSON input.
     * 
     * @return a list of Library objects
     */
    public List<Library> getJsonLibraries() {
        return jsonLibraries;
    }
    
    /**
     * Validates a library to ensure it has at least one usable file.
     * 
     * @param library the Library object to validate
     * @return true if the library has valid files, false otherwise
     */
    private boolean validateLibrary(Library library) {
        boolean hasValidFile = false;
        
        if (!library.getDexFiles().isEmpty()) {
            hasValidFile = true;
        }
        if (library.getClassJarFile().exists()) {
            hasValidFile = true;
        }
        if (library.requiresResourceFile()) {
            hasValidFile = true;
        }
        
        if (!hasValidFile) {
            mProject.getLogger().w(TAG, "Library " + library.getName() + " has no valid files");
        }
        
        return hasValidFile;
    }
}