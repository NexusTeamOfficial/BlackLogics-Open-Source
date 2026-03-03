/* Decompiler 2899ms, total 6535ms, lines 4614 */
package com.nexusteam.internal.editor;
import com.nexusteam.blacklogics.R;

import b.b.b.Qm;
import b.b.b.Qf;
import com.nexusteam.internal.BlockStorage;
import com.nexusteam.internal.FullStorage;
import com.nexusteam.internal.ei;
import com.nexusteam.internal.ej;
import com.nexusteam.internal.es;
import com.nexusteam.internal.hb;
import com.nexusteam.internal.ek;
import com.nexusteam.internal.el;
import com.nexusteam.internal.em;
import com.nexusteam.internal.et;
import com.nexusteam.internal.fa;
import com.nexusteam.internal.fc;
import com.nexusteam.internal.fe;
import com.nexusteam.internal.ff;
import com.nexusteam.internal.fm;
import com.nexusteam.internal.fn;
import com.nexusteam.internal.fo;
import com.nexusteam.internal.fp;
import com.nexusteam.internal.BlockComponentView;
import com.nexusteam.internal.fq;
import com.nexusteam.internal.fr;
import com.nexusteam.internal.hc;
import com.nexusteam.internal.hl;
import com.nexusteam.internal.jg;
import com.nexusteam.internal.jq;
import com.nexusteam.internal.CustomAlertDialog;
import com.nexusteam.internal.ke;
import com.nexusteam.internal.ki;
import com.nexusteam.internal.kk;
import com.nexusteam.internal.ViewHelper;
import com.nexusteam.internal.StringResourceManager;
import com.nexusteam.internal.kv;
import com.nexusteam.internal.kx;
import com.nexusteam.internal.ky;
import com.nexusteam.internal.lf;
import com.nexusteam.internal.lr;
import com.nexusteam.internal.lu;
import com.nexusteam.internal.lw;
import com.nexusteam.internal.ma;
import com.nexusteam.internal.mb;
import com.nexusteam.blacklogics.FileUtil;
import com.nexusteam.internal.model.ComponentData;
import com.nexusteam.internal.model.VariableData;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.media.AudioAttributes.Builder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.os.Build.VERSION;
import androidx.fragment.app.FragmentActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.ActionBar;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.*;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.textfield.TextInputLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
import com.nexusteam.internal.beans.BlockBean;
import com.nexusteam.internal.beans.BlockCollectionBean;
import com.nexusteam.internal.beans.ComponentBean;
import com.nexusteam.internal.beans.HistoryBlockBean;
import com.nexusteam.internal.beans.MoreBlockCollectionBean;
import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.internal.beans.ProjectResourceBean;
import com.nexusteam.internal.beans.ViewBean;
import com.nexusteam.internal.editor.component.ComponentAddActivity;
import com.nexusteam.internal.editor.logic.LogicTopMenu;
import com.nexusteam.internal.editor.logic.PaletteBlock;
import com.nexusteam.internal.editor.logic.PaletteSelector;
import com.nexusteam.internal.editor.makeblock.MakeBlockActivity;
import com.besome.blacklogics.development.Complex;

import com.nexusteam.internal.editor.view.ViewDummy;
import com.nexusteam.internal.editor.view.ViewLogicEditor;
import com.nexusteam.internal.lib.base.BaseAppCompatActivity;

