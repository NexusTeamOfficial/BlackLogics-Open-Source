package com.nexusteam.blacklogics.sdk.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WidgetBlueprint implements Serializable {
    private String blueprintId;
    private String displayName;
    private String widgetClass;
    private String widgetType;
    private long creationTimestamp;
    private long lastModifiedTimestamp;
    private String thumbnailPath;
    private String description;
    private List<String> tags;
    private int usageCount;
    private boolean isFavorite;
    
    private Map<String, String> attributes;
    private Map<String, String> layoutParams;
    private List<WidgetBlueprint> childBlueprints;
    private String parentBlueprintId;
    
    public WidgetBlueprint() {
        this.blueprintId = generateBlueprintId();
        this.creationTimestamp = System.currentTimeMillis();
        this.lastModifiedTimestamp = this.creationTimestamp;
        this.attributes = new HashMap<>();
        this.layoutParams = new HashMap<>();
        this.childBlueprints = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.usageCount = 0;
        this.isFavorite = false;
    }
    
    private String generateBlueprintId() {
        return "WIDGET_" + System.currentTimeMillis() + "_" + 
               Integer.toHexString(hashCode());
    }
    
    public String getBlueprintId() {
        return blueprintId;
    }
    
    public void setBlueprintId(String blueprintId) {
        this.blueprintId = blueprintId;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    public String getWidgetClass() {
        return widgetClass;
    }
    
    public void setWidgetClass(String widgetClass) {
        this.widgetClass = widgetClass;
        if (widgetClass != null) {
            int lastDot = widgetClass.lastIndexOf('.');
            if (lastDot != -1 && lastDot < widgetClass.length() - 1) {
                this.widgetType = widgetClass.substring(lastDot + 1);
            } else {
                this.widgetType = widgetClass;
            }
        }
    }
    
    public String getWidgetType() {
        return widgetType;
    }
    
    public long getCreationTimestamp() {
        return creationTimestamp;
    }
    
    public long getLastModifiedTimestamp() {
        return lastModifiedTimestamp;
    }
    
    public void setLastModifiedTimestamp(long lastModifiedTimestamp) {
        this.lastModifiedTimestamp = lastModifiedTimestamp;
    }
    
    public String getThumbnailPath() {
        return thumbnailPath;
    }
    
    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public List<String> getTags() {
        return tags;
    }
    
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    
    public void addTag(String tag) {
        if (!this.tags.contains(tag)) {
            this.tags.add(tag);
        }
    }
    
    public int getUsageCount() {
        return usageCount;
    }
    
    public void incrementUsageCount() {
        this.usageCount++;
        this.lastModifiedTimestamp = System.currentTimeMillis();
    }
    
    public boolean isFavorite() {
        return isFavorite;
    }
    
    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
    
    public Map<String, String> getAttributes() {
        return attributes;
    }
    
    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
    
    public void addAttribute(String key, String value) {
        this.attributes.put(key, value);
    }
    
    public Map<String, String> getLayoutParams() {
        return layoutParams;
    }
    
    public void setLayoutParams(Map<String, String> layoutParams) {
        this.layoutParams = layoutParams;
    }
    
    public void addLayoutParam(String key, String value) {
        this.layoutParams.put(key, value);
    }
    
    public List<WidgetBlueprint> getChildBlueprints() {
        return childBlueprints;
    }
    
    public void setChildBlueprints(List<WidgetBlueprint> childBlueprints) {
        this.childBlueprints = childBlueprints;
    }
    
    public void addChildBlueprint(WidgetBlueprint child) {
        child.setParentBlueprintId(this.blueprintId);
        this.childBlueprints.add(child);
    }
    
    public String getParentBlueprintId() {
        return parentBlueprintId;
    }
    
    public void setParentBlueprintId(String parentBlueprintId) {
        this.parentBlueprintId = parentBlueprintId;
    }
    
    public boolean hasChildren() {
        return childBlueprints != null && !childBlueprints.isEmpty();
    }
    
    @Override
    public String toString() {
        return displayName != null ? displayName : widgetType;
    }
}