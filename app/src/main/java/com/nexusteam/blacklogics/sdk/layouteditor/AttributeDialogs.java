package com.nexusteam.blacklogics.sdk.layouteditor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;

import com.nexusteam.blacklogics.SketchwareUtil;
import com.nexusteam.blacklogics.FileUtil;
import com.nexusteam.blacklogics.DesignActivity;
import com.nexusteam.blacklogics.R;
import com.nexusteam.blacklogics.development.Complex;
import com.nexusteam.blacklogics.project.ProjectDataHelper;
import com.nexusteam.blacklogics.sdk.AttributeSet;
import com.nexusteam.blacklogics.sdk.utils.AttributeUtils;
import com.nexusteam.blacklogics.sdk.utils.DimensionUtils;
import com.nexusteam.blacklogics.sdk.utils.ReflectionUtils;
import com.nexusteam.internal.os.layouteditor.color.ColorPickerActivity;
import com.nexusteam.ui.FloatAttributeMaterialDialog;
import com.nexusteam.ui.FloatAttributeMaterialDialog.OnFloatSelected;
import com.nexusteam.ui.TextSizePickerDialog;
import com.nexusteam.ui.TextSizePickerDialog.OnTextSizeSelected;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AttributeDialogs {
	
	public interface Host {
		Context getContext();
		void showMessage(String msg);
		void applyAttribute(View view, Attribute attribute);
		void applyAttribute(View view, Attribute attribute, HashMap<String, Object> map);
		AttributeSet getOrCreateAttributeSet(View view);
		void pushSingleAttributeUndoAndApply(View view, String attrName, String newValue,
		HashMap<String, Object> attributeMeta);
		void pushMultiAttributeUndoAndApply(View view, String[] attrNames,
		String[] rawNewValues, String actionLabel, boolean dimensionSuffix);
		Complex getProjectComplex();
		void handleWidgetConversion(View view, String customClassName);
		AttributeSet cloneAttributeSet(AttributeSet source);
		void applyConvertedAttributeSet(View view, AttributeSet set);
		void pushConvertWidgetUndo(View view, AttributeSet beforeSet, AttributeSet afterSet);
	}
	
	private final EditorUiHelpers ui;
	private final Host host;
	
	public AttributeDialogs(EditorUiHelpers ui, Host host) {
		this.ui = ui;
		this.host = host;
	}
	
	private MaterialAlertDialogBuilder m3Dialog() {
		return new MaterialAlertDialogBuilder(ctx());
	}
	
	
	/** Outlined Material text field for attribute value entry. */
	private View outlinedField(EditText et, String hint) {
		try {
			TextInputLayout til = new TextInputLayout(ctx(), null,
			com.google.android.material.R.attr.textInputOutlinedStyle);
			try { til.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE); } catch (Throwable ignore) {}
			if (hint != null && (et.getHint() == null || et.getHint().length() == 0)) {
				til.setHint(hint);
			}
			int pad = (int) TypedValue.applyDimension(
			TypedValue.COMPLEX_UNIT_DIP, 16, ctx().getResources().getDisplayMetrics());
			til.setPadding(pad, pad / 2, pad, pad / 2);
			if (et.getParent() instanceof android.view.ViewGroup) {
				((android.view.ViewGroup) et.getParent()).removeView(et);
			}
			til.addView(et, new android.widget.LinearLayout.LayoutParams(
			android.view.ViewGroup.LayoutParams.MATCH_PARENT,
			android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
			return til;
		} catch (Throwable t) {
			return et;
		}
	}
	
	private Context ctx() {
		return host.getContext();
	}
	
	
	public void showAttributeInputDialog(final View view,
	final HashMap<String, Object> attribute) {
		if (view == null || attribute == null) return;
		try {
			final MaterialAlertDialogBuilder dialog = m3Dialog();
			Object nameObj = attribute.get("name");
			dialog.setTitle(nameObj != null ? nameObj.toString() : "Attribute");
			dialog.setNeutralButton("Cancel", null);
			
			AttributeSet attrSet = host.getOrCreateAttributeSet(view);
			Object an = attribute.get("attribute_name");
			Object at = attribute.get("argument_type");
			if (an == null || at == null) {
				host.showMessage("Attribute meta missing");
				return;
			}
			final String attrName = an.toString();
			final String attrType = at.toString();
			String currentValue = (attrSet != null
			&& attrSet.getAttribute(attrName) != null)
			? attrSet.getAttribute(attrName).getValue() : "";
			if (currentValue == null) currentValue = "";
			if (currentValue.trim().isEmpty()) {
				currentValue = resolveCurrentValueFromView(view, attrName, attrType);
				if (currentValue == null) currentValue = "";
			}
			
			boolean shouldShow = true;
			
			if ("boolean".equals(attrType)) {
				RadioGroup rg = ui.buildBooleanRadioGroup(currentValue);
				final RadioGroup finalRg = rg;
				dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface d, int w) {
						String val = ui.getCheckedRadioText(finalRg);
						if (val != null) host.pushSingleAttributeUndoAndApply(view, attrName, val, attribute);
					}
				});
				dialog.setView(rg);
				
			} else if ("int".equals(attrType)) {
				final TextInputEditText et = new TextInputEditText(ctx());
				et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
				if (!currentValue.isEmpty()) et.setText(currentValue);
				dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface d, int w) {
						host.pushSingleAttributeUndoAndApply(view, attrName,
						et.getText().toString(), attribute);
					}
				});
				dialog.setView(outlinedField(et, attrName));
				
			} else if ("float".equals(attrType)) {
				shouldShow = false;
				handleFloatAttributeDialog(view, attribute, attrName, currentValue);
				
			} else if ("String".equals(attrType)) {
				if ("Convert Widget".equals(attrName)) {
					showConvertWidgetDialog(view);
					shouldShow = false;
				} else {
					final TextInputEditText et = new TextInputEditText(ctx());
					if (!currentValue.isEmpty()) et.setText(currentValue);
					dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface d, int w) {
							host.pushSingleAttributeUndoAndApply(view, attrName,
							et.getText().toString(), attribute);
						}
					});
					dialog.setView(outlinedField(et, attrName));
				}
				
			} else if ("Size".equals(attrType)) {
				if ("android:layout_width".equals(attrName)
				|| "android:layout_height".equals(attrName)) {
					showSizePickerDialog(view, attrName, currentValue, attribute);
					shouldShow = false;
				} else {
					View sizeView = buildSizePickerView(currentValue);
					final RadioGroup rgSize = (RadioGroup) sizeView.getTag(R.id.rg_width);
					final EditText etCustomDp = (EditText) sizeView.getTag(R.id.edittext_width);
					dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface d, int w) {
							String val;
							int checkedId = rgSize.getCheckedRadioButtonId();
							if (checkedId == 1) val = "match_parent";
							else if (checkedId == 2) val = "wrap_content";
							else if (checkedId == 3) val = "fill_parent";
							else val = etCustomDp.getText().toString() + "dp";
							host.pushSingleAttributeUndoAndApply(view, attrName, val, attribute);
						}
					});
					dialog.setView(sizeView);
				}
				
			} else if ("Color".equals(attrType)) {
				shouldShow = false;
				int initial = currentValue.isEmpty() ? Color.WHITE
				: parseColorValue(currentValue);
				final String fa = attrName;
				final HashMap<String, Object> fAttr = attribute;
				final View fView = view;
				BroadcastReceiver colorReceiver = new BroadcastReceiver() {
					@Override
					public void onReceive(Context ctx, Intent i) {
						String col = i.getStringExtra("color");
						if (col != null) {
							host.pushSingleAttributeUndoAndApply(fView, fa, col, fAttr);
							LocalBroadcastManager.getInstance(ctx())
							.unregisterReceiver(this);
						}
					}
				};
				LocalBroadcastManager.getInstance(ctx())
				.registerReceiver(colorReceiver, new IntentFilter("data"));
				Intent colorIntent = new Intent(ctx(), ColorPickerActivity.class);
				colorIntent.putExtra("initialColor", initial);
				ctx().startActivity(colorIntent);
				
			} else if ("Drawable".equals(attrType) || "image".equals(attrType)) {
				// thumbnail picker; merges .blacklogics/data/<sc>/files/resource/drawable
				// and .blacklogics/resources/images/<sc>
				shouldShow = false;
				ImagePickerDialog.show(ctx(), String.valueOf(attribute.get("name")),
						DrawableResolver.currentScId(ctx()), currentValue,
						new ImagePickerDialog.Callback() {
					@Override public void onPicked(String name) {
						host.pushSingleAttributeUndoAndApply(view, attrName,
								"@drawable/" + name, attribute);
					}
				});
				
			} else if ("dimension".equals(attrType)) {
				final TextInputEditText et = new TextInputEditText(ctx());
				et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL
				| InputType.TYPE_NUMBER_FLAG_SIGNED);
				if (!currentValue.isEmpty()) {
					et.setText(currentValue.endsWith("dp") || currentValue.endsWith("sp")
					|| currentValue.endsWith("px") ? currentValue : currentValue + "dp");
				} else {
					et.setText("0dp");
				}
				dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface d, int w) {
						String val = et.getText().toString().trim();
						if (val.length() > 0 && !val.endsWith("dp") && !val.endsWith("sp")
						&& !val.endsWith("px") && val.matches("-?\\d+(\\.\\d+)?")) {
							val = val + "dp";
						}
						host.pushSingleAttributeUndoAndApply(view, attrName, val, attribute);
					}
				});
				dialog.setView(outlinedField(et, "e.g. 16dp"));
				
			} else if ("enum".equals(attrType)) {
				@SuppressWarnings("unchecked")
				ArrayList<String> xmlArgs =
				(ArrayList<String>) attribute.get("xml_arguments");
				final RadioGroup rgEnum = ui.buildScrollableRadioGroup(
				xmlArgs != null ? xmlArgs : new ArrayList<String>(), currentValue);
				dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface d, int w) {
						String val = ui.getCheckedRadioText(rgEnum);
						if (val != null) host.pushSingleAttributeUndoAndApply(view, attrName, val, attribute);
					}
				});
				dialog.setView(ui.wrapInScrollViewInternal(rgEnum));
				
			} else if ("flag".equals(attrType)) {
				@SuppressWarnings("unchecked")
				ArrayList<String> xmlArgs =
				(ArrayList<String>) attribute.get("xml_arguments");
				if (xmlArgs == null) xmlArgs = new ArrayList<String>();
				final List<String> selectedFlags = currentValue.isEmpty()
				? new ArrayList<String>()
				: Arrays.asList(currentValue.split("\\|"));
				
				LinearLayout checkContainer = new LinearLayout(ctx());
				checkContainer.setOrientation(LinearLayout.VERTICAL);
				int pad = (int) DimensionUtils.getValueInPx(ctx(), "8dp");
				checkContainer.setPadding(pad, pad, pad, pad);
				final ArrayList<MaterialCheckBox> checkBoxes = new ArrayList<MaterialCheckBox>();
				for (int i = 0; i < xmlArgs.size(); i++) {
					String xa = xmlArgs.get(i);
					MaterialCheckBox cb = new MaterialCheckBox(ctx());
					cb.setText(xa);
					cb.setChecked(selectedFlags.contains(xa));
					checkContainer.addView(cb);
					checkBoxes.add(cb);
				}
				dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface d, int w) {
						StringBuilder sb = new StringBuilder();
						for (int i = 0; i < checkBoxes.size(); i++) {
							MaterialCheckBox cb = checkBoxes.get(i);
							if (cb.isChecked()) {
								if (sb.length() > 0) sb.append("|");
								sb.append(cb.getText().toString());
							}
						}
						host.pushSingleAttributeUndoAndApply(view, attrName, sb.toString(), attribute);
					}
				});
				dialog.setView(ui.wrapInScrollViewInternal(checkContainer));
				
			} else if ("customView".equals(attrType)) {
				shouldShow = false;
				showCustomViewPickerDialog(view, attrName, currentValue, attribute);
				
			} else if ("custom".equals(attrType)) {
				shouldShow = false;
				if ("android:layout_margin_all".equals(attrName)) {
					showMarginEditorDialog(view);
				} else if ("android:padding_all".equals(attrName)) {
					showPaddingEditorDialog(view);
				}
			}
			
			if (shouldShow) {
				dialog.show();
			}
		} catch (Throwable t) {
			Log.e("AttributeDialogs", "showAttributeInputDialog failed", t);
			try { host.showMessage("Attribute dialog error: " + t.getMessage()); } catch (Throwable ignore) {}
		}
	}
	
	/** When AttributeSet has no value, infer from live View so dialogs pre-select correctly. */
	private String resolveCurrentValueFromView(View view, String attrName, String attrType) {
		if (view == null || attrName == null) return "";
		try {
			if ("android:visibility".equals(attrName)) {
				int v = view.getVisibility();
				if (v == View.VISIBLE) return "visible";
				if (v == View.INVISIBLE) return "invisible";
				if (v == View.GONE) return "gone";
			}
			if ("android:enabled".equals(attrName)) {
				return view.isEnabled() ? "true" : "false";
			}
			if ("android:orientation".equals(attrName) && view instanceof LinearLayout) {
				return ((LinearLayout) view).getOrientation() == LinearLayout.HORIZONTAL
				? "horizontal" : "vertical";
			}
			if ("android:layout_width".equals(attrName) || "android:layout_height".equals(attrName)) {
				ViewGroup.LayoutParams lp = view.getLayoutParams();
				if (lp != null) {
					int dim = "android:layout_width".equals(attrName) ? lp.width : lp.height;
					if (dim == ViewGroup.LayoutParams.MATCH_PARENT) return "match_parent";
					if (dim == ViewGroup.LayoutParams.WRAP_CONTENT) return "wrap_content";
					if (dim > 0) {
						float d = ctx().getResources().getDisplayMetrics().density;
						return Math.round(dim / d) + "dp";
					}
				}
			}
		} catch (Throwable ignore) {}
		if ("boolean".equals(attrType)) return "true";
		return "";
	}
	
	private int parseColorValue(String value) {
		if (value == null || value.trim().isEmpty()) return Color.BLACK;
		String trimmed = value.trim();
		try {
			if (trimmed.matches("-?\\d+")) {
				return (int) Long.parseLong(trimmed);
			}
			return Color.parseColor(trimmed);
		} catch (Exception e) {
			return Color.BLACK;
		}
	}
	
	
	private static final String CUSTOM_VIEW_NONE = "none";
	
	private void showCustomViewPickerDialog(final View view, final String attrName,
	final String currentValue, final HashMap<String, Object> attribute) {
		
		ArrayList<String> customViewNames = new ArrayList<String>();
		customViewNames.add(CUSTOM_VIEW_NONE);
		
		try {
			Complex complex = host.getProjectComplex();
			if (complex != null) {
				List<String> fromComplex = complex.getCustomViewNames();
				if (fromComplex != null) {
					for (int i = 0; i < fromComplex.size(); i++) {
						String n = fromComplex.get(i);
						if (n != null && !n.trim().isEmpty() && !customViewNames.contains(n)) {
							if (n.startsWith("_drawer_") || n.contains("_drawer_")) continue;
							customViewNames.add(n);
						}
					}
				}
			}
		} catch (Exception e) {
			Log.e("AttributeDialogs", "Error loading custom views from Complex", e);
		}
		
		try {
			String scId = null;
			try {
				Complex cx = host.getProjectComplex();
				if (cx != null) scId = cx.sc_id;
			} catch (Throwable ignore) {}
			if (scId != null && scId.length() > 0) {
				com.nexusteam.internal.ly ly = com.nexusteam.internal.ma.b(scId);
				if (ly != null && ly.b() != null) {
					java.util.ArrayList list = ly.b();
					for (Object o : list) {
						if (!(o instanceof com.nexusteam.internal.beans.ProjectFileBean)) continue;
						com.nexusteam.internal.beans.ProjectFileBean p =
						(com.nexusteam.internal.beans.ProjectFileBean) o;
						if (p.fileType != com.nexusteam.internal.beans.ProjectFileBean.PROJECT_FILE_TYPE_CUSTOM_VIEW) {
							continue;
						}
						String n = p.fileName;
						if (n == null || n.trim().isEmpty()) continue;
						if (n.startsWith("_drawer_")) continue;
						if (!customViewNames.contains(n)) customViewNames.add(n);
					}
				}
			}
		} catch (Exception e) {
			Log.e("AttributeDialogs", "Error loading custom views from project files", e);
		}
		
		String selected = currentValue;
		if (selected == null || selected.trim().isEmpty()
		|| CUSTOM_VIEW_NONE.equalsIgnoreCase(selected.trim())) {
			selected = CUSTOM_VIEW_NONE;
		}
		
		final RadioGroup rgCustomView =
		ui.buildScrollableRadioGroup(customViewNames, selected);
		
		final MaterialAlertDialogBuilder dialog = m3Dialog();
		dialog.setTitle(attrName != null ? attrName : "Custom View");
		dialog.setNeutralButton("Cancel", null);
		dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface d, int w) {
				String val = ui.getCheckedRadioText(rgCustomView);
				if (val == null) return;
				if (CUSTOM_VIEW_NONE.equalsIgnoreCase(val.trim())) {
					val = "";
				}
				host.pushSingleAttributeUndoAndApply(view, attrName, val, attribute);
			}
		});
		dialog.setView(ui.wrapInScrollViewInternal(rgCustomView));
		dialog.show();
	}
	
	
	private void showSizePickerDialog(final View view,
	final String attrName,
	final String currentValue,
	final HashMap<String, Object> attribute) {
		LinearLayout container = new LinearLayout(ctx());
		container.setOrientation(LinearLayout.VERTICAL);
		int pad = (int) SketchwareUtil.getDip(ctx(), 16);
		container.setPadding(pad, pad / 2, pad, pad / 2);
		
		final RadioGroup rg = new RadioGroup(ctx());
		rg.setOrientation(RadioGroup.VERTICAL);
		
		MaterialRadioButton rbMatch = new MaterialRadioButton(ctx());
		rbMatch.setId(1);
		rbMatch.setText("match_parent");
		MaterialRadioButton rbWrap = new MaterialRadioButton(ctx());
		rbWrap.setId(2);
		rbWrap.setText("wrap_content");
		MaterialRadioButton rbFill = new MaterialRadioButton(ctx());
		rbFill.setText("fill_parent");
		rbFill.setId(3);
		MaterialRadioButton rbCustom = new MaterialRadioButton(ctx());
		rbCustom.setId(3);
		rbCustom.setText("Custom dp:");
		rg.addView(rbMatch);
		rg.addView(rbWrap);
		rg.addView(rbFill);
		rg.addView(rbCustom);
		
		final TextInputEditText etCustom = new TextInputEditText(ctx());
		etCustom.setInputType(InputType.TYPE_CLASS_NUMBER);
		etCustom.setHint("e.g. 100");
		
		if ("match_parent".equals(currentValue)) rg.check(1);
		else if ("wrap_content".equals(currentValue)) rg.check(2);
		else if ("fill_parent".equals(currentValue)) rg.check(3);
		else {
			rg.check(3);
			etCustom.setText(currentValue.replace("dp", "").trim());
		}
		
		container.addView(rg);
		container.addView(etCustom);
		
		m3Dialog()
		.setTitle(attrName)
		.setView(container)
		.setPositiveButton("Save", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface d, int w) {
				String val;
				int cid = rg.getCheckedRadioButtonId();
				if (cid == 1) val = "match_parent";
				else if (cid == 2) val = "wrap_content";
				else if (cid == 3) val = "fill_parent";
				else val = etCustom.getText().toString().trim() + "dp";
				host.pushSingleAttributeUndoAndApply(view, attrName, val, attribute);
			}
		})
		.setNeutralButton("Cancel", null)
		.show();
	}
	
	
	public void showMarginEditorDialog(final View view) { showInsets(view, true); }

	public void showPaddingEditorDialog(final View view) { showInsets(view, false); }

	private void showInsets(final View view, final boolean margin) {
		int[] v = readInsets(view, margin);
		boolean all = v[0] == v[1] && v[1] == v[2] && v[2] == v[3];
		android.graphics.drawable.Drawable icon = null;
		try {
			int id = ctx().getResources().getIdentifier(margin ? "ic_margin" : "ic_padding",
					"drawable", ctx().getPackageName());
			if (id != 0) icon = ctx().getDrawable(id);
		} catch (Throwable ignore) {}
		InsetsDialog.show(ctx(), margin ? "Margin" : "Padding", icon, margin, all,
				v[0], v[1], v[2], v[3], new InsetsDialog.Callback() {
			@Override public void onSave(boolean allSides, int l, int t, int r, int b) {
				saveInsets(view, margin, allSides, l, t, r, b);
			}
		});
	}

	/** Current values come from the attribute store (what is really in the XML), not from the view. */
	private int[] readInsets(View view, boolean margin) {
		int[] v = new int[4]; // left, top, right, bottom (dp)
		AttributeSet set = host.getOrCreateAttributeSet(view);
		if (set == null) return v;
		String p = margin ? "android:layout_margin" : "android:padding";
		Integer x;
		if ((x = dpOf(set, p)) != null) v[0] = v[1] = v[2] = v[3] = x;
		if ((x = dpOf(set, p + "Horizontal")) != null) { v[0] = x; v[2] = x; }
		if ((x = dpOf(set, p + "Vertical")) != null) { v[1] = x; v[3] = x; }
		if ((x = dpOf(set, p + "Start")) != null) v[0] = x;
		if ((x = dpOf(set, p + "End")) != null) v[2] = x;
		if ((x = dpOf(set, p + "Left")) != null) v[0] = x;
		if ((x = dpOf(set, p + "Top")) != null) v[1] = x;
		if ((x = dpOf(set, p + "Right")) != null) v[2] = x;
		if ((x = dpOf(set, p + "Bottom")) != null) v[3] = x;
		return v;
	}

	private Integer dpOf(AttributeSet set, String name) {
		Attribute a = set.getAttribute(name);
		if (a == null || a.getValue() == null) return null;
		java.util.regex.Matcher m = java.util.regex.Pattern
				.compile("^(-?\\d+(?:\\.\\d+)?)\\s*(dp|dip|sp|px)?$").matcher(a.getValue().trim());
		if (!m.matches()) return null; // @dimen/... etc: leave as is
		float f = Float.parseFloat(m.group(1));
		if ("px".equals(m.group(2))) f = f / ctx().getResources().getDisplayMetrics().density;
		return Math.round(f);
	}

	private void saveInsets(View view, boolean margin, boolean all, int l, int t, int r, int b) {
		String p = margin ? "android:layout_margin" : "android:padding";
		String label = margin ? "Change margin" : "Change padding";
		AttributeSet set = host.getOrCreateAttributeSet(view);
		String[] sides = {p + "Left", p + "Top", p + "Right", p + "Bottom"};
		String[] others = {p + "Start", p + "End", p + "Horizontal", p + "Vertical"};
		if (set != null) {
			// shorthand + specific attributes conflict in real Android (padding wins), so keep only one form
			for (String s : others) set.remove(s);
			if (all) { for (String s : sides) set.remove(s); } else { set.remove(p); }
		}
		if (all) {
			host.pushMultiAttributeUndoAndApply(view, new String[] {p},
					new String[] {String.valueOf(l)}, label, true);
			float d = ctx().getResources().getDisplayMetrics().density;
			int px = Math.round(l * d);
			if (margin) {
				ViewGroup.LayoutParams lp = view.getLayoutParams();
				if (lp instanceof ViewGroup.MarginLayoutParams) {
					((ViewGroup.MarginLayoutParams) lp).setMargins(px, px, px, px);
					view.setLayoutParams(lp);
				}
			} else {
				view.setPadding(px, px, px, px);
			}
		} else {
			host.pushMultiAttributeUndoAndApply(view, sides, new String[] {
					String.valueOf(l), String.valueOf(t), String.valueOf(r), String.valueOf(b)},
					label, true);
		}
	}

	
	public void showWrapInContainerDialog(final View view, final WrapCallback callback) {
		final String[] containers = {"LinearLayout (Vertical)", "LinearLayout (Horizontal)",
			"FrameLayout", "ScrollView"};
		m3Dialog()
		.setTitle("Wrap in Container")
		.setItems(containers, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface d, int which) {
				callback.onContainerChosen(view, which);
			}
		})
		.setNegativeButton("Cancel", null)
		.show();
	}
	
	public interface WrapCallback {
		void onContainerChosen(View view, int containerType);
	}
	
	
	public void showCommonAttributesDialog(View view) {
		View inflated = LayoutInflater.from(ctx())
		.inflate(R.layout.common_attributes, null);
		m3Dialog()
		.setView(inflated)
		.setNegativeButton("Cancel", null)
		.show();
	}
	
	/**
	 * User-defined custom attribute: Material dialog with name + value EditTexts.
	 * Saves into AttributeStore → appears in XmlGenerator output.
	 */
	public void showCustomAttributesDialog(final View view) {
		if (view == null) return;
		float d = ctx().getResources().getDisplayMetrics().density;
		int pad = (int) (20 * d);

		LinearLayout root = new LinearLayout(ctx());
		root.setOrientation(LinearLayout.VERTICAL);
		root.setPadding(pad, pad, pad, pad / 2);

		final com.google.android.material.textfield.TextInputLayout tilName =
				new com.google.android.material.textfield.TextInputLayout(ctx());
		tilName.setHint("Attribute name");
		tilName.setBoxBackgroundMode(
				com.google.android.material.textfield.TextInputLayout.BOX_BACKGROUND_OUTLINE);
		final com.google.android.material.textfield.TextInputEditText etName =
				new com.google.android.material.textfield.TextInputEditText(tilName.getContext());
		etName.setSingleLine(true);
		etName.setHint("android:clipToPadding");
		tilName.addView(etName);
		root.addView(tilName);

		final com.google.android.material.textfield.TextInputLayout tilValue =
				new com.google.android.material.textfield.TextInputLayout(ctx());
		tilValue.setHint("Value");
		tilValue.setBoxBackgroundMode(
				com.google.android.material.textfield.TextInputLayout.BOX_BACKGROUND_OUTLINE);
		LinearLayout.LayoutParams lpV = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		lpV.topMargin = (int) (12 * d);
		tilValue.setLayoutParams(lpV);
		final com.google.android.material.textfield.TextInputEditText etValue =
				new com.google.android.material.textfield.TextInputEditText(tilValue.getContext());
		etValue.setSingleLine(true);
		etValue.setHint("false / 16dp / #FF0000");
		tilValue.addView(etValue);
		root.addView(tilValue);

		TextView hint = new TextView(ctx());
		hint.setText("Saved to AttributeStore and included in generated XML.");
		hint.setTextSize(12f);
		hint.setAlpha(0.7f);
		LinearLayout.LayoutParams lpH = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		lpH.topMargin = (int) (10 * d);
		hint.setLayoutParams(lpH);
		root.addView(hint);

		final androidx.appcompat.app.AlertDialog dialog = m3Dialog()
				.setTitle("Custom attributes")
				.setView(root)
				.setPositiveButton("Apply", null)
				.setNegativeButton("Cancel", null)
				.create();

		dialog.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override
			public void onShow(DialogInterface d) {
				dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
						.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						String name = etName.getText() != null
								? etName.getText().toString().trim() : "";
						String value = etValue.getText() != null
								? etValue.getText().toString().trim() : "";
						if (name.length() == 0) {
							host.showMessage("Enter attribute name");
							return;
						}
						if (!name.contains(":")) {
							name = "android:" + name;
						}
						try {
							HashMap<String, Object> meta = new HashMap<String, Object>();
							meta.put("attribute_name", name);
							meta.put("name", name);
							meta.put("argument_type", "String");
							host.pushSingleAttributeUndoAndApply(view, name, value, meta);
							host.showMessage("Saved: " + name);
							dialog.dismiss();
						} catch (Exception e) {
							host.showMessage("Failed: " + e.getMessage());
						}
					}
				});
			}
		});
		dialog.show();
	}

	private View buildSizePickerView(String currentValue) {
		final MaterialRadioButton rbMatch = new MaterialRadioButton(ctx());
		final MaterialRadioButton rbWrap = new MaterialRadioButton(ctx());
		final MaterialRadioButton rbCustom = new MaterialRadioButton(ctx());
		final TextInputEditText etCustom = new TextInputEditText(ctx());
		final TextView tvDp = new TextView(ctx());
		
		rbMatch.setText("match_parent");
		rbWrap.setText("wrap_content");
		MaterialRadioButton rbFill = new MaterialRadioButton(ctx());
		rbFill.setText("fill_parent");
		rbFill.setId(3);
		rbCustom.setText("Value in dp");
		tvDp.setText("dp");
		rbMatch.setId(1);
		rbWrap.setId(2);
		rbCustom.setId(3);
		etCustom.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
		
		final LinearLayout customRow = new LinearLayout(ctx());
		customRow.setOrientation(LinearLayout.HORIZONTAL);
		customRow.setVisibility(View.GONE);
		LinearLayout.LayoutParams etP = new LinearLayout.LayoutParams(
		0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
		customRow.addView(etCustom, etP);
		customRow.addView(tvDp);
		
		final RadioGroup rgSize = new RadioGroup(ctx());
		rgSize.addView(rbMatch);
		rgSize.addView(rbWrap);
		rgSize.addView(rbFill);
		rgSize.addView(rbCustom);
		rgSize.addView(customRow);
		
		rbCustom.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(android.widget.CompoundButton btn, boolean isChecked) {
				customRow.setVisibility(isChecked ? View.VISIBLE : View.GONE);
			}
		});
		
		if (currentValue != null && !currentValue.isEmpty()) {
			if ("match_parent".equals(currentValue)) rbMatch.setChecked(true);
			else if ("wrap_content".equals(currentValue)) rbWrap.setChecked(true);
		else if ("fill_parent".equals(currentValue)) rbFill.setChecked(true);
			else {
				rbCustom.setChecked(true);
				etCustom.setText(currentValue.replace("dp", "").trim());
			}
		}
		
		rgSize.setTag(R.id.rg_width, rgSize);
		rgSize.setTag(R.id.edittext_width, etCustom);
		return rgSize;
	}
	
	
	private void handleFloatAttributeDialog(final View view,
	final HashMap<String, Object> attribute,
	final String attrName,
	final String currentValue) {
		if ("android:textSize".equals(attrName)) {
			String currentTs = currentValue;
			if ((currentValue == null || currentValue.isEmpty())
			&& view instanceof TextView) {
				float px = ((TextView) view).getTextSize();
				float sp = px / ctx().getResources().getDisplayMetrics().scaledDensity;
				currentTs = String.format(Locale.getDefault(), "%.1fsp", sp);
			}
			new TextSizePickerDialog(ctx(), currentTs,
			new OnTextSizeSelected() {
				@Override
				public void onSelected(String selectedValue) {
					try {
						host.pushSingleAttributeUndoAndApply(view, attrName, selectedValue, attribute);
					} catch (Exception e) {
						host.showMessage("textSize error: " + e.getMessage());
					}
				}
			}).show();
		} else {
			float currentFloat = 0f;
			try {
				if (currentValue != null && !currentValue.isEmpty())
				currentFloat = Float.parseFloat(currentValue);
			} catch (NumberFormatException ignored) {}
			
			FloatAttributeMaterialDialog.show(ctx(),
			attribute.get("name").toString(), currentFloat,
			new OnFloatSelected() {
				@Override
				public void onSelected(float value) {
					try {
						String formatted = String.format(
						Locale.getDefault(), "%.1f", value);
						host.pushSingleAttributeUndoAndApply(view, attrName, formatted, attribute);
					} catch (Exception e) {
						host.showMessage("Error: " + e.getMessage());
					}
				}
			});
		}
	}
	
	
	private void showConvertWidgetDialog(final View view) {
		final EditText editText = new EditText(ctx());
		editText.setHint("android.widget.SearchView");
		editText.setSingleLine(true);
		
		AttributeSet attrSet = host.getOrCreateAttributeSet(view);
		if (attrSet != null) {
			Attribute ca = attrSet.getAttribute("app:convertedWidget");
			if (ca != null) editText.setText(ca.getValue());
		}
		
		TextView hint = new TextView(ctx());
		hint.setText("Enter custom widget class name:\nExamples: MaterialButton, CardView");
		hint.setTextSize(12);
		hint.setTextColor(0xFF666666);
		
		LinearLayout layout = new LinearLayout(ctx());
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setPadding(50, 30, 50, 30);
		layout.addView(hint);
		layout.addView(editText);
		
		m3Dialog()
		.setTitle("Convert Widget")
		.setView(layout)
		.setPositiveButton("Convert", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface d, int w) {
				String className = editText.getText().toString().trim();
				if (!className.isEmpty()) {
					handleWidgetConversionRequest(view, className);
				}
			}
		})
		.setNegativeButton("Cancel", null)
		.show();
	}
	
	private void handleWidgetConversionRequest(View originalView, String customClassName) {
		try {
			if (originalView == null) return;
			if (customClassName == null || customClassName.trim().isEmpty()) return;
			
			String originalTag = originalView.getClass().getSimpleName();
			AttributeSet originalAttributes = host.getOrCreateAttributeSet(originalView);
			final AttributeSet beforeSet = (originalAttributes != null)
			? host.cloneAttributeSet(originalAttributes) : new AttributeSet();
			
			AttributeSet newAttributeSet = new AttributeSet();
			if (originalAttributes != null) {
				for (Attribute a : originalAttributes.getAttributes()) {
					if ("app:convertedWidget".equals(a.getName())) continue;
					newAttributeSet.add(new Attribute(
					a.getName(), a.getValue(), originalTag, customClassName));
				}
			}
			newAttributeSet.add(new Attribute(
			"app:convertedWidget", customClassName,
			originalTag, customClassName));
			final AttributeSet afterSet = newAttributeSet;
			
			host.applyConvertedAttributeSet(originalView, afterSet);
			host.pushConvertWidgetUndo(originalView, beforeSet, afterSet);
		} catch (Exception e) {
			Log.e("AttributeDialogs", "Conversion failed", e);
			host.showMessage("Conversion error: " + e.getMessage());
		}
	}
}
