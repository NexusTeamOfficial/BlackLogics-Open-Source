package com.nexusteam.internal.os.layouteditor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.besome.blacklogics.R;
import com.besome.blacklogics.WidgetAttributesManager; // Add import

import com.nexusteam.internal.os.layouteditor.color.LinearColorPicker;
import com.nexusteam.internal.os.layouteditor.model.FileItem;
import com.nexusteam.internal.os.layouteditor.adapter.FileListAdapter;
import com.nexusteam.internal.os.layouteditor.util.WidgetUtil;
import com.nexusteam.internal.os.layouteditor.widget.Widget;
import com.nexusteam.internal.os.layouteditor.widget.WidgetTextView;
import com.nexusteam.internal.os.layouteditor.widget.WidgetImageView;
import com.nexusteam.internal.os.layouteditor.widget.WidgetWebView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import android.util.SparseBooleanArray;

public class WidgetDialogManager {
	private final Context context;
	private final UndoRedoManager undoRedoManager;
	private WidgetAttributesManager attributesManager; // Add manager
	private String activityName; // Add activity context
	private View selectedWidget;
	
	public WidgetDialogManager(Context context, UndoRedoManager undoRedoManager) {
		this.context = context;
		this.undoRedoManager = undoRedoManager;
		this.activityName = ""; // Default value
		this.attributesManager = new WidgetAttributesManager(context, "", "", "", "");
	}
	
	public void setSelectedWidget(View widget) {
		this.selectedWidget = widget;
	}
	
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
	public void setAttributesManager(WidgetAttributesManager attributesManager) {
		this.attributesManager = attributesManager;
	}
	
