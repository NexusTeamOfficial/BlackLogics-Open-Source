package com.nexusteam.blacklogics.sdk.layouteditor;

import android.view.View;

import com.nexusteam.blacklogics.sdk.AttributeSet;

import java.util.ArrayList;
import java.util.HashMap;

public class AttributeStore {

	private final HashMap<View, AttributeSet> attributesValueMap = new HashMap<View, AttributeSet>();
	private HashMap<View, AttributeSet> oldAttributesValueMap = new HashMap<View, AttributeSet>();

	private HashMap<String, ArrayList<HashMap<String, Object>>> attributesMap =
	new HashMap<String, ArrayList<HashMap<String, Object>>>();
	private HashMap<String, ArrayList<HashMap<String, Object>>> parentAttributesMap =
	new HashMap<String, ArrayList<HashMap<String, Object>>>();

	public void loadSchemas(HashMap<String, ArrayList<HashMap<String, Object>>> attributesMap,
	HashMap<String, ArrayList<HashMap<String, Object>>> parentAttributesMap) {
		this.attributesMap = attributesMap;
		this.parentAttributesMap = parentAttributesMap;
	}

	public AttributeSet get(View view) {
		return attributesValueMap.get(view);
	}

	public void put(View view, AttributeSet set) {
		attributesValueMap.put(view, set);
	}

	public void remove(View view) {
		if (view == null) return;
		attributesValueMap.remove(view);
	}

	public void remove(View view, boolean recursive) {
		if (view == null) return;
		attributesValueMap.remove(view);
		if (recursive && view instanceof android.view.ViewGroup) {
			android.view.ViewGroup group = (android.view.ViewGroup) view;
			int count = group.getChildCount();
			for (int i = 0; i < count; i++) {
				remove(group.getChildAt(i), true);
			}
		}
	}

	public boolean contains(View view) {
		return attributesValueMap.containsKey(view);
	}

	public java.util.Set<View> keySet() {
		return attributesValueMap.keySet();
	}

	public void clear() {
		attributesValueMap.clear();
	}

	public HashMap<View, AttributeSet> snapshot() {
		return new HashMap<View, AttributeSet>(attributesValueMap);
	}

	public void restoreFrom(HashMap<View, AttributeSet> snapshot) {
		attributesValueMap.clear();
		attributesValueMap.putAll(snapshot);
	}

	public HashMap<View, AttributeSet> backupAndSwapOld() {
		HashMap<View, AttributeSet> old = new HashMap<View, AttributeSet>(attributesValueMap);
		oldAttributesValueMap = old;
		return old;
	}

	public HashMap<View, AttributeSet> getOldAttributesValueMap() {
		return oldAttributesValueMap;
	}

	public HashMap<View, AttributeSet> backingMap() {
		return attributesValueMap;
	}

	public AttributeSet getOrCreate(View view) {
		AttributeSet set = attributesValueMap.get(view);
		if (set == null) {
			set = new AttributeSet();
			attributesValueMap.put(view, set);
		}
		return set;
	}

	public boolean hasCustomViewSet(View view) {
		AttributeSet set = attributesValueMap.get(view);
		if (set == null) return false;
		Attribute cv = set.getAttribute("app:customView");
		if (cv == null || cv.getValue() == null) return false;
		String v = cv.getValue().trim();
		return !v.isEmpty() && !"none".equalsIgnoreCase(v);
	}

	public void buildAttributeListForView(View view,
	ArrayList<HashMap<String, Object>> attributesList) {
		Class<?> cls = view.getClass();
		Class<?> stop = View.class.getSuperclass();
		while (cls != stop) {
			ArrayList<HashMap<String, Object>> temp = attributesMap.get(cls.getName());
			if (temp != null) attributesList.addAll(0, temp);
			cls = cls.getSuperclass();
		}
		if (view.getParent() instanceof android.view.ViewGroup) {
			cls = view.getParent().getClass();
			while (cls != stop) {
				ArrayList<HashMap<String, Object>> temp =
				parentAttributesMap.get(cls.getName());
				if (temp != null) attributesList.addAll(temp);
				cls = cls.getSuperclass();
			}
		}
	}

	public HashMap<String, Object> findAttributeMap(String attrName, View view) {
		Class<?> cls = view.getClass();
		while (cls != null && !cls.equals(View.class.getSuperclass())) {
			ArrayList<HashMap<String, Object>> list = attributesMap.get(cls.getName());
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					HashMap<String, Object> map = list.get(i);
					if (attrName.equals(map.get("attribute_name"))) return map;
				}
			}
			cls = cls.getSuperclass();
		}
		return null;
	}

	public ArrayList<HashMap<String, Object>> findAttributesListFor(Class<?> viewClass) {
		ArrayList<HashMap<String, Object>> listMap = new ArrayList<HashMap<String, Object>>();
		Class<?> cls = viewClass;
		Class<?> viewRootClass = View.class.getSuperclass();
		while (cls != viewRootClass) {
			ArrayList<HashMap<String, Object>> temp = attributesMap.get(cls.getName());
			if (temp != null) listMap.addAll(0, temp);
			cls = cls.getSuperclass();
		}
		return listMap;
	}
}
