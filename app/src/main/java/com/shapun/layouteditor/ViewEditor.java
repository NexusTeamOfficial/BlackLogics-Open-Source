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

import b.b.b.pq;
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
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.os.Looper;
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
import android.widget.AdapterView;
import android.view.View.*;
import android.view.KeyEvent;
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
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import com.besome.blacklogics.*;
import com.besome.blacklogics.beans.CodeBean;
import com.besome.blacklogics.DesignActivity;
import com.besome.blacklogics.FileUtil;
import com.besome.blacklogics.SketchwareUtil;
import com.besome.blacklogics.project.ProjectDataHelper;
import com.besome.blacklogics.beans.ProjectActivityBean;
import com.besome.blacklogics.logic.editor.LogicEditorActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.nexusteam.ui.FloatAttributeMaterialDialog;
import com.nexusteam.ui.TextSizePickerDialog;
import com.nexusteam.internal.os.layouteditor.color.ColorPickerActivity;
import com.nexusteam.internal.os.layouteditor.util.*;
import com.nexusteam.internal.os.layouteditor.util.WidgetStorageManager;
import com.shapun.layouteditor.*;
import com.shapun.layouteditor.managers.*;
import com.shapun.layouteditor.utils.*;
import android.graphics.Color;
import java.nio.charset.StandardCharsets;
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
import java.util.Locale;
import java.util.Map;
import java.util.LinkedHashMap;
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
	//	private AdView adview2;
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
	public static String SAVE_PATH = "";
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
	
	private Stack<EditorAction> undoStack = new Stack<>();
	private Stack<EditorAction> redoStack = new Stack<>();
	
	private Stack<String> undoXmlStack = new Stack<>(); 
	private Stack<String> redoXmlStack = new Stack<>(); 
	private boolean isUndoRedoInProgress = false;
	
	public Intent intent = new Intent();
	
	private CodeBean codeBean;
	
	private TempLayoutManager tempLayoutManager;
	
	private ViewBeanManager beanManager;
	
	private DesignActivity designActivity;
	
	private final HashMap<View, Drawable> originalBackgrounds = new HashMap<>();
	private View selectedView = null;
	
	private static final int ACTION_ADD_VIEW = 1;
	private static final int ACTION_REMOVE_VIEW = 2;
	private static final int ACTION_UPDATE_ATTR = 3;
	
	private HashMap<String, Stack<EditorAction>> undoStacks = new HashMap<>();
	private HashMap<String, Stack<EditorAction>> redoStacks = new HashMap<>();
	private static final char[] ENCRYPT_PASSWORD = "NexusTeamStrongKey!".toCharArray();
	
	private static final String AES_KEY = ""; 
	private static final String AES_IV = ""; 
	private static final int[] KEY_PARTS = {78,101,120,117,115,84,101,97,109,83,109,97,114,116,73,110,100,105,97,50,48,50,53,76,97,121,111,117,116};
	private static final int[] IV_PARTS  = {49,50,51,52,53,54,55,56,57,48,97,98,99,100,101,102};
	
	private static final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";
	
	private static class EditorAction {
		int actionType;
		View view;
		ViewGroup parent;
		int index;
		AttributeSet oldAttributes;
		AttributeSet newAttributes;
		String viewId;
		String activityName;
		String xmlSnapshot;
		
		EditorAction(int actionType, View view, ViewGroup parent, int index,
		AttributeSet oldAttributes, AttributeSet newAttributes, 
		String activityName, String xmlSnapshot) {
			this.actionType = actionType;
			this.view = view;
			this.parent = parent;
			this.index = index;
			this.oldAttributes = oldAttributes;
			this.newAttributes = newAttributes;
			this.viewId = (view != null) ? idManager.getId(view) : null;
			this.activityName = activityName;
			this.xmlSnapshot = xmlSnapshot;
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
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// Check karte hain ki key back hai aur action up hai
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
			// Yahan aapka method call karenge
			unselectSelectedWidget();
			
			// Event consume ho gaya, Activity ko mat bhejo
			return true;
		}
		return super.dispatchKeyEvent(event);
	}
	
	
	public void setOnWidgetAddListener(OnWidgetAdd listener) {
		this.onWidgetAddListener = listener;
	}
	
	public void setScId(String SC_ID) {
		this.SC_ID = SC_ID;
	}
	public void setDesignActivity(DesignActivity designActivity) {
		this.designActivity = designActivity;
		// Initialize beanManager here instead of in initialize()
		if (beanManager != null) {
			beanManager.initialize(getContext(), SC_ID, getCurrentActivityName());
			beanManager.cleanupOrphanedBeans();
		}
	}
	private void initialize(Context context) {
		setOrientation(VERTICAL);
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.view_editor, this, true);
		
		//	adview2 = view.findViewById(R.id.adview2);
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
		beanManager = ViewBeanManager.getInstance();
		codeBean = new CodeBean();
		/*	
beanManager = ViewBeanManager.getInstance();
beanManager.initialize(context, SC_ID, getCurrentActivityName());

// Clean up any orphaned beans from previous sessions
beanManager.cleanupOrphanedBeans();*/		
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
		
		img_add_view.setOnClickListener(v -> {
			showMaterialWidgetDialog(getContext(), viewsList);
		});
		editorLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				hideProperties();
				unselectSelectedWidget();
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
		
		View root = editorLayout.getChildAt(0);
		if (root instanceof ViewGroup) {
			applyContainerStroke(root);
		}
		
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
			if (obj instanceof View) {
				setDefaultTextSize((View) obj); // Add this
			}
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
				if (obj instanceof View) {
					setDefaultTextSize((View) obj); // Add this
				}
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
		
		if (selectedView != null && selectedView != view) {
			Drawable original = originalBackgrounds.get(selectedView);
			if (original != null) {
				selectedView.setBackground(original);
			} else {
				selectedView.setBackground(null);
			}
		}
		
		
		selectedView = view;
		
		if (!originalBackgrounds.containsKey(view)) {
			Drawable currentBg = view.getBackground();
			if (currentBg != null) {
				originalBackgrounds.put(view, currentBg.getConstantState() != null
				? currentBg.getConstantState().newDrawable().mutate()
				: currentBg);
			} else {
				originalBackgrounds.put(view, null);
			}
		}
		
		GradientDrawable highlightDrawable = new GradientDrawable();
		highlightDrawable.setColor(Color.parseColor("#77BBCCDD"));
		highlightDrawable.setCornerRadius(8f);
		view.setBackground(highlightDrawable);
		ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0.85f, 1f);
		animator.setDuration(180);
		animator.start();
		
		
		showProperties();
		
		// Create BottomSheetDialog
		final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
		bottomSheetDialog.setCancelable(true);
		bottomSheetDialog.setCanceledOnTouchOutside(true);
		
		// Inflate layout for BottomSheetDialog
		View inflated = LayoutInflater.from(getContext()).inflate(R.layout.attributes_bottom_sheet, null);
		bottomSheetDialog.setContentView(inflated);
		
		if (bottomSheetDialog.getBehavior() != null) {
			bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
		}
		/*
// Set corner radius and background
if (bottomSheetDialog.getWindow() != null) {
bottomSheetDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
}
*/		
		/*	GradientDrawable background = new GradientDrawable();
int d = (int) getContext().getResources().getDisplayMetrics().density;
background.setColor(0xFFFFFFFF);
background.setCornerRadius(d * 16);
inflated.setBackground(background);*/		
		inflated.setClipToOutline(true);
		
		// Initialize UI components (same as before)
		final TextView tv_view_id = inflated.findViewById(R.id.tv_view_id);
		final RecyclerView rv_attributes = inflated.findViewById(R.id.rv_attributes);
		final RecyclerView rv_listeners = inflated.findViewById(R.id.listeners);
		final ImageView img_edit_id = inflated.findViewById(R.id.img_edit_id);
		final ImageView img_common_attributes = inflated.findViewById(R.id.img_common_attributes);
		final ImageView img_custom = inflated.findViewById(R.id.img_custom);
		final com.google.android.material.tabs.TabLayout tabLayout = inflated.findViewById(R.id.attributesOrListener);
		final LinearLayout attributesTab = inflated.findViewById(R.id.attributesTab);
		final LinearLayout eventTab = inflated.findViewById(R.id.eventTab);
		
		// Setup TabLayout (same as before)
		tabLayout.removeAllTabs();
		tabLayout.addTab(tabLayout.newTab().setText("attributes"));
		tabLayout.addTab(tabLayout.newTab().setText("events"));
		
		// Setup RecyclerViews (same as before)
		LinearLayoutManager attrLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
		rv_attributes.setLayoutManager(attrLayoutManager);
		AttributeRecyclerAdapter attrAdapter = new AttributeRecyclerAdapter(getContext(), attributesList);
		rv_attributes.setAdapter(attrAdapter);
		
		LinearLayoutManager listenerLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
		rv_listeners.setLayoutManager(listenerLayoutManager);
		ListenerRecyclerAdapter listenerAdapter = new ListenerRecyclerAdapter(getContext(), listenersList);
		rv_listeners.setAdapter(listenerAdapter);
		
		// Set view ID
		tv_view_id.setText(idManager.getId(view));
		
		// Tab selection listener (same as before)
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
		
		// Edit ID listener (same as before)
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
				saveStateForUndo("change_id");
			});
			idDialog.setNegativeButton("Cancel", null);
			idDialog.show();
		});
		
		// Common attributes listener (same as before)
		img_common_attributes.setOnClickListener(v -> {
			bottomSheetDialog.dismiss();
			showCommonAttributesDialog(view);
		});
		
		// Custom attributes listener (same as before)
		img_custom.setOnClickListener(v -> {
			bottomSheetDialog.dismiss();
			showCustomAttributesDialog(view);
		});
		
		// Attribute item click listener
		attrAdapter.setOnItemClickListener(position -> {
			final HashMap<String, Object> attribute = attributesList.get(position);
			final String attrName = attribute.get("attribute_name").toString();
			final String attrType = attribute.get("argument_type").toString();
			final com.google.android.material.dialog.MaterialAlertDialogBuilder inputDialog =
			new com.google.android.material.dialog.MaterialAlertDialogBuilder(
			getContext(),
			com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered
			);
			inputDialog.setTitle(attribute.get("name").toString());
			inputDialog.setNeutralButton("Cancel", null);
			attrSet = attributesValueMap.get(view);
			String currentValue = attrSet != null && attrSet.getAttribute(attrName) != null ?
			attrSet.getAttribute(attrName).getValue() : "";
			
			boolean shouldShowDialog = true;
			
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
				shouldShowDialog = false;
				// Check if the attribute is textSize → open TextSizePickerDialog
				if (attrName.equals("android:textSize")) {
					try {
						String currentTextSize = currentValue;
						
						if ((currentValue == null || currentValue.isEmpty()) && view instanceof TextView) {
							float px = ((TextView) view).getTextSize();
							float spValue = px / getContext().getResources().getDisplayMetrics().scaledDensity;
							currentTextSize = String.format(Locale.getDefault(), "%.1fsp", spValue);
						}
						
						new com.nexusteam.ui.TextSizePickerDialog(
						getContext(),
						currentTextSize,
						selectedValue -> {
							try {
								attr = new Attribute(attrName, selectedValue);
								attr.setValue(selectedValue);
								
								float pxValue = DimensionUtils.getValueInPx(getContext(), selectedValue);
								
								if (view instanceof TextView) {
									((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, pxValue);
								}
								
								if (attributesValueMap.get(view) != null) {
									attributesValueMap.get(view).add(attr);
								}
								
								applyAttribute(view, attr, attribute);
								
								saveStateForUndo("change_textsize");
								
								ViewGroup rootView = (ViewGroup) editorLayout.getChildAt(0);
								String xmlCode = generateCode(rootView);
								tempLayoutManager.saveTempLayout(xmlCode);
								
							} catch (Exception e) {
								SketchwareUtil.showMessage(getContext(), "Error applying textSize: " + e.getMessage());
							}
						}
						).show();
						
					} catch (Exception e) {
						SketchwareUtil.showMessage(getContext(), "Error reading textSize: " + e.getMessage());
					}
				} 
				else {
					try {
						// Parse current float or default to 0
						float currentFloat = 0f;
						try {
							if (!currentValue.isEmpty()) {
								currentFloat = Float.parseFloat(currentValue);
								Log.d("FLOAT_DEBUG", "Parsed currentValue: " + currentValue + " to: " + currentFloat);
							}
						} catch (NumberFormatException e) {
							Log.e("FLOAT_DEBUG", "Error parsing currentValue: " + currentValue, e);
							currentFloat = 0f;
						}
						
						Log.d("FLOAT_DEBUG", "Calling FloatAttributeMaterialDialog with: " + currentFloat);
						
						FloatAttributeMaterialDialog.show(
						getContext(),
						attribute.get("name").toString(),
						currentFloat,
						value -> {
							try {
								String formatted = String.format(Locale.getDefault(), "%.1f", value);
								Log.d("FLOAT_DEBUG", "Selected value: " + value + ", formatted: " + formatted);
								
								attr = new Attribute(attrName, formatted);
								if (attrSet == null) {
									attrSet = new AttributeSet();
									attributesValueMap.put(view, attrSet);
								}
								attrSet.add(attr);
								applyAttribute(view, attr, attribute);
							} catch (Exception e) {
								Log.e("FLOAT_DEBUG", "Error in callback", e);
								SketchwareUtil.showMessage(getContext(), "Error: " + e.getMessage());
							}
						}
						);
						
					} catch (Exception e) {
						Log.e("FLOAT_DEBUG", "Overall error in float case", e);
						SketchwareUtil.showMessage(getContext(), "Float dialog error: " + e.getMessage());
					}
					shouldShowDialog = false;
				}
				break;
				
				case "String":
				if (attrName.equals("Convert Widget")) {
					showConvertWidgetDialog(view);
					shouldShowDialog = false; 
				} else {
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
				}
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
				final int initialColor = !currentValue.isEmpty()
				? Color.parseColor(currentValue)
				: Color.WHITE;
				
				// Store current context
				final String currentAttrName = attrName;
				final HashMap<String, Object> currentAttribute = attribute;
				final View currentView = view;
				
				// Register BroadcastReceiver FIRST
				BroadcastReceiver colorReceiver = new BroadcastReceiver() {
					@Override
					public void onReceive(Context context, Intent intent) {
						String selectedColor = intent.getStringExtra("color");
						if (selectedColor != null) {
							Attribute colorAttr = new Attribute(currentAttrName, selectedColor);
							if (attrSet == null) {
								attrSet = new AttributeSet();
								attributesValueMap.put(currentView, attrSet);
							}
							attrSet.add(colorAttr);
							applyAttribute(currentView, colorAttr, currentAttribute);
							
							// Unregister receiver
							LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(this);
						}
					}
				};
				
				LocalBroadcastManager.getInstance(getContext()).registerReceiver(
				colorReceiver,
				new IntentFilter("data")
				);
				
				// Now launch ColorPickerActivity
				Intent colorIntent = new Intent(getContext(), ColorPickerActivity.class);
				colorIntent.putExtra("initialColor", initialColor);
				getContext().startActivity(colorIntent);
				shouldShowDialog = false;
				break;
				case "Drawable":
				ArrayList<String> listData = new ArrayList<>();
				ArrayList<String> imagePaths = new ArrayList<>();
				FileUtil.listDir(FileUtil.getExternalStorageDir() + "/.blacklogics/resources/images/" + ProjectDataHelper.getScId(getContext()) + "/", imagePaths);
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
				inputDialog.setPositiveButton("Save", (dialoiog, which) -> {
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
				inputDialog.setPositiveButton("Save", (diaulog, which) -> {
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
				inputDialog.setPositiveButton("Save", (dialoug, which) -> {
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
				inputDialog.setPositiveButton("Save", (dialogi, which) -> {
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
				case "custom":
				
				if ("android:padding_all".equals(attrName)) {
					showPaddingDialog(view);
					shouldShowDialog = false;
					return;
				} else if ("android:layout_margin_all".equals(attrName)) {
					showMarginDialog(view);
					shouldShowDialog = false;
					return;
				}
				break;
				default:
				//   SketchwareUtil.showMessage(getContext(), "No such type found");
				break;
			}
			if (shouldShowDialog) {
				inputDialog.show();
			}
		});
		
		// Listener item click listener
		listenerAdapter.setOnItemClickListener(position -> {
			DesignActivity designActivity = (DesignActivity) getContext();
			
			saveLayout(ProjectDataHelper.getActivityName(getContext()));
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
			widget_add_map.put("activityName", ProjectDataHelper.getActivityName(getContext()));
			
			
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
			ProjectDataHelper.getActivityName(getContext()) +
			event);
			intent.putExtra("sc_id", ProjectDataHelper.getScId(getContext()));
			intent.putExtra("activityName", ProjectDataHelper.getActivityName(getContext()));
			intent.putExtra("widgetId", idManager.getId(view));
			intent.putExtra("type", "");
			intent.setClass(getContext(), LogicEditorActivity.class);
			getContext().startActivity(intent);
		});
		
		
		bottomSheetDialog.show();
		
		// Set listeners for the view (same as before)
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
		
		// Fetch attributes
		AttributeSet attributeSet = attributesValueMap.get(view);
		String customWidgetClass = null;
		if (attributeSet != null) {
			Attribute convertAttr = attributeSet.getAttribute("app:convertedWidget");
			if (convertAttr != null) customWidgetClass = convertAttr.getValue();
		}
		
		// Tag name
		String tagName = (customWidgetClass != null) ? customWidgetClass : view.getClass().getSimpleName();
		if ("PlaceholderWebView".equals(tagName)) tagName = "WebView";
		
		sb.append("<").append(tagName);
		
		// Add attributes
		if (attributeSet != null) {
			for (Attribute attr : attributeSet.getAttributes()) {
				if (!"xmlns:android".equals(attr.getName()) && !"app:convertedWidget".equals(attr.getName())) {
					HashMap<String, Object> attrMap = findAttributeMap(attr.getName(), view);
					String value = formatAttributeValue(attr, attrMap);
					sb.append("\n    ").append(attr.getName()).append("=\"").append(value).append("\"");
				}
			}
		} else {
			// Default attributes
			sb.append("\n    android:layout_width=\"wrap_content\"");
			sb.append("\n    android:layout_height=\"wrap_content\"");
		}
		
		// Add namespace if needed
		if (customWidgetClass != null && !sb.toString().contains("xmlns:app")) {
			sb.append("\n    xmlns:app=\"http://schemas.android.com/apk/res-auto\"");
		}
		
		// Check if view is a ViewGroup
		if (view instanceof ViewGroup) {
			sb.append(">\n");
			ViewGroup group = (ViewGroup) view;
			
			for (int i = 0; i < group.getChildCount(); i++) {
				View child = group.getChildAt(i);
				// Skip placeholders if needed
				if (child == placeHolder) continue;
				
				String childXml = generateCode(child);
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
	
	private class DragListener implements View.OnDragListener {
		
		@Override
		public boolean onDrag(View destinationView, DragEvent event) {
			View draggedView = null;
			if (event.getLocalState() instanceof View) {
				draggedView = (View) event.getLocalState();
			}
			
			try {
				switch (event.getAction()) {
					
					case DragEvent.ACTION_DRAG_STARTED:
					log("drag started on " + destinationView);
					if (draggedView != null) {
						ViewGroupUtils.removeView(draggedView);
					}
					return true;
					
					case DragEvent.ACTION_DRAG_ENTERED:
					case DragEvent.ACTION_DRAG_LOCATION:
					log("drag entered/location on " + destinationView);
					addView(placeHolder, (ViewGroup) destinationView, event);
					return true;
					
					case DragEvent.ACTION_DRAG_EXITED:
					ViewGroupUtils.removeView(placeHolder);
					return true;
					
					case DragEvent.ACTION_DROP:
					if (draggedView == null) {
						// Create new widget from widget list
						try {
							HashMap<String, Object> viewData = (HashMap<String, Object>) event.getLocalState();
							View newView = ReflectionUtils.createView(getContext(), viewData.get("class_path").toString());
							
							// Set minimum size
							newView.setMinimumHeight((int) SketchwareUtil.getDip(getContext(), 30));
							newView.setMinimumWidth((int) SketchwareUtil.getDip(getContext(), 30));
							
							_rearrangeListener(newView);
							//newView.setOnClickListener(v -> showAttributesDialog(v));
							newView.setOnClickListener(v -> showAttributesDialog(v));
							
							// Remove placeholder and add new view
							ViewGroupUtils.removeView(placeHolder);
							addView(newView, (ViewGroup) destinationView, event);
							
							// Setup for ViewGroup
							if (newView instanceof ViewGroup) {
								newView.setOnDragListener(dragListener);
								int dp = (int) SketchwareUtil.getDip(getContext(), 8);
								newView.setPadding(dp, dp, dp, dp);
								com.shapun.layouteditor.utils.AnimationUtils.animate((ViewGroup)newView);
								newView.setBackground(UiUtils.createStrokedBackground(0, 0xFF000000, 1));
							}
							
							// ID & Attributes
							String newId = idManager.generateNewId(newView);
							idManager.addNewId(newView, newId);
							
							AttributeSet attributeSet = new AttributeSet();
							attributesValueMap.put(newView, attributeSet);
							attributeSet.add(new Attribute("android:id", "@+id/" + newId));
							
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
                            
                            
                            setDefaultTextSize(newView);
							
							// Save undo state
							if (!isUndoRedoInProgress) {
								saveStateForUndo("add_widget");
							}
							
							// Save temp layout
							ViewGroup rootView = (ViewGroup) editorLayout.getChildAt(0);
							String xmlCode = generateCode(rootView);
							tempLayoutManager.saveTempLayout(xmlCode);
							
							if (onWidgetAddListener != null) {
								onWidgetAddListener.onWidgetAdded(newView, (ViewGroup) destinationView);
							}
							
						} catch (Throwable t) {
							SketchwareUtil.showMessage(getContext(), "Create failed: " + t.getMessage());
						}
					} else {
						// Move existing view
						ViewGroupUtils.removeView(placeHolder);
						addView(draggedView, (ViewGroup) destinationView, event);
						
						if (!isUndoRedoInProgress) {
							saveStateForUndo("move_widget");
						}
					}
					return true;
					
					case DragEvent.ACTION_DRAG_ENDED:
					log("drag ended on " + destinationView);
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
		
		// --- Helper Methods ---
		
		private int getIndexForNewChildOfLinearLayout(LinearLayout linear, DragEvent dragEvent) {
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
		
		private int getGravityForNewChildOfFrameLayout(FrameLayout frameLayout, DragEvent event) {
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
			if (posY > height - childHeight) {
				gravity |= Gravity.BOTTOM;
			}
			return gravity;
		}
		
		private void addView(View view, ViewGroup destination, DragEvent event) {
			try {
				if (destination instanceof LinearLayout) {
					int index = getIndexForNewChildOfLinearLayout((LinearLayout) destination, event);
					ViewGroupUtils.addView(view, destination, index);
					return;
				}
				
				if (destination instanceof FrameLayout) {
					ViewGroupUtils.addView(view, destination);
					FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
					params.gravity = getGravityForNewChildOfFrameLayout((FrameLayout) destination, event);
					return;
				}
				
				ViewGroupUtils.addView(view, destination);
			} catch (Exception e) {
				SketchwareUtil.showMessage(getContext(), "Add failed: " + e.getMessage());
			}
		}
		
		private void addInitialAttributes(View view, HashMap<String, Object> map) {
			if (map.containsKey("initial_attributes")) {
				Map<String, String> initial_attributes = (Map<String, String>) map.get("initial_attributes");
				for (String key : initial_attributes.keySet()) {
					Attribute attr = new Attribute(key, initial_attributes.get(key));
					applyAttribute(view, attr);
					attributesValueMap.get(view).add(attr);
				}
			}
		}
	}
	
	public void applyAttribute(View view, Attribute attribute) {
		AttributeSet currentAttrSet = attributesValueMap.get(view);
		Attribute oldAttr = (currentAttrSet != null) ? currentAttrSet.getAttribute(attribute.getName()) : null;
		// Inside applyAttribute():
		if (!isUndoRedoInProgress) {
			saveStateForUndo("attribute_change_" + attribute.getName());
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
		
		if (currentAttrSet == null) {
			currentAttrSet = new AttributeSet();
			attributesValueMap.put(view, currentAttrSet);
		}
		currentAttrSet.add(attribute);
		
	}
	
	public void applyAttribute(View view, Attribute attribute, HashMap<String, Object> map) {
		if (!isUndoRedoInProgress) {
		}
		final String argument_type = map.get("argument_type").toString();
		String value = attribute.getValue();
		String attribute_name = attribute.getName();
		Object argumentValue = null;
		String member_name = map.get("member_name").toString();
		
		if ("app:convertedWidget".equals(attribute_name)) {
			handleWidgetConversion(view, value);
			return;
		}
		
		if (attribute_name.equals("android:layout_width") || attribute_name.equals("android:layout_height")) {
			
			ViewGroup.LayoutParams params = view.getLayoutParams();
			
			if (params == null) {
				params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT
				);
			}
			
			int pixelValue = parseDimension(value); 
			
			if (attribute_name.equals("android:layout_width")) {
				params.width = pixelValue;
			} else { 
				params.height = pixelValue;
			}
			
			view.setLayoutParams(params);
			view.requestLayout();
		}
		
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
				
				if ("android:textSize".equals(attribute_name)) {
					if (value != null && !value.isEmpty()) {
						try {
							if (value.endsWith("sp")) {
								value = value.replace("sp", "").trim();
								((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, Float.parseFloat(value));
								return;
							} else if (value.endsWith("dp")) {
								value = value.replace("dp", "").trim();
								((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_DIP, Float.parseFloat(value));
								return;
							} else {
								((TextView) view).setTextSize(Float.parseFloat(value));
								return;
							}
						} catch (Exception e) {
							SketchwareUtil.showMessage(getContext(), "Invalid textSize: " + value);
							return;
						}
					} else {
						// Default fallback if empty
						((TextView) view).setTextSize(12f);
						return;
					}
				}
				
				try {
					if (map.containsKey("dimension")) {
						argumentValue = (float) DimensionUtils.getValueInPx(getContext(), value);
					} else {
						argumentValue = Float.parseFloat(value);
					}
				} catch (Exception e) {
					SketchwareUtil.showMessage(getContext(), "Invalid float value: " + value);
					return;
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
				String path = FileUtil.getExternalStorageDir() + "/.blacklogics/resources/images/" + ProjectDataHelper.getScId(getContext()) + "/" +
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
				
				//	SketchwareUtil.showMessage(getContext(), "Directory creation failed: " + file.getParent());
				
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
							
							applyContainerStroke(view);
							
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
			
			//SketchwareUtil.showMessage(getContext(), "Load error: " + e.getMessage());
			
			Log.e("ViewEditor", "loadLayout() failed", e);
			
		}
		
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
				unselectSelectedWidget();
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
		unselectSelectedWidget();
		LinearLayoutCompat base = attributesContainer;
		anim.setTarget(base);
		anim.setProperty(View.TRANSLATION_Y);
		anim.setFloatValues(new float[]{(float) base.getHeight()});
		anim.setInterpolator(new DecelerateInterpolator());
		anim.start();
	}
	
	public void unselectSelectedWidget()
	{
		if (selectedView != null) {
			// Save the current applied color/background before resetting
			AttributeSet attrSet = attributesValueMap.get(selectedView);
			if (attrSet != null) {
				Attribute bgColorAttr = attrSet.getAttribute("android:background");
				if (bgColorAttr != null) {
					// Apply the saved background color instead of original
					String colorValue = bgColorAttr.getValue();
					try {
						int color = Color.parseColor(colorValue);
						selectedView.setBackgroundColor(color);
					} catch (Exception e) {
						// If it's not a color, keep the current background
					}
				} else {
					// No background attribute, reset to original
					Drawable original = originalBackgrounds.get(selectedView);
					if (original != null) {
						selectedView.setBackground(original);
					} else {
						selectedView.setBackground(null);
					}
				}
			}
			selectedView = null;
		}
	}
	
	public boolean isHiddenProperties() {
		return attributesContainer.getTranslationY() == ((float) attributesContainer.getHeight());
	}
	
	public boolean undo() {
		if (undoStack.isEmpty()) {
			SketchwareUtil.showMessage(getContext(), "Nothing to undo");
			return false;
		}
		
		isUndoRedoInProgress = true;
		try {
			EditorAction action = undoStack.pop();
			redoStack.push(createCurrentStateAction());
			
			// Restore from XML snapshot for reliable undo
			if (action.xmlSnapshot != null && !action.xmlSnapshot.isEmpty()) {
				restoreFromXmlSnapshot(action.xmlSnapshot);
			} else {
				// Fallback to individual action handling
				performUndoAction(action);
			}
			
			SketchwareUtil.showMessage(getContext(), "Undo completed");
			return true;
		} catch (Exception e) {
			SketchwareUtil.showMessage(getContext(), "Undo error: " + e.getMessage());
			Log.e("ViewEditor", "Undo failed", e);
			return false;
		} finally {
			isUndoRedoInProgress = false;
		}
	}
	
	// Improved redo method
	public boolean redo() {
		if (redoStack.isEmpty()) {
			SketchwareUtil.showMessage(getContext(), "Nothing to redo");
			return false;
		}
		
		isUndoRedoInProgress = true;
		try {
			EditorAction action = redoStack.pop();
			undoStack.push(createCurrentStateAction());
			
			if (action.xmlSnapshot != null && !action.xmlSnapshot.isEmpty()) {
				restoreFromXmlSnapshot(action.xmlSnapshot);
			} else {
				performRedoAction(action);
			}
			
			SketchwareUtil.showMessage(getContext(), "Redo completed");
			return true;
		} catch (Exception e) {
			SketchwareUtil.showMessage(getContext(), "Redo error: " + e.getMessage());
			Log.e("ViewEditor", "Redo failed", e);
			return false;
		} finally {
			isUndoRedoInProgress = false;
		}
	}
	
	private EditorAction createCurrentStateAction() {
		String xmlState = getXMLCode();
		return new EditorAction(
		ACTION_UPDATE_ATTR, null, null, -1,
		null, null, getCurrentActivityName(), xmlState
		);
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
	
	// Returns a map of id -> full class name (TextView, LinearLayout, etc.)
	public static Map<String, String> getIdsWithClass(String type, String activityName) {
		Map<String, String> ids = new LinkedHashMap<>(); // maintain order
		
		String layoutFilePath = SAVE_PATH + "/root_layout.json";
		if (!FileUtil.isExistFile(layoutFilePath)) {
			Log.e("ViewEditor", "Layout file not found: " + layoutFilePath);
			return ids;
		}
		
		try {
			ArrayList<HashMap<String, Object>> layoutList = new Gson().fromJson(
			FileUtil.readFile(layoutFilePath),
			new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType()
			);
			
			HashMap<String, Object> targetLayout = null;
			for (HashMap<String, Object> layout : layoutList) {
				if (activityName.equals(layout.get("name"))) {
					targetLayout = layout;
					break;
				}
			}
			
			if (targetLayout == null) {
				Log.e("ViewEditor", "Layout not found: " + activityName);
				return ids;
			}
			
			String xmlContent = (String) targetLayout.get("xml");
			
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(new StringReader(xmlContent));
			
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					String tagName = parser.getName(); // full XML class name
					String viewId = null;
					
					for (int i = 0; i < parser.getAttributeCount(); i++) {
						if ("android:id".equals(parser.getAttributeName(i))) {
							viewId = AttributeUtils.getName(parser.getAttributeValue(i));
							break;
						}
					}
					
					if (viewId != null && !viewId.isEmpty()) {
						if ("all".equalsIgnoreCase(type) || tagName.equalsIgnoreCase(type)) {
							ids.put(viewId, tagName); // store full class name
						}
					}
				}
				eventType = parser.next();
			}
			
		} catch (Exception e) {
			Log.e("ViewEditor", "Error loading IDs by type: " + e.getMessage(), e);
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
				if (view != null) {
					setDefaultTextSize(view); // Add this
					return view;
				}
				view = ReflectionUtils.createView(getContext(), tag);
				if (view != null) {
					setDefaultTextSize(view); // Add this
					return view;
				}
			} catch (ClassNotFoundException |
			InstantiationException |
			InvocationTargetException |
			NoSuchMethodException |
			IllegalAccessException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
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
		// This should only be called from UI thread
		if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
			// If called from background thread, post to main thread
			post(() -> {
				idManager = oldIdManager;
				attributesValueMap = oldAttributesValueMap;
				editorLayout.removeAllViews();
				for (View v : oldAttributesValueMap.keySet()) {
					if (v.getParent() == null) editorLayout.addView(v);
				}
			});
			return;
		}
		
		// Original implementation for UI thread
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
	
	private void handleWidgetConversion(View originalView, String customClassName) {
		try {
			if (customClassName == null || customClassName.trim().isEmpty()) {
				return;
			}
			
			
			// Store original attributes
			AttributeSet originalAttributes = attributesValueMap.get(originalView);
			String originalId = idManager.getId(originalView);
			ViewGroup parent = (ViewGroup) originalView.getParent();
			int index = parent.indexOfChild(originalView);
			
			// Create custom widget
			View customView;
			try {
				// Try to create from full class path
				customView = ReflectionUtils.createView(getContext(), customClassName);
			} catch (Exception e) {
				try {
					// Try with android.widget prefix
					customView = ReflectionUtils.createView(getContext(), "android.widget." + customClassName);
				} catch (Exception e2) {
					try {
						// Try with androidx prefix
						customView = ReflectionUtils.createView(getContext(), "androidx.appcompat.widget." + customClassName);
					} catch (Exception e3) {
						//	SketchwareUtil.showMessage(getContext(), "Cannot create widget: " + customClassName);
						return;
					}
				}
			}
			
			if (customView != null) {
				// Copy layout parameters
				ViewGroup.LayoutParams params = originalView.getLayoutParams();
				customView.setLayoutParams(params);
				
				// Copy basic properties
				customView.setPadding(
				originalView.getPaddingLeft(),
				originalView.getPaddingTop(),
				originalView.getPaddingRight(),
				originalView.getPaddingBottom()
				);
				
				// Apply original attributes to new widget
				if (originalAttributes != null) {
					for (Attribute attr : originalAttributes.getAttributes()) {
						if (!"app:convertedWidget".equals(attr.getName())) {
							applyAttribute(customView, attr);
						}
					}
				}
				
				// Set up listeners and ID
				_rearrangeListener(customView);
				customView.setOnClickListener(v -> showAttributesDialog(v));
				if (customView instanceof ViewGroup) {
					customView.setOnDragListener(dragListener);
				}
				
				idManager.addNewId(customView, originalId);
				attributesValueMap.put(customView, originalAttributes);
				
				// Replace view in parent
				parent.removeViewAt(index);
				parent.addView(customView, index);
				
				String activityName = getCurrentActivityName();
				String widgetType = getWidgetTypeName(customView);
				saveWidgetInfo(activityName, widgetType, originalId);
				
				//SketchwareUtil.showMessage(getContext(), "Converted to: " + customClassName);
				
				if (!isUndoRedoInProgress) {
					saveStateForUndo("convert_widget");
				}
				
				// Save temp layout
				ViewGroup rootView = (ViewGroup) editorLayout.getChildAt(0);
				String xmlCode = generateCode(rootView);
				tempLayoutManager.saveTempLayout(xmlCode);
			}
			
		} catch (Exception e) {
			SketchwareUtil.showMessage(getContext(), "Conversion error: " + e.getMessage());
			Log.e("ViewEditor", "Widget conversion failed", e);
		}
	}
	
	private void showConvertWidgetDialog(final View view) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
		dialog.setTitle("Convert Widget");
		
		LinearLayout layout = new LinearLayout(getContext());
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setPadding(50, 30, 50, 30);
		
		TextView hintText = new TextView(getContext());
		hintText.setText("Enter custom widget class name:\nExamples: MaterialButton, CardView, SearchView");
		hintText.setTextSize(12);
		hintText.setTextColor(0xFF666666);
		hintText.setPadding(0, 0, 0, 20);
		
		final EditText editText = new EditText(getContext());
		editText.setHint("android.widget.SearchView");
		editText.setSingleLine(true);
		
		// Show current converted widget if any
		attrSet = attributesValueMap.get(view);
		if (attrSet != null) {
			Attribute convertAttr = attrSet.getAttribute("app:convertedWidget");
			if (convertAttr != null) {
				editText.setText(convertAttr.getValue());
			}
		}
		
		layout.addView(hintText);
		layout.addView(editText);
		
		dialog.setView(layout);
		dialog.setPositiveButton("Convert", (d, which) -> {
			String className = editText.getText().toString().trim();
			if (!className.isEmpty()) {
				// Create or update the conversion attribute
				Attribute convertAttr = new Attribute("app:convertedWidget", className);
				if (attrSet == null) {
					attrSet = new AttributeSet();
					attributesValueMap.put(view, attrSet);
				}
				attrSet.add(convertAttr);
				
				// Apply the conversion
				handleWidgetConversion(view, className);
			}
		});
		dialog.setNegativeButton("Cancel", null);
		/*	dialog.setNeutralButton("Reset", (d, which) -> {
// Remove conversion and restore original view
if (attrSet != null) {
attrSet.remove("app:convertedWidget");
}
SketchwareUtil.showMessage(getContext(), "Widget conversion reset");
});*/		
		dialog.show();
	}
	
	private String[] getCommonWidgetSuggestions() {
		return new String[]{
			"android.widget.SearchView",
			"androidx.cardview.widget.CardView",
			"com.google.android.material.button.MaterialButton",
			"com.google.android.material.card.MaterialCardView",
			"com.google.android.material.textfield.TextInputLayout",
			"androidx.appcompat.widget.Toolbar",
			"androidx.recyclerview.widget.RecyclerView",
			"androidx.viewpager2.widget.ViewPager2",
			"android.widget.ProgressBar",
			"android.widget.RatingBar"
		};
	}
	private void setupCheckboxToggle(CheckBox checkBox, EditText editText, EditText[] linkedEditTexts) {
		checkBox.setChecked(true); // Default checked
		
		checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
			editText.setEnabled(isChecked);
			editText.setVisibility(isChecked ? View.VISIBLE : View.GONE);
			
			// If this is the "All" checkbox, enable/disable all linked fields
			if (linkedEditTexts != null) {
				for (EditText linkedEt : linkedEditTexts) {
					linkedEt.setEnabled(isChecked);
					linkedEt.setVisibility(isChecked ? View.VISIBLE : View.GONE);
				}
			}
		});
	}
	
	private void applyPaddingToView(View view, EditText etLeft, EditText etTop, EditText etRight, EditText etBottom,
	CheckBox cbLeft, CheckBox cbTop, CheckBox cbRight, CheckBox cbBottom) {
		try {
			int left = cbLeft.isChecked() && !TextUtils.isEmpty(etLeft.getText()) ? 
			(int) SketchwareUtil.getDip(getContext(), Integer.parseInt(etLeft.getText().toString())) : view.getPaddingLeft();
			
			int top = cbTop.isChecked() && !TextUtils.isEmpty(etTop.getText()) ? 
			(int) SketchwareUtil.getDip(getContext(), Integer.parseInt(etTop.getText().toString())) : view.getPaddingTop();
			
			int right = cbRight.isChecked() && !TextUtils.isEmpty(etRight.getText()) ? 
			(int) SketchwareUtil.getDip(getContext(), Integer.parseInt(etRight.getText().toString())) : view.getPaddingRight();
			
			int bottom = cbBottom.isChecked() && !TextUtils.isEmpty(etBottom.getText()) ? 
			(int) SketchwareUtil.getDip(getContext(), Integer.parseInt(etBottom.getText().toString())) : view.getPaddingBottom();
			
			view.setPadding(left, top, right, bottom);
			view.requestLayout();
			
			// Save to attributes
			AttributeSet attrSet = attributesValueMap.get(view);
			if (attrSet == null) {
				attrSet = new AttributeSet();
				attributesValueMap.put(view, attrSet);
			}
			
			// Save individual padding attributes
			if (cbLeft.isChecked()) {
				attrSet.add(new Attribute("android:paddingLeft", etLeft.getText().toString() + "dp"));
			}
			if (cbTop.isChecked()) {
				attrSet.add(new Attribute("android:paddingTop", etTop.getText().toString() + "dp"));
			}
			if (cbRight.isChecked()) {
				attrSet.add(new Attribute("android:paddingRight", etRight.getText().toString() + "dp"));
			}
			if (cbBottom.isChecked()) {
				attrSet.add(new Attribute("android:paddingBottom", etBottom.getText().toString() + "dp"));
			}
			
		} catch (NumberFormatException e) {
			SketchwareUtil.showMessage(getContext(), "Invalid padding values");
		}
	}
	
	private void applyMarginToView(View view, EditText etLeft, EditText etTop, EditText etRight, EditText etBottom,
	CheckBox cbLeft, CheckBox cbTop, CheckBox cbRight, CheckBox cbBottom) {
		try {
			ViewGroup.LayoutParams params = view.getLayoutParams();
			if (params instanceof ViewGroup.MarginLayoutParams) {
				ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) params;
				
				marginParams.leftMargin = cbLeft.isChecked() && !TextUtils.isEmpty(etLeft.getText()) ? 
				(int) SketchwareUtil.getDip(getContext(), Integer.parseInt(etLeft.getText().toString())) : marginParams.leftMargin;
				
				marginParams.topMargin = cbTop.isChecked() && !TextUtils.isEmpty(etTop.getText()) ? 
				(int) SketchwareUtil.getDip(getContext(), Integer.parseInt(etTop.getText().toString())) : marginParams.topMargin;
				
				marginParams.rightMargin = cbRight.isChecked() && !TextUtils.isEmpty(etRight.getText()) ? 
				(int) SketchwareUtil.getDip(getContext(), Integer.parseInt(etRight.getText().toString())) : marginParams.rightMargin;
				
				marginParams.bottomMargin = cbBottom.isChecked() && !TextUtils.isEmpty(etBottom.getText()) ? 
				(int) SketchwareUtil.getDip(getContext(), Integer.parseInt(etBottom.getText().toString())) : marginParams.bottomMargin;
				
				view.setLayoutParams(marginParams);
				
				// Save to attributes
				AttributeSet attrSet = attributesValueMap.get(view);
				if (attrSet == null) {
					attrSet = new AttributeSet();
					attributesValueMap.put(view, attrSet);
				}
				
				// Save individual margin attributes
				if (cbLeft.isChecked()) {
					attrSet.add(new Attribute("android:layout_marginLeft", etLeft.getText().toString() + "dp"));
				}
				if (cbTop.isChecked()) {
					attrSet.add(new Attribute("android:layout_marginTop", etTop.getText().toString() + "dp"));
				}
				if (cbRight.isChecked()) {
					attrSet.add(new Attribute("android:layout_marginRight", etRight.getText().toString() + "dp"));
				}
				if (cbBottom.isChecked()) {
					attrSet.add(new Attribute("android:layout_marginBottom", etBottom.getText().toString() + "dp"));
				}
			}
		} catch (NumberFormatException e) {
			SketchwareUtil.showMessage(getContext(), "Invalid margin values");
		}
	}
	private void showPaddingDialog(final View view) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
		dialog.setTitle("Padding Settings");
		
		// Inflate custom layout
		View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dimension_settings, null);
		dialog.setView(dialogView);
		
		// Initialize views
		final EditText etAll = dialogView.findViewById(R.id.et_all);
		final CheckBox cbAll = dialogView.findViewById(R.id.cb_all);
		
		final EditText etLeft = dialogView.findViewById(R.id.et_left);
		final CheckBox cbLeft = dialogView.findViewById(R.id.cb_left);
		
		final EditText etTop = dialogView.findViewById(R.id.et_top);
		final CheckBox cbTop = dialogView.findViewById(R.id.cb_top);
		
		final EditText etRight = dialogView.findViewById(R.id.et_right);
		final CheckBox cbRight = dialogView.findViewById(R.id.cb_right);
		
		final EditText etBottom = dialogView.findViewById(R.id.et_bottom);
		final CheckBox cbBottom = dialogView.findViewById(R.id.cb_bottom);
		
		final TextView tvTitle = dialogView.findViewById(R.id.tv_title);
		tvTitle.setText("Padding (in dp)");
		
		// Load current padding values
		etLeft.setText(String.valueOf((int) SketchwareUtil.pxToDp(getContext(), view.getPaddingLeft())));
		etTop.setText(String.valueOf((int) SketchwareUtil.pxToDp(getContext(), view.getPaddingTop())));
		etRight.setText(String.valueOf((int) SketchwareUtil.pxToDp(getContext(), view.getPaddingRight())));
		etBottom.setText(String.valueOf((int) SketchwareUtil.pxToDp(getContext(), view.getPaddingBottom())));
		
		// Checkbox listeners for show/hide
		setupCheckboxToggle(cbAll, etAll, new EditText[]{etLeft, etTop, etRight, etBottom});
		setupCheckboxToggle(cbLeft, etLeft, null);
		setupCheckboxToggle(cbTop, etTop, null);
		setupCheckboxToggle(cbRight, etRight, null);
		setupCheckboxToggle(cbBottom, etBottom, null);
		
		// All padding field - when changed, update all fields
		etAll.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				if (cbAll.isChecked() && !TextUtils.isEmpty(s)) {
					etLeft.setText(s.toString());
					etTop.setText(s.toString());
					etRight.setText(s.toString());
					etBottom.setText(s.toString());
				}
			}
		});
		
		dialog.setPositiveButton("Apply", (d, which) -> {
			applyPaddingToView(view, etLeft, etTop, etRight, etBottom, cbLeft, cbTop, cbRight, cbBottom);
			saveStateForUndo("change_padding");
		});
		
		dialog.setNegativeButton("Cancel", null);
		dialog.show();
	}
	private void showMarginDialog(final View view) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
		dialog.setTitle("Margin Settings");
		
		// Inflate custom layout
		View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dimension_settings, null);
		dialog.setView(dialogView);
		
		// Initialize views
		final EditText etAll = dialogView.findViewById(R.id.et_all);
		final CheckBox cbAll = dialogView.findViewById(R.id.cb_all);
		
		final EditText etLeft = dialogView.findViewById(R.id.et_left);
		final CheckBox cbLeft = dialogView.findViewById(R.id.cb_left);
		
		final EditText etTop = dialogView.findViewById(R.id.et_top);
		final CheckBox cbTop = dialogView.findViewById(R.id.cb_top);
		
		final EditText etRight = dialogView.findViewById(R.id.et_right);
		final CheckBox cbRight = dialogView.findViewById(R.id.cb_right);
		
		final EditText etBottom = dialogView.findViewById(R.id.et_bottom);
		final CheckBox cbBottom = dialogView.findViewById(R.id.cb_bottom);
		
		final TextView tvTitle = dialogView.findViewById(R.id.tv_title);
		tvTitle.setText("Margin (in dp)");
		
		// Load current margin values
		ViewGroup.LayoutParams params = view.getLayoutParams();
		if (params instanceof ViewGroup.MarginLayoutParams) {
			ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) params;
			etLeft.setText(String.valueOf((int) SketchwareUtil.pxToDp(getContext(), marginParams.leftMargin)));
			etTop.setText(String.valueOf((int) SketchwareUtil.pxToDp(getContext(), marginParams.topMargin)));
			etRight.setText(String.valueOf((int) SketchwareUtil.pxToDp(getContext(), marginParams.rightMargin)));
			etBottom.setText(String.valueOf((int) SketchwareUtil.pxToDp(getContext(), marginParams.bottomMargin)));
		}
		
		// Checkbox listeners
		setupCheckboxToggle(cbAll, etAll, new EditText[]{etLeft, etTop, etRight, etBottom});
		setupCheckboxToggle(cbLeft, etLeft, null);
		setupCheckboxToggle(cbTop, etTop, null);
		setupCheckboxToggle(cbRight, etRight, null);
		setupCheckboxToggle(cbBottom, etBottom, null);
		
		// All margin field - when changed, update all fields
		etAll.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				if (cbAll.isChecked() && !TextUtils.isEmpty(s)) {
					etLeft.setText(s.toString());
					etTop.setText(s.toString());
					etRight.setText(s.toString());
					etBottom.setText(s.toString());
				}
			}
		});
		
		dialog.setPositiveButton("Apply", (d, which) -> {
			applyMarginToView(view, etLeft, etTop, etRight, etBottom, cbLeft, cbTop, cbRight, cbBottom);
			saveStateForUndo("change_margin");
		});
		
		dialog.setNegativeButton("Cancel", null);
		dialog.show();
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
	
	// इसे ViewEditor क्लास के अंदर जोड़ें
	public int parseDimension(String dimensionValue) {
		if (dimensionValue == null || dimensionValue.isEmpty()) {
			return ViewGroup.LayoutParams.WRAP_CONTENT;
		}
		
		// 'match_parent' और 'wrap_content' को Android constants में बदलें
		if (dimensionValue.equalsIgnoreCase("match_parent")) {
			return ViewGroup.LayoutParams.MATCH_PARENT;
		}
		if (dimensionValue.equalsIgnoreCase("wrap_content")) {
			return ViewGroup.LayoutParams.WRAP_CONTENT;
		}
		
		// "100dp" जैसी वैल्यू को हैंडल करें
		String lowerCaseValue = dimensionValue.toLowerCase();
		
		if (lowerCaseValue.endsWith("dp")) {
			try {
				// 'dp' हटाकर वैल्यू को float में पार्स करें
				float value = Float.parseFloat(lowerCaseValue.substring(0, lowerCaseValue.length() - 2));
				
				// TypedValue का उपयोग करके dp को वर्तमान स्क्रीन के px में बदलें
				return (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP,
				value,
				getContext().getResources().getDisplayMetrics()
				);
			} catch (NumberFormatException e) {
				// अगर पार्सिंग में दिक्कत हो तो default wrap_content दें
				return ViewGroup.LayoutParams.WRAP_CONTENT; 
			}
		}
		
		// अगर कोई यूनिट न हो, तो भी WRAP_CONTENT default करें
		return ViewGroup.LayoutParams.WRAP_CONTENT; 
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
	}
	
	private void setupClickListener(View view) {
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showAttributesDialog(v); // Attributes dialog
				Log.d("ViewEditor", "onClick triggered on normal view: " + view.getClass().getSimpleName());
			}
		});
	}
	
	// Helper method to set default text size for TextView and its subclasses
	private void setDefaultTextSize(View view) {
		if (view instanceof TextView) {
			((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		}
	}
	public Map<String, List<String>> getLiveWidgetsByType() {
		Map<String, List<String>> widgets = new HashMap<>(); // type -> List<ids>
		
		
		ViewGroup root = (ViewGroup) editorLayout.getChildAt(0);
		if (root == null) return widgets;
		
		collectWidgetsRecursive(root, widgets);
		return widgets;
	}
	
	private void collectWidgetsRecursive(ViewGroup parent, Map<String, List<String>> widgets) {
		for (int i = 0; i < parent.getChildCount(); i++) {
			View child = parent.getChildAt(i);
			String id = idManager.getId(child); // Live ID
			if (id != null && !id.isEmpty()) {
				String type = getWidgetTypeFromView(child);
				widgets.computeIfAbsent(type, k -> new ArrayList<>()).add(id);
			}
			
			if (child instanceof ViewGroup) {
				collectWidgetsRecursive((ViewGroup) child, widgets);
			}
		}
	}
	
	private String getWidgetTypeFromView(View view) {
		AttributeSet attrSet = attributesValueMap.get(view);
		if (attrSet != null) {
			// "app:convertedWidget" attribute check (jaise tumhare convert dialog mein set hota hai)
			Attribute convertAttr = attrSet.getAttribute("app:convertedWidget");
			if (convertAttr != null && !convertAttr.getValue().trim().isEmpty()) {
				String fullClassName = convertAttr.getValue().trim(); // e.g., "android.widget.SearchView"
				// Simple name extract karo (last part after dot)
				int lastDot = fullClassName.lastIndexOf('.');
				if (lastDot != -1 && lastDot < fullClassName.length() - 1) {
					return fullClassName.substring(lastDot + 1); // "SearchView"
				}
				return fullClassName; // Agar package nahi to full
			}
		}
		
		// Step 2: Default - view class ka simple name
		String simpleName = view.getClass().getSimpleName();
		if (simpleName == null || simpleName.isEmpty()) {
			return "View"; // Fallback
		}
		
		// "Widget" prefix hatao (jaise WidgetButton -> Button)
		if (simpleName.startsWith("Widget")) {
			simpleName = simpleName.substring(6); // 6 chars after "Widget"
			if (simpleName.isEmpty()) simpleName = "View";
		}
		
		// Step 3: Common aliases handle (optional, aur types add kar sakte ho)
		switch (simpleName.toLowerCase()) {
			case "imagebutton": return "ImageButton";
			case "linearlayoutcompat": return "LinearLayout"; // Compat classes normalize
			case "constraintlayout": return "ConstraintLayout";
			default: return simpleName;
		}
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
	public void removeViewWithUndo(View view) {
		if (!isUndoRedoInProgress) {
			ViewGroup parent = (ViewGroup) view.getParent();
			int index = parent.indexOfChild(view);
			String viewId = idManager.getId(view);
			AttributeSet attributes = attributesValueMap.get(view);
			
			EditorAction action = new EditorAction(
			ACTION_REMOVE_VIEW, view, parent, index,
			attributes, null, getCurrentActivityName(), null
			);
			undoStack.push(action);
			redoStack.clear();
		}
		
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}
		idManager.remove(view);
		attributesValueMap.remove(view);
	}
	
	// Clear undo/redo history
	public void clearUndoRedoHistory() {
		undoStack.clear();
		redoStack.clear();
	}
	
	// Get undo/redo status
	public boolean canUndo() {
		return !undoStack.isEmpty();
	}
	
	public boolean canRedo() {
		return !redoStack.isEmpty();
	}
	
	// Get stack sizes for debugging
	public int getUndoStackSize() {
		return undoStack.size();
	}
	
	public int getRedoStackSize() {
		return redoStack.size();
	}
	
	public void saveStateForUndo(String actionType) {
		if (isUndoRedoInProgress) return;
		
		try {
			String xmlState = getXMLCode();
			EditorAction action = new EditorAction(
			ACTION_UPDATE_ATTR, null, null, -1,
			null, null, getCurrentActivityName(), xmlState
			);
			undoStack.push(action);
			redoStack.clear();
			
			// Limit stack size to prevent memory issues
			if (undoStack.size() > 50) {
				undoStack.remove(0);
			}
		} catch (Exception e) {
			Log.e("ViewEditor", "Error saving undo state: " + e.getMessage());
		}
	}
	
	private void performUndoAction(EditorAction action) {
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
				action.parent.addView(action.view, Math.min(action.index, action.parent.getChildCount()));
				idManager.addNewId(action.view, action.viewId);
				
				if (action.oldAttributes != null) {
					attributesValueMap.put(action.view, action.oldAttributes);
					for (Attribute attr : action.oldAttributes.getAttributes()) {
						applyAttribute(action.view, attr);
					}
				}
			}
			break;
			
			case ACTION_UPDATE_ATTR:
			if (action.view != null && action.oldAttributes != null) {
				attributesValueMap.put(action.view, action.oldAttributes);
				for (Attribute attr : action.oldAttributes.getAttributes()) {
					applyAttribute(action.view, attr);
				}
			}
			break;
		}
	}
	
	private View createViewFromTag(String tagName) {
		try {
			// Handle custom widgets
			if (tagName.contains(".")) {
				return ReflectionUtils.createView(getContext(), tagName);
			} else {
				return ReflectionUtils.createView(getContext(), "android.widget." + tagName);
			}
		} catch (Exception e) {
			Log.e("ViewEditor", "Failed to create view for tag: " + tagName, e);
			return null;
		}
	}
	private void restoreFromXmlSnapshot(String xmlSnapshot) {
		try {
			// Clear current layout
			editorLayout.removeAllViews();
			idManager = new IdManager();
			attributesValueMap.clear();
			
			// Parse and rebuild from XML
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(new StringReader(xmlSnapshot));
			
			ArrayList<View> viewStack = new ArrayList<>();
			viewStack.add(editorLayout);
			
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					String tagName = parser.getName();
					if ("LinearLayout".equals(tagName) && parser.getDepth() == 2) {
						// Skip the root LinearLayout we automatically add
						eventType = parser.next();
						continue;
					}
					
					View view = createViewFromTag(tagName);
					if (view != null) {
						setupImportedView(view);
						
						AttributeSet attrSet = new AttributeSet();
						attributesValueMap.put(view, attrSet);
						
						// Parse attributes
						for (int i = 0; i < parser.getAttributeCount(); i++) {
							String attrName = parser.getAttributeName(i);
							String attrValue = parser.getAttributeValue(i);
							
							if ("android:id".equals(attrName)) {
								String id = AttributeUtils.getName(attrValue);
								idManager.addNewId(view, id);
							}
							
							attrSet.add(new Attribute(attrName, attrValue));
						}
						
						viewStack.add(view);
					}
				} else if (eventType == XmlPullParser.END_TAG && viewStack.size() > 1) {
					View child = viewStack.remove(viewStack.size() - 1);
					ViewGroup parent = (ViewGroup) viewStack.get(viewStack.size() - 1);
					
					// Add to appropriate parent
					if (parent == editorLayout) {
						// Add to main editor layout
						editorLayout.addView(child);
					} else {
						// Add to parent view group
						parent.addView(child);
					}
					
					// Apply attributes
					AttributeSet childAttrSet = attributesValueMap.get(child);
					if (childAttrSet != null) {
						for (Attribute attr : childAttrSet.getAttributes()) {
							applyAttribute(child, attr);
						}
					}
				}
				
				eventType = parser.next();
			}
			
		} catch (Exception e) {
			Log.e("ViewEditor", "Error restoring from XML snapshot", e);
			throw new RuntimeException("Failed to restore layout", e);
		}
	}
	
	private void performRedoAction(EditorAction action) {
		switch (action.actionType) {
			case ACTION_ADD_VIEW:
			if (action.parent != null && action.view != null) {
				action.parent.addView(action.view, Math.min(action.index, action.parent.getChildCount()));
				idManager.addNewId(action.view, action.viewId);
				
				if (action.newAttributes != null) {
					attributesValueMap.put(action.view, action.newAttributes);
					for (Attribute attr : action.newAttributes.getAttributes()) {
						applyAttribute(action.view, attr);
					}
				}
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
			if (action.view != null && action.newAttributes != null) {
				attributesValueMap.put(action.view, action.newAttributes);
				for (Attribute attr : action.newAttributes.getAttributes()) {
					applyAttribute(action.view, attr);
				}
			}
			break;
		}
	}
	
	private void applyContainerStroke(View view) {
		if (view instanceof ViewGroup) {
			int dp = (int) SketchwareUtil.getDip(getContext(), 1);
			view.setBackground(UiUtils.createStrokedBackground(0, 0xFF000000, dp));
		}
	}
	private void showMaterialWidgetDialog(Context context, ArrayList<HashMap<String, Object>> viewsList) {
		
		LinearLayout layout = new LinearLayout(context);
		layout.setOrientation(LinearLayout.VERTICAL);
		int pad = (int) (20 * context.getResources().getDisplayMetrics().density);
		layout.setPadding(pad, pad, pad, pad);
		
		TextInputLayout inputLayout = new TextInputLayout(context, null, com.google.android.material.R.attr.textInputOutlinedStyle);
		inputLayout.setHint("Enter or choose widget class path");
		inputLayout.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE);
		inputLayout.setBoxCornerRadii(14, 14, 14, 14);
		
		MaterialAutoCompleteTextView autoCompleteTextView = new MaterialAutoCompleteTextView(context);
		autoCompleteTextView.setHint("e.g. android.widget.Button");
		
		ArrayList<String> suggestions = new ArrayList<>();
		
		suggestions.add("android.widget.Button");
		suggestions.add("android.widget.TextView");
		suggestions.add("android.widget.EditText");
		suggestions.add("android.widget.ImageView");
		suggestions.add("android.widget.ImageButton");
		suggestions.add("android.widget.CheckBox");
		suggestions.add("android.widget.CompoundButton");
		suggestions.add("android.widget.Switch");
		suggestions.add("android.widget.RadioButton");
		suggestions.add("android.widget.ToggleButton");
		suggestions.add("android.widget.SeekBar");
		suggestions.add("android.widget.ProgressBar");
		suggestions.add("android.widget.RatingBar");
		suggestions.add("android.widget.Spinner");
		suggestions.add("android.widget.AutoCompleteTextView");
		suggestions.add("android.widget.MultiAutoCompleteTextView");
		suggestions.add("android.widget.ListView");
		suggestions.add("android.widget.ExpandableListView");
		suggestions.add("android.widget.GridView");
		suggestions.add("android.widget.ScrollView");
		suggestions.add("android.widget.HorizontalScrollView");
		suggestions.add("android.widget.LinearLayout");
		suggestions.add("android.widget.RelativeLayout");
		suggestions.add("android.widget.FrameLayout");
		suggestions.add("android.widget.TableLayout");
		suggestions.add("android.widget.TableRow");
		suggestions.add("android.widget.ViewFlipper");
		suggestions.add("android.widget.ViewAnimator");
		suggestions.add("android.widget.ViewSwitcher");
		suggestions.add("android.widget.ViewStub");
		suggestions.add("android.widget.VideoView");
		suggestions.add("android.widget.Chronometer");
		suggestions.add("android.widget.DatePicker");
		suggestions.add("android.widget.TimePicker");
		suggestions.add("android.widget.NumberPicker");
		suggestions.add("android.widget.SearchView");
		suggestions.add("android.widget.Space");
		suggestions.add("android.widget.TextClock");
		
		suggestions.add("androidx.appcompat.widget.AppCompatButton");
		suggestions.add("androidx.appcompat.widget.AppCompatTextView");
		suggestions.add("androidx.appcompat.widget.AppCompatEditText");
		suggestions.add("androidx.appcompat.widget.AppCompatImageView");
		suggestions.add("androidx.appcompat.widget.AppCompatCheckBox");
		suggestions.add("androidx.appcompat.widget.AppCompatRadioButton");
		suggestions.add("androidx.appcompat.widget.AppCompatSpinner");
		suggestions.add("androidx.cardview.widget.CardView");
		suggestions.add("androidx.recyclerview.widget.RecyclerView");
		suggestions.add("androidx.viewpager.widget.ViewPager");
		suggestions.add("androidx.viewpager2.widget.ViewPager2");
		suggestions.add("androidx.constraintlayout.widget.ConstraintLayout");
		suggestions.add("androidx.drawerlayout.widget.DrawerLayout");
		suggestions.add("androidx.swiperefreshlayout.widget.SwipeRefreshLayout");
		suggestions.add("androidx.coordinatorlayout.widget.CoordinatorLayout");
		suggestions.add("androidx.gridlayout.widget.GridLayout");
		suggestions.add("androidx.appcompat.widget.Toolbar");
		suggestions.add("androidx.core.widget.NestedScrollView");
		
		suggestions.add("com.google.android.material.button.MaterialButton");
		suggestions.add("com.google.android.material.textfield.TextInputLayout");
		suggestions.add("com.google.android.material.textfield.TextInputEditText");
		suggestions.add("com.google.android.material.slider.Slider");
		suggestions.add("com.google.android.material.switchmaterial.SwitchMaterial");
		suggestions.add("com.google.android.material.checkbox.MaterialCheckBox");
		suggestions.add("com.google.android.material.radiobutton.MaterialRadioButton");
		suggestions.add("com.google.android.material.progressindicator.LinearProgressIndicator");
		suggestions.add("com.google.android.material.progressindicator.CircularProgressIndicator");
		suggestions.add("com.google.android.material.floatingactionbutton.FloatingActionButton");
		suggestions.add("com.google.android.material.chip.Chip");
		suggestions.add("com.google.android.material.chip.ChipGroup");
		suggestions.add("com.google.android.material.tabs.TabLayout");
		suggestions.add("com.google.android.material.appbar.MaterialToolbar");
		suggestions.add("com.google.android.material.navigation.NavigationView");
		suggestions.add("com.google.android.material.bottomnavigation.BottomNavigationView");
		suggestions.add("com.google.android.material.card.MaterialCardView");
		suggestions.add("com.google.android.material.imageview.ShapeableImageView");
		suggestions.add("com.google.android.material.textview.MaterialTextView");
		suggestions.add("com.google.android.material.badge.BadgeDrawable");
		suggestions.add("com.google.android.material.divider.MaterialDivider");
		suggestions.add("com.google.android.material.navigationrail.NavigationRailView");
		suggestions.add("com.google.android.material.navigation.NavigationBarView");
		suggestions.add("com.google.android.material.bottomsheet.BottomSheetDialog");
		suggestions.add("com.google.android.material.snackbar.Snackbar");
		suggestions.add("com.google.android.material.tooltip.TooltipDrawable");
		suggestions.add("com.google.android.material.button.MaterialButtonToggleGroup");
		suggestions.add("com.google.android.material.shape.MaterialShapeDrawable");
		suggestions.add("com.google.android.material.elevation.SurfaceColors");
		suggestions.add("com.google.android.material.textfield.MaterialAutoCompleteTextView");
		suggestions.add("com.google.android.material.search.SearchBar");
		suggestions.add("com.google.android.material.search.SearchView");
		
		suggestions.add("android.webkit.WebView");
		suggestions.add("android.view.SurfaceView");
		suggestions.add("android.view.TextureView");
		suggestions.add("android.view.View");
		suggestions.add("android.view.ViewGroup");
		suggestions.add("android.view.ViewStub");
		
		
		ArrayAdapter<String> adapter = new ArrayAdapter<>(
		context,
		R.layout.material_list_item,
		R.id.item_text,
		suggestions
		);
		
		autoCompleteTextView.setThreshold(1);
		autoCompleteTextView.setAdapter(adapter);
		
		inputLayout.addView(autoCompleteTextView);
		
		layout.addView(inputLayout, new LinearLayout.LayoutParams(
		ViewGroup.LayoutParams.MATCH_PARENT,
		ViewGroup.LayoutParams.WRAP_CONTENT
		));
		
		new MaterialAlertDialogBuilder(context)
		.setTitle("Add Custom Widget")
		.setMessage("")
		.setView(layout)
		.setPositiveButton("Save", (dialog, which) -> {
			try {
				String classPath = autoCompleteTextView.getText().toString().trim();
				
				if (classPath.isEmpty()) {
					return;
				}
				
				Class<?> cls = Class.forName(classPath);
				HashMap<String, Object> map = new HashMap<>();
				map.put("name", cls.getSimpleName());
				map.put("class_path", classPath);
				viewsList.add(map);
				
			} catch (Exception e) {
				
			}
		})
		.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
		.show();
	}
	
	
	public static class TempLayoutManager {
		private final ViewEditor editor;
		private static final String TEMP_DIR = "/.blacklogic/.temp_layouts/";
		private static final String AES_KEY = "NexusTeamSmartIndia2025LayoutKey"; // 32 bytes
		private static final String AES_IV = "1234567890abcdef"; // 16 bytes
		private static final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";
		
		public TempLayoutManager(ViewEditor editor) {
			this.editor = editor;
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
				Log.e("TempLayoutManager", "Encryption failed: " + e.getMessage(), e);
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
				Log.e("TempLayoutManager", "Decryption failed: " + e.getMessage(), e);
				return null;
			}
		}
		
		// Save temporary layout
		public void saveTempLayout(String activityName) {
			if (activityName == null || activityName.trim().isEmpty()) {
				activityName = editor.getCurrentActivityName();
			}
			try {
				FileUtil.makeDir(FileUtil.getPackageDataDir(editor.getContext()) + TEMP_DIR);
				if (editor.editorLayout.getChildCount() == 0) return;
				
				String xmlCode = editor.getXMLCode();
				String encrypted = encrypt(xmlCode);
				if (encrypted != null) {
					FileUtil.writeFile(getTempFilePath(activityName), encrypted);
					Log.d("TempLayoutManager", "Temp layout saved: " + activityName);
				} else {
					SketchwareUtil.showMessage(editor.getContext(), "Failed to save temp layout");
				}
			} catch (Exception e) {
				Log.e("TempLayoutManager", "saveTempLayout failed: " + e.getMessage(), e);
				SketchwareUtil.showMessage(editor.getContext(), "Save error: " + e.getMessage());
			}
		}
		
		// Restore temporary layout
		public void restoreTempLayout(String activityName) {
			if (activityName == null || activityName.trim().isEmpty()) {
				activityName = editor.getCurrentActivityName();
			}
			try {
				String tempFilePath = getTempFilePath(activityName);
				if (!FileUtil.isExistFile(tempFilePath)) return;
				
				String encryptedContent = FileUtil.readFile(tempFilePath);
				String xmlCode = decrypt(encryptedContent);
				if (xmlCode == null || xmlCode.trim().isEmpty()) {
					SketchwareUtil.showMessage(editor.getContext(), "Failed to decrypt temp layout");
					return;
				}
				
				//editor.loadXmlIntoEditor(xmlCode);
				new File(tempFilePath).delete();
				editor.saveLayout(activityName); // Save to permanent storage
				Log.d("TempLayoutManager", "Temp layout restored: " + activityName);
				SketchwareUtil.showMessage(editor.getContext(), "Layout restored: " + activityName);
			} catch (Exception e) {
				Log.e("TempLayoutManager", "restoreTempLayout failed: " + e.getMessage(), e);
				SketchwareUtil.showMessage(editor.getContext(), "Restore error: " + e.getMessage());
			}
		}
		
		// Helper: Get temp file path
		private String getTempFilePath(String activityName) {
			return FileUtil.getPackageDataDir(editor.getContext()) + TEMP_DIR + "temp_" + activityName + ".json";
		}
		
		// Show global restore dialog
		public void showGlobalRestoreDialog() {
			File dir = new File(FileUtil.getPackageDataDir(editor.getContext()) + TEMP_DIR);
			if (!dir.exists()) return;
			
			File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));
			if (files == null || files.length == 0) return;
			
			ArrayList<String> pendingLayouts = new ArrayList<>();
			for (File f : files) {
				pendingLayouts.add(f.getName().replace("temp_", "").replace(".json", ""));
			}
			
			AlertDialog.Builder builder = new AlertDialog.Builder(editor.getContext());
			builder.setTitle("Restore Pending Layouts");
			builder.setMessage("Found " + pendingLayouts.size() + " unsaved layouts:\n" + String.join(", ", pendingLayouts));
			builder.setPositiveButton("Restore All", (dialog, which) -> {
				for (String layout : pendingLayouts) {
					restoreTempLayout(layout);
				}
				SketchwareUtil.showMessage(editor.getContext(), "Restored " + pendingLayouts.size() + " layouts");
			});
			builder.setNegativeButton("Cancel", null);
			builder.show();
		}
		
		public interface GlobalRestoreCallback {
			void onLayoutRestored(String activityName, String xmlCode);
			void onAllRestored();
		}
	}
}
