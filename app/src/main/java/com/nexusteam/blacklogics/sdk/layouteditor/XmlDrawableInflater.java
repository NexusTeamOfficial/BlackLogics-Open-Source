package com.nexusteam.blacklogics.sdk.layouteditor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;

import androidx.core.graphics.PathParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Preview-only inflater for plain-text drawable XML (the project's .xml files are NOT compiled,
 * so Drawable.createFromXml cannot be used). Supports shape, selector (default item),
 * layer-list, ripple (content), inset, bitmap, color and a simple vector renderer.
 */
final class XmlDrawableInflater {

	private XmlDrawableInflater() {}

	static Drawable inflate(Context ctx, String scId, File f, int depth) {
		if (depth > 6) return null;
		FileInputStream in = null;
		try {
			in = new FileInputStream(f);
			XmlPullParser p = XmlPullParserFactory.newInstance().newPullParser();
			p.setInput(new InputStreamReader(in, "UTF-8"));
			int ev;
			while ((ev = p.next()) != XmlPullParser.START_TAG && ev != XmlPullParser.END_DOCUMENT) { }
			if (ev == XmlPullParser.END_DOCUMENT) return null;
			return new Inflater(ctx, scId, depth).element(p);
		} catch (Throwable t) {
			Log.w("XmlDrawableInflater", "inflate failed: " + f + " : " + t);
			return null;
		} finally {
			try { if (in != null) in.close(); } catch (Throwable ignore) {}
		}
	}

	private static final class Inflater {
		final Context ctx;
		final String scId;
		final int depth;
		final float density;

		Inflater(Context c, String sc, int d) {
			ctx = c; scId = sc; depth = d;
			density = c.getResources().getDisplayMetrics().density;
		}

		// ---- helpers --------------------------------------------------------------------

		String a(XmlPullParser p, String n) {
			String v = p.getAttributeValue(null, "android:" + n);
			if (v == null) v = p.getAttributeValue(null, n);
			return v;
		}

		boolean hasStateAttr(XmlPullParser p) {
			for (int i = 0; i < p.getAttributeCount(); i++) {
				String n = p.getAttributeName(i);
				if (n != null && n.startsWith("android:state_")) return true;
			}
			return false;
		}

		void skip(XmlPullParser p) throws Exception {
			int d = 1;
			while (d > 0) {
				int e = p.next();
				if (e == XmlPullParser.START_TAG) d++;
				else if (e == XmlPullParser.END_TAG) d--;
				else if (e == XmlPullParser.END_DOCUMENT) return;
			}
		}

		int color(String v) {
			if (v == null) return Color.TRANSPARENT;
			v = v.trim();
			if (v.startsWith("@color/")) return ColorResourceReader.read(scId, v.substring(7));
			if (v.startsWith("@android:color/")) {
				String n = v.substring(15);
				if ("white".equals(n)) return Color.WHITE;
				if ("black".equals(n)) return Color.BLACK;
				return Color.TRANSPARENT;
			}
			if (v.startsWith("#")) {
				try {
					if (v.length() == 4 || v.length() == 5) { // #RGB / #ARGB
						StringBuilder sb = new StringBuilder("#");
						for (int i = 1; i < v.length(); i++) sb.append(v.charAt(i)).append(v.charAt(i));
						v = sb.toString();
					}
					return Color.parseColor(v);
				} catch (Exception e) { return Color.TRANSPARENT; }
			}
			return Color.TRANSPARENT;
		}

		float px(String v) {
			if (v == null) return 0f;
			v = v.trim();
			try {
				if (v.endsWith("dp")) return Float.parseFloat(v.substring(0, v.length() - 2)) * density;
				if (v.endsWith("dip")) return Float.parseFloat(v.substring(0, v.length() - 3)) * density;
				if (v.endsWith("sp")) return Float.parseFloat(v.substring(0, v.length() - 2)) * density;
				if (v.endsWith("px")) return Float.parseFloat(v.substring(0, v.length() - 2));
				return Float.parseFloat(v);
			} catch (Exception e) { return 0f; }
		}

