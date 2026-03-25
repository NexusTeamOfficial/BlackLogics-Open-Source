package com.nexusteam.internal.manager;

import com.nexusteam.internal.beans.ComponentBean;
import com.nexusteam.blacklogics.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComponentManager {
    private static ComponentManager instance;
    private List<ComponentBean> defaultComponents;
    private List<ComponentBean> customComponents;
    private static final String COMPONENT_JSON_PATH = "/storage/emulated/0/.blacklogics/system/component.json";
    private Gson gson;

    private ComponentManager() {
        defaultComponents = new ArrayList<ComponentBean>();
        customComponents = new ArrayList<ComponentBean>();
        gson = new Gson();
        loadDefaultComponents();
    }

    public static ComponentManager getInstance() {
        if (instance == null) {
            instance = new ComponentManager();
        }
        return instance;
    }

    private void loadDefaultComponents() {
        defaultComponents.clear();

        defaultComponents.add(createDefaultComponent(1, "Intent", "Intent component for navigation", R.drawable.widget_intent));
        defaultComponents.add(createDefaultComponent(2, "SharedPreferences", "Store data locally", R.drawable.widget_shared_preference));
        defaultComponents.add(createDefaultComponent(3, "Calendar", "Calendar events", R.drawable.widget_calendar));
        defaultComponents.add(createDefaultComponent(4, "Vibrator", "Vibration control", R.drawable.widget_vibrator));
        defaultComponents.add(createDefaultComponent(5, "Timer", "Timer tasks", R.drawable.widget_timer));
        defaultComponents.add(createDefaultComponent(6, "Firebase DB", "Firebase database", R.drawable.widget_firebase));
        defaultComponents.add(createDefaultComponent(7, "Dialog", "Alert dialogs", R.drawable.widget_alertdialog));
        defaultComponents.add(createDefaultComponent(8, "MediaPlayer", "Play audio/video", R.drawable.widget_mediaplayer));
        defaultComponents.add(createDefaultComponent(9, "SoundPool", "Sound effects", R.drawable.widget_soundpool));
        defaultComponents.add(createDefaultComponent(10, "ObjectAnimator", "Animate objects", R.drawable.widget_objectanimator));
        defaultComponents.add(createDefaultComponent(11, "Gyroscope", "Gyroscope sensor", R.drawable.widget_gyroscope));
        defaultComponents.add(createDefaultComponent(12, "Firebase Auth", "Firebase authentication", R.drawable.widget_firebase));
        defaultComponents.add(createDefaultComponent(13, "Interstitial Ad", "Full screen ads", R.drawable.widget_admob));
        defaultComponents.add(createDefaultComponent(14, "Firebase Storage", "Firebase storage", R.drawable.widget_firebase));
        defaultComponents.add(createDefaultComponent(15, "Camera", "Camera capture", R.drawable.widget_camera));
        defaultComponents.add(createDefaultComponent(16, "FilePicker", "Pick files", R.drawable.widget_file));
        defaultComponents.add(createDefaultComponent(17, "RequestNetwork", "Network requests", R.drawable.widget_network_request));
        defaultComponents.add(createDefaultComponent(18, "TextToSpeech", "Text to speech", R.drawable.widget_text_to_speech));
        defaultComponents.add(createDefaultComponent(19, "SpeechToText", "Speech to text", R.drawable.widget_speech_to_text));
        defaultComponents.add(createDefaultComponent(20, "BluetoothConnect", "Bluetooth connection", R.drawable.widget_bluetooth));
        defaultComponents.add(createDefaultComponent(21, "LocationManager", "Location services", R.drawable.widget_location));
    }

    private ComponentBean createDefaultComponent(int type, String name, String description, int iconRes) {
        ComponentBean component = new ComponentBean(type, String.valueOf(type), name, description, "");
        component.name = name;
        component.description = description;
        component.icon = String.valueOf(iconRes);
        return component;
    }

    public void loadCustomComponentsFromJson() {
        customComponents.clear();

        File jsonFile = new File(COMPONENT_JSON_PATH);
        if (!jsonFile.exists()) {
            return;
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(jsonFile), "UTF-8"));
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(reader).getAsJsonArray();

            int baseType = 100;

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject obj = jsonArray.get(i).getAsJsonObject();

                ComponentBean component = new ComponentBean();
                component.type = baseType + i;
                component.componentId = getJsonString(obj, "id", String.valueOf(component.type));
                component.name = getJsonString(obj, "name", "Unknown");
                component.param1 = component.name;
                component.description = getJsonString(obj, "description", "");
                component.param2 = component.description;
                component.imports = getJsonString(obj, "imports", "");
                component.additionalVar = getJsonString(obj, "additionalVar", "");
                component.defineAdditionalVar = getJsonString(obj, "defineAdditionalVar", "");
                component.icon = getJsonString(obj, "icon", "2131165882");
                component.typeName = getJsonString(obj, "typeName", "");
                component.className = getJsonString(obj, "class", "");
                component.buildClass = getJsonString(obj, "buildClass", "");
                component.url = getJsonString(obj, "url", "");

                // SAFELY HANDLE PROPERTIES
                if (obj.has("properties") && !obj.get("properties").isJsonNull()) {
                    try {
                        JsonObject propertiesObj = obj.getAsJsonObject("properties");
                        Map<String, Object> properties = parseJsonObject(propertiesObj);

                        try {
                            java.lang.reflect.Field field = ComponentBean.class.getDeclaredField("properties");
                            field.setAccessible(true);
                            field.set(component, properties);
                        } catch (NoSuchFieldException e) {
                            // Properties field nahi hai - ignore
                        } catch (IllegalAccessException e) {
                            // Ignore
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                // Combine imports and additionalVar in param3
                StringBuilder param3Builder = new StringBuilder();
                if (component.imports != null && !component.imports.isEmpty()) {
                    param3Builder.append(component.imports);
                }
                param3Builder.append("||");
                if (component.additionalVar != null && !component.additionalVar.isEmpty()) {
                    param3Builder.append(component.additionalVar);
                }
                component.param3 = param3Builder.toString();

                customComponents.add(component);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // ✅ FIX 1: TypeToken<HashMap> use karo — Map.class nahi
    // Map.class se Gson LinkedTreeMap deta tha jo ClassCastException cause karta tha
    private Map<String, Object> parseJsonObject(JsonObject jsonObj) {
        Map<String, Object> result = new HashMap<String, Object>();

        if (jsonObj == null) {
            return result;
        }

        // ✅ TypeToken<HashMap<String, Object>> se proper HashMap milega
        Type type = new TypeToken<HashMap<String, Object>>(){}.getType();
        Map<String, Object> tempMap = gson.fromJson(jsonObj, type);

        convertMap(tempMap, result);

        return result;
    }

    // ✅ FIX 2: instanceof LinkedTreeMap ki jagah instanceof Map use karo
    // LinkedTreeMap internal class hai — Map safe aur future-proof hai
    private void convertMap(Map<String, Object> source, Map<String, Object> target) {
        if (source == null) return;

        for (Map.Entry<String, Object> entry : source.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Map) {
                // ✅ LinkedTreeMap ya koi bhi Map — sab handle hoga
                Map<String, Object> nestedMap = (Map<String, Object>) value;
                Map<String, Object> convertedNested = new HashMap<String, Object>();
                convertMap(nestedMap, convertedNested);
                target.put(key, convertedNested);
            } else if (value instanceof List) {
                List<Object> list = (List<Object>) value;
                List<Object> convertedList = new ArrayList<Object>();

                for (Object item : list) {
                    if (item instanceof Map) {
                        // ✅ List ke andar bhi Map check karo
                        Map<String, Object> nestedMap = (Map<String, Object>) item;
                        Map<String, Object> convertedItem = new HashMap<String, Object>();
                        convertMap(nestedMap, convertedItem);
                        convertedList.add(convertedItem);
                    } else {
                        convertedList.add(item);
                    }
                }
                target.put(key, convertedList);
            } else {
                target.put(key, value);
            }
        }
    }

    private String getJsonString(JsonObject obj, String key, String defaultValue) {
        if (obj.has(key) && !obj.get(key).isJsonNull()) {
            return obj.get(key).getAsString();
        }
        return defaultValue;
    }

    public List<ComponentBean> getAllComponents() {
        List<ComponentBean> combined = new ArrayList<ComponentBean>();
        combined.addAll(defaultComponents);
        combined.addAll(customComponents);
        return combined;
    }

    public List<ComponentBean> getDefaultComponents() {
        return new ArrayList<ComponentBean>(defaultComponents);
    }

    public List<ComponentBean> getCustomComponents() {
        List<ComponentBean> safeCopy = new ArrayList<ComponentBean>();
        for (ComponentBean original : customComponents) {
            safeCopy.add(createSafeCopy(original));
        }
        return safeCopy;
    }

    private ComponentBean createSafeCopy(ComponentBean original) {
        ComponentBean copy = new ComponentBean();

        copy.type = original.type;
        copy.componentId = original.componentId;
        copy.name = original.name;
        copy.param1 = original.param1;
        copy.description = original.description;
        copy.param2 = original.param2;
        copy.imports = original.imports;
        copy.additionalVar = original.additionalVar;
        copy.defineAdditionalVar = original.defineAdditionalVar;
        copy.icon = original.icon;
        copy.typeName = original.typeName;
        copy.className = original.className;
        copy.buildClass = original.buildClass;
        copy.url = original.url;
        copy.param3 = original.param3;

        try {
            java.lang.reflect.Field field = ComponentBean.class.getDeclaredField("properties");
            field.setAccessible(true);
            Object originalProps = field.get(original);
            if (originalProps instanceof Map) {
                Map<String, Object> safeProps = new HashMap<String, Object>();
                copyMap((Map<String, Object>) originalProps, safeProps);
                field.set(copy, safeProps);
            }
        } catch (Exception e) {
            // Properties field nahi hai ya error aaya — ignore
        }

        return copy;
    }

    private void copyMap(Map<String, Object> source, Map<String, Object> target) {
        if (source == null) return;

        for (Map.Entry<String, Object> entry : source.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Map) {
                Map<String, Object> nestedSource = (Map<String, Object>) value;
                Map<String, Object> nestedTarget = new HashMap<String, Object>();
                copyMap(nestedSource, nestedTarget);
                target.put(key, nestedTarget);
            } else if (value instanceof List) {
                List<Object> listSource = (List<Object>) value;
                List<Object> listTarget = new ArrayList<Object>();
                for (Object item : listSource) {
                    if (item instanceof Map) {
                        Map<String, Object> nestedSource = (Map<String, Object>) item;
                        Map<String, Object> nestedTarget = new HashMap<String, Object>();
                        copyMap(nestedSource, nestedTarget);
                        listTarget.add(nestedTarget);
                    } else {
                        listTarget.add(item);
                    }
                }
                target.put(key, listTarget);
            } else {
                target.put(key, value);
            }
        }
    }

    public ComponentBean getComponentByType(int type) {
        if (type <= 21) {
            for (ComponentBean component : defaultComponents) {
                if (component.type == type) {
                    return component;
                }
            }
        } else {
            for (ComponentBean component : customComponents) {
                if (component.type == type) {
                    return createSafeCopy(component);
                }
            }
        }
        return null;
    }

    public ComponentBean getComponentById(String componentId) {
        for (ComponentBean component : defaultComponents) {
            if (componentId.equals(component.componentId)) {
                return component;
            }
        }
        for (ComponentBean component : customComponents) {
            if (componentId.equals(component.componentId)) {
                return createSafeCopy(component);
            }
        }
        return null;
    }

    public void refreshCustomComponents() {
        loadCustomComponentsFromJson();
    }
}