	public void showTextWidgetDialog(String title, String message) {
		LayoutInflater inflater = LayoutInflater.from(context);
		final View v = inflater.inflate(R.layout.custom_dialog, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setView(v);
		final AlertDialog alert = builder.create();
		configureDialogWindow(alert);
		
		ImageView icon = v.findViewById(R.id.img_icon);
		TextView titleDlg = v.findViewById(R.id.tv_title);
		TextView messageDlg = v.findViewById(R.id.tv_message);
		LinearLayout sizeLayout = v.findViewById(R.id.widget_temanho);
		final EditText widgetTextId = v.findViewById(R.id.et_widget);
		final EditText sizeInput = v.findViewById(R.id.ed_input);
		Button btnCancel = v.findViewById(R.id.btn_cancel);
		Button btnSave = v.findViewById(R.id.btn_ok);
		
		setupEditText(widgetTextId);
		setupEditText(sizeInput);
		icon.setImageResource(R.drawable.abc_96);
		titleDlg.setText(title);
		messageDlg.setVisibility(View.GONE);
		sizeLayout.setVisibility(View.GONE);
		// Load current text from attributesManager
		String widgetId = WidgetUtil.getWidgetId(selectedWidget);
		String currentText = attributesManager.getAttribute(activityName, widgetId, "text", WidgetUtil.getTextViewOfWidget(selectedWidget).getText().toString());
		widgetTextId.setText(currentText);
		
		btnCancel.setOnClickListener(v1 -> alert.cancel());
		btnSave.setOnClickListener(v1 -> {
			alert.cancel();
			undoRedoManager.saveStateToUndo();
			String newText = widgetTextId.getText().toString();
			WidgetUtil.getTextViewOfWidget(selectedWidget).setText(newText);
			attributesManager.saveAttribute(activityName, widgetId, "text", newText); // Save to JSON
			selectedWidget.requestLayout();
		});
		
		alert.show();
	}
	
	public void showWidgetWidthDialog(String title, String message) {
		LayoutInflater inflater = LayoutInflater.from(context);
		final View v = inflater.inflate(R.layout.custom_dialog, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setView(v);
		final AlertDialog alert = builder.create();
		configureDialogWindow(alert);
		
		ImageView icon = v.findViewById(R.id.img_icon);
		TextView titleDlg = v.findViewById(R.id.tv_title);
		TextView messageDlg = v.findViewById(R.id.tv_message);
		final RadioButton rbMatch = v.findViewById(R.id.rb_matchparent);
		final RadioButton rbWrap = v.findViewById(R.id.rb_wrapcontent);
		final RadioButton rbText = v.findViewById(R.id.rb_directinput);
		final EditText widgetTextId = v.findViewById(R.id.et_widget);
		final EditText sizeInput = v.findViewById(R.id.ed_input);
		Button btnCancel = v.findViewById(R.id.btn_cancel);
		Button btnSave = v.findViewById(R.id.btn_ok);
		
		setupEditText(widgetTextId);
		setupEditText(sizeInput);
		icon.setImageResource(R.drawable.width_96);
		messageDlg.setVisibility(View.GONE);
		titleDlg.setText(title);
		widgetTextId.setVisibility(View.GONE);
		sizeInput.setEnabled(false);
		
		String widgetId = WidgetUtil.getWidgetId(selectedWidget);
		String currentWidth = attributesManager.getAttribute(activityName, widgetId, "width", getSizeString(selectedWidget.getLayoutParams().width));
		if (currentWidth.equals("match_parent")) {
			rbMatch.setChecked(true);
		} else if (currentWidth.equals("wrap_content")) {
			rbWrap.setChecked(true);
		} else {
			rbText.setChecked(true);
			sizeInput.setEnabled(true);
			sizeInput.setText(currentWidth.replace("dp", ""));
		}
		
		rbMatch.setOnClickListener(v1 -> sizeInput.setEnabled(false));
		rbWrap.setOnClickListener(v1 -> sizeInput.setEnabled(false));
		rbText.setOnClickListener(v1 -> sizeInput.setEnabled(true));
		
		btnCancel.setOnClickListener(v1 -> alert.cancel());
		btnSave.setOnClickListener(v1 -> {
			alert.cancel();
			undoRedoManager.saveStateToUndo();
			if (selectedWidget == null) return;
			
			View widgetView = ((Widget) selectedWidget).getWidget();
			ViewGroup.LayoutParams params = widgetView.getLayoutParams();
			String widthValue;
			
			if (rbMatch.isChecked()) {
				params.width = ViewGroup.LayoutParams.MATCH_PARENT;
				widthValue = "match_parent";
			} else if (rbWrap.isChecked()) {
				params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
				widthValue = "wrap_content";
			} else if (rbText.isChecked()) {
				String inputText = sizeInput.getText().toString().trim();
				if (inputText.isEmpty()) {
					sizeInput.setError("0 ~ 999");
					return;
				}
				try {
					int value = Integer.parseInt(inputText);
					if (value < 0 || value > 999) {
						sizeInput.setError("0 ~ 999");
						return;
					}
					params.width = value;
					widthValue = value + "dp";
				} catch (NumberFormatException e) {
					sizeInput.setError("Invalid number");
					return;
				}
			} else {
				return;
			}
			widgetView.setLayoutParams(params);
			attributesManager.saveAttribute(activityName, widgetId, "width", widthValue); // Save to JSON
			widgetView.requestLayout();
		});
		
		alert.show();
	}
	
	public void showWidgetHeightDialog(String title, String message) {
		LayoutInflater inflater = LayoutInflater.from(context);
		final View v = inflater.inflate(R.layout.custom_dialog, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setView(v);
		final AlertDialog alert = builder.create();
		configureDialogWindow(alert);
		
		ImageView icon = v.findViewById(R.id.img_icon);
		TextView titleDlg = v.findViewById(R.id.tv_title);
		TextView messageDlg = v.findViewById(R.id.tv_message);
		final RadioButton rbMatch = v.findViewById(R.id.rb_matchparent);
		final RadioButton rbWrap = v.findViewById(R.id.rb_wrapcontent);
		final RadioButton rbText = v.findViewById(R.id.rb_directinput);
		final EditText widgetTextId = v.findViewById(R.id.et_widget);
		final EditText sizeInput = v.findViewById(R.id.ed_input);
		Button btnCancel = v.findViewById(R.id.btn_cancel);
		Button btnSave = v.findViewById(R.id.btn_ok);
		
		setupEditText(widgetTextId);
		setupEditText(sizeInput);
		icon.setImageResource(R.drawable.height_96);
		messageDlg.setVisibility(View.GONE);
		titleDlg.setText(title);
		widgetTextId.setVisibility(View.GONE);
		sizeInput.setEnabled(false);
		
		String widgetId = WidgetUtil.getWidgetId(selectedWidget);
		String currentHeight = attributesManager.getAttribute(activityName, widgetId, "height", getSizeString(selectedWidget.getLayoutParams().height));
		if (currentHeight.equals("match_parent")) {
			rbMatch.setChecked(true);
		} else if (currentHeight.equals("wrap_content")) {
			rbWrap.setChecked(true);
		} else {
			rbText.setChecked(true);
			sizeInput.setEnabled(true);
			sizeInput.setText(currentHeight.replace("dp", ""));
		}
		
		rbMatch.setOnClickListener(v1 -> sizeInput.setEnabled(false));
		rbWrap.setOnClickListener(v1 -> sizeInput.setEnabled(false));
		rbText.setOnClickListener(v1 -> sizeInput.setEnabled(true));
		
		btnCancel.setOnClickListener(v1 -> alert.cancel());
		btnSave.setOnClickListener(v1 -> {
			alert.cancel();
			undoRedoManager.saveStateToUndo();
			if (selectedWidget == null) return;
			
			LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ((Widget) selectedWidget).getWidget().getLayoutParams();
			String heightValue;
			
			if (rbMatch.isChecked()) {
				layoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT;
				heightValue = "match_parent";
			} else if (rbWrap.isChecked()) {
				layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
				heightValue = "wrap_content";
			} else if (rbText.isChecked()) {
				String inputText = sizeInput.getText().toString().trim();
				if (inputText.isEmpty()) {
					sizeInput.setError("0 ~ 999");
					return;
				}
				try {
					int value = Integer.parseInt(inputText);
					if (value < 0 || value > 999) {
						sizeInput.setError("0 ~ 999");
						return;
					}
					layoutParams.height = value;
					heightValue = value + "dp";
				} catch (NumberFormatException e) {
					sizeInput.setError("Invalid number");
					return;
				}
			} else {
				return;
			}
			attributesManager.saveAttribute(activityName, widgetId, "height", heightValue); // Save to JSON
			selectedWidget.requestLayout();
		});
		
		alert.show();
	}
	
	public void showWidgetIdDialog(String title, String message) {
		LayoutInflater inflater = LayoutInflater.from(context);
		final View v = inflater.inflate(R.layout.custom_dialog, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setView(v);
		final AlertDialog alert = builder.create();
		configureDialogWindow(alert);
		
		ImageView icon = v.findViewById(R.id.img_icon);
		TextView titleDlg = v.findViewById(R.id.tv_title);
		TextView messageDlg = v.findViewById(R.id.tv_message);
		LinearLayout sizeLayout = v.findViewById(R.id.widget_temanho);
		final EditText widgetTextId = v.findViewById(R.id.et_widget);
		final EditText sizeInput = v.findViewById(R.id.ed_input);
		Button btnCancel = v.findViewById(R.id.btn_cancel);
		Button btnSave = v.findViewById(R.id.btn_ok);
		
		setupEditText(widgetTextId);
		setupEditText(sizeInput);
		icon.setImageResource(R.drawable.abc_96);
		titleDlg.setText(title);
		messageDlg.setVisibility(View.GONE);
		sizeLayout.setVisibility(View.GONE);
		String widgetId = WidgetUtil.getWidgetId(selectedWidget);
		widgetTextId.setText(attributesManager.getAttribute(activityName, widgetId, "id", widgetId));
		
		btnCancel.setOnClickListener(v1 -> alert.cancel());
		btnSave.setOnClickListener(v1 -> {
			alert.cancel();
			undoRedoManager.saveStateToUndo();
			String newId = widgetTextId.getText().toString();
			WidgetUtil.setWidgetId(selectedWidget, newId);
			attributesManager.saveAttribute(activityName, newId, "id", newId); // Save to JSON
			selectedWidget.requestLayout();
		});
		
		alert.show();
	}
	
	public void showWidgetSrcDialog(String title, String message) {
		if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions((AppCompatActivity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
			return;
		}
		
		LayoutInflater inflater = LayoutInflater.from(context);
		final View dialogView = inflater.inflate(R.layout.dialog_file_selector, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setView(dialogView);
		final AlertDialog alert = builder.create();
		
		TextView tvTitle = dialogView.findViewById(R.id.dialog_title);
		ListView listView = dialogView.findViewById(R.id.file_list_view);
		Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
		Button btnSelect = dialogView.findViewById(R.id.btn_select);
		
		tvTitle.setText(title);
		ArrayList<FileItem> fileItems = getStorageFiles();
		FileListAdapter adapter = new FileListAdapter(context, fileItems);
		listView.setAdapter(adapter);
		
		String widgetId = WidgetUtil.getWidgetId(selectedWidget);
		String currentPath = attributesManager.getAttribute(activityName, widgetId, "src", WidgetUtil.getImagePath(selectedWidget));
		if (currentPath != null && !currentPath.isEmpty()) {
			for (int i = 0; i < fileItems.size(); i++) {
				if (fileItems.get(i).getPath().equals(currentPath)) {
					listView.setItemChecked(i, true);
					break;
				}
			}
		}
		
		alert.getWindow().setGravity(Gravity.CENTER);
		alert.show();
		
		btnCancel.setOnClickListener(v -> alert.cancel());
		btnSelect.setOnClickListener(v -> {
			SparseBooleanArray checked = listView.getCheckedItemPositions();
			for (int i = 0; i < checked.size(); i++) {
				if (checked.valueAt(i)) {
					undoRedoManager.saveStateToUndo();
					FileItem selectedItem = fileItems.get(checked.keyAt(i));
					WidgetUtil.setImagePath(selectedWidget, selectedItem.getPath());
					attributesManager.saveAttribute(activityName, widgetId, "src", selectedItem.getPath()); // Save to JSON
					break;
				}
			}
			alert.cancel();
			selectedWidget.requestLayout();
		});
	}
	
	public void showWidgetTranslationXDialog(String title, String message) {
		LayoutInflater inflater = LayoutInflater.from(context);
		final View v = inflater.inflate(R.layout.custom_dialog, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setView(v);
		final AlertDialog alert = builder.create();
		configureDialogWindow(alert);
		
		ImageView icon = v.findViewById(R.id.img_icon);
		TextView titleDlg = v.findViewById(R.id.tv_title);
		TextView messageDlg = v.findViewById(R.id.tv_message);
		LinearLayout sizeLayout = v.findViewById(R.id.widget_temanho);
		final EditText widgetTranslationX = v.findViewById(R.id.et_widget);
		final EditText sizeInput = v.findViewById(R.id.ed_input);
		Button btnCancel = v.findViewById(R.id.btn_cancel);
		Button btnSave = v.findViewById(R.id.btn_ok);
		
		setupEditText(widgetTranslationX);
		setupEditText(sizeInput);
		widgetTranslationX.setHint("Enter X translation");
		icon.setImageResource(R.drawable.enlarge_48);
		titleDlg.setText(title);
		messageDlg.setVisibility(View.GONE);
		sizeLayout.setVisibility(View.GONE);
		String widgetId = WidgetUtil.getWidgetId(selectedWidget);
		String currentTranslationX = attributesManager.getAttribute(activityName, widgetId, "translationX", String.valueOf(selectedWidget.getTranslationX()));
		widgetTranslationX.setText(currentTranslationX);
		
		btnCancel.setOnClickListener(v1 -> alert.cancel());
		btnSave.setOnClickListener(v1 -> {
			try {
				undoRedoManager.saveStateToUndo();
				float newTranslationX = Float.parseFloat(widgetTranslationX.getText().toString());
				float currentTranslationY = selectedWidget.getTranslationY();
				selectedWidget.setTranslationX(newTranslationX);
				selectedWidget.setTranslationY(currentTranslationY);
				attributesManager.saveAttribute(activityName, widgetId, "translationX", String.valueOf(newTranslationX)); // Save to JSON
				selectedWidget.requestLayout();
			} catch (NumberFormatException e) {
				Toast.makeText(context, "Invalid translation value", Toast.LENGTH_SHORT).show();
			}
			alert.cancel();
		});
		
		alert.show();
	}
	
	public void showWidgetTranslationYDialog(String title, String message) {
		LayoutInflater inflater = LayoutInflater.from(context);
		final View v = inflater.inflate(R.layout.custom_dialog, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setView(v);
		final AlertDialog alert = builder.create();
		configureDialogWindow(alert);
		
		ImageView icon = v.findViewById(R.id.img_icon);
		TextView titleDlg = v.findViewById(R.id.tv_title);
		TextView messageDlg = v.findViewById(R.id.tv_message);
		LinearLayout sizeLayout = v.findViewById(R.id.widget_temanho);
		final EditText widgetTranslationY = v.findViewById(R.id.et_widget);
		final EditText sizeInput = v.findViewById(R.id.ed_input);
		Button btnCancel = v.findViewById(R.id.btn_cancel);
		Button btnSave = v.findViewById(R.id.btn_ok);
		
		setupEditText(widgetTranslationY);
		setupEditText(sizeInput);
		widgetTranslationY.setHint("Enter Y translation");
		icon.setImageResource(R.drawable.enlarge_48);
		titleDlg.setText(title);
		messageDlg.setVisibility(View.GONE);
		sizeLayout.setVisibility(View.GONE);
		String widgetId = WidgetUtil.getWidgetId(selectedWidget);
		String currentTranslationY = attributesManager.getAttribute(activityName, widgetId, "translationY", String.valueOf(selectedWidget.getTranslationY()));
		widgetTranslationY.setText(currentTranslationY);
		
		btnCancel.setOnClickListener(v1 -> alert.cancel());
		btnSave.setOnClickListener(v1 -> {
			try {
				undoRedoManager.saveStateToUndo();
				float newTranslationY = Float.parseFloat(widgetTranslationY.getText().toString());
				float currentTranslationX = selectedWidget.getTranslationX();
				selectedWidget.setTranslationY(newTranslationY);
				selectedWidget.setTranslationX(currentTranslationX);
				attributesManager.saveAttribute(activityName, widgetId, "translationY", String.valueOf(newTranslationY)); // Save to JSON
				selectedWidget.requestLayout();
			} catch (NumberFormatException e) {
				Toast.makeText(context, "Invalid translation value", Toast.LENGTH_SHORT).show();
			}
			alert.cancel();
		});
		
		alert.show();
	}
	
	public void showTextColorDialog() {
		if (selectedWidget == null) {
			Toast.makeText(context, "Error: TextView is null", Toast.LENGTH_SHORT).show();
			return;
		}
		
		LayoutInflater inflater = LayoutInflater.from(context);
		final View dialogView = inflater.inflate(R.layout.base, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setView(dialogView);
		
		LinearColorPicker colorPicker = dialogView.findViewById(R.id.colorPicker);
		if (colorPicker == null) {
			Toast.makeText(context, "Error: Color Picker not found", Toast.LENGTH_SHORT).show();
			return;
		}
		
		String widgetId = WidgetUtil.getWidgetId(selectedWidget);
		String currentColor = attributesManager.getAttribute(activityName, widgetId, "textColor", String.format("#%06X", (0xFFFFFF & WidgetUtil.getTextViewOfWidget(selectedWidget).getCurrentTextColor())));
		final int[] selectedColor = {Color.parseColor(currentColor)};
		AlertDialog alertDialog = builder.create();
		
		colorPicker.setOnColorSelectedListener(color -> {
			selectedColor[0] = color;
			undoRedoManager.saveStateToUndo();
			WidgetUtil.getTextViewOfWidget(selectedWidget).setTextColor(selectedColor[0]);
			attributesManager.saveAttribute(activityName, widgetId, "textColor", String.format("#%06X", (0xFFFFFF & selectedColor[0]))); // Save to JSON
			alertDialog.dismiss();
		});
		
		builder.setPositiveButton("OK", (dialog, which) -> {
			WidgetUtil.getTextViewOfWidget(selectedWidget).setTextColor(selectedColor[0]);
			attributesManager.saveAttribute(activityName, widgetId, "textColor", String.format("#%06X", (0xFFFFFF & selectedColor[0]))); // Save to JSON
		});
		builder.setNegativeButton("Cancel", null);
		
		alertDialog.show();
	}
	
	public void showTextSizeDialog(WidgetTextView textView) {
		LayoutInflater inflater = LayoutInflater.from(context);
		final View dialogView = inflater.inflate(R.layout.custom_textsize_dialog, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setView(dialogView);
		final AlertDialog alert = builder.create();
		alert.getWindow().setGravity(Gravity.CENTER);
		
		TextView title = dialogView.findViewById(R.id.dialog_title);
		ListView sizeList = dialogView.findViewById(R.id.size_list);
		Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
		Button btnSelect = dialogView.findViewById(R.id.btn_select);
		
		title.setText("Text Size");
		ArrayList<String> sizes = new ArrayList<>();
		for (int i = 10; i <= 100; i += 2) {
			sizes.add(i + "sp");
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_single_choice, sizes);
		sizeList.setAdapter(adapter);
		sizeList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		String widgetId = WidgetUtil.getWidgetId(textView);
		String currentSize = attributesManager.getAttribute(activityName, widgetId, "textSize", String.valueOf(textView.getTextSize() / context.getResources().getDisplayMetrics().scaledDensity));
		float currentSizeFloat = Float.parseFloat(currentSize.replace("sp", ""));
		int closestIndex = 0;
		float smallestDiff = Float.MAX_VALUE;
		
		for (int i = 0; i < sizes.size(); i++) {
			float size = Float.parseFloat(sizes.get(i).replace("sp", ""));
			float diff = Math.abs(size - currentSizeFloat);
			if (diff < smallestDiff) {
				smallestDiff = diff;
				closestIndex = i;
			}
		}
		sizeList.setItemChecked(closestIndex, true);
		
		btnCancel.setOnClickListener(v -> alert.dismiss());
		btnSelect.setOnClickListener(v -> {
			undoRedoManager.saveStateToUndo();
			int selectedPosition = sizeList.getCheckedItemPosition();
			if (selectedPosition != ListView.INVALID_POSITION) {
				String selectedSize = sizes.get(selectedPosition);
				float size = Float.parseFloat(selectedSize.replace("sp", ""));
				textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
				attributesManager.saveAttribute(activityName, widgetId, "textSize", selectedSize); // Save to JSON
			}
			alert.dismiss();
		});
		
		alert.show();
	}
	
	public void showTextStyleDialog() {
		LayoutInflater inflater = LayoutInflater.from(context);
		final View dialogView = inflater.inflate(R.layout.text_style_only_dialog, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setView(dialogView);
		final AlertDialog alert = builder.create();
		alert.getWindow().setGravity(Gravity.CENTER);
		
		RadioGroup styleGroup = dialogView.findViewById(R.id.style_radio_group);
		RadioButton normalStyle = dialogView.findViewById(R.id.normal_style);
		RadioButton boldStyle = dialogView.findViewById(R.id.bold_style);
		RadioButton italicStyle = dialogView.findViewById(R.id.italic_style);
		RadioButton boldItalicStyle = dialogView.findViewById(R.id.bold_italic_style);
		Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
		Button btnSelect = dialogView.findViewById(R.id.btn_select);
		
		String widgetId = WidgetUtil.getWidgetId(selectedWidget);
		String currentStyle = attributesManager.getAttribute(activityName, widgetId, "textStyle", "normal");
		switch (currentStyle) {
			case "bold":
			boldStyle.setChecked(true);
			break;
			case "italic":
			italicStyle.setChecked(true);
			break;
			case "bold_italic":
			boldItalicStyle.setChecked(true);
			break;
			default:
			normalStyle.setChecked(true);
			break;
		}
		
		btnCancel.setOnClickListener(v -> alert.dismiss());
		btnSelect.setOnClickListener(v -> {
			int selectedId = styleGroup.getCheckedRadioButtonId();
			int style = Typeface.NORMAL;
			String styleValue = "normal";
			if (selectedId == R.id.bold_style) {
				style = Typeface.BOLD;
				styleValue = "bold";
			} else if (selectedId == R.id.italic_style) {
				style = Typeface.ITALIC;
				styleValue = "italic";
			} else if (selectedId == R.id.bold_italic_style) {
				style = Typeface.BOLD_ITALIC;
				styleValue = "bold_italic";
			}
			undoRedoManager.saveStateToUndo();
			WidgetUtil.getTextViewOfWidget(selectedWidget).setTypeface(Typeface.defaultFromStyle(style));
			attributesManager.saveAttribute(activityName, widgetId, "textStyle", styleValue); // Save to JSON
			alert.dismiss();
		});
		
		alert.show();
	}
	
	public void showLinesDialog(String title, String message) {
		LayoutInflater inflater = LayoutInflater.from(context);
		final View v = inflater.inflate(R.layout.custom_dialog, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setView(v);
		final AlertDialog alert = builder.create();
		configureDialogWindow(alert);
		
		ImageView icon = v.findViewById(R.id.img_icon);
		TextView titleDlg = v.findViewById(R.id.tv_title);
		TextView messageDlg = v.findViewById(R.id.tv_message);
		LinearLayout sizeLayout = v.findViewById(R.id.widget_temanho);
		final EditText widgetTextId = v.findViewById(R.id.et_widget);
		final EditText sizeInput = v.findViewById(R.id.ed_input);
		Button btnCancel = v.findViewById(R.id.btn_cancel);
		Button btnSave = v.findViewById(R.id.btn_ok);
		
		setupEditText(widgetTextId);
		setupEditText(sizeInput);
		icon.setImageResource(R.drawable.numbers_48);
		titleDlg.setText(title);
		messageDlg.setVisibility(View.GONE);
		sizeLayout.setVisibility(View.GONE);
		String widgetId = WidgetUtil.getWidgetId(selectedWidget);
		String currentLines = attributesManager.getAttribute(activityName, widgetId, "lines", String.valueOf(((WidgetTextView) selectedWidget).getLines()));
		widgetTextId.setText(currentLines);
		
		btnCancel.setOnClickListener(v1 -> alert.cancel());
		btnSave.setOnClickListener(v1 -> {
			alert.cancel();
			undoRedoManager.saveStateToUndo();
			String text = widgetTextId.getText().toString();
			try {
				int lines = Integer.parseInt(text);
				((WidgetTextView) selectedWidget).setLines(lines);
				attributesManager.saveAttribute(activityName, widgetId, "lines", String.valueOf(lines)); // Save to JSON
				selectedWidget.requestLayout();
			} catch (NumberFormatException e) {
				Toast.makeText(context, "Invalid number of lines", Toast.LENGTH_SHORT).show();
			}
		});
		
		alert.show();
	}
	
	public void showBackgroundColorDialog() {
		if (selectedWidget == null) {
			Toast.makeText(context, "Error: Widget is null", Toast.LENGTH_SHORT).show();
			return;
		}
		
		LayoutInflater inflater = LayoutInflater.from(context);
		final View dialogView = inflater.inflate(R.layout.base, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setView(dialogView);
		
		LinearColorPicker colorPicker = dialogView.findViewById(R.id.colorPicker);
		if (colorPicker == null) {
			Toast.makeText(context, "Error: Color Picker not found", Toast.LENGTH_SHORT).show();
			return;
		}
		
		String widgetId = WidgetUtil.getWidgetId(selectedWidget);
		String currentColor = attributesManager.getAttribute(activityName, widgetId, "backgroundColor", "#00000000");
		final int[] selectedColor = {Color.parseColor(currentColor)};
		AlertDialog alertDialog = builder.create();
		
		colorPicker.setOnColorSelectedListener(color -> {
			selectedColor[0] = color;
			undoRedoManager.saveStateToUndo();
			selectedWidget.setBackgroundColor(selectedColor[0]);
			attributesManager.saveAttribute(activityName, widgetId, "backgroundColor", String.format("#%08X", (0xFFFFFFFF & selectedColor[0]))); // Save to JSON
			alertDialog.dismiss();
		});
		
		builder.setPositiveButton("OK", (dialog, which) -> {
			selectedWidget.setBackgroundColor(selectedColor[0]);
			attributesManager.saveAttribute(activityName, widgetId, "backgroundColor", String.format("#%08X", (0xFFFFFFFF & selectedColor[0]))); // Save to JSON
		});
		builder.setNegativeButton("Cancel", null);
		
		alertDialog.show();
	}
	
	private void configureDialogWindow(AlertDialog alert) {
		if (alert.getWindow() != null) {
			alert.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
			alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
			alert.getWindow().setGravity(Gravity.CENTER);
		}
	}
	
	private void setupEditText(EditText editText) {
		editText.setFocusable(true);
		editText.setFocusableInTouchMode(true);
		editText.setClickable(true);
	}
	
	private ArrayList<FileItem> getStorageFiles() {
		ArrayList<FileItem> fileItems = new ArrayList<>();
		File storageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
		
		FilenameFilter imageFilter = (dir, name) -> {
			String lowercaseName = name.toLowerCase();
			return lowercaseName.endsWith(".jpg") || lowercaseName.endsWith(".jpeg") ||
			lowercaseName.endsWith(".png") || lowercaseName.endsWith(".gif");
		};
		
		File[] files = storageDir.listFiles(imageFilter);
		if (files != null) {
			for (File file : files) {
				fileItems.add(new FileItem(file.getName(), file.getAbsolutePath()));
			}
		}
		return fileItems;
	}
	
	private String getSizeString(int size) {
		if (size == ViewGroup.LayoutParams.MATCH_PARENT) return "match_parent";
		if (size == ViewGroup.LayoutParams.WRAP_CONTENT) return "wrap_content";
		return size + "dp";
	}
}
