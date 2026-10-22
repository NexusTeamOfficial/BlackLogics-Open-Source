package com.nexusteam.blacklogics.generator.source;

import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.SeekBar;
import android.text.Editable;
import android.text.TextWatcher;

import java.util.HashMap;
import java.util.Map;

public class WidgetEventResolver {


    public interface EventBinder {
        void bind(StringBuilder out, String widgetId, String logic);
    }


    public static class ClickBinder implements EventBinder {
        @Override
        public void bind(StringBuilder out, String id, String logic) {
            out.append("        ").append(id)
               .append(".setOnClickListener(new View.OnClickListener() {\n")
               .append("            @Override\n")
               .append("            public void onClick(View _view) {\n");
            if (logic != null && !logic.trim().isEmpty()) {
                out.append(indent(logic, 16));
            }
            out.append("            }\n")
               .append("        });\n");
        }
    }

    public static class LongClickBinder implements EventBinder {
        @Override
        public void bind(StringBuilder out, String id, String logic) {
            out.append("        ").append(id)
               .append(".setOnLongClickListener(new View.OnLongClickListener() {\n")
               .append("            @Override\n")
               .append("            public boolean onLongClick(View _view) {\n");
            if (logic != null && !logic.trim().isEmpty()) {
                out.append(indent(logic, 16));
            }
            out.append("                return true;\n")
               .append("            }\n")
               .append("        });\n");
        }
    }

    public static class TouchBinder implements EventBinder {
        @Override
        public void bind(StringBuilder out, String id, String logic) {
            out.append("        ").append(id)
               .append(".setOnTouchListener(new View.OnTouchListener() {\n")
               .append("            @Override\n")
               .append("            public boolean onTouch(View _view, MotionEvent _event) {\n");
            if (logic != null && !logic.trim().isEmpty()) {
                out.append(indent(logic, 16));
            }
            out.append("                return false;\n")
               .append("            }\n")
               .append("        });\n");
        }
    }

    public static class TextChangeBinder implements EventBinder {
        @Override
        public void bind(StringBuilder out, String id, String logic) {
            out.append("        ").append(id)
               .append(".addTextChangedListener(new TextWatcher() {\n")
               .append("            @Override\n")
               .append("            public void onTextChanged(CharSequence _s, int _start, int _before, int _count) {\n")
               .append("                final String _text = _s.toString();\n\n");
            if (logic != null && !logic.trim().isEmpty()) {
                out.append(indent(logic, 16));
            }
            out.append("            }\n")
               .append("            @Override\n")
               .append("            public void beforeTextChanged(CharSequence _s, int _start, int _count, int _after) {}\n")
               .append("            @Override\n")
               .append("            public void afterTextChanged(Editable _s) {}\n")
               .append("        });\n");
        }
    }

    public static class CheckedChangeBinder implements EventBinder {
        @Override
        public void bind(StringBuilder out, String id, String logic) {
            out.append("        ").append(id)
               .append(".setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {\n")
               .append("            @Override\n")
               .append("            public void onCheckedChanged(CompoundButton _buttonView, boolean _isChecked) {\n");
            if (logic != null && !logic.trim().isEmpty()) {
                out.append(indent(logic, 16));
            }
            out.append("            }\n")
               .append("        });\n");
        }
    }

    public static class SpinnerBinder implements EventBinder {
        @Override
        public void bind(StringBuilder out, String id, String logic) {
            out.append("        ").append(id)
               .append(".setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {\n")
               .append("            @Override\n")
               .append("            public void onItemSelected(AdapterView<?> _parent, View _view, int _position, long _id) {\n");
            if (logic != null && !logic.trim().isEmpty()) {
                out.append(indent(logic, 16));
            }
            out.append("            }\n")
               .append("            @Override\n")
               .append("            public void onNothingSelected(AdapterView<?> _parent) {}\n")
               .append("        });\n");
        }
    }

