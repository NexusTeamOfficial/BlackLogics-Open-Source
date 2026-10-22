package com.nexusteam.blacklogics.generator.source.model;

import java.io.Serializable;

public class BlockParameter implements Serializable {
    private String name;
    private String type;
    private String defaultValue;
    private String displayName;
    
    public BlockParameter() {
    }
    
    public BlockParameter(String name, String type) {
        this.name = name;
        this.type = type;
        setDefaultForType(type);
    }
    
    public BlockParameter(String name, String type, String displayName) {
        this.name = name;
        this.type = type;
        this.displayName = displayName;
        setDefaultForType(type);
    }
    
    private void setDefaultForType(String type) {
        if ("int".equals(type)) {
            this.defaultValue = "0";
        } else if ("boolean".equals(type)) {
            this.defaultValue = "false";
        } else if ("double".equals(type)) {
            this.defaultValue = "0.0";
        } else if ("float".equals(type)) {
            this.defaultValue = "0.0f";
        } else if ("long".equals(type)) {
            this.defaultValue = "0L";
        } else {
            this.defaultValue = "\"\"";
        }
    }
    

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
        setDefaultForType(type);
    }
    
    public String getDefaultValue() {
        return defaultValue;
    }
    
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}