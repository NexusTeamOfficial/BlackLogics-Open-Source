
package com.nexusteam.internal.os.layouteditor;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Build;
import android.os.Vibrator;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class EditorDragController {
	
	private static final String TAG           = "EditorDragController";
	private static final int    GRID_SNAP_DP  = 4;
	private static final long   VIB_DROP      = 60L;
	private static final long   VIB_DELETE    = 120L;
	private static final long   VIB_SNAP      = 20L;
	
	
	public interface DragCallback {
		void onNewWidgetDropped(Object widgetData, ViewGroup destination, DragEvent event);
		void onExistingWidgetMoved(View draggedView, ViewGroup newParent, int insertIndex);
		void onWidgetDeleted(View draggedView);
		void onDragStarted(View draggedView);
		void onDragEnded(boolean dropSuccessful, View draggedView);
	}
	
	
	private final Context   context;
	private final ViewGroup editorLayout;
	private final View      placeHolder;
	private DragCallback    callback;
	private View            deleteTarget;
	private final Vibrator  vibrator;
	
	private ViewGroup dragSourceParent;
	private int       dragSourceIndex;

	private int shadowWpx = 0;
	private int shadowHpx = 0;
	
	private boolean snapToGrid = false;
	
	private boolean dragActive = false;
	
	
	public EditorDragController(Context context, ViewGroup editorLayout, View placeHolder) {
		this.context      = context;
		this.editorLayout = editorLayout;
		this.placeHolder  = placeHolder;
		this.vibrator     = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
	}
	
	
	public void setCallback(DragCallback callback) {
		this.callback = callback;
	}
	
	public void setSnapToGrid(boolean snap) {
		this.snapToGrid = snap;
	}
	
	public boolean isSnapToGrid() {
		return snapToGrid;
	}
	
	public void setDeleteTarget(View deleteTarget) {
		this.deleteTarget = deleteTarget;
		
		if (deleteTarget == null) return;
		
		deleteTarget.setOnDragListener(new View.OnDragListener() {
			@Override
			public boolean onDrag(View v, DragEvent event) {
				
				switch (event.getAction()) {
					
					case DragEvent.ACTION_DRAG_STARTED:
					return event.getLocalState() instanceof View;
					
					
					case DragEvent.ACTION_DRAG_ENTERED:
					v.animate()
					.scaleX(1.3f)
					.scaleY(1.3f)
					.setDuration(150)
					.start();
					
					v.setAlpha(1.0f);
					
					if (v instanceof ImageView) {
						((ImageView) v).setColorFilter(
						0xFFFF4444,
						PorterDuff.Mode.SRC_ATOP
						);
					}
					
					return true;
					
					
					case DragEvent.ACTION_DRAG_EXITED:
					v.animate()
					.scaleX(1.0f)
					.scaleY(1.0f)
					.setDuration(150)
					.start();
					
					if (v instanceof ImageView) {
						((ImageView) v).setColorFilter(
						0xFF757575,
						PorterDuff.Mode.SRC_ATOP
						);
					}
					
					return true;
					
					
					case DragEvent.ACTION_DROP:
					safeRemoveView(placeHolder);
					
					View draggedView = (View) event.getLocalState();
					
					if (draggedView != null) {
						draggedView.setAlpha(1f);
					}
					
					if (draggedView != null && callback != null) {
						callback.onWidgetDeleted(draggedView);
					}
					
					v.animate()
					.scaleX(1.0f)
					.scaleY(1.0f)
					.setDuration(150)
					.start();
					
					if (v instanceof ImageView) {
						((ImageView) v).setColorFilter(
						0xFF757575,
						PorterDuff.Mode.SRC_ATOP
						);
					}
					
					return true;
					
					
					case DragEvent.ACTION_DRAG_ENDED:
					v.animate()
					.scaleX(1.0f)
					.scaleY(1.0f)
					.setDuration(150)
					.start();
					
					if (v instanceof ImageView) {
						((ImageView) v).setColorFilter(
						0xFF757575,
						PorterDuff.Mode.SRC_ATOP
						);
					}
					
					return true;
				}
				
				return false;
			}
		});
	}
	
	
	public ViewGroup getDragSourceParent() { return dragSourceParent; }
	public int       getDragSourceIndex()  { return dragSourceIndex;  }
	
	
	public View.OnDragListener getDragListener() {
		return new SafeDragListener();
	}
	
	
	private class SafeDragListener implements View.OnDragListener {
		
		@Override
		public boolean onDrag(View destinationView, DragEvent event) {
			final Object localState  = event.getLocalState();
			final View   draggedView = (localState instanceof View) ? (View) localState : null;
			
			try {
				switch (event.getAction()) {
					
					case DragEvent.ACTION_DRAG_STARTED: {
						handleDragStarted(draggedView);
						return true;
					}
					
					case DragEvent.ACTION_DRAG_ENTERED:
					case DragEvent.ACTION_DRAG_LOCATION: {
						handleDragLocation(destinationView, draggedView, event);
						return true;
					}
					
					case DragEvent.ACTION_DRAG_EXITED: {
						safeRemoveView(placeHolder);
						highlightDeleteTarget(false);
						return true;
					}
					
					case DragEvent.ACTION_DROP: {
						handleDrop(destinationView, draggedView, localState, event);
						return true;
					}
					
					case DragEvent.ACTION_DRAG_ENDED: {
						handleDragEnded(draggedView, event);
						return true;
					}
					
					default:
					return true;
				}
			} catch (Exception e) {
				Log.e(TAG, "DragListener error: " + e.getMessage(), e);
				safeRemoveView(placeHolder);
				if (draggedView != null) draggedView.setAlpha(1f);
			}
			return true;
		}
	}
	
	
	private void handleDragStarted(View draggedView) {
		if (dragActive) return;
		dragActive = true;
		
		if (draggedView != null) {
			draggedView.setAlpha(0f);
			dragSourceParent = (draggedView.getParent() instanceof ViewGroup)
			? (ViewGroup) draggedView.getParent() : null;
			dragSourceIndex  = (dragSourceParent != null)
			? dragSourceParent.indexOfChild(draggedView) : 0;
			shadowWpx = draggedView.getWidth() > 0 ? draggedView.getWidth() : 0;
			shadowHpx = draggedView.getHeight() > 0 ? draggedView.getHeight() : 0;
		} else {
			shadowWpx = 0;
			shadowHpx = 0;
			dragSourceParent = null;
			dragSourceIndex  = 0;
		}
		if (callback != null) callback.onDragStarted(draggedView);
	}
	
	private void handleDragLocation(View destinationView, View draggedView, DragEvent event) {
		if (isOverDeleteTarget(destinationView, event)) {
			safeRemoveView(placeHolder);
			highlightDeleteTarget(true);
		} else {
			highlightDeleteTarget(false);
			ViewGroup target = resolveDropTarget(destinationView);
			
			boolean ok;
			if (draggedView != null) {
				ok = canAcceptChild(target, draggedView);
			} else {
				String classPath = extractClassPath(event.getLocalState());
				ok = canAcceptChild(target, placeHolder)
				&& isChildTypeCompatibleByClassPath(target, classPath);
			}
			
			if (ok) {
				insertPlaceholder(target, event);
			} else {
				safeRemoveView(placeHolder);
			}
		}
	}
	
	private String extractClassPath(Object localState) {
		if (localState instanceof java.util.HashMap) {
			Object v = ((java.util.HashMap<?, ?>) localState).get("class_path");
			return v != null ? v.toString() : null;
		}
		return null;
	}
	
	private void handleDrop(View destinationView, View draggedView,
	Object localState, DragEvent event) {
		safeRemoveView(placeHolder);
		
		if (draggedView != null) {
			draggedView.setAlpha(1f);
		}
		
		if (isOverDeleteTarget(destinationView, event) && draggedView != null) {
			vibrate(VIB_DELETE);
			if (callback != null) callback.onWidgetDeleted(draggedView);
			else safeRemoveView(draggedView);
			return;
		}
		
		ViewGroup dest = resolveDropTarget(destinationView);
		
		if (draggedView != null) {
			if (!canAcceptChild(dest, draggedView)) {
				dest = editorLayout;
			}
			int insertIdx = getInsertIndex(dest, event);
			
			applyLayoutParamsForDestination(draggedView, dest, event);
			
			addViewSafe(draggedView, dest, insertIdx);
			vibrate(VIB_DROP);
			
			if (callback != null) {
				callback.onExistingWidgetMoved(draggedView, dest, insertIdx);
			}
			
		} else if (localState instanceof java.util.HashMap) {
			String classPath = extractClassPath(localState);
			if (!isChildTypeCompatibleByClassPath(dest, classPath)) {
				Toast.makeText(context,
				dest.getClass().getSimpleName() + " ye widget accept nahi karta",
				Toast.LENGTH_SHORT).show();
				dest = editorLayout;
			}
			if (callback != null) {
				callback.onNewWidgetDropped(localState, dest, event);
			}
		}
	}
	
	private void handleDragEnded(View draggedView, DragEvent event) {
		if (!dragActive) return;
		dragActive = false;
		
		safeRemoveView(placeHolder);
		highlightDeleteTarget(false);
		
		boolean success = event.getResult();
		
		if (!success && draggedView != null) {
			draggedView.setAlpha(1f);
			if (draggedView.getParent() == null) {
				Log.w(TAG, "Drag cancelled — view lost parent: "
				+ draggedView.getClass().getSimpleName());
				if (dragSourceParent != null) {
					addViewSafe(draggedView, dragSourceParent,
					Math.min(dragSourceIndex, dragSourceParent.getChildCount()));
				}
			}
		}
		
		if (success) vibrate(VIB_DROP);
		
		if (callback != null) callback.onDragEnded(success, draggedView);
		
		dragSourceParent = null;
		dragSourceIndex  = 0;
	}
	
	
	public void applyLayoutParamsForDestination(View view, ViewGroup dest, DragEvent event) {
		if (view == null || dest == null) return;
		
		ViewGroup.LayoutParams existing = view.getLayoutParams();
		int w = (existing != null) ? existing.width  : ViewGroup.LayoutParams.WRAP_CONTENT;
		int h = (existing != null) ? existing.height : ViewGroup.LayoutParams.WRAP_CONTENT;
		
		ViewGroup.LayoutParams newLp;
		
		if (dest instanceof LinearLayout) {
			LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(w, h);
			copyMarginsIfPossible(existing, llp);
			newLp = llp;
			
		} else if (dest instanceof RelativeLayout) {
			RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(w, h);
			copyMarginsIfPossible(existing, rlp);
			newLp = rlp;
			
		} else if (dest instanceof FrameLayout) {
			FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(w, h);
			if (event != null) {
				flp.gravity = gravityForFrameLayout((FrameLayout) dest, event);
			}
			copyMarginsIfPossible(existing, flp);
			newLp = flp;
			
		} else {
			ViewGroup.MarginLayoutParams mlp = new ViewGroup.MarginLayoutParams(w, h);
			copyMarginsIfPossible(existing, mlp);
			newLp = mlp;
		}
		
		view.setLayoutParams(newLp);
	}
	
	private void copyMarginsIfPossible(ViewGroup.LayoutParams from,
	ViewGroup.MarginLayoutParams to) {
		if (from instanceof ViewGroup.MarginLayoutParams) {
			ViewGroup.MarginLayoutParams mFrom = (ViewGroup.MarginLayoutParams) from;
			to.leftMargin   = mFrom.leftMargin;
			to.topMargin    = mFrom.topMargin;
			to.rightMargin  = mFrom.rightMargin;
			to.bottomMargin = mFrom.bottomMargin;
		}
	}
	
	
	public ViewGroup resolveDropTarget(View destinationView) {
		View cursor = destinationView;
		while (cursor != null) {
			if (cursor instanceof ViewGroup
					&& cursor != placeHolder
					&& !isLeafOrOpaqueContainer(cursor)) {
				return (ViewGroup) cursor;
			}
			Object p = cursor.getParent();
			cursor = (p instanceof View) ? (View) p : null;
		}
		return editorLayout;
	}

	public static boolean isLeafOrOpaqueContainer(View v) {
		if (v == null) return true;
		try {
			if (v instanceof com.nexusteam.blacklogics.sdk.widgets.PlaceholderWidget) {
				return true;
			}
		} catch (Throwable ignore) {}
		try {
			if (v instanceof com.nexusteam.blacklogics.sdk.widgets.PlaceholderWebView) {
				return true;
			}
		} catch (Throwable ignore) {}

		if (v instanceof android.webkit.WebView) return true;
		if (v instanceof android.widget.VideoView) return true;
		if (v instanceof android.view.SurfaceView) return true;
		if (v instanceof android.view.TextureView) return true;

		if (v instanceof android.widget.CalendarView) return true;
		if (v instanceof android.widget.DatePicker) return true;
		if (v instanceof android.widget.TimePicker) return true;
		if (v instanceof android.widget.NumberPicker) return true;
		if (v instanceof android.widget.SearchView) return true;
		if (v instanceof android.widget.ZoomControls) return true;
		if (v instanceof android.widget.TabHost) return true;
		if (v instanceof android.widget.DialerFilter) return true;

		if (v instanceof android.widget.AdapterView) return true;
		try {
			if (v instanceof androidx.recyclerview.widget.RecyclerView) return true;
		} catch (Throwable ignore) {}
		try {
			if (v instanceof androidx.viewpager.widget.ViewPager) return true;
		} catch (Throwable ignore) {}
		try {
			if (v instanceof androidx.viewpager2.widget.ViewPager2) return true;
		} catch (Throwable ignore) {}

		try {
			if (v instanceof com.google.android.material.tabs.TabLayout) return true;
		} catch (Throwable ignore) {}
		try {
			if (v instanceof com.google.android.material.bottomnavigation.BottomNavigationView) return true;
		} catch (Throwable ignore) {}
		try {
			if (v instanceof com.google.android.material.navigation.NavigationView) return true;
		} catch (Throwable ignore) {}

		String cn = v.getClass().getName();
		String sn = v.getClass().getSimpleName();
		if ("WebView".equals(sn) || "WebView2".equals(sn) || "VideoView".equals(sn)
				|| "SurfaceView".equals(sn) || "TextureView".equals(sn)
				|| "GLSurfaceView".equals(sn) || "PlaceholderWidget".equals(sn)
				|| "PlaceholderWebView".equals(sn)
				|| "CalendarView".equals(sn) || "DatePicker".equals(sn)
				|| "TimePicker".equals(sn) || "NumberPicker".equals(sn)
				|| "SearchView".equals(sn) || "TabLayout".equals(sn)
				|| "BottomNavigationView".equals(sn) || "NavigationView".equals(sn)
				|| "RecyclerView".equals(sn) || "ViewPager".equals(sn)
				|| "ViewPager2".equals(sn) || "Spinner".equals(sn)
				|| "ListView".equals(sn) || "GridView".equals(sn)
				|| "ExpandableListView".equals(sn)) {
			return true;
		}
		if (cn.contains("PlaceholderWidget") || cn.contains("PlaceholderWebView")) {
			return true;
		}
		return false;
	}
	
	
	public boolean canAcceptChild(ViewGroup destination, View child) {
		if (destination == null || child == null) return false;
		
		View cursor = destination;
		while (cursor != null) {
			if (cursor == child) return false;
			cursor = (cursor.getParent() instanceof View)
			? (View) cursor.getParent() : null;
		}
		
		if (!isChildTypeCompatible(destination, child)) {
			showIncompatibleChildWarning(destination, child);
			return false;
		}
		
		if (destination instanceof ScrollView
		|| destination instanceof HorizontalScrollView) {
			int realChildCount = 0;
			for (int i = 0; i < destination.getChildCount(); i++) {
				if (destination.getChildAt(i) != placeHolder) {
					realChildCount++;
				}
			}
			if (realChildCount >= 1 && child != placeHolder) {
				showSingleChildWarning(destination);
				return false;
			}
		}
		
		return true;
	}
	
	private boolean isChildTypeCompatible(ViewGroup destination, View child) {
		if (child == placeHolder) return true;

		if (isLeafOrOpaqueContainer(destination)) {
			return false;
		}
		
		if (destination instanceof com.google.android.material.textfield.TextInputLayout) {
			if (!(child instanceof android.widget.EditText)) return false;
			com.google.android.material.textfield.TextInputLayout til =
			(com.google.android.material.textfield.TextInputLayout) destination;
			return til.getEditText() == null;
		}
		
		if (destination instanceof android.widget.Spinner
		|| destination instanceof android.widget.AbsListView
		|| destination instanceof androidx.recyclerview.widget.RecyclerView
		|| destination instanceof androidx.viewpager.widget.ViewPager
		|| destination instanceof androidx.viewpager2.widget.ViewPager2) {
			return false;
		}
		
		return true;
	}
	
	private boolean isChildTypeCompatibleByClassPath(ViewGroup destination, String childClassPath) {
		if (destination == null) return false;
		if (isLeafOrOpaqueContainer(destination)) return false;
		if (childClassPath == null) return true;
		
		boolean isEditText = childClassPath.endsWith("EditText");
		
		if (destination instanceof com.google.android.material.textfield.TextInputLayout) {
			if (!isEditText) return false;
			com.google.android.material.textfield.TextInputLayout til =
			(com.google.android.material.textfield.TextInputLayout) destination;
			return til.getEditText() == null;
		}
		
		if (destination instanceof android.widget.Spinner
		|| destination instanceof android.widget.AbsListView
		|| destination instanceof androidx.recyclerview.widget.RecyclerView
		|| destination instanceof androidx.viewpager.widget.ViewPager
		|| destination instanceof androidx.viewpager2.widget.ViewPager2) {
			return false;
		}
		
		return true;
	}
	
	private void showIncompatibleChildWarning(ViewGroup destination, View child) {
		Toast.makeText(context,
		destination.getClass().getSimpleName() + " ye widget accept nahi karta",
		Toast.LENGTH_SHORT).show();
	}
	
	private void showSingleChildWarning(ViewGroup destination) {
		String name = (destination instanceof HorizontalScrollView)
		? "HorizontalScrollView" : "ScrollView";
		Toast.makeText(context,
		name + " mein sirf ek child ho sakta hai",
		Toast.LENGTH_SHORT).show();
	}
	
	
	public int getInsertIndex(ViewGroup parent, DragEvent event) {
		if (!(parent instanceof LinearLayout)) {
			return parent.getChildCount();
		}
		
		LinearLayout ll         = (LinearLayout) parent;
		boolean      isVertical = ll.getOrientation() == LinearLayout.VERTICAL;
		int          insertIdx  = 0;
		
		float pos = isVertical ? event.getY() : event.getX();
		
		if (snapToGrid) {
			float snap = dp(GRID_SNAP_DP);
			pos = Math.round(pos / snap) * snap;
		}
		
		for (int i = 0; i < ll.getChildCount(); i++) {
			View child = ll.getChildAt(i);
			if (child == null || child == placeHolder) continue;
			
			int center = isVertical
			? child.getTop()  + child.getHeight() / 2
			: child.getLeft() + child.getWidth()  / 2;
			
			if (center < (int) pos) insertIdx++;
			else break;
		}
		
		return Math.min(insertIdx, parent.getChildCount());
	}
	
	
	private void insertPlaceholder(ViewGroup destination, DragEvent event) {
		safeRemoveView(placeHolder);
		if (!canAcceptChild(destination, placeHolder)) return;
		int idx = getInsertIndex(destination, event);
		try {
			float density = destination.getResources().getDisplayMetrics().density;
			int w = shadowWpx > 0 ? shadowWpx : Math.round(70f * density);
			int h = shadowHpx > 0 ? shadowHpx : Math.round(40f * density);
			ViewGroup.LayoutParams lp;
			if (destination instanceof android.widget.LinearLayout) {
				lp = new android.widget.LinearLayout.LayoutParams(w, h);
			} else {
				lp = new ViewGroup.MarginLayoutParams(w, h);
			}
			placeHolder.setLayoutParams(lp);
			placeHolder.setMinimumWidth(w);
			placeHolder.setMinimumHeight(h);
			placeHolder.setBackgroundColor(0x88757575);
			placeHolder.setAlpha(0.85f);
		} catch (Throwable ignore) {}
		addViewSafe(placeHolder, destination, idx);
	}
	
	
	private boolean isOverDeleteTarget(View destinationView, DragEvent event) {
		if (deleteTarget == null
		|| deleteTarget.getVisibility() != View.VISIBLE
		|| deleteTarget.getWidth() == 0) {
			return false;
		}
		
		int[] destLoc = new int[2];
		destinationView.getLocationInWindow(destLoc);
		
		float scale = editorLayout.getScaleX();
		if (scale <= 0f) scale = 1f;
		
		int pointerX = destLoc[0] + Math.round(event.getX() * scale);
		int pointerY = destLoc[1] + Math.round(event.getY() * scale);
		
		int[] deleteLoc = new int[2];
		deleteTarget.getLocationInWindow(deleteLoc);
		int tolerance = (int) dp(8f);
		Rect deleteRect = new Rect(
		deleteLoc[0] - tolerance,
		deleteLoc[1] - tolerance,
		deleteLoc[0] + deleteTarget.getWidth()  + tolerance,
		deleteLoc[1] + deleteTarget.getHeight() + tolerance);
		
		return deleteRect.contains(pointerX, pointerY);
	}
	
	@Deprecated
	public boolean isOverDeleteTarget(DragEvent event) {
		return isOverDeleteTarget(editorLayout, event);
	}
	
	
	private void highlightDeleteTarget(boolean highlight) {
		if (deleteTarget == null) return;
		if (!(deleteTarget instanceof ImageView)) return;
		
		ImageView img = (ImageView) deleteTarget;
		if (highlight) {
			img.setColorFilter(0xFFFF4444, PorterDuff.Mode.SRC_ATOP);
			img.setScaleX(1.25f);
			img.setScaleY(1.25f);
		} else {
			img.setColorFilter(0xFF757575, PorterDuff.Mode.SRC_ATOP);
			img.setScaleX(1f);
			img.setScaleY(1f);
		}
	}
	
	
	public static void safeRemoveView(View view) {
		if (view == null) return;
		ViewGroup parent = (view.getParent() instanceof ViewGroup)
		? (ViewGroup) view.getParent() : null;
		if (parent != null) parent.removeView(view);
	}
	
	public static void addViewSafe(View view, ViewGroup parent, int index) {
		if (view == null || parent == null) return;
		safeRemoveView(view);
		int safeIdx = Math.max(0, Math.min(index, parent.getChildCount()));
		try {
			parent.addView(view, safeIdx);
		} catch (Exception e) {
			Log.e(TAG, "addViewSafe failed: " + e.getMessage(), e);
		}
	}
	
	public static void addViewSafe(View view, ViewGroup parent) {
		if (view == null || parent == null) return;
		safeRemoveView(view);
		try {
			parent.addView(view);
		} catch (Exception e) {
			Log.e(TAG, "addViewSafe (append) failed: " + e.getMessage(), e);
		}
	}
	
	
	public static int gravityForFrameLayout(FrameLayout frame, DragEvent event) {
		int gravity = 0;
		float posX = event.getX();
		float posY = event.getY();
		float w    = frame.getWidth();
		float h    = frame.getHeight();
		
		if (posX < w / 3f)           gravity |= Gravity.LEFT;
		else if (posX > 2f * w / 3f) gravity |= Gravity.RIGHT;
		else                          gravity |= Gravity.CENTER_HORIZONTAL;
		
		if (posY < h / 3f)           gravity |= Gravity.TOP;
		else if (posY > 2f * h / 3f) gravity |= Gravity.BOTTOM;
		else                          gravity |= Gravity.CENTER_VERTICAL;
		
		return gravity;
	}
	
	
	private void vibrate(long ms) {
		if (vibrator == null) return;
		try {
			vibrator.vibrate(ms);
		} catch (Exception ignored) {
		}
	}
	
	
	private float dp(float value) {
		return value * context.getResources().getDisplayMetrics().density;
	}
}