		float num(String v, float def) {
			try { return v == null ? def : Float.parseFloat(v.trim()); } catch (Exception e) { return def; }
		}

		Drawable ref(String v) {
			if (v == null) return null;
			v = v.trim();
			try {
				if (v.startsWith("@drawable/") || v.startsWith("@mipmap/")) {
					String name = DrawableResolver.nameFromRef(v);
					java.io.File f = DrawableResolver.find(scId, name);
					if (f != null) return DrawableResolver.loadFile(ctx, scId, f, depth + 1);
					int id = ctx.getResources().getIdentifier(name, "drawable", ctx.getPackageName());
					return id != 0 ? ctx.getResources().getDrawable(id, ctx.getTheme()) : null;
				}
				if (v.startsWith("@android:drawable/")) {
					int id = ctx.getResources().getIdentifier(v.substring(18), "drawable", "android");
					return id != 0 ? ctx.getResources().getDrawable(id, ctx.getTheme()) : null;
				}
				if (v.startsWith("@color/") || v.startsWith("#")) return new ColorDrawable(color(v));
			} catch (Throwable ignore) {}
			return null;
		}

		// ---- elements: entered at START_TAG, returns with parser at matching END_TAG -----------

		Drawable element(XmlPullParser p) throws Exception {
			String n = p.getName();
			if ("shape".equals(n)) return shape(p);
			if ("selector".equals(n)) return selector(p);
			if ("layer-list".equals(n)) return layerList(p);
			if ("ripple".equals(n)) return ripple(p);
			if ("inset".equals(n)) return inset(p);
			if ("bitmap".equals(n)) { Drawable d = ref(a(p, "src")); skip(p); return d; }
			if ("color".equals(n)) { Drawable d = new ColorDrawable(color(a(p, "color"))); skip(p); return d; }
			if ("vector".equals(n)) return vector(p);
			if ("level-list".equals(n) || "transition".equals(n)) return selector(p);
			if ("rotate".equals(n) || "scale".equals(n) || "clip".equals(n)) {
				Drawable d = ref(a(p, "drawable"));
				while (true) {
					int ev = p.next();
					if (ev == XmlPullParser.END_TAG || ev == XmlPullParser.END_DOCUMENT) break;
					if (ev == XmlPullParser.START_TAG) { Drawable c = element(p); if (d == null) d = c; }
				}
				return d;
			}
			skip(p);
			return null;
		}

		Drawable shape(XmlPullParser p) throws Exception {
			GradientDrawable g = new GradientDrawable();
			String t = a(p, "shape");
			if ("oval".equals(t)) g.setShape(GradientDrawable.OVAL);
			else if ("line".equals(t)) g.setShape(GradientDrawable.LINE);
			else if ("ring".equals(t)) g.setShape(GradientDrawable.RING);
			else g.setShape(GradientDrawable.RECTANGLE);
			while (true) {
				int ev = p.next();
				if (ev == XmlPullParser.END_TAG || ev == XmlPullParser.END_DOCUMENT) break;
				if (ev != XmlPullParser.START_TAG) continue;
				String n = p.getName();
				if ("solid".equals(n)) {
					g.setColor(color(a(p, "color")));
				} else if ("gradient".equals(n)) {
					gradient(g, p);
				} else if ("corners".equals(n)) {
					corners(g, p);
				} else if ("stroke".equals(n)) {
					int w = Math.round(px(a(p, "width")));
					int c = color(a(p, "color"));
					float dw = px(a(p, "dashWidth")), dg = px(a(p, "dashGap"));
					if (dw > 0) g.setStroke(w, c, dw, dg); else g.setStroke(w, c);
				} else if ("size".equals(n)) {
					g.setSize(Math.round(px(a(p, "width"))), Math.round(px(a(p, "height"))));
				}
				skip(p);
			}
			return g;
		}

