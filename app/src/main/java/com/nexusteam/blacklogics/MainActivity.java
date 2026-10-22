package com.nexusteam.blacklogics;

import com.nexusteam.blacklogics.SplashActivity;
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
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.solver.*;
import androidx.constraintlayout.widget.*;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.*;
import androidx.core.splashscreen.*;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import com.airbnb.viewmodeladapter.*;
import com.bumptech.glide.*;
import com.bumptech.glide.gifdecoder.*;
import com.example.myapp.*;
import com.github.angads25.filepicker.*;
import com.google.android.flexbox.*;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.*;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.*;
import com.googlecode.d2j.*;
import com.larswerkman.holocolorpicker.*;
import com.nexusteam.blacklogics.widget.AdLayout;
import com.nexusteam.filepicker.elitefileselector.*;
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
import java.util.HashMap;
import java.util.regex.*;
import org.antlr.v4.runtime.*;
import org.benf.cfr.reader.*;
import org.json.*;
import com.nexusteam.blacklogics.manager.ProjectSettingsManager;
import com.nexusteam.blacklogics.ui.ProjectSettingsBuilder;
import com.nexusteam.blacklogics.dialogfragment.CreateProjectBottomSheet;
import com.nexusteam.blacklogics.adapter.ProjectAdapter;
import com.nexusteam.blacklogics.model.*;
import com.nexusteam.blacklogics.manager.BackupManager;
import com.nexusteam.blacklogics.util.FileHandler;
import com.nexusteam.blacklogics.beans.ProjectActivityBean;
import com.nexusteam.blacklogics.sdk.util.BlockWrite;
import com.nexusteam.blacklogics.ui.CrashLogUpdateNotice;
import com.nexusteam.filepicker.elitefileselector.EliteFilePicker;
import com.nexusteam.blacklogics.activities.project.CreateProjectActivity;
// Android core
import android.Manifest;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.view.inputmethod.InputMethodManager;

// AndroidX
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;

// Material Components
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.nexusteam.blacklogics.activities.AboutTeamActivity;

public class MainActivity extends com.nexusteam.blacklogics.lib.base.LocaleBaseActivity {
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private FloatingActionButton _fab;
	private DrawerLayout _drawer;
	private boolean isSearchVisible = false;
	public ProjectAdapter adapter;
	public ProjectActivityBean bean;
	public BackupManager backUpRestore;
	private BlockWrite blockWrite;
	private HashMap<String, Object> e = new HashMap<>();
	private static final int REQ_CREATE_PROJECT = 3001;
	
	private MaterialToolbar baseTopLayout;
	private SwipeRefreshLayout swipeRefresh;
	private ImageView baseMenu;
	private ImageView baseTop;
	private ImageView baseSearch;
	private NestedScrollView baseDesign;
	private LinearLayout uiUx;
	private LinearLayout searchBarLayout;
	private MaterialCardView linear1;
	private AdView adview1;
	private CircleImageView circleimageview2;
	private ShapeableImageView circleimageview1;
	private RecyclerView baseRecycler;
	private AdLayout adLayout;
	private SearchView searchBar;
	private LinearLayout linear3;
	private ShapeableImageView imageview1;
	private MaterialTextView textview1;
	private LinearLayout _drawer_linear_header;
	private ScrollView _drawer_vscroll1;
	private View _drawer_view20;
	private LinearLayout _drawer_linear21;
	private CircleImageView _drawer_profileImage;
	private TextView _drawer_names;
	private TextView _drawer_userEmail;
	private LinearLayout _drawer_linear11;
	private MaterialCardView _drawer_about;
	private MaterialCardView _drawer_Changelog;
	private MaterialCardView _drawer_Setting;
	private MaterialCardView _drawer_Programs;
	private MaterialCardView _drawer_developer;
	private MaterialCardView _drawer_Create;
	private MaterialCardView _drawer_webtoapktool;
	private MaterialCardView _drawer_cardview3;
	private MaterialCardView _drawer_donate;
	private LinearLayout _drawer_linear12;
	private ImageView _drawer_imageview10;
	private TextView _drawer_textview18;
	private LinearLayout _drawer_linear13;
	private ImageView _drawer_imageview6;
	private TextView _drawer_textview13;
	private LinearLayout _drawer_linear14;
	private ImageView _drawer_imageview12;
	private TextView _drawer_textview21;
	private LinearLayout _drawer_linear15;
	private ImageView _drawer_imageview14;
	private TextView _drawer_textview23;
	private LinearLayout _drawer_linear16;
	private ImageView _drawer_imageview15;
	private TextView _drawer_textview24;
	private LinearLayout _drawer_linear17;
	private ImageView _drawer_imageview16;
	private TextView _drawer_textview25;
	private LinearLayout _drawer_linear18;
	private ImageView _drawer_imagevieejw16;
	private TextView _drawer_textviesw25;
	private LinearLayout _drawer_javaToSmali;
	private ImageView _drawer_imageview19;
	private TextView _drawer_textview28;
	private LinearLayout _drawer_linear19;
	private ImageView _drawer_imageview18;
	private TextView _drawer_textview27;
	private MaterialCardView _drawer_facebook;
	private MaterialCardView _drawer_Program;
	private MaterialCardView _drawer_youtube;
	private ImageView _drawer_imageview4;
	private ImageView _drawer_imageview5;
	private ImageView _drawer_imageview7;
	
