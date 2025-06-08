package com.besome.blacklogics;

import android.Manifest;
import android.animation.*;
import android.animation.ObjectAnimator;
import android.app.*;
import android.content.*;
import android.content.pm.PackageManager;
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
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.multidex.*;
import androidx.recyclerview.*;
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
import java.net.URI;
import java.io.File;
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
import com.nexusteam.internal.os.layouteditor.widget.WidgetContract;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.File;
import java.util.ArrayList;
import android.util.ArrayMap;
import com.google.gson.Gson;
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
import androidx.core.view.GravityCompat;
import androidx.viewpager2.widget.ViewPager2;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.google.android.material.tabs.TabLayoutMediator;
import androidx.fragment.app.FragmentActivity;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.activities.tools.ConfigActivity;
import mod.hey.studios.activity.managers.assets.ManageAssetsActivity;
import mod.hey.studios.activity.managers.java.ManageJavaActivity;
import mod.hey.studios.activity.managers.nativelib.ManageNativelibsActivity;
import mod.agus.jcoderz.editor.manage.permission.ManagePermissionActivity;
import mod.agus.jcoderz.editor.manage.resource.ManageResourceActivity;
import mod.hey.studios.project.proguard.ManageProguardActivity;
import mod.hey.studios.project.stringfog.ManageStringfogActivity;
import dev.aldi.sayuti.editor.manage.ManageLocalLibraryActivity;
import com.besome.blacklogics.tools.CompileLogActivity;
import mod.jbk.diagnostic.CompileErrorSaver;
import androidx.core.content.FileProvider;
import android.content.ActivityNotFoundException;
import mod.hey.studios.build.BuildSettingsDialog;
import mod.hey.studios.logic.SourceCodeDialog;
import com.besome.blacklogics.beans.ProjectBean;
import androidx.core.content.ContextCompat;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.besome.blacklogics.custom_blocks.CustomBlocksDialog;
import com.google.gson.Gson;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import mod.hey.studios.build.BuildSettings;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import android.text.util.Linkify;
import android.view.View;
import android.widget.Spinner;
import android.widget.ViewAnimator;
import android.util.Log;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;


public class DesignActivity extends AppCompatActivity implements CompilerLogListener, CustomBlocksDialog.OnBlocksSelectedListener {
	
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
	
	private Handler handler;
	private Runnable uiUpdateRunnable;
	private final Handler viewLoadHandler = new Handler();
	private Runnable viewLoadRunnable;
	private final Handler dialogViewLoadHandler = new Handler();
	private Runnable dialogViewLoadRunnable;
	private BuildSettings settings;
	private Handler spinnerUpdateHandler; // New Handler for Spinner updates
	private Runnable spinnerUpdateRunnable; // Runnable for Spinner updates
	private boolean isSpinnerUpdating = false; 
	
	private Handler javaSourceHandler; // New Handler for Java code updates
	private Runnable javaSourceRunnable; // Runnable for Java code updates
	
	private boolean isRunnableActive = false; // Declare globally
	
	private Consumer<View> declareWidget;
	private Consumer<View> initializeWidget;
	private static Map<String, Map<String, List<String>>> variableMap = new HashMap<>();
	
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
	public static ProjectActivityBean currentActivityBean;
	public static List<ProjectActivityBean.ViewBean> currentLayoutWidgets;
	public static ArrayMap<String, List<ProjectActivityBean.ViewBean>> allWidgetsMap;
	public static String ROOT_VIEW_PATH;
	public static Complex complex;
	private String basePath = "";
	public static DesignActivity abc;
	public Context context;
	public static String projectPath;
	public static String defaultAcName = "MainActivity";
	public static String defaultLayName = "main";
	private String xmlOutput = "";
	
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
	
	private ObjectAnimator anim = new ObjectAnimator();
	private TabAdapterFragmentAdapter tabAdapter;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.design);
		initialize(_savedInstanceState);
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		} else {
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		abc = this;
		context = DesignActivity.this;
		viewEditor = new ViewEditorFragmentActivity();
		complex = new Complex();
		complex.setC(DesignActivity.this);
		complex.setId(getIntent().getStringExtra("sc_id"));
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
		tabAdapter = new TabAdapterFragmentAdapter(getApplicationContext(), getSupportFragmentManager());
		
		tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				final int _position = tab.getPosition();
				pos = _position;
				if (_position == 0) {
					complex.setXmlAdapter(file_spinner);
				}
				if (_position == 1) {
					complex.setJavaAdapter(file_spinner);
				}
				file_spinner.invalidate(); // Force redraw
				startSpinnerUpdate(); // Start periodic Spinner updates
				//ViewEditorFragmentActivity.ll.invalidate();
			}
			
			@Override
			public void onTabUnselected(TabLayout.Tab tab) {
				final int _position = tab.getPosition();
				
			}
			
			@Override
			public void onTabReselected(TabLayout.Tab tab) {
				final int _position = tab.getPosition();
				
			}
		});
		
		img_icon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				FragmentManager fragmentManager = getSupportFragmentManager();
ViewBuilderFragmentActivity activity = (ViewBuilderFragmentActivity) fragmentManager
    .findFragmentByTag("android:switcher:" + customViewPager.getId() + ":0");
				if (activity != null && activity.viewEditor != null) {
						if (activity.viewEditor.isHiddenProperties())
						{ 
								int n = tab_layout.getSelectedTabPosition();
								if (n != 0)
								{
										tab_layout.getTabAt(n - 1).select();
										
										return;
								}
								exibirMensagemEdt("Exit from this project", "Do you want to save your before quitting?");
								return;
						}
				}
				activity.viewEditor.hideProperties();
				
			}
		});
		
		undoIcon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				FragmentManager fragmentManager = getSupportFragmentManager();
ViewBuilderFragmentActivity activity = (ViewBuilderFragmentActivity) fragmentManager
    .findFragmentByTag("android:switcher:" + customViewPager.getId() + ":0");
				if (ViewBuilderFragmentActivity.instance != null && ViewBuilderFragmentActivity.instance.viewEditor != null) {
					    ViewBuilderFragmentActivity.instance.viewEditor.undo();
				}
			}
		});
		
		redoIcon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				FragmentManager fragmentManager = getSupportFragmentManager();
