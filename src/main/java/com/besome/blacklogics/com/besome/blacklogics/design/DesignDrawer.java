package com.besome.blacklogics.design;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.sketchware.remod.R;
import com.besome.blacklogics.*;

import a.a.a.mB;
import a.a.a.wB;
import a.a.a.xB;

public class DesignDrawer extends LinearLayout implements View.OnClickListener {

    public final int a = 1;
    public final int b = 2;
    public final int c = 3;
    public final int d = 4;
    public final int e = 5;
    public final int f = 6;
    public final int g = 7;
    public final int h = 8;
    public final int i = 100;
    public final int j = 101;
    public final int k = 102;
    public final int l = 103;
    public final int m = 104;
    public final int n = 300;
    public final int o = 301;
    public Context context;
    public LinearLayout menusLayout;
    public LinearLayout bottomMenuslayout;

    public DesignDrawer(Context context) {
        super(context);
        a(context);
    }

    public DesignDrawer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    public final DrawerItem addDrawerItem(int tag, boolean useSeparator, int iconResId, int titleResId, int descriptionResId) {
        DrawerItem drawerItem = new DrawerItem(context, tag);
        drawerItem.setContent(iconResId, xB.b().a(context, titleResId), xB.b().a(context, descriptionResId));
        drawerItem.setTag(tag);
        drawerItem.setOnClickListener(this);
        drawerItem.setSeparatorVisibility(useSeparator);
        drawerItem.setSubSeparatorVisibility(!useSeparator);
        if (tag == 19) {
            drawerItem.setSubSeparatorVisibility(false);
        }
        return drawerItem;
    }

    public final void a(Context context) {
        this.context = context;
        wB.a(context, this, R.layout.design_drawer);
        TextView tv_title_configuration = findViewById(R.id.tv_title_configuration);
        tv_title_configuration.setText(xB.b().a(context, R.string.design_drawer_menu_title));
        ((TextView) findViewById(R.id.tv_title_global))
                .setText(xB.b().a(context, R.string.design_drawer_menu_bottom_title));
        menusLayout = findViewById(R.id.layout_menus);
        bottomMenuslayout = findViewById(R.id.layout_bottom_menus);
        /* Add collection item */
        bottomMenuslayout.addView(addDrawerItem(
                1,
                false,
                R.drawable.ic_bookmark_red_48dp,
                R.string.design_drawer_menu_title_collection,
                R.string.design_drawer_menu_description_collection
        ));
        /* Add built-in Library Manager (AppCompat, Firebase, AdMob, Google Maps SDK) */
        /* INCLUDES SECTION SEPARATOR */
        menusLayout.addView(addDrawerItem(
                3,
                true,
                R.drawable.categorize_48,
                R.string.design_drawer_menu_title_library,
                R.string.design_drawer_menu_description_library
        ));
        /* Add View Manager */
        menusLayout.addView(addDrawerItem(
                4,
                false,
                R.drawable.multiple_devices_48,
                R.string.design_drawer_menu_title_view,
                R.string.design_drawer_menu_description_view
        ));
        /* Add Image Manager */
        menusLayout.addView(addDrawerItem(
                5,
                false,
                R.drawable.ic_picture_48dp,
                R.string.design_drawer_menu_title_image,
                R.string.design_drawer_menu_description_image
        ));
        /* Add Sound Manager */
        menusLayout.addView(addDrawerItem(
                6,
                false,
                R.drawable.ic_sound_wave_48dp,
                R.string.design_drawer_menu_title_sound,
                R.string.design_drawer_menu_description_sound
        ));
        /* Add Font Manager */
        menusLayout.addView(addDrawerItem(
                7,
                false,
                R.drawable.ic_font_48dp,
                R.string.design_drawer_menu_title_font,
                R.string.design_drawer_menu_description_font
        ));
        /* Add Java Manager */
        menusLayout.addView(addDrawerItem(
                8,
                false,
                R.drawable.java_96,
                R.string.text_title_menu_java,
                R.string.text_subtitle_menu_java
        ));
        /* Add Resource Manager */
        menusLayout.addView(addDrawerItem(
                9,
                false,
                R.drawable.file_app_icon,
                R.string.text_title_menu_resource,
                R.string.text_subtitle_menu_resource
        ));
        /* Add Asset Manager */
        menusLayout.addView(addDrawerItem(
                10,
                false,
                R.drawable.file_48_blue,
                R.string.text_title_menu_assets,
                R.string.text_subtitle_menu_assets
        ));
        /* Add Permission Manager */
        menusLayout.addView(addDrawerItem(
                11,
                false,
                R.drawable.plugin_purple_96,
                R.string.text_title_menu_permission,
                R.string.text_subtitle_menu_permission
        ));
        /* Add AppCompat Injection Manager */
        menusLayout.addView(addDrawerItem(
                12,
                false,
                R.drawable.ic_property_inject,
                R.string.design_drawer_menu_injection,
                R.string.design_drawer_menu_injection_subtitle
        ));
        /* Add AndroidManifest Manager */
        menusLayout.addView(addDrawerItem(
                13,
                false,
                R.drawable.icon8_code_am,
                R.string.design_drawer_menu_androidmanifest,
                R.string.design_drawer_menu_androidmanifest_subtitle
        ));
        /* Add Used Custom Blocks */
        menusLayout.addView(addDrawerItem(
                21,
                false,
                R.drawable.block_96_blue,
                R.string.design_drawer_menu_customblocks,
                R.string.design_drawer_menu_customblocks_subtitle
        ));
        /* Add Local library Manager */
        menusLayout.addView(addDrawerItem(
                14,
                false,
                R.drawable.open_box_48,
                R.string.text_title_menu_local_library,
                R.string.text_subtitle_menu_local_library
        ));
        /* Add Native library Manager */
        menusLayout.addView(addDrawerItem(20,
                false,
                R.drawable.cpp,
                R.string.design_drawer_menu_nativelibs,
                R.string.design_drawer_menu_nativelibs_subtitle));
        /* Add ProGuard Manager */
        menusLayout.addView(addDrawerItem(17,
                false,
                R.drawable.connected_96,
                R.string.design_drawer_menu_proguard,
                R.string.design_drawer_menu_proguard_subtitle));
        /* Add StringFog Manager */
        /* INCLUDES SECTION SEPARATOR */
        menusLayout.addView(addDrawerItem(18,
                true,
                R.drawable.color_lock_96,
                R.string.design_drawer_menu_stringfog,
                R.string.design_drawer_menu_stringfog_subtitle));
        /* Add Source Code Viewer */
        menusLayout.addView(addDrawerItem(16,
                false,
                R.drawable.code_icon,
                R.string.design_drawer_menu_title_source_code,
                R.string.design_drawer_menu_description_source_code));
        /* Add Direct Code Editor */
        menusLayout.addView(addDrawerItem(19,
                false,
                R.drawable.notes_alt2,
                R.string.design_drawer_menu_title_editor_code,
                R.string.design_drawer_menu_subtitle_editor_code));
    }

