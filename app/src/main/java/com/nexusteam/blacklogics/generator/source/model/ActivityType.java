package com.nexusteam.blacklogics.generator.source.model;

/**
 * Activity / Fragment type registry.
 *
 * IMPORTANT (backward-compat):
 *   FRAGMENT_ACTIVITY is stored in existing projects as the UI "Fragment" choice.
 *   It is intentionally treated as a plain androidx.fragment.app.Fragment
 *   (not FragmentActivity). Do NOT rename the enum constant.
 */
public enum ActivityType {
    ACTIVITY("Activity", "android.app.Activity", "extends Activity"),
    APP_COMPAT_ACTIVITY("AppCompatActivity", "androidx.appcompat.app.AppCompatActivity", "extends AppCompatActivity"),
    /**
     * UI label = "Fragment".  Generated as androidx.fragment.app.Fragment.
     * Enum name kept for saved ActivityConfig entries.
     */
    FRAGMENT_ACTIVITY("Fragment", "androidx.fragment.app.Fragment", "extends Fragment"),
    DIALOG_FRAGMENT("DialogFragment", "androidx.fragment.app.DialogFragment", "extends DialogFragment"),
    APP_COMPAT_DIALOG_FRAGMENT("AppCompatDialogFragment", "androidx.appcompat.app.AppCompatDialogFragment", "extends AppCompatDialogFragment"),
    BOTTOM_SHEET_DIALOG_FRAGMENT("BottomSheetDialogFragment", "com.google.android.material.bottomsheet.BottomSheetDialogFragment", "extends BottomSheetDialogFragment"),
    LIST_FRAGMENT("ListFragment", "androidx.fragment.app.ListFragment", "extends ListFragment"),
    PREFERENCE_FRAGMENT("PreferenceFragment", "androidx.preference.PreferenceFragmentCompat", "extends PreferenceFragmentCompat"),
    WEB_VIEW_ACTIVITY("WebViewActivity", "android.webkit.WebView", "extends Activity"),
    MAP_ACTIVITY("MapActivity", "com.google.android.gms.maps.SupportMapFragment", "implements OnMapReadyCallback"),
    CUSTOM_ACTIVITY("CustomActivity", "", "extends");

    private final String displayName;
    private final String importPath;
    private final String classDeclaration;

    ActivityType(String displayName, String importPath, String classDeclaration) {
        this.displayName = displayName;
        this.importPath = importPath;
        this.classDeclaration = classDeclaration;
    }

    public String getDisplayName() { return displayName; }
    public String getImportPath() { return importPath; }
    public String getClassDeclaration() { return classDeclaration; }

    public boolean isFragment() {
        return this == DIALOG_FRAGMENT
                || this == APP_COMPAT_DIALOG_FRAGMENT
                || this == BOTTOM_SHEET_DIALOG_FRAGMENT
                || this == LIST_FRAGMENT
                || this == FRAGMENT_ACTIVITY
                || this == PREFERENCE_FRAGMENT;
    }

    public boolean isDialogFragment() {
        return this == DIALOG_FRAGMENT
                || this == APP_COMPAT_DIALOG_FRAGMENT
                || this == BOTTOM_SHEET_DIALOG_FRAGMENT;
    }

    public boolean isPlainFragment() {
        return this == FRAGMENT_ACTIVITY;
    }

    /** Class name used in "extends X" for generated source. */
    public String getGeneratedExtends() {
        switch (this) {
            case FRAGMENT_ACTIVITY:            return "Fragment";
            case DIALOG_FRAGMENT:              return "DialogFragment";
            case APP_COMPAT_DIALOG_FRAGMENT:   return "AppCompatDialogFragment";
            case BOTTOM_SHEET_DIALOG_FRAGMENT: return "BottomSheetDialogFragment";
            case LIST_FRAGMENT:                return "ListFragment";
            case PREFERENCE_FRAGMENT:          return "PreferenceFragmentCompat";
            case APP_COMPAT_ACTIVITY:          return "AppCompatActivity";
            case ACTIVITY:                     return "Activity";
            default:                           return "AppCompatActivity";
        }
    }

    /** Fully-qualified import for the base class of generated source. */
    public String getGeneratedImport() {
        switch (this) {
            case FRAGMENT_ACTIVITY:            return "androidx.fragment.app.Fragment";
            case DIALOG_FRAGMENT:              return "androidx.fragment.app.DialogFragment";
            case APP_COMPAT_DIALOG_FRAGMENT:   return "androidx.appcompat.app.AppCompatDialogFragment";
            case BOTTOM_SHEET_DIALOG_FRAGMENT: return "com.google.android.material.bottomsheet.BottomSheetDialogFragment";
            case LIST_FRAGMENT:                return "androidx.fragment.app.ListFragment";
            case PREFERENCE_FRAGMENT:          return "androidx.preference.PreferenceFragmentCompat";
            case APP_COMPAT_ACTIVITY:          return "androidx.appcompat.app.AppCompatActivity";
            case ACTIVITY:                     return "android.app.Activity";
            default:                           return getImportPath();
        }
    }
}
