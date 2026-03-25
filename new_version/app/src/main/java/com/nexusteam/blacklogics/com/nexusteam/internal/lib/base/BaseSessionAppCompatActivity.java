package com.nexusteam.internal.lib.base;
import com.nexusteam.blacklogics.R;

import com.nexusteam.internal.eo;
import com.nexusteam.internal.kd;
import com.nexusteam.internal.ke;
import com.nexusteam.internal.ki;
import com.nexusteam.internal.kq;
import com.nexusteam.internal.ky;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.view.View;



public abstract class BaseSessionAppCompatActivity extends BaseAppCompatActivity {

    /* renamed from: a  reason: collision with root package name */
    private int f1586a = 0;
    private final int b = 9001;
    private final int c = 9002;

    public abstract void a();

    public abstract void a(int i);

    public abstract void a(int i, String str);

    public abstract void b();

    public abstract void b(int i);

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public boolean n() {
        return ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.READ_EXTERNAL_STORAGE") == 0;
    }

    public boolean d(int i) {
        boolean n = n();
        if (!n) {
            e(i);
        }
        return n;
    }

    /* access modifiers changed from: protected */
    public void e(final int i) {
        if (!eo.f120a) {
            final kd kdVar = new kd(this);
            kdVar.a(kq.a().a(getApplicationContext(), R.string.common_message_permission_title_storage));
            kdVar.a(R.drawable.break_warning_96_red);
            kdVar.b(kq.a().a(getApplicationContext(), R.string.common_message_permission_storage));
            kdVar.a(kq.a().a(getApplicationContext(), R.string.common_word_ok), new View.OnClickListener() {
                public void onClick(View view) {
                    if (!ki.a()) {
                        ActivityCompat.requestPermissions(BaseSessionAppCompatActivity.this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, i);
                        kdVar.dismiss();
                    }
                }
            });
            kdVar.b(kq.a().a(getApplicationContext(), R.string.common_word_cancel), new View.OnClickListener() {
                public void onClick(View view) {
                    BaseSessionAppCompatActivity.this.a();
                    kdVar.dismiss();
                }
            });
            kdVar.setOnDismissListener(new DialogInterface.OnDismissListener() {
                public void onDismiss(DialogInterface dialogInterface) {
                    eo.f120a = false;
                }
            });
            kdVar.setCancelable(false);
            kdVar.setCanceledOnTouchOutside(false);
            kdVar.show();
            eo.f120a = true;
        }
    }

    /* access modifiers changed from: protected */
    public void f(final int i) {
        if (!eo.f120a) {
            final kd kdVar = new kd(this);
            kdVar.a(kq.a().a(getApplicationContext(), R.string.common_message_permission_title_storage));
            kdVar.a(R.drawable.break_warning_96_red);
            kdVar.b(kq.a().a(getApplicationContext(), R.string.common_message_permission_storage1));
            kdVar.a(kq.a().a(getApplicationContext(), R.string.common_word_settings), new View.OnClickListener() {
                public void onClick(View view) {
                    if (!ki.a()) {
                        BaseSessionAppCompatActivity.this.a(i);
                        kdVar.dismiss();
                    }
                }
            });
            kdVar.b(kq.a().a(getApplicationContext(), R.string.common_word_cancel), new View.OnClickListener() {
                public void onClick(View view) {
                    BaseSessionAppCompatActivity.this.b();
                    kdVar.dismiss();
                }
            });
            kdVar.setOnDismissListener(new DialogInterface.OnDismissListener() {
                public void onDismiss(DialogInterface dialogInterface) {
                    eo.f120a = false;
                }
            });
            kdVar.setCancelable(false);
            kdVar.setCanceledOnTouchOutside(false);
            kdVar.show();
            eo.f120a = true;
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        for (String equals : strArr) {
            if ("android.permission.WRITE_EXTERNAL_STORAGE".equals(equals)) {
                if (iArr.length > 0 && iArr[0] == 0 && iArr[1] == 0) {
                    b(i);
                } else {
                    f(i);
                    return;
                }
            }
        }
    }

    public void g(int i) {
        if (!ky.d(getApplicationContext())) {
            ke.a(getBaseContext(), (CharSequence) kq.a().a(getApplicationContext(), R.string.common_message_check_network), 0).show();
            return;
        }
        this.f1586a = i;
        if (!this.N.b()) {
            c(9001);
        } else if (this.N.g().isEmpty()) {
            h(9002);
        } else {
            a(this.f1586a, this.N.o());
        }
    }

    private void c(int i) {
      /*  ke.a(getBaseContext(), (CharSequence) kq.a().a(getApplicationContext(), R.string.common_message_need_login), 0).show();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.setFlags(536870912);
        startActivityForResult(intent, i);*/
    }

    private void h(int i) {
       /* ke.a(getBaseContext(), (CharSequence) kq.a().a(getApplicationContext(), R.string.common_message_need_username), 0).show();
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        intent.setFlags(536870912);
        startActivityForResult(intent, i);*/
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        switch (i) {
            case 9001:
                if (i2 != -1) {
                    return;
                }
                if (this.N.g().isEmpty()) {
                    h(9002);
                    return;
                } else {
                    a(this.f1586a, this.N.o());
                    return;
                }
            case 9002:
                if (i2 == -1) {
                    a(this.f1586a, this.N.o());
                    return;
                }
                return;
            default:
                return;
        }
    }
}
