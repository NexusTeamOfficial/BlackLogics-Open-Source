package com.nexusteam.blacklogics;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
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
import com.google.android.material.button.*;
import com.google.android.material.card.*;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.gson.*;
import com.googlecode.d2j.*;
import com.larswerkman.holocolorpicker.*;
import com.nexusteam.blacklogics.widget.AdLayout;
import com.squareup.leakcanary.*;
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
import com.besome.blacklogics.block_manager.BlocksManagerActivity;
import com.nexusteam.blacklogics.lib.language.LanguageManager;
import androidx.appcompat.app.AppCompatDelegate;
import android.widget.CompoundButton;

public class AppSettingsActivity extends AppCompatActivity {
	
	private CompoundButton.OnCheckedChangeListener languageListener;
	
	private ScrollView vscroll2;
	private LinearLayout linear1;
	private TextView textview7;
	private MaterialCardView blockManagerCard;
	private MaterialCardView aboutAppCard;
	private TextView textview8;
	private MaterialCardView cardview2;
	private MaterialButton materialbutton1;
	private AdLayout adLayout;
	private Button button1;
	private Button button2;
	private LinearLayout linear11;
	private ImageView blockManager;
	private LinearLayout linear3;
	private TextView blocksManagerTitle;
	private TextView blockManagerDesc;
	private LinearLayout linear12;
	private ImageView imageview1;
	private LinearLayout linear6;
	private TextView textview1;
	private TextView textview2;
	private LinearLayout linear13;
	private LinearLayout linear14;
	private View view15;
	private LinearLayout linear16;
	private TextView textview9;
	private SwitchMaterial switch1;
	private TextView textview10;
	private TextView languageValue;
	
	private SharedPreferences prefs;
	private com.google.android.material.bottomsheet.BottomSheetDialog bottomTestSheet;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.app_settings);
		initialize(_savedInstanceState);
		
		MobileAds.initialize(this);
		
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		languageListener = new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					LanguageManager.changeLanguage(AppSettingsActivity.this, "hi");
				} else {
					LanguageManager.changeLanguage(AppSettingsActivity.this, "en");
				}
			}
		};
		
		vscroll2 = findViewById(R.id.vscroll2);
		linear1 = findViewById(R.id.linear1);
		textview7 = findViewById(R.id.textview7);
		blockManagerCard = findViewById(R.id.blockManagerCard);
		aboutAppCard = findViewById(R.id.aboutAppCard);
		textview8 = findViewById(R.id.textview8);
		cardview2 = findViewById(R.id.cardview2);
		materialbutton1 = findViewById(R.id.materialbutton1);
		adLayout = findViewById(R.id.adLayout);
		button1 = findViewById(R.id.button1);
		button2 = findViewById(R.id.button2);
		linear11 = findViewById(R.id.linear11);
		blockManager = findViewById(R.id.blockManager);
		linear3 = findViewById(R.id.linear3);
		blocksManagerTitle = findViewById(R.id.blocksManagerTitle);
		blockManagerDesc = findViewById(R.id.blockManagerDesc);
		linear12 = findViewById(R.id.linear12);
		imageview1 = findViewById(R.id.imageview1);
		linear6 = findViewById(R.id.linear6);
		textview1 = findViewById(R.id.textview1);
		textview2 = findViewById(R.id.textview2);
		linear13 = findViewById(R.id.linear13);
		linear14 = findViewById(R.id.linear14);
		view15 = findViewById(R.id.view15);
		linear16 = findViewById(R.id.linear16);
		textview9 = findViewById(R.id.textview9);
		switch1 = findViewById(R.id.switch1);
		textview10 = findViewById(R.id.textview10);
		languageValue = findViewById(R.id.languageValue);
		prefs = getSharedPreferences("AppSettings", Activity.MODE_PRIVATE);
		
		materialbutton1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				bottomTestSheet = new com.google.android.material.bottomsheet.BottomSheetDialog(AppSettingsActivity.this);
				View bottomTestSheetV;
				bottomTestSheetV = getLayoutInflater().inflate(R.layout.widget_property,null );
				bottomTestSheet.setContentView(bottomTestSheetV);
				bottomTestSheet.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
				bottomTestSheet.setCancelable(true);
				bottomTestSheet.show();
			}
		});
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				com.nexusteam.blacklogics.lib.language.LanguageManager.changeLanguage(AppSettingsActivity.this, "en");
			}
		});
		
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				com.nexusteam.blacklogics.lib.language.LanguageManager.changeLanguage(AppSettingsActivity.this, "hi");
			}
		});
		
		linear11.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Intent intent = new Intent(getApplicationContext(), com.besome.blacklogics.block_manager.BlocksManagerActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
			}
		});
	}
	
	private void initializeLogic() {
		ImageView back = findViewById(R.id.ig_toolbar_back);
		TextView title = findViewById(R.id.tx_toolbar_title);
		ImageView loadFile = findViewById(R.id.ig_toolbar_load_file);
		
		Helper.applyRippleToToolbarView(back);
		back.setOnClickListener(Helper.getBackPressedClickListener(this));
		title.setText("Settings");
		loadFile.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
		loadFile.setImageResource(R.drawable.ic_more_vert_black);
		loadFile.setVisibility(View.GONE);
		Helper.applyRippleToToolbarView(loadFile);
		boolean isDarkMode = prefs.getBoolean("dark_mode", false);
		switch1.setChecked(isDarkMode);
		
		switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				SharedPreferences.Editor editor = prefs.edit();
				editor.putBoolean("dark_mode", _isChecked);
				editor.apply();
				
				// Apply dark mode
				if (_isChecked) {
					AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
				} else {
					AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
				}
			}
		});
		
		String lang = com.nexusteam.blacklogics.lib.language.LanguageManager.getLanguage(this);
		/*switch2.setOnCheckedChangeListener(null);
switch2.setChecked("hi".equals(lang));
switch2.setOnCheckedChangeListener(languageListener);*/
		adLayout.loadBannerAd();
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