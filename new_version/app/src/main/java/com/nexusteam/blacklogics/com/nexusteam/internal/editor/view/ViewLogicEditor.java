package com.nexusteam.internal.editor.view;

import com.nexusteam.internal.fq;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class ViewLogicEditor extends LogicEditorScrollView {

    /* renamed from: a  reason: collision with root package name */
    private Context f1400a;
    private fq b;
    private boolean c = true;
    private int[] d = new int[2];

    public ViewLogicEditor(Context context) {
        super(context);
        a(context);
    }

    public ViewLogicEditor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    private void a(Context context) {
        this.f1400a = context;
        this.b = new fq(this.f1400a);
        this.b.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        addView(this.b);
    }

    public fq getBlockPane() {
        return this.b;
    }

    public boolean a(float f, float f2) {
        getLocationOnScreen(this.d);
        return f > ((float) this.d[0]) && f < ((float) (this.d[0] + getWidth())) && f2 > ((float) this.d[1]) && f2 < ((float) (this.d[1] + getHeight()));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.c) {
            this.b.getLayoutParams().width = i3 - i;
            this.b.getLayoutParams().height = i4 - i2;
            this.b.c();
            this.c = false;
        }
    }
}
