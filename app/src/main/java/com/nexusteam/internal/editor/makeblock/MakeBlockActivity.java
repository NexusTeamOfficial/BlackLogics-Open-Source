package com.nexusteam.internal.editor.makeblock;

import com.nexusteam.internal.BlockMakerLayout;
import com.nexusteam.internal.CustomAlertDialog;
import com.nexusteam.internal.ki;
import com.nexusteam.internal.StringResourceManager;
import com.nexusteam.internal.ma;
import com.nexusteam.internal.BlockStorage;

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
	
	String f1118a;
	ProjectFileBean b;
	LinearLayout c;
	BlockMakerLayout d;
	private Toolbar e;
	String activityName;
	
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
			if (this.d.isEmpty()) {
				return false;
			}
			if (this.d.isValid()) {
				Pair<String, String> blockInformation = this.d.getBlockInformation();
				String blockName = (String) blockInformation.first;
				String blockSpec = (String) blockInformation.second;
				
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
			String key = this.activityName + ".java";

			ArrayList<Pair<String, String>> existingFunctions = BlockStorage.loadFunctions(key);
			if (existingFunctions == null) {
				existingFunctions = new ArrayList<Pair<String, String>>();
			}

			for (Pair<String, String> func : existingFunctions) {
				String existingName = func.first;
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

			existingFunctions.add(new Pair<String, String>(blockName, blockSpec));
			BlockStorage.saveFunctions(key, existingFunctions);

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
	

	private void loadAllFunctions() {
		try {
			ArrayList<Pair<String, String>> functions = BlockStorage.loadFunctions(this.activityName + ".java");
			if (functions == null) return;

			for (Pair<String, String> function : functions) {
				String name = function.first;
				String spec = function.second;

				System.out.println("Function: " + name + " Spec: " + spec);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void deleteFunction(String functionName) {
		try {
			String key = this.activityName + ".java";
			ArrayList<Pair<String, String>> functions = BlockStorage.loadFunctions(key);
			if (functions == null) return;

			ArrayList<Pair<String, String>> updatedFunctions = new ArrayList<Pair<String, String>>();
			for (Pair<String, String> func : functions) {
				if (!functionName.equals(func.first)) {
					updatedFunctions.add(func);
				}
			}

			BlockStorage.saveFunctions(key, updatedFunctions);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void onResume() {
		super.onResume();
		if (!super.n()) {
			finish();
		}
		loadAllFunctions();
	}
	
	public void onBackPressed() {
		if (this.d.isEmpty()) {
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
