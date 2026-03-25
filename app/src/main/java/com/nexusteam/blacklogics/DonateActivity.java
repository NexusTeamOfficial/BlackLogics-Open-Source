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
import com.google.android.material.chip.*;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.*;
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
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.antlr.v4.runtime.*;
import org.benf.cfr.reader.*;
import org.eclipse.jdt.*;
import org.json.*;

public class DonateActivity extends AppCompatActivity {
	
	private LinearLayout linear7;
	private LinearLayout linear8;
	private View view9;
	private MaterialCardView cardview4;
	private MaterialCardView cardview8;
	private MaterialButton btn_donate_now;
	private MaterialButton btn_share;
	private LinearLayout linear12;
	private ImageView imageview3;
	private TextView textview8;
	private TextView textview9;
	private LinearLayout linear10;
	private TextView textview10;
	private TextView textview11;
	private MaterialCardView cardview5;
	private Chip chip_upi_id;
	private TextView textview12;
	private ImageView iv_qr_code;
	private LinearLayout linear14;
	private TextView textview16;
	private ChipGroup chip_group_amounts;
	private TextInputLayout til_custom_amount;
	private Chip chip_amount_10;
	private Chip chip_amount_50;
	private Chip chip_amount_100;
	private Chip chip_amount_500;
	private TextInputEditText et_custom_amount;
	private ImageView imageview4;
	private TextView textview14;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.donate);
		initialize(_savedInstanceState);
		
		MobileAds.initialize(this);
		
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear7 = findViewById(R.id.linear7);
		linear8 = findViewById(R.id.linear8);
		view9 = findViewById(R.id.view9);
		cardview4 = findViewById(R.id.cardview4);
		cardview8 = findViewById(R.id.cardview8);
		btn_donate_now = findViewById(R.id.btn_donate_now);
		btn_share = findViewById(R.id.btn_share);
		linear12 = findViewById(R.id.linear12);
		imageview3 = findViewById(R.id.imageview3);
		textview8 = findViewById(R.id.textview8);
		textview9 = findViewById(R.id.textview9);
		linear10 = findViewById(R.id.linear10);
		textview10 = findViewById(R.id.textview10);
		textview11 = findViewById(R.id.textview11);
		cardview5 = findViewById(R.id.cardview5);
		chip_upi_id = findViewById(R.id.chip_upi_id);
		textview12 = findViewById(R.id.textview12);
		iv_qr_code = findViewById(R.id.iv_qr_code);
		linear14 = findViewById(R.id.linear14);
		textview16 = findViewById(R.id.textview16);
		chip_group_amounts = findViewById(R.id.chip_group_amounts);
		til_custom_amount = findViewById(R.id.til_custom_amount);
		chip_amount_10 = findViewById(R.id.chip_amount_10);
		chip_amount_50 = findViewById(R.id.chip_amount_50);
		chip_amount_100 = findViewById(R.id.chip_amount_100);
		chip_amount_500 = findViewById(R.id.chip_amount_500);
		et_custom_amount = findViewById(R.id.et_custom_amount);
		imageview4 = findViewById(R.id.imageview4);
		textview14 = findViewById(R.id.textview14);
	}
	
	private void initializeLogic() {
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