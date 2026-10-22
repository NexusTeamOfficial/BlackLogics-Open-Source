package com.nexusteam.blacklogics.editor.manage.sound_manager;

import com.nexusteam.internal.fc;
import com.nexusteam.internal.fe;
import com.nexusteam.internal.kq;
import com.nexusteam.internal.kz;
import com.nexusteam.internal.lo;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.fragment.app.FragmentActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.android.dx.io.Opcodes;
import com.nexusteam.blacklogics.R;
import com.nexusteam.internal.beans.ProjectResourceBean;
import com.nexusteam.internal.lib.base.BaseDialogActivity;
import com.nexusteam.internal.lib.ui.EasyDeleteEditText;
import android.graphics.drawable.Drawable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.analytics.HitBuilders;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class AddSoundCollectionActivity extends BaseDialogActivity implements View.OnClickListener {

    EasyDeleteEditText f1300a;
    private String b;
    private LinearLayout d;
    private LinearLayout e;
    private LinearLayout f;
    private RelativeLayout g;
    private EditText h;
    private TextView i;
    private TextView j;
    private TextView k;
    private ImageView l;
    private ImageView m;
    private MediaPlayer n;
    private TimerTask p;
    private SeekBar q;
    private lo t;
    private ArrayList<ProjectResourceBean> u;
    private boolean c = false;
    private Timer o = new Timer();
    private Uri r = null;
    private boolean s = false;
    private ProjectResourceBean v = null;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        a(kq.a().a(this, R.string.design_manager_sound_title_add_sound));
        setContentView(R.layout.manage_sound_add);
        b(kq.a().a(this, R.string.common_word_save));
        c(kq.a().a(this, R.string.common_word_cancel));
        Intent intent = getIntent();
        this.u = intent.getParcelableArrayListExtra("sounds");
        this.b = intent.getStringExtra("sc_id");
        this.v = (ProjectResourceBean) intent.getParcelableExtra("edit_target");
        if (this.v != null) {
            this.c = true;
        }
        this.i = (TextView) findViewById(R.id.file_name);
        this.j = (TextView) findViewById(R.id.current_time);
        this.k = (TextView) findViewById(R.id.file_length);
        this.d = (LinearLayout) findViewById(R.id.layout_check);
        this.d.setVisibility(8);
        this.e = (LinearLayout) findViewById(R.id.layout_control);
        this.f = (LinearLayout) findViewById(R.id.layout_guide);
        this.g = (RelativeLayout) findViewById(R.id.select_file);
        this.m = (ImageView) findViewById(R.id.play);
        this.l = (ImageView) findViewById(R.id.img_album);
        this.q = (SeekBar) findViewById(R.id.seek);
        this.e.setVisibility(8);
        this.f1300a = (EasyDeleteEditText) findViewById(R.id.ed_input);
        this.h = this.f1300a.getEditText();
        this.h.setPrivateImeOptions("defaultInputmode=english;");
        this.f1300a.setHint(kq.a().a(this, R.string.design_manager_sound_hint_enter_sound_name));
        this.t = new lo(this, this.f1300a.getTextInputLayout(), fc.b, h());
        this.m.setEnabled(false);
        this.m.setOnClickListener(this);
        this.q.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (AddSoundCollectionActivity.this.n == null || !AddSoundCollectionActivity.this.n.isPlaying() || AddSoundCollectionActivity.this.o == null) {
                    return;
                }
                AddSoundCollectionActivity.this.o.cancel();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (AddSoundCollectionActivity.this.n != null) {
                    AddSoundCollectionActivity.this.n.seekTo(seekBar.getProgress() * 100);
                    if (AddSoundCollectionActivity.this.n.isPlaying()) {
                        AddSoundCollectionActivity.this.g();
                        return;
                    }
                    return;
                }
                seekBar.setProgress(0);
            }
        });
        this.g.setOnClickListener(this);
        this.Q.setOnClickListener(this);
        this.ABC.setOnClickListener(this);
        if (this.c) {
            a(kq.a().a(this, R.string.design_manager_sound_title_edit_sound_name));
            this.t = new lo(this, this.f1300a.getTextInputLayout(), fc.b, h(), this.v.resName);
            this.h.setText(this.v.resName);
            e(a(this.v));
        }
    }

    private String a(ProjectResourceBean projectResourceBean) {
        return fe.w() + File.separator + "sound" + File.separator + "data" + File.separator + this.v.resFullName;
    }

    private void c() {
        if (this.n.isPlaying()) {
            d();
            return;
        }
        this.n.start();
        g();
        this.m.setImageResource(R.drawable.ic_pause_circle_outline_black_36dp);
    }

    private void d() {
        if (this.n == null || !this.n.isPlaying()) {
            return;
        }
        this.o.cancel();
        this.n.pause();
        this.m.setImageResource(R.drawable.ic_play_circle_outline_black_36dp);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        d();
    }

    @Override
    public void onClick(View view)  {
        int id = view.getId();
        if (id == R.id.common_dialog_cancel_button) {
            finish();
            return;
        }
        if (id == R.id.common_dialog_ok_button) {
            e();
            return;
        }
        if (id == R.id.play) {
            c();
        } else if (id == R.id.select_file && !this.c) {
            this.g.setEnabled(false);
            f();
        }
    }

    @Override
    public void finish() {
        if (this.o != null) {
            this.o.cancel();
        }
        if (this.n != null) {
            if (this.n.isPlaying()) {
                this.n.stop();
            }
            this.n.release();
            this.n = null;
        }
        super.finish();
    }

    private void e() {
        try {
            setResult(RESULT_OK);
            finish();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void e_original_skipped() {
        throw new UnsupportedOperationException("Method not decompiled: AddSoundCollectionActivity.e()");
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent)  {
        Uri data;
        super.onActivityResult(i, i2, intent);
        if (i == 218 && this.g != null) {
            this.g.setEnabled(true);
            if (i2 != -1 || (data = intent.getData()) == null) {
                return;
            }
            this.r = data;
            if (kz.a(this, this.r) == null) {
                return;
            }
            a(data);
        }
    }

    private void a(Uri uri)  {
        final String strA = kz.a(this, uri);
        this.r = uri;
        try {
            if (this.n != null) {
                if (this.p != null) {
                    this.p.cancel();
                }
                if (this.n.isPlaying()) {
                    this.n.stop();
                }
            }
            this.n = new MediaPlayer();
            this.n.setAudioStreamType(3);
            this.n.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    if (strA == null) {
                        return;
                    }
                    AddSoundCollectionActivity.this.m.setImageResource(R.drawable.ic_pause_circle_outline_black_36dp);
                    AddSoundCollectionActivity.this.m.setEnabled(true);
                    AddSoundCollectionActivity.this.q.setMax(mediaPlayer.getDuration() / 100);
                    AddSoundCollectionActivity.this.q.setProgress(0);
                    int duration = mediaPlayer.getDuration() / 1000;
                    AddSoundCollectionActivity.this.k.setText(String.format("%d : %02d", Integer.valueOf(duration / 60), Integer.valueOf(duration % 60)));
                    AddSoundCollectionActivity.this.i.setText(strA.substring(strA.lastIndexOf("/") + 1));
                    mediaPlayer.start();
                    AddSoundCollectionActivity.this.g();
                }
            });
            this.n.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    AddSoundCollectionActivity.this.o.cancel();
                    AddSoundCollectionActivity.this.m.setImageResource(R.drawable.ic_play_circle_outline_black_36dp);
                    AddSoundCollectionActivity.this.q.setProgress(0);
                    AddSoundCollectionActivity.this.j.setText("0 : 00");
                }
            });
            this.n.setDataSource(this, uri);
            this.n.prepare();
            this.s = true;
            a(kz.a(this, this.r), this.l);
            this.e.setVisibility(0);
            this.f.setVisibility(8);
            try {
                if (this.h.getText() == null || this.h.getText().length() <= 0) {
                    int iLastIndexOf = strA.lastIndexOf("/");
                    int iLastIndexOf2 = strA.lastIndexOf(".");
                    if (iLastIndexOf2 <= 0) {
                        iLastIndexOf2 = strA.length();
                    }
                    this.h.setText(strA.substring(iLastIndexOf + 1, iLastIndexOf2));
                }
            } catch (Exception unused) {
            }
        } catch (Exception e) {
            this.s = false;
            this.e.setVisibility(8);
            this.f.setVisibility(0);
            e.printStackTrace();
        }
    }

    private void e(String str) {
        try {
            if (this.n != null) {
                if (this.p != null) {
                    this.p.cancel();
                }
                if (this.n.isPlaying()) {
                    this.n.stop();
                }
            }
            this.n = new MediaPlayer();
            this.n.setAudioStreamType(3);
            this.n.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    AddSoundCollectionActivity.this.m.setImageResource(R.drawable.ic_pause_circle_outline_black_36dp);
                    AddSoundCollectionActivity.this.m.setEnabled(true);
                    AddSoundCollectionActivity.this.q.setMax(mediaPlayer.getDuration() / 100);
                    AddSoundCollectionActivity.this.q.setProgress(0);
                    int duration = mediaPlayer.getDuration() / 1000;
                    AddSoundCollectionActivity.this.k.setText(String.format("%d : %02d", Integer.valueOf(duration / 60), Integer.valueOf(duration % 60)));
                    AddSoundCollectionActivity.this.n.start();
                    AddSoundCollectionActivity.this.g();
                }
            });
            this.n.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    AddSoundCollectionActivity.this.o.cancel();
                    AddSoundCollectionActivity.this.m.setImageResource(R.drawable.ic_play_circle_outline_black_36dp);
                    AddSoundCollectionActivity.this.q.setProgress(0);
                    AddSoundCollectionActivity.this.j.setText("0 : 00");
                }
            });
            this.n.setDataSource(this, Uri.parse(str));
            this.n.prepare();
            this.s = true;
            a(str, this.l);
            this.e.setVisibility(0);
            this.f.setVisibility(8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void a(String str, final ImageView imageView) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            mediaMetadataRetriever.setDataSource(str);
            if (mediaMetadataRetriever.getEmbeddedPicture() != null) {
                Glide.with((FragmentActivity) this).load(mediaMetadataRetriever.getEmbeddedPicture()).centerCrop().into(new CustomTarget<Drawable>() {
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
            }
        } catch (IllegalArgumentException unused) {
            imageView.setImageResource(R.drawable.default_album_art_200dp);
        }
        try { mediaMetadataRetriever.release(); } catch (Exception ignored) {}
    }

    protected boolean a(lo loVar) {
        if (!loVar.a()) {
            return false;
        }
        if ((this.s && this.r != null) || this.c) {
            return true;
        }
        this.g.startAnimation(AnimationUtils.loadAnimation(this, R.anim.ani_1));
        return false;
    }

    private void f() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("audio/*");
        startActivityForResult(Intent.createChooser(intent, kq.a().a(this, R.string.common_word_choose)), Opcodes.MUL_INT_LIT8);
    }

    public void g() {
        this.o = new Timer();
        this.p = new TimerTask() {
            @Override
            public void run() {
                AddSoundCollectionActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (AddSoundCollectionActivity.this.n == null) {
                            AddSoundCollectionActivity.this.o.cancel();
                            return;
                        }
                        int currentPosition = AddSoundCollectionActivity.this.n.getCurrentPosition() / 1000;
                        AddSoundCollectionActivity.this.j.setText(String.format("%d : %02d", Integer.valueOf(currentPosition / 60), Integer.valueOf(currentPosition % 60)));
                        AddSoundCollectionActivity.this.q.setProgress(AddSoundCollectionActivity.this.n.getCurrentPosition() / 100);
                    }
                });
            }
        };
        this.o.schedule(this.p, 100L, 100L);
    }

    private ArrayList<String> h() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("app_icon");
        Iterator<ProjectResourceBean> it = this.u.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().resName);
        }
        return arrayList;
    }
}
