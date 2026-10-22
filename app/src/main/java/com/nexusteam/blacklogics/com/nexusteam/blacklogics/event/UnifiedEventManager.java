package com.nexusteam.blacklogics.event;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Pair;

import com.nexusteam.internal.BlockStorage;
import com.nexusteam.internal.ma;
import com.nexusteam.internal.beans.ComponentBean;
import com.nexusteam.internal.beans.EventBean;
import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.internal.beans.ViewBean;
import com.nexusteam.internal.lw;
import com.nexusteam.blacklogics.utils.EventUtils;

import java.util.ArrayList;
import java.util.List;

import com.nexusteam.blacklogics.beans.ActivityFileInfo;
import com.nexusteam.blacklogics.manager.ProjectLogicManager;

public class UnifiedEventManager {

    private static UnifiedEventManager instance;
    private Context context;
    private String sc_id;

    private static final String PREF_NAME = "EventSystemPref";
    private static final String KEY_ACTIVE_SYSTEM = "active_event_system";
    private static final String KEY_MIGRATION_DONE = "migration_done";

    public static final int SYSTEM_LEGACY = 0;
    public static final int SYSTEM_NEW = 1;
    public static final int SYSTEM_AUTO = 2;

    private int activeSystem = SYSTEM_AUTO;

    private UnifiedEventManager(Context context) {
        this.context = context.getApplicationContext();
        loadPreferences();
    }

    public static synchronized UnifiedEventManager getInstance(Context context) {
        if (instance == null) {
            instance = new UnifiedEventManager(context);
        }
        return instance;
    }


    private void loadPreferences() {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        activeSystem = prefs.getInt(KEY_ACTIVE_SYSTEM, SYSTEM_AUTO);
    }

