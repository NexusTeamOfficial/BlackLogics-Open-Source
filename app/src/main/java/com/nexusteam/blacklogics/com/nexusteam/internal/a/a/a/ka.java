package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.app.Dialog;
import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ka extends Dialog {

    /* renamed from: a  reason: collision with root package name */
    Context f322a;
    TextView b;
    ProgressBar c = ((ProgressBar) findViewById(R.id.prog_box));
    boolean d;

    public ka(Context context) {
        super(context);
        this.f322a = context;
        getWindow().requestFeature(1);
        setContentView(R.layout.progress_msg_box);
        setTitle(kq.a().a(context, R.string.common_message_progress));
        this.b = (TextView) findViewById(R.id.tv_progress);
        this.b.setText(kq.a().a(context, R.string.common_message_loading));
        super.setCanceledOnTouchOutside(false);
        super.setCancelable(true);
    }

    public void a(String str) {
        this.b.setText(str);
    }

    public void a(boolean z) {
        this.d = z;
    }

    public boolean a() {
        return this.d;
    }

    public void onBackPressed() {
        if (!this.d) {
            super.onBackPressed();
        }
    }
}
