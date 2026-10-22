package b.b.b;

import android.content.Context;
import android.content.res.Configuration;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import b.b.b.pq;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.Base64;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.nexusteam.blacklogics.FileUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shapun.layouteditor.utils.AttributeUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.io.StringReader;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;



public class yg {
    
    private aq packageManager;
    private xq activityManager;
    private String sc_id;
    private String projectPath;
    private String output;
    private String xmlOutPut;
    private Gson gson;
    private final Set<String> LIFECYCLE_METHODS = new HashSet<>(Arrays.asList(
    "onCreate", "onStart", "onResume", "onPause", "onStop", "onDestroy",
    "onBackPressed", "onActivityResult", "onRequestPermissionsResult", "onConfigurationChanged",
    "onSaveInstanceState", "onRestoreInstanceState", "onRestart",
    "onNewIntent", "onWindowFocusChanged"
    ));
    
    private final Set<String> SYSTEM_CALLBACKS_WITH_SUPER = new HashSet<>(Arrays.asList(
    "onCreate", "onStart", "onResume", "onPause", "onStop", "onDestroy",
    "onSaveInstanceState", "onRestoreInstanceState", "onRestart",
    "onActivityResult", "onRequestPermissionsResult", "onConfigurationChanged"
    ));
    
    
    private static final char[] ENC_PASSWORD =
    "blacklogic_layout_secure".toCharArray();
    
    private String cachedLayoutJson = null;
    private long cacheLastModified = -1;
    
    private String layoutPath = "";
    
    private static final Pattern MORE_BLOCK_PATTERN = Pattern.compile("%block\\.([a-zA-Z_][a-zA-Z0-9_]*)\\(([^)]*)\\)");
    private static final Pattern MORE_BLOCK_SIMPLE_PATTERN = Pattern.compile("\\$([a-zA-Z_][a-zA-Z0-9_]*)\\(([^)]*)\\)");
    
    private static final char[] ENCRYPT_PASSWORD = "NexusTeamStrongKey!".toCharArray();
    
    public yg(Context context, String sc_id) {
        this.packageManager = new aq();
        this.activityManager = new xq();
        this.sc_id = sc_id;
        this.projectPath = "/storage/emulated/0" + "/.blacklogics/data/" + sc_id;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.activityManager.load(context, sc_id);
        this.packageManager.load(context, sc_id);
        Qf.setPath(projectPath);
        this.layoutPath = "/storage/emulated/0/.blacklogics/mysc/list/" + sc_id;
    }
    

    
    public interface EventBinder {
        void bind(StringBuilder javaCode, String widgetId, String logic);
    }
    

    public static class ClickBinder implements EventBinder {
        @Override
        public void bind(StringBuilder javaCode, String widgetId, String logic) {
            javaCode.append("        ").append(widgetId)
            .append(".setOnClickListener(v -> {\n")
            .append(format(logic, 8))
            .append("        });\n");
        }
        
        private String format(String s, int space) {
            String pad = " ".repeat(space);
            return pad + s.replace("\n", "\n" + pad) + "\n";
        }
    }
    

    public static class TextChangeBinder implements EventBinder {
        @Override
        public void bind(StringBuilder javaCode, String widgetId, String logic) {
            javaCode.append("        ").append(widgetId)
            .append(".addTextChangedListener(new TextWatcher() {\n")
            .append("            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {\n")
            .append(format(logic, 16))
            .append("            }\n")
            .append("            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}\n")
            .append("            @Override public void afterTextChanged(Editable s) {}\n")
            .append("        });\n");
        }
        
        private String format(String s, int space) {
            String pad = " ".repeat(space);
            return pad + s.replace("\n", "\n" + pad) + "\n";
        }
    }
    
    

    public static class CheckedChangeBinder implements EventBinder {
        @Override
        public void bind(StringBuilder javaCode, String widgetId, String logic) {
            javaCode.append("        ").append(widgetId)
            .append(".setOnCheckedChangeListener((buttonView, isChecked) -> {\n")
            .append(format(logic, 8))
            .append("        });\n");
        }
        
        private String format(String s, int space) {
            String pad = " ".repeat(space);
            return pad + s.replace("\n", "\n" + pad) + "\n";
        }
    }
    

    public static class SpinnerBinder implements EventBinder {
        @Override
        public void bind(StringBuilder javaCode, String widgetId, String logic) {
            javaCode.append("        ").append(widgetId)
            .append(".setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {\n")
            .append("            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {\n")
            .append(format(logic, 16))
            .append("            }\n")
            .append("            @Override public void onNothingSelected(AdapterView<?> parent) {}\n")
            .append("        });\n");
        }
        
        private String format(String s, int space) {
            String pad = " ".repeat(space);
            return pad + s.replace("\n", "\n" + pad) + "\n";
        }
    }
    

    public static class SeekBarBinder implements EventBinder {
        @Override
        public void bind(StringBuilder javaCode, String widgetId, String logic) {
            javaCode.append("        ").append(widgetId)
            .append(".setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {\n")
            .append("            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {\n")
            .append(format(logic, 16))
            .append("            }\n")
            .append("            @Override public void onStartTrackingTouch(SeekBar seekBar) {}\n")
            .append("            @Override public void onStopTrackingTouch(SeekBar seekBar) {}\n")
            .append("        });\n");
        }
        
