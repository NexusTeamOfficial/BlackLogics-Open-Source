package com.nexusteam.blacklogics.editor.manage.sound_manager;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.nexusteam.blacklogics.R;
import com.nexusteam.internal.lib.base.BaseFragment;
import android.graphics.drawable.Drawable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import java.io.IOException;

public class ManageSoundImportFragment extends BaseFragment {

    MediaPlayer f1332a;
    String b;
    boolean c;
    private ImageView d;
    private ImageView e;

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        ViewGroup viewGroup2 = (ViewGroup) layoutInflater.inflate(R.layout.manage_sound_import_pager, viewGroup, false);
        this.d = (ImageView) viewGroup2.findViewById(R.id.img);
        this.e = (ImageView) viewGroup2.findViewById(R.id.img_play);
        this.b = getArguments().getString("sound_path");
        a(this.b, this.d);
        this.d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ManageSoundImportFragment.this.c) {
                    if (ManageSoundImportFragment.this.f1332a.isPlaying()) {
                        ManageSoundImportFragment.this.a();
                    } else {
                        ManageSoundImportFragment.this.f1332a.start();
                        ManageSoundImportFragment.this.e.setImageResource(R.drawable.ic_pause_circle_outline_black_36dp);
                    }
                }
            }
        });
        this.f1332a = new MediaPlayer();
        this.f1332a.setAudioStreamType(3);
        this.f1332a.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                ManageSoundImportFragment.this.c = true;
                ManageSoundImportFragment.this.e.setImageResource(R.drawable.ic_play_circle_outline_black_36dp);
            }
        });
        this.f1332a.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                ManageSoundImportFragment.this.c = false;
            }
        });
        try {
            this.f1332a.setDataSource(this.b);
            this.f1332a.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewGroup2;
    }

    private void a(String str, final ImageView imageView) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            try {
                mediaMetadataRetriever.setDataSource(str);
                if (mediaMetadataRetriever.getEmbeddedPicture() != null) {
                    Glide.with(getActivity()).load(mediaMetadataRetriever.getEmbeddedPicture()).centerCrop().into(new CustomTarget<Drawable>() {
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
        } finally {
            try { mediaMetadataRetriever.release(); } catch (Exception ignored) {}
        }
    }

    public void a() {
        if (this.c && this.f1332a.isPlaying()) {
            this.f1332a.pause();
            this.e.setImageResource(R.drawable.ic_play_circle_outline_black_36dp);
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
        if (this.f1332a != null) {
            this.f1332a.stop();
            this.f1332a.release();
        }
    }
}
