/*
* Copyright (C) 2024 NexusTeam.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
* Developed by NexusTeam.
* © 2024™
*/

package com.besome.blacklogics.development;

import android.view.View;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;

import java.util.*;
import android.util.Log;

import android.util.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
* JavaSourceCodeMaker holds the application source builder
* @author NexusTeam & SmartIndiaGaming
*/

public class JavaSourceCodeMaker {
	//  private String packageName;
	public String packageName;
	private String activityName = "MainActivity"; 
	private String parentActivity = "Activity";
	private String logic = "";
	private List<String> imports = new ArrayList<>();
	private List<Event> events = new ArrayList<>();
	public List<CustomView> views = new ArrayList<>();
	private String jsonFilePath;
	private boolean isDialogFragment = false;
	private boolean isFragment = false;
	private String layoutName = "main";
	
	private static final String ENCRYPTION_KEY = "yourEncryptionKeyHere"; // Must be 16, 24, or 32 characters long
	
	private Stack<JavaSourceCodeMakerState> undoStack = new Stack<>();
	private Stack<JavaSourceCodeMakerState> redoStack = new Stack<>();
	
	// Constructor that loads JSON data from file
	public JavaSourceCodeMaker(String jsonFilePath, String packageName) {
		this.jsonFilePath = jsonFilePath;
		this.packageName = packageName;
		loadFromJson(jsonFilePath);
	}
	
	// Method to set or update the package name
	// ✅ Set Package Name (Ensures Default Value)
	public void setPackageName(String packageName) {
		this.packageName = (packageName == null || packageName.trim().isEmpty()) ? "com.default.package" : packageName;
	}
	
	// ✅ Set Activity Name (Ensures Default Value)
	public void setActivityName(String activityName) {
		this.activityName = (activityName == null || activityName.trim().isEmpty()) ? "MainActivity" : activityName;
	}
	
	// ✅ Set Parent Activity (Ensures Default Value)
	public void setParentActivity(String parentActivity) {
		this.parentActivity = (parentActivity == null || parentActivity.trim().isEmpty()) ? "AppCompatActivity" : parentActivity;
	}
	
	// ✅ Set Fragment as DialogFragment
	public void isDialogFragment(boolean dialogFragment) {
		isDialogFragment = dialogFragment;
		if (dialogFragment) {
			isFragment = false; // Ensure it's not a regular fragment
			parentActivity = "DialogFragment";
		}
	}
	
	// ✅ Set Fragment as Regular Fragment
	public void isFragment(boolean fragment) {
		isFragment = fragment;
		if (fragment) {
			isDialogFragment = false; // Ensure it's not a dialog fragment
			parentActivity = "Fragment";
		}
	}
	
	// ✅ Set Layout Name (Ensures Default Value)
	public void setLayoutName(String layoutName) {
		this.layoutName = (layoutName == null || layoutName.trim().isEmpty()) ? "activity_main" : layoutName;
	}
	
	// ✅ Load from JSON (Base64 Decoding Support & Duplicate Handling)
	public void loadFromJson(String jsonFilePath) {
		try {
			File file = new File(jsonFilePath);
			if (!file.exists()) {
				Log.e("JSON Loader", "❌ Config file not found: " + jsonFilePath);
				return;
			}
			
			// ✅ Read file content
			StringBuilder jsonContent = new StringBuilder();
			try (FileReader reader = new FileReader(file)) {
				int i;
				while ((i = reader.read()) != -1) {
					jsonContent.append((char) i);
				}
			}
			
			// ✅ Base64 Decode JSON Content
			String decodedJson;
			try {
				decodedJson = new String(Base64.decode(jsonContent.toString(), Base64.DEFAULT));
			} catch (Exception e) {
				Log.w("JSON Loader", "⚠️ Base64 decoding failed, using raw JSON.");
				decodedJson = jsonContent.toString(); // ✅ Fallback to raw JSON
			}
			
			// ✅ Parse JSON
			JSONObject jsonObject = new JSONObject(decodedJson);
			
			// ✅ Set Default Values if Missing
			packageName = jsonObject.optString("packageName", "com.default.package");
			activityName = jsonObject.optString("activityName", "MainActivity");
			parentActivity = jsonObject.optString("parentActivity", "AppCompatActivity");
			
			// ✅ Load Imports (Remove Duplicates)
			JSONArray importsArray = jsonObject.optJSONArray("imports");
			imports.clear();
			if (importsArray != null) {
				Set<String> uniqueImports = new HashSet<>();
				for (int j = 0; j < importsArray.length(); j++) {
					uniqueImports.add(importsArray.getString(j));
				}
				imports.addAll(uniqueImports);
			}
			
			// ✅ Load Events (Remove Duplicates)
			JSONArray eventsArray = jsonObject.optJSONArray("events");
			events.clear();
			if (eventsArray != null) {
				Set<Event> uniqueEvents = new HashSet<>();
				for (int j = 0; j < eventsArray.length(); j++) {
					JSONObject eventObject = eventsArray.getJSONObject(j);
					uniqueEvents.add(new Event(
					eventObject.getString("name"),
					eventObject.getString("method"),
					eventObject.getString("code")
					));
				}
				events.addAll(uniqueEvents);
			}
			
			// ✅ Load Views (Remove Duplicates)
			JSONArray viewsArray = jsonObject.optJSONArray("views");
			views.clear();
			if (viewsArray != null) {
				Set<CustomView> uniqueViews = new HashSet<>();
				for (int j = 0; j < viewsArray.length(); j++) {
					JSONObject viewObject = viewsArray.getJSONObject(j);
					uniqueViews.add(new CustomView(
					viewObject.getString("id"),
					viewObject.getString("type")
					));
				}
				views.addAll(uniqueViews);
			}
			
			Log.d("JSON Loader", "✅ Config loaded successfully!");
			
		} catch (Exception e) {
			Log.e("JSON Loader", "❌ Error loading JSON: " + e.toString());
		}
	}
	
