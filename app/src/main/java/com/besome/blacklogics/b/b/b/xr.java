package b.b.b;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class xr {

    private String projectPath;
    private String activityName;

    public xr(String projectPath, String activityName) {
        this.projectPath = projectPath;
        this.activityName = activityName;
    }

    public List<String> getIdsByType(String type) {
        List<String> ids = new ArrayList<>();
        Map<String, String> widgets = extractWidgetsFromLayout(activityName);

        for (Map.Entry<String, String> entry : widgets.entrySet()) {
            String widgetId = entry.getKey();
            String widgetType = entry.getValue();

            if (type.equalsIgnoreCase("all") || type.equalsIgnoreCase("view")) {
                ids.add(widgetId);
            } else if (type.equalsIgnoreCase("listSpn")) {
                if (widgetType.equalsIgnoreCase("ListView") || widgetType.equalsIgnoreCase("Spinner"))
                    ids.add(widgetId);
            } else if (widgetType.equalsIgnoreCase(type)) {
                ids.add(widgetId);
            }
        }
        return ids;
    }

    private Map<String, String> extractWidgetsFromLayout(String activityName) {
        Map<String, String> widgets = new LinkedHashMap<>();
        try {
            String layoutPath = projectPath + "/root_layout.json";
            if (new File(layoutPath).exists()) {
                String jsonContent = new String(Files.readAllBytes(new File(layoutPath).toPath()));
                JSONArray layoutArray = new JSONArray(jsonContent);

                for (int i = 0; i < layoutArray.length(); i++) {
                    JSONObject layoutObj = layoutArray.getJSONObject(i);
                    if (layoutObj.getString("name").equals(activityName)) {
                        String xmlContent = layoutObj.getString("xml");
                        widgets = parseWidgetsFromXML(xmlContent);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return widgets;
    }

    private Map<String, String> parseWidgetsFromXML(String xmlContent) {
        Map<String, String> widgets = new LinkedHashMap<>();
        try {
            String decodedXml = decodeUnicodeEscapes(xmlContent);
            String[] lines = decodedXml.split("\n");
            String currentTag = null;

            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("<!--") || line.startsWith("<?")) continue;

                if (line.startsWith("<") && !line.startsWith("</")) {
                    currentTag = extractTagName(line);
                }

                if (line.contains("android:id=\"@+id/") && currentTag != null) {
                    int idStart = line.indexOf("android:id=\"@+id/") + 17;
                    int idEnd = line.indexOf("\"", idStart);
                    if (idEnd > idStart) {
                        String widgetId = line.substring(idStart, idEnd);
                        widgets.put(widgetId, mapTagToWidgetType(currentTag));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return widgets;
    }

    private String decodeUnicodeEscapes(String input) {
        StringBuilder output = new StringBuilder();
        int i = 0;
        while (i < input.length()) {
            if (input.charAt(i) == '\\' && i + 5 < input.length() && input.charAt(i + 1) == 'u') {
                try {
                    String hex = input.substring(i + 2, i + 6);
                    char unicodeChar = (char) Integer.parseInt(hex, 16);
                    output.append(unicodeChar);
                    i += 6;
                } catch (NumberFormatException e) {
                    output.append(input.charAt(i));
                    i++;
                }
            } else {
                output.append(input.charAt(i));
                i++;
            }
        }
        return output.toString();
    }

    private String extractTagName(String xmlLine) {
        int tagStart = xmlLine.indexOf("<") + 1;
        if (tagStart == 0) return "View";
        if (xmlLine.charAt(tagStart) == '?') return "View";

        int tagEnd = xmlLine.length();
        for (int i = tagStart; i < xmlLine.length(); i++) {
            char c = xmlLine.charAt(i);
            if (c == ' ' || c == '>' || c == '/' || c == '\n' || c == '\t') {
                tagEnd = i;
                break;
            }
        }
        return (tagEnd > tagStart) ? xmlLine.substring(tagStart, tagEnd) : "View";
    }

    private String mapTagToWidgetType(String tagName) {
        switch (tagName) {
            case "TextView": return "TextView";
            case "Button": return "Button";
            case "EditText": return "EditText";
            case "ImageView": return "ImageView";
            case "LinearLayout": return "LinearLayout";
            case "RelativeLayout": return "RelativeLayout";
            case "ConstraintLayout": return "ConstraintLayout";
            case "ScrollView": return "ScrollView";
            case "RecyclerView": return "RecyclerView";
            case "ListView": return "ListView";
            case "Spinner": return "Spinner";
            case "CheckBox": return "CheckBox";
            case "Switch": return "Switch";
            case "SeekBar": return "SeekBar";
            case "ProgressBar": return "ProgressBar";
            default: return tagName;
        }
    }
}