    public static class SeekBarBinder implements EventBinder {
        @Override
        public void bind(StringBuilder out, String id, String logic) {
            out.append("        ").append(id)
               .append(".setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {\n")
               .append("            @Override\n")
               .append("            public void onProgressChanged(SeekBar _seekBar, int _progress, boolean _fromUser) {\n");
            if (logic != null && !logic.trim().isEmpty()) {
                out.append(indent(logic, 16));
            }
            out.append("            }\n")
               .append("            @Override\n")
               .append("            public void onStartTrackingTouch(SeekBar _seekBar) {}\n")
               .append("            @Override\n")
               .append("            public void onStopTrackingTouch(SeekBar _seekBar) {}\n")
               .append("        });\n");
        }
    }

    public static class FocusChangeBinder implements EventBinder {
        @Override
        public void bind(StringBuilder out, String id, String logic) {
            out.append("        ").append(id)
               .append(".setOnFocusChangeListener(new View.OnFocusChangeListener() {\n")
               .append("            @Override\n")
               .append("            public void onFocusChange(View _view, boolean _hasFocus) {\n");
            if (logic != null && !logic.trim().isEmpty()) {
                out.append(indent(logic, 16));
            }
            out.append("            }\n")
               .append("        });\n");
        }
    }

    public static class RatingBarBinder implements EventBinder {
        @Override
        public void bind(StringBuilder out, String id, String logic) {
            out.append("        ").append(id)
               .append(".setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {\n")
               .append("            @Override\n")
               .append("            public void onRatingChanged(RatingBar _ratingBar, float _rating, boolean _fromUser) {\n");
            if (logic != null && !logic.trim().isEmpty()) {
                out.append(indent(logic, 16));
            }
            out.append("            }\n")
               .append("        });\n");
        }
    }

    public static class ScrollChangeBinder implements EventBinder {
        @Override
        public void bind(StringBuilder out, String id, String logic) {
            out.append("        ").append(id)
               .append(".setOnScrollChangeListener(new View.OnScrollChangeListener() {\n")
               .append("            @Override\n")
               .append("            public void onScrollChange(View _view, int _scrollX, int _scrollY, int _oldScrollX, int _oldScrollY) {\n");
            if (logic != null && !logic.trim().isEmpty()) {
                out.append(indent(logic, 16));
            }
            out.append("            }\n")
               .append("        });\n");
        }
    }

    public static class ChipGroupCheckedBinder implements EventBinder {
        @Override
        public void bind(StringBuilder out, String id, String logic) {
            out.append("        ").append(id)
               .append(".setOnCheckedChangeListener(new com.google.android.material.chip.ChipGroup.OnCheckedChangeListener() {\n")
               .append("            @Override\n")
               .append("            public void onCheckedChanged(com.google.android.material.chip.ChipGroup _group, int _checkedId) {\n");
            if (logic != null && !logic.trim().isEmpty()) {
                out.append(indent(logic, 16));
            }
            out.append("            }\n")
               .append("        });\n");
        }
    }

    public static class NavigationItemSelectedBinder implements EventBinder {
        @Override
        public void bind(StringBuilder out, String id, String logic) {
            out.append("        ").append(id)
               .append(".setNavigationItemSelectedListener(new com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener() {\n")
               .append("            @Override\n")
               .append("            public boolean onNavigationItemSelected(android.view.MenuItem _item) {\n")
               .append("                final int _itemId = _item.getItemId();\n")
               .append("                final String _itemTitle = _item.getTitle() != null ? _item.getTitle().toString() : \"\";\n\n");
            if (logic != null && !logic.trim().isEmpty()) {
                out.append(indent(logic, 16));
            }
            out.append("                return true;\n")
               .append("            }\n")
               .append("        });\n");
        }
    }

