package com.shapun.layouteditor.utils;

import android.view.View;
import android.view.ViewGroup;

public class ViewGroupUtils {
	
	public static ViewGroup getParent(View view) {
		return (ViewGroup)view.getParent();
	}
	
	public static void removeView(View view) {
		ViewGroup parent = getParent(view);
		if(parent != null) {
			parent.removeView(view);
		}
	}
	
	public static void replaceView(View view1, View view2){
		removeView(view2);
		ViewGroup parent = (ViewGroup)view1.getParent();
		int index = parent.indexOfChild(view1);
		parent.removeView(view1);
		parent.addView(view2,index);
		
	}
	
	
	public static void addView(View view,ViewGroup parent){
		removeView(view);
		parent.addView(view);
	}
	
	
	public static void addView(View view, ViewGroup newParent,int pos){
		ViewGroup parent = (ViewGroup)view.getParent();
		
		// Checks if view is already in desired position
		if(parent == newParent){
			if(parent.indexOfChild(view) == pos){
				return;
			}
		}
		removeView(view);
		((ViewGroup)newParent).addView(view,pos);
	}
	
	
	
	
}
