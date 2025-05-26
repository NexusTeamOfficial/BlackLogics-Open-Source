package com.besome.blacklogics.logic.editor;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import android.graphics.Color;
import android.widget.LinearLayout.LayoutParams;
import java.util.Set;
import java.util.HashSet;
import com.besome.blacklogics.R;

public class PaletteSelector extends LinearLayout implements OnClickListener {

    private Context mContext;
    private OnBlockCategorySelectListener mListener;
    private static final String PALETTE_PATH = "/storage/emulated/0/.blacklogics/resources/block/My Block/palette.json";
    private Set<String> validPaletteIds;

    public PaletteSelector(Context context) {
        super(context);
        init(context);
    }

    public PaletteSelector(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }
    
    public void setValidPaletteIds(Set<String> paletteIds) {
        this.validPaletteIds = paletteIds;
        //Log.d(TAG, "Valid palette IDs set: " + (paletteIds != null ? paletteIds.toString() : "null"));
    }

    private void addCategory() {
        addCategoryItem(0, getResources().getString(R.string.title_bl_cate_var), -1147626);
        addCategoryItem(1, getResources().getString(R.string.title_bl_cate_list), -3384542);
        addCategoryItem(2, getResources().getString(R.string.title_bl_cate_control), -1988310);
        addCategoryItem(3, getResources().getString(R.string.title_bl_cate_operator), -10701022);
        addCategoryItem(4, getResources().getString(R.string.title_bl_cate_function), -11899692);
        addCategoryItem(5, getResources().getString(R.string.title_bl_cate_moreblock), -7711273);

        // Load custom palettes from palette.json
        int index = 6; // Start index for dynamically adding category items
        try {
            // Read the JSON file
            BufferedReader bufferedReader = new BufferedReader(new FileReader(PALETTE_PATH));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();

            // Parse JSON data
            JSONArray jsonArray = new JSONArray(stringBuilder.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String color = jsonObject.getString("color");
                addCategoryItem(index++, name, Color.parseColor(color));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            // Optionally, log the error or show a toast to the user
        }
    }

    private void addCategoryItem(int i, String str, int i2) {
        PaletteSelectorItem paletteSelectorItem = new PaletteSelectorItem(this.mContext, i, str, i2);
        paletteSelectorItem.setOnClickListener(this);
        addView(paletteSelectorItem);
        if (i == 0) {
            paletteSelectorItem.setSelected(true);
        }
    }

    private void clearSelection() {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof PaletteSelectorItem) {
                ((PaletteSelectorItem) childAt).setSelected(false);
            }
        }
    }

    private void init(Context context) {
        this.mContext = context;
        setOrientation(1);
        setLayoutParams(new LayoutParams(-1, -2));
        int dip = (int) LayoutUtil.getDip(context, 8.0f);
        int dip2 = (int) LayoutUtil.getDip(context, 4.0f);
        setPadding(dip, dip2, dip, dip2);
        addCategory();
    }

    public void onClick(View view) {
        if (view instanceof PaletteSelectorItem) {
            clearSelection();
            PaletteSelectorItem paletteSelectorItem = (PaletteSelectorItem) view;
            paletteSelectorItem.setSelected(true);
            this.mListener.onBlockCategorySelect(paletteSelectorItem.getId(), paletteSelectorItem.getColor());
        }
    }

    public void setOnBlockCategorySelectListener(OnBlockCategorySelectListener onBlockCategorySelectListener) {
        this.mListener = onBlockCategorySelectListener;
    }
}