        private String format(String s, int space) {
            String pad = " ".repeat(space);
            return pad + s.replace("\n", "\n" + pad) + "\n";
        }
    }
    
    public static final class WidgetEventResolver {
        
        private static final Map<Class<?>, EventBinder> BINDERS = new LinkedHashMap<>();
        
        static {

            BINDERS.put(android.widget.EditText.class, new TextChangeBinder());
            BINDERS.put(com.google.android.material.textfield.TextInputEditText.class, new TextChangeBinder());
            

            BINDERS.put(android.widget.CompoundButton.class, new CheckedChangeBinder());
            

            BINDERS.put(android.widget.Spinner.class, new SpinnerBinder());
            

            BINDERS.put(android.widget.SeekBar.class, new SeekBarBinder());
            

            BINDERS.put(android.view.View.class, new ClickBinder());
        }
        
        public static EventBinder resolve(String widgetClassName) {
            try {
                Class<?> widgetClass = Class.forName(widgetClassName);
                
                for (Map.Entry<Class<?>, EventBinder> e : BINDERS.entrySet()) {
                    if (e.getKey().isAssignableFrom(widgetClass)) {
                        return e.getValue();
                    }
                }
            } catch (Throwable ignored) {}
            

            return new ClickBinder();
        }
    }
    
    
    public void setOutPut(String output) {
        this.output = output;
    }
    
    public void setXmlOutPut(String xmlOutPut) {
        this.xmlOutPut = xmlOutPut;
    }
    