	private Intent i = new Intent();
	private Intent a = new Intent();
	private Intent ip = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		
		MobileAds.initialize(this);
		
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
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
		_fab = findViewById(R.id._fab);
		_drawer = findViewById(R.id._drawer);
		ActionBarDrawerToggle _toggle = new ActionBarDrawerToggle(MainActivity.this, _drawer, _toolbar, R.string.app_name, R.string.app_name);
		_drawer.addDrawerListener(_toggle);
		_toggle.syncState();
		
		LinearLayout _nav_view = findViewById(R.id._nav_view);
		
		baseTopLayout = findViewById(R.id.baseTopLayout);
		swipeRefresh = findViewById(R.id.swipeRefresh);
		baseMenu = findViewById(R.id.baseMenu);
		baseTop = findViewById(R.id.baseTop);
		baseSearch = findViewById(R.id.baseSearch);
		baseDesign = findViewById(R.id.baseDesign);
		uiUx = findViewById(R.id.uiUx);
		searchBarLayout = findViewById(R.id.searchBarLayout);
		linear1 = findViewById(R.id.linear1);
		adview1 = findViewById(R.id.adview1);
		circleimageview2 = findViewById(R.id.circleimageview2);
		circleimageview1 = findViewById(R.id.circleimageview1);
		baseRecycler = findViewById(R.id.baseRecycler);
		adLayout = findViewById(R.id.adLayout);
		searchBar = findViewById(R.id.searchBar);
		linear3 = findViewById(R.id.linear3);
		imageview1 = findViewById(R.id.imageview1);
		textview1 = findViewById(R.id.textview1);
		_drawer_linear_header = _nav_view.findViewById(R.id.linear_header);
		_drawer_vscroll1 = _nav_view.findViewById(R.id.vscroll1);
		_drawer_view20 = _nav_view.findViewById(R.id.view20);
		_drawer_linear21 = _nav_view.findViewById(R.id.linear21);
		_drawer_profileImage = _nav_view.findViewById(R.id.profileImage);
		_drawer_names = _nav_view.findViewById(R.id.names);
		_drawer_userEmail = _nav_view.findViewById(R.id.userEmail);
		_drawer_linear11 = _nav_view.findViewById(R.id.linear11);
		_drawer_about = _nav_view.findViewById(R.id.about);
		_drawer_Changelog = _nav_view.findViewById(R.id.Changelog);
		_drawer_Setting = _nav_view.findViewById(R.id.Setting);
		_drawer_Programs = _nav_view.findViewById(R.id.Programs);
		_drawer_developer = _nav_view.findViewById(R.id.developer);
		_drawer_Create = _nav_view.findViewById(R.id.Create);
		_drawer_webtoapktool = _nav_view.findViewById(R.id.webtoapktool);
		_drawer_cardview3 = _nav_view.findViewById(R.id.cardview3);
		_drawer_donate = _nav_view.findViewById(R.id.donate);
		_drawer_linear12 = _nav_view.findViewById(R.id.linear12);
		_drawer_imageview10 = _nav_view.findViewById(R.id.imageview10);
		_drawer_textview18 = _nav_view.findViewById(R.id.textview18);
		_drawer_linear13 = _nav_view.findViewById(R.id.linear13);
		_drawer_imageview6 = _nav_view.findViewById(R.id.imageview6);
		_drawer_textview13 = _nav_view.findViewById(R.id.textview13);
		_drawer_linear14 = _nav_view.findViewById(R.id.linear14);
		_drawer_imageview12 = _nav_view.findViewById(R.id.imageview12);
		_drawer_textview21 = _nav_view.findViewById(R.id.textview21);
		_drawer_linear15 = _nav_view.findViewById(R.id.linear15);
		_drawer_imageview14 = _nav_view.findViewById(R.id.imageview14);
		_drawer_textview23 = _nav_view.findViewById(R.id.textview23);
		_drawer_linear16 = _nav_view.findViewById(R.id.linear16);
		_drawer_imageview15 = _nav_view.findViewById(R.id.imageview15);
		_drawer_textview24 = _nav_view.findViewById(R.id.textview24);
		_drawer_linear17 = _nav_view.findViewById(R.id.linear17);
		_drawer_imageview16 = _nav_view.findViewById(R.id.imageview16);
		_drawer_textview25 = _nav_view.findViewById(R.id.textview25);
		_drawer_linear18 = _nav_view.findViewById(R.id.linear18);
		_drawer_imagevieejw16 = _nav_view.findViewById(R.id.imagevieejw16);
		_drawer_textviesw25 = _nav_view.findViewById(R.id.textviesw25);
		_drawer_javaToSmali = _nav_view.findViewById(R.id.javaToSmali);
		_drawer_imageview19 = _nav_view.findViewById(R.id.imageview19);
		_drawer_textview28 = _nav_view.findViewById(R.id.textview28);
		_drawer_linear19 = _nav_view.findViewById(R.id.linear19);
		_drawer_imageview18 = _nav_view.findViewById(R.id.imageview18);
		_drawer_textview27 = _nav_view.findViewById(R.id.textview27);
		_drawer_facebook = _nav_view.findViewById(R.id.facebook);
		_drawer_Program = _nav_view.findViewById(R.id.Program);
		_drawer_youtube = _nav_view.findViewById(R.id.youtube);
		_drawer_imageview4 = _nav_view.findViewById(R.id.imageview4);
		_drawer_imageview5 = _nav_view.findViewById(R.id.imageview5);
		_drawer_imageview7 = _nav_view.findViewById(R.id.imageview7);
		
