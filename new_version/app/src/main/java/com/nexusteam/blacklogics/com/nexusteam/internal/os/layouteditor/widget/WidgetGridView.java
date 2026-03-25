package com.nexusteam.internal.os.layouteditor.widget;

import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import com.nexusteam.internal.os.layouteditor.util.WidgetUtil;
import java.util.ArrayList;

public class WidgetGridView extends Widget {
    private GridView mGridView;
    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> mItems;

    public WidgetGridView(Context context) {
        super(context);
        mGridView = new GridView(context);
        mGridView.setNumColumns(2); // 2 columns like Sketchware


        mItems = new ArrayList<>();
        mItems.add("Item 1");
        mItems.add("Item 2");
        mItems.add("Item 3");

        mAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, mItems);
        mGridView.setAdapter(mAdapter);

        addView(mGridView);
    }


    public void setLayoutParams(LayoutParams layoutParams) {
        super.setLayoutParams(layoutParams);
        mGridView.setLayoutParams(layoutParams);
    }

    public static String newWidgetId() {
        int i = 1;
        while (WidgetUtil.isWidgetIdExist("gridview" + i)) {
            i++;
        }
        return "gridview" + i;
    }
}
