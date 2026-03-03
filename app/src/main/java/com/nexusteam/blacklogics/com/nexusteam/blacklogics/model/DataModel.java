package com.nexusteam.blacklogics.model;

public class DataModel {
    private static final String[] BLOCKED_LAYOUT_NAMES = {
    "null",
    "new",
    "class",
    "public",
    "private",
    "protected",
    "static",
    "final",
    "void",
    "int",
    "float",
    "double",
    "boolean",
    "char",
    "long",
    "short",
    "byte",
    "if",
    "else",
    "for",
    "while",
    "switch",
    "case",
    "default",
    "try",
    "catch",
    "finally",
    "return",
    "break",
    "continue",
    "this",
    "super"
};
   public static String ADD_ACTIVITY = "Activity";
    public static String ADD_SPACE = " ";
    public static String ADD_UNDERSCORE = "_";
    public static String ADD_DOT = ".";
    public static String ADD_FRAGMENT = "Fragment";
    public static String ADD_DIALOG_FRAGMENT = "DialogFragment";
    public static String DEFAULT_ACTIVITY = "MainActivity";
    public static String DEFAULT_LAYOUT_NAME = "main";
    public static String PROJECT_TEMPLATE_NAME = "NewProject";
    public static int DEFAULT_PROJECT_ID = 601;
    public static String PROJECT_TEMPLATE_PACKAGE_NAME = "com.my.newproject";
    public static int DEFAULT_VERSION = 1;
    public static int ZERO_VERSION = 0;
    public static int MAX_VERSION = 99;
        
    public static String ADD_ACTIVITY() {
        return "activity";
    }
    
    public static String ADD_SPACE() {
        return " ";
    }
    
    public static String ADD_UNDERSCORE() {
        return "_";
    }
    
    public static String ADD_DOT() {
        return ".";
    }
    
    public static String ADD_FRAGMENT() {
        return "fragment";
    }
    
    public static String ADD_DIALOG_FRAGMENT() {
        return "dialog_fragment";
    }
    
    public static String DEFAULT_ACTIVITY() {
        return "MainActivity";
    }
    
    public static String DEFAULT_LAYOUT_NAME() {
        return "main";
    }
    
    public static int DEFAULT_PROJECT_ID() {
        return 601;
    }
    
    public static String PROJECT_TEMPLATE_NAME() {
        return "NewProject";
    }
    
    public static String PROJECT_TEMPLATE_PACKAGE_NAME() {
        return "com.my.newproject";
    }
    
    public static String[] BLOCKED_VALIDATOR_NAME() {
        return BLOCKED_LAYOUT_NAMES;
    }
    
    public static int DEFAULT_VERSION() {
        return 1;
    }
    
    public static int ZERO_VERSION() {
        return 0;
    }
    
    public static int MAX_VERSION() {
        return 99;
    }
    
} 