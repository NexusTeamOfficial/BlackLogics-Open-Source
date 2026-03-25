package com.nexusteam.blacklogics;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.Context;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.os.Vibrator;
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
import androidx.annotation.*;
import androidx.appcompat.*;
import androidx.appcompat.app.AppCompatActivity;
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
import com.shapun.layouteditor.ViewEditor;
import com.squareup.leakcanary.*;
import de.hdodenhof.circleimageview.*;
import io.github.rosemoe.editor.*;
import io.github.rosemoe.sora.*;
import io.github.rosemoe.sora.langs.java.*;
import io.github.rosemoe.sora.langs.textmate.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import org.antlr.v4.runtime.*;
import org.benf.cfr.reader.*;
import org.eclipse.jdt.*;
import org.json.*;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.shapun.layouteditor.ViewEditor;
import com.besome.blacklogics.development.Complex;
import com.nexusteam.blacklogics.sdk.util.ActivityConfig;

public class ViewBuilderFragmentActivity extends Fragment implements ViewEditor.OnWidgetAdd {
	
	public static LinearLayout fab;
	public static ViewBuilderFragmentActivity instance;
	public static boolean isMainActivity = true;
	public static String projectPath;
	public static String scName = "NewProject";
	public static String sc_id = "601";
	public String layoutName = "main";
	public String activityName = "MainActivity";
	private ActivityConfig activityConfig;
	
	private ArrayList<HashMap<String, Object>> viewsList = new ArrayList<>();
	private ArrayList<String> typeList = new ArrayList<>();
	
	private LinearLayout lin_toolbar;
	public ViewEditor viewEditor;
	private ImageView img_views;
	private LinearLayout linear9;
	private ImageView img_import;
	private ImageView img_copy;
	private ImageView img_add_image;
	
