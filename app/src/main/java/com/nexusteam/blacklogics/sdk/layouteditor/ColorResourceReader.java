package com.nexusteam.blacklogics.sdk.layouteditor;

import android.graphics.Color;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

/**
 * Resolves @color/xxx for the ViewEditor preview.
 * Fixes: (1) values-night/colors.xml was ignored, (2) "@color/other" references
 * returned BLACK, (3) white/black/transparent built-ins missing -> BLACK.
 */
class ColorResourceReader {

	private static final String ROOT = "/storage/emulated/0/.blacklogics/";

	static int read(String scId, String resourceName) {
		if (scId == null || resourceName == null) return Color.BLACK;

		HashMap<String, String> map = new HashMap<String, String>();
		load(map, firstExisting(
				ROOT + "data/" + scId + "/files/resource/values/colors.xml",
				ROOT + "resources/colors/" + scId + "/colors.xml"));
		if (isNight()) {
			// night values override day values
			load(map, firstExisting(
					ROOT + "data/" + scId + "/files/resource/values-night/colors.xml",
					ROOT + "resources/colors/" + scId + "/colors-night.xml"));
		}
		return resolve(map, resourceName, 0);
	}

	private static boolean isNight() {
		int mode = AppCompatDelegate.getDefaultNightMode();
		if (mode == AppCompatDelegate.MODE_NIGHT_YES) return true;
		if (mode == AppCompatDelegate.MODE_NIGHT_NO) return false;
		int ui = android.content.res.Resources.getSystem()
				.getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
		return ui == android.content.res.Configuration.UI_MODE_NIGHT_YES;
	}

	private static File firstExisting(String... paths) {
		for (String p : paths) {
			File f = new File(p);
			if (f.exists()) return f;
		}
		return null;
	}

	private static int resolve(HashMap<String, String> map, String name, int depth) {
		if (depth > 8) return Color.BLACK;
		String raw = map.get(name);
		if (raw == null) return builtin(name);
		raw = raw.trim();
		if (raw.startsWith("@color/")) return resolve(map, raw.substring(7), depth + 1);
		if (raw.startsWith("@android:color/")) return builtin(raw.substring(15));
		try {
			return Color.parseColor(raw);
		} catch (Exception e) {
			Log.w("ColorResourceReader", "Bad color value for " + name + ": " + raw);
			return Color.BLACK;
		}
	}

	private static int builtin(String n) {
		if ("white".equals(n)) return Color.WHITE;
		if ("black".equals(n)) return Color.BLACK;
		if ("transparent".equals(n)) return Color.TRANSPARENT;
		return Color.BLACK;
	}

	private static void load(HashMap<String, String> out, File file) {
		if (file == null) return;
		try {
			XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
			parser.setInput(new FileInputStream(file), "UTF-8");
			String cur = null;
			StringBuilder val = new StringBuilder();
			int ev = parser.getEventType();
			while (ev != XmlPullParser.END_DOCUMENT) {
				if (ev == XmlPullParser.START_TAG && "color".equals(parser.getName())) {
					cur = parser.getAttributeValue(null, "name");
					val.setLength(0);
				} else if (ev == XmlPullParser.TEXT && cur != null) {
					val.append(parser.getText());
				} else if (ev == XmlPullParser.END_TAG && "color".equals(parser.getName())) {
					if (cur != null) out.put(cur, val.toString().trim());
					cur = null;
					val.setLength(0);
				}
				ev = parser.next();
			}
		} catch (Exception e) {
			Log.e("ColorResourceReader", "Error parsing " + file + ": " + e.getMessage(), e);
		}
	}
}
