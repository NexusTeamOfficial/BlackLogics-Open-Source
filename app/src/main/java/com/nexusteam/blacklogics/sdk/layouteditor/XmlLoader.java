package com.nexusteam.blacklogics.sdk.layouteditor;

import android.view.View;
import android.view.ViewGroup;

import com.nexusteam.blacklogics.SketchwareUtil;
import com.nexusteam.blacklogics.sdk.AttributeSet;
import com.nexusteam.blacklogics.sdk.utils.AttributeUtils;
import com.nexusteam.blacklogics.sdk.widgets.PlaceholderWidget;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.Stack;

class XmlLoader {

	private final ViewEditor editor;

	XmlLoader(ViewEditor editor) {
		this.editor = editor;
	}

	void loadFromXml(String xml) throws Exception {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		XmlPullParser parser = factory.newPullParser();
		parser.setInput(new StringReader(xml));

		Stack<ViewGroup> groupStack = new Stack<ViewGroup>();
		Stack<String> tagStack = new Stack<String>();
		groupStack.push(editor.editorLayout);
		tagStack.push("__root__");

		int type;
		while ((type = parser.next()) != XmlPullParser.END_DOCUMENT) {
			if (type == XmlPullParser.START_TAG) {
				String tag = parser.getName();
				String previewTag = null;
				String viewId = null;
				String layoutWidth = null;
				String layoutHeight = null;
				String orientation = null;
				boolean hasRealAttrs = false;

				for (int i = 0; i < parser.getAttributeCount(); i++) {
					String n = parser.getAttributeName(i);
					String v = parser.getAttributeValue(i);
					if (!(n.startsWith("xmlns") || n.startsWith("tools:")
							|| "android:id".equals(n) || "id".equals(n)
							|| "android:layout_width".equals(n) || "layout_width".equals(n)
							|| "android:layout_height".equals(n) || "layout_height".equals(n)
							|| (("android:orientation".equals(n) || "orientation".equals(n))
									&& "vertical".equals(v)))) {
						hasRealAttrs = true;
					}
					if ("android:id".equals(n) || "id".equals(n)) {
						viewId = AttributeUtils.getName(v);
					} else if ("app:convertedWidget".equals(n)
					|| "convertedWidget".equals(n)) {
						previewTag = v;
					} else if ("android:layout_width".equals(n) || "layout_width".equals(n)) {
						layoutWidth = v;
					} else if ("android:layout_height".equals(n) || "layout_height".equals(n)) {
						layoutHeight = v;
					} else if ("android:orientation".equals(n) || "orientation".equals(n)) {
						orientation = v;
					}
				}

				// The activity FAB (@+id/_fab) lives on the canvas overlay, not in the widget tree.
				// Merge its XML attributes into that real FAB instead of creating a duplicate widget.
				if ("_fab".equals(viewId) && tag != null && tag.endsWith("FloatingActionButton")) {
					java.util.LinkedHashMap<String, String> fabAttrs =
							new java.util.LinkedHashMap<String, String>();
					for (int i = 0; i < parser.getAttributeCount(); i++) {
						fabAttrs.put(parser.getAttributeName(i), parser.getAttributeValue(i));
					}
					if (editor.mergeXmlIntoEditorFab(fabAttrs)) continue;
				}

				// Skip synthetic root wrappers produced by XmlGenerator
				// (LinearLayout / CoordinatorLayout / FrameLayout with no real id,
				// match_parent x match_parent). Their children must become direct
				// children of editorLayout, otherwise an extra LinearLayout appears
				// after every save → reload / backup restore / XML sync.
				if (groupStack.size() == 1 && !hasRealAttrs
						&& isSyntheticRoot(tag, viewId, layoutWidth, layoutHeight)) {
					// Do not create a View, do not push onto stacks.
					// Children will be added to editorLayout; END_TAG for this root
					// will see tagStack.size()==1 and will not pop.
					continue;
				}

				boolean isConvertedWidget = (previewTag != null && !previewTag.isEmpty());

				String renderTag = isConvertedWidget ? previewTag : tag;
				View view = editor.createPlaceholderOrStandardViewPublic(renderTag);
				if (view == null) view = new PlaceholderWidget(
				editor.getContext(), renderTag);

				AttributeSet set = new AttributeSet();

				boolean xmlHadId = viewId != null && !viewId.isEmpty();
				String finalId;
				if (xmlHadId) {
					// Preserve the real ID from XML. Only uniquify if truly taken
					// (should not happen after clearAllIds). Never replace with
					// className+number like switchmaterial1.
					finalId = viewId;
					if (editor.idManager.isIdUsed(finalId)) {
						int n = 2;
						String candidate = finalId + "_" + n;
						while (editor.idManager.isIdUsed(candidate)) {
							n++;
							candidate = finalId + "_" + n;
						}
						android.util.Log.w("XmlLoader", "id conflict: '" + finalId
								+ "' already used — keeping base, using '" + candidate + "'");
						finalId = candidate;
					}
				} else {
					finalId = editor.idManager.generateNewId(view);
				}
				editor.idManager.addNewId(view, finalId);
				final String registeredId = finalId;

				for (int i = 0; i < parser.getAttributeCount(); i++) {
					String n = parser.getAttributeName(i);
					String v = parser.getAttributeValue(i);

					if ("android:id".equals(n) || "id".equals(n)) {
						set.add(new Attribute("android:id", "@+id/" + registeredId));
					} else if ("app:convertedWidget".equals(n) || "convertedWidget".equals(n)) {
						set.add(isConvertedWidget
						? new Attribute(n, tag, previewTag, tag)
						: new Attribute(n, v));
					} else {
						set.add(isConvertedWidget
						? new Attribute(n, v, previewTag, tag)
						: new Attribute(n, v));
					}
				}
				if (!xmlHadId) set.add(new Attribute("android:id", "@+id/" + registeredId));
				editor.putAttributeSetPublic(view, set);

				groupStack.peek().addView(view);
				editor.attachEditorClickListener(view);
				editor.attachDragAndClickListener(view);

				if (view instanceof ViewGroup) {
					ViewGroup vg = (ViewGroup) view;
					vg.setOnDragListener(editor.getDragListenerPublic());
					editor.applyContainerStrokePublic(vg);
					vg.setMinimumHeight((int) SketchwareUtil.getDip(editor.getContext(), 30));
					groupStack.push(vg);
					tagStack.push(tag);
				}

			} else if (type == XmlPullParser.END_TAG) {
				if (tagStack.size() > 1
				&& parser.getName().equals(tagStack.peek())) {
					groupStack.pop();
					tagStack.pop();
				}
			}
		}

		editor.applyAllAttributesAfterLoadPublic();
	}

