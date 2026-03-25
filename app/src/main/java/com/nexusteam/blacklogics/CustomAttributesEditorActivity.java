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
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
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
import com.nexusteam.blacklogics.R;
import com.nexusteam.blacklogics.generator.source.AndroidManifestGenerator;
import com.besome.blacklogics.development.Complex;
import com.besome.blacklogics.beans.ProjectActivityBean;
import com.nexusteam.blacklogics.FileUtil;
import com.nexusteam.internal.*;
import com.bumptech.glide.*;
import com.github.angads25.filepicker.*;
import com.google.android.material.*;
import com.google.gson.*;
import com.larswerkman.holocolorpicker.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import io.github.rosemoe.sora.langs.java.*;
import io.github.rosemoe.sora.langs.textmate.*;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.component.EditorAutoCompletion;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import io.github.rosemoe.sora.widget.schemes.SchemeDarcula;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.json.*;

public class CustomAttributesEditorActivity extends AppCompatActivity {
    
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    
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
    private AndroidManifestGenerator manifestGenerator;
    
    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.custom_attributes_editor);
        initialize(_savedInstanceState);
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
        
        sc_id = getIntent().getStringExtra("sc_id");
        complex = new Complex();
        complex.setId(sc_id);
        
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
                saveAttributes();
            }
        });
        
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(CustomAttributesEditorActivity.this, more);
                popup.getMenuInflater().inflate(R.menu.editor_more_menu, popup.getMenu());
                
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_format:
                                // Format attributes
                                showMessage("Format feature coming soon");
                                return true;
                            case R.id.action_undo_all:
                                editor.undo();
                                return true;
                            case R.id.action_redo_all:
                                editor.redo();
                                return true;
                            case R.id.action_layout_view:
                                // Preview manifest
                                previewManifest();
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
            // For newer versions
        }
        
        editor.setTypefaceText(Typeface.MONOSPACE);
        editor.setColorScheme(new SchemeDarcula());
        editor.setTextSize(14);
        editor.setEditable(true);
        editor.setLineNumberEnabled(true);
        
        acName.setText("custom_attributes.txt");
        textview9.setText("Custom Manifest Attributes");
        
        // Load existing attributes
        loadAttributes();
    }
    
    private void loadAttributes() {
        try {
            File dir = new File("/storage/emulated/0/.blacklogics/data/" + sc_id);
            File file = new File(dir, "custom_attributes.txt");
            
            StringBuilder content = new StringBuilder();
            content.append("# Custom Attributes for Android Manifest\n");
            content.append("# Format: component_name:attribute_key=attribute_value\n");
            content.append("# Examples:\n");
            content.append("# MainActivity:hardwareAccelerated=true\n");
            content.append("# .SecondActivity:screenOrientation=portrait\n");
            content.append("# MyService:exported=false\n");
            content.append("# MyReceiver:enabled=true\n");
            content.append("\n");
            
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                reader.close();
            } else {
                // Try to load from manifest generator
                manifestGenerator = new AndroidManifestGenerator();
                manifestGenerator.load(this, sc_id);
                List<String> existingAttrs = manifestGenerator.getCustomAttributesAsStrings();
                if (existingAttrs != null && !existingAttrs.isEmpty()) {
                    content.append("# Loaded from existing manifest:\n");
                    for (String attr : existingAttrs) {
                        content.append(attr).append("\n");
                    }
                }
            }
            
            editor.setText(content.toString());
            
        } catch (Exception e) {
            e.printStackTrace();
            showMessage("Error loading attributes: " + e.getMessage());
            editor.setText("# Error loading attributes\n");
        }
    }
    
    	private void saveAttributes() {
		try {
			String textContent = editor.getText().toString();
			
			List<String> attributes = new ArrayList<>();
			String[] lines = textContent.split("\n");
			
			for (String line : lines) {
				String trimmed = line.trim();
				if (!trimmed.isEmpty() && !trimmed.startsWith("#")) {
					attributes.add(trimmed);
				}
			}
			
			if (manifestGenerator == null) {
				manifestGenerator = new AndroidManifestGenerator();
				manifestGenerator.load(this, sc_id);
			}
			
			// Clear old and add new
			manifestGenerator.clearCustomAttributes();
			manifestGenerator.addCustomAttributes(attributes); // Direct add
			manifestGenerator.save(this, sc_id);
			
			saveToFile(attributes);
			
			//showMessage("Saved: " + attributes.size() + " attributes");
			
		} catch (Exception e) {
			e.printStackTrace();
			showMessage("Error: " + e.getMessage());
		}
	}
    
    private void saveToFile(List<String> attributes) {
        try {
            // 1. Save to custom_attributes.txt file
            File dir = new File("/storage/emulated/0/.blacklogics/data/" + sc_id);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            
            File file = new File(dir, "custom_attributes.txt");
            FileOutputStream fos = new FileOutputStream(file);
            
            // Write with comments
            StringBuilder content = new StringBuilder();
            content.append("# Custom Attributes for Android Manifest\n");
            content.append("# Format: component_name:attribute_key=attribute_value\n");
            content.append("# Examples:\n");
            content.append("# MainActivity:hardwareAccelerated=true\n");
            content.append("# .SecondActivity:screenOrientation=portrait\n");
            content.append("# MyService:exported=false\n");
            content.append("# MyReceiver:enabled=true\n");
            content.append("\n");
            
            for (String attr : attributes) {
                content.append(attr).append("\n");
            }
            
            fos.write(content.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8));
            fos.close();
            
            // 2. Update manifest generator
            if (manifestGenerator == null) {
                manifestGenerator = new AndroidManifestGenerator();
                manifestGenerator.load(this, sc_id);
            }
            
            // Clear existing custom attributes? Or merge?
            // We'll replace with new ones
            manifestGenerator.clearCustomAttributes();
            manifestGenerator.addCustomAttributesFromList(attributes);
            manifestGenerator.save(this, sc_id);
            
            showMessage("Attributes saved successfully! (" + attributes.size() + " attributes)");
            
            // 3. Also save to manifest.txt for easy access
            File manifestFile = new File(dir, "manifest.txt");
            fos = new FileOutputStream(manifestFile);
            fos.write(manifestGenerator.generate().getBytes(java.nio.charset.StandardCharsets.UTF_8));
            fos.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            showMessage("Save failed: " + e.getMessage());
        }
    }
    
    private void previewManifest() {
        try {
            if (manifestGenerator == null) {
                manifestGenerator = new AndroidManifestGenerator();
                manifestGenerator.load(this, sc_id);
            }
            String manifest = manifestGenerator.generate();
            
            // Show in dialog
            ScrollView scrollView = new ScrollView(this);
            TextView textView = new TextView(this);
            textView.setText(manifest);
            textView.setTypeface(Typeface.MONOSPACE);
            textView.setTextSize(10);
            textView.setPadding(40, 40, 40, 40);
            scrollView.addView(textView);
            
            new AlertDialog.Builder(this)
                .setTitle("AndroidManifest.xml Preview")
                .setView(scrollView)
                .setPositiveButton("Close", null)
                .show();
                
        } catch (Exception e) {
            e.printStackTrace();
            showMessage("Preview error: " + e.getMessage());
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Auto save on destroy
        saveAttributes();
    }
    
    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}