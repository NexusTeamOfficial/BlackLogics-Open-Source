package com.nexusteam.blacklogics.parser;

import com.apk.builder.model.Library;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    
    public static List<Library> parseLibrariesFromJson(String jsonString) throws JSONException {
        List<Library> libraries = new ArrayList<>();
        
        if (jsonString == null || jsonString.isEmpty()) {
            System.out.println("JsonParser: JSON string is null or empty");
            return libraries;
        }
        
        JSONArray jsonArray = new JSONArray(jsonString);
        
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject libObject = jsonArray.getJSONObject(i);
            

            String jarPath = libObject.optString("jarPath", "");
            String dexPath = libObject.optString("dexPath", "");
            String name = libObject.optString("name", "");
            String manifestPath = libObject.optString("manifestPath", "");
            String resPath = libObject.optString("resPath", "");
            

            String libraryPath = "";
            if (!jarPath.isEmpty()) {
                libraryPath = new File(jarPath).getParent();
            } else if (!dexPath.isEmpty()) {
                libraryPath = new File(dexPath).getParent();
            }
            
            if (!libraryPath.isEmpty()) {
                Library library = new Library(libraryPath);
                library.setName(name);
                if (!manifestPath.isEmpty()) {
                    library.setManifestPath(manifestPath);
                }
                if (!resPath.isEmpty()) {
                    File resFile = new File(resPath);
                    if (resFile.exists() && resFile.isDirectory()) {
                        library.setResPath(resPath);
                        System.out.println("JsonParser: Set resource path for library " + name + ": " + resPath);
                    } else {
                        System.out.println("JsonParser: Invalid or non-existent resource path for library " + name + ": " + resPath);
                    }
                }
                libraries.add(library);
            } else {
                System.out.println("JsonParser: Skipping library " + name + " due to empty jarPath and dexPath");
            }
        }
        
        return libraries;
    }
}
