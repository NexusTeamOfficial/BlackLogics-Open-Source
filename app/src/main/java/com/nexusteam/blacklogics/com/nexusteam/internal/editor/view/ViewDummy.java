package com.nexusteam.internal.editor.view;

import com.nexusteam.internal.kp;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.nexusteam.blacklogics.R;

public class ViewDummy extends RelativeLayout {
    
    /* renamed from: a  reason: collision with root package name */
    private ImageView f1387a;
    private ImageView b;
    private LinearLayout c;
    private int[] d = new int[2];
    private int[] e = new int[2];
    private boolean f = false;
    
    private int resId = 0;
    
    public ViewDummy(Context context) {
        super(context);
        a(context);
    }
    
    public ViewDummy(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }
    
    private void a(Context context) {
        kp.a(context, this, R.layout.dummy);
        this.f1387a = (ImageView) findViewById(R.id.img_notallowed);
        this.b = (ImageView) findViewById(R.id.img_dummy);
        this.c = (LinearLayout) findViewById(R.id.layout_dummy);
    }
    
    private Bitmap b(View view) {
        Bitmap createBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(createBitmap));
        return createBitmap;
    }
    
    public void a(View view) {
        Bitmap b2 = b(view);
        view.getLocationOnScreen(this.d);
        this.b.setImageBitmap(b2);
        this.b.setAlpha(0.5f);
    }
    
    public void a(com.nexusteam.internal.fm fmVar) {
        String w = fmVar.w;
        int type = -1;
        
        switch (w) {
            case "a":
            type = 10;
            break;
            case "b":
            type = 0;
            break;
            case "c":
            type = 4;
            break;
            case "d":
            type = 1;
            break;
            case "e":
            type = 5;
            break;
            case "f":
            type = 6;
            break;
            case "l":
            type = 9;
            break;
            case "n":
            type = 2;
            break;
            case "p":
            type = 8;
            break;
            case "s":
            type = 3;
            break;
            case "v":
            type = 7;
            break;
        }
        
        switch (type) {
            case 0:
            this.b.setImageResource(R.drawable.selected_block_boolean);
            break;
            case 1:
            case 2:
            this.b.setImageResource(R.drawable.selected_block_integer);
            break;
            case 3:
            this.b.setImageResource(R.drawable.selected_block_string);
            break;
            case 4:
            this.b.setImageResource(R.drawable.selected_block_loop);
            break;
            case 5:
            this.b.setImageResource(R.drawable.selected_block_ifelse);
            break;
            case 6:
            this.b.setImageResource(R.drawable.selected_block_final);
            break;
            case 7:
            case 8:
            case 9:
            case 10:
            this.b.setImageResource(R.drawable.selected_block_string);
            break;
            default:
            this.b.setImageResource(R.drawable.selected_block_command);
        }
        
        this.b.setAlpha(0.5f);
        fmVar.getLocationOnScreen(this.d);
    }
    
    
    public void a(View view, float f2, float f3, float f4, float f5, float f6, float f7) {
        if (this.c.getVisibility() != 0) {
            setDummyVisibility(0);
        }
        getLocationOnScreen(this.e);
        this.c.setX((((((float) (this.d[0] - this.e[0])) + f2) - f4) - ((float) this.f1387a.getWidth())) + f6);
        this.c.setY(((((float) (this.d[1] - this.e[1])) + f3) - f5) + f7);
    }
    
    public void a(View view, float f2, float f3, float f4, float f5) {
        a(view, f2, f3, f4, f5, 0.0f, 0.0f);
    }
    
    public void setDummyVisibility(int i) {
        this.c.setVisibility(i);
    }
    
    public void setAllow(boolean z) {
        this.f = z;
        if (z) {
            this.f1387a.setVisibility(4);
        } else {
            this.f1387a.setVisibility(0);
        }
    }
    
    public boolean getAllow() {
        return this.f;
    }
    
    public void a(int[] iArr) {
        this.b.getLocationOnScreen(iArr);
    }
}
