package com.nexusteam.blacklogics.sdk.layouteditor;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import android.view.View;
import android.view.ViewGroup;

public class IdManager {

    private static IdManager instance;

    public HashMap<View, String> mViewIdMap;

    private final HashMap<String, View> mIdViewMap = new HashMap<>();

    IdManager() {
        mViewIdMap = new HashMap<>();
    }

    public static synchronized IdManager getInstance() {
        if (instance == null) {
            instance = new IdManager();
        }
        return instance;
    }

    public View getView(String id) {
        if (id == null) return null;
        return mIdViewMap.get(id);
    }

    public View getView(int androidId) {
        for (Map.Entry<View, String> e : mViewIdMap.entrySet()) {
            if (e.getKey().getId() == androidId) return e.getKey();
        }
        return null;
    }

    public int getId(String id) {
        View v = getView(id);
        return v != null ? v.getId() : 0;
    }

    public String generateNewId(View view) {
        if (view == null) return "view1";
        String name = preferredIdPrefix(view.getClass().getSimpleName());
        if (name == null || name.isEmpty()) {
            name = "view";
        }
        int n = 1;
        while (isIdUsed(name + n)) {
            n++;
        }
        return name + n;
    }

    /**
     * Maps common widget class simple names to short, stable ID prefixes
     * so reload / save does not produce ugly names like switchmaterial1.
     */
    private static String preferredIdPrefix(String simpleName) {
        if (simpleName == null) return "view";
        String s = simpleName;
        if (s.startsWith("Placeholder") || s.toLowerCase().startsWith("placeholder")) {
            return "widget";
        }
        switch (s) {
            case "SwitchMaterial":
            case "MaterialSwitch":
                return "switch";
            case "MaterialButton":
                return "materialbutton";
            case "MaterialCardView":
                return "materialcard";
            case "TextInputLayout":
                return "textinputlayout";
            case "TextInputEditText":
                return "textinputedittext";
            case "BottomNavigationView":
                return "bottomnav";
            case "NavigationView":
                return "navigation";
            case "FloatingActionButton":
                return "fab";
            case "LottieAnimationView":
                return "lottie";
            case "CircleImageView":
                return "circleimage";
            case "CollapsingToolbarLayout":
                return "collapsingtoolbar";
            case "CoordinatorLayout":
                return "coordinator";
            case "ConstraintLayout":
                return "constraint";
            case "NestedScrollView":
                return "nestedscroll";
            case "HorizontalScrollView":
                return "hscroll";
            case "ScrollView":
                return "scroll";
            case "RecyclerView":
                return "recycler";
            case "ViewPager":
                return "viewpager";
            case "ViewPager2":
                return "viewpager2";
            case "AutoCompleteTextView":
                return "autocomplete";
            case "ExpandableListView":
                return "expandablelist";
            case "ImageButton":
                return "imagebutton";
            case "SearchView":
                return "search";
            case "ChipGroup":
                return "chipgroup";
            case "RadioGroup":
                return "radiogroup";
            case "LinearLayout":
                return "linear";
            case "RelativeLayout":
                return "relative";
            case "FrameLayout":
                return "frame";
            case "GridLayout":
                return "gridlayout";
            case "TableLayout":
                return "table";
            case "MotionLayout":
                return "motion";
            case "CardView":
                return "card";
            case "Chip":
                return "chip";
            case "Slider":
                return "slider";
            case "TabLayout":
                return "tablayout";
            default:
                return s.toLowerCase();
        }
    }

    public String getId(View view) {
        return view != null ? mViewIdMap.get(view) : null;
    }

    public void setId(View view, String id) {
        addNewId(view, id);
    }

    public void addNewId(View view, String s) {
        if (view == null || s == null) {
            throw new IllegalArgumentException("view and id must not be null");
        }
        if (isIdUsed(s)) {
            throw new IllegalArgumentException(s + " is already in use");
        }
        view.setId(View.generateViewId());
        mViewIdMap.put(view, s);
        mIdViewMap.put(s, view);
    }

    public boolean isIdUsed(String s) {
        if (s == null) return false;
        return mIdViewMap.containsKey(s);
    }

    public void remove(View view) {
        if (view == null) return;
        String id = mViewIdMap.remove(view);
        if (id != null) {
            mIdViewMap.remove(id);
        }
    }

    public void remove(View view, boolean recursive) {
        if (view == null) return;
        String id = mViewIdMap.remove(view);
        if (id != null) {
            mIdViewMap.remove(id);
        }

        if (recursive && view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                remove(group.getChildAt(i), true);
            }
        }
    }

    public void updateId(View view, String newName) {
        if (view == null || newName == null) return;
        String old = mViewIdMap.get(view);
        if (old != null) {
            mIdViewMap.remove(old);
        }
        if (isIdUsed(newName) && getView(newName) != view) {
            if (old != null) mIdViewMap.put(old, view);
            return;
        }
        mViewIdMap.put(view, newName);
        mIdViewMap.put(newName, view);
    }

    public ArrayList<View> getViews() {
        return new ArrayList<>(mViewIdMap.keySet());
    }

    public ArrayList<String> getIds() {
        return new ArrayList<>(mIdViewMap.keySet());
    }

    void putReverseForRestore(String id, View view) {
        if (id != null && view != null) mIdViewMap.put(id, view);
    }

    public void clearAllIds() {
        mViewIdMap.clear();
        mIdViewMap.clear();
    }

    public boolean hasId(View view) {
        return view != null && mViewIdMap.containsKey(view);
    }

    public String generate(View view) {
        return generateNewId(view);
    }

    public void bind(View view, String id) {
        addNewId(view, id);
    }
}
