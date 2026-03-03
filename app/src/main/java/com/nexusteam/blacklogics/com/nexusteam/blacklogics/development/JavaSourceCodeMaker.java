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
    

    public JavaSourceCodeMaker(String jsonFilePath, String packageName) {
        this.jsonFilePath = jsonFilePath;
        this.packageName = packageName;
        loadFromJson(jsonFilePath);
    }
    


    public void setPackageName(String packageName) {
        this.packageName = (packageName == null || packageName.trim().isEmpty()) ? "com.default.package" : packageName;
    }
    

    public void setActivityName(String activityName) {
        this.activityName = (activityName == null || activityName.trim().isEmpty()) ? "MainActivity" : activityName;
    }
    

    public void setParentActivity(String parentActivity) {
        this.parentActivity = (parentActivity == null || parentActivity.trim().isEmpty()) ? "AppCompatActivity" : parentActivity;
    }
    

    public void isDialogFragment(boolean dialogFragment) {
        isDialogFragment = dialogFragment;
        if (dialogFragment) {
            isFragment = false; // Ensure it's not a regular fragment
            parentActivity = "DialogFragment";
        }
    }
    

    public void isFragment(boolean fragment) {
        isFragment = fragment;
        if (fragment) {
            isDialogFragment = false; // Ensure it's not a dialog fragment
            parentActivity = "Fragment";
        }
    }
    

    public void setLayoutName(String layoutName) {
        this.layoutName = (layoutName == null || layoutName.trim().isEmpty()) ? "activity_main" : layoutName;
    }
    

    public void loadFromJson(String jsonFilePath) {
        try {
            File file = new File(jsonFilePath);
            if (!file.exists()) {
                Log.e("JSON Loader", "❌ Config file not found: " + jsonFilePath);
                return;
            }
            

            StringBuilder jsonContent = new StringBuilder();
            try (FileReader reader = new FileReader(file)) {
                int i;
                while ((i = reader.read()) != -1) {
                    jsonContent.append((char) i);
                }
            }
            

            String decodedJson;
            try {
                decodedJson = new String(Base64.decode(jsonContent.toString(), Base64.DEFAULT));
            } catch (Exception e) {
                Log.w("JSON Loader", "⚠️ Base64 decoding failed, using raw JSON.");
                decodedJson = jsonContent.toString(); // ✅ Fallback to raw JSON
            }
            

            JSONObject jsonObject = new JSONObject(decodedJson);
            

            packageName = jsonObject.optString("packageName", "com.default.package");
            activityName = jsonObject.optString("activityName", "MainActivity");
            parentActivity = jsonObject.optString("parentActivity", "AppCompatActivity");
            

            JSONArray importsArray = jsonObject.optJSONArray("imports");
            imports.clear();
            if (importsArray != null) {
                Set<String> uniqueImports = new HashSet<>();
                for (int j = 0; j < importsArray.length(); j++) {
                    uniqueImports.add(importsArray.getString(j));
                }
                imports.addAll(uniqueImports);
            }
            

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
            

            jsonObject.put("imports", new JSONArray(imports));
            

            JSONArray eventsArray = new JSONArray();
            for (Event event : events) {
                JSONObject eventObject = new JSONObject();
                eventObject.put("name", event.name);
                eventObject.put("method", event.method);
                eventObject.put("code", event.code);
                eventsArray.put(eventObject);
            }
            jsonObject.put("events", eventsArray);
            

            JSONArray viewsArray = new JSONArray();
            for (CustomView view : views) {
                JSONObject viewObject = new JSONObject();
                viewObject.put("id", view.id);
                viewObject.put("type", view.type);
                viewsArray.put(viewObject);
            }
            jsonObject.put("views", viewsArray);
            

            String jsonString = jsonObject.toString(4);
            

            jsonString = Base64.encodeToString(jsonString.getBytes(), Base64.DEFAULT);
            

            try (FileWriter fileWriter = new FileWriter(outputFilePath)) {
                fileWriter.write(jsonString);
            }
            
            Log.d("JSON Saver", "✅ Config saved successfully!");
            
        } catch (Exception e) {
            Log.e("JSON Saver", "❌ Error saving JSON: " + e.toString());
        }
    }
    

    public void saveToJson(String outputFilePath, boolean useBase64) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("packageName", packageName);
            jsonObject.put("activityName", activityName);
            jsonObject.put("parentActivity", parentActivity);
            

            jsonObject.put("imports", new JSONArray(imports));
            

            JSONArray eventsArray = new JSONArray();
            for (Event event : events) {
                JSONObject eventObject = new JSONObject();
                eventObject.put("name", event.name);
                eventObject.put("method", event.method);
                eventObject.put("code", event.code);
                eventsArray.put(eventObject);
            }
            jsonObject.put("events", eventsArray);
            

            JSONArray viewsArray = new JSONArray();
            for (CustomView view : views) {
                JSONObject viewObject = new JSONObject();
                viewObject.put("id", view.id);
                viewObject.put("type", view.type);
                viewsArray.put(viewObject);
            }
            jsonObject.put("views", viewsArray);
            

            String jsonString = jsonObject.toString(4);
            

            if (useBase64) {
                jsonString = Base64.encodeToString(jsonString.getBytes(), Base64.DEFAULT);
            }
            

            try (FileWriter fileWriter = new FileWriter(outputFilePath)) {
                fileWriter.write(jsonString);
            }
            
            Log.d("JSON Saver", "✅ Config saved successfully!");
            
        } catch (Exception e) {
            Log.e("JSON Saver", "❌ Error saving JSON: " + e.toString());
        }
    }

    public void saveState() {
        undoStack.push(new JavaSourceCodeMakerState(new ArrayList<>(imports), new ArrayList<>(events), new ArrayList<>(views)));
        redoStack.clear();  // Clear the redo stack when a new action is made
    }
    

    public void undo() {
        if (!undoStack.isEmpty()) {
            JavaSourceCodeMakerState previousState = undoStack.pop();
            redoStack.push(new JavaSourceCodeMakerState(new ArrayList<>(imports), new ArrayList<>(events), new ArrayList<>(views)));
            this.imports = previousState.imports;
            this.events = previousState.events;
            this.views = previousState.views;
        }
    }
    

    public void redo() {
        if (!redoStack.isEmpty()) {
            JavaSourceCodeMakerState nextState = redoStack.pop();
            undoStack.push(new JavaSourceCodeMakerState(new ArrayList<>(imports), new ArrayList<>(events), new ArrayList<>(views)));
            this.imports = nextState.imports;
            this.events = nextState.events;
            this.views = nextState.views;
        }
    }
    

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
    

    public void addEvent(String name, String method, String code) {
        saveState();
        events.add(new Event(name, method, code));
    }
    

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
    

    public void updateId(String currentId, String newId) {
        saveState(); // Save the current state for undo/redo
        
        boolean updated = false; // Flag to check if any ID was updated
        

        if (views != null) {
            for (CustomView view : views) {
                if (view != null && currentId.equals(view.id)) {
                    view.id = newId;
                    updated = true;

                }
            }
        } else {
            throw new IllegalStateException("Views list is null");
        }
        

        if (events != null) {
            for (Event event : events) {
                if (event != null && currentId.equals(event.name)) {
                    event.name = newId;
                    updated = true;

                }
            }
        } else {
            throw new IllegalStateException("Events list is null");
        }
        

        if (!updated) {

        }
    }
    
    

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

    }
    
    

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
    

    public void setLogic(String logic) {
        this.logic = logic;
    }

    public String generateJavaClassCode() {
        StringBuilder javaCode = new StringBuilder();
        

        javaCode.append("package ").append(packageName).append(";\n\n");
        

        for (String importLine : imports) {
            javaCode.append("import ").append(importLine).append(";\n");
        }
        
        javaCode.append("\n");
        

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
        

        String parentClass = isDialogFragment ? "DialogFragment" : (isFragment ? "Fragment" : parentActivity);
        

        javaCode.append("public class ").append(activityName).append(" extends ").append(parentClass).append(" {\n\n");
        

        for (CustomView view : views) {
            javaCode.append("    private ").append(view.type).append(" ").append(view.id).append(";\n");
        }
        
        javaCode.append("\n");
        

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

        else if (isFragment) {
            javaCode.append("    @Override\n")
            .append("    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {\n")
            .append("        View view = inflater.inflate(R.layout.").append(layoutName).append(", container, false);\n")
            .append("        initializeViews(view);\n")
            .append("        initializeLogic();\n")
            .append("        return view;\n")
            .append("    }\n\n");
        }  

        else {
            javaCode.append("    @Override\n")
            .append("    protected void onCreate(Bundle savedInstanceState) {\n")
            .append("        super.onCreate(savedInstanceState);\n")
            .append("        setContentView(R.layout.").append(layoutName).append(");\n")
            .append("        initializeViews(getWindow().getDecorView());\n")
            .append("        initializeLogic();\n")
            .append("    }\n\n");
        }
        

        javaCode.append("    private void initializeViews(View root) {\n");
        
        for (CustomView view : views) {
            javaCode.append("        ").append(view.id).append(" = root.findViewById(R.id.").append(view.id).append(");\n");
        }
        
        javaCode.append("    }\n\n");
        

        javaCode.append("    private void initializeLogic() {\n")
        .append("        ").append(logic).append("\n")
        .append("    }\n");
        

        javaCode.append("}\n");
        
        return javaCode.toString();
    }
    
    
    public String generateKotlinClassCode() {
        StringBuilder kotlinCode = new StringBuilder();
        

        kotlinCode.append("package ").append(packageName).append("\n\n");
        

        for (String importLine : imports) {
            kotlinCode.append("import ").append(importLine).append("\n");
        }
        
        kotlinCode.append("\n");
        

        String parentClass = isDialogFragment ? "DialogFragment" : (isFragment ? "Fragment" : parentActivity);
        

        kotlinCode.append("class ").append(activityName).append(" : ").append(parentClass).append("() {\n\n");
        

        for (CustomView view : views) {
            kotlinCode.append("    private lateinit var ").append(view.id).append(": ").append(view.type).append("\n");
        }
        
        kotlinCode.append("\n");
        

        if (isDialogFragment) {
            kotlinCode.append("    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {\n")
            .append("        val dialog = super.onCreateDialog(savedInstanceState)\n")
            .append("        dialog.setContentView(R.layout.").append(layoutName).append(")\n")
            .append("        initializeViews(dialog.window?.decorView)\n")
            .append("        initializeLogic()\n")
            .append("        return dialog\n")
            .append("    }\n\n");
        }  

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

        else {
            kotlinCode.append("    override fun onCreate(savedInstanceState: Bundle?) {\n")
            .append("        super.onCreate(savedInstanceState)\n")
            .append("        setContentView(R.layout.").append(layoutName).append(")\n")
            .append("        initializeViews(window.decorView)\n")
            .append("        initializeLogic()\n")
            .append("    }\n\n");
        }
        

        kotlinCode.append("    private fun initializeViews(root: View?) {\n");
        
        for (CustomView view : views) {
            kotlinCode.append("        ").append(view.id).append(" = root?.findViewById(R.id.").append(view.id).append(")!!\n");
        }
        
        kotlinCode.append("    }\n\n");
        

        kotlinCode.append("    private fun initializeLogic() {\n")
        .append("        ").append(logic).append("\n")
        .append("    }\n");
        

        kotlinCode.append("}\n");
        
        return kotlinCode.toString();
    }
    
    
    public void saveGeneratedCode(String outputFilePath) {
        try (FileWriter fileWriter = new FileWriter(outputFilePath)) {
            fileWriter.write(generateJavaClassCode());

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
        

        activityName = activityName.replace(".java", "");
        
        this.activityName = activityName;
        this.parentActivity = parentActivity;
        
        List<String> currentImports = new ArrayList<>(imports);
        imports.clear();
        for (String importStatement : currentImports) {
            addImport(importStatement);
        }
        

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
            

            JSONArray activitiesArray = jsonObject.optJSONArray("activities");
            if (activitiesArray == null) {
                activitiesArray = new JSONArray();
            }
            

            for (int i = 0; i < activitiesArray.length(); i++) {
                JSONObject existingActivity = activitiesArray.getJSONObject(i);
                if (existingActivity.getString("name").equals(activityName)) {
                    return; // Activity already exists
                }
            }
            

            JSONObject newActivity = new JSONObject();
            newActivity.put("name", activityName);
            newActivity.put("parentActivity", parentActivity);
            

            activitiesArray.put(newActivity);
            jsonObject.put("activities", activitiesArray);
            

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(jsonObject.toString(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public void saveActivityToJson(String jsonFilePath, String activityName, String parentActivity, boolean isPrivate) {
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
            

            JSONArray activitiesArray = jsonObject.optJSONArray("activities");
            if (activitiesArray == null) {
                activitiesArray = new JSONArray();
            }
            

            activityName = (activityName == null || activityName.trim().isEmpty()) ? "MainActivity" : activityName.replace(".java", "");
            

            for (int i = 0; i < activitiesArray.length(); i++) {
                JSONObject existingActivity = activitiesArray.getJSONObject(i);
                if (existingActivity.getString("name").equals(activityName)) {
                    return; // ⚠️ Already Exists, No Need to Add Again
                }
            }
            

            JSONObject newActivity = new JSONObject();
            newActivity.put("name", activityName);
            newActivity.put("parentActivity", (parentActivity == null || parentActivity.trim().isEmpty()) ? "AppCompatActivity" : parentActivity);
            newActivity.put("isPrivate", isPrivate); // ✅ Private Activity Support
            

            activitiesArray.put(newActivity);
            jsonObject.put("activities", activitiesArray);
            

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
                

                String name = new String(Base64.decode(component.getString("name"), Base64.NO_WRAP));
                String cusType = new String(Base64.decode(component.getString("cusType"), Base64.NO_WRAP));
                String activity = component.getString("activity");
                String importStatement = component.getString("import");
                

                if (activity.equals(this.activityName)) {
                    JSONObject fieldObject = new JSONObject();
                    fieldObject.put("name", name);
                    fieldObject.put("type", cusType);
                    
                    privateFields.put(fieldObject);
                    importsSet.add(importStatement);
                }
            }
            

            result.put("privateFields", privateFields);
            

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
            

            String activityKey = null;
            for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                String key = it.next();
                if (key.equals(this.activityName)) {
                    activityKey = key;
                    break;
                }
            }
            

            if (activityKey == null) {
                Log.w("Activity Extractor", "❌ Activity not found: " + this.activityName);
                return;
            }
            

            JSONObject activityJson = jsonObject.getJSONObject(activityKey);
            

            JSONArray tagAttributesArray = activityJson.optJSONArray("tagAttributes");
            if (tagAttributesArray != null) {
                for (int i = 0; i < tagAttributesArray.length(); i++) {
                    JSONObject tagAttribute = tagAttributesArray.getJSONObject(i);
                    if (tagAttribute.has("key") && tagAttribute.has("value")) {
                        JSONObject valueObject = tagAttribute.getJSONObject("value");
                        if ("android:id".equals(tagAttribute.getString("key")) &&
                        valueObject.has("data") && valueObject.getJSONObject("data").has("value")) {
                            

                            String viewId = valueObject.getJSONObject("data").getString("value");
                            String viewType = getViewType(activityJson.optString("type", "View"));
                            

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
            

            JSONObject activitiesJson = jsonObject.optJSONObject("activities");
            if (activitiesJson == null) {
                activitiesJson = new JSONObject();
            }
            

            JSONObject activityJson = new JSONObject();
            activityJson.put("packageName", packageName);
            activityJson.put("parentActivity", parentActivity);
            activityJson.put("layoutName", layoutName);
            

            activityJson.put("imports", new JSONArray(imports));
            

            JSONArray viewsArray = new JSONArray();
            for (CustomView view : views) {
                JSONObject viewJson = new JSONObject();
                viewJson.put("id", view.id);
                viewJson.put("type", view.type);
                viewsArray.put(viewJson);
            }
            activityJson.put("views", viewsArray);
            

            activitiesJson.put(activityName, activityJson);
            jsonObject.put("activities", activitiesJson);
            

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
            

            for (Iterator<String> it = activitiesJson.keys(); it.hasNext(); ) {
                String activityName = it.next();
                JSONObject activityJson = activitiesJson.getJSONObject(activityName);
                

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
            

            int activityCounter = 1;
            Map<String, JavaSourceCodeMaker> activityInstances = new HashMap<>();
            

            for (Iterator<String> it = activitiesJson.keys(); it.hasNext(); ) {
                String activityName = it.next();
                JSONObject activityJson = activitiesJson.getJSONObject(activityName);
                
                String packageName = activityJson.optString("packageName", "com.default.package");
                String parentActivity = activityJson.optString("parentActivity", "AppCompatActivity");
                String layoutName = activityJson.optString("layoutName", "activity_main");
                

                JavaSourceCodeMaker newActivity = new JavaSourceCodeMaker(jsonFilePath, packageName);
                newActivity.setActivityName(activityName);
                newActivity.setParentActivity(parentActivity);
                newActivity.setLayoutName(layoutName);
                

                activityInstances.put("newActivity" + activityCounter, newActivity);
                activityCounter++;
                

                String generatedCode = newActivity.generateJavaClassCode();
                

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
