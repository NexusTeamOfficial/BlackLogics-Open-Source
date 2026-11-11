
package com.nexusteam.blacklogics.editor.layout;

import android.util.Log;

import com.nexusteam.blacklogics.editor.layout.model.LayoutData;
import com.nexusteam.blacklogics.sdk.utils.AttributeUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public final class LayoutLoader {


    private static final String TAG            = "LayoutLoader";
    private static final String BIN_FILE_NAME   = "layout.bin";
    private static final String STORE_FILE_NAME = "layoutstore";
    private static final byte[] MASK_KEY = "NEXUS_LAYOUT_KEY_7734".getBytes();

    private static final String ATTR_ANDROID_ID  = "android:id";
    private static final String ATTR_APP_CV      = "app:customView";

    private LayoutLoader() {}


    public static Map<String, String> extractWidgetsFromLayout(
            String activityName,
            String layoutDirPath) {

        Map<String, String> widgets = new LinkedHashMap<String, String>();
        if (isBlank(activityName) || isBlank(layoutDirPath)) return widgets;

        String xml = resolveLayoutXml(activityName, layoutDirPath);
        if (xml != null && !xml.isEmpty()) {
            parseWidgetsFromXml(xml, widgets, null);
        }
        return widgets;
    }

    public static String getLayoutXml(String activityName, String layoutDirPath) {
        if (isBlank(activityName) || isBlank(layoutDirPath)) return null;
        return resolveLayoutXml(activityName, layoutDirPath);
    }

    public static Object[] extractWidgetsAndCustomViews(
            String activityName,
            String layoutDirPath) {

        Map<String, String> widgets     = new LinkedHashMap<String, String>();
        Map<String, String> customViews = new LinkedHashMap<String, String>();

        if (isBlank(activityName) || isBlank(layoutDirPath)) {
            return new Object[]{ widgets, customViews };
        }

        String xml = resolveLayoutXml(activityName, layoutDirPath);
        if (xml != null && !xml.isEmpty()) {
            parseWidgetsFromXml(xml, widgets, customViews);
        }
        return new Object[]{ widgets, customViews };
    }

    public static Map<String, String> getAllLayoutXmls(String layoutDirPath) {
        Map<String, String> result = new LinkedHashMap<String, String>();
        if (isBlank(layoutDirPath)) return result;

        File binFile = new File(layoutDirPath, BIN_FILE_NAME);
        if (binFile.exists()) {
            try {
                ArrayList<LayoutData> layouts = readBinaryLayouts(binFile);
                for (LayoutData data : layouts) {
                    if (data != null && !isBlank(data.name) && !isBlank(data.xml)) {
                        result.put(data.name, data.xml);
                    }
                }
            } catch (Exception e) {
                Log.w(TAG, "getAllLayoutXmls: binary read failed — " + e.getMessage());
            }
        }

        File storeFile = new File(layoutDirPath, STORE_FILE_NAME);
        if (storeFile.exists()) {
            try {
                ArrayList<LayoutEntry> entries = readStoreEntries(storeFile);
                for (int i = 0; i < entries.size(); i++) {
                    LayoutEntry entry = entries.get(i);
                    if (isBlank(entry.name)) continue;
                    String xml = resolveXmlFromEntry(entry);
                    if (!isBlank(xml)) {
                        result.put(entry.name, xml);
                    }
                }
            } catch (Exception e) {
                Log.w(TAG, "getAllLayoutXmls: layoutstore read failed — " + e.getMessage());
            }
        }

        return result;
    }


    private static String resolveLayoutXml(String activityName, String layoutDirPath) {

        File storeFile = new File(layoutDirPath, STORE_FILE_NAME);
        if (storeFile.exists()) {
            String xml = lookupXmlInStore(storeFile, activityName);
            if (!isBlank(xml)) return xml;
        }

        File binFile = new File(layoutDirPath, BIN_FILE_NAME);
        if (binFile.exists()) {
            String xml = lookupXmlInBinary(binFile, activityName);
            if (!isBlank(xml)) return xml;
        }

        Log.d(TAG, "resolveLayoutXml: no XML found for '" + activityName + "'");
        return null;
    }


    private static class LayoutEntry {
        String name;
        String xml;
        ArrayList<ViewNode> roots = new ArrayList<ViewNode>();
    }

    private static class ViewNode {
        String tag;
        boolean isPlaceholder;
        boolean isPlaceholderWebView;
        String widgetTag;
        String id;
        ArrayList<String[]> attributes = new ArrayList<String[]>();
        ArrayList<ViewNode> children = new ArrayList<ViewNode>();
    }

    private static byte[] applyMask(byte[] data) {
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = (byte) (data[i] ^ MASK_KEY[i % MASK_KEY.length]);
        }
        return result;
    }

    private static ArrayList<LayoutEntry> readStoreEntries(File storeFile) {
        ArrayList<LayoutEntry> entries = new ArrayList<LayoutEntry>();
        if (!storeFile.exists()) return entries;

        FileInputStream fis = null;
        DataInputStream in = null;
        try {
            byte[] raw = new byte[(int) storeFile.length()];
            fis = new FileInputStream(storeFile);
            fis.read(raw);

            byte[] plain = applyMask(raw);
            in = new DataInputStream(new ByteArrayInputStream(plain));

            in.readInt();
            int entryCount = in.readInt();
            for (int i = 0; i < entryCount; i++) {
                LayoutEntry entry = new LayoutEntry();
                entry.name = in.readUTF();
                entry.xml = in.readUTF();
                int rootCount = in.readInt();
                for (int r = 0; r < rootCount; r++) {
                    entry.roots.add(readViewNode(in));
                }
                entries.add(entry);
            }
        } catch (Exception e) {
            Log.w(TAG, "readStoreEntries failed: " + e.getMessage());
            entries = new ArrayList<LayoutEntry>();
        } finally {
            try { if (in != null) in.close(); } catch (Exception ignored) {}
            try { if (fis != null) fis.close(); } catch (Exception ignored) {}
        }
        return entries;
    }

    private static ViewNode readViewNode(DataInputStream in) throws Exception {
        ViewNode node = new ViewNode();
        node.tag = in.readUTF();
        node.isPlaceholder = in.readBoolean();
        node.isPlaceholderWebView = in.readBoolean();
        node.widgetTag = in.readUTF();
        node.id = in.readUTF();

        int attrCount = in.readInt();
        for (int i = 0; i < attrCount; i++) {
            String name = in.readUTF();
            String value = in.readUTF();
            node.attributes.add(new String[]{name, value});
        }

        int childCount = in.readInt();
        for (int i = 0; i < childCount; i++) {
            node.children.add(readViewNode(in));
        }
        return node;
    }

    private static String lookupXmlInStore(File storeFile, String activityName) {
        try {
            ArrayList<LayoutEntry> entries = readStoreEntries(storeFile);

            for (int i = 0; i < entries.size(); i++) {
                LayoutEntry entry = entries.get(i);
                if (activityName.equals(entry.name)) {
                    String xml = resolveXmlFromEntry(entry);
                    if (!isBlank(xml)) return xml;
                }
            }

            String normalised = normaliseLayoutName(activityName);
            if (!normalised.equals(activityName)) {
                for (int i = 0; i < entries.size(); i++) {
                    LayoutEntry entry = entries.get(i);
                    if (normalised.equals(entry.name)) {
                        String xml = resolveXmlFromEntry(entry);
                        if (!isBlank(xml)) return xml;
                    }
                }
            }

            for (int i = 0; i < entries.size(); i++) {
                LayoutEntry entry = entries.get(i);
                if (namesEquivalent(activityName, entry.name)) {
                    String xml = resolveXmlFromEntry(entry);
                    if (!isBlank(xml)) return xml;
                }
            }

        } catch (Exception e) {
            Log.w(TAG, "lookupXmlInStore failed for '" + activityName + "': " + e.getMessage());
        }
        return null;
    }

    private static String resolveXmlFromEntry(LayoutEntry entry) {
        if (!isBlank(entry.xml)) return entry.xml;

        if (entry.roots != null && !entry.roots.isEmpty()) {
            return reconstructXmlFromViewTree(entry.roots);
        }
        return null;
    }

    private static String reconstructXmlFromViewTree(ArrayList<ViewNode> roots) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        for (int i = 0; i < roots.size(); i++) {
            appendViewNodeXml(roots.get(i), sb, 0);
        }
        return sb.toString();
    }

    private static void appendViewNodeXml(ViewNode node, StringBuilder sb, int depth) {
        String tag = resolveViewTag(node);
        String indent = repeat("    ", depth);

        sb.append(indent).append("<").append(tag);

        for (int i = 0; i < node.attributes.size(); i++) {
            String[] pair = node.attributes.get(i);
            String k = pair[0];
            String v = pair[1] != null ? pair[1] : "";
            if (!k.contains(":") && !k.startsWith("xmlns")) {
                k = "android:" + k;
            }
            sb.append("\n").append(indent).append("    ")
              .append(k).append("=\"").append(escapeXmlValue(v)).append("\"");
        }

        if (!isBlank(node.id) && !hasAttr(node, ATTR_ANDROID_ID)) {
            sb.append("\n").append(indent).append("    ")
              .append("android:id=\"@+id/").append(node.id).append("\"");
        }

        if (!node.children.isEmpty()) {
            sb.append(">\n");
            for (int i = 0; i < node.children.size(); i++) {
                appendViewNodeXml(node.children.get(i), sb, depth + 1);
            }
            sb.append(indent).append("</").append(tag).append(">\n");
            return;
        }

        sb.append(" />\n");
    }

    private static String resolveViewTag(ViewNode node) {
        if (node.isPlaceholderWebView) return "WebView";
        if (node.isPlaceholder && !isBlank(node.widgetTag)) return node.widgetTag;

        String tag = !isBlank(node.tag) ? node.tag : "View";
        if (tag.contains(".")) {
            tag = tag.substring(tag.lastIndexOf('.') + 1);
        }
        return tag;
    }

    private static boolean hasAttr(ViewNode node, String key) {
        for (int i = 0; i < node.attributes.size(); i++) {
            if (key.equals(node.attributes.get(i)[0])) return true;
        }
        return false;
    }


    private static String normaliseLayoutName(String name) {
        if (isBlank(name)) return "";

        name = name.replace(".java", "").replace(".xml", "");

        StringBuilder snake = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (Character.isUpperCase(c) && i > 0) snake.append('_');
            snake.append(Character.toLowerCase(c));
        }

        String[] tokens = snake.toString().split("_");

        for (int i = 1; i < tokens.length; i++) {
            String key = tokens[i];
            int j = i - 1;
            while (j >= 0 && tokens[j].compareTo(key) > 0) {
                tokens[j + 1] = tokens[j];
                j--;
            }
            tokens[j + 1] = key;
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < tokens.length; i++) {
            if (i > 0) result.append('_');
            result.append(tokens[i]);
        }
        return result.toString();
    }

    private static boolean namesEquivalent(String a, String b) {
        if (a == null || b == null) return false;
        if (a.equals(b)) return true;
        if (a.equalsIgnoreCase(b)) return true;
        return normaliseLayoutName(a).equals(normaliseLayoutName(b));
    }


    private static String mapTagToWidgetType(String tag) {
        if (isBlank(tag)) return "View";
        if (tag.contains(".")) return tag.substring(tag.lastIndexOf('.') + 1);
        return tag;
    }


    private static String escapeXmlValue(String value) {
        if (value == null) return "";
        return value.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;");
    }


    private static String lookupXmlInBinary(File binFile, String activityName) {
        try {
            ArrayList<LayoutData> layouts = readBinaryLayouts(binFile);

            for (LayoutData data : layouts) {
                if (data != null && activityName.equals(data.name)
                        && !isBlank(data.xml)) {
                    return data.xml;
                }
            }

            for (LayoutData data : layouts) {
                if (data != null && !isBlank(data.xml)
                        && namesEquivalent(activityName, data.name)) {
                    return data.xml;
                }
            }

        } catch (Exception e) {
            Log.w(TAG, "lookupXmlInBinary failed for '" + activityName
                    + "': " + e.getMessage());
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static ArrayList<LayoutData> readBinaryLayouts(File file) throws Exception {
        FileInputStream  fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            Object obj = ois.readObject();
            if (obj instanceof ArrayList) {
                return (ArrayList<LayoutData>) obj;
            }
            return new ArrayList<LayoutData>();
        } finally {
            if (ois != null) { try { ois.close(); } catch (IOException ignored) {} }
            if (fis != null) { try { fis.close(); } catch (IOException ignored) {} }
        }
    }


    private static void parseWidgetsFromXml(String xml,
                                             Map<String, String> widgets,
                                             Map<String, String> customViews) {
        if (isBlank(xml)) return;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xml));

            int eventType;
            while ((eventType = parser.next()) != XmlPullParser.END_DOCUMENT) {
                if (eventType != XmlPullParser.START_TAG) continue;

                String tag        = parser.getName();
                String widgetType = mapTagToWidgetType(tag);

                String foundId         = null;
                String foundCustomView = null;

                for (int i = 0; i < parser.getAttributeCount(); i++) {
                    String attrName  = parser.getAttributeName(i);
                    String attrValue = parser.getAttributeValue(i);

                    if (ATTR_ANDROID_ID.equals(attrName) || "id".equals(attrName)) {
                        foundId = AttributeUtils.getName(attrValue);
                    } else if (customViews != null && ATTR_APP_CV.equals(attrName)) {
                        foundCustomView = attrValue != null ? attrValue.trim() : null;
                    }
                }

                if (!isBlank(foundId)) {
                    widgets.put(foundId, widgetType);
                    if (customViews != null && !isBlank(foundCustomView)
                            && !"none".equalsIgnoreCase(foundCustomView)) {
                        customViews.put(foundId, foundCustomView);
                    }
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "parseWidgetsFromXml failed", e);
        }
    }


    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private static String repeat(String s, int times) {
        if (times <= 0) return "";
        StringBuilder sb = new StringBuilder(s.length() * times);
        for (int i = 0; i < times; i++) sb.append(s);
        return sb.toString();
    }
}
