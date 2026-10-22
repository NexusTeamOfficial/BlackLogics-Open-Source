package com.nexusteam.blacklogics.sdk.layouteditor;

public class Attribute {
    String mAttributeName;
    String mValue;
    String mPreviewTag;
    String mConvertedTag;
    
    public Attribute(String attrName, String value){
        this.mAttributeName = attrName;
        this.mValue = value;
        this.mPreviewTag = null;
        this.mConvertedTag = null;
    }
    
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
    
    public String getPreviewTag() {
        return mPreviewTag != null ? mPreviewTag : mConvertedTag;
    }
    
    public String getConvertedTag() {
        return mConvertedTag != null ? mConvertedTag : mPreviewTag;
    }
    
    public void setConversionInfo(String previewTag, String convertedTag) {
        this.mPreviewTag = previewTag;
        this.mConvertedTag = convertedTag;
    }
    
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
        
        if(mPreviewTag != null ? !mPreviewTag.equals(other.mPreviewTag) : other.mPreviewTag != null) 
            return false;
        if(mConvertedTag != null ? !mConvertedTag.equals(other.mConvertedTag) : other.mConvertedTag != null) 
            return false;
            
        return true;
    }

    @Override
    public int hashCode() {
        int result = mAttributeName != null ? mAttributeName.hashCode() : 0;
        result = 31 * result + (mValue != null ? mValue.hashCode() : 0);
        result = 31 * result + (mPreviewTag != null ? mPreviewTag.hashCode() : 0);
        result = 31 * result + (mConvertedTag != null ? mConvertedTag.hashCode() : 0);
        return result;
    }
}