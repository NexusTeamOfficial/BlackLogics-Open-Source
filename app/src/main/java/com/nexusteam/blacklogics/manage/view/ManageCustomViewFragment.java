package com.nexusteam.blacklogics.manage.view;

import com.nexusteam.internal.ez;
import com.nexusteam.internal.fe;
import com.nexusteam.internal.ki;
import com.nexusteam.internal.kq;
import com.nexusteam.internal.lw;
import com.nexusteam.internal.ma;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import com.google.android.material.color.MaterialColors;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nexusteam.blacklogics.R;
import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.internal.beans.ViewBean;
import com.nexusteam.internal.lib.base.BaseFragment;
import java.util.ArrayList;
import java.util.Iterator;

public class ManageCustomViewFragment extends BaseFragment {

    private RecyclerView f1360a;
    private String b;
    private ArrayList<ProjectFileBean> c;
    private TextView f;
    private a d = null;
    private Boolean e = false;
    private int[] g = new int[19];

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        ViewGroup viewGroup2 = (ViewGroup) layoutInflater.inflate(R.layout.fr_manage_view_list, viewGroup, false);
        this.c = new ArrayList<>();
        this.f1360a = (RecyclerView) viewGroup2.findViewById(R.id.list_activities);
        this.f1360a.setHasFixedSize(true);
        this.f1360a.setLayoutManager(new LinearLayoutManager(getContext()));
        this.d = new a(this.f1360a);
        this.f1360a.setAdapter(this.d);
        if (bundle == null) {
            this.b = getActivity().getIntent().getStringExtra("sc_id");
        } else {
            this.b = bundle.getString("sc_id");
        }
        this.f = (TextView) viewGroup2.findViewById(R.id.tv_guide);
        this.f.setText(kq.a().a(getActivity(), R.string.design_manager_view_description_guide_create_custom_view));
        return viewGroup2;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("sc_id", this.b);
        bundle.putParcelableArrayList("custom_views", this.c);
        super.onSaveInstanceState(bundle);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        if (bundle == null) {
            a();
        } else {
            this.b = bundle.getString("sc_id");
            this.c = bundle.getParcelableArrayList("custom_views");
        }
        this.f1360a.getAdapter().notifyDataSetChanged();
        d();
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        switch (i) {
            case 277:
            case 278:
                if (i2 == -1) {
                    ProjectFileBean projectFileBean = this.c.get(this.d.f1361a);
                    ArrayList<ViewBean> arrayListA = ma.a(this.b).a(projectFileBean.getXmlName());
                    for (int size = arrayListA.size() - 1; size >= 0; size--) {
                        ma.a(this.b).a(projectFileBean, arrayListA.get(size));
                    }
                    ArrayList<ViewBean> arrayListA2 = a(((ProjectFileBean) intent.getParcelableExtra("preset_data")).presetName, i);
                    ma.a(this.b);
                    for (ViewBean viewBean : lw.a(arrayListA2)) {
                        viewBean.id = a(viewBean.type, projectFileBean.getXmlName());
                        ma.a(this.b).b(projectFileBean.getXmlName(), viewBean);
                        if (viewBean.type == 3 && projectFileBean.fileType == 0) {
                            ma.a(this.b).a(projectFileBean.getJavaName(), 1, viewBean.type, viewBean.id, "onClick");
                        }
                    }
                    this.d.notifyItemChanged(this.d.f1361a);
                }
                break;
        }
    }

    private ArrayList<ViewBean> a(String str, int i) {
        ArrayList<ViewBean> arrayList = new ArrayList<>();
        switch (i) {
            case 277:
                return ez.b(str);
            case 278:
                return ez.c(str);
            default:
                return arrayList;
        }
    }

    private String a(int i, String str) {
        String strA = fe.a(i);
        StringBuilder sb = new StringBuilder();
        sb.append(strA);
        int[] iArr = this.g;
        int i2 = iArr[i] + 1;
        iArr[i] = i2;
        sb.append(i2);
        String string = sb.toString();
        ArrayList<ViewBean> arrayListA = ma.a(this.b).a(str);
        while (true) {
            boolean z = false;
            Iterator<ViewBean> it = arrayListA.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (string.equals(it.next().id)) {
                    z = true;
                    break;
                }
            }
            if (!z) {
                return string;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(strA);
            int[] iArr2 = this.g;
            int i3 = iArr2[i] + 1;
            iArr2[i] = i3;
            sb2.append(i3);
            string = sb2.toString();
        }
    }

    public void a() {
        ArrayList<ProjectFileBean> arrayListB = ma.b(this.b).b();
        if (arrayListB == null) {
            return;
        }
        Iterator<ProjectFileBean> it = arrayListB.iterator();
        while (it.hasNext()) {
            this.c.add(it.next());
        }
    }

    public void a(boolean z) {
        this.e = Boolean.valueOf(z);
        e();
        this.d.notifyDataSetChanged();
    }

    private void e() {
        Iterator<ProjectFileBean> it = this.c.iterator();
        while (it.hasNext()) {
            it.next().isSelected = false;
        }
    }

    public void a(String str) {
        boolean z;
        Iterator<ProjectFileBean> it = this.c.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            ProjectFileBean next = it.next();
            if (next.fileType == 2 && next.fileName.equals(str)) {
                z = true;
                break;
            }
        }
        if (z) {
            return;
        }
        this.c.add(new ProjectFileBean(2, str));
        this.d.notifyDataSetChanged();
    }

    public void b(String str) {
        Iterator<ProjectFileBean> it = this.c.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ProjectFileBean next = it.next();
            if (next.fileType == 2 && next.fileName.equals(str)) {
                this.c.remove(next);
                break;
            }
        }
        this.d.notifyDataSetChanged();
    }

    public ArrayList<ProjectFileBean> b() {
        return this.c;
    }

    public void a(ProjectFileBean projectFileBean) {
        this.c.add(projectFileBean);
        this.d.notifyDataSetChanged();
    }

    public void c() {
        int size = this.c.size();
        while (true) {
            size--;
            if (size >= 0) {
                if (this.c.get(size).isSelected) {
                    this.c.remove(size);
                }
            } else {
                this.d.notifyDataSetChanged();
                return;
            }
        }
    }

    public void d() {
        if (this.c != null) {
            if (this.c.size() == 0) {
                this.f.setVisibility(0);
                this.f1360a.setVisibility(8);
            } else {
                this.f.setVisibility(8);
                this.f1360a.setVisibility(0);
            }
        }
    }

    class a extends RecyclerView.Adapter<a.C0063a> {

        int f1361a = -1;

        public a(RecyclerView recyclerView) {
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView2, int i, int i2) {
                        super.onScrolled(recyclerView2, i, i2);
                        if (i2 > 2) {
                            if (((ManageViewActivity) ManageCustomViewFragment.this.getActivity()).f1367a.isEnabled()) {
                                ((ManageViewActivity) ManageCustomViewFragment.this.getActivity()).f1367a.hide();
                            }
                        } else {
                            if (i2 >= -2 || !((ManageViewActivity) ManageCustomViewFragment.this.getActivity()).f1367a.isEnabled()) {
                                return;
                            }
                            ((ManageViewActivity) ManageCustomViewFragment.this.getActivity()).f1367a.show();
                        }
                    }
                });
            }
        }

        @Override
        public C0063a onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new C0063a(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.manage_view_custom_list_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(C0063a c0063a, int i) {
            if (ManageCustomViewFragment.this.e.booleanValue()) {
                c0063a.e.setVisibility(0);
                c0063a.b.setVisibility(8);
            } else {
                c0063a.e.setVisibility(8);
                c0063a.b.setVisibility(0);
            }
            ProjectFileBean projectFileBean = (ProjectFileBean) ManageCustomViewFragment.this.c.get(i);
            c0063a.b.setImageResource(R.drawable.activity_preset_1);
            c0063a.f1363a.setChecked(projectFileBean.isSelected);
            if (projectFileBean.fileType == 1) {
                c0063a.d.setText(projectFileBean.getXmlName());
                c0063a.d.setTextColor(MaterialColors.getColor(c0063a.d, com.google.android.material.R.attr.colorOnSurface));
            } else if (projectFileBean.fileType == 2) {
                c0063a.f1363a.setVisibility(8);
                c0063a.b.setImageResource(R.drawable.activity_0110);
                c0063a.d.setText(projectFileBean.fileName.substring(1));
                c0063a.d.setTextColor(ManageCustomViewFragment.this.getResources().getColor(R.color.scolor_red_02));
            }
            if (projectFileBean.isSelected) {
                c0063a.c.setImageResource(R.drawable.ic_checkmark_green_48dp);
            } else {
                c0063a.c.setImageResource(R.drawable.ic_trashcan_white_48dp);
            }
        }

        @Override
        public int getItemCount() {
            if (ManageCustomViewFragment.this.c != null) {
                return ManageCustomViewFragment.this.c.size();
            }
            return 0;
        }

        class C0063a extends RecyclerView.ViewHolder {

            public CheckBox f1363a;
            public ImageView b;
            public ImageView c;
            public TextView d;
            public LinearLayout e;
            public ImageView f;

            public C0063a(View view) {
                super(view);
                this.f1363a = (CheckBox) view.findViewById(R.id.chk_select);
                this.b = (ImageView) view.findViewById(R.id.img_activity);
                this.d = (TextView) view.findViewById(R.id.tv_screen_name);
                this.e = (LinearLayout) view.findViewById(R.id.delete_img_container);
                this.c = (ImageView) view.findViewById(R.id.img_delete);
                this.f = (ImageView) view.findViewById(R.id.img_preset_setting);
                this.f1363a.setVisibility(8);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view2) {
                        a.this.f1361a = C0063a.this.getLayoutPosition();
                        if (ManageCustomViewFragment.this.e.booleanValue()) {
                            C0063a.this.f1363a.setChecked(!C0063a.this.f1363a.isChecked());
                            ((ProjectFileBean) ManageCustomViewFragment.this.c.get(a.this.f1361a)).isSelected = C0063a.this.f1363a.isChecked();
                            a.this.notifyItemChanged(a.this.f1361a);
                        }
                    }
                });
                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view2) {
                        ((ManageViewActivity) ManageCustomViewFragment.this.getActivity()).a(true);
                        a.this.f1361a = C0063a.this.getLayoutPosition();
                        C0063a.this.f1363a.setChecked(!C0063a.this.f1363a.isChecked());
                        ((ProjectFileBean) ManageCustomViewFragment.this.c.get(a.this.f1361a)).isSelected = C0063a.this.f1363a.isChecked();
                        return true;
                    }
                });
                this.f.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view2) {
                        if (ki.a()) {
                            return;
                        }
                        a.this.f1361a = C0063a.this.getLayoutPosition();
                        Intent intent = new Intent(ManageCustomViewFragment.this.getActivity(), (Class<?>) PresetSettingActivity.class);
                        int i = ((ProjectFileBean) ManageCustomViewFragment.this.c.get(a.this.f1361a)).fileType == 1 ? 277 : 278;
                        intent.putExtra("request_code", i);
                        intent.putExtra("edit_mode", true);
                        ManageCustomViewFragment.this.startActivityForResult(intent, i);
                    }
                });
            }
        }
    }
}
