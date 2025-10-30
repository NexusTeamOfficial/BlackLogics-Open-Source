package com.shapun.layouteditor.utils;

import android.util.TypedValue;
import android.content.Context;

public class DimensionUtils {
	
	static String[] mDimensions = {"px","dp","dip","sp", "pt","in","mm" };
	
	
	public static String getDimension(final String value){
		
		for(String s : mDimensions){
			if(value.endsWith(s)) return s;
		}
		return null;
	}
	
	public static float getValueWithoutDimension(String value){
		for(String s: mDimensions){
			value = value.replaceAll(s,"");
		}
		return Float.parseFloat(value);
	}
	
	public static int getValueInPx(Context c,String attributeValue){
		return getValueInPx(c,getDimension(attributeValue),getValueWithoutDimension(attributeValue));
	} 
	
	
	public static int getValueInPx(Context context,String dimension,float value){
		int dimen = 0;
		
		switch(dimension){
			case "px":
			dimen =TypedValue.COMPLEX_UNIT_PX;
			break;
			
			case "dp":
			dimen =TypedValue.COMPLEX_UNIT_DIP;
			break;
			
			case "dip":
			dimen =TypedValue.COMPLEX_UNIT_DIP;
			break;
			
			case "sp":
			dimen =TypedValue.COMPLEX_UNIT_SP;
			break;
			
			case "pt":
			dimen =TypedValue.COMPLEX_UNIT_PT;
			break;
			
			case "in":
			dimen =TypedValue.COMPLEX_UNIT_IN;
			break;
			
			case "mm":
			dimen =TypedValue.COMPLEX_UNIT_MM;
			break;
			
			default :
			dimen =TypedValue.COMPLEX_UNIT_PX;
			break;
			
		}
		
		
		return(int) TypedValue.applyDimension(dimen, value, context.getResources().getDisplayMetrics());
		
	}
	
	
	
	
	
	
}
