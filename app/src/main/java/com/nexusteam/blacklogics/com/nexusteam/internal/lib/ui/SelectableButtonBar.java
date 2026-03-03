package com.nexusteam.internal.lib.ui;
import com.nexusteam.blacklogics.R;

import com.nexusteam.internal.jy;
import com.nexusteam.internal.kp;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class SelectableButtonBar extends LinearLayout {

    /* renamed from: a  reason: collision with root package name */
    private ArrayList<Integer> f1604a;
    private ArrayList<String> b;
    private jy c;
    private int d = 0;

    public SelectableButtonBar(Context context) {
        super(context);
        a(context);
    }

    public SelectableButtonBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    private void a(Context context) {
        this.f1604a = new ArrayList<>();
        this.b = new ArrayList<>();
    }

    public void a(int i, String str) {
        this.f1604a.add(Integer.valueOf(i));
        this.b.add(str);
    }

    public void setListener(jy jyVar) {
        this.c = jyVar;
    }

    public jy getListener() {
        return this.c;
    }

    public void a() {
        removeAllViews();
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            LinearLayout b2 = b(this.f1604a.get(i).intValue(), this.b.get(i));
            if (i == 0) {
                b2.setBackgroundResource(R.drawable.selector_btnbar_left);
            } else if (i == size - 1) {
                b2.setBackgroundResource(R.drawable.selector_btnbar_right);
            } else {
                b2.setBackgroundResource(R.drawable.selector_btnbar_center);
            }
            addView(b2);
        }
        setSelectedItemByIndex(this.d);
    }

    private LinearLayout b(int i, String str) {
        a aVar = new a(this, getContext(), i, str);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -2);
        layoutParams.weight = 1.0f;
        aVar.setLayoutParams(layoutParams);
        aVar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SelectableButtonBar.this.setSelectedItemByKey(((a) view).b);
            }
        });
        return aVar;
    }

    public void setSelectedItemByIndex(int i) {
        this.d = i;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            a aVar = (a) getChildAt(i2);
            aVar.setSelected(false);
            if (i2 == i) {
                aVar.setSelected(true);
            }
        }
    }

    public void setSelectedItemByKey(int i) {
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            a aVar = (a) getChildAt(i2);
            aVar.setSelected(false);
            if (aVar.b == i) {
                aVar.setSelected(true);
                this.d = i2;
                if (this.c != null) {
                    this.c.a(i);
                }
            }
        }
    }

    public int getSelectedItemKey() {
        return ((a) getChildAt(this.d)).b;
    }

    public class a extends LinearLayout {

        /* renamed from: a  reason: collision with root package name */
        int f1606a;
        int b;
        String c;
        TextView d;

        public a(SelectableButtonBar selectableButtonBar, Context context, int i, String str) {
            this(context, 0, i, str);
        }

        public a(Context context, int i, int i2, String str) {
            super(context);
            a(context, i, i2, str);
        }

        private void a(Context context, int i, int i2, String str) {
            this.f1606a = i;
            this.b = i2;
            this.c = str;
            setOrientation(0);
            setGravity(17);
            int a2 = (int) kp.a(getContext(), 4.0f);
            setPadding(a2, a2, a2, a2);
            this.d = new TextView(context);
            this.d.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
            this.d.setText(str);
            this.d.setTextColor(-16740915);
            this.d.setLines(1);
            this.d.setTextSize(2, 12.0f);
            addView(this.d);
        }

        public void setSelected(boolean z) {
            super.setSelected(z);
            if (z) {
                this.d.setTextColor(-1);
            } else {
                this.d.setTextColor(-16740915);
            }
        }
    }
}
