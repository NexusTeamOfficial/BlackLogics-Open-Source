package com.nexusteam.blacklogics.generator.source;

import android.util.Log;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nexusteam.blacklogics.security.crypto.FileEncryptionUtil;
import com.nexusteam.blacklogics.editor.layout.model.LayoutData;
import com.nexusteam.blacklogics.editor.layout.LayoutLoader;
import com.nexusteam.blacklogics.generator.source.model.ActivityStructureRegistry;
import com.nexusteam.blacklogics.logic.ProjectLogicRepository;
import com.nexusteam.blacklogics.generator.source.model.ActivityType;
import com.nexusteam.blacklogics.generator.source.model.ActivityTypeRegistry;
import com.nexusteam.blacklogics.model.DataModel;
import com.shapun.layouteditor.utils.AttributeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.StringReader;
import java.io.ObjectInputStream;
import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.regex.Pattern;

public class JavaCodeBuilder {
    
    private static final Pattern ONE_LINE_IF_PATTERN = Pattern.compile("^if\\s*\\(.*\\)\\s*[^\\{;]+;\\s*$");
    private static final Pattern DECREASE_INDENT_PATTERN = Pattern.compile("^(\\}|\\s*else(\\s+if)?|\\s*catch|\\s*finally|\\s*case |\\s*default:)");
    private static final Pattern INCREASE_INDENT_PATTERN = Pattern.compile(".*\\{\\s*$");
    private static final char[] ENC_PASSWORD =
    "blacklogic_layout_secure".toCharArray();
    private static String cachedLayoutJson = null;
    private static long cacheLastModified = -1;
    
    public interface EventBinder {
        void bind(StringBuilder javaCode, String widgetId, String logic);
    }
    
