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
import android.util.Base64;
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
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
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
import android.widget.ProgressBar;
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
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.besome.blacklogics.*;
import com.besome.blacklogics.DesignActivity;
import com.besome.blacklogics.FileUtil;
import com.besome.blacklogics.SketchwareUtil;
import com.besome.blacklogics.beans.ProjectActivityBean;
import com.besome.blacklogics.beans.ProjectActivityBean.ViewBean;
import com.besome.blacklogics.logic.editor.LogicEditorActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.nexusteam.internal.os.layouteditor.util.*;
import com.nexusteam.internal.os.layouteditor.util.WidgetStorageManager;
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
import java.io.StringReader;
import java.lang.ClassNotFoundException;
import java.lang.InstantiationException;
import java.lang.NoSuchMethodException;
import java.lang.IllegalAccessException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.Stack;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


public class ViewEditor extends LinearLayout {
	
	public static interface OnWidgetAdd {
		void onWidgetAdded(View widget, ViewGroup parent);
	}
	
	private ArrayList<String> typeList = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> viewsList = new ArrayList<>();
	
	private HashMap<String, Object> widget_add_map = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> list_widget_map = new ArrayList<>();
	
	private LinearLayout lin_toolbar;
	private LinearLayout linear7;
	private ImageView img_views;
	private LinearLayout linear9;
	private ImageView img_import;
	private ImageView img_copy;
	private ImageView img_add_image;
	private LinearLayout linear8;
	public  LinearLayout editorLayout;
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
	public String SAVE_PATH = "";
	public static String SC_ID = "601";
	public int index;
	private Vibrator vib;
	private View placeHolder;
	private DragListener dragListener;
	private ImageView deleteImg;
	private HashMap<String, ArrayList<HashMap<String, Object>>> attributesMap = new HashMap<>();
	private HashMap<String, ArrayList<HashMap<String, Object>>> parentAttributesMap = new HashMap<>();
	private HashMap<View, AttributeSet> attributesValueMap = new HashMap<>();
	public static IdManager idManager = new IdManager();
	public IdManager oldIdManager = new IdManager();
	private OnWidgetAdd onWidgetAddListener;
	// Undo/Redo stacks (action-based)
	private Stack<EditorAction> undoStack = new Stack<>();
	private Stack<EditorAction> redoStack = new Stack<>();
	private boolean isUndoRedoInProgress = false;
	
	public Intent intent = new Intent();
	
	private TempLayoutManager tempLayoutManager;
	private DesignActivity designActivity;
	
	// Action types
	private static final int ACTION_ADD_VIEW = 1;
	private static final int ACTION_REMOVE_VIEW = 2;
	private static final int ACTION_UPDATE_ATTR = 3;
	
	private HashMap<String, Stack<EditorAction>> undoStacks = new HashMap<>();
	private HashMap<String, Stack<EditorAction>> redoStacks = new HashMap<>();
	
	// AES Encryption Key (256-bit, must be 32 bytes)
	private static final String AES_KEY = "NexusTeamSmartIndia2025LayoutKey"; // Replace with secure key management
	private static final String AES_IV = "1234567890abcdef"; // 16-byte IV, replace with secure IV
	// Obfuscated key & IV parts (XOR + split)
	private static final int[] KEY_PARTS = {78,101,120,117,115,84,101,97,109,83,109,97,114,116,73,110,100,105,97,50,48,50,53,76,97,121,111,117,116};
	private static final int[] IV_PARTS  = {49,50,51,52,53,54,55,56,57,48,97,98,99,100,101,102};
	
	private static final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";
	
	// Action data structure
	private static class EditorAction {
		int actionType;
		View view;
		ViewGroup parent;
		int index;
		Attribute oldAttr;
		Attribute newAttr;
		String viewId;
		String activityName;
		
		EditorAction(int actionType, View view, ViewGroup parent, int index,
		Attribute oldAttr, Attribute newAttr, String activityName) {
			this.actionType = actionType;
			this.view = view;
			this.parent = parent;
			this.index = index;
			this.oldAttr = oldAttr;
			this.newAttr = newAttr;
			this.viewId = (view != null) ? idManager.getId(view) : null;
			this.activityName = activityName;
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
	
	public void setScId(String SC_ID) {
		this.SC_ID = SC_ID;
	}
	
	public void setDesignActivity(DesignActivity designActivity) {
		this.designActivity = designActivity;
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
		
		this.tempLayoutManager = new TempLayoutManager(this);
		setupListeners();
		initializeLogic(context);
	}
	
	public void setPath(String SAVE_PATH) {
		this.SAVE_PATH = SAVE_PATH;
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
		editorLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				hideProperties();
			}
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
		//DragAndDropUtils.startDragAndDrop(img_import, null, new View.DragShadowBuilder(null), null, 0);
		deleteImg = new ImageView(context);
		deleteImg.setImageResource(R.drawable.ic_delete_white);
		deleteImg.setColorFilter(0xFF757575, PorterDuff.Mode.SRC_ATOP);
		deleteImg.setVisibility(View.GONE);
		
		if (tempLayoutManager != null) {
			tempLayoutManager.showGlobalRestoreDialog();
		}
		
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
		listview_widgets.setAdapter(new Listview_widgetsAdapter(context, viewsList));
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
	
	/*
* Modified showAttributesDialog method to use a tabbed layout with Attributes and Events tabs,
* supporting listeners like onClick, onTouch, and onLongPress based on widget type.
*/	
	
	public void showAttributesDialog(final View view) {
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
		
		// Collect parent-dependent attributes
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
		
		// Initialize listeners list based on widget type
		final ArrayList<HashMap<String, Object>> listenersList = new ArrayList<>();
		String viewType = view.getClass().getSimpleName().toLowerCase();
		
		// Common listeners for most views
		listenersList.add(createListenerMap("onClick", "View.OnClickListener"));
		listenersList.add(createListenerMap("onLongPress", "View.OnLongPressListener"));
		listenersList.add(createListenerMap("onTouch", "View.OnTouchListener"));
		
		// EditText and text-based views
		if (viewType.contains("edittext") || viewType.contains("textview")) {
			listenersList.add(createListenerMap("onTextChanged", "TextWatcher"));
			listenersList.add(createListenerMap("afterTextChanged", "TextWatcher"));
		}
		
		// Button and ImageButton
		if (viewType.contains("button") || viewType.contains("imagebutton")) {
			listenersList.add(createListenerMap("onLongClick", "View.OnLongClickListener"));
		}
		
		// SeekBar
		if (viewType.contains("seekbar")) {
			listenersList.add(createListenerMap("onProgressChanged", "SeekBar.OnSeekBarChangeListener"));
			listenersList.add(createListenerMap("onStartTrackingTouch", "SeekBar.OnSeekBarChangeListener"));
			listenersList.add(createListenerMap("onStopTrackingTouch", "SeekBar.OnSeekBarChangeListener"));
		}
		
		// CheckBox, Switch, ToggleButton
		if (viewType.contains("checkbox") || viewType.contains("switch") || viewType.contains("togglebutton")) {
			listenersList.add(createListenerMap("onCheckedChanged", "CompoundButton.OnCheckedChangeListener"));
		}
		
		// ListView, RecyclerView, Spinner
		if (viewType.contains("listview") || viewType.contains("recyclerview")) {
			listenersList.add(createListenerMap("onItemClick", "AdapterView.OnItemClickListener"));
			listenersList.add(createListenerMap("onItemLongClick", "AdapterView.OnItemLongClickListener"));
		}
		if (viewType.contains("spinner")) {
			listenersList.add(createListenerMap("onItemSelected", "AdapterView.OnItemSelectedListener"));
			listenersList.add(createListenerMap("onNothingSelected", "AdapterView.OnItemSelectedListener"));
		}
		
		// ViewPager
		if (viewType.contains("viewpager")) {
			listenersList.add(createListenerMap("onPageSelected", "ViewPager.OnPageChangeListener"));
			listenersList.add(createListenerMap("onPageScrolled", "ViewPager.OnPageChangeListener"));
			listenersList.add(createListenerMap("onPageScrollStateChanged", "ViewPager.OnPageChangeListener"));
		}
		
		// ScrollView
		if (viewType.contains("scrollview")) {
			listenersList.add(createListenerMap("onScrollChanged", "ViewTreeObserver.OnScrollChangedListener"));
		}
		
		// RatingBar
		if (viewType.contains("ratingbar")) {
			listenersList.add(createListenerMap("onRatingChanged", "RatingBar.OnRatingBarChangeListener"));
		}
		
		// TextInputLayout or custom fields
		if (viewType.contains("textinput")) {
			listenersList.add(createListenerMap("onTextChanged", "TextWatcher"));
		}
		
		// WebView
		if (viewType.contains("webview")) {
			listenersList.add(createListenerMap("onPageStarted", "WebViewClient"));
			listenersList.add(createListenerMap("onPageFinished", "WebViewClient"));
			listenersList.add(createListenerMap("shouldOverrideUrlLoading", "WebViewClient"));
		}
		
		
		// Create or clear attributes container
		if (attributesContainer == null) {
			attributesContainer = new LinearLayoutCompat(getContext());
			attributesContainer.setOrientation(LinearLayout.VERTICAL);
			attributesContainer.setLayoutParams(new LinearLayout.LayoutParams(
			ViewGroup.LayoutParams.MATCH_PARENT,
			ViewGroup.LayoutParams.WRAP_CONTENT));
			attributesContainer.setBackgroundColor(0xFFFFFFFF);
			((ViewGroup) ((Activity) getContext()).getWindow().findViewById(android.R.id.content))
			.addView(attributesContainer);
		} else {
			attributesContainer.removeAllViews();
		}
		
		showProperties();
		
		// Inflate new tabbed layout
		View inflated = LayoutInflater.from(getContext()).inflate(R.layout.attributes_bottom_sheet, attributesContainer, false);
		attributesContainer.addView(inflated);
		
		// Initialize UI components
		final TextView tv_view_id = inflated.findViewById(R.id.tv_view_id);
		final RecyclerView rv_attributes = inflated.findViewById(R.id.rv_attributes);
		final RecyclerView rv_listeners = inflated.findViewById(R.id.listeners);
		final ImageView img_edit_id = inflated.findViewById(R.id.img_edit_id);
		final ImageView img_common_attributes = inflated.findViewById(R.id.img_common_attributes);
		final ImageView img_custom = inflated.findViewById(R.id.img_custom);
		final com.google.android.material.tabs.TabLayout tabLayout = inflated.findViewById(R.id.attributesOrListener);
		final LinearLayout attributesTab = inflated.findViewById(R.id.attributesTab);
		final LinearLayout eventTab = inflated.findViewById(R.id.eventTab);
		
		// Setup TabLayout
		tabLayout.removeAllTabs();
		tabLayout.addTab(tabLayout.newTab().setText("attributes"));
		tabLayout.addTab(tabLayout.newTab().setText("events"));
		
		// Setup RecyclerViews
		LinearLayoutManager attrLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
		rv_attributes.setLayoutManager(attrLayoutManager);
		AttributeRecyclerAdapter attrAdapter = new AttributeRecyclerAdapter(getContext(), attributesList);
		rv_attributes.setAdapter(attrAdapter);
		
		LinearLayoutManager listenerLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
		rv_listeners.setLayoutManager(listenerLayoutManager);
		ListenerRecyclerAdapter listenerAdapter = new ListenerRecyclerAdapter(getContext(), listenersList);
		rv_listeners.setAdapter(listenerAdapter);
		
		// Set corner radius and background
		GradientDrawable background = new GradientDrawable();
		int d = (int) getContext().getResources().getDisplayMetrics().density;
		background.setColor(0xFFFFFFFF);
		background.setCornerRadius(d * 16);
		inflated.setBackground(background);
		inflated.setClipToOutline(true);
		
		// Set view ID
		tv_view_id.setText(idManager.getId(view));
		
		// Tab selection listener
		tabLayout.addOnTabSelectedListener(new com.google.android.material.tabs.TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(com.google.android.material.tabs.TabLayout.Tab tab) {
				if (tab.getPosition() == 0) {
					attributesTab.setVisibility(View.VISIBLE);
					eventTab.setVisibility(View.GONE);
				} else {
					attributesTab.setVisibility(View.GONE);
					eventTab.setVisibility(View.VISIBLE);
				}
			}
			
			@Override
			public void onTabUnselected(com.google.android.material.tabs.TabLayout.Tab tab) {}
			
			@Override
			public void onTabReselected(com.google.android.material.tabs.TabLayout.Tab tab) {}
		});
		
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
		attrAdapter.setOnItemClickListener(position -> {
			final HashMap<String, Object> attribute = attributesList.get(position);
			final String attrName = attribute.get("attribute_name").toString();
			final String attrType = attribute.get("argument_type").toString();
			final AlertDialog.Builder inputDialog = new AlertDialog.Builder(getContext());
			inputDialog.setTitle(attribute.get("name").toString());
			inputDialog.setNeutralButton("Cancel", null);
			attrSet = attributesValueMap.get(view);
			String currentValue = attrSet != null && attrSet.getAttribute(attrName) != null ?
			attrSet.getAttribute(attrName).getValue() : "";
			
			// Handle attribute input dialogs (unchanged from original)
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
				FileUtil.listDir(FileUtil.getExternalStorageDir() + "/.blacklogics/resources/images/" + designActivity.currentActivityBean.getScId() + "/", imagePaths);
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
		
		// Listener item click listener
		listenerAdapter.setOnItemClickListener(position -> {
			saveLayout(designActivity.currentActivityBean.getLayoutName());
			DesignActivity designActivity = (DesignActivity) getContext();
			final HashMap<String, Object> listener = listenersList.get(position);
			
			final String listenerName = listener.get("name").toString();
			final String listenerType = listener.get("type").toString();
			
			// Load previous list
			if (FileUtil.isExistFile(FileUtil.getExternalStorageDir()
			.concat("/.blacklogics/data/")
			.concat(SC_ID)
			.concat("/basedata"))) {
				list_widget_map = new Gson().fromJson(
				FileUtil.readFile(FileUtil.getExternalStorageDir()
				.concat("/.blacklogics/data/")
				.concat(SC_ID)
				.concat("/basedata")),
				new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType()
				);
			} else {
				list_widget_map = new ArrayList<>();
			}
			
			// New map
			widget_add_map = new HashMap<>();
			widget_add_map.put("widget_id", idManager.getId(view));
			widget_add_map.put("widget_type", getWidgetTypeName(view));
			widget_add_map.put("widget_listener_name", listenerName);
			widget_add_map.put("activityName", designActivity.currentActivityBean.getActivityName());
			
			// ✅ Check if same entry already exists
			boolean alreadyExists = false;
			for (HashMap<String, Object> item : list_widget_map) {
				if (item.get("widget_id").equals(widget_add_map.get("widget_id"))
				&& item.get("widget_listener_name").equals(widget_add_map.get("widget_listener_name"))
				&& item.get("activityName").equals(widget_add_map.get("activityName"))) {
					alreadyExists = true;
					break;
				}
			}
			
			if (!alreadyExists) {
				list_widget_map.add(widget_add_map);
				FileUtil.writeFile(
				FileUtil.getExternalStorageDir()
				.concat("/.blacklogics/data/")
				.concat(SC_ID)
				.concat("/basedata"),
				new Gson().toJson(list_widget_map)
				);
			}
			
			// Dynamic event set hoga (name ya type se)
			String event = listenerType;
			
			intent.putExtra("id", idManager.getId(view));
			intent.putExtra("event", event);
			intent.putExtra("event_text", idManager.getId(view));
			intent.putExtra("filename",
			idManager.getId(view) +
			designActivity.currentActivityBean.getActivityName() +
			event);
			intent.putExtra("sc_id", DesignActivity.getScId());
			intent.putExtra("activityName", designActivity.currentActivityBean.getActivityName());
			intent.putExtra("widgetId", idManager.getId(view));
			intent.putExtra("type", "");
			intent.setClass(getContext(), LogicEditorActivity.class);
			getContext().startActivity(intent);
		});
		
		
		
		// Set listeners for the view
		_rearrangeListener(view);
		if (view instanceof ViewGroup) {
			view.setOnDragListener(dragListener);
			view.setMinimumHeight((int) SketchwareUtil.getDip(getContext(), 30));
		}
	}
	
	// Helper method to create listener map
	private HashMap<String, Object> createListenerMap(String name, String type) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("name", name);
		map.put("type", type);
		return map;
	}
	
	// New RecyclerView Adapter for listeners
	public class ListenerRecyclerAdapter extends RecyclerView.Adapter<ListenerRecyclerAdapter.ViewHolder> {
		private ArrayList<HashMap<String, Object>> data;
		private Context context;
		private AttributeRecyclerAdapter.OnItemClickListener listener;
		
		public ListenerRecyclerAdapter(Context context, ArrayList<HashMap<String, Object>> data) {
			this.context = context;
			this.data = data;
		}
		
		public void setOnItemClickListener(AttributeRecyclerAdapter.OnItemClickListener listener) {
			this.listener = listener;
		}
		
		@NonNull
		@Override
		public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(context).inflate(R.layout.attribute_view, parent, false);
			return new ViewHolder(view);
		}
		
		@Override
		public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
			HashMap<String, Object> item = data.get(position);
			String listenerName = item.get("name").toString();
			holder.tvName.setText(listenerName);
			
			// Set icon based on listener type (customize as needed)
			holder.ivIcon.setVisibility(View.GONE);
			/*
if (listenerName.equals("onClick")) {
holder.ivIcon.setImageResource(R.drawable.ic_touch_app_48);
} else if (listenerName.equals("onLongPress") || listenerName.equals("onLongClick")) {
holder.ivIcon.setImageResource(R.drawable.ic_gesture_48);
} else if (listenerName.equals("onTouch")) {
holder.ivIcon.setImageResource(R.drawable.ic_swipe_48);
} else if (listenerName.equals("onTextChanged")) {
holder.ivIcon.setImageResource(R.drawable.ic_text_fields_48);
} else if (listenerName.equals("onProgressChanged")) {
holder.ivIcon.setImageResource(R.drawable.ic_sliders_48);
} else if (listenerName.equals("onCheckedChanged")) {
holder.ivIcon.setImageResource(R.drawable.ic_check_box_48);
} else if (listenerName.equals("onItemClick")) {
holder.ivIcon.setImageResource(R.drawable.ic_list_48);
} else {
holder.ivIcon.setVisibility(View.GONE);
}*/			
			
			holder.itemView.setOnClickListener(v -> {
				if (listener != null) {
					listener.onItemClick(position);
				}
			});
		}
		
