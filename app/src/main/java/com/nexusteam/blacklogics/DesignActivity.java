package com.nexusteam.blacklogics;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.Intent;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.os.Bundle;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.*;
import androidx.appcompat.*;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.solver.*;
import androidx.constraintlayout.widget.*;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.*;
import androidx.core.view.GravityCompat;
import androidx.customview.*;
import androidx.draganddrop.*;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.dynamicanimation.*;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.multidex.*;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnAdapterChangeListener;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import com.airbnb.viewmodeladapter.*;
import com.bumptech.glide.*;
import com.bumptech.glide.gifdecoder.*;
import com.example.myapp.*;
import com.github.angads25.filepicker.*;
import com.google.android.flexbox.*;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.*;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.*;
import com.google.android.material.card.*;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.google.android.material.textfield.*;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.*;
import com.googlecode.d2j.*;
import com.larswerkman.holocolorpicker.*;
import com.squareup.leakcanary.*;
import de.hdodenhof.circleimageview.*;
import io.github.rosemoe.editor.*;
import io.github.rosemoe.sora.*;
import io.github.rosemoe.sora.langs.java.*;
import io.github.rosemoe.sora.langs.textmate.*;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.regex.*;
import org.antlr.v4.runtime.*;
import org.benf.cfr.reader.*;
import org.eclipse.jdt.*;
import org.json.*;
import a.a.a.Lx;
import b.b.b.aq;
import b.b.b.Qf;
import b.b.b.xq;
import b.b.b.yg;
import mod.SketchwareUtil;
import android.widget.Space;
import com.besome.blacklogics.model.*;
import com.besome.blacklogics.development.*;
import com.besome.blacklogics.custom.CustomActivityDialog;
import com.besome.blacklogics.design.DesignDrawer;
import com.besome.blacklogics.interfaces.CompilerLogListener;
import com.besome.blacklogics.util.FileHandler;
import com.besome.blacklogics.util.ProjectActivityManager;
import androidx.lifecycle.ViewModelProvider;
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
import com.besome.blacklogics.model.ActivityData;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.DragEvent;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.os.Environment;
import android.util.Base64;
import com.google.gson.reflect.TypeToken;
import com.besome.blacklogics.model.WidgetViewModel;
import android.content.Context;
import java.net.URISyntaxException;
import java.util.function.Consumer;
import java.lang.reflect.Type;
import com.apk.builder.model.Project;
import com.apk.builder.model.Library;
import com.apk.builder.FileUtil;
import com.apk.builder.logger.*;
import com.tyron.compiler.CompilerAsyncTask;
import com.apk.builder.SystemLogPrinter;
import com.apk.builder.model.BuildSettings.DexCompilerType;
import com.google.android.material.snackbar.Snackbar;
import com.besome.blacklogics.model.DesignDataManager;
import com.besome.blacklogics.util.FileHandler;
import com.nexusteam.blacklogics.beans.SelectedActivityState;
import com.besome.blacklogics.image_manager.ManageImageActivity;
import com.besome.blacklogics.font_manager.ManageFontActivity;
import com.besome.blacklogics.sound_manager.ManageSoundActivity;
import com.besome.blacklogics.view_manager.ManageViewActivity;
import com.besome.blacklogics.custom.CustomViewPager;
import com.besome.blacklogics.activities.ViewCodeEditorActivity;
import com.besome.blacklogics.beans.ProjectActivityBean;
import com.besome.blacklogics.file.WidgetClickListenerManager;
import com.besome.blacklogics.file.AssetCopyUtil;
import com.besome.blacklogics.development.Complex.ViewItem;
import com.besome.blacklogics.custom.CustomSpinner;
import com.nexusteam.blacklogics.design.CreateCustomViewDialog;
import com.shapun.layouteditor.XmlLayoutGenerator;
import com.nexusteam.blacklogics.design.CreateActivityDialog;
import com.nexusteam.blacklogics.design.CreateActivityDialogFragment;
import com.nexusteam.blacklogics.design.CustomSpinnerDialog;
import com.shapun.layouteditor.ViewEditor;
import java.lang.ref.WeakReference;
import com.besome.blacklogics.lib.base.BaseActivity;
import com.besome.blacklogics.beans.ActivityBean;
import com.besome.blacklogics.model.ActivityData;
import com.nexusteam.blacklogics.model.BuildSettings;
import com.nexusteam.blacklogics.logcat.LogcatActivity;
import com.nexusteam.blacklogics.ui.BuildSettingsDialog;
import com.nexusteam.blacklogics.ui.SourceCodeDialog;
import com.nexusteam.blacklogics.activities.ManageAssetsActivity;
import com.nexusteam.blacklogics.permission.ManagePermissionActivity;
import com.nexusteam.blacklogics.utils.Helper;
import com.nexusteam.sdk.compilerlog.CompileErrorSaver;
import com.besome.blacklogics.beans.ProjectBean;
import com.besome.blacklogics.custom_blocks.CustomBlocksDialog;
import com.nexusteam.sdk.java.ManageJavaActivity;
import com.nexusteam.sdk.resource.ManageResourceActivity;
import dev.aldi.sayuti.editor.manage.ManageLocalLibraryActivity ;
import com.nexusteam.sdk.nativelib.ManageNativelibsActivity;
import com.besome.blacklogics.tools.CompileLogActivity;
import com.nexusteam.sdk.activities.tools.ConfigActivity;
import androidx.core.content.ContextCompat;
import com.nexusteam.sdk.project.proguard.ManageProguardActivity;
import com.nexusteam.sdk.project.stringfog.ManageStringfogActivity;
import com.nexusteam.blacklogics.generator.source.SourceCodeGenerator;
import android.util.Log;
import java.net.URI;
import java.io.File;
import com.nexusteam.blacklogics.custom.LoadingDialog;
import com.nexusteam.blacklogics.utils.LoadingAsyncTask;
import com.nexusteam.blacklogics.interfaces.AsyncTaskListener;

