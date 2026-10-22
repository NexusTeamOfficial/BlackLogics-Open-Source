package com.nexusteam.blacklogics.sdk.layouteditor;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.nexusteam.blacklogics.sdk.utils.DimensionUtils;

/**
 * Preview support for attributes that have no entry in BuiltInAttributes / attributes.json.
 * Before this, such attributes were stored + saved to XML but never shown in the editor canvas.
 * Called from AttributeApplier (not-found branch + @drawable references).
 */
final class ExtraAttributeApplier {

	private final Context ctx;
	private final AttributeApplier owner;

	ExtraAttributeApplier(Context ctx, AttributeApplier owner) {
		this.ctx = ctx;
		this.owner = owner;
	}

	// ---------------------------------------------------------------- drawable references

	/** android:background / foreground / src / drawableX / app:icon ... = "@drawable/x". */
	boolean applyDrawableRef(View v, String name, String value) {
		if (v == null || name == null || value == null) return false;
		String v0 = value.trim();
		if (!(v0.startsWith("@drawable/") || v0.startsWith("@mipmap/"))) return false;
		String key = key(name);
		boolean known = key.equals("background") || key.equals("foreground") || key.equals("src")
				|| key.equals("srcCompat") || key.equals("icon") || key.equals("button")
				|| key.startsWith("drawable");
		if (!known) return false;

		Drawable d = DrawableResolver.load(ctx, DrawableResolver.currentScId(ctx), v0);
		if (d == null) return false;
		try {
			if (key.equals("background")) {
				v.setBackground(d);
			} else if (key.equals("foreground")) {
				v.setForeground(d);
			} else if (key.equals("src") || key.equals("srcCompat")) {
				if (v instanceof ImageView) ((ImageView) v).setImageDrawable(d); else return false;
			} else if (key.equals("icon")) {
				if (v instanceof MaterialButton) ((MaterialButton) v).setIcon(d); else return false;
			} else if (key.equals("button")) {
				if (v instanceof android.widget.CompoundButton)
					((android.widget.CompoundButton) v).setButtonDrawable(d);
				else return false;
			} else if (v instanceof TextView) {
				TextView tv = (TextView) v;
				Drawable[] c = tv.getCompoundDrawablesRelative();
				if (key.equals("drawableLeft") || key.equals("drawableStart")) c[0] = d;
				else if (key.equals("drawableTop")) c[1] = d;
				else if (key.equals("drawableRight") || key.equals("drawableEnd")) c[2] = d;
				else if (key.equals("drawableBottom")) c[3] = d;
				else return false;
				tv.setCompoundDrawablesRelativeWithIntrinsicBounds(c[0], c[1], c[2], c[3]);
			} else {
				return false;
			}
			v.requestLayout();
			return true;
		} catch (Throwable t) {
			Log.w("ExtraAttrApplier", "drawable ref failed " + name + "=" + value, t);
			return false;
		}
	}

	// ---------------------------------------------------------------- generic fallback