    private void savePreferences() {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_ACTIVE_SYSTEM, activeSystem);
        editor.apply();
    }

    public void setActiveSystem(int system) {
        this.activeSystem = system;
        savePreferences();
    }

    public int getActiveSystem() {
        return activeSystem;
    }

    public boolean isMigrationDone() {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(KEY_MIGRATION_DONE, false);
    }

    public void setMigrationDone(boolean done) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_MIGRATION_DONE, done);
        editor.apply();
    }

    public void setScId(String sc_id) {
        this.sc_id = sc_id;
        BlockStorage.setScId(sc_id);
    }

    public String getScId() {
        return sc_id;
    }


    public int detectBestSystem(String activityName) {
        String javaName = activityName + ".java";

        try {
            ArrayList<Pair<String, String>> newFunctions = BlockStorage.loadFunctions(javaName);
            if (newFunctions != null && !newFunctions.isEmpty()) {
                return SYSTEM_NEW;
            }
        } catch (Exception e) {
        }

        try {
            lw lwInstance = ma.a(sc_id);
            if (lwInstance != null) {
                ArrayList<Pair<String, String>> legacyFunctions = lwInstance.h(javaName);
                if (legacyFunctions != null && !legacyFunctions.isEmpty()) {
                    return SYSTEM_LEGACY;
                }

                ArrayList<EventBean> legacyEvents = lwInstance.j(javaName);
                if (legacyEvents != null && !legacyEvents.isEmpty()) {
                    return SYSTEM_LEGACY;
                }
            }
        } catch (Exception e) {
        }

        if (isMigrationDone()) {
            return SYSTEM_NEW;
        }

        return SYSTEM_NEW;
    }

    private int getEffectiveSystem(String activityName) {
        if (activeSystem == SYSTEM_AUTO) {
            return detectBestSystem(activityName);
        }
        return activeSystem;
    }


    public List<String> getLifecycleEvents() {
        String[] eventsArray = EventUtils.getLifecycleEvents();
        List<String> result = new ArrayList<String>();
        for (String event : eventsArray) {
            result.add(event);
        }
        return result;
    }

    public List<String> getLifecycleEvents(String activityName) {
        String javaName = activityName + ".java";
        List<String> merged = new ArrayList<String>();

        try {
            List<String> newEvents = EventUtils.loadLifecycleEvents(sc_id, activityName);
            for (String event : newEvents) {
                if (!merged.contains(event)) {
                    merged.add(event);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            lw lwInstance = ma.a(sc_id);
            if (lwInstance != null) {
                ArrayList<EventBean> beans = lwInstance.j(javaName);
                for (EventBean bean : beans) {
                    if (bean.eventType == EventBean.EVENT_TYPE_ACTIVITY) {
                        String eventName = bean.eventName;
                        if (!merged.contains(eventName)) {
                            merged.add(eventName);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int system = getEffectiveSystem(activityName);
        if (system == SYSTEM_LEGACY && !merged.isEmpty()) {
            try {
                List<String> newEventsOnly = EventUtils.loadLifecycleEvents(sc_id, activityName);
                boolean needsMigration = false;
                for (String event : merged) {
                    if (!newEventsOnly.contains(event)) {
                        needsMigration = true;
                        break;
                    }
                }

                if (needsMigration) {
                    EventUtils.saveLifecycleEvents(sc_id, activityName, merged);
                    setMigrationDone(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return merged;
    }


    public boolean saveLifecycleEvents(String activityName, List<String> events) {
        String javaName = activityName + ".java";
        boolean allSuccess = true;

        try {
            boolean newSuccess = EventUtils.saveLifecycleEvents(sc_id, activityName, events);
            if (!newSuccess) {
                allSuccess = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            allSuccess = false;
        }

        try {
            lw lwInstance = ma.a(sc_id);
            if (lwInstance != null) {
                ArrayList<EventBean> existing = lwInstance.j(javaName);
                ArrayList<EventBean> keepEvents = new ArrayList<EventBean>();

                for (EventBean bean : existing) {
                    if (bean.eventType != EventBean.EVENT_TYPE_ACTIVITY) {
                        keepEvents.add(bean);
                    }
                }

                for (String eventName : events) {
                    EventBean bean = new EventBean(
                        EventBean.EVENT_TYPE_ACTIVITY,
                        -1,
                        "onCreate",
                        eventName
                    );
                    bean.initValue();
                    keepEvents.add(bean);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return allSuccess;
    }


    public boolean removeLifecycleEvent(String activityName, String eventName) {
        String javaName = activityName + ".java";
        boolean success = true;

        try {
            success = EventUtils.removeLifecycleEvent(sc_id, activityName, eventName);
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        }

        try {
            String placeholder = EventUtils.getEventPlaceholder(eventName);
            String blockKey = eventName + "_" + placeholder + "_" + activityName;
            BlockStorage.save(blockKey, new ArrayList<com.nexusteam.internal.beans.BlockBean>());
            EventUtils.deleteEventFromProject(sc_id, activityName + "_" + eventName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            ProjectLogicManager.setPath(com.nexusteam.internal.fe.d(sc_id));
            ProjectLogicManager.removeBlockLogicForWidget(activityName, eventName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            lw lwInstance = ma.a(sc_id);
            if (lwInstance != null) {
                lwInstance.d(javaName, "onCreate", eventName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }


    public List<MoreBlockInfo> getMoreBlocks(String activityName) {
        String javaName = activityName;
        List<MoreBlockInfo> merged = new ArrayList<MoreBlockInfo>();
        List<String> existingNames = new ArrayList<String>();

        try {
            ArrayList<Pair<String, String>> newFunctions = BlockStorage.loadFunctions(javaName);
            if (newFunctions != null) {
                for (Pair<String, String> pair : newFunctions) {
                    String name = pair.first;
                    String spec = pair.second;
                    if (!existingNames.contains(name)) {
                        merged.add(new MoreBlockInfo(name, spec));
                        existingNames.add(name);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            lw lwInstance = ma.a(sc_id);
            if (lwInstance != null) {
                ArrayList<Pair<String, String>> legacyFunctions = lwInstance.h(javaName);
                if (legacyFunctions != null) {
                    for (Pair<String, String> pair : legacyFunctions) {
                        String name = pair.first;
                        String spec = pair.second;
                        if (!existingNames.contains(name)) {
                            merged.add(new MoreBlockInfo(name, spec));
                            existingNames.add(name);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int system = getEffectiveSystem(activityName);
        if (system == SYSTEM_LEGACY && !merged.isEmpty()) {
            try {
                ArrayList<Pair<String, String>> newFunctions = BlockStorage.loadFunctions(javaName);
                boolean needsMigration = false;
                for (MoreBlockInfo info : merged) {
                    boolean found = false;
                    if (newFunctions != null) {
                        for (Pair<String, String> pair : newFunctions) {
                            if (pair.first.equals(info.name)) {
                                found = true;
                                break;
                            }
                        }
                    }
                    if (!found) {
                        needsMigration = true;
                        break;
                    }
                }

                if (needsMigration) {
                    ArrayList<Pair<String, String>> allFunctions = new ArrayList<Pair<String, String>>();
                    for (MoreBlockInfo info : merged) {
                        allFunctions.add(new Pair<String, String>(info.name, info.spec));
                    }
                    BlockStorage.saveFunctions(javaName, allFunctions);
                    setMigrationDone(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return merged;
    }


    public void saveMoreBlock(String activityName, String name, String spec) {
        String javaName = activityName;
        int system = getEffectiveSystem(activityName);

        try {
            ArrayList<Pair<String, String>> functions = BlockStorage.loadFunctions(javaName);
            if (functions == null) {
                functions = new ArrayList<Pair<String, String>>();
            }

            boolean exists = false;
            for (Pair<String, String> pair : functions) {
                if (pair.first.equals(name)) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                functions.add(new Pair<String, String>(name, spec));
                BlockStorage.saveFunctions(javaName, functions);
                BlockStorage.save(activityName + "_" + name, new ArrayList<com.nexusteam.internal.beans.BlockBean>());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (system == SYSTEM_LEGACY) {
            try {
                lw lwInstance = ma.a(sc_id);
                if (lwInstance != null) {
                    lwInstance.c(javaName, name, spec);
                    lwInstance.a(javaName, name + "_moreBlock", new ArrayList<com.nexusteam.internal.beans.BlockBean>());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void deleteMoreBlock(String activityName, String name) {
        String javaName = activityName;
        int system = getEffectiveSystem(activityName);

        try {
            ArrayList<Pair<String, String>> functions = BlockStorage.loadFunctions(javaName);
            if (functions != null) {
                ArrayList<Pair<String, String>> newFunctions = new ArrayList<Pair<String, String>>();
                for (Pair<String, String> pair : functions) {
                    if (!pair.first.equals(name)) {
                        newFunctions.add(pair);
                    }
                }
                BlockStorage.saveFunctions(javaName, newFunctions);
                BlockStorage.save(activityName + "_" + name, new ArrayList<com.nexusteam.internal.beans.BlockBean>());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (system == SYSTEM_LEGACY) {
            try {
                lw lwInstance = ma.a(sc_id);
                if (lwInstance != null) {
                    lwInstance.h(javaName, name);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public Intent createEventIntent(Context context, String eventName,
                                    String activityName, Object session,
                                    ProjectFileBean fileBean) {
        int system = getEffectiveSystem(activityName);

        if (system == SYSTEM_NEW) {
            return EventUtils.createEventIntent(
                context,
                eventName,
                EventUtils.getEventDescription(eventName),
                activityName,
                sc_id,
                (ActivityFileInfo) session,
                fileBean
            );
        } else {
            Intent intent = new Intent(context,
                com.nexusteam.internal.editor.LogicEditorActivity.class);
            intent.putExtra("sc_id", sc_id);
            intent.putExtra("id", "onCreate");
            intent.putExtra("event", eventName);
            intent.putExtra("event_text", EventUtils.getEventDescription(eventName));
            intent.putExtra("filename", activityName);
            intent.putExtra("activityName", activityName);
            intent.putExtra("project_file", fileBean);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            return intent;
        }
    }


    public ArrayList<ViewBean> getViews(String xmlName) {
        try {
            lw lwInstance = ma.a(sc_id);
            if (lwInstance != null) {
                return lwInstance.a(xmlName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<ViewBean>();
    }

    public ViewBean getView(String xmlName, String viewId) {
        try {
            lw lwInstance = ma.a(sc_id);
            if (lwInstance != null) {
                return lwInstance.e(xmlName, viewId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public ArrayList<ComponentBean> getComponents(String activityName) {
        try {
            lw lwInstance = ma.a(sc_id);
            if (lwInstance != null) {
                return lwInstance.k(activityName + ".java");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<ComponentBean>();
    }

    public void addComponent(String activityName, ComponentBean component) {
        try {
            lw lwInstance = ma.a(sc_id);
            if (lwInstance != null) {
                lwInstance.a(activityName + ".java", component);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeComponent(String activityName, ComponentBean component) {
        try {
            lw lwInstance = ma.a(sc_id);
            if (lwInstance != null) {
                lwInstance.b(activityName + ".java", component);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addEvent(String activityName, EventBean event) {
        try {
            lw lwInstance = ma.a(sc_id);
            if (lwInstance != null) {
                lwInstance.a(activityName + ".java", event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteEvent(String activityName, String targetId, String eventName) {
        try {
            lw lwInstance = ma.a(sc_id);
            if (lwInstance != null) {
                lwInstance.d(activityName + ".java", targetId, eventName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean migrateActivity(String activityName) {
        try {
            String javaName = activityName + ".java";
            lw lwInstance = ma.a(sc_id);

            if (lwInstance == null) {
                return false;
            }

            ArrayList<Pair<String, String>> legacyFunctions = lwInstance.h(javaName);
            if (legacyFunctions != null && !legacyFunctions.isEmpty()) {
                ArrayList<Pair<String, String>> newFunctions = BlockStorage.loadFunctions(javaName);
                if (newFunctions == null) {
                    newFunctions = new ArrayList<Pair<String, String>>();
                }

                for (Pair<String, String> pair : legacyFunctions) {
                    boolean exists = false;
                    for (Pair<String, String> p : newFunctions) {
                        if (p.first.equals(pair.first)) {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        newFunctions.add(pair);
                        try {
                            ArrayList<com.nexusteam.internal.beans.BlockBean> blocks =
                                lwInstance.l(javaName, pair.first + "_moreBlock");
                            if (blocks != null && !blocks.isEmpty()) {
                                BlockStorage.save(activityName + "_" + pair.first, blocks);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                BlockStorage.saveFunctions(javaName, newFunctions);
            }

            ArrayList<EventBean> legacyEvents = lwInstance.j(javaName);
            List<String> events = new ArrayList<String>();
            for (EventBean bean : legacyEvents) {
                if (bean.eventType == EventBean.EVENT_TYPE_ACTIVITY) {
                    events.add(bean.eventName);
                }
            }

            if (!events.isEmpty()) {
                EventUtils.saveLifecycleEvents(sc_id, activityName, events);
            }

            setMigrationDone(true);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void migrateAllActivities(List<String> activityNames) {
        for (String name : activityNames) {
            migrateActivity(name);
        }
    }


    public static class MoreBlockInfo {
        public String name;
        public String spec;
        public String type;

        public MoreBlockInfo(String name, String spec) {
            this.name = name;
            this.spec = spec;
            this.type = "definedFunc";
        }
    }
}