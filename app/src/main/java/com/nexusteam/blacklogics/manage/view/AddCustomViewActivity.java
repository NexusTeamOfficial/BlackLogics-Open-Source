package com.nexusteam.blacklogics.manage.view;

import com.nexusteam.internal.ez;
import com.nexusteam.internal.fc;
import com.nexusteam.internal.ke;
import com.nexusteam.internal.kq;
import com.nexusteam.internal.ActivityNameValidator;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.nexusteam.blacklogics.R;
import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.internal.beans.ViewBean;
import com.nexusteam.internal.lib.base.BaseDialogActivity;
import java.util.ArrayList;

public class AddCustomViewActivity extends BaseDialogActivity implements View.OnClickListener {

    private EditText f1351a;
    private TextView b;
    private ArrayList<String> c;
    private ActivityNameValidator d;
    private String e;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.manage_screen_custom_view_add);
        a(kq.a().a(getApplicationContext(), R.string.design_manager_view_title_add_custom_view));
        a(R.drawable.new_window_96);
        b(kq.a().a(getApplicationContext(), R.string.common_word_add));
        c(kq.a().a(getApplicationContext(), R.string.common_word_cancel));
        this.c = getIntent().getStringArrayListExtra("screen_names");
        this.f1351a = (EditText) findViewById(R.id.ed_input);
        ((TextInputLayout) findViewById(R.id.ti_input)).setHint(kq.a().a(getApplicationContext(), R.string.design_manager_view_hint_enter_view_name));
        this.b = (TextView) findViewById(R.id.tv_desc);
        this.b.setText(kq.a().a(getApplicationContext(), R.string.design_manager_view_description_guide_use_custom_view));
        this.d = new ActivityNameValidator(this, (TextInputLayout) findViewById(R.id.ti_input), fc.b, this.c);
        this.f1351a.setPrivateImeOptions("defaultInputmode=english;");
        this.f1351a.setImeOptions(5);
        this.f1351a.setLines(1);
        View okBtn = findViewById(R.id.common_dialog_ok_button);
        View cancelBtn = findViewById(R.id.common_dialog_cancel_button);
        if (cancelBtn == null) cancelBtn = findViewById(R.id.common_dialog_default_button);
        if (okBtn != null) okBtn.setOnClickListener(this);
        if (cancelBtn != null) cancelBtn.setOnClickListener(this);
    }

    protected boolean a(ActivityNameValidator lqVar) {
        return lqVar.a();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.common_dialog_cancel_button) {
            finish();
            return;
        }
        if (id != R.id.common_dialog_default_button) {
            if (id == R.id.common_dialog_ok_button && a(this.d)) {
                Intent intent = new Intent();
                intent.putExtra("project_file", new ProjectFileBean(1, this.f1351a.getText().toString()));
                if (this.e != null) {
                    intent.putExtra("preset_views", e(this.e));
                }
                setResult(-1, intent);
                ke.a(getApplicationContext(), kq.a().a(getApplicationContext(), R.string.design_manager_message_add_complete), 0).show();
                finish();
                return;
            }
            return;
        }
        Intent intent2 = new Intent(getApplicationContext(), (Class<?>) PresetSettingActivity.class);
        intent2.putExtra("request_code", 277);
        startActivityForResult(intent2, 277);
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 277 && i2 == -1) {
            this.e = ((ProjectFileBean) intent.getParcelableExtra("preset_data")).presetName;
        }
    }

    private ArrayList<ViewBean> e(String str) {
        return ez.b(str);
    }
}
