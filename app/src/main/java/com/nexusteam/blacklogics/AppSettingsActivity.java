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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.*;
import androidx.constraintlayout.widget.*;
import androidx.core.*;
import androidx.core.splashscreen.*;
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
import com.google.android.material.card.*;
import com.google.android.material.imageview.ShapeableImageView;
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
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.antlr.v4.runtime.*;
import org.benf.cfr.reader.*;
import org.json.*;
import com.nexusteam.blacklogics.utils.Helper;
import com.nexusteam.blacklogics.block_manager.BlocksManagerActivity;
import com.nexusteam.blacklogics.lib.language.LanguageManager;
import com.nexusteam.internal.editor.component.ComponentMakeActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import androidx.appcompat.app.AppCompatDelegate;
import android.widget.CompoundButton;

public class AppSettingsActivity extends com.nexusteam.blacklogics.lib.base.LocaleBaseActivity {
	
	private CompoundButton.OnCheckedChangeListener languageListener;
	private static final int REQUEST_MAKE_COMPONENT = 1001;
	private static final String THEME_PREF = "AppSettings";
	private static final String KEY_THEME_MODE = "theme_mode";
	
	private ScrollView vscroll2;
	private LinearLayout linear1;
	private TextView textview7;
	private MaterialCardView blockManagerCard;
	private MaterialCardView aboutAppCard;
	private MaterialCardView componentManagerCard;
	private TextView textview8;
	private MaterialCardView cardview2;
	private AdLayout adLayout;
	private LinearLayout linear11;
	private ShapeableImageView blockManager;
	private LinearLayout linear3;
	private ImageView imageview7;
	private TextView blocksManagerTitle;
	private TextView blockManagerDesc;
	private LinearLayout linear12;
	private ShapeableImageView imageview1;
	private LinearLayout linear6;
	private ImageView imageview8;
	private TextView textview1;
	private TextView textview2;
	private LinearLayout linear17;
	private ShapeableImageView imageview2;
	private LinearLayout linear18;
	private ImageView imageview9;
	private TextView textview11;
	private TextView textview12;
	private LinearLayout linear13;
	private LinearLayout linear14;
	private View view15;
	private LinearLayout linear16;
	private TextView textview9;
	private TextView themeValue;
	private ImageView imageview4;
	private TextView textview10;
	private TextView languageValue;
	private ImageView imageview10;
	
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
		componentManagerCard = findViewById(R.id.componentManagerCard);
		textview8 = findViewById(R.id.textview8);
		cardview2 = findViewById(R.id.cardview2);
		adLayout = findViewById(R.id.adLayout);
		linear11 = findViewById(R.id.linear11);
		blockManager = findViewById(R.id.blockManager);
		linear3 = findViewById(R.id.linear3);
		imageview7 = findViewById(R.id.imageview7);
		blocksManagerTitle = findViewById(R.id.blocksManagerTitle);
		blockManagerDesc = findViewById(R.id.blockManagerDesc);
		linear12 = findViewById(R.id.linear12);
		imageview1 = findViewById(R.id.imageview1);
		linear6 = findViewById(R.id.linear6);
		imageview8 = findViewById(R.id.imageview8);
		textview1 = findViewById(R.id.textview1);
		textview2 = findViewById(R.id.textview2);
		linear17 = findViewById(R.id.linear17);
		imageview2 = findViewById(R.id.imageview2);
		linear18 = findViewById(R.id.linear18);
		imageview9 = findViewById(R.id.imageview9);
		textview11 = findViewById(R.id.textview11);
		textview12 = findViewById(R.id.textview12);
		linear13 = findViewById(R.id.linear13);
		linear14 = findViewById(R.id.linear14);
		view15 = findViewById(R.id.view15);
		linear16 = findViewById(R.id.linear16);
		textview9 = findViewById(R.id.textview9);
		themeValue = findViewById(R.id.themeValue);
		imageview4 = findViewById(R.id.imageview4);
		textview10 = findViewById(R.id.textview10);
		languageValue = findViewById(R.id.languageValue);
		imageview10 = findViewById(R.id.imageview10);
		prefs = getSharedPreferences("AppSettings", Activity.MODE_PRIVATE);
		
		linear11.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Intent intent = new Intent(getApplicationContext(), com.nexusteam.blacklogics.block_manager.BlocksManagerActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
			}
		});
		
		linear17.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Intent intent = new Intent(AppSettingsActivity.this, ComponentMakeActivity.class);
				startActivityForResult(intent, REQUEST_MAKE_COMPONENT);
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
		updateThemeLabel();
		linear14.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showThemeDialog();
			}
		});
		updateLanguageLabel();
		linear16.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showLanguageDialog();
			}
		});
		String lang = com.nexusteam.blacklogics.lib.language.LanguageManager.getLanguage(this);
		/*switch2.setOnCheckedChangeListener(null);
switch2.setChecked("hi".equals(lang));
switch2.setOnCheckedChangeListener(languageListener);*/
		adLayout.loadBannerAd();
	}
	
	public void _block() {
	}
	private int getSavedThemeMode() {
		return prefs.getInt(KEY_THEME_MODE,
		AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
	}
	
	private void updateThemeLabel() {
		switch (getSavedThemeMode()) {
			case AppCompatDelegate.MODE_NIGHT_NO:
			themeValue.setText(R.string.theme_light);
			break;
			case AppCompatDelegate.MODE_NIGHT_YES:
			themeValue.setText(R.string.theme_dark);
			break;
			default:
			themeValue.setText(R.string.theme_system_default);
		}
	}
	private void showThemeDialog() {
		// ✅ "System Default" option hata diya — yeh flip karta hai
		final String[] options = {
			getString(R.string.theme_system_default),
			getString(R.string.theme_light),
			getString(R.string.theme_dark)
		};
		final int[] modes = {
			AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
			AppCompatDelegate.MODE_NIGHT_NO,
			AppCompatDelegate.MODE_NIGHT_YES
		};
		
		int current = getSavedThemeMode();
		int checked = 0;
		for (int i = 0; i < modes.length; i++) {
			if (modes[i] == current) {
				checked = i;
				break;
			}
		}
		
		new MaterialAlertDialogBuilder(this)
		.setTitle(R.string.theme_title)
		.setSingleChoiceItems(options, checked, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				int newMode = modes[which];
				prefs.edit().putInt(KEY_THEME_MODE, newMode).apply();
				dialog.dismiss();
				if (AppCompatDelegate.getDefaultNightMode() != newMode) {
					AppCompatDelegate.setDefaultNightMode(newMode);
				}
				updateThemeLabel();
			}
		})
		.setNegativeButton(R.string.cancel, null)
		.show();
	}
	
	private void updateLanguageLabel() {
		String lang = LanguageManager.getLanguage(this);
		languageValue.setText("hi".equals(lang) ? "हिन्दी" : "English");
	}
	
	private void showLanguageDialog() {
		final String[] names = {"English", "हिन्दी"};
		final String[] codes = {"en", "hi"};
		
		String current = LanguageManager.getLanguage(this);
		int checked = "hi".equals(current) ? 1 : 0;
		
		new MaterialAlertDialogBuilder(this)
		.setTitle(R.string.language_title)
		.setSingleChoiceItems(names, checked, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if (!codes[which].equals(LanguageManager.getLanguage(AppSettingsActivity.this))) {
					LanguageManager.changeLanguage(AppSettingsActivity.this, codes[which]);
				}
			}
		})
		.setNegativeButton(R.string.cancel, null)
		.show();
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