package com.shapun.layouteditor;

import com.shapun.layouteditor.Attribute;
import java.util.ArrayList;
import java.util.HashMap;

public class AttributeSet {
    HashMap<String, Attribute> attrsMap;
    
    public AttributeSet(){
        attrsMap = new HashMap<>();
    }
    
    public void add(Attribute attr){
        attrsMap.put(attr.getName(), attr);
    }
    
    public void remove(String attrName){
        attrsMap.remove(attrName);
    }
    
    public Attribute getAttribute(String attrName){
        return attrsMap.get(attrName);
    }
    
    public ArrayList<Attribute> getAttributes(){
        return new ArrayList<>(attrsMap.values());
    }
    
    // Check if any attribute is converted
    public boolean hasConvertedWidget() {
        for(Attribute attr : attrsMap.values()) {
            if(attr.isConverted()) return true;
        }
        return false;
    }
    
    // Visual editor ke liye - preview tag do
    public String getPreviewTag(String defaultTag) {
        for(Attribute attr : attrsMap.values()) {
            if(attr.isConverted()) {
                return attr.getPreviewTag();
            }
        }
        return defaultTag;
    }
    
    // XML generation ke liye - converted tag do
    public String getConvertedTag(String defaultTag) {
        for(Attribute attr : attrsMap.values()) {
            if(attr.isConverted()) {
                return attr.getConvertedTag();
            }
        }
        return defaultTag;
    }
    
    @Override 
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Attribute attr : attrsMap.values()){
            sb.append(attr.toString());
            sb.append("\n");
        }
        return sb.toString().trim();
    }
}