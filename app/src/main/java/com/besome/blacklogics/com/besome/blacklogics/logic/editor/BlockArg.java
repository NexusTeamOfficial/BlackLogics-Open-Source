package com.besome.blacklogics.logic.editor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;

import android.widget.Toast;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;

import com.bumptech.glide.*;

import com.besome.blacklogics.R;
import com.besome.blacklogics.DesignActivity;
import com.besome.blacklogics.ViewEditorFragmentActivity;
import com.besome.blacklogics.file.ProjectFileManager;

import com.shapun.layouteditor.ViewEditor;

import com.besome.blacklogics.development.Complex;

import com.nexusteam.internal.os.layouteditor.beans.ViewBean;
import com.nexusteam.internal.os.layouteditor.color.ColorPickerActivity;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Iterator;
import android.content.DialogInterface;

public class BlockArg extends BlockBase {
	private Object argValue = "";
	private ViewGroup content;
	private int defaultArgWidth = 20;
	private boolean isEditable = false;
	private Context mContext;
	private AlertDialog mDlg;
	private String mMenuName = "";
	private TextView mTextView;
	private int paddingText = 4;
	private ViewEditor viewEditor;
	
	private Complex complex;
	
	public BlockArg(Context var1, String var2, int var3, String var4) {
		super(var1, var2, true);
		this.mContext = var1;
		this.mMenuName = var4;
		this.init(var1);
		
		this.complex = new Complex();
		
		if (DesignActivity.getScId() != null && !DesignActivity.getScId().isEmpty()) {
			this.complex.setId(DesignActivity.getScId());
		} else {
			android.util.Log.w("BlockArg", "DesignActivity.getScId() returned null or empty, using default ID");
			this.complex.setId("600");
		}
		
		this.viewEditor = new ViewEditor(var1);
		if (DesignActivity.currentActivityBean != null) {
			this.viewEditor.loadLayout(DesignActivity.currentActivityBean.getActivityName());
		}
	}
	
	
	
	// $FF: synthetic method
	static AlertDialog access$000(BlockArg var0) {
		return var0.mDlg;
	}
	
	// $FF: synthetic method
	static ViewGroup access$100(BlockArg var0) {
		return var0.content;
	}
	
	private RadioButton createImageRadioButton(String var1) {
		RadioButton var2 = new RadioButton(this.getContext());
		var2.setText("");
		var2.setTag(var1);
		LayoutParams var3 = new LayoutParams(-2, (int)(60.0F * LayoutUtil.getDip(this.getContext(), 1.0F)));
		var2.setGravity(19);
		var2.setLayoutParams(var3);
		return var2;
	}
	
	private RadioButton createPairItem(String var1, String var2) {
		RadioButton var3 = new RadioButton(this.getContext());
		var3.setText(var1 + " : " + var2);
		var3.setTag(var2);
		LayoutParams var4 = new LayoutParams(-1, (int)(40.0F * LayoutUtil.getDip(this.getContext(), 1.0F)));
		var3.setGravity(19);
		var3.setLayoutParams(var4);
		return var3;
	}
	
	private RadioButton createRadioButton(String var1) {
		RadioButton var2 = new RadioButton(this.getContext());
		var2.setText("");
		var2.setTag(var1);
		LayoutParams var3 = new LayoutParams(-2, (int)(40.0F * LayoutUtil.getDip(this.getContext(), 1.0F)));
		var2.setGravity(19);
		var2.setLayoutParams(var3);
		return var2;
	}
	
	private LinearLayout createRadioImage(String var1, boolean var2) {
		float var3 = LayoutUtil.getDip(this.getContext(), 1.0F);
		LinearLayout var4 = new LinearLayout(this.getContext());
		var4.setLayoutParams(new LayoutParams(-1, (int)(60.0F * var3)));
		var4.setGravity(19);
		var4.setOrientation(0);
		TextView var5 = new TextView(this.getContext());
		LinearLayout.LayoutParams var6 = new LinearLayout.LayoutParams(0, -2);
		var6.weight = 1.0F;
		var5.setLayoutParams(var6);
		var5.setText(var1);
		var4.addView(var5);
		ImageView var7 = new ImageView(this.getContext());
		var7.setScaleType(ScaleType.CENTER_CROP);
		var7.setLayoutParams(new LayoutParams((int)(48.0F * var3), (int)(48.0F * var3)));
		if (var2 && var1.equals("default_image")) {
			var7.setImageResource(this.getContext().getResources().getIdentifier(var1, "drawable", this.getContext().getPackageName()));
		} else {
			String basePath = "/storage/emulated/0/.blacklogics/resources/images/" + DesignActivity.getScId() + "/" + var1;
			String[] extensions = {".png", ".jpg", ".jpeg"};
			File imageFile = null;
			for (String ext : extensions) {
				File tempFile = new File(basePath + ext);
				if (tempFile.exists()) {
					imageFile = tempFile;
					break;
				}
			}
			if (imageFile != null) {
				Uri var8 = Uri.fromFile(imageFile);
				Glide.with(this.getContext())
				.load(var8)
				.error(R.drawable.ic_remove_grey600_24dp)
				.into(var7);
			} else {
				var7.setImageResource(R.drawable.ic_remove_grey600_24dp);
			}
		}
		var7.setBackgroundColor(-4342339);
		var4.addView(var7);
		return var4;
	}
	
