package com.nexusteam.blacklogics.sdk.layouteditor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

public final class BuiltInAttributes {

	private BuiltInAttributes() {}

	public static HashMap<String, ArrayList<HashMap<String, Object>>> getAll() {
		HashMap<String, ArrayList<HashMap<String, Object>>> map =
				new LinkedHashMap<String, ArrayList<HashMap<String, Object>>>();
		map.put("android.widget.LinearLayout", attrs(
				attr("member_type", "method", "name", "Gravity", "attribute_name", "android:gravity", "member_name", "setGravity", "argument_type", "flag", "java_arguments", list("android.view.Gravity$LEFT", "android.view.Gravity$RIGHT", "android.view.Gravity$BOTTOM", "android.view.Gravity$TOP", "android.view.Gravity$CENTER", "android.view.Gravity$CENTER_HORIZONTAL", "android.view.Gravity$CENTER_VERTICAL"), "xml_arguments", list("left", "right", "bottom", "top", "center", "center_horizontal", "center_vertical")),
				attr("member_type", "method", "name", "Orientation", "attribute_name", "android:orientation", "member_name", "setOrientation", "argument_type", "enum", "java_arguments", list("android.widget.LinearLayout$HORIZONTAL", "android.widget.LinearLayout$VERTICAL"), "xml_arguments", list("horizontal", "vertical")),
				attr("member_type", "method", "name", "Weight Sum", "attribute_name", "android:weightSum", "member_name", "setWeightSum", "argument_type", "float"),
				attr("member_type", "method", "name", "Baseline Aligned", "attribute_name", "android:baselineAligned", "member_name", "setBaselineAligned", "argument_type", "boolean")));

		map.put("android.widget.TextView", attrs(
				attr("member_type", "method", "name", "Text", "attribute_name", "android:text", "member_name", "setText", "argument_type", "String"),
				attr("member_type", "method", "name", "Text Size", "attribute_name", "android:textSize", "member_name", "setTextSize", "argument_type", "float", "dimension", "sp"),
				attr("member_type", "method", "name", "Text Color", "attribute_name", "android:textColor", "member_name", "setTextColor", "argument_type", "Color"),
				attr("member_type", "method", "name", "Gravity", "attribute_name", "android:gravity", "member_name", "setGravity", "argument_type", "flag", "java_arguments", list("android.view.Gravity$LEFT", "android.view.Gravity$RIGHT", "android.view.Gravity$BOTTOM", "android.view.Gravity$TOP", "android.view.Gravity$CENTER", "android.view.Gravity$CENTER_HORIZONTAL", "android.view.Gravity$CENTER_VERTICAL"), "xml_arguments", list("left", "right", "bottom", "top", "center", "center_horizontal", "center_vertical")),
				attr("member_type", "method", "name", "Max Lines", "attribute_name", "android:maxLines", "member_name", "setMaxLines", "argument_type", "int"),
				attr("member_type", "method", "name", "Min Lines", "attribute_name", "android:minLines", "member_name", "setMinLines", "argument_type", "int"),
				attr("member_type", "method", "name", "Lines", "attribute_name", "android:lines", "member_name", "setLines", "argument_type", "int"),
				attr("member_type", "method", "name", "Single Line", "attribute_name", "android:singleLine", "member_name", "setSingleLine", "argument_type", "boolean"),
				attr("member_type", "method", "name", "All Caps", "attribute_name", "android:textAllCaps", "member_name", "setAllCaps", "argument_type", "boolean"),
				attr("member_type", "method", "name", "Letter Spacing", "attribute_name", "android:letterSpacing", "member_name", "setLetterSpacing", "argument_type", "float"),
				attr("member_type", "method", "name", "Hint Color", "attribute_name", "android:textColorHint", "member_name", "setHintTextColor", "argument_type", "Color"),
				attr("member_type", "method", "name", "Ellipsize", "attribute_name", "android:ellipsize", "member_name", "setEllipsize", "argument_type", "enum", "java_arguments", list("android.text.TextUtils$TruncateAt$START", "android.text.TextUtils$TruncateAt$MIDDLE", "android.text.TextUtils$TruncateAt$END"), "xml_arguments", list("start", "middle", "end"))));

		map.put("android.widget.CompoundButton", attrs(
				attr("member_type", "method", "name", "Set Checked", "attribute_name", "android:checked", "member_name", "setChecked", "argument_type", "boolean")));

		map.put("android.widget.ImageView", attrs(
				attr("member_type", "method", "name", "Image", "attribute_name", "android:src", "member_name", "setImageDrawable", "argument_type", "Drawable"),
				attr("member_type", "method", "name", "Set Tint", "attribute_name", "android:tint", "member_name", "setColorFilter", "argument_type", "Color"),
				attr("member_type", "method", "java_arguments", list("android.widget.ImageView$ScaleType$MATRIX", "android.widget.ImageView$ScaleType$FIT_XY", "android.widget.ImageView$ScaleType$FIT_START", "android.widget.ImageView$ScaleType$FIT_CENTER", "android.widget.ImageView$ScaleType$FIT_END", "android.widget.ImageView$ScaleType$CENTER", "android.widget.ImageView$ScaleType$CENTER_CROP", "android.widget.ImageView$ScaleType$CENTER_INSIDE"), "xml_arguments", list("matrix", "fitXY", "fitStart", "fitCenter", "fitEnd", "center", "centerCrop", "centerInside"), "name", "Scale Type", "attribute_name", "android:scaleType", "argument_class", "android.widget.ImageView$ScaleType$", "member_name", "setScaleType", "argument_type", "enum"),
				attr("member_type", "method", "name", "Adjust View Bounds", "attribute_name", "android:adjustViewBounds", "member_name", "setAdjustViewBounds", "argument_type", "boolean")));

		map.put("android.view.View", attrs(
				attr("member_type", "field", "name", "Width", "attribute_name", "android:layout_width", "layout_params", true, "member_name", "width", "argument_type", "Size"),
				attr("member_type", "field", "name", "Height", "attribute_name", "android:layout_height", "layout_params", true, "member_name", "height", "argument_type", "Size"),
				attr("member_type", "method", "name", "Background Color", "attribute_name", "android:background", "member_name", "setBackgroundColor", "argument_type", "Color"),
				attr("member_type", "method", "name", "Elevation", "attribute_name", "android:elevation", "member_name", "setElevation", "argument_type", "float", "dp", true, "dimension", "dp"),
				attr("member_type", "method", "name", "Alpha", "attribute_name", "android:alpha", "member_name", "setAlpha", "argument_type", "float"),
				attr("member_type", "method", "name", "Translation Y", "attribute_name", "android:transitionY", "member_name", "setTranslationY", "argument_type", "float", "dimension", "dp"),
				attr("member_type", "method", "name", "Translation X", "attribute_name", "android:transitionX", "member_name", "setTranslationX", "argument_type", "float", "dimension", "dp"),
				attr("member_type", "method", "name", "Scale X", "attribute_name", "android:scaleX", "member_name", "setScaleX", "argument_type", "float"),
				attr("member_type", "method", "name", "Scale Y", "attribute_name", "android:scaleY", "member_name", "setScaleY", "argument_type", "float"),
				attr("member_type", "method", "name", "Convert Widget", "attribute_name", "app:convertedWidget", "member_name", "setConvertedWidget", "argument_type", "String", "description", "Convert this view to a custom widget class"),
				attr("member_type", "method", "name", "Padding Settings", "attribute_name", "android:padding_all", "member_name", "setPaddingAll", "argument_type", "custom", "description", "Set padding for all sides"),
				attr("member_type", "field", "name", "Margin Settings", "attribute_name", "android:layout_margin_all", "layout_params", true, "member_name", "setMarginAll", "argument_type", "custom", "description", "Set margin for all sides"),
				attr("member_type", "method", "name", "Visibility", "attribute_name", "android:visibility", "member_name", "setVisibility", "argument_type", "enum", "java_arguments", list("android.view.View$VISIBLE", "android.view.View$INVISIBLE", "android.view.View$GONE"), "xml_arguments", list("visible", "invisible", "gone")),
				attr("member_type", "method", "name", "Enabled", "attribute_name", "android:enabled", "member_name", "setEnabled", "argument_type", "boolean"),
				attr("member_type", "method", "name", "Clickable", "attribute_name", "android:clickable", "member_name", "setClickable", "argument_type", "boolean"),
				attr("member_type", "method", "name", "Focusable", "attribute_name", "android:focusable", "member_name", "setFocusable", "argument_type", "boolean"),
				attr("member_type", "method", "name", "Rotation", "attribute_name", "android:rotation", "member_name", "setRotation", "argument_type", "float"),
				attr("member_type", "method", "name", "Min Width", "attribute_name", "android:minWidth", "member_name", "setMinimumWidth", "argument_type", "int", "dimension", "dp"),
				attr("member_type", "method", "name", "Min Height", "attribute_name", "android:minHeight", "member_name", "setMinimumHeight", "argument_type", "int", "dimension", "dp")));

		map.put("android.widget.EditText", attrs(
				attr("member_type", "method", "name", "Hint", "attribute_name", "android:hint", "member_name", "setHint", "argument_type", "String"),
				attr("member_type", "method", "name", "Select All On Focus", "attribute_name", "android:selectAllOnFocus", "member_name", "setSelectAllOnFocus", "argument_type", "boolean")));

		map.put("com.google.android.material.appbar.CollapsingToolbarLayout", attrs(
				attr("member_type", "method", "name", "app:title", "attribute_name", "app:title", "member_name", "setTitle", "argument_type", "String")));

		map.put("android.widget.ScrollView", attrs(
				attr("member_type", "method", "name", "FillViewport", "attribute_name", "android:fillViewport", "member_name", "setFillViewport", "argument_type", "boolean")));

		map.put("android.widget.ListView", attrs(
				attr("member_type", "method", "name", "Custom View", "attribute_name", "app:customView", "member_name", "setCustomView", "argument_type", "customView", "description", "Select a custom view for this ListView adapter item layout")));

		map.put("android.widget.GridView", attrs(
				attr("member_type", "method", "name", "Custom View", "attribute_name", "app:customView", "member_name", "setCustomView", "argument_type", "customView", "description", "Select a custom view for this GridView adapter item layout")));

		map.put("android.widget.ExpandableListView", attrs(
				attr("member_type", "method", "name", "Custom View", "attribute_name", "app:customView", "member_name", "setCustomView", "argument_type", "customView", "description", "Select a custom view for this ExpandableListView item layout")));

		map.put("android.widget.Spinner", attrs(
				attr("member_type", "method", "name", "Custom View", "attribute_name", "app:customView", "member_name", "setCustomView", "argument_type", "customView", "description", "Select a custom view for this Spinner item layout")));

		map.put("androidx.recyclerview.widget.RecyclerView", attrs(
				attr("member_type", "method", "name", "Custom View", "attribute_name", "app:customView", "member_name", "setCustomView", "argument_type", "customView", "description", "Select a custom view for this RecyclerView item layout")));


		map.put("com.google.android.material.floatingactionbutton.FloatingActionButton", attrs(
				attr("member_type", "method",
						"name", "Size",
						"attribute_name", "app:fabSize",
						"member_name", "setSize",
						"argument_type", "enum",
						"java_arguments", list("0", "1", "-1"),
						"xml_arguments", list("auto", "normal", "mini")),
				attr("member_type", "method",
						"name", "Background Tint",
						"attribute_name", "app:backgroundTint",
						"member_name", "setBackgroundTintList",
						"argument_type", "Color"),
				attr("member_type", "method",
						"name", "Icon",
						"attribute_name", "app:srcCompat",
						"member_name", "setImageResource",
						"argument_type", "image"),
				attr("member_type", "method",
						"name", "Icon Tint",
						"attribute_name", "app:tint",
						"member_name", "setImageTintList",
						"argument_type", "Color"),
				attr("member_type", "method",
						"name", "Elevation",
						"attribute_name", "app:elevation",
						"member_name", "setElevation",
						"argument_type", "dimension"),
				attr("member_type", "method",
						"name", "Use Compat Padding",
						"attribute_name", "app:useCompatPadding",
						"member_name", "setUseCompatPadding",
						"argument_type", "boolean"),
				attr("member_type", "method",
						"name", "Ripple Color",
						"attribute_name", "app:rippleColor",
						"member_name", "setRippleColor",
						"argument_type", "Color"),
				attr("member_type", "method",
						"name", "Custom Size",
						"attribute_name", "app:fabCustomSize",
						"member_name", "setCustomSize",
						"argument_type", "dimension"),
				attr("member_type", "method",
						"name", "Visibility",
						"attribute_name", "android:visibility",
						"member_name", "setVisibility",
						"argument_type", "enum",
						"java_arguments", list("android.view.View$VISIBLE", "android.view.View$INVISIBLE", "android.view.View$GONE"),
						"xml_arguments", list("visible", "invisible", "gone")),
				attr("member_type", "method",
						"name", "Content Description",
						"attribute_name", "android:contentDescription",
						"member_name", "setContentDescription",
						"argument_type", "String"),
				attr("member_type", "method",
						"name", "Enabled",
						"attribute_name", "android:enabled",
						"member_name", "setEnabled",
						"argument_type", "boolean")
		));

		return map;
	}


	@SafeVarargs
	private static ArrayList<HashMap<String, Object>> attrs(HashMap<String, Object>... items) {
		return new ArrayList<HashMap<String, Object>>(Arrays.asList(items));
	}

	private static HashMap<String, Object> attr(Object... kv) {
		LinkedHashMap<String, Object> m = new LinkedHashMap<String, Object>();
		for (int i = 0; i + 1 < kv.length; i += 2) m.put((String) kv[i], kv[i + 1]);
		return m;
	}

	private static ArrayList<String> list(String... v) {
		return new ArrayList<String>(Arrays.asList(v));
	}
}
