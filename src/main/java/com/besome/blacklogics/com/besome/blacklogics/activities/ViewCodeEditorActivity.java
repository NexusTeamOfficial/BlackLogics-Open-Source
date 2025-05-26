package com.besome.blacklogics.activities;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.*;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.multidex.*;
import androidx.viewpager2.*;
import com.besome.blacklogics.R;
import com.besome.blacklogics.ViewEditorFragmentActivity;
import com.besome.blacklogics.development.Complex;
import com.besome.blacklogics.beans.ProjectActivityBean;
import com.besome.blacklogics.FileUtil;
import com.besome.sketch.*;
import com.bumptech.glide.*;
import com.bumptech.glide.gifdecoder.*;
import com.github.angads25.filepicker.*;
import com.google.android.material.*;
import com.google.gson.*;
import com.googlecode.d2j.*;
import com.larswerkman.holocolorpicker.*;
import coyamo.visualxml.ViewActivity;
import coyamo.visualxml.lib.proxy.ProxyResources;
import coyamo.visualxml.ui.adapter.ResourcePagerAdapter;
import coyamo.visualxml.ui.adapter.SignAdapter;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import io.github.rosemoe.sora.langs.java.*;
import io.github.rosemoe.sora.langs.textmate.*;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.component.EditorAutoCompletion;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import io.github.rosemoe.sora.widget.schemes.SchemeDarcula;
import io.github.rosemoe.sora.widget.schemes.SchemeEclipse;
import io.github.rosemoe.sora.widget.schemes.SchemeGitHub;
import io.github.rosemoe.sora.widget.schemes.SchemeNotepadXX;
import io.github.rosemoe.sora.widget.schemes.SchemeVS2019;
import io.github.rosemoe.sora.langs.textmate.TextMateLanguage;
/*
import io.github.rosemoe.sora.langs.textmate.registry.TextMateRegistry;
import io.github.rosemoe.sora.langs.textmate.registry.TextMateGrammar;
import io.github.rosemoe.sora.langs.textmate.registry.TextMateTheme;
*/
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.schemes.SchemeDarcula;
import io.github.rosemoe.sora.widget.schemes.SchemeDarcula;
import mod.jbk.code.CodeEditorLanguages;
import mod.jbk.code.CodeEditorColorSchemes;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.antlr.v4.runtime.*;
import org.benf.cfr.reader.*;
import org.eclipse.jdt.*;
import org.json.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.xml.sax.InputSource;

