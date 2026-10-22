package com.nexusteam.internal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatTextView;
import com.nexusteam.internal.beans.ViewBean;

public class MaterialButtonEditor extends AppCompatTextView implements hq {

	public Paint selectionPaint;
	private ViewBean bean;
	private boolean selected;
	private boolean fixed;

	public MaterialButtonEditor(Context context) {
		super(context);
		init(context);
	}

	protected void init(Context context) {
		setGravity(17);
		setTypeface((Typeface) null, 1);
		setText("MaterialButton");
		setTextSize(12f);
		selectionPaint = new Paint(1);
		selectionPaint.setColor(-1785080368);
		setDrawingCacheEnabled(true);
		setFocusable(false);
		setClickable(false);
		setMinimumWidth((int) kp.a(context, 48.0f));
		setMinimumHeight((int) kp.a(context, 32.0f));
	}

	@Override public ViewBean getBean() { return bean; }
	@Override public void setBean(ViewBean viewBean) {
		this.bean = viewBean;
		if (viewBean != null && viewBean.id != null) setTag(viewBean.id);
	}
	@Override public void setSelection(boolean z) { selected = z; invalidate(); }
	@Override public boolean getFixed() { return fixed; }
	public void setFixed(boolean z) { fixed = z; }

	@Override
	protected void onDraw(Canvas canvas) {
		if (selected) {
			canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), selectionPaint);
		}
		super.onDraw(canvas);
	}
}
