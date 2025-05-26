package com.nexusteam.internal.os.layouteditor.color;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

//import com.nexusteam.internal.os.layouteditor.*;
import com.besome.blacklogics.*;

public class LinearColorPicker extends LinearLayout {
	
	private EditText hexInput;
	private Button addButton;
	private LinearLayout customColorsContainer;
	private LinearLayout shadesContainer;
	private OnColorSelectedListener colorSelectedListener;
	private List<Integer> customColors = new ArrayList<>();
	private View colorPreview;
	private View selectedIndicator;
	private int currentlySelectedColor = Color.TRANSPARENT;
	
	// Color shades structure
	private Map<String, List<Integer>> colorShades = new HashMap<>();
	private static final Pattern HEX_PATTERN = Pattern.compile("^#?([A-Fa-f0-9]{6}|[A-Fa-f0-9]{8})$");
	
	public interface OnColorSelectedListener {
		void onColorSelected(int color);
	}
	
	public LinearColorPicker(Context context) {
		super(context);
		init(context);
	}
	
	public LinearColorPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public LinearColorPicker(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}
	
	private void init(Context context) {
		LayoutInflater.from(context).inflate(R.layout.color_picker_advanced, this, true);
		setOrientation(VERTICAL);
		
		hexInput = findViewById(R.id.hexInput);
		addButton = findViewById(R.id.addButton);
		customColorsContainer = findViewById(R.id.customColorsContainer);
		shadesContainer = findViewById(R.id.shadesContainer);
		colorPreview = findViewById(R.id.colorPreview);
		selectedIndicator = findViewById(R.id.selectedIndicator);
		
		hexInput.setFocusable(true);
		hexInput.setFocusableInTouchMode(true);
		hexInput.setClickable(true);
		
		initializeAllColorShades();
		setupHexInputListener();
		setupAddButton();
		loadCustomColorsFromStorage();
		showAllColorFamilies();
	}
	
