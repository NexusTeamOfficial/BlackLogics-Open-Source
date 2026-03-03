package com.nexusteam.internal.lib.base;
import com.nexusteam.blacklogics.R;

import com.nexusteam.internal.dt;
import com.nexusteam.internal.jp;
import com.nexusteam.internal.jq;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.analytics.Tracker;

public class BaseFragment extends Fragment {
    protected Activity m;
    /* access modifiers changed from: protected */
    @Deprecated
    public Context n;
    protected Tracker o;
    /* access modifiers changed from: protected */
    public jp p;
    /* access modifiers changed from: protected */
    public dt q;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.m = getActivity();
        this.n = this.m.getApplicationContext();
        this.p = new jp(this.n);
        this.q = new dt(this.n);



    }

    /* access modifiers changed from: protected */
    public void h() {
        FragmentActivity activity = getActivity();
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).a();
        } else if (activity instanceof BaseAppCompatActivity) {
            ((BaseAppCompatActivity) activity).m();
        }
    }

    /* access modifiers changed from: protected */
    public void i() {
        FragmentActivity activity = getActivity();
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).b();
        } else if (activity instanceof BaseAppCompatActivity) {
            ((BaseAppCompatActivity) activity).o();
        }
    }

    public void a(jq jqVar) {
        FragmentActivity activity = getActivity();
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).a(jqVar);
        } else if (activity instanceof BaseAppCompatActivity) {
            ((BaseAppCompatActivity) activity).a(jqVar);
        }
    }

    public void onDetach() {
        super.onDetach();
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
