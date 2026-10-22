package com.nexusteam.blacklogics.editor.manage.sound_manager;

import com.nexusteam.internal.ke;
import com.nexusteam.internal.ki;
import com.nexusteam.internal.kk;
import com.nexusteam.internal.kq;
import com.nexusteam.internal.ma;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import com.nexusteam.blacklogics.R;
import com.nexusteam.internal.beans.ProjectResourceBean;
import com.nexusteam.internal.lib.base.BaseFragment;
import android.graphics.drawable.Drawable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class ManageSoundProjectFragment extends BaseFragment implements View.OnClickListener {
    private kk B;
    private ArrayList<ProjectResourceBean> C;
    private FloatingActionButton H;

    protected RecyclerView f1337a;
    private String c;
    private LinearLayout d;
    private Button e;
    private Button f;
    private MediaPlayer h;
    private TimerTask v;
    private MediaPlayer w;
    private TextView z;
    public boolean b = false;
    private a g = null;
    private ImageView i = null;
    private ImageView j = null;
    private SeekBar k = null;
    private TextView l = null;
    private TextView r = null;
    private TextView s = null;
    private EditText t = null;
    private Timer u = new Timer();
    private boolean x = false;
    private Uri y = null;
    private String A = "";
    private int D = -1;
    private int E = -1;
    private TextView F = null;
    private ProgressBar G = null;

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        ViewGroup viewGroup2 = (ViewGroup) layoutInflater.inflate(R.layout.fr_manage_sound_list, viewGroup, false);
        setHasOptionsMenu(true);
        this.d = (LinearLayout) viewGroup2.findViewById(R.id.layout_btn_group);
        this.e = (Button) viewGroup2.findViewById(R.id.btn_delete);
        this.f = (Button) viewGroup2.findViewById(R.id.btn_cancel);
        this.H = (FloatingActionButton) viewGroup2.findViewById(R.id.fab);
        this.e.setText(kq.a().a(getActivity(), R.string.common_word_delete));
        this.f.setText(kq.a().a(getActivity(), R.string.common_word_cancel));
        this.e.setOnClickListener(this);
        this.f.setOnClickListener(this);
        this.f1337a = (RecyclerView) viewGroup2.findViewById(R.id.sound_list);
        this.f1337a.setLayoutManager(new LinearLayoutManager(getActivity(), 1, false));
        this.g = new a(this.f1337a);
        this.f1337a.setAdapter(this.g);
        this.z = (TextView) viewGroup2.findViewById(R.id.tv_guide);
        this.z.setText(kq.a().a(getActivity(), R.string.design_manager_sound_description_guide_add_sound));
        this.z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ki.a()) {
                    return;
                }
                ManageSoundProjectFragment.this.a(false);
                ManageSoundProjectFragment.this.j();
            }
        });
        this.H.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ki.a()) {
                    return;
                }
                ManageSoundProjectFragment.this.a(false);
                ManageSoundProjectFragment.this.j();
            }
        });
        return viewGroup2;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        bundle.putString("sc_id", this.c);
        bundle.putString("dir_path", this.A);
        bundle.putParcelableArrayList("sounds", this.C);
        super.onSaveInstanceState(bundle);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        this.B = new kk();
        this.B.c(this.A);
        this.C = new ArrayList<>();
        if (bundle == null) {
            d();
        } else {
            this.c = bundle.getString("sc_id");
            this.A = bundle.getString("dir_path");
            this.C = bundle.getParcelableArrayList("sounds");
        }
        this.g.notifyDataSetChanged();
        f();
    }

    public void a() {
        this.u.cancel();
        if (this.h != null && this.h.isPlaying()) {
            this.h.pause();
            this.j.setImageResource(R.drawable.ic_play_circle_outline_black_36dp);
        }
        if (this.E != -1) {
            this.C.get(this.E).curSoundPosition = 0;
            this.E = -1;
            this.D = -1;
            this.g.notifyDataSetChanged();
        }
        if (this.w == null || !this.w.isPlaying()) {
            return;
        }
        this.w.pause();
    }

    private void d() {
        this.c = getActivity().getIntent().getStringExtra("sc_id");
        this.A = ma.c(this.c).e();
        ArrayList<ProjectResourceBean> arrayList = ma.c(this.c).b;
        if (arrayList == null) {
            return;
        }
        Iterator<ProjectResourceBean> it = arrayList.iterator();
        while (it.hasNext()) {
            ProjectResourceBean srcBean = it.next();
            ProjectResourceBean copy = new ProjectResourceBean(srcBean.resType, srcBean.resName, srcBean.resFullName);
            copy.isNew = srcBean.isNew;
            copy.isSelected = false;
            copy.curSoundPosition = 0;
            copy.totalSoundDuration = srcBean.totalSoundDuration;
            copy.savedPos = srcBean.savedPos;
            this.C.add(copy);
        }
    }

    public void b() {
        if (this.C != null && this.C.size() > 0) {
            for (ProjectResourceBean projectResourceBean : this.C) {
                if (projectResourceBean.isNew) {
                    this.B.e(b(projectResourceBean.resFullName));
                }
            }
        }
        for (ProjectResourceBean projectResourceBean2 : this.C) {
            if (projectResourceBean2.isNew) {
                try {
                    String strA = a(projectResourceBean2);
                    if (this.B.a(strA)) {
                        this.B.e(strA);
                    }
                    a(projectResourceBean2.resFullName, strA);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        for (int i = 0; i < this.C.size(); i++) {
            ProjectResourceBean projectResourceBean3 = this.C.get(i);
            if (projectResourceBean3.isNew) {
                this.C.set(i, new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, projectResourceBean3.resName, projectResourceBean3.resName + projectResourceBean3.resFullName.substring(projectResourceBean3.resFullName.lastIndexOf("."))));
            }
        }
        ma.c(this.c).b(this.C);
        ma.c(this.c).j();
        ma.a(this.c).b(ma.c(this.c));
        ma.a(this.c).c();
    }

    public ArrayList<ProjectResourceBean> c() {
        return this.C;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.manage_sound_menu, menu);
        if (this.b) {
            menu.findItem(R.id.menu_sound_delete).setVisible(false);
        } else {
            menu.findItem(R.id.menu_sound_delete).setVisible(true);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_sound_add :
                a(false);
                j();
                break;
            case R.id.menu_sound_delete :
                a(!this.b);
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void e() {
        Iterator<ProjectResourceBean> it = this.C.iterator();
        while (it.hasNext()) {
            it.next().isSelected = false;
        }
    }

    public void a(boolean z) {
        this.b = z;
        getActivity().invalidateOptionsMenu();
        e();
        if (this.b) {
            a();
            this.d.setVisibility(0);
        } else {
            this.d.setVisibility(8);
        }
        this.g.notifyDataSetChanged();
    }

    public void a(final int i) {
        String strA;
        if (this.E == i) {
            if (this.w != null) {
                if (this.w.isPlaying()) {
                    this.u.cancel();
                    this.w.pause();
                    this.C.get(this.E).curSoundPosition = this.w.getCurrentPosition();
                    this.g.notifyItemChanged(this.E);
                    return;
                }
                this.w.start();
                b(i);
                this.g.notifyDataSetChanged();
                return;
            }
            return;
        }
        if (this.w != null && this.w.isPlaying()) {
            this.u.cancel();
            this.w.pause();
            this.w.release();
        }
        if (this.D != -1) {
            this.C.get(this.D).curSoundPosition = 0;
            this.g.notifyItemChanged(this.D);
        }
        this.E = i;
        this.D = i;
        this.g.notifyItemChanged(this.E);
        this.w = new MediaPlayer();
        this.w.setAudioStreamType(3);
        this.w.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                ManageSoundProjectFragment.this.w.start();
                ManageSoundProjectFragment.this.b(i);
                ManageSoundProjectFragment.this.g.notifyItemChanged(ManageSoundProjectFragment.this.E);
            }
        });
        this.w.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                ManageSoundProjectFragment.this.u.cancel();
                ((ProjectResourceBean) ManageSoundProjectFragment.this.C.get(ManageSoundProjectFragment.this.E)).curSoundPosition = 0;
                ManageSoundProjectFragment.this.g.notifyItemChanged(ManageSoundProjectFragment.this.E);
                ManageSoundProjectFragment.this.E = -1;
            }
        });
        try {
            if (this.C.get(this.E).isNew) {
                strA = this.C.get(this.E).resFullName;
            } else {
                strA = a(this.C.get(this.E));
            }
            this.w.setDataSource(strA);
            this.w.prepare();
        } catch (Exception e) {
            this.E = -1;
            this.g.notifyItemChanged(this.E);
            e.printStackTrace();
        }
    }

    public void a(ArrayList<ProjectResourceBean> arrayList) {
        ArrayList<ProjectResourceBean> arrayList2 = new ArrayList<>();
        ArrayList<String> arrayList3 = new ArrayList();
        for (ProjectResourceBean projectResourceBean : arrayList) {
            String str = projectResourceBean.resName;
            if (a(str)) {
                arrayList3.add(str);
            } else {
                ProjectResourceBean projectResourceBean2 = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, str, projectResourceBean.resFullName);
                projectResourceBean2.isNew = true;
                arrayList2.add(projectResourceBean2);
            }
        }
        if (arrayList3.size() > 0) {
            String strA = kq.a().a(getActivity(), R.string.common_message_name_unavailable);
            String str2 = "";
            for (String str3 : arrayList3) {
                if (str2.length() > 0) {
                    str2 = str2 + ", ";
                }
                str2 = str2 + str3;
            }
            ke.a(getActivity(), strA + "\n[" + str2 + "]", 1).show();
        } else {
            ke.a(getActivity(), kq.a().a(getActivity(), R.string.design_manager_message_import_complete), 1).show();
            b(arrayList2);
            this.g.notifyDataSetChanged();
        }
        f();
    }

    private void f() {
        if (this.C.size() == 0) {
            this.z.setVisibility(0);
            this.f1337a.setVisibility(8);
        } else {
            this.f1337a.setVisibility(0);
            this.z.setVisibility(8);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        a();
    }

    @Override
    public void onClick(View view) {
        if (ki.a()) {
            return;
        }
        int id = view.getId();
        if (id != R.id.btn_cancel) {
            if (id != R.id.btn_delete || !this.b) {
                return;
            }
            int size = this.C.size();
            while (true) {
                size--;
                if (size >= 0) {
                    ProjectResourceBean projectResourceBean = this.C.get(size);
                    projectResourceBean.curSoundPosition = 0;
                    if (projectResourceBean.isSelected) {
                        this.C.remove(size);
                    }
                } else {
                    this.g.notifyDataSetChanged();
                    this.E = -1;
                    this.D = -1;
                    a(false);
                    f();
                    ke.a(getActivity(), kq.a().a(getActivity(), R.string.common_message_complete_delete), 1).show();
                    this.H.show();
                    return;
                }
            }
        } else if (this.b) {
            a(false);
        }
    }

    private String b(String str) {
        return this.A + File.separator + str;
    }

    public String a(ProjectResourceBean projectResourceBean) {
        return this.A + File.separator + projectResourceBean.resName + projectResourceBean.resFullName.substring(projectResourceBean.resFullName.lastIndexOf("."));
    }

    private void a(String str, String str2) {
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            FileOutputStream fileOutputStream = new FileOutputStream(str2);
            byte[] bArr = new byte[1024];
            while (true) {
                int i = fileInputStream.read(bArr);
                if (i != -1) {
                    fileOutputStream.write(bArr, 0, i);
                } else {
                    fileInputStream.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    return;
                }
            }
        } catch (Exception unused) {
        }
    }

    public boolean a(String str) {
        Iterator<ProjectResourceBean> it = this.C.iterator();
        while (it.hasNext()) {
            if (it.next().resName.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public void b(ArrayList<ProjectResourceBean> arrayList) {
        Iterator<ProjectResourceBean> it = arrayList.iterator();
        while (it.hasNext()) {
            this.C.add(it.next());
        }
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        switch (i) {
            case 269:
                if (i2 == -1) {
                    ProjectResourceBean added = intent.getParcelableExtra("project_resource");
                    if (added != null) {
                        this.C.add(added);
                    } else {
                        return;
                    }
                    ke.a(getActivity(), kq.a().a(getActivity(), R.string.design_manager_message_add_complete), 1).show();
                    this.g.notifyDataSetChanged();
                    f();
                    ((ManageSoundActivity) getActivity()).b().c();
                }
                break;
            case 270:
                if (i2 == -1) {
                    ProjectResourceBean edited = intent.getParcelableExtra("project_resource");
                    if (edited != null) {
                        this.C.set(this.g.f1344a, edited);
                    } else {
                        return;
                    }
                    ke.a(getActivity(), kq.a().a(getActivity(), R.string.design_manager_message_edit_complete), 1).show();
                    this.g.notifyDataSetChanged();
                    f();
                    ((ManageSoundActivity) getActivity()).b().c();
                }
                break;
        }
    }

    public void b(final int i) {
        this.u = new Timer();
        this.v = new TimerTask() {
            @Override
            public void run() {
                ManageSoundProjectFragment.this.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (ManageSoundProjectFragment.this.w == null) {
                            ManageSoundProjectFragment.this.u.cancel();
                            return;
                        }
                        a.C0062a c0062a = (a.C0062a) ManageSoundProjectFragment.this.f1337a.findViewHolderForLayoutPosition(i);
                        int currentPosition = ManageSoundProjectFragment.this.w.getCurrentPosition() / 1000;
                        c0062a.g.setText(String.format("%d:%02d", Integer.valueOf(currentPosition / 60), Integer.valueOf(currentPosition % 60)));
                        c0062a.h.setProgress(ManageSoundProjectFragment.this.w.getCurrentPosition() / 100);
                    }
                });
            }
        };
        this.u.schedule(this.v, 100L, 100L);
    }

    private ArrayList<String> g() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("app_icon");
        Iterator<ProjectResourceBean> it = this.C.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().resName);
        }
        return arrayList;
    }

    public int c(String str) {
        long j;
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            mediaMetadataRetriever.setDataSource(str);
            j = Long.parseLong(mediaMetadataRetriever.extractMetadata(9));
        } catch (IllegalArgumentException unused) {
            j = 0;
        }
        return (int) j;
    }

    public void j() {
        a();
        Intent intent = new Intent(getContext(), (Class<?>) AddSoundActivity.class);
        intent.putExtra("sc_id", this.c);
        intent.putExtra("dir_path", this.A);
        intent.putExtra("sound_names", g());
        startActivityForResult(intent, 269);
    }

    public void k() {
        Intent intent = new Intent(getContext(), (Class<?>) AddSoundActivity.class);
        intent.putExtra("sc_id", this.c);
        intent.putExtra("dir_path", this.A);
        intent.putExtra("sound_names", g());
        intent.putExtra("request_code", 270);
        intent.putExtra("project_resource", this.C.get(this.g.f1344a));
        startActivityForResult(intent, 270);
    }

    class a extends RecyclerView.Adapter<a.C0062a> {

        int f1344a = -1;

        public a(RecyclerView recyclerView) {
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView2, int i, int i2) {
                        super.onScrolled(recyclerView2, i, i2);
                        if (i2 > 2) {
                            if (ManageSoundProjectFragment.this.H.isEnabled()) {
                                ManageSoundProjectFragment.this.H.hide();
                            }
                        } else {
                            if (i2 >= -2 || !ManageSoundProjectFragment.this.H.isEnabled()) {
                                return;
                            }
                            ManageSoundProjectFragment.this.H.show();
                        }
                    }
                });
            }
        }

        @Override
        public C0062a onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new C0062a(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.manage_sound_list_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(C0062a c0062a, int i) {
            if (i < 0 || i >= ManageSoundProjectFragment.this.C.size()) {
                return;
            }
            ProjectResourceBean bean = ManageSoundProjectFragment.this.C.get(i);
            if (bean == null) {
                return;
            }
            if (!ManageSoundProjectFragment.this.b) {
                a(bean, c0062a.c);
                c0062a.c.setVisibility(0);
                c0062a.j.setVisibility(8);
            } else {
                c0062a.c.setVisibility(8);
                c0062a.j.setVisibility(0);
            }
            if (((ProjectResourceBean) ManageSoundProjectFragment.this.C.get(i)).isSelected) {
                c0062a.f.setImageResource(R.drawable.ic_checkmark_green_48dp);
            } else {
                c0062a.f.setImageResource(R.drawable.ic_trashcan_white_48dp);
            }
            int i2 = ((ProjectResourceBean) ManageSoundProjectFragment.this.C.get(i)).curSoundPosition / 1000;
            if (((ProjectResourceBean) ManageSoundProjectFragment.this.C.get(i)).totalSoundDuration == 0) {
                ((ProjectResourceBean) ManageSoundProjectFragment.this.C.get(i)).totalSoundDuration = ManageSoundProjectFragment.this.c(((ProjectResourceBean) ManageSoundProjectFragment.this.C.get(i)).isNew ? ((ProjectResourceBean) ManageSoundProjectFragment.this.C.get(i)).resFullName : ManageSoundProjectFragment.this.a((ProjectResourceBean) ManageSoundProjectFragment.this.C.get(i)));
            }
            int i3 = ((ProjectResourceBean) ManageSoundProjectFragment.this.C.get(i)).totalSoundDuration / 1000;
            c0062a.g.setText(String.format("%d:%02d", Integer.valueOf(i2 / 60), Integer.valueOf(i2 % 60)));
            c0062a.i.setText(String.format("%d:%02d", Integer.valueOf(i3 / 60), Integer.valueOf(i3 % 60)));
            c0062a.b.setChecked(((ProjectResourceBean) ManageSoundProjectFragment.this.C.get(i)).isSelected);
            c0062a.d.setText(((ProjectResourceBean) ManageSoundProjectFragment.this.C.get(i)).resName);
            if (ManageSoundProjectFragment.this.E == i && ManageSoundProjectFragment.this.w != null && ManageSoundProjectFragment.this.w.isPlaying()) {
                c0062a.e.setImageResource(R.drawable.ic_pause_blue_circle_48dp);
            } else {
                c0062a.e.setImageResource(R.drawable.circled_play_96_blue);
            }
            c0062a.h.setMax(((ProjectResourceBean) ManageSoundProjectFragment.this.C.get(i)).totalSoundDuration / 100);
            c0062a.h.setProgress(((ProjectResourceBean) ManageSoundProjectFragment.this.C.get(i)).curSoundPosition / 100);
        }

        @Override
        public int getItemCount() {
            return ManageSoundProjectFragment.this.C.size();
        }

        class C0062a extends RecyclerView.ViewHolder {

            public CardView f1347a;
            public CheckBox b;
            public ImageView c;
            public TextView d;
            public ImageView e;
            public ImageView f;
            public TextView g;
            public ProgressBar h;
            public TextView i;
            public LinearLayout j;

            public C0062a(View view) {
                super(view);
                this.f1347a = (CardView) view.findViewById(R.id.layout_item);
                this.b = (CheckBox) view.findViewById(R.id.chk_select);
                this.c = (ImageView) view.findViewById(R.id.img_album);
                this.f = (ImageView) view.findViewById(R.id.img_delete);
                this.d = (TextView) view.findViewById(R.id.tv_sound_name);
                this.e = (ImageView) view.findViewById(R.id.img_play);
                this.g = (TextView) view.findViewById(R.id.tv_currenttime);
                this.h = (ProgressBar) view.findViewById(R.id.prog_playtime);
                this.i = (TextView) view.findViewById(R.id.tv_endtime);
                this.j = (LinearLayout) view.findViewById(R.id.delete_img_container);
                this.e.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view2) {
                        if (ki.a()) {
                            return;
                        }
                        a.this.f1344a = C0062a.this.getLayoutPosition();
                        if (ManageSoundProjectFragment.this.b) {
                            return;
                        }
                        ManageSoundProjectFragment.this.a(a.this.f1344a);
                    }
                });
                this.b.setVisibility(8);
                this.f1347a.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view2) {
                        if (ki.a()) {
                            return;
                        }
                        a.this.f1344a = C0062a.this.getLayoutPosition();
                        if (ManageSoundProjectFragment.this.b) {
                            C0062a.this.b.setChecked(!C0062a.this.b.isChecked());
                            ((ProjectResourceBean) ManageSoundProjectFragment.this.C.get(a.this.f1344a)).isSelected = C0062a.this.b.isChecked();
                            a.this.notifyItemChanged(a.this.f1344a);
                            return;
                        }
                        ManageSoundProjectFragment.this.a();
                        ManageSoundProjectFragment.this.k();
                    }
                });
                this.f1347a.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view2) {
                        ManageSoundProjectFragment.this.a(true);
                        a.this.f1344a = C0062a.this.getLayoutPosition();
                        C0062a.this.b.setChecked(!C0062a.this.b.isChecked());
                        ((ProjectResourceBean) ManageSoundProjectFragment.this.C.get(a.this.f1344a)).isSelected = C0062a.this.b.isChecked();
                        return true;
                    }
                });
            }
        }

        private void a(ProjectResourceBean projectResourceBean, final ImageView imageView) {
            if (projectResourceBean == null || imageView == null) {
                return;
            }
            String strA;
            if (!projectResourceBean.isNew) {
                strA = ManageSoundProjectFragment.this.a(projectResourceBean);
            } else {
                strA = projectResourceBean.resFullName;
            }
            if (strA == null || strA.isEmpty()) {
                imageView.setImageResource(R.drawable.default_album_art_200dp);
                return;
            }
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            try {
                mediaMetadataRetriever.setDataSource(strA);
                if (mediaMetadataRetriever.getEmbeddedPicture() != null) {
                    Glide.with(ManageSoundProjectFragment.this.getActivity()).load(mediaMetadataRetriever.getEmbeddedPicture()).centerCrop().into(new CustomTarget<Drawable>() {
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
                    imageView.setBackgroundResource(R.drawable.bg_outline_album);
                }
            } catch (IllegalArgumentException unused) {
                imageView.setImageResource(R.drawable.default_album_art_200dp);
                imageView.setBackgroundResource(R.drawable.bg_outline_album);
            } catch (RuntimeException unused2) {
                imageView.setImageResource(R.drawable.default_album_art_200dp);
                imageView.setBackgroundResource(R.drawable.bg_outline_album);
            }
            try { mediaMetadataRetriever.release(); } catch (Exception ignored) {}
        }
    }
}