    public static class BottomNavItemSelectedBinder implements EventBinder {
        @Override
        public void bind(StringBuilder out, String id, String logic) {
            out.append("        ").append(id)
               .append(".setOnNavigationItemSelectedListener(new com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener() {\n")
               .append("            @Override\n")
               .append("            public boolean onNavigationItemSelected(android.view.MenuItem _item) {\n")
               .append("                final int _itemId = _item.getItemId();\n")
               .append("                final String _itemTitle = _item.getTitle() != null ? _item.getTitle().toString() : \"\";\n\n");
            if (logic != null && !logic.trim().isEmpty()) {
                out.append(indent(logic, 16));
            }
            out.append("                return true;\n")
               .append("            }\n")
               .append("        });\n");
        }
    }


    public static class ItemClickBinder implements EventBinder {
        @Override
        public void bind(StringBuilder out, String id, String logic) {
            out.append("        ").append(id)
               .append(".setOnItemClickListener(new AdapterView.OnItemClickListener() {\n")
               .append("            @Override\n")
               .append("            public void onItemClick(AdapterView<?> _parent, View _view, int _position, long _id) {\n");
            if (logic != null && !logic.trim().isEmpty()) {
                out.append(indent(logic, 16));
            }
            out.append("            }\n")
               .append("        });\n");
        }
    }

    public static class ItemLongClickBinder implements EventBinder {
        @Override
        public void bind(StringBuilder out, String id, String logic) {
            out.append("        ").append(id)
               .append(".setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {\n")
               .append("            @Override\n")
               .append("            public boolean onItemLongClick(AdapterView<?> _parent, View _view, int _position, long _id) {\n");
            if (logic != null && !logic.trim().isEmpty()) {
                out.append(indent(logic, 16));
            }
            out.append("                return true;\n")
               .append("            }\n")
               .append("        });\n");
        }
    }

    public static class ChildClickBinder implements EventBinder {
        @Override
        public void bind(StringBuilder out, String id, String logic) {
            out.append("        ").append(id)
               .append(".setOnChildClickListener(new ExpandableListView.OnChildClickListener() {\n")
               .append("            @Override\n")
               .append("            public boolean onChildClick(ExpandableListView _parent, View _view, int _groupPosition, int _childPosition, long _id) {\n");
            if (logic != null && !logic.trim().isEmpty()) {
                out.append(indent(logic, 16));
            }
            out.append("                return false;\n")
               .append("            }\n")
               .append("        });\n");
        }
    }

    public static class ChildLongClickBinder implements EventBinder {
        @Override
        public void bind(StringBuilder out, String id, String logic) {
            out.append("        ").append(id)
               .append(".setOnChildLongClickListener(new ExpandableListView.OnChildLongClickListener() {\n")
               .append("            @Override\n")
               .append("            public boolean onChildLongClick(ExpandableListView _parent, View _view, int _groupPosition, int _childPosition, long _id) {\n");
            if (logic != null && !logic.trim().isEmpty()) {
                out.append(indent(logic, 16));
            }
            out.append("                return true;\n")
               .append("            }\n")
               .append("        });\n");
        }
    }

    public static class GroupClickBinder implements EventBinder {
        @Override
        public void bind(StringBuilder out, String id, String logic) {
            out.append("        ").append(id)
               .append(".setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {\n")
               .append("            @Override\n")
               .append("            public boolean onGroupClick(ExpandableListView _parent, View _view, int _groupPosition, long _id) {\n");
            if (logic != null && !logic.trim().isEmpty()) {
                out.append(indent(logic, 16));
            }
            out.append("                return false;\n")
               .append("            }\n")
               .append("        });\n");
        }
    }

    public static class GroupExpandBinder implements EventBinder {
        @Override
        public void bind(StringBuilder out, String id, String logic) {
            out.append("        ").append(id)
               .append(".setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {\n")
               .append("            @Override\n")
               .append("            public void onGroupExpand(int _groupPosition) {\n");
            if (logic != null && !logic.trim().isEmpty()) {
                out.append(indent(logic, 16));
            }
            out.append("            }\n")
               .append("        });\n");
        }
    }

