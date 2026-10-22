package com.nexusteam.blacklogics.sdk.layouteditor;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.nexusteam.blacklogics.sdk.AttributeSet;

import org.json.JSONArray;

/**
 * Persists the REAL attribute set of the activity FAB (id "_fab") per project + activity.
 *
 * The FAB lives on the canvas frame (outside the editor layout tree), so it is not part of
 * LayoutPersistence. Without this store every re-open of the editor recreated the FAB with
 * defaults and XmlGenerator emitted hard-coded values instead of what the user edited.
 *
 * Both ViewEditor (live edits) and XmlGenerator (XML output) read/write through here.
 */
public final class FabAttributeStore {

	private static final String TAG = "FabAttributeStore";
	private static final String PREFS = "editor_fab_attrs";

	public static final String FAB_ID = "_fab";
	public static final String FAB_CLASS =
			"com.google.android.material.floatingactionbutton.FloatingActionButton";

	private FabAttributeStore() {}

	private static String key(String scId, String activityName) {
		String n = activityName == null ? "" : activityName.trim();
		if (n.endsWith(".java")) n = n.substring(0, n.length() - 5);
		if (n.endsWith(".xml")) n = n.substring(0, n.length() - 4);
		return "fab_" + (scId == null ? "" : scId.trim()) + "_" + n;
	}

	/** Defaults a freshly enabled FAB starts with (same values the XML used to hard-code). */
	public static AttributeSet defaults() {
		AttributeSet set = new AttributeSet();
		set.add(new Attribute("android:id", "@+id/" + FAB_ID));
		set.add(new Attribute("android:layout_width", "wrap_content"));
		set.add(new Attribute("android:layout_height", "wrap_content"));
		set.add(new Attribute("android:layout_gravity", "bottom|end"));
		set.add(new Attribute("android:layout_margin", "16dp"));
		set.add(new Attribute("app:srcCompat", "@android:drawable/ic_input_add"));
		set.add(new Attribute("app:backgroundTint", "#212121"));
		set.add(new Attribute("app:tint", "#FFFFFF"));
		set.add(new Attribute("app:elevation", "6dp"));
		set.add(new Attribute("app:fabSize", "normal"));
		return set;
	}

	/** True when the user has a saved attribute set for this activity's FAB. */
	public static boolean hasSaved(Context ctx, String scId, String activityName) {
		try {
			return prefs(ctx).contains(key(scId, activityName));
		} catch (Throwable t) {
			return false;
		}
	}

	/** Saved attributes if any, else defaults. Always contains android:id. */
	public static AttributeSet loadOrDefaults(Context ctx, String scId, String activityName) {
		AttributeSet set = null;
		try {
			String raw = prefs(ctx).getString(key(scId, activityName), null);
			if (raw != null && raw.length() > 0) {
				JSONArray arr = new JSONArray(raw);
				set = new AttributeSet();
				for (int i = 0; i < arr.length(); i++) {
					JSONArray pair = arr.getJSONArray(i);
					String name = pair.getString(0);
					String value = pair.isNull(1) ? "" : pair.getString(1);
					if (name != null && name.length() > 0) {
						set.add(new Attribute(name, value));
					}
				}
			}
		} catch (Throwable t) {
			Log.w(TAG, "load failed, using defaults", t);
			set = null;
		}
		if (set == null) set = defaults();
		ensureId(set);
		return set;
	}

	public static void save(Context ctx, String scId, String activityName, AttributeSet set) {
		if (ctx == null || set == null) return;
		try {
			JSONArray arr = new JSONArray();
			for (Attribute a : set.getAttributes()) {
				if (a == null || a.getName() == null) continue;
				JSONArray pair = new JSONArray();
				pair.put(a.getName());
				pair.put(a.getValue() == null ? "" : a.getValue());
				arr.put(pair);
			}
			prefs(ctx).edit().putString(key(scId, activityName), arr.toString()).apply();
		} catch (Throwable t) {
			Log.w(TAG, "save failed", t);
		}
	}

	public static void clear(Context ctx, String scId, String activityName) {
		try {
			prefs(ctx).edit().remove(key(scId, activityName)).apply();
		} catch (Throwable ignore) {}
	}

	/** android:id must always be present so XML output + id chips stay in sync. */
	public static void ensureId(AttributeSet set) {
		if (set == null) return;
		Attribute id = set.getAttribute("android:id");
		if (id == null || id.getValue() == null || id.getValue().trim().isEmpty()) {
			set.add(new Attribute("android:id", "@+id/" + FAB_ID));
		}
	}

	/** "@+id/_fab" -> "_fab" */
	public static String idNameOf(AttributeSet set) {
		if (set == null) return FAB_ID;
		Attribute id = set.getAttribute("android:id");
		if (id == null || id.getValue() == null) return FAB_ID;
		String v = id.getValue().trim();
		int slash = v.lastIndexOf('/');
		if (slash >= 0) v = v.substring(slash + 1);
		return v.isEmpty() ? FAB_ID : v;
	}

	private static SharedPreferences prefs(Context ctx) {
		return ctx.getApplicationContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
	}
}
