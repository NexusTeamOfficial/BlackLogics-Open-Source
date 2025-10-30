package com.shapun.layouteditor.utils;


public class PrimitiveUtils {
	
	public static boolean isPrimitiveType(Class<?> cls) { 
		if(cls == byte.class)return true;
		if(cls == short.class)return true;
		if(cls == int.class)return true;
		if(cls == long.class)return true;
		if(cls == float.class)return true;
		if(cls == double.class)return true;
		if(cls == char.class) return true;
		if(cls == boolean.class)return true;
		
		return false;
	}
	
	
	
	public static Object getDefaultValue(Class cls){
		if(cls == byte.class)return 0;
		if(cls == short.class)return 0;
		if(cls == int.class)return 0;
		if(cls == long.class)return 0L;
		if(cls == float.class)return 0.0f;
		if(cls == double.class)return 0.0d;
		if(cls == char.class)return '\u0000';
		if(cls == boolean.class)return false;
		return null;
		
	}
	
	
	public static Class<?> getPrimitiveType(Class<?> cls) { 
		
		if(cls == Byte.class)return byte.class;
		if(cls == Short.class)return short.class;
		if(cls == Integer.class)return int.class;
		if(cls == Long.class)return long.class;
		if(cls == Float.class)return float.class;
		if(cls == Double.class)return double.class;
		if(cls == Character.class)return char.class;
		if(cls == Boolean.class)return boolean.class;
		
		return cls;
	}
	
}
