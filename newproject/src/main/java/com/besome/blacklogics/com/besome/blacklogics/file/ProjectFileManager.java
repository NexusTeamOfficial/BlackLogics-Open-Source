package com.besome.blacklogics.file;

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

import com.besome.blacklogics.SketchwareUtil;

import com.nexusteam.internal.os.layouteditor.util.TheBlockLogicsUtil;

public class ProjectFileManager {

    private static final String BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/.blacklogics/data/";
    private final Context context;
    private final String sc_id;

    public ProjectFileManager(Context context, String sc_id) {
        this.context = context;
        this.sc_id = sc_id;
    }

    /**
     * Saves widget IDs extracted from the XML layout string to a JSON file named after the layoutName
     * in the path: emulated/0/.blacklogics/<sc_id>/views/<layoutName>.json
     *
     * @param xmlLayout  The XML layout string to parse
     * @param layoutName The name of the layout (used as the file name)
     * @return true if saved successfully, false otherwise
     */
    public boolean saveWidgetIdsFromXml(String xmlLayout, String layoutName) {
        if (xmlLayout == null || xmlLayout.isEmpty() || layoutName == null || layoutName.isEmpty()) {
          //  SketchwareUtil.showMessage(context, "Invalid XML layout or layout name");
            return false;
        }

        try {
            // Create directory if it doesn't exist
            File viewsDir = new File(BASE_PATH + sc_id + "/views");
            if (!viewsDir.exists() && !viewsDir.mkdirs()) {
                //SketchwareUtil.showMessage(context, "Failed to create views directory");
                return false;
            }

            // Parse XML to extract widget IDs
            List<Pair<String, String>> widgetData = parseWidgetIdsFromXml(xmlLayout);

            // Create JSON object
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

            // Write to file
            File outputFile = new File(viewsDir, layoutName + ".json");
            try (FileWriter writer = new FileWriter(outputFile)) {
                writer.write(jsonObject.toString(4)); // Pretty print with indent
                writer.flush();
            }

          //  SketchwareUtil.showMessage(context, "Widget IDs saved to " + outputFile.getAbsolutePath());
            return true;

        } catch (JSONException e) {
          //  SketchwareUtil.showMessage(context, "JSON Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (IOException e) {
          //  SketchwareUtil.showMessage(context, "File Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Parses the XML layout string to extract widget IDs and their types
     *
     * @param xmlLayout The XML layout string
     * @return List of Pair<WidgetType, WidgetID> (e.g., Pair<"TextView", "@+id/textview1">)
     */
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
            //SketchwareUtil.showMessage(context, "XML Parsing Error: " + e.getMessage());
            e.printStackTrace();
        }

        return widgetData;
    }

    /**
     * Retrieves widget IDs for a specific layout, optionally filtered by widget type
     *
     * @param layoutName The layout name (e.g., "main")
     * @param widgetType The widget type to filter (e.g., "TextView", "ImageView", null for all)
     * @return List of Pair<Integer, String> (index, widget ID) compatible with showSelectPairPopup
     */
    public List<Pair<Integer, String>> getWidgetIds(String layoutName, String widgetType) {
        List<Pair<Integer, String>> result = new ArrayList<>();
        try {
            // Read the JSON file
            File jsonFile = new File(BASE_PATH + sc_id + "/views/" + layoutName + ".json");
            if (!jsonFile.exists()) {
               // SketchwareUtil.showMessage(context, "Widget IDs file not found for " + layoutName);
                return result;
            }

            String jsonContent = TheBlockLogicsUtil.readFile(jsonFile.getAbsolutePath());
            JSONObject jsonObject = new JSONObject(jsonContent);
            JSONArray widgetIds = jsonObject.getJSONArray("widget_ids");

            for (int i = 0; i < widgetIds.length(); i++) {
                JSONObject widgetObj = widgetIds.getJSONObject(i);
                String type = widgetObj.getString("type");
                String id = widgetObj.getString("id");

                // Filter by widget type if specified
                if (widgetType == null || isWidgetTypeMatch(type, widgetType)) {
                    result.add(new Pair<>(i, id));
                }
            }
        } catch (JSONException e) {
          //  SketchwareUtil.showMessage(context, "Error reading widget IDs: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Checks if the widget type matches the requested type
     *
     * @param xmlType The type from XML (e.g., "TextView", "Button")
     * @param requestedType The type requested (e.g., "textview", "imageview", "listSpn")
     * @return true if the types match
     */
    private boolean isWidgetTypeMatch(String xmlType, String requestedType) {
        switch (requestedType.toLowerCase()) {
            case "view":
                return true; // All views
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

    /**
     * Converts Java file name to XML layout name
     *
     * @param javaFileName The Java file name (e.g., "MainActivity.java")
     * @return The corresponding XML layout name (e.g., "main")
     */
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