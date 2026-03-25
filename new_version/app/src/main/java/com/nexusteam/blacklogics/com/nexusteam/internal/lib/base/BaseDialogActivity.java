package com.nexusteam.internal.lib.base;
import com.nexusteam.blacklogics.R;

import com.nexusteam.internal.ki;
import com.nexusteam.internal.kp;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BaseDialogActivity extends BaseAppCompatActivity {
    protected TextView P;
    protected TextView Q;
    protected TextView ABC;

    /* renamed from: a  reason: collision with root package name */
    private LinearLayout f1572a;
    private ViewGroup b;
    private LinearLayout c;
    private LinearLayout d;
    private TextView e;
    private ImageView f;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        super.setContentView(com.nexusteam.blacklogics.R.layout.common_dialog_layout);
        overridePendingTransition(com.nexusteam.blacklogics.R.anim.ani_fade_in, R.anim.ani_fade_out);
        this.f = (ImageView) findViewById(com.nexusteam.blacklogics.R.id.common_dialog_icon);
        this.f1572a = (LinearLayout) findViewById(com.nexusteam.blacklogics.R.id.common_dialog_container);
        this.b = (ViewGroup) findViewById(com.nexusteam.blacklogics.R.id.common_dialog_content);
        this.c = (LinearLayout) findViewById(com.nexusteam.blacklogics.R.id.common_dialog_button_layout);
        this.e = (TextView) findViewById(com.nexusteam.blacklogics.R.id.common_dialog_tv_title);
        this.d = (LinearLayout) findViewById(com.nexusteam.blacklogics.R.id.common_dialog_title_layout);
        this.P = (TextView) findViewById(com.nexusteam.blacklogics.R.id.common_dialog_default_button);
        this.Q = (TextView) findViewById(com.nexusteam.blacklogics.R.id.common_dialog_ok_button);
        this.ABC = (TextView) findViewById(com.nexusteam.blacklogics.R.id.common_dialog_cancel_button);
        this.f1572a.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!ki.a()) {
                    BaseDialogActivity.this.finish();
                }
            }
        });
        this.f.setVisibility(8);
    }

    public void setContentView(int i) {
        kp.a(this, this.b, i);
    }

    public void finish() {
        super.finish();
        overridePendingTransition(com.nexusteam.blacklogics.R.anim.ani_fade_in, com.nexusteam.blacklogics.R.anim.ani_fade_out);
    }

    /* access modifiers changed from: protected */
    public void a() {
        this.c.setVisibility(8);
    }

    /* access modifiers changed from: protected */
    public void a(String str) {
        this.e.setText(str);
    }

    /* access modifiers changed from: protected */
    public void b() {
        this.d.setVisibility(8);
    }

    /* access modifiers changed from: protected */
    public void b(String str) {
        this.Q.setText(str.toUpperCase());
    }

    /* access modifiers changed from: protected */
    public void c(String str) {
        this.ABC.setText(str.toUpperCase());
    }

    /* access modifiers changed from: protected */
    public void a(int i) {
        this.f.setImageResource(i);
        this.f.setVisibility(0);
    }
}
