package com.nexusteam.blacklogics.sdk.layouteditor;

import android.content.Context;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.nexusteam.blacklogics.SketchwareUtil;
import com.nexusteam.blacklogics.sdk.utils.DimensionUtils;

import java.util.List;

public class EditorUiHelpers {

	private final Context context;

	public EditorUiHelpers(Context context) {
		this.context = context;
	}

	public int parseDimension(String value) {
		if ("match_parent".equals(value)) return ViewGroup.LayoutParams.MATCH_PARENT;
		if ("wrap_content".equals(value)) return ViewGroup.LayoutParams.WRAP_CONTENT;
		try {
			return (int) DimensionUtils.getValueInPx(context, value);
		} catch (Exception e) {
			return ViewGroup.LayoutParams.WRAP_CONTENT;
		}
	}

	public ViewGroup.LayoutParams createCompatibleLayoutParams(View view, int w, int h) {
		ViewParent parent = view.getParent();
		if (parent instanceof androidx.constraintlayout.widget.ConstraintLayout) {
			ViewGroup.LayoutParams old = view.getLayoutParams();
			if (old instanceof androidx.constraintlayout.widget.ConstraintLayout.LayoutParams) {
				// keep constraints + margins, only change size
				androidx.constraintlayout.widget.ConstraintLayout.LayoutParams c =
						new androidx.constraintlayout.widget.ConstraintLayout.LayoutParams(
						(androidx.constraintlayout.widget.ConstraintLayout.LayoutParams) old);
				c.width = w;
				c.height = h;
				return c;
			}
			return new androidx.constraintlayout.widget.ConstraintLayout.LayoutParams(w, h);
		}
		if (parent instanceof LinearLayout)
		return new LinearLayout.LayoutParams(w, h);
		if (parent instanceof RelativeLayout)
		return new RelativeLayout.LayoutParams(w, h);
		if (parent instanceof FrameLayout)
		return new FrameLayout.LayoutParams(w, h);
		return new ViewGroup.MarginLayoutParams(w, h);
	}

	public RadioGroup buildScrollableRadioGroup(List<String> options, String selectedValue) {
		RadioGroup radioGroup = new RadioGroup(context);
		radioGroup.setOrientation(RadioGroup.VERTICAL);
		int pad = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 4, context.getResources().getDisplayMetrics());
		radioGroup.setPadding(pad * 2, pad, pad * 2, pad);
		String sel = selectedValue != null ? selectedValue.trim() : "";
		boolean anyChecked = false;
		for (int i = 0; i < options.size(); i++) {
			String option = options.get(i);
			MaterialRadioButton rb = new MaterialRadioButton(context);
			rb.setId(View.generateViewId());
			rb.setText(option);
			rb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			rb.setPadding(pad, pad, pad, pad);
			try {
				TypedValue tv = new TypedValue();
				if (context.getTheme().resolveAttribute(
						com.google.android.material.R.attr.colorOnSurface, tv, true)) {
					rb.setTextColor(tv.data);
				}
			} catch (Throwable ignore) {}
			if (!anyChecked && option != null && option.equalsIgnoreCase(sel)) {
				rb.setChecked(true);
				anyChecked = true;
			}
			radioGroup.addView(rb);
		}
		if (!anyChecked && options != null && options.size() == 1) {
			View only = radioGroup.getChildAt(0);
			if (only instanceof RadioButton) ((RadioButton) only).setChecked(true);
		}
		return radioGroup;
	}

	@Nullable
	public String getCheckedRadioText(RadioGroup radioGroup) {
		int checkedId = radioGroup.getCheckedRadioButtonId();
		if (checkedId == -1) return null;
		RadioButton rb = (RadioButton) radioGroup.findViewById(checkedId);
		return rb != null ? rb.getText().toString() : null;
	}

	public RadioGroup buildBooleanRadioGroup(String currentValue) {
		int pad = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 4, context.getResources().getDisplayMetrics());
		MaterialRadioButton rbTrue = new MaterialRadioButton(context);
		rbTrue.setText("True");
		rbTrue.setId(View.generateViewId());
		rbTrue.setPadding(pad, pad, pad, pad);
		MaterialRadioButton rbFalse = new MaterialRadioButton(context);
		rbFalse.setText("False");
		rbFalse.setId(View.generateViewId());
		rbFalse.setPadding(pad, pad, pad, pad);
		RadioGroup rg = new RadioGroup(context);
		rg.setPadding(pad * 2, pad, pad * 2, pad);
		rg.addView(rbTrue);
		rg.addView(rbFalse);
		String cv = currentValue != null ? currentValue.trim().toLowerCase() : "";
		if ("true".equals(cv) || "1".equals(cv) || "yes".equals(cv)) {
			rbTrue.setChecked(true);
		} else if ("false".equals(cv) || "0".equals(cv) || "no".equals(cv)) {
			rbFalse.setChecked(true);
		} else {
			rbTrue.setChecked(true);
		}
		return rg;
	}

	/** Material TextInput for dp / number fields in attribute dialogs. */
	public EditText createDpEditText(int currentDpValue, String hint) {
		TextInputEditText et = new TextInputEditText(context);
		et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED
				| InputType.TYPE_NUMBER_FLAG_DECIMAL);
		et.setHint(hint);
		if (currentDpValue != 0) et.setText(String.valueOf(currentDpValue));
		return et;
	}

	public TextInputLayout wrapInTextInputLayout(EditText editText, String hint) {
		TextInputLayout til = new TextInputLayout(context,
				null, com.google.android.material.R.attr.textInputOutlinedStyle);
		try {
			til.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE);
		} catch (Throwable ignore) {}
		if (hint != null) til.setHint(hint);
		int pad = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 16, context.getResources().getDisplayMetrics());
		til.setPadding(pad, pad / 2, pad, pad / 2);
		if (editText.getParent() instanceof ViewGroup) {
			((ViewGroup) editText.getParent()).removeView(editText);
		}
		til.addView(editText, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		return til;
	}

	public LinearLayout labeledRow(String labelText, View inputView) {
		LinearLayout row = new LinearLayout(context);
		row.setOrientation(LinearLayout.HORIZONTAL);
		row.setGravity(android.view.Gravity.CENTER_VERTICAL);
		int pad = (int) SketchwareUtil.getDip(context, 4);
		row.setPadding(0, pad, 0, pad);

		TextView label = new TextView(context);
		label.setText(labelText);
		label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
		label.setMinWidth((int) SketchwareUtil.getDip(context, 80));
		row.addView(label);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
		0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
		row.addView(inputView, lp);

		return row;
	}

	public ScrollView wrapInScrollViewInternal(View v) {
		ScrollView sv = new ScrollView(context);
		sv.addView(v);
		return sv;
	}

	public int resolveThemeColor(int attrResId, int fallbackColor) {
		try {
			TypedValue tv = new TypedValue();
			if (context.getTheme().resolveAttribute(attrResId, tv, true)) {
				if (tv.resourceId != 0) {
					return ContextCompat.getColor(context, tv.resourceId);
				}
				return tv.data;
			}
		} catch (Exception ignored) {}
		return fallbackColor;
	}
}
