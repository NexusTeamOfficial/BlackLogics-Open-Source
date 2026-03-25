package com.nexusteam.internal.editor.logic;

import com.nexusteam.blacklogics.R;
import com.nexusteam.internal.fr;
import com.nexusteam.internal.fs;
import com.nexusteam.internal.kp;
import com.nexusteam.internal.kq;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.graphics.Color;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import org.json.JSONArray;
import org.json.JSONObject;

public class PaletteSelector extends LinearLayout implements View.OnClickListener {

    private Context f1117a;
    private fr b;

    public PaletteSelector(Context context) {
        super(context);
        a(context);
    }

    public PaletteSelector(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    private void a(Context context) {
        this.f1117a = context;
        setOrientation(1);
        setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        int a2 = (int) kp.a(context, 8.0f);
        int a3 = (int) kp.a(context, 4.0f);
        setPadding(a2, a3, a2, a3);
        a();
    }

    private void a() {

        a(0, kq.a().a(getResources(), R.string.block_category_var), -1147626);
        a(1, kq.a().a(getResources(), R.string.block_category_list), -3384542);
        a(2, kq.a().a(getResources(), R.string.block_category_control), -1988310);
        a(3, kq.a().a(getResources(), R.string.block_category_operator), -10701022);
        a(4, kq.a().a(getResources(), R.string.block_category_math), -14435927);
        a(5, kq.a().a(getResources(), R.string.block_category_file), -6190977);
        a(6, kq.a().a(getResources(), R.string.block_category_view_func), -11899692);
        a(7, kq.a().a(getResources(), R.string.block_category_component_func), -13851166);
        a(8, kq.a().a(getResources(), R.string.block_category_moreblock), -7711273);


        loadCustomPalettes();
    }

    private void loadCustomPalettes() {
        String path = "/storage/emulated/0/.blacklogics/resources/block/My Block/palette.json";
        File file = new File(path);
        if (!file.exists()) return;

        try {
            String json = readFileAsString(file);
            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                String name = obj.getString("name");
                String col = obj.getString("color");
                int color = Color.parseColor(col);
                int id = 9 + i;                    // custom IDs start from 9
                a(id, name, color);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String readFileAsString(File file) throws Exception {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    private void a(int i, String str, int i2) {
        fs fsVar = new fs(this.f1117a, i, str, i2);
        fsVar.setTag(String.valueOf(i));
        fsVar.setOnClickListener(this);
        addView(fsVar);
        if (i == 0) {
            fsVar.setSelected(true);
        }
    }

    private void b() {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof fs) {
                ((fs) childAt).setSelected(false);
            }
        }
    }

    public void setOnBlockCategorySelectListener(fr frVar) {
        this.b = frVar;
    }

    public void onClick(View view) {
        if (view instanceof fs) {
            b();
            fs fsVar = (fs) view;
            fsVar.setSelected(true);
            this.b.a(Integer.parseInt((String) fsVar.getTag()), fsVar.getColor());
        }
    }
}