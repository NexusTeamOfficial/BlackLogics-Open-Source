package com.nexusteam.internal.lib.base;

import com.nexusteam.internal.eo;
import com.nexusteam.internal.kd;
import com.nexusteam.internal.ki;
import com.nexusteam.internal.kq;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.view.View;
import com.nexusteam.blacklogics.R;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.content.Intent;
import android.net.Uri;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import android.os.Bundle;

public abstract class BasePermissionFragment extends BaseFragment {
    
    private ActivityResultLauncher<Intent> manageStorageLauncher;
    private int currentRequestCode = -1;

    public abstract void a();
    public abstract void a(int i);
    public abstract void b();
    public abstract void b(int i);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerManageStorageLauncher();
    }

    private void registerManageStorageLauncher() {
        manageStorageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new androidx.activity.result.ActivityResultCallback<androidx.activity.result.ActivityResult>() {
                @Override
                public void onActivityResult(androidx.activity.result.ActivityResult result) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        if (Environment.isExternalStorageManager()) {
                            if (currentRequestCode != -1) {
                                b(currentRequestCode);
                                currentRequestCode = -1;
                            }
                        } else {
                            a();
                            currentRequestCode = -1;
                        }
                    }
                }
            }
        );
    }

    public boolean d() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            return ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED 
                    && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
    }

    public boolean c(int i) {
        boolean zD = d();
        if (!zD) {
            d(i);
        }
        return zD;
    }

    protected void d(final int i) {
        if (eo.f120a) {
            return;
        }
        final kd kdVar = new kd(this.m);
        kdVar.a(kq.a().a(getContext(), R.string.common_message_permission_title_storage));
        kdVar.a(R.drawable.break_warning_96_red);
        kdVar.b(kq.a().a(getContext(), R.string.common_message_permission_storage));
        kdVar.a(kq.a().a(getContext(), R.string.common_word_ok), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ki.a()) {
                    return;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    requestManageStoragePermission(i);
                } else {
                    BasePermissionFragment.this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, i);
                }
                kdVar.dismiss();
            }
        });
        kdVar.b(kq.a().a(getContext(), R.string.common_word_cancel), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BasePermissionFragment.this.b();
                kdVar.dismiss();
            }
        });
        kdVar.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                eo.f120a = false;
            }
        });
        kdVar.setCancelable(false);
        kdVar.setCanceledOnTouchOutside(false);
        kdVar.show();
        eo.f120a = true;
    }

    private void requestManageStoragePermission(int requestCode) {
        currentRequestCode = requestCode;
        try {
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setData(Uri.parse("package:" + getContext().getPackageName()));
            manageStorageLauncher.launch(intent);
        } catch (Exception e) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
            manageStorageLauncher.launch(intent);
        }
    }

    protected void e(final int i) {
        if (eo.f120a) {
            return;
        }
        final kd kdVar = new kd(this.m);
        kdVar.a(kq.a().a(getContext(), R.string.common_message_permission_title_storage));
        kdVar.a(R.drawable.break_warning_96_red);
        kdVar.b(kq.a().a(getContext(), R.string.common_message_permission_storage1));
        kdVar.a(kq.a().a(getContext(), R.string.common_word_settings), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ki.a()) {
                    return;
                }
                BasePermissionFragment.this.a(i);
                kdVar.dismiss();
            }
        });
        kdVar.b(kq.a().a(getContext(), R.string.common_word_cancel), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BasePermissionFragment.this.a();
                kdVar.dismiss();
            }
        });
        kdVar.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                eo.f120a = false;
            }
        });
        kdVar.setCancelable(false);
        kdVar.setCanceledOnTouchOutside(false);
        kdVar.show();
        eo.f120a = true;
    }

    @Override
    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        for (String str : strArr) {
            if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(str)) {
                if (iArr.length > 0 && iArr[0] == PackageManager.PERMISSION_GRANTED && iArr[1] == PackageManager.PERMISSION_GRANTED) {
                    b(i);
                } else {
                    e(i);
                    return;
                }
            }
        }
    }
}