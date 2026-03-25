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
import android.view.View;
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
import com.google.android.material.textfield.*;
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
import com.droiddevcoach.tools.KeyStoreHelper;

public class NewKeyStoreActivity extends AppCompatActivity {
	
	private ScrollView vscroll;
	private LinearLayout bottom;
	private LinearLayout container;
	private TextView title;
	private TextView export_path;
	private TextView textview5;
	private TextInputLayout textinputlayout19;
	private TextInputLayout textinputlayout20;
	private TextInputLayout textinputlayout21;
	private TextInputLayout textinputlayout22;
	private TextView textview6;
	private LinearLayout linear6;
	private LinearLayout linear7;
	private TextInputLayout textinputlayout27;
	private AdLayout adLayout;
	private EditText edittext_name;
	private EditText edittext_password;
	private EditText edittext_alias;
	private EditText edittext_validity;
	private TextInputLayout textinputlayout23;
	private TextInputLayout textinputlayout24;
	private EditText edittext_org;
	private EditText edittext_orgunit;
	private TextInputLayout textinputlayout25;
	private TextInputLayout textinputlayout26;
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
		
		MobileAds.initialize(this);
		
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		vscroll = findViewById(R.id.vscroll);
		bottom = findViewById(R.id.bottom);
		container = findViewById(R.id.container);
		title = findViewById(R.id.title);
		export_path = findViewById(R.id.export_path);
		textview5 = findViewById(R.id.textview5);
		textinputlayout19 = findViewById(R.id.textinputlayout19);
		textinputlayout20 = findViewById(R.id.textinputlayout20);
		textinputlayout21 = findViewById(R.id.textinputlayout21);
		textinputlayout22 = findViewById(R.id.textinputlayout22);
		textview6 = findViewById(R.id.textview6);
		linear6 = findViewById(R.id.linear6);
		linear7 = findViewById(R.id.linear7);
		textinputlayout27 = findViewById(R.id.textinputlayout27);
		adLayout = findViewById(R.id.adLayout);
		edittext_name = findViewById(R.id.edittext_name);
		edittext_password = findViewById(R.id.edittext_password);
		edittext_alias = findViewById(R.id.edittext_alias);
		edittext_validity = findViewById(R.id.edittext_validity);
		textinputlayout23 = findViewById(R.id.textinputlayout23);
		textinputlayout24 = findViewById(R.id.textinputlayout24);
		edittext_org = findViewById(R.id.edittext_org);
		edittext_orgunit = findViewById(R.id.edittext_orgunit);
		textinputlayout25 = findViewById(R.id.textinputlayout25);
		textinputlayout26 = findViewById(R.id.textinputlayout26);
		edittext_city = findViewById(R.id.edittext_city);
		edittext_state = findViewById(R.id.edittext_state);
		edittext_country = findViewById(R.id.edittext_country);
		cancel = findViewById(R.id.cancel);
		create = findViewById(R.id.create);
		
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
	}
	
	private void initializeLogic() {
		adLayout.loadBannerAd();
		create.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String commonName = edittext_name.getText().toString().trim();
				String keyPassword = edittext_password.getText().toString().trim();
				String alias = edittext_alias.getText().toString().trim();
				String validityStr = edittext_validity.getText().toString().trim();
				String org = edittext_org.getText().toString().trim();
				String orgUnit = edittext_orgunit.getText().toString().trim();
				String city = edittext_city.getText().toString().trim();
				String state = edittext_state.getText().toString().trim();
				String country = edittext_country.getText().toString().trim();
				
				if (commonName.isEmpty() || keyPassword.isEmpty() || alias.isEmpty() || validityStr.isEmpty()) {
					showMessage("Please fill all required fields!");
					return;
				}
				
				int validity = 365;
				try {
					validity = Integer.parseInt(validityStr);
				} catch (NumberFormatException e) {
					showMessage("Invalid validity!");
					return;
				}
				
				try {
					File keystoreDir = new File(Environment.getExternalStorageDirectory(), "blacklogics/keystore");
					if (!keystoreDir.exists()) keystoreDir.mkdirs();
					
					File keystoreFile = new File(keystoreDir, "release.jks");
					
					KeyStoreHelper.Builder builder = new KeyStoreHelper.Builder()
					.setAlg(KeyStoreHelper.Algorithm.RSA)
					.setSize(KeyStoreHelper.Size.SIZE_2048)
					.setSigAlg(KeyStoreHelper.SigAlgorithm.SHA256withRSA)
					.setType(KeyStoreHelper.Type.JKS)
					.setAlias(alias)
					.setKeypass(keyPassword)
					.setStorepass(keyPassword)
					.setCommonName(commonName)
					.setOrganizationName(org)
					.setOrganizationUnit(orgUnit)
					.setCityOrLocality(city)
					.setStateName(state)
					.setCountryCode(country)
					.setValidity(validity)
					.setOutputFile(keystoreFile);
					
					KeyStoreHelper.generate(builder);
					
					new com.google.android.material.dialog.MaterialAlertDialogBuilder(v.getContext())
					.setTitle("Certificate Created!")
					.setMessage("Your keystore has been successfully created at:\n" + keystoreFile.getAbsolutePath())
					.setPositiveButton("OK", null)
					.show();
					
				} catch (Exception e) {
					e.printStackTrace();
					showMessage("Error creating keystore: " + e.getMessage());
				}
			}
		});
		
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