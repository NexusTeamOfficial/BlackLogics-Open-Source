/*
* MIT License (Modified) – Nexus Edition
*
* Copyright (c) 2025 NexusTeam & SmartIndiaGaming
*
* This file defines the `ViewEditor` class, originally inspired by a layout editor activity.
* It has been ported, refactored, and fully restructured into a custom `LinearLayout`-based
* component for advanced view editing functionality inside Android apps.
*
* **Important Notice:**
* - This is NOT the original class by Shapun 963. The class did not originally exist in this form.
* - Only the package name (`com.shapun.layouteditor`) is used for compatibility.
* - All core logic, structure, and UI behavior are custom-built by NexusTeam & SmartIndiaGaming.
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, subject to the following conditions:
*
* - The above copyright notice and this permission notice shall be
*   included in all copies or substantial portions of the Software.
* - Proper attribution to NexusTeam & SmartIndiaGaming must be retained.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED.
*
* Concept, Engineering & Development by: NexusTeam & SmartIndiaGaming (2025)
*/

package com.shapun.layouteditor;

import android.Manifest;
import android.animation.*;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable; 
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.*;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.webkit.*;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter; 
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.LinearLayoutCompat;
import com.besome.blacklogics.R;
import com.besome.blacklogics.FileUtil;
import com.besome.blacklogics.SketchwareUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shapun.layouteditor.*;
import com.shapun.layouteditor.utils.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map; 
import java.util.Random;
import java.util.regex.Pattern;
import java.util.Stack;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class ViewEditor extends LinearLayout {
	
	public static interface OnWidgetAdd {
		void onWidgetAdded(View widget, ViewGroup parent);
	}
	
	private ArrayList<String> typeList = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> viewsList = new ArrayList<>();
	
	private LinearLayout lin_toolbar;
	private LinearLayout linear7;
	private ImageView img_views;
	private LinearLayout linear9;
	private ImageView img_import;
	private ImageView img_copy;
	private ImageView img_add_image;
	private LinearLayout linear8;
	public static LinearLayout editorLayout;
	private ImageView img_add_view;
	private RecyclerView listview_widgets;
	
	public static TextView tv_view_name;
	public static LinearLayout phone_action_bar;
	
	private ViewGroup currentViewGroup;
	private LinearLayoutCompat attributesContainer;
	private ObjectAnimator anim = new ObjectAnimator();
	
	public Attribute attr;
	public AttributeSet attrSet;
	
	public HashMap<View, AttributeSet> oldAttributesValueMap = new HashMap<>();
	
	public String id = "";
	public static String SAVE_PATH = "";
	public int index;
	private Vibrator vib;
	private View placeHolder;
	private DragListener dragListener;
	private ImageView deleteImg;
	private HashMap<String, ArrayList<HashMap<String, Object>>> attributesMap = new HashMap<>();
	private HashMap<String, ArrayList<HashMap<String, Object>>> parentAttributesMap = new HashMap<>();
	private HashMap<View, AttributeSet> attributesValueMap = new HashMap<>();
	public static IdManager idManager = new IdManager();
	public static IdManager oldIdManager = new IdManager();
	private OnWidgetAdd onWidgetAddListener;
	// Undo/Redo stacks (action-based)
	private Stack<EditorAction> undoStack = new Stack<>();
	private Stack<EditorAction> redoStack = new Stack<>();
	private boolean isUndoRedoInProgress = false;
	
	// Action types
	private static final int ACTION_ADD_VIEW = 1;
	private static final int ACTION_REMOVE_VIEW = 2;
	private static final int ACTION_UPDATE_ATTR = 3;
	
	// Action data structure
	private static class EditorAction {
		int actionType;
		View view;
		ViewGroup parent;
		int index; // Position in parent
		Attribute oldAttr; // For attribute changes
		Attribute newAttr;
		String viewId; // To restore views if needed
		
		EditorAction(int actionType, View view, ViewGroup parent, int index, Attribute oldAttr, Attribute newAttr) {
			this.actionType = actionType;
			this.view = view;
			this.parent = parent;
			this.index = index;
			this.oldAttr = oldAttr;
			this.newAttr = newAttr;
			this.viewId = (view != null) ? idManager.getId(view) : null;
		}
	}
	
	public ViewEditor(Context context) {
		super(context);
		initialize(context);
	}
	
	public ViewEditor(Context context, android.util.AttributeSet attrs) {
		super(context, attrs);
		initialize(context);
	}
	
	public ViewEditor(Context context, android.util.AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initialize(context);
	}
	
	public void setOnWidgetAddListener(OnWidgetAdd listener) {
		this.onWidgetAddListener = listener;
	}
	
	private void initialize(Context context) {
		setOrientation(VERTICAL);
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.view_editor, this, true);
		
		lin_toolbar = view.findViewById(R.id.lin_toolbar);
		linear7 = view.findViewById(R.id.linear7);
		img_views = view.findViewById(R.id.img_views);
		linear9 = view.findViewById(R.id.linear9);
		img_import = view.findViewById(R.id.img_import);
		img_copy = view.findViewById(R.id.img_copy);
		img_add_image = view.findViewById(R.id.img_add_image);
		linear8 = view.findViewById(R.id.linear8);
		editorLayout = view.findViewById(R.id.editorLayout);
		img_add_view = view.findViewById(R.id.img_add_view);
		listview_widgets = view.findViewById(R.id.listview_widgets);
		tv_view_name = view.findViewById(R.id.tv_view_name);
		phone_action_bar = view.findViewById(R.id.phone_action_bar);
		vib = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		
		setupListeners();
		initializeLogic(context);
	}
	
	public void setPath(String SAVE_PATH) {
		ViewEditor.SAVE_PATH = SAVE_PATH;
	}
	
	public void setCurrentViewGroup(ViewGroup viewGroup) {
		this.currentViewGroup = viewGroup;
	}
	
	public void a(LinearLayoutCompat viewGroup) {
		this.attributesContainer = viewGroup;
	}
	
	private void setupListeners() {
		img_views.setOnClickListener(v -> {
			AlertDialog.Builder builder;
			builder = new AlertDialog.Builder(getContext());
			builder.setTitle("Select view");
			ArrayList<String> idsList = idManager.getIds();
			String[] ids = new String[idsList.size()];
			idsList.toArray(ids);
			builder.setItems(ids, (dialog, which) -> showAttributesDialog(idManager.getView(ids[which])));
			builder.create().show();
		});
		
		img_import.setOnClickListener(v -> {
			Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
			chooseFile.setType("text/xml");
			chooseFile = Intent.createChooser(chooseFile, "Choose the xml file");
			((Activity) getContext()).startActivityForResult(chooseFile, 1002);
		});
		
		img_copy.setOnClickListener(v -> {
			ViewGroup rootView = (ViewGroup)editorLayout.getChildAt(0);
			if (rootView == null) {
				SketchwareUtil.showMessage(((Activity) getContext()), "Add Some Views");
			} else {
				String code = generateCode(rootView);
				
				code = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"+ code.substring(0,code.indexOf("\n"))+"\n"+"xmlns:android=\"http://schemas.android.com/apk/res/android\""+code.substring(code.indexOf("\n"),code.length());
				/*	((ClipboardManager) getSystemService(((Activity) getContext()).CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", code));
				SketchwareUtil.showMessage(((Activity) getContext()), "Copied");*/
			}
		});
		
		img_add_image.setOnClickListener(v -> {
			Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
			chooseFile.setType("image/*");
			chooseFile = Intent.createChooser(chooseFile, "Choose a file");
			((Activity) getContext()).startActivityForResult(chooseFile, 1001);
		});
		
		img_add_view.setOnClickListener(v -> {
			AlertDialog.Builder inputDialog = new AlertDialog.Builder(getContext());
			inputDialog.setTitle("Enter custom widget class path");
			final EditText edittext = new EditText(getContext());
			edittext.setHint("android.widget.SearchView");
			inputDialog.setPositiveButton("Save", (dialog, which) -> {
				try {
					String class_path = edittext.getText().toString();
					Class<?> cls = Class.forName(class_path);
					HashMap<String, Object> map = new HashMap<>();
					map.put("name", cls.getSimpleName());
					map.put("class_path", class_path);
					viewsList.add(map);
					//	((BaseAdapter) listview_widgets.getAdapter()).notifyDataSetChanged();
				} catch (Exception e) {
					showMessage(e.toString());
				}
			});
			inputDialog.setView(edittext);
			inputDialog.show();
		});
	}
	
	private void initializeLogic(Context context) {
		placeHolder = new View(context);
		placeHolder.setLayoutParams(new ViewGroup.LayoutParams(
		(int) SketchwareUtil.getDip(context, 70),
		(int) SketchwareUtil.getDip(context, 40)));
		placeHolder.setMinimumHeight((int) SketchwareUtil.getDip(context, 40));
		placeHolder.setMinimumWidth((int) SketchwareUtil.getDip(context, 70));
		placeHolder.setBackgroundColor(0xFF757575);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			int flags = getSystemUiVisibility();
			flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
			setSystemUiVisibility(flags);
			((Activity) context).getWindow().setStatusBarColor(Color.WHITE);
			((Activity) context).getWindow().setNavigationBarColor(Color.WHITE);
		}
		
		dragListener = new DragListener();
		editorLayout.setOnDragListener(dragListener);
		DragAndDropUtils.startDragAndDrop(img_import, null, new View.DragShadowBuilder(null), null, 0);
		deleteImg = new ImageView(context);
		deleteImg.setImageResource(R.drawable.ic_delete_white);
		deleteImg.setColorFilter(0xFF757575, PorterDuff.Mode.SRC_ATOP);
		deleteImg.setVisibility(View.GONE);
		
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
		(int) SketchwareUtil.getDip(context, 60),
		(int) SketchwareUtil.getDip(context, 60));
		params.gravity = Gravity.LEFT | Gravity.BOTTOM;
		deleteImg.setLayoutParams(params);
		((ViewGroup) ((Activity) context).getWindow().findViewById(Window.ID_ANDROID_CONTENT)).addView(deleteImg);
		
		UiUtils.addRipple(img_add_view);
		UiUtils.addRipple(img_views);
		UiUtils.addRipple(img_copy);
		UiUtils.addRipple(img_add_image);
		UiUtils.addRipple(img_import);
		
		viewsList = new Gson().fromJson(Utils.readFromAsset(context, "views.json"),
		new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType());
		attributesMap = new Gson().fromJson(Utils.readFromAsset(context, "attributes.json"),
		new TypeToken<HashMap<String, ArrayList<HashMap<String, Object>>>>() {}.getType());
		parentAttributesMap = new Gson().fromJson(Utils.readFromAsset(context, "parent_dependent_attributes.json"),
		new TypeToken<HashMap<String, ArrayList<HashMap<String, Object>>>>() {}.getType());
		listview_widgets.setLayoutManager(new LinearLayoutManager(context));
		listview_widgets.setAdapter(new Listview_widgetsAdapter(viewsList));
		lin_toolbar.setElevation(5);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK && data != null) {
			final Uri uri = data.getData();
			switch (requestCode) {
				case 1001:
				String filePath = uri.getPath();
				showMessage(filePath);
				AlertDialog.Builder inputDialog = new AlertDialog.Builder(getContext());
				inputDialog.setTitle("Enter custom widget class path");
				final EditText edittext = new EditText(getContext());
				edittext.setHint("android.widget.SearchView");
				inputDialog.setNegativeButton("Cancel", null);
				inputDialog.setPositiveButton("Save", (dialog, which) -> {
					String toPath = FileUtil.getPackageDataDir(getContext()) + "/images/" +
					edittext.getText().toString() + ".png";
					FileUtil.makeDir(FileUtil.getPackageDataDir(getContext()) + "/images/");
					try {
						InputStream is = getContext().getContentResolver().openInputStream(uri);
						OutputStream os = new FileOutputStream(toPath);
						byte[] buffer = new byte[1024];
						int length;
						while ((length = is.read(buffer)) > 0) {
							os.write(buffer, 0, length);
						}
						is.close();
						os.close();
					} catch (Exception e) {
						showMessage(e.toString());
					}
				});
				inputDialog.setView(edittext);
				inputDialog.show();
				break;
				case 1002:
				StringBuilder sb = new StringBuilder();
				ArrayList<View> viewsList = new ArrayList<>();
				try {
					XmlPullParserFactory pullParserFactory = XmlPullParserFactory.newInstance();
					XmlPullParser parser = pullParserFactory.newPullParser();
					parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
					parser.setInput(getContext().getContentResolver().openInputStream(uri), null);
					int eventType = parser.getEventType();
					while (eventType != XmlPullParser.END_DOCUMENT) {
						String name = null;
						switch (eventType) {
							case XmlPullParser.START_DOCUMENT:
							editorLayout.removeAllViews();
							viewsList.add(editorLayout);
							idManager = new IdManager();
							break;
							case XmlPullParser.START_TAG:
							View view = ReflectionUtils.createView(getContext(), "android.widget." + parser.getName());
							if (view != null) {
								viewsList.add(view);
								view.setOnClickListener(v -> showAttributesDialog(view));
								_rearrangeListener(view);
								if (view instanceof ViewGroup) {
									view.setOnDragListener(dragListener);
									view.setMinimumHeight((int) SketchwareUtil.getDip(getContext(), 30));
								}
								AttributeSet attributeSet = new AttributeSet();
								attributesValueMap.put(view, attributeSet);
								for (int i = 0; i < parser.getAttributeCount(); i++) {
									String attributeName = parser.getAttributeName(i);
									String attributeValue = parser.getAttributeValue(i);
									if (attributeName.equals("android:id")) {
										idManager.addNewId(view, AttributeUtils.getName(attributeValue));
									}
									attributeSet.add(new Attribute(attributeName, attributeValue));
								}
							}
							break;
							case XmlPullParser.END_TAG:
							int index = parser.getDepth();
							((ViewGroup) viewsList.get(index - 1)).addView(viewsList.get(index));
							viewsList.remove(index);
							break;
						}
						eventType = parser.next();
					}
				} catch (Exception e) {
					showMessage(e.toString());
				}
				for (View view : attributesValueMap.keySet()) {
					AttributeSet attributeSet = attributesValueMap.get(view);
					for (Attribute attr : attributeSet.getAttributes()) {
						applyAttribute(view, attr);
					}
				}
				((ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE))
				.setPrimaryClip(ClipData.newPlainText("clipboard", sb.toString()));
				break;
			}
		}
	}
	
	public class AttributeAdaper extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public AttributeAdaper(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		@Override
		public int getCount() {
			return _data.size();
		}
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		@Override
		public View getView(final int _position, View _view, ViewGroup _viewGroup) {
			LayoutInflater _inflater = LayoutInflater.from(getContext());
			View _v = _view;
			if (_v == null) {
				_v = _inflater.inflate(R.layout.attribute_view, null);
			}
			TextView tv_name = _v.findViewById(R.id.tv_name);
			tv_name.setText(_data.get(_position).get("name").toString());
			return _v;
		}
	}
	
	public Object createObject(Class<?> cls) {
		Object obj = null;
		try {
			obj = cls.getConstructor().newInstance();
			return obj;
		} catch (Exception e) {}
		java.lang.reflect.Constructor[] constructorsArray = cls.getConstructors();
		for (java.lang.reflect.Constructor constructor : constructorsArray) {
			Class[] constructorParams = constructor.getParameterTypes();
			Object[] argList = new Object[constructorParams.length];
			for (int i = 0; i < constructorParams.length; i++) {
				if (Context.class.isAssignableFrom(constructorParams[i])) {
					argList[i] = getContext();
					continue;
				}
				argList[i] = PrimitiveUtils.getDefaultValue(constructorParams[i]);
			}
			try {
				obj = constructor.newInstance(argList);
				return obj;
			} catch (Exception e) {}
		}
		return null;
	}
	
	public void copy(String s) {
		((ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE))
		.setPrimaryClip(ClipData.newPlainText("clipboard", s));
	}
	
	public void log(String s) {
		FileUtil.writeFile(FileUtil.getPackageDataDir(((Activity) getContext())) + "/log.txt",
		FileUtil.readFile(FileUtil.getPackageDataDir(getContext()) + "/log.txt") + "\n" + s);
	}
	
	public void _rearrangeListener(final View _view) {
		final View view = _view;
		final GestureDetector gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() { 
			public void onLongPress(MotionEvent event) {	
				
				DragAndDropUtils.startDragAndDrop(view,null, new View.DragShadowBuilder(view), view , 0);
				
				
			} }); 
		
		_view.setOnTouchListener(new OnTouchListener() {
			boolean bClick = true;
			float startX = 0;
			float startY= 0;
			float endX=0;
			float endY = 0;
			float diffX= 0;
			float diffY = 0;
			@Override
			public boolean onTouch(final View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN: 
					startX = event.getX(); 
					startY = event.getY(); 
					bClick = true;
					/*
					new Handler().postDelayed(new Runnable(){ 
					public void run() { 
					if (bClick == true) { 
					MainActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
					//long click event 
					try{				
					DragAndDropUtils.startDragAndDrop(v,null, new View.DragShadowBuilder(v), v , 1);	
					}catch(
					Exception e){showMessage(e.toString());
					}
					}
					});				
					} 
					} }, 300);
					*/
					break;
					case MotionEvent.ACTION_UP:
					endX = event.getX(); 
					endY = event.getY(); 
					diffX = Math.abs(startX - endX);
					diffY = Math.abs(startY - endY); 
					if (diffX <= 5 && diffY <= 5 && bClick == true) {
						//click event 
						showAttributesDialog(v);
					} 
					bClick = false;
					break;
					default: break; 
				}
				
				gestureDetector.onTouchEvent(event);
				return true;
				
				
			}
		});
		
	}
	
	public void showCustomAttributesDialog(final View view) {
		final AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
		View inflated = LayoutInflater.from(getContext()).inflate(R.layout.custom_methods, null);
		dialog.setView(inflated);
		final EditText et_function_name = inflated.findViewById(R.id.et_function_name);
		final EditText et_function_value = inflated.findViewById(R.id.et_function_value);
		final Spinner spinner = inflated.findViewById(R.id.spinner);
		final Button apply = inflated.findViewById(R.id.apply);
		final CheckBox cb_field = inflated.findViewById(R.id.cb_field);
		final CheckBox cb_layout_params = inflated.findViewById(R.id.cb_layout_params);
		ArrayList<String> typesList = new ArrayList<>();
		typesList.add("boolean");
		typesList.add("int");
		typesList.add("float");
		typesList.add("CharSequence");
		typesList.add("String");
		typesList.add("color");
		et_function_name.setFocusableInTouchMode(true);
		et_function_value.setFocusableInTouchMode(true);
		spinner.setAdapter(new ArrayAdapter<String>(getContext(),
		android.R.layout.simple_spinner_dropdown_item, typesList));
		apply.setOnClickListener(v -> {
			String memberName = et_function_name.getText().toString();
			String value = et_function_value.getText().toString();
			Class<?> argument_class = null;
			Object argument = null;
			try {
				switch (spinner.getSelectedItemPosition()) {
					case 0:
					argument_class = boolean.class;
					argument = value.equals("true");
					break;
					case 1:
					argument = Integer.parseInt(value);
					argument_class = int.class;
					break;
					case 2:
					argument = Float.parseFloat(value);
					argument_class = float.class;
					break;
					case 3:
					argument = value;
					argument_class = CharSequence.class;
					break;
					case 4:
					argument = value;
					argument_class = String.class;
					break;
					case 5:
					argument = Color.parseColor(value);
					argument_class = int.class;
					break;
				}
				if (cb_field.isChecked()) {
					if (cb_layout_params.isChecked()) {
						ReflectionUtils.setField(view.getLayoutParams(), memberName, argument);
					} else {
						ReflectionUtils.setField(view, memberName, argument);
					}
					view.requestLayout();
				} else {
					if (cb_layout_params.isChecked()) {
						ReflectionUtils.invokeMethod(view.getLayoutParams(), memberName, argument);
					} else {
						ReflectionUtils.invokeMethod(view, memberName, argument);
					}
				}
			} catch (Exception e) {
				showMessage(e.toString());
			}
		});
		dialog.show();
	}
	
	private void showAttributesDialog(final View view) {
		// Initialize attributes list
		final ArrayList<HashMap<String, Object>> attributesList = new ArrayList<>();
		Class<?> cls = view.getClass();
		Class<?> viewParentClass = View.class.getSuperclass();
		
		// Collect attributes for view class hierarchy
		while (cls != viewParentClass) {
			ArrayList<HashMap<String, Object>> tempList = attributesMap.get(cls.getName());
			if (tempList != null) {
				attributesList.addAll(0, tempList);
			}
			cls = cls.getSuperclass();
		}
		
		// Collect parent-dependent attributes (e.g., layout params) if parent is a ViewGroup
		if (view.getParent() instanceof ViewGroup) {
			cls = view.getParent().getClass();
			while (cls != viewParentClass) {
				ArrayList<HashMap<String, Object>> tempList = parentAttributesMap.get(cls.getName());
				if (tempList != null) {
					attributesList.addAll(tempList);
				}
				cls = cls.getSuperclass();
			}
		}
		
		// Create or clear attributes container
		if (attributesContainer == null) {
			attributesContainer = new LinearLayoutCompat(getContext());
			attributesContainer.setOrientation(LinearLayout.VERTICAL);
			attributesContainer.setLayoutParams(new LinearLayout.LayoutParams(
			ViewGroup.LayoutParams.MATCH_PARENT,
			ViewGroup.LayoutParams.WRAP_CONTENT));
			attributesContainer.setBackgroundColor(0xFFFFFFFF);
			// Add to the root view of the activity
			((ViewGroup) ((Activity) getContext()).getWindow().findViewById(android.R.id.content))
			.addView(attributesContainer);
		} else {
			attributesContainer.removeAllViews();
		}
		
		showProperties();
		
		// Inflate attributes UI into attributesContainer
		View inflated = LayoutInflater.from(getContext()).inflate(R.layout.attributes_bottom_sheet, attributesContainer, false);
		attributesContainer.addView(inflated);
		
		// Initialize UI components
		final TextView tv_view_id = inflated.findViewById(R.id.tv_view_id);
		final RecyclerView rv_attributes = inflated.findViewById(R.id.rv_attributes);
		final ImageView img_edit_id = inflated.findViewById(R.id.img_edit_id);
		final ImageView img_common_attributes = inflated.findViewById(R.id.img_common_attributes);
		final ImageView img_custom = inflated.findViewById(R.id.img_custom);
		
		// Setup RecyclerView for attributes
		LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
		rv_attributes.setLayoutManager(layoutManager);
		AttributeRecyclerAdapter adapter = new AttributeRecyclerAdapter(getContext(), attributesList);
		rv_attributes.setAdapter(adapter);
		
		// Set corner radius and background
		GradientDrawable background = new GradientDrawable();
		int d = (int) getContext().getResources().getDisplayMetrics().density;
		background.setColor(0xFFFFFFFF);
		background.setCornerRadius(d * 16);
		inflated.setBackground(background);
		inflated.setClipToOutline(true);
		
		// Set view ID
		tv_view_id.setText(idManager.getId(view));
		
		// Edit ID listener
		img_edit_id.setOnClickListener(v -> {
			AlertDialog.Builder idDialog = new AlertDialog.Builder(getContext());
			idDialog.setTitle("Edit ID");
			final EditText editText = new EditText(getContext());
			editText.setText(idManager.getId(view));
			idDialog.setView(editText);
			idDialog.setPositiveButton("Save", (dialog, which) -> {
				String newId = editText.getText().toString();
				idManager.updateId(view, newId);
				tv_view_id.setText(newId);
			});
			idDialog.setNegativeButton("Cancel", null);
			idDialog.show();
		});
		
		// Common attributes listener
		img_common_attributes.setOnClickListener(v -> showCommonAttributesDialog(view));
		
		// Custom attributes listener
		img_custom.setOnClickListener(v -> showCustomAttributesDialog(view));
		
		// Attribute item click listener
		adapter.setOnItemClickListener(position -> {
			final HashMap<String, Object> attribute = attributesList.get(position);
			final String attrName = attribute.get("attribute_name").toString();
			final String attrType = attribute.get("argument_type").toString();
			final AlertDialog.Builder inputDialog = new AlertDialog.Builder(getContext());
			inputDialog.setTitle(attribute.get("name").toString());
			inputDialog.setNeutralButton("Cancel", null);
			attrSet = attributesValueMap.get(view);
			String currentValue = attrSet != null && attrSet.getAttribute(attrName) != null ?
			attrSet.getAttribute(attrName).getValue() : "";
			
			switch (attrType) {
				case "boolean":
				final RadioGroup rg = new RadioGroup(getContext());
				ViewGroup.LayoutParams p = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
				final RadioButton rbTrue = new RadioButton(getContext());
				rbTrue.setText("True");
				rbTrue.setLayoutParams(p);
				rbTrue.setId(View.generateViewId());
				final RadioButton rbFalse = new RadioButton(getContext());
				rbFalse.setText("False");
				rbFalse.setId(View.generateViewId());
				rbFalse.setLayoutParams(p);
				rg.addView(rbTrue);
				rg.addView(rbFalse);
				if (currentValue.equals("true")) {
					rbTrue.setChecked(true);
				} else if (currentValue.equals("false")) {
					rbFalse.setChecked(true);
				}
				inputDialog.setPositiveButton("Save", (dialog, which) -> {
					String value = rbTrue.isChecked() ? "true" : "false";
					attr = new Attribute(attrName, value);
					if (attrSet == null) {
						attrSet = new AttributeSet();
						attributesValueMap.put(view, attrSet);
					}
					attrSet.add(attr);
					applyAttribute(view, attr, attribute);
				});
				inputDialog.setView(rg);
				break;
				case "int":
				final EditText editTextInt = new EditText(getContext());
				editTextInt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
				if (!currentValue.isEmpty()) {
					editTextInt.setText(currentValue);
				}
				inputDialog.setPositiveButton("Save", (dialog, which) -> {
					String value = editTextInt.getText().toString();
					attr = new Attribute(attrName, value);
					if (attrSet == null) {
						attrSet = new AttributeSet();
						attributesValueMap.put(view, attrSet);
					}
					attrSet.add(attr);
					applyAttribute(view, attr, attribute);
				});
				inputDialog.setView(editTextInt);
				break;
				case "float":
				final EditText editTextFloat = new EditText(getContext());
				editTextFloat.setInputType(InputType.TYPE_CLASS_NUMBER |
				InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
				if (!currentValue.isEmpty()) {
					editTextFloat.setText(currentValue);
				}
				inputDialog.setPositiveButton("Save", (dialog, which) -> {
					String value = editTextFloat.getText().toString();
					attr = new Attribute(attrName, value);
					if (attrSet == null) {
						attrSet = new AttributeSet();
						attributesValueMap.put(view, attrSet);
					}
					attrSet.add(attr);
					applyAttribute(view, attr, attribute);
				});
				inputDialog.setView(editTextFloat);
				break;
				case "String":
				final EditText editTextString = new EditText(getContext());
				if (!currentValue.isEmpty()) {
					editTextString.setText(currentValue);
				}
				inputDialog.setPositiveButton("Save", (dialog, which) -> {
					String value = editTextString.getText().toString();
					attr = new Attribute(attrName, value);
					if (attrSet == null) {
						attrSet = new AttributeSet();
						attributesValueMap.put(view, attrSet);
					}
					attrSet.add(attr);
					applyAttribute(view, attr, attribute);
				});
				inputDialog.setView(editTextString);
				break;
				case "Size":
				final RadioGroup rgSize = new RadioGroup(getContext());
				final RadioButton rb_match_parent = new RadioButton(getContext());
				rb_match_parent.setText("match_parent");
				final RadioButton rb_wrap_content = new RadioButton(getContext());
				rb_wrap_content.setText("wrap_content");
				final RadioButton rb_custom = new RadioButton(getContext());
				rb_custom.setText("Value in dp");
				final EditText editTextSize = new EditText(getContext());
				final TextView tv_dp = new TextView(getContext());
				tv_dp.setText("dp");
				tv_dp.setTextSize(16);
				final LinearLayout lin_custom = new LinearLayout(getContext());
				lin_custom.setOrientation(LinearLayout.HORIZONTAL);
				lin_custom.setVisibility(View.GONE);
				lin_custom.addView(editTextSize);
				lin_custom.addView(tv_dp);
				rgSize.addView(rb_match_parent);
				rgSize.addView(rb_wrap_content);
				rgSize.addView(rb_custom);
				rgSize.addView(lin_custom);
				rb_match_parent.setId(1);
				rb_wrap_content.setId(2);
				rb_custom.setId(3);
				editTextSize.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
				lin_custom.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
				((LinearLayout.LayoutParams) editTextSize.getLayoutParams()).weight = 1.0f;
				editTextSize.requestLayout();
				com.shapun.layouteditor.utils.AnimationUtils.animate(rgSize);
				rb_custom.setOnCheckedChangeListener((button, isChecked) -> {
					lin_custom.setVisibility(isChecked ? View.VISIBLE : View.GONE);
				});
				if (!currentValue.isEmpty()) {
					if (currentValue.equals("match_parent")) {
						rb_match_parent.setChecked(true);
					} else if (currentValue.equals("wrap_content")) {
						rb_wrap_content.setChecked(true);
					} else {
						rb_custom.setChecked(true);
						editTextSize.setText(currentValue.replaceAll("dp", ""));
					}
				}
				inputDialog.setPositiveButton("Save", (dialog, which) -> {
					String value = null;
					switch (rgSize.getCheckedRadioButtonId()) {
						case 1:
						value = "match_parent";
						break;
						case 2:
						value = "wrap_content";
						break;
						case 3:
						value = editTextSize.getText().toString() + "dp";
						break;
					}
					attr = new Attribute(attrName, value);
					if (attrSet == null) {
						attrSet = new AttributeSet();
						attributesValueMap.put(view, attrSet);
					}
					attrSet.add(attr);
					applyAttribute(view, attr, attribute);
				});
				inputDialog.setView(rgSize);
				break;
				case "Color":
				final EditText editTextColor = new EditText(getContext());
				if (!currentValue.isEmpty()) {
					editTextColor.setText(currentValue);
				}
				inputDialog.setPositiveButton("Save", (dialog, which) -> {
					String value = editTextColor.getText().toString();
					attr = new Attribute(attrName, value);
					if (attrSet == null) {
						attrSet = new AttributeSet();
						attributesValueMap.put(view, attrSet);
					}
					attrSet.add(attr);
					applyAttribute(view, attr, attribute);
				});
				inputDialog.setView(editTextColor);
				break;
				case "Drawable":
				ArrayList<String> listData = new ArrayList<>();
				ArrayList<String> imagePaths = new ArrayList<>();
				FileUtil.listDir(FileUtil.getPackageDataDir(getContext()) + "/images/", imagePaths);
				for (String name : imagePaths) {
					if (name.endsWith(".png")) {
						name = Uri.parse(name).getLastPathSegment();
						name = name.substring(0, name.lastIndexOf("."));
						listData.add(name);
					}
				}
				String selectedData = currentValue.isEmpty() ? "" : AttributeUtils.getName(currentValue);
				final ScrollView scrollViewDrawable = new ScrollView(getContext());
				final RadioGroup rgDrawable = new RadioGroup(getContext());
				rgDrawable.setOrientation(RadioGroup.VERTICAL);
				scrollViewDrawable.addView(rgDrawable);
				for (String str : listData) {
					RadioButton rb = new RadioButton(getContext());
					rb.setId(View.generateViewId());
					if (str.equals(selectedData)) rb.setChecked(true);
					rb.setText(str);
					rgDrawable.addView(rb);
				}
				inputDialog.setPositiveButton("Save", (dialog, which) -> {
					RadioButton rb = rgDrawable.findViewById(rgDrawable.getCheckedRadioButtonId());
					if (rb != null) {
						String value = "@drawable/" + rb.getText().toString();
						attr = new Attribute(attrName, value);
						if (attrSet == null) {
							attrSet = new AttributeSet();
							attributesValueMap.put(view, attrSet);
						}
						attrSet.add(attr);
						applyAttribute(view, attr, attribute);
					}
				});
				inputDialog.setView(scrollViewDrawable);
				break;
				case "View":
				ArrayList<String> listDataView = new ArrayList<>();
				if (view.getParent() instanceof ViewGroup) {
					ViewGroup parent = (ViewGroup) view.getParent();
					for (int i = 0; i < parent.getChildCount(); i++) {
						listDataView.add(idManager.getId(parent.getChildAt(i)));
					}
				}
				String selectedDataView = currentValue.isEmpty() ? "" : AttributeUtils.getName(currentValue);
				final ScrollView scrollViewView = new ScrollView(getContext());
				final RadioGroup rgView = new RadioGroup(getContext());
				rgView.setOrientation(RadioGroup.VERTICAL);
				scrollViewView.addView(rgView);
				for (String str : listDataView) {
					RadioButton rb = new RadioButton(getContext());
					rb.setId(View.generateViewId());
					if (str.equals(selectedDataView)) rb.setChecked(true);
					rb.setText(str);
					rgView.addView(rb);
				}
				inputDialog.setPositiveButton("Save", (dialog, which) -> {
					RadioButton rb = rgView.findViewById(rgView.getCheckedRadioButtonId());
					if (rb != null) {
						String value = "@+id/" + rb.getText().toString();
						attr = new Attribute(attrName, value);
						if (attrSet == null) {
							attrSet = new AttributeSet();
							attributesValueMap.put(view, attrSet);
						}
						attrSet.add(attr);
						applyAttribute(view, attr, attribute);
					}
				});
				inputDialog.setView(scrollViewView);
				break;
				case "enum":
				final ScrollView scrollViewEnum = new ScrollView(getContext());
				final RadioGroup rgEnum = new RadioGroup(getContext());
				rgEnum.setOrientation(RadioGroup.VERTICAL);
				scrollViewEnum.addView(rgEnum);
				ArrayList<String> listDataEnum = (ArrayList<String>) attribute.get("xml_arguments");
				String selectedDataEnum = currentValue.isEmpty() ? "" : AttributeUtils.getName(currentValue);
				for (String str : listDataEnum) {
					RadioButton rb = new RadioButton(getContext());
					rb.setId(View.generateViewId());
					if (str.equals(selectedDataEnum)) rb.setChecked(true);
					rb.setText(str);
					rgEnum.addView(rb);
				}
				inputDialog.setPositiveButton("Save", (dialog, which) -> {
					RadioButton rb = rgEnum.findViewById(rgEnum.getCheckedRadioButtonId());
					if (rb != null) {
						String value = rb.getText().toString();
						attr = new Attribute(attrName, value);
						if (attrSet == null) {
							attrSet = new AttributeSet();
							attributesValueMap.put(view, attrSet);
						}
						attrSet.add(attr);
						applyAttribute(view, attr, attribute);
					}
				});
				inputDialog.setView(scrollViewEnum);
				break;
				case "flag":
				final ScrollView scrollViewFlag = new ScrollView(getContext());
				final LinearLayout lin = new LinearLayout(getContext());
				lin.setOrientation(LinearLayout.VERTICAL);
				scrollViewFlag.addView(lin);
				String selectedDataFlag = currentValue.isEmpty() ? "" : AttributeUtils.getName(currentValue);
				ArrayList<String> selectedList = new ArrayList<>(Arrays.asList(selectedDataFlag.split("\\|")));
				ArrayList<String> listDataFlag = (ArrayList<String>) attribute.get("xml_arguments");
				for (String str : listDataFlag) {
					CheckBox cb = new CheckBox(getContext());
					if (selectedList.contains(str)) cb.setChecked(true);
					cb.setText(str);
					lin.addView(cb);
				}
				inputDialog.setPositiveButton("Save", (dialog, which) -> {
					String value = "";
					try {
						for (int i = 0; i < lin.getChildCount(); i++) {
							CheckBox cb = (CheckBox) lin.getChildAt(i);
							if (cb.isChecked()) {
								value = value.isEmpty() ? cb.getText().toString() : value + "|" + cb.getText().toString();
							}
						}
						attr = new Attribute(attrName, value);
						if (attrSet == null) {
							attrSet = new AttributeSet();
							attributesValueMap.put(view, attrSet);
						}
						attrSet.add(attr);
						applyAttribute(view, attr, attribute);
					} catch (Exception e) {
						SketchwareUtil.showMessage(getContext(), e.toString());
					}
				});
				inputDialog.setView(scrollViewFlag);
				break;
				default:
				//   SketchwareUtil.showMessage(getContext(), "No such type found");
				break;
			}
			inputDialog.show();
		});
		
		// Set listeners for the view
		_rearrangeListener(view);
		if (view instanceof ViewGroup) {
			view.setOnDragListener(dragListener);
			view.setMinimumHeight((int) SketchwareUtil.getDip(getContext(), 30));
		}
	}
	
	String generateCode(View view) {
		StringBuilder sb = new StringBuilder();
		sb.append("<").append(view.getClass().getSimpleName());
		
		AttributeSet attributeSet = attributesValueMap.get(view);
		if (attributeSet != null) {
			StringBuilder attrBuilder = new StringBuilder();
			for (Attribute attr : attributeSet.getAttributes()) {
				// Skip xmlns:android to avoid duplicates
				if (!attr.getName().equals("xmlns:android")) {
					attrBuilder.append("    ").append(attr.getName()).append("=\"")
					.append(attr.getValue()).append("\"\n");
				}
			}
			if (attrBuilder.length() > 0) {
				sb.append("\n").append(attrBuilder.toString().trim());
			}
		}
		
		if (view instanceof ViewGroup) {
			sb.append(">\n");
			for (int i = 0; i < ((ViewGroup)view).getChildCount(); i++) {
				View child = ((ViewGroup)view).getChildAt(i);
				String childXml = generateCode(child);
				sb.append(childXml.replaceAll("(?m)^", "    ")).append("\n");
			}
			sb.append("</").append(view.getClass().getSimpleName()).append(">");
		} else {
			sb.append("/>");
		}
		return sb.toString();
	}
	
	public String generateCode(View view, boolean isRoot) {
		StringBuilder sb = new StringBuilder();
		
		// Start opening tag
		sb.append("<").append(view.getClass().getSimpleName());
		
		// Attributes
		AttributeSet attributeSet = attributesValueMap.get(view);
		if (attributeSet != null) {
			// Ensure a space before attributes
			sb.append(" ");
			// Format attributes with proper spacing and newlines
			sb.append(attributeSet.toString().replaceAll("(?m)^", "    ").trim());
		}
		
		// Add namespace for root view
		if (isRoot) {
			// Insert namespace as the first attribute
			if (attributeSet == null || !attributeSet.toString().contains("xmlns:android")) {
				if (attributeSet != null && !attributeSet.toString().isEmpty()) {
					sb.append("\n    ");
				}
				sb.append("xmlns:android=\"http://schemas.android.com/apk/res/android\"");
			}
		}
		
		// Handle ViewGroup with children
		if (view instanceof ViewGroup) {
			sb.append(">\n");
			ViewGroup vg = (ViewGroup) view;
			for (int i = 0; i < vg.getChildCount(); i++) {
				View child = vg.getChildAt(i);
				// Indent child elements
				String childXml = generateCode(child, false);
				sb.append(childXml.replaceAll("(?m)^", "    ")).append("\n");
			}
			sb.append("</").append(view.getClass().getSimpleName()).append(">");
		} else {
			// Self-closing tag for leaf views
			sb.append("/>");
		}
		
		return sb.toString();
	}
	
	public class DragListener implements View.OnDragListener {
		public boolean onDrag(View destinationView, DragEvent event) {
			View draggedView = null;
			if (event.getLocalState() instanceof View) draggedView = (View) event.getLocalState();
			try {
				final int action = event.getAction();
				switch (action) {
					case DragEvent.ACTION_DRAG_STARTED:
					log("drag started" + destinationView.toString());
					if (draggedView != null) ViewGroupUtils.removeView(draggedView);
					log("start ended");
					return true;
					case DragEvent.ACTION_DRAG_LOCATION:
					case DragEvent.ACTION_DRAG_ENTERED:
					log("drag entered location " + destinationView.toString());
					if (destinationView != deleteImg) {
						addView(placeHolder, (ViewGroup) destinationView, event);
					}
					return true;
					case DragEvent.ACTION_DRAG_EXITED:
					ViewGroupUtils.removeView(placeHolder);
					return true;
					case DragEvent.ACTION_DROP:
					if (destinationView == deleteImg && draggedView != null) {
						ViewGroup parent = (ViewGroup) draggedView.getParent();
						if (parent != null) {
							int index = parent.indexOfChild(draggedView);
							parent.removeView(draggedView);
							idManager.remove(draggedView);
							attributesValueMap.remove(draggedView);
							undoStack.push(new EditorAction(ACTION_REMOVE_VIEW, draggedView, parent, index, null, null));
							redoStack.clear();
							SketchwareUtil.showMessage(getContext(), "View deleted");
							vib.vibrate(100);
						}
						return true;
					}
					if (draggedView == null) {
						try {
							HashMap<String, Object> viewData = (HashMap) event.getLocalState();
							String classPath = viewData.get("class_path").toString();
							View newView;
							index = ((ViewGroup) destinationView).indexOfChild(placeHolder);
							
							if (classPath.equals("android.widget.WebView")) {
								newView = new PlaceholderWebView(getContext());
							} else if (classPath.equals("android.widget.VideoView")) {
								newView = new PlaceholderWidget(getContext(), "VideoView");
								newView.setForeground(getContext().getResources().getDrawable(R.drawable.item_video_view));
							} else if (classPath.equals("androidx.viewpager.widget.ViewPager")) {
								newView = new PlaceholderWidget(getContext(), "ViewPager");
							} else {
								newView = ReflectionUtils.createView(getContext(), classPath);
							}
							
							newView.setMinimumHeight((int) SketchwareUtil.getDip(getContext(), 30));
							newView.setMinimumWidth((int) SketchwareUtil.getDip(getContext(), 30));
							_rearrangeListener(newView);
							ViewGroupUtils.removeView(placeHolder);
							addView(newView, (ViewGroup) destinationView, event);
							
							if (newView instanceof ViewGroup) {
								newView.setOnDragListener(dragListener);
								int dp = (int) SketchwareUtil.getDip(getContext(), 8);
								newView.setPadding(dp, dp, dp, dp);
								com.shapun.layouteditor.utils.AnimationUtils.animate((ViewGroup) newView);
								newView.setBackground(UiUtils.createStrokedBackground(0, 0xFF000000, 1));
							}
							
							idManager.addNewId(newView, idManager.generateNewId(newView));
							AttributeSet attributeSet = new AttributeSet();
							attributesValueMap.put(newView, attributeSet);
							attributeSet.add(new Attribute("android:id", "@+id/" + idManager.getId(newView)));
							ViewGroup.LayoutParams params = newView.getLayoutParams();
							
							if (newView instanceof ViewGroup) {
								params.width = ViewGroup.LayoutParams.MATCH_PARENT;
								attributeSet.add(new Attribute("android:layout_width", "match_parent"));
								params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
								attributeSet.add(new Attribute("android:layout_height", "wrap_content"));
							} else {
								params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
								params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
								attributeSet.add(new Attribute("android:layout_height", "wrap_content"));
								attributeSet.add(new Attribute("android:layout_width", "wrap_content"));
							}
							addInitialAttributes(newView, viewData);
							
							EditorAction actionB = new EditorAction(ACTION_ADD_VIEW, newView, (ViewGroup) destinationView, index, null, null);
							undoStack.push(actionB);
							redoStack.clear();
							
							if (onWidgetAddListener != null) {
								onWidgetAddListener.onWidgetAdded(newView, (ViewGroup) destinationView);
							}
						} catch (Throwable t) {
							SketchwareUtil.showMessage(getContext(), event.toString() + t.toString());
						}
					} else {
						ViewGroupUtils.removeView(placeHolder);
						addView(draggedView, (ViewGroup) destinationView, event);
					}
					return true;
					case DragEvent.ACTION_DRAG_ENDED:
					log("drag ended" + destinationView.toString());
					ViewGroupUtils.removeView(placeHolder);
					if (event.getResult()) {
						vib.vibrate(100);
					} else {
						if (draggedView != null) {
							idManager.remove(draggedView);
							attributesValueMap.remove(draggedView);
						}
					}
					draggedView = null;
					return true;
					default:
					break;
				}
			} catch (Exception e) {
				showMessage(e.toString());
			}
			return true;
		}
		
		public int getIndexForNewChildOfLinearLayout(LinearLayout linear, DragEvent dragEvent) {
			int orientation = linear.getOrientation();
			if (orientation == LinearLayout.VERTICAL) {
				int posY = (int) dragEvent.getY();
				int index = 0;
				for (int i = 0; i < linear.getChildCount(); i++) {
					View child = linear.getChildAt(i);
					if (child == placeHolder) continue;
					if (child.getTop() < posY) index++;
				}
				return index;
			} else if (orientation == LinearLayout.HORIZONTAL) {
				int posX = (int) dragEvent.getX();
				int index = 0;
				for (int i = 0; i < linear.getChildCount(); i++) {
					View child = linear.getChildAt(i);
					if (child == placeHolder) continue;
					if (child.getRight() < posX) index++;
				}
				return index;
			}
			return -1;
		}
		
		public int getGravityForNewChildOfFrameLayout(FrameLayout frameLayout, DragEvent event) {
			int gravity = 0;
			int posX = (int) event.getX();
			int posY = (int) event.getY();
			int height = frameLayout.getHeight();
			int width = frameLayout.getWidth();
			int childHeight = placeHolder.getLayoutParams().height;
			int childWidth = placeHolder.getLayoutParams().width;
			if (posX > width / 2 - childWidth && posX < width / 2 + childWidth) {
				gravity |= Gravity.CENTER_HORIZONTAL;
			}
			if (posY > height / 2 - childHeight && posY < height / 2 + childHeight) {
				gravity |= Gravity.CENTER_VERTICAL;
			}
			if (posX > width - childWidth) {
				gravity |= Gravity.RIGHT;
			}
			if (posY > height - childWidth) {
				gravity |= Gravity.BOTTOM;
			}
			return gravity;
		}
		
		public void addView(View view, ViewGroup destination, DragEvent event) {
			try {
				if (destination instanceof LinearLayout) {
					int index = getIndexForNewChildOfLinearLayout((LinearLayout) destination, event);
					ViewGroupUtils.addView(view, (ViewGroup) destination, index);
					return;
				}
				if (destination instanceof FrameLayout) {
					ViewGroupUtils.addView(view, (ViewGroup) destination);
					((FrameLayout.LayoutParams) view.getLayoutParams())
					.gravity = getGravityForNewChildOfFrameLayout((FrameLayout) destination, event);
					return;
				}
				ViewGroupUtils.addView(view, (ViewGroup) destination);
			} catch (Exception e) {
				SketchwareUtil.showMessage(getContext(), view.toString() + destination.toString() + e.toString());
			}
		}
		
		public void addInitialAttributes(View view, HashMap<String, Object> map) {
			if (map.containsKey("initial_attributes")) {
				Map<String, String> initial_attributes = (Map) map.get("initial_attributes");
				for (String key : initial_attributes.keySet()) {
					Attribute attr = new Attribute(key, initial_attributes.get(key));
					applyAttribute(view, attr);
					attributesValueMap.get(view).add(attr);
				}
			}
		}
	}
	
	public void applyAttribute(View view, Attribute attribute) {
		// Inside applyAttribute():
		if (!isUndoRedoInProgress) {
			Attribute oldAttr = (attrSet != null) ? attrSet.getAttribute(attribute.getName()) : null;
			EditorAction action = new EditorAction(ACTION_UPDATE_ATTR, view, null, -1, oldAttr, attribute);
			undoStack.push(action);
			redoStack.clear();
		}
		final ArrayList<HashMap<String, Object>> listMap = new ArrayList<>();
		Class cls = view.getClass();
		Class viewParentClass = View.class.getSuperclass();
		while (cls != viewParentClass) {
			ArrayList<HashMap<String, Object>> tempListMap = attributesMap.get(cls.getName());
			if (tempListMap != null) listMap.addAll(0, tempListMap);
			cls = cls.getSuperclass();
		}
		final String attribute_name = attribute.getName();
		for (HashMap<String, Object> map : listMap) {
			if (map.get("attribute_name").toString().equals(attribute_name)) {
				applyAttribute(view, attribute, map);
				break;
			}
		}
	}
	
	public void applyAttribute(View view, Attribute attribute, HashMap<String, Object> map) {
		// Inside applyAttribute():
		if (!isUndoRedoInProgress) {
			Attribute oldAttr = (attrSet != null) ? attrSet.getAttribute(attribute.getName()) : null;
			EditorAction action = new EditorAction(ACTION_UPDATE_ATTR, view, null, -1, oldAttr, attribute);
			undoStack.push(action);
			redoStack.clear();
		}
		final String argument_type = map.get("argument_type").toString();
		String value = attribute.getValue();
		String attribute_name = attribute.getName();
		Object argumentValue = null;
		String member_name = map.get("member_name").toString();
		try {
			Object targetObj = view;
			if (map.containsKey("layout_params") && (boolean) map.get("layout_params")) {
				targetObj = view.getLayoutParams();
			}
			switch (argument_type) {
				case "boolean":
				argumentValue = value.equals("true");
				break;
				case "int":
				if (map.containsKey("dimension")) {
					argumentValue = (int) DimensionUtils.getValueInPx(getContext(), value);
				} else {
					argumentValue = Integer.parseInt(value);
				}
				break;
				case "float":
				if (map.containsKey("dimension")) {
					argumentValue = (float) DimensionUtils.getValueInPx(getContext(), value);
				} else {
					argumentValue = Float.parseFloat(value);
				}
				break;
				case "String":
				argumentValue = value;
				break;
				case "Size":
				if (value.equals("match_parent")) {
					argumentValue = ViewGroup.LayoutParams.MATCH_PARENT;
				} else if (value.equals("wrap_content")) {
					argumentValue = ViewGroup.LayoutParams.WRAP_CONTENT;
				} else {
					argumentValue = DimensionUtils.getValueInPx(getContext(), value);
				}
				break;
				case "Color":
				argumentValue = Color.parseColor(value);
				break;
				case "Drawable":
				String path = FileUtil.getPackageDataDir(((Activity) getContext())) + "/images/" +
				AttributeUtils.getImageName(value) + ".png";
				Bitmap bitmap = BitmapFactory.decodeFile(path);
				android.graphics.drawable.Drawable drawable =
				new android.graphics.drawable.BitmapDrawable(getContext().getResources(), bitmap);
				argumentValue = drawable;
				break;
				case "View":
				if (value.startsWith("@")) {
					argumentValue = idManager.getId(AttributeUtils.getName(value));
				} else {
					ArrayList<String> xml_list = (ArrayList<String>) map.get("xml_arguments");
					ArrayList<String> java_list = (ArrayList<String>) map.get("java_arguments");
					String Path = java_list.get(xml_list.indexOf(value));
					try {
						Class<?> cls = Class.forName(Path);
						Object obj = createObject(cls);
						argumentValue = obj;
					} catch (ClassNotFoundException e) {
						java.lang.reflect.Field field = ReflectionUtils.getStaticField(Path);
						argumentValue = field.get(null);
					}
				}
				break;
				case "enum":
				ArrayList<String> xml_arguments = (ArrayList<String>) map.get("xml_arguments");
				ArrayList<String> java_arguments = (ArrayList<String>) map.get("java_arguments");
				String pAth = java_arguments.get(xml_arguments.indexOf(value));
				try {
					Class<?> cls = Class.forName(pAth);
					Object obj = createObject(cls);
					argumentValue = obj;
				} catch (ClassNotFoundException e) {
					java.lang.reflect.Field field = ReflectionUtils.getStaticField(pAth);
					argumentValue = field.get(null);
				}
				break;
				case "flag":
				List<String> selectedList = Arrays.asList(value.split("\\|"));
				ArrayList<String> Xml_arguments = (ArrayList<String>) map.get("xml_arguments");
				ArrayList<String> Java_arguments = (ArrayList<String>) map.get("java_arguments");
				try {
					argumentValue = 0;
					for (String str : selectedList) {
						String fieldFullPath = Java_arguments.get(Xml_arguments.indexOf(str));
						argumentValue = (int) argumentValue | (int) ReflectionUtils.getStaticFieldValue(fieldFullPath);
					}
				} catch (Exception e) {
					SketchwareUtil.showMessage(getContext(), e.toString());
				}
				break;
				default:
				SketchwareUtil.showMessage(getContext(), "No such type found");
				break;
			}
			if (map.containsKey("member_type")) {
				if (map.get("member_type").toString().equals("method")) {
					if (map.containsKey("constant")) {
						String constant_path = map.get("constant").toString();
						Object constant = null;
						try {
							Class<?> cls = Class.forName(constant_path);
							try {
								constant = createObject(cls);
							} catch (Exception e) {
								showMessage("couldn't initialize " + cls.toString());
							}
						} catch (ClassNotFoundException e) {
							constant = ReflectionUtils.getStaticFieldValue(constant_path);
						}
						ReflectionUtils.invokeMethod(targetObj, member_name, constant, argumentValue);
						view.requestLayout();
					} else {
						ReflectionUtils.invokeMethod(targetObj, member_name, argumentValue);
					}
				} else {
					ReflectionUtils.setField(targetObj, member_name, argumentValue);
					view.requestLayout();
				}
			}
		} catch (Exception e) {
			showMessage("error while applying attribute \"" + attribute_name + "\"\n" + e.toString());
		}
	}
	
	public void showCommonAttributesDialog(View view) {
		final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
		View inflated = LayoutInflater.from(getContext()).inflate(R.layout.common_attributes, null);
		dialog.setNegativeButton("Cancel", null);
		RadioGroup rg_width = inflated.findViewById(R.id.rg_width);
		RadioGroup rg_height = inflated.findViewById(R.id.rg_height);
		LinearLayout lin_padding = inflated.findViewById(R.id.lin_padding);
		LinearLayout lin_margin = inflated.findViewById(R.id.lin_margin);
		RadioButton width_match_parent = inflated.findViewById(R.id.width_match_parent);
		RadioButton width_wrap_content = inflated.findViewById(R.id.width_wrap_content);
		RadioButton width_custom = inflated.findViewById(R.id.width_custom);
		EditText edittext_width = inflated.findViewById(R.id.edittext_width);
		RadioButton height_match_parent = inflated.findViewById(R.id.height_match_parent);
		RadioButton height_wrap_content = inflated.findViewById(R.id.height_wrap_content);
		RadioButton height_custom = inflated.findViewById(R.id.height_custom);
		EditText edittext_height = inflated.findViewById(R.id.edittext_height);
		EditText padding_all = inflated.findViewById(R.id.padding_all);
		CheckBox cb_padding_all = inflated.findViewById(R.id.cb_padding_all);
		EditText padding_left = inflated.findViewById(R.id.padding_left);
		EditText padding_top = inflated.findViewById(R.id.padding_top);
		TextView textview4 = inflated.findViewById(R.id.textview4);
		EditText padding_right = inflated.findViewById(R.id.padding_right);
		TextView textview5 = inflated.findViewById(R.id.textview5);
		EditText padding_bottom = inflated.findViewById(R.id.padding_bottom);
		EditText margin_all = inflated.findViewById(R.id.margin_all);
		CheckBox cb_margin_all = inflated.findViewById(R.id.cb_margin_all);
		EditText margin_left = inflated.findViewById(R.id.margin_left);
		EditText margin_top = inflated.findViewById(R.id.margin_top);
		EditText margin_right = inflated.findViewById(R.id.margin_right);
		EditText margin_bottom = inflated.findViewById(R.id.margin_bottom);
		dialog.setPositiveButton("Save", (dialog1, which) -> {});
		dialog.setView(inflated);
		dialog.show();
	}
	
	public void saveLayout(String layoutName) {
		try {
			// Validate inputs
			if (SAVE_PATH == null || SAVE_PATH.isEmpty()) {
				//SketchwareUtil.showMessage(getContext(), "SAVE_PATH is not set");
				return;
			}
			if (layoutName == null || layoutName.trim().isEmpty()) {
				//	SketchwareUtil.showMessage(getContext(), "Layout name cannot be empty");
				return;
			}
			
			// Generate XML
			StringBuilder xmlBuilder = new StringBuilder();
			if (editorLayout.getChildCount() == 0) {
				/*	xmlBuilder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n")
				.append("<LinearLayout\n")
				.append("    xmlns:android=\"http://schemas.android.com/apk/res/android\"\n")
				.append("    android:layout_width=\"match_parent\"\n")
				.append("    android:layout_height=\"match_parent\"\n")
				.append("    android:orientation=\"vertical\">\n")
				.append("</LinearLayout>");*/
			} else {
				View view = editorLayout.getChildAt(0);
				String xmlCode = generateCode(view);
				xmlCode = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
				xmlCode.substring(0, xmlCode.indexOf("\n")) + "\n" +
				"xmlns:android=\"http://schemas.android.com/apk/res/android\"" +
				xmlCode.substring(xmlCode.indexOf("\n"));
				xmlBuilder.append(xmlCode);
				
			}
			String xmlCode = xmlBuilder.toString();
			Log.d("ViewEditor", "Generated XML: " + xmlCode);
			
			// Prepare layout data
			HashMap<String, Object> layoutData = new HashMap<>();
			layoutData.put("name", layoutName);
			layoutData.put("xml", xmlCode);
			
			// Prepare file path and ensure directory exists
			String layoutsFile = SAVE_PATH + "/root_layout.json";
			File file = new File(layoutsFile);
			File parentDir = file.getParentFile();
			if (!parentDir.exists() && !parentDir.mkdirs()) {
				SketchwareUtil.showMessage(getContext(), "Failed to create directory: " + parentDir);
				return;
			}
			
			// Read existing layouts or create new list
			ArrayList<HashMap<String, Object>> layoutsList;
			if (FileUtil.isExistFile(layoutsFile)) {
				String json = FileUtil.readFile(layoutsFile);
				layoutsList = new Gson().fromJson(json,
				new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType());
			} else {
				layoutsList = new ArrayList<>();
			}
			
			// Remove existing layout with the same name (case-insensitive)
			layoutsList.removeIf(layout -> layoutName.equalsIgnoreCase((String) layout.get("name")));
			layoutsList.add(layoutData);
			
			// Serialize to JSON
			String json = new Gson().toJson(layoutsList);
			Log.d("ViewEditor", "JSON: " + json);
			
			// Write to file
			FileUtil.writeFile(layoutsFile, json);
			//SketchwareUtil.showMessage(getContext(), "Layout '" + layoutName + "' saved successfully");
			
		} catch (Exception e) {
			SketchwareUtil.showMessage(getContext(), "Error saving layout: " + e.getMessage());
			Log.e("ViewEditor", "saveLayout failed", e);
		}
	}
	
	public void loadLayout(String layoutName) {
		try {
			
			// Clear current editor
			editorLayout.removeAllViews();
			
			// Read layouts file
			String layoutsFile = SAVE_PATH + "/root_layout.json";
			if (!FileUtil.isExistFile(layoutsFile)) {
				// SketchwareUtil.showMessage(getContext(), "No saved layouts found");
				return;
			}
			
			// Parse JSON
			String json = FileUtil.readFile(layoutsFile);
			ArrayList<HashMap<String, Object>> layoutsList = new Gson().fromJson(json,
			new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType());
			
			// Find layout by name
			HashMap<String, Object> targetLayout = null;
			for (HashMap<String, Object> layout : layoutsList) {
				if (layoutName.equals(layout.get("name"))) {
					targetLayout = layout;
					break;
				}
			}
			
			if (targetLayout == null) {
				// SketchwareUtil.showMessage(getContext(), "Layout '" + layoutName + "' not found");
				return;
			}
			
			// Backup current state in case loading fails
			oldIdManager = idManager;
			oldAttributesValueMap = new HashMap<>(attributesValueMap);
			
			idManager = new IdManager();
			attributesValueMap.clear();
			
			// Parse XML
			String xmlContent = (String) targetLayout.get("xml");
			XmlPullParserFactory pullParserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = pullParserFactory.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(new java.io.StringReader(xmlContent));
			
			// Stack to track view hierarchy
			ArrayList<View> viewStack = new ArrayList<>();
			viewStack.add(editorLayout); // Root parent
			
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
					case XmlPullParser.START_TAG:
					String viewName = parser.getName();
					View view;
					// Use placeholder views for specific widgets
					if (viewName.equals("WebView")) {
						view = new PlaceholderWebView(getContext());
					} else if (viewName.equals("VideoView")) {
						view = new PlaceholderWidget(getContext(), "VideoView");
					} else if (viewName.equals("ViewPager")) {
						view = new PlaceholderWidget(getContext(), "ViewPager");
					} else {
						view = ReflectionUtils.createView(getContext(), "android.widget." + viewName);
						if (view == null) {
							view = ReflectionUtils.createView(getContext(), viewName);
						}
					}
					if (view != null) {
						// Setup view properties
						view.setOnClickListener(v -> showAttributesDialog(v));
						_rearrangeListener(view);
						if (view instanceof ViewGroup) {
							view.setOnDragListener(dragListener);
							view.setMinimumHeight((int) SketchwareUtil.getDip(getContext(), 30));
						}
						
						// Handle attributes
						AttributeSet attributeSet = new AttributeSet();
						attributesValueMap.put(view, attributeSet);
						for (int i = 0; i < parser.getAttributeCount(); i++) {
							String attributeName = parser.getAttributeName(i);
							String attributeValue = parser.getAttributeValue(i);
							if (attributeName.equals("android:id")) {
								idManager.addNewId(view, AttributeUtils.getName(attributeValue));
							}
							attributeSet.add(new Attribute(attributeName, attributeValue));
						}
						
						// Add to view stack
						viewStack.add(view);
					} else {
						//   SketchwareUtil.showMessage(getContext(), "Failed to create view: " + viewName);
					}
					break;
					
					case XmlPullParser.END_TAG:
					if (viewStack.size() > 1) { // Ensure we have a view to add
						View childView = viewStack.remove(viewStack.size() - 1); // Pop current view
						View parentView = viewStack.get(viewStack.size() - 1); // Get parent
						if (parentView instanceof ViewGroup) {
							((ViewGroup) parentView).addView(childView);
						} else {
							//SketchwareUtil.showMessage(getContext(), "Parent is not a ViewGroup: " + parentView.getClass().getSimpleName());
						}
					}
					break;
				}
				eventType = parser.next();
			}
			
			// Apply attributes after building the hierarchy
			for (View view : attributesValueMap.keySet()) {
				AttributeSet attributeSet = attributesValueMap.get(view);
				for (Attribute attr : attributeSet.getAttributes()) {
					applyAttribute(view, attr);
				}
			}
			
			//SketchwareUtil.showMessage(getContext(), "Layout '" + layoutName + "' loaded successfully");
		} catch (Exception e) {
			// Restore previous state on failure
			idManager = oldIdManager;
			attributesValueMap = oldAttributesValueMap;
			editorLayout.removeAllViews();
			// Re-add views from old state
			for (View view : oldAttributesValueMap.keySet()) {
				if (view.getParent() == null) {
					editorLayout.addView(view);
				}
			}
			// SketchwareUtil.showMessage(getContext(), "Error loading layout: " + e.toString());
		}
	}
	
	public String getXMLCode() {
		ViewGroup rootView = (ViewGroup) editorLayout.getChildAt(0);
		String code;
		if (rootView == null) {
			code = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
			"<LinearLayout\n" +
			"    xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
			"    android:layout_width=\"match_parent\"\n" +
			"    android:layout_height=\"match_parent\"\n" +
			"    android:orientation=\"vertical\">\n" +
			"</LinearLayout>";
		} else {
			code = generateCode(rootView);
			// Only add the namespace if it doesn't already exist in the generated code
			if (!code.contains("xmlns:android")) {
				code = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
				code.substring(0, code.indexOf("\n")) + "\n" +
				"    xmlns:android=\"http://schemas.android.com/apk/res/android\"" +
				code.substring(code.indexOf("\n"));
			} else {
				code = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + code;
			}
		}
		return code;
	}
	
	public class PlaceholderWidget extends View {
		private final String placeholderText;
		private final Paint placeholderPaint;
		private Drawable placeholderDrawable;
		
		public PlaceholderWidget(Context context, String placeholderText) {
			super(context);
			this.placeholderText = placeholderText;
			
			// Initialize paint for text
			placeholderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			placeholderPaint.setColor(Color.BLACK);
			placeholderPaint.setTextSize(SketchwareUtil.getDip(context, 14));
			placeholderPaint.setTextAlign(Paint.Align.CENTER);
			
			// Initialize drawable (optional)
			placeholderDrawable = context.getResources().getDrawable(R.drawable.item_video_view);
			setForeground(placeholderDrawable); // Set foreground drawable
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			// Draw placeholder text at the center
			canvas.drawText(placeholderText, getWidth() / 2f, getHeight() / 2f, placeholderPaint);
		}
	}
	public class PlaceholderWebView extends WebView {
		private final Paint placeholderPaint;
		
		public PlaceholderWebView(Context context) {
			super(context);
			placeholderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			placeholderPaint.setColor(Color.BLACK);
			placeholderPaint.setTextSize(SketchwareUtil.getDip(context, 14));
			placeholderPaint.setTextAlign(Paint.Align.CENTER);
			setForeground(getResources().getDrawable(android.R.drawable.ic_media_play));
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			canvas.drawText("WebView", getWidth() / 2f, getHeight() / 2f, placeholderPaint);
		}
	}
	
	public class Listview_widgetsAdapter extends RecyclerView.Adapter<Listview_widgetsAdapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;
		
		public Listview_widgetsAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@NonNull
		@Override
		public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			LayoutInflater _inflater = LayoutInflater.from(parent.getContext());
			View _view = _inflater.inflate(R.layout.views_item, parent, false);
			return new ViewHolder(_view);
		}
		
		@Override
		public void onBindViewHolder(@NonNull ViewHolder holder, int _position) {
			holder.tv_name.setText(_data.get(_position).get("name").toString());
			holder.img_icon.setVisibility(View.GONE);
			holder.lin_main.setOnLongClickListener(v -> {
				DragAndDropUtils.startDragAndDrop(v, null, new View.DragShadowBuilder(holder.lin_main), _data.get(_position), 1);
				return true;
			});
			/*
			if (_data.get(_position).get("name").toString().equals("TextView")) {
			holder.img_icon.setImageResource(R.drawable.widget_text_view);
			} else if (_data.get(_position).get("name").toString().equals("ImageView")) {
			holder.img_icon.setImageResource(R.drawable.widget_image_view);
			} else if (_data.get(_position).get("name").toString().equals("Button")) {
			holder.img_icon.setImageResource(R.drawable.widget_button);
			} else if (_data.get(_position).get("name").toString().equals("FrameLayout")) {
			holder.img_icon.setImageResource(R.drawable.widget_linear_horizontal);
			} else if (_data.get(_position).get("name").toString().equals("Switch")) {
			holder.img_icon.setImageResource(R.drawable.widget_switch);
			} else if (_data.get(_position).get("name").toString().equals("LinearLayout")) {
			holder.img_icon.setImageResource(R.drawable.widget_linear_horizontal);
			} else if (_data.get(_position).get("name").toString().equals("CheckBox")) {
			holder.img_icon.setImageResource(R.drawable.widget_check_box);
			} else if (_data.get(_position).get("name").toString().equals("RadioButton")) {
			holder.img_icon.setImageResource(R.drawable.widget_radio_button);
			} else if (_data.get(_position).get("name").toString().equals("EditText")) {
			holder.img_icon.setImageResource(R.drawable.widget_edit_text);
			} else if (_data.get(_position).get("name").toString().equals("SeekBar")) {
			holder.img_icon.setImageResource(R.drawable.widget_seek_bar);
			} else if (_data.get(_position).get("name").toString().equals("ProgressBar")) {
			holder.img_icon.setImageResource(R.drawable.widget_progress_bar);
			} else if (_data.get(_position).get("name").toString().equals("RatingBar")) {
			holder.img_icon.setImageResource(R.drawable.star_blank);
			} else if (_data.get(_position).get("name").toString().equals("SearchView")) {
			holder.img_icon.setImageResource(R.drawable.search_icon_grey);
			} else if (_data.get(_position).get("name").toString().equals("ListView")) {
			holder.img_icon.setImageResource(R.drawable.widget_list_view);
			} else if (_data.get(_position).get("name").toString().equals("DigitalClock")) {
			holder.img_icon.setImageResource(R.drawable.widget_timer);
			} else if (_data.get(_position).get("name").toString().equals("TimePicker")) {
			holder.img_icon.setImageResource(R.drawable.widget_time_picker_dialog);
			} else if (_data.get(_position).get("name").toString().equals("HorizontalScrollView")) {
			holder.img_icon.setImageResource(R.drawable.widget_horizontalscrollview);
			} else if (_data.get(_position).get("name").toString().equals("ScrollView")) {
			holder.img_icon.setImageResource(R.drawable.widget_scrollview);
			} else if (_data.get(_position).get("name").toString().equals("VideoView")) {
			holder.img_icon.setImageResource(R.drawable.widget_video_view);
			} else {
			holder.img_icon.setVisibility(View.GONE);
			}
			*/
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			LinearLayout lin_main;
			TextView tv_name;
			ImageView img_icon;
			
			public ViewHolder(@NonNull View itemView) {
				super(itemView);
				lin_main = itemView.findViewById(R.id.lin_main);
				tv_name = itemView.findViewById(R.id.tv_name);
				img_icon = itemView.findViewById(R.id.img_icon);
			}
		}
	}
	
	public /*static*/ void showProperties() {
		LinearLayoutCompat base = attributesContainer;
		base.setVisibility(View.VISIBLE);
		anim.setTarget(base);
		anim.setProperty(View.TRANSLATION_Y);
		anim.setFloatValues(new float[]{(float) 0});
		anim.setInterpolator(new DecelerateInterpolator());
		anim.start();
	}
	
	public /*static */void hideProperties() {
		LinearLayoutCompat base = attributesContainer;
		anim.setTarget(base);
		anim.setProperty(View.TRANSLATION_Y);
		anim.setFloatValues(new float[]{(float) base.getHeight()});
		anim.setInterpolator(new DecelerateInterpolator());
		anim.start();
	}
	
	public boolean isHiddenProperties() {
		return attributesContainer.getTranslationY() == ((float) attributesContainer.getHeight());
	}
	
	public boolean undo() {
		if (undoStack.isEmpty()) {
			Log.d("ViewEditor", "Undo stack is empty");
			return false;
		}
		
		isUndoRedoInProgress = true;
		EditorAction action = undoStack.pop();
		Log.d("ViewEditor", "Undoing action: " + action.actionType + ", View ID: " + action.viewId);
		
		try {
			switch (action.actionType) {
				case ACTION_ADD_VIEW:
				// Undo = Remove the view
				if (action.view != null && action.view.getParent() != null) {
					ViewGroup parent = (ViewGroup) action.view.getParent();
					int index = parent.indexOfChild(action.view);
					parent.removeView(action.view);
					idManager.remove(action.view);
					attributesValueMap.remove(action.view);
					redoStack.push(new EditorAction(ACTION_REMOVE_VIEW, action.view, parent, index, null, null));
					Log.d("ViewEditor", "Undid ACTION_ADD_VIEW for view: " + action.viewId);
				} else {
					Log.e("ViewEditor", "View or parent is null for ACTION_ADD_VIEW");
					return false;
				}
				break;
				
				case ACTION_REMOVE_VIEW:
				// Undo = Re-add the view
				if (action.parent != null && action.view != null) {
					if (action.index >= 0 && action.index <= action.parent.getChildCount()) {
						action.parent.addView(action.view, action.index);
					} else {
						action.parent.addView(action.view);
						action.index = action.parent.getChildCount() - 1; // Update index
					}
					idManager.addNewId(action.view, action.viewId);
					// Reattach listeners
					action.view.setOnClickListener(v -> showAttributesDialog(v));
					_rearrangeListener(action.view);
					if (action.view instanceof ViewGroup) {
						action.view.setOnDragListener(dragListener);
					}
					redoStack.push(new EditorAction(ACTION_ADD_VIEW, action.view, action.parent, action.index, null, null));
					Log.d("ViewEditor", "Undid ACTION_REMOVE_VIEW for view: " + action.viewId);
				} else {
					Log.e("ViewEditor", "Invalid parent or view for ACTION_REMOVE_VIEW");
					return false;
				}
				break;
				
				case ACTION_UPDATE_ATTR:
				// Undo = Revert to old attribute
				if (action.view != null && action.oldAttr != null) {
					AttributeSet attrSet = attributesValueMap.get(action.view);
					if (attrSet == null) {
						attrSet = new AttributeSet();
						attributesValueMap.put(action.view, attrSet);
					}
					attrSet.add(action.oldAttr);
					applyAttribute(action.view, action.oldAttr);
					redoStack.push(new EditorAction(ACTION_UPDATE_ATTR, action.view, null, -1, action.newAttr, action.oldAttr));
					Log.d("ViewEditor", "Undid ACTION_UPDATE_ATTR for view: " + action.viewId);
				} else {
					Log.e("ViewEditor", "Invalid view or oldAttr for ACTION_UPDATE_ATTR");
					return false;
				}
				break;
				
				default:
				Log.e("ViewEditor", "Unknown action type: " + action.actionType);
				return false;
			}
		} catch (Exception e) {
			Log.e("ViewEditor", "Undo failed: " + e.getMessage(), e);
			SketchwareUtil.showMessage(getContext(), "Undo failed: " + e.getMessage());
			return false;
		} finally {
			isUndoRedoInProgress = false;
		}
		
		return true;
	}
	
	public boolean redo() {
		if (redoStack.isEmpty()) {
			Log.d("ViewEditor", "Redo stack is empty");
			return false;
		}
		
		isUndoRedoInProgress = true;
		EditorAction action = redoStack.pop();
		Log.d("ViewEditor", "Redoing action: " + action.actionType + ", View ID: " + action.viewId);
		
		try {
			switch (action.actionType) {
				case ACTION_ADD_VIEW:
				// Redo = Add the view again
				if (action.parent != null && action.view != null) {
					// Ensure view is not already attached
					if (action.view.getParent() != null) {
						((ViewGroup) action.view.getParent()).removeView(action.view);
					}
					if (action.index >= 0 && action.index <= action.parent.getChildCount()) {
						action.parent.addView(action.view, action.index);
					} else {
						action.parent.addView(action.view);
						action.index = action.parent.getChildCount() - 1; // Update index
					}
					idManager.addNewId(action.view, action.viewId);
					// Reattach listeners
					action.view.setOnClickListener(v -> showAttributesDialog(v));
					_rearrangeListener(action.view);
					if (action.view instanceof ViewGroup) {
						action.view.setOnDragListener(dragListener);
					}
					undoStack.push(new EditorAction(ACTION_REMOVE_VIEW, action.view, action.parent, action.index, null, null));
					Log.d("ViewEditor", "Redid ACTION_ADD_VIEW for view: " + action.viewId);
				} else {
					Log.e("ViewEditor", "Invalid parent or view for ACTION_ADD_VIEW");
					return false;
				}
				break;
				
				case ACTION_REMOVE_VIEW:
				// Redo = Delete the view again
				if (action.view != null && action.view.getParent() != null) {
					ViewGroup parent = (ViewGroup) action.view.getParent();
					int index = parent.indexOfChild(action.view);
					parent.removeView(action.view);
					idManager.remove(action.view);
					attributesValueMap.remove(action.view);
					undoStack.push(new EditorAction(ACTION_ADD_VIEW, action.view, parent, index, null, null));
					Log.d("ViewEditor", "Redid ACTION_REMOVE_VIEW for view: " + action.viewId);
				} else {
					Log.e("ViewEditor", "View or parent is null for ACTION_REMOVE_VIEW");
					return false;
				}
				break;
				
				case ACTION_UPDATE_ATTR:
				// Redo = Reapply the new attribute
				if (action.view != null && action.newAttr != null) {
					AttributeSet attrSet = attributesValueMap.get(action.view);
					if (attrSet == null) {
						attrSet = new AttributeSet();
						attributesValueMap.put(action.view, attrSet);
					}
					attrSet.add(action.newAttr);
					applyAttribute(action.view, action.newAttr);
					undoStack.push(new EditorAction(ACTION_UPDATE_ATTR, action.view, null, -1, action.oldAttr, action.newAttr));
					Log.d("ViewEditor", "Redid ACTION_UPDATE_ATTR for view: " + action.viewId + ", attr: " + action.newAttr.getName());
				} else {
					Log.e("ViewEditor", "Invalid view or newAttr for ACTION_UPDATE_ATTR");
					return false;
				}
				break;
				
				default:
				Log.e("ViewEditor", "Unknown action type: " + action.actionType);
				return false;
			}
		} catch (Exception e) {
			Log.e("ViewEditor", "Redo failed: " + e.getMessage(), e);
			SketchwareUtil.showMessage(getContext(), "Redo failed: " + e.getMessage());
			return false;
		} finally {
			isUndoRedoInProgress = false;
		}
		
		return true;
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int[] _location = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int[] _location = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double) _arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}
