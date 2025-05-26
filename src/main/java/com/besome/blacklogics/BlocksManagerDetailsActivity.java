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

public class BlocksManagerDetailsActivity extends AppCompatActivity {
	
	private FloatingActionButton _fab;
	public float dip = 0.0f;
	private String paletteIndex = "";
	
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
		loadFile.setVisibility(View.GONE);
		Helper.applyRippleToToolbarView(loadFile);
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