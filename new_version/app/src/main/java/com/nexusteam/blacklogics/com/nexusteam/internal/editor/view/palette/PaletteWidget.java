package com.nexusteam.internal.editor.view.palette;

import com.nexusteam.internal.ik;
import com.nexusteam.internal.il;
import com.nexusteam.internal.im;
import com.nexusteam.internal.in;
import com.nexusteam.internal.io;
import com.nexusteam.internal.ip;
import com.nexusteam.internal.ir;
import com.nexusteam.internal.is;
import com.nexusteam.internal.it;
import com.nexusteam.internal.iu;
import com.nexusteam.internal.iv;
import com.nexusteam.internal.iw;
import com.nexusteam.internal.ix;
import com.nexusteam.internal.iy;
import com.nexusteam.internal.iz;
import com.nexusteam.internal.ja;
import com.nexusteam.internal.jb;
import com.nexusteam.internal.jc;
import com.nexusteam.internal.jd;
import com.nexusteam.internal.kp;
import com.nexusteam.internal.kq;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nexusteam.internal.lib.ui.CustomScrollView;
import com.nexusteam.blacklogics.R;

public class PaletteWidget extends LinearLayout implements View.OnClickListener {

    /* renamed from: a  reason: collision with root package name */
    private LinearLayout f1417a;
    private LinearLayout b;
    private View c;
    private TextView d;
    private TextView e;
    private CustomScrollView f;

    public enum a {
        eLinearHorizontal,
        eLinearVertical,
        eScrollHorizontal,
        eScrollVertical
    }

    public enum b {
        eButton,
        eTextView,
        eEditText,
        eImageView,
        eListView,
        eSpinner,
        eCheckBox,
        eWebView,
        eSwitch,
        eSeekBar,
        eCalendarView,
        eAdView,
        eProgressBar,
        eMapView
    }

    public void onClick(View view) {
    }

    public PaletteWidget(Context context) {
        super(context);
        a(context);
    }

    public PaletteWidget(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    private void a(Context context) {
        kp.a(context, this, R.layout.palette_widget);
        this.f1417a = (LinearLayout) findViewById(R.id.layout);
        this.b = (LinearLayout) findViewById(R.id.widget);
        this.c = findViewById(R.id.divider);
        this.d = (TextView) findViewById(R.id.tv_layout);
        this.e = (TextView) findViewById(R.id.tv_widget);
        this.d.setText("Layouts");
        this.e.setText("Widgets");
        this.f = (CustomScrollView) findViewById(R.id.scv);
    }

    public void setScrollEnabled(boolean z) {
        if (z) {
            this.f.a();
        } else {
            this.f.b();
        }
    }

    public void a() {
        this.f1417a.removeAllViews();
    }

    public void b() {
        this.b.removeAllViews();
    }

    public View a(a aVar, String str) {
        il ilVar;
        switch (aVar) {
            case eLinearHorizontal:
                ilVar = new is(getContext());
                break;
            case eLinearVertical:
                ilVar = new it(getContext());
                break;
            case eScrollHorizontal:
                ilVar = new ix(getContext());
                break;
            case eScrollVertical:
                ilVar = new iy(getContext());
                break;
            default:
                ilVar = null;
                break;
        }
        if (str != null && str.length() > 0) {
            ilVar.setTag(str);
        }
        this.f1417a.addView(ilVar);
        return ilVar;
    }

    public View a(b bVar, String str, String str2, String str3) {
        il ilVar;
        switch (bVar) {
            case eButton:
                ilVar = new im(getContext());
                break;
            case eEditText:
                ilVar = new ip(getContext());
                break;
            case eTextView:
                ilVar = new jc(getContext());
                break;
            case eImageView:
                ilVar = new ir(getContext());
                ((ir) ilVar).setResourceName(str3);
                break;
            case eListView:
                ilVar = new iu(getContext());
                break;
            case eSpinner:
                ilVar = new ja(getContext());
                break;
            case eCheckBox:
                ilVar = new io(getContext());
                break;
            case eWebView:
                ilVar = new jd(getContext());
                break;
            case eSwitch:
                ilVar = new jb(getContext());
                break;
            case eSeekBar:
                ilVar = new iz(getContext());
                break;
            case eCalendarView:
                ilVar = new in(getContext());
                break;
            case eAdView:
                ilVar = new ik(getContext());
                break;
            case eProgressBar:
                ilVar = new iw(getContext());
                break;
            case eMapView:
                ilVar = new iv(getContext());
                break;
            default:
                ilVar = null;
                break;
        }
        if (str != null && str.length() > 0) {
            ilVar.setTag(str);
        }
        ilVar.setText(str2);
        ilVar.setName(str3);
        this.b.addView(ilVar);
        return ilVar;
    }

    public void setLayoutVisible(int i) {
        this.f1417a.setVisibility(i);
        this.c.setVisibility(i);
        this.d.setVisibility(i);
    }

    public void setWidgetVisible(int i) {
        this.b.setVisibility(i);
        this.e.setVisibility(i);
    }
}
