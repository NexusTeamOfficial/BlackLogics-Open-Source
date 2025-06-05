package com.besome.blacklogics;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.Intent;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.os.Bundle;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.*;
import com.googlecode.d2j.*;
import com.larswerkman.holocolorpicker.*;
import io.github.rosemoe.editor.*;
import io.github.rosemoe.sora.*;
import io.github.rosemoe.sora.langs.java.*;
import io.github.rosemoe.sora.langs.textmate.*;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import org.antlr.v4.runtime.*;
import org.benf.cfr.reader.*;
import org.eclipse.jdt.*;
import org.json.*;
import mod.hey.studios.util.Helper;
import com.besome.blacklogics.block_manager.BlocksManagerActivity;
import com.besome.blacklogics.logic.editor.PaletteBlock;
import com.besome.blacklogics.logic.editor.BlockBase;
import com.besome.blacklogics.logic.editor.LayoutUtil;
import com.besome.blacklogics.logic.editor.Block;
import android.widget.RelativeLayout.LayoutParams;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;

public class BlocksManagerDetailsActivity extends AppCompatActivity {
	
	private FloatingActionButton _fab;
	public float dip = 0.0f;
	private String paletteIndex = "";
	private static final int REQUEST_STORAGE_PERMISSION = 100;
	private FilePickerDialog filePickerDialog;
	
	private ArrayList<HashMap<String, Object>> blockList = new ArrayList<>();
	
	private LinearLayout linear1;
	private ListView listview1;
	
