package com.nexusteam.blacklogics.sdk.layouteditor;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * Padding / Margin dialog:
 *  - "<x> on all sides" checked  -> one outlined field
 *  - unchecked                    -> left | right / top | bottom (dp suffix, range hint/error)
 */
final class InsetsDialog {

	interface Callback { void onSave(boolean allSides, int l, int t, int r, int b); }

	private InsetsDialog() {}

	static void show(final Context ctx, final String kind /* "Padding" | "Margin" */,
			Drawable icon, boolean allowNegative,
			boolean allSides, int l, int t, int r, int b, final Callback cb) {

		final int min = allowNegative ? -999 : 0, max = 999;
		final String rangeMsg = min + " ~ " + max;
		final float d = ctx.getResources().getDisplayMetrics().density;

		LinearLayout root = new LinearLayout(ctx);
		root.setOrientation(LinearLayout.VERTICAL);
		root.setPadding((int) (24 * d), (int) (8 * d), (int) (24 * d), 0);

		// ---- single field (all sides)
		final TextInputLayout tilAll = field(ctx, "Enter " + kind.toLowerCase() + " value", false, allowNegative);
		final TextInputEditText etAll = (TextInputEditText) tilAll.getEditText();
		if (allSides && l != 0) etAll.setText(String.valueOf(l));
		root.addView(tilAll, lp(d, 0, 12));

		// ---- 2x2 grid
		final LinearLayout grid = new LinearLayout(ctx);
		grid.setOrientation(LinearLayout.VERTICAL);
		final TextInputLayout tilL = field(ctx, "left", true, allowNegative);
		final TextInputLayout tilR = field(ctx, "right", true, allowNegative);
		final TextInputLayout tilT = field(ctx, "top", true, allowNegative);
		final TextInputLayout tilB = field(ctx, "bottom", true, allowNegative);
		tilL.getEditText().setText(String.valueOf(l));
		tilR.getEditText().setText(String.valueOf(r));
		tilT.getEditText().setText(String.valueOf(t));
		tilB.getEditText().setText(String.valueOf(b));
		grid.addView(row(ctx, d, tilL, tilR));
		grid.addView(row(ctx, d, tilT, tilB));
		root.addView(grid, lp(d, 0, 12));

		// ---- checkbox
		final MaterialCheckBox cbAll = new MaterialCheckBox(ctx);
		cbAll.setText(kind + " on all sides");
		cbAll.setChecked(allSides);
		root.addView(cbAll, lp(d, 16, 0));

		final View[] views = {tilAll, grid};
		applyMode(allSides, views);
		cbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override public void onCheckedChanged(CompoundButton v, boolean checked) {
				if (checked) {
					String lv = text(tilL);
					etAll.setText(lv.equals("0") ? "" : lv);
				} else {
					String av = text(tilAll);
					String s = av.isEmpty() ? "0" : av;
					tilL.getEditText().setText(s); tilR.getEditText().setText(s);
					tilT.getEditText().setText(s); tilB.getEditText().setText(s);
				}
				applyMode(checked, views);
			}
		});

		final TextInputLayout[] four = {tilL, tilR, tilT, tilB};
		for (final TextInputLayout til : four) {
			til.getEditText().addTextChangedListener(new TextWatcher() {
				@Override public void beforeTextChanged(CharSequence s, int a, int c, int e) {}
				@Override public void onTextChanged(CharSequence s, int a, int c, int e) {}
				@Override public void afterTextChanged(Editable s) { validate(til, min, max, rangeMsg, false); }
			});
		}
		etAll.addTextChangedListener(new TextWatcher() {
			@Override public void beforeTextChanged(CharSequence s, int a, int c, int e) {}
			@Override public void onTextChanged(CharSequence s, int a, int c, int e) {}
			@Override public void afterTextChanged(Editable s) { validate(tilAll, min, max, rangeMsg, true); }
		});

		MaterialAlertDialogBuilder bd = new MaterialAlertDialogBuilder(ctx);
		if (icon != null) bd.setIcon(icon);
		bd.setTitle(kind);
		bd.setView(root);
		bd.setNegativeButton("Cancel", null);
		bd.setPositiveButton("Save", null); // overridden below so invalid input keeps dialog open
		final AlertDialog dlg = bd.create();
		dlg.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override public void onShow(DialogInterface di) {
				dlg.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
					@Override public void onClick(View v) {
						if (cbAll.isChecked()) {
							if (!validate(tilAll, min, max, rangeMsg, true)) return;
							int val = parse(text(tilAll));
							cb.onSave(true, val, val, val, val);
						} else {
							boolean ok = true;
							for (TextInputLayout til : four) ok &= validate(til, min, max, rangeMsg, false);
							if (!ok) return;
							cb.onSave(false, parse(text(tilL)), parse(text(tilT)),
									parse(text(tilR)), parse(text(tilB)));
						}
						dlg.dismiss();
					}
				});
			}
		});
		dlg.show();
	}

	// ------------------------------------------------------------------ helpers

	private static void applyMode(boolean all, View[] v) {
		v[0].setVisibility(all ? View.VISIBLE : View.GONE);
		v[1].setVisibility(all ? View.GONE : View.VISIBLE);
	}

	private static TextInputLayout field(Context ctx, String hint, boolean dpSuffix, boolean neg) {
		TextInputLayout til = new TextInputLayout(ctx, null,
				com.google.android.material.R.attr.textInputOutlinedStyle);
		til.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE);
		til.setHint(hint);
		if (dpSuffix) til.setSuffixText("dp");
		TextInputEditText et = new TextInputEditText(til.getContext());
		int type = InputType.TYPE_CLASS_NUMBER;
		if (neg) type |= InputType.TYPE_NUMBER_FLAG_SIGNED;
		et.setInputType(type);
		et.setSingleLine(true);
		til.addView(et, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		return til;
	}

	private static LinearLayout row(Context ctx, float d, TextInputLayout a, TextInputLayout b) {
		LinearLayout row = new LinearLayout(ctx);
		row.setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout.LayoutParams la = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
		la.rightMargin = (int) (6 * d);
		LinearLayout.LayoutParams lb = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
		lb.leftMargin = (int) (6 * d);
		lb.bottomMargin = 0;
		row.addView(a, la);
		row.addView(b, lb);
		LinearLayout.LayoutParams rl = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		rl.bottomMargin = (int) (8 * d);
		row.setLayoutParams(rl);
		return row;
	}

	private static LinearLayout.LayoutParams lp(float d, int topDp, int bottomDp) {
		LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		p.topMargin = (int) (topDp * d);
		p.bottomMargin = (int) (bottomDp * d);
		return p;
	}

	private static String text(TextInputLayout til) {
		CharSequence s = til.getEditText().getText();
		return s == null ? "" : s.toString().trim();
	}

	private static int parse(String s) {
		try { return Integer.parseInt(s.trim()); } catch (Exception e) { return 0; }
	}

	/** allowEmpty: the single "all sides" field may be empty (= 0). */
	private static boolean validate(TextInputLayout til, int min, int max, String msg, boolean allowEmpty) {
		String s = text(til);
		boolean ok;
		if (s.isEmpty()) {
			ok = allowEmpty;
		} else {
			try {
				int v = Integer.parseInt(s);
				ok = v >= min && v <= max;
			} catch (NumberFormatException e) {
				ok = false;
			}
		}
		til.setError(ok ? null : msg);
		return ok;
	}
}
