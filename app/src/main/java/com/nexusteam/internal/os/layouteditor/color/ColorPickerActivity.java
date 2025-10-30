package com.nexusteam.internal.os.layouteditor.color;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import androidx.core.graphics.ColorUtils;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.besome.blacklogics.*;

public class ColorPickerActivity extends AppCompatActivity {
    private Button btn_add;
    private Button btn_pick;
    /* access modifiers changed from: private */
    public ArrayList<HashMap<String, Object>> customColorsListMap = new ArrayList<>();
    private HashMap<String, Object> customColorsMap = new HashMap<>();
    /* access modifiers changed from: private */
    public EditText edit;
    private String fontName = "";
    private LinearLayout lin_main;
    /* access modifiers changed from: private */
    public LinearLayout lin_preview;
    private LinearLayout linear3;
    private LinearLayout linear4;
    private ArrayList<String> lsit = new ArrayList<>();
    private ListView lv_primary_colors;
    /* access modifiers changed from: private */
    public ListView lv_sub_colors;
    private boolean pipEnabled = false;
    /* access modifiers changed from: private */
    public ArrayList<HashMap<String, Object>> primaryColorsMap = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> recentColorsListMap = new ArrayList<>();
    /* access modifiers changed from: private */
    public SharedPreferences savedColors;
    /* access modifiers changed from: private */
    public double themeColor = 0.0d;
    private SharedPreferences themeColors;
    private double themeTextColor = 0.0d;
    private TextView tv_hash;
    private String typeace = "";
    private AlertDialog.Builder xbx;

    public interface ColorObservable {
        int getColor();

        void subscribe(ColorObserver colorObserver);

        void unsubscribe(ColorObserver colorObserver);
    }

    public interface ColorObserver {
        void onColor(int i, boolean z);
    }

    public static class Constants {
        static final int EVENT_MIN_INTERVAL = 16;
        static final int SELECTOR_RADIUS_DP = 9;
    }