		@Override
		public int getItemCount() {
			return data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			TextView tvName;
			ImageView ivIcon;
			
			public ViewHolder(@NonNull View itemView) {
				super(itemView);
				tvName = itemView.findViewById(R.id.tv_name);
				ivIcon = itemView.findViewById(R.id.iv_icon);
			}
		}
	}
	
	private HashMap<String, Object> findAttributeMap(String attrName, View view) {
		Class<?> cls = view.getClass();
		while (cls != null && !cls.equals(View.class.getSuperclass())) {
			ArrayList<HashMap<String, Object>> tempList = attributesMap.get(cls.getName());
			if (tempList != null) {
				for (HashMap<String, Object> map : tempList) {
					if (map.get("attribute_name").equals(attrName)) {
						return map;
					}
				}
			}
			cls = cls.getSuperclass();
		}
		return null;
	}
	
	// Helper: format value with correct unit
	private String formatAttributeValue(Attribute attr, HashMap<String, Object> map) {
		String value = attr.getValue();
		
		if (map != null && map.containsKey("dimension")) {
			String unit = map.get("dimension").toString(); // "dp" or "sp"
			if (!value.endsWith(unit)) {
				return value.replaceAll("\\.0$", "") + unit; // remove ".0" if present
			}
		}
		
		return value.replaceAll("\\.0$", ""); // clean numbers like 15.0 -> 15
	}
	