    public static void addFieldsSection(StringBuilder javaCode, String activityName, String projectPath, String layoutPath) {
    
        ActivityTypeRegistry registry = ActivityTypeRegistry.getInstance();
        com.nexusteam.blacklogics.generator.source.model.ActivityConfig config = registry.getActivityConfig(activityName);
        ActivityType activityType = (config != null) ? config.getType() : ActivityType.ACTIVITY;
        

        if (activityType != null && activityType.isFragment()) {
            javaCode.append("    private View rootView;\n");
        }
        

        if (activityType != null && activityType.isDialogFragment()) {
            javaCode.append("    private AlertDialog.Builder alertBuilder;\n");
            javaCode.append("    private AlertDialog alertDialog;\n");
        }
        
        Map<String, String> widgets = extractWidgetsFromLayout(activityName, layoutPath);
        for (Map.Entry<String, String> widget : widgets.entrySet()) {
            javaCode.append("    private ").append(widget.getValue())
            .append(" ").append(widget.getKey()).append(";\n");
        }
        
        List<HashMap<String, String>> variables = ProjectLogicRepository.loadVariableLogic(activityName);
        if (!variables.isEmpty()) {
            for (HashMap<String, String> var : variables) {
                String type = var.get("varTypeName");
                String name = var.get("varName");
                String value = getInitialValue(type);
                javaCode.append("    private ").append(type).append(" ")
                .append(name).append(" = ").append(value).append(";\n");
            }
        }
        
        List<HashMap<String, String>> components = ProjectLogicRepository.loadComponentLogic(activityName);
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
    
    public static void addOnCreateMethod(StringBuilder javaCode, String activityName, String layoutName) {

        ActivityTypeRegistry registry = ActivityTypeRegistry.getInstance();
        com.nexusteam.blacklogics.generator.source.model.ActivityConfig config = registry.getActivityConfig(activityName);
        ActivityType activityType = (config != null) ? config.getType() : ActivityType.ACTIVITY;
        
        if (activityType != null && activityType.isFragment()) {

            javaCode.append("    @Override\n");
            javaCode.append("    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {\n");
            javaCode.append("        rootView = inflater.inflate(R.layout.").append(layoutName).append(", container, false);\n");
            javaCode.append("        return rootView;\n");
            javaCode.append("    }\n\n");
            

            javaCode.append("    @Override\n");
            javaCode.append("    public void onViewCreated(View view, Bundle savedInstanceState) {\n");
            javaCode.append("        super.onViewCreated(view, savedInstanceState);\n");
            javaCode.append("        initialize(savedInstanceState);\n");
            javaCode.append("        initializeLogic();\n");
            javaCode.append("    }\n\n");
            

            if (activityType.isDialogFragment()) {
                javaCode.append("    @Override\n");
                javaCode.append("    public Dialog onCreateDialog(Bundle savedInstanceState) {\n");
                javaCode.append("        alertBuilder = new AlertDialog.Builder(getActivity());\n");
                javaCode.append("        LayoutInflater inflater = getActivity().getLayoutInflater();\n");
                javaCode.append("        View dialogView = inflater.inflate(R.layout.").append(layoutName).append(", null);\n");
                javaCode.append("        alertBuilder.setView(dialogView);\n");
                javaCode.append("        rootView = dialogView;\n");
                javaCode.append("        alertDialog = alertBuilder.create();\n");
                javaCode.append("        return alertDialog;\n");
                javaCode.append("    }\n\n");
            }
            
        } else {

            javaCode.append("    @Override\n");
            javaCode.append("    protected void onCreate(Bundle savedInstanceState) {\n");
            javaCode.append("        super.onCreate(savedInstanceState);\n");
            javaCode.append("        setContentView(R.layout.").append(layoutName).append(");\n");
            javaCode.append("        initialize(savedInstanceState);\n");
            javaCode.append("        initializeLogic();\n");
            javaCode.append("    }\n\n");
        }
    }
    
    public static void addInitializeMethod(StringBuilder javaCode, String activityName, String projectPath, String layoutPath) {
        ActivityTypeRegistry registry = ActivityTypeRegistry.getInstance();
        com.nexusteam.blacklogics.generator.source.model.ActivityConfig config = registry.getActivityConfig(activityName);
        ActivityType activityType = (config != null) ? config.getType() : ActivityType.ACTIVITY;
        boolean isFragment = (activityType != null && activityType.isFragment());
        
        javaCode.append("    private void initialize(Bundle savedInstanceState) {\n");
        

        Map<String, String> widgets = extractWidgetsFromLayout(activityName, layoutPath);
        
        if (isFragment) {
            javaCode.append("        if (rootView == null) return;\n\n");
            for (String widgetId : widgets.keySet()) {
                javaCode.append("        ").append(widgetId)
                .append(" = rootView.findViewById(R.id.").append(widgetId).append(");\n");
            }
        } else {
            for (String widgetId : widgets.keySet()) {
                javaCode.append("        ").append(widgetId)
                .append(" = findViewById(R.id.").append(widgetId).append(");\n");
            }
        }
        
        List<HashMap<String, String>> components = ProjectLogicRepository.loadComponentLogic(activityName);
        for (HashMap<String, String> comp : components) {
            String type = comp.get("componentName");
            String name = comp.get("fieldName");
            String logic = ProjectLogicRepository.getBlockLogicForEvent(activityName, name);
            
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
        
        addWidgetEventBindings(javaCode, activityName, layoutPath);
        javaCode.append("    }\n\n");
    }
    
    public static void addInitializeLogicMethod(StringBuilder javaCode, String activityName, String projectPath) {
        String mainLogic = ProjectLogicRepository.getBlockLogics(activityName);
        StringBuilder formattedLogic = new StringBuilder();
        
        if (mainLogic != null && !mainLogic.isEmpty()) {
            formattedLogic.append("        ").append(mainLogic.replace("\n", "\n        "));
        }
        
        javaCode.append("    private void initializeLogic() {\n");
        javaCode.append(formattedLogic.toString()).append("\n");
        javaCode.append("    }\n\n");
    }
    
    public static void getBlockLogicForLifecycleEvent(StringBuilder javaCode, String activityName, String projectPath) {
        try {
            String path = projectPath + "/events/lifecycle_events.json";
            File file = new File(path);
            
            if (file.exists()) {
                String json = new String(java.nio.file.Files.readAllBytes(file.toPath()), java.nio.charset.StandardCharsets.UTF_8);
                String decoded = new String(android.util.Base64.decode(json, android.util.Base64.DEFAULT), java.nio.charset.StandardCharsets.UTF_8);
                
                JSONObject allEvents = new JSONObject(decoded);
                JSONArray activityEvents = allEvents.optJSONArray(activityName);
                
                if (activityEvents != null) {
                    for (int i = 0; i < activityEvents.length(); i++) {
                        String event = activityEvents.optString(i);
                        addLifecycleEvent(javaCode, activityName, event, projectPath);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void addCustomFunctions(StringBuilder javaCode, String activityName, String projectPath) {
        List<HashMap<String, Object>> functions = ProjectLogicRepository.loadFunctions(activityName);
        
        for (Object fnObj : functions) {
            if (!(fnObj instanceof Map)) continue;
            
            Map<?, ?> func = (Map<?, ?>) fnObj;
            String name = String.valueOf(func.get("functionName"));
            String returnType = String.valueOf(func.get("returnType"));
            String eventLogic = ProjectLogicRepository.getBlockLogicForEvent(activityName, name);
            
            StringBuilder formattedLogic = new StringBuilder();
            if (eventLogic != null && !eventLogic.isEmpty()) {
                formattedLogic.append("        ").append(eventLogic.replace("\n", "\n        "));
            }
            
            javaCode.append("    public ").append(returnType).append(" ").append(name).append("(");
            
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
            javaCode.append(formattedLogic.toString()).append("\n");
            
            if (!"void".equals(returnType)) {
                javaCode.append("        return ").append(getDefaultReturnValue(returnType)).append(";\n");
            }
            
            javaCode.append("    }\n\n");
        }
    }
    
    public static void addUtilityMethods(StringBuilder javaCode, String projectPath) {
        javaCode.append("    public void showMessage(String message) {\n");
        javaCode.append("        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();\n");
        javaCode.append("    }\n\n");
        
        if (new File(projectPath + "/permission").exists()) {
            javaCode.append("    private void initPermissions() {\n");
            javaCode.append("        // Initialize permissions here\n");
            javaCode.append("    }\n\n");
        }
    }
    
    private static void addWidgetEventBindings(StringBuilder javaCode, String activityName, String layoutPath) {
        Map<String, String> widgetEvents = ProjectLogicRepository.getWidgetClickListeners(activityName);
        Map<String, String> widgets = extractWidgetsFromLayout(activityName, layoutPath);
        
        for (String widgetId : widgets.keySet()) {
            String logic = widgetEvents.get(widgetId);
            if (logic == null || logic.trim().isEmpty()) continue;
            
            String widgetClassName = widgets.get(widgetId);
            WidgetEventResolver.EventBinder binder = WidgetEventResolver.resolve(widgetClassName);
            binder.bind(javaCode, widgetId, logic);
        }
    }
    
    private static Map<String, String> extractWidgetsFromLayout(
    String activityName,
    String layoutPath
    ) {
        return LayoutLoader.extractWidgetsFromLayout(activityName, layoutPath);
    }
    
    private static String readEncryptedJson(File file) throws Exception {
        
        long modified = file.lastModified();
        
        if (cachedLayoutJson != null && cacheLastModified == modified) {
            return cachedLayoutJson;
        }
        
        byte[] decrypted = FileEncryptionUtil.decryptFromFile(file, ENC_PASSWORD);
        
        String json = new String(decrypted, "UTF-8");
        
        cachedLayoutJson = json;
        cacheLastModified = modified;
        
        return json;
    }
    
    private static ArrayList<LayoutData> readEncryptedBinaryLayouts(File file)
    throws Exception {
        
        byte[] decrypted =
        FileEncryptionUtil.decryptFromFile(file, ENC_PASSWORD);
        
        ObjectInputStream ois =
        new ObjectInputStream(
        new ByteArrayInputStream(decrypted)
        );
        
        Object obj = ois.readObject();
        ois.close();
        
        if (!(obj instanceof ArrayList)) {
            return new ArrayList<>();
        }
        
        @SuppressWarnings("unchecked")
        ArrayList<LayoutData> list =
        (ArrayList<LayoutData>) obj;
        
        return list;
    }
    
    
    private static void parseWidgetsFromXML(String xml, Map<String, String> widgets) {
        
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
    
    private static String getInitialValue(String type) {
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
    
    private static String getDefaultReturnValue(String returnType) {
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
    


/**
 * Generate more blocks code
 */
public static void addMoreBlocksCode(StringBuilder javaCode, String activityName, String projectPath, String sc_id) {

    String projectId = extractProjectIdFromPath(projectPath);
    
    if (projectId != null && !projectId.isEmpty()) {
        MoreBlockCodeGenerator.generateMoreBlockMethods(javaCode, sc_id, activityName);
    }
}

/**
 * Extract project ID from path
 */
private static String extractProjectIdFromPath(String projectPath) {
    if (projectPath == null || projectPath.isEmpty()) {
        return null;
    }
    

    String[] parts = projectPath.split("/");
    for (int i = 0; i < parts.length; i++) {
        if ("projects".equals(parts[i]) && i + 1 < parts.length) {
            return parts[i + 1];
        }
    }
    
    return null;
}
    
    private static boolean hasPermissions(String activityName) {

        return false;
    }
    
    private static String mapTagToWidgetType(String tag) {
        if (tag == null) return "View";
        if (tag.contains(".")) {
            return tag.substring(tag.lastIndexOf('.') + 1);
        }
        return tag;
    }
    
    private static void addLifecycleEvent(StringBuilder javaCode, String activityName, String event, String projectPath) {
        String eventLogic = ProjectLogicRepository.getBlockLogicForEvent(activityName, event);
        StringBuilder formattedLogic = new StringBuilder();
        
        if (eventLogic != null && !eventLogic.isEmpty()) {
            formattedLogic.append("        ").append(eventLogic.replace("\n", "\n        "));
        }
        
        switch (event) {
            case "onCreate":
            javaCode.append("    @Override\n");
            javaCode.append("    protected void onCreate(Bundle savedInstanceState) {\n");
            javaCode.append("        super.onCreate(savedInstanceState);\n");
            javaCode.append("        setContentView(R.layout.activity_").append(activityName.toLowerCase()).append(");\n");
            javaCode.append("        initViews();\n");
            javaCode.append(formattedLogic.toString()).append("\n");
            javaCode.append("    }\n\n");
            break;
            
            case "onStart":
            javaCode.append("    @Override\n");
            javaCode.append("    protected void onStart() {\n");
            javaCode.append("        super.onStart();\n");
            javaCode.append(formattedLogic.toString()).append("\n");
            javaCode.append("    }\n\n");
            break;
            
            case "onResume":
            javaCode.append("    @Override\n");
            javaCode.append("    protected void onResume() {\n");
            javaCode.append("        super.onResume();\n");
            javaCode.append(formattedLogic.toString()).append("\n");
            javaCode.append("    }\n\n");
            break;
            
            case "onPause":
            javaCode.append("    @Override\n");
            javaCode.append("    protected void onPause() {\n");
            javaCode.append("        super.onPause();\n");
            javaCode.append(formattedLogic.toString()).append("\n");
            javaCode.append("    }\n\n");
            break;
            
            case "onStop":
            javaCode.append("    @Override\n");
            javaCode.append("    protected void onStop() {\n");
            javaCode.append("        super.onStop();\n");
            javaCode.append(formattedLogic.toString()).append("\n");
            javaCode.append("    }\n\n");
            break;
            
            case "onRestart":
            javaCode.append("    @Override\n");
            javaCode.append("    protected void onRestart() {\n");
            javaCode.append("        super.onRestart();\n");
            javaCode.append(formattedLogic.toString()).append("\n");
            javaCode.append("    }\n\n");
            break;
            
            case "onDestroy":
            javaCode.append("    @Override\n");
            javaCode.append("    protected void onDestroy() {\n");
            javaCode.append("        super.onDestroy();\n");
            javaCode.append(formattedLogic.toString()).append("\n");
            javaCode.append("    }\n\n");
            break;
            
            case "onSaveInstanceState":
            javaCode.append("    @Override\n");
            javaCode.append("    protected void onSaveInstanceState(Bundle outState) {\n");
            javaCode.append("        super.onSaveInstanceState(outState);\n");
            javaCode.append(formattedLogic.toString()).append("\n");
            javaCode.append("    }\n\n");
            break;
            
            case "onRestoreInstanceState":
            javaCode.append("    @Override\n");
            javaCode.append("    protected void onRestoreInstanceState(Bundle savedInstanceState) {\n");
            javaCode.append("        super.onRestoreInstanceState(savedInstanceState);\n");
            javaCode.append(formattedLogic.toString()).append("\n");
            javaCode.append("    }\n\n");
            break;
            
            case "onActivityResult":
            javaCode.append("    @Override\n");
            javaCode.append("    protected void onActivityResult(int requestCode, int resultCode, Intent data) {\n");
            javaCode.append("        super.onActivityResult(requestCode, resultCode, data);\n");
            javaCode.append(formattedLogic.toString()).append("\n");
            javaCode.append("    }\n\n");
            break;
            
            case "onCreateOptionsMenu":
            javaCode.append("    @Override\n");
            javaCode.append("    public boolean onCreateOptionsMenu(Menu menu) {\n");
            javaCode.append("        super.onCreateOptionsMenu(menu);\n");
            javaCode.append(formattedLogic.toString()).append("\n");
            javaCode.append("        return true;\n");
            javaCode.append("    }\n\n");
            break;
            
            case "onOptionsItemSelected":
            javaCode.append("    @Override\n");
            javaCode.append("    public boolean onOptionsItemSelected(MenuItem item) {\n");
            javaCode.append("        super.onOptionsItemSelected(item);\n");
            javaCode.append("        int id = item.getItemId();\n");
            javaCode.append(formattedLogic.toString()).append("\n");
            javaCode.append("        return true;\n");
            javaCode.append("    }\n\n");
            break;
            
            case "onBackPressed":
            javaCode.append("    @Override\n");
            javaCode.append("    public void onBackPressed() {\n");
            javaCode.append(formattedLogic.toString()).append("\n");
            javaCode.append("        super.onBackPressed();\n");
            javaCode.append("    }\n\n");
            break;
            
            case "onRequestPermissionsResult":
            javaCode.append("    @Override\n");
            javaCode.append("    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {\n");
            javaCode.append("        super.onRequestPermissionsResult(requestCode, permissions, grantResults);\n");
            javaCode.append(formattedLogic.toString()).append("\n");
            javaCode.append("    }\n\n");
            break;
            
            case "onConfigurationChanged":
            javaCode.append("    @Override\n");
            javaCode.append("    public void onConfigurationChanged(Configuration newConfig) {\n");
            javaCode.append("        super.onConfigurationChanged(newConfig);\n");
            javaCode.append(formattedLogic.toString()).append("\n");
            javaCode.append("    }\n\n");
            break;
            
            case "onUserInteraction":
            javaCode.append("    @Override\n");
            javaCode.append("    public void onUserInteraction() {\n");
            javaCode.append("        super.onUserInteraction();\n");
            javaCode.append(formattedLogic.toString()).append("\n");
            javaCode.append("    }\n\n");
            break;
            
            case "onWindowFocusChanged":
            javaCode.append("    @Override\n");
            javaCode.append("    public void onWindowFocusChanged(boolean hasFocus) {\n");
            javaCode.append("        super.onWindowFocusChanged(hasFocus);\n");
            javaCode.append(formattedLogic.toString()).append("\n");
            javaCode.append("    }\n\n");
            break;
            
            case "onTrimMemory":
            javaCode.append("    @Override\n");
            javaCode.append("    public void onTrimMemory(int level) {\n");
            javaCode.append("        super.onTrimMemory(level);\n");
            javaCode.append(formattedLogic.toString()).append("\n");
            javaCode.append("    }\n\n");
            break;
            
            case "onLowMemory":
            javaCode.append("    @Override\n");
            javaCode.append("    public void onLowMemory() {\n");
            javaCode.append("        super.onLowMemory();\n");
            javaCode.append(formattedLogic.toString()).append("\n");
            javaCode.append("    }\n\n");
            break;
            
            case "onPostCreate":
            javaCode.append("    @Override\n");
            javaCode.append("    protected void onPostCreate(Bundle savedInstanceState) {\n");
            javaCode.append("        super.onPostCreate(savedInstanceState);\n");
            javaCode.append(formattedLogic.toString()).append("\n");
            javaCode.append("    }\n\n");
            break;
            
            case "onPostResume":
            javaCode.append("    @Override\n");
            javaCode.append("    protected void onPostResume() {\n");
            javaCode.append("        super.onPostResume();\n");
            javaCode.append(formattedLogic.toString()).append("\n");
            javaCode.append("    }\n\n");
            break;
            
            case "onContentChanged":
            javaCode.append("    @Override\n");
            javaCode.append("    public void onContentChanged() {\n");
            javaCode.append("        super.onContentChanged();\n");
            javaCode.append(formattedLogic.toString()).append("\n");
            javaCode.append("    }\n\n");
            break;
            
            default:
            System.out.println("Unknown event: " + event);
            break;
        }
    }
}
