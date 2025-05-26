package com.shapun.layouteditor.utils;

public class AttributeUtils{
	
	public static String getImageName(String str){
		return str.substring(str.indexOf("/")+1,str.length());
		
	}
	
	static public String getName(String str){
		return str.substring(str.indexOf("/")+1,str.length());
	}
	
	static public boolean getBoolean(String str){
		return str.equalsIgnoreCase("true")?true:false;
	}
	
	
}
