package com.shapun.layouteditor.utils;

import android.view.View;
import android.view.ViewGroup;
import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.GradientDrawable;
import android.content.res.ColorStateList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public  class Utils {
	
	
	public static String readFromAsset(Context context ,String path){
		java.io.BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		try { 
			reader = new BufferedReader( new InputStreamReader(context.getAssets().open(path), "UTF-8"));
			
			String mLine;
			while((mLine = reader.readLine()) != null) { 
				sb.append(mLine);
				sb.append("\n");
			} 
		} catch (IOException e) {
			
		} finally { 
			if (reader != null) { 
				try { 
					reader.close(); 
				} catch (IOException e) { 
					
				} } }
		
		return sb.toString();
	}
	
	
}
