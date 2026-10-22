package com.nexusteam.blacklogics.sdk.layouteditor;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.nexusteam.blacklogics.SketchwareUtil;
import com.nexusteam.blacklogics.development.Complex;
import com.nexusteam.blacklogics.sdk.AttributeSet;
import com.nexusteam.blacklogics.sdk.widgets.PlaceholderWebView;
import com.nexusteam.blacklogics.sdk.widgets.PlaceholderWidget;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class LayoutPersistence {

	public interface ViewRebuildDelegate {
		View createPlaceholderOrStandardView(String tag);
		void attachEditorClickListener(View view);
		void attachDragAndClickListener(View view);
		android.view.View.OnDragListener getDragListener();
		void applyContainerStroke(View view);
		void applyAllAttributesAfterLoad();
		void addNewId(View view, String id);
		String getId(View view);
		void removeProbe(View probe);
		Complex getProjectComplex();
	}

	private static final String STORE_FILE = "layoutstore";
	private static final int STORE_VERSION = 1;
	private static final byte[] MASK_KEY = "NEXUS_LAYOUT_KEY_7734".getBytes();

	private final Context context;
	private final AttributeStore attributeStore;
	private final XmlGenerator xmlGenerator;
	private final LayoutBackupManager backupManager;
	private final ViewRebuildDelegate delegate;
	private final View placeHolder;
	private final String savePath;

	public LayoutPersistence(Context context, AttributeStore attributeStore,
	XmlGenerator xmlGenerator, LayoutBackupManager backupManager,
	ViewRebuildDelegate delegate, View placeHolder, String savePath) {
		this.context = context;
		this.attributeStore = attributeStore;
		this.xmlGenerator = xmlGenerator;
		this.backupManager = backupManager;
		this.delegate = delegate;
		this.placeHolder = placeHolder;
		this.savePath = savePath;
	}

	private File getStoreFile() {
		return new File(savePath, STORE_FILE);
	}

	private static byte[] applyMask(byte[] data) {
		byte[] result = new byte[data.length];
		for (int i = 0; i < data.length; i++) {
			result[i] = (byte) (data[i] ^ MASK_KEY[i % MASK_KEY.length]);
		}
		return result;
	}


	private static class LayoutEntry {
		String name;
		String xml;
		ArrayList<ViewNode> roots = new ArrayList<ViewNode>();
	}

	private static class ViewNode {
		String tag;
		boolean isPlaceholder;
		boolean isPlaceholderWebView;
		String widgetTag;
		String id;
		ArrayList<String[]> attributes = new ArrayList<String[]>();
		ArrayList<ViewNode> children = new ArrayList<ViewNode>();
	}


	public void saveLayout(ViewGroup editorLayout, String layoutName, String activityName) {
		if (layoutName == null || layoutName.trim().isEmpty()) return;
		if (editorLayout.getChildCount() == 0) return;
		try {
			saveLayoutAsJson(editorLayout, layoutName, activityName);
			String xml = xmlGenerator.generateLayoutXml(editorLayout, activityName,
			layoutName, delegate.getProjectComplex());
			backupManager.saveBackup(layoutName, xml);
		} catch (Exception e) {
			Log.e("LayoutPersistence", "saveLayout failed", e);
		}
	}

	public void saveLayoutAsJson(ViewGroup editorLayout, String layoutName, String activityName)
	throws Exception {
		if (layoutName == null || layoutName.trim().isEmpty()) return;

		ArrayList<ViewNode> roots = new ArrayList<ViewNode>();
		for (int i = 0; i < editorLayout.getChildCount(); i++) {
			View child = editorLayout.getChildAt(i);
			if (child == placeHolder) continue;
			if (!attributeStore.contains(child)) {
				if (!isEditorWidget(child)) continue;
				attributeStore.put(child, synthesizeAttributeSet(child));
			}
			ViewNode node = serializeView(child);
			if (node != null) roots.add(node);
		}

		String xml = xmlGenerator.generateLayoutXml(editorLayout, activityName,
		layoutName, delegate.getProjectComplex());

		File storeFile = getStoreFile();
		storeFile.getParentFile().mkdirs();

		ArrayList<LayoutEntry> entries = readEntriesWithRecovery(storeFile);

		boolean found = false;
		for (int i = 0; i < entries.size(); i++) {
			LayoutEntry entry = entries.get(i);
			if (layoutName.equals(entry.name)) {
				entry.xml = xml;
				entry.roots = roots;
				found = true;
				break;
			}
		}
		if (!found) {
			LayoutEntry newEntry = new LayoutEntry();
			newEntry.name = layoutName;
			newEntry.xml = xml;
			newEntry.roots = roots;
			entries.add(newEntry);
		}

		writeAllEntries(storeFile, entries);

		Log.d("LayoutPersistence", "Saved: " + layoutName + " (" + roots.size() + " roots)");
	}

	public boolean rootLayoutFileExists() {
		return getStoreFile().exists();
	}

	public void loadLayoutFromJson(ViewGroup editorLayout, String layoutName) throws Exception {
		applyParsedLayout(editorLayout, parseLayout(layoutName));
	}

	public static final class ParsedLayout {
		private final LayoutEntry entry;
		private ParsedLayout(LayoutEntry entry) {
			this.entry = entry;
		}
	}

	public ParsedLayout parseLayout(String layoutName) {
		File storeFile = getStoreFile();
		if (!storeFile.exists()) return null;

		ArrayList<LayoutEntry> entries;
		try {
			entries = readEntriesWithRecovery(storeFile);
		} catch (Exception e) {
			Log.e("LayoutPersistence", "parseLayout: store unreadable", e);
			return null;
		}
		for (int i = 0; i < entries.size(); i++) {
			if (layoutName.equals(entries.get(i).name)) {
				return new ParsedLayout(entries.get(i));
			}
		}
		return null;
	}

	public int countViews(ParsedLayout parsed) {
		if (parsed == null) return 0;
		int n = 0;
		for (int i = 0; i < parsed.entry.roots.size(); i++) {
			n += countNode(parsed.entry.roots.get(i));
		}
		return n;
	}

	private int countNode(ViewNode node) {
		if (node == null) return 0;
		int n = 1;
		for (int i = 0; i < node.children.size(); i++) n += countNode(node.children.get(i));
		return n;
	}

	public void applyParsedLayout(ViewGroup editorLayout, ParsedLayout parsed) {
		if (parsed == null) return;

		for (int i = 0; i < parsed.entry.roots.size(); i++) {
			rebuildView(parsed.entry.roots.get(i), editorLayout);
		}
		delegate.applyAllAttributesAfterLoad();
	}


	private ArrayList<LayoutEntry> readEntriesWithRecovery(File storeFile) throws Exception {
		try {
			return readAllEntries(storeFile);
		} catch (StoreCorruptException first) {
			File bak = new File(storeFile.getParentFile(), STORE_FILE + ".bak");
			if (bak.exists() && bak.length() > 0) {
				Log.w("LayoutPersistence", "main store corrupt — recovering from .bak");
				ArrayList<LayoutEntry> fromBak = readAllEntries(bak);
				try {
					if (!bak.renameTo(storeFile)) {
						writeAllEntries(storeFile, fromBak);
					}
				} catch (Throwable ignored) {}
				return fromBak;
			}
			throw first;
		}
	}

	private static class StoreCorruptException extends Exception {
		StoreCorruptException(String m, Throwable c) { super(m, c); }
	}

	private ArrayList<LayoutEntry> readAllEntries(File storeFile) throws StoreCorruptException {
		ArrayList<LayoutEntry> entries = new ArrayList<LayoutEntry>();
		if (!storeFile.exists() || storeFile.length() == 0) return entries;

		FileInputStream fis = null;
		DataInputStream in = null;
		try {
			int len = (int) storeFile.length();
			byte[] raw = new byte[len];
			fis = new FileInputStream(storeFile);
			int off = 0;
			while (off < len) {
				int n = fis.read(raw, off, len - off);
				if (n < 0) break;
				off += n;
			}
			if (off != len) throw new java.io.IOException("short read " + off + "/" + len);

			byte[] plain = applyMask(raw);
			in = new DataInputStream(new ByteArrayInputStream(plain));

			int version = in.readInt();
			if (version != STORE_VERSION) {
				throw new java.io.IOException("unknown store version " + version);
			}
			int entryCount = in.readInt();
			if (entryCount < 0 || entryCount > 100000) {
				throw new java.io.IOException("bad entryCount " + entryCount);
			}
			for (int i = 0; i < entryCount; i++) {
				LayoutEntry entry = new LayoutEntry();
				entry.name = in.readUTF();
				entry.xml = in.readUTF();
				int rootCount = in.readInt();
				for (int r = 0; r < rootCount; r++) {
					entry.roots.add(readViewNode(in));
				}
				entries.add(entry);
			}
			return entries;
		} catch (Exception e) {
			Log.e("LayoutPersistence", "readAllEntries failed — store NOT overwritten", e);
			try { if (in != null) in.close(); } catch (Exception ignored) {}
			try { if (fis != null) fis.close(); } catch (Exception ignored) {}
			in = null; fis = null;
			try {
				File bad = new File(storeFile.getParentFile(),
						STORE_FILE + ".corrupt." + System.currentTimeMillis());
				storeFile.renameTo(bad);
			} catch (Throwable ignored) {}
			throw new StoreCorruptException("layoutstore unreadable: " + e.getMessage(), e);
		} finally {
			try { if (in != null) in.close(); } catch (Exception ignored) {}
			try { if (fis != null) fis.close(); } catch (Exception ignored) {}
		}
	}

	private void writeAllEntries(File storeFile, ArrayList<LayoutEntry> entries) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(bos);
		out.writeInt(STORE_VERSION);
		out.writeInt(entries.size());
		for (int i = 0; i < entries.size(); i++) {
			LayoutEntry entry = entries.get(i);
			out.writeUTF(entry.name != null ? entry.name : "");
			out.writeUTF(entry.xml != null ? entry.xml : "");
			out.writeInt(entry.roots.size());
			for (int r = 0; r < entry.roots.size(); r++) {
				writeViewNode(out, entry.roots.get(r));
			}
		}
		out.flush();
		byte[] masked = applyMask(bos.toByteArray());

		File dir = storeFile.getParentFile();
		File tmp = new File(dir, STORE_FILE + ".tmp");
		File bak = new File(dir, STORE_FILE + ".bak");

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(tmp);
			fos.write(masked);
			fos.flush();
			try { fos.getFD().sync(); } catch (Throwable ignored) {}
		} finally {
			try { if (fos != null) fos.close(); } catch (Exception ignored) {}
		}

		if (tmp.length() != masked.length) {
			tmp.delete();
			throw new java.io.IOException("tmp write size mismatch");
		}

		if (storeFile.exists()) {
			if (bak.exists()) bak.delete();
			storeFile.renameTo(bak);
		}
		if (!tmp.renameTo(storeFile)) {
			if (bak.exists()) bak.renameTo(storeFile);
			throw new java.io.IOException("atomic rename failed");
		}
	}

	private void writeViewNode(DataOutputStream out, ViewNode node) throws Exception {
		out.writeUTF(node.tag != null ? node.tag : "View");
		out.writeBoolean(node.isPlaceholder);
		out.writeBoolean(node.isPlaceholderWebView);
		out.writeUTF(node.widgetTag != null ? node.widgetTag : "");
		out.writeUTF(node.id != null ? node.id : "");

		out.writeInt(node.attributes.size());
		for (int i = 0; i < node.attributes.size(); i++) {
			String[] pair = node.attributes.get(i);
			out.writeUTF(pair[0]);
			out.writeUTF(pair[1] != null ? pair[1] : "");
		}

		out.writeInt(node.children.size());
		for (int i = 0; i < node.children.size(); i++) {
			writeViewNode(out, node.children.get(i));
		}
	}

	private ViewNode readViewNode(DataInputStream in) throws Exception {
		ViewNode node = new ViewNode();
		node.tag = in.readUTF();
		node.isPlaceholder = in.readBoolean();
		node.isPlaceholderWebView = in.readBoolean();
		node.widgetTag = in.readUTF();
		node.id = in.readUTF();

		int attrCount = in.readInt();
		for (int i = 0; i < attrCount; i++) {
			String name = in.readUTF();
			String value = in.readUTF();
			node.attributes.add(new String[]{name, value});
		}

		int childCount = in.readInt();
		for (int i = 0; i < childCount; i++) {
			node.children.add(readViewNode(in));
		}
		return node;
	}


	private ViewNode serializeView(View view) {
		if (view == null || view == placeHolder) return null;

		ViewNode node = new ViewNode();

		if (view instanceof PlaceholderWidget) {
			String orig = ((PlaceholderWidget) view).getWidgetTag();
			node.tag = (orig != null && !orig.isEmpty()) ? orig : "View";
			node.isPlaceholder = true;
			node.widgetTag = node.tag;
		} else if (view instanceof PlaceholderWebView) {
			node.tag = "WebView";
			node.isPlaceholderWebView = true;
		} else {
			node.tag = view.getClass().getName();
		}

		String viewId = delegate.getId(view);
		if (viewId != null) node.id = viewId;

		AttributeSet attrSet = attributeStore.get(view);
		if (attrSet != null) {
			float density = context.getResources().getDisplayMetrics().density;
			boolean hasPaddingInMap = false;
			ViewGroup.MarginLayoutParams mlp = null;
			if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
				mlp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
			}

			for (Attribute a : attrSet.getAttributes()) {
				String name = a.getName();
				String val = (a.getValue() != null) ? a.getValue() : "";
				if (name.startsWith("android:padding")) hasPaddingInMap = true;
				node.attributes.add(new String[]{name, val});
			}

			int pl = view.getPaddingLeft(), pt = view.getPaddingTop(),
			pr = view.getPaddingRight(), pb = view.getPaddingBottom();
			if (!hasPaddingInMap && (pl != 0 || pt != 0 || pr != 0 || pb != 0)) {
				node.attributes.add(new String[]{"android:paddingLeft",
					Math.round(pl / density) + "dp"});
				node.attributes.add(new String[]{"android:paddingTop",
					Math.round(pt / density) + "dp"});
				node.attributes.add(new String[]{"android:paddingRight",
					Math.round(pr / density) + "dp"});
				node.attributes.add(new String[]{"android:paddingBottom",
					Math.round(pb / density) + "dp"});
			}

			if (mlp != null) {
				if (!hasAttr(node, "android:layout_marginLeft") && mlp.leftMargin != 0)
				node.attributes.add(new String[]{"android:layout_marginLeft",
					Math.round(mlp.leftMargin / density) + "dp"});
				if (!hasAttr(node, "android:layout_marginTop") && mlp.topMargin != 0)
				node.attributes.add(new String[]{"android:layout_marginTop",
					Math.round(mlp.topMargin / density) + "dp"});
				if (!hasAttr(node, "android:layout_marginRight") && mlp.rightMargin != 0)
				node.attributes.add(new String[]{"android:layout_marginRight",
					Math.round(mlp.rightMargin / density) + "dp"});
				if (!hasAttr(node, "android:layout_marginBottom") && mlp.bottomMargin != 0)
				node.attributes.add(new String[]{"android:layout_marginBottom",
					Math.round(mlp.bottomMargin / density) + "dp"});
			}
		}

		if (view instanceof ViewGroup && !isOpaqueSystemWidget(view)) {
			ViewGroup vg = (ViewGroup) view;
			
			if (view instanceof com.google.android.material.textfield.TextInputLayout) {
				View editText = ((com.google.android.material.textfield.TextInputLayout) view)
				.getEditText();
				if (editText != null && editText != placeHolder
				&& attributeStore.contains(editText)) {
					ViewNode childNode = serializeView(editText);
					if (childNode != null) node.children.add(childNode);
				}
			} else {
				for (int i = 0; i < vg.getChildCount(); i++) {
					View child = vg.getChildAt(i);
					if (child == placeHolder) continue;
					if (!attributeStore.contains(child)) {
						if (!isEditorWidget(child)) continue;
						attributeStore.put(child, synthesizeAttributeSet(child));
					}
					ViewNode childNode = serializeView(child);
					if (childNode != null) node.children.add(childNode);
				}
			}
		}

		return node;
	}

	private boolean isEditorWidget(View v) {
		if (v == null || v == placeHolder) return false;
		if (v instanceof PlaceholderWidget || v instanceof PlaceholderWebView) return true;
		String id = delegate.getId(v);
		return id != null && !id.isEmpty();
	}

	private AttributeSet synthesizeAttributeSet(View v) {
		AttributeSet set = new AttributeSet();
		String id = delegate.getId(v);
		if (id != null && !id.isEmpty()) {
			set.add(new Attribute("android:id", "@+id/" + id));
		}
		ViewGroup.LayoutParams lp = v.getLayoutParams();
		if (lp != null) {
			set.add(new Attribute("android:layout_width", dimToXml(lp.width)));
			set.add(new Attribute("android:layout_height", dimToXml(lp.height)));
		}
		return set;
	}

	private String dimToXml(int px) {
		if (px == ViewGroup.LayoutParams.MATCH_PARENT) return "match_parent";
		if (px == ViewGroup.LayoutParams.WRAP_CONTENT) return "wrap_content";
		float d = context.getResources().getDisplayMetrics().density;
		return Math.round(px / d) + "dp";
	}

	private boolean hasAttr(ViewNode node, String name) {
		for (int i = 0; i < node.attributes.size(); i++) {
			if (node.attributes.get(i)[0].equals(name)) return true;
		}
		return false;
	}

	private boolean isOpaqueSystemWidget(View view) {
		String cls = view.getClass().getName();
		return cls.equals("android.widget.CalendarView")
		|| cls.equals("android.widget.DatePicker")
		|| cls.equals("android.widget.TimePicker")
		|| cls.equals("android.widget.NumberPicker")
		|| cls.equals("android.widget.SearchView")
		|| cls.equals("android.widget.ExpandableListView")
		|| cls.equals("android.widget.VideoView");
	}

	private String uniqueIdFrom(String base) {
		String stem = base.replaceAll("\\d+$", "");
		if (stem.isEmpty()) stem = "view";
		int n = 1;
		while (true) {
			String cand = stem + n;
			if (!cand.equals(base) && !isIdTakenByDelegate(cand)) return cand;
			n++;
			if (n > 100000) return stem + System.nanoTime();
		}
	}

	private boolean isIdTakenByDelegate(String id) {
		View probe = new View(context);
		try {
			delegate.addNewId(probe, id);
			delegate.removeProbe(probe);
			return false;
		} catch (IllegalArgumentException taken) {
			return true;
		}
	}

	private void rebuildView(ViewNode node, ViewGroup parentView) {
		if (node == null || parentView == null) return;
		try {
			String tag = (node.tag != null) ? node.tag : "View";

			View view;
			if (node.isPlaceholder) {
				String widgetTag = (node.widgetTag != null && !node.widgetTag.isEmpty())
				? node.widgetTag : tag;
				view = new PlaceholderWidget(context, widgetTag);
			} else if (node.isPlaceholderWebView) {
				view = new PlaceholderWebView(context);
			} else {
				view = delegate.createPlaceholderOrStandardView(tag);
			}

			if (view == null) view = new PlaceholderWidget(context, tag);

			if (!node.children.isEmpty() && !(view instanceof ViewGroup)) {
				Log.w("LayoutPersistence", "Container '" + tag
				+ "' ViewGroup ki tarah reconstruct nahi hui — "
				+ node.children.size()
				+ " child(ren) ko recovery wrapper mein daal rahe hain "
				+ "taaki save/load mein woh gaayab na ho.");
				android.widget.FrameLayout recoveryWrapper =
				new android.widget.FrameLayout(context);
				recoveryWrapper.setTag("recovered:" + tag);
				view = recoveryWrapper;
			}

			String finalId = node.id;
			if (finalId == null || finalId.trim().isEmpty()) {
				String stem = tag;
				int dot = stem.lastIndexOf('.');
				if (dot >= 0 && dot < stem.length() - 1) stem = stem.substring(dot + 1);
				stem = stem.toLowerCase().replaceAll("[^a-z0-9_]", "");
				if (stem.isEmpty()) stem = "view";
				finalId = uniqueIdFrom(stem + "1");
				Log.w("LayoutPersistence", "node had empty id for tag=" + tag
						+ " — assigned '" + finalId + "'");
			}
			try {
				delegate.addNewId(view, finalId);
			} catch (IllegalArgumentException dup) {
				String unique = uniqueIdFrom(finalId);
				Log.w("LayoutPersistence", "id '" + finalId
						+ "' already in use — using '" + unique + "'");
				delegate.addNewId(view, unique);
				finalId = unique;
			}

			AttributeSet set = new AttributeSet();
			boolean sawIdAttr = false;
			for (int i = 0; i < node.attributes.size(); i++) {
				String[] pair = node.attributes.get(i);
				if ("android:id".equals(pair[0]) && finalId != null && !finalId.isEmpty()) {
					set.add(new Attribute("android:id", "@+id/" + finalId));
					sawIdAttr = true;
				} else {
					set.add(new Attribute(pair[0], pair[1]));
				}
			}
			if (!sawIdAttr && finalId != null && !finalId.isEmpty()) {
				set.add(new Attribute("android:id", "@+id/" + finalId));
			}
			attributeStore.put(view, set);
			parentView.addView(view);

			delegate.attachEditorClickListener(view);
			delegate.attachDragAndClickListener(view);

			boolean leaf = false;
			try {
				leaf = com.nexusteam.internal.os.layouteditor.EditorDragController
						.isLeafOrOpaqueContainer(view);
			} catch (Throwable ignore) {
				String sn = view.getClass().getSimpleName();
				leaf = "WebView".equals(sn) || "VideoView".equals(sn)
						|| "PlaceholderWidget".equals(sn) || "PlaceholderWebView".equals(sn);
			}

			if (view instanceof ViewGroup && !leaf) {
				ViewGroup vg = (ViewGroup) view;
				vg.setOnDragListener(delegate.getDragListener());
				delegate.applyContainerStroke(vg);
				vg.setMinimumHeight((int) SketchwareUtil.getDip(context, 30));

				for (int i = 0; i < node.children.size(); i++) {
					rebuildView(node.children.get(i), vg);
				}
			} else if (view instanceof ViewGroup && leaf) {
				if (!node.children.isEmpty()) {
					Log.w("LayoutPersistence", "leaf container '" + tag
							+ "' had " + node.children.size()
							+ " nested child(ren) — relocating to parent");
					for (int i = 0; i < node.children.size(); i++) {
						rebuildView(node.children.get(i), parentView);
					}
				}
			}
		} catch (Exception e) {
			Log.e("LayoutPersistence", "rebuildView failed for: "
			+ (node.tag != null ? node.tag : "?"), e);
		}
	}
}
