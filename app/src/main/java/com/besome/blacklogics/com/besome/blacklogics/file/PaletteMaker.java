package com.besome.blacklogics.file;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PaletteMaker {
	private static final String TAG = "PaletteMaker";
	private final File paletteFile;
	
	public PaletteMaker(String filePath) {
		this.paletteFile = new File(filePath);
		ensureFileExists();
	}
	
	private void ensureFileExists() {
		try {
			if (!paletteFile.exists()) {
				paletteFile.getParentFile().mkdirs();
				paletteFile.createNewFile();
				writeJsonArray(new JSONArray()); // Empty JSON array
			}
		} catch (IOException e) {
			Log.e(TAG, "Error creating file: " + e.getMessage());
		}
	}
	
	private JSONArray readJsonArray() {
		try (BufferedReader reader = new BufferedReader(new FileReader(paletteFile))) {
			StringBuilder builder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			return new JSONArray(builder.toString().isEmpty() ? "[]" : builder.toString());
		} catch (IOException | JSONException e) {
			Log.e(TAG, "Error reading JSON: " + e.getMessage());
			return new JSONArray();
		}
	}
	
	private void writeJsonArray(JSONArray array) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(paletteFile))) {
			writer.write(array.toString(4)); // Pretty print
		} catch (IOException | JSONException e) {
			Log.e(TAG, "Error writing JSON: " + e.getMessage());
		}
	}
	
	
	public void addPalette(String name, String color) {
		JSONArray array = readJsonArray();
		JSONObject newPalette = new JSONObject();
		try {
			newPalette.put("name", name);
			newPalette.put("color", color);
			array.put(newPalette);
			writeJsonArray(array);
		} catch (JSONException e) {
			Log.e(TAG, "Error adding palette: " + e.getMessage());
		}
	}
	
	public void updatePalette(String name, String newColor) {
		JSONArray array = readJsonArray();
		boolean updated = false;
		
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject obj = array.getJSONObject(i);
				if (obj.getString("name").equals(name)) {
					obj.put("color", newColor);
					updated = true;
					break;
				}
			} catch (JSONException e) {
				Log.e(TAG, "Error updating palette: " + e.getMessage());
			}
		}
		
		if (updated) {
			writeJsonArray(array);
		}
	}
	
	public List<String> getPaletteNames() {
		List<String> names = new ArrayList<>();
		JSONArray array = readJsonArray();
		for (int i = 0; i < array.length(); i++) {
			try {
				names.add(array.getJSONObject(i).getString("name"));
			} catch (JSONException e) {
				Log.e(TAG, "Error getting name: " + e.getMessage());
			}
		}
		return names;
	}
	
	public String getColorByName(String name) {
		JSONArray array = readJsonArray();
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject obj = array.getJSONObject(i);
				if (obj.getString("name").equals(name)) {
					return obj.getString("color");
				}
			} catch (JSONException e) {
				Log.e(TAG, "Error getting color: " + e.getMessage());
			}
		}
		return null;
	}
}

/**
USING 
PaletteMaker maker = new PaletteMaker("/storage/emulated/0/.blacklogics/resources/block/My Block/palette.json");
maker.addPalette("test", "#E91E63");
maker.updatePalette("test", "#FF4081");
*/
