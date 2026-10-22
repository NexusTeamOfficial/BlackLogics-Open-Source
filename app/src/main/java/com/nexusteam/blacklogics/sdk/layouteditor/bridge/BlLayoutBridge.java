package com.nexusteam.blacklogics.sdk.layouteditor.bridge;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nexusteam.internal.beans.ViewBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class BlLayoutBridge {

	private static final String TAG = "BlLayoutBridge";
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	private BlLayoutBridge() {}

	public static File rootLayoutJson(String projectDataDir) {
		return new File(projectDataDir, "root_layout.json");
	}

	public static String beansToXml(List<ViewBean> beans) {
		if (beans == null || beans.isEmpty()) {
			return defaultEmptyLayoutXml();
		}
		ViewBean root = null;
		for (ViewBean b : beans) {
			if (b == null) continue;
			if (b.parent == null || b.parent.length() == 0
					|| "root".equals(b.parent) || "parent".equals(b.parent)) {
				root = b;
				break;
			}
		}
		if (root == null) root = beans.get(0);

		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		appendBeanXml(sb, root, beans, 0, true);
		return sb.toString();
	}

	private static void appendBeanXml(StringBuilder sb, ViewBean bean,
			List<ViewBean> all, int indent, boolean isRoot) {
		String pad = pad(indent);
		String tag = xmlTagForType(bean.type);
		sb.append(pad).append("<").append(tag);
		if (isRoot) {
			sb.append("\n").append(pad).append("    xmlns:android=\"http://schemas.android.com/apk/res/android\"");
			sb.append("\n").append(pad).append("    xmlns:app=\"http://schemas.android.com/apk/res-auto\"");
			sb.append("\n").append(pad).append("    xmlns:tools=\"http://schemas.android.com/tools\"");
		}
		if (bean.id != null && bean.id.length() > 0 && bean.id.charAt(0) != '_') {
			sb.append("\n").append(pad).append("    android:id=\"@+id/").append(bean.id).append("\"");
		}
		appendLayoutAttrs(sb, bean, pad);
		appendCommonAttrs(sb, bean, pad);

		List<ViewBean> children = childrenOf(all, bean.id);
		if (children.isEmpty()) {
			sb.append(" />\n");
		} else {
			sb.append(">\n");
			for (ViewBean c : children) {
				appendBeanXml(sb, c, all, indent + 4, false);
			}
			sb.append(pad).append("</").append(tag).append(">\n");
		}
	}

	private static void appendLayoutAttrs(StringBuilder sb, ViewBean bean, String pad) {
		if (bean.layout == null) {
			sb.append("\n").append(pad).append("    android:layout_width=\"wrap_content\"");
			sb.append("\n").append(pad).append("    android:layout_height=\"wrap_content\"");
			return;
		}
		sb.append("\n").append(pad).append("    android:layout_width=\"")
			.append(layoutSize(bean.layout.width)).append("\"");
		sb.append("\n").append(pad).append("    android:layout_height=\"")
			.append(layoutSize(bean.layout.height)).append("\"");
		if (bean.layout.paddingLeft != 0 || bean.layout.paddingTop != 0
				|| bean.layout.paddingRight != 0 || bean.layout.paddingBottom != 0) {
			if (bean.layout.paddingLeft == bean.layout.paddingTop
					&& bean.layout.paddingTop == bean.layout.paddingRight
					&& bean.layout.paddingRight == bean.layout.paddingBottom) {
				sb.append("\n").append(pad).append("    android:padding=\"")
					.append(bean.layout.paddingLeft).append("dp\"");
			} else {
				if (bean.layout.paddingLeft != 0)
					sb.append("\n").append(pad).append("    android:paddingLeft=\"")
						.append(bean.layout.paddingLeft).append("dp\"");
				if (bean.layout.paddingTop != 0)
					sb.append("\n").append(pad).append("    android:paddingTop=\"")
						.append(bean.layout.paddingTop).append("dp\"");
				if (bean.layout.paddingRight != 0)
					sb.append("\n").append(pad).append("    android:paddingRight=\"")
						.append(bean.layout.paddingRight).append("dp\"");
				if (bean.layout.paddingBottom != 0)
					sb.append("\n").append(pad).append("    android:paddingBottom=\"")
						.append(bean.layout.paddingBottom).append("dp\"");
			}
		}
		if (bean.layout.marginLeft != 0)
			sb.append("\n").append(pad).append("    android:layout_marginLeft=\"")
				.append(bean.layout.marginLeft).append("dp\"");
		if (bean.layout.marginTop != 0)
			sb.append("\n").append(pad).append("    android:layout_marginTop=\"")
				.append(bean.layout.marginTop).append("dp\"");
		if (bean.layout.marginRight != 0)
			sb.append("\n").append(pad).append("    android:layout_marginRight=\"")
				.append(bean.layout.marginRight).append("dp\"");
		if (bean.layout.marginBottom != 0)
			sb.append("\n").append(pad).append("    android:layout_marginBottom=\"")
				.append(bean.layout.marginBottom).append("dp\"");
		if (bean.type == 0 && bean.layout.orientation == 0) {
			sb.append("\n").append(pad).append("    android:orientation=\"horizontal\"");
		} else if (bean.type == 0) {
			sb.append("\n").append(pad).append("    android:orientation=\"vertical\"");
		}
	}

	private static void appendCommonAttrs(StringBuilder sb, ViewBean bean, String pad) {
		if (bean.text != null && bean.text.text != null && bean.text.text.length() > 0) {
			if (bean.type == 5 || bean.type == 26 || bean.type == 32) {
				sb.append("\n").append(pad).append("    android:hint=\"")
					.append(escape(bean.text.text)).append("\"");
			} else {
				sb.append("\n").append(pad).append("    android:text=\"")
					.append(escape(bean.text.text)).append("\"");
			}
		}
		if (bean.text != null && bean.text.textSize > 0) {
			sb.append("\n").append(pad).append("    android:textSize=\"")
				.append(bean.text.textSize).append("sp\"");
		}
		if (bean.enabled == 0) {
			sb.append("\n").append(pad).append("    android:enabled=\"false\"");
		}
	
		// Free-form attrs from ViewBean (do not re-emit keys already written above)
		try {
			java.util.Map<String, String> attrs = bean.getAttrs();
			if (attrs != null && !attrs.isEmpty()) {
				java.util.HashSet<String> skip = new java.util.HashSet<String>();
				skip.add("android:layout_width");
				skip.add("android:layout_height");
				skip.add("android:padding");
				skip.add("android:paddingLeft");
				skip.add("android:paddingTop");
				skip.add("android:paddingRight");
				skip.add("android:paddingBottom");
				skip.add("android:layout_marginLeft");
				skip.add("android:layout_marginTop");
				skip.add("android:layout_marginRight");
				skip.add("android:layout_marginBottom");
				skip.add("android:orientation");
				skip.add("android:text");
				skip.add("android:hint");
				skip.add("android:textSize");
				skip.add("android:enabled");
				skip.add("android:id");
				java.util.Iterator<java.util.Map.Entry<String, String>> it = attrs.entrySet().iterator();
				while (it.hasNext()) {
					java.util.Map.Entry<String, String> e = it.next();
					String k = e.getKey();
					String v = e.getValue();
					if (k == null || k.length() == 0) continue;
					if (skip.contains(k)) continue;
					if (v == null) v = "";
					sb.append("\n").append(pad).append("    ").append(k).append("=\"")
						.append(escape(v)).append("\"");
				}
			}
		} catch (Exception ignored) {}
	}

	private static String layoutSize(int v) {
		if (v == LayoutBeanMatch()) return "match_parent";
		if (v == LayoutBeanWrap()) return "wrap_content";
		if (v < 0) return "wrap_content";
		return v + "dp";
	}

	private static int LayoutBeanMatch() { return -1; }
	private static int LayoutBeanWrap() { return -2; }

	private static List<ViewBean> childrenOf(List<ViewBean> all, String parentId) {
		List<ViewBean> out = new ArrayList<ViewBean>();
		if (parentId == null) return out;
		for (ViewBean b : all) {
			if (b != null && parentId.equals(b.parent)) out.add(b);
		}
		java.util.Collections.sort(out, new java.util.Comparator<ViewBean>() {
			@Override
			public int compare(ViewBean a, ViewBean b) {
				return Integer.compare(a.index, b.index);
			}
		});
		return out;
	}

	public static String xmlTagForType(int type) {
		String name = ViewBean.getViewTypeName(type);
		if (name == null || name.length() == 0) return "View";
		switch (type) {
			case 0: return "LinearLayout";
			case 1: return "RelativeLayout";
			case 2: return "HorizontalScrollView";
			case 12: return "ScrollView";
			case 7: return "WebView";
			case 16: return "com.google.android.material.floatingactionbutton.FloatingActionButton";
			case 17: return "com.google.android.gms.ads.AdView";
			case 18: return "com.google.android.gms.maps.MapView";
			case 29: return "com.google.android.material.button.MaterialButton";
			case 30: return "com.google.android.material.card.MaterialCardView";
			case 31: return "com.google.android.material.textfield.TextInputLayout";
			case 32: return "com.google.android.material.textfield.TextInputEditText";
			case 33: return "com.google.android.material.chip.Chip";
			case 34: return "com.google.android.material.chip.ChipGroup";
			case 35: return "com.google.android.material.bottomnavigation.BottomNavigationView";
			case 36: return "com.google.android.material.tabs.TabLayout";
			case 37: return "com.google.android.material.switchmaterial.SwitchMaterial";
			case 38: return "com.google.android.material.slider.Slider";
			case 39: return "androidx.recyclerview.widget.RecyclerView";
			case 40: return "androidx.viewpager.widget.ViewPager";
			case 41: return "androidx.viewpager2.widget.ViewPager2";
			case 42: return "androidx.cardview.widget.CardView";
			case 43: return "androidx.constraintlayout.widget.ConstraintLayout";
			case 44: return "androidx.coordinatorlayout.widget.CoordinatorLayout";
			case 45: return "androidx.core.widget.NestedScrollView";
			case 46: return "com.airbnb.lottie.LottieAnimationView";
			case 47: return "de.hdodenhof.circleimageview.CircleImageView";
			case 48: return "com.google.android.material.navigation.NavigationView";
			default:
				if (name.contains(".")) return name;
				return name;
		}
	}

	private static String escape(String s) {
		return s.replace("&", "&amp;").replace("\"", "&quot;")
			.replace("<", "&lt;").replace(">", "&gt;");
	}

	private static String pad(int n) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < n; i++) b.append(' ');
		return b.toString();
	}

	public static String defaultEmptyLayoutXml() {
		return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
			+ "<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n"
			+ "    xmlns:app=\"http://schemas.android.com/apk/res-auto\"\n"
			+ "    xmlns:tools=\"http://schemas.android.com/tools\"\n"
			+ "    android:layout_width=\"match_parent\"\n"
			+ "    android:layout_height=\"match_parent\"\n"
			+ "    android:orientation=\"vertical\">\n"
			+ "</LinearLayout>\n";
	}

	public static boolean save(String projectDataDir, String xmlName, List<ViewBean> beans,
			File resLayoutDir) {
		try {
			String xml = beansToXml(beans);
			File jsonFile = rootLayoutJson(projectDataDir);
			JsonObject root;
			if (jsonFile.exists()) {
				try (InputStreamReader r = new InputStreamReader(
						new FileInputStream(jsonFile), StandardCharsets.UTF_8)) {
					JsonElement el = new JsonParser().parse(r);
					root = el.isJsonObject() ? el.getAsJsonObject() : new JsonObject();
				}
			} else {
				root = new JsonObject();
			}

			JsonObject entry = new JsonObject();
			entry.addProperty("xml", xml);
			entry.addProperty("xmlName", xmlName);
			entry.addProperty("xmlFileName", xmlName + ".xml");
			root.add(xmlName, entry);

			File parent = jsonFile.getParentFile();
			if (parent != null && !parent.exists()) parent.mkdirs();
			try (OutputStreamWriter w = new OutputStreamWriter(
					new FileOutputStream(jsonFile), StandardCharsets.UTF_8)) {
				GSON.toJson(root, w);
			}

			if (resLayoutDir != null) {
				if (!resLayoutDir.exists()) resLayoutDir.mkdirs();
				File xmlFile = new File(resLayoutDir, xmlName + ".xml");
				try (OutputStreamWriter w = new OutputStreamWriter(
						new FileOutputStream(xmlFile), StandardCharsets.UTF_8)) {
					w.write(stripInternalMarkers(xml));
				}
			}
			Log.i(TAG, "Saved layout " + xmlName + " beans=" + (beans != null ? beans.size() : 0));
			return true;
		} catch (Throwable t) {
			Log.e(TAG, "save failed", t);
			return false;
		}
	}

	public static String stripInternalMarkers(String xml) {
		if (xml == null) return defaultEmptyLayoutXml();
		return xml;
	}

	public static String loadXml(String projectDataDir, String xmlName) {
		try {
			File jsonFile = rootLayoutJson(projectDataDir);
			if (!jsonFile.exists()) return defaultEmptyLayoutXml();
			try (InputStreamReader r = new InputStreamReader(
					new FileInputStream(jsonFile), StandardCharsets.UTF_8)) {
				JsonElement el = new JsonParser().parse(r);
				if (!el.isJsonObject()) return defaultEmptyLayoutXml();
				JsonObject root = el.getAsJsonObject();
				if (root.has(xmlName) && root.get(xmlName).isJsonObject()) {
					JsonObject e = root.getAsJsonObject(xmlName);
					if (e.has("xml")) return e.get("xml").getAsString();
				}
				if (root.has("layouts") && root.get("layouts").isJsonArray()) {
					JsonArray arr = root.getAsJsonArray("layouts");
					for (JsonElement item : arr) {
						if (!item.isJsonObject()) continue;
						JsonObject o = item.getAsJsonObject();
						String name = o.has("xmlName") ? o.get("xmlName").getAsString() : "";
						if (xmlName.equals(name) && o.has("xml")) {
							return o.get("xml").getAsString();
						}
					}
				}
			}
		} catch (Throwable t) {
			Log.e(TAG, "loadXml failed", t);
		}
		return defaultEmptyLayoutXml();
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<ViewBean> loadBeansFromMa(String scId, String xmlName) {
		try {
			Class<?> maCls = Class.forName("com.nexusteam.internal.ma");
			java.lang.reflect.Method a = maCls.getMethod("a", String.class);
			Object repo = a.invoke(null, scId);
			java.lang.reflect.Method get = repo.getClass().getMethod("a", String.class);
			Object list = get.invoke(repo, xmlName);
			if (list instanceof ArrayList) {
				return (ArrayList<ViewBean>) list;
			}
		} catch (Throwable t) {
			Log.w(TAG, "loadBeansFromMa: " + t.getMessage());
		}
		return new ArrayList<ViewBean>();
	}

	public static boolean syncEditorToBlackLogics(String scId, String projectDataDir,
			String xmlName, File resLayoutDir) {
		ArrayList<ViewBean> beans = loadBeansFromMa(scId, xmlName);
		return save(projectDataDir, xmlName, beans, resLayoutDir);
	}
}