	private RadioButton createSingleItem(String var1) {
		RadioButton var2 = new RadioButton(this.getContext());
		var2.setText(var1);
		LayoutParams var3 = new LayoutParams(-1, (int)(40.0F * LayoutUtil.getDip(this.getContext(), 1.0F)));
		var2.setGravity(19);
		var2.setLayoutParams(var3);
		return var2;
	}
	
	private int getLabelWidth() {
		Rect var1 = new Rect();
		this.mTextView.getPaint().getTextBounds(this.mTextView.getText().toString(), 0, this.mTextView.getText().length(), var1);
		return var1.width() + this.paddingText;
	}
	
	private void init(Context var1) {
		byte var3;
		label48: {
			String var2 = this.mType;
			switch(var2.hashCode()) {
				case 98:
				if(var2.equals("b")) {
					var3 = 0;
					break label48;
				}
				break;
				case 100:
				if(var2.equals("d")) {
					var3 = 1;
					break label48;
				}
				break;
				case 109:
				if(var2.equals("m")) {
					var3 = 4;
					break label48;
				}
				break;
				case 110:
				if(var2.equals("n")) {
					var3 = 2;
					break label48;
				}
				break;
				case 115:
				if(var2.equals("s")) {
					var3 = 3;
					break label48;
				}
				break;
				case 3237658: // Hash code for "s.inputOnly"
				if(var2.equals("s.inputOnly")) {
					var3 = 5;
					break label48;
				}
				break;
			}
			
			var3 = -1;
		}
		
		switch(var3) {
			case 0:
			this.mColor = 1342177280;
			this.defaultArgWidth = 25;
			break;
			case 1:
			this.mColor = -657931;
			break;
			case 2:
			this.mColor = -3155748;
			break;
			case 3:
			this.mColor = -1;
			break;
			case 4:
			this.mColor = 805306368;
			break;
			case 5:
			this.mColor = -1; // Same color as 's'
			this.isEditable = true; // Always editable
			break;
		}
		
		this.defaultArgWidth = (int)((float)this.defaultArgWidth * this.dip);
		this.paddingText = (int)((float)this.paddingText * this.dip);
		if(this.mType.equals("m") || this.mType.equals("d") || this.mType.equals("n") || this.mType.equals("s") || this.mType.equals("s.inputOnly")) {
			this.mTextView = this.makeEditText(""); // Same method for 's' and 's.inputOnly'
			this.addView(this.mTextView);
		}
		
		this.setBackgroundColor(this.mColor); 
		
		this.setWidthAndTopHeight((float)this.defaultArgWidth, (float)this.labelAndArgHeight, false);
	}
	
	private TextView makeEditText(String var1) {
		TextView var2 = new TextView(this.mContext);
		var2.setText(var1);
		var2.setTextSize(9.0F);
		android.widget.RelativeLayout.LayoutParams var3 = new android.widget.RelativeLayout.LayoutParams(this.defaultArgWidth, this.labelAndArgHeight);
		var3.setMargins(0, 0, 0, 0);
		var2.setPadding(5, 0, 0, 0);
		var2.setLayoutParams(var3);
		var2.setBackgroundColor(0);
		var2.setSingleLine();
		var2.setGravity(17);
		if(!this.mType.equals("m")) {
			var2.setTextColor(-268435456);
			return var2;
		} else {
			var2.setTextColor(-251658241);
			return var2;
		}
	}
	
	private void showColorPopup() {
		// Parse initial color from argValue
		int initialColor = 0;
		Object var2 = this.argValue;
		if (var2 != null && var2.toString().length() > 0) {
			String argStr = var2.toString();
			if (argStr.startsWith("0xFF")) {
				try {
					initialColor = Color.parseColor(argStr.replace("0xFF", "#"));
				} catch (IllegalArgumentException e) {
					// Invalid color format, keep initialColor as 0
				}
			}
		}
		
		// Create ColorPicker and set listener
		ColorPicker colorPicker = new ColorPicker(this.getContext(), false); // pipEnabled = false
		colorPicker.setOnColorPickedListener(new OnColorPickedListener() {
			@Override
			public void onColorPicked(String str) {
				try {
					// Ensure color is in 0xFF format
					String colorStr = str;
					if (str.startsWith("#")) {
						colorStr = "0xFF" + str.substring(1);
					}
					setArgValue(colorStr);
					parentBlock.recalcWidthToParent();
					parentBlock.topBlock().fixLayout();
					parentBlock.pane.calculateWidthHeight();
				} catch (Exception e) {
					// Handle invalid color
					Toast.makeText(getContext(), "Invalid color selected", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		// Launch color picker
		colorPicker.pick();
	}
	
	private void showImagePopup() {
		View var1 = LayoutUtil.inflate(this.getContext(), R.layout.property_popup_selector_color);
		Builder var2 = new Builder(this.getContext());
		var2.setView(var1);
		var2.setTitle(this.getResources().getString(R.string.title_popup_select_image));
		RadioGroup var5 = (RadioGroup) var1.findViewById(R.id.rg);
		this.content = (LinearLayout) var1.findViewById(R.id.content);
		ArrayList<String> var6 = getImageNamesFromPath();
		if (ScDefine.isCustomEditMode(DesignActivity.getScId())) {
			var6.add(0, "default_image");
		}
		
		Iterator<String> var7 = var6.iterator();
		
		while (var7.hasNext()) {
			String var10 = var7.next();
			RadioButton var11 = this.createImageRadioButton(var10);
			var5.addView(var11);
			if (var10.equals(this.argValue)) {
				var11.setChecked(true);
			}
			
			LinearLayout var12;
			if (ScDefine.isCustomEditMode(DesignActivity.getScId())) {
				if (var10.equals("default_image")) {
					var12 = this.createRadioImage(var10, true);
				} else {
					var12 = this.createRadioImage(var10, false);
				}
			} else {
				var12 = this.createRadioImage(var10, false);
			}
			
			var12.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					for (int i = 0; i < var5.getChildCount(); i++) {
						RadioButton rb = (RadioButton) var5.getChildAt(i);
						if (rb.getTag().equals(var10)) {
							rb.setChecked(true);
							break;
						}
					}
				}
			});
			this.content.addView(var12);
		}
		
		var2.setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mDlg.dismiss();
			}
		});
		
