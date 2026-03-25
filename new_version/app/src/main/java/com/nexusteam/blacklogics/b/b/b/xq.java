package b.b.b;

import android.content.Context;
import android.os.Environment;
import org.json.JSONObject;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.Base64;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class xq {

    private List<ViewItem> activities = new ArrayList<>();

    public static class ViewItem {
        public String xmlName;
        public String xmlFileName;
        public String javaName;
        public String javaFileName;

        public ViewItem(String xmlName, String xmlFileName, String javaName, String javaFileName) {
            this.xmlName = xmlName;
            this.xmlFileName = xmlFileName;
            this.javaName = javaName;
            this.javaFileName = javaFileName;
        }

        public String getXmlName() { return xmlName; }
        public String getXmlFileName() { return xmlFileName; }
        public String getJavaName() { return javaName; }
        public String getJavaFileName() { return javaFileName; }
    }

    public xq addActivity(String name, String xmlName) {
        if (!exists(name)) {
            activities.add(new ViewItem(xmlName, xmlName + ".xml", name, name + ".java"));
        }
        return this;
    }

    private boolean exists(String name) {
        for (ViewItem item : activities)
            if (item.javaName.equals(name) || item.xmlName.equals(name))
                return true;
        return false;
    }

    public void save(Context context, String sc_id) {
        try {
            File dir = new File("/storage/emulated/0" + "/.blacklogics/data/" + sc_id);
            if (!dir.exists()) dir.mkdirs();

            File file = new File(dir, "logic");
            JSONObject json = file.exists()
                    ? new JSONObject(new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8))
                    : new JSONObject();

            Map<String, Object> data = new HashMap<>();
            if (json.has("activity_data")) {
                String decoded = new String(Base64.getDecoder().decode(json.getString("activity_data")), StandardCharsets.UTF_8);
                data = new Gson().fromJson(decoded, new TypeToken<Map<String, Object>>(){}.getType());
            }

            List<Map<String, String>> actList = new ArrayList<>();
            for (ViewItem item : activities) {
                Map<String, String> actMap = new HashMap<>();
                actMap.put("xmlName", item.xmlName);
                actMap.put("xmlFileName", item.xmlFileName);
                actMap.put("javaName", item.javaName);
                actMap.put("javaFileName", item.javaFileName);
                actList.add(actMap);
            }

            data.put("activities", actList);
            String base64 = Base64.getEncoder().encodeToString(new Gson().toJson(data).getBytes(StandardCharsets.UTF_8));
            json.put("activity_data", base64);

            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(json.toString().getBytes(StandardCharsets.UTF_8));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load(Context context, String sc_id) {
        try {
            File dir = new File("/storage/emulated/0" + "/.blacklogics/data/" + sc_id);
            File file = new File(dir, "logic");
            if (!file.exists()) return;

            JSONObject json = new JSONObject(new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8));
            if (!json.has("activity_data")) return;

            String decoded = new String(Base64.getDecoder().decode(json.getString("activity_data")), StandardCharsets.UTF_8);
            Map<String, Object> data = new Gson().fromJson(decoded, new TypeToken<Map<String, Object>>(){}.getType());
            if (data == null || !data.containsKey("activities")) return;

            Object rawList = data.get("activities");
            if (rawList instanceof List) {
                List<?> list = (List<?>) rawList;
                activities.clear();
                for (Object obj : list) {
                    if (obj instanceof Map) {
                        Map<?, ?> map = (Map<?, ?>) obj;
                        activities.add(new ViewItem(
                                String.valueOf(map.get("xmlName")),
                                String.valueOf(map.get("xmlFileName")),
                                String.valueOf(map.get("javaName")),
                                String.valueOf(map.get("javaFileName"))
                        ));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ViewItem> getActivities() { return activities; }
    public List<String> getActivityNames() {
        List<String> names = new ArrayList<>();
        for (ViewItem item : activities) names.add(item.javaName);
        return names;
    }
    public List<String> getXmlNames() {
        List<String> names = new ArrayList<>();
        for (ViewItem item : activities) names.add(item.xmlName);
        return names;
    }
}
