package com.besome.blacklogics;

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
import com.shapun.layouteditor.ViewEditor;
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

public class ViewBuilderFragmentActivity extends Fragment implements ViewEditor.OnWidgetAdd {
	
	public static String layoutName = "main";
	public static String activityName = "MainActivity";
	public static LinearLayout fab;
	public static ViewBuilderFragmentActivity instance;
	public static boolean isMainActivity = true;
	public static String projectPath;
	public static String scName = "NewProject";
	public static String sc_id = "601";
	
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
		viewEditor.tv_view_name.setText(DesignActivity.currentActivityBean.getLayoutName() + ".xml");
		viewEditor.setPath(projectPath);
		viewEditor.a(DesignActivity.ll_properties);
		u();
		loadLayout();
	}
	
	public void _set_up() {
	}
	public void u() {
			boolean isToolbarEnable = DesignActivity.isToolbarEnabled(activityName);
			if (isToolbarEnable) {
					viewEditor.phone_action_bar.setVisibility(View.VISIBLE);
			} else {
					viewEditor.phone_action_bar.setVisibility(View.GONE);
			}
			boolean isFabEnable = DesignActivity.isEnableFab(activityName);
			if (isFabEnable) {
					enableFAB(true);
			} else {
					enableFAB(false);
			}
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
			        viewEditor.editorLayout.addView(fab);
			
			        // Wait until layout is ready, then move it
			        viewEditor.editorLayout.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
				            int parentWidth = viewEditor.editorLayout.getWidth();
				            int parentHeight = viewEditor.editorLayout.getHeight();
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
			if (DesignActivity.abc != null) {
					String javaCode = DesignActivity.abc.getJavaCode();
					String xmlCode = DesignActivity.abc.getXmlCode();
					DesignActivity.abc.complex.setXmlCode(DesignActivity.abc.currentActivityBean.getLayoutName(), xmlCode);
					DesignActivity.abc.complex.setJavaCode(DesignActivity.abc.currentActivityBean.getActivityName(), javaCode);
			}
	}
	public void showMessage(String a) {
		
	}
	{
	}
	
}