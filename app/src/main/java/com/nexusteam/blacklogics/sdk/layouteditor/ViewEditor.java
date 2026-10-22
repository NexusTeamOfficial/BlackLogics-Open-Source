package com.nexusteam.blacklogics.sdk.layouteditor;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Looper;
import android.os.Vibrator;
import android.util.Log;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.app.AlertDialog;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import com.nexusteam.blacklogics.utils.EventUtils;
import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.blacklogics.lib.base.BaseActivity;
import com.nexusteam.blacklogics.*;
import com.nexusteam.blacklogics.beans.*;
import com.nexusteam.blacklogics.editor.layout.storage.*;
import com.nexusteam.blacklogics.editor.layout.model.LayoutData;
import com.nexusteam.blacklogics.logic.editor.LogicEditorActivity;
import com.nexusteam.blacklogics.project.*;
import com.nexusteam.blacklogics.R;
import com.nexusteam.blacklogics.adapter.*;
import com.nexusteam.blacklogics.constants.*;
import com.nexusteam.blacklogics.ui.*;
import com.nexusteam.blacklogics.editor.*;
import com.nexusteam.blacklogics.interfaces.*;
import com.nexusteam.blacklogics.custom.*;
import com.nexusteam.internal.os.layouteditor.*;
import com.nexusteam.ui.FloatAttributeMaterialDialog.OnFloatSelected;
import com.nexusteam.ui.TextSizePickerDialog;
import com.nexusteam.ui.TextSizePickerDialog.OnTextSizeSelected;
import com.nexusteam.internal.os.layouteditor.color.ColorPickerActivity;
import com.nexusteam.internal.os.layouteditor.util.*;
import com.nexusteam.ui.*;
import com.nexusteam.blacklogics.sdk.AttributeSet;
import com.nexusteam.blacklogics.sdk.ui.SkAddToCollectionDialog;
import com.nexusteam.blacklogics.sdk.collection.SkCollectionIntegration;
import com.nexusteam.blacklogics.sdk.collection.WidgetCollectionBean;
import com.nexusteam.blacklogics.sdk.collection.CollectionInflater;
import com.nexusteam.blacklogics.sdk.adapter.SkCollectionListAdapter;
// Deprecated collection (kept commented for reference):
// import com.nexusteam.blacklogics.sdk.ui.AddToCollectionDialog;
// import com.nexusteam.blacklogics.sdk.managers.WidgetRepository;
// import com.nexusteam.blacklogics.sdk.models.WidgetBlueprint;
import com.nexusteam.internal.os.layouteditor.undo.StableUndoManager;
import com.nexusteam.internal.os.layouteditor.undo.HistoryManager;
import com.nexusteam.internal.beans.ViewHistoryBean;
import com.nexusteam.internal.beans.ViewBean;
import com.nexusteam.internal.os.layouteditor.EditorDragController;
import com.nexusteam.blacklogics.sdk.utils.DragAndDropUtils;
import com.nexusteam.blacklogics.sdk.widgets.*;
import com.nexusteam.blacklogics.sdk.managers.*;
import com.nexusteam.blacklogics.sdk.utils.*;
import com.nexusteam.blacklogics.sdk.adapter.CollectionWidgetsAdapter;
// import com.nexusteam.blacklogics.sdk.adapter.CollectionTreeAdapter;
// import com.nexusteam.blacklogics.sdk.managers.CollectionFolderRepository;
// import com.nexusteam.blacklogics.sdk.models.CollectionFolder;
import com.nexusteam.blacklogics.sdk.util.ActivityConfig;
import com.nexusteam.blacklogics.development.Complex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewEditor extends EditorLayout implements WidgetOnClick, WidgetOnAttribute {
	
	public interface OnWidgetAdd {
		void onWidgetAdded(View widget, ViewGroup parent);
	}
	
	public static final String ACTION_XML_SYNC = "com.nexusteam.blacklogics.sdk.ACTION_XML_SYNC";
	public static final String EXTRA_XML = "xml_content";
	
	
	private LinearLayout lin_toolbar;
	private ImageView img_views;
	private ImageView img_add_view;
	private ImageView img_undo;
	private ImageView img_redo;
	private ImageView img_grid;
	private ImageView img_snap;
	
	public LinearLayout editorLayout;
	private LinearLayout editorCanvasViewport;
	private FrameLayout editorCanvasFrame;
	private RecyclerView listview_widgets;
	private RecyclerView collection_list;
	private NavBar customNav;
	private LinearLayout widgetPalette;
	private LinearLayout collectionPalette;
	private WidgetPaletteAdapter paletteAdapter;
	private android.widget.TextView tvCanvasEmpty;
	
	public static android.widget.TextView tv_view_name;
	public static LinearLayout phone_action_bar;
	public static LinearLayout phone_status_bar;
	
	/** Sketchware-style activity FAB overlay (id = _fab). Not the deprecated ViewBuilder FAB. */
	private View editorFabView;
	
	
	private StableUndoManager stableUndoManager;
	private HistoryManager historyManager;
	private ViewBeanRegistry viewBeanRegistry;
	public boolean isUndoRedoInProgress = false;
	/** Tracks which manager received the most recent push for combined undo/redo routing. */
	private final java.util.HashMap<String, String> lastUndoSource = new java.util.HashMap<String, String>();
	private static final String SOURCE_STABLE = "stable";
	private static final String SOURCE_HISTORY = "history";
	private android.os.Handler autoSaveHandler;
	private final Runnable autoSaveRunnable = new Runnable() {
		@Override public void run() {
			try {
				if (isUndoRedoInProgress) return;
				if (historyManager != null && historyManager.isApplying()) return;
				if (editorLayout == null || editorLayout.getChildCount() == 0) return;
				String name = getCurrentActivityName();
				if (name != null) flushSave();
			} catch (Exception e) {
				android.util.Log.e("ViewEditor", "autosave failed", e);
			}
		}
	};
	private static final long AUTOSAVE_DELAY_MS = 1500L;
	
	private ViewGroup currentViewGroup;
	private LinearLayoutCompat attributesContainer;
	private ObjectAnimator slideAnimator = new ObjectAnimator();
	
	private ViewEditorDrag viewEditorDrag;
	
	private final AttributeStore attributeStore = new AttributeStore();
	
	public final HashMap<View, AttributeSet> attributesValueMap = attributeStore.backingMap();
	
	public String id = "";
	public static String SAVE_PATH = "";
	public static String SC_ID = "601";
	public int index;
	public Vibrator vib;
	private View placeHolder;
	private EditorDragController dragController;
	public DragListener dragListener;
	
	private ImageView deleteImg;
	public static IdManager idManager = new IdManager();
	public IdManager oldIdManager = new IdManager();
	public OnWidgetAdd onWidgetAddListener;
	
	public Intent intent = new Intent();
	
	private DesignActivity designActivity;
	
	private View selectedView = null;
	
	private final java.util.LinkedHashSet<View> selectedViews = new java.util.LinkedHashSet<View>();
	
	private LinearLayout selectionActionBar = null;
	
	private static class ClipboardWidget {
		String className;
		AttributeSet attributes;
	}
	private ClipboardWidget widgetClipboard = null;
	
	private ArrayList<HashMap<String, Object>> viewsList = new ArrayList<HashMap<String, Object>>();
	private ArrayList<HashMap<String, Object>> list_widget_map = new ArrayList<HashMap<String, Object>>();
	private HashMap<String, Object> widget_add_map = new HashMap<String, Object>();
	
	public ViewItems viewItems;
	
	private BroadcastReceiver xmlSyncReceiver;
	
	private Complex projectComplex;
	
	
	private EditorUiHelpers uiHelpers;
	private AttributeApplier attributeApplier;
	private AttributeDialogs attributeDialogs;
	private XmlGenerator xmlGenerator;
	private LayoutBackupManager backupManager;
	private LayoutPersistence layoutPersistence;
	private AsyncTask<Void, Void, LayoutPersistence.ParsedLayout> loadLayoutTask;
	private static final java.util.HashMap<String, LayoutPersistence.ParsedLayout> sParsedLayoutCache =
	new java.util.HashMap<String, LayoutPersistence.ParsedLayout>();
	private WidgetDropHandler widgetDropHandler;
	private ListenerCatalog listenerCatalog;
	
	
	public ViewEditor(Context context) {
		super(context);
		initialize(context);
	}
	
	public ViewEditor(Context context, android.util.AttributeSet attrs) {
		super(context, attrs);
		initialize(context);
	}
	
	public ViewEditor(Context context, android.util.AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initialize(context);
	}
	
	
	private void disableCanvasStateRestore() {
		if (editorLayout == null) return;
		editorLayout.setSaveEnabled(false);
		editorLayout.setSaveFromParentEnabled(false);
		editorLayout.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
			@Override public void onChildViewAdded(View parent, View child) {
				disableSaveStateRecursive(child, 0);
				updateCanvasEmptyHint();
			}
			@Override public void onChildViewRemoved(View parent, View child) {
				updateCanvasEmptyHint();
			}
		});
	}
	
	private void updateCanvasEmptyHint() {
		if (tvCanvasEmpty == null || editorLayout == null) return;
		tvCanvasEmpty.animate().cancel();
		if (editorLayout.getChildCount() == 0) {
			tvCanvasEmpty.setVisibility(View.VISIBLE);
			tvCanvasEmpty.animate().alpha(1f).setStartDelay(600).setDuration(200).start();
		} else {
			tvCanvasEmpty.setAlpha(0f);
			tvCanvasEmpty.setVisibility(View.GONE);
		}
	}
	
	private void disableSaveStateRecursive(View v, int depth) {
		if (v == null || depth > 30) return;
		v.setSaveEnabled(false);
		v.setSaveFromParentEnabled(false);
		if (v instanceof ViewGroup) {
			ViewGroup g = (ViewGroup) v;
			for (int i = 0; i < g.getChildCount(); i++) {
				disableSaveStateRecursive(g.getChildAt(i), depth + 1);
			}
		}
	}
	
	private boolean isRegistryOutOfSync() {
		if (editorLayout == null) return false;
		int canvasKids = 0;
		for (int i = 0; i < editorLayout.getChildCount(); i++) {
			View c = editorLayout.getChildAt(i);
			if (c == null || c == placeHolder || c == selectionActionBar) continue;
			canvasKids++;
			if (idManager == null || !idManager.hasId(c)) {
				return true;
			}
		}
		if (canvasKids == 0) return false;
		return idManager == null || idManager.getIds().isEmpty();
	}
	
	private boolean recoverRegistryIfNeeded() {
		if (!isRegistryOutOfSync()) return false;
		final int canvasCount = countTopLevelAndNested(editorLayout, 0);
		Log.w("ViewEditor", "Registry empty but canvas has " + canvasCount
		+ " view(s) — process-death / stale-restore detected");
		try {
			String name = getCurrentActivityName();
			String key = name == null ? "" : name.trim();
			int diskCount = -1;
			try {
				LayoutPersistence.ParsedLayout parsed = layoutPersistence.parseLayout(key);
				if (parsed == null) {
					String alt = alternateLayoutKey(key);
					if (alt != null) { parsed = layoutPersistence.parseLayout(alt); if (parsed != null) key = alt; }
				}
				if (parsed != null) diskCount = layoutPersistence.countViews(parsed);
			} catch (Throwable t) {
				Log.w("ViewEditor", "disk probe failed", t);
			}
			
			if (diskCount >= canvasCount && diskCount > 0) {
				Log.d("ViewEditor", "recovery: reload from disk (" + diskCount + " >= " + canvasCount + ")");
				sParsedLayoutCache.remove(key);
				LayoutPersistence.ParsedLayout p = layoutPersistence.parseLayout(key);
				editorLayout.removeAllViews();
				resetIdManagerInPlace();
				attributeStore.clear();
				layoutPersistence.applyParsedLayout(editorLayout, p);
			} else {
				Log.d("ViewEditor", "recovery: canvas has unsaved/newer content (" + canvasCount
				+ " > disk " + diskCount + ") — rebuilding registry from canvas");
				rebuildRegistryFromCanvas(editorLayout, 0);
			}
			refreshUndoRedoButtons();
			return true;
		} catch (Throwable t) {
			Log.e("ViewEditor", "registry recovery failed", t);
			return false;
		}
	}
	
	public void forceRegistryRecovery() {
		try {
			if (editorLayout == null) return;
			boolean recovered = recoverRegistryIfNeeded();
			if (recovered) {
				Log.i("ViewEditor", "forceRegistryRecovery: registry restored");
				selectedView = null;
				selectedViews.clear();
				if (selectionActionBar != null) {
					selectionActionBar.setVisibility(View.GONE);
				}
			}
			if (isRegistryOutOfSync()) {
				Log.w("ViewEditor", "forceRegistryRecovery: still out of sync, rebuild from canvas");
				rebuildRegistryFromCanvas(editorLayout, 0);
			}
			refreshUndoRedoButtons();
		} catch (Throwable t) {
			Log.e("ViewEditor", "forceRegistryRecovery failed", t);
		}
	}
	
	public void ensureRegistryAfterLoad() {
		try {
			if (editorLayout == null) return;
			if (isRegistryOutOfSync()) {
				Log.w("ViewEditor", "ensureRegistryAfterLoad: out of sync after load — rebuild");
				rebuildRegistryFromCanvas(editorLayout, 0);
			}
			syncEditorFabRegistry();
		} catch (Throwable t) {
			Log.e("ViewEditor", "ensureRegistryAfterLoad failed", t);
		}
	}
	
	public static void clearParsedLayoutCache() {
		try {
			sParsedLayoutCache.clear();
		} catch (Throwable ignore) {}
	}
	
	public void clearForProjectSwitch() {
		try {
			resetIdManagerInPlace();
			attributeStore.clear();
			clearParsedLayoutCache();
			selectedView = null;
			selectedViews.clear();
			if (editorLayout != null) {
				editorLayout.removeAllViews();
			}
			try {
				if (stableUndoManager != null) {
					stableUndoManager.clearAll();
				}
			} catch (Throwable ignore) {
			}
			refreshUndoRedoButtons();
			Log.i("ViewEditor", "clearForProjectSwitch done");
		} catch (Throwable t) {
			Log.e("ViewEditor", "clearForProjectSwitch failed", t);
		}
	}
	
	private int countTopLevelAndNested(ViewGroup parent, int depth) {
		if (parent == null || depth > 40) return 0;
		int n = 0;
		for (int i = 0; i < parent.getChildCount(); i++) {
			View c = parent.getChildAt(i);
			if (c == null || c == placeHolder || c == selectionActionBar) continue;
			n++;
			if (c instanceof ViewGroup && !isOpaqueForRebuild(c)) {
				n += countTopLevelAndNested((ViewGroup) c, depth + 1);
			}
		}
		return n;
	}
	
	private int rebuildRegistryFromCanvas(ViewGroup parent, int depth) {
		if (parent == null || depth > 40) return 0;
		int count = 0;
		for (int i = 0; i < parent.getChildCount(); i++) {
			View child = parent.getChildAt(i);
			if (child == null || child == placeHolder) continue;
			if (child == selectionActionBar) continue;
			
			// Prefer existing android:id from AttributeSet — never invent switchmaterial1
			// when the real ID is already known on the attribute map.
			if (!idManager.hasId(child)) {
				String recoveredId = null;
				AttributeSet existing = attributeStore.get(child);
				if (existing != null) {
					Attribute idAttr = existing.getAttribute("android:id");
					if (idAttr == null) idAttr = existing.getAttribute("id");
					if (idAttr != null && idAttr.getValue() != null) {
						recoveredId = stripIdPrefix(idAttr.getValue());
					}
				}
				try {
					if (recoveredId != null && !recoveredId.isEmpty()
							&& !idManager.isIdUsed(recoveredId)) {
						idManager.addNewId(child, recoveredId);
					} else if (recoveredId != null && !recoveredId.isEmpty()
							&& idManager.getView(recoveredId) == child) {
						// already bound to this view via reverse map edge case
					} else {
						String gen = idManager.generateNewId(child);
						idManager.addNewId(child, gen);
					}
				} catch (Throwable t) {
					Log.w("ViewEditor", "id rebuild failed for "
					+ child.getClass().getSimpleName(), t);
				}
			}
			if (!attributeStore.contains(child)) {
				AttributeSet set = new AttributeSet();
				String id = idManager.getId(child);
				if (id != null) set.add(new Attribute("android:id", "@+id/" + id));
				ViewGroup.LayoutParams lp = child.getLayoutParams();
				if (lp != null) {
					set.add(new Attribute("android:layout_width", rebuildDim(lp.width)));
					set.add(new Attribute("android:layout_height", rebuildDim(lp.height)));
				}
				attributeStore.put(child, set);
			} else {
				// Keep AttributeSet android:id in sync with IdManager
				String liveId = idManager.getId(child);
				if (liveId != null) {
					AttributeSet set = attributeStore.get(child);
					if (set != null) {
						AttributeSet synced = new AttributeSet();
						boolean hadId = false;
						for (Attribute a : set.getAttributes()) {
							if ("android:id".equals(a.getName()) || "id".equals(a.getName())) {
								synced.add(new Attribute("android:id", "@+id/" + liveId));
								hadId = true;
							} else {
								synced.add(a);
							}
						}
						if (!hadId) {
							synced.add(new Attribute("android:id", "@+id/" + liveId));
						}
						attributeStore.put(child, synced);
					}
				}
			}
			count++;
			
			if (child instanceof ViewGroup && !isOpaqueForRebuild(child)) {
				count += rebuildRegistryFromCanvas((ViewGroup) child, depth + 1);
			}
		}
		return count;
	}
	
	private String rebuildDim(int px) {
		if (px == ViewGroup.LayoutParams.MATCH_PARENT) return "match_parent";
		if (px == ViewGroup.LayoutParams.WRAP_CONTENT) return "wrap_content";
		return Math.round(px / getResources().getDisplayMetrics().density) + "dp";
	}

	/** Strip @+id/ or @id/ prefix from an android:id attribute value. */
	private static String stripIdPrefix(String raw) {
		if (raw == null) return null;
		String v = raw.trim();
		if (v.startsWith("@+id/")) return v.substring(5);
		if (v.startsWith("@id/")) return v.substring(4);
		return v;
	}
	
	private boolean isOpaqueForRebuild(View v) {
		if (v instanceof com.google.android.material.textfield.TextInputLayout) return true;
		try {
			if (com.nexusteam.internal.os.layouteditor.EditorDragController
			.isLeafOrOpaqueContainer(v)) return true;
		} catch (Throwable ignore) {}
		return v instanceof com.google.android.material.tabs.TabLayout
		|| v instanceof com.google.android.material.bottomnavigation.BottomNavigationView
		|| v instanceof com.google.android.material.navigation.NavigationView
		|| v instanceof android.widget.AdapterView
		|| v instanceof androidx.recyclerview.widget.RecyclerView
		|| v instanceof androidx.viewpager2.widget.ViewPager2
		|| v instanceof com.nexusteam.blacklogics.sdk.widgets.PlaceholderWidget
		|| v instanceof android.webkit.WebView
		|| v instanceof android.widget.VideoView;
	}
	
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		registerXmlSyncReceiverInternal();
		post(new Runnable() {
			@Override public void run() {
				try { recoverRegistryIfNeeded(); } catch (Throwable t) {
					Log.w("ViewEditor", "auto registry recovery failed", t);
				}
			}
		});
	}
	
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		unregisterXmlSyncReceiverInternal();
	}
	
	
	public void registerXmlSyncReceiver() {
		registerXmlSyncReceiverInternal();
	}
	
	public void unregisterXmlSyncReceiver() {
		unregisterXmlSyncReceiverInternal();
	}
	
	private void registerXmlSyncReceiverInternal() {
		if (xmlSyncReceiver != null) return;
		xmlSyncReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String xml = intent.getStringExtra(EXTRA_XML);
				if (xml == null || xml.trim().isEmpty()) return;
				loadFromXmlString(xml);
				saveLayout(getCurrentActivityName());
			}
		};
		LocalBroadcastManager.getInstance(getContext())
		.registerReceiver(xmlSyncReceiver, new IntentFilter(ACTION_XML_SYNC));
	}
	
	private void unregisterXmlSyncReceiverInternal() {
		if (xmlSyncReceiver != null) {
			LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(xmlSyncReceiver);
			xmlSyncReceiver = null;
		}
	}
	
	private void resetIdManagerInPlace() {
		if (idManager == null) {
			idManager = new IdManager();
		} else {
			idManager.clearAllIds();
		}
		// Keep XmlGenerator pointed at the live IdManager so save never writes stale IDs
		if (xmlGenerator != null) {
			xmlGenerator.setIdManager(idManager);
		}
		// FAB id must survive registry resets immediately (not only on the next frame)
		try { syncEditorFabRegistry(); } catch (Throwable ignore) {}
		post(new Runnable() {
			@Override public void run() { syncEditorFabRegistry(); }
		});
	}
	
	private IdManager snapshotIdManager(IdManager src) {
		IdManager copy = new IdManager();
		if (src == null) return copy;
		try {
			for (java.util.Map.Entry<View, String> e
			: new ArrayList<java.util.Map.Entry<View, String>>(src.mViewIdMap.entrySet())) {
				copy.mViewIdMap.put(e.getKey(), e.getValue());
				copy.putReverseForRestore(e.getValue(), e.getKey());
			}
		} catch (Throwable ignore) {}
		return copy;
	}
	
	private void restoreIdManagerFromOld() {
		if (oldIdManager == null) return;
		idManager.clearAllIds();
		try {
			for (java.util.Map.Entry<View, String> e
			: new ArrayList<java.util.Map.Entry<View, String>>(oldIdManager.mViewIdMap.entrySet())) {
				idManager.mViewIdMap.put(e.getKey(), e.getValue());
				idManager.putReverseForRestore(e.getValue(), e.getKey());
			}
		} catch (Throwable ignore) {}
		try { syncEditorFabRegistry(); } catch (Throwable ignore) {}
	}
	
	public void loadFromXmlString(String xml) {
		if (xml == null || xml.trim().isEmpty()) return;
		editorLayout.removeAllViews();
		resetIdManagerInPlace();
		attributeStore.clear();
		try {
			new XmlLoader(this).loadFromXml(xml);
		} catch (Exception e) {
			Log.e("ViewEditor", "loadFromXmlString failed: " + e.getMessage(), e);
		}
	}
	
	
	public void setOnWidgetAddListener(OnWidgetAdd listener) {
		this.onWidgetAddListener = listener;
	}
	
	public void setScId(String scId) {
		String newId = scId != null ? scId.trim() : "";
		String oldId = SC_ID != null ? SC_ID.trim() : "";
		if (!newId.isEmpty() && !newId.equals(oldId)) {
			Log.i("ViewEditor", "SC_ID change " + oldId + " → " + newId + " — clearing project state");
			clearForProjectSwitch();
		}
		SC_ID = newId;
	}
	
	public void setDesignActivity(DesignActivity designActivity) {
		this.designActivity = designActivity;
		refreshUndoRedoButtons();
	}
	
	public void setPath(String savePath) {
		SAVE_PATH = savePath;
	}
	
	public void setCurrentViewGroup(ViewGroup viewGroup) {
		this.currentViewGroup = viewGroup;
	}
	
	public void setAttributesContainer(LinearLayoutCompat container) {
		this.attributesContainer = container;
	}
	
	
	@Override
	public void onWidgetClick(View view) {
		showAttributesDialog(view);
	}
	
	@Override
	public void onWidgetAttributes(View view, Attribute attribute) {
		if (view == null || attribute == null) return;
		saveAndApplyAttribute(view, attribute.getName(), attribute.getValue(), null);
	}
	
	
	private void initialize(Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.view_editor, this, true);
		
		stableUndoManager = StableUndoManager.getInstance();
		historyManager = HistoryManager.getInstance();
		viewBeanRegistry = new ViewBeanRegistry();
		autoSaveHandler = new android.os.Handler(android.os.Looper.getMainLooper());
		historyManager.setBridge(new HistoryManager.AttributeApplyBridge() {
			@Override
			public void applyAttributeValue(android.view.View view, String attrName, String value) {
				AttributeSet set = attributeStore.getOrCreate(view);
				applyAttrValue(view, set, attrName, value, null);
			}
			@Override
			public AttributeStore getAttributeStore() { return attributeStore; }
			@Override
			public IdManager getIdManager() { return idManager; }
			@Override
			public ViewBeanRegistry getBeanRegistry() { return viewBeanRegistry; }
		});
		historyManager.setListener(new HistoryManager.OnStackChangedListener() {
			@Override
			public void onStackChanged(boolean canUndo, boolean canRedo) {
				refreshUndoRedoButtons();
			}
		});
		
		lin_toolbar = view.findViewById(R.id.lin_toolbar);
		collection_list = view.findViewById(R.id.collection_list);
		img_views = view.findViewById(R.id.img_views);
		editorLayout = view.findViewById(R.id.editorLayout);
		disableCanvasStateRestore();
		editorCanvasViewport = view.findViewById(R.id.editorCanvasViewport);
		try {
			editorCanvasFrame = view.findViewById(R.id.editorCanvasFrame);
		} catch (Throwable ignore) {
			editorCanvasFrame = null;
		}
		tvCanvasEmpty = view.findViewById(R.id.tv_canvas_empty);
		setContainerBorderRoot(editorLayout);
		setupCanvasScale();
		img_add_view = view.findViewById(R.id.img_add_view);
		listview_widgets = view.findViewById(R.id.listview_widgets);
		tv_view_name = view.findViewById(R.id.tv_view_name);
		phone_action_bar = view.findViewById(R.id.phone_action_bar);
		phone_status_bar = view.findViewById(R.id.linear13);
		customNav = view.findViewById(R.id.customNav);
		widgetPalette = view.findViewById(R.id.widgetPalette);
		collectionPalette = view.findViewById(R.id.collectionPalette);
		
		img_undo = null;
		img_redo = null;
		img_grid = null;
		img_snap = null;
		
		injectUndoRedoButtons(context);
		
		vib = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		
		uiHelpers = new EditorUiHelpers(context);
		
		setupToolbarListeners();
		initializeEditorComponents(context);
		applyCanvasEditorTheme(context);
		initializeCollaborators(context);
		
		updateCanvasEmptyHint();
		
		MobileAds.initialize(context);
		List<String> testDeviceIds = Arrays.asList("D4516BAC0B080049F0FABC1AD69374C4");
		MobileAds.setRequestConfiguration(
		new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build());
	}
	
	
	private void applyCanvasEditorTheme(Context context) {
		if (context == null || editorLayout == null) return;
		try {
			applyThemeColorsToWidgets(editorLayout, 0);
		} catch (Exception e) {
			Log.w("ViewEditor", "applyCanvasEditorTheme failed", e);
		}
	}
	
	private void applyThemeColorsToWidgets(ViewGroup parent, int depth) {
		if (parent == null || depth > 40) return;
		
		final int onSurface = 0xDE000000;
		final int outline = 0x33888888;
		
		for (int i = 0; i < parent.getChildCount(); i++) {
			View child = parent.getChildAt(i);
			if (child == null || child == placeHolder || child == selectionActionBar) continue;
			
			if (child instanceof ViewGroup && !isOpaqueForRebuild(child)) {
				try {
					boolean hasExplicitBg = false;
					if (attributeStore != null) {
						AttributeSet set = attributeStore.get(child);
						if (set != null && (set.getAttribute("android:background") != null
						|| set.getAttribute("app:cardBackgroundColor") != null
						|| set.getAttribute("app:backgroundTint") != null)) {
							hasExplicitBg = true;
						}
					}
					if (!hasExplicitBg && child.getBackground() == null) {
						int dp = (int) SketchwareUtil.getDip(getContext(), 1);
						child.setBackground(UiUtils.createStrokedBackground(0, outline, dp));
					}
				} catch (Exception ignored) {}
				applyThemeColorsToWidgets((ViewGroup) child, depth + 1);
			}
			
			if (child instanceof TextView) {
				TextView tv = (TextView) child;
				try {
					AttributeSet set = attributeStore != null ? attributeStore.get(child) : null;
					boolean hasExplicit = set != null && set.getAttribute("android:textColor") != null;
					if (!hasExplicit) {
						int c = tv.getCurrentTextColor();
						int avg = (((c >> 16) & 0xFF) + ((c >> 8) & 0xFF) + (c & 0xFF)) / 3;
						if (avg > 180) {
							tv.setTextColor(onSurface);
						}
					}
				} catch (Exception ignored) {}
			}
			
			try {
				com.nexusteam.blacklogics.sdk.utils.DynamicWidgetFactory
				.applyCanvasLightDefaults(child);
			} catch (Throwable ignore) {}
		}
	}
	
	private void injectUndoRedoButtons(Context context) {
		img_undo = null;
		img_redo = null;
	}
	
	private void initializeCollaborators(Context context) {
		attributeApplier = new AttributeApplier(context, attributeStore,
		new AttributeApplier.MessageSink() {
			@Override public void showMessage(String msg) { ViewEditor.this.showMessage(msg); }
		},
		new AttributeApplier.WidgetConversionHandler() {
			@Override public void handleWidgetConversion(View view, String value) {
				ViewEditor.this.handleWidgetConversion(view, value);
			}
			@Override public String getStringResourceValue(String scId, String resourceName) {
				return ViewEditor.this.getStringResourceValue(scId, resourceName);
			}
			@Override public Object createObject(Class<?> cls) {
				return ViewEditor.this.createObject(cls);
			}
			@Override public ViewGroup.LayoutParams createCompatibleLayoutParams(View view, int w, int h) {
				return uiHelpers.createCompatibleLayoutParams(view, w, h);
			}
			@Override public int parseDimension(String value) {
				return uiHelpers.parseDimension(value);
			}
		});
		
		attributeDialogs = new AttributeDialogs(uiHelpers, new AttributeDialogs.Host() {
			@Override public Context getContext() { return ViewEditor.this.getContext(); }
			@Override public void showMessage(String msg) { ViewEditor.this.showMessage(msg); }
			@Override public void applyAttribute(View view, Attribute attribute) {
				ViewEditor.this.applyAttribute(view, attribute);
			}
			@Override public void applyAttribute(View view, Attribute attribute, HashMap<String, Object> map) {
				ViewEditor.this.applyAttribute(view, attribute, map);
			}
			@Override public AttributeSet getOrCreateAttributeSet(View view) {
				return attributeStore.getOrCreate(view);
			}
			@Override public void pushSingleAttributeUndoAndApply(View view, String attrName,
			String newValue, HashMap<String, Object> attributeMeta) {
				ViewEditor.this.saveAndApplyAttribute(view, attrName, newValue, attributeMeta);
			}
			@Override public void pushMultiAttributeUndoAndApply(View view, String[] attrNames,
			String[] rawNewValues, String actionLabel, boolean dimensionSuffix) {
				ViewEditor.this.pushMultiAttributeUndoAndApply(view, attrNames, rawNewValues,
				actionLabel, dimensionSuffix);
			}
			@Override public Complex getProjectComplex() { return ViewEditor.this.getProjectComplex(); }
			@Override public void handleWidgetConversion(View view, String customClassName) {
				ViewEditor.this.handleWidgetConversion(view, customClassName);
			}
			@Override public AttributeSet cloneAttributeSet(AttributeSet source) {
				return ViewEditor.this.cloneAttributeSet(source);
			}
			@Override public void applyConvertedAttributeSet(View view, AttributeSet set) {
				ViewEditor.this.applyConvertedAttributeSet(view, set);
			}
			@Override public void pushConvertWidgetUndo(View view, AttributeSet beforeSet, AttributeSet afterSet) {
				ViewEditor.this.pushConvertWidgetUndo(view, beforeSet, afterSet);
			}
		});
		
		xmlGenerator = new XmlGenerator(context, attributeStore, placeHolder, SC_ID);
		if (idManager != null) {
			xmlGenerator.setIdManager(idManager);
		}
		
		backupManager = new LayoutBackupManager(context, SC_ID, SAVE_PATH, xmlGenerator,
		new LayoutBackupManager.RestoreCallback() {
			@Override public void loadFromXmlString(String xml) { ViewEditor.this.loadFromXmlString(xml); }
			@Override public void saveLayoutAsJson(String layoutName) throws Exception {
				ViewEditor.this.saveLayoutAsJson(layoutName);
			}
			@Override public void showMessage(String message) { ViewEditor.this.showMessage(message); }
		});
		
		String projectPathBase = FileUtil.getExternalStorageDir() + "/.blacklogics/data/" + SC_ID;
		
		layoutPersistence = new LayoutPersistence(context, attributeStore, xmlGenerator,
		backupManager, new LayoutPersistence.ViewRebuildDelegate() {
			@Override public View createPlaceholderOrStandardView(String tag) {
				return ViewEditor.this.createPlaceholderOrStandardView(tag);
			}
			@Override public void attachEditorClickListener(View view) {
				ViewEditor.this.attachEditorClickListener(view);
			}
			@Override public void attachDragAndClickListener(View view) {
				ViewEditor.this.attachDragAndClickListener(view);
			}
			@Override public View.OnDragListener getDragListener() {
				return dragController != null ? dragController.getDragListener() : null;
			}
			@Override public void applyContainerStroke(View view) {
				ViewEditor.this.applyContainerStroke(view);
			}
			@Override public void applyAllAttributesAfterLoad() {
				ViewEditor.this.applyAllAttributesAfterLoad();
			}
			@Override public void addNewId(View view, String id) {
				idManager.addNewId(view, id);
			}
			@Override public String getId(View view) {
				return idManager.getId(view);
			}
			@Override public void removeProbe(View probe) {
				idManager.remove(probe);
			}
			@Override public Complex getProjectComplex() {
				return ViewEditor.this.getProjectComplex();
			}
		}, placeHolder, projectPathBase);
		
		widgetDropHandler = new WidgetDropHandler(new WidgetDropHandler.Host() {
			@Override public Context getContext() { return ViewEditor.this.getContext(); }
			@Override public AttributeStore getAttributeStore() { return attributeStore; }
			@Override public IdManager getIdManager() { return idManager; }
			@Override public EditorDragController getDragController() { return dragController; }
			@Override public StableUndoManager getStableUndoManager() { return stableUndoManager; }
			@Override public String getCurrentActivityName() { return ViewEditor.this.getCurrentActivityName(); }
			@Override public void showMessage(String msg) { ViewEditor.this.showMessage(msg); }
			@Override public void applyDefaultViewSetup(View view) { ViewEditor.this.applyDefaultViewSetup(view); }
			@Override public void ensureDefaultLayoutParams(View view) { ViewEditor.this.ensureDefaultLayoutParams(view); }
			@Override public void applyContainerStroke(View view) { ViewEditor.this.applyContainerStroke(view); }
			@Override public void applyAttribute(View view, Attribute attribute) {
				ViewEditor.this.applyAttribute(view, attribute);
			}
			@Override public void addViewWithUndo(View view, ViewGroup parent, int index) {
				ViewEditor.this.addViewWithUndo(view, parent, index);
			}
			@Override public void setDefaultTextSize(View view) { ViewEditor.this.setDefaultTextSize(view); }
			@Override public void refreshUndoRedoButtons() { ViewEditor.this.refreshUndoRedoButtons(); }
			@Override public void notifyWidgetAdded(View widget, ViewGroup parent) {
				// Register ViewBean + sync attrs so collection / export see full attributes
				registerViewBeanFromLive(widget);
				if (onWidgetAddListener != null) onWidgetAddListener.onWidgetAdded(widget, parent);
			}
			@Override public ViewGroup getEditorLayout() { return editorLayout; }
		});
		
		listenerCatalog = new ListenerCatalog(new ListenerCatalog.CustomViewCheck() {
			@Override public boolean hasCustomViewSet(View view) {
				return attributeStore.hasCustomViewSet(view);
			}
		}, context);
	}
	
	
	private void setupToolbarListeners() {
		img_views.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showViewSelectorDialog();
			}
		});
		img_views.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				showFabToggleMenu(v);
				return true;
			}
		});
		
		img_add_view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ViewEditor.this.showMaterialWidgetDialog(getContext(), viewsList);
			}
		});
		
		if (img_undo != null) {
			img_undo.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (canUndo()) {
						if (undo()) {
							showMessage("↶ Undone");
							if (vib != null) vib.vibrate(20);
						}
					} else {
						showMessage("Nothing to undo");
					}
				}
			});
		}
		
		if (img_redo != null) {
			img_redo.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (canRedo()) {
						if (redo()) {
							showMessage("↷ Redone");
							if (vib != null) vib.vibrate(20);
						}
					} else {
						showMessage("Nothing to redo");
					}
				}
			});
		}
		
		if (img_grid != null) {
			img_grid.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					boolean newState = !isGridVisible();
					setGridVisible(newState);
					img_grid.setAlpha(newState ? 1f : 0.4f);
				}
			});
		}
		
		if (img_snap != null) {
			img_snap.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					boolean newState = !dragController.isSnapToGrid();
					dragController.setSnapToGrid(newState);
					img_snap.setAlpha(newState ? 1f : 0.4f);
					showMessage("Snap " + (newState ? "ON" : "OFF"));
				}
			});
		}
		
		editorLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				hideProperties();
				unselectSelectedWidget();
			}
		});
	}
	
	
	private void initializeEditorComponents(Context context) {
		placeHolder = new View(context);
		placeHolder.setTag("drop_insert_indicator");
		placeHolder.setLayoutParams(new ViewGroup.LayoutParams(
		(int) SketchwareUtil.getDip(context, 70),
		(int) SketchwareUtil.getDip(context, 40)));
		placeHolder.setMinimumHeight((int) SketchwareUtil.getDip(context, 40));
		placeHolder.setMinimumWidth((int) SketchwareUtil.getDip(context, 70));
		placeHolder.setBackgroundColor(0x88757575);
		placeHolder.setAlpha(0.85f);
		
		viewEditorDrag = new ViewEditorDrag(context, placeHolder);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			int surface = 0xFFFFFFFF;
			try {
				TypedValue tv = new TypedValue();
				if (context.getTheme().resolveAttribute(
				android.R.attr.colorBackground, tv, true)) {
					surface = tv.resourceId != 0
					? ContextCompat.getColor(context, tv.resourceId) : tv.data;
				}
			} catch (Exception ignored) {}
			boolean lightSurface = ((surface >> 16) & 0xFF) > 0xA0
			&& ((surface >> 8) & 0xFF) > 0xA0
			&& (surface & 0xFF) > 0xA0;
			int flags = getSystemUiVisibility();
			if (lightSurface) {
				flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
					flags |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
				}
			} else {
				flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
					flags &= ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
				}
			}
			setSystemUiVisibility(flags);
			((Activity) context).getWindow().setStatusBarColor(surface);
			((Activity) context).getWindow().setNavigationBarColor(surface);
		}
		
		deleteImg = new ImageView(context);
		deleteImg.setImageResource(R.drawable.ic_delete_white);
		deleteImg.setColorFilter(0xFF757575, PorterDuff.Mode.SRC_ATOP);
		deleteImg.setVisibility(View.GONE);
		
		dragController = new EditorDragController(context, editorLayout, placeHolder);
		dragController.setDeleteTarget(deleteImg);
		dragController.setCallback(buildDragCallback());
		editorLayout.setOnDragListener(dragController.getDragListener());
		
		setPreviewTouchCallback(new EditorLayout.PreviewTouchCallback() {
			@Override public void onWidgetTapped(View widget) { handlePreviewTap(widget); }
			@Override public void onWidgetLongPressed(View widget) { handlePreviewLongPress(widget); }
		});
		
		dragListener = new DragListener();
		
		float dp = context.getResources().getDisplayMetrics().density;
		android.widget.FrameLayout.LayoutParams deleteParams = new android.widget.FrameLayout.LayoutParams(
		(int) (56 * dp), (int) (56 * dp));
		deleteParams.gravity = Gravity.BOTTOM | Gravity.START;
		deleteParams.bottomMargin = (int) (24 * dp);
		deleteParams.leftMargin = (int) (16 * dp);
		
		GradientDrawable deleteBg = new GradientDrawable();
		deleteBg.setShape(GradientDrawable.OVAL);
		deleteBg.setColor(0xEEFFFFFF);
		deleteBg.setStroke((int) dp, 0x33000000);
		deleteImg.setBackground(deleteBg);
		int delPad = (int) (12 * dp);
		deleteImg.setPadding(delPad, delPad, delPad, delPad);
		deleteImg.setLayoutParams(deleteParams);
		
		((ViewGroup) ((Activity) context).getWindow()
		.findViewById(Window.ID_ANDROID_CONTENT)).addView(deleteImg);
		
		View imgCopy = findViewById(R.id.img_copy);
		View imgImport = findViewById(R.id.img_import);
		UiUtils.addRipple(img_add_view);
		UiUtils.addRipple(img_views);
		if (imgCopy != null) {
			UiUtils.addRipple(imgCopy);
			imgCopy.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (selectedView != null || !selectedViews.isEmpty()) {
						copySelectedToClipboard();
					} else {
						showMessage("Pehle koi widget select karo");
					}
				}
			});
			imgCopy.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					if (widgetClipboard != null) {
						pasteFromClipboard(selectedView);
					} else {
						showMessage("Clipboard empty hai");
					}
					return true;
				}
			});
		}
		if (imgImport != null) UiUtils.addRipple(imgImport);
		
		viewsList = BuiltInWidgets.getAll();
		viewsList = injectPaletteCategories(viewsList);
		
		HashMap<String, ArrayList<HashMap<String, Object>>> attributesMap =
		BuiltInAttributes.getAll();
		Gson gson = new Gson();
		
		HashMap<String, ArrayList<HashMap<String, Object>>> parentAttributesMap = gson.fromJson(
		Utils.readFromAsset(context, "parent_dependent_attributes.json"),
		new TypeToken<HashMap<String, ArrayList<HashMap<String, Object>>>>() {}.getType());
		attributeStore.loadSchemas(attributesMap, parentAttributesMap);
		
		listview_widgets.setLayoutManager(new LinearLayoutManager(context));
		paletteAdapter = new WidgetPaletteAdapter(context, viewsList, uiHelpers,
		new WidgetPaletteAdapter.UnselectCallback() {
			@Override public void unselectSelectedWidget() {
				ViewEditor.this.unselectSelectedWidget();
			}
		});
		listview_widgets.setAdapter(paletteAdapter);
		
		lin_toolbar.setElevation(5f);
		
		customNav.addNavItem(R.drawable.ic_home, 101);
		customNav.addNavItem(R.drawable.ic_bookmark, 102);
		customNav.selectItem(101);
		customNav.setOnTabSelectedListener(buildNavBarListener());
		
		setupCollectionList();
		
		View root = editorLayout.getChildAt(0);
		if (root instanceof ViewGroup) {
			applyContainerStroke(root);
		}
	}
	
	
	private EditorDragController.DragCallback buildDragCallback() {
		return new EditorDragController.DragCallback() {
			
			@Override
			public void onDragStarted(View draggedView) {
				post(new Runnable() {
					@Override public void run() {
						hideSelectionActionBar();
						showDeleteImage();
						refreshUndoRedoButtons();
					}
				});
			}
			
			@Override
			public void onNewWidgetDropped(Object widgetData, ViewGroup destination, DragEvent event) {
				// Sketchware-style collection drop first
				if (SkCollectionIntegration.handleDrop(widgetData, destination, buildCollectionHost())) {
					refreshUndoRedoButtons();
					return;
				}
				widgetDropHandler.handleDropNewWidget(destination, widgetData, event);
				refreshUndoRedoButtons();
			}
			
			@Override
			public void onExistingWidgetMoved(View draggedView, ViewGroup newParent, int insertIndex) {
				ViewGroup oldParent = dragController.getDragSourceParent();
				int oldIndex = dragController.getDragSourceIndex();
				if (oldParent == null) return;
				pushStableCommand(getCurrentActivityName(),
				new StableUndoManager.MoveViewCommand(
				draggedView, oldParent, oldIndex, newParent, insertIndex));
				refreshUndoRedoButtons();
			}
			
			@Override
			public void onWidgetDeleted(View draggedView) {
				if (draggedView == null) return;
				draggedView.setAlpha(1f);
				
				ViewGroup parent = dragController.getDragSourceParent();
				int idx = dragController.getDragSourceIndex();
				if (parent == null && draggedView.getParent() instanceof ViewGroup) {
					parent = (ViewGroup) draggedView.getParent();
					idx = parent.indexOfChild(draggedView);
				}
				if (parent == null && draggedView.getParent() instanceof ViewGroup) {
					parent = (ViewGroup) draggedView.getParent();
					idx = parent.indexOfChild(draggedView);
				}
				if (parent == null) {
					idManager.remove(draggedView, true);
					attributeStore.remove(draggedView, true);
					refreshUndoRedoButtons();
					return;
				}
				
				if (!isUndoRedoInProgress) {
					pushStableCommand(getCurrentActivityName(),
					new StableUndoManager.RemoveViewCommand(draggedView, parent, idx));
				}
				try {
					if (draggedView.getParent() == parent) {
						parent.removeView(draggedView);
					} else if (draggedView.getParent() instanceof ViewGroup) {
						((ViewGroup) draggedView.getParent()).removeView(draggedView);
					}
				} catch (Exception e) {
					Log.w("ViewEditor", "delete removeView failed", e);
				}
				idManager.remove(draggedView, true);
				attributeStore.remove(draggedView, true);
				refreshUndoRedoButtons();
			}
			
			@Override
			public void onDragEnded(boolean success, View draggedView) {
				hideDeleteImage();
				if (success) {
					vib.vibrate(100);
				} else if (draggedView != null && draggedView.getParent() == null) {
					idManager.remove(draggedView, true);
					attributeStore.remove(draggedView, true);
				}
				refreshUndoRedoButtons();
			}
		};
	}
	
	
	private NavBar.OnTabSelectedListener buildNavBarListener() {
		return new NavBar.OnTabSelectedListener() {
			@Override
			public void onTabSelected(int id) {
				if (id == 101) {
					animateTabSwitch(widgetPalette, collectionPalette);
				} else if (id == 102) {
					animateTabSwitch(collectionPalette, widgetPalette);
				}
			}
		};
	}
	
	private void animateTabSwitch(final View showView, final View hideView) {
		showView.setAlpha(0f);
		showView.setVisibility(View.VISIBLE);
		showView.animate().alpha(1f).setDuration(200);
		hideView.animate().alpha(0f).setDuration(200)
		.withEndAction(new Runnable() {
			@Override
			public void run() {
				hideView.setVisibility(View.GONE);
			}
		});
	}
	
	
	private void showViewSelectorDialog() {
		recoverRegistryIfNeeded();
		ensureEditorFabRegistered();
		ArrayList<String> idsList = idManager.getIds();
		final String[] ids = idsList.toArray(new String[0]);
		new AlertDialog.Builder(getContext())
		.setTitle("View select karo")
		.setItems(ids, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				View target = idManager.getView(ids[which]);
				if (target != null) showAttributesDialog(target);
			}
		})
		.create()
		.show();
	}
	
	
	public void showAttributesDialog(final View view) {
		recoverRegistryIfNeeded();
		if (view != null && view == editorFabView) syncEditorFabRegistry();
		final ArrayList<HashMap<String, Object>> attributesList =
		new ArrayList<HashMap<String, Object>>();
		attributeStore.buildAttributeListForView(view, attributesList);
		applyCommonFilter(attributesList);
		
		final ArrayList<HashMap<String, Object>> listenersList =
		listenerCatalog.buildListenersForView(view);
		
		highlightWidget(view);
		showProperties();
		
		final com.google.android.material.bottomsheet.BottomSheetDialog bottomSheetDialog =
		new com.google.android.material.bottomsheet.BottomSheetDialog(getContext());
		bottomSheetDialog.setCancelable(true);
		bottomSheetDialog.setCanceledOnTouchOutside(true);
		
		View inflated = LayoutInflater.from(getContext())
		.inflate(R.layout.attributes_bottom_sheet, null);
		bottomSheetDialog.setContentView(inflated);
		
		if (bottomSheetDialog.getBehavior() != null) {
			bottomSheetDialog.getBehavior().setState(
			com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED);
		}
		inflated.setClipToOutline(true);
		
		viewItems = inflated.findViewById(R.id.viewItems);
		final RecyclerView rvAttributes = inflated.findViewById(R.id.rv_attributes);
		final RecyclerView rvListeners = inflated.findViewById(R.id.listeners);
		final ImageView imgEditId = inflated.findViewById(R.id.img_edit_id);
		final ImageView imgCollection = inflated.findViewById(R.id.collection);
		final ImageView imgCommonAttrs = inflated.findViewById(R.id.img_common_attributes);
		final ImageView imgCustomAttrs = inflated.findViewById(R.id.img_custom);
		final ImageView imgDuplicate = null;
		final ImageView imgWrapIn = null;
		final ImageView imgMarginEditor = null;
		final com.google.android.material.tabs.TabLayout tabLayout =
		inflated.findViewById(R.id.attributesOrListener);
		final LinearLayout attributesTab = inflated.findViewById(R.id.attributesTab);
		final LinearLayout eventTab = inflated.findViewById(R.id.eventTab);
		
		viewItems.setIdManager(idManager);
		viewItems.forceRefresh();
		
		String currentViewId = idManager.getId(view);
		if (currentViewId != null) viewItems.selectIdSilently(currentViewId);
		
		final View[] sheetTarget = new View[] { view };
		
		viewItems.setOnViewChangeListener(
		new OnViewChangeListener() {
			@Override
			public void onViewChanged(View newView, String id) {
				if (newView == null) return;
				if (sheetTarget[0] == newView && selectedView == newView) return;
				highlightWidget(newView);
				sheetTarget[0] = selectedView != null ? selectedView : newView;
				attributesList.clear();
				attributeStore.buildAttributeListForView(sheetTarget[0], attributesList);
				applyCommonFilter(attributesList);
				if (rvAttributes.getAdapter() instanceof AttributeRecyclerAdapter) {
					((AttributeRecyclerAdapter) rvAttributes.getAdapter()).updateData(attributesList);
				}
				String newId = idManager.getId(sheetTarget[0]);
				if (newId != null) viewItems.selectIdSilently(newId);
			}
		});
		
		if (imgCollection != null) {
			imgCollection.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					bottomSheetDialog.dismiss();
					View tgt = sheetTarget[0] != null ? sheetTarget[0] : view;
					if (tgt == null) {
						showMessage("Pehle koi widget select karo");
						return;
					}
					SkCollectionIntegration.showAddDialog(
						getContext(),
						tgt,
						attributeStore,
						idManager != null ? idManager : IdManager.getInstance(),
						viewBeanRegistry,
						new SkAddToCollectionDialog.Callback() {
							@Override
							public void onSaved(String collectionName) {
								showMessage("Saved: " + collectionName);
								refreshCollectionTree();
							}
							@Override
							public void onCancelled() {}
						}
					);
				}
			});
		}
		
		if (imgDuplicate != null) {
			imgDuplicate.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					bottomSheetDialog.dismiss();
					duplicateWidget(view);
				}
			});
		}
		
		if (imgWrapIn != null) {
			imgWrapIn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					bottomSheetDialog.dismiss();
					showWrapInContainerDialog(view);
				}
			});
		}
		
		if (imgMarginEditor != null) {
			imgMarginEditor.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					bottomSheetDialog.dismiss();
					showMarginEditorDialog(view);
				}
			});
		}
		
		tabLayout.removeAllTabs();
		tabLayout.addTab(tabLayout.newTab().setText("attributes"));
		tabLayout.addTab(tabLayout.newTab().setText("events"));
		
		LinearLayoutManager attrLm = new LinearLayoutManager(
		getContext(), LinearLayoutManager.HORIZONTAL, false);
		rvAttributes.setLayoutManager(attrLm);
		AttributeRecyclerAdapter attrAdapter =
		new AttributeRecyclerAdapter(getContext(), attributesList);
		rvAttributes.setAdapter(attrAdapter);
		
		LinearLayoutManager listenerLm = new LinearLayoutManager(
		getContext(), LinearLayoutManager.HORIZONTAL, false);
		rvListeners.setLayoutManager(listenerLm);
		ListenerRecyclerAdapter listenerAdapter =
		new ListenerRecyclerAdapter(getContext(), listenersList);
		rvListeners.setAdapter(listenerAdapter);
		
		tabLayout.addOnTabSelectedListener(
		new com.google.android.material.tabs.TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(com.google.android.material.tabs.TabLayout.Tab tab) {
				boolean isAttr = tab.getPosition() == 0;
				attributesTab.setVisibility(isAttr ? View.VISIBLE : View.GONE);
				eventTab.setVisibility(isAttr ? View.GONE : View.VISIBLE);
			}
			@Override
			public void onTabUnselected(com.google.android.material.tabs.TabLayout.Tab tab) {}
			@Override
			public void onTabReselected(com.google.android.material.tabs.TabLayout.Tab tab) {}
		});
		
		imgEditId.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final View tgt = sheetTarget[0] != null ? sheetTarget[0] : view;
				final android.widget.EditText et = new android.widget.EditText(getContext());
				et.setText(idManager.getId(tgt));
				new AlertDialog.Builder(getContext())
				.setTitle("ID edit karo")
				.setView(et)
				.setPositiveButton("Change", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface d, int w) {
						String newId = et.getText().toString().trim();
						if (!newId.isEmpty() && !isUndoRedoInProgress) {
							final String oldId = idManager.getId(tgt);
							applyIdChangeWithAttributeSync(tgt, newId);
							if (oldId != null && !oldId.equals(newId)) {
								pushStableCommand(getCurrentActivityName(),
								new StableUndoManager.IdChangeCommand(
								tgt, oldId, newId,
								new StableUndoManager.IdChangeCommand.IdManagerProxy() {
									@Override
									public void updateId(View v, String id) {
										applyIdChangeWithAttributeSync(v, id);
									}
								}));
								refreshUndoRedoButtons();
							}
						}
					}
				})
				.setNegativeButton("Cancel", null)
				.show();
			}
		});
		
		// Dimension icon = See All (full Edit Properties, schema from this view)
		if (imgCommonAttrs != null) {
			imgCommonAttrs.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					bottomSheetDialog.dismiss();
					View tgt = sheetTarget[0] != null ? sheetTarget[0] : view;
					android.app.Activity act = designActivity != null ? designActivity
							: (getContext() instanceof android.app.Activity
							? (android.app.Activity) getContext() : null);
					if (act != null) {
						try {
							registerEditPropertiesLiveListener(tgt);
							EditPropertiesLauncher.open(act, tgt, idManager, attributeStore);
						} catch (Exception e) {
							showCommonAttributesDialog(tgt);
						}
					} else {
						showCommonAttributesDialog(tgt);
					}
				}
			});
		}

		// Legacy hidden img_custom (gone in layout) — safe no-op if null/gone
		if (imgCustomAttrs != null) {
			imgCustomAttrs.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					bottomSheetDialog.dismiss();
					View tgt = sheetTarget[0] != null ? sheetTarget[0] : view;
					showCustomAttributesDialog(tgt);
				}
			});
		}
		
		attrAdapter.setOnItemClickListener(
		new AttributeRecyclerAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(int position) {
				View tgt = sheetTarget[0] != null ? sheetTarget[0] : view;
				if (position >= 0 && position < attributesList.size()) {
					showAttributeInputDialog(tgt, attributesList.get(position));
				}
			}
		});
		
		listenerAdapter.setOnItemClickListener(
		new AttributeRecyclerAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(int position) {
				View tgt = sheetTarget[0] != null ? sheetTarget[0] : view;
				if (position >= 0 && position < listenersList.size()) {
					openLogicEditorForListener(tgt, listenersList.get(position));
				}
			}
		});
		
		bottomSheetDialog.show();
		
		attachDragAndClickListener(view);
		if (view instanceof ViewGroup) {
			view.setMinimumHeight((int) SketchwareUtil.getDip(getContext(), 30));
		}
	}
	
	public void showAttributeInputDialog(View view, HashMap<String, Object> attribute) {
		attributeDialogs.showAttributeInputDialog(view, attribute);
	}
	
	public void showMarginEditorDialog(View view) {
		attributeDialogs.showMarginEditorDialog(view);
	}
	
	public void showPaddingEditorDialog(View view) {
		attributeDialogs.showPaddingEditorDialog(view);
	}
	
	public void showCommonAttributesDialog(View view) {
		attributeDialogs.showCommonAttributesDialog(view);
	}
	
	public void showCustomAttributesDialog(View view) {
		attributeDialogs.showCustomAttributesDialog(view);
	}
	
	private void showWrapInContainerDialog(final View view) {
		attributeDialogs.showWrapInContainerDialog(view, new AttributeDialogs.WrapCallback() {
			@Override
			public void onContainerChosen(View view, int containerType) {
				widgetDropHandler.wrapViewInContainer(view, containerType);
			}
		});
	}
	
	public void duplicateWidget(View original) {
		widgetDropHandler.duplicateWidget(original);
	}
	
	
	public void showContextMenu(final View anchor) {
		final View target = selectedView != null ? selectedView : anchor;
		if (target == null) return;
		
		android.widget.PopupMenu popup = new android.widget.PopupMenu(
		getContext(), anchor != null ? anchor : target);
		android.view.Menu menu = popup.getMenu();
		
		menu.add(0, 1, 1, "Duplicate");
		menu.add(0, 2, 2, "Copy");
		menu.add(0, 3, 3, "Paste" + (widgetClipboard != null ? "" : " (empty)"));
		menu.add(0, 4, 4, "Delete");
		menu.add(0, 5, 5, "Wrap in…");
		menu.add(0, 6, 6, "Bring to Front");
		menu.add(0, 7, 7, "Send to Back");
		if (selectedViews.size() > 1) {
			menu.add(0, 8, 8, "Align Left");
			menu.add(0, 9, 9, "Align Center H");
			menu.add(0, 10, 10, "Align Right");
			menu.add(0, 11, 11, "Align Top");
			menu.add(0, 12, 12, "Align Center V");
			menu.add(0, 13, 13, "Align Bottom");
		}
		menu.add(0, 14, 14, "Select only this");
		menu.add(0, 15, 15, "Add to multi-select");
		
		popup.setOnMenuItemClickListener(new android.widget.PopupMenu.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(android.view.MenuItem item) {
				int id = item.getItemId();
				switch (id) {
					case 1:
					for (View v : new ArrayList<View>(selectedViews)) {
						duplicateWidget(v);
					}
					return true;
					case 2:
					copySelectedToClipboard();
					return true;
					case 3:
					pasteFromClipboard(target);
					return true;
					case 4:
					deleteSelectedViews();
					return true;
					case 5:
					showWrapInContainerDialog(target);
					return true;
					case 6:
					bringToFront(target);
					return true;
					case 7:
					sendToBack(target);
					return true;
					case 8:
					alignSelected(Align.LEFT);
					return true;
					case 9:
					alignSelected(Align.CENTER_H);
					return true;
					case 10:
					alignSelected(Align.RIGHT);
					return true;
					case 11:
					alignSelected(Align.TOP);
					return true;
					case 12:
					alignSelected(Align.CENTER_V);
					return true;
					case 13:
					alignSelected(Align.BOTTOM);
					return true;
					case 14:
					highlightWidget(target, false);
					return true;
					case 15:
					highlightWidget(target, true);
					showMessage("Multi-select: " + selectedViews.size() + " views");
					return true;
					default:
					return false;
				}
			}
		});
		popup.show();
	}
	
	private enum Align { LEFT, CENTER_H, RIGHT, TOP, CENTER_V, BOTTOM }
	
	private void alignSelected(Align mode) {
		if (selectedViews.size() < 2) return;
		View first = null;
		for (View v : selectedViews) {
			first = v;
			break;
		}
		if (first == null || !(first.getParent() instanceof ViewGroup)) return;
		ViewGroup parent = (ViewGroup) first.getParent();
		
		java.util.List<View> siblings = new ArrayList<View>();
		for (View v : selectedViews) {
			if (v.getParent() == parent) siblings.add(v);
		}
		if (siblings.size() < 2) {
			showMessage("Align ke liye same parent ke views select karo");
			return;
		}
		
		int refLeft = first.getLeft();
		int refTop = first.getTop();
		int refRight = first.getRight();
		int refBottom = first.getBottom();
		int refCenterX = (refLeft + refRight) / 2;
		int refCenterY = (refTop + refBottom) / 2;
		
		for (View v : siblings) {
			if (v == first) continue;
			ViewGroup.LayoutParams lp = v.getLayoutParams();
			if (lp instanceof ViewGroup.MarginLayoutParams) {
				ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) lp;
				int w = v.getWidth();
				int h = v.getHeight();
				switch (mode) {
					case LEFT:
					mlp.leftMargin = refLeft - parent.getPaddingLeft();
					break;
					case CENTER_H:
					mlp.leftMargin = refCenterX - w / 2 - parent.getPaddingLeft();
					break;
					case RIGHT:
					mlp.leftMargin = refRight - w - parent.getPaddingLeft();
					break;
					case TOP:
					mlp.topMargin = refTop - parent.getPaddingTop();
					break;
					case CENTER_V:
					mlp.topMargin = refCenterY - h / 2 - parent.getPaddingTop();
					break;
					case BOTTOM:
					mlp.topMargin = refBottom - h - parent.getPaddingTop();
					break;
				}
				v.setLayoutParams(mlp);
			}
		}
		parent.requestLayout();
		showMessage("Aligned " + siblings.size() + " views");
	}
	
	public void bringToFront(final View view) {
		if (view == null || !(view.getParent() instanceof ViewGroup)) return;
		final ViewGroup parent = (ViewGroup) view.getParent();
		final int oldIndex = parent.indexOfChild(view);
		if (oldIndex < 0 || oldIndex == parent.getChildCount() - 1) {
			showMessage("Already at front");
			return;
		}
		if (!isUndoRedoInProgress) {
			pushStableCommand(getCurrentActivityName(), new StableUndoManager.EditorCommand() {
				@Override
				public void execute() {
					parent.bringChildToFront(view);
					parent.invalidate();
				}
				@Override
				public void undo() {
					parent.removeView(view);
					int idx = Math.min(oldIndex, parent.getChildCount());
					parent.addView(view, idx);
					parent.invalidate();
				}
				@Override
				public String getDescription() { return "Bring to front"; }
			});
		}
		parent.bringChildToFront(view);
		parent.invalidate();
		refreshUndoRedoButtons();
		showMessage("Brought to front");
	}
	
	public void sendToBack(final View view) {
		if (view == null || !(view.getParent() instanceof ViewGroup)) return;
		final ViewGroup parent = (ViewGroup) view.getParent();
		final int oldIndex = parent.indexOfChild(view);
		if (oldIndex <= 0) {
			showMessage("Already at back");
			return;
		}
		if (!isUndoRedoInProgress) {
			pushStableCommand(getCurrentActivityName(), new StableUndoManager.EditorCommand() {
				@Override
				public void execute() {
					parent.removeView(view);
					parent.addView(view, 0);
					parent.invalidate();
				}
				@Override
				public void undo() {
					parent.removeView(view);
					int idx = Math.min(oldIndex, parent.getChildCount());
					parent.addView(view, idx);
					parent.invalidate();
				}
				@Override
				public String getDescription() { return "Send to back"; }
			});
		}
		parent.removeView(view);
		parent.addView(view, 0);
		parent.invalidate();
		refreshUndoRedoButtons();
		showMessage("Sent to back");
	}
	
	private void copySelectedToClipboard() {
		View src = selectedView;
		if (src == null && !selectedViews.isEmpty()) {
			src = selectedViews.iterator().next();
		}
		if (src == null) {
			showMessage("Koi view select nahi hai");
			return;
		}
		ClipboardWidget clip = new ClipboardWidget();
		clip.className = src.getClass().getName();
		AttributeSet set = attributeStore.get(src);
		if (set != null) {
			AttributeSet copy = new AttributeSet();
			for (Attribute a : set.getAttributes()) {
				if (!"android:id".equals(a.getName())) {
					copy.add(new Attribute(a.getName(), a.getValue()));
				}
			}
			clip.attributes = copy;
		} else {
			clip.attributes = new AttributeSet();
		}
		widgetClipboard = clip;
		showMessage("Copied: " + (idManager.getId(src) != null ? idManager.getId(src) : src.getClass().getSimpleName()));
	}
	
	private void pasteFromClipboard(View near) {
		if (widgetClipboard == null) {
			showMessage("Clipboard empty hai");
			return;
		}
		try {
			ViewGroup parent;
			int index = -1;
			if (near != null && near.getParent() instanceof ViewGroup) {
				parent = (ViewGroup) near.getParent();
				index = parent.indexOfChild(near) + 1;
			} else if (selectedView != null && selectedView.getParent() instanceof ViewGroup) {
				parent = (ViewGroup) selectedView.getParent();
				index = parent.indexOfChild(selectedView) + 1;
			} else {
				parent = editorLayout;
				index = parent.getChildCount();
			}
			
			View copy = null;
			try {
				copy = DynamicWidgetFactory.resolve(getContext(), widgetClipboard.className, null);
			} catch (Exception ignored) {}
			if (copy == null) {
				try {
					copy = (View) Class.forName(widgetClipboard.className)
					.getConstructor(Context.class)
					.newInstance(getContext());
				} catch (Exception e) {
					TextView tv = new TextView(getContext());
					tv.setText(widgetClipboard.className);
					copy = tv;
				}
			}
			if (copy == null) {
				showMessage("Paste failed: class not found");
				return;
			}
			
			applyDefaultViewSetup(copy);
			String newId = idManager.generateNewId(copy);
			idManager.addNewId(copy, newId);
			
			AttributeSet newSet = new AttributeSet();
			newSet.add(new Attribute("android:id", "@+id/" + newId));
			if (widgetClipboard.attributes != null) {
				for (Attribute a : widgetClipboard.attributes.getAttributes()) {
					newSet.add(new Attribute(a.getName(), a.getValue()));
				}
			}
			attributeStore.put(copy, newSet);
			
			ensureDefaultLayoutParams(copy);
			if (index < 0 || index > parent.getChildCount()) index = parent.getChildCount();
			parent.addView(copy, index);
			addViewWithUndo(copy, parent, index);
			
			for (Attribute a : newSet.getAttributes()) {
				applyAttribute(copy, a);
			}
			if (copy instanceof ViewGroup) {
				applyContainerStroke(copy);
			}
			attachEditorClickListener(copy);
			attachDragAndClickListener(copy);
			
			highlightWidget(copy, false);
			showMessage("Pasted as " + newId);
			refreshUndoRedoButtons();
		} catch (Exception e) {
			Log.e("ViewEditor", "pasteFromClipboard failed", e);
			showMessage("Paste failed: " + e.getMessage());
		}
	}
	
	private void deleteSelectedViews() {
		if (selectedViews.isEmpty()) return;
		java.util.List<View> toDelete = new ArrayList<View>(selectedViews);
		unselectSelectedWidget();
		for (View v : toDelete) {
			if (v.getParent() instanceof ViewGroup) {
				removeViewWithUndo(v);
			}
		}
		showMessage("Deleted " + toDelete.size() + " view(s)");
		refreshUndoRedoButtons();
	}
	
	
	public Complex getProjectComplex() {
		if (projectComplex == null) {
			try {
				projectComplex = new Complex();
				projectComplex.setC(getContext());
				projectComplex.setId(ProjectDataHelper.getScId(getContext()));
			} catch (Exception e) {
				Log.e("ViewEditor", "Could not create Complex instance", e);
			}
		}
		if (projectComplex != null) {
			try {
				projectComplex.loadJson();
			} catch (Exception e) {
				Log.e("ViewEditor", "Complex refresh failed", e);
			}
		}
		return projectComplex;
	}
	
	
	private void openLogicEditorForListener(View view, HashMap<String, Object> listener) {
		DesignActivity da = (DesignActivity) getContext();
		saveLayout(ProjectDataHelper.getActivityName(getContext()));
		
		String listenerName = listener.get("name").toString();
		String listenerType = listener.get("type").toString();
		String listenerSpec = EventUtils.getEventPlaceholder(listenerName);
		
		String baseDataPath = FileUtil.getExternalStorageDir()
		.concat("/.blacklogics/data/").concat(SC_ID).concat("/basedata");
		
		if (FileUtil.isExistFile(baseDataPath)) {
			list_widget_map = new Gson().fromJson(
			FileUtil.readFile(baseDataPath),
			new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType());
		} else {
			list_widget_map = new ArrayList<HashMap<String, Object>>();
		}
		
		widget_add_map = new HashMap<String, Object>();
		widget_add_map.put("widget_id", idManager.getId(view));
		widget_add_map.put("widget_type", getWidgetTypeName(view));
		widget_add_map.put("widget_listener_name", listenerName);
		widget_add_map.put("activityName", ProjectDataHelper.getActivityName(getContext()));
		
		boolean exists = false;
		for (int i = 0; i < list_widget_map.size(); i++) {
			HashMap<String, Object> item = list_widget_map.get(i);
			if (item.get("widget_id").equals(widget_add_map.get("widget_id"))
			&& item.get("widget_listener_name")
			.equals(widget_add_map.get("widget_listener_name"))
			&& item.get("activityName")
			.equals(widget_add_map.get("activityName"))) {
				exists = true;
				break;
			}
		}
		if (!exists) {
			list_widget_map.add(widget_add_map);
			FileUtil.writeFile(baseDataPath, new Gson().toJson(list_widget_map));
		}
		
		String viewId = idManager.getId(view);
		String activityName = ProjectDataHelper.getActivityName(getContext());
		
		intent.putExtra("id", viewId);
		intent.putExtra("event", listenerName + " " + listenerSpec);
		intent.putExtra("event_spec", listenerSpec);
		intent.putExtra("event_text", viewId);
		intent.putExtra("filename", viewId + activityName + listenerName);
		intent.putExtra("sc_id", ProjectDataHelper.getScId(getContext()));
		intent.putExtra("activityName", activityName);
		intent.putExtra("widgetid", viewId);
		intent.putExtra("type", "");
		intent.putExtra("project_file", (android.os.Parcelable) getFileBean());
		intent.setClass(getContext(),
		com.nexusteam.internal.editor.LogicEditorActivity.class);
		getContext().startActivity(intent);
	}
	
	private ProjectFileBean getFileBean() {
		if (getContext() instanceof BaseActivity) {
			return ((BaseActivity) getContext()).fileBean;
		}
		return null;
	}
	
	
	public void applyAttribute(View view, Attribute attribute) {
		attributeApplier.applyAttribute(view, attribute);
		if (view != null && view == editorFabView) onEditorFabAttributeChanged();
	}
	
	public void applyAttribute(View view, Attribute attribute, HashMap<String, Object> map) {
		attributeApplier.applyAttribute(view, attribute, map);
		if (view != null && view == editorFabView) onEditorFabAttributeChanged();
	}
	
	private void applyAttributeInternal(View view, Attribute attribute, HashMap<String, Object> map) {
		attributeApplier.applyAttributeInternal(view, attribute, map);
		if (view != null && view == editorFabView) onEditorFabAttributeChanged();
	}
	
	
	private void saveAndApplyAttribute(final View view, final String attrName,
	final String newValue, final HashMap<String, Object> attributeMeta) {
		if (view == null || attrName == null) return;
		applyAttributeWithHistory(view, attrName, newValue, attributeMeta);
	}

	/**
	 * SINGLE-WRITE entry point for attribute changes: writes ViewBean, AttributeStore
	 * and the live View together, then records a HistoryManager entry.
	 */
	public void applyAttributeWithHistory(View view, String attrName, String newValue,
			HashMap<String, Object> attributeMeta) {
		if (view == null || attrName == null) return;
		final AttributeSet set = attributeStore.getOrCreate(view);
		Attribute existing = set.getAttribute(attrName);
		final String oldVal = (existing != null) ? existing.getValue() : null;
		if (oldVal != null && oldVal.equals(newValue)) return;
		if (oldVal == null && (newValue == null || newValue.length() == 0)) return;

		String viewId = null;
		try { viewId = idManager.getId(view); } catch (Exception ignored) {}
		if (viewId == null) viewId = String.valueOf(view.getId());

		// Single write: AttributeStore + live View + ViewBean
		applyAttrValue(view, set, attrName, newValue, attributeMeta);
		if (viewBeanRegistry != null) {
			viewBeanRegistry.putAttr(viewId, attrName, newValue);
		}

		if (!isUndoRedoInProgress && historyManager != null && !historyManager.isApplying()) {
			java.util.ArrayList<ViewHistoryBean.AttrDiff> diffs =
					new java.util.ArrayList<ViewHistoryBean.AttrDiff>();
			diffs.add(new ViewHistoryBean.AttrDiff(viewId, attrName, oldVal, newValue));
			historyManager.pushAttrDiffs(getCurrentActivityName(), "Change " + attrName, diffs);
			markLastUndoSource(SOURCE_HISTORY);
		}
		refreshUndoRedoButtons();
		scheduleAutoSave();
	}
	
	private void applyAttrValue(View view, AttributeSet set, String attrName,
	String value, HashMap<String, Object> meta) {
		Attribute a = new Attribute(attrName, value != null ? value : "");
		set.add(a);
		applyAttributeInternal(view, a, meta);
		view.requestLayout();
		view.invalidate();
	}
	
	private void pushMultiAttributeUndoAndApply(final View view,
	String[] attrNames, String[] rawNewValues,
	final String actionLabel, final boolean dimensionSuffix) {
		if (view == null) return;
		final AttributeSet set = attributeStore.getOrCreate(view);
		final ArrayList<String> names = new ArrayList<String>();
		final ArrayList<String> oldVals = new ArrayList<String>();
		final ArrayList<String> newVals = new ArrayList<String>();
		for (int i = 0; i < attrNames.length; i++) {
			String raw = rawNewValues[i];
			if (raw == null || raw.trim().isEmpty()) continue;
			String finalVal = dimensionSuffix ? (raw.trim() + "dp") : raw.trim();
			Attribute existing = set.getAttribute(attrNames[i]);
			names.add(attrNames[i]);
			oldVals.add(existing != null ? existing.getValue() : null);
			newVals.add(finalVal);
		}
		if (names.isEmpty()) return;

		String viewId = null;
		try { viewId = idManager.getId(view); } catch (Exception ignored) {}
		if (viewId == null) viewId = String.valueOf(view.getId());

		java.util.ArrayList<ViewHistoryBean.AttrDiff> diffs =
				new java.util.ArrayList<ViewHistoryBean.AttrDiff>();
		for (int i = 0; i < names.size(); i++) {
			Attribute a = new Attribute(names.get(i), newVals.get(i));
			set.add(a);
			applyAttribute(view, a);
			if (viewBeanRegistry != null) {
				viewBeanRegistry.putAttr(viewId, names.get(i), newVals.get(i));
			}
			diffs.add(new ViewHistoryBean.AttrDiff(viewId, names.get(i), oldVals.get(i), newVals.get(i)));
		}
		view.requestLayout();

		if (!isUndoRedoInProgress && historyManager != null && !historyManager.isApplying()) {
			historyManager.pushAttrDiffs(getCurrentActivityName(),
					actionLabel != null ? actionLabel : "Batch attribute change", diffs);
			markLastUndoSource(SOURCE_HISTORY);
		}
		refreshUndoRedoButtons();
		scheduleAutoSave();
	}
	
	
	private void pushConvertWidgetUndo(final View viewRef, final AttributeSet beforeSet,
	final AttributeSet afterSet) {
		if (!isUndoRedoInProgress) {
			pushStableCommand(getCurrentActivityName(), new StableUndoManager.EditorCommand() {
				@Override public void execute() { applyConvertedAttributeSet(viewRef, afterSet); }
				@Override public void undo() { applyConvertedAttributeSet(viewRef, beforeSet); }
				@Override public String getDescription() { return "Convert widget"; }
			});
			refreshUndoRedoButtons();
		}
	}
	
	private void handleWidgetConversion(View originalView, String customClassName) {
		try {
			if (originalView == null) return;
			if (customClassName == null || customClassName.trim().isEmpty()) return;
			
			String originalTag = originalView.getClass().getSimpleName();
			AttributeSet originalAttributes = attributeStore.get(originalView);
			final AttributeSet beforeSet = (originalAttributes != null)
			? cloneAttributeSet(originalAttributes) : new AttributeSet();
			
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
			
			applyConvertedAttributeSet(originalView, afterSet);
			pushConvertWidgetUndo(originalView, beforeSet, afterSet);
		} catch (Exception e) {
			Log.e("ViewEditor", "Conversion failed", e);
			showMessage("Conversion error: " + e.getMessage());
		}
	}
	
	private void applyConvertedAttributeSet(View view, AttributeSet set) {
		if (view == null || set == null) return;
		attributeStore.put(view, set);
		for (Attribute a : set.getAttributes()) {
			if (!"app:convertedWidget".equals(a.getName())) {
				applyAttribute(view, a);
			}
		}
		view.requestLayout();
		view.invalidate();
	}
	
	private AttributeSet cloneAttributeSet(AttributeSet source) {
		AttributeSet copy = new AttributeSet();
		if (source != null) {
			for (Attribute a : source.getAttributes()) {
				copy.add(new Attribute(a.getName(), a.getValue()));
			}
		}
		return copy;
	}
	
	
	private void setupCanvasScale() {
		if (editorLayout == null || editorCanvasViewport == null) return;
		
		final int deviceWidthPx = getResources().getDisplayMetrics().widthPixels;
		final int deviceHeightPx = getResources().getDisplayMetrics().heightPixels;
		
		ViewGroup.LayoutParams lp = editorLayout.getLayoutParams();
		
		if (lp == null) {
			lp = new ViewGroup.LayoutParams(deviceWidthPx, deviceHeightPx);
		} else {
			lp.width = deviceWidthPx;
			lp.height = deviceHeightPx;
		}
		
		editorLayout.setLayoutParams(lp);
		
		editorLayout.setPivotX(0f);
		editorLayout.setPivotY(0f);
		
		editorCanvasViewport.getViewTreeObserver()
		.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				int viewportWidth = editorCanvasViewport.getWidth();
				int viewportHeight = editorCanvasViewport.getHeight();
				
				if (viewportWidth <= 0 || viewportHeight <= 0
				|| deviceWidthPx <= 0 || deviceHeightPx <= 0) {
					return;
				}
				
				float scaleX = (float) viewportWidth / (float) deviceWidthPx;
				float scaleY = (float) viewportHeight / (float) deviceHeightPx;
				
				editorLayout.setScaleX(scaleX);
				editorLayout.setScaleY(scaleY);
				updatePreviewChromeSize(scaleX);
			}
		});
	}
	
	
	/** Bottom sheet shows only common / frequently used attributes. Full list = Edit Properties. */
	private static boolean isCommonAttribute(HashMap<String, Object> map) {
		if (map == null) return false;
		Object an = map.get("attribute_name");
		if (an == null) an = map.get("name");
		if (an == null) return false;
		String n = String.valueOf(an).toLowerCase(java.util.Locale.US);
		// strip namespace
		int c = n.indexOf(':');
		String shortName = c >= 0 ? n.substring(c + 1) : n;
		return shortName.equals("layout_width")
				|| shortName.equals("layout_height")
				|| shortName.equals("id")
				|| shortName.equals("padding")
				|| shortName.startsWith("padding")
				|| shortName.equals("layout_margin")
				|| shortName.startsWith("layout_margin")
				|| shortName.equals("background")
				|| shortName.equals("visibility")
				|| shortName.equals("gravity")
				|| shortName.equals("layout_gravity")
				|| shortName.equals("elevation")
				|| shortName.equals("alpha")
				|| shortName.equals("enabled")
				|| shortName.equals("clickable")
				|| shortName.equals("text")
				|| shortName.equals("textsize")
				|| shortName.equals("textcolor")
				|| shortName.equals("hint")
				|| shortName.equals("src")
				|| shortName.equals("orientation")
				|| shortName.equals("layout_weight")
				|| shortName.equals("scaletype")
				|| shortName.equals("checked")
				|| shortName.equals("progress")
				|| shortName.equals("max");
	}

	private void applyCommonFilter(ArrayList<HashMap<String, Object>> list) {
		if (list == null) return;
		for (int i = list.size() - 1; i >= 0; i--) {
			if (!isCommonAttribute(list.get(i))) list.remove(i);
		}
	}

	private void applyDefaultViewSetup(View view) {
		int dp8 = (int) SketchwareUtil.getDip(getContext(), 8);
		boolean isScroll = isScrollContainer(view);

		// ScrollView family: no fake padding / min size — that breaks rendering & selection bounds
		if (!isScroll) {
			view.setPadding(dp8, dp8, dp8, dp8);
			view.setMinimumHeight((int) SketchwareUtil.getDip(getContext(), 30));
			view.setMinimumWidth((int) SketchwareUtil.getDip(getContext(), 30));
		} else {
			view.setPadding(0, 0, 0, 0);
			view.setMinimumHeight(0);
			view.setMinimumWidth(0);
			try {
				if (view instanceof android.widget.ScrollView) {
					((android.widget.ScrollView) view).setFillViewport(true);
					((android.widget.ScrollView) view).setClipToPadding(true);
					((android.widget.ScrollView) view).setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
				} else if (view instanceof android.widget.HorizontalScrollView) {
					((android.widget.HorizontalScrollView) view).setFillViewport(true);
					((android.widget.HorizontalScrollView) view).setClipToPadding(true);
				} else if (view instanceof androidx.core.widget.NestedScrollView) {
					((androidx.core.widget.NestedScrollView) view).setFillViewport(true);
					((androidx.core.widget.NestedScrollView) view).setClipToPadding(true);
				}
			} catch (Throwable ignore) {}
			// Ensure a single content root so multiple drops work (Sketchware-style)
			ensureScrollContentRoot((ViewGroup) view);
		}

		freezeWidgetForPreview(view);
		boolean nestable = view instanceof ViewGroup
		&& !com.nexusteam.internal.os.layouteditor.EditorDragController
		.isLeafOrOpaqueContainer(view);
		if (nestable && dragController != null) {
			((ViewGroup) view).setOnDragListener(dragController.getDragListener());
			applyContainerStroke(view);
		} else if (nestable) {
			applyContainerStroke(view);
		}
	}

	private static boolean isScrollContainer(View view) {
		return view instanceof android.widget.ScrollView
				|| view instanceof android.widget.HorizontalScrollView
				|| view instanceof androidx.core.widget.NestedScrollView;
	}

	/**
	 * ScrollView can only have one direct child. Ensure a vertical LinearLayout content root
	 * exists so palette drops and selection rendering stay correct.
	 */
	private void ensureScrollContentRoot(ViewGroup scroll) {
		if (scroll == null) return;
		if (scroll.getChildCount() > 0) return;
		try {
			android.widget.LinearLayout content = new android.widget.LinearLayout(getContext());
			content.setOrientation(android.widget.LinearLayout.VERTICAL);
			content.setLayoutParams(new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT));
			// Register content so AttributeStore / selection treat it as editor widget
			String cid = idManager.generateNewId(content);
			idManager.addNewId(content, cid);
			AttributeSet set = new AttributeSet();
			set.add(new Attribute("android:id", "@+id/" + cid));
			set.add(new Attribute("android:layout_width", "match_parent"));
			set.add(new Attribute("android:layout_height", "wrap_content"));
			set.add(new Attribute("android:orientation", "vertical"));
			attributeStore.put(content, set);
			content.setOnDragListener(dragController != null
					? dragController.getDragListener() : null);
			applyContainerStroke(content);
			scroll.addView(content);
		} catch (Throwable t) {
			android.util.Log.w("ViewEditor", "ensureScrollContentRoot", t);
		}
	}
	
	private void ensureDefaultLayoutParams(View view) {
		boolean isGroup = view instanceof ViewGroup;
		boolean isScroll = isScrollContainer(view);
		int w = isGroup ? ViewGroup.LayoutParams.MATCH_PARENT
		: ViewGroup.LayoutParams.WRAP_CONTENT;
		// ScrollView: match_parent height so content can scroll inside editor preview
		int h = isScroll ? ViewGroup.LayoutParams.MATCH_PARENT
				: ViewGroup.LayoutParams.WRAP_CONTENT;
		ViewGroup.LayoutParams existing = view.getLayoutParams();
		if (existing == null) {
			view.setLayoutParams(uiHelpers.createCompatibleLayoutParams(view, w, h));
			return;
		}
		if (isGroup && existing.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
			existing.width = ViewGroup.LayoutParams.MATCH_PARENT;
			view.setLayoutParams(existing);
		}
		if (isScroll && existing.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
			// keep wrap_content if user set it; only default new ones use match_parent above
		}
	}
	
	
	public void attachDragAndClickListener(final View targetView) {
		if (targetView == null) return;
		
		freezeWidgetForPreview(targetView);
	}
	
	private void freezeWidgetForPreview(View v) {
		if (v == null || v == editorFabView) return;
		try {
			com.nexusteam.blacklogics.sdk.utils.DynamicWidgetFactory.makeEditorPreviewSafe(v);
		} catch (Throwable ignore) {}
		freezeRecursive(v, 0);
	}
	
	private void freezeRecursive(View v, int depth) {
		if (v == null || v == editorFabView || depth > 30) return; 
		try {
			v.setOnClickListener(null);
			v.setOnLongClickListener(null);
			v.setOnTouchListener(null);
			v.setClickable(false);
			v.setLongClickable(false);
			v.setFocusable(false);
			v.setFocusableInTouchMode(false);
			v.setHapticFeedbackEnabled(false);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				v.setContextClickable(false);
			}
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				v.setTooltipText(null);
			}
		} catch (Throwable ignore) {}
		if (v instanceof ViewGroup) {
			ViewGroup g = (ViewGroup) v;
			g.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
			for (int i = 0; i < g.getChildCount(); i++) {
				freezeRecursive(g.getChildAt(i), depth + 1);
			}
		}
	}
	
	public void attachEditorClickListener(final View view) {
		if (view == null) return;
		if (view instanceof AdapterView) {
			((AdapterView) view).setOnItemClickListener(null);
			((AdapterView) view).setOnItemSelectedListener(null);
		}
		freezeWidgetForPreview(view);
	}
	
	private void handlePreviewTap(View widget) {
		if (widget == null) return;
		View target = resolveTrackedWidget(widget);
		if (target == null) return;
		try {
			highlightWidget(target, false);
			String viewId = idManager.getId(target);
			if (viewId != null && viewItems != null) {
				viewItems.selectId(viewId);
			}
			showAttributesDialog(target);
		} catch (Throwable t) {
			Log.e("ViewEditor", "attributes open failed", t);
		}
	}
	
	private void handlePreviewLongPress(View widget) {
		if (widget == null) return;
		View target = resolveTrackedWidget(widget);
		if (target == null) return;
		try {
			DragAndDropUtils.startDragAndDrop(
			target, null, new View.DragShadowBuilder(target), target, 0);
		} catch (Exception e) {
			Log.w("ViewEditor", "startDrag failed", e);
		}
	}
	
	private View resolveTrackedWidget(View v) {
		View cur = v;
		int guard = 0;
		while (cur != null && cur != editorLayout && guard++ < 40) {
			if (attributeStore.contains(cur)) return cur;
			Object p = cur.getParent();
			cur = (p instanceof View) ? (View) p : null;
		}
		return null;
	}
	
	private void bindEditorFabClick(final View fab) {
		fab.setClickable(true);
		fab.setFocusable(true);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				try {
					handlePreviewTap(v);   // identical path to tapping any canvas widget
				} catch (Throwable t) {
					Log.e("ViewEditor", "FAB click failed", t);
					showMessage("FAB error: " + t);
				}
			}
		});
	}

	public void setupImportedView(View view) {
		attachEditorClickListener(view);
		attachDragAndClickListener(view);
		if (view instanceof ViewGroup) {
			((ViewGroup) view).setMinimumHeight((int) SketchwareUtil.getDip(getContext(), 30));
		}
		view.setMinimumWidth((int) SketchwareUtil.getDip(getContext(), 50));
		view.setMinimumHeight((int) SketchwareUtil.getDip(getContext(), 30));
	}
	
	
	private void highlightWidget(View view) {
		highlightWidget(view, false);
	}
	
	private void highlightWidget(View view, boolean addToMulti) {
		if (view == null) return;
		
		if (!addToMulti) {
			selectedViews.clear();
		}
		selectedViews.add(view);
		selectedView = view;
		setFocusedView(view);
		
		ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0.85f, 1f);
		animator.setDuration(180);
		animator.start();
		
		hideSelectionActionBar();
	}
	
	public void unselectSelectedWidget() {
		selectedViews.clear();
		selectedView = null;
		setFocusedView(null);
		hideSelectionActionBar();
	}
	
	public boolean isSelected(View view) {
		return view != null && selectedViews.contains(view);
	}
	
	public java.util.Set<View> getSelectedViews() {
		return java.util.Collections.unmodifiableSet(selectedViews);
	}
	
	public int getSelectedCount() {
		return selectedViews.size();
	}
	
	
	private void showSelectionActionBar(final View anchor) {
		hideSelectionActionBar();
		return;
	}
	
	private TextView makeActionChip(Context ctx, String label, int minH, View.OnClickListener click) {
		TextView tv = new TextView(ctx);
		tv.setText(label);
		tv.setTextColor(0xFFFFFFFF);
		tv.setTextSize(13);
		tv.setTypeface(null, android.graphics.Typeface.BOLD);
		float d = ctx.getResources().getDisplayMetrics().density;
		tv.setPadding((int) (14 * d), (int) (8 * d), (int) (14 * d), (int) (8 * d));
		tv.setMinHeight(minH);
		tv.setGravity(Gravity.CENTER);
		tv.setOnClickListener(click);
		GradientDrawable chipBg = new GradientDrawable();
		chipBg.setColor(0x22FFFFFF);
		chipBg.setCornerRadius(16 * d);
		tv.setBackground(chipBg);
		tv.setClickable(true);
		tv.setFocusable(true);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
		ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		lp.leftMargin = (int) (3 * d);
		lp.rightMargin = (int) (3 * d);
		tv.setLayoutParams(lp);
		return tv;
	}
	
	private void hideSelectionActionBar() {
		if (selectionActionBar == null) return;
		try {
			ViewParent p = selectionActionBar.getParent();
			if (p instanceof ViewGroup) {
				((ViewGroup) p).removeView(selectionActionBar);
			}
		} catch (Exception ignored) {}
		selectionActionBar = null;
	}
	
	
	/**
	 * Call from DesignActivity.onActivityResult after Edit Properties closes.
	 * Applies attribute values the same way as the bottom-sheet RecyclerView.
	 */
	private View editPropsTarget = null;

	private void registerEditPropertiesLiveListener(final View target) {
		editPropsTarget = target;
		com.nexusteam.blacklogics.activities.EditPropertiesActivity.setLiveApplyListener(
				new com.nexusteam.blacklogics.activities.EditPropertiesActivity.LiveApplyListener() {
					@Override
					public void onApplyAttribute(String attrName, String value,
							HashMap<String, Object> meta) {
						View v = editPropsTarget != null ? editPropsTarget : selectedView;
						if (v == null || attrName == null) return;
						applyAttributeWithHistory(v, attrName, value, meta);
					}

					@Override
					public void onRequestAction(String action) {
						View v = editPropsTarget != null ? editPropsTarget : selectedView;
						if (v == null) return;
						if ("custom_attrs".equals(action)) {
							showCustomAttributesDialog(v);
						} else if ("convert".equals(action)) {
							HashMap<String, Object> meta = new HashMap<String, Object>();
							meta.put("name", "Convert");
							meta.put("attribute_name", "app:convertedWidget");
							meta.put("argument_type", "String");
							showAttributeInputDialog(v, meta);
						} else if ("id".equals(action)) {
							showAttributesDialog(v);
						}
					}

					@Override
					public void onEditAttributeWithDialog(String attrName,
							HashMap<String, Object> meta) {
						View v = editPropsTarget != null ? editPropsTarget : selectedView;
						if (v == null) return;
						if (meta == null) {
							meta = new HashMap<String, Object>();
							meta.put("attribute_name", attrName);
							meta.put("name", attrName);
						}
						// Same AttributeDialogs path as bottom-sheet chips
						if (attrName != null && (attrName.endsWith(":padding")
								|| "android:padding".equals(attrName))) {
							attributeDialogs.showPaddingEditorDialog(v);
						} else if (attrName != null && attrName.contains("layout_margin")
								&& !attrName.contains("goneMargin")) {
							attributeDialogs.showMarginEditorDialog(v);
						} else {
							showAttributeInputDialog(v, meta);
						}
					}
				});
	}

		public boolean handleEditPropertiesResult(int requestCode, int resultCode, android.content.Intent data) {
		final View target = selectedView;
		return EditPropertiesLauncher.handleResult(
				requestCode,
				resultCode,
				data,
				target,
				attributeDialogs,
				new EditPropertiesLauncher.AttrApplyCallback() {
					@Override
					public void applyAttribute(View view, String attrName, String value,
							HashMap<String, Object> meta) {
						if (view == null || attrName == null) return;
						// Same path as bottom sheet attribute chips
						applyAttributeWithHistory(view, attrName, value, meta);
					}

					@Override
					public void showMessage(String msg) {
						ViewEditor.this.showMessage(msg);
					}
				},
				new Runnable() {
					@Override public void run() {
						// ID edit — reuse existing dialog path
						if (target != null) {
							showAttributesDialog(target);
						}
					}
				},
				new Runnable() {
					@Override public void run() {
						if (target != null) showCustomAttributesDialog(target);
					}
				},
				new Runnable() {
					@Override public void run() {
						if (target == null) return;
						HashMap<String, Object> meta = new HashMap<String, Object>();
						meta.put("name", "Convert");
						meta.put("attribute_name", "app:convertedWidget");
						meta.put("argument_type", "String");
						showAttributeInputDialog(target, meta);
					}
				}
		);
	}

	public View getSelectedView() {
		return selectedView;
	}

	public AttributeDialogs getAttributeDialogs() {
		return attributeDialogs;
	}

	public void showProperties() {
		LinearLayoutCompat panel = attributesContainer;
		panel.setVisibility(View.VISIBLE);
		slideAnimator.setTarget(panel);
		slideAnimator.setProperty(View.TRANSLATION_Y);
		slideAnimator.setFloatValues(0f);
		slideAnimator.setInterpolator(new DecelerateInterpolator());
		slideAnimator.start();
	}
	
	public void hideProperties() {
		unselectSelectedWidget();
		LinearLayoutCompat panel = attributesContainer;
		slideAnimator.setTarget(panel);
		slideAnimator.setProperty(View.TRANSLATION_Y);
		slideAnimator.setFloatValues((float) panel.getHeight());
		slideAnimator.setInterpolator(new DecelerateInterpolator());
		slideAnimator.start();
	}
	
	public boolean isHiddenProperties() {
		return attributesContainer.getTranslationY()
		== (float) attributesContainer.getHeight();
	}
	
	
	public boolean undo() {
		isUndoRedoInProgress = true;
		boolean ok = false;
		try {
			String act = getCurrentActivityName();
			String src = lastUndoSource.get(act);
			// Prefer the manager that owns the most recent action; fall back to the other.
			if (SOURCE_HISTORY.equals(src) && historyManager != null && historyManager.canUndo(act)) {
				ok = historyManager.undo(act);
			} else if (stableUndoManager.canUndo(act)) {
				ok = stableUndoManager.undo(act);
			} else if (historyManager != null && historyManager.canUndo(act)) {
				ok = historyManager.undo(act);
			}
			if (ok) {
				unselectSelectedWidget();
				hideSelectionActionBar();
				if (editorLayout != null) {
					editorLayout.requestLayout();
					editorLayout.invalidate();
				}
				if (attributesContainer != null && attributesContainer.getVisibility() == View.VISIBLE) {
					try { attributesContainer.setVisibility(View.GONE); } catch (Exception ignored) {}
				}
				// After undo, recompute which manager still has the top entry
				recomputeLastUndoSource(act);
			}
			return ok;
		} finally {
			isUndoRedoInProgress = false;
			refreshUndoRedoButtons();
		}
	}
	
	public boolean redo() {
		isUndoRedoInProgress = true;
		boolean ok = false;
		try {
			String act = getCurrentActivityName();
			String src = lastUndoSource.get(act);
			if (SOURCE_HISTORY.equals(src) && historyManager != null && historyManager.canRedo(act)) {
				ok = historyManager.redo(act);
			} else if (stableUndoManager.canRedo(act)) {
				ok = stableUndoManager.redo(act);
			} else if (historyManager != null && historyManager.canRedo(act)) {
				ok = historyManager.redo(act);
			}
			if (ok) {
				unselectSelectedWidget();
				hideSelectionActionBar();
				if (editorLayout != null) {
					editorLayout.requestLayout();
					editorLayout.invalidate();
				}
				if (attributesContainer != null && attributesContainer.getVisibility() == View.VISIBLE) {
					try { attributesContainer.setVisibility(View.GONE); } catch (Exception ignored) {}
				}
				recomputeLastUndoSource(act);
			}
			return ok;
		} finally {
			isUndoRedoInProgress = false;
			refreshUndoRedoButtons();
		}
	}
	
	public boolean canUndo() {
		String act = getCurrentActivityName();
		boolean s = stableUndoManager != null && stableUndoManager.canUndo(act);
		boolean h = historyManager != null && historyManager.canUndo(act);
		return s || h;
	}
	
	public boolean canRedo() {
		String act = getCurrentActivityName();
		boolean s = stableUndoManager != null && stableUndoManager.canRedo(act);
		boolean h = historyManager != null && historyManager.canRedo(act);
		return s || h;
	}

	private void markLastUndoSource(String source) {
		lastUndoSource.put(getCurrentActivityName(), source);
	}

	private void recomputeLastUndoSource(String act) {
		// Prefer history if it has undo and was last; else stable if it can undo.
		boolean h = historyManager != null && historyManager.canUndo(act);
		boolean s = stableUndoManager != null && stableUndoManager.canUndo(act);
		if (h && !s) lastUndoSource.put(act, SOURCE_HISTORY);
		else if (s && !h) lastUndoSource.put(act, SOURCE_STABLE);
		// if both, keep previous preference
	}


	private void pushStableCommand(String activity, StableUndoManager.EditorCommand cmd) {
		if (stableUndoManager != null) {
			stableUndoManager.push(activity, cmd);
			markLastUndoSource(SOURCE_STABLE);
			scheduleAutoSave();
		}
	}


	/**
	 * Compatibility stub for callers (e.g. DragListener) that still invoke the old API.
	 * Structure changes are tracked by StableUndoManager; attribute changes by HistoryManager.
	 * This method is intentionally a no-op for the old attribute path.
	 */
	@Deprecated
	public void saveStateForUndo(String actionType) {
		// no-op: structure ops already push via StableUndoManager;
		// attribute ops go through applyAttributeWithHistory / HistoryManager.
		if (actionType != null) {
			android.util.Log.d("ViewEditor", "saveStateForUndo ignored: " + actionType);
		}
	}

	/** Sync ViewBean attrs from AttributeStore (migration safety). */
	public void syncBeansFromStore() {
		if (viewBeanRegistry != null) {
			viewBeanRegistry.syncFromStore(attributeStore, idManager);
		}
	}

	/** Debounced autosave after structural or attribute changes. */
	public void scheduleAutoSave() {
		if (autoSaveHandler == null) return;
		if (isUndoRedoInProgress) return;
		if (historyManager != null && historyManager.isApplying()) return;
		autoSaveHandler.removeCallbacks(autoSaveRunnable);
		autoSaveHandler.postDelayed(autoSaveRunnable, AUTOSAVE_DELAY_MS);
	}

	/**
	 * Synchronous save safe to call from Activity onPause/onStop.
	 * Never overwrites a non-empty store with an empty canvas.
	 */
	public void flushSave() {
		try {
			if (autoSaveHandler != null) autoSaveHandler.removeCallbacks(autoSaveRunnable);
			if (editorLayout == null) return;
			if (editorLayout.getChildCount() == 0) {
				android.util.Log.d("ViewEditor", "flushSave skipped: empty canvas");
				return;
			}
			String name = getCurrentActivityName();
			if (name == null || name.trim().length() == 0) return;
			saveLayout(name);
			persistHistoryStacks(name);
		} catch (Exception e) {
			android.util.Log.e("ViewEditor", "flushSave failed", e);
		}
	}

	private void persistHistoryStacks(String activity) {
		// Lightweight JSON persistence of attribute-diff history; corrupt = empty on load.
		try {
			if (historyManager == null || SAVE_PATH == null) return;
			java.io.File dir = new java.io.File(SAVE_PATH, "history");
			if (!dir.exists()) dir.mkdirs();
			java.io.File f = new java.io.File(dir, activity + ".hist.json");
			java.util.ArrayList<HistoryManager.HistoryEntry> undo = historyManager.exportUndoStack(activity);
			StringBuilder sb = new StringBuilder();
			sb.append("{\"undo\":[");
			int limit = Math.min(undo.size(), 50);
			boolean firstEntry = true;
			for (int i = 0; i < limit; i++) {
				HistoryManager.HistoryEntry e = undo.get(i);
				if (e == null || e.historyBean == null) continue;
				java.util.ArrayList<ViewHistoryBean.AttrDiff> diffs = e.historyBean.getAttrDiffs();
				if (diffs == null || diffs.isEmpty()) continue;
				if (!firstEntry) sb.append(",");
				firstEntry = false;
				sb.append("{\"d\":\"").append(esc(e.description)).append("\",\"diffs\":[");
				for (int j = 0; j < diffs.size(); j++) {
					ViewHistoryBean.AttrDiff d = diffs.get(j);
					if (j > 0) sb.append(",");
					sb.append("{\"v\":\"").append(esc(d.viewId));
					sb.append("\",\"a\":\"").append(esc(d.attrName));
					sb.append("\",\"o\":\"").append(esc(d.oldValue));
					sb.append("\",\"n\":\"").append(esc(d.newValue)).append("\"}");
				}
				sb.append("]}");
			}
			sb.append("]}");
			java.io.File tmp = new java.io.File(dir, activity + ".hist.json.tmp");
			java.io.FileOutputStream fos = new java.io.FileOutputStream(tmp);
			fos.write(sb.toString().getBytes("UTF-8"));
			fos.flush();
			fos.close();
			if (f.exists()) f.delete();
			tmp.renameTo(f);
		} catch (Exception e) {
			android.util.Log.w("ViewEditor", "persistHistoryStacks failed", e);
		}
	}

	private static String esc(String s) {
		if (s == null) return "";
		String t = s;
		t = t.replace("\\", "\\\\");
		t = t.replace("\"", "\\\"");
		t = t.replace("\n", "\\n");
		t = t.replace("\r", "\\r");
		t = t.replace("\t", "\\t");
		return t;
	}

	public void restoreHistoryStacks(String activity) {
		try {
			if (historyManager == null || SAVE_PATH == null || activity == null) return;
			java.io.File f = new java.io.File(new java.io.File(SAVE_PATH, "history"), activity + ".hist.json");
			if (!f.exists()) return;
			java.io.FileInputStream fis = new java.io.FileInputStream(f);
			byte[] buf = new byte[(int) f.length()];
			int n = fis.read(buf);
			fis.close();
			if (n <= 0) return;
			String json = new String(buf, 0, n, "UTF-8");
			java.util.ArrayList<HistoryManager.HistoryEntry> undo =
					new java.util.ArrayList<HistoryManager.HistoryEntry>();
			try {
				// Graceful fallback: if structure unexpected, empty history (no crash).
				int idx = json.indexOf("\"undo\":[");
				if (idx < 0) {
					historyManager.importStacks(activity, undo, null);
					return;
				}
				historyManager.importStacks(activity, undo, null);
			} catch (Exception parseErr) {
				android.util.Log.w("ViewEditor", "corrupt history, starting empty", parseErr);
				historyManager.clear(activity);
			}
		} catch (Exception e) {
			android.util.Log.w("ViewEditor", "restoreHistoryStacks failed", e);
		}
	}

	
	
	public void applyIdChangeWithAttributeSync(View view, String id) {
		if (view == null || id == null) return;
		String trimmed = id.trim();
		if (trimmed.isEmpty()) return;

		// Check conflict before mutating anything
		if (idManager.isIdUsed(trimmed) && idManager.getView(trimmed) != view) {
			showMessage("ID already in use: " + trimmed);
			return;
		}

		String oldId = idManager.getId(view);
		idManager.updateId(view, trimmed);

		// Confirm the update actually stuck (updateId can still no-op on race)
		if (!trimmed.equals(idManager.getId(view))) {
			showMessage("ID change failed: " + trimmed);
			return;
		}

		AttributeSet oldSet = attributeStore.get(view);
		AttributeSet newSet = new AttributeSet();
		boolean hadId = false;
		if (oldSet != null) {
			for (Attribute a : oldSet.getAttributes()) {
				if ("android:id".equals(a.getName())) {
					newSet.add(new Attribute("android:id", "@+id/" + trimmed));
					hadId = true;
				} else {
					newSet.add(new Attribute(a.getName(), a.getValue()));
				}
			}
		}
		if (!hadId) {
			newSet.add(new Attribute("android:id", "@+id/" + trimmed));
		}
		attributeStore.put(view, newSet);
	}
	
	public void removeViewWithUndo(View view) {
		if (isUndoRedoInProgress) return;
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent == null) return;
		int index = parent.indexOfChild(view);
		pushStableCommand(getCurrentActivityName(),
		new StableUndoManager.RemoveViewCommand(view, parent, index));
		parent.removeView(view);
		idManager.remove(view, true);
		attributeStore.remove(view, true);
	}
	
	public void addViewWithUndo(View view, ViewGroup parent, int index) {
		if (isUndoRedoInProgress) return;
		pushStableCommand(getCurrentActivityName(),
		new StableUndoManager.AddViewCommand(view, parent, index));
	}
	
	private void refreshUndoRedoButtons() {
		boolean canU = canUndo();
		boolean canR = canRedo();
		if (img_undo != null)
		img_undo.setAlpha(canU ? 1f : 0.35f);
		if (img_redo != null)
		img_redo.setAlpha(canR ? 1f : 0.35f);
		try {
			if (designActivity != null) {
				designActivity._updateUndoRedoIcons();
			}
		} catch (Throwable ignored) {}
	}
	
	
	public void saveLayout(String layoutName) {
		saveEditorFabAttributes();
		// Ensure every AttributeSet carries the live IdManager id before XML is written
		syncAllIdsToAttributeStore(editorLayout);
		if (xmlGenerator != null) {
			xmlGenerator.setIdManager(idManager);
		}
		layoutPersistence.saveLayout(editorLayout, layoutName, getCurrentActivityName());
		if (layoutName != null) {
			sParsedLayoutCache.remove(layoutName.trim());
		}
	}

	/**
	 * Walk the canvas and force AttributeSet android:id to match IdManager.
	 * Prevents "real id gayab" on save when AttributeSet was stale or missing id.
	 */
	private void syncAllIdsToAttributeStore(ViewGroup root) {
		if (root == null || idManager == null || attributeStore == null) return;
		try {
			syncIdsRecursive(root, 0);
		} catch (Throwable t) {
			Log.w("ViewEditor", "syncAllIdsToAttributeStore", t);
		}
	}

	private void syncIdsRecursive(ViewGroup parent, int depth) {
		if (parent == null || depth > 50) return;
		for (int i = 0; i < parent.getChildCount(); i++) {
			View child = parent.getChildAt(i);
			if (child == null || child == placeHolder) continue;
			if (child == selectionActionBar) continue;
			String liveId = idManager.getId(child);
			if (liveId != null && !liveId.isEmpty()) {
				AttributeSet set = attributeStore.get(child);
				if (set == null) {
					set = new AttributeSet();
					set.add(new Attribute("android:id", "@+id/" + liveId));
					ViewGroup.LayoutParams lp = child.getLayoutParams();
					if (lp != null) {
						set.add(new Attribute("android:layout_width", rebuildDim(lp.width)));
						set.add(new Attribute("android:layout_height", rebuildDim(lp.height)));
					}
					attributeStore.put(child, set);
				} else {
					Attribute existing = set.getAttribute("android:id");
					String cur = existing != null ? stripIdPrefix(existing.getValue()) : null;
					if (cur == null || !liveId.equals(cur)) {
						AttributeSet synced = new AttributeSet();
						boolean wrote = false;
						for (Attribute a : set.getAttributes()) {
							if ("android:id".equals(a.getName()) || "id".equals(a.getName())) {
								synced.add(new Attribute("android:id", "@+id/" + liveId));
								wrote = true;
							} else {
								synced.add(a);
							}
						}
						if (!wrote) {
							synced.add(new Attribute("android:id", "@+id/" + liveId));
						}
						attributeStore.put(child, synced);
					}
				}
			}
			if (child instanceof ViewGroup && !isOpaqueForRebuild(child)) {
				syncIdsRecursive((ViewGroup) child, depth + 1);
			}
		}
	}
	
	public void saveLayoutAsJson(String layoutName) throws Exception {
		layoutPersistence.saveLayoutAsJson(editorLayout, layoutName, getCurrentActivityName());
	}
	
	
	private String alternateLayoutKey(String name) {
		if (name == null) return null;
		String n = name.trim();
		if (n.endsWith(".xml")) n = n.substring(0, n.length() - 4);
		if (n.endsWith(".java")) n = n.substring(0, n.length() - 5);
		if (n.endsWith("Activity") && n.length() > 8) {
			String base = n.substring(0, n.length() - 8);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < base.length(); i++) {
				char c = base.charAt(i);
				if (Character.isUpperCase(c) && i > 0) sb.append('_');
				sb.append(Character.toLowerCase(c));
			}
			return sb.toString();
		}
		StringBuilder sb = new StringBuilder();
		boolean up = true;
		for (int i = 0; i < n.length(); i++) {
			char c = n.charAt(i);
			if (c == '_') { up = true; continue; }
			sb.append(up ? Character.toUpperCase(c) : c);
			up = false;
		}
		if (sb.length() == 0) return null;
		String j = sb.toString();
		if (!j.endsWith("Activity")) j = j + "Activity";
		return j;
	}
	
	public void loadLayout(String layoutName) {
		editorLayout.removeAllViews();
		if (layoutName == null || layoutName.trim().isEmpty()) return;
		
		stableUndoManager.clearHistory(getCurrentActivityName());
		
		syncActivityUiPreview();
		
		oldIdManager = snapshotIdManager(idManager);
		HashMap<View, AttributeSet> oldSnapshot = attributeStore.backupAndSwapOld();
		resetIdManagerInPlace();
		attributeStore.clear();
		
		if (layoutPersistence.rootLayoutFileExists()) {
			try {
				layoutPersistence.loadLayoutFromJson(editorLayout, layoutName);
				if (editorLayout.getChildCount() == 0) {
					String alt = alternateLayoutKey(layoutName);
					if (alt != null) {
						try { layoutPersistence.loadLayoutFromJson(editorLayout, alt); } catch (Exception ignore) {}
					}
				}
				ensureRegistryAfterLoad();
				Log.d("ViewEditor", "Loaded from JSON: " + layoutName);
				return;
			} catch (Exception e) {
				Log.e("ViewEditor", "JSON load failed", e);
				editorLayout.removeAllViews();
				resetIdManagerInPlace();
				attributeStore.clear();
			}
		}
		Log.d("ViewEditor", "No saved layout found: " + layoutName);
		restorePreviousStateOnFailure();
	}
	
	public interface LoadCallback {
		void onLoaded();
		void onFailed(Exception e);
	}
	
	public void loadLayoutAsync(final String layoutName, final LoadCallback callback) {
		try {
			if (loadLayoutTask != null) {
				loadLayoutTask.cancel(true);
				loadLayoutTask = null;
			}
		} catch (Throwable ignore) {}
		
		if (editorLayout != null) {
			editorLayout.removeAllViews();
		}
		try {
			resetIdManagerInPlace();
			attributeStore.clear();
		} catch (Throwable ignore) {}
		
		if (layoutName == null || layoutName.trim().isEmpty()) {
			if (callback != null) callback.onLoaded();
			return;
		}
		
		try {
			stableUndoManager.clearHistory(getCurrentActivityName());
		} catch (Throwable ignore) {}
		
		syncActivityUiPreview();
		
		oldIdManager = snapshotIdManager(idManager);
		try {
			attributeStore.backupAndSwapOld();
		} catch (Throwable ignore) {}
		resetIdManagerInPlace();
		attributeStore.clear();
		
		final String key = layoutName.trim();
		
		if (sParsedLayoutCache.containsKey(key)) {
			LayoutPersistence.ParsedLayout cached = sParsedLayoutCache.get(key);
			try {
				if (editorLayout != null) editorLayout.removeAllViews();
				resetIdManagerInPlace();
				attributeStore.clear();
				if (cached != null) {
					layoutPersistence.applyParsedLayout(editorLayout, cached);
				}
				ensureRegistryAfterLoad();
				syncBeansFromStore();
				restoreHistoryStacks(key);
				refreshUndoRedoButtons();
				Log.d("ViewEditor", "Loaded from MEMORY cache: " + key);
				if (callback != null) callback.onLoaded();
			} catch (Exception e) {
				Log.e("ViewEditor", "cache apply failed, will reload disk", e);
				sParsedLayoutCache.remove(key);
			}
			if (sParsedLayoutCache.containsKey(key)) {
				return;
			}
		}
		
		loadLayoutTask = new AsyncTask<Void, Void, LayoutPersistence.ParsedLayout>() {
			private Exception error;
			
			@Override
			protected LayoutPersistence.ParsedLayout doInBackground(Void... params) {
				try {
					return layoutPersistence.parseLayout(key);
				} catch (Exception e) {
					error = e;
					return null;
				}
			}
			
			@Override
			protected void onPostExecute(LayoutPersistence.ParsedLayout parsed) {
				loadLayoutTask = null;
				if (isCancelled()) return;
				if (editorLayout != null) {
					editorLayout.removeAllViews();
				}
				if (error != null) {
					Log.e("ViewEditor", "JSON load failed key=" + key, error);
					resetIdManagerInPlace();
					attributeStore.clear();
					if (callback != null) callback.onFailed(error);
					return;
				}
				if (parsed == null) {
					Log.d("ViewEditor", "No entry for layout key=" + key + " — empty canvas");
					resetIdManagerInPlace();
					attributeStore.clear();
					if (callback != null) callback.onLoaded();
					return;
				}
				try {
					layoutPersistence.applyParsedLayout(editorLayout, parsed);
					if (parsed != null) {
						sParsedLayoutCache.put(key, parsed);
					}
					ensureRegistryAfterLoad();
					syncBeansFromStore();
					restoreHistoryStacks(key);
					refreshUndoRedoButtons();
					Log.d("ViewEditor", "Loaded from JSON (async) + cached: " + key);
					if (callback != null) callback.onLoaded();
				} catch (Exception e) {
					Log.e("ViewEditor", "apply failed key=" + key, e);
					if (editorLayout != null) editorLayout.removeAllViews();
					resetIdManagerInPlace();
					attributeStore.clear();
					if (callback != null) callback.onFailed(e);
				}
			}
		};
		loadLayoutTask.execute((Void[]) null);
	}
	
	public void loadLayoutFromJson(String layoutName) throws Exception {
		layoutPersistence.loadLayoutFromJson(editorLayout, layoutName);
		refreshUndoRedoButtons();
	}
	
	private void restorePreviousStateOnFailure() {
		Runnable restore = new Runnable() {
			@Override
			public void run() {
				restoreIdManagerFromOld();
				attributeStore.restoreFrom(attributeStore.getOldAttributesValueMap());
				editorLayout.removeAllViews();
				for (View v : attributeStore.getOldAttributesValueMap().keySet()) {
					if (v.getParent() == null) editorLayout.addView(v);
				}
			}
		};
		if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
			post(restore);
		} else {
			restore.run();
		}
	}
	
	private void applyAllAttributesAfterLoad() {
		for (View v : attributeStore.keySet()) {
			AttributeSet set = attributeStore.get(v);
			if (set == null) continue;
			for (Attribute a : set.getAttributes()) {
				applyAttribute(v, a);
			}
		}
		for (View v : attributeStore.keySet()) {
			AttributeSet set = attributeStore.get(v);
			if (set == null) continue;
			for (Attribute a : set.getAttributes()) {
				String name = a.getName();
				String val = a.getValue();
				if (val == null || val.isEmpty()) continue;
				try {
					if (name.startsWith("android:padding")) {
						attributeApplier.applyPaddingAttribute(v, name, val);
					} else if (name.startsWith("android:layout_margin")) {
						attributeApplier.applyMarginAttribute(v, name, val);
					}
				} catch (Exception e) {
					Log.w("ViewEditor", "applyAllAttributesAfterLoad failed for " + name, e);
				}
			}
		}
	}
	
	
	public String getXMLCode() {
		return xmlGenerator.getXMLCode(editorLayout, getCurrentActivityName(), getProjectComplex());
	}
	
	public String generateCode(View view) {
		return xmlGenerator.generateCode(view);
	}
	
	
	public void checkAndRestoreBackup(String layoutName) {
		backupManager.checkAndRestoreBackup(layoutName);
	}
	
	private void showDeleteImage() {
		if (deleteImg == null) return;
		deleteImg.post(new Runnable() {
			@Override
			public void run() {
				deleteImg.setVisibility(View.VISIBLE);
				deleteImg.setAlpha(1f);
				deleteImg.bringToFront();
				dragController.setDeleteTarget(deleteImg);
			}
		});
	}
	
	private void hideDeleteImage() {
		if (deleteImg == null) return;
		deleteImg.post(new Runnable() {
			@Override
			public void run() {
				deleteImg.animate().alpha(0f).setDuration(200)
				.withEndAction(new Runnable() {
					@Override
					public void run() {
						deleteImg.setVisibility(View.GONE);
					}
				}).start();
			}
		});
	}
	
	private void applyContainerStroke(View view) {
		if (view instanceof ViewGroup) {
			int dp = (int) SketchwareUtil.getDip(getContext(), 1);
			int stroke = uiHelpers != null
			? uiHelpers.resolveThemeColor(android.R.attr.textColorSecondary, 0xFF888888)
			: 0xFF888888;
			view.setBackground(UiUtils.createStrokedBackground(0, stroke, dp));
		}
	}
	
	
	public static List<String> getIdsByType(String type) {
		List<String> ids = new ArrayList<String>();
		for (View view : idManager.getViews()) {
			String cn = view.getClass().getSimpleName();
			if ("all".equalsIgnoreCase(type) || cn.equalsIgnoreCase(type)) {
				ids.add(idManager.getId(view));
			}
		}
		return ids;
	}
	
	public static Map<String, String> getIdsWithClass(String type, String activityName) {
		return IdXmlScanner.getIdsWithClass(type, activityName, SAVE_PATH);
	}
	
	
	private void showMaterialWidgetDialog(final Context context,
	final ArrayList<HashMap<String, Object>> widgetList) {
		new WidgetClassPickerDialog(context, new OnWidgetClassSelectedListener() {
			@Override
			public void onWidgetClassSelected(String widgetName, String classPath) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("name", widgetName);
				map.put("class_path", classPath);
				widgetList.add(map);
				if (paletteAdapter != null) paletteAdapter.refresh();
			}
		}).show();
	}
	
	public void setDefaultTextSize(View view) {
		if (view instanceof android.widget.TextView) {
			((android.widget.TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		}
	}
	
	private String getWidgetTypeName(View view) {
		String className = view.getClass().getSimpleName();
		if (className.startsWith("Widget")) return className.substring(6);
		return className;
	}
	
	
	private ArrayList<HashMap<String, Object>> injectPaletteCategories(
	ArrayList<HashMap<String, Object>> src) {
		ArrayList<HashMap<String, Object>> out = new ArrayList<HashMap<String, Object>>();
		if (src == null) return out;
		
		String[] order = new String[] {
			"Layouts", "Widgets", "Lists", "Scrolling", "Media", "AndroidX / Material"
		};
		java.util.LinkedHashMap<String, ArrayList<HashMap<String, Object>>> buckets =
		new java.util.LinkedHashMap<String, ArrayList<HashMap<String, Object>>>();
		for (String c : order) {
			buckets.put(c, new ArrayList<HashMap<String, Object>>());
		}
		for (int i = 0; i < src.size(); i++) {
			HashMap<String, Object> item = src.get(i);
			if (item == null) continue;
			if ("header".equals(String.valueOf(item.get("type")))) continue;
			String name = item.containsKey("name") ? String.valueOf(item.get("name")) : "";
			String cat = paletteCategoryFor(name);
			if (!buckets.containsKey(cat)) {
				buckets.put(cat, new ArrayList<HashMap<String, Object>>());
			}
			buckets.get(cat).add(item);
		}
		for (String c : order) {
			ArrayList<HashMap<String, Object>> list = buckets.get(c);
			if (list == null || list.isEmpty()) continue;
			HashMap<String, Object> header = new HashMap<String, Object>();
			header.put("type", "header");
			header.put("name", c);
			out.add(header);
			out.addAll(list);
		}
		return out;
	}
	
	private String paletteCategoryFor(String name) {
		if (name == null || name.length() == 0) return "Widgets";
		
		if (name.equals("LinearLayout") || name.equals("RelativeLayout") || name.equals("FrameLayout")
		|| name.equals("GridLayout") || name.equals("TableLayout")
		|| name.equals("ConstraintLayout") || name.equals("MotionLayout")
		|| name.equals("CoordinatorLayout")) {
			return "Layouts";
		}
		if (name.equals("ScrollView") || name.equals("HorizontalScrollView") || name.equals("NestedScrollView")) {
			return "Scrolling";
		}
		if (name.equals("ListView") || name.equals("GridView") || name.equals("ExpandableListView")
		|| name.equals("Spinner") || name.equals("RecyclerView")) {
			return "Lists";
		}
		if (name.equals("WebView") || name.equals("WebView2") || name.equals("VideoView")) {
			return "Media";
		}
		if (name.equals("CardView") || name.equals("MaterialCardView") || name.equals("MaterialButton")
		|| name.equals("MaterialSwitch") || name.equals("TextInputLayout")
		|| name.equals("BottomNavigationView") || name.equals("NavigationView")
		|| name.equals("TabLayout") || name.equals("Chip") || name.equals("ChipGroup")
		|| name.equals("CollapsingToolbarLayout") || name.equals("ViewPager")
		|| name.equals("ViewPager2") || name.equals("LottieAnimationView")
		|| name.equals("CircleImageView") || name.startsWith("Material")) {
			return "AndroidX / Material";
		}
		return "Widgets";
	}
	
	private View createPlaceholderOrStandardView(String tag) {
		if (tag == null || tag.trim().isEmpty()) return null;
		if ("WebView".equals(tag) || "VideoView".equals(tag)
		|| "SurfaceView".equals(tag) || "TextureView".equals(tag)
		|| "GLSurfaceView".equals(tag) || "Space".equals(tag)) {
			return new PlaceholderWidget(getContext(), tag);
		}
		return DynamicWidgetFactory.resolve(getContext(), tag, null);
	}
	
	
	View createPlaceholderOrStandardViewPublic(String tag) {
		return createPlaceholderOrStandardView(tag);
	}
	
	void putAttributeSetPublic(View view, AttributeSet set) {
		attributeStore.put(view, set);
	}
	
	View.OnDragListener getDragListenerPublic() {
		return dragController != null ? dragController.getDragListener() : null;
	}
	
	void applyContainerStrokePublic(View view) {
		applyContainerStroke(view);
	}
	
	void applyAllAttributesAfterLoadPublic() {
		applyAllAttributesAfterLoad();
	}
	
	private String getStringResourceValue(String scId, String resourceName) {
		return StringResourceReader.read(scId, resourceName);
	}
	
	public Object createObject(Class<?> cls) {
		try {
			Object obj = cls.getConstructor().newInstance();
			if (obj instanceof View) setDefaultTextSize((View) obj);
			return obj;
		} catch (Exception ignored) {}
		for (java.lang.reflect.Constructor<?> constructor : cls.getConstructors()) {
			Class<?>[] params = constructor.getParameterTypes();
			Object[] args = new Object[params.length];
			for (int i = 0; i < params.length; i++) {
				args[i] = Context.class.isAssignableFrom(params[i])
				? getContext()
				: PrimitiveUtils.getDefaultValue(params[i]);
			}
			try {
				Object obj = constructor.newInstance(args);
				if (obj instanceof View) setDefaultTextSize((View) obj);
				return obj;
			} catch (Exception ignored) {}
		}
		return null;
	}
	
	public void copy(String text) {
		((ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE))
		.setPrimaryClip(ClipData.newPlainText("clipboard", text));
	}
	
	public void log(String message) {
		try {
			String logPath = FileUtil.getPackageDataDir(((Activity) getContext()))
			+ "/log.txt";
			FileUtil.writeFile(logPath, FileUtil.readFile(logPath) + "\n" + message);
		} catch (Exception ignored) {}
	}
	
	@Deprecated
	public float getDip(int input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
		input, getResources().getDisplayMetrics());
	}
	
	public void showMessage(String msg) {
		Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
	}
	
	private String getCurrentActivityName() {
		if (designActivity != null && designActivity.activityBean != null) {
			return designActivity.activityBean.getActivityName();
		}
		return ProjectDataHelper.getActivityName(getContext());
	}
	
	
	/**
	 * Fake phone chrome in the preview: status bar = colorPrimaryDark, toolbar = colorPrimary
	 * (from ResourceManager). Text/icons get a readable contrast colour and the clock shows
	 * the real device time.
	 */
	public void applyPreviewChromeColors() {
		try {
			com.nexusteam.blacklogics.generator.source.ResourceManager rm =
					new com.nexusteam.blacklogics.generator.source.ResourceManager();
			rm.load(getContext(), SC_ID);
			java.util.Map<String, String> colors = rm.getColors();
			Integer primary = parsePreviewColor(colors.get("colorPrimary"));
			Integer primaryDark = parsePreviewColor(colors.get("colorPrimaryDark"));
			
			if (phone_action_bar != null && primary != null) {
				phone_action_bar.setBackgroundColor(primary.intValue());
				android.widget.TextView title = findViewById(R.id.textview6);
				if (title != null) title.setTextColor(previewContrastColor(primary.intValue()));
			}
			
			if (phone_status_bar != null && primaryDark != null) {
				phone_status_bar.setBackgroundColor(primaryDark.intValue());
				int fg = previewContrastColor(primaryDark.intValue());
				android.widget.TextView time = findViewById(R.id.textview5);
				if (time != null) time.setTextColor(fg);
				int[] iconIds = { R.id.imageview4, R.id.imageview5, R.id.imageview6 };
				for (int id : iconIds) {
					ImageView icon = findViewById(id);
					if (icon != null) icon.setColorFilter(fg, PorterDuff.Mode.SRC_IN);
				}
			}
			
			android.widget.TextView time = findViewById(R.id.textview5);
			if (time != null) {
				String pattern = android.text.format.DateFormat.is24HourFormat(getContext()) ? "HH:mm" : "h:mm";
				time.setText(new java.text.SimpleDateFormat(pattern, java.util.Locale.getDefault())
						.format(new java.util.Date()));
			}
		} catch (Throwable t) {
			Log.w("ViewEditor", "applyPreviewChromeColors failed", t);
		}
	}
	
	/** White on dark backgrounds, near-black on light ones. */
	private int previewContrastColor(int bg) {
		return androidx.core.graphics.ColorUtils.calculateLuminance(bg) > 0.5
				? Color.argb(222, 0, 0, 0) : Color.WHITE;
	}
	
	/**
	 * Sizes the status bar / toolbar like a real phone (24dp / 56dp) scaled by the same
	 * factor the canvas is scaled by, so the mock keeps real proportions.
	 */
	private void updatePreviewChromeSize(float scale) {
		if (scale <= 0f) return;
		try {
			if (phone_status_bar != null) {
				setPreviewHeight(phone_status_bar, previewPx(24, scale));
				int pad = previewPx(8, scale);
				phone_status_bar.setPadding(pad, 0, pad, 0);
				android.widget.TextView time = findViewById(R.id.textview5);
				if (time != null) time.setTextSize(TypedValue.COMPLEX_UNIT_PX, previewPx(12, scale));
				int[] iconIds = { R.id.imageview4, R.id.imageview5, R.id.imageview6 };
				for (int id : iconIds) {
					View icon = findViewById(id);
					if (icon == null) continue;
					ViewGroup.LayoutParams lp = icon.getLayoutParams();
					int size = previewPx(14, scale);
					if (lp != null && (lp.width != size || lp.height != size)) {
						lp.width = size;
						lp.height = size;
						icon.setLayoutParams(lp);
					}
				}
			}
			if (phone_action_bar != null) {
				setPreviewHeight(phone_action_bar, previewPx(56, scale));
				int pad = previewPx(16, scale);
				phone_action_bar.setPadding(pad, 0, pad, 0);
				android.widget.TextView title = findViewById(R.id.textview6);
				if (title != null) title.setTextSize(TypedValue.COMPLEX_UNIT_PX, previewPx(20, scale));
			}
		} catch (Throwable t) {
			Log.w("ViewEditor", "updatePreviewChromeSize failed", t);
		}
	}
	
	private int previewPx(float dp, float scale) {
		return Math.max(1, Math.round(TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, dp * scale, getResources().getDisplayMetrics())));
	}
	
	private void setPreviewHeight(View v, int heightPx) {
		ViewGroup.LayoutParams lp = v.getLayoutParams();
		if (lp != null && lp.height != heightPx) {
			lp.height = heightPx;
			v.setLayoutParams(lp);
		}
	}
	
	private Integer parsePreviewColor(String value) {
		if (value == null) return null;
		try {
			return Integer.valueOf(Color.parseColor(value.trim()));
		} catch (Throwable t) {
			return null;
		}
	}
	
	public void syncActivityUiPreview() {
		String activityName = getCurrentActivityName();
		if (activityName == null || activityName.trim().isEmpty()) return;
		
		ActivityConfig uiConfig = new ActivityConfig();
		uiConfig.init(getContext(), SC_ID);
		
		Complex complex = getProjectComplex();
		
		if (phone_action_bar != null) {
			boolean tb = uiConfig.hasToolbar(activityName);
			phone_action_bar.setVisibility(tb ? View.VISIBLE : View.GONE);
		}
		
		if (phone_status_bar != null) {
			boolean sb = uiConfig.hasStatusBar(activityName);
			phone_status_bar.setVisibility(sb ? View.VISIBLE : View.GONE);
		}
		
		applyPreviewChromeColors();
		
		// Simple Sketchware-style FAB in ViewEditor (not ViewBuilder logic)
		boolean fabEnabled = isActivityFabEnabled(activityName, uiConfig, complex);
		if (fabEnabled) {
			enableEditorFab();
		} else {
			disableEditorFab();
		}
	}
	
	/**
* Whether this activity has FAB enabled (ActivityConfig + Complex fallback),
* same logic XmlGenerator uses for injectChrome.
*/	
	private boolean isActivityFabEnabled(String activityName, ActivityConfig uiConfig, Complex complex) {
		if (activityName == null || activityName.trim().isEmpty()) return false;
		boolean hasFab = false;
		try {
			if (uiConfig != null) hasFab = uiConfig.hasFab(activityName);
		} catch (Throwable ignore) {}
		if (!hasFab && complex != null) {
			try {
				String n = activityName;
				if (n.endsWith(".java")) n = n.substring(0, n.length() - 5);
				hasFab = complex.getEnableFabBoolean(n);
			} catch (Throwable ignore) {}
		}
		return hasFab;
	}
	
	/** True if the Sketchware-style editor FAB overlay is currently visible. */
	public boolean isEditorFabVisible() {
		// "visible" here = FAB is on the canvas (enabled); android:visibility is a separate attribute
		return editorFabView != null && editorFabView.getParent() != null;
	}
	
	/**
* Enable FAB in View Editor — Sketchware same behaviour:
* adds a fixed FloatingActionButton with id/tag "_fab" on the canvas.
*/	
	public void enableEditorFab() {
		Log.d(FTAG, "enableEditorFab called, existing=" + editorFabView);
		if (editorLayout == null && editorCanvasFrame == null) return;
		if (editorFabView != null && editorFabView.getParent() != null) {
			// visibility is owned by the FAB's android:visibility attribute — don't override it
			syncEditorFabRegistry();
			return;
		}
		try {
			ViewGroup host = ensureFabOverlayHost();
			Log.d(FTAG, "host=" + host);
			if (host == null) return;

			View fab = createEditorFabView();
			Log.d(FTAG, "fab=" + fab);
			if (fab == null) return;
			editorFabView = fab;
			fab.setTag("_fab");

			// Position is NOT done with gravity/margins: it is computed from the FAB's own
			// attributes against the (scaled) phone canvas — see layoutEditorFab().
			FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			flp.gravity = Gravity.TOP | Gravity.START;
			fab.setLayoutParams(flp);

			if (fab.getParent() instanceof ViewGroup) {
				((ViewGroup) fab.getParent()).removeView(fab);
			}
			host.addView(fab, flp);
			try {
				fab.bringToFront();
				host.bringChildToFront(fab);
			} catch (Throwable ignore) {}

			// id + real attributes are registered synchronously, BEFORE the first frame,
			// so the id chip / attributes sheet are ready the instant the FAB is visible.
			syncEditorFabRegistry();
			installFabPositioner(fab);
			Log.d(FTAG, "added, parent=" + fab.getParent()
					+ " id=" + (idManager == null ? null : idManager.getId(fab)));
		} catch (Throwable t) {
			Log.e(FTAG, "enableEditorFab failed " + errChain(t), t);
		}
	}

	/**
* Phone canvas FrameLayout from view_editor.xml — FAB overlays here (bottom|end).
* Hierarchy: editorCanvasFrame > editorCanvasViewport > editorLayout
*/	
	private ViewGroup ensureFabOverlayHost() {
		if (editorCanvasFrame != null) return editorCanvasFrame;
		try {
			View v = findViewById(R.id.editorCanvasFrame);
			if (v instanceof FrameLayout) {
				editorCanvasFrame = (FrameLayout) v;
				return editorCanvasFrame;
			}
		} catch (Throwable ignore) {}
		// Fallback: parent of viewport if it is already a FrameLayout
		if (editorCanvasViewport != null) {
			ViewParent p = editorCanvasViewport.getParent();
			if (p instanceof FrameLayout) return (ViewGroup) p;
		}
		if (editorLayout != null) {
			ViewParent p = editorLayout.getParent();
			if (p instanceof FrameLayout) return (ViewGroup) p;
			p = (editorLayout.getParent() instanceof View)
			? ((View) editorLayout.getParent()).getParent() : null;
			if (p instanceof FrameLayout) return (ViewGroup) p;
		}
		return null;
	}
	
	/** No-op unwrap — we no longer wrap the hierarchy; FAB is a child of editorCanvasFrame. */
	private void unwrapFabOverlayHost() {
		// intentionally empty
	}
	
	/**
* Disable FAB in View Editor — removes the _fab overlay.
*/	
	public void disableEditorFab() {
		if (editorFabView == null) {
			unwrapFabOverlayHost();
			return;
		}
		try {
			// keep the user's attributes so re-enabling restores them
			saveEditorFabAttributes();
			removeFabPositioner();
			setOverlayTapTarget(null);
			if (xmlGenerator != null) xmlGenerator.setEditorFabView(null);
			if (idManager != null) {
				idManager.remove(editorFabView, true);
			}
			if (attributeStore != null) {
				attributeStore.remove(editorFabView, true);
			}
			try {
				attributesValueMap.remove(editorFabView);
			} catch (Throwable ignore) {}
			ViewParent p = editorFabView.getParent();
			if (p instanceof ViewGroup) {
				((ViewGroup) p).removeView(editorFabView);
			}
		} catch (Throwable t) {
			Log.w("ViewEditor", "disableEditorFab failed", t);
		}
		editorFabView = null;
		unwrapFabOverlayHost();
	}

	/** Toggle FAB on/off and persist preference when possible. */
	public void toggleEditorFab() {
		String activityName = getCurrentActivityName();
		ActivityConfig uiConfig = new ActivityConfig();
		try {
			uiConfig.init(getContext(), SC_ID);
		} catch (Throwable ignore) {}
		Complex complex = getProjectComplex();
		boolean currently = isActivityFabEnabled(activityName, uiConfig, complex)
		|| isEditorFabVisible();
		if (currently) {
			disableEditorFab();
			persistFabEnabled(activityName, false);
			showMessage("FAB Disabled");
		} else {
			enableEditorFab();
			persistFabEnabled(activityName, true);
			showMessage("FAB Enabled");
		}
	}
	
	private void persistFabEnabled(String activityName, boolean enabled) {
		if (activityName == null) return;
		String n = activityName;
		if (n.endsWith(".java")) n = n.substring(0, n.length() - 5);
		try {
			Complex complex = getProjectComplex();
			if (complex != null) {
				try {
					java.lang.reflect.Method m = complex.getClass()
					.getMethod("setEnableFabBoolean", String.class, boolean.class);
					m.invoke(complex, n, enabled);
					return;
				} catch (NoSuchMethodException ignored) {}
				try {
					java.lang.reflect.Method m = complex.getClass()
					.getMethod("setEnableFab", String.class, boolean.class);
					m.invoke(complex, n, enabled);
					return;
				} catch (NoSuchMethodException ignored) {}
				try {
					java.lang.reflect.Method m = complex.getClass()
					.getMethod("setFabEnabled", String.class, boolean.class);
					m.invoke(complex, n, enabled);
					return;
				} catch (NoSuchMethodException ignored) {}
			}
		} catch (Throwable t) {
			Log.w("ViewEditor", "persistFabEnabled Complex failed", t);
		}
		try {
			ActivityConfig uiConfig = new ActivityConfig();
			uiConfig.init(getContext(), SC_ID);
			try {
				java.lang.reflect.Method m = uiConfig.getClass()
				.getMethod("setFab", String.class, boolean.class);
				m.invoke(uiConfig, activityName, enabled);
				return;
			} catch (NoSuchMethodException ignored) {}
			try {
				java.lang.reflect.Method m = uiConfig.getClass()
				.getMethod("setHasFab", String.class, boolean.class);
				m.invoke(uiConfig, activityName, enabled);
				return;
			} catch (NoSuchMethodException ignored) {}
			try {
				java.lang.reflect.Method m = uiConfig.getClass()
				.getMethod("setEnableFab", String.class, boolean.class);
				m.invoke(uiConfig, activityName, enabled);
			} catch (NoSuchMethodException ignored) {}
		} catch (Throwable t) {
			Log.w("ViewEditor", "persistFabEnabled ActivityConfig failed", t);
		}
	}
	private static final String FTAG = "FabForce";

	private String errChain(Throwable t) {
		StringBuilder sb = new StringBuilder();
		int guard = 0;
		while (t != null && guard++ < 6) {
			sb.append(t.getClass().getSimpleName()).append(": ")
			.append(t.getMessage()).append(" <- ");
			t = t.getCause();
		}
		return sb.toString();
	}

	private boolean isNightMode(Context c) {
		int m = c.getResources().getConfiguration().uiMode
		& android.content.res.Configuration.UI_MODE_NIGHT_MASK;
		return m == android.content.res.Configuration.UI_MODE_NIGHT_YES;
	}

	private View tryRealFab(String label, Context ctx) {
		try {
			Log.d(FTAG, "try [" + label + "] ctx=" + ctx.getClass().getName());
			com.google.android.material.floatingactionbutton.FloatingActionButton fab =
			new com.google.android.material.floatingactionbutton.FloatingActionButton(ctx);
			Log.i(FTAG, "SUCCESS [" + label + "]");
			return fab;
		} catch (Throwable t) {
			Log.e(FTAG, "FAIL [" + label + "] " + errChain(t), t);
			return null;
		}
	}

	private View createEditorFabView() {
		final Context base = getContext();
		Log.d(FTAG, "base ctx=" + base.getClass().getName() + " pkg=" + base.getPackageName());

		try {
			android.util.TypedValue tv = new android.util.TypedValue();
			boolean pv = base.getTheme().resolveAttribute(
					com.google.android.material.R.attr.colorPrimaryVariant, tv, true);
			boolean pc = base.getTheme().resolveAttribute(
					com.google.android.material.R.attr.colorPrimaryContainer, tv, true);
			Log.d(FTAG, "base theme: colorPrimaryVariant=" + pv + " colorPrimaryContainer=" + pc);
		} catch (Throwable t) { Log.w(FTAG, "attr probe failed", t); }

		try {
			Class.forName("com.google.android.material.floatingactionbutton.FloatingActionButton");
			Log.d(FTAG, "FAB class present");
		} catch (Throwable t) {
			Log.e(FTAG, "FAB CLASS MISSING " + errChain(t));
		}

		boolean night = isNightMode(base);
		int appTheme = night ? R.style.AppTheme_Dark : R.style.AppTheme;

		String[] labels = {
			"base", "AppTheme(auto)", "AppTheme", "AppTheme.Dark",
			"M3 DayNight", "M3 Light", "MC DayNight", "MC Light",
			"MC DayNight Bridge", "MC Light Bridge"
		};
		int[] themes = {
			0, appTheme, R.style.AppTheme, R.style.AppTheme_Dark,
			com.google.android.material.R.style.Theme_Material3_DayNight_NoActionBar,
			com.google.android.material.R.style.Theme_Material3_Light_NoActionBar,
			com.google.android.material.R.style.Theme_MaterialComponents_DayNight_NoActionBar,
			com.google.android.material.R.style.Theme_MaterialComponents_Light_NoActionBar,
			com.google.android.material.R.style.Theme_MaterialComponents_DayNight_Bridge,
			com.google.android.material.R.style.Theme_MaterialComponents_Light_Bridge
		};

		for (int i = 0; i < themes.length; i++) {
			Context c = themes[i] == 0 ? base
			: new android.view.ContextThemeWrapper(base, themes[i]);
			View v = tryRealFab(labels[i], c);
			if (v instanceof com.google.android.material.floatingactionbutton.FloatingActionButton) {
				styleEditorFab(
				(com.google.android.material.floatingactionbutton.FloatingActionButton) v, base, 0);
				return v;
			}
		}

		try {
			View r = com.nexusteam.blacklogics.sdk.utils.DynamicWidgetFactory.resolve(
					base, "FloatingActionButton", null);
			Log.d(FTAG, "DynamicWidgetFactory returned "
					+ (r == null ? "null" : r.getClass().getName()));
			if (r instanceof com.google.android.material.floatingactionbutton.FloatingActionButton) {
				styleEditorFab(
				(com.google.android.material.floatingactionbutton.FloatingActionButton) r, base, 0);
				return r;
			}
		} catch (Throwable t) {
			Log.e(FTAG, "DynamicWidgetFactory FAIL " + errChain(t), t);
		}

		Log.e(FTAG, "ALL strategies failed - no real FAB");
		showMessage("Real FAB create failed - logcat tag FabForce");
		return null;
	}
	
	private void styleEditorFab(
	com.google.android.material.floatingactionbutton.FloatingActionButton fab,
	Context base, int size) {
		try { fab.setImageResource(android.R.drawable.ic_input_add); } catch (Throwable ignore) {}
		fab.setTag("_fab");
		fab.setVisibility(View.VISIBLE);
	}
	
	
	
	
	
	
	
	private void ensureEditorFabRegistered() {
		final View fab = editorFabView;
		if (fab == null || idManager == null) return;
		try {
			AttributeSet set = attributeStore.get(fab);
			String wanted = FabAttributeStore.idNameOf(set);

			String existing = idManager.getId(fab);
			if (existing != null && existing.length() > 0) {
				if (idManager.getView(existing) == fab) return;   // already healthy
				idManager.remove(fab);                             // stale half-entry
			}

			String name = wanted;
			if (idManager.isIdUsed(name)) {
				View old = idManager.getView(name);
				if (old == fab) return;
				if (old != null) {
					boolean liveCanvasWidget = old.getParent() != null && attributeStore.contains(old);
					if (liveCanvasWidget) {
						int n = 1;
						while (idManager.isIdUsed(wanted + n)) n++;
						name = wanted + n;
					} else {
						try { idManager.remove(old, true); } catch (Throwable ignore) {
							try { idManager.remove(old); } catch (Throwable ignore2) {}
						}
					}
				}
			}
			idManager.addNewId(fab, name);
			if (set != null && !name.equals(wanted)) {
				set.add(new Attribute("android:id", "@+id/" + name));
			}
		} catch (Throwable t) {
			Log.w("ViewEditor", "ensureEditorFabRegistered", t);
		}
	}

	private boolean fabApplyingAttrs;

	/**
	 * FAB gets a REAL attribute set (saved one, else defaults) in attributeStore — the same
	 * store every other widget uses — and those attributes are APPLIED to the live view,
	 * so preview, attributes sheet and generated XML always agree.
	 */
	private void ensureEditorFabAttributes() {
		final View fab = editorFabView;
		if (fab == null) return;
		try {
			AttributeSet set = attributeStore.get(fab);
			boolean fresh = (set == null);
			if (fresh) {
				set = FabAttributeStore.loadOrDefaults(getContext(), SC_ID, getCurrentActivityName());
				attributeStore.put(fab, set);
			}
			FabAttributeStore.ensureId(set);
			if (fresh) {
				applyFabAttributesToView(fab, set);
			}
		} catch (Throwable t) {
			Log.w("ViewEditor", "ensureEditorFabAttributes", t);
		}
	}

	private void applyFabAttributesToView(View fab, AttributeSet set) {
		fabApplyingAttrs = true;
		try {
			for (Attribute a : set.getAttributes()) {
				String n = a.getName();
				if (n == null || "android:id".equals(n) || n.startsWith("xmlns:")) continue;
				if (a.getValue() == null || a.getValue().length() == 0) continue;
				try {
					attributeApplier.applyAttribute(fab, a);
				} catch (Throwable t) {
					Log.w(FTAG, "apply " + n + " failed", t);
				}
			}
		} finally {
			fabApplyingAttrs = false;
		}
		neutralizeFabLayoutParams();
	}

	/** Layout params are kept neutral (top|start, 0 margins); real position comes from attributes. */
	private void neutralizeFabLayoutParams() {
		final View fab = editorFabView;
		if (fab == null) return;
		try {
			ViewGroup.LayoutParams raw = fab.getLayoutParams();
			if (raw instanceof FrameLayout.LayoutParams) {
				FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) raw;
				lp.gravity = Gravity.TOP | Gravity.START;
				lp.setMargins(0, 0, 0, 0);
				try { lp.setMarginStart(0); lp.setMarginEnd(0); } catch (Throwable ignore) {}
				fab.setLayoutParams(lp);
			}
		} catch (Throwable ignore) {}
	}

	/** Called after ANY attribute was applied to the FAB (dialog, undo, redo, margin editor...). */
	private void onEditorFabAttributeChanged() {
		if (fabApplyingAttrs) return;
		final View fab = editorFabView;
		if (fab == null) return;
		neutralizeFabLayoutParams();
		fab.post(new Runnable() {
			@Override public void run() { layoutEditorFab(); }
		});
		saveEditorFabAttributes();
	}

	/**
	 * XML -> canvas sync for the activity FAB: attributes found in the XML replace the FAB's
	 * real attribute set (same store/applier as every widget). Returns false when no overlay
	 * FAB is active, so the caller keeps its old behaviour.
	 */
	boolean mergeXmlIntoEditorFab(java.util.Map<String, String> xmlAttrs) {
		if (xmlAttrs == null) return false;
		final View fab = editorFabView;
		if (fab == null || fab.getParent() == null) return false;
		try {
			syncEditorFabRegistry();               // set may have been wiped by attributeStore.clear()
			AttributeSet set = attributeStore.get(fab);
			if (set == null) return false;
			fabApplyingAttrs = true;
			try {
				for (Attribute old : set.getAttributes()) {
					String n = old.getName();
					if (n == null || "android:id".equals(n) || n.startsWith("xmlns:")) continue;
					if (!xmlAttrs.containsKey(n)) set.remove(n);   // attribute deleted in XML
				}
				for (java.util.Map.Entry<String, String> e : xmlAttrs.entrySet()) {
					String n = e.getKey();
					if (n == null || "android:id".equals(n) || n.startsWith("xmlns:")) continue;
					Attribute a = new Attribute(n, e.getValue() == null ? "" : e.getValue());
					set.add(a);
					try { attributeApplier.applyAttribute(fab, a); } catch (Throwable t) {
						Log.w(FTAG, "xml apply " + n + " failed", t);
					}
				}
			} finally {
				fabApplyingAttrs = false;
			}
			FabAttributeStore.ensureId(set);
			onEditorFabAttributeChanged();
			return true;
		} catch (Throwable t) {
			Log.w(FTAG, "mergeXmlIntoEditorFab failed", t);
			return false;
		}
	}

	private void saveEditorFabAttributes() {
		try {
			if (editorFabView == null) return;
			AttributeSet set = attributeStore.get(editorFabView);
			if (set == null) return;
			FabAttributeStore.save(getContext(), SC_ID, getCurrentActivityName(), set);
		} catch (Throwable t) {
			Log.w("ViewEditor", "saveEditorFabAttributes", t);
		}
	}

	private void syncEditorFabRegistry() {
		final View fab = editorFabView;
		if (fab == null || fab.getParent() == null) return;
		ensureEditorFabAttributes();     // attributes first: they decide the id name
		ensureEditorFabRegistered();     // id is live immediately
		bindEditorFabClick(fab);
		setOverlayTapTarget(fab);        // canvas now routes taps on the FAB like any widget
		if (xmlGenerator != null) xmlGenerator.setEditorFabView(fab);
		try { ((ViewGroup) fab.getParent()).bringChildToFront(fab); } catch (Throwable ignore) {}
	}

	// ---- FAB positioning: attribute-driven, aligned to the scaled phone canvas ----

	private ViewTreeObserver.OnGlobalLayoutListener fabPositioner;

	private void installFabPositioner(View fab) {
		removeFabPositioner();
		fabPositioner = new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override public void onGlobalLayout() { layoutEditorFab(); }
		};
		try { fab.getViewTreeObserver().addOnGlobalLayoutListener(fabPositioner); } catch (Throwable ignore) {}
		fab.post(new Runnable() {
			@Override public void run() { layoutEditorFab(); }
		});
	}

	private void removeFabPositioner() {
		if (fabPositioner != null && editorFabView != null) {
			try {
				editorFabView.getViewTreeObserver().removeOnGlobalLayoutListener(fabPositioner);
			} catch (Throwable ignore) {}
		}
		fabPositioner = null;
	}

	private static String fabAttr(AttributeSet set, String name, String def) {
		if (set == null) return def;
		Attribute a = set.getAttribute(name);
		if (a == null || a.getValue() == null) return def;
		String v = a.getValue().trim();
		return v.isEmpty() ? def : v;
	}

	private int fabPx(String v) {
		if (v == null) return 0;
		String t = v.trim();
		if (t.isEmpty()) return 0;
		try {
			if (t.matches("-?\\d+(\\.\\d+)?")) t = t + "dp";
			return com.nexusteam.blacklogics.sdk.utils.DimensionUtils.getValueInPx(getContext(), t);
		} catch (Throwable ignore) {
			return 0;
		}
	}

	private void layoutEditorFab() {
		final View fab = editorFabView;
		if (fab == null || fab.getParent() == null || editorLayout == null) return;
		final int w = fab.getWidth();
		final int h = fab.getHeight();
		if (w <= 0 || h <= 0) return;   // not measured yet; listener fires again

		try {
			AttributeSet set = attributeStore.get(fab);

			float sx = editorLayout.getScaleX();
			float sy = editorLayout.getScaleY();
			if (sx <= 0f) sx = 1f;
			if (sy <= 0f) sy = 1f;

			ViewGroup.LayoutParams elp = editorLayout.getLayoutParams();
			float devW = (elp != null && elp.width > 0) ? elp.width
					: getResources().getDisplayMetrics().widthPixels;
			float devH = (elp != null && elp.height > 0) ? elp.height
					: getResources().getDisplayMetrics().heightPixels;

			// gravity from attribute
			String gravity = fabAttr(set, "android:layout_gravity", "top|start");
			boolean hEnd = false, hCenter = false, vBottom = false, vCenter = false;
			for (String tok : gravity.split("\\|")) {
				String g = tok.trim();
				if ("end".equals(g) || "right".equals(g)) hEnd = true;
				else if ("center_horizontal".equals(g)) hCenter = true;
				else if ("bottom".equals(g)) vBottom = true;
				else if ("center_vertical".equals(g)) vCenter = true;
				else if ("center".equals(g)) { hCenter = true; vCenter = true; }
			}

			// margins from attributes (specific ones override layout_margin)
			int all = fabPx(fabAttr(set, "android:layout_margin", "0"));
			int mL = all, mT = all, mR = all, mB = all;
			String v;
			if ((v = fabAttr(set, "android:layout_marginHorizontal", null)) != null) { mL = fabPx(v); mR = mL; }
			if ((v = fabAttr(set, "android:layout_marginVertical", null)) != null) { mT = fabPx(v); mB = mT; }
			if ((v = fabAttr(set, "android:layout_marginLeft", null)) != null) mL = fabPx(v);
			if ((v = fabAttr(set, "android:layout_marginStart", null)) != null) mL = fabPx(v);
			if ((v = fabAttr(set, "android:layout_marginRight", null)) != null) mR = fabPx(v);
			if ((v = fabAttr(set, "android:layout_marginEnd", null)) != null) mR = fabPx(v);
			if ((v = fabAttr(set, "android:layout_marginTop", null)) != null) mT = fabPx(v);
			if ((v = fabAttr(set, "android:layout_marginBottom", null)) != null) mB = fabPx(v);

			// position in unscaled device space
			float x = hEnd ? devW - w - mR : (hCenter ? (devW - w) / 2f + mL - mR : mL);
			float y = vBottom ? devH - h - mB : (vCenter ? (devH - h) / 2f + mT - mB : mT);

			// device origin on screen = canvas top-left (pivot is 0,0 so scale keeps it fixed)
			int[] canvasLoc = new int[2];
			int[] parentLoc = new int[2];
			editorLayout.getLocationOnScreen(canvasLoc);
			((View) fab.getParent()).getLocationOnScreen(parentLoc);

			float targetLeft = (canvasLoc[0] - parentLoc[0]) + x * sx;
			float targetTop  = (canvasLoc[1] - parentLoc[1]) + y * sy;

			fab.setPivotX(0f);
			fab.setPivotY(0f);
			fab.setScaleX(sx);
			fab.setScaleY(sy);
			fab.setTranslationX(targetLeft - fab.getLeft());
			fab.setTranslationY(targetTop - fab.getTop());
		} catch (Throwable t) {
			Log.w(FTAG, "layoutEditorFab failed", t);
		}
	}

	private void showFabToggleMenu(View anchor) {
		android.widget.PopupMenu popup = new android.widget.PopupMenu(getContext(), anchor);
		android.view.Menu menu = popup.getMenu();
		boolean on = isEditorFabVisible();
		menu.add(0, 1, 0, on ? "Disable FAB" : "Enable FAB");
		menu.add(0, 2, 1, "View select…");
		popup.setOnMenuItemClickListener(new android.widget.PopupMenu.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(android.view.MenuItem item) {
				if (item.getItemId() == 1) {
					toggleEditorFab();
					return true;
				}
				if (item.getItemId() == 2) {
					showViewSelectorDialog();
					return true;
				}
				return false;
			}
		});
		popup.show();
	}
	
	

	/**
	 * Create / update ViewBean in registry from live AttributeStore.
	 * Ensures attributes are always present on the bean (ViewEditor <-> ViewBean bridge).
	 */
	private void registerViewBeanFromLive(View widget) {
		if (widget == null || viewBeanRegistry == null || idManager == null) return;
		try {
			String id = idManager.getId(widget);
			if (id == null || id.isEmpty()) return;
			ViewBean bean = viewBeanRegistry.getBean(id);
			if (bean == null) {
				bean = new ViewBean();
				bean.id = id;
				viewBeanRegistry.putBean(id, bean);
			}
			AttributeSet set = attributeStore != null ? attributeStore.get(widget) : null;
			if (set != null) {
				java.util.Map<String, String> attrs = bean.getAttrs();
				for (Attribute a : set.getAttributes()) {
					if (a == null || a.getName() == null) continue;
					attrs.put(a.getName(), a.getValue() != null ? a.getValue() : "");
				}
			}
		} catch (Exception e) {
			android.util.Log.w("ViewEditor", "registerViewBeanFromLive failed", e);
		}
	}

	// ========== Sketchware-style Widget Collection (replaces WidgetBlueprint tree) ==========
	private SkCollectionListAdapter skCollectionAdapter;

	private SkCollectionIntegration.EditorHost buildCollectionHost() {
		return new SkCollectionIntegration.EditorHost() {
			@Override
			public android.content.Context getContext() {
				return ViewEditor.this.getContext();
			}

			@Override
			public AttributeStore getAttributeStore() {
				return attributeStore;
			}

			@Override
			public IdManager getIdManager() {
				return idManager != null ? idManager : IdManager.getInstance();
			}

			@Override
			public ViewBeanRegistry getBeanRegistry() {
				return viewBeanRegistry;
			}

			@Override
			public void applyAttribute(View view, Attribute attribute) {
				ViewEditor.this.applyAttribute(view, attribute);
			}

			@Override
			public void applyDefaultViewSetup(View view) {
				// no-op; editor applies defaults elsewhere
			}

			@Override
			public void ensureDefaultLayoutParams(View view) {
				if (view != null && view.getLayoutParams() == null) {
					view.setLayoutParams(new ViewGroup.LayoutParams(
							ViewGroup.LayoutParams.WRAP_CONTENT,
							ViewGroup.LayoutParams.WRAP_CONTENT));
				}
			}

			@Override
			public void applyContainerStroke(View view) {
				ViewEditor.this.applyContainerStroke(view);
			}

			@Override
			public void showMessage(String msg) {
				ViewEditor.this.showMessage(msg);
			}

			@Override
			public void addViewWithUndo(View view, ViewGroup parent, int index) {
				if (parent == null || view == null) return;
				if (view.getParent() == null) {
					if (index < 0 || index > parent.getChildCount()) parent.addView(view);
					else parent.addView(view, index);
				}
			}

			@Override
			public void notifyWidgetAdded(View widget, ViewGroup parent) {
				try {
					registerViewBeanFromLive(widget);
					selectedView = widget;
					if (selectedViews != null) {
						selectedViews.clear();
						selectedViews.add(widget);
					}
				} catch (Exception ignored) {}
			}

			@Override
			public void refreshUndoRedoButtons() {
				ViewEditor.this.refreshUndoRedoButtons();
			}

			@Override
			public ViewGroup getEditorRoot() {
				if (editorLayout == null) return null;
				if (selectedView instanceof ViewGroup
						&& !(selectedView instanceof android.widget.TextView)
						&& !(selectedView instanceof android.widget.ImageView)
						&& !(selectedView instanceof android.widget.Button)) {
					return (ViewGroup) selectedView;
				}
				if (editorLayout.getChildCount() > 0
						&& editorLayout.getChildAt(0) instanceof ViewGroup) {
					return (ViewGroup) editorLayout.getChildAt(0);
				}
				return editorLayout;
			}

			@Override
			public void showDeleteZone() {
				showDeleteImage();
			}

			@Override
			public void hideDeleteZone() {
				hideDeleteImage();
			}

			@Override
			public View getDeleteZoneView() {
				return deleteImg;
			}
		};
	}

	private void setupCollectionList() {
		if (collection_list == null) return;
		skCollectionAdapter = SkCollectionIntegration.setupList(
				getContext(),
				collection_list,
				buildCollectionHost()
		);
	}

	private void refreshCollectionTree() {
		SkCollectionIntegration.refresh(skCollectionAdapter);
	}

	// Old blueprint preview/add kept disabled — replaced by SkCollectionPreviewDialog
	// private void showCollectionWidgetPreview(...) {}
	// private void addBlueprintToEditor(...) {}

		public class DragListener implements View.OnDragListener {
		@Override
		public boolean onDrag(View destinationView, DragEvent event) {
			if (dragController != null && dragController.getDragListener() != null) {
				return dragController.getDragListener().onDrag(destinationView, event);
			}
			return false;
		}
	}
}
