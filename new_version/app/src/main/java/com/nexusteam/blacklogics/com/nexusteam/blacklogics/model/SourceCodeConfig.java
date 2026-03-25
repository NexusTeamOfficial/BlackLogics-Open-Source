package com.nexusteam.blacklogics.model;

public class SourceCodeConfig {
    public static final String LANGUAGE_JAVA = "java";
    public static final String LANGUAGE_XML = "xml";
    public static final String LANGUAGE_JAVASCRIPT = "javascript";
    public static final String LANGUAGE_JSON = "json";
    
    private int defaultTextSize = 12;
    private boolean defaultEditable = false;
    private boolean defaultWordWrap = false;
    private boolean enableMagnifier = true;
    
    public SourceCodeConfig() {}
    

    public int getDefaultTextSize() { return defaultTextSize; }
    public void setDefaultTextSize(int defaultTextSize) { 
        this.defaultTextSize = defaultTextSize; 
    }
    
    public boolean isDefaultEditable() { return defaultEditable; }
    public void setDefaultEditable(boolean defaultEditable) { 
        this.defaultEditable = defaultEditable; 
    }
    
    public boolean isDefaultWordWrap() { return defaultWordWrap; }
    public void setDefaultWordWrap(boolean defaultWordWrap) { 
        this.defaultWordWrap = defaultWordWrap; 
    }
    
    public boolean isEnableMagnifier() { return enableMagnifier; }
    public void setEnableMagnifier(boolean enableMagnifier) { 
        this.enableMagnifier = enableMagnifier; 
    }
}