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
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
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
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.material.*;
import com.google.gson.*;
import com.googlecode.d2j.*;
import com.larswerkman.holocolorpicker.*;
import com.nexusteam.blacklogics.sdk.layouteditor.ViewEditor;
import com.nexusteam.filepicker.elitefileselector.*;
import com.nexusteam.nexatask.*;
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
import org.json.*;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.nexusteam.blacklogics.sdk.layouteditor.ViewEditor;
import com.nexusteam.blacklogics.development.Complex;
import com.nexusteam.blacklogics.sdk.util.ActivityConfig;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nexusteam.blacklogics.sdk.AttributeSet;
import com.nexusteam.blacklogics.sdk.layouteditor.Attribute;


public class ViewBuilderFragmentActivity extends Fragment implements ViewEditor.OnWidgetAdd {
	
	public static View fab;
	public static ViewBuilderFragmentActivity instance;
	public static boolean isMainActivity = true;
	public static String projectPath;
	public static String scName = "NewProject";
	public static String sc_id = "601";
	public String layoutName = "main";
	public String activityName = "MainActivity";
	public boolean isCustomLayout = false;
	public ActivityConfig activityConfig;
	
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
	private Dialog loadingDialog;
	
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
        String projectPathBase = FileUtil.getExternalStorageDir() + "/.blacklogics/data/" + sc_id;
		viewEditor.tv_view_name.setText(DesignActivity.abc.activityBean.getLayoutName() + ".xml");
		String projectDataDir = com.nexusteam.internal.fe.d(sc_id);
		viewEditor.setPath(projectPathBase);
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
		c.setId(sc_id);
		
		String currentActivityName = activityName;
		if (currentActivityName == null || currentActivityName.trim().isEmpty()) {
			currentActivityName = (DesignActivity.abc != null && DesignActivity.abc.activityBean != null)
					? DesignActivity.abc.activityBean.getActivityName()
					: "MainActivity";
		}
		if (currentActivityName.endsWith(".java")) {
			currentActivityName = currentActivityName.substring(0, currentActivityName.length() - 5);
		}
		Log.i("ViewBuilderFragment", "u() toolbar/fab check for " + currentActivityName
				+ " isCustomLayout=" + isCustomLayout);
		
		if (isCustomLayout) {
			try {
				if (viewEditor != null && viewEditor.phone_action_bar != null) {
					viewEditor.phone_action_bar.setVisibility(View.GONE);
				}
			} catch (Throwable ignore) {}
			enableFAB(false);
			return;
		}

		try {
			if (activityConfig != null && sc_id != null && getContext() != null) {
				activityConfig.init(getContext(), sc_id);
			}
		} catch (Throwable ignore) {}

