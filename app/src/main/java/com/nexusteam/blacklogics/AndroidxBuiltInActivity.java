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
import android.widget.Switch;
import android.widget.TextView;
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
import com.nexusteam.blacklogics.utils.Helper;
import com.besome.blacklogics.development.Complex;

public class AndroidxBuiltInActivity extends AppCompatActivity {
	
	public static Complex complex;
	private CompoundButton.OnCheckedChangeListener switchListener;
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear4;
	private TextView textview3;
	private LinearLayout linear3;
	private Switch switch1;
	private TextView textview1;
	private ImageView imageview1;
	private TextView textview2;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.androidx_built_in);
		initialize(_savedInstanceState);
		
		MobileAds.initialize(this);
		
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		linear4 = findViewById(R.id.linear4);
		textview3 = findViewById(R.id.textview3);
		linear3 = findViewById(R.id.linear3);
		switch1 = findViewById(R.id.switch1);
		textview1 = findViewById(R.id.textview1);
		imageview1 = findViewById(R.id.imageview1);
		textview2 = findViewById(R.id.textview2);
	}
	
	private void initializeLogic() {
		ImageView back = findViewById(R.id.ig_toolbar_back);
		TextView title = findViewById(R.id.tx_toolbar_title);
		ImageView loadFile = findViewById(R.id.ig_toolbar_load_file);
		
		Helper.applyRippleToToolbarView(back);
		back.setOnClickListener(Helper.getBackPressedClickListener(this));
		title.setText("AppCompat and Design");
		loadFile.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
		loadFile.setImageResource(R.drawable.ic_more_vert_black);
		loadFile.setVisibility(View.GONE);
		Helper.applyRippleToToolbarView(loadFile);
		complex = new Complex();
		complex.setC(this);
		complex.setId(getIntent().getStringExtra("sc_id"));
		switch1.setChecked(complex.getAndroidXEnable());
		switchListener = new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (complex.getAndroidXEnable()) {
					new com.google.android.material.dialog.MaterialAlertDialogBuilder(AndroidxBuiltInActivity.this)
					.setTitle("Warning")
					.setMessage("The existing Drawer Layout and FAB will be deleted. Would you like to continue?")
					.setIcon(R.drawable.ic_warning_red)
					.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							complex.enableAndroidX(true);
							complex.refreshData();
							
							switch1.setOnCheckedChangeListener(null);
							switch1.setChecked(true);
							switch1.setOnCheckedChangeListener(switchListener);
						}
					})
					.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							complex.enableAndroidX(false);
							complex.refreshData();
						}
					})
					.show();
				} else {
					complex.enableAndroidX(isChecked);
					complex.refreshData();
				}
			}
		};
		
		switch1.setOnCheckedChangeListener(switchListener);
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