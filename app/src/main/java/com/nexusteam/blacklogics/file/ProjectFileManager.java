package com.nexusteam.blacklogics.file;

import android.content.Context;
import android.os.Environment;
import android.util.Pair;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import com.nexusteam.blacklogics.SketchwareUtil;

import com.nexusteam.internal.os.layouteditor.util.TheBlockLogicsUtil;

public class ProjectFileManager {

    private static final String BASE_PATH = "/storage/emulated/0" + "/.blacklogics/data/";
    private final Context context;
    private final String sc_id;

    public ProjectFileManager(Context context, String sc_id) {
        this.context = context;
        this.sc_id = sc_id;
    }

    public boolean saveWidgetIdsFromXml(String xmlLayout, String layoutName) {
        if (xmlLayout == null || xmlLayout.isEmpty() || layoutName == null || layoutName.isEmpty()) {

            return false;
        }

        try {

            File viewsDir = new File(BASE_PATH + sc_id + "/views");
            if (!viewsDir.exists() && !viewsDir.mkdirs()) {

                return false;
            }


            List<Pair<String, String>> widgetData = parseWidgetIdsFromXml(xmlLayout);


            JSONObject jsonObject = new JSONObject();
            jsonObject.put("layout_name", layoutName);
            jsonObject.put("sc_id", sc_id);

            JSONArray idsArray = new JSONArray();
            for (Pair<String, String> data : widgetData) {
                JSONObject widgetObj = new JSONObject();
                widgetObj.put("type", data.first);
                widgetObj.put("id", data.second);
                idsArray.put(widgetObj);
            }
            jsonObject.put("widget_ids", idsArray);


            File outputFile = new File(viewsDir, layoutName + ".json");
            try (FileWriter writer = new FileWriter(outputFile)) {
                writer.write(jsonObject.toString(4));
                writer.flush();
            }


            return true;

        } catch (JSONException e) {

            e.printStackTrace();
            return false;
        } catch (IOException e) {

            e.printStackTrace();
            return false;
        }
    }

    private List<Pair<String, String>> parseWidgetIdsFromXml(String xmlLayout) {
        List<Pair<String, String>> widgetData = new ArrayList<>();

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xmlLayout));

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String tagName = parser.getName();
                    String widgetId = null;
                    for (int i = 0; i < parser.getAttributeCount(); i++) {
                        if ("android:id".equals(parser.getAttributeName(i))) {
                            widgetId = parser.getAttributeValue(i);
                            break;
                        }
                    }
                    if (widgetId != null && widgetId.startsWith("@+id/")) {
                        widgetData.add(new Pair<>(tagName, widgetId));
                    }
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException | IOException e) {

            e.printStackTrace();
        }

        return widgetData;
    }

    public List<Pair<Integer, String>> getWidgetIds(String layoutName, String widgetType) {
        List<Pair<Integer, String>> result = new ArrayList<>();
        try {

            File jsonFile = new File(BASE_PATH + sc_id + "/views/" + layoutName + ".json");
            if (!jsonFile.exists()) {

                return result;
            }

            String jsonContent = TheBlockLogicsUtil.readFile(jsonFile.getAbsolutePath());
            JSONObject jsonObject = new JSONObject(jsonContent);
            JSONArray widgetIds = jsonObject.getJSONArray("widget_ids");

            for (int i = 0; i < widgetIds.length(); i++) {
                JSONObject widgetObj = widgetIds.getJSONObject(i);
                String type = widgetObj.getString("type");
                String id = widgetObj.getString("id");


                if (widgetType == null || isWidgetTypeMatch(type, widgetType)) {
                    result.add(new Pair<>(i, id));
                }
            }
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return result;
    }

    private boolean isWidgetTypeMatch(String xmlType, String requestedType) {
        switch (requestedType.toLowerCase()) {
            case "view":
                return true;
            case "textview":
                return xmlType.equals("WidgetTextView") || xmlType.equals("WidgetButton") || xmlType.equals("WidgetEditText");
            case "imageview":
                return xmlType.equals("WidgetImageView");
            case "checkbox":
                return xmlType.equals("WidgetCheckBox");
            case "listview":
                return xmlType.equals("WidgetListView");
            case "spinner":
                return xmlType.equals("WidgetSpinner");
            case "listspn":
                return xmlType.equals("WidgetListView") || xmlType.equals("WidgetSpinner");
            default:
                return false;
        }
    }

    public static String getXmlNameFromJava(String javaFileName) {
        if (javaFileName == null || javaFileName.isEmpty()) {
            return "main";
        }
        String baseName = javaFileName.replace(".java", "");
        if (baseName.equals("MainActivity")) {
            return "main";
        }
        return baseName.toLowerCase();
    }
}