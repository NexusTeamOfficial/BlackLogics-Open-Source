package com.shapun.layouteditor;

public class Attribute {
	String mAttributeName;
	String mValue;
	
	
	public Attribute(String attrName, String value){
		mAttributeName = attrName;
		mValue = value;
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
	
	// ToDo escape mValue
	@Override
	public String toString(){
		return mAttributeName+"=\""+mValue+"\"";
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj == this) return true;
		if (obj == null || obj.getClass() != getClass()) return false;
		
		if(getName().equals(((Attribute)obj).getName()) && getValue().equals(((Attribute)obj).getValue()) ) return true;
		return false;
	}
	
}
