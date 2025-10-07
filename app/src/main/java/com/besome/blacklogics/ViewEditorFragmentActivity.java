package com.besome.blacklogics;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.*;
import androidx.constraintlayout.widget.*;
import androidx.core.*;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.multidex.*;
import androidx.viewpager.*;
import androidx.viewpager2.*;
import com.besome.sketch.*;
import com.bumptech.glide.*;
import com.bumptech.glide.gifdecoder.*;
import com.example.myapp.*;
import com.github.angads25.filepicker.*;
import com.google.android.material.*;
import com.google.gson.*;
import com.googlecode.d2j.*;
import com.larswerkman.holocolorpicker.*;
import com.nexusteam.internal.os.layouteditor.custom.MyDragWidget;
import io.github.rosemoe.editor.*;
import io.github.rosemoe.sora.*;
import io.github.rosemoe.sora.langs.java.*;
import io.github.rosemoe.sora.langs.textmate.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.antlr.v4.runtime.*;
import org.benf.cfr.reader.*;
import org.eclipse.jdt.*;
import org.json.*;
import com.nexusteam.internal.os.layouteditor.custom.MyDragWidget;
import com.nexusteam.internal.os.layouteditor.widget.Widget;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import /*android.view.WindowManager.LayoutParams*/android.widget.LinearLayout.LayoutParams;
import androidx.cardview.widget.CardView;
import com.nexusteam.internal.os.layouteditor.widget.*;
import com.nexusteam.internal.os.layouteditor.util.*;
import com.nexusteam.internal.os.layouteditor.adapter.*;
import com.nexusteam.internal.os.layouteditor.color.*;
import com.nexusteam.internal.os.layouteditor.beans.ViewBean;
import com.besome.blacklogics.model.ActivityData;
import com.besome.blacklogics.file.WidgetClickListenerManager;
import com.besome.blacklogics.beans.ProjectActivityBean;
import com.besome.blacklogics.beans.WidgetAttributes;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.DragEvent;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import com.besome.blacklogics.model.*;
import androidx.lifecycle.ViewModelProvider;
import com.nexusteam.internal.os.layouteditor.WidgetDialogManager;
import com.besome.blacklogics.model.WidgetViewModel;
import com.besome.blacklogics.file.ProjectFileManager;
import com.besome.blacklogics.beans.ActivityBean;
import com.nexusteam.internal.os.layouteditor.util.WidgetStorageManager;
import com.nexusteam.internal.os.layouteditor.UndoRedoManager;
import java.util.Stack;
import android.util.Log;
import com.besome.blacklogics.WidgetAttributesManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import android.util.ArrayMap;
import com.google.gson.Gson;
import java.io.File;
import java.util.ArrayList;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.util.SparseBooleanArray;
import com.nexusteam.internal.os.layouteditor.model.FileItem;
import com.nexusteam.internal.os.layouteditor.adapter.FileListAdapter;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.Observer;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

import com.google.gson.Gson;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import java.io.File;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAKeyGenParameterSpec;

import javax.crypto.Cipher;

import android.util.Base64;
import android.os.Parcel;

import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.xml.sax.InputSource;
import java.io.StringReader;

import android.graphics.Color;
import android.widget.LinearLayout;

public class ViewEditorFragmentActivity extends Fragment implements OnDragListener, OnLongClickListener, DesignActivity.OnActivityNameChangeListener  { 
	
	private static Stack<String> undoStack = new Stack<>();
	private static Stack<String> redoStack = new Stack<>();
	private static boolean isProgrammaticChange = false; // Flag to prevent recursive state saving
	
	private long lastDragUpdate = 0; // Tracks the last time ACTION_DRAG_LOCATION was processed
	private static final long DRAG_THROTTLE_MS = 50; // Minimum time (in ms) between drag updates
	
	// Add a Map to store custom attributes for each widget
	public static Map<String, Map<String, String>> widgetCustomAttributes = new HashMap<>();
	
	public static int linearLayoutCounter = 1;
	public static int frameLayoutCounter = 1;
	public static int buttonCounter = 1;
	public static int checkBoxCounter = 1;
	public static int viewPagerCounter = 1;
	public static int editTextCounter = 1;
	public static int webViewCounter = 1;
	public static int searchViewCounter = 1;
	public static int ratingViewCounter = 1;
	public static int seekBarCounter = 1;
	public static int videoViewCounter = 1;
	public static int progressBarCounter = 1;
	public static int scrollViewCounter = 1;
	public static int digitalClockCounter = 1;
	public static int timePickerCounter = 1;
	public static int circleImageViewCounter = 1;
	public static int codeViewerCounter = 1;
	public static int horizontalScrollViewCounter = 1;
	public static int listViewCounter = 1;
	public static int switchCounter = 1;
	public static int radioButtonCounter = 1;
	
	public WidgetAttributesManager attributesManager;
	private String projectsFile = "$" + "/data/projects.json";
	private ProjectStorageManager storageManager;
	private UndoRedoManager undoRedoManager;
	private WidgetDialogManager dialogManager;
	
	private WidgetStorageManager widgetStorageManager;
	private String projectId = "601";
	
	private static final long DRAG_UPDATE_INTERVAL = 50;
	private int[] lixeiraLocation = new int[2];
	private int[] llLocation = new int[2];
	private boolean locationsCached = false;
	private boolean hasVibratedOnLixeira = false;
	int i = 0;
	public static View view_location;
	int defaultIndex;
	View mWidget;
	View mView;
	Widget wigetUtil;
	int mTxt = 1;
	int mBtn = 1;
	public static LinearLayout linear;
	public static View selectedWidget;
	public static CardView widget_width;
	public static CardView widget_height;
	public static CardView widget_text;
	public static CardView widget_src;
	public static LinearLayout widgetpropertiesLinearLayout1;
	public static String activityName = "MainActivity" /* TUDO : DEFAULT ACTIVITY NAME**/;
	public static String layoutName = "main" /*TUDO : DEFAULT LAYOUT NAME**/;
	public static ObjectAnimator anim = new ObjectAnimator();
	public static boolean useAndroidX = false/* TUDO : YOU CAN USE TRUE AND FALSE FOR ANDROIDX IMPORTA ETC FOR**/;
	public int index = 0;
	int mLinear = 1;
	public boolean isDraggingOverLixeira = false;
	public int mWebView = 1;
	public static CardView translationX;
	public static CardView transY;
	public static CardView colorText;
	public static CardView textSize;
	public static CardView Lines;
	public static CardView textStyle;
	public static int mListView = 1;
	public static CardView padding;
	public static CardView margin;
	public static CardView background;
	public static String pkgName = "com.my.newproject"/**TUDO : DEFAULT PACKAGE NAME*/;
	public static LinearLayout ll;
	LayoutInflater layoutInflater;
	public static CardView widget_id;
	public static String projectPath = ""/*TUDO : YOU CAN ADD CUSTOM DEFAULT PATH OF ORIGINAL**/;
	public static List<String> sAllWidgetIds;
	public static String sAllWidgetIdsString;
	private WidgetViewModel viewModel;
	public static boolean addedInLayout;
	public static boolean isMainActivity = true;
	public static ArrayList<ActivityData> otherActivities = new ArrayList<>();
	public static String projectName = "My project";
	public static final String TAG = "ViewFragment";
	public ProjectFileManager fileManager;
	public static ViewBean viewBean;
	ArrayMap<String, List<ProjectActivityBean.ViewBean>> allWidgetsMap;
	public ProjectActivityBean acBean;
	public int mEditText;
	public static WidgetProperties selectedWidgetProperties;
	public static int mProgressBar = 1;
	public static int mVideoView = 1;
	public static int mSwitch = 1;
	public static int mCheckBox = 1;
	public static int mSeekBar = 1;
	public static int mRadioButton = 1;
	public static int mEdt = 1;
	public static int mPrg = 1;
	public static int mDigitalClock = 1;
	public static int mImg = 1;
	public static int mTimePicker = 1;
	public static int mScrollView = 1;
	public static int mHorizontalScrollView = 1;
	public View widget;
	public Context context;
	private String scName = "";
	public static LinearLayout fab;
	public static ViewEditorFragmentActivity instance;
	public static final int TAG_CUSTOM_CLASS_NAME = R.id.tag_custom_class_name;
	public static int textViewCounter = 1;
	public static int imageViewCounter = 1;
	private static WidgetInteractionListener widgetInteractionListener;
	private String id = "";
	public static String sc_id;
	public DesignActivity designActivity;
	
