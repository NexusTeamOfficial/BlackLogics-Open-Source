package com.nexusteam.internal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.LinearLayout;
import com.nexusteam.internal.beans.ViewBean;

public class NestedScrollViewEditor extends LinearLayout implements hq, hr {

	public Paint selectionPaint;
	private ViewBean bean;
	private boolean selected;
	private boolean fixed;
	private int layoutGravity;

	public NestedScrollViewEditor(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		setOrientation(VERTICAL);
		setDrawingCacheEnabled(true);
		setMinimumWidth((int) kp.a(context, 32.0f));
		setMinimumHeight((int) kp.a(context, 32.0f));
		selectionPaint = new Paint(1);
		selectionPaint.setStrokeWidth(kp.a(getContext(), 2.0f));
		selectionPaint.setColor(-1785080368);
	}

	@Override public ViewBean getBean() { return bean; }
	@Override public void setBean(ViewBean viewBean) { this.bean = viewBean; }
	@Override public void setSelection(boolean z) { selected = z; invalidate(); }
	@Override public boolean getFixed() { return fixed; }
	public void setFixed(boolean z) { fixed = z; }
	public void setLayoutGravity(int i) { layoutGravity = i; super.setGravity(i); }
	public int getLayoutGravity() { return layoutGravity; }
	@Override public void a() { requestLayout(); invalidate(); }
	@Override public void setChildScrollEnabled(boolean z) { }

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (selected) {
			canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), selectionPaint);
		}
	}
}
