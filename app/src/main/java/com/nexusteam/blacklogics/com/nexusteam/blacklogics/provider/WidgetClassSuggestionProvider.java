package com.nexusteam.blacklogics.provider;

import java.util.ArrayList;

public final class WidgetClassSuggestionProvider {

    private WidgetClassSuggestionProvider() {

    }

    public static ArrayList<String> provide() {

        ArrayList<String> list = new ArrayList<String>();


        list.add("android.widget.Button");
        list.add("android.widget.TextView");
        list.add("android.widget.EditText");
        list.add("android.widget.ImageView");
        list.add("android.widget.ImageButton");
        list.add("android.widget.CheckBox");
        list.add("android.widget.Switch");
        list.add("android.widget.RadioButton");
        list.add("android.widget.SeekBar");
        list.add("android.widget.ProgressBar");
        list.add("android.widget.ListView");
        list.add("android.widget.ScrollView");
        list.add("android.widget.LinearLayout");
        list.add("android.widget.RelativeLayout");
        list.add("android.widget.FrameLayout");


        list.add("androidx.recyclerview.widget.RecyclerView");
        list.add("androidx.constraintlayout.widget.ConstraintLayout");
        list.add("androidx.appcompat.widget.Toolbar");
        list.add("androidx.core.widget.NestedScrollView");


        list.add("com.google.android.material.button.MaterialButton");
        list.add("com.google.android.material.textview.MaterialTextView");
        list.add("com.google.android.material.textfield.TextInputLayout");
        list.add("com.google.android.material.textfield.TextInputEditText");
        list.add("com.google.android.material.card.MaterialCardView");
        list.add("com.google.android.material.floatingactionbutton.FloatingActionButton");

        return list;
    }
}
