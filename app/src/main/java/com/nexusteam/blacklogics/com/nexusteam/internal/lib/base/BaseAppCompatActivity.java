package com.nexusteam.internal.lib.base;
import com.nexusteam.blacklogics.R;

import android.os.Build;
import com.nexusteam.internal.dt;
import com.nexusteam.internal.jp;
import com.nexusteam.internal.jq;
import com.nexusteam.internal.jz;
import com.nexusteam.internal.ka;
import com.nexusteam.internal.mc;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Environment;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.analytics.Tracker;
import java.util.ArrayList;
import java.util.Iterator;

public class BaseAppCompatActivity extends AppCompatActivity {
	protected Tracker J;
	/* access modifiers changed from: protected */
	@Deprecated
	public Context K;
	protected jz L;
	/* access modifiers changed from: protected */
	public ka M;
	/* access modifiers changed from: protected */
	public jp N;
	/* access modifiers changed from: protected */
	public dt O;
	
	/* renamed from: a  reason: collision with root package name */
	private ArrayList<jq> f1571a;
	
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		this.K = getApplicationContext();
		this.N = new jp(getApplicationContext());
		this.O = new dt(getApplicationContext());
		
		
		
		this.f1571a = new ArrayList<>();
		this.L = new jz(this);
		mc.a(getApplicationContext(), false);
		this.M = new ka(this);
	}
	
	public void a(jq jqVar) {
		this.f1571a.add(jqVar);
	}
	
	/* access modifiers changed from: protected */
	public void m() {
		if (this.L != null && !this.L.isShowing() && !isFinishing()) {
			this.L.show();
		}
	}
	public boolean n() {
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
			return Environment.isExternalStorageManager();
		} else {
			return ContextCompat.checkSelfPermission(this,
			android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
			== PackageManager.PERMISSION_GRANTED;
		}
	}
	
	/* access modifiers changed from: protected */
	public void a(DialogInterface.OnCancelListener onCancelListener) {
		if (this.M != null && !this.M.isShowing()) {
			this.M.setOnCancelListener(onCancelListener);
			this.M.show();
		}
	}
	
	/* access modifiers changed from: protected */
	public void d(String str) {
		if (this.M != null && this.M.isShowing()) {
			this.M.a(str);
		}
	}
	
	/* access modifiers changed from: protected */
	public void o() {
		try {
			if (this.L != null && this.L.isShowing()) {
				this.L.dismiss();
			}
		} catch (Exception unused) {
			this.L = null;
			this.L = new jz(this);
		}
	}
	
	/* access modifiers changed from: protected */
	public void p() {
		try {
			if (this.M != null && this.M.isShowing()) {
				this.M.dismiss();
			}
		} catch (Exception unused) {
			this.M = null;
			this.M = new ka(this);
		}
	}
	
	public void onResume() {
		super.onResume();
		if (this.L != null && this.L.isShowing()) {
			this.L.b();
		}
	}
	
	public void onPause() {
		if (this.L != null && this.L.isShowing()) {
			this.L.a();
		}
		super.onPause();
	}
	
	public void onDestroy() {
		q();
		if (this.L != null && this.L.isShowing()) {
			this.L.c();
		}
		super.onDestroy();
	}
	
	/* access modifiers changed from: protected */
	public void q() {
		Iterator<jq> it = this.f1571a.iterator();
		while (it.hasNext()) {
			jq next = it.next();
			if (next.getStatus() != AsyncTask.Status.FINISHED && !next.isCancelled()) {
				next.cancel(true);
			}
		}
		this.f1571a.clear();
	}
}
