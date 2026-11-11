package com.nexusteam.blacklogics.editor.manage.image_manager;

import com.nexusteam.internal.ke;
import com.nexusteam.internal.kh;
import com.nexusteam.internal.ki;
import com.nexusteam.internal.kk;
import com.nexusteam.internal.kq;
import com.nexusteam.internal.ma;
import com.nexusteam.internal.mb;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.recyclerview.widget.GridLayoutManager;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.dx.io.Opcodes;
import com.nexusteam.blacklogics.R;
import com.nexusteam.internal.beans.ProjectResourceBean;
import com.nexusteam.internal.lib.base.BaseFragment;
import android.graphics.drawable.Drawable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.nexusteam.internal.common.ImportIconActivity;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class ManageImageProjectFragment extends BaseFragment implements View.OnClickListener {
    private String b;
    private kk d;
    private RecyclerView e;
    private ArrayList<ProjectResourceBean> f;
    private LinearLayout h;
    private Button i;
    private Button j;
    private TextView k;
    private FloatingActionButton l;
    private String c = "";
    private a g = null;

    public boolean f1220a = false;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ViewGroup viewGroup2 = (ViewGroup) layoutInflater.inflate(R.layout.fr_manage_image_list, viewGroup, false);
        setHasOptionsMenu(true);
        this.f = new ArrayList<>();
        this.e = (RecyclerView) viewGroup2.findViewById(R.id.image_list);
        this.e.setHasFixedSize(true);
        this.e.setLayoutManager(new GridLayoutManager(getActivity(), k()));
        this.g = new a(this.e);
        this.e.setAdapter(this.g);
        this.k = (TextView) viewGroup2.findViewById(R.id.tv_guide);
        this.k.setText(kq.a().a(getContext(), R.string.design_manager_image_description_guide_add_image));
        this.h = (LinearLayout) viewGroup2.findViewById(R.id.layout_btn_group);
        this.i = (Button) viewGroup2.findViewById(R.id.btn_accept);
        this.j = (Button) viewGroup2.findViewById(R.id.btn_cancel);
        this.i.setText(kq.a().a(getContext(), R.string.common_word_delete));
        this.j.setText(kq.a().a(getContext(), R.string.common_word_cancel));
        this.i.setOnClickListener(this);
        this.j.setOnClickListener(this);
        this.l = (FloatingActionButton) viewGroup2.findViewById(R.id.fab);
        this.l.setVisibility(0);
        this.l.setOnClickListener(this);
        mb.c();
        return viewGroup2;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        bundle.putString("sc_id", this.b);
        bundle.putString("dir_path", this.c);
        bundle.putParcelableArrayList("images", this.f);
        super.onSaveInstanceState(bundle);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        if (bundle == null) {
            a();
        } else {
            this.b = bundle.getString("sc_id");
            this.c = bundle.getString("dir_path");
            this.f = bundle.getParcelableArrayList("images");
        }
        this.d = new kk();
        this.d.c(this.c);
        this.g.notifyDataSetChanged();
        e();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.manage_image_menu, menu);
        if (this.f1220a) {
            menu.findItem(R.id.menu_image_delete).setVisible(false);
        } else {
            menu.findItem(R.id.menu_image_delete).setVisible(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.menu_image_delete) {
            a(!this.f1220a);
        } else if (itemId == R.id.menu_image_import) {
            g();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void f() {
        Intent intent = new Intent(getContext(), (Class<?>) AddImageActivity.class);
        intent.putParcelableArrayListExtra("images", this.f);
        intent.putExtra("sc_id", this.b);
        intent.putExtra("dir_path", this.c);
        startActivityForResult(intent, 267);
    }

    public void a(ProjectResourceBean projectResourceBean) {
        Intent intent = new Intent(getContext(), (Class<?>) AddImageActivity.class);
        intent.putParcelableArrayListExtra("images", this.f);
        intent.putExtra("sc_id", this.b);
        intent.putExtra("dir_path", this.c);
        intent.putExtra("edit_target", projectResourceBean);
        startActivityForResult(intent, 268);
    }

    private void g() {
        Intent intent = new Intent(getActivity(), (Class<?>) ImportIconActivity.class);
        intent.setFlags(536870912);
        intent.putStringArrayListExtra("imageNames", j());
        startActivityForResult(intent, Opcodes.MUL_INT_LIT16);
    }

    private ArrayList<String> j() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("app_icon");
        Iterator<ProjectResourceBean> it = this.f.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().resName);
        }
        return arrayList;
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
                projectResourceBean2.savedPos = 1;
                projectResourceBean2.isNew = true;
                projectResourceBean2.rotate = 0;
                projectResourceBean2.flipVertical = 1;
                projectResourceBean2.flipHorizontal = 1;
                arrayList2.add(projectResourceBean2);
            }
        }
        a(arrayList2, false);
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
        }
        this.g.notifyDataSetChanged();
        e();
    }

    public boolean a(String str) {
        Iterator<ProjectResourceBean> it = this.f.iterator();
        while (it.hasNext()) {
            if (it.next().resName.equals(str)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        if (ki.a()) {
            return;
        }
        int id = view.getId();
        if (id == R.id.btn_accept) {
            if (this.f1220a) {
                d();
                a(false);
                e();
                ke.a(getActivity(), kq.a().a(getActivity(), R.string.common_message_complete_delete), 1).show();
                this.l.show();
                return;
            }
            return;
        }
        if (id == R.id.btn_cancel) {
            if (this.f1220a) {
                a(false);
            }
        } else {
            if (id != R.id.fab) {
                return;
            }
            a(false);
            f();
        }
    }

    public void a() {
        this.b = getActivity().getIntent().getStringExtra("sc_id");
        this.c = ma.c(this.b).d();
        ArrayList<ProjectResourceBean> arrayList = ma.c(this.b).f373a;
        if (arrayList != null) {
            for (ProjectResourceBean projectResourceBean : arrayList) {
                if (projectResourceBean.flipVertical == 0) {
                    projectResourceBean.flipVertical = 1;
                }
                if (projectResourceBean.flipHorizontal == 0) {
                    projectResourceBean.flipHorizontal = 1;
                }
                projectResourceBean.savedPos = 0;
                this.f.add(projectResourceBean);
            }
        }
    }

    public void b() {
        String strB;
        Iterator<ProjectResourceBean> it = this.f.iterator();
        while (it.hasNext()) {
            ProjectResourceBean next = it.next();
            if (next.isNew || next.isEdited) {
                try {
                    String str = this.c + File.separator + next.resName;
                    String str2 = next.isNinePatch() ? str + ".9.png" : str + ".png";
                    if (next.savedPos != 1 && next.savedPos == 0) {
                        strB = b(next);
                    } else {
                        strB = next.resFullName;
                    }
                    kh.a(strB, str2, next.rotate, next.flipHorizontal, next.flipVertical);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        for (int i = 0; i < this.f.size(); i++) {
            ProjectResourceBean projectResourceBean = this.f.get(i);
            if (projectResourceBean.isNew || projectResourceBean.isEdited) {
                String str3 = projectResourceBean.resName;
                this.f.set(i, new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, projectResourceBean.resName, projectResourceBean.isNinePatch() ? str3 + ".9.png" : str3 + ".png"));
            }
        }
        ma.c(this.b).a(this.f);
        ma.c(this.b).j();
        ma.a(this.b).a(ma.c(this.b));
        ma.a(this.b).c();
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ((GridLayoutManager) this.e.getLayoutManager()).setSpanCount(k());
        this.e.requestLayout();
    }

    private String b(ProjectResourceBean projectResourceBean) {
        return this.c + File.separator + projectResourceBean.resFullName;
    }

    private int k() {
        return ((int) (getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().density)) / 100;
    }

    private void l() {
        Iterator<ProjectResourceBean> it = this.f.iterator();
        while (it.hasNext()) {
            it.next().isSelected = false;
        }
    }

    public void a(boolean z) {
        this.f1220a = z;
        getActivity().invalidateOptionsMenu();
        l();
        if (this.f1220a) {
            this.h.setVisibility(0);
        } else {
            this.h.setVisibility(8);
        }
        this.g.notifyDataSetChanged();
    }

    public ArrayList<ProjectResourceBean> c() {
        return this.f;
    }

    public void a(ProjectResourceBean projectResourceBean, boolean z) {
        this.f.add(projectResourceBean);
        if (z) {
            this.g.notifyItemInserted(this.g.getItemCount());
            e();
        }
    }

    public void a(ArrayList<ProjectResourceBean> arrayList, boolean z) {
        Iterator<ProjectResourceBean> it = arrayList.iterator();
        while (it.hasNext()) {
            this.f.add(it.next());
        }
        if (z) {
            this.g.notifyItemInserted(this.g.getItemCount());
            e();
        }
    }

    public void d() {
        int size = this.f.size();
        while (true) {
            size--;
            if (size >= 0) {
                if (this.f.get(size).isSelected) {
                    this.f.remove(size);
                }
            } else {
                this.g.notifyDataSetChanged();
                return;
            }
        }
    }

    public void e() {
        if (this.f.size() == 0) {
            this.k.setVisibility(0);
            this.e.setVisibility(8);
        } else {
            this.e.setVisibility(0);
            this.k.setVisibility(8);
        }
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 210) {
            if (i2 == -1) {
                ProjectResourceBean projectResourceBean = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, intent.getStringExtra("iconName"), intent.getStringExtra("iconPath"));
                projectResourceBean.savedPos = 2;
                projectResourceBean.isNew = true;
                a(projectResourceBean, true);
                ke.a(getActivity(), kq.a().a(getActivity(), R.string.design_manager_message_add_complete), 0).show();
            }
            return;
        }
        switch (i) {
            case 267:
                if (i2 == -1) {
                    ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra("images");
                    if (parcelableArrayListExtra == null) break;
                    Iterator it = parcelableArrayListExtra.iterator();
                    while (it.hasNext()) {
                        ProjectResourceBean _b = (ProjectResourceBean) it.next();
                        if (_b != null) this.f.add(_b);
                    }
                    this.g.notifyItemRangeInserted(this.f.size() - parcelableArrayListExtra.size(), parcelableArrayListExtra.size());
                    e();
                    ((ManageImageActivity) getActivity()).b().b();
                    ke.a(getActivity(), kq.a().a(getActivity(), R.string.design_manager_message_add_complete), 0).show();
                }
                break;
            case 268:
                if (i2 == -1) {
                    ProjectResourceBean projectResourceBean2 = (ProjectResourceBean) intent.getParcelableExtra("image");
                    mb.c();
                    if (projectResourceBean2 != null) {
                        Iterator<ProjectResourceBean> it2 = this.f.iterator();
                        while (it2.hasNext()) {
                            ProjectResourceBean next = it2.next();
                            if (next.resName.equals(projectResourceBean2.resName)) {
                                next.copy(projectResourceBean2);
                                this.g.notifyItemChanged(this.f.indexOf(next));
                                break;
                            }
                        }
                    }
                    e();
                    ((ManageImageActivity) getActivity()).b().b();
                    ke.a(getActivity(), kq.a().a(getActivity(), R.string.design_manager_message_edit_complete), 0).show();
                }
                break;
        }
    }

    class a extends RecyclerView.Adapter<a.C0058a> {

        int f1221a = -1;

        public a(RecyclerView recyclerView) {
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView2, int i, int i2) {
                        super.onScrolled(recyclerView2, i, i2);
                        if (i2 > 2) {
                            if (ManageImageProjectFragment.this.l.isEnabled()) {
                                ManageImageProjectFragment.this.l.hide();
                            }
                        } else {
                            if (i2 >= -2 || !ManageImageProjectFragment.this.l.isEnabled()) {
                                return;
                            }
                            ManageImageProjectFragment.this.l.show();
                        }
                    }
                });
            }
        }

        @Override
        public C0058a onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new C0058a(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.manage_image_list_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(C0058a c0058a, int i) {
            if (ManageImageProjectFragment.this.f1220a) {
                c0058a.f.setVisibility(0);
            } else {
                c0058a.f.setVisibility(8);
            }
            if (((ProjectResourceBean) ManageImageProjectFragment.this.f.get(i)).isNinePatch()) {
                c0058a.e.setVisibility(0);
            } else {
                c0058a.e.setVisibility(8);
            }
            if (((ProjectResourceBean) ManageImageProjectFragment.this.f.get(i)).isSelected) {
                c0058a.d.setImageResource(R.drawable.ic_checkmark_green_48dp);
            } else {
                c0058a.d.setImageResource(R.drawable.ic_trashcan_white_48dp);
            }
            c0058a.f1225a.setChecked(((ProjectResourceBean) ManageImageProjectFragment.this.f.get(i)).isSelected);
            c0058a.b.setText(((ProjectResourceBean) ManageImageProjectFragment.this.f.get(i)).resName);
            if (((ProjectResourceBean) ManageImageProjectFragment.this.f.get(i)).savedPos == 0) {
                final int i2 = ((ProjectResourceBean) ManageImageProjectFragment.this.f.get(i)).rotate;
                final int i3 = ((ProjectResourceBean) ManageImageProjectFragment.this.f.get(i)).flipVertical;
                final int i4 = ((ProjectResourceBean) ManageImageProjectFragment.this.f.get(i)).flipHorizontal;
                Glide.with(ManageImageProjectFragment.this.getActivity()).asBitmap().load(ManageImageProjectFragment.this.c + File.separator + ((ProjectResourceBean) ManageImageProjectFragment.this.f.get(i)).resFullName).centerCrop().signature((Key) mb.b()).error(R.drawable.ic_remove_grey600_24dp).into(new BitmapImageViewTarget(c0058a.c) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> glideAnimation) {
                        super.onResourceReady(kh.a(bitmap, i2, i4, i3), glideAnimation);
                    }
                });
                return;
            }
            final int i5 = ((ProjectResourceBean) ManageImageProjectFragment.this.f.get(i)).rotate;
            final int i6 = ((ProjectResourceBean) ManageImageProjectFragment.this.f.get(i)).flipVertical;
            final int i7 = ((ProjectResourceBean) ManageImageProjectFragment.this.f.get(i)).flipHorizontal;
            Glide.with(ManageImageProjectFragment.this.getActivity()).asBitmap().load(((ProjectResourceBean) ManageImageProjectFragment.this.f.get(i)).resFullName).centerCrop().signature((Key) mb.b()).error(R.drawable.ic_remove_grey600_24dp).into(new BitmapImageViewTarget(c0058a.c) {
                @Override
                public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> glideAnimation) {
                    super.onResourceReady(kh.a(bitmap, i5, i7, i6), glideAnimation);
                }
            });
        }

        @Override
        public int getItemCount() {
            return ManageImageProjectFragment.this.f.size();
        }

        class C0058a extends RecyclerView.ViewHolder {

            public CheckBox f1225a;
            public TextView b;
            public ImageView c;
            public ImageView d;
            public ImageView e;
            public LinearLayout f;

            public C0058a(View view) {
                super(view);
                this.f1225a = (CheckBox) view.findViewById(R.id.chk_select);
                this.b = (TextView) view.findViewById(R.id.tv_image_name);
                this.c = (ImageView) view.findViewById(R.id.img);
                this.d = (ImageView) view.findViewById(R.id.img_delete);
                this.e = (ImageView) view.findViewById(R.id.img_nine_patch);
                this.f = (LinearLayout) view.findViewById(R.id.delete_img_container);
                this.c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view2) {
                        a.this.f1221a = C0058a.this.getLayoutPosition();
                        if (!ManageImageProjectFragment.this.f1220a) {
                            ManageImageProjectFragment.this.a((ProjectResourceBean) ManageImageProjectFragment.this.f.get(C0058a.this.getLayoutPosition()));
                            return;
                        }
                        C0058a.this.f1225a.setChecked(!C0058a.this.f1225a.isChecked());
                        ((ProjectResourceBean) ManageImageProjectFragment.this.f.get(a.this.f1221a)).isSelected = C0058a.this.f1225a.isChecked();
                        a.this.notifyItemChanged(a.this.f1221a);
                    }
                });
                this.c.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view2) {
                        ManageImageProjectFragment.this.a(true);
                        a.this.f1221a = C0058a.this.getLayoutPosition();
                        C0058a.this.f1225a.setChecked(!C0058a.this.f1225a.isChecked());
                        ((ProjectResourceBean) ManageImageProjectFragment.this.f.get(a.this.f1221a)).isSelected = C0058a.this.f1225a.isChecked();
                        return true;
                    }
                });
            }
        }
    }
}
