package com.besome.blacklogics.logic.editor;

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

   /* public static String makeEventSrc(EventBean eventBean) {
        String str = "";
        return eventBean.eventType == 1 ? eventBean.eventName.equals("onClick") ? "\t\t" + getVariableNameFromId(eventBean.targetId) + ".setOnClickListener(new View.OnClickListener() {\n" + "\t\t\t" + "@Override" + "\n" + "\t\t\t" + "public void onClick(View _v) { " + "\n" + "<?" + eventBean.targetId + "_" + eventBean.eventName + "?>" + "\n" + "\t\t\t" + "}" + "\n" + "\t\t" + "});" : eventBean.eventName.equals("onCheckedChange") ? "\t\t" + getVariableNameFromId(eventBean.targetId) + ".setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {\n" + "\t\t\t" + "@Override" + "\n" + "\t\t\t" + "public void onCheckedChanged(CompoundButton _buttonView, boolean _isChecked)  { " + "\n" + "<?" + eventBean.targetId + "_" + eventBean.eventName + "?>" + "\n" + "\t\t\t" + "}" + "\n" + "\t\t" + "});" : eventBean.eventName.equals("onItemSelected") ? "\t\t" + getVariableNameFromId(eventBean.targetId) + ".setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {\n" + "\t\t\t" + "@Override" + "\n" + "\t\t\t" + "public void onItemSelected(AdapterView<?> _parent, View _view, int _position, long _id) { " + "\n" + "<?" + eventBean.targetId + "_" + eventBean.eventName + "?>" + "\n" + "\t\t\t" + "}" + "\n" + "\t\t\t" + "@Override" + "\n" + "\t\t\t" + "public void onNothingSelected(AdapterView<?> _parent) { " + "\n" + "\t\t\t" + "}" + "\n" + "\t\t" + "});" : eventBean.eventName.equals("onItemClicked") ? "\t\t" + getVariableNameFromId(eventBean.targetId) + ".setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" + "\t\t\t" + "@Override" + "\n" + "\t\t\t" + "public void onItemClick(AdapterView<?> _parent, View _view, int _position, long _id) { " + "\n" + "<?" + eventBean.targetId + "_" + eventBean.eventName + "?>" + "\n" + "\t\t\t" + "}" + "\n" + "\t\t" + "});" : eventBean.eventName.equals("onTextChanged") ? "\t\t" + getVariableNameFromId(eventBean.targetId) + ".addTextChangedListener(new TextWatcher() {\n" + "\t\t\t" + "@Override" + "\n" + "\t\t\t" + "public void beforeTextChanged(CharSequence _text, int _start, int _count, int _after) {" + "\n" + "\t\t\t" + "}" + "\n" + "\t\t\t" + "@Override" + "\n" + "\t\t\t" + "public void onTextChanged(CharSequence _text, int _start, int _before, int _count) {" + "\n" + "<?" + eventBean.targetId + "_" + eventBean.eventName + "?>" + "\n" + "\t\t\t" + "}" + "\n" + "\t\t\t" + "@Override" + "\n" + "\t\t\t" + "public void afterTextChanged(Editable _text) {" + "\n" + "\t\t\t" + "}" + "\n" + "\t\t" + "});" : str : str;
    }*/

  /*  public static String makeInitComponentSrc(ComponentBean componentBean) {
        String str = componentBean.componentId;
        switch (componentBean.type) {
            case 2:
                return "\t\t" + str + " = getSharedPreferences(\"" + componentBean.param1 + "\", Activity.MODE_PRIVATE);";
            case 4:
                return "\t\t" + str + " = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);";
            default:
                return "";
        }
    }*/

  /*  public static String makeInitSrc(ViewBean viewBean) {
        String variableNameFromId = getVariableNameFromId(viewBean.id);
        switch (viewBean.type) {
            case 0:
                return "\t\t" + variableNameFromId + " = (LinearLayout) findViewById(R.id." + viewBean.id + ");";
            case 3:
                return "\t\t" + variableNameFromId + " = (Button) findViewById(R.id." + viewBean.id + ");";
            case 4:
                return "\t\t" + variableNameFromId + " = (TextView) findViewById(R.id." + viewBean.id + ");";
            case 5:
                return "\t\t" + variableNameFromId + " = (EditText) findViewById(R.id." + viewBean.id + ");";
            case 6:
                return "\t\t" + variableNameFromId + " = (ImageView) findViewById(R.id." + viewBean.id + ");";
            case 9:
                return "\t\t" + variableNameFromId + " = (ListView) findViewById(R.id." + viewBean.id + ");";
            case 10:
                return "\t\t" + variableNameFromId + " = (Spinner) findViewById(R.id." + viewBean.id + ");";
            case 11:
                return "\t\t" + variableNameFromId + " = (CheckBox) findViewById(R.id." + viewBean.id + ");";
            default:
                return "";
        }
    }

    public static String makeOnclickSrc(ViewBean viewBean) {
        switch (viewBean.type) {
            case 3:
            case 4:
            case 6:
                return "\t\tif (v.getId() == R.id." + viewBean.id + ") {" + "\r\n" + "\t\t\t<?logic_" + viewBean.id + "?>" + "\r\n" + "\t\t\treturn;\r\n\t\t}";
            case 5:
                return "";
            default:
                return "";
        }
    }

    public static String makeVariableSrc(ComponentBean componentBean) {
        String str = componentBean.componentId;
        switch (componentBean.type) {
            case 1:
                return "\tprivate Intent " + str + " = new Intent();";
            case 2:
                return "\tprivate SharedPreferences " + str + ";";
            case 3:
                return "\tprivate Calendar " + str + " = Calendar.getInstance();";
            case 4:
                return "\tprivate Vibrator " + str + ";";
            default:
                return "";
        }
    }

    public static String makeVariableSrc(ViewBean viewBean) {
        String variableNameFromId = getVariableNameFromId(viewBean.id);
        switch (viewBean.type) {
            case 0:
                return "\tprivate LinearLayout " + variableNameFromId + ";";
            case 3:
                return "\tprivate Button " + variableNameFromId + ";";
            case 4:
                return "\tprivate TextView " + variableNameFromId + ";";
            case 5:
                return "\tprivate EditText " + variableNameFromId + ";";
            case 6:
                return "\tprivate ImageView " + variableNameFromId + ";";
            case 9:
                return "\tprivate ListView " + variableNameFromId + ";";
            case 10:
                return "\tprivate Spinner " + variableNameFromId + ";";
            case 11:
                return "\tprivate CheckBox " + variableNameFromId + ";";
            default:
                return "";
        }
    }*/
}
