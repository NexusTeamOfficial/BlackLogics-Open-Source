package com.nexusteam.blacklogics.editor.manage.sound_manager;
import com.nexusteam.internal.em;

import com.nexusteam.internal.fc;
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
import android.widget.CheckBox;
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
import java.util.Timer;
import java.util.TimerTask;

public class AddSoundActivity extends BaseDialogActivity implements View.OnClickListener {

    private String f1293a;
    private String b;
    private int c;
    private LinearLayout d;
    private LinearLayout e;
    private LinearLayout f;
    private RelativeLayout g;
    private CheckBox h;
    private EditText i;
    private TextView j;
    private TextView k;
    private TextView l;
    private TextView m;
    private ImageView n;
    private ImageView o;
    private MediaPlayer p;
    private TimerTask r;
    private SeekBar s;
    private lo v;
    private ArrayList<String> w;
    private EasyDeleteEditText x;
    private Timer q = new Timer();
    private Uri t = null;
    private boolean u = false;

    @Override
    public void onCreate(Bundle bundle)  {
        String strA;
        super.onCreate(bundle);
        a(kq.a().a(this, R.string.design_manager_sound_title_add_sound));
        setContentView(R.layout.manage_sound_add);
        b(kq.a().a(this, R.string.common_word_save));
        c(kq.a().a(this, R.string.common_word_cancel));
        Intent intent = getIntent();
        this.w = intent.getStringArrayListExtra("sound_names");
        this.f1293a = intent.getStringExtra("sc_id");
        this.b = intent.getStringExtra("dir_path");
        this.c = intent.getIntExtra("request_code", -1);
        this.k = (TextView) findViewById(R.id.file_name);
        this.l = (TextView) findViewById(R.id.current_time);
        this.m = (TextView) findViewById(R.id.file_length);
        this.d = (LinearLayout) findViewById(R.id.layout_check);
        this.e = (LinearLayout) findViewById(R.id.layout_control);
        this.f = (LinearLayout) findViewById(R.id.layout_guide);
        this.h = (CheckBox) findViewById(R.id.chk_collection);
        this.j = (TextView) findViewById(R.id.tv_collection);
        this.g = (RelativeLayout) findViewById(R.id.select_file);
        this.o = (ImageView) findViewById(R.id.play);
        this.n = (ImageView) findViewById(R.id.img_album);
        this.s = (SeekBar) findViewById(R.id.seek);
        this.d.setVisibility(0);
        this.e.setVisibility(8);
        this.j.setText(kq.a().a(this, R.string.design_manager_title_add_to_collection));
        this.x = (EasyDeleteEditText) findViewById(R.id.ed_input);
        this.i = this.x.getEditText();
        this.x.setHint(kq.a().a(this, R.string.design_manager_sound_hint_enter_sound_name));
        this.v = new lo(this, this.x.getTextInputLayout(), fc.b, this.w);
        this.i.setPrivateImeOptions("defaultInputmode=english;");
        this.o.setEnabled(false);
        this.o.setOnClickListener(this);
        this.s.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (AddSoundActivity.this.p == null || !AddSoundActivity.this.p.isPlaying() || AddSoundActivity.this.q == null) {
                    return;
                }
                AddSoundActivity.this.q.cancel();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (AddSoundActivity.this.p != null) {
                    AddSoundActivity.this.p.seekTo(seekBar.getProgress() * 100);
                    if (AddSoundActivity.this.p.isPlaying()) {
                        AddSoundActivity.this.g();
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
        if (this.c == 270) {
            a(kq.a().a(this, R.string.design_manager_sound_title_edit_sound));
            ProjectResourceBean projectResourceBean = (ProjectResourceBean) intent.getParcelableExtra("project_resource");
            this.v = new lo(this, this.x.getTextInputLayout(), fc.b, new ArrayList());
            this.i.setText(projectResourceBean.resName);
            this.i.setEnabled(false);
            this.h.setEnabled(false);
            if (projectResourceBean.isNew) {
                strA = projectResourceBean.resFullName;
            } else {
                strA = a(projectResourceBean);
            }
            a(Uri.fromFile(new File(strA)));
        }
    }

    private String a(ProjectResourceBean projectResourceBean) {
        return this.b + File.separator + projectResourceBean.resName + projectResourceBean.resFullName.substring(projectResourceBean.resFullName.lastIndexOf("."));
    }

    private void c() {
        if (this.p.isPlaying()) {
            d();
            return;
        }
        this.p.start();
        g();
        this.o.setImageResource(R.drawable.ic_pause_circle_outline_black_36dp);
    }

    private void d() {
        if (this.p == null || !this.p.isPlaying()) {
            return;
        }
        this.q.cancel();
        this.p.pause();
        this.o.setImageResource(R.drawable.ic_play_circle_outline_black_36dp);
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
        } else {
            if (id != R.id.select_file) {
                return;
            }
            this.g.setEnabled(false);
            f();
        }
    }

    @Override
    public void finish() {
        if (this.q != null) {
            this.q.cancel();
        }
        if (this.p != null) {
            if (this.p.isPlaying()) {
                this.p.stop();
            }
            this.p.release();
            this.p = null;
        }
        super.finish();
    }

    private void e() {
        try {
            if (this.v != null && !this.v.a()) {
                return;
            }
            String name = this.i != null ? this.i.getText().toString().trim() : "";
            if (name.isEmpty()) {
                return;
            }
            if (this.c != 270 && (this.t == null || !this.u)) {
                if (this.g != null) {
                    this.g.startAnimation(AnimationUtils.loadAnimation(this, R.anim.ani_1));
                }
                return;
            }

            Intent result = new Intent();
            if (this.c == 270) {
                ProjectResourceBean existing = getIntent().getParcelableExtra("project_resource");
                if (existing == null) {
                    finish();
                    return;
                }
                result.putExtra("project_resource", existing);
            } else {
                String filePath = kz.a(this, this.t);
                if (filePath == null || filePath.isEmpty()) {
                    return;
                }
                String ext = "";
                int dot = filePath.lastIndexOf('.');
                if (dot >= 0) {
                    ext = filePath.substring(dot);
                }
                ProjectResourceBean bean = new ProjectResourceBean(
                        ProjectResourceBean.PROJECT_RES_TYPE_FILE,
                        name,
                        filePath
                );
                bean.isNew = true;
                bean.isSelected = false;
                bean.curSoundPosition = 0;
                if (this.p != null) {
                    try {
                        bean.totalSoundDuration = this.p.getDuration();
                    } catch (Exception ignored) {
                        bean.totalSoundDuration = 0;
                    }
                }
                result.putExtra("project_resource", bean);
                if (this.h != null && this.h.isChecked()) {
                    try {
                        em.f().a(name, bean);
                    } catch (Exception ignored) {
                    }
                }
            }
            setResult(RESULT_OK, result);
            finish();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
            a(data);
        }
    }

    private void a(Uri uri)  {
        final String strA = kz.a(this, uri);
        if (strA == null) {
            return;
        }
        this.t = uri;
        try {
            if (this.p != null) {
                if (this.r != null) {
                    this.r.cancel();
                }
                if (this.p.isPlaying()) {
                    this.p.stop();
                }
            }
            this.p = new MediaPlayer();
            this.p.setAudioStreamType(3);
            this.p.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    if (strA == null) {
                        return;
                    }
                    AddSoundActivity.this.o.setImageResource(R.drawable.ic_pause_circle_outline_black_36dp);
                    AddSoundActivity.this.o.setEnabled(true);
                    AddSoundActivity.this.s.setMax(mediaPlayer.getDuration() / 100);
                    AddSoundActivity.this.s.setProgress(0);
                    int duration = mediaPlayer.getDuration() / 1000;
                    AddSoundActivity.this.m.setText(String.format("%d : %02d", Integer.valueOf(duration / 60), Integer.valueOf(duration % 60)));
                    AddSoundActivity.this.k.setText(strA.substring(strA.lastIndexOf("/") + 1));
                    mediaPlayer.start();
                    AddSoundActivity.this.g();
                }
            });
            this.p.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    AddSoundActivity.this.q.cancel();
                    AddSoundActivity.this.o.setImageResource(R.drawable.ic_play_circle_outline_black_36dp);
                    AddSoundActivity.this.s.setProgress(0);
                    AddSoundActivity.this.l.setText("0 : 00");
                }
            });
            this.p.setDataSource(this, uri);
            this.p.prepare();
            this.u = true;
            a(kz.a(this, this.t), this.n);
            this.e.setVisibility(0);
            this.f.setVisibility(8);
            try {
                if (this.i.getText() == null || this.i.getText().length() <= 0) {
                    int iLastIndexOf = strA.lastIndexOf("/");
                    int iLastIndexOf2 = strA.lastIndexOf(".");
                    if (iLastIndexOf2 <= 0) {
                        iLastIndexOf2 = strA.length();
                    }
                    this.i.setText(strA.substring(iLastIndexOf + 1, iLastIndexOf2));
                }
            } catch (Exception unused) {
            }
        } catch (Exception e) {
            this.u = false;
            this.e.setVisibility(8);
            this.f.setVisibility(0);
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
                Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.default_album_art_200dp)).centerCrop().into(imageView);
            }
        } catch (IllegalArgumentException unused) {
            Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.default_album_art_200dp)).centerCrop().into(imageView);
        }
        try { mediaMetadataRetriever.release(); } catch (Exception ignored) {}
    }

    protected boolean a(lo loVar) {
        if (!loVar.a()) {
            return false;
        }
        if (this.u && this.t != null) {
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
        this.q = new Timer();
        this.r = new TimerTask() {
            @Override
            public void run() {
                AddSoundActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (AddSoundActivity.this.p == null) {
                            AddSoundActivity.this.q.cancel();
                            return;
                        }
                        int currentPosition = AddSoundActivity.this.p.getCurrentPosition() / 1000;
                        AddSoundActivity.this.l.setText(String.format("%d : %02d", Integer.valueOf(currentPosition / 60), Integer.valueOf(currentPosition % 60)));
                        AddSoundActivity.this.s.setProgress(AddSoundActivity.this.p.getCurrentPosition() / 100);
                    }
                });
            }
        };
        this.q.schedule(this.r, 100L, 100L);
    }
}