//import com.bumptech.glide.DrawableRequestBuilder;
//import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class LogicEditorActivity extends BaseAppCompatActivity implements fr, OnClickListener, OnTouchListener {
    private int A;
    private int B;
    private int[] C = new int[2];
    private int D = 0;
    private Vibrator E;
    private boolean F;
    private kv G;
    private kk H = new kk();
    private int I = 0;
    private int P = -30;
    private ObjectAnimator Q;
    private ObjectAnimator R;
    private boolean S = false;
    private boolean T = false;
    private final Handler U = new Handler();
    private Runnable V = new LogicEditorActivity$23(this);
    private ObjectAnimator W;
    private ObjectAnimator X;
    private boolean Y = false;
    private boolean Z = false;
    protected Toolbar a;
    private ObjectAnimator aa;
    private ObjectAnimator ab;
    private boolean ac = false;
    private boolean ad = false;
    private ArrayList<Pair<Integer, String>> ae;
    private ArrayList<Pair<Integer, String>> af;
    private ArrayList<ProjectResourceBean> ag;
    private ArrayList<ProjectResourceBean> ah;
    private ArrayList<ProjectResourceBean> ai;
    protected PaletteSelector b;
    protected PaletteBlock c;
    protected ViewLogicEditor d;
    protected fq e;
    protected String f = "";
    protected String g = "";
    protected String h = "";
    protected String i = "";
    protected LinearLayout j;
    protected LinearLayout k;
    protected FloatingActionButton l;
    protected ProjectFileBean m;
    protected LogicTopMenu n;
    protected LogicEditorDrawer o; 
    protected View p = null;
    LogicEditorActivity.a q;
    ArrayList<MoreBlockCollectionBean> r;
    private ViewDummy s;
    private float t = 0.0F;
    private float u = 0.0F;
    private float v = 0.0F;
    private float w = 0.0F;
    private boolean x = false;
    private int[] y = new int[2];
    private fm z;
    private String type;
    private String widgetId;
    private String activityName;
    private Complex complex;
    
    public class LogicEditorActivity$23 implements Runnable {
        
        final LogicEditorActivity a;
        
        public LogicEditorActivity$23(LogicEditorActivity activity) {
            this.a = activity;
        }
        
        @Override
        public void run() {
            a.b(); // Calls the b() method of LogicEditorActivity
        }
    }
    
    public class LogicEditorActivity$1 implements View.OnClickListener {
        
        final LogicEditorActivity a;
        
        public LogicEditorActivity$1(LogicEditorActivity activity) {
            this.a = activity;
        }
        
        @Override
        public void onClick(View v) {

            if (ki.a()) {
                return;
            }
            
            a.onBackPressed();
        }
    }
    
    class LogicEditorActivity$2 implements View.OnClickListener {
        final CustomAlertDialog a;
        final LogicEditorActivity b;
        
        LogicEditorActivity$2(LogicEditorActivity activity, CustomAlertDialog dialog) {
            this.b = activity;
            this.a = dialog;
        }
        
        @Override
        public void onClick(View v) {
            a.dismiss();
        }
    }
    
    class LogicEditorActivity$3 implements View.OnClickListener {
        final lr a;
        final RadioGroup b;
        final EditText c;
        final CustomAlertDialog d;
        final LogicEditorActivity e;
        
        LogicEditorActivity$3(LogicEditorActivity activity, lr validator, RadioGroup group, EditText input, CustomAlertDialog dialog) {
            this.e = activity;
            this.a = validator;
            this.b = group;
            this.c = input;
            this.d = dialog;
        }
        
        @Override
        public void onClick(View v) {

            if (!a.a()) {
                return;
            }
            
            int checkedId = b.getCheckedRadioButtonId();
            int type = 1;
            
            if (checkedId == com.nexusteam.blacklogics.R.id.rb_int) { // was 0x7f08039b
                type = 1;
            } else if (checkedId == com.nexusteam.blacklogics.R.id.rb_string) { // was 0x7f08039f
                type = 2;
            } else if (checkedId == com.nexusteam.blacklogics.R.id.rb_map) { // was 0x7f08039c
                type = 3;
            }
            
            String text = c.getText().toString();
            
            e.b(type, text);
            ki.a(e.getApplicationContext(), c);
            d.dismiss();
        }
    }
    
    public class LogicEditorActivity$4 implements View.OnClickListener {
        
        final EditText a;
        final CustomAlertDialog b;
        final LogicEditorActivity c;
        
        public LogicEditorActivity$4(LogicEditorActivity logicEditorActivity, EditText editText, CustomAlertDialog dialog) {
            this.c = logicEditorActivity;
            this.a = editText;
            this.b = dialog;
        }
        
        @Override
        public void onClick(View v) {

            Context context = c.getApplicationContext();
            

            ki.a(context, a);
            

            b.dismiss();
        }
    }
    
    public class LogicEditorActivity$5 implements View.OnClickListener {
        
        final ViewGroup a;
        final CustomAlertDialog b;
        final LogicEditorActivity c;
        
        public LogicEditorActivity$5(LogicEditorActivity logicEditorActivity, ViewGroup viewGroup, CustomAlertDialog dialog) {
            this.c = logicEditorActivity;
            this.a = viewGroup;
            this.b = dialog;
        }
        
        @Override
        public void onClick(View v) {
            int childCount = a.getChildCount();
            for (int i = 0; i < childCount; i++) {
                RadioButton rb = (RadioButton) a.getChildAt(i);
                if (rb.isChecked()) {
                    

                    fq fqVar = c.e;
                    String name = rb.getText().toString();
                    boolean exists = fqVar.b(name);
                    
                    if (!exists) {
                        lw lwVar = ma.a(c.f);
                        String javaName = c.getJavaName();
                        String selected = rb.getText().toString();
                        String finalName = c.g + "_" + c.h;
                        
                        boolean duplicate = lwVar.b(javaName, selected, finalName);
                        if (!duplicate) {

                            c.c(selected);
                        } else {

                            Context ctx = c.getApplicationContext();
                            String msg = StringResourceManager.a().a(c.getApplicationContext(), com.nexusteam.blacklogics.R.string.block_fileutil_write_head);
                            Toast.makeText(ctx, msg, 0).show();
                            return;
                        }
                    } else {

                        Context ctx = c.getApplicationContext();
                        String msg = StringResourceManager.a().a(c.getApplicationContext(), com.nexusteam.blacklogics.R.string.block_fileutil_write_head);
                        Toast.makeText(ctx, msg, 0).show();
                        return;
                    }
                }
            }
            

            b.dismiss();
        }
    }
    
    class LogicEditorActivity$6 implements View.OnClickListener {
        final CustomAlertDialog a;
        final LogicEditorActivity b;
        
        LogicEditorActivity$6(LogicEditorActivity activity, CustomAlertDialog dialog) {
            this.b = activity;
            this.a = dialog;
        }
        
        @Override
        public void onClick(View v) {
            a.dismiss();
        }
    }
    
    public class LogicEditorActivity$7 implements View.OnClickListener {
        
        final String a;
        final CustomAlertDialog b;
        final LogicEditorActivity c;
        
        public LogicEditorActivity$7(LogicEditorActivity logicEditorActivity, String str, CustomAlertDialog dialog) {
            this.c = logicEditorActivity;
            this.a = str;
            this.b = dialog;
        }
        
        @Override
        public void onClick(View v) {

            ei manager = ei.f();
            manager.a(a, true);
            

            c.o.a(a);
            

            b.dismiss();
        }
    }
    
    public class LogicEditorActivity$8 implements View.OnClickListener {
        
        final CustomAlertDialog a;
        final LogicEditorActivity b;
        
        public LogicEditorActivity$8(LogicEditorActivity logicEditorActivity, CustomAlertDialog dialog) {
            this.b = logicEditorActivity;
            this.a = dialog;
        }
        
        @Override
        public void onClick(View v) {

            a.dismiss();
        }
    }
    
    class LogicEditorActivity$9 implements View.OnClickListener {
        
        final fn a;
        final EditText b;
        final CustomAlertDialog c;
        final LogicEditorActivity d;
        
        LogicEditorActivity$9(LogicEditorActivity d, fn a, EditText b, CustomAlertDialog c) {
            this.d = d;
            this.a = a;
            this.b = b;
            this.c = c;
        }
        
        @Override
        public void onClick(View view) {
            d.a(a, b.getText().toString());
            

            Context context = d.getApplicationContext();
            ki.a(context, b);
            

            c.dismiss();
        }
    }
    
    
    class LogicEditorActivity$10 implements View.OnClickListener {
        
        final EditText a;
        final CustomAlertDialog b;
        final LogicEditorActivity c;
        
        LogicEditorActivity$10(LogicEditorActivity c, EditText a, CustomAlertDialog b) {
            this.c = c;
            this.a = a;
            this.b = b;
        }
        
        @Override
        public void onClick(View view) {
            Context context = c.getApplicationContext();
            ki.a(context, a); // Smali me static method call
            b.dismiss();
        }
    }
    

    class LogicEditorActivity$11 implements View.OnClickListener {
        
        final fn a;
        final EditText b;
        final CustomAlertDialog c;
        final LogicEditorActivity d;
        
        LogicEditorActivity$11(LogicEditorActivity d, fn a, EditText b, CustomAlertDialog c) {
            this.d = d;
            this.a = a;
            this.b = b;
            this.c = c;
        }
        
        @Override
        public void onClick(View view) {
            d.a(a, b.getText().toString());
            Context context = d.getApplicationContext();
            ki.a(context, b); // Likely to hide keyboard
            c.dismiss();
        }
    }
    

    class LogicEditorActivity$12 implements View.OnClickListener {
        
        final LogicEditorActivity a;
        
        LogicEditorActivity$12(LogicEditorActivity a) {
            this.a = a;
        }
        
        @Override
        public void onClick(View view) {
            boolean current = LogicEditorActivity.a(a);
            a.a(!current); // XOR logic in smali = toggle boolean
        }
    }
    
    class LogicEditorActivity$13 implements View.OnClickListener {
        
        final EditText a;
        final CustomAlertDialog b;
        final LogicEditorActivity c;
        
        LogicEditorActivity$13(LogicEditorActivity c, EditText a, CustomAlertDialog b) {
            this.c = c;
            this.a = a;
            this.b = b;
        }
        
        @Override
        public void onClick(View view) {
            Context context = c.getApplicationContext();
            ki.a(context, a); // Smali me static method call
            b.dismiss();
        }
    }
    
    class LogicEditorActivity$14 implements View.OnClickListener {
        
        final EditText a;
        final boolean b;
        final fn c;
        final CustomAlertDialog d;
        final LogicEditorActivity e;
        
        LogicEditorActivity$14(LogicEditorActivity e, EditText a, boolean b, fn c, CustomAlertDialog d) {
            this.e = e;
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }
        
        @Override
        public void onClick(View view) {
            String text = a.getText().toString();
            
            if (b) { // numeric check
                try {
                    double value = Double.parseDouble(text);
                    if (Double.isNaN(value) || Double.isInfinite(value)) {
                        text = "";
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    text = "";
                }
            } else {

                if (text.length() > 0 && text.charAt(0) == '@') {
                    text = " " + text;
                }
            }
            
            e.a(c, text);
            

            Context context = e.getApplicationContext();
            ki.a(context, a);
            

            d.dismiss();
        }
    }
    

    class LogicEditorActivity$15 implements View.OnClickListener {
        
        final EditText a;
        final CustomAlertDialog b;
        final LogicEditorActivity c;
        
        LogicEditorActivity$15(LogicEditorActivity c, EditText a, CustomAlertDialog b) {
            this.c = c;
            this.a = a;
            this.b = b;
        }
        
        @Override
        public void onClick(View view) {

            Context context = c.getApplicationContext();
            ki.a(context, a);
            

            b.dismiss();
        }
    }
    class LogicEditorActivity$16 implements View.OnClickListener {
        
        final ViewGroup a;
        final fn b;
        final CustomAlertDialog c;
        final LogicEditorActivity d;
        
        LogicEditorActivity$16(LogicEditorActivity d, ViewGroup a, fn b, CustomAlertDialog c) {
            this.d = d;
            this.a = a;
            this.b = b;
            this.c = c;
        }
        
        @Override
        public void onClick(View view) {
            int count = a.getChildCount();
            for (int i = 0; i < count; i++) {
                RadioButton radioButton = (RadioButton) a.getChildAt(i);
                if (radioButton.isChecked()) {
                    d.a(b, radioButton.getText());
                    break;
                }
            }
            c.dismiss();
        }
    }
    

    class LogicEditorActivity$17 implements View.OnClickListener {
        
        final CustomAlertDialog a;
        final LogicEditorActivity b;
        
        LogicEditorActivity$17(LogicEditorActivity b, CustomAlertDialog a) {
            this.b = b;
            this.a = a;
        }
        
        @Override
        public void onClick(View view) {
            a.dismiss();
        }
    }
    
    class LogicEditorActivity$18 implements View.OnClickListener {
        
        final ViewGroup a;
        final fn b;
        final CustomAlertDialog c;
        final LogicEditorActivity d;
        
        LogicEditorActivity$18(LogicEditorActivity activity, ViewGroup viewGroup, fn fnObj, CustomAlertDialog kdObj) {
            this.d = activity;
            this.a = viewGroup;
            this.b = fnObj;
            this.c = kdObj;
        }
        
        @Override
        public void onClick(View view) {
            int childCount = a.getChildCount();
            
            for (int i = 0; i < childCount; i++) {
                RadioButton rb = (RadioButton) a.getChildAt(i);
                if (rb.isChecked()) {

                    Object tag = rb.getTag();
                    d.a(b, tag);
                    break;
                }
            }
            

            c.dismiss();
        }
    }
    
    class LogicEditorActivity$19 implements View.OnClickListener {
        
        final CustomAlertDialog a;
        final LogicEditorActivity b;
        
        LogicEditorActivity$19(LogicEditorActivity activity, CustomAlertDialog kdObj) {
            this.b = activity;
            this.a = kdObj;
        }
        
        @Override
        public void onClick(View view) {
            a.dismiss();
        }
    }
    
    
    class LogicEditorActivity$20 implements hl.b {
        
        final fn a;
        final LogicEditorActivity b;
        
        LogicEditorActivity$20(LogicEditorActivity b, fn a) {
            this.b = b;
            this.a = a;
        }
        
        @Override
        public void a(int p1) {
            if (p1 == 0) {
                b.a(a, "Color.TRANSPARENT");
            } else {
                String format = "0x%08X";
                Object[] args = new Object[1];
                args[0] = Integer.valueOf(p1 & 0xFFFFFFFF);
                String formatted = String.format(format, args);
                b.a(a, formatted);
            }
        }
    }
    
    class LogicEditorActivity$21 implements View.OnClickListener {
        
        final ViewGroup a;
        final RadioGroup b;
        final LogicEditorActivity c;
        
        LogicEditorActivity$21(LogicEditorActivity c, ViewGroup a, RadioGroup b) {
            this.c = c;
            this.a = a;
            this.b = b;
        }
        
        @Override
        public void onClick(View view) {
            int index = a.indexOfChild(view);
            RadioButton radioButton = (RadioButton) b.getChildAt(index);
            radioButton.setChecked(true);
        }
    }
    

    class LogicEditorActivity$22 implements View.OnClickListener {
        
        final RadioGroup a;
        final fn b;
        final CustomAlertDialog c;
        final LogicEditorActivity d;
        
        LogicEditorActivity$22(LogicEditorActivity d, RadioGroup a, fn b, CustomAlertDialog c) {
            this.d = d;
            this.a = a;
            this.b = b;
            this.c = c;
        }
        
        @Override
        public void onClick(View view) {
            int count = a.getChildCount();
            for (int i = 0; i < count; i++) {
                RadioButton radioButton = (RadioButton) a.getChildAt(i);
                if (radioButton.isChecked()) {
                    d.a(b, radioButton.getTag());
                    break;
                }
            }
            c.dismiss();
        }
    }
    
    
    class LogicEditorActivity$24 implements View.OnClickListener {
        
        final CustomAlertDialog a;
        final LogicEditorActivity b;
        
        LogicEditorActivity$24(LogicEditorActivity b, CustomAlertDialog a) {
            this.b = b;
            this.a = a;
        }
        
        @Override
        public void onClick(View view) {
            a.dismiss();
        }
    }
    

    class LogicEditorActivity$25 implements SoundPool.OnLoadCompleteListener {
        
        final LogicEditorActivity a;
        
        LogicEditorActivity$25(LogicEditorActivity activity) {
            this.a = activity;
        }
        
        @Override
        public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
            if (soundPool != null) {

                soundPool.play(
                sampleId, // sound ID
                1.0f,     // leftVolume
                1.0f,     // rightVolume
                1,        // priority
                0,        // loop
                1.0f      // rate
                );
            }
        }
    }
    
    
    private class LogicEditorActivity$26 implements View.OnClickListener {
        
        private final LogicEditorActivity activity;
        private final SoundPool soundPool;
        
        public LogicEditorActivity$26(LogicEditorActivity activity, SoundPool soundPool) {
            this.activity = activity;
            this.soundPool = soundPool;
        }
        
        @Override
        public void onClick(View view) {

            mb soundManager = ma.c(activity.f);
            

            String soundFile = ((RadioButton) view).getText().toString();
            

            String path = soundManager.d(soundFile);
            

            soundPool.load(path, 1);
        }
    }
    

    class LogicEditorActivity$27 implements View.OnClickListener {
        
        final RadioGroup a;
        final fn b;
        final CustomAlertDialog c;
        final LogicEditorActivity d;
        
        LogicEditorActivity$27(LogicEditorActivity activity, RadioGroup rg, fn fnObj, CustomAlertDialog kdObj) {
            this.d = activity;
            this.a = rg;
            this.b = fnObj;
            this.c = kdObj;
        }
        
        @Override
        public void onClick(View view) {
            int childCount = a.getChildCount();
            
            for (int i = 0; i < childCount; i++) {
                RadioButton rb = (RadioButton) a.getChildAt(i);
                if (rb.isChecked()) {

                    d.a(b, rb.getText().toString());
                    break;
                }
            }
            

            c.dismiss();
        }
    }
    

    class LogicEditorActivity$28 implements View.OnClickListener {
        
        final CustomAlertDialog a; // Dialog
        final LogicEditorActivity b;
        
        LogicEditorActivity$28(LogicEditorActivity activity, CustomAlertDialog dialog) {
            this.b = activity;
            this.a = dialog;
        }
        
        @Override
        public void onClick(View view) {

            a.dismiss();
        }
    }
    

    class LogicEditorActivity$29 implements View.OnClickListener {
        
        final ViewGroup a; // Parent container
        final RadioGroup b; // RadioGroup to select
        final LogicEditorActivity c;
        
        LogicEditorActivity$29(LogicEditorActivity activity, ViewGroup container, RadioGroup radioGroup) {
            this.c = activity;
            this.a = container;
            this.b = radioGroup;
        }
        
        @Override
        public void onClick(View view) {

            int index = a.indexOfChild(view);
            

            RadioButton rb = (RadioButton) b.getChildAt(index);
            rb.setChecked(true);
        }
    }
    

    class LogicEditorActivity$30 implements View.OnClickListener {
        
        final RadioGroup a; // RadioGroup
        final fn b;          // fn object to update
        final CustomAlertDialog c;          // Dialog
        final LogicEditorActivity d;
        
        LogicEditorActivity$30(LogicEditorActivity activity, RadioGroup rg, fn fnObj, CustomAlertDialog dialog) {
            this.d = activity;
            this.a = rg;
            this.b = fnObj;
            this.c = dialog;
        }
        
        @Override
        public void onClick(View view) {
            int count = a.getChildCount();
            for (int i = 0; i < count; i++) {
                RadioButton rb = (RadioButton) a.getChildAt(i);
                if (rb.isChecked()) {

                    d.a(b, rb.getTag());
                    break;
                }
            }
            c.dismiss();
        }
    }
    

    class LogicEditorActivity$31 implements View.OnClickListener {
        
        final CustomAlertDialog a; // Dialog
        final LogicEditorActivity b;
        
        LogicEditorActivity$31(LogicEditorActivity activity, CustomAlertDialog dialog) {
            this.b = activity;
            this.a = dialog;
        }
        
        @Override
        public void onClick(View view) {
            a.dismiss();
        }
    }
    

    class LogicEditorActivity$32 implements View.OnClickListener {
        
        final RadioGroup a; // RadioGroup
        final fn b;          // fn object to update
        final CustomAlertDialog c;          // Dialog
        final LogicEditorActivity d;
        
        LogicEditorActivity$32(LogicEditorActivity activity, RadioGroup rg, fn fnObj, CustomAlertDialog dialog) {
            this.d = activity;
            this.a = rg;
            this.b = fnObj;
            this.c = dialog;
        }
        
        @Override
        public void onClick(View view) {
            int count = a.getChildCount();
            for (int i = 0; i < count; i++) {
                RadioButton rb = (RadioButton) a.getChildAt(i);
                if (rb.isChecked()) {

                    d.a(b, rb.getText().toString());
                    break;
                }
            }
            c.dismiss();
        }
    }
    

    class LogicEditorActivity$33 implements View.OnClickListener {
        
        final CustomAlertDialog a; // Dialog
        final LogicEditorActivity b;
        
        LogicEditorActivity$33(LogicEditorActivity activity, CustomAlertDialog dialog) {
            this.b = activity;
            this.a = dialog;
        }
        
        @Override
        public void onClick(View view) {
            a.dismiss();
        }
    }
    
    
    public class LogicEditorActivity$34 implements Runnable {
        
        final LogicEditorActivity a;
        
        public LogicEditorActivity$34(LogicEditorActivity activity) {
            this.a = activity;
        }
        
        @Override
        public void run() {
            b task = new b(a, a.getApplicationContext());
            task.execute();
        }
    }
    

    public class LogicEditorActivity$35 implements View.OnClickListener {
        
        final lr a;
        final MoreBlockCollectionBean b;
        final EditText c;
        final CustomAlertDialog d;
        final LogicEditorActivity e;
        
        public LogicEditorActivity$35(LogicEditorActivity e, lr a, MoreBlockCollectionBean b, EditText c, CustomAlertDialog d) {
            this.e = e;
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }
        
        @Override
        public void onClick(View view) {
            if (!a.a()) {
                return;
            }
            

            String spec = b.spec;
            if (spec.contains(" ")) {
                int index = spec.indexOf(" ");
                spec = spec.substring(index);
            } else {
                spec = "";
            }
            

            String newSpec = c.getText().toString() + spec;
            b.spec = newSpec;
            

            LogicEditorActivity.a(e, b);
            

            Context context = e.getApplicationContext();
            ki.a(context, c);
            

            d.dismiss();
        }
    }
    
    private class LogicEditorActivity$36 implements View.OnClickListener {
        
        final EditText a;
        final CustomAlertDialog b;
        final LogicEditorActivity c;
        
        public LogicEditorActivity$36(LogicEditorActivity c, EditText a, CustomAlertDialog b) {
            this.c = c;
            this.a = a;
            this.b = b;
        }
        
        @Override
        public void onClick(View view) {

            Context context = c.getApplicationContext();
            ki.a(context, a);
            

            b.dismiss();
        }
    }
    
    class LogicEditorActivity$37 implements View.OnClickListener {
        
        final MoreBlockCollectionBean a;
        final CustomAlertDialog b;
        final LogicEditorActivity c;
        
        LogicEditorActivity$37(LogicEditorActivity c, MoreBlockCollectionBean a, CustomAlertDialog b) {
            this.c = c;
            this.a = a;
            this.b = b;
        }
        
        @Override
        public void onClick(View view) {

            Iterator<Pair<Integer, String>> it1 = LogicEditorActivity.e(c).iterator();
            while (it1.hasNext()) {
                Pair<Integer, String> pair = it1.next();
                lw obj = ma.a(c.f);
                obj.a(c.getJavaName(), pair.first.intValue(), pair.second);
            }
            

            Iterator<Pair<Integer, String>> it2 = LogicEditorActivity.f(c).iterator();
            while (it2.hasNext()) {
                Pair<Integer, String> pair = it2.next();
                lw obj = ma.a(c.f);
                obj.b(c.getJavaName(), pair.first.intValue(), pair.second);
            }
            

            Iterator<ProjectResourceBean> it3 = LogicEditorActivity.g(c).iterator();
            while (it3.hasNext()) {
                ProjectResourceBean res = it3.next();
                LogicEditorActivity.a(c, res.resName);
            }
            

            Iterator<ProjectResourceBean> it4 = LogicEditorActivity.h(c).iterator();
            while (it4.hasNext()) {
                ProjectResourceBean res = it4.next();
                LogicEditorActivity.b(c, res.resName);
            }
            

            Iterator<ProjectResourceBean> it5 = LogicEditorActivity.i(c).iterator();
            while (it5.hasNext()) {
                ProjectResourceBean res = it5.next();
                LogicEditorActivity.c(c, res.resName);
            }
            

            LogicEditorActivity.b(c, a);
            b.dismiss();
        }
    }
    
    
    public class LogicEditorActivity$38 implements View.OnClickListener {
        
        private final CustomAlertDialog a;
        private final LogicEditorActivity b;
        
        public LogicEditorActivity$38(LogicEditorActivity activity, CustomAlertDialog dialog) {
            this.b = activity;
            this.a = dialog;
        }
        
        @Override
        public void onClick(View v) {

            a.dismiss();
        }
    }
    
    
    public class LogicEditorActivity$39 implements View.OnClickListener {
        
        private final CustomAlertDialog a; // Dialog reference
        private final LogicEditorActivity b; // Outer activity reference
        
        public LogicEditorActivity$39(LogicEditorActivity activity, CustomAlertDialog dialog) {
            this.b = activity;
            this.a = dialog;
        }
        
        @Override
        public void onClick(View v) {

            if (ki.a()) {
                return;
            }
            

            int selectedIndex = b.q.a;
            if (selectedIndex < 0) {
                return;
            }
            

            MoreBlockCollectionBean bean = (MoreBlockCollectionBean) b.r.get(selectedIndex);
            

            LogicEditorActivity.c(b, bean);
            

            a.dismiss();
        }
    }
    
    
    public class LogicEditorActivity$40 implements View.OnClickListener {
        
        private final CustomAlertDialog a; // The dialog to dismiss
        private final LogicEditorActivity b; // Outer activity reference
        
        public LogicEditorActivity$40(LogicEditorActivity activity, CustomAlertDialog dialog) {
            this.b = activity;
            this.a = dialog;
        }
        
        @Override
        public void onClick(View v) {
            a.dismiss(); // Close the dialog
        }
    }
    
    private class LogicEditorActivity$41 implements View.OnClickListener {
        
        final lf a;
        final EditText b;
        final fm c;
        final CustomAlertDialog d;
        final LogicEditorActivity e;
        
        public LogicEditorActivity$41(LogicEditorActivity e, lf a, EditText b, fm c, CustomAlertDialog d) {
            this.e = e;
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }
        
        @Override
        public void onClick(View view) {

            if (a.a()) {

                LogicEditorActivity.a(e, b.getText().toString(), c);
                

                Context context = e.getApplicationContext();
                ki.a(context, b);
                

                d.dismiss();
            }
        }
    }
    

    private class LogicEditorActivity$42 implements View.OnClickListener {
        
        final EditText a;
        final CustomAlertDialog b;
        final LogicEditorActivity c;
        
        public LogicEditorActivity$42(LogicEditorActivity c, EditText a, CustomAlertDialog b) {
            this.c = c;
            this.a = a;
            this.b = b;
        }
        
        @Override
        public void onClick(View view) {

            Context context = c.getApplicationContext();
            ki.a(context, a);
            

            b.dismiss();
        }
    }
    class LogicEditorActivity$43 implements View.OnClickListener {
        
        final RadioGroup a;
        final EditText b;
        final lr c;
        final CustomAlertDialog d;
        final LogicEditorActivity e;
        
        LogicEditorActivity$43(LogicEditorActivity activity, RadioGroup radioGroup, EditText editText, lr lrObj, CustomAlertDialog kdObj) {
            this.e = activity;
            this.a = radioGroup;
            this.b = editText;
            this.c = lrObj;
            this.d = kdObj;
        }
        
        @Override
        public void onClick(View view) {
            int checkedId = a.getCheckedRadioButtonId();
            int value = 1; // default
            

            if (checkedId == com.nexusteam.blacklogics.R.id.rb_boolean) { // 0x7f080399
                value = 0;
            } else if (checkedId == com.nexusteam.blacklogics.R.id.rb_int) { // 0x7f08039b
                value = 1;
            } else if (checkedId == com.nexusteam.blacklogics.R.id.rb_string) { // 0x7f08039f
                value = 2;
            } else if (checkedId == com.nexusteam.blacklogics.R.id.rb_map) { // 0x7f08039c
                value = 3;
            }
            
            String text = b.getText().toString();
            
            if (!c.a()) { // if not valid, just return
                return;
            }
            
            e.a(value, text);

            

            Context context = e.getApplicationContext();
            ki.a(context, b);
            

            d.dismiss();
        }
    }
    
    

    class LogicEditorActivity$44 implements View.OnClickListener {
        
        final EditText a;
        final CustomAlertDialog b;
        final LogicEditorActivity c;
        
        LogicEditorActivity$44(LogicEditorActivity c, EditText a, CustomAlertDialog b) {
            this.c = c;
            this.a = a;
            this.b = b;
        }
        
        @Override
        public void onClick(View view) {

            Context context = c.getApplicationContext();
            ki.a(context, a);
            

            b.dismiss();
        }
    }
    
    public class LogicEditorActivity$45 implements View.OnClickListener {
        
        private final ViewGroup a;
        private final CustomAlertDialog b;
        private final LogicEditorActivity c;
        
        public LogicEditorActivity$45(LogicEditorActivity activity, ViewGroup viewGroup, CustomAlertDialog dialog) {
            this.c = activity;
            this.a = viewGroup;
            this.b = dialog;
        }
        
        @Override
        public void onClick(View v) {
            int childCount = a.getChildCount();
            
            for (int i = 0; i < childCount; i++) {
                RadioButton radio = (RadioButton) a.getChildAt(i);
                
                if (radio.isChecked()) {

                    boolean exists = c.e.a(radio.getText().toString());
                    
                    if (!exists) {

                        lw lwObject = ma.a(c.f);
                        String javaName = c.getJavaName();
                        String radioText = radio.getText().toString();
                        String uniqueName = c.g + "_" + c.h;
                        
                        
                        boolean result = lwObject.a(javaName, radioText, uniqueName);
                        
                        if (!result) {

                            c.b(radio.getText().toString());
                        } else {

                            Context context = c.getApplicationContext();
                            String msg = StringResourceManager.a().a(context, com.nexusteam.blacklogics.R.string.block_fileutil_write_tail);
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } else {

                        Context context = c.getApplicationContext();
                        String msg = StringResourceManager.a().a(context, com.nexusteam.blacklogics.R.string.block_fileutil_write_tail);
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    break;
                }
            }
            

            b.dismiss();
        }
    }
    
    
    
    private void A() {
    CustomAlertDialog var3 = new CustomAlertDialog(this);
    var3.a(StringResourceManager.a().a(this.getApplicationContext(), 
           com.nexusteam.blacklogics.R.string.logic_editor_title_remove_variable));
    var3.a(com.nexusteam.blacklogics.R.drawable.delete_96);
    
    View var2 = ViewHelper.a(this, com.nexusteam.blacklogics.R.layout.property_popup_selector_single);
    ViewGroup var1 = (ViewGroup)var2.findViewById(com.nexusteam.blacklogics.R.id.rg_content);
    

    ArrayList<Pair<Integer, String>> var4 = ma.a(this.f).e(this.getJavaName());
    Iterator var7 = var4.iterator();
    
    while(var7.hasNext()) {
        Pair var5 = (Pair)var7.next();
        RadioButton var6 = this.e((String)var5.second);
        var6.setTag(var5.first);  // type set kiya (0,1,2,3)
        var1.addView(var6);
    }
    

    ArrayList<Pair<Integer, String>> listVars = ma.a(this.f).f(this.getJavaName());
    Iterator listIter = listVars.iterator();
    
    while(listIter.hasNext()) {
        Pair var5 = (Pair)listIter.next();
        RadioButton var6 = this.e((String)var5.second);

        var6.setTag((Integer)var5.first + 3);  
        var1.addView(var6);
    }
    
    var3.a(var2);

    var3.a(StringResourceManager.a().a(this.getApplicationContext(), 
           com.nexusteam.blacklogics.R.string.common_word_remove), 
           new LogicEditorActivity$46(this, var1, var3));  // Naya listener $46
    var3.b(StringResourceManager.a().a(this.getApplicationContext(), 
           com.nexusteam.blacklogics.R.string.common_word_cancel), 
           new LogicEditorActivity$2(this, var3));
    var3.show();
}


class LogicEditorActivity$46 implements View.OnClickListener {
    
    private final ViewGroup a;
    private final CustomAlertDialog b;
    private final LogicEditorActivity c;
    
    public LogicEditorActivity$46(LogicEditorActivity activity, ViewGroup viewGroup, CustomAlertDialog dialog) {
        this.c = activity;
        this.a = viewGroup;
        this.b = dialog;
    }
    
    @Override
    public void onClick(View v) {
        int childCount = a.getChildCount();
        
        for (int i = 0; i < childCount; i++) {
            RadioButton radio = (RadioButton) a.getChildAt(i);
            
            if (radio.isChecked()) {
                int type = (Integer) radio.getTag();  // 0-6
                String name = radio.getText().toString();
                

                if (type <= 3) {

                    ma.a(c.f).f(c.getJavaName(), name);
                } else {

                    ma.a(c.f).g(c.getJavaName(), name);
                }
                

                BlockStorage.removeVariable(c.getJavaName(), type, name);
                
                break;
            }
        }
        

        c.a(0, -1147626);  // variable palette refresh
        b.dismiss();
    }
}
    
    private void f(fn var1) {
        View var4 = ViewHelper.a(this, com.nexusteam.blacklogics.R.layout.color_picker);
        var4.setAnimation(AnimationUtils.loadAnimation(this, com.nexusteam.blacklogics.R.anim.abc_fade_in));
        int var2;
        if (var1.getArgValue() != null && var1.getArgValue().toString().length() > 0 && var1.getArgValue().toString().indexOf("0xFF") == 0) {
            var2 = Color.parseColor(var1.getArgValue().toString().replace("0xFF", "#"));
        } else {
            var2 = 0;
        }
        
        hl var3 = new hl(var4, this, var2, true, false);
        var3.a(new LogicEditorActivity$20(this, var1));
        var3.setAnimationStyle(com.nexusteam.blacklogics.R.anim.abc_fade_in);
        var3.showAtLocation(var4, 17, 0, 0);
    }
    
    private void B() {
        CustomAlertDialog var2 = new CustomAlertDialog(this);
        var2.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_add_new_list));
        var2.a(com.nexusteam.blacklogics.R.drawable.add_96_blue);
        View var1 = ViewHelper.a(this, com.nexusteam.blacklogics.R.layout.logic_popup_add_list);
        RadioGroup var4 = (RadioGroup)var1.findViewById(com.nexusteam.blacklogics.R.id.rg_type);
        EditText var3 = (EditText)var1.findViewById(com.nexusteam.blacklogics.R.id.ed_input);
        ((TextInputLayout)var1.findViewById(com.nexusteam.blacklogics.R.id.ti_input)).setHint(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_hint_enter_variable_name));
        ((TextView)var1.findViewById(com.nexusteam.blacklogics.R.id.rb_int)).setText(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_variable_type_number));
        ((TextView)var1.findViewById(com.nexusteam.blacklogics.R.id.rb_string)).setText(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_variable_type_string));
        ((TextView)var1.findViewById(com.nexusteam.blacklogics.R.id.rb_map)).setText(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_variable_type_map));
        Context var5 = this.K;
        TextInputLayout var7 = (TextInputLayout)var1.findViewById(com.nexusteam.blacklogics.R.id.ti_input);
        String[] var6 = fc.b;
        lr var8 = new lr(var5, var7, var6, fc.c(), ma.a(this.f).b(this.m));
        var3.setPrivateImeOptions("defaultInputmode=english;");
        var2.a(var1);
        var2.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_word_add), new LogicEditorActivity$3(this, var8, var4, var3, var2));
        var2.b(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_word_cancel), new LogicEditorActivity$4(this, var3, var2));
        var2.show();
    }
    
    private void C() {
        CustomAlertDialog var2 = new CustomAlertDialog(this);
        var2.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_remove_list));
        var2.a(com.nexusteam.blacklogics.R.drawable.delete_96);
        View var1 = ViewHelper.a(this, com.nexusteam.blacklogics.R.layout.property_popup_selector_single);
        ViewGroup var3 = (ViewGroup)var1.findViewById(com.nexusteam.blacklogics.R.id.rg_content);
        new ArrayList();
        ArrayList var4 = ma.a(this.f).f(this.getJavaName());
        Iterator var6 = var4.iterator();
        
        while(var6.hasNext()) {
            Pair var5 = (Pair)var6.next();
            RadioButton var7 = this.e((String)var5.second);
            var3.addView(var7);
        }
        
        var2.a(var1);
        var2.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_word_remove), new LogicEditorActivity$5(this, var3, var2));
        var2.b(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_word_cancel), new LogicEditorActivity$6(this, var2));
        var2.show();
    }
    
    private void D() {
        CustomAlertDialog var4 = new CustomAlertDialog(this);
        var4.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_more_block_title_select_more_block));
        var4.a(com.nexusteam.blacklogics.R.drawable.more_block_96dp);
        View var1 = ViewHelper.a(this.getBaseContext(), com.nexusteam.blacklogics.R.layout.manage_collection_popup_import_more_block);
        RecyclerView var3 = (RecyclerView)var1.findViewById(com.nexusteam.blacklogics.R.id.list);
        var3.setHasFixedSize(true);
        LinearLayoutManager var2 = new LinearLayoutManager(this.getBaseContext());
        var3.setLayoutManager(var2);
        this.q = new LogicEditorActivity.a(this);
        var3.setAdapter(this.q);
        this.E();
        var4.a(var1);
        var4.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_word_select), new LogicEditorActivity$39(this, var4));
        var4.b(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_word_cancel), new LogicEditorActivity$40(this, var4));
        var4.show();
    }
    
    private void E() {
        this.r = el.f().g();
        this.q.notifyDataSetChanged();
    }
    
    private View a(String var1, String var2, String var3) {
        fo var4 = this.c.a(var1, var2, var3);
        var4.setTag(var3);
        var4.setClickable(true);
        var4.setOnTouchListener(this);
        return var4;
    }
    
    private View a(String var1, String var2, String var3, String var4) {
        fo var5 = this.c.a(var1, var2, var3, var4);
        var5.setTag(var4);
        var5.setClickable(true);
        var5.setOnTouchListener(this);
        return var5;
    }
    
    private LinearLayout a(String var1, boolean var2) {
        float var3 = ViewHelper.a(this, 1.0F);
        LinearLayout var5 = new LinearLayout(this);
        LayoutParams var6 = new LayoutParams(-1, (int)(60.0F * var3));
        var5.setLayoutParams(var6);
        var5.setGravity(19);
        var5.setOrientation(0);
        TextView var7 = new TextView(this);
        var6 = new LayoutParams(0, -2);
        var6.weight = 1.0F;
        var7.setLayoutParams(var6);
        var7.setText(var1);
        var5.addView(var7);
        ImageView var14 = new ImageView(this);
        var14.setScaleType(ScaleType.CENTER_CROP);
        int var4 = (int)(var3 * 48.0F);
        var14.setLayoutParams(new LayoutParams(var4, var4));
        if (!"NONE".equals(var1)) {
            if (var2) {
                var14.setImageResource(this.getResources().getIdentifier(var1, "drawable", this.getApplicationContext().getPackageName()));
            } else {
                Uri var11;
                if (VERSION.SDK_INT >= 24) {
                    Context var15 = this.K;
                    StringBuilder var8 = new StringBuilder();
                    var8.append(this.getApplicationContext().getPackageName());
                    var8.append(".provider");
                    String var17 = var8.toString();
                    String var9 = this.f;
                    File var10 = new File(ma.c(var9).b(var1));
                    var11 = FileProvider.getUriForFile(var15, var17, var10);
                } else {
                    var11 = Uri.fromFile(new File(ma.c(this.f).b(var1)));
                }
                
                Glide.with(this)
     .load(var11)
     .error(com.nexusteam.blacklogics.R.drawable.ic_remove_grey600_24dp)
     .into(var14);
                
                /*    RequestManager var16 = Glide.with(this);
DrawableTypeRequest var12 = var16.load(var11);
DrawableRequestBuilder var13 = var12.signature(mb.b());
var13 = var13.error(com.nexusteam.internal.R.drawable.ic_remove_grey600_24dp);
var13.into(var14);*/                
            }
            
            var14.setBackgroundColor(-4342339);
        } else {
            var14.setBackgroundColor(-1);
        }
        
        var5.addView(var14);
        return var5;
    }
    
    private ArrayList<BlockBean> a(ArrayList<BlockBean> var1, int var2, int var3, boolean var4) {
        HashMap var8 = new HashMap();
        ArrayList var9 = new ArrayList();
        Iterator var14 = var1.iterator();
        
        BlockBean var10;
        while(var14.hasNext()) {
            var10 = (BlockBean)var14.next();
            if (var10.id != null && !var10.id.equals("")) {
                var10 = var10.clone();
                var9.add(var10);
            }
        }
        
        var14 = var9.iterator();
        
        int var5;
        while(var14.hasNext()) {
            var10 = (BlockBean)var14.next();
            if (Integer.valueOf(var10.id) >= 99000000) {
                Integer var19 = Integer.valueOf(var10.id);
                fq var11 = this.e;
                var5 = var11.a++;
                var8.put(var19, var5);
            } else {
                var8.put(Integer.valueOf(var10.id), Integer.valueOf(var10.id));
            }
        }
        
        var5 = var9.size();
        
        while(true) {
            int var6 = var5 - 1;
            if (var6 < 0) {
                var14 = var9.iterator();
                
                while(var14.hasNext()) {
                    var10 = (BlockBean)var14.next();
                    if (var8.containsKey(Integer.valueOf(var10.id))) {
                        var10.id = String.valueOf(var8.get(Integer.valueOf(var10.id)));
                    } else {
                        var10.id = "";
                    }
                    
                    int var7 = var10.parameters.size();
                    
                    for(var5 = 0; var5 < var7; ++var5) {
                        String var20 = (String)var10.parameters.get(var5);
                        if (var20 != null && var20.length() > 0 && var20.charAt(0) == '@') {
                            var6 = Integer.valueOf(var20.substring(1));
                            if (var8.containsKey(var6)) {
                                var6 = (Integer)var8.get(var6);
                            } else {
                                var6 = 0;
                            }
                            
                            Integer var12 = var6;
                            if (var12 >= 0) {
                                ArrayList var21 = var10.parameters;
                                StringBuilder var13 = new StringBuilder();
                                var13.append('@');
                                var13.append(String.valueOf(var12));
                                var21.set(var5, var13.toString());
                            } else {
                                var10.parameters.set(var5, "");
                            }
                        }
                    }
                    
                    if (var10.subStack1 >= 0 && var8.containsKey(var10.subStack1)) {
                        var10.subStack1 = (Integer)var8.get(var10.subStack1);
                    }
                    
                    if (var10.subStack2 >= 0 && var8.containsKey(var10.subStack2)) {
                        var10.subStack2 = (Integer)var8.get(var10.subStack2);
                    }
                    
                    if (var10.nextBlock >= 0 && var8.containsKey(var10.nextBlock)) {
                        var10.nextBlock = (Integer)var8.get(var10.nextBlock);
                    }
                }
                
                fm var16 = null;
                
                fm var17;
                for(var5 = 0; var5 < var9.size(); var16 = var17) {
                    var10 = (BlockBean)var9.get(var5);
                    var17 = var16;
                    if (var10.id != null) {
                        if (var10.id.equals("")) {
                            var17 = var16;
                        } else {
                            var17 = this.a(var10);
                            if (var5 == 0) {
                                var16 = var17;
                            }
                            
                            this.e.c(var17, var2, var3);
                            var17.setOnTouchListener(this);
                            var17 = var16;
                        }
                    }
                    
                    ++var5;
                }
                
                Iterator var18 = var9.iterator();
                
                while(var18.hasNext()) {
                    var10 = (BlockBean)var18.next();
                    if (var10.id != null && !var10.id.equals("")) {
                        this.a(var10, false);
                    }
                }
                
                if (var16 != null && var4) {
                    var16.c().b();
                    this.e.c();
                }
                
                return var9;
            }
            
            BlockBean var15 = (BlockBean)var9.get(var6);
            var5 = var6;
            if (!this.b(var15)) {
                var9.remove(var6);
                var8.remove(Integer.valueOf(var15.id));
                var5 = var6;
            }
        }
    }
    
    private void a(int var1) {
        int var2;
        LayoutParams var3;
        android.widget.RelativeLayout.LayoutParams var4;
        if (2 == var1) {
            var2 = (int)ViewHelper.a(this, 320.0F);
            var3 = new LayoutParams(var2, -1);
            this.k.setLayoutParams(var3);
            var3 = new LayoutParams(-2, -2);
            var3.gravity = 81;
            var2 = (int)this.getResources().getDimension(com.nexusteam.blacklogics.R.dimen.action_button_margin);
            var3.setMargins(var2, var2, var2, var2);
            this.l.setLayoutParams(var3);
            var4 = new android.widget.RelativeLayout.LayoutParams(-2, -1);
            var4.addRule(10);
            var4.addRule(11);
            var4.topMargin = ky.g(this.K);
            this.j.setOrientation(0);
            this.j.setLayoutParams(var4);
        } else {
            var2 = (int)ViewHelper.a(this, 240.0F);
            var3 = new LayoutParams(-1, var2);
            this.k.setLayoutParams(var3);
            var3 = new LayoutParams(-2, -2);
            var3.gravity = 21;
            var2 = (int)this.getResources().getDimension(com.nexusteam.blacklogics.R.dimen.action_button_margin);
            var3.setMargins(var2, var2, var2, var2);
            this.l.setLayoutParams(var3);
            var4 = new android.widget.RelativeLayout.LayoutParams(-1, -2);
            var4.addRule(9);
            var4.addRule(12);
            this.j.setOrientation(1);
            this.j.setLayoutParams(var4);
        }
        
        this.b(var1);
        this.c(var1);
    }
    
    private void a(fn var1, String var2) {
        CustomAlertDialog var3 = new CustomAlertDialog(this);
        if (var2.equals("property_image")) {
            var3.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_image));
        } else if (var2.equals("property_background_resource")) {
            var3.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_image_background));
        }
        
        var3.a(com.nexusteam.blacklogics.R.drawable.ic_picture_48dp);
        View var5 = ViewHelper.a(this, com.nexusteam.blacklogics.R.layout.property_popup_selector_color);
        RadioGroup var4 = (RadioGroup)var5.findViewById(com.nexusteam.blacklogics.R.id.rg);
        LinearLayout var6 = (LinearLayout)var5.findViewById(com.nexusteam.blacklogics.R.id.content);
        ArrayList var7 = ma.c(this.f).m();
        if (ff.a(this.f) || ff.b(this.f)) {
            if ("property_image" == var2) {
                var7.add(0, "default_image");
            } else if ("property_background_resource" == var2) {
                var7.add(0, "NONE");
            }
        }
        
        Iterator var11 = var7.iterator();
        
        while(var11.hasNext()) {
            String var8 = (String)var11.next();
            RadioButton var9 = this.g(var8);
            var4.addView(var9);
            if (var8.equals(var1.getArgValue())) {
                var9.setChecked(true);
            }
            
            LinearLayout var10;
            if (!ff.a(this.f) && !ff.b(this.f)) {
                var10 = this.a(var8, true);
            } else if (!var8.equals("default_image") && !"NONE".equals(var8)) {
                var10 = this.a(var8, false);
            } else {
                var10 = this.a(var8, true);
            }
            
            var10.setOnClickListener(new LogicEditorActivity$21(this, var6, var4));
            var6.addView(var10);
        }
        
        var3.a(var5);
        var3.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_word_save), new LogicEditorActivity$22(this, var4, var1, var3));
        var3.b(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_word_cancel), new LogicEditorActivity$24(this, var3));
        var3.show();
    }
    
    @Deprecated
    public void showMessage(String _s) {
        Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
    }
    
    
    private void a(BlockBean var1, boolean var2) {
        fm var10 = this.e.c(var1.id);
        if (var10 != null) {
            var10.m = -1;
            var10.n = -1;
            var10.l = -1;
            int var5 = var1.parameters.size();
            
            fm var18;
            for(int var4 = 0; var4 < var5; ++var4) {
                String var11 = (String)var1.parameters.get(var4);
                if (var11 != null) {
                    int var3 = var11.length();
                    boolean var7 = true;
                    if (var3 > 0 && var11.charAt(0) == '@') {
                        var3 = Integer.valueOf(var11.substring(1));
                        if (var3 > 0) {
                            var18 = this.e.a(var3);
                            if (var18 != null) {
                                var10.a((fo)var10.c.get(var4), var18);
                            }
                        }
                    } else if (var10.c.get(var4) instanceof fn) {
                        fn var12 = (fn)var10.c.get(var4);
                        String var13 = this.getJavaName();
                        String var9 = getXmlName();
                        String var8 = var9;
                        if (this.h.equals("onBindCustomView")) {
                            ViewBean var14 = ma.a(this.f).e(getXmlName(), this.g);
                            var8 = var9;
                            if (var14.customView != null) {
                                var8 = ProjectFileBean.getXmlName(var14.customView);
                            }
                        }
                        
                        boolean var6 = var7;
                        if (var11.length() > 0) {
                            var6 = var7;
                            if (var12.w == "m") {
                                byte var16;
                                label275: {
                                    var9 = var12.x;
                                    switch(var9.hashCode()) {
                                        case -2004438503:
                                        if (var9.equals("spinner")) {
                                            var16 = 15;
                                            break label275;
                                        }
                                        break;
                                        case -1811660373:
                                        if (var9.equals("soundpool")) {
                                            var16 = 27;
                                            break label275;
                                        }
                                        break;
                                        case -1677313857:
                                        if (var9.equals("requestnetwork")) {
                                            var16 = 34;
                                            break label275;
                                        }
                                        break;
                                        case -1655966961:
                                        if (var9.equals("activity")) {
                                            var16 = 41;
                                            break label275;
                                        }
                                        break;
                                        case -1558241498:
                                        if (var9.equals("objectanimator")) {
                                            var16 = 28;
                                            break label275;
                                        }
                                        break;
                                        case -1421968056:
                                        if (var9.equals("adview")) {
                                            var16 = 13;
                                            break label275;
                                        }
                                        break;
                                        case -1332085432:
                                        if (var9.equals("dialog")) {
                                            var16 = 25;
                                            break label275;
                                        }
                                        break;
                                        case -1197746358:
                                        if (var9.equals("texttospeech")) {
                                            var16 = 35;
                                            break label275;
                                        }
                                        break;
                                        case -1183762788:
                                        if (var9.equals("intent")) {
                                            var16 = 20;
                                            break label275;
                                        }
                                        break;
                                        case -1002626734:
                                        if (var9.equals("textview")) {
                                            var16 = 8;
                                            break label275;
                                        }
                                        break;
                                        case -961721768:
                                        if (var9.equals("locationmanager")) {
                                            var16 = 38;
                                            break label275;
                                        }
                                        break;
                                        case -889473228:
                                        if (var9.equals("switch")) {
                                            var16 = 17;
                                            break label275;
                                        }
                                        break;
                                        case -877150592:
                                        if (var9.equals("imageview")) {
                                            var16 = 10;
                                            break label275;
                                        }
                                        break;
                                        case -823676088:
                                        if (var9.equals("varInt")) {
                                            var16 = 0;
                                            break label275;
                                        }
                                        break;
                                        case -823666294:
                                        if (var9.equals("varStr")) {
                                            var16 = 2;
                                            break label275;
                                        }
                                        break;
                                        case -563351033:
                                        if (var9.equals("firebase")) {
                                            var16 = 29;
                                            break label275;
                                        }
                                        break;
                                        case -351639837:
                                        if (var9.equals("calendarview")) {
                                            var16 = 12;
                                            break label275;
                                        }
                                        break;
                                        case -341064690:
                                        if (var9.equals("resource")) {
                                            var16 = 40;
                                            break label275;
                                        }
                                        break;
                                        case -290065014:
                                        if (var9.equals("speechtotext")) {
                                            var16 = 36;
                                            break label275;
                                        }
                                        break;
                                        case -178324674:
                                        if (var9.equals("calendar")) {
                                            var16 = 22;
                                            break label275;
                                        }
                                        break;
                                        case 3143036:
                                        if (var9.equals("file")) {
                                            var16 = 21;
                                            break label275;
                                        }
                                        break;
                                        case 3322014:
                                        if (var9.equals("list")) {
                                            var16 = 6;
                                            break label275;
                                        }
                                        break;
                                        case 3619493:
                                        if (var9.equals("view")) {
                                            var16 = 7;
                                            break label275;
                                        }
                                        break;
                                        case 62092335:
                                        if (var9.equals("firebaseauth")) {
                                            var16 = 30;
                                            break label275;
                                        }
                                        break;
                                        case 109627663:
                                        if (var9.equals("sound")) {
                                            var16 = 42;
                                            break label275;
                                        }
                                        break;
                                        case 110364485:
                                        if (var9.equals("timer")) {
                                            var16 = 23;
                                            break label275;
                                        }
                                        break;
                                        case 181944945:
                                        if (var9.equals("listInt")) {
                                            var16 = 3;
                                            break label275;
                                        }
                                        break;
                                        case 181948382:
                                        if (var9.equals("listMap")) {
                                            var16 = 5;
                                            break label275;
                                        }
                                        break;
                                        case 181954739:
                                        if (var9.equals("listStr")) {
                                            var16 = 4;
                                            break label275;
                                        }
                                        break;
                                        case 235637425:
                                        if (var9.equals("varBool")) {
                                            var16 = 1;
                                            break label275;
                                        }
                                        break;
                                        case 325741829:
                                        if (var9.equals("gyroscope")) {
                                            var16 = 32;
                                            break label275;
                                        }
                                        break;
                                        case 485199813:
                                        if (var9.equals("mediaplayer")) {
                                            var16 = 26;
                                            break label275;
                                        }
                                        break;
                                        case 690484860:
                                        if (var9.equals("bluetoothconnect")) {
                                            var16 = 37;
                                            break label275;
                                        }
                                        break;
                                        case 837734913:
                                        if (var9.equals("mapview")) {
                                            var16 = 19;
                                            break label275;
                                        }
                                        break;
                                        case 1105738265:
                                        if (var9.equals("vibrator")) {
                                            var16 = 24;
                                            break label275;
                                        }
                                        break;
                                        case 1131540166:
                                        if (var9.equals("progressbar")) {
                                            var16 = 18;
                                            break label275;
                                        }
                                        break;
                                        case 1224424441:
                                        if (var9.equals("webview")) {
                                            var16 = 16;
                                            break label275;
                                        }
                                        break;
                                        case 1234536982:
                                        if (var9.equals("resource_bg")) {
                                            var16 = 39;
                                            break label275;
                                        }
                                        break;
                                        case 1322145871:
                                        if (var9.equals("interstitialad")) {
                                            var16 = 33;
                                            break label275;
                                        }
                                        break;
                                        case 1346661443:
                                        if (var9.equals("listview")) {
                                            var16 = 14;
                                            break label275;
                                        }
                                        break;
                                        case 1536891843:
                                        if (var9.equals("checkbox")) {
                                            var16 = 9;
                                            break label275;
                                        }
                                        break;
                                        case 1719159444:
                                        if (var9.equals("firebasestorage")) {
                                            var16 = 31;
                                            break label275;
                                        }
                                        break;
                                        case 1971813019:
                                        if (var9.equals("seekbar")) {
                                            var16 = 11;
                                            break label275;
                                        }
                                    }
                                    
                                    var16 = -1;
                                }
                                
                                Iterator var17;
                                label227:
                                switch(var16) {
                                    case 0:
                                    var6 = ma.a(this.f).d(var13, 1, var11);
                                    break;
                                    case 1:
                                    var6 = ma.a(this.f).d(var13, 0, var11);
                                    break;
                                    case 2:
                                    var6 = ma.a(this.f).d(var13, 2, var11);
                                    break;
                                    case 3:
                                    var6 = ma.a(this.f).e(var13, 1, var11);
                                    break;
                                    case 4:
                                    var6 = ma.a(this.f).e(var13, 2, var11);
                                    break;
                                    case 5:
                                    var6 = ma.a(this.f).e(var13, 3, var11);
                                    break;
                                    case 6:
                                    var7 = ma.a(this.f).e(var13, 1, var11);
                                    var6 = var7;
                                    if (!var7) {
                                        var6 = ma.a(this.f).e(var13, 2, var11);
                                    }
                                    
                                    var7 = var6;
                                    var6 = var6;
                                    if (!var7) {
                                        var6 = ma.a(this.f).e(var13, 3, var11);
                                    }
                                    break;
                                    case 7:
                                    var6 = ma.a(this.f).n(var8, var11);
                                    break;
                                    case 8:
                                    var6 = ma.a(this.f).o(var8, var11);
                                    break;
                                    case 9:
                                    var6 = ma.a(this.f).p(var8, var11);
                                    break;
                                    case 10:
                                    var6 = ma.a(this.f).f(var8, 6, var11);
                                    break;
                                    case 11:
                                    var6 = ma.a(this.f).f(var8, 14, var11);
                                    break;
                                    case 12:
                                    var6 = ma.a(this.f).f(var8, 15, var11);
                                    break;
                                    case 13:
                                    var6 = ma.a(this.f).f(var8, 17, var11);
                                    break;
                                    case 14:
                                    var6 = ma.a(this.f).f(var8, 9, var11);
                                    break;
                                    case 15:
                                    var6 = ma.a(this.f).f(var8, 10, var11);
                                    break;
                                    case 16:
                                    var6 = ma.a(this.f).f(var8, 7, var11);
                                    break;
                                    case 17:
                                    var6 = ma.a(this.f).f(var8, 13, var11);
                                    break;
                                    case 18:
                                    var6 = ma.a(this.f).f(var8, 8, var11);
                                    break;
                                    case 19:
                                    var6 = ma.a(this.f).f(var8, 18, var11);
                                    break;
                                    case 20:
                                    var6 = ma.a(this.f).g(var13, 1, var11);
                                    break;
                                    case 21:
                                    var6 = ma.a(this.f).g(var13, 2, var11);
                                    break;
                                    case 22:
                                    var6 = ma.a(this.f).g(var13, 3, var11);
                                    break;
                                    case 23:
                                    var6 = ma.a(this.f).g(var13, 5, var11);
                                    break;
                                    case 24:
                                    var6 = ma.a(this.f).g(var13, 4, var11);
                                    break;
                                    case 25:
                                    var6 = ma.a(this.f).g(var13, 7, var11);
                                    break;
                                    case 26:
                                    var6 = ma.a(this.f).g(var13, 8, var11);
                                    break;
                                    case 27:
                                    var6 = ma.a(this.f).g(var13, 9, var11);
                                    break;
                                    case 28:
                                    var6 = ma.a(this.f).g(var13, 10, var11);
                                    break;
                                    case 29:
                                    var6 = ma.a(this.f).g(var13, 6, var11);
                                    break;
                                    case 30:
                                    var6 = ma.a(this.f).g(var13, 12, var11);
                                    break;
                                    case 31:
                                    var6 = ma.a(this.f).g(var13, 14, var11);
                                    break;
                                    case 32:
                                    var6 = ma.a(this.f).g(var13, 11, var11);
                                    break;
                                    case 33:
                                    var6 = ma.a(this.f).g(var13, 13, var11);
                                    break;
                                    case 34:
                                    var6 = ma.a(this.f).g(var13, 17, var11);
                                    break;
                                    case 35:
                                    var6 = ma.a(this.f).g(var13, 18, var11);
                                    break;
                                    case 36:
                                    var6 = ma.a(this.f).g(var13, 19, var11);
                                    break;
                                    case 37:
                                    var6 = ma.a(this.f).g(var13, 20, var11);
                                    break;
                                    case 38:
                                    var6 = ma.a(this.f).g(var13, 21, var11);
                                    break;
                                    case 39:
                                    case 40:
                                    var17 = ma.c(this.f).m().iterator();
                                    var7 = false;
                                    
                                    while(true) {
                                        var6 = var7;
                                        if (!var17.hasNext()) {
                                            break label227;
                                        }
                                        
                                        var9 = (String)var17.next();
                                        if (var11.equals(var9)) {
                                            var7 = true;
                                        }
                                    }
                                    case 41:
                                    var17 = ma.b(this.f).k().iterator();
                                    var7 = false;
                                    
                                    while(true) {
                                        var6 = var7;
                                        if (!var17.hasNext()) {
                                            break label227;
                                        }
                                        
                                        var9 = (String)var17.next();
                                        var9 = var9.substring(0, var9.indexOf(".java"));
                                        if (var11.equals(var9)) {
                                            var7 = true;
                                        }
                                    }
                                    case 42:
                                    var17 = ma.c(this.f).n().iterator();
                                    var7 = false;
                                    
                                    while(true) {
                                        var6 = var7;
                                        if (!var17.hasNext()) {
                                            break label227;
                                        }
                                        
                                        var9 = (String)var17.next();
                                        if (var11.equals(var9)) {
                                            var7 = true;
                                        }
                                    }
                                    default:
                                    var6 = true;
                                }
                            }
                        }
                        
                        if (var6) {
                            var12.setArgValue(var11);
                            var10.g();
                        }
                    }
                }
            }
            
            if (var1.subStack1 >= 0) {
                var18 = this.e.a(var1.subStack1);
                if (var18 != null) {
                    var10.d(var18);
                }
            }
            
            if (var1.subStack2 >= 0) {
                var18 = this.e.a(var1.subStack2);
                if (var18 != null) {
                    var10.e(var18);
                }
            }
            
            if (var1.nextBlock >= 0) {
                fm var15 = this.e.a(var1.nextBlock);
                if (var15 != null) {
                    var10.a(var15);
                }
            }
            
            var10.g();
            if (var2) {
                var10.c().b();
                this.e.c();
            }
            
        }
    }
    
    private void a(MoreBlockCollectionBean var1) {
        this.b(var1);
    }
    

    static void a(LogicEditorActivity var0, MoreBlockCollectionBean var1) {
        var0.d(var1);
    }
    

    static void a(LogicEditorActivity var0, String var1) {
        var0.m(var1);
    }
    

    static void a(LogicEditorActivity var0, String var1, fm var2) {
        var0.a(var1, var2);
    }
    
    private void a(String var1, int var2) {
        this.c.a(var1, var2);
    }
    
    private void a(String var1, fm var2) {
        ArrayList var3 = var2.getAllChildren();
        ArrayList var11 = new ArrayList();
        
        BlockBean var4;
        label53:
        for(Iterator var12 = var3.iterator(); var12.hasNext(); var11.add(var4)) {
            fm var5 = (fm)var12.next();
            var4 = new BlockBean();
            BlockBean var13 = var5.getBean();
            var4.copy(var13);
            var4.id = String.format("99%06d", Integer.valueOf(var13.id));
            if (var13.subStack1 > 0) {
                var4.subStack1 = var13.subStack1 + 99000000;
            }
            
            if (var13.subStack2 > 0) {
                var4.subStack2 = var13.subStack2 + 99000000;
            }
            
            if (var13.nextBlock > 0) {
                var4.nextBlock = var13.nextBlock + 99000000;
            }
            
            var4.parameters.clear();
            Iterator var14 = var13.parameters.iterator();
            
            while(true) {
                while(true) {
                    if (!var14.hasNext()) {
                        continue label53;
                    }
                    
                    String var6 = (String)var14.next();
                    if (var6.length() > 1 && var6.charAt(0) == '@') {
                        var6 = String.format("99%06d", Integer.valueOf(var6.substring(1)));
                        ArrayList var8 = var4.parameters;
                        StringBuilder var7 = new StringBuilder();
                        var7.append('@');
                        var7.append(var6);
                        var8.add(var7.toString());
                    } else {
                        var4.parameters.add(var6);
                    }
                }
            }
        }
        
        try {
            ei.f().a(var1, var11, true);
            View var10 = this.o.a(var1, var11);
            var10.setOnTouchListener(this);
        } catch (jg var9) {
            var9.printStackTrace();
        }
        
    }
    
    private boolean a(float var1, float var2) {
        return this.n.a(var1, var2);
    }
    

    static boolean a(LogicEditorActivity var0) {
        return var0.T;
    }
    
    private View b(String var1, String var2) {
        fo var3 = this.c.a("", var1, var2);
        var3.setTag(var2);
        var3.setClickable(true);
        var3.setOnTouchListener(this);
        return var3;
    }
    
    private void b(int var1) {
        if (2 == var1) {
            if (this.T) {
                this.j.setTranslationX(0.0F);
                this.j.setTranslationY(0.0F);
            } else {
                this.j.setTranslationX((float)((int)ViewHelper.a(this, 320.0F)));
                this.j.setTranslationY(0.0F);
            }
        } else if (this.T) {
            this.j.setTranslationX(0.0F);
            this.j.setTranslationY(0.0F);
        } else {
            this.j.setTranslationX(0.0F);
            this.j.setTranslationY((float)((int)ViewHelper.a(this, 240.0F)));
        }
        
        if (2 == var1) {
            this.Q = ObjectAnimator.ofFloat(this.j, "TranslationX", new float[]{0.0F});
            this.R = ObjectAnimator.ofFloat(this.j, "TranslationX", new float[]{(float)((int)ViewHelper.a(this, 320.0F))});
        } else {
            this.Q = ObjectAnimator.ofFloat(this.j, "TranslationY", new float[]{0.0F});
            this.R = ObjectAnimator.ofFloat(this.j, "TranslationY", new float[]{(float)((int)ViewHelper.a(this, 240.0F))});
        }
        
        this.Q.setDuration(500L);
        this.Q.setInterpolator(new DecelerateInterpolator());
        this.R.setDuration(300L);
        this.R.setInterpolator(new DecelerateInterpolator());
        this.S = true;
    }
    
    private void b(fm var1) {
        this.z = null;
        this.B = -1;
        this.A = 0;
        this.C = new int[2];
        var1.getLocationOnScreen(this.C);
        if (var1.ac != null) {
            this.z = var1.ac;
        }
        
        if (this.z != null) {
            if (this.z.l == (Integer)var1.getTag()) {
                this.A = 0;
            } else if (this.z.m == (Integer)var1.getTag()) {
                this.A = 2;
            } else if (this.z.n == (Integer)var1.getTag()) {
                this.A = 3;
            } else if (this.z.c.contains(var1)) {
                this.A = 5;
                this.B = this.z.c.indexOf(var1);
            }
            
        }
    }
    
    private void b(MoreBlockCollectionBean var1) {
        String var6 = var1.spec;
        boolean var4 = var6.contains(" ");
        boolean var3 = false;
        String var5 = var6;
        if (var4) {
            var5 = var6.substring(0, var6.indexOf(32));
        }
        
        ArrayList var8 = ma.a(this.f).h(this.getJavaName());
        Iterator var9 = var8.iterator();
        
        boolean var2;
        while(true) {
            var2 = var3;
            if (!var9.hasNext()) {
                break;
            }
            
            Pair var7 = (Pair)var9.next();
            if (((String)var7.first).equals(var5)) {
                var2 = true;
                break;
            }
        }
        
        if (!var2) {
            this.d(var1);
        } else {
            this.c(var1);
        }
        
    }
    

    static void b(LogicEditorActivity var0, MoreBlockCollectionBean var1) {
        var0.f(var1);
    }
    

    static void b(LogicEditorActivity var0, String var1) {
        var0.n(var1);
    }
    
    private void b(boolean var1) {
        this.n.b(false);
        this.n.c(false);
        this.n.d(false);
        this.n.e(false);
        if (!this.Y) {
            this.u();
        }
        
        if (this.Z != var1) {
            this.Z = var1;
            this.v();
            if (var1) {
                this.W.start();
            } else {
                this.X.start();
            }
            
        }
    }
    
    private boolean b(float var1, float var2) {
        return this.n.b(var1, var2);
    }
    
    private boolean b(BlockBean var1) {
        String var6;
        if (var1.opCode.equals("getArg")) {
            int var2 = 0;
            
            boolean var3;
            boolean var4;
            for(var4 = false; var2 < this.e.getRoot().c.size(); var4 = var3) {
                View var7 = (View)this.e.getRoot().c.get(var2);
                var3 = var4;
                if (var7 instanceof fm) {
                    var6 = var1.type;
                    fm var12 = (fm)var7;
                    var3 = var4;
                    if (var6.equals(var12.w)) {
                        var3 = var4;
                        if (var1.spec.equals(var12.a)) {
                            var3 = true;
                        }
                    }
                }
                
                ++var2;
            }
            
            if (!var4) {
                return false;
            }
        }
        
        if (var1.opCode.equals("definedFunc")) {
            ArrayList var10 = ma.a(this.f).h(this.getJavaName());
            Iterator var11 = var10.iterator();
            boolean var9 = false;
            
            while(var11.hasNext()) {
                Pair var13 = (Pair)var11.next();
                if (var1.spec.equals(var13.second)) {
                    var9 = true;
                }
            }
            
            if (!var9) {
                return false;
            }
        }
        
        if (var1.opCode.equals("getVar")) {
            boolean var5;
            if (var1.type.equals("v")) {
                if (!this.h.equals("onBindCustomView")) {
                    var5 = ma.a(this.f).f(getXmlName(), ViewBean.getViewTypeByTypeName(var1.typeName), var1.spec);
                } else {
                    var6 = "";
                    ViewBean var14 = ma.a(this.f).e(getXmlName(), this.g);
                    if (var14.customView != null) {
                        var6 = ProjectFileBean.getXmlName(var14.customView);
                    }
                    
                    var5 = ma.a(this.f).f(var6, ViewBean.getViewTypeByTypeName(var1.typeName), var1.spec);
                }
            } else if (var1.type.equals("p")) {
                var5 = ma.a(this.f).g(this.getJavaName(), ComponentBean.getComponentTypeByTypeName(var1.typeName), var1.spec);
            } else {
                label133: {
                    if (var1.type.equals("l")) {
                        if (var1.typeName.equals("List Number")) {
                            var5 = ma.a(this.f).e(this.getJavaName(), 1, var1.spec);
                            break label133;
                        }
                        
                        if (var1.typeName.equals("List String")) {
                            var5 = ma.a(this.f).e(this.getJavaName(), 2, var1.spec);
                            break label133;
                        }
                        
                        if (var1.typeName.equals("List Map")) {
                            var5 = ma.a(this.f).e(this.getJavaName(), 3, var1.spec);
                            break label133;
                        }
                        
                        if (var1.typeName.equals("List") && (ma.a(this.f).e(this.getJavaName(), 1, var1.spec) || ma.a(this.f).e(this.getJavaName(), 2, var1.spec) || ma.a(this.f).e(this.getJavaName(), 3, var1.spec))) {
                            var5 = true;
                            break label133;
                        }
                    } else {
                        if (var1.type.equals("b")) {
                            var5 = ma.a(this.f).d(this.getJavaName(), 0, var1.spec);
                            break label133;
                        }
                        
                        if (var1.type.equals("d")) {
                            var5 = ma.a(this.f).d(this.getJavaName(), 1, var1.spec);
                            break label133;
                        }
                        
                        if (var1.type.equals("s")) {
                            var5 = ma.a(this.f).d(this.getJavaName(), 2, var1.spec);
                            break label133;
                        }
                        
                        if (var1.type.equals("a")) {
                            var5 = ma.a(this.f).d(this.getJavaName(), 3, var1.spec);
                            break label133;
                        }
                    }
                    
                    var5 = false;
                }
            }
            
            if (!var5) {
                return false;
            }
        }
        
        if (var1.opCode.equals("viewOnClick")) {
            String var8 = this.h;
            if (!var8.equals("onBindCustomView")) {
                return false;
            }
        }
        
        return true;
    }
    
    private void c(int var1) {
        boolean var5 = this.T;
        byte var3 = -1;
        LayoutParams var6;
        if (var5) {
            int var2 = this.getResources().getDisplayMetrics().widthPixels;
            int var4 = this.getResources().getDisplayMetrics().heightPixels;
            if (var2 <= var4) {
                var2 = var4;
            }
            
            if (2 == var1) {
                var2 -= (int)ViewHelper.a(this, 320.0F);
                var1 = var3;
            } else {
                var1 = ky.g(this.K);
                int var7 = ky.h(this.K);
                var1 = var2 - var1 - var7 - (int)ViewHelper.a(this, 240.0F);
                var2 = -1;
            }
            
            var6 = new LayoutParams(var2, var1);
            this.d.setLayoutParams(var6);
            this.d.requestLayout();
        } else {
            var6 = new LayoutParams(-1, -1);
            this.d.setLayoutParams(var6);
            this.d.requestLayout();
        }
        
    }
    
    private void c(int var1, String var2) {
        if (this.ae == null) {
            this.ae = new ArrayList();
        }
        
        ArrayList var5 = ma.a(this.f).e(this.getJavaName());
        Iterator var6 = var5.iterator();
        
        Pair var7;
        while(var6.hasNext()) {
            var7 = (Pair)var6.next();
            if ((Integer)var7.first == var1 && ((String)var7.second).equals(var2)) {
                return;
            }
        }
        
        boolean var4 = false;
        var6 = this.ae.iterator();
        
        boolean var3;
        while(true) {
            var3 = var4;
            if (!var6.hasNext()) {
                break;
            }
            
            var7 = (Pair)var6.next();
            if ((Integer)var7.first == var1 && ((String)var7.second).equals(var2)) {
                var3 = true;
                break;
            }
        }
        
        if (!var3) {
            this.ae.add(new Pair(var1, var2));
        }
        
    }
    
    private void c(fm var1) {
        CustomAlertDialog var2 = new CustomAlertDialog(this);
        var2.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_block_favorites_save_title));
        var2.a(com.nexusteam.blacklogics.R.drawable.ic_bookmark_red_48dp);
        View var3 = ViewHelper.a(this, com.nexusteam.blacklogics.R.layout.property_popup_save_to_favorite);
        ((TextView)var3.findViewById(com.nexusteam.blacklogics.R.id.tv_favorites_guide)).setText(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_block_favorites_save_guide));
        EditText var4 = (EditText)var3.findViewById(com.nexusteam.blacklogics.R.id.ed_input);
        var4.setPrivateImeOptions("defaultInputmode=english;");
        var4.setLines(1);
        var4.setInputType(524289);
        var4.setImeOptions(6);
        lf var5 = new lf(this, (TextInputLayout)var3.findViewById(com.nexusteam.blacklogics.R.id.ti_input), ei.f().h());
        var2.a(var3);
        var2.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_word_save), new LogicEditorActivity$41(this, var5, var4, var1, var2));
        var2.b(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_word_cancel), new LogicEditorActivity$42(this, var4, var2));
        var2.show();
    }
    
    private void c(MoreBlockCollectionBean var1) {
        CustomAlertDialog var3 = new CustomAlertDialog(this);
        var3.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_more_block_title_change_block_name));
        var3.a(com.nexusteam.blacklogics.R.drawable.more_block_96dp);
        View var2 = ViewHelper.a(this.getBaseContext(), com.nexusteam.blacklogics.R.layout.property_popup_save_to_favorite);
        ((TextView)var2.findViewById(com.nexusteam.blacklogics.R.id.tv_favorites_guide)).setText(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_more_block_desc_change_block_name));
        EditText var4 = (EditText)var2.findViewById(com.nexusteam.blacklogics.R.id.ed_input);
        var4.setPrivateImeOptions("defaultInputmode=english;");
        var4.setLines(1);
        var4.setInputType(524289);
        var4.setImeOptions(6);
        lr var5 = new lr(this.getBaseContext(), (TextInputLayout)var2.findViewById(com.nexusteam.blacklogics.R.id.ti_input), fc.b, fc.c(), ma.a(this.f).b(this.m));
        var3.a(var2);
        var3.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_word_save), new LogicEditorActivity$35(this, var5, var1, var4, var3));
        var3.b(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_word_cancel), new LogicEditorActivity$36(this, var4, var3));
        var3.show();
    }
    

    static void c(LogicEditorActivity var0, MoreBlockCollectionBean var1) {
        var0.a(var1);
    }
    

    static void c(LogicEditorActivity var0, String var1) {
        var0.o(var1);
    }
    
    private void c(String var1, String var2) {
        TextView var3 = this.c.a(var1);
        var3.setTag(var2);
        var3.setSoundEffectsEnabled(true);
        var3.setOnClickListener(this);
    }
    
    private void c(boolean var1) {
        this.n.a(var1);
    }
    
    private boolean c(float var1, float var2) {
        return this.n.c(var1, var2);
    }
    
    private RadioButton d(String var1, String var2) {
        RadioButton var4 = new RadioButton(this);
        StringBuilder var5 = new StringBuilder();
        var5.append(var1);
        var5.append(" : ");
        var5.append(var2);
        var4.setText(var5.toString());
        var4.setTag(var2);
        float var3 = ViewHelper.a(this, 1.0F);
        LayoutParams var6 = new LayoutParams(-1, (int)(var3 * 40.0F));
        var4.setGravity(19);
        var4.setLayoutParams(var6);
        return var4;
    }
    
    private void d(int var1, String var2) {
        if (this.af == null) {
            this.af = new ArrayList();
        }
        
        ArrayList var5 = ma.a(this.f).f(this.getJavaName());
        Iterator var6 = var5.iterator();
        
        while(var6.hasNext()) {
            Pair var7 = (Pair)var6.next();
            if ((Integer)var7.first == var1 && ((String)var7.second).equals(var2)) {
                return;
            }
        }
        
        boolean var4 = false;
        Iterator var8 = this.af.iterator();
        
        boolean var3;
        while(true) {
            var3 = var4;
            if (!var8.hasNext()) {
                break;
            }
            
            Pair var9 = (Pair)var8.next();
            if ((Integer)var9.first == var1 && ((String)var9.second).equals(var2)) {
                var3 = true;
                break;
            }
        }
        
        if (!var3) {
            this.af.add(new Pair(var1, var2));
        }
        
    }
    
    private void d(MoreBlockCollectionBean var1) {
        this.ae = new ArrayList();
        this.af = new ArrayList();
        this.ag = new ArrayList();
        this.ah = new ArrayList();
        this.ai = new ArrayList();
        Iterator var7 = var1.blocks.iterator();
        
        while(var7.hasNext()) {
            BlockBean var4 = (BlockBean)var7.next();
            if (var4.opCode.equals("getVar")) {
                if (var4.type.equals("b")) {
                    this.c(0, var4.spec);
                } else if (var4.type.equals("d")) {
                    this.c(1, var4.spec);
                } else if (var4.type.equals("s")) {
                    this.c(2, var4.spec);
                } else if (var4.type.equals("a")) {
                    this.c(3, var4.spec);
                } else if (var4.type.equals("l")) {
                    if (var4.typeName.equals("List Number")) {
                        this.d(1, var4.spec);
                    } else if (var4.typeName.equals("List String")) {
                        this.d(2, var4.spec);
                    } else if (var4.typeName.equals("List Map")) {
                        this.d(3, var4.spec);
                    }
                }
            }
            
            ArrayList var3 = var4.getParamClassInfo();
            if (var3.size() > 0) {
                for(int var2 = 0; var2 < var3.size(); ++var2) {
                    hc var6 = (hc)var3.get(var2);
                    String var5 = (String)var4.parameters.get(var2);
                    if (var5.length() > 0 && var5.charAt(0) != '@') {
                        if (var6.b("boolean.SelectBoolean")) {
                            this.c(0, var5);
                        } else if (var6.b("double.SelectDouble")) {
                            this.c(1, var5);
                        } else if (var6.b("String.SelectString")) {
                            this.c(2, var5);
                        } else if (var6.b("Map")) {
                            this.c(3, var5);
                        } else if (var6.b("ListInt")) {
                            this.d(1, var5);
                        } else if (var6.b("ListString")) {
                            this.d(2, var5);
                        } else if (var6.b("ListMap")) {
                            this.d(3, var5);
                        } else if (!var6.b("resource_bg") && !var6.b("resource")) {
                            if (var6.b("sound")) {
                                this.k(var5);
                            } else if (var6.b("font")) {
                                this.l(var5);
                            }
                        } else {
                            this.j(var5);
                        }
                    }
                }
            }
        }
        
        if (this.ae.size() <= 0 && this.af.size() <= 0 && this.ag.size() <= 0 && this.ah.size() <= 0 && this.ai.size() <= 0) {
            this.f(var1);
        } else {
            this.e(var1);
        }
        
    }
    
    private void d(boolean var1) {
        this.n.b(var1);
    }
    
    private boolean d(float var1, float var2) {
        return this.n.d(var1, var2);
    }
    
    private RadioButton e(String var1) {
        RadioButton var2 = new RadioButton(this);
        var2.setText(var1);
        LayoutParams var3 = new LayoutParams(-1, -2);
        var3.topMargin = (int)ViewHelper.a(this.getApplicationContext(), 4.0F);
        var3.bottomMargin = (int)ViewHelper.a(this.getApplicationContext(), 4.0F);
        var2.setGravity(19);
        var2.setLayoutParams(var3);
        return var2;
    }
    

    static ArrayList e(LogicEditorActivity var0) {
        return var0.ae;
    }
    
    private void e(MoreBlockCollectionBean var1) {
        CustomAlertDialog var2 = new CustomAlertDialog(this);
        var2.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_more_block_title_add_variable_resource));
        var2.a(com.nexusteam.blacklogics.R.drawable.break_warning_96_red);
        var2.b(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_more_block_desc_add_variable_resource));
        var2.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_word_continue), new LogicEditorActivity$37(this, var1, var2));
        var2.b(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_word_cancel), new LogicEditorActivity$38(this, var2));
        var2.show();
    }
    
    private void e(boolean var1) {
        this.n.c(var1);
    }
    

    static ArrayList f(LogicEditorActivity var0) {
        return var0.af;
    }
    
    private void f(MoreBlockCollectionBean var1) {
        String var3 = var1.spec;
        String var2;
        if (var3.contains(" ")) {
            var2 = var3.substring(0, var3.indexOf(32));
        } else {
            var2 = var3;
        }
        
        ma.a(this.f).c(this.getJavaName(), var2, var3);
        lw var6 = ma.a(this.f);
        String var5 = this.getJavaName();
        StringBuilder var4 = new StringBuilder();
        var4.append(var2);
        var4.append("_");
        var4.append("moreBlock");
        var6.a(var5, var4.toString(), var1.blocks);
        ke.a(this.getApplicationContext(), StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_message_complete_save), 0).show();
        this.a(8, -7711273);
    }
    
    private void f(String var1) {
        CustomAlertDialog var2 = new CustomAlertDialog(this);
        var2.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_block_favorites_delete_title));
        var2.a(com.nexusteam.blacklogics.R.drawable.high_priority_96_red);
        var2.b(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_block_favorites_delete_message));
        var2.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_word_delete), new LogicEditorActivity$7(this, var1, var2));
        var2.b(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_word_cancel), new LogicEditorActivity$8(this, var2));
        var2.show();
    }
    
    private void f(boolean var1) {
        this.n.d(var1);
    }
    
    private RadioButton g(String var1) {
        RadioButton var3 = new RadioButton(this);
        var3.setText("");
        var3.setTag(var1);
        float var2 = ViewHelper.a(this, 1.0F);
        LayoutParams var4 = new LayoutParams(-2, (int)(var2 * 60.0F));
        var3.setGravity(19);
        var3.setLayoutParams(var4);
        return var3;
    }
    

    static ArrayList g(LogicEditorActivity var0) {
        return var0.ag;
    }
    
    private void g() {
        if (this.Q.isRunning()) {
            this.Q.cancel();
        }
        
        if (this.R.isRunning()) {
            this.R.cancel();
        }
        
    }
    
    private void g(fn var1) {
        CustomAlertDialog var3 = new CustomAlertDialog(this);
        var3.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_sound));
        var3.a(com.nexusteam.blacklogics.R.drawable.music_48);
        View var4 = ViewHelper.a(this, com.nexusteam.blacklogics.R.layout.property_popup_selector_single);
        RadioGroup var5 = (RadioGroup)var4.findViewById(com.nexusteam.blacklogics.R.id.rg_content);
        SoundPool var10;
        if (VERSION.SDK_INT >= 21) {
            Builder var2 = new Builder();
            var2.setUsage(1);
            var2.setContentType(2);
            AudioAttributes var9 = var2.build();
            android.media.SoundPool.Builder var6 = new android.media.SoundPool.Builder();
            var6.setMaxStreams(1);
            var6.setAudioAttributes(var9);
            var10 = var6.build();
        } else {
            var10 = new SoundPool(1, 3, 0);
        }
        
        var10.setOnLoadCompleteListener(new LogicEditorActivity$25(this));
        ArrayList var11 = ma.c(this.f).n();
        
        RadioButton var8;
        for(Iterator var7 = var11.iterator(); var7.hasNext(); var8.setOnClickListener(new LogicEditorActivity$26(this, var10))) {
            String var12 = (String)var7.next();
            var8 = this.e(var12);
            var5.addView(var8);
            if (var12.equals(var1.getArgValue())) {
                var8.setChecked(true);
            }
        }
        
        var3.a(var4);
        var3.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_word_select), new LogicEditorActivity$27(this, var5, var1, var3));
        var3.b(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_word_cancel), new LogicEditorActivity$28(this, var3));
        var3.show();
    }
    
    private void g(boolean var1) {
        this.n.e(var1);
    }
    
    private RadioButton h(String var1) {
        RadioButton var3 = new RadioButton(this);
        var3.setText("");
        var3.setTag(var1);
        float var2 = ViewHelper.a(this, 1.0F);
        LayoutParams var4 = new LayoutParams(-2, (int)(var2 * 60.0F));
        var3.setGravity(19);
        var3.setLayoutParams(var4);
        return var3;
    }
    
    private String h() {
        return lu.a(this.getJavaName(), this.g, this.h);
    }
    

    static ArrayList h(LogicEditorActivity var0) {
        return var0.ah;
    }
    
    private void h(fn var1) {
        CustomAlertDialog var3 = new CustomAlertDialog(this);
        var3.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_font));
        var3.a(com.nexusteam.blacklogics.R.drawable.abc_96_color);
        View var5 = ViewHelper.a(this, com.nexusteam.blacklogics.R.layout.property_popup_selector_color);
        RadioGroup var2 = (RadioGroup)var5.findViewById(com.nexusteam.blacklogics.R.id.rg);
        LinearLayout var4 = (LinearLayout)var5.findViewById(com.nexusteam.blacklogics.R.id.content);
        ArrayList var6 = ma.c(this.f).o();
        if (ff.a(this.f) || ff.b(this.f)) {
            var6.add(0, "default_font");
        }
        
        Iterator var9 = var6.iterator();
        
        while(var9.hasNext()) {
            String var7 = (String)var9.next();
            RadioButton var8 = this.h(var7);
            var2.addView(var8);
            if (var7.equals(var1.getArgValue())) {
                var8.setChecked(true);
            }
            
            LinearLayout var10 = this.i(var7);
            var10.setOnClickListener(new LogicEditorActivity$29(this, var4, var2));
            var4.addView(var10);
        }
        
        var3.a(var5);
        var3.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_word_select), new LogicEditorActivity$30(this, var2, var1, var3));
        var3.b(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_word_cancel), new LogicEditorActivity$31(this, var3));
        var3.show();
    }
    
    private void h(boolean var1) {
        if (!this.ac) {
            this.w();
        }
        
        if (this.ad != var1) {
            this.ad = var1;
            this.x();
            if (var1) {
                this.aa.start();
            } else {
                this.ab.start();
            }
            
        }
    }
    
    private LinearLayout i(String var1) {
        float var2 = ViewHelper.a(this, 1.0F);
        LinearLayout var3 = new LinearLayout(this);
        LayoutParams var4 = new LayoutParams(-1, (int)(var2 * 60.0F));
        var3.setLayoutParams(var4);
        var3.setGravity(19);
        var3.setOrientation(0);
        TextView var5 = new TextView(this);
        var4 = new LayoutParams(0, -2);
        var4.weight = 1.0F;
        var5.setLayoutParams(var4);
        var5.setText(var1);
        var3.addView(var5);
        var5 = new TextView(this);
        LayoutParams var6 = new LayoutParams(0, -2);
        var6.weight = 1.0F;
        var5.setLayoutParams(var4);
        var5.setText("Preview");
        if (!var1.toLowerCase().equals("default_font".toLowerCase())) {
            var5.setTypeface(Typeface.createFromFile(ma.c(this.f).c(var1)));
        } else {
            var5.setTypeface(Typeface.DEFAULT);
        }
        
        var3.addView(var5);
        return var3;
    }
    

    static ArrayList i(LogicEditorActivity var0) {
        return var0.ai;
    }
    
    private void i() {
        if (!this.x) {
            HistoryBlockBean var2 = lu.a(this.f).g(this.h());
            if (var2 == null) {
                this.a();
            } else {
                int var1 = var2.getActionType();
                int[] var3;
                if (var1 == 0) {
                    var3 = new int[2];
                    this.e.getLocationOnScreen(var3);
                    this.a(var2.getAddedData(), var2.getCurrentX() + var3[0], var2.getCurrentY() + var3[1], true);
                    if (var2.getCurrentParentData() != null) {
                        this.a(var2.getCurrentParentData(), true);
                    }
                } else if (var1 == 1) {
                    BlockBean var5 = var2.getCurrentUpdateData();
                    this.a(var5, true);
                } else if (var1 == 2) {
                    ArrayList var6 = var2.getRemovedData();
                    var1 = var6.size();
                    
                    while(true) {
                        --var1;
                        if (var1 < 0) {
                            if (var2.getCurrentParentData() != null) {
                                this.a(var2.getCurrentParentData(), true);
                            }
                            break;
                        }
                        
                        this.e.a((BlockBean)var6.get(var1), false);
                    }
                } else if (var1 == 3) {
                    Iterator var7 = var2.getAfterMoveData().iterator();
                    
                    while(var7.hasNext()) {
                        BlockBean var4 = (BlockBean)var7.next();
                        this.e.a(var4, true);
                    }
                    
                    var3 = new int[2];
                    this.e.getLocationOnScreen(var3);
                    this.a(var2.getAfterMoveData(), var2.getCurrentX() + var3[0], var2.getCurrentY() + var3[1], true);
                    if (var2.getCurrentParentData() != null) {
                        this.a(var2.getCurrentParentData(), true);
                    }
                    
                    if (var2.getCurrentOriginalParent() != null) {
                        this.a(var2.getCurrentOriginalParent(), true);
                    }
                }
                
                this.a();
            }
        }
    }
    
    private void i(fn var1) {
        CustomAlertDialog var4 = new CustomAlertDialog(this);
        var4.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_typeface));
        var4.a(com.nexusteam.blacklogics.R.drawable.abc_96_color);
        View var5 = ViewHelper.a(this, com.nexusteam.blacklogics.R.layout.property_popup_selector_single);
        RadioGroup var7 = (RadioGroup)var5.findViewById(com.nexusteam.blacklogics.R.id.rg_content);
        Pair[] var6 = fa.a("property_text_style");
        int var3 = var6.length;
        
        for(int var2 = 0; var2 < var3; ++var2) {
            Pair var8 = var6[var2];
            RadioButton var9 = this.e((String)var8.second);
            var7.addView(var9);
            if (((String)var8.second).equals(var1.getArgValue())) {
                var9.setChecked(true);
            }
        }
        
        var4.a(var5);
        var4.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_word_save), new LogicEditorActivity$32(this, var7, var1, var4));
        var4.b(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_word_cancel), new LogicEditorActivity$33(this, var4));
        var4.show();
    }
    
    private void j() {
        if (!this.x) {
            HistoryBlockBean var2 = lu.a(this.f).e(this.h());
            if (var2 == null) {
                this.a();
            } else {
                int var1 = var2.getActionType();
                if (var1 == 0) {
                    ArrayList var3 = var2.getAddedData();
                    var1 = var3.size();
                    
                    while(true) {
                        --var1;
                        if (var1 < 0) {
                            if (var2.getPrevParentData() != null) {
                                var2.getPrevParentData().print();
                                this.a(var2.getPrevParentData(), true);
                            }
                            break;
                        }
                        
                        this.e.a((BlockBean)var3.get(var1), false);
                    }
                } else if (var1 == 1) {
                    BlockBean var5 = var2.getPrevUpdateData();
                    this.a(var5, true);
                } else {
                    int[] var6;
                    if (var1 == 2) {
                        ArrayList var4 = var2.getRemovedData();
                        var6 = new int[2];
                        this.e.getLocationOnScreen(var6);
                        this.a(var4, var2.getCurrentX() + var6[0], var2.getCurrentY() + var6[1], true);
                        if (var2.getPrevParentData() != null) {
                            this.a(var2.getPrevParentData(), true);
                        }
                    } else if (var1 == 3) {
                        Iterator var8 = var2.getBeforeMoveData().iterator();
                        
                        while(var8.hasNext()) {
                            BlockBean var7 = (BlockBean)var8.next();
                            this.e.a(var7, true);
                        }
                        
                        var6 = new int[2];
                        this.e.getLocationOnScreen(var6);
                        this.a(var2.getBeforeMoveData(), var2.getPrevX() + var6[0], var2.getPrevY() + var6[1], true);
                        if (var2.getPrevParentData() != null) {
                            this.a(var2.getPrevParentData(), true);
                        }
                        
                        if (var2.getPrevOriginalParent() != null) {
                            this.a(var2.getPrevOriginalParent(), true);
                        }
                    }
                }
                
                this.a();
            }
        }
    }
    
    private void j(String var1) {
        if (this.ag == null) {
            this.ag = new ArrayList();
        }
        
        Iterator var5 = ma.c(this.f).m().iterator();
        
        while(var5.hasNext()) {
            String var4 = (String)var5.next();
            if (var4.equals(var1)) {
                return;
            }
        }
        
        ProjectResourceBean var6 = ek.f().b(var1);
        if (var6 != null) {
            boolean var3 = false;
            var5 = this.ag.iterator();
            
            boolean var2;
            while(true) {
                var2 = var3;
                if (!var5.hasNext()) {
                    break;
                }
                
                ProjectResourceBean var7 = (ProjectResourceBean)var5.next();
                if (var7.resName.equals(var1)) {
                    var2 = true;
                    break;
                }
            }
            
            if (!var2) {
                this.ag.add(var6);
            }
            
        }
    }
    
    private void k() {
        ArrayList var5 = ma.a(this.f).e(this.getJavaName());
        Iterator var6 = var5.iterator();
        int var4 = 0;
        int var3 = 0;
        int var2 = 0;
        int var1 = 0;
        
        while(var6.hasNext()) {
            Pair var8 = (Pair)var6.next();
            View var7;
            if ((Integer)var8.first == 0) {
                var7 = this.a((String)var8.second, "b", "getVar");
                var7.setTag(var8.second);
                ++var4;
            } else if ((Integer)var8.first == 1) {
                var7 = this.a((String)var8.second, "d", "getVar");
                var7.setTag(var8.second);
                ++var3;
            } else if ((Integer)var8.first == 2) {
                var7 = this.a((String)var8.second, "s", "getVar");
                var7.setTag(var8.second);
                ++var2;
            } else if ((Integer)var8.first == 3) {
                var7 = this.a((String)var8.second, "a", et.b((Integer)var8.first), "getVar");
                var7.setTag(var8.second);
                ++var1;
            }
        }
        
        if (var4 > 0) {
            this.b(" ", "setVarBoolean");
        }
        
        if (var3 > 0) {
            this.b(" ", "setVarInt");
            this.b(" ", "increaseInt");
            this.b(" ", "decreaseInt");
        }
        
        if (var2 > 0) {
            this.b(" ", "setVarString");
        }
        
        if (var1 > 0) {
            this.b(" ", "mapCreateNew");
            this.b(" ", "mapPut");
            this.b("s", "mapGet");
            this.b("b", "mapContainKey");
            this.b(" ", "mapRemoveKey");
            this.b("d", "mapSize");
            this.b(" ", "mapClear");
            this.b("b", "mapIsEmpty");
            this.b(" ", "mapGetAllKeys");
        }
        
        Iterator var10;
        View var11;
        ViewBean var14;
        if (this.h.equals("onBindCustomView")) {
            String var9 = ma.a(this.f).e(getXmlName(), this.g).customView;
            if (var9 != null && var9.length() > 0) {
                this.a("Custom Views", -11184811);
                var5 = ma.a(this.f).a(ProjectFileBean.getXmlName(var9));
                var10 = var5.iterator();
                
                while(var10.hasNext()) {
                    var14 = (ViewBean)var10.next();
                    var11 = this.a(var14.id, "v", ViewBean.getViewTypeName(var14.type), "getVar");
                    var11.setTag(var14.id);
                }
            }
            
            this.b("c", "viewOnClick");
        } else {
            this.a("Views", -11184811);
            var5 = ma.a(this.f).a(getXmlName());
            var10 = var5.iterator();
            
            while(var10.hasNext()) {
                var14 = (ViewBean)var10.next();
                var11 = this.a(var14.id, "v", ViewBean.getViewTypeName(var14.type), "getVar");
                var11.setTag(var14.id);
            }
            
            this.m.hasActivityOption(8);
            this.m.hasActivityOption(4);
        }
        
        this.a("Components", -11184811);
        var5 = ma.a(this.f).k(this.getJavaName());
        Iterator var15 = var5.iterator();
        
        while(var15.hasNext()) {
            ComponentBean var12 = (ComponentBean)var15.next();
            View var13 = this.a(var12.componentId, "p", ComponentBean.getComponentTypeName(var12.type), "getVar");
            var13.setTag(var12.componentId);
        }
        
    }
    
    private void k(String var1) {
        if (this.ah == null) {
            this.ah = new ArrayList();
        }
        
        Iterator var4 = ma.c(this.f).n().iterator();
        
        while(var4.hasNext()) {
            String var5 = (String)var4.next();
            if (var5.equals(var1)) {
                return;
            }
        }
        
        ProjectResourceBean var7 = em.f().b(var1);
        if (var7 != null) {
            boolean var3 = false;
            Iterator var6 = this.ah.iterator();
            
            boolean var2;
            while(true) {
                var2 = var3;
                if (!var6.hasNext()) {
                    break;
                }
                
                ProjectResourceBean var8 = (ProjectResourceBean)var6.next();
                if (var8.resName.equals(var1)) {
                    var2 = true;
                    break;
                }
            }
            
            if (!var2) {
                this.ah.add(var7);
            }
            
        }
    }
    
    private void l() {
        ArrayList var4 = ma.a(this.f).f(this.getJavaName());
        Iterator var7 = var4.iterator();
        int var3 = 0;
        int var2 = 0;
        int var1 = 0;
        
        while(var7.hasNext()) {
            Pair var6 = (Pair)var7.next();
            if ((Integer)var6.first == 1) {
                ++var3;
            } else if ((Integer)var6.first == 2) {
                ++var2;
            } else {
                ++var1;
            }
            
            View var5 = this.a((String)var6.second, "l", et.a((Integer)var6.first), "getVar");
            var5.setTag(var6.second);
        }
        
        if (var3 > 0) {
            this.b(" ", "addListInt");
            this.b(" ", "insertListInt");
            this.b("d", "getAtListInt");
            this.b("d", "indexListInt");
            this.b("b", "containListInt");
        }
        
        if (var2 > 0) {
            this.b(" ", "addListStr");
            this.b(" ", "insertListStr");
            this.b("s", "getAtListStr");
            this.b("d", "indexListStr");
            this.b("b", "containListStr");
        }
        
        if (var1 > 0 || this.h.equals("onBindCustomView")) {
            this.b(" ", "addListMap");
            this.b(" ", "insertListMap");
            this.b(" ", "setListMap");
            this.b("s", "getAtListMap");
            this.b("b", "containListMap");
            this.b(" ", "addMapToList");
            this.b(" ", "insertMapToList");
            this.b(" ", "getMapInList");
        }
        
        if (var3 > 0 || var2 > 0 || var1 > 0 || this.h.equals("onBindCustomView")) {
            this.b(" ", "deleteList");
            this.b("d", "lengthList");
            this.b(" ", "clearList");
        }
        
    }
    
    private void l(String var1) {
        if (this.ai == null) {
            this.ai = new ArrayList();
        }
        
        Iterator var5 = ma.c(this.f).o().iterator();
        
        while(var5.hasNext()) {
            String var4 = (String)var5.next();
            if (var4.equals(var1)) {
                return;
            }
        }
        
        ProjectResourceBean var8 = ej.f().b(var1);
        if (var8 != null) {
            boolean var3 = false;
            Iterator var7 = this.ai.iterator();
            
            boolean var2;
            while(true) {
                var2 = var3;
                if (!var7.hasNext()) {
                    break;
                }
                
                ProjectResourceBean var6 = (ProjectResourceBean)var7.next();
                if (var6.resName.equals(var1)) {
                    var2 = true;
                    break;
                }
            }
            
            if (!var2) {
                this.ai.add(var8);
            }
            
        }
    }
    
    private void m(String var1) {
        if (ek.f().a(var1)) {
            ProjectResourceBean var5 = ek.f().b(var1);
            StringBuilder var2 = new StringBuilder();
            var2.append(fe.w());
            var2.append(File.separator);
            var2.append("image");
            var2.append(File.separator);
            var2.append("data");
            var2.append(File.separator);
            var2.append(var5.resFullName);
            String var6 = var2.toString();
            StringBuilder var3 = new StringBuilder();
            var3.append(fe.r());
            var3.append(File.separator);
            var3.append(this.f);
            var3.append(File.separator);
            var3.append(var5.resFullName);
            String var7 = var3.toString();
            
            try {
                this.H.a(var6, var7);
                ma.c(this.f).f373a.add(var5);
            } catch (Exception var4) {
                var4.printStackTrace();
            }
            
        }
    }
    
    private void n(String var1) {
        if (em.f().a(var1)) {
            ProjectResourceBean var5 = em.f().b(var1);
            StringBuilder var2 = new StringBuilder();
            var2.append(fe.w());
            var2.append(File.separator);
            var2.append("sound");
            var2.append(File.separator);
            var2.append("data");
            var2.append(File.separator);
            var2.append(var5.resFullName);
            String var6 = var2.toString();
            StringBuilder var3 = new StringBuilder();
            var3.append(fe.s());
            var3.append(File.separator);
            var3.append(this.f);
            var3.append(File.separator);
            var3.append(var5.resFullName);
            String var7 = var3.toString();
            
            try {
                this.H.a(var6, var7);
                ma.c(this.f).b.add(var5);
            } catch (Exception var4) {
                var4.printStackTrace();
            }
            
        }
    }
    
    private void o(String var1) {
        if (ej.f().a(var1)) {
            ProjectResourceBean var5 = ej.f().b(var1);
            StringBuilder var2 = new StringBuilder();
            var2.append(fe.w());
            var2.append(File.separator);
            var2.append("font");
            var2.append(File.separator);
            var2.append("data");
            var2.append(File.separator);
            var2.append(var5.resFullName);
            String var6 = var2.toString();
            StringBuilder var3 = new StringBuilder();
            var3.append(fe.t());
            var3.append(File.separator);
            var3.append(this.f);
            var3.append(File.separator);
            var3.append(var5.resFullName);
            String var7 = var3.toString();
            
            try {
                this.H.a(var6, var7);
                ma.c(this.f).c.add(var5);
            } catch (Exception var4) {
                var4.printStackTrace();
            }
            
        }
    }
    
    private void r() {
        ArrayList var1 = ma.a(this.f).h(this.getJavaName());
        Iterator var2 = var1.iterator();
        
        while(var2.hasNext()) {
            Pair var4 = (Pair)var2.next();
            View var3 = this.a((String)var4.second, " ", "definedFunc");
            var3.setTag(var4.second);
        }
        
    }
    
    private void s() {
        HashMap var4 = new HashMap();
        lw var7 = ma.a(this.f);
        String var5 = this.getJavaName();
        StringBuilder var6 = new StringBuilder();
        var6.append(this.g);
        var6.append("_");
        var6.append(this.h);
        ArrayList var11 = var7.l(var5, var6.toString());
        if (var11 != null) {
            if (var11.size() == 0) {
                this.a(this.T);
            }
            
            Iterator var10 = var11.iterator();
            boolean var1 = true;
            
            fm var14;
            while(var10.hasNext()) {
                BlockBean var13 = (BlockBean)var10.next();
                if (this.h.equals("onTextChanged") && var13.opCode.equals("getArg") && var13.spec.equals("text")) {
                    var13.spec = "charSeq";
                }
                
                var14 = this.a(var13);
                var4.put((Integer)var14.getTag(), var14);
                this.e.a = Math.max(this.e.a, (Integer)var14.getTag() + 1);
                this.e.c(var14, 0, 0);
                var14.setOnTouchListener(this);
                if (var1) {
                    this.e.getRoot().a(var14);
                    var1 = false;
                }
            }
            
            var10 = var11.iterator();
            
            while(true) {
                BlockBean var12;
                do {
                    if (!var10.hasNext()) {
                        this.e.getRoot().b();
                        this.e.c();
                        return;
                    }
                    
                    var12 = (BlockBean)var10.next();
                    var14 = (fm)var4.get(Integer.valueOf(var12.id));
                } while(var14 == null);
                
                fm var8;
                if (var12.subStack1 >= 0) {
                    var8 = (fm)var4.get(var12.subStack1);
                    if (var8 != null) {
                        var14.d(var8);
                    }
                }
                
                if (var12.subStack2 >= 0) {
                    var8 = (fm)var4.get(var12.subStack2);
                    if (var8 != null) {
                        var14.e(var8);
                    }
                }
                
                if (var12.nextBlock >= 0) {
                    var8 = (fm)var4.get(var12.nextBlock);
                    if (var8 != null) {
                        var14.a(var8);
                    }
                }
                
                int var2 = var12.parameters.size();
                
                for(int var9 = 0; var9 < var2; ++var9) {
                    String var15 = (String)var12.parameters.get(var9);
                    if (var15 != null && var15.length() > 0) {
                        if (var15.charAt(0) == '@') {
                            int var3 = Integer.valueOf(var15.substring(1));
                            var8 = (fm)var4.get(var3);
                            if (var8 != null) {
                                var14.a((fo)var14.c.get(var9), var8);
                            }
                        } else {
                            ((fn)var14.c.get(var9)).setArgValue(var15);
                            var14.g();
                        }
                    }
                }
            }
        }
    }
    
    private void t() {
        ArrayList var1 = this.e.getBlocks();
        lw var4 = ma.a(this.f);
        String var3 = this.getJavaName();
        StringBuilder var2 = new StringBuilder();
        var2.append(this.g);
        var2.append("_");
        var2.append(this.h);
        var4.a(var3, var2.toString(), var1);
        ArrayList<BlockBean> blocks = this.e.getBlocks();
        
        ma.b(); // lw.b() → save
        ma.c(); // ly.c() → save
        ma.d(); // mb.a() → save
        ma.e(); // lz.a() → saveif (widgetId != null) {
        
        if (widgetId != null && !widgetId.isEmpty()) {

    Qf.saveBlockLogicForWidget(
        activityName,
        widgetId,
        getSourceCode()
    );

} else if (type != null && !type.isEmpty()) {

    Qf.saveBlockLogicForEvent(
        activityName,
        type,
        getSourceCode()
    );

} else {

    Qf.saveBlockLogic(
        activityName,
        getSourceCode()
    );
}

        

        FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("code/code.txt"), getSourceCode());
        
        BlockStorage.save(
        this.g + "_" + this.h + "_" + this.activityName,
        blocks
        );
        
    }
    
    private void u() {
        this.W = ObjectAnimator.ofFloat(this.n, "TranslationY", new float[]{0.0F});
        this.W.setDuration(500L);
        this.W.setInterpolator(new DecelerateInterpolator());
        this.X = ObjectAnimator.ofFloat(this.n, "TranslationY", new float[]{(float)(this.n.getHeight() * -1)});
        this.X.setDuration(300L);
        this.X.setInterpolator(new DecelerateInterpolator());
        this.Y = true;
    }
    
    private void v() {
        if (this.W.isRunning()) {
            this.W.cancel();
        }
        
        if (this.X.isRunning()) {
            this.X.cancel();
        }
        
    }
    
    private void w() {
        this.aa = ObjectAnimator.ofFloat(this.o, "TranslationX", new float[]{0.0F});
        this.aa.setDuration(500L);
        this.aa.setInterpolator(new DecelerateInterpolator());
        this.ab = ObjectAnimator.ofFloat(this.o, "TranslationX", new float[]{(float)this.o.getHeight()});
        this.ab.setDuration(300L);
        this.ab.setInterpolator(new DecelerateInterpolator());
        this.ac = true;
    }
    
    private void x() {
        if (this.aa.isRunning()) {
            this.aa.cancel();
        }
        
        if (this.ab.isRunning()) {
            this.ab.cancel();
        }
        
    }
    
    private void y() {
        ArrayList var1 = ei.f().g();
        this.o.a();
        Iterator var3 = var1.iterator();
        
        while(var3.hasNext()) {
            BlockCollectionBean var2 = (BlockCollectionBean)var3.next();
            View var4 = this.o.a(var2.name, var2.blocks);
            var4.setOnTouchListener(this);
        }
        
    }
    
    private void z() {
        CustomAlertDialog var4 = new CustomAlertDialog(this);
        var4.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_add_new_variable));
        var4.a(com.nexusteam.blacklogics.R.drawable.add_96_blue);
        View var1 = ViewHelper.a(this, com.nexusteam.blacklogics.R.layout.logic_popup_add_variable);
        RadioGroup var3 = (RadioGroup)var1.findViewById(com.nexusteam.blacklogics.R.id.rg_type);
        EditText var2 = (EditText)var1.findViewById(com.nexusteam.blacklogics.R.id.ed_input);
        ((TextInputLayout)var1.findViewById(com.nexusteam.blacklogics.R.id.ti_input)).setHint(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_hint_enter_variable_name));
        var2.setPrivateImeOptions("defaultInputmode=english;");
        ((TextView)var1.findViewById(com.nexusteam.blacklogics.R.id.rb_boolean)).setText(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_variable_type_boolean));
        ((TextView)var1.findViewById(com.nexusteam.blacklogics.R.id.rb_int)).setText(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_variable_type_number));
        ((TextView)var1.findViewById(com.nexusteam.blacklogics.R.id.rb_string)).setText(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_variable_type_string));
        ((TextView)var1.findViewById(com.nexusteam.blacklogics.R.id.rb_map)).setText(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_variable_type_map));
        Context var5 = this.getApplicationContext();
        TextInputLayout var6 = (TextInputLayout)var1.findViewById(com.nexusteam.blacklogics.R.id.ti_input);
        String[] var7 = fc.b;
        lr var8 = new lr(var5, var6, var7, fc.c(), ma.a(this.f).b(this.m));
        var4.a(var1);
        var4.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_word_add), new LogicEditorActivity$43(this, var3, var2, var8, var4));
        var4.b(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_word_cancel), new LogicEditorActivity$44(this, var2, var4));
        var4.show();
    }
    
    protected fm a(fm var1, int var2, int var3, boolean var4) {
        var1 = this.e.a(var1, var2, var3, var4);
        if (!var4) {
            var1.setOnTouchListener(this);
        }
        
        return var1;
    }
    
    public fm a(BlockBean var1) {
        return new fm(this, Integer.valueOf(var1.id), var1.spec, var1.type, var1.typeName, var1.opCode);
    }
    
    public void a() {
        this.invalidateOptionsMenu();
    }
    
    public void a(int var1, int var2) {
        this.c.a();
        switch(var1) {
            case 0:
            this.c(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_panel_button_add_variable), "variableAdd");
            this.c(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_panel_button_remove_variable), "variableRemove");
            this.k();
            break;
            case 1:
            this.c(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_panel_button_add_list), "listAdd");
            this.c(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_panel_button_remove_list), "listRemove");
            this.l();
            break;
            case 2:
            this.b("c", "repeat");
            this.b("c", "forever");
            this.b("f", "break");
            this.b("c", "if");
            this.b("e", "ifElse");
            break;
            case 3:
            this.b("b", "true");
            this.b("b", "false");
            this.b("b", "<");
            this.b("b", "=");
            this.b("b", ">");
            this.b("b", "&&");
            this.b("b", "||");
            this.b("b", "not");
            this.b("d", "+");
            this.b("d", "-");
            this.b("d", "*");
            this.b("d", "/");
            this.b("d", "%");
            this.b("d", "random");
            this.b("d", "stringLength");
            this.b("s", "stringJoin");
            this.b("d", "stringIndex");
            this.b("d", "stringLastIndex");
            this.b("s", "stringSub");
            this.b("b", "stringEquals");
            this.b("b", "stringContains");
            this.b("s", "stringReplace");
            this.b("s", "trim");
            this.b("s", "toUpperCase");
            this.b("s", "toLowerCase");
            this.b("d", "toNumber");
            this.b("s", "toString");
            this.b("s", "toStringWithDecimal");
            this.b("s", "toStringFormat");
            this.b(" ", "strToMap");
            this.b("s", "mapToStr");
            this.b(" ", "strToListMap");
            this.b("s", "listMapToStr");
            this.b(" ", "addSourceDirectly");
            break;
            case 4:
            this.b("d", "mathGetDip");
            this.b("d", "mathGetDisplayWidth");
            this.b("d", "mathGetDisplayHeight");
            this.b("d", "mathPi");
            this.b("d", "mathE");
            this.b("d", "mathPow");
            this.b("d", "mathMin");
            this.b("d", "mathMax");
            this.b("d", "mathSqrt");
            this.b("d", "mathAbs");
            this.b("d", "mathRound");
            this.b("d", "mathCeil");
            this.b("d", "mathFloor");
            this.b("d", "mathSin");
            this.b("d", "mathCos");
            this.b("d", "mathTan");
            this.b("d", "mathAsin");
            this.b("d", "mathAcos");
            this.b("d", "mathAtan");
            this.b("d", "mathExp");
            this.b("d", "mathLog");
            this.b("d", "mathLog10");
            this.b("d", "mathToRadian");
            this.b("d", "mathToDegree");
            break;
            case 5:
            this.b("s", "fileutilread");
            this.b(" ", "fileutilwrite");
            this.b(" ", "fileutilcopy");
            this.b(" ", "fileutilmove");
            this.b(" ", "fileutildelete");
            this.b("b", "fileutilisexist");
            this.b(" ", "fileutilmakedir");
            this.b(" ", "fileutillistdir");
            this.b("b", "fileutilisdir");
            this.b("b", "fileutilisfile");
            this.b("d", "fileutillength");
            this.b("b", "fileutilStartsWith");
            this.b("b", "fileutilEndsWith");
            this.b("s", "fileutilGetLastSegmentPath");
            this.b("s", "getExternalStorageDir");
            this.b("s", "getPackageDataDir");
            this.b("s", "getPublicDir");
            this.b(" ", "resizeBitmapFileRetainRatio");
            this.b(" ", "resizeBitmapFileToSquare");
            this.b(" ", "resizeBitmapFileToCircle");
            this.b(" ", "resizeBitmapFileWithRoundedBorder");
            this.b(" ", "cropBitmapFileFromCenter");
            this.b(" ", "rotateBitmapFile");
            this.b(" ", "scaleBitmapFile");
            this.b(" ", "skewBitmapFile");
            this.b(" ", "setBitmapFileColorFilter");
            this.b(" ", "setBitmapFileBrightness");
            this.b(" ", "setBitmapFileContrast");
            this.b("d", "getJpegRotate");
            break;
            case 6:
            if (this.m.hasActivityOption(4)) {
                this.b("b", "isDrawerOpen");
                this.b(" ", "openDrawer");
                this.b(" ", "closeDrawer");
            }
            
            this.b(" ", "setEnable");
            this.b("b", "getEnable");
            this.b(" ", "setVisible");
            this.b(" ", "setRotate");
            this.b("d", "getRotate");
            this.b(" ", "setAlpha");
            this.b("d", "getAlpha");
            this.b(" ", "setTranslationX");
            this.b("d", "getTranslationX");
            this.b(" ", "setTranslationY");
            this.b("d", "getTranslationY");
            this.b(" ", "setScaleX");
            this.b("d", "getScaleX");
            this.b(" ", "setScaleY");
            this.b("d", "getScaleY");
            this.b("d", "getLocationX");
            this.b("d", "getLocationY");
            this.b(" ", "requestFocus");
            this.b(" ", "setText");
            this.b("s", "getText");
            this.b(" ", "setTypeface");
            this.b(" ", "setHint");
            this.b(" ", "setChecked");
            this.b("b", "getChecked");
            this.b(" ", "setBgColor");
            this.b(" ", "setBgResource");
            this.b(" ", "setTextColor");
            this.b(" ", "setHintTextColor");
            this.b(" ", "setImage");
            this.b(" ", "setColorFilter");
            this.b(" ", "setImageFilePath");
            this.b(" ", "setImageUrl");
            this.b(" ", "seekBarSetProgress");
            this.b("d", "seekBarGetProgress");
            this.b(" ", "seekBarSetMax");
            this.b("d", "seekBarGetMax");
            this.b(" ", "progressBarSetIndeterminate");
            if (!this.h.equals("onBindCustomView")) {
                this.b(" ", "listSetData");
                this.b(" ", "listSetCustomViewData");
                this.b(" ", "listRefresh");
                this.b(" ", "listSmoothScrollTo");
                this.b(" ", "spnSetData");
                this.b(" ", "spnRefresh");
                this.b(" ", "spnSetSelection");
                this.b("d", "spnGetSelection");
                this.b(" ", "webViewLoadUrl");
                this.b("s", "webViewGetUrl");
                this.b(" ", "webViewSetCacheMode");
                this.b("b", "webViewCanGoBack");
                this.b("b", "webViewCanGoForward");
                this.b(" ", "webViewGoBack");
                this.b(" ", "webViewGoForward");
                this.b(" ", "webViewClearCache");
                this.b(" ", "webViewClearHistory");
                this.b(" ", "webViewStopLoading");
                this.b(" ", "webViewZoomIn");
                this.b(" ", "webViewZoomOut");
                this.b(" ", "calendarViewSetDate");
                this.b(" ", "calendarViewSetMinDate");
                this.b(" ", "calnedarViewSetMaxDate");
            }
            
            if (ma.d(this.f).e().useYn.equals("Y")) {
                this.b(" ", "adViewLoadAd");
            }
            
            this.b(" ", "mapViewSetMapType");
            this.b(" ", "mapViewMoveCamera");
            this.b(" ", "mapViewZoomTo");
            this.b(" ", "mapViewZoomIn");
            this.b(" ", "mapViewZoomOut");
            this.b(" ", "mapViewAddMarker");
            this.b(" ", "mapViewSetMarkerInfo");
            this.b(" ", "mapViewSetMarkerPosition");
            this.b(" ", "mapViewSetMarkerColor");
            this.b(" ", "mapViewSetMarkerIcon");
            this.b(" ", "mapViewSetMarkerVisible");
            break;
            case 7:
            this.c(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_panel_button_add_component), "componentAdd");
            this.b(" ", "doToast");
            this.b(" ", "copyToClipboard");
            this.b(" ", "setTitle");
            if (ma.a(this.f).g(getJavaName(), 1)) {
                this.b(" ", "intentSetAction");
                this.b(" ", "intentSetData");
                if (ff.a(this.f) || ff.b(this.f)) {
                    this.b(" ", "intentSetScreen");
                    this.b(" ", "intentPutExtra");
                }
                
                this.b(" ", "intentSetFlags");
                this.b(" ", "startActivity");
            }
            
            if (ff.a(this.f) || ff.b(this.f)) {
                this.b("s", "intentGetString");
                this.b("f", "finishActivity");
            }
            
            if (ma.a(this.f).g(getJavaName(), 2)) {
                this.b("s", "fileGetData");
                this.b(" ", "fileSetData");
                this.b(" ", "fileRemoveData");
            }
            
            if (ma.a(this.f).g(getJavaName(), 3)) {
                this.b(" ", "calendarGetNow");
                this.b(" ", "calendarAdd");
                this.b(" ", "calendarSet");
                this.b("s", "calendarFormat");
                this.b("d", "calendarDiff");
                this.b("d", "calendarGetTime");
                this.b(" ", "calendarSetTime");
            }
            
            if (ma.a(this.f).g(getJavaName(), 4)) {
                this.b(" ", "vibratorAction");
            }
            
            if (ma.a(this.f).g(getJavaName(), 5)) {
                this.b("c", "timerAfter");
                this.b("c", "timerEvery");
                this.b(" ", "timerCancel");
            }
            
            if (ma.a(this.f).g(getJavaName(), 7)) {
                this.b(" ", "dialogSetTitle");
                this.b(" ", "dialogSetMessage");
                this.b("c", "dialogOkButton");
                this.b("c", "dialogCancelButton");
                this.b("c", "dialogNeutralButton");
                this.b(" ", "dialogShow");
            }
            
            if (ma.a(this.f).g(getJavaName(), 8)) {
                this.b(" ", "mediaplayerCreate");
                this.b(" ", "mediaplayerStart");
                this.b(" ", "mediaplayerPause");
                this.b(" ", "mediaplayerSeek");
                this.b("d", "mediaplayerGetCurrent");
                this.b("d", "mediaplayerGetDuration");
                this.b("b", "mediaplayerIsPlaying");
                this.b(" ", "mediaplayerSetLooping");
                this.b("b", "mediaplayerIsLooping");
                this.b(" ", "mediaplayerReset");
                this.b(" ", "mediaplayerRelease");
            }
            
            if (ma.a(this.f).g(getJavaName(), 9)) {
                this.b(" ", "soundpoolCreate");
                this.b("d", "soundpoolLoad");
                this.b("d", "soundpoolStreamPlay");
                this.b(" ", "soundpoolStreamStop");
            }
            
            if (ma.a(this.f).g(getJavaName(), 10)) {
                this.b(" ", "objectanimatorSetTarget");
                this.b(" ", "objectanimatorSetProperty");
                this.b(" ", "objectanimatorSetValue");
                this.b(" ", "objectanimatorSetFromTo");
                this.b(" ", "objectanimatorSetDuration");
                this.b(" ", "objectanimatorSetRepeatMode");
                this.b(" ", "objectanimatorSetRepeatCount");
                this.b(" ", "objectanimatorSetInterpolator");
                this.b(" ", "objectanimatorStart");
                this.b(" ", "objectanimatorCancel");
                this.b("b", "objectanimatorIsRunning");
            }
            
            if (ma.a(this.f).g(getJavaName(), 6)) {
                this.b(" ", "firebaseAdd");
                this.b(" ", "firebasePush");
                this.b("s", "firebaseGetPushKey");
                this.b(" ", "firebaseDelete");
                this.b("c", "firebaseGetChildren");
                this.b(" ", "firebaseStartListen");
                this.b(" ", "firebaseStopListen");
            }
            
            if (ma.a(this.f).g(getJavaName(), 12)) {
                this.b(" ", "firebaseauthCreateUser");
                this.b(" ", "firebaseauthSignInUser");
                this.b(" ", "firebaseauthSignInAnonymously");
                this.b("b", "firebaseauthIsLoggedIn");
                this.b("s", "firebaseauthGetCurrentUser");
                this.b("s", "firebaseauthGetUid");
                this.b(" ", "firebaseauthResetPassword");
                this.b(" ", "firebaseauthSignOutUser");
            }
            
            if (ma.a(this.f).g(getJavaName(), 11)) {
                this.b(" ", "gyroscopeStartListen");
                this.b(" ", "gyroscopeStopListen");
            }
            
            if (ma.a(this.f).g(getJavaName(), 13)) {
                this.b(" ", "interstitialadCreate");
                this.b(" ", "interstitialadLoadAd");
                this.b(" ", "interstitialadShow");
            }
            
            if (ma.a(this.f).g(getJavaName(), 14)) {
                this.b(" ", "firebasestorageUploadFile");
                this.b(" ", "firebasestorageDownloadFile");
                this.b(" ", "firebasestorageDelete");
            }
            
            if (ma.a(this.f).g(getJavaName(), 15)) {
                this.b(" ", "camerastarttakepicture");
            }
            
            if (ma.a(this.f).g(getJavaName(), 16)) {
                this.b(" ", "filepickerstartpickfiles");
            }
            
            if (ma.a(this.f).g(getJavaName(), 17)) {
                this.b(" ", "requestnetworkSetParams");
                this.b(" ", "requestnetworkSetHeaders");
                this.b(" ", "requestnetworkStartRequestNetwork");
            }
            
            if (ma.a(this.f).g(getJavaName(), 18)) {
                this.b(" ", "textToSpeechSetPitch");
                this.b(" ", "textToSpeechSetSpeechRate");
                this.b(" ", "textToSpeechSpeak");
                this.b("b", "textToSpeechIsSpeaking");
                this.b(" ", "textToSpeechStop");
                this.b(" ", "textToSpeechShutdown");
            }
            
            if (ma.a(this.f).g(getJavaName(), 19)) {
                this.b(" ", "speechToTextStartListening");
                this.b(" ", "speechToTextStopListening");
                this.b(" ", "speechToTextShutdown");
            }
            
            if (ma.a(this.f).g(getJavaName(), 20)) {
                this.b(" ", "bluetoothConnectReadyConnection");
                this.b(" ", "bluetoothConnectReadyConnectionToUuid");
                this.b(" ", "bluetoothConnectStartConnection");
                this.b(" ", "bluetoothConnectStartConnectionToUuid");
                this.b(" ", "bluetoothConnectStopConnection");
                this.b(" ", "bluetoothConnectSendData");
                this.b("b", "bluetoothConnectIsBluetoothEnabled");
                this.b("b", "bluetoothConnectIsBluetoothActivated");
                this.b(" ", "bluetoothConnectActivateBluetooth");
                this.b(" ", "bluetoothConnectGetPairedDevices");
                this.b("s", "bluetoothConnectGetRandomUuid");
            }
            
            if (ma.a(this.f).g(getJavaName(), 21)) {
                this.b(" ", "locationManagerRequestLocationUpdates");
                this.b(" ", "locationManagerRemoveUpdates");
            }
            break;
            case 8:
            this.c(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_panel_button_create_moreblock), "blockAdd");
            this.c("Import from collection", "blockImport");
            this.c("Explore Shared Collection", "sharedMoreBlock");
            this.r();
        }
        
    }
    
    protected void a(int var1, String var2) {
        ma.a(this.f).a(this.getJavaName(), var1, var2);
        BlockStorage.addVariable(this.getJavaName(), new VariableData(var1, var2, ""));
        this.a(0, -1147626);
    }
    
    protected void a(fm var1) {
        this.e.c(var1);
    }
    
    public void a(fm var1, float var2, float var3) {
        Iterator var4 = var1.c.iterator();
        
        while(var4.hasNext()) {
            View var5 = (View)var4.next();
            if (var5 instanceof fn && var5.getX() < var2 && var5.getX() + (float)var5.getWidth() > var2 && var5.getY() < var3 && var5.getY() + (float)var5.getHeight() > var3) {
                this.a((fn)var5);
                break;
            }
        }
        
    }
    
    public void a(fn var1) {
        boolean var4 = var1.w.equals("d");
        byte var3 = 1;
        byte var2 = 1;
        if (var4) {
            this.a(var1, true);
        } else {
            String var5;
            if (var1.w.equals("s")) {
                label84: {
                    var5 = var1.getMenuName();
                    int var6 = var5.hashCode();
                    if (var6 != -1700991898) {
                        if (var6 == 116079 && var5.equals("url")) {
                            break label84;
                        }
                    } else if (var5.equals("intentData")) {
                        var2 = 0;
                        break label84;
                    }
                    
                    var2 = -1;
                }
                
                switch(var2) {
                    case 0:
                    this.b(var1);
                    break;
                    case 1:
                    this.c(var1);
                    break;
                    default:
                    this.a(var1, false);
                }
            } else if (var1.w.equals("m")) {
                label112: {
                    var5 = var1.getMenuName();
                    switch(var5.hashCode()) {
                        case -2004438503:
                        if (var5.equals("spinner")) {
                            var2 = 11;
                            break label112;
                        }
                        break;
                        case -1587760963:
                        if (var5.equals("compoundButton")) {
                            var2 = 18;
                            break label112;
                        }
                        break;
                        case -1421968056:
                        if (var5.equals("adview")) {
                            var2 = 19;
                            break label112;
                        }
                        break;
                        case -1002626734:
                        if (var5.equals("textview")) {
                            var2 = 7;
                            break label112;
                        }
                        break;
                        case -889473228:
                        if (var5.equals("switch")) {
                            var2 = 15;
                            break label112;
                        }
                        break;
                        case -877150592:
                        if (var5.equals("imageview")) {
                            var2 = 9;
                            break label112;
                        }
                        break;
                        case -675792745:
                        if (var5.equals("typeface")) {
                            var2 = 4;
                            break label112;
                        }
                        break;
                        case -351639837:
                        if (var5.equals("calendarview")) {
                            var2 = 17;
                            break label112;
                        }
                        break;
                        case -341064690:
                        if (var5.equals("resource")) {
                            var2 = 0;
                            break label112;
                        }
                        break;
                        case 3148879:
                        if (var5.equals("font")) {
                            var2 = 3;
                            break label112;
                        }
                        break;
                        case 3619493:
                        if (var5.equals("view")) {
                            var2 = 6;
                            break label112;
                        }
                        break;
                        case 94842723:
                        if (var5.equals("color")) {
                            var2 = 5;
                            break label112;
                        }
                        break;
                        case 109627663:
                        if (var5.equals("sound")) {
                            var2 = 2;
                            break label112;
                        }
                        break;
                        case 181954611:
                        if (var5.equals("listSpn")) {
                            var2 = 12;
                            break label112;
                        }
                        break;
                        case 837734913:
                        if (var5.equals("mapview")) {
                            var2 = 21;
                            break label112;
                        }
                        break;
                        case 1131540166:
                        if (var5.equals("progressbar")) {
                            var2 = 20;
                            break label112;
                        }
                        break;
                        case 1224424441:
                        if (var5.equals("webview")) {
                            var2 = 13;
                            break label112;
                        }
                        break;
                        case 1234536982:
                        if (var5.equals("resource_bg")) {
                            var2 = var3;
                            break label112;
                        }
                        break;
                        case 1346661443:
                        if (var5.equals("listview")) {
                            var2 = 10;
                            break label112;
                        }
                        break;
                        case 1536891843:
                        if (var5.equals("checkbox")) {
                            var2 = 14;
                            break label112;
                        }
                        break;
                        case 1602985527:
                        if (var5.equals("edittext")) {
                            var2 = 8;
                            break label112;
                        }
                        break;
                        case 1971813019:
                        if (var5.equals("seekbar")) {
                            var2 = 16;
                            break label112;
                        }
                    }
                    
                    var2 = -1;
                }
                
                switch(var2) {
                    case 0:
                    if (String.valueOf(var1.getArgValue()).startsWith("0x")) {
                        this.f(var1);
                    } else {
                        this.a(var1, "property_image");
                    }
                    break;
                    case 1:
                    this.a(var1, "property_background_resource");
                    break;
                    case 2:
                    this.g(var1);
                    break;
                    case 3:
                    this.h(var1);
                    break;
                    case 4:
                    this.i(var1);
                    break;
                    case 5:
                    this.f(var1);
                    break;
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                    case 19:
                    case 20:
                    case 21:
                    this.e(var1);
                    break;
                    default:
                    this.d(var1);
                }
            }
        }
        
    }
    
    protected void a(fn var1, Object var2) {
        BlockBean var3 = var1.ac.getBean().clone();
        var1.setArgValue(var2);
        var1.ac.g();
        var1.ac.c().b();
        var1.ac.s.c();
        BlockBean var4 = var1.ac.getBean().clone();
        lu.a(this.f).a(this.h(), var3, var4);
        this.a();
    }
    
    public void a(fn fnVar, boolean z2) {
        final CustomAlertDialog kdVar = new CustomAlertDialog(this);
        if (z2) {
            kdVar.a(StringResourceManager.a().a(getApplicationContext(), (int) com.nexusteam.blacklogics.R.string.logic_editor_title_enter_number_value));
        } else {
            kdVar.a(StringResourceManager.a().a(getApplicationContext(), (int) com.nexusteam.blacklogics.R.string.logic_editor_title_enter_string_value));
        }
        kdVar.a((int) com.nexusteam.blacklogics.R.drawable.rename_96_blue);
        View a2 = ViewHelper.a((Context) this, (int) com.nexusteam.blacklogics.R.layout.property_popup_input_text);
        final EditText editText = (EditText) a2.findViewById(com.nexusteam.blacklogics.R.id.ed_input);
        if (z2) {
            editText.setInputType(12290);
            editText.setImeOptions(6);
            editText.setMaxLines(1);
        } else {
            editText.setInputType(655361);
            editText.setImeOptions(1);
        }
        editText.setText(fnVar.getArgValue().toString());
        kdVar.a(a2);
        final EditText editText2 = editText;
        final boolean z3 = z2;
        final fn fnVar2 = fnVar;
        final CustomAlertDialog kdVar2 = kdVar;
        kdVar.a(StringResourceManager.a().a(getApplicationContext(), (int) com.nexusteam.blacklogics.R.string.common_word_save), new View.OnClickListener() {
            public void onClick(View view) {
                String obj = editText2.getText().toString();
                if (z3) {
                    try {
                        double parseDouble = Double.parseDouble(obj);
                        if (Double.isNaN(parseDouble) || Double.isInfinite(parseDouble)) {
                            obj = "";
                        }
                    } catch (NumberFormatException e2) {
                        e2.printStackTrace();
                        obj = "";
                    }
                } else if (obj.length() > 0 && obj.charAt(0) == '@') {
                    obj = MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + obj;
                }
                LogicEditorActivity.this.a(fnVar2, (Object) obj);
                ki.a(LogicEditorActivity.this.getApplicationContext(), editText2);
                kdVar2.dismiss();
            }
        });
        kdVar.b(StringResourceManager.a().a(getApplicationContext(), (int) com.nexusteam.blacklogics.R.string.common_word_cancel), new View.OnClickListener() {
            public void onClick(View view) {
                ki.a(LogicEditorActivity.this.getApplicationContext(), editText);
                kdVar.dismiss();
            }
        });
        kdVar.show();
    }
    
    public void a(String var1) {
        /*Intent var2 = new Intent(this.getApplicationContext(), ShowBlockCollectionActivity.class);
var2.putExtra("block_name", var1);
this.startActivity(var2);*/        
    }
    
    protected void a(String var1, String var2) {
        ma.a(this.f).c(this.getJavaName(), var1, var2);
        ArrayList<Pair<String, String>> list =
        BlockStorage.loadFunctions(this.getJavaName());
        
        list.add(new Pair<>(var1, var2));
        
        BlockStorage.saveFunctions(this.getJavaName(), list);
        this.a(8, -7711273);
    }
    
    protected void a(boolean var1) {
        if (!this.S) {
            this.b(this.getResources().getConfiguration().orientation);
        }
        
        if (this.T != var1) {
            this.T = var1;
            this.g();
            if (var1) {
                this.h(false);
                this.Q.start();
            } else {
                this.R.start();
            }
            
            this.c(this.getResources().getConfiguration().orientation);
        }
    }
    
    protected void b() {
        if (this.p != null) {
            this.c.setDragEnabled(false);
            this.d.setScrollEnabled(false);
            this.o.setDragEnabled(false);
            if (this.ad) {
                this.h(false);
            }
            
            if (this.F) {
                this.E.vibrate(100L);
            }
            
            this.x = true;
            if (((fm)this.p).getBlockType() == 0) {
                this.b((fm)this.p);
                this.c(true);
                this.b(true);
                this.s.a((fm)this.p);
                this.e.a((fm)this.p, 8);
                this.e.b((fm)this.p);
                this.e.a((fm)this.p);
            } else if (((fm)this.p).getBlockType() == 2) {
                this.c(false);
                this.b(true);
                this.s.a((fm)this.p);
                this.e.a((fm)this.p, ((fp)this.p).getData());
            } else {
                this.s.a((fm)this.p);
                this.e.a((fm)this.p);
            }
            
            float var2 = this.t - this.v;
            float var1 = this.u - this.w;
            this.s.a(this.p, var2, var1, var2, var1, (float)this.I, (float)this.P);
            this.s.a(this.y);
            if (this.d.a((float)this.y[0], (float)this.y[1])) {
                this.s.setAllow(true);
                this.e.a((fm)this.p, this.y[0], this.y[1]);
            } else {
                this.s.setAllow(false);
                this.e.a();
            }
            
        }
    }
    
    protected void b(int var1, String var2) {
        ma.a(this.f).b(this.getJavaName(), var1, var2);
        this.a(1, -3384542);
    }
    
    public void b(fn var1) {
        CustomAlertDialog var3 = new CustomAlertDialog(this);
        var3.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_enter_data_value));
        var3.a(com.nexusteam.blacklogics.R.drawable.rename_96_blue);
        View var4 = ViewHelper.a(this, com.nexusteam.blacklogics.R.layout.property_popup_input_intent_data);
        ((TextView)var4.findViewById(com.nexusteam.blacklogics.R.id.tv_desc_intent_usage)).setText(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.property_description_component_intent_usage));
        EditText var2 = (EditText)var4.findViewById(com.nexusteam.blacklogics.R.id.ed_input);
        ((TextInputLayout)var4.findViewById(com.nexusteam.blacklogics.R.id.ti_input)).setHint(StringResourceManager.a().a(this, com.nexusteam.blacklogics.R.string.property_hint_enter_value));
        var2.setInputType(524289);
        var2.setText(var1.getArgValue().toString());
        var3.a(var4);
        var3.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_word_save), new LogicEditorActivity$9(this, var1, var2, var3));
        var3.b(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_word_cancel), new LogicEditorActivity$10(this, var2, var3));
        var3.show();
    }
    
    protected void b(String var1) {
        ma.a(this.f).f(this.getJavaName(), var1);
        this.a(0, -1147626);
    }
    
    protected void c() {
    }
    
    public void c(fn var1) {
        CustomAlertDialog var3 = new CustomAlertDialog(this);
        var3.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_enter_string_value));
        var3.a(com.nexusteam.blacklogics.R.drawable.rename_96_blue);
        View var2 = ViewHelper.a(this, com.nexusteam.blacklogics.R.layout.property_popup_input_text);
        ((TextInputLayout)var2.findViewById(com.nexusteam.blacklogics.R.id.ti_input)).setHint(StringResourceManager.a().a(this, com.nexusteam.blacklogics.R.string.property_hint_enter_value));
        EditText var4 = (EditText)var2.findViewById(com.nexusteam.blacklogics.R.id.ed_input);
        var4.setSingleLine(true);
        var4.setInputType(524497);
        var4.setImeOptions(6);
        var4.setText(var1.getArgValue().toString());
        var3.a(var2);
        var3.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_word_save), new LogicEditorActivity$11(this, var1, var4, var3));
        var3.b(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_word_cancel), new LogicEditorActivity$13(this, var4, var3));
        var3.show();
    }
    
    protected void c(String var1) {
        ma.a(this.f).g(this.getJavaName(), var1);
        this.a(1, -3384542);
    }
    
    public void d(fn var1) {
        CustomAlertDialog var7 = new CustomAlertDialog(this);
        View var9 = ViewHelper.a(this, com.nexusteam.blacklogics.R.layout.property_popup_selector_single);
        ViewGroup var8 = (ViewGroup)var9.findViewById(com.nexusteam.blacklogics.R.id.rg_content);
        ArrayList var6 = new ArrayList();
        boolean var4 = var1.getMenuName().equals("varInt");
        int var2 = 0;
        ArrayList var5;
        if (var4) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_variable_number));
            var5 = ma.a(this.f).a(this.getJavaName(), 1);
        } else if (var1.getMenuName().equals("varBool")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_variable_boolean));
            var5 = ma.a(this.f).a(this.getJavaName(), 0);
        } else if (var1.getMenuName().equals("varStr")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_variable_string));
            var5 = ma.a(this.f).a(this.getJavaName(), 2);
        } else if (var1.getMenuName().equals("varMap")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_variable_map));
            var5 = ma.a(this.f).a(this.getJavaName(), 3);
        } else if (var1.getMenuName().equals("listInt")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_list_number));
            var5 = ma.a(this.f).b(this.getJavaName(), 1);
        } else if (var1.getMenuName().equals("listStr")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_list_string));
            var5 = ma.a(this.f).b(this.getJavaName(), 2);
        } else if (var1.getMenuName().equals("listMap")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_list_map));
            var5 = ma.a(this.f).b(this.getJavaName(), 3);
        } else if (var1.getMenuName().equals("list")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_list));
            var5 = ma.a(this.f).g(this.getJavaName());
        } else if (var1.getMenuName().equals("intent")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_component_intent));
            var5 = ma.a(this.f).e(this.getJavaName(), 1);
        } else if (var1.getMenuName().equals("file")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_component_file));
            var5 = ma.a(this.f).e(this.getJavaName(), 2);
        } else if (var1.getMenuName().equals("intentAction")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_component_intent_action));
            var5 = new ArrayList(Arrays.asList(fc.a()));
        } else if (var1.getMenuName().equals("intentFlags")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_component_intent_flags));
            var5 = new ArrayList(Arrays.asList(fc.b()));
        } else if (var1.getMenuName().equals("activity")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_activity));
        /*    Iterator var10 = ma.b(this.f).a().iterator();
            
            while(true) {
                var5 = var6;
                if (!var10.hasNext()) {
                    break;
                }
                
                ProjectFileBean var13 = (ProjectFileBean)var10.next();
                var6.add(var13.getJavaName().substring(0, var13.getJavaName().indexOf(".java")));
            }*/
            var5 = var6;
            List<String> activityNames = complex.getActivityNames();
            for (String activityName : activityNames) {
                var6.add(activityName); // activityName is already without .java extension
            }
        } else if (var1.getMenuName().equals("calendar")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_component_calendar));
            var5 = ma.a(this.f).e(this.getJavaName(), 3);
        } else if (var1.getMenuName().equals("calendarField")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_component_calendar_field));
            var5 = new ArrayList(Arrays.asList(fc.e));
        } else if (var1.getMenuName().equals("vibrator")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_component_vibrator));
            var5 = ma.a(this.f).e(this.getJavaName(), 4);
        } else if (var1.getMenuName().equals("timer")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_component_timer));
            var5 = ma.a(this.f).e(this.getJavaName(), 5);
        } else if (var1.getMenuName().equals("firebase")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_component_firebase));
            var5 = ma.a(this.f).e(this.getJavaName(), 6);
        } else if (var1.getMenuName().equals("firebaseauth")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_component_firebaseauth_title_select_firebase_auth));
            var5 = ma.a(this.f).e(this.getJavaName(), 12);
        } else if (var1.getMenuName().equals("firebasestorage")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_component_firebasestorage));
            var5 = ma.a(this.f).e(this.getJavaName(), 14);
        } else if (var1.getMenuName().equals("dialog")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_component_dialog));
            var5 = ma.a(this.f).e(this.getJavaName(), 7);
        } else if (var1.getMenuName().equals("mediaplayer")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_component_mediaplayer));
            var5 = ma.a(this.f).e(this.getJavaName(), 8);
        } else if (var1.getMenuName().equals("soundpool")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_component_soundpool));
            var5 = ma.a(this.f).e(this.getJavaName(), 9);
        } else if (var1.getMenuName().equals("objectanimator")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_component_objectanimator));
            var5 = ma.a(this.f).e(this.getJavaName(), 10);
        } else if (var1.getMenuName().equals("aniRepeatMode")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_animator_repeat_mode));
            var5 = new ArrayList(Arrays.asList(fc.j));
        } else if (var1.getMenuName().equals("aniInterpolator")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_animator_interpolator));
            var5 = new ArrayList(Arrays.asList(fc.k));
        } else if (var1.getMenuName().equals("visible")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_visibility));
            var5 = new ArrayList(Arrays.asList(fc.g));
        } else if (var1.getMenuName().equals("cacheMode")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_cache_mode));
            var5 = new ArrayList(Arrays.asList(fc.h));
        } else if (var1.getMenuName().equals("animatorproperty")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_animator_target_property));
            var5 = new ArrayList(Arrays.asList(fc.i));
        } else if (var1.getMenuName().equals("gyroscope")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_component_gyroscope));
            var5 = ma.a(this.f).e(this.getJavaName(), 11);
        } else if (var1.getMenuName().equals("interstitialad")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_component_interstitialad));
            var5 = ma.a(this.f).e(this.getJavaName(), 13);
        } else if (var1.getMenuName().equals("firebasestorage")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_component_firebasestorage));
            var5 = ma.a(this.f).e(this.getJavaName(), 14);
        } else if (var1.getMenuName().equals("camera")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_component_camera));
            var5 = ma.a(this.f).e(this.getJavaName(), 15);
        } else if (var1.getMenuName().equals("filepicker")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_component_filepicker));
            var5 = ma.a(this.f).e(this.getJavaName(), 16);
        } else if (var1.getMenuName().equals("directoryType")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_directory_type));
            var5 = new ArrayList(Arrays.asList(fc.l));
        } else if (var1.getMenuName().equals("requestnetwork")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_component_request_network));
            var5 = ma.a(this.f).e(this.getJavaName(), 17);
        } else if (var1.getMenuName().equals("method")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_request_network_method));
            var5 = new ArrayList(Arrays.asList(fc.n));
        } else if (var1.getMenuName().equals("requestType")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_request_network_request_type));
            var5 = new ArrayList(Arrays.asList(fc.o));
        } else if (var1.getMenuName().equals("texttospeech")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_component_text_to_speech));
            var5 = ma.a(this.f).e(this.getJavaName(), 18);
        } else if (var1.getMenuName().equals("speechtotext")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_component_speech_to_text));
            var5 = ma.a(this.f).e(this.getJavaName(), 19);
        } else if (var1.getMenuName().equals("bluetoothconnect")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_component_bluetooth_connect));
            var5 = ma.a(this.f).e(this.getJavaName(), 20);
        } else if (var1.getMenuName().equals("locationmanager")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_component_location_manager));
            var5 = ma.a(this.f).e(this.getJavaName(), 21);
        } else if (var1.getMenuName().equals("providerType")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_location_manager_provider_type));
            var5 = new ArrayList(Arrays.asList(fc.p));
        } else if (var1.getMenuName().equals("mapType")) {
            var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_mapview_map_type));
            var5 = new ArrayList(Arrays.asList(fc.q));
        } else {
            var5 = var6;
            if (var1.getMenuName().equals("markerColor")) {
                var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_mapview_marker_color));
                var5 = new ArrayList(Arrays.asList(fc.r));
            }
        }
        
        Iterator var14 = var5.iterator();
        
        while(var14.hasNext()) {
            String var11 = (String)var14.next();
            RadioButton var12 = this.e(var11);
            var8.addView(var12);
        }
        
        for(int var3 = var8.getChildCount(); var2 < var3; ++var2) {
            RadioButton var15 = (RadioButton)var8.getChildAt(var2);
            if (var1.getArgValue().toString().equals(var15.getText().toString())) {
                var15.setChecked(true);
                break;
            }
        }
        
        var7.a(var9);
        var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_word_select), new LogicEditorActivity$16(this, var8, var1, var7));
        var7.b(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_word_cancel), new LogicEditorActivity$17(this, var7));
        var7.show();
    }
    
    public boolean d() {
        int var2 = this.e.getChildCount();
        
        for(int var1 = 0; var1 < var2; ++var1) {
            View var3 = this.e.getChildAt(var1);
            if (var3 instanceof fm) {
                ((fm)var3).b.equals("Forever");
            }
        }
        
        return true;
    }
    
    public void e(fn var1) {
        CustomAlertDialog var7 = new CustomAlertDialog(this);
        View var6 = ViewHelper.a(this, com.nexusteam.blacklogics.R.layout.property_popup_selector_single);
        ViewGroup var8 = (ViewGroup)var6.findViewById(com.nexusteam.blacklogics.R.id.rg_content);
        new ArrayList();
        String var5 = getXmlName();
        String var4 = var5;
        if (this.h.equals("onBindCustomView")) {
            ViewBean var9 = ma.a(this.f).e(getXmlName(), this.g);
            var4 = var5;
            if (var9.customView != null) {
                var4 = ProjectFileBean.getXmlName(var9.customView);
            }
        }
        
        var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.logic_editor_title_select_view));
        var5 = var1.getClassInfo().a();
        ArrayList var10 = ma.a(this.f).c(var4, var5);
        Iterator var11 = var10.iterator();
        
        while(var11.hasNext()) {
            Pair var12 = (Pair)var11.next();
            RadioButton var13 = this.d(ViewBean.getViewTypeName((Integer)var12.first), (String)var12.second);
            var8.addView(var13);
        }
        
        int var3 = var8.getChildCount();
        
        for(int var2 = 0; var2 < var3; ++var2) {
            RadioButton var14 = (RadioButton)var8.getChildAt(var2);
            if (var1.getArgValue().toString().equals(var14.getTag().toString())) {
                var14.setChecked(true);
                break;
            }
        }
        
        var7.a(var6);
        var7.a(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_word_select), new LogicEditorActivity$18(this, var8, var1, var7));
        var7.b(StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_word_cancel), new LogicEditorActivity$19(this, var7));
        var7.show();
    }
    
    public boolean e() {
        return true;
    }
    
    protected void f() {
        try {
            Handler var2 = new Handler();
            LogicEditorActivity$34 var1 = new LogicEditorActivity$34(this);
            var2.postDelayed(var1, 500L);
        } catch (Exception var3) {
            var3.printStackTrace();
        }
        
    }
    
    public void finish() {
        lu.a(this.f).c(this.h());
        super.finish();
    }
    
    private String getSourceCode() {
        es projectSettings = new es();
       ArrayList<BlockBean> blocks = this.e.getBlocks();

if (blocks == null || blocks.isEmpty()) {
    return "// No blocks found";
}

hb generator = new hb(this.h, projectSettings, activityName, blocks);

String javaCode = generator.a();

return javaCode;

    }
    
    protected void onActivityResult(int var1, int var2, Intent var3) {
        super.onActivityResult(var1, var2, var3);
        if (var1 != 222) {
            if (var1 != 224) {
                if (var1 == 463 && var2 == -1 && var3.getBooleanExtra("req_update_design_activity", false)) {
                    this.y();
                }
            } else if (var2 == -1) {
                this.a(7, -13851166);
            }
        } else if (var2 == -1) {
            String var4 = var3.getStringExtra("block_name");
            String var5 = var3.getStringExtra("block_spec");
            this.a(var4, var5);
            loadFunctionsIntoMemory();
        }
        
    }
    
    public void onBackPressed() {
        if (this.ad) {
            this.h(false);
        } else if (this.T) {
            this.a(this.T ^ true);
        } else {
            this.m();
            if (this.d()) {
                if (this.e()) {
                    this.f();
                }
            }
        }
    }
    
    public void onClick(View var1) {
        if (!ki.a()) {
            if (var1.getTag() != null) {
                if (var1.getTag().equals("variableAdd")) {
                    this.z();
                } else if (var1.getTag().equals("variableRemove")) {
                    this.A();
                } else if (var1.getTag().equals("listAdd")) {
                    this.B();
                } else if (var1.getTag().equals("listRemove")) {
                    this.C();
                } else {
                    Intent var3;
                    if (var1.getTag().equals("blockAdd")) {
                        var3 = new Intent(this.getApplicationContext(), MakeBlockActivity.class);
                        var3.putExtra("sc_id", this.f);
                        var3.putExtra("project_file", this.m);
                        var3.setFlags(536870912);                        
                        this.startActivityForResult(var3, 222);                    
                    } else if (var1.getTag().equals("componentAdd")) {
                        var3 = new Intent(this.getApplicationContext(), ComponentAddActivity.class);
                        var3.putExtra("sc_id", this.f);
                        var3.putExtra("project_file", this.m);
                        var3.putExtra("filename", this.getJavaName());
                        this.startActivityForResult(var3, 224);                        
                    } else if (var1.getTag().equals("blockImport")) {
                        this.D();
                    } else if (var1.getTag().equals("sharedMoreBlock")) {
                        /* var3 = new Intent(this.getApplicationContext(), SharedMoreBlocksListActivity.class);
var3.setFlags(536870912);
this.startActivityForResult(var3, 464);*/                        
                    }
                }
            }
            
            int var2 = var1.getId();
            if (var2 != com.nexusteam.blacklogics.R.id.btn_accept) {
                if (var2 == com.nexusteam.blacklogics.R.id.btn_cancel) {
                    this.setResult(0);
                    this.finish();
                }
            } else {
                Intent var4 = new Intent();
                this.setResult(-1, var4);
                this.finish();
            }
            
        }
    }
    
    public void onConfigurationChanged(Configuration var1) {
        super.onConfigurationChanged(var1);
        this.a(var1.orientation);
    }
    
    public void onCreate(Bundle var1) {
        super.onCreate(var1);
        this.setContentView(com.nexusteam.blacklogics.R.layout.logic_editor);
        if (!super.n()) {
            this.finish();
        }
        
        readExtras(var1);
        
        complex = new Complex();
        complex.setId(this.f);
        
        String projectDataDir = com.nexusteam.internal.fe.d(this.f);
        Qf.setPath(projectDataDir);
        
        if (getIntent().hasExtra("type")) {
            this.type = getIntent().getStringExtra("type");
        }
        
        if (getIntent().hasExtra("widgetid")) {
            this.widgetId = getIntent().getStringExtra("widgetid");
        }
        
        if (getIntent().hasExtra("activityName")) {
            this.activityName = getIntent().getStringExtra("activityName");
        }
        
        BlockStorage.setScId(this.f);
        FullStorage.setScId(this.f);
        
        this.G = new kv(this.K, "P1");
        this.P = (int)ViewHelper.a(this.getBaseContext(), (float)this.P);
        this.a = (Toolbar)this.findViewById(com.nexusteam.blacklogics.R.id.toolbar);
        this.setSupportActionBar(this.a);
        this.findViewById(com.nexusteam.blacklogics.R.id.layout_main_logo).setVisibility(8);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);
        this.a.setNavigationOnClickListener(new LogicEditorActivity$1(this));
        this.a.setPopupTheme(com.nexusteam.blacklogics.R.style.ThemeOverlay);
        kv var4 = new kv(this.K, "P12");
        this.F = var4.a("P12I0", true);
        ViewConfiguration var5 = ViewConfiguration.get(this.K);
        this.D = var5.getScaledTouchSlop();
        this.E = (Vibrator)this.getSystemService("vibrator");
        String var6 = this.getIntent().getStringExtra("event_text");
        if (this.g.equals("onCreate")) {
            this.getSupportActionBar().setTitle(var6);
        } else if (this.g.equals("_fab")) {
            ActionBar var2 = this.getSupportActionBar();
            StringBuilder var3 = new StringBuilder();
            var3.append("fab : ");
            var3.append(var6);
            var2.setTitle(var3.toString());
        } else {
            ActionBar var8 = this.getSupportActionBar();
            StringBuilder var7 = new StringBuilder();
            var7.append(this.g);
            var7.append(" : ");
            var7.append(var6);
            var8.setTitle(var7.toString());
        }
        
        this.b = (PaletteSelector)this.findViewById(com.nexusteam.blacklogics.R.id.palette_selector);
        this.b.setOnBlockCategorySelectListener(this);
        this.c = (PaletteBlock)this.findViewById(com.nexusteam.blacklogics.R.id.palette_block);
        this.s = (ViewDummy)this.findViewById(com.nexusteam.blacklogics.R.id.dummy);
        this.d = (ViewLogicEditor)this.findViewById(com.nexusteam.blacklogics.R.id.editor);
        this.e = this.d.getBlockPane();
        this.j = (LinearLayout)this.findViewById(com.nexusteam.blacklogics.R.id.layout_palette);
        this.k = (LinearLayout)this.findViewById(com.nexusteam.blacklogics.R.id.area_palette);
        this.l = (FloatingActionButton)this.findViewById(com.nexusteam.blacklogics.R.id.fab_toggle_palette);
        this.l.setOnClickListener(new LogicEditorActivity$12(this));
        this.n = (LogicTopMenu)this.findViewById(com.nexusteam.blacklogics.R.id.top_menu);
        this.o = (LogicEditorDrawer)this.findViewById(com.nexusteam.blacklogics.R.id.right_drawer);
    }
    
    public boolean onCreateOptionsMenu(Menu var1) {
        this.getMenuInflater().inflate(com.nexusteam.blacklogics.R.menu.logic_menu, var1);
        var1.findItem(com.nexusteam.blacklogics.R.id.menu_logic_redo).setEnabled(false);
        var1.findItem(com.nexusteam.blacklogics.R.id.menu_logic_undo).setEnabled(false);
        if (this.m == null) {
            return true;
        } else {
            if (lu.a(this.f).f(this.h())) {
                var1.findItem(com.nexusteam.blacklogics.R.id.menu_logic_redo).setIcon(com.nexusteam.blacklogics.R.drawable.ic_redo_white_48dp);
                var1.findItem(com.nexusteam.blacklogics.R.id.menu_logic_redo).setEnabled(true);
            } else {
                var1.findItem(com.nexusteam.blacklogics.R.id.menu_logic_redo).setIcon(com.nexusteam.blacklogics.R.drawable.ic_redo_grey_48dp);
                var1.findItem(com.nexusteam.blacklogics.R.id.menu_logic_redo).setEnabled(false);
            }
            
            if (lu.a(this.f).d(this.h())) {
                var1.findItem(com.nexusteam.blacklogics.R.id.menu_logic_undo).setIcon(com.nexusteam.blacklogics.R.drawable.ic_undo_white_48dp);
                var1.findItem(com.nexusteam.blacklogics.R.id.menu_logic_undo).setEnabled(true);
            } else {
                var1.findItem(com.nexusteam.blacklogics.R.id.menu_logic_undo).setIcon(com.nexusteam.blacklogics.R.drawable.ic_undo_grey_48dp);
                var1.findItem(com.nexusteam.blacklogics.R.id.menu_logic_undo).setEnabled(false);
            }
            
            return true;
        }
    }
    
    private void loadFunctionsIntoMemory() {
        Object[] o = BlockStorage.readAll();
        HashMap<String, ArrayList<Pair<String, String>>> functions =
        (HashMap<String, ArrayList<Pair<String, String>>>) o[1];
        
        ArrayList<Pair<String, String>> list =
        functions.get(this.getJavaName());
        
        if (list != null) {
            for (Pair<String, String> p : list) {
                ma.a(this.f).c(this.getJavaName(), p.first, p.second);
            }
        }
    }
    
    private void readExtras(Bundle var1) {

    Intent intent = getIntent();
    Bundle bundle = var1;

    if (bundle == null && intent != null) {
        bundle = intent.getExtras();
    }

    if (bundle == null) {
        // last fallback – sab null safe
        f = null;
        g = null;
        h = null;
        m = null;
        return;
    }

    f = bundle.getString("sc_id", null);
    g = bundle.getString("id", null);
    h = bundle.getString("event", null);

    try {
        m = bundle.getParcelable("project_file");
    } catch (Exception e) {
        m = null;
    }
}