import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.ViewGroup;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DesignActivity extends BaseActivity implements CompilerLogListener, CustomBlocksDialog.OnBlocksSelectedListener, Complex.SyncData, WidgetInteractionListener {
	
	private String _ad_unit_id;
	
	private Consumer<View> declareWidget;
	private Consumer<View> initializeWidget;
	private static Map<String, Map<String, List<String>>> variableMap = new HashMap<>();
	
	private int lastSpinnerPosition = -1;
	
	
	private int savedComponentPosition = 0; 
	
	public static View selectedWidget;
	
	private UiUpdateManager uiUpdateManager;
	
	private static final String TAG = "DesignActivity";
	
	private BuildSettings settings;
	
	private ArrayList<ActivityBean> activityList = new ArrayList<>();
	public ActivityBean activityBean;
	
	public static CardView widget_width;
	public static CardView widget_height;
	public static CardView widget_text;
	public static CardView widget_src;
	public static LinearLayout widgetpropertiesLinearLayout1;
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
	public static CardView widget_id;
	public static CardView widget_inject_attributes;
	public static CardView gravityLayout;
	public static CardView layoutGravity;
	public static CardView checkState;
	public static CardView switchCheckState;
	public static CardView progressStyle;
	public static CardView widget_scale;
	public static CardView max_progress;
	public static CardView widget_orientation;
	public static CardView widget_convert;
	public static CardView widget_weight;
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private DrawerLayout _drawer;
	private String sc_id = "";
	private String pkgName = "";
	public int pos = 0;
	public ViewEditorFragmentActivity viewEditor;
	private String varCode = "";
	private String varName = "";
	public Logger mLogger;
	private String scName = "";
	private String jsonLibraryPath = "";
	private String apkPath = "";
	private String baseMYSC = "";
	public ProjectBean projectBean;
	public static String scId;
	private boolean isViewLoading = false;
	public Dialog loadingDialog;
	public static List<ProjectActivityBean.ViewBean> currentLayoutWidgets;
	public static ArrayMap<String, List<ProjectActivityBean.ViewBean>> allWidgetsMap;
	public static String ROOT_VIEW_PATH;
	private String basePath = "";
	public static DesignActivity abc;
	public Context context;
	public static String projectPath;
	public static String defaultLayName = "main";
	private String xmlOutput = "";
	public int savedSpinnerPosition = 0;
	public boolean isMainActivity = true;
	public ProjectActivityManager projectActivityManager;
	public Complex complex;
	public ProjectActivityBean currentActivityBean;
	public String defaultAcName = "MainActivity";
	public TabAdapterFragmentAdapter adapter;
	public static ObjectAnimator anim = new ObjectAnimator();
	public String activityName = "MainActivity";
	public String layoutName = "main";
	private String projectPathBase = "";
	public static String sCId;
	public ActivityData activityData;
	public OnActivityNameChangeListener activityNameChangeListener;
	private DesignAdapterFragmentAdapter designAdapterFragmentAdapter;
	private SelectedActivityState currentState = new SelectedActivityState();
	private boolean isSpinnerUpdatingProgrammatically = false;
	
	private ArrayList<String> s = new ArrayList<>();
	
	private LinearLayoutCompat designToolbar;
	private LinearLayoutCompat toolbar_improved;
	private TabLayout tab_layout;
	private ViewPager customViewPager;
	private RecyclerView ah;
	private LinearLayoutCompat anchor;
	private MaterialCardView controlPanelCard;
	private MaterialCardView propertiesCard;
	private CircleImageView circleimageview1;
	public static LinearLayout linear18;
	private ImageView img_icon;
	private LinearLayoutCompat linear9;
	private LinearLayoutCompat linear10;
	private LinearLayoutCompat linear11;
	private Space linear16;
	private ImageView undoIcon;
	private Space linear14;
	private ImageView redoIcon;
	private Space linear17;
	private ImageView save_logic_button;
	private Space linear12;
	private ImageView img_more;
	private MaterialTextView tv_root_title;
	private MaterialTextView tv_sub_title;
	private LinearLayoutCompat control_panel;
	private TextInputLayout fileSpinnerLayout;
	private ImageView project_detail;
	private Space linear15;
	private MaterialButton execute_button;
	private AppCompatSpinner file_spinner;
	public static LinearLayoutCompat ll_properties;
	private DesignDrawer _drawer_drawer_layout_base_d;
	
	private InterstitialAd adS;
	private InterstitialAdLoadCallback _adS_interstitial_ad_load_callback;
	private FullScreenContentCallback _adS_full_screen_content_callback;
	private Intent i = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.design);
		initialize(_savedInstanceState);
		
		MobileAds.initialize(this);
		_ad_unit_id = "ca-app-pub-2903137488772866/3754424748";
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		abc = this;
		context = DesignActivity.this;
		viewEditor = new ViewEditorFragmentActivity();
		complex = new Complex();
		complex.setC(DesignActivity.this);
		complex.setId(getIntent().getStringExtra("sc_id"));
		complex.setActivityName(defaultAcName);
		_app_bar = findViewById(R.id._app_bar);
		_coordinator = findViewById(R.id._coordinator);
		_toolbar = findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		_drawer = findViewById(R.id._drawer);
		ActionBarDrawerToggle _toggle = new ActionBarDrawerToggle(DesignActivity.this, _drawer, _toolbar, R.string.app_name, R.string.app_name);
		_drawer.addDrawerListener(_toggle);
		_toggle.syncState();
		
		LinearLayout _nav_view = findViewById(R.id._nav_view);
		
		designToolbar = findViewById(R.id.designToolbar);
		toolbar_improved = findViewById(R.id.toolbar_improved);
		tab_layout = findViewById(R.id.tab_layout);
		customViewPager = findViewById(R.id.customViewPager);
		ah = findViewById(R.id.ah);
		anchor = findViewById(R.id.anchor);
		controlPanelCard = findViewById(R.id.controlPanelCard);
		propertiesCard = findViewById(R.id.propertiesCard);
		circleimageview1 = findViewById(R.id.circleimageview1);
		linear18 = findViewById(R.id.linear18);
		img_icon = findViewById(R.id.img_icon);
		linear9 = findViewById(R.id.linear9);
		linear10 = findViewById(R.id.linear10);
		linear11 = findViewById(R.id.linear11);
		linear16 = findViewById(R.id.linear16);
		undoIcon = findViewById(R.id.undoIcon);
		linear14 = findViewById(R.id.linear14);
		redoIcon = findViewById(R.id.redoIcon);
		linear17 = findViewById(R.id.linear17);
		save_logic_button = findViewById(R.id.save_logic_button);
		linear12 = findViewById(R.id.linear12);
		img_more = findViewById(R.id.img_more);
		tv_root_title = findViewById(R.id.tv_root_title);
		tv_sub_title = findViewById(R.id.tv_sub_title);
		control_panel = findViewById(R.id.control_panel);
		fileSpinnerLayout = findViewById(R.id.fileSpinnerLayout);
		project_detail = findViewById(R.id.project_detail);
		linear15 = findViewById(R.id.linear15);
		execute_button = findViewById(R.id.execute_button);
		file_spinner = findViewById(R.id.file_spinner);
		ll_properties = findViewById(R.id.ll_properties);
		_drawer_drawer_layout_base_d = _nav_view.findViewById(R.id.drawer_layout_base_d);
		
		img_icon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (!isAdapterReady()) {
					exibirMensagemEdt("Exit from this project", "Do you want to save before quitting?");
					return;
				}
				
				ViewBuilderFragmentActivity viewFragment = (ViewBuilderFragmentActivity) getFragmentFromViewPager(0);
				// Drawer check
				if (_drawer.isDrawerOpen(Gravity.RIGHT)) {
					_drawer.closeDrawer(Gravity.RIGHT);
					return;
				}
				
				// Properties check
				if (viewFragment.viewEditor.isHiddenProperties()) {
					int n = tab_layout.getSelectedTabPosition();
					if (n != 0) {
						tab_layout.getTabAt(n - 1).select();
						return;
					}
					exibirMensagemEdt("Exit from this project", "Do you want to save before quitting?");
					return;
				}
				
				// Agar properties visible hain, unhe hide karo
				viewFragment.viewEditor.hideProperties();
				
			}
		});
		
		undoIcon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				ViewBuilderFragmentActivity viewFragment =
				(ViewBuilderFragmentActivity) getFragmentFromViewPager(0);
				
				if (viewFragment != null) {
					viewFragment.viewEditor.undo();
				}
				
			}
		});
		
		redoIcon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				ViewBuilderFragmentActivity viewFragment = (ViewBuilderFragmentActivity) getFragmentFromViewPager(0);
				
				if (viewFragment != null) {
					viewFragment.viewEditor.redo();
				}
				
			}
		});
		
		save_logic_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_loading();
			}
		});
		
		img_more.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_drawer.openDrawer(Gravity.RIGHT);
			}
		});
		
		project_detail.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				PopupMenu popupMenu = new PopupMenu(DesignActivity.this, project_detail);
				Menu menu = popupMenu.getMenu();
				
				// TODO: Add nice title item(s) which are smaller, can't be selected, etc.
				menu.add(Menu.NONE, 1, Menu.NONE, "Build Settings");
				menu.add(Menu.NONE, 2, Menu.NONE, "Clean temporary files");
				menu.add(Menu.NONE, 3, Menu.NONE, "Show last compile error");
				menu.add(Menu.NONE, 5, Menu.NONE, "Show source code");
				if (FileUtil.isExistFile(apkPath)) {
					menu.add(Menu.NONE, 4, Menu.NONE, "Install last built APK");
				}
				if (pos == 0) {
					menu.add(Menu.NONE, 6, Menu.NONE, "Direct XML Editor");
				}
				
				
				popupMenu.setOnMenuItemClickListener(
				new PopupMenu.OnMenuItemClickListener() {
					
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						
						switch (item.getItemId()) {
							
							case 1:
							new BuildSettingsDialog(
							DesignActivity.this, sc_id
							).show();
							break;
							
							case 2:
							startCleanTempThread();
							break;
							
							case 3:
							new CompileErrorSaver(sc_id)
							.showLastErrors(DesignActivity.this);
							break;
							
							case 4:
							if (FileUtil.isExistFile(apkPath)) {
								installBuiltApk();
							} else {
								SketchwareUtil.toast(
								"APK doesn't exist anymore"
								);
							}
							break;
							
							case 5:
							showCurrentActivitySrcCode();
							break;
							
							case 6:
							directXMLEdit();
							break;
							
							default:
							return false;
						}
						return true;
					}
				}
				);
				
				popupMenu.show();
			}
		});
		
		execute_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				ViewBuilderFragmentActivity viewFragment = (ViewBuilderFragmentActivity) getFragmentFromViewPager(0);
				viewFragment.saveLayout();
				
				_processApk();
			}
		});
		
		_adS_interstitial_ad_load_callback = new InterstitialAdLoadCallback() {
			@Override
			public void onAdLoaded(InterstitialAd _param1) {
				
			}
			
			@Override
			public void onAdFailedToLoad(LoadAdError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		
		_adS_full_screen_content_callback = new FullScreenContentCallback() {
			@Override
			public void onAdDismissedFullScreenContent() {
				
			}
			
			@Override
			public void onAdFailedToShowFullScreenContent(AdError _adError) {
				final int _errorCode = _adError.getCode();
				final String _errorMessage = _adError.getMessage();
				
			}
			
			@Override
			public void onAdShowedFullScreenContent() {
				
			}
		};
	}
	
	private void initializeLogic() {
		context = DesignActivity.this;
		getSupportActionBar().hide();
		complex = new Complex();
		complex.setC(DesignActivity.this);
		complex.setId(getIntent().getStringExtra("sc_id"));
		complex.setActivityName(defaultAcName);
		complex.refreshDataWithListener(this);
		sc_id = getIntent().getStringExtra("sc_id");
		sCId = getIntent().getStringExtra("sc_id");
		projectPath = getIntent().getStringExtra("projectPath");
		ROOT_VIEW_PATH = FileUtil.getExternalStorageDir().concat("/.blacklogics/data/".concat(sc_id.concat("/root_view")));
		scId = getIntent().getStringExtra("sc_id");
		pkgName = getIntent().getStringExtra("pkgName");
		varCode = getIntent().getStringExtra("varCode");
		varName = getIntent().getStringExtra("varName");
		scName = getIntent().getStringExtra("scName");
		getSupportActionBar().setTitle(scName);
		getSupportActionBar().setSubtitle(sc_id);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		jsonLibraryPath = FileUtil.getExternalStorageDir().concat("/.blacklogics/data/".concat(sc_id.concat("/local_library")));
		apkPath = FileUtil.getExternalStorageDir().concat("/.blacklogics/mysc/".concat(sc_id.concat("/bin/".concat(scName + ".apk"))));
		baseMYSC = FileUtil.getExternalStorageDir().concat("/.blacklogics/mysc/".concat(sc_id.concat("/")));
		basePath = FileUtil.getExternalStorageDir().concat("/.blacklogics/data/".concat(sc_id));
		com.besome.blacklogics.project.ProjectDataHelper.setScId(this, sc_id);
		com.besome.blacklogics.project.ProjectDataHelper.setActivityName(this, defaultAcName);
		LinearLayout _nav_view = (LinearLayout) findViewById(R.id._nav_view);
		androidx.drawerlayout.widget.DrawerLayout
		.LayoutParams lp = new androidx.drawerlayout.widget.DrawerLayout
		.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		lp.gravity=Gravity.RIGHT;
		_nav_view.setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));
		_nav_view.setLayoutParams(lp);
		projectPathBase = FileUtil.getExternalStorageDir() + "/.blacklogics/data/" + sc_id;
		activityData = new ActivityData("MainActivity.java", "main.xml", true);
		activityData.setActivityName("MainActivity");
		_initUI();
		tv_root_title.setText(getIntent().getStringExtra("scName"));
		tv_sub_title.setText(getIntent().getStringExtra("sc_id"));
		_setupTabs();
		currentActivityBean = new ProjectActivityBean(
		defaultAcName,
		defaultLayName,
		pkgName,
		isMainActivity,
		sc_id,
		scName
		);
		
		currentActivityBean.setUseAndroidX(complex.getAndroidXEnable());
		currentActivityBean.setScId(sc_id);
		activityBean = new ActivityBean("MainActivity", "main");
		activityList.add(activityBean);
		complex.setSpinnerAdapter(file_spinner);
		File logicFile = new File(basePath, ".blacklogics/data/" + sc_id + "/root_logic");
		if (logicFile.isFile() && logicFile.length() > 0) {
			complex.extractAllLogicsFromJson(logicFile.getAbsolutePath());
		}
		
		settings = new BuildSettings(sc_id);
		projectBean = new ProjectBean();
		projectBean.screens.add(complex.getAllJavaActivity());
		if (tab_layout.getSelectedTabPosition() == 0) {
			complex.setXmlAdapter(file_spinner);
			pos = 0;
		} else {
			complex.setJavaAdapter(file_spinner);
			pos = 1;
		}
		mLogger = new Logger();
		mLogger.attach(ah);
		initializeActivityList();
		_ensureJavaCode("BlackLogicsUtil", Lx.i(pkgName));
		_setupSpinnerListener();
		_setupViewPagerListener();
		uiUpdateManager = new UiUpdateManager(undoIcon, redoIcon, customViewPager);
		_updateUndoRedoIcons();
		_setCurrentActivity("MainActivity");
	}
	
	@Override
	public void onBackPressed() {
		try {
			
			ViewBuilderFragmentActivity viewFragment = (ViewBuilderFragmentActivity) getFragmentFromViewPager(0);
			
			if (viewFragment == null || viewFragment.viewEditor == null) {
				super.onBackPressed();
				return;
			}
			
			if (_drawer != null && _drawer.isDrawerOpen(Gravity.RIGHT)) {
				_drawer.closeDrawer(Gravity.RIGHT);
				return;
			}
			
			if (viewFragment.viewEditor.isHiddenProperties()) {
				int n = tab_layout.getSelectedTabPosition();
				if (n != 0) {
					tab_layout.getTabAt(n - 1).select();
					return;
				}
				
				exibirMensagemEdt(
				"Exit from this project",
				"Do you want to save before quitting?"
				);
				return;
			}
			
			viewFragment.viewEditor.unselectSelectedWidget();
			viewFragment.viewEditor.hideProperties();
			
		} catch (Exception e) {
			exibirMensagemEdt(
			"Exit from this project",
			"Do you want to save before quitting?"
			);
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.design_toolbar_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		final int _id = item.getItemId();
		final String _title = (String) item.getTitle();
		switch (item.getItemId()) {
			
			case android.R.id.home:
			finish();
			return true;
			
			case R.id.action_undo:
			ViewBuilderFragmentActivity viewFragmentQ =
			(ViewBuilderFragmentActivity) getFragmentFromViewPager(0);
			
			if (viewFragmentQ != null) {
				viewFragmentQ.viewEditor.undo();
			}
			
			return true;
			
			case R.id.action_redo:
			ViewBuilderFragmentActivity viewFragment =
			(ViewBuilderFragmentActivity) getFragmentFromViewPager(0);
			
			if (viewFragment != null) {
				viewFragment.viewEditor.redo();
			}
			
			return true;
			
			case R.id.action_save:
			_loading();
			return true;
			
			case R.id.action_more:
			_drawer.openDrawer(Gravity.RIGHT);
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString("currentActivity", defaultAcName);
		outState.putString("currentLayout", defaultLayName);
		outState.putString("sc_id", sc_id);
		outState.putInt("currentTab", tab_layout.getSelectedTabPosition());
		outState.putParcelable("selectedState", currentState);
		outState.putString("activityName", activityBean.getActivityName());
		outState.putString("layoutName", activityBean.getLayoutName());
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		defaultAcName = savedInstanceState.getString("currentActivity");
		defaultLayName = savedInstanceState.getString("currentLayout");
		sc_id = savedInstanceState.getString("sc_id");
		int tabPos = savedInstanceState.getInt("currentTab", 0);
		tab_layout.getTabAt(tabPos).select();
		_setCurrentActivity(savedInstanceState.getString("activityName"));
		// Re-init if needed
		if (activityBean == null) {
			initializeActivityList(); // Re-load if null
		}
		SelectedActivityState saved = savedInstanceState.getParcelable("selectedState");
		if (saved != null) currentState = saved;
		
		super.onRestoreInstanceState(savedInstanceState);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		uiUpdateManager.cleanup();
		generateJavaCode();
	}
	public void _b() {
	}
	/**
TUDO ; HELPER showCreateActivityDialog
**/
	public void yq(boolean yes, String no) {
		new CreateActivityDialogFragment(this, sc_id, complex, yes, no)
		.show(getSupportFragmentManager(), "create_activity_dialog");
	}
	
	public boolean saveView() {
		
		try {
			ViewBuilderFragmentActivity activity = (ViewBuilderFragmentActivity) getFragmentFromViewPager(0);
			
			if (activity == null) {
				return false;
			}
			
			if (ViewBuilderFragmentActivity.instance != null
			&& ViewBuilderFragmentActivity.instance.viewEditor != null) {
				
				ViewBuilderFragmentActivity.instance.saveLayout();
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void exibirMensagemEdt(String titulo, String texto) {
		LayoutInflater inflater = LayoutInflater.from(this);  
		final View v = inflater.inflate(R.layout.custom_dialog, null);  
		AlertDialog.Builder builer = new AlertDialog.Builder(this); 
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
				finish();
			} 
		}); 
		btnSave.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {  
				alert.cancel();
				FragmentManager fragmentManager = getSupportFragmentManager();
ViewBuilderFragmentActivity activity = (ViewBuilderFragmentActivity) fragmentManager
    .findFragmentByTag("android:switcher:" + customViewPager.getId() + ":0");
				if (activity != null && activity.viewEditor != null) {
					activity.saveLayout();
				}
				TheBlockLogicsUtil.showToast(getApplicationContext(),"Projeto Saved!");
				finish();
			}
		});
	}
	
	/**
 * Set the selected item in a standard Spinner
 * @param spinner The Spinner to set the selection for
 * @param value The value to select
 */
	private void setSpinnerSelection(Spinner spinner, String value) {
		SpinnerAdapter adapter = spinner.getAdapter();
		if (adapter != null) {
			for (int i = 0; i < adapter.getCount(); i++) {
				if (adapter.getItem(i).toString().equals(value)) {
					spinner.setSelection(i);
					break;
				}
			}
		}
	}
	
	public void showCurrentActivitySrcCode() {
		switch (tab_layout.getSelectedTabPosition()) {
			case 0:
			SourceCodeDialog.show(DesignActivity.this, getXmlCode());
			break;
			case 1:
			SourceCodeDialog.show(DesignActivity.this, getJavaCode());
			break;
			
			case 2:
			SourceCodeDialog.show(DesignActivity.this, getJavaCode());
			break;   
		}
	}
	
	public void setCurrentProjectScreen(String screenName) {
		projectBean.setCurrentScreen(screenName);
		android.util.Log.d("ScreenChange", "Switched to: " + projectBean.getCurrentScreen());
	}
	private String convertToActivityName(String input) {
		if (input == null || input.isEmpty()) {
			return input;
		}
		
		StringBuilder result = new StringBuilder();
		boolean capitalizeNext = true;
		
		for (char c : input.toCharArray()) {
			if (c == '_') {
				capitalizeNext = true;
			} else {
				if (capitalizeNext) {
					result.append(Character.toUpperCase(c));
					capitalizeNext = false;
				} else {
					result.append(Character.toLowerCase(c));
				}
			}
		}
		
		// Append the appropriate activity suffix
		return result.toString() + (ViewEditorFragmentActivity.useAndroidX ? "AppCompatActivity" : "Activity");
	}
	private void showCustomSpinnerDialog() {
		new CustomSpinnerDialog(this, sc_id, complex).show();
	}
	
	public void handleItemSelection(final ViewItem item) {
		
		/*   if (DesignActivity.abc != null) {
complex.setXmlCode(activityBean.getLayoutName(), getXmlCode());
complex.setJavaCode(activityBean.getActivityName(), getJavaCode());
}
*/	
		if (!isAdapterReady()) {
			return;
		}
		
		final ViewBuilderFragmentActivity viewFragment =
		(ViewBuilderFragmentActivity) getFragmentFromViewPager(0);
		
		if (viewFragment == null) return;
		
		final String newXml = item.getXmlFileName();
		final String newJava = item.getJavaName();
		
		boolean layoutChanged = !newXml.equals(viewFragment.layoutName);
		boolean activityChanged = !newJava.equals(viewFragment.activityName);
		
		// Clear IDs only if layout changed
		if (layoutChanged) {
			viewFragment.viewEditor.idManager.clearAllIds();
		}
		
		if (!newXml.isEmpty() && layoutChanged) {
			updateLayoutName(viewFragment, item);
		}
		
		if (!newJava.isEmpty() && activityChanged) {
			updateActivityName(viewFragment, item);
		}
		
		// Reload only if layout actually changed
		if (layoutChanged) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					viewFragment.loadLayout();
				}
			});
		}
		
		if (ComponentFragmentActivity.componentFragmentActivity != null) {
			ComponentFragmentActivity.componentFragmentActivity.refreshUi();
		}
		
		if (EventFragmentActivity.eventFragmentActivity != null) {
			EventFragmentActivity.eventFragmentActivity.displaySelectedEvents();
		}
		
		boolean isMain = "MainActivity".equalsIgnoreCase(newJava);
		isMainActivity = isMain;
		currentActivityBean.setMainActivity(isMain);
		
		// Add listener only once (make sure you implement hasListener())
		if (!activityBean.hasListener()) {
			activityBean.addListener(new ActivityBean.OnChangeListener() {
				@Override
				public void onChanged(ActivityBean updated) {
					if (viewFragment != null) {
						viewFragment.viewEditor.tv_view_name
						.setText(item.getXmlName() + ".xml");
					}
				}
			});
		}
		
		// Spinner update only if needed
		if (file_spinner != null && file_spinner.getAdapter() != null) {
			String currentSelection = (String) file_spinner.getSelectedItem();
			
			if (currentSelection != null &&
			!currentSelection.equals(item.getXmlName())) {
				
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						setSpinnerSelection(file_spinner, item.getXmlName());
					}
				});
			}
		}
		
		// handleItemSelection ke end mein yeh add karo
		currentState = new SelectedActivityState(
		item.getXmlName(),
		item.getXmlFileName(),
		item.getJavaName(),
		item.getJavaFileName(),
		savedXmlPosition,
		savedJavaPosition
		);
	}
	
	private void updateActivityName(ViewBuilderFragmentActivity viewFragment, ViewItem item) {
		viewFragment.activityName = item.getJavaName();
		EventFragmentActivity.myData = item.getJavaName();
		currentActivityBean.setActivityName(item.getJavaName());
		defaultAcName = item.getJavaName();
		activityName = item.getJavaName();
		activityData.setActivityName(item.getJavaName());
		
		_setCurrentActivity(item.getJavaName());
		complex.setActivityName(defaultAcName);
		activityBean.setActivityName(item.getJavaName());
		com.besome.blacklogics.project.ProjectDataHelper.setActivityName(this, item.getJavaName());
		
		setCurrentActivityName(item.getJavaName());
		if (activityNameChangeListener != null) {
			activityNameChangeListener.onActivityNameChanged(item.getJavaName());
		}
	}
	
	private void updateLayoutName(ViewBuilderFragmentActivity viewFragment, ViewItem item) {
		final String xmlFileName = item.getXmlFileName();
		final String xmlName = item.getXmlName();
		
		viewFragment.layoutName = xmlFileName;
		defaultLayName = layoutName = xmlName;
		viewFragment.viewEditor.tv_view_name.setText(xmlFileName); // UI अपडेट
		setCurrentLayoutName(xmlName);
		
		if (activityNameChangeListener != null) {
			activityNameChangeListener.onLayoutNameChanged(xmlName);
		}
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				currentActivityBean.setLayoutName(xmlName);
				activityBean.setLayoutName(xmlName);
				activityData.setLayoutName(xmlFileName);
			}
		}).start();
		
	}
	
	private void showCreateCustomViewDialog() {
		new CreateCustomViewDialog(this, complex).show();
	}
	
	public void showCustomBlocksDialog() {
		CustomBlocksDialog dialog = CustomBlocksDialog.newInstance(sc_id);
		dialog.show(getSupportFragmentManager(), "CustomBlocksDialog");
	}
	
	@Override
	public void onBlocksSelected(List<CustomBlocksDialog.Block> selectedBlocks) {
		if (selectedBlocks == null || selectedBlocks.isEmpty()) {
			showMessage("No blocks selected");
			return;
		}
		
		try {
			File blocksFile = new File(getProjectBlocksPath());
			JSONArray existingBlocks = new JSONArray();
			
			if (blocksFile.exists()) {
				String existingJson = FileUtil.readFile(blocksFile.getAbsolutePath());
				if (!existingJson.isEmpty()) {
					existingBlocks = new JSONArray(existingJson);
				}
			}
			
			JSONObject myBlocks = null;
			for (int i = 0; i < existingBlocks.length(); i++) {
				JSONObject cat = existingBlocks.getJSONObject(i);
				if ("My Blocks".equals(cat.getString("name"))) {
					myBlocks = cat;
					break;
				}
			}
			if (myBlocks == null) {
				myBlocks = new JSONObject();
				myBlocks.put("name", "My Blocks");
				myBlocks.put("color", "#2196F3");
				myBlocks.put("blocks", new JSONArray());
				existingBlocks.put(myBlocks);
			}
			
			JSONArray blocksArray = myBlocks.getJSONArray("blocks");
			for (CustomBlocksDialog.Block block : selectedBlocks) {
				JSONObject blockObj = new JSONObject();
				blockObj.put("id", block.id);
				blockObj.put("type", block.type);
				blockObj.put("views", block.views);
				blockObj.put("code", block.code);
				blockObj.put("color", block.color);
				if (!block.tty.isEmpty()) {
					blockObj.put("tty", block.tty);
				}
				blocksArray.put(blockObj);
			}
			
			FileUtil.writeFile(blocksFile.getAbsolutePath(), existingBlocks.toString(2));
			showMessage("Custom blocks imported successfully");
			
			refreshBlocksView();
			
		} catch (Exception e) {
			e.printStackTrace();
			showMessage("Error importing blocks: " + e.getMessage());
		}
	}
	
	private String getProjectBlocksPath() {
		return FileHandler.getPettlePath() + "/blocks.json";
	}
	
	private void refreshBlocksView() {
		// Implement this to refresh your blocks UI
		// Example: notifyAdapter();
	}
	
	
	public void installBuiltApk() {
		String PATH = apkPath;
		java.io.File file = new java.io.File(PATH);
		if (file.exists()) {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(uriFromFile(this, new java.io.File(PATH)), "application/vnd.android.package-archive");
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			try {
				startActivity(intent);
			} catch (ActivityNotFoundException e) {
				e.printStackTrace();
				android.util.Log.e("TAG", "Error in opening the file!");					
			}
		} else {
			Toast.makeText(this, "APK not found", Toast.LENGTH_LONG).show();				        
		}
	}
	
	Uri uriFromFile(Context context, java.io.File file) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			return androidx.core.content.FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file); 
		} else {
			return Uri.fromFile(file);
		}
	}
	@Override
	public void onErrorLog(String errorLog) {
		// showMessage(errorLog);
		new CompileErrorSaver(sc_id).writeLogsToFile(errorLog);
		_q(errorLog);
	}
	@Override
	public void onLog(String log) {
		/*runOnUiThread(() -> {
            // Handle regular logs
            Log.d("BUILD_LOG", log);
        });*/
	}
	@Override
	public void onDataRefreshed() {
		//Toast.makeText(this, "Data (XML, Java, Permissions) refreshed successfully!", Toast.LENGTH_SHORT).show();
		// Update UI or other components as needed
	}
	
	@Override
	public void onRefreshFailed(String errorMessage) {
		//	Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
	}
	
	public void directXMLEdit() {
		Intent intent = new Intent(getApplicationContext(), com.besome.blacklogics.activities.ViewCodeEditorActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		intent.putExtra("activityBean", currentActivityBean);
		intent.putExtra("layoutName", ViewEditorFragmentActivity.layoutName);
		intent.putExtra("pkgName", pkgName);
		intent.putExtra("projectPath", getIntent().getStringExtra("projectPath"));
		startActivity(intent);
	}
	private void showLoadingDialog(Activity activity) {
		try {
			if (loadingDialog == null) {
				loadingDialog = new Dialog(activity);
				loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				loadingDialog.setCancelable(false);
				loadingDialog.setContentView(R.layout.loading);
				
				if (loadingDialog.getWindow() != null) {
					loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
					loadingDialog.getWindow().setLayout(
					WindowManager.LayoutParams.MATCH_PARENT,
					WindowManager.LayoutParams.MATCH_PARENT
					);
				}
			}
			
			if (!loadingDialog.isShowing()) {
				loadingDialog.show();
			}
		} catch (Exception e) {
			Log.e("SaveProject", "Error showing loading dialog", e);
		}
	}
	
	/**
 * Dismisses the loading dialog safely.
 */
	private void dismissLoadingDialog() {
		try {
			if (loadingDialog != null && loadingDialog.isShowing()) {
				loadingDialog.dismiss();
			}
		} catch (Exception e) {
			Log.e("SaveProject", "Error dismissing loading dialog", e);
		}
	}
	
	/**
 * Shows a Toast safely even if called from any thread.
 */
	private void showToastSafe(final Activity activity, final String message) {
		if (activity == null || activity.isFinishing() || activity.isDestroyed()) return;
		
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				try {
					Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					Log.e("SaveProject", "Error showing toast", e);
				}
			}
		});
	}
	
	@Deprecated
	public void qr(final String _s) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
			}
		});
	}
	private void saveWidgetsToFile() {
		try {
			Gson gson = new Gson();
			JSONObject wrapper = new JSONObject();
			wrapper.put("activityName", ViewEditorFragmentActivity.activityName);
			wrapper.put("layoutName", ViewEditorFragmentActivity.layoutName);
			wrapper.put("widgets", new JSONArray(gson.toJson(currentLayoutWidgets)));
			String json = wrapper.toString();
			String savePath = String.format(ROOT_VIEW_PATH, sc_id);
			FileUtil.writeFile(savePath, json);
		} catch (Exception e) {
			//  TheBlockLogicsUtil.showToast(getApplicationContext(), "Error saving widgets: " + e.toString());
		}
	}
	
	/**
TUDO : EXTRA MATHODS FOR MORE BEST DEVELOPING THIS ALL MATHODS ME ADDED
**/
	public static void saveBlockLogic(String activityName, String logic) {
	}
	
	private String getBlockLogicForWidget(String widgetId) {
		return "";
	}
	
	public static boolean isBlockLogicAvailable(String activityName, String widgetId) {
		return false;
	}
	public static void saveBlockLogicForWidget(String activityName, String widgetId, String logic) {
	}
	private String getBlockLogic(String widgetId) {
		try {
			String blockLogicPath = projectPath + "/block_logic/project_logic.json";
			if (FileUtil.isExistFile(blockLogicPath)) {
				String encodedJson = FileUtil.readFile(blockLogicPath);
				String decodedJson = new String(Base64.decode(encodedJson, Base64.DEFAULT));
				Type mapType = new TypeToken<Map<String, Map<String, String>>>(){}.getType();
				Map<String, Map<String, String>> logicMap = new Gson().fromJson(decodedJson, mapType);
				Map<String, String> activityLogic = logicMap.get(currentActivityBean.getActivityName());
				if (activityLogic != null) {
					return activityLogic.getOrDefault(widgetId, "");
				}
			}
		} catch (Exception e) {
			// TheBlockLogicsUtil.showToast(TheBlockLogicsUtil.getContext(), "Error loading block logic: " + e.toString());
		}
		return "";
	}
	private void copyAssets() {
		AssetCopyUtil.copyAssetsToExternalStorage(this, "icon", ".blacklogics/mysc/" + sc_id + "/app/src/main/res/mipmap/");
		AssetCopyUtil.copyAssetsToExternalStorage(this, "default_image", ".blacklogics/mysc/" + sc_id + "/app/src/main/res/drawable-xhdpi/");
	}
	
	public static boolean isToolbarEnabled(String acName) {
		Complex c= new Complex();
		//	c.setC(DesignActivity.this);
		//c.setId(getIntent().getStringExtra("sc_id"));
		return c.isToolbarEnabled(acName);
	}    
	public static boolean isEnableFab(String activityName) {
		Complex c= new Complex();
		//	c.setC(DesignActivity.this);
		//c.setId(getIntent().getStringExtra("sc_id"));
		return c.getEnableFabBoolean(activityName);
	}
	
	private boolean isValidInput(String input) {
		return input.matches("^[a-z_][a-z0-9_]*$");
	}
	
	private String formatToCamelCase(String input) {
		StringBuilder result = new StringBuilder();
		String[] parts = input.split("_");
		for (String part : parts) {
			if (!part.isEmpty()) {
				result.append(part.substring(0, 1).toUpperCase())
				.append(part.substring(1));
			}
		}
		return result.toString();
	}
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
	public static void saveComponentLogic(String activityName, String componentName, String fieldName) {
		Qf.saveComponentLogic(activityName, componentName, fieldName);
	}
	public static List<HashMap<String, String>> loadComponentLogic(String activityName) {
		return Qf.loadComponentLogic(activityName);
	}
	public static List<HashMap<String, String>> loadIntentComponents(String activityName) {
		return Qf.loadComponentFromName(activityName, "Intent");
	}
	public static List<HashMap<String, String>> loadCalendarComponents(String activityName) {
		return Qf.loadComponentFromName(activityName, "Calendar");
	}
	public static void removeComponentLogic(String activityName, String componentName, String fieldName) {
		Qf.removeComponentLogic(activityName, componentName, fieldName);
	}
	private String getImageScaleType(String scaleType) {
		if (scaleType == null || scaleType.equals("fitCenter")) {
			return null; // No need to add to XML
		}
		
		// Map scale types to valid XML values
		switch (scaleType) {
			case "fitXY":
			case "centerCrop":
			case "centerInside":
			case "fitStart":
			case "fitEnd":
			case "center":
			case "matrix":
			return scaleType; // Already valid XML values
			default:
			return "fitCenter"; // fallback
		}
	}
	public static void saveVariable(String activityName, String componentName, String fieldName) {
		Qf.saveVariable(activityName, componentName, fieldName);
	}
	public static Map<String, List<String>> getVariables(String activityName) {
		Map<String, List<String>> vars = variableMap.get(activityName);
		if (vars == null) {
			vars = new HashMap<String, List<String>>();
		}
		return vars;
	}
	
	public static List<String> getVariable(String activityName, String varType) {
		// Get the map for the activity
		Map<String, List<String>> activityVars = variableMap.get(activityName);
		if (activityVars == null) {
			activityVars = new HashMap<String, List<String>>();
		}
		
		// Get the list for the variable type
		List<String> vars = activityVars.get(varType);
		if (vars == null) {
			vars = new ArrayList<String>();
		}
		
		return vars;
	}
	
	public static void removeVariable(String activityName, String componentName, String fieldName) {
		Qf.removeVariable(activityName, componentName, fieldName);
	}
	
	public static List<HashMap<String, String>> loadVariableLogic(String activityName) {
		return Qf.loadVariableLogic(activityName);
	}
	/**
 * Saves a function declaration for a specific activity.
 * @param activityName The name of the activity to associate the function with.
 * @param functionName The name of the function (e.g., "calculateSum").
 * @param returnType The return type of the function (e.g., "int", "void", "String").
 * @param parameters A list of parameter types and names (e.g., [{"type": "int", "name": "a"}, {"type": "int", "name": "b"}]).
 */
	public static void addFunction(String activityName, String functionName, String returnType, List<HashMap<String, String>> parameters) {
	}
	
	/**
 * Loads all function declarations for a specific activity.
 * @param activityName The name of the activity.
 * @return A list of function metadata, each containing functionName, returnType, and parameters.
 */
	public static List<HashMap<String, Object>> loadFunctions(String activityName) {
		return null;
	}
	
	/**
 * Removes a function declaration from a specific activity.
 * @param activityName The name of the activity.
 * @param functionName The name of the function to remove.
 */
	public static void removeFunction(String activityName, String functionName) {
	}
	public static List<HashMap<String, String>> loadComponentFromName(String activityName, String componentName) {
		return Qf.loadComponentFromName(activityName, componentName);
	}
	public static void saveBlockLogicForEvent(String activityName, String widgetId, String logic) {
		Qf.saveBlockLogicForEvent(activityName, widgetId, logic);
	}
	public static String getBlockLogics(String activityName) {
		return ""; // Agar kuch bhi na mile
	}
	
	private String getBlockLogicForEvent(String widgetId) {
		return "";
	}
	public static void saveWidgetInfo(String activityName, String widgetType, String widgetId) {
	}
	public static Map<String, List<String>> getWidgetInfoForActivity(String activityName) {
		return null;
	}
	public static List<String> getWidgetIds(String activityName, String widgetType) {
		return null;
	}
	
	public interface WidgetInteractionListener {
		void onWidgetClicked(View widget);
		void onWidgetLongClicked(View widget);
		void onWidgetSelected(View widget);
		void onWidgetDeselected();
	}
	
	@Override
	public void onWidgetClicked(View widget) {
		Log.d(TAG, "DesignActivity: Widget clicked - " + widget.getClass().getSimpleName());
		WidgetPropertyManager.selectWidget(widget);
	}
	
	/**
* ✅ WIDGET LONG CLICK LISTENER IMPLEMENTATION
*/
	@Override
	public void onWidgetLongClicked(View widget) {
		Log.d(TAG, "DesignActivity: Widget long clicked - " + widget.getClass().getSimpleName());
		/* TUDO: NOT NEED */
	}
	
	/**
* ✅ WIDGET SELECTION LISTENER IMPLEMENTATION
*/
	@Override
	public void onWidgetSelected(View widget) {
		Log.d(TAG, "DesignActivity: Widget selected - " + widget.getClass().getSimpleName());
		
	}
	
	/**
* ✅ WIDGET DESELECTION LISTENER IMPLEMENTATION
*/
	@Override
	public void onWidgetDeselected() {
		Log.d(TAG, "DesignActivity: Widget deselected");
		WidgetPropertyManager.unselectSelectedWidget();
	}
	
	
	@Override
	protected boolean isAdapterReady() {
		
		if (customViewPager == null) return false;
		
		if (customViewPager.getAdapter() == null) return false;
		if (customViewPager.getAdapter().getCount() == 0) return false;
		
		Fragment f = getFragmentFromViewPager(customViewPager.getCurrentItem());
		return f != null && f.isAdded();
	}
	
	private void initializeActivityList() {
		activityList.clear();
		loadSavedActivities();
		// Check if MainActivity already exists
		boolean mainExists = false;
		for (ActivityBean act : activityList) {
			if (act.getActivityName().equals("MainActivity")) {
				mainExists = true;
				activityBean = act; // Set here if found
				break;
			}
		}
		if (!mainExists) {
			activityBean = new ActivityBean("MainActivity", "main");
			activityList.add(activityBean);
		}
	}
	public void addActivity(String activityName, String layoutName) {
		ActivityBean newActivity = new ActivityBean(activityName, layoutName);
		activityList.add(newActivity);
		saveActivities();
	}
	public ActivityBean getCurrentActivityBean() {
		return new ActivityBean(activityName, layoutName);
	}
	private void saveActivities() {
		try {
			Gson gson = new Gson();
			String json = gson.toJson(activityList);
			
			String encoded = Base64.encodeToString(json.getBytes(), Base64.DEFAULT);
			FileUtil.writeFile(getActivitiesFilePath(), encoded);
		} catch (Exception e) {
			Log.e("DesignActivity", "Error saving activities: " + e.getMessage());
		}
	}
	
	private void loadSavedActivities() {
		try {
			String filePath = getActivitiesFilePath();
			if (FileUtil.isExistFile(filePath)) {
				String encoded = FileUtil.readFile(filePath);
				String json = new String(Base64.decode(encoded, Base64.DEFAULT));
				Gson gson = new Gson();
				Type listType = new TypeToken<ArrayList<ActivityBean>>(){}.getType();
				ArrayList<ActivityBean> savedActivities = gson.fromJson(json, listType);
				if (savedActivities != null && !savedActivities.isEmpty()) {
					activityList.addAll(savedActivities);
				}
			}
		} catch (Exception e) {
			Log.e("DesignActivity", "Error loading activities: " + e.getMessage());
			
			activityBean = new ActivityBean("MainActivity", "main");
			activityList.add(activityBean);
		}
	}
	
	/**
 * Activities file path
 */
	private String getActivitiesFilePath() {
		return FileUtil.getExternalStorageDir() + "/.blacklogics/data/" + sc_id + "/base_activities.json";
	}
	
	/**
 * Activity spinner update करें
 */
	private void updateActivitySpinner() {
		if (file_spinner != null && complex != null) {
			complex.setSpinnerAdapter(file_spinner);
		}
	}
	private Map<String, String> createMainActivityAttributes(String themeName) {
		Map<String, String> attrs = new HashMap<>();
		attrs.put("exported", "true");
		attrs.put("launchMode", "singleTop");
		attrs.put("configChanges", "orientation|keyboardHidden|screenSize");
		attrs.put("windowSoftInputMode", "adjustResize");
		
		// Set theme dynamically based on toolbar/status bar selection
		attrs.put("theme", "@style/" + themeName);
		
		return attrs;
	}
	public static String getScId() {
		return scId;
	}
	public String getXmlCode() {
		try {
			if (!isAdapterReady()) {
				return "Failed to generate xml code.";
			}
			
			ViewBuilderFragmentActivity viewFragment = (ViewBuilderFragmentActivity) getFragmentFromViewPager(0);
			
			String formattedXml = "";
			
			if (viewFragment.viewEditor != null) {
				formattedXml = viewFragment.viewEditor.getXMLCode();
			}
			
			if (formattedXml == null || formattedXml.trim().isEmpty()) {
				return "Failed to generate XML layout. Please check your design.";
			}
			
			return formattedXml;
			
		} catch (Exception e) {
			e.printStackTrace();
			return "Error generating XML: " + e.getMessage();
		}
	}
	
	private Fragment getFragmentFromViewPager(int position) {
		if (designAdapterFragmentAdapter == null) return null;
		return designAdapterFragmentAdapter.getFragment(position);
	}
	public class DesignAdapterFragmentAdapter extends FragmentStatePagerAdapter {
		
		private final SparseArray<Fragment> fragmentMap = new SparseArray<>();
		private int tabCount = 0;
		
		public DesignAdapterFragmentAdapter(FragmentManager fm) {
			super(fm);
		}
		
		public void setTabCount(int count) {
			this.tabCount = count;
			notifyDataSetChanged();
		}
		
		@Override
		public int getCount() {
			return tabCount;
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
				case 0:
				return "View";
				case 1:
				return "Event";
				case 2:
				return "Component";
				default:
				return "View";
			}
		}
		
		@Override
		public Fragment getItem(int position) {
			if (position == 0) return new ViewBuilderFragmentActivity();
			if (position == 1) return new EventFragmentActivity();
			if (position == 2) return new com.nexusteam.internal.editor.ComponentListFragment();
			return new Fragment();
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			Fragment fragment = (Fragment) super.instantiateItem(container, position);
			fragmentMap.put(position, fragment);
			return fragment;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			fragmentMap.remove(position);
			super.destroyItem(container, position, object);
		}
		
		public Fragment getFragment(int position) {
			return fragmentMap.get(position);
		}
	}
	
	private void startCleanTempThread() {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				FileUtil.deleteFile(baseMYSC);
				
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						SketchwareUtil.toast(
						"Done cleaning temporary files!"
						);
					}
				});
			}
		}).start();
	}
	
	{
	}
	
	
	public void _processApk() {
		// Clean up existing directories
		String projectRoot = FileUtil.getExternalStorageDir() + "/.blacklogics/mysc/" + sc_id;
		String projectBase = FileUtil.getExternalStorageDir() + "/.blacklogics/data/" + sc_id;
		String binPath = projectRoot + "/bin";
		String genPath = projectRoot + "/gen";
		String proguardPath = projectBase + "/proguard-rules.pro";
		
		if (FileUtil.isExistFile(binPath)) {
			FileUtil.deleteFile(binPath);
			mLogger.d("ProjectSetup", "Deleted bin directory: " + binPath);
		}
		if (FileUtil.isExistFile(genPath)) {
			FileUtil.deleteFile(genPath);
			mLogger.d("ProjectSetup", "Deleted gen directory: " + genPath);
		}
		if (FileUtil.isExistFile(projectRoot + "/")) {
			// FileUtil.deleteFile(projectRoot + "/");
			mLogger.d("ProjectSetup", "Keeping project root: " + projectRoot);
		}
		
		complex = new Complex();
		complex.setId(sc_id);
		complex.setPkgName(pkgName);
		ProjectBuilder builder = new ProjectBuilder(this, complex, FileUtil.getExternalStorageDir(), sc_id);
		builder.setContext(this);
		builder.buildProject();
		SystemLogPrinter.start(mLogger);
		
		// Set up Project
		Project project = new Project();
		project.setId(sc_id);
		project.setPackageName(pkgName);
		String dexer = settings.getValue(BuildSettings.SETTING_DEXER, BuildSettings.SETTING_DEXER_DX);
		
		if (BuildSettings.SETTING_DEXER_R8.equals(dexer)) {
			project.getBuildSettings().setDexCompilerType(DexCompilerType.R8);
		} else if (BuildSettings.SETTING_DEXER_D8.equals(dexer)) {
			project.getBuildSettings().setDexCompilerType(DexCompilerType.D8);
		}
		
		// Set working directory
		File workingDir = new File(projectRoot, "build_temp"); 
		if (!workingDir.exists() && !workingDir.mkdirs()) {
			mLogger.e("ProjectSetup", "Failed to create working directory: " + workingDir.getAbsolutePath());
			throw new RuntimeException("Failed to create working directory");
		}
		project.setWorkingDirectory(workingDir);
		mLogger.d("ProjectSetup", "Set working directory: " + workingDir.getAbsolutePath());
		
		// Set libraries
		project.setLibraries(Library.fromFile(new File(projectRoot + "/app/libs/")));
		
		// Set project name
		project.setProjectName(scName);
		
		// Set ProGuard file
		project.setProguardFile(new File(proguardPath));
		
		List<File> resourceDirs = new ArrayList<>();
		resourceDirs.add(new File(FileUtil.getExternalStorageDir() + "/.blacklogics/data/" + sc_id + "/files/resource/"));
		resourceDirs.add(new File(projectRoot + "/app/src/main/res/"));
		project.setResourcesFiles(resourceDirs);
		
		// Set output file
		project.setOutputFile(new File(projectRoot + "/"));
		
		FileUtil.deleteFile(new File(project.getOutputFile(), "bin").getAbsolutePath());
		FileUtil.deleteFile(new File(project.getOutputFile(), "gen").getAbsolutePath());
		
		// Set Java files
		List<File> javaFiles = new ArrayList<>();
		javaFiles.add(new File(projectRoot + "/app/src/main/java/" + pkgName.replace(".", "/") + "/"));
		javaFiles.add(new File(FileUtil.getExternalStorageDir() + "/.blacklogics/data/" + sc_id + "/files/java/"));
		project.setJavaFiles(javaFiles);
		
		// Set manifest file
		project.setManifestFile(new File(projectRoot + "/app/src/main/AndroidManifest.xml"));
		
		// Set version name
		project.setVersionName(varName);
		
		// Set assets file
		String assetsPath = FileUtil.getExternalStorageDir() + "/.blacklogics/data/" + sc_id + "/files/assets/";
		if (!android.text.TextUtils.isEmpty(assetsPath)) {
			File assetsDir = new File(assetsPath);
			if (assetsDir.exists() && assetsDir.isDirectory() && assetsDir.canRead()) {
				project.setAssetsFile(assetsDir);
				mLogger.d("ProjectSetup", "Set assets folder: " + assetsDir.getAbsolutePath());
			} else {
				mLogger.w("ProjectSetup", "Assets path invalid or inaccessible: " + assetsPath);
			}
		}
		
		// Set logger and build settings
		project.setLogger(mLogger);
		project.setMinSdk(getMinSdk());
		project.setVersionCode(Integer.parseInt(varCode));
		project.setTargetSdk(getTargetSdk());
		
		// Execute compilation
		CompilerAsyncTask task = new CompilerAsyncTask(DesignActivity.this);
		task.setProject(project);
		task.setLogListener(this);
		task.setProjectJson(FileUtil.getExternalStorageDir().concat("/.blacklogics/data/".concat(sc_id.concat("/project.json"))));
		if (FileUtil.isExistFile(jsonLibraryPath)) {
			task.setLibraryJsonPath(jsonLibraryPath);
		}
		task.setScId(sc_id);
		task.setRecycler(ah);
		task.execute(project);
	}
	
	
	public void _a() {
	}
	/**
     * Opens {@link ManageAssetsActivity}.
     */
	public void toAssets() {
		Intent intent = new Intent(getApplicationContext(), ManageAssetsActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivity(intent);
	}
	/**
     * Opens {@link ManageJavaActivity}.
     */
	public void toJava() {
		Intent intent = new Intent(getApplicationContext(), ManageJavaActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		intent.putExtra("pkgName", pkgName);
		startActivity(intent);
	}
	/**
     * Opens {@link ManageNativelibsActivity}.
     */
	public void toNativelibs() {
		Intent intent = new Intent(getApplicationContext(), ManageNativelibsActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivity(intent);
	}
	
	/**
     * Opens {@link ManagePermissionActivity}.
     */
	public void toPermission() {
		Intent intent = new Intent(getApplicationContext(), com.nexusteam.blacklogics.permission.ManagePermissionActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivity(intent);
	}
	
	/**
     * Opens {@link ManageResourceActivity}.
     */
	public void toResource() {
		Intent intent = new Intent(getApplicationContext(), ManageResourceActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivity(intent);
	}
	/**
     * Opens {@link ManageProguardActivity}.
     */
	public void toProguard() {
		Intent intent = new Intent(getApplicationContext(), ManageProguardActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivity(intent);
	}
	
	/**
     * Opens {@link ManageStringfogActivity}.
     */
	public void toStringfog() {
		Intent intent = new Intent(getApplicationContext(), ManageStringfogActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivity(intent);
	}
	/**
     * Opens {@link ManageLocalLibraryActivity}.
     */
	public void toLocalLibrary() {
		Intent intent = new Intent(getApplicationContext(), ManageLocalLibraryActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivity(intent);
	}
	/**
     * Opens {@link ManageImageActivity}.
     */
	public void toManageImageActivity() {
		Intent intent = new Intent(getApplicationContext(), com.besome.blacklogics.image_manager.ManageImageActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivity(intent);
	}
	/**
     * Opens {@link ManageFontActivity}.
     */
	public void toManageFontActivity() {
		Intent intent = new Intent(getApplicationContext(), com.besome.blacklogics.font_manager.ManageFontActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivity(intent);
	}
	/**
     * Opens {@link ManageSoundActivity}.
     */
	public void toManageSoundActivity() {
		Intent intent = new Intent(getApplicationContext(), com.besome.blacklogics.sound_manager.ManageSoundActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivity(intent);
	}
	/**
     * Opens {@link ManageViewActivity}.
     */
	public void toManageViewActivity() {
		Intent intent = new Intent(getApplicationContext(), com.besome.blacklogics.view_manager.ManageViewActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivity(intent);
	}
	/**
* Opens {@link SrcViewerActivity}.
*/
	public void toSrcViewer() {
		Intent intent = new Intent(getApplicationContext(), SrcViewerActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivity(intent);
	}
	
	/**
* Opens {@link LibraryManager}.
*/
	public void toLibraryManager() {
		Intent intent = new Intent(getApplicationContext(), LibraryManagerActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivity(intent);
	}
	
	/**
* Opens {@link ResourceEncryptManager}.
*/
	public void toEncryptManager() {
		Intent intent = new Intent(getApplicationContext(), ResourceEncryptActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivity(intent);
	}
	
	/**
* Opens {@link Dex Encryption}.
*/
	public void toEncryptDex() {
		Intent intent = new Intent(getApplicationContext(), DexEncryptionActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivity(intent);
	}
	
	/**
* Opens {@link LogcatViewer}.
*/
	public void toLogcatViewer() {
		Intent intent = new Intent(getApplicationContext(), LogcatActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivity(intent);
	}
	
	/**
* Opens {@link AndroidManifest Editor}.
*/
	public void toManifestEditor() {
		Intent intent = new Intent(getApplicationContext(), AndroidManifestEditorActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivity(intent);
	}
	
	{
	}
	
	
	public void _q(final String _q) {
		// Create the Snackbar
		Snackbar snackbar = Snackbar.make(anchor, "Show compile log", Snackbar.LENGTH_LONG);
		
		// Set anchor view (e.g., above a FloatingActionButton)
		snackbar.setAnchorView(R.id.anchor);
		
		// Set action button and handle click
		snackbar.setAction(R.string.common_word_show, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), CompileLogActivity.class);
				intent.putExtra("error", _q);
				intent.putExtra("sc_id", sc_id);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				// Action when "UNDO" is clicked
				//Toast.makeText(getApplicationContext(), "Item Restored", Toast.LENGTH_SHORT).show();
			}
		});
		
		// Show the Snackbar
		snackbar.show();
	}
	
	
	public boolean _save_view() {
		
		return true;
	}
	
	
	public void _setupTabs() {
		designAdapterFragmentAdapter =
		new DesignAdapterFragmentAdapter(getSupportFragmentManager());
		
		customViewPager.setAdapter(designAdapterFragmentAdapter);
		
		designAdapterFragmentAdapter.setTabCount(3);
		
		customViewPager.setOffscreenPageLimit(2);
		customViewPager.setCurrentItem(0);
		
		tab_layout.addTab(tab_layout.newTab().setText("View"));
		tab_layout.addTab(tab_layout.newTab().setText("Event"));
		tab_layout.addTab(tab_layout.newTab().setText("Component"));
		
		tab_layout.setupWithViewPager(customViewPager);
		tab_layout.setInlineLabel(true);
	}
	
	
	public void _initUI() {
		WidgetPropertyManager.widgetpropertiesLinearLayout1 = findViewById(R.id.widgetpropertiesLinearLayout1);
		WidgetPropertyManager.widget_width = findViewById(R.id.widget_width);
		WidgetPropertyManager.widget_height = findViewById(R.id.widget_height);
		WidgetPropertyManager.widget_text = findViewById(R.id.text);
		WidgetPropertyManager.widget_id = findViewById(R.id.widget_id);
		WidgetPropertyManager.widget_src = findViewById(R.id.widget_src);
		WidgetPropertyManager.translationX = findViewById(R.id.translationX);
		WidgetPropertyManager.transY = findViewById(R.id.transY);
		WidgetPropertyManager.colorText = findViewById(R.id.colorText);
		WidgetPropertyManager.textSize = findViewById(R.id.textSize);
		WidgetPropertyManager.textStyle = findViewById(R.id.textStyle);
		WidgetPropertyManager.Lines = findViewById(R.id.Lines);
		WidgetPropertyManager.margin = findViewById(R.id.margin);
		WidgetPropertyManager.padding = findViewById(R.id.padding);
		WidgetPropertyManager.background = findViewById(R.id.background);
		WidgetPropertyManager.widget_inject_attributes = findViewById(R.id.widget_inject_attributes);
		WidgetPropertyManager.gravityLayout = findViewById(R.id.gravityLayout);
		WidgetPropertyManager.layoutGravity = findViewById(R.id.layoutGravity);
		WidgetPropertyManager.checkState = findViewById(R.id.checkState);
		WidgetPropertyManager.switchCheckState = findViewById(R.id.switchCheckState);
		WidgetPropertyManager.progressStyle = findViewById(R.id.progressStyle);
		WidgetPropertyManager.widget_scale = findViewById(R.id.widget_scale);
		WidgetPropertyManager.max_progress = findViewById(R.id.max_progress);
		WidgetPropertyManager.widget_orientation = findViewById(R.id.widget_orientation);
		WidgetPropertyManager.widget_convert = findViewById(R.id.widget_convert);
		WidgetPropertyManager.widget_weight = findViewById(R.id.widget_weight);
		WidgetPropertyManager.ll_properties = findViewById(R.id.ll_properties);
		widgetpropertiesLinearLayout1 = findViewById(R.id.widgetpropertiesLinearLayout1);
		widget_width = findViewById(R.id.widget_width);
		widget_height = findViewById(R.id.widget_height);
		widget_text = findViewById(R.id.text);
		widget_id = findViewById(R.id.widget_id);
		widget_src = findViewById(R.id.widget_src);
		translationX = findViewById(R.id.translationX);
		transY = findViewById(R.id.transY);
		colorText = findViewById(R.id.colorText);
		textSize = findViewById(R.id.textSize);
		textStyle = findViewById(R.id.textStyle);
		Lines = findViewById(R.id.Lines);
		margin = findViewById(R.id.margin);
		padding = findViewById(R.id.padding);
		background = findViewById(R.id.background);
		widget_inject_attributes = findViewById(R.id.widget_inject_attributes);
		gravityLayout = findViewById(R.id.gravityLayout);
		layoutGravity = findViewById(R.id.layoutGravity);
		checkState = findViewById(R.id.checkState);
		switchCheckState = findViewById(R.id.switchCheckState);
		progressStyle = findViewById(R.id.progressStyle);
		widget_scale = findViewById(R.id.widget_scale);
		max_progress = findViewById(R.id.max_progress);
		widget_orientation = findViewById(R.id.widget_orientation);
		widget_convert = findViewById(R.id.widget_convert);
		widget_weight = findViewById(R.id.widget_weight);
		ll_properties = findViewById(R.id.ll_properties);
	}
	
	
	public void _ensureJavaCode(final String _className, final String _code) {
		if (!complex.isJavaActivityAvailable(_className)) {
			complex.setJavaCode(_className, _code);
		}
		
	}
	
	
	public void _setupSpinnerListener() {
		file_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (isSpinnerUpdatingProgrammatically) return;
				if (parent == null || position < 0 || position >= parent.getCount()) {
					return;
				}
				
				if (position == lastSpinnerPosition) {
					return;
				}
				
				lastSpinnerPosition = position;
				
				Object item = parent.getItemAtPosition(position);
				if (item instanceof String) {
					String selectedItem = (String) item;
					if (selectedItem != null) {
						if (pos == 0) {
							savedXmlPosition = position;
							_setCurrentLayout(selectedItem);
						} else {
							savedJavaPosition = position;
							_setCurrentActivity(selectedItem);
						}
					}
				}
				
				((PagerAdapter)customViewPager.getAdapter()).notifyDataSetChanged();
				
			}
			
			@Override 
			public void onNothingSelected(AdapterView<?> parent) {}
		});
		
		file_spinner.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					showCustomSpinnerDialog();
				}
				return true;
			}
		});
		
	}
	
	
	public void _setupViewPagerListener() {
		customViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				pos = position;
				
				isSpinnerUpdatingProgrammatically = true; // 🔒 Lock
				
				if (position == 0) {
					complex.setXmlAdapter(file_spinner);
					file_spinner.setSelection(currentState.getXmlSpinnerPosition());
				} else {
					complex.setJavaAdapter(file_spinner);
					file_spinner.setSelection(currentState.getJavaSpinnerPosition());
				}
				tab_layout.getTabAt(position).select();
			}
			@Override public void onPageScrolled(int pos, float offset, int offsetPx) {}
			@Override public void onPageScrollStateChanged(int state) {}
		});
		
	}
	
	
	public void _updateUndoRedoIcons() {
		uiUpdateManager.updateUndoRedoIcons();
	}
	
	
	public void _setCurrentActivity(final String _activityName) {
		setCurrentActivityName(_activityName);
		for (ActivityBean activity : activityList) {
			if (activity.getActivityName().equals(_activityName)) {
				activityBean = activity;
				break;
			}
		}
		if (activityNameChangeListener != null) {
			activityNameChangeListener.onActivityNameChanged(_activityName);
			activityNameChangeListener.onLayoutNameChanged(activityBean.getLayoutName());
		}
	}
	
	
	public void _setCurrentLayout(final String _layoutName) {
		setCurrentLayoutName(_layoutName);
		activityBean.setLayoutName(_layoutName);
	}
	
	
	public void _generators() {
	}
	@Override
	public void generateJavaCode() {
		// TUDO NOT REQUIRE IN THIS
	}
	
	// Declare widget fields using all types from layout
	private void declareWidgetFields(String activityName, StringBuilder javaCode) {
		Map<String, String> widgets = ViewEditor.getIdsWithClass("all", activityName);
		for (Map.Entry<String, String> entry : widgets.entrySet()) {
			String id = entry.getKey();
			String type = entry.getValue();
			javaCode.append("    private ").append(type).append(" ").append(id).append(";\n");
		}
	}
	
	private void initializeWidgetFields(String activityName, StringBuilder javaCode) {
		Map<String, String> widgets = ViewEditor.getIdsWithClass("all", activityName);
		for (String id : widgets.keySet()) {
			javaCode.append("        ").append(id)
			.append(" = findViewById(R.id.").append(id).append(");\n");
		}
	}
	
	public String getJavaCode() {
		SourceCodeGenerator codeGenerator = new SourceCodeGenerator(this, sc_id);
		return codeGenerator.generateCompleteJavaCode(activityBean.getActivityName(), activityBean.getLayoutName());
	}
	
	public void generateJavaCode(String acName, String layoutName) {
		//TUDO NOT REQUIRED IN THIS
	}
	
	private void bindWidgetEvents(String activityName, StringBuilder javaCode) {
	}
	
	private void initPermissions(StringBuilder javaCode) {
	}
	
	private void initImports(String acName, StringBuilder javaCode) {
	}
	
	private String getInitialValue(String varType) {
		switch (varType) {
			case "int":
			case "byte":
			case "short":
			case "long":
			case "float":
			case "double":
			return "0";
			case "boolean":
			return "false";
			case "String":
			return "\"\"";
			case "ArrayList<String>":
			return "new ArrayList<>()";
			case "ArrayList<Double>":
			return "new ArrayList<>()";
			case "HashMap<String, String>":
			return "new HashMap<>()";
			default:
			return ""; 
		}
	}
	
	{
	}
	
	
	public void _interfaces() {
		/*
 * TODO: Added bracket support to enhance compatibility with the Sketchware environment.
 * This improvement enables additional block features and better block structure handling.
 * 
 * Note: This change is intentional and essential for extended functionality.
 * Please do not misinterpret or remove this bracket, as it plays a crucial role
 * in maintaining system stability and feature support within the Sketchware platform.
 */
		
	}
	public interface OnActivityNameChangeListener {
		void onActivityNameChanged(String newActivityName);
		void onLayoutNameChanged(String newLayoutName);
	}
	
	public void setOnActivityNameChangeListener(OnActivityNameChangeListener listener) {
		this.activityNameChangeListener = listener;
	}
	{
	}
	
	
	public void _loading() {
		final LoadingDialog loadingDialog = new LoadingDialog(this);
		loadingDialog.show();
		
		ExecutorService executor = Executors.newSingleThreadExecutor();
		final Handler mainHandler = new Handler(Looper.getMainLooper());
		
		executor.execute(new Runnable() {
			@Override
			public void run() {
				
				Exception error = null;
				
				try {
					boolean success = saveView();
					if (!success) {
						throw new Exception("Save failed");
					}
				} catch (Exception e) {
					error = e;
				}
				
				final Exception finalError = error;
				
				mainHandler.post(new Runnable() {
					@Override
					public void run() {
						
						if (loadingDialog.isShowing()) {
							loadingDialog.hide();
						}
						
						if (finalError == null) {
							TheBlockLogicsUtil.showToast(
							getApplicationContext(),
							"Projeto Saved;"
							);
						} else {
							TheBlockLogicsUtil.showToast(
							getApplicationContext(),
							"Projeto Save Failed: " + finalError.getMessage()
							);
						}
					}
				});
			}
		});
		
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
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
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
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