		void gradient(GradientDrawable g, XmlPullParser p) {
			int s = color(a(p, "startColor")), e = color(a(p, "endColor"));
			if (a(p, "centerColor") != null) g.setColors(new int[] {s, color(a(p, "centerColor")), e});
			else g.setColors(new int[] {s, e});
			String type = a(p, "type");
			if ("radial".equals(type)) {
				g.setGradientType(GradientDrawable.RADIAL_GRADIENT);
				float r = px(a(p, "gradientRadius"));
				g.setGradientRadius(r > 0 ? r : 150 * density);
			} else if ("sweep".equals(type)) {
				g.setGradientType(GradientDrawable.SWEEP_GRADIENT);
			} else {
				g.setGradientType(GradientDrawable.LINEAR_GRADIENT);
				int ang = Math.round(num(a(p, "angle"), 0f));
				ang = ((ang % 360) + 360) % 360;
				GradientDrawable.Orientation o;
				switch (ang / 45) {
					case 1: o = GradientDrawable.Orientation.BL_TR; break;
					case 2: o = GradientDrawable.Orientation.BOTTOM_TOP; break;
					case 3: o = GradientDrawable.Orientation.BR_TL; break;
					case 4: o = GradientDrawable.Orientation.RIGHT_LEFT; break;
					case 5: o = GradientDrawable.Orientation.TR_BL; break;
					case 6: o = GradientDrawable.Orientation.TOP_BOTTOM; break;
					case 7: o = GradientDrawable.Orientation.TL_BR; break;
					default: o = GradientDrawable.Orientation.LEFT_RIGHT;
				}
				g.setOrientation(o);
			}
		}

		void corners(GradientDrawable g, XmlPullParser p) {
			float r = px(a(p, "radius"));
			boolean indiv = a(p, "topLeftRadius") != null || a(p, "topRightRadius") != null
					|| a(p, "bottomLeftRadius") != null || a(p, "bottomRightRadius") != null;
			if (indiv) {
				float tl = a(p, "topLeftRadius") != null ? px(a(p, "topLeftRadius")) : r;
				float tr = a(p, "topRightRadius") != null ? px(a(p, "topRightRadius")) : r;
				float br = a(p, "bottomRightRadius") != null ? px(a(p, "bottomRightRadius")) : r;
				float bl = a(p, "bottomLeftRadius") != null ? px(a(p, "bottomLeftRadius")) : r;
				g.setCornerRadii(new float[] {tl, tl, tr, tr, br, br, bl, bl});
			} else {
				g.setCornerRadius(r);
			}
		}

		Drawable itemDrawable(XmlPullParser p) throws Exception { // p at <item>
			Drawable d = ref(a(p, "drawable"));
			while (true) {
				int ev = p.next();
				if (ev == XmlPullParser.END_TAG || ev == XmlPullParser.END_DOCUMENT) break;
				if (ev == XmlPullParser.START_TAG) { Drawable c = element(p); if (d == null) d = c; }
			}
			return d;
		}

		/** Picks the default-state item (first one without android:state_*), else the first. */
		Drawable selector(XmlPullParser p) throws Exception {
			Drawable def = null, first = null;
			while (true) {
				int ev = p.next();
				if (ev == XmlPullParser.END_TAG || ev == XmlPullParser.END_DOCUMENT) break;
				if (ev != XmlPullParser.START_TAG) continue;
				if ("item".equals(p.getName())) {
					boolean st = hasStateAttr(p);
					Drawable d = itemDrawable(p);
					if (first == null) first = d;
					if (!st && def == null) def = d;
				} else {
					skip(p);
				}
			}
			return def != null ? def : first;
		}

