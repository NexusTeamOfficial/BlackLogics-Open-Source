package com.shapun.layouteditor;

import java.util.HashMap;
import java.util.ArrayList;
import android.view.View;
import android.view.ViewGroup;

public  class IdManager {
	
	public HashMap<View,String> mViewIdMap ;
	
	IdManager(){
		mViewIdMap= new HashMap<>();
		
	}
	
	public View getView(String id){
		for ( View view : mViewIdMap.keySet() ) {  
			if(mViewIdMap.get(view).equals(id))return view;
		}
		return null;
	}
	
	public View getView(int id){
		for ( View view : mViewIdMap.keySet() ) {  
			if(view.getId()==id)return view;
		}
		return null;
	}
	
	public int getId(String id){
		for ( View view : mViewIdMap.keySet() ) {  
			if(mViewIdMap.get(view).equals(id))return view.getId();
		}
		return 0;
	}
	
	public String generateNewId(View view){
		String name = view.getClass().getSimpleName().toLowerCase();
		int n= 1;
		while(isIdUsed(name+n)){
			n++;
		}
		return name+n;
	}		
	
	public String getId(View view){
		return mViewIdMap.get(view);
	}
	
	
	public void addNewId(View view,String s){
		if(isIdUsed(s))throw new IllegalArgumentException(s+" is already in use"); 
		//if(view.getId() <= 0)
		view.setId(View.generateViewId());
		mViewIdMap.put(view,s);
	}
	
	public boolean isIdUsed(String s){
		
		for (View view :mViewIdMap.keySet() ) {  
			if(mViewIdMap.get(view).equals(s))return true;
		}
		return false;
	}
	
	public void remove(View view){
		mViewIdMap.remove(view);
	}
	
	// if recursive is true it  also remove nested childrens
	public void remove(View view,boolean recursive){
		mViewIdMap.remove(view);
		//remove all childs including recursive if recursive is true 
		if(recursive){
			if(view instanceof ViewGroup){
				for(int i=0;i<((ViewGroup)view).getChildCount();i++){
					remove(((ViewGroup)view).getChildAt(i));
				}
			}
		}
	}
	
	public void updateId(View view,String newName){
		mViewIdMap.put(view,newName);
		
	}
	
	public ArrayList<View> getViews(){
		ArrayList<View> list = new ArrayList<>();
		for (View view :mViewIdMap.keySet() ) {  
			list.add(view);
		}
		return list;
	}
	
	
	public ArrayList<String> getIds(){
		ArrayList<String> list = new ArrayList<>();
		for (View view :mViewIdMap.keySet() ) {  
			list.add(getId(view));
		}
		return list;
	}
	
	
	
}
