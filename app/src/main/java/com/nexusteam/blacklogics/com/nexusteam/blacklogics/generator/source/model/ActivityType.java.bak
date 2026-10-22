package com.nexusteam.blacklogics.generator.source.model;

public enum ActivityType {
    ACTIVITY("Activity", "android.app.Activity", "extends Activity"),
    APP_COMPAT_ACTIVITY("AppCompatActivity", "androidx.appcompat.app.AppCompatActivity", "extends AppCompatActivity"),
    FRAGMENT_ACTIVITY("FragmentActivity", "androidx.fragment.app.FragmentActivity", "extends FragmentActivity"),
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
        return this == DIALOG_FRAGMENT || 
               this == APP_COMPAT_DIALOG_FRAGMENT || 
               this == BOTTOM_SHEET_DIALOG_FRAGMENT || 
               this == LIST_FRAGMENT || 
               this == PREFERENCE_FRAGMENT;
    }
    public boolean isDialogFragment() {
        return this == DIALOG_FRAGMENT || 
               this == APP_COMPAT_DIALOG_FRAGMENT || 
               this == BOTTOM_SHEET_DIALOG_FRAGMENT;
    }
}