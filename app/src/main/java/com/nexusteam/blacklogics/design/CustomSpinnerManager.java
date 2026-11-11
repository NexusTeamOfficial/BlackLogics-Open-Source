package com.nexusteam.blacklogics.design;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.util.Log;

public class CustomSpinnerManager {
    
    private static final String PREF_NAME = "spinner_state";
    private static final String KEY_XML_POSITION = "saved_xml_position";
    private static final String KEY_JAVA_POSITION = "saved_java_position";
    private static final String KEY_XML_ITEM = "saved_xml_item";
    private static final String KEY_JAVA_ITEM = "saved_java_item";
    private static final String KEY_LAST_TAB = "last_tab_position";
    
    private Context context;
    private SharedPreferences prefs;
    private boolean isUpdatingProgrammatically;
    
    private int savedXmlPosition;
    private int savedJavaPosition;
    private String savedXmlItem;
    private String savedJavaItem;
    
    public CustomSpinnerManager(Context context) {
        this.context = context;
        this.prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.isUpdatingProgrammatically = false;
        loadSavedState();
    }
    
    private void loadSavedState() {
        savedXmlPosition = prefs.getInt(KEY_XML_POSITION, 0);
        savedJavaPosition = prefs.getInt(KEY_JAVA_POSITION, 0);
        savedXmlItem = prefs.getString(KEY_XML_ITEM, "");
        savedJavaItem = prefs.getString(KEY_JAVA_ITEM, "");
    }
    
    public void saveXmlState(int position, String selectedItem) {
        savedXmlPosition = position;
        savedXmlItem = selectedItem;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_XML_POSITION, position);
        editor.putString(KEY_XML_ITEM, selectedItem);
        editor.apply();
    }
    
    public void saveJavaState(int position, String selectedItem) {
        savedJavaPosition = position;
        savedJavaItem = selectedItem;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_JAVA_POSITION, position);
        editor.putString(KEY_JAVA_ITEM, selectedItem);
        editor.apply();
    }
    
    public void saveLastTab(int tabPosition) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_LAST_TAB, tabPosition);
        editor.apply();
    }
    
    public int getLastTab() {
        return prefs.getInt(KEY_LAST_TAB, 0);
    }
    
    public int getSavedXmlPosition() {
        return savedXmlPosition;
    }
    
    public int getSavedJavaPosition() {
        return savedJavaPosition;
    }
    
    public String getSavedXmlItem() {
        return savedXmlItem;
    }
    
    public String getSavedJavaItem() {
        return savedJavaItem;
    }
    
    public void setIsUpdating(boolean isUpdating) {
        this.isUpdatingProgrammatically = isUpdating;
    }
    
    public boolean isUpdating() {
        return isUpdatingProgrammatically;
    }
    
    public void setSpinnerSelection(Spinner spinner, int position, boolean isXmlTab) {
        if (spinner == null || spinner.getAdapter() == null) {
            return;
        }
        
        setIsUpdating(true);
        
        int posToSet = position;
        if (posToSet >= spinner.getAdapter().getCount()) {
            posToSet = 0;
        }
        
        spinner.setSelection(posToSet, false);
        setIsUpdating(false);
    }
    
    public void setSpinnerSelectionByValue(Spinner spinner, String value, boolean isXmlTab) {
        if (spinner == null || spinner.getAdapter() == null || value == null || value.isEmpty()) {
            return;
        }
        
        ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            String item = adapter.getItem(i).toString();
            if (item.equals(value)) {
                setIsUpdating(true);
                spinner.setSelection(i, false);
                setIsUpdating(false);
                
                if (isXmlTab) {
                    saveXmlState(i, value);
                } else {
                    saveJavaState(i, value);
                }
                break;
            }
        }
    }
    
    public void clearState() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
        savedXmlPosition = 0;
        savedJavaPosition = 0;
        savedXmlItem = "";
        savedJavaItem = "";
    }
}