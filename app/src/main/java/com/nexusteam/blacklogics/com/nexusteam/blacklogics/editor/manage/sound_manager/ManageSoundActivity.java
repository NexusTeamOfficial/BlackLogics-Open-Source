package com.nexusteam.blacklogics.editor.manage.sound_manager;

import com.nexusteam.internal.em;
import com.nexusteam.internal.jj;
import com.nexusteam.internal.jq;
import com.nexusteam.internal.ki;
import com.nexusteam.internal.kq;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import com.nexusteam.blacklogics.R;
import com.nexusteam.internal.lib.base.BaseAppCompatActivity;
import com.google.android.gms.analytics.HitBuilders;

public class ManageSoundActivity extends BaseAppCompatActivity implements ViewPager.OnPageChangeListener {

    ManageSoundProjectFragment f1309a;
    ManageSoundCollectionFragment b;
    private final int c = 2;
    private String d;
    private Toolbar e;
    private ViewPager f;
    private TabLayout g;

    @Override
    public void onPageScrollStateChanged(int i) {
    }

    @Override
    public void onPageScrolled(int i, float f, int i2) {
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!super.n()) {
            finish();
        }
        setContentView(R.layout.manage_sound);
        this.e = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.e);
        findViewById(R.id.layout_main_logo).setVisibility(8);
        getSupportActionBar().setTitle(kq.a().a(getApplicationContext(), R.string.design_actionbar_title_manager_sound));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.e.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ki.a()) {
                    return;
                }
                ManageSoundActivity.this.onBackPressed();
            }
        });
        if (bundle == null) {
            this.d = getIntent().getStringExtra("sc_id");
        } else {
            this.d = bundle.getString("sc_id");
        }
        this.g = (TabLayout) findViewById(R.id.tab_layout);
        this.f = (ViewPager) findViewById(R.id.view_pager);
        this.f.setAdapter(new b(getSupportFragmentManager()));
        this.f.setOffscreenPageLimit(2);
        this.f.addOnPageChangeListener(this);
        this.g.setupWithViewPager(this.f);
    }

    public ManageSoundProjectFragment a() {
        return this.f1309a;
    }

    public ManageSoundCollectionFragment b() {
        return this.b;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("sc_id", this.d);
        super.onSaveInstanceState(bundle);
    }

    @Override
    public void onPostCreate(@Nullable Bundle bundle) {
        super.onPostCreate(bundle);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!super.n()) {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (this.f1309a.b) {
            this.f1309a.a(false);
            return;
        }
        try {
            m();
            this.f1309a.a();
            this.b.a();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ManageSoundActivity.this.new a(ManageSoundActivity.this.getApplicationContext()).execute(new Void[0]);
                }
            }, 500L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class a extends jq {
        public a(Context context) {
            super(context);
            ManageSoundActivity.this.a(this);
        }

        @Override
        public void a() {
            try {
                publishProgress(new String[]{"Now processing.."});
                ManageSoundActivity.this.f1309a.b();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(kq.a().a(this.h, R.string.common_error_unknown));
            }
        }

        @Override
        public void b() {
            ManageSoundActivity.this.o();
            ManageSoundActivity.this.setResult(-1);
            ManageSoundActivity.this.finish();
            em.f().e();
        }

        @Override
        public void a(String str) {
            ManageSoundActivity.this.o();
        }
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
            this.b[0] = kq.a().a(ManageSoundActivity.this.getApplicationContext(), R.string.design_manager_tab_title_this_project).toUpperCase();
            this.b[1] = kq.a().a(ManageSoundActivity.this.getApplicationContext(), R.string.design_manager_tab_title_my_collection).toUpperCase();
        }

        @Override
        public Object instantiateItem(ViewGroup viewGroup, int i) {
            Fragment fragment = (Fragment) super.instantiateItem(viewGroup, i);
            if (i == 0) {
                ManageSoundActivity.this.f1309a = (ManageSoundProjectFragment) fragment;
            } else {
                ManageSoundActivity.this.b = (ManageSoundCollectionFragment) fragment;
            }
            return fragment;
        }

        @Override
        @Nullable
        public CharSequence getPageTitle(int i) {
            return this.b[i];
        }

        @Override
        public Fragment getItem(int i) {
            if (i == 0) {
                return new ManageSoundProjectFragment();
            }
            return new ManageSoundCollectionFragment();
        }
    }

    @Override
    public void onPageSelected(int i) {
        if (i == 0) {
            this.b.a();
        } else {
            this.f1309a.a();
        }
    }

    public void a(int i) {
        this.f.setCurrentItem(i);
    }
}
