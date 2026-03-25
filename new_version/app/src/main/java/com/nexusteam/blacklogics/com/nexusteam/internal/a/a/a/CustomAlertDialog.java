package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nexusteam.blacklogics.R;


public class CustomAlertDialog extends Dialog {

    /* renamed from: a  reason: collision with root package name */
    private Activity f325a;
    private LinearLayout b;
    private View c;
    private ImageView d;
    private TextView e;
    private TextView f;
    private FrameLayout g;
    private TextView h;
    private TextView i;
    private String j = "";
    private String k = "";
    private String l = "Yes";
    private String m = "No";
    private int n = -1;
    private int o = 0;
    private View.OnClickListener p = null;
    private View.OnClickListener q = null;
    private View r;

    public CustomAlertDialog(Activity activity) {
        super(activity);
        this.f325a = activity;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        if (this.o == 0) {
            getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog_inset_white);
        } else if (this.o == 1) {
            getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog_inset_light_grey);
        } else if (this.o == 2) {
            getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog_inset_black);
        }
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = -1;
        getWindow().setAttributes(attributes);
        setContentView(R.layout.dialog);
        this.b = (LinearLayout) findViewById(R.id.sdialog_root);
        this.d = (ImageView) findViewById(R.id.dialog_img);
        this.e = (TextView) findViewById(R.id.dialog_title);
        this.f = (TextView) findViewById(R.id.dialog_msg);
        this.g = (FrameLayout) findViewById(R.id.custom_view);
        this.r = findViewById(R.id.layout_button);
        this.h = (TextView) findViewById(R.id.dialog_btn_yes);
        this.h.setText(this.l);
        this.h.setOnClickListener(this.p);
        this.i = (TextView) findViewById(R.id.dialog_btn_no);
        this.i.setText(this.m);
        this.i.setOnClickListener(this.q);
        if (this.j.isEmpty()) {
            this.e.setVisibility(8);
        } else {
            this.e.setVisibility(0);
            this.e.setText(this.j);
        }
        if (this.k.isEmpty()) {
            this.f.setVisibility(8);
        } else {
            this.f.setVisibility(0);
            this.f.setText(this.k);
        }
        if (this.q == null) {
            this.i.setVisibility(8);
        }
        if (this.p == null) {
            this.h.setVisibility(8);
        }
        if (this.n == -1) {
            this.d.setVisibility(8);
        } else {
            this.d.setImageResource(this.n);
        }
        if (this.c != null) {
            this.g.setVisibility(0);
            this.g.addView(this.c);
            return;
        }
        this.g.setVisibility(8);
    }

    public void a(String str) {
        this.j = str;
    }

    public void b(String str) {
        this.k = str;
    }

    public void a(int i2) {
        this.n = i2;
    }

    public void a(View view) {
        this.c = view;
    }

    public void a(String str, View.OnClickListener onClickListener) {
        this.l = str;
        this.p = onClickListener;
    }

    public void b(String str, View.OnClickListener onClickListener) {
        this.m = str;
        this.q = onClickListener;
    }

    public void show() {
        super.show();
        if (this.p == null && this.q == null && this.r != null) {
            this.r.setVisibility(8);
        }
    }
}
