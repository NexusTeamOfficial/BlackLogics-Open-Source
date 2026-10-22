package com.nexusteam.blacklogics.manage.view;

import com.nexusteam.internal.ez;
import com.nexusteam.internal.fe;
import com.nexusteam.internal.ki;
import com.nexusteam.internal.kq;
import com.nexusteam.internal.lw;
import com.nexusteam.internal.ma;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.nexusteam.internal.beans.ProjectLibraryBean;
import com.nexusteam.internal.beans.ViewBean;
import com.nexusteam.internal.lib.base.BaseFragment;
import java.util.ArrayList;
import java.util.Iterator;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;

public class ManageViewFragment extends BaseFragment {

    private RecyclerView f1372a;
    private String b;
    private ArrayList<ProjectFileBean> d;
    private TextView g;
    private String c = ProjectLibraryBean.LIB_USE_N;
    private a e = null;
    private Boolean f = false;
    private int[] h = new int[19];

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        ViewGroup viewGroup2 = (ViewGroup) layoutInflater.inflate(R.layout.fr_manage_view_list, viewGroup, false);
        this.d = new ArrayList<>();
        this.f1372a = (RecyclerView) viewGroup2.findViewById(R.id.list_activities);
        this.f1372a.setHasFixedSize(true);
        this.f1372a.setLayoutManager(new LinearLayoutManager(getContext()));
        this.e = new a(this.f1372a);
        this.f1372a.setAdapter(this.e);
        this.g = (TextView) viewGroup2.findViewById(R.id.tv_guide);
        this.g.setText(kq.a().a(getActivity(), R.string.design_manager_view_description_guide_create_activity));
        return viewGroup2;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("sc_id", this.b);
        bundle.putString("compatUseYn", this.c);
        bundle.putParcelableArrayList("activities", this.d);
        super.onSaveInstanceState(bundle);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        if (bundle == null) {
            a();
        } else {
            this.b = bundle.getString("sc_id");
            this.c = bundle.getString("compatUseYn");
            this.d = bundle.getParcelableArrayList("activities");
        }
        this.e.notifyDataSetChanged();
        d();
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 265) {
            if (i2 == -1) {
                b((ProjectFileBean) intent.getParcelableExtra("project_file"));
                this.e.notifyItemChanged(this.e.f1373a);
                return;
            }
            return;
        }
        if (i == 276 && i2 == -1) {
            ProjectFileBean projectFileBean = (ProjectFileBean) intent.getParcelableExtra("preset_data");
            b(projectFileBean);
            c(projectFileBean);
            this.e.notifyItemChanged(this.e.f1373a);
        }
    }

    private void b(ProjectFileBean projectFileBean) {
        ProjectFileBean projectFileBean2 = this.d.get(this.e.f1373a);
        projectFileBean2.keyboardSetting = projectFileBean.keyboardSetting;
        projectFileBean2.orientation = projectFileBean.orientation;
        projectFileBean2.options = projectFileBean.options;
        if (projectFileBean.hasActivityOption(4)) {
            ((ManageViewActivity) getActivity()).a(ProjectFileBean.getDrawerName(projectFileBean2.fileName));
        } else {
            ((ManageViewActivity) getActivity()).b(ProjectFileBean.getDrawerName(projectFileBean2.fileName));
        }
        if (projectFileBean.hasActivityOption(4) || projectFileBean.hasActivityOption(8)) {
            ma.d(this.b).d().useYn = ProjectLibraryBean.LIB_USE_Y;
        }
    }

    private void c(ProjectFileBean projectFileBean) {
        ProjectFileBean projectFileBean2 = this.d.get(this.e.f1373a);
        ArrayList<ViewBean> arrayListA = ma.a(this.b).a(projectFileBean2.getXmlName());
        for (int size = arrayListA.size() - 1; size >= 0; size--) {
            ma.a(this.b).a(projectFileBean2, arrayListA.get(size));
        }
        ArrayList<ViewBean> arrayListA2 = a(projectFileBean.presetName);
        ma.a(this.b);
        for (ViewBean viewBean : lw.a(arrayListA2)) {
            viewBean.id = a(viewBean.type, projectFileBean2.getXmlName());
            ma.a(this.b).b(projectFileBean2.getXmlName(), viewBean);
            if (viewBean.type == 3 && projectFileBean2.fileType == 0) {
                ma.a(this.b).a(projectFileBean2.getJavaName(), 1, viewBean.type, viewBean.id, "onClick");
            }
        }
    }

    private ArrayList<ViewBean> a(String str) {
        return ez.a(str);
    }

    private String a(int i, String str) {
        String strA = fe.a(i);
        StringBuilder sb = new StringBuilder();
        sb.append(strA);
        int[] iArr = this.h;
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
            int[] iArr2 = this.h;
            int i3 = iArr2[i] + 1;
            iArr2[i] = i3;
            sb2.append(i3);
            string = sb2.toString();
        }
    }

    public void a() {
        this.b = getActivity().getIntent().getStringExtra("sc_id");
        this.c = getActivity().getIntent().getStringExtra("compatUseYn");
        ArrayList<ProjectFileBean> arrayListA = ma.b(this.b).a();
        if (arrayListA == null) {
            return;
        }
        boolean z = false;
        for (ProjectFileBean projectFileBean : arrayListA) {
            if (projectFileBean.fileName.equals("main")) {
                this.d.add(0, projectFileBean);
                z = true;
            } else {
                this.d.add(projectFileBean);
            }
        }
        if (z) {
            return;
        }
        this.d.add(0, new ProjectFileBean(0, "main"));
    }

    class a extends RecyclerView.Adapter<a.C0064a> {

        int f1373a = -1;

        public a(RecyclerView recyclerView) {
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView2, int i, int i2) {
                        super.onScrolled(recyclerView2, i, i2);
                        if (i2 > 2) {
                            if (((ManageViewActivity) ManageViewFragment.this.getActivity()).f1367a.isEnabled()) {
                                ((ManageViewActivity) ManageViewFragment.this.getActivity()).f1367a.hide();
                            }
                        } else {
                            if (i2 >= -2 || !((ManageViewActivity) ManageViewFragment.this.getActivity()).f1367a.isEnabled()) {
                                return;
                            }
                            ((ManageViewActivity) ManageViewFragment.this.getActivity()).f1367a.show();
                        }
                    }
                });
            }
        }

        @Override
        public C0064a onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new C0064a(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.manage_view_list_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(C0064a c0064a, int i) {
            c0064a.c.setVisibility(0);
            c0064a.f.setVisibility(8);
            if (i != 0) {
                if (ManageViewFragment.this.f.booleanValue()) {
                    c0064a.f.setVisibility(0);
                    c0064a.c.setVisibility(8);
                } else {
                    c0064a.f.setVisibility(8);
                    c0064a.c.setVisibility(0);
                }
            } else {
                c0064a.f1375a.setVisibility(8);
            }
            ProjectFileBean projectFileBean = (ProjectFileBean) ManageViewFragment.this.d.get(i);
            c0064a.c.setImageResource(a(projectFileBean.options));
            c0064a.d.setText(projectFileBean.getXmlName());
            c0064a.e.setText(projectFileBean.getJavaName());
            if (projectFileBean.isSelected) {
                c0064a.g.setImageResource(R.drawable.ic_checkmark_green_48dp);
            } else {
                c0064a.g.setImageResource(R.drawable.ic_trashcan_white_48dp);
            }
        }

        @Override
        public int getItemCount() {
            if (ManageViewFragment.this.d != null) {
                return ManageViewFragment.this.d.size();
            }
            return 0;
        }

        class C0064a extends RecyclerView.ViewHolder {

            public CheckBox f1375a;
            public View b;
            public ImageView c;
            public TextView d;
            public TextView e;
            public LinearLayout f;
            public ImageView g;
            public ImageView h;

            public C0064a(View view) {
                super(view);
                this.f1375a = (CheckBox) view.findViewById(R.id.chk_select);
                this.b = view.findViewById(R.id.view_item);
                this.c = (ImageView) view.findViewById(R.id.img_activity);
                this.d = (TextView) view.findViewById(R.id.tv_screen_name);
                this.e = (TextView) view.findViewById(R.id.tv_activity_name);
                this.f = (LinearLayout) view.findViewById(R.id.delete_img_container);
                this.g = (ImageView) view.findViewById(R.id.img_delete);
                this.h = (ImageView) view.findViewById(R.id.img_preset_setting);
                this.f1375a.setVisibility(8);
                this.b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view2) {
                        if (ki.a()) {
                            return;
                        }
                        a.this.f1373a = C0064a.this.getLayoutPosition();
                        if (ManageViewFragment.this.f.booleanValue()) {
                            if (a.this.f1373a == 0) {
                                return;
                            }
                            C0064a.this.f1375a.setChecked(!C0064a.this.f1375a.isChecked());
                            ((ProjectFileBean) ManageViewFragment.this.d.get(a.this.f1373a)).isSelected = C0064a.this.f1375a.isChecked();
                            a.this.notifyItemChanged(a.this.f1373a);
                            return;
                        }
                        Intent intent = new Intent(ManageViewFragment.this.getContext(), (Class<?>) AddViewActivity.class);
                        intent.putExtra("project_file", (Parcelable) ManageViewFragment.this.d.get(a.this.f1373a));
                        intent.putExtra("request_code", 265);
                        ManageViewFragment.this.startActivityForResult(intent, 265);
                    }
                });
                this.b.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view2) {
                        ((ManageViewActivity) ManageViewFragment.this.getActivity()).a(true);
                        a.this.f1373a = C0064a.this.getLayoutPosition();
                        C0064a.this.f1375a.setChecked(!C0064a.this.f1375a.isChecked());
                        ((ProjectFileBean) ManageViewFragment.this.d.get(a.this.f1373a)).isSelected = C0064a.this.f1375a.isChecked();
                        return true;
                    }
                });
                this.h.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view2) {
                        if (ki.a()) {
                            return;
                        }
                        a.this.f1373a = C0064a.this.getLayoutPosition();
                        Intent intent = new Intent(ManageViewFragment.this.getContext(), (Class<?>) PresetSettingActivity.class);
                        intent.putExtra("request_code", 276);
                        intent.putExtra("edit_mode", true);
                        ManageViewFragment.this.startActivityForResult(intent, 276);
                    }
                });
            }
        }

        private int a(int i) {
            String strReplace = String.format("%4s", Integer.toBinaryString(i)).replace(' ', ExternalAnnotationProvider.NULLABLE);
            return ManageViewFragment.this.getContext().getResources().getIdentifier("activity_" + strReplace, "drawable", ManageViewFragment.this.getContext().getPackageName());
        }
    }

    public void a(boolean z) {
        this.f = Boolean.valueOf(z);
        e();
        this.e.notifyDataSetChanged();
    }

    private void e() {
        Iterator<ProjectFileBean> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().isSelected = false;
        }
    }

    public ArrayList<ProjectFileBean> b() {
        return this.d;
    }

    public void a(ProjectFileBean projectFileBean) {
        this.d.add(projectFileBean);
        this.e.notifyDataSetChanged();
    }

    public void c() {
        int size = this.d.size();
        while (true) {
            size--;
            if (size >= 0) {
                ProjectFileBean projectFileBean = this.d.get(size);
                if (projectFileBean.isSelected) {
                    this.d.remove(size);
                    if (projectFileBean.hasActivityOption(4)) {
                        ((ManageViewActivity) getActivity()).b(ProjectFileBean.getDrawerName(projectFileBean.fileName));
                    }
                }
            } else {
                this.e.notifyDataSetChanged();
                return;
            }
        }
    }

    public void d() {
        if (this.d != null) {
            if (this.d.size() == 0) {
                this.g.setVisibility(0);
                this.f1372a.setVisibility(8);
            } else {
                this.f1372a.setVisibility(0);
                this.g.setVisibility(8);
            }
        }
    }
}
