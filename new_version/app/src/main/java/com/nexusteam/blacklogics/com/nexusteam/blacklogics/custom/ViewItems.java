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
    private boolean suppressCallback = false;
    private String currentlySelectedId = null; // Track current selection

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
                // ❌ Programmatic change ignore
                if (suppressCallback) return;

                if (idManager == null || listener == null) return;

                String selectedId = adapter.getItem(position);
                if (selectedId == null) return;

                // Don't trigger if same as currently selected
                if (selectedId.equals(currentlySelectedId)) return;

                View selectedView = idManager.getView(selectedId);
                if (selectedView == null) return;

                // Update tracking
                currentlySelectedId = selectedId;

                // Notify listener
                listener.onViewChanged(selectedView, selectedId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void setIdManager(IdManager manager) {
        this.idManager = manager;
        refreshIds();
    }

    public void setOnViewChangeListener(OnViewChangeListener listener) {
        this.listener = listener;
    }

    public void refreshIds() {
        if (idManager == null) return;

        List<String> ids = idManager.getIds();

        suppressCallback = true;
        adapter.clear();
        adapter.addAll(ids);
        adapter.notifyDataSetChanged();

        // Restore selection if possible
        if (currentlySelectedId != null && ids.contains(currentlySelectedId)) {
            final int index = adapter.getPosition(currentlySelectedId);
            if (index >= 0) {
                spinner.post(new Runnable() {
                    @Override
                    public void run() {
                        spinner.setSelection(index, false);
                    }
                });
            }
        }

        // Re-enable callbacks
        spinner.post(new Runnable() {
            @Override
            public void run() {
                suppressCallback = false;
            }
        });
    }

    public void selectId(String id) {
        selectIdSilently(id);
    }

    public void selectIdSilently(final String id) {
        if (id == null) return;

        final int index = adapter.getPosition(id);
        if (index < 0) return;

        // Update tracking
        currentlySelectedId = id;

        suppressCallback = true;
        spinner.post(new Runnable() {
            @Override
            public void run() {
                spinner.setSelection(index, false);
                suppressCallback = false;
            }
        });
    }

    // Add this method to force a complete refresh
    public void forceRefresh() {
        refreshIds();
    }
}