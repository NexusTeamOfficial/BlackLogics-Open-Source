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
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.multidex.*;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.viewpager.*;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnAdapterChangeListener;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import androidx.viewpager2.*;
import com.besome.sketch.*;
import com.bumptech.glide.*;
import com.bumptech.glide.gifdecoder.*;
import com.example.myapp.*;
import com.github.angads25.filepicker.*;
import com.google.android.material.*;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.*;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.*;
import com.googlecode.d2j.*;
import com.larswerkman.holocolorpicker.*;
import de.hdodenhof.circleimageview.*;
import io.github.rosemoe.editor.*;
import io.github.rosemoe.sora.*;
import io.github.rosemoe.sora.langs.java.*;
import io.github.rosemoe.sora.langs.textmate.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.regex.*;
import org.antlr.v4.runtime.*;
import org.benf.cfr.reader.*;
import org.eclipse.jdt.*;
import org.json.*;
import a.a.a.Lx;
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
import com.shapun.layouteditor.XmlLayoutGenerator;
import com.shapun.layouteditor.ViewEditor;
import java.lang.ref.WeakReference;
import com.besome.blacklogics.lib.base.BaseActivity;
import com.besome.blacklogics.beans.ActivityBean;
import com.besome.blacklogics.model.ActivityData;
import android.util.Log;
import java.net.URI;
import java.io.File;
import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.util.Linkify;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewAnimator;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.besome.blacklogics.beans.ProjectBean;
import com.besome.blacklogics.custom_blocks.CustomBlocksDialog;
import com.besome.blacklogics.tools.CompileLogActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nexusteam.internal.os.layouteditor.adapter.FileListAdapter;
import com.nexusteam.internal.os.layouteditor.model.FileItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import dev.aldi.sayuti.editor.manage.ManageLocalLibraryActivity;
import mod.agus.jcoderz.editor.manage.permission.ManagePermissionActivity;
import mod.agus.jcoderz.editor.manage.resource.ManageResourceActivity;
import mod.hey.studios.activity.managers.assets.ManageAssetsActivity;
import mod.hey.studios.activity.managers.java.ManageJavaActivity;
import mod.hey.studios.activity.managers.nativelib.ManageNativelibsActivity;
import mod.hey.studios.build.BuildSettings;
import mod.hey.studios.build.BuildSettingsDialog;
import mod.hey.studios.logic.SourceCodeDialog;
import mod.hey.studios.project.proguard.ManageProguardActivity;
import mod.hey.studios.project.stringfog.ManageStringfogActivity;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.activities.tools.ConfigActivity;
import mod.jbk.diagnostic.CompileErrorSaver;


public class DesignActivity extends BaseActivity implements CompilerLogListener, CustomBlocksDialog.OnBlocksSelectedListener, Complex.SyncData, WidgetInteractionListener {
	
	private Consumer<View> declareWidget;
	private Consumer<View> initializeWidget;
	private static Map<String, Map<String, List<String>>> variableMap = new HashMap<>();
	
	// Add these as class members in DesignActivity
	private int savedXmlPosition = 0;
	private int savedJavaPosition = 0;
	private int savedComponentPosition = 0; // If third tab needs it
	
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
	
	public CodeGenerator codeGenerator;
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
	
	private ArrayList<String> s = new ArrayList<>();
	
