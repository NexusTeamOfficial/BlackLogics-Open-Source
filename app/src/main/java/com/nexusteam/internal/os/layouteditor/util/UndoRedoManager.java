package com.nexusteam.internal.os.layouteditor;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;
import com.besome.blacklogics.R;
import com.besome.blacklogics.ViewEditorFragmentActivity;
import com.nexusteam.internal.os.layouteditor.util.TheBlockLogicsUtil;
import com.nexusteam.internal.os.layouteditor.util.WidgetUtil;
import com.nexusteam.internal.os.layouteditor.widget.WidgetButton;
import com.nexusteam.internal.os.layouteditor.widget.WidgetImageView;
import com.nexusteam.internal.os.layouteditor.widget.WidgetTextView;
import com.nexusteam.internal.os.layouteditor.widget.WidgetWebView;
import com.nexusteam.internal.os.layouteditor.widget.WidgetListView;
import com.nexusteam.internal.os.layouteditor.widget.WidgetLinear;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Stack;

public class UndoRedoManager {
	private Stack<String> undoStack = new Stack<>();
	private Stack<String> redoStack = new Stack<>();
	private boolean isProgrammaticChange = false;
    
    private final ViewEditorFragmentActivity activity;
	private Context context;
	private LinearLayout ll;
	private String activityName;
	private String layoutName;
	private String pkgName;
	private boolean useAndroidX;
	
	public UndoRedoManager(Context context, LinearLayout ll, String activityName, String layoutName, String pkgName, boolean useAndroidX, ViewEditorFragmentActivity activity) {
		this.context = context;
		this.ll = ll;
		this.activityName = activityName;
		this.layoutName = layoutName;
		this.pkgName = pkgName;
		this.useAndroidX = useAndroidX;
        this.activity = activity;
	}
	
