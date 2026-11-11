package com.nexusteam.internal.editor.blockparser.utils;

import android.content.Context;
import com.nexusteam.internal.kq;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class KqOpcodeSource {

    private static final Map LABELS = new HashMap();
    private static final Set OPCODES = new HashSet();
    private static boolean loaded = false;

    private KqOpcodeSource() {}

    public static synchronized void init(Context context) {
        if (context == null) return;
        try {
            kq instance = kq.a();
            instance.a(context, "doToast");

            Field f = kq.class.getDeclaredField("c");
            f.setAccessible(true);
            Object raw = f.get(instance);
            if (!(raw instanceof Map)) {
                android.util.Log.w("KqOpcodeSource", "field c is not a Map");
                return;
            }
            Map map = (Map) raw;
            LABELS.clear();
            OPCODES.clear();
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry e = (Map.Entry) it.next();
                if (e.getKey() == null) continue;
                String op = String.valueOf(e.getKey());
                String label = e.getValue() == null ? "" : String.valueOf(e.getValue());
                OPCODES.add(op);
                LABELS.put(op, label);
            }
            loaded = true;
            android.util.Log.i("KqOpcodeSource", "Loaded " + OPCODES.size() + " opcodes from kq");
        } catch (Throwable t) {
            android.util.Log.e("KqOpcodeSource", "Failed to load from kq", t);
            loadFallback();
        }
    }

    private static void loadFallback() {
        String[] core = new String[] {
            "doToast", "setText", "setTextColor", "setVisible", "setEnable",
            "setBgColor", "setHint", "getText", "setClickable", "requestFocus",
            "setVarString", "setVarInt", "setVarBoolean",
            "if", "ifElse", "repeat", "forever", "break",
            "true", "false", "not", ">", "<", "=", "!=", ">=", "<=",
            "stringEquals", "stringContains", "stringJoin", "stringLength",
            "fileutilwrite", "fileutilread", "fileutilisexist", "fileutilcopy",
            "fileutildelete", "fileutilmakedir", "fileutilmove",
            "intentSetAction", "intentSetData", "intentSetScreen",
            "intentPutExtra", "intentSetFlags", "intentGetString",
            "startActivity", "finishActivity",
            "webViewLoadUrl", "webViewGoBack", "webViewGoForward",
            "mapPut", "mapGet", "mapCreateNew", "mapClear", "mapSize",
            "addListStr", "addListInt", "getAtListStr", "getAtListInt",
            "clearList", "lengthList", "increaseInt", "decreaseInt",
            "addSourceDirectly", "random"
        };
        for (int i = 0; i < core.length; i++) {
            OPCODES.add(core[i]);
            LABELS.put(core[i], core[i]);
        }
        loaded = true;
        android.util.Log.w("KqOpcodeSource", "Using fallback opcodes: " + OPCODES.size());
    }

    public static boolean isLoaded() {
        return loaded;
    }

    public static boolean has(String opcode) {
        if (opcode == null) return false;
        if (!loaded) return true;
        return OPCODES.contains(opcode);
    }

    public static String label(String opcode) {
        if (opcode == null) return "";
        Object v = LABELS.get(opcode);
        return v == null ? "" : (String) v;
    }

    public static int size() {
        return OPCODES.size();
    }

    public static Set allOpcodes() {
        return Collections.unmodifiableSet(OPCODES);
    }

    public static List allOpcodesList() {
        return new ArrayList(OPCODES);
    }

    public static String normalize(String candidate) {
        if (candidate == null) return "addSourceDirectly";
        if (has(candidate)) return candidate;
        if ("setVisibility".equals(candidate)) return has("setVisible") ? "setVisible" : candidate;
        if ("setEnabled".equals(candidate)) return has("setEnable") ? "setEnable" : candidate;
        if ("setBackgroundColor".equals(candidate)) return has("setBgColor") ? "setBgColor" : candidate;
        if ("toast".equals(candidate) || "showToast".equals(candidate))
            return has("doToast") ? "doToast" : candidate;
        if ("finish".equals(candidate)) return has("finishActivity") ? "finishActivity" : candidate;
        if ("intentSetClass".equals(candidate)) return has("intentSetScreen") ? "intentSetScreen" : candidate;
        if ("setVar".equals(candidate)) return has("setVarString") ? "setVarString" : candidate;
        return candidate;
    }
}
