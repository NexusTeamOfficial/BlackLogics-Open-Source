package com.nexusteam.blacklogics;

import android.animation.*;
import com.nexusteam.blacklogics.design.AppCompatManagerActivity;
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
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.*;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.*;
import com.google.android.material.card.*;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.google.android.material.textfield.*;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.*;
import com.googlecode.d2j.*;
import com.larswerkman.holocolorpicker.*;
import com.nexusteam.nexatask.*;
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
import org.json.*;
import a.a.a.Lx;
import com.nexusteam.blacklogics.generator.source.AndroidManifestGenerator;
import com.nexusteam.blacklogics.manager.ProjectLogicManager;
import com.nexusteam.blacklogics.manager.ActivityManager;
import mod.SketchwareUtil;
import android.widget.Space;
import com.nexusteam.blacklogics.model.*;
import com.nexusteam.blacklogics.development.*;
import com.nexusteam.blacklogics.custom.CustomActivityDialog;
import com.nexusteam.blacklogics.design.DesignDrawer;
import com.nexusteam.blacklogics.interfaces.CompilerLogListener;
import com.nexusteam.blacklogics.util.FileHandler;
import com.nexusteam.blacklogics.util.ProjectActivityManager;
import androidx.lifecycle.ViewModelProvider;
import com.nexusteam.internal.os.layouteditor.custom.MyDragWidget;
import com.nexusteam.internal.os.layouteditor.widget.Widget;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import android.widget.LinearLayout.LayoutParams;
import androidx.cardview.widget.CardView;
import com.nexusteam.internal.os.layouteditor.widget.*;
import com.nexusteam.internal.os.layouteditor.util.*;
import com.nexusteam.internal.os.layouteditor.adapter.*;
import com.nexusteam.internal.os.layouteditor.color.*;
import com.nexusteam.blacklogics.model.ActivityData;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.DragEvent;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.os.Environment;
import android.util.Base64;
import com.google.gson.reflect.TypeToken;
import com.nexusteam.blacklogics.model.WidgetViewModel;
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
import com.nexusteam.blacklogics.model.DesignDataManager;
import com.nexusteam.blacklogics.util.FileHandler;
import com.nexusteam.blacklogics.beans.SelectedActivityState;
import com.nexusteam.blacklogics.image_manager.ManageImageActivity;
import com.nexusteam.blacklogics.font_manager.ManageFontActivity;
import com.nexusteam.blacklogics.sound_manager.ManageSoundActivity;
import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.internal.editor.view.ProjectFileSelector;
import com.nexusteam.blacklogics.activityfile.ProjectFileBootstrap;
import com.nexusteam.blacklogics.activityfile.HoBridge;
import com.nexusteam.blacklogics.activityfile.OnProjectFileChangeListener;
import com.nexusteam.blacklogics.activityfile.ActivityFileController;
import com.nexusteam.blacklogics.manage.view.ManageViewActivity;
import com.nexusteam.blacklogics.custom.CustomViewPager;
import com.nexusteam.blacklogics.activities.ViewCodeEditorActivity;
import com.nexusteam.blacklogics.beans.ProjectActivityBean;
import com.nexusteam.blacklogics.file.WidgetClickListenerManager;
import com.nexusteam.blacklogics.file.AssetCopyUtil;
import com.nexusteam.blacklogics.development.Complex.ViewItem;
import com.nexusteam.blacklogics.custom.CustomSpinner;
import com.nexusteam.blacklogics.design.CreateCustomViewDialog;
import com.nexusteam.blacklogics.sdk.layouteditor.XmlLayoutGenerator;
import com.nexusteam.blacklogics.design.CreateActivityDialog;
import com.nexusteam.blacklogics.design.CreateActivityDialogFragment;
import com.nexusteam.blacklogics.design.CustomSpinnerDialog;
import com.nexusteam.blacklogics.design.*;
import com.nexusteam.blacklogics.sdk.layouteditor.ViewEditor;
import java.lang.ref.WeakReference;
import com.nexusteam.blacklogics.lib.base.BaseActivity;
import com.nexusteam.blacklogics.beans.ActivityBean;
import com.nexusteam.blacklogics.model.ActivityData;
import com.nexusteam.blacklogics.model.BuildSettings;
import com.nexusteam.blacklogics.logcat.LogcatActivity;
import com.nexusteam.blacklogics.ui.BuildSettingsDialog;
import com.nexusteam.blacklogics.ui.SourceCodeDialog;
import com.nexusteam.blacklogics.activities.ManageAssetsActivity;
import com.nexusteam.blacklogics.permission.ManagePermissionActivity;
import com.nexusteam.blacklogics.utils.Helper;
import com.nexusteam.sdk.compilerlog.CompileErrorSaver;
import com.nexusteam.blacklogics.beans.ProjectBean;
import com.nexusteam.blacklogics.custom_blocks.CustomBlocksDialog;
import com.nexusteam.sdk.java.ManageJavaActivity;
import com.nexusteam.sdk.resource.ManageResourceActivity;
import dev.aldi.sayuti.editor.manage.ManageLocalLibraryActivity;
import com.nexusteam.sdk.nativelib.ManageNativelibsActivity;
import com.nexusteam.blacklogics.tools.CompileLogActivity;
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
	private ActivityFileController activityFileController;
	private ProjectFileSelector projectFileSelector;
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
	private boolean suppressEditorSave = false;
	private String projectPathBase = "";
	public static String sCId;
	public ActivityData activityData;
	public OnActivityNameChangeListener activityNameChangeListener;
	private DesignAdapterFragmentAdapter designAdapterFragmentAdapter;
	private SelectedActivityState currentState = new SelectedActivityState();
	private boolean isFirstLoad = true;
	
	private ArrayList<String> s = new ArrayList<>();
	
	private MaterialToolbar designToolbar;
	private MenuItem undoMenuItem;
	private MenuItem redoMenuItem;
	private LinearLayoutCompat toolbar_improved;
	private TabLayout tab_layout;
	private ViewPager customViewPager;
	private RecyclerView ah;
	private LinearLayoutCompat anchor;
	private MaterialCardView controlPanelCard;
	private MaterialCardView propertiesCard;
	private CircleImageView circleimageview1;
	public static LinearLayout linear18;
	private Space linear16;
	private Space linear14;
	private Space linear17;
	private Space linear12;
	private LinearLayoutCompat control_panel;
	private ImageView project_detail;
	private Space linear15;
	private MaterialButton execute_button;
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
		_ad_unit_id = "ca-app-pub-3940256099942544/1033173712";
		List<String> testDeviceIds = Arrays.asList("D4516BAC0B080049F0FABC1AD69374C4", "09B7C7D54982B3ED7D75AC6D6E66BC22");
		MobileAds.setRequestConfiguration(new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build());
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
		control_panel = findViewById(R.id.control_panel);
		project_detail = findViewById(R.id.project_detail);
		execute_button = findViewById(R.id.execute_button);
		ll_properties = findViewById(R.id.ll_properties);
		_drawer_drawer_layout_base_d = _nav_view.findViewById(R.id.drawer_layout_base_d);
		
		Menu _tbMenu = designToolbar.getMenu();
		undoMenuItem = _tbMenu.findItem(R.id.action_undo);
		redoMenuItem = _tbMenu.findItem(R.id.action_redo);
		
		designToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (!isAdapterReady()) {
					exibirMensagemEdt("Exit from this project", "Do you want to save before quitting?");
					return;
				}
				
				ViewBuilderFragmentActivity viewFragment = (ViewBuilderFragmentActivity) getFragmentFromViewPager(0);
				if (_drawer.isDrawerOpen(Gravity.END)) {
					_drawer.closeDrawer(Gravity.END);
					return;
				}
				
				if (viewFragment.viewEditor.isHiddenProperties()) {
					int n = tab_layout.getSelectedTabPosition();
					if (n != 0) {
						tab_layout.getTabAt(n - 1).select();
						return;
					}
					exibirMensagemEdt("Exit from this project", "Do you want to save before quitting?");
					return;
				}
				
				viewFragment.viewEditor.hideProperties();
			}
		});
		
		designToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				int id = item.getItemId();
				if (id == R.id.action_undo) {
					ViewBuilderFragmentActivity viewFragment = (ViewBuilderFragmentActivity) getFragmentFromViewPager(0);
					if (viewFragment != null) {
						viewFragment.viewEditor.undo();
						_updateUndoRedoIcons();
					}
					return true;
				} else if (id == R.id.action_redo) {
					ViewBuilderFragmentActivity viewFragment = (ViewBuilderFragmentActivity) getFragmentFromViewPager(0);
					if (viewFragment != null) {
						viewFragment.viewEditor.redo();
						_updateUndoRedoIcons();
					}
					return true;
				} else if (id == R.id.action_save) {
					_loading();
					return true;
				} else if (id == R.id.action_more) {
					_drawer.openDrawer(Gravity.END);
					return true;
				}
				return false;
			}
		});
		
		project_detail.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				PopupMenu popupMenu = new PopupMenu(DesignActivity.this, project_detail);
				Menu menu = popupMenu.getMenu();
				
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
				flushDesignBeforeRun();
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
		com.nexusteam.blacklogics.project.ProjectDataHelper.setScId(this, sc_id);
		com.nexusteam.blacklogics.project.ProjectDataHelper.setActivityName(this, defaultAcName);
		LinearLayout _nav_view = (LinearLayout) findViewById(R.id._nav_view);
		androidx.drawerlayout.widget.DrawerLayout
		.LayoutParams lp = new androidx.drawerlayout.widget.DrawerLayout
		.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		lp.gravity = Gravity.END;
		_nav_view.setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));
		_nav_view.setLayoutParams(lp);
		projectPathBase = FileUtil.getExternalStorageDir() + "/.blacklogics/data/" + sc_id;
		activityData = new ActivityData("MainActivity.java", "main.xml", true);
		activityData.setActivityName("MainActivity");
		_initUI();
		designToolbar.setTitle(getIntent().getStringExtra("scName"));
		designToolbar.setSubtitle(getIntent().getStringExtra("sc_id"));
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
		setupActivityFileSystem();
		File logicFile = new File(basePath, ".blacklogics/data/" + sc_id + "/root_logic");
		if (logicFile.isFile() && logicFile.length() > 0) {
			complex.extractAllLogicsFromJson(logicFile.getAbsolutePath());
		}
		
		settings = new BuildSettings(sc_id);
		projectBean = new ProjectBean();
		projectBean.screens.add(complex.getAllJavaActivity());
		mLogger = new Logger();
		mLogger.attach(ah);
		initializeActivityList();
		_ensureJavaCode("BlackLogicsUtil", Lx.i(pkgName));
		_setupViewPagerListener();
		uiUpdateManager = new UiUpdateManager(new ImageView(this), new ImageView(this), customViewPager);
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
			
			if (_drawer != null && _drawer.isDrawerOpen(Gravity.END)) {
				_drawer.closeDrawer(Gravity.END);
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
			_drawer.openDrawer(Gravity.END);
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
		if (activityBean == null) {
			initializeActivityList();
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
	
	@Override
	public void onPause() {
		super.onPause();
		try {
			ViewBuilderFragmentActivity vf =
			(ViewBuilderFragmentActivity) getFragmentFromViewPager(0);
			if (vf != null) {
				vf.saveLayout();
			}
		} catch (Throwable t) {
			android.util.Log.e("DesignActivity", "onPause saveLayout failed", t);
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		try {
			final ViewBuilderFragmentActivity vf =
			(ViewBuilderFragmentActivity) getFragmentFromViewPager(0);
			if (vf != null && vf.viewEditor != null) {
				vf.viewEditor.post(new Runnable() {
					@Override
					public void run() {
						try {
							vf.viewEditor.forceRegistryRecovery();
						} catch (Throwable t) {
							android.util.Log.e("DesignActivity", "onResume recovery failed", t);
						}
					}
				});
			}
		} catch (Throwable t) {
			android.util.Log.e("DesignActivity", "onResume failed", t);
		}
	}
	
	public void _b() {
	}
	
	public interface OnActivityListChangedListener {
		void onActivityListChanged();
	}
	
	private OnActivityListChangedListener activityListChangedListener;
	
	public void setOnActivityListChangedListener(OnActivityListChangedListener listener) {
		this.activityListChangedListener = listener;
	}
	
	public void yq(boolean yes, String no) {
		yq(yes, no, null);
	}
	
	public void yq(boolean yes, String no, final Runnable extraOnSaved) {
		this.pendingActivitySavedRunnable = extraOnSaved;
		Intent intent = new Intent(getApplicationContext(),
		com.nexusteam.blacklogics.manage.view.AddViewActivity.class);
		intent.putExtra("sc_id", sc_id);
		if (!yes && no != null && no.length() > 0) {
			try {
				com.nexusteam.internal.ly ly = com.nexusteam.internal.ma.b(sc_id);
				if (ly != null) {
					com.nexusteam.internal.beans.ProjectFileBean bean = ly.a(no.endsWith(".xml") ? no : no + ".xml");
					if (bean == null) bean = ly.a(no);
					if (bean != null) {
						intent.putExtra("project_file", bean);
					}
				}
			} catch (Throwable ignore) {
			}
		}
		startActivityForResult(intent, 264);
	}
	
	private transient Runnable pendingActivitySavedRunnable;
	
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
		new MaterialAlertDialogBuilder(this)
		.setIcon(R.drawable.exit_96)
		.setTitle(titulo)
		.setMessage(texto)
		.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				finish();
			}
		})
		.setPositiveButton("Save & exit", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				FragmentManager fragmentManager = getSupportFragmentManager();
				ViewBuilderFragmentActivity activity = (ViewBuilderFragmentActivity) fragmentManager
				.findFragmentByTag("android:switcher:" + customViewPager.getId() + ":0");
				if (activity != null && activity.viewEditor != null) {
					activity.saveLayout();
				}
				TheBlockLogicsUtil.showToast(getApplicationContext(), "Projeto Saved!");
				finish();
			}
		})
		.show();
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
		
		return result.toString() + (ViewEditorFragmentActivity.useAndroidX ? "AppCompatActivity" : "Activity");
	}
	
	private void showCustomSpinnerDialog() {
		new CustomSpinnerDialog(this, sc_id, complex).show();
	}
	
	public void handleItemSelection(final ViewItem item) {
		
		if (!isAdapterReady()) {
			return;
		}
		
		final ViewBuilderFragmentActivity viewFragment =
		(ViewBuilderFragmentActivity) getFragmentFromViewPager(0);
		
		if (viewFragment == null) return;
		
		final String newXml = item.getXmlFileName() != null ? item.getXmlFileName() : "";
		final String newJava = item.getJavaName() != null ? item.getJavaName() : "";
		
		String curLay = viewFragment.layoutName != null ? viewFragment.layoutName : "";
		if (curLay.endsWith(".xml")) curLay = curLay.substring(0, curLay.length() - 4);
		String wantLay = newXml;
		if (wantLay.endsWith(".xml")) wantLay = wantLay.substring(0, wantLay.length() - 4);
		
		String curAct = viewFragment.activityName != null ? viewFragment.activityName : "";
		if (curAct.endsWith(".java")) curAct = curAct.substring(0, curAct.length() - 5);
		String wantAct = newJava;
		if (wantAct.endsWith(".java")) wantAct = wantAct.substring(0, wantAct.length() - 5);
		
		boolean layoutChanged = !wantLay.equals(curLay);
		boolean activityChanged = !wantAct.equals(curAct);
		
		if (layoutChanged || activityChanged) {
			try {
				viewFragment.saveLayout();
			} catch (Throwable t) {
				android.util.Log.e("DesignActivity", "save before switch", t);
			}
		}
		
		if (layoutChanged || activityChanged) {
			try {
				if (viewFragment.viewEditor != null) {
					viewFragment.viewEditor.idManager.clearAllIds();
				}
			} catch (Throwable ignore) {}
		}
		
		if (!wantLay.isEmpty() && layoutChanged) {
			updateLayoutName(viewFragment, item);
		}
		
		if (!wantAct.isEmpty() && activityChanged) {
			updateActivityName(viewFragment, item);
		}
		
		if (layoutChanged || activityChanged) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					viewFragment.loadLayout();
				}
			});
		}
		
		try {
			notifyComponentListActivityChanged(wantAct);
		} catch (Throwable t) {
			android.util.Log.w("DesignActivity", "component refresh", t);
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
		
		currentState = new SelectedActivityState(
		item.getXmlName(),
		item.getXmlFileName(),
		item.getJavaName(),
		item.getJavaFileName(),
		savedXmlPosition,
		savedJavaPosition
		);
	}
	
	
	private void notifyComponentListActivityChanged(String activityName) {
		if (activityName == null) return;
		String act = activityName;
		if (act.endsWith(".java")) act = act.substring(0, act.length() - 5);
		try {
			com.nexusteam.blacklogics.project.ProjectDataHelper.setActivityName(this, act);
		} catch (Throwable ignore) {}
		try {
			Object frag = getFragmentFromViewPager(2);
			if (frag != null) {
				try {
					java.lang.reflect.Method m = frag.getClass().getMethod("setActivityNameAndRefresh", String.class);
					m.invoke(frag, act);
					android.util.Log.i("DesignActivity", "ComponentList refreshed for " + act);
					return;
				} catch (NoSuchMethodException ignore) {}
				try {
					java.lang.reflect.Field f = frag.getClass().getDeclaredField("activityName");
					f.setAccessible(true);
					f.set(frag, act);
					try {
						java.lang.reflect.Method r = frag.getClass().getMethod("refreshComponentList");
						r.invoke(frag);
					} catch (NoSuchMethodException ignore2) {
						try {
							java.lang.reflect.Method r2 = frag.getClass().getMethod("refreshUi");
							r2.invoke(frag);
						} catch (Throwable ignore3) {}
					}
				} catch (Throwable t2) {
					android.util.Log.w("DesignActivity", "component field refresh", t2);
				}
			}
		} catch (Throwable t) {
			android.util.Log.w("DesignActivity", "notifyComponentList", t);
		}
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
		com.nexusteam.blacklogics.project.ProjectDataHelper.setActivityName(this, item.getJavaName());
		
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
		viewFragment.viewEditor.tv_view_name.setText(xmlFileName);
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
		Intent intent = new Intent(getApplicationContext(),
		com.nexusteam.blacklogics.manage.view.AddCustomViewActivity.class);
		intent.putExtra("sc_id", sc_id);
		startActivityForResult(intent, 266);
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
		new CompileErrorSaver(sc_id).writeLogsToFile(errorLog);
		_q(errorLog);
	}
	
	@Override
	public void onLog(String log) {
	}
	
	@Override
	public void onDataRefreshed() {
	}
	
	@Override
	public void onRefreshFailed(String errorMessage) {
	}
	
	public void directXMLEdit() {
		Intent intent = new Intent(getApplicationContext(), com.nexusteam.blacklogics.activities.ViewCodeEditorActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		intent.putExtra("activityBean", currentActivityBean);
		intent.putExtra("layoutName", currentActivityBean.getActivityName());
		intent.putExtra("code", getXmlCode());
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
	
	private void dismissLoadingDialog() {
		try {
			if (loadingDialog != null && loadingDialog.isShowing()) {
				loadingDialog.dismiss();
			}
		} catch (Exception e) {
			Log.e("SaveProject", "Error dismissing loading dialog", e);
		}
	}
	
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
		}
	}
	
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
				Type mapType = new TypeToken<Map<String, Map<String, String>>>() {
				}.getType();
				Map<String, Map<String, String>> logicMap = new Gson().fromJson(decodedJson, mapType);
				Map<String, String> activityLogic = logicMap.get(currentActivityBean.getActivityName());
				if (activityLogic != null) {
					String val = activityLogic.get(widgetId);
					return val != null ? val : "";
				}
			}
		} catch (Exception e) {
		}
		return "";
	}
	
	private void copyDirRecursive(File src, File dst) {
		if (src == null || !src.exists()) {
			return;
		}
		if (src.isDirectory()) {
			if (!dst.exists() && !dst.mkdirs()) {
				mLogger.e("AssetSync", "Failed to create dir: " + dst.getAbsolutePath());
				return;
			}
			File[] children = src.listFiles();
			if (children != null) {
				for (File child : children) {
					copyDirRecursive(child, new File(dst, child.getName()));
				}
			}
		} else {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new FileInputStream(src);
				out = new FileOutputStream(dst);
				byte[] buffer = new byte[8192];
				int len;
				while ((len = in.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
			} catch (IOException e) {
				mLogger.e("AssetSync", "Failed to copy " + src.getAbsolutePath() + " -> " + dst.getAbsolutePath() + ": " + e);
			} finally {
				try { if (in != null) in.close(); } catch (IOException e) { }
				try { if (out != null) out.close(); } catch (IOException e) { }
			}
		}
	}
	
	private void syncFontsAndSoundsToAssets() {
		String externalRoot = FileUtil.getExternalStorageDir();
		File fontsSrc = new File(externalRoot + "/.blacklogics/resources/fonts/" + sc_id + "/");
		File soundsSrc = new File(externalRoot + "/.blacklogics/resources/sounds/" + sc_id + "/");
		
		String assetsBase = externalRoot + "/.blacklogics/data/" + sc_id + "/files/assets/";
		String soundFileBase = externalRoot + "/.blacklogics/mysc/" + sc_id + "/app/src/main/res/";
		File fontsDst = new File(assetsBase, "fonts");
		File soundsDst = new File(soundFileBase, "raw");
		
		if (fontsSrc.exists() && fontsSrc.isDirectory()) {
			copyDirRecursive(fontsSrc, fontsDst);
			mLogger.d("AssetSync", "Synced fonts: " + fontsSrc.getAbsolutePath() + " -> " + fontsDst.getAbsolutePath());
		} else {
			mLogger.w("AssetSync", "Fonts source not found: " + fontsSrc.getAbsolutePath());
		}
		
		if (soundsSrc.exists() && soundsSrc.isDirectory()) {
			copyDirRecursive(soundsSrc, soundsDst);
			mLogger.d("AssetSync", "Synced sounds: " + soundsSrc.getAbsolutePath() + " -> " + soundsDst.getAbsolutePath());
		} else {
			mLogger.w("AssetSync", "Sounds source not found: " + soundsSrc.getAbsolutePath());
		}
	}
	
	private void copyAssets() {
		AssetCopyUtil.copyAssetsToExternalStorage(this, "icon", ".blacklogics/mysc/" + sc_id + "/app/src/main/res/mipmap/");
		AssetCopyUtil.copyAssetsToExternalStorage(this, "default_image", ".blacklogics/mysc/" + sc_id + "/app/src/main/res/drawable-xhdpi/");
	}
	
	public static boolean isToolbarEnabled(String acName) {
		Complex c = new Complex();
		return c.isToolbarEnabled(acName);
	}
	
	public static boolean isEnableFab(String activityName) {
		Complex c = new Complex();
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
		ProjectLogicManager.saveComponentLogic(activityName, componentName, fieldName);
	}
	
	public static List<HashMap<String, String>> loadComponentLogic(String activityName) {
		return ProjectLogicManager.loadComponentLogic(activityName);
	}
	
	public static List<HashMap<String, String>> loadIntentComponents(String activityName) {
		return ProjectLogicManager.loadComponentFromName(activityName, "Intent");
	}
	
	public static List<HashMap<String, String>> loadCalendarComponents(String activityName) {
		return ProjectLogicManager.loadComponentFromName(activityName, "Calendar");
	}
	
	public static void removeComponentLogic(String activityName, String componentName, String fieldName) {
		ProjectLogicManager.removeComponentLogic(activityName, componentName, fieldName);
	}
	
	private String getImageScaleType(String scaleType) {
		if (scaleType == null || scaleType.equals("fitCenter")) {
			return null;
		}
		
		switch (scaleType) {
			case "fitXY":
			case "centerCrop":
			case "centerInside":
			case "fitStart":
			case "fitEnd":
			case "center":
			case "matrix":
			return scaleType;
			default:
			return "fitCenter";
		}
	}
	
	public static void saveVariable(String activityName, String componentName, String fieldName) {
		ProjectLogicManager.saveVariable(activityName, componentName, fieldName);
	}
	
	public static Map<String, List<String>> getVariables(String activityName) {
		Map<String, List<String>> vars = variableMap.get(activityName);
		if (vars == null) {
			vars = new HashMap<String, List<String>>();
		}
		return vars;
	}
	
	public static List<String> getVariable(String activityName, String varType) {
		Map<String, List<String>> activityVars = variableMap.get(activityName);
		if (activityVars == null) {
			activityVars = new HashMap<String, List<String>>();
		}
		
		List<String> vars = activityVars.get(varType);
		if (vars == null) {
			vars = new ArrayList<String>();
		}
		
		return vars;
	}
	
	public static void removeVariable(String activityName, String componentName, String fieldName) {
		ProjectLogicManager.removeVariable(activityName, componentName, fieldName);
	}
	
	public static List<HashMap<String, String>> loadVariableLogic(String activityName) {
		return ProjectLogicManager.loadVariableLogic(activityName);
	}
	
	public static void addFunction(String activityName, String functionName, String returnType, List<HashMap<String, String>> parameters) {
	}
	
	public static List<HashMap<String, Object>> loadFunctions(String activityName) {
		return null;
	}
	
	public static void removeFunction(String activityName, String functionName) {
	}
	
	public static List<HashMap<String, String>> loadComponentFromName(String activityName, String componentName) {
		return ProjectLogicManager.loadComponentFromName(activityName, componentName);
	}
	
	public static void saveBlockLogicForEvent(String activityName, String widgetId, String logic) {
		ProjectLogicManager.saveBlockLogicForEvent(activityName, widgetId, logic);
	}
	
	public static String getBlockLogics(String activityName) {
		return "";
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
	
	@Override
	public void onWidgetLongClicked(View widget) {
		Log.d(TAG, "DesignActivity: Widget long clicked - " + widget.getClass().getSimpleName());
	}
	
	@Override
	public void onWidgetSelected(View widget) {
		Log.d(TAG, "DesignActivity: Widget selected - " + widget.getClass().getSimpleName());
		
	}
	
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
		boolean mainExists = false;
		for (ActivityBean act : activityList) {
			if (act.getActivityName().equals("MainActivity")) {
				mainExists = true;
				activityBean = act;
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
				Type listType = new TypeToken<ArrayList<ActivityBean>>() {
				}.getType();
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
	
	private String getActivitiesFilePath() {
		return FileUtil.getExternalStorageDir() + "/.blacklogics/data/" + sc_id + "/base_activities.json";
	}
	
	private Map<String, String> createMainActivityAttributes(String themeName) {
		Map<String, String> attrs = new HashMap<>();
		attrs.put("exported", "true");
		attrs.put("launchMode", "singleTop");
		attrs.put("configChanges", "orientation|keyboardHidden|screenSize");
		attrs.put("windowSoftInputMode", "adjustResize");
		
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
	
	public void resetSpinnerState() {
	}
	
	{
	}
	
	public void _processApk() {
		flushDesignBeforeRun();
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
			mLogger.d("ProjectSetup", "Keeping project root: " + projectRoot);
		}
		
		SystemLogPrinter.start(mLogger);
		
		Project project = new Project();
		project.setId(sc_id);
		AndroidManifestGenerator freshManifest = new AndroidManifestGenerator();
		freshManifest.load(this, sc_id);
		String freshPkgName = freshManifest.getPackageName();
		if (freshPkgName != null && !freshPkgName.trim().isEmpty()) {
			pkgName = freshPkgName;
		}
		project.setPackageName(pkgName);
		String dexer = settings.getValue(BuildSettings.SETTING_DEXER, BuildSettings.SETTING_DEXER_DX);
		
		if (BuildSettings.SETTING_DEXER_R8.equals(dexer)) {
			project.getBuildSettings().setDexCompilerType(DexCompilerType.R8);
		} else if (BuildSettings.SETTING_DEXER_D8.equals(dexer)) {
			project.getBuildSettings().setDexCompilerType(DexCompilerType.D8);
		} else if (BuildSettings.SETTING_DEXER_DX.equals(dexer)) {
			project.getBuildSettings().setDexCompilerType(DexCompilerType.DX);
		}
		
		File workingDir = new File(projectRoot, "build_temp");
		if (!workingDir.exists() && !workingDir.mkdirs()) {
			mLogger.e("ProjectSetup", "Failed to create working directory: " + workingDir.getAbsolutePath());
			throw new RuntimeException("Failed to create working directory");
		}
		project.setWorkingDirectory(workingDir);
		mLogger.d("ProjectSetup", "Set working directory: " + workingDir.getAbsolutePath());
		
		project.setLibraries(Library.fromFile(new File(projectRoot + "/app/libs/")));
		
		project.setProjectName(scName);
		
		project.setProguardFile(new File(proguardPath));
		
		List<File> resourceDirs = new ArrayList<>();
		resourceDirs.add(new File(FileUtil.getExternalStorageDir() + "/.blacklogics/data/" + sc_id + "/files/resource/"));
		resourceDirs.add(new File(projectRoot + "/app/src/main/res/"));
		project.setResourcesFiles(resourceDirs);
		
		project.setOutputFile(new File(projectRoot + "/"));
		
		FileUtil.deleteFile(new File(project.getOutputFile(), "bin").getAbsolutePath());
		FileUtil.deleteFile(new File(project.getOutputFile(), "gen").getAbsolutePath());
		
		List<File> javaFiles = new ArrayList<>();
		javaFiles.add(new File(projectRoot + "/app/src/main/java/" + pkgName.replace(".", "/") + "/"));
		javaFiles.add(new File(FileUtil.getExternalStorageDir() + "/.blacklogics/data/" + sc_id + "/files/java/"));
		project.setJavaFiles(javaFiles);
		
		project.setManifestFile(new File(projectRoot + "/app/src/main/AndroidManifest.xml"));
		
		project.setVersionName(varName);
		
		syncFontsAndSoundsToAssets();
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
		
		project.setLogger(mLogger);
		project.setMinSdk(getMinSdk());
		project.setVersionCode(Integer.parseInt(varCode));
		project.setTargetSdk(getTargetSdk());
		
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
	
	public void toAssets() {
		Intent intent = new Intent(getApplicationContext(), ManageAssetsActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivity(intent);
	}
	
	public void toJava() {
		Intent intent = new Intent(getApplicationContext(), ManageJavaActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		intent.putExtra("pkgName", pkgName);
		startActivity(intent);
	}
	
	public void toNativelibs() {
		Intent intent = new Intent(getApplicationContext(), ManageNativelibsActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivity(intent);
	}
	
	public void toPermission() {
		Intent intent = new Intent(getApplicationContext(), com.nexusteam.blacklogics.permission.ManagePermissionActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivity(intent);
	}
	
	public void toResource() {
		Intent intent = new Intent(getApplicationContext(), ManageResourceActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivity(intent);
	}
	
	public void toProguard() {
		Intent intent = new Intent(getApplicationContext(), ManageProguardActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivity(intent);
	}
	
	public void toStringfog() {
		Intent intent = new Intent(getApplicationContext(), ManageStringfogActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivity(intent);
	}
	
	public void toLocalLibrary() {
		Intent intent = new Intent(getApplicationContext(), ManageLocalLibraryActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivity(intent);
	}
	
	public void toManageImageActivity() {
		Intent intent = new Intent(getApplicationContext(),
		com.nexusteam.blacklogics.editor.manage.image_manager.ManageImageActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivityForResult(intent, 231);
	}
	
	public void toManageFontActivity() {
		Intent intent = new Intent(getApplicationContext(), com.nexusteam.blacklogics.font_manager.ManageFontActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivity(intent);
	}
	
	public void toManageSoundActivity() {
		Intent intent = new Intent(getApplicationContext(), com.nexusteam.blacklogics.editor.manage.sound_manager.ManageSoundActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivityForResult(intent, 232);
	}
	
	public void toManageViewActivity() {
		Intent intent = new Intent(getApplicationContext(),
		com.nexusteam.blacklogics.manage.view.ManageViewActivity.class);
		intent.putExtra("sc_id", sc_id);
		intent.putExtra("compatUseYn", "Y");
		startActivityForResult(intent, 270);
	}
	
	public void toSrcViewer() {
		Intent intent = new Intent(getApplicationContext(), SrcViewerActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivity(intent);
	}
	
	public void toLibraryManager() {
		Intent intent = new Intent(getApplicationContext(), LibraryManagerActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivity(intent);
	}
	
	public void toEncryptManager() {
		Intent intent = new Intent(getApplicationContext(), ResourceEncryptActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivity(intent);
	}
	
	public void toEncryptDex() {
		Intent intent = new Intent(getApplicationContext(), DexEncryptionActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivity(intent);
	}
	
	public void toLogcatViewer() {
		Intent intent = new Intent(getApplicationContext(), LogcatActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivity(intent);
	}
	
	public void toManifestEditor() {
		Intent intent = new Intent(getApplicationContext(), AndroidManifestEditorActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sc_id", sc_id);
		startActivity(intent);
	}
	
	{
	}
	
	
	public void _q(final String _q) {
		Snackbar snackbar = Snackbar.make(anchor, "Show compile log", Snackbar.LENGTH_LONG);
		
		snackbar.setAnchorView(R.id.anchor);
		
		snackbar.setAction(R.string.common_word_show, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), CompileLogActivity.class);
				intent.putExtra("error", _q);
				intent.putExtra("sc_id", sc_id);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
			}
		});
		
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
	
	
	public void _setupViewPagerListener() {
		customViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				pos = position;
				
				if (position == 0) {
					if (projectFileSelector != null) {
						projectFileSelector.setFileType(0);
						projectFileSelector.a();
					}
				} else {
					if (projectFileSelector != null) {
						projectFileSelector.setFileType(1);
						projectFileSelector.a();
					}
				}
				
				tab_layout.getTabAt(position).select();
			}
			
			@Override
			public void onPageScrolled(int position, float offset, int offsetPx) {
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
	}
	
	public void _updateUndoRedoIcons() {
		ViewBuilderFragmentActivity viewFragment =
		(ViewBuilderFragmentActivity) getFragmentFromViewPager(0);
		
		boolean canUndo = false;
		boolean canRedo = false;
		
		if (viewFragment != null && viewFragment.viewEditor != null) {
			canUndo = viewFragment.viewEditor.canUndo();
			canRedo = viewFragment.viewEditor.canRedo();
		}
		
		applyUndoRedoIconState(undoMenuItem, canUndo);
		applyUndoRedoIconState(redoMenuItem, canRedo);
	}
	
	private void applyUndoRedoIconState(MenuItem item, boolean available) {
		if (item == null) return;
		
		item.setEnabled(available);
		if (item.getIcon() != null) {
			Drawable icon = item.getIcon().mutate();
			icon.setAlpha(available ? 255 : 97);
			item.setIcon(icon);
		}
	}
	
	
	public void _setCurrentActivity(final String _activityName) {
		if (_activityName == null || _activityName.trim().isEmpty()) return;
		final String act = _activityName.trim();
		if (act.equals(activityName) && act.equals(defaultAcName)
		&& layoutName != null && layoutName.equals(defaultLayName)) {
			applyActivitySwitchToFragments(act, layoutName);
			return;
		}
		
		setCurrentActivityName(act);
		activityName = act;
		defaultAcName = act;
		
		String linkedLayout = null;
		for (ActivityBean activity : activityList) {
			if (activity != null && act.equals(activity.getActivityName())) {
				activityBean = activity;
				linkedLayout = activity.getLayoutName();
				break;
			}
		}
		try {
			if (linkedLayout == null && complex != null) {
				linkedLayout = null;
			}
		} catch (Throwable ignore) {
		}
		if (linkedLayout == null && activityBean != null) {
			linkedLayout = activityBean.getLayoutName();
		}
		if (linkedLayout == null || linkedLayout.isEmpty()) {
			if ("MainActivity".equalsIgnoreCase(act)) linkedLayout = "main";
			else {
				String base = act.endsWith("Activity") ? act.substring(0, act.length() - 8) : act;
				if (base.length() > 0) {
					linkedLayout = Character.toLowerCase(base.charAt(0)) + (base.length() > 1 ? base.substring(1) : "");
				}
			}
		}
		
		if (activityBean != null) {
			activityBean.setActivityName(act);
			if (linkedLayout != null) activityBean.setLayoutName(linkedLayout);
		}
		if (currentActivityBean != null) {
			currentActivityBean.setActivityName(act);
			currentActivityBean.setMainActivity("MainActivity".equalsIgnoreCase(act));
		}
		if (activityData != null) {
			activityData.setActivityName(act);
			if (linkedLayout != null) activityData.setLayoutName(linkedLayout);
		}
		try {
			complex.setActivityName(act);
		} catch (Throwable ignore) {
		}
		try {
			com.nexusteam.blacklogics.project.ProjectDataHelper.setActivityName(this, act);
		} catch (Throwable ignore) {
		}
		
		if (activityNameChangeListener != null) {
			activityNameChangeListener.onActivityNameChanged(act);
			if (linkedLayout != null) {
				activityNameChangeListener.onLayoutNameChanged(linkedLayout);
			}
		}
		
		applyActivitySwitchToFragments(act, linkedLayout);
		
		if (projectFileSelector != null) {
			projectFileSelector.setFileType(0);
			if (linkedLayout != null) {
				projectFileSelector.setShownText(linkedLayout);
			}
		}
	}
	
	
	public void _setCurrentLayout(final String _layoutName) {
		if (_layoutName == null || _layoutName.trim().isEmpty()) return;
		String lay = _layoutName.trim();
		if (lay.endsWith(".xml")) lay = lay.substring(0, lay.length() - 4);
		final String layout = lay;
		
		if (!suppressEditorSave) {
			saveCurrentEditorLayoutNow();
		}
		setFragmentCustomFlag(false);
		
		setCurrentLayoutName(layout);
		defaultLayName = layoutName = layout;
		if (activityBean != null) activityBean.setLayoutName(layout);
		if (activityData != null) activityData.setLayoutName(layout);
		
		String linkedAct = activityName;
		for (ActivityBean activity : activityList) {
			if (activity != null && layout.equals(activity.getLayoutName())) {
				linkedAct = activity.getActivityName();
				activityBean = activity;
				break;
			}
		}
		try {
			String fromComplex = null;
			if (fromComplex != null && fromComplex.length() > 0) linkedAct = fromComplex;
		} catch (Throwable ignore) {
		}
		
		if (linkedAct != null && !linkedAct.isEmpty() && !linkedAct.equals(activityName)) {
			activityName = linkedAct;
			defaultAcName = linkedAct;
			setCurrentActivityName(linkedAct);
			try {
				complex.setActivityName(linkedAct);
			} catch (Throwable ignore) {
			}
			try {
				com.nexusteam.blacklogics.project.ProjectDataHelper.setActivityName(this, linkedAct);
			} catch (Throwable ignore) {
			}
		}
		
		if (activityNameChangeListener != null) {
			activityNameChangeListener.onLayoutNameChanged(layout);
			if (linkedAct != null) {
				activityNameChangeListener.onActivityNameChanged(linkedAct);
			}
		}
		
		applyActivitySwitchToFragments(linkedAct, layout);
		
		if (projectFileSelector != null) {
			projectFileSelector.setFileType(0);
			projectFileSelector.setShownText(layout);
		}
	}
	
	private void applyActivitySwitchToFragments(final String actName, final String layoutName) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				try {
					final ViewBuilderFragmentActivity viewFragment =
					(ViewBuilderFragmentActivity) getFragmentFromViewPager(0);
					if (viewFragment != null) {
						String wantLay = layoutName;
						if (wantLay != null && wantLay.endsWith(".xml")) {
							wantLay = wantLay.substring(0, wantLay.length() - 4);
						}
						String curLay = viewFragment.layoutName;
						if (curLay != null && curLay.endsWith(".xml")) {
							curLay = curLay.substring(0, curLay.length() - 4);
						}
						boolean layoutChanged = wantLay != null
						&& !wantLay.equals(curLay);
						boolean activityChanged = actName != null
						&& !actName.equals(viewFragment.activityName);
						boolean customModeChanged = false;
						
						
						if (actName != null && !viewFragment.isCustomLayout) {
							viewFragment.activityName = actName;
						}
						if (wantLay != null) {
							viewFragment.layoutName = wantLay;
							try {
								if (viewFragment.viewEditor != null
								&& viewFragment.viewEditor.idManager != null) {
									viewFragment.viewEditor.idManager.clearAllIds();
								}
							} catch (Throwable ignore) {
							}
							try {
								if (viewFragment.viewEditor != null
								&& viewFragment.viewEditor.tv_view_name != null) {
									viewFragment.viewEditor.tv_view_name.setText(wantLay + ".xml");
								}
							} catch (Throwable ignore) {
							}
						}
						if (layoutChanged || activityChanged || viewFragment.isCustomLayout) {
							viewFragment.loadLayout();
							try {
								viewFragment.u();
							} catch (Throwable ignore) {
							}
						}
					}
				} catch (Throwable t) {
					android.util.Log.e("DesignActivity", "view switch", t);
				}
				
				try {
					if (EventFragmentActivity.eventFragmentActivity != null) {
						EventFragmentActivity.myData = actName != null ? actName : activityName;
						EventFragmentActivity.eventFragmentActivity.displaySelectedEvents();
					}
				} catch (Throwable ignore) {
				}
				
				try {
					notifyComponentListActivityChanged(actName != null ? actName : activityName);
				} catch (Throwable t) {
					android.util.Log.w("DesignActivity", "component refresh on switch", t);
				}
				
				try {
					if (ComponentFragmentActivity.componentFragmentActivity != null) {
						ComponentFragmentActivity.componentFragmentActivity.refreshUi();
					}
				} catch (Throwable ignore) {
				}
			}
		});
	}
	
	
	private void setupActivityFileSystem() {
		try {
			activityFileController = new ActivityFileController(this, sc_id, complex);
			activityFileController.setOnProjectFileChangeListener(new OnProjectFileChangeListener() {
				@Override
				public void onProjectFileSelected(int type, ProjectFileBean file) {
					if (file == null) return;
					switchToProjectFile(file);
				}
			});
			activityFileController.ensureProjectFileListBootstrapped();
			activityFileController.syncFromComplex();
		} catch (Throwable t) {
			android.util.Log.e("DesignActivity", "setupActivityFileSystem", t);
		}
		try {
			projectFileSelector = findViewById(R.id.file_selector);
			if (projectFileSelector != null) {
				projectFileSelector.setScId(sc_id);
				if (activityFileController != null) {
					projectFileSelector.setOnSelectedFileChangeListener(
					new HoBridge(activityFileController.getChangeListener()));
				}
			}
		} catch (Throwable t) {
			android.util.Log.w("DesignActivity", "no file_selector in layout", t);
		}
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// Edit Properties (See All) — apply attributes to selected widget
		try {
			if (requestCode == com.nexusteam.blacklogics.sdk.layouteditor.EditPropertiesLauncher.REQ_EDIT_PROPERTIES) {
				ViewBuilderFragmentActivity vf =
						(ViewBuilderFragmentActivity) getFragmentFromViewPager(0);
				if (vf != null && vf.viewEditor != null) {
					vf.viewEditor.handleEditPropertiesResult(requestCode, resultCode, data);
				}
				return;
			}
		} catch (Throwable t) {
			android.util.Log.w("DesignActivity", "EditProperties result", t);
		}

		if ((requestCode == 263 || requestCode == 264 || requestCode == 266 || requestCode == 270) && resultCode == RESULT_OK) {
			try {
				if (data != null && data.hasExtra("project_file")) {
					com.nexusteam.internal.beans.ProjectFileBean bean =
					data.getParcelableExtra("project_file");
					if (bean != null) {
						try {
							com.nexusteam.internal.ly ly = com.nexusteam.internal.ma.b(sc_id);
							if (ly != null) {
								boolean found = false;
								java.util.ArrayList list = bean.fileType == 0 ? ly.a() : ly.b();
								if (list != null) {
									for (int idx = 0; idx < list.size(); idx++) {
										Object o = list.get(idx);
										if (o instanceof com.nexusteam.internal.beans.ProjectFileBean
										&& bean.fileName != null
										&& bean.fileName.equals(((com.nexusteam.internal.beans.ProjectFileBean) o).fileName)) {
											list.set(idx, bean);
											found = true;
											break;
										}
									}
								}
								if (!found) {
									ly.a(bean);
								}
								ly.e();
							}
						} catch (Throwable ignore) {
						}
						try {
							com.nexusteam.blacklogics.activityfile.ActivitySyncBridge
							.syncNewOrUpdatedActivity(DesignActivity.this, sc_id, complex, bean);
						} catch (Throwable ignore) {
						}
						try {
							if (projectFileSelector != null) projectFileSelector.setXmlFileName(bean);
						} catch (Throwable ignore) {
						}
						try {
							switchToProjectFile(bean);
						} catch (Throwable ignore) {
						}
					}
				}
			} catch (Throwable t) {
				android.util.Log.e("DesignActivity", "AddView result", t);
			}
			try {
				if (projectFileSelector != null) projectFileSelector.a();
			} catch (Throwable ignore) {
			}
			try {
				if (activityFileController != null) {
					activityFileController.reload();
					activityFileController.handleActivityResult(requestCode, resultCode, data);
				}
			} catch (Throwable ignore) {
			}
			try {
				if (activityListChangedListener != null) activityListChangedListener.onActivityListChanged();
			} catch (Throwable ignore) {
			}
			try {
				if (pendingActivitySavedRunnable != null) {
					pendingActivitySavedRunnable.run();
					pendingActivitySavedRunnable = null;
				}
			} catch (Throwable ignore) {
			}
			try {
				if (ViewBuilderFragmentActivity.instance != null) {
					try {
						if (ViewBuilderFragmentActivity.instance.activityConfig != null && sc_id != null) {
							ViewBuilderFragmentActivity.instance.activityConfig.init(
							DesignActivity.this, sc_id);
						}
					} catch (Throwable ignore) {}
					try {
						ViewBuilderFragmentActivity.instance.u();
					} catch (Throwable ignore) {}
					try {
						ViewBuilderFragmentActivity.instance.refreshUI();
					} catch (Throwable ignore) {}
					if (ViewBuilderFragmentActivity.instance.viewEditor != null) {
						ViewBuilderFragmentActivity.instance.viewEditor.syncActivityUiPreview();
					}
				}
			} catch (Throwable ignore) {
			}
			return;
		}
		
		if (requestCode == 270 && resultCode == RESULT_OK) {
			try {
				if (projectFileSelector != null) projectFileSelector.a();
			} catch (Throwable ignore) {
			}
			try {
				if (activityFileController != null) {
					activityFileController.reload();
					if (data != null) {
						activityFileController.handleActivityResult(requestCode, resultCode, data);
					}
				}
			} catch (Throwable t) {
				android.util.Log.e("DesignActivity", "ManageView result", t);
			}
			return;
		}
		try {
			if (activityFileController != null
			&& activityFileController.handleActivityResult(requestCode, resultCode, data)) {
				return;
			}
		} catch (Throwable ignore) {
		}
	}
	
	public void switchToProjectFile(com.nexusteam.internal.beans.ProjectFileBean bean) {
		if (bean == null) return;
		saveCurrentEditorLayoutNow();
		suppressEditorSave = true;
		try {
			if (bean.fileType == 0) {
				String java = bean.getJavaName();
				String xml = bean.getXmlName();
				if (xml != null && xml.endsWith(".xml")) xml = xml.substring(0, xml.length() - 4);
				if (java != null && java.endsWith(".java")) java = java.substring(0, java.length() - 5);
				setFragmentCustomFlag(false);
				if (java != null && java.length() > 0) _setCurrentActivity(java);
				if (xml != null && xml.length() > 0) _setCurrentLayout(xml);
			} else if (bean.fileType == 1 || bean.fileType == 2) {
				String xml = bean.getXmlName();
				if (xml == null || xml.isEmpty()) xml = bean.fileName;
				if (xml != null && xml.endsWith(".xml")) xml = xml.substring(0, xml.length() - 4);
				setFragmentCustomFlag(true);
				if (xml != null && xml.length() > 0) {
					_setCurrentLayoutForCustom(xml);
				}
			}
		} finally {
			suppressEditorSave = false;
		}
	}
	
	private void saveCurrentEditorLayoutNow() {
		try {
			ViewBuilderFragmentActivity vf =
			(ViewBuilderFragmentActivity) getFragmentFromViewPager(0);
			if (vf != null) vf.saveLayout();
		} catch (Throwable t) {
			android.util.Log.e("DesignActivity", "saveCurrentEditorLayoutNow", t);
		}
	}
	
	private void setFragmentCustomFlag(boolean isCustom) {
		try {
			ViewBuilderFragmentActivity vf =
			(ViewBuilderFragmentActivity) getFragmentFromViewPager(0);
			if (vf != null) {
				vf.isCustomLayout = isCustom;
			}
		} catch (Throwable ignore) {}
	}
	
	public void _setCurrentLayoutForCustom(final String _layoutName) {
		if (_layoutName == null || _layoutName.trim().isEmpty()) return;
		String lay = _layoutName.trim();
		if (lay.endsWith(".xml")) lay = lay.substring(0, lay.length() - 4);
		final String layout = lay;
		
		setCurrentLayoutName(layout);
		defaultLayName = layoutName = layout;
		if (activityBean != null) activityBean.setLayoutName(layout);
		if (activityData != null) activityData.setLayoutName(layout);
		
		if (activityNameChangeListener != null) {
			activityNameChangeListener.onLayoutNameChanged(layout);
		}
		
		setFragmentCustomFlag(true);
		applyActivitySwitchToFragments(activityName, layout);
		
		if (projectFileSelector != null) {
			projectFileSelector.setFileType(0);
			String shown = layout.endsWith(".xml") ? layout : layout + ".xml";
			projectFileSelector.setShownText(shown);
		}
	}
	
	public void _generators() {
	}
	
	@Override
	public void generateJavaCode() {
		flushDesignBeforeRun();
	}
	
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
	}
	
	public interface OnActivityNameChangeListener {
		void onActivityNameChanged(String newActivityName);
		
		void onLayoutNameChanged(String newLayoutName);
	}
	
	public void setOnActivityNameChangeListener(OnActivityNameChangeListener listener) {
		this.activityNameChangeListener = listener;
	}
	
	public void toAppCompatManager() {
		Intent intent = new Intent(getApplicationContext(), AppCompatManagerActivity.class);
		intent.putExtra("sc_id", sc_id);   // use whatever your project-id field is called
		startActivity(intent);
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
	
	private void flushDesignBeforeRun() {
		try {
			saveCurrentEditorLayoutNow();
		} catch (Throwable t) {
			android.util.Log.e("DesignActivity", "flush: saveLayout failed", t);
		}
		
		try {
			SourceCodeGenerator gen = new SourceCodeGenerator(this, sc_id);
			gen.extractAndSaveAllXmlCodes();
			gen.extractAllJavaCodes();
		} catch (Throwable t) {
			android.util.Log.e("DesignActivity", "flush: extract code failed", t);
			try {
				Toast.makeText(this, "Layout export failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
			} catch (Throwable ignore) {}
		}
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
