package b.b.b;

import android.util.Log;

import com.nexusteam.blacklogics.editor.layout.model.LayoutData;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class xr {

    private static final String TAG = "xr";

    private String projectPath;
    private String activityName;

    public xr(String projectPath, String activityName) {
        this.projectPath = projectPath;
        this.activityName = activityName;
    }

    /* ===================== PUBLIC APIs ===================== */

    public List<String> getIdsByType(String type) {
        List<String> ids = new ArrayList<>();
        Map<String, String> widgets = extractWidgetsFromLayout(activityName);

        for (Map.Entry<String, String> e : widgets.entrySet()) {
            String id = e.getKey();
            String widgetType = e.getValue();

            if ("all".equalsIgnoreCase(type) || "view".equalsIgnoreCase(type)) {
                ids.add(id);
            } else if ("listSpn".equalsIgnoreCase(type)) {
                if ("ListView".equalsIgnoreCase(widgetType)
                        || "Spinner".equalsIgnoreCase(widgetType)) {
                    ids.add(id);
                }
            } else if (widgetType.equalsIgnoreCase(type)) {
                ids.add(id);
            }
        }
        return ids;
    }

    public List<String> getIdsByTextViewFamily() {
        List<String> ids = new ArrayList<>();
        Map<String, String> widgets = extractWidgetsFromLayout(activityName);

        for (Map.Entry<String, String> e : widgets.entrySet()) {
            if (isTextViewFamily(e.getValue())) {
                ids.add(e.getKey());
            }
        }
        return ids;
    }

    /* ===================== CORE LOGIC ===================== */

    private Map<String, String> extractWidgetsFromLayout(String activityName) {
        Map<String, String> widgets = new LinkedHashMap<>();

        try {
            File bin = new File(projectPath, "layout.bin");
            if (!bin.exists()) return widgets;

            ObjectInputStream ois =
                    new ObjectInputStream(new FileInputStream(bin));

            Object obj = ois.readObject();
            ois.close();

            if (!(obj instanceof ArrayList)) return widgets;

            @SuppressWarnings("unchecked")
            ArrayList<LayoutData> layouts = (ArrayList<LayoutData>) obj;

            for (LayoutData data : layouts) {
                if (data != null
                        && activityName.equals(data.name)
                        && data.xml != null) {

                    parseWidgetsFromXML(data.xml, widgets);
                    break;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "layout parse failed", e);
        }
        return widgets;
    }

    private void parseWidgetsFromXML(String xml, Map<String, String> widgets) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);

            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xml));

            int event;
            while ((event = parser.next()) != XmlPullParser.END_DOCUMENT) {
                if (event == XmlPullParser.START_TAG) {

                    String tag = parser.getName();
                    String widgetType = mapTagToWidgetType(tag);

                    for (int i = 0; i < parser.getAttributeCount(); i++) {
                        String attr = parser.getAttributeName(i);
                        if ("android:id".equals(attr) || "id".equals(attr)) {

                            String raw = parser.getAttributeValue(i);
                            String id = extractId(raw);

                            if (id != null) {
                                widgets.put(id, widgetType);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "xml parse error", e);
        }
    }

    /* ===================== HELPERS ===================== */

    private String extractId(String raw) {
        if (raw == null) return null;
        int idx = raw.indexOf('/');
        return (idx != -1 && idx + 1 < raw.length())
                ? raw.substring(idx + 1)
                : null;
    }

    private boolean isTextViewFamily(String type) {
        switch (type) {
            case "TextView":
            case "Button":
            case "MaterialButton":
            case "CheckBox":
            case "RadioButton":
            case "Switch":
            case "ToggleButton":
            case "EditText":
            case "TextInputEditText":
            case "AutoCompleteTextView":
            case "AppCompatTextView":
            case "AppCompatButton":
                return true;
            default:
                return false;
        }
    }

    private String mapTagToWidgetType(String tag) {
        if (tag == null) return "View";
        if (tag.contains(".")) {
            return tag.substring(tag.lastIndexOf('.') + 1);
        }
        return tag;
    }
}