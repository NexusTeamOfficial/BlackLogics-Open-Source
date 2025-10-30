package com.nexusteam.internal.os.layouteditor.util;

import com.apk.builder.FileUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.besome.blacklogics.R;
import com.besome.blacklogics.ViewEditorFragmentActivity;

import com.nexusteam.internal.os.layouteditor.*;
import com.nexusteam.internal.os.layouteditor.widget.Widget;
import com.nexusteam.internal.os.layouteditor.widget.WidgetTextView;
import com.nexusteam.internal.os.layouteditor.widget.WidgetImageView;
import com.nexusteam.internal.os.layouteditor.widget.WidgetButton;
import com.nexusteam.internal.os.layouteditor.widget.WidgetWebView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

/**
* ProjectStorageManager
* Handles saving, loading and generating Java/XML code for layouts
* Made by NexusTeam
*/
public class ProjectStorageManager {
	
    private final ViewEditorFragmentActivity activity;
	private final Context context;
	private final String projectPath;
	private final String projectsFile;
	
	public ProjectStorageManager(Context context, String projectPath, String projectsFile, ViewEditorFragmentActivity activity) {
		this.context = context;
		this.projectPath = projectPath;
		this.projectsFile = projectsFile;
        this.activity = activity;
	}
	
	/**
* SAVE LAYOUT
*/	
	public boolean saveLayout(LinearLayout ll, String activityName, String layoutName, String pkgName, boolean useAndroidX) {
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("activity_name", activityName);
			jsonObject.put("layout_name", layoutName);
			jsonObject.put("package_name", pkgName);
			jsonObject.put("use_androidx", useAndroidX);
			
			JSONArray widgetsArray = new JSONArray();
			for (int i = 0; i < ll.getChildCount(); i++) {
				View childAt = ((ViewGroup) ll.getChildAt(i)).getChildAt(0);
				ViewGroup.LayoutParams params = childAt.getLayoutParams();
				
				JSONObject widgetObject = new JSONObject();
				widgetObject.put("name_s", childAt.getClass().getSimpleName());
				widgetObject.put("id", WidgetUtil.getWidgetId(childAt));
				widgetObject.put("width", params.width);
				widgetObject.put("height", params.height);
				
				// Common properties
				widgetObject.put("visibility", childAt.getVisibility());
				widgetObject.put("alpha", childAt.getAlpha());
				widgetObject.put("rotation", childAt.getRotation());
				widgetObject.put("scaleX", childAt.getScaleX());
				widgetObject.put("scaleY", childAt.getScaleY());
				widgetObject.put("translationX", childAt.getTranslationX());
				widgetObject.put("translationY", childAt.getTranslationY());
				
				// Background
				if (childAt.getBackground() instanceof ColorDrawable) {
					int color = ((ColorDrawable) childAt.getBackground()).getColor();
					widgetObject.put("background_color", color);
				}
				
				// Margins
				if (params instanceof ViewGroup.MarginLayoutParams) {
					ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) params;
					widgetObject.put("margin_left", marginParams.leftMargin);
					widgetObject.put("margin_top", marginParams.topMargin);
					widgetObject.put("margin_right", marginParams.rightMargin);
					widgetObject.put("margin_bottom", marginParams.bottomMargin);
				}
				
				// Padding
				widgetObject.put("padding_left", childAt.getPaddingLeft());
				widgetObject.put("padding_top", childAt.getPaddingTop());
				widgetObject.put("padding_right", childAt.getPaddingRight());
				widgetObject.put("padding_bottom", childAt.getPaddingBottom());
				
				// TextView or Button attributes
				if (WidgetUtil.getTextViewOfWidget(childAt) != null) {
					TextView textView = WidgetUtil.getTextViewOfWidget(childAt);
					widgetObject.put("text", textView.getText().toString());
					widgetObject.put("text_size", textView.getTextSize());
					widgetObject.put("text_color", textView.getCurrentTextColor());
					widgetObject.put("gravity", textView.getGravity());
				}
				
				// ImageView
				if (childAt instanceof WidgetImageView) {
					WidgetImageView imageView = (WidgetImageView) childAt;
					String imagePath = WidgetUtil.getImagePath(childAt);
					if (imagePath != null) {
						widgetObject.put("image_path", imagePath);
					}
					widgetObject.put("scale_type", imageView.getScaleType().toString());
				}
				
				// WebView
				if (childAt instanceof WidgetWebView) {
					widgetObject.put("is_webview", true);
				}
				
				widgetsArray.put(widgetObject);
			}
			
			jsonObject.put("widgets", widgetsArray);
			
