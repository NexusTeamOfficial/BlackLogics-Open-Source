package com.nexusteam.blacklogics;

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
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.solver.*;
import androidx.constraintlayout.widget.*;
import androidx.core.*;
import androidx.customview.*;
import androidx.draganddrop.*;
import androidx.dynamicanimation.*;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.multidex.*;
import com.airbnb.viewmodeladapter.*;
import com.bumptech.glide.*;
import com.bumptech.glide.gifdecoder.*;
import com.example.myapp.*;
import com.github.angads25.filepicker.*;
import com.google.android.flexbox.*;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.*;
import com.google.gson.*;
import com.googlecode.d2j.*;
import com.larswerkman.holocolorpicker.*;
import com.nexusteam.internal.os.layouteditor.custom.MyDragWidget;
import com.squareup.leakcanary.*;
import de.hdodenhof.circleimageview.*;
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

public class ViewEditorFragmentActivity extends Fragment {
	
	int i = 0;
	public static View view_location;
	int defaultIndex;
	View mWidget;
	View mView;
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
	public static boolean addedInLayout;
	public static boolean isMainActivity = true;
	public static String projectName = "My project";
	public static final String TAG = "ViewFragment";
	public int mEditText;
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
	private TextView tv_view_name;
	private LinearLayout linear14;
	private ImageView imageview4;
	private ImageView imageview5;
	private ImageView imageview6;
	private TextView textview5;
	private TextView textview6;
	private LinearLayout ll_widgets;
	private CardView cardview1;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.view_editor_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
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
		ll_widgets = _view.findViewById(R.id.ll_widgets);
		cardview1 = _view.findViewById(R.id.cardview1);
	}
	
	private void initializeLogic() {
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
		
	}
	
}