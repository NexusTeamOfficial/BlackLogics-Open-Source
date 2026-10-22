package com.nexusteam.blacklogics.manage.view;

import com.nexusteam.internal.ez;
import com.nexusteam.internal.kd;
import com.nexusteam.internal.kq;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.nexusteam.blacklogics.R;
import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.internal.lib.base.BaseDialogActivity;
import java.util.ArrayList;

public class PresetSettingActivity extends BaseDialogActivity implements View.OnClickListener {

    ImageView f1379a;
    ImageView b;
    ImageView c;
    TextView d;
    int e;
    ArrayList<ProjectFileBean> g;
    int f = 0;
    boolean h = false;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.manage_screen_activity_add_view_preset_setting);
        a(kq.a().a(getApplicationContext(), R.string.preset_setting_title));
        this.e = getIntent().getIntExtra("request_code", -1);
        if (getIntent().hasExtra("edit_mode")) {
            this.h = true;
        }
        this.f1379a = (ImageView) findViewById(R.id.btn_left);
        this.f1379a.setOnClickListener(this);
        this.b = (ImageView) findViewById(R.id.btn_right);
        this.b.setOnClickListener(this);
        this.c = (ImageView) findViewById(R.id.img_activity);
        this.d = (TextView) findViewById(R.id.tv_activity_name);
        if (this.e == 276) {
            this.g = ez.a();
        } else if (this.e == 277) {
            this.g = ez.b();
        } else {
            this.g = ez.c();
        }
        b(kq.a().a(getApplicationContext(), R.string.common_word_import));
        c(kq.a().a(getApplicationContext(), R.string.common_word_cancel));
        View okBtn = findViewById(R.id.common_dialog_ok_button);
        View cancelBtn = findViewById(R.id.common_dialog_cancel_button);
        if (okBtn != null) okBtn.setOnClickListener(this);
        if (cancelBtn != null) cancelBtn.setOnClickListener(this);
        e(this.g.get(this.f).presetName);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_left) {
            if (this.f == 0) {
                this.f = this.g.size() - 1;
            } else {
                this.f--;
            }
            e(this.g.get(this.f).presetName);
            return;
        }
        if (id == R.id.btn_right) {
            if (this.f == this.g.size() - 1) {
                this.f = 0;
            } else {
                this.f++;
            }
            e(this.g.get(this.f).presetName);
            return;
        }
        if (id == R.id.common_dialog_cancel_button) {
            setResult(0);
            finish();
        } else {
            if (id != R.id.common_dialog_ok_button) {
                return;
            }
            if (!this.h) {
                d();
            } else {
                c();
            }
        }
    }

    private void c() {
        final kd kdVar = new kd(this);
        kdVar.a(kq.a().a(getApplicationContext(), R.string.preset_setting_title));
        kdVar.a(R.drawable.ic_detail_setting_48dp);
        kdVar.setCancelable(false);
        kdVar.b(kq.a().a(getApplicationContext(), R.string.preset_setting_edit_warning));
        kdVar.a(kq.a().a(getApplicationContext(), R.string.common_word_ok), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PresetSettingActivity.this.d();
            }
        });
        kdVar.b(kq.a().a(getApplicationContext(), R.string.common_word_cancel), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kdVar.dismiss();
            }
        });
        kdVar.show();
    }

    public void d() {
        Intent intent = new Intent();
        intent.putExtra("preset_data", this.g.get(this.f));
        setResult(-1, intent);
        finish();
    }

    private void e(String str) {
        int iD;
        switch (this.e) {
            case 276:
                iD = ez.d(str);
                break;
            case 277:
                iD = ez.e(str);
                break;
            case 278:
                iD = ez.f(str);
                break;
            default:
                iD = -1;
                break;
        }
        this.c.setImageResource(iD);
        this.d.setText(str);
    }
}
