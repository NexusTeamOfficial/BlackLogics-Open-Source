package com.nexusteam.blacklogics.editor.manage.image_manager;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.nexusteam.blacklogics.R;
import com.nexusteam.internal.lib.base.BasePermissionFragment;
import android.graphics.drawable.Drawable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

public class ManageImageImportFragment extends BasePermissionFragment {

    private ImageView f1218a;

    @Override
    public void a() {
    }

    @Override
    public void a(int i) {
    }

    @Override
    public void b() {
    }

    @Override
    public void b(int i) {
    }

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        ViewGroup viewGroup2 = (ViewGroup) layoutInflater.inflate(R.layout.manage_image_import_pager, viewGroup, false);
        this.f1218a = (ImageView) viewGroup2.findViewById(R.id.img);
        Glide.with(getActivity()).asBitmap().load(getArguments().getString("img_path")).centerCrop().error(R.drawable.ic_remove_grey600_24dp).into(new BitmapImageViewTarget(this.f1218a) {
            @Override
            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> glideAnimation) {
                super.onResourceReady(bitmap, glideAnimation);
            }
        });
        return viewGroup2;
    }
}
