package com.nexusteam.blacklogics;

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
import com.google.gson.*;
import com.googlecode.d2j.*;
import com.larswerkman.holocolorpicker.*;
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
import java.util.regex.*;
import org.antlr.v4.runtime.*;
import org.benf.cfr.reader.*;
import org.eclipse.jdt.*;
import org.json.*;
import com.nexusteam.blacklogics.utils.Helper;
import androidx.appcompat.app.AlertDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.nexusteam.blacklogics.activities.OssLicenseActivity;

public class ProgramInformationActivity extends AppCompatActivity {
	
	private ScrollView scrollView;
	private RelativeLayout relativelayout97;
	private RelativeLayout headerLayout;
	private LinearLayout buttonLayout;
	private TextView settingsHeader;
	private MaterialCardView cardview1;
	private MaterialCardView cardview2;
	private View divider1;
	private TextView communityHeader;
	private MaterialCardView cardview3;
	private View divider2;
	private TextView legalHeader;
	private MaterialCardView cardview4;
	private MaterialCardView cardview5;
	private TextView footerText;
	private MaterialCardView cardview6;
	private TextView textview1;
	private TextView textViewVersion;
	private ImageView imageview1;
	private MaterialButton materialbutton1;
	private MaterialButton materialbutton2;
	private RelativeLayout relativelayout98;
	private TextView textview3;
	private TextView textview4;
	private RelativeLayout relativelayout99;
	private TextView textview5;
	private TextView textview6;
	private RelativeLayout relativelayout100;
	private TextView textview7;
	private TextView textview8;
	private RelativeLayout relativelayout101;
	private TextView textview9;
	private RelativeLayout relativelayout102;
	private TextView textview10;
	
	private Intent i = new Intent();
	private Intent oss = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.program_information);
		initialize(_savedInstanceState);
		
		MobileAds.initialize(this);
		
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		scrollView = findViewById(R.id.scrollView);
		relativelayout97 = findViewById(R.id.relativelayout97);
		headerLayout = findViewById(R.id.headerLayout);
		buttonLayout = findViewById(R.id.buttonLayout);
		settingsHeader = findViewById(R.id.settingsHeader);
		cardview1 = findViewById(R.id.cardview1);
		cardview2 = findViewById(R.id.cardview2);
		divider1 = findViewById(R.id.divider1);
		communityHeader = findViewById(R.id.communityHeader);
		cardview3 = findViewById(R.id.cardview3);
		divider2 = findViewById(R.id.divider2);
		legalHeader = findViewById(R.id.legalHeader);
		cardview4 = findViewById(R.id.cardview4);
		cardview5 = findViewById(R.id.cardview5);
		footerText = findViewById(R.id.footerText);
		cardview6 = findViewById(R.id.cardview6);
		textview1 = findViewById(R.id.textview1);
		textViewVersion = findViewById(R.id.textViewVersion);
		imageview1 = findViewById(R.id.imageview1);
		materialbutton1 = findViewById(R.id.materialbutton1);
		materialbutton2 = findViewById(R.id.materialbutton2);
		relativelayout98 = findViewById(R.id.relativelayout98);
		textview3 = findViewById(R.id.textview3);
		textview4 = findViewById(R.id.textview4);
		relativelayout99 = findViewById(R.id.relativelayout99);
		textview5 = findViewById(R.id.textview5);
		textview6 = findViewById(R.id.textview6);
		relativelayout100 = findViewById(R.id.relativelayout100);
		textview7 = findViewById(R.id.textview7);
		textview8 = findViewById(R.id.textview8);
		relativelayout101 = findViewById(R.id.relativelayout101);
		textview9 = findViewById(R.id.textview9);
		relativelayout102 = findViewById(R.id.relativelayout102);
		textview10 = findViewById(R.id.textview10);
		
		cardview4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				oss.setClass(getApplicationContext(), OssLicenseActivity.class);
				oss.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(oss);
			}
		});
		
		materialbutton1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				showResetDialog();
			}
		});
		
		materialbutton2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), DonateActivity.class);
				startActivity(i);
			}
		});
	}
	
	private void initializeLogic() {
		ImageView back = findViewById(R.id.ig_toolbar_back);
		TextView title = findViewById(R.id.tx_toolbar_title);
		ImageView loadFile = findViewById(R.id.ig_toolbar_load_file);
		
		Helper.applyRippleToToolbarView(back);
		back.setOnClickListener(Helper.getBackPressedClickListener(this));
		title.setText("Program Information");
		loadFile.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
		loadFile.setImageResource(R.drawable.ic_more_vert_black);
		loadFile.setVisibility(View.GONE);
		Helper.applyRippleToToolbarView(loadFile);
		_checkVer();
	}
	
	public void _a() {
	}
	private void showResetDialog() {
		LayoutInflater inflater = getLayoutInflater();
		View dialogView = inflater.inflate(R.layout.dialog_reset, null);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(dialogView);
		final AlertDialog dialog = builder.create();
		
		final RadioGroup radioGroup = dialogView.findViewById(R.id.reset_radio_group);
		Button cancelButton = dialogView.findViewById(R.id.cancel_button);
		Button yesButton = dialogView.findViewById(R.id.yes_button);
		
		cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		yesButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int selectedId = radioGroup.getCheckedRadioButtonId();
				if (selectedId == R.id.radio_reset_all_data) {
					if (FileUtil.isExistFile(FileUtil.getExternalStorageDir() + "/.blacklogics/")) {
						FileUtil.deleteFile(FileUtil.getExternalStorageDir() + "/.blacklogics/");
					}
				} else if (selectedId == R.id.radio_reset_settings) {
					// reset settings only
				}
				dialog.dismiss();
			}
		});
		
		dialog.show();
	}
	
	{
	}
	
	
	public void _checkVer() {
		try {
			// Get package info
			PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			
			// Get version name and code
			String versionName = packageInfo.versionName;
			long versionCode;
			
			// Handle different Android versions for version code
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
				versionCode = packageInfo.getLongVersionCode();
			} else {
				versionCode = packageInfo.versionCode;
			}
			
			// Format version string
			String versionString = versionName + " (" + versionCode + ")";
			
			// Check if it's alpha/beta build
			if (versionName.contains("alpha")) {
				versionString = versionName + " alpha (" + versionCode + ")";
			} else if (versionName.contains("beta")) {
				versionString = versionName + " beta (" + versionCode + ")";
			}
			
			// Set the text
			textViewVersion.setText(versionString);
			footerText.setText(versionString);
			
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			textViewVersion.setText("Version unknown");
		}
		
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