    public static class GroupCollapseBinder implements EventBinder {
        @Override
        public void bind(StringBuilder out, String id, String logic) {
            out.append("        ").append(id)
               .append(".setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {\n")
               .append("            @Override\n")
               .append("            public void onGroupCollapse(int _groupPosition) {\n");
            if (logic != null && !logic.trim().isEmpty()) {
                out.append(indent(logic, 16));
            }
            out.append("            }\n")
               .append("        });\n");
        }
    }

    public static class GenericBinder implements EventBinder {
        @Override
        public void bind(StringBuilder out, String id, String logic) {
            out.append("        ").append(id)
               .append(".setOnClickListener(new View.OnClickListener() {\n")
               .append("            @Override\n")
               .append("            public void onClick(View _view) {\n");
            if (logic != null && !logic.trim().isEmpty()) {
                out.append(indent(logic, 16));
            }
            out.append("            }\n")
               .append("        });\n");
        }
    }


    public static void bindSeekBarFull(StringBuilder out, String id,
            String progressLogic, String startTrackLogic, String stopTrackLogic) {
        out.append("        ").append(id)
           .append(".setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {\n")
           .append("            @Override\n")
           .append("            public void onProgressChanged(SeekBar _seekBar, int _progress, boolean _fromUser) {\n");
        if (progressLogic != null && !progressLogic.trim().isEmpty()) out.append(indent(progressLogic, 16));
        out.append("            }\n")
           .append("            @Override\n")
           .append("            public void onStartTrackingTouch(SeekBar _seekBar) {\n");
        if (startTrackLogic != null && !startTrackLogic.trim().isEmpty()) out.append(indent(startTrackLogic, 16));
        out.append("            }\n")
           .append("            @Override\n")
           .append("            public void onStopTrackingTouch(SeekBar _seekBar) {\n");
        if (stopTrackLogic != null && !stopTrackLogic.trim().isEmpty()) out.append(indent(stopTrackLogic, 16));
        out.append("            }\n")
           .append("        });\n");
    }

    public static void bindWebViewClientFull(StringBuilder out, String id,
            String pageStartedLogic, String pageFinishedLogic) {
        out.append("        ").append(id)
           .append(".setWebViewClient(new WebViewClient() {\n")
           .append("            @Override\n")
           .append("            public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {\n")
           .append("                super.onPageStarted(view, url, favicon);\n");
        if (pageStartedLogic != null && !pageStartedLogic.trim().isEmpty()) out.append(indent(pageStartedLogic, 16));
        out.append("            }\n")
           .append("            @Override\n")
           .append("            public void onPageFinished(WebView view, String url) {\n")
           .append("                super.onPageFinished(view, url);\n");
        if (pageFinishedLogic != null && !pageFinishedLogic.trim().isEmpty()) out.append(indent(pageFinishedLogic, 16));
        out.append("            }\n")
           .append("        });\n");
    }

    public static void bindViewPagerFull(StringBuilder out, String id,
            String pageSelectedLogic, String pageScrolledLogic, String pageScrollStateChangedLogic) {
        out.append("        ").append(id)
           .append(".addOnPageChangeListener(new ViewPager.OnPageChangeListener() {\n")
           .append("            @Override\n")
           .append("            public void onPageSelected(int _position) {\n");
        if (pageSelectedLogic != null && !pageSelectedLogic.trim().isEmpty()) out.append(indent(pageSelectedLogic, 16));
        out.append("            }\n")
           .append("            @Override\n")
           .append("            public void onPageScrolled(int _position, float _positionOffset, int _positionOffsetPixels) {\n");
        if (pageScrolledLogic != null && !pageScrolledLogic.trim().isEmpty()) out.append(indent(pageScrolledLogic, 16));
        out.append("            }\n")
           .append("            @Override\n")
           .append("            public void onPageScrollStateChanged(int _state) {\n");
        if (pageScrollStateChangedLogic != null && !pageScrollStateChangedLogic.trim().isEmpty()) out.append(indent(pageScrollStateChangedLogic, 16));
        out.append("            }\n")
           .append("        });\n");
    }

