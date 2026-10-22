package com.nexusteam.blacklogics.editor.manage.image_manager;

import com.nexusteam.internal.fc;
import com.nexusteam.internal.ke;
import com.nexusteam.internal.ki;
import com.nexusteam.internal.kq;
import com.nexusteam.internal.li;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nexusteam.blacklogics.R;
import com.nexusteam.internal.beans.EventBean;
import com.nexusteam.internal.beans.ProjectResourceBean;
import com.nexusteam.internal.lib.base.BaseAppCompatActivity;
import com.nexusteam.internal.lib.ui.EasyDeleteEditText;
import android.graphics.drawable.Drawable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.analytics.HitBuilders;
import java.util.ArrayList;
import java.util.Iterator;

public class ManageImageImportActivity extends BaseAppCompatActivity implements View.OnClickListener {

    li f1211a;
    private ImageView b;
    private TextView c;
    private TextView d;
    private TextView e;
    private TextView f;
    private RecyclerView g;
    private EasyDeleteEditText h;
    private EditText i;
    private CheckBox j;
    private a k;
    private ArrayList<ProjectResourceBean> l;
    private ArrayList<ProjectResourceBean> m;
    private int n = 0;
    private int o = 0;
    private Button p;
    private ImageView q;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!super.n()) {
            finish();
        }
        setContentView(R.layout.manage_image_import);
        this.b = (ImageView) findViewById(R.id.img_backbtn);
        this.b.setOnClickListener(this);
        this.c = (TextView) findViewById(R.id.tv_currentnum);
        this.d = (TextView) findViewById(R.id.tv_totalnum);
        this.e = (TextView) findViewById(R.id.tv_sendbtn);
        this.e.setText(kq.a().a(getApplicationContext(), R.string.common_word_import).toUpperCase());
        this.e.setOnClickListener(this);
        this.f = (TextView) findViewById(R.id.tv_samename);
        this.f.setText(kq.a().a(getApplicationContext(), R.string.design_manager_image_title_apply_same_naming));
        this.k = new a();
        this.g = (RecyclerView) findViewById(R.id.recycler_list);
        this.g.setHasFixedSize(true);
        this.g.setAdapter(this.k);
        this.g.setLayoutManager(new LinearLayoutManager(getApplicationContext(), 0, false));
        this.l = getIntent().getParcelableArrayListExtra("project_images");
        this.m = getIntent().getParcelableArrayListExtra("selected_collections");
        this.n = this.m.size();
        this.c.setText(String.valueOf(1));
        this.d.setText(String.valueOf(this.n));
        this.h = (EasyDeleteEditText) findViewById(R.id.ed_input);
        this.i = this.h.getEditText();
        this.i.setText(this.m.get(0).resName);
        this.i.setPrivateImeOptions("defaultInputmode=english;");
        this.h.setHint(kq.a().a(this, R.string.design_manager_image_hint_enter_image_name));
        this.f1211a = new li(getApplicationContext(), this.h.getTextInputLayout(), fc.b, d(), e());
        this.j = (CheckBox) findViewById(R.id.chk_samename);
        this.j.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (!z) {
                    ManageImageImportActivity.this.f1211a.b(((ProjectResourceBean) ManageImageImportActivity.this.m.get(ManageImageImportActivity.this.o)).resName);
                    ManageImageImportActivity.this.f1211a.a(1);
                } else {
                    ManageImageImportActivity.this.f1211a.b(null);
                    ManageImageImportActivity.this.f1211a.a(ManageImageImportActivity.this.m.size());
                }
            }
        });
        this.p = (Button) findViewById(R.id.btn_decide);
        this.p.setText(kq.a().a(getApplicationContext(), R.string.design_manager_change_name_button));
        this.p.setOnClickListener(this);
        this.q = (ImageView) findViewById(R.id.img);
    }

    @Override
    public void onPostCreate(@Nullable Bundle bundle) {
        super.onPostCreate(bundle);
        a();
        a(0);
        this.k.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!super.n()) {
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        if (ki.a()) {
            return;
        }
        int id = view.getId();
        if (id != R.id.btn_decide) {
            if (id != R.id.img_backbtn) {
                if (id == R.id.tv_sendbtn && !b()) {
                    Intent intent = new Intent();
                    intent.putParcelableArrayListExtra("results", this.m);
                    setResult(-1, intent);
                    finish();
                    return;
                }
                return;
            }
            onBackPressed();
            return;
        }
        String string = this.i.getText().toString();
        if (!c()) {
            this.i.setText(this.m.get(this.o).resName);
            return;
        }
        if (!this.j.isChecked()) {
            ProjectResourceBean projectResourceBean = this.m.get(this.o);
            projectResourceBean.resName = string;
            projectResourceBean.isDuplicateCollection = false;
            this.f1211a.a(e());
            this.k.notifyDataSetChanged();
            return;
        }
        int i = 0;
        while (i < this.m.size()) {
            ProjectResourceBean projectResourceBean2 = this.m.get(i);
            StringBuilder sb = new StringBuilder();
            sb.append(string);
            sb.append(EventBean.SEPARATOR);
            i++;
            sb.append(i);
            projectResourceBean2.resName = sb.toString();
            projectResourceBean2.isDuplicateCollection = false;
        }
        this.f1211a.a(e());
        this.k.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        setResult(-1);
        super.onBackPressed();
    }

    private void a() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (ProjectResourceBean projectResourceBean : this.m) {
            if (a(projectResourceBean.resName)) {
                projectResourceBean.isDuplicateCollection = true;
                arrayList.add(projectResourceBean);
            } else {
                projectResourceBean.isDuplicateCollection = false;
                arrayList2.add(projectResourceBean);
            }
        }
        if (arrayList.size() > 0) {
            ke.b(getApplicationContext(), kq.a().a(getApplicationContext(), R.string.design_manager_message_collection_name_conflict), 1).show();
        } else {
            ke.a(getApplicationContext(), kq.a().a(getApplicationContext(), R.string.design_manager_message_collection_name_no_conflict), 0).show();
        }
        this.m = new ArrayList<>();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            this.m.add((ProjectResourceBean) it.next());
        }
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            this.m.add((ProjectResourceBean) it2.next());
        }
    }

    private boolean b() {
        ArrayList<String> arrayList = new ArrayList();
        for (ProjectResourceBean projectResourceBean : this.m) {
            if (projectResourceBean.isDuplicateCollection) {
                arrayList.add(projectResourceBean.resName);
            }
        }
        if (arrayList.size() <= 0) {
            return false;
        }
        String strA = kq.a().a(getApplicationContext(), R.string.common_message_name_unavailable);
        String str = "";
        for (String str2 : arrayList) {
            if (str.length() > 0) {
                str = str + ", ";
            }
            str = str + str2;
        }
        ke.a(getApplicationContext(), strA + "\n[" + str + "]", 1).show();
        return true;
    }

    private boolean a(String str) {
        Iterator<ProjectResourceBean> it = this.l.iterator();
        while (it.hasNext()) {
            if (it.next().resName.equals(str)) {
                return true;
            }
        }
        return false;
    }

    private boolean c() {
        return this.f1211a.a();
    }

    private ArrayList<String> d() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("app_icon");
        Iterator<ProjectResourceBean> it = this.l.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().resName);
        }
        return arrayList;
    }

    private ArrayList<String> e() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("app_icon");
        Iterator<ProjectResourceBean> it = this.m.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().resName);
        }
        return arrayList;
    }

    public void a(int i) {
        Glide.with(getApplicationContext()).asBitmap().load(this.m.get(i).resFullName).centerCrop().error(R.drawable.ic_remove_grey600_24dp).into(new BitmapImageViewTarget(this.q) {
            @Override
            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> glideAnimation) {
                super.onResourceReady(bitmap, glideAnimation);
            }
        });
    }

    class a extends RecyclerView.Adapter<a.C0057a> {
        public a() {
        }

        @Override
        public C0057a onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new C0057a(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.manage_import_list_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(C0057a c0057a, int i) {
            ProjectResourceBean projectResourceBean = (ProjectResourceBean) ManageImageImportActivity.this.m.get(i);
            String str = projectResourceBean.resFullName;
            if (projectResourceBean.isDuplicateCollection) {
                c0057a.b.setImageResource(R.drawable.ic_cancel_48dp);
            } else {
                c0057a.b.setImageResource(R.drawable.ic_ok_48dp);
            }
            if (i == ManageImageImportActivity.this.o) {
                c0057a.c.setBackgroundResource(R.drawable.bg_outline_dark_yellow);
            } else {
                c0057a.c.setBackgroundColor(Color.parseColor("#ffffff"));
            }
            Glide.with(ManageImageImportActivity.this.getApplicationContext()).asBitmap().load(str).centerCrop().error(R.drawable.ic_remove_grey600_24dp).into(new BitmapImageViewTarget(c0057a.c) {
                @Override
                public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> glideAnimation) {
                    super.onResourceReady(bitmap, glideAnimation);
                }
            });
            c0057a.d.setText(((ProjectResourceBean) ManageImageImportActivity.this.m.get(i)).resName);
        }

        @Override
        public int getItemCount() {
            return ManageImageImportActivity.this.m.size();
        }

        class C0057a extends RecyclerView.ViewHolder {

            public LinearLayout f1216a;
            public ImageView b;
            public ImageView c;
            public TextView d;

            public C0057a(View view) {
                super(view);
                this.f1216a = (LinearLayout) view.findViewById(R.id.layout_item);
                this.b = (ImageView) view.findViewById(R.id.img_conflict);
                this.c = (ImageView) view.findViewById(R.id.img);
                this.d = (TextView) view.findViewById(R.id.tv_name);
                this.c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view2) {
                        if (ki.a()) {
                            return;
                        }
                        ManageImageImportActivity.this.o = C0057a.this.getLayoutPosition();
                        ManageImageImportActivity.this.a(ManageImageImportActivity.this.o);
                        ManageImageImportActivity.this.c.setText(String.valueOf(ManageImageImportActivity.this.o + 1));
                        ManageImageImportActivity.this.i.setText(((ProjectResourceBean) ManageImageImportActivity.this.m.get(ManageImageImportActivity.this.o)).resName);
                        if (!ManageImageImportActivity.this.j.isChecked()) {
                            ManageImageImportActivity.this.f1211a.b(((ProjectResourceBean) ManageImageImportActivity.this.m.get(ManageImageImportActivity.this.o)).resName);
                            ManageImageImportActivity.this.f1211a.a(1);
                        } else {
                            ManageImageImportActivity.this.f1211a.b(null);
                            ManageImageImportActivity.this.f1211a.a(ManageImageImportActivity.this.m.size());
                        }
                        ManageImageImportActivity.this.k.notifyDataSetChanged();
                    }
                });
            }
        }
    }
}
