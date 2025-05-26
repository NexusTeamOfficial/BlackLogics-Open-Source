package com.besome.blacklogics.logic.editor;

public class DefineSource {
    public static final String[] CALENDAR_FIELD = new String[]{"YEAR", "MONTH", "DAY_OF_MONTH", "HOUR", "MINUTE", "SECOND"};
    public static final String[] EVENT_LIST_BUTTON = new String[]{"onClick"};
    public static final String[] EVENT_LIST_CHECKBOX = new String[]{"onClick", "onCheckedChange"};
    public static final String[] EVENT_LIST_EDITTEXT = new String[]{"onClick", "onTextChanged"};
    public static final String[] EVENT_LIST_IMAGEVIEW = new String[]{"onClick"};
    public static final String[] EVENT_LIST_LISTVIEW = new String[]{"onItemClicked"};
    public static final String[] EVENT_LIST_SPINNER = new String[]{"onItemSelected"};
    public static final String[] EVENT_LIST_TEXTVIEW = new String[]{"onClick"};
    public static final String EVENT_NAME_DEFINEFUNC = "moreBlock";
    public static final String EVENT_NAME_INIT = "initializeLogic";
    public static final String EVENT_NAME_ONCHECKCHANGED = "onCheckedChange";
    public static final String EVENT_NAME_ONCLICK = "onClick";
    public static final String EVENT_NAME_ONITEMCLICKED = "onItemClicked";
    public static final String EVENT_NAME_ONITEMSELECTED = "onItemSelected";
    public static final String EVENT_NAME_ONTEXTCHANGED = "onTextChanged";
    public static final String EVENT_NAME_TOUCHDOWN = "touchDown";
    public static final String EVENT_NAME_TOUCHMOVE = "touchMove";
    public static final String EVENT_NAME_TOUCHUP = "touchUp";
    public static final String[] INTENT_ACTION_COMMON = new String[]{"ACTION_DIAL", "ACTION_CALL", "ACTION_VIEW"};
    public static final String LOGIC_TITLE_ONCREATE = "onCreate";
    public static final int PERMISSION_CALL = 0;
    public static final int PERMISSION_INTERNET = 1;
    public static final int PERMISSION_VIBRATOR = 2;
    public static final String[] RESERVED_WORD = new String[]{"abstract", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue", "default", "do", "double", "else", "extends", "final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "null", "package", "private", "protected", "public", "return", "short", "static", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void", "volatile", "while", "Activity", "View", "EditText", "onCreate", "onClick", "LinearLayout", "TextView", "ImageView", "Button", "ArrayList", "String", "Intent", "SharedPreperences", "Calendar"};
    public static final String SRC_LINE = "\r\n";
    public static final String TAG_FUNCTION = "function";
    public static final String TAG_INIT = "init";
    public static final String TAG_INIT_LOGIC = "init_logic";
    public static final String TAG_LIST_LOGIC = "list_logic";
    public static final String TAG_ONACTIVITYRESULT = "onactivityresult";
    public static final String TAG_ONCLICK = "onclick";
    public static final String TAG_PACKAGE = "package";
    public static final String TAG_ROOT = "root";
    public static final String TAG_VARIABLE = "variable";
    public static final String TAG_VARIABLE_LOGIC = "variable_logic";
    public static final String[] USED_WORD_COMMON = new String[]{"onCreate", "setContentView", "initialize", "initializeLogic", "getRandom", "showMessage"};
    public static final String[] VISIBILITY_FIELD = new String[]{"VISIBLE", "INVISIBLE", "GONE"};

    public static String[] getIntentAction() {
        return INTENT_ACTION_COMMON;
    }

    public static final String[] getUsedWord(String var0) {
        return USED_WORD_COMMON;
    }

    public static String[] getViewEventList(int var0) {
        switch(var0) {
            case 3:
                return EVENT_LIST_BUTTON;
            case 4:
                return EVENT_LIST_TEXTVIEW;
            case 5:
                return EVENT_LIST_EDITTEXT;
            case 6:
                return EVENT_LIST_IMAGEVIEW;
            case 7:
            case 8:
            default:
                return new String[0];
            case 9:
                return EVENT_LIST_LISTVIEW;
            case 10:
                return EVENT_LIST_SPINNER;
            case 11:
                return EVENT_LIST_CHECKBOX;
        }
    }

    public static String getWidgetName(int var0) {
        switch(var0) {
            case 0:
                return "LinearLayout";
            case 1:
            case 2:
            case 7:
            case 8:
            default:
                return "";
            case 3:
                return "Button";
            case 4:
                return "TextView";
            case 5:
                return "EditText";
            case 6:
                return "ImageView";
            case 9:
                return "ListView";
            case 10:
                return "Spinner";
            case 11:
                return "CheckBox";
        }
    }
}

