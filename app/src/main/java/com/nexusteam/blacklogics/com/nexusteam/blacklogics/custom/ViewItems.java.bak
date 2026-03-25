package com.nexusteam.blacklogics.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.shapun.layouteditor.IdManager;
import com.nexusteam.blacklogics.interfaces.OnViewChangeListener;

import java.util.ArrayList;
import java.util.List;

public class ViewItems extends LinearLayout {

    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private IdManager idManager;
    private OnViewChangeListener listener;

    // 🔑 ye flag auto-callback rokne ke liye
    private boolean suppressCallback = false;

    public ViewItems(Context context) {
        super(context);
        init(context);
    }

    public ViewItems(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ViewItems(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);

        spinner = new Spinner(context);
        addView(spinner, new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        ));

        adapter = new ArrayAdapter<String>(
                context,
                android.R.layout.simple_spinner_dropdown_item,
                new ArrayList<String>()
        );

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // ❌ programmatic change ignore
                if (suppressCallback) return;

                // ❌ layout / refresh ke time ignore
                if (!spinner.hasFocus()) return;

                if (idManager == null || listener == null) return;

                String selectedId = adapter.getItem(position);
                if (selectedId == null) return;

                View selectedView = idManager.getView(selectedId);
                if (selectedView == null) return;

                listener.onViewChanged(selectedView, selectedId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    // 🔗 IdManager set
    public void setIdManager(IdManager manager) {
        this.idManager = manager;
        refreshIds();
    }

    // 🔗 Listener set
    public void setOnViewChangeListener(OnViewChangeListener listener) {
        this.listener = listener;
    }

    // 🔄 IDs refresh (NO auto select)
    public void refreshIds() {
        if (idManager == null) return;

        List<String> ids = idManager.getIds();

        suppressCallback = true;
        adapter.clear();
        adapter.addAll(ids);
        adapter.notifyDataSetChanged();

        // spinner ko shant hone ka time do
        spinner.post(new Runnable() {
            @Override
            public void run() {
                suppressCallback = false;
            }
        });
    }

    // ❌ YE USE MAT KARNA (callback fire karta hai)
    
    public void selectId(String id) {
        selectIdSilently(id);
    }
    

    // ✅ SAFE selection (silent)
    public void selectIdSilently(final String id) {
        if (id == null) return;

        final int index = adapter.getPosition(id);
        if (index < 0) return;

        suppressCallback = true;
        spinner.post(new Runnable() {
            @Override
            public void run() {
                spinner.setSelection(index, false);
                suppressCallback = false;
            }
        });
    }
}