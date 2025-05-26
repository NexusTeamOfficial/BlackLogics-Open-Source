package com.besome.blacklogics.block_manager;

import android.app.Dialog;
import android.content.Intent;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.besome.blacklogics.R;
import com.besome.blacklogics.BlocksManagerDetailsActivity;
import com.besome.blacklogics.file.PaletteMaker;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import com.nexusteam.internal.os.layouteditor.color.ColorPickerActivity;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import mod.hey.studios.util.Helper;
import com.google.android.material.*;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class BlocksManagerActivity extends AppCompatActivity {
	
	public interface OnItemClickListener {
		void onItemClick(int position, HashMap<String, Object> item);
	}
	
	private RecyclerView paletteView;
	private TextView paletteCounter;
	private ArrayList<HashMap<String, Object>> paletteList = new ArrayList<>();
	private PaletteViewAdapter adapter;
	private FloatingActionButton add_palette;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blocks_manager);
		initialize();
		initializeLogic();
	}
	
	private void initialize() {
		paletteView = findViewById(R.id.paletteView);
		paletteCounter = findViewById(R.id.paletteCounter);
		add_palette = findViewById(R.id.add_palette);
	}
	
	private void initializeLogic() {
		ImageView back = findViewById(R.id.ig_toolbar_back);
		TextView title = findViewById(R.id.tx_toolbar_title);
		ImageView loadFile = findViewById(R.id.ig_toolbar_load_file);
		
		Helper.applyRippleToToolbarView(back);
		back.setOnClickListener(Helper.getBackPressedClickListener(this));
		title.setText("Block Manager");
		loadFile.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
		loadFile.setImageResource(R.drawable.ic_more_vert_black);
		loadFile.setVisibility(View.GONE);
		Helper.applyRippleToToolbarView(loadFile);
		
		// Setup RecyclerView
		paletteView.setLayoutManager(new LinearLayoutManager(this));
		adapter = new PaletteViewAdapter(paletteList);
		paletteView.setAdapter(adapter);
		
		adapter.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(int position, HashMap<String, Object> item) {
				Intent intent = new Intent(BlocksManagerActivity.this, BlocksManagerDetailsActivity.class);
				// Use position + 6 for paletteIndex
				intent.putExtra("paletteIndex", String.valueOf(position + 6));
				startActivity(intent);
			}
		});
		
		add_palette.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Dialog dialog = new Dialog(BlocksManagerActivity.this);
				dialog.setContentView(R.layout.dialog_create_palette);
				
				EditText paletteNameInput = dialog.findViewById(R.id.palette_name_input);
				EditText colorHexInput = dialog.findViewById(R.id.color_hex_input);
				ImageView colorPickerIcon = dialog.findViewById(R.id.color_picker_icon);
				Button cancelButton = dialog.findViewById(R.id.cancel_button);
				Button saveButton = dialog.findViewById(R.id.save_button);
				
				// Ensure EditText fields are enabled and editable
				paletteNameInput.setEnabled(true);
				paletteNameInput.setFocusable(true);
				paletteNameInput.setFocusableInTouchMode(true);
				
				colorHexInput.setEnabled(true);
				colorHexInput.setFocusable(true);
				colorHexInput.setFocusableInTouchMode(true);
				
				// Automatically add # at the start if not present
				colorHexInput.addTextChangedListener(new TextWatcher() {
					@Override
					public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
					
					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count) {}
					
					@Override
					public void afterTextChanged(Editable s) {
						String input = s.toString();
						
						// Ensure the input starts with #
						if (!input.startsWith("#")) {
							colorHexInput.setText("#" + input);
							colorHexInput.setSelection(colorHexInput.getText().length()); // Move cursor to end
						}
						
						// Validate hex code
						if (input.length() > 1) { // Ignore the # for validation
							String hexPart = input.substring(1); // Get part after #
							if (!hexPart.matches("[0-9A-Fa-f]{0,6}")) { // Allow 0-6 hex chars
								colorHexInput.setError("Invalid hex code! Use 0-9, A-F (e.g., #FF0000)");
							} else if (hexPart.length() != 6 && hexPart.length() != 3 && hexPart.length() != 0) {
								colorHexInput.setError("Hex code must be 3 or 6 characters long (e.g., #FFF or #FFFFFF)");
							} else {
								colorHexInput.setError(null); // Clear error if valid
							}
						}
					}
				});
				
				// Request focus on the first field
				paletteNameInput.requestFocus();
				
				cancelButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				
				colorPickerIcon.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						ColorPicker colorPicker = new ColorPicker(BlocksManagerActivity.this, false); // pipEnabled = false
						colorPicker.setOnColorPickedListener(new OnColorPickedListener() {
							@Override
							public void onColorPicked(String str) {
								try {
								   colorHexInput.setText(str);
								} catch (Exception e) {
								
								}
							}
						});
						
						// Launch color picker
						colorPicker.pick();
						
					}
				});
				
				saveButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						String paletteName = paletteNameInput.getText().toString();
						String colorHex = colorHexInput.getText().toString();
						
						// Final validation before saving
						if (colorHex.length() != 4 && colorHex.length() != 7) { // #FFF (4 chars) or #FFFFFF (7 chars)
							colorHexInput.setError("Hex code must be 3 or 6 characters long!");
							return;
						}
						
						PaletteMaker maker = new PaletteMaker("/storage/emulated/0/.blacklogics/resources/block/My Block/palette.json");
						maker.addPalette(paletteName, colorHex);
						
						dialog.dismiss();
						adapter.notifyDataSetChanged();
					}
				});
				
				dialog.show();
			}
		});
		
		loadPalettesFromJson();
	}
	
	private void loadPalettesFromJson() {
		String palettePath = "/storage/emulated/0/.blacklogics/resources/block/My Block/palette.json";
		String blockPath = "/storage/emulated/0/.blacklogics/resources/block/My Block/block.json";
		File paletteFile = new File(palettePath);
		File blockFile = new File(blockPath);
		
		// Load block counts from block1.json
		HashMap<String, Integer> blockCounts = new HashMap<>();
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
					String paletteId = blockObj.getString("palette");
					blockCounts.put(paletteId, blockCounts.getOrDefault(paletteId, 0) + 1);
				}
			} catch (Exception e) {
				e.printStackTrace();
				//showMessage("Error loading blocks: " + e.getMessage());
			}
		} else {
			//	showMessage("Block file not found");
		}
		
		// Load palettes from palette.json
		if (paletteFile.exists()) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(paletteFile));
				StringBuilder jsonString = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					jsonString.append(line);
				}
				reader.close();
				
				JSONArray jsonArray = new JSONArray(jsonString.toString());
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject paletteObj = jsonArray.getJSONObject(i);
					
					HashMap<String, Object> paletteMap = new HashMap<>();
					paletteMap.put("name", paletteObj.getString("name"));
					paletteMap.put("color", paletteObj.getString("color"));
					// Map position to palette ID (position + 6)
					String paletteId = String.valueOf(i + 6);
					// Get block count for this palette ID
					int count = blockCounts.getOrDefault(paletteId, 0);
					paletteMap.put("count", count);
					
					paletteList.add(paletteMap);
				}
				
				// Update counter text
				paletteCounter.setText(paletteList.size() + " palettes");
				adapter.notifyDataSetChanged();
				
			} catch (Exception e) {
				e.printStackTrace();
				//	showMessage("Error loading palettes: " + e.getMessage());
			}
		} else {
			//	showMessage("Palette file not found");
		}
	}
	
	public class PaletteViewAdapter extends RecyclerView.Adapter<PaletteViewAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> data;
		private OnItemClickListener listener;
		
		public PaletteViewAdapter(ArrayList<HashMap<String, Object>> arr) {
			data = arr;
		}
		
		public void setOnItemClickListener(OnItemClickListener listener) {
			this.listener = listener;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater inflater = getLayoutInflater();
			View view = inflater.inflate(R.layout.block_manager_view, parent, false);
			return new ViewHolder(view);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder holder, int position) {
			HashMap<String, Object> item = data.get(position);
			
			holder.paletteName.setText(item.get("name").toString());
			// Show block count
			holder.paletteCounter.setText(item.get("count").toString() + " blocks");
			
			try {
				int color = android.graphics.Color.parseColor(item.get("color").toString());
				holder.paletteColor.setBackgroundColor(color);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			holder.itemView.setOnClickListener(v -> {
				if (listener != null) {
					listener.onItemClick(position, item);
				}
			});
		}
		
		@Override
		public int getItemCount() {
			return data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			LinearLayout linear1, linear3;
			TextView paletteName, paletteCounter;
			View paletteColor;
			
			public ViewHolder(View itemView) {
				super(itemView);
				linear1 = itemView.findViewById(R.id.linear1);
				paletteColor = itemView.findViewById(R.id.paletteColor);
				linear3 = itemView.findViewById(R.id.linear3);
				paletteName = itemView.findViewById(R.id.paletteName);
				paletteCounter = itemView.findViewById(R.id.paletteCounter);
			}
		}
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
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
	
}
