package com.nexusteam.internal.editor.view.palette;

import com.nexusteam.internal.iq;
import com.nexusteam.internal.kp;
import com.nexusteam.internal.kq;
import android.content.Context;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nexusteam.internal.beans.ViewBean;

import com.nexusteam.internal.lib.ui.CustomScrollView;

import java.util.ArrayList;

public class PaletteFavorite extends LinearLayout implements View.OnClickListener {

    /* renamed from: a  reason: collision with root package name */
    private LinearLayout f1416a;
    private CustomScrollView b;
    private CardView c;

    public PaletteFavorite(Context context) {
        super(context);
        a(context);
    }

    public PaletteFavorite(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    private void a(Context context) {
        kp.a(context, this, com.nexusteam.blacklogics.R.layout.palette_favorite);
        this.f1416a = (LinearLayout) findViewById(com.nexusteam.blacklogics.R.id.widget);
        this.b = (CustomScrollView) findViewById(com.nexusteam.blacklogics.R.id.scv);
        this.c = (CardView) findViewById(com.nexusteam.blacklogics.R.id.cv_shared_views);
        this.c.setOnClickListener(this);
        ((TextView) findViewById(com.nexusteam.blacklogics.R.id.tv_shared_views)).setText("Shared View");
    }

    public void setScrollEnabled(boolean z) {
        if (z) {
            this.b.a();
        } else {
            this.b.b();
        }
    }

    public void a() {
        this.f1416a.removeAllViews();
    }

    public View a(String str, ArrayList<ViewBean> arrayList) {
        iq iqVar = new iq(getContext(), str, arrayList);
        this.f1416a.addView(iqVar);
        return iqVar;
    }

    private void b() {



    }

    public void onClick(View view) {
        if (view.getId() == com.nexusteam.blacklogics.R.id.cv_shared_views) {
            b();
        }
    }
}