	public void saveToJson(String outputFilePath) {
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("packageName", packageName);
			jsonObject.put("activityName", activityName);
			jsonObject.put("parentActivity", parentActivity);
			
			// ✅ Save Imports
			jsonObject.put("imports", new JSONArray(imports));
			
			// ✅ Save Events
			JSONArray eventsArray = new JSONArray();
			for (Event event : events) {
				JSONObject eventObject = new JSONObject();
				eventObject.put("name", event.name);
				eventObject.put("method", event.method);
				eventObject.put("code", event.code);
				eventsArray.put(eventObject);
			}
			jsonObject.put("events", eventsArray);
			
			// ✅ Save Views
			JSONArray viewsArray = new JSONArray();
			for (CustomView view : views) {
				JSONObject viewObject = new JSONObject();
				viewObject.put("id", view.id);
				viewObject.put("type", view.type);
				viewsArray.put(viewObject);
			}
			jsonObject.put("views", viewsArray);
			
			// ✅ Convert JSON to String
			String jsonString = jsonObject.toString(4);
			
			// ✅ Base64 Encode if Needed
			jsonString = Base64.encodeToString(jsonString.getBytes(), Base64.DEFAULT);
			
			// ✅ Write to File
			try (FileWriter fileWriter = new FileWriter(outputFilePath)) {
				fileWriter.write(jsonString);
			}
			
			Log.d("JSON Saver", "✅ Config saved successfully!");
			
		} catch (Exception e) {
			Log.e("JSON Saver", "❌ Error saving JSON: " + e.toString());
		}
	}
	
	// ✅ Save to JSON (Base64 Encoding Toggle & Better Formatting) **For Base64**
	public void saveToJson(String outputFilePath, boolean useBase64) {
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("packageName", packageName);
			jsonObject.put("activityName", activityName);
			jsonObject.put("parentActivity", parentActivity);
			
			// ✅ Save Imports
			jsonObject.put("imports", new JSONArray(imports));
			
			// ✅ Save Events
			JSONArray eventsArray = new JSONArray();
			for (Event event : events) {
				JSONObject eventObject = new JSONObject();
				eventObject.put("name", event.name);
				eventObject.put("method", event.method);
				eventObject.put("code", event.code);
				eventsArray.put(eventObject);
			}
			jsonObject.put("events", eventsArray);
			
			// ✅ Save Views
			JSONArray viewsArray = new JSONArray();
			for (CustomView view : views) {
				JSONObject viewObject = new JSONObject();
				viewObject.put("id", view.id);
				viewObject.put("type", view.type);
				viewsArray.put(viewObject);
			}
			jsonObject.put("views", viewsArray);
			
			// ✅ Convert JSON to String
			String jsonString = jsonObject.toString(4);
			
			// ✅ Base64 Encode if Needed
			if (useBase64) {
				jsonString = Base64.encodeToString(jsonString.getBytes(), Base64.DEFAULT);
			}
			
			// ✅ Write to File
			try (FileWriter fileWriter = new FileWriter(outputFilePath)) {
				fileWriter.write(jsonString);
			}
			
			Log.d("JSON Saver", "✅ Config saved successfully!");
			
		} catch (Exception e) {
			Log.e("JSON Saver", "❌ Error saving JSON: " + e.toString());
		}
	}
	// Save the current state to the undo stack
	public void saveState() {
		undoStack.push(new JavaSourceCodeMakerState(new ArrayList<>(imports), new ArrayList<>(events), new ArrayList<>(views)));
		redoStack.clear();  // Clear the redo stack when a new action is made
	}
	
	// Undo the last change
	public void undo() {
		if (!undoStack.isEmpty()) {
			JavaSourceCodeMakerState previousState = undoStack.pop();
			redoStack.push(new JavaSourceCodeMakerState(new ArrayList<>(imports), new ArrayList<>(events), new ArrayList<>(views)));
			this.imports = previousState.imports;
			this.events = previousState.events;
			this.views = previousState.views;
		}
	}
	
	// Redo the last undone change
	public void redo() {
		if (!redoStack.isEmpty()) {
			JavaSourceCodeMakerState nextState = redoStack.pop();
			undoStack.push(new JavaSourceCodeMakerState(new ArrayList<>(imports), new ArrayList<>(events), new ArrayList<>(views)));
			this.imports = nextState.imports;
			this.events = nextState.events;
			this.views = nextState.views;
		}
	}
	
	// Add import to the list
	public void addImport(String importStatement) {
		saveState();
		if (!imports.contains(importStatement)) {
			imports.add(importStatement);
		}
	}
	
	public void removeImport(String importStatement) {
		saveState();
		imports.remove(importStatement);
	}
	
	// Add event to the list
	public void addEvent(String name, String method, String code) {
		saveState();
		events.add(new Event(name, method, code));
	}
	
	// Add view to the list
	public void addView(String id, String type) {
		saveState();
		views.add(new CustomView(id, type));
	}
	
	public void removeView(String viewStatement) {
		saveState();
		views.remove(viewStatement);
	}
	
	/** ✅ Feature 1: Clear All Imports */
	public void clearImports() {
		saveState(); // Save state for undo
		imports.clear();
		Log.d("JavaSourceCodeMaker", "All imports cleared successfully.");
	}
	
	/** ✅ Feature 2: Clear All Private IDs */
	public void clearPrivateIds() {
		saveState(); // Save state for undo
		for (CustomView view : views) {
			view.id = ""; // Reset IDs to empty
		}
		Log.d("JavaSourceCodeMaker", "All private IDs cleared successfully.");
	}
	
	/** ✅ Feature 3: Clear Everything (Minimal Reset) */
	public void clearEverything() {
		saveState(); // Save state for undo
		imports.clear();
		views.clear();
		events.clear();
		Log.d("JavaSourceCodeMaker", "All imports, views, and events cleared successfully.");
	}
	
	// Method to update the ID of a CustomView or Event
	public void updateId(String currentId, String newId) {
		saveState(); // Save the current state for undo/redo
		
		boolean updated = false; // Flag to check if any ID was updated
		
		// Update ID in views
		if (views != null) {
			for (CustomView view : views) {
				if (view != null && currentId.equals(view.id)) {
					view.id = newId;
					updated = true;
					//   System.out.println("Updated View ID: " + currentId + " to " + newId);
				}
			}
		} else {
			throw new IllegalStateException("Views list is null");
		}
		
		// Update ID in events
		if (events != null) {
			for (Event event : events) {
				if (event != null && currentId.equals(event.name)) {
					event.name = newId;
					updated = true;
					//    System.out.println("Updated Event ID: " + currentId + " to " + newId);
				}
			}
		} else {
			throw new IllegalStateException("Events list is null");
		}
		
		// Log if no matching ID was found
		if (!updated) {
			// System.out.println("ID " + currentId + " not found for update.");
		}
	}
	
	
	// Method to enable AndroidX imports
	public void enableAndroidXImports() {
		saveState();
		String[] androidxImports = {
			"androidx.appcompat.app.AppCompatActivity", // Base Activity
			"androidx.recyclerview.widget.RecyclerView", // RecyclerView
			"androidx.lifecycle.ViewModel", // ViewModel
			"androidx.lifecycle.LiveData", // LiveData
			"androidx.appcompat.widget.Toolbar", // Toolbar
			"androidx.constraintlayout.widget.ConstraintLayout", // Constraint Layout
			"androidx.cardview.widget.CardView", // CardView
			"androidx.core.widget.NestedScrollView", // Nested ScrollView
			"androidx.viewpager2.widget.ViewPager2", // ViewPager2
			"androidx.core.view.GravityCompat", // Drawer Gravity
			"androidx.appcompat.app.ActionBar", // ActionBar
			"androidx.core.view.ViewCompat", // View Compatibility
			"androidx.fragment.app.FragmentManager", // Fragment Manager
			"androidx.fragment.app.FragmentTransaction", // Fragment Transaction
			"androidx.navigation.NavController", // Navigation Controller
			"androidx.navigation.ui.AppBarConfiguration", // AppBar Configuration
			"androidx.navigation.ui.NavigationUI", // Navigation UI
			"androidx.drawerlayout.widget.DrawerLayout", // Drawer Layout
			"androidx.gridlayout.widget.GridLayout", // Grid Layout
			"androidx.core.widget.TextViewCompat" // TextView Compatibility
		};
		
		for (String importStatement : androidxImports) {
			addImport(importStatement);
		}
		//   System.out.println("AndroidX imports added successfully.");
	}
	
	
	// Method to enable Firebase imports
	public void enableFirebaseImports() {
		saveState();
		String[] firebaseImports = {
			"com.google.firebase.FirebaseApp",
			"com.google.firebase.auth.FirebaseAuth",
			"com.google.firebase.database.FirebaseDatabase",
			"com.google.firebase.storage.FirebaseStorage"
		};
		for (String importStatement : firebaseImports) {
			addImport(importStatement);
		}
	}
	// Method to enable essential Kotlin imports
	public void enableEssentialKotlinImports() {
		saveState();
		String[] essentialKotlinImports = {
			"kotlin.collections.List",
			"kotlin.collections.Map",
			"kotlin.math.PI",
			"kotlin.math.sqrt",
			"kotlin.concurrent.thread",
			"kotlin.random.Random",
			"kotlin.coroutines.CoroutineScope",
			"kotlin.coroutines.Dispatchers",
			"kotlinx.coroutines.launch"
		};
		for (String importStatement : essentialKotlinImports) {
			addImport(importStatement);
		}
	}
	
	// Method to enable essential Android KTX imports
	public void enableEssentialAndroidKtxImports() {
		saveState();
		String[] essentialAndroidKtxImports = {
			"androidx.core.app.ActivityCompat",
			"androidx.core.content.ContextCompat",
			"androidx.appcompat.widget.Toolbar",
			"androidx.lifecycle.LiveData",
			"androidx.lifecycle.ViewModel",
			"androidx.recyclerview.widget.LinearLayoutManager",
			"androidx.navigation.fragment.NavHostFragment",
			"androidx.navigation.ui.NavigationUI"
		};
		for (String importStatement : essentialAndroidKtxImports) {
			addImport(importStatement);
		}
	}
	
	// Method to enable essential Google and Firebase imports
	public void enableEssentialGoogleAndFirebaseImports() {
		saveState();
		String[] essentialGoogleFirebaseImports = {
			"com.google.firebase.auth.FirebaseAuth",
			"com.google.firebase.firestore.FirebaseFirestore",
			"com.google.firebase.messaging.FirebaseMessaging",
			"com.google.firebase.database.DatabaseReference",
			"com.google.firebase.storage.FirebaseStorage"
		};
		for (String importStatement : essentialGoogleFirebaseImports) {
			addImport(importStatement);
		}
	}
	
	// Method to enable essential Retrofit imports
	public void enableEssentialRetrofitImports() {
		saveState();
		String[] essentialRetrofitImports = {
			"retrofit2.Retrofit",
			"retrofit2.converter.gson.GsonConverterFactory",
			"retrofit2.Call",
			"retrofit2.http.GET"
		};
		for (String importStatement : essentialRetrofitImports) {
			addImport(importStatement);
		}
	}
	
	// Method to enable essential Room database imports
	public void enableEssentialRoomDatabaseImports() {
		saveState();
		String[] essentialRoomDatabaseImports = {
			"androidx.room.Room",
			"androidx.room.Database",
			"androidx.room.Dao",
			"androidx.room.Entity",
			"androidx.room.PrimaryKey",
			"androidx.room.Query"
		};
		for (String importStatement : essentialRoomDatabaseImports) {
			addImport(importStatement);
		}
	}
	
	// Method to enable essential Jetpack Compose imports
	public void enableEssentialJetpackComposeImports() {
		saveState();
		String[] essentialComposeImports = {
			"androidx.compose.foundation.layout.Column",
			"androidx.compose.material3.Button",
			"androidx.compose.material3.Text",
			"androidx.compose.ui.tooling.preview.Preview",
			"androidx.compose.runtime.Composable"
		};
		for (String importStatement : essentialComposeImports) {
			addImport(importStatement);
		}
	}
	
	// Method to enable essential Dagger/Hilt imports
	public void enableEssentialDaggerHiltImports() {
		saveState();
		String[] essentialDaggerHiltImports = {
			"dagger.hilt.android.HiltAndroidApp",
			"dagger.hilt.android.AndroidEntryPoint",
			"dagger.hilt.android.components.ActivityComponent"
		};
		for (String importStatement : essentialDaggerHiltImports) {
			addImport(importStatement);
		}
	}
	// Method to enable Google Maps imports
	public void enableGoogleMapsImports() {
		saveState();
		String[] googleMapsImports = {
			"com.google.android.gms.maps.GoogleMap",
			"com.google.android.gms.maps.MapFragment",
			"com.google.android.gms.maps.OnMapReadyCallback",
			"com.google.android.gms.maps.SupportMapFragment"
		};
		for (String importStatement : googleMapsImports) {
			addImport(importStatement);
		}
	}
	
	// Method to enable Google Ads imports
	public void enableGoogleAdsImports() {
		saveState();
		String[] googleAdsImports = {
			"com.google.android.gms.ads.AdRequest",
			"com.google.android.gms.ads.AdView",
			"com.google.android.gms.ads.InterstitialAd",
			"com.google.android.gms.ads.MobileAds"
		};
		for (String importStatement : googleAdsImports) {
			addImport(importStatement);
		}
	}
	
	// Method to enable Supabase Database imports
	public void enableSupabaseImports() {
		saveState();
		String[] supabaseImports = {
			"io.supabase.SupabaseClient",
			"io.supabase.postgrest.PostgrestClient",
			"io.supabase.gotrue.GotrueClient",
			"io.supabase.realtime.RealtimeClient"
		};
		for (String importStatement : supabaseImports) {
			addImport(importStatement);
		}
	}
	
	// Method to enable Retrofit imports
	public void enableRetrofitImports() {
		saveState();
		String[] retrofitImports = {
			"retrofit2.Retrofit",
			"retrofit2.converter.gson.GsonConverterFactory",
			"retrofit2.Call",
			"retrofit2.http.GET"
		};
		for (String importStatement : retrofitImports) {
			addImport(importStatement);
		}
	}
	
	// Method to enable Room Database imports
	public void enableRoomDatabaseImports() {
		saveState();
		String[] roomImports = {
			"androidx.room.Room",
			"androidx.room.Database",
			"androidx.room.Dao",
			"androidx.room.Entity"
		};
		for (String importStatement : roomImports) {
			addImport(importStatement);
		}
	}
	
	// Method to enable Glide imports for image loading
	public void enableGlideImports() {
		saveState();
		String[] glideImports = {
			"com.bumptech.glide.Glide",
			"com.bumptech.glide.request.RequestOptions",
			"com.bumptech.glide.load.engine.DiskCacheStrategy"
		};
		for (String importStatement : glideImports) {
			addImport(importStatement);
		}
	}
	
	// mathod to add source code in your generating class 
	public void setLogic(String logic) {
		this.logic = logic;
	}
	// @Override
	public String generateJavaClassCode() {
		StringBuilder javaCode = new StringBuilder();
		
		// Package declaration
		javaCode.append("package ").append(packageName).append(";\n\n");
		
		// Add necessary imports
		for (String importLine : imports) {
			javaCode.append("import ").append(importLine).append(";\n");
		}
		
		javaCode.append("\n");
		
		// Ensure required imports based on type
		if (isDialogFragment) {
			javaCode.append("import android.app.Dialog;\n")
			.append("import android.os.Bundle;\n")
			.append("import android.view.LayoutInflater;\n")
			.append("import android.view.View;\n")
			.append("import android.view.ViewGroup;\n")
			.append("import android.app.DialogFragment;\n\n");
		} else if (isFragment) {
			javaCode.append("import android.os.Bundle;\n")
			.append("import android.view.LayoutInflater;\n")
			.append("import android.view.View;\n")
			.append("import android.view.ViewGroup;\n")
			.append("import android.app.Fragment;\n\n");
		} else {
			javaCode.append("import android.os.Bundle;\n")
			.append("import android.app.Activity;\n\n");
		}
		
		// Determine the parent class
		String parentClass = isDialogFragment ? "DialogFragment" : (isFragment ? "Fragment" : parentActivity);
		
		// Class declaration
		javaCode.append("public class ").append(activityName).append(" extends ").append(parentClass).append(" {\n\n");
		
		// Add the views
		for (CustomView view : views) {
			javaCode.append("    private ").append(view.type).append(" ").append(view.id).append(";\n");
		}
		
		javaCode.append("\n");
		
		// Handle DialogFragment separately
		if (isDialogFragment) {
			javaCode.append("    @Override\n")
			.append("    public Dialog onCreateDialog(Bundle savedInstanceState) {\n")
			.append("        Dialog dialog = super.onCreateDialog(savedInstanceState);\n")
			.append("        dialog.setContentView(R.layout.").append(layoutName).append(");\n")
			.append("        initializeViews(dialog.getWindow().getDecorView());\n")
			.append("        initializeLogic();\n")
			.append("        return dialog;\n")
			.append("    }\n\n");
		}  
		// Handle Fragment
		else if (isFragment) {
			javaCode.append("    @Override\n")
			.append("    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {\n")
			.append("        View view = inflater.inflate(R.layout.").append(layoutName).append(", container, false);\n")
			.append("        initializeViews(view);\n")
			.append("        initializeLogic();\n")
			.append("        return view;\n")
			.append("    }\n\n");
		}  
		// Handle Activity
		else {
			javaCode.append("    @Override\n")
			.append("    protected void onCreate(Bundle savedInstanceState) {\n")
			.append("        super.onCreate(savedInstanceState);\n")
			.append("        setContentView(R.layout.").append(layoutName).append(");\n")
			.append("        initializeViews(getWindow().getDecorView());\n")
			.append("        initializeLogic();\n")
			.append("    }\n\n");
		}
		
		// Method to initialize views dynamically
		javaCode.append("    private void initializeViews(View root) {\n");
		
		for (CustomView view : views) {
			javaCode.append("        ").append(view.id).append(" = root.findViewById(R.id.").append(view.id).append(");\n");
		}
		
		javaCode.append("    }\n\n");
		
		// Stub for additional logic
		javaCode.append("    private void initializeLogic() {\n")
		.append("        ").append(logic).append("\n")
		.append("    }\n");
		
		// Close class
		javaCode.append("}\n");
		
		return javaCode.toString();
	}
	
	
	public String generateKotlinClassCode() {
		StringBuilder kotlinCode = new StringBuilder();
		
		// Package declaration
		kotlinCode.append("package ").append(packageName).append("\n\n");
		
		// Adding import statements
		for (String importLine : imports) {
			kotlinCode.append("import ").append(importLine).append("\n");
		}
		
		kotlinCode.append("\n");
		
		// Determine the parent class
		String parentClass = isDialogFragment ? "DialogFragment" : (isFragment ? "Fragment" : parentActivity);
		
		// Class declaration
		kotlinCode.append("class ").append(activityName).append(" : ").append(parentClass).append("() {\n\n");
		
		// View declarations (using Kotlin syntax)
		for (CustomView view : views) {
			kotlinCode.append("    private lateinit var ").append(view.id).append(": ").append(view.type).append("\n");
		}
		
		kotlinCode.append("\n");
		
		// Handle DialogFragment
		if (isDialogFragment) {
			kotlinCode.append("    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {\n")
			.append("        val dialog = super.onCreateDialog(savedInstanceState)\n")
			.append("        dialog.setContentView(R.layout.").append(layoutName).append(")\n")
			.append("        initializeViews(dialog.window?.decorView)\n")
			.append("        initializeLogic()\n")
			.append("        return dialog\n")
			.append("    }\n\n");
		}  
		// Handle Fragment
		else if (isFragment) {
			kotlinCode.append("    override fun onCreateView(\n")
			.append("        inflater: LayoutInflater,\n")
			.append("        container: ViewGroup?,\n")
			.append("        savedInstanceState: Bundle?\n")
			.append("    ): View? {\n")
			.append("        val view = inflater.inflate(R.layout.").append(layoutName).append(", container, false)\n")
			.append("        initializeViews(view)\n")
			.append("        initializeLogic()\n")
			.append("        return view\n")
			.append("    }\n\n");
		}  
		// Handle Activity
		else {
			kotlinCode.append("    override fun onCreate(savedInstanceState: Bundle?) {\n")
			.append("        super.onCreate(savedInstanceState)\n")
			.append("        setContentView(R.layout.").append(layoutName).append(")\n")
			.append("        initializeViews(window.decorView)\n")
			.append("        initializeLogic()\n")
			.append("    }\n\n");
		}
		
		// Method to initialize views dynamically
		kotlinCode.append("    private fun initializeViews(root: View?) {\n");
		
		for (CustomView view : views) {
			kotlinCode.append("        ").append(view.id).append(" = root?.findViewById(R.id.").append(view.id).append(")!!\n");
		}
		
		kotlinCode.append("    }\n\n");
		
		// Additional logic
		kotlinCode.append("    private fun initializeLogic() {\n")
		.append("        ").append(logic).append("\n")
		.append("    }\n");
		
		// Close class
		kotlinCode.append("}\n");
		
		return kotlinCode.toString();
	}
	
	
	public void saveGeneratedCode(String outputFilePath) {
		try (FileWriter fileWriter = new FileWriter(outputFilePath)) {
			fileWriter.write(generateJavaClassCode());
			//  System.out.println("Generated Java code saved to " + outputFilePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	* Create a new activity by automatically adding imports from the existing configuration.
	* @param activityName The name of the new activity to create.
	* @param parentActivity The parent class of the activity (e.g., "AppCompatActivity").
	*/
	public void createActivity(String activityName, String parentActivity) {
		saveState();
		
		// Remove ".java" if present in activityName
		activityName = activityName.replace(".java", "");
		
		this.activityName = activityName;
		this.parentActivity = parentActivity;
		
		List<String> currentImports = new ArrayList<>(imports);
		imports.clear();
		for (String importStatement : currentImports) {
			addImport(importStatement);
		}
		
		// ✅ Automatically save activity data to JSON
		saveActivityToJson(activityName, parentActivity);
	}
	
	/**
	* Saves activity information to a JSON file.
	*/
	private void saveActivityToJson(String activityName, String parentActivity) {
		try {
			File file = new File(jsonFilePath);
			JSONObject jsonObject;
			
			if (file.exists()) {
				try (FileReader reader = new FileReader(file)) {
					StringBuilder content = new StringBuilder();
					int i;
					while ((i = reader.read()) != -1) {
						content.append((char) i);
					}
					jsonObject = new JSONObject(content.toString());
				}
			} else {
				jsonObject = new JSONObject();
			}
			
			// Ensure "activities" array exists
			JSONArray activitiesArray = jsonObject.optJSONArray("activities");
			if (activitiesArray == null) {
				activitiesArray = new JSONArray();
			}
			
			// Avoid duplicate activity entries
			for (int i = 0; i < activitiesArray.length(); i++) {
				JSONObject existingActivity = activitiesArray.getJSONObject(i);
				if (existingActivity.getString("name").equals(activityName)) {
					return; // Activity already exists
				}
			}
			
			// Create new activity object
			JSONObject newActivity = new JSONObject();
			newActivity.put("name", activityName);
			newActivity.put("parentActivity", parentActivity);
			
			// Add the new activity to the array
			activitiesArray.put(newActivity);
			jsonObject.put("activities", activitiesArray);
			
			// Save JSON back to file
			try (FileWriter writer = new FileWriter(file)) {
				writer.write(jsonObject.toString(4));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// ✅ Add or Update Activity in JSON
	public void saveActivityToJson(String jsonFilePath, String activityName, String parentActivity, boolean isPrivate) {
		try {
			File file = new File(jsonFilePath);
			JSONObject jsonObject;
			
			// ✅ Load Existing JSON or Create New
			if (file.exists()) {
				try (FileReader reader = new FileReader(file)) {
					StringBuilder content = new StringBuilder();
					int i;
					while ((i = reader.read()) != -1) {
						content.append((char) i);
					}
					jsonObject = new JSONObject(content.toString());
				}
			} else {
				jsonObject = new JSONObject();
			}
			
			// ✅ Ensure "activities" array exists
			JSONArray activitiesArray = jsonObject.optJSONArray("activities");
			if (activitiesArray == null) {
				activitiesArray = new JSONArray();
			}
			
			// ✅ Remove ".java" if present in activity name
			activityName = (activityName == null || activityName.trim().isEmpty()) ? "MainActivity" : activityName.replace(".java", "");
			
			// ✅ Check if Activity Already Exists (Prevent Duplicates)
			for (int i = 0; i < activitiesArray.length(); i++) {
				JSONObject existingActivity = activitiesArray.getJSONObject(i);
				if (existingActivity.getString("name").equals(activityName)) {
					return; // ⚠️ Already Exists, No Need to Add Again
				}
			}
			
			// ✅ Create New Activity Object
			JSONObject newActivity = new JSONObject();
			newActivity.put("name", activityName);
			newActivity.put("parentActivity", (parentActivity == null || parentActivity.trim().isEmpty()) ? "AppCompatActivity" : parentActivity);
			newActivity.put("isPrivate", isPrivate); // ✅ Private Activity Support
			
			// ✅ Add to Activities Array
			activitiesArray.put(newActivity);
			jsonObject.put("activities", activitiesArray);
			
			// ✅ Save JSON Back to File
			try (FileWriter writer = new FileWriter(file)) {
				writer.write(jsonObject.toString(4));
			}
			
			Log.d("ActivityJSON", "✅ Activity " + activityName + " saved successfully!");
			
		} catch (Exception e) {
			Log.e("ActivityJSON", "❌ Error saving activity: " + e.toString());
		}
	}
	
	public JSONObject getPrivateIdsImportsToJson(JSONArray jsonArray) {
		JSONObject result = new JSONObject();
		HashSet<String> importsSet = new HashSet<>();
		
		try {
			JSONArray privateFields = new JSONArray();
			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject component = jsonArray.getJSONObject(i);
				
				// Decode Base64 encoded values
				String name = new String(Base64.decode(component.getString("name"), Base64.NO_WRAP));
				String cusType = new String(Base64.decode(component.getString("cusType"), Base64.NO_WRAP));
				String activity = component.getString("activity");
				String importStatement = component.getString("import");
				
				// Only process components belonging to this activity
				if (activity.equals(this.activityName)) {
					JSONObject fieldObject = new JSONObject();
					fieldObject.put("name", name);
					fieldObject.put("type", cusType);
					
					privateFields.put(fieldObject);
					importsSet.add(importStatement);
				}
			}
			
			// Add private fields to result JSON
			result.put("privateFields", privateFields);
			
			// Add unique imports as a JSON array
			JSONArray importsArray = new JSONArray(importsSet);
			result.put("imports", importsArray);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private String getViewType(String type) {
		switch (type) {
			case "LinearLayout": return "LinearLayout";
			case "RelativeLayout": return "RelativeLayout";
			case "TextView": return "TextView";
			case "Button": return "Button";
			case "EditText": return "EditText";
			case "ImageView": return "ImageView";
			case "RecyclerView": return "RecyclerView";
			case "ListView": return "ListView";
			default: return "View";
		}
	}
	
	public void extractAndAddViews(String jsonString) {
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			
			// Find the correct activity JSON
			String activityKey = null;
			for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
				String key = it.next();
				if (key.equals(this.activityName)) {
					activityKey = key;
					break;
				}
			}
			
			// If activity not found, return
			if (activityKey == null) {
				Log.w("Activity Extractor", "❌ Activity not found: " + this.activityName);
				return;
			}
			
			// Extract the JSON for the current activity
			JSONObject activityJson = jsonObject.getJSONObject(activityKey);
			
			// Extract IDs from "tagAttributes"
			JSONArray tagAttributesArray = activityJson.optJSONArray("tagAttributes");
			if (tagAttributesArray != null) {
				for (int i = 0; i < tagAttributesArray.length(); i++) {
					JSONObject tagAttribute = tagAttributesArray.getJSONObject(i);
					if (tagAttribute.has("key") && tagAttribute.has("value")) {
						JSONObject valueObject = tagAttribute.getJSONObject("value");
						if ("android:id".equals(tagAttribute.getString("key")) &&
						valueObject.has("data") && valueObject.getJSONObject("data").has("value")) {
							
							// Extract View ID and type
							String viewId = valueObject.getJSONObject("data").getString("value");
							String viewType = getViewType(activityJson.optString("type", "View"));
							
							// Automatically add view using existing addView method
							addView(viewId, viewType);
							Log.d("View Added", "✅ " + viewType + " " + viewId);
						}
					}
				}
			}
			
		} catch (JSONException e) {
			Log.e("JSON Parser", "❌ Error parsing JSON: " + e.toString());
		}
	}
	public void saveAsMultipleActivityInJson(String jsonFilePath) {
		try {
			File file = new File(jsonFilePath);
			JSONObject jsonObject;
			
			// ✅ Load existing JSON or create new
			if (file.exists()) {
				try (FileReader reader = new FileReader(file)) {
					StringBuilder content = new StringBuilder();
					int i;
					while ((i = reader.read()) != -1) {
						content.append((char) i);
					}
					jsonObject = new JSONObject(content.toString());
				}
			} else {
				jsonObject = new JSONObject();
			}
			
			// ✅ Ensure "activities" section exists
			JSONObject activitiesJson = jsonObject.optJSONObject("activities");
			if (activitiesJson == null) {
				activitiesJson = new JSONObject();
			}
			
			// ✅ Save current activity details
			JSONObject activityJson = new JSONObject();
			activityJson.put("packageName", packageName);
			activityJson.put("parentActivity", parentActivity);
			activityJson.put("layoutName", layoutName);
			
			// Save imports
			activityJson.put("imports", new JSONArray(imports));
			
			// Save views
			JSONArray viewsArray = new JSONArray();
			for (CustomView view : views) {
				JSONObject viewJson = new JSONObject();
				viewJson.put("id", view.id);
				viewJson.put("type", view.type);
				viewsArray.put(viewJson);
			}
			activityJson.put("views", viewsArray);
			
			// ✅ Add activity to "activities" section
			activitiesJson.put(activityName, activityJson);
			jsonObject.put("activities", activitiesJson);
			
			// ✅ Write to file
			try (FileWriter writer = new FileWriter(file)) {
				writer.write(jsonObject.toString(4));
			}
			
			Log.d("JSON Saver", "✅ Activity " + activityName + " saved successfully!");
			
		} catch (Exception e) {
			Log.e("JSON Saver", "❌ Error saving activity: " + e.toString());
		}
	}
	public void loadMultipleActivitiesFromJson(String jsonFilePath) {
		try {
			File file = new File(jsonFilePath);
			if (!file.exists()) {
				Log.e("JSON Loader", "❌ Config file not found: " + jsonFilePath);
				return;
			}
			
			// ✅ Read JSON file
			StringBuilder jsonContent = new StringBuilder();
			try (FileReader reader = new FileReader(file)) {
				int i;
				while ((i = reader.read()) != -1) {
					jsonContent.append((char) i);
				}
			}
			
			JSONObject jsonObject = new JSONObject(jsonContent.toString());
			JSONObject activitiesJson = jsonObject.optJSONObject("activities");
			
			if (activitiesJson == null) {
				Log.w("JSON Loader", "⚠️ No activities found in JSON.");
				return;
			}
			
			// ✅ Load all activities
			for (Iterator<String> it = activitiesJson.keys(); it.hasNext(); ) {
				String activityName = it.next();
				JSONObject activityJson = activitiesJson.getJSONObject(activityName);
				
				// Load activity data
				String packageName = activityJson.optString("packageName", "com.default.package");
				String parentActivity = activityJson.optString("parentActivity", "AppCompatActivity");
				String layoutName = activityJson.optString("layoutName", "activity_main");
				
				JSONArray importsArray = activityJson.optJSONArray("imports");
				List<String> imports = new ArrayList<>();
				if (importsArray != null) {
					for (int j = 0; j < importsArray.length(); j++) {
						imports.add(importsArray.getString(j));
					}
				}
				
				JSONArray viewsArray = activityJson.optJSONArray("views");
				List<CustomView> views = new ArrayList<>();
				if (viewsArray != null) {
					for (int j = 0; j < viewsArray.length(); j++) {
						JSONObject viewJson = viewsArray.getJSONObject(j);
						views.add(new CustomView(viewJson.getString("id"), viewJson.getString("type")));
					}
				}
				
				// ✅ Create and store JavaSourceCodeMaker instance for each activity
				JavaSourceCodeMaker newActivity = new JavaSourceCodeMaker(jsonFilePath, packageName);
				newActivity.setActivityName(activityName);
				newActivity.setParentActivity(parentActivity);
				newActivity.setLayoutName(layoutName);
				newActivity.imports = imports;
				newActivity.views = views;
				
				Log.d("JSON Loader", "✅ Loaded Activity: " + activityName);
			}
			
		} catch (Exception e) {
			Log.e("JSON Loader", "❌ Error loading JSON: " + e.toString());
		}
	}
	public void generateAllActivitiesCode(String outputDir) {
		try {
			File jsonFile = new File(jsonFilePath);
			if (!jsonFile.exists()) {
				Log.e("Code Generator", "❌ JSON file not found!");
				return;
			}
			
			// ✅ Read JSON file
			StringBuilder jsonContent = new StringBuilder();
			try (FileReader reader = new FileReader(jsonFile)) {
				int i;
				while ((i = reader.read()) != -1) {
					jsonContent.append((char) i);
				}
			}
			
			JSONObject jsonObject = new JSONObject(jsonContent.toString());
			JSONObject activitiesJson = jsonObject.optJSONObject("activities");
			
			if (activitiesJson == null) {
				Log.w("Code Generator", "⚠️ No activities found in JSON.");
				return;
			}
			
			// ✅ Dynamic instance creation with a counter
			int activityCounter = 1;
			Map<String, JavaSourceCodeMaker> activityInstances = new HashMap<>();
			
			// ✅ Generate and save Java code for each activity
			for (Iterator<String> it = activitiesJson.keys(); it.hasNext(); ) {
				String activityName = it.next();
				JSONObject activityJson = activitiesJson.getJSONObject(activityName);
				
				String packageName = activityJson.optString("packageName", "com.default.package");
				String parentActivity = activityJson.optString("parentActivity", "AppCompatActivity");
				String layoutName = activityJson.optString("layoutName", "activity_main");
				
				// ✅ Dynamically create unique instance names (newActivity1, newActivity2, etc.)
				JavaSourceCodeMaker newActivity = new JavaSourceCodeMaker(jsonFilePath, packageName);
				newActivity.setActivityName(activityName);
				newActivity.setParentActivity(parentActivity);
				newActivity.setLayoutName(layoutName);
				
				// Store dynamically created instance
				activityInstances.put("newActivity" + activityCounter, newActivity);
				activityCounter++;
				
				// ✅ Generate Java class code
				String generatedCode = newActivity.generateJavaClassCode();
				
				// ✅ Save to file
				File outputFile = new File(outputDir, activityName + ".java");
				try (FileWriter fileWriter = new FileWriter(outputFile)) {
					fileWriter.write(generatedCode);
				}
				
				Log.d("Code Generator", "✅ Java Class Generated: " + activityName + ".java");
			}
			
			Log.d("Code Generator", "✅ Total Activities Created: " + activityInstances.size());
			
		} catch (Exception e) {
			Log.e("Code Generator", "❌ Error generating Java classes: " + e.toString());
		}
	}
	
	// Event class
	public static class Event {
		public String name;
		public String method;
		public String code;
		
		public Event(String name, String method, String code) {
			this.name = name;
			this.method = method;
			this.code = code;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null || getClass() != obj.getClass()) return false;
			Event event = (Event) obj;
			return name.equals(event.name) &&
			method.equals(event.method) &&
			code.equals(event.code);
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(name, method, code);
		}
	}
	// CustomView class to replace View
	public static class CustomView {
		public String id;
		public String type;
		
		public CustomView(String id, String type) {
			this.id = id;
			this.type = type;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null || getClass() != obj.getClass()) return false;
			CustomView view = (CustomView) obj;
			return id.equals(view.id) && type.equals(view.type);
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(id, type);
		}
	}
	// Class to store state for undo/redo
	public static class JavaSourceCodeMakerState {
		List<String> imports;
		List<Event> events;
		List<CustomView> views;
		
		JavaSourceCodeMakerState(List<String> imports, List<Event> events, List<CustomView> views) {
			this.imports = imports;
			this.events = events;
			this.views = views;
		}
	}
}
