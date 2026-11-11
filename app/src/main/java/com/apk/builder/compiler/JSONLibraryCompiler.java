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

public class JSONLibraryCompiler extends Compiler {
    
    private static final String TAG = "JSONLibraryCompiler";
    
    private Project mProject;
    private String jsonInput;
    private List<Library> jsonLibraries;
    
    public JSONLibraryCompiler(Project project, String jsonInput) {
        this.mProject = project;
        this.jsonInput = jsonInput;
        this.jsonLibraries = new ArrayList<>();
    }
    
    @Override
    public void prepare() throws CompilerException {
        try {

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
                

                Library library = new Library(basePath != null ? basePath : "/tmp/" + name);
                

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
            

            if (!jsonLibraries.isEmpty()) {
                List<Library> existingLibraries = mProject.getLibraries();
                if (existingLibraries == null) {
                    mProject.setLibraries(new ArrayList<Library>());
                }
                mProject.getLibraries().addAll(jsonLibraries);
                mProject.getLogger().d(TAG, "Added " + jsonLibraries.size() + " JSON libraries to project");
            }
            
        } catch (JSONException e) {
            throw new CompilerException("Failed to parse JSON library data: " + e.getMessage());
        }
    }
    
    @Override
    public void run() throws CompilerException, IOException {
        onProgressUpdate("Processing JSON libraries...");
        mProject.getLogger().d(TAG, "Starting JSON libraries compilation...");
        
        if (jsonLibraries.isEmpty()) {
            mProject.getLogger().w(TAG, "No valid JSON libraries to compile");
            return;
        }
        

        boolean hasResources = false;
        for (Library library : jsonLibraries) {
            if (library.requiresResourceFile()) {
                hasResources = true;
                break;
            }
        }
        
        if (hasResources) {
            AAPT2Compiler aapt2Compiler = new AAPT2Compiler(mProject);
            aapt2Compiler.setProgressListener(new OnProgressUpdateListener() {
                @Override
                public void onProgressUpdate(String... update) {
                    JSONLibraryCompiler.this.onProgressUpdate(update);
                }
            });
            aapt2Compiler.prepare();
            aapt2Compiler.run();
        }
        

        boolean hasJarFiles = false;
        for (Library library : jsonLibraries) {
            if (library.getClassJarFile().exists()) {
                hasJarFiles = true;
                break;
            }
        }
        
        if (hasJarFiles) {
            ECJCompiler ecjCompiler = new ECJCompiler(mProject);
            ecjCompiler.setProgressListener(new OnProgressUpdateListener() {
                @Override
                public void onProgressUpdate(String... update) {
                    JSONLibraryCompiler.this.onProgressUpdate(update);
                }
            });
            ecjCompiler.prepare();
            ecjCompiler.run();
        }
        

        boolean hasDexOrJarFiles = false;
        for (Library library : jsonLibraries) {
            if (!library.getDexFiles().isEmpty() || library.getClassJarFile().exists()) {
                hasDexOrJarFiles = true;
                break;
            }
        }
        
        if (hasDexOrJarFiles) {
            D8Compiler d8Compiler = new D8Compiler(mProject);
            d8Compiler.setProgressListener(new OnProgressUpdateListener() {
                @Override
                public void onProgressUpdate(String... update) {
                    JSONLibraryCompiler.this.onProgressUpdate(update);
                }
            });
            d8Compiler.prepare();
            d8Compiler.run();
        }
        
        mProject.getLogger().d(TAG, "JSON libraries compilation completed");
    }
    
    public List<Library> getJsonLibraries() {
        return jsonLibraries;
    }
    
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