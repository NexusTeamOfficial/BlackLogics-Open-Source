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
import androidx.recyclerview.*;
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
import com.nexusteam.internal.os.layouteditor.widget.WidgetContract;
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
import java.util.Stack;
import com.besome.blacklogics.model.*;
import androidx.lifecycle.ViewModelProvider;
import com.besome.blacklogics.model.WidgetViewModel;
import com.besome.blacklogics.file.ProjectFileManager;
import android.util.Log;
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
// Add these to your existing imports
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


public class ViewEditorFragmentActivity extends Fragment implements OnDragListener, OnLongClickListener  { 
	
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
	String sc_id;
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
	
	private LinearLayout editorLinearLayout1;
	private LinearLayout lixeira;
	private LinearLayout linear8;
	private ScrollView vscroll3;
	private View view9;
	private LinearLayout fundo_landscape;
	private LinearLayout linear3;
	private RelativeLayout relativelayout2;
	private LinearLayout widgets;
	private TextView textview4;
	private MyDragWidget widget_linear;
	private MyDragWidget widget_frame;
	private MyDragWidget widget_scrollview;
	private MyDragWidget widget_horizontalscrollview;
	private MyDragWidget widget_textview;
	private MyDragWidget widget_edit_text;
	private MyDragWidget widget_checkbox;
	private MyDragWidget widget_switch;
	private MyDragWidget widget_button;
	private MyDragWidget widget_image_view;
	private MyDragWidget widget_circle_image_view;
	private MyDragWidget widget_radio_button;
	private MyDragWidget widget_seek_bar;
	private MyDragWidget widget_progress_bar;
	private MyDragWidget widget_rating_view;
	private MyDragWidget widget_search_view;
	private MyDragWidget widget_video_view;
	private MyDragWidget widget_web_view;
	private MyDragWidget widget_list_view;
	private MyDragWidget viewpager;
	private MyDragWidget widget_digital_clock;
	private MyDragWidget widget_time_picker;
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
		context = getContext();
		widgetpropertiesLinearLayout1 = (LinearLayout) _view.findViewById(R.id.widgetpropertiesLinearLayout1);
		widget_width = (CardView) _view.findViewById(R.id.widget_width);
		widget_height = (CardView) _view.findViewById(R.id.widget_height);
		widget_text = (CardView) _view.findViewById(R.id.text);
		widget_id = (CardView) _view.findViewById(R.id.widget_id);
		widget_src = (CardView) _view.findViewById(R.id.widget_src);
		translationX = (CardView) _view.findViewById(R.id.translationX);
		transY = (CardView) _view.findViewById(R.id.transY);
		colorText = (CardView) _view.findViewById(R.id.colorText);
		textSize = (CardView) _view.findViewById(R.id.textSize);
		textStyle = (CardView) _view.findViewById(R.id.textStyle);
		Lines = (CardView) _view.findViewById(R.id.Lines);
		margin = (CardView) _view.findViewById(R.id.margin);
		padding = (CardView) _view.findViewById(R.id.padding);
		background = (CardView) _view.findViewById(R.id.background);
		//ll_properties = (LinearLayout) _view.findViewById(R.id.ll_properties);
		
		viewModel = new ViewModelProvider(requireActivity()).get(WidgetViewModel.class);
		
		        // जब भी refreshTrigger ट्रिगर हो, विजेट्स रीलोड करें
		        viewModel.getRefreshTrigger().observe(getViewLifecycleOwner(), shouldRefresh -> {
				            if (shouldRefresh) {
						                reloadAllWidgets();
						                viewModel.refreshTrigger.setValue(false); // ✅ Correct (since refreshTrigger is public) // रीसेट
						            }
				        });
		viewBean = new ViewBean();
		instance = this;
		editorLinearLayout1 = _view.findViewById(R.id.editorLinearLayout1);
		lixeira = _view.findViewById(R.id.lixeira);
		linear8 = _view.findViewById(R.id.linear8);
		vscroll3 = _view.findViewById(R.id.vscroll3);
		view9 = _view.findViewById(R.id.view9);
		fundo_landscape = _view.findViewById(R.id.fundo_landscape);
		linear3 = _view.findViewById(R.id.linear3);
		relativelayout2 = _view.findViewById(R.id.relativelayout2);
		widgets = _view.findViewById(R.id.widgets);
		textview4 = _view.findViewById(R.id.textview4);
		widget_linear = _view.findViewById(R.id.widget_linear);
		widget_frame = _view.findViewById(R.id.widget_frame);
		widget_scrollview = _view.findViewById(R.id.widget_scrollview);
		widget_horizontalscrollview = _view.findViewById(R.id.widget_horizontalscrollview);
		widget_textview = _view.findViewById(R.id.widget_textview);
		widget_edit_text = _view.findViewById(R.id.widget_edit_text);
		widget_checkbox = _view.findViewById(R.id.widget_checkbox);
		widget_switch = _view.findViewById(R.id.widget_switch);
		widget_button = _view.findViewById(R.id.widget_button);
		widget_image_view = _view.findViewById(R.id.widget_image_view);
		widget_circle_image_view = _view.findViewById(R.id.widget_circle_image_view);
		widget_radio_button = _view.findViewById(R.id.widget_radio_button);
		widget_seek_bar = _view.findViewById(R.id.widget_seek_bar);
		widget_progress_bar = _view.findViewById(R.id.widget_progress_bar);
		widget_rating_view = _view.findViewById(R.id.widget_rating_view);
		widget_search_view = _view.findViewById(R.id.widget_search_view);
		widget_video_view = _view.findViewById(R.id.widget_video_view);
		widget_web_view = _view.findViewById(R.id.widget_web_view);
		widget_list_view = _view.findViewById(R.id.widget_list_view);
		viewpager = _view.findViewById(R.id.viewpager);
		widget_digital_clock = _view.findViewById(R.id.widget_digital_clock);
		widget_time_picker = _view.findViewById(R.id.widget_time_picker);
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
		context = getContext();
		/**
TUDO : PROJECT PATH FOR SAVE AND LOAD LAYOUT HELP
**/
		fileManager = new ProjectFileManager(getContext(), DesignActivity.getScId());
		//currentLayoutWidgets = new ArrayList<>();
		allWidgetsMap = new ArrayMap<>();
		
		tv_view_name.setText(DesignActivity.currentActivityBean.getLayoutName() + ".xml");
		try{
			projectPath = requireActivity().getIntent().getStringExtra("projectPath");
			sc_id = requireActivity().getIntent().getStringExtra("sc_id");
			scName = requireActivity().getIntent().getStringExtra("scName");
		}catch(Exception e){
			SketchwareUtil.showMessage(getContext().getApplicationContext(), e.toString());
		}
		String listenersDir = projectPath + "/listeners/";
		FileUtil.makeDir(listenersDir);
		
		/**
TUDO : SAVED LAYOUT LOAD
**/
		
		    try {
			        if (getContext() != null) {
				            projectPath = requireActivity().getIntent().getStringExtra("projectPath");
				        }
			    } catch(Exception e) {
			        if (getContext() != null) {
				            SketchwareUtil.showMessage(getContext().getApplicationContext(), e.toString());
				        }
			    }
		    