	private LinearLayoutCompat designToolbar;
	private LinearLayoutCompat toolbar_improved;
	private TabLayout tab_layout;
	private ViewPager customViewPager;
	private RecyclerView ah;
	private LinearLayoutCompat anchor;
	private LinearLayoutCompat control_panel;
	public static LinearLayoutCompat ll_properties;
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
	private AppCompatSpinner file_spinner;
	private ImageView project_detail;
	private Space linear15;
	private MaterialButton execute_button;
	private DesignDrawer _drawer_drawer_layout_base_d;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.design);
		initialize(_savedInstanceState);
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
		control_panel = findViewById(R.id.control_panel);
		ll_properties = findViewById(R.id.ll_properties);
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
		file_spinner = findViewById(R.id.file_spinner);
		project_detail = findViewById(R.id.project_detail);
		linear15 = findViewById(R.id.linear15);
		execute_button = findViewById(R.id.execute_button);
		_drawer_drawer_layout_base_d = _nav_view.findViewById(R.id.drawer_layout_base_d);
		
		img_icon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				ViewBuilderFragmentActivity viewFragment =
				(ViewBuilderFragmentActivity) adapter.getFragment(0);
				
				if (viewFragment == null) {
					// Safety check (normal me kabhi null nahi aayega)
					exibirMensagemEdt("Exit from this project", "Do you want to save before quitting?");
					return;
				}
				
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
				(ViewBuilderFragmentActivity) adapter.getFragment(0);
				
				if (viewFragment != null) {
					viewFragment.viewEditor.undo();
				}
				
			}
		});
		
		redoIcon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				ViewBuilderFragmentActivity viewFragment =
				(ViewBuilderFragmentActivity) adapter.getFragment(0);
				
				if (viewFragment != null) {
					viewFragment.viewEditor.redo();
				}
				
			}
		});
		
		save_logic_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				try{
					generateJavaCode();
					generateXmlLayout();
					saveView();
				}catch(Exception e){
					TheBlockLogicsUtil.showToast(getApplicationContext(),"Projeto Save Failed: " + e.toString());
				}
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
				
				popupMenu.setOnMenuItemClickListener(item -> {
					switch (item.getItemId()) {
						case 1:
						new BuildSettingsDialog(DesignActivity.this, sc_id).show();
						break;
						
						case 2:
						new Thread(() -> {
							FileUtil.deleteFile(baseMYSC);
							runOnUiThread(() ->
							SketchwareUtil.toast("Done cleaning temporary files!"));
						}).start();
						break;
						
						case 3:
						new CompileErrorSaver(sc_id).showLastErrors(DesignActivity.this);
						break;
						
						case 4:
						if (FileUtil.isExistFile(apkPath)) {
							installBuiltApk();
						} else {
							SketchwareUtil.toast("APK doesn't exist anymore");
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
				});
				
				popupMenu.show();
				
			}
		});
		
		execute_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_processApk();
			}
		});
	}
	
	private void initializeLogic() {
		context = DesignActivity.this;
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
		codeGenerator = new CodeGenerator(this, sc_id, pkgName, complex, activityBean, currentActivityBean);
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
		_ensureJavaCode("FileUtil", Lx.e(pkgName));
		_ensureJavaCode("BlackLogicsUtil", Lx.i(pkgName));
		_setupSpinnerListener();
		_setupViewPagerListener();
		uiUpdateManager = new UiUpdateManager(undoIcon, redoIcon, customViewPager);
		_updateUndoRedoIcons();
		_setCurrentActivity("MainActivity");
		generateXmlLayout();
		generateJavaCode();
	}
	
	@Override
	public void onBackPressed() {
		ViewBuilderFragmentActivity viewFragment =
		(ViewBuilderFragmentActivity) adapter.getFragment(0);
		
		if (viewFragment == null) {
			// Safety check (normal me kabhi null nahi aayega)
			exibirMensagemEdt("Exit from this project", "Do you want to save before quitting?");
			return;
		}
		
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
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.design_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		final int _id = item.getItemId();
		final String _title = (String) item.getTitle();
		if (item.getItemId() == R.id.action_more) {
			showPopupMenu(findViewById(R.id.action_more));
			return true;
		}
		if (item.getItemId() == R.id.action_save) {
			if (saveView()) {
				TheBlockLogicsUtil.showToast(getApplicationContext(),"Projeto Saved!");
				//		finish();
			}
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
		super.onRestoreInstanceState(savedInstanceState);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		uiUpdateManager.cleanup();
		generateJavaCode();
		generateXmlLayout();
		
	}
	public void _b() {
	}
	public static class WidgetClickListener implements OnClickListener
	{
		@Override
		public void onClick(View view)
		{
			WidgetPropertyManager.selectWidget(view);
		}
	}
	private void showPopupMenu(View anchor) {
		PopupMenu popup = new PopupMenu(this, anchor);
		popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
		popup.setOnMenuItemClickListener(item -> {
			switch (item.getItemId()) {
				case R.id.action_cast:
				DesignSharedViewModel viewModel = new ViewModelProvider(this)
				.get(DesignSharedViewModel.class);
				viewModel.triggerSave();
				Toast.makeText(this, "Cast Selected", Toast.LENGTH_SHORT).show();
				return true;
				case R.id.action_create_activity:
				///   showCreateActivityDialog();
				return true;
				case R.id.action_about:
				Toast.makeText(this, "About Selected", Toast.LENGTH_SHORT).show();
				return true;
				default:
				return false;
			}
		});
		popup.show();
	}
	/**
 * Saves the current layout via the viewFragment's viewEditor.
 */
	public boolean saveView() {
		try {
			showLoadingDialog(this);
			
			// Get the ViewEditorFragmentActivity (fragment containing the viewEditor)
			ViewBuilderFragmentActivity viewFragment = (ViewBuilderFragmentActivity) adapter.getFragment(0);
			
			if (viewFragment != null && viewFragment.viewEditor != null) {
				viewFragment.saveLayout();
				TheBlockLogicsUtil.showToast(getApplicationContext(),"Projeto Saved!");
			} else {
				TheBlockLogicsUtil.showToast(getApplicationContext(),"Projeto Failed!");
			}
			
		} catch (Exception e) {
			TheBlockLogicsUtil.showToast(getApplicationContext(),"Save Error!");
			Log.e("SaveProject", "Error saving project", e);
		} finally {
			dismissLoadingDialog();
		}
		
		return true;
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
				ViewBuilderFragmentActivity viewFragment =
				(ViewBuilderFragmentActivity) adapter.getFragment(0);
				if (viewFragment != null) {
					//	activity.saveLayout();
					viewFragment.saveLayout();
				}
				TheBlockLogicsUtil.showToast(getApplicationContext(),"Projeto Saved!");
				finish();
			}
		});
	}
	
	/*
TUDO : generateXmlLayout
**/
	/**
 * Generates XML layout code for the current activity, using direct View properties.
 */
	public void generateXmlLayout() {
		if (currentActivityBean == null) {
			//System.err.println("No design data available to generate XML.");
			return;
		}
		
		String layoutName = currentActivityBean.getLayoutName();
		generateAndSaveXml(layoutName);
	}
	
	/**
 * Generates XML layout code for a given layout name.
 */
	@Override
	public void generateXmlCode(String layoutName) {
		if (currentActivityBean == null) {
			//System.err.println("No design data available to generate XML.");
			return;
		}
		
		generateAndSaveXml(layoutName);
	}
	
	/**
 * Shared helper method to generate and save XML code using ViewEditor.
 */
	private void generateAndSaveXml(String layoutName) {
		try {
			// Create ViewEditor
			ViewEditor viewEditor = new ViewEditor(this);
			viewEditor.setPath(projectPath);
			viewEditor.setScId(sc_id);
			viewEditor.a(ll_properties);
			viewEditor.setDesignActivity(this);
			
			// Generate formatted XML
			String formattedXml = ViewBuilderFragmentActivity.instance.viewEditor.getXMLCode();
			
			if (formattedXml == null || formattedXml.trim().isEmpty()) {
				// System.err.println("Failed to generate XML layout. Please check your design.");
				return;
			}
			
			// Save XML using complex object
			complex.setXmlCode(layoutName, formattedXml);
			
		} catch (Exception e) {
			e.printStackTrace();
			//System.err.println("An unexpected error occurred while generating XML: " + e.getMessage());
		}
	}
	
	public String getXmlCode() {
		try {
			ViewEditor viewEditor = new ViewEditor(this);
			viewEditor.setPath(projectPath);
			viewEditor.setScId(sc_id);
			viewEditor.a(ll_properties);
			viewEditor.setDesignActivity(this);
			
			String formattedXml = ViewBuilderFragmentActivity.instance.viewEditor.getXMLCode();
			
			if (formattedXml == null || formattedXml.trim().isEmpty()) {
				return "Failed to generate XML layout. Please check your design.";
			}
			
			return formattedXml;
			
		} catch (Exception e) {
			e.printStackTrace();
			return "Error generating XML: " + e.getMessage();
		}
	}
	
	
	
	private String formatXmlAttributes(String xml) {
		StringBuilder formatted = new StringBuilder();
		String[] lines = xml.split("\n");
		for (String line : lines) {
			if (line.trim().startsWith("<") && line.contains(" ")) {
				int firstSpace = line.indexOf(" ");
				if (firstSpace != -1 && !line.trim().startsWith("<?xml")) {
					String tagStart = line.substring(0, firstSpace);
					String rest = line.substring(firstSpace).trim();
					formatted.append(tagStart).append("\n");
					String[] attributes = rest.split("\" ");
					for (int i = 0; i < attributes.length; i++) {
						String attr = attributes[i].trim();
						if (!attr.endsWith("\"")) {
							attr = attr + "\"";
						}
						if (i < attributes.length - 1 || rest.endsWith("\"")) {
							formatted.append("  ").append(attr).append("\n");
						} else {
							formatted.append("  ").append(attr);
						}
					}
					if (line.endsWith("/>")) {
						formatted.append("/>\n");
					} else if (line.contains(">") && !line.endsWith("/>")) {
						formatted.append(">\n");
					}
				} else {
					formatted.append(line).append("\n");
				}
			} else {
				formatted.append(line).append("\n");
			}
		}
		return formatted.toString().trim();
	}
	
	private Element createWidgetElement(Document doc, View widget) {
		return null;
	}
	
	// Helper methods (unchanged or slightly modified)
	private String getDimensionString(int dimension) {
		if (dimension == ViewGroup.LayoutParams.MATCH_PARENT) return "match_parent";
		if (dimension == ViewGroup.LayoutParams.WRAP_CONTENT) return "wrap_content";
		return dimension + "dp";
	}
	
	private String visibilityToString(int visibility) {
		switch (visibility) {
			case View.VISIBLE: return "visible";
			case View.INVISIBLE: return "invisible";
			case View.GONE: return "gone";
			default: return "visible";
		}
	}
	
	private String gravityToString(int gravity) {
		List<String> parts = new ArrayList<>();
		if ((gravity & Gravity.LEFT) == Gravity.LEFT) parts.add("left");
		if ((gravity & Gravity.RIGHT) == Gravity.RIGHT) parts.add("right");
		if ((gravity & Gravity.TOP) == Gravity.TOP) parts.add("top");
		if ((gravity & Gravity.BOTTOM) == Gravity.BOTTOM) parts.add("bottom");
		if ((gravity & Gravity.CENTER) == Gravity.CENTER) parts.add("center");
		return parts.isEmpty() ? "start" : String.join("|", parts);
	}
	
	private String typefaceStyleToString(int style) {
		switch (style) {
			case android.graphics.Typeface.BOLD: return "bold";
			case android.graphics.Typeface.ITALIC: return "italic";
			case android.graphics.Typeface.BOLD_ITALIC: return "bold|italic";
			default: return "normal";
		}
	}
	
	private String ellipsizeToString(TextUtils.TruncateAt ellipsize) {
		if (ellipsize == null) return "none";
		switch (ellipsize) {
			case START: return "start";
			case MIDDLE: return "middle";
			case END: return "end";
			case MARQUEE: return "marquee";
			default: return "none";
		}
	}
	
	private String autoLinkToString(int autoLinkMask) {
		List<String> links = new ArrayList<>();
		if ((autoLinkMask & Linkify.WEB_URLS) != 0) links.add("web");
		if ((autoLinkMask & Linkify.EMAIL_ADDRESSES) != 0) links.add("email");
		if ((autoLinkMask & Linkify.PHONE_NUMBERS) != 0) links.add("phone");
		if ((autoLinkMask & Linkify.MAP_ADDRESSES) != 0) links.add("map");
		return links.isEmpty() ? "none" : String.join("|", links);
	}
	private String getWidgetType(View widget) {
		return widget.getClass().getSimpleName();
	}
	
	/**
TUDO ; HELPER showCreateActivityDialog
**/
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
		Dialog dialog = new Dialog(this, R.style.TransparentDialogTheme);
		dialog.setContentView(R.layout.dialog_custom_view);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setCancelable(true);
		
		RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView);
		FloatingActionButton fab = dialog.findViewById(R.id.fabAddView);
		MaterialButtonToggleGroup toggleGroup = dialog.findViewById(R.id.toggleGroup);
		MaterialButton tabView = dialog.findViewById(R.id.tabView);
		MaterialButton tabCustom = dialog.findViewById(R.id.tabCustomView);
		
		tabView.setChecked(true);
		
		// Common Item Selection Logic
		Consumer<ViewItem> onItemSelected = item -> {
			dialog.dismiss();
			handleItemSelection(item);
		};
		
		// Toggle group (View / Custom View)
		toggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
			if (!isChecked) return;
			
			if (checkedId == R.id.tabView) {
				complex.setupViewAdapter(recyclerView, onItemSelected::accept);
			} else if (checkedId == R.id.tabCustomView) {
				complex.setupCustomViewRecycler(recyclerView, viewName -> {
					ViewItem customItem = new ViewItem(
					viewName,
					viewName.toLowerCase() + ".xml",
					"",
					""
					);
					onItemSelected.accept(customItem);
				});
			}
		});
		
		// Default adapter load
		complex.setupViewAdapter(recyclerView, onItemSelected::accept);
		
		// FAB click
		fab.setOnClickListener(v -> {
			if (tabView.isChecked()) {
				yq(false, null);
			} else {
				showCreateCustomViewDialog();
			}
		});
		
		dialog.show();
	}
	
	/**
 * Handles selection of a ViewItem from RecyclerView
 */
	private void handleItemSelection(ViewItem item) {
		if (DesignActivity.abc != null) {
			complex.setXmlCode(activityBean.getLayoutName(), getXmlCode());
			complex.setJavaCode(activityBean.getActivityName(), getJavaCode());
		}
		
		ViewBuilderFragmentActivity viewFragment =
		(ViewBuilderFragmentActivity) adapter.getFragment(0);
		
		if (viewFragment != null) {
			if (!item.getXmlFileName().isEmpty()) {
				updateLayoutName(viewFragment, item);
			}
			if (!item.getJavaName().isEmpty()) {
				updateActivityName(viewFragment, item);
			}
			viewFragment.loadLayout();
		}
		
		// Refresh other fragments
		if (ComponentFragmentActivity.componentFragmentActivity != null) {
			ComponentFragmentActivity.componentFragmentActivity.c();
		}
		if (EventFragmentActivity.eventFragmentActivity != null) {
			EventFragmentActivity.eventFragmentActivity.displaySelectedEvents();
		}
		
		// Check if MainActivity
		boolean isMain = "MainActivity".equalsIgnoreCase(item.getJavaName());
		isMainActivity = isMain;
		currentActivityBean.setMainActivity(isMain);
		
		// Listener for UI update
		activityBean.addListener(updated -> {
			if (viewFragment != null) {
				viewFragment.viewEditor.tv_view_name.setText(item.getXmlName() + ".xml");
			}
		});
		
		// Update Spinner selection safely
		runOnUiThread(() -> {
			if (file_spinner != null && file_spinner.getAdapter() != null) {
				String currentSelection = (String) file_spinner.getSelectedItem();
				if (currentSelection != null) {
					setSpinnerSelection(file_spinner, currentSelection);
				}
			}
		});
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
		com.besome.blacklogics.project.ProjectDataHelper.setActivityName(this, defaultAcName);
		
		// Listener को notify करें
		if (activityNameChangeListener != null) {
			activityNameChangeListener.onActivityNameChanged(item.getJavaName());
		}
	}
	
	// Layout name change के लिए भी similar method
	private void updateLayoutName(ViewBuilderFragmentActivity viewFragment, ViewItem item) {
		viewFragment.layoutName = item.getXmlFileName();
		currentActivityBean.setLayoutName(item.getXmlName());
		defaultLayName = layoutName = item.getXmlName();
		activityBean.setLayoutName(item.getXmlName());
		activityData.setLayoutName(item.getXmlFileName());
		viewFragment.viewEditor.tv_view_name.setText(item.getXmlFileName());
		
		// Listener को notify करें
		if (activityNameChangeListener != null) {
			activityNameChangeListener.onLayoutNameChanged(item.getXmlName());
		}
	}
	private void showCreateCustomViewDialog() {
		Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.create_custom_view); // New Material3 layout
		
		// Transparent background + dim
		if (dialog.getWindow() != null) {
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			dialog.getWindow().setDimAmount(0.5f);
		}
		
		// Find views
		TextInputEditText editText = dialog.findViewById(R.id.edittext3);
		MaterialButton cancelButton = dialog.findViewById(R.id.button5);
		MaterialButton addButton = dialog.findViewById(R.id.button6);
		
		// Make EditText focusable
		editText.setFocusable(true);
		editText.setFocusableInTouchMode(true);
		
		// Cancel button
		cancelButton.setOnClickListener(v -> dialog.dismiss());
		
		// Add button
		addButton.setOnClickListener(v -> {
			String viewName = editText.getText().toString().trim();
			if (viewName.isEmpty()) {
				Toast.makeText(this, "Please enter a view name", Toast.LENGTH_SHORT).show();
			} else if (!viewName.matches("^[a-zA-Z][a-zA-Z0-9]*$")) {
				Toast.makeText(this, "View name must start with a letter and contain only letters and numbers", Toast.LENGTH_SHORT).show();
			} else if (complex.hasCustomView(viewName)) {
				Toast.makeText(this, "View name already exists", Toast.LENGTH_SHORT).show();
			} else {
				// Save custom view
				complex.setCustomViewName(viewName);
				// Toast.makeText(this, "Custom view '" + viewName + "' added", Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		});
		
		dialog.show();
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
	
	public static String getScId() {
		return scId;
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
	/**
 * Displays a custom transparent loading dialog.
 */
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
		try {
			String blockLogicPath = projectPath + "/block_logic/project_logic.json";
			String prettyLogicPath = projectPath + "/block_logic/" + activityName + "_logic_pretty.json";
			
			FileUtil.makeDir(projectPath + "/block_logic/");
			
			// Read existing logic (if encoded file exists)
			Map<String, Map<String, String>> logicMap = new HashMap<>();
			if (FileUtil.isExistFile(blockLogicPath)) {
				String encodedJson = FileUtil.readFile(blockLogicPath);
				String decodedJson = new String(Base64.decode(encodedJson, Base64.DEFAULT));
				Type mapType = new TypeToken<Map<String, Map<String, String>>>() {}.getType();
				logicMap = new Gson().fromJson(decodedJson, mapType);
			}
			
			// Update logic
			Map<String, String> activityLogic = logicMap.getOrDefault(activityName, new HashMap<>());
			activityLogic.put(activityName, logic);
			logicMap.put(activityName, activityLogic);
			
			// Create a pretty-printed JSON first
			Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();
			String prettyJson = gsonPretty.toJson(logicMap);
			
			// Save encoded version (Base64 of pretty JSON)
			String encodedJson = Base64.encodeToString(prettyJson.getBytes(), Base64.DEFAULT);
			FileUtil.writeFile(blockLogicPath, encodedJson);
			
			// Save pretty-printed version (plain readable JSON)
			//   FileUtil.writeFile(prettyLogicPath, prettyJson);
			
		} catch (Exception e) {
			// TheBlockLogicsUtil.showToast(TheBlockLogicsUtil.getContext(), "Error saving block logic: " + e.toString());
		}
	}
	private String getBlockLogicForWidget(String widgetId) {
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
	public static boolean isBlockLogicAvailable(String activityName, String widgetId) {
		try {
			String blockLogicPath = projectPath + "/block_logic/project_logic.json";
			if (FileUtil.isExistFile(blockLogicPath)) {
				String encodedJson = FileUtil.readFile(blockLogicPath);
				String decodedJson = new String(Base64.decode(encodedJson, Base64.DEFAULT));
				Type mapType = new TypeToken<Map<String, Map<String, String>>>(){}.getType();
				Map<String, Map<String, String>> logicMap = new Gson().fromJson(decodedJson, mapType);
				Map<String, String> activityLogic = logicMap.get(activityName);
				return activityLogic != null && activityLogic.containsKey(widgetId) && !activityLogic.get(widgetId).isEmpty();
			}
		} catch (Exception e) {
			// Log or handle error if needed
		}
		return false;
	}
	public static void saveBlockLogicForWidget(String activityName, String widgetId, String logic) {
		try {
			String blockLogicPath = projectPath + "/block_logic/project_logic.json";
			FileUtil.makeDir(projectPath + "/block_logic/");
			
			// Read existing logic
			Map<String, Map<String, String>> logicMap = new HashMap<>();
			if (FileUtil.isExistFile(blockLogicPath)) {
				String encodedJson = FileUtil.readFile(blockLogicPath);
				String decodedJson = new String(Base64.decode(encodedJson, Base64.DEFAULT));
				Type mapType = new TypeToken<Map<String, Map<String, String>>>() {}.getType();
				logicMap = new Gson().fromJson(decodedJson, mapType);
			}
			
			// Update logic
			Map<String, String> activityLogic = logicMap.getOrDefault(activityName, new HashMap<>());
			activityLogic.put(widgetId, logic);
			logicMap.put(activityName, activityLogic);
			
			// Create pretty-printed JSON first
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String prettyJson = gson.toJson(logicMap);
			
			// Then encode the pretty JSON
			String encodedJson = Base64.encodeToString(prettyJson.getBytes(), Base64.DEFAULT);
			FileUtil.writeFile(blockLogicPath, encodedJson);
			
		} catch (Exception e) {
			// TheBlockLogicsUtil.showToast(TheBlockLogicsUtil.getContext(), "Error saving block logic: " + e.toString());
		}
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
	
	// Convert hello_world → HelloWorld
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
	public void yq(boolean isUpdateMode, String existingActivityName) {
		LayoutInflater inflater = LayoutInflater.from(this);
		View dialogView = inflater.inflate(R.layout.create_activity_dialog, null);
		
		// Material3 AlertDialog
		MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
		builder.setView(dialogView);
		androidx.appcompat.app.AlertDialog dialog = builder.create();
		
		// UI elements
		ImageView previewImage = dialogView.findViewById(R.id.preview_image);
		TextInputEditText editTextName = dialogView.findViewById(R.id.edittext_name);
		CheckBox checkBoxStatusBar = dialogView.findViewById(R.id.checkbox_status_bar);
		CheckBox checkBoxToolbar = dialogView.findViewById(R.id.checkbox_toolbar);
		CheckBox checkBoxDrawer = dialogView.findViewById(R.id.checkbox_drawer);
		CheckBox checkBoxFab = dialogView.findViewById(R.id.checkbox_fab);
		RadioGroup radioGroupType = dialogView.findViewById(R.id.radio_group_type);
		RadioGroup radioGroupOrientation = dialogView.findViewById(R.id.radio_group_orientation);
		MaterialButton buttonCancel = dialogView.findViewById(R.id.button_cancel);
		MaterialButton buttonSave = dialogView.findViewById(R.id.button_save);
		
		editTextName.setFocusable(true);
		editTextName.setFocusableInTouchMode(true);
		
		// Prefill data if updating
		if (isUpdateMode && existingActivityName != null) {
			try {
				JSONObject activityData = complex.getActivityData(existingActivityName);
				if (activityData != null) {
					editTextName.setText(existingActivityName);
					checkBoxStatusBar.setChecked(activityData.optBoolean("statusBar", true));
					checkBoxToolbar.setChecked(activityData.optBoolean("toolbar", true));
					checkBoxDrawer.setChecked(activityData.optBoolean("drawer", false));
					checkBoxFab.setChecked(activityData.optBoolean("fab", false));
					
					String type = activityData.optString("type", "Activity");
					switch (type) {
						case "Fragment":
						radioGroupType.check(R.id.radio_fragment);
						break;
						case "DialogFragment":
						radioGroupType.check(R.id.radio_dialog_fragment);
						break;
						default:
						radioGroupType.check(R.id.radio_activity);
						break;
					}
					
					String orientation = activityData.optString("orientation", "Both");
					switch (orientation) {
						case "Portrait":
						radioGroupOrientation.check(R.id.radio_portrait);
						break;
						case "Landscape":
						radioGroupOrientation.check(R.id.radio_landscape);
						break;
						default:
						radioGroupOrientation.check(R.id.radio_both);
						break;
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		// Update preview image logic (unchanged)
		View.OnClickListener updatePreviewListener = v -> {
			boolean hasStatusBar = checkBoxStatusBar.isChecked();
			boolean hasToolbar = checkBoxToolbar.isChecked();
			boolean hasDrawer = checkBoxDrawer.isChecked();
			boolean hasFab = checkBoxFab.isChecked();
			
			// 16 combinations logic remains same
			if (hasStatusBar && hasToolbar && hasDrawer && hasFab) {
				previewImage.setImageResource(R.drawable.activity_1101);
			} else if (hasStatusBar && hasToolbar && hasDrawer && !hasFab) {
				previewImage.setImageResource(R.drawable.activity_0101);
			} else if (hasStatusBar && hasToolbar && !hasDrawer && hasFab) {
				previewImage.setImageResource(R.drawable.activity_1001);
			} else if (hasStatusBar && hasToolbar && !hasDrawer && !hasFab) {
				previewImage.setImageResource(R.drawable.activity_0001);
			} else if (hasStatusBar && !hasToolbar && hasDrawer && hasFab) {
				previewImage.setImageResource(R.drawable.activity_1100);
			} else if (hasStatusBar && !hasToolbar && hasDrawer && !hasFab) {
				previewImage.setImageResource(R.drawable.activity_0100);
			} else if (hasStatusBar && !hasToolbar && !hasDrawer && hasFab) {
				previewImage.setImageResource(R.drawable.activity_1000);
			} else if (hasStatusBar && !hasToolbar && !hasDrawer && !hasFab) {
				previewImage.setImageResource(R.drawable.activity_0000);
			} else if (!hasStatusBar && hasToolbar && hasDrawer && hasFab) {
				previewImage.setImageResource(R.drawable.activity_1111);
			} else if (!hasStatusBar && hasToolbar && hasDrawer && !hasFab) {
				previewImage.setImageResource(R.drawable.activity_1011);
			} else if (!hasStatusBar && hasToolbar && !hasDrawer && hasFab) {
				previewImage.setImageResource(R.drawable.activity_0111);
			} else if (!hasStatusBar && hasToolbar && !hasDrawer && !hasFab) {
				previewImage.setImageResource(R.drawable.activity_preset_1);
			} else if (!hasStatusBar && !hasToolbar && hasDrawer && hasFab) {
				previewImage.setImageResource(R.drawable.activity_1110);
			} else if (!hasStatusBar && !hasToolbar && hasDrawer && !hasFab) {
				previewImage.setImageResource(R.drawable.activity_0110);
			} else if (!hasStatusBar && !hasToolbar && !hasDrawer && hasFab) {
				previewImage.setImageResource(R.drawable.activity_1010);
			} else {
				previewImage.setImageResource(R.drawable.activity_0010);
			}
		};
		
		checkBoxStatusBar.setOnClickListener(updatePreviewListener);
		checkBoxToolbar.setOnClickListener(updatePreviewListener);
		checkBoxDrawer.setOnClickListener(updatePreviewListener);
		checkBoxFab.setOnClickListener(updatePreviewListener);
		
		updatePreviewListener.onClick(null);
		
		buttonCancel.setOnClickListener(v -> dialog.dismiss());
		
		buttonSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = editTextName.getText().toString().trim();
				if (name.isEmpty()) {
					Toast.makeText(getApplicationContext(), "Activity name cannot be empty", Toast.LENGTH_SHORT).show();
					return;
				}
				
				String formattedName = formatToCamelCase(name);
				String xmlName = name.toLowerCase().replaceAll("[^a-z0-9]", "_");
				
				// Check if activity or XML name already exists (skip check for the existing name in update mode)
				List<String> existingNames = complex.getAllJavaAndXmlNames();
				boolean nameExists = false;
				String conflictingName = "";
				
				if (!isUpdateMode || !formattedName.equals(existingActivityName)) {
					if (existingNames.contains(formattedName + ".java")) {
						nameExists = true;
						conflictingName = formattedName + ".java";
					} else if (existingNames.contains(xmlName)) {
						nameExists = true;
						conflictingName = xmlName;
					}
				}
				
				if (nameExists) {
					Toast.makeText(getApplicationContext(), "Name already exists: " + conflictingName, Toast.LENGTH_SHORT).show();
					return;
				}
				
				boolean hasStatusBar = checkBoxStatusBar.isChecked();
				boolean hasToolbar = checkBoxToolbar.isChecked();
				boolean hasDrawer = checkBoxDrawer.isChecked();
				boolean hasFab = checkBoxFab.isChecked();
				
				String type = "Activity";
				switch (radioGroupType.getCheckedRadioButtonId()) {
					case R.id.radio_activity:
					type = "Activity";
					break;
					case R.id.radio_fragment:
					type = "Fragment";
					break;
					case R.id.radio_dialog_fragment:
					type = "DialogFragment";
					break;
				}
				
				String orientation = "Both";
				switch (radioGroupOrientation.getCheckedRadioButtonId()) {
					case R.id.radio_portrait:
					orientation = "Portrait";
					break;
					case R.id.radio_landscape:
					orientation = "Landscape";
					break;
					case R.id.radio_both:
					orientation = "Both";
					break;
				}
				
				try {
					if (isUpdateMode && existingActivityName != null) {
						complex.updateActivity(
						existingActivityName,
						formattedName,
						xmlName,
						hasFab,
						hasToolbar,
						complex.getAndroidXEnable(),
						hasDrawer,
						type,
						orientation,
						hasStatusBar
						);
						Toast.makeText(getApplicationContext(), "Activity updated: " + formattedName, Toast.LENGTH_SHORT).show();
					} else {
						String activityNameY = formattedName;
						String layoutNameX = xmlName;
						
						// ActivityBean में add करें
						addActivity(activityNameY, layoutNameX);
						complex.setAcName(formattedName);
						complex.setXName(xmlName);
						complex.addActivityToManifest(formattedName);
						complex.enableFab(formattedName, hasFab);
						complex.enableToolBar(formattedName, complex.getAndroidXEnable(), hasToolbar);
						
						// Set default XML layout (only for NEW activity)
						String layoutType = complex.getAndroidXEnable() ? 
						"androidx.constraintlayout.widget.ConstraintLayout" : "LinearLayout";
						
						String layoutParams = complex.getAndroidXEnable() ?
						"    android:layout_width=\"match_parent\"\n" +
						"    android:layout_height=\"match_parent\"\n" +
						"    xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
						"    xmlns:app=\"http://schemas.android.com/apk/res-auto\"" :
						"    android:layout_width=\"match_parent\"\n" +
						"    android:layout_height=\"match_parent\"\n" +
						"    android:orientation=\"vertical\"\n" +
						"    xmlns:android=\"http://schemas.android.com/apk/res/android\"";
						
						String defaultXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
						"<" + layoutType + "\n" +
						layoutParams + ">\n\n" +
						(hasFab && complex.getAndroidXEnable() ? 
						"    <com.google.android.material.floatingactionbutton.FloatingActionButton\n" +
						"        android:id=\"@+id/fab\"\n" +
						"        android:layout_width=\"wrap_content\"\n" +
						"        android:layout_height=\"wrap_content\"\n" +
						"        android:layout_margin=\"16dp\"\n" +
						"        app:layout_constraintEnd_toEndOf=\"parent\"\n" +
						"        app:layout_constraintBottom_toBottomOf=\"parent\"\n" +
						"        android:src=\"@android:drawable/ic_input_add\" />\n" : "") +
						"</" + layoutType + ">";
						
						
						complex.setXmlCode(xmlName, defaultXml);
						ProjectActivityBean secondBean = new ProjectActivityBean(
						formattedName,
						xmlName,
						pkgName,
						false,
						sc_id, // Activity scId
						scName
						);
						
						generateJavaCode(formattedName, xmlName);
						
						ProjectActivityManager manager = ProjectActivityManager.getInstance(getApplicationContext(), sc_id);
						manager.addActivity(secondBean);
						
						// Save into folder named by project scId = "601"
						manager.saveToFile(context, sc_id);
						
						//Toast.makeText(getApplicationContext(), "Activity created: " + formattedName, Toast.LENGTH_SHORT).show();
					}
					
					dialog.dismiss();
				} catch (JSONException e) {
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "Error saving activity: " + e.getMessage(), Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		dialog.show();
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
		try {
			String componentLogicPath = projectPath + "/component_logic/project_components.json";
			FileUtil.makeDir(projectPath + "/component_logic/");
			
			// Read existing components
			Map<String, List<HashMap<String, String>>> componentMap = new HashMap<>();
			if (FileUtil.isExistFile(componentLogicPath)) {
				String encodedJson = FileUtil.readFile(componentLogicPath);
				String decodedJson = new String(Base64.decode(encodedJson, Base64.DEFAULT));
				Type mapType = new TypeToken<Map<String, List<HashMap<String, String>>>>(){}.getType();
				componentMap = new Gson().fromJson(decodedJson, mapType);
			}
			
			// Update components
			List<HashMap<String, String>> activityComponents = componentMap.getOrDefault(activityName, new ArrayList<>());
			HashMap<String, String> componentData = new HashMap<>();
			componentData.put("componentName", componentName);
			componentData.put("fieldName", fieldName);
			activityComponents.add(componentData);
			componentMap.put(activityName, activityComponents);
			
			// Create pretty-printed JSON
			Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.create();
			String prettyJson = gson.toJson(componentMap);
			
			// Encode and save
			String encodedJson = Base64.encodeToString(prettyJson.getBytes(), Base64.DEFAULT);
			FileUtil.writeFile(componentLogicPath, encodedJson);
			
		} catch (Exception e) {
			// TheBlockLogicsUtil.showToast(getApplicationContext(), "Error saving component logic: " + e.toString());
		}
	}
	public static List<HashMap<String, String>> loadComponentLogic(String activityName) {
		try {
			String componentLogicPath = projectPath + "/component_logic/project_components.json";
			if (FileUtil.isExistFile(componentLogicPath)) {
				String encodedJson = FileUtil.readFile(componentLogicPath);
				String decodedJson = new String(Base64.decode(encodedJson, Base64.DEFAULT));
				Type mapType = new TypeToken<Map<String, List<HashMap<String, String>>>>(){}.getType();
				Map<String, List<HashMap<String, String>>> componentMap = new Gson().fromJson(decodedJson, mapType);
				return componentMap.getOrDefault(activityName, new ArrayList<>());
			}
		} catch (Exception e) {
			//TheBlockLogicsUtil.showToast(getApplicationContext(), "Error loading component logic: " + e.toString());
		}
		return new ArrayList<>();
	}
	public static List<HashMap<String, String>> loadIntentComponents(String activityName) {
		try {
			String componentLogicPath = projectPath + "/component_logic/project_components.json";
			if (FileUtil.isExistFile(componentLogicPath)) {
				String encodedJson = FileUtil.readFile(componentLogicPath);
				String decodedJson = new String(Base64.decode(encodedJson, Base64.DEFAULT));
				Type mapType = new TypeToken<Map<String, List<HashMap<String, String>>>>(){}.getType();
				Map<String, List<HashMap<String, String>>> componentMap = new Gson().fromJson(decodedJson, mapType);
				
				// Get components for the activity, or an empty list if none exist
				List<HashMap<String, String>> activityComponents = componentMap.getOrDefault(activityName, new ArrayList<>());
				
				// Filter components to only include those with componentName "Intent"
				List<HashMap<String, String>> intentComponents = new ArrayList<>();
				for (HashMap<String, String> component : activityComponents) {
					if ("Intent".equals(component.get("componentName"))) {
						intentComponents.add(component);
					}
				}
				return intentComponents;
			}
		} catch (Exception e) {
			// Optionally log the error or show a toast
			// TheBlockLogicsUtil.showToast(TheBlockLogicsUtil.getContext(), "Error loading Intent components: " + e.toString());
		}
		return new ArrayList<>();
	}
	public static List<HashMap<String, String>> loadCalendarComponents(String activityName) {
		try {
			String componentLogicPath = projectPath + "/component_logic/project_components.json";
			if (FileUtil.isExistFile(componentLogicPath)) {
				String encodedJson = FileUtil.readFile(componentLogicPath);
				String decodedJson = new String(Base64.decode(encodedJson, Base64.DEFAULT));
				Type mapType = new TypeToken<Map<String, List<HashMap<String, String>>>>(){}.getType();
				Map<String, List<HashMap<String, String>>> componentMap = new Gson().fromJson(decodedJson, mapType);
				
				// Get components for the activity, or an empty list if none exist
				List<HashMap<String, String>> activityComponents = componentMap.getOrDefault(activityName, new ArrayList<>());
				
				// Filter components to only include those with componentName "Intent"
				List<HashMap<String, String>> intentComponents = new ArrayList<>();
				for (HashMap<String, String> component : activityComponents) {
					if ("Calendar".equals(component.get("componentName"))) {
						intentComponents.add(component);
					}
				}
				return intentComponents;
			}
		} catch (Exception e) {
			// Optionally log the error or show a toast
			// TheBlockLogicsUtil.showToast(TheBlockLogicsUtil.getContext(), "Error loading Intent components: " + e.toString());
		}
		return new ArrayList<>();
	}
	public static void removeComponentLogic(String activityName, String componentName, String fieldName) {
		try {
			String componentLogicPath = projectPath + "/component_logic/project_components.json";
			FileUtil.makeDir(projectPath + "/component_logic/");
			
			// Read existing components
			Map<String, List<HashMap<String, String>>> componentMap = new HashMap<>();
			if (FileUtil.isExistFile(componentLogicPath)) {
				String encodedJson = FileUtil.readFile(componentLogicPath);
				String decodedJson = new String(Base64.decode(encodedJson, Base64.DEFAULT));
				Type mapType = new TypeToken<Map<String, List<HashMap<String, String>>>>(){}.getType();
				componentMap = new Gson().fromJson(decodedJson, mapType);
			}
			
			// Get components for the activity
			List<HashMap<String, String>> activityComponents = componentMap.getOrDefault(activityName, new ArrayList<>());
			
			// Find and remove the component
			for (int i = 0; i < activityComponents.size(); i++) {
				HashMap<String, String> comp = activityComponents.get(i);
				if (comp.get("componentName").equals(componentName) && 
				comp.get("fieldName").equals(fieldName)) {
					activityComponents.remove(i);
					break;
				}
			}
			
			// Update the component map
			if (activityComponents.isEmpty()) {
				componentMap.remove(activityName);
			} else {
				componentMap.put(activityName, activityComponents);
			}
			
			// Save updated components
			String json = new Gson().toJson(componentMap);
			String encodedJson = Base64.encodeToString(json.getBytes(), Base64.DEFAULT);
			FileUtil.writeFile(componentLogicPath, encodedJson);
			
		} catch (Exception e) {
			// Handle error appropriately
			// TheBlockLogicsUtil.showToast(getApplicationContext(), "Error removing component logic: " + e.toString());
		}
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
		try {
			String componentLogicPath = projectPath + "/block_logic/project_variables.json";
			FileUtil.makeDir(projectPath + "/block_logic/");
			
			// Read existing variables
			Map<String, List<HashMap<String, String>>> variableMap = new HashMap<>();
			if (FileUtil.isExistFile(componentLogicPath)) {
				String encodedJson = FileUtil.readFile(componentLogicPath);
				String decodedJson = new String(Base64.decode(encodedJson, Base64.DEFAULT));
				Type mapType = new TypeToken<Map<String, List<HashMap<String, String>>>>(){}.getType();
				variableMap = new Gson().fromJson(decodedJson, mapType);
			}
			
			// Update variables
			List<HashMap<String, String>> activityVariables = variableMap.getOrDefault(activityName, new ArrayList<>());
			HashMap<String, String> variableData = new HashMap<>();
			variableData.put("varTypeName", componentName);
			variableData.put("varName", fieldName);
			activityVariables.add(variableData);
			variableMap.put(activityName, activityVariables);
			
			// Create pretty-printed JSON
			Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.create();
			String prettyJson = gson.toJson(variableMap);
			
			// Encode and save
			String encodedJson = Base64.encodeToString(prettyJson.getBytes(), Base64.DEFAULT);
			FileUtil.writeFile(componentLogicPath, encodedJson);
			
		} catch (Exception e) {
			// TheBlockLogicsUtil.showToast(getApplicationContext(), "Error saving variable: " + e.toString());
		}
	}
	public static Map<String, List<String>> getVariables(String activityName) {
		return variableMap.getOrDefault(activityName, new HashMap<>());
	}
	public static List<String> getVariable(String activityName, String varType) {
		Map<String, List<String>> activityVars = variableMap.getOrDefault(activityName, new HashMap<>());
		return activityVars.getOrDefault(varType, new ArrayList<>());
	}
	public static void removeVariable(String activityName, String componentName, String fieldName) {
		try {
			String componentLogicPath = projectPath + "/block_logic/project_variables.json";
			FileUtil.makeDir(projectPath + "/block_logic/");
			
			if (!FileUtil.isExistFile(componentLogicPath)) return;
			
			String encodedJson = FileUtil.readFile(componentLogicPath);
			String decodedJson = new String(Base64.decode(encodedJson, Base64.DEFAULT));
			Type mapType = new TypeToken<Map<String, List<HashMap<String, String>>>>(){}.getType();
			Map<String, List<HashMap<String, String>>> componentMap = new Gson().fromJson(decodedJson, mapType);
			
			if (componentMap.containsKey(activityName)) {
				List<HashMap<String, String>> activityComponents = componentMap.get(activityName);
				
				// Use iterator to safely remove matching variable
				Iterator<HashMap<String, String>> iterator = activityComponents.iterator();
				while (iterator.hasNext()) {
					HashMap<String, String> componentData = iterator.next();
					if (componentName.equals(componentData.get("varTypeName")) &&
					fieldName.equals(componentData.get("varName"))) {
						iterator.remove();
					}
				}
				
				// Update the map
				componentMap.put(activityName, activityComponents);
				String json = new Gson().toJson(componentMap);
				String encoded = Base64.encodeToString(json.getBytes(), Base64.DEFAULT);
				FileUtil.writeFile(componentLogicPath, encoded);
			}
		} catch (Exception e) {
			// Handle error
		}
	}
	
	public static List<HashMap<String, String>> loadVariableLogic(String activityName) {
		try {
			String componentLogicPath = projectPath + "/block_logic/project_variables.json";
			if (FileUtil.isExistFile(componentLogicPath)) {
				String encodedJson = FileUtil.readFile(componentLogicPath);
				String decodedJson = new String(Base64.decode(encodedJson, Base64.DEFAULT));
				Type mapType = new TypeToken<Map<String, List<HashMap<String, String>>>>(){}.getType();
				Map<String, List<HashMap<String, String>>> componentMap = new Gson().fromJson(decodedJson, mapType);
				return componentMap.getOrDefault(activityName, new ArrayList<>());
			}
		} catch (Exception e) {
			//TheBlockLogicsUtil.showToast(getApplicationContext(), "Error loading component logic: " + e.toString());
		}
		return new ArrayList<>();
	}
	/**
 * Saves a function declaration for a specific activity.
 * @param activityName The name of the activity to associate the function with.
 * @param functionName The name of the function (e.g., "calculateSum").
 * @param returnType The return type of the function (e.g., "int", "void", "String").
 * @param parameters A list of parameter types and names (e.g., [{"type": "int", "name": "a"}, {"type": "int", "name": "b"}]).
 */
	public static void addFunction(String activityName, String functionName, String returnType, List<HashMap<String, String>> parameters) {
		try {
			String functionLogicPath = projectPath + "/block_logic/project_functions.json";
			FileUtil.makeDir(projectPath + "/block_logic/");
			
			// Read existing functions
			Map<String, List<HashMap<String, Object>>> functionMap = new HashMap<>();
			if (FileUtil.isExistFile(functionLogicPath)) {
				String encodedJson = FileUtil.readFile(functionLogicPath);
				String decodedJson = new String(Base64.decode(encodedJson, Base64.DEFAULT));
				Type mapType = new TypeToken<Map<String, List<HashMap<String, Object>>>>(){}.getType();
				functionMap = new Gson().fromJson(decodedJson, mapType);
				if (functionMap == null) {
					functionMap = new HashMap<>();
				}
			}
			
			// Get or create function list for the activity
			List<HashMap<String, Object>> activityFunctions = functionMap.getOrDefault(activityName, new ArrayList<>());
			
			// Check if function already exists (to avoid duplicates)
			boolean functionExists = false;
			for (HashMap<String, Object> existingFunc : activityFunctions) {
				if (functionName.equals(existingFunc.get("functionName"))) {
					functionExists = true;
					// Update existing function
					existingFunc.put("returnType", returnType);
					existingFunc.put("parameters", parameters);
					break;
				}
			}
			
			// Add new function if it doesn't exist
			if (!functionExists) {
				HashMap<String, Object> functionData = new HashMap<>();
				functionData.put("functionName", functionName);
				functionData.put("returnType", returnType);
				functionData.put("parameters", parameters);
				activityFunctions.add(functionData);
			}
			
			// Update function map
			functionMap.put(activityName, activityFunctions);
			
			// Save updated functions
			String json = new Gson().toJson(functionMap);
			String encodedJson = Base64.encodeToString(json.getBytes(), Base64.DEFAULT);
			FileUtil.writeFile(functionLogicPath, encodedJson);
			
		} catch (Exception e) {
			// Consistent with other methods in DesignActivity, log error but don't throw
			//  TheBlockLogicsUtil.showToast(this, "Error saving function logic: " + e.toString());
		}
	}
	
	/**
 * Loads all function declarations for a specific activity.
 * @param activityName The name of the activity.
 * @return A list of function metadata, each containing functionName, returnType, and parameters.
 */
	public static List<HashMap<String, Object>> loadFunctions(String activityName) {
		try {
			String functionLogicPath = projectPath + "/block_logic/project_functions.json";
			if (FileUtil.isExistFile(functionLogicPath)) {
				String encodedJson = FileUtil.readFile(functionLogicPath);
				String decodedJson = new String(Base64.decode(encodedJson, Base64.DEFAULT));
				Type mapType = new TypeToken<Map<String, List<HashMap<String, Object>>>>() {}.getType();
				Map<String, List<HashMap<String, Object>>> functionMap = new Gson().fromJson(decodedJson, mapType);
				if (functionMap != null && functionMap.containsKey(activityName)) {
					return functionMap.get(activityName);
				}
			}
		} catch (Exception e) {
			Log.e("loadFunctions", "Error loading function logic: ", e);
		}
		return new ArrayList<>();
	}
	
	/**
 * Removes a function declaration from a specific activity.
 * @param activityName The name of the activity.
 * @param functionName The name of the function to remove.
 */
	public static void removeFunction(String activityName, String functionName) {
		try {
			String functionLogicPath = projectPath + "/block_logic/project_functions.json";
			FileUtil.makeDir(projectPath + "/block_logic/");
			
			// Read existing functions
			Map<String, List<HashMap<String, Object>>> functionMap = new HashMap<>();
			if (FileUtil.isExistFile(functionLogicPath)) {
				String encodedJson = FileUtil.readFile(functionLogicPath);
				String decodedJson = new String(Base64.decode(encodedJson, Base64.DEFAULT));
				Type mapType = new TypeToken<Map<String, List<HashMap<String, Object>>>>(){}.getType();
				functionMap = new Gson().fromJson(decodedJson, mapType);
			}
			
			// Get functions for the activity
			List<HashMap<String, Object>> activityFunctions = functionMap.getOrDefault(activityName, new ArrayList<>());
			
			// Remove the function
			for (int i = 0; i < activityFunctions.size(); i++) {
				HashMap<String, Object> func = activityFunctions.get(i);
				if (func.get("functionName").equals(functionName)) {
					activityFunctions.remove(i);
					break;
				}
			}
			
			// Update the function map
			if (activityFunctions.isEmpty()) {
				functionMap.remove(activityName);
			} else {
				functionMap.put(activityName, activityFunctions);
			}
			
			// Save updated functions
			String json = new Gson().toJson(functionMap);
			String encodedJson = Base64.encodeToString(json.getBytes(), Base64.DEFAULT);
			FileUtil.writeFile(functionLogicPath, encodedJson);
		} catch (Exception e) {
			// TheBlockLogicsUtil.showToast(getApplicationContext(), "Error removing function logic: " + e.toString());
		}
	}
	public static List<HashMap<String, String>> loadComponentFromName(String activityName, String componentName) {
		try {
			String componentLogicPath = projectPath + "/component_logic/project_components.json";
			if (FileUtil.isExistFile(componentLogicPath)) {
				String encodedJson = FileUtil.readFile(componentLogicPath);
				String decodedJson = new String(Base64.decode(encodedJson, Base64.DEFAULT));
				Type mapType = new TypeToken<Map<String, List<HashMap<String, String>>>>(){}.getType();
				Map<String, List<HashMap<String, String>>> componentMap = new Gson().fromJson(decodedJson, mapType);
				
				// Get components for the activity, or an empty list if none exist
				List<HashMap<String, String>> activityComponents = componentMap.getOrDefault(activityName, new ArrayList<>());
				
				// Filter components to only include those with componentName "Intent"
				List<HashMap<String, String>> intentComponents = new ArrayList<>();
				for (HashMap<String, String> component : activityComponents) {
					if (componentName.equals(component.get("componentName"))) {
						intentComponents.add(component);
					}
				}
				return intentComponents;
			}
		} catch (Exception e) {
			// Optionally log the error or show a toast
			// TheBlockLogicsUtil.showToast(TheBlockLogicsUtil.getContext(), "Error loading Intent components: " + e.toString());
		}
		return new ArrayList<>();
	}
	public static void saveBlockLogicForEvent(String activityName, String widgetId, String logic) {
		try {
			String blockLogicPath = projectPath + "/block_logic/project_logic.json";
			FileUtil.makeDir(projectPath + "/block_logic/");
			
			// Read existing logic
			Map<String, Map<String, String>> logicMap = new HashMap<>();
			if (FileUtil.isExistFile(blockLogicPath)) {
				String encodedJson = FileUtil.readFile(blockLogicPath);
				String decodedJson = new String(Base64.decode(encodedJson, Base64.DEFAULT));
				Type mapType = new TypeToken<Map<String, Map<String, String>>>() {}.getType();
				logicMap = new Gson().fromJson(decodedJson, mapType);
			}
			
			// Update logic
			Map<String, String> activityLogic = logicMap.getOrDefault(activityName, new HashMap<>());
			activityLogic.put(widgetId, logic);
			logicMap.put(activityName, activityLogic);
			
			// Create pretty-printed JSON first
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String prettyJson = gson.toJson(logicMap);
			
			// Then encode the pretty JSON
			String encodedJson = Base64.encodeToString(prettyJson.getBytes(), Base64.DEFAULT);
			FileUtil.writeFile(blockLogicPath, encodedJson);
			
		} catch (Exception e) {
			// TheBlockLogicsUtil.showToast(TheBlockLogicsUtil.getContext(), "Error saving block logic: " + e.toString());
		}
	}
	public static String getBlockLogics(String activityName) {
		try {
			String blockLogicPath = projectPath + "/block_logic/project_logic.json";
			
			if (!FileUtil.isExistFile(blockLogicPath)) {
				return ""; // File exist nahi karti
			}
			
			// Read encoded Base64 JSON
			String encodedJson = FileUtil.readFile(blockLogicPath);
			String decodedJson = new String(Base64.decode(encodedJson, Base64.DEFAULT));
			
			// Parse JSON into map
			Type mapType = new TypeToken<Map<String, Map<String, String>>>() {}.getType();
			Map<String, Map<String, String>> logicMap = new Gson().fromJson(decodedJson, mapType);
			
			if (logicMap.containsKey(activityName)) {
				Map<String, String> activityLogic = logicMap.get(activityName);
				return activityLogic.get(""); // Return stored logic
			}
			
		} catch (Exception e) {
			// Error handle karna
			e.printStackTrace();
		}
		return ""; // Agar kuch bhi na mile
	}
	
	private String getBlockLogicForEvent(String widgetId) {
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
	public static void saveWidgetInfo(String activityName, String widgetType, String widgetId) {
		try {
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
			
			// Update - widgetType as key, widgetId as value (super simple!)
			Map<String, String> activityWidgets = widgetMap.getOrDefault(activityName, new HashMap<>());
			activityWidgets.put(widgetType, widgetId);  // "TextView" -> "textview1"
			widgetMap.put(activityName, activityWidgets);
			
			// Save (same as block logic)
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String prettyJson = gson.toJson(widgetMap);
			String encodedJson = Base64.encodeToString(prettyJson.getBytes(), Base64.DEFAULT);
			FileUtil.writeFile(widgetPath, encodedJson);
			
		} catch (Exception e) {
			// Silent
		}
	}
	public static Map<String, List<String>> getWidgetInfoForActivity(String activityName) {
		try {
			String projectPath = FileUtil.getExternalStorageDir() + "/.blacklogics/data/" + scId;
			String widgetPath = projectPath + "/widget_info/project_widgets.json";
			if (!FileUtil.isExistFile(widgetPath)) return new HashMap<>();
			
			String encodedJson = FileUtil.readFile(widgetPath);
			String decodedJson = new String(Base64.decode(encodedJson, Base64.DEFAULT));
			Type mapType = new TypeToken<Map<String, Map<String, List<String>>>>() {}.getType();
			Map<String, Map<String, List<String>>> widgetMap = new Gson().fromJson(decodedJson, mapType);
			
			return widgetMap.getOrDefault(activityName, new HashMap<>());
		} catch (Exception e) {
			Log.e("ViewEditor", "Error loading widget info: " + e.getMessage());
			return new HashMap<>();
		}
	}
	public static List<String> getWidgetIds(String activityName, String widgetType) {
		Map<String, List<String>> widgets = getWidgetInfoForActivity(activityName);
		return widgets.getOrDefault(widgetType, new ArrayList<>());
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
		ProjectBuilder builder = new ProjectBuilder(complex, Environment.getExternalStorageDirectory().getAbsolutePath(), sc_id);
		builder.setContext(this);
		builder.buildProject();
		SystemLogPrinter.start(mLogger);
		
		// Set up Project
		Project project = new Project();
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
		project.setMinSdk(21);
		project.setVersionCode(Integer.parseInt(varCode));
		project.setTargetSdk(34);
		
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
		Intent intent = new Intent(getApplicationContext(), ManagePermissionActivity.class);
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
		adapter = new TabAdapterFragmentAdapter(this, getSupportFragmentManager(), 3);
		customViewPager.setAdapter(adapter);
		tab_layout.setupWithViewPager(customViewPager);
		tab_layout.setInlineLabel(true);
		tab_layout.setTabTextColors(getResources().getColor(R.color.spinner_text), getResources().getColor(R.color.colorPrimaryVariant));
		tab_layout.setSelectedTabIndicatorColor(getResources().getColor(R.color.tab_ripple));
		tab_layout.setSelectedTabIndicatorHeight(4);
		customViewPager.setOffscreenPageLimit((int)0);
		customViewPager.setCurrentItem((int)0);
		int[][] states = new int[][]{
			new int[]{android.R.attr.state_pressed},
			new int[]{android.R.attr.state_focused},
			new int[]{}
		};
		
		int[] colors = new int[]{
			ContextCompat.getColor(this, R.color.tab_ripple),
			ContextCompat.getColor(this, R.color.colorPrimaryVariant),
			0x00000000
		};
		
		tab_layout.setTabRippleColor(new ColorStateList(states, colors));
		tab_layout.setTabTextColors(ContextCompat.getColor(this, R.color.textColorSecondary),
		ContextCompat.getColor(this, R.color.white));
		
		tab_layout.setInlineLabel(true);
		customViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				pos = position;
				if (position == 0) {
					complex.setXmlAdapter(file_spinner);
					file_spinner.setSelection(savedXmlPosition);
				} else {
					complex.setJavaAdapter(file_spinner);
					file_spinner.setSelection(savedJavaPosition);
				}
				tab_layout.getTabAt(position).select();
			}
			@Override public void onPageScrolled(int pos, float offset, int offsetPx) {}
			@Override public void onPageScrollStateChanged(int state) {}
		});
		
		tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				customViewPager.setCurrentItem(tab.getPosition());
			}
			@Override public void onTabUnselected(TabLayout.Tab tab) {}
			@Override public void onTabReselected(TabLayout.Tab tab) {}
		});
		
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
				String selectedItem = (String) parent.getItemAtPosition(position); // Assuming adapter has activity/layout names
				if (pos == 0) { // XML tab (layouts)
					savedXmlPosition = position;
					_setCurrentLayout(selectedItem); // Update layout
				} else { // Java tab (activities)
					savedJavaPosition = position;
					_setCurrentActivity(selectedItem); // Update activityBean
				}
				// Refresh the current fragment/tab
				adapter.notifyDataSetChanged(); // Or reload viewEditor/complex
				generateJavaCode(); // If needed, regenerate code
				generateXmlLayout();
			}
			@Override public void onNothingSelected(AdapterView<?> parent) {}
		});
		file_spinner.setOnTouchListener((v, event) -> {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				showCustomSpinnerDialog();
			}
			return true;
		});
	}
	
	
	public void _setupViewPagerListener() {
		customViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				pos = position;
				if (position == 0) {
					complex.setXmlAdapter(file_spinner);
					file_spinner.setSelection(savedXmlPosition);
				} else {
					complex.setJavaAdapter(file_spinner);
					file_spinner.setSelection(savedJavaPosition);
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
		for (ActivityBean activity : activityList) {
			if (activity.getActivityName().equals(_activityName)) {
				activityBean = activity;
				break;
			}
		}
		
		// Listener को notify करें
		if (activityNameChangeListener != null) {
			activityNameChangeListener.onActivityNameChanged(_activityName);
			activityNameChangeListener.onLayoutNameChanged(activityBean.getLayoutName());
		}
	}
	
	
	public void _setCurrentLayout(final String _layoutName) {
		activityBean.setLayoutName(_layoutName);
	}
	
	
	public void _generators() {
	}
	@Override
	public void generateJavaCode() {
		StringBuilder javaCode = new StringBuilder();
		javaCode.append("package ").append(pkgName).append(";\n\n");
		
		if (complex.getAndroidXEnable()) {
			javaCode.append("import androidx.appcompat.app.AppCompatActivity;\n");
			javaCode.append("import androidx.fragment.app.Fragment;\n");
			javaCode.append("import androidx.fragment.app.FragmentManager;\n");
			javaCode.append("import androidx.fragment.app.DialogFragment;\n");
			javaCode.append("import com.google.android.material.*;\n");
		} else {
			javaCode.append("import android.app.Activity;\n");
		}
		
		javaCode.append("import android.app.*;\n");
		javaCode.append("import android.os.*;\n");
		javaCode.append("import android.os.Bundle;\n");
		javaCode.append("import android.widget.*;\n");
		javaCode.append("import android.text.*;\n");
		javaCode.append("import android.net.*;\n");
		javaCode.append("import android.util.*;\n");
		javaCode.append("import android.view.*;\n");
		javaCode.append("import android.graphics.*;\n");
		javaCode.append("import android.content.*;\n");
		javaCode.append("import android.widget.Toast;\n");
		javaCode.append("import android.webkit.*;\n");
		javaCode.append("import android.view.animation.*;\n");
		
		// Add imports for components
		List<HashMap<String, String>> variables = loadVariableLogic(activityBean.getActivityName());
		List<HashMap<String, String>> components = loadComponentLogic(activityBean.getActivityName());
		for (HashMap<String, String> component : components) {
			String componentName = component.get("componentName");
			switch (componentName) {
				case "Intent":
				javaCode.append("import android.content.Intent;\n");
				break;
				case "Dialog":
				javaCode.append("import android.app.Dialog;\n");
				break;
				case "ObjectAnimator":
				javaCode.append("import android.animation.ObjectAnimator;\n");
				break;
				case "SharedPreferences":
				javaCode.append("import android.content.SharedPreferences;\n");
				break;
				case "AsyncTask":
				javaCode.append("import android.os.AsyncTask;\n");
				break;
				case "Handler":
				javaCode.append("import android.os.Handler;\n");
				break;
				case "Service":
				javaCode.append("import android.app.Service;\n");
				break;
				case "BroadcastReceiver":
				javaCode.append("import android.content.BroadcastReceiver;\n");
				break;
				case "ContentProvider":
				javaCode.append("import android.content.ContentProvider;\n");
				break;
				case "Fragment":
				javaCode.append("import androidx.fragment.app.Fragment;\n");
				break;
				case "ViewModel":
				javaCode.append("import androidx.lifecycle.ViewModel;\n");
				break;
				case "LiveData":
				javaCode.append("import androidx.lifecycle.LiveData;\n");
				break;
				case "Room":
				javaCode.append("import androidx.room.*;\n");
				break;
				case "WorkManager":
				javaCode.append("import androidx.work.*;\n");
				break;
				case "RecyclerView":
				javaCode.append("import androidx.recyclerview.widget.RecyclerView;\n");
				break;
				case "ViewPager":
				javaCode.append("import androidx.viewpager.widget.ViewPager;\n");
				break;
				case "MediaPlayer":
				javaCode.append("import android.media.MediaPlayer;\n");
				break;
				case "Camera":
				javaCode.append("import android.hardware.Camera;\n");
				break;
				case "LocationManager":
				javaCode.append("import android.location.LocationManager;\n");
				break;
				case "SensorManager":
				javaCode.append("import android.hardware.SensorManager;\n");
				break;
				case "BluetoothAdapter":
				javaCode.append("import android.bluetooth.BluetoothAdapter;\n");
				break;
				case "Timer":
				javaCode.append("import java.util.Timer;\n");
				javaCode.append("import java.util.TimerTask;\n");
				break;
				case "CameraX":
				javaCode.append("import androidx.camera.core.*;\n");
				javaCode.append("import androidx.camera.lifecycle.ProcessCameraProvider;\n");
				break;
				case "FilePicker":
				javaCode.append("import android.content.Intent;\n");
				javaCode.append("import android.provider.DocumentsContract;\n");
				break;
				case "ImagePicker":
				javaCode.append("import android.provider.MediaStore;\n");
				break;
				case "VideoPicker":
				javaCode.append("import android.provider.MediaStore;\n");
				break;
				case "AudioRecorder":
				javaCode.append("import android.media.MediaRecorder;\n");
				break;
				case "SpeechRecognizer":
				javaCode.append("import android.speech.SpeechRecognizer;\n");
				break;
				case "QRScanner":
				javaCode.append("import com.google.zxing.integration.android.IntentIntegrator;\n");
				javaCode.append("import com.google.zxing.integration.android.IntentResult;\n");
				break;
				case "DocumentPicker":
				javaCode.append("import android.content.Intent;\n");
				javaCode.append("import android.provider.OpenableColumns;\n");
				break;
				case "BiometricAuth":
				javaCode.append("import androidx.biometric.BiometricPrompt;\n");
				javaCode.append("import androidx.core.content.ContextCompat;\n");
				break;
			}
		}
		
		for (HashMap<String, String> componentA : variables) {
			String componentName = componentA.get("varTypeName");
			switch (componentName) {
				case "ArrayList<String>":
				case "ArrayList<Double>":
				javaCode.append("import java.util.ArrayList;\n");
				break;
			}
		}
		
		javaCode.append("\npublic class ").append(activityBean.getActivityName()).append(" extends ");
		javaCode.append(complex.getAndroidXEnable() ? "AppCompatActivity" : "Activity").append(" {\n\n");
		
		// Add widget fields
		declareWidgetFields(activityBean.getActivityName(), javaCode);
		
		// Add component fields
		boolean timerDeclared = false;
		
		for (HashMap<String, String> component : components) {
			String componentName = component.get("componentName");
			String fieldName = component.get("fieldName");
			
			if ("Timer".equals(componentName)) {
				if (!timerDeclared) {
					javaCode.append("private Timer _timer = new Timer();\n");
					timerDeclared = true;
				}
				javaCode.append("private TimerTask ").append(fieldName).append(";\n");
			} else {
				javaCode.append("private ").append(componentName).append(" ").append(fieldName).append(";\n");
			}
		}
		
		for (HashMap<String, String> variable : variables) {
			String varType = variable.get("varTypeName");
			String varName = variable.get("varName");
			javaCode.append("    private ").append(varType).append(" ").append(varName).append(";\n");
		}
		
		javaCode.append("\n    @Override\n");
		javaCode.append("    protected void onCreate(Bundle savedInstanceState) {\n");
		javaCode.append("        super.onCreate(savedInstanceState);\n");
		javaCode.append("        setContentView(R.layout.").append(activityBean.getLayoutName()).append(");\n");
		javaCode.append("        initialize(savedInstanceState);\n");
		javaCode.append("        initializeLogic();\n");
		javaCode.append("    }\n");
		
		javaCode.append("    private void initialize(Bundle _savedInstanceState) {\n");
		initializeWidgetFields(activityBean.getActivityName(), javaCode);
		
		// Initialize components with event listeners
		for (HashMap<String, String> component : components) {
			String componentName = component.get("componentName");
			String fieldName = component.get("fieldName");
			String logic = getBlockLogic(fieldName); // Assuming this fetches component-specific logic
			String eventLogic = getBlockLogicForEvent(fieldName);
			switch (componentName) {
				case "Intent":
				javaCode.append("        ").append(fieldName).append(" = new Intent();\n");
				break;
				case "Dialog":
				javaCode.append("        ").append(fieldName).append(" = new Dialog(this);\n");
				javaCode.append("        ").append(fieldName).append(".setOnShowListener(new DialogInterface.OnShowListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onShow(DialogInterface dialog) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				javaCode.append("        ").append(fieldName).append(".setOnDismissListener(new DialogInterface.OnDismissListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onDismiss(DialogInterface dialog) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				break;
				case "ObjectAnimator":
				javaCode.append("        ").append(fieldName).append(" = new ObjectAnimator();\n");
				javaCode.append("        ").append(fieldName).append(".addListener(new Animator.AnimatorListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onAnimationStart(Animator animation) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onAnimationEnd(Animator animation) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onAnimationCancel(Animator animation) {}\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onAnimationRepeat(Animator animation) {}\n");
				javaCode.append("        });\n");
				break;
				case "SharedPreferences":
				javaCode.append("        ").append(fieldName).append(" = getSharedPreferences(\"prefs\", MODE_PRIVATE);\n");
				javaCode.append("        ").append(fieldName).append(".registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onSharedPreferenceChanged(SharedPreferences sp, String key) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				break;
				case "AsyncTask":
				javaCode.append("        // AsyncTask should be subclassed as subclass\n");
				break;
				case "Handler":
				javaCode.append("        ").append(fieldName).append(" = new Handler(Looper.getMainLooper());\n");
				javaCode.append("        // Handler typically uses post() with Runnable\n");
				break;
				case "Service":
				javaCode.append("        // Service is typically started, not instantiated directly\n");
				break;
				case "BroadcastReceiver":
				javaCode.append("        ").append(fieldName).append(" = new BroadcastReceiver() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onReceive(Context context, Intent intent) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        };\n");
				javaCode.append("        registerReceiver(").append(fieldName).append(", new IntentFilter());\n");
				break;
				case "ContentProvider":
				javaCode.append("        // ContentProvider is initialized by system\n");
				break;
				case "Fragment":
				javaCode.append("        // Fragment lifecycle handled separately\n");
				break;
				case "ViewModel":
				javaCode.append("        ").append(fieldName).append(" = new ViewModelProvider(this).get(ViewModel.class);\n");
				break;
				case "LiveData":
				javaCode.append("        // LiveData is observed with Observer\n");
				javaCode.append("        ").append(fieldName).append(".observe(this, new Observer<Object>() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onChanged(Object value) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				break;
				case "Room":
				javaCode.append("        // Room database initialized separately\n");
				break;
				case "WorkManager":
				javaCode.append("        // WorkManager initialized with WorkRequest\n");
				break;
				case "RecyclerView":
				javaCode.append("        ").append(fieldName).append(" = findViewById(R.id.").append(fieldName).append(");\n");
				javaCode.append("        ").append(fieldName).append(".addOnScrollListener(new RecyclerView.OnScrollListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				break;
				case "ViewPager":
				javaCode.append("        ").append(fieldName).append(" = findViewById(R.id.").append(fieldName).append(");\n");
				javaCode.append("        ").append(fieldName).append(".addOnPageChangeListener(new ViewPager.OnPageChangeListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onPageSelected(int position) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onPageScrollStateChanged(int state) {}\n");
				javaCode.append("        });\n");
				break;
				case "MediaPlayer":
				javaCode.append("        ").append(fieldName).append(" = new MediaPlayer();\n");
				javaCode.append("        ").append(fieldName).append(".setOnCompletionListener(new MediaPlayer.OnCompletionListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onCompletion(MediaPlayer mp) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				break;
				case "Camera":
				javaCode.append("        // Legacy Camera API initialization\n");
				break;
				case "LocationManager":
				javaCode.append("        ").append(fieldName).append(" = (LocationManager) getSystemService(Context.LOCATION_SERVICE);\n");
				javaCode.append("        ").append(fieldName).append(".requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onLocationChanged(Location location) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				break;
				case "SensorManager":
				javaCode.append("        ").append(fieldName).append(" = (SensorManager) getSystemService(Context.SENSOR_SERVICE);\n");
				javaCode.append("        ").append(fieldName).append(".registerListener(new SensorEventListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onSensorChanged(SensorEvent event) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onAccuracyChanged(Sensor sensor, int accuracy) {}\n");
				javaCode.append("        }, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);\n");
				break;
				case "BluetoothAdapter":
				javaCode.append("        ").append(fieldName).append(" = BluetoothAdapter.getDefaultAdapter();\n");
				break; // Events typically handled via BroadcastReceiver
				case "CameraX":
				javaCode.append("        // CameraX initialization\n");
				break;
				case "FilePicker":
				javaCode.append("        ").append(fieldName).append(" = new Intent(Intent.ACTION_OPEN_DOCUMENT);\n");
				javaCode.append("        ").append(fieldName).append(".setType(\"*/*\");\n");
				break;
				case "ImagePicker":
				javaCode.append("        ").append(fieldName).append(" = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);\n");
				break;
				case "VideoPicker":
				javaCode.append("        ").append(fieldName).append(" = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);\n");
				break;
				case "AudioRecorder":
				javaCode.append("        ").append(fieldName).append(" = new MediaRecorder();\n");
				break;
				case "SpeechRecognizer":
				javaCode.append("        ").append(fieldName).append(" = SpeechRecognizer.createSpeechRecognizer(this);\n");
				javaCode.append("        ").append(fieldName).append(".setRecognitionListener(new RecognitionListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onResults(Bundle results) {\n");
				javaCode.append("                ").append(logic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("            // Implement other RecognitionListener methods as needed\n");
				javaCode.append("        });\n");
				break;
				case "QRScanner":
				javaCode.append("        // QRScanner typically handled via Activity result\n");
				break;
				case "DocumentPicker":
				javaCode.append("        ").append(fieldName).append(" = new Intent(Intent.ACTION_OPEN_DOCUMENT);\n");
				break;
				case "BiometricAuth":
				javaCode.append("        ").append(fieldName).append(" = new BiometricPrompt(this, ContextCompat.getMainExecutor(this), new BiometricPrompt.AuthenticationCallback() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {\n");
				javaCode.append("                ").append(logic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				break;
				case "Timer":
				javaCode.append("        ").append(fieldName).append(" = new TimerTask() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void run() {\n");
				javaCode.append("                ").append(logic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        };\n");
				javaCode.append("        _timer.schedule(").append(fieldName).append(", 0, 1000);\n");
				break;
			}
		}
		
		
		bindWidgetEvents(activityBean.getActivityName(), javaCode);
		
		javaCode.append("\n    }\n");
		
		String logic = getBlockLogics(activityBean.getActivityName());
		
		javaCode.append("    private void initializeLogic() {\n");
		
		if (logic != null && !logic.isEmpty()) {
			String[] lines = logic.split("\n");
			for (String line : lines) {
				javaCode.append("        ").append(line.strip()).append("\n"); // proper 8-space indent
			}
		}
		
		javaCode.append("    }\n");
		
		
		// After your onCreate() method generation, add lifecycle events
		try {
			String path = FileHandler.codeSavePath + "/" + sc_id + "/events/lifecycle_events.json";
			File file = new File(path);
			JSONObject allEvents = new JSONObject();
			
			if (file.exists()) {
				String json = FileHandler.readFile(path);
				String decoded = new String(Base64.decode(json, Base64.DEFAULT));
				allEvents = new JSONObject(decoded);
			}
			
			String activityName = activityBean.getActivityName();
			JSONArray existingArray = allEvents.optJSONArray(activityName);
			
			if (existingArray != null) {
				for (int i = 0; i < existingArray.length(); i++) {
					String event = existingArray.optString(i);
					String eventLogic = getBlockLogicForEvent(event);
					
					String formattedLogic = "";
					if (eventLogic != null && !eventLogic.isEmpty()) {
						formattedLogic = "        " + eventLogic.replace("\n", "\n        ") + "\n";
					}
					
					switch (event) {
						case "onCreate":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onCreate(Bundle savedInstanceState) {\n");
						javaCode.append("        super.onCreate(savedInstanceState);\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onPostCreate":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onPostCreate(Bundle savedInstanceState) {\n");
						javaCode.append("        super.onPostCreate(savedInstanceState);\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onStart":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onStart() {\n");
						javaCode.append("        super.onStart();\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onResume":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onResume() {\n");
						javaCode.append("        super.onResume();\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onPostResume":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onPostResume() {\n");
						javaCode.append("        super.onPostResume();\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onPause":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onPause() {\n");
						javaCode.append("        super.onPause();\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onStop":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onStop() {\n");
						javaCode.append("        super.onStop();\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onRestart":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onRestart() {\n");
						javaCode.append("        super.onRestart();\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onDestroy":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onDestroy() {\n");
						javaCode.append("        super.onDestroy();\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onBackPressed":
						javaCode.append("\n    @Override\n");
						javaCode.append("    public void onBackPressed() {\n");
						javaCode.append("        super.onBackPressed();\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onSaveInstanceState":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onSaveInstanceState(Bundle outState) {\n");
						javaCode.append("        super.onSaveInstanceState(outState);\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onRestoreInstanceState":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onRestoreInstanceState(Bundle savedInstanceState) {\n");
						javaCode.append("        super.onRestoreInstanceState(savedInstanceState);\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onActivityResult":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onActivityResult(int requestCode, int resultCode, Intent data) {\n");
						javaCode.append("        super.onActivityResult(requestCode, resultCode, data);\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onRequestPermissionsResult":
						javaCode.append("\n    @Override\n");
						javaCode.append("    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {\n");
						javaCode.append("        super.onRequestPermissionsResult(requestCode, permissions, grantResults);\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onUserLeaveHint":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onUserLeaveHint() {\n");
						javaCode.append("        super.onUserLeaveHint();\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onTrimMemory":
						javaCode.append("\n    @Override\n");
						javaCode.append("    public void onTrimMemory(int level) {\n");
						javaCode.append("        super.onTrimMemory(level);\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onLowMemory":
						javaCode.append("\n    @Override\n");
						javaCode.append("    public void onLowMemory() {\n");
						javaCode.append("        super.onLowMemory();\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onNewIntent":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onNewIntent(Intent intent) {\n");
						javaCode.append("        super.onNewIntent(intent);\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onConfigurationChanged":
						javaCode.append("\n    @Override\n");
						javaCode.append("    public void onConfigurationChanged(@NonNull Configuration newConfig) {\n");
						javaCode.append("        super.onConfigurationChanged(newConfig);\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						default:
						javaCode.append("\n    // Custom event: ").append(event).append("\n");
						javaCode.append("    private void ").append(event).append("() {\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		/*// Add pretty print utility method
javaCode.append("\n    private String prettyPrint(String message) {\n")
.append("        StringBuilder formatted = new StringBuilder();\n")
.append("        formatted.append(").append(message).append("\\n\"");
.append("        return formatted.toString();\n")
.append("    }\n");*/	
		
		List<HashMap<String, Object>> functions = loadFunctions(activityBean.getActivityName());
		if (functions != null) {
			for (HashMap<String, Object> func : functions) {
				String functionName = (String) func.get("functionName");
				String returnType = (String) func.get("returnType");
				List<HashMap<String, String>> parameters = (List<HashMap<String, String>>) func.get("parameters");
				
				javaCode.append("public ").append(returnType).append(" ").append(functionName).append("(");
				for (int i = 0; i < parameters.size(); i++) {
					HashMap<String, String> param = parameters.get(i);
					javaCode.append(param.get("type")).append(" ").append(param.get("name"));
					if (i < parameters.size() - 1) {
						javaCode.append(", ");
					}
				}
				javaCode.append(") {\n");
				javaCode.append("   \n");
				javaCode.append("}\n\n");
			}
		}
		
		javaCode.append("    public void showMessage(String message) {\n");
		javaCode.append("        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();\n");
		javaCode.append("\n    }\n");
		
		javaCode.append("}\n");
		
		// Save the generated Java code
		complex.setJavaCode(activityBean.getActivityName(), javaCode.toString());
		
		// Pretty print the generated code for debugging (if needed)
		String prettyPrintedCode = prettyPrintCode(javaCode.toString());
		Log.d("GeneratedJavaCode", prettyPrintedCode);
	}
	
	// Sirf private fields declare karega (JSON ke hisaab se)
	private void declareWidgetFields(String activityName, StringBuilder javaCode) {
		Map<String, List<String>> widgets = getWidgetInfoForActivity(activityName);
		
		for (Map.Entry<String, List<String>> entry : widgets.entrySet()) {
			String widgetType = entry.getKey();
			List<String> widgetIds = entry.getValue();
			
			for (String widgetId : widgetIds) {
				javaCode.append("    private ").append(widgetType).append(" ").append(widgetId).append(";\n");
			}
		}
	}
	
	
	// Sirf findViewById wali initialization karega (JSON ke hisaab se)
	private void initializeWidgetFields(String activityName, StringBuilder javaCode) {
		Map<String, List<String>> widgets = getWidgetInfoForActivity(activityName);
		
		for (Map.Entry<String, List<String>> entry : widgets.entrySet()) {
			List<String> widgetIds = entry.getValue();
			
			for (String widgetId : widgetIds) {
				javaCode.append("        ").append(widgetId)
				.append(" = findViewById(R.id.").append(widgetId).append(");\n");
			}
		}
	}
	
	public String getJavaCode() {
		StringBuilder javaCode = new StringBuilder();
		javaCode.append("package ").append(pkgName).append(";\n\n");
		
		if (complex.getAndroidXEnable()) {
			javaCode.append("import androidx.appcompat.app.AppCompatActivity;\n");
			javaCode.append("import androidx.fragment.app.Fragment;\n");
			javaCode.append("import androidx.fragment.app.FragmentManager;\n");
			javaCode.append("import androidx.fragment.app.DialogFragment;\n");
			javaCode.append("import com.google.android.material.*;\n");
		} else {
			javaCode.append("import android.app.Activity;\n");
		}
		
		javaCode.append("import android.app.*;\n");
		javaCode.append("import android.os.*;\n");
		javaCode.append("import android.os.Bundle;\n");
		javaCode.append("import android.widget.*;\n");
		javaCode.append("import android.text.*;\n");
		javaCode.append("import android.net.*;\n");
		javaCode.append("import android.util.*;\n");
		javaCode.append("import android.view.*;\n");
		javaCode.append("import android.graphics.*;\n");
		javaCode.append("import android.content.*;\n");
		javaCode.append("import android.widget.Toast;\n");
		javaCode.append("import android.webkit.*;\n");
		javaCode.append("import android.view.animation.*;\n");
		
		// Add imports for components
		List<HashMap<String, String>> variables = loadVariableLogic(activityBean.getActivityName());
		List<HashMap<String, String>> components = loadComponentLogic(activityBean.getActivityName());
		for (HashMap<String, String> component : components) {
			String componentName = component.get("componentName");
			switch (componentName) {
				case "Intent":
				javaCode.append("import android.content.Intent;\n");
				break;
				case "Dialog":
				javaCode.append("import android.app.Dialog;\n");
				break;
				case "ObjectAnimator":
				javaCode.append("import android.animation.ObjectAnimator;\n");
				break;
				case "SharedPreferences":
				javaCode.append("import android.content.SharedPreferences;\n");
				break;
				case "AsyncTask":
				javaCode.append("import android.os.AsyncTask;\n");
				break;
				case "Handler":
				javaCode.append("import android.os.Handler;\n");
				break;
				case "Service":
				javaCode.append("import android.app.Service;\n");
				break;
				case "BroadcastReceiver":
				javaCode.append("import android.content.BroadcastReceiver;\n");
				break;
				case "ContentProvider":
				javaCode.append("import android.content.ContentProvider;\n");
				break;
				case "Fragment":
				javaCode.append("import androidx.fragment.app.Fragment;\n");
				break;
				case "ViewModel":
				javaCode.append("import androidx.lifecycle.ViewModel;\n");
				break;
				case "LiveData":
				javaCode.append("import androidx.lifecycle.LiveData;\n");
				break;
				case "Room":
				javaCode.append("import androidx.room.*;\n");
				break;
				case "WorkManager":
				javaCode.append("import androidx.work.*;\n");
				break;
				case "RecyclerView":
				javaCode.append("import androidx.recyclerview.widget.RecyclerView;\n");
				break;
				case "ViewPager":
				javaCode.append("import androidx.viewpager.widget.ViewPager;\n");
				break;
				case "MediaPlayer":
				javaCode.append("import android.media.MediaPlayer;\n");
				break;
				case "Camera":
				javaCode.append("import android.hardware.Camera;\n");
				break;
				case "LocationManager":
				javaCode.append("import android.location.LocationManager;\n");
				break;
				case "SensorManager":
				javaCode.append("import android.hardware.SensorManager;\n");
				break;
				case "BluetoothAdapter":
				javaCode.append("import android.bluetooth.BluetoothAdapter;\n");
				break;
				case "Timer":
				javaCode.append("import java.util.Timer;\n");
				javaCode.append("import java.util.TimerTask;\n");
				break;
				case "CameraX":
				javaCode.append("import androidx.camera.core.*;\n");
				javaCode.append("import androidx.camera.lifecycle.ProcessCameraProvider;\n");
				break;
				case "FilePicker":
				javaCode.append("import android.content.Intent;\n");
				javaCode.append("import android.provider.DocumentsContract;\n");
				break;
				case "ImagePicker":
				javaCode.append("import android.provider.MediaStore;\n");
				break;
				case "VideoPicker":
				javaCode.append("import android.provider.MediaStore;\n");
				break;
				case "AudioRecorder":
				javaCode.append("import android.media.MediaRecorder;\n");
				break;
				case "SpeechRecognizer":
				javaCode.append("import android.speech.SpeechRecognizer;\n");
				break;
				case "QRScanner":
				javaCode.append("import com.google.zxing.integration.android.IntentIntegrator;\n");
				javaCode.append("import com.google.zxing.integration.android.IntentResult;\n");
				break;
				case "DocumentPicker":
				javaCode.append("import android.content.Intent;\n");
				javaCode.append("import android.provider.OpenableColumns;\n");
				break;
				case "BiometricAuth":
				javaCode.append("import androidx.biometric.BiometricPrompt;\n");
				javaCode.append("import androidx.core.content.ContextCompat;\n");
				break;
			}
		}
		
		for (HashMap<String, String> componentA : variables) {
			String componentName = componentA.get("varTypeName");
			switch (componentName) {
				case "ArrayList<String>":
				case "ArrayList<Double>":
				javaCode.append("import java.util.ArrayList;\n");
				break;
			}
		}
		
		javaCode.append("\npublic class ").append(activityBean.getActivityName()).append(" extends ");
		javaCode.append(complex.getAndroidXEnable() ? "AppCompatActivity" : "Activity").append(" {\n\n");
		
		// Add widget fields
		declareWidgetFields(activityBean.getActivityName(), javaCode);
		
		// Add component fields
		boolean timerDeclared = false;
		
		for (HashMap<String, String> component : components) {
			String componentName = component.get("componentName");
			String fieldName = component.get("fieldName");
			
			if ("Timer".equals(componentName)) {
				if (!timerDeclared) {
					javaCode.append("private Timer _timer = new Timer();\n");
					timerDeclared = true;
				}
				javaCode.append("private TimerTask ").append(fieldName).append(";\n");
			} else {
				javaCode.append("private ").append(componentName).append(" ").append(fieldName).append(";\n");
			}
		}
		
		for (HashMap<String, String> variable : variables) {
			String varType = variable.get("varTypeName");
			String varName = variable.get("varName");
			javaCode.append("    private ").append(varType).append(" ").append(varName).append(";\n");
		}
		
		javaCode.append("\n    @Override\n");
		javaCode.append("    protected void onCreate(Bundle savedInstanceState) {\n");
		javaCode.append("        super.onCreate(savedInstanceState);\n");
		javaCode.append("        setContentView(R.layout.").append(activityBean.getLayoutName()).append(");\n");
		javaCode.append("        initialize(savedInstanceState);\n");
		javaCode.append("        initializeLogic();\n");
		javaCode.append("    }\n");
		
		javaCode.append("    private void initialize(Bundle _savedInstanceState) {\n");
		initializeWidgetFields(activityBean.getActivityName(), javaCode);
		
		// Initialize components with event listeners
		for (HashMap<String, String> component : components) {
			String componentName = component.get("componentName");
			String fieldName = component.get("fieldName");
			String logic = getBlockLogic(fieldName); // Assuming this fetches component-specific logic
			String eventLogic = getBlockLogicForEvent(fieldName);
			switch (componentName) {
				case "Intent":
				javaCode.append("        ").append(fieldName).append(" = new Intent();\n");
				break;
				case "Dialog":
				javaCode.append("        ").append(fieldName).append(" = new Dialog(this);\n");
				javaCode.append("        ").append(fieldName).append(".setOnShowListener(new DialogInterface.OnShowListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onShow(DialogInterface dialog) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				javaCode.append("        ").append(fieldName).append(".setOnDismissListener(new DialogInterface.OnDismissListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onDismiss(DialogInterface dialog) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				break;
				case "ObjectAnimator":
				javaCode.append("        ").append(fieldName).append(" = new ObjectAnimator();\n");
				javaCode.append("        ").append(fieldName).append(".addListener(new Animator.AnimatorListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onAnimationStart(Animator animation) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onAnimationEnd(Animator animation) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onAnimationCancel(Animator animation) {}\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onAnimationRepeat(Animator animation) {}\n");
				javaCode.append("        });\n");
				break;
				case "SharedPreferences":
				javaCode.append("        ").append(fieldName).append(" = getSharedPreferences(\"prefs\", MODE_PRIVATE);\n");
				javaCode.append("        ").append(fieldName).append(".registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onSharedPreferenceChanged(SharedPreferences sp, String key) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				break;
				case "AsyncTask":
				javaCode.append("        // AsyncTask should be subclassed as subclass\n");
				break;
				case "Handler":
				javaCode.append("        ").append(fieldName).append(" = new Handler(Looper.getMainLooper());\n");
				javaCode.append("        // Handler typically uses post() with Runnable\n");
				break;
				case "Service":
				javaCode.append("        // Service is typically started, not instantiated directly\n");
				break;
				case "BroadcastReceiver":
				javaCode.append("        ").append(fieldName).append(" = new BroadcastReceiver() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onReceive(Context context, Intent intent) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        };\n");
				javaCode.append("        registerReceiver(").append(fieldName).append(", new IntentFilter());\n");
				break;
				case "ContentProvider":
				javaCode.append("        // ContentProvider is initialized by system\n");
				break;
				case "Fragment":
				javaCode.append("        // Fragment lifecycle handled separately\n");
				break;
				case "ViewModel":
				javaCode.append("        ").append(fieldName).append(" = new ViewModelProvider(this).get(ViewModel.class);\n");
				break;
				case "LiveData":
				javaCode.append("        // LiveData is observed with Observer\n");
				javaCode.append("        ").append(fieldName).append(".observe(this, new Observer<Object>() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onChanged(Object value) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				break;
				case "Room":
				javaCode.append("        // Room database initialized separately\n");
				break;
				case "WorkManager":
				javaCode.append("        // WorkManager initialized with WorkRequest\n");
				break;
				case "RecyclerView":
				javaCode.append("        ").append(fieldName).append(" = findViewById(R.id.").append(fieldName).append(");\n");
				javaCode.append("        ").append(fieldName).append(".addOnScrollListener(new RecyclerView.OnScrollListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				break;
				case "ViewPager":
				javaCode.append("        ").append(fieldName).append(" = findViewById(R.id.").append(fieldName).append(");\n");
				javaCode.append("        ").append(fieldName).append(".addOnPageChangeListener(new ViewPager.OnPageChangeListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onPageSelected(int position) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onPageScrollStateChanged(int state) {}\n");
				javaCode.append("        });\n");
				break;
				case "MediaPlayer":
				javaCode.append("        ").append(fieldName).append(" = new MediaPlayer();\n");
				javaCode.append("        ").append(fieldName).append(".setOnCompletionListener(new MediaPlayer.OnCompletionListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onCompletion(MediaPlayer mp) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				break;
				case "Camera":
				javaCode.append("        // Legacy Camera API initialization\n");
				break;
				case "LocationManager":
				javaCode.append("        ").append(fieldName).append(" = (LocationManager) getSystemService(Context.LOCATION_SERVICE);\n");
				javaCode.append("        ").append(fieldName).append(".requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onLocationChanged(Location location) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				break;
				case "SensorManager":
				javaCode.append("        ").append(fieldName).append(" = (SensorManager) getSystemService(Context.SENSOR_SERVICE);\n");
				javaCode.append("        ").append(fieldName).append(".registerListener(new SensorEventListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onSensorChanged(SensorEvent event) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onAccuracyChanged(Sensor sensor, int accuracy) {}\n");
				javaCode.append("        }, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);\n");
				break;
				case "BluetoothAdapter":
				javaCode.append("        ").append(fieldName).append(" = BluetoothAdapter.getDefaultAdapter();\n");
				break; // Events typically handled via BroadcastReceiver
				case "CameraX":
				javaCode.append("        // CameraX initialization\n");
				break;
				case "FilePicker":
				javaCode.append("        ").append(fieldName).append(" = new Intent(Intent.ACTION_OPEN_DOCUMENT);\n");
				javaCode.append("        ").append(fieldName).append(".setType(\"*/*\");\n");
				break;
				case "ImagePicker":
				javaCode.append("        ").append(fieldName).append(" = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);\n");
				break;
				case "VideoPicker":
				javaCode.append("        ").append(fieldName).append(" = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);\n");
				break;
				case "AudioRecorder":
				javaCode.append("        ").append(fieldName).append(" = new MediaRecorder();\n");
				break;
				case "SpeechRecognizer":
				javaCode.append("        ").append(fieldName).append(" = SpeechRecognizer.createSpeechRecognizer(this);\n");
				javaCode.append("        ").append(fieldName).append(".setRecognitionListener(new RecognitionListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onResults(Bundle results) {\n");
				javaCode.append("                ").append(logic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("            // Implement other RecognitionListener methods as needed\n");
				javaCode.append("        });\n");
				break;
				case "QRScanner":
				javaCode.append("        // QRScanner typically handled via Activity result\n");
				break;
				case "DocumentPicker":
				javaCode.append("        ").append(fieldName).append(" = new Intent(Intent.ACTION_OPEN_DOCUMENT);\n");
				break;
				case "BiometricAuth":
				javaCode.append("        ").append(fieldName).append(" = new BiometricPrompt(this, ContextCompat.getMainExecutor(this), new BiometricPrompt.AuthenticationCallback() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {\n");
				javaCode.append("                ").append(logic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				break;
				case "Timer":
				javaCode.append("        ").append(fieldName).append(" = new TimerTask() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void run() {\n");
				javaCode.append("                ").append(logic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        };\n");
				javaCode.append("        _timer.schedule(").append(fieldName).append(", 0, 1000);\n");
				break;
			}
		}
		
		bindWidgetEvents(activityBean.getActivityName(), javaCode);
		
		javaCode.append("\n    }\n");
		
		String logic = getBlockLogics(activityBean.getActivityName());
		
		javaCode.append("    private void initializeLogic() {\n");
		
		if (logic != null && !logic.isEmpty()) {
			String[] lines = logic.split("\n");
			for (String line : lines) {
				javaCode.append("        ").append(line.strip()).append("\n"); // proper 8-space indent
			}
		}
		
		javaCode.append("    }\n");
		
		
		// After your onCreate() method generation, add lifecycle events
		try {
			String path = FileHandler.codeSavePath + "/" + sc_id + "/events/lifecycle_events.json";
			File file = new File(path);
			JSONObject allEvents = new JSONObject();
			
			if (file.exists()) {
				String json = FileHandler.readFile(path);
				String decoded = new String(Base64.decode(json, Base64.DEFAULT));
				allEvents = new JSONObject(decoded);
			}
			
			String activityName = activityBean.getActivityName();
			JSONArray existingArray = allEvents.optJSONArray(activityName);
			
			if (existingArray != null) {
				for (int i = 0; i < existingArray.length(); i++) {
					String event = existingArray.optString(i);
					String eventLogic = getBlockLogicForEvent(event);
					
					String formattedLogic = "";
					if (eventLogic != null && !eventLogic.isEmpty()) {
						formattedLogic = "        " + eventLogic.replace("\n", "\n        ") + "\n";
					}
					
					switch (event) {
						case "onCreate":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onCreate(Bundle savedInstanceState) {\n");
						javaCode.append("        super.onCreate(savedInstanceState);\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onPostCreate":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onPostCreate(Bundle savedInstanceState) {\n");
						javaCode.append("        super.onPostCreate(savedInstanceState);\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onStart":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onStart() {\n");
						javaCode.append("        super.onStart();\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onResume":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onResume() {\n");
						javaCode.append("        super.onResume();\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onPostResume":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onPostResume() {\n");
						javaCode.append("        super.onPostResume();\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onPause":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onPause() {\n");
						javaCode.append("        super.onPause();\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onStop":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onStop() {\n");
						javaCode.append("        super.onStop();\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onRestart":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onRestart() {\n");
						javaCode.append("        super.onRestart();\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onDestroy":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onDestroy() {\n");
						javaCode.append("        super.onDestroy();\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onBackPressed":
						javaCode.append("\n    @Override\n");
						javaCode.append("    public void onBackPressed() {\n");
						javaCode.append("        super.onBackPressed();\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onSaveInstanceState":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onSaveInstanceState(Bundle outState) {\n");
						javaCode.append("        super.onSaveInstanceState(outState);\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onRestoreInstanceState":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onRestoreInstanceState(Bundle savedInstanceState) {\n");
						javaCode.append("        super.onRestoreInstanceState(savedInstanceState);\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onActivityResult":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onActivityResult(int requestCode, int resultCode, Intent data) {\n");
						javaCode.append("        super.onActivityResult(requestCode, resultCode, data);\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onRequestPermissionsResult":
						javaCode.append("\n    @Override\n");
						javaCode.append("    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {\n");
						javaCode.append("        super.onRequestPermissionsResult(requestCode, permissions, grantResults);\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onUserLeaveHint":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onUserLeaveHint() {\n");
						javaCode.append("        super.onUserLeaveHint();\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onTrimMemory":
						javaCode.append("\n    @Override\n");
						javaCode.append("    public void onTrimMemory(int level) {\n");
						javaCode.append("        super.onTrimMemory(level);\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onLowMemory":
						javaCode.append("\n    @Override\n");
						javaCode.append("    public void onLowMemory() {\n");
						javaCode.append("        super.onLowMemory();\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onNewIntent":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onNewIntent(Intent intent) {\n");
						javaCode.append("        super.onNewIntent(intent);\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onConfigurationChanged":
						javaCode.append("\n    @Override\n");
						javaCode.append("    public void onConfigurationChanged(@NonNull Configuration newConfig) {\n");
						javaCode.append("        super.onConfigurationChanged(newConfig);\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						default:
						javaCode.append("\n    // Custom event: ").append(event).append("\n");
						javaCode.append("    private void ").append(event).append("() {\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		/*// Add pretty print utility method
javaCode.append("\n    private String prettyPrint(String message) {\n")
.append("        StringBuilder formatted = new StringBuilder();\n")
.append("        formatted.append(").append(message).append("\\n\"");
.append("        return formatted.toString();\n")
.append("    }\n");*/	
		
		List<HashMap<String, Object>> functions = loadFunctions(activityBean.getActivityName());
		if (functions != null) {
			for (HashMap<String, Object> func : functions) {
				String functionName = (String) func.get("functionName");
				String returnType = (String) func.get("returnType");
				List<HashMap<String, String>> parameters = (List<HashMap<String, String>>) func.get("parameters");
				
				javaCode.append("public ").append(returnType).append(" ").append(functionName).append("(");
				for (int i = 0; i < parameters.size(); i++) {
					HashMap<String, String> param = parameters.get(i);
					javaCode.append(param.get("type")).append(" ").append(param.get("name"));
					if (i < parameters.size() - 1) {
						javaCode.append(", ");
					}
				}
				javaCode.append(") {\n");
				javaCode.append("   \n");
				javaCode.append("}\n\n");
			}
		}
		
		javaCode.append("    public void showMessage(String message) {\n");
		javaCode.append("        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();\n");
		javaCode.append("\n    }\n");
		
		javaCode.append("}\n");
		
		// Save the generated Java code
		complex.setJavaCode(activityBean.getActivityName(), javaCode.toString());
		
		// Pretty print the generated code for debugging (if needed)
		String prettyPrintedCode = prettyPrintCode(javaCode.toString());
		Log.d("GeneratedJavaCode", prettyPrintedCode);
		
		return javaCode.toString();
	}
	
	// Utility method for pretty printing the generated code for debugging
	private String prettyPrintCode(String code) {
		StringBuilder formatted = new StringBuilder();
		String[] lines = code.split("\n");
		int indentLevel = 0;
		for (String line : lines) {
			String trimmedLine = line.trim();
			if (trimmedLine.endsWith("}")) {
				indentLevel--;
			}
			formatted.append("  ".repeat(Math.max(0, indentLevel)))
			.append(trimmedLine)
			.append("\n");
			if (trimmedLine.endsWith("{")) {
				indentLevel++;
			}
		}
		return formatted.toString();
	}
	
	public void generateJavaCode(String acName, String layoutName) {
		StringBuilder javaCode = new StringBuilder();
		javaCode.append("package ").append(pkgName).append(";\n\n");
		
		if (complex.getAndroidXEnable()) {
			javaCode.append("import androidx.appcompat.app.AppCompatActivity;\n");
			javaCode.append("import androidx.fragment.app.Fragment;\n");
			javaCode.append("import androidx.fragment.app.FragmentManager;\n");
			javaCode.append("import androidx.fragment.app.DialogFragment;\n");
			javaCode.append("import com.google.android.material.*;\n");
		} else {
			javaCode.append("import android.app.Activity;\n");
		}
		
		javaCode.append("import android.app.*;\n");
		javaCode.append("import android.os.*;\n");
		javaCode.append("import android.os.Bundle;\n");
		javaCode.append("import android.widget.*;\n");
		javaCode.append("import android.text.*;\n");
		javaCode.append("import android.net.*;\n");
		javaCode.append("import android.util.*;\n");
		javaCode.append("import android.view.*;\n");
		javaCode.append("import android.graphics.*;\n");
		javaCode.append("import android.content.*;\n");
		javaCode.append("import android.widget.Toast;\n");
		javaCode.append("import android.webkit.*;\n");
		javaCode.append("import android.view.animation.*;\n");
		
		// Add imports for components
		List<HashMap<String, String>> variables = loadVariableLogic(acName);
		List<HashMap<String, String>> components = loadComponentLogic(acName);
		for (HashMap<String, String> component : components) {
			String componentName = component.get("componentName");
			switch (componentName) {
				case "Intent":
				javaCode.append("import android.content.Intent;\n");
				break;
				case "Dialog":
				javaCode.append("import android.app.Dialog;\n");
				break;
				case "ObjectAnimator":
				javaCode.append("import android.animation.ObjectAnimator;\n");
				break;
				case "SharedPreferences":
				javaCode.append("import android.content.SharedPreferences;\n");
				break;
				case "AsyncTask":
				javaCode.append("import android.os.AsyncTask;\n");
				break;
				case "Handler":
				javaCode.append("import android.os.Handler;\n");
				break;
				case "Service":
				javaCode.append("import android.app.Service;\n");
				break;
				case "BroadcastReceiver":
				javaCode.append("import android.content.BroadcastReceiver;\n");
				break;
				case "ContentProvider":
				javaCode.append("import android.content.ContentProvider;\n");
				break;
				case "Fragment":
				javaCode.append("import androidx.fragment.app.Fragment;\n");
				break;
				case "ViewModel":
				javaCode.append("import androidx.lifecycle.ViewModel;\n");
				break;
				case "LiveData":
				javaCode.append("import androidx.lifecycle.LiveData;\n");
				break;
				case "Room":
				javaCode.append("import androidx.room.*;\n");
				break;
				case "WorkManager":
				javaCode.append("import androidx.work.*;\n");
				break;
				case "RecyclerView":
				javaCode.append("import androidx.recyclerview.widget.RecyclerView;\n");
				break;
				case "ViewPager":
				javaCode.append("import androidx.viewpager.widget.ViewPager;\n");
				break;
				case "MediaPlayer":
				javaCode.append("import android.media.MediaPlayer;\n");
				break;
				case "Camera":
				javaCode.append("import android.hardware.Camera;\n");
				break;
				case "LocationManager":
				javaCode.append("import android.location.LocationManager;\n");
				break;
				case "SensorManager":
				javaCode.append("import android.hardware.SensorManager;\n");
				break;
				case "BluetoothAdapter":
				javaCode.append("import android.bluetooth.BluetoothAdapter;\n");
				break;
				case "Timer":
				javaCode.append("import java.util.Timer;\n");
				javaCode.append("import java.util.TimerTask;\n");
				break;
				case "CameraX":
				javaCode.append("import androidx.camera.core.*;\n");
				javaCode.append("import androidx.camera.lifecycle.ProcessCameraProvider;\n");
				break;
				case "FilePicker":
				javaCode.append("import android.content.Intent;\n");
				javaCode.append("import android.provider.DocumentsContract;\n");
				break;
				case "ImagePicker":
				javaCode.append("import android.provider.MediaStore;\n");
				break;
				case "VideoPicker":
				javaCode.append("import android.provider.MediaStore;\n");
				break;
				case "AudioRecorder":
				javaCode.append("import android.media.MediaRecorder;\n");
				break;
				case "SpeechRecognizer":
				javaCode.append("import android.speech.SpeechRecognizer;\n");
				break;
				case "QRScanner":
				javaCode.append("import com.google.zxing.integration.android.IntentIntegrator;\n");
				javaCode.append("import com.google.zxing.integration.android.IntentResult;\n");
				break;
				case "DocumentPicker":
				javaCode.append("import android.content.Intent;\n");
				javaCode.append("import android.provider.OpenableColumns;\n");
				break;
				case "BiometricAuth":
				javaCode.append("import androidx.biometric.BiometricPrompt;\n");
				javaCode.append("import androidx.core.content.ContextCompat;\n");
				break;
			}
		}
		
		for (HashMap<String, String> componentA : variables) {
			String componentName = componentA.get("varTypeName");
			switch (componentName) {
				case "ArrayList<String>":
				case "ArrayList<Double>":
				javaCode.append("import java.util.ArrayList;\n");
				break;
			}
		}
		
		javaCode.append("\npublic class ").append(acName).append(" extends ");
		javaCode.append(complex.getAndroidXEnable() ? "AppCompatActivity" : "Activity").append(" {\n\n");
		
		// Add widget fields
		declareWidgetFields(acName, javaCode);
		
		// Add component fields
		boolean timerDeclared = false;
		
		for (HashMap<String, String> component : components) {
			String componentName = component.get("componentName");
			String fieldName = component.get("fieldName");
			
			if ("Timer".equals(componentName)) {
				if (!timerDeclared) {
					javaCode.append("private Timer _timer = new Timer();\n");
					timerDeclared = true;
				}
				javaCode.append("private TimerTask ").append(fieldName).append(";\n");
			} else {
				javaCode.append("private ").append(componentName).append(" ").append(fieldName).append(";\n");
			}
		}
		
		for (HashMap<String, String> variable : variables) {
			String varType = variable.get("varTypeName");
			String varName = variable.get("varName");
			javaCode.append("    private ").append(varType).append(" ").append(varName).append(";\n");
		}
		
		javaCode.append("\n    @Override\n");
		javaCode.append("    protected void onCreate(Bundle savedInstanceState) {\n");
		javaCode.append("        super.onCreate(savedInstanceState);\n");
		javaCode.append("        setContentView(R.layout.").append(layoutName).append(");\n");
		javaCode.append("        initialize(savedInstanceState);\n");
		javaCode.append("        initializeLogic();\n");
		javaCode.append("    }\n");
		
		javaCode.append("    private void initialize(Bundle _savedInstanceState) {\n");
		initializeWidgetFields(acName, javaCode);
		
		// Initialize components with event listeners
		for (HashMap<String, String> component : components) {
			String componentName = component.get("componentName");
			String fieldName = component.get("fieldName");
			String logic = getBlockLogic(fieldName); // Assuming this fetches component-specific logic
			String eventLogic = getBlockLogicForEvent(fieldName);
			switch (componentName) {
				case "Intent":
				javaCode.append("        ").append(fieldName).append(" = new Intent();\n");
				break;
				case "Dialog":
				javaCode.append("        ").append(fieldName).append(" = new Dialog(this);\n");
				javaCode.append("        ").append(fieldName).append(".setOnShowListener(new DialogInterface.OnShowListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onShow(DialogInterface dialog) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				javaCode.append("        ").append(fieldName).append(".setOnDismissListener(new DialogInterface.OnDismissListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onDismiss(DialogInterface dialog) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				break;
				case "ObjectAnimator":
				javaCode.append("        ").append(fieldName).append(" = new ObjectAnimator();\n");
				javaCode.append("        ").append(fieldName).append(".addListener(new Animator.AnimatorListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onAnimationStart(Animator animation) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onAnimationEnd(Animator animation) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onAnimationCancel(Animator animation) {}\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onAnimationRepeat(Animator animation) {}\n");
				javaCode.append("        });\n");
				break;
				case "SharedPreferences":
				javaCode.append("        ").append(fieldName).append(" = getSharedPreferences(\"prefs\", MODE_PRIVATE);\n");
				javaCode.append("        ").append(fieldName).append(".registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onSharedPreferenceChanged(SharedPreferences sp, String key) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				break;
				case "AsyncTask":
				javaCode.append("        // AsyncTask should be subclassed as subclass\n");
				break;
				case "Handler":
				javaCode.append("        ").append(fieldName).append(" = new Handler(Looper.getMainLooper());\n");
				javaCode.append("        // Handler typically uses post() with Runnable\n");
				break;
				case "Service":
				javaCode.append("        // Service is typically started, not instantiated directly\n");
				break;
				case "BroadcastReceiver":
				javaCode.append("        ").append(fieldName).append(" = new BroadcastReceiver() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onReceive(Context context, Intent intent) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        };\n");
				javaCode.append("        registerReceiver(").append(fieldName).append(", new IntentFilter());\n");
				break;
				case "ContentProvider":
				javaCode.append("        // ContentProvider is initialized by system\n");
				break;
				case "Fragment":
				javaCode.append("        // Fragment lifecycle handled separately\n");
				break;
				case "ViewModel":
				javaCode.append("        ").append(fieldName).append(" = new ViewModelProvider(this).get(ViewModel.class);\n");
				break;
				case "LiveData":
				javaCode.append("        // LiveData is observed with Observer\n");
				javaCode.append("        ").append(fieldName).append(".observe(this, new Observer<Object>() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onChanged(Object value) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				break;
				case "Room":
				javaCode.append("        // Room database initialized separately\n");
				break;
				case "WorkManager":
				javaCode.append("        // WorkManager initialized with WorkRequest\n");
				break;
				case "RecyclerView":
				javaCode.append("        ").append(fieldName).append(" = findViewById(R.id.").append(fieldName).append(");\n");
				javaCode.append("        ").append(fieldName).append(".addOnScrollListener(new RecyclerView.OnScrollListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				break;
				case "ViewPager":
				javaCode.append("        ").append(fieldName).append(" = findViewById(R.id.").append(fieldName).append(");\n");
				javaCode.append("        ").append(fieldName).append(".addOnPageChangeListener(new ViewPager.OnPageChangeListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onPageSelected(int position) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onPageScrollStateChanged(int state) {}\n");
				javaCode.append("        });\n");
				break;
				case "MediaPlayer":
				javaCode.append("        ").append(fieldName).append(" = new MediaPlayer();\n");
				javaCode.append("        ").append(fieldName).append(".setOnCompletionListener(new MediaPlayer.OnCompletionListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onCompletion(MediaPlayer mp) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				break;
				case "Camera":
				javaCode.append("        // Legacy Camera API initialization\n");
				break;
				case "LocationManager":
				javaCode.append("        ").append(fieldName).append(" = (LocationManager) getSystemService(Context.LOCATION_SERVICE);\n");
				javaCode.append("        ").append(fieldName).append(".requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onLocationChanged(Location location) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				break;
				case "SensorManager":
				javaCode.append("        ").append(fieldName).append(" = (SensorManager) getSystemService(Context.SENSOR_SERVICE);\n");
				javaCode.append("        ").append(fieldName).append(".registerListener(new SensorEventListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onSensorChanged(SensorEvent event) {\n");
				javaCode.append("                ").append(eventLogic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onAccuracyChanged(Sensor sensor, int accuracy) {}\n");
				javaCode.append("        }, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);\n");
				break;
				case "BluetoothAdapter":
				javaCode.append("        ").append(fieldName).append(" = BluetoothAdapter.getDefaultAdapter();\n");
				break; // Events typically handled via BroadcastReceiver
				case "CameraX":
				javaCode.append("        // CameraX initialization\n");
				break;
				case "FilePicker":
				javaCode.append("        ").append(fieldName).append(" = new Intent(Intent.ACTION_OPEN_DOCUMENT);\n");
				javaCode.append("        ").append(fieldName).append(".setType(\"*/*\");\n");
				break;
				case "ImagePicker":
				javaCode.append("        ").append(fieldName).append(" = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);\n");
				break;
				case "VideoPicker":
				javaCode.append("        ").append(fieldName).append(" = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);\n");
				break;
				case "AudioRecorder":
				javaCode.append("        ").append(fieldName).append(" = new MediaRecorder();\n");
				break;
				case "SpeechRecognizer":
				javaCode.append("        ").append(fieldName).append(" = SpeechRecognizer.createSpeechRecognizer(this);\n");
				javaCode.append("        ").append(fieldName).append(".setRecognitionListener(new RecognitionListener() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onResults(Bundle results) {\n");
				javaCode.append("                ").append(logic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("            // Implement other RecognitionListener methods as needed\n");
				javaCode.append("        });\n");
				break;
				case "QRScanner":
				javaCode.append("        // QRScanner typically handled via Activity result\n");
				break;
				case "DocumentPicker":
				javaCode.append("        ").append(fieldName).append(" = new Intent(Intent.ACTION_OPEN_DOCUMENT);\n");
				break;
				case "BiometricAuth":
				javaCode.append("        ").append(fieldName).append(" = new BiometricPrompt(this, ContextCompat.getMainExecutor(this), new BiometricPrompt.AuthenticationCallback() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {\n");
				javaCode.append("                ").append(logic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        });\n");
				break;
				case "Timer":
				javaCode.append("        ").append(fieldName).append(" = new TimerTask() {\n");
				javaCode.append("            @Override\n");
				javaCode.append("            public void run() {\n");
				javaCode.append("                ").append(logic).append("\n");
				javaCode.append("            }\n");
				javaCode.append("        };\n");
				javaCode.append("        _timer.schedule(").append(fieldName).append(", 0, 1000);\n");
				break;
			}
		}
		
		bindWidgetEvents(acName, javaCode);
		
		javaCode.append("\n    }\n");
		
		String logic = getBlockLogics(acName);
		
		javaCode.append("    private void initializeLogic() {\n");
		
		if (logic != null && !logic.isEmpty()) {
			String[] lines = logic.split("\n");
			for (String line : lines) {
				javaCode.append("        ").append(line.strip()).append("\n"); // proper 8-space indent
			}
		}
		
		javaCode.append("    }\n");
		
		
		// After your onCreate() method generation, add lifecycle events
		try {
			String path = FileHandler.codeSavePath + "/" + sc_id + "/events/lifecycle_events.json";
			File file = new File(path);
			JSONObject allEvents = new JSONObject();
			
			if (file.exists()) {
				String json = FileHandler.readFile(path);
				String decoded = new String(Base64.decode(json, Base64.DEFAULT));
				allEvents = new JSONObject(decoded);
			}
			
			String activityName = activityBean.getActivityName();
			JSONArray existingArray = allEvents.optJSONArray(activityName);
			
			if (existingArray != null) {
				for (int i = 0; i < existingArray.length(); i++) {
					String event = existingArray.optString(i);
					String eventLogic = getBlockLogicForEvent(event);
					
					String formattedLogic = "";
					if (eventLogic != null && !eventLogic.isEmpty()) {
						formattedLogic = "        " + eventLogic.replace("\n", "\n        ") + "\n";
					}
					
					switch (event) {
						case "onCreate":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onCreate(Bundle savedInstanceState) {\n");
						javaCode.append("        super.onCreate(savedInstanceState);\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onPostCreate":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onPostCreate(Bundle savedInstanceState) {\n");
						javaCode.append("        super.onPostCreate(savedInstanceState);\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onStart":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onStart() {\n");
						javaCode.append("        super.onStart();\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onResume":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onResume() {\n");
						javaCode.append("        super.onResume();\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onPostResume":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onPostResume() {\n");
						javaCode.append("        super.onPostResume();\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onPause":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onPause() {\n");
						javaCode.append("        super.onPause();\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onStop":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onStop() {\n");
						javaCode.append("        super.onStop();\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onRestart":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onRestart() {\n");
						javaCode.append("        super.onRestart();\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onDestroy":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onDestroy() {\n");
						javaCode.append("        super.onDestroy();\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onBackPressed":
						javaCode.append("\n    @Override\n");
						javaCode.append("    public void onBackPressed() {\n");
						javaCode.append("        super.onBackPressed();\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onSaveInstanceState":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onSaveInstanceState(Bundle outState) {\n");
						javaCode.append("        super.onSaveInstanceState(outState);\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onRestoreInstanceState":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onRestoreInstanceState(Bundle savedInstanceState) {\n");
						javaCode.append("        super.onRestoreInstanceState(savedInstanceState);\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onActivityResult":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onActivityResult(int requestCode, int resultCode, Intent data) {\n");
						javaCode.append("        super.onActivityResult(requestCode, resultCode, data);\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onRequestPermissionsResult":
						javaCode.append("\n    @Override\n");
						javaCode.append("    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {\n");
						javaCode.append("        super.onRequestPermissionsResult(requestCode, permissions, grantResults);\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onUserLeaveHint":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onUserLeaveHint() {\n");
						javaCode.append("        super.onUserLeaveHint();\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onTrimMemory":
						javaCode.append("\n    @Override\n");
						javaCode.append("    public void onTrimMemory(int level) {\n");
						javaCode.append("        super.onTrimMemory(level);\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onLowMemory":
						javaCode.append("\n    @Override\n");
						javaCode.append("    public void onLowMemory() {\n");
						javaCode.append("        super.onLowMemory();\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onNewIntent":
						javaCode.append("\n    @Override\n");
						javaCode.append("    protected void onNewIntent(Intent intent) {\n");
						javaCode.append("        super.onNewIntent(intent);\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						case "onConfigurationChanged":
						javaCode.append("\n    @Override\n");
						javaCode.append("    public void onConfigurationChanged(@NonNull Configuration newConfig) {\n");
						javaCode.append("        super.onConfigurationChanged(newConfig);\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
						
						default:
						javaCode.append("\n    // Custom event: ").append(event).append("\n");
						javaCode.append("    private void ").append(event).append("() {\n");
						javaCode.append(formattedLogic);
						javaCode.append("    }\n");
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		/*// Add pretty print utility method
javaCode.append("\n    private String prettyPrint(String message) {\n")
.append("        StringBuilder formatted = new StringBuilder();\n")
.append("        formatted.append(").append(message).append("\\n\"");
.append("        return formatted.toString();\n")
.append("    }\n");*/	
		
		List<HashMap<String, Object>> functions = loadFunctions(acName);
		if (functions != null) {
			for (HashMap<String, Object> func : functions) {
				String functionName = (String) func.get("functionName");
				String returnType = (String) func.get("returnType");
				List<HashMap<String, String>> parameters = (List<HashMap<String, String>>) func.get("parameters");
				
				javaCode.append("public ").append(returnType).append(" ").append(functionName).append("(");
				for (int i = 0; i < parameters.size(); i++) {
					HashMap<String, String> param = parameters.get(i);
					javaCode.append(param.get("type")).append(" ").append(param.get("name"));
					if (i < parameters.size() - 1) {
						javaCode.append(", ");
					}
				}
				javaCode.append(") {\n");
				javaCode.append("   \n");
				javaCode.append("}\n\n");
			}
		}
		
		javaCode.append("    public void showMessage(String message) {\n");
		javaCode.append("        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();\n");
		javaCode.append("\n    }\n");
		
		javaCode.append("}\n");
		
		// Save the generated Java code
		complex.setJavaCode(acName, javaCode.toString());
		
		// Pretty print the generated code for debugging (if needed)
		String prettyPrintedCode = prettyPrintCode(javaCode.toString());
		Log.d("GeneratedJavaCode", prettyPrintedCode);
	}
	
	private void bindWidgetEvents(String activityName, StringBuilder javaCode) {
		Map<String, List<String>> widgets = getWidgetInfoForActivity(activityName);
		
		for (Map.Entry<String, List<String>> entry : widgets.entrySet()) {
			String widgetType = entry.getKey();
			List<String> widgetIds = entry.getValue();
			
			for (String widgetId : widgetIds) {
				String logic = getBlockLogic(widgetId); // tumhara logic fetch karne ka method
				
				switch (widgetType) {
					
					case "CheckBox":
					case "Switch":
					javaCode.append("\n        ").append(widgetId)
					.append(".setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {\n")
					.append("            @Override\n")
					.append("            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {\n")
					.append("                ").append(logic).append("\n")
					.append("            }\n")
					.append("        });\n");
					break;
					
					case "EditText":
					javaCode.append("\n        ").append(widgetId)
					.append(".addTextChangedListener(new TextWatcher() {\n")
					.append("            @Override\n")
					.append("            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}\n")
					.append("            @Override\n")
					.append("            public void onTextChanged(CharSequence s, int start, int before, int count) {\n")
					.append("                ").append(logic).append("\n")
					.append("            }\n")
					.append("            @Override\n")
					.append("            public void afterTextChanged(Editable s) {}\n")
					.append("        });\n");
					break;
					
					case "SeekBar":
					javaCode.append("\n        ").append(widgetId)
					.append(".setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {\n")
					.append("            @Override\n")
					.append("            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {\n")
					.append("                ").append(logic).append("\n")
					.append("            }\n")
					.append("            @Override\n")
					.append("            public void onStartTrackingTouch(SeekBar seekBar) {}\n")
					.append("            @Override\n")
					.append("            public void onStopTrackingTouch(SeekBar seekBar) {}\n")
					.append("        });\n");
					break;
					
					case "Spinner":
					javaCode.append("\n        ").append(widgetId)
					.append(".setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {\n")
					.append("            @Override\n")
					.append("            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {\n")
					.append("                ").append(logic).append("\n")
					.append("            }\n")
					.append("            @Override\n")
					.append("            public void onNothingSelected(AdapterView<?> parent) {}\n")
					.append("        });\n");
					break;
					
					default:
					javaCode.append("\n        ").append(widgetId)
					.append(".setOnClickListener(new View.OnClickListener() {\n")
					.append("            @Override\n")
					.append("            public void onClick(View v) {\n")
					.append("                ").append(logic).append("\n")
					.append("            }\n")
					.append("        });\n");
					break;
				}
			}
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