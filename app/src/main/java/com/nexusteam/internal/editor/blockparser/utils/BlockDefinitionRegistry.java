package com.nexusteam.internal.editor.blockparser.utils;

import com.nexusteam.internal.editor.blockparser.model.BlockDefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BlockDefinitionRegistry {

    private static BlockDefinitionRegistry instance;
    private final List definitions = new ArrayList();
    private final Map byOpcode = new HashMap();

    public static synchronized BlockDefinitionRegistry getInstance() {
        if (instance == null) {
            instance = new BlockDefinitionRegistry();
        }
        return instance;
    }

    private BlockDefinitionRegistry() {
        registerBuiltins();
    }

    public List getAllDefinitions() {
        return definitions;
    }

    public BlockDefinition getByOpcode(String opcode) {
        return (BlockDefinition) byOpcode.get(opcode);
    }

    public int size() {
        return definitions.size();
    }

    private void add(BlockDefinition def) {
        if (def == null || def.getOpcode() == null) return;
        String op = KqOpcodeSource.normalize(def.getOpcode());
        def.setOpcode(op);
        definitions.add(def);
        byOpcode.put(op, def);
    }

    private void addContainer(String opcode, String name, String category, String pattern, String... params) {
        BlockDefinition d = new BlockDefinition(opcode, name, category);
        d.setContainer(true);
        d.addCodePattern(pattern);
        for (int i = 0; i < params.length; i++) d.addParameter(params[i]);
        add(d);
    }

    private void addStatement(String opcode, String name, String category, String pattern, String... params) {
        BlockDefinition d = new BlockDefinition(opcode, name, category);
        d.addCodePattern(pattern);
        for (int i = 0; i < params.length; i++) d.addParameter(params[i]);
        add(d);
    }

    private void addBoolean(String opcode, String name, String category, String pattern, String... params) {
        addStatement(opcode, name, category, pattern, params);
    }

    private void addNumber(String opcode, String name, String category, String pattern, String... params) {
        addStatement(opcode, name, category, pattern, params);
    }

    private void addString(String opcode, String name, String category, String pattern, String... params) {
        addStatement(opcode, name, category, pattern, params);
    }

    private void registerBuiltins() {
        addContainer("if", "If", "Control", "if\\s*\\(", "condition");
        addContainer("ifElse", "If Else", "Control", "if\\s*\\(", "condition");
        addContainer("repeat", "Repeat", "Control", "for\\s*\\(", "times");
        addContainer("forever", "Forever", "Control", "while\\s*\\(\\s*true\\s*\\)");
        addStatement("break", "Break", "Control", "\\bbreak\\s*;");

        addStatement("setVarString", "Set String", "Variable",
                "(?:String\\s+)?([a-zA-Z_][a-zA-Z0-9_]*)\\s*=\\s*\"([^\"]*)\"\\s*;", "variable", "value");
        addStatement("setVarInt", "Set Number", "Variable",
                "(?:int|double|float|long)\\s+([a-zA-Z_][a-zA-Z0-9_]*)\\s*=\\s*([^;]+)\\s*;", "variable", "value");
        addStatement("setVarBoolean", "Set Boolean", "Variable",
                "(?:boolean)\\s+([a-zA-Z_][a-zA-Z0-9_]*)\\s*=\\s*(true|false)\\s*;", "variable", "value");
        addStatement("setVarString", "Set String", "Variable",
                "([a-zA-Z_][a-zA-Z0-9_]*)\\s*=\\s*\"([^\"]*)\"\\s*;", "variable", "value");
        addStatement("setVarInt", "Set Number", "Variable",
                "([a-zA-Z_][a-zA-Z0-9_]*)\\s*=\\s*(-?\\d+(?:\\.\\d+)?)\\s*;", "variable", "value");
        addStatement("increaseInt", "Increase", "Variable",
                "([a-zA-Z_][a-zA-Z0-9_]*)\\s*\\+\\+\\s*;", "variable");
        addStatement("decreaseInt", "Decrease", "Variable",
                "([a-zA-Z_][a-zA-Z0-9_]*)\\s*--\\s*;", "variable");

        addStatement("doToast", "Show Toast", "UI",
                "Toast\\.makeText\\s*\\([\\s\\S]*?\\)\\s*\\.show\\s*\\(\\s*\\)");
        addStatement("doToast", "Show Toast", "UI",
                "Toast\\.makeText\\s*\\([^,]+,\\s*([\\s\\S]*?),\\s*[^)]+\\)\\s*\\.show\\s*\\(\\s*\\)", "message");

        addStatement("setText", "Set Text", "View",
                "([a-zA-Z_][a-zA-Z0-9_]*)\\.setText\\s*\\(([^)]+)\\)", "view", "text");
        addStatement("setTextColor", "Set Text Color", "View",
                "([a-zA-Z_][a-zA-Z0-9_]*)\\.setTextColor\\s*\\(([^)]+)\\)", "view", "color");
        addStatement("setVisible", "Set Visibility", "View",
                "([a-zA-Z_][a-zA-Z0-9_]*)\\.setVisibility\\s*\\(([^)]+)\\)", "view", "visibility");
        addStatement("setEnable", "Set Enabled", "View",
                "([a-zA-Z_][a-zA-Z0-9_]*)\\.setEnabled\\s*\\(([^)]+)\\)", "view", "enabled");
        addStatement("setBgColor", "Set Background Color", "View",
                "([a-zA-Z_][a-zA-Z0-9_]*)\\.setBackgroundColor\\s*\\(([^)]+)\\)", "view", "color");
        addStatement("setHint", "Set Hint", "View",
                "([a-zA-Z_][a-zA-Z0-9_]*)\\.setHint\\s*\\(([^)]+)\\)", "view", "hint");
        addStatement("setClickable", "Set Clickable", "View",
                "([a-zA-Z_][a-zA-Z0-9_]*)\\.setClickable\\s*\\(([^)]+)\\)", "view", "clickable");
        addStatement("setImage", "Set Image", "View",
                "([a-zA-Z_][a-zA-Z0-9_]*)\\.setImageResource\\s*\\(([^)]+)\\)", "view", "res");
        addString("getText", "Get Text", "View",
                "([a-zA-Z_][a-zA-Z0-9_]*)\\.getText\\s*\\(\\s*\\)", "view");

        addStatement("fileutilwrite", "File Write", "File",
                "FileUtil\\.writeFile\\s*\\(([^,]+),\\s*([^)]+)\\)", "path", "data");
        addString("fileutilread", "File Read", "File",
                "FileUtil\\.readFile\\s*\\(([^)]+)\\)", "path");
        addBoolean("fileutilisexist", "File Exists", "File",
                "FileUtil\\.isExistFile\\s*\\(([^)]+)\\)", "path");
        addStatement("fileutilcopy", "File Copy", "File",
                "FileUtil\\.copyFile\\s*\\(([^,]+),\\s*([^)]+)\\)", "src", "dest");
        addStatement("fileutildelete", "File Delete", "File",
                "FileUtil\\.deleteFile\\s*\\(([^)]+)\\)", "path");
        addStatement("fileutilmakedir", "Make Dir", "File",
                "FileUtil\\.makeDir\\s*\\(([^)]+)\\)", "path");
        addStatement("fileutilmove", "File Move", "File",
                "FileUtil\\.moveFile\\s*\\(([^,]+),\\s*([^)]+)\\)", "src", "dest");

        addStatement("intentSetScreen", "Intent Set Screen", "Intent",
                "([a-zA-Z_][a-zA-Z0-9_]*)\\.setClass\\s*\\([^,]+,\\s*([^)]+)\\)", "intent", "activity");
        addStatement("intentPutExtra", "Intent Put Extra", "Intent",
                "([a-zA-Z_][a-zA-Z0-9_]*)\\.putExtra\\s*\\(([^,]+),\\s*([^)]+)\\)", "intent", "key", "value");
        addStatement("intentSetAction", "Intent Set Action", "Intent",
                "([a-zA-Z_][a-zA-Z0-9_]*)\\.setAction\\s*\\(([^)]+)\\)", "intent", "action");
        addStatement("intentSetData", "Intent Set Data", "Intent",
                "([a-zA-Z_][a-zA-Z0-9_]*)\\.setData\\s*\\(([^)]+)\\)", "intent", "data");
        addStatement("intentSetFlags", "Intent Set Flags", "Intent",
                "([a-zA-Z_][a-zA-Z0-9_]*)\\.setFlags\\s*\\(([^)]+)\\)", "intent", "flags");
        addStatement("startActivity", "Start Activity", "Intent",
                "startActivity\\s*\\(([^)]+)\\)", "intent");
        addStatement("finishActivity", "Finish", "Intent", "finish\\s*\\(\\s*\\)");

        addStatement("webViewLoadUrl", "WebView Load URL", "WebView",
                "([a-zA-Z_][a-zA-Z0-9_]*)\\.loadUrl\\s*\\(([^)]+)\\)", "webview", "url");
        addStatement("webViewGoBack", "WebView Go Back", "WebView",
                "([a-zA-Z_][a-zA-Z0-9_]*)\\.goBack\\s*\\(\\s*\\)", "webview");
        addStatement("webViewGoForward", "WebView Go Forward", "WebView",
                "([a-zA-Z_][a-zA-Z0-9_]*)\\.goForward\\s*\\(\\s*\\)", "webview");

        addStatement("addListStr", "Add List String", "List",
                "([a-zA-Z_][a-zA-Z0-9_]*)\\.add\\s*\\(([^)]+)\\)", "list", "value");
        addStatement("clearList", "Clear List", "List",
                "([a-zA-Z_][a-zA-Z0-9_]*)\\.clear\\s*\\(\\s*\\)", "list");
        addNumber("lengthList", "List Size", "List",
                "([a-zA-Z_][a-zA-Z0-9_]*)\\.size\\s*\\(\\s*\\)", "list");
        addString("getAtListStr", "Get At List", "List",
                "([a-zA-Z_][a-zA-Z0-9_]*)\\.get\\s*\\(([^)]+)\\)", "list", "index");

        addStatement("mapPut", "Map Put", "Map",
                "([a-zA-Z_][a-zA-Z0-9_]*)\\.put\\s*\\(([^,]+),\\s*([^)]+)\\)", "map", "key", "value");
        addString("mapGet", "Map Get", "Map",
                "([a-zA-Z_][a-zA-Z0-9_]*)\\.get\\s*\\(([^)]+)\\)", "map", "key");
        addStatement("mapClear", "Map Clear", "Map",
                "([a-zA-Z_][a-zA-Z0-9_]*)\\.clear\\s*\\(\\s*\\)", "map");
        addNumber("mapSize", "Map Size", "Map",
                "([a-zA-Z_][a-zA-Z0-9_]*)\\.size\\s*\\(\\s*\\)", "map");

        addBoolean("stringEquals", "String Equals", "Operator",
                "([^=!<>]+)\\.equals\\s*\\(([^)]+)\\)", "left", "right");
        addBoolean("stringContains", "String Contains", "Operator",
                "([^\\.]+)\\.contains\\s*\\(([^)]+)\\)", "left", "right");
        addNumber("stringLength", "String Length", "Operator",
                "([^\\.]+)\\.length\\s*\\(\\s*\\)", "string");
        addNumber("random", "Random", "Math",
                "SketchwareUtil\\.getRandom\\s*\\(([^,]+),\\s*([^)]+)\\)", "min", "max");

        android.util.Log.i("BlockDefRegistry", "Patterns registered: " + definitions.size()
                + " | kq opcodes available: " + KqOpcodeSource.size());
    }

    public int getTotalCount() {
        return definitions.size();
    }

    public List getAllBlocks() {
        return definitions;
    }

    public void registerCustom(BlockDefinition definition) {
        if (definition == null) return;
        definition.setCustom(true);
        add(definition);
    }

    public void clearCustomBlocks() {
        clearCustom();
    }

    public void clearCustom() {
        List keep = new ArrayList();
        for (int i = 0; i < definitions.size(); i++) {
            BlockDefinition d = (BlockDefinition) definitions.get(i);
            if (!d.isCustom()) keep.add(d);
        }
        definitions.clear();
        byOpcode.clear();
        for (int i = 0; i < keep.size(); i++) {
            BlockDefinition d = (BlockDefinition) keep.get(i);
            definitions.add(d);
            byOpcode.put(d.getOpcode(), d);
        }
    }

}
