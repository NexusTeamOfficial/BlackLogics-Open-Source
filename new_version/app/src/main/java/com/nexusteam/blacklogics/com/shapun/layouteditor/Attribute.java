package com.shapun.layouteditor;

public class Attribute {
    String mAttributeName;
    String mValue;
    String mPreviewTag;      // Visual editor ke liye original tag
    String mConvertedTag;     // XML generation ke liye converted tag
    
    // ✅ OLD CONSTRUCTOR - 2 parameters (Backward compatible)
    public Attribute(String attrName, String value){
        this.mAttributeName = attrName;
        this.mValue = value;
        this.mPreviewTag = null;
        this.mConvertedTag = null;
    }
    
    // ✅ NEW CONSTRUCTOR - 4 parameters
    public Attribute(String attrName, String value, String previewTag, String convertedTag){
        this.mAttributeName = attrName;
        this.mValue = value;
        this.mPreviewTag = previewTag;
        this.mConvertedTag = convertedTag;
    }
    
    public void setValue(String value){
        mValue = value;
    }
    
    public String getValue(){
        return mValue;
    }
    
    public String getName(){
        return mAttributeName;
    }
    
    // Visual editor ke liye - original tag do
    public String getPreviewTag() {
        return mPreviewTag != null ? mPreviewTag : mConvertedTag;
    }
    
    // XML generation ke liye - converted tag do
    public String getConvertedTag() {
        return mConvertedTag != null ? mConvertedTag : mPreviewTag;
    }
    
    // Conversion info set karo
    public void setConversionInfo(String previewTag, String convertedTag) {
        this.mPreviewTag = previewTag;
        this.mConvertedTag = convertedTag;
    }
    
    // Check if this is a converted widget
    public boolean isConverted() {
        return mPreviewTag != null && mConvertedTag != null 
            && !mPreviewTag.equals(mConvertedTag);
    }

    @Override
    public String toString(){
        return mAttributeName + "=\"" + mValue + "\"";
    }
    
    @Override
    public boolean equals(Object obj){
        if (obj == this) return true;
        if (obj == null || obj.getClass() != getClass()) return false;
        
        Attribute other = (Attribute) obj;
        if(!getName().equals(other.getName())) return false;
        if(!getValue().equals(other.getValue())) return false;
        
        // Tags bhi compare karo (optional - backward compatibility ke liye null check)
        if(mPreviewTag != null ? !mPreviewTag.equals(other.mPreviewTag) : other.mPreviewTag != null) 
            return false;
        if(mConvertedTag != null ? !mConvertedTag.equals(other.mConvertedTag) : other.mConvertedTag != null) 
            return false;
            
        return true;
    }
}