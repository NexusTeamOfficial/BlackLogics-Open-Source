package com.nexusteam.blacklogics.manage.view;

import com.nexusteam.internal.fe;
import com.nexusteam.internal.jj;
import com.nexusteam.internal.jq;
import com.nexusteam.internal.ke;
import com.nexusteam.internal.ki;
import com.nexusteam.internal.kq;
import com.nexusteam.internal.lw;
import com.nexusteam.internal.ma;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import com.nexusteam.blacklogics.R;
import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.blacklogics.activityfile.ActivitySyncBridge;
import com.nexusteam.blacklogics.development.Complex;
import com.nexusteam.internal.beans.ProjectLibraryBean;
import com.nexusteam.internal.beans.ViewBean;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.analytics.HitBuilders;
import java.util.ArrayList;
import java.util.Iterator;

public class ManageViewActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    public FloatingActionButton f1367a;
    ManageViewFragment b;
    ManageCustomViewFragment c;
    private String e;
    private Toolbar f;
    private LinearLayout g;
    private Button h;
    private Button i;
    private ViewPager l;
    private TabLayout m;
    private final int d = 2;
    private boolean j = false;
    private String k = ProjectLibraryBean.LIB_USE_N;
    private int[] n = new int[19];

    @Override
    public void onPageScrollStateChanged(int i) {
    }

    @Override
    public void onPageScrolled(int i, float f, int i2) {
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!isTaskRoot() && false) {
            finish();
        }
        setContentView(R.layout.manage_view);
        this.f = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.f);
        findViewById(R.id.layout_main_logo).setVisibility(8);
        getSupportActionBar().setTitle(kq.a().a(getApplicationContext(), R.string.design_actionbar_title_manager_view));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.f.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ki.a()) {
                    return;
                }
                ManageViewActivity.this.onBackPressed();
            }
        });
        this.g = (LinearLayout) findViewById(R.id.layout_btn_group);
        this.h = (Button) findViewById(R.id.btn_delete);
        this.i = (Button) findViewById(R.id.btn_cancel);
        this.h.setText(kq.a().a(getApplicationContext(), R.string.common_word_delete));
        this.i.setText(kq.a().a(getApplicationContext(), R.string.common_word_cancel));
        this.h.setOnClickListener(this);
        this.i.setOnClickListener(this);
        if (bundle == null) {
            this.e = getIntent().getStringExtra("sc_id");
            this.k = getIntent().getStringExtra("compatUseYn");
        } else {
            this.e = bundle.getString("sc_id");
            this.k = bundle.getString("compatUseYn");
        }
        this.m = (TabLayout) findViewById(R.id.tab_layout);
        this.l = (ViewPager) findViewById(R.id.view_pager);
        this.l.setAdapter(new b(getSupportFragmentManager()));
        this.l.setOffscreenPageLimit(2);
        this.l.addOnPageChangeListener(this);
        this.m.setupWithViewPager(this.l);
        this.f1367a = (FloatingActionButton) findViewById(R.id.fab);
        this.f1367a.setOnClickListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("sc_id", this.e);
        bundle.putString("compatUseYn", this.k);
        super.onSaveInstanceState(bundle);
    }

    @Override
    public void onPostCreate(@Nullable Bundle bundle) {
        super.onPostCreate(bundle);
    }

    public void b() {
        ma.b(this.e).a(this.b.b());
        ma.b(this.e).b(this.c.b());
        ma.b(this.e).e();
        try { ma.b(this.e).g(); } catch (Throwable ignore) {}
        ma.b(this.e).l();
        ma.a(this.e).a(ma.b(this.e));
        try {
            for (ProjectFileBean bean : this.b.b()) {
                if (bean != null && bean.fileType == 0) {
                    ActivitySyncBridge.syncNewOrUpdatedActivity(
                            getApplicationContext(), this.e, null, bean);
                }
            }
            for (ProjectFileBean bean : this.c.b()) {
                if (bean != null) {
                    ActivitySyncBridge.syncNewOrUpdatedActivity(
                            getApplicationContext(), this.e, null, bean);
                }
            }
        } catch (Throwable t) {
            android.util.Log.e("ManageView", "bridge batch", t);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isTaskRoot() && false) {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manage_screen_menu, menu);
        if (this.j) {
            menu.findItem(R.id.menu_screen_delete).setVisible(false);
        } else {
            menu.findItem(R.id.menu_screen_delete).setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.menu_screen_delete) {
            a(!this.j);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onBackPressed() {
        if (this.j) {
            a(false);
            return;
        }
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ManageViewActivity.this.new a(ManageViewActivity.this.getApplicationContext()).execute(new Void[0]);
                }
            }, 500L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class a extends jq {
        public a(Context context) {
            super(context);
        }

        @Override
        public void a() {
            try {
                publishProgress(new String[]{kq.a().a(ManageViewActivity.this.getApplicationContext(), R.string.common_message_progress)});
                ManageViewActivity.this.b();
            } catch (Exception e) {
                e.printStackTrace();
                android.util.Log.e("ManageView", kq.a().a(this.h, R.string.common_error_unknown));
            }
        }

        @Override
        public void b() {
            ManageViewActivity.this.setResult(-1);
            ManageViewActivity.this.finish();
        }

        @Override
        public void a(String str) {
        }
    }

    public void a(boolean z) {
        this.j = z;
        invalidateOptionsMenu();
        if (this.j) {
            this.g.setVisibility(0);
        } else {
            this.g.setVisibility(8);
        }
        this.b.a(this.j);
        this.c.a(this.j);
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i != 264) {
            if (i == 266 && i2 == -1) {
                ProjectFileBean projectFileBean = (ProjectFileBean) intent.getParcelableExtra("project_file");
                this.c.a(projectFileBean);
                this.c.d();
                if (intent.hasExtra("preset_views")) {
                    a(projectFileBean, (ArrayList) intent.getParcelableArrayListExtra("preset_views"));
                }
                try {
                    ActivitySyncBridge.syncNewOrUpdatedActivity(
                            getApplicationContext(), this.e, null, projectFileBean);
                } catch (Throwable t) {
                    android.util.Log.e("ManageView", "sync custom", t);
                }
                return;
            }
            return;
        }
        if (i2 == -1) {
            ProjectFileBean projectFileBean2 = (ProjectFileBean) intent.getParcelableExtra("project_file");
            this.b.a(projectFileBean2);
            if (projectFileBean2.hasActivityOption(4)) {
                a(projectFileBean2.getDrawerName());
            }
            if (projectFileBean2.hasActivityOption(4) || projectFileBean2.hasActivityOption(8)) {
                ma.d(this.e).d().useYn = ProjectLibraryBean.LIB_USE_Y;
            }
            if (intent.hasExtra("preset_views")) {
                a(projectFileBean2, (ArrayList) intent.getParcelableArrayListExtra("preset_views"));
            }
            try {
                ActivitySyncBridge.syncNewOrUpdatedActivity(
                        getApplicationContext(), this.e, null, projectFileBean2);
            } catch (Throwable t) {
                android.util.Log.e("ManageView", "sync bridge", t);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (ki.a()) {
            return;
        }
        int id = view.getId();
        if (id == R.id.btn_cancel) {
            if (this.j) {
                a(false);
                return;
            }
            return;
        }
        if (id == R.id.btn_delete) {
            if (this.j) {
                java.util.ArrayList<ProjectFileBean> toRemove = new java.util.ArrayList<>();
                try {
                    for (ProjectFileBean bean : this.b.b()) {
                        if (bean != null && bean.isSelected) toRemove.add(bean);
                    }
                    for (ProjectFileBean bean : this.c.b()) {
                        if (bean != null && bean.isSelected) toRemove.add(bean);
                    }
                } catch (Throwable ignore) {}

                this.b.c();
                this.c.c();

                try {
                    ma.b(this.e).a(this.b.b());
                    ma.b(this.e).b(this.c.b());
                    ma.b(this.e).e();
                    try { ma.b(this.e).g(); } catch (Throwable ignore) {}
                    ma.b(this.e).l();
                } catch (Throwable t) {
                    android.util.Log.e("ManageView", "delete save", t);
                }

                for (ProjectFileBean bean : toRemove) {
                    try {
                        ActivitySyncBridge.removeActivity(
                                getApplicationContext(), this.e, null, bean);
                    } catch (Throwable t) {
                        android.util.Log.e("ManageView", "remove bridge", t);
                    }
                }

                a(false);
                this.b.d();
                this.c.d();
                ke.a(getApplicationContext(), kq.a().a(getApplicationContext(), R.string.common_message_complete_delete), 1).show();
                this.f1367a.show();
                return;
            }
            return;
        }
        if (id != R.id.fab) {
            return;
        }
        a(false);
        if (this.l.getCurrentItem() == 0) {
            Intent intent = new Intent(this, (Class<?>) AddViewActivity.class);
            intent.putStringArrayListExtra("screen_names", a());
            intent.putExtra("request_code", 264);
            startActivityForResult(intent, 264);
            return;
        }
        Intent intent2 = new Intent(this, (Class<?>) AddCustomViewActivity.class);
        intent2.putStringArrayListExtra("screen_names", a());
        startActivityForResult(intent2, 266);
    }

    private void a(ProjectFileBean projectFileBean, ArrayList<ViewBean> arrayList) {
        ma.a(this.e);
        for (ViewBean viewBean : lw.a(arrayList)) {
            viewBean.id = a(viewBean.type, projectFileBean.getXmlName());
            ma.a(this.e).b(projectFileBean.getXmlName(), viewBean);
            if (viewBean.type == 3 && projectFileBean.fileType == 0) {
                ma.a(this.e).a(projectFileBean.getJavaName(), 1, viewBean.type, viewBean.id, "onClick");
            }
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
        ArrayList<ViewBean> arrayListA = ma.a(this.e).a(str);
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

    public ArrayList<String> a() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("debug");
        ArrayList<ProjectFileBean> arrayListB = this.b.b();
        ArrayList<ProjectFileBean> arrayListB2 = this.c.b();
        Iterator<ProjectFileBean> it = arrayListB.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().fileName);
        }
        Iterator<ProjectFileBean> it2 = arrayListB2.iterator();
        while (it2.hasNext()) {
            arrayList.add(it2.next().fileName);
        }
        return arrayList;
    }

    public void a(String str) {
        this.c.a(str);
        this.c.d();
    }

    public void b(String str) {
        this.c.b(str);
        this.c.d();
    }

    class b extends FragmentPagerAdapter {
        private String[] b;

        @Override
        public int getCount() {
            return 2;
        }

        public b(FragmentManager fragmentManager) {
            super(fragmentManager);
            this.b = new String[2];
            this.b[0] = kq.a().a(ManageViewActivity.this.getApplicationContext(), R.string.common_word_view).toUpperCase();
            this.b[1] = kq.a().a(ManageViewActivity.this.getApplicationContext(), R.string.common_word_custom_view).toUpperCase();
        }

        @Override
        public Object instantiateItem(ViewGroup viewGroup, int i) {
            Fragment fragment = (Fragment) super.instantiateItem(viewGroup, i);
            if (i == 0) {
                ManageViewActivity.this.b = (ManageViewFragment) fragment;
            } else {
                ManageViewActivity.this.c = (ManageCustomViewFragment) fragment;
            }
            return fragment;
        }

        @Override
        public Fragment getItem(int i) {
            if (i == 0) {
                return new ManageViewFragment();
            }
            return new ManageCustomViewFragment();
        }

        @Override
        public CharSequence getPageTitle(int i) {
            return this.b[i];
        }
    }

    @Override
    public void onPageSelected(int i) {
        this.f1367a.show();
    }
}