ViewBuilderFragmentActivity activity = (ViewBuilderFragmentActivity) fragmentManager
    .findFragmentByTag("android:switcher:" + customViewPager.getId() + ":0");
				if (ViewBuilderFragmentActivity.instance != null && ViewBuilderFragmentActivity.instance.viewEditor != null) {
					    ViewBuilderFragmentActivity.instance.viewEditor.redo();
				}
			}
		});
		
		save_logic_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				try{
					if (ViewBuilderFragmentActivity.instance != null && ViewBuilderFragmentActivity.instance.viewEditor != null) {
						    ViewBuilderFragmentActivity.instance.saveLayout();
					}
					generateXmlLayout();
					generateJavaCode();
					TheBlockLogicsUtil.showToast(getApplicationContext(),"Projeto Saved!");
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
				generateXmlLayout();
				generateJavaCode();
				_processApk();
			}
		});
	}
	
	private void initializeLogic() {
		context = DesignActivity.this;
		complex.setC(DesignActivity.this);
		sc_id = getIntent().getStringExtra("sc_id");
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
		tv_root_title.setText(getIntent().getStringExtra("scName"));
		tv_sub_title.setText(getIntent().getStringExtra("sc_id"));
		tab_layout.setupWithViewPager(customViewPager);
		tab_layout.setInlineLabel(true);
		tab_layout.setTabTextColors(getResources().getColor(R.color.spinner_text), getResources().getColor(R.color.colorPrimaryVariant));
		tab_layout.setSelectedTabIndicatorColor(getResources().getColor(R.color.tab_ripple));
		tab_layout.setSelectedTabIndicatorHeight(4);
		tabAdapter.setTabCount(3);
		customViewPager.setAdapter(tabAdapter);
		customViewPager.setOffscreenPageLimit((int)0);
		customViewPager.setCurrentItem((int)0);
		currentLayoutWidgets = new ArrayList<>();
		allWidgetsMap = new ArrayMap<>();
		
		LinearLayout _nav_view = (LinearLayout) findViewById(R.id._nav_view);
		androidx.drawerlayout.widget.DrawerLayout
		.LayoutParams lp = new androidx.drawerlayout.widget.DrawerLayout
		.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		lp.gravity=Gravity.RIGHT;
		_nav_view.setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));
		_nav_view.setLayoutParams(lp);
		mLogger = new Logger();
		mLogger.attach(ah);
		widgetpropertiesLinearLayout1 = (LinearLayout) findViewById(R.id.widgetpropertiesLinearLayout1);
		widget_width = (CardView) findViewById(R.id.widget_width);
		widget_height = (CardView) findViewById(R.id.widget_height);
		widget_text = (CardView) findViewById(R.id.text);
		widget_id = (CardView) findViewById(R.id.widget_id);
		widget_src = (CardView) findViewById(R.id.widget_src);
		translationX = (CardView) findViewById(R.id.translationX);
		transY = (CardView) findViewById(R.id.transY);
		colorText = (CardView) findViewById(R.id.colorText);
		textSize = (CardView) findViewById(R.id.textSize);
		textStyle = (CardView) findViewById(R.id.textStyle);
		Lines = (CardView) findViewById(R.id.Lines);
		margin = (CardView) findViewById(R.id.margin);
		padding = (CardView) findViewById(R.id.padding);
		background = (CardView) findViewById(R.id.background);
		widget_inject_attributes = (CardView) findViewById(R.id.widget_inject_attributes);
		gravityLayout = (CardView) findViewById(R.id.gravityLayout);
		layoutGravity = (CardView) findViewById(R.id.layoutGravity);
		checkState = (CardView) findViewById(R.id.checkState);
		switchCheckState = (CardView) findViewById(R.id.switchCheckState);
		progressStyle = (CardView) findViewById(R.id.progressStyle);
		widget_scale = (CardView) findViewById(R.id.widget_scale);
		max_progress = (CardView) findViewById(R.id.max_progress);
		widget_orientation = (CardView) findViewById(R.id.widget_orientation);
		widget_convert = (CardView) findViewById(R.id.widget_convert);
		widget_weight = (CardView) findViewById(R.id.widget_weight);
		ll_properties = (LinearLayoutCompat) findViewById(R.id.ll_properties);
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
		currentActivityBean = new ProjectActivityBean(
		defaultAcName,
		defaultLayName,
		pkgName,
		ViewEditorFragmentActivity.isMainActivity,
		sc_id,
		scName
		);
		currentActivityBean.setUseAndroidX(complex.getAndroidXEnable());
		complex.setSpinnerAdapter(file_spinner);
		File logicFile = new File(basePath, ".blacklogics/data/" + sc_id + "/root_logic");
		if (logicFile.isFile() && logicFile.length() > 0) {
				complex.extractAllLogicsFromJson(logicFile.getAbsolutePath());
		}
		
		settings = new BuildSettings(sc_id);
		projectBean = new ProjectBean();
		projectBean.screens.add(complex.getAllJavaActivity());
		startSpinnerUpdate();
		if (tab_layout.getSelectedTabPosition() == 0) {
			    complex.setXmlAdapter(file_spinner);
			    pos = 0;
		} else {
			    complex.setJavaAdapter(file_spinner);
			    pos = 1;
		}
		if (complex.isJavaActivityAvailable("FileUtil")) {
			
		} else {
			complex.setJavaCode("FileUtil", Lx.e(pkgName));
		}
		if (complex.isJavaActivityAvailable("BlackLogicsUtil")) {
			
		} else {
			complex.setJavaCode("BlackLogicsUtil", Lx.i(pkgName));
		}
		file_spinner.setOnTouchListener(new View.OnTouchListener() {
			    @Override
			    public boolean onTouch(View v, MotionEvent event) {
				        if (event.getAction() == MotionEvent.ACTION_UP) {
					            showCustomSpinnerDialog(); // your custom dialog here
					        }
				        return true; // consume event so default spinner dialog doesn't show
				    }
		});
		if (customViewPager.getCurrentItem() == 0) {
			undoIcon.setVisibility(View.VISIBLE);
			redoIcon.setVisibility(View.VISIBLE);
		} else {
			undoIcon.setVisibility(View.GONE);
			redoIcon.setVisibility(View.GONE);
		}
	}
	
	public class TabAdapterFragmentAdapter extends FragmentStatePagerAdapter {
		// This class is deprecated, you should migrate to ViewPager2:
		// https://developer.android.com/reference/androidx/viewpager2/widget/ViewPager2
		Context context;
		int tabCount;
		
		public TabAdapterFragmentAdapter(Context context, FragmentManager manager) {
			super(manager);
			this.context = context;
		}
		
		public void setTabCount(int tabCount) {
			this.tabCount = tabCount;
		}
		
		@Override
		public int getCount() {
			return tabCount;
		}
		
		@Override
		public CharSequence getPageTitle(int _position) {
			if (_position == 0) {
					return "View";
			} else if (_position == 1) {
					return "Event";
			} else if (_position == 2) {
					return "Component";
			}
			return "View";
		}
		
		@Override
		public Fragment getItem(int _position) {
			if (_position == 0) {
					return new ViewBuilderFragmentActivity();
			} else if (_position == 1) {
					return new EventFragmentActivity();
			} else if (_position == 2) {
					return new ComponentFragmentActivity();
			}
			return new ViewEditorFragmentActivity();
		}
	}
	
	@Override
	public void onBackPressed() {
		if (ViewBuilderFragmentActivity.instance.viewEditor.isHiddenProperties())
		{ 
				int n = tab_layout.getSelectedTabPosition();
				if (n != 0)
				{
						tab_layout.getTabAt(n - 1).select();
						
						return;
				}
				exibirMensagemEdt("Exit from this project", "Do you want to save your before quitting?");
				return;
		}
		ViewBuilderFragmentActivity.instance.viewEditor.hideProperties();
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
	public void onResume() {
		super.onResume();
		
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString("currentActivity", defaultAcName);
		outState.putString("currentLayout", defaultLayName);
		outState.putString("sc_id", sc_id);
		outState.putInt("currentTab", tab_layout.getSelectedTabPosition());
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		defaultAcName = savedInstanceState.getString("currentActivity");
		sc_id = savedInstanceState.getString("sc_id");
		defaultLayName = savedInstanceState.getString("currentLayout");
		int tabPos = savedInstanceState.getInt("currentTab", 0);
		tab_layout.getTabAt(tabPos).select();
		
		super.onRestoreInstanceState(savedInstanceState);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (handler != null && uiUpdateRunnable != null) {
				handler.removeCallbacks(uiUpdateRunnable);
		}
		if (viewLoadHandler != null && viewLoadRunnable != null) {
				viewLoadHandler.removeCallbacks(viewLoadRunnable);
		}
		if (dialogViewLoadHandler != null && dialogViewLoadRunnable != null) {
				dialogViewLoadHandler.removeCallbacks(dialogViewLoadRunnable);
		}
	}
	public void _b() {
	}
	public /*static*/ class WidgetClickListener implements OnClickListener
		{
		        @Override
		        public void onClick(View view)
				{
			            if (saveView()) {
				            /*ProjectActivity.*/selectWidget(view);
				            } 
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
				                    showCreateActivityDialog();
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
	public boolean saveView() {
			FragmentManager fragmentManager = getSupportFragmentManager();
ViewBuilderFragmentActivity activity = (ViewBuilderFragmentActivity) fragmentManager
    .findFragmentByTag("android:switcher:" + customViewPager.getId() + ":0");
			if (ViewBuilderFragmentActivity.instance != null && ViewBuilderFragmentActivity.instance.viewEditor != null) {
					ViewBuilderFragmentActivity.instance.saveLayout();
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
	
	public static LinearLayout getPropertiesPanel() {
		    return linear18;
	}
	/**
TUDO : TOGGLE HIDE PROPERTIES, SHOW PROPERTY'S
**/
	public void hideProperties() {
			anim.setTarget(ll_properties);
			anim.setProperty(View.TRANSLATION_Y);
			anim.setFloatValues(new float[]{(float) ll_properties.getHeight()});
			anim.setInterpolator(new DecelerateInterpolator());
			anim.start();
			
	}
	
	public void showProperties() {
			ll_properties.setVisibility(View.VISIBLE);
			anim.setTarget(ll_properties);
			anim.setProperty(View.TRANSLATION_Y);
			anim.setFloatValues(new float[]{(float) 0});
			anim.setInterpolator(new DecelerateInterpolator());
			anim.start();
	}
	
	/**
TUDO : isHiddenProperties
**/
	public boolean isHiddenProperties() {
		    return ll_properties.getTranslationY() == ((float) ll_properties.getHeight());
	}
	/**
TUDO : unselectSelectedWidget
**/
	public /*static */void unselectSelectedWidget()
		{
		        if (ViewEditorFragmentActivity.selectedWidget != null)
				{
			            ((Widget) ViewEditorFragmentActivity.selectedWidget).setBackgroundColor(0);
			            ViewEditorFragmentActivity.selectedWidget = (View) null;
			            hideProperties();
			        }
		    }
	/**
TUDO : selectWidget
**/
	public/* static */void selectWidget(View view)
	{
			if (ViewEditorFragmentActivity.selectedWidget != null) 
			{
					unselectSelectedWidget();
			}
			ViewEditorFragmentActivity.selectedWidget = view; 
			((Widget) view).setBackgroundColor(Color.parseColor("#77BBCCDD"));
			((TextView)DesignActivity.ll_properties.findViewById(R.id.tv_widget_id)).setText(WidgetUtil.getWidgetId(view));
			//if (requireActivity() instanceof DesignActivity) {
				LinearLayout panel = getPropertiesPanel();
				if (panel != null) {
						((TextView)panel.findViewById(R.id.tv_widget_id)).setText(WidgetUtil.getWidgetId(view));
				}
		//	}
			if ((view instanceof WidgetButton) || (view instanceof WidgetTextView)) {
					DesignActivity.widget_text.setVisibility(View.VISIBLE);
					DesignActivity.colorText.setVisibility(View.VISIBLE);
					DesignActivity.textSize.setVisibility(View.VISIBLE);
					DesignActivity.textStyle.setVisibility(View.VISIBLE);
					DesignActivity.Lines.setVisibility(View.VISIBLE);
					DesignActivity.widget_src.setVisibility(View.GONE);
			        DesignActivity.checkState.setVisibility(View.GONE);
			        DesignActivity.switchCheckState.setVisibility(View.GONE);
			        DesignActivity.progressStyle.setVisibility(View.GONE);
			        DesignActivity.widget_scale.setVisibility(View.GONE);
			        DesignActivity.max_progress.setVisibility(View.GONE);
			}
			
			if ((view instanceof WidgetImageView) || (view instanceof WidgetImageView)) {
					DesignActivity.widget_text.setVisibility(View.GONE);
					DesignActivity.textSize.setVisibility(View.GONE);
					DesignActivity.widget_src.setVisibility(View.VISIBLE);
					DesignActivity.colorText.setVisibility(View.GONE);
					DesignActivity.textStyle.setVisibility(View.GONE);
					DesignActivity.Lines.setVisibility(View.GONE);
			        DesignActivity.checkState.setVisibility(View.GONE);
			        DesignActivity.switchCheckState.setVisibility(View.GONE);
			        DesignActivity.progressStyle.setVisibility(View.GONE);
			        DesignActivity.widget_scale.setVisibility(View.VISIBLE);
			        DesignActivity.max_progress.setVisibility(View.GONE);
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
			        DesignActivity.checkState.setVisibility(View.GONE);
					DesignActivity.Lines.setVisibility(View.GONE);
			        DesignActivity.switchCheckState.setVisibility(View.GONE);
			        DesignActivity.progressStyle.setVisibility(View.GONE);
			        DesignActivity.widget_scale.setVisibility(View.GONE);
			        DesignActivity.max_progress.setVisibility(View.GONE);
			}
			
			if (view instanceof WidgetCheckBox) {
			        DesignActivity.widget_text.setVisibility(View.VISIBLE);
					DesignActivity.checkState.setVisibility(View.VISIBLE);
			        DesignActivity.progressStyle.setVisibility(View.GONE);
			        DesignActivity.widget_scale.setVisibility(View.GONE);
			        DesignActivity.max_progress.setVisibility(View.GONE);
			}  
		    
		    if (view instanceof WidgetSwitch) {
			        DesignActivity.widget_text.setVisibility(View.VISIBLE);
			        DesignActivity.progressStyle.setVisibility(View.GONE);
					DesignActivity.switchCheckState.setVisibility(View.VISIBLE);
			        DesignActivity.widget_scale.setVisibility(View.GONE);
			        DesignActivity.max_progress.setVisibility(View.GONE);
			}   
		    
		    if (view instanceof WidgetProgressBar) {
			        DesignActivity.widget_text.setVisibility(View.GONE);
					DesignActivity.progressStyle.setVisibility(View.VISIBLE);
			        DesignActivity.switchCheckState.setVisibility(View.GONE);
			        DesignActivity.widget_scale.setVisibility(View.GONE);
			        DesignActivity.max_progress.setVisibility(View.VISIBLE);
			}   
		    
		    if ((view instanceof WidgetDigitalClock) || (view instanceof WidgetWebView) || (view instanceof WidgetViewPager) || (view instanceof WidgetListView) || (view instanceof WidgetVideoView)) {
			        DesignActivity.widget_text.setVisibility(View.GONE);
					DesignActivity.progressStyle.setVisibility(View.GONE);
			        DesignActivity.switchCheckState.setVisibility(View.GONE);
			        DesignActivity.textSize.setVisibility(View.GONE);
					DesignActivity.textStyle.setVisibility(View.GONE);
			        DesignActivity.checkState.setVisibility(View.GONE);
					DesignActivity.widget_src.setVisibility(View.GONE);
					DesignActivity.colorText.setVisibility(View.GONE);
			        DesignActivity.Lines.setVisibility(View.GONE);
			        DesignActivity.widget_scale.setVisibility(View.GONE);
			        DesignActivity.max_progress.setVisibility(View.GONE);
			}
			
			showProperties();
	}
	
	public void generateJavaCode() {
			FragmentManager fragmentManager = getSupportFragmentManager();
ViewBuilderFragmentActivity activity = (ViewBuilderFragmentActivity) fragmentManager
    .findFragmentByTag("android:switcher:" + customViewPager.getId() + ":0");
			if (ViewBuilderFragmentActivity.instance != null && ViewBuilderFragmentActivity.instance.viewEditor != null) {
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
					List<HashMap<String, String>> variables = loadVariableLogic(DesignActivity.currentActivityBean.getActivityName());
					List<HashMap<String, String>> components = loadComponentLogic(DesignActivity.currentActivityBean.getActivityName());
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
					
					javaCode.append("\npublic class ").append(currentActivityBean.getActivityName()).append(" extends ");
					javaCode.append(complex.getAndroidXEnable() ? "AppCompatActivity" : "Activity").append(" {\n\n");
					
					// Add widget fields
					for (int i = 0; i < ViewBuilderFragmentActivity.instance.viewEditor.editorLayout.getChildCount(); i++) {
							View view = ViewBuilderFragmentActivity.instance.viewEditor.editorLayout.getChildAt(i);
							declareWidgetFields(view, javaCode);
					}
					
					// Add component fields
					boolean timerDeclared = false;
					
					for (HashMap<String, String> component : components) {
							String componentName = component.get("componentName");
							String fieldName = component.get("fieldName");
							switch (componentName) {
									case "Intent":
									javaCode.append("        ").append(fieldName).append(" = new Intent();\n");
									break;
									case "Dialog":
									javaCode.append("        ").append(fieldName).append(" = new Dialog(this);\n");
									break;
									case "ObjectAnimator":
									javaCode.append("        ").append(fieldName).append(" = new ObjectAnimator();\n");
									break;
									case "CameraX":
									javaCode.append("        // Initialize CameraX if required\n");
									break;
									case "FilePicker":
									javaCode.append("        ").append(fieldName).append(" = new Intent(Intent.ACTION_OPEN_DOCUMENT);\n");
									javaCode.append("        ").append(fieldName).append(".setType(\"*/*\");\n");
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
									break;
									/*	case "QRScanner":
				javaCode.append("        // Use IntentIntegrator to start QR scan\n");
				break;
				case "BiometricAuth":
				javaCode.append("        // Setup Biometric Prompt\n");
				break;*/
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
					javaCode.append("        setContentView(R.layout.").append(currentActivityBean.getLayoutName()).append(");\n");
					javaCode.append("        initialize(savedInstanceState);\n");
					javaCode.append("        initializeLogic();\n");
					javaCode.append("\n    }\n");
					
					javaCode.append("    private void initialize(Bundle _savedInstanceState) {\n");
					for (int i = 0; i < ViewBuilderFragmentActivity.instance.viewEditor.editorLayout.getChildCount(); i++) {
							View view = ViewBuilderFragmentActivity.instance.viewEditor.editorLayout.getChildAt(i);
							initializeWidgetFields(view, javaCode);
					}
					
					// Initialize components
					for (HashMap<String, String> component : components) {
							String componentName = component.get("componentName");
							String fieldName = component.get("fieldName");
							switch (componentName) {
									case "Intent":
									javaCode.append("        ").append(fieldName).append(" = new Intent();\n");
									break;
									case "Dialog":
									javaCode.append("        ").append(fieldName).append(" = new Dialog(this);\n");
									break;
									case "ObjectAnimator":
									javaCode.append("        ").append(fieldName).append(" = new ObjectAnimator();\n");
									break;
							}
					}
					
					for (int y = 0; y < ViewBuilderFragmentActivity.instance.viewEditor.editorLayout.getChildCount(); y++) {
							View view = ViewBuilderFragmentActivity.instance.viewEditor.editorLayout.getChildAt(y);
							if (view instanceof ViewGroup) {
									ViewGroup parent = (ViewGroup) view;
									for (int i = 0; i < parent.getChildCount(); i++) {
											View child = parent.getChildAt(i);
											if (child != null) {
													String widgetIdChild = ViewBuilderFragmentActivity.instance.viewEditor.idManager.getId(child);
													String logic = getBlockLogic(widgetIdChild);
													String widgetType = child.getClass().getSimpleName();
													
													if (widgetIdChild != null && !widgetIdChild.isEmpty()) {
															switch (widgetType) {
																	case "CheckBox":
																	case "Switch":
																	javaCode.append("\n        ")
																	.append(widgetIdChild)
																	.append(".setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {\n")
																	.append("            @Override\n")
																	.append("            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {\n")
																	.append("                ")
																	.append(logic)
																	.append("\n")
																	.append("            }\n")
																	.append("        });\n");
																	break;
																	
																	case "EditText":
																	javaCode.append("\n        ")
																	.append(widgetIdChild)
																	.append(".addTextChangedListener(new TextWatcher() {\n")
																	.append("            @Override\n")
																	.append("            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}\n")
																	.append("            @Override\n")
																	.append("            public void onTextChanged(CharSequence s, int start, int before, int count) {\n")
																	.append("                ")
																	.append(logic)
																	.append("\n")
																	.append("            }\n")
																	.append("            @Override\n")
																	.append("            public void afterTextChanged(Editable s) {}\n")
																	.append("        });\n");
																	break;
																	
																	case "SeekBar":
																	javaCode.append("\n        ")
																	.append(widgetIdChild)
																	.append(".setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {\n")
																	.append("            @Override\n")
																	.append("            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {\n")
																	.append("                ")
																	.append(logic)
																	.append("\n")
																	.append("            }\n")
																	.append("            @Override\n")
																	.append("            public void onStartTrackingTouch(SeekBar seekBar) {}\n")
																	.append("            @Override\n")
																	.append("            public void onStopTrackingTouch(SeekBar seekBar) {}\n")
																	.append("        });\n");
																	break;
																	
																	case "Spinner":
																	javaCode.append("\n        ")
																	.append(widgetIdChild)
																	.append(".setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {\n")
																	.append("            @Override\n")
																	.append("            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {\n")
																	.append("                ")
																	.append(logic)
																	.append("\n")
																	.append("            }\n")
																	.append("            @Override\n")
																	.append("            public void onNothingSelected(AdapterView<?> parent) {}\n")
																	.append("        });\n");
																	break;
																	
																	default:
																	javaCode.append("\n        ")
																	.append(widgetIdChild)
																	.append(".setOnClickListener(new View.OnClickListener() {\n")
																	.append("            @Override\n")
																	.append("            public void onClick(View v) {\n")
																	.append("                ")
																	.append(logic)
																	.append("\n")
																	.append("            }\n")
																	.append("        });\n");
																	break;
															}
													}
											}
									}
							}
					}
					
					
					
					javaCode.append("\n    }\n");
					
					javaCode.append("    private void initializeLogic() {\n")
					.append("        ").append(getBlockLogicForWidget(currentActivityBean.getActivityName() + "initializeLogic")).append("\n")
					.append("    }\n");
					
					List<HashMap<String, Object>> functions = loadFunctions(DesignActivity.currentActivityBean.getActivityName());
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
			        String prettyPrintedCode = prettyPrintCode(javaCode.toString());
					
					// Save the generated Java code
					complex.setJavaCode(currentActivityBean.getActivityName(), javaCode.toString());
			} else {
					
			}
	}
	
	// Helper methods for widget declaration and initialization
	private void declareWidgetFields(View view, StringBuilder javaCode) {
			FragmentManager fragmentManager = getSupportFragmentManager();
ViewBuilderFragmentActivity activity = (ViewBuilderFragmentActivity) fragmentManager
    .findFragmentByTag("android:switcher:" + customViewPager.getId() + ":0");
			String widgetType = view.getClass().getSimpleName();
			String widgetId = ViewBuilderFragmentActivity.instance.viewEditor.idManager.getId(view);
			if (widgetId != null && !widgetId.isEmpty()) {
					javaCode.append("    private ").append(widgetType).append(" ").append(widgetId).append(";\n");
			}
			
			if (view instanceof ViewGroup) {
					ViewGroup viewGroup = (ViewGroup) view;
					for (int i = 0; i < viewGroup.getChildCount(); i++) {
							declareWidgetFields(viewGroup.getChildAt(i), javaCode);
					}
			}
	}
	
	private void initializeWidgetFields(View view, StringBuilder javaCode) {
			FragmentManager fragmentManager = getSupportFragmentManager();
ViewBuilderFragmentActivity activity = (ViewBuilderFragmentActivity) fragmentManager
    .findFragmentByTag("android:switcher:" + customViewPager.getId() + ":0");
			String widgetId = ViewBuilderFragmentActivity.instance.viewEditor.idManager.getId(view);
			if (widgetId != null && !widgetId.isEmpty()) {
					javaCode.append("        ").append(widgetId).append(" = findViewById(R.id.").append(widgetId).append(");\n");
			}
			
			if (view instanceof ViewGroup) {
					ViewGroup viewGroup = (ViewGroup) view;
					for (int i = 0; i < viewGroup.getChildCount(); i++) {
							initializeWidgetFields(viewGroup.getChildAt(i), javaCode);
					}
			}
	}
	
	public String getJavaCode() {
		    FragmentManager fragmentManager = getSupportFragmentManager();
ViewBuilderFragmentActivity activity = (ViewBuilderFragmentActivity) fragmentManager
    .findFragmentByTag("android:switcher:" + customViewPager.getId() + ":0");
		    if (ViewBuilderFragmentActivity.instance != null && ViewBuilderFragmentActivity.instance.viewEditor != null) {
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
			        List<HashMap<String, String>> variables = loadVariableLogic(DesignActivity.currentActivityBean.getActivityName());
			        List<HashMap<String, String>> components = loadComponentLogic(DesignActivity.currentActivityBean.getActivityName());
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
			
			        javaCode.append("\npublic class ").append(currentActivityBean.getActivityName()).append(" extends ");
			        javaCode.append(complex.getAndroidXEnable() ? "AppCompatActivity" : "Activity").append(" {\n\n");
			
			        // Add widget fields
			        for (int i = 0; i < ViewBuilderFragmentActivity.instance.viewEditor.editorLayout.getChildCount(); i++) {
				            View view = ViewBuilderFragmentActivity.instance.viewEditor.editorLayout.getChildAt(i);
				            declareWidgetFields(view, javaCode);
				        }
			
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
			        javaCode.append("        setContentView(R.layout.").append(currentActivityBean.getLayoutName()).append(");\n");
			        javaCode.append("        initialize(savedInstanceState);\n");
			        javaCode.append("        initializeLogic();\n");
			        javaCode.append("\n    }\n");
			
			        javaCode.append("    private void initialize(Bundle _savedInstanceState) {\n");
			        for (int i = 0; i < ViewBuilderFragmentActivity.instance.viewEditor.editorLayout.getChildCount(); i++) {
				            View view = ViewBuilderFragmentActivity.instance.viewEditor.editorLayout.getChildAt(i);
				            initializeWidgetFields(view, javaCode);
				        }
			
			        // Initialize components
			        for (HashMap<String, String> component : components) {
				            String componentName = component.get("componentName");
				            String fieldName = component.get("fieldName");
				            switch (componentName) {
					                case "Intent":
					                    javaCode.append("        ").append(fieldName).append(" = new Intent();\n");
					                    break;
					                case "Dialog":
					                    javaCode.append("        ").append(fieldName).append(" = new Dialog(this);\n");
					                    break;
					                case "ObjectAnimator":
					                    javaCode.append("        ").append(fieldName).append(" = new ObjectAnimator();\n");
					                    break;
					                case "CameraX":
					                    javaCode.append("        // Initialize CameraX if required\n");
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
					                    break;
					            }
				        }
			
			        for (int y = 0; y < ViewBuilderFragmentActivity.instance.viewEditor.editorLayout.getChildCount(); y++) {
				            View view = ViewBuilderFragmentActivity.instance.viewEditor.editorLayout.getChildAt(y);
				            if (view instanceof ViewGroup) {
					                ViewGroup parent = (ViewGroup) view;
					                for (int i = 0; i < parent.getChildCount(); i++) {
						                    View child = parent.getChildAt(i);
						                    if (child != null) {
							                        String widgetIdChild = ViewBuilderFragmentActivity.instance.viewEditor.idManager.getId(child);
							                        String logic = getBlockLogic(widgetIdChild);
							                        String widgetType = child.getClass().getSimpleName();
							
							                        if (widgetIdChild != null && !widgetIdChild.isEmpty()) {
								                            switch (widgetType) {
									                                case "CheckBox":
									                                case "Switch":
									                                    javaCode.append("\n        ")
									                                            .append(widgetIdChild)
									                                            .append(".setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {\n")
									                                            .append("            @Override\n")
									                                            .append("            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {\n")
									                                            .append("                ")
									                                            .append(logic)
									                                            .append("\n")
									                                            .append("            }\n")
									                                            .append("        });\n");
									                                    break;
									
									                                case "EditText":
									                                    javaCode.append("\n        ")
									                                            .append(widgetIdChild)
									                                            .append(".addTextChangedListener(new TextWatcher() {\n")
									                                            .append("            @Override\n")
									                                            .append("            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}\n")
									                                            .append("            @Override\n")
									                                            .append("            public void onTextChanged(CharSequence s, int start, int before, int count) {\n")
									                                            .append("                ")
									                                            .append(logic)
									                                            .append("\n")
									                                            .append("            }\n")
									                                            .append("            @Override\n")
									                                            .append("            public void afterTextChanged(Editable s) {}\n")
									                                            .append("        });\n");
									                                    break;
									
									                                case "SeekBar":
									                                    javaCode.append("\n        ")
									                                            .append(widgetIdChild)
									                                            .append(".setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {\n")
									                                            .append("            @Override\n")
									                                            .append("            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {\n")
									                                            .append("                ")
									                                            .append(logic)
									                                            .append("\n")
									                                            .append("            }\n")
									                                            .append("            @Override\n")
									                                            .append("            public void onStartTrackingTouch(SeekBar seekBar) {}\n")
									                                            .append("            @Override\n")
									                                            .append("            public void onStopTrackingTouch(SeekBar seekBar) {}\n")
									                                            .append("        });\n");
									                                    break;
									
									                                case "Spinner":
									                                    javaCode.append("\n        ")
									                                            .append(widgetIdChild)
									                                            .append(".setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {\n")
									                                            .append("            @Override\n")
									                                            .append("            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {\n")
									                                            .append("                ")
									                                            .append(logic)
									                                            .append("\n")
									                                            .append("            }\n")
									                                            .append("            @Override\n")
									                                            .append("            public void onNothingSelected(AdapterView<?> parent) {}\n")
									                                            .append("        });\n");
									                                    break;
									
									                                default:
									                                    javaCode.append("\n        ")
									                                            .append(widgetIdChild)
									                                            .append(".setOnClickListener(new View.OnClickListener() {\n")
									                                            .append("            @Override\n")
									                                            .append("            public void onClick(View v) {\n")
									                                            .append("                ")
									                                            .append(logic)
									                                            .append("\n")
									                                            .append("            }\n")
									                                            .append("        });\n");
									                                    break;
									                            }
								                        }
							                    }
						                }
					            }
				        }
			
			        javaCode.append("\n    }\n");
			
			        javaCode.append("    private void initializeLogic() {\n")
			                .append("        ").append(getBlockLogicForWidget(currentActivityBean.getActivityName() + "initializeLogic")).append("\n")
			                .append("    }\n");
			
			      /*  // Add pretty print utility method
        javaCode.append("\n    private String prettyPrint(String message) {\n")
                .append("        StringBuilder formatted = new StringBuilder();\n")
                .append("        formatted.append(\"[INFO] \").append(message).append(\"\\n\");\n")
                .append("        return formatted.toString();\n")
                .append("    }\n");*/
			
			        List<HashMap<String, Object>> functions = loadFunctions(DesignActivity.currentActivityBean.getActivityName());
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
			
			        // Modify showMessage to use prettyPrint
			        javaCode.append("    public void showMessage(String message) {\n");
			        javaCode.append("        Toast.makeText(getApplicationContext(), prettyPrint(message), Toast.LENGTH_SHORT).show();\n");
			        javaCode.append("\n    }\n");
			
			        javaCode.append("}\n");
			
			        // Save the generated Java code
			        complex.setJavaCode(currentActivityBean.getActivityName(), javaCode.toString());
			
			        // Pretty print the generated code for debugging (if needed)
			        String prettyPrintedCode = prettyPrintCode(javaCode.toString());
			        Log.d("GeneratedJavaCode", prettyPrintedCode); // Assuming android.util.Log for debugging
			
			        return javaCode.toString();
			    } else {
			        String errorMessage = "Error generating source code...";
			        Log.d("GeneratedJavaCode", prettyPrintCode(errorMessage));
			        return errorMessage;
			    }
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
	/*
TUDO : generateXmlLayout
**/
	/**
* Generates XML layout code for the current activity, using direct View properties.
*/
	public void generateXmlLayout() {
		 try {
			        // Post-process to format attributes one per line
			        FragmentManager fragmentManager = getSupportFragmentManager();
			ViewBuilderFragmentActivity activity = (ViewBuilderFragmentActivity) fragmentManager
			    .findFragmentByTag("android:switcher:" + customViewPager.getId() + ":0");
					if (ViewBuilderFragmentActivity.instance != null && ViewBuilderFragmentActivity.instance.viewEditor != null) {
					   	 xmlOutput = ViewBuilderFragmentActivity.instance.viewEditor.getXMLCode();
					}
			        complex.setXmlCode(currentActivityBean.getLayoutName(), xmlOutput);
			    } catch (Exception e) {
			        e.printStackTrace();
			        return;
			    }
	}
	
	public String getXmlCode() {
		    try {
			        FragmentManager fragmentManager = getSupportFragmentManager();
			        ViewBuilderFragmentActivity activity = (ViewBuilderFragmentActivity) fragmentManager
			            .findFragmentByTag("android:switcher:" + customViewPager.getId() + ":0");
			
			        if (ViewBuilderFragmentActivity.instance != null && ViewBuilderFragmentActivity.instance.viewEditor != null) {
				            return ViewBuilderFragmentActivity.instance.viewEditor.getXMLCode();
				        } else {
				            return "Error generating source code...";
				        }
			    } catch (Exception e) {
			        e.printStackTrace();
			        return "";
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
			try {
					String widgetType = (String) widget.getTag(ViewEditorFragmentActivity.TAG_CUSTOM_CLASS_NAME);
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
					
					Map<String, String> customAttrs = ViewEditorFragmentActivity.widgetCustomAttributes.getOrDefault(widgetId, new HashMap<>());
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
							//element.setAttribute("android:gravity", gravityToString(horizontalScrollView.getGravity()));
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
			if (widget instanceof WidgetTextView) return "TextView";
			if (widget instanceof WidgetButton) return "Button";
			if (widget instanceof WidgetEditText) return "EditText";
			if (widget instanceof WidgetImageView) return "ImageView";
			if (widget instanceof WidgetCircleImageView) return "CircleImageView";
			if (widget instanceof WidgetLinearLayout) return "LinearLayout";
			if (widget instanceof WidgetFrameLayout) return "FrameLayout";
			if (widget instanceof WidgetWebView) return "WebView";
			if (widget instanceof WidgetListView) return "ListView";
			if (widget instanceof WidgetViewPager) return "ViewPager";
			if (widget instanceof WidgetCheckBox) return "CheckBox";
			if (widget instanceof WidgetSwitch) return "Switch";
			if (widget instanceof WidgetVideoView) return "VideoView";
			if (widget instanceof WidgetProgressBar) return "ProgressBar";
			if (widget instanceof WidgetSeekBar) return "SeekBar";
			if (widget instanceof WidgetRadioButton) return "RadioButton";
			if (widget instanceof WidgetSearchView) return "SearchView";
			if (widget instanceof WidgetRatingView) return "RatingBar";
			if (widget instanceof WidgetDigitalClock) return "DigitalClock";
			if (widget instanceof WidgetTimePicker) return "TimePicker";
			if (widget instanceof WidgetScrollView) return "ScrollView";
			if (widget instanceof WidgetHorizontalScrollView) return "HorizontalScrollView";
			return widget.getClass().getSimpleName();
	}
	
	/**
TUDO ; HELPER showCreateActivityDialog
**/
	// Create the dialog
	public void showCreateActivityDialog() {
		    CustomActivityDialog dialog = new CustomActivityDialog(this);
		
		    dialog.setOnSaveClickListener(new CustomActivityDialog.OnSaveClickListener() {
			        @Override
			        public void onSave(String formattedName, String unformattedName) {
				            if (complex != null) {
					                complex.setAcName(formattedName);
					                complex.setLogic(" ", formattedName);
					                complex.setXName(unformattedName);
					            } else {
					                Toast.makeText(getApplicationContext(), "Error: complex is null", Toast.LENGTH_SHORT).show();
					                return;
					            }
				
				            // Spinner update
				            if (file_spinner != null) {
					                complex.setSpinnerAdapter(file_spinner); // Use the new method with Spinner & Context
					
					                // Optionally restore previous selection
					                if (file_spinner.getAdapter() != null && file_spinner.getAdapter().getCount() > 0) {
						                    file_spinner.setSelection(0); // or restore previous if needed
						                }
					            } else {
					                Toast.makeText(getApplicationContext(), "Error: Spinner is null", Toast.LENGTH_SHORT).show();
					            }
				        }
			    });
		
		    // Show the dialog
		    dialog.show();
	}
	
	private void addWidgetInLayout(View v, int index) {
			if (v.getParent() != null) {
					((ViewGroup) v.getParent()).removeView(v);
			}
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.WRAP_CONTENT
			);
			v.setLayoutParams(lp);
			ViewEditorFragmentActivity.ll.addView(v, index);
			v.requestLayout();
			ViewEditorFragmentActivity.addedInLayout = true;
			
			ProjectActivityBean.ViewBean viewBean = createViewBean(v);
			
			// Add to temporary current layout widgets list
			currentLayoutWidgets.add(viewBean);
			
			// Add to currentActivityBean's widgets
			if (currentActivityBean.getActivityName().equals(ViewEditorFragmentActivity.activityName)) {
					List<ProjectActivityBean.ViewBean> activityWidgets = currentActivityBean.getWidgets();
					if (activityWidgets == null) {
							activityWidgets = new ArrayList<>();
							currentActivityBean.setWidgets(activityWidgets);
					}
					activityWidgets.add(viewBean);
			}
			
			// Optionally add to allWidgetsMap if needed
			String layoutKey = ViewEditorFragmentActivity.layoutName;
			List<ProjectActivityBean.ViewBean> layoutWidgets = allWidgetsMap.get(layoutKey);
			if (layoutWidgets == null) {
					layoutWidgets = new ArrayList<>();
					allWidgetsMap.put(layoutKey, layoutWidgets);
			}
			layoutWidgets.add(viewBean);
			
			// Save to root_view.json
			saveWidgetsToFile();
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
		    Dialog dialog = new Dialog(this, R.style.TransparentDialogTheme);
		    dialog.setContentView(R.layout.dialog_custom_view);
		    
		    RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView);
		    FloatingActionButton fab = dialog.findViewById(R.id.fabAddView);
		    MaterialButtonToggleGroup toggleGroup = dialog.findViewById(R.id.toggleGroup);
		    MaterialButton tabView = dialog.findViewById(R.id.tabView);
		    MaterialButton tabCustom = dialog.findViewById(R.id.tabCustomView);
		   
		    tabView.setChecked(true); 
		   
		    Consumer<ViewItem> onItemSelected = item -> {
			        dialog.dismiss();
			        ViewBuilderFragmentActivity.instance.viewEditor.idManager.clearAllIds();
			        
			        if (DesignActivity.abc != null) {
				            String javaCode = DesignActivity.abc.getJavaCode();
				            String xmlCode = DesignActivity.abc.getXmlCode();
				            DesignActivity.abc.complex.setXmlCode(DesignActivity.abc.currentActivityBean.getLayoutName(), xmlCode);
				            DesignActivity.abc.complex.setJavaCode(DesignActivity.abc.currentActivityBean.getActivityName(), javaCode);
				        }
			        
			        FragmentManager fragmentManager = getSupportFragmentManager();
ViewBuilderFragmentActivity activity = (ViewBuilderFragmentActivity) fragmentManager
    .findFragmentByTag("android:switcher:" + customViewPager.getId() + ":0");
			        if (ViewBuilderFragmentActivity.instance != null && ViewBuilderFragmentActivity.instance.viewEditor != null) {
				            ViewBuilderFragmentActivity.instance.saveLayout();
				        }
			        
			        if (!item.getXmlFileName().isEmpty()) {
				            ViewBuilderFragmentActivity.instance.layoutName = item.getXmlFileName();
				            ViewBuilderFragmentActivity.instance.viewEditor.tv_view_name.setText(item.getXmlName() + ".xml");
				            DesignActivity.currentActivityBean.setLayoutName(item.getXmlName());
				            defaultLayName = item.getXmlName();
				        }
			        
			        if (!item.getJavaName().isEmpty()) {
				            ViewBuilderFragmentActivity.instance.activityName = item.getJavaName();
				            EventFragmentActivity.myData = item.getJavaName();
				            DesignActivity.currentActivityBean.setActivityName(item.getJavaName());
				            defaultAcName = item.getJavaName();
				        }
			        
			        if (ViewBuilderFragmentActivity.instance != null) {
				            ViewBuilderFragmentActivity.instance.loadLayout();
				            ViewBuilderFragmentActivity.instance.u();
				        }
			        if (ComponentFragmentActivity.componentFragmentActivity != null) {
				            ComponentFragmentActivity.componentFragmentActivity.c();
				        }
			        
			        runOnUiThread(() -> {
				            if (file_spinner != null && file_spinner.getAdapter() != null) {
					                String currentSelection = file_spinner.getSelectedItem().toString(); 
					                
					                if (currentSelection != null) {
						                    setSpinnerSelection(file_spinner, currentSelection); 
						                }
					                
					                String targetItem = (tab_layout.getSelectedTabPosition() == 0)
					                    ? ViewBuilderFragmentActivity.instance.layoutName
					                    : ViewBuilderFragmentActivity.instance.activityName;
					                if (targetItem != null && !targetItem.isEmpty()) {
						                    setSpinnerSelection(file_spinner, targetItem);
						                }
					            }
				        });
			    };
		    
		    toggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
			        if (isChecked) {
				            if (checkedId == R.id.tabView) {
					                complex.setupViewAdapter(recyclerView, item -> onItemSelected.accept(item));
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
				        }
			    });
		    
		    complex.setupViewAdapter(recyclerView, item -> onItemSelected.accept(item));
		    
		    fab.setOnClickListener(v -> {
			        if (tabView.isChecked()) {
				            yq(false, null);
				        } else {
				            showCreateCustomViewDialog();
				        }
			    });
		    
		    dialog.setOnDismissListener(d -> {
			       
			    });
		    
		    dialog.show();
	}
	private void showCreateCustomViewDialog() {
		    Dialog dialog = new Dialog(this);
		    // Move this line BEFORE setContentView()
		    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		    dialog.setContentView(R.layout.create_custom_view);
		    
		    // Set transparent background
		    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		    dialog.getWindow().setDimAmount(0.5f);
		    
		    // Find views
		    EditText editText = dialog.findViewById(R.id.edittext3);
		    Button cancelButton = dialog.findViewById(R.id.button5);
		    Button addButton = dialog.findViewById(R.id.button6);
		    
		    // Make EditText focusable
		    editText.setFocusable(true);
		    editText.setFocusableInTouchMode(true);
		    
		    // Cancel button listener
		    cancelButton.setOnClickListener(v -> dialog.dismiss());
		    
		    // Add button listener
		    addButton.setOnClickListener(v -> {
			        String viewName = editText.getText().toString().trim();
			        if (viewName.isEmpty()) {
				            Toast.makeText(this, "Please enter a view name", Toast.LENGTH_SHORT).show();
				        } else if (!viewName.matches("^[a-zA-Z][a-zA-Z0-9]*$")) {
				            Toast.makeText(this, "View name must start with a letter and contain only letters and numbers", Toast.LENGTH_SHORT).show();
				        } else if (complex.hasCustomView(viewName)) {
				            Toast.makeText(this, "View name already exists", Toast.LENGTH_SHORT).show();
				        } else {
				            // Save the custom view name using Complex class
				            complex.setCustomViewName(viewName);
				            Toast.makeText(this, "Custom view '" + viewName + "' added", Toast.LENGTH_SHORT).show();
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
	private boolean hasUnsavedChanges() {
		    // Implement logic to detect if there are unsaved changes
		    // For now, always return true to force save
		    return true;
	}
	private boolean shouldAutoSave() {
		    // Add conditions here if you want to check if saving is needed
		    return true; // Always save for now
	}
	private Fragment getFragmentForPosition(int position) {
			switch (position) {
					case 0: return new ViewEditorFragmentActivity();
					case 1: return new EventFragmentActivity();
			        case 2: return new ComponentFragmentActivity();
					default: return new ViewEditorFragmentActivity();
			}
	}
	
	private void loadFragment(Fragment fragment) {
		    getSupportFragmentManager().beginTransaction()
		        .replace(R.id.customViewPager, fragment)
		        .commit();
	}
	private Fragment getCurrentLoadedFragment() {
		    return getSupportFragmentManager().findFragmentById(R.id.customViewPager);
	}
	public void startViewAutoLoader() {
		    Fragment current = getSupportFragmentManager().findFragmentById(R.id.customViewPager);
		
		    if (current instanceof ViewEditorFragmentActivity) {
			        if (isViewLoading) return; // agar pehle se chal raha hai toh return
			
			        isViewLoading = true;
			
			        dialogViewLoadRunnable = new Runnable() {
				            @Override
				            public void run() {
					               // ((ViewEditorFragmentActivity) current).loadView();
					               ((ViewEditorFragmentActivity) current)._load_view();
					                isViewLoading = false; // firse allow karne ke liye
					            }
				        };
			
			        dialogViewLoadHandler.postDelayed(dialogViewLoadRunnable, 500); // 1 sec delay se
			    }
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
			startActivity(intent);
	}
	private void startSpinnerUpdate() {
			if (spinnerUpdateHandler != null && spinnerUpdateRunnable != null) {
					spinnerUpdateHandler.removeCallbacks(spinnerUpdateRunnable); // Remove any existing callbacks
					spinnerUpdateHandler.post(spinnerUpdateRunnable); // Start new updates
			}
	}
	
	// Method to stop Spinner updates
	private void stopSpinnerUpdate() {
			if (spinnerUpdateHandler != null && spinnerUpdateRunnable != null) {
					spinnerUpdateHandler.removeCallbacks(spinnerUpdateRunnable);
			}
	}
	private void updateSpinnerContent() {
	}
	
	private class SaveProjectTask extends AsyncTask<Void, Void, Boolean> {
		    private String message; // Add this to store the result message
		    
		    @Override
		    protected void onPreExecute() {
			        super.onPreExecute();
			        showLoadingDialog();
			    }
		    
		    @Override
		    protected Boolean doInBackground(Void... voids) {
			        try {
				            boolean result = _save_view(); // Your existing save logic
				            message = result ? "Project Saved!" : "Save Failed!";
				            return result;
				        } catch (Exception e) {
				            message = "Save Error: " + e.getMessage();
				            return false;
				        }
			    }
		    
		    @Override
		    protected void onPostExecute(Boolean result) {
			        dismissLoadingDialog();
			      //  qr(message); // This will now run on UI thread
			    }
	}
	private void showLoadingDialog() {
		    loadingDialog = new Dialog(this);
		    loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		    loadingDialog.setCancelable(false);
		    loadingDialog.setContentView(R.layout.loading);
		    if (loadingDialog.getWindow() != null) {
			        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			        loadingDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
			    }
		    loadingDialog.show();
	}
	
	private void dismissLoadingDialog() {
		    if (loadingDialog != null && loadingDialog.isShowing()) {
			        loadingDialog.dismiss();
			    }
	}
	public static ProjectActivityBean.ViewBean createViewBean(View childAt) {
			ProjectActivityBean.ViewBean viewBean = new ProjectActivityBean.ViewBean();
			ViewGroup.LayoutParams params = childAt.getLayoutParams();
			
			viewBean.setWidgetType(childAt.getClass().getSimpleName());
			viewBean.setWidgetId(WidgetUtil.getWidgetId(childAt));
			viewBean.setWidth(params.width);
			viewBean.setHeight(params.height);
			viewBean.setTranslationX(childAt.getTranslationX());
			viewBean.setTranslationY(childAt.getTranslationY());
			
			if (childAt.getBackground() instanceof ColorDrawable) {
					viewBean.setBackgroundColor(((ColorDrawable) childAt.getBackground()).getColor());
			}
			
			if (params instanceof ViewGroup.MarginLayoutParams) {
					ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) params;
					viewBean.setMarginLeft(marginParams.leftMargin);
					viewBean.setMarginTop(marginParams.topMargin);
					viewBean.setMarginRight(marginParams.rightMargin);
					viewBean.setMarginBottom(marginParams.bottomMargin);
			}
			
			viewBean.setPaddingLeft(childAt.getPaddingLeft());
			viewBean.setPaddingTop(childAt.getPaddingTop());
			viewBean.setPaddingRight(childAt.getPaddingRight());
			viewBean.setPaddingBottom(childAt.getPaddingBottom());
			
			// Additional properties
			viewBean.setAlpha(childAt.getAlpha());
			viewBean.setVisibility(childAt.getVisibility()); // View.VISIBLE, INVISIBLE, GONE
			viewBean.setRotation(childAt.getRotation());
			viewBean.setScaleX(childAt.getScaleX());
			viewBean.setScaleY(childAt.getScaleY());
			viewBean.setElevation(childAt.getElevation());
			
			TextView textView = WidgetUtil.getTextViewOfWidget(childAt);
			if (textView != null) {
					viewBean.setText(textView.getText().toString());
					viewBean.setTextSize(textView.getTextSize());
					viewBean.setTextColor(textView.getCurrentTextColor());
					viewBean.setGravity(textView.getGravity());
					viewBean.setMaxLines(textView.getMaxLines());
			}
			
			if (childAt instanceof WidgetImageView) {
					String imagePath = WidgetUtil.getImagePath(childAt);
					if (imagePath != null) {
							viewBean.setImagePath(imagePath);
					}
					viewBean.setScaleType(((WidgetImageView) childAt).getScaleType().name());
			}
			
			if (childAt instanceof WidgetSwitch) {
					viewBean.setChecked(((WidgetSwitch) childAt).isCheckedDisplay());
			}
			
			if (childAt instanceof WidgetCheckBox) {
					viewBean.setChecked(((WidgetCheckBox) childAt).isCheckedDisplay());
			}
			
			
			// Add to createViewBean method, inside the method body, after existing widget-specific checks
			if (childAt instanceof WidgetProgressBar) {
					WidgetProgressBar progressBar = (WidgetProgressBar) childAt;
					viewBean.setProgress(progressBar.getProgressDisplay());
					viewBean.setMaxProgress(progressBar.getMaxProgress());
					viewBean.setProgressType(progressBar.getProgressType().name());
			}
			if (childAt instanceof WidgetSeekBar) {
					WidgetSeekBar seekBar = (WidgetSeekBar) childAt;
					viewBean.setProgress(seekBar.getProgressDisplay());
					viewBean.setMaxProgress(seekBar.getMaxProgress());
			}
			if (childAt instanceof WidgetRadioButton) {
					viewBean.setChecked(((WidgetRadioButton) childAt).isCheckedDisplay());
			}
			
			return viewBean;
	}
	
	public static void loadActivityFromJson(String filePath) {
		    /*
    try {
        String encodedJson = TheBlockLogicsUtil.readFile(filePath);
        String json = TheBlockLogicsUtil.decodeFromBase64(encodedJson);
        Gson gson = new Gson();
        currentActivityBean = gson.fromJson(json, ProjectActivityBean.class);
        ViewEditorFragmentActivity.activityName = currentActivityBean.getActivityName();
        ViewEditorFragmentActivity.layoutName = currentActivityBean.getLayoutName();
        ViewEditorFragmentActivity.pkgName = currentActivityBean.getPackageName();
        ViewEditorFragmentActivity.useAndroidX = currentActivityBean.isUseAndroidX();
        // Update UI or other components as needed
    } catch (Exception e) {
        TheBlockLogicsUtil.showToast(getApplicationContext(), "Load Error: " + e.toString());
    }
    */
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
	
	private JSONObject createWidgetJsonFromViewBean(ProjectActivityBean.ViewBean viewBean) throws JSONException {
		    JSONObject widgetObject = new JSONObject();
		    
		    widgetObject.put("name_s", viewBean.getWidgetType());
		    widgetObject.put("id", viewBean.getWidgetId());
		    widgetObject.put("width", viewBean.getWidth());
		    widgetObject.put("height", viewBean.getHeight());
		    widgetObject.put("translationX", viewBean.getTranslationX());
		    widgetObject.put("translationY", viewBean.getTranslationY());
		    widgetObject.put("background_color", viewBean.getBackgroundColor());
		    widgetObject.put("text", viewBean.getText());
		    widgetObject.put("text_size", viewBean.getTextSize());
		    widgetObject.put("text_color", viewBean.getTextColor());
		    widgetObject.put("image_path", viewBean.getImagePath());
		    widgetObject.put("margin_left", viewBean.getMarginLeft());
		    widgetObject.put("margin_top", viewBean.getMarginTop());
		    widgetObject.put("margin_right", viewBean.getMarginRight());
		    widgetObject.put("margin_bottom", viewBean.getMarginBottom());
		    widgetObject.put("padding_left", viewBean.getPaddingLeft());
		    widgetObject.put("padding_top", viewBean.getPaddingTop());
		    widgetObject.put("padding_right", viewBean.getPaddingRight());
		    widgetObject.put("padding_bottom", viewBean.getPaddingBottom());
		
		    return widgetObject;
	}
	public void loadWidgetsFromFile() {
		    try {
			        String filePath = String.format(ROOT_VIEW_PATH, sc_id);
			        if (FileUtil.isExistFile(filePath)) {
				            String json = FileUtil.readFile(filePath);
				            JSONObject wrapper = new JSONObject(json);
				            String activityName = wrapper.getString("activityName");
				            if (activityName.equals(ViewEditorFragmentActivity.activityName)) {
					                Gson gson = new Gson();
					                ProjectActivityBean.ViewBean[] loadedWidgets = gson.fromJson(
					                    wrapper.getJSONArray("widgets").toString(),
					                    ProjectActivityBean.ViewBean[].class
					                );
					                currentLayoutWidgets.clear();
					                currentLayoutWidgets.addAll(Arrays.asList(loadedWidgets));
					                if (currentActivityBean.getActivityName().equals(ViewEditorFragmentActivity.activityName)) {
						                    currentActivityBean.setWidgets(new ArrayList<>(currentLayoutWidgets));
						                }
					            } else {
					                currentLayoutWidgets.clear();
					                currentActivityBean.setWidgets(new ArrayList<>());
					            }
				        }
			    } catch (Exception e) {
			       // TheBlockLogicsUtil.showToast(getApplicationContext(), "Error loading widgets: " + e.toString());
			    }
	}
	public void switchActivityLayoutAsync(final String activityName, final String layoutName) {
		    new Handler(Looper.getMainLooper()).post(() -> {
			        ViewEditorFragmentActivity.activityName = activityName;
			        ViewEditorFragmentActivity.layoutName = layoutName;
			        
			        currentActivityBean.setActivityName(activityName);
			        currentActivityBean.setLayoutName(layoutName);
			
			        // Update UI
			        if (ViewEditorFragmentActivity.ll != null) {
				            ViewEditorFragmentActivity.ll.invalidate();
				        }
			
			        // Switch in fragment
			        Fragment fragment = getSupportFragmentManager().findFragmentByTag("view_editor");
			        if (fragment != null && fragment instanceof ViewEditorFragmentActivity && fragment.isAdded()) {
				            ((ViewEditorFragmentActivity) fragment).switchActivityLayout(activityName, layoutName,
				                layoutName.equalsIgnoreCase("main") || layoutName.equalsIgnoreCase("MainActivity"));
				        }
			
			        if (ViewEditorFragmentActivity.tv_view_name != null) {
				            ViewEditorFragmentActivity.tv_view_name.setText(layoutName + ".xml");
				        }
			
			        // Optional: refresh the view
			        ViewEditorFragmentActivity.refreshUI();
			    });
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
			        activityLogic.put(activityName + "initializeLogic", logic);
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
				            Map<String, String> activityLogic = logicMap.get(DesignActivity.currentActivityBean.getActivityName());
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
				            Map<String, String> activityLogic = logicMap.get(DesignActivity.currentActivityBean.getActivityName());
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
		    return complex.isToolbarEnabled(acName);
	}    
	public static boolean isEnableFab(String activityName) {
		    return complex.getEnableFabBoolean(activityName);
	}
	// Validate: No leading numbers, only a-z, 0-9, _
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
		    // Inflate the dialog layout
		    LayoutInflater inflater = LayoutInflater.from(this);
		    View dialogView = inflater.inflate(R.layout.create_activity_dialog, null);
		
		    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this)
		            .setView(dialogView)
		            .setCancelable(true);
		
		    AlertDialog dialog = dialogBuilder.create();
		
		    // Initialize UI elements
		    ImageView previewImage = dialogView.findViewById(R.id.preview_image);
		    EditText editTextName = dialogView.findViewById(R.id.edittext_name);
		    CheckBox checkBoxStatusBar = dialogView.findViewById(R.id.checkbox_status_bar);
		    CheckBox checkBoxToolbar = dialogView.findViewById(R.id.checkbox_toolbar);
		    CheckBox checkBoxDrawer = dialogView.findViewById(R.id.checkbox_drawer);
		    CheckBox checkBoxFab = dialogView.findViewById(R.id.checkbox_fab);
		    RadioGroup radioGroupType = dialogView.findViewById(R.id.radio_group_type);
		    RadioGroup radioGroupOrientation = dialogView.findViewById(R.id.radio_group_orientation);
		    Button buttonCancel = dialogView.findViewById(R.id.button_cancel);
		    Button buttonSave = dialogView.findViewById(R.id.button_save);
		
		    editTextName.setFocusable(true);
		    editTextName.setFocusableInTouchMode(true);
		    editTextName.setClickable(true);
		
		    // If in update mode, prefill the dialog with existing activity data
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
		
		    // Function to update the preview image based on checkbox states
		    View.OnClickListener updatePreviewListener = new View.OnClickListener() {
			        @Override
			        public void onClick(View v) {
				            boolean hasStatusBar = checkBoxStatusBar.isChecked();
				            boolean hasToolbar = checkBoxToolbar.isChecked();
				            boolean hasDrawer = checkBoxDrawer.isChecked();
				            boolean hasFab = checkBoxFab.isChecked();
				
				            // Logic for all 16 combinations
				            if (hasStatusBar && hasToolbar && hasDrawer && hasFab) {
					                previewImage.setImageResource(R.drawable.activity_1101); // All enabled
					            } else if (hasStatusBar && hasToolbar && hasDrawer && !hasFab) {
					                previewImage.setImageResource(R.drawable.activity_0101); // Image 1
					            } else if (hasStatusBar && hasToolbar && !hasDrawer && hasFab) {
					                previewImage.setImageResource(R.drawable.activity_1001); // Image 5
					            } else if (hasStatusBar && hasToolbar && !hasDrawer && !hasFab) {
					                previewImage.setImageResource(R.drawable.activity_0001); // Image 2
					            } else if (hasStatusBar && !hasToolbar && hasDrawer && hasFab) {
					                previewImage.setImageResource(R.drawable.activity_1100); // Placeholder
					            } else if (hasStatusBar && !hasToolbar && hasDrawer && !hasFab) {
					                previewImage.setImageResource(R.drawable.activity_0100); // Image 4
					            } else if (hasStatusBar && !hasToolbar && !hasDrawer && hasFab) {
					                previewImage.setImageResource(R.drawable.activity_1000); // Placeholder
					            } else if (hasStatusBar && !hasToolbar && !hasDrawer && !hasFab) {
					                previewImage.setImageResource(R.drawable.activity_0000); // Placeholder
					            } else if (!hasStatusBar && hasToolbar && hasDrawer && hasFab) {
					                previewImage.setImageResource(R.drawable.activity_1111); // Placeholder
					            } else if (!hasStatusBar && hasToolbar && hasDrawer && !hasFab) {
					                previewImage.setImageResource(R.drawable.activity_1011); // Placeholder
					            } else if (!hasStatusBar && hasToolbar && !hasDrawer && hasFab) {
					                previewImage.setImageResource(R.drawable.activity_0111); // Placeholder
					            } else if (!hasStatusBar && hasToolbar && !hasDrawer && !hasFab) {
					                previewImage.setImageResource(R.drawable.activity_preset_1); // Placeholder
					            } else if (!hasStatusBar && !hasToolbar && hasDrawer && hasFab) {
					                previewImage.setImageResource(R.drawable.activity_1110); // Placeholder
					            } else if (!hasStatusBar && !hasToolbar && hasDrawer && !hasFab) {
					                previewImage.setImageResource(R.drawable.activity_0110); // Placeholder
					            } else if (!hasStatusBar && !hasToolbar && !hasDrawer && hasFab) {
					                previewImage.setImageResource(R.drawable.activity_1010); // Image 3
					            } else {
					                previewImage.setImageResource(R.drawable.activity_0010); // No components (Image 6)
					            }
				        }
			    };
		
		    // Set listeners for all checkboxes to update the preview
		    checkBoxStatusBar.setOnClickListener(updatePreviewListener);
		    checkBoxToolbar.setOnClickListener(updatePreviewListener);
		    checkBoxDrawer.setOnClickListener(updatePreviewListener);
		    checkBoxFab.setOnClickListener(updatePreviewListener);
		
		    // Set initial preview image
		    updatePreviewListener.onClick(null);
		
		    // Cancel button click
		    buttonCancel.setOnClickListener(new View.OnClickListener() {
			        @Override
			        public void onClick(View v) {
				            dialog.dismiss();
				        }
			    });
		
		    // Save button click
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
						                    // Update existing activity
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
						                    // Create new activity
						                    complex.setAcName(formattedName);
						                    complex.setXName(xmlName);
						                    //complex.addActivity(formattedName, xmlName, hasFab, false, hasDrawer);
						                    complex.addActivityToManifest(formattedName);
						                    complex.enableFab(formattedName, hasFab);
						                    complex.enableToolBar(formattedName, complex.getAndroidXEnable(), hasToolbar);
						
						                    // Set default XML layout
						                    String defaultXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
						                            "<androidx.constraintlayout.widget.ConstraintLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
						                            "    android:layout_width=\"match_parent\"\n" +
						                            "    android:layout_height=\"match_parent\">\n\n" +
						                            (hasFab ? "    <com.google.android.material.floatingactionbutton.FloatingActionButton\n" +
						                            "        android:id=\"@+id/fab\"\n" +
						                            "        android:layout_width=\"wrap_content\"\n" +
						                            "        android:layout_height=\"wrap_content\"\n" +
						                            "        android:layout_margin=\"16dp\"\n" +
						                            "        app:layout_constraintEnd_toEndOf=\"parent\"\n" +
						                            "        app:layout_constraintBottom_toBottomOf=\"parent\"\n" +
						                            "        android:src=\"@android:drawable/ic_input_add\" />\n" : "") +
						                            "</androidx.constraintlayout.widget.ConstraintLayout>";
						                    complex.setXmlCode(xmlName, "");
						
						                    Toast.makeText(getApplicationContext(), "Activity created: " + formattedName, Toast.LENGTH_SHORT).show();
						                }
					
					                // Log the data
					                System.out.println("Name: " + formattedName + ", XML: " + xmlName + ", Type: " + type +
					                        ", Orientation: " + orientation + ", StatusBar: " + hasStatusBar +
					                        ", Toolbar: " + hasToolbar + ", Drawer: " + hasDrawer + ", FAB: " + hasFab);
					
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
	public void updateWidgetInLayout(ProjectActivityBean.ViewBean viewBean) {
		    if (viewBean == null || viewBean.getWidgetId() == null) {
			        TheBlockLogicsUtil.showToast(getApplicationContext(), "Invalid widget data");
			        return;
			    }
		
		    if (currentActivityBean == null || currentActivityBean.getActivityName() == null) {
			        TheBlockLogicsUtil.showToast(getApplicationContext(), "No activity selected");
			        return;
			    }
		
		    // Get the list of widgets for the current activity
		    List<ProjectActivityBean.ViewBean> widgetBeans = allWidgetsMap.get(currentActivityBean.getActivityName());
		    if (widgetBeans == null) {
			        widgetBeans = new ArrayList<>();
			        allWidgetsMap.put(currentActivityBean.getActivityName(), widgetBeans);
			    }
		
		    // Update or add the ViewBean in widgetBeans
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
			        widgetBeans.add(viewBean);
			    }
		
		    // Update the actual View in currentLayoutWidgets
		    boolean viewUpdated = false;
		    for (int i = 0; i < ViewEditorFragmentActivity.ll.getChildCount(); i++) {
			        View childAt = ((ViewGroup) ViewEditorFragmentActivity.ll.getChildAt(i)).getChildAt(0);
			        String widgetId = WidgetUtil.getWidgetId(childAt);
			        if (widgetId.equals(viewBean.getWidgetId())) {
				            // Apply properties from ViewBean to View
				            childAt.setBackgroundColor(viewBean.getBackgroundColor());
				
				            // Apply margins
				            ViewGroup.LayoutParams params = childAt.getLayoutParams();
				            if (params instanceof ViewGroup.MarginLayoutParams) {
					                ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) params;
					                marginParams.setMargins(
					                    viewBean.getMarginLeft(),
					                    viewBean.getMarginTop(),
					                    viewBean.getMarginRight(),
					                    viewBean.getMarginBottom()
					                );
					                childAt.setLayoutParams(marginParams);
					            }
				
				            // Apply padding
				            childAt.setPadding(
				                viewBean.getPaddingLeft(),
				                viewBean.getPaddingTop(),
				                viewBean.getPaddingRight(),
				                viewBean.getPaddingBottom()
				            );
				
				            // Apply text properties if applicable
				            if (WidgetUtil.getTextViewOfWidget(childAt) != null) {
					                TextView textView = WidgetUtil.getTextViewOfWidget(childAt);
					                textView.setText(viewBean.getText());
					                textView.setTextSize(viewBean.getTextSize());
					                textView.setTextColor(viewBean.getTextColor());
					            }
				
				            // Apply image path if applicable
				            if (childAt instanceof WidgetImageView && viewBean.getImagePath() != null) {
					                WidgetImageView imageView = (WidgetImageView) childAt;
					                // Note: Actual image loading logic depends on WidgetUtil.getImagePath implementation
					                // This is a placeholder; adjust based on your image loading mechanism
					                imageView.setImageDrawable(WidgetUtil.loadImageFromPath(viewBean.getImagePath()));
					            }
				
				            // Apply CheckBox/Switch state
				            if (childAt instanceof WidgetCheckBox) {
					                ((WidgetCheckBox) childAt).setCheckedDisplay(viewBean.isChecked());
					            }
				            if (childAt instanceof WidgetSwitch) {
					                ((WidgetSwitch) childAt).setCheckedDisplay(viewBean.isChecked());
					            }
				
				            childAt.requestLayout();
				            ViewEditorFragmentActivity.ll.requestLayout();
				            ViewEditorFragmentActivity.ll.invalidate();
				            viewUpdated = true;
				            break;
				        }
			    }
		
		    if (!viewUpdated) {
			        TheBlockLogicsUtil.showToast(getApplicationContext(), "Widget not found in layout");
			    }
		
		    // Update currentActivityBean and allWidgetsMap
		    currentActivityBean.setWidgets(widgetBeans);
		    allWidgetsMap.put(currentActivityBean.getActivityName(), widgetBeans);
		
		    // Save to JSON
		    _save_view();
		
		    // Save state for undo/redo (assuming ViewEditorFragmentActivity has this method)
		    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.customViewPager);
		    if (fragment != null && fragment instanceof ViewEditorFragmentActivity && fragment.isAdded()) {
			        ((ViewEditorFragmentActivity) fragment).saveStateToUndo();
			    }
	}
	public void updateWidgetTextAlignment(ProjectActivityBean.ViewBean viewBean, int gravity) {
		    if (viewBean == null || viewBean.getWidgetId() == null) {
			        TheBlockLogicsUtil.showToast(getApplicationContext(), "Invalid widget data");
			        return;
			    }
		
		    if (currentActivityBean == null || currentActivityBean.getActivityName() == null) {
			        TheBlockLogicsUtil.showToast(getApplicationContext(), "No activity selected");
			        return;
			    }
		
		    // Update ViewBean gravity
		    viewBean.setGravity(gravity);
		
		    // Get the list of widgets for the current activity
		    List<ProjectActivityBean.ViewBean> widgetBeans = allWidgetsMap.get(currentActivityBean.getActivityName());
		    if (widgetBeans == null) {
			        widgetBeans = new ArrayList<>();
			        allWidgetsMap.put(currentActivityBean.getActivityName(), widgetBeans);
			    }
		
		    // Update or add the ViewBean in widgetBeans
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
			        widgetBeans.add(viewBean);
			    }
		
		    // Update the actual View in currentLayoutWidgets
		    boolean viewUpdated = false;
		    for (int i = 0; i < ViewEditorFragmentActivity.ll.getChildCount(); i++) {
			        View childAt = ((ViewGroup) ViewEditorFragmentActivity.ll.getChildAt(i)).getChildAt(0);
			        String widgetId = WidgetUtil.getWidgetId(childAt);
			        if (widgetId.equals(viewBean.getWidgetId()) && WidgetUtil.getTextViewOfWidget(childAt) != null) {
				            TextView textView = WidgetUtil.getTextViewOfWidget(childAt);
				            textView.setGravity(gravity);
				
				            childAt.requestLayout();
				            ViewEditorFragmentActivity.ll.requestLayout();
				            ViewEditorFragmentActivity.ll.invalidate();
				            viewUpdated = true;
				            break;
				        }
			    }
		
		    if (!viewUpdated) {
			        TheBlockLogicsUtil.showToast(getApplicationContext(), "Widget does not support text alignment");
			    }
		
		    // Update currentActivityBean and allWidgetsMap
		    currentActivityBean.setWidgets(widgetBeans);
		    allWidgetsMap.put(currentActivityBean.getActivityName(), widgetBeans);
		
		    // Save to JSON
		    _save_view();
		
		    // Save state for undo/redo
		    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.customViewPager);
		    if (fragment != null && fragment instanceof ViewEditorFragmentActivity && fragment.isAdded()) {
			        ((ViewEditorFragmentActivity) fragment).saveStateToUndo();
			    }
	}
	private void loadViewBeansFromJson() {
		    try {
			        File file = new File(ViewEditorFragmentActivity.projectPath, "layout.json");
			        if (!file.exists()) {
				           // showToast("No saved layout found");
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
					                //showToast("Widgets loaded for " + currentActivity.getActivityName());
					            } else {
					                //showToast("No saved widgets for this activity");
					            }
				        } else {
				           // showToast("No activity selected");
				        }
			
			    } catch (Exception e) {
			       // showToast("Error loading data: " + e.getMessage());
			        e.printStackTrace();
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
									((Widget) widget).setWidgetId(widgetId);
							    	widget.setOnClickListener(new WidgetClickListener());
								//	widget.setOnLongClickListener(this);
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
	{
	}
	
	
	public void _processApk() {
		if (FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat("/.blacklogics/mysc/".concat(sc_id.concat("/bin"))))) {
			FileUtil.deleteFile(FileUtil.getExternalStorageDir().concat("/.blacklogics/mysc/".concat(sc_id.concat("/bin"))));
		}
		if (FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat("/.blacklogics/mysc/".concat(sc_id.concat("/gen"))))) {
			FileUtil.deleteFile(FileUtil.getExternalStorageDir().concat("/.blacklogics/mysc/".concat(sc_id.concat("/gen"))));
		}
		if (FileUtil.isExistFile(FileUtil.getExternalStorageDir() + "/.blacklogics/mysc/" + sc_id + "/")) {
			FileUtil.deleteFile(FileUtil.getExternalStorageDir() + "/.blacklogics/mysc/" + sc_id + "/");
		}
		complex = new Complex();
		complex.setId(sc_id);
		complex.setPkgName(pkgName);
		ProjectBuilder builder = new ProjectBuilder(complex, Environment.getExternalStorageDirectory().getAbsolutePath(), sc_id);
		builder.setContext(this);
		builder.buildProject();
		SystemLogPrinter.start(mLogger);
		generateJavaCode();
		// Assuming this is in DesignActivity.java or similar
		Project project = new Project();
		String dexer = settings.getValue(BuildSettings.SETTING_DEXER, BuildSettings.SETTING_DEXER_DX); // default DX
		
		if (BuildSettings.SETTING_DEXER_R8.equals(dexer)) {
				project.getBuildSettings().setDexCompilerType(DexCompilerType.R8);
		} else if (BuildSettings.SETTING_DEXER_D8.equals(dexer)) {
				project.getBuildSettings().setDexCompilerType(DexCompilerType.D8);
		}
		// Set libraries
		project.setLibraries(Library.fromFile(new File(FileUtil.getExternalStorageDir() + "/.blacklogics/mysc/" + sc_id + "/app/libs/")));
		
		// Set project name
		project.setProjectName(scName);
		
		// Set ProGuard file
		
		project.setProguardFile(new File(FileUtil.getExternalStorageDir() + "/.blacklogics/mysc/" + sc_id + "/app/proguard-rules.pro"));
		
		List<File> resourceDirs = new ArrayList<>();
		resourceDirs.add(new File(FileUtil.getExternalStorageDir() + "/.blacklogics/data/" + sc_id + "/files/resource/"));
		resourceDirs.add(new File(FileUtil.getExternalStorageDir() + "/.blacklogics/mysc/" + sc_id + "/app/src/main/res/"));
		project.setResourcesFiles(resourceDirs);
		
		// Set output file
		project.setOutputFile(new File(FileUtil.getExternalStorageDir() + "/.blacklogics/mysc/" + sc_id + "/"));
		
		
		FileUtil.deleteFile(new File(project.getOutputFile(), "bin").getAbsolutePath());
		FileUtil.deleteFile(new File(project.getOutputFile(), "gen").getAbsolutePath());
		
		// Set Java files (support multiple directories if needed)
		
		List<File> javaFiles = new ArrayList<>();
		javaFiles.add(new File(FileUtil.getExternalStorageDir() + "/.blacklogics/mysc/" + sc_id + "/app/src/main/java/" + pkgName.replace(".", "/") + "/"));
		javaFiles.add(new File(FileUtil.getExternalStorageDir() + "/.blacklogics/data/" + sc_id + "/files/java/"));
		project.setJavaFiles(javaFiles);
		
		// Set manifest file
		project.setManifestFile(new File(FileUtil.getExternalStorageDir() + "/.blacklogics/mysc/" + sc_id + "/app/src/main/AndroidManifest.xml"));
		
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
		FragmentManager fragmentManager = getSupportFragmentManager();
ViewBuilderFragmentActivity activity = (ViewBuilderFragmentActivity) fragmentManager
    .findFragmentByTag("android:switcher:" + customViewPager.getId() + ":0");
		if (ViewBuilderFragmentActivity.instance != null && ViewBuilderFragmentActivity.instance.viewEditor != null) {
			    ViewBuilderFragmentActivity.instance.saveLayout();
		}
		return true;
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