	/** Returns true if the attribute was handled (applied or deliberately ignored). */
	boolean apply(View v, String name, String value) {
		if (v == null || name == null || value == null) return false;
		value = value.trim();
		if (value.isEmpty()) return false;
		String k = key(name);
		try {
			// ---- any view
			switch (k) {
				case "alpha": v.setAlpha(f(value, 1f)); return true;
				case "elevation": v.setElevation(px(value)); return true;
				case "rotation": v.setRotation(f(value, 0f)); return true;
				case "rotationX": v.setRotationX(f(value, 0f)); return true;
				case "rotationY": v.setRotationY(f(value, 0f)); return true;
				case "scaleX": v.setScaleX(f(value, 1f)); return true;
				case "scaleY": v.setScaleY(f(value, 1f)); return true;
				case "translationX": v.setTranslationX(px(value)); return true;
				case "translationY": v.setTranslationY(px(value)); return true;
				case "translationZ": v.setTranslationZ(px(value)); return true;
				case "minWidth": v.setMinimumWidth((int) px(value)); return true;
				case "minHeight": v.setMinimumHeight((int) px(value)); return true;
				case "contentDescription": v.setContentDescription(value); return true;
				case "background":
					if (value.startsWith("@color/") || value.startsWith("#")) {
						v.setBackgroundColor(owner.parseColorValue(value));
						return true;
					}
					return false;
				case "backgroundTint": {
					ColorStateList c = csl(value);
					if (c != null) { ViewCompat.setBackgroundTintList(v, c); return true; }
					return false;
				}
				case "clipChildren":
					if (v instanceof ViewGroup) { ((ViewGroup) v).setClipChildren("true".equals(value)); return true; }
					return false;
				case "clipToPadding":
					if (v instanceof ViewGroup) { ((ViewGroup) v).setClipToPadding("true".equals(value)); return true; }
					return false;
				case "layout_gravity": return layoutGravity(v, value);
				case "gravity": return gravity(v, value);
				case "weightSum":
					if (v instanceof LinearLayout) { ((LinearLayout) v).setWeightSum(f(value, 0f)); return true; }
					return false;
				case "orientation":
					if (v instanceof LinearLayout) {
						((LinearLayout) v).setOrientation("horizontal".equals(value)
								? LinearLayout.HORIZONTAL : LinearLayout.VERTICAL);
						return true;
					}
					return false;
				default: break;
			}

			// ---- TextView
			if (v instanceof TextView) {
				TextView tv = (TextView) v;
				switch (k) {
					case "textAllCaps": tv.setAllCaps("true".equals(value)); return true;
					case "fontFamily":
						if (!value.startsWith("@")) {
							Typeface cur = tv.getTypeface();
							tv.setTypeface(Typeface.create(value, cur != null ? cur.getStyle() : Typeface.NORMAL));
						}
						return true;
					case "letterSpacing": tv.setLetterSpacing(f(value, 0f)); return true;
					case "lineSpacingExtra": tv.setLineSpacing(px(value), tv.getLineSpacingMultiplier()); return true;
					case "lineSpacingMultiplier": tv.setLineSpacing(tv.getLineSpacingExtra(), f(value, 1f)); return true;
					case "maxLines": tv.setMaxLines((int) f(value, Integer.MAX_VALUE)); return true;
					case "minLines": tv.setMinLines((int) f(value, 0)); return true;
					case "lines": tv.setLines((int) f(value, 1)); return true;
					case "singleLine": tv.setSingleLine("true".equals(value)); return true;
					case "ellipsize":
						if ("end".equals(value)) tv.setEllipsize(TextUtils.TruncateAt.END);
						else if ("start".equals(value)) tv.setEllipsize(TextUtils.TruncateAt.START);
						else if ("middle".equals(value)) tv.setEllipsize(TextUtils.TruncateAt.MIDDLE);
						else if ("marquee".equals(value)) tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
						else tv.setEllipsize(null);
						return true;
					case "textColorHint": tv.setHintTextColor(owner.parseColorValue(value)); return true;
					case "includeFontPadding": tv.setIncludeFontPadding("true".equals(value)); return true;
					case "drawablePadding": tv.setCompoundDrawablePadding((int) px(value)); return true;
					default: break;
				}
			}

			// ---- ImageView
			if (v instanceof ImageView) {
				ImageView iv = (ImageView) v;
				switch (k) {
					case "scaleType": {
						ImageView.ScaleType st = scaleType(value);
						if (st != null) iv.setScaleType(st);
						return true;
					}
					case "tint": {
						ColorStateList c = csl(value);
						if (c != null) iv.setImageTintList(c);
						return true;
					}
					case "adjustViewBounds": iv.setAdjustViewBounds("true".equals(value)); return true;
					case "maxWidth": iv.setMaxWidth((int) px(value)); return true;
					case "maxHeight": iv.setMaxHeight((int) px(value)); return true;
					default: break;
				}
			}

			// ---- CardView / MaterialCardView
			if (v instanceof CardView) {
				CardView cv = (CardView) v;
				switch (k) {
					case "cardBackgroundColor": cv.setCardBackgroundColor(owner.parseColorValue(value)); return true;
					case "cardCornerRadius": cv.setRadius(px(value)); return true;
					case "cardElevation": cv.setCardElevation(px(value)); return true;
					case "cardMaxElevation": cv.setMaxCardElevation(px(value)); return true;
					case "cardUseCompatPadding": cv.setUseCompatPadding("true".equals(value)); return true;
					case "cardPreventCornerOverlap": cv.setPreventCornerOverlap("true".equals(value)); return true;
					case "contentPadding": {
						int p = (int) px(value);
						cv.setContentPadding(p, p, p, p);
						return true;
					}
					default: break;
				}
				if (v instanceof MaterialCardView) {
					MaterialCardView mc = (MaterialCardView) v;
					if (k.equals("strokeColor")) { mc.setStrokeColor(owner.parseColorValue(value)); return true; }
					if (k.equals("strokeWidth")) { mc.setStrokeWidth((int) px(value)); return true; }
				}
			}

			// ---- MaterialButton
			if (v instanceof MaterialButton) {
				MaterialButton mb = (MaterialButton) v;
				switch (k) {
					case "cornerRadius": mb.setCornerRadius((int) px(value)); return true;
					case "strokeColor": {
						ColorStateList c = csl(value);
						if (c != null) mb.setStrokeColor(c);
						return true;
					}
					case "strokeWidth": mb.setStrokeWidth((int) px(value)); return true;
					case "iconTint": {
						ColorStateList c = csl(value);
						if (c != null) mb.setIconTint(c);
						return true;
					}
					case "iconPadding": mb.setIconPadding((int) px(value)); return true;
					case "iconSize": mb.setIconSize((int) px(value)); return true;
					case "insetTop": mb.setInsetTop((int) px(value)); return true;
					case "insetBottom": mb.setInsetBottom((int) px(value)); return true;
					default: break;
				}
			}
		} catch (Throwable t) {
			Log.w("ExtraAttrApplier", "apply failed " + name + "=" + value + " : " + t);
		}
		return false;
	}