	private Vibrator vib;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.view_builder_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		instance = this;
		try{
			projectPath = requireActivity().getIntent().getStringExtra("projectPath");
			sc_id = requireActivity().getIntent().getStringExtra("sc_id");
			scName = requireActivity().getIntent().getStringExtra("scName");
		}catch(Exception e){
			SketchwareUtil.showMessage(getContext().getApplicationContext(), e.toString());
		}
		activityConfig = new ActivityConfig();
		activityConfig.init(getContext(), sc_id);
		lin_toolbar = _view.findViewById(R.id.lin_toolbar);
		viewEditor = _view.findViewById(R.id.viewEditor);
		img_views = _view.findViewById(R.id.img_views);
		linear9 = _view.findViewById(R.id.linear9);
		img_import = _view.findViewById(R.id.img_import);
		img_copy = _view.findViewById(R.id.img_copy);
		img_add_image = _view.findViewById(R.id.img_add_image);
		vib = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
	}
	
	private void initializeLogic() {
		try{
			projectPath = requireActivity().getIntent().getStringExtra("projectPath");
			sc_id = requireActivity().getIntent().getStringExtra("sc_id");
			scName = requireActivity().getIntent().getStringExtra("scName");
		}catch(Exception e){
			SketchwareUtil.showMessage(getContext().getApplicationContext(), e.toString());
		}
		viewEditor.tv_view_name.setText(DesignActivity.abc.activityBean.getLayoutName() + ".xml");
		String projectDataDir = com.nexusteam.internal.fe.d(sc_id);
		viewEditor.setPath(projectDataDir);
		viewEditor.setScId(sc_id);
		viewEditor.setAttributesContainer(DesignActivity.ll_properties);
		viewEditor.setDesignActivity(DesignActivity.abc);
		u();
		loadLayout();
	}
	
	public void _set_up() {
	}
	public void u() {
		Complex c= new Complex();
		//c.setC(DesignActivity.abc);
		c.setId(sc_id);
		boolean isToolbarEnable = activityConfig.hasToolbar(DesignActivity.abc.activityBean.getActivityName());
		if (isToolbarEnable) {
			viewEditor.phone_action_bar.setVisibility(View.VISIBLE);
		} else {
			viewEditor.phone_action_bar.setVisibility(View.GONE);
		}
		boolean isFabEnable = activityConfig.hasFab(activityName);
		if (isFabEnable) {
			enableFAB(true);
		} else {
			enableFAB(false);
		}
	}
	
	public void enableFAB(boolean enable) {
		if (fab == null) {
			// Create FAB LinearLayout
			fab = new LinearLayout(getContext());
			fab.setId(R.id._fab);
			fab.setBackgroundResource(R.drawable.circle_background);
			fab.setGravity(Gravity.CENTER);
			fab.setOrientation(LinearLayout.VERTICAL);
			
			// Set layout parameters
			final int size = getDp(56); // Standard FAB size 56dp
			
			// Use ViewGroup.LayoutParams for LinearLayout
			LinearLayout.LayoutParams fabParams = new LinearLayout.LayoutParams(size, size);
			fab.setLayoutParams(fabParams);
			
			// Add margin to FAB (optional)
			int margin = getDp(16);
			if (Build.VERSION.SDK_INT >= 17) {
				ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) fab.getLayoutParams();
				marginParams.setMargins(0, 0, margin, margin);
				fab.setLayoutParams(marginParams);
			}
			
			// Create plus icon
			ImageView plusIcon = new ImageView(getContext());
			
			// Set image resource - use ic_add instead of ic_plus (standard Android icon)
			if (Build.VERSION.SDK_INT >= 21) {
				plusIcon.setImageResource(android.R.drawable.ic_input_add);
			} else {
				// Fallback for older versions
				plusIcon.setImageResource(android.R.drawable.ic_menu_add);
			}
			
			// Set icon layout params
			int iconSize = getDp(24);
			LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(iconSize, iconSize);
			plusIcon.setLayoutParams(iconParams);
			
			// Set icon color to white
			if (Build.VERSION.SDK_INT >= 21) {
				plusIcon.setColorFilter(Color.WHITE);
			} else {
				// For older versions, use a ColorFilter
				plusIcon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
			}
			
			// Add icon to FAB
			fab.addView(plusIcon);
			
			// Add FAB to editor layout
			viewEditor.editorLayout.addView(fab);
			
			// Position FAB at bottom-right
			positionFabAtBottomRight();
		}
		
		// Show/hide FAB based on enable parameter
		if (fab != null) {
			fab.setVisibility(enable ? View.VISIBLE : View.GONE);
		}
	}
	
	/**
 * Position FAB at bottom-right corner
 */
	private void positionFabAtBottomRight() {
		if (fab == null || viewEditor == null || viewEditor.editorLayout == null) {
			return;
		}
		
		// Use ViewTreeObserver to position after layout
		viewEditor.editorLayout.getViewTreeObserver().addOnGlobalLayoutListener(
		new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				if (fab == null || viewEditor == null || viewEditor.editorLayout == null) {
					return;
				}
				
				int parentWidth = viewEditor.editorLayout.getWidth();
				int parentHeight = viewEditor.editorLayout.getHeight();
				int fabWidth = fab.getWidth();
				int fabHeight = fab.getHeight();
				int margin = getDp(16);
				
				// Calculate position
				float x = parentWidth - fabWidth - margin;
				float y = parentHeight - fabHeight - margin;
				
				// Set position
				if (Build.VERSION.SDK_INT >= 11) {
					fab.setX(x);
					fab.setY(y);
				} else {
					// For older versions, use LayoutParams
					LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) fab.getLayoutParams();
					params.leftMargin = (int) x;
					params.topMargin = (int) y;
					fab.setLayoutParams(params);
				}
				
				// Remove listener to avoid multiple calls
				if (Build.VERSION.SDK_INT >= 16) {
					viewEditor.editorLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				} else {
					viewEditor.editorLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				}
			}
		});
	}
	
	/**
 * Vibrate on FAB click
 */
	private void vibrate() {
		if (vib != null) {
			vib.vibrate(50); // 50ms vibration
		}
	}
	
	/**
 * Refresh FAB position when orientation changes
 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Reposition FAB after configuration change
		if (fab != null && fab.getVisibility() == View.VISIBLE) {
			positionFabAtBottomRight();
		}
	}
	
	/**
 * Check if FAB should be shown based on activity config
 */
	private void updateFabFromConfig() {
		if (activityConfig != null) {
			boolean shouldShowFab = activityConfig.hasFab(activityName);
			enableFAB(shouldShowFab);
		}
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
	/**
 * Saves the current layout to a file in internal storage
 */
	public void saveLayout() {
		viewEditor.saveLayout(activityName);
	}
	
	/**
 * Loads a layout from a file and applies it to the editorLayout.
*/ 
	public void loadLayout() {
		viewEditor.loadLayout(activityName);
	}
	@Override
	public void onWidgetAdded(View widget, ViewGroup parent) {
	}
	
	public void showMessage(String a) {
		
	}
	
	/**
 * Call this after loading layout to update FAB state
 */
	public void refreshUI() {
		updateFabFromConfig();
		positionFabAtBottomRight();
	}
	{
	}
	
}