		baseMenu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_drawer.openDrawer(GravityCompat.START);
			}
		});
		
		linear1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				backUpRestore.importFromSketchware();
			}
		});
		
		adview1.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				
			}
			
			@Override
			public void onAdFailedToLoad(LoadAdError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
			
			@Override
			public void onAdOpened() {
				
			}
			
			@Override
			public void onAdClicked() {
				
			}
			
			@Override
			public void onAdClosed() {
				
			}
		});
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				com.nexusteam.blacklogics.activities.project.CreateProjectActivity.launch(MainActivity.this, false, null, REQ_CREATE_PROJECT);
			}
		});
		
		_drawer_about.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Intent intent = new Intent(MainActivity.this, AboutTeamActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
			}
		});
		
		_drawer_Changelog.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Intent aboutModIntent = new Intent();
				aboutModIntent.setClass(MainActivity.this, AboutTeamActivity.class);
				aboutModIntent.putExtra("select", "changelog");
				startActivity(aboutModIntent);
				
			}
		});
		
		_drawer_Setting.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		_drawer_Programs.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Intent intent = new Intent(getApplicationContext(), ProgramInformationActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
			}
		});
		
		_drawer_developer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Intent intent = new Intent(getApplicationContext(), AppSettingsActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
			}
		});
		
		_drawer_Create.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Intent intent = new Intent(getApplicationContext(), NewKeyStoreActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
			}
		});
		
		_drawer_webtoapktool.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Intent intent = new Intent(getApplicationContext(), WebToApkActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
			}
		});
		
		_drawer_javaToSmali.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Intent intent = new Intent(getApplicationContext(), JavaToSmaliActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
			}
		});
		
		_drawer_linear19.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				ip.setClass(getApplicationContext(), DonateActivity.class);
				startActivity(ip);
			}
		});
	}
	
	private void initializeLogic() {
		_create_paths();
		blockWrite = new BlockWrite(this, new BlockWrite.BlockWriteCallback() {
			@Override
			public void onSuccess(String message) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						//	Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
					}
				});
			}
			
			@Override
			public void onError(String error) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						//	Toast.makeText(MainActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
					}
				});
			}
		});
		
		final LinearLayout _nav_view = (LinearLayout) findViewById(R.id._nav_view); _nav_view.setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));
		backUpRestore = new BackupManager(this);
		backUpRestore.setOnImportListener(new BackupManager.OnImportListener() {
			@Override
			public void onImportSuccess(String newScId, String projectName) {
				// Project list refresh karo
				refreshProjects();
			}
			@Override
			public void onImportError(String reason) {
				showMessage("Import failed: " + reason);
			}
		});
		baseRecycler.setLayoutManager(new LinearLayoutManager(this));
		
		// Create and set adapter
		adapter = new ProjectAdapter(this);
		baseRecycler.setAdapter(adapter);
		// Initialize RecyclerView
		adapter.setOnItemClickListener(new ProjectAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(int position, ProjectAdapter.ProjectItem project) {
				// Handle item click
				com.nexusteam.blacklogics.model.DesignDataManager.setJavaName("MainActivity");
				// DesignActivity.currentActivityBean.setLayoutName("main");
				//DesignActivity.currentActivityBean.setActivityName("MainActivity");
				Intent intent = new Intent(MainActivity.this, DesignActivity.class);
				intent.putExtra("projectPath", project.projectDir.getAbsolutePath());
				intent.putExtra("sc_id", String.valueOf(project.projectId));
				intent.putExtra("pkgName", project.packageName);
				intent.putExtra("varName", project.versionName);
				intent.putExtra("varCode", project.versionCode);
				intent.putExtra("scName", project.projectName);
				startActivity(intent);
			}
			
			@Override
			public void onItemLongClick(int position, ProjectAdapter.ProjectItem project) {
				// Handle long click (e.g., show delete dialog)
				showDeleteDialog(position, project);
			}
			
			@Override
			public void onEditClick(int position, ProjectAdapter.ProjectItem project) {
				com.nexusteam.blacklogics.activities.project.CreateProjectActivity.launch(MainActivity.this, true, project.projectDir, REQ_CREATE_PROJECT);
			}
			
			@Override
			public void onBackupClick(int position, ProjectAdapter.ProjectItem project) {
				// Handle backup click
				backUpRestore.backup(String.valueOf(project.projectId), project.projectName);
			}
			
			@Override
			public void onDeleteClick(int position, ProjectAdapter.ProjectItem project) {
				showDeleteDialog(position, project);
			} 
			@Override
			public void onExportClick(int position, ProjectAdapter.ProjectItem project) {
				Intent intent = new Intent(MainActivity.this, ExportProjectActivity.class);
				intent.putExtra("sc_id", String.valueOf(project.projectId));
				startActivity(intent);
			}     
			
			@Override
			public void onConfigClick(int position, ProjectAdapter.ProjectItem project) {
				ProjectSettingsManager settings = new ProjectSettingsManager(String.valueOf(project.projectId));
				settings.load(MainActivity.this); 
				
				ProjectSettingsBuilder dialog = new ProjectSettingsBuilder(MainActivity.this, settings);
				dialog.show();
				
			}     
		});
		
		swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				refreshProjects();
			}
		});
		
		CrashLogUpdateNotice.showIfNeeded(this);
		baseSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_toggleSearchBar();
			}
		});
		searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				filterProjects(query);
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				filterProjects(newText);
				return false;
			}
		});
		
		// Close search when back button is pressed
		searchBar.setOnCloseListener(new SearchView.OnCloseListener() {
			@Override
			public boolean onClose() {
				if (isSearchVisible) {
					_toggleSearchBar();
					return true;
				}
				return false;
			}
		});
		adLayout.loadBannerAd();
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		EliteFilePicker.getInstance().handleResult(_requestCode, _resultCode, _data);
		if (backUpRestore != null) {
			backUpRestore.onActivityResult(_requestCode, _resultCode, _data);
		}
		
		if (_requestCode == REQ_CREATE_PROJECT) {
			if (_resultCode == CreateProjectActivity.RESULT_CREATED) {
				// Project created/updated successfully
				refreshProjects();
			}
			return;
		}
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	
	@Override
	public void onBackPressed() {
		finishAffinity();
	}
	
	@Override
	public void onStart() {
		super.onStart();
		blockWrite.writeMathBlocks();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		adLayout.setOnAdLoadedListener(new AdLayout.OnAdLoadedListener() {
			@Override
			public void onLoaded() {
				// Ad successfully load ho gayi
				Log.d("MainActivity", "Ad loaded successfully!");
				Toast.makeText(MainActivity.this, "Ad Loaded!", Toast.LENGTH_SHORT).show();
				
				// Agar chahe to yahan banner ko visible karo
				adLayout.setVisibility(View.VISIBLE);
			}
		});
		
		adLayout.setOnAdFailedListener(new AdLayout.OnAdFailedListener() {
			@Override
			public void onFailed(String error) {
				// Ad load karne mein error aayi
				Log.e("MainActivity", "Ad failed to load: " + error);
				Toast.makeText(MainActivity.this, "Ad Failed: " + error, Toast.LENGTH_SHORT).show();
				
				// Error par banner ko hide kar do
				adLayout.setVisibility(View.GONE);
			}
		});
		adLayout.loadBannerAd();
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (adview1 != null) {
			adview1.destroy();
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if (adview1 != null) {
			adview1.pause();
		}
	}
	public void _b() {
	}
	private void showDeleteDialog(final int position, final ProjectAdapter.ProjectItem project) {
		new MaterialAlertDialogBuilder(this)
		.setTitle("Delete Project")
		.setMessage(
		"Are you sure you want to delete \"" 
		+ project.projectName 
		+ "\"? This action cannot be undone."
		)
		.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String scId = String.valueOf(project.projectId);
				
				boolean deleted = deleteProjectDirectory(project.projectDir);
				deleteExtraResources(scId);
				
				if (deleted) {
					ProjectAdapter adapter =
					(ProjectAdapter) baseRecycler.getAdapter();
					
					if (adapter != null) {
						adapter.removeProject(position);
					}
					showMessage("Project deleted successfully");
				} else {
					showMessage("Failed to delete project");
				}
			}
		})
		.setNegativeButton("Cancel", null)
		.show();
	}
	
	private void deleteExtraResources(String scId) {
		String base = "/storage/emulated/0/.blacklogics/";
		
		// resources/<type>/<sc_id> -> folders
		String[] resourceTypes = { "images", "icon", "sounds", "font" };
		for (String type : resourceTypes) {
			File dir = new File(base + "resources/" + type + File.separator + scId);
			deleteRecursive(dir);
		}
		
		// mysc/<sc_id> -> folder
		File myscDir = new File(base + "mysc" + File.separator + scId);
		deleteRecursive(myscDir);
		
		// data/<sc_id> -> folder
		File dataDir = new File(base + "data" + File.separator + scId);
		deleteRecursive(dataDir);
		
		// mysc/list/<sc_id> -> folder
		File myscListDir = new File(base + "mysc" + File.separator + "list" + File.separator + scId);
		deleteRecursive(myscListDir);
	}
	
	private boolean deleteRecursive(File fileOrDir) {
		if (fileOrDir == null || !fileOrDir.exists()) {
			return false;
		}
		if (fileOrDir.isDirectory()) {
			File[] children = fileOrDir.listFiles();
			if (children != null) {
				for (File child : children) {
					deleteRecursive(child);
				}
			}
		}
		return fileOrDir.delete();
	}
	
	private boolean deleteProjectDirectory(File projectDir) {
		return deleteRecursive(projectDir);
	}
	private void refreshProjects() {
		if (checkStoragePermission()) {
			if (adapter != null) {
				adapter.refresh();
				blockWrite.writeMathBlocks();
			}
		} else {
			// requestPermission();
			Snackbar.make(
			swipeRefresh,
			"Storage permission is required to refresh projects.",
			Snackbar.LENGTH_LONG
			)
			.setAction("Grant", new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					requestStoragePermission();
				}
			})
			.show();
		}
		
		// Stop the refreshing animation
		swipeRefresh.setRefreshing(false);
	}
	
	private boolean checkStoragePermission() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
			// Android 11, 12, 13, 14, 15 ke liye
			return Environment.isExternalStorageManager();
		} else {
			// Android 10 aur niche ke liye
			int read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
			return read == PackageManager.PERMISSION_GRANTED;
		}
	}
	
	// 2. Permission maangne ke liye
	private void requestStoragePermission() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
			try {
				Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
				intent.addCategory("android.intent.category.DEFAULT");
				intent.setData(Uri.parse(String.format("package:%s", getPackageName())));
				startActivityForResult(intent, 2000);
			} catch (Exception e) {
				Intent intent = new Intent();
				intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
				startActivityForResult(intent, 2000);
			}
		} else {
			// Purane Androids ke liye simple popup
			ActivityCompat.requestPermissions(this, 
			new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 
			1000);
		}
	}
	{
	}
	
	
	public void _toggleSearchBar() {
		isSearchVisible = !isSearchVisible;
		
		if (isSearchVisible) {
			searchBarLayout.setVisibility(View.VISIBLE);
			searchBar.requestFocus();
			// Show keyboard
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(searchBar, InputMethodManager.SHOW_IMPLICIT);
		} else {
			searchBarLayout.setVisibility(View.GONE);
			searchBar.setQuery("", false);
			// Hide keyboard
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(searchBar.getWindowToken(), 0);
			// Reset filter
			filterProjects("");
		}
	}
	
	private void filterProjects(String query) {
		if (adapter != null) {
			adapter.getFilter().filter(query);
		}
	}
	{
	}
	
	
	public void _create_paths() {
		
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