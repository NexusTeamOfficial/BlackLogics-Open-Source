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
import com.nexusteam.blacklogics.R;
import com.nexusteam.blacklogics.ViewEditorFragmentActivity;
import com.besome.blacklogics.development.Complex;
import com.besome.blacklogics.beans.ProjectActivityBean;
import com.nexusteam.blacklogics.FileUtil;
import com.nexusteam.internal.*;
import com.bumptech.glide.*;

import com.github.angads25.filepicker.*;
import com.google.android.material.*;
import com.google.gson.*;
import com.googlecode.d2j.*;
import com.shapun.layouteditor.ViewEditor;
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
import com.nexusteam.blacklogics.editor.layout.storage.LayoutStorage;
/*
import io.github.rosemoe.sora.langs.textmate.registry.TextMateRegistry;
import io.github.rosemoe.sora.langs.textmate.registry.TextMateGrammar;
import io.github.rosemoe.sora.langs.textmate.registry.TextMateTheme;
*/
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.schemes.SchemeDarcula;
import io.github.rosemoe.sora.widget.schemes.SchemeDarcula;
import com.nexusteam.blacklogics.editor.CodeEditorLanguages;
import com.nexusteam.blacklogics.editor.CodeEditorColorSchemes;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.antlr.v4.runtime.*;
import org.benf.cfr.reader.*;
import org.eclipse.jdt.*;
import org.json.*; // Added for JSON handling
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
    private ViewEditor viewEditor;
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
        

        viewEditor = new ViewEditor(this);
        complex = new Complex();
        sc_id = getIntent().getStringExtra("sc_id");
        viewEditor.setScId(getIntent().getStringExtra("sc_id"));
        viewEditor.setPath(getIntent().getStringExtra("projectPath"));
        layoutName = getIntent().getStringExtra("layoutName");
        pkgName = getIntent().getStringExtra("pkgName");
        complex.setId(sc_id);
        complex.setPkgName(pkgName);
        
        
        menu_view_undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                editor.undo();
            }
        });
        menu_view_redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                editor.redo();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (saveView()) {
                    showMessage("XML saved successfully!");
                }
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(ViewCodeEditorActivity.this, more);
                popup.getMenuInflater().inflate(R.menu.editor_more_menu, popup.getMenu());
                
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
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
                    }
                });
                
                popup.show();
                
            }
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

        editor.setTypefaceText(Typeface.MONOSPACE);
        
        editor.setColorScheme(new SchemeDarcula());
        
        editor.setTextSize(14);
        editor.setEditable(true);
        editor.setLineNumberEnabled(true);

        

        String existingXml = activityBean.getXmlCode();
        if (existingXml != null && !existingXml.isEmpty()) {
            editor.setText(existingXml);
        } else {

            String defaultXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
            "    android:layout_width=\"match_parent\"\n" +
            "    android:layout_height=\"match_parent\"\n" +
            "    android:orientation=\"vertical\">\n\n" +
            "</LinearLayout>";
            editor.setText(defaultXml);
            complex.setXmlCode(layoutName, defaultXml);
        }
        

        acName.setText(layoutName + ".xml");
    }
    
    public boolean saveView() {
        try {
            String xmlContent = editor.getText().toString().trim();
            

            if (xmlContent.isEmpty()) {
                showMessage("XML content is empty!");
                return false;
            }
            

            if (!isValidXml(xmlContent)) {
                showMessage("Invalid XML format! Please check the syntax.");
                return false;
            }
            
            String projectDataDir = com.nexusteam.internal.fe.d(sc_id);
            
            LayoutStorage.save(
            new java.io.File(projectDataDir),
            layoutName,
            xmlContent,
            null);
            
            /*// 1. Complex mein XML code set karein
            complex.setXmlCode(layoutName, xmlContent);
            

            if (saveProjectJson(layoutName, xmlContent)) {

                Intent result = new Intent();
                result.putExtra("xmlContent", xmlContent);
                setResult(RESULT_OK, result);
                return true;
            } else {
                showMessage("Failed to update and save JSON data.");
                return false;
            }
            */
            return true;
        } catch (Exception e) {
            showMessage("Save Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    

    private boolean isValidXml(String xmlContent) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource inputSource = new InputSource(new StringReader(xmlContent));
            

            Document doc = builder.parse(inputSource);
            doc.getDocumentElement().normalize();
            

            if (!xmlContent.contains("xmlns:android=\"http://schemas.android.com/apk/res/android\"")) {
                return false; // Missing Android namespace
            }
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    

    

    private JSONArray readProjectJson() {
        try {

            String filePath = "/storage/emulated/0" +
            "/.blacklogics/data/" + sc_id + "/root_layout.json";
            File file = new File(filePath);
            
            if (!file.exists()) {

                return new JSONArray();
            }
            

            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            

            return new JSONArray(sb.toString());
            
        } catch (FileNotFoundException e) {
            return new JSONArray();
        } catch (Exception e) {
            showMessage("Error reading JSON: " + e.getMessage());
            e.printStackTrace();
            return new JSONArray();
        }
    }
    

    private boolean updateActivityXmlInJson(String activityName, String xmlContent, JSONArray projectJsonArray) throws JSONException {
        boolean found = false;
        for (int i = 0; i < projectJsonArray.length(); i++) {
            JSONObject activityObject = projectJsonArray.getJSONObject(i);
            

            if (activityObject.optString("name").equals(activityName)) {
                

                activityObject.put("xml", xmlContent); 
                found = true;
                break;
            }
        }
        
        if (!found) {

            JSONObject newActivity = new JSONObject();
            newActivity.put("xml", xmlContent);
            newActivity.put("name", activityName);
            projectJsonArray.put(newActivity);
        }
        
        return true;
    }
    

    private boolean saveProjectJson(String activityName, String xmlContent) {
        try {
            JSONArray jsonArray = readProjectJson(); // Existing data read karein
            updateActivityXmlInJson(activityName, xmlContent, jsonArray); // Data update karein
            
            String savePath = "/storage/emulated/0" +
            "/.blacklogics/data/" + sc_id + "/root_layout.json";
            File file = new File(savePath);
            file.getParentFile().mkdirs();
            
            FileOutputStream fos = new FileOutputStream(file);

            fos.write(jsonArray.toString().getBytes()); 
            fos.close();
            
            return true;
        } catch (Exception e) {
            showMessage("JSON Save Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    private void formatCode() {
        try {
            String xmlContent = editor.getText().toString();
            if (!xmlContent.isEmpty()) {

                String formatted = formatXml(xmlContent);
                editor.setText(formatted);
                showMessage("Code formatted!");
            }
        } catch (Exception e) {
            showMessage("Formatting error: " + e.getMessage());
        }
    }
    
    private String formatXml(String xml) {

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
