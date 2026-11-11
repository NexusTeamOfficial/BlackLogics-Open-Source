package com.nexusteam.blacklogics.generator.source;

import android.content.Context;
import android.util.Log;

import com.nexusteam.blacklogics.model.ProjectTemplate;

import java.io.File;
import com.nexusteam.internal.os.layouteditor.util.TheBlockLogicsUtil;
import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

public class TemplateLayoutWriter {

    public static void apply(File projectDir, ProjectTemplate template) {
        if (projectDir == null || template == null) return;
        try {
            File layoutDir = new File(projectDir, "app/src/main/res/layout");
            if (!layoutDir.exists()) {
                layoutDir.mkdirs();
            }
            String xml = buildLayoutXml(template.getType());
            write(new File(layoutDir, "main.xml"), xml);

            if (template.getType() == ProjectTemplate.TYPE_DRAWER) {
                write(new File(layoutDir, "nav_header_main.xml"), buildNavHeader());
            }
            if (template.getType() == ProjectTemplate.TYPE_BOTTOM_NAV
                    || template.getType() == ProjectTemplate.TYPE_TABS) {
                write(new File(layoutDir, "fragment_home.xml"), buildSimpleFragment("Home"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void write(File file, String content) throws Exception {
        FileOutputStream fos = new FileOutputStream(file);
        try {
            fos.write(content.getBytes(StandardCharsets.UTF_8));
        } finally {
            fos.close();
        }
    }

    private static String buildLayoutXml(int type) {
        switch (type) {
            case ProjectTemplate.TYPE_BASIC:
                return layoutBasicToolbar();
            case ProjectTemplate.TYPE_DRAWER:
                return layoutDrawer();
            case ProjectTemplate.TYPE_BOTTOM_NAV:
                return layoutBottomNav();
            case ProjectTemplate.TYPE_TABS:
                return layoutTabs();
            case ProjectTemplate.TYPE_FULLSCREEN:
                return layoutFullscreen();
            case ProjectTemplate.TYPE_LOGIN:
                return layoutLogin();
            case ProjectTemplate.TYPE_FAB:
                return layoutBasicToolbar();
            case ProjectTemplate.TYPE_EMPTY:
            default:
                return layoutEmpty();
        }
    }

    private static String layoutEmpty() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
            + "<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n"
            + "    android:layout_width=\"match_parent\"\n"
            + "    android:layout_height=\"match_parent\"\n"
            + "    android:orientation=\"vertical\"\n"
            + "    android:gravity=\"center\"\n"
            + "    android:background=\"?android:attr/colorBackground\"\n"
            + "    android:padding=\"24dp\">\n"
            + "\n"
            + "    <TextView\n"
            + "        android:id=\"@+id/tv_hello\"\n"
            + "        android:layout_width=\"wrap_content\"\n"
            + "        android:layout_height=\"wrap_content\"\n"
            + "        android:text=\"Hello World\"\n"
            + "        android:textSize=\"18sp\"/>\n"
            + "\n"
            + "</LinearLayout>\n";
    }

    private static String layoutBasicToolbar() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
            + "<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n"
            + "    xmlns:app=\"http://schemas.android.com/apk/res-auto\"\n"
            + "    android:layout_width=\"match_parent\"\n"
            + "    android:layout_height=\"match_parent\"\n"
            + "    android:orientation=\"vertical\">\n"
            + "\n"
            + "    <androidx.appcompat.widget.Toolbar\n"
            + "        android:id=\"@+id/toolbar\"\n"
            + "        android:layout_width=\"match_parent\"\n"
            + "        android:layout_height=\"?attr/actionBarSize\"\n"
            + "        android:background=\"?attr/colorPrimary\"\n"
            + "        android:theme=\"@style/ThemeOverlay.AppCompat.Dark.ActionBar\"\n"
            + "        app:titleTextColor=\"@android:color/white\"/>\n"
            + "\n"
            + "    <FrameLayout\n"
            + "        android:id=\"@+id/content\"\n"
            + "        android:layout_width=\"match_parent\"\n"
            + "        android:layout_height=\"0dp\"\n"
            + "        android:layout_weight=\"1\"\n"
            + "        android:padding=\"16dp\">\n"
            + "\n"
            + "        <TextView\n"
            + "            android:layout_width=\"wrap_content\"\n"
            + "            android:layout_height=\"wrap_content\"\n"
            + "            android:layout_gravity=\"center\"\n"
            + "            android:text=\"Main content\"\n"
            + "            android:textSize=\"16sp\"/>\n"
            + "    </FrameLayout>\n"
            + "\n"
            + "</LinearLayout>\n";
    }

    private static String layoutDrawer() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
            + "<androidx.drawerlayout.widget.DrawerLayout\n"
            + "    xmlns:android=\"http://schemas.android.com/apk/res/android\"\n"
            + "    xmlns:app=\"http://schemas.android.com/apk/res-auto\"\n"
            + "    android:id=\"@+id/drawer_layout\"\n"
            + "    android:layout_width=\"match_parent\"\n"
            + "    android:layout_height=\"match_parent\">\n"
            + "\n"
            + "    <LinearLayout\n"
            + "        android:layout_width=\"match_parent\"\n"
            + "        android:layout_height=\"match_parent\"\n"
            + "        android:orientation=\"vertical\">\n"
            + "\n"
            + "        <androidx.appcompat.widget.Toolbar\n"
            + "            android:id=\"@+id/toolbar\"\n"
            + "            android:layout_width=\"match_parent\"\n"
            + "            android:layout_height=\"?attr/actionBarSize\"\n"
            + "            android:background=\"?attr/colorPrimary\"\n"
            + "            android:theme=\"@style/ThemeOverlay.AppCompat.Dark.ActionBar\"\n"
            + "            app:titleTextColor=\"@android:color/white\"/>\n"
            + "\n"
            + "        <FrameLayout\n"
            + "            android:id=\"@+id/content\"\n"
            + "            android:layout_width=\"match_parent\"\n"
            + "            android:layout_height=\"0dp\"\n"
            + "            android:layout_weight=\"1\"\n"
            + "            android:padding=\"16dp\">\n"
            + "            <TextView\n"
            + "                android:layout_width=\"wrap_content\"\n"
            + "                android:layout_height=\"wrap_content\"\n"
            + "                android:layout_gravity=\"center\"\n"
            + "                android:text=\"Drawer content\"\n"
            + "                android:textSize=\"16sp\"/>\n"
            + "        </FrameLayout>\n"
            + "    </LinearLayout>\n"
            + "\n"
            + "    <com.google.android.material.navigation.NavigationView\n"
            + "        android:id=\"@+id/nav_view\"\n"
            + "        android:layout_width=\"wrap_content\"\n"
            + "        android:layout_height=\"match_parent\"\n"
            + "        android:layout_gravity=\"start\"\n"
            + "        app:headerLayout=\"@layout/nav_header_main\"/>\n"
            + "\n"
            + "</androidx.drawerlayout.widget.DrawerLayout>\n";
    }

    private static String layoutBottomNav() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
            + "<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n"
            + "    xmlns:app=\"http://schemas.android.com/apk/res-auto\"\n"
            + "    android:layout_width=\"match_parent\"\n"
            + "    android:layout_height=\"match_parent\"\n"
            + "    android:orientation=\"vertical\">\n"
            + "\n"
            + "    <androidx.appcompat.widget.Toolbar\n"
            + "        android:id=\"@+id/toolbar\"\n"
            + "        android:layout_width=\"match_parent\"\n"
            + "        android:layout_height=\"?attr/actionBarSize\"\n"
            + "        android:background=\"?attr/colorPrimary\"\n"
            + "        android:theme=\"@style/ThemeOverlay.AppCompat.Dark.ActionBar\"\n"
            + "        app:titleTextColor=\"@android:color/white\"/>\n"
            + "\n"
            + "    <FrameLayout\n"
            + "        android:id=\"@+id/fragment_container\"\n"
            + "        android:layout_width=\"match_parent\"\n"
            + "        android:layout_height=\"0dp\"\n"
            + "        android:layout_weight=\"1\"/>\n"
            + "\n"
            + "    <com.google.android.material.bottomnavigation.BottomNavigationView\n"
            + "        android:id=\"@+id/bottom_nav\"\n"
            + "        android:layout_width=\"match_parent\"\n"
            + "        android:layout_height=\"wrap_content\"\n"
            + "        android:background=\"?attr/colorSurface\"\n"
            + "        app:elevation=\"8dp\"/>\n"
            + "\n"
            + "</LinearLayout>\n";
    }

    private static String layoutTabs() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
            + "<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n"
            + "    xmlns:app=\"http://schemas.android.com/apk/res-auto\"\n"
            + "    android:layout_width=\"match_parent\"\n"
            + "    android:layout_height=\"match_parent\"\n"
            + "    android:orientation=\"vertical\">\n"
            + "\n"
            + "    <androidx.appcompat.widget.Toolbar\n"
            + "        android:id=\"@+id/toolbar\"\n"
            + "        android:layout_width=\"match_parent\"\n"
            + "        android:layout_height=\"?attr/actionBarSize\"\n"
            + "        android:background=\"?attr/colorPrimary\"\n"
            + "        android:theme=\"@style/ThemeOverlay.AppCompat.Dark.ActionBar\"\n"
            + "        app:titleTextColor=\"@android:color/white\"/>\n"
            + "\n"
            + "    <com.google.android.material.tabs.TabLayout\n"
            + "        android:id=\"@+id/tab_layout\"\n"
            + "        android:layout_width=\"match_parent\"\n"
            + "        android:layout_height=\"wrap_content\"\n"
            + "        android:background=\"?attr/colorPrimary\"\n"
            + "        app:tabTextColor=\"@android:color/white\"\n"
            + "        app:tabSelectedTextColor=\"@android:color/white\"\n"
            + "        app:tabIndicatorColor=\"@android:color/white\"/>\n"
            + "\n"
            + "    <androidx.viewpager.widget.ViewPager\n"
            + "        android:id=\"@+id/view_pager\"\n"
            + "        android:layout_width=\"match_parent\"\n"
            + "        android:layout_height=\"0dp\"\n"
            + "        android:layout_weight=\"1\"/>\n"
            + "\n"
            + "</LinearLayout>\n";
    }