		Drawable layerList(XmlPullParser p) throws Exception {
			ArrayList<Drawable> ds = new ArrayList<Drawable>();
			ArrayList<int[]> insets = new ArrayList<int[]>();
			ArrayList<int[]> sizes = new ArrayList<int[]>();
			while (true) {
				int ev = p.next();
				if (ev == XmlPullParser.END_TAG || ev == XmlPullParser.END_DOCUMENT) break;
				if (ev != XmlPullParser.START_TAG) continue;
				if ("item".equals(p.getName())) {
					int[] ins = {Math.round(px(a(p, "left"))), Math.round(px(a(p, "top"))),
							Math.round(px(a(p, "right"))), Math.round(px(a(p, "bottom")))};
					int[] sz = {a(p, "width") != null ? Math.round(px(a(p, "width"))) : -1,
							a(p, "height") != null ? Math.round(px(a(p, "height"))) : -1};
					Drawable d = itemDrawable(p);
					ds.add(d != null ? d : new ColorDrawable(Color.TRANSPARENT));
					insets.add(ins);
					sizes.add(sz);
				} else {
					skip(p);
				}
			}
			if (ds.isEmpty()) return null;
			LayerDrawable ld = new LayerDrawable(ds.toArray(new Drawable[0]));
			for (int i = 0; i < ds.size(); i++) {
				int[] n = insets.get(i);
				ld.setLayerInset(i, n[0], n[1], n[2], n[3]);
				int[] s = sizes.get(i);
				if (s[0] >= 0 || s[1] >= 0) ld.setLayerSize(i, s[0], s[1]);
			}
			return ld;
		}

		Drawable ripple(XmlPullParser p) throws Exception {
			Drawable content = null;
			while (true) {
				int ev = p.next();
				if (ev == XmlPullParser.END_TAG || ev == XmlPullParser.END_DOCUMENT) break;
				if (ev != XmlPullParser.START_TAG) continue;
				if ("item".equals(p.getName())) {
					boolean mask = "@android:id/mask".equals(a(p, "id"));
					Drawable d = itemDrawable(p);
					if (!mask && content == null) content = d;
				} else {
					skip(p);
				}
			}
			return content != null ? content : new ColorDrawable(Color.TRANSPARENT);
		}

		Drawable inset(XmlPullParser p) throws Exception {
			int all = Math.round(px(a(p, "inset")));
			int l = a(p, "insetLeft") != null ? Math.round(px(a(p, "insetLeft"))) : all;
			int t = a(p, "insetTop") != null ? Math.round(px(a(p, "insetTop"))) : all;
			int r = a(p, "insetRight") != null ? Math.round(px(a(p, "insetRight"))) : all;
			int b = a(p, "insetBottom") != null ? Math.round(px(a(p, "insetBottom"))) : all;
			Drawable d = ref(a(p, "drawable"));
			while (true) {
				int ev = p.next();
				if (ev == XmlPullParser.END_TAG || ev == XmlPullParser.END_DOCUMENT) break;
				if (ev == XmlPullParser.START_TAG) { Drawable c = element(p); if (d == null) d = c; }
			}
			return d == null ? null : new InsetDrawable(d, l, t, r, b);
		}

		// ---- vector ---------------------------------------------------------------------------

		Drawable vector(XmlPullParser p) throws Exception {
			VectorLite v = new VectorLite();
			v.vw = num(a(p, "viewportWidth"), 24f);
			v.vh = num(a(p, "viewportHeight"), 24f);
			v.iw = Math.max(1, Math.round(px(a(p, "width")) > 0 ? px(a(p, "width")) : 24 * density));
			v.ih = Math.max(1, Math.round(px(a(p, "height")) > 0 ? px(a(p, "height")) : 24 * density));
			int tint = color(a(p, "tint"));
			if (a(p, "tint") != null && tint != Color.TRANSPARENT) v.tint = tint;
			v.rootAlpha = num(a(p, "alpha"), 1f);
			vectorChildren(p, new Matrix(), v);
			return v;
		}

