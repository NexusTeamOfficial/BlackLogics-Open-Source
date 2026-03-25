package com.nexusteam.internal.lib.base;

import com.nexusteam.internal.jq;
import com.nexusteam.internal.jz;
import com.nexusteam.internal.mc;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.analytics.Tracker;
import java.util.ArrayList;
import java.util.Iterator;

public class BaseActivity extends AppCompatActivity {

    /* renamed from: a  reason: collision with root package name */
    protected Tracker f1568a;
    @Deprecated
    protected Context b;
    protected jz c;
    private ArrayList<jq> d;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.d = new ArrayList<>();
        this.b = getApplicationContext();
        this.c = new jz(this);



        mc.a(getApplicationContext(), false);
    }

    public void a() {
        if (this.c != null && !this.c.isShowing()) {
            this.c.show();
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    BaseActivity.this.c.d();
                }
            }, 5000);
        }
    }

    public void b() {
        if (this.c != null && this.c.isShowing()) {
            this.c.dismiss();
        }
    }

    public void a(jq jqVar) {
        this.d.add(jqVar);
    }

    public void onResume() {
        super.onResume();
        if (this.c != null && this.c.isShowing()) {
            this.c.b();
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        if (this.c != null && this.c.isShowing()) {
            this.c.a();
        }
        super.onPause();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        if (this.c != null && this.c.isShowing()) {
            this.c.c();
        }
        Iterator<jq> it = this.d.iterator();
        while (it.hasNext()) {
            jq next = it.next();
            if (next.getStatus() != AsyncTask.Status.FINISHED) {
                next.cancel(false);
            }
        }
        super.onDestroy();
    }
}
