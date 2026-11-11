package com.nexusteam.blacklogics.generator.source.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Typed attribute bag used by the code generator.
 *
 * Stored inside {@link ActivityConfig#getAttributes()} under well-known keys.
 * Values are always stored as String in the map for JSON/Base64 round-trip safety;
 * typed getters parse on demand.
 *
 * Well-known keys (use the KEY_* constants):
 *   theme                 – style resource name, e.g. "AppTheme.Dialog"
 *   windowSoftInputMode   – "adjustResize" | "adjustPan" | "stateHidden" | …
 *   retainInstance        – "true"/"false"  (Fragment only, deprecated but still used)
 *   cancelable            – DialogFragment cancelable
 *   fullScreen            – DialogFragment full-screen window
 *   widthRatio            – 0.0–1.0 dialog width
 *   heightRatio           – 0.0–1.0 dialog height
 *   statusBarColor        – "#RRGGBB" or color resource name
 *   navigationBarColor    – same
 *   orientation           – "portrait" | "landscape" | "unspecified"
 *   hardwareAccelerated   – "true"/"false"
 *   showAsAction          – menu related
 *   customClassExtends    – for CUSTOM_ACTIVITY override
 */
public final class GenerationAttribute {

	// ---- well-known keys ----
	public static final String KEY_THEME                 = "theme";
	public static final String KEY_WINDOW_SOFT_INPUT     = "windowSoftInputMode";
	public static final String KEY_RETAIN_INSTANCE       = "retainInstance";
	public static final String KEY_CANCELABLE            = "cancelable";
	public static final String KEY_FULL_SCREEN           = "fullScreen";
	public static final String KEY_WIDTH_RATIO           = "widthRatio";
	public static final String KEY_HEIGHT_RATIO          = "heightRatio";
	public static final String KEY_STATUS_BAR_COLOR      = "statusBarColor";
	public static final String KEY_NAV_BAR_COLOR         = "navigationBarColor";
	public static final String KEY_ORIENTATION           = "orientation";
	public static final String KEY_HARDWARE_ACCELERATED  = "hardwareAccelerated";
	public static final String KEY_CUSTOM_EXTENDS        = "customClassExtends";
	public static final String KEY_PARENT_ACTIVITY       = "parentActivity";
	public static final String KEY_LAUNCH_MODE           = "launchMode";
	public static final String KEY_CONFIG_CHANGES        = "configChanges";

	private final Map<String, String> map;

	public GenerationAttribute() {
		this.map = new HashMap<String, String>();
	}

	public GenerationAttribute(Map<String, ?> raw) {
		this.map = new HashMap<String, String>();
		if (raw != null) {
			for (Map.Entry<String, ?> e : raw.entrySet()) {
				if (e.getKey() != null && e.getValue() != null) {
					map.put(e.getKey(), String.valueOf(e.getValue()));
				}
			}
		}
	}

	// ---- mutators ----

	public GenerationAttribute put(String key, String value) {
		if (key != null && value != null) map.put(key, value);
		return this;
	}

	public GenerationAttribute put(String key, boolean value) {
		return put(key, value ? "true" : "false");
	}

	public GenerationAttribute put(String key, float value) {
		return put(key, String.valueOf(value));
	}

	public GenerationAttribute put(String key, int value) {
		return put(key, String.valueOf(value));
	}

	public GenerationAttribute remove(String key) {
		map.remove(key);
		return this;
	}

	// ---- typed getters ----

	public String getString(String key) {
		return map.get(key);
	}

	public String getString(String key, String def) {
		String v = map.get(key);
		return v != null ? v : def;
	}

	public boolean getBoolean(String key, boolean def) {
		String v = map.get(key);
		if (v == null) return def;
		return "true".equalsIgnoreCase(v) || "1".equals(v);
	}

	public float getFloat(String key, float def) {
		String v = map.get(key);
		if (v == null) return def;
		try {
			return Float.parseFloat(v);
		} catch (NumberFormatException e) {
			return def;
		}
	}

	public int getInt(String key, int def) {
		String v = map.get(key);
		if (v == null) return def;
		try {
			return Integer.parseInt(v);
		} catch (NumberFormatException e) {
			return def;
		}
	}

	public boolean has(String key) {
		return map.containsKey(key) && map.get(key) != null;
	}

	public Map<String, String> asMap() {
		return Collections.unmodifiableMap(map);
	}

	/** Merge into ActivityConfig attributes map (String values). */
	public void applyTo(Map<String, Object> attributes) {
		if (attributes == null) return;
		for (Map.Entry<String, String> e : map.entrySet()) {
			attributes.put(e.getKey(), e.getValue());
		}
	}

	/** Build from ActivityConfig.attributes. */
	public static GenerationAttribute from(ActivityConfig config) {
		if (config == null || config.getAttributes() == null) {
			return new GenerationAttribute();
		}
		return new GenerationAttribute(config.getAttributes());
	}

	/** Convenience: dialog-oriented defaults. */
	public static GenerationAttribute dialogDefaults() {
		return new GenerationAttribute()
				.put(KEY_CANCELABLE, true)
				.put(KEY_WIDTH_RATIO, 0.9f)
				.put(KEY_FULL_SCREEN, false);
	}

	/** Emit AndroidManifest activity/fragment attributes as XML snippets. */
	public String toManifestAttributes() {
		StringBuilder sb = new StringBuilder();
		String theme = getString(KEY_THEME);
		if (theme != null && !theme.isEmpty()) {
			sb.append(" android:theme=\"@style/").append(theme).append("\"");
		}
		String soft = getString(KEY_WINDOW_SOFT_INPUT);
		if (soft != null && !soft.isEmpty()) {
			sb.append(" android:windowSoftInputMode=\"").append(soft).append("\"");
		}
		String orient = getString(KEY_ORIENTATION);
		if (orient != null && !orient.isEmpty()) {
			sb.append(" android:screenOrientation=\"").append(orient.toLowerCase(Locale.US)).append("\"");
		}
		if (has(KEY_HARDWARE_ACCELERATED)) {
			sb.append(" android:hardwareAccelerated=\"")
					.append(getBoolean(KEY_HARDWARE_ACCELERATED, true)).append("\"");
		}
		String launch = getString(KEY_LAUNCH_MODE);
		if (launch != null && !launch.isEmpty()) {
			sb.append(" android:launchMode=\"").append(launch).append("\"");
		}
		String cfg = getString(KEY_CONFIG_CHANGES);
		if (cfg != null && !cfg.isEmpty()) {
			sb.append(" android:configChanges=\"").append(cfg).append("\"");
		}
		String parent = getString(KEY_PARENT_ACTIVITY);
		if (parent != null && !parent.isEmpty()) {
			sb.append(" android:parentActivityName=\"").append(parent).append("\"");
		}
		return sb.toString();
	}

	/**
	 * Emit Java statements that apply runtime attributes inside onCreate / onViewCreated.
	 * {@code ctx} is "this" for Activity or "requireContext()" for Fragment.
	 */
	public void emitRuntimeSetup(StringBuilder javaCode, String indent, String ctx, boolean isFragment) {
		if (javaCode == null) return;
		if (indent == null) indent = "        ";

		// status bar / nav bar colors (API 21+)
		String status = getString(KEY_STATUS_BAR_COLOR);
		String nav = getString(KEY_NAV_BAR_COLOR);
		if ((status != null && !status.isEmpty()) || (nav != null && !nav.isEmpty())) {
			javaCode.append(indent).append("if (android.os.Build.VERSION.SDK_INT >= 21) {\n");
			if (status != null && !status.isEmpty()) {
				if (status.startsWith("#")) {
					javaCode.append(indent).append("    ")
							.append(isFragment ? "requireActivity()" : "this")
							.append(".getWindow().setStatusBarColor(android.graphics.Color.parseColor(\"")
							.append(status).append("\"));\n");
				} else {
					javaCode.append(indent).append("    ")
							.append(isFragment ? "requireActivity()" : "this")
							.append(".getWindow().setStatusBarColor(androidx.core.content.ContextCompat.getColor(")
							.append(ctx).append(", R.color.").append(status).append("));\n");
				}
			}
			if (nav != null && !nav.isEmpty()) {
				if (nav.startsWith("#")) {
					javaCode.append(indent).append("    ")
							.append(isFragment ? "requireActivity()" : "this")
							.append(".getWindow().setNavigationBarColor(android.graphics.Color.parseColor(\"")
							.append(nav).append("\"));\n");
				} else {
					javaCode.append(indent).append("    ")
							.append(isFragment ? "requireActivity()" : "this")
							.append(".getWindow().setNavigationBarColor(androidx.core.content.ContextCompat.getColor(")
							.append(ctx).append(", R.color.").append(nav).append("));\n");
				}
			}
			javaCode.append(indent).append("}\n");
		}

		// retainInstance (Fragment only, suppressed deprecation)
		if (isFragment && getBoolean(KEY_RETAIN_INSTANCE, false)) {
			javaCode.append(indent).append("@SuppressWarnings(\"deprecation\")\n");
			javaCode.append(indent).append("{ setRetainInstance(true); }\n");
		}
	}

	@Override
	public String toString() {
		return "GenerationAttribute" + map;
	}
}