	// ---------------------------------------------------------------- helpers

	private static String key(String name) {
		if (name.startsWith("android:")) return name.substring(8);
		if (name.startsWith("app:")) return name.substring(4);
		return name;
	}

	private float f(String v, float def) {
		try { return Float.parseFloat(v.trim()); } catch (Exception e) { return def; }
	}

	private float px(String v) {
		String t = v.trim();
		if (t.isEmpty()) return 0f;
		if (t.matches("-?\\d+(\\.\\d+)?")) t = t + "dp";
		try { return DimensionUtils.getValueInPx(ctx, t); } catch (Throwable e) { return 0f; }
	}

	private ColorStateList csl(String v) {
		if (v.startsWith("@color/") || v.startsWith("#")) {
			return ColorStateList.valueOf(owner.parseColorValue(v));
		}
		return null;
	}

	private static ImageView.ScaleType scaleType(String v) {
		switch (v) {
			case "center": return ImageView.ScaleType.CENTER;
			case "centerCrop": return ImageView.ScaleType.CENTER_CROP;
			case "centerInside": return ImageView.ScaleType.CENTER_INSIDE;
			case "fitCenter": return ImageView.ScaleType.FIT_CENTER;
			case "fitStart": return ImageView.ScaleType.FIT_START;
			case "fitEnd": return ImageView.ScaleType.FIT_END;
			case "fitXY": return ImageView.ScaleType.FIT_XY;
			case "matrix": return ImageView.ScaleType.MATRIX;
			default: return null;
		}
	}

	static int parseGravity(String v) {
		int g = 0;
		for (String s : v.split("\\|")) {
			switch (s.trim()) {
				case "top": g |= Gravity.TOP; break;
				case "bottom": g |= Gravity.BOTTOM; break;
				case "left": g |= Gravity.LEFT; break;
				case "right": g |= Gravity.RIGHT; break;
				case "start": g |= Gravity.START; break;
				case "end": g |= Gravity.END; break;
				case "center": g |= Gravity.CENTER; break;
				case "center_vertical": g |= Gravity.CENTER_VERTICAL; break;
				case "center_horizontal": g |= Gravity.CENTER_HORIZONTAL; break;
				case "fill": g |= Gravity.FILL; break;
				case "fill_vertical": g |= Gravity.FILL_VERTICAL; break;
				case "fill_horizontal": g |= Gravity.FILL_HORIZONTAL; break;
				default: break;
			}
		}
		return g;
	}

	private boolean gravity(View v, String value) {
		int g = parseGravity(value);
		if (v instanceof TextView) { ((TextView) v).setGravity(g); return true; }
		if (v instanceof LinearLayout) { ((LinearLayout) v).setGravity(g); return true; }
		if (v instanceof RelativeLayout) { ((RelativeLayout) v).setGravity(g); return true; }
		return false;
	}

	private boolean layoutGravity(View v, String value) {
		ViewGroup.LayoutParams lp = v.getLayoutParams();
		int g = parseGravity(value);
		if (lp instanceof LinearLayout.LayoutParams) {
			((LinearLayout.LayoutParams) lp).gravity = g;
		} else if (lp instanceof FrameLayout.LayoutParams) {
			((FrameLayout.LayoutParams) lp).gravity = g;
		} else {
			return false;
		}
		v.setLayoutParams(lp);
		return true;
	}
}