private void loadComponenetsIntoMemory() {


    Object[] allData = BlockStorage.readAll();


    HashMap<String, ArrayList<ComponentData>> components =
        (HashMap<String, ArrayList<ComponentData>>) allData[2];


    for (String activityName : components.keySet()) {

        ArrayList<ComponentData> list = components.get(activityName);

        if (list == null) continue;

        for (int i = 0; i < list.size(); i++) {

            ComponentData data = list.get(i);


            ma.a(this.f).a(
                activityName,
                data.type,
                data.mainData,
                data.extraData
            );
        }
    }
}

private void saveAllVariablesToStorage() {
    lw runtimeCache = ma.a(this.f);
    ArrayList<VariableData> variables = new ArrayList<VariableData>();
    

    ArrayList<Pair<Integer, String>> allVars = runtimeCache.e(this.getJavaName());
    for (int i = 0; i < allVars.size(); i++) {
        Pair<Integer, String> p = allVars.get(i);
        int type = p.first; // 0=boolean, 1=int, 2=string, 3=map
        String name = p.second;
        variables.add(new VariableData(type, name, ""));
    }
    

    ArrayList<Pair<Integer, String>> allLists = runtimeCache.f(this.getJavaName());
    for (int i = 0; i < allLists.size(); i++) {
        Pair<Integer, String> p = allLists.get(i);
        int listType = p.first; // 1=listInt, 2=listStr, 3=listMap

        int storageType = listType + 3;
        String name = p.second;
        variables.add(new VariableData(storageType, name, ""));
    }
    
    BlockStorage.saveVariables(this.getJavaName(), variables);
}


