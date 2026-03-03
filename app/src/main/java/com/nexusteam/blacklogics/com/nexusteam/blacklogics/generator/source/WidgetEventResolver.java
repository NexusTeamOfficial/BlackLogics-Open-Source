package com.nexusteam.blacklogics.generator.source;

import java.util.HashMap;
import java.util.Map;

public class WidgetEventResolver {

    public interface EventBinder {
        void bind(StringBuilder out, String widgetId, String logic);
    }



    private static String indent(String code, int spaces) {
    StringBuilder pad = new StringBuilder();
    for (int i = 0; i < spaces; i++) {
        pad.append(' ');
    }

    String p = pad.toString();
    return p + code.replace("\n", "\n" + p) + "\n";
}




    public static class ClickBinder implements EventBinder {
        @Override
        public void bind(StringBuilder out, String id, String logic) {
            out.append("        ").append(id)
               .append(".setOnClickListener(new View.OnClickListener() {\n")
               .append("            @Override public void onClick(View v) {\n")
               .append(indent(logic, 16))
               .append("            }\n")
               .append("        });\n");
        }
    }

    public static class TextChangeBinder implements EventBinder {
        @Override
        public void bind(StringBuilder out, String id, String logic) {
            out.append("        ").append(id)
               .append(".addTextChangedListener(new TextWatcher() {\n")
               .append("            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {\n")
               .append(indent(logic, 16))
               .append("            }\n")
               .append("            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}\n")
               .append("            @Override public void afterTextChanged(Editable s) {}\n")
               .append("        });\n");
        }
    }

    public static class CheckedChangeBinder implements EventBinder {
        @Override
        public void bind(StringBuilder out, String id, String logic) {
            out.append("        ").append(id)
               .append(".setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {\n")
               .append("            @Override public void onCheckedChanged(CompoundButton v, boolean isChecked) {\n")
               .append(indent(logic, 16))
               .append("            }\n")
               .append("        });\n");
        }
    }

    public static class SpinnerBinder implements EventBinder {
        @Override
        public void bind(StringBuilder out, String id, String logic) {
            out.append("        ").append(id)
               .append(".setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {\n")
               .append("            @Override public void onItemSelected(AdapterView<?> p, View v, int pos, long id) {\n")
               .append(indent(logic, 16))
               .append("            }\n")
               .append("            @Override public void onNothingSelected(AdapterView<?> p) {}\n")
               .append("        });\n");
        }
    }

    public static class SeekBarBinder implements EventBinder {
        @Override
        public void bind(StringBuilder out, String id, String logic) {
            out.append("        ").append(id)
               .append(".setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {\n")
               .append("            @Override public void onProgressChanged(SeekBar sb, int progress, boolean fromUser) {\n")
               .append(indent(logic, 16))
               .append("            }\n")
               .append("            @Override public void onStartTrackingTouch(SeekBar sb) {}\n")
               .append("            @Override public void onStopTrackingTouch(SeekBar sb) {}\n")
               .append("        });\n");
        }
    }



    private static final Map<String, EventBinder> MAP = new HashMap<>();

    static {
        MAP.put("Button", new ClickBinder());
        MAP.put("TextView", new ClickBinder());
        MAP.put("ImageView", new ClickBinder());

        MAP.put("EditText", new TextChangeBinder());
        MAP.put("TextInputEditText", new TextChangeBinder());

        MAP.put("CheckBox", new CheckedChangeBinder());
        MAP.put("Switch", new CheckedChangeBinder());
        MAP.put("RadioButton", new CheckedChangeBinder());

        MAP.put("Spinner", new SpinnerBinder());
        MAP.put("SeekBar", new SeekBarBinder());
    }



    public static EventBinder resolve(String widgetType) {
        EventBinder b = MAP.get(widgetType);
        return b != null ? b : new ClickBinder();
    }
}
