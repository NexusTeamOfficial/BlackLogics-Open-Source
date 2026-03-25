package com.nexusteam.internal.editor.makeblock;

import com.nexusteam.internal.BlockMakerLayout;
import com.nexusteam.internal.CustomAlertDialog;
import com.nexusteam.internal.ki;
import com.nexusteam.internal.StringResourceManager;
import com.nexusteam.internal.ma;
import com.nexusteam.blacklogics.logic.ProjectLogicRepository;
import b.b.b.Qf;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.nexusteam.blacklogics.R;
import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.internal.lib.base.BaseAppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MakeBlockActivity extends BaseAppCompatActivity {
	
	/* renamed from: a  reason: collision with root package name */
	String f1118a;
	ProjectFileBean b;
	LinearLayout c;
	BlockMakerLayout d;
	private Toolbar e;
	String activityName;
	
	/* access modifiers changed from: protected */
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		if (!super.n()) {
			finish();
		}
		setContentView((int) R.layout.make_block);
		if (bundle == null) {
			this.f1118a = getIntent().getStringExtra("sc_id");
			this.activityName = getIntent().getStringExtra("activityName");
			this.b = (ProjectFileBean) getIntent().getParcelableExtra("project_file");
		} else {
			this.f1118a = bundle.getString("sc_id");
			this.activityName = bundle.getString("activityName");
			this.b = (ProjectFileBean) bundle.getParcelable("project_file");
		}
		this.e = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(this.e);
		findViewById(R.id.layout_main_logo).setVisibility(8);
		getSupportActionBar().setTitle((CharSequence) StringResourceManager.a().a(getApplicationContext(), (int) R.string.logic_editor_more_block_actionbar_title_create_more_block));
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		this.e.setNavigationOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (!ki.a()) {
					MakeBlockActivity.this.onBackPressed();
				}
			}
		});
		this.d = new BlockMakerLayout(this);
		this.d.setFuncNameValidator(ma.a(this.f1118a).b(this.b));
		this.c = (LinearLayout) findViewById(R.id.makeblock_view);
		this.c.addView(this.d);
	}
	
	/* access modifiers changed from: protected */
	public void onSaveInstanceState(Bundle bundle) {
		bundle.putString("sc_id", this.f1118a);
		bundle.putString("activityName", this.activityName);
		bundle.putParcelable("project_file", this.b);
		super.onSaveInstanceState(bundle);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.moreblock_menu, menu);
		menu.findItem(R.id.moreblock_create).setTitle(StringResourceManager.a().a(getApplicationContext(), (int) R.string.common_word_create));
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		if (menuItem.getItemId() == R.id.moreblock_create) {
			if (this.d.b()) {
				return false;
			}
			if (this.d.a()) {
				Pair<String, String> blockInformation = this.d.getBlockInformation();
				String blockName = (String) blockInformation.first;
				String blockSpec = (String) blockInformation.second;
				
				// REAL FUNCTION ADD KAR RAHE HAIN YAHAN
				saveMoreBlockToRepository(blockName, blockSpec);
				
				Intent intent = new Intent();
				intent.putExtra("block_name", blockName);
				intent.putExtra("block_spec", blockSpec);
				setResult(-1, intent);
				finish();
				return true;
			}
		}
		return super.onOptionsItemSelected(menuItem);
	}
	
	private void saveMoreBlockToRepository(final String blockName, final String blockSpec) {
		try {
			// Step 1: Check if function already exists
			List<HashMap<String, Object>> existingFunctions = ProjectLogicRepository.loadFunctions(this.activityName);
			
			for (HashMap<String, Object> func : existingFunctions) {
				String existingName = (String) func.get("functionName");
				if (blockName.equals(existingName)) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(MakeBlockActivity.this, 
							"Error saving function: Already exists", 
							Toast.LENGTH_SHORT).show();
						}
					});
					return;
				}
			}
			
			// 🔥 FIX: Parse block spec to get parameters with their actual names
			List<HashMap<String, String>> parameters = parseBlockSpecWithNames(blockSpec);
			
			// Step 3: Save to repository using addFunction method
			ProjectLogicRepository.addFunction(
			this.activityName,           // activity name
			blockName,                   // function name
			"void",                      // return type
			parameters                   // parameters with actual names
			);
			Qf.addFunction(activityName, blockName, "void", parameters);
			
			// Step 4: Show success message
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(MakeBlockActivity.this, 
					"Function '" + blockName + "' saved successfully!", 
					Toast.LENGTH_SHORT).show();
				}
			});
			
		} catch (final Exception e) {
			e.printStackTrace();
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(MakeBlockActivity.this, 
					"Error saving function: " + e.getMessage(), 
					Toast.LENGTH_SHORT).show();
				}
			});
		}
	}
	
	// 🔥 NEW METHOD: Parse block spec with actual parameter names
	private List<HashMap<String, String>> parseBlockSpecWithNames(String blockSpec) {
		List<HashMap<String, String>> parameters = new ArrayList<>();
		
		if (blockSpec == null || blockSpec.isEmpty()) {
			return parameters;
		}
		
		try {
			// Split by spaces to get individual tokens
			String[] tokens = blockSpec.split("\\s+");
			
			// First token is function name, skip it
			for (int i = 1; i < tokens.length; i++) {
				String token = tokens[i];
				
				// Check if this token contains a parameter placeholder
				if (token.startsWith("%")) {
					HashMap<String, String> param = new HashMap<>();
					
					// Determine type from placeholder
					char typeChar = token.charAt(1);
					String paramType = "String";
					switch (typeChar) {
						case 's':
						case 't':
						paramType = "String";
						break;
						case 'n':
						case 'd':
						paramType = "double";
						break;
						case 'b':
						paramType = "boolean";
						break;
						case 'c':
						paramType = "int";
						break;
					}
					
					// Extract parameter name (after dot)
					String paramName;
					int dotIndex = token.indexOf('.');
					if (dotIndex > 0 && dotIndex < token.length() - 1) {
						paramName = token.substring(dotIndex + 1);
					} else {
						// If no dot, generate a default name based on type
						paramName = getDefaultParamName(paramType, i);
					}
					
					param.put("varTypeName", paramType);
					param.put("varName", paramName);
					
					parameters.add(param);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return parameters;
	}
	
	private String getDefaultParamName(String type, int index) {
		switch (type) {
			case "String": return "text" + index;
			case "double": return "number" + index;
			case "boolean": return "flag" + index;
			case "int": return "value" + index;
			default: return "param" + index;
		}
	}
	
	private List<HashMap<String, String>> parseBlockSpec(String blockSpec) {
		List<HashMap<String, String>> parameters = new ArrayList<>();
		
		if (blockSpec == null || blockSpec.isEmpty()) {
			return parameters;
		}
		
		try {
			// Split the spec to extract parameter names and types
			// Example: "myFunction %s.userMessage %n.delayTime %b.isEnabled"
			String[] parts = blockSpec.split("\\s+");
			
			// Skip first part (function name)
			for (int i = 1; i < parts.length; i++) {
				String part = parts[i];
				
				// Check if this part contains a parameter placeholder
				if (part.contains("%")) {
					HashMap<String, String> param = new HashMap<>();
					
					// Extract type and name from placeholder
					// Format: %t.paramName or just %t
					int dotIndex = part.indexOf('.');
					String paramType = "String";
					String paramName = "param" + i;
					
					if (part.startsWith("%s")) {
						paramType = "String";
					} else if (part.startsWith("%n") || part.startsWith("%d")) {
						paramType = "double";
					} else if (part.startsWith("%b")) {
						paramType = "boolean";
					} else if (part.startsWith("%c")) {
						paramType = "int";
					}
					
					// Extract parameter name if present
					if (dotIndex > 0 && dotIndex < part.length() - 1) {
						paramName = part.substring(dotIndex + 1);
					} else {
						paramName = "param" + i;
					}
					
					param.put("varTypeName", paramType);
					param.put("varName", paramName);
					param.put("originalName", part); // Store original for debugging
					
					parameters.add(param);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return parameters;
	}
	
	/**
* Saari functions ko load karta hai
*/	
	private void loadAllFunctions() {
		try {
			List<HashMap<String, Object>> functions = ProjectLogicRepository.loadFunctions(this.activityName);
			
			for (HashMap<String, Object> function : functions) {
				String name = (String) function.get("functionName");
				String returnType = (String) function.get("returnType");
				List<?> params = (List<?>) function.get("parameters");
				
				System.out.println("Function: " + name + " Return: " + returnType + " Params: " + params.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
* Kisi function ko delete karne ke liye (agar zaroorat ho)
*/	
	private void deleteFunction(String functionName) {
		try {
			List<HashMap<String, Object>> functions = ProjectLogicRepository.loadFunctions(this.activityName);
			List<HashMap<String, Object>> updatedFunctions = new ArrayList<>();
			
			for (HashMap<String, Object> func : functions) {
				String name = (String) func.get("functionName");
				if (!functionName.equals(name)) {
					updatedFunctions.add(func);
				}
			}
			
			// Purani functions ko replace karo
			// Note: Repository mein direct delete nahi hai, isliye hum empty addFunction call karke overwrite karenge
			// Better approach: ProjectLogicRepository mein removeFunction method add karo
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* access modifiers changed from: protected */
	public void onResume() {
		super.onResume();
		if (!super.n()) {
			finish();
		}
		// Load functions when activity resumes (optional)
		loadAllFunctions();
	}
	
	public void onBackPressed() {
		if (this.d.b()) {
			super.onBackPressed();
		} else {
			a();
		}
	}
	
	private void a() {
		final CustomAlertDialog kdVar = new CustomAlertDialog(this);
		kdVar.a(StringResourceManager.a().a(getApplicationContext(), (int) R.string.logic_editor_more_block_dialog_message_confirm_goback));
		kdVar.a((int) R.drawable.exit_96);
		kdVar.b(StringResourceManager.a().a(getApplicationContext(), (int) R.string.logic_editor_more_block_dialog_description_goback));
		kdVar.a(StringResourceManager.a().a(getApplicationContext(), (int) R.string.common_word_goback), new View.OnClickListener() {
			public void onClick(View view) {
				if (!ki.a()) {
					kdVar.dismiss();
					MakeBlockActivity.this.finish();
				}
			}
		});
		kdVar.b(StringResourceManager.a().a(getApplicationContext(), (int) R.string.common_word_cancel), new View.OnClickListener() {
			public void onClick(View view) {
				kdVar.dismiss();
			}
		});
		kdVar.show();
	}
}