		var2.setPositiveButton(R.string.btn_accept, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				int checkedId = var5.getCheckedRadioButtonId();
				if (checkedId != -1) {
					RadioButton selected = var5.findViewById(checkedId);
					setArgValue(selected.getTag().toString());
					parentBlock.recalcWidthToParent();
					parentBlock.topBlock().fixLayout();
					parentBlock.pane.calculateWidthHeight();
				}
				mDlg.dismiss();
			}
		});
		
		this.mDlg = var2.create();
		this.mDlg.show();
	}
	
	public Object getArgValue() {
		if (this.mType.equals("s.inputOnly")) {
			return this.mTextView.getText().toString();
		}
		return !this.mType.equals("d") && !this.mType.equals("m") && !this.mType.equals("s") ? this.argValue : this.mTextView.getText();
	}
	
	public void setArgValue(Object var1) {
		this.argValue = var1;
		if (this.mType.equals("d") || this.mType.equals("m") || this.mType.equals("s") || this.mType.equals("s.inputOnly")) {
			this.mTextView.setText(var1.toString());
			int var2 = Math.max(this.defaultArgWidth, this.getLabelWidth());
			this.mTextView.getLayoutParams().width = var2;
			this.setWidthAndTopHeight((float)var2, (float)this.labelAndArgHeight, true);
		}
	}
	
	public void setEditable(boolean var1) {
		this.isEditable = var1;
	}
	
	public void showEditPopup(final boolean var1) {
		View var2 = LayoutUtil.inflate(this.getContext(), R.layout.property_popup_input_text);
		Builder var3 = new Builder(this.getContext());
		var3.setView(var2);
		if(var1) {
			var3.setTitle(this.getResources().getString(R.string.title_popup_input_int_value));
		} else {
			var3.setTitle(this.getResources().getString(R.string.title_popup_input_str_value));
		}
		
		final EditText var6 = (EditText)var2.findViewById(R.id.ed_input);
		if(var1) {
			var6.setInputType(4098);
			var6.setImeOptions(6);
			var6.setMaxLines(1);
		} else {
			var6.setInputType(131073);
			var6.setImeOptions(1);
		}
		/*
		String currentValue = this.mTextView.getText() != null ? this.mTextView.getText().toString() : "";
		if (this.mType.equals("s.inputOnly")) {
		this.mTextView.setText(""); // Force empty for s.inputOnly
		}
		*/
		var6.setText(this.mTextView.getText());
		var3.setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface var1, int var2) {
				mDlg.dismiss();
			}
		});
		var3.setPositiveButton(R.string.btn_accept, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface var11, int var2) {
				
				String var3 = var6.getText().toString();
				String var4;
				if(var1) {
					var4 = Integer.valueOf(var3).toString();
				} else if(var3.length() > 0 && var3.charAt(0) == 64) {
					var4 = " " + var3;
				} else {
					var4 = var3;
				}
				
				setArgValue(var4);
				parentBlock.recalcWidthToParent();
				parentBlock.topBlock().fixLayout();
				parentBlock.pane.calculateWidthHeight();
				
				mDlg.dismiss();
			}
		});
		this.mDlg = var3.create();
		this.mDlg.show();
	}
	
	public void showIntentDataPopup() {
		View var1 = LayoutUtil.inflate(this.getContext(), R.layout.property_popup_input_intent_data);
		Builder var2 = new Builder(this.getContext());
		var2.setView(var1);
		var2.setTitle(this.getResources().getString(R.string.title_popup_input_data_value));
		EditText var5 = (EditText)var1.findViewById(R.id.ed_input);
		var5.setInputType(1);
		var5.setText(this.mTextView.getText());
		
		var2.setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				mDlg.dismiss();
			}
		});
		
		var2.setPositiveButton(R.string.btn_accept, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String input = var5.getText().toString();
				setArgValue(input);
				parentBlock.recalcWidthToParent();
				parentBlock.topBlock().fixLayout();
				parentBlock.pane.calculateWidthHeight();
				mDlg.dismiss();
			}
		});
		
		this.mDlg = var2.create();
		this.mDlg.show();
	}
	
	public void showPopup() {
		if (this.mType.equals("d")) {
			this.showEditPopup(true);
		} else if (this.mType.equals("s") || this.mType.equals("s.inputOnly")) {
			if (this.mMenuName.equals("intentData")) {
				this.showIntentDataPopup();
				return;
			}
			this.showEditPopup(false); // Same popup for both 's' and 's.inputOnly'
		} else if (this.mType.equals("m")) {
			if (this.mMenuName.equals("resource")) {
				this.showImagePopup();
				return;
			}
			if (this.mMenuName.equals("color")) {
				this.showColorPopup();
				return;
			}
			if (!this.mMenuName.equals("view") && !this.mMenuName.equals("textview") && 
			!this.mMenuName.equals("imageview") && !this.mMenuName.equals("listview") && 
			!this.mMenuName.equals("spinner") && !this.mMenuName.equals("listSpn") && 
			!this.mMenuName.equals("checkbox")) {
				this.showSelectPopup();
				return;
			}
			this.showSelectPairPopup();
		}
	}
	
	
	public void showSelectPairPopup() {
		View var1 = LayoutInflater.from(this.getContext()).inflate(R.layout.property_popup_selector_single, null);
		AlertDialog.Builder var2 = new AlertDialog.Builder(this.getContext());
		var2.setView(var1);
		this.content = var1.findViewById(R.id.rg_content);
		
		var2.setTitle("Select View");
		
		List<String> idList = new ArrayList<>();
		switch (mMenuName) {
			case "view":
			idList = ViewEditor.getIdsByType("all");
			break;
			case "textview":
			idList = ViewEditor.getIdsByType("TextView");
			break;
			case "imageview":
			idList = ViewEditor.getIdsByType("ImageView");
			break;
			case "checkbox":
			idList = ViewEditor.getIdsByType("CheckBox");
			break;
			case "listview":
			idList = ViewEditor.getIdsByType("ListView");
			break;
			case "spinner":
			idList = ViewEditor.getIdsByType("Spinner");
			break;
			case "listSpn":
			idList.addAll(ViewEditor.getIdsByType("ListView"));
			idList.addAll(ViewEditor.getIdsByType("Spinner"));
			break;
		}
		
		for (String id : idList) {
			RadioButton radioButton = createPairItem("id", id);
			this.content.addView(radioButton);
		}
		
		for (int i = 0; i < this.content.getChildCount(); i++) {
			RadioButton radioButton = (RadioButton) this.content.getChildAt(i);
			if (argValue.equals(radioButton.getTag().toString())) {
				radioButton.setChecked(true);
				break;
			}
		}
		
		var2.setNegativeButton(R.string.btn_cancel, (dialog, which) -> dialog.dismiss());
		var2.setPositiveButton(R.string.btn_accept, (dialog, which) -> {
			for (int i = 0; i < content.getChildCount(); i++) {
				RadioButton radioButton = (RadioButton) content.getChildAt(i);
				if (radioButton.isChecked()) {
					String selectedWidgetId = radioButton.getTag().toString();
					setArgValue(selectedWidgetId);
					parentBlock.recalcWidthToParent();
					parentBlock.topBlock().fixLayout();
					parentBlock.pane.calculateWidthHeight();
					break;
				}
			}
		});
		
		mDlg = var2.create();
		mDlg.show();
	}
	
	
	public void showSelectPopup() {
		View var1 = LayoutUtil.inflate(this.getContext(), R.layout.property_popup_selector_single);
		Builder var2 = new Builder(this.getContext());
		var2.setView(var1);
		this.content = (ViewGroup)var1.findViewById(R.id.rg_content);
		ArrayList var4 = new ArrayList();
		if(this.mMenuName.equals("varInt")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_int_var));
			var4 = DesignDataManager.getVariablesByType(LogicEditorActivity.filename, 1);
		} else if(this.mMenuName.equals("varBool")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_bool_var));
			var4 = DesignDataManager.getVariablesByType(LogicEditorActivity.filename, 0);
		} else if(this.mMenuName.equals("varStr")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_str_var));
			var4 = DesignDataManager.getVariablesByType(LogicEditorActivity.filename, 2);
		} else if(this.mMenuName.equals("listInt")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_int_list));
			var4 = DesignDataManager.getListsByType(LogicEditorActivity.filename, 1);
		} else if(this.mMenuName.equals("listStr")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_str_list));
			var4 = DesignDataManager.getListsByType(LogicEditorActivity.filename, 2);
		} else if(this.mMenuName.equals("list")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_list));
			var4 = DesignDataManager.getAllLists(LogicEditorActivity.filename);
		} else if(this.mMenuName.equals("visible")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_visibility));
			var4 = new ArrayList(Arrays.asList(DefineSource.VISIBILITY_FIELD));
		} else if (this.mMenuName.equals("activity")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_activity));
			// Use Complex.getActivityNames() instead of ProjectFileManager.javaFileList
			List<String> activityNames = complex.getActivityNames();
			for (String activityName : activityNames) {
				var4.add(activityName); // activityName is already without .java extension
			}
		} else if(this.mMenuName.equals("intentAction")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_action));
			var4 = new ArrayList(Arrays.asList(DefineSource.getIntentAction()));
		} else if(this.mMenuName.equals("calendarField")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_calendar_field));
			var4 = new ArrayList(Arrays.asList(DefineSource.CALENDAR_FIELD));
		} else if (this.mMenuName.equals("intent")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_component));
			var4 = new ArrayList<>();
			List<HashMap<String, String>> intentComponents = DesignActivity.loadIntentComponents(DesignActivity.currentActivityBean.getActivityName());
			if (intentComponents != null) {
				for (HashMap<String, String> component : intentComponents) {
					String componentId = component.get("fieldName"); // Or use another field like "name"
					if (componentId != null) {
						var4.add(componentId);
					}
				}
			}
			if (var4.isEmpty()) {
				// Toast.makeText(this.getContext(), "No intent components found", Toast.LENGTH_SHORT).show();
			}
		} else if (this.mMenuName.equals("intent")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_component));
			var4 = new ArrayList<>();
			List<HashMap<String, String>> intentComponents = DesignActivity.loadIntentComponents(DesignActivity.currentActivityBean.getActivityName());
			if (intentComponents != null) {
				for (HashMap<String, String> component : intentComponents) {
					String componentId = component.get("fieldName"); // Or use another field like "name"
					if (componentId != null) {
						var4.add(componentId);
					}
				}
			}
			if (var4.isEmpty()) {
				// Toast.makeText(this.getContext(), "No intent components found", Toast.LENGTH_SHORT).show();
			}
		} else if (this.mMenuName.equals("timer")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_component));
			var4 = new ArrayList<>();
			List<HashMap<String, String>> timerComponent = DesignActivity.loadComponentFromName(DesignActivity.currentActivityBean.getActivityName(), "Timer");
			if (timerComponent != null) {
				for (HashMap<String, String> timerComponemtQ : timerComponent) {
					String cI = timerComponemtQ.get("fieldName"); // Or use another field like "name"
					if (cI != null) {
						var4.add(cI);
					}
				}
			}
			if (var4.isEmpty()) {
				//Toast.makeText(this.getContext(), "No intent components found", Toast.LENGTH_SHORT).show();
			}
		} else if (this.mMenuName.equals("dialog")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_component));
			var4 = new ArrayList<>();
			List<HashMap<String, String>> dialogComponent = DesignActivity.loadComponentFromName(DesignActivity.currentActivityBean.getActivityName(), "Dialog");
			if (dialogComponent != null) {
				for (HashMap<String, String> dcomponent : dialogComponent) {
					String dcomponentId = dcomponent.get("fieldName"); // Or use another field like "name"
					if (dcomponentId != null) {
						var4.add(dcomponentId);
					}
				}
			}
			if (var4.isEmpty()) {
				// Toast.makeText(this.getContext(), "No intent components found", Toast.LENGTH_SHORT).show();
			}
		} else if (this.mMenuName.equals("objectanimator")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_component));
			var4 = new ArrayList<>();
			List<HashMap<String, String>> objectAnimatorComponent = DesignActivity.loadComponentFromName(DesignActivity.currentActivityBean.getActivityName(), "ObjectAnimator");
			if (objectAnimatorComponent != null) {
				for (HashMap<String, String> obComponent : objectAnimatorComponent) {
					String oCI = obComponent.get("fieldName"); // Or use another field like "name"
					if (oCI != null) {
						var4.add(oCI);
					}
				}
			}
			if (var4.isEmpty()) {
				// Toast.makeText(this.getContext(), "No intent components found", Toast.LENGTH_SHORT).show();
			}
		} else if (this.mMenuName.equals("sharedpreferences")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_component));
			var4 = new ArrayList<>();
			List<HashMap<String, String>> sharedPreferencesComponent = DesignActivity.loadComponentFromName(DesignActivity.currentActivityBean.getActivityName(), "SharedPreferences");
			if (sharedPreferencesComponent != null) {
				for (HashMap<String, String> sharedPreferencescomponent : sharedPreferencesComponent) {
					String sComponentId = sharedPreferencescomponent.get("fieldName"); // Or use another field like "name"
					if (sComponentId != null) {
						var4.add(sComponentId);
					}
				}
			}
			if (var4.isEmpty()) {
				// Toast.makeText(this.getContext(), "No intent components found", Toast.LENGTH_SHORT).show();
			}
		} else if (this.mMenuName.equals("datepickerdialog")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_component));
			var4 = new ArrayList<>();
			List<HashMap<String, String>> datePickerComponent = DesignActivity.loadComponentFromName(DesignActivity.currentActivityBean.getActivityName(), "DatePickerDialog");
			if (datePickerComponent != null) {
				for (HashMap<String, String> component : datePickerComponent) {
					String fieldName = component.get("fieldName");
					if (fieldName != null) {
						var4.add(fieldName);
					}
				}
			}
		} else if (this.mMenuName.equals("timepickerdialog")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_component));
			var4 = new ArrayList<>();
			List<HashMap<String, String>> timePickerComponent = DesignActivity.loadComponentFromName(DesignActivity.currentActivityBean.getActivityName(), "TimePickerDialog");
			if (timePickerComponent != null) {
				for (HashMap<String, String> component : timePickerComponent) {
					String fieldName = component.get("fieldName");
					if (fieldName != null) {
						var4.add(fieldName);
					}
				}
			}
		} else if (this.mMenuName.equals("progressdialog")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_component));
			var4 = new ArrayList<>();
			List<HashMap<String, String>> progressDialogComponent = DesignActivity.loadComponentFromName(DesignActivity.currentActivityBean.getActivityName(), "ProgressDialog");
			if (progressDialogComponent != null) {
				for (HashMap<String, String> component : progressDialogComponent) {
					String fieldName = component.get("fieldName");
					if (fieldName != null) {
						var4.add(fieldName);
					}
				}
			}
		} else if (this.mMenuName.equals("alertdialog")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_component));
			var4 = new ArrayList<>();
			List<HashMap<String, String>> alertDialogComponent = DesignActivity.loadComponentFromName(DesignActivity.currentActivityBean.getActivityName(), "AlertDialog");
			if (alertDialogComponent != null) {
				for (HashMap<String, String> component : alertDialogComponent) {
					String fieldName = component.get("fieldName");
					if (fieldName != null) {
						var4.add(fieldName);
					}
				}
			}
		} else if (this.mMenuName.equals("vibrator")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_component));
			var4 = new ArrayList<>();
			List<HashMap<String, String>> vibratorComponent = DesignActivity.loadComponentFromName(DesignActivity.currentActivityBean.getActivityName(), "Vibrator");
			if (vibratorComponent != null) {
				for (HashMap<String, String> component : vibratorComponent) {
					String fieldName = component.get("fieldName");
					if (fieldName != null) {
						var4.add(fieldName);
					}
				}
			}
		} else if (this.mMenuName.equals("notification")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_component));
			var4 = new ArrayList<>();
			List<HashMap<String, String>> notificationComponent = DesignActivity.loadComponentFromName(DesignActivity.currentActivityBean.getActivityName(), "Notification");
			if (notificationComponent != null) {
				for (HashMap<String, String> component : notificationComponent) {
					String fieldName = component.get("fieldName");
					if (fieldName != null) {
						var4.add(fieldName);
					}
				}
			}
		} else if (this.mMenuName.equals("locationmanager")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_component));
			var4 = new ArrayList<>();
			List<HashMap<String, String>> locationManagerComponent = DesignActivity.loadComponentFromName(DesignActivity.currentActivityBean.getActivityName(), "LocationManager");
			if (locationManagerComponent != null) {
				for (HashMap<String, String> component : locationManagerComponent) {
					String fieldName = component.get("fieldName");
					if (fieldName != null) {
						var4.add(fieldName);
					}
				}
			}
		} else if (this.mMenuName.equals("mediaplayer")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_component));
			var4 = new ArrayList<>();
			List<HashMap<String, String>> mediaPlayerComponent = DesignActivity.loadComponentFromName(DesignActivity.currentActivityBean.getActivityName(), "MediaPlayer");
			if (mediaPlayerComponent != null) {
				for (HashMap<String, String> component : mediaPlayerComponent) {
					String fieldName = component.get("fieldName");
					if (fieldName != null) {
						var4.add(fieldName);
					}
				}
			}
		} else if (this.mMenuName.equals("sensormanager")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_component));
			var4 = new ArrayList<>();
			List<HashMap<String, String>> sensorManagerComponent = DesignActivity.loadComponentFromName(DesignActivity.currentActivityBean.getActivityName(), "SensorManager");
			if (sensorManagerComponent != null) {
				for (HashMap<String, String> component : sensorManagerComponent) {
					String fieldName = component.get("fieldName");
					if (fieldName != null) {
						var4.add(fieldName);
					}
				}
			}
		} else if (this.mMenuName.equals("toast")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_component));
			var4 = new ArrayList<>();
			List<HashMap<String, String>> toastComponent = DesignActivity.loadComponentFromName(DesignActivity.currentActivityBean.getActivityName(), "Toast");
			if (toastComponent != null) {
				for (HashMap<String, String> component : toastComponent) {
					String fieldName = component.get("fieldName");
					if (fieldName != null) {
						var4.add(fieldName);
					}
				}
			}
		} else if (this.mMenuName.equals("sharedpreferences")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_component));
			var4 = new ArrayList<>();
			List<HashMap<String, String>> sharedPreferencesComponent = DesignActivity.loadComponentFromName(DesignActivity.currentActivityBean.getActivityName(), "SharedPreferences");
			if (sharedPreferencesComponent != null) {
				for (HashMap<String, String> component : sharedPreferencesComponent) {
					String fieldName = component.get("fieldName");
					if (fieldName != null) {
						var4.add(fieldName);
					}
				}
			}
		} else if (this.mMenuName.equals("alarmmanager")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_component));
			var4 = new ArrayList<>();
			List<HashMap<String, String>> alarmManagerComponent = DesignActivity.loadComponentFromName(DesignActivity.currentActivityBean.getActivityName(), "AlarmManager");
			if (alarmManagerComponent != null) {
				for (HashMap<String, String> component : alarmManagerComponent) {
					String fieldName = component.get("fieldName");
					if (fieldName != null) {
						var4.add(fieldName);
					}
				}
			}
		} else if (this.mMenuName.equals("bluetoothadapter")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_component));
			var4 = new ArrayList<>();
			List<HashMap<String, String>> bluetoothAdapterComponent = DesignActivity.loadComponentFromName(DesignActivity.currentActivityBean.getActivityName(), "BluetoothAdapter");
			if (bluetoothAdapterComponent != null) {
				for (HashMap<String, String> component : bluetoothAdapterComponent) {
					String fieldName = component.get("fieldName");
					if (fieldName != null) {
						var4.add(fieldName);
					}
				}
			}
		} else if (this.mMenuName.equals("wifimanager")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_component));
			var4 = new ArrayList<>();
			List<HashMap<String, String>> wifiManagerComponent = DesignActivity.loadComponentFromName(DesignActivity.currentActivityBean.getActivityName(), "WifiManager");
			if (wifiManagerComponent != null) {
				for (HashMap<String, String> component : wifiManagerComponent) {
					String fieldName = component.get("fieldName");
					if (fieldName != null) {
						var4.add(fieldName);
					}
				}
			}
		} else if (this.mMenuName.equals("camera")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_component));
			var4 = new ArrayList<>();
			List<HashMap<String, String>> cameraComponent = DesignActivity.loadComponentFromName(DesignActivity.currentActivityBean.getActivityName(), "Camera");
			if (cameraComponent != null) {
				for (HashMap<String, String> component : cameraComponent) {
					String fieldName = component.get("fieldName");
					if (fieldName != null) {
						var4.add(fieldName);
					}
				}
			}
		} else if (this.mMenuName.equals("clipboardmanager")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_component));
			var4 = new ArrayList<>();
			List<HashMap<String, String>> clipboardManagerComponent = DesignActivity.loadComponentFromName(DesignActivity.currentActivityBean.getActivityName(), "ClipboardManager");
			if (clipboardManagerComponent != null) {
				for (HashMap<String, String> component : clipboardManagerComponent) {
					String fieldName = component.get("fieldName");
					if (fieldName != null) {
						var4.add(fieldName);
					}
				}
			}
		} else if (this.mMenuName.equals("powermanager")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_component));
			var4 = new ArrayList<>();
			List<HashMap<String, String>> powerManagerComponent = DesignActivity.loadComponentFromName(DesignActivity.currentActivityBean.getActivityName(), "PowerManager");
			if (powerManagerComponent != null) {
				for (HashMap<String, String> component : powerManagerComponent) {
					String fieldName = component.get("fieldName");
					if (fieldName != null) {
						var4.add(fieldName);
					}
				}
			}
		} else if (this.mMenuName.equals("connectivitymanager")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_component));
			var4 = new ArrayList<>();
			List<HashMap<String, String>> connectivityManagerComponent = DesignActivity.loadComponentFromName(DesignActivity.currentActivityBean.getActivityName(), "ConnectivityManager");
			if (connectivityManagerComponent != null) {
				for (HashMap<String, String> component : connectivityManagerComponent) {
					String fieldName = component.get("fieldName");
					if (fieldName != null) {
						var4.add(fieldName);
					}
				}
			}
		} else if (this.mMenuName.equals("audiomanager")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_component));
			var4 = new ArrayList<>();
			List<HashMap<String, String>> audioManagerComponent = DesignActivity.loadComponentFromName(DesignActivity.currentActivityBean.getActivityName(), "AudioManager");
			if (audioManagerComponent != null) {
				for (HashMap<String, String> component : audioManagerComponent) {
					String fieldName = component.get("fieldName");
					if (fieldName != null) {
						var4.add(fieldName);
					}
				}
			}
		} else if (this.mMenuName.equals("sensor")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_component));
			var4 = new ArrayList<>();
			List<HashMap<String, String>> sensorComponent = DesignActivity.loadComponentFromName(DesignActivity.currentActivityBean.getActivityName(), "Sensor");
			if (sensorComponent != null) {
				for (HashMap<String, String> component : sensorComponent) {
					String fieldName = component.get("fieldName");
					if (fieldName != null) {
						var4.add(fieldName);
					}
				}
			}
		} else if (this.mMenuName.equals("mediarouter")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_component));
			var4 = new ArrayList<>();
			List<HashMap<String, String>> mediaRouterComponent = DesignActivity.loadComponentFromName(DesignActivity.currentActivityBean.getActivityName(), "MediaRouter");
			if (mediaRouterComponent != null) {
				for (HashMap<String, String> component : mediaRouterComponent) {
					String fieldName = component.get("fieldName");
					if (fieldName != null) var4.add(fieldName);
				}
			}
		} else if (this.mMenuName.equals("telecommanager")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_component));
			var4 = new ArrayList<>();
			List<HashMap<String, String>> telecomManagerComponent = DesignActivity.loadComponentFromName(DesignActivity.currentActivityBean.getActivityName(), "TelecomManager");
			if (telecomManagerComponent != null) {
				for (HashMap<String, String> component : telecomManagerComponent) {
					String fieldName = component.get("fieldName");
					if (fieldName != null) var4.add(fieldName);
				}
			}
		} else if (this.mMenuName.equals("shortcutmanager")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_component));
			var4 = new ArrayList<>();
			List<HashMap<String, String>> shortcutManagerComponent = DesignActivity.loadComponentFromName(DesignActivity.currentActivityBean.getActivityName(), "ShortcutManager");
			if (shortcutManagerComponent != null) {
				for (HashMap<String, String> component : shortcutManagerComponent) {
					String fieldName = component.get("fieldName");
					if (fieldName != null) var4.add(fieldName);
				}
			}
		} else if (this.mMenuName.equals("mediaprojectionmanager")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_component));
			var4 = new ArrayList<>();
			List<HashMap<String, String>> mediaProjectionManagerComponent = DesignActivity.loadComponentFromName(DesignActivity.currentActivityBean.getActivityName(), "MediaProjectionManager");
			if (mediaProjectionManagerComponent != null) {
				for (HashMap<String, String> component : mediaProjectionManagerComponent) {
					String fieldName = component.get("fieldName");
					if (fieldName != null) var4.add(fieldName);
				}
			}
		} else if (this.mMenuName.equals("biometricprompt")) {
			var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_component));
			var4 = new ArrayList<>();
			List<HashMap<String, String>> biometricPromptComponent = DesignActivity.loadComponentFromName(DesignActivity.currentActivityBean.getActivityName(), "BiometricPrompt");
			if (biometricPromptComponent != null) {
				for (HashMap<String, String> component : biometricPromptComponent) {
					String fieldName = component.get("fieldName");
					if (fieldName != null) var4.add(fieldName);
				}
			}
		} /*else if(this.mMenuName.equals("vibrator")) {
		var2.setTitle(this.getResources().getString(R.string.title_popup_select_vibrator_component));
		var4 = DesignDataManager.getComponentsByType(LogicEditorActivity.filename, 4);
		}*/ /* else if(this.mMenuName.equals("intent")) {
		var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_component));
		var4 = DesignDataManager.getComponentsByType(LogicEditorActivity.filename, 1);
		} else if(this.mMenuName.equals("file")) {
		var2.setTitle(this.getResources().getString(R.string.title_popup_select_file_component));
		var4 = DesignDataManager.getComponentsByType(LogicEditorActivity.filename, 2);
		} else if(this.mMenuName.equals("intentAction")) {
		var2.setTitle(this.getResources().getString(R.string.title_popup_select_intent_action));
		var4 = new ArrayList(Arrays.asList(DefineSource.getIntentAction()));
		} else if(this.mMenuName.equals("activity")) {
		var2.setTitle(this.getResources().getString(R.string.title_popup_select_activity));
		Iterator var17 = ProjectFileManager.javaFileList.iterator();
		
		while(var17.hasNext()) {
		String var18 = (String)var17.next();
		var4.add(var18.substring(0, var18.indexOf(".java")));
		}
		} else if(this.mMenuName.equals("calendar")) {
		var2.setTitle(this.getResources().getString(R.string.title_popup_select_calendar_component));
		var4 = DesignDataManager.getComponentsByType(LogicEditorActivity.filename, 3);
		} else if(this.mMenuName.equals("calendarField")) {
		var2.setTitle(this.getResources().getString(R.string.title_popup_select_calendar_field));
		var4 = new ArrayList(Arrays.asList(DefineSource.CALENDAR_FIELD));
		} else if(this.mMenuName.equals("vibrator")) {
		var2.setTitle(this.getResources().getString(R.string.title_popup_select_vibrator_component));
		var4 = DesignDataManager.getComponentsByType(LogicEditorActivity.filename, 4);
		} else if(this.mMenuName.equals("visible")) {
		var2.setTitle(this.getResources().getString(R.string.title_popup_select_visibility));
		var4 = new ArrayList(Arrays.asList(DefineSource.VISIBILITY_FIELD));
		}*/
		
		Iterator var6 = var4.iterator();
		
		while(var6.hasNext()) {
			RadioButton var12 = this.createSingleItem((String)var6.next());
			this.content.addView(var12);
		}
		
		int var7 = this.content.getChildCount();
		
		for(int var8 = 0; var8 < var7; ++var8) {
			RadioButton var11 = (RadioButton)this.content.getChildAt(var8);
			if(this.argValue.toString().equals(var11.getText().toString())) {
				var11.setChecked(true);
				break;
			}
		}
		
		var2.setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface var1, int var2) {
				mDlg.dismiss();
			}
		});
		var2.setPositiveButton(R.string.btn_accept, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface var1, int var2) {
				
				int var3 = content.getChildCount();
				
				for(int var4 = 0; var4 < var3; ++var4) {
					RadioButton var5 = (RadioButton)content.getChildAt(var4);
					if(var5.isChecked()) {
						setArgValue(var5.getText());
						parentBlock.recalcWidthToParent();
						parentBlock.topBlock().fixLayout();
						parentBlock.pane.calculateWidthHeight();
						break;
					}
				}
				
				mDlg.dismiss();
			}
		});
		this.mDlg = var2.create();
		this.mDlg.show();
	}
	
	private ArrayList<String> getImageNamesFromPath() {
		ArrayList<String> imageNames = new ArrayList<>();
		String dirPath = "/storage/emulated/0/.blacklogics/resources/images/" + DesignActivity.getScId() + "/";
		File directory = new File(dirPath);
		
		if (directory.exists() && directory.isDirectory()) {
			File[] files = directory.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.isFile()) {
						String name = file.getName();
						String extension = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
						if (extension.equals("png") || extension.equals("jpg") || extension.equals("jpeg")) {
							String baseName = name.substring(0, name.lastIndexOf(".")); // Remove extension
							imageNames.add(baseName);
						}
					}
				}
			}
		}
		
		return imageNames;
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
