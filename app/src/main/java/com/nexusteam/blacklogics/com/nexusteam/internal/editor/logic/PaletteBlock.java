package com.nexusteam.internal.editor.logic;
import com.nexusteam.blacklogics.R;

import com.nexusteam.internal.DebugLog;
import com.nexusteam.internal.fm;
import com.nexusteam.internal.fo;
import com.nexusteam.internal.kp;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nexusteam.internal.lib.ui.CustomHorizontalScrollView;
import com.nexusteam.internal.lib.ui.CustomScrollView;

public class PaletteBlock extends LinearLayout {
    
    /* renamed from: a  reason: collision with root package name */
    public CustomScrollView f1116a;
    public CustomHorizontalScrollView b;
    public LinearLayout c;
    public PaletteSelector d;
    private Context e;
    private float f = 0.0f;
    private boolean creatingFm = false;
    
    public PaletteBlock(Context context) {
        super(context);
        a(context);
    }
    
    public PaletteBlock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }
    
    private void a(Context context) {
        this.e = context;
        kp.a(context, this, R.layout.palette_block);
        this.d = (PaletteSelector) findViewById(R.id.palette_selector);
        this.f1116a = (CustomScrollView) findViewById(R.id.scv);
        this.b = (CustomHorizontalScrollView) findViewById(R.id.hscv);
        this.c = (LinearLayout) findViewById(R.id.block_builder);
        this.f = kp.a(this.e, 1.0f);
    }
    
    public void setMinWidth(int i) {
        this.f1116a.setMinimumWidth(i - ((int) (this.f * 5.0f)));
        this.b.setMinimumWidth(i - ((int) (this.f * 5.0f)));
        getLayoutParams().width = i;
    }
    
    public void setDragEnabled(boolean z) {
        if (z) {
            this.f1116a.a();
            this.b.a();
            return;
        }
        this.f1116a.b();
        this.b.b();
    }
    
    public void setUseScroll(boolean z) {
        this.f1116a.setUseScroll(z);
        this.b.setUseScroll(z);
    }
    
    public fo a(String str, String str2, String str3) {
        if (creatingFm) {
            DebugLog.e(e, "PaletteBlock", "⚠️ Recursive fm creation prevented: " + str2);
            fm emptyFm = new fm(this.e, -999, "temp", "s", "getArg");
            emptyFm.setBlockType(1);
            return emptyFm;
        }
        creatingFm = true;
        try {
            fm fmVar = new fm(this.e, -1, str, str2, str3);
            fmVar.setBlockType(1);
            this.c.addView(fmVar);
            return fmVar;
        } finally {
            creatingFm = false;
        }
    }
    
    
    
    public fo a(String str, String str2, String str3, String str4) {
        try {
            View view = new View(this.e);
            view.setLayoutParams(new LinearLayout.LayoutParams(-1, (int) (this.f * 8.0f)));
            this.c.addView(view);
            fm fmVar = new fm(this.e, -1, str, str2, str3, str4);
            fmVar.setBlockType(1);
            this.c.addView(fmVar);
            return fmVar;
        } catch(Exception ez) {
            DebugLog.e(e, "Failed str str2 str3 stré", ez.getMessage());
            View v = new View(this.e);
            return null;
        }
    }
    
    public TextView a(String str) {
        TextView textView = new TextView(this.e);
        textView.setLayoutParams(new LinearLayout.LayoutParams(-1, (int) (this.f * 30.0f)));
        textView.setBackgroundResource(R.drawable.bg_feed);
        textView.setText(str);
        textView.setTextSize(10.0f);
        textView.setGravity(17);
        textView.setPadding((int) (this.f * 8.0f), 0, (int) (this.f * 8.0f), 0);
        this.c.addView(textView);
        return textView;
    }
    
    public void a(String str, int i) {
        TextView textView = new TextView(this.e);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, (int) (this.f * 16.0f));
        layoutParams.topMargin = (int) (this.f * 16.0f);
        textView.setLayoutParams(layoutParams);
        textView.setBackgroundColor(i);
        textView.setText(str);
        textView.setTextColor(-1);
        textView.setTextSize(9.0f);
        textView.setTypeface((Typeface) null, 1);
        textView.setGravity(19);
        textView.setPadding((int) (this.f * 12.0f), 0, (int) (this.f * 12.0f), 0);
        this.c.addView(textView);
    }
    
    public void a() {
        this.c.removeAllViews();
    }
}
