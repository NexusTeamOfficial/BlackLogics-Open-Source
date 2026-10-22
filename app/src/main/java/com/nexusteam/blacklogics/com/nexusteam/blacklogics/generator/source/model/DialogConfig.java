package com.nexusteam.blacklogics.generator.source.model;

import android.R;
import java.util.HashMap;
import java.util.Map;

public class DialogConfig {
    private int themeResId = R.style.Theme_Material_Light_Dialog;
    private boolean cancelable = true;
    private String title;
    private Float widthRatio = 0.9f;
    private Float heightRatio;
    private Integer customAnimation;
    private boolean fullScreen = false;
    private int style = -1;
    
    public DialogConfig() {}
    

    public int getThemeResId() { return themeResId; }
    public void setThemeResId(int themeResId) { this.themeResId = themeResId; }
    
    public boolean isCancelable() { return cancelable; }
    public void setCancelable(boolean cancelable) { this.cancelable = cancelable; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public Float getWidthRatio() { return widthRatio; }
    public void setWidthRatio(Float widthRatio) { this.widthRatio = widthRatio; }
    
    public Float getHeightRatio() { return heightRatio; }
    public void setHeightRatio(Float heightRatio) { this.heightRatio = heightRatio; }
    
    public Integer getCustomAnimation() { return customAnimation; }
    public void setCustomAnimation(Integer customAnimation) { this.customAnimation = customAnimation; }
    
    public boolean isFullScreen() { return fullScreen; }
    public void setFullScreen(boolean fullScreen) { this.fullScreen = fullScreen; }
    
    public int getStyle() { return style; }
    public void setStyle(int style) { this.style = style; }
    
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("themeResId", themeResId);
        map.put("cancelable", cancelable);
        map.put("title", title);
        map.put("widthRatio", widthRatio);
        map.put("heightRatio", heightRatio);
        map.put("customAnimation", customAnimation);
        map.put("fullScreen", fullScreen);
        map.put("style", style);
        return map;
    }
    
    @SuppressWarnings("unchecked")
    public static DialogConfig fromMap(Map<String, Object> map) {
        DialogConfig config = new DialogConfig();
        

        if (map.containsKey("themeResId")) {
            config.themeResId = (int) map.get("themeResId");
        }
        
        if (map.containsKey("cancelable")) {
            config.cancelable = (boolean) map.get("cancelable");
        }
        
        config.title = (String) map.get("title");
        

        Object widthObj = map.get("widthRatio");
        if (widthObj instanceof Double) {
            config.widthRatio = ((Double) widthObj).floatValue();
        } else {
            config.widthRatio = (Float) widthObj;
        }
        
        Object heightObj = map.get("heightRatio");
        if (heightObj instanceof Double) {
            config.heightRatio = ((Double) heightObj).floatValue();
        } else {
            config.heightRatio = (Float) heightObj;
        }
        
        config.customAnimation = (Integer) map.get("customAnimation");
        
        if (map.containsKey("fullScreen")) {
            config.fullScreen = (boolean) map.get("fullScreen");
        }
        
        if (map.containsKey("style")) {
            config.style = (int) map.get("style");
        }
        
        return config;
    }
}