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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
import com.nexusteam.blacklogics.generator.source.AndroidManifestGenerator;
import com.nexusteam.blacklogics.ui.SourceCodeDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textfield.TextInputEditText;

public class AndroidManifestEditorActivity extends AppCompatActivity {
	
	private AndroidManifestGenerator androidManifestGenerator;
	private String sc_id = "";
	
	private LinearLayout mainBg;
	private LinearLayout linear1;
	private LinearLayout linear2;
	private ImageView imageview1;
	private TextView textview8;
	private ImageView imageview2;
	private CardView cardview1;
	private TextView textview19;
	private LinearLayout linear20;
	private MaterialButton button1;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private LinearLayout div1;
	private LinearLayout linear9;
	private LinearLayout div2;
	private LinearLayout change_launcher;
	private LinearLayout div3;
	private LinearLayout linear15;
	private LinearLayout div4;
	private LinearLayout linear18;
	private ImageView imageview3;
	private LinearLayout linear5;
	private TextView textview9;
	private TextView textview10;
	private ImageView imageview4;
	private LinearLayout linear10;
	private TextView textview11;
	private TextView textview12;
	private ImageView imageview5;
	private LinearLayout linear13;
	private TextView textview13;
	private TextView textview14;
	private ImageView imageview6;
	private LinearLayout linear16;
	private TextView textview15;
	private TextView textview16;
	private ImageView imageview7;
	private LinearLayout linear19;
	private TextView textview17;
	private TextView textview18;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.android_manifest_editor);
		initialize(_savedInstanceState);
		
		MobileAds.initialize(this);
		
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		sc_id = getIntent().getStringExtra("sc_id");
		mainBg = findViewById(R.id.mainBg);
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		imageview1 = findViewById(R.id.imageview1);
		textview8 = findViewById(R.id.textview8);
		imageview2 = findViewById(R.id.imageview2);
		cardview1 = findViewById(R.id.cardview1);
		textview19 = findViewById(R.id.textview19);
		linear20 = findViewById(R.id.linear20);
		button1 = findViewById(R.id.button1);
		linear3 = findViewById(R.id.linear3);
		linear4 = findViewById(R.id.linear4);
		div1 = findViewById(R.id.div1);
		linear9 = findViewById(R.id.linear9);
		div2 = findViewById(R.id.div2);
		change_launcher = findViewById(R.id.change_launcher);
		div3 = findViewById(R.id.div3);
		linear15 = findViewById(R.id.linear15);
		div4 = findViewById(R.id.div4);
		linear18 = findViewById(R.id.linear18);
		imageview3 = findViewById(R.id.imageview3);
		linear5 = findViewById(R.id.linear5);
		textview9 = findViewById(R.id.textview9);
		textview10 = findViewById(R.id.textview10);
		imageview4 = findViewById(R.id.imageview4);
		linear10 = findViewById(R.id.linear10);
		textview11 = findViewById(R.id.textview11);
		textview12 = findViewById(R.id.textview12);
		imageview5 = findViewById(R.id.imageview5);
		linear13 = findViewById(R.id.linear13);
		textview13 = findViewById(R.id.textview13);
		textview14 = findViewById(R.id.textview14);
		imageview6 = findViewById(R.id.imageview6);
		linear16 = findViewById(R.id.linear16);
		textview15 = findViewById(R.id.textview15);
		textview16 = findViewById(R.id.textview16);
		imageview7 = findViewById(R.id.imageview7);
		linear19 = findViewById(R.id.linear19);
		textview17 = findViewById(R.id.textview17);
		textview18 = findViewById(R.id.textview18);
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				new GenerateManifestTask().execute();
			}
		});
		
		change_launcher.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				TextInputLayout layout = new TextInputLayout(AndroidManifestEditorActivity.this);
				layout.setLayoutParams(new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT
				));
				layout.setPadding(40, 20, 40, 0);
				
				final TextInputEditText edit = new TextInputEditText(AndroidManifestEditorActivity.this);
				edit.setHint("Enter launcher activity");
				edit.setText(androidManifestGenerator.getLauncherActivity());
				
				layout.addView(edit);
				
				new MaterialAlertDialogBuilder(AndroidManifestEditorActivity.this)
				.setTitle("Set Launcher Activity")
				.setView(layout)
				.setPositiveButton("Save", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String launcher = edit.getText().toString().trim();
						
						if(!launcher.isEmpty()){
							androidManifestGenerator.addLauncherActivity(launcher);
							//showMessage("Launcher activity updated");
						}
					}
				})
				.setNegativeButton("Cancel", null)
				.show();
				
			}
		});
		
		linear18.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Intent intent = new Intent(AndroidManifestEditorActivity.this, CustomAttributesEditorActivity.class);
				intent.putExtra("sc_id", sc_id);
				startActivity(intent);
			}
		});
	}
	
	private void initializeLogic() {
		androidManifestGenerator = new AndroidManifestGenerator();
		androidManifestGenerator.load(this, sc_id);
	}
	
	public void _task() {
	}
	private class GenerateManifestTask extends AsyncTask<Void, Void, String> {
		
		ProgressDialog progressDialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(AndroidManifestEditorActivity.this);
			progressDialog.setMessage("Generating Manifest...");
			progressDialog.setCancelable(false);
			progressDialog.show();
		}
		
		@Override
		protected String doInBackground(Void... params) {
			return androidManifestGenerator.generate();
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			
			if(progressDialog != null && progressDialog.isShowing()){
				progressDialog.dismiss();
			}
			
			SourceCodeDialog.show(AndroidManifestEditorActivity.this, result);
		}
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