    private static String layoutFullscreen() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
            + "<FrameLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n"
            + "    android:layout_width=\"match_parent\"\n"
            + "    android:layout_height=\"match_parent\"\n"
            + "    android:background=\"#000000\">\n"
            + "\n"
            + "    <TextView\n"
            + "        android:layout_width=\"wrap_content\"\n"
            + "        android:layout_height=\"wrap_content\"\n"
            + "        android:layout_gravity=\"center\"\n"
            + "        android:text=\"Fullscreen\"\n"
            + "        android:textColor=\"#FFFFFF\"\n"
            + "        android:textSize=\"18sp\"/>\n"
            + "\n"
            + "</FrameLayout>\n";
    }

    private static String layoutLogin() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
            + "<ScrollView xmlns:android=\"http://schemas.android.com/apk/res/android\"\n"
            + "    android:layout_width=\"match_parent\"\n"
            + "    android:layout_height=\"match_parent\"\n"
            + "    android:fillViewport=\"true\">\n"
            + "\n"
            + "    <LinearLayout\n"
            + "        android:layout_width=\"match_parent\"\n"
            + "        android:layout_height=\"wrap_content\"\n"
            + "        android:orientation=\"vertical\"\n"
            + "        android:gravity=\"center_horizontal\"\n"
            + "        android:padding=\"32dp\">\n"
            + "\n"
            + "        <TextView\n"
            + "            android:layout_width=\"wrap_content\"\n"
            + "            android:layout_height=\"wrap_content\"\n"
            + "            android:layout_marginTop=\"48dp\"\n"
            + "            android:layout_marginBottom=\"32dp\"\n"
            + "            android:text=\"Welcome\"\n"
            + "            android:textSize=\"28sp\"\n"
            + "            android:textStyle=\"bold\"/>\n"
            + "\n"
            + "        <EditText\n"
            + "            android:id=\"@+id/et_email\"\n"
            + "            android:layout_width=\"match_parent\"\n"
            + "            android:layout_height=\"wrap_content\"\n"
            + "            android:hint=\"Email\"\n"
            + "            android:inputType=\"textEmailAddress\"\n"
            + "            android:layout_marginBottom=\"12dp\"/>\n"
            + "\n"
            + "        <EditText\n"
            + "            android:id=\"@+id/et_password\"\n"
            + "            android:layout_width=\"match_parent\"\n"
            + "            android:layout_height=\"wrap_content\"\n"
            + "            android:hint=\"Password\"\n"
            + "            android:inputType=\"textPassword\"\n"
            + "            android:layout_marginBottom=\"24dp\"/>\n"
            + "\n"
            + "        <Button\n"
            + "            android:id=\"@+id/btn_login\"\n"
            + "            android:layout_width=\"match_parent\"\n"
            + "            android:layout_height=\"wrap_content\"\n"
            + "            android:text=\"Login\"/>\n"
            + "\n"
            + "    </LinearLayout>\n"
            + "</ScrollView>\n";
    }

    private static String buildNavHeader() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
            + "<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n"
            + "    android:layout_width=\"match_parent\"\n"
            + "    android:layout_height=\"176dp\"\n"
            + "    android:background=\"?attr/colorPrimary\"\n"
            + "    android:gravity=\"bottom\"\n"
            + "    android:orientation=\"vertical\"\n"
            + "    android:padding=\"16dp\">\n"
            + "\n"
            + "    <TextView\n"
            + "        android:layout_width=\"wrap_content\"\n"
            + "        android:layout_height=\"wrap_content\"\n"
            + "        android:text=\"Navigation\"\n"
            + "        android:textColor=\"@android:color/white\"\n"
            + "        android:textSize=\"18sp\"/>\n"
            + "\n"
            + "</LinearLayout>\n";
    }

    private static String buildSimpleFragment(String title) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
            + "<FrameLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n"
            + "    android:layout_width=\"match_parent\"\n"
            + "    android:layout_height=\"match_parent\"\n"
            + "    android:padding=\"16dp\">\n"
            + "    <TextView\n"
            + "        android:layout_width=\"wrap_content\"\n"
            + "        android:layout_height=\"wrap_content\"\n"
            + "        android:layout_gravity=\"center\"\n"
            + "        android:text=\"" + title + "\"\n"
            + "        android:textSize=\"16sp\"/>\n"
            + "</FrameLayout>\n";
    }


    private static final String STORE_FILE = "layoutstore";
    private static final int STORE_VERSION = 1;
    private static final byte[] MASK_KEY = "NEXUS_LAYOUT_KEY_7734".getBytes();

    public static void seedLayoutStore(Context context, String scId, int templateType) {
        if (context == null || scId == null) return;
        try {
            File dir = new File(android.os.Environment.getExternalStorageDirectory(),
                    ".blacklogics/data/" + scId);
            if (!dir.exists()) dir.mkdirs();
            File store = new File(dir, STORE_FILE);
            if (store.exists() && store.length() > 32) {
                Log.i("TemplateLayoutWriter", "layoutstore exists — skip seed");
                return;
            }

            String xml = xmlForTemplate(templateType);
            java.util.ArrayList nodes = nodesForTemplate(templateType);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(bos);
            out.writeInt(STORE_VERSION);
            out.writeInt(1);
            out.writeUTF("MainActivity");
            out.writeUTF(xml != null ? xml : "");
            out.writeInt(nodes.size());
            for (int i = 0; i < nodes.size(); i++) {
                writeNode(out, (SeedNode) nodes.get(i));
            }
            out.flush();
            byte[] masked = mask(bos.toByteArray());
            FileOutputStream fos = new FileOutputStream(store);
            fos.write(masked);
            fos.flush();
            fos.close();
            Log.i("TemplateLayoutWriter", "layoutstore seeded type=" + templateType);
        } catch (Throwable t) {
            Log.e("TemplateLayoutWriter", "seedLayoutStore failed", t);
        }
    }

    private static String xmlForTemplate(int type) {
        switch (type) {
            case ProjectTemplate.TYPE_LOGIN: return layoutLogin();
            case ProjectTemplate.TYPE_TABS: return layoutTabs();
            case ProjectTemplate.TYPE_BOTTOM_NAV: return layoutBottomNav();
            case ProjectTemplate.TYPE_DRAWER: return layoutDrawer();
            case ProjectTemplate.TYPE_BASIC:
            case ProjectTemplate.TYPE_FAB: return layoutBasicToolbar();
            case ProjectTemplate.TYPE_FULLSCREEN: return layoutFullscreen();
            default: return layoutEmpty();
        }
    }

    private static java.util.ArrayList nodesForTemplate(int type) {
        java.util.ArrayList list = new java.util.ArrayList();
        switch (type) {
            case ProjectTemplate.TYPE_LOGIN: {
                SeedNode root = node("android.widget.LinearLayout", "linear1",
                        attr("android:layout_width", "match_parent"),
                        attr("android:layout_height", "match_parent"),
                        attr("android:orientation", "vertical"),
                        attr("android:padding", "24dp"),
                        attr("android:gravity", "center_horizontal"));
                root.children.add(node("android.widget.TextView", "textview1",
                        attr("android:layout_width", "wrap_content"),
                        attr("android:layout_height", "wrap_content"),
                        attr("android:text", "Welcome"),
                        attr("android:textSize", "24sp"),
                        attr("android:textStyle", "bold"),
                        attr("android:layout_marginBottom", "24dp")));
                root.children.add(node("android.widget.EditText", "edittext1",
                        attr("android:layout_width", "match_parent"),
                        attr("android:layout_height", "wrap_content"),
                        attr("android:hint", "Email"),
                        attr("android:inputType", "textEmailAddress"),
                        attr("android:layout_marginBottom", "12dp")));
                root.children.add(node("android.widget.EditText", "edittext2",
                        attr("android:layout_width", "match_parent"),
                        attr("android:layout_height", "wrap_content"),
                        attr("android:hint", "Password"),
                        attr("android:inputType", "textPassword"),
                        attr("android:layout_marginBottom", "16dp")));
                root.children.add(node("android.widget.Button", "button1",
                        attr("android:layout_width", "match_parent"),
                        attr("android:layout_height", "wrap_content"),
                        attr("android:text", "Login")));
                list.add(root);
                break;
            }
            case ProjectTemplate.TYPE_TABS: {
                SeedNode root = node("android.widget.LinearLayout", "linear1",
                        attr("android:layout_width", "match_parent"),
                        attr("android:layout_height", "match_parent"),
                        attr("android:orientation", "vertical"));
                root.children.add(node("com.google.android.material.tabs.TabLayout", "tablayout1",
                        attr("android:layout_width", "match_parent"),
                        attr("android:layout_height", "wrap_content")));
                root.children.add(node("androidx.viewpager.widget.ViewPager", "viewpager1",
                        attr("android:layout_width", "match_parent"),
                        attr("android:layout_height", "0dp"),
                        attr("android:layout_weight", "1")));
                list.add(root);
                break;
            }
            case ProjectTemplate.TYPE_BOTTOM_NAV: {
                SeedNode root = node("android.widget.LinearLayout", "linear1",
                        attr("android:layout_width", "match_parent"),
                        attr("android:layout_height", "match_parent"),
                        attr("android:orientation", "vertical"));
                root.children.add(node("android.widget.FrameLayout", "framelayout1",
                        attr("android:layout_width", "match_parent"),
                        attr("android:layout_height", "0dp"),
                        attr("android:layout_weight", "1")));
                root.children.add(node("com.google.android.material.bottomnavigation.BottomNavigationView", "bottomnavigation1",
                        attr("android:layout_width", "match_parent"),
                        attr("android:layout_height", "wrap_content")));
                list.add(root);
                break;
            }
            default: {
                SeedNode root = node("android.widget.LinearLayout", "linear1",
                        attr("android:layout_width", "match_parent"),
                        attr("android:layout_height", "match_parent"),
                        attr("android:orientation", "vertical"));
                list.add(root);
                break;
            }
        }
        return list;
    }

    private static String[] attr(String name, String value) {
        return new String[]{name, value};
    }

    private static SeedNode node(String tag, String id, String[]... attrs) {
        SeedNode n = new SeedNode();
        n.tag = tag;
        n.id = id;
        for (int i = 0; i < attrs.length; i++) {
            n.attributes.add(attrs[i]);
        }
        n.attributes.add(0, attr("android:id", "@+id/" + id));
        return n;
    }

    private static void writeNode(DataOutputStream out, SeedNode node) throws Exception {
        out.writeUTF(node.tag != null ? node.tag : "View");
        out.writeBoolean(false);
        out.writeBoolean(false);
        out.writeUTF("");
        out.writeUTF(node.id != null ? node.id : "");
        out.writeInt(node.attributes.size());
        for (int i = 0; i < node.attributes.size(); i++) {
            String[] pair = (String[]) node.attributes.get(i);
            out.writeUTF(pair[0]);
            out.writeUTF(pair[1] != null ? pair[1] : "");
        }
        out.writeInt(node.children.size());
        for (int i = 0; i < node.children.size(); i++) {
            writeNode(out, (SeedNode) node.children.get(i));
        }
    }

    private static byte[] mask(byte[] data) {
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = (byte) (data[i] ^ MASK_KEY[i % MASK_KEY.length]);
        }
        return result;
    }

    private static class SeedNode {
        String tag;
        String id;
        java.util.ArrayList attributes = new java.util.ArrayList();
        java.util.ArrayList children = new java.util.ArrayList();
    }

}