    public interface Updatable {
        void update(MotionEvent motionEvent);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.color_picker);
        initialize(bundle);
        initializeLogic();
    }

    private void initialize(Bundle bundle) {
        this.lin_main = (LinearLayout) findViewById(R.id.lin_main);
        this.linear4 = (LinearLayout) findViewById(R.id.linear4);
        this.linear3 = (LinearLayout) findViewById(R.id.linear3);
        this.tv_hash = (TextView) findViewById(R.id.tv_hash);
        this.edit = (EditText) findViewById(R.id.edit);
        this.lin_preview = (LinearLayout) findViewById(R.id.lin_preview);
        this.btn_pick = (Button) findViewById(R.id.btn_pick);
        this.btn_add = (Button) findViewById(R.id.btn_add);
        this.lv_primary_colors = (ListView) findViewById(R.id.lv_primary_colors);
        this.lv_sub_colors = (ListView) findViewById(R.id.lv_sub_colors);
        this.savedColors = getSharedPreferences("savedColors", 0);
        this.themeColors = getSharedPreferences("themeColors", 0);
        this.xbx = new AlertDialog.Builder(this);
        
        this.edit.setFocusable(true);
        this.edit.setFocusableInTouchMode(true);
        this.edit.setClickable(true);
        
        
        this.edit.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                String charSequence2 = charSequence.toString();
                if (charSequence2.equals("")) {
                    ColorPickerActivity.this.edit.setError((CharSequence) null);
                    ColorPickerActivity.this.lin_preview.setBackgroundColor(0);
                    return;
                }
                try {
                    ColorPickerActivity.this.lin_preview.setBackgroundColor(Color.parseColor("#" + charSequence2));
                    ColorPickerActivity.this.edit.setError((CharSequence) null);
                } catch (Exception e) {
                    ColorPickerActivity.this.edit.setError("Invalid Hex Color");
                    ColorPickerActivity.this.lin_preview.setBackgroundColor(0);
                }
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
            }
        });
        this.btn_pick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new ColorPickerPopup.Builder(ColorPickerActivity.this).initialColor(Color.parseColor("#ffffff")).enableAlpha(true).okTitle("Select").showIndicator(true).showValue(true).themeColor((int) ColorPickerActivity.this.themeColor).build().show(new ColorPickerPopup.ColorPickerObserver() {
                    public void onColorPicked(int i) {
                        int alpha = Color.alpha(i);
                        int red = Color.red(i);
                        int green = Color.green(i);
                        int blue = Color.blue(i);
                        ColorPickerActivity.this._returnColor(String.format(Locale.getDefault(), "#%02X%02X%02X%02X", new Object[]{Integer.valueOf(alpha), Integer.valueOf(red), Integer.valueOf(green), Integer.valueOf(blue)}));
                    }

                    public void onCustomClicked() {
                    }

                    public void onColor(int i, boolean z) {
                    }
                });
            }
        });
        this.btn_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (ColorPickerActivity.this.edit.getError() == null) {
                    String upperCase = ("#" + ColorPickerActivity.this.edit.getText().toString()).toUpperCase();
                    if (upperCase.length() == 7) {
                        upperCase = upperCase.replace("#", "#FF");
                    }
                    if (ColorPickerActivity.this.customColorsListMap == null) {
                        ColorPickerActivity.this.customColorsListMap = new ArrayList();
                    }
                    HashMap hashMap = new HashMap();
                    hashMap.put("hexCode", upperCase);
                    if (ColorUtils.calculateLuminance(Color.parseColor(upperCase)) < 0.5d) {
                        hashMap.put("isDark", "true");
                    } else {
                        hashMap.put("isDark", "false");
                    }
                    ColorPickerActivity.this.customColorsListMap.remove(hashMap);
                    ColorPickerActivity.this.customColorsListMap.add(0, hashMap);
                    ColorPickerActivity.this.savedColors.edit().putString("customColors", new Gson().toJson((Object) ColorPickerActivity.this.customColorsListMap)).commit();
                    ColorPickerActivity.this._returnColor(upperCase);
                    return;
                }
                SketchwareUtil.showMessage(ColorPickerActivity.this.getApplicationContext(), "Unknown color");
            }
        });
        this.lv_primary_colors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                ColorPickerActivity.this.lv_sub_colors.setAdapter(new Lv_sub_colorsAdapter((ArrayList) ((HashMap) ColorPickerActivity.this.primaryColorsMap.get(i)).get("listMap")));
                ((BaseAdapter) ColorPickerActivity.this.lv_sub_colors.getAdapter()).notifyDataSetChanged();
            }
        });
    }

    private void initializeLogic() {
        _changeActivityFont("myfont");
        this.pipEnabled = getIntent().getBooleanExtra("pip", false);
        getWindow().getDecorView().setBackgroundResource(17170445);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(-1);
        gradientDrawable.setCornerRadius((float) (((int) getApplicationContext().getResources().getDisplayMetrics().density) * 15));
        this.lin_main.setBackground(gradientDrawable);
        try {
            this.recentColorsListMap = (ArrayList) new Gson().fromJson(this.savedColors.getString("recentColors", ""), new TypeToken<ArrayList<HashMap<String, Object>>>() {
            }.getType());
            HashMap hashMap = new HashMap();
            hashMap.put("name", "Recent");
            hashMap.put("hexCode", this.recentColorsListMap.get(0).get("hexCode").toString());
            hashMap.put("listMap", this.recentColorsListMap);
            this.primaryColorsMap.add(hashMap);
        } catch (Exception e) {
            this.recentColorsListMap = new ArrayList<>();
        }
        try {
            this.customColorsListMap = (ArrayList) new Gson().fromJson(this.savedColors.getString("customColors", ""), new TypeToken<ArrayList<HashMap<String, Object>>>() {
            }.getType());
            HashMap hashMap2 = new HashMap();
            hashMap2.put("name", TypedValues.Custom.NAME);
            hashMap2.put("hexCode", this.customColorsListMap.get(0).get("hexCode").toString());
            hashMap2.put("listMap", this.customColorsListMap);
            this.primaryColorsMap.add(hashMap2);
        } catch (Exception e2) {
            this.customColorsListMap = new ArrayList<>();
        }
        try {
            this.themeColor = Double.parseDouble(this.themeColors.getString("themeColor", ""));
        } catch (Exception e3) {
            this.themeColor = -1.6740915E7d;
        }
        try {
            this.themeTextColor = Double.parseDouble(this.themeColors.getString("themeTextColor", ""));
        } catch (Exception e4) {
            this.themeTextColor = -1.6740915E7d;
        }
        this.tv_hash.setTextColor((int) this.themeTextColor);
        this.edit.setTextColor((int) this.themeTextColor);
        this.edit.getBackground().mutate().setColorFilter((int) this.themeColor, PorterDuff.Mode.SRC_ATOP);
        this.edit.setHintTextColor((int) this.themeTextColor);
        GradientDrawable gradientDrawable2 = new GradientDrawable();
        gradientDrawable2.setColor((int) this.themeColor);
        gradientDrawable2.setCornerRadius((float) (((int) getApplicationContext().getResources().getDisplayMetrics().density) * 5));
        this.btn_pick.setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{-1}), gradientDrawable2, (Drawable) null));
        GradientDrawable gradientDrawable3 = new GradientDrawable();
        gradientDrawable3.setColor((int) this.themeColor);
        gradientDrawable3.setCornerRadius((float) (((int) getApplicationContext().getResources().getDisplayMetrics().density) * 5));
        this.btn_add.setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{-1}), gradientDrawable3, (Drawable) null));
        _addColor("White", "{ \"500\": \"#ffffff\"} ");
        _addColor("Black", "{ \"500\": \"#000000\"} ");
        _addColor("Red", "{ \"50\": \"#ffebee\", \"100\": \"#ffcdd2\", \"200\": \"#ef9a9a\", \"300\": \"#e57373\", \"400\": \"#ef5350\", \"500\": \"#f44336\", \"600\": \"#e53935\", \"700\": \"#d32f2f\", \"800\": \"#c62828\", \"900\": \"#b71c1c\", \"a100\": \"#ff8a80\", \"a200\": \"#ff5252\", \"a400\": \"#ff1744\", \"a700\": \"#d50000\" }");
        _addColor("Pink", "{ \"50\": \"#fce4ec\", \"100\": \"#f8bbd0\", \"200\": \"#f48fb1\", \"300\": \"#f06292\", \"400\": \"#ec407a\", \"500\": \"#e91e63\", \"600\": \"#d81b60\", \"700\": \"#c2185b\", \"800\": \"#ad1457\", \"900\": \"#880e4f\", \"a100\": \"#ff80ab\", \"a200\": \"#ff4081\", \"a400\": \"#f50057\", \"a700\": \"#c51162\" }");
        _addColor("Purple", "{ \"50\": \"#f3e5f5\", \"100\": \"#e1bee7\", \"200\": \"#ce93d8\", \"300\": \"#ba68c8\", \"400\": \"#ab47bc\", \"500\": \"#9c27b0\", \"600\": \"#8e24aa\", \"700\": \"#7b1fa2\", \"800\": \"#6a1b9a\", \"900\": \"#4a148c\", \"a100\": \"#ea80fc\", \"a200\": \"#e040fb\", \"a400\": \"#d500f9\", \"a700\": \"#aa00ff\" }");
        _addColor("Deep Purple", "{ \"50\": \"#ede7f6\", \"100\": \"#d1c4e9\", \"200\": \"#b39ddb\", \"300\": \"#9575cd\", \"400\": \"#7e57c2\", \"500\": \"#673ab7\", \"600\": \"#5e35b1\", \"700\": \"#512da8\", \"800\": \"#4527a0\", \"900\": \"#311b92\", \"a100\": \"#b388ff\", \"a200\": \"#7c4dff\", \"a400\": \"#651fff\", \"a700\": \"#6200ea\" }");
        _addColor("Indigo", "{ \"50\": \"#e8eaf6\", \"100\": \"#c5cae9\", \"200\": \"#9fa8da\", \"300\": \"#7986cb\", \"400\": \"#5c6bc0\", \"500\": \"#3f51b5\", \"600\": \"#3949ab\", \"700\": \"#303f9f\", \"800\": \"#283593\", \"900\": \"#1a237e\", \"a100\": \"#8c9eff\", \"a200\": \"#536dfe\", \"a400\": \"#3d5afe\", \"a700\": \"#304ffe\" }");
        _addColor("Blue", "{ \"50\": \"#e3f2fd\", \"100\": \"#bbdefb\", \"200\": \"#90caf9\", \"300\": \"#64b5f6\", \"400\": \"#42a5f5\", \"500\": \"#2196f3\", \"600\": \"#1e88e5\", \"700\": \"#1976d2\", \"800\": \"#1565c0\", \"900\": \"#0d47a1\", \"a100\": \"#82b1ff\", \"a200\": \"#448aff\", \"a400\": \"#2979ff\", \"a700\": \"#2962ff\" }");
        _addColor("Light Blue", "{ \"50\": \"#e1f5fe\", \"100\": \"#b3e5fc\", \"200\": \"#81d4fa\", \"300\": \"#4fc3f7\", \"400\": \"#29b6f6\", \"500\": \"#03a9f4\", \"600\": \"#039be5\", \"700\": \"#0288d1\", \"800\": \"#0277bd\", \"900\": \"#01579b\", \"a100\": \"#80d8ff\", \"a200\": \"#40c4ff\", \"a400\": \"#00b0ff\", \"a700\": \"#0091ea\" }");
        _addColor("Cyan", "{ \"50\": \"#e0f7fa\", \"100\": \"#b2ebf2\", \"200\": \"#80deea\", \"300\": \"#4dd0e1\", \"400\": \"#26c6da\", \"500\": \"#00bcd4\", \"600\": \"#00acc1\", \"700\": \"#0097a7\", \"800\": \"#00838f\", \"900\": \"#006064\", \"a100\": \"#84ffff\", \"a200\": \"#18ffff\", \"a400\": \"#00e5ff\", \"a700\": \"#00b8d4\" }");
        _addColor("Teal", "{ \"50\": \"#e0f2f1\", \"100\": \"#b2dfdb\", \"200\": \"#80cbc4\", \"300\": \"#4db6ac\", \"400\": \"#26a69a\", \"500\": \"#009688\", \"600\": \"#00897b\", \"700\": \"#00796b\", \"800\": \"#00695c\", \"900\": \"#004d40\", \"a100\": \"#a7ffeb\", \"a200\": \"#64ffda\", \"a400\": \"#1de9b6\", \"a700\": \"#00bfa5\" }");
        _addColor("Green", "{ \"50\": \"#e8f5e9\", \"100\": \"#c8e6c9\", \"200\": \"#a5d6a7\", \"300\": \"#81c784\", \"400\": \"#66bb6a\", \"500\": \"#4caf50\", \"600\": \"#43a047\", \"700\": \"#388e3c\", \"800\": \"#2e7d32\", \"900\": \"#1b5e20\", \"a100\": \"#b9f6ca\", \"a200\": \"#69f0ae\", \"a400\": \"#00e676\", \"a700\": \"#00c853\" }");
        _addColor("Light Green", "{ \"50\": \"#f1f8e9\", \"100\": \"#dcedc8\", \"200\": \"#c5e1a5\", \"300\": \"#aed581\", \"400\": \"#9ccc65\", \"500\": \"#8bc34a\", \"600\": \"#7cb342\", \"700\": \"#689f38\", \"800\": \"#558b2f\", \"900\": \"#33691e\", \"a100\": \"#ccff90\", \"a200\": \"#b2ff59\", \"a400\": \"#76ff03\", \"a700\": \"#64dd17\" }");
        _addColor("Lime", "{\n    \"50\": \"#f9fbe7\",\n    \"100\": \"#f0f4c3\",\n    \"200\": \"#e6ee9c\",\n    \"300\": \"#dce775\",\n    \"400\": \"#d4e157\",\n    \"500\": \"#cddc39\",\n    \"600\": \"#c0ca33\",\n    \"700\": \"#afb42b\",\n    \"800\": \"#9e9d24\",\n    \"900\": \"#827717\",\n    \"a100\": \"#f4ff81\",\n    \"a200\": \"#eeff41\",\n    \"a400\": \"#c6ff00\",\n    \"a700\": \"#aeea00\"\n  }");
        _addColor("Yellow", "{\n    \"50\": \"#fffde7\",\n    \"100\": \"#fff9c4\",\n    \"200\": \"#fff59d\",\n    \"300\": \"#fff176\",\n    \"400\": \"#ffee58\",\n    \"500\": \"#ffeb3b\",\n    \"600\": \"#fdd835\",\n    \"700\": \"#fbc02d\",\n    \"800\": \"#f9a825\",\n    \"900\": \"#f57f17\",\n    \"a100\": \"#ffff8d\",\n    \"a200\": \"#ffff00\",\n    \"a400\": \"#ffea00\",\n    \"a700\": \"#ffd600\"\n  }");
        _addColor("Amber", " {\n    \"50\": \"#fff8e1\",\n    \"100\": \"#ffecb3\",\n    \"200\": \"#ffe082\",\n    \"300\": \"#ffd54f\",\n    \"400\": \"#ffca28\",\n    \"500\": \"#ffc107\",\n    \"600\": \"#ffb300\",\n    \"700\": \"#ffa000\",\n    \"800\": \"#ff8f00\",\n    \"900\": \"#ff6f00\",\n    \"a100\": \"#ffe57f\",\n    \"a200\": \"#ffd740\",\n    \"a400\": \"#ffc400\",\n    \"a700\": \"#ffab00\"\n  }");
        _addColor("Orange", "{\n    \"50\": \"#fff3e0\",\n    \"100\": \"#ffe0b2\",\n    \"200\": \"#ffcc80\",\n    \"300\": \"#ffb74d\",\n    \"400\": \"#ffa726\",\n    \"500\": \"#ff9800\",\n    \"600\": \"#fb8c00\",\n    \"700\": \"#f57c00\",\n    \"800\": \"#ef6c00\",\n    \"900\": \"#e65100\",\n    \"a100\": \"#ffd180\",\n    \"a200\": \"#ffab40\",\n    \"a400\": \"#ff9100\",\n    \"a700\": \"#ff6d00\"\n  }");
        _addColor("Deep Orange", "{\n    \"50\": \"#fbe9e7\",\n    \"100\": \"#ffccbc\",\n    \"200\": \"#ffab91\",\n    \"300\": \"#ff8a65\",\n    \"400\": \"#ff7043\",\n    \"500\": \"#ff5722\",\n    \"600\": \"#f4511e\",\n    \"700\": \"#e64a19\",\n    \"800\": \"#d84315\",\n    \"900\": \"#bf360c\",\n    \"a100\": \"#ff9e80\",\n    \"a200\": \"#ff6e40\",\n    \"a400\": \"#ff3d00\",\n    \"a700\": \"#dd2c00\"\n  }");
        _addColor("Brown", " {\n    \"50\": \"#efebe9\",\n    \"100\": \"#d7ccc8\",\n    \"200\": \"#bcaaa4\",\n    \"300\": \"#a1887f\",\n    \"400\": \"#8d6e63\",\n    \"500\": \"#795548\",\n    \"600\": \"#6d4c41\",\n    \"700\": \"#5d4037\",\n    \"800\": \"#4e342e\",\n    \"900\": \"#3e2723\"\n  }");
        _addColor("Grey", " {\n    \"50\": \"#fafafa\",\n    \"100\": \"#f5f5f5\",\n    \"200\": \"#eeeeee\",\n    \"300\": \"#e0e0e0\",\n    \"400\": \"#bdbdbd\",\n    \"500\": \"#9e9e9e\",\n    \"600\": \"#757575\",\n    \"700\": \"#616161\",\n    \"800\": \"#424242\",\n    \"900\": \"#212121\"\n  }");
        _addColor("Blue Grey", "{\n    \"50\": \"#eceff1\",\n    \"100\": \"#cfd8dc\",\n    \"200\": \"#b0bec5\",\n    \"300\": \"#90a4ae\",\n    \"400\": \"#78909c\",\n    \"500\": \"#607d8b\",\n    \"600\": \"#546e7a\",\n    \"700\": \"#455a64\",\n    \"800\": \"#37474f\",\n    \"900\": \"#263238\"\n  }");
        this.lv_primary_colors.setAdapter(new Lv_primary_colorsAdapter(this.primaryColorsMap));
        ((BaseAdapter) this.lv_primary_colors.getAdapter()).notifyDataSetChanged();
        this.edit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
        this.lv_primary_colors.performItemClick(this.lv_primary_colors.getAdapter().getView(0, (View) null, (ViewGroup) null), 0, this.lv_primary_colors.getAdapter().getItemId(0));
    }

    public void onBackPressed() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("data"));
        finish();
    }

    public void _addColor(String str, String str2) {
        try {
            HashMap hashMap = new HashMap();
            new HashMap();
            HashMap hashMap2 = (HashMap) new Gson().fromJson(str2, new TypeToken<HashMap<String, Object>>() {
            }.getType());
            hashMap.put("name", str);
            if (hashMap2.containsKey("500")) {
                hashMap.put("hexCode", hashMap2.get("500").toString().toUpperCase());
            } else {
                hashMap.put("hexCode", ((String) new ArrayList(hashMap2.values()).get(0)).toUpperCase());
            }
            ArrayList arrayList = new ArrayList(hashMap2.values());
            ArrayList arrayList2 = new ArrayList();
            if (arrayList.size() != 1) {
                arrayList.add(0, hashMap2.get("500").toString());
            }
            int i = 0;
            int i2 = 0;
            while (i < arrayList.size()) {
                HashMap hashMap3 = new HashMap();
                hashMap3.put("hexCode", ((String) arrayList.get(i2)).toUpperCase());
                if (ColorUtils.calculateLuminance(Color.parseColor((String) arrayList.get(i2))) < 0.5d) {
                    hashMap3.put("isDark", "true");
                } else {
                    hashMap3.put("isDark", "false");
                }
                arrayList2.add(hashMap3);
                i++;
                i2++;
            }
            hashMap.put("listMap", arrayList2);
            this.primaryColorsMap.add(hashMap);
        } catch (Exception e) {
            showMessage(e.toString());
        }
    }

    public void _returnColor(String str) {
        if (str.equals("NONE")) {
            Intent intent = new Intent();
            intent.putExtra(TypedValues.Custom.S_COLOR, str);
            setResult(-1, intent);
            finish();
            return;
        }
        String upperCase = str.toUpperCase();
        if (str.length() == 7) {
            upperCase = upperCase.replace("#", "#FF");
        }
        if (this.recentColorsListMap == null) {
            this.recentColorsListMap = new ArrayList<>();
        }
        HashMap hashMap = new HashMap();
        hashMap.put("hexCode", upperCase);
        if (ColorUtils.calculateLuminance(Color.parseColor(upperCase)) < 0.5d) {
            hashMap.put("isDark", "true");
        } else {
            hashMap.put("isDark", "false");
        }
        this.recentColorsListMap.remove(hashMap);
        this.recentColorsListMap.add(0, hashMap);
        this.savedColors.edit().putString("recentColors", new Gson().toJson((Object) this.recentColorsListMap)).commit();
        Intent intent2 = new Intent("data");
        intent2.putExtra(TypedValues.Custom.S_COLOR, upperCase);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent2);
        finish();
    }

    public void _extra() {
    }

    public void setContentView(int i) {
        Class cls = null;
        supportRequestWindowFeature(1);
        setTheme(2131689996);
        try {
            Method declaredMethod = Activity.class.getDeclaredMethod("getActivityOptions", new Class[0]);
            declaredMethod.setAccessible(true);
            Object invoke = declaredMethod.invoke(this, new Object[0]);
            Class[] declaredClasses = Activity.class.getDeclaredClasses();
            int length = declaredClasses.length;
            int i2 = 0;
            while (i2 < length) {
                Class cls2 = declaredClasses[i2];
                if (!cls2.getSimpleName().contains("TranslucentConversionListener")) {
                    cls2 = cls;
                }
                i2++;
                cls = cls2;
            }
            Method declaredMethod2 = Activity.class.getDeclaredMethod("convertToTranslucent", new Class[]{cls, ActivityOptions.class});
            declaredMethod2.setAccessible(true);
            declaredMethod2.invoke(this, new Object[]{null, invoke});
        } catch (Throwable th) {
        }
        super.setContentView(i);
    }

    public void _colorPickerPopup() {
    }

    public static class ColorPickerView extends LinearLayout implements ColorObservable {
        private AlphaSliderView alphaSliderView;
        private BrightnessSliderView brightnessSliderView;
        private ColorWheelView colorWheelView;
        private int initialColor;
        private ColorObservable observableOnDuty;
        List<ColorObserver> observers;
        private int sliderHeight;
        private int sliderMargin;

        public ColorPickerView(Context context) {
            this(context, (AttributeSet) null);
        }

        public ColorPickerView(Context context, AttributeSet attributeSet) {
            this(context, attributeSet, 0);
        }

        public ColorPickerView(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
            this.initialColor = -1;
            this.observers = new ArrayList();
            setOrientation(1);
            this.colorWheelView = new ColorWheelView(context);
            float f = getResources().getDisplayMetrics().density;
            int i2 = (int) (8.0f * f);
            this.sliderMargin = i2 * 2;
            this.sliderHeight = (int) (f * 24.0f);
            addView(this.colorWheelView, new LinearLayout.LayoutParams(-2, -2));
            setEnabledBrightness(true);
            setEnabledAlpha(true);
            setPadding(i2, 0, i2, i2);
        }

        /* access modifiers changed from: protected */
        public void onMeasure(int i, int i2) {
            int size = View.MeasureSpec.getSize(i);
            int size2 = (View.MeasureSpec.getSize(i2) - (getPaddingTop() + getPaddingBottom())) + getPaddingLeft() + getPaddingRight();
            if (this.brightnessSliderView != null) {
                size2 -= this.sliderMargin + this.sliderHeight;
            }
            if (this.alphaSliderView != null) {
                size2 -= this.sliderMargin + this.sliderHeight;
            }
            int min = Math.min(size, size2);
            int paddingLeft = (min - (getPaddingLeft() + getPaddingRight())) + getPaddingTop() + getPaddingBottom();
            if (this.brightnessSliderView != null) {
                paddingLeft += this.sliderMargin + this.sliderHeight;
            }
            if (this.alphaSliderView != null) {
                paddingLeft += this.sliderMargin + this.sliderHeight;
            }
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(min, View.MeasureSpec.getMode(i)), View.MeasureSpec.makeMeasureSpec(paddingLeft, View.MeasureSpec.getMode(i2)));
        }

        public void setInitialColor(int i) {
            this.initialColor = i;
            this.colorWheelView.setColor(i);
        }

        public void setEnabledBrightness(boolean z) {
            if (z) {
                if (this.brightnessSliderView == null) {
                    this.brightnessSliderView = new BrightnessSliderView(getContext());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, this.sliderHeight);
                    layoutParams.topMargin = this.sliderMargin;
                    addView(this.brightnessSliderView, 1, layoutParams);
                }
                this.brightnessSliderView.bind(this.colorWheelView);
                updateObservableOnDuty();
            } else {
                if (this.brightnessSliderView != null) {
                    this.brightnessSliderView.unbind();
                    removeView(this.brightnessSliderView);
                    this.brightnessSliderView = null;
                }
                updateObservableOnDuty();
            }
            if (this.alphaSliderView != null) {
                setEnabledAlpha(true);
            }
        }

        public void setEnabledAlpha(boolean z) {
            if (z) {
                if (this.alphaSliderView == null) {
                    this.alphaSliderView = new AlphaSliderView(getContext());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                    layoutParams.topMargin = this.sliderMargin;
                    addView(this.alphaSliderView, layoutParams);
                }
                ColorObservable colorObservable = this.brightnessSliderView;
                if (colorObservable == null) {
                    colorObservable = this.colorWheelView;
                }
                this.alphaSliderView.bind(colorObservable);
                updateObservableOnDuty();
                return;
            }
            if (this.alphaSliderView != null) {
                this.alphaSliderView.unbind();
                removeView(this.alphaSliderView);
                this.alphaSliderView = null;
            }
            updateObservableOnDuty();
        }

        private void updateObservableOnDuty() {
            if (this.observableOnDuty != null) {
                for (ColorObserver unsubscribe : this.observers) {
                    this.observableOnDuty.unsubscribe(unsubscribe);
                }
            }
            if (this.brightnessSliderView == null && this.alphaSliderView == null) {
                this.observableOnDuty = this.colorWheelView;
            } else if (this.alphaSliderView != null) {
                this.observableOnDuty = this.alphaSliderView;
            } else {
                this.observableOnDuty = this.brightnessSliderView;
            }
            if (this.observers != null) {
                for (ColorObserver next : this.observers) {
                    this.observableOnDuty.subscribe(next);
                    next.onColor(this.observableOnDuty.getColor(), false);
                }
            }
        }

        public void reset() {
            this.colorWheelView.setColor(this.initialColor);
        }

        public void subscribe(ColorObserver colorObserver) {
            this.observableOnDuty.subscribe(colorObserver);
            this.observers.add(colorObserver);
        }

        public void unsubscribe(ColorObserver colorObserver) {
            this.observableOnDuty.unsubscribe(colorObserver);
            this.observers.remove(colorObserver);
        }

        public int getColor() {
            return this.observableOnDuty.getColor();
        }
    }

    public static class ThrottledTouchEventHandler {
        private long lastPassedEventTime;
        private int minInterval;
        private Updatable updatable;

        ThrottledTouchEventHandler(Updatable updatable2) {
            this(16, updatable2);
        }

        ThrottledTouchEventHandler(int i, Updatable updatable2) {
            this.minInterval = 16;
            this.lastPassedEventTime = 0;
            this.minInterval = i;
            this.updatable = updatable2;
        }

        /* access modifiers changed from: package-private */
        public void onTouchEvent(MotionEvent motionEvent) {
            if (this.updatable != null) {
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - this.lastPassedEventTime > ((long) this.minInterval)) {
                    this.lastPassedEventTime = currentTimeMillis;
                    this.updatable.update(motionEvent);
                }
            }
        }
    }

    public static class ColorWheelSelector extends View {
        private PointF currentPoint;
        private Paint selectorPaint;
        private float selectorRadiusPx;

        public ColorWheelSelector(Context context) {
            this(context, (AttributeSet) null);
        }

        public ColorWheelSelector(Context context, AttributeSet attributeSet) {
            this(context, attributeSet, 0);
        }

        public ColorWheelSelector(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
            this.selectorRadiusPx = 27.0f;
            this.currentPoint = new PointF();
            this.selectorPaint = new Paint(1);
            this.selectorPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
            this.selectorPaint.setStyle(Paint.Style.STROKE);
            this.selectorPaint.setStrokeWidth(2.0f);
        }

        /* access modifiers changed from: protected */
        public void onDraw(Canvas canvas) {
            Canvas canvas2 = canvas;
            canvas2.drawLine(this.currentPoint.x - this.selectorRadiusPx, this.currentPoint.y, this.selectorRadiusPx + this.currentPoint.x, this.currentPoint.y, this.selectorPaint);
            Canvas canvas3 = canvas;
            canvas3.drawLine(this.currentPoint.x, this.currentPoint.y - this.selectorRadiusPx, this.currentPoint.x, this.selectorRadiusPx + this.currentPoint.y, this.selectorPaint);
            canvas.drawCircle(this.currentPoint.x, this.currentPoint.y, this.selectorRadiusPx * 0.66f, this.selectorPaint);
        }

        public void setSelectorRadiusPx(float f) {
            this.selectorRadiusPx = f;
        }

        public void setCurrentPoint(PointF pointF) {
            this.currentPoint = pointF;
            invalidate();
        }
    }

    public static class ColorWheelPalette extends View {
        private float centerX;
        private float centerY;
        private Paint huePaint;
        private float radius;
        private Paint saturationPaint;

        public ColorWheelPalette(Context context) {
            this(context, (AttributeSet) null);
        }

        public ColorWheelPalette(Context context, AttributeSet attributeSet) {
            this(context, attributeSet, 0);
        }

        public ColorWheelPalette(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
            this.huePaint = new Paint(1);
            this.saturationPaint = new Paint(1);
        }

        /* access modifiers changed from: protected */
        public void onSizeChanged(int i, int i2, int i3, int i4) {
            this.radius = ((float) Math.min((i - getPaddingLeft()) - getPaddingRight(), (i2 - getPaddingTop()) - getPaddingBottom())) * 0.5f;
            if (this.radius >= 0.0f) {
                this.centerX = ((float) i) * 0.5f;
                this.centerY = ((float) i2) * 0.5f;
                this.huePaint.setShader(new SweepGradient(this.centerX, this.centerY, new int[]{SupportMenu.CATEGORY_MASK, -65281, -16776961, -16711681, -16711936, InputDeviceCompat.SOURCE_ANY, SupportMenu.CATEGORY_MASK}, (float[]) null));
                this.saturationPaint.setShader(new RadialGradient(this.centerX, this.centerY, this.radius, -1, ViewCompat.MEASURED_SIZE_MASK, Shader.TileMode.CLAMP));
            }
        }

        /* access modifiers changed from: protected */
        public void onDraw(Canvas canvas) {
            canvas.drawCircle(this.centerX, this.centerY, this.radius, this.huePaint);
            canvas.drawCircle(this.centerX, this.centerY, this.radius, this.saturationPaint);
        }
    }

    public static abstract class ColorSliderView extends View implements ColorObservable, Updatable {
        protected int baseColor;
        private ColorObserver bindObserver;
        private Paint borderPaint;
        private ColorObservable boundObservable;
        private Paint colorPaint;
        private Path currentSelectorPath;
        protected float currentValue;
        private ColorObservableEmitter emitter;
        private ThrottledTouchEventHandler handler;
        private Paint selectorPaint;
        private Path selectorPath;
        protected float selectorSize;

        /* access modifiers changed from: protected */
        public abstract int assembleColor();

        /* access modifiers changed from: protected */
        public abstract void configurePaint(Paint paint);

        /* access modifiers changed from: protected */
        public abstract float resolveValue(int i);

        public ColorSliderView(Context context) {
            this(context, (AttributeSet) null);
        }

        public ColorSliderView(Context context, AttributeSet attributeSet) {
            this(context, attributeSet, 0);
        }

        public ColorSliderView(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
            this.baseColor = -1;
            this.currentSelectorPath = new Path();
            this.currentValue = 1.0f;
            this.emitter = new ColorObservableEmitter();
            this.handler = new ThrottledTouchEventHandler(this);
            this.bindObserver = new ColorObserver() {
                public void onColor(int i, boolean z) {
                    ColorSliderView.this.setBaseColor(i, z);
                }
            };
            this.colorPaint = new Paint(1);
            this.borderPaint = new Paint(1);
            this.borderPaint.setStyle(Paint.Style.STROKE);
            this.borderPaint.setStrokeWidth(0.0f);
            this.borderPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
            this.selectorPaint = new Paint(1);
            this.selectorPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
            this.selectorPath = new Path();
            this.selectorPath.setFillType(Path.FillType.WINDING);
        }

        /* access modifiers changed from: protected */
        public void onSizeChanged(int i, int i2, int i3, int i4) {
            configurePaint(this.colorPaint);
            this.selectorPath.reset();
            this.selectorSize = ((float) i2) * 0.25f;
            this.selectorPath.moveTo(0.0f, 0.0f);
            this.selectorPath.lineTo(this.selectorSize * 2.0f, 0.0f);
            this.selectorPath.lineTo(this.selectorSize, this.selectorSize);
            this.selectorPath.close();
        }

        /* access modifiers changed from: protected */
        public void onDraw(Canvas canvas) {
            float width = (float) getWidth();
            float height = (float) getHeight();
            canvas.drawRect(this.selectorSize, this.selectorSize, width - this.selectorSize, height, this.colorPaint);
            canvas.drawRect(this.selectorSize, this.selectorSize, width - this.selectorSize, height, this.borderPaint);
            this.selectorPath.offset(this.currentValue * (width - (2.0f * this.selectorSize)), 0.0f, this.currentSelectorPath);
            canvas.drawPath(this.currentSelectorPath, this.selectorPaint);
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            switch (motionEvent.getActionMasked()) {
                case 0:
                case 2:
                    this.handler.onTouchEvent(motionEvent);
                    return true;
                case 1:
                    update(motionEvent);
                    return true;
                default:
                    return super.onTouchEvent(motionEvent);
            }
        }

        public void update(MotionEvent motionEvent) {
            updateValue(motionEvent.getX());
            this.emitter.onColor(assembleColor(), true);
        }

        /* access modifiers changed from: package-private */
        public void setBaseColor(int i, boolean z) {
            this.baseColor = i;
            configurePaint(this.colorPaint);
            if (!z) {
                this.currentValue = resolveValue(i);
                this.emitter.onColor(i, false);
            } else {
                this.emitter.onColor(assembleColor(), true);
            }
            invalidate();
        }

        private void updateValue(float f) {
            float f2;
            float f3 = this.selectorSize;
            float width = ((float) getWidth()) - this.selectorSize;
            if (f < f3) {
                f2 = f3;
            } else {
                f2 = f;
            }
            if (f2 > width) {
                f2 = width;
            }
            this.currentValue = (f2 - f3) / (width - f3);
            invalidate();
        }

        public void subscribe(ColorObserver colorObserver) {
            this.emitter.subscribe(colorObserver);
        }

        public void unsubscribe(ColorObserver colorObserver) {
            this.emitter.unsubscribe(colorObserver);
        }

        public int getColor() {
            return this.emitter.getColor();
        }

        public void bind(ColorObservable colorObservable) {
            if (colorObservable != null) {
                colorObservable.subscribe(this.bindObserver);
                setBaseColor(colorObservable.getColor(), true);
            }
            this.boundObservable = colorObservable;
        }

        public void unbind() {
            if (this.boundObservable != null) {
                this.boundObservable.unsubscribe(this.bindObserver);
                this.boundObservable = null;
            }
        }
    }

    public static class ColorPickerPopup {
        private String cancelTitle;
        private Context context;
        private boolean enableAlpha;
        private boolean enableBrightness;
        private int initialColor;
        private String okTitle;
        /* access modifiers changed from: private */
        public boolean showIndicator;
        /* access modifiers changed from: private */
        public boolean showValue;
        private int themeColor;

        public interface ColorPickerObserver extends ColorObserver {
            void onColorPicked(int i);

            void onCustomClicked();
        }

        private ColorPickerPopup(Builder builder) {
            this.themeColor = -16740915;
            this.context = builder.context;
            this.initialColor = builder.initialColor;
            this.enableBrightness = builder.enableBrightness;
            this.enableAlpha = builder.enableAlpha;
            this.okTitle = builder.okTitle;
            this.cancelTitle = builder.cancelTitle;
            this.showIndicator = builder.showIndicator;
            this.showValue = builder.showValue;
            this.themeColor = builder.themeColor;
        }

        /* synthetic */ ColorPickerPopup(Builder builder, ColorPickerPopup colorPickerPopup) {
            this(builder);
        }

        public void show(ColorPickerObserver colorPickerObserver) {
            show((View) null, colorPickerObserver);
        }

        public void show(View view, ColorPickerObserver colorPickerObserver) {
            if (((LayoutInflater) this.context.getSystemService("layout_inflater")) != null) {
                int dip = (int) SketchwareUtil.getDip(this.context, 8);
                LinearLayout linearLayout = new LinearLayout(this.context);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
                linearLayout.setGravity(16);
                final ColorPickerView colorPickerView = new ColorPickerView(this.context);
                colorPickerView.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
                LinearLayout linearLayout2 = new LinearLayout(this.context);
                linearLayout2.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
                linearLayout2.setOrientation(0);
                linearLayout2.setGravity(17);
                linearLayout2.setPadding(0, 16, 0, 16);
                final TextView textView = new TextView(this.context);
                textView.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
                textView.setPadding(dip, dip, dip, dip);
                textView.setSingleLine(true);
                textView.setGravity(17);
                final LinearLayout linearLayout3 = new LinearLayout(this.context);
                linearLayout3.setLayoutParams(new LinearLayout.LayoutParams(200, 60));
                linearLayout3.setOrientation(0);
                linearLayout2.addView(textView, 0);
                linearLayout2.addView(linearLayout3);
                LinearLayout linearLayout4 = new LinearLayout(this.context);
                linearLayout4.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
                linearLayout4.setOrientation(0);
                linearLayout4.setGravity(5);
                linearLayout4.setBackgroundColor(this.themeColor);
                TextView textView2 = new TextView(this.context);
                textView2.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
                textView2.setTextColor(-1);
                textView2.setTextSize(16.0f);
                textView2.setPadding(dip, dip, dip, dip);
                TextView textView3 = new TextView(this.context);
                textView3.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
                textView3.setTextColor(-1);
                textView3.setTextSize(16.0f);
                textView3.setPadding(16, 0, 0, 0);
                textView3.setPadding(dip, dip, dip, dip);
                textView3.setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{-1}), (Drawable) null, (Drawable) null));
                textView2.setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{-1}), (Drawable) null, (Drawable) null));
                linearLayout4.addView(textView2);
                linearLayout4.addView(textView3);
                LinearLayout linearLayout5 = new LinearLayout(this.context);
                linearLayout5.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
                linearLayout5.setBackgroundColor(-1);
                linearLayout5.setOrientation(1);
                linearLayout5.setPadding(0, 16, 0, 0);
                linearLayout5.addView(colorPickerView);
                linearLayout5.addView(linearLayout2);
                linearLayout5.addView(linearLayout4);
                linearLayout.addView(linearLayout5);
                final AlertDialog create = new AlertDialog.Builder(this.context).create();
                new LinearLayout(this.context);
                create.setView(linearLayout);
                colorPickerView.setInitialColor(this.initialColor);
                colorPickerView.setEnabledBrightness(this.enableBrightness);
                colorPickerView.setEnabledAlpha(this.enableAlpha);
                colorPickerView.subscribe(colorPickerObserver);
                textView2.setText(this.cancelTitle);
                textView2.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        create.dismiss();
                    }
                });
                textView3.setText(this.okTitle);
                final ColorPickerObserver colorPickerObserver2 = colorPickerObserver;
                textView3.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        create.dismiss();
                        if (colorPickerObserver2 != null) {
                            colorPickerObserver2.onColorPicked(colorPickerView.getColor());
                        }
                    }
                });
                linearLayout3.setVisibility(this.showIndicator ? 0 : 8);
                textView.setVisibility(this.showValue ? 0 : 8);
                colorPickerView.subscribe(new ColorObserver() {
                    public void onColor(int i, boolean z) {
                        if (ColorPickerPopup.this.showIndicator) {
                            linearLayout3.setBackgroundColor(i);
                        }
                        if (ColorPickerPopup.this.showValue) {
                            textView.setText(ColorPickerPopup.this.colorHex(i));
                        }
                    }
                });
                create.show();
            }
        }

        public static class Builder {
            /* access modifiers changed from: private */
            public String cancelTitle = "Cancel";
            /* access modifiers changed from: private */
            public Context context;
            /* access modifiers changed from: private */
            public boolean enableAlpha = false;
            /* access modifiers changed from: private */
            public boolean enableBrightness = true;
            /* access modifiers changed from: private */
            public int initialColor = -65281;
            /* access modifiers changed from: private */
            public String okTitle = "OK";
            /* access modifiers changed from: private */
            public boolean showIndicator = true;
            /* access modifiers changed from: private */
            public boolean showValue = true;
            /* access modifiers changed from: private */
            public int themeColor = -16740915;

            public Builder(Context context2) {
                this.context = context2;
            }

            public Builder initialColor(int i) {
                this.initialColor = i;
                return this;
            }

            public Builder enableBrightness(boolean z) {
                this.enableBrightness = z;
                return this;
            }

            public Builder enableAlpha(boolean z) {
                this.enableAlpha = z;
                return this;
            }

            public Builder okTitle(String str) {
                this.okTitle = str;
                return this;
            }

            public Builder cancelTitle(String str) {
                this.cancelTitle = str;
                return this;
            }

            public Builder showIndicator(boolean z) {
                this.showIndicator = z;
                return this;
            }

            public Builder showValue(boolean z) {
                this.showValue = z;
                return this;
            }

            public ColorPickerPopup build() {
                return new ColorPickerPopup(this, (ColorPickerPopup) null);
            }

            public Builder themeColor(int i) {
                this.themeColor = i;
                return this;
            }
        }

        /* access modifiers changed from: private */
        public String colorHex(int i) {
            int alpha = Color.alpha(i);
            int red = Color.red(i);
            int green = Color.green(i);
            int blue = Color.blue(i);
            return String.format(Locale.getDefault(), "#%02X%02X%02X%02X", new Object[]{Integer.valueOf(alpha), Integer.valueOf(red), Integer.valueOf(green), Integer.valueOf(blue)});
        }
    }

    public static class ColorObservableEmitter implements ColorObservable {
        private int color;
        private List<ColorObserver> observers = new ArrayList();

        public void subscribe(ColorObserver colorObserver) {
            if (colorObserver != null) {
                this.observers.add(colorObserver);
            }
        }

        public void unsubscribe(ColorObserver colorObserver) {
            if (colorObserver != null) {
                this.observers.remove(colorObserver);
            }
        }

        public int getColor() {
            return this.color;
        }

        /* access modifiers changed from: package-private */
        public void onColor(int i, boolean z) {
            this.color = i;
            for (ColorObserver onColor : this.observers) {
                onColor.onColor(i, z);
            }
        }
    }

    public static class BrightnessSliderView extends ColorSliderView {
        public BrightnessSliderView(Context context) {
            super(context);
        }

        public BrightnessSliderView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public BrightnessSliderView(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
        }

        /* access modifiers changed from: protected */
        public float resolveValue(int i) {
            float[] fArr = new float[3];
            Color.colorToHSV(i, fArr);
            return fArr[2];
        }

        /* access modifiers changed from: protected */
        public void configurePaint(Paint paint) {
            float[] fArr = new float[3];
            Color.colorToHSV(this.baseColor, fArr);
            fArr[2] = 0.0f;
            int HSVToColor = Color.HSVToColor(fArr);
            fArr[2] = 1.0f;
            paint.setShader(new LinearGradient(0.0f, 0.0f, (float) getWidth(), (float) getHeight(), HSVToColor, Color.HSVToColor(fArr), Shader.TileMode.CLAMP));
        }

        /* access modifiers changed from: protected */
        public int assembleColor() {
            float[] fArr = new float[3];
            Color.colorToHSV(this.baseColor, fArr);
            fArr[2] = this.currentValue;
            return Color.HSVToColor(fArr);
        }
    }

    public static class AlphaSliderView extends ColorSliderView {
        private Bitmap backgroundBitmap;
        private Canvas backgroundCanvas;

        public AlphaSliderView(Context context) {
            super(context);
        }

        public AlphaSliderView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public AlphaSliderView(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
        }

        /* access modifiers changed from: protected */
        public void onSizeChanged(int i, int i2, int i3, int i4) {
            super.onSizeChanged(i, i2, i3, i4);
            this.backgroundBitmap = Bitmap.createBitmap((int) (((float) i) - (2.0f * this.selectorSize)), (int) (((float) i2) - this.selectorSize), Bitmap.Config.ARGB_8888);
            this.backgroundCanvas = new Canvas(this.backgroundBitmap);
        }

        /* access modifiers changed from: protected */
        public void onDraw(Canvas canvas) {
            CheckerboardDrawable create = CheckerboardDrawable.create();
            create.setBounds(0, 0, this.backgroundCanvas.getWidth(), this.backgroundCanvas.getHeight());
            create.draw(this.backgroundCanvas);
            canvas.drawBitmap(this.backgroundBitmap, this.selectorSize, this.selectorSize, (Paint) null);
            super.onDraw(canvas);
        }

        /* access modifiers changed from: protected */
        public float resolveValue(int i) {
            return ((float) Color.alpha(i)) / 255.0f;
        }

        /* access modifiers changed from: protected */
        public void configurePaint(Paint paint) {
            float[] fArr = new float[3];
            Color.colorToHSV(this.baseColor, fArr);
            paint.setShader(new LinearGradient(0.0f, 0.0f, (float) getWidth(), (float) getHeight(), Color.HSVToColor(0, fArr), Color.HSVToColor(255, fArr), Shader.TileMode.CLAMP));
        }

        /* access modifiers changed from: protected */
        public int assembleColor() {
            float[] fArr = new float[3];
            Color.colorToHSV(this.baseColor, fArr);
            return Color.HSVToColor((int) (this.currentValue * 255.0f), fArr);
        }
    }

    public static class ColorWheelView extends FrameLayout implements ColorObservable, Updatable {
        private float centerX;
        private float centerY;
        private int currentColor;
        private PointF currentPoint;
        private ColorObservableEmitter emitter;
        private ThrottledTouchEventHandler handler;
        private float radius;
        private ColorWheelSelector selector;
        private float selectorRadiusPx;

        public ColorWheelView(Context context) {
            this(context, (AttributeSet) null);
        }

        public ColorWheelView(Context context, AttributeSet attributeSet) {
            this(context, attributeSet, 0);
        }

        public ColorWheelView(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
            this.selectorRadiusPx = 27.0f;
            this.currentPoint = new PointF();
            this.currentColor = -65281;
            this.emitter = new ColorObservableEmitter();
            this.handler = new ThrottledTouchEventHandler(this);
            this.selectorRadiusPx = 9.0f * getResources().getDisplayMetrics().density;
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
            ColorWheelPalette colorWheelPalette = new ColorWheelPalette(context);
            int i2 = (int) this.selectorRadiusPx;
            colorWheelPalette.setPadding(i2, i2, i2, i2);
            addView(colorWheelPalette, layoutParams);
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, -1);
            this.selector = new ColorWheelSelector(context);
            this.selector.setSelectorRadiusPx(this.selectorRadiusPx);
            addView(this.selector, layoutParams2);
        }

        /* access modifiers changed from: protected */
        public void onMeasure(int i, int i2) {
            int min = Math.min(View.MeasureSpec.getSize(i), View.MeasureSpec.getSize(i2));
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(min, BasicMeasure.EXACTLY), View.MeasureSpec.makeMeasureSpec(min, BasicMeasure.EXACTLY));
        }

        /* access modifiers changed from: protected */
        public void onSizeChanged(int i, int i2, int i3, int i4) {
            int paddingLeft = (i - getPaddingLeft()) - getPaddingRight();
            int paddingTop = (i2 - getPaddingTop()) - getPaddingBottom();
            this.radius = (((float) Math.min(paddingLeft, paddingTop)) * 0.5f) - this.selectorRadiusPx;
            if (this.radius >= 0.0f) {
                this.centerX = ((float) paddingLeft) * 0.5f;
                this.centerY = ((float) paddingTop) * 0.5f;
                setColor(this.currentColor);
            }
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            switch (motionEvent.getActionMasked()) {
                case 0:
                case 2:
                    this.handler.onTouchEvent(motionEvent);
                    return true;
                case 1:
                    update(motionEvent);
                    return true;
                default:
                    return super.onTouchEvent(motionEvent);
            }
        }

        public void update(MotionEvent motionEvent) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            this.emitter.onColor(getColorAtPoint(x, y), true);
            updateSelector(x, y);
        }

        private int getColorAtPoint(float f, float f2) {
            float f3 = f - this.centerX;
            float f4 = f2 - this.centerY;
            double sqrt = Math.sqrt((double) ((f3 * f3) + (f4 * f4)));
            float[] fArr = {0.0f, 0.0f, 1.0f};
            fArr[0] = ((float) ((Math.atan2((double) f4, (double) (-f3)) / 3.141592653589793d) * 180.0d)) + 180.0f;
            fArr[1] = Math.max(0.0f, Math.min(1.0f, (float) (sqrt / ((double) this.radius))));
            return Color.HSVToColor(fArr);
        }

        public void setColor(int i) {
            float[] fArr = new float[3];
            Color.colorToHSV(i, fArr);
            float f = fArr[1] * this.radius;
            float f2 = (float) (((double) (fArr[0] / 180.0f)) * 3.141592653589793d);
            updateSelector((float) ((((double) f) * Math.cos((double) f2)) + ((double) this.centerX)), (float) ((Math.sin((double) f2) * ((double) (-f))) + ((double) this.centerY)));
            this.currentColor = i;
            this.emitter.onColor(i, false);
        }

        private void updateSelector(float f, float f2) {
            float f3 = f - this.centerX;
            float f4 = f2 - this.centerY;
            double sqrt = Math.sqrt((double) ((f3 * f3) + (f4 * f4)));
            if (sqrt > ((double) this.radius)) {
                f3 = (float) (((double) f3) * (((double) this.radius) / sqrt));
                f4 = (float) ((((double) this.radius) / sqrt) * ((double) f4));
            }
            this.currentPoint.x = f3 + this.centerX;
            this.currentPoint.y = f4 + this.centerY;
            this.selector.setCurrentPoint(this.currentPoint);
        }

        public void subscribe(ColorObserver colorObserver) {
            this.emitter.subscribe(colorObserver);
        }

        public void unsubscribe(ColorObserver colorObserver) {
            this.emitter.unsubscribe(colorObserver);
        }

        public int getColor() {
            return this.emitter.getColor();
        }
    }

    public static class CheckerboardDrawable extends Drawable {
        private int colorEven;
        private int colorOdd;
        private Paint paint;
        private int size;

        public static CheckerboardDrawable create() {
            return new CheckerboardDrawable(new Builder());
        }

        private CheckerboardDrawable(Builder builder) {
            this.paint = new Paint(1);
            this.size = builder.size;
            this.colorOdd = builder.colorOdd;
            this.colorEven = builder.colorEven;
            configurePaint();
        }

        /* synthetic */ CheckerboardDrawable(Builder builder, CheckerboardDrawable checkerboardDrawable) {
            this(builder);
        }

        private void configurePaint() {
            Bitmap createBitmap = Bitmap.createBitmap(this.size * 2, this.size * 2, Bitmap.Config.ARGB_8888);
            Paint paint2 = new Paint(1);
            paint2.setStyle(Paint.Style.FILL);
            Canvas canvas = new Canvas(createBitmap);
            Rect rect = new Rect(0, 0, this.size, this.size);
            paint2.setColor(this.colorOdd);
            canvas.drawRect(rect, paint2);
            rect.offset(this.size, this.size);
            canvas.drawRect(rect, paint2);
            paint2.setColor(this.colorEven);
            rect.offset(-this.size, 0);
            canvas.drawRect(rect, paint2);
            rect.offset(this.size, -this.size);
            canvas.drawRect(rect, paint2);
            this.paint.setShader(new BitmapShader(createBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        }

        public void draw(Canvas canvas) {
            canvas.drawPaint(this.paint);
        }

        public void setAlpha(int i) {
            this.paint.setAlpha(i);
        }

        public void setColorFilter(ColorFilter colorFilter) {
            this.paint.setColorFilter(colorFilter);
        }

        public int getOpacity() {
            return -1;
        }

        public static final class Builder {
            /* access modifiers changed from: private */
            public int colorEven = -789517;
            /* access modifiers changed from: private */
            public int colorOdd = -4013374;
            /* access modifiers changed from: private */
            public int size = 40;

            public Builder size(int i) {
                this.size = i;
                return this;
            }

            public Builder colorOdd(int i) {
                this.colorOdd = i;
                return this;
            }

            public Builder colorEven(int i) {
                this.colorEven = i;
                return this;
            }

            public CheckerboardDrawable build() {
                return new CheckerboardDrawable(this, (CheckerboardDrawable) null);
            }
        }
    }

    public void _changeActivityFont(String str) {
        this.fontName = "fonts/".concat(str.concat(".ttf"));
        overrideFonts(this, getWindow().getDecorView());
    }

    private void overrideFonts(Context context, View view) {
        try {
            Typeface createFromAsset = Typeface.createFromAsset(getAssets(), this.fontName);
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    overrideFonts(context, viewGroup.getChildAt(i));
                }
            } else if (view instanceof TextView) {
                ((TextView) view).setTypeface(createFromAsset);
            } else if (view instanceof EditText) {
                ((EditText) view).setTypeface(createFromAsset);
            } else if (view instanceof Button) {
                ((Button) view).setTypeface(createFromAsset);
            }
        } catch (Exception e) {
            SketchwareUtil.showMessage(getApplicationContext(), "Error Loading Font");
        }
    }

    public class Lv_primary_colorsAdapter extends BaseAdapter {
        ArrayList<HashMap<String, Object>> _data;

        public Lv_primary_colorsAdapter(ArrayList<HashMap<String, Object>> arrayList) {
            this._data = arrayList;
        }

        public int getCount() {
            return this._data.size();
        }

        public HashMap<String, Object> getItem(int i) {
            return this._data.get(i);
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater layoutInflater = ColorPickerActivity.this.getLayoutInflater();
            if (view == null) {
                view = layoutInflater.inflate(R.layout.primary_color, (ViewGroup) null);
            }
            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linear2);
            LinearLayout linearLayout2 = (LinearLayout) view.findViewById(R.id.linear);
            TextView textView = (TextView) view.findViewById(R.id.tv);
            try {
                linearLayout2.setBackgroundColor(Color.parseColor(this._data.get(i).get("hexCode").toString()));
                textView.setText(this._data.get(i).get("name").toString());
                if (ColorUtils.calculateLuminance(Color.parseColor(this._data.get(i).get("hexCode").toString())) < 0.5d) {
                    textView.setTextColor(-1);
                } else {
                    textView.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                }
            } catch (Exception e) {
                ColorPickerActivity.this.showMessage(e.toString());
            }
            return view;
        }
    }

    public class Lv_sub_colorsAdapter extends BaseAdapter {
        ArrayList<HashMap<String, Object>> _data;

        public Lv_sub_colorsAdapter(ArrayList<HashMap<String, Object>> arrayList) {
            this._data = arrayList;
        }

        public int getCount() {
            return this._data.size();
        }

        public HashMap<String, Object> getItem(int i) {
            return this._data.get(i);
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public View getView(final int i, View view, ViewGroup viewGroup) {
            LayoutInflater layoutInflater = ColorPickerActivity.this.getLayoutInflater();
            if (view == null) {
                view = layoutInflater.inflate(R.layout.sub_colors, (ViewGroup) null);
            }
            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linear);
            TextView textView = (TextView) view.findViewById(R.id.tv);
            try {
                linearLayout.setBackgroundColor(Color.parseColor(this._data.get(i).get("hexCode").toString()));
            } catch (Exception e) {
                linearLayout.setBackgroundColor(-1118482);
            }
            try {
                textView.setText(this._data.get(i).get("hexCode").toString());
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        ColorPickerActivity.this._returnColor(Lv_sub_colorsAdapter.this._data.get(i).get("hexCode").toString());
                    }
                });
                linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                    public boolean onLongClick(View view) {
                        ColorPickerActivity access$1 = ColorPickerActivity.this;
                        ColorPickerActivity.this.getApplicationContext();
                        ((ClipboardManager) access$1.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("clipboard", Lv_sub_colorsAdapter.this._data.get(i).get("hexCode").toString()));
                        SketchwareUtil.showMessage(ColorPickerActivity.this.getApplicationContext(), Lv_sub_colorsAdapter.this._data.get(i).get("hexCode").toString().concat("  Copied to clipboard"));
                        return true;
                    }
                });
                if (this._data.get(i).get("isDark").toString().equals("true")) {
                    textView.setTextColor(-1);
                } else {
                    textView.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                }
            } catch (Exception e2) {
            }
            return view;
        }
    }

    @Deprecated
    public void showMessage(String str) {
        Toast.makeText(getApplicationContext(), str, 0).show();
    }

    @Deprecated
    public int getLocationX(View view) {
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        return iArr[0];
    }

    @Deprecated
    public int getLocationY(View view) {
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        return iArr[1];
    }

    @Deprecated
    public int getRandom(int i, int i2) {
        return new Random().nextInt((i2 - i) + 1) + i;
    }

    @Deprecated
    public ArrayList<Double> getCheckedItemPositionsToArray(ListView listView) {
        ArrayList<Double> arrayList = new ArrayList<>();
        SparseBooleanArray checkedItemPositions = listView.getCheckedItemPositions();
        for (int i = 0; i < checkedItemPositions.size(); i++) {
            if (checkedItemPositions.valueAt(i)) {
                arrayList.add(Double.valueOf((double) checkedItemPositions.keyAt(i)));
            }
        }
        return arrayList;
    }

    @Deprecated
    public float getDip(int i) {
        return TypedValue.applyDimension(1, (float) i, getResources().getDisplayMetrics());
    }

    @Deprecated
    public int getDisplayWidthPixels() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    @Deprecated
    public int getDisplayHeightPixels() {
        return getResources().getDisplayMetrics().heightPixels;
    }
}