	/**
	 * True for the dummy root that XmlGenerator always emits
	 * (LinearLayout / CoordinatorLayout / FrameLayout, match_parent×match_parent,
	 * either no id or a chrome id like _coordinator / _drawer).
	 */
	private static boolean isSyntheticRoot(String tag, String viewId,
			String layoutWidth, String layoutHeight) {
		if (tag == null) return false;
		boolean isContainer =
				"LinearLayout".equals(tag)
				|| "FrameLayout".equals(tag)
				|| "androidx.coordinatorlayout.widget.CoordinatorLayout".equals(tag)
				|| tag.endsWith(".CoordinatorLayout")
				|| "androidx.drawerlayout.widget.DrawerLayout".equals(tag)
				|| tag.endsWith(".DrawerLayout");
		if (!isContainer) return false;

		boolean noRealId = (viewId == null || viewId.isEmpty()
				|| viewId.startsWith("_") /* _coordinator, _drawer, _app_bar ... */);
		if (!noRealId) return false;

		boolean matchParentW = layoutWidth == null
				|| "match_parent".equals(layoutWidth)
				|| "fill_parent".equals(layoutWidth)
				|| "-1".equals(layoutWidth);
		boolean matchParentH = layoutHeight == null
				|| "match_parent".equals(layoutHeight)
				|| "fill_parent".equals(layoutHeight)
				|| "-1".equals(layoutHeight);
		return matchParentW && matchParentH;
	}
}