private void loadVariablesIntoMemory() {
    Object[] o = BlockStorage.readAll();
    HashMap<String, ArrayList<VariableData>> variables = 
        (HashMap<String, ArrayList<VariableData>>) o[3];
    
    ArrayList<VariableData> list = variables.get(this.getJavaName());
    
    if (list != null) {
        for (int i = 0; i < list.size(); i++) {
            VariableData v = list.get(i);
            
            if (v.type <= 3) { // Primitive variable (0-3)


                ma.a(this.f).a(this.getJavaName(), v.type, v.name);
            } else { // List variable (4-6)

                int listType = v.type - 3;


                ma.a(this.f).a(this.getJavaName(), listType, v.name);
            }
        }
    }
}
    public void onDestroy() {
        super.onDestroy();
    }
    
    public boolean onOptionsItemSelected(MenuItem var1) {
        int var2 = var1.getItemId();
        if (var2 != com.nexusteam.blacklogics.R.id.menu_block_helper) {
            switch(var2) {
                case com.nexusteam.blacklogics.R.id.menu_logic_redo:
                this.i();
                break;
                case com.nexusteam.blacklogics.R.id.menu_logic_undo:
                this.j();
            }
        } else {
            this.a(false);
            this.h(this.ad ^ true);
        }
        
        return super.onOptionsItemSelected(var1);
    }
    
    protected void onPostCreate(@Nullable Bundle var1) {
        super.onPostCreate(var1);
        String var8;
        if (this.h.equals("moreBlock")) {
            var8 = ma.a(this.f).i(this.getJavaName(), this.g);
            String var5 = StringResourceManager.a().a(this.getApplicationContext(), com.nexusteam.blacklogics.R.string.root_spec_common_define);
            StringBuilder var6 = new StringBuilder();
            var6.append(var5);
            var6.append(" ");
            var6.append(var8);
            this.i = var6.toString();
        } else if (this.g.equals("_fab")) {
            this.i = StringResourceManager.a().a(this.getApplicationContext(), "fab", this.h);
        } else {
            this.i = StringResourceManager.a().a(this.getApplicationContext(), this.g, this.h);
        }
        
        this.e.a(this.i, this.h);
        ArrayList var10 = kx.d(this.i);
        int var3 = 0;
        
        int var2;
        for(int var4 = 0; var3 < var10.size(); var4 = var2) {
            String var11 = (String)var10.get(var3);
            var2 = var4;
            if (var11.charAt(0) == '%') {
                label43: {
                    fm var9;
                    if (var11.charAt(1) == 'b') {
                        var9 = new fm(this.K, var4 + 1, var11.substring(3), "b", "getArg");
                    } else if (var11.charAt(1) == 'd') {
                        var9 = new fm(this.K, var4 + 1, var11.substring(3), "d", "getArg");
                    } else if (var11.charAt(1) == 's') {
                        var9 = new fm(this.K, var4 + 1, var11.substring(3), "s", "getArg");
                    } else {
                        var2 = var4;
                        if (var11.charAt(1) != 'm') {
                            break label43;
                        }
                        
                        var8 = var11.substring(var11.lastIndexOf(".") + 1);
                        String var7 = var11.substring(var11.indexOf(".") + 1, var11.lastIndexOf("."));
                        var11 = et.b(var7);
                        var9 = new fm(this.K, var4 + 1, var8, var11, et.a(var7), "getArg");
                    }
                    
                    var9.setBlockType(1);
                    this.e.addView(var9);
                    this.e.getRoot().a((fo)this.e.getRoot().c.get(var4), var9);
                    var9.setOnTouchListener(this);
                    var2 = var4 + 1;
                }
            }
            
            ++var3;
        }
        
        
        
        this.e.getRoot().b();
        this.a(this.getResources().getConfiguration().orientation);
        this.a(0, -1147626);

        ArrayList<BlockBean> saved =
        BlockStorage.load(
        this.g + "_" + this.h + "_" + this.activityName
        );
        
        if (saved != null && !saved.isEmpty()) {
            lw cache = ma.a(this.f);
            cache.a(
            this.getJavaName(),
            this.g + "_" + this.h,
            saved
            );
        }
        
        this.s();
        this.y();
        loadFunctionsIntoMemory();
        loadComponenetsIntoMemory();
        loadVariablesIntoMemory();
    }
    
    public void onResume() {
        super.onResume();
        if (!super.n()) {
            this.finish();
        }
        


    }
    
    protected void onSaveInstanceState(Bundle var1) {
        var1.putString("sc_id", this.f);
        var1.putString("id", this.g);
        var1.putString("event", this.h);
        var1.putParcelable("project_file", this.m);
        super.onSaveInstanceState(var1);
        ArrayList var3 = this.e.getBlocks();
        lw var2 = ma.a(this.f);
        String var4 = this.getJavaName();
        StringBuilder var5 = new StringBuilder();
        var5.append(this.g);
        var5.append("_");
        var5.append(this.h);
        var2.a(var4, var5.toString(), var3);
        ma.a(this.f).c();
    }
    
    public boolean onTouch(View view, MotionEvent motionEvent) {
        BlockBean blockBean;
        BlockBean blockBean2;
        View view2 = view;
        int actionMasked = motionEvent.getActionMasked();
        if (motionEvent.getPointerId(motionEvent.getActionIndex()) > 0) {
            return true;
        }
        if (actionMasked == 0) {
            this.x = false;
            this.U.postDelayed(this.V, (long) (ViewConfiguration.getLongPressTimeout() / 2));
            int[] iArr = new int[2];
            view2.getLocationOnScreen(iArr);
            this.v = (float) iArr[0];
            this.w = (float) iArr[1];
            this.t = motionEvent.getRawX();
            this.u = motionEvent.getRawY();
            this.p = view2;
            return true;
        }
        BlockBean blockBean3 = null;
        if (actionMasked == 2) {
            if (this.x) {
                this.U.removeCallbacks(this.V);
                this.s.a(view2, motionEvent.getRawX() - this.v, motionEvent.getRawY() - this.w, this.t - this.v, this.u - this.w, (float) this.I, (float) this.P);
                if (a(motionEvent.getRawX(), motionEvent.getRawY())) {
                    this.s.setAllow(true);
                    d(true);
                    e(false);
                    f(false);
                    g(false);
                    return true;
                }
                d(false);
                if (b(motionEvent.getRawX(), motionEvent.getRawY())) {
                    this.s.setAllow(true);
                    e(true);
                    f(false);
                    g(false);
                    return true;
                }
                e(false);
                if (c(motionEvent.getRawX(), motionEvent.getRawY())) {
                    this.s.setAllow(true);
                    f(true);
                    g(false);
                    return true;
                }
                f(false);
                if (d(motionEvent.getRawX(), motionEvent.getRawY())) {
                    this.s.setAllow(true);
                    g(true);
                    return true;
                }
                g(false);
                this.s.a(this.y);
                if (this.d.a((float) this.y[0], (float) this.y[1])) {
                    this.s.setAllow(true);
                    this.e.a((fm) view2, this.y[0], this.y[1]);
                } else {
                    this.s.setAllow(false);
                    this.e.a();
                }
                return true;
            } else if (Math.abs((this.t - this.v) - motionEvent.getX()) < ((float) this.D) && Math.abs((this.u - this.w) - motionEvent.getY()) < ((float) this.D)) {
                return false;
            } else {
                this.p = null;
                this.U.removeCallbacks(this.V);
                return false;
            }
        } else if (actionMasked == 1) {
            /* HitBuilders.EventBuilder eventBuilder = new HitBuilders.EventBuilder();
eventBuilder.setCategory("editor");
eventBuilder.setAction("block");
eventBuilder.setLabel("Block drop");
this.J.send(eventBuilder.build());*/            
            this.p = null;
            this.U.removeCallbacks(this.V);
            if (!this.x) {
                if (view2 instanceof fm) {
                    fm fmVar = (fm) view2;
                    if (fmVar.getBlockType() == 0) {
                        a(fmVar, motionEvent.getX(), motionEvent.getY());
                    }
                }
                return false;
            }
            this.c.setDragEnabled(true);
            this.d.setScrollEnabled(true);
            this.o.setDragEnabled(true);
            this.s.setDummyVisibility(8);
            if (!this.s.getAllow()) {
                fm fmVar2 = (fm) view2;
                if (fmVar2.getBlockType() == 0) {
                    this.e.a(fmVar2, 0);
                    if (this.z != null) {
                        if (this.A == 0) {
                            this.z.l = ((Integer) view.getTag()).intValue();
                        }
                        if (this.A == 2) {
                            this.z.m = ((Integer) view.getTag()).intValue();
                        }
                        if (this.A == 3) {
                            this.z.n = ((Integer) view.getTag()).intValue();
                        }
                        if (this.A == 5) {
                            this.z.a((fo) this.z.c.get(this.B), fmVar2);
                        }
                        fmVar2.ac = this.z;
                        this.z.c().b();
                    } else {
                        fmVar2.c().b();
                    }
                }
                c();
            } else if (this.n.a()) {
                fm fmVar3 = (fm) view2;
                if (fmVar3.getBlockType() == 2) {
                    h(true);
                    f(fmVar3.a);
                } else {
                    d(false);
                    int intValue = Integer.valueOf(fmVar3.getBean().id).intValue();
                    fm fmVar4 = this.z;
                    if (fmVar4 != null) {
                        BlockBean clone = fmVar4.getBean().clone();
                        if (this.A == 0) {
                            clone.nextBlock = intValue;
                        } else if (this.A == 2) {
                            clone.subStack1 = intValue;
                        } else if (this.A == 3) {
                            clone.subStack2 = intValue;
                        } else if (this.A == 5) {
                            clone.parameters.set(this.B, "@" + intValue);
                        }
                        blockBean2 = clone;
                    } else {
                        blockBean2 = null;
                    }
                    ArrayList<fm> allChildren = fmVar3.getAllChildren();
                    ArrayList arrayList = new ArrayList();
                    Iterator<fm> it = allChildren.iterator();
                    while (it.hasNext()) {
                        arrayList.add(it.next().getBean().clone());
                    }
                    a(fmVar3);
                    if (fmVar4 != null) {
                        blockBean3 = fmVar4.getBean().clone();
                    }
                    int[] iArr2 = new int[2];
                    this.e.getLocationOnScreen(iArr2);
                    lu.a(this.f).b(h(), arrayList, ((int) this.v) - iArr2[0], ((int) this.w) - iArr2[1], blockBean2, blockBean3);
                    a();
                }
            } else if (this.n.c()) {
                f(false);
                fm fmVar5 = (fm) view2;
                this.e.a(fmVar5, 0);
                if (this.z != null) {
                    if (this.A == 0) {
                        this.z.l = ((Integer) view.getTag()).intValue();
                    }
                    if (this.A == 2) {
                        this.z.m = ((Integer) view.getTag()).intValue();
                    }
                    if (this.A == 3) {
                        this.z.n = ((Integer) view.getTag()).intValue();
                    }
                    if (this.A == 5) {
                        this.z.a((fo) this.z.c.get(this.B), fmVar5);
                    }
                    fmVar5.ac = this.z;
                    this.z.c().b();
                } else {
                    fmVar5.c().b();
                }
                c(fmVar5);
            } else if (this.n.d()) {
                g(false);
                if (view2 instanceof fp) {
                    a(((fp) view2).a);
                }
            } else if (this.n.b()) {
                e(false);
                fm fmVar6 = (fm) view2;
                this.e.a(fmVar6, 0);
                if (this.z != null) {
                    if (this.A == 0) {
                        this.z.l = ((Integer) view.getTag()).intValue();
                    }
                    if (this.A == 2) {
                        this.z.m = ((Integer) view.getTag()).intValue();
                    }
                    if (this.A == 3) {
                        this.z.n = ((Integer) view.getTag()).intValue();
                    }
                    if (this.A == 5) {
                        this.z.a((fo) this.z.c.get(this.B), fmVar6);
                    }
                    fmVar6.ac = this.z;
                    this.z.c().b();
                } else {
                    fmVar6.c().b();
                }
                ArrayList<fm> allChildren2 = fmVar6.getAllChildren();
                ArrayList arrayList2 = new ArrayList();
                Iterator<fm> it2 = allChildren2.iterator();
                while (it2.hasNext()) {
                    BlockBean clone2 = it2.next().getBean().clone();
                    clone2.id = String.valueOf(Integer.valueOf(clone2.id).intValue() + 99000000);
                    if (clone2.nextBlock > 0) {
                        clone2.nextBlock += 99000000;
                    }
                    if (clone2.subStack1 > 0) {
                        clone2.subStack1 += 99000000;
                    }
                    if (clone2.subStack2 > 0) {
                        clone2.subStack2 += 99000000;
                    }
                    for (int i2 = 0; i2 < clone2.parameters.size(); i2++) {
                        String str = clone2.parameters.get(i2);
                        if (str != null && str.length() > 0 && str.charAt(0) == '@') {
                            String substring = str.substring(1);
                            clone2.parameters.set(i2, "@" + String.valueOf(Integer.valueOf(substring).intValue() + 99000000));
                        }
                    }
                    arrayList2.add(clone2);
                }
                int[] iArr3 = new int[2];
                this.d.getLocationOnScreen(iArr3);
                int width = iArr3[0] + (this.d.getWidth() / 2);
                int a2 = iArr3[1] + ((int) ViewHelper.a(getApplicationContext(), 4.0f));
                ArrayList<BlockBean> a3 = a((ArrayList<BlockBean>) arrayList2, width, a2, true);
                int[] iArr4 = new int[2];
                this.e.getLocationOnScreen(iArr4);
                lu.a(this.f).a(h(), a3, width - iArr4[0], a2 - iArr4[1], (BlockBean) null, (BlockBean) null);
                a();
            } else if (view2 instanceof fm) {
                this.s.a(this.y);
                fm fmVar7 = (fm) view2;
                if (fmVar7.getBlockType() == 1) {
                    int addTargetId = this.e.getAddTargetId();
                    BlockBean clone3 = addTargetId >= 0 ? this.e.a(addTargetId).getBean().clone() : null;
                    fm a4 = a(fmVar7, this.y[0], this.y[1], false);
                    if (addTargetId >= 0) {
                        blockBean3 = this.e.a(addTargetId).getBean().clone();
                    }
                    int[] iArr5 = new int[2];
                    this.e.getLocationOnScreen(iArr5);
                    lu.a(this.f).a(h(), a4.getBean().clone(), this.y[0] - iArr5[0], this.y[1] - iArr5[1], clone3, blockBean3);
                    if (clone3 != null) {
                        clone3.print();
                    }
                    if (blockBean3 != null) {
                        blockBean3.print();
                    }
                    a();
                } else if (fmVar7.getBlockType() == 2) {
                    int addTargetId2 = this.e.getAddTargetId();
                    BlockBean clone4 = addTargetId2 >= 0 ? this.e.a(addTargetId2).getBean().clone() : null;
                    ArrayList<BlockBean> a5 = a(((fp) view2).getData(), this.y[0], this.y[1], true);
                    if (a5.size() > 0) {
                        a(this.e.c(a5.get(0).id), this.y[0], this.y[1], true);
                        if (addTargetId2 >= 0) {
                            blockBean3 = this.e.a(addTargetId2).getBean().clone();
                        }
                        int[] iArr6 = new int[2];
                        this.e.getLocationOnScreen(iArr6);
                        lu.a(this.f).a(h(), a5, this.y[0] - iArr6[0], this.y[1] - iArr6[1], clone4, blockBean3);
                        a();
                    }
                } else {
                    this.e.a(fmVar7, 0);
                    int intValue2 = Integer.valueOf(fmVar7.getBean().id).intValue();
                    fm fmVar8 = this.z;
                    if (fmVar8 != null) {
                        blockBean = fmVar8.getBean().clone();
                        if (this.A == 0) {
                            blockBean.nextBlock = intValue2;
                        } else if (this.A == 2) {
                            blockBean.subStack1 = intValue2;
                        } else if (this.A == 3) {
                            blockBean.subStack2 = intValue2;
                        } else if (this.A == 5) {
                            blockBean.parameters.set(this.B, "@" + intValue2);
                        }
                    } else {
                        blockBean = null;
                    }
                    fm a6 = this.e.a(this.e.getAddTargetId());
                    BlockBean clone5 = a6 != null ? a6.getBean().clone() : null;
                    ArrayList<fm> allChildren3 = fmVar7.getAllChildren();
                    ArrayList arrayList3 = new ArrayList();
                    Iterator<fm> it3 = allChildren3.iterator();
                    while (it3.hasNext()) {
                        arrayList3.add(it3.next().getBean().clone());
                    }
                    a(fmVar7, this.y[0], this.y[1], true);
                    ArrayList arrayList4 = new ArrayList();
                    Iterator<fm> it4 = allChildren3.iterator();
                    while (it4.hasNext()) {
                        arrayList4.add(it4.next().getBean().clone());
                    }
                    BlockBean clone6 = fmVar8 != null ? fmVar8.getBean().clone() : null;
                    if (a6 != null) {
                        blockBean3 = a6.getBean().clone();
                    }
                    BlockBean blockBean4 = blockBean3;
                    if (blockBean == null || clone6 == null || !blockBean.isEqual(clone6)) {
                        int[] iArr7 = new int[2];
                        this.e.getLocationOnScreen(iArr7);
                        lu.a(this.f).a(h(), arrayList3, arrayList4, ((int) this.v) - iArr7[0], ((int) this.w) - iArr7[1], this.y[0] - iArr7[0], this.y[1] - iArr7[1], blockBean, clone6, clone5, blockBean4);
                        a();
                    }
                }
                this.e.b();
            }
            this.s.setAllow(false);
            b(false);
            this.x = false;
            return true;
        } else if (actionMasked == 3) {
            this.U.removeCallbacks(this.V);
            this.x = false;
            return false;
        } else if (actionMasked != 8) {
            return true;
        } else {
            this.U.removeCallbacks(this.V);
            this.x = false;
            return false;
        }
    }
    
    
    public class a extends RecyclerView.Adapter<a.aA> {
        
        int a;
        final LogicEditorActivity b;
        
        public a(LogicEditorActivity var1) {
            this.b = var1;
            this.a = -1;
        }
        
        public aA onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(b.getBaseContext()).inflate(com.nexusteam.blacklogics.R.layout.manage_collection_popup_import_more_block_list_item, parent, false);
            return new aA(this, view);
        }
        
        public void onBindViewHolder(aA holder, int position) {
            MoreBlockCollectionBean bean = (MoreBlockCollectionBean) b.r.get(position);
            holder.b.setVisibility(bean.isSelected ? View.VISIBLE : View.GONE);
            holder.c.setText(bean.name);
            holder.d.removeAllViews();
            
            fm header = new fm(b.getBaseContext(), 0, bean.spec, " ", "definedFunc");
            holder.d.addView(header);
            
            ArrayList<String> list = kx.d(bean.spec);
            int index = 0;
            
            for (String s : list) {
                if (s.charAt(0) != '%') continue;
                
                fm block = null;
                switch (s.charAt(1)) {
                    case 'b':
                    block = new fm(b.getBaseContext(), index + 1, s.substring(3), "b", "getVar");
                    break;
                    case 'd':
                    block = new fm(b.getBaseContext(), index + 1, s.substring(3), "d", "getVar");
                    break;
                    case 's':
                    block = new fm(b.getBaseContext(), index + 1, s.substring(3), "s", "getVar");
                    break;
                    case 'm':
                    String var9 = s.substring(s.lastIndexOf(".") + 1);
                    String var7 = s.substring(s.indexOf(".") + 1, s.lastIndexOf("."));
                    String typeName = et.b(var7);
                    block = new fm(b.getBaseContext(), index + 1, var9, typeName, et.a(var7), "getVar");
                    break;
                }
                
                if (block != null) {
                    holder.d.addView(block);
                    header.a((fo) header.c.get(index), block);
                    index++;
                }
            }
            
            header.b();
        }
        
        public int getItemCount() {
            return b.r.size();
        }
        
        public class aA extends RecyclerView.ViewHolder {
            public ViewGroup a;
            public ImageView b;
            public TextView c;
            public ViewGroup d;
            final a e;
            
            public aA(a adapter, View itemView) {
                super(itemView);
                this.e = adapter;
                this.a = itemView.findViewById(com.nexusteam.blacklogics.R.id.layout_item);
                this.b = itemView.findViewById(com.nexusteam.blacklogics.R.id.img_selected);
                this.c = itemView.findViewById(com.nexusteam.blacklogics.R.id.tv_block_name);
                this.d = itemView.findViewById(com.nexusteam.blacklogics.R.id.block_area);
                this.b.setVisibility(View.GONE);
                
                this.a.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        e.a = getLayoutPosition();
                        a(e.a);
                    }
                });
                
                this.d.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        e.a = getLayoutPosition();
                        a(e.a);
                    }
                });
            }
            
            private void a(int position) {
                if (e.b.r.size() <= 0) return;
                
                for (Object o : e.b.r) {
                    ((MoreBlockCollectionBean) o).isSelected = false;
                }
                
                ((MoreBlockCollectionBean) e.b.r.get(position)).isSelected = true;
                e.b.q.notifyDataSetChanged();
            }
        }
    }
    

