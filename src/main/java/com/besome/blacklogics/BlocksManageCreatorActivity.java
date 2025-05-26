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
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
import com.google.android.material.button.*;
import com.google.android.material.textfield.*;
import com.google.gson.*;
import com.googlecode.d2j.*;
import com.larswerkman.holocolorpicker.*;
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
import mod.SketchwareUtil;
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
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import com.nexusteam.internal.os.layouteditor.color.ColorPickerActivity;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.besome.blacklogics.file.BlockMaker;

import android.text.Editable;
import android.text.TextWatcher;
import android.graphics.Color;

public class BlocksManageCreatorActivity extends AppCompatActivity {
	
	private String paletteId = "";
	public float dip = 0.0f;
	public boolean isUpdateMode = false;
	public String blockCode = "";
	
	private ScrollView vscroll1;
	private LinearLayout linear10;
	private CardView cardv;
	private CardView cardview3;
	private CardView cardview4;
	private LinearLayout linear16;
	private LinearLayout linear11;
	private TextView textview5;
	private TextInputLayout name_lay;
	private LinearLayout linear12;
	private TextInputLayout textinputlayout4;
	private EditText name;
	private TextInputLayout textinputlayout3;
	private LinearLayout select_type;
	private EditText type;
	private ImageView image;
	private EditText type_name;
	private LinearLayout linear13;
	private TextView textview6;
	private LinearLayout linear14;
	private EditText spec;
	private TextView textview7;
	private HorizontalScrollView hscroll1;
	private TextInputLayout spec_2lay;
	private TextView textview9;
	private RelativeLayout blockPreview;
	private LinearLayout reset;
	private TextInputLayout colour_lay;
	private LinearLayout colour_selector;
	private ImageView imageview2;
	private EditText color;
	private ImageView image_view1;
	private LinearLayout parametersHolder;
	private EditText spec2;
	private LinearLayout linear15;
	private TextView textview8;
	private EditText code;
	private MaterialButton cancel;
	private MaterialButton save;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.blocks_manage_creator);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		vscroll1 = findViewById(R.id.vscroll1);
		linear10 = findViewById(R.id.linear10);
		cardv = findViewById(R.id.cardv);
		cardview3 = findViewById(R.id.cardview3);
		cardview4 = findViewById(R.id.cardview4);
		linear16 = findViewById(R.id.linear16);
		linear11 = findViewById(R.id.linear11);
		textview5 = findViewById(R.id.textview5);
		name_lay = findViewById(R.id.name_lay);
		linear12 = findViewById(R.id.linear12);
		textinputlayout4 = findViewById(R.id.textinputlayout4);
		name = findViewById(R.id.name);
		textinputlayout3 = findViewById(R.id.textinputlayout3);
		select_type = findViewById(R.id.select_type);
		type = findViewById(R.id.type);
		image = findViewById(R.id.image);
		type_name = findViewById(R.id.type_name);
		linear13 = findViewById(R.id.linear13);
		textview6 = findViewById(R.id.textview6);
		linear14 = findViewById(R.id.linear14);
		spec = findViewById(R.id.spec);
		textview7 = findViewById(R.id.textview7);
		hscroll1 = findViewById(R.id.hscroll1);
		spec_2lay = findViewById(R.id.spec_2lay);
		textview9 = findViewById(R.id.textview9);
		blockPreview = findViewById(R.id.blockPreview);
		reset = findViewById(R.id.reset);
		colour_lay = findViewById(R.id.colour_lay);
		colour_selector = findViewById(R.id.colour_selector);
		imageview2 = findViewById(R.id.imageview2);
		color = findViewById(R.id.color);
		image_view1 = findViewById(R.id.image_view1);
		parametersHolder = findViewById(R.id.parametersHolder);
		spec2 = findViewById(R.id.spec2);
		linear15 = findViewById(R.id.linear15);
		textview8 = findViewById(R.id.textview8);
		code = findViewById(R.id.code);
		cancel = findViewById(R.id.cancel);
		save = findViewById(R.id.save);
		
		select_type.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				List<String> types = Arrays.asList(
				"regular",
				"c",
				"e",
				"s",
				"b",
				"d",
				"v",
				"a",
				"f",
				"l",
				"p",
				"h"
				);
				List<String> choices = Arrays.asList(
				"Regular block (regular)",
				"if block (c)",
				"if-else block (e)",
				"String (s)",
				"Boolean (b)",
				"Number (d)",
				"Variable (v)",
				"Map (a)",
				"stop block (f)",
				"List (l)",
				"Component (p)",
				"Header (h)"
				);
				AtomicInteger choice = new AtomicInteger();
				new AlertDialog.Builder(BlocksManageCreatorActivity.this).setTitle("Block type")
				.setSingleChoiceItems(choices.toArray(new String[0]),
				types.indexOf(type.getText().toString()), (dialog, which) -> choice.set(which))
				.setPositiveButton(R.string.common_word_save, (dialog, which) -> type.setText(types.get(choice.get())))
				.setNegativeButton(R.string.common_word_cancel, null)
				.create().show();
				
			}
		});
		
		colour_selector.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				ColorPicker colorPicker = new ColorPicker(BlocksManageCreatorActivity.this, false); // pipEnabled = false
				colorPicker.setOnColorPickedListener(new OnColorPickedListener() {
						@Override
						public void onColorPicked(String str) {
								try {
										// Ensure color is in 0xFF format
										color.setText(str);
								} catch (Exception e) {
										// Handle invalid color
										Toast.makeText(BlocksManageCreatorActivity.this, "Invalid color selected", Toast.LENGTH_SHORT).show();
								}
						}
				});
				
				// Launch color picker
				colorPicker.pick();
				
			}
		});
		
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (name.getText().toString().isEmpty()) {
						name.setError("Name is null.");
				} else if (type.getText().toString().isEmpty()) {
						type.setError("Type is null.");
				} else if (color.getText().toString().isEmpty()) {
						color.setError("Color is empty.");
				} else {
						BlockMaker maker = new BlockMaker("/storage/emulated/0/.blacklogics/resources/block/My Block/block.json");
						JSONObject block = new JSONObject();
						try {
								block.put("code", code.getText().toString());
								block.put("name", name.getText().toString());
								block.put("palette", paletteId);
								block.put("type", type.getText().toString());
								block.put("typeName", type_name.getText().toString());
								block.put("spec", spec.getText().toString());
								block.put("color", color.getText().toString());
								if (isUpdateMode) {
										maker.updateBlock(blockCode, block);
										Toast.makeText(BlocksManageCreatorActivity.this, "Block updated", Toast.LENGTH_SHORT).show();
								} else {
										maker.addBlock(block);
										Toast.makeText(BlocksManageCreatorActivity.this, "Block added", Toast.LENGTH_SHORT).show();
								}
								finish();
						} catch (JSONException e) {
								e.printStackTrace();
								Toast.makeText(BlocksManageCreatorActivity.this, "Error saving block", Toast.LENGTH_SHORT).show();
						}
				}
				
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
		title.setText(isUpdateMode ? "Edit Block" : "Add a new block");
		loadFile.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
		loadFile.setImageResource(R.drawable.ic_more_vert_black);
		loadFile.setVisibility(View.GONE);
		Helper.applyRippleToToolbarView(loadFile);
		
		addParameters();
		if (getIntent().hasExtra("index")) {
			paletteId = getIntent().getStringExtra("index");
		}
		if (getIntent().hasExtra("isUpdateMode") && getIntent().getBooleanExtra("isUpdateMode", false)) {
				isUpdateMode = true;
				blockCode = getIntent().getStringExtra("blockCode");
				loadBlockData();
		}
		
		TextWatcher previewWatcher = new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {}
				
				@Override
				public void afterTextChanged(Editable s) {
						updateBlockPreview();
				}
		};
		
		spec.addTextChangedListener(previewWatcher);
		type.addTextChangedListener(previewWatcher);
		color.addTextChangedListener(previewWatcher);
		type_name.addTextChangedListener(previewWatcher);
		
		// Initial preview update
		updateBlockPreview();
		
	}
	
	public void _a() {
	}
	private View addBlockMenu(final String menu, String name) {
			TextView textView = new TextView(this);
			textView.setLayoutParams(new LinearLayout.LayoutParams(
			ViewGroup.LayoutParams.WRAP_CONTENT,
			ViewGroup.LayoutParams.MATCH_PARENT));
			textView.setPadding(
			(int) SketchwareUtil.getDip(this, 8),
			0,
			(int) SketchwareUtil.getDip(this, 8),
			0
			);
			textView.setTextColor(0xff006064);
			textView.setText(name);
			textView.setTextSize(14.0f);
			textView.setTypeface(Typeface.DEFAULT_BOLD);
			textView.setOnClickListener(v -> {
					StringBuilder sb = new StringBuilder(spec.getText().toString());
					int selectionStart = spec.getSelectionStart();
					sb.insert(selectionStart, menu);
					spec.setText(sb);
					spec.setSelection(selectionStart + menu.length());
			});
			return textView;
	}
	
	private void addParameters() {
			parametersHolder.addView(addBlockMenu("%s.inputOnly ", "inputOnly"));
			parametersHolder.addView(addBlockMenu("%s ", "string"));
			parametersHolder.addView(addBlockMenu("%b ", "boolean"));
			parametersHolder.addView(addBlockMenu("%d ", "number"));
			parametersHolder.addView(addBlockMenu("%m.varMap ", "map"));
			parametersHolder.addView(addBlockMenu("%m.view ", "view"));
			parametersHolder.addView(addBlockMenu("%m.textview ", "textView"));
			parametersHolder.addView(addBlockMenu("%m.edittext ", "editText"));
			parametersHolder.addView(addBlockMenu("%m.imageview ", "ImageView"));
			parametersHolder.addView(addBlockMenu("%m.listview ", "listView"));
			parametersHolder.addView(addBlockMenu("%m.list ", "list"));
			parametersHolder.addView(addBlockMenu("%m.listMap ", "listMap"));
			parametersHolder.addView(addBlockMenu("%m.listStr ", "listString"));
			parametersHolder.addView(addBlockMenu("%m.listInt ", "listNumber"));
			parametersHolder.addView(addBlockMenu("%m.intent ", "intent"));
			parametersHolder.addView(addBlockMenu("%m.color ", "color"));
			parametersHolder.addView(addBlockMenu("%m.activity ", "activity"));
			parametersHolder.addView(addBlockMenu("%m.resource ", "resource"));
			parametersHolder.addView(addBlockMenu("%m.customViews ", "custom views"));
			parametersHolder.addView(addBlockMenu("%m.layout ", "layout"));
			parametersHolder.addView(addBlockMenu("%m.anim ", "anim"));
			parametersHolder.addView(addBlockMenu("%m.drawable ", "drawable"));
	}
	
	interface OnColorPickedListener {
			void onColorPicked(String str);
	}
	
	class ColorPicker {
			private OnColorPickedListener listener;
			private Context context;
			private boolean pipEnabled;
			
			ColorPicker(Context context, boolean pipEnabled) {
					this.context = context;
					this.pipEnabled = pipEnabled;
			}
			
			public void setOnColorPickedListener(OnColorPickedListener onColorPickedListener) {
					this.listener = onColorPickedListener;
			}
			
			public void pick() {
					Intent intent = new Intent(context, ColorPickerActivity.class);
					intent.putExtra("pip", pipEnabled);
					LocalBroadcastManager.getInstance(context).registerReceiver(new BroadcastReceiver() {
							@Override
							public void onReceive(Context context, Intent intent) {
									LocalBroadcastManager.getInstance(context).unregisterReceiver(this);
									if (listener != null && intent.hasExtra(TypedValues.Custom.S_COLOR)) {
											listener.onColorPicked(intent.getStringExtra(TypedValues.Custom.S_COLOR));
									}
							}
					}, new IntentFilter("data"));
					context.startActivity(intent);
			}
	}
	
	{
	}
	
	
	public void _b() {
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
	
	private void updateBlockPreview() {
			// Clear existing views in blockPreview
			blockPreview.removeAllViews();
			
			// Get current values from EditText fields
			String specText = spec.getText().toString();
			String typeText = type.getText().toString();
			String colorText = color.getText().toString();
			String codeText = code.getText().toString(); // Optional, depending on whether code affects preview
			String typeNameText = type_name.getText().toString();
			
			// Default values to avoid crashes
			if (specText.isEmpty()) specText = " "; // Default spec
			if (typeText.isEmpty()) typeText = " "; // Default to regular block type (" ")
			if (codeText.isEmpty()) codeText = "";
			if (typeNameText.isEmpty()) typeNameText = "";
			
			// Parse color, default to a fallback if invalid
			// Normalize input
			String colorTextBASE = color.getText() != null ? color.getText().toString().trim() : "";
			
			// Now check and parse
			int colorValue;
			if (colorText.isEmpty()) {
					colorValue = Color.GRAY;
			} else {
					try {
							colorValue = Color.parseColor(colorTextBASE);
					} catch (IllegalArgumentException e) {
							colorValue = Color.GRAY;
					}
			}
			
			
			
			// Add block to preview
			addBlockToPalette(blockPreview, specText, typeText, codeText, colorValue, new Object[]{specText});
	}
	
	private void loadBlockData() {
			BlockMaker maker = new BlockMaker("/storage/emulated/0/.blacklogics/resources/block/My Block/block.json");
			JSONObject block = maker.getBlockByCode(blockCode);
			if (block != null) {
					try {
							name.setText(block.getString("name"));
							type.setText(block.getString("type"));
							type_name.setText(block.getString("typeName"));
							spec.setText(block.getString("spec"));
							color.setText(block.getString("color"));
							code.setText(block.getString("code"));
					} catch (JSONException e) {
							e.printStackTrace();
							Toast.makeText(this, "Error loading block data", Toast.LENGTH_SHORT).show();
					}
			} else {
					Toast.makeText(this, "Block not found", Toast.LENGTH_SHORT).show();
					finish();
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