    public void extractAllJavaCodes() {
        Qf.resetCache();
        try {
            Qf.resetCache();
            
            List<xq.ViewItem> activities = activityManager.getActivities();
            Map<String, String> allJavaCodes = new LinkedHashMap<>();
            
            for (xq.ViewItem activity : activities) {
                String activityName = activity.getJavaName(); 
                String layoutName = activity.getXmlName();   
                
                System.out.println("Extracting code for: " + activityName);
                
                String javaCode = generateCompleteJavaCode(activityName, layoutName);
                
                saveActivityCode(activityName, javaCode);
                
                allJavaCodes.put(activityName, javaCode);
            }
            
            saveAllCodes(allJavaCodes);
            
            System.out.println("✅ Successfully extracted " + allJavaCodes.size() + " activities!");
            
        } catch (Exception e) {
            System.err.println("❌ Error extracting codes: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
* Extracts XML for all activities from root_layout.json and saves them as individual .xml files.
*/    
    public void extractAndSaveAllXmlCodes() {
        System.out.println("Starting XML extraction and saving...");
        
        try {
            List<xq.ViewItem> activities = activityManager.getActivities();
            int savedCount = 0;
            
            String layoutPath = projectPath + "/root_layout.json";
            File layoutFile = new File(layoutPath);
            
            if (!layoutFile.exists()) {
                System.err.println("❌ Error: root_layout.json not found at " + layoutPath);
                return;
            }
            
            String jsonContent = new String(Files.readAllBytes(layoutFile.toPath()), StandardCharsets.UTF_8);
            JSONArray layoutArray = new JSONArray(jsonContent);
            

            new File(output).mkdirs();
            
            for (xq.ViewItem activity : activities) {
                String activityName = activity.getJavaName(); 
                String layoutName = activity.getXmlName();
                String rawXmlContent = null;
                

                for (int i = 0; i < layoutArray.length(); i++) {
                    JSONObject layoutObj = layoutArray.getJSONObject(i);
                    if (layoutObj.getString("name").equals(activityName)) {
                        rawXmlContent = layoutObj.getString("xml"); 
                        break;
                    }
                }
                
                if (rawXmlContent != null) {

                    String decodedXml = decodeUnicodeEscapes(rawXmlContent);
                    

                    saveXmlCode(layoutName, decodedXml);
                    savedCount++;
                }
            }
            
            System.out.println("✅ Successfully extracted and saved " + savedCount + " XML layouts to: " + output);
            
        } catch (Exception e) {
            System.err.println("❌ Error extracting and saving XML codes: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public String generateCompleteJavaCode(String activityName, String layoutName) {
        StringBuilder javaCode = new StringBuilder();
        

        String pkgName = extractPackageName();
        javaCode.append("package ").append(pkgName).append(";\n\n");
        

        addRequiredImports(javaCode, activityName);
        

        javaCode.append("public class ").append(activityName).append(" extends Activity {\n\n");
        

        addFieldsSection(javaCode, activityName);
        

        addOnCreateMethod(javaCode, activityName, layoutName);
        

        addInitializeMethod(javaCode, activityName);
        

        addInitializeLogicMethod(javaCode, activityName);
        

        getBlockLogicForLifecycleEvent(javaCode, activityName);
        

        addCustomFunctions(javaCode, activityName);
        

        addUtilityMethods(javaCode);
        
        javaCode.append("}\n");
        
        return javaCode.toString();
    }
    
    public String getLayoutXml(String layoutName) {
        
        if (layoutName == null || layoutName.trim().isEmpty()) return null;
        
        try {
            File encFile = new File(layoutPath + "/layout.bin");
            if (!encFile.exists()) return null;
            
            String json = readEncryptedJson(encFile);
            if (json == null || json.trim().isEmpty()) return null;
            
            ArrayList<HashMap<String, Object>> layoutList =
            new Gson().fromJson(
            json,
            new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType()
            );
            
            if (layoutList == null) return null;
            
            for (HashMap<String, Object> layout : layoutList) {
                
                Object nameObj = layout.get("name");
                Object xmlObj  = layout.get("xml");
                
                if (nameObj == null || xmlObj == null) continue;
                
                if (layoutName.equals(String.valueOf(nameObj))) {
                    return String.valueOf(xmlObj);
                }
            }
            
        } catch (Exception e) {
            Log.e("CodeGenerator", "Failed to load layout XML", e);
        }
        
        return null;
    }
    
    
    private String extractPackageName() {
        try {
            return packageManager.getPackageName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "com.example.app"; // Fallback package name
    }
    
    private void addRequiredImports(StringBuilder javaCode, String activityName) {

        javaCode.append("import android.app.Activity;\n");
        javaCode.append("import android.os.Bundle;\n");
        javaCode.append("import android.widget.*;\n");
        javaCode.append("import android.view.*;\n");
        javaCode.append("import android.content.*;\n");
        javaCode.append("import android.util.*;\n");
        javaCode.append("import android.os.*;\n");
        javaCode.append("import android.webkit.*;\n");
        javaCode.append("import java.util.*;\n");
        

        Set<String> requiredImports = new HashSet<>();
        

        List<String> activityEvents = getLifecycleEventsForActivity(activityName);
        

        for (String event : activityEvents) {
            switch (event) {
                case "onConfigurationChanged":
                requiredImports.add("import android.content.res.Configuration;\n");
                break;
                case "onProvideKeyboardShortcuts":
                requiredImports.add("import android.view.KeyboardShortcutGroup;\n");
                break;
                case "onCreateContextMenu":
                case "onContextItemSelected":
                requiredImports.add("import android.view.ContextMenu;\n");
                requiredImports.add("import android.view.ContextMenu.ContextMenuInfo;\n");
                break;
                case "onTouchEvent":
                case "onTrackballEvent":
                case "onGenericMotionEvent":
                requiredImports.add("import android.view.MotionEvent;\n");
                break;
                case "onKeyDown":
                case "onKeyUp":
                case "onKeyLongPress":
                case "onKeyShortcut":
                case "onKeyMultiple":
                requiredImports.add("import android.view.KeyEvent;\n");
                break;
                case "onSensorChanged":
                case "onAccuracyChanged":
                requiredImports.add("import android.hardware.Sensor;\n");
                requiredImports.add("import android.hardware.SensorEvent;\n");
                requiredImports.add("import android.hardware.SensorEventListener;\n");
                requiredImports.add("import android.hardware.SensorManager;\n");
                break;
                case "onLocationChanged":
                case "onStatusChanged":
                case "onProviderEnabled":
                case "onProviderDisabled":
                requiredImports.add("import android.location.Location;\n");
                requiredImports.add("import android.location.LocationListener;\n");
                requiredImports.add("import android.location.LocationManager;\n");
                break;
                case "onCreateOptionsMenu":
                case "onPrepareOptionsMenu":
                case "onOptionsItemSelected":
                case "onOptionsMenuClosed":
                requiredImports.add("import android.view.Menu;\n");
                requiredImports.add("import android.view.MenuItem;\n");
                break;
            }
        }
        

        for (String importStr : requiredImports) {
            javaCode.append(importStr);
        }
        

        List<HashMap<String, String>> variables = getBlockLogicsVariables(activityName);
        for (HashMap<String, String> var : variables) {
            String type = var.get("varTypeName");
            if (type.contains("ArrayList") && !javaCode.toString().contains("import java.util.ArrayList")) {
                javaCode.append("import java.util.ArrayList;\n");
                break;
            }
            if (type.contains("HashMap") && !javaCode.toString().contains("import java.util.HashMap")) {
                javaCode.append("import java.util.HashMap;\n");
                break;
            }
        }
        

        List<HashMap<String, String>> components = getBlockLogicsComponents(activityName);
        for (HashMap<String, String> comp : components) {
            String type = comp.get("componentName");
            switch (type) {
                case "Timer":
                if (!javaCode.toString().contains("import java.util.Timer")) {
                    javaCode.append("import java.util.Timer;\n");
                    javaCode.append("import java.util.TimerTask;\n");
                }
                break;
                case "SharedPreferences":
                if (!javaCode.toString().contains("import android.content.SharedPreferences")) {
                    javaCode.append("import android.content.SharedPreferences;\n");
                }
                break;
                case "Intent":

                break;
                case "MediaPlayer":
                if (!javaCode.toString().contains("import android.media.MediaPlayer")) {
                    javaCode.append("import android.media.MediaPlayer;\n");
                }
                break;
                case "SensorManager":
                if (!javaCode.toString().contains("import android.hardware.SensorManager")) {
                    javaCode.append("import android.hardware.SensorManager;\n");
                }
                break;
            }
        }
        
        javaCode.append("\n");
    }
    

    private List<String> getLifecycleEventsForActivity(String activityName) {
        List<String> events = new ArrayList<>();
        try {
            String path = projectPath + "/events/lifecycle_events.json";
            File file = new File(path);
            
            if (file.exists()) {
                String json = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
                String decoded = new String(android.util.Base64.decode(json, android.util.Base64.DEFAULT), StandardCharsets.UTF_8);
                
                JSONObject allEvents = new JSONObject(decoded);
                JSONArray activityEvents = allEvents.optJSONArray(activityName);
                
                if (activityEvents != null) {
                    for (int i = 0; i < activityEvents.length(); i++) {
                        String event = activityEvents.optString(i);
                        events.add(event);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }
    
    private void addFieldsSection(StringBuilder javaCode, String activityName) {

        

        Map<String, String> widgets = extractWidgetsFromLayout(activityName);
        for (Map.Entry<String, String> widget : widgets.entrySet()) {
            javaCode.append("    private ").append(widget.getValue())
            .append(" ").append(widget.getKey()).append(";\n");
        }
        

        List<HashMap<String, String>> variables = loadVariableLogic(activityName);
        if (!variables.isEmpty()) {
            for (HashMap<String, String> var : variables) {
                String type = var.get("varTypeName");
                String name = var.get("varName");
                String value = getInitialValue(type);
                javaCode.append("    private ").append(type).append(" ")
                .append(name).append(" = ").append(value).append(";\n");
            }
        }
        

        List<HashMap<String, String>> components = getBlockLogicsComponents(activityName);
        if (!components.isEmpty()) {
            boolean timerDeclared = false;
            for (HashMap<String, String> comp : components) {
                String type = comp.get("componentName");
                String name = comp.get("fieldName");
                
                if ("Timer".equals(type)) {
                    if (!timerDeclared) {
                        javaCode.append("    private Timer _timer = new Timer();\n");
                        timerDeclared = true;
                    }
                    javaCode.append("    private TimerTask ").append(name).append(";\n");
                } else {
                    javaCode.append("    private ").append(type).append(" ").append(name).append(";\n");
                }
            }
        }
        
        javaCode.append("\n");
    }
    
    private void addOnCreateMethod(StringBuilder javaCode, String activityName, String layoutName) {
        javaCode.append("    @Override\n");
        javaCode.append("    protected void onCreate(Bundle savedInstanceState) {\n");
        javaCode.append("        super.onCreate(savedInstanceState);\n");
        javaCode.append("        setContentView(R.layout.").append(layoutName).append(");\n");
        javaCode.append("        initialize(savedInstanceState);\n");
        javaCode.append("        initializeLogic();\n");
        

        if (hasPermissions()) {
            javaCode.append("        initPermissions();\n");
        }
        
        javaCode.append("    }\n\n");
    }
    
    private void addInitializeMethod(StringBuilder javaCode, String activityName) {
        javaCode.append("    private void initialize(Bundle _savedInstanceState) {\n");
        

        Map<String, String> widgets = extractWidgetsFromLayout(activityName);
        for (String widgetId : widgets.keySet()) {
            javaCode.append("        ").append(widgetId)
            .append(" = findViewById(R.id.").append(widgetId).append(");\n");
        }
        

        List<HashMap<String, String>> components = getBlockLogicsComponents(activityName);
        for (HashMap<String, String> comp : components) {
            String type = comp.get("componentName");
            String name = comp.get("fieldName");
            String logic = getBlockLogicForEvent(activityName, name);
            
            switch (type) {
                case "Intent":
                javaCode.append("        ").append(name).append(" = new Intent();\n");
                break;
                case "Timer":
                javaCode.append("        ").append(name).append(" = new TimerTask() {\n");
                javaCode.append("            @Override\n");
                javaCode.append("            public void run() {\n");
                if (logic != null && !logic.isEmpty()) {
                    javaCode.append("                ").append(logic).append("\n");
                } else {

                }
                javaCode.append("            }\n");
                javaCode.append("        };\n");
                javaCode.append("        _timer.schedule(").append(name).append(", 0, 1000);\n");
                break;
                case "SharedPreferences":
                javaCode.append("        ").append(name).append(" = getSharedPreferences(\"app_prefs\", MODE_PRIVATE);\n");
                break;
                case "MediaPlayer":
                javaCode.append("        ").append(name).append(" = new MediaPlayer();\n");
                break;
            }
        }
        

        addWidgetEventBindings(javaCode, activityName);
        
        javaCode.append("    }\n\n");
    }
    
    private void addInitializeLogicMethod(StringBuilder javaCode, String activityName) {
        String mainLogic = getBlockLogics(activityName);
        String formattedLogic = "";
        if (mainLogic != null && !mainLogic.isEmpty()) {
            formattedLogic = "        " + mainLogic.replace("\n", "\n        ");
        }
        javaCode.append("    private void initializeLogic() {\n");
        
        javaCode.append(formattedLogic).append("\n");
        
        javaCode.append("    }\n\n");
    }
    

    private void getBlockLogicForLifecycleEvent(StringBuilder javaCode, String activityName) {
        try {
            String path = projectPath + "/events/lifecycle_events.json";
            File file = new File(path);
            
            if (file.exists()) {
                String json = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
                String decoded = new String(android.util.Base64.decode(json, android.util.Base64.DEFAULT), StandardCharsets.UTF_8);
                
                JSONObject allEvents = new JSONObject(decoded);
                JSONArray activityEvents = allEvents.optJSONArray(activityName);
                
                if (activityEvents != null) {
                    for (int i = 0; i < activityEvents.length(); i++) {
                        String event = activityEvents.optString(i);
                        String eventLogic = getBlockLogicForLifecycleEvent(activityName, event);
                        
                        String formattedLogic = "";
                        if (eventLogic != null && !eventLogic.isEmpty()) {
                            formattedLogic = "        " + eventLogic.replace("\n", "\n        ");
                        }
                        
                        switch (event) {
                            case "onCreate":

                            break;
                            
                            case "onPostCreate":
                            javaCode.append("    @Override\n");
                            javaCode.append("    protected void onPostCreate(Bundle savedInstanceState) {\n");
                            javaCode.append("        super.onPostCreate(savedInstanceState);\n");
                            if (!formattedLogic.isEmpty()) {
                                javaCode.append(formattedLogic).append("\n");
                            }
                            javaCode.append("    }\n\n");
                            break;
                            
                            case "onStart":
                            javaCode.append("    @Override\n");
                            javaCode.append("    protected void onStart() {\n");
                            javaCode.append("        super.onStart();\n");
                            if (!formattedLogic.isEmpty()) {
                                javaCode.append(formattedLogic).append("\n");
                            }
                            javaCode.append("    }\n\n");
                            break;
                            
                            case "onResume":
                            javaCode.append("    @Override\n");
                            javaCode.append("    protected void onResume() {\n");
                            javaCode.append("        super.onResume();\n");
                            if (!formattedLogic.isEmpty()) {
                                javaCode.append(formattedLogic).append("\n");
                            }
                            javaCode.append("    }\n\n");
                            break;
                            
                            case "onPostResume":
                            javaCode.append("    @Override\n");
                            javaCode.append("    protected void onPostResume() {\n");
                            javaCode.append("        super.onPostResume();\n");
                            if (!formattedLogic.isEmpty()) {
                                javaCode.append(formattedLogic).append("\n");
                            }
                            javaCode.append("    }\n\n");
                            break;
                            
                            case "onPause":
                            javaCode.append("    @Override\n");
                            javaCode.append("    protected void onPause() {\n");
                            javaCode.append("        super.onPause();\n");
                            if (!formattedLogic.isEmpty()) {
                                javaCode.append(formattedLogic).append("\n");
                            }
                            javaCode.append("    }\n\n");
                            break;
                            
                            case "onStop":
                            javaCode.append("    @Override\n");
                            javaCode.append("    protected void onStop() {\n");
                            javaCode.append("        super.onStop();\n");
                            if (!formattedLogic.isEmpty()) {
                                javaCode.append(formattedLogic).append("\n");
                            }
                            javaCode.append("    }\n\n");
                            break;
                            
                            case "onRestart":
                            javaCode.append("    @Override\n");
                            javaCode.append("    protected void onRestart() {\n");
                            javaCode.append("        super.onRestart();\n");
                            if (!formattedLogic.isEmpty()) {
                                javaCode.append(formattedLogic).append("\n");
                            }
                            javaCode.append("    }\n\n");
                            break;
                            
                            case "onDestroy":
                            javaCode.append("    @Override\n");
                            javaCode.append("    protected void onDestroy() {\n");
                            javaCode.append("        super.onDestroy();\n");
                            if (!formattedLogic.isEmpty()) {
                                javaCode.append(formattedLogic).append("\n");
                            }
                            javaCode.append("    }\n\n");
                            break;
                            
                            case "onBackPressed":
                            javaCode.append("    @Override\n");
                            javaCode.append("    public void onBackPressed() {\n");
                            javaCode.append("        super.onBackPressed();\n");
                            if (!formattedLogic.isEmpty()) {
                                javaCode.append(formattedLogic).append("\n");
                            }
                            javaCode.append("    }\n\n");
                            break;
                            
                            case "onSaveInstanceState":
                            javaCode.append("    @Override\n");
                            javaCode.append("    protected void onSaveInstanceState(Bundle outState) {\n");
                            javaCode.append("        super.onSaveInstanceState(outState);\n");
                            if (!formattedLogic.isEmpty()) {
                                javaCode.append(formattedLogic).append("\n");
                            }
                            javaCode.append("    }\n\n");
                            break;
                            
                            case "onRestoreInstanceState":
                            javaCode.append("    @Override\n");
                            javaCode.append("    protected void onRestoreInstanceState(Bundle savedInstanceState) {\n");
                            javaCode.append("        super.onRestoreInstanceState(savedInstanceState);\n");
                            if (!formattedLogic.isEmpty()) {
                                javaCode.append(formattedLogic).append("\n");
                            }
                            javaCode.append("    }\n\n");
                            break;
                            
                            case "onActivityResult":
                            javaCode.append("    @Override\n");
                            javaCode.append("    protected void onActivityResult(int requestCode, int resultCode, Intent data) {\n");
                            javaCode.append("        super.onActivityResult(requestCode, resultCode, data);\n");
                            if (!formattedLogic.isEmpty()) {
                                javaCode.append(formattedLogic).append("\n");
                            }
                            javaCode.append("    }\n\n");
                            break;
                            
                            case "onRequestPermissionsResult":
                            javaCode.append("    @Override\n");
                            javaCode.append("    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {\n");
                            javaCode.append("        super.onRequestPermissionsResult(requestCode, permissions, grantResults);\n");
                            if (!formattedLogic.isEmpty()) {
                                javaCode.append(formattedLogic).append("\n");
                            }
                            javaCode.append("    }\n\n");
                            break;
                            
                            case "onUserLeaveHint":
                            javaCode.append("    @Override\n");
                            javaCode.append("    protected void onUserLeaveHint() {\n");
                            javaCode.append("        super.onUserLeaveHint();\n");
                            if (!formattedLogic.isEmpty()) {
                                javaCode.append(formattedLogic).append("\n");
                            }
                            javaCode.append("    }\n\n");
                            break;
                            
                            case "onTrimMemory":
                            javaCode.append("    @Override\n");
                            javaCode.append("    public void onTrimMemory(int level) {\n");
                            javaCode.append("        super.onTrimMemory(level);\n");
                            if (!formattedLogic.isEmpty()) {
                                javaCode.append(formattedLogic).append("\n");
                            }
                            javaCode.append("    }\n\n");
                            break;
                            
                            case "onLowMemory":
                            javaCode.append("    @Override\n");
                            javaCode.append("    public void onLowMemory() {\n");
                            javaCode.append("        super.onLowMemory();\n");
                            if (!formattedLogic.isEmpty()) {
                                javaCode.append(formattedLogic).append("\n");
                            }
                            javaCode.append("    }\n\n");
                            break;
                            
                            case "onNewIntent":
                            javaCode.append("    @Override\n");
                            javaCode.append("    protected void onNewIntent(Intent intent) {\n");
                            javaCode.append("        super.onNewIntent(intent);\n");
                            if (!formattedLogic.isEmpty()) {
                                javaCode.append(formattedLogic).append("\n");
                            }
                            javaCode.append("    }\n\n");
                            break;
                            
                            case "onConfigurationChanged":
                            javaCode.append("    @Override\n");
                            javaCode.append("    public void onConfigurationChanged(Configuration newConfig) {\n");
                            javaCode.append("        super.onConfigurationChanged(newConfig);\n");
                            if (!formattedLogic.isEmpty()) {
                                javaCode.append(formattedLogic).append("\n");
                            }
                            javaCode.append("    }\n\n");
                            break;
                            case "onCreateOptionsMenu":
                            javaCode.append("    @Override\n");
                            javaCode.append("    public boolean onCreateOptionsMenu(Menu menu) {\n");
                            if (!formattedLogic.isEmpty()) {
                                javaCode.append(formattedLogic).append("\n");
                            }
                            javaCode.append("        return super.onCreateOptionsMenu(menu);\n");
                            javaCode.append("    }\n\n");
                            break;
                            
                            case "onOptionsItemSelected":
                            javaCode.append("    @Override\n");
                            javaCode.append("    public boolean onOptionsItemSelected(MenuItem item) {\n");
                            javaCode.append("        final int _id = item.getItemId();\n");
                            javaCode.append("        final String _title = (String) item.getTitle();\n");
                            if (!formattedLogic.isEmpty()) {
                                javaCode.append(formattedLogic).append("\n");
                            }
                            javaCode.append("        return super.onOptionsItemSelected(item);\n");
                            javaCode.append("    }\n\n");
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    private void addCustomFunctions(StringBuilder javaCode, String activityName) {
        
        List<HashMap<String, Object>> functions = getBlockLogicsFunctions(activityName);
        
        for (Object fnObj : functions) {
            
            if (!(fnObj instanceof Map)) continue;
            
            Map<?, ?> func = (Map<?, ?>) fnObj;
            
            String name = String.valueOf(func.get("functionName"));
            String returnType = String.valueOf(func.get("returnType"));
            
            String eventLogic = getBlockLogicForEvent(activityName, name);
            
            String formattedLogic = "";
            if (eventLogic != null && !eventLogic.isEmpty()) {
                formattedLogic = "        " + eventLogic.replace("\n", "\n        ");
            }
            
            javaCode.append("    public ")
            .append(returnType)
            .append(" ")
            .append(name)
            .append("(");
            

            List<String> params = new ArrayList<>();
            
            Object paramsObj = func.get("parameters");
            if (paramsObj instanceof List) {
                
                for (Object pObj : (List<?>) paramsObj) {
                    
                    if (pObj instanceof Map) {
                        Map<?, ?> p = (Map<?, ?>) pObj;
                        
                        String type = String.valueOf(p.get("type"));
                        String pname = String.valueOf(p.get("name"));
                        
                        params.add(type + " " + pname);
                    }
                }
            }
            
            javaCode.append(String.join(", ", params)).append(") {\n");
            
            javaCode.append(formattedLogic).append("\n");
            
            if (!"void".equals(returnType)) {
                javaCode.append("        return ")
                .append(getDefaultReturnValue(returnType))
                .append(";\n");
            }
            
            javaCode.append("    }\n\n");
        }
    }
    
    
    private void addWidgetEventBindings(StringBuilder javaCode, String activityName) {
        
        Map<String, String> widgetEvents = getWidgetClickListeners(activityName);
        Map<String, String> widgets = extractWidgetsFromLayout(activityName);
        
        for (String widgetId : widgets.keySet()) {
            
            String logic = widgetEvents.get(widgetId);
            if (logic == null || logic.trim().isEmpty()) continue;
            
            String widgetClassName = widgets.get(widgetId); // full class name e.g., android.widget.Button
            
            EventBinder binder = WidgetEventResolver.resolve(widgetClassName);
            
            binder.bind(javaCode, widgetId, logic);
        }
    }
    
    
    /**
* 🧩 IntelliJ-style pretty printer (same as Android Studio formatting)
*/    
    private String prettyFormatJava(String rawCode, int baseIndentSpaces) {
        String[] lines = rawCode.replace("\r", "").split("\n");
        StringBuilder formatted = new StringBuilder();
        
        int indentLevel = 0;
        String indentUnit = "    "; // 4 spaces
        String baseIndent = " ".repeat(baseIndentSpaces);
        

        Pattern decreaseIndent = Pattern.compile("^(**\\}**|\\s*else(\\s+if)?|\\s*catch|\\s*finally|\\s*case |\\s*default:)");
        Pattern increaseIndent = Pattern.compile(".*\\{\\s*$");
        Pattern oneLineIf = Pattern.compile("^if\\s*\\(.*\\)\\s*[^\\{;]+;\\s*$");
        
        for (String rawLine : lines) {
            String line = rawLine.strip();
            
            if (line.isEmpty()) {
                formatted.append("\n");
                continue;
            }
            
            if (decreaseIndent.matcher(line).find()) {
                indentLevel = Math.max(0, indentLevel - 1);
            }
            
            formatted.append(baseIndent)
            .append(indentUnit.repeat(indentLevel))
            .append(line)
            .append("\n");
            
            if (increaseIndent.matcher(line).matches()) {
                indentLevel++;
            } else if (oneLineIf.matcher(line).matches()) {

            }
        }
        
        return formatted.toString();
    }
    
    
    private void addUtilityMethods(StringBuilder javaCode) {
        javaCode.append("    public void showMessage(String message) {\n");
        javaCode.append("        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();\n");
        javaCode.append("    }\n\n");
        
        if (hasPermissions()) {
            javaCode.append("    private void initPermissions() {\n");
            javaCode.append("        // Initialize permissions here\n");
            javaCode.append("    }\n\n");
        }
    }
    

    private Map<String, String> extractWidgetsFromLayout(String activityName) {
        
        Map<String, String> widgets = new LinkedHashMap<>();
        
        if (activityName == null || activityName.trim().isEmpty()) {
            return widgets;
        }
        
        try {
            

            File encFile = new File(layoutPath + "/layout.bin");
            if (!encFile.exists()) return widgets;
            

            String json = readEncryptedJson(encFile);
            if (json == null || json.trim().isEmpty()) return widgets;
            
            ArrayList<HashMap<String, Object>> layoutList =
            new Gson().fromJson(
            json,
            new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType()
            );
            
            if (layoutList == null) return widgets;
            
            for (HashMap<String, Object> layout : layoutList) {
                
                Object nameObj = layout.get("name");
                Object xmlObj  = layout.get("xml");
                
                if (nameObj == null || xmlObj == null) continue;
                
                if (activityName.equals(String.valueOf(nameObj))) {
                    String xml = String.valueOf(xmlObj);
                    parseWidgetsFromXML(xml, widgets);
                    break;
                }
            }
            
        } catch (Exception e) {
            Log.e("WidgetExtract", "Encrypted extract failed", e);
        }
        
        return widgets;
    }
    
    private String readEncryptedJson(File file) throws Exception {
        
        long modified = file.lastModified();
        
        if (cachedLayoutJson != null && cacheLastModified == modified) {
            return cachedLayoutJson;
        }
        
        byte[] decrypted = pq.decryptFromFile(file, ENC_PASSWORD);
        
        String json = new String(decrypted, "UTF-8");
        
        cachedLayoutJson = json;
        cacheLastModified = modified;
        
        return json;
    }
    
    
    private void parseWidgetsFromXML(String xml, Map<String, String> widgets) {
        
        try {
            
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xml));
            
            int type;
            
            while ((type = parser.next()) != XmlPullParser.END_DOCUMENT) {
                
                if (type == XmlPullParser.START_TAG) {
                    
                    String tag = parser.getName();
                    String widgetType = mapTagToWidgetType(tag);
                    
                    for (int i = 0; i < parser.getAttributeCount(); i++) {
                        
                        String attrName  = parser.getAttributeName(i);
                        String attrValue = parser.getAttributeValue(i);
                        
                        if ("android:id".equals(attrName)) {
                            
                            String id = AttributeUtils.getName(attrValue);
                            if (id != null && !id.isEmpty()) {
                                widgets.put(id, widgetType);
                            }
                        }
                    }
                }
            }
            
        } catch (Exception e) {
            Log.e("WidgetExtract", "parseWidgetsFromXML failed", e);
        }
    }
    
    private String decodeUnicodeEscapes(String input) {
        StringBuilder output = new StringBuilder();
        int i = 0;
        while (i < input.length()) {
            if (input.charAt(i) == '\\' && i + 5 < input.length() && input.charAt(i + 1) == 'u') {
                try {
                    String hex = input.substring(i + 2, i + 6);
                    char unicodeChar = (char) Integer.parseInt(hex, 16);
                    output.append(unicodeChar);
                    i += 6;
                } catch (NumberFormatException e) {
                    output.append(input.charAt(i));
                    i++;
                }
            } else {
                output.append(input.charAt(i));
                i++;
            }
        }
        return output.toString();
    }
    
    private String extractTagName(String xmlLine) {
        int tagStart = xmlLine.indexOf("<") + 1;
        if (tagStart == 0) return "View";
        
        if (xmlLine.charAt(tagStart) == '?') return "View"; // Skip XML declaration
        
        int tagEnd = xmlLine.length();
        for (int i = tagStart; i < xmlLine.length(); i++) {
            char c = xmlLine.charAt(i);
            if (c == ' ' || c == '>' || c == '/' || c == '\n' || c == '\t') {
                tagEnd = i;
                break;
            }
        }
        return (tagEnd > tagStart) ? xmlLine.substring(tagStart, tagEnd) : "View";
    }
    
    private String mapTagToWidgetType(String tag) {
        
        if (tag == null) return "View";
        
        switch (tag) {
            
            case "TextView":
            case "Button":
            case "EditText":
            case "ImageView":
            case "CheckBox":
            case "Switch":
            case "SeekBar":
            case "ProgressBar":
            case "Spinner":
            case "RecyclerView":
            case "ListView":
            return tag;
            
            case "LinearLayout":
            case "RelativeLayout":
            case "FrameLayout":
            case "GridLayout":
            case "TableLayout":
            case "ConstraintLayout":
            case "CoordinatorLayout":
            case "MotionLayout":
            case "ScrollView":
            return tag;
            
            default:
            if (tag.contains(".")) {
                return tag.substring(tag.lastIndexOf(".") + 1);
            }
            return tag;
        }
    }
    

    private List<HashMap<String, String>> getBlockLogicsVariables(String activityName) {
        return Qf.loadVariableLogic(activityName);
    }
    

    private List<HashMap<String, String>> getBlockLogicsComponents(String activityName) {
        return Qf.loadComponentLogic(activityName);
    }
    
    
    private List<HashMap<String, Object>> getBlockLogicsFunctions(String activityName) {
        return Qf.loadFunctions(activityName);
    }
    
    
    private String getBlockLogics(String activityName) {
        return Qf.getBlockLogics(activityName);
    }
    
    
    
    public String getBlockLogicForEvent(String activityName, String widgetId, String eventName) {
        return Qf.getBlockLogicForEvent(activityName, widgetId, eventName);
    }
    
    public String getBlockLogicForEvent(String activityName, String eventName) {
        return Qf.getBlockLogicForEvent(activityName, eventName);
    }
    
    
    private Map<String, String> getWidgetClickListeners(String activityName) {
        return Qf.getWidgetClickListeners(activityName);
    }
    
    
    private String getBlockLogicForComponent(String componentName) {

        try {
            String compLogicPath = projectPath + "/block_logic/components/" + componentName + ".json";
            if (new File(compLogicPath).exists()) {
                String encodedJson = new String(Files.readAllBytes(new File(compLogicPath).toPath()));
                return new String(Base64.getDecoder().decode(encodedJson));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public List<HashMap<String, String>> loadVariableLogic(String activityName) {
        return Qf.loadVariableLogic(activityName);
    }
    
    
    private Map<String, String> getLifecycleEventsLogic(String activityName) {
        return Qf.getLifecycleEventsLogic(activityName);
    }
    
    
    
    private boolean hasPermissions() {

        return new File(projectPath + "/permission").exists();
    }
    

    
    private String getInitialValue(String type) {
        switch (type) {
            case "int": return "0";
            case "boolean": return "false";
            case "String": return "\"\"";
            case "ArrayList<String>": return "new ArrayList<>()";
            case "double": return "0.0";
            case "float": return "0.0f";
            case "long": return "0L";
            default: return "null";
        }
    }
    
    private String getDefaultReturnValue(String returnType) {
        switch (returnType) {
            case "int": return "0";
            case "boolean": return "false";
            case "String": return "\"\"";
            case "double": return "0.0";
            case "float": return "0.0f";
            case "long": return "0L";
            default: return "null";
        }
    }
    
    
    
    private String getBlockLogicForLifecycleEvent(String activityName, String eventName) {
        return Qf.getBlockLogicForEvent(activityName, eventName);
    }
    
    private void saveActivityCode(String activityName, String javaCode) {
        try {
            String outputDir = output;
            new File(outputDir).mkdirs();
            
            String filePath = outputDir + activityName + ".java";
            try (
            java.io.FileOutputStream fos = new java.io.FileOutputStream(filePath);
            java.io.OutputStreamWriter writer = new java.io.OutputStreamWriter(fos, StandardCharsets.UTF_8)
            ) {
                writer.write(javaCode);
            }
            
            
        } catch (Exception e) {
            
        }
    }
    
    private void saveXmlCode(String activityName, String javaCode) {
        try {
            String outputDir = xmlOutPut;
            new File(outputDir).mkdirs();
            
            String filePath = outputDir + activityName + ".xml";
            try (
            java.io.FileOutputStream fos = new java.io.FileOutputStream(filePath);
            java.io.OutputStreamWriter writer = new java.io.OutputStreamWriter(fos, StandardCharsets.UTF_8)
            ) {
                writer.write(javaCode);
            }
            
            
        } catch (Exception e) {
            
        }
    }
    
    private void saveAllCodes(Map<String, String> allJavaCodes) {
        try {
            String outputPath = projectPath + "/all_extracted_codes.json";
            String json = gson.toJson(allJavaCodes);
            
            try (FileWriter writer = new FileWriter(outputPath)) {

            }
            
            
        } catch (Exception e) {
            
        }
    }
}
