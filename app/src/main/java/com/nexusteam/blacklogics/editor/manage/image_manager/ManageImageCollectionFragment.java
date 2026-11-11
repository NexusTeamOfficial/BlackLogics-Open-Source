package com.nexusteam.blacklogics.editor.manage.image_manager;

import com.nexusteam.internal.ek;
import com.nexusteam.internal.fe;
import com.nexusteam.internal.ki;
import com.nexusteam.internal.kq;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.nexusteam.blacklogics.R;
import com.nexusteam.internal.beans.ProjectResourceBean;
import com.nexusteam.internal.lib.base.BaseFragment;
import android.graphics.drawable.Drawable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class ManageImageCollectionFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView f1206a;
    private String b;
    private ArrayList<ProjectResourceBean> c;
    private a d = null;
    private TextView e;
    private Button f;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ViewGroup viewGroup2 = (ViewGroup) layoutInflater.inflate(R.layout.fr_manage_image_list, viewGroup, false);
        this.f1206a = (RecyclerView) viewGroup2.findViewById(R.id.image_list);
        this.f1206a.setHasFixedSize(true);
        this.f1206a.setLayoutManager(new GridLayoutManager(getActivity(), f()));
        this.d = new a();
        this.f1206a.setAdapter(this.d);
        this.e = (TextView) viewGroup2.findViewById(R.id.tv_guide);
        this.e.setText(kq.a().a(getContext(), R.string.design_manager_image_description_guide_add_image));
        this.f = (Button) viewGroup2.findViewById(R.id.btn_import);
        this.f.setText(kq.a().a(getContext(), R.string.common_word_import).toUpperCase());
        this.f.setOnClickListener(this);
        this.f.setVisibility(8);
        return viewGroup2;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        bundle.putString("sc_id", this.b);
        super.onSaveInstanceState(bundle);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        if (bundle == null) {
            c();
        } else {
            this.b = bundle.getString("sc_id");
        }
        b();
    }

    @Override
    public void onClick(View view) {
        if (!ki.a() && view.getId() == R.id.btn_import) {
            this.f.setVisibility(8);
            a();
        }
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 232 && i2 == -1 && intent != null) {
            a((ArrayList<ProjectResourceBean>) (ArrayList<?>) intent.getParcelableArrayListExtra("results"));
        }
    }

    public void a() {
        ArrayList<ProjectResourceBean> arrayList = new ArrayList<>();
        for (ProjectResourceBean projectResourceBean : this.c) {
            if (projectResourceBean.isSelected) {
                arrayList.add(new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, projectResourceBean.resName, fe.w() + File.separator + "image" + File.separator + "data" + File.separator + projectResourceBean.resFullName));
            }
        }
        if (arrayList.size() > 0) {
            ArrayList<ProjectResourceBean> arrayListC = ((ManageImageActivity) getActivity()).a().c();
            Intent intent = new Intent(getActivity(), (Class<?>) ManageImageImportActivity.class);
            intent.putParcelableArrayListExtra("project_images", arrayListC);
            intent.putParcelableArrayListExtra("selected_collections", arrayList);
            startActivityForResult(intent, 232);
        }
        d();
        this.d.notifyDataSetChanged();
    }

    public void a(ArrayList<ProjectResourceBean> arrayList) {
        ArrayList<ProjectResourceBean> arrayList2 = new ArrayList<>();
        for (ProjectResourceBean projectResourceBean : arrayList) {
            arrayList2.add(new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, projectResourceBean.resName, projectResourceBean.resFullName));
        }
        if (arrayList2.size() > 0) {
            ((ManageImageActivity) getActivity()).a().a(arrayList2);
            ((ManageImageActivity) getActivity()).a(0);
        }
    }

    public void b() {
        try {
            this.c = ek.f().g();
            if (this.c == null) this.c = new ArrayList<>();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.c = new ArrayList<>();
        }
        if (this.d != null) this.d.notifyDataSetChanged();
        e();
    }

    public void c() {
        this.b = getActivity().getIntent().getStringExtra("sc_id");
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ((GridLayoutManager) this.f1206a.getLayoutManager()).setSpanCount(f());
        this.f1206a.requestLayout();
    }

    private int f() {
        return ((int) (getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().density)) / 100;
    }

    public void d() {
        Iterator<ProjectResourceBean> it = this.c.iterator();
        while (it.hasNext()) {
            it.next().isSelected = false;
        }
    }

    public void e() {
        if (this.c.size() == 0) {
            this.e.setVisibility(0);
            this.f1206a.setVisibility(8);
        } else {
            this.f1206a.setVisibility(0);
            this.e.setVisibility(8);
        }
    }

    public void g() {
        Iterator<ProjectResourceBean> it = this.c.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next().isSelected) {
                i++;
            }
        }
        if (i > 0) {
            this.f.setText(kq.a().a(getContext(), R.string.common_word_import_count, Integer.valueOf(i)).toUpperCase());
            this.f.setVisibility(0);
        } else {
            this.f.setVisibility(8);
        }
    }

    class a extends RecyclerView.Adapter<a.C0056a> {

        int f1207a = -1;

        public a() {
        }

        @Override
        public C0056a onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new C0056a(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.manage_image_list_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(C0056a c0056a, int i) {
            ProjectResourceBean projectResourceBean = (ProjectResourceBean) ManageImageCollectionFragment.this.c.get(i);
            String str = fe.w() + File.separator + "image" + File.separator + "data" + File.separator + projectResourceBean.resFullName;
            c0056a.f1209a.setVisibility(0);
            if (projectResourceBean.isNinePatch()) {
                c0056a.d.setVisibility(0);
            } else {
                c0056a.d.setVisibility(8);
            }
            Glide.with(ManageImageCollectionFragment.this.getActivity()).asBitmap().load(str).centerCrop().error(R.drawable.ic_remove_grey600_24dp).into(new BitmapImageViewTarget(c0056a.c) {
                @Override
                public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> glideAnimation) {
                    super.onResourceReady(bitmap, glideAnimation);
                }
            });
            c0056a.b.setText(((ProjectResourceBean) ManageImageCollectionFragment.this.c.get(i)).resName);
            c0056a.f1209a.setChecked(((ProjectResourceBean) ManageImageCollectionFragment.this.c.get(i)).isSelected);
        }

        @Override
        public int getItemCount() {
            return ManageImageCollectionFragment.this.c.size();
        }

        class C0056a extends RecyclerView.ViewHolder {

            public CheckBox f1209a;
            public TextView b;
            public ImageView c;
            public ImageView d;

            public C0056a(View view) {
                super(view);
                this.f1209a = (CheckBox) view.findViewById(R.id.chk_select);
                this.b = (TextView) view.findViewById(R.id.tv_image_name);
                this.c = (ImageView) view.findViewById(R.id.img);
                this.d = (ImageView) view.findViewById(R.id.img_nine_patch);
                this.f1209a.setVisibility(0);
                this.c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view2) {
                        C0056a.this.f1209a.setChecked(!C0056a.this.f1209a.isChecked());
                        a.this.f1207a = C0056a.this.getLayoutPosition();
                        ((ProjectResourceBean) ManageImageCollectionFragment.this.c.get(a.this.f1207a)).isSelected = C0056a.this.f1209a.isChecked();
                        ManageImageCollectionFragment.this.g();
                        a.this.notifyItemChanged(a.this.f1207a);
                    }
                });
            }
        }
    }
}