    public static void bindViewPager2Full(StringBuilder out, String id,
            String pageSelectedLogic, String pageScrolledLogic, String pageScrollStateChangedLogic) {
        out.append("        ").append(id)
           .append(".registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {\n")
           .append("            @Override\n")
           .append("            public void onPageSelected(int _position) {\n")
           .append("                super.onPageSelected(_position);\n");
        if (pageSelectedLogic != null && !pageSelectedLogic.trim().isEmpty()) out.append(indent(pageSelectedLogic, 16));
        out.append("            }\n")
           .append("            @Override\n")
           .append("            public void onPageScrolled(int _position, float _positionOffset, int _positionOffsetPixels) {\n")
           .append("                super.onPageScrolled(_position, _positionOffset, _positionOffsetPixels);\n");
        if (pageScrolledLogic != null && !pageScrolledLogic.trim().isEmpty()) out.append(indent(pageScrolledLogic, 16));
        out.append("            }\n")
           .append("            @Override\n")
           .append("            public void onPageScrollStateChanged(int _state) {\n")
           .append("                super.onPageScrollStateChanged(_state);\n");
        if (pageScrollStateChangedLogic != null && !pageScrollStateChangedLogic.trim().isEmpty()) out.append(indent(pageScrollStateChangedLogic, 16));
        out.append("            }\n")
           .append("        });\n");
    }

    public static void bindTabLayoutFull(StringBuilder out, String id,
            String selectedLogic, String unselectedLogic, String reselectedLogic) {
        out.append("        ").append(id)
           .append(".addOnTabSelectedListener(new com.google.android.material.tabs.TabLayout.OnTabSelectedListener() {\n")
           .append("            @Override\n")
           .append("            public void onTabSelected(com.google.android.material.tabs.TabLayout.Tab _tab) {\n")
           .append("                final int _position = _tab.getPosition();\n")
           .append("                final String _tabText = _tab.getText() != null ? _tab.getText().toString() : \"\";\n\n");
        if (selectedLogic != null && !selectedLogic.trim().isEmpty()) out.append(indent(selectedLogic, 16));
        out.append("            }\n")
           .append("            @Override\n")
           .append("            public void onTabUnselected(com.google.android.material.tabs.TabLayout.Tab _tab) {\n")
           .append("                final int _position = _tab.getPosition();\n")
           .append("                final String _tabText = _tab.getText() != null ? _tab.getText().toString() : \"\";\n\n");
        if (unselectedLogic != null && !unselectedLogic.trim().isEmpty()) out.append(indent(unselectedLogic, 16));
        out.append("            }\n")
           .append("            @Override\n")
           .append("            public void onTabReselected(com.google.android.material.tabs.TabLayout.Tab _tab) {\n")
           .append("                final int _position = _tab.getPosition();\n")
           .append("                final String _tabText = _tab.getText() != null ? _tab.getText().toString() : \"\";\n\n");
        if (reselectedLogic != null && !reselectedLogic.trim().isEmpty()) out.append(indent(reselectedLogic, 16));
        out.append("            }\n")
           .append("        });\n");
    }


    private static String indent(String code, int spaces) {
        StringBuilder pad = new StringBuilder();
        for (int i = 0; i < spaces; i++) {
            pad.append(' ');
        }
        String p = pad.toString();
        if (code == null || code.trim().isEmpty()) {
            return "";
        }
        return p + code.replace("\n", "\n" + p) + "\n";
    }


