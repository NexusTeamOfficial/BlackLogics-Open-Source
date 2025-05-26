package com.besome.blacklogics;

import com.besome.blacklogics.SplashActivity;
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
import androidx.appcompat.*;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.solver.*;
import androidx.constraintlayout.widget.*;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.*;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.multidex.*;
import androidx.recyclerview.*;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import androidx.viewpager.*;
import androidx.viewpager2.*;
import com.besome.sketch.*;
import com.bumptech.glide.*;
import com.bumptech.glide.gifdecoder.*;
import com.example.myapp.*;
import com.github.angads25.filepicker.*;
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
import io.github.rosemoe.editor.*;
import io.github.rosemoe.sora.*;
import io.github.rosemoe.sora.langs.java.*;
import io.github.rosemoe.sora.langs.textmate.*;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.antlr.v4.runtime.*;
import org.benf.cfr.reader.*;
import org.eclipse.jdt.*;
import org.json.*;
import com.besome.blacklogics.adapter.ProjectAdapter;
import com.besome.blacklogics.model.*;
import mod.hey.studios.project.backup.BackupRestoreManager;
import com.besome.blacklogics.util.FileHandler;
import com.besome.blacklogics.beans.ProjectActivityBean;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.widget.EditText;
import android.view.inputmethod.InputMethodManager;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import mod.ilyasse.activities.about.AboutModActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.material.snackbar.Snackbar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class MainActivity extends AppCompatActivity {
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private FloatingActionButton _fab;
	private DrawerLayout _drawer;
	private boolean isSearchVisible = false;
	public ProjectAdapter adapter;
	public BackupRestoreManager backUpRestore;
	public ProjectActivityBean bean;
	
	private SwipeRefreshLayout swipeRefresh;
	private NestedScrollView baseDesign;
	private LinearLayout uiUx;
	private LinearLayout searchBarLayout;
	private MaterialToolbar baseTopLayout;
	private MaterialCardView linear1;
	private ShapeableImageView circleimageview1;
	private RecyclerView baseRecycler;
	private SearchView searchBar;
	private ImageView baseMenu;
	private ImageView baseTop;
	private ImageView baseSearch;
	private LinearLayout linear3;
	private ShapeableImageView imageview1;
	private MaterialTextView textview1;
	private LinearLayout _drawer_linear1;
	private ScrollView _drawer_vscroll1;
	private LinearLayout _drawer_linear2;
	private LinearLayout _drawer_linear24;
	private LinearLayout _drawer_linear28;
	private LinearLayout _drawer_linear_profiless;
	private TextView _drawer_names;
	private LinearLayout _drawer_about;
	private LinearLayout _drawer_Changelog;
	private LinearLayout _drawer_Setting;
	private LinearLayout _drawer_Programs;
	private LinearLayout _drawer_developer;
	private LinearLayout _drawer_Create;
	private LinearLayout _drawer_linear36;
	private LinearLayout _drawer_linear13;
	private ImageView _drawer_imageview10;
	private TextView _drawer_textview18;
	private ImageView _drawer_imageview6;
	private TextView _drawer_textview13;
	private ImageView _drawer_imageview12;
	private TextView _drawer_textview21;
	private ImageView _drawer_imageview14;
	private TextView _drawer_textview23;
	private ImageView _drawer_imageview15;
	private TextView _drawer_textview24;
	private ImageView _drawer_imageview16;
	private TextView _drawer_textview25;
	private ImageView _drawer_facebook;
	private ImageView _drawer_Program;
	private ImageView _drawer_youtube;
	
	private Intent i = new Intent();
	private Intent a = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
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
		
		swipeRefresh = findViewById(R.id.swipeRefresh);
		baseDesign = findViewById(R.id.baseDesign);
		uiUx = findViewById(R.id.uiUx);
		searchBarLayout = findViewById(R.id.searchBarLayout);
		baseTopLayout = findViewById(R.id.baseTopLayout);
		linear1 = findViewById(R.id.linear1);
		circleimageview1 = findViewById(R.id.circleimageview1);
		baseRecycler = findViewById(R.id.baseRecycler);
		searchBar = findViewById(R.id.searchBar);
		baseMenu = findViewById(R.id.baseMenu);
		baseTop = findViewById(R.id.baseTop);
		baseSearch = findViewById(R.id.baseSearch);
		linear3 = findViewById(R.id.linear3);
		imageview1 = findViewById(R.id.imageview1);
		textview1 = findViewById(R.id.textview1);
		_drawer_linear1 = _nav_view.findViewById(R.id.linear1);
		_drawer_vscroll1 = _nav_view.findViewById(R.id.vscroll1);
		_drawer_linear2 = _nav_view.findViewById(R.id.linear2);
		_drawer_linear24 = _nav_view.findViewById(R.id.linear24);
		_drawer_linear28 = _nav_view.findViewById(R.id.linear28);
		_drawer_linear_profiless = _nav_view.findViewById(R.id.linear_profiless);
		_drawer_names = _nav_view.findViewById(R.id.names);
		_drawer_about = _nav_view.findViewById(R.id.about);
		_drawer_Changelog = _nav_view.findViewById(R.id.Changelog);
		_drawer_Setting = _nav_view.findViewById(R.id.Setting);
		_drawer_Programs = _nav_view.findViewById(R.id.Programs);
		_drawer_developer = _nav_view.findViewById(R.id.developer);
		_drawer_Create = _nav_view.findViewById(R.id.Create);
		_drawer_linear36 = _nav_view.findViewById(R.id.linear36);
		_drawer_linear13 = _nav_view.findViewById(R.id.linear13);
		_drawer_imageview10 = _nav_view.findViewById(R.id.imageview10);
		_drawer_textview18 = _nav_view.findViewById(R.id.textview18);
		_drawer_imageview6 = _nav_view.findViewById(R.id.imageview6);
		_drawer_textview13 = _nav_view.findViewById(R.id.textview13);
		_drawer_imageview12 = _nav_view.findViewById(R.id.imageview12);
		_drawer_textview21 = _nav_view.findViewById(R.id.textview21);
		_drawer_imageview14 = _nav_view.findViewById(R.id.imageview14);
		_drawer_textview23 = _nav_view.findViewById(R.id.textview23);
		_drawer_imageview15 = _nav_view.findViewById(R.id.imageview15);
		_drawer_textview24 = _nav_view.findViewById(R.id.textview24);
		_drawer_imageview16 = _nav_view.findViewById(R.id.imageview16);
		_drawer_textview25 = _nav_view.findViewById(R.id.textview25);
		_drawer_facebook = _nav_view.findViewById(R.id.facebook);
		_drawer_Program = _nav_view.findViewById(R.id.Program);
		_drawer_youtube = _nav_view.findViewById(R.id.youtube);
		
		linear1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				backUpRestore.restore();
			}
		});
		
		baseMenu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_drawer.openDrawer(GravityCompat.START);
			}
		});
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), CreateProjectActivity.class);
				startActivity(i);
			}
		});
		
		_drawer_about.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Intent intent = new Intent(MainActivity.this, AboutModActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
			}
		});
		
		_drawer_Changelog.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Intent aboutModIntent = new Intent();
				aboutModIntent.setClass(MainActivity.this, AboutModActivity.class);
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
	}
	
	private void initializeLogic() {
		_create_paths();
		final LinearLayout _nav_view = (LinearLayout) findViewById(R.id._nav_view); _nav_view.setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));
		backUpRestore = new BackupRestoreManager(this);
		baseRecycler.setLayoutManager(new LinearLayoutManager(this));
		
		// Create and set adapter
		adapter = new ProjectAdapter(this);
		baseRecycler.setAdapter(adapter);
		// Initialize RecyclerView
		adapter.setOnItemClickListener(new ProjectAdapter.OnItemClickListener() {
			    @Override
			    public void onItemClick(int position, ProjectAdapter.ProjectItem project) {
				        // Handle item click
				        com.besome.blacklogics.model.DesignDataManager.setJavaName("MainActivity");
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
				        // Handle edit click
				        Intent intent = new Intent(MainActivity.this, CreateProjectActivity.class);
				        intent.putExtra("update", true);
				        intent.putExtra("project", (Serializable)project);
				       // intent.putExtra("project", project);
				        startActivity(intent);
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
		});
		
		// Refresh when needed
		swipeRefresh.setOnRefreshListener(() -> {
						refreshProjects();
				});
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
	}
	
	
	@Override
	public void onBackPressed() {
		finishAffinity();
	}
	public void _b() {
	}
	// Add this method to MainActivity.java
	private void showDeleteDialog(int position, ProjectAdapter.ProjectItem project) {
		    new MaterialAlertDialogBuilder(this)
		        .setTitle("Delete Project")
		        .setMessage("Are you sure you want to delete \"" + project.projectName + "\"? This action cannot be undone.")
		        .setPositiveButton("Delete", (dialog, which) -> {
			            // Delete the project directory
			            if (deleteProjectDirectory(project.projectDir)) {
				                // Remove from adapter
				                ProjectAdapter adapter = (ProjectAdapter) baseRecycler.getAdapter();
				                if (adapter != null) {
					                    adapter.removeProject(position);
					                }
				                showMessage("Project deleted successfully");
				            } else {
				                showMessage("Failed to delete project");
				            }
			        })
		        .setNegativeButton("Cancel", null)
		        .show();
	}
	
	private boolean deleteProjectDirectory(File projectDir) {
		    if (projectDir.exists() && projectDir.isDirectory()) {
			        // Recursively delete directory
			        File[] files = projectDir.listFiles();
			        if (files != null) {
				            for (File file : files) {
					                if (file.isDirectory()) {
						                    deleteProjectDirectory(file);
						                } else {
						                    file.delete();
						                }
					            }
				        }
			        return projectDir.delete();
			    }
		    return false;
	}
	
	// Update the initializeLogic method to uncomment and use the showDeleteDialog
	private void refreshProjects() {
		    if (checkPermission()) {
			        if (adapter != null) {
				            adapter.refresh();
				        }
			    } else {
			      //  requestPermission();
			        Snackbar.make(swipeRefresh, "Storage permission is required to refresh projects.", Snackbar.LENGTH_LONG)
			                .setAction("Grant", v -> requestPermission())
			                .show();
			    }
		
		    // Stop the refreshing animation
		    swipeRefresh.setRefreshing(false);
	}
	
	private boolean checkPermission() {
		    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
			        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED &&
			               ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED &&
			               ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED;
			    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) { // Android 11 - 12
			        return Environment.isExternalStorageManager();
			    } else { // Android 10 and below
			        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
			               ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
			    }
	}
	
	private void requestPermission() {
		    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
			        ActivityCompat.requestPermissions(this, new String[]{
				                Manifest.permission.READ_MEDIA_IMAGES,
				                Manifest.permission.READ_MEDIA_VIDEO,
				                Manifest.permission.READ_MEDIA_AUDIO
				        }, 100);
			    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) { // Android 11 - 12
			        try {
				            Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
				            intent.setData(Uri.parse("package:" + getPackageName()));
				            startActivity(intent);
				        } catch (Exception e) {
				            Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
				            startActivity(intent);
				        }
			    } else { // Android 10 and below
			        ActivityCompat.requestPermissions(this, new String[]{
				                Manifest.permission.READ_EXTERNAL_STORAGE,
				                Manifest.permission.WRITE_EXTERNAL_STORAGE
				        }, 100);
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