			// Load existing projects.json or create new
			JSONObject allProjects = new JSONObject();
			if (FileUtil.isExistFile(projectsFile)) {
				String existingData = TheBlockLogicsUtil.decodeBase64(TheBlockLogicsUtil.readFile(projectsFile));
				if (!TextUtils.isEmpty(existingData)) {
					allProjects = new JSONObject(existingData);
				}
			}

			// Add or update layout
			if (!allProjects.has("layouts")) {
				allProjects.put("layouts", new JSONObject());
			}
			allProjects.getJSONObject("layouts").put(layoutName, jsonObject);

			// Save to projects.json
			TheBlockLogicsUtil.writeFile(projectsFile, TheBlockLogicsUtil.encodeToBase64(allProjects.toString()));
			
			TheBlockLogicsUtil.showToast(context, "Project Saved!");
			return true;
		} catch (Exception e) {
			TheBlockLogicsUtil.showToast(context, "Save Error: " + e.toString());
			return false;
		}
	}
	
	/**
* LOAD LAYOUT
*/	
	public void loadLayout(LinearLayout ll, String layoutName) {
		ll.removeAllViews();
		try {
			if (FileUtil.isExistFile(projectsFile)) {
				String jsonData = TheBlockLogicsUtil.decodeBase64(TheBlockLogicsUtil.readFile(projectsFile));
				if (!TextUtils.isEmpty(jsonData)) {
					JSONObject allProjects = new JSONObject(jsonData);
					if (allProjects.has("layouts") && allProjects.getJSONObject("layouts").has(layoutName)) {
						JSONObject layoutData = allProjects.getJSONObject("layouts").getJSONObject(layoutName);
						JSONArray widgetsArray = layoutData.getJSONArray("widgets");

						for (int i = 0; i < widgetsArray.length(); i++) {
							JSONObject widgetData = widgetsArray.getJSONObject(i);

							LinearLayout container = new LinearLayout(context);
							container.setOrientation(LinearLayout.VERTICAL);
							container.setLayoutParams(new LinearLayout.LayoutParams(
								LinearLayout.LayoutParams.WRAP_CONTENT,
								LinearLayout.LayoutParams.WRAP_CONTENT));
							// Set drag listener to match ViewEditorFragmentActivity
							container.setOnDragListener(activity);

							String widgetName = widgetData.getString("name_s");
							String widgetId = widgetData.getString("id");
							int width = widgetData.getInt("width");
							int height = widgetData.getInt("height");

							LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
							View widget = null;

							if (widgetName.equals(WidgetTextView.class.getSimpleName())) {
								WidgetTextView widgetTextView = new WidgetTextView(context);
								widgetTextView.setLayoutParams(lp);
								widgetTextView.setWidgetId(widgetId);
								widgetTextView.setOnClickListener(new ViewEditorFragmentActivity.WidgetClickListener());
								widgetTextView.setOnLongClickListener(activity);
								if (widgetData.has("text")) {
									widgetTextView.getTextView().setText(widgetData.getString("text"));
								}
								if (widgetData.has("text_size")) {
									widgetTextView.getTextView().setTextSize(TypedValue.COMPLEX_UNIT_PX,
										(float) widgetData.getDouble("text_size"));
								}
								if (widgetData.has("text_color")) {
									widgetTextView.getTextView().setTextColor(widgetData.getInt("text_color"));
								}
								if (widgetData.has("gravity")) {
									widgetTextView.getTextView().setGravity(widgetData.getInt("gravity"));
								}
								widget = widgetTextView;
							} else if (widgetName.equals(WidgetButton.class.getSimpleName())) {
								WidgetButton widgetButton = new WidgetButton(context);
								widgetButton.setLayoutParams(lp);
								widgetButton.setWidgetId(widgetId);
								widgetButton.setOnClickListener(new ViewEditorFragmentActivity.WidgetClickListener());
								widgetButton.setOnLongClickListener(activity);
								if (widgetData.has("text")) {
									widgetButton.getTextView().setText(widgetData.getString("text"));
								}
								if (widgetData.has("text_size")) {
									widgetButton.getTextView().setTextSize(TypedValue.COMPLEX_UNIT_PX,
										(float) widgetData.getDouble("text_size"));
								}
								if (widgetData.has("text_color")) {
									widgetButton.getTextView().setTextColor(widgetData.getInt("text_color"));
								}
								if (widgetData.has("gravity")) {
									widgetButton.getTextView().setGravity(widgetData.getInt("gravity"));
								}
								widget = widgetButton;
							} else if (widgetName.equals(WidgetImageView.class.getSimpleName())) {
								WidgetImageView widgetImageView = new WidgetImageView(context);
								widgetImageView.setLayoutParams(lp);
								widgetImageView.setWidgetId(widgetId);
								widgetImageView.setOnClickListener(new ViewEditorFragmentActivity.WidgetClickListener());
								widgetImageView.setOnLongClickListener(activity);
								if (widgetData.has("image_path")) {
									String imagePath = widgetData.getString("image_path");
									Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
									if (bitmap != null) {
										widgetImageView.setImageBitmap(bitmap);
									}
								}
								widget = widgetImageView;
							} else if (widgetName.equals(WidgetWebView.class.getSimpleName())) {
								WidgetWebView widgetWebView = new WidgetWebView(context);
								widgetWebView.setLayoutParams(lp);
								widgetWebView.setWidgetId(widgetId);
								widgetWebView.setOnClickListener(new ViewEditorFragmentActivity.WidgetClickListener());
								widgetWebView.setOnLongClickListener(activity);
								widget = widgetWebView;
							}

							if (widget != null) {
								if (widgetData.has("visibility")) {
									widget.setVisibility(widgetData.getInt("visibility"));
								}
								if (widgetData.has("alpha")) {
									widget.setAlpha((float) widgetData.getDouble("alpha"));
								}
								if (widgetData.has("rotation")) {
									widget.setRotation((float) widgetData.getDouble("rotation"));
								}
								if (widgetData.has("scaleX")) {
									widget.setScaleX((float) widgetData.getDouble("scaleX"));
								}
								if (widgetData.has("scaleY")) {
									widget.setScaleY((float) widgetData.getDouble("scaleY"));
								}
								if (widgetData.has("translationX")) {
									widget.setTranslationX((float) widgetData.getDouble("translationX"));
								}
								if (widgetData.has("translationY")) {
									widget.setTranslationY((float) widgetData.getDouble("translationY"));
								}
								if (widgetData.has("background_color")) {
									widget.setBackgroundColor(widgetData.getInt("background_color"));
								}

								if (widgetData.has("margin_left") || widgetData.has("margin_top") ||
									widgetData.has("margin_right") || widgetData.has("margin_bottom")) {
									int left = widgetData.optInt("margin_left", 0);
									int top = widgetData.optInt("margin_top", 0);
									int right = widgetData.optInt("margin_right", 0);
									int bottom = widgetData.optInt("margin_bottom", 0);
									((ViewGroup.MarginLayoutParams) lp).setMargins(left, top, right, bottom);
								}

								int pLeft = widgetData.optInt("padding_left", 0);
								int pTop = widgetData.optInt("padding_top", 0);
								int pRight = widgetData.optInt("padding_right", 0);
								int pBottom = widgetData.optInt("padding_bottom", 0);
								widget.setPadding(pLeft, pTop, pRight, pBottom);

								container.addView(widget);
								ll.addView(container);
							}
						}
					} else {
						TheBlockLogicsUtil.showToast(context, "No layout found for: " + layoutName);
					}
				}
			} else {
				TheBlockLogicsUtil.showToast(context, "No saved projects found");
			}
		} catch (Exception e) {
			TheBlockLogicsUtil.showToast(context, "Load Error: " + e.toString());
		}
	}
	
	/**
* GENERATE JAVA CODE
*/	
	public String generateJavaCode(LinearLayout ll, String activityName, String layoutName, String pkgName, boolean useAndroidX) {
		StringBuilder javaCode = new StringBuilder();
		javaCode.append("package ").append(pkgName).append(";\n\n");
		
		if (useAndroidX) {
			javaCode.append("import androidx.appcompat.app.AppCompatActivity;\n");
		} else {
			javaCode.append("import android.app.Activity;\n");
		}
		javaCode.append("import android.os.Bundle;\n");
		javaCode.append("import android.widget.*;\n\n");
		
		javaCode.append("public class ").append(activityName).append(" extends ")
			.append(useAndroidX ? "AppCompatActivity" : "Activity").append(" {\n\n");
		
		// Fields
		for (int i = 0; i < ll.getChildCount(); i++) {
			View child = ((ViewGroup) ll.getChildAt(i)).getChildAt(0);
			String widgetType = child.getClass().getSimpleName();
			
			if (widgetType.equals("WidgetButton")) widgetType = "Button";
			if (widgetType.equals("WidgetTextView")) widgetType = "TextView";
			if (widgetType.equals("WidgetImageView")) widgetType = "ImageView";
			if (widgetType.equals("WidgetWebView")) widgetType = "WebView";
			
			String widgetId = WidgetUtil.getWidgetId(child);
			if (!TextUtils.isEmpty(widgetId)) {
				javaCode.append("    private ").append(widgetType).append(" ").append(widgetId).append(";\n");
			}
		}
		
		javaCode.append("\n    @Override\n");
		javaCode.append("    protected void onCreate(Bundle savedInstanceState) {\n");
		javaCode.append("        super.onCreate(savedInstanceState);\n");
		javaCode.append("        setContentView(R.layout.").append(layoutName).append(");\n\n");
		
		for (int i = 0; i < ll.getChildCount(); i++) {
			View child = ((ViewGroup) ll.getChildAt(i)).getChildAt(0);
			String widgetId = WidgetUtil.getWidgetId(child);
			if (!TextUtils.isEmpty(widgetId)) {
				javaCode.append("        ").append(widgetId).append(" = findViewById(R.id.").append(widgetId).append(");\n");
			}
		}
		
		javaCode.append("    }\n");
		javaCode.append("}\n");
		
		return javaCode.toString();
	}
	
	/**
* GENERATE XML LAYOUT
*/	
	public String generateXmlLayout(LinearLayout ll, String layoutName) {
		StringBuilder xmlCode = new StringBuilder();
		xmlCode.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		xmlCode.append("<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n");
		xmlCode.append("    android:layout_width=\"match_parent\"\n");
		xmlCode.append("    android:layout_height=\"match_parent\"\n");
		xmlCode.append("    android:orientation=\"vertical\">\n\n");
		
		for (int i = 0; i < ll.getChildCount(); i++) {
			View child = ((ViewGroup) ll.getChildAt(i)).getChildAt(0);
			String widgetType = child.getClass().getSimpleName();
			String widgetId = WidgetUtil.getWidgetId(child);
			ViewGroup.LayoutParams params = child.getLayoutParams();
			
			// Map custom to Android
			widgetType = widgetType.equals("WidgetButton") ? "Button" :
			widgetType.equals("WidgetTextView") ? "TextView" :
			widgetType.equals("WidgetImageView") ? "ImageView" :
			widgetType.equals("WidgetWebView") ? "WebView" :
			widgetType;
			
			xmlCode.append("    <").append(widgetType).append("\n");
			xmlCode.append("        android:id=\"@+id/").append(widgetId).append("\"\n");
			xmlCode.append("        android:layout_width=\"")
				.append(params.width == -1 ? "match_parent" : params.width == -2 ? "wrap_content" : params.width + "dp").append("\"\n");
			xmlCode.append("        android:layout_height=\"")
				.append(params.height == -1 ? "match_parent" : params.height == -2 ? "wrap_content" : params.height + "dp").append("\"");
			
			// TextViews
			if (child instanceof WidgetTextView) {
				WidgetTextView tv = (WidgetTextView) child;
				if (!TextUtils.isEmpty(tv.getText())) {
					xmlCode.append("\n        android:text=\"").append(tv.getText().toString()).append("\"");
				}
				if (tv.getTextSize() > 0) {
					xmlCode.append("\n        android:textSize=\"").append(tv.getTextSize() / context.getResources().getDisplayMetrics().scaledDensity).append("sp\"");
				}
				if (tv.getCurrentTextColor() != 0) {
					xmlCode.append("\n        android:textColor=\"#").append(Integer.toHexString(tv.getCurrentTextColor())).append("\"");
				}
				if (tv.getGravity() != Gravity.START) {
					xmlCode.append("\n        android:gravity=\"").append(gravityToString(tv.getGravity())).append("\"");
				}
			}
			
			// ImageViews
			if (child instanceof WidgetImageView) {
				WidgetImageView iv = (WidgetImageView) child;
				String imagePath = WidgetUtil.getImagePath(child);
				if (imagePath != null) {
					xmlCode.append("\n        android:src=\"@drawable/").append(new File(imagePath).getName().replace(".png", "").replace(".jpg", "")).append("\"");
				}
			}
			
			xmlCode.append(" />\n\n");
		}
		
		xmlCode.append("</LinearLayout>");
		return xmlCode.toString();
	}
	
	private String gravityToString(int gravity) {
		switch (gravity) {
			case Gravity.CENTER: return "center";
			case Gravity.CENTER_HORIZONTAL: return "center_horizontal";
			case Gravity.CENTER_VERTICAL: return "center_vertical";
			case Gravity.START: return "start";
			case Gravity.END: return "end";
			case Gravity.LEFT: return "left";
			case Gravity.RIGHT: return "right";
			case Gravity.TOP: return "top";
			case Gravity.BOTTOM: return "bottom";
			default: return "start";
		}
	}
}