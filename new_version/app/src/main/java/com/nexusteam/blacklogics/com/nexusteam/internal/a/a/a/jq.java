package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import java.net.ConnectException;
import java.net.UnknownHostException;

public abstract class jq extends AsyncTask<Void, String, String> {
    protected Context h;
    protected Void[] i;

    public abstract void a();

    public abstract void a(String str);

    public abstract void b();

    public jq(Context context) {
        this.h = context;
    }

    public void onPreExecute() {
        super.onPreExecute();
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public String doInBackground(Void... voidArr) {
        try {
            this.i = voidArr;
            if (isCancelled()) {
                return "";
            }
            a();
            return "";
        } catch (Exception e2) {
            Log.e(getClass().getSimpleName(), e2.getMessage(), e2);
            return kq.a().a(this.h, R.string.common_error_an_error_occurred) + "[" + e2.getMessage() + "]";
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: c */
    public void onPostExecute(String str) {
        super.onPostExecute(str);
        if (str.isEmpty()) {
            b();
            return;
        }
        a(str);
        ke.b(this.h, (CharSequence) str, 1).show();
    }
}
