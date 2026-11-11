package com.nexusteam.blacklogics.logic.editor;

import java.util.ArrayList;

public class SourceUtil {
    public static String getFunctionName(String str, String str2) {
        String str3 = "\tprivate void " + str + " (";
        ArrayList tokenize = StringUtil.tokenize(str2);
        int i = 1;
        String str4 = str3;
        for (int i2 = 0; i2 < tokenize.size(); i2++) {
            str3 = (String) tokenize.get(i2);
            if (str3.charAt(0) == '%') {
                if (str3.charAt(1) == 'b') {
                    if (i == 0) {
                        str4 = str4 + ", ";
                    }
                    str4 = str4 + "boolean _" + str3.substring(3);
                    i = 0;
                } else if (str3.charAt(1) == 'd') {
                    if (i == 0) {
                        str4 = str4 + ", ";
                    }
                    str4 = str4 + "int _" + str3.substring(3);
                    i = 0;
                } else if (str3.charAt(1) == 's') {
                    if (i == 0) {
                        str4 = str4 + ", ";
                    }
                    str4 = str4 + "String _" + str3.substring(3);
                    i = 0;
                }
            }
        }
        return ((str4 + ") {\r\n") + "<?" + str + "_" + "moreBlock" + "?>") + "\r\n\t}";
    }

    public static String getListName(int i, String str) {
        String str2 = "" + "\tprivate ";
        return i == 1 ? str2 + "ArrayList<Integer> " + str + " = new ArrayList<Integer>();" : i == 2 ? str2 + "ArrayList<String> " + str + " = new ArrayList<String>();" : "";
    }

    public static String getRandomSrc() {
        return "\tprivate int getRandom(int _minValue ,int _maxValue){\r\n\t\tRandom random = new Random();\r\n\t\treturn random.nextInt(_maxValue - _minValue + 1) + _minValue;\r\n\t}\r\n";
    }

    public static String getShowMessageSrc() {
        return "\tprivate void showMessage(String _s) {\r\n\t\tToast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();\r\n\t}\r\n";
    }

    public static String getVariableName(int i, String str) {
        String str2 = "" + "\tprivate ";
        if (i == 0) {
            str2 = str2 + "boolean";
        } else if (i == 1) {
            str2 = str2 + "int";
        } else if (i != 2) {
            return "";
        } else {
            str2 = str2 + "String";
        }
        str2 = str2 + " " + str;
        if (i == 1) {
            str2 = str2 + " = 0";
        } else if (i == 2) {
            str2 = str2 + " = \"\"";
        }
        return str2 + ";";
    }

    public static String getVariableNameFromId(String str) {
        return str;
    }



}
