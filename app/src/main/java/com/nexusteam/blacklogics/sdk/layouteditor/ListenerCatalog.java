package com.nexusteam.blacklogics.sdk.layouteditor;

import android.content.Context;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nexusteam.blacklogics.sdk.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ListenerCatalog {

	public interface CustomViewCheck {
		boolean hasCustomViewSet(View view);
	}

	private static final String VIEWS_JSON_ASSET = "views.json";

	private final CustomViewCheck customViewCheck;
	private final List<Map<String, Object>> widgetDefinitions;

	public ListenerCatalog(CustomViewCheck customViewCheck, Context context) {
		this.customViewCheck = customViewCheck;
		this.widgetDefinitions = loadWidgetDefinitions(context);
	}

	public ListenerCatalog(CustomViewCheck customViewCheck) {
		this(customViewCheck, null);
	}

	@SuppressWarnings("unchecked")
	private static List<Map<String, Object>> loadWidgetDefinitions(Context context) {
		if (context == null) {
			return new ArrayList<Map<String, Object>>();
		}
		try {
			String json = Utils.readFromAsset(context, VIEWS_JSON_ASSET);
			Gson gson = new Gson();
			List<Map<String, Object>> defs = gson.fromJson(json,
			new TypeToken<ArrayList<Map<String, Object>>>() {}.getType());
			return defs != null ? defs : new ArrayList<Map<String, Object>>();
		} catch (Exception e) {
			return new ArrayList<Map<String, Object>>();
		}
	}

	public ArrayList<HashMap<String, Object>> buildListenersForView(View view) {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		list.add(createListenerEntry("onClick", "View.OnClickListener"));
		list.add(createListenerEntry("onLongPress", "View.OnLongPressListener"));
		list.add(createListenerEntry("onTouch", "View.OnTouchListener"));
		list.add(createListenerEntry("onFocusChange", "View.OnFocusChangeListener"));

		if (view != null) {
			Object tag = view.getTag();
			boolean isFab = "_fab".equals(tag)
					|| "fab".equals(String.valueOf(tag))
					|| (view.getClass() != null
						&& view.getClass().getName() != null
						&& view.getClass().getName().contains("FloatingActionButton"));
			if (isFab) {
				ensureListener(list, "onClick", "Click");
				ensureListener(list, "onLongClick", "Long Click");
				ensureListener(list, "onTouch", "Touch");
				ensureListener(list, "onFocusChange", "Focus Change");
				return list;
			}
		}

		Map<String, Object> definition = findDefinitionForView(view);
		if (definition == null) {
			return list;
		}

		Object eventsObj = definition.get("events");
		if (!(eventsObj instanceof List)) {
			return list;
		}

		for (Object entryObj : (List<Object>) eventsObj) {
			if (!(entryObj instanceof Map)) continue;
			Map<String, Object> entry = (Map<String, Object>) entryObj;

			Object nameObj = entry.get("name");
			Object typeObj = entry.get("type");
			if (nameObj == null || typeObj == null) continue;

			String eventName = String.valueOf(nameObj);
			String eventType = String.valueOf(typeObj);

			Object requiresCustomViewObj = entry.get("requiresCustomView");
			boolean requiresCustomView = Boolean.TRUE.equals(requiresCustomViewObj);
			if (requiresCustomView && (customViewCheck == null || !customViewCheck.hasCustomViewSet(view))) {
				continue;
			}

			list.add(createListenerEntry(eventName, eventType));
		}

		return list;
	}

	private Map<String, Object> findDefinitionForView(View view) {
		if (widgetDefinitions.isEmpty()) {
			return null;
		}

		Class<?> klass = view.getClass();
		while (klass != null) {
			String className = klass.getName();
			for (Map<String, Object> def : widgetDefinitions) {
				Object classPathObj = def.get("class_path");
				if (classPathObj != null && className.equals(String.valueOf(classPathObj))) {
					return def;
				}
			}
			klass = klass.getSuperclass();
		}

		String simpleType = view.getClass().getSimpleName().toLowerCase(Locale.ENGLISH);
		Map<String, Object> best = null;
		int bestLen = -1;
		for (Map<String, Object> def : widgetDefinitions) {
			Object nameObj = def.get("name");
			if (nameObj == null) continue;
			String defName = String.valueOf(nameObj).toLowerCase(Locale.ENGLISH);
			if (simpleType.contains(defName) && defName.length() > bestLen) {
				best = def;
				bestLen = defName.length();
			}
		}
		return best;
	}


	private void ensureListener(ArrayList<HashMap<String, Object>> list, String type, String name) {
		if (list == null) return;
		for (HashMap<String, Object> e : list) {
			if (e == null) continue;
			Object t = e.get("type");
			if (t != null && type.equals(String.valueOf(t))) return;
			Object n = e.get("name");
			if (n != null && name.equals(String.valueOf(n))) return;
		}
		list.add(createListenerEntry(name, type));
	}

	private HashMap<String, Object> createListenerEntry(String name, String type) {
		HashMap<String, Object> entry = new HashMap<String, Object>();
		entry.put("name", name);
		entry.put("type", type);
		return entry;
	}
}