	private String generateCode(View view) {
		StringBuilder sb = new StringBuilder();
		String tagName = view.getClass().getSimpleName();
		
		// Replace PlaceholderWebView with WebView
		if ("PlaceholderWebView".equals(tagName)) {
			tagName = "WebView";
		}
		
		sb.append("<").append(tagName);
		
		AttributeSet attributeSet = attributesValueMap.get(view);
		if (attributeSet != null) {
			for (Attribute attr : attributeSet.getAttributes()) {
				if (!"xmlns:android".equals(attr.getName())) {
					HashMap<String, Object> attrMap = findAttributeMap(attr.getName(), view);
					String value = formatAttributeValue(attr, attrMap);
					
					sb.append("\n    ").append(attr.getName())
					.append("=\"").append(value).append("\"");
				}
			}
		} else {
			// Default attributes
			sb.append("\n    android:layout_width=\"wrap_content\"");
			sb.append("\n    android:layout_height=\"wrap_content\"");
		}
		
		if (view instanceof ViewGroup) {
			sb.append(">\n");
			ViewGroup group = (ViewGroup) view;
			for (int i = 0; i < group.getChildCount(); i++) {
				String childXml = generateCode(group.getChildAt(i));
				sb.append(indent(childXml, 4)).append("\n");
			}
			sb.append("</").append(tagName).append(">");
		} else {
			sb.append(" />");
		}
		
		return sb.toString();
	}
	
	
	// Helper method for indenting child views
	private String indent(String xml, int spaces) {
		String pad = " ".repeat(spaces);
		return xml.replaceAll("(?m)^", pad);
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
					hideProperties();
					log("drag started: " + destinationView.toString());
					if (draggedView != null) {
						ViewGroupUtils.removeView(draggedView);
						deleteImg.setVisibility(View.VISIBLE);
						deleteImg.bringToFront();
						deleteImg.invalidate();
					} else {
						deleteImg.setVisibility(View.GONE);
					}
					log("start ended");
					return true;
					case DragEvent.ACTION_DRAG_LOCATION:
					case DragEvent.ACTION_DRAG_ENTERED:
					log("drag entered location: " + destinationView.toString());
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
							String widgetId = idManager.getId(draggedView);
							
							// Remove from ViewBean and update parent
							removeViewBean(widgetId, parent);
							
							parent.removeView(draggedView);
							idManager.remove(draggedView);
							attributesValueMap.remove(draggedView);
							EditorAction deleteAction = new EditorAction(ACTION_REMOVE_VIEW, draggedView, parent, index,
							null, null, getCurrentActivityName());
							addAction(deleteAction);
							redoStack.clear();
							SketchwareUtil.showMessage(getContext(), "View deleted");
							vib.vibrate(100);
						}
						return true;
					}
					
					ViewGroup destinationParent = (ViewGroup) destinationView;
					index = destinationParent.indexOfChild(placeHolder);
					
					if (draggedView == null) {
						// Adding a new view
						HashMap<String, Object> viewData = (HashMap) event.getLocalState();
						String classPath = viewData.get("class_path").toString();
						View newView;
						
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
						addView(newView, destinationParent, event);
						
						if (newView instanceof ViewGroup) {
							newView.setOnDragListener(dragListener);
							int dp = (int) SketchwareUtil.getDip(getContext(), 8);
							newView.setPadding(dp, dp, dp, dp);
							com.shapun.layouteditor.utils.AnimationUtils.animate((ViewGroup) newView);
							newView.setBackground(UiUtils.createStrokedBackground(0, 0xFF000000, 1));
						}
						
						String widgetId = idManager.generateNewId(newView);
						idManager.addNewId(newView, widgetId);
						AttributeSet attributeSet = new AttributeSet();
						attributesValueMap.put(newView, attributeSet);
						attributeSet.add(new Attribute("android:id", "@+id/" + widgetId));
						
						ViewGroup.LayoutParams params = newView.getLayoutParams();
						if (newView instanceof ViewGroup) {
							params.width = ViewGroup.LayoutParams.MATCH_PARENT;
							params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
							attributeSet.add(new Attribute("android:layout_width", "match_parent"));
							attributeSet.add(new Attribute("android:layout_height", "wrap_content"));
						} else {
							params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
							params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
							attributeSet.add(new Attribute("android:layout_width", "wrap_content"));
							attributeSet.add(new Attribute("android:layout_height", "wrap_content"));
						}
						addInitialAttributes(newView, viewData);
						
						String activityName = getCurrentActivityName();
						String widgetType = getWidgetTypeName(newView);
						
						saveWidgetInfo(activityName, widgetType, widgetId);
						WidgetStorageManager storageManager = new WidgetStorageManager(getContext(), SC_ID, activityName);
						storageManager.saveWidgetMetadata(newView, widgetId);
						
						designActivity.generateJavaCode();
						designActivity.generateXmlLayout();
						
						EditorAction addAction = new EditorAction(ACTION_ADD_VIEW, newView, destinationParent, index,
						null, null, activityName);
						addAction(addAction);
						
						// Notify listener
						if (onWidgetAddListener != null) {
							onWidgetAddListener.onWidgetAdded(newView, destinationParent);
						}
						
						//	saveAndGenerateCode(newView);
					} else {
						// Moving an existing view
						ViewGroupUtils.removeView(placeHolder);
						addView(draggedView, destinationParent, event);
						
						String widgetId = idManager.getId(draggedView);
						updateViewBeanParent(widgetId, destinationParent);
						
						EditorAction moveAction = new EditorAction(ACTION_ADD_VIEW, draggedView, destinationParent, index,
						null, null, getCurrentActivityName());
						addAction(moveAction);
					}
					
					// Save the entire layout to temp storage
					ViewGroup rootView = (ViewGroup) editorLayout.getChildAt(0);
					if (rootView != null) {
						String xmlCode = generateCode(rootView);
						//	tempLayoutManager.saveTempLayout(activityName);
					}
					return true;
					case DragEvent.ACTION_DRAG_ENDED:
					log("drag ended: " + destinationView.toString());
					ViewGroupUtils.removeView(placeHolder);
					deleteImg.setVisibility(View.GONE);
					if (event.getResult()) {
						vib.vibrate(100);
					} else if (draggedView != null) {
						idManager.remove(draggedView);
						attributesValueMap.remove(draggedView);
					}
					return true;
					default:
					break;
				}
			} catch (Exception e) {
				Log.e("ViewEditor", "Drag event error: " + e.getMessage(), e);
				SketchwareUtil.showMessage(getContext(), "Drag error: " + e.getMessage());
			}
			return true;
		}
		
		// Helper method to create a ViewBean
		private ProjectActivityBean.ViewBean createViewBean(View view, String widgetId, String widgetType, ViewGroup parent, String activityName) {
			ProjectActivityBean.ViewBean bean = new ProjectActivityBean.ViewBean(widgetType, widgetId);
			bean.setScId(SC_ID);
			bean.setActivityName(activityName);
			
			// Set dimensions and margins
			ViewGroup.LayoutParams params = view.getLayoutParams();
			if (params != null) {
				bean.setWidth(params.width);
				bean.setHeight(params.height);
				if (params instanceof ViewGroup.MarginLayoutParams) {
					ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) params;
					bean.setMarginLeft(marginParams.leftMargin);
					bean.setMarginTop(marginParams.topMargin);
					bean.setMarginRight(marginParams.rightMargin);
					bean.setMarginBottom(marginParams.bottomMargin);
				}
			}
			
			// Set parent-child relationship
			if (parent != null) {
				String parentId = idManager.getId(parent);
				if (parentId != null) {
					bean.setParentId(parentId);
					// Update parent's children list
					ProjectActivityBean parentActivityBean = ProjectActivityBean.loadFromStorage(SC_ID, activityName);
					if (parentActivityBean != null) {
						for (ProjectActivityBean.ViewBean parentBean : parentActivityBean.getWidgets()) {
							if (parentBean.getWidgetId().equals(parentId)) {
								if (parentBean.getChildren() == null) {
									parentBean.setChildren(new ArrayList<>());
								}
								parentBean.getChildren().add(bean);
								parentActivityBean.saveToStorage();
								break;
							}
						}
					}
				}
			}
			
			// Set type-specific properties
			if (view instanceof TextView) {
				TextView tv = (TextView) view;
				bean.setText(tv.getText().toString());
				bean.setTextSize(tv.getTextSize() / getResources().getDisplayMetrics().density);
				bean.setTextColor(tv.getCurrentTextColor());
				bean.setGravity(tv.getGravity());
				bean.setTextAlignment(tv.getTextAlignment());
				bean.setMaxLines(tv.getMaxLines());
				bean.setHint(tv.getHint() != null ? tv.getHint().toString() : "");
				bean.setInputType(tv.getInputType());
				Typeface typeface = tv.getTypeface();
				if (typeface != null) {
					StringBuilder textStyle = new StringBuilder();
					if (typeface.isBold()) textStyle.append("bold|");
					if (typeface.isItalic()) textStyle.append("italic|");
					if (textStyle.length() > 0) {
						bean.setTextStyle(textStyle.substring(0, textStyle.length() - 1));
					}
					bean.setFontFamily(typeface.toString());
				}
				bean.setLineSpacingMultiplier(tv.getLineSpacingMultiplier());
				bean.setLineSpacingExtra(tv.getLineSpacingExtra() / getResources().getDisplayMetrics().density);
			} else if (view instanceof Button) {
				Button btn = (Button) view;
				bean.setText(btn.getText().toString());
				bean.setTextSize(btn.getTextSize() / getResources().getDisplayMetrics().density);
				bean.setTextColor(btn.getCurrentTextColor());
				bean.setGravity(btn.getGravity());
				Typeface typeface = btn.getTypeface();
				if (typeface != null) {
					StringBuilder textStyle = new StringBuilder();
					if (typeface.isBold()) textStyle.append("bold|");
					if (typeface.isItalic()) textStyle.append("italic|");
					if (textStyle.length() > 0) {
						bean.setTextStyle(textStyle.substring(0, textStyle.length() - 1));
					}
				}
			} else if (view instanceof ImageView) {
				ImageView img = (ImageView) view;
				bean.setScaleType(img.getScaleType() != null ? img.getScaleType().toString() : "FIT_CENTER");
				if (img.getDrawable() instanceof BitmapDrawable) {
					bean.setImagePath(""); // Implement actual image path logic based on your project structure
				}
			} else if (view instanceof SeekBar) {
				SeekBar seekBar = (SeekBar) view;
				bean.setProgress(seekBar.getProgress());
				bean.setMaxProgress(seekBar.getMax());
				bean.setProgressType("HORIZONTAL");
			} else if (view instanceof ProgressBar) {
				ProgressBar progressBar = (ProgressBar) view;
				bean.setProgress(progressBar.getProgress());
				bean.setMaxProgress(progressBar.getMax());
				bean.setProgressType("CIRCULAR");
			} else if (view instanceof CheckBox || view instanceof Switch) {
				CompoundButton cb = (CompoundButton) view;
				bean.setChecked(cb.isChecked());
				bean.setText(cb.getText().toString());
				bean.setTextSize(cb.getTextSize() / getResources().getDisplayMetrics().density);
				bean.setTextColor(cb.getCurrentTextColor());
			} else if (view instanceof EditText) {
				EditText et = (EditText) view;
				bean.setText(et.getText().toString());
				bean.setTextSize(et.getTextSize() / getResources().getDisplayMetrics().density);
				bean.setTextColor(et.getCurrentTextColor());
				bean.setHint(et.getHint() != null ? et.getHint().toString() : "");
				bean.setInputType(et.getInputType());
				bean.setGravity(et.getGravity());
				bean.setMaxLines(et.getMaxLines());
			}
			
			// Set common properties
			bean.setBackgroundColor(Color.TRANSPARENT);
			bean.setVisibility(view.getVisibility());
			bean.setAlpha(view.getAlpha());
			bean.setRotation(view.getRotation());
			bean.setScaleX(view.getScaleX());
			bean.setScaleY(view.getScaleY());
			bean.setPaddingLeft(view.getPaddingLeft());
			bean.setPaddingTop(view.getPaddingTop());
			bean.setPaddingRight(view.getPaddingRight());
			bean.setPaddingBottom(view.getPaddingBottom());
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				bean.setElevation(view.getElevation());
			}
			
			return bean;
		}
		
		// Helper method to remove a ViewBean
		private void removeViewBean(String widgetId, ViewGroup parent) {
			ProjectActivityBean activityBean = ProjectActivityBean.loadFromStorage(SC_ID, getCurrentActivityName());
			if (activityBean != null) {
				// Remove from widgets list
				activityBean.getWidgets().removeIf(bean -> bean.getWidgetId().equals(widgetId));
				
				// Remove from parent's children list
				if (parent != null) {
					String parentId = idManager.getId(parent);
					if (parentId != null) {
						for (ProjectActivityBean.ViewBean parentBean : activityBean.getWidgets()) {
							if (parentBean.getWidgetId().equals(parentId)) {
								parentBean.getChildren().removeIf(child -> child.getWidgetId().equals(widgetId));
								break;
							}
						}
					}
				}
				
				activityBean.saveToStorage();
			}
		}
		
		// Helper method to update ViewBean parent
		private void updateViewBeanParent(String widgetId, ViewGroup newParent) {
			ProjectActivityBean activityBean = ProjectActivityBean.loadFromStorage(SC_ID, getCurrentActivityName());
			if (activityBean != null) {
				// Find the ViewBean
				for (ProjectActivityBean.ViewBean bean : activityBean.getWidgets()) {
					if (bean.getWidgetId().equals(widgetId)) {
						// Remove from old parent's children list
						String oldParentId = bean.getParentId();
						if (oldParentId != null) {
							for (ProjectActivityBean.ViewBean parentBean : activityBean.getWidgets()) {
								if (parentBean.getWidgetId().equals(oldParentId)) {
									parentBean.getChildren().removeIf(child -> child.getWidgetId().equals(widgetId));
									break;
								}
							}
						}
						
						// Update parentId and add to new parent's children
						String newParentId = idManager.getId(newParent);
						bean.setParentId(newParentId != null ? newParentId : "");
						if (newParentId != null) {
							for (ProjectActivityBean.ViewBean parentBean : activityBean.getWidgets()) {
								if (parentBean.getWidgetId().equals(newParentId)) {
									if (parentBean.getChildren() == null) {
										parentBean.setChildren(new ArrayList<>());
									}
									parentBean.getChildren().add(bean);
									break;
								}
							}
						}
						
						break;
					}
				}
				activityBean.saveToStorage();
			}
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
				// After drop completes, save temp layout globally
				ViewGroup rootView = (ViewGroup) editorLayout.getChildAt(0);
				String xmlCode = generateCode(rootView);
				tempLayoutManager.saveTempLayout(xmlCode);
				
				
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
			String currentActivity = getCurrentActivityName();
			Attribute oldAttr = (attrSet != null) ? attrSet.getAttribute(attribute.getName()) : null;
			EditorAction action = new EditorAction(ACTION_UPDATE_ATTR, view, null, -1,
			oldAttr, attribute, currentActivity);
			addAction(action);
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
		
		// After drop completes, save temp layout globally
		ViewGroup rootView = (ViewGroup) editorLayout.getChildAt(0);
		String xmlCode = generateCode(rootView);
		tempLayoutManager.saveTempLayout(xmlCode);
		
	}
	
	public void applyAttribute(View view, Attribute attribute, HashMap<String, Object> map) {
		// Inside applyAttribute():
		if (!isUndoRedoInProgress) {
			/*Attribute oldAttr = (attrSet != null) ? attrSet.getAttribute(attribute.getName()) : null;
EditorAction action = new EditorAction(ACTION_UPDATE_ATTR, view, null, -1, oldAttr, attribute);
undoStack.push(action);
redoStack.clear();*/			
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
				// Handle @string/ references for android:text
				if ("android:text".equals(attribute_name) && value.startsWith("@string/")) {
					String resourceName = value.substring("@string/".length());
					String scId = DesignActivity.getScId(); // Get sc_id from DesignActivity
					String resolvedValue = getStringResourceValue(scId, resourceName);
					argumentValue = (resolvedValue != null) ? resolvedValue : value; // Fallback to original value if not found
				} else {
					argumentValue = value;
				}
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
			// After drop completes, save temp layout globally
			ViewGroup rootView = (ViewGroup) editorLayout.getChildAt(0);
			String xmlCode = generateCode(rootView);
			tempLayoutManager.saveTempLayout(xmlCode);
			String activityName = getCurrentActivityName();
			/*  designActivity.complex.setXmlCode(designActivity.currentActivityBean.getLayoutName(), getXMLCode());
designActivity.generateJavaCode(activityName, designActivity.currentActivityBean.getLayoutName());*/			
			
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
	
	// Updated saveLayout method
	public void saveLayout(String layoutName) {
		if (SAVE_PATH == null || SAVE_PATH.isEmpty() || layoutName == null || layoutName.trim().isEmpty())
		return;
		
		try {
			String xmlCode = cleanXML(getXMLCode());
			
			if (!validateXML(xmlCode)) return;
			
			if (editorLayout.getChildCount() == 0) return;
			
			View rootView = editorLayout.getChildAt(0);
			String rawXml = generateCode(rootView);
			
			String formattedXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
			rawXml.substring(0, rawXml.indexOf("\n")) + "\n" +
			"xmlns:android=\"http://schemas.android.com/apk/res/android\"" +
			rawXml.substring(rawXml.indexOf("\n"));
			
			HashMap<String, Object> layoutData = new HashMap<>();
			layoutData.put("name", layoutName.trim());
			layoutData.put("xml", formattedXml);
			
			String layoutFilePath = SAVE_PATH + "/root_layout.json";
			File file = new File(layoutFilePath);
			if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
				SketchwareUtil.showMessage(getContext(), "Directory creation failed: " + file.getParent());
				return;
			}
			
			ArrayList<HashMap<String, Object>> layoutList = FileUtil.isExistFile(layoutFilePath)
			? new Gson().fromJson(FileUtil.readFile(layoutFilePath),
			new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType())
			: new ArrayList<>();
			
			layoutList.removeIf(layout -> layoutName.equalsIgnoreCase((String) layout.get("name")));
			layoutList.add(layoutData);
			
			FileUtil.writeFile(layoutFilePath, new Gson().toJson(layoutList));
			
			// SketchwareUtil.showMessage(getContext(), "Layout saved: " + layoutName);
		} catch (Exception e) {
			SketchwareUtil.showMessage(getContext(), "Error: " + e.getMessage());
			Log.e("ViewEditor", "saveLayout() failed", e);
		}
	}
	
	
	public void loadLayout(String layoutName) {
		editorLayout.removeAllViews();
		if (layoutName == null || layoutName.trim().isEmpty()) return;
		
		try {
			
			String layoutFilePath = SAVE_PATH + "/root_layout.json";
			if (!FileUtil.isExistFile(layoutFilePath)) return;
			
			ArrayList<HashMap<String, Object>> layoutList = new Gson().fromJson(FileUtil.readFile(layoutFilePath),
			new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType());
			
			HashMap<String, Object> targetLayout = layoutList.stream()
			.filter(layout -> layoutName.equals(layout.get("name")))
			.findFirst().orElse(null);
			
			if (targetLayout == null) return;
			
			// Backup previous state
			oldIdManager = idManager;
			oldAttributesValueMap = new HashMap<>(attributesValueMap);
			
			idManager = new IdManager();
			attributesValueMap.clear();
			
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(new StringReader((String) targetLayout.get("xml")));
			
			ArrayList<View> viewStack = new ArrayList<>();
			viewStack.add(editorLayout);
			
			for (int eventType = parser.getEventType(); eventType != XmlPullParser.END_DOCUMENT; eventType = parser.next()) {
				if (eventType == XmlPullParser.START_TAG) {
					String tag = parser.getName();
					View view = createPlaceholderOrStandardView(tag);
					
					if (view != null) {
						view.setOnClickListener(v -> showAttributesDialog(v));
						_rearrangeListener(view);
						if (view instanceof ViewGroup) {
							view.setOnDragListener(dragListener);
							view.setMinimumHeight((int) SketchwareUtil.getDip(getContext(), 30));
						}
						
						AttributeSet attrSet = new AttributeSet();
						attributesValueMap.put(view, attrSet);
						for (int i = 0; i < parser.getAttributeCount(); i++) {
							String attrName = parser.getAttributeName(i);
							String attrValue = parser.getAttributeValue(i);
							if ("android:id".equals(attrName)) {
								idManager.addNewId(view, AttributeUtils.getName(attrValue));
							}
							attrSet.add(new Attribute(attrName, attrValue));
						}
						
						viewStack.add(view);
					}
					
				} else if (eventType == XmlPullParser.END_TAG && viewStack.size() > 1) {
					View child = viewStack.remove(viewStack.size() - 1);
					ViewGroup parent = (ViewGroup) viewStack.get(viewStack.size() - 1);
					parent.addView(child);
				}
			}
			
			for (View v : attributesValueMap.keySet()) {
				for (Attribute a : attributesValueMap.get(v).getAttributes()) {
					applyAttribute(v, a);
				}
			}
			
			// SketchwareUtil.showMessage(getContext(), "Layout loaded: " + layoutName);
		} catch (Exception e) {
			restorePreviousStateOnFailure();
			SketchwareUtil.showMessage(getContext(), "Load error: " + e.getMessage());
			Log.e("ViewEditor", "loadLayout() failed", e);
		}
	}
	
	// Helper: Recursive save view to JsonObject (includes all attributes in properties)
	private JsonObject saveViewRecursive(View view) {
		JsonObject jsonObject = new JsonObject();
		
		// Type with orientation if LinearLayout
		String type = view.getClass().getSimpleName();
		if (view instanceof LinearLayout) {
			type += (((LinearLayout) view).getOrientation() == LinearLayout.HORIZONTAL) ? "(H)" : "(V)";
		}
		jsonObject.addProperty("type", type);
		
		// ID
		jsonObject.addProperty("id", idManager.getId(view));
		
		// All attributes in properties (as per your request for all attributes)
		JsonObject properties = new JsonObject();
		AttributeSet set = attributesValueMap.get(view);
		if (set != null) {
			for (Attribute attr : set.getAttributes()) {
				properties.addProperty(attr.getName(), attr.getValue());
			}
		}
		jsonObject.add("properties", properties);
		
		// Common layout params as top-level (matching example structure)
		ViewGroup.LayoutParams params = view.getLayoutParams();
		if (params != null) {
			jsonObject.addProperty("width", params.width);
			jsonObject.addProperty("height", params.height);
		}
		if (params instanceof ViewGroup.MarginLayoutParams) {
			ViewGroup.MarginLayoutParams mparams = (ViewGroup.MarginLayoutParams) params;
			jsonObject.addProperty("leftMargin", mparams.leftMargin);
			jsonObject.addProperty("topMargin", mparams.topMargin);
			jsonObject.addProperty("rightMargin", mparams.rightMargin);
			jsonObject.addProperty("bottomMargin", mparams.bottomMargin);
		}
		
		// Additional common attributes (paddings, gravity, etc. for more completeness)
		if (params instanceof LinearLayout.LayoutParams) {
			LinearLayout.LayoutParams lparams = (LinearLayout.LayoutParams) params;
			jsonObject.addProperty("layout_weight", lparams.weight);
			jsonObject.addProperty("layout_gravity", lparams.gravity);
		}
		jsonObject.addProperty("paddingLeft", view.getPaddingLeft());
		jsonObject.addProperty("paddingTop", view.getPaddingTop());
		jsonObject.addProperty("paddingRight", view.getPaddingRight());
		jsonObject.addProperty("paddingBottom", view.getPaddingBottom());
		jsonObject.addProperty("backgroundColor", (view.getBackground() instanceof ColorDrawable ? ((ColorDrawable) view.getBackground()).getColor() : 0));
		jsonObject.addProperty("visibility", view.getVisibility());
		jsonObject.addProperty("alpha", view.getAlpha());
		jsonObject.addProperty("rotation", view.getRotation());
		jsonObject.addProperty("scaleX", view.getScaleX());
		jsonObject.addProperty("scaleY", view.getScaleY());
		jsonObject.addProperty("elevation", view.getElevation());
		
		// Type-specific additional attributes
		if (view instanceof TextView) {
			TextView tv = (TextView) view;
			jsonObject.addProperty("textSize", tv.getTextSize());
			jsonObject.addProperty("textColor", tv.getCurrentTextColor());
			jsonObject.addProperty("gravity", tv.getGravity());
			jsonObject.addProperty("maxLines", tv.getMaxLines());
		} else if (view instanceof ImageView) {
			ImageView iv = (ImageView) view;
			jsonObject.addProperty("scaleType", iv.getScaleType().ordinal());
		} else if (view instanceof ProgressBar) {
			ProgressBar pb = (ProgressBar) view;
			jsonObject.addProperty("progress", pb.getProgress());
			jsonObject.addProperty("maxProgress", pb.getMax());
		}
		
		// Children for hierarchy
		if (view instanceof ViewGroup) {
			JsonArray children = new JsonArray();
			ViewGroup group = (ViewGroup) view;
			for (int i = 0; i < group.getChildCount(); i++) {
				children.add(saveViewRecursive(group.getChildAt(i)));
			}
			jsonObject.add("children", children);
		}
		
		return jsonObject;
	}
	
	// Helper: Recursive load view from JsonObject
	private View loadViewRecursive(Context context, JsonObject jsonObject) {
		String typeStr = jsonObject.get("type").getAsString();
		String type = typeStr.replaceAll("\\(H\\)|\\(V\\)", "");
		View view = null;
		
		try {
			view = ReflectionUtils.createView(context, "android.widget." + type);
		} catch (Exception e) {
			view = new View(context);
		}
		
		if (view == null) return null;
		
		// Set orientation for LinearLayout
		if (view instanceof LinearLayout) {
			if (typeStr.endsWith("(H)")) {
				((LinearLayout) view).setOrientation(LinearLayout.HORIZONTAL);
			} else if (typeStr.endsWith("(V)")) {
				((LinearLayout) view).setOrientation(LinearLayout.VERTICAL);
			}
		}
		
		// Set ID
		if (jsonObject.has("id")) {
			idManager.addNewId(view, jsonObject.get("id").getAsString());
		}
		
		// Apply all attributes from properties
		JsonObject properties = jsonObject.getAsJsonObject("properties");
		AttributeSet set = new AttributeSet();
		if (properties != null) {
			for (Map.Entry<String, JsonElement> entry : properties.entrySet()) {
				Attribute attr = new Attribute(entry.getKey(), entry.getValue().getAsString());
				set.add(attr);
				applyAttribute(view, attr);
			}
		}
		attributesValueMap.put(view, set);
		
		// Set common layout params
		int width = jsonObject.has("width") ? jsonObject.get("width").getAsInt() : ViewGroup.LayoutParams.WRAP_CONTENT;
		int height = jsonObject.has("height") ? jsonObject.get("height").getAsInt() : ViewGroup.LayoutParams.WRAP_CONTENT;
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width, height);
		
		if (jsonObject.has("leftMargin") || jsonObject.has("topMargin") || jsonObject.has("rightMargin") || jsonObject.has("bottomMargin")) {
			ViewGroup.MarginLayoutParams mparams = new ViewGroup.MarginLayoutParams(params);
			if (jsonObject.has("leftMargin")) mparams.leftMargin = jsonObject.get("leftMargin").getAsInt();
			if (jsonObject.has("topMargin")) mparams.topMargin = jsonObject.get("topMargin").getAsInt();
			if (jsonObject.has("rightMargin")) mparams.rightMargin = jsonObject.get("rightMargin").getAsInt();
			if (jsonObject.has("bottomMargin")) mparams.bottomMargin = jsonObject.get("bottomMargin").getAsInt();
			params = mparams;
		}
		
		if (jsonObject.has("layout_weight") || jsonObject.has("layout_gravity")) {
			LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(params);
			if (jsonObject.has("layout_weight")) lparams.weight = jsonObject.get("layout_weight").getAsFloat();
			if (jsonObject.has("layout_gravity")) lparams.gravity = jsonObject.get("layout_gravity").getAsInt();
			params = lparams;
		}
		view.setLayoutParams(params);
		
		// --- FIXED PADDING SECTION ---
		int paddingLeft = view.getPaddingLeft();
		int paddingTop = view.getPaddingTop();
		int paddingRight = view.getPaddingRight();
		int paddingBottom = view.getPaddingBottom();
		
		if (jsonObject.has("paddingLeft")) {
			paddingLeft = jsonObject.get("paddingLeft").getAsInt();
		}
		if (jsonObject.has("paddingTop")) {
			paddingTop = jsonObject.get("paddingTop").getAsInt();
		}
		if (jsonObject.has("paddingRight")) {
			paddingRight = jsonObject.get("paddingRight").getAsInt();
		}
		if (jsonObject.has("paddingBottom")) {
			paddingBottom = jsonObject.get("paddingBottom").getAsInt();
		}
		
		view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
		
		// Set additional common attributes
		if (jsonObject.has("backgroundColor")) view.setBackgroundColor(jsonObject.get("backgroundColor").getAsInt());
		if (jsonObject.has("visibility")) view.setVisibility(jsonObject.get("visibility").getAsInt());
		if (jsonObject.has("alpha")) view.setAlpha(jsonObject.get("alpha").getAsFloat());
		if (jsonObject.has("rotation")) view.setRotation(jsonObject.get("rotation").getAsFloat());
		if (jsonObject.has("scaleX")) view.setScaleX(jsonObject.get("scaleX").getAsFloat());
		if (jsonObject.has("scaleY")) view.setScaleY(jsonObject.get("scaleY").getAsFloat());
		if (jsonObject.has("elevation")) view.setElevation(jsonObject.get("elevation").getAsFloat());
		
		// Type-specific
		if (view instanceof TextView && jsonObject.has("textSize")) {
			((TextView) view).setTextSize(jsonObject.get("textSize").getAsFloat());
		}
		if (view instanceof ImageView && jsonObject.has("scaleType")) {
			((ImageView) view).setScaleType(ImageView.ScaleType.values()[jsonObject.get("scaleType").getAsInt()]);
		}
		if (view instanceof ProgressBar && jsonObject.has("progress")) {
			((ProgressBar) view).setProgress(jsonObject.get("progress").getAsInt());
		}
		
		// Load children
		if (jsonObject.has("children") && view instanceof ViewGroup) {
			JsonArray children = jsonObject.getAsJsonArray("children");
			ViewGroup group = (ViewGroup) view;
			for (JsonElement e : children) {
				View child = loadViewRecursive(context, e.getAsJsonObject());
				if (child != null) group.addView(child);
			}
		}
		
		// Setup listeners
		view.setOnClickListener(v -> showAttributesDialog(v));
		_rearrangeListener(view);
		if (view instanceof ViewGroup) {
			view.setOnDragListener(dragListener);
			view.setMinimumHeight((int) SketchwareUtil.getDip(getContext(), 30));
		}
		
		return view;
	}
	
	public String getXMLCode() {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		
		// Default root LinearLayout with white background
		sb.append("<LinearLayout\n")
		.append("    xmlns:android=\"http://schemas.android.com/apk/res/android\"\n")
		.append("    android:layout_width=\"match_parent\"\n")
		.append("    android:layout_height=\"match_parent\"\n")
		.append("    android:orientation=\"vertical\"\n")
		.append("    android:background=\"#FFFFFF\">\n");
		
		// Iterate all direct children of editorLayout
		for (int i = 0; i < editorLayout.getChildCount(); i++) {
			View child = editorLayout.getChildAt(i);
			String childXml = generateCode(child);
			sb.append(indent(childXml, 4)).append("\n");
		}
		
		sb.append("</LinearLayout>");
		return sb.toString();
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
		
		private Context context;
		private ArrayList<HashMap<String, Object>> data;
		
		public Listview_widgetsAdapter(Context context, ArrayList<HashMap<String, Object>> data) {
			this.context = context;
			this.data = data;
		}
		
		@NonNull
		@Override
		public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			View v = LayoutInflater.from(context).inflate(R.layout.list_widget, parent, false);
			return new ViewHolder(v);
		}
		
		@Override
		public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
			HashMap<String, Object> item = data.get(position);
			
			int n = holder.lin_main.getId();
			int n2 = TheBlockLogicsUtil.getDip(context, 18);
			int n3 = TheBlockLogicsUtil.getDip(context, 18);
			int n4 = TheBlockLogicsUtil.getDip(context, 0);
			int n5 = TheBlockLogicsUtil.getDip(context, 2);
			
			GradientDrawable gradientDrawable = TheBlockLogicsUtil.getGradient(
			context, 1, Color.parseColor("#DDDDDD"), -1
			);
			gradientDrawable.setCornerRadius((float) n4);
			
			holder.lin_main.setElevation(2f);
			holder.lin_main.setGravity(Gravity.CENTER_VERTICAL);
			holder.lin_main.setPadding(n5, n5, n5, n5);
			holder.lin_main.setBackground(gradientDrawable);
			
			// 🟢 Apply same padding to children
			holder.ivIcon.setPadding(n5, n5, n5, n5);
			//holder.tvName.setPadding(n5, n5, n5, n5);
			
			// 🟢 Icon handling
			if (item.containsKey("icon")) {
				String iconName = item.get("icon").toString();
				int resId = context.getResources()
				.getIdentifier(iconName, "drawable", context.getPackageName());
				if (resId != 0) {
					holder.ivIcon.setImageResource(resId);
					holder.ivIcon.setVisibility(View.VISIBLE);
				} else {
					holder.ivIcon.setVisibility(View.GONE);
				}
			} else {
				holder.ivIcon.setVisibility(View.GONE);
			}
			
			// 🟢 Text handling
			if (item.containsKey("name")) {
				holder.tvName.setText(item.get("name").toString());
			} else {
				holder.tvName.setText("LinearLayout");
			}
			holder.tvName.setTextColor(Color.parseColor("#555555"));
			holder.tvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
			holder.tvName.setSingleLine();
			
			
			holder.lin_main.setOnLongClickListener(v -> {
				DragAndDropUtils.startDragAndDrop(v, null, new View.DragShadowBuilder(holder.lin_main), data.get(position), 1);
				return true;
			});
		}
		@Override
		public int getItemCount() {
			return data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			LinearLayout lin_main;
			TextView tvName;
			ImageView ivIcon;
			
			public ViewHolder(@NonNull View itemView) {
				super(itemView);
				lin_main = itemView.findViewById(R.id.lin_main);
				tvName = itemView.findViewById(R.id.title);
				ivIcon = itemView.findViewById(R.id.icon);
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
		String activityName = getCurrentActivityName();
		if (!undoStacks.containsKey(activityName) || undoStacks.get(activityName).isEmpty())
		return false;
		
		EditorAction action = undoStacks.get(activityName).pop();
		redoStacks.get(activityName).push(action);
		
		isUndoRedoInProgress = true;
		try {
			switch (action.actionType) {
				case ACTION_ADD_VIEW:
				if (action.parent != null && action.view != null) {
					action.parent.removeView(action.view);
					idManager.remove(action.view);
					attributesValueMap.remove(action.view);
				}
				break;
				
				case ACTION_REMOVE_VIEW:
				if (action.parent != null && action.view != null) {
					action.parent.addView(action.view, action.index);
					idManager.addNewId(action.view, action.viewId);
					if (action.oldAttr != null) {
						applyAttribute(action.view, action.oldAttr);
					}
				}
				break;
				
				case ACTION_UPDATE_ATTR:
				if (action.view != null && action.oldAttr != null) {
					applyAttribute(action.view, action.oldAttr);
				}
				break;
			}
		} catch (Exception e) {
			SketchwareUtil.showMessage(getContext(), "Undo error: " + e.toString());
		} finally {
			isUndoRedoInProgress = false;
		}
		return true;
	}
	
	public boolean redo() {
		String activityName = getCurrentActivityName();
		if (!redoStacks.containsKey(activityName) || redoStacks.get(activityName).isEmpty())
		return false;
		
		EditorAction action = redoStacks.get(activityName).pop();
		undoStacks.get(activityName).push(action);
		
		isUndoRedoInProgress = true;
		try {
			switch (action.actionType) {
				case ACTION_ADD_VIEW:
				if (action.parent != null && action.view != null) {
					action.parent.addView(action.view, action.index);
					idManager.addNewId(action.view, action.viewId);
				}
				break;
				
				case ACTION_REMOVE_VIEW:
				if (action.parent != null && action.view != null) {
					action.parent.removeView(action.view);
					idManager.remove(action.view);
					attributesValueMap.remove(action.view);
				}
				break;
				
				case ACTION_UPDATE_ATTR:
				if (action.view != null && action.newAttr != null) {
					applyAttribute(action.view, action.newAttr);
				}
				break;
			}
		} catch (Exception e) {
			SketchwareUtil.showMessage(getContext(), "Redo error: " + e.toString());
		} finally {
			isUndoRedoInProgress = false;
		}
		return true;
	}
	
	public static List<String> getIdsByType(String type) {
		List<String> ids = new ArrayList<>();
		for (View view : idManager.getViews()) {
			String className = view.getClass().getSimpleName();
			if (type.equalsIgnoreCase("all") || className.equalsIgnoreCase(type)) {
				ids.add(idManager.getId(view));
			}
		}
		return ids;
	}
	
	private String cleanXML(String xmlCode) {
		// Remove duplicate xmlns declarations
		xmlCode = xmlCode.replaceAll("(?m)^\\s*xmlns:android=\"http://schemas.android.com/apk/res/android\"\\s*$", "");
		// Ensure single xmlns declaration at root
		if (!xmlCode.contains("xmlns:android")) {
			xmlCode = xmlCode.replaceFirst("<(\\w+)", "<$1 xmlns:android=\"http://schemas.android.com/apk/res/android\"");
		}
		// Remove invalid or empty IDs
		xmlCode = xmlCode.replaceAll("android:id=\"@\\+id/\"", "");
		xmlCode = xmlCode.replaceAll("android:id=\"@\\+id/\\s*\"", "");
		// Normalize whitespace
		xmlCode = xmlCode.replaceAll("\\s+\n", "\n").trim();
		return xmlCode;
	}
	
	private boolean validateXML(String xmlContent) {
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(new StringReader(xmlContent));
			parser.nextTag(); // Ensure it starts with a tag
			return true;
		} catch (XmlPullParserException | IOException e) {
			Log.e("ViewEditor", "XML validation failed: " + e.getMessage());
			return false;
		}
	}
	
	private View createPlaceholderOrStandardView(String tag) {
		switch (tag) {
			case "WebView":
			return new PlaceholderWebView(getContext());
			case "VideoView":
			case "ViewPager":
			return new PlaceholderWidget(getContext(), tag);
			default:
			try {
				View view = ReflectionUtils.createView(getContext(), "android.widget." + tag);
				return (view != null) ? view : ReflectionUtils.createView(getContext(), tag);
			} catch (ClassNotFoundException |
			InstantiationException |
			InvocationTargetException |
			NoSuchMethodException |
			IllegalAccessException e) {
				e.printStackTrace(); // Optional: log the error
				return null; // Or a default placeholder view
			}
		}
	}
	
	private void setupImportedView(View view) {
		view.setOnClickListener(v -> showAttributesDialog(v));
		_rearrangeListener(view);
		
		if (view instanceof ViewGroup) {
			((ViewGroup) view).setOnDragListener(dragListener);
			((ViewGroup) view).setMinimumHeight((int) SketchwareUtil.getDip(getContext(), 30));
		}
		
		view.setMinimumWidth((int) SketchwareUtil.getDip(getContext(), 50));
		view.setMinimumHeight((int) SketchwareUtil.getDip(getContext(), 30));
	}
	
	
	private void restorePreviousStateOnFailure() {
		idManager = oldIdManager;
		attributesValueMap = oldAttributesValueMap;
		editorLayout.removeAllViews();
		for (View v : oldAttributesValueMap.keySet()) {
			if (v.getParent() == null) editorLayout.addView(v);
		}
	}
	
	// Deterministic runtime key generation
	private static SecretKeySpec getKey() {
		byte[] keyBytes = new byte[KEY_PARTS.length];
		for (int i = 0; i < KEY_PARTS.length; i++) {
			keyBytes[i] = (byte) ((KEY_PARTS[i] ^ 0x55) + 3); // XOR + shift obfuscation
		}
		return new SecretKeySpec(keyBytes, "AES");
	}
	
	// Deterministic runtime IV generation
	private static IvParameterSpec getIV() {
		byte[] ivBytes = new byte[IV_PARTS.length];
		for (int i = 0; i < IV_PARTS.length; i++) {
			ivBytes[i] = (byte) ((IV_PARTS[i] ^ 0x55) + 3); // XOR + shift obfuscation
		}
		return new IvParameterSpec(ivBytes);
	}
	
	// Encrypt data
	private String encrypt(String data) {
		try {
			SecretKeySpec keySpec = new SecretKeySpec(AES_KEY.getBytes("UTF-8"), "AES");
			IvParameterSpec ivSpec = new IvParameterSpec(AES_IV.getBytes("UTF-8"));
			Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
			byte[] encrypted = cipher.doFinal(data.getBytes("UTF-8"));
			return Base64.encodeToString(encrypted, Base64.DEFAULT);
		} catch (Exception e) {
			Log.e("ViewEditor", "Encryption failed: " + e.getMessage(), e);
			return null;
		}
	}
	
	// Decrypt data
	private String decrypt(String encryptedData) {
		try {
			SecretKeySpec keySpec = new SecretKeySpec(AES_KEY.getBytes("UTF-8"), "AES");
			IvParameterSpec ivSpec = new IvParameterSpec(AES_IV.getBytes("UTF-8"));
			Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
			byte[] decoded = Base64.decode(encryptedData, Base64.DEFAULT);
			byte[] decrypted = cipher.doFinal(decoded);
			return new String(decrypted, "UTF-8");
		} catch (Exception e) {
			Log.e("ViewEditor", "Decryption failed: " + e.getMessage(), e);
			return null;
		}
	}
	
	
	private String getStringResourceValue(String scId, String resourceName) {
		String filePath = "/storage/emulated/0/.blacklogics/data/" + scId + "/files/resource/values/strings.xml";
		File file = new File(filePath);
		
		if (!file.exists()) {
			Log.e("ViewEditor", "strings.xml not found at: " + filePath);
			return null;
		}
		
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(new FileInputStream(file), "UTF-8");
			
			String currentName = null;
			StringBuilder currentValue = new StringBuilder();
			int eventType = parser.getEventType();
			
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG && "string".equals(parser.getName())) {
					currentName = parser.getAttributeValue(null, "name");
				} else if (eventType == XmlPullParser.TEXT && currentName != null) {
					currentValue.append(parser.getText());
				} else if (eventType == XmlPullParser.END_TAG && "string".equals(parser.getName())) {
					if (currentName != null && currentName.equals(resourceName)) {
						return currentValue.toString();
					}
					currentName = null;
					currentValue.setLength(0);
				}
				eventType = parser.next();
			}
		} catch (XmlPullParserException | IOException e) {
			Log.e("ViewEditor", "Error parsing strings.xml: " + e.getMessage(), e);
		}
		
		return null;
	}
	
	private String getCurrentActivityName() {
		return (designActivity.activityBean != null)
		? designActivity.activityBean.getActivityName()
		: "MainActivity";
	}
	
	private void addAction(EditorAction action) {
		String activityName = action.activityName;
		if (!undoStacks.containsKey(activityName)) {
			undoStacks.put(activityName, new Stack<>());
			redoStacks.put(activityName, new Stack<>());
		}
		undoStacks.get(activityName).push(action);
		redoStacks.get(activityName).clear(); // Clear redo after new action
	}
	
	public static void saveWidgetInfo(String activityName, String widgetType, String widgetId) {
		try {
			String projectPath = FileUtil.getExternalStorageDir() + "/.blacklogics/data/" + SC_ID;
			String widgetPath = projectPath + "/widget_info/project_widgets.json";
			FileUtil.makeDir(projectPath + "/widget_info/");
			
			// Read existing widget map
			Map<String, Map<String, List<String>>> widgetMap = new HashMap<>();
			if (FileUtil.isExistFile(widgetPath)) {
				String encodedJson = FileUtil.readFile(widgetPath);
				String decodedJson = new String(Base64.decode(encodedJson, Base64.DEFAULT));
				Type mapType = new TypeToken<Map<String, Map<String, List<String>>>>() {}.getType();
				widgetMap = new Gson().fromJson(decodedJson, mapType);
			}
			
			// Get activity widget list
			Map<String, List<String>> activityWidgets = widgetMap.getOrDefault(activityName, new HashMap<>());
			List<String> widgetIds = activityWidgets.getOrDefault(widgetType, new ArrayList<>());
			
			// Avoid duplicate IDs
			if (!widgetIds.contains(widgetId)) {
				widgetIds.add(widgetId);
			}
			
			activityWidgets.put(widgetType, widgetIds);
			widgetMap.put(activityName, activityWidgets);
			
			// Save updated map
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String prettyJson = gson.toJson(widgetMap);
			String encodedJson = Base64.encodeToString(prettyJson.getBytes(), Base64.DEFAULT);
			FileUtil.writeFile(widgetPath, encodedJson);
			
			Log.d("ViewEditor", "Saved widget: " + activityName + " -> " + widgetType + " -> " + widgetIds);
			
		} catch (Exception e) {
			Log.e("ViewEditor", "Error saving widget info: " + e.getMessage());
		}
	}
	
	
	private void removeWidgetInfo(String activityName, String widgetType, String widgetId) {
		try {
			String projectPath = FileUtil.getExternalStorageDir() + "/.blacklogics/data/" + SC_ID;
			String widgetPath = projectPath + "/widget_info/project_widgets.json";
			
			// Load existing widget map
			Map<String, Map<String, List<String>>> widgetMap = new HashMap<>();
			if (FileUtil.isExistFile(widgetPath)) {
				String encodedJson = FileUtil.readFile(widgetPath);
				String decodedJson = new String(Base64.decode(encodedJson, Base64.DEFAULT));
				Type mapType = new TypeToken<Map<String, Map<String, List<String>>>>() {}.getType();
				widgetMap = new Gson().fromJson(decodedJson, mapType);
			}
			
			Map<String, List<String>> activityWidgets = widgetMap.get(activityName);
			if (activityWidgets != null) {
				List<String> widgetIds = activityWidgets.get(widgetType);
				if (widgetIds != null) {
					widgetIds.remove(widgetId); // Remove specific widgetId
					
					// Remove type if empty
					if (widgetIds.isEmpty()) {
						activityWidgets.remove(widgetType);
					}
					
					// Remove activity if empty
					if (activityWidgets.isEmpty()) {
						widgetMap.remove(activityName);
					}
				}
			}
			
			// Save updated map
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String prettyJson = gson.toJson(widgetMap);
			String encodedJson = Base64.encodeToString(prettyJson.getBytes(), Base64.DEFAULT);
			FileUtil.writeFile(widgetPath, encodedJson);
			
			Log.d("ViewEditor", "Removed widget info for ID: " + widgetId + " in activity: " + activityName);
			
		} catch (Exception e) {
			Log.e("ViewEditor", "Error removing widget info for " + widgetId, e);
		}
	}
	
	
	private String getWidgetTypeName(View view) {
		String className = view.getClass().getSimpleName();
		// Convert WidgetTextView -> TextView, WidgetButton -> Button
		if (className.startsWith("Widget")) {
			return className.substring(6);  // Remove "Widget" prefix
		}
		return className;
	}
	
	public static Map<String, String> getWidgetInfoForActivity(String activityName) {
		try {
			String projectPath = FileUtil.getExternalStorageDir() + "/.blacklogics/data/" + 
			SC_ID;
			String widgetPath = projectPath + "/widget_info/project_widgets.json";
			if (!FileUtil.isExistFile(widgetPath)) return new HashMap<>();
			
			String encodedJson = FileUtil.readFile(widgetPath);
			String decodedJson = new String(Base64.decode(encodedJson, Base64.DEFAULT));
			Type mapType = new TypeToken<Map<String, Map<String, String>>>() {}.getType();
			Map<String, Map<String, String>> widgetMap = new Gson().fromJson(decodedJson, mapType);
			
			return widgetMap.getOrDefault(activityName, new HashMap<>());
			
		} catch (Exception e) {
			Log.e("ViewEditor", "Error loading widget info: " + e.getMessage());
			return new HashMap<>();
		}
	}
	
	private void saveAndGenerateCode(View newView) {
		String activityName = getCurrentActivityName();
		String widgetType = getWidgetTypeName(newView);
		String widgetId = idManager.getId(newView);
		
		if (widgetId != null) {
			ProjectActivityBean.ViewBean bean = new ProjectActivityBean.ViewBean(widgetType, widgetId);
			bean.setScId(SC_ID);
			bean.setActivityName(activityName);
			
			// Set proper dimensions
			ViewGroup.LayoutParams params = newView.getLayoutParams();
			if (params != null) {
				bean.setWidth(params.width);
				bean.setHeight(params.height);
			}
			
			// Set margins
			if (params instanceof ViewGroup.MarginLayoutParams) {
				ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) params;
				bean.setMarginLeft(marginParams.leftMargin);
				bean.setMarginTop(marginParams.topMargin);
				bean.setMarginRight(marginParams.rightMargin);
				bean.setMarginBottom(marginParams.bottomMargin);
			}
			
			// Set paddings
			bean.setPaddingLeft(newView.getPaddingLeft());
			bean.setPaddingTop(newView.getPaddingTop());
			bean.setPaddingRight(newView.getPaddingRight());
			bean.setPaddingBottom(newView.getPaddingBottom());
			
			// Set common properties
			bean.setBackgroundColor(newView.getBackground() instanceof ColorDrawable ? ((ColorDrawable) newView.getBackground()).getColor() : Color.TRANSPARENT);
			bean.setVisibility(newView.getVisibility());
			bean.setAlpha(newView.getAlpha());
			bean.setRotation(newView.getRotation());
			bean.setScaleX(newView.getScaleX());
			bean.setScaleY(newView.getScaleY());
			bean.setElevation(newView.getElevation());
			
			// Set type-specific properties
			if (newView instanceof TextView) {
				TextView tv = (TextView) newView;
				bean.setText(tv.getText().toString());
				bean.setTextSize(tv.getTextSize() / getResources().getDisplayMetrics().density);
				bean.setTextColor(tv.getCurrentTextColor());
				bean.setGravity(tv.getGravity());
				bean.setTextAlignment(tv.getTextAlignment());
				bean.setTextStyle(tv.getTypeface() != null ? (tv.getTypeface().isBold() ? "bold" : (tv.getTypeface().isItalic() ? "italic" : "")) : "");
				bean.setFontFamily(tv.getTypeface() != null ? tv.getTypeface().toString() : "");
				bean.setMaxLines(tv.getMaxLines());
				bean.setLineSpacingMultiplier(tv.getLineSpacingMultiplier());
				bean.setLineSpacingExtra(tv.getLineSpacingExtra());
				bean.setSingleLine(tv.isSingleLine());
				
				if (newView instanceof EditText) {
					EditText et = (EditText) newView;
					bean.setHint(et.getHint() != null ? et.getHint().toString() : "");
					bean.setInputType(et.getInputType());
				} else if (newView instanceof Button) {
					// Button-specific if needed
				} else if (newView instanceof CheckBox || newView instanceof Switch) {
					CompoundButton cb = (CompoundButton) newView;
					bean.setChecked(cb.isChecked());
				}
			} else if (newView instanceof ImageView) {
				ImageView iv = (ImageView) newView;
				bean.setScaleType(iv.getScaleType().toString());
				bean.setImagePath(""); // Update if image path is available
			} else if (newView instanceof ProgressBar) {
				ProgressBar pb = (ProgressBar) newView;
				bean.setProgress(pb.getProgress());
				bean.setMaxProgress(pb.getMax());
				bean.setProgressType(pb.isIndeterminate() ? "CIRCULAR" : "HORIZONTAL");
			} else if (newView instanceof SeekBar) {
				SeekBar sb = (SeekBar) newView;
				bean.setProgress(sb.getProgress());
				bean.setMaxProgress(sb.getMax());
			} else if (newView instanceof ViewGroup) {
				bean.setChildren(new ArrayList<>());
			} else {
				bean.setText(widgetType);
			}
			
			// Save to JSON storage
			boolean saved = bean.saveToStorage();
			if (saved) {
				Log.d("ViewEditor", "Widget saved: " + widgetType + " (" + widgetId + ")");
			} else {
				Log.e("ViewEditor", "Failed to save widget: " + widgetId);
			}
		}
		
		// Generate XML using XmlLayoutGenerator
		XmlLayoutGenerator xmlGenerator = new XmlLayoutGenerator();
		String formattedXml = xmlGenerator.generate(designActivity.currentActivityBean);
		
		if (formattedXml.isEmpty()) {
			//	SketchwareUtil.showMessage(getContext(), "Failed to generate XML layout");
			return;
		}
		
		// Save XML and generate Java code
		if (designActivity != null && designActivity.complex != null) {
			String layoutName = (designActivity.currentActivityBean != null)
			? designActivity.currentActivityBean.getLayoutName()
			: activityName;
			designActivity.complex.setXmlCode(layoutName, formattedXml);
			designActivity.generateJavaCode(activityName, layoutName);
		} else {
			Log.w("ViewEditor", "DesignActivity or complex is null, falling back to file-based storage");
			//	String projectPath = FileUtil.getExternalStorageDir() + "/.blacklogics/data/" + SC_ID + "/files/";
			//	FileUtil.writeFile(projectPath + activityName + ".xml", formattedXml);
		}
		
		// Push action to undo stack
		EditorAction action = new EditorAction(
		ACTION_ADD_VIEW,
		newView,
		(ViewGroup) newView.getParent(),
		((ViewGroup) newView.getParent()).indexOfChild(newView),
		null,
		null,
		activityName
		);
		addAction(action);
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
	
	/**
* Inner class for managing temporary layouts with full ViewEditor access
*/	
	public static class TempLayoutManager {
		
		// Direct reference to parent ViewEditor
		private final ViewEditor editor;
		
		// Constants
		private static final String TEMP_DIR = "/.blacklogic/.temp_layouts/";
		private static final String AES_KEY = "NexusTeamSmartIndia2025LayoutKey"; // Replace with secure key management
		private static final String AES_IV = "1234567890abcdef"; // 16-byte IV, replace with secure IV
		private static final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";
		private static final int[] KEY_PARTS = {78,101,120,117,115,84,101,97,109,83,109,97,114,116,73,110,100,105,97,50,48,50,53,76,97,121,111,117,116};
		private static final int[] IV_PARTS  = {49,50,51,52,53,54,55,56,57,48,97,98,99,100,101,102};
		
		public TempLayoutManager(ViewEditor editor) {
			this.editor = editor;
		}
		
		/** ---------------- AES ENCRYPTION/DECRYPTION ---------------- **/
		
		private SecretKeySpec getKey() {
			byte[] keyBytes = new byte[KEY_PARTS.length];
			for (int i = 0; i < KEY_PARTS.length; i++) {
				keyBytes[i] = (byte) ((KEY_PARTS[i] ^ 0x55) + 3);
			}
			return new SecretKeySpec(keyBytes, "AES");
		}
		
		private IvParameterSpec getIV() {
			byte[] ivBytes = new byte[IV_PARTS.length];
			for (int i = 0; i < IV_PARTS.length; i++) {
				ivBytes[i] = (byte) ((IV_PARTS[i] ^ 0x55) + 3);
			}
			return new IvParameterSpec(ivBytes);
		}
		
		// Encrypt data
		private String encrypt(String data) {
			try {
				SecretKeySpec keySpec = new SecretKeySpec(AES_KEY.getBytes("UTF-8"), "AES");
				IvParameterSpec ivSpec = new IvParameterSpec(AES_IV.getBytes("UTF-8"));
				Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
				cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
				byte[] encrypted = cipher.doFinal(data.getBytes("UTF-8"));
				return Base64.encodeToString(encrypted, Base64.DEFAULT);
			} catch (Exception e) {
				Log.e("ViewEditor", "Encryption failed: " + e.getMessage(), e);
				return null;
			}
		}
		
		// Decrypt data
		private String decrypt(String encryptedData) {
			try {
				SecretKeySpec keySpec = new SecretKeySpec(AES_KEY.getBytes("UTF-8"), "AES");
				IvParameterSpec ivSpec = new IvParameterSpec(AES_IV.getBytes("UTF-8"));
				Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
				cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
				byte[] decoded = Base64.decode(encryptedData, Base64.DEFAULT);
				byte[] decrypted = cipher.doFinal(decoded);
				return new String(decrypted, "UTF-8");
			} catch (Exception e) {
				Log.e("ViewEditor", "Decryption failed: " + e.getMessage(), e);
				return null;
			}
		}
		
		
		/** ---------------- FILE OPERATIONS ---------------- **/
		
		private String getTempFilePath(String activityName) {
			return FileUtil.getPackageDataDir(editor.getContext()) + TEMP_DIR + "temp_" + activityName + ".json";
		}
		
		private void ensureTempDirExists() {
			FileUtil.makeDir(FileUtil.getPackageDataDir(editor.getContext()) + TEMP_DIR);
		}
		
		/** ---------------- SAVE TEMP LAYOUT ---------------- **/
		
		public void saveTempLayout(String activityName) {
			if (activityName == null || activityName.trim().isEmpty()) {
				activityName = editor.getCurrentActivityName();
			}
			
			try {
				ensureTempDirExists();
				
				if (editor.editorLayout.getChildCount() == 0) {
					Log.w("TempLayoutManager", "No views to save as temp layout");
					return;
				}
				
				// Use ViewEditor's existing generateCode method
				View rootView = editor.editorLayout.getChildAt(0);
				String xmlCode = editor.generateCode(rootView);
				
				// Format XML properly
				String formattedXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
				xmlCode.substring(0, xmlCode.indexOf("\n")) + "\n" +
				"xmlns:android=\"http://schemas.android.com/apk/res/android\"" +
				xmlCode.substring(xmlCode.indexOf("\n"));
				
				String encrypted = encrypt(formattedXml);
				if (encrypted != null) {
					FileUtil.writeFile(getTempFilePath(activityName), encrypted);
					Log.d("TempLayoutManager", "Temp layout saved for: " + activityName);
				} else {
					SketchwareUtil.showMessage(editor.getContext(), "Failed to encrypt temp layout");
				}
				
			} catch (Exception e) {
				Log.e("TempLayoutManager", "saveTempLayout failed for " + activityName, e);
				SketchwareUtil.showMessage(editor.getContext(), "Temp save error: " + e.getMessage());
			}
		}
		
		/** ---------------- LOAD XML INTO EDITOR (WITH FULL ACCESS) ---------------- **/
		
		/**
* Loads XML into editor using ViewEditor's existing infrastructure
*/		
		public void loadXmlIntoEditor(String xmlCode) {
			try {
				// Backup current state using ViewEditor's existing backup mechanism
				editor.oldIdManager = new IdManager();
				editor.oldAttributesValueMap = new HashMap<>(editor.attributesValueMap);
				
				// Clear current layout
				editor.editorLayout.removeAllViews();
				editor.idManager = new IdManager();
				editor.attributesValueMap.clear();
				
				XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
				XmlPullParser parser = factory.newPullParser();
				parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
				parser.setInput(new StringReader(xmlCode));
				
				ArrayList<View> viewStack = new ArrayList<>();
				viewStack.add(editor.editorLayout);
				
				// Parse XML using ViewEditor's existing parsing logic
				int eventType = parser.getEventType();
				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
						case XmlPullParser.START_TAG:
						handleXmlStartTag(parser, viewStack);
						break;
						
						case XmlPullParser.END_TAG:
						handleXmlEndTag(viewStack);
						break;
					}
					eventType = parser.next();
				}
				
				// Apply attributes using ViewEditor's existing method
				applyAllAttributes();
				
				Log.d("TempLayoutManager", "XML loaded successfully with " + editor.attributesValueMap.size() + " views");
				
			} catch (Exception e) {
				Log.e("TempLayoutManager", "loadXmlIntoEditor failed", e);
				editor.restorePreviousStateOnFailure();
				SketchwareUtil.showMessage(editor.getContext(), "Failed to load layout: " + e.getMessage());
			}
		}
		
		/**
* Handles START_TAG using ViewEditor's infrastructure
*/		
		private void handleXmlStartTag(XmlPullParser parser, ArrayList<View> viewStack) 
		throws XmlPullParserException {
			
			String tagName = parser.getName();
			if (tagName == null || tagName.trim().isEmpty()) return;
			
			// Use ViewEditor's existing view creation method
			View view = editor.createPlaceholderOrStandardView(tagName);
			if (view == null) {
				Log.w("TempLayoutManager", "Cannot create view for tag: " + tagName);
				// Fallback to generic view
				view = new View(editor.getContext());
			}
			
			// Setup view using ViewEditor's existing method
			editor.setupImportedView(view);
			
			// Create AttributeSet using ViewEditor's existing logic
			AttributeSet attributeSet = new AttributeSet();
			int attributeCount = parser.getAttributeCount();
			
			for (int i = 0; i < attributeCount; i++) {
				String attributeName = parser.getAttributeName(i);
				String attributeValue = parser.getAttributeValue(i);
				
				// Skip namespace declarations
				if (attributeName.startsWith("xmlns")) continue;
				
				// Handle ID using ViewEditor's IdManager
				if ("android:id".equals(attributeName)) {
					String idName = AttributeUtils.getName(attributeValue);
					if (idName != null && !idName.trim().isEmpty()) {
						editor.idManager.addNewId(view, idName);
					}
				}
				
				// Add to AttributeSet
				Attribute attr = new Attribute(attributeName, attributeValue);
				attributeSet.add(attr);
			}
			
			// Store in ViewEditor's attribute map
			editor.attributesValueMap.put(view, attributeSet);
			viewStack.add(view);
			
			Log.v("TempLayoutManager", "Created view: " + tagName + " with ID: " + editor.idManager.getId(view));
		}
		
		/**
* Handles END_TAG - builds hierarchy
*/		
		private void handleXmlEndTag(ArrayList<View> viewStack) {
			if (viewStack.size() > 1) {
				View child = viewStack.remove(viewStack.size() - 1);
				ViewGroup parent = (ViewGroup) viewStack.get(viewStack.size() - 1);
				parent.addView(child);
			}
		}
		
		/**
* Applies all attributes using ViewEditor's existing applyAttribute method
*/		
		private void applyAllAttributes() {
			for (Map.Entry<View, AttributeSet> entry : editor.attributesValueMap.entrySet()) {
				View view = entry.getKey();
				AttributeSet attributeSet = entry.getValue();
				
				if (attributeSet == null) continue;
				
				for (Attribute attr : attributeSet.getAttributes()) {
					try {
						// Use ViewEditor's existing applyAttribute method
						editor.applyAttribute(view, attr);
					} catch (Exception e) {
						Log.w("TempLayoutManager", "Failed to apply attribute: " + attr.getName(), e);
					}
				}
			}
			
			Log.d("TempLayoutManager", "Applied " + editor.attributesValueMap.size() + " view attributes");
		}
		
		/** ---------------- RESTORE TEMP LAYOUT ---------------- **/
		
		public void restoreTempLayout(String activityName) {
			if (activityName == null || activityName.trim().isEmpty()) {
				activityName = editor.getCurrentActivityName();
			}
			
			try {
				String tempFilePath = getTempFilePath(activityName);
				if (!FileUtil.isExistFile(tempFilePath)) {
					Log.w("TempLayoutManager", "No temp layout found for: " + activityName);
					return;
				}
				
				String encryptedContent = FileUtil.readFile(tempFilePath);
				String xmlCode = decrypt(encryptedContent);
				
				if (xmlCode == null || xmlCode.trim().isEmpty()) {
					SketchwareUtil.showMessage(editor.getContext(), "Failed to decrypt temp layout");
					return;
				}
				
				// Use the new load method
				loadXmlIntoEditor(xmlCode);
				
				// Delete temp file after successful restore
				new File(tempFilePath).delete();
				
				// Save to permanent storage using ViewEditor's existing method
				editor.saveLayout(activityName);
				
				Log.d("TempLayoutManager", "Temp layout restored for: " + activityName);
				SketchwareUtil.showMessage(editor.getContext(), "Layout restored: " + activityName);
				
			} catch (Exception e) {
				Log.e("TempLayoutManager", "restoreTempLayout failed for " + activityName, e);
				SketchwareUtil.showMessage(editor.getContext(), "Restore error: " + e.getMessage());
			}
		}
		
		/** ---------------- GLOBAL RESTORE DIALOG ---------------- **/
		
		public void showGlobalRestoreDialog() {
			File dir = new File(FileUtil.getPackageDataDir(editor.getContext()) + TEMP_DIR);
			if (!dir.exists()) return;
			
			File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));
			if (files == null || files.length == 0) return;
			
			ArrayList<String> pendingLayouts = new ArrayList<>();
			for (File f : files) {
				String name = f.getName().replace("temp_", "").replace(".json", "");
				pendingLayouts.add(name);
			}
			
			if (pendingLayouts.isEmpty()) return;
			
			AlertDialog.Builder builder = new AlertDialog.Builder(editor.getContext());
			builder.setTitle("Restore Pending Layouts");
			builder.setMessage("Found " + pendingLayouts.size() + " unsaved layouts:\n\n" + 
			String.join(", ", pendingLayouts.subList(0, 
			Math.min(3, pendingLayouts.size()))) + 
			(pendingLayouts.size() > 3 ? "..." : ""));
			
			builder.setPositiveButton("Restore All", (dialog, which) -> {
				restoreAllTempLayouts(pendingLayouts.size(), new GlobalRestoreCallback() {
					@Override
					public void onLayoutRestored(String activityName, String xmlCode) {
						Log.d("TempLayoutManager", "Restored: " + activityName);
					}
					
					@Override
					public void onAllRestored() {
						SketchwareUtil.showMessage(editor.getContext(), 
						"Successfully restored " + pendingLayouts.size() + " layouts!");
					}
				});
			});
			
			builder.setNegativeButton("Later", null);
			
			builder.setNeutralButton("View Details", (dialog, which) -> {
				StringBuilder details = new StringBuilder("Pending Layouts:\n\n");
				for (String layout : pendingLayouts) {
					details.append("• ").append(layout).append("\n");
				}
				
				new AlertDialog.Builder(editor.getContext())
				.setTitle("Pending Layouts (" + pendingLayouts.size() + ")")
				.setMessage(details.toString())
				.setPositiveButton("Restore All", (d, w) -> restoreAllTempLayouts(pendingLayouts.size(), null))
				.setNegativeButton("Cancel", null)
				.show();
			});
			
			builder.show();
		}
		
		/** ---------------- RESTORE ALL LAYOUTS ---------------- **/
		
		private void restoreAllTempLayouts(int totalCount, GlobalRestoreCallback callback) {
			File dir = new File(FileUtil.getPackageDataDir(editor.getContext()) + TEMP_DIR);
			if (!dir.exists()) {
				if (callback != null) callback.onAllRestored();
				return;
			}
			
			File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));
			if (files == null || files.length == 0) {
				if (callback != null) callback.onAllRestored();
				return;
			}
			
			int restoredCount = 0;
			for (File f : files) {
				String layoutName = f.getName().replace("temp_", "").replace(".json", "");
				
				try {
					String encryptedContent = FileUtil.readFile(f.getAbsolutePath());
					String xmlCode = decrypt(encryptedContent);
					
					if (xmlCode != null && !xmlCode.trim().isEmpty()) {
						// Save to permanent storage using ViewEditor's method
						editor.saveLayout(layoutName);
						
						// Load into current editor if it matches current activity
						if (editor.getCurrentActivityName().equals(layoutName)) {
							loadXmlIntoEditor(xmlCode);
						}
						
						restoredCount++;
						if (callback != null) {
							callback.onLayoutRestored(layoutName, xmlCode);
						}
					}
					
				} catch (Exception e) {
					Log.e("TempLayoutManager", "Failed to restore: " + layoutName, e);
				}
				
				// Delete temp file
				try {
					f.delete();
				} catch (Exception e) {
					Log.w("TempLayoutManager", "Failed to delete temp file: " + f.getAbsolutePath());
				}
			}
			
			Log.i("TempLayoutManager", "Restored " + restoredCount + " layouts out of " + totalCount);
			if (callback != null) callback.onAllRestored();
		}
		
		/** ---------------- UTILITY METHODS ---------------- **/
		
		public ArrayList<String> getPendingTempLayouts() {
			ArrayList<String> pending = new ArrayList<>();
			File dir = new File(FileUtil.getPackageDataDir(editor.getContext()) + TEMP_DIR);
			if (!dir.exists()) return pending;
			
			File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));
			if (files != null) {
				for (File f : files) {
					String name = f.getName().replace("temp_", "").replace(".json", "");
					pending.add(name);
				}
			}
			return pending;
		}
		
		public void clearAllTempLayouts() {
			File dir = new File(FileUtil.getPackageDataDir(editor.getContext()) + TEMP_DIR);
			if (!dir.exists()) return;
			
			File[] files = dir.listFiles();
			if (files != null) {
				for (File f : files) {
					f.delete();
				}
				Log.d("TempLayoutManager", "Cleared all temp layouts");
				SketchwareUtil.showMessage(editor.getContext(), "Cleared all temporary layouts");
			}
		}
		
		/** ---------------- CALLBACK INTERFACE ---------------- **/
		
		public interface GlobalRestoreCallback {
			void onLayoutRestored(String activityName, String xmlCode);
			void onAllRestored();
		}
	}
}
