package com.nexusteam.blacklogics.editor.manage.sound_manager;

import com.nexusteam.internal.em;
import com.nexusteam.internal.fe;
import com.nexusteam.internal.ki;
import com.nexusteam.internal.kk;
import com.nexusteam.internal.kq;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.nexusteam.blacklogics.R;
import com.nexusteam.internal.beans.ProjectResourceBean;
import com.nexusteam.internal.lib.base.BaseFragment;
import android.graphics.drawable.Drawable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class ManageSoundCollectionFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView f1314a;
    private String b;
    private kk d;
    private ArrayList<ProjectResourceBean> e;
    private TextView g;
    private Button h;
    private TimerTask j;
    private MediaPlayer k;
    private String c = "";
    private a f = null;
    private Timer i = new Timer();
    private int l = -1;
    private int r = -1;

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        ViewGroup viewGroup2 = (ViewGroup) layoutInflater.inflate(R.layout.fr_manage_sound_list, viewGroup, false);
        this.f1314a = (RecyclerView) viewGroup2.findViewById(R.id.sound_list);
        this.f1314a.setLayoutManager(new LinearLayoutManager(getActivity(), 1, false));
        this.f = new a();
        this.f1314a.setAdapter(this.f);
        this.g = (TextView) viewGroup2.findViewById(R.id.tv_guide);
        this.g.setText(kq.a().a(getActivity(), R.string.design_manager_sound_description_guide_add_sound));
        this.h = (Button) viewGroup2.findViewById(R.id.btn_import);
        this.h.setText(kq.a().a(getContext(), R.string.common_word_import).toUpperCase());
        this.h.setOnClickListener(this);
        this.h.setVisibility(8);
        viewGroup2.findViewById(R.id.fab).setVisibility(8);
        return viewGroup2;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("sc_id", this.b);
        bundle.putString("dir_path", this.c);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        this.d = new kk();
        this.d.c(this.c);
        if (bundle == null) {
            d();
        } else {
            this.b = bundle.getString("sc_id");
            this.c = bundle.getString("dir_path");
        }
        c();
    }

    @Override
    public void onPause() {
        super.onPause();
        a();
    }

    public void a() {
        this.i.cancel();
        if (this.r != -1) {
            this.e.get(this.r).curSoundPosition = 0;
            this.r = -1;
            this.l = -1;
            this.f.notifyDataSetChanged();
        }
        if (this.k == null || !this.k.isPlaying()) {
            return;
        }
        this.k.pause();
    }

    @Override
    public void onClick(View view) {
        if (!ki.a() && view.getId() == R.id.btn_import) {
            this.h.setVisibility(8);
            b();
        }
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 232 && i2 == -1 && intent != null) {
            a((ArrayList<ProjectResourceBean>) (ArrayList<?>) intent.getParcelableArrayListExtra("results"));
        }
    }

    public void b() {
        a();
        ArrayList<ProjectResourceBean> arrayList = new ArrayList<>();
        for (ProjectResourceBean projectResourceBean : this.e) {
            if (projectResourceBean.isSelected) {
                arrayList.add(new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, projectResourceBean.resName, fe.w() + File.separator + "sound" + File.separator + "data" + File.separator + projectResourceBean.resFullName));
            }
        }
        if (arrayList.size() > 0) {
            ArrayList<ProjectResourceBean> arrayListC = ((ManageSoundActivity) getActivity()).a().c();
            Intent intent = new Intent(getActivity(), (Class<?>) ManageSoundImportActivity.class);
            intent.putParcelableArrayListExtra("project_sounds", arrayListC);
            intent.putParcelableArrayListExtra("selected_collections", arrayList);
            startActivityForResult(intent, 232);
        }
        f();
        this.f.notifyDataSetChanged();
    }

    public void a(ArrayList<ProjectResourceBean> arrayList) {
        ArrayList<ProjectResourceBean> arrayList2 = new ArrayList<>();
        for (ProjectResourceBean projectResourceBean : arrayList) {
            arrayList2.add(new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, projectResourceBean.resName, projectResourceBean.resFullName));
        }
        if (arrayList2.size() > 0) {
            ((ManageSoundActivity) getActivity()).a().a(arrayList2);
            ((ManageSoundActivity) getActivity()).a(0);
        }
    }

    public void c() {
        try {
            this.e = em.f().g();
            if (this.e == null) {
                this.e = new ArrayList<>();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            this.e = new ArrayList<>();
        }
        if (this.f != null) {
            this.f.notifyDataSetChanged();
        }
        e();
    }

    public void d() {
        this.b = getActivity().getIntent().getStringExtra("sc_id");
        this.c = getActivity().getIntent().getStringExtra("dir_path");
    }

    private void f() {
        Iterator<ProjectResourceBean> it = this.e.iterator();
        while (it.hasNext()) {
            it.next().isSelected = false;
        }
    }

    public void e() {
        if (this.e.size() == 0) {
            this.g.setVisibility(0);
            this.f1314a.setVisibility(8);
        } else {
            this.f1314a.setVisibility(0);
            this.g.setVisibility(8);
        }
    }

    public void a(final int i) {
        if (this.r == i) {
            if (this.k != null) {
                if (this.k.isPlaying()) {
                    this.i.cancel();
                    this.k.pause();
                    this.e.get(this.r).curSoundPosition = this.k.getCurrentPosition();
                    this.f.notifyItemChanged(this.r);
                    return;
                }
                this.k.start();
                b(i);
                this.f.notifyDataSetChanged();
                return;
            }
            return;
        }
        if (this.k != null && this.k.isPlaying()) {
            this.i.cancel();
            this.k.pause();
            this.k.release();
        }
        if (this.l != -1) {
            this.e.get(this.l).curSoundPosition = 0;
            this.f.notifyItemChanged(this.l);
        }
        this.r = i;
        this.l = i;
        this.f.notifyItemChanged(this.r);
        this.k = new MediaPlayer();
        this.k.setAudioStreamType(3);
        this.k.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                ManageSoundCollectionFragment.this.k.start();
                ManageSoundCollectionFragment.this.b(i);
                ManageSoundCollectionFragment.this.f.notifyItemChanged(ManageSoundCollectionFragment.this.r);
            }
        });
        this.k.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                ManageSoundCollectionFragment.this.i.cancel();
                ((ProjectResourceBean) ManageSoundCollectionFragment.this.e.get(ManageSoundCollectionFragment.this.r)).curSoundPosition = 0;
                ManageSoundCollectionFragment.this.f.notifyItemChanged(ManageSoundCollectionFragment.this.r);
                ManageSoundCollectionFragment.this.r = -1;
            }
        });
        try {
            this.k.setDataSource(fe.w() + File.separator + "sound" + File.separator + "data" + File.separator + this.e.get(this.r).resFullName);
            this.k.prepare();
        } catch (Exception e) {
            this.r = -1;
            this.f.notifyItemChanged(this.r);
            e.printStackTrace();
        }
    }

    public void b(final int i) {
        this.i = new Timer();
        this.j = new TimerTask() {
            @Override
            public void run() {
                ManageSoundCollectionFragment.this.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (ManageSoundCollectionFragment.this.k == null) {
                            ManageSoundCollectionFragment.this.i.cancel();
                            return;
                        }
                        a.C0060a c0060a = (a.C0060a) ManageSoundCollectionFragment.this.f1314a.findViewHolderForLayoutPosition(i);
                        int currentPosition = ManageSoundCollectionFragment.this.k.getCurrentPosition() / 1000;
                        c0060a.e.setText(String.format("%d:%02d", Integer.valueOf(currentPosition / 60), Integer.valueOf(currentPosition % 60)));
                        c0060a.f.setProgress(ManageSoundCollectionFragment.this.k.getCurrentPosition() / 100);
                    }
                });
            }
        };
        this.i.schedule(this.j, 100L, 100L);
    }

    public int a(String str) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(str);
        return (int) Long.parseLong(mediaMetadataRetriever.extractMetadata(9));
    }

    public void g() {
        Iterator<ProjectResourceBean> it = this.e.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next().isSelected) {
                i++;
            }
        }
        if (i > 0) {
            this.h.setText(kq.a().a(getContext(), R.string.common_word_import_count, Integer.valueOf(i)).toUpperCase());
            this.h.setVisibility(0);
        } else {
            this.h.setVisibility(8);
        }
    }

    class a extends RecyclerView.Adapter<a.C0060a> {

        int f1319a = -1;

        public a() {
        }

        @Override
        public C0060a onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new C0060a(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.manage_sound_list_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(C0060a c0060a, int i) {
            String str = fe.w() + File.separator + "sound" + File.separator + "data" + File.separator + ((ProjectResourceBean) ManageSoundCollectionFragment.this.e.get(i)).resFullName;
            c0060a.f1321a.setVisibility(0);
            a(str, c0060a.b);
            int i2 = ((ProjectResourceBean) ManageSoundCollectionFragment.this.e.get(i)).curSoundPosition / 1000;
            if (((ProjectResourceBean) ManageSoundCollectionFragment.this.e.get(i)).totalSoundDuration == 0) {
                ((ProjectResourceBean) ManageSoundCollectionFragment.this.e.get(i)).totalSoundDuration = ManageSoundCollectionFragment.this.a(str);
            }
            int i3 = ((ProjectResourceBean) ManageSoundCollectionFragment.this.e.get(i)).totalSoundDuration / 1000;
            c0060a.e.setText(String.format("%d:%02d", Integer.valueOf(i2 / 60), Integer.valueOf(i2 % 60)));
            c0060a.g.setText(String.format("%d:%02d", Integer.valueOf(i3 / 60), Integer.valueOf(i3 % 60)));
            c0060a.f1321a.setChecked(((ProjectResourceBean) ManageSoundCollectionFragment.this.e.get(i)).isSelected);
            c0060a.c.setText(((ProjectResourceBean) ManageSoundCollectionFragment.this.e.get(i)).resName);
            if (ManageSoundCollectionFragment.this.r == i && ManageSoundCollectionFragment.this.k != null && ManageSoundCollectionFragment.this.k.isPlaying()) {
                c0060a.d.setImageResource(R.drawable.ic_pause_blue_circle_48dp);
            } else {
                c0060a.d.setImageResource(R.drawable.circled_play_96_blue);
            }
            c0060a.f.setMax(((ProjectResourceBean) ManageSoundCollectionFragment.this.e.get(i)).totalSoundDuration / 100);
            c0060a.f.setProgress(((ProjectResourceBean) ManageSoundCollectionFragment.this.e.get(i)).curSoundPosition / 100);
        }

        @Override
        public int getItemCount() {
            return ManageSoundCollectionFragment.this.e.size();
        }

        class C0060a extends RecyclerView.ViewHolder {

            public CheckBox f1321a;
            public ImageView b;
            public TextView c;
            public ImageView d;
            public TextView e;
            public ProgressBar f;
            public TextView g;

            public C0060a(View view) {
                super(view);
                this.f1321a = (CheckBox) view.findViewById(R.id.chk_select);
                this.b = (ImageView) view.findViewById(R.id.img_album);
                this.c = (TextView) view.findViewById(R.id.tv_sound_name);
                this.d = (ImageView) view.findViewById(R.id.img_play);
                this.e = (TextView) view.findViewById(R.id.tv_currenttime);
                this.f = (ProgressBar) view.findViewById(R.id.prog_playtime);
                this.g = (TextView) view.findViewById(R.id.tv_endtime);
                this.d.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view2) {
                        if (ki.a()) {
                            return;
                        }
                        ManageSoundCollectionFragment.this.a(C0060a.this.getLayoutPosition());
                    }
                });
                this.f1321a.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view2) {
                        a.this.f1319a = C0060a.this.getLayoutPosition();
                        ((ProjectResourceBean) ManageSoundCollectionFragment.this.e.get(a.this.f1319a)).isSelected = C0060a.this.f1321a.isChecked();
                        ManageSoundCollectionFragment.this.g();
                        a.this.notifyItemChanged(a.this.f1319a);
                    }
                });
            }
        }

        private void a(String str, final ImageView imageView) {
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            try {
                mediaMetadataRetriever.setDataSource(str);
                if (mediaMetadataRetriever.getEmbeddedPicture() != null) {
                    Glide.with(ManageSoundCollectionFragment.this.getActivity()).load(mediaMetadataRetriever.getEmbeddedPicture()).centerCrop().into(new CustomTarget<Drawable>() {
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
            }
            try { mediaMetadataRetriever.release(); } catch (Exception ignored) {}
        }
    }
}
