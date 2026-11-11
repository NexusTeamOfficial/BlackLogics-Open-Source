package com.nexusteam.blacklogics.editor.manage.image_manager;

import com.nexusteam.internal.ek;
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

public class ManageImageActivity extends BaseAppCompatActivity implements ViewPager.OnPageChangeListener {
	
	ManageImageProjectFragment f1201a;
	ManageImageCollectionFragment b;
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
		setContentView(R.layout.manage_image_ac);
		if (!super.n()) {
			finish();
		}
		this.e = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(this.e);
		findViewById(R.id.layout_main_logo).setVisibility(8);
		getSupportActionBar().setTitle(kq.a().a(getApplicationContext(), R.string.design_actionbar_title_manager_image));
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		this.e.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (ki.a()) {
					return;
				}
				ManageImageActivity.this.onBackPressed();
			}
		});
		if (bundle == null) {
			this.d = getIntent().getStringExtra("sc_id");
		} else {
			this.d = bundle.getString("sc_id");
		}
		this.g = (TabLayout) findViewById(R.id.tab_layout);
		this.f = (ViewPager) findViewById(R.id.view_pager);
		this.f.setAdapter(new a(getSupportFragmentManager()));
		this.f.setOffscreenPageLimit(2);
		this.f.addOnPageChangeListener(this);
		this.g.setupWithViewPager(this.f);
	}
	
	@Override
	public void onPostCreate(@Nullable Bundle bundle) {
		super.onPostCreate(bundle);
	}
	
	@Override
	public void onSaveInstanceState(Bundle bundle) {
		bundle.putString("sc_id", this.d);
		super.onSaveInstanceState(bundle);
	}
	
	public ManageImageProjectFragment a() {
		return this.f1201a;
	}
	
	public ManageImageCollectionFragment b() {
		return this.b;
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
		if (this.f1201a.f1220a) {
			this.f1201a.a(false);
			return;
		}
		try {
			m();
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					ManageImageActivity.this.new b(ManageImageActivity.this.getApplicationContext()).execute(new Void[0]);
				}
			}, 500L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	class b extends jq {
		public b(Context context) {
			super(context);
			ManageImageActivity.this.a(this);
		}
		
		@Override
		public void a() {
			try {
				publishProgress(new String[]{"Now processing.."});
				ManageImageActivity.this.f1201a.b();
			} catch (Exception e) {
				e.printStackTrace();
				
				String realError = e.getClass().getName() + ": " + e.getMessage();
				
				saveErrorToDownload(realError, e);
				
				throw new RuntimeException(realError);
			}
		}
		
		@Override
		public void b() {
			ManageImageActivity.this.o();
			ManageImageActivity.this.setResult(-1);
			ManageImageActivity.this.finish();
			ek.f().e();
		}
		
		@Override
		public void a(String str) {
			ManageImageActivity.this.o();
		}
		
		private void saveErrorToDownload(String errorText, Exception e) {
			try {
				java.io.StringWriter sw = new java.io.StringWriter();
				java.io.PrintWriter pw = new java.io.PrintWriter(sw);
				e.printStackTrace(pw);
				pw.flush();
				
				String fullLog = "===== ERROR ====="
				+ "\nTime: " + new java.util.Date().toString()
				+ "\nMessage: " + errorText
				+ "\n\n----- STACK TRACE -----\n"
				+ sw.toString();
				
				String fileName = "error_" + System.currentTimeMillis() + ".txt";
				
				java.io.File downloadDir = android.os.Environment
				.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_DOWNLOADS);
				
				if (!downloadDir.exists()) {
					downloadDir.mkdirs();
				}
				
				java.io.File file = new java.io.File(downloadDir, fileName);
				java.io.FileWriter writer = new java.io.FileWriter(file, false);
				writer.write(fullLog);
				writer.flush();
				writer.close();
				
				android.util.Log.e("ManageImageActivity", "Error saved to: " + file.getAbsolutePath());
				
			} catch (Exception io) {
				io.printStackTrace();
			}
		}
	}
	public void a(int i) {
		this.f.setCurrentItem(i);
	}
	
	class a extends FragmentPagerAdapter {
		private String[] b;
		
		@Override
		public int getCount() {
			return 2;
		}
		
		public a(FragmentManager fragmentManager) {
			super(fragmentManager);
			this.b = new String[2];
			this.b[0] = kq.a().a(ManageImageActivity.this.getApplicationContext(), R.string.design_manager_tab_title_this_project).toUpperCase();
			this.b[1] = kq.a().a(ManageImageActivity.this.getApplicationContext(), R.string.design_manager_tab_title_my_collection).toUpperCase();
		}
		
		@Override
		public Object instantiateItem(ViewGroup viewGroup, int i) {
			Fragment fragment = (Fragment) super.instantiateItem(viewGroup, i);
			if (i == 0) {
				ManageImageActivity.this.f1201a = (ManageImageProjectFragment) fragment;
			} else {
				ManageImageActivity.this.b = (ManageImageCollectionFragment) fragment;
			}
			return fragment;
		}
		
		@Override
		public CharSequence getPageTitle(int i) {
			return this.b[i];
		}
		
		@Override
		public Fragment getItem(int i) {
			if (i == 0) {
				return new ManageImageProjectFragment();
			}
			return new ManageImageCollectionFragment();
		}
	}
	
	@Override
	public void onPageSelected(int i) {
		if (i == 0) {
			this.b.d();
		} else {
			this.f1201a.a(false);
		}
	}
}