    @Override
    public void onClick(View view) {
        if (!mB.a()) {
            if (context instanceof DesignActivity) {
                DesignActivity designActivity = (DesignActivity) context;
                switch ((Integer) view.getTag()) {
                    case 1:
                       // designActivity.t();
                        return;

                    case 3:
                        designActivity.toLibraryManager();
                        return;

                    case 4:
                       designActivity.toManageViewActivity();
                        return;

                    case 5:
                       designActivity.toManageImageActivity();
                        return;

                    case 6:
                        designActivity.toManageSoundActivity();
                        return;

                    case 7:
                        designActivity.toManageFontActivity();
                        return;

                    case 8:
                        designActivity.toJava();
                        return;

                    case 9:
                        designActivity.toResource();
                        return;

                    case 10:
                       designActivity.toAssets();
                        return;

                    case 11:
                        designActivity.toPermission();
                        return;

                    case 12:
                      //  designActivity.toAppCompatInjection();
                        return;

                    case 13:
                    //    designActivity.toAndroidManifest();
                        return;

                    case 14:
                        designActivity.toLocalLibrary();
                        return;

                    case 15:
                       // designActivity.toBroadcast();
                        return;

                    case 16:
                        designActivity.toSrcViewer();
                        return;

                    case 17:
                        designActivity.toProguard();
                        return;

                    case 18:
                        designActivity.toStringfog();
                        return;

                    case 19:
                      //  designActivity.zz();
                        return;

                    case 20:
                        designActivity.toNativelibs();
                        return;

                    case 21:
                       // designActivity.toCustomBlocks();
                       designActivity.showCustomBlocksDialog();
                        return;

                    case 2:
                    default:
                }
            }
        }
    }

    static class DrawerItem extends LinearLayout {

        public int tag;
        public ImageView imgIcon;
        public TextView titleTextView;
        public TextView subTitleTextView;
        public View subSeparator;
        public View separator;
        public LinearLayout subItems;

        public DrawerItem(Context context) {
            super(context);
            new DrawerItem(context, 0);
        }

        public DrawerItem(Context context, AttributeSet set) {
            super(context, set);
            new DrawerItem(context, 0);
        }

        public DrawerItem(Context context, int tag) {
            super(context);
            initialize(context, tag);
        }

        public void setContent(int iconResId, String rootTitleText, String subTitleText) {
            imgIcon.setImageResource(iconResId);
            titleTextView.setText(rootTitleText);
            subTitleTextView.setText(subTitleText);
        }

        public final void initialize(Context context, int tag) {
            this.tag = tag;
            wB.a(context, this, R.layout.design_drawer_item);
            imgIcon = findViewById(R.id.img_icon);
            titleTextView = findViewById(R.id.tv_root_title);
            subTitleTextView = findViewById(R.id.tv_sub_title);
            subSeparator = findViewById(R.id.sub_separator);
            separator = findViewById(R.id.separator);
            subItems = findViewById(R.id.sub_items);
        }

        public void setSeparatorVisibility(boolean visible) {
            separator.setVisibility(visible ? VISIBLE : GONE);
        }

        public void setSubSeparatorVisibility(boolean visible) {
            subSeparator.setVisibility(visible ? VISIBLE : GONE);
        }
    }
}