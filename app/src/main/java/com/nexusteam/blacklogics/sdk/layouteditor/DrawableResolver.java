package com.nexusteam.blacklogics.sdk.layouteditor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.nexusteam.blacklogics.DesignActivity;
import com.nexusteam.blacklogics.project.ProjectDataHelper;

import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 * One place that knows where project images live. Both folders are merged:
 *   1) /storage/emulated/0/.blacklogics/data/<sc_id>/files/resource/drawable
 *   2) /storage/emulated/0/.blacklogics/resources/images/<sc_id>
 * (1) wins when the same name exists in both. Supports png/jpg/jpeg/webp/gif and xml drawables.
 */
final class DrawableResolver {

	private static final String ROOT = "/storage/emulated/0/.blacklogics/";
	private static final String[] EXTS = {".png", ".jpg", ".jpeg", ".webp", ".gif", ".xml"};

	private DrawableResolver() {}

	static String currentScId(Context ctx) {
		String sc = null;
		try { sc = ProjectDataHelper.getScId(ctx); } catch (Throwable ignore) {}
		if (sc == null || sc.isEmpty()) {
			try { sc = DesignActivity.getScId(); } catch (Throwable ignore) {}
		}
		return sc;
	}

	static File[] dirs(String scId) {
		return new File[] {
			new File(ROOT + "data/" + scId + "/files/resource/drawable"),
			new File(ROOT + "resources/images/" + scId)
		};
	}

	/** name (no extension) -> file, merged from both folders, sorted by name. */
	static Map<String, File> listAll(String scId) {
		TreeMap<String, File> out = new TreeMap<String, File>(String.CASE_INSENSITIVE_ORDER);
		if (scId == null || scId.isEmpty()) return out;
		for (File dir : dirs(scId)) {
			File[] files = dir.listFiles();
			if (files == null) continue;
			for (File f : files) {
				if (!f.isFile()) continue;
				String n = f.getName();
				String low = n.toLowerCase(Locale.ROOT);
				boolean ok = false;
				for (String e : EXTS) if (low.endsWith(e)) { ok = true; break; }
				if (!ok) continue;
				String base = n.substring(0, n.lastIndexOf('.'));
				if (!out.containsKey(base)) out.put(base, f);
			}
		}
		return out;
	}

	static String nameFromRef(String v) {
		if (v == null) return null;
		v = v.trim();
		if (v.startsWith("@drawable/")) return v.substring(10);
		if (v.startsWith("@mipmap/")) return v.substring(8);
		if (v.startsWith("@")) return null;
		return v;
	}

	static File find(String scId, String name) {
		if (scId == null || name == null || name.isEmpty()) return null;
		for (File dir : dirs(scId)) {
			File exact = new File(dir, name);
			if (exact.isFile()) return exact;
			for (String e : EXTS) {
				File f = new File(dir, name + e);
				if (f.isFile()) return f;
			}
		}
		return null;
	}

	/** value like "@drawable/foo" (or plain "foo"). Returns null if not found in project folders. */
	static Drawable load(Context ctx, String scId, String value) {
		String name = nameFromRef(value);
		File f = find(scId, name);
		return f == null ? null : loadFile(ctx, scId, f, 0);
	}

	static Drawable loadFile(Context ctx, String scId, File f, int depth) {
		try {
			String n = f.getName().toLowerCase(Locale.ROOT);
			if (n.endsWith(".xml")) return XmlDrawableInflater.inflate(ctx, scId, f, depth);
			Bitmap b = decode(f, 2048);
			return b == null ? null : new BitmapDrawable(ctx.getResources(), b);
		} catch (Throwable t) {
			return null;
		}
	}

	static Drawable thumbnail(Context ctx, String scId, File f, int maxPx) {
		try {
			if (f.getName().toLowerCase(Locale.ROOT).endsWith(".xml")) {
				return XmlDrawableInflater.inflate(ctx, scId, f, 0);
			}
			Bitmap b = decode(f, maxPx);
			return b == null ? null : new BitmapDrawable(ctx.getResources(), b);
		} catch (Throwable t) {
			return null;
		}
	}

	static Bitmap decode(File f, int maxDim) {
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(f.getAbsolutePath(), o);
		int s = 1;
		while (o.outWidth / s > maxDim * 2 || o.outHeight / s > maxDim * 2) s *= 2;
		o.inJustDecodeBounds = false;
		o.inSampleSize = s;
		return BitmapFactory.decodeFile(f.getAbsolutePath(), o);
	}
}