		    if (getContext() != null) {
			        loadView();
			    }
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
		widget_edit_text.setOnLongClickListener(this);
		widget_button.setOnLongClickListener(this);
		widget_image_view.setOnLongClickListener(this);
		widget_web_view.setOnLongClickListener(this);
		widget_video_view.setOnLongClickListener(this);
		widget_linear.setOnLongClickListener(this);
		widget_list_view.setOnLongClickListener(this);
		widget_frame.setOnLongClickListener(this);
		widget_scrollview.setOnLongClickListener(this);
		widget_horizontalscrollview.setOnLongClickListener(this);
		widget_checkbox.setOnLongClickListener(this);
		widget_switch.setOnLongClickListener(this);
		widget_circle_image_view.setOnLongClickListener(this);
		widget_radio_button.setOnLongClickListener(this);
		widget_seek_bar.setOnLongClickListener(this);
		widget_progress_bar.setOnLongClickListener(this);
		widget_rating_view.setOnLongClickListener(this);
		widget_search_view.setOnLongClickListener(this);
		viewpager.setOnLongClickListener(this);
		widget_digital_clock.setOnLongClickListener(this);
		widget_time_picker.setOnLongClickListener(this);
		view_location = new View(getContext());
				view_location.setBackgroundColor(Color.parseColor("#DDDDDD"));
				view_location.setLayoutParams(new ViewGroup.LayoutParams(0, 0));
				view_location.setElevation(3);
		/*
TUDO : SET WIDGETS PROPERTIES 
*/
		DesignActivity.widget_width.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View p1) {
						widgetWidth("layout_width","",p1);
				}
		});
		DesignActivity.widget_height.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View p1) {
						widgetHeight("layout_height","",p1);
				}
		}); 
		DesignActivity.widget_text.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View p1) {
						textWidget("Text","",p1);
				}
		});
		DesignActivity.widget_id.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View p1) {
						idWidget("ID","",p1); 
				}
		});
		DesignActivity.widget_src.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View p1) {
						widgetSrc("Select Image", "Choose image from storage", p1);
				}
		});
		DesignActivity.transY.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View p1) {
						showWidgetTranslationYDialog("TranslationY", "", p1);
				}
		});
		DesignActivity.translationX.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View p1) {
						showWidgetTranslationDialog("TranslationX", "", p1);
				}
		});
		DesignActivity.colorText.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View p1) {
						try {
								textColorDialog(p1);
						} catch(Exception e) { showMessage(e.toString());}
				}
		});
		DesignActivity.textSize.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View p1) {
						showTextSizeDialog(selectedWidget);
				}
		});
		DesignActivity.textStyle.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View p1) {
						try {
								showTextStyleOnlyDialog(p1);
						} catch(Exception e) { showMessage(e.toString());}
				}
		});
		DesignActivity.Lines.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View p1) {
						try {
								setLines("Set numbers", "", selectedWidget);;
						} catch(Exception e) { showMessage(e.toString());}
				}
		});
		DesignActivity.background.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View p1) {
						try {
								backgroundColorDialog(p1);
						} catch(Exception e) { showMessage(e.toString());}
				}
		});
		DesignActivity.margin.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View p1) {
						try {
								setMarginDialog("Set margin", "", p1);
						} catch(Exception e) { showMessage(e.toString());}
				}
		});
		DesignActivity.padding.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View p1) {
						try {
								setPaddingDialog("Set Padding", "", p1);
						} catch(Exception e) { showMessage(e.toString());}
				}
		});
		DesignActivity.widget_inject_attributes.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
						showInjectAttributesDialog(v);
				}
		});
		DesignActivity.gravityLayout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View p1) {
						try {
								showGravityDialog("", "", p1);
						} catch(Exception e) { showMessage(e.toString());}
				}
		});
		DesignActivity.layoutGravity.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
						showLayoutGravityDialog("", "", v);
				}
		});
		DesignActivity.max_progress.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
						showMaxProgressDialog();
				}
		});
		DesignActivity.widget_scale.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
						showScaleTypeDialog();
				}
		});
		DesignActivity.progressStyle.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
						showProgressTypeDialog();
				}
		});
		
		DesignActivity.switchCheckState.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
						showSwitchCheckedDialog(v);
				}
		});
		DesignActivity.checkState.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
						showCheckedStateDialog(v);
				}
		});
		DesignActivity.widget_orientation.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
						showOrientationDialog("Orientation", "", v);
				}
		});
		DesignActivity.widget_convert.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
						showChangeClassDialog();
				}
		});
		DesignActivity.widget_weight.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
						showWeightDialog();
				}
		});
		
		ll.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
						unselectSelectedWidget();
				}
		});
		u();
		loadCustomAttributes();
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		try {
				if (getContext() != null) {
						projectPath = requireActivity().getIntent().getStringExtra("projectPath");
				}
		} catch(Exception e) {
				if (getContext() != null) {
						SketchwareUtil.showMessage(getContext().getApplicationContext(), e.toString());
				}
		}
		
		if (getContext() != null) {
				loadView();
		}/*
String xmlLayout = getXMLCode();
fileManager.saveWidgetIdsFromXml(xmlLayout, activityName);*/
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		/*String xmlLayout = getXMLCode();
fileManager.saveWidgetIdsFromXml(xmlLayout, activityName);**/
		
		
		loadView();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		loadView();
	}
	public void _a() {
		/*
TUDO : MAKED <A> MOREBLOCK FOR ADD THIS ADD SOURCE DIRECTLY FOR MADE IN SKETCHWARE NO RIQUERE<a> MORE BLOCK FOR ANDROID STUDIO AND MORE IDE'S
*/
	}
	/**
TUDO : ON WIDGET CLICK LISTENER
**/
	public /*static*/ class WidgetClickListener implements OnClickListener
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
			if (getContext() == null || getActivity() == null) {
					Log.e(TAG, "Invalid context or activity");
					return false;
			}
			Context context = getContext();
			unselectSelectedWidget();
			
			// Validate parent ViewGroup
			if (!(v.getParent() instanceof ViewGroup)) {
					Log.e(TAG, "Parent ViewGroup is null");
					return false;
			}
			ViewGroup vg = (ViewGroup) v.getParent();
			
			// Set view_location dimensions
			ViewGroup.LayoutParams locationParams = view_location.getLayoutParams();
			if (locationParams != null) {
					locationParams.width = v.getWidth();
					locationParams.height = v.getHeight();
					view_location.setLayoutParams(locationParams);
			}
			
			// Determine default index for reordering
			defaultIndex = (ll == vg.getParent()) ? ll.indexOfChild(vg) : -1;
			
			// Widget creation
		    widget = null;
			String widgetId = null;
			String widgetType = null;
			String widgetName = null;
			Runnable counter = null;
			
			int viewId = v.getId();
			if (viewId == R.id.widget_textview) {
					WidgetTextView textView = new WidgetTextView(context);
					widgetId = textView.newWidgetId();
					widgetName = "TextView" + textViewCounter;
					textView.setWidgetId(widgetId);
					textView.setWidgetName(widgetName);
					textView.setText("TextView");
					textView.setTextSize(12f);
					textView.setTextColor(Color.BLACK);
					textView.setGravity(Gravity.START);
					widget = textView;
					widgetType = "TextView";
					counter = () -> textViewCounter++;
			} else if (viewId == R.id.widget_image_view) {
					WidgetImageView imageView = new WidgetImageView(context);
					widgetId = imageView.newWidgetId();
					widgetName = "ImageView" + imageViewCounter;
					imageView.setWidgetId(widgetId);
					imageView.setWidgetName(widgetName);
					imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
					widget = imageView;
					widgetType = "ImageView";
					counter = () -> imageViewCounter++;
			} else if (viewId == R.id.widget_button) {
					WidgetButton button = new WidgetButton(context);
					widgetId = button.newWidgetId();
					widgetName = "Button" + buttonCounter;
					button.setWidgetId(widgetId);
					button.setWidgetName(widgetName);
					button.setText("Button");
					button.setTextSize(12f);
					button.setTextColor(Color.BLACK);
					button.setGravity(Gravity.START);
					widget = button;
					widgetType = "Button";
					counter = () -> buttonCounter++;
			} else if (viewId == R.id.widget_linear) {
					WidgetLinearLayout linearLayout = new WidgetLinearLayout(context);
					widgetId = linearLayout.newWidgetId();
					widgetName = "LinearLayout" + linearLayoutCounter;
					linearLayout.setWidgetId(widgetId);
					linearLayout.setWidgetName(widgetName);
					linearLayout.setOrientation(LinearLayout.VERTICAL);
				//	linearLayout.setGravity(Gravity.CENTER_VERTICAL);
					linearLayout.setBackgroundColor(Color.parseColor("#F5F5F5"));
					linearLayout.setOnDragListener(this);
					widget = linearLayout;
					widgetType = "LinearLayout";
					counter = () -> linearLayoutCounter++;
			} else if (viewId == R.id.widget_frame) {
					WidgetFrameLayout frameLayout = new WidgetFrameLayout(context);
					widgetId = frameLayout.newWidgetId();
					widgetName = "FrameLayout" + frameLayoutCounter;
					frameLayout.setWidgetId(widgetId);
					frameLayout.setWidgetName(widgetName);
				//	frameLayout.setGravity(Gravity.CENTER_VERTICAL);
					frameLayout.setBackgroundColor(Color.parseColor("#F5F5F5"));
					frameLayout.setOnDragListener(this);
					widget = frameLayout;
					widgetType = "FrameLayout";
					counter = () -> frameLayoutCounter++;
			} else if (viewId == R.id.widget_web_view) {
					WidgetWebView webView = new WidgetWebView(context);
					widgetId = webView.newWidgetId();
					widgetName = "WebView" + webViewCounter;
					webView.setWidgetId(widgetId);
					webView.setWidgetName(widgetName);
					webView.setLayoutParams(new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT));
					widget = webView;
					widgetType = "WebView";
					counter = () -> webViewCounter++;
			} else if (viewId == R.id.widget_list_view) {
					WidgetListView listView = new WidgetListView(context);
					widgetId = listView.newWidgetId();
					widgetName = "ListView" + listViewCounter;
					listView.setWidgetId(widgetId);
					listView.setWidgetName(widgetName);
					widget = listView;
					widgetType = "ListView";
					counter = () -> listViewCounter++;
			} else if (viewId == R.id.widget_edit_text) {
					WidgetEditText editText = new WidgetEditText(context);
					widgetId = editText.newWidgetId();
					widgetName = "EditText" + editTextCounter;
					editText.setWidgetId(widgetId);
					editText.setWidgetName(widgetName);
					editText.setHint("EditText"); // Assuming direct EditText extension
					widget = editText;
					widgetType = "EditText";
					counter = () -> editTextCounter++;
			} else if (viewId == R.id.viewpager) {
					WidgetViewPager viewPager = new WidgetViewPager(context);
					widgetId = viewPager.newWidgetId();
					widgetName = "ViewPager" + viewPagerCounter;
					viewPager.setWidgetId(widgetId);
					viewPager.setWidgetName(widgetName);
					viewPager.setLayoutParams(new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT));
					widget = viewPager;
					widgetType = "ViewPager";
					counter = () -> viewPagerCounter++;
			} else if (viewId == R.id.widget_checkbox) {
					WidgetCheckBox checkBox = new WidgetCheckBox(context);
					widgetId = checkBox.newWidgetId();
					widgetName = "CheckBox" + checkBoxCounter;
					checkBox.setWidgetId(widgetId);
					checkBox.setWidgetName(widgetName);
					checkBox.setText("CheckBox");
					checkBox.setTextSize(12f);
					checkBox.setTextColor(Color.BLACK);
					checkBox.setGravity(Gravity.START);
					checkBox.setChecked(false);
					widget = checkBox;
					widgetType = "CheckBox";
					counter = () -> checkBoxCounter++;
			} else if (viewId == R.id.widget_switch) {
					WidgetSwitch switchWidget = new WidgetSwitch(context);
					widgetId = switchWidget.newWidgetId();
					widgetName = "Switch" + switchCounter;
					switchWidget.setWidgetId(widgetId);
					switchWidget.setWidgetName(widgetName);
					switchWidget.setText("Switch");
					switchWidget.setTextSize(12f);
					switchWidget.setTextColor(Color.BLACK);
					switchWidget.setGravity(Gravity.START);
					switchWidget.setChecked(false);
					widget = switchWidget;
					widgetType = "Switch";
					counter = () -> switchCounter++;
			} else if (viewId == R.id.widget_video_view) {
					WidgetVideoView videoView = new WidgetVideoView(context);
					widgetId = videoView.newWidgetId();
					widgetName = "VideoView" + videoViewCounter;
					videoView.setWidgetId(widgetId);
					videoView.setWidgetName(widgetName);
					videoView.setLayoutParams(new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT));
					widget = videoView;
					widgetType = "VideoView";
					counter = () -> videoViewCounter++;
			} else if (viewId == R.id.widget_progress_bar) {
					WidgetProgressBar progressBar = new WidgetProgressBar(context);
					widgetId = progressBar.newWidgetId();
					widgetName = "ProgressBar" + progressBarCounter;
					progressBar.setWidgetId(widgetId);
					progressBar.setWidgetName(widgetName);
					progressBar.setProgress(0);
					progressBar.setMax(100);
					widget = progressBar;
					widgetType = "ProgressBar";
					counter = () -> progressBarCounter++;
			} else if (viewId == R.id.widget_seek_bar) {
					WidgetSeekBar seekBar = new WidgetSeekBar(context);
					widgetId = seekBar.newWidgetId();
					widgetName = "SeekBar" + seekBarCounter;
					seekBar.setWidgetId(widgetId);
					seekBar.setWidgetName(widgetName);
					seekBar.setProgress(0);
					seekBar.setMax(100);
					widget = seekBar;
					widgetType = "SeekBar";
					counter = () -> seekBarCounter++;
			} else if (viewId == R.id.widget_radio_button) {
					WidgetRadioButton radioButton = new WidgetRadioButton(context);
					widgetId = radioButton.newWidgetId();
					widgetName = "RadioButton" + radioButtonCounter;
					radioButton.setWidgetId(widgetId);
					radioButton.setWidgetName(widgetName);
					radioButton.setText("RadioButton");
					radioButton.setTextSize(12f);
					radioButton.setTextColor(Color.BLACK);
					radioButton.setGravity(Gravity.START);
					radioButton.setChecked(false);
					widget = radioButton;
					widgetType = "RadioButton";
					counter = () -> radioButtonCounter++;
			} else if (viewId == R.id.widget_search_view) {
					WidgetSearchView searchView = new WidgetSearchView(context);
					widgetId = searchView.newWidgetId();
					widgetName = "SearchView" + searchViewCounter;
					searchView.setWidgetId(widgetId);
					searchView.setWidgetName(widgetName);
					//searchView.setHint("Search"); // Assuming direct SearchView extension
					widget = searchView;
					widgetType = "SearchView";
					counter = () -> searchViewCounter++;
			} else if (viewId == R.id.widget_rating_view) {
					WidgetRatingView ratingView = new WidgetRatingView(context);
					widgetId = ratingView.newWidgetId();
					widgetName = "RatingView" + ratingViewCounter;
					ratingView.setWidgetId(widgetId);
					ratingView.setWidgetName(widgetName);
					ratingView.setRating(0.0f);
					ratingView.setNumStars(5);
					widget = ratingView;
					widgetType = "RatingView";
					counter = () -> ratingViewCounter++;
			} else if (viewId == R.id.widget_circle_image_view) {
					WidgetCircleImageView circleImageView = new WidgetCircleImageView(context);
					widgetId = circleImageView.newWidgetId();
					widgetName = "CircleImageView" + circleImageViewCounter;
					circleImageView.setWidgetId(widgetId);
					circleImageView.setWidgetName(widgetName);
					circleImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
					widget = circleImageView;
					widgetType = "CircleImageView";
					counter = () -> circleImageViewCounter++;
			} else if (viewId == R.id.widget_digital_clock) {
					WidgetDigitalClock digitalClock = new WidgetDigitalClock(context);
					widgetId = digitalClock.newWidgetId();
					widgetName = "DigitalClock" + digitalClockCounter;
					digitalClock.setWidgetId(widgetId);
					digitalClock.setWidgetName(widgetName);
					digitalClock.setLayoutParams(new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT));
					widget = digitalClock;
					widgetType = "DigitalClock";
					counter = () -> digitalClockCounter++;
			} else if (viewId == R.id.widget_time_picker) {
					WidgetTimePicker timePicker = new WidgetTimePicker(context);
					widgetId = timePicker.newWidgetId();
					widgetName = "TimePicker" + timePickerCounter;
					timePicker.setWidgetId(widgetId);
					timePicker.setWidgetName(widgetName);
					timePicker.setLayoutParams(new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT));
					widget = timePicker;
					widgetType = "TimePicker";
					counter = () -> timePickerCounter++;
			} else if (viewId == R.id.widget_scrollview) {
					WidgetScrollView scrollView = new WidgetScrollView(context);
					widgetId = scrollView.newWidgetId();
					widgetName = "ScrollView" + scrollViewCounter;
					scrollView.setWidgetId(widgetId);
					scrollView.setWidgetName(widgetName);
				//	scrollView.setGravity(Gravity.CENTER_VERTICAL);
					scrollView.setBackgroundColor(Color.parseColor("#F5F5F5"));
					scrollView.setOnDragListener(this);
					widget = scrollView;
					widgetType = "ScrollView";
					counter = () -> scrollViewCounter++;
			} else if (viewId == R.id.widget_horizontalscrollview) {
					WidgetHorizontalScrollView horizontalScrollView = new WidgetHorizontalScrollView(context);
					widgetId = horizontalScrollView.newWidgetId();
					widgetName = "HorizontalScrollView" + horizontalScrollViewCounter;
					horizontalScrollView.setWidgetId(widgetId);
					horizontalScrollView.setWidgetName(widgetName);
					//horizontalScrollView.setGravity(Gravity.CENTER_VERTICAL);
					horizontalScrollView.setBackgroundColor(Color.parseColor("#F5F5F5"));
					horizontalScrollView.setOnDragListener(this);
					widget = horizontalScrollView;
					widgetType = "HorizontalScrollView";
					counter = () -> horizontalScrollViewCounter++;
			}/* else if (viewId == R.id.widget_code_viewer) {
		WidgetCodeViewer codeViewer = new WidgetCodeViewer(context);
		widgetId = WidgetCodeViewer.newWidgetId();
		widgetName = "CodeViewer" + codeViewerCounter;
		codeViewer.setWidgetId(widgetId);
		codeViewer.setWidgetName(widgetName);
		codeViewer.setText("CodeViewer");
		codeViewer.setTextSize(12f);
		codeViewer.setTextColor(Color.BLACK);
		codeViewer.setGravity(Gravity.START);
		widget = codeViewer;
		widgetType = "CodeViewer";
		counter = () -> codeViewerCounter++;
	}*/else {
					mWidget = vg;
					return false;
			}
			
			// Common widget setup
			if (widget != null) {
					// Set default layout parameters
					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
					params.setMargins(0, 0, 0, 0);
					widget.setLayoutParams(params);
					
					// Set default padding
					widget.setPadding(8, 8, 8, 8);
					
					// Set default background
					widget.setBackgroundColor(Color.TRANSPARENT);
					
					// Set default visibility and alpha
					widget.setVisibility(View.VISIBLE);
					widget.setAlpha(1.0f);
					
					// Set event listeners
					widget.setOnLongClickListener(this);
					widget.setOnClickListener(new WidgetClickListener());
					widget.setOnDragListener(this);
					
					// Update data model and UI
					qrwt(widgetId, " ");
					this.viewBean.addWidget(widgetId, widgetType, widgetName); // Assuming viewBean exists elsewhere
					selectedWidget = widget;
					counter.run();
					mWidget = widget;
					
					a(); // Update UI or state
					
					// Animate widget selection
				//	Log.d(TAG, "Starting animation for widget: " + widget);
					widget.animate()
					.scaleX(1.1f)
					.scaleY(1.1f)
					.setDuration(100)
					.setListener(new AnimatorListenerAdapter() {
							@Override
							public void onAnimationEnd(Animator animation) {
								//	Log.d(TAG, "Animation ended, widget state: " + widget);
									if (widget.getParent() != null) {
											widget.animate()
											.scaleX(1.0f)
											.scaleY(1.0f)
											.setDuration(100)
											.start();
									} else {
											Log.w(TAG, "Widget is detached, skipping animation");
									}
							}
					})
					.start();
			}
			
			// Start drag operation
			ClipData dragData = ClipData.newPlainText("widget", widgetId);
			View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v) {
					@Override
					public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
							super.onProvideShadowMetrics(shadowSize, shadowTouchPoint);
							shadowSize.set(v.getWidth(), v.getHeight());
							shadowTouchPoint.set(v.getWidth() / 2, v.getHeight() / 2);
					}
			};
			
			if (v.startDragAndDrop(dragData, shadowBuilder, v, 0)) {
					if (ll == vg) {
							ll.removeView(v);
					}
					Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
							vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
					} else {
							vibrator.vibrate(50);
					}
			} else {
					Log.e(TAG, "Failed to start drag");
					return false;
			}
			return true;
	}
	// Helper method to apply ViewBean properties
	private void applyViewBeanProperties(View widget, ProjectActivityBean.ViewBean viewBean) {
	}
	
	@Override
	public boolean onDrag(View v, DragEvent event) {
			int action = event.getAction();
			View draggedView = (View) event.getLocalState();
			Rect lixeiraRect = new Rect();
			lixeira.getGlobalVisibleRect(lixeiraRect);
			float x = event.getX();
			float y = event.getY();
			long currentTime = System.currentTimeMillis();
			
			switch (action) {
					case DragEvent.ACTION_DRAG_STARTED:
					lixeira.setVisibility(View.VISIBLE);
					lixeira.setAlpha(0f);
					lixeira.animate().alpha(1f).setDuration(200).start();
					isDraggingOverLixeira = false;
					index = ll.getChildCount();
					addedInLayout = false;
					mView = null;
					view_location.setVisibility(View.VISIBLE);
					updateWidgetTree();
					return true;
					
					case DragEvent.ACTION_DRAG_LOCATION:
					if (currentTime - lastDragUpdate < DRAG_THROTTLE_MS) return true;
					lastDragUpdate = currentTime;
					
					boolean isOverLixeira = lixeiraRect.contains((int) x + v.getLeft(), (int) y + v.getTop());
					if (isOverLixeira != isDraggingOverLixeira) {
							lixeira.setBackgroundResource(isOverLixeira ? R.drawable.icon_delete_active : R.drawable.icon_delete);
							isDraggingOverLixeira = isOverLixeira;
					}
					
					if (mView == v) return true;
					mView = v;
					
					// Determine the target container
					ViewGroup targetContainer;
					if (v instanceof WidgetScrollView || v instanceof WidgetHorizontalScrollView) {
							ViewGroup scrollView = (ViewGroup) v;
							if (scrollView.getChildCount() == 0) {
									// No child yet, show a toast to prompt adding a ViewGroup
									TheBlockLogicsUtil.showToast(context, "Add a LinearLayout or FrameLayout first");
									return true;
							} else if (scrollView.getChildCount() > 1) {
									// Should not happen, log error
									Log.e(TAG, "ScrollView has multiple children");
									return true;
							} else {
									// Check if the child is a ViewGroup
									View child = scrollView.getChildAt(0);
									if (child instanceof ViewGroup) {
											targetContainer = (ViewGroup) child;
									} else {
											TheBlockLogicsUtil.showToast(context, "Cannot add to non-ViewGroup child");
											return true;
									}
							}
					} else if (v instanceof WidgetLinearLayout || v instanceof WidgetFrameLayout) {
							targetContainer = (ViewGroup) v;
					} else {
							targetContainer = ll;
					}
					
					index = calculateDropIndexOptimized(targetContainer, x, y);
					
					// Update view_location
					if (view_location.getParent() != targetContainer) {
							if (view_location.getParent() instanceof ViewGroup) {
									((ViewGroup) view_location.getParent()).removeView(view_location);
							}
							try {
									targetContainer.addView(view_location, index);
									view_location.setVisibility(View.VISIBLE);
							} catch (Exception e) {
									Log.e(TAG, "Error adding view_location: " + e.getMessage());
							}
					}
					return true;
					
					case DragEvent.ACTION_DROP:
					if (view_location.getParent() instanceof ViewGroup) {
							((ViewGroup) view_location.getParent()).removeView(view_location);
					}
					
					if (isDraggingOverLixeira) {
							if (draggedView != null && draggedView.getParent() != null) {
									String widgetId = WidgetUtil.getWidgetId(draggedView);
									draggedView.clearAnimation();
									((ViewGroup) draggedView.getParent()).removeView(draggedView);
									removeWidgetFromDataModel(widgetId);
									Toast.makeText(context, "Widget deleted", Toast.LENGTH_SHORT).show();
							}
					} else {
							// Determine the target container for dropping
							ViewGroup targetContainerBase;
							if (v instanceof WidgetScrollView || v instanceof WidgetHorizontalScrollView) {
									ViewGroup scrollView = (ViewGroup) v;
									if (scrollView.getChildCount() == 0) {
											TheBlockLogicsUtil.showToast(context, "Add a LinearLayout or FrameLayout first");
											return true;
									} else if (scrollView.getChildCount() > 1) {
											Log.e(TAG, "ScrollView has multiple children");
											return true;
									} else {
											View child = scrollView.getChildAt(0);
											if (child instanceof ViewGroup) {
													targetContainerBase = (ViewGroup) child;
											} else {
													TheBlockLogicsUtil.showToast(context, "Cannot add to non-ViewGroup child");
													return true;
											}
									}
							} else if (v instanceof WidgetLinearLayout || v instanceof WidgetFrameLayout) {
									targetContainerBase = (ViewGroup) v;
							} else {
									targetContainerBase = ll;
							}
							addWidgetToLayoutContainer(targetContainerBase, mWidget, index);
					}
					view_location.setVisibility(View.GONE);
					return true;
					
					case DragEvent.ACTION_DRAG_ENDED:
					if (mWidget != null) {
							mWidget.clearAnimation();
					}
					if (view_location.getParent() instanceof ViewGroup) {
							((ViewGroup) view_location.getParent()).removeView(view_location);
					}
					view_location.setVisibility(View.GONE);
					
					if (defaultIndex != -1 && !addedInLayout && !isDraggingOverLixeira) {
							addWidgetToLayoutContainer(ll, mWidget, defaultIndex);
					}
					
					lixeira.animate().alpha(0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
							@Override
							public void onAnimationEnd(Animator animation) {
									lixeira.setVisibility(View.GONE);
									isDraggingOverLixeira = false;
							}
					}).start();
					return true;
			}
			return true;
	}
	private void updateWidgetTree() {
			/*	widgetTree.clear();
	for (int i = 0; i < ll.getChildCount(); i++) {
	View widget = ll.getChildAt(i);
	RectF bounds = new RectF();
	widget.getHitRect(new Rect()); // Convert to RectF
	bounds.set(widget.getLeft(), widget.getTop(), widget.getRight(), widget.getBottom());
	widgetTree.insert(new QuadTree.Node(bounds, i));
	}*/
			//LAG CASE FOR COMMENTED
	}
	
	private int calculateDropIndexOptimized(ViewGroup container, float x, float y) {
			int index = 0;
			float minDistance = Float.MAX_VALUE;
			
			for (int i = 0; i < container.getChildCount(); i++) {
					View child = container.getChildAt(i);
					Rect childBounds = new Rect();
					child.getHitRect(childBounds);
					
					// Calculate distance to child's center
					float childCenterY = childBounds.centerY();
					float distance = Math.abs(childCenterY - y);
					
					if (distance < minDistance) {
							minDistance = distance;
							index = i + (y > childCenterY ? 1 : 0);
					}
			}
			return index;
	}
	// Simple QuadTree implementation
	private static class QuadTree {
			private static class Node {
					RectF bounds;
					int data;
					Node(RectF bounds, int data) {
							this.bounds = bounds;
							this.data = data;
					}
			}
			private List<Node> nodes = new ArrayList<>();
			private RectF bounds;
			
			QuadTree(RectF bounds) {
					this.bounds = bounds;
			}
			
			void clear() {
					nodes.clear();
			}
			
			void insert(Node node) {
					nodes.add(node);
			}
			
			List<Node> query(RectF range) {
					List<Node> result = new ArrayList<>();
					for (Node node : nodes) {
							if (range.intersect(node.bounds)) {
									result.add(node);
							}
					}
					return result;
			}
	}
	private void addWidgetToLayoutContainer(ViewGroup container, View widget, int index) {
			if (widget == null || container == null) {
					Log.e(TAG, "Invalid widget or container");
					return;
			}
			
			// Ensure widget is not already in the container
			if (widget.getParent() != null) {
					((ViewGroup) widget.getParent()).removeView(widget);
			}
			
			// Add widget to container
			try {
					container.addView(widget, index);
					addedInLayout = true;
					
					widget.setOnLongClickListener(this);
					widget.setOnDragListener(this);
					
					if (DesignActivity.abc != null) {
							String javaCode = DesignActivity.abc.getJavaCode();
							String xmlCode = DesignActivity.abc.getXmlCode();
							DesignActivity.abc.complex.setXmlCode(DesignActivity.abc.currentActivityBean.getLayoutName(), xmlCode);
							DesignActivity.abc.complex.setJavaCode(DesignActivity.abc.currentActivityBean.getActivityName(), javaCode);
					}
					
					// Save state for undo
					saveStateToUndo();
					container.requestLayout();
			} catch (Exception e) {
					Log.e(TAG, "Error adding widget to container: " + e.getMessage());
					TheBlockLogicsUtil.showToast(context, "Error adding widget");
			}
	}
	
	// Helper method to generate a unique widget ID
	private String generateUniqueWidgetId(View widget) {
			String widgetType = getWidgetType(widget);
			String baseId = widgetType.toLowerCase();
			int counter = 1;
			String newId = baseId + counter;
			
			while (isDuplicateId(newId, widget)) {
					counter++;
					newId = baseId + counter;
			}
			return newId;
	}
	private int calculateDropIndex(View v, float x, float y) {
			ViewGroup container = (v instanceof WidgetLinearLayout || v instanceof WidgetFrameLayout) ? (ViewGroup) v : ll;
			if (container.getChildCount() == 0) return 0;
			
			for (int i = 0; i < container.getChildCount(); i++) {
					View child = container.getChildAt(i);
					Rect rect = new Rect();
					child.getHitRect(rect);
					if (rect.contains((int) x, (int) y)) {
							return container instanceof LinearLayout ? i + 1 : i;
					}
			}
			return container.getChildCount();
	}
	
	// Helper method to remove widget from data model
	private void removeWidgetFromDataModel(String widgetId) {
			if (widgetId == null) return;
			
			// Remove from currentLayoutWidgets
			DesignActivity.currentLayoutWidgets.removeIf(bean -> Objects.equals(widgetId, bean.getWidgetId()));
			
			// Remove from allWidgetsMap
			String layoutKey = ViewEditorFragmentActivity.layoutName;
			Optional.ofNullable(DesignActivity.allWidgetsMap.get(layoutKey))
			.ifPresent(widgets -> {
					widgets.removeIf(bean -> Objects.equals(widgetId, bean.getWidgetId()));
					// Remove from nested children
					for (ProjectActivityBean.ViewBean bean : widgets) {
							List<ProjectActivityBean.ViewBean> children = bean.getChildren();
							if (children != null) {
									children.removeIf(child -> Objects.equals(widgetId, child.getWidgetId()));
							}
					}
			});
			
			// Notify ViewModel of changes
			//viewModel.setWidgets(DesignActivity.currentLayoutWidgets);
			saveWidgetsToFile();
	}
	// Helper method to add widget to main layout
	private void addWidgetInLayout(View v, int index) {
			saveStateToUndo();
			if (v == null) return;
			
			// Remove from current parent
			Optional.ofNullable(v.getParent())
			.filter(parent -> parent instanceof ViewGroup)
			.ifPresent(parent -> ((ViewGroup) parent).removeView(v));
			
			// Set LayoutParams
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.WRAP_CONTENT
			);
			v.setLayoutParams(lp);
			
			// Add view
			ll.addView(v, index);
			ll.requestLayout();
			addedInLayout = true;
			
			// Update code and data model
			if (DesignActivity.abc != null) {
					String javaCode = DesignActivity.abc.getJavaCode();
					String xmlCode = DesignActivity.abc.getXmlCode();
					DesignActivity.abc.complex.setXmlCode(DesignActivity.abc.currentActivityBean.getLayoutName(), xmlCode);
					DesignActivity.abc.complex.setJavaCode(DesignActivity.abc.currentActivityBean.getActivityName(), javaCode);
			}
			
			ProjectActivityBean.ViewBean viewBean = DesignActivity.createViewBean(v);
			DesignActivity.currentLayoutWidgets.add(viewBean);
			
			// Update currentActivityBean
			if (DesignActivity.currentActivityBean.getActivityName().equals(ViewEditorFragmentActivity.activityName)) {
					List<ProjectActivityBean.ViewBean> activityWidgets = DesignActivity.currentActivityBean.getWidgets();
					if (activityWidgets == null) {
							activityWidgets = new ArrayList<>();
							DesignActivity.currentActivityBean.setWidgets(activityWidgets);
					}
					activityWidgets.add(viewBean);
			}
			
			// Update allWidgetsMap
			String layoutKey = ViewEditorFragmentActivity.layoutName;
			List<ProjectActivityBean.ViewBean> layoutWidgets = DesignActivity.allWidgetsMap.computeIfAbsent(
			layoutKey, k -> new ArrayList<>()
			);
			layoutWidgets.add(viewBean);
			
			saveWidgetsToFile();
	}
	
	private void addWidgetInLayoutS(View v, int index) {
			saveStateToUndo(); 
			if (v == null) return; // Safety check
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
			
			
			ProjectActivityBean.ViewBean viewBean = DesignActivity.createViewBean(v);
			
			// Add to temporary current layout widgets list
			DesignActivity.currentLayoutWidgets.add(viewBean);
			
			// Add to currentActivityBean's widgets
			if (DesignActivity.currentActivityBean.getActivityName().equals(ViewEditorFragmentActivity.activityName)) {
					List<ProjectActivityBean.ViewBean> activityWidgets = DesignActivity.currentActivityBean.getWidgets();
					if (activityWidgets == null) {
							activityWidgets = new ArrayList<>();
							DesignActivity.currentActivityBean.setWidgets(activityWidgets);
					}
					activityWidgets.add(viewBean);
			}
			
			// Optionally add to allWidgetsMap if needed
			String layoutKey = ViewEditorFragmentActivity.layoutName;
			List<ProjectActivityBean.ViewBean> layoutWidgets = DesignActivity.allWidgetsMap.get(layoutKey);
			if (layoutWidgets == null) {
					layoutWidgets = new ArrayList<>();
					DesignActivity.allWidgetsMap.put(layoutKey, layoutWidgets);
			}
			layoutWidgets.add(viewBean);
	}
	
	/**
TUDO : LOAD LAYOUT 
**/
	public void loadView() {
		    loadLayout();
		    /*
	ll.removeAllViews();
	File file = new File(projectPath + "/saved_project.json");
    DesignActivity.loadActivityFromJson(projectPath + "/saved_project.json");
	if (file.exists()) {
		try {
			String jsonData = TheBlockLogicsUtil.decodeBase64(TheBlockLogicsUtil.readFile(file.getAbsolutePath()));
			JSONObject projectObject = new JSONObject(jsonData);
			
			// Load project settings
			projectName = projectObject.optString("project_name", "MyProject");
			pkgName = projectObject.optString("package_name", "com.nexusteam.internal.os.layouteditor");
			useAndroidX = projectObject.optBoolean("use_androidx", false);
			
			viewBean.setActivityName(projectObject.optString("activity_name", "MainActivity"));
			viewBean.setLayoutName(projectObject.optString("layout_name", "main"));
			
			// Clear existing activities
			otherActivities.clear();
			
			JSONArray activitiesArray = projectObject.getJSONArray("activities");
			
			// Find and load the current activity
			boolean currentActivityFound = false;
			for (int a = 0; a < activitiesArray.length(); a++) {
				JSONObject activityData = activitiesArray.getJSONObject(a);
				
				if (activityData.getString("activity_name").equals(DesignActivity.currentActivityBean.getActivityName())) {
					// This is the current activity we want to load
					layoutName = activityData.getString("layout_name");
					isMainActivity = activityData.optBoolean("is_main_activity", false);
					
					// Load widgets for current activity
					JSONArray widgetsArray = activityData.getJSONArray("widgets");
					loadWidgetsFromArray(widgetsArray);
					currentActivityFound = true;
				} else {
					// Store other activities for later use
					ActivityData activity = new ActivityData(
					activityData.getString("activity_name"),
					activityData.getString("layout_name"),
					activityData.optBoolean("is_main_activity", false)
					);
					
					JSONArray widgetsArray = activityData.getJSONArray("widgets");
					for (int i = 0; i < widgetsArray.length(); i++) {
						JSONObject widgetData = widgetsArray.getJSONObject(i);
						String widgetName = widgetData.getString("name_s");
						String widgetId = widgetData.getString("id");
						View widget = createWidgetWithoutAdding(widgetData);
						if (widget != null) {
							activity.addWidget(widget);
						}
						
						String widgetType = widgetName;
						if (widgetName.equals("WidgetTextView")) widgetType = "TextView";
						else if (widgetName.equals("WidgetButton")) widgetType = "Button";
						else if (widgetName.equals("WidgetImageView")) widgetType = "ImageView";
						else if (widgetName.equals("WidgetWebView")) widgetType = "WebView";
						else if (widgetName.equals("WidgetListView")) widgetType = "ListView";
						else if (widgetName.equals("WidgetLinear")) widgetType = "LinearLayout";
                        else if (widgetName.equals("WidgetEditText")) widgetType = "EditText";
						
						viewBean.addWidget(widgetId, widgetType, widgetName);
					}
					
					otherActivities.add(activity);
                    
                    
                    WidgetClickListenerManager.getInstance().loadListenersFromFile();
				}
			}
			
			if (!currentActivityFound) {
				//showMessage("Activity not found - loading default");
				// Load empty layout for new activity
			}
		} catch (Exception e) {
			Activity activity = getActivity();
			if (activity != null) {
				TheBlockLogicsUtil.showToast(activity, "Load Error: " + e.toString());
				//	projectPath = activity.getIntent().getStringExtra("projectPath");
			}
		}
	}*/
	}
	
	// Helper method to create widget without adding to layout
	private View createWidgetWithoutAdding(JSONObject widgetData) throws JSONException {
			String widgetName = widgetData.getString("name_s");
			String widgetId = widgetData.getString("id");
			int width = widgetData.getInt("width");
			int height = widgetData.getInt("height");
			
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
			
			View widget = createWidgetFromJson(widgetName, widgetId, lp, widgetData);
			
			if (widget != null) {
					applyCommonWidgetProperties(widget, widgetData);
			}
			
			return widget;
	}
	
	private void loadWidgetsFromArray(JSONArray widgetsArray) throws JSONException {
			for (int i = 0; i < widgetsArray.length(); i++) {
					JSONObject widgetData = widgetsArray.getJSONObject(i);
					
					LinearLayout container = new LinearLayout(requireActivity());
					container.setOrientation(LinearLayout.VERTICAL);
					container.setOnDragListener(this);
					container.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
					
					// Widget properties
					String widgetName = widgetData.getString("name_s");
					String widgetId = widgetData.getString("id");
					int width = widgetData.getInt("width");
					int height = widgetData.getInt("height");
					
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
					
					// Create widget based on type
					View widget = createWidgetFromJson(widgetName, widgetId, lp, widgetData);
					
					// Apply common view properties
					if (widget != null) {
							applyCommonWidgetProperties(widget, widgetData);
							
							container.addView(widget);
							addWidgetInLayout(container, ll.getChildCount());
					}
			}
	}
	
	private View createWidgetFromJson(String widgetName, String widgetId, 
	LinearLayout.LayoutParams lp, JSONObject widgetData) throws JSONException {
			View widget = null;
			
			if (widgetName.equals(WidgetTextView.class.getSimpleName())) {
					WidgetTextView widgetTextView = new WidgetTextView(requireActivity());
					widgetTextView.setLayoutParams(lp);
					widgetTextView.setWidgetId(widgetId);
					widgetTextView.setOnClickListener(new WidgetClickListener());
					widgetTextView.setOnLongClickListener(this);
					
					if (widgetData.has("text")) {
							widgetTextView.getTextView().setText(widgetData.getString("text"));
					}
					if (widgetData.has("text_size")) {
							widgetTextView.getTextView().setTextSize(TypedValue.COMPLEX_UNIT_PX, 
							(float)widgetData.getDouble("text_size"));
					}
					if (widgetData.has("text_color")) {
							widgetTextView.getTextView().setTextColor(widgetData.getInt("text_color"));
					}
					if (widgetData.has("gravity")) {
							widgetTextView.getTextView().setGravity(widgetData.getInt("gravity"));
					}
					
					widget = widgetTextView;
			} 
			else if (widgetName.equals(WidgetButton.class.getSimpleName())) {
					WidgetButton widgetButton = new WidgetButton(requireActivity());
					widgetButton.setLayoutParams(lp);
					widgetButton.setWidgetId(widgetId);
					widgetButton.setOnClickListener(new WidgetClickListener());
					widgetButton.setOnLongClickListener(this);
					
					if (widgetData.has("text")) {
							widgetButton.getTextView().setText(widgetData.getString("text"));
					}
					if (widgetData.has("text_size")) {
							widgetButton.getTextView().setTextSize(TypedValue.COMPLEX_UNIT_PX, 
							(float)widgetData.getDouble("text_size"));
					}
					if (widgetData.has("text_color")) {
							widgetButton.getTextView().setTextColor(widgetData.getInt("text_color"));
					}
					if (widgetData.has("gravity")) {
							widgetButton.getTextView().setGravity(widgetData.getInt("gravity"));
					}
					
					widget = widgetButton;
			} 
			else if (widgetName.equals(WidgetImageView.class.getSimpleName())) {
					WidgetImageView widgetImageView = new WidgetImageView(requireActivity());
					widgetImageView.setLayoutParams(lp);
					widgetImageView.setWidgetId(widgetId);
					widgetImageView.setOnClickListener(new WidgetClickListener());
					widgetImageView.setOnLongClickListener(this);
					
					if (widgetData.has("image_path")) {
							String imagePath = widgetData.getString("image_path");
							Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
							if (bitmap != null) {
									widgetImageView.setImageBitmap(bitmap);
							}
					}
					if (widgetData.has("scale_type")) {
							String scaleType = widgetData.getString("scale_type");
							// Convert string to ScaleType enum
							// This would need proper mapping based on your ScaleType values
					}
					
					widget = widgetImageView;
			} 
			else if (widgetName.equals(WidgetWebView.class.getSimpleName())) {
					WidgetWebView widgetWebView = new WidgetWebView(requireActivity());
					widgetWebView.setLayoutParams(lp);
					widgetWebView.setWidgetId(widgetId);
					widgetWebView.setOnClickListener(new WidgetClickListener());
					widgetWebView.setOnLongClickListener(this);
					widget = widgetWebView;
			}
		    else if (widgetName.equals(WidgetEditText.class.getSimpleName())) {
					/*WidgetEditText widgetTextView = new WidgetEditText(requireActivity());
		widgetTextView.setLayoutParams(lp);
		widgetTextView.setWidgetId(widgetId);
		widgetTextView.setOnClickListener(new WidgetClickListener());
		widgetTextView.setOnLongClickListener(this);
		
		if (widgetData.has("text")) {
			widgetTextView.getTextView().setText(widgetData.getString("text"));
		}
		if (widgetData.has("text_size")) {
			widgetTextView.getTextView().setTextSize(TypedValue.COMPLEX_UNIT_PX, 
			(float)widgetData.getDouble("text_size"));
		}
		if (widgetData.has("text_color")) {
			widgetTextView.getTextView().setTextColor(widgetData.getInt("text_color"));
		}
		if (widgetData.has("gravity")) {
			widgetTextView.getTextView().setGravity(widgetData.getInt("gravity"));
		}
        if (widgetData.has("hint")) {
			widgetTextView.getTextView().setHint(widgetData.getString("hint"));
		}
		
		widget = widgetTextView;*/
			} 
			
			return widget;
	}
	
	private void applyCommonWidgetProperties(View widget, JSONObject widgetData) throws JSONException {
			if (widgetData.has("visibility")) {
					widget.setVisibility(widgetData.getInt("visibility"));
			}
			if (widgetData.has("alpha")) {
					widget.setAlpha((float)widgetData.getDouble("alpha"));
			}
			if (widgetData.has("rotation")) {
					widget.setRotation((float)widgetData.getDouble("rotation"));
			}
			if (widgetData.has("scaleX")) {
					widget.setScaleX((float)widgetData.getDouble("scaleX"));
			}
			if (widgetData.has("scaleY")) {
					widget.setScaleY((float)widgetData.getDouble("scaleY"));
			}
			if (widgetData.has("translationX")) {
					widget.setTranslationX((float)widgetData.getDouble("translationX"));
			}
			if (widgetData.has("translationY")) {
					widget.setTranslationY((float)widgetData.getDouble("translationY"));
			}
			if (widgetData.has("background_color")) {
					widget.setBackgroundColor(widgetData.getInt("background_color"));
			}
			
			// Apply margins
			if (widgetData.has("margin_left") || widgetData.has("margin_top") || 
			widgetData.has("margin_right") || widgetData.has("margin_bottom")) {
					
					int left = widgetData.optInt("margin_left", 0);
					int top = widgetData.optInt("margin_top", 0);
					int right = widgetData.optInt("margin_right", 0);
					int bottom = widgetData.optInt("margin_bottom", 0);
					
					ViewGroup.LayoutParams params = widget.getLayoutParams();
					if (params instanceof ViewGroup.MarginLayoutParams) {
							((ViewGroup.MarginLayoutParams)params).setMargins(left, top, right, bottom);
							widget.setLayoutParams(params);
					}
			}
			
			// Apply padding
			if (widgetData.has("padding_left") || widgetData.has("padding_top") || 
			widgetData.has("padding_right") || widgetData.has("padding_bottom")) {
					
					int left = widgetData.optInt("padding_left", 0);
					int top = widgetData.optInt("padding_top", 0);
					int right = widgetData.optInt("padding_right", 0);
					int bottom = widgetData.optInt("padding_bottom", 0);
					
					widget.setPadding(left, top, right, bottom);
			}
			
			// Load and apply custom attributes
			if (widgetData.has("custom_attributes")) {
					JSONObject customAttrs = widgetData.getJSONObject("custom_attributes");
					Map<String, String> customAttributes = new HashMap<>();
					Iterator<String> keys = customAttrs.keys();
					while (keys.hasNext()) {
							String key = keys.next();
							String value = customAttrs.getString(key);
							customAttributes.put(key, value);
							injectCustomAttributes(widget, key, value); // Apply immediately
					}
					widget.setTag(R.id.custom_attributes_tag, customAttributes);
			}
	}
	
	/**
TUDO : textWidget SET PROPERTY'S
**/
	public void textWidget(String titulo, String texto, final View base) {
		    if (selectedWidget == null) {
			        showToast("No widget selected");
			        return;
			    }
		
		    // Save current state for undo
		    saveStateToUndo();
		
		    // Inflate dialog
		    LayoutInflater inflater = LayoutInflater.from(getContext());
		    final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
		    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		    builder.setView(dialogView);
		    final AlertDialog alert = builder.create();
		
		    // Configure dialog window
		    if (alert.getWindow() != null) {
			        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
			        alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
			        alert.getWindow().setGravity(Gravity.CENTER);
			    }
		
		    // Initialize dialog views
		    ImageView icon = dialogView.findViewById(R.id.img_icon);
		    TextView tituloDlg = dialogView.findViewById(R.id.tv_title);
		    TextView mensagemDlg = dialogView.findViewById(R.id.tv_message);
		    LinearLayout tamanho = dialogView.findViewById(R.id.widget_temanho);
		    final EditText widgetTextId = dialogView.findViewById(R.id.et_widget);
		    final EditText etTamanho = dialogView.findViewById(R.id.ed_input);
		    Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
		    Button btnSave = dialogView.findViewById(R.id.btn_ok);
		
		    // Configure input fields
		    widgetTextId.setTextIsSelectable(true);
		    widgetTextId.setFocusable(true);
		    widgetTextId.setLongClickable(true);
		    widgetTextId.setFocusableInTouchMode(true);
		    widgetTextId.setClickable(true);
		    etTamanho.setFocusable(true);
		    etTamanho.setFocusableInTouchMode(true);
		    etTamanho.setClickable(true);
		    etTamanho.setLongClickable(true);
		    etTamanho.setTextIsSelectable(true);
		
		    // Set dialog content
		    icon.setImageResource(R.drawable.abc_96);
		    tituloDlg.setText(titulo);
		    mensagemDlg.setVisibility(View.GONE);
		    tamanho.setVisibility(View.GONE);
		
		    // Set current widget text
		    TextView textView = WidgetUtil.getTextViewOfWidget(selectedWidget);
		    if (textView != null) {
			        widgetTextId.setText(textView.getText().toString());
			    } else {
			        showToast("Cannot edit text for this widget");
			        alert.dismiss();
			        return;
			    }
		
		    // Show dialog
		    alert.show();
		
		    // Cancel button
		    btnCancel.setOnClickListener(v -> alert.dismiss());
		
		    // Save button
		    btnSave.setOnClickListener(v -> {
			        String newText = widgetTextId.getText().toString().trim();
			        // Removed empty text validation to allow empty strings
			        // Previously: if (newText.isEmpty()) { showToast("Text cannot be empty"); return; }
			
			        // Apply text to widget
			        if (textView != null) {
				            textView.setText(newText);
				            selectedWidget.requestLayout();
				/*
            // Update ViewBean
            ProjectActivityBean.ViewBean viewBean = DesignActivity.createViewBean(selectedWidget);
            if (viewBean != null) {
                viewBean.setText(newText);
                updateWidgetInLayout(viewBean);
            }

            // Save changes
            a(); // New save method*/
				
				            // Clear redo stack
				            redoStack.clear();
				            showToast("Text updated");
				        }
			
			        alert.dismiss();
			    });
	}
	/**
TUDO : widgetWidth SETTINGS
**/
	public void widgetWidth(String titulo, String texto, final View y) {
		    if (selectedWidget == null) {
			        showToast("No widget selected");
			        return;
			    }
		
		    // Save current state for undo
		    saveStateToUndo();
		
		    // Inflate dialog
		    LayoutInflater inflater = LayoutInflater.from(getContext());
		    final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
		    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		    builder.setView(dialogView);
		    final AlertDialog alert = builder.create();
		
		    // Configure dialog window
		    if (alert.getWindow() != null) {
			        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
			        alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
			        alert.getWindow().setGravity(Gravity.CENTER);
			    }
		
		    // Initialize dialog views
		    ImageView icon = dialogView.findViewById(R.id.img_icon);
		    TextView tituloDlg = dialogView.findViewById(R.id.tv_title);
		    TextView mensagemDlg = dialogView.findViewById(R.id.tv_message);
		    final RadioButton rbMatch = dialogView.findViewById(R.id.rb_matchparent);
		    final RadioButton rbWrap = dialogView.findViewById(R.id.rb_wrapcontent);
		    final RadioButton rbText = dialogView.findViewById(R.id.rb_directinput);
		    final EditText widgetTextId = dialogView.findViewById(R.id.et_widget);
		    final EditText etTamanho = dialogView.findViewById(R.id.ed_input);
		    Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
		    Button btnSave = dialogView.findViewById(R.id.btn_ok);
		
		    // Configure input fields
		    widgetTextId.setFocusable(true);
		    widgetTextId.setFocusableInTouchMode(true);
		    widgetTextId.setClickable(true);
		    etTamanho.setFocusable(true);
		    etTamanho.setFocusableInTouchMode(true);
		    etTamanho.setClickable(true);
		
		    // Set dialog content
		    icon.setImageResource(R.drawable.width_96);
		    mensagemDlg.setVisibility(View.GONE);
		    tituloDlg.setText(titulo);
		    widgetTextId.setVisibility(View.GONE);
		
		    // Initialize current width
		    etTamanho.setEnabled(false);
		    ViewGroup.LayoutParams params = selectedWidget.getLayoutParams();
		    int currentWidth = params != null ? params.width : LinearLayout.LayoutParams.WRAP_CONTENT;
		
		    if (currentWidth == LinearLayout.LayoutParams.MATCH_PARENT) {
			        rbMatch.setChecked(true);
			    } else if (currentWidth == LinearLayout.LayoutParams.WRAP_CONTENT) {
			        rbWrap.setChecked(true);
			    } else if (currentWidth > 0) {
			        rbText.setChecked(true);
			        etTamanho.setEnabled(true);
			        float dpWidth = currentWidth / getResources().getDisplayMetrics().density;
			        etTamanho.setText(String.format(Locale.getDefault(), "%.0f", dpWidth));
			    }
		
		    // Radio button listeners
		    rbMatch.setOnClickListener(v -> etTamanho.setEnabled(false));
		    rbWrap.setOnClickListener(v -> etTamanho.setEnabled(false));
		    rbText.setOnClickListener(v -> etTamanho.setEnabled(true));
		
		    // Show dialog
		    alert.show();
		
		    // Cancel button
		    btnCancel.setOnClickListener(v -> alert.dismiss());
		
		    // Save button
		    btnSave.setOnClickListener(v -> {
			        if (rbMatch.isChecked()) {
				            params.width = LinearLayout.LayoutParams.MATCH_PARENT;
				        } else if (rbWrap.isChecked()) {
				            params.width = LinearLayout.LayoutParams.WRAP_CONTENT;
				        } else if (rbText.isChecked()) {
				            String inputText = etTamanho.getText().toString().trim();
				            if (inputText.isEmpty()) {
					                // Accept empty input and set width to 0 (or another default if desired)
					                params.width = 0; // You can change this to WRAP_CONTENT or another default
					            } else {
					                try {
						                    int value = Integer.parseInt(inputText);
						                    if (value < 0 || value > 999) {
							                        etTamanho.setError("Enter a value between 0 and 999");
							                        return;
							                    }
						                    params.width = (int) TypedValue.applyDimension(
						                            TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
						                } catch (NumberFormatException e) {
						                    etTamanho.setError("Invalid number");
						                    return;
						                }
					            }
				        }
			
			        // Apply layout params
			        selectedWidget.setLayoutParams(params);
			        selectedWidget.requestLayout();
			        if (selectedWidget.getParent() != null) {
				            ((View) selectedWidget.getParent()).requestLayout();
				            ((View) selectedWidget.getParent()).invalidate();
				        }
			
			        // Update ViewBean
			      /*  ProjectActivityBean.ViewBean viewBean = DesignActivity.createViewBean(selectedWidget);
        if (viewBean != null) {
            viewBean.setWidth(params.width);
            updateWidgetInLayout(viewBean);
        }

        // Save changes
        b();
*/
			        // Clear redo stack
			        redoStack.clear();
			        //showToast("Width updated");
			
			        alert.dismiss();
			    });
	}
	/**
TUDO : widgetHeight SETTINGS
**/
	public void widgetHeight(String titulo, String texto, final View a) {
		    if (selectedWidget == null) {
			        showToast("No widget selected");
			        return;
			    }
		
		    // Save current state for undo
		    saveStateToUndo();
		
		    // Inflate dialog
		    LayoutInflater inflater = LayoutInflater.from(getContext());
		    final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
		    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		    builder.setView(dialogView);
		    final AlertDialog alert = builder.create();
		
		    // Configure dialog window
		    if (alert.getWindow() != null) {
			        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
			        alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
			        alert.getWindow().setGravity(Gravity.CENTER);
			    }
		
		    // Initialize dialog views
		    ImageView icon = dialogView.findViewById(R.id.img_icon);
		    TextView tituloDlg = dialogView.findViewById(R.id.tv_title);
		    TextView mensagemDlg = dialogView.findViewById(R.id.tv_message);
		    final RadioButton rbMatch = dialogView.findViewById(R.id.rb_matchparent);
		    final RadioButton rbWrap = dialogView.findViewById(R.id.rb_wrapcontent);
		    final RadioButton rbText = dialogView.findViewById(R.id.rb_directinput);
		    final EditText widgetTextId = dialogView.findViewById(R.id.et_widget);
		    final EditText etTamanho = dialogView.findViewById(R.id.ed_input);
		    Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
		    Button btnSave = dialogView.findViewById(R.id.btn_ok);
		
		    // Configure input fields
		    widgetTextId.setFocusable(true);
		    widgetTextId.setFocusableInTouchMode(true);
		    widgetTextId.setClickable(true);
		    etTamanho.setFocusable(true);
		    etTamanho.setFocusableInTouchMode(true);
		    etTamanho.setClickable(true);
		
		    // Set dialog content
		    icon.setImageResource(R.drawable.height_96);
		    mensagemDlg.setVisibility(View.GONE);
		    tituloDlg.setText(titulo);
		    widgetTextId.setVisibility(View.GONE);
		
		    // Initialize current height
		    etTamanho.setEnabled(false);
		    ViewGroup.LayoutParams params = selectedWidget.getLayoutParams();
		    int currentHeight = params != null ? params.height : LinearLayout.LayoutParams.WRAP_CONTENT;
		
		    if (currentHeight == LinearLayout.LayoutParams.MATCH_PARENT) {
			        rbMatch.setChecked(true);
			    } else if (currentHeight == LinearLayout.LayoutParams.WRAP_CONTENT) {
			        rbWrap.setChecked(true);
			    } else if (currentHeight > 0) {
			        rbText.setChecked(true);
			        etTamanho.setEnabled(true);
			        float dpHeight = currentHeight / getResources().getDisplayMetrics().density;
			        etTamanho.setText(String.format(Locale.getDefault(), "%.0f", dpHeight));
			    }
		
		    // Radio button listeners
		    rbMatch.setOnClickListener(v -> etTamanho.setEnabled(false));
		    rbWrap.setOnClickListener(v -> etTamanho.setEnabled(false));
		    rbText.setOnClickListener(v -> etTamanho.setEnabled(true));
		
		    // Show dialog
		    alert.show();
		
		    // Cancel button
		    btnCancel.setOnClickListener(v -> alert.dismiss());
		
		    // Save button
		    btnSave.setOnClickListener(v -> {
			        if (rbMatch.isChecked()) {
				            params.height = LinearLayout.LayoutParams.MATCH_PARENT;
				        } else if (rbWrap.isChecked()) {
				            params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
				        } else if (rbText.isChecked()) {
				            String inputText = etTamanho.getText().toString().trim();
				            if (inputText.isEmpty()) {
					                // Accept empty input and set height to 0 (or another default if desired)
					                params.height = 0; // You can change this to WRAP_CONTENT or another default
					            } else {
					                try {
						                    int value = Integer.parseInt(inputText);
						                    if (value < 0 || value > 999) {
							                        etTamanho.setError("Enter a value between 0 and 999");
							                        return;
							                    }
						                    params.height = (int) TypedValue.applyDimension(
						                            TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
						                } catch (NumberFormatException e) {
						                    etTamanho.setError("Invalid number");
						                    return;
						                }
					            }
				        }
			
			        // Apply layout params
			        selectedWidget.setLayoutParams(params);
			        selectedWidget.requestLayout();
			        if (selectedWidget.getParent() != null) {
				            ((View) selectedWidget.getParent()).requestLayout();
				            ((View) selectedWidget.getParent()).invalidate();
				        }
			/*
        // Update ViewBean
        ProjectActivityBean.ViewBean viewBean = DesignActivity.createViewBean(selectedWidget);
        if (viewBean != null) {
            viewBean.setHeight(params.height);
            updateWidgetInLayout(viewBean);
        }

        // Save changes
        c();
*/
			        // Clear redo stack
			        redoStack.clear();
			        showToast("Height updated");
			
			        alert.dismiss();
			    });
	}
	/**
TUDO : saveView FOR SAVE LAYOUT
**/
	public boolean saveView() {
			//public boolean _save_view() {
		    try {
			        // Use a HashMap to group widgets by activity_name
			        HashMap<String, ArrayList<HashMap<String, Object>>> activityLayouts = new HashMap<>();
			        
			        // Load existing layouts if available
			        String savePath = FileUtil.getExternalStorageDir().concat("/.blacklogics/data/".concat(sc_id.concat("/layoutdata")));
			        if (FileUtil.isExistFile(savePath)) {
				            String jsonData = TheBlockLogicsUtil.decodeBase64(FileUtil.readFile(savePath));
				            try {
					                activityLayouts = new Gson().fromJson(jsonData, new TypeToken<HashMap<String, ArrayList<HashMap<String, Object>>>>(){}.getType());
					            } catch (Exception e) {
					                // Handle old format for backward compatibility
					                ArrayList<HashMap<String, Object>> oldLayout = new Gson().fromJson(jsonData, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
					                if (oldLayout != null) {
						                    for (HashMap<String, Object> widget : oldLayout) {
							                        String actName = widget.get("activity_name") != null ? widget.get("activity_name").toString() : "MainActivity";
							                        ArrayList<HashMap<String, Object>> actWidgets = activityLayouts.getOrDefault(actName, new ArrayList<>());
							                        actWidgets.add(widget);
							                        activityLayouts.put(actName, actWidgets);
							                    }
						                }
					            }
				            if (activityLayouts == null) {
					                activityLayouts = new HashMap<>();
					            }
				        }
			
			        // Create a list for the current activity's widgets
			        ArrayList<HashMap<String, Object>> currentLayout = new ArrayList<>();
			        HashSet<String> widgetIds = new HashSet<>(); // Track IDs to detect duplicates
			
			        // Iterate through all widgets in the current layout
			        for (int i = 0; i < ViewEditorFragmentActivity.ll.getChildCount(); i++) {
				            View childAt = ((ViewGroup) ViewEditorFragmentActivity.ll.getChildAt(i)).getChildAt(0);
				            String widgetId = WidgetUtil.getWidgetId(childAt);
				
				            // Check for duplicate IDs
				            if (!widgetIds.add(widgetId)) {
					               // TheBlockLogicsUtil.showToast(getContext().getApplicationContext(), "Error: Duplicate widget ID '" + widgetId + "' found in " + activityName);
					                return false;
					            }
				
				            HashMap<String, Object> layout_map = new HashMap<>();
				            ViewGroup.LayoutParams params = childAt.getLayoutParams();
				
				            layout_map.put("name_s", childAt.getClass().getSimpleName());
				            layout_map.put("id", widgetId); // Use original ID
				            layout_map.put("width", params.width);
				            layout_map.put("height", params.height);
				            layout_map.put("visibility", childAt.getVisibility());
				            layout_map.put("alpha", childAt.getAlpha());
				            layout_map.put("rotation", childAt.getRotation());
				            layout_map.put("scaleX", childAt.getScaleX());
				            layout_map.put("scaleY", childAt.getScaleY());
				            layout_map.put("translationX", childAt.getTranslationX());
				            layout_map.put("translationY", childAt.getTranslationY());
				
				            if (childAt.getBackground() instanceof ColorDrawable) {
					                int color = ((ColorDrawable) childAt.getBackground()).getColor();
					                layout_map.put("background_color", (int)(color));
					            }
				
				            if (params instanceof ViewGroup.MarginLayoutParams) {
					                ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) params;
					                layout_map.put("margin_left", marginParams.leftMargin);
					                layout_map.put("margin_right", marginParams.rightMargin);
					                layout_map.put("margin_top", marginParams.topMargin);
					                layout_map.put("margin_bottom", marginParams.bottomMargin);
					            }
				
				            layout_map.put("padding_right", childAt.getPaddingRight());
				            layout_map.put("padding_left", childAt.getPaddingLeft());
				            layout_map.put("padding_top", childAt.getPaddingTop());
				            layout_map.put("padding_bottom", childAt.getPaddingBottom());
				
				            if (WidgetUtil.getTextViewOfWidget(childAt) != null) {
					                TextView textView = WidgetUtil.getTextViewOfWidget(childAt);
					                layout_map.put("text", textView.getText().toString());
					                layout_map.put("text_size", textView.getTextSize());
					                layout_map.put("text_color", textView.getCurrentTextColor());
					                layout_map.put("gravity", textView.getGravity());
					                if (textView.getTypeface() != null) {
						                    layout_map.put("typeface", textView.getTypeface().toString());
						                }
					            }
				
				            if (childAt instanceof WidgetImageView) {
					                WidgetImageView imageView = (WidgetImageView) childAt;
					                if (imageView.getDrawable() != null) {
						                    String imagePath = WidgetUtil.getImagePath(childAt);
						                    if (imagePath != null) {
							                        layout_map.put("image_path", imagePath);
							                    }
						                }
					                layout_map.put("scale_type", imageView.getScaleType().toString());
					            }
				
				            if (childAt instanceof WidgetWebView) {
					                layout_map.put("is_webview", true);
					            }
				
				            if (childAt instanceof WidgetEditText) {
					                WidgetEditText editText = (WidgetEditText) childAt;
					                layout_map.put("text", editText.getText().toString());
					                layout_map.put("text_size", editText.getTextSize());
					                layout_map.put("text_color", editText.getCurrentTextColor());
					                layout_map.put("gravity", editText.getEditText().getGravity());
					                layout_map.put("hint", editText.getHint() != null ? editText.getHint().toString() : "");
					                layout_map.put("input_type", editText.getInputType());
					                if (editText.getTypeface() != null) {
						                    layout_map.put("typeface", editText.getTypeface().toString());
						                }
					            }
				
				            currentLayout.add(layout_map);
				        }
			
			        // Add project settings to the first widget map (optional, for compatibility)
			        if (!currentLayout.isEmpty()) {
				            currentLayout.get(0).put("activity_name", DesignActivity.currentActivityBean.getActivityName());
				            currentLayout.get(0).put("layout_name", DesignActivity.currentActivityBean.getLayoutName());
				            currentLayout.get(0).put("pkgName", pkgName);
				            currentLayout.get(0).put("useAndroidX", useAndroidX);
				        }
			
			        // Update the activityLayouts map
			        activityLayouts.put(activityName, currentLayout);
			
			        // Save to file
			        DesignActivity.complex.setXmlCode(DesignActivity.currentActivityBean.getLayoutName(), getXmlCode());
			        FileUtil.writeFile(savePath, TheBlockLogicsUtil.encodeToBase64(new Gson().toJson(activityLayouts)));
			        TheBlockLogicsUtil.showToast(getContext().getApplicationContext(), "Project Saved!");
			        return true;
			
			    } catch (Exception e) {
			        TheBlockLogicsUtil.showToast(getContext().getApplicationContext(), "Save Error: " + e.toString());
			        return false;
			    }
		//}
	}
	
	// Helper method to create widget JSON
	private JSONObject createWidgetJson(View childAt) throws JSONException {
			ViewGroup.LayoutParams params = childAt.getLayoutParams();
			JSONObject widgetObject = new JSONObject();
			
			widgetObject.put("name_s", childAt.getClass().getSimpleName());
			widgetObject.put("id", WidgetUtil.getWidgetId(childAt));
			widgetObject.put("width", params.width);
			widgetObject.put("height", params.height);
			
			// Basic view properties
			widgetObject.put("visibility", childAt.getVisibility());
			widgetObject.put("alpha", childAt.getAlpha());
			widgetObject.put("rotation", childAt.getRotation());
			widgetObject.put("scaleX", childAt.getScaleX());
			widgetObject.put("scaleY", childAt.getScaleY());
			widgetObject.put("translationX", childAt.getTranslationX());
			widgetObject.put("translationY", childAt.getTranslationY());
			
			// Background color
			if (childAt.getBackground() instanceof ColorDrawable) {
					int color = ((ColorDrawable) childAt.getBackground()).getColor();
					widgetObject.put("background_color", color);
			}
			
			// Margins if available
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
			
			// Text related widgets
			if (WidgetUtil.getTextViewOfWidget(childAt) != null) {
					TextView textView = WidgetUtil.getTextViewOfWidget(childAt);
					widgetObject.put("text", textView.getText().toString());
					widgetObject.put("text_size", textView.getTextSize());
					widgetObject.put("text_color", textView.getCurrentTextColor());
					widgetObject.put("gravity", textView.getGravity());
					
					if (textView.getTypeface() != null) {
							widgetObject.put("typeface", textView.getTypeface().toString());
					}
			}
			
			// ImageView specific properties
			if (childAt instanceof WidgetImageView) {
					WidgetImageView imageView = (WidgetImageView) childAt;
					if (imageView.getDrawable() != null) {
							String imagePath = WidgetUtil.getImagePath(childAt);
							if (imagePath != null) {
									widgetObject.put("image_path", imagePath);
							}
					}
					widgetObject.put("scale_type", imageView.getScaleType().toString());
			}
			
			// WebView specific properties
			if (childAt instanceof WidgetWebView) {
					widgetObject.put("is_webview", true);
			}
			
			Map<String, String> customAttributes = (Map<String, String>) childAt.getTag(R.id.custom_attributes_tag);
			if (customAttributes != null && !customAttributes.isEmpty()) {
					JSONObject customAttrsJson = new JSONObject(customAttributes);
					widgetObject.put("custom_attributes", customAttrsJson);
			}
			
			return widgetObject;
	}
	
	/**
TUDO : TOGGLE HIDE PROPERTIES, SHOW PROPERTY'S
**/
	public /*static */void hideProperties() {
		    LinearLayout base = getPropertiesPanel();
			anim.setTarget(base);
			anim.setProperty(View.TRANSLATION_Y);
			anim.setFloatValues(new float[]{(float) base.getHeight()});
			anim.setInterpolator(new DecelerateInterpolator());
			anim.start();
	}
	
	public /*static*/ void showProperties() {
		    LinearLayout base = getPropertiesPanel();
			base.setVisibility(View.VISIBLE);
			anim.setTarget(base);
			anim.setProperty(View.TRANSLATION_Y);
			anim.setFloatValues(new float[]{(float) 0});
			anim.setInterpolator(new DecelerateInterpolator());
			anim.start();
	}
	
	/**
TUDO : isHiddenProperties
**/
	public /*static*/ boolean isHiddenProperties() {
		    LinearLayout panel = getPropertiesPanel();
		    return panel.getTranslationY() == ((float) panel.getHeight());
	}
	/**
TUDO : unselectSelectedWidget
**/
	public /*static */void unselectSelectedWidget()
	{
			if (selectedWidget != null)
			{
					if (selectedWidgetProperties != null) {
							selectedWidget.setBackgroundColor(selectedWidgetProperties.getBackgroundColor());
					} else {
							selectedWidget.setBackgroundColor(0);
					}
					selectedWidget = (View) null;
					selectedWidgetProperties = null;
					hideProperties();
			}
	}
	
	/**
TUDO : selectWidget
**/
	public void selectWidget(View view) {
		    if (view == null) return;
		
		    // Unselect the previously selected widget
		    if (selectedWidget != null) {
			        unselectSelectedWidget();
			    }
		
		    // Set the new selected widget and initialize properties
		    selectedWidget = view;
		    selectedWidgetProperties = new WidgetProperties();
		
		    // Update widget properties for Widget subclasses
		    String widgetId = WidgetUtil.getWidgetId(view); // Assume WidgetUtil handles ID extraction
		    selectedWidgetProperties.setWidgetId(widgetId);
		
		    // Set background color for selection highlight
		    if (view.getBackground() instanceof ColorDrawable) {
			        selectedWidgetProperties.setBackgroundColor(((ColorDrawable) view.getBackground()).getColor());
			    } else {
			        selectedWidgetProperties.setBackgroundColor(Color.TRANSPARENT);
			    }
		    view.setBackgroundColor(Color.parseColor("#77BBCCDD")); // Highlight selected widget
		
		    // Update widget ID in properties panel
		    if (DesignActivity.ll_properties != null) {
			        View widgetIdView = DesignActivity.ll_properties.findViewById(R.id.tv_widget_id);
			        if (widgetIdView instanceof TextView) {
				            ((TextView) widgetIdView).setText(widgetId);
				        }
			    }
		
		    // Update properties panel in DesignActivity
		    if (requireActivity() instanceof DesignActivity) {
			        DesignActivity activity = (DesignActivity) requireActivity();
			        LinearLayout panel = activity.getPropertiesPanel();
			        if (panel != null) {
				            View panelWidgetIdView = panel.findViewById(R.id.tv_widget_id);
				            if (panelWidgetIdView instanceof TextView) {
					                ((TextView) panelWidgetIdView).setText(widgetId);
					            }
				        }
			    }
		
		    // Reset all property fields to GONE
		    if (DesignActivity.widget_text != null) DesignActivity.widget_text.setVisibility(View.GONE);
		    if (DesignActivity.colorText != null) DesignActivity.colorText.setVisibility(View.GONE);
		    if (DesignActivity.textSize != null) DesignActivity.textSize.setVisibility(View.GONE);
		    if (DesignActivity.textStyle != null) DesignActivity.textStyle.setVisibility(View.GONE);
		    if (DesignActivity.Lines != null) DesignActivity.Lines.setVisibility(View.GONE);
		    if (DesignActivity.widget_src != null) DesignActivity.widget_src.setVisibility(View.GONE);
		    if (DesignActivity.checkState != null) DesignActivity.checkState.setVisibility(View.GONE);
		    if (DesignActivity.switchCheckState != null) DesignActivity.switchCheckState.setVisibility(View.GONE);
		    if (DesignActivity.progressStyle != null) DesignActivity.progressStyle.setVisibility(View.GONE);
		    if (DesignActivity.widget_scale != null) DesignActivity.widget_scale.setVisibility(View.GONE);
		    if (DesignActivity.max_progress != null) DesignActivity.max_progress.setVisibility(View.GONE);
		
		    // Set visibility based on widget type
		    if (view instanceof WidgetButton || view instanceof WidgetTextView) {
			        if (DesignActivity.widget_text != null) DesignActivity.widget_text.setVisibility(View.VISIBLE);
			        if (DesignActivity.colorText != null) DesignActivity.colorText.setVisibility(View.VISIBLE);
			        if (DesignActivity.textSize != null) DesignActivity.textSize.setVisibility(View.VISIBLE);
			        if (DesignActivity.textStyle != null) DesignActivity.textStyle.setVisibility(View.VISIBLE);
			        if (DesignActivity.Lines != null) DesignActivity.Lines.setVisibility(View.VISIBLE);
			    } else if (view instanceof WidgetImageView) {
			        if (DesignActivity.widget_src != null) DesignActivity.widget_src.setVisibility(View.VISIBLE);
			        if (DesignActivity.widget_scale != null) DesignActivity.widget_scale.setVisibility(View.VISIBLE);
			    } else if (view instanceof WidgetCheckBox) {
			        if (DesignActivity.widget_text != null) DesignActivity.widget_text.setVisibility(View.VISIBLE);
			        if (DesignActivity.checkState != null) DesignActivity.checkState.setVisibility(View.VISIBLE);
			    } else if (view instanceof WidgetSwitch) {
			        if (DesignActivity.widget_text != null) DesignActivity.widget_text.setVisibility(View.VISIBLE);
			        if (DesignActivity.switchCheckState != null) DesignActivity.switchCheckState.setVisibility(View.VISIBLE);
			    } else if (view instanceof WidgetProgressBar) {
			        if (DesignActivity.progressStyle != null) DesignActivity.progressStyle.setVisibility(View.VISIBLE);
			        if (DesignActivity.max_progress != null) DesignActivity.max_progress.setVisibility(View.VISIBLE);
			    }
		    // No properties shown for WidgetWebView, WidgetDigitalClock, WidgetViewPager, WidgetListView, WidgetVideoView
		
		    // Show the properties panel
		    showProperties();
	}
	/**
TUDO : exitMessageEdit
**/
	public void exibirMensagemEdt(String titulo, String texto) {
				LayoutInflater inflater = LayoutInflater.from(getContext());  
		        final View v = inflater.inflate(R.layout.custom_dialog, null);  
		        AlertDialog.Builder builer = new AlertDialog.Builder(getContext()); 
		        builer.setView(v); 
		
				ImageView icon = (ImageView) v.findViewById(R.id.img_icon);
				TextView titulo_dlg = (TextView) v.findViewById(R.id.tv_title);
				TextView mensagem_dlg = (TextView) v.findViewById(R.id.tv_message);
				LinearLayout tamanho = (LinearLayout) v.findViewById(R.id.widget_temanho);
				final EditText widget_text_id = (EditText) v.findViewById(R.id.et_widget);
		        Button btnCancel = (Button) v.findViewById(R.id.btn_cancel); 
				Button btnSave = (Button) v.findViewById(R.id.btn_ok);
		
				icon.setImageResource(R.drawable.exit_96);
				mensagem_dlg.setText(texto);
				titulo_dlg.setText(titulo);
				tamanho.setVisibility(View.GONE);
				widget_text_id.setVisibility(View.GONE);
		        final AlertDialog alert = builer.create(); 
		        alert.getWindow().setGravity(Gravity.CENTER); 
		        alert.show(); 
		
				btnCancel.setText("EXIT");
				btnSave.setText("SAVE & EXIT");
		
		        btnCancel.setOnClickListener(new View.OnClickListener() { 
							@Override 
							public void onClick(View v) {  
									alert.cancel();
									requireActivity().finish();
							} 
					}); 
				btnSave.setOnClickListener(new View.OnClickListener() { 
							@Override
							public void onClick(View v) {  
									alert.cancel();
									if (saveView()) {
											TheBlockLogicsUtil.showToast(getContext().getApplicationContext(),"Projeto Saved!");
											requireActivity().finish();
									}
							    }
					});
		}
	/**
TUDO : idWidget
**/
	public void idWidget(String titulo, String texto, final View base) {
		    if (selectedWidget == null) {
			        showToast("No widget selected");
			        return;
			    }
		
		    // Save current state for undo
		    saveStateToUndo();
		
		    // Inflate dialog
		    LayoutInflater inflater = LayoutInflater.from(getContext());
		    final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
		    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		    builder.setView(dialogView);
		    final AlertDialog alert = builder.create();
		
		    // Configure dialog window
		    if (alert.getWindow() != null) {
			        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
			        alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
			        alert.getWindow().setGravity(Gravity.CENTER);
			    }
		
		    // Initialize dialog views
		    ImageView icon = dialogView.findViewById(R.id.img_icon);
		    TextView tituloDlg = dialogView.findViewById(R.id.tv_title);
		    TextView mensagemDlg = dialogView.findViewById(R.id.tv_message);
		    LinearLayout tamanho = dialogView.findViewById(R.id.widget_temanho);
		    final EditText widgetTextId = dialogView.findViewById(R.id.et_widget);
		    final EditText etTamanho = dialogView.findViewById(R.id.ed_input);
		    Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
		    Button btnSave = dialogView.findViewById(R.id.btn_ok);
		
		    // Configure input fields
		    widgetTextId.setFocusable(true);
		    widgetTextId.setFocusableInTouchMode(true);
		    widgetTextId.setClickable(true);
		    etTamanho.setFocusable(true);
		    etTamanho.setFocusableInTouchMode(true);
		    etTamanho.setClickable(true);
		
		    // Set dialog content
		    icon.setImageResource(R.drawable.abc_96);
		    tituloDlg.setText(titulo);
		    mensagemDlg.setVisibility(View.GONE);
		    tamanho.setVisibility(View.GONE);
		
		    // Set current widget ID
		    String currentId = WidgetUtil.getWidgetId(selectedWidget);
		    widgetTextId.setText(currentId != null ? currentId : "");
		
		    // Show dialog
		    alert.show();
		
		    // Cancel button
		    btnCancel.setOnClickListener(v -> alert.dismiss());
		
		    // Save button
		    btnSave.setOnClickListener(v -> {
			        String newId = widgetTextId.getText().toString().trim();
			        if (newId.isEmpty()) {
				            widgetTextId.setError("ID cannot be empty");
				            return;
				        }
			        if (!isValidId(newId)) {
				            widgetTextId.setError("ID must start with a letter and contain only letters, numbers, or underscores");
				            return;
				        }
			
			        // Check for duplicate IDs
			        if (isDuplicateId(newId, selectedWidget)) {
				            widgetTextId.setError("ID already exists");
				            return;
				        }
			
			        // Apply new ID
			        WidgetUtil.setWidgetId(selectedWidget, newId);
			        selectedWidget.requestLayout();
			
			        // Update ViewBean
			     /*   ProjectActivityBean.ViewBean viewBean = DesignActivity.createViewBean(selectedWidget);
        if (viewBean != null) {
            viewBean.setWidgetId(newId);
            updateWidgetInLayout(viewBean);
        }

        // Save changes
        d();
*/
			        // Clear redo stack
			        redoStack.clear();
			        showToast("Widget ID updated");
			
			        alert.dismiss();
			    });
	}
	/**
TUDO : EXTRA MATHODS FOR MORE BEST DEVELOPING THIS ALL MATHODS ME ADDED
**/
	public void generateJavaCode() {
				StringBuilder javaCode = new StringBuilder();
				javaCode.append("package").append(" ").append(pkgName).append(";").append("\n");
				//javaCode.append("package com.example.generated;\n\n");
		
				// Use AndroidX or old support libraries based on the boolean variable
				if (useAndroidX) {
						javaCode.append("import androidx.appcompat.app.AppCompatActivity;\n");
				} else {
						javaCode.append("import android.app.Activity;\n");
				}
		
				javaCode.append("import android.os.Bundle;\n");
				javaCode.append("import android.widget.*;\n\n");
		
				// Use dynamic activity name
				javaCode.append("public class ").append(this.activityName).append(" extends ");
				javaCode.append(useAndroidX ? "AppCompatActivity" : "Activity").append(" {\n\n");
		
				for (int i = 0; i < ll.getChildCount(); i++) {
						View child = ((ViewGroup) ll.getChildAt(i)).getChildAt(0);
						/*String widgetType = child.getClass().getSimpleName();*/
						String widgetType = child.getClass().getSimpleName();
			
						// Change widget names to standard Android views
						if (widgetType.equals("WidgetButton")) widgetType = "Button";
						if (widgetType.equals("WidgetTextView")) widgetType = "TextView";
						if (widgetType.equals("WidgetImageView")) widgetType = "ImageView";
			            if (widgetType.equals("WidgetWebView")) widgetType = "WebView";
						
						String widgetId = WidgetUtil.getWidgetId(child);
						if (!widgetId.isEmpty()) {
								javaCode.append("    private ").append(widgetType).append(" ").append(widgetId).append(";\n");
						}
				}
		
				javaCode.append("\n    @Override\n");
				javaCode.append("    protected void onCreate(Bundle savedInstanceState) {\n");
				javaCode.append("        super.onCreate(savedInstanceState);\n");
				/*javaCode.append("        setContentView(R.layout.generated_layout);\n\n");*/
				javaCode.append("        setContentView(R.layout.").append(layoutName).append("").append(")").append(";\n");
		
				for (int i = 0; i < ll.getChildCount(); i++) {
						View child = ((ViewGroup) ll.getChildAt(i)).getChildAt(0);
						String widgetId = WidgetUtil.getWidgetId(child);
						if (!widgetId.isEmpty()) {
								javaCode.append("        ").append(widgetId).append(" = findViewById(R.id.").append(widgetId).append(");\n");
						}
				}
		
				javaCode.append("    }\n");
				javaCode.append("}\n");
		
				//TheBlockLogicsUtil.writeFile(projectPath + "/" + this.activityName + ".java", javaCode.toString());
		        
		        /**
        TUDO : I WILL GIVE COMPLEX CLASS IN NEXT UPDATE THIS COMPLEX CLASS LIKE SKETCHWARE DATA SAVING
        */
		        
				//complex.setJavaCode(activityName, javaCode.toString());
				//TheBlockLogicsUtil.showToast(getContext().getApplicationContext(), "Java Code Generated!");
		}
	/*
TUDO : generateXmlLayout
**/
	public void generateXmlLayout() {
		    StringBuilder xmlCode = new StringBuilder();
		    xmlCode.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		    xmlCode.append("<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n");
		    xmlCode.append("    xmlns:app=\"http://schemas.android.com/apk/res-auto\"\n");
		    xmlCode.append("    android:layout_width=\"match_parent\"\n");
		    xmlCode.append("    android:layout_height=\"match_parent\"\n");
		    xmlCode.append("    android:orientation=\"vertical\">\n\n");
		
		    for (int i = 0; i < ll.getChildCount(); i++) {
			        View child = ((ViewGroup) ll.getChildAt(i)).getChildAt(0);
			        String widgetType = child.getClass().getSimpleName();
			        String widgetId = WidgetUtil.getWidgetId(child);
			        ViewGroup.LayoutParams params = child.getLayoutParams();
			        
			        // Convert widget type to standard Android class names
			        widgetType = widgetType.equals("WidgetButton") ? "Button" : 
			                   widgetType.equals("WidgetTextView") ? "TextView" : 
			                   widgetType.equals("WidgetImageView") ? "ImageView" : 
			                   widgetType.equals("WidgetWebView") ? "WebView" : 
			                   widgetType.equals("WidgetLinear") ? "LinearLayout" : 
			                   widgetType;
			
			        xmlCode.append("    <").append(widgetType).append("\n");
			        xmlCode.append("        android:id=\"@+id/").append(widgetId).append("\"\n");
			        
			        // Layout dimensions
			        xmlCode.append("        android:layout_width=\"")
			              .append(params.width == -1 ? "match_parent" : params.width == -2 ? "wrap_content" : params.width + "dp").append("\"\n");
			        xmlCode.append("        android:layout_height=\"")
			              .append(params.height == -1 ? "match_parent" : params.height == -2 ? "wrap_content" : params.height + "dp").append("\"");
			
			        // Common View properties
			        if (child.getVisibility() != View.VISIBLE) {
				            xmlCode.append("\n        android:visibility=\"")
				                  .append(child.getVisibility() == View.INVISIBLE ? "invisible" : "gone").append("\"");
				        }
			        
			        if (child.getAlpha() != 1.0f) {
				            xmlCode.append("\n        android:alpha=\"").append(child.getAlpha()).append("\"");
				        }
			        
			        if (child.getRotation() != 0f) {
				            xmlCode.append("\n        android:rotation=\"").append(child.getRotation()).append("\"");
				        }
			        
			        if (child.getScaleX() != 1.0f || child.getScaleY() != 1.0f) {
				            xmlCode.append("\n        android:scaleX=\"").append(child.getScaleX()).append("\"");
				            xmlCode.append("\n        android:scaleY=\"").append(child.getScaleY()).append("\"");
				        }
			        
			        if (child.getTranslationX() != 0f || child.getTranslationY() != 0f) {
				            xmlCode.append("\n        android:translationX=\"").append(child.getTranslationX()).append("dp\"");
				            xmlCode.append("\n        android:translationY=\"").append(child.getTranslationY()).append("dp\"");
				        }
			        
			        // Background handling with default white
			        if (child.getBackground() != null && child.getBackground() instanceof ColorDrawable) {
				            int color = ((ColorDrawable) child.getBackground()).getColor();
				            if (color == 0 || color == Color.TRANSPARENT) {
					                xmlCode.append("\n        android:background=\"#FFFFFF\""); // Default white
					            } else {
					                xmlCode.append("\n        android:background=\"#").append(String.format("%08X", color)).append("\"");
					            }
				        } else {
				            xmlCode.append("\n        android:background=\"#FFFFFF\""); // Default white
				        }
			
			        // Text related widgets (TextView and Button)
			        if (child instanceof WidgetTextView || child instanceof WidgetButton) {
				            TextView textView = (TextView) child;
				            
				            if (!TextUtils.isEmpty(textView.getText())) {
					                xmlCode.append("\n        android:text=\"").append(textView.getText().toString()).append("\"");
					            }
				            
				            if (textView.getTextSize() != 0) {
					                xmlCode.append("\n        android:textSize=\"").append(textView.getTextSize() / getResources().getDisplayMetrics().scaledDensity).append("sp\"");
					            }
				            
				            // Text color handling with default black
				            int textColor = textView.getCurrentTextColor();
				            if (textColor == 0 || textColor == Color.TRANSPARENT) {
					                xmlCode.append("\n        android:textColor=\"#000000\""); // Default black
					            } else {
					                xmlCode.append("\n        android:textColor=\"#").append(String.format("%08X", textColor)).append("\"");
					            }
				            
				            if (textView.getTypeface() != null) {
					                xmlCode.append("\n        android:fontFamily=\"").append(textView.getTypeface().toString()).append("\"");
					            }
				            
				            if (textView.getGravity() != Gravity.START) {
					                xmlCode.append("\n        android:gravity=\"")
					                      .append(gravityToString(textView.getGravity())).append("\"");
					            }
				            
				            if (textView.getLineSpacingMultiplier() != 1.0f) {
					                xmlCode.append("\n        android:lineSpacingMultiplier=\"").append(textView.getLineSpacingMultiplier()).append("\"");
					            }
				        }
			
			        // ImageView specific properties
			        if (child instanceof WidgetImageView) {
				            WidgetImageView imageView = (WidgetImageView) child;
				            
				            String imagePath = WidgetUtil.getImagePath(child);
				            if (imagePath != null && !imagePath.isEmpty()) {
					                xmlCode.append("\n        android:src=\"@drawable/").append(new File(imagePath).getName().replace(".png", "").replace(".jpg", "")).append("\"");
					            } else {
					                xmlCode.append("\n        android:src=\"@drawable/default_image\""); // Default image
					            }
				            
				            if (imageView.getScaleType() != ImageView.ScaleType.FIT_CENTER) {
					                xmlCode.append("\n        android:scaleType=\"")
					                      .append(imageView.getScaleType().toString().toLowerCase()).append("\"");
					            }
				        }
			
			        // WebView specific properties
			        if (child instanceof WidgetWebView) {
				            WidgetWebView webView = (WidgetWebView) child;
				            xmlCode.append("\n        android:layout_weight=\"1\"");
				            xmlCode.append("\n        android:scrollbarStyle=\"outsideOverlay\"");
				            xmlCode.append("\n        android:scrollbars=\"vertical\"");
				        }
			        
			        if (child instanceof WidgetEditText) {
							WidgetEditText editText = (WidgetEditText) child;
							
							if (!TextUtils.isEmpty(editText.getText())) {
									xmlCode.append("\n        android:text=\"").append(editText.getText().toString()).append("\"");
							}
							
							if (editText.getTextSize() != 0) {
									xmlCode.append("\n        android:textSize=\"").append(editText.getTextSize() / getResources().getDisplayMetrics().scaledDensity).append("sp\"");
							}
							
							if (editText.getCurrentTextColor() != 0) {
									xmlCode.append("\n        android:textColor=\"#").append(Integer.toHexString(editText.getCurrentTextColor())).append("\"");
							}
							
							if (editText.getTypeface() != null) {
									xmlCode.append("\n        android:fontFamily=\"").append(editText.getTypeface().toString()).append("\"");
							}
							
							if (editText.getGravity() != Gravity.START) {
									xmlCode.append("\n        android:gravity=\"").append(gravityToString(editText.getGravity())).append("\"");
							}
							
							if (editText.getLineSpacingMultiplier() != 1.0f) {
									xmlCode.append("\n        android:lineSpacingMultiplier=\"").append(editText.getLineSpacingMultiplier()).append("\"");
							}
							
							if (editText.getHint() != null && !TextUtils.isEmpty(editText.getHint())) {
									xmlCode.append("\n        android:hint=\"").append(editText.getHint().toString()).append("\"");
							}
							
							if (editText.getInputType() != android.text.InputType.TYPE_CLASS_TEXT) {
									xmlCode.append("\n        android:inputType=\"").append(inputTypeToString(editText.getInputType())).append("\"");
							}
					}
			        
			        if (child instanceof WidgetCheckBox) {
							WidgetCheckBox checkBox = (WidgetCheckBox) child;
							
							if (!TextUtils.isEmpty(checkBox.getText())) {
									xmlCode.append("\n        android:text=\"").append(checkBox.getText().toString()).append("\"");
							}
							
							if (checkBox.getTextSize() != 0) {
									xmlCode.append("\n        android:textSize=\"").append(checkBox.getTextSize() / getResources().getDisplayMetrics().scaledDensity).append("sp\"");
							}
							
							if (checkBox.getCurrentTextColor() != 0) {
									xmlCode.append("\n        android:textColor=\"#").append(Integer.toHexString(checkBox.getCurrentTextColor())).append("\"");
							}
							
							if (checkBox.getTypeface() != null) {
									xmlCode.append("\n        android:fontFamily=\"").append(checkBox.getTypeface().toString()).append("\"");
							}
							
							if (checkBox.getGravity() != Gravity.START) {
									xmlCode.append("\n        android:gravity=\"").append(gravityToString(checkBox.getGravity())).append("\"");
							}
							
							if (checkBox.isCheckedDisplay()) {
									xmlCode.append("\n        android:checked=\"true\"");
							}
					}
			
			        // Layout margins
			        if (params instanceof ViewGroup.MarginLayoutParams) {
				            ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) params;
				            if (marginParams.leftMargin != 0 || marginParams.topMargin != 0 || 
				                marginParams.rightMargin != 0 || marginParams.bottomMargin != 0) {
					                xmlCode.append("\n        android:layout_margin=\"")
					                      .append(marginParams.leftMargin).append("dp\"");
					                xmlCode.append("\n        android:layout_marginLeft=\"")
					                      .append(marginParams.leftMargin).append("dp\"");
					                xmlCode.append("\n        android:layout_marginTop=\"")
					                      .append(marginParams.topMargin).append("dp\"");
					                xmlCode.append("\n        android:layout_marginRight=\"")
					                      .append(marginParams.rightMargin).append("dp\"");
					                xmlCode.append("\n        android:layout_marginBottom=\"")
					                      .append(marginParams.bottomMargin).append("dp\"");
					                xmlCode.append("\n        android:layout_marginStart=\"")
					                      .append(marginParams.leftMargin).append("dp\"");
					                xmlCode.append("\n        android:layout_marginEnd=\"")
					                      .append(marginParams.rightMargin).append("dp\"");
					            }
				        }
			
			        // Padding
			        if (child.getPaddingLeft() != 0 || child.getPaddingTop() != 0 || 
			            child.getPaddingRight() != 0 || child.getPaddingBottom() != 0) {
				            xmlCode.append("\n        android:padding=\"")
				                  .append(child.getPaddingLeft()).append("dp\"");
				            xmlCode.append("\n        android:paddingLeft=\"")
				                  .append(child.getPaddingLeft()).append("dp\"");
				            xmlCode.append("\n        android:paddingTop=\"")
				                  .append(child.getPaddingTop()).append("dp\"");
				            xmlCode.append("\n        android:paddingRight=\"")
				                  .append(child.getPaddingRight()).append("dp\"");
				            xmlCode.append("\n        android:paddingBottom=\"")
				                  .append(child.getPaddingBottom()).append("dp\"");
				            xmlCode.append("\n        android:paddingStart=\"")
				                  .append(child.getPaddingLeft()).append("dp\"");
				            xmlCode.append("\n        android:paddingEnd=\"")
				                  .append(child.getPaddingRight()).append("dp\"");
				        }
			        
			        // Add custom attributes
			        Map<String, String> customAttributes = (Map<String, String>) child.getTag(R.id.custom_attributes_tag);
			        if (customAttributes != null) {
				            for (Map.Entry<String, String> entry : customAttributes.entrySet()) {
					                xmlCode.append("\n        ").append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"");
					            }
				        }
			
			        xmlCode.append(" />\n\n");
			    }
		
		    xmlCode.append("</LinearLayout>");
		    // Note: The method is void, so no return statement
	}
	/*

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
}*/
	public String getXMLCode() {
		    StringBuilder xmlCode = new StringBuilder();
		    xmlCode.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		    xmlCode.append("<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n");
		    xmlCode.append("    xmlns:app=\"http://schemas.android.com/apk/res-auto\"\n");
		    xmlCode.append("    android:layout_width=\"match_parent\"\n");
		    xmlCode.append("    android:layout_height=\"match_parent\"\n");
		    xmlCode.append("    android:orientation=\"vertical\">\n\n");
		
		    for (int i = 0; i < ll.getChildCount(); i++) {
			        View child = ((ViewGroup) ll.getChildAt(i)).getChildAt(0);
			        String widgetType = child.getClass().getSimpleName();
			        String widgetId = WidgetUtil.getWidgetId(child);
			        ViewGroup.LayoutParams params = child.getLayoutParams();
			        
			        // Convert widget type to standard Android class names
			        widgetType = widgetType.equals("WidgetButton") ? "Button" : 
			                   widgetType.equals("WidgetTextView") ? "TextView" : 
			                   widgetType.equals("WidgetImageView") ? "ImageView" : 
			                   widgetType.equals("WidgetWebView") ? "WebView" : 
			                   widgetType.equals("WidgetLinear") ? "LinearLayout" : 
			                   widgetType;
			
			        xmlCode.append("    <").append(widgetType).append("\n");
			        xmlCode.append("        android:id=\"@+id/").append(widgetId).append("\"\n");
			        
			        // Layout dimensions
			        xmlCode.append("        android:layout_width=\"")
			              .append(params.width == -1 ? "match_parent" : params.width == -2 ? "wrap_content" : params.width + "dp").append("\"\n");
			        xmlCode.append("        android:layout_height=\"")
			              .append(params.height == -1 ? "match_parent" : params.height == -2 ? "wrap_content" : params.height + "dp").append("\"");
			
			        // Common View properties
			        if (child.getVisibility() != View.VISIBLE) {
				            xmlCode.append("\n        android:visibility=\"")
				                  .append(child.getVisibility() == View.INVISIBLE ? "invisible" : "gone").append("\"");
				        }
			        
			        if (child.getAlpha() != 1.0f) {
				            xmlCode.append("\n        android:alpha=\"").append(child.getAlpha()).append("\"");
				        }
			        
			        if (child.getRotation() != 0f) {
				            xmlCode.append("\n        android:rotation=\"").append(child.getRotation()).append("\"");
				        }
			        
			        if (child.getScaleX() != 1.0f || child.getScaleY() != 1.0f) {
				            xmlCode.append("\n        android:scaleX=\"").append(child.getScaleX()).append("\"");
				            xmlCode.append("\n        android:scaleY=\"").append(child.getScaleY()).append("\"");
				        }
			        
			        if (child.getTranslationX() != 0f || child.getTranslationY() != 0f) {
				            xmlCode.append("\n        android:translationX=\"").append(child.getTranslationX()).append("dp\"");
				            xmlCode.append("\n        android:translationY=\"").append(child.getTranslationY()).append("dp\"");
				        }
			        
			        // Background handling with default white
			        if (child.getBackground() != null && child.getBackground() instanceof ColorDrawable) {
				            int color = ((ColorDrawable) child.getBackground()).getColor();
				            if (color == 0 || color == Color.TRANSPARENT) {
					                xmlCode.append("\n        android:background=\"#FFFFFF\""); // Default white
					            } else {
					                xmlCode.append("\n        android:background=\"#").append(String.format("%08X", color)).append("\"");
					            }
				        } else {
				            xmlCode.append("\n        android:background=\"#FFFFFF\""); // Default white
				        }
			
			        // Text related widgets (TextView and Button)
			        if (child instanceof WidgetTextView || child instanceof WidgetButton) {
				            TextView textView = (TextView) child;
				            
				            if (!TextUtils.isEmpty(textView.getText())) {
					                xmlCode.append("\n        android:text=\"").append(textView.getText().toString()).append("\"");
					            }
				            
				            if (textView.getTextSize() != 0) {
					                xmlCode.append("\n        android:textSize=\"").append(textView.getTextSize() / getResources().getDisplayMetrics().scaledDensity).append("sp\"");
					            }
				            
				            // Text color handling with default black
				            int textColor = textView.getCurrentTextColor();
				            if (textColor == 0 || textColor == Color.TRANSPARENT) {
					                xmlCode.append("\n        android:textColor=\"#000000\""); // Default black
					            } else {
					                xmlCode.append("\n        android:textColor=\"#").append(String.format("%08X", textColor)).append("\"");
					            }
				            
				            if (textView.getTypeface() != null) {
					                xmlCode.append("\n        android:fontFamily=\"").append(textView.getTypeface().toString()).append("\"");
					            }
				            
				            if (textView.getGravity() != Gravity.START) {
					                xmlCode.append("\n        android:gravity=\"")
					                      .append(gravityToString(textView.getGravity())).append("\"");
					            }
				            
				            if (textView.getLineSpacingMultiplier() != 1.0f) {
					                xmlCode.append("\n        android:lineSpacingMultiplier=\"").append(textView.getLineSpacingMultiplier()).append("\"");
					            }
				        }
			
			        // ImageView specific properties
			        if (child instanceof WidgetImageView) {
				            WidgetImageView imageView = (WidgetImageView) child;
				            
				            String imagePath = WidgetUtil.getImagePath(child);
				            if (imagePath != null && !imagePath.isEmpty()) {
					                xmlCode.append("\n        android:src=\"@drawable/").append(new File(imagePath).getName().replace(".png", "").replace(".jpg", "")).append("\"");
					            } else {
					                xmlCode.append("\n        android:src=\"@drawable/default_image\""); // Default image
					            }
				            
				            if (imageView.getScaleType() != ImageView.ScaleType.FIT_CENTER) {
					                xmlCode.append("\n        android:scaleType=\"")
					                      .append(imageView.getScaleType().toString().toLowerCase()).append("\"");
					            }
				        }
			
			        // WebView specific properties
			        if (child instanceof WidgetWebView) {
				            WidgetWebView webView = (WidgetWebView) child;
				            xmlCode.append("\n        android:layout_weight=\"1\"");
				            xmlCode.append("\n        android:scrollbarStyle=\"outsideOverlay\"");
				            xmlCode.append("\n        android:scrollbars=\"vertical\"");
				        }
			
			        // Layout margins
			        if (params instanceof ViewGroup.MarginLayoutParams) {
				            ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) params;
				            if (marginParams.leftMargin != 0 || marginParams.topMargin != 0 || 
				                marginParams.rightMargin != 0 || marginParams.bottomMargin != 0) {
					                xmlCode.append("\n        android:layout_margin=\"")
					                      .append(marginParams.leftMargin).append("dp\"");
					                xmlCode.append("\n        android:layout_marginLeft=\"")
					                      .append(marginParams.leftMargin).append("dp\"");
					                xmlCode.append("\n        android:layout_marginTop=\"")
					                      .append(marginParams.topMargin).append("dp\"");
					                xmlCode.append("\n        android:layout_marginRight=\"")
					                      .append(marginParams.rightMargin).append("dp\"");
					                xmlCode.append("\n        android:layout_marginBottom=\"")
					                      .append(marginParams.bottomMargin).append("dp\"");
					                xmlCode.append("\n        android:layout_marginStart=\"")
					                      .append(marginParams.leftMargin).append("dp\"");
					                xmlCode.append("\n        android:layout_marginEnd=\"")
					                      .append(marginParams.rightMargin).append("dp\"");
					            }
				        }
			
			        // Padding
			        if (child.getPaddingLeft() != 0 || child.getPaddingTop() != 0 || 
			            child.getPaddingRight() != 0 || child.getPaddingBottom() != 0) {
				            xmlCode.append("\n        android:padding=\"")
				                  .append(child.getPaddingLeft()).append("dp\"");
				            xmlCode.append("\n        android:paddingLeft=\"")
				                  .append(child.getPaddingLeft()).append("dp\"");
				            xmlCode.append("\n        android:paddingTop=\"")
				                  .append(child.getPaddingTop()).append("dp\"");
				            xmlCode.append("\n        android:paddingRight=\"")
				                  .append(child.getPaddingRight()).append("dp\"");
				            xmlCode.append("\n        android:paddingBottom=\"")
				                  .append(child.getPaddingBottom()).append("dp\"");
				            xmlCode.append("\n        android:paddingStart=\"")
				                  .append(child.getPaddingLeft()).append("dp\"");
				            xmlCode.append("\n        android:paddingEnd=\"")
				                  .append(child.getPaddingRight()).append("dp\"");
				        }
			        
			        // Add custom attributes
			        Map<String, String> customAttributes = (Map<String, String>) child.getTag(R.id.custom_attributes_tag);
			        if (customAttributes != null) {
				            for (Map.Entry<String, String> entry : customAttributes.entrySet()) {
					                xmlCode.append("\n        ").append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"");
					            }
				        }
			
			        xmlCode.append(" />\n\n");
			    }
		
		    xmlCode.append("</LinearLayout>");
		    return xmlCode.toString();
	}
	public String getXmlCode() {
		    StringBuilder xmlCode = new StringBuilder();
		    xmlCode.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		    xmlCode.append("<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n");
		    xmlCode.append("    xmlns:app=\"http://schemas.android.com/apk/res-auto\"\n");
		    xmlCode.append("    android:layout_width=\"match_parent\"\n");
		    xmlCode.append("    android:layout_height=\"match_parent\"\n");
		    xmlCode.append("    android:orientation=\"vertical\">\n\n");
		
		    for (int i = 0; i < ViewEditorFragmentActivity.ll.getChildCount(); i++) {
			        View child = ((ViewGroup) ViewEditorFragmentActivity.ll.getChildAt(i)).getChildAt(0);
			        String widgetType = child.getClass().getSimpleName();
			        String widgetId = WidgetUtil.getWidgetId(child);
			        ViewGroup.LayoutParams params = child.getLayoutParams();
			        
			        // Convert widget type to standard Android class names
			        widgetType = widgetType.equals("WidgetButton") ? "Button" : 
			                   widgetType.equals("WidgetTextView") ? "TextView" : 
			                   widgetType.equals("WidgetImageView") ? "ImageView" : 
			                   widgetType.equals("WidgetWebView") ? "WebView" : 
			                   widgetType.equals("WidgetLinear") ? "LinearLayout" : 
			                   widgetType.equals("WidgetEditText") ? "EditText" : 
			                   widgetType;
			
			        xmlCode.append("    <").append(widgetType).append("\n");
			        xmlCode.append("        android:id=\"@+id/").append(widgetId).append("\"\n");
			        
			        // Layout dimensions
			        xmlCode.append("        android:layout_width=\"")
			              .append(params.width == -1 ? "match_parent" : params.width == -2 ? "wrap_content" : params.width + "dp").append("\"\n");
			        xmlCode.append("        android:layout_height=\"")
			              .append(params.height == -1 ? "match_parent" : params.height == -2 ? "wrap_content" : params.height + "dp").append("\"");
			
			        // Common View properties
			        if (child.getVisibility() != View.VISIBLE) {
				            xmlCode.append("\n        android:visibility=\"")
				                  .append(child.getVisibility() == View.INVISIBLE ? "invisible" : "gone").append("\"");
				        }
			        
			        if (child.getAlpha() != 1.0f) {
				            xmlCode.append("\n        android:alpha=\"").append(child.getAlpha()).append("\"");
				        }
			        
			        if (child.getRotation() != 0f) {
				            xmlCode.append("\n        android:rotation=\"").append(child.getRotation()).append("\"");
				        }
			        
			        if (child.getScaleX() != 1.0f || child.getScaleY() != 1.0f) {
				            xmlCode.append("\n        android:scaleX=\"").append(child.getScaleX()).append("\"");
				            xmlCode.append("\n        android:scaleY=\"").append(child.getScaleX()).append("\"");
				        }
			        
			        if (child.getTranslationX() != 0f || child.getTranslationY() != 0f) {
				            xmlCode.append("\n        android:translationX=\"").append(child.getTranslationX()).append("dp\"");
				            xmlCode.append("\n        android:translationY=\"").append(child.getTranslationY()).append("dp\"");
				        }
			        
			        // Background handling with default white
			        if (child.getBackground() != null && child.getBackground() instanceof ColorDrawable) {
				            int color = ((ColorDrawable) child.getBackground()).getColor();
				            if (color == 0 || color == Color.TRANSPARENT) {
					                xmlCode.append("\n        android:background=\"#FFFFFF\""); // Default white
					            } else {
					                xmlCode.append("\n        android:background=\"#").append(String.format("%08X", color)).append("\"");
					            }
				        } else {
				            xmlCode.append("\n        android:background=\"#FFFFFF\""); // Default white
				        }
			
			        // Text related widgets (TextView and Button)
			        if (child instanceof WidgetTextView) {
				            WidgetTextView textView = (WidgetTextView) child;
				            
				            if (!TextUtils.isEmpty(textView.getText())) {
					                xmlCode.append("\n        android:text=\"").append(textView.getText().toString()).append("\"");
					            }
				            
				            if (textView.getTextSize() != 0) {
					                xmlCode.append("\n        android:textSize=\"").append(textView.getTextSize() / getResources().getDisplayMetrics().scaledDensity).append("sp\"");
					            }
				            
				            int textColor = textView.getCurrentTextColor();
				            if (textColor == 0 || textColor == Color.TRANSPARENT) {
					                xmlCode.append("\n        android:textColor=\"#000000\""); // Default black
					            } else {
					                xmlCode.append("\n        android:textColor=\"#").append(String.format("%08X", textColor)).append("\"");
					            }
				            
				            if (textView.getTypeface() != null) {
					                xmlCode.append("\n        android:fontFamily=\"").append(textView.getTypeface().toString()).append("\"");
					            }
				            
				            if (textView.getGravity() != Gravity.START) {
					                xmlCode.append("\n        android:gravity=\"")
					                      .append(gravityToString(textView.getGravity())).append("\"");
					            }
				            
				            if (textView.getLineSpacingMultiplier() != 1.0f) {
					                xmlCode.append("\n        android:lineSpacingMultiplier=\"").append(textView.getLineSpacingMultiplier()).append("\"");
					            }
				        }
			        
			        if (child instanceof WidgetEditText) {
				            WidgetEditText textView = (WidgetEditText) child;
				            
				            if (!TextUtils.isEmpty(textView.getText())) {
					                xmlCode.append("\n        android:text=\"").append(textView.getText().toString()).append("\"");
					            }
				            
				            if (textView.getTextSize() != 0) {
					                xmlCode.append("\n        android:textSize=\"").append(textView.getTextSize() / getResources().getDisplayMetrics().scaledDensity).append("sp\"");
					            }
				            
				            int textColor = textView.getCurrentTextColor();
				            if (textColor == 0 || textColor == Color.TRANSPARENT) {
					                xmlCode.append("\n        android:textColor=\"#000000\""); // Default black
					            } else {
					                xmlCode.append("\n        android:textColor=\"#").append(String.format("%08X", textColor)).append("\"");
					            }
				            
				            if (textView.getTypeface() != null) {
					                xmlCode.append("\n        android:fontFamily=\"").append(textView.getTypeface().toString()).append("\"");
					            }
				            
				            if (textView.getGravity() != Gravity.START) {
					                xmlCode.append("\n        android:gravity=\"")
					                      .append(gravityToString(textView.getGravity())).append("\"");
					            }
				            
				            if (textView.getLineSpacingMultiplier() != 1.0f) {
					                xmlCode.append("\n        android:lineSpacingMultiplier=\"").append(textView.getLineSpacingMultiplier()).append("\"");
					            }
				            
				            if (!TextUtils.isEmpty(textView.getHint())) {
					                xmlCode.append("\n        android:hint=\"").append(textView.getHint().toString()).append("\"");
					            }
				        }
			        
			        if (child instanceof WidgetButton) {
				            WidgetButton textView = (WidgetButton) child;
				            
				            if (!TextUtils.isEmpty(textView.getText())) {
					                xmlCode.append("\n        android:text=\"").append(textView.getText().toString()).append("\"");
					            }
				            
				            if (textView.getTextSize() != 0) {
					                xmlCode.append("\n        android:textSize=\"").append(textView.getTextSize() / getResources().getDisplayMetrics().scaledDensity).append("sp\"");
					            }
				            
				            int textColor = textView.getCurrentTextColor();
				            if (textColor == 0 || textColor == Color.TRANSPARENT) {
					                xmlCode.append("\n        android:textColor=\"#000000\""); // Default black
					            } else {
					                xmlCode.append("\n        android:textColor=\"#").append(String.format("%08X", textColor)).append("\"");
					            }
				            
				            if (textView.getTypeface() != null) {
					                xmlCode.append("\n        android:fontFamily=\"").append(textView.getTypeface().toString()).append("\"");
					            }
				            
				            if (textView.getGravity() != Gravity.START) {
					                xmlCode.append("\n        android:gravity=\"")
					                      .append(gravityToString(textView.getGravity())).append("\"");
					            }
				            
				            if (textView.getLineSpacingMultiplier() != 1.0f) {
					                xmlCode.append("\n        android:lineSpacingMultiplier=\"").append(textView.getLineSpacingMultiplier()).append("\"");
					            }
				        }
			
			        // ImageView specific properties
			        if (child instanceof WidgetImageView) {
				            WidgetImageView imageView = (WidgetImageView) child;
				            
				            String imagePath = WidgetUtil.getImagePath(child);
				            if (imagePath != null && !imagePath.isEmpty()) {
					                xmlCode.append("\n        android:src=\"@drawable/").append(new File(imagePath).getName().replace(".png", "").replace(".jpg", "")).append("\"");
					            } else {
					                xmlCode.append("\n        android:src=\"@drawable/default_image\""); // Default image
					            }
				            
				            if (imageView.getScaleType() != ImageView.ScaleType.FIT_CENTER) {
					                xmlCode.append("\n        android:scaleType=\"")
					                      .append(imageView.getScaleType().toString().toLowerCase()).append("\"");
					            }
				        }
			
			        // WebView specific properties
			        if (child instanceof WidgetWebView) {
				            WidgetWebView webView = (WidgetWebView) child;
				            xmlCode.append("\n        android:layout_weight=\"1\"");
				            xmlCode.append("\n        android:scrollbarStyle=\"outsideOverlay\"");
				            xmlCode.append("\n        android:scrollbars=\"vertical\"");
				        }
			
			        // Layout margins
			        if (params instanceof ViewGroup.MarginLayoutParams) {
				            ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) params;
				            if (marginParams.leftMargin != 0 || marginParams.topMargin != 0 || 
				                marginParams.rightMargin != 0 || marginParams.bottomMargin != 0) {
					                xmlCode.append("\n        android:layout_margin=\"")
					                      .append(marginParams.leftMargin).append("dp\"");
					                xmlCode.append("\n        android:layout_marginLeft=\"")
					                      .append(marginParams.leftMargin).append("dp\"");
					                xmlCode.append("\n        android:layout_marginTop=\"")
					                      .append(marginParams.topMargin).append("dp\"");
					                xmlCode.append("\n        android:layout_marginRight=\"")
					                      .append(marginParams.rightMargin).append("dp\"");
					                xmlCode.append("\n        android:layout_marginBottom=\"")
					                      .append(marginParams.bottomMargin).append("dp\"");
					                xmlCode.append("\n        android:layout_marginStart=\"")
					                      .append(marginParams.leftMargin).append("dp\"");
					                xmlCode.append("\n        android:layout_marginEnd=\"")
					                      .append(marginParams.rightMargin).append("dp\"");
					            }
				        }
			
			        // Padding
			        if (child.getPaddingLeft() != 0 || child.getPaddingTop() != 0 || 
			            child.getPaddingRight() != 0 || child.getPaddingBottom() != 0) {
				            xmlCode.append("\n        android:padding=\"")
				                  .append(child.getPaddingLeft()).append("dp\"");
				            xmlCode.append("\n        android:paddingLeft=\"")
				                  .append(child.getPaddingLeft()).append("dp\"");
				            xmlCode.append("\n        android:paddingTop=\"")
				                  .append(child.getPaddingTop()).append("dp\"");
				            xmlCode.append("\n        android:paddingRight=\"")
				                  .append(child.getPaddingRight()).append("dp\"");
				            xmlCode.append("\n        android:paddingBottom=\"")
				                  .append(child.getPaddingBottom()).append("dp\"");
				            xmlCode.append("\n        android:paddingStart=\"")
				                  .append(child.getPaddingLeft()).append("dp\"");
				            xmlCode.append("\n        android:paddingEnd=\"")
				                  .append(child.getPaddingRight()).append("dp\"");
				        }
			
			        xmlCode.append(" />\n\n");
			    }
		
		    xmlCode.append("</LinearLayout>");
		    return xmlCode.toString();
	}
	/**
 * TUDO : widgetSrc SET PROPERTY'S FOR IMAGE FROM STORAGE
 **/
	public void widgetSrc(String title, String message, final View g) {
		    if (selectedWidget == null) {
			        showToast("No widget selected");
			        return;
			    }
		
		    // Check storage permission
		    if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
		            != PackageManager.PERMISSION_GRANTED) {
			        ActivityCompat.requestPermissions(requireActivity(),
			                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
			        showToast("Storage permission required");
			        return;
			    }
		
		    // Save current state for undo
		    saveStateToUndo();
		
		    // Inflate dialog
		    LayoutInflater inflater = LayoutInflater.from(getContext());
		    final View dialogView = inflater.inflate(R.layout.dialog_file_selector, null);
		    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		    builder.setView(dialogView);
		    final AlertDialog alert = builder.create();
		
		    // Configure dialog window
		    if (alert.getWindow() != null) {
			        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
			        alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
			        alert.getWindow().setGravity(Gravity.CENTER);
			    }
		
		    // Initialize dialog views
		    TextView tvTitle = dialogView.findViewById(R.id.dialog_title);
		    ListView listView = dialogView.findViewById(R.id.file_list_view);
		    Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
		    Button btnSelect = dialogView.findViewById(R.id.btn_select);
		
		    // Set dialog content
		    tvTitle.setText(title);
		
		    // Get files from storage
		    ArrayList<FileItem> fileItems = getStorageFiles();
		    if (fileItems.isEmpty()) {
			        showToast("No images found in storage");
			        alert.dismiss();
			        return;
			    }
		
		    FileListAdapter adapter = new FileListAdapter(getContext(), fileItems);
		    listView.setAdapter(adapter);
		    listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		    // Check current selection if any
		    String currentPath = WidgetUtil.getImagePath(selectedWidget);
		    if (currentPath != null && !currentPath.isEmpty()) {
			        for (int i = 0; i < fileItems.size(); i++) {
				            if (fileItems.get(i).getPath().equals(currentPath)) {
					                listView.setItemChecked(i, true);
					                listView.setSelection(i);
					                break;
					            }
				        }
			    }
		
		    // Show dialog
		    alert.show();
		
		    // Cancel button
		    btnCancel.setOnClickListener(v -> alert.dismiss());
		
		    // Select button
		    btnSelect.setOnClickListener(v -> {
			        SparseBooleanArray checked = listView.getCheckedItemPositions();
			        boolean hasSelection = false;
			        for (int i = 0; i < checked.size(); i++) {
				            if (checked.valueAt(i)) {
					                FileItem selectedItem = fileItems.get(checked.keyAt(i));
					                if (!isValidImageFile(selectedItem.getPath())) {
						                    showToast("Invalid image file");
						                    return;
						                }
					                WidgetUtil.setImagePath(selectedWidget, selectedItem.getPath());
					                selectedWidget.requestLayout();
					                hasSelection = true;
					/*
                // Update ViewBean
                ProjectActivityBean.ViewBean viewBean = DesignActivity.createViewBean(selectedWidget);
                if (viewBean != null) {
                    viewBean.setImagePath(selectedItem.getPath());
                    updateWidgetInLayout(viewBean);
                }

                // Save changes
                e();
*/
					                // Clear redo stack
					                redoStack.clear();
					                showToast("Image source updated");
					                break;
					            }
				        }
			        if (!hasSelection) {
				            showToast("No image selected");
				        }
			        alert.dismiss();
			    });
	}
	private ArrayList<FileItem> getStorageFiles() {
		    ArrayList<FileItem> fileItems = new ArrayList<>();
		    File storageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/.blacklogics/resources/images/" + sc_id + "/");
		
		    if (!storageDir.exists() || !storageDir.isDirectory()) {
			        return fileItems; // Return empty list if directory doesn't exist
			    }
		
		    // Filter only image files
		    FilenameFilter imageFilter = (dir, name) -> {
			        String lowercaseName = name.toLowerCase();
			        return lowercaseName.endsWith(".jpg") || lowercaseName.endsWith(".jpeg") ||
			               lowercaseName.endsWith(".png") || lowercaseName.endsWith(".gif");
			    };
		
		    File[] files = storageDir.listFiles(imageFilter);
		    if (files != null) {
			        Arrays.sort(files, (f1, f2) -> f1.getName().compareToIgnoreCase(f2.getName())); // Sort alphabetically
			        for (File file : files) {
				            if (file.isFile() && file.canRead()) {
					                fileItems.add(new FileItem(file.getName(), file.getAbsolutePath()));
					            }
				        }
			    }
		    return fileItems;
	}
	// TODO: Implemented translation X at the top of the dialog
	public void showWidgetTranslationDialog(String titulo, String texto, final View y) {
		    if (selectedWidget == null) {
			        showToast("No widget selected");
			        return;
			    }
		
		    // Save current state for undo
		    saveStateToUndo();
		
		    // Inflate dialog
		    LayoutInflater inflater = LayoutInflater.from(getContext());
		    final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
		    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		    builder.setView(dialogView);
		    final AlertDialog alert = builder.create();
		
		    // Configure dialog window
		    if (alert.getWindow() != null) {
			        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
			        alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
			        alert.getWindow().setGravity(Gravity.CENTER);
			    }
		
		    // Initialize dialog views
		    ImageView icon = dialogView.findViewById(R.id.img_icon);
		    TextView tituloDlg = dialogView.findViewById(R.id.tv_title);
		    TextView mensagemDlg = dialogView.findViewById(R.id.tv_message);
		    LinearLayout tamanho = dialogView.findViewById(R.id.widget_temanho);
		    final EditText widgetTranslationX = dialogView.findViewById(R.id.et_widget);
		    final EditText widgetTranslationY = dialogView.findViewById(R.id.ed_input);
		    Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
		    Button btnSave = dialogView.findViewById(R.id.btn_ok);
		
		    // Configure input fields
		    widgetTranslationX.setFocusable(true);
		    widgetTranslationX.setFocusableInTouchMode(true);
		    widgetTranslationX.setClickable(true);
		    widgetTranslationX.setHint("X Translation (px)");
		    widgetTranslationX.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		
		    widgetTranslationY.setFocusable(true);
		    widgetTranslationY.setFocusableInTouchMode(true);
		    widgetTranslationY.setClickable(true);
		    widgetTranslationY.setHint("Y Translation (px)");
		    widgetTranslationY.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		
		    // Set dialog content
		    icon.setImageResource(R.drawable.enlarge_48);
		    tituloDlg.setText(titulo);
		    mensagemDlg.setVisibility(View.GONE);
		    tamanho.setVisibility(View.GONE);
		
		    // Set current translation values
		    float currentTranslationX = selectedWidget.getTranslationX();
		    float currentTranslationY = selectedWidget.getTranslationY();
		    widgetTranslationX.setText(String.format(Locale.getDefault(), "%.1f", currentTranslationX));
		    widgetTranslationY.setText(String.format(Locale.getDefault(), "%.1f", currentTranslationY));
		
		    // Show dialog
		    alert.show();
		
		    // Cancel button
		    btnCancel.setOnClickListener(v -> alert.dismiss());
		
		    // Save button
		    btnSave.setOnClickListener(v -> {
			        String xInput = widgetTranslationX.getText().toString().trim();
			        String yInput = widgetTranslationY.getText().toString().trim();
			
			        // Validate inputs
			        if (xInput.isEmpty()) {
				            widgetTranslationX.setError("Enter X translation");
				            return;
				        }
			        if (yInput.isEmpty()) {
				            widgetTranslationY.setError("Enter Y translation");
				            return;
				        }
			
			        try {
				            float newTranslationX = Float.parseFloat(xInput);
				            float newTranslationY = Float.parseFloat(yInput);
				
				            // Apply translations
				            selectedWidget.setTranslationX(newTranslationX);
				            selectedWidget.setTranslationY(newTranslationY);
				            selectedWidget.requestLayout();
				            if (selectedWidget.getParent() != null) {
					                ((View) selectedWidget.getParent()).requestLayout();
					                ((View) selectedWidget.getParent()).invalidate();
					            }
				
				            /*// Update ViewBean
            ProjectActivityBean.ViewBean viewBean = DesignActivity.createViewBean(selectedWidget);
            if (viewBean != null) {
                viewBean.setTranslationX(newTranslationX);
                viewBean.setTranslationY(newTranslationY);
                updateWidgetInLayout(viewBean);
            }

            // Save changes
            f();
            
            ((WidgetImageView) selectedWidget).setScaleType
*/
				            // Clear redo stack
				            redoStack.clear();
				            showToast("Translation updated");
				
				        } catch (NumberFormatException e) {
				            showToast("Invalid translation values");
				            return;
				        }
			
			        alert.dismiss();
			    });
	}
	/**
TUDO : HELPER FOR TEXT VIEW COLOR SET 
**/
	public void textColorDialog(View kk) {
		    if (selectedWidget == null) {
			        showToast("No widget selected");
			        return;
			    }
		
		    TextView textView = WidgetUtil.getTextViewOfWidget(selectedWidget);
		    if (textView == null) {
			        showToast("Selected widget is not a TextView");
			        return;
			    }
		
		    // Save current state for undo
		    saveStateToUndo();
		
		    // Inflate dialog
		    LayoutInflater inflater = LayoutInflater.from(getContext());
		    final View dialogView = inflater.inflate(R.layout.base, null);
		    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		    builder.setView(dialogView);
		    final AlertDialog alertDialog = builder.create();
		
		    // Configure dialog window
		    if (alertDialog.getWindow() != null) {
			        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
			        alertDialog.getWindow().setGravity(Gravity.CENTER);
			    }
		
		    // Initialize dialog views
		    LinearColorPicker colorPicker = dialogView.findViewById(R.id.colorPicker);
		    if (colorPicker == null) {
			        showToast("Color picker not found");
			        alertDialog.dismiss();
			        return;
			    }
		
		    // Set initial color
		    final int[] selectedColor = {textView.getCurrentTextColor()};
		    colorPicker.setSelectedColor(selectedColor[0]);
		
		    // Set up color picker listener for real-time preview
		    colorPicker.setOnColorSelectedListener(color -> {
			        selectedColor[0] = color;
			        textView.setTextColor(color); // Real-time preview
			        textView.requestLayout();
			    });
		
		    // Set dialog buttons
		    builder.setPositiveButton("OK", (dialog, which) -> {
			        // Apply final color
			        textView.setTextColor(selectedColor[0]);
			
			        // Update ViewBean
			        /*
        ProjectActivityBean.ViewBean viewBean = DesignActivity.createViewBean(selectedWidget);
        if (viewBean != null) {
            viewBean.setTextColor(selectedColor[0]);
            updateWidgetInLayout(viewBean);
        }

        // Save changes
        g();
        **/
			
			        // Clear redo stack
			        redoStack.clear();
			        showToast("Text color updated");
			    });
		
		    builder.setNegativeButton("Cancel", (dialog, which) -> {
			        // Revert to original color on cancel
			        textView.setTextColor(WidgetUtil.getTextViewOfWidget(selectedWidget).getCurrentTextColor());
			        textView.requestLayout();
			    });
		
		    // Show dialog
		    alertDialog.show();
	}
	/**
TUDO :  * Shows a dialog for setting text size with 2-unit intervals (10,12,14...) up to 100sp
 * Includes bold checkbox option
 */
	public void showTextSizeDialog(final View g) {
			if (selectedWidget == null) {
					showToast("No widget selected");
					return;
			}
			
			if (!(selectedWidget instanceof WidgetTextView || selectedWidget instanceof WidgetButton ||
			selectedWidget instanceof WidgetEditText || selectedWidget instanceof WidgetCheckBox ||
			selectedWidget instanceof WidgetSwitch)) {
					showToast("Text size not supported for this widget");
					return;
			}
			
			// Save current state for undo
			saveStateToUndo();
			
			// Inflate dialog
			LayoutInflater inflater = LayoutInflater.from(getContext());
			final View dialogView = inflater.inflate(R.layout.custom_textsize_dialog, null);
			AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
			builder.setView(dialogView);
			final AlertDialog alert = builder.create();
			
			// Configure dialog window
			if (alert.getWindow() != null) {
					alert.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
					alert.getWindow().setGravity(Gravity.CENTER);
			}
			
			// Initialize views
			TextView title = dialogView.findViewById(R.id.dialog_title);
			ListView sizeList = dialogView.findViewById(R.id.size_list);
			Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
			Button btnSelect = dialogView.findViewById(R.id.btn_select);
			
			// Set dialog content
			title.setText("Select Text Size");
			
			// Create size options from 8 to 100 with 2-unit intervals
			ArrayList<String> sizes = new ArrayList<>();
			for (int i = 8; i <= 100; i += 2) {
					sizes.add(i + "sp");
			}
			
			ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
			android.R.layout.simple_list_item_single_choice, sizes);
			sizeList.setAdapter(adapter);
			sizeList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			
			// Get current text size
			float currentSize = 0;
			final TextView[] targetView = new TextView[1];
			
			if (selectedWidget instanceof WidgetTextView) {
					targetView[0] = ((WidgetTextView) selectedWidget).getTextView();
			} else if (selectedWidget instanceof WidgetButton) {
					targetView[0] = ((WidgetButton) selectedWidget).getTextView();
			} else if (selectedWidget instanceof WidgetEditText) {
					targetView[0] = ((WidgetEditText) selectedWidget).getEditText();
			} else if (selectedWidget instanceof WidgetCheckBox) {
					targetView[0] = ((WidgetCheckBox) selectedWidget).getCheckBoxDisplay();
			} else if (selectedWidget instanceof WidgetSwitch) {
					targetView[0] = ((WidgetSwitch) selectedWidget).getSwitchDisplay();
			}
			
			if (targetView[0] != null) {
					currentSize = targetView[0].getTextSize() / getResources().getDisplayMetrics().scaledDensity;
			}
			
			
			// Set current selection
			int closestIndex = 0;
			float smallestDiff = Float.MAX_VALUE;
			for (int i = 0; i < sizes.size(); i++) {
					float size = Float.parseFloat(sizes.get(i).replace("sp", ""));
					float diff = Math.abs(size - currentSize);
					if (diff < smallestDiff) {
							smallestDiff = diff;
							closestIndex = i;
					}
			}
			sizeList.setItemChecked(closestIndex, true);
			sizeList.setSelection(closestIndex);
			
			// Show dialog
			alert.show();
			
			// Button click listeners
			btnCancel.setOnClickListener(v -> alert.dismiss());
			
			btnSelect.setOnClickListener(v -> {
					int selectedPosition = sizeList.getCheckedItemPosition();
					if (selectedPosition == ListView.INVALID_POSITION) {
							showToast("Please select a text size");
							return;
					}
					
					// Get selected size
					String selectedSize = sizes.get(selectedPosition);
					float size = Float.parseFloat(selectedSize.replace("sp", ""));
					
					// Apply text size
					if (targetView[0] != null) {
							targetView[0].setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
							selectedWidget.requestLayout();
							if (selectedWidget.getParent() != null) {
									((View) selectedWidget.getParent()).requestLayout();
									((View) selectedWidget.getParent()).invalidate();
							}
							
							// Update ViewBean
				            /*
			ProjectActivityBean.ViewBean viewBean = DesignActivity.createViewBean(selectedWidget);
			if (viewBean != null) {
				viewBean.setTextSize(size);
				updateWidgetInLayout(viewBean);
			}
			
			// Save changes
			h();*/
							
							// Clear redo stack
							redoStack.clear();
							showToast("Text size updated");
					}
					
					alert.dismiss();
			});
	}
	
	/*
TUDO : TEXT STYLE DIALOG
*/
	public void showTextStyleOnlyDialog(final View q) {
		    if (selectedWidget == null) {
			        showToast("No widget selected");
			        return;
			    }
		
		    TextView textView = WidgetUtil.getTextViewOfWidget(selectedWidget);
		    if (textView == null) {
			        showToast("Selected widget is not a TextView");
			        return;
			    }
		
		    // Save current state for undo
		    saveStateToUndo();
		
		    // Inflate dialog
		    LayoutInflater inflater = LayoutInflater.from(getContext());
		    final View dialogView = inflater.inflate(R.layout.text_style_only_dialog, null);
		    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		    builder.setView(dialogView);
		    final AlertDialog alert = builder.create();
		
		    // Configure dialog window
		    if (alert.getWindow() != null) {
			        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
			        alert.getWindow().setGravity(Gravity.CENTER);
			    }
		
		    // Initialize views
		    TextView title = dialogView.findViewById(R.id.dialog_title);
		    RadioGroup styleGroup = dialogView.findViewById(R.id.style_radio_group);
		    RadioButton normalStyle = dialogView.findViewById(R.id.normal_style);
		    RadioButton boldStyle = dialogView.findViewById(R.id.bold_style);
		    RadioButton italicStyle = dialogView.findViewById(R.id.italic_style);
		    RadioButton boldItalicStyle = dialogView.findViewById(R.id.bold_italic_style);
		    Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
		    Button btnSelect = dialogView.findViewById(R.id.btn_select);
		
		    // Set dialog title
		    title.setText("Select Text Style");
		
		    // Set current style
		    Typeface currentTypeface = textView.getTypeface();
		    if (currentTypeface != null) {
			        if (currentTypeface.isBold() && currentTypeface.isItalic()) {
				            boldItalicStyle.setChecked(true);
				        } else if (currentTypeface.isBold()) {
				            boldStyle.setChecked(true);
				        } else if (currentTypeface.isItalic()) {
				            italicStyle.setChecked(true);
				        } else {
				            normalStyle.setChecked(true);
				        }
			    } else {
			        normalStyle.setChecked(true);
			    }
		
		    // Real-time preview on radio button change
		    styleGroup.setOnCheckedChangeListener((group, checkedId) -> {
			        int style = Typeface.NORMAL;
			        if (checkedId == R.id.bold_style) {
				            style = Typeface.BOLD;
				        } else if (checkedId == R.id.italic_style) {
				            style = Typeface.ITALIC;
				        } else if (checkedId == R.id.bold_italic_style) {
				            style = Typeface.BOLD_ITALIC;
				        }
			        textView.setTypeface(Typeface.defaultFromStyle(style));
			        textView.requestLayout();
			    });
		
		    // Cancel button
		    btnCancel.setOnClickListener(v -> {
			        // Revert to original style
			        textView.setTypeface(currentTypeface);
			        textView.requestLayout();
			        alert.dismiss();
			    });
		
		    // Select button
		    btnSelect.setOnClickListener(v -> {
			        int selectedId = styleGroup.getCheckedRadioButtonId();
			        if (selectedId == -1) {
				            showToast("Please select a text style");
				            return;
				        }
			
			        int style = Typeface.NORMAL;
			        if (selectedId == R.id.bold_style) {
				            style = Typeface.BOLD;
				        } else if (selectedId == R.id.italic_style) {
				            style = Typeface.ITALIC;
				        } else if (selectedId == R.id.bold_italic_style) {
				            style = Typeface.BOLD_ITALIC;
				        }
			
			        // Apply text style
			        textView.setTypeface(Typeface.defaultFromStyle(style));
			        selectedWidget.requestLayout();
			        if (selectedWidget.getParent() != null) {
				            ((View) selectedWidget.getParent()).requestLayout();
				            ((View) selectedWidget.getParent()).invalidate();
				        }
			
			        // Update ViewBean
			        ProjectActivityBean.ViewBean viewBean = DesignActivity.createViewBean(selectedWidget);
			        if (viewBean != null) {
				            viewBean.setTextStyle(String.valueOf((long)(style)));
				            updateWidgetInLayout(viewBean);
				        }
			
			        // Save changes
			        i();
			
			        // Clear redo stack
			        redoStack.clear();
			        showToast("Text style updated");
			
			        alert.dismiss();
			    });
		
		    // Show dialog
		    alert.show();
	}
	/**
TUDO : setLines
**/
	public void setLines(String titulo, String texto, final View selectedWidget) {
		    if (selectedWidget == null) {
			        showToast("No widget selected");
			        return;
			    }
		
		    if (!(selectedWidget instanceof WidgetTextView || selectedWidget instanceof WidgetEditText)) {
			        showToast("Lines not supported for this widget");
			        return;
			    }
		
		    // Save current state for undo
		    saveStateToUndo();
		
		    // Inflate dialog
		    LayoutInflater inflater = LayoutInflater.from(getContext());
		    final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
		    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		    builder.setView(dialogView);
		    final AlertDialog alert = builder.create();
		
		    // Configure dialog window
		    if (alert.getWindow() != null) {
			        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
			        alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
			        alert.getWindow().setGravity(Gravity.CENTER);
			    }
		
		    // Initialize views
		    ImageView icon = dialogView.findViewById(R.id.img_icon);
		    TextView tituloDlg = dialogView.findViewById(R.id.tv_title);
		    TextView mensagemDlg = dialogView.findViewById(R.id.tv_message);
		    LinearLayout tamanho = dialogView.findViewById(R.id.widget_temanho);
		    final EditText widgetLines = dialogView.findViewById(R.id.ed_input);
		    Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
		    Button btnSave = dialogView.findViewById(R.id.btn_ok);
		
		    // Configure input field
		    widgetLines.setFocusable(true);
		    widgetLines.setFocusableInTouchMode(true);
		    widgetLines.setClickable(true);
		    widgetLines.setHint("Number of Lines (1-100)");
		    widgetLines.setInputType(InputType.TYPE_CLASS_NUMBER);
		    tamanho.setVisibility(View.VISIBLE);
		   // ((TextView) dialogView.findViewById(R.id.tv_tamanho)).setText("Lines:");
		
		    // Hide unused field
		    final EditText widgetTextId = dialogView.findViewById(R.id.et_widget);
		    widgetTextId.setVisibility(View.GONE);
		
		    // Set dialog content
		    icon.setImageResource(R.drawable.numbers_48);
		    tituloDlg.setText(titulo);
		    mensagemDlg.setVisibility(View.GONE);
		
		    // Set current lines
		    int currentLines = 0;
		    if (selectedWidget instanceof WidgetTextView) {
			        currentLines = ((WidgetTextView) selectedWidget).getLines();
			    } else if (selectedWidget instanceof WidgetEditText) {
			        currentLines = ((WidgetEditText) selectedWidget).getEditText().getMaxLines();
			    }
		    widgetLines.setText(String.valueOf(currentLines));
		
		    // Show dialog
		    alert.show();
		
		    // Cancel button
		    btnCancel.setOnClickListener(v -> alert.dismiss());
		
		    // Save button
		    btnSave.setOnClickListener(v -> {
			        String input = widgetLines.getText().toString().trim();
			        if (input.isEmpty()) {
				            widgetLines.setError("Enter number of lines");
				            return;
				        }
			
			        try {
				            int lines = Integer.parseInt(input);
				            if (lines < 1 || lines > 100) {
					                widgetLines.setError("Lines must be between 1 and 100");
					                return;
					            }
				
				            // Apply lines
				            if (selectedWidget instanceof WidgetTextView) {
					                ((WidgetTextView) selectedWidget).setLines(lines);
					                ((WidgetTextView) selectedWidget).setMaxLines(lines);
					            } else if (selectedWidget instanceof WidgetEditText) {
					                ((WidgetEditText) selectedWidget).getEditText().setLines(lines);
					                ((WidgetEditText) selectedWidget).getEditText().setMaxLines(lines);
					            }
				            selectedWidget.requestLayout();
				            if (selectedWidget.getParent() != null) {
					                ((View) selectedWidget.getParent()).requestLayout();
					                ((View) selectedWidget.getParent()).invalidate();
					            }
				
				            // Update ViewBean
				            ProjectActivityBean.ViewBean viewBean = DesignActivity.createViewBean(selectedWidget);
				            if (viewBean != null) {
					                viewBean.setLines(lines);
					                updateWidgetInLayout(viewBean);
					            }
				
				            // Save changes
				            j();
				
				            // Clear redo stack
				            redoStack.clear();
				            showToast("Lines updated");
				
				        } catch (NumberFormatException e) {
				            widgetLines.setError("Invalid number");
				            return;
				        }
			
			        alert.dismiss();
			    });
	}
	/**
TUDO : HELPER FOR BACKGROUND COLOR SET 
**/
	public void backgroundColorDialog(View y) {
		    if (context == null || selectedWidget == null || selectedWidgetProperties == null) return;
		
		    // Save current state for undo
		    saveStateToUndo();
		
		    // Inflate dialog
		    LayoutInflater inflater = LayoutInflater.from(getContext());
		    final View dialogView = inflater.inflate(R.layout.base, null);
		    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		    builder.setView(dialogView);
		    final AlertDialog alertDialog = builder.create();
		
		    // Configure dialog window
		    if (alertDialog.getWindow() != null) {
			        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
			        alertDialog.getWindow().setGravity(Gravity.CENTER);
			    }
		
		    // Initialize dialog views
		    LinearColorPicker colorPicker = dialogView.findViewById(R.id.colorPicker);
		    if (colorPicker == null) {
			        showToast("Color picker not found");
			        alertDialog.dismiss();
			        return;
			    }
		
		    // Get current background color (default to transparent if none set)
		    final int[] selectedColor = {Color.TRANSPARENT};
		    Drawable background = new ColorDrawable(selectedWidgetProperties.getBackgroundColor());
		    if (background instanceof ColorDrawable) {
			        selectedColor[0] = ((ColorDrawable) background).getColor();
			    }
		    colorPicker.setSelectedColor(selectedColor[0]);
		
		    // Set up color picker listener for real-time preview
		    colorPicker.setOnColorSelectedListener(color -> {
			        selectedColor[0] = color;
			        selectedWidget.setBackgroundColor(color); // Real-time preview
			       selectedWidgetProperties.setBackgroundColor(selectedColor[0]);
			        selectedWidget.requestLayout();
			      //  ll.invaildate();
			    });
		
		    // Set dialog buttons
		    builder.setPositiveButton("OK", (dialog, which) -> {
			        // Apply final color
			        selectedWidget.setBackgroundColor(selectedColor[0]);
			        selectedWidgetProperties.setBackgroundColor(selectedColor[0]);
			
			        // Clear redo stack
			        redoStack.clear();
			        showToast("Background color updated");
			    });
		
		    builder.setNegativeButton("Cancel", (dialog, which) -> {
			        // Revert to original background
			        selectedWidget.setBackground(background);
			        selectedWidget.requestLayout();
			    });
		
		    // Show dialog
		    alertDialog.show();
	}
	/**
TUDO : HELPER SHOW MESSAGE
**/
	public void showMessage(String str) {
			SketchwareUtil.showMessage(getContext().getApplicationContext(), str);
	}
	public void showToast(String str) {
		//	SketchwareUtil.showMessage(getContext().getApplicationContext(), str);
	}
	/**
TUDO : A HELPER FOR ON BACK PRESS
**/
	public boolean handleBackPressed() {
		        if (isHiddenProperties()) {
			            exibirMensagemEdt("Exit from this project", "Do you want to save your before quitting?");
			            return true; // Consumed the back press
			        }
		        unselectSelectedWidget();
		        return false; // Let activity handle it
		    }
	// In ViewEditorFragmentActivity.java
	private/* static*/ LinearLayout getPropertiesPanel() {
		    if (requireActivity() instanceof DesignActivity) {
			        return ((DesignActivity) requireActivity()).getPropertiesPanel();
			    }
		    return null;
	}
	/**
TUDO : HELPER FOR GET ALL WIDGETS IDS LIST!!!
**/
	/**
 * TUDO: Get all widget IDs as a comma-separated string
 * @return Comma-separated string of widget IDs
 */
	public static String getAllWidgetsIdString(ViewGroup rootView) {
		    return TextUtils.join(",", getAllWidgetsId(rootView));
	}
	/**
 * TUDO: Get all widget IDs from the layout
 * @return List of widget IDs in @+id/... format
 */
	public static List<String> getAllWidgetsId(ViewGroup rootView) {
		    List<Pair<Integer, String>> pairs = getAllWidgetsIdAsPairs();
		    List<String> ids = new ArrayList<>();
		    for (Pair<Integer, String> pair : pairs) {
			        ids.add(pair.second);
			    }
		    return ids;
	}
	/**
 * TUDO : SET MARGIN DIALOG FOR WIDGETS
 **/
	public void setMarginDialog(String title, String message, final View sg) {
		    if (selectedWidget == null) {
			        showToast("No widget selected");
			        return;
			    }
		
		    ViewGroup.LayoutParams params = selectedWidget.getLayoutParams();
		    if (!(params instanceof ViewGroup.MarginLayoutParams)) {
			        showToast("Margins not supported for this widget");
			        return;
			    }
		
		    // Save current state for undo
		    saveStateToUndo();
		
		    // Inflate dialog
		    LayoutInflater inflater = LayoutInflater.from(getContext());
		    final View dialogView = inflater.inflate(R.layout.dialog2, null);
		    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		    builder.setView(dialogView);
		    final AlertDialog alert = builder.create();
		
		    // Configure dialog window
		    if (alert.getWindow() != null) {
			        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
			        alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
			        alert.getWindow().setGravity(Gravity.CENTER);
			    }
		
		    // Initialize views
		    ImageView icon = dialogView.findViewById(R.id.imageview1);
		    TextView titleTv = dialogView.findViewById(R.id.textview1);
		    EditText allMarginsEt = dialogView.findViewById(R.id.edittext1);
		    EditText leftEt = dialogView.findViewById(R.id.edittext2);
		    EditText rightEt = dialogView.findViewById(R.id.edittext4);
		    EditText topEt = dialogView.findViewById(R.id.edittext5);
		    EditText bottomEt = dialogView.findViewById(R.id.edittext3);
		    CheckBox toggleCb = dialogView.findViewById(R.id.checkbox1);
		    LinearLayout advancedLayout = dialogView.findViewById(R.id.linear5);
		    TextView btnCancel = dialogView.findViewById(R.id.textview12);
		    TextView btnSave = dialogView.findViewById(R.id.textview13);
		
		    // Set dialog title and icon
		    icon.setImageResource(R.drawable.ic_fullscreen_grey);
		    titleTv.setText(title);
		
		    // Get current margins
		    ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) params;
		    allMarginsEt.setText(String.valueOf(marginParams.leftMargin)); // Default for simple mode
		    leftEt.setText(String.valueOf(marginParams.leftMargin));
		    rightEt.setText(String.valueOf(marginParams.rightMargin));
		    topEt.setText(String.valueOf(marginParams.topMargin));
		    bottomEt.setText(String.valueOf(marginParams.bottomMargin));
		
		    // Toggle between simple and advanced margin controls
		    toggleCb.setOnCheckedChangeListener((buttonView, isChecked) -> {
			        advancedLayout.setVisibility(isChecked ? View.GONE : View.VISIBLE);
			        allMarginsEt.setVisibility(isChecked ? View.VISIBLE : View.GONE);
			    });
		
		    // Show dialog
		    alert.show();
		
		    // Cancel button
		    btnCancel.setOnClickListener(v -> alert.dismiss());
		
		    // Save button
		    btnSave.setOnClickListener(v -> {
			        try {
				            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) selectedWidget.getLayoutParams();
				            int left, right, top, bottom;
				
				            if (toggleCb.isChecked()) {
					                // Apply same margin to all sides
					                String allMarginsStr = allMarginsEt.getText().toString().trim();
					                if (allMarginsStr.isEmpty()) {
						                    allMarginsEt.setError("Enter margin value");
						                    return;
						                }
					                int margin = Integer.parseInt(allMarginsStr);
					                if (margin < 0 || margin > 1000) {
						                    allMarginsEt.setError("Margin must be between 0 and 1000");
						                    return;
						                }
					                left = right = top = bottom = margin;
					            } else {
					                // Apply individual margins
					                String leftStr = leftEt.getText().toString().trim();
					                String rightStr = rightEt.getText().toString().trim();
					                String topStr = topEt.getText().toString().trim();
					                String bottomStr = bottomEt.getText().toString().trim();
					
					                if (leftStr.isEmpty()) {
						                    leftEt.setError("Enter left margin");
						                    return;
						                }
					                if (rightStr.isEmpty()) {
						                    rightEt.setError("Enter right margin");
						                    return;
						                }
					                if (topStr.isEmpty()) {
						                    topEt.setError("Enter top margin");
						                    return;
						                }
					                if (bottomStr.isEmpty()) {
						                    bottomEt.setError("Enter bottom margin");
						                    return;
						                }
					
					                left = Integer.parseInt(leftStr);
					                right = Integer.parseInt(rightStr);
					                top = Integer.parseInt(topStr);
					                bottom = Integer.parseInt(bottomStr);
					
					                if (left < 0 || left > 1000) {
						                    leftEt.setError("Margin must be between 0 and 1000");
						                    return;
						                }
					                if (right < 0 || right > 1000) {
						                    rightEt.setError("Margin must be between 0 and 1000");
						                    return;
						                }
					                if (top < 0 || top > 1000) {
						                    topEt.setError("Margin must be between 0 and 1000");
						                    return;
						                }
					                if (bottom < 0 || bottom > 1000) {
						                    bottomEt.setError("Margin must be between 0 and 1000");
						                    return;
						                }
					            }
				
				            // Apply margins
				            layoutParams.setMargins(left, top, right, bottom);
				            selectedWidget.setLayoutParams(layoutParams);
				            selectedWidget.requestLayout();
				            if (selectedWidget.getParent() != null) {
					                ((View) selectedWidget.getParent()).requestLayout();
					                ((View) selectedWidget.getParent()).invalidate();
					            }
				
				            // Update ViewBean
				
				            // Clear redo stack
				            redoStack.clear();
				            showToast("Margins updated");
				
				            alert.dismiss();
				        } catch (NumberFormatException e) {
				            showToast("Please enter valid numbers");
				        }
			    });
	}
	/**
 * TUDO : SET PADDING DIALOG FOR WIDGETS
 **/
	public void setPaddingDialog(String title, String message, final View ks) {
		    if (selectedWidget == null) {
			        showToast("No widget selected");
			        return;
			    }
		
		    // Save current state for undo
		    saveStateToUndo();
		
		    // Inflate dialog
		    LayoutInflater inflater = LayoutInflater.from(getContext());
		    final View dialogView = inflater.inflate(R.layout.dialog2, null);
		    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		    builder.setView(dialogView);
		    final AlertDialog alert = builder.create();
		
		    // Configure dialog window
		    if (alert.getWindow() != null) {
			        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
			        alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
			        alert.getWindow().setGravity(Gravity.CENTER);
			    }
		
		    // Initialize views
		    ImageView icon = dialogView.findViewById(R.id.imageview1);
		    TextView titleTv = dialogView.findViewById(R.id.textview1);
		    EditText allPaddingsEt = dialogView.findViewById(R.id.edittext1);
		    EditText leftEt = dialogView.findViewById(R.id.edittext2);
		    EditText rightEt = dialogView.findViewById(R.id.edittext4);
		    EditText topEt = dialogView.findViewById(R.id.edittext5);
		    EditText bottomEt = dialogView.findViewById(R.id.edittext3);
		    CheckBox toggleCb = dialogView.findViewById(R.id.checkbox1);
		    LinearLayout advancedLayout = dialogView.findViewById(R.id.linear5);
		    TextView btnCancel = dialogView.findViewById(R.id.textview12);
		    TextView btnSave = dialogView.findViewById(R.id.textview13);
		
		    // Set dialog title and icon
		    icon.setImageResource(R.drawable.collect_48);
		    titleTv.setText(title);
		
		    // Get current padding
		    allPaddingsEt.setText(String.valueOf(selectedWidget.getPaddingLeft())); // Default for simple mode
		    leftEt.setText(String.valueOf(selectedWidget.getPaddingLeft()));
		    rightEt.setText(String.valueOf(selectedWidget.getPaddingRight()));
		    topEt.setText(String.valueOf(selectedWidget.getPaddingTop()));
		    bottomEt.setText(String.valueOf(selectedWidget.getPaddingBottom()));
		
		    // Toggle between simple and advanced padding controls
		    toggleCb.setOnCheckedChangeListener((buttonView, isChecked) -> {
			        advancedLayout.setVisibility(isChecked ? View.GONE : View.VISIBLE);
			        allPaddingsEt.setVisibility(isChecked ? View.VISIBLE : View.GONE);
			    });
		
		    // Show dialog
		    alert.show();
		
		    // Cancel button
		    btnCancel.setOnClickListener(v -> alert.dismiss());
		
		    // Save button
		    btnSave.setOnClickListener(v -> {
			        try {
				            int left, right, top, bottom;
				
				            if (toggleCb.isChecked()) {
					                // Apply same padding to all sides
					                String allPaddingsStr = allPaddingsEt.getText().toString().trim();
					                if (allPaddingsStr.isEmpty()) {
						                    allPaddingsEt.setError("Enter padding value");
						                    return;
						                }
					                int padding = Integer.parseInt(allPaddingsStr);
					                if (padding < 0 || padding > 1000) {
						                    allPaddingsEt.setError("Padding must be between 0 and 1000");
						                    return;
						                }
					                left = right = top = bottom = padding;
					            } else {
					                // Apply individual paddings
					                String leftStr = leftEt.getText().toString().trim();
					                String rightStr = rightEt.getText().toString().trim();
					                String topStr = topEt.getText().toString().trim();
					                String bottomStr = bottomEt.getText().toString().trim();
					
					                if (leftStr.isEmpty()) {
						                    leftEt.setError("Enter left padding");
						                    return;
						                }
					                if (rightStr.isEmpty()) {
						                    rightEt.setError("Enter right padding");
						                    return;
						                }
					                if (topStr.isEmpty()) {
						                    topEt.setError("Enter top padding");
						                    return;
						                }
					                if (bottomStr.isEmpty()) {
						                    bottomEt.setError("Enter bottom padding");
						                    return;
						                }
					
					                left = Integer.parseInt(leftStr);
					                right = Integer.parseInt(rightStr);
					                top = Integer.parseInt(topStr);
					                bottom = Integer.parseInt(bottomStr);
					
					                if (left < 0 || left > 1000) {
						                    leftEt.setError("Padding must be between 0 and 1000");
						                    return;
						                }
					                if (right < 0 || right > 1000) {
						                    rightEt.setError("Padding must be between 0 and 1000");
						                    return;
						                }
					                if (top < 0 || top > 1000) {
						                    topEt.setError("Padding must be between 0 and 1000");
						                    return;
						                }
					                if (bottom < 0 || bottom > 1000) {
						                    bottomEt.setError("Padding must be between 0 and 1000");
						                    return;
						                }
					            }
				
				            // Apply padding
				            selectedWidget.setPadding(left, top, right, bottom);
				            selectedWidget.requestLayout();
				            if (selectedWidget.getParent() != null) {
					                ((View) selectedWidget.getParent()).requestLayout();
					                ((View) selectedWidget.getParent()).invalidate();
					            }
				
				            // Update ViewBean
				            
				            // Clear redo stack
				            redoStack.clear();
				            showToast("Padding updated");
				
				            alert.dismiss();
				        } catch (NumberFormatException e) {
				            showToast("Please enter valid numbers");
				        }
			    });
	}
	/**
 * TUDO: Get all WidgetTextView IDs from the layout
 **/
	
	public List<String> getAllWidgetTextViewIds() {
		    List<String> textViewIds = new ArrayList<>();
		    if (ll == null) return textViewIds;
		    
		    for (int i = 0; i < ll.getChildCount(); i++) {
			        View child = ll.getChildAt(i);
			        if (child instanceof ViewGroup) {
				            ViewGroup viewGroup = (ViewGroup) child;
				            for (int j = 0; j < viewGroup.getChildCount(); j++) {
					                View widget = viewGroup.getChildAt(j);
					                if (widget instanceof WidgetTextView) {
						                    String widgetId = WidgetUtil.getWidgetId(widget);
						                    if (widgetId != null && !widgetId.isEmpty()) {
							                        textViewIds.add(widgetId);
							                    }
						                }
					            }
				        }
			    }
		    return textViewIds;
	}
	
	/**
 * TUDO: Get all WidgetTextView IDs as comma-separated string
 **/
	public String getAllWidgetTextViewIdsString() {
		    return TextUtils.join(",", getAllWidgetTextViewIds());
	}
	/**
TUDO : REFRESH LIFECYCLE
**/
	public static void refreshUI() {
		    if (ll != null) {
			        ll.post(() -> {
				            ll.invalidate();
				            ll.requestLayout();
				        });
			    }
	}
	public void reloadAllWidgets() {
		      /*  if (ll != null) {
            ll.removeAllViews();
            loadView();
        }*/
		    }
	/**
* TUDO: Switch to a different activity/layout
* @param activityName Name of the activity to switch to
* @param layoutName Name of the layout to load
* @param isMain Whether this is the main activity
*/
	public void switchActivityLayout(String activityName, String layoutName, boolean isMain) {
			// Check if context is still valid
			if (getContext() == null || getActivity() == null || getActivity().isFinishing()) {
					return;
			}
			
			try {
					// Save current state before switching
					if (!saveView()) {
							showMessage("Failed to save current layout before switching");
							return;
					}
					
					// Update current activity info
					this.activityName = activityName != null ? activityName : "MainActivity";
					this.layoutName = layoutName != null ? layoutName : "main";
					this.isMainActivity = isMain;
					
					DesignActivity.currentActivityBean = new ProjectActivityBean(
					activityName,
					layoutName,
					pkgName,
					false,
					sc_id,
					scName
					);
					
					if (ViewEditorFragmentActivity.instance != null) {
							ViewEditorFragmentActivity.instance.loadLayout();
					} 
					
					// Refresh the view
					refreshActivityView();
			} catch (Exception e) {
					showMessage("Error switching activity: " + e.getMessage());
					e.printStackTrace();
			}
	}
	/**
* TUDO: Refresh the current activity view
*/
	public void refreshActivityView() {
			if (getContext() == null || ll == null) {
					return;
			}
			
			try {
					// Clear current view
					ll.removeAllViews();
					
					// Load the new layout
					loadView();
					
					// Update UI elements
					if (tv_view_name != null) {
							tv_view_name.setText(layoutName);
					}
					
					// Refresh properties panel
					if (isHiddenProperties()) {
							hideProperties();
					} else {
							showProperties();
					}
					
					// Notify view model of refresh
					if (viewModel != null) {
							viewModel.refreshTrigger.setValue(true);
					}
			} catch (Exception e) {
					showMessage("Error refreshing view: " + e.getMessage());
					e.printStackTrace();
			}
	}
	
	/**
* TUDO: Add a new activity to the project
* @param activityData The activity data to add
*/
	public void addActivity(ActivityData activityData) {
			if (otherActivities == null) {
					otherActivities = new ArrayList<>();
			}
			
			// Check if activity already exists
			for (ActivityData existing : otherActivities) {
					if (existing.getName().equals(activityData.getName())) {
							showMessage("Activity already exists");
							return;
					}
			}
			
			otherActivities.add(activityData);
			showMessage("Activity added - switch to it to edit");
	}
	
	/**
* TUDO: Get activity by name
* @param name Name of the activity to find
* @return The ActivityData or null if not found
*/
	public ActivityData getActivityByName(String name) {
			if (otherActivities == null) return null;
			
			for (ActivityData activity : otherActivities) {
					if (activity.getName().equals(name)) {
							return activity;
					}
			}
			return null;
	}
	
	// Modified showInjectAttributesDialog to handle custom attributes
	private void showInjectAttributesDialog(View h) {
		    View widget = selectedWidget;
		    Context context = getContext();
		    if (context == null) return;
		
		    View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_inject_dialog, null);
		    EditText attributesInput = dialogView.findViewById(R.id.et_attribute);
		    Button btnOk = dialogView.findViewById(R.id.btn_ok);
		    Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
		
		    String widgetId = WidgetUtil.getWidgetId(widget);
		    if (widgetId == null) {
			        TheBlockLogicsUtil.showToast(context, "Widget ID not found");
			        return;
			    }
		
		    Map<String, String> existingAttributes = widgetCustomAttributes.getOrDefault(widgetId, new HashMap<>());
		    StringBuilder attributesText = new StringBuilder();
		    for (Map.Entry<String, String> entry : existingAttributes.entrySet()) {
			        attributesText.append(entry.getKey()).append("=").append(entry.getValue()).append("\n");
			    }
		    attributesInput.setText(attributesText.toString());
		
		    AlertDialog dialog = new AlertDialog.Builder(context)
		            .setView(dialogView)
		            .create();
		
		    btnCancel.setOnClickListener(v -> dialog.dismiss());
		
		    btnOk.setOnClickListener(v -> {
			        String input = attributesInput.getText().toString().trim();
			        Map<String, String> newAttributes = new HashMap<>();
			
			        if (!input.isEmpty()) {
				            String[] lines = input.split("\n");
				            for (String line : lines) {
					                line = line.trim();
					                if (!line.isEmpty() && line.contains("=")) {
						                    String[] parts = line.split("=", 2);
						                    String key = parts[0].trim();
						                    String value = parts.length > 1 ? parts[1].trim() : "";
						                    if (!key.isEmpty()) {
							                        newAttributes.put(key, value);
							                    }
						                }
					            }
				        }
			
			        widgetCustomAttributes.put(widgetId, newAttributes);
			        saveStateToUndo();
			        saveCustomAttributes();
			        TheBlockLogicsUtil.showToast(context, "Attributes updated");
			
			        dialog.dismiss();
			    });
		
		    dialog.show();
	}
	
	
	// Save custom attributes using Parcel
	private void saveCustomAttributes() {
		    try {
			        generateKeyIfNotExists(); // Key sirf pehli baar banegi
			        PublicKey publicKey = getPublicKey();
			
			        File file = new File(projectPath, "custom_attributes.parcel");
			        Parcel parcel = Parcel.obtain();
			        WidgetAttributes widgetAttributes = new WidgetAttributes(widgetCustomAttributes);
			        widgetAttributes.writeToParcel(parcel, 0);
			        byte[] bytes = parcel.marshall();
			        parcel.recycle();
			
			        byte[] encrypted = encryptWithRSA(bytes, publicKey);
			        FileUtil.writeFile(file.getAbsolutePath(), Base64.encodeToString(encrypted, Base64.DEFAULT));
			    } catch (Exception e) {
			        TheBlockLogicsUtil.showToast(context, "Error saving attributes: " + e.getMessage());
			        e.printStackTrace();
			    }
	}
	
	// Load custom attributes using Parcel
	private void loadCustomAttributes() {
		    try {
			        generateKeyIfNotExists(); // Ensure key exists
			        PrivateKey privateKey = getPrivateKey();
			
			        File file = new File(projectPath, "custom_attributes.parcel");
			        if (!file.exists()) return;
			
			        String encoded = FileUtil.readFile(file.getAbsolutePath());
			        byte[] encrypted = Base64.decode(encoded, Base64.DEFAULT);
			        byte[] decrypted = decryptWithRSA(encrypted, privateKey);
			
			        Parcel parcel = Parcel.obtain();
			        parcel.unmarshall(decrypted, 0, decrypted.length);
			        parcel.setDataPosition(0);
			        WidgetAttributes widgetAttributes = WidgetAttributes.CREATOR.createFromParcel(parcel);
			        widgetCustomAttributes = widgetAttributes.getAttributes();
			        parcel.recycle();
			    } catch (Exception e) {
			        TheBlockLogicsUtil.showToast(context, "Error loading attributes: " + e.getMessage());
			        e.printStackTrace();
			    }
	}
	
	/**
 * TUDO: Inject custom attributes into the selected widget
 */
	private void injectCustomAttributes(View widget, String key, String value) {
		    if (widget == null) return;
		
		    // Store custom attributes in a tag or custom map
		    Map<String, String> customAttributes = (Map<String, String>) widget.getTag(R.id.custom_attributes_tag);
		    if (customAttributes == null) {
			        customAttributes = new HashMap<>();
			        widget.setTag(R.id.custom_attributes_tag, customAttributes);
			    }
		    customAttributes.put(key, value);
		
		    // Apply known attributes immediately
		    try {
			        switch (key) {
				            case "android:gravity":
				                if (widget instanceof TextView || widget instanceof Button) {
					                    ((TextView) widget).setGravity(parseGravity(value));
					                } else if (widget instanceof WidgetLinear) {
					                    ((WidgetLinear) widget).setGravity(parseGravity(value));
					                }
				                break;
				            case "android:text":
				                if (widget instanceof TextView || widget instanceof Button) {
					                    ((TextView) widget).setText(value);
					                }
				                break;
				            // Add support for custom attributes
				            case "custom:tooltip": // Example custom attribute
				                if (widget instanceof View) {
					                    widget.setTooltipText(value); // Requires API 26+
					                }
				                break;
				            case "custom:tag": // Example custom attribute
				                widget.setTag(value); // Store as a general tag
				                break;
				            default:
				                // Unknown attributes are stored but not applied immediately
				                break;
				        }
			        widget.requestLayout();
			    } catch (Exception e) {
			        showMessage("Error applying attribute: " + e.getMessage());
			    }
	}
	
	/**
 * TUDO: Helper to parse gravity string to int
 *//*
private int parseGravity(String gravity) {
    switch (gravity.toLowerCase()) {
        case "center": return Gravity.CENTER;
        case "left": return Gravity.LEFT;
        case "right": return Gravity.RIGHT;
        case "top": return Gravity.TOP;
        case "bottom": return Gravity.BOTTOM;
        case "start": return Gravity.START;
        case "end": return Gravity.END;
        default: return Gravity.START;
    }
}*/
	/**
 * TUDO: Get all widget IDs from the layout as Pair<Integer, String> for compatibility with showSelectPairPopup
 * @return List of Pair<Index, WidgetID> in @+id/... format
 */
	public static List<Pair<Integer, String>> getAllWidgetsIdAsPairs() {
		    List<Pair<Integer, String>> widgetIds = new ArrayList<>();
		    if (ll == null) return widgetIds;
		
		    for (int i = 0; i < ll.getChildCount(); i++) {
			        View child = ll.getChildAt(i);
			        if (child instanceof ViewGroup) {
				            ViewGroup viewGroup = (ViewGroup) child;
				            for (int j = 0; j < viewGroup.getChildCount(); j++) {
					                View widget = viewGroup.getChildAt(j);
					                String widgetId = WidgetUtil.getWidgetId(widget);
					                if (widgetId != null && !widgetId.isEmpty()) {
						                    widgetIds.add(new Pair<>(i * 100 + j, "@+id/" + widgetId));
						                }
					            }
				        }
			    }
		    return widgetIds;
	}
	
	/**
 * TUDO: Get widget IDs filtered by type as Pair<Integer, String>
 * @param widgetType The type to filter (e.g., "textview", "imageview", "listSpn", null for all)
 * @return List of Pair<Index, WidgetID> in @+id/... format
 */
	public static List<Pair<Integer, String>> getWidgetIdsByType(String widgetType) {
		    List<Pair<Integer, String>> filteredIds = new ArrayList<>();
		    if (ll == null) return filteredIds;
		
		    for (int i = 0; i < ll.getChildCount(); i++) {
			        View child = ll.getChildAt(i);
			        if (child instanceof ViewGroup) {
				            ViewGroup viewGroup = (ViewGroup) child;
				            for (int j = 0; j < viewGroup.getChildCount(); j++) {
					                View widget = viewGroup.getChildAt(j);
					                String widgetId = WidgetUtil.getWidgetId(widget);
					                if (widgetId != null && !widgetId.isEmpty() && isWidgetTypeMatch(widget, widgetType)) {
						                    filteredIds.add(new Pair<>(i * 100 + j, "@+id/" + widgetId));
						                }
					            }
				        }
			    }
		    return filteredIds;
	}
	
	/**
 * TUDO: Check if a widget matches the requested type
 * @param widget The widget to check
 * @param requestedType The type requested (e.g., "textview", "imageview", "listSpn")
 * @return true if the widget matches the type
 */
	private static boolean isWidgetTypeMatch(View widget, String requestedType) {
		    if (requestedType == null || requestedType.equalsIgnoreCase("view")) {
			        return true; // All widgets
			    }
		    switch (requestedType.toLowerCase()) {
			        case "textview":
			            return widget instanceof WidgetTextView || widget instanceof WidgetButton;
			        case "imageview":
			            return widget instanceof WidgetImageView;
			        case "checkbox":
			            return widget instanceof CheckBox; // Assuming you add WidgetCheckBox later
			        case "listview":
			            return widget instanceof WidgetListView;
			        case "spinner":
			            return widget instanceof Spinner; // Assuming you add WidgetSpinner later
			        case "listspn":
			            return widget instanceof WidgetListView || widget instanceof Spinner;
			        case "webview":
			            return widget instanceof WidgetWebView;
			        case "linear":
			            return widget instanceof WidgetLinear;
			        default:
			            return false;
			    }
	}
	/**
* Collects widget IDs from MainActivity's ll LinearLayout, optionally filtered by widget type.
* @param widgetType The type of widget to filter (e.g., "TextView", "ImageView", "ListView", etc.).
*                   Use null or empty string for all widgets.
* @return ArrayList of widget IDs.
*/
	public static ArrayList<String> getWidgetIds(String widgetType) {
			ArrayList<String> widgetIds = new ArrayList<>();
			
			if (ll == null) {
					return widgetIds; // Return empty list if ll is not initialized
			}
			
			for (int i = 0; i < ll.getChildCount(); i++) {
					View child = ((ViewGroup) ll.getChildAt(i)).getChildAt(0);
					String widgetId = WidgetUtil.getWidgetId(child);
					
					if (!widgetId.isEmpty()) {
							String childType = child.getClass().getSimpleName();
							// Map custom widget names to standard Android widget names
							switch (childType) {
									case "WidgetTextView":
									childType = "TextView";
									break;
									case "WidgetButton":
									childType = "Button";
									break;
									case "WidgetImageView":
									childType = "ImageView";
									break;
									case "WidgetWebView":
									childType = "WebView";
									break;
									case "WidgetLinear":
									childType = "LinearLayout";
									break;
									case "WidgetListView":
									childType = "ListView";
									break;
									// Add other widget types as needed
							}
							
							// Add widget ID if no filter or if it matches the specified type
							if (widgetType == null || widgetType.isEmpty() || childType.equalsIgnoreCase(widgetType)) {
									widgetIds.add(widgetId);
							}
					}
			}
			
			return widgetIds;
	}
	public static ViewBean createViewBeanFromLayout() {
		    viewBean.setLayoutName(DesignActivity.currentActivityBean.getLayoutName());
		    viewBean.setActivityName(DesignActivity.currentActivityBean.getActivityName());
		
		    for (int i = 0; i < ll.getChildCount(); i++) {
			        View child = ((ViewGroup) ll.getChildAt(i)).getChildAt(0);
			        String widgetId = WidgetUtil.getWidgetId(child);
			        String widgetType = child.getClass().getSimpleName();
			        String widgetName = widgetType;
			
			        // Map custom widget types to standard Android types
			        switch (widgetType) {
				            case "WidgetTextView":
				                widgetType = "TextView";
				                break;
				            case "WidgetButton":
				                widgetType = "Button";
				                break;
				            case "WidgetImageView":
				                widgetType = "ImageView";
				                break;
				            case "WidgetWebView":
				                widgetType = "WebView";
				                break;
				            case "WidgetListView":
				                widgetType = "ListView";
				                break;
				            case "WidgetLinear":
				                widgetType = "LinearLayout";
				                break;
				        }
			
			        if (!widgetId.isEmpty()) {
				            viewBean.addWidget(widgetId, widgetType, widgetName);
				        }
			    }
		
		    return viewBean;
	}
	public static ViewBean loadViewBeanFromJson() {
		    File file = new File(projectPath + "/saved_project.json");
		    if (file.exists()) {
			        try {
				            String jsonData = TheBlockLogicsUtil.decodeBase64(TheBlockLogicsUtil.readFile(file.getAbsolutePath()));
				            JSONObject jsonObject = new JSONObject(jsonData);
				            
				            if (activityName.equals(jsonObject.optString("activity_name"))) {
					                //viewBean = loadViewBeanFromJson();
					                viewBean.setActivityName(jsonObject.optString("activity_name", "MainActivity"));
					                viewBean.setLayoutName(jsonObject.optString("layout_name", "main"));
					            }
				
				            JSONArray widgetsArray = jsonObject.getJSONArray("widgets");
				            for (int i = 0; i < widgetsArray.length(); i++) {
					                JSONObject widgetData = widgetsArray.getJSONObject(i);
					                String widgetName = widgetData.getString("name_s");
					                String widgetId = widgetData.getString("id");
					
					                // Map to standard Android types
					                String widgetType = widgetName;
					                if (widgetName.equals("WidgetTextView")) widgetType = "TextView";
					                else if (widgetName.equals("WidgetButton")) widgetType = "Button";
					                else if (widgetName.equals("WidgetImageView")) widgetType = "ImageView";
					                else if (widgetName.equals("WidgetWebView")) widgetType = "WebView";
					                else if (widgetName.equals("WidgetListView")) widgetType = "ListView";
					                else if (widgetName.equals("WidgetLinear")) widgetType = "LinearLayout";
					
					                viewBean.addWidget(widgetId, widgetType, widgetName);
					            }
				        } catch (Exception e) {
				          //  Toast.makeText(context, "Load Error: " + e.toString(), Toast.LENGTH_SHORT).show();
				        }
			    }
		    return viewBean;
	}
	private void saveWidgetsToFile() {
		    try {
			        HashMap<String, ArrayList<HashMap<String, Object>>> activityLayouts = new HashMap<>();
			        List<HashMap<String, Object>> widgets = new ArrayList<>();
			
			        // Serialize current layout widgets
			        for (ProjectActivityBean.ViewBean viewBean : DesignActivity.currentLayoutWidgets) {
				            HashMap<String, Object> widgetData = new HashMap<>();
				            widgetData.put("id", viewBean.getWidgetId());
				            widgetData.put("name_s", viewBean.getWidgetType());
				            widgetData.put("original_id", viewBean.getWidgetId());
				            widgetData.put("activity_name", DesignActivity.currentActivityBean.getActivityName());
				            widgetData.put("layout_name", layoutName);
				            widgetData.put("pkgName", pkgName);
				            widgetData.put("useAndroidX", useAndroidX);
				            widgetData.put("width", viewBean.getWidth());
				            widgetData.put("height", viewBean.getHeight());
				            widgetData.put("margin_left", viewBean.getMarginLeft());
				            widgetData.put("margin_top", viewBean.getMarginTop());
				            widgetData.put("margin_right", viewBean.getMarginRight());
				            widgetData.put("margin_bottom", viewBean.getMarginBottom());
				            widgetData.put("visibility", viewBean.getVisibility());
				            widgetData.put("alpha", viewBean.getAlpha());
				            widgetData.put("background_color", viewBean.getBackgroundColor());
				            widgetData.put("text", viewBean.getText());
				            widgetData.put("text_size", viewBean.getTextSize() * getResources().getDisplayMetrics().scaledDensity);
				            widgetData.put("text_color", viewBean.getTextColor());
				            widgetData.put("gravity", viewBean.getGravity());
				            widgetData.put("hint", viewBean.getHint());
				            // Add other properties as needed
				            widgets.add(widgetData);
				        }
			
			        activityLayouts.put(DesignActivity.currentActivityBean.getActivityName(), new ArrayList<>(widgets));
			
			        // Save to file
			        Gson gson = new Gson();
			        String jsonData = gson.toJson(activityLayouts);
			        String filePath = FileUtil.getExternalStorageDir().concat("/.blacklogics/data/".concat(sc_id.concat("/layoutdata")));
			        //FileUtil.writeFile(filePath, TheBlockLogicsUtil.encodeToBase64(jsonData));
			    } catch (Exception e) {
			        //TheBlockLogicsUtil.showToast(getContext(), "Error saving widgets: " + e.toString());
			        e.printStackTrace();
			    }
	}
	public void qrwt(String widgetId, String logic) {
		    //TUDO : NOT NEED logic 
		    WidgetClickListenerManager.getInstance().addClickListener(activityName, widgetId, "// AUTOMATICALLY GENERATED BY BLACKLOGICS.");
	}
	public void saveStateToUndo() {
		    if (isProgrammaticChange) return; // Prevent saving during undo/redo
		    try {
			        JSONObject jsonObject = new JSONObject();
			        jsonObject.put("activity_name", activityName);
			        jsonObject.put("layout_name", layoutName);
			        jsonObject.put("package_name", pkgName);
			        jsonObject.put("use_androidx", useAndroidX);
			
			        JSONArray widgetsArray = new JSONArray();
			        for (int i = 0; i < ll.getChildCount(); i++) {
				            View childAt = ((ViewGroup) ll.getChildAt(i)).getChildAt(0);
				            JSONObject widgetObject = createWidgetJsonBase(childAt);
				            widgetsArray.put(widgetObject);
				        }
			        jsonObject.put("widgets", widgetsArray);
			
			        undoStack.push(jsonObject.toString());
			        redoStack.clear(); // Clear redo stack on new action
			    } catch (Exception e) {
			        //TheBlockLogicsUtil.showToast(getContext().getApplicationContext(), "Error saving state: " + e.toString());
			    }
	}
	
	// Helper method to create JSON for a widget
	private JSONObject createWidgetJsonBase(View child) throws JSONException {
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
		
		    return widgetObject;
	}
	
	// Undo method
	public void undo() {
		    if (undoStack.isEmpty()) {
			        //TheBlockLogicsUtil.showToast(getContext().getApplicationContext(), "Nothing to undo");
			        return;
			    }
		
		    isProgrammaticChange = true;
		    try {
			        String currentState = undoStack.pop();
			        redoStack.push(getCurrentState()); // Save current state to redo
			        restoreState(currentState);
			    } catch (Exception e) {
			        //TheBlockLogicsUtil.showToast(getContext().getApplicationContext(), "Undo error: " + e.toString());
			    } finally {
			        isProgrammaticChange = false;
			    }
	}
	
	// Redo method
	public void redo() {
		    if (redoStack.isEmpty()) {
			        //TheBlockLogicsUtil.showToast(getContext().getApplicationContext(), "Nothing to redo");
			        return;
			    }
		
		    isProgrammaticChange = true;
		    try {
			        String redoState = redoStack.pop();
			        undoStack.push(getCurrentState()); // Save current state to undo
			        restoreState(redoState);
			    } catch (Exception e) {
			       // TheBlockLogicsUtil.showToast(getContext().getApplicationContext(), "Redo error: " + e.toString());
			    } finally {
			        isProgrammaticChange = false;
			    }
	}
	
	// Helper to get current state as JSON string
	private String getCurrentState() throws JSONException {
		    JSONObject jsonObject = new JSONObject();
		    jsonObject.put("activity_name", activityName);
		    jsonObject.put("layout_name", layoutName);
		    jsonObject.put("package_name", pkgName);
		    jsonObject.put("use_androidx", useAndroidX);
		
		    JSONArray widgetsArray = new JSONArray();
		    for (int i = 0; i < ll.getChildCount(); i++) {
			        View childAt = ((ViewGroup) ll.getChildAt(i)).getChildAt(0);
			        widgetsArray.put(createWidgetJsonBase(childAt));
			    }
		    jsonObject.put("widgets", widgetsArray);
		    return jsonObject.toString();
	}
	
	// Restore layout state from JSON string
	private void restoreState(String state) throws JSONException {
		    ll.removeAllViews();
		    JSONObject jsonObject = new JSONObject(state);
		
		    activityName = jsonObject.optString("activity_name", "MainActivity");
		    layoutName = jsonObject.optString("layout_name", "main");
		    pkgName = jsonObject.optString("package_name", "com.nexusteam.internal.os.layouteditor");
		    useAndroidX = jsonObject.optBoolean("use_androidx", false);
		
		    JSONArray widgetsArray = jsonObject.getJSONArray("widgets");
		    for (int i = 0; i < widgetsArray.length(); i++) {
			        JSONObject widgetData = widgetsArray.getJSONObject(i);
			        LinearLayout container = new LinearLayout(getContext());
			        container.setOrientation(LinearLayout.VERTICAL);
			        container.setOnDragListener(this);
			        container.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			
			        String widgetName = widgetData.getString("name_s");
			        String widgetId = widgetData.getString("id");
			        int width = widgetData.getInt("width");
			        int height = widgetData.getInt("height");
			
			        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
			        View widget = null;
			
			        if (widgetName.equals(WidgetTextView.class.getSimpleName())) {
				            WidgetTextView widgetTextView = new WidgetTextView(getContext());
				            widgetTextView.setLayoutParams(lp);
				            widgetTextView.setWidgetId(widgetId);
				            widgetTextView.setOnClickListener(new WidgetClickListener());
				            widgetTextView.setOnLongClickListener(this);
				
				            if (widgetData.has("text")) {
					                widgetTextView.getTextView().setText(widgetData.getString("text"));
					            }
				            if (widgetData.has("text_size")) {
					                widgetTextView.getTextView().setTextSize(TypedValue.COMPLEX_UNIT_PX, 
					                    (float)widgetData.getDouble("text_size"));
					            }
				            if (widgetData.has("text_color")) {
					                widgetTextView.getTextView().setTextColor(widgetData.getInt("text_color"));
					            }
				            if (widgetData.has("gravity")) {
					                widgetTextView.getTextView().setGravity(widgetData.getInt("gravity"));
					            }
				            widget = widgetTextView;
				        } else if (widgetName.equals(WidgetButton.class.getSimpleName())) {
				            WidgetButton widgetButton = new WidgetButton(getContext());
				            widgetButton.setLayoutParams(lp);
				            widgetButton.setWidgetId(widgetId);
				            widgetButton.setOnClickListener(new WidgetClickListener());
				            widgetButton.setOnLongClickListener(this);
				
				            if (widgetData.has("text")) {
					                widgetButton.getTextView().setText(widgetData.getString("text"));
					            }
				            if (widgetData.has("text_size")) {
					                widgetButton.getTextView().setTextSize(TypedValue.COMPLEX_UNIT_PX, 
					                    (float)widgetData.getDouble("text_size"));
					            }
				            if (widgetData.has("text_color")) {
					                widgetButton.getTextView().setTextColor(widgetData.getInt("text_color"));
					            }
				            if (widgetData.has("gravity")) {
					                widgetButton.getTextView().setGravity(widgetData.getInt("gravity"));
					            }
				            widget = widgetButton;
				        } else if (widgetName.equals(WidgetImageView.class.getSimpleName())) {
				            WidgetImageView widgetImageView = new WidgetImageView(getContext());
				            widgetImageView.setLayoutParams(lp);
				            widgetImageView.setWidgetId(widgetId);
				            widgetImageView.setOnClickListener(new WidgetClickListener());
				            widgetImageView.setOnLongClickListener(this);
				
				            if (widgetData.has("image_path")) {
					                String imagePath = widgetData.getString("image_path");
					                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
					                if (bitmap != null) {
						                    widgetImageView.setImageBitmap(bitmap);
						                }
					            }
				            widget = widgetImageView;
				        } else if (widgetName.equals(WidgetWebView.class.getSimpleName())) {
				            WidgetWebView widgetWebView = new WidgetWebView(getContext());
				            widgetWebView.setLayoutParams(lp);
				            widgetWebView.setWidgetId(widgetId);
				            widgetWebView.setOnClickListener(new WidgetClickListener());
				            widgetWebView.setOnLongClickListener(this);
				            widget = widgetWebView;
				        }
			
			        if (widget != null) {
				            if (widgetData.has("visibility")) {
					                widget.setVisibility(widgetData.getInt("visibility"));
					            }
				            if (widgetData.has("alpha")) {
					                widget.setAlpha((float)widgetData.getDouble("alpha"));
					            }
				            if (widgetData.has("rotation")) {
					                widget.setRotation((float)widgetData.getDouble("rotation"));
					            }
				            if (widgetData.has("scaleX")) {
					                widget.setScaleX((float)widgetData.getDouble("scaleX"));
					            }
				            if (widgetData.has("scaleY")) {
					                widget.setScaleY((float)widgetData.getDouble("scaleY"));
					            }
				            if (widgetData.has("translationX")) {
					                widget.setTranslationX((float)widgetData.getDouble("translationX"));
					            }
				            if (widgetData.has("translationY")) {
					                widget.setTranslationY((float)widgetData.getDouble("translationY"));
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
						                    ((ViewGroup.MarginLayoutParams)lp).setMargins(left, top, right, bottom);
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
				            addWidgetInLayoutS(container, ll.getChildCount());
				        }
			    }
	}
	public void showGravityDialog(String title, String message, final View widget) {
		    LayoutInflater inflater = LayoutInflater.from(getContext());
		    final View dialogView = inflater.inflate(R.layout.gravity_dialog, null);
		    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		    builder.setView(dialogView);
		    final AlertDialog alert = builder.create();
		
		    if (alert.getWindow() != null) {
			        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
			        alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
			    }
		
		    // Gravity Options
		    CheckedTextView left = dialogView.findViewById(R.id.left_gravity);
		    CheckedTextView right = dialogView.findViewById(R.id.right_gravity);
		    CheckedTextView center = dialogView.findViewById(R.id.center_gravity);
		    CheckedTextView centerHorizontal = dialogView.findViewById(R.id.center_horizontal_gravity);
		    CheckedTextView top = dialogView.findViewById(R.id.top_gravity);
		    CheckedTextView bottom = dialogView.findViewById(R.id.bottom_gravity);
		    CheckedTextView centerVertical = dialogView.findViewById(R.id.center_vertical_gravity);
		    Button cancel = dialogView.findViewById(R.id.cancel_button);
		    Button select = dialogView.findViewById(R.id.select_button);
		
		    CheckedTextView[] options = {left, right, center, centerHorizontal, top, bottom, centerVertical};
		
		    // Get current gravity
		    int currentGravity = Gravity.START;
		
		    if (widget instanceof TextView) {
			        currentGravity = ((TextView) widget).getGravity();
			    } else if (widget instanceof LinearLayout) {
			        currentGravity = ((LinearLayout) widget).getGravity();
			    } else if (widget instanceof FrameLayout) {
			        currentGravity = ((FrameLayout) widget).getForegroundGravity();
			    }
		
		    Map<Integer, CheckedTextView> gravityMap = new HashMap<>();
		    gravityMap.put(Gravity.LEFT, left);
		    gravityMap.put(Gravity.RIGHT, right);
		    gravityMap.put(Gravity.CENTER, center);
		    gravityMap.put(Gravity.CENTER_HORIZONTAL, centerHorizontal);
		    gravityMap.put(Gravity.TOP, top);
		    gravityMap.put(Gravity.BOTTOM, bottom);
		    gravityMap.put(Gravity.CENTER_VERTICAL, centerVertical);
		
		    for (Map.Entry<Integer, CheckedTextView> entry : gravityMap.entrySet()) {
			        entry.getValue().setChecked((currentGravity & entry.getKey()) == entry.getKey());
			    }
		
		    for (CheckedTextView option : options) {
			        option.setOnClickListener(v -> {
				            CheckedTextView clicked = (CheckedTextView) v;
				            clicked.setChecked(!clicked.isChecked());
				
				            // Horizontal
				            if (clicked == left && clicked.isChecked()) {
					                right.setChecked(false);
					                centerHorizontal.setChecked(false);
					            } else if (clicked == right && clicked.isChecked()) {
					                left.setChecked(false);
					                centerHorizontal.setChecked(false);
					            } else if (clicked == centerHorizontal && clicked.isChecked()) {
					                left.setChecked(false);
					                right.setChecked(false);
					            }
				
				            // Vertical
				            if (clicked == top && clicked.isChecked()) {
					                bottom.setChecked(false);
					                centerVertical.setChecked(false);
					            } else if (clicked == bottom && clicked.isChecked()) {
					                top.setChecked(false);
					                centerVertical.setChecked(false);
					            } else if (clicked == centerVertical && clicked.isChecked()) {
					                top.setChecked(false);
					                bottom.setChecked(false);
					            }
				
				            // Center disables all others
				            if (clicked == center && clicked.isChecked()) {
					                for (CheckedTextView other : options) {
						                    if (other != center) other.setChecked(false);
						                }
					            } else if (clicked != center && clicked.isChecked()) {
					                center.setChecked(false);
					            }
				        });
			    }
		
		    alert.getWindow().setGravity(Gravity.CENTER);
		    alert.show();
		
		    cancel.setOnClickListener(v -> alert.dismiss());
		
		    select.setOnClickListener(v -> {
			        if (widget == null) {
				            Toast.makeText(getContext(), "No widget selected", Toast.LENGTH_SHORT).show();
				            alert.dismiss();
				            return;
				        }
			
			        int selectedGravity = 0;
			        for (Map.Entry<Integer, CheckedTextView> entry : gravityMap.entrySet()) {
				            if (entry.getValue().isChecked()) {
					                selectedGravity |= entry.getKey();
					            }
				        }
			
			        if (selectedGravity == 0) {
				            selectedGravity = Gravity.START;
				        }
			
			        // Apply gravity
			        if (widget instanceof TextView) {
				            ((TextView) widget).setGravity(selectedGravity);
				        } else if (widget instanceof LinearLayout) {
				            ((LinearLayout) widget).setGravity(selectedGravity);
				        } else if (widget instanceof FrameLayout) {
				            ((FrameLayout) widget).setForegroundGravity(selectedGravity);
				        } else {
				            Toast.makeText(getContext(), "This widget does not support gravity", Toast.LENGTH_SHORT).show();
				            alert.dismiss();
				            return;
				        }
			
			        widget.requestLayout();
			        ((View) widget.getParent()).invalidate();
			        saveLayout();
			        saveStateToUndo();
			        alert.dismiss();
			    });
	}
	
	public void showLayoutGravityDialog(String title, String message, final View v) {
		    LayoutInflater inflater = LayoutInflater.from(getContext());
		    final View dialogView = inflater.inflate(R.layout.gravity_dialog, null);
		    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		    builder.setView(dialogView);
		    final AlertDialog alert = builder.create();
		
		    if (alert.getWindow() != null) {
			        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
			        alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
			    }
		
		    // Gravity options
		    CheckedTextView left = dialogView.findViewById(R.id.left_gravity);
		    CheckedTextView right = dialogView.findViewById(R.id.right_gravity);
		    CheckedTextView center = dialogView.findViewById(R.id.center_gravity);
		    CheckedTextView centerH = dialogView.findViewById(R.id.center_horizontal_gravity);
		    CheckedTextView top = dialogView.findViewById(R.id.top_gravity);
		    CheckedTextView bottom = dialogView.findViewById(R.id.bottom_gravity);
		    CheckedTextView centerV = dialogView.findViewById(R.id.center_vertical_gravity);
		    Button cancel = dialogView.findViewById(R.id.cancel_button);
		    Button select = dialogView.findViewById(R.id.select_button);
		
		    CheckedTextView[] options = {left, right, center, centerH, top, bottom, centerV};
		
		    // Current layout_gravity
		    int currentGravity = 0;
		    if (selectedWidget != null && selectedWidget.getLayoutParams() instanceof LinearLayout.LayoutParams) {
			        currentGravity = ((LinearLayout.LayoutParams) selectedWidget.getLayoutParams()).gravity;
			    }
		
		    Map<Integer, CheckedTextView> gravityMap = new HashMap<>();
		    gravityMap.put(Gravity.LEFT, left);
		    gravityMap.put(Gravity.RIGHT, right);
		    gravityMap.put(Gravity.CENTER, center);
		    gravityMap.put(Gravity.CENTER_HORIZONTAL, centerH);
		    gravityMap.put(Gravity.TOP, top);
		    gravityMap.put(Gravity.BOTTOM, bottom);
		    gravityMap.put(Gravity.CENTER_VERTICAL, centerV);
		
		    for (Map.Entry<Integer, CheckedTextView> entry : gravityMap.entrySet()) {
			        entry.getValue().setChecked((currentGravity & entry.getKey()) == entry.getKey());
			    }
		
		    for (CheckedTextView option : options) {
			        option.setOnClickListener(v0 -> {
				            CheckedTextView clicked = (CheckedTextView) v;
				            clicked.setChecked(!clicked.isChecked());
				
				            // Horizontal conflict
				            if (clicked == left && clicked.isChecked()) {
					                right.setChecked(false);
					                centerH.setChecked(false);
					            } else if (clicked == right && clicked.isChecked()) {
					                left.setChecked(false);
					                centerH.setChecked(false);
					            } else if (clicked == centerH && clicked.isChecked()) {
					                left.setChecked(false);
					                right.setChecked(false);
					            }
				
				            // Vertical conflict
				            if (clicked == top && clicked.isChecked()) {
					                bottom.setChecked(false);
					                centerV.setChecked(false);
					            } else if (clicked == bottom && clicked.isChecked()) {
					                top.setChecked(false);
					                centerV.setChecked(false);
					            } else if (clicked == centerV && clicked.isChecked()) {
					                top.setChecked(false);
					                bottom.setChecked(false);
					            }
				
				            // Full center disables others
				            if (clicked == center && clicked.isChecked()) {
					                for (CheckedTextView other : options) {
						                    if (other != center) other.setChecked(false);
						                }
					            } else if (clicked != center && clicked.isChecked()) {
					                center.setChecked(false);
					            }
				        });
			    }
		
		    alert.getWindow().setGravity(Gravity.CENTER);
		    alert.show();
		
		    cancel.setOnClickListener(v1 -> alert.dismiss());
		
		    select.setOnClickListener(v2 -> {
			        if (selectedWidget == null) {
				            Toast.makeText(getContext(), "No widget selected", Toast.LENGTH_SHORT).show();
				            alert.dismiss();
				            return;
				        }
			
			        // Calculate layout_gravity
			        int selectedGravity = 0;
			        for (Map.Entry<Integer, CheckedTextView> entry : gravityMap.entrySet()) {
				            if (entry.getValue().isChecked()) {
					                selectedGravity |= entry.getKey();
					            }
				        }
			
			        if (selectedGravity == 0) selectedGravity = Gravity.START;
			
			        // Apply to widget
			        View widgetView;
			        try {
				            widgetView = selectedWidget;
				        } catch (ClassCastException e) {
				            Toast.makeText(getContext(), "Invalid widget type", Toast.LENGTH_SHORT).show();
				            alert.dismiss();
				            return;
				        }
			
			        if (widgetView == null) {
				            Toast.makeText(getContext(), "Widget view is null", Toast.LENGTH_SHORT).show();
				            alert.dismiss();
				            return;
				        }
			
			        ViewGroup.LayoutParams lp = widgetView.getLayoutParams();
			        if (lp instanceof LinearLayout.LayoutParams) {
				            ((LinearLayout.LayoutParams) lp).gravity = selectedGravity;
				        } else {
				            LinearLayout.LayoutParams newParams = new LinearLayout.LayoutParams(lp.width, lp.height);
				            newParams.gravity = selectedGravity;
				            widgetView.setLayoutParams(newParams);
				        }
			
			        widgetView.requestLayout();
			        if (widgetView.getParent() != null) {
				            ((View) widgetView.getParent()).invalidate();
				        }
			
			        saveLayout();        // Save layout method from your system
			        saveStateToUndo();   // Undo support if available
			        alert.dismiss();
			    });
	}
	
	public void showCheckedStateDialog(View v) {
		    if (!(selectedWidget instanceof WidgetCheckBox)) {
			        TheBlockLogicsUtil.showToast(getContext().getApplicationContext(), "Select a CheckBox widget first");
			        return;
			    }
		
		    LayoutInflater inflater = LayoutInflater.from(getContext());
		    final View dialogView = inflater.inflate(R.layout.dialog_check_box, null);
		    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		    builder.setView(dialogView);
		    final AlertDialog alert = builder.create();
		
		    if (alert.getWindow() != null) {
			        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
			        alert.getWindow().setGravity(Gravity.CENTER);
			    }
		
		    // Initialize dialog views
		    RadioButton rbChecked = dialogView.findViewById(R.id.rb_checked);
		    RadioButton rbUnchecked = dialogView.findViewById(R.id.rb_unchecked);
		    Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
		    Button btnOk = dialogView.findViewById(R.id.btn_ok);
		
		    // Get current checked state from the real CheckBox
		    WidgetCheckBox checkBox = (WidgetCheckBox) selectedWidget;
		    boolean isChecked = checkBox.isChecked();
		    rbChecked.setChecked(isChecked);
		    rbUnchecked.setChecked(!isChecked);
		
		    alert.show();
		
		    btnCancel.setOnClickListener(view1 -> alert.dismiss());
		
		    btnOk.setOnClickListener(view12 -> {
			        boolean newChecked = rbChecked.isChecked();
			
			        // Optionally update your ViewBean model
			
			        checkBox.setCheckedDisplay(newChecked); // apply state
			        saveStateToUndo();
			        checkBox.requestLayout();
			        alert.dismiss();
			    });
	}
	
	public void enableFAB(boolean enable) {
		    if (fab == null) {
			        fab = new LinearLayout(getContext());
			        fab.setId(R.id._fab);
			        fab.setBackgroundResource(R.drawable.circle_background);
			        fab.setGravity(Gravity.CENTER);
			        fab.setOrientation(LinearLayout.VERTICAL);
			
			        // Set size manually
			        int size = getDp(35);
			        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
			        fab.setLayoutParams(params);
			
			        ImageView plusIcon = new ImageView(getContext());
			        plusIcon.setImageResource(R.drawable.ic_plus);
			        plusIcon.setLayoutParams(new LinearLayout.LayoutParams(
			            getDp(24),
			           getDp(24)
			        ));
			        plusIcon.setColorFilter(ContextCompat.getColor(getContext(), R.color.white));
			        fab.addView(plusIcon);
			
			        // Add to root layout
			        ll.addView(fab);
			
			        // Wait until layout is ready, then move it
			        ll.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
				            int parentWidth = ll.getWidth();
				            int parentHeight = ll.getHeight();
				            int margin = getDp(16);
				
				            fab.setX(parentWidth - size - margin);
				            fab.setY(parentHeight - size - margin);
				        });
			    }
		
		    fab.setVisibility(enable ? View.VISIBLE : View.GONE);
	}
	
	public int getDp(float dp) {
		    Context context = getContext(); // use getActivity() if you're in a Fragment
		    if (context == null) return (int) dp; // fallback or skip
		    return (int) TypedValue.applyDimension(
		        TypedValue.COMPLEX_UNIT_DIP,
		        dp,
		        context.getResources().getDisplayMetrics()
		    );
	}
	private int getDip(int dp) {
		    float scale = getResources().getDisplayMetrics().density;
		    return (int) (dp * scale + 0.5f);
	}
	
	/*
private String inputTypeToString(int inputType) {
    switch (inputType) {
        case InputType.TYPE_CLASS_TEXT:
            return "text";
        case InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD:
            return "textPassword";
        case InputType.TYPE_CLASS_NUMBER:
            return "number";
        case InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD:
            return "numberPassword";
        case InputType.TYPE_CLASS_PHONE:
            return "phone";
        case InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS:
            return "textEmailAddress";
        default:
            return "text";
    }
}
*/
	public void showSwitchCheckedDialog(View v) {
		    if (!(selectedWidget instanceof WidgetSwitch)) {
			        TheBlockLogicsUtil.showToast(getContext().getApplicationContext(), "Select a Switch widget first");
			        return;
			    }
		
		    LayoutInflater inflater = LayoutInflater.from(getContext());
		    final View dialogView = inflater.inflate(R.layout.dialog_check_box, null);
		    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		    builder.setView(dialogView);
		    final AlertDialog alert = builder.create();
		
		    if (alert.getWindow() != null) {
			        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
			        alert.getWindow().setGravity(Gravity.CENTER);
			    }
		
		    // Initialize dialog views
		    ImageView icon = dialogView.findViewById(R.id.img_icon);
		    RadioButton rbChecked = dialogView.findViewById(R.id.rb_checked);
		    RadioButton rbUnchecked = dialogView.findViewById(R.id.rb_unchecked);
		    Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
		    Button btnOk = dialogView.findViewById(R.id.btn_ok);
		
		    icon.setImageResource(R.drawable.widget_switch);
		
		    // Get current checked state from the widget directly
		    WidgetSwitch switchWidget = (WidgetSwitch) selectedWidget;
		    boolean isChecked = switchWidget.isChecked();
		    rbChecked.setChecked(isChecked);
		    rbUnchecked.setChecked(!isChecked);
		
		    alert.show();
		
		    btnCancel.setOnClickListener(view -> alert.dismiss());
		
		    btnOk.setOnClickListener(view -> {
			        boolean newCheckedState = rbChecked.isChecked();
			
			        // Update widget checked state
			        switchWidget.setCheckedDisplay(newCheckedState);
			
			        saveStateToUndo();
			        switchWidget.requestLayout();
			        alert.dismiss();
			    });
	}
	
	public void u() {
			boolean isToolbarEnable = DesignActivity.isToolbarEnabled(activityName);
			if (isToolbarEnable) {
					phone_action_bar.setVisibility(View.VISIBLE);
			} else {
					phone_action_bar.setVisibility(View.GONE);
			}
			boolean isFabEnable = DesignActivity.isEnableFab(activityName);
			if (isFabEnable) {
					enableFAB(true);
			} else {
					enableFAB(false);
			}
	}
	
	// TODO: Implemented translation Y at the top of the dialog
	public void showWidgetTranslationYDialog(String titulo, String texto, final View v1) {
			LayoutInflater inflater = LayoutInflater.from(getContext());  
			final View v = inflater.inflate(R.layout.custom_dialog, null);  
			AlertDialog.Builder builder = new AlertDialog.Builder(getContext()); 
			builder.setView(v); 
			final AlertDialog alert = builder.create();
			
			if (alert.getWindow() != null) {
					// Set dialog to appear at top with translation X
					/* WindowManager.LayoutParams params = alert.getWindow().getAttributes();
		params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
		params.x = 0;  // X translation (horizontal)
		params.y = 100; // Y position from top (in pixels)
		params.width = WindowManager.LayoutParams.MATCH_PARENT;
		alert.getWindow().setAttributes(params);*/
					
					alert.getWindow().setGravity(Gravity.CENTER);
					
					alert.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
					alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
			}
			
			ImageView icon = v.findViewById(R.id.img_icon);
			TextView titulo_dlg = v.findViewById(R.id.tv_title);
			TextView mensagem_dlg = v.findViewById(R.id.tv_message);
			LinearLayout tamanho = v.findViewById(R.id.widget_temanho);
			final EditText widget_translation_x = v.findViewById(R.id.et_widget); // Changed from ID to translation X
			final EditText et_tamanho = v.findViewById(R.id.ed_input);
			Button btnCancel = v.findViewById(R.id.btn_cancel); 
			Button btnSave = v.findViewById(R.id.btn_ok);
			
			// Focus setup
			widget_translation_x.setFocusable(true);
			widget_translation_x.setFocusableInTouchMode(true);
			widget_translation_x.setClickable(true);
			widget_translation_x.setHint("Enter Y translation");
			
			et_tamanho.setFocusable(true);
			et_tamanho.setFocusableInTouchMode(true);
			et_tamanho.setClickable(true);
			
			// Dialog setup
			icon.setImageResource(R.drawable.enlarge_48);
			titulo_dlg.setText(titulo);
			mensagem_dlg.setVisibility(View.GONE);
			tamanho.setVisibility(View.GONE);
			alert.show(); 
			
			// Set current translation X value
			float currentTranslationX = selectedWidget.getTranslationY();
			widget_translation_x.setText(String.valueOf(currentTranslationX));
			
			btnCancel.setOnClickListener(qv -> alert.cancel());
			
			btnSave.setOnClickListener(vqe -> {
					try {
							float newTranslationX = Float.parseFloat(widget_translation_x.getText().toString());
							float currentTranslationY = selectedWidget.getTranslationX(); // Save Y translation
							selectedWidget.setTranslationY(newTranslationX);
							selectedWidget.setTranslationX(currentTranslationY); // Restore Y translation
							selectedWidget.requestLayout();
							saveStateToUndo(); 
					} catch (NumberFormatException e) {
							Toast.makeText(getContext(), "Invalid translation value", Toast.LENGTH_SHORT).show();
					}
					alert.cancel();
			});
			
	}
	
	private void a() {
		    try {
			        ProjectActivityBean currentActivity = DesignActivity.currentActivityBean;
			        if (currentActivity == null) {
				            showToast("No activity selected");
				            return;
				        }
			
			        // Update currentLayoutWidgets
			        List<ProjectActivityBean.ViewBean> widgets = new ArrayList<>();
			        for (int i = 0; i < ll.getChildCount(); i++) {
				            View widget = ll.getChildAt(i);
				            if (widget instanceof ViewGroup) {
					                View child = ((ViewGroup) widget).getChildAt(0);
					                ProjectActivityBean.ViewBean viewBean = DesignActivity.createViewBean(child);
					                if (viewBean != null) {
						                    widgets.add(viewBean);
						                }
					            }
				        }
			        currentActivity.setWidgets(widgets);
			        DesignActivity.allWidgetsMap.put(currentActivity.getActivityName(), widgets);
			
			        // Save to JSON
			        Gson gson = new Gson();
			        String jsonData = gson.toJson(DesignActivity.allWidgetsMap);
			        File file = new File(projectPath, "layout.json");
			        try (FileOutputStream fos = new FileOutputStream(file)) {
				            fos.write(jsonData.getBytes());
				        }
			    } catch (Exception e) {
			        showToast("Error saving: " + e.getMessage());
			        e.printStackTrace();
			    }
	}
	
	private void b() {
		    try {
			        ProjectActivityBean currentActivity = DesignActivity.currentActivityBean;
			        if (currentActivity == null) {
				            showToast("No activity selected");
				            return;
				        }
			
			        // Update currentLayoutWidgets
			        List<ProjectActivityBean.ViewBean> widgets = new ArrayList<>();
			        for (int i = 0; i < ll.getChildCount(); i++) {
				            View widget = ll.getChildAt(i);
				            if (widget instanceof ViewGroup) {
					                View child = ((ViewGroup) widget).getChildAt(0);
					                ProjectActivityBean.ViewBean viewBean = DesignActivity.createViewBean(child);
					                if (viewBean != null) {
						                    widgets.add(viewBean);
						                }
					            }
				        }
			        currentActivity.setWidgets(widgets);
			        DesignActivity.allWidgetsMap.put(currentActivity.getActivityName(), widgets);
			
			        // Save to JSON
			        Gson gson = new Gson();
			        String jsonData = gson.toJson(DesignActivity.allWidgetsMap);
			        File file = new File(projectPath, "layout.json");
			        try (FileOutputStream fos = new FileOutputStream(file)) {
				            fos.write(jsonData.getBytes());
				        }
			    } catch (Exception e) {
			        showToast("Error saving: " + e.getMessage());
			        e.printStackTrace();
			    }
	}
	
	private void c() {
		    try {
			        ProjectActivityBean currentActivity = DesignActivity.currentActivityBean;
			        if (currentActivity == null) {
				            showToast("No activity selected");
				            return;
				        }
			
			        // Update currentLayoutWidgets
			        List<ProjectActivityBean.ViewBean> widgets = new ArrayList<>();
			        for (int i = 0; i < ll.getChildCount(); i++) {
				            View widget = ll.getChildAt(i);
				            if (widget instanceof ViewGroup) {
					                View child = ((ViewGroup) widget).getChildAt(0);
					                ProjectActivityBean.ViewBean viewBean = DesignActivity.createViewBean(child);
					                if (viewBean != null) {
						                    widgets.add(viewBean);
						                }
					            }
				        }
			        currentActivity.setWidgets(widgets);
			        DesignActivity.allWidgetsMap.put(currentActivity.getActivityName(), widgets);
			
			        // Save to JSON
			        Gson gson = new Gson();
			        String jsonData = gson.toJson(DesignActivity.allWidgetsMap);
			        File file = new File(projectPath, "layout.json");
			        try (FileOutputStream fos = new FileOutputStream(file)) {
				            fos.write(jsonData.getBytes());
				        }
			    } catch (Exception e) {
			        showToast("Error saving: " + e.getMessage());
			        e.printStackTrace();
			    }
	}
	
	private void d() {
		    try {
			        ProjectActivityBean currentActivity = DesignActivity.currentActivityBean;
			        if (currentActivity == null) {
				            showToast("No activity selected");
				            return;
				        }
			
			        // Update currentLayoutWidgets
			        List<ProjectActivityBean.ViewBean> widgets = new ArrayList<>();
			        for (int i = 0; i < ll.getChildCount(); i++) {
				            View widget = ll.getChildAt(i);
				            if (widget instanceof ViewGroup) {
					                View child = ((ViewGroup) widget).getChildAt(0);
					                ProjectActivityBean.ViewBean viewBean = DesignActivity.createViewBean(child);
					                if (viewBean != null) {
						                    widgets.add(viewBean);
						                }
					            }
				        }
			        currentActivity.setWidgets(widgets);
			        DesignActivity.allWidgetsMap.put(currentActivity.getActivityName(), widgets);
			
			        // Save to JSON
			        Gson gson = new Gson();
			        String jsonData = gson.toJson(DesignActivity.allWidgetsMap);
			        File file = new File(projectPath, "layout.json");
			        try (FileOutputStream fos = new FileOutputStream(file)) {
				            fos.write(jsonData.getBytes());
				        }
			    } catch (Exception e) {
			        showToast("Error saving: " + e.getMessage());
			        e.printStackTrace();
			    }
	}
	
	private void e() {
		    try {
			        ProjectActivityBean currentActivity = DesignActivity.currentActivityBean;
			        if (currentActivity == null) {
				            showToast("No activity selected");
				            return;
				        }
			
			        // Update currentLayoutWidgets
			        List<ProjectActivityBean.ViewBean> widgets = new ArrayList<>();
			        for (int i = 0; i < ll.getChildCount(); i++) {
				            View widget = ll.getChildAt(i);
				            if (widget instanceof ViewGroup) {
					                View child = ((ViewGroup) widget).getChildAt(0);
					                ProjectActivityBean.ViewBean viewBean = DesignActivity.createViewBean(child);
					                if (viewBean != null) {
						                    widgets.add(viewBean);
						                }
					            }
				        }
			        currentActivity.setWidgets(widgets);
			        DesignActivity.allWidgetsMap.put(currentActivity.getActivityName(), widgets);
			
			        // Save to JSON
			        Gson gson = new Gson();
			        String jsonData = gson.toJson(DesignActivity.allWidgetsMap);
			        File file = new File(projectPath, "layout.json");
			        try (FileOutputStream fos = new FileOutputStream(file)) {
				            fos.write(jsonData.getBytes());
				        }
			    } catch (Exception e) {
			        showToast("Error saving: " + e.getMessage());
			        e.printStackTrace();
			    }
	}
	
	private void f() {
		    try {
			        ProjectActivityBean currentActivity = DesignActivity.currentActivityBean;
			        if (currentActivity == null) {
				            showToast("No activity selected");
				            return;
				        }
			
			        // Update currentLayoutWidgets
			        List<ProjectActivityBean.ViewBean> widgets = new ArrayList<>();
			        for (int i = 0; i < ll.getChildCount(); i++) {
				            View widget = ll.getChildAt(i);
				            if (widget instanceof ViewGroup) {
					                View child = ((ViewGroup) widget).getChildAt(0);
					                ProjectActivityBean.ViewBean viewBean = DesignActivity.createViewBean(child);
					                if (viewBean != null) {
						                    widgets.add(viewBean);
						                }
					            }
				        }
			        currentActivity.setWidgets(widgets);
			        DesignActivity.allWidgetsMap.put(currentActivity.getActivityName(), widgets);
			
			        // Save to JSON
			        Gson gson = new Gson();
			        String jsonData = gson.toJson(DesignActivity.allWidgetsMap);
			        File file = new File(projectPath, "layout.json");
			        try (FileOutputStream fos = new FileOutputStream(file)) {
				            fos.write(jsonData.getBytes());
				        }
			    } catch (Exception e) {
			        showToast("Error saving: " + e.getMessage());
			        e.printStackTrace();
			    }
	}
	
	private void g() {
		    try {
			        ProjectActivityBean currentActivity = DesignActivity.currentActivityBean;
			        if (currentActivity == null) {
				            showToast("No activity selected");
				            return;
				        }
			
			        // Update currentLayoutWidgets
			        List<ProjectActivityBean.ViewBean> widgets = new ArrayList<>();
			        for (int i = 0; i < ll.getChildCount(); i++) {
				            View widget = ll.getChildAt(i);
				            if (widget instanceof ViewGroup) {
					                View child = ((ViewGroup) widget).getChildAt(0);
					                ProjectActivityBean.ViewBean viewBean = DesignActivity.createViewBean(child);
					                if (viewBean != null) {
						                    widgets.add(viewBean);
						                }
					            }
				        }
			        currentActivity.setWidgets(widgets);
			        DesignActivity.allWidgetsMap.put(currentActivity.getActivityName(), widgets);
			
			        // Save to JSON
			        Gson gson = new Gson();
			        String jsonData = gson.toJson(DesignActivity.allWidgetsMap);
			        File file = new File(projectPath, "layout.json");
			        try (FileOutputStream fos = new FileOutputStream(file)) {
				            fos.write(jsonData.getBytes());
				        }
			    } catch (Exception e) {
			        showToast("Error saving: " + e.getMessage());
			        e.printStackTrace();
			    }
	}
	
	private void h() {
		    try {
			        ProjectActivityBean currentActivity = DesignActivity.currentActivityBean;
			        if (currentActivity == null) {
				            showToast("No activity selected");
				            return;
				        }
			
			        // Update currentLayoutWidgets
			        List<ProjectActivityBean.ViewBean> widgets = new ArrayList<>();
			        for (int i = 0; i < ll.getChildCount(); i++) {
				            View widget = ll.getChildAt(i);
				            if (widget instanceof ViewGroup) {
					                View child = ((ViewGroup) widget).getChildAt(0);
					                ProjectActivityBean.ViewBean viewBean = DesignActivity.createViewBean(child);
					                if (viewBean != null) {
						                    widgets.add(viewBean);
						                }
					            }
				        }
			        currentActivity.setWidgets(widgets);
			        DesignActivity.allWidgetsMap.put(currentActivity.getActivityName(), widgets);
			
			        // Save to JSON
			        Gson gson = new Gson();
			        String jsonData = gson.toJson(DesignActivity.allWidgetsMap);
			        File file = new File(projectPath, "layout.json");
			        try (FileOutputStream fos = new FileOutputStream(file)) {
				            fos.write(jsonData.getBytes());
				        }
			    } catch (Exception e) {
			        showToast("Error saving: " + e.getMessage());
			        e.printStackTrace();
			    }
	}
	
	private void i() {
		    try {
			        ProjectActivityBean currentActivity = DesignActivity.currentActivityBean;
			        if (currentActivity == null) {
				            showToast("No activity selected");
				            return;
				        }
			
			        // Update currentLayoutWidgets
			        List<ProjectActivityBean.ViewBean> widgets = new ArrayList<>();
			        for (int i = 0; i < ll.getChildCount(); i++) {
				            View widget = ll.getChildAt(i);
				            if (widget instanceof ViewGroup) {
					                View child = ((ViewGroup) widget).getChildAt(0);
					                ProjectActivityBean.ViewBean viewBean = DesignActivity.createViewBean(child);
					                if (viewBean != null) {
						                    widgets.add(viewBean);
						                }
					            }
				        }
			        currentActivity.setWidgets(widgets);
			        DesignActivity.allWidgetsMap.put(currentActivity.getActivityName(), widgets);
			
			        // Save to JSON
			        Gson gson = new Gson();
			        String jsonData = gson.toJson(DesignActivity.allWidgetsMap);
			        File file = new File(projectPath, "layout.json");
			        try (FileOutputStream fos = new FileOutputStream(file)) {
				            fos.write(jsonData.getBytes());
				        }
			    } catch (Exception e) {
			        showToast("Error saving: " + e.getMessage());
			        e.printStackTrace();
			    }
	}
	private void j() {
		    try {
			        ProjectActivityBean currentActivity = DesignActivity.currentActivityBean;
			        if (currentActivity == null) {
				            showToast("No activity selected");
				            return;
				        }
			
			        // Update currentLayoutWidgets
			        List<ProjectActivityBean.ViewBean> widgets = new ArrayList<>();
			        for (int i = 0; i < ll.getChildCount(); i++) {
				            View widget = ll.getChildAt(i);
				            if (widget instanceof ViewGroup) {
					                View child = ((ViewGroup) widget).getChildAt(0);
					                ProjectActivityBean.ViewBean viewBean = DesignActivity.createViewBean(child);
					                if (viewBean != null) {
						                    widgets.add(viewBean);
						                }
					            }
				        }
			        currentActivity.setWidgets(widgets);
			        DesignActivity.allWidgetsMap.put(currentActivity.getActivityName(), widgets);
			
			        // Save to JSON
			        Gson gson = new Gson();
			        String jsonData = gson.toJson(DesignActivity.allWidgetsMap);
			        File file = new File(projectPath, "layout.json");
			        try (FileOutputStream fos = new FileOutputStream(file)) {
				            fos.write(jsonData.getBytes());
				        }
			    } catch (Exception e) {
			        showToast("Error saving: " + e.getMessage());
			        e.printStackTrace();
			    }
	}
	
	private void k() {
		    try {
			        ProjectActivityBean currentActivity = DesignActivity.currentActivityBean;
			        if (currentActivity == null) {
				            showToast("No activity selected");
				            return;
				        }
			
			        // Update currentLayoutWidgets
			        List<ProjectActivityBean.ViewBean> widgets = new ArrayList<>();
			        for (int i = 0; i < ll.getChildCount(); i++) {
				            View widget = ll.getChildAt(i);
				            if (widget instanceof ViewGroup) {
					                View child = ((ViewGroup) widget).getChildAt(0);
					                ProjectActivityBean.ViewBean viewBean = DesignActivity.createViewBean(child);
					                if (viewBean != null) {
						                    widgets.add(viewBean);
						                }
					            }
				        }
			        currentActivity.setWidgets(widgets);
			        DesignActivity.allWidgetsMap.put(currentActivity.getActivityName(), widgets);
			
			        // Save to JSON
			        Gson gson = new Gson();
			        String jsonData = gson.toJson(DesignActivity.allWidgetsMap);
			        File file = new File(projectPath, "layout.json");
			        try (FileOutputStream fos = new FileOutputStream(file)) {
				            fos.write(jsonData.getBytes());
				        }
			    } catch (Exception e) {
			        showToast("Error saving: " + e.getMessage());
			        e.printStackTrace();
			    }
	}
	
	private void l() {
		    try {
			        ProjectActivityBean currentActivity = DesignActivity.currentActivityBean;
			        if (currentActivity == null) {
				            showToast("No activity selected");
				            return;
				        }
			
			        // Update currentLayoutWidgets
			        List<ProjectActivityBean.ViewBean> widgets = new ArrayList<>();
			        for (int i = 0; i < ll.getChildCount(); i++) {
				            View widget = ll.getChildAt(i);
				            if (widget instanceof ViewGroup) {
					                View child = ((ViewGroup) widget).getChildAt(0);
					                ProjectActivityBean.ViewBean viewBean = DesignActivity.createViewBean(child);
					                if (viewBean != null) {
						                    widgets.add(viewBean);
						                }
					            }
				        }
			        currentActivity.setWidgets(widgets);
			        DesignActivity.allWidgetsMap.put(currentActivity.getActivityName(), widgets);
			
			        // Save to JSON
			        Gson gson = new Gson();
			        String jsonData = gson.toJson(DesignActivity.allWidgetsMap);
			        File file = new File(projectPath, "layout.json");
			        try (FileOutputStream fos = new FileOutputStream(file)) {
				            fos.write(jsonData.getBytes());
				        }
			    } catch (Exception e) {
			        showToast("Error saving: " + e.getMessage());
			        e.printStackTrace();
			    }
	}
	
	private void m() {
		    try {
			        ProjectActivityBean currentActivity = DesignActivity.currentActivityBean;
			        if (currentActivity == null) {
				            showToast("No activity selected");
				            return;
				        }
			
			        // Update currentLayoutWidgets
			        List<ProjectActivityBean.ViewBean> widgets = new ArrayList<>();
			        for (int i = 0; i < ll.getChildCount(); i++) {
				            View widget = ll.getChildAt(i);
				            if (widget instanceof ViewGroup) {
					                View child = ((ViewGroup) widget).getChildAt(0);
					                ProjectActivityBean.ViewBean viewBean = DesignActivity.createViewBean(child);
					                if (viewBean != null) {
						                    widgets.add(viewBean);
						                }
					            }
				        }
			        currentActivity.setWidgets(widgets);
			        DesignActivity.allWidgetsMap.put(currentActivity.getActivityName(), widgets);
			
			        // Save to JSON
			        Gson gson = new Gson();
			        String jsonData = gson.toJson(DesignActivity.allWidgetsMap);
			        File file = new File(projectPath, "layout.json");
			        try (FileOutputStream fos = new FileOutputStream(file)) {
				            fos.write(jsonData.getBytes());
				        }
			    } catch (Exception e) {
			        showToast("Error saving: " + e.getMessage());
			        e.printStackTrace();
			    }
	}
	
	private void n() {
		    try {
			        ProjectActivityBean currentActivity = DesignActivity.currentActivityBean;
			        if (currentActivity == null) {
				            showToast("No activity selected");
				            return;
				        }
			
			        Gson gson = new Gson();
			        String jsonData = gson.toJson(DesignActivity.allWidgetsMap);
			        File file = new File(projectPath, "layout.json");
			        try (FileOutputStream fos = new FileOutputStream(file)) {
				            fos.write(jsonData.getBytes());
				        }
			    } catch (Exception e) {
			        showToast("Error saving layout: " + e.getMessage());
			        e.printStackTrace();
			    }
	}
	private boolean isValidId(String id) {
		    return id.matches("^[a-zA-Z][a-zA-Z0-9_]*$");
	}
	private boolean isDuplicateId(String newId, View selectedWidget) {
		    // 1) Get the currently-selected widget’s ID
		    String selectedId = WidgetUtil.getWidgetId(selectedWidget);
		
		    // 2) Loop over the beans, not real Views
		    for (com.besome.blacklogics.beans.ProjectActivityBean.ViewBean bean : DesignActivity.currentLayoutWidgets) {
			        // Skip the bean that corresponds to the selectedView
			        if (bean.getWidgetId().equals(selectedId)) {
				            continue;
				        }
			        // If any other bean has the same ID as newId → duplicate
			        if (newId.equals(bean.getWidgetId())) {
				            return true;
				        }
			    }
		    return false;
	}
	private boolean isValidImageFile(String path) {
		    if (path == null || path.isEmpty()) {
			        return false;
			    }
		    File file = new File(path);
		    return file.exists() && file.isFile() && file.canRead() &&
		           (path.toLowerCase().endsWith(".jpg") || path.toLowerCase().endsWith(".jpeg") ||
		            path.toLowerCase().endsWith(".png") || path.toLowerCase().endsWith(".gif"));
	}
	private int getCurrentTextSize(View widget) {
		    try {
			        TextView textView = WidgetUtil.getTextViewOfWidget(widget);
			        if (textView != null) {
				            return (int) (textView.getTextSize() / getResources().getDisplayMetrics().scaledDensity);
				        }
			    } catch (Exception e) {
			        e.printStackTrace();
			    }
		    return 12; // Default text size
	}
	private boolean applyTextSize(View widget, int size) {
		    try {
			        TextView textView = WidgetUtil.getTextViewOfWidget(widget);
			        if (textView != null) {
				            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
				            return true;
				        }
			    } catch (Exception e) {
			        e.printStackTrace();
			    }
		    return false;
	}
	public void updateWidgetInLayout(ProjectActivityBean.ViewBean viewBean) {
		    if (viewBean == null) {
			        showToast("Invalid widget data");
			        return;
			    }
		
		    ProjectActivityBean currentActivity = DesignActivity.currentActivityBean;
		    if (currentActivity == null) {
			        showToast("No activity selected");
			        return;
			    }
		
		    // Update widgetBeans
		    List<ProjectActivityBean.ViewBean> widgetBeans = DesignActivity.allWidgetsMap.get(currentActivity.getActivityName());
		    if (widgetBeans == null) {
			        showToast("No widgets found for this activity");
			        return;
			    }
		
		    boolean updated = false;
		    for (int i = 0; i < widgetBeans.size(); i++) {
			        ProjectActivityBean.ViewBean existingBean = widgetBeans.get(i);
			        if (existingBean.getWidgetId().equals(viewBean.getWidgetId())) {
				            widgetBeans.set(i, viewBean);
				            updated = true;
				            break;
				        }
			    }
		
		    if (!updated) {
			        showToast("Widget not found in layout");
			        return;
			    }
		
		    // Update the actual View in ll (LinearLayout)
		    for (int i = 0; i < ll.getChildCount(); i++) {
			        View widget = ll.getChildAt(i);
			        if (widget instanceof ViewGroup) {
				            View child = ((ViewGroup) widget).getChildAt(0); // Assuming widget is wrapped in a container
				            String widgetId = WidgetUtil.getWidgetId(child);
				            if (widgetId != null && widgetId.equals(viewBean.getWidgetId())) {
					                // Apply properties from ViewBean to View
					                child.setBackgroundColor(viewBean.getBackgroundColor());
					
					                // Apply margins
					                ViewGroup.LayoutParams params = child.getLayoutParams();
					                if (params instanceof ViewGroup.MarginLayoutParams) {
						                    ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) params;
						                    marginParams.setMargins(
						                        viewBean.getMarginLeft(),
						                        viewBean.getMarginTop(),
						                        viewBean.getMarginRight(),
						                        viewBean.getMarginBottom()
						                    );
						                    child.setLayoutParams(marginParams);
						                }
					
					                // Apply padding
					                child.setPadding(
					                    viewBean.getPaddingLeft(),
					                    viewBean.getPaddingTop(),
					                    viewBean.getPaddingRight(),
					                    viewBean.getPaddingBottom()
					                );
					
					                child.requestLayout();
					                if (child.getParent() != null) {
						                    ((View) child.getParent()).requestLayout();
						                    ((View) child.getParent()).invalidate();
						                }
					                break;
					            }
				        }
			    }
		
		    // Update the activity's widget list
		    currentActivity.setWidgets(widgetBeans);
		    DesignActivity.allWidgetsMap.put(currentActivity.getActivityName(), widgetBeans);
		
		    // Save to JSON
		    n();
	}
	private void loadViewBeansFromJson() {
		    try {
			        File file = new File(projectPath, "layout.json");
			        if (!file.exists()) {
				            showToast("No saved layout found");
				            return;
				        }
			
			        // Read JSON
			        String jsonData;
			        try (FileInputStream fis = new FileInputStream(file)) {
				            byte[] buffer = new byte[(int) file.length()];
				            fis.read(buffer);
				            jsonData = new String(buffer);
				        }
			
			        // Parse JSON
			        Gson gson = new Gson();
			        Type type = new TypeToken<Map<String, List<ProjectActivityBean.ViewBean>>>() {}.getType();
			        Map<String, List<ProjectActivityBean.ViewBean>> loadedMap = gson.fromJson(jsonData, type);
			
			        // Get current activity and update widgets
			        ProjectActivityBean currentActivity = DesignActivity.currentActivityBean;
			        if (currentActivity != null) {
				            List<ProjectActivityBean.ViewBean> widgets = loadedMap.get(currentActivity.getActivityName());
				            if (widgets != null) {
					                DesignActivity.allWidgetsMap.put(currentActivity.getActivityName(), widgets);
					                currentActivity.setWidgets(widgets);
					                showToast("Widgets loaded for " + currentActivity.getActivityName());
					            } else {
					                showToast("No saved widgets for this activity");
					            }
				        } else {
				            showToast("No activity selected");
				        }
			
			    } catch (Exception e) {
			        showToast("Error loading data: " + e.getMessage());
			        e.printStackTrace();
			    }
	}
	
	/*private void applyViewBeanProperties(View widget, ProjectActivityBean.ViewBean viewBean) {
    // Apply layout parameters
    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(viewBean.getWidth(), viewBean.getHeight());
    lp.setMargins(viewBean.getMarginLeft(), viewBean.getMarginTop(), viewBean.getMarginRight(), viewBean.getMarginBottom());
    widget.setLayoutParams(lp);

    // Apply padding
    widget.setPadding(viewBean.getPaddingLeft(), viewBean.getPaddingTop(), viewBean.getPaddingRight(), viewBean.getPaddingBottom());

    // Apply background color
    widget.setBackgroundColor(viewBean.getBackgroundColor());

    // Apply visibility, alpha, rotation, scale
    widget.setVisibility(viewBean.getVisibility());
    widget.setAlpha(viewBean.getAlpha());
    widget.setRotation(viewBean.getRotation());
    widget.setScaleX(viewBean.getScaleX());
    widget.setScaleY(viewBean.getScaleY());

    // Apply text-related properties for text-based widgets
    TextView textView = WidgetUtil.getTextViewOfWidget(widget);
    if (textView != null) {
        if (viewBean.getText() != null) {
            textView.setText(viewBean.getText());
        }
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, viewBean.getTextSize());
        textView.setTextColor(viewBean.getTextColor());
        textView.setGravity(viewBean.getGravity());
        if (widget instanceof WidgetEditText) {
            ((WidgetEditText) widget).getEditText().setHint(viewBean.getHint());
            ((WidgetEditText) widget).getEditText().setInputType(viewBean.getInputType());
        }
    }

    // Apply image-specific properties
    if (widget instanceof WidgetImageView && viewBean.getImagePath() != null) {
        Bitmap bitmap = BitmapFactory.decodeFile(viewBean.getImagePath());
        if (bitmap != null) {
            ((WidgetImageView) widget).setImageBitmap(bitmap);
        }
        ((WidgetImageView) widget).setScaleType(ImageView.ScaleType.valueOf(viewBean.getScaleType()));
    }

    // Apply CheckBox/Switch properties
    if (widget instanceof WidgetCheckBox) {
        ((WidgetCheckBox) widget).setCheckedDisplay(viewBean.isChecked());
    } else if (widget instanceof WidgetSwitch) {
        ((WidgetSwitch) widget).setCheckedDisplay(viewBean.isChecked());
    }

    // Apply elevation
    widget.setElevation(viewBean.getElevation());
}*/
	/**
* Shows a dialog to set the progress type (horizontal or circular) of a WidgetProgressBar
*/
	private void showProgressTypeDialog() {
			Context context = getContext();
			LayoutInflater inflater = LayoutInflater.from(context);
			View dialogView = inflater.inflate(R.layout.progress_type_dialog, null);
			
			RadioButton horizontalRadio = dialogView.findViewById(R.id.radio_horizontal);
			RadioButton circularRadio = dialogView.findViewById(R.id.radio_circular);
			horizontalRadio.setChecked(!((WidgetProgressBar) selectedWidget).isIndeterminate());
			circularRadio.setChecked(((WidgetProgressBar) selectedWidget).isIndeterminate());
			
			AlertDialog dialog = new AlertDialog.Builder(context)
			.setTitle("Set Progress Type")
			.setView(dialogView)
			.setPositiveButton("OK", (d, which) -> {
					boolean isCircular = circularRadio.isChecked();
					((WidgetProgressBar) selectedWidget).setIndeterminate(isCircular);
					saveStateToUndo();
					ViewEditorFragmentActivity.ll.invalidate();
			})
			.setNegativeButton("Cancel", null)
			.create();
			dialog.show();
	}
	/**
* Shows a dialog to set the progress of a WidgetProgressBar
*/
	private void showProgressDialog() {
			Context context = getContext();
			LayoutInflater inflater = LayoutInflater.from(context);
			View dialogView = inflater.inflate(R.layout.progress_dialog, null);
			
			EditText progressInput = dialogView.findViewById(R.id.progress_input);
			progressInput.setText(String.valueOf(((WidgetProgressBar) selectedWidget).getProgress()));
			
			AlertDialog dialog = new AlertDialog.Builder(context)
			.setTitle("Set Progress")
			.setView(dialogView)
			.setPositiveButton("OK", (d, which) -> {
					try {
							int progress = Integer.parseInt(progressInput.getText().toString());
							int maxProgress = ((WidgetProgressBar) selectedWidget).getMax();
							if (progress < 0 || progress > maxProgress) {
									TheBlockLogicsUtil.showToast(context, "Progress must be between 0 and " + maxProgress);
									return;
							}
							((WidgetProgressBar) selectedWidget).setProgress(progress);
							saveStateToUndo();
							ViewEditorFragmentActivity.ll.invalidate();
					} catch (NumberFormatException e) {
							TheBlockLogicsUtil.showToast(context, "Invalid number format");
					}
			})
			.setNegativeButton("Cancel", null)
			.create();
			dialog.show();
	}
	
	/**
* Shows a dialog to set the maximum progress of a WidgetProgressBar
*/
	private void showMaxProgressDialog() {
			Context context = getContext();
			LayoutInflater inflater = LayoutInflater.from(context);
			View dialogView = inflater.inflate(R.layout.max_progress_dialog, null);
			
			EditText maxProgressInput = dialogView.findViewById(R.id.max_progress_input);
			maxProgressInput.setText(String.valueOf(((WidgetProgressBar) selectedWidget).getMax()));
			
			AlertDialog dialog = new AlertDialog.Builder(context)
			.setTitle("Set Max Progress")
			.setView(dialogView)
			.setPositiveButton("OK", (d, which) -> {
					try {
							int maxProgress = Integer.parseInt(maxProgressInput.getText().toString());
							if (maxProgress <= 0) {
									TheBlockLogicsUtil.showToast(context, "Max progress must be greater than 0");
									return;
							}
							((WidgetProgressBar) selectedWidget).setMax(maxProgress);
							int currentProgress = ((WidgetProgressBar) selectedWidget).getProgress();
							if (currentProgress > maxProgress) {
									((WidgetProgressBar) selectedWidget).setProgress(maxProgress);
							}
							saveStateToUndo();
							ViewEditorFragmentActivity.ll.invalidate();
					} catch (NumberFormatException e) {
							TheBlockLogicsUtil.showToast(context, "Invalid number format");
					}
			})
			.setNegativeButton("Cancel", null)
			.create();
			dialog.show();
	}
	
	public void saveLayout() {
			try {
					String filePath = new File(projectPath, "layouts.json").getAbsolutePath();
					JSONObject allLayoutsJson = new JSONObject();
					
					if (FileUtil.isExistFile(filePath)) {
							String existingData = FileUtil.readFile(filePath);
							if (!existingData.isEmpty()) {
									allLayoutsJson = new JSONObject(existingData);
							}
					}
					
					String xmlLayout = generateLayoutXml();
					
					JSONObject layoutJson = new JSONObject();
					layoutJson.put("layoutName", layoutName);
					layoutJson.put("activityName", activityName);
					layoutJson.put("xml", xmlLayout);
					layoutJson.put("timestamp", System.currentTimeMillis());
					
					if (DesignActivity.abc != null) {
							String javaCode = DesignActivity.abc.getJavaCode();
							String xmlCode = DesignActivity.abc.getXmlCode();
							DesignActivity.abc.complex.setXmlCode(DesignActivity.abc.currentActivityBean.getLayoutName(), xmlCode);
							DesignActivity.abc.complex.setJavaCode(DesignActivity.abc.currentActivityBean.getActivityName(), javaCode);
					}
					
					allLayoutsJson.put(layoutName, layoutJson);
					FileUtil.writeFile(filePath, allLayoutsJson.toString(2));
					TheBlockLogicsUtil.showToast(context, "Layout saved successfully");
			} catch (Exception e) {
					TheBlockLogicsUtil.showToast(context, "Error saving layout: " + e.getMessage());
					e.printStackTrace();
			}
	}
	
	public void loadLayout() {
			try {
					String filePath = new File(projectPath, "layouts.json").getAbsolutePath();
					File file = new File(filePath);
					
					ll.removeAllViews();
					
					if (!file.exists()) {
							return;
					}
					
					String jsonData = FileUtil.readFile(filePath);
					JSONObject allLayoutsJson = new JSONObject(jsonData);
					
					if (!allLayoutsJson.has(layoutName)) {
							return;
					}
					
					if (DesignActivity.abc != null) {
							String javaCode = DesignActivity.abc.getJavaCode();
							String xmlCode = DesignActivity.abc.getXmlCode();
							DesignActivity.abc.complex.setXmlCode(DesignActivity.abc.currentActivityBean.getLayoutName(), xmlCode);
							DesignActivity.abc.complex.setJavaCode(DesignActivity.abc.currentActivityBean.getActivityName(), javaCode);
					}
					
					JSONObject layoutJson = allLayoutsJson.getJSONObject(layoutName);
					String xmlLayout = layoutJson.getString("xml");
					
					parseXmlLayout(xmlLayout);
					ll.requestLayout();
			} catch (Exception e) {
					TheBlockLogicsUtil.showToast(context, "Error loading layout: " + e.getMessage());
					e.printStackTrace();
			}
	}
	
	private String generateLayoutXml() {
			try {
					DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
					Document doc = docBuilder.newDocument();
					
					Element rootElement = doc.createElement("LinearLayout");
					rootElement.setAttribute("xmlns:android", "http://schemas.android.com/apk/res/android");
					rootElement.setAttribute("android:layout_width", "match_parent");
					rootElement.setAttribute("android:layout_height", "match_parent");
					rootElement.setAttribute("android:orientation", "vertical");
					rootElement.setAttribute("android:background", "#FFFFFF");
					doc.appendChild(rootElement);
					
					for (int i = 0; i < ll.getChildCount(); i++) {
							View child = ll.getChildAt(i);
							Element childElement = createWidgetElement(doc, child);
							if (childElement != null) {
									rootElement.appendChild(childElement);
							}
					}
					
					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
					transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
					StringWriter writer = new StringWriter();
					transformer.transform(new DOMSource(doc), new StreamResult(writer));
					return writer.toString();
			} catch (Exception e) {
					e.printStackTrace();
					return "";
			}
	}
	
	private Element createWidgetElement(Document doc, View widget) {
			try {
					String widgetType = (String) widget.getTag(TAG_CUSTOM_CLASS_NAME);
					if (widgetType == null) {
							widgetType = WidgetUtil.getWidgetType(widget);
					}
					if (widgetType == null) {
							return null;
					}
					
					Element element = doc.createElement(widgetType.contains(".") ? widgetType : widgetType.toLowerCase());
					
					String widgetId = WidgetUtil.getWidgetId(widget);
					if (widgetId == null || widgetId.isEmpty()) {
							widgetId = ((WidgetContract) widget).newWidgetId();
							((WidgetContract) widget).setWidgetId(widgetId);
					}
					element.setAttribute("android:id", "@+id/" + widgetId);
					element.setAttribute("android:layout_width", getDimensionString(widget.getLayoutParams().width));
					element.setAttribute("android:layout_height", getDimensionString(widget.getLayoutParams().height));
					
					Map<String, String> customAttrs = widgetCustomAttributes.getOrDefault(widgetId, new HashMap<>());
					for (Map.Entry<String, String> attr : customAttrs.entrySet()) {
							element.setAttribute(attr.getKey(), attr.getValue());
					}
					
					if (widget.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
							ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) widget.getLayoutParams();
							element.setAttribute("android:layout_marginLeft", params.leftMargin + "dp");
							element.setAttribute("android:layout_marginTop", params.topMargin + "dp");
							element.setAttribute("android:layout_marginRight", params.rightMargin + "dp");
							element.setAttribute("android:layout_marginBottom", params.bottomMargin + "dp");
					}
					
					element.setAttribute("android:paddingLeft", widget.getPaddingLeft() + "dp");
					element.setAttribute("android:paddingTop", widget.getPaddingTop() + "dp");
					element.setAttribute("android:paddingRight", widget.getPaddingRight() + "dp");
					element.setAttribute("android:paddingBottom", widget.getPaddingBottom() + "dp");
					
					if (widget.getBackground() instanceof android.graphics.drawable.ColorDrawable) {
							int color = ((android.graphics.drawable.ColorDrawable) widget.getBackground()).getColor();
							element.setAttribute("android:background", String.format("#%08X", (0xFFFFFFFF & color)));
					}
					
					if (widget instanceof WidgetLinearLayout) {
							WidgetLinearLayout linearLayout = (WidgetLinearLayout) widget;
							element.setAttribute("android:orientation",
							linearLayout.getOrientation() == LinearLayout.HORIZONTAL ? "horizontal" : "vertical");
							element.setAttribute("android:gravity", gravityToString(linearLayout.getGravity()));
							for (int i = 0; i < linearLayout.getChildCount(); i++) {
									Element childElement = createWidgetElement(doc, linearLayout.getChildAt(i));
									if (childElement != null) {
											element.appendChild(childElement);
									}
							}
					} else if (widget instanceof WidgetScrollView) {
							WidgetScrollView scrollView = (WidgetScrollView) widget;
							//element.setAttribute("android:gravity", gravityToString(scrollView.getGravity()));
							for (int i = 0; i < scrollView.getChildCount(); i++) {
									Element childElement = createWidgetElement(doc, scrollView.getChildAt(i));
									if (childElement != null) {
											element.appendChild(childElement);
									}
							}
					} else if (widget instanceof WidgetHorizontalScrollView) {
							WidgetHorizontalScrollView horizontalScrollView = (WidgetHorizontalScrollView) widget;
							//	element.setAttribute("android:gravity", gravityToString(horizontalScrollView.getGravity()));
							for (int i = 0; i < horizontalScrollView.getChildCount(); i++) {
									Element childElement = createWidgetElement(doc, horizontalScrollView.getChildAt(i));
									if (childElement != null) {
											element.appendChild(childElement);
									}
							}
					} else if (widget instanceof WidgetFrameLayout) {
							WidgetFrameLayout frameLayout = (WidgetFrameLayout) widget;
							for (int i = 0; i < frameLayout.getChildCount(); i++) {
									Element childElement = createWidgetElement(doc, frameLayout.getChildAt(i));
									if (childElement != null) {
											element.appendChild(childElement);
									}
							}
					}
					
					if (widget instanceof WidgetTextView || widget instanceof WidgetButton || widget instanceof WidgetEditText ||
					widget instanceof WidgetCheckBox || widget instanceof WidgetSwitch || widget instanceof WidgetRadioButton) {
							TextView textView = WidgetUtil.getTextViewOfWidget(widget);
							if (textView != null) {
									element.setAttribute("android:text", textView.getText().toString());
									element.setAttribute("android:textSize", (textView.getTextSize() / textView.getResources().getDisplayMetrics().scaledDensity) + "sp");
									element.setAttribute("android:textColor", String.format("#%08X", (0xFFFFFFFF & textView.getCurrentTextColor())));
									element.setAttribute("android:gravity", gravityToString(textView.getGravity()));
									element.setAttribute("android:maxLines", String.valueOf(textView.getMaxLines()));
									if (textView.getTypeface() != null) {
											element.setAttribute("android:textStyle", typefaceStyleToString(textView.getTypeface().getStyle()));
									}
							}
					}
					
					if (widget instanceof WidgetEditText) {
							WidgetEditText editText = (WidgetEditText) widget;
							element.setAttribute("android:hint", editText.getEditText().getHint() != null ? editText.getEditText().getHint().toString() : "");
							element.setAttribute("android:inputType", inputTypeToString(editText.getEditText().getInputType()));
					}
					
					if (widget instanceof WidgetImageView || widget instanceof WidgetCircleImageView) {
							String imagePath = WidgetUtil.getImagePath(widget);
							if (imagePath != null && !imagePath.isEmpty()) {
									element.setAttribute("android:src", imagePath);
							}
							if (widget instanceof ImageView) {
									element.setAttribute("android:scaleType", ((ImageView) widget).getScaleType().toString().toLowerCase());
							}
					}
					
					if (widget instanceof WidgetCheckBox) {
							element.setAttribute("android:checked", String.valueOf(((WidgetCheckBox) widget).isChecked()));
					} else if (widget instanceof WidgetSwitch) {
							element.setAttribute("android:checked", String.valueOf(((WidgetSwitch) widget).isChecked()));
					} else if (widget instanceof WidgetRadioButton) {
							element.setAttribute("android:checked", String.valueOf(((WidgetRadioButton) widget).isChecked()));
					}
					
					if (widget instanceof WidgetProgressBar) {
							WidgetProgressBar progressBar = (WidgetProgressBar) widget;
							element.setAttribute("android:progress", String.valueOf(progressBar.getProgress()));
							element.setAttribute("android:max", String.valueOf(progressBar.getMax()));
					}
					
					if (widget instanceof WidgetSeekBar) {
							WidgetSeekBar seekBar = (WidgetSeekBar) widget;
							element.setAttribute("android:progress", String.valueOf(seekBar.getProgress()));
							element.setAttribute("android:max", String.valueOf(seekBar.getMax()));
					}
					
					if (widget instanceof WidgetVideoView) {
							String videoPath = ((WidgetVideoView) widget).getVideoPath();
							if (videoPath != null && !videoPath.isEmpty()) {
									element.setAttribute("android:src", videoPath);
							}
					}
					
					if (widget instanceof WidgetWebView) {
							String url = ((WidgetWebView) widget).getUrl();
							if (url != null && !url.isEmpty()) {
									element.setAttribute("android:src", url);
							}
							element.setAttribute("android:javaScriptEnabled", String.valueOf(((WidgetWebView) widget).getSettings().getJavaScriptEnabled()));
					}
					
					if (widget instanceof WidgetViewPager) {
							// No specific attributes; adapter is runtime-configured
					}
					
					element.setAttribute("android:translationX", widget.getTranslationX() + "dp");
					element.setAttribute("android:translationY", widget.getTranslationY() + "dp");
					element.setAttribute("android:rotation", String.valueOf(widget.getRotation()));
					element.setAttribute("android:scaleX", String.valueOf(widget.getScaleX()));
					element.setAttribute("android:scaleY", String.valueOf(widget.getScaleY()));
					element.setAttribute("android:alpha", String.valueOf(widget.getAlpha()));
					element.setAttribute("android:visibility", visibilityToString(widget.getVisibility()));
					element.setAttribute("android:elevation", widget.getElevation() + "dp");
					
					return element;
			} catch (Exception e) {
					e.printStackTrace();
					return null;
			}
	}
	
	private void parseXmlLayout(String xmlLayout) {
			try {
					Log.d(TAG, "Parsing XML layout:\n" + xmlLayout);
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = factory.newDocumentBuilder();
					Document doc = builder.parse(new InputSource(new StringReader(xmlLayout)));
					doc.getDocumentElement().normalize();
					
					Element root = doc.getDocumentElement();
					Log.d(TAG, "Root element: " + root.getTagName());
					if (!root.getTagName().equals("LinearLayout")) {
							TheBlockLogicsUtil.showToast(context, "Invalid root element: " + root.getTagName());
							return;
					}
					
					NodeList nodes = root.getChildNodes();
					Log.d(TAG, "Root has " + nodes.getLength() + " child nodes");
					for (int i = 0; i < nodes.getLength(); i++) {
							if (nodes.item(i) instanceof Element) {
									Element element = (Element) nodes.item(i);
									Log.d(TAG, "Processing child element: " + element.getTagName());
									View widget = createWidgetFromXmlElement(element);
									if (widget != null) {
											ll.addView(widget);
											
											if (DesignActivity.abc != null) {
													String javaCode = DesignActivity.abc.getJavaCode();
													String xmlCode = DesignActivity.abc.getXmlCode();
													DesignActivity.abc.complex.setXmlCode(DesignActivity.abc.currentActivityBean.getLayoutName(), xmlCode);
													DesignActivity.abc.complex.setJavaCode(DesignActivity.abc.currentActivityBean.getActivityName(), javaCode);
											}
											
											String widgetId = element.getAttribute("android:id").replace("@+id/", "");
											Map<String, String> customAttrs = new HashMap<>();
											NamedNodeMap attrs = element.getAttributes();
											for (int j = 0; j < attrs.getLength(); j++) {
													Node attr = attrs.item(j);
													String attrName = attr.getNodeName();
													if (!attrName.startsWith("android:")) {
															customAttrs.put(attrName, attr.getNodeValue());
													}
											}
											
											widgetCustomAttributes.put(widgetId, customAttrs);
									} else {
											Log.w(TAG, "Failed to create widget from element: " + element.getTagName());
									}
							}
					}
					
					ll.requestLayout();
					saveCustomAttributes();
			} catch (Exception e) {
					TheBlockLogicsUtil.showToast(context, "Error parsing XML: " + e.getMessage());
					Log.e(TAG, "XML parsing error: " + e.getMessage(), e);
			}
	}
	
	/**
* Creates a widget from an XML element
*/
	private View createWidgetFromXmlElement(Element element) {
			try {
					String type = element.getTagName();
					String widgetId = element.getAttribute("android:id").replace("@+id/", "");
					View widget = createWidgetFromType("Widget" + type, widgetId);
					if (widget == null) {
							return null;
					}
					
					// Apply layout parameters
					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					parseDimension(element.getAttribute("android:layout_width")),
					parseDimension(element.getAttribute("android:layout_height"))
					);
					params.setMargins(
					parseDimension(element.getAttribute("android:layout_marginLeft")),
					parseDimension(element.getAttribute("android:layout_marginTop")),
					parseDimension(element.getAttribute("android:layout_marginRight")),
					parseDimension(element.getAttribute("android:layout_marginBottom"))
					);
					widget.setLayoutParams(params);
					
					
					// Apply padding
					widget.setPadding(
					parseDimension(element.getAttribute("android:paddingLeft")),
					parseDimension(element.getAttribute("android:paddingTop")),
					parseDimension(element.getAttribute("android:paddingRight")),
					parseDimension(element.getAttribute("android:paddingBottom"))
					);
					
					// Apply background
					if (element.hasAttribute("android:background")) {
							try {
									widget.setBackgroundColor(Color.parseColor(element.getAttribute("android:background")));
							} catch (IllegalArgumentException e) {
									// Ignore invalid color
							}
					}
					
					// Apply text properties
					TextView textView = WidgetUtil.getTextViewOfWidget(widget);
					if (textView != null && element.hasAttribute("android:text")) {
							textView.setText(element.getAttribute("android:text"));
							if (element.hasAttribute("android:textSize")) {
									textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, parseFloat(element.getAttribute("android:textSize")));
							}
							if (element.hasAttribute("android:textColor")) {
									try {
											textView.setTextColor(Color.parseColor(element.getAttribute("android:textColor")));
									} catch (IllegalArgumentException e) {
											// Ignore invalid color
									}
							}
							if (element.hasAttribute("android:gravity")) {
									textView.setGravity(parseGravity(element.getAttribute("android:gravity")));
							}
							if (element.hasAttribute("android:maxLines")) {
									try {
											textView.setMaxLines(Integer.parseInt(element.getAttribute("android:maxLines")));
									} catch (NumberFormatException e) {
											// Ignore invalid maxLines
									}
							}
							if (element.hasAttribute("android:textStyle")) {
									textView.setTypeface(textView.getTypeface(), parseTextStyle(element.getAttribute("android:textStyle")));
							}
					}
					
					if (widget instanceof WidgetEditText && element.hasAttribute("android:hint")) {
							((WidgetEditText) widget).getEditText().setHint(element.getAttribute("android:hint"));
							if (element.hasAttribute("android:inputType")) {
									((WidgetEditText) widget).getEditText().setInputType(stringToInputType(element.getAttribute("android:inputType")));
							}
					}
					
					if (widget instanceof WidgetImageView && element.hasAttribute("android:scaleType")) {
							try {
									((WidgetImageView) widget).setScaleType(ImageView.ScaleType.valueOf(element.getAttribute("android:scaleType").toUpperCase()));
							} catch (IllegalArgumentException e) {
									// Ignore invalid scaleType
							}
					} else if (widget instanceof WidgetCircleImageView && element.hasAttribute("android:scaleType")) {
							try {
									((WidgetCircleImageView) widget).setScaleType(ImageView.ScaleType.valueOf(element.getAttribute("android:scaleType").toUpperCase()));
							} catch (IllegalArgumentException e) {
									// Ignore invalid scaleType
							}
					}
					
					if (widget instanceof WidgetCheckBox && element.hasAttribute("android:checked")) {
							((WidgetCheckBox) widget).setChecked(Boolean.parseBoolean(element.getAttribute("android:checked")));
					} else if (widget instanceof WidgetSwitch && element.hasAttribute("android:checked")) {
							((WidgetSwitch) widget).setChecked(Boolean.parseBoolean(element.getAttribute("android:checked")));
					} else if (widget instanceof WidgetRadioButton && element.hasAttribute("android:checked")) {
							((WidgetRadioButton) widget).setChecked(Boolean.parseBoolean(element.getAttribute("android:checked")));
					}
					
					if (widget instanceof WidgetProgressBar) {
							if (element.hasAttribute("android:progress")) {
									try {
											((WidgetProgressBar) widget).setProgress(Integer.parseInt(element.getAttribute("android:progress")));
									} catch (NumberFormatException e) {
											// Ignore invalid progress
									}
							}
							if (element.hasAttribute("android:max")) {
									try {
											((WidgetProgressBar) widget).setMax(Integer.parseInt(element.getAttribute("android:max")));
									} catch (NumberFormatException e) {
											// Ignore invalid max
									}
							}
					}
					
					if (widget instanceof WidgetSeekBar) {
							if (element.hasAttribute("android:progress")) {
									try {
											((WidgetSeekBar) widget).setProgress(Integer.parseInt(element.getAttribute("android:progress")));
									} catch (NumberFormatException e) {
											// Ignore invalid progress
									}
							}
							if (element.hasAttribute("android:max")) {
									try {
											((WidgetSeekBar) widget).setMax(Integer.parseInt(element.getAttribute("android:max")));
									} catch (NumberFormatException e) {
											// Ignore invalid max
									}
							}
					}
					
					if (widget instanceof WidgetRatingView && element.hasAttribute("android:rating")) {
							try {
									((WidgetRatingView) widget).setRating(Float.parseFloat(element.getAttribute("android:rating")));
							} catch (NumberFormatException e) {
									// Ignore invalid rating
							}
							if (element.hasAttribute("android:numStars")) {
									try {
											((WidgetRatingView) widget).setNumStars(Integer.parseInt(element.getAttribute("android:numStars")));
									} catch (NumberFormatException e) {
											// Ignore invalid numStars
									}
							}
					}
					
					if (widget instanceof WidgetVideoView && element.hasAttribute("android:src")) {
							((WidgetVideoView) widget).setVideoPath(element.getAttribute("android:src"));
					}
					
					if (widget instanceof WidgetWebView && element.hasAttribute("android:src")) {
							((WidgetWebView) widget).setUrl(element.getAttribute("android:src"));
							if (element.hasAttribute("android:javaScriptEnabled")) {
									((WidgetWebView) widget).enableJavaScript(Boolean.parseBoolean(element.getAttribute("android:javaScriptEnabled")));
							}
					}
					
					if (widget instanceof WidgetViewPager) {
							// No specific attributes; adapter is runtime-configured
					}
					
					// Handle nested layouts
					if (widget instanceof WidgetLinearLayout || widget instanceof WidgetFrameLayout) {
							NodeList childNodes = element.getChildNodes();
							for (int i = 0; i < childNodes.getLength(); i++) {
									if (childNodes.item(i) instanceof Element) {
											View child = createWidgetFromXmlElement((Element) childNodes.item(i));
											if (child != null) {
													((ViewGroup) widget).addView(child);
											}
									}
							}
							if (widget instanceof WidgetLinearLayout && element.hasAttribute("android:orientation")) {
									((WidgetLinearLayout) widget).setOrientation(
									element.getAttribute("android:orientation").equals("horizontal") ? LinearLayout.HORIZONTAL : LinearLayout.VERTICAL);
									if (element.hasAttribute("android:gravity")) {
											((WidgetLinearLayout) widget).setGravity(parseGravity(element.getAttribute("android:gravity")));
									}
							}
					}
					
					// Apply transformations
					if (element.hasAttribute("android:translationX")) {
							widget.setTranslationX(parseDimension(element.getAttribute("android:translationX")));
					}
					if (element.hasAttribute("android:translationY")) {
							widget.setTranslationY(parseDimension(element.getAttribute("android:translationY")));
					}
					if (element.hasAttribute("android:rotation")) {
							try {
									widget.setRotation(Float.parseFloat(element.getAttribute("android:rotation")));
							} catch (NumberFormatException e) {
									// Ignore invalid rotation
							}
					}
					if (element.hasAttribute("android:scaleX")) {
							try {
									widget.setScaleX(Float.parseFloat(element.getAttribute("android:scaleX")));
							} catch (NumberFormatException e) {
									// Ignore invalid scaleX
							}
					}
					if (element.hasAttribute("android:scaleY")) {
							try {
									widget.setScaleY(Float.parseFloat(element.getAttribute("android:scaleY")));
							} catch (NumberFormatException e) {
									// Ignore invalid scaleY
							}
					}
					if (element.hasAttribute("android:alpha")) {
							try {
									widget.setAlpha(Float.parseFloat(element.getAttribute("android:alpha")));
							} catch (NumberFormatException e) {
									// Ignore invalid alpha
							}
					}
					if (element.hasAttribute("android:visibility")) {
							widget.setVisibility(parseVisibility(element.getAttribute("android:visibility")));
					}
					if (element.hasAttribute("android:elevation")) {
							widget.setElevation(parseDimension(element.getAttribute("android:elevation")));
					}
					
					return widget;
			} catch (Exception e) {
					e.printStackTrace();
					return null;
			}
	}
	
	/**
* Helper methods for XML attribute parsing
*/
	private String getDimensionString(int dimension) {
			if (dimension == ViewGroup.LayoutParams.MATCH_PARENT) return "match_parent";
			if (dimension == ViewGroup.LayoutParams.WRAP_CONTENT) return "wrap_content";
			return dimension + "dp";
	}
	
	private int parseDimension(String value) {
			if (value == null || value.isEmpty()) return 0;
			if (value.equals("match_parent")) return ViewGroup.LayoutParams.MATCH_PARENT;
			if (value.equals("wrap_content")) return ViewGroup.LayoutParams.WRAP_CONTENT;
			try {
					return Integer.parseInt(value.replace("dp", "").trim());
			} catch (NumberFormatException e) {
					return 0;
			}
	}
	
	private float parseFloat(String value) {
			if (value == null || value.isEmpty()) return 0f;
			try {
					return Float.parseFloat(value.replace("sp", "").trim());
			} catch (NumberFormatException e) {
					return 0f;
			}
	}
	
	private String gravityToString(int gravity) {
			if (gravity == 0) return "";
			List<String> parts = new ArrayList<>();
			if ((gravity & Gravity.LEFT) == Gravity.LEFT) parts.add("left");
			if ((gravity & Gravity.RIGHT) == Gravity.RIGHT) parts.add("right");
			if ((gravity & Gravity.TOP) == Gravity.TOP) parts.add("top");
			if ((gravity & Gravity.BOTTOM) == Gravity.BOTTOM) parts.add("bottom");
			if ((gravity & Gravity.CENTER) == Gravity.CENTER) parts.add("center");
			if ((gravity & Gravity.CENTER_HORIZONTAL) == Gravity.CENTER_HORIZONTAL) parts.add("center_horizontal");
			if ((gravity & Gravity.CENTER_VERTICAL) == Gravity.CENTER_VERTICAL) parts.add("center_vertical");
			if ((gravity & Gravity.START) == Gravity.START) parts.add("start");
			if ((gravity & Gravity.END) == Gravity.END) parts.add("end");
			return String.join("|", parts);
	}
	
	private int parseGravity(String gravity) {
			if (gravity == null || gravity.isEmpty()) return Gravity.START;
			int result = 0;
			for (String part : gravity.split("\\|")) {
					switch (part.trim()) {
							case "left": result |= Gravity.LEFT; break;
							case "right": result |= Gravity.RIGHT; break;
							case "top": result |= Gravity.TOP; break;
							case "bottom": result |= Gravity.BOTTOM; break;
							case "center": result |= Gravity.CENTER; break;
							case "center_horizontal": result |= Gravity.CENTER_HORIZONTAL; break;
							case "center_vertical": result |= Gravity.CENTER_VERTICAL; break;
							case "start": result |= Gravity.START; break;
							case "end": result |= Gravity.END; break;
					}
			}
			return result == 0 ? Gravity.START : result;
	}
	
	private String typefaceStyleToString(int style) {
			switch (style) {
					case Typeface.BOLD: return "bold";
					case Typeface.ITALIC: return "italic";
					case Typeface.BOLD_ITALIC: return "bold|italic";
					default: return "normal";
			}
	}
	
	private int parseTextStyle(String style) {
			if (style == null || style.isEmpty() || style.equals("normal")) return Typeface.NORMAL;
			int result = 0;
			for (String part : style.split("\\|")) {
					if (part.equals("bold")) result |= Typeface.BOLD;
					if (part.equals("italic")) result |= Typeface.ITALIC;
			}
			return result;
	}
	
	private String visibilityToString(int visibility) {
			switch (visibility) {
					case View.VISIBLE: return "visible";
					case View.INVISIBLE: return "invisible";
					case View.GONE: return "gone";
					default: return "visible";
			}
	}
	
	private int parseVisibility(String visibility) {
			if (visibility == null || visibility.isEmpty()) return View.VISIBLE;
			switch (visibility) {
					case "visible": return View.VISIBLE;
					case "invisible": return View.INVISIBLE;
					case "gone": return View.GONE;
					default: return View.VISIBLE;
			}
	}
	
	private String inputTypeToString(int inputType) {
			if ((inputType & InputType.TYPE_CLASS_TEXT) == InputType.TYPE_CLASS_TEXT) {
					if ((inputType & InputType.TYPE_TEXT_VARIATION_PASSWORD) == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
							return "textPassword";
					} else if ((inputType & InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS) == InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS) {
							return "textEmailAddress";
					} else if ((inputType & InputType.TYPE_TEXT_FLAG_MULTI_LINE) == InputType.TYPE_TEXT_FLAG_MULTI_LINE) {
							return "textMultiLine";
					} else {
							return "text";
					}
			} else if ((inputType & InputType.TYPE_CLASS_NUMBER) == InputType.TYPE_CLASS_NUMBER) {
					return "number";
			} else if ((inputType & InputType.TYPE_CLASS_PHONE) == InputType.TYPE_CLASS_PHONE) {
					return "phone";
			}
			return "text";
	}
	
	private int stringToInputType(String inputType) {
			if (inputType == null || inputType.isEmpty()) return InputType.TYPE_CLASS_TEXT;
			switch (inputType) {
					case "text": return InputType.TYPE_CLASS_TEXT;
					case "textPassword": return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
					case "textEmailAddress": return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
					case "textMultiLine": return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE;
					case "number": return InputType.TYPE_CLASS_NUMBER;
					case "phone": return InputType.TYPE_CLASS_PHONE;
					default: return InputType.TYPE_CLASS_TEXT;
			}
	}
	
	private JSONObject getWidgetData(View v) throws JSONException {
		    JSONObject json = new JSONObject();
		/*
    if (v instanceof Widget) {
        Widget w = (Widget) v;
        json.put("id", w.getWidgetId());
        json.put("type", w.getClass().getSimpleName());

        ViewGroup.LayoutParams lp = v.getLayoutParams();
        if (lp != null) {
            json.put("width", lp.width);
            json.put("height", lp.height);
        }

        // Example: for WidgetTextView
        if (w instanceof WidgetTextView) {
            WidgetTextView wt = (WidgetTextView) w;
            json.put("text", wt.getTextView().getText().toString());
            json.put("text_size", wt.getTextView().getTextSize());
            json.put("text_color", wt.getTextView().getCurrentTextColor());
        }

        // If it's a ViewGroup (container)
        if (v instanceof ViewGroup) {
            JSONArray children = new JSONArray();
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++) {
                View child = vg.getChildAt(i);
                JSONObject childJson = getWidgetData(child);
                if (childJson != null) {
                    children.put(childJson);
                }
            }
            if (children.length() > 0) {
                json.put("children", children);
            }
        }
    }
*/
		    return json;
	}
	/**
* Returns the XML tag name for a given widget View
*/
	private String getWidgetType(View widget) {
			if (widget instanceof WidgetTextView) {
					return "TextView";
			} else if (widget instanceof WidgetButton) {
					return "Button";
			} else if (widget instanceof WidgetEditText) {
					return "EditText";
			} else if (widget instanceof WidgetImageView) {
					return "ImageView";
			} else if (widget instanceof WidgetCircleImageView) {
					return "CircleImageView";
			} else if (widget instanceof WidgetLinearLayout) {
					return "LinearLayout";
			} else if (widget instanceof WidgetFrameLayout) {
					return "FrameLayout";
			} else if (widget instanceof WidgetWebView) {
					return "WebView";
			} else if (widget instanceof WidgetListView) {
					return "ListView";
			} else if (widget instanceof WidgetViewPager) {
					return "ViewPager";
			} else if (widget instanceof WidgetCheckBox) {
					return "CheckBox";
			} else if (widget instanceof WidgetSwitch) {
					return "Switch";
			} else if (widget instanceof WidgetVideoView) {
					return "VideoView";
			} else if (widget instanceof WidgetProgressBar) {
					return "ProgressBar";
			} else if (widget instanceof WidgetSeekBar) {
					return "SeekBar";
			} else if (widget instanceof WidgetRadioButton) {
					return "RadioButton";
			} else if (widget instanceof WidgetSearchView) {
					return "SearchView";
			} else if (widget instanceof WidgetRatingView) {
					return "RatingBar"; // Matches Android's XML tag
			} else if (widget instanceof WidgetDigitalClock) {
					return "DigitalClock"; // Matches Android's XML tag
			} else if (widget instanceof WidgetTimePicker) {
					return "TimePicker"; // Matches Android's XML tag
			} else if (widget instanceof WidgetScrollView) {
					return "ScrollView"; // Matches Android's XML tag
			} else if (widget instanceof WidgetHorizontalScrollView) {
					return "HorizontalScrollView"; // Matches Android's XML tag
			} else {
				//	return widget.getClass().getSimpleName(); // Fallback to class name
			    return "";
			}
	}
	
	public void showScaleTypeDialog() {
		    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		    View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_scale_type, null);
		    builder.setView(view);
		
		    RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
		    Button cancelBtn = view.findViewById(R.id.btn_cancel);
		    Button selectBtn = view.findViewById(R.id.btn_select);
		
		    AlertDialog dialog = builder.create();
		
		    // Preselect current scale type
		    ImageView.ScaleType currentType = ((WidgetImageView) selectedWidget).getScaleType();
		    switch (currentType) {
			        case FIT_XY:
			            radioGroup.check(R.id.rb_fit_xy);
			            break;
			        case FIT_START:
			            radioGroup.check(R.id.rb_fit_start);
			            break;
			        case FIT_CENTER:
			            radioGroup.check(R.id.rb_fit_center);
			            break;
			        case FIT_END:
			            radioGroup.check(R.id.rb_fit_end);
			            break;
			        case CENTER:
			            radioGroup.check(R.id.rb_center);
			            break;
			        case CENTER_CROP:
			            radioGroup.check(R.id.rb_center_crop);
			            break;
			        case CENTER_INSIDE:
			            radioGroup.check(R.id.rb_center_inside);
			            break;
			    }
		
		    cancelBtn.setOnClickListener(v0 -> dialog.dismiss());
		
		    selectBtn.setOnClickListener(v1 -> {
			        int selectedId = radioGroup.getCheckedRadioButtonId();
			        if (selectedId != -1) {
				            switch (selectedId) {
					                case R.id.rb_fit_xy:
					                    ((WidgetImageView) selectedWidget).setScaleType(ImageView.ScaleType.FIT_XY);
					                    break;
					                case R.id.rb_fit_start:
					                    ((WidgetImageView) selectedWidget).setScaleType(ImageView.ScaleType.FIT_START);
					                    break;
					                case R.id.rb_fit_center:
					                    ((WidgetImageView) selectedWidget).setScaleType(ImageView.ScaleType.FIT_CENTER);
					                    break;
					                case R.id.rb_fit_end:
					                    ((WidgetImageView) selectedWidget).setScaleType(ImageView.ScaleType.FIT_END);
					                    break;
					                case R.id.rb_center:
					                    ((WidgetImageView) selectedWidget).setScaleType(ImageView.ScaleType.CENTER);
					                    break;
					                case R.id.rb_center_crop:
					                    ((WidgetImageView) selectedWidget).setScaleType(ImageView.ScaleType.CENTER_CROP);
					                    break;
					                case R.id.rb_center_inside:
					                    ((WidgetImageView) selectedWidget).setScaleType(ImageView.ScaleType.CENTER_INSIDE);
					                    break;
					            }
				        }
			        dialog.dismiss();
			    });
		
		    dialog.show();
	}
	
	{
	}
	
	
	public void _b() {
	}
	private View createWidgetFromType(String widgetType, String widgetId) {
			View widget = null;
			if (context == null) return null;
			
			try {
					if (widgetType.contains(".")) { // Custom view (fully qualified name)
							Class<?> customClass = Class.forName(widgetType);
							if (View.class.isAssignableFrom(customClass)) {
									widget = (View) customClass.getConstructor(Context.class).newInstance(context);
									//	widget.setTag(TAG_WIDGET_ID, widgetId);
									widget.setTag(ViewEditorFragmentActivity.TAG_CUSTOM_CLASS_NAME, widgetType); // Store the class name for XML
							}
					} else {
							switch (widgetType) {
									case "WidgetTextView":
									widget = new WidgetTextView(context);
									break;
									case "WidgetButton":
									widget = new WidgetButton(context);
									break;
									case "WidgetImageView":
									widget = new WidgetImageView(context);
									break;
									case "WidgetWebView":
									widget = new WidgetWebView(context);
									break;
									case "WidgetEditText":
									widget = new WidgetEditText(context);
									break;
									case "WidgetCheckBox":
									widget = new WidgetCheckBox(context);
									break;
									case "WidgetSwitch":
									widget = new WidgetSwitch(context);
									break;
									case "WidgetVideoView":
									widget = new WidgetVideoView(context);
									break;
									case "WidgetProgressBar":
									widget = new WidgetProgressBar(context);
									break;
									case "WidgetSeekBar":
									widget = new WidgetSeekBar(context);
									break;
									case "WidgetRadioButton":
									widget = new WidgetRadioButton(context);
									break;
									case "WidgetLinearLayout":
									widget = new WidgetLinearLayout(context);
									break;
									case "WidgetFrameLayout":
									widget = new WidgetFrameLayout(context);
									break;
									case "WidgetListView":
									widget = new WidgetListView(context);
									break;
									case "WidgetViewPager":
									widget = new WidgetViewPager(context);
									break;
									case "WidgetSearchView":
									widget = new WidgetSearchView(context);
									break;
									case "WidgetRatingView":
									widget = new WidgetRatingView(context);
									break;
									case "WidgetCircleImageView":
									widget = new WidgetCircleImageView(context);
									break;
									case "WidgetDigitalClock":
									widget = new DigitalClock(context);
									break;
									case "WidgetTimePicker":
									widget = new WidgetTimePicker(context);
									break;
					                case "WidgetScrollView":
									widget = new WidgetScrollView(context);
									break;
					                case "WidgetHorizontalScrollView":
									widget = new WidgetHorizontalScrollView(context);
									break;
									default:
									return null;
							}
							if (widget instanceof Widget) {
									((WidgetContract) widget).setWidgetId(widgetId);
							    	widget.setOnClickListener(new WidgetClickListener());
									widget.setOnLongClickListener(this);
							}
							return widget;
					}
			} catch (Exception e) {
					//	TheBlockLogicsUtil.showToast(context, "Error creating widget: " + e.getMessage());
					e.printStackTrace();
					return null;
			}
			return null;
	}
	
	private void showOrientationDialog(String title, String message, View view) {
		    Context context = getContext();
		    if (context == null) {
			        TheBlockLogicsUtil.showToast(context, "Context unavailable");
			        return;
			    }
		
		    LayoutInflater inflater = LayoutInflater.from(context);
		    View dialogView = inflater.inflate(R.layout.orientation_dialog, null);
		
		    RadioButton horizontalRadio = dialogView.findViewById(R.id.radio_horizontal);
		    RadioButton verticalRadio = dialogView.findViewById(R.id.radio_vertical);
		
		    // Preselect the current orientation
		    if (selectedWidget instanceof WidgetLinearLayout) {
			        int currentOrientation = ((WidgetLinearLayout) selectedWidget).getOrientation();
			        if (currentOrientation == LinearLayout.HORIZONTAL) {
				            horizontalRadio.setChecked(true);
				        } else {
				            verticalRadio.setChecked(true); // Default to vertical
				        }
			    } else {
			        verticalRadio.setChecked(true); // Default to vertical if not a LinearLayout
			    }
		
		    AlertDialog dialog = new AlertDialog.Builder(context)
		            .setTitle("Orientation")
		            .setView(dialogView)
		            .setPositiveButton("Select", (d, which) -> {
			                String orientation = horizontalRadio.isChecked() ? "horizontal" : "vertical";
			
			                if (selectedWidget instanceof WidgetLinearLayout) {
				                    WidgetLinearLayout linearLayout = (WidgetLinearLayout) selectedWidget;
				                    if (orientation.equals("horizontal")) {
					                        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
					                    } else {
					                        linearLayout.setOrientation(LinearLayout.VERTICAL);
					                    }
				
				                    saveStateToUndo();
				                    ll.invalidate();
				                } else {
				                    TheBlockLogicsUtil.showToast(context, "Orientation can only be set for LinearLayout");
				                }
			            })
		            .setNegativeButton("Cancel", null)
		            .create();
		    dialog.show();
	}
	private void showChangeClassDialog() {
		    widget = selectedWidget;
			Context context = getContext();
			if (context == null) {
					TheBlockLogicsUtil.showToast(context, "Context unavailable");
					return;
			}
			
			// Inflate a dialog layout similar to idWidget dialog
			LayoutInflater inflater = LayoutInflater.from(context);
			View dialogView = inflater.inflate(R.layout.change_class_dialog, null);
			
			EditText classNameInput = new EditText(context);
			classNameInput.setHint("Enter custom class name (e.g., com.example.myapp.MyCustomView)");
			classNameInput.setLayoutParams(new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.WRAP_CONTENT
			));
			
			// Display the current class name
			String currentClass = (String) widget.getTag(TAG_CUSTOM_CLASS_NAME);
			if (currentClass == null) {
					currentClass = getWidgetType(widget); // Fallback to standard widget type
			}
			classNameInput.setText(currentClass);
			
			LinearLayout container = new LinearLayout(context);
			container.setOrientation(LinearLayout.VERTICAL);
			container.setPadding(16, 16, 16, 16);
			container.addView(classNameInput);
			
			AlertDialog dialog = new AlertDialog.Builder(context)
			.setTitle("Change Widget Class")
			.setView(container)
			.setPositiveButton("Apply", (d, which) -> {
					String newClassName = classNameInput.getText().toString().trim();
					if (newClassName.isEmpty()) {
							TheBlockLogicsUtil.showToast(context, "Class name cannot be empty");
							return;
					}
					
					// Validate the class exists (optional, since this is an IDE)
					try {
							Class<?> customClass = Class.forName(newClassName);
							if (!View.class.isAssignableFrom(customClass)) {
									//TheBlockLogicsUtil.showToast(context, "Class must extend View");
									return;
							}
					} catch (ClassNotFoundException e) {
							//TheBlockLogicsUtil.showToast(context, "Class not found, but will save to XML");
					}
					
					// Store the new class name in the widget's tag using the string key
					widget.setTag(TAG_CUSTOM_CLASS_NAME, newClassName);
					
					// Update ViewBean and save layout to reflect the change in XML
					saveStateToUndo();
					ll.invalidate();
			})
			.setNegativeButton("Cancel", null)
			.create();
			dialog.show();
	}
	
	private void generateKeyIfNotExists() throws Exception {
		    KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
		    keyStore.load(null);
		
		    if (!keyStore.containsAlias("my_rsa_key")) {
			        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(
			                KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore");
			        keyPairGenerator.initialize(
			            new KeyGenParameterSpec.Builder("my_rsa_key",
			                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
			                .setKeySize(2048)
			                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
			                .build());
			        keyPairGenerator.generateKeyPair();
			    }
	}
	private PublicKey getPublicKey() throws Exception {
		    KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
		    keyStore.load(null);
		    return keyStore.getCertificate("my_rsa_key").getPublicKey();
	}
	
	private PrivateKey getPrivateKey() throws Exception {
		    KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
		    keyStore.load(null);
		    return (PrivateKey) keyStore.getKey("my_rsa_key", null);
	}
	
	private byte[] encryptWithRSA(byte[] data, PublicKey publicKey) throws Exception {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			return cipher.doFinal(data);
	}
	
	private byte[] decryptWithRSA(byte[] data, PrivateKey privateKey) throws Exception {
		    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		    cipher.init(Cipher.DECRYPT_MODE, privateKey);
		    return cipher.doFinal(data);
	}
	
	private void showWeightDialog() {
		    Context context = getContext();
		    Dialog dialog = new Dialog(context);
		    dialog.setContentView(R.layout.dialog_weight);
		
		    EditText weightInput = dialog.findViewById(R.id.weight_input);
		    Button cancelButton = dialog.findViewById(R.id.cancel_button);
		    Button saveButton = dialog.findViewById(R.id.save_button);
		
		    // Ensure the selectedWidget is inside a LinearLayout
		    if (selectedWidget.getLayoutParams() instanceof LinearLayout.LayoutParams) {
			        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) selectedWidget.getLayoutParams();
			        weightInput.setText(String.valueOf(params.weight));
			    } else {
			        weightInput.setText("0"); // Default or unsupported layout
			    }
		
		    weightInput.requestFocus();
		    weightInput.setSelection(weightInput.getText().length());
		
		    cancelButton.setOnClickListener(v -> dialog.dismiss());
		
		    saveButton.setOnClickListener(v -> {
			        try {
				            float newWeight = Float.parseFloat(weightInput.getText().toString());
				
				            if (selectedWidget.getLayoutParams() instanceof LinearLayout.LayoutParams) {
					                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) selectedWidget.getLayoutParams();
					                params.weight = newWeight;
					                selectedWidget.setLayoutParams(params);
					            }
				
				            dialog.dismiss();
				        } catch (NumberFormatException e) {
				            weightInput.setError("Invalid weight value");
				        }
			    });
		
		    dialog.show();
	}
	
	{
	}
	
	
	public void _load_view() {
		
	}
	
}