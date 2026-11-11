package com.nexusteam.blacklogics.editor.manage.image_manager;

import com.nexusteam.internal.fc;
import com.nexusteam.internal.fe;
import com.nexusteam.internal.jq;
import com.nexusteam.internal.ke;
import com.nexusteam.internal.kh;
import com.nexusteam.internal.ki;
import com.nexusteam.internal.kq;
import com.nexusteam.internal.kz;
import com.nexusteam.internal.lh;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.dx.io.Opcodes;
import com.nexusteam.blacklogics.R;
import com.nexusteam.internal.beans.ProjectResourceBean;
import com.nexusteam.internal.lib.base.BaseDialogActivity;
import com.nexusteam.internal.lib.ui.EasyDeleteEditText;
import com.google.android.gms.analytics.HitBuilders;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class AddImageCollectionActivity extends BaseDialogActivity implements View.OnClickListener {
    private int A;
    TextView d;
    TextView e;
    ImageView f;
    ImageView g;
    ImageView h;
    ImageView i;
    EditText j;
    EasyDeleteEditText k;
    TextView l;
    CheckBox m;
    private String o;
    private ArrayList<ProjectResourceBean> p;
    private lh w;
    private int z;
    private boolean n = false;

    LinearLayout f1198a = null;
    LinearLayout b = null;
    TextView c = null;
    private boolean q = false;
    private String r = null;
    private int s = 0;
    private int t = 0;
    private int u = 1;
    private int v = 1;
    private boolean x = false;
    private ProjectResourceBean y = null;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        a(kq.a().a(this, R.string.design_manager_image_title_add_image));
        b(kq.a().a(getApplicationContext(), R.string.common_word_save));
        setContentView(R.layout.manage_image_add);
        Intent intent = getIntent();
        this.p = intent.getParcelableArrayListExtra("images");
        this.o = intent.getStringExtra("sc_id");
        this.y = (ProjectResourceBean) intent.getParcelableExtra("edit_target");
        if (this.y != null) {
            this.x = true;
        }
        this.f1198a = (LinearLayout) findViewById(R.id.layout_img_inform);
        this.b = (LinearLayout) findViewById(R.id.layout_img_modify);
        this.m = (CheckBox) findViewById(R.id.chk_collection);
        this.m.setVisibility(8);
        this.l = (TextView) findViewById(R.id.tv_desc);
        this.c = (TextView) findViewById(R.id.tv_imgcnt);
        this.d = (TextView) findViewById(R.id.tv_collection);
        this.d.setVisibility(8);
        this.e = (TextView) findViewById(R.id.tv_add_photo);
        this.i = (ImageView) findViewById(R.id.img_selected);
        this.f = (ImageView) findViewById(R.id.img_rotate);
        this.g = (ImageView) findViewById(R.id.img_vertical);
        this.h = (ImageView) findViewById(R.id.img_horizontal);
        this.k = (EasyDeleteEditText) findViewById(R.id.ed_input);
        this.j = this.k.getEditText();
        this.j.setPrivateImeOptions("defaultInputmode=english;");
        this.k.setHint(kq.a().a(this, R.string.design_manager_image_hint_enter_image_name));
        this.w = new lh(this, this.k.getTextInputLayout(), fc.b, e());
        this.w.a(1);
        this.e.setText(kq.a().a(this, R.string.design_manager_image_title_add_image));
        this.i.setOnClickListener(this);
        this.f.setOnClickListener(this);
        this.g.setOnClickListener(this);
        this.h.setOnClickListener(this);
        this.Q.setOnClickListener(this);
        this.ABC.setOnClickListener(this);
        this.q = false;
        this.s = 0;
        this.u = 1;
        this.v = 1;
    }

    @Override
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        if (this.x) {
            this.y.isEdited = true;
            a(kq.a().a(this, R.string.design_manager_image_title_edit_image_name));
            this.w = new lh(this, this.k.getTextInputLayout(), fc.b, e(), this.y.resName);
            this.w.a(1);
            this.j.setText(this.y.resName);
            this.m.setVisibility(8);
            this.d.setVisibility(8);
            this.e.setVisibility(8);
            e(a(this.y));
            this.b.setVisibility(8);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        if (ki.a()) {
        }
        switch (view.getId()) {
            case R.id.cancel_button :
                setResult(0);
                finish();
                break;
            case R.id.common_dialog_cancel_button :
                finish();
                break;
            case R.id.common_dialog_ok_button :
                c();
                break;
            case R.id.img_horizontal :
                h();
                break;
            case R.id.img_rotate :
                b(this.s + 90);
                break;
            case R.id.img_selected :
                this.i.setEnabled(false);
                if (!this.x) {
                    f();
                }
                break;
            case R.id.img_vertical :
                g();
                break;
        }
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 215 && this.i != null) {
            this.i.setEnabled(true);
            if (i2 == -1) {
                this.s = 0;
                this.u = 1;
                this.v = 1;
                this.q = true;
                a(intent.getData());
                if (this.w != null) {
                    this.w.a(1);
                }
            }
        }
    }

    protected boolean a(lh lhVar) {
        if (!lhVar.a()) {
            return false;
        }
        if (this.q || this.r != null) {
            return true;
        }
        this.l.startAnimation(AnimationUtils.loadAnimation(this, R.anim.ani_1));
        return false;
    }

    private void c() {
        if (a(this.w)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    AddImageCollectionActivity.this.m();
                    AddImageCollectionActivity.this.new a(AddImageCollectionActivity.this.getApplicationContext()).execute(new Void[0]);
                }
            }, 500L);
        }
    }

    class a extends jq {
        public a(Context context) {
            super(context);
            AddImageCollectionActivity.this.a(this);
        }

        @Override
        public void a() {
            try {
                publishProgress(new String[]{"Processing..."});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void b() {
            if (AddImageCollectionActivity.this.x) {
                ke.a(AddImageCollectionActivity.this.getApplicationContext(), kq.a().a(AddImageCollectionActivity.this.getApplicationContext(), R.string.design_manager_message_edit_complete), 0).show();
            } else {
                ke.a(AddImageCollectionActivity.this.getApplicationContext(), kq.a().a(AddImageCollectionActivity.this.getApplicationContext(), R.string.design_manager_message_add_complete), 0).show();
            }
            AddImageCollectionActivity.this.o();
            AddImageCollectionActivity.this.finish();
        }

        @Override
        public void a(String str) {
            AddImageCollectionActivity.this.o();
        }
    }

    private void a(Uri uri) {
        String strA;
        if (uri == null || (strA = kz.a(this, uri)) == null) {
            return;
        }
        e(strA);
    }

    private void e(String str) {
        this.r = str;
        this.i.setImageBitmap(kh.a(str, 1024, 1024));
        this.z = str.lastIndexOf("/");
        this.A = str.lastIndexOf(".");
        if (str.endsWith(".9.png")) {
            this.A = str.lastIndexOf(".9.png");
        }
        if (this.j != null && (this.j.getText() == null || this.j.getText().length() <= 0)) {
            this.j.setText(str.substring(this.z + 1, this.A));
        }
        try {
            this.t = kh.a(str);
            i();
        } catch (Exception e) {
            e.printStackTrace();
        }
        d();
    }

    private void d() {
        if (this.l != null) {
            this.l.setVisibility(4);
        }
        if (this.f1198a == null || this.b == null || this.c == null) {
            return;
        }
        this.f1198a.setVisibility(8);
        this.b.setVisibility(0);
        this.c.setVisibility(8);
    }

    private ArrayList<String> e() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("app_icon");
        Iterator<ProjectResourceBean> it = this.p.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().resName);
        }
        return arrayList;
    }

    private void f() {
        try {
            Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, kq.a().a(this, R.string.common_word_choose)), Opcodes.XOR_INT_LIT16);
        } catch (ActivityNotFoundException unused) {
            ke.b(this, kq.a().a(this, R.string.common_error_activity_not_found), 0).show();
        }
    }

    private void b(int i) {
        if (this.r == null || this.r.length() <= 0) {
            return;
        }
        this.s = i;
        if (this.s == 360) {
            this.s = 0;
        }
        i();
    }

    private void g() {
        if (this.r == null || this.r.length() <= 0) {
            return;
        }
        if (this.s == 90 || this.s == 270) {
            this.v *= -1;
        } else {
            this.u *= -1;
        }
        i();
    }

    private void h() {
        if (this.r == null || this.r.length() <= 0) {
            return;
        }
        if (this.s == 90 || this.s == 270) {
            this.u *= -1;
        } else {
            this.v *= -1;
        }
        i();
    }

    private void i() {
        this.i.setImageBitmap(kh.a(kh.a(kh.a(this.r, 1024, 1024), this.t), this.s, this.v, this.u));
    }

    private String a(ProjectResourceBean projectResourceBean) {
        return fe.w() + File.separator + "image" + File.separator + "data" + File.separator + projectResourceBean.resFullName;
    }
}
