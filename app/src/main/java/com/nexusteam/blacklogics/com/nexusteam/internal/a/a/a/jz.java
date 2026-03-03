package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;

public class jz extends Dialog {

    /* renamed from: a  reason: collision with root package name */
    private TextView f318a;
    private LottieAnimationView b;

    public jz(Context context) {
        super(context, R.style.progress);
        setContentView(R.layout.progress);
        f318a = ((TextView) findViewById(R.id.tv_progress));
        b = ((LottieAnimationView) findViewById(R.id.anim_sketchware));
        this.f318a.setText(kq.a().a(context, R.string.common_message_loading));
        super.setCancelable(false);
    }

    public void a() {
    /*    if ((this.b != null) && this.b.b()) {
            this.b.f();
        }*/
    }

    public void b() {
      /*  if ((this.b != null) && (true ^ this.b.b())) {
            this.b.d();
        }*/
    }

    public void c() {
     /*   if ((this.b != null) && this.b.b()) {
            this.b.e();
        }*/
    }

    public void show() {
        super.show();
    }

    public void d() {
        super.setCancelable(true);
    }
}
