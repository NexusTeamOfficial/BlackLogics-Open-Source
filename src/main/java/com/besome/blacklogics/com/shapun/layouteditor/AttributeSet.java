package com.shapun.layouteditor;

import com.shapun.layouteditor.Attribute;
import java.util.ArrayList;
import java.util.HashMap;

public class AttributeSet {
	HashMap<String,Attribute> attrsMap;
	
	public AttributeSet(){
		attrsMap = new HashMap<>();
	}
	public void add(Attribute attr){
		attrsMap.put(attr.getName(),attr);
	}
	
	public void remove(String attrName){
		attrsMap.remove(attrName);
	}
	
	public Attribute getAttribute(String attrName){
		return attrsMap.get(attrName);
	}
	
	public ArrayList<Attribute> getAttributes(){
		ArrayList<Attribute> list = new ArrayList<>();
		for(String key:attrsMap.keySet())list.add(attrsMap.get(key));
		return list;
	}
	
	@Override 
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(String key:attrsMap.keySet()){
			sb.append(attrsMap.get(key).toString());
			sb.append("\n");
			}
		return sb.toString().trim();
		}
}
