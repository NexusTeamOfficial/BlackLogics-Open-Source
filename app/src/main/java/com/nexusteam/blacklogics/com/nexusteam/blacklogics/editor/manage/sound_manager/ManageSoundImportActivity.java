package com.nexusteam.blacklogics.editor.manage.sound_manager;

import com.nexusteam.internal.fc;
import com.nexusteam.internal.ke;
import com.nexusteam.internal.ki;
import com.nexusteam.internal.kq;
import com.nexusteam.internal.li;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
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
import com.google.android.gms.analytics.HitBuilders;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ManageSoundImportActivity extends BaseAppCompatActivity implements View.OnClickListener {

    li f1324a;
    boolean b;
    private ImageView c;
    private TextView d;
    private TextView e;
    private TextView f;
    private TextView g;
    private RecyclerView h;
    private EasyDeleteEditText i;
    private EditText j;
    private CheckBox k;
    private a l;
    private ArrayList<ProjectResourceBean> m;
    private ArrayList<ProjectResourceBean> n;
    private int o = 0;
    private int p = 0;
    private Button q;
    private MediaPlayer r;
    private ImageView s;
    private ImageView t;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!super.n()) {
            finish();
        }
        setContentView(R.layout.manage_sound_import);
        this.c = (ImageView) findViewById(R.id.img_backbtn);
        this.c.setOnClickListener(this);
        this.d = (TextView) findViewById(R.id.tv_currentnum);
        this.e = (TextView) findViewById(R.id.tv_totalnum);
        this.f = (TextView) findViewById(R.id.tv_sendbtn);
        this.f.setText(kq.a().a(getApplicationContext(), R.string.common_word_import).toUpperCase());
        this.f.setOnClickListener(this);
        this.g = (TextView) findViewById(R.id.tv_samename);
        this.g.setText(kq.a().a(getApplicationContext(), R.string.design_manager_sound_title_apply_same_naming));
        this.l = new a();
        this.h = (RecyclerView) findViewById(R.id.recycler_list);
        this.h.setHasFixedSize(true);
        this.h.setAdapter(this.l);
        this.h.setLayoutManager(new LinearLayoutManager(getApplicationContext(), 0, false));
        this.m = getIntent().getParcelableArrayListExtra("project_sounds");
        this.n = getIntent().getParcelableArrayListExtra("selected_collections");
        this.o = this.n.size();
        this.d.setText(String.valueOf(1));
        this.e.setText(String.valueOf(this.o));
        this.i = (EasyDeleteEditText) findViewById(R.id.ed_input);
        this.j = this.i.getEditText();
        this.j.setText(this.n.get(0).resName);
        this.j.setPrivateImeOptions("defaultInputmode=english;");
        this.i.setHint(kq.a().a(this, R.string.design_manager_sound_hint_enter_sound_name));
        this.f1324a = new li(getApplicationContext(), this.i.getTextInputLayout(), fc.b, e(), f());
        this.k = (CheckBox) findViewById(R.id.chk_samename);
        this.k.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (!z) {
                    ManageSoundImportActivity.this.f1324a.b(((ProjectResourceBean) ManageSoundImportActivity.this.n.get(ManageSoundImportActivity.this.p)).resName);
                    ManageSoundImportActivity.this.f1324a.a(1);
                } else {
                    ManageSoundImportActivity.this.f1324a.b(null);
                    ManageSoundImportActivity.this.f1324a.a(ManageSoundImportActivity.this.n.size());
                }
            }
        });
        this.q = (Button) findViewById(R.id.btn_decide);
        this.q.setText(kq.a().a(getApplicationContext(), R.string.design_manager_change_name_button));
        this.q.setOnClickListener(this);
        this.s = (ImageView) findViewById(R.id.img_album);
        this.t = (ImageView) findViewById(R.id.img_play);
        this.t.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!super.n()) {
            finish();
        }
    }

    @Override
    public void onPostCreate(@Nullable Bundle bundle) {
        super.onPostCreate(bundle);
        b();
        a(0);
        this.l.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if (ki.a()) {
            return;
        }
        int id = view.getId();
        if (id != R.id.btn_decide) {
            if (id == R.id.img_backbtn) {
                onBackPressed();
                return;
            }
            if (id != R.id.img_play) {
                if (id == R.id.tv_sendbtn && !c()) {
                    if (this.r != null && this.r.isPlaying()) {
                        this.r.stop();
                        this.r.release();
                    }
                    Intent intent = new Intent();
                    intent.putParcelableArrayListExtra("results", this.n);
                    setResult(-1, intent);
                    finish();
                    return;
                }
                return;
            }
            if (this.b) {
                if (this.r.isPlaying()) {
                    a();
                    return;
                } else {
                    this.r.start();
                    this.t.setImageResource(R.drawable.ic_pause_circle_outline_black_36dp);
                    return;
                }
            }
            return;
        }
        String string = this.j.getText().toString();
        if (!d()) {
            this.j.setText(this.n.get(this.p).resName);
            return;
        }
        if (!this.k.isChecked()) {
            ProjectResourceBean projectResourceBean = this.n.get(this.p);
            projectResourceBean.resName = string;
            projectResourceBean.isDuplicateCollection = false;
            this.f1324a.a(f());
            this.l.notifyDataSetChanged();
            return;
        }
        int i = 0;
        while (i < this.n.size()) {
            ProjectResourceBean projectResourceBean2 = this.n.get(i);
            StringBuilder sb = new StringBuilder();
            sb.append(string);
            sb.append(EventBean.SEPARATOR);
            i++;
            sb.append(i);
            projectResourceBean2.resName = sb.toString();
            projectResourceBean2.isDuplicateCollection = false;
        }
        this.f1324a.a(f());
        this.l.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        setResult(-1);
        super.onBackPressed();
    }

    private void b() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (ProjectResourceBean projectResourceBean : this.n) {
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
        this.n = new ArrayList<>();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            this.n.add((ProjectResourceBean) it.next());
        }
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            this.n.add((ProjectResourceBean) it2.next());
        }
    }

    private boolean c() {
        ArrayList<String> arrayList = new ArrayList();
        for (ProjectResourceBean projectResourceBean : this.n) {
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
        Iterator<ProjectResourceBean> it = this.m.iterator();
        while (it.hasNext()) {
            if (it.next().resName.equals(str)) {
                return true;
            }
        }
        return false;
    }

    private boolean d() {
        return this.f1324a.a();
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

    private ArrayList<String> f() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("app_icon");
        Iterator<ProjectResourceBean> it = this.n.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().resName);
        }
        return arrayList;
    }

    public void a(int i) {
        String str = this.n.get(i).resFullName;
        a(str, this.s, -1);
        this.r = new MediaPlayer();
        this.r.setAudioStreamType(3);
        this.r.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                ManageSoundImportActivity.this.b = true;
                ManageSoundImportActivity.this.t.setImageResource(R.drawable.ic_play_circle_outline_black_36dp);
            }
        });
        this.r.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                ManageSoundImportActivity.this.b = false;
            }
        });
        try {
            this.r.setDataSource(str);
            this.r.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class a extends RecyclerView.Adapter<a.C0061a> {
        public a() {
        }

        @Override
        public C0061a onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new C0061a(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.manage_import_list_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(C0061a c0061a, int i) {
            ProjectResourceBean projectResourceBean = (ProjectResourceBean) ManageSoundImportActivity.this.n.get(i);
            String str = projectResourceBean.resFullName;
            int unused = ManageSoundImportActivity.this.p;
            if (projectResourceBean.isDuplicateCollection) {
                c0061a.b.setImageResource(R.drawable.ic_cancel_48dp);
            } else {
                c0061a.b.setImageResource(R.drawable.ic_ok_48dp);
            }
            if (i == ManageSoundImportActivity.this.p) {
                c0061a.c.setBackgroundResource(R.drawable.bg_outline_dark_yellow);
            } else {
                c0061a.c.setBackgroundColor(Color.parseColor("#ffffff"));
            }
            ManageSoundImportActivity.this.a(str, c0061a.c, i);
            c0061a.d.setText(((ProjectResourceBean) ManageSoundImportActivity.this.n.get(i)).resName);
        }

        @Override
        public int getItemCount() {
            return ManageSoundImportActivity.this.n.size();
        }

        class C0061a extends RecyclerView.ViewHolder {

            public LinearLayout f1330a;
            public ImageView b;
            public ImageView c;
            public TextView d;

            public C0061a(View view) {
                super(view);
                this.f1330a = (LinearLayout) view.findViewById(R.id.layout_item);
                this.b = (ImageView) view.findViewById(R.id.img_conflict);
                this.c = (ImageView) view.findViewById(R.id.img);
                this.d = (TextView) view.findViewById(R.id.tv_name);
                this.c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view2) {
                        if (ki.a()) {
                            return;
                        }
                        ManageSoundImportActivity.this.a();
                        ManageSoundImportActivity.this.p = C0061a.this.getLayoutPosition();
                        ManageSoundImportActivity.this.a(ManageSoundImportActivity.this.p);
                        ManageSoundImportActivity.this.d.setText(String.valueOf(ManageSoundImportActivity.this.p + 1));
                        ManageSoundImportActivity.this.j.setText(((ProjectResourceBean) ManageSoundImportActivity.this.n.get(ManageSoundImportActivity.this.p)).resName);
                        if (!ManageSoundImportActivity.this.k.isChecked()) {
                            ManageSoundImportActivity.this.f1324a.b(((ProjectResourceBean) ManageSoundImportActivity.this.n.get(ManageSoundImportActivity.this.p)).resName);
                            ManageSoundImportActivity.this.f1324a.a(1);
                        } else {
                            ManageSoundImportActivity.this.f1324a.b(null);
                            ManageSoundImportActivity.this.f1324a.a(ManageSoundImportActivity.this.n.size());
                        }
                        ManageSoundImportActivity.this.l.notifyDataSetChanged();
                    }
                });
            }
        }
    }

    public void a(String str, final ImageView imageView, int i) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            mediaMetadataRetriever.setDataSource(str);
            if (mediaMetadataRetriever.getEmbeddedPicture() != null) {
                Glide.with(getApplicationContext()).load(mediaMetadataRetriever.getEmbeddedPicture()).centerCrop().into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable glideDrawable, Transition<? super Drawable> glideAnimation) {
                        imageView.setImageDrawable(glideDrawable);
                    }
                    @Override
                    public void onLoadCleared(Drawable placeholder) {
                    }
                });
            } else {
                imageView.setImageResource(R.drawable.default_album_art_200dp);
                if (i != -1 && this.p != i) {
                    imageView.setBackgroundResource(R.drawable.bg_outline_album);
                }
            }
        } catch (IllegalArgumentException unused) {
            imageView.setImageResource(R.drawable.default_album_art_200dp);
            if (i != -1 && this.p != i) {
                imageView.setBackgroundResource(R.drawable.bg_outline_album);
            }
        }
        try { mediaMetadataRetriever.release(); } catch (Exception ignored) {}
    }

    public void a() {
        if (this.r != null && this.b && this.r.isPlaying()) {
            this.r.pause();
            this.t.setImageResource(R.drawable.ic_play_circle_outline_black_36dp);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        a();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.r != null) {
            this.r.stop();
            this.r.release();
        }
    }
}