	public void saveStateToUndo() {
		if (isProgrammaticChange) return;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("activity_name", activityName);
			jsonObject.put("layout_name", layoutName);
			jsonObject.put("package_name", pkgName);
			jsonObject.put("use_androidx", useAndroidX);
			
			JSONArray widgetsArray = new JSONArray();
			for (int i = 0; i < ll.getChildCount(); i++) {
				View childAt = ((ViewGroup) ll.getChildAt(i)).getChildAt(0);
				widgetsArray.put(createWidgetJson(childAt));
			}
			jsonObject.put("widgets", widgetsArray);
			
			undoStack.push(jsonObject.toString());
			redoStack.clear();
		} catch (Exception e) {
			TheBlockLogicsUtil.showToast(context, "Error saving state: " + e.toString());
		}
	}
	
	public void undo() {
		if (undoStack.isEmpty()) {
			TheBlockLogicsUtil.showToast(context, "Nothing to undo");
			return;
		}
		
		isProgrammaticChange = true;
		try {
			String currentState = undoStack.pop();
			redoStack.push(getCurrentState());
			restoreState(currentState);
		} catch (Exception e) {
			TheBlockLogicsUtil.showToast(context, "Undo error: " + e.toString());
		} finally {
			isProgrammaticChange = false;
		}
	}
	
	public void redo() {
		if (redoStack.isEmpty()) {
			TheBlockLogicsUtil.showToast(context, "Nothing to redo");
			return;
		}
		
		isProgrammaticChange = true;
		try {
			String redoState = redoStack.pop();
			undoStack.push(getCurrentState());
			restoreState(redoState);
		} catch (Exception e) {
			TheBlockLogicsUtil.showToast(context, "Redo error: " + e.toString());
		} finally {
			isProgrammaticChange = false;
		}
	}
	
	private String getCurrentState() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("activity_name", activityName);
		jsonObject.put("layout_name", layoutName);
		jsonObject.put("package_name", pkgName);
		jsonObject.put("use_androidx", useAndroidX);
		
		JSONArray widgetsArray = new JSONArray();
		for (int i = 0; i < ll.getChildCount(); i++) {
			View childAt = ((ViewGroup) ll.getChildAt(i)).getChildAt(0);
			widgetsArray.put(createWidgetJson(childAt));
		}
		jsonObject.put("widgets", widgetsArray);
		return jsonObject.toString();
	}
	
	private JSONObject createWidgetJson(View child) throws JSONException {
		JSONObject widgetObject = new JSONObject();
		ViewGroup.LayoutParams params = child.getLayoutParams();
		
		widgetObject.put("name_s", child.getClass().getSimpleName());
		widgetObject.put("id", WidgetUtil.getWidgetId(child));
		widgetObject.put("width", params.width);
		widgetObject.put("height", params.height);
		widgetObject.put("visibility", child.getVisibility());
		widgetObject.put("alpha", child.getAlpha());
		widgetObject.put("rotation", child.getRotation());
		widgetObject.put("scaleX", child.getScaleX());
		widgetObject.put("scaleY", child.getScaleY());
		widgetObject.put("translationX", child.getTranslationX());
		widgetObject.put("translationY", child.getTranslationY());
		
		if (child.getBackground() instanceof ColorDrawable) {
			widgetObject.put("background_color", ((ColorDrawable) child.getBackground()).getColor());
		}
		
		if (params instanceof ViewGroup.MarginLayoutParams) {
			ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) params;
			widgetObject.put("margin_left", marginParams.leftMargin);
			widgetObject.put("margin_top", marginParams.topMargin);
			widgetObject.put("margin_right", marginParams.rightMargin);
			widgetObject.put("margin_bottom", marginParams.bottomMargin);
		}
		
		widgetObject.put("padding_left", child.getPaddingLeft());
		widgetObject.put("padding_top", child.getPaddingTop());
		widgetObject.put("padding_right", child.getPaddingRight());
		widgetObject.put("padding_bottom", child.getPaddingBottom());
		
		if (WidgetUtil.getTextViewOfWidget(child) != null) {
			TextView textView = WidgetUtil.getTextViewOfWidget(child);
			widgetObject.put("text", textView.getText().toString());
			widgetObject.put("text_size", textView.getTextSize());
			widgetObject.put("text_color", textView.getCurrentTextColor());
			widgetObject.put("gravity", textView.getGravity());
			if (textView.getTypeface() != null) {
				widgetObject.put("typeface", textView.getTypeface().toString());
			}
			if (child instanceof WidgetTextView) {
				try {
					widgetObject.put("lines", ((WidgetTextView) child).getLines());
				} catch (Exception e) {
					widgetObject.put("lines", 1); // Default value
				}
			}
		}
		
		if (child instanceof WidgetImageView) {
			String imagePath = WidgetUtil.getImagePath(child);
			if (imagePath != null) {
				widgetObject.put("image_path", imagePath);
			}
			widgetObject.put("scale_type", ((WidgetImageView) child).getScaleType().toString());
		}
		
		if (child instanceof WidgetWebView) {
			widgetObject.put("is_webview", true);
		}
		
		if (child instanceof WidgetListView) {
			widgetObject.put("is_listview", true);
		}
		
		if (child instanceof WidgetLinear) {
			widgetObject.put("is_linear", true);
		}
		
		return widgetObject;
	}
	
	private void restoreState(String state) throws JSONException {
		ll.removeAllViews();
		JSONObject jsonObject = new JSONObject(state);
		
		activityName = jsonObject.optString("activity_name", "ViewEditorFragmentActivity");
		layoutName = jsonObject.optString("layout_name", "main");
		pkgName = jsonObject.optString("package_name", "com.nexusteam.internal.os.layouteditor");
		useAndroidX = jsonObject.optBoolean("use_androidx", false);
		
		JSONArray widgetsArray = jsonObject.getJSONArray("widgets");
		for (int i = 0; i < widgetsArray.length(); i++) {
			JSONObject widgetData = widgetsArray.getJSONObject(i);
			LinearLayout container = new LinearLayout(context);
			container.setOrientation(LinearLayout.VERTICAL);
			container.setOnDragListener(activity);
			container.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
			
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
					widgetTextView.getTextView().setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) widgetData.getDouble("text_size"));
				}
				if (widgetData.has("text_color")) {
					widgetTextView.getTextView().setTextColor(widgetData.getInt("text_color"));
				}
				if (widgetData.has("gravity")) {
					widgetTextView.getTextView().setGravity(widgetData.getInt("gravity"));
				}
				if (widgetData.has("typeface")) {
					String typefaceStr = widgetData.getString("typeface");
					Typeface typeface = Typeface.create(typefaceStr, Typeface.NORMAL);
					widgetTextView.getTextView().setTypeface(typeface);
				}
				if (widgetData.has("lines")) {
					try {
						widgetTextView.setLines(widgetData.getInt("lines"));
					} catch (JSONException e) {
						// If it's not an integer, try to parse it or use default value
						try {
							String linesValue = widgetData.getString("lines");
							// Try to parse as integer, or use default value of 1
							int lines = 1;
							if (linesValue != null && !linesValue.isEmpty()) {
								try {
									lines = Integer.parseInt(linesValue);
								} catch (NumberFormatException nfe) {
									lines = 1; // Default value
								}
							}
							widgetTextView.setLines(lines);
						} catch (JSONException je) {
							widgetTextView.setLines(1); // Default fallback
						}
					}
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
					widgetButton.getTextView().setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) widgetData.getDouble("text_size"));
				}
				if (widgetData.has("text_color")) {
					widgetButton.getTextView().setTextColor(widgetData.getInt("text_color"));
				}
				if (widgetData.has("gravity")) {
					widgetButton.getTextView().setGravity(widgetData.getInt("gravity"));
				}
				if (widgetData.has("typeface")) {
					String typefaceStr = widgetData.getString("typeface");
					Typeface typeface = Typeface.create(typefaceStr, Typeface.NORMAL);
					widgetButton.getTextView().setTypeface(typeface);
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
				if (widgetData.has("scale_type")) {
					String scaleTypeStr = widgetData.getString("scale_type");
					ImageView.ScaleType scaleType = ImageView.ScaleType.valueOf(scaleTypeStr);
					widgetImageView.setScaleType(scaleType);
				}
				widget = widgetImageView;
			} else if (widgetName.equals(WidgetWebView.class.getSimpleName())) {
				WidgetWebView widgetWebView = new WidgetWebView(context);
				widgetWebView.setLayoutParams(lp);
				widgetWebView.setWidgetId(widgetId);
				widgetWebView.setOnClickListener(new ViewEditorFragmentActivity.WidgetClickListener());
				widgetWebView.setOnLongClickListener(activity);
				widget = widgetWebView;
			} else if (widgetName.equals(WidgetListView.class.getSimpleName())) {
				WidgetListView widgetListView = new WidgetListView(context);
				widgetListView.setLayoutParams(lp);
				widgetListView.setWidgetId(widgetId);
				widgetListView.setOnClickListener(new ViewEditorFragmentActivity.WidgetClickListener());
				widgetListView.setOnLongClickListener(activity);
				widgetListView.setOnItemClickListener((parent, view, position, id) -> ViewEditorFragmentActivity.selectWidget(view));
				widget = widgetListView;
			} else if (widgetName.equals(WidgetLinear.class.getSimpleName())) {
				WidgetLinear widgetLinear = new WidgetLinear(context);
				widgetLinear.setLayoutParams(lp);
				widgetLinear.setWidgetId(widgetId);
				widgetLinear.setOnClickListener(new ViewEditorFragmentActivity.WidgetClickListener());
				widgetLinear.setOnLongClickListener(activity);
				widget = widgetLinear;
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
					if (lp instanceof ViewGroup.MarginLayoutParams) {
						((ViewGroup.MarginLayoutParams) lp).setMargins(left, top, right, bottom);
					}
				}
				
				if (widgetData.has("padding_left") || widgetData.has("padding_top") ||
				widgetData.has("padding_right") || widgetData.has("padding_bottom")) {
					int left = widgetData.optInt("padding_left", 0);
					int top = widgetData.optInt("padding_top", 0);
					int right = widgetData.optInt("padding_right", 0);
					int bottom = widgetData.optInt("padding_bottom", 0);
					widget.setPadding(left, top, right, bottom);
				}
				
				container.addView(widget);
				(activity).addWidgetInLayout(container, ll.getChildCount());
			}
		}
	}
}