	private Intent intent = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.blocks_manager_details);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		_fab = findViewById(R.id._fab);
		linear1 = findViewById(R.id.linear1);
		listview1 = findViewById(R.id.listview1);
		
		listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				HashMap<String, Object> block = blockList.get(_position);
				intent.setClass(getApplicationContext(), BlocksManageCreatorActivity.class);
				intent.putExtra("index", paletteIndex);
				intent.putExtra("isUpdateMode", true);
				intent.putExtra("blockCode", block.get("code").toString());
				startActivity(intent);
			}
		});
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Intent ft = new Intent();
				ft.setClass(getApplicationContext(), BlocksManageCreatorActivity.class);
				ft.putExtra("index", paletteIndex);
				startActivity(ft);
			}
		});
	}
	
	private void initializeLogic() {
		dip = LayoutUtil.getDip(this, 1.0f); 
		ImageView back = findViewById(R.id.ig_toolbar_back);
		TextView title = findViewById(R.id.tx_toolbar_title);
		ImageView loadFile = findViewById(R.id.ig_toolbar_load_file);
		
		Helper.applyRippleToToolbarView(back);
		back.setOnClickListener(Helper.getBackPressedClickListener(this));
		title.setText("Manage Block");
		loadFile.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
		loadFile.setImageResource(R.drawable.ic_more_vert_black);
		loadFile.setVisibility(View.VISIBLE);
		Helper.applyRippleToToolbarView(loadFile);
		loadFile.setOnClickListener(v -> showImportExportMenu());
		paletteIndex = getIntent().getStringExtra("paletteIndex");
		
		// Load blocks and set adapter
		loadBlocksFromJson(paletteIndex);
		listview1.setAdapter(new Listview1Adapter(blockList));
		
	}
	private void loadBlocksFromJson(String paletteIndex) {
			String blockPath = "/storage/emulated/0/.blacklogics/resources/block/My Block/block.json";
			File blockFile = new File(blockPath);
			
			if (blockFile.exists()) {
					try {
							BufferedReader reader = new BufferedReader(new FileReader(blockFile));
							StringBuilder jsonString = new StringBuilder();
							String line;
							while ((line = reader.readLine()) != null) {
									jsonString.append(line);
							}
							reader.close();
							
							JSONArray jsonArray = new JSONArray(jsonString.toString());
							for (int i = 0; i < jsonArray.length(); i++) {
									JSONObject blockObj = jsonArray.getJSONObject(i);
									
									// Check if block's palette matches the selected paletteIndex
									if (blockObj.getString("palette").equals(paletteIndex)) {
											HashMap<String, Object> blockMap = new HashMap<>();
											blockMap.put("name", blockObj.getString("name"));
											blockMap.put("code", blockObj.getString("code"));
											blockMap.put("color", blockObj.getString("color"));
											blockMap.put("spec", blockObj.getString("spec"));
											blockMap.put("type", blockObj.getString("type"));
											blockMap.put("typeName", blockObj.getString("typeName"));
											blockList.add(blockMap);
									}
							}
							
							if (blockList.isEmpty()) {
									showMessage("No blocks found for this palette");
							}
							
					} catch (Exception e) {
							e.printStackTrace();
							showMessage("Error loading blocks: " + e.getMessage());
					}
			} else {
					showMessage("Block file not found");
			}
	}
	{
			
	}
	
	public void _a() {
	}
	private void addBlockToPalette(RelativeLayout blockBuilder, String str, String str2, String str3, int i, Object... objArr) {
			BlockBase addBlock = addBlock(blockBuilder, str, str2, str3, i, objArr);
			addBlock.setClickable(false);
			//	addBlock.setOnTouchListener(this);
	}
	
	public BlockBase addBlock(RelativeLayout blockBuilder, String str, String str2, String str3, int i, Object... objArr) {
			View view = new View(this);
			view.setLayoutParams(new RelativeLayout.LayoutParams(-1, (int) (8.0f * this.dip)));
			blockBuilder.addView(view);
			Block block = new Block(this, -1, str, str2, str3, new Object[]{Integer.valueOf(i), objArr});
			block.setBlockType(1);
			blockBuilder.addView(block);
			return block;
	}
	
	private void showImportExportMenu() {
			PopupMenu popup = new PopupMenu(this, findViewById(R.id.ig_toolbar_load_file));
			popup.getMenu().add("Export Blocks");
			popup.getMenu().add("Import Blocks");
			popup.setOnMenuItemClickListener(item -> {
					if (item.getTitle().equals("Export Blocks")) {
							checkStoragePermissions("export");
					} else if (item.getTitle().equals("Import Blocks")) {
							checkStoragePermissions("import");
					}
					return true;
			});
			popup.show();
	}
	
	private void checkStoragePermissions(String action) {
			if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
			ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
					ActivityCompat.requestPermissions(this, 
					new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
					REQUEST_STORAGE_PERMISSION);
					// Store the action to perform after permission is granted
					intent.putExtra("pendingAction", action);
			} else {
					if (action.equals("export")) {
							exportBlocks();
					} else {
							importBlocks();
					}
			}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
			super.onRequestPermissionsResult(requestCode, permissions, grantResults);
			if (requestCode == REQUEST_STORAGE_PERMISSION && grantResults.length > 0 && 
			grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					String action = intent.getStringExtra("pendingAction");
					if (action != null) {
							if (action.equals("export")) {
									exportBlocks();
							} else if (action.equals("import")) {
									importBlocks();
							}
							intent.removeExtra("pendingAction");
					}
			} else {
					showMessage("Storage permissions denied");
			}
	}
	
	private void exportBlocks() {
			DialogProperties properties = new DialogProperties();
			properties.selection_mode = DialogConfigs.SINGLE_MODE;
			properties.selection_type = DialogConfigs.DIR_SELECT;
			properties.root = new File("/storage/emulated/0/");
			properties.error_dir = new File("/storage/emulated/0/");
			properties.offset = new File("/storage/emulated/0/.blacklogics/resources/block/Export blocks/");
			properties.extensions = null;
			
			filePickerDialog = new FilePickerDialog(this, properties);
			filePickerDialog.setTitle("Select Export Directory");
			filePickerDialog.setDialogSelectionListener(new DialogSelectionListener() {
					@Override
					public void onSelectedFilePaths(String[] files) {
							if (files.length > 0) {
									String selectedDir = files[0];
									exportBlocksToFile(selectedDir);
							}
					}
			});
			filePickerDialog.show();
	}
	
	private void exportBlocksToFile(String selectedDir) {
			try {
					String blockPath = "/storage/emulated/0/.blacklogics/resources/block/My Block/block.json";
					File blockFile = new File(blockPath);
					String exportDirPath = selectedDir.endsWith("/") ? selectedDir : selectedDir + "/";
					String paletteName = "palette_" + paletteIndex; // Replace with actual palette name if available
					String exportFilePath = exportDirPath + paletteName + ".json";
					File exportFile = new File(exportFilePath);
					
					// Ensure export directory exists
					File exportDir = new File(exportDirPath);
					if (!exportDir.exists()) {
							exportDir.mkdirs();
					}
					
					if (blockFile.exists()) {
							BufferedReader reader = new BufferedReader(new FileReader(blockFile));
							StringBuilder jsonString = new StringBuilder();
							String line;
							while ((line = reader.readLine()) != null) {
									jsonString.append(line);
							}
							reader.close();
							
							// Filter blocks by current paletteIndex
							JSONArray jsonArray = new JSONArray(jsonString.toString());
							JSONArray filteredArray = new JSONArray();
							for (int i = 0; i < jsonArray.length(); i++) {
									JSONObject blockObj = jsonArray.getJSONObject(i);
									if (blockObj.getString("palette").equals(paletteIndex)) {
											filteredArray.put(blockObj);
									}
							}
							
							// Write filtered blocks to the export file
							try (FileWriter writer = new FileWriter(exportFile)) {
									writer.write(filteredArray.toString(2));
									showMessage("Blocks exported successfully to " + exportFilePath);
							}
					} else {
							showMessage("No blocks file found to export");
					}
			} catch (Exception e) {
					e.printStackTrace();
					showMessage("Error exporting blocks: " + e.getMessage());
			}
	}
	
	private void importBlocks() {
			DialogProperties properties = new DialogProperties();
			properties.selection_mode = DialogConfigs.SINGLE_MODE;
			properties.selection_type = DialogConfigs.FILE_SELECT;
			properties.root = new File("/storage/emulated/0/");
			properties.error_dir = new File("/storage/emulated/0/");
			properties.offset = new File("/storage/emulated/0/.blacklogics/resources/block/Export blocks/");
			properties.extensions = new String[]{"json"};
			
			filePickerDialog = new FilePickerDialog(this, properties);
			filePickerDialog.setTitle("Select JSON File to Import");
			filePickerDialog.setDialogSelectionListener(new DialogSelectionListener() {
					@Override
					public void onSelectedFilePaths(String[] files) {
							if (files.length > 0) {
									String selectedFile = files[0];
									importBlocksFromFile(selectedFile);
							}
					}
			});
			filePickerDialog.show();
	}
	
	private void importBlocksFromFile(String selectedFile) {
			try {
					String blockPath = "/storage/emulated/0/.blacklogics/resources/block/My Block/block.json";
					File blockFile = new File(blockPath);
					File parentDir = blockFile.getParentFile();
					if (!parentDir.exists()) {
							parentDir.mkdirs();
					}
					
					// Read imported JSON
					File importFile = new File(selectedFile);
					if (importFile.exists()) {
							BufferedReader reader = new BufferedReader(new FileReader(importFile));
							StringBuilder jsonString = new StringBuilder();
							String line;
							while ((line = reader.readLine()) != null) {
									jsonString.append(line);
							}
							reader.close();
							
							// Parse imported JSON (expecting an array of blocks)
							JSONArray importedArray = new JSONArray(jsonString.toString());
							JSONArray existingArray = new JSONArray();
							
							// Read existing blocks if file exists
							if (blockFile.exists()) {
									BufferedReader existingReader = new BufferedReader(new FileReader(blockFile));
									StringBuilder existingJson = new StringBuilder();
									while ((line = existingReader.readLine()) != null) {
											existingJson.append(line);
									}
									existingReader.close();
									existingArray = new JSONArray(existingJson.toString());
							}
							
							// Keep blocks that don't belong to the current palette
							JSONArray otherPalettesArray = new JSONArray();
							for (int i = 0; i < existingArray.length(); i++) {
									JSONObject blockObj = existingArray.getJSONObject(i);
									if (!blockObj.getString("palette").equals(paletteIndex)) {
											otherPalettesArray.put(blockObj);
									}
							}
							
							// Merge imported blocks into the current palette
							for (int i = 0; i < importedArray.length(); i++) {
									JSONObject blockObj = importedArray.getJSONObject(i);
									blockObj.put("palette", paletteIndex);
									otherPalettesArray.put(blockObj);
							}
							
							// Write merged blocks back to file
							try (FileWriter writer = new FileWriter(blockFile)) {
									writer.write(otherPalettesArray.toString(2));
							}
							
							// Reload blocks to update UI
							blockList.clear();
							loadBlocksFromJson(paletteIndex);
							((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
							showMessage("Blocks imported successfully from " + selectedFile);
					} else {
							showMessage("Selected file not found");
					}
			} catch (Exception e) {
					e.printStackTrace();
					showMessage("Error importing blocks: " + e.getMessage());
			}
	}
	
	{
			
			
	}
	
	public class Listview1Adapter extends BaseAdapter {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = getLayoutInflater();
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.block_view, null);
			}
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final TextView blockName = _view.findViewById(R.id.blockName);
			final RelativeLayout paletteBlock = _view.findViewById(R.id.paletteBlock);
			
			HashMap<String, Object> item = _data.get(_position);
			blockName.setText(item.get("name").toString());
			
			// Use the data that's already loaded in blockList instead of reading the file again
			String name = item.get("name").toString();
			String type = item.get("type").toString();
			String code = item.get("code").toString();
			String color = item.get("color").toString();
			String spec = item.get("spec").toString();
			
			// Clear any existing views in paletteBlock
			paletteBlock.removeAllViews();
			
			// Add the block to the palette
			addBlockToPalette(paletteBlock, spec, type, code, 
			Color.parseColor(color), new Object[]{spec});
			
			
			return _view;
		}
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