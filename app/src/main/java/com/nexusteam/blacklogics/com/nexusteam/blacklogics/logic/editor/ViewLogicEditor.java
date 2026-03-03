package com.besome.blacklogics.logic.editor;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout.LayoutParams;

public class ViewLogicEditor extends LogicEditorScrollView {
    private boolean isFirst;
    private Context mContext;
    private BlockPane pane;
    private int[] posArea;

    public ViewLogicEditor(Context context) {
        super(context);
        this.isFirst = true;
        this.posArea = new int[2];
        init(context);
    }

    public ViewLogicEditor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isFirst = true;
        this.posArea = new int[2];
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        this.pane = new BlockPane(this.mContext);
        this.pane.setLayoutParams(new LayoutParams(-2, -2));
        addView(this.pane);
    }

    public BlockPane getBlockPane() {
        return this.pane;
    }

    public boolean hitTest(float f, float f2) {
        getLocationOnScreen(this.posArea);
        return f > ((float) this.posArea[0]) && f < ((float) (this.posArea[0] + getWidth())) && f2 > ((float) this.posArea[1]) && f2 < ((float) (this.posArea[1] + getHeight()));
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.isFirst) {
            this.pane.getLayoutParams().width = i3 - i;
            this.pane.getLayoutParams().height = i4 - i2;
            this.pane.calculateWidthHeight();
            this.isFirst = false;
        }
    }
}
