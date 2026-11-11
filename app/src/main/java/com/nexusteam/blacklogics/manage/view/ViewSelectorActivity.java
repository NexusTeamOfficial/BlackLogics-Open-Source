package com.nexusteam.blacklogics.manage.view;

import com.nexusteam.internal.ez;
import com.nexusteam.internal.fe;
import com.nexusteam.internal.jy;
import com.nexusteam.internal.ki;
import com.nexusteam.internal.kq;
import com.nexusteam.internal.lw;
import com.nexusteam.internal.ma;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nexusteam.blacklogics.R;
import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.internal.beans.ProjectLibraryBean;
import com.nexusteam.internal.beans.ViewBean;
import com.nexusteam.blacklogics.manage.view.AddCustomViewActivity;
import com.nexusteam.blacklogics.manage.view.AddViewActivity;
import com.nexusteam.blacklogics.manage.view.PresetSettingActivity;
import com.nexusteam.blacklogics.activityfile.ActivitySyncBridge;
import com.nexusteam.blacklogics.development.Complex;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.color.MaterialColors;
import com.nexusteam.internal.lib.ui.SelectableButtonBar;
import java.util.ArrayList;
import java.util.Iterator;

public class ViewSelectorActivity extends AppCompatActivity {

    LinearLayout f1160a;
    RecyclerView b;
    a c;
    String d;
    ProjectFileBean e;
    SelectableButtonBar f;
    TextView g;
    ImageView h;
    String i;
    int j;
    private final int l = 0;
    private final int m = 1;
    boolean k = false;
    private int[] n = new int[19];
    private Button extraAddBtn;