public String getJavaName() {
/*    if (m != null) {
        return getJavaName();  // "MainActivity.java"
    }*/
    if (activityName != null) {
        return activityName.endsWith(".java") ? activityName : activityName + ".java";
    }
    return null;
}

public String getXmlName() {
    if (activityName == null) return null;

    // Remove .java if present
    String name = activityName.replace(".java", "");

    // Convert CamelCase to snake_case
    StringBuilder xmlName = new StringBuilder();

    for (int i = 0; i < name.length(); i++) {
        char c = name.charAt(i);

        if (Character.isUpperCase(c)) {
            if (i != 0) {
                xmlName.append("_");
            }
            xmlName.append(Character.toLowerCase(c));
        } else {
            xmlName.append(c);
        }
    }

    return xmlName.toString() + ".xml";
}
    
    class b extends jq {
        final LogicEditorActivity a;
        
        public b(LogicEditorActivity var1, Context var2) {
            super(var2);
            this.a = var1;
            var1.a((jq)this);
        }
        
        public void a() {
            this.publishProgress(new String[]{"Now saving.."});
            this.a.t();
        }
        
        public void a(String var1) {
            Toast.makeText(this.h, StringResourceManager.a().a(this.a.getApplicationContext(), com.nexusteam.blacklogics.R.string.common_error_failed_to_save), 0).show();
            this.a.o();
        }
        
        public void b() {
            this.a.o();
            this.a.finish();
        }
    }
}