	private void initializeAllColorShades() {
		// RED shades
		List<Integer> redShades = new ArrayList<>();
		redShades.add(Color.parseColor("#FFEBEE"));
		redShades.add(Color.parseColor("#FFCDD2"));
		redShades.add(Color.parseColor("#EF9A9A"));
		redShades.add(Color.parseColor("#E57373"));
		redShades.add(Color.parseColor("#EF5350"));
		redShades.add(Color.parseColor("#F44336"));
		redShades.add(Color.parseColor("#E53935"));
		redShades.add(Color.parseColor("#D32F2F"));
		colorShades.put("RED", redShades);
		
		// PINK shades
		List<Integer> pinkShades = new ArrayList<>();
		pinkShades.add(Color.parseColor("#FCE4EC"));
		pinkShades.add(Color.parseColor("#F8BBD0"));
		pinkShades.add(Color.parseColor("#F48FB1"));
		pinkShades.add(Color.parseColor("#F06292"));
		pinkShades.add(Color.parseColor("#EC407A"));
		pinkShades.add(Color.parseColor("#E91E63"));
		pinkShades.add(Color.parseColor("#D81B60"));
		pinkShades.add(Color.parseColor("#C2185B"));
		colorShades.put("PINK", pinkShades);
		
		// PURPLE shades
		List<Integer> purpleShades = new ArrayList<>();
		purpleShades.add(Color.parseColor("#F3E5F5"));
		purpleShades.add(Color.parseColor("#E1BEE7"));
		purpleShades.add(Color.parseColor("#CE93D8"));
		purpleShades.add(Color.parseColor("#BA68C8"));
		purpleShades.add(Color.parseColor("#AB47BC"));
		purpleShades.add(Color.parseColor("#9C27B0"));
		purpleShades.add(Color.parseColor("#8E24AA"));
		purpleShades.add(Color.parseColor("#7B1FA2"));
		colorShades.put("PURPLE", purpleShades);
		
		// DEEP PURPLE
		List<Integer> deepPurpleShades = new ArrayList<>();
		deepPurpleShades.add(Color.parseColor("#EDE7F6"));
		deepPurpleShades.add(Color.parseColor("#D1C4E9"));
		deepPurpleShades.add(Color.parseColor("#B39DDB"));
		deepPurpleShades.add(Color.parseColor("#9575CD"));
		deepPurpleShades.add(Color.parseColor("#7E57C2"));
		deepPurpleShades.add(Color.parseColor("#673AB7"));
		deepPurpleShades.add(Color.parseColor("#5E35B1"));
		deepPurpleShades.add(Color.parseColor("#512DA8"));
		colorShades.put("DEEP PURPLE", deepPurpleShades);
		
		// BLUE shades
		List<Integer> blueShades = new ArrayList<>();
		blueShades.add(Color.parseColor("#E3F2FD"));
		blueShades.add(Color.parseColor("#BBDEFB"));
		blueShades.add(Color.parseColor("#90CAF9"));
		blueShades.add(Color.parseColor("#64B5F6"));
		blueShades.add(Color.parseColor("#42A5F5"));
		blueShades.add(Color.parseColor("#2196F3"));
		blueShades.add(Color.parseColor("#1E88E5"));
		blueShades.add(Color.parseColor("#1976D2"));
		colorShades.put("BLUE", blueShades);
		
		// GREEN shades
		List<Integer> greenShades = new ArrayList<>();
		greenShades.add(Color.parseColor("#E8F5E9"));
		greenShades.add(Color.parseColor("#C8E6C9"));
		greenShades.add(Color.parseColor("#A5D6A7"));
		greenShades.add(Color.parseColor("#81C784"));
		greenShades.add(Color.parseColor("#66BB6A"));
		greenShades.add(Color.parseColor("#4CAF50"));
		greenShades.add(Color.parseColor("#43A047"));
		greenShades.add(Color.parseColor("#388E3C"));
		colorShades.put("GREEN", greenShades);
		
		// LIGHT GREEN shades
		List<Integer> lightGreenShades = new ArrayList<>();
		lightGreenShades.add(Color.parseColor("#F1F8E9"));
		lightGreenShades.add(Color.parseColor("#DCEDC8"));
		lightGreenShades.add(Color.parseColor("#C5E1A5"));
		lightGreenShades.add(Color.parseColor("#AED581"));
		lightGreenShades.add(Color.parseColor("#9CCC65"));
		lightGreenShades.add(Color.parseColor("#8BC34A"));
		lightGreenShades.add(Color.parseColor("#7CB342"));
		lightGreenShades.add(Color.parseColor("#689F38"));
		colorShades.put("LIGHT GREEN", lightGreenShades);
		
		// TEAL shades
		List<Integer> tealShades = new ArrayList<>();
		tealShades.add(Color.parseColor("#E0F2F1"));
		tealShades.add(Color.parseColor("#B2DFDB"));
		tealShades.add(Color.parseColor("#80CBC4"));
		tealShades.add(Color.parseColor("#4DB6AC"));
		tealShades.add(Color.parseColor("#26A69A"));
		tealShades.add(Color.parseColor("#009688"));
		tealShades.add(Color.parseColor("#00897B"));
		tealShades.add(Color.parseColor("#00796B"));
		colorShades.put("TEAL", tealShades);
		
		// CYAN shades
		List<Integer> cyanShades = new ArrayList<>();
		cyanShades.add(Color.parseColor("#E0F7FA"));
		cyanShades.add(Color.parseColor("#B2EBF2"));
		cyanShades.add(Color.parseColor("#80DEEA"));
		cyanShades.add(Color.parseColor("#4DD0E1"));
		cyanShades.add(Color.parseColor("#26C6DA"));
		cyanShades.add(Color.parseColor("#00BCD4"));
		cyanShades.add(Color.parseColor("#00ACC1"));
		cyanShades.add(Color.parseColor("#0097A7"));
		colorShades.put("CYAN", cyanShades);
		
		// ORANGE shades
		List<Integer> orangeShades = new ArrayList<>();
		orangeShades.add(Color.parseColor("#FFF3E0"));
		orangeShades.add(Color.parseColor("#FFE0B2"));
		orangeShades.add(Color.parseColor("#FFCC80"));
		orangeShades.add(Color.parseColor("#FFB74D"));
		orangeShades.add(Color.parseColor("#FFA726"));
		orangeShades.add(Color.parseColor("#FF9800"));
		orangeShades.add(Color.parseColor("#FB8C00"));
		orangeShades.add(Color.parseColor("#F57C00"));
		colorShades.put("ORANGE", orangeShades);
		
		// BROWN shades
		List<Integer> brownShades = new ArrayList<>();
		brownShades.add(Color.parseColor("#EFEBE9"));
		brownShades.add(Color.parseColor("#D7CCC8"));
		brownShades.add(Color.parseColor("#BCAAA4"));
		brownShades.add(Color.parseColor("#A1887F"));
		brownShades.add(Color.parseColor("#8D6E63"));
		brownShades.add(Color.parseColor("#795548"));
		brownShades.add(Color.parseColor("#6D4C41"));
		brownShades.add(Color.parseColor("#5D4037"));
		colorShades.put("BROWN", brownShades);
		
		// YELLOW shades
		List<Integer> yellowShades = new ArrayList<>();
		yellowShades.add(Color.parseColor("#FFFDE7"));
		yellowShades.add(Color.parseColor("#FFF9C4"));
		yellowShades.add(Color.parseColor("#FFF59D"));
		yellowShades.add(Color.parseColor("#FFF176"));
		yellowShades.add(Color.parseColor("#FFEE58"));
		yellowShades.add(Color.parseColor("#FFEB3B"));
		yellowShades.add(Color.parseColor("#FDD835"));
		yellowShades.add(Color.parseColor("#FBC02D"));
		colorShades.put("YELLOW", yellowShades);
		
		// AMBER shades
		List<Integer> amberShades = new ArrayList<>();
		amberShades.add(Color.parseColor("#FFF8E1"));
		amberShades.add(Color.parseColor("#FFECB3"));
		amberShades.add(Color.parseColor("#FFE082"));
		amberShades.add(Color.parseColor("#FFD54F"));
		amberShades.add(Color.parseColor("#FFCA28"));
		amberShades.add(Color.parseColor("#FFC107"));
		amberShades.add(Color.parseColor("#FFB300"));
		amberShades.add(Color.parseColor("#FFA000"));
		colorShades.put("AMBER", amberShades);
		
		// LIME shades
		List<Integer> limeShades = new ArrayList<>();
		limeShades.add(Color.parseColor("#F9FBE7"));
		limeShades.add(Color.parseColor("#F0F4C3"));
		limeShades.add(Color.parseColor("#E6EE9C"));
		limeShades.add(Color.parseColor("#DCE775"));
		limeShades.add(Color.parseColor("#D4E157"));
		limeShades.add(Color.parseColor("#CDDC39"));
		limeShades.add(Color.parseColor("#C0CA33"));
		limeShades.add(Color.parseColor("#AFB42B"));
		colorShades.put("LIME", limeShades);
		
		// DEEP ORANGE shades
		List<Integer> deepOrangeShades = new ArrayList<>();
		deepOrangeShades.add(Color.parseColor("#FBE9E7"));
		deepOrangeShades.add(Color.parseColor("#FFCCBC"));
		deepOrangeShades.add(Color.parseColor("#FFAB91"));
		deepOrangeShades.add(Color.parseColor("#FF8A65"));
		deepOrangeShades.add(Color.parseColor("#FF7043"));
		deepOrangeShades.add(Color.parseColor("#FF5722"));
		deepOrangeShades.add(Color.parseColor("#F4511E"));
		deepOrangeShades.add(Color.parseColor("#E64A19"));
		colorShades.put("DEEP ORANGE", deepOrangeShades);
		
		// BLUE GRAY shades
		List<Integer> blueGrayShades = new ArrayList<>();
		blueGrayShades.add(Color.parseColor("#ECEFF1"));
		blueGrayShades.add(Color.parseColor("#CFD8DC"));
		blueGrayShades.add(Color.parseColor("#B0BEC5"));
		blueGrayShades.add(Color.parseColor("#90A4AE"));
		blueGrayShades.add(Color.parseColor("#78909C"));
		blueGrayShades.add(Color.parseColor("#607D8B"));
		blueGrayShades.add(Color.parseColor("#546E7A"));
		blueGrayShades.add(Color.parseColor("#455A64"));
		colorShades.put("BLUE GRAY", blueGrayShades);
		
		// GRAY shades
		List<Integer> grayShades = new ArrayList<>();
		grayShades.add(Color.parseColor("#FAFAFA"));
		grayShades.add(Color.parseColor("#F5F5F5"));
		grayShades.add(Color.parseColor("#EEEEEE"));
		grayShades.add(Color.parseColor("#E0E0E0"));
		grayShades.add(Color.parseColor("#BDBDBD"));
		grayShades.add(Color.parseColor("#9E9E9E"));
		grayShades.add(Color.parseColor("#757575"));
		grayShades.add(Color.parseColor("#616161"));
		colorShades.put("GRAY", grayShades);
		
		// RED Tints
		List<Integer> redTints = new ArrayList<>();
		redTints.add(Color.parseColor("#FF8A80")); // Lighter
		redTints.add(Color.parseColor("#FF5252"));
		redTints.add(Color.parseColor("#FF1744")); // Darker
		redTints.add(Color.parseColor("#D50000")); // Darkest
		colorShades.put("RED TINT", redTints);
		
		// BLUE Tints
		List<Integer> blueTints = new ArrayList<>();
		blueTints.add(Color.parseColor("#82B1FF")); // Lighter
		blueTints.add(Color.parseColor("#448AFF"));
		blueTints.add(Color.parseColor("#2979FF")); // Darker
		blueTints.add(Color.parseColor("#2962FF")); // Darkest
		colorShades.put("BLUE TINT", blueTints);
		
		// GREEN Tints
		List<Integer> greenTints = new ArrayList<>();
		greenTints.add(Color.parseColor("#B9F6CA")); // Lighter
		greenTints.add(Color.parseColor("#69F0AE"));
		greenTints.add(Color.parseColor("#00E676")); // Darker
		greenTints.add(Color.parseColor("#00C853")); // Darkest
		colorShades.put("GREEN TINT", greenTints);
		
		// INDIGO shades
		List<Integer> indigoShades = new ArrayList<>();
		indigoShades.add(Color.parseColor("#E8EAF6"));
		indigoShades.add(Color.parseColor("#C5CAE9"));
		indigoShades.add(Color.parseColor("#9FA8DA"));
		indigoShades.add(Color.parseColor("#7986CB"));
		indigoShades.add(Color.parseColor("#5C6BC0"));
		indigoShades.add(Color.parseColor("#3F51B5"));
		indigoShades.add(Color.parseColor("#3949AB"));
		indigoShades.add(Color.parseColor("#303F9F"));
		colorShades.put("INDIGO", indigoShades);
		
		// LIGHT BLUE shades
		List<Integer> lightBlueShades = new ArrayList<>();
		lightBlueShades.add(Color.parseColor("#E1F5FE"));
		lightBlueShades.add(Color.parseColor("#B3E5FC"));
		lightBlueShades.add(Color.parseColor("#81D4FA"));
		lightBlueShades.add(Color.parseColor("#4FC3F7"));
		lightBlueShades.add(Color.parseColor("#29B6F6"));
		lightBlueShades.add(Color.parseColor("#03A9F4"));
		lightBlueShades.add(Color.parseColor("#039BE5"));
		lightBlueShades.add(Color.parseColor("#0288D1"));
		colorShades.put("LIGHT BLUE", lightBlueShades);
		
		// DARK BLUE shades
		List<Integer> darkBlueShades = new ArrayList<>();
		darkBlueShades.add(Color.parseColor("#1565C0"));
		darkBlueShades.add(Color.parseColor("#0D47A1"));
		darkBlueShades.add(Color.parseColor("#08378A"));
		darkBlueShades.add(Color.parseColor("#062E73"));
		darkBlueShades.add(Color.parseColor("#041E52"));
		colorShades.put("DARK BLUE", darkBlueShades);
		
		// TURQUOISE shades
		List<Integer> turquoiseShades = new ArrayList<>();
		turquoiseShades.add(Color.parseColor("#E0F7FA"));
		turquoiseShades.add(Color.parseColor("#B2EBF2"));
		turquoiseShades.add(Color.parseColor("#80DEEA"));
		turquoiseShades.add(Color.parseColor("#4DD0E1"));
		turquoiseShades.add(Color.parseColor("#26C6DA"));
		turquoiseShades.add(Color.parseColor("#00ACC1"));
		turquoiseShades.add(Color.parseColor("#00838F"));
		turquoiseShades.add(Color.parseColor("#006064"));
		colorShades.put("TURQUOISE", turquoiseShades);
		
		// GOLD shades
		List<Integer> goldShades = new ArrayList<>();
		goldShades.add(Color.parseColor("#FFF8E1"));
		goldShades.add(Color.parseColor("#FFECB3"));
		goldShades.add(Color.parseColor("#FFE082"));
		goldShades.add(Color.parseColor("#FFD54F"));
		goldShades.add(Color.parseColor("#FFC107"));
		goldShades.add(Color.parseColor("#FFB300"));
		goldShades.add(Color.parseColor("#FFA000"));
		goldShades.add(Color.parseColor("#FF6F00"));
		colorShades.put("GOLD", goldShades);
		
		// SILVER shades
		List<Integer> silverShades = new ArrayList<>();
		silverShades.add(Color.parseColor("#ECEFF1"));
		silverShades.add(Color.parseColor("#CFD8DC"));
		silverShades.add(Color.parseColor("#B0BEC5"));
		silverShades.add(Color.parseColor("#90A4AE"));
		silverShades.add(Color.parseColor("#78909C"));
		silverShades.add(Color.parseColor("#607D8B"));
		silverShades.add(Color.parseColor("#546E7A"));
		silverShades.add(Color.parseColor("#455A64"));
		colorShades.put("SILVER", silverShades);
		
		// BRONZE shades
		List<Integer> bronzeShades = new ArrayList<>();
		bronzeShades.add(Color.parseColor("#FBE9E7"));
		bronzeShades.add(Color.parseColor("#FFCCBC"));
		bronzeShades.add(Color.parseColor("#FFAB91"));
		bronzeShades.add(Color.parseColor("#FF8A65"));
		bronzeShades.add(Color.parseColor("#FF7043"));
		bronzeShades.add(Color.parseColor("#D84315"));
		bronzeShades.add(Color.parseColor("#BF360C"));
		colorShades.put("BRONZE", bronzeShades);
		
		// BLACK shades
		List<Integer> blackShades = new ArrayList<>();
		blackShades.add(Color.parseColor("#000000"));
		blackShades.add(Color.parseColor("#1C1C1C"));
		blackShades.add(Color.parseColor("#2F2F2F"));
		blackShades.add(Color.parseColor("#424242"));
		blackShades.add(Color.parseColor("#616161"));
		blackShades.add(Color.parseColor("#757575"));
		blackShades.add(Color.parseColor("#9E9E9E"));
		blackShades.add(Color.parseColor("#BDBDBD"));
		colorShades.put("BLACK", blackShades);
		
		
		
		// Add more color families...
	}
	
