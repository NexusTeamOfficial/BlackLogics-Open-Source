package com.besome.blacklogics;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import androidx.recyclerview.*;
import androidx.viewpager.*;
import androidx.viewpager2.*;
import com.besome.sketch.*;
import com.bumptech.glide.*;
import com.bumptech.glide.gifdecoder.*;
import com.example.myapp.*;
import com.github.angads25.filepicker.*;
import com.google.android.material.*;
import com.google.android.material.button.*;
import com.google.android.material.textfield.*;
import com.google.gson.*;
import com.googlecode.d2j.*;
import com.larswerkman.holocolorpicker.*;
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

public class NewKeyStoreActivity extends AppCompatActivity {
	
	private LinearLayout linear6;
	private LinearLayout bottom;
	private LinearLayout nav;
	private ScrollView vscroll;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private TextView textview5;
	private TextInputLayout textinputlayout4;
	private TextInputLayout textinputlayout2;
	private TextInputLayout textinputlayout1;
	private TextInputLayout textinputlayout3;
	private TextView textview3;
	private LinearLayout linear7;
	private LinearLayout linear8;
	private TextInputLayout textinputlayout9;
	private TextView textview1;
	private TextView textview4;
	private EditText edittext_name;
	private EditText edittext_password;
	private EditText edittext_alias;
	private EditText edittext_validity;
	private TextInputLayout textinputlayout6;
	private TextInputLayout textinputlayout5;
	private EditText edittext_org;
	private EditText edittext_orgunit;
	private LinearLayout linear9;
	private LinearLayout linear10;
	private EditText edittext_city;
	private EditText edittext_state;
	private EditText edittext_country;
	private MaterialButton cancel;
	private MaterialButton create;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.new_key_store);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear6 = findViewById(R.id.linear6);
		bottom = findViewById(R.id.bottom);
		nav = findViewById(R.id.nav);
		vscroll = findViewById(R.id.vscroll);
		linear2 = findViewById(R.id.linear2);
		linear3 = findViewById(R.id.linear3);
		textview5 = findViewById(R.id.textview5);
		textinputlayout4 = findViewById(R.id.textinputlayout4);
		textinputlayout2 = findViewById(R.id.textinputlayout2);
		textinputlayout1 = findViewById(R.id.textinputlayout1);
		textinputlayout3 = findViewById(R.id.textinputlayout3);
		textview3 = findViewById(R.id.textview3);
		linear7 = findViewById(R.id.linear7);
		linear8 = findViewById(R.id.linear8);
		textinputlayout9 = findViewById(R.id.textinputlayout9);
		textview1 = findViewById(R.id.textview1);
		textview4 = findViewById(R.id.textview4);
		edittext_name = findViewById(R.id.edittext_name);
		edittext_password = findViewById(R.id.edittext_password);
		edittext_alias = findViewById(R.id.edittext_alias);
		edittext_validity = findViewById(R.id.edittext_validity);
		textinputlayout6 = findViewById(R.id.textinputlayout6);
		textinputlayout5 = findViewById(R.id.textinputlayout5);
		edittext_org = findViewById(R.id.edittext_org);
		edittext_orgunit = findViewById(R.id.edittext_orgunit);
		linear9 = findViewById(R.id.linear9);
		linear10 = findViewById(R.id.linear10);
		edittext_city = findViewById(R.id.edittext_city);
		edittext_state = findViewById(R.id.edittext_state);
		edittext_country = findViewById(R.id.edittext_country);
		cancel = findViewById(R.id.cancel);
		create = findViewById(R.id.create);
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