    public static EventBinder resolve(String widgetType, String eventType) {
        if (eventType == null || eventType.isEmpty()) {
            eventType = "onClick";
        }

        switch (eventType) {
            case "onClick":
                return new ClickBinder();
            case "onLongClick":
                return new LongClickBinder();
            case "onTouch":
                return new TouchBinder();
            case "onTextChanged":
                return new TextChangeBinder();
            case "onCheckedChanged":
                return new CheckedChangeBinder();
            case "onItemSelected":
                return new SpinnerBinder();
            case "onProgressChanged":
                return new SeekBarBinder();
            case "onFocusChange":
                return new FocusChangeBinder();
            case "onRatingChanged":
                return new RatingBarBinder();
            case "onScrollChanged":
                return new ScrollChangeBinder();
            case "onChipCheckedChanged":
                return new ChipGroupCheckedBinder();
            case "onNavigationItemSelected":
                return new NavigationItemSelectedBinder();
            case "onNavItemSelected":
                return new BottomNavItemSelectedBinder();

            case "onItemClick":
                return new ItemClickBinder();
            case "onItemLongClick":
                return new ItemLongClickBinder();
            case "onChildClick":
                return new ChildClickBinder();
            case "onChildLongClick":
                return new ChildLongClickBinder();
            case "onGroupClick":
                return new GroupClickBinder();
            case "onGroupExpand":
                return new GroupExpandBinder();
            case "onGroupCollapse":
                return new GroupCollapseBinder();

            default:
                return resolve(widgetType);
        }
    }

    public static EventBinder resolve(String widgetType) {
        if (widgetType == null || widgetType.isEmpty()) {
            return new GenericBinder();
        }

        EventBinder b = MAP.get(widgetType);

        if (b == null) {
            for (Map.Entry<String, EventBinder> entry : MAP.entrySet()) {
                if (widgetType.toLowerCase().contains(entry.getKey().toLowerCase())) {
                    return entry.getValue();
                }
            }
        }

        return b != null ? b : new GenericBinder();
    }

    public static boolean isAdapterViewWidget(String widgetType) {
        return "ListView".equals(widgetType)
            || "GridView".equals(widgetType)
            || "Spinner".equals(widgetType);
    }

    public static boolean isExpandableListWidget(String widgetType) {
        return "ExpandableListView".equals(widgetType);
    }


    private static final Map<String, EventBinder> MAP = new HashMap<>();

    static {
        MAP.put("Button", new ClickBinder());
        MAP.put("TextView", new ClickBinder());
        MAP.put("ImageView", new ClickBinder());
        MAP.put("ImageButton", new ClickBinder());
        MAP.put("LinearLayout", new ClickBinder());
        MAP.put("RelativeLayout", new ClickBinder());
        MAP.put("FrameLayout", new ClickBinder());
        MAP.put("CardView", new ClickBinder());
        MAP.put("MaterialButton", new ClickBinder());
        MAP.put("MaterialCardView", new ClickBinder());
        MAP.put("FloatingActionButton", new ClickBinder());
        MAP.put("RecyclerView", new ClickBinder());
        MAP.put("ListView", new ClickBinder());
        MAP.put("GridView", new ClickBinder());
        MAP.put("ExpandableListView", new ClickBinder());

        MAP.put("EditText", new TextChangeBinder());
        MAP.put("TextInputEditText", new TextChangeBinder());
        MAP.put("AutoCompleteTextView", new TextChangeBinder());
        MAP.put("MultiAutoCompleteTextView", new TextChangeBinder());

        MAP.put("CheckBox", new CheckedChangeBinder());
        MAP.put("Switch", new CheckedChangeBinder());
        MAP.put("RadioButton", new CheckedChangeBinder());
        MAP.put("ToggleButton", new CheckedChangeBinder());

        MAP.put("Spinner", new SpinnerBinder());

        MAP.put("SeekBar", new SeekBarBinder());

        MAP.put("RatingBar", new ClickBinder());
        MAP.put("TabLayout", new ClickBinder());
        MAP.put("ChipGroup", new ClickBinder());
        MAP.put("NavigationView", new ClickBinder());
        MAP.put("BottomNavigationView", new ClickBinder());
    }
}