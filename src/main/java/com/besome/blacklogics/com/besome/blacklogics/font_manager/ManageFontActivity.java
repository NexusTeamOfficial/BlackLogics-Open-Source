package com.besome.blacklogics.font_manager;

import static mod.SketchwareUtil.dpToPx;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.besome.blacklogics.R;
import com.besome.blacklogics.base.FileUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import mod.hey.studios.util.Helper;

public class ManageFontActivity extends AppCompatActivity {
	public final int REQ_CD_FONT_PICKER = 102;
	private TabLayout tab_layout;
	private ViewPager view_pager;
	private LinearLayout layout_ads;
	private Intent fontPicker = new Intent(Intent.ACTION_GET_CONTENT);
	private String sc_id = "601";
	private ArrayList<String> selectedFontPaths = new ArrayList<>();
	private String currentFontPath;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.manage_image); // Reuse layout, assuming it has tab_layout, view_pager, layout_ads
		initialize(_savedInstanceState);
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1001);
		} else {
			setupUI();
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1001) {
			setupUI();
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		tab_layout = findViewById(R.id.tab_layout);
		view_pager = findViewById(R.id.view_pager);
		layout_ads = findViewById(R.id.layout_ads);
		fontPicker.setType("*/*");
	/*	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			fontPicker.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{
				"application/x-font-ttf",
				"application/x-font-truetype",
				"application/x-font-opentype",
				"application/x-font-otf",
				"application/font-sfnt",
				"application/vnd.ms-opentype",
				"font/ttf",
				"font/otf",
				"font/collection"
			});
		}*/
		
		fontPicker.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		sc_id = getIntent().getStringExtra("sc_id");
		
		view_pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
			@NonNull
			@Override
			public Fragment getItem(int position) {
				if (position == 0) return new FontFragment();
				return new FontCollectionFragment();
			}
			
			@Override
			public int getCount() {
				return 2;
			}
			
			@Override
			public CharSequence getPageTitle(int position) {
				return position == 0 ? "This project" : "My collection";
			}
		});
		
		tab_layout.setupWithViewPager(view_pager);
	}
	
	private void initializeLogic() {
		File collectionDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), ".blacklogics/collection/font");
		if (!collectionDir.exists()) collectionDir.mkdirs();
		File resourceDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), ".blacklogics/resources/fonts/" + sc_id);
		if (!resourceDir.exists()) resourceDir.mkdirs();
	}
	
	public void _shape(final double _top1, final double _top2, final double _bottom2, final double _bottom1,
	final String _inside_color, final String _side_color, final double _side_size, final View _view) {
		Double tlr = _top1;
		Double trr = _top2;
		Double blr = _bottom2;
		Double brr = _bottom1;
		Double sw = _side_size;
		GradientDrawable s = new GradientDrawable();
		s.setShape(GradientDrawable.RECTANGLE);
		s.setCornerRadii(new float[]{tlr.floatValue(), tlr.floatValue(), trr.floatValue(), trr.floatValue(),
			blr.floatValue(), blr.floatValue(), brr.floatValue(), brr.floatValue()});
		s.setColor(Color.parseColor(_inside_color));
		s.setStroke(sw.intValue(), Color.parseColor(_side_color));
		_view.setBackground(s);
	}
	
	public void _FontPickerDialog(final boolean _check, final String path) {
		final AlertDialog dialog = new AlertDialog.Builder(this).create();
		View inflate = getLayoutInflater().inflate(R.layout.add_font, null);
		dialog.setView(inflate);
		
		EditText fontName = inflate.findViewById(R.id.fontName);
		TextView fontPreview = inflate.findViewById(R.id.fontPreview);
		Button selectFont = inflate.findViewById(R.id.selectFont);
		CheckBox addToCollection = inflate.findViewById(R.id.addToCollection);
		TextView cancelButton = inflate.findViewById(R.id.cancelButton);
		TextView saveButton = inflate.findViewById(R.id.saveButton);
        
        fontName.setFocusable(true);
        fontName.setFocusableInTouchMode(true);
        fontName.setClickable(true);
		
		selectFont.setOnClickListener(v -> {
			startActivityForResult(fontPicker, REQ_CD_FONT_PICKER);
		});
		
		saveButton.setOnClickListener(v -> {
			String name = fontName.getText().toString().trim();
			if (name.isEmpty()) {
				showMessage("Please enter a font name");
				return;
			}
			
			File resourceDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), ".blacklogics/resources/fonts/" + sc_id);
			File destFile = new File(resourceDir, name + ".ttf");
			if (destFile.exists()) {
				showMessage("Font name already exists!");
				return;
			}
			
			if (addToCollection.isChecked()) {
				saveToCollection(name, currentFontPath);
			}
			importFonts(Arrays.asList(currentFontPath));
			dialog.dismiss();
		});
		
		cancelButton.setOnClickListener(v -> dialog.dismiss());
		
		if (_check && path != null) {
			File fontFile = new File(path);
			if (fontFile.exists()) {
				try {
					Typeface typeface = Typeface.createFromFile(fontFile);
					fontPreview.setTypeface(typeface);
					fontName.setText(fontFile.getName().replaceFirst("[.][^.]+$", ""));
				} catch (Exception e) {
					showMessage("Error loading font: " + e.getMessage());
					fontPreview.setTypeface(Typeface.DEFAULT);
				}
			} else {
				showMessage("Font file not found");
				fontPreview.setTypeface(Typeface.DEFAULT);
			}
		} else {
			fontPreview.setTypeface(Typeface.DEFAULT);
		}
		
		dialog.setCancelable(true);
		dialog.show();
	}
	private boolean isValidFontFile(String path) {
		if (path == null || path.isEmpty()) return false;
		
		String extension = path.substring(path.lastIndexOf(".") + 1).toLowerCase();
		if (!extension.equals("ttf") && !extension.equals("otf")) {
			return false;
		}
		
		// Additional validation by trying to create Typeface
		try {
			Typeface typeface = Typeface.createFromFile(path);
			return typeface != null;
		} catch (Exception e) {
			return false;
		}
	}
	
	// Then modify the onActivityResult to check the file extension:
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CD_FONT_PICKER && resultCode == RESULT_OK && data != null) {
			selectedFontPaths.clear();
			
			// Handle multiple files
			if (data.getClipData() != null) {
				for (int i = 0; i < data.getClipData().getItemCount(); i++) {
					Uri uri = data.getClipData().getItemAt(i).getUri();
					String path = FileUtil.convertUriToFilePath(getApplicationContext(), uri);
					if (path != null && isValidFontFile(path)) {
						selectedFontPaths.add(path);
					}
				}
			} 
			// Handle single file
			else if (data.getData() != null) {
				Uri uri = data.getData();
				String path = FileUtil.convertUriToFilePath(getApplicationContext(), uri);
				if (path != null && isValidFontFile(path)) {
					selectedFontPaths.add(path);
				}
			}
			
			if (!selectedFontPaths.isEmpty()) {
				currentFontPath = selectedFontPaths.get(0);
				_FontPickerDialog(true, currentFontPath);
			} else {
				showMessage("No valid font files selected (only .ttf or .otf)");
			}
		}
	}
	
	private String getRealPathFromURI(Uri uri) {
		String path = "";
		try {
			java.io.InputStream inputStream = getContentResolver().openInputStream(uri);
			File file = new File(getCacheDir(), "temp_font_" + System.currentTimeMillis() + ".ttf");
			java.io.FileOutputStream outputStream = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int len;
			while ((len = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, len);
			}
			inputStream.close();
			outputStream.close();
			path = file.getAbsolutePath();
		} catch (Exception e) {
			showMessage("Error getting font path: " + e.getMessage());
		}
		return path;
	}
	
	private void saveToCollection(String name, String path) {
		File collectionFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), ".blacklogics/collection/font_collection.json");
		List<HashMap<String, String>> collections = new ArrayList<>();
		Gson gson = new Gson();
		
		try {
			if (collectionFile.exists()) {
				collections = gson.fromJson(new FileReader(collectionFile),
				new TypeToken<List<HashMap<String, String>>>() {}.getType());
				if (collections == null) {
					collections = new ArrayList<>();
				}
			}
			
			HashMap<String, String> entry = new HashMap<>();
			entry.put("name", name);
			entry.put("path", path);
			collections.add(entry);
			
			FileWriter writer = new FileWriter(collectionFile);
			gson.toJson(collections, writer);
			writer.close();
			showMessage("Font added to collection");
		} catch (Exception e) {
			showMessage("Error saving collection: " + e.getMessage());
		}
	}
	
	public void importFonts(List<String> fontPaths) {
		File resourceDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), ".blacklogics/resources/fonts/" + sc_id);
		for (String path : fontPaths) {
			try {
				if (!isValidFontFile(path)) {
					showMessage("Skipping invalid font file: " + path);
					continue;
				}
				File sourceFile = new File(path);
				String name = sourceFile.getName();
				if (currentFontPath != null && currentFontPath.equals(path)) {
					String newName = new File(currentFontPath).getName();
					if (!newName.isEmpty() && !newName.equals(name)) {
						name = newName;
					}
				}
				File destFile = new File(resourceDir, name);
				if (destFile.exists()) {
					showMessage("Font " + name + " already exists");
					continue;
				}
				FileUtil.copyFile(sourceFile.getAbsolutePath(), destFile.getAbsolutePath());
				showMessage("Font imported successfully");
			} catch (Exception e) {
				showMessage("Error importing font: " + e.getMessage());
			}
		}
	}
	
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	private void setupUI() {
		ImageView back = findViewById(R.id.ig_toolbar_back);
		TextView title = findViewById(R.id.tx_toolbar_title);
		ImageView loadFile = findViewById(R.id.ig_toolbar_load_file);
		
		loadFile.setImageResource(R.drawable.ic_delete_btn_white_96dp);
		
		ImageView importFont = new ImageView(this);
		importFont.setLayoutParams(new LinearLayout.LayoutParams(
		dpToPx(ManageFontActivity.this, 40),
		dpToPx(ManageFontActivity.this, 40)));
		importFont.setPadding(
		dpToPx(ManageFontActivity.this, 9),
		dpToPx(ManageFontActivity.this, 9),
		dpToPx(ManageFontActivity.this, 9),
		dpToPx(ManageFontActivity.this, 9)
		);
		importFont.setImageResource(R.drawable.ic_import_white_24dp);
		importFont.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		((ViewGroup) loadFile.getParent()).addView(importFont, ((ViewGroup) loadFile.getParent()).indexOfChild(loadFile));
		Helper.applyRippleToToolbarView(importFont);
		
		Helper.applyRippleToToolbarView(back);
		back.setOnClickListener(Helper.getBackPressedClickListener(this));
		title.setText("Font manager");
		
		loadFile.setVisibility(View.VISIBLE);
		Helper.applyRippleToToolbarView(loadFile);
	}
	
	public static class FontFragment extends Fragment {
		private RecyclerView recyclerView;
		private List<File> fonts = new ArrayList<>();
		private FloatingActionButton fabImport;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.fragment_image, container, false); // Reuse layout
			recyclerView = view.findViewById(R.id.recycler_view);
			fabImport = view.findViewById(R.id.fab_import);
			
			recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
			loadFonts();
			recyclerView.setAdapter(new FontAdapter());
			
			fabImport.setOnClickListener(v -> {
				((ManageFontActivity) getActivity())._FontPickerDialog(false, null);
			});
			
			return view;
		}
		
		private void loadFonts() {
			File resourceDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), ".blacklogics/resources/fonts/" + ((ManageFontActivity) getActivity()).sc_id);
			if (resourceDir.exists()) {
				fonts = Arrays.asList(resourceDir.listFiles((dir, name) -> name.endsWith(".ttf") || name.endsWith(".otf")));
			}
		}
		
		private class FontAdapter extends RecyclerView.Adapter<FontAdapter.ViewHolder> {
			@Override
			public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
				View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false); // Reuse layout
				return new ViewHolder(view);
			}
			
			@Override
			public void onBindViewHolder(ViewHolder holder, int position) {
				File font = fonts.get(position);
				String name = font.getName().substring(0, font.getName().lastIndexOf('.'));
				holder.textView.setText(name);
				try {
					Typeface typeface = Typeface.createFromFile(font);
					holder.textView.setTypeface(typeface);
				} catch (Exception e) {
					holder.textView.setTypeface(Typeface.DEFAULT);
				}
				holder.imageView.setVisibility(View.GONE); // Hide image for fonts
			}
			
			@Override
			public int getItemCount() {
				return fonts.size();
			}
			
			class ViewHolder extends RecyclerView.ViewHolder {
				ImageView imageView;
				TextView textView;
				
				ViewHolder(View itemView) {
					super(itemView);
					imageView = itemView.findViewById(R.id.image_view);
					textView = itemView.findViewById(R.id.text_view);
				}
			}
		}
	}
	
	public static class FontCollectionFragment extends Fragment {
		private RecyclerView recyclerView;
		private LinearLayout buttonLayout;
		private Button importButton, cancelButton;
		private List<HashMap<String, String>> collections = new ArrayList<>();
		private List<Integer> selectedPositions = new ArrayList<>();
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.fragment_collection, container, false);
			recyclerView = view.findViewById(R.id.recycler_view);
			buttonLayout = view.findViewById(R.id.button_layout);
			importButton = view.findViewById(R.id.import_button);
			cancelButton = view.findViewById(R.id.cancel_button);
			
			recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
			loadCollections();
			recyclerView.setAdapter(new CollectionAdapter());
			
			importButton.setOnClickListener(v -> {
				List<String> pathsToImport = new ArrayList<>();
				for (int pos : selectedPositions) {
					pathsToImport.add(collections.get(pos).get("path"));
				}
				((ManageFontActivity) getActivity()).importFonts(pathsToImport);
				selectedPositions.clear();
				buttonLayout.setVisibility(View.GONE);
				recyclerView.getAdapter().notifyDataSetChanged();
			});
			
			cancelButton.setOnClickListener(v -> {
				selectedPositions.clear();
				buttonLayout.setVisibility(View.GONE);
				recyclerView.getAdapter().notifyDataSetChanged();
			});
			
			return view;
		}
		
		private void loadCollections() {
			File collectionFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), ".blacklogics/collection/font_collection.json");
			if (collectionFile.exists()) {
				try {
					collections = new Gson().fromJson(new FileReader(collectionFile),
					new TypeToken<List<HashMap<String, String>>>() {}.getType());
					if (collections == null) {
						collections = new ArrayList<>();
					}
				} catch (Exception e) {
					((ManageFontActivity) getActivity()).showMessage("Error loading collections");
				}
			}
		}
		
		private class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {
			@Override
			public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
				View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collection, parent, false);
				return new ViewHolder(view);
			}
			
			@Override
			public void onBindViewHolder(ViewHolder holder, int position) {
				HashMap<String, String> item = collections.get(position);
				holder.textView.setText(item.get("name"));
				try {
					Typeface typeface = Typeface.createFromFile(new File(item.get("path")));
					holder.textView.setTypeface(typeface);
				} catch (Exception e) {
					holder.textView.setTypeface(Typeface.DEFAULT);
				}
				holder.imageView.setVisibility(View.GONE); // Hide image for fonts
				holder.checkBox.setChecked(selectedPositions.contains(position));
				
				holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
					if (isChecked) {
						selectedPositions.add(position);
					} else {
						selectedPositions.remove(Integer.valueOf(position));
					}
					buttonLayout.setVisibility(selectedPositions.isEmpty() ? View.GONE : View.VISIBLE);
				});
			}
			
			@Override
			public int getItemCount() {
				return collections.size();
			}
			
			class ViewHolder extends RecyclerView.ViewHolder {
				ImageView imageView;
				TextView textView;
				CheckBox checkBox;
				
				ViewHolder(View itemView) {
					super(itemView);
					imageView = itemView.findViewById(R.id.image_view);
					textView = itemView.findViewById(R.id.text_view);
					checkBox = itemView.findViewById(R.id.check_box);
				}
			}
		}
	}
}