public class ViewCodeEditorActivity extends AppCompatActivity {
	static {
		AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
	}
	private ProjectActivityBean activityBean;
	private CodeEditor editor;
	private LinearLayout toolbar;
	private LinearLayout linear9;
	private ImageView menu_view_undo;
	private ImageView menu_view_redo;
	private ImageView save;
	private ImageView more;
	private TextView textview9;
	private TextView acName;
	private Complex complex;
	private String sc_id;
	private String layoutName;
	private String pkgName;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.view_code_editor);
		initialize(_savedInstanceState);
		ProxyResources.init(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		editor = findViewById(R.id.editor);
		toolbar = findViewById(R.id.toolbar);
		linear9 = findViewById(R.id.linear9);
		menu_view_undo = findViewById(R.id.menu_view_undo);
		menu_view_redo = findViewById(R.id.menu_view_redo);
		save = findViewById(R.id.save);
		more = findViewById(R.id.more);
		textview9 = findViewById(R.id.textview9);
		acName = findViewById(R.id.acName);
		
		// Initialize Complex and retrieve intent data
		complex = new Complex();
		sc_id = getIntent().getStringExtra("sc_id");
		layoutName = getIntent().getStringExtra("layoutName");
		pkgName = getIntent().getStringExtra("pkgName");
		complex.setId(sc_id);
		complex.setPkgName(pkgName);
		
		// Set up toolbar actions
		menu_view_undo.setOnClickListener(v -> editor.undo());
		menu_view_redo.setOnClickListener(v -> editor.redo());
		
		save.setOnClickListener(v -> {
			if (saveView()) {
				showMessage("XML saved successfully!");
				// Notify DesignActivity of the save
				Intent result = new Intent();
				result.putExtra("xmlContent", editor.getText().toString());
				setResult(RESULT_OK, result);
			} else {
				showMessage("Failed to save XML!");
			}
		});
		
		more.setOnClickListener(v -> {
			PopupMenu popup = new PopupMenu(this, more);
			popup.getMenuInflater().inflate(R.menu.editor_more_menu, popup.getMenu());
			popup.setOnMenuItemClickListener(item -> {
				switch (item.getItemId()) {
					case R.id.action_format:
					formatCode();
					return true;
					case R.id.action_undo_all:
					editor.undo();
					return true;
					case R.id.action_redo_all:
					editor.redo();
					return true;
					case R.id.action_layout_view:
					layoutPreview();
					return true;
					default:
					return false;
				}
			});
			popup.show();
		});
	}
	
	private void initializeLogic() {
		
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
			activityBean = getIntent().getParcelableExtra("activityBean", ProjectActivityBean.class);
		} else {
			activityBean = getIntent().getParcelableExtra("activityBean");
		}
		
		if (activityBean == null) {
			throw new IllegalStateException("No activity data received");
		}
		// Configure CodeEditor
		editor.setTypefaceText(Typeface.MONOSPACE);
		// Load the XML grammar and theme
		/*
		TextMateGrammar xmlGrammar = TextMateRegistry.getInstance().loadGrammar("source.xml");
		TextMateTheme theme = TextMateRegistry.getInstance().loadTheme("darcula");
		
		// Set the language and theme
		editor.setEditorLanguage(new TextMateLanguage(xmlGrammar));*/
		editor.setColorScheme(new SchemeDarcula());
		
		editor.setTextSize(14);
		editor.setEditable(true);
		editor.setLineNumberEnabled(true);
		//	editor.setAutoCompletionEnabled(true);
		
		// Load existing XML content from Complex
		String existingXml = activityBean.getXmlCode();
		if (existingXml != null && !existingXml.isEmpty()) {
			editor.setText(existingXml);
		} else {
			// Set default XML if none exists
			String defaultXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
			"<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
			"    android:layout_width=\"match_parent\"\n" +
			"    android:layout_height=\"match_parent\"\n" +
			"    android:orientation=\"vertical\">\n\n" +
			"</LinearLayout>";
			editor.setText(defaultXml);
			complex.setXmlCode(layoutName, defaultXml);
		}
		
		// Set activity name in toolbar
		acName.setText(layoutName + ".xml");
	}
	
	public boolean saveView() {
		try {
			String xmlContent = editor.getText().toString().trim();
			
			// Check if content is empty
			if (xmlContent.isEmpty()) {
				showMessage("XML content is empty!");
				return false;
			}
			
			// Validate XML using simple parser
			if (!isValidXml(xmlContent)) {
				showMessage("Invalid XML format! Please check the syntax.");
				return false;
			}
			
			// Save XML content to Complex
			complex.setXmlCode(layoutName, xmlContent);
			
			// Save to layouts.json
			String filePath = new File(ViewEditorFragmentActivity.projectPath, "layouts.json").getAbsolutePath();
			JSONObject layoutJson = new JSONObject();
			layoutJson.put("layoutName", layoutName);
			layoutJson.put("xml", xmlContent);
			layoutJson.put("timestamp", System.currentTimeMillis());
			
			FileUtil.writeFile(filePath, layoutJson.toString(2));
			showMessage("XML saved successfully!");
			
			// Notify DesignActivity of the save
			Intent result = new Intent();
			result.putExtra("xmlContent", xmlContent);
			setResult(RESULT_OK, result);
			
			return true;
		} catch (Exception e) {
			showMessage("Save Error: " + e.getMessage());
			return false;
		}
	}
	
	// Updated isValidXml method with simpler XML parsing
	private boolean isValidXml(String xmlContent) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource inputSource = new InputSource(new StringReader(xmlContent));
			
			// Parse XML with basic validation
			Document doc = builder.parse(inputSource);
			doc.getDocumentElement().normalize();
			
			// Additional checks for Android XML requirements
			if (!xmlContent.contains("xmlns:android=\"http://schemas.android.com/apk/res/android\"")) {
				return false; // Missing Android namespace
			}
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	// Helper method to create JSON object with widget properties
	private JSONObject createProjectJson(String xmlContent) throws Exception {
		JSONObject projectObject = new JSONObject();
		projectObject.put("project_name", sc_id);
		projectObject.put("package_name", pkgName);
		projectObject.put("layout_name", layoutName);
		
		// Parse XML to extract widget properties
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource inputSource = new InputSource(new StringReader(xmlContent));
		Document document = builder.parse(inputSource);
		document.getDocumentElement().normalize();
		
		JSONArray widgetsArray = new JSONArray();
		// Recursively process all elements (widgets)
		processElement(document.getDocumentElement(), widgetsArray);
		
		// Store widgets in activity data
		JSONObject activityObject = new JSONObject();
		activityObject.put("activity_name", formatToCamelCase(layoutName));
		activityObject.put("layout_name", layoutName);
		activityObject.put("is_main_activity", false); // Adjust based on your logic
		activityObject.put("widgets", widgetsArray);
		
		JSONArray activitiesArray = new JSONArray();
		activitiesArray.put(activityObject);
		projectObject.put("activities", activitiesArray);
		
		return projectObject;
	}
	
	// Helper method to process XML elements and extract widget properties
	private void processElement(Element element, JSONArray widgetsArray) throws JSONException {
		JSONObject widgetObject = new JSONObject();
		String tagName = element.getTagName();
		
		// Map XML tag to widget type
		widgetObject.put("name_s", tagName);
		
		// Extract common attributes
		if (element.hasAttribute("android:id")) {
			String id = element.getAttribute("android:id").replace("@+id/", "").replace("@id/", "");
			widgetObject.put("id", id);
		}
		widgetObject.put("width", element.getAttribute("android:layout_width"));
		widgetObject.put("height", element.getAttribute("android:layout_height"));
		
		// Margins
		if (element.hasAttribute("android:layout_marginLeft")) {
			widgetObject.put("margin_left", parseDimension(element.getAttribute("android:layout_marginLeft")));
		}
		if (element.hasAttribute("android:layout_marginTop")) {
			widgetObject.put("margin_top", parseDimension(element.getAttribute("android:layout_marginTop")));
		}
		if (element.hasAttribute("android:layout_marginRight")) {
			widgetObject.put("margin_right", parseDimension(element.getAttribute("android:layout_marginRight")));
		}
		if (element.hasAttribute("android:layout_marginBottom")) {
			widgetObject.put("margin_bottom", parseDimension(element.getAttribute("android:layout_marginBottom")));
		}
		
		// Padding
		if (element.hasAttribute("android:paddingLeft")) {
			widgetObject.put("padding_left", parseDimension(element.getAttribute("android:paddingLeft")));
		}
		if (element.hasAttribute("android:paddingTop")) {
			widgetObject.put("padding_top", parseDimension(element.getAttribute("android:paddingTop")));
		}
		if (element.hasAttribute("android:paddingRight")) {
			widgetObject.put("padding_right", parseDimension(element.getAttribute("android:paddingRight")));
		}
		if (element.hasAttribute("android:paddingBottom")) {
			widgetObject.put("padding_bottom", parseDimension(element.getAttribute("android:paddingBottom")));
		}
		
		// Text-related properties (for TextView, EditText, Button, etc.)
		if (tagName.equals("TextView") || tagName.equals("EditText") || tagName.equals("Button")) {
			if (element.hasAttribute("android:text")) {
				widgetObject.put("text", element.getAttribute("android:text"));
			}
			if (element.hasAttribute("android:textSize")) {
				widgetObject.put("text_size", parseDimension(element.getAttribute("android:textSize")));
			}
			if (element.hasAttribute("android:textColor")) {
				widgetObject.put("text_color", parseColor(element.getAttribute("android:textColor")));
			}
			if (element.hasAttribute("android:gravity")) {
				widgetObject.put("gravity", element.getAttribute("android:gravity"));
			}
		}
		
		// ImageView-specific properties
		if (tagName.equals("ImageView")) {
			if (element.hasAttribute("android:src")) {
				String src = element.getAttribute("android:src");
				if (src.startsWith("@drawable/")) {
					widgetObject.put("image_path", src.replace("@drawable/", ""));
				}
			}
			if (element.hasAttribute("android:scaleType")) {
				widgetObject.put("scale_type", element.getAttribute("android:scaleType"));
			}
		}
		
		// WebView-specific properties
		if (tagName.equals("WebView")) {
			widgetObject.put("is_webview", true);
		}
		
		// Background color
		if (element.hasAttribute("android:background")) {
			String background = element.getAttribute("android:background");
			if (background.startsWith("#")) {
				widgetObject.put("background_color", parseColor(background));
			}
		}
		
		// Add widget to array
		widgetsArray.put(widgetObject);
		
		// Process child elements
		NodeList children = element.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			if (children.item(i) instanceof Element) {
				processElement((Element) children.item(i), widgetsArray);
			}
		}
	}
	
	// Helper method to parse dimension (e.g., "16dp" -> 16)
	private int parseDimension(String dimension) {
		try {
			if (dimension.endsWith("dp") || dimension.endsWith("sp")) {
				return Integer.parseInt(dimension.replaceAll("[^0-9]", ""));
			}
			return 0;
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	
	// Helper method to parse color (e.g., "#FF0000" or "@color/red")
	private String parseColor(String color) {
		if (color.startsWith("#")) {
			return color; // Return hex color directly
		} else if (color.startsWith("@color/")) {
			return color.replace("@color/", ""); // Return color resource name
		}
		return "";
	}
	
	// Helper method to save JSON to file
	private void saveProjectJson(JSONObject projectObject) {
		try {
			String savePath = ViewEditorFragmentActivity.projectPath + "/saved_project.json";
			//TheBlockLogicsUtil.writeFile(savePath, TheBlockLogicsUtil.encodeToBase64(projectObject.toString()));
			//	String savePath = getFilesDir() + "/projects/" + sc_id + "/saved_project.json";
			File file = new File(savePath);
			file.getParentFile().mkdirs();
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(projectObject.toString().getBytes());
			fos.close();
		} catch (Exception e) {
			showMessage("JSON Save Error: " + e.getMessage());
		}
	}
	
	private void formatCode() {
		try {
			String xmlContent = editor.getText().toString();
			if (!xmlContent.isEmpty()) {
				// Basic XML formatting (indentation)
				String formatted = formatXml(xmlContent);
				editor.setText(formatted);
				showMessage("Code formatted!");
			}
		} catch (Exception e) {
			showMessage("Formatting error: " + e.getMessage());
		}
	}
	
	private String formatXml(String xml) {
		// Simple XML formatting (add indentation)
		StringBuilder formatted = new StringBuilder();
		int indentLevel = 0;
		String[] lines = xml.split("\n");
		for (String line : lines) {
			line = line.trim();
			if (line.isEmpty()) continue;
			
			if (line.startsWith("</")) {
				indentLevel--;
			}
			
			for (int i = 0; i < indentLevel; i++) {
				formatted.append("    ");
			}
			formatted.append(line).append("\n");
			
			if (line.startsWith("<") && !line.startsWith("</") && !line.endsWith("/>")) {
				indentLevel++;
			}
		}
		return formatted.toString();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// Save content before destroying
		saveView();
	}
	
	public void layoutPreview() {
		Intent i = new Intent(ViewCodeEditorActivity.this, ViewActivity.class);
		i.putExtra("xml", editor.getText().toString());
		startActivity(i);
	}
	
	private String formatToCamelCase(String input) {
		StringBuilder result = new StringBuilder();
		String[] parts = input.split("_");
		for (String part : parts) {
			if (!part.isEmpty()) {
				result.append(part.substring(0, 1).toUpperCase())
				.append(part.substring(1));
			}
		}
		return result.toString();
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
			_result.add((double) _arr.keyAt(_iIdx));
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
