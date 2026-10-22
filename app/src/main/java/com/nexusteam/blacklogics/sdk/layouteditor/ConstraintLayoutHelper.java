package com.nexusteam.blacklogics.sdk.layouteditor;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * Applies app:layout_constraint* attributes to ConstraintLayout.LayoutParams
 * so ConstraintLayout children are positioned in the ViewEditor preview
 * instead of all stacking at (0,0).
 *
 * Needs: androidx.constraintlayout:constraintlayout in the editor app's deps.
 */
final class ConstraintLayoutHelper {

	private ConstraintLayoutHelper() {}

	static boolean isConstraintChild(View v) {
		return v != null && v.getParent() instanceof ConstraintLayout;
	}

	/** Returns ConstraintLayout.LayoutParams for the view, converting + keeping margins if needed. */
	static ConstraintLayout.LayoutParams ensureParams(View view) {
		ViewGroup.LayoutParams old = view.getLayoutParams();
		if (old instanceof ConstraintLayout.LayoutParams) {
			return (ConstraintLayout.LayoutParams) old;
		}
		int w = old != null ? old.width : ViewGroup.LayoutParams.WRAP_CONTENT;
		int h = old != null ? old.height : ViewGroup.LayoutParams.WRAP_CONTENT;
		ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(w, h);
		if (old instanceof ViewGroup.MarginLayoutParams) {
			ViewGroup.MarginLayoutParams m = (ViewGroup.MarginLayoutParams) old;
			lp.leftMargin = m.leftMargin;
			lp.topMargin = m.topMargin;
			lp.rightMargin = m.rightMargin;
			lp.bottomMargin = m.bottomMargin;
		}
		return lp;
	}

	/** Handles one attribute. Returns true if it was a constraint attribute (handled). */
	static boolean applyOne(View view, String name, String value) {
		if (view == null || name == null || value == null) return false;
		String key = name.startsWith("app:") ? name.substring(4) : name;
		boolean isConstraint = key.startsWith("layout_constraint")
				|| key.equals("layout_constrainedWidth")
				|| key.equals("layout_constrainedHeight");
		if (!isConstraint) return false;
		if (!isConstraintChild(view)) return true; // handled (stored only), nothing to apply

		value = value.trim();
		try {
			ConstraintLayout.LayoutParams lp = ensureParams(view);

			if (key.equals("layout_constrainedWidth")) {
				lp.constrainedWidth = "true".equals(value);
			} else if (key.equals("layout_constrainedHeight")) {
				lp.constrainedHeight = "true".equals(value);
			} else {
				String k = key.substring("layout_constraint".length());
				switch (k) {
					case "Left_toLeftOf":       lp.leftToLeft = ref(value); break;
					case "Left_toRightOf":      lp.leftToRight = ref(value); break;
					case "Right_toLeftOf":      lp.rightToLeft = ref(value); break;
					case "Right_toRightOf":     lp.rightToRight = ref(value); break;
					case "Top_toTopOf":         lp.topToTop = ref(value); break;
					case "Top_toBottomOf":      lp.topToBottom = ref(value); break;
					case "Bottom_toTopOf":      lp.bottomToTop = ref(value); break;
					case "Bottom_toBottomOf":   lp.bottomToBottom = ref(value); break;
					case "Baseline_toBaselineOf": lp.baselineToBaseline = ref(value); break;
					case "Start_toStartOf":     lp.startToStart = ref(value); break;
					case "Start_toEndOf":       lp.startToEnd = ref(value); break;
					case "End_toStartOf":       lp.endToStart = ref(value); break;
					case "End_toEndOf":         lp.endToEnd = ref(value); break;
					case "Horizontal_bias":     lp.horizontalBias = f(value, 0.5f); break;
					case "Vertical_bias":       lp.verticalBias = f(value, 0.5f); break;
					case "Horizontal_weight":   lp.horizontalWeight = f(value, 0f); break;
					case "Vertical_weight":     lp.verticalWeight = f(value, 0f); break;
					case "Horizontal_chainStyle": lp.horizontalChainStyle = chain(value); break;
					case "Vertical_chainStyle": lp.verticalChainStyle = chain(value); break;
					case "DimensionRatio":      lp.dimensionRatio = value; break;
					default: break; // unsupported in preview, still kept in attribute store / XML
				}
			}
			try { lp.validate(); } catch (Throwable ignore) {}
			view.setLayoutParams(lp);
			view.requestLayout();
		} catch (Throwable t) {
			Log.w("ConstraintHelper", "apply failed: " + name + "=" + value, t);
		}
		return true;
	}

	private static int ref(String v) {
		if ("parent".equals(v)) return ConstraintLayout.LayoutParams.PARENT_ID;
		String n = v;
		if (n.startsWith("@+id/")) n = n.substring(5);
		else if (n.startsWith("@id/")) n = n.substring(4);
		int id = ViewEditor.idManager.getId(n);
		return id != 0 ? id : ConstraintLayout.LayoutParams.UNSET;
	}

	private static float f(String v, float def) {
		try { return Float.parseFloat(v); } catch (Exception e) { return def; }
	}

	private static int chain(String v) {
		if ("packed".equals(v)) return ConstraintLayout.LayoutParams.CHAIN_PACKED;
		if ("spread_inside".equals(v)) return ConstraintLayout.LayoutParams.CHAIN_SPREAD_INSIDE;
		return ConstraintLayout.LayoutParams.CHAIN_SPREAD;
	}
}
