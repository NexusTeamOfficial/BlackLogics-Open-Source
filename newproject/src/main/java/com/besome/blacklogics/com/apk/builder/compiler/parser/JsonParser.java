/**
* A utility class for parsing JSON data into Library objects.
* <p>
* This class provides methods to parse JSON strings containing library information
* and convert them into a list of Library objects.
* </p>
*
* @author NexusTeam
* @version 1.1
* @since 1.0
* @see Library
*/
/*
* This is free and unencumbered software released into the public domain.
* 
* Anyone is free to copy, modify, publish, use, compile, sell, or
* distribute this software, either in source code form or as a compiled
* binary, for any purpose, commercial or non-commercial, and by any
* means.
* 
* In jurisdictions that recognize copyright laws, the author or authors
* of this software dedicate any and all copyright interest in the
* software to the public domain. We make this dedication for the benefit
* of the public at large and to the detriment of our heirs and
* successors. We intend this dedication to be an overt act of
* relinquishment in perpetuity of all present and future rights to this
* software under copyright law.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
* EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
* MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
* IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
* OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
* ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
* OTHER DEALINGS IN THE SOFTWARE.
* 
* For more information, please refer to <http://unlicense.org>
* 
* Note: When using this class, please credit @NexusTeam or @SmartIndiaGaming
*/
package com.besome.blacklogics.parser;

import com.apk.builder.model.Library;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {
	
	/**
	* Parses a JSON string containing library information into a list of Library objects.
	* <p>
	* The JSON should contain information about jarPath, dexPath, name, manifestPath,
	* and resPath for each library.
	* </p>
	*
	* @param jsonString The JSON string to parse
	* @return List of Library objects parsed from the JSON
	* @throws JSONException If there is an error parsing the JSON string
	*/
	public static List<Library> parseLibrariesFromJson(String jsonString) throws JSONException {
		List<Library> libraries = new ArrayList<>();
		
		if (jsonString == null || jsonString.isEmpty()) {
			System.out.println("JsonParser: JSON string is null or empty");
			return libraries;
		}
		
		JSONArray jsonArray = new JSONArray(jsonString);
		
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject libObject = jsonArray.getJSONObject(i);
			
			// Extract paths from JSON
			String jarPath = libObject.optString("jarPath", "");
			String dexPath = libObject.optString("dexPath", "");
			String name = libObject.optString("name", "");
			String manifestPath = libObject.optString("manifestPath", "");
			String resPath = libObject.optString("resPath", "");
			
			// Determine library path (parent directory of jar or dex)
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
