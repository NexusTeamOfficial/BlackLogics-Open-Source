
package com.nexusteam.blacklogics.sdk.layouteditor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;

public class EditorLayout extends FrameLayout {

    private static final String TAG = "EditorLayout";
    private static final boolean DEBUG_SCROLL = true;

    private static final int   FOCUS_COLOR          = 0x440D6EFD;
    private static final int   FOCUS_STROKE_COLOR   = 0xFF0D6EFD;
    private static final int   HOVER_COLOR          = 0x2200C853;
    private static final int   CONTAINER_DASH_COLOR = 0x88888888;
    private static final int   GRID_COLOR           = 0x18000000;
    private static final int   CONTAINER_DASH_DARK  = 0x99AAAAAA;
    private static final int   GRID_COLOR_DARK      = 0x22FFFFFF;
    private static final float FOCUS_CORNER_RADIUS  = 4f;
    private static final float FOCUS_STROKE_WIDTH   = 2f;
    private static final float CONTAINER_STROKE_W   = 1f;
    private static final float GRID_STEP_DP         = 16f;
    private static final float RESIZE_HANDLE_RADIUS = 5f;
    private static final int   MAX_COLLECT_DEPTH    = 30;

    private View            mFocusedView;
    private View            mHoveredView;
    private boolean         mShowGrid    = false;
    private boolean         mShowBorders = false;

    private Paint mFocusFillPaint;
    private Paint mFocusStrokePaint;
    private Paint mHoverPaint;
    private Paint mContainerPaint;
    private Paint mGridPaint;
    private Paint mResizeHandlePaint;

    private Rect              mFocusedRect    = new Rect();
    private ArrayList<Rect>   mContainerRects = new ArrayList<Rect>();
    private float             mDensity;

    private ViewGroup         mContainerBorderRoot;

    /** Phone canvas area (editorCanvasFrame). Overlays are clipped to this, not to the whole screen. */
    private View              mOverlayClipView;

    public void setOverlayClipView(View v) {
        mOverlayClipView = v;
        invalidate();
    }

    /** Visible phone-canvas rect in this view's coordinates; falls back to whole view. */
    private Rect getOverlayClipRect() {
        View v = mOverlayClipView;
        // Fallback that needs no ViewEditor wiring: the phone canvas viewport is the parent of
        // the container-border root (editorLayout), so overlays never reach phone_action_bar.
        if (v == null && mContainerBorderRoot != null
                && mContainerBorderRoot.getParent() instanceof View) {
            v = (View) mContainerBorderRoot.getParent();
        }
        if (v != null && v.isAttachedToWindow() && v.getWidth() > 0 && v.getHeight() > 0) {
            int l = getRelativeLeft(v, 0);
            int t = getRelativeTop(v, 0);
            return new Rect(l, t, l + v.getWidth(), t + v.getHeight());
        }
        return new Rect(0, 0, getWidth(), getHeight());
    }

    public void setContainerBorderRoot(ViewGroup root) {
        mContainerBorderRoot = root;
        if (mShowBorders) {
            mContainerRects = collectContainerRects(
                    mContainerBorderRoot != null ? mContainerBorderRoot : this, 0);
            invalidate();
        }
    }


    public EditorLayout(Context context) {
        this(context, null);
    }

