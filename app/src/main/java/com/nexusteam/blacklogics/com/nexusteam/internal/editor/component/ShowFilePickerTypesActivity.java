package com.nexusteam.internal.editor.component;

import com.nexusteam.internal.StringResourceManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.nexusteam.blacklogics.R;
import com.nexusteam.internal.lib.base.BaseDialogActivity;


public class ShowFilePickerTypesActivity extends BaseDialogActivity implements View.OnClickListener {

    /* renamed from: a  reason: collision with root package name */
    RadioGroup f1104a;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        a(StringResourceManager.a().a((Context) this, (int) R.string.component_file_picker_title_select_mime_type));
        b(StringResourceManager.a().a(getApplicationContext(), (int) R.string.common_word_select));
        c(StringResourceManager.a().a(getApplicationContext(), (int) R.string.common_word_cancel));
        setContentView(R.layout.show_file_picker_types);
        this.f1104a = (RadioGroup) findViewById(R.id.radio_group);
        ((RadioButton) findViewById(R.id.radio_all)).setText(StringResourceManager.a().a((Context) this, (int) R.string.component_file_picker_title_select_mime_type_all_files));
        ((RadioButton) findViewById(R.id.radio_image)).setText(StringResourceManager.a().a((Context) this, (int) R.string.component_file_picker_title_select_mime_type_image_files));
        ((RadioButton) findViewById(R.id.radio_audio)).setText(StringResourceManager.a().a((Context) this, (int) R.string.component_file_picker_title_select_mime_type_audio_files));
        ((RadioButton) findViewById(R.id.radio_text)).setText(StringResourceManager.a().a((Context) this, (int) R.string.component_file_picker_title_select_mime_type_text_files));
        this.Q.setOnClickListener(this);
        this.ABC.setOnClickListener(this);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();


    }

    public void onClick(View view) {
        String str;
        int id = view.getId();
        if (id == R.id.common_dialog_cancel_button) {
            finish();
        } else if (id == R.id.common_dialog_ok_button) {
            if (this.f1104a.getCheckedRadioButtonId() == R.id.radio_all) {
                str = "*/*";
            } else if (this.f1104a.getCheckedRadioButtonId() == R.id.radio_image) {
                str = "image/*";
            } else {
                str = this.f1104a.getCheckedRadioButtonId() == R.id.radio_audio ? "audio/*" : "text/*";
            }
            Intent intent = new Intent();
            intent.putExtra("mime_type", str);
            setResult(-1, intent);
            finish();
        }
    }
}