	private void showAllColorFamilies() {
		shadesContainer.removeAllViews();
		
		for (Map.Entry<String, List<Integer>> entry : colorShades.entrySet()) {
			showColorShades(entry.getKey());
		}
	}
	
	private void setupHexInputListener() {
		hexInput.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// Enable editing while typing
				hexInput.setEnabled(true);
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				updateColorPreview(s.toString());
			}
		});
	}
	
	private void updateColorPreview(String hex) {
		if (hex.isEmpty()) {
			colorPreview.setBackgroundColor(Color.TRANSPARENT);
			return;
		}
		
		try {
			String properHex = hex.startsWith("#") ? hex : "#" + hex;
			int color = Color.parseColor(properHex);
			colorPreview.setBackgroundColor(color);
		} catch (IllegalArgumentException e) {
			colorPreview.setBackgroundColor(Color.TRANSPARENT);
		}
	}
	
	private void setupAddButton() {
		addButton.setOnClickListener(v -> {
			String hex = hexInput.getText().toString().trim();
			if (hex.isEmpty()) {
				Toast.makeText(getContext(), "Please enter a color code", Toast.LENGTH_SHORT).show();
				return;
			}
			
			if (!isValidHex(hex)) {
				Toast.makeText(getContext(), "Invalid hex format (use #RRGGBB or #AARRGGBB)", Toast.LENGTH_SHORT).show();
				return;
			}
			
			try {
				String properHex = hex.startsWith("#") ? hex : "#" + hex;
				int color = Color.parseColor(properHex);
				addCustomColor(color);
				saveCustomColorToStorage(color);
				hexInput.setText("");
			} catch (IllegalArgumentException e) {
				Toast.makeText(getContext(), "Invalid color code", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private boolean isValidHex(String hex) {
		return HEX_PATTERN.matcher(hex).matches();
	}
	
	private void showColorShades(String colorFamily) {
		TextView title = new TextView(getContext());
		title.setText(colorFamily);
		title.setTextSize(16);
		title.setTextColor(Color.BLACK);
		title.setPadding(0, 16, 0, 8);
		shadesContainer.addView(title);
		
		HorizontalScrollView scrollView = new HorizontalScrollView(getContext());
		LinearLayout shadesRow = new LinearLayout(getContext());
		shadesRow.setOrientation(LinearLayout.HORIZONTAL);
		
		List<Integer> shades = colorShades.get(colorFamily);
		if (shades != null) {
			for (int shade : shades) {
				View shadeView = createColorView(shade, dpToPx(48), dpToPx(48));
				shadesRow.addView(shadeView);
			}
		}
		
		scrollView.addView(shadesRow);
		shadesContainer.addView(scrollView);
	}
	
	private void addCustomColor(int color) {
		if (!customColors.contains(color)) {
			customColors.add(color);
			View colorView = createColorView(color, dpToPx(40), dpToPx(40));
			customColorsContainer.addView(colorView);
		}
	}
	
	private View createColorView(int color, int width, int height) {
		LinearLayout container = new LinearLayout(getContext());
		container.setOrientation(LinearLayout.VERTICAL);
		
		// Main color view
		View colorView = new View(getContext());
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
		params.setMargins(dpToPx(4), dpToPx(4), dpToPx(4), dpToPx(4));
		colorView.setLayoutParams(params);
		colorView.setBackgroundColor(color);
		
		// Selection indicator (initially hidden)
		View indicator = new View(getContext());
		LinearLayout.LayoutParams indicatorParams = new LinearLayout.LayoutParams(dpToPx(8), dpToPx(8));
		indicatorParams.gravity = android.view.Gravity.CENTER_HORIZONTAL;
		indicator.setLayoutParams(indicatorParams);
		indicator.setBackgroundResource(R.drawable.selected_indicator);
		indicator.setVisibility(color == currentlySelectedColor ? View.VISIBLE : View.INVISIBLE);
		
		container.addView(colorView);
		container.addView(indicator);
		
		container.setOnClickListener(v -> {
			// Update selection
			currentlySelectedColor = color;
			updateSelectionIndicators();
			
			// Show selection on main indicator
			selectedIndicator.setBackgroundColor(color);
			
			if (colorSelectedListener != null) {
				colorSelectedListener.onColorSelected(color);
			}
		});
		
		return container;
	}
	
	private void updateSelectionIndicators() {
		for (int i = 0; i < customColorsContainer.getChildCount(); i++) {
			View container = customColorsContainer.getChildAt(i);
			View indicator = ((LinearLayout)container).getChildAt(1);
			int color = ((View)((LinearLayout)container).getChildAt(0)).getSolidColor();
			indicator.setVisibility(color == currentlySelectedColor ? View.VISIBLE : View.INVISIBLE);
		}
		
		// Also update shades selection indicators if needed
	}
	
	private void saveCustomColorToStorage(int color) {
		File colorsDir = new File("/storage/emulated/0/.blacklogics/data/");
		if (!colorsDir.exists()) {
			colorsDir.mkdirs();
		}
		
		File colorsFile = new File(colorsDir, "custom_colors.xml");
		try (FileOutputStream fos = new FileOutputStream(colorsFile, true)) {
			String colorEntry = String.format("<color name=\"custom_%d\">#%08X</color>\n", 
			System.currentTimeMillis(), color);
			fos.write(colorEntry.getBytes());
			Toast.makeText(getContext(), "Color saved to storage", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			Toast.makeText(getContext(), "Failed to save color: " + e.toString(), Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}
	
	private void loadCustomColorsFromStorage() {
		customColorsContainer.removeAllViews();
		customColors.clear();
		
		File colorsFile = new File("/storage/emulated/0/.blacklogics/data/custom_colors.xml");
		if (!colorsFile.exists()) return;
		
		try (FileInputStream fis = new FileInputStream(colorsFile)) {
			byte[] buffer = new byte[(int) colorsFile.length()];
			fis.read(buffer);
			String xml = new String(buffer);
			
			// Simple parsing - in real app use XML parser
			String[] parts = xml.split("<color name=\"custom_");
			for (int i = 1; i < parts.length; i++) {
				String colorPart = parts[i].split("</color>")[0];
				String hexValue = colorPart.split(">")[1];
				if (hexValue.startsWith("#")) {
					try {
						int color = Color.parseColor(hexValue);
						addCustomColor(color);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setSelectedColor(int color) {
		currentlySelectedColor = color;
		if (colorPreview != null) {
			colorPreview.setBackgroundColor(color);
		}
		if (colorSelectedListener != null) {
			colorSelectedListener.onColorSelected(color);
		}
	}
	
	
	public void setOnColorSelectedListener(OnColorSelectedListener listener) {
		this.colorSelectedListener = listener;
	}
	
	private int dpToPx(int dp) {
		float density = getContext().getResources().getDisplayMetrics().density;
		return Math.round(dp * density);
	}
}
