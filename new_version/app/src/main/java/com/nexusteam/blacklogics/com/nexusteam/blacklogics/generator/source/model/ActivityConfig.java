package com.nexusteam.blacklogics.generator.source.model;

import java.util.*;

public class ActivityConfig {
    private String activityName;
    private ActivityType type;
    private String layoutName;
    private Map<String, Object> attributes;
    private boolean isLauncher = false;
    private String parentActivity;
    private List<String> lifecycleMethods = new ArrayList<>();
    private DialogConfig dialogConfig;
    private String packageName;
    private long createdAt;
    private long updatedAt;
    
    public ActivityConfig() {
        this.attributes = new HashMap<>();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
    }
    
    public ActivityConfig(String activityName, ActivityType type, String layoutName) {
        this();
        this.activityName = activityName;
        this.type = type;
        this.layoutName = layoutName;
    }
    

    public String getActivityName() { return activityName; }
    public void setActivityName(String activityName) { 
        this.activityName = activityName; 
        this.updatedAt = System.currentTimeMillis();
    }
    
    public ActivityType getType() { return type; }
    public void setType(ActivityType type) { 
        this.type = type; 
        this.updatedAt = System.currentTimeMillis();
    }
    
    public String getLayoutName() { return layoutName; }
    public void setLayoutName(String layoutName) { 
        this.layoutName = layoutName; 
        this.updatedAt = System.currentTimeMillis();
    }
    
    public Map<String, Object> getAttributes() { return attributes; }
    public void setAttributes(Map<String, Object> attributes) { 
        this.attributes = attributes; 
        this.updatedAt = System.currentTimeMillis();
    }
    public void addAttribute(String key, Object value) { 
        attributes.put(key, value); 
        this.updatedAt = System.currentTimeMillis();
    }
    
    public boolean isLauncher() { return isLauncher; }
    public void setLauncher(boolean launcher) { 
        isLauncher = launcher; 
        this.updatedAt = System.currentTimeMillis();
    }
    
    public String getParentActivity() { return parentActivity; }
    public void setParentActivity(String parentActivity) { 
        this.parentActivity = parentActivity; 
        this.updatedAt = System.currentTimeMillis();
    }
    
    public List<String> getLifecycleMethods() { return lifecycleMethods; }
    public void setLifecycleMethods(List<String> lifecycleMethods) { 
        this.lifecycleMethods = lifecycleMethods; 
        this.updatedAt = System.currentTimeMillis();
    }
    public void addLifecycleMethod(String method) { 
        lifecycleMethods.add(method); 
        this.updatedAt = System.currentTimeMillis();
    }
    
    public DialogConfig getDialogConfig() { return dialogConfig; }
    public void setDialogConfig(DialogConfig dialogConfig) { 
        this.dialogConfig = dialogConfig; 
        this.updatedAt = System.currentTimeMillis();
    }
    
    public String getPackageName() { return packageName; }
    public void setPackageName(String packageName) { 
        this.packageName = packageName; 
        this.updatedAt = System.currentTimeMillis();
    }
    
    public long getCreatedAt() { return createdAt; }
    public long getUpdatedAt() { return updatedAt; }
    
    public String getImportStatement() {
        return type.getImportPath().isEmpty() ? "" : "import " + type.getImportPath() + ";\n";
    }
    
    public String getClassDeclaration() {
        if (type == ActivityType.CUSTOM_ACTIVITY) {
            Object decl = attributes.get("customDeclaration");
            return decl != null ? (String) decl : "extends Activity";
        }
        return type.getClassDeclaration();
    }
    
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("activityName", activityName);
        map.put("type", type.name());
        map.put("layoutName", layoutName);
        map.put("attributes", attributes);
        map.put("isLauncher", isLauncher);
        map.put("parentActivity", parentActivity);
        map.put("lifecycleMethods", lifecycleMethods);
        map.put("dialogConfig", dialogConfig != null ? dialogConfig.toMap() : null);
        map.put("packageName", packageName);
        map.put("createdAt", createdAt);
        map.put("updatedAt", updatedAt);
        return map;
    }
    
    @SuppressWarnings("unchecked")
    public static ActivityConfig fromMap(Map<String, Object> map) {
        ActivityConfig config = new ActivityConfig();
        config.activityName = (String) map.get("activityName");
        config.type = ActivityType.valueOf((String) map.get("type"));
        config.layoutName = (String) map.get("layoutName");
        

        if (map.containsKey("attributes")) {
            config.attributes = (Map<String, Object>) map.get("attributes");
        }
        
        if (map.containsKey("isLauncher")) {
            config.isLauncher = (boolean) map.get("isLauncher");
        }
        
        config.parentActivity = (String) map.get("parentActivity");
        
        if (map.containsKey("lifecycleMethods")) {
            config.lifecycleMethods = (List<String>) map.get("lifecycleMethods");
        }
        
        Map<String, Object> dialogMap = (Map<String, Object>) map.get("dialogConfig");
        if (dialogMap != null) {
            config.dialogConfig = DialogConfig.fromMap(dialogMap);
        }
        
        config.packageName = (String) map.get("packageName");
        
        if (map.containsKey("createdAt")) {
            config.createdAt = (long) map.get("createdAt");
        }
        
        if (map.containsKey("updatedAt")) {
            config.updatedAt = (long) map.get("updatedAt");
        }
        
        return config;
    }
}