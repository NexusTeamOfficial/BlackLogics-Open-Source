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
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import io.github.rosemoe.editor.*;
import io.github.rosemoe.sora.*;
import io.github.rosemoe.sora.langs.java.*;
import io.github.rosemoe.sora.langs.textmate.*;
import io.github.rosemoe.sora.widget.CodeEditor;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.antlr.v4.runtime.*;
import org.benf.cfr.reader.*;
import org.eclipse.jdt.*;
import org.json.*;
import com.besome.blacklogics.custom.CustomSpinner;
import android.widget.NumberPicker;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import io.github.rosemoe.sora.widget.CodeEditor;
import com.besome.blacklogics.development.Complex;
import io.github.rosemoe.sora.langs.java.JavaLanguage;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.component.Magnifier;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;

public class SrcViewerActivity extends AppCompatActivity {
	
	public SharedPreferences logViewerPreferences;
	private static final String PREFERENCE_FONT_SIZE = "font_size";
	public Complex complex;
	
	private LinearLayout linear1;
	private CodeEditor editor;
	private Spinner file_spinner;
	private ImageView textSize;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.src_viewer);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		logViewerPreferences = getPreferences(Context.MODE_PRIVATE);
		complex = new Complex();
		complex.setId(getIntent().getStringExtra("sc_id"));
		linear1 = findViewById(R.id.linear1);
		editor = findViewById(R.id.editor);
		file_spinner = findViewById(R.id.file_spinner);
		textSize = findViewById(R.id.textSize);
		
		textSize.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				changeFontSizeDialog();
			}
		});
	}
	
	private void initializeLogic() {
		editor.setTextSize(getFontSizePreference());
		editor.setTypefaceText(Typeface.MONOSPACE);
		editor.setColorScheme(new EditorColorScheme());
		editor.setEditable(false);
		editor.setWordwrap(false);
		editor.getComponent(Magnifier.class).setWithinEditorForcibly(true);
		List<String> allItems = new ArrayList<>();
		allItems.addAll(complex.javaItems); // e.g. MainActivity.java
		allItems.addAll(complex.xmlItems);  // e.g. activity_main.xml
		
		// Step 2: Set adapter to spinner
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allItems);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		file_spinner.setAdapter(adapter);
		
		// Step 3: Set item selected listener
		file_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
						String item = parent.getItemAtPosition(position).toString();
						
						if (complex.javaItems.contains(item)) {
								// It's a Java file
								editor.setText(complex.getJavaContent(item));
						} else if (complex.xmlItems.contains(item)) {
								// It's an XML file
								editor.setText(complex.getXmlContent(item));
						} else {
								editor.setText("// Unknown item: " + item);
						}
				}
				
				@Override
				public void onNothingSelected(AdapterView<?> parent) {}
		});
		
	}
	
	public void _a() {
	}
	private int getFontSizePreference() {
			return logViewerPreferences.getInt(PREFERENCE_FONT_SIZE, 11);
	}
	private void changeFontSizeDialog() {
			NumberPicker picker = new NumberPicker(this);
			picker.setMinValue(10); //Must not be less than setValue(), which is currently 11 in src_viewer.xml
			picker.setMaxValue(70);
			picker.setWrapSelectorWheel(false);
			picker.setValue(getFontSizePreference());
			
			LinearLayout layout = new LinearLayout(this);
			layout.addView(picker, new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.WRAP_CONTENT,
			LinearLayout.LayoutParams.WRAP_CONTENT,
			Gravity.CENTER));
			
			new AlertDialog.Builder(this)
			.setTitle("Select font size")
			.setView(layout)
			.setPositiveButton(android.R.string.ok, (dialog, which) -> {
					logViewerPreferences.edit().putInt(PREFERENCE_FONT_SIZE, picker.getValue()).apply();
					
					editor.setTextSize((float) picker.getValue());
			})
			.setNegativeButton(android.R.string.cancel, null)
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