	private LinearLayout editorLinearLayout1;
	private LinearLayout lixeira;
	private LinearLayout linear8;
	private RelativeLayout relativelayout2;
	private View view9;
	private LinearLayout fundo_landscape;
	private LinearLayout widgets;
	private TextView textview4;
	private MyDragWidget widget_textview;
	private MyDragWidget widget_button;
	private MyDragWidget widget_image_view;
	private MyDragWidget widget_video_view;
	private MyDragWidget widget_linear;
	private MyDragWidget widget_web_view;
	private MyDragWidget widget_list_view;
	private LinearLayout linear10;
	private LinearLayout linear11;
	private LinearLayout linear12;
	private LinearLayout linear13;
	private LinearLayout phone_action_bar;
	private ScrollView vscroll2;
	public static TextView tv_view_name;
	private LinearLayout linear14;
	private ImageView imageview4;
	private ImageView imageview5;
	private ImageView imageview6;
	private TextView textview5;
	private TextView textview6;
	private LinearLayout ll_widgets;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.view_editor_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		try {
			widgetInteractionListener = (WidgetInteractionListener) context;
		} catch (ClassCastException e) {
			throw new ClassCastException(context.toString() + " must implement WidgetInteractionListener");
		}
		if (_savedInstanceState != null) {
			activityName = _savedInstanceState.getString("activity_name", "");
			layoutName = _savedInstanceState.getString("layout_name", "");
		}
		editorLinearLayout1 = _view.findViewById(R.id.editorLinearLayout1);
		lixeira = _view.findViewById(R.id.lixeira);
		linear8 = _view.findViewById(R.id.linear8);
		relativelayout2 = _view.findViewById(R.id.relativelayout2);
		view9 = _view.findViewById(R.id.view9);
		fundo_landscape = _view.findViewById(R.id.fundo_landscape);
		widgets = _view.findViewById(R.id.widgets);
		textview4 = _view.findViewById(R.id.textview4);
		widget_textview = _view.findViewById(R.id.widget_textview);
		widget_button = _view.findViewById(R.id.widget_button);
		widget_image_view = _view.findViewById(R.id.widget_image_view);
		widget_video_view = _view.findViewById(R.id.widget_video_view);
		widget_linear = _view.findViewById(R.id.widget_linear);
		widget_web_view = _view.findViewById(R.id.widget_web_view);
		widget_list_view = _view.findViewById(R.id.widget_list_view);
		linear10 = _view.findViewById(R.id.linear10);
		linear11 = _view.findViewById(R.id.linear11);
		linear12 = _view.findViewById(R.id.linear12);
		linear13 = _view.findViewById(R.id.linear13);
		phone_action_bar = _view.findViewById(R.id.phone_action_bar);
		vscroll2 = _view.findViewById(R.id.vscroll2);
		tv_view_name = _view.findViewById(R.id.tv_view_name);
		linear14 = _view.findViewById(R.id.linear14);
		imageview4 = _view.findViewById(R.id.imageview4);
		imageview5 = _view.findViewById(R.id.imageview5);
		imageview6 = _view.findViewById(R.id.imageview6);
		textview5 = _view.findViewById(R.id.textview5);
		textview6 = _view.findViewById(R.id.textview6);
		ll = _view.findViewById(R.id.ll_widgets);
	}
	
	private void initializeLogic() {
		/**
TUDO : PROJECT PATH FOR SAVE AND LOAD LAYOUT HELP
**/
		sc_id = getActivity().getIntent().getStringExtra("sc_id");
		projectId = getActivity().getIntent().getStringExtra("sc_id");
		pkgName = getActivity().getIntent().getStringExtra("pkgName");
		scName = getActivity().getIntent().getStringExtra("scName");
		projectPath = FileUtil.getExternalStorageDir().concat("/.blacklogics/data/".concat(sc_id));
		new Handler().post(() -> {
			_setupDesignActivityListeners();
		});
		
		projectsFile = projectPath + "/projects_root.json";
		attributesManager = new WidgetAttributesManager(getContext(), projectPath, scName, projectId, pkgName);
		storageManager = new ProjectStorageManager(getContext(), projectPath, projectsFile, this);
		undoRedoManager = new UndoRedoManager(getContext(), ll, activityName, layoutName, pkgName, useAndroidX, this);
		dialogManager = new WidgetDialogManager(getContext(), undoRedoManager);
		dialogManager.setActivityName(activityName);
		dialogManager.setAttributesManager(attributesManager);
		
		
		// Load
		storageManager.loadLayout(ll, layoutName);
		
		widgetStorageManager = new WidgetStorageManager(getContext(), projectId, activityName);
		//widgetStorageManager.loadWidgetIds();
		/**
TUDO : SAVED LAYOUT LOAD
**/
		loadView();
		ll.setOrientation(LinearLayout.VERTICAL);
		ll.setOnDragListener(this);
		/**
TUDO : HIDE DELETE WIDGET AREA GONE 
**/
		lixeira.setVisibility(View.GONE);
		lixeira.setAlpha(0f);
		/**
TUDO : SET ON DRAG LISTENER
**/
		widget_textview.setOnLongClickListener(this);
		widget_button.setOnLongClickListener(this);
		widget_image_view.setOnLongClickListener(this);
		widget_web_view.setOnLongClickListener(this);
		widget_video_view.setOnLongClickListener(this);
		widget_linear.setOnLongClickListener(this);
		widget_list_view.setOnLongClickListener(this);
		view_location = new View(getContext());
		view_location.setBackgroundColor(Color.parseColor("#DDDDDD"));
		view_location.setLayoutParams(new ViewGroup.LayoutParams(0, 0));
		view_location.setElevation(3);
		/*
TUDO : SET WIDGETS PROPERTIES 
*/
		setupWidgetClickListeners();
		getCurrentActivityUsingBean();
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
	}
	
	@Override
	public void onStop() {
		super.onStop();
		
	}
	public void _a() {
		/**
TUDO : isHiddenProperties
**/
		/*
TUDO : MAKED <A> MOREBLOCK FOR ADD THIS ADD SOURCE DIRECTLY FOR MADE IN SKETCHWARE NO RIQUERE<a> MORE BLOCK FOR ANDROID STUDIO AND MORE IDE'S
*/
	}
	/**
TUDO : ON WIDGET CLICK LISTENER
**/
	public static class WidgetClickListener implements OnClickListener
	{
		@Override
		public void onClick(View view)
		{
			/*ProjectActivity.*/selectWidget(view);
		}
	}
	/**
TUDO : ON WIDGETS DRAG LISTENER
**/
	@Override
	public boolean onLongClick(View v) {
		unselectSelectedWidget();
		ViewGroup vg = (ViewGroup) v.getParent();
		view_location.getLayoutParams().width = v.getWidth();
		view_location.getLayoutParams().height = v.getHeight();
		
		if (ll == vg.getParent()) {
			for (int i = 0; i < ll.getChildCount(); i++) {
				if (vg == ll.getChildAt(i)) {
					defaultIndex = i;
				}
			}
		} else {
			defaultIndex = -1;
		}
		
		if (v.startDrag(null, new View.DragShadowBuilder(v), v, 0)) {
			linear = new LinearLayout(getContext());
			linear.setOrientation(LinearLayout.VERTICAL);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.WRAP_CONTENT
			);
			linear.setLayoutParams(params);
			linear.setOnDragListener(this);
			
			View createdWidget = null;
			String widgetType = "";
			
			if (v.getId() == R.id.widget_textview) {
				WidgetTextView widgetTextView = new WidgetTextView(getContext());
				widgetTextView.setOnLongClickListener(this);
				widgetTextView.getTextView().setText("TextView");
				widgetTextView.setWidgetName(new TextView(getContext()).getClass().getSimpleName());
				widgetTextView.setWidgetId(WidgetTextView.newWidgetId());
				widgetTextView.setOnClickListener(new WidgetClickListener());
				linear.addView(widgetTextView);
				selectedWidget = widgetTextView;
				mTxt++;
				id = WidgetTextView.newWidgetId();
				createdWidget = widgetTextView;
				widgetType = "TextView";
				
				// Set default attributes for TextView
				setDefaultTextViewAttributes(widgetTextView, id);
				
			} else if (v.getId() == R.id.widget_image_view) {
				WidgetImageView widgetImageView = new WidgetImageView(getContext());
				widgetImageView.setOnLongClickListener(this);
				widgetImageView.setWidgetId(WidgetImageView.newWidgetId());
				widgetImageView.setOnClickListener(new WidgetClickListener());
				linear.addView(widgetImageView);
				selectedWidget = widgetImageView;
				mImg++;
				id = WidgetImageView.newWidgetId();
				createdWidget = widgetImageView;
				widgetType = "ImageView";
				
				// Set default attributes for ImageView
				setDefaultImageViewAttributes(widgetImageView, id);
				
			} else if (v.getId() == R.id.widget_button) {
				WidgetButton widgetButton = new WidgetButton(getContext());
				widgetButton.setOnLongClickListener(this);
				widgetButton.setOnClickListener(new WidgetClickListener());
				widgetButton.getTextView().setText("Button");
				widgetButton.setWidgetName(new Button(getContext()).getClass().getSimpleName());
				widgetButton.setWidgetId(WidgetButton.newWidgetId());
				selectedWidget = widgetButton;
				mBtn++;
				linear.addView(widgetButton);
				id = WidgetButton.newWidgetId();
				createdWidget = widgetButton;
				widgetType = "Button";
				
				// Set default attributes for Button
				setDefaultButtonAttributes(widgetButton, id);
				
			} else if (v.getId() == R.id.widget_linear) {
				WidgetLinear widgetLinear = new WidgetLinear(getContext());
				widgetLinear.setOnLongClickListener(this);
				widgetLinear.setOnClickListener(new WidgetClickListener());
				widgetLinear.setWidgetName(new WidgetLinear(getContext()).getClass().getSimpleName());
				widgetLinear.setWidgetId(WidgetLinear.newWidgetId());
				selectedWidget = widgetLinear;
				mLinear++;
				linear.addView(widgetLinear);
				id = WidgetLinear.newWidgetId();
				createdWidget = widgetLinear;
				widgetType = "LinearLayout";
				
				// Set default attributes for LinearLayout
				setDefaultLinearLayoutAttributes(widgetLinear, id);
				
			} else if (v.getId() == R.id.widget_web_view) {
				WidgetWebView widgetWebView = new WidgetWebView(getContext());
				widgetWebView.setOnLongClickListener(this);
				widgetWebView.setOnClickListener(new WidgetClickListener());
				widgetWebView.setWidgetName(new WidgetWebView(getContext()).getClass().getSimpleName());
				widgetWebView.setWidgetId(widgetWebView.newWidgetId());
				selectedWidget = widgetWebView;
				mWebView++;
				linear.addView(widgetWebView);
				id = widgetWebView.newWidgetId();
				createdWidget = widgetWebView;
				widgetType = "WebView";
				
				// Set default attributes for WebView
				setDefaultWebViewAttributes(widgetWebView, id);
				
			} else if (v.getId() == R.id.widget_list_view) {
				WidgetListView widgetListView = new WidgetListView(getContext());
				widgetListView.setOnLongClickListener(this);
				widgetListView.setOnClickListener(new WidgetClickListener());
				widgetListView.setOnItemClickListener((parent, view, position, id) -> selectWidget(view));
				widgetListView.setWidgetName(new WidgetListView(getContext()).getClass().getSimpleName());
				widgetListView.setWidgetId(widgetListView.newWidgetId());
				selectedWidget = widgetListView;
				mListView++;
				linear.addView(widgetListView);
				id = widgetListView.newWidgetId();
				createdWidget = widgetListView;
				widgetType = "ListView";
				
				// Set default attributes for ListView
				setDefaultListViewAttributes(widgetListView, id);
				
			} else {
				mWidget = vg;
			}
			
			mWidget = linear;
			
			if (ll == vg.getParent()) {
				ll.removeView(vg);
			}
			
			TheBlockLogicsUtil.vibrate(getContext());
			
		}
		return false;
	}
	
	private void cacheLocations() {
		if (!locationsCached && lixeira != null && ll != null) {
			lixeira.getLocationOnScreen(lixeiraLocation);
			ll.getLocationOnScreen(llLocation);
			locationsCached = true;
		}
	}
	
	@Override
	public boolean onDrag(View v, DragEvent event) {
		if (lixeira == null || ll == null || getContext() == null || getResources() == null) {
			return false;
		}
		
		cacheLocations();
		int action = event.getAction();
		View draggedView = (View) event.getLocalState();
		long currentTime = System.currentTimeMillis();
		
		if (action == DragEvent.ACTION_DRAG_LOCATION && 
		currentTime - lastDragUpdate < DRAG_UPDATE_INTERVAL) {
			return true;
		}
		
		float x = event.getX() + llLocation[0];
		float y = event.getY() + llLocation[1];
		if (Float.isNaN(x) || Float.isNaN(y)) {
			return false;
		}
		
		switch (action) {
			case DragEvent.ACTION_DRAG_STARTED:
			lixeira.setVisibility(View.GONE);
			lixeira.setAlpha(0.3f);
			isDraggingOverLixeira = false;
			hasVibratedOnLixeira = false;
			index = ll.getChildCount();
			addedInLayout = false;
			mView = null;
			locationsCached = false; // Reset cache
			break;
			
			case DragEvent.ACTION_DRAG_LOCATION:
			lastDragUpdate = currentTime;
			int screenHeight = getResources().getDisplayMetrics().heightPixels;
			int bottomThreshold = screenHeight - 200;
			
			if (y >= bottomThreshold) {
				if (lixeira.getVisibility() == View.GONE) {
					lixeira.setVisibility(View.VISIBLE);
					lixeira.animate().alpha(1f).setDuration(200).start();
				}
			} else if (lixeira.getVisibility() == View.VISIBLE) {
				lixeira.animate().alpha(0f).setDuration(200).withEndAction(() -> 
				lixeira.setVisibility(View.GONE)).start();
			}
			
			Rect lixeiraRect = new Rect(
			lixeiraLocation[0], lixeiraLocation[1],
			lixeiraLocation[0] + lixeira.getWidth(),
			lixeiraLocation[1] + lixeira.getHeight()
			);
			boolean isOverLixeira = lixeiraRect.contains((int) x, (int) y);
			
			if (isOverLixeira && !isDraggingOverLixeira && !hasVibratedOnLixeira) {
				lixeira.setBackgroundResource(R.drawable.lixeira_bg_active);
				isDraggingOverLixeira = true;
				TheBlockLogicsUtil.vibrate(getContext(), 50);
				hasVibratedOnLixeira = true;
				if (draggedView != null && draggedView.getParent() != null) {
					saveStateToUndo();
					((ViewGroup) draggedView.getParent()).removeView(draggedView);
				}
			} else if (!isOverLixeira && isDraggingOverLixeira) {
				lixeira.setBackgroundResource(R.drawable.lixeira_bg_normal);
				isDraggingOverLixeira = false;
				hasVibratedOnLixeira = false;
			}
			
			if (mView != v) {
				mView = v;
				if (mView != ll) {
					for (int i = 0; i < ll.getChildCount(); i++) {
						if (v == ll.getChildAt(i)) {
							index = i;
							break;
						}
					}
				}
				if (view_location != null) {
					int currentIndex = ll.indexOfChild(view_location);
					if (currentIndex != index) {
						ll.removeView(view_location);
						ll.addView(view_location, index);
					}
				}
			}
			break;
			
			case DragEvent.ACTION_DROP:
			if (view_location != null && view_location.getParent() != null) {
				ll.removeView(view_location);
				addWidgetInLayout(mWidget, index);
				new Thread(() -> {
					if (draggedView instanceof Widget) {
						Widget actualWidget = (Widget) draggedView;
						String actualWidgetId = actualWidget.getWidgetId();
						String actualWidgetType = actualWidget.getClass().getSimpleName();
						WidgetStorageManager storageManager = new WidgetStorageManager(getContext(), projectId, activityName);
						storageManager.saveWidgetMetadata(actualWidget, actualWidgetId);
						saveWidgetInfo(activityName, actualWidgetType, actualWidgetId);
						generateAllActivitiesCode();
					}
				}).start();
				addedInLayout = true;
			}
			break;
			
			case DragEvent.ACTION_DRAG_EXITED:
			if (view_location != null && view_location.getParent() != null) {
				ll.removeView(view_location);
			}
			if (mView == ll) index = ll.getChildCount();
			mView = null;
			break;
			
			case DragEvent.ACTION_DRAG_ENDED:
			new Handler().post(() -> {
				if (defaultIndex != -1 && !addedInLayout) {
					addWidgetInLayout(mWidget, defaultIndex);
				}
				if (view_location != null && view_location.getParent() != null) {
					ll.removeView(view_location);
				}
				if (lixeira != null) {
					lixeira.animate().alpha(0f).setDuration(200).withEndAction(() -> 
					lixeira.setVisibility(View.GONE)).start();
				}
				isDraggingOverLixeira = false;
			});
			break;
		}
		return true;
	}
	public void addWidgetInLayout(View v, int index) {
		undoRedoManager.saveStateToUndo();
		if (v.getParent() != null) {
			((ViewGroup) v.getParent()).removeView(v);
		}
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.MATCH_PARENT,
		LinearLayout.LayoutParams.WRAP_CONTENT
		);
		v.setLayoutParams(lp);
		ll.addView(v, index);
		v.requestLayout();
		addedInLayout = true;
	}
	
	/**
TUDO : LOAD LAYOUT 
**/
	public void loadView() {
		storageManager.loadLayout(ll, layoutName);
	}
	/**
TUDO : textWidget SET PROPERTY'S
**/
	/**
TUDO : saveView FOR SAVE LAYOUT
**/
	public boolean saveView() {
		return storageManager.saveLayout(ll, activityName, layoutName, pkgName, useAndroidX);
	}
	/**
TUDO : TOGGLE HIDE PROPERTIES, SHOW PROPERTY'S
**/
	public static void hideProperties()
	{
		anim.setTarget(DesignActivity.ll_properties);
		anim.setProperty(View.TRANSLATION_Y);
		anim.setFloatValues(new float[]{(float) DesignActivity.ll_properties.getHeight()});
		anim.setInterpolator(new DecelerateInterpolator());
		anim.start();
	}
	public static void showProperties()
	{
		DesignActivity.ll_properties.setVisibility(View.VISIBLE);
		anim.setTarget(DesignActivity.ll_properties);
		anim.setProperty(View.TRANSLATION_Y);
		anim.setFloatValues(new float[]{(float) 0});
		anim.setInterpolator(new DecelerateInterpolator());
		anim.start();
	}
	/**
TUDO : isHiddenProperties
**/
	public static boolean isHiddenProperties()
	{
		return DesignActivity.ll_properties.getTranslationY() == ((float) DesignActivity.ll_properties.getHeight());
	}
	/**
TUDO : unselectSelectedWidget
**/
	public static void unselectSelectedWidget()
	{
		if (selectedWidget != null)
		{
			((Widget) selectedWidget).setBackgroundColor(0);
			selectedWidget = (View) null;
			hideProperties();
		}
	}
	/**
TUDO : selectWidget
**/
	public static void selectWidget(View view)
	{
		if (selectedWidget != null) 
		{
			unselectSelectedWidget();
		}
		selectedWidget = view; 
		((Widget) view).setBackgroundColor(Color.parseColor("#77BBCCDD"));
		((TextView)DesignActivity.ll_properties.findViewById(R.id.tv_widget_id)).setText(WidgetUtil.getWidgetId(view));
		if ((view instanceof WidgetButton) || (view instanceof WidgetTextView)) {
			DesignActivity.widget_text.setVisibility(View.VISIBLE);
			DesignActivity.colorText.setVisibility(View.VISIBLE);
			DesignActivity.textSize.setVisibility(View.VISIBLE);
			DesignActivity.textStyle.setVisibility(View.VISIBLE);
			DesignActivity.Lines.setVisibility(View.VISIBLE);
			DesignActivity.widget_src.setVisibility(View.GONE);
		}
		
		if ((view instanceof WidgetImageView) || (view instanceof WidgetImageView)) {
			DesignActivity.widget_text.setVisibility(View.GONE);
			DesignActivity.textSize.setVisibility(View.GONE);
			DesignActivity.widget_src.setVisibility(View.VISIBLE);
			DesignActivity.colorText.setVisibility(View.GONE);
			DesignActivity.textStyle.setVisibility(View.GONE);
			DesignActivity.Lines.setVisibility(View.GONE);
		}
		/**
        TUDO : GONE SET TEXT PROPERTIES AND SET IMAGE RESOURCE PROPERTIES 
        **/
		if ((view instanceof WidgetWebView) || (view instanceof WidgetWebView)) {
			DesignActivity.widget_text.setVisibility(View.GONE);
			DesignActivity.widget_src.setVisibility(View.GONE);
			DesignActivity.colorText.setVisibility(View.GONE);
			DesignActivity.textSize.setVisibility(View.GONE);
			DesignActivity.textStyle.setVisibility(View.GONE);
			DesignActivity.Lines.setVisibility(View.GONE);
		}
		
		showProperties();
	}
	// Helper method to convert camelCase to snake_case
	public String camelToSnakeCase(String input) {
		if (input == null || input.isEmpty()) {
			return input;
		}
		StringBuilder result = new StringBuilder();
		char[] chars = input.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (Character.isUpperCase(c)) {
				if (i > 0 && Character.isLowerCase(chars[i - 1])) {
					result.append('_');
				}
				result.append(Character.toLowerCase(c));
			} else {
				result.append(c);
			}
		}
		return result.toString();
	}
	
	private void saveStateToUndo() {
		undoRedoManager.saveStateToUndo();
	}
	public void undo() {
		undoRedoManager.undo();
	}
	public void redo() {
		undoRedoManager.redo();
	}
	private void handleWidgetClick(View view) {
		
		if (widgetInteractionListener != null) {
			widgetInteractionListener.onWidgetClicked(view);
		}
		
		selectWidget(view);
		
	}
	
	private void setupWidgetClickListeners() {
	}
	private void getCurrentActivityUsingBean() {
		if (getActivity() instanceof DesignActivity) {
			DesignActivity designActivity = (DesignActivity) getActivity();
			
			// ActivityBean banake use karo
			ActivityBean currentActivity = new ActivityBean(
			designActivity.activityName, 
			designActivity.layoutName
			);
			
			// Ab ActivityBean ke through values lo
			String activityName = currentActivity.getActivityName();
			String layoutName = currentActivity.getLayoutName();
			
			Log.d(TAG, "Using ActivityBean - Activity: " + activityName);
			Log.d(TAG, "Using ActivityBean - Layout: " + layoutName);
			
			// Use karo
			
		}
	}
	
	// Ya fir DesignActivity se directly ActivityBean lelo agar wahan method hai
	private void getActivityBeanFromDesign() {
		if (getActivity() instanceof DesignActivity) {
			DesignActivity designActivity = (DesignActivity) getActivity();
			ActivityBean currentActivity = designActivity.getCurrentActivityBean();
			
			if (currentActivity != null) {
				String activityName = currentActivity.getActivityName();
				String layoutName = currentActivity.getLayoutName();
				tv_view_name.setText(layoutName);
				Log.d(TAG, "ActivityBean from Design: " + activityName + ", " + layoutName);
			}
		}
	}
	
	public static void saveWidgetInfo(String activityName, String widgetType, String widgetId) {
		try {
			String projectPath = FileUtil.getExternalStorageDir() + "/.blacklogics/data/" + 
			sc_id; // Global scId from DesignActivity
			String widgetPath = projectPath + "/widget_info/project_widgets.json";
			FileUtil.makeDir(projectPath + "/widget_info/");
			
			// Read existing (same as block logic)
			Map<String, Map<String, String>> widgetMap = new HashMap<>();
			if (FileUtil.isExistFile(widgetPath)) {
				String encodedJson = FileUtil.readFile(widgetPath);
				String decodedJson = new String(Base64.decode(encodedJson, Base64.DEFAULT));
				Type mapType = new TypeToken<Map<String, Map<String, String>>>() {}.getType();
				widgetMap = new Gson().fromJson(decodedJson, mapType);
			}
			
			// Update - widgetType as key, widgetId as value
			Map<String, String> activityWidgets = widgetMap.getOrDefault(activityName, new HashMap<>());
			activityWidgets.put(widgetType, widgetId);
			widgetMap.put(activityName, activityWidgets);
			
			// Save (same as block logic)
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String prettyJson = gson.toJson(widgetMap);
			String encodedJson = Base64.encodeToString(prettyJson.getBytes(), Base64.DEFAULT);
			FileUtil.writeFile(widgetPath, encodedJson);
			
			Log.d("ViewEditor", "Saved widget: " + activityName + " -> " + widgetType + " -> " + widgetId);
			
		} catch (Exception e) {
			Log.e("ViewEditor", "Error saving widget info: " + e.getMessage());
		}
	}
	
	public void generateAllActivitiesCode() {
		try {
			// EK master JSON object
			JSONObject projectJson = new JSONObject();
			
			// Project basic info
			projectJson.put("project_name", projectName);
			projectJson.put("project_id", sc_id);
			projectJson.put("package_name", pkgName);
			projectJson.put("created_date", new Date().toString());
			
			// SAB ACTIVITIES KA DATA EK ARRAY MEIN
			JSONArray activitiesArray = new JSONArray();
			
			// 1. MainActivity (current activity)
			JSONObject mainActivity = new JSONObject();
			mainActivity.put("activity_name", activityName);
			mainActivity.put("layout_name", layoutName);
			mainActivity.put("is_main_activity", true);
			mainActivity.put("layout_data", getCurrentLayoutJson());
			activitiesArray.put(mainActivity);
			
			// 2. Other activities (agar koi hai toh)
			addOtherActivitiesToJson(activitiesArray);
			
			projectJson.put("activities", activitiesArray);
			
			// EK HI FILE MEIN SAB KUCH
			String jsonString = projectJson.toString(2);
			String filePath = projectPath + "/all_activities_layouts.json";
			FileUtil.writeFile(filePath, jsonString);
			
			Log.d("JSONGenerator", "All activities saved in single JSON: " + filePath);
			TheBlockLogicsUtil.showToast(getContext(), "All activities saved!");
			
		} catch (Exception e) {
			Log.e("JSONGenerator", "Error: " + e.getMessage());
		}
	}
	
	/**
* Current layout ka JSON banayo
*/
	private JSONObject getCurrentLayoutJson() {
		try {
			JSONObject layoutJson = new JSONObject();
			
			layoutJson.put("type", "LinearLayout");
			layoutJson.put("orientation", ll.getOrientation() == 1 ? "vertical" : "horizontal");
			
			// Sab widgets current layout ke
			JSONArray widgetsArray = new JSONArray();
			addWidgetsToJson(ll, widgetsArray);
			
			layoutJson.put("widgets", widgetsArray);
			return layoutJson;
			
		} catch (Exception e) {
			return new JSONObject();
		}
	}
	
	/**
* Current layout ke sab widgets ko JSON mein add karo
*/
	private void addWidgetsToJson(View view, JSONArray widgetsArray) {
		try {
			JSONObject widgetJson = new JSONObject();
			
			// Widget type
			String type = "View";
			if (view instanceof WidgetTextView) type = "TextView";
			else if (view instanceof WidgetButton) type = "Button";
			else if (view instanceof WidgetImageView) type = "ImageView";
			else if (view instanceof WidgetWebView) type = "WebView";
			else if (view instanceof WidgetListView) type = "ListView";
			else if (view instanceof LinearLayout) type = "LinearLayout";
			
			widgetJson.put("type", type);
			widgetJson.put("id", WidgetUtil.getWidgetId(view));
			
			// Properties
			JSONObject props = new JSONObject();
			
			// Size
			ViewGroup.LayoutParams params = view.getLayoutParams();
			if (params != null) {
				props.put("width", getSizeString(params.width));
				props.put("height", getSizeString(params.height));
			}
			
			// Text properties
			if (view instanceof WidgetTextView) {
				WidgetTextView tv = (WidgetTextView) view;
				props.put("text", tv.getTextView().getText().toString());
				props.put("text_size", tv.getTextView().getTextSize() / getResources().getDisplayMetrics().scaledDensity);
			}
			else if (view instanceof WidgetButton) {
				WidgetButton btn = (WidgetButton) view;
				props.put("text", btn.getTextView().getText().toString());
				props.put("text_size", btn.getTextView().getTextSize() / getResources().getDisplayMetrics().scaledDensity);
			}
			
			widgetJson.put("properties", props);
			widgetsArray.put(widgetJson);
			
			// Agar layout hai toh children ko bhi process karo
			if (view instanceof LinearLayout) {
				LinearLayout layout = (LinearLayout) view;
				for (int i = 0; i < layout.getChildCount(); i++) {
					addWidgetsToJson(layout.getChildAt(i), widgetsArray);
				}
			}
			
		} catch (Exception e) {
			// Skip this widget, continue with others
		}
	}
	
	/**
* Other activities ko JSON mein add karo
*/
	private void addOtherActivitiesToJson(JSONArray activitiesArray) {
		try {
			// Tumhare otherActivities list se data lo
			for (ActivityData activity : otherActivities) {
				JSONObject activityJson = new JSONObject();
				activityJson.put("activity_name", activity.getActivityName());
				activityJson.put("layout_name", activity.getLayoutName());
				activityJson.put("is_main_activity", false);
				
				// Agar saved layout file hai toh load karo
				String layoutPath = projectPath + "/layouts/" + activity.getLayoutName() + ".json";
				if (FileUtil.isExistFile(layoutPath)) {
					String layoutData = FileUtil.readFile(layoutPath);
					activityJson.put("layout_data", new JSONObject(layoutData));
				} else {
					activityJson.put("layout_data", new JSONObject()); // Empty
				}
				
				activitiesArray.put(activityJson);
			}
		} catch (Exception e) {
			Log.e("JSONGenerator", "Error adding other activities: " + e.getMessage());
		}
	}
	
	private String getSizeString(int size) {
		if (size == ViewGroup.LayoutParams.MATCH_PARENT) return "match_parent";
		if (size == ViewGroup.LayoutParams.WRAP_CONTENT) return "wrap_content";
		return size + "dp";
	}
	/**
* Manual call ke liye
*/
	public void saveAllActivitiesToJson() {
		generateAllActivitiesCode();
	}
	/**
 * ==========================
 * HELPER FUNCTIONS
 * ==========================
 */
	
	/**
 * Convert LayoutParams size to string (match_parent, wrap_content, dp).
 */
	private String convertSizeToString(int size) {
		if (size == ViewGroup.LayoutParams.MATCH_PARENT) {
			return "match_parent";
		} else if (size == ViewGroup.LayoutParams.WRAP_CONTENT) {
			return "wrap_content";
		} else {
			float dp = size / getContext().getResources().getDisplayMetrics().density;
			return (int) dp + "dp";
		}
	}
	
	/**
 * Extract background color if ColorDrawable, else return className.
 */
	private String getBackgroundString(View v) {
		try {
			if (v.getBackground() instanceof ColorDrawable) {
				int color = ((ColorDrawable) v.getBackground()).getColor();
				return String.format("#%08X", (0xFFFFFFFF & color));
			} else if (v.getBackground() != null) {
				return v.getBackground().getClass().getSimpleName();
			}
		} catch (Exception ignored) {}
		return "#00000000"; // transparent default
	}
	
	/**
 * Save padding values (L,T,R,B).
 */
	private void savePaddingAttributes(WidgetAttributesManager am, String activityName, String widgetId, View v) {
		am.saveAttribute(activityName, widgetId, "paddingLeft", String.valueOf(v.getPaddingLeft()));
		am.saveAttribute(activityName, widgetId, "paddingTop", String.valueOf(v.getPaddingTop()));
		am.saveAttribute(activityName, widgetId, "paddingRight", String.valueOf(v.getPaddingRight()));
		am.saveAttribute(activityName, widgetId, "paddingBottom", String.valueOf(v.getPaddingBottom()));
	}
	
	/**
 * Save margin values if LayoutParams is MarginLayoutParams.
 */
	private void saveMarginAttributes(WidgetAttributesManager am, String activityName, String widgetId, View v) {
		if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
			ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
			am.saveAttribute(activityName, widgetId, "marginLeft", String.valueOf(lp.leftMargin));
			am.saveAttribute(activityName, widgetId, "marginTop", String.valueOf(lp.topMargin));
			am.saveAttribute(activityName, widgetId, "marginRight", String.valueOf(lp.rightMargin));
			am.saveAttribute(activityName, widgetId, "marginBottom", String.valueOf(lp.bottomMargin));
		}
	}
	
	/**
 * ==========================
 * WIDGET ATTRIBUTE SAVERS
 * ==========================
 */
	
	private void setDefaultLinearLayoutAttributes(WidgetLinear linearLayout, String widgetId) {
		try {
			LinearLayout ll = linearLayout.getLinearLayout();
			WidgetAttributesManager am = new WidgetAttributesManager(getContext(), projectPath, projectName, projectId, pkgName);
			
			String widthStr = convertSizeToString(ll.getLayoutParams().width);
			String heightStr = convertSizeToString(ll.getLayoutParams().height);
			
			am.saveAttribute(activityName, widgetId, "type", "LinearLayout");
			
			am.saveAttribute(activityName, widgetId, "orientation", (ll.getOrientation() == LinearLayout.HORIZONTAL) ? "horizontal" : "vertical");
			am.saveAttribute(activityName, widgetId, "backgroundColor", getBackgroundString(ll));
			am.saveAttribute(activityName, widgetId, "width", widthStr);
			am.saveAttribute(activityName, widgetId, "height", heightStr);
			am.saveAttribute(activityName, widgetId, "gravity", String.valueOf(ll.getGravity()));
			am.saveAttribute(activityName, widgetId, "weightSum", String.valueOf(ll.getWeightSum()));
			
			savePaddingAttributes(am, activityName, widgetId, ll);
			saveMarginAttributes(am, activityName, widgetId, ll);
			
		} catch (Exception e) {
			Log.e(TAG, "Error saving LinearLayout attributes: " + e.getMessage());
		}
	}
	
	private void setDefaultTextViewAttributes(WidgetTextView textView, String widgetId) {
		try {
			TextView tv = textView.getTextView();
			WidgetAttributesManager am = new WidgetAttributesManager(getContext(), projectPath, projectName, projectId, pkgName);
			
			String widthStr = convertSizeToString(tv.getLayoutParams().width);
			String heightStr = convertSizeToString(tv.getLayoutParams().height);
			
			am.saveAttribute(activityName, widgetId, "type", "TextView");
			am.saveAttribute(activityName, widgetId, "text", tv.getText().toString());
			am.saveAttribute(activityName, widgetId, "textSize", String.valueOf(tv.getTextSize() / getContext().getResources().getDisplayMetrics().scaledDensity));
			am.saveAttribute(activityName, widgetId, "textColor", String.format("#%08X", tv.getCurrentTextColor()));
			am.saveAttribute(activityName, widgetId, "backgroundColor", getBackgroundString(tv));
			am.saveAttribute(activityName, widgetId, "width", widthStr);
			am.saveAttribute(activityName, widgetId, "height", heightStr);
			am.saveAttribute(activityName, widgetId, "gravity", String.valueOf(tv.getGravity()));
			
			savePaddingAttributes(am, activityName, widgetId, tv);
			saveMarginAttributes(am, activityName, widgetId, tv);
			
		} catch (Exception e) {
			Log.e(TAG, "Error saving TextView attributes: " + e.getMessage());
		}
	}
	
	private void setDefaultButtonAttributes(WidgetButton button, String widgetId) {
		try {
			TextView btn = button.getTextView();
			WidgetAttributesManager am = new WidgetAttributesManager(getContext(), projectPath, projectName, projectId, pkgName);
			
			String widthStr = convertSizeToString(btn.getLayoutParams().width);
			String heightStr = convertSizeToString(btn.getLayoutParams().height);
			
			am.saveAttribute(activityName, widgetId, "type", "Button");
			am.saveAttribute(activityName, widgetId, "text", btn.getText().toString());
			am.saveAttribute(activityName, widgetId, "textSize", String.valueOf(btn.getTextSize() / getContext().getResources().getDisplayMetrics().scaledDensity));
			am.saveAttribute(activityName, widgetId, "textColor", String.format("#%08X", btn.getCurrentTextColor()));
			am.saveAttribute(activityName, widgetId, "backgroundColor", getBackgroundString(btn));
			am.saveAttribute(activityName, widgetId, "width", widthStr);
			am.saveAttribute(activityName, widgetId, "height", heightStr);
			am.saveAttribute(activityName, widgetId, "gravity", String.valueOf(btn.getGravity()));
			
			savePaddingAttributes(am, activityName, widgetId, btn);
			saveMarginAttributes(am, activityName, widgetId, btn);
			
		} catch (Exception e) {
			Log.e(TAG, "Error saving Button attributes: " + e.getMessage());
		}
	}
	
	private void setDefaultImageViewAttributes(WidgetImageView imageView, String widgetId) {
		try {
			WidgetAttributesManager am = new WidgetAttributesManager(getContext(), projectPath, projectName, projectId, pkgName);
			
			String widthStr = convertSizeToString(imageView.getLayoutParams().width);
			String heightStr = convertSizeToString(imageView.getLayoutParams().height);
			
			am.saveAttribute(activityName, widgetId, "type", "ImageView");
			am.saveAttribute(activityName, widgetId, "src", imageView.getImagePath() != null ? imageView.getImagePath() : "@drawable/ic_launcher");
			am.saveAttribute(activityName, widgetId, "backgroundColor", getBackgroundString(imageView));
			am.saveAttribute(activityName, widgetId, "width", widthStr);
			am.saveAttribute(activityName, widgetId, "height", heightStr);
			am.saveAttribute(activityName, widgetId, "scaleType", imageView.getScaleType().toString());
			
			savePaddingAttributes(am, activityName, widgetId, imageView);
			saveMarginAttributes(am, activityName, widgetId, imageView);
			
		} catch (Exception e) {
			Log.e(TAG, "Error saving ImageView attributes: " + e.getMessage());
		}
	}
	
	private void setDefaultWebViewAttributes(WidgetWebView webView, String widgetId) {
		try {
			WidgetAttributesManager am = new WidgetAttributesManager(getContext(), projectPath, projectName, projectId, pkgName);
			
			String widthStr = convertSizeToString(webView.getLayoutParams().width);
			String heightStr = convertSizeToString(webView.getLayoutParams().height);
			
			am.saveAttribute(activityName, widgetId, "type", "WebView");
			am.saveAttribute(activityName, widgetId, "url", "https://www.example.com");
			am.saveAttribute(activityName, widgetId, "backgroundColor", getBackgroundString(webView));
			am.saveAttribute(activityName, widgetId, "width", widthStr);
			am.saveAttribute(activityName, widgetId, "height", heightStr);
			
			savePaddingAttributes(am, activityName, widgetId, webView);
			saveMarginAttributes(am, activityName, widgetId, webView);
			
		} catch (Exception e) {
			Log.e(TAG, "Error saving WebView attributes: " + e.getMessage());
		}
	}
	
	private void setDefaultListViewAttributes(WidgetListView listView, String widgetId) {
		try {
			ListView lv = (ListView) listView.getChildAt(0); // internal mListView
			WidgetAttributesManager am = new WidgetAttributesManager(getContext(), projectPath, projectName, projectId, pkgName);
			
			String widthStr = convertSizeToString(lv.getLayoutParams().width);
			String heightStr = convertSizeToString(lv.getLayoutParams().height);
			
			am.saveAttribute(activityName, widgetId, "type", "ListView");
			am.saveAttribute(activityName, widgetId, "backgroundColor", getBackgroundString(lv));
			am.saveAttribute(activityName, widgetId, "width", widthStr);
			am.saveAttribute(activityName, widgetId, "height", heightStr);
			am.saveAttribute(activityName, widgetId, "dividerHeight", String.valueOf(lv.getDividerHeight()));
			am.saveAttribute(activityName, widgetId, "choiceMode", String.valueOf(lv.getChoiceMode()));
			
			if (lv.getDivider() instanceof ColorDrawable) {
				int color = ((ColorDrawable) lv.getDivider()).getColor();
				am.saveAttribute(activityName, widgetId, "dividerColor", String.format("#%08X", (0xFFFFFFFF & color)));
			}
			
			savePaddingAttributes(am, activityName, widgetId, lv);
			saveMarginAttributes(am, activityName, widgetId, lv);
			
		} catch (Exception e) {
			Log.e(TAG, "Error saving ListView attributes: " + e.getMessage());
		}
	}
	
	
	/**
 * Helper method to save attributes to global custom attributes map
 */
	private void saveToCustomAttributes(String widgetId, String attributeName, String value) {
		if (!widgetCustomAttributes.containsKey(widgetId)) {
			widgetCustomAttributes.put(widgetId, new HashMap<>());
		}
		widgetCustomAttributes.get(widgetId).put(attributeName, value);
	}
	
	/**
 * Get REAL color value from attributes
 */
	public static int getWidgetColor(String widgetId, String attributeName, int defaultColor) {
		try {
			if (widgetCustomAttributes.containsKey(widgetId)) {
				Map<String, String> attrs = widgetCustomAttributes.get(widgetId);
				if (attrs.containsKey(attributeName)) {
					String colorHex = attrs.get(attributeName);
					return Color.parseColor(colorHex);
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "Error getting color: " + e.getMessage());
		}
		return defaultColor;
	}
	
	/**
 * Get REAL text value from attributes
 */
	public static String getWidgetText(String widgetId, String attributeName, String defaultText) {
		try {
			if (widgetCustomAttributes.containsKey(widgetId)) {
				Map<String, String> attrs = widgetCustomAttributes.get(widgetId);
				if (attrs.containsKey(attributeName)) {
					return attrs.get(attributeName);
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "Error getting text: " + e.getMessage());
		}
		return defaultText;
	}
	
	/**
 * Get REAL size value from attributes
 */
	public static int getWidgetSize(String widgetId, String attributeName, int defaultSize) {
		try {
			if (widgetCustomAttributes.containsKey(widgetId)) {
				Map<String, String> attrs = widgetCustomAttributes.get(widgetId);
				if (attrs.containsKey(attributeName)) {
					String sizeStr = attrs.get(attributeName);
					// Handle "dp", "match_parent", "wrap_content"
					if (sizeStr.endsWith("dp")) {
						return Integer.parseInt(sizeStr.replace("dp", ""));
					} else if (sizeStr.equals("match_parent")) {
						return ViewGroup.LayoutParams.MATCH_PARENT;
					} else if (sizeStr.equals("wrap_content")) {
						return ViewGroup.LayoutParams.WRAP_CONTENT;
					} else {
						return Integer.parseInt(sizeStr);
					}
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "Error getting size: " + e.getMessage());
		}
		return defaultSize;
	}
	{
	}
	
	
	public void _setupDesignActivityListeners() {
		if (getActivity() instanceof DesignActivity) {
			DesignActivity designActivity = (DesignActivity) getActivity();
			
			// ActivityBean banake use karo
			ActivityBean currentActivity = new ActivityBean(
			designActivity.activityName, 
			designActivity.layoutName
			);
			activityName = designActivity.activityName;
			// Ab ActivityBean ke through values lo
			String activityName = currentActivity.getActivityName();
			String layoutName = currentActivity.getLayoutName();
			
			Log.d(TAG, "Using ActivityBean - Activity: " + activityName);
			Log.d(TAG, "Using ActivityBean - Layout: " + layoutName);
			
			// Use karo
			
		}
		
	}
	
	
	public void _overrides() {
		/*
 * TODO: Implement required overrides and lifecycle handling methods for proper
 * fragment and activity interaction within the Sketchware-like environment.
 *
 * This section contains placeholder and lifecycle override methods, including:
 * - _overrides(): Reserved for future custom override logic.
 * - onAttach(Context): Handles attaching the fragment to the DesignActivity context 
 *   and registers the activity name change listener.
 * - onDetach(): Cleans up listeners to prevent memory leaks and maintain system stability.
 * - onActivityNameChanged(String): Updates the current activity name reference.
 * - onLayoutNameChanged(String): Updates the current layout name reference.
 *
 * Note: Ensure these methods are properly implemented to maintain compatibility 
 * and stability within the Sketchware environment. Do not remove these overrides, 
 * as they are essential for activity-fragment synchronization and lifecycle management.
 */
		
	}
	@Override
	public void onDetach() {
		super.onDetach();
		widgetInteractionListener = null;
		if (designActivity != null) {
			designActivity.setOnActivityNameChangeListener(null);
		}
	}
	
	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		if (context instanceof DesignActivity) {
			designActivity = (DesignActivity) context;
			designActivity.setOnActivityNameChangeListener(this);
		}
	}
	@Override
	public void onActivityNameChanged(String newActivityName) {
		if (newActivityName != null && !newActivityName.isEmpty()) {
			activityName = newActivityName;
			if (dialogManager != null) {
				dialogManager.setActivityName(activityName);
			}
		}
	}
	
	@Override
	public void onLayoutNameChanged(String newLayoutName) {
		if (newLayoutName != null && !newLayoutName.isEmpty()) {
			layoutName = newLayoutName;
		}
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		if (activityName != null && !activityName.isEmpty()) {
			dialogManager.setActivityName(activityName);
		}
	}
	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("activity_name", activityName);
		outState.putString("layout_name", layoutName);
	}
	{
	}
	
}