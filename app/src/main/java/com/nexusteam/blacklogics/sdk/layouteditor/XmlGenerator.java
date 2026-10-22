package com.nexusteam.blacklogics.sdk.layouteditor;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.nexusteam.blacklogics.design.AppCompatInjectionReader;
import com.nexusteam.blacklogics.development.Complex;
import com.nexusteam.blacklogics.project.ProjectDataHelper;
import com.nexusteam.blacklogics.sdk.AttributeSet;
import com.nexusteam.blacklogics.sdk.util.ActivityConfig;
import com.nexusteam.blacklogics.sdk.widgets.PlaceholderWebView;
import com.nexusteam.blacklogics.sdk.widgets.PlaceholderWidget;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlGenerator {
	
	private final Context context;
	private final AttributeStore attributeStore;
	private final View placeHolder;
	private final String scId;
	/** Optional — when set, android:id is always taken from IdManager (source of truth). */
	private IdManager idManager;
	
	public XmlGenerator(Context context, AttributeStore attributeStore,
	View placeHolder, String scId) {
		this.context = context;
		this.attributeStore = attributeStore;
		this.placeHolder = placeHolder;
		this.scId = scId;
	}

	public void setIdManager(IdManager idManager) {
		this.idManager = idManager;
	}
	
	public String generateLayoutXml(ViewGroup editorLayout, String activityName) {
		return generateLayoutXml(editorLayout, activityName, null, null);
	}
	
	public String generateLayoutXml(ViewGroup editorLayout, String activityName,
	Complex projectComplex) {
		return generateLayoutXml(editorLayout, activityName, null, projectComplex);
	}
	
	public String generateLayoutXml(ViewGroup editorLayout, String activityName,
	String layoutName, Complex projectComplex) {
		List<View> realChildren = collectRealChildren(editorLayout);
		
		if (isCustomOrDrawerLayout(layoutName, activityName, projectComplex)) {
			return generateSimpleLayoutXml(realChildren);
		}
		
		boolean hasToolbar = false;
		boolean useAndroidX = false;
		
		if (activityName != null && !activityName.trim().isEmpty()) {
			ActivityConfig uiConfig = new ActivityConfig();
			uiConfig.init(context, scId);
			hasToolbar = uiConfig.hasToolbar(activityName);
			useAndroidX = projectComplex != null
			? projectComplex.getAndroidXEnable()
			: uiConfig.getAndroidXEnabled();
		}
		
		if (hasToolbar && useAndroidX) {
			return generateToolbarLayoutXml(realChildren);
		}
		return generateSimpleLayoutXml(realChildren);
	}
	
	private boolean isCustomOrDrawerLayout(String layoutName, String activityName,
	Complex projectComplex) {
		if (isDrawerLayoutName(layoutName) || isDrawerLayoutName(activityName)) return true;
		
		if (isActivityJavaName(layoutName)) return false;
		
		if (isActivityJavaName(activityName)) {
			if (layoutName == null || layoutName.trim().isEmpty()) return false;
			String n = layoutName.trim();
			if (n.endsWith(".xml")) n = n.substring(0, n.length() - 4);
			if (isDrawerLayoutName(n)) return true;
			if (isActivityJavaName(n)) return false;
			return isRegisteredCustomViewOnly(n, projectComplex);
		}
		
		if (layoutName != null) {
			String n = layoutName.trim();
			if (n.endsWith(".xml")) n = n.substring(0, n.length() - 4);
			if (isDrawerLayoutName(n)) return true;
			if (isActivityJavaName(n)) return false;
			return isRegisteredCustomViewOnly(n, projectComplex);
		}
		return false;
	}
	
	private boolean isRegisteredCustomViewOnly(String name, Complex projectComplex) {
		if (name == null || name.trim().isEmpty()) return false;
		String n = name.trim();
		if (n.endsWith(".xml")) n = n.substring(0, n.length() - 4);
		if ("main".equalsIgnoreCase(n)) return false;
		try {
			return isCurrentNameCustomView(n, projectComplex)
			|| isCurrentNameCustomView(n.toLowerCase(), projectComplex);
		} catch (Throwable t) {
			return false;
		}
	}
	
	private boolean isActivityJavaName(String name) {
		if (name == null) return false;
		String n = name.trim();
		if (n.endsWith(".java")) n = n.substring(0, n.length() - 5);
		return n.endsWith("Activity");
	}
	
	private boolean isDrawerLayoutName(String name) {
		if (name == null) return false;
		String n = name.trim();
		if (n.endsWith(".xml")) n = n.substring(0, n.length() - 4);
		return n.startsWith("_drawer_") || n.contains("_drawer_");
	}
	
	public String getXMLCode(ViewGroup editorLayout, String activityName,
	Complex projectComplex) {
		return getXMLCode(editorLayout, activityName, null, projectComplex);
	}
	
	public String getXMLCode(ViewGroup editorLayout, String activityName,
	String layoutName, Complex projectComplex) {
		List<View> realChildren = collectRealChildren(editorLayout);
		
		if (isCustomOrDrawerLayout(layoutName, activityName, projectComplex)) {
			return generateSimpleLayoutXml(realChildren);
		}
		
		boolean hasToolbar = false;
		boolean hasFab = false;
		boolean hasDrawer = false;
		boolean useAndroidX = false;
		
		if (activityName != null && !activityName.trim().isEmpty()) {
			ActivityConfig uiConfig = new ActivityConfig();
			uiConfig.init(context, scId);
			hasToolbar = uiConfig.hasToolbar(activityName);
			hasFab = uiConfig.hasFab(activityName);
			hasDrawer = uiConfig.hasDrawer(activityName);
			useAndroidX = projectComplex != null
			? projectComplex.getAndroidXEnable()
			: uiConfig.getAndroidXEnabled();
			try {
				if (projectComplex != null) {
					String n = activityName;
					if (n.endsWith(".java")) n = n.substring(0, n.length() - 5);
					if (!hasFab) hasFab = projectComplex.getEnableFabBoolean(n);
					if (!hasDrawer) hasDrawer = projectComplex.isDrawerEnabled(n);
				}
			} catch (Throwable ignore) {}
		}
		
		String xml;
		if (hasToolbar && useAndroidX) {
			xml = generateToolbarLayoutXml(realChildren);
		} else {
			xml = generateSimpleLayoutXml(realChildren);
		}
		String chromed = injectChrome(xml, hasFab, hasDrawer, activityName, editorLayout);
		String injected = applyAppCompatInjection(activityName, layoutName, chromed);   // <-- NEW
		return prettyPrintXml(injected);
	}
	
	private String injectChrome(String xml, boolean hasFab, boolean hasDrawer,
	String activityName, ViewGroup editorLayout) {
		if (xml == null || xml.isEmpty()) return xml;
		if (!hasFab && !hasDrawer) return xml;
		String result = xml;
		
		if (hasFab && !result.contains("@+id/_fab") && !result.contains("@id/_fab")) {
			String fabXml = buildFabXml(editorLayout, activityName);
			int lastClose = result.lastIndexOf("</");
			if (lastClose > 0) {
				result = result.substring(0, lastClose) + fabXml + result.substring(lastClose);
			}
		}
		
		if (hasDrawer && !result.contains("@+id/_drawer") && !result.contains("DrawerLayout")) {
			String body = result;
			if (body.startsWith("<?xml")) {
				int gt = body.indexOf("?>");
				if (gt >= 0) body = body.substring(gt + 2).trim();
			}
			String drawerRes = resolveDrawerLayoutResName(activityName);
			String drawerSide =
			"    <LinearLayout\n" +
			"        android:id=\"@+id/_nav_view\"\n" +
			"        android:layout_width=\"240dp\"\n" +
			"        android:layout_height=\"match_parent\"\n" +
			"        android:layout_gravity=\"start\"\n" +
			"        android:orientation=\"vertical\"\n" +
			"        android:background=\"#FFFFFF\">\n" +
			"        <include\n" +
			"            layout=\"@layout/" + drawerRes + "\"\n" +
			"            android:id=\"@+id/" + drawerRes + "\"\n" +
			"            android:layout_width=\"match_parent\"\n" +
			"            android:layout_height=\"match_parent\" />\n" +
			"    </LinearLayout>\n";
			result = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
			"<androidx.drawerlayout.widget.DrawerLayout\n" +
			"    xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
			"    xmlns:app=\"http://schemas.android.com/apk/res-auto\"\n" +
			"    xmlns:tools=\"http://schemas.android.com/tools\"\n" +
			"    android:id=\"@+id/_drawer\"\n" +
			"    android:layout_width=\"match_parent\"\n" +
			"    android:layout_height=\"match_parent\">\n\n" +
			body + "\n\n" +
			drawerSide +
			"</androidx.drawerlayout.widget.DrawerLayout>\n";
		}
		return result;
	}
	
	private String resolveDrawerLayoutResName(String activityName) {
		if (activityName == null || activityName.trim().isEmpty()) return "_drawer_main";
		String n = activityName.trim();
		if (n.endsWith(".java")) n = n.substring(0, n.length() - 5);
		if (n.endsWith(".xml")) n = n.substring(0, n.length() - 4);
		if (n.startsWith("_drawer_")) return n.toLowerCase();
		if (n.endsWith("Activity") && n.length() > 8) {
			String base = n.substring(0, n.length() - 8);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < base.length(); i++) {
				char c = base.charAt(i);
				if (Character.isUpperCase(c) && i > 0) sb.append('_');
				sb.append(Character.toLowerCase(c));
			}
			n = sb.length() > 0 ? sb.toString() : "main";
		} else {
			n = n.toLowerCase();
		}
		if (n.isEmpty()) n = "main";
		return "_drawer_" + n;
	}
	
	private List<View> collectRealChildren(ViewGroup editorLayout) {
		List<View> realChildren = new java.util.ArrayList<View>();
		for (int i = 0; i < editorLayout.getChildCount(); i++) {
			View child = editorLayout.getChildAt(i);
			if (child == null || child == placeHolder) continue;
			// FAB is injected via injectChrome — do not emit as normal child
			Object tag = child.getTag();
			if ("_fab".equals(tag) || "fab".equals(String.valueOf(tag))) continue;
			if (attributeStore.contains(child)) {
				realChildren.add(child);
			}
		}
		return realChildren;
	}
	
	/** FAB view registered by ViewEditor (lives on the canvas frame, outside the editor layout). */
	private View editorFabView;
	
	public void setEditorFabView(View fab) {
		this.editorFabView = fab;
	}
	
	/**
* FAB XML comes ONLY from the FAB's real attribute set — exactly like every other widget
* (generateCode). Nothing is hard-coded here any more. If the FAB view is not alive
* (editor closed / headless build) the persisted attribute set is used instead.
*/	
	private String buildFabXml(ViewGroup editorLayout, String activityName) {
		String code;
		View fab = findFabView(editorLayout);
		AttributeSet live = fab != null ? attributeStore.get(fab) : null;
		if (fab != null && live != null) {
			FabAttributeStore.ensureId(live);
			code = generateCode(fab);
		} else {
			code = fabXmlFromSet(FabAttributeStore.loadOrDefaults(context, scId, activityName));
		}
		// root already declares xmlns:app; drop the per-widget duplicate generateCode adds for custom views
		code = code.replaceAll("\\n\\s*xmlns:app=\"[^\"]*\"", "");
		// cleared attributes (value "") must not be emitted as attr=""
		code = code.replaceAll("\\n\\s*[A-Za-z_:]+=\"\"", "");
		return indent(code, 4) + "\n";
	}
	
	private String fabXmlFromSet(AttributeSet set) {
		StringBuilder sb = new StringBuilder();
		sb.append("<").append(FabAttributeStore.FAB_CLASS);
		if (set != null) {
			for (Attribute a : set.getAttributes()) {
				if (a == null || a.getName() == null) continue;
				String name = a.getName();
				if ("xmlns:android".equals(name) || "xmlns:app".equals(name)
				|| "app:convertedWidget".equals(name)) continue;
				String value = formatAttributeValue(a, null);
				if (value == null || value.isEmpty()) continue;
				sb.append("\n    ").append(name).append("=\"").append(value).append("\"");
			}
		}
		sb.append(" />");
		return sb.toString();
	}
	
	private View findFabView(ViewGroup editorLayout) {
		if (editorFabView != null && editorFabView.getParent() != null) return editorFabView;
		if (editorLayout == null) return null;
		// FAB is a sibling of the canvas viewport, i.e. a child of an ANCESTOR — walk up a few levels
		android.view.ViewParent p = editorLayout.getParent();
		int guard = 0;
		while (p instanceof ViewGroup && guard++ < 5) {
			View hit = findFabInGroup((ViewGroup) p);
			if (hit != null) return hit;
			p = p.getParent();
		}
		return findFabInGroup(editorLayout);
	}
	
	private View findFabInGroup(ViewGroup group) {
		if (group == null) return null;
		for (int i = 0; i < group.getChildCount(); i++) {
			View child = group.getChildAt(i);
			if (child == null) continue;
			Object tag = child.getTag();
			if ("_fab".equals(tag) || "fab".equals(String.valueOf(tag))) {
				return child;
			}
		}
		return null;
	}
	
	private static String attrOr(AttributeSet set, String key1, String key2, String def) {
		if (set == null) return def;
		try {
			Attribute a = set.getAttribute(key1);
			if (a != null && a.getValue() != null && a.getValue().trim().length() > 0) {
				return a.getValue().trim();
			}
			a = set.getAttribute(key2);
			if (a != null && a.getValue() != null && a.getValue().trim().length() > 0) {
				return a.getValue().trim();
			}
		} catch (Throwable ignore) {}
		return def;
	}
	
	private boolean isCurrentNameCustomView(String name, Complex projectComplex) {
		if (name == null || name.trim().isEmpty()) return false;
		try {
			if (projectComplex == null) return false;
			return projectComplex.hasCustomView(name);
		} catch (Exception e) {
			Log.e("XmlGenerator", "isCurrentNameCustomView failed for: " + name, e);
			return false;
		}
	}
	
	private String generateSimpleLayoutXml(List<View> children) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		sb.append("<LinearLayout\n");
		sb.append("    xmlns:android=\"http://schemas.android.com/apk/res/android\"\n");
		sb.append("    xmlns:app=\"http://schemas.android.com/apk/res-auto\"\n");
		sb.append("    xmlns:tools=\"http://schemas.android.com/tools\"\n");
		sb.append("    android:layout_width=\"match_parent\"\n");
		sb.append("    android:layout_height=\"match_parent\"\n");
		sb.append("    android:orientation=\"vertical\"");
		
		if (children.isEmpty()) {
			sb.append(" />");
			return sb.toString();
		}
		
		sb.append(">\n");
		for (int i = 0; i < children.size(); i++) {
			String code = generateCode(children.get(i));
			if (!code.trim().isEmpty()) {
				sb.append(indent(code, 4)).append("\n");
			}
		}
		sb.append("</LinearLayout>");
		return sb.toString();
	}
	
	private String generateToolbarLayoutXml(List<View> children) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		sb.append("<androidx.coordinatorlayout.widget.CoordinatorLayout\n");
		sb.append("    xmlns:android=\"http://schemas.android.com/apk/res/android\"\n");
		sb.append("    xmlns:app=\"http://schemas.android.com/apk/res-auto\"\n");
		sb.append("    xmlns:tools=\"http://schemas.android.com/tools\"\n");
		sb.append("    android:id=\"@+id/_coordinator\"\n");
		sb.append("    android:layout_width=\"match_parent\"\n");
		sb.append("    android:layout_height=\"match_parent\">\n");
		
		sb.append("    <com.google.android.material.appbar.AppBarLayout\n");
		sb.append("        android:id=\"@+id/_app_bar\"\n");
		sb.append("        android:layout_width=\"match_parent\"\n");
		sb.append("        android:layout_height=\"wrap_content\"\n");
		sb.append("        android:theme=\"@style/AppTheme.AppBarOverlay\">\n");
		
		sb.append("        <androidx.appcompat.widget.Toolbar\n");
		sb.append("            android:id=\"@+id/_toolbar\"\n");
		sb.append("            android:layout_width=\"match_parent\"\n");
		sb.append("            android:layout_height=\"?attr/actionBarSize\"\n");
		sb.append("            android:background=\"?attr/colorPrimary\"\n");
		sb.append("            app:popupTheme=\"@style/AppTheme.PopupOverlay\" />\n");
		
		sb.append("    </com.google.android.material.appbar.AppBarLayout>\n");
		
		sb.append("    <LinearLayout\n");
		sb.append("        android:layout_width=\"match_parent\"\n");
		sb.append("        android:layout_height=\"wrap_content\"\n");
		sb.append("        android:orientation=\"vertical\"\n");
		sb.append("        app:layout_behavior=\"@string/appbar_scrolling_view_behavior\"");
		
		if (children.isEmpty()) {
			sb.append(" />\n");
		} else {
			sb.append(">\n");
			for (int i = 0; i < children.size(); i++) {
				String code = generateCode(children.get(i));
				if (!code.trim().isEmpty()) {
					sb.append(indent(code, 8)).append("\n");
				}
			}
			sb.append("    </LinearLayout>\n");
		}
		
		sb.append("</androidx.coordinatorlayout.widget.CoordinatorLayout>");
		return sb.toString();
	}
	
	private String generateCustomViewLayoutXml(List<View> children) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		sb.append("<merge\n");
		sb.append("    xmlns:android=\"http://schemas.android.com/apk/res/android\"\n");
		sb.append("    xmlns:app=\"http://schemas.android.com/apk/res-auto\"\n");
		sb.append("    xmlns:tools=\"http://schemas.android.com/tools\">\n");
		
		for (int i = 0; i < children.size(); i++) {
			String code = generateCode(children.get(i));
			if (!code.trim().isEmpty()) {
				sb.append(indent(code, 4)).append("\n");
			}
		}
		
		sb.append("</merge>");
		return sb.toString();
	}
	
	public String generateCode(View view) {
		if ("EDITOR_DUMMY_ROOT".equals(view.getTag())) {
			StringBuilder rb = new StringBuilder();
			ViewGroup vg = (ViewGroup) view;
			for (int i = 0; i < vg.getChildCount(); i++) {
				View child = vg.getChildAt(i);
				if (child == placeHolder) continue;
				rb.append(generateCode(child)).append("\n");
			}
			return rb.toString();
		}
		
		StringBuilder sb = new StringBuilder();
		AttributeSet attributeSet = attributeStore.get(view);
		
		String tagName;
		if (view instanceof PlaceholderWidget) {
			String orig = ((PlaceholderWidget) view).getWidgetTag();
			tagName = (orig != null && !orig.isEmpty()) ? orig : "View";
		} else if (view instanceof PlaceholderWebView) {
			tagName = "WebView";
		} else {
			String pkg = view.getClass().getPackage() != null
			? view.getClass().getPackage().getName() : "";
			tagName = (pkg.equals("android.widget") || pkg.equals("android.view"))
			? view.getClass().getSimpleName()
			: view.getClass().getName();
		}
		
		boolean hasConvertedWidget = (attributeSet != null)
		&& (attributeSet.getAttribute("app:convertedWidget") != null);
		
		String xmlTagName = hasConvertedWidget
		? attributeSet.getConvertedTag(tagName)
		: tagName;
		
		boolean hasCustomViewAttr = false;
		if (attributeSet != null) {
			Attribute cv = attributeSet.getAttribute("app:customView");
			if (cv != null && cv.getValue() != null) {
				String cvv = cv.getValue().trim();
				hasCustomViewAttr = cvv.length() > 0 && !"none".equalsIgnoreCase(cvv);
			}
		}
		
		boolean needsAppNs = hasConvertedWidget || hasCustomViewAttr
		|| (xmlTagName.contains(".")
		&& !xmlTagName.startsWith("android.")
		&& !xmlTagName.startsWith("java."));
		
		sb.append("<").append(xmlTagName);

		// IdManager is the source of truth for view IDs. Always emit it so
		// custom names (e.g. my_switch) survive save → reload and are never
		// replaced by className+number from a stale/missing AttributeSet.
		String authoritativeId = null;
		if (idManager != null) {
			authoritativeId = idManager.getId(view);
		}
		boolean wroteId = false;
		
		if (attributeSet != null) {
			Attribute convertedAttr =
			attributeSet.getAttribute("app:convertedWidget");
			if (convertedAttr != null) {
				sb.append("\n    app:convertedWidget=\"")
				.append(attributeSet.getPreviewTag(tagName)).append("\"");
			}
			for (Attribute a : attributeSet.getAttributes()) {
				String name = a.getName();
				if ("xmlns:android".equals(name) || "xmlns:app".equals(name)
				|| "app:convertedWidget".equals(name)) continue;
				if ("android:id".equals(name) || "id".equals(name)) {
					// Skip AttributeSet id — write authoritative one below
					continue;
				}
				String rawVal = a.getValue() != null ? a.getValue().trim() : "";
				if (("app:customView".equals(name) || "customView".equals(name))
				&& (rawVal.isEmpty() || "none".equalsIgnoreCase(rawVal))) {
					continue;
				}
				HashMap<String, Object> attrMap = attributeStore.findAttributeMap(name, view);
				String value = formatAttributeValue(a, attrMap);
				sb.append("\n    ").append(name).append("=\"").append(value).append("\"");
			}
		} else {
			sb.append("\n    android:layout_width=\"wrap_content\"");
			sb.append("\n    android:layout_height=\"wrap_content\"");
		}

		if (authoritativeId != null && !authoritativeId.isEmpty()) {
			sb.append("\n    android:id=\"@+id/").append(authoritativeId).append("\"");
			wroteId = true;
		} else if (attributeSet != null) {
			Attribute idAttr = attributeSet.getAttribute("android:id");
			if (idAttr == null) idAttr = attributeSet.getAttribute("id");
			if (idAttr != null && idAttr.getValue() != null && !idAttr.getValue().trim().isEmpty()) {
				String v = idAttr.getValue().trim();
				if (!v.startsWith("@")) v = "@+id/" + v;
				sb.append("\n    android:id=\"").append(v).append("\"");
				wroteId = true;
			}
		}
		
		if (needsAppNs) {
			sb.append("\n    xmlns:app=\"http://schemas.android.com/apk/res-auto\"");
		}
		
		boolean isOpaque = isOpaqueSystemWidget(view);
		if (view instanceof ViewGroup && !isOpaque) {
			ViewGroup group = (ViewGroup) view;
			int userChildCount = 0;
			for (int i = 0; i < group.getChildCount(); i++) {
				View child = group.getChildAt(i);
				if (child != placeHolder && attributeStore.contains(child))
				userChildCount++;
			}
			if (userChildCount == 0) {
				sb.append(" />");
			} else {
				sb.append(">\n");
				for (int i = 0; i < group.getChildCount(); i++) {
					View child = group.getChildAt(i);
					if (child == placeHolder) continue;
					if (!attributeStore.contains(child)) continue;
					sb.append(indent(generateCode(child), 4)).append("\n");
				}
				sb.append("</").append(xmlTagName).append(">");
			}
		} else {
			sb.append(" />");
		}
		return sb.toString();
	}
	
	private boolean isOpaqueSystemWidget(View view) {
		String cls = view.getClass().getName();
		return cls.equals("android.widget.CalendarView")
		|| cls.equals("android.widget.DatePicker")
		|| cls.equals("android.widget.TimePicker")
		|| cls.equals("android.widget.NumberPicker")
		|| cls.equals("android.widget.SearchView")
		|| cls.equals("android.widget.ExpandableListView")
		|| cls.equals("android.widget.VideoView");
	}
	
	private String indent(String xml, int spaces) {
		StringBuilder pad = new StringBuilder();
		for (int i = 0; i < spaces; i++) pad.append(" ");
		return pad + xml.replace("\n", "\n" + pad);
	}
	
	private String formatAttributeValue(Attribute attr,
	HashMap<String, Object> map) {
		String value = attr.getValue();
		String attrName = attr.getName();
		if (value == null || value.isEmpty()) return "";
		if (attrName.startsWith("android:layout_margin")
		|| attrName.startsWith("android:padding")) {
			if (!value.endsWith("dp") && !value.endsWith("sp")
			&& !value.endsWith("px") && !value.endsWith("dip")
			&& !value.equals("match_parent") && !value.equals("wrap_content")) {
				try {
					float px = Float.parseFloat(value.trim());
					float dp = px / context.getResources().getDisplayMetrics().density;
					return Math.round(dp) + "dp";
				} catch (NumberFormatException ignored) {}
			}
			return value.replaceAll("\\.0$", "");
		}
		if (map != null && map.containsKey("dimension")) {
			String unit = map.get("dimension").toString();
			if (!value.endsWith(unit)) return value.replaceAll("\\.0$", "") + unit;
		}
		return value.replaceAll("\\.0$", "");
	}
	
	public boolean validateXML(String xmlContent) {
		try {
			org.xmlpull.v1.XmlPullParserFactory factory =
			org.xmlpull.v1.XmlPullParserFactory.newInstance();
			org.xmlpull.v1.XmlPullParser parser = factory.newPullParser();
			parser.setFeature(org.xmlpull.v1.XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(new java.io.StringReader(xmlContent));
			parser.nextTag();
			return true;
		} catch (Exception e) {
			Log.e("XmlGenerator", "XML validation failed: " + e.getMessage());
			return false;
		}
	}
	
	public static String prettyPrintXml(String raw) {
		if (raw == null || raw.trim().isEmpty()) return raw;
		try {
			String xml = raw.trim();
			xml = xml.replaceAll(">\\s+<", "><");
			StringBuilder out = new StringBuilder();
			int indent = 0;
			int i = 0;
			int n = xml.length();
			while (i < n) {
				if (xml.charAt(i) == '<') {
					int end = xml.indexOf('>', i);
					if (end < 0) {
						out.append(xml.substring(i));
						break;
					}
					String tag = xml.substring(i, end + 1);
					boolean closing = tag.startsWith("</");
					boolean selfClose = tag.endsWith("/>");
					boolean comment = tag.startsWith("<!--") || tag.startsWith("<?");
					if (closing && !comment) indent = Math.max(0, indent - 1);
					for (int s = 0; s < indent; s++) out.append("    ");
					out.append(tag).append("\n");
					if (!closing && !selfClose && !comment) indent++;
					i = end + 1;
				} else {
					int next = xml.indexOf('<', i);
					if (next < 0) next = n;
					String text = xml.substring(i, next).trim();
					if (!text.isEmpty()) {
						for (int s = 0; s < indent; s++) out.append("    ");
						out.append(text).append("\n");
					}
					i = next;
				}
			}
			return out.toString().trim() + "\n";
		} catch (Throwable t) {
			return raw;
		}
	}
	
	private String applyAppCompatInjection(String activityName, String layoutName, String xml) {
		if (xml == null) return xml;
		AppCompatInjectionReader inj = null;
		if (layoutName != null && !layoutName.trim().isEmpty()) {
			inj = AppCompatInjectionReader.load(scId, layoutName);
		}
		if (inj == null && activityName != null && !activityName.trim().isEmpty()) {
			inj = AppCompatInjectionReader.load(scId, activityName);
		}
		if (inj == null || inj.isEmpty()) return xml;
		
		String result = xml;
		result = overlay(result, inj.attrs("toolbar"), "@+id/_toolbar");
		result = overlay(result, inj.attrs("appbarlayout"), "@+id/_app_bar");
		result = overlay(result, inj.attrs("coordinatorlayout"), "@+id/_coordinator");
		result = overlay(result, inj.attrs("fab"), "@+id/_fab");
		result = overlay(result, inj.attrs("drawerlayout"), "@+id/_drawer");
		result = overlay(result, inj.attrs("navigationdrawer"), "@+id/_nav_view");
		
		result = overlayByTag(result, inj.attrs("toolbar"), "androidx.appcompat.widget.Toolbar");
		result = overlayByTag(result, inj.attrs("appbarlayout"), "com.google.android.material.appbar.AppBarLayout");
		result = overlayByTag(result, inj.attrs("coordinatorlayout"),
		"androidx.coordinatorlayout.widget.CoordinatorLayout");
		result = overlayByTag(result, inj.attrs("fab"),
		"com.google.android.material.floatingactionbutton.FloatingActionButton");
		result = overlayByTag(result, inj.attrs("drawerlayout"), "androidx.drawerlayout.widget.DrawerLayout");
		return result;
	}
	
	private String overlay(String xml, Map<String, String> attrs, String id) {
		if (attrs == null || attrs.isEmpty() || !xml.contains(id)) return xml;
		int idIdx = xml.indexOf(id);
		int tagStart = xml.lastIndexOf('<', idIdx);
		int tagEnd = xml.indexOf('>', idIdx);
		if (tagStart < 0 || tagEnd < 0) return xml;
		String openTag = xml.substring(tagStart, tagEnd + 1);
		String merged = mergeAttrs(openTag, attrs);
		return xml.substring(0, tagStart) + merged + xml.substring(tagEnd + 1);
	}
	
	private String overlayByTag(String xml, Map<String, String> attrs, String tagName) {
		if (attrs == null || attrs.isEmpty() || tagName == null) return xml;
		String result = xml;
		String open = "<" + tagName;
		int from = 0;
		while (true) {
			int start = result.indexOf(open, from);
			if (start < 0) break;
			int end = result.indexOf('>', start);
			if (end < 0) break;
			char prev = start > 0 ? result.charAt(start - 1) : ' ';
			char next = start + open.length() < result.length() ? result.charAt(start + open.length()) : ' ';
			if (prev == '<' || prev == '/') { from = start + 1; continue; }
			if (next != ' ' && next != '/' && next != '>' && next != '\n') { from = start + 1; continue; }
			String openTag = result.substring(start, end + 1);
			String merged = mergeAttrs(openTag, attrs);
			result = result.substring(0, start) + merged + result.substring(end + 1);
			from = start + merged.length();
		}
		return result;
	}
	
	private String mergeAttrs(String openTag, Map<String, String> attrs) {
		boolean selfClose = openTag.endsWith("/>");
		String tag = selfClose ? openTag.substring(0, openTag.length() - 2) : openTag.substring(0, openTag.length() - 1);
		for (Map.Entry<String, String> e : attrs.entrySet()) {
			String name = e.getKey();
			String value = e.getValue();
			if (name.startsWith("xmlns:") || value == null || value.isEmpty()) continue;
			String escaped = value.replace("&", "&amp;").replace("\"", "&quot;");
			java.util.regex.Matcher m = java.util.regex.Pattern
			.compile("\\s+" + java.util.regex.Pattern.quote(name) + "\\s*=\\s*\"[^\"]*\"")
			.matcher(tag);
			if (m.find()) {
				tag = m.replaceFirst(" " + name + "=\"" + escaped + "\"");
			} else {
				tag = tag + "\n    " + name + "=\"" + escaped + "\"";
			}
		}
		return tag + (selfClose ? " />" : ">");
	}
	
}