		void vectorChildren(XmlPullParser p, Matrix parent, VectorLite v) throws Exception {
			while (true) {
				int ev = p.next();
				if (ev == XmlPullParser.END_TAG || ev == XmlPullParser.END_DOCUMENT) break;
				if (ev != XmlPullParser.START_TAG) continue;
				String n = p.getName();
				if ("group".equals(n)) {
					float px0 = num(a(p, "pivotX"), 0f), py0 = num(a(p, "pivotY"), 0f);
					Matrix local = new Matrix();
					local.postTranslate(-px0, -py0);
					local.postScale(num(a(p, "scaleX"), 1f), num(a(p, "scaleY"), 1f));
					local.postRotate(num(a(p, "rotation"), 0f));
					local.postTranslate(px0 + num(a(p, "translateX"), 0f), py0 + num(a(p, "translateY"), 0f));
					Matrix total = new Matrix(parent);
					total.preConcat(local);
					vectorChildren(p, total, v);
				} else if ("path".equals(n)) {
					VectorLite.Node node = new VectorLite.Node();
					String data = a(p, "pathData");
					Path path = data == null ? null : PathParser.createPathFromPathData(data);
					if (path != null) {
						node.path = path;
						node.m = new Matrix(parent);
						node.fill = a(p, "fillColor") != null ? color(a(p, "fillColor")) : Color.TRANSPARENT;
						node.stroke = a(p, "strokeColor") != null ? color(a(p, "strokeColor")) : Color.TRANSPARENT;
						node.strokeW = num(a(p, "strokeWidth"), 0f);
						node.fillA = num(a(p, "fillAlpha"), 1f);
						node.strokeA = num(a(p, "strokeAlpha"), 1f);
						String cap = a(p, "strokeLineCap"), join = a(p, "strokeLineJoin");
						node.cap = "round".equals(cap) ? Paint.Cap.ROUND : "square".equals(cap) ? Paint.Cap.SQUARE : Paint.Cap.BUTT;
						node.join = "round".equals(join) ? Paint.Join.ROUND : "bevel".equals(join) ? Paint.Join.BEVEL : Paint.Join.MITER;
						if ("evenOdd".equals(a(p, "fillType"))) path.setFillType(Path.FillType.EVEN_ODD);
						v.nodes.add(node);
					}
					skip(p);
				} else {
					skip(p);
				}
			}
		}
	}

	/** Minimal VectorDrawable renderer (paths + group transforms, solid fills/strokes). */
	static final class VectorLite extends Drawable {
		static final class Node {
			Path path; Matrix m;
			int fill, stroke;
			float strokeW, fillA = 1f, strokeA = 1f;
			Paint.Cap cap = Paint.Cap.BUTT;
			Paint.Join join = Paint.Join.MITER;
		}

		final ArrayList<Node> nodes = new ArrayList<Node>();
		float vw = 24f, vh = 24f, rootAlpha = 1f;
		int iw = 24, ih = 24, tint = 0, alpha = 255;
		ColorFilter filter;

		@Override public void draw(Canvas c) {
			Rect b = getBounds();
			if (b.isEmpty() || vw <= 0 || vh <= 0) return;
			c.save();
			c.translate(b.left, b.top);
			c.scale(b.width() / vw, b.height() / vh);
			Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			if (filter != null) paint.setColorFilter(filter);
			else if (tint != 0) paint.setColorFilter(new android.graphics.PorterDuffColorFilter(
					tint, android.graphics.PorterDuff.Mode.SRC_IN));
			for (Node n : nodes) {
				c.save();
				c.concat(n.m);
				if (Color.alpha(n.fill) > 0) {
					paint.setStyle(Paint.Style.FILL);
					paint.setColor(n.fill);
					paint.setAlpha(Math.round(Color.alpha(n.fill) * n.fillA * rootAlpha * alpha / 255f));
					c.drawPath(n.path, paint);
				}
				if (Color.alpha(n.stroke) > 0 && n.strokeW > 0) {
					paint.setStyle(Paint.Style.STROKE);
					paint.setStrokeWidth(n.strokeW);
					paint.setStrokeCap(n.cap);
					paint.setStrokeJoin(n.join);
					paint.setColor(n.stroke);
					paint.setAlpha(Math.round(Color.alpha(n.stroke) * n.strokeA * rootAlpha * alpha / 255f));
					c.drawPath(n.path, paint);
				}
				c.restore();
			}
			c.restore();
		}

		@Override public void setAlpha(int a) { alpha = a; invalidateSelf(); }
		@Override public void setColorFilter(ColorFilter f) { filter = f; invalidateSelf(); }
		@Override public int getOpacity() { return PixelFormat.TRANSLUCENT; }
		@Override public int getIntrinsicWidth() { return iw; }
		@Override public int getIntrinsicHeight() { return ih; }
	}
}
