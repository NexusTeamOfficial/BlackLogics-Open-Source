package com.nexusteam.blacklogics.sdk.layouteditor;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nexusteam.blacklogics.SketchwareUtil;
import com.nexusteam.blacklogics.sdk.AttributeSet;
import com.nexusteam.blacklogics.sdk.managers.WidgetRepository;
import com.nexusteam.blacklogics.sdk.models.WidgetBlueprint;
import com.nexusteam.blacklogics.sdk.utils.DynamicWidgetFactory;
import com.nexusteam.internal.os.layouteditor.EditorDragController;
import com.nexusteam.internal.os.layouteditor.undo.StableUndoManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WidgetDropHandler {

	public interface Host {
		Context getContext();
		AttributeStore getAttributeStore();
		IdManager getIdManager();
		EditorDragController getDragController();
		StableUndoManager getStableUndoManager();
		String getCurrentActivityName();
		void showMessage(String msg);
		void applyDefaultViewSetup(View view);
		void ensureDefaultLayoutParams(View view);
		void applyContainerStroke(View view);
		void applyAttribute(View view, Attribute attribute);
		void addViewWithUndo(View view, ViewGroup parent, int index);
		void setDefaultTextSize(View view);
		void refreshUndoRedoButtons();
		void notifyWidgetAdded(View widget, ViewGroup parent);
		ViewGroup getEditorLayout();
	}

	private final Host host;

	public WidgetDropHandler(Host host) {
		this.host = host;
	}

	private Context ctx() {
		return host.getContext();
	}

	public void handleDropNewWidget(ViewGroup destination, Object widgetData, DragEvent event) {
		if (widgetData instanceof HashMap) {
			@SuppressWarnings("unchecked")
			HashMap<String, Object> data = (HashMap<String, Object>) widgetData;
			Object bpObj = data.get("blueprint");
			if (bpObj instanceof WidgetBlueprint) {
				dropCollectionWidget((WidgetBlueprint) bpObj, destination, event);
			} else {
				dropRegularWidget(data, destination, event);
			}
		}
	}

	private void dropCollectionWidget(WidgetBlueprint blueprint,
	ViewGroup destinationView, DragEvent event) {
		try {
			View newView = WidgetRepository.getInstance(ctx())
			.loadWidgetFromRepository(blueprint.getBlueprintId(), ctx());

			if (newView == null) {
				newView = createLabeledPlaceholder(ctx(), blueprint.getWidgetClass());
			}
			if (newView == null) return;

			host.applyDefaultViewSetup(newView);

			ViewGroup dest = getSafeDestination(destinationView);
			if (!host.getDragController().canAcceptChild(dest, newView)) {
				dest = host.getEditorLayout();
			}
			int insertIdx = host.getDragController().getInsertIndex(dest, event);

			EditorDragController.addViewSafe(newView, dest, insertIdx);
			host.addViewWithUndo(newView, dest, insertIdx);

			String newId = host.getIdManager().generateNewId(newView);
			host.getIdManager().addNewId(newView, newId);

			AttributeSet attributeSet = new AttributeSet();
			host.getAttributeStore().put(newView, attributeSet);
			attributeSet.add(new Attribute("android:id", "@+id/" + newId));

			if (blueprint.getAttributes() != null) {
				Iterator<Map.Entry<String, String>> it =
				blueprint.getAttributes().entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<String, String> entry = it.next();
					if (attributeSet.getAttribute(entry.getKey()) == null) {
						Attribute a = new Attribute(entry.getKey(), entry.getValue());
						attributeSet.add(a);
						host.applyAttribute(newView, a);
					}
				}
			}

			blueprint.incrementUsageCount();
			host.notifyWidgetAdded(newView, destinationView);

			host.showMessage("Collection widget added: " + blueprint.getDisplayName());
			host.refreshUndoRedoButtons();
		} catch (Exception e) {
			host.showMessage("Collection widget error: " + e.getMessage());
			Log.e("WidgetDropHandler", "Collection drop failed", e);
		}
	}

	private void dropRegularWidget(HashMap<String, Object> widgetData,
	View destinationView, DragEvent event) {
		try {
			String classPath = widgetData.get("class_path").toString();
			boolean isUserCustom = isUserCustomWidget(classPath);

			View newView;
			if (isUserCustom) {
				newView = createLabeledPlaceholder(ctx(), classPath);
			} else {
				newView = DynamicWidgetFactory.resolve(ctx(), classPath, null);
				if (newView == null)
				newView = createLabeledPlaceholder(ctx(), classPath);
				else
				DynamicWidgetFactory.applyCanvasLightDefaults(newView);
			}

			host.applyDefaultViewSetup(newView);

			ViewGroup dest = getSafeDestination(destinationView);
			if (!host.getDragController().canAcceptChild(dest, newView)) {
				dest = host.getEditorLayout();
			}
			int insertIdx = host.getDragController().getInsertIndex(dest, event);

			host.getDragController().applyLayoutParamsForDestination(newView, dest, event);

			EditorDragController.addViewSafe(newView, dest, insertIdx);
			host.addViewWithUndo(newView, dest, insertIdx);

			String newId = host.getIdManager().generateNewId(newView);
			host.getIdManager().addNewId(newView, newId);

			AttributeSet attributeSet = new AttributeSet();
			host.getAttributeStore().put(newView, attributeSet);
			attributeSet.add(new Attribute("android:id", "@+id/" + newId));

			if (newView instanceof ViewGroup
					&& !EditorDragController.isLeafOrOpaqueContainer(newView)) {
				String defaultWidth = (dest instanceof LinearLayout)
				? "match_parent" : "wrap_content";
				attributeSet.add(new Attribute("android:layout_width", defaultWidth));
				attributeSet.add(new Attribute("android:layout_height", "wrap_content"));
				((ViewGroup) newView).setOnDragListener(host.getDragController().getDragListener());
				host.applyContainerStroke(newView);
			} else {
				attributeSet.add(new Attribute("android:layout_width", "wrap_content"));
				attributeSet.add(new Attribute("android:layout_height", "wrap_content"));
			}

			host.ensureDefaultLayoutParams(newView);
			addInitialAttributes(newView, widgetData);
			host.setDefaultTextSize(newView);

			host.notifyWidgetAdded(newView, dest);

			host.refreshUndoRedoButtons();
		} catch (Throwable t) {
			Log.e("WidgetDropHandler", "dropRegularWidget failed", t);
			host.showMessage("Create failed: " + t.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	private void addInitialAttributes(View view, HashMap<String, Object> widgetData) {
		if (!widgetData.containsKey("initial_attributes")) return;
		Map<String, String> initialAttrs =
		(Map<String, String>) widgetData.get("initial_attributes");
		AttributeSet set = host.getAttributeStore().getOrCreate(view);
		Iterator<Map.Entry<String, String>> it = initialAttrs.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			Attribute a = new Attribute(entry.getKey(), entry.getValue());
			set.add(a);
			host.applyAttribute(view, a);
		}
	}

	private ViewGroup getSafeDestination(View potentialDest) {
		try {
			if (host.getDragController() != null) {
				return host.getDragController().resolveDropTarget(potentialDest);
			}
		} catch (Throwable ignore) {}
		View cursor = potentialDest;
		while (cursor != null) {
			if (cursor instanceof ViewGroup
					&& !EditorDragController.isLeafOrOpaqueContainer(cursor)) {
				return (ViewGroup) cursor;
			}
			ViewParent p = cursor.getParent();
			cursor = (p instanceof View) ? (View) p : null;
		}
		return host.getEditorLayout();
	}

	private boolean isUserCustomWidget(String classPath) {
		if (classPath == null) return false;
		if (classPath.startsWith("user.")) return true;
		if (!classPath.contains(".")) return false;
		if (classPath.startsWith("android.")
		|| classPath.startsWith("androidx.")
		|| classPath.startsWith("com.google.android.material.")
		|| classPath.startsWith("com.google.android.flexbox.")) return false;
		return true;
	}

	private View createLabeledPlaceholder(Context ctx, String classPath) {
		String displayName = classPath.contains(".")
		? classPath.substring(classPath.lastIndexOf('.') + 1)
		: classPath;

		LinearLayout box = new LinearLayout(ctx);
		box.setOrientation(LinearLayout.HORIZONTAL);
		box.setGravity(Gravity.CENTER_VERTICAL);

		GradientDrawable border = new GradientDrawable();
		border.setColor(0xFFF5F5F5);
		border.setStroke((int) SketchwareUtil.getDip(ctx, 1), 0xFF9E9E9E);
		border.setCornerRadius(SketchwareUtil.getDip(ctx, 4));
		box.setBackground(border);

		int pad = (int) SketchwareUtil.getDip(ctx, 8);
		box.setPadding(pad, pad, pad, pad);

		TextView icon = new TextView(ctx);
		icon.setText("[W]");
		icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
		icon.setTextColor(0xFF757575);
		icon.setPadding(0, 0, (int) SketchwareUtil.getDip(ctx, 4), 0);

		TextView label = new TextView(ctx);
		label.setText(displayName);
		label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		label.setTextColor(0xFF424242);
		label.setSingleLine(true);

		box.addView(icon);
		box.addView(label);
		box.setTag(classPath);

		return box;
	}


	public void duplicateWidget(View original) {
		if (original == null) return;
		try {
			String classPath = original.getClass().getName();
			View copy = DynamicWidgetFactory.resolve(ctx(), classPath, null);
			if (copy != null) DynamicWidgetFactory.applyCanvasLightDefaults(copy);
			if (copy == null) copy = createLabeledPlaceholder(ctx(), classPath);

			host.applyDefaultViewSetup(copy);

			ViewGroup parent = (original.getParent() instanceof ViewGroup)
			? (ViewGroup) original.getParent() : host.getEditorLayout();
			int idx = parent.indexOfChild(original) + 1;

			String newId = host.getIdManager().generateNewId(copy);
			host.getIdManager().addNewId(copy, newId);

			AttributeSet origSet = host.getAttributeStore().get(original);
			AttributeSet newSet = new AttributeSet();
			if (origSet != null) {
				for (Attribute a : origSet.getAttributes()) {
					if ("android:id".equals(a.getName())) {
						newSet.add(new Attribute("android:id", "@+id/" + newId));
					} else {
						newSet.add(new Attribute(a.getName(), a.getValue()));
					}
				}
			} else {
				newSet.add(new Attribute("android:id", "@+id/" + newId));
			}
			host.getAttributeStore().put(copy, newSet);

			host.ensureDefaultLayoutParams(copy);
			EditorDragController.addViewSafe(copy, parent, idx);
			host.addViewWithUndo(copy, parent, idx);

			for (Attribute a : newSet.getAttributes()) {
				host.applyAttribute(copy, a);
			}

			if (copy instanceof ViewGroup
					&& !EditorDragController.isLeafOrOpaqueContainer(copy)) {
				((ViewGroup) copy).setOnDragListener(host.getDragController().getDragListener());
				host.applyContainerStroke(copy);
			}

			host.showMessage("Widget duplicate ho gaya!");
			host.refreshUndoRedoButtons();
		} catch (Exception e) {
			Log.e("WidgetDropHandler", "duplicateWidget failed", e);
			host.showMessage("Duplicate failed: " + e.getMessage());
		}
	}


	public void wrapViewInContainer(View view, int containerType) {
		if (view == null || !(view.getParent() instanceof ViewGroup)) return;
		try {
			ViewGroup parent = (ViewGroup) view.getParent();
			int origIdx = parent.indexOfChild(view);
			ViewGroup.LayoutParams origLp = view.getLayoutParams();

			ViewGroup wrapper;
			if (containerType == 0) {
				LinearLayout ll = new LinearLayout(ctx());
				ll.setOrientation(LinearLayout.VERTICAL);
				wrapper = ll;
			} else if (containerType == 1) {
				LinearLayout ll = new LinearLayout(ctx());
				ll.setOrientation(LinearLayout.HORIZONTAL);
				wrapper = ll;
			} else if (containerType == 3) {
				wrapper = new ScrollView(ctx());
			} else {
				wrapper = new FrameLayout(ctx());
			}

			host.applyDefaultViewSetup(wrapper);
			wrapper.setLayoutParams(origLp != null ? origLp
			: new ViewGroup.MarginLayoutParams(
			ViewGroup.LayoutParams.MATCH_PARENT,
			ViewGroup.LayoutParams.WRAP_CONTENT));

			String wrapperId = host.getIdManager().generateNewId(wrapper);
			host.getIdManager().addNewId(wrapper, wrapperId);
			AttributeSet wrapperSet = new AttributeSet();
			wrapperSet.add(new Attribute("android:id", "@+id/" + wrapperId));
			wrapperSet.add(new Attribute("android:layout_width", "match_parent"));
			wrapperSet.add(new Attribute("android:layout_height", "wrap_content"));
			host.getAttributeStore().put(wrapper, wrapperSet);

			parent.removeView(view);
			wrapper.setOnDragListener(host.getDragController().getDragListener());
			host.applyContainerStroke(wrapper);
			wrapper.addView(view);
			parent.addView(wrapper, origIdx);

			host.getStableUndoManager().push(host.getCurrentActivityName(),
			new StableUndoManager.WrapViewCommand(view, wrapper, parent, origIdx));

			host.showMessage("Widget wrap ho gaya!");
			host.refreshUndoRedoButtons();
		} catch (Exception e) {
			Log.e("WidgetDropHandler", "wrapViewInContainer failed", e);
			host.showMessage("Wrap failed: " + e.getMessage());
		}
	}
}
