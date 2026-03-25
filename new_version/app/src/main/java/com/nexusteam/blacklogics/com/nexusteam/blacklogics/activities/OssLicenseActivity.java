package com.nexusteam.blacklogics.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;

import com.nexusteam.blacklogics.R;

import java.util.ArrayList;
import java.util.List;

public class OssLicenseActivity extends AppCompatActivity {
	
	// ── Model ────────────────────────────────────────────────────────────────
	static class LicenseItem {
		String libName;
		String author;
		String licenseType;
		String description;
		String copyright;
		String url;
		String icon; // emoji icon
		
		LicenseItem(String libName, String author, String licenseType,
		String description, String copyright, String url, String icon) {
			this.libName     = libName;
			this.author      = author;
			this.licenseType = licenseType;
			this.description = description;
			this.copyright   = copyright;
			this.url         = url;
			this.icon        = icon;
		}
	}
	
	// ── Views ─────────────────────────────────────────────────────────────────
	private MaterialToolbar toolbar;
	private LinearLayout llLicensesContainer;
	
	// ─────────────────────────────────────────────────────────────────────────
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_oss_license);
		
		toolbar             = findViewById(R.id.toolbar);
		llLicensesContainer = findViewById(R.id.ll_licenses_container);
		
		// Back button
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		
		// Build and render license list
		List<LicenseItem> licenses = buildLicenseList();
		for (LicenseItem item : licenses) {
			addLicenseCard(this, llLicensesContainer, item);
		}
	}
	
	// ── License Data ──────────────────────────────────────────────────────────
	private List<LicenseItem> buildLicenseList() {
		List<LicenseItem> list = new ArrayList<>();
		
		// ── Third-party libraries used by BlackLogics ──────────────────────
		
		list.add(new LicenseItem(
		"logcat",
		"YurkivTaras",
		"Apache 2.0",
		"Provides real-time logcat log reading and display functionality used in BlackLogics' built-in Logcat Viewer feature.",
		"Copyright © Yuriy Taras",
		"https://github.com/YurkivTaras/logcat",
		"📋"
		));
		
		list.add(new LicenseItem(
		"Material Components for Android",
		"Google LLC",
		"Apache 2.0",
		"Material Design 3 UI components including Chips, Cards, Toolbars, Navigation Drawers, and more used throughout BlackLogics.",
		"Copyright © Google LLC",
		"https://github.com/material-components/material-components-android",
		"🎨"
		));
		
		list.add(new LicenseItem(
		"AndroidX Core",
		"The Android Open Source Project",
		"Apache 2.0",
		"Core AndroidX libraries providing backward-compatible versions of Android framework APIs, including NestedScrollView and other components.",
		"Copyright © The Android Open Source Project",
		"https://developer.android.com/jetpack/androidx",
		"🤖"
		));
		
		list.add(new LicenseItem(
		"AndroidX AppCompat",
		"The Android Open Source Project",
		"Apache 2.0",
		"Provides backward-compatible implementations of higher-level Android APIs, including AppCompatActivity used as the base activity class.",
		"Copyright © The Android Open Source Project",
		"https://developer.android.com/jetpack/androidx/releases/appcompat",
		"📱"
		));
		
		list.add(new LicenseItem(
		"AndroidX RecyclerView",
		"The Android Open Source Project",
		"Apache 2.0",
		"Flexible view for providing a limited window into a large data set, used in BlackLogics for displaying lists of components, projects, and logs.",
		"Copyright © The Android Open Source Project",
		"https://developer.android.com/jetpack/androidx/releases/recyclerview",
		"📜"
		));
		
		list.add(new LicenseItem(
		"AndroidX ConstraintLayout",
		"The Android Open Source Project",
		"Apache 2.0",
		"A layout that allows flexible positioning and sizing of widgets, used in various BlackLogics UI screens.",
		"Copyright © The Android Open Source Project",
		"https://developer.android.com/develop/ui/views/layout/constraint-layout",
		"📐"
		));
		
		list.add(new LicenseItem(
		"Gradle Build Tool",
		"Gradle Inc.",
		"Apache 2.0",
		"The build automation tool used to compile, test, and package BlackLogics.",
		"Copyright © Gradle Inc.",
		"https://gradle.org",
		"🔧"
		));
		
		list.add(new LicenseItem(
		"Android Support Library",
		"Google LLC",
		"Apache 2.0",
		"Backward-compatible versions of Android framework APIs for older Android versions.",
		"Copyright © Google LLC",
		"https://developer.android.com/topic/libraries/support-library",
		"🤖"
		));
		
		list.add(new LicenseItem(
		"Google Material Design",
		"Google LLC",
		"Apache 2.0",
		"Material Design components and guidelines for Android UI development.",
		"Copyright © Google LLC",
		"https://material.io/develop/android",
		"🎨"
		));
		
		list.add(new LicenseItem(
		"OkHttp",
		"Square Inc.",
		"Apache 2.0",
		"HTTP client for Android and Java applications.",
		"Copyright © Square Inc.",
		"https://square.github.io/okhttp/",
		"🌐"
		));
		
		list.add(new LicenseItem(
		"Gson",
		"Google LLC",
		"Apache 2.0",
		"Java library for converting Java Objects to JSON and back.",
		"Copyright © Google LLC",
		"https://github.com/google/gson",
		"📦"
		));
		
		list.add(new LicenseItem(
		"Glide",
		"Bump Technologies",
		"Apache 2.0",
		"Image loading and caching library for Android.",
		"Copyright © Bump Technologies",
		"https://github.com/bumptech/glide",
		"🖼️"
		));
		
		list.add(new LicenseItem(
		"Eclipse Compiler for Java (ECJ)",
		"Eclipse Foundation",
		"Eclipse Public License 2.0",
		"Java compiler used for building Android applications.",
		"Copyright © Eclipse Foundation",
		"https://eclipse.dev/jdt/",
		"☕"
		));
		
		list.add(new LicenseItem(
		"OpenJDK",
		"Oracle Corporation",
		"GNU General Public License v2",
		"Java Development Kit used for compiling Android applications.",
		"Copyright © Oracle Corporation",
		"https://openjdk.org/",
		"🖥️"
		));
		
		list.add(new LicenseItem(
		"Android SDK Build Tools",
		"Google LLC",
		"Apache 2.0",
		"Tools for building Android applications including aapt, dx, and zipalign.",
		"Copyright © Google LLC",
		"https://developer.android.com/studio/releases/build-tools",
		"🔨"
		));
		
		list.add(new LicenseItem(
		"APK Signer",
		"Google LLC",
		"Apache 2.0",
		"Tool for signing APK files for Android application distribution.",
		"Copyright © Google LLC",
		"https://developer.android.com/studio/command-line/apksigner",
		"✍️"
		));
		
		list.add(new LicenseItem(
		"Gradle",
		"Gradle Inc.",
		"Apache 2.0",
		"Build automation tool for Android and Java projects.",
		"Copyright © Gradle Inc.",
		"https://gradle.org/",
		"🔧"
		));
		
		list.add(new LicenseItem(
		"Android Gradle Plugin",
		"Google LLC",
		"Apache 2.0",
		"Gradle plugin for building Android applications.",
		"Copyright © Google LLC",
		"https://developer.android.com/studio/releases/gradle-plugin",
		"📦"
		));
		
		list.add(new LicenseItem(
		"Apache Commons IO",
		"Apache Software Foundation",
		"Apache 2.0",
		"Utilities for IO operations used in file handling.",
		"Copyright © Apache Software Foundation",
		"https://commons.apache.org/proper/commons-io/",
		"📁"
		));
		
		list.add(new LicenseItem(
		"Apache Commons Lang",
		"Apache Software Foundation",
		"Apache 2.0",
		"Utilities for Java language core classes.",
		"Copyright © Apache Software Foundation",
		"https://commons.apache.org/proper/commons-lang/",
		"🔧"
		));
		
		list.add(new LicenseItem(
		"ZXing (Zebra Crossing)",
		"Sean Owen",
		"Apache 2.0",
		"Barcode and QR code scanning library for Android.",
		"Copyright © Sean Owen",
		"https://github.com/zxing/zxing",
		"📷"
		));
		
		list.add(new LicenseItem(
		"EventBus",
		"GreenRobot",
		"Apache 2.0",
		"Event bus library for Android and Java simplifying communication between components.",
		"Copyright © GreenRobot",
		"https://github.com/greenrobot/EventBus",
		"📢"
		));
		
		// Add more libraries here as needed:
		// list.add(new LicenseItem("LibraryName", "Author", "LicenseType",
		//     "Description", "Copyright © Author", "https://...", "emoji"));
		
		return list;
	}
	
	// ── Card Inflater ─────────────────────────────────────────────────────────
	private void addLicenseCard(Context context, LinearLayout container, LicenseItem item) {
		View card = LayoutInflater.from(context)
		.inflate(R.layout.item_oss_license, container, false);
		
		TextView tvIcon        = card.findViewById(R.id.tv_icon);
		TextView tvLibName     = card.findViewById(R.id.tv_lib_name);
		TextView tvAuthor      = card.findViewById(R.id.tv_author);
		Chip     chipLicense   = card.findViewById(R.id.chip_license_type);
		TextView tvDescription = card.findViewById(R.id.tv_description);
		TextView tvCopyright   = card.findViewById(R.id.tv_copyright);
		TextView tvUrl         = card.findViewById(R.id.tv_url);
		
		tvIcon.setText(item.icon);
		tvLibName.setText(item.libName);
		tvAuthor.setText(item.author);
		chipLicense.setText(item.licenseType);
		tvDescription.setText(item.description);
		tvCopyright.setText(item.copyright);
		tvUrl.setText(item.url);
		
		container.addView(card);
	}
}
