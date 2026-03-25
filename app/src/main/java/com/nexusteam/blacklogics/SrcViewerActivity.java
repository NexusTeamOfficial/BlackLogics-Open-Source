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
import com.google.gson.*;
import com.googlecode.d2j.*;
import com.larswerkman.holocolorpicker.*;
import com.squareup.leakcanary.*;
import de.hdodenhof.circleimageview.*;
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
import com.nexusteam.blacklogics.generator.source.SourceCodeGenerator;
import com.besome.blacklogics.custom.CustomSpinner;
import android.widget.NumberPicker;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import io.github.rosemoe.sora.widget.CodeEditor;
import com.besome.blacklogics.development.Complex;
import android.os.Environment;
import io.github.rosemoe.sora.langs.java.JavaLanguage;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.component.Magnifier;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;

public class SrcViewerActivity extends AppCompatActivity {
	
	public SharedPreferences logViewerPreferences;
	private static final String PREFERENCE_FONT_SIZE = "font_size";
	public Complex complex;
	private SourceCodeGenerator codeGenerator;
	
	private LinearLayout linear1;
	private CodeEditor editor;
	private Spinner file_spinner;
	private ImageView textSize;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.src_viewer);
		initialize(_savedInstanceState);
		
		MobileAds.initialize(this);
		
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		logViewerPreferences = getPreferences(Context.MODE_PRIVATE);
		complex = new Complex();
		complex.setC(this);
		complex.setId(getIntent().getStringExtra("sc_id"));
		codeGenerator = new SourceCodeGenerator(this, getIntent().getStringExtra("sc_id"));
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
		complex.setC(this);
		complex.refreshData();
		editor.setTextSize(getFontSizePreference());
		editor.setTypefaceText(Typeface.MONOSPACE);
		editor.setColorScheme(new EditorColorScheme());
		editor.setEditable(false);
		editor.setWordwrap(false);
		editor.getComponent(Magnifier.class).setWithinEditorForcibly(true);
		List<String> allItems = new ArrayList<>();
		
		// Populate Java and XML items, ensuring correct extensions
		for (String javaName : complex.javaItems) {
			allItems.add(javaName.endsWith(".java") ? javaName : javaName + ".java");
		}
		for (String xmlName : complex.xmlItems) {
			allItems.add(xmlName.endsWith(".xml") ? xmlName : xmlName + ".xml");
		}
		
		// Handle empty case
		if (allItems.isEmpty()) {
			Log.e("SrcViewer", "No Java or XML files found for project ID: " + complex.sc_id);
			allItems.add("MainActivity.java");
			allItems.add("activity_main.xml");
			editor.setText("// No files found. Check if logic file exists at: " + 
			Environment.getExternalStorageDirectory().getAbsolutePath() + "/.blacklogics/data/" + complex.sc_id + "/logic");
			showMessage("No files found for project ID: " + complex.sc_id);
		}
		
		// Set spinner adapter
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allItems);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		file_spinner.setAdapter(adapter);
		
		// Load first item if available
		if (!allItems.isEmpty()) {
			file_spinner.setSelection(0);
			loadFileContent(allItems.get(0));
		}
		
		// Set spinner listener
		file_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String item = parent.getItemAtPosition(position).toString();
				loadFileContent(item);
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				editor.setText("// No file selected.");
			}
		});
	}
	
	public void _a() {
	}
	private int getFontSizePreference() {
		return logViewerPreferences.getInt(PREFERENCE_FONT_SIZE, 11);
	}
	private void changeFontSizeDialog() {
		final NumberPicker picker = new NumberPicker(this);
		picker.setMinValue(10); // Must not be less than setValue(), currently 11 in src_viewer.xml
		picker.setMaxValue(70);
		picker.setWrapSelectorWheel(false);
		picker.setValue(getFontSizePreference());
		
		LinearLayout layout = new LinearLayout(this);
		layout.addView(picker, new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.WRAP_CONTENT,
		LinearLayout.LayoutParams.WRAP_CONTENT,
		Gravity.CENTER
		));
		
		new AlertDialog.Builder(this)
		.setTitle(R.string.select_text_size)
		.setView(layout)
		.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				logViewerPreferences.edit()
				.putInt(PREFERENCE_FONT_SIZE, picker.getValue())
				.apply();
				
				editor.setTextSize((float) picker.getValue());
			}
		})
		.setNegativeButton(android.R.string.cancel, null)
		.show();
	}
	
	private void loadFileContent(String item) {
		if (item == null || item.trim().isEmpty()) return;
		
		item = item.trim();
		String cleanName = item.replace(".java", "").replace(".xml", "").trim();
		
		if (item.endsWith(".java")) {
			// For Java files, cleanName is the class name (e.g., "MainActivity")
			String layoutName = getLayoutName(cleanName); // This returns XML filename
			
			if (layoutName != null && !layoutName.isEmpty()) {
				String javaContent = codeGenerator.generateCompleteJavaCode(cleanName, layoutName);
				if (javaContent != null && !javaContent.isEmpty()) {
					editor.setEditorLanguage(new JavaLanguage());
					editor.setText(javaContent);
				}
			}
			
		} else if (item.endsWith(".xml")) {
			// For XML files, we need the Java class name, not the XML filename
			String javaClassName = findJavaClassForXml(cleanName);
			if (javaClassName != null) {
				String xmlContent = codeGenerator.getLayoutXml(javaClassName);
				if (xmlContent != null && !xmlContent.isEmpty()) {
					editor.setText(xmlContent);
				} else {
					editor.setText("// XML content is empty for: " + cleanName + " (using class: " + javaClassName + ")");
				}
			} else {
				editor.setText("// No Java class found for XML: " + cleanName);
			}
		}
	}
	
	/**
 * Find Java class name that corresponds to an XML filename
 */
	private String findJavaClassForXml(String xmlFileName) {
		if (complex.javaItems == null || complex.javaItems.isEmpty()) return null;
		
		// First, try to find matching Java class
		for (String javaName : complex.javaItems) {
			String cleanJavaName = javaName.replace(".java", "").trim();
			
			// Common patterns: activity_main.xml -> MainActivity
			if (xmlFileName.equalsIgnoreCase("activity_" + cleanJavaName.toLowerCase()) ||
			xmlFileName.equalsIgnoreCase("fragment_" + cleanJavaName.toLowerCase()) ||
			xmlFileName.equalsIgnoreCase("dialog_" + cleanJavaName.toLowerCase()) ||
			xmlFileName.toLowerCase().contains(cleanJavaName.toLowerCase()) ||
			cleanJavaName.toLowerCase().contains(xmlFileName.toLowerCase())) {
				return cleanJavaName;
			}
		}
		
		// Fallback: return first Java class
		return complex.javaItems.get(0).replace(".java", "").trim();
	}
	
	/**
 * Finds the real XML layout name corresponding to a Java class.
 */
	private String getLayoutName(String javaClassName) {
		if (complex.xmlItems == null || complex.xmlItems.isEmpty()) return null;
		
		for (String xmlItem : complex.xmlItems) {
			String cleanXmlName = xmlItem.replace(".xml", "").trim();
			// Check if the XML name matches class name convention
			if (cleanXmlName.equalsIgnoreCase(javaClassName) || 
			cleanXmlName.toLowerCase().contains(javaClassName.toLowerCase()) ||
			("activity_" + javaClassName.toLowerCase()).equals(cleanXmlName.toLowerCase()) ||
			("fragment_" + javaClassName.toLowerCase()).equals(cleanXmlName.toLowerCase())) {
				return cleanXmlName;
			}
		}
		
		// fallback: return first XML layout if nothing matches
		return complex.xmlItems.isEmpty() ? null : complex.xmlItems.get(0).replace(".xml", "").trim();
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