		boolean isToolbarEnable = true;
		boolean isFabEnable = false;
		try {
			if (activityConfig != null && currentActivityName != null) {
				isToolbarEnable = activityConfig.hasToolbar(currentActivityName);
				isFabEnable = activityConfig.hasFab(currentActivityName);
			}
		} catch (Throwable ignore) {}
		Log.i("ViewBuilderFragment", "toolbar=" + isToolbarEnable
				+ " fab=" + isFabEnable + " act=" + currentActivityName);
		try {
			if (viewEditor != null && viewEditor.phone_action_bar != null) {
				viewEditor.phone_action_bar.setVisibility(
						isToolbarEnable ? View.VISIBLE : View.GONE);
			}
		} catch (Throwable ignore) {}
		enableFAB(isFabEnable);
	}
	
	/**
	 * Enable / Disable FAB — uses ViewEditor simple methods only.
	 * (ViewBuilder FAB logic is not used; enableEditorFab / disableEditorFab in ViewEditor.)
	 */
	public void enableFAB(boolean enable) {
		if (viewEditor == null) return;
		try {
			if (enable) {
				viewEditor.enableEditorFab();
			} else {
				viewEditor.disableEditorFab();
			}
			// sync static fab for any old code that still reads ViewBuilderFragmentActivity.fab
			fab = null;
			try {
				if (viewEditor.editorLayout != null) {
					ViewGroup root = viewEditor.editorLayout;
					for (int i = 0; i < root.getChildCount(); i++) {
						View c = root.getChildAt(i);
						if (c != null && ("_fab".equals(c.getTag()) || "fab".equals(String.valueOf(c.getTag())))) {
							fab = c;
							break;
						}
					}
				}
			} catch (Throwable ignore) {}
			Log.i("ViewBuilderFragment", "FAB via ViewEditor enable=" + enable + " fab=" + (fab != null));
		} catch (Throwable t) {
			Log.e("ViewBuilderFragment", "enableFAB -> ViewEditor failed", t);
		}
	}

	private void positionFabAtBottomRight() {
	}

	private void vibrate() {
		if (vib != null) {
			vib.vibrate(50);
		}
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (fab != null && fab.getVisibility() == View.VISIBLE) {
			positionFabAtBottomRight();
		}
	}
	
	private void updateFabFromConfig() {
		if (activityConfig != null) {
			boolean shouldShowFab = activityConfig.hasFab(activityName);
			enableFAB(shouldShowFab);
		}
	}
	
	public int getDp(float dp) {
		Context context = getContext();
		if (context == null) return (int) dp;
		return (int) TypedValue.applyDimension(
		TypedValue.COMPLEX_UNIT_DIP,
		dp,
		context.getResources().getDisplayMetrics()
		);
	}
	private String layoutKey() {
		String key;
		if (isCustomLayout && layoutName != null && layoutName.trim().length() > 0) {
			key = layoutName.trim();
		} else {
			key = activityName;
			if (key == null || key.trim().isEmpty()) {
				key = layoutName != null ? layoutName : "MainActivity";
			}
		}
		key = key.trim();
		if (key.endsWith(".java")) key = key.substring(0, key.length() - 5);
		if (key.endsWith(".xml")) key = key.substring(0, key.length() - 4);
		return key;
	}

	public void saveLayout() {
		if (viewEditor == null) return;
		String key = layoutKey();
		Log.i("ViewBuilderFragment", "saveLayout key=" + key);
		try {
			viewEditor.saveLayout(key);
		} catch (Throwable t) {
			Log.e("ViewBuilderFragment", "saveLayout " + key, t);
		}
	}
	
	public void loadLayout() {
		if (viewEditor == null) return;
		final String key = layoutKey();
		final String label;
		if (layoutName != null && layoutName.length() > 0) {
			String l = layoutName;
			if (l.endsWith(".xml")) l = l.substring(0, l.length() - 4);
			label = l + ".xml";
		} else {
			label = key + ".xml";
		}
		Log.i("ViewBuilderFragment", "loadLayout key=" + key + " label=" + label + " isCustom=" + isCustomLayout);
		showLoadingDialog();
		viewEditor.loadLayoutAsync(key, new ViewEditor.LoadCallback() {
			@Override
			public void onLoaded() {
				dismissLoadingDialog();
				try {
					if (viewEditor.tv_view_name != null) {
						viewEditor.tv_view_name.setText(label);
					}
				} catch (Throwable ignore) {}
				try {
					if (viewEditor != null) {
						viewEditor.ensureRegistryAfterLoad();
						viewEditor.forceRegistryRecovery();
					}
				} catch (Throwable t) {
					android.util.Log.e("ViewBuilderFragment", "post-load registry ensure", t);
				}
				refreshUI();
			}

			@Override
			public void onFailed(Exception e) {
				Log.e("ViewBuilderFragment", "loadLayoutAsync failed key=" + key, e);
				dismissLoadingDialog();
				refreshUI();
			}
		});
	}

	private void showLoadingDialog() {
		try {
			if (getContext() == null) return;
			if (loadingDialog == null) {
				loadingDialog = new Dialog(getContext());
				loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				loadingDialog.setCancelable(false);
				loadingDialog.setContentView(R.layout.loading);
				if (loadingDialog.getWindow() != null) {
					loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				}
			}
			if (!loadingDialog.isShowing()) {
				loadingDialog.show();
			}
		} catch (Exception e) {
			Log.e("ViewBuilderFragment", "Error showing loading dialog", e);
		}
	}

	private void dismissLoadingDialog() {
		try {
			if (loadingDialog != null && loadingDialog.isShowing()) {
				loadingDialog.dismiss();
			}
		} catch (Exception e) {
			Log.e("ViewBuilderFragment", "Error dismissing loading dialog", e);
		}
	}

	@Override
	public void onDestroyView() {
		dismissLoadingDialog();
		loadingDialog = null;
		super.onDestroyView();
	}
	@Override
	public void onWidgetAdded(View widget, ViewGroup parent) {
	}
	
	public void showMessage(String a) {
		
	}
	
	public void refreshUI() {
		updateFabFromConfig();
		positionFabAtBottomRight();
	}
	{
	}
	
}
