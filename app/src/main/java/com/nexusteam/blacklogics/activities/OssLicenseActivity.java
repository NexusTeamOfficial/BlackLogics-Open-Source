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
	
	static class LicenseItem {
		String libName;
		String author;
		String licenseType;
		String description;
		String copyright;
		String url;
		String icon;
		
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
	
	private MaterialToolbar toolbar;
	private LinearLayout llLicensesContainer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_oss_license);
		
		toolbar             = findViewById(R.id.toolbar);
		llLicensesContainer = findViewById(R.id.ll_licenses_container);
		
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		
		List<LicenseItem> licenses = buildLicenseList();
		for (LicenseItem item : licenses) {
			addLicenseCard(this, llLicensesContainer, item);
		}
	}
	
	private List<LicenseItem> buildLicenseList() {
		List<LicenseItem> list = new ArrayList<>();
		
		
		list.add(new LicenseItem(
		"Material Components for Android",
		"Google LLC",
		"Apache 2.0",
		"Material Design UI components (Toolbar, Chip, Card, Navigation, Buttons, etc.) used throughout BlackLogics.",
		"Copyright © Google LLC",
		"https://github.com/material-components/material-components-android",
		"🎨"
		));
		
		list.add(new LicenseItem(
		"AndroidX AppCompat",
		"The Android Open Source Project",
		"Apache 2.0",
		"Backward-compatible Activity and UI APIs (AppCompatActivity, themes).",
		"Copyright © The Android Open Source Project",
		"https://developer.android.com/jetpack/androidx/releases/appcompat",
		"📱"
		));
		
		list.add(new LicenseItem(
		"AndroidX Core",
		"The Android Open Source Project",
		"Apache 2.0",
		"Core AndroidX utilities and compatibility helpers.",
		"Copyright © The Android Open Source Project",
		"https://developer.android.com/jetpack/androidx/releases/core",
		"🤖"
		));
		
		list.add(new LicenseItem(
		"AndroidX RecyclerView",
		"The Android Open Source Project",
		"Apache 2.0",
		"Efficient lists for components, projects, logs, and editor panels.",
		"Copyright © The Android Open Source Project",
		"https://developer.android.com/jetpack/androidx/releases/recyclerview",
		"📜"
		));
		
		list.add(new LicenseItem(
		"AndroidX ConstraintLayout",
		"The Android Open Source Project",
		"Apache 2.0",
		"Flexible constraint-based layouts used in BlackLogics screens.",
		"Copyright © The Android Open Source Project",
		"https://developer.android.com/jetpack/androidx/releases/constraintlayout",
		"📐"
		));
		
		list.add(new LicenseItem(
		"AndroidX CardView",
		"The Android Open Source Project",
		"Apache 2.0",
		"Material-style card containers.",
		"Copyright © The Android Open Source Project",
		"https://developer.android.com/jetpack/androidx/releases/cardview",
		"🃏"
		));
		
		list.add(new LicenseItem(
		"AndroidX CoordinatorLayout",
		"The Android Open Source Project",
		"Apache 2.0",
		"Coordinator behaviors for app bars and scrolling layouts.",
		"Copyright © The Android Open Source Project",
		"https://developer.android.com/jetpack/androidx/releases/coordinatorlayout",
		"🧭"
		));
		
		list.add(new LicenseItem(
		"AndroidX DrawerLayout",
		"The Android Open Source Project",
		"Apache 2.0",
		"Navigation drawer container.",
		"Copyright © The Android Open Source Project",
		"https://developer.android.com/jetpack/androidx/releases/drawerlayout",
		"📂"
		));
		
		list.add(new LicenseItem(
		"AndroidX Fragment",
		"The Android Open Source Project",
		"Apache 2.0",
		"Fragment APIs for design tabs and multi-pane UI.",
		"Copyright © The Android Open Source Project",
		"https://developer.android.com/jetpack/androidx/releases/fragment",
		"🧩"
		));
		
		list.add(new LicenseItem(
		"AndroidX Lifecycle / ViewModel",
		"The Android Open Source Project",
		"Apache 2.0",
		"Lifecycle-aware components and ViewModel.",
		"Copyright © The Android Open Source Project",
		"https://developer.android.com/jetpack/androidx/releases/lifecycle",
		"♻️"
		));
		
		list.add(new LicenseItem(
		"AndroidX ViewPager / ViewPager2",
		"The Android Open Source Project",
		"Apache 2.0",
		"Swipeable page containers in the design workspace.",
		"Copyright © The Android Open Source Project",
		"https://developer.android.com/jetpack/androidx/releases/viewpager2",
		"📑"
		));
		
		list.add(new LicenseItem(
		"AndroidX MultiDex",
		"The Android Open Source Project",
		"Apache 2.0",
		"Support for apps exceeding the 64K method limit.",
		"Copyright © The Android Open Source Project",
		"https://developer.android.com/jetpack/androidx/releases/multidex",
		"📚"
		));
		
		list.add(new LicenseItem(
		"AndroidX Room",
		"The Android Open Source Project",
		"Apache 2.0",
		"SQLite object mapping library.",
		"Copyright © The Android Open Source Project",
		"https://developer.android.com/jetpack/androidx/releases/room",
		"🗄️"
		));
		
		list.add(new LicenseItem(
		"AndroidX WorkManager",
		"The Android Open Source Project",
		"Apache 2.0",
		"Background work scheduling.",
		"Copyright © The Android Open Source Project",
		"https://developer.android.com/jetpack/androidx/releases/work",
		"⏱️"
		));
		
		list.add(new LicenseItem(
		"AndroidX Preference",
		"The Android Open Source Project",
		"Apache 2.0",
		"Settings / preference UI fragments.",
		"Copyright © The Android Open Source Project",
		"https://developer.android.com/jetpack/androidx/releases/preference",
		"⚙️"
		));
		
		list.add(new LicenseItem(
		"AndroidX SwipeRefreshLayout",
		"The Android Open Source Project",
		"Apache 2.0",
		"Pull-to-refresh layout widget.",
		"Copyright © The Android Open Source Project",
		"https://developer.android.com/jetpack/androidx/releases/swiperefreshlayout",
		"🔄"
		));
		
		list.add(new LicenseItem(
		"AndroidX LocalBroadcastManager",
		"The Android Open Source Project",
		"Apache 2.0",
		"In-app local broadcast messaging (e.g. color picker results).",
		"Copyright © The Android Open Source Project",
		"https://developer.android.com/reference/androidx/localbroadcastmanager/content/LocalBroadcastManager",
		"📡"
		));
		
		list.add(new LicenseItem(
		"AndroidX CustomView",
		"The Android Open Source Project",
		"Apache 2.0",
		"Helpers for custom views and touch exploration.",
		"Copyright © The Android Open Source Project",
		"https://developer.android.com/jetpack/androidx/releases/customview",
		"🧩"
		));
		
		list.add(new LicenseItem(
		"AndroidX DragAndDrop",
		"The Android Open Source Project",
		"Apache 2.0",
		"Drag-and-drop support used by the layout editor.",
		"Copyright © The Android Open Source Project",
		"https://developer.android.com/jetpack/androidx/releases/draganddrop",
		"✋"
		));
		
		list.add(new LicenseItem(
		"AndroidX DynamicAnimation",
		"The Android Open Source Project",
		"Apache 2.0",
		"Physics-based animation APIs.",
		"Copyright © The Android Open Source Project",
		"https://developer.android.com/jetpack/androidx/releases/dynamicanimation",
		"✨"
		));
		
		list.add(new LicenseItem(
		"AndroidX Collection",
		"The Android Open Source Project",
		"Apache 2.0",
		"Efficient collection types used by AndroidX libraries.",
		"Copyright © The Android Open Source Project",
		"https://developer.android.com/jetpack/androidx/releases/collection",
		"📚"
		));
		
		list.add(new LicenseItem(
		"FlexboxLayout",
		"Google LLC",
		"Apache 2.0",
		"CSS flexbox-like layout manager for Android.",
		"Copyright © Google LLC",
		"https://github.com/google/flexbox-layout",
		"📦"
		));
		
		
		list.add(new LicenseItem(
		"OkHttp",
		"Square Inc.",
		"Apache 2.0",
		"HTTP client for network requests.",
		"Copyright © Square Inc.",
		"https://square.github.io/okhttp/",
		"🌐"
		));
		
		list.add(new LicenseItem(
		"Gson",
		"Google LLC",
		"Apache 2.0",
		"JSON serialization and deserialization for project data and configs.",
		"Copyright © Google LLC",
		"https://github.com/google/gson",
		"📦"
		));
		
		list.add(new LicenseItem(
		"Jackson",
		"FasterXML",
		"Apache 2.0",
		"JSON data-binding library (fasterxml).",
		"Copyright © FasterXML",
		"https://github.com/FasterXML/jackson",
		"📦"
		));
		
		list.add(new LicenseItem(
		"Glide",
		"Bump Technologies",
		"Apache 2.0 / BSD",
		"Image loading and caching for thumbnails and resources.",
		"Copyright © Bump Technologies",
		"https://github.com/bumptech/glide",
		"🖼️"
		));
		
		list.add(new LicenseItem(
		"Apache Commons Collections 4",
		"Apache Software Foundation",
		"Apache 2.0",
		"Extended collection utilities for Java.",
		"Copyright © Apache Software Foundation",
		"https://commons.apache.org/proper/commons-collections/",
		"📚"
		));
		
		
		list.add(new LicenseItem(
		"Sora Editor",
		"Rosemoe",
		"LGPL-2.1",
		"Syntax-highlighting code editor used in source viewer and code screens.",
		"Copyright © Rosemoe",
		"https://github.com/Rosemoe/sora-editor",
		"📝"
		));
		
		list.add(new LicenseItem(
		"Eclipse TM4E",
		"Eclipse Foundation",
		"EPL-2.0",
		"TextMate grammar support for the code editor.",
		"Copyright © Eclipse Foundation",
		"https://github.com/eclipse/tm4e",
		"🔤"
		));
		
		list.add(new LicenseItem(
		"ANTLR 4",
		"Terence Parr",
		"BSD",
		"Parser generator runtime used with language tooling.",
		"Copyright © Terence Parr",
		"https://www.antlr.org/",
		"🌳"
		));
		
		
		list.add(new LicenseItem(
		"Eclipse Compiler for Java (ECJ)",
		"Eclipse Foundation",
		"EPL-2.0",
		"On-device Java compiler used to compile user projects.",
		"Copyright © Eclipse Foundation",
		"https://eclipse.dev/jdt/",
		"☕"
		));
		
		list.add(new LicenseItem(
		"Android DX / compiler tooling",
		"Google LLC / AOSP",
		"Apache 2.0",
		"DEX compilation tooling used in the build pipeline.",
		"Copyright © Google LLC",
		"https://developer.android.com/studio/command-line",
		"🔨"
		));
		
		list.add(new LicenseItem(
		"dexlib2",
		"JesusFreke / smali project",
		"BSD-style",
		"Library for reading and writing Android DEX files.",
		"Copyright © smali contributors",
		"https://github.com/JesusFreke/smali",
		"📦"
		));
		
		list.add(new LicenseItem(
		"dex2jar",
		"pxb1988",
		"Apache 2.0",
		"Tools for converting between DEX and JAR formats.",
		"Copyright © pxb1988",
		"https://github.com/pxb1988/dex2jar",
		"🔄"
		));
		
		list.add(new LicenseItem(
		"CFR Decompiler",
		"Lee Benfield",
		"MIT",
		"Java bytecode decompiler used in analysis tools.",
		"Copyright © Lee Benfield",
		"https://github.com/leibnitz27/cfr",
		"🔍"
		));
		
		list.add(new LicenseItem(
		"JavaParser",
		"JavaParser.org",
		"Apache 2.0",
		"Java source AST parsing for code tools.",
		"Copyright © JavaParser contributors",
		"https://github.com/javaparser/javaparser",
		"☕"
		));
		
		list.add(new LicenseItem(
		"APK Signer",
		"Google LLC",
		"Apache 2.0",
		"Signs built APK files for installation and distribution.",
		"Copyright © Google LLC",
		"https://developer.android.com/studio/command-line/apksigner",
		"✍️"
		));
		
		list.add(new LicenseItem(
		"Android SDK Lib (sdklib)",
		"Google LLC / AOSP",
		"Apache 2.0",
		"Android SDK library utilities used during project builds.",
		"Copyright © Google LLC",
		"https://android.googlesource.com/platform/tools/base",
		"🛠️"
		));
		
		list.add(new LicenseItem(
		"Android SDK Build Tools",
		"Google LLC",
		"Apache 2.0",
		"aapt, zipalign and related packaging tools concepts used in builds.",
		"Copyright © Google LLC",
		"https://developer.android.com/studio/releases/build-tools",
		"🔨"
		));
		
		list.add(new LicenseItem(
		"AXML2XML",
		"Open-source community",
		"Apache 2.0 / see jar",
		"Converts binary Android XML (AXML) to readable XML.",
		"Copyright © respective authors",
		"",
		"📄"
		));
		
		
		list.add(new LicenseItem(
		"Lottie",
		"Airbnb",
		"Apache 2.0",
		"Vector animation views (LottieAnimationView).",
		"Copyright © Airbnb",
		"https://github.com/airbnb/lottie-android",
		"✨"
		));
		
		list.add(new LicenseItem(
		"Epoxy",
		"Airbnb",
		"Apache 2.0",
		"RecyclerView adapter framework and annotation processor.",
		"Copyright © Airbnb",
		"https://github.com/airbnb/epoxy",
		"📋"
		));
		
		list.add(new LicenseItem(
		"CircleImageView",
		"Henning Dodenhof",
		"Apache 2.0",
		"Circular ImageView widget.",
		"Copyright © Henning Dodenhof",
		"https://github.com/hdodenhof/CircleImageView",
		"⭕"
		));
		
		list.add(new LicenseItem(
		"HoloColorPicker",
		"Lars Werkman",
		"Apache 2.0",
		"Color picker used in layout and property editors.",
		"Copyright © Lars Werkman",
		"https://github.com/LarsWerkman/HoloColorPicker",
		"🎨"
		));
		
		list.add(new LicenseItem(
		"Android FilePicker",
		"Angad Singh",
		"Apache 2.0",
		"File selection dialogs for assets and project files.",
		"Copyright © Angad Singh",
		"https://github.com/Angads25/android-filepicker",
		"📂"
		));
		
		list.add(new LicenseItem(
		"Elite File Selector",
		"NexusTeam",
		"Project license",
		"Enhanced file selector UI used inside BlackLogics.",
		"Copyright © NexusTeam",
		"",
		"📁"
		));
		
		list.add(new LicenseItem(
		"LeakCanary",
		"Square Inc.",
		"Apache 2.0",
		"Memory leak detection (debug builds).",
		"Copyright © Square Inc.",
		"https://github.com/square/leakcanary",
		"💧"
		));
		
		
		list.add(new LicenseItem(
		"Google Mobile Ads (Play Services)",
		"Google LLC",
		"Google proprietary + open components",
		"AdMob / Mobile Ads SDK integration.",
		"Copyright © Google LLC",
		"https://developers.google.com/admob/android",
		"📢"
		));
		
		list.add(new LicenseItem(
		"Firebase Android SDK",
		"Google LLC",
		"Apache 2.0 (client libraries)",
		"Firebase client libraries where enabled in the project.",
		"Copyright © Google LLC",
		"https://firebase.google.com/",
		"🔥"
		));
		
		
		list.add(new LicenseItem(
		"Gradle",
		"Gradle Inc.",
		"Apache 2.0",
		"Build automation concepts and tooling used around Android builds.",
		"Copyright © Gradle Inc.",
		"https://gradle.org/",
		"🔧"
		));
		
		list.add(new LicenseItem(
		"Android Gradle Plugin",
		"Google LLC",
		"Apache 2.0",
		"Gradle plugin model for Android application builds.",
		"Copyright © Google LLC",
		"https://developer.android.com/studio/releases/gradle-plugin",
		"📦"
		));
		
		list.add(new LicenseItem(
		"logcat",
		"YurkivTaras",
		"Apache 2.0",
		"Real-time logcat reading used by the built-in Logcat Viewer.",
		"Copyright © Yuriy Taras",
		"https://github.com/YurkivTaras/logcat",
		"📋"
		));
		
		return list;
	}
	
	
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