    @Override
    public void onCreate(Bundle bundle) {
        boolean night = (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES;
        setTheme(night ? R.style.AppTheme_DialogActivity_Dark : R.style.AppTheme_DialogActivity);
        super.onCreate(bundle);

        Window w = getWindow();
        if (w != null) {
            w.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            w.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            w.setStatusBarColor(Color.TRANSPARENT);
            w.setNavigationBarColor(Color.TRANSPARENT);
            w.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

        setContentView(R.layout.file_selector_popup_select_xml);
        Intent intent = getIntent();
        if (bundle == null) {
            this.d = intent.getStringExtra("sc_id");
            this.i = intent.getStringExtra("current_xml");
            this.k = intent.getBooleanExtra("is_custom_view", false);
        } else {
            this.d = bundle.getString("sc_id");
            this.i = bundle.getString("current_xml");
            this.k = bundle.getBoolean("is_custom_view");
        }
        if (this.k) {
            this.j = 1;
        } else {
            this.j = 0;
        }
        this.f = (SelectableButtonBar) findViewById(R.id.button_bar);
        this.g = (TextView) findViewById(R.id.empty_message);
        this.b = (RecyclerView) findViewById(R.id.list_xml);
        this.h = (ImageView) findViewById(R.id.add_button);
        this.f1160a = (LinearLayout) findViewById(R.id.container);
        this.f.a(0, kq.a().a(this, R.string.common_word_view).toUpperCase());
        this.f.a(1, kq.a().a(this, R.string.common_word_custom_view).toUpperCase());
        this.f.setSelectedItemByIndex(this.j);
        this.f.a();
        this.c = new a();
        this.b.setLayoutManager(new LinearLayoutManager(getApplicationContext(), 1, false));
        this.b.setHasFixedSize(true);
        this.b.setAdapter(this.c);
        ensureDefaultActivityListed();
        this.f.setListener(new jy() {
            @Override
            public void a(int i) {
                if (i == ViewSelectorActivity.this.j) {
                    return;
                }
                ViewSelectorActivity.this.j = i;
                ViewSelectorActivity.this.c.notifyDataSetChanged();
                if (ViewSelectorActivity.this.c.getItemCount() == 0) {
                    ViewSelectorActivity.this.g.setVisibility(0);
                } else {
                    ViewSelectorActivity.this.g.setVisibility(8);
                }
                ViewSelectorActivity.this.ensureAddButtonVisible();
            }
        });
        try {
            this.g.setText(kq.a().a(this, R.string.design_manager_view_message_no_view));
        } catch (Throwable ignore) {}
        ensureAddButtonVisible();
        View.OnClickListener addClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddScreen();
            }
        };
        if (this.h != null) {
            this.h.setOnClickListener(addClick);
        }
        if (this.f1160a != null) {
            this.f1160a.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view == ViewSelectorActivity.this.f1160a) {
                        ViewSelectorActivity.this.finish();
                    }
                }
            });
        }
        overridePendingTransition(R.anim.ani_fade_in, R.anim.ani_fade_out);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("sc_id", this.d);
        bundle.putString("current_xml", this.i);
        bundle.putBoolean("is_custom_view", this.k);
        super.onSaveInstanceState(bundle);
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        switch (i) {
            case 264:
                if (i2 == -1) {
                    ProjectFileBean projectFileBean = (ProjectFileBean) intent.getParcelableExtra("project_file");
                    ma.b(this.d).a(projectFileBean);
                    if (projectFileBean.hasActivityOption(4)) {
                        ma.b(this.d).a(2, projectFileBean.getDrawerName());
                    }
                    if (intent.hasExtra("preset_views")) {
                        a(projectFileBean, (ArrayList) intent.getParcelableArrayListExtra("preset_views"));
                    }
                    ma.b(this.d).l();
                    ma.b(this.d).g();
                    syncActivityToConfig(projectFileBean);
                    this.c.notifyDataSetChanged();
                    Intent ok264 = new Intent();
                    ok264.putExtra("project_file", projectFileBean);
                    setResult(-1, ok264);
                }
                break;
            case 265:
                if (i2 == -1) {
                    ProjectFileBean projectFileBean2 = (ProjectFileBean) intent.getParcelableExtra("project_file");
                    ProjectFileBean projectFileBean3 = ma.b(this.d).a().get(this.c.f1164a);
                    projectFileBean3.keyboardSetting = projectFileBean2.keyboardSetting;
                    projectFileBean3.orientation = projectFileBean2.orientation;
                    projectFileBean3.options = projectFileBean2.options;
                    if (projectFileBean2.hasActivityOption(4)) {
                        ma.b(this.d).a(2, projectFileBean2.getDrawerName());
                    } else {
                        ma.b(this.d).b(2, projectFileBean2.getDrawerName());
                    }
                    if (projectFileBean2.hasActivityOption(4) || projectFileBean2.hasActivityOption(8)) {
                        ma.d(this.d).d().useYn = ProjectLibraryBean.LIB_USE_Y;
                    }
                    ma.b(this.d).l();
                    syncActivityToConfig(projectFileBean3);
                    this.c.notifyItemChanged(this.c.f1164a);
                    Intent intent2 = new Intent();
                    intent2.putExtra("project_file", projectFileBean3);
                    setResult(-1, intent2);
                }
                break;
            case 266:
                if (i2 == -1) {
                    ProjectFileBean projectFileBean4 = (ProjectFileBean) intent.getParcelableExtra("project_file");
                    ma.b(this.d).a(projectFileBean4);
                    if (intent.hasExtra("preset_views")) {
                        a(projectFileBean4, (ArrayList) intent.getParcelableArrayListExtra("preset_views"));
                    }
                    ma.b(this.d).l();
                    ma.b(this.d).g();
                    syncActivityToConfig(projectFileBean4);
                    this.c.notifyDataSetChanged();
                    Intent ok266 = new Intent();
                    ok266.putExtra("project_file", projectFileBean4);
                    setResult(-1, ok266);
                }
                break;
            default:
                switch (i) {
                    case 276:
                        if (i2 == -1) {
                            ProjectFileBean projectFileBean5 = (ProjectFileBean) intent.getParcelableExtra("preset_data");
                            ProjectFileBean projectFileBean6 = ma.b(this.d).a().get(this.c.f1164a);
                            projectFileBean6.keyboardSetting = projectFileBean5.keyboardSetting;
                            projectFileBean6.orientation = projectFileBean5.orientation;
                            projectFileBean6.options = projectFileBean5.options;
                            if (projectFileBean5.hasActivityOption(4) || projectFileBean5.hasActivityOption(8)) {
                                ma.d(this.d).d().useYn = ProjectLibraryBean.LIB_USE_Y;
                            }
                            a(projectFileBean5, projectFileBean6, i);
                            ma.b(this.d).l();
                            this.c.notifyDataSetChanged();
                            Intent intent3 = new Intent();
                            intent3.putExtra("project_file", projectFileBean6);
                            setResult(-1, intent3);
                        }
                        break;
                    case 277:
                    case 278:
                        if (i2 == -1) {
                            ProjectFileBean projectFileBean7 = (ProjectFileBean) intent.getParcelableExtra("preset_data");
                            ProjectFileBean projectFileBean8 = ma.b(this.d).b().get(this.c.f1164a);
                            a(projectFileBean7, projectFileBean8, i);
                            ma.b(this.d).l();
                            this.c.notifyDataSetChanged();
                            Intent intent4 = new Intent();
                            intent4.putExtra("project_file", projectFileBean8);
                            setResult(-1, intent4);
                        }
                        break;
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.ani_fade_in, R.anim.ani_fade_out);
    }

    private void a(ProjectFileBean projectFileBean, ArrayList<ViewBean> arrayList) {
        ma.a(this.d);
        for (ViewBean viewBean : lw.a(arrayList)) {
            viewBean.id = a(viewBean.type, projectFileBean.getXmlName());
            ma.a(this.d).b(projectFileBean.getXmlName(), viewBean);
            if (viewBean.type == 3 && projectFileBean.fileType == 0) {
                ma.a(this.d).a(projectFileBean.getJavaName(), 1, viewBean.type, viewBean.id, "onClick");
            }
        }
    }

    private void a(ProjectFileBean projectFileBean, ProjectFileBean projectFileBean2, int i) {
        ArrayList<ViewBean> arrayListA = ma.a(this.d).a(projectFileBean2.getXmlName());
        for (int size = arrayListA.size() - 1; size >= 0; size--) {
            ma.a(this.d).a(projectFileBean2, arrayListA.get(size));
        }
        ArrayList<ViewBean> arrayListA2 = a(projectFileBean.presetName, i);
        ma.a(this.d);
        for (ViewBean viewBean : lw.a(arrayListA2)) {
            viewBean.id = a(viewBean.type, projectFileBean2.getXmlName());
            ma.a(this.d).b(projectFileBean2.getXmlName(), viewBean);
            if (viewBean.type == 3 && projectFileBean2.fileType == 0) {
                ma.a(this.d).a(projectFileBean2.getJavaName(), 1, viewBean.type, viewBean.id, "onClick");
            }
        }
    }

    private ArrayList<ViewBean> a(String str, int i) {
        ArrayList<ViewBean> arrayList = new ArrayList<>();
        switch (i) {
            case 276:
                return ez.a(str);
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
        int[] iArr = this.n;
        int i2 = iArr[i] + 1;
        iArr[i] = i2;
        sb.append(i2);
        String string = sb.toString();
        ArrayList<ViewBean> arrayListA = ma.a(this.d).a(str);
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
            int[] iArr2 = this.n;
            int i3 = iArr2[i] + 1;
            iArr2[i] = i3;
            sb2.append(i3);
            string = sb2.toString();
        }
    }

    public int a(int i) {
        String strReplace = String.format("%4s", Integer.toBinaryString(i)).replace(' ', '0');
        return getApplicationContext().getResources().getIdentifier("activity_" + strReplace, "drawable", getApplicationContext().getPackageName());
    }

    class a extends RecyclerView.Adapter<a.C0052a> {

        int f1164a = -1;

        public a() {
        }

        @Override
        public C0052a onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new C0052a(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.file_selector_popup_select_xml_activity_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(C0052a c0052a, int i) {
            c0052a.f1165a.setBackgroundColor(ViewSelectorActivity.this.getResources().getColor(R.color.transparent));
            if (ViewSelectorActivity.this.j == 0) {
                c0052a.d.setVisibility(0);
                c0052a.e.setVisibility(0);
                ProjectFileBean projectFileBean = ma.b(ViewSelectorActivity.this.d).a().get(i);
                String xmlName = projectFileBean.getXmlName();
                if (ViewSelectorActivity.this.i.equals(xmlName)) {
                    c0052a.f1165a.setBackgroundColor(ViewSelectorActivity.this.getResources().getColor(R.color.scolor_dark_yellow_01));
                }
                String javaName = projectFileBean.getJavaName();
                c0052a.b.setVisibility(0);
                c0052a.c.setImageResource(ViewSelectorActivity.this.a(projectFileBean.options));
                c0052a.d.setText(xmlName);
                c0052a.e.setVisibility(0);
                c0052a.e.setText(javaName);
                c0052a.d.setTextColor(MaterialColors.getColor(c0052a.d, com.google.android.material.R.attr.colorOnSurface));
                return;
            }
            if (ViewSelectorActivity.this.j == 1) {
                c0052a.b.setVisibility(8);
                c0052a.e.setVisibility(8);
                ProjectFileBean projectFileBean2 = ma.b(ViewSelectorActivity.this.d).b().get(i);
                if (ViewSelectorActivity.this.i.equals(projectFileBean2.getXmlName())) {
                    c0052a.f1165a.setBackgroundColor(ViewSelectorActivity.this.getResources().getColor(R.color.scolor_dark_yellow_01));
                }
                if (projectFileBean2.fileType == 2) {
                    c0052a.c.setImageResource(ViewSelectorActivity.this.a(4));
                    c0052a.d.setText(projectFileBean2.fileName.substring(1));
                    c0052a.d.setTextColor(MaterialColors.getColor(c0052a.d, com.google.android.material.R.attr.colorError));
                } else {
                    c0052a.c.setImageResource(ViewSelectorActivity.this.a(3));
                    c0052a.d.setText(projectFileBean2.getXmlName());
                    c0052a.d.setTextColor(MaterialColors.getColor(c0052a.d, com.google.android.material.R.attr.colorOnSurface));
                }
            }
        }

        @Override
        public int getItemCount() {
            int size;
            ViewSelectorActivity.this.g.setVisibility(8);
            if (ViewSelectorActivity.this.j == 0) {
                size = ma.b(ViewSelectorActivity.this.d).a() != null ? ma.b(ViewSelectorActivity.this.d).a().size() : 0;
            } else {
                size = ViewSelectorActivity.this.j == 1 && ma.b(ViewSelectorActivity.this.d).b() != null ? ma.b(ViewSelectorActivity.this.d).b().size() : 0;
            }
            if (size == 0) {
                ViewSelectorActivity.this.g.setVisibility(0);
            }
            return size;
        }

        class C0052a extends RecyclerView.ViewHolder {

            public LinearLayout f1165a;
            public ImageView b;
            public ImageView c;
            public TextView d;
            public TextView e;
            public ImageView f;

            public C0052a(View view) {
                super(view);
                this.f1165a = (LinearLayout) view.findViewById(R.id.container);
                this.b = (ImageView) view.findViewById(R.id.img_edit);
                this.c = (ImageView) view.findViewById(R.id.img_view);
                this.d = (TextView) view.findViewById(R.id.tv_filename);
                this.e = (TextView) view.findViewById(R.id.tv_linked_filename);
                this.f = (ImageView) view.findViewById(R.id.img_preset_setting);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view2) {
                        if (ki.a()) {
                            return;
                        }
                        a.this.f1164a = C0052a.this.getLayoutPosition();
                        if (ViewSelectorActivity.this.j == 0) {
                            ViewSelectorActivity.this.e = ma.b(ViewSelectorActivity.this.d).a().get(C0052a.this.getLayoutPosition());
                        } else if (ViewSelectorActivity.this.j == 1) {
                            ViewSelectorActivity.this.e = ma.b(ViewSelectorActivity.this.d).b().get(C0052a.this.getLayoutPosition());
                        }
                        Intent intent = new Intent();
                        intent.putExtra("project_file", ViewSelectorActivity.this.e);
                        ViewSelectorActivity.this.setResult(-1, intent);
                        ViewSelectorActivity.this.finish();
                    }
                });
                this.c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view2) {
                        if (ViewSelectorActivity.this.j != 0 || ki.a()) {
                            return;
                        }
                        a.this.f1164a = C0052a.this.getLayoutPosition();
                        Intent intent = new Intent(ViewSelectorActivity.this.getApplicationContext(), (Class<?>) AddViewActivity.class);
                        intent.putExtra("project_file", ma.b(ViewSelectorActivity.this.d).a().get(C0052a.this.getLayoutPosition()));
                        intent.putExtra("request_code", 265);
                        ViewSelectorActivity.this.startActivityForResult(intent, 265);
                    }
                });
                this.f.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view2) {
                        if (ki.a()) {
                            return;
                        }
                        a.this.f1164a = C0052a.this.getLayoutPosition();
                        int iA = ViewSelectorActivity.this.a(ma.b(ViewSelectorActivity.this.d).a().get(C0052a.this.getLayoutPosition()));
                        Intent intent = new Intent(ViewSelectorActivity.this.getApplicationContext(), (Class<?>) PresetSettingActivity.class);
                        intent.putExtra("request_code", iA);
                        intent.putExtra("edit_mode", true);
                        ViewSelectorActivity.this.startActivityForResult(intent, iA);
                    }
                });
            }
        }
    }

    private void openAddScreen() {
        if (ki.a()) return;
        try {
            if (this.j == 0) {
                Intent intent2 = new Intent(getApplicationContext(), AddViewActivity.class);
                intent2.putStringArrayListExtra("screen_names", a());
                intent2.putExtra("request_code", 264);
                startActivityForResult(intent2, 264);
            } else {
                Intent intent3 = new Intent(getApplicationContext(), AddCustomViewActivity.class);
                intent3.putStringArrayListExtra("screen_names", a());
                startActivityForResult(intent3, 266);
            }
        } catch (Throwable t) {
            android.util.Log.e("ViewSelector", "openAddScreen", t);
        }
    }

    void ensureAddButtonVisible() {
        try {
            if (this.h != null) {
                this.h.setVisibility(View.VISIBLE);
                this.h.setEnabled(true);
                this.h.setClickable(true);
                this.h.bringToFront();
                try { this.h.setImageResource(R.drawable.ic_add_white_24dp); } catch (Throwable ignore) {}
            }
            ViewGroup host = null;
            if (this.g != null && this.g.getParent() instanceof ViewGroup) {
                host = (ViewGroup) this.g.getParent();
            } else if (this.b != null && this.b.getParent() instanceof ViewGroup) {
                host = (ViewGroup) this.b.getParent();
            }
            if (host == null) return;
            if (extraAddBtn == null) {
                extraAddBtn = new Button(this);
                extraAddBtn.setAllCaps(false);
                extraAddBtn.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) { openAddScreen(); }
                });
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.topMargin = (int) (12 * getResources().getDisplayMetrics().density);
                extraAddBtn.setLayoutParams(lp);
                host.addView(extraAddBtn);
            }
            extraAddBtn.setText(this.j == 1 ? "+ Add custom view" : "+ Add view");
            extraAddBtn.setVisibility(View.VISIBLE);
            extraAddBtn.bringToFront();
        } catch (Throwable t) {
            android.util.Log.e("ViewSelector", "ensureAddButtonVisible", t);
        }
    }

    private void syncActivityToConfig(ProjectFileBean bean) {
        if (bean == null || this.d == null) return;
        try {
            Complex cx = new Complex();
            cx.setId(this.d);
            ActivitySyncBridge.syncNewOrUpdatedActivity(this, this.d, cx, bean);
            android.util.Log.i("ViewSelector", "ActivityConfig synced for "
                    + bean.getJavaName() + " toolbar="
                    + bean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_TOOLBAR));
        } catch (Throwable t) {
            android.util.Log.e("ViewSelector", "syncActivityToConfig", t);
        }
    }

    private void ensureDefaultActivityListed() {
        try {
            if (this.d == null) return;
            java.util.ArrayList<ProjectFileBean> acts = ma.b(this.d).a();
            if (acts != null && !acts.isEmpty()) return;
            ProjectFileBean main = new ProjectFileBean(0, "main");
            try {
                main.setActivityOptions(ProjectFileBean.OPTION_ACTIVITY_TOOLBAR);
            } catch (Throwable ignore) {}
            ma.b(this.d).a(main);
            try { ma.b(this.d).l(); } catch (Throwable ignore) {}
            try { ma.b(this.d).g(); } catch (Throwable ignore) {}
        } catch (Throwable t) {
            android.util.Log.e("ViewSelector", "ensureDefaultActivityListed", t);
        }
    }

    public int a(ProjectFileBean projectFileBean) {
        if (this.j == 0) {
            return 276;
        }
        return projectFileBean.fileType == 1 ? 277 : 278;
    }

    public ArrayList<String> a() {
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<ProjectFileBean> arrayListA = ma.b(this.d).a();
        if (arrayListA != null) {
            Iterator<ProjectFileBean> it = arrayListA.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().fileName);
            }
        }
        ArrayList<ProjectFileBean> arrayListB = ma.b(this.d).b();
        if (arrayListB != null) {
            Iterator<ProjectFileBean> it2 = arrayListB.iterator();
            while (it2.hasNext()) {
                arrayList.add(it2.next().fileName);
            }
        }
        return arrayList;
    }
}
