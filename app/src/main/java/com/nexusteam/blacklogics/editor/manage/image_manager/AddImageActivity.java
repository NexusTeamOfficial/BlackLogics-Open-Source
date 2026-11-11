package com.nexusteam.blacklogics.editor.manage.image_manager;

import com.nexusteam.internal.fc;
import com.nexusteam.internal.jq;
import com.nexusteam.internal.ke;
import com.nexusteam.internal.kh;
import com.nexusteam.internal.kk;
import com.nexusteam.internal.kq;
import com.nexusteam.internal.kz;
import com.nexusteam.internal.lh;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
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

public class AddImageActivity extends BaseDialogActivity implements View.OnClickListener {
    private int D;
    private int E;
    private kk F;
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
    private String p;
    private ArrayList<ProjectResourceBean> q;
    private ArrayList<ProjectResourceBean> r;
    private ArrayList<Uri> y;
    private lh z;
    private boolean n = false;
    private boolean o = false;

    LinearLayout f1195a = null;
    LinearLayout b = null;
    TextView c = null;
    private boolean s = false;
    private String t = null;
    private int u = 0;
    private int v = 0;
    private int w = 1;
    private int x = 1;
    private String A = "";
    private boolean B = false;
    private ProjectResourceBean C = null;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        a(kq.a().a(this, R.string.design_manager_image_title_add_image));
        b(kq.a().a(getApplicationContext(), R.string.common_word_save));
        setContentView(R.layout.manage_image_add);
        Intent intent = getIntent();
        this.r = intent.getParcelableArrayListExtra("images");
        this.p = intent.getStringExtra("sc_id");
        this.A = intent.getStringExtra("dir_path");
        this.C = (ProjectResourceBean) intent.getParcelableExtra("edit_target");
        if (this.C != null) {
            this.B = true;
        }
        this.f1195a = (LinearLayout) findViewById(R.id.layout_img_inform);
        this.b = (LinearLayout) findViewById(R.id.layout_img_modify);
        this.m = (CheckBox) findViewById(R.id.chk_collection);
        this.l = (TextView) findViewById(R.id.tv_desc);
        this.c = (TextView) findViewById(R.id.tv_imgcnt);
        this.d = (TextView) findViewById(R.id.tv_collection);
        this.e = (TextView) findViewById(R.id.tv_add_photo);
        this.i = (ImageView) findViewById(R.id.img_selected);
        this.f = (ImageView) findViewById(R.id.img_rotate);
        this.g = (ImageView) findViewById(R.id.img_vertical);
        this.h = (ImageView) findViewById(R.id.img_horizontal);
        this.k = (EasyDeleteEditText) findViewById(R.id.ed_input);
        this.j = this.k.getEditText();
        this.j.setPrivateImeOptions("defaultInputmode=english;");
        this.k.setHint(kq.a().a(this, R.string.design_manager_image_hint_enter_image_name));
        this.z = new lh(this, this.k.getTextInputLayout(), fc.b, e());
        this.z.a(1);
        this.d.setText(kq.a().a(getApplicationContext(), R.string.design_manager_title_add_to_collection));
        this.e.setText(kq.a().a(getApplicationContext(), R.string.design_manager_image_title_add_image));
        this.i.setOnClickListener(this);
        this.f.setOnClickListener(this);
        this.g.setOnClickListener(this);
        this.h.setOnClickListener(this);
        this.Q.setOnClickListener(this);
        this.ABC.setOnClickListener(this);
        this.s = false;
        this.u = 0;
        this.w = 1;
        this.x = 1;
        this.F = new kk();
        this.F.c(this.A);
        this.q = new ArrayList<>();
    }

    @Override
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        if (this.B) {
            this.C.isEdited = true;
            a(kq.a().a(this, R.string.design_manager_image_title_edit_image));
            this.u = this.C.rotate;
            this.x = this.C.flipHorizontal;
            this.w = this.C.flipVertical;
            this.z = new lh(this, this.k.getTextInputLayout(), fc.b, e(), this.C.resName);
            this.z.a(1);
            this.j.setText(this.C.resName);
            this.j.setEnabled(false);
            this.m.setEnabled(false);
            this.e.setVisibility(8);
            if (this.C.savedPos == 0) {
                e(a(this.C));
            } else {
                e(this.C.resFullName);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
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
                g();
                break;
            case R.id.img_rotate :
                c(this.u + 90);
                break;
            case R.id.img_selected :
                this.i.setEnabled(false);
                if (this.B) {
                    a(false);
                } else {
                    a(true);
                }
                break;
            case R.id.img_vertical :
                f();
                break;
        }
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 215 && this.i != null) {
            this.i.setEnabled(true);
            if (i2 == -1) {
                this.e.setVisibility(8);
                this.u = 0;
                this.w = 1;
                this.x = 1;
                if (intent.getClipData() == null) {
                    this.s = true;
                    this.n = false;
                    a(intent.getData());
                    if (this.z != null) {
                        this.z.a(1);
                        return;
                    }
                    return;
                }
                ClipData clipData = intent.getClipData();
                if (clipData.getItemCount() == 1) {
                    this.s = true;
                    this.n = false;
                    a(clipData.getItemAt(0).getUri());
                    if (this.z != null) {
                        this.z.a(1);
                        return;
                    }
                    return;
                }
                a(clipData);
                this.n = true;
                if (this.z != null) {
                    this.z.a(clipData.getItemCount());
                }
            }
        }
    }

    protected boolean a(lh lhVar) {
        if (!lhVar.a()) {
            return false;
        }
        if (this.s || this.t != null) {
            return true;
        }
        this.l.startAnimation(AnimationUtils.loadAnimation(this, R.anim.ani_1));
        return false;
    }

    private void c() {
        if (a(this.z)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    AddImageActivity.this.m();
                    AddImageActivity.this.new a(AddImageActivity.this.getApplicationContext()).execute(new Void[0]);
                }
            }, 500L);
        }
    }

    class a extends jq {
        public a(Context context) {
            super(context);
            AddImageActivity.this.a(this);
        }

        @Override
        public void a() {
            try {
                publishProgress(new String[]{"Processing..."});
                if (AddImageActivity.this.q == null) {
                    AddImageActivity.this.q = new ArrayList<>();
                }
                AddImageActivity.this.q.clear();

                if (AddImageActivity.this.B) {
                    if (AddImageActivity.this.C != null) {
                        String name = AddImageActivity.this.j != null
                                ? AddImageActivity.this.j.getText().toString().trim()
                                : AddImageActivity.this.C.resName;
                        if (name != null && !name.isEmpty()) {
                            AddImageActivity.this.C.resName = name;
                        }
                    }
                    return;
                }

                String nameBase = AddImageActivity.this.j != null
                        ? AddImageActivity.this.j.getText().toString().trim()
                        : "image";
                if (nameBase.isEmpty()) nameBase = "image";

                java.util.List<Uri> uris = new ArrayList<>();
                if (AddImageActivity.this.y != null && !AddImageActivity.this.y.isEmpty()) {
                    uris.addAll(AddImageActivity.this.y);
                } else if (AddImageActivity.this.t != null) {
                }

                int index = 0;
                for (Uri uri : uris) {
                    String filePath = kz.a(AddImageActivity.this, uri);
                    if (filePath == null || filePath.isEmpty()) continue;
                    String resName = nameBase;
                    if (uris.size() > 1) {
                        resName = nameBase + "_" + (index + 1);
                    }
                    resName = resName.replaceAll("[^a-zA-Z0-9_]", "_");
                    ProjectResourceBean bean = new ProjectResourceBean(
                            ProjectResourceBean.PROJECT_RES_TYPE_FILE,
                            resName,
                            filePath
                    );
                    bean.isNew = true;
                    bean.isSelected = false;
                    bean.savedPos = 1;
                    AddImageActivity.this.q.add(bean);
                    index++;
                }

                if (AddImageActivity.this.q.isEmpty() && AddImageActivity.this.t != null) {
                    String filePath = AddImageActivity.this.t;
                    ProjectResourceBean bean = new ProjectResourceBean(
                            ProjectResourceBean.PROJECT_RES_TYPE_FILE,
                            nameBase.replaceAll("[^a-zA-Z0-9_]", "_"),
                            filePath
                    );
                    bean.isNew = true;
                    bean.savedPos = 1;
                    AddImageActivity.this.q.add(bean);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage() != null ? e.getMessage() : "image process failed");
            }
        }

        @Override
        public void b() {
            AddImageActivity.this.o();
            Intent intent = new Intent();
            intent.putExtra("sc_id", AddImageActivity.this.p);
            if (AddImageActivity.this.B) {
                intent.putExtra("image", AddImageActivity.this.C);
            } else {
                intent.putExtra("images", AddImageActivity.this.q);
            }
            AddImageActivity.this.setResult(-1, intent);
            AddImageActivity.this.finish();
        }

        @Override
        public void a(String str) {
            AddImageActivity.this.o();
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
        this.t = str;
        this.i.setImageBitmap(kh.a(str, 1024, 1024));
        this.D = str.lastIndexOf("/");
        this.E = str.lastIndexOf(".");
        if (str.endsWith(".9.png")) {
            this.E = str.lastIndexOf(".9.png");
        }
        if (this.j != null && (this.j.getText() == null || this.j.getText().length() <= 0)) {
            this.j.setText(str.substring(this.D + 1, this.E));
        }
        try {
            this.v = kh.a(str);
            h();
        } catch (Exception e) {
            e.printStackTrace();
        }
        d();
    }

    private void d() {
        if (this.l != null) {
            this.l.setVisibility(4);
        }
        if (this.f1195a == null || this.b == null || this.c == null) {
            return;
        }
        this.f1195a.setVisibility(8);
        this.b.setVisibility(0);
        this.c.setVisibility(8);
    }

    private void b(int i) {
        if (this.f1195a == null || this.b == null || this.c == null) {
            return;
        }
        this.f1195a.setVisibility(0);
        this.b.setVisibility(8);
        this.c.setVisibility(0);
        TextView textView = this.c;
        StringBuilder sb = new StringBuilder();
        sb.append("+ ");
        sb.append(i - 1);
        sb.append(" more");
        textView.setText(sb.toString());
    }

    private void a(ClipData clipData) {
        if (clipData != null) {
            this.y = new ArrayList<>();
            for (int i = 0; i < clipData.getItemCount(); i++) {
                if (i == 0) {
                    a(clipData.getItemAt(i).getUri());
                }
                this.y.add(clipData.getItemAt(i).getUri());
            }
            b(clipData.getItemCount());
        }
    }

    private ArrayList<String> e() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("app_icon");
        Iterator<ProjectResourceBean> it = this.r.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().resName);
        }
        return arrayList;
    }

    private void a(boolean z) {
        try {
            Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            if (z) {
                intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", true);
            }
            startActivityForResult(Intent.createChooser(intent, kq.a().a(this, R.string.common_word_choose)), Opcodes.XOR_INT_LIT16);
        } catch (ActivityNotFoundException unused) {
            ke.b(this, kq.a().a(this, R.string.common_error_activity_not_found), 0).show();
        }
    }

    private void c(int i) {
        if (this.t == null || this.t.length() <= 0) {
            return;
        }
        this.u = i;
        if (this.u == 360) {
            this.u = 0;
        }
        h();
    }

    private void f() {
        if (this.t == null || this.t.length() <= 0) {
            return;
        }
        if (this.u == 90 || this.u == 270) {
            this.x *= -1;
        } else {
            this.w *= -1;
        }
        h();
    }

    private void g() {
        if (this.t == null || this.t.length() <= 0) {
            return;
        }
        if (this.u == 90 || this.u == 270) {
            this.w *= -1;
        } else {
            this.x *= -1;
        }
        h();
    }

    private void h() {
        this.i.setImageBitmap(kh.a(kh.a(kh.a(this.t, 1024, 1024), this.v), this.u, this.x, this.w));
    }

    private String a(ProjectResourceBean projectResourceBean) {
        return this.A + File.separator + projectResourceBean.resFullName;
    }
}
