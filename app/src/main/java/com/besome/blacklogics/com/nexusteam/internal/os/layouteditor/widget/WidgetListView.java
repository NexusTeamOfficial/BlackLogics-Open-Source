package com.nexusteam.internal.os.layouteditor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.besome.blacklogics.R;
import com.besome.blacklogics.*;
//import com.nexusteam.internal.os.layouteditor.MainActivity;
import com.nexusteam.internal.os.layouteditor.util.WidgetUtil;
import java.util.ArrayList;

public class WidgetListView extends Widget {
    private ListView mListView;
    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> mItems;
    private Paint selectionPaint;
    private boolean isSelected = false;
    private View.OnClickListener clickListener;

    public WidgetListView(Context context) {
        this(context, null);
    }

    public WidgetListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WidgetListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
    mListView = new ListView(context);
    mListView.setLayoutParams(new ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    mListView.setSelector(new ColorDrawable(Color.TRANSPARENT)); // Click effect remove
    mListView.setEnabled(false); // ListView items click disable
    mListView.setLongClickable(false); // Long press disable
    mListView.setFocusable(false); // Focus remove
    mListView.setFocusableInTouchMode(false);
    mListView.setChoiceMode(ListView.CHOICE_MODE_NONE); // Selection mode none

    mItems = new ArrayList<>();
    mItems.add("List item 1");
    mItems.add("List item 2");
    mItems.add("List item 3");

    mAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, mItems);
    mListView.setAdapter(mAdapter);

    mListView.setDivider(new ColorDrawable(Color.parseColor("#90D7D7")));
    mListView.setDividerHeight(1);

    // WidgetListView ko click karne par event trigger hoga
    this.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            ViewEditorFragmentActivity.selectWidget(v);
            if (clickListener != null) {
                clickListener.onClick(WidgetListView.this);
            }
        }
    });

    addView(mListView);
}


    @Override
    public void setOnClickListener(OnClickListener l) {
        this.clickListener = l;
    }

    // ========================
    // Widget Methods
    // ========================
    /*
    TUDO : NOT NEED
    @Override
    public void select() {
        isSelected = true;
        selectionPaint.setColor(getResources().getColor(R.color.widget_selection_color));
        invalidate();
    }
    

    @Override
    public void unselect() {
        isSelected = false;
        selectionPaint.setColor(0);
        invalidate();
    }
   */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (isSelected) {
            canvas.drawRect(0, 0, getWidth(), getHeight(), selectionPaint);
        }
    }

    public static String newWidgetId() {
        int i = 1;
        while (WidgetUtil.isWidgetIdExist("listview" + i)) {
            i++;
        }
        return "listview" + i;
    }

    // ========================
    // ListView Methods Delegation
    // ========================
    
    // Item management
    public void addItem(String item) {
        mItems.add(item);
        mAdapter.notifyDataSetChanged();
    }

    public void removeItem(int position) {
        if (position >= 0 && position < mItems.size()) {
            mItems.remove(position);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void clearItems() {
        mItems.clear();
        mAdapter.notifyDataSetChanged();
    }

    public ArrayList<String> getItems() {
        return mItems;
    }

    public void setItems(ArrayList<String> items) {
        this.mItems = new ArrayList<>(items);
        mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mItems);
        mListView.setAdapter(mAdapter);
    }

    // Adapter methods
    public void setAdapter(ListAdapter adapter) {
        mListView.setAdapter(adapter);
    }

    public ListAdapter getAdapter() {
        return mListView.getAdapter();
    }

    // Selection methods
    public void setSelection(int position) {
        mListView.setSelection(position);
    }

    public int getSelectedItemPosition() {
        return mListView.getSelectedItemPosition();
    }

    public long getSelectedItemId() {
        return mListView.getSelectedItemId();
    }

    public Object getSelectedItem() {
        return mListView.getSelectedItem();
    }

    // ListView configuration
    public void setChoiceMode(int choiceMode) {
        mListView.setChoiceMode(choiceMode);
    }

    public void setDividerHeight(int height) {
        mListView.setDividerHeight(height);
    }

    public void setItemsCanFocus(boolean itemsCanFocus) {
        mListView.setItemsCanFocus(itemsCanFocus);
    }

    // Layout methods
    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
        if (mListView != null) {
            mListView.setLayoutParams(params);
        }
    }

    // ========================
    // View Methods Overrides
    // ========================
    
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mListView.setEnabled(enabled);
    }
    /**
    TUDO : NOT NEED NOW
    **/

   /* @Override
    public void setOnClickListener(View.OnClickListener l) {
        mListView.setOnClickListener(l);
    }
*/
   // @Override
    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mListView.setOnItemClickListener(listener);
    }
/*
   // @Override
    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener listener) {
        mListView.setOnItemLongClickListener(listener);
    }*/
}