    public EditorLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDensity = context.getResources().getDisplayMetrics().density;
        initPaints();
        // Keep selection / overlay drawing inside this viewport
        setClipChildren(true);
        setClipToPadding(true);
    }


    private void initPaints() {
        mFocusFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFocusFillPaint.setColor(FOCUS_COLOR);
        mFocusFillPaint.setStyle(Style.FILL);

        mFocusStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFocusStrokePaint.setColor(FOCUS_STROKE_COLOR);
        mFocusStrokePaint.setStyle(Style.STROKE);
        mFocusStrokePaint.setStrokeWidth(dp(FOCUS_STROKE_WIDTH));

        mHoverPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHoverPaint.setColor(HOVER_COLOR);
        mHoverPaint.setStyle(Style.FILL);

        mContainerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mContainerPaint.setColor(CONTAINER_DASH_COLOR);
        mContainerPaint.setStyle(Style.STROKE);
        mContainerPaint.setStrokeWidth(dp(CONTAINER_STROKE_W));
        mContainerPaint.setPathEffect(new DashPathEffect(new float[]{dp(4), dp(3)}, 0));

        mGridPaint = new Paint();
        mGridPaint.setColor(GRID_COLOR);
        mGridPaint.setStyle(Style.STROKE);
        mGridPaint.setStrokeWidth(1f);

        mResizeHandlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mResizeHandlePaint.setColor(FOCUS_STROKE_COLOR);
        mResizeHandlePaint.setStyle(Style.FILL);
    }

    public void applyDarkMode(boolean dark) {
        if (mGridPaint != null) {
            mGridPaint.setColor(dark ? GRID_COLOR_DARK : GRID_COLOR);
        }
        if (mContainerPaint != null) {
            mContainerPaint.setColor(dark ? CONTAINER_DASH_DARK : CONTAINER_DASH_COLOR);
        }
        invalidate();
    }


    public void setFocusedView(View view) {
        mFocusedView = view;
        if (view != null && view.getWidth() > 0 && view.getHeight() > 0) {
            mFocusedRect = getRelativeRect(view);
        } else {
            mFocusedRect = new Rect();
        }
        invalidate();
    }

    public View getFocusedView() {
        return mFocusedView;
    }

    public void setHoveredView(View view) {
        mHoveredView = view;
        invalidate();
    }

    public View getHoveredView() {
        return mHoveredView;
    }

    public void setGridVisible(boolean visible) {
        mShowGrid = visible;
        invalidate();
    }

    public boolean isGridVisible() {
        return mShowGrid;
    }

    public void setContainerBordersVisible(boolean visible) {
        mShowBorders = visible;
        invalidate();
    }

    public boolean isContainerBordersVisible() {
        return mShowBorders;
    }



    public interface PreviewTouchCallback {
        void onWidgetTapped(View widget);
        void onWidgetLongPressed(View widget);
    }

    private PreviewTouchCallback mPreviewTouchCallback;
    private boolean mPreviewTouchEnabled = true;
    private final Handler mTouchHandler = new Handler(Looper.getMainLooper());
    private float mDownX, mDownY;
    private boolean mTouchMoved, mLongFired;
    private View mTouchTarget;
    private int mTouchSlop = -1;

    private float mLastX, mLastY;
    private float mTotalDx, mTotalDy;
    private boolean mScrollAxisLocked;
    private boolean mScrollHorizontal;
    private View mScrollTarget;
    private VelocityTracker mVelocityTracker;
    private int mMinFlingVelocity = -1;
    private int mMaxFlingVelocity = -1;

    public void setPreviewTouchCallback(PreviewTouchCallback cb) {
        mPreviewTouchCallback = cb;
    }

    /**
     * A view that lives OUTSIDE the canvas tree (e.g. the activity FAB overlay) but must behave
     * like any other canvas widget: tap -> onWidgetTapped (attributes sheet).
     * Without this, the canvas swallows every touch inside its rect and the FAB never gets it.
     */
    private View mOverlayTapTarget;
    private boolean mOverlayGesture;

    public void setOverlayTapTarget(View v) {
        mOverlayTapTarget = v;
    }

    private boolean isRawPointOnOverlayTarget(float rawX, float rawY) {
        View t = mOverlayTapTarget;
        if (t == null || t.getParent() == null || t.getVisibility() != View.VISIBLE) return false;
        Rect r = new Rect();
        if (!t.getGlobalVisibleRect(r)) return false;
        return r.contains((int) rawX, (int) rawY);
    }

    public void setPreviewTouchEnabled(boolean enabled) {
        mPreviewTouchEnabled = enabled;
    }

    private final Runnable mLongPressRunnable = new Runnable() {
        @Override public void run() {
            if (mTouchTarget == null || mTouchMoved) return;
            mLongFired = true;
            try {
                if (mPreviewTouchCallback != null) {
                    mPreviewTouchCallback.onWidgetLongPressed(mTouchTarget);
                }
            } catch (Throwable t) {
                Log.w(TAG, "long press failed", t);
            }
        }
    };

    private boolean mGestureOwnedByCanvas = false;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!mPreviewTouchEnabled || mPreviewTouchCallback == null
                || mContainerBorderRoot == null) {
            return super.dispatchTouchEvent(ev);
        }
        if (mTouchSlop < 0) {
            mTouchSlop = (int) (8 * mDensity);
        }

        int action = ev.getActionMasked();

        if (action == MotionEvent.ACTION_DOWN) {
            mOverlayGesture = isRawPointOnOverlayTarget(ev.getRawX(), ev.getRawY());
            mGestureOwnedByCanvas = mOverlayGesture
                    || (isPointInsideCanvas(ev.getRawX(), ev.getRawY())
                    && !isPointOverOverlayChrome(ev.getRawX(), ev.getRawY()));
        }
        if (!mGestureOwnedByCanvas) {
            return super.dispatchTouchEvent(ev);
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                mTouchMoved = false;
                mLongFired = false;
                mTouchTarget = mOverlayGesture ? mOverlayTapTarget
                        : findWidgetAt(ev.getX(), ev.getY());
                mLastX = ev.getRawX();
                mLastY = ev.getRawY();
                mTotalDx = 0;
                mTotalDy = 0;
                mScrollAxisLocked = false;
                mScrollTarget = null;
                if (mVelocityTracker == null) mVelocityTracker = VelocityTracker.obtain();
                else mVelocityTracker.clear();
                mVelocityTracker.addMovement(ev);
                mTouchHandler.removeCallbacks(mLongPressRunnable);
                if (mTouchTarget != null && !mOverlayGesture) {
                    mTouchHandler.postDelayed(mLongPressRunnable, 450);
                }
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                if (DEBUG_SCROLL) {
                    Log.d(TAG, "DOWN target=" + (mTouchTarget == null ? "null"
                            : mTouchTarget.getClass().getSimpleName()));
                }
                return true;

            case MotionEvent.ACTION_MOVE:
                if (mVelocityTracker != null) mVelocityTracker.addMovement(ev);
                mTotalDx += ev.getRawX() - mLastX;
                mTotalDy += ev.getRawY() - mLastY;
                if (!mTouchMoved
                        && (Math.abs(ev.getX() - mDownX) > mTouchSlop
                        || Math.abs(ev.getY() - mDownY) > mTouchSlop)) {
                    mTouchMoved = true;
                    mTouchHandler.removeCallbacks(mLongPressRunnable);
                }
                if (mTouchMoved && !mLongFired && !mOverlayGesture) {
                    handleScrollMove(ev.getRawX() - mLastX, ev.getRawY() - mLastY);
                }
                mLastX = ev.getRawX();
                mLastY = ev.getRawY();
                return true;

            case MotionEvent.ACTION_UP:
                mTouchHandler.removeCallbacks(mLongPressRunnable);
                if (mVelocityTracker != null) mVelocityTracker.addMovement(ev);
                if (mScrollTarget != null && mTouchMoved && !mLongFired) {
                    flingScrollTarget();
                }
                releaseVelocityTracker();
                mScrollTarget = null;
                if (!mLongFired && !mTouchMoved && mTouchTarget != null) {
                    try {
                        mPreviewTouchCallback.onWidgetTapped(mTouchTarget);
                    } catch (Throwable t) {
                        Log.w(TAG, "tap failed", t);
                    }
                }
                mTouchTarget = null;
                mGestureOwnedByCanvas = false;
                mOverlayGesture = false;
                return true;

            case MotionEvent.ACTION_CANCEL:
                if (DEBUG_SCROLL) Log.d(TAG, "CANCEL (parent ne gesture chheena?)");
                mTouchHandler.removeCallbacks(mLongPressRunnable);
                releaseVelocityTracker();
                mScrollTarget = null;
                mTouchTarget = null;
                mGestureOwnedByCanvas = false;
                mOverlayGesture = false;
                return true;

            default:
                return true;
        }
    }


    private void releaseVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private boolean canScrollInDir(View v, boolean horizontal, int dir) {
        if (!isScrollableType(v)) return false;
        return horizontal ? v.canScrollHorizontally(dir) : v.canScrollVertically(dir);
    }

    private boolean isScrollableType(View v) {
        return v instanceof ScrollView
                || v instanceof HorizontalScrollView
                || v instanceof androidx.core.widget.NestedScrollView
                || v instanceof androidx.recyclerview.widget.RecyclerView
                || v instanceof AbsListView;
    }

    private View findScrollTarget(boolean horizontal, float screenDelta) {
        int dir = screenDelta > 0 ? -1 : 1;
        View v = mTouchTarget;
        int depth = 0;
        while (v != null && depth++ <= MAX_COLLECT_DEPTH) {
            if (canScrollInDir(v, horizontal, dir)) return v;
            if (v == mContainerBorderRoot || v == this) break;
            Object p = v.getParent();
            v = (p instanceof View) ? (View) p : null;
        }
        return null;
    }

    private void handleScrollMove(float dxRaw, float dyRaw) {
        if (mTouchTarget == null) return;
        if (!mScrollAxisLocked) {
            mScrollHorizontal = Math.abs(mTotalDx) > Math.abs(mTotalDy);
            mScrollAxisLocked = true;
        }
        float deltaRaw = mScrollHorizontal ? dxRaw : dyRaw;
        if (deltaRaw == 0) return;

        View target = mScrollTarget;
        int dir = deltaRaw > 0 ? -1 : 1;
        if (target == null || !canScrollInDir(target, mScrollHorizontal, dir)) {
            target = findScrollTarget(mScrollHorizontal, deltaRaw);
            mScrollTarget = target;
        }
        if (DEBUG_SCROLL) {
            Log.d(TAG, "scroll horiz=" + mScrollHorizontal + " delta=" + deltaRaw
                    + " target=" + (target == null ? "NULL" : target.getClass().getSimpleName()));
        }
        if (target == null) return;

        float scale = mScrollHorizontal ? accumulatedScale(target) * target.getScaleX()
                                        : accumulatedScaleY(target) * target.getScaleY();
        if (scale <= 0.01f) scale = 1f;
        int amount = Math.round(-deltaRaw / scale);
        if (amount == 0) amount = deltaRaw > 0 ? -1 : 1;

        try {
            int bx = mScrollHorizontal ? amount : 0;
            int by = mScrollHorizontal ? 0 : amount;
            if (target instanceof AbsListView) {
                if (!mScrollHorizontal && android.os.Build.VERSION.SDK_INT >= 19) {
                    ((AbsListView) target).scrollListBy(by);
                }
            } else {
                target.scrollBy(bx, by);
            }
        } catch (Throwable t) {
            Log.w(TAG, "scroll failed", t);
        }
        refreshOverlaysAfterScroll();
    }

    private void flingScrollTarget() {
        if (mVelocityTracker == null || mScrollTarget == null) return;
        ViewConfiguration vc = ViewConfiguration.get(getContext());
        if (mMinFlingVelocity < 0) {
            mMinFlingVelocity = vc.getScaledMinimumFlingVelocity();
            mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();
        }
        mVelocityTracker.computeCurrentVelocity(1000, mMaxFlingVelocity);
        float v = mScrollHorizontal ? mVelocityTracker.getXVelocity()
                                    : mVelocityTracker.getYVelocity();
        if (Math.abs(v) < mMinFlingVelocity) return;
        View t = mScrollTarget;
        float scale = mScrollHorizontal ? accumulatedScale(t) * t.getScaleX()
                                        : accumulatedScaleY(t) * t.getScaleY();
        if (scale <= 0.01f) scale = 1f;
        int vel = Math.round(-v / scale);
        try {
            if (t instanceof ScrollView && !mScrollHorizontal) {
                ((ScrollView) t).fling(vel);
            } else if (t instanceof HorizontalScrollView && mScrollHorizontal) {
                ((HorizontalScrollView) t).fling(vel);
            } else if (t instanceof androidx.core.widget.NestedScrollView && !mScrollHorizontal) {
                ((androidx.core.widget.NestedScrollView) t).fling(vel);
            } else if (t instanceof androidx.recyclerview.widget.RecyclerView) {
                androidx.recyclerview.widget.RecyclerView rv =
                        (androidx.recyclerview.widget.RecyclerView) t;
                if (mScrollHorizontal) rv.fling(vel, 0); else rv.fling(0, vel);
            }
        } catch (Throwable th) {
            Log.w(TAG, "fling failed", th);
        }
    }

    private void refreshOverlaysAfterScroll() {
        if (mFocusedView != null && isViewDrawable(mFocusedView)) {
            mFocusedRect = getRelativeRect(mFocusedView);
        }
        if (mShowBorders) {
            mContainerRects = collectContainerRects(
                    mContainerBorderRoot != null ? mContainerBorderRoot : this, 0);
        }
        invalidate();
    }

    private final ViewTreeObserver.OnScrollChangedListener mScrollListener =
            new ViewTreeObserver.OnScrollChangedListener() {
        @Override public void onScrollChanged() {
            refreshOverlaysAfterScroll();
        }
    };

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnScrollChangedListener(mScrollListener);
    }

    @Override
    protected void onDetachedFromWindow() {
        try {
            getViewTreeObserver().removeOnScrollChangedListener(mScrollListener);
        } catch (Throwable ignore) {}
        mTouchHandler.removeCallbacks(mLongPressRunnable);
        releaseVelocityTracker();
        super.onDetachedFromWindow();
    }

    private View findWidgetAt(float x, float y) {
        ViewGroup root = mContainerBorderRoot != null ? mContainerBorderRoot : this;
        int[] myLoc = new int[2];
        getLocationOnScreen(myLoc);
        return hitTestOnScreen(root, myLoc[0] + x, myLoc[1] + y, 0);
    }

    private boolean isPointInsideCanvas(float rawX, float rawY) {
        View canvas = mContainerBorderRoot;
        if (canvas == null || !canvas.isShown()) return false;
        Rect visible = new Rect();
        if (!canvas.getGlobalVisibleRect(visible)) return false;
        return visible.contains((int) rawX, (int) rawY);
    }

    private boolean isPointOverOverlayChrome(float rawX, float rawY) {
        return findChromeAt(this, rawX, rawY, 0);
    }

    private boolean findChromeAt(ViewGroup parent, float rawX, float rawY, int depth) {
        if (depth > 6) return false;
        for (int i = parent.getChildCount() - 1; i >= 0; i--) {
            View c = parent.getChildAt(i);
            if (c.getVisibility() != View.VISIBLE) continue;
            if (c == mContainerBorderRoot) continue;
            if (isDescendantOf(c, mContainerBorderRoot)) continue;
            boolean chrome = "editor_chrome".equals(c.getTag());
            if (chrome) {
                Rect r = new Rect();
                if (c.getGlobalVisibleRect(r) && r.contains((int) rawX, (int) rawY)) {
                    return true;
                }
            }
            if (c instanceof ViewGroup && c != mContainerBorderRoot) {
                if (findChromeAt((ViewGroup) c, rawX, rawY, depth + 1)) return true;
            }
        }
        return false;
    }

    private boolean isDescendantOf(View v, View ancestor) {
        if (ancestor == null) return false;
        Object p = v.getParent();
        int d = 0;
        while (p instanceof View && d++ < MAX_COLLECT_DEPTH) {
            if (p == ancestor) return true;
            p = ((View) p).getParent();
        }
        return false;
    }

    private float accumulatedScaleY(View v) {
        float s = 1f;
        Object p = v.getParent();
        int d = 0;
        while (p instanceof View && d++ < MAX_COLLECT_DEPTH) {
            View pv = (View) p;
            s *= pv.getScaleY();
            if (pv == this) break;
            p = pv.getParent();
        }
        return s;
    }

    private View hitTestOnScreen(ViewGroup parent, float sx, float sy, int depth) {
        if (depth > MAX_COLLECT_DEPTH) return null;
        for (int i = parent.getChildCount() - 1; i >= 0; i--) {
            View c = parent.getChildAt(i);
            if (c.getVisibility() != View.VISIBLE) continue;
            int[] loc = new int[2];
            c.getLocationOnScreen(loc);
            float total = c.getScaleX() * accumulatedScale(c);
            float totalY = c.getScaleY() * accumulatedScaleY(c);
            float w = c.getWidth() * total;
            float h = c.getHeight() * totalY;
            if (sx < loc[0] || sy < loc[1] || sx > loc[0] + w || sy > loc[1] + h) continue;
            if (c instanceof ViewGroup && isEditorContainer((ViewGroup) c)) {
                View deeper = hitTestOnScreen((ViewGroup) c, sx, sy, depth + 1);
                if (deeper != null) return deeper;
            }
            return c;
        }
        return null;
    }

    private float accumulatedScale(View v) {
        float s = 1f;
        Object p = v.getParent();
        int d = 0;
        while (p instanceof View && d++ < MAX_COLLECT_DEPTH) {
            View pv = (View) p;
            s *= pv.getScaleX();
            if (pv == this) break;
            p = pv.getParent();
        }
        return s;
    }

    private boolean isEditorContainer(ViewGroup vg) {
        if (vg instanceof com.google.android.material.tabs.TabLayout
                || vg instanceof com.google.android.material.bottomnavigation.BottomNavigationView
                || vg instanceof com.google.android.material.navigation.NavigationView
                || vg instanceof com.google.android.material.textfield.TextInputLayout
                || vg instanceof android.widget.AdapterView
                || vg instanceof androidx.recyclerview.widget.RecyclerView
                || vg instanceof androidx.viewpager2.widget.ViewPager2
                || vg instanceof android.widget.HorizontalScrollView
                        && vg.getChildCount() > 0
                        && vg.getChildAt(0) instanceof com.google.android.material.chip.ChipGroup
                || vg instanceof android.widget.Spinner
                || vg instanceof android.widget.CalendarView
                || vg instanceof android.widget.DatePicker
                || vg instanceof android.widget.TimePicker
                || vg instanceof android.widget.NumberPicker
                || vg instanceof android.widget.SearchView) {
            return false;
        }
        String n = vg.getClass().getName();
        if (n.startsWith("com.nexusteam.blacklogics.sdk.widgets.Placeholder")) return false;
        return true;
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (mFocusedView != null
                && mFocusedView.isAttachedToWindow()
                && mFocusedView.getWidth() > 0
                && mFocusedView.getHeight() > 0) {
            mFocusedRect = getRelativeRect(mFocusedView);
        }

        if (mShowBorders) {
            mContainerRects = collectContainerRects(
                    mContainerBorderRoot != null ? mContainerBorderRoot : this, 0);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        if (mShowGrid) {
            try {
                drawGrid(canvas);
            } catch (Exception e) {
                Log.e(TAG, "drawGrid error", e);
            }
        }

        super.dispatchDraw(canvas);

        // Clip overlays to EditorLayout visible area so tall ScrollView content
        // does not paint selection/outline outside the editor viewport.
        int save = canvas.save();
        try {
            canvas.clipRect(getOverlayClipRect());

            if (mShowBorders && !mContainerRects.isEmpty()) {
                try {
                    drawContainerBorders(canvas);
                } catch (Exception e) {
                    Log.e(TAG, "drawContainerBorders error", e);
                }
            }

            if (mHoveredView != null && isViewDrawable(mHoveredView)) {
                try {
                    drawHoverHighlight(canvas);
                } catch (Exception e) {
                    Log.e(TAG, "drawHoverHighlight error", e);
                }
            }

            if (mFocusedView != null && isViewDrawable(mFocusedView)) {
                try {
                    drawFocusHighlight(canvas);
                    if (!mFocusedRect.isEmpty()) {
                        int hsPx = Math.round(dp(RESIZE_HANDLE_RADIUS));
                        Rect handleClip = getOverlayClipRect();
                        handleClip.inset(-hsPx, -hsPx);
                        int hSave = canvas.save();
                        canvas.clipRect(handleClip);
                        drawResizeHandles(canvas, mFocusedRect);
                        canvas.restoreToCount(hSave);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "drawFocusHighlight error", e);
                }
            }
        } finally {
            canvas.restoreToCount(save);
        }
    }


    private void drawGrid(Canvas canvas) {
        int w = getWidth();
        int h = getHeight();
        if (w == 0 || h == 0) return;

        float step = dp(GRID_STEP_DP);
        if (step <= 0) return;

        for (float x = 0; x < w; x += step) {
            canvas.drawLine(x, 0, x, h, mGridPaint);
        }
        for (float y = 0; y < h; y += step) {
            canvas.drawLine(0, y, w, y, mGridPaint);
        }
    }

    private void drawContainerBorders(Canvas canvas) {
        for (int i = 0; i < mContainerRects.size(); i++) {
            Rect r = mContainerRects.get(i);
            if (r != null && !r.isEmpty()) {
                canvas.drawRect(r.left, r.top, r.right, r.bottom, mContainerPaint);
            }
        }
    }

    /**
     * Part of {@code v} that is really visible on screen (clipped by every ancestor, e.g. a
     * ScrollView that has scrolled it under a fixed Toolbar), in this view's coordinates.
     * Returns null when nothing of it is visible.
     */
    private Rect getAncestorVisibleRect(View v) {
        if (v == null || v == mOverlayTapTarget) return new Rect(getOverlayClipRect());
        Rect g = new Rect();
        if (!v.getGlobalVisibleRect(g)) return null;
        int[] me = new int[2];
        getLocationOnScreen(me);
        g.offset(-me[0], -me[1]);
        return g;
    }

    private void drawHoverHighlight(Canvas canvas) {
        Rect r = getRelativeRect(mHoveredView);
        if (r.isEmpty()) return;
        Rect visible = getOverlayClipRect();
        if (!r.intersect(visible)) return;
        Rect anc = getAncestorVisibleRect(mHoveredView);
        if (anc == null || !r.intersect(anc)) return;
        RectF rf = toRectF(r);
        float cr = dp(FOCUS_CORNER_RADIUS);
        canvas.drawRoundRect(rf, cr, cr, mHoverPaint);
    }

    private void drawFocusHighlight(Canvas canvas) {
        Rect r = getRelativeRect(mFocusedView);
        if (r.isEmpty()) return;
        // Keep outline inside editor viewport (tall ScrollView content)
        Rect visible = getOverlayClipRect();
        if (!r.intersect(visible)) {
            mFocusedRect = new Rect();
            return;
        }
        // Widget scrolled out of its ScrollView must not paint over the Toolbar / action bar
        Rect anc = getAncestorVisibleRect(mFocusedView);
        if (anc == null || !r.intersect(anc)) {
            mFocusedRect = new Rect();
            return;
        }
        mFocusedRect = r;
        RectF rf = toRectF(r);
        float cr = dp(FOCUS_CORNER_RADIUS);
        canvas.drawRoundRect(rf, cr, cr, mFocusFillPaint);
        canvas.drawRoundRect(rf, cr, cr, mFocusStrokePaint);
    }

    private void drawResizeHandles(Canvas canvas, Rect r) {
        float hs = dp(RESIZE_HANDLE_RADIUS);
        canvas.drawCircle(r.left,                        r.top,                        hs, mResizeHandlePaint);
        canvas.drawCircle(r.right,                       r.top,                        hs, mResizeHandlePaint);
        canvas.drawCircle(r.left,                        r.bottom,                     hs, mResizeHandlePaint);
        canvas.drawCircle(r.right,                       r.bottom,                     hs, mResizeHandlePaint);
        canvas.drawCircle((r.left + r.right) / 2f,      r.top,                        hs, mResizeHandlePaint);
        canvas.drawCircle((r.left + r.right) / 2f,      r.bottom,                     hs, mResizeHandlePaint);
        canvas.drawCircle(r.left,                        (r.top + r.bottom) / 2f,      hs, mResizeHandlePaint);
        canvas.drawCircle(r.right,                       (r.top + r.bottom) / 2f,      hs, mResizeHandlePaint);
    }


    private int getRelativeLeft(View view) {
        if (mContainerBorderRoot != null && isStrictDescendantOf(view, mContainerBorderRoot)) {
            int rawOffset = getOffsetWithinAncestor(view, mContainerBorderRoot);
            int scaledOffset = Math.round(rawOffset * mContainerBorderRoot.getScaleX());
            return scaledOffset + getRelativeLeft(mContainerBorderRoot, 0);
        }
        return getRelativeLeft(view, 0);
    }

    private int getRelativeLeft(View view, int depth) {
        if (view == null || view == this || depth > MAX_COLLECT_DEPTH) return 0;
        if (view.getParent() == this) return view.getLeft();
        Object parent = view.getParent();
        if (!(parent instanceof View)) return view.getLeft();
        return view.getLeft() - ((View) parent).getScrollX()
                + getRelativeLeft((View) parent, depth + 1);
    }

    private int getRelativeTop(View view) {
        if (mContainerBorderRoot != null && isStrictDescendantOf(view, mContainerBorderRoot)) {
            int rawOffset = getVerticalOffsetWithinAncestor(view, mContainerBorderRoot);
            int scaledOffset = Math.round(rawOffset * mContainerBorderRoot.getScaleY());
            return scaledOffset + getRelativeTop(mContainerBorderRoot, 0);
        }
        return getRelativeTop(view, 0);
    }

    private int getRelativeTop(View view, int depth) {
        if (view == null || view == this || depth > MAX_COLLECT_DEPTH) return 0;
        if (view.getParent() == this) return view.getTop();
        Object parent = view.getParent();
        if (!(parent instanceof View)) return view.getTop();
        return view.getTop() - ((View) parent).getScrollY()
                + getRelativeTop((View) parent, depth + 1);
    }

    private int getOffsetWithinAncestor(View view, View ancestor) {
        int offset = 0;
        View current = view;
        int depth = 0;
        while (current != null && current != ancestor && depth <= MAX_COLLECT_DEPTH) {
            offset += current.getLeft();
            Object p = current.getParent();
            if (p instanceof View && p != ancestor) offset -= ((View) p).getScrollX();
            current = (p instanceof View) ? (View) p : null;
            depth++;
        }
        return offset;
    }

    private int getVerticalOffsetWithinAncestor(View view, View ancestor) {
        int offset = 0;
        View current = view;
        int depth = 0;
        while (current != null && current != ancestor && depth <= MAX_COLLECT_DEPTH) {
            offset += current.getTop();
            Object p = current.getParent();
            if (p instanceof View && p != ancestor) offset -= ((View) p).getScrollY();
            current = (p instanceof View) ? (View) p : null;
            depth++;
        }
        return offset;
    }

    private boolean isStrictDescendantOf(View view, View ancestor) {
        if (view == null || ancestor == null || view == ancestor) return false;
        View current = view;
        int depth = 0;
        while (current != null && depth <= MAX_COLLECT_DEPTH) {
            Object p = current.getParent();
            if (p == ancestor) return true;
            current = (p instanceof View) ? (View) p : null;
            depth++;
        }
        return false;
    }

    private Rect getRelativeRect(View view) {
        if (view == null) return new Rect();
        if (view.getWidth() == 0 || view.getHeight() == 0) return new Rect();
        if (view == mOverlayTapTarget && view.getParent() != null) {
            // overlay (FAB) is positioned with translation/scale outside the canvas tree,
            // so derive its box from real screen coordinates
            int[] me = new int[2];
            int[] vl = new int[2];
            getLocationOnScreen(me);
            view.getLocationOnScreen(vl);
            int ow = Math.round(view.getWidth() * view.getScaleX());
            int oh = Math.round(view.getHeight() * view.getScaleY());
            int ol = vl[0] - me[0];
            int ot = vl[1] - me[1];
            return new Rect(ol, ot, ol + ow, ot + oh);
        }
        int left = getRelativeLeft(view);
        int top  = getRelativeTop(view);

        float scaleX = 1f, scaleY = 1f;
        if (mContainerBorderRoot != null && isStrictDescendantOf(view, mContainerBorderRoot)) {
            scaleX = mContainerBorderRoot.getScaleX();
            scaleY = mContainerBorderRoot.getScaleY();
        }
        int w = Math.round(view.getWidth()  * scaleX);
        int h = Math.round(view.getHeight() * scaleY);
        return new Rect(left, top, left + w, top + h);
    }

    private Rect getContentBoundingRect(ViewGroup group) {
        if (group == null || group.getChildCount() == 0) return new Rect();
        Rect union = null;
        for (int i = 0; i < group.getChildCount(); i++) {
            View child = group.getChildAt(i);
            Rect r = getRelativeRect(child);
            if (r.isEmpty()) continue;
            if (union == null) {
                union = new Rect(r);
            } else {
                union.union(r);
            }
        }
        return union != null ? union : new Rect();
    }

    private ArrayList<Rect> collectContainerRects(ViewGroup parent, int depth) {
        ArrayList<Rect> list = new ArrayList<Rect>();
        if (depth > MAX_COLLECT_DEPTH) return list;
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            if (child instanceof ViewGroup) {
                ViewGroup childGroup = (ViewGroup) child;
                Rect r = getContentBoundingRect(childGroup);
                if (!r.isEmpty()) list.add(r);
                list.addAll(collectContainerRects(childGroup, depth + 1));
            }
        }
        return list;
    }


    private boolean isViewDrawable(View view) {
        return view != null
                && view.isAttachedToWindow()
                && view.getWidth() > 0
                && view.getHeight() > 0;
    }


    private float dp(float value) {
        return value * mDensity;
    }

    private RectF toRectF(Rect r) {
        return new RectF(r.left, r.top, r.right, r.bottom);
    }
}