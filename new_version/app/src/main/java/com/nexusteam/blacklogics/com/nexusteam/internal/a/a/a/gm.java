package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

public class gm extends RelativeLayout implements OnClickListener {
    
    private Activity a;
    private String b;
    private String c;
    private String d;
    private TextView e;
    private TextView f;
    private ImageView g;
    private int h;
    private RadioGroup i;
    private LinearLayout j;
    private mb k;
    private View l;
    private View m;
    private gi n;
    
    public gm(Activity context) {
        super(context);
        this.a = context;
    }
    
    class AnonymousClass_1 implements OnClickListener {
        final gm a;
        
        AnonymousClass_1(gm gm) {
            this.a = gm;
        }
        
        public void onClick(View v) {
            int index = a.j.indexOfChild(v);
            if (index >= 0 && index < a.i.getChildCount()) {
                ((RadioButton) a.i.getChildAt(index)).setChecked(true);
            }
        }
    }
    
    class AnonymousClass_2 implements OnClickListener {
        final kd a;
        final gm b;
        
        AnonymousClass_2(gm gm, kd kd) {
            this.b = gm;
            this.a = kd;
        }
        
        public void onClick(View v) {
            for (int x = 0; x < b.i.getChildCount(); x++) {
                RadioButton r = (RadioButton) b.i.getChildAt(x);
                if (r.isChecked()) {
                    b.setValue(r.getTag().toString());
                    if (b.n != null) b.n.a(b.c, b.d);
                    break;
                }
            }
            a.dismiss();
        }
    }
    
    class AnonymousClass_3 implements OnClickListener {
        final kd a;
        final gm b;
        
        AnonymousClass_3(gm gm, kd kd) {
            this.b = gm;
            this.a = kd;
        }
        
        public void onClick(View v) {
            a.dismiss();
        }
    }
    
    class AnonymousClass_4 implements DialogInterface.OnDismissListener {
        final gm a;
        
        AnonymousClass_4(gm gm) {
            this.a = gm;
        }
        
        public void onDismiss(DialogInterface dialog) {
            eo.f120a = false;
        }
    }
    
    class AnonymousClass_5 implements DialogInterface.OnShowListener {
        final ScrollView a;
        final RadioButton b;
        final gm c;
        
        AnonymousClass_5(gm gm, ScrollView sv, RadioButton rb) {
            this.c = gm;
            this.a = sv;
            this.b = rb;
        }
        
        public void onShow(DialogInterface dialog) {
            if (b != null) {
                a.post(new Runnable() {
                    @Override
                    public void run() {
                        a.smoothScrollTo(0, (int) b.getY());
                    }
                });
                
            }
        }
    }
    
    private RadioButton a(String text) {
        RadioButton rb = new RadioButton(getContext());
        rb.setText("");
        rb.setTag(text);
        rb.setGravity(19);
        rb.setLayoutParams(new LinearLayout.LayoutParams(-2, (int) (kp.a(getContext(), 1.0f) * 60.0f)));
        return rb;
    }
    
    private void a() {
        kd dialog = new kd(a);
        dialog.a(e.getText().toString());
        dialog.a(h);
        
        View root = kp.a(getContext(), R.layout.property_popup_selector_color);
        ScrollView scroll = root.findViewById(R.id.scroll_view);
        i = root.findViewById(R.id.rg);
        j = root.findViewById(R.id.content);
        
        ArrayList<String> list = k.o();
        if ((ff.a(b) || ff.b(b)) && "property_text_font".equals(c)) {
            list.add(0, "default_font");
        }
        
        RadioButton selectedButton = null;
        for (String item : list) {
            RadioButton rb = a(item);
            i.addView(rb);
            
            if (item.equals(d)) {
                rb.setChecked(true);
                selectedButton = rb;
            }
            
            LinearLayout option = b(item);
            option.setOnClickListener(new AnonymousClass_1(this));
            j.addView(option);
        }
        
        if (selectedButton == null && i.getChildCount() > 0) {
            selectedButton = (RadioButton) i.getChildAt(0);
            selectedButton.setChecked(true);
        }
        
        dialog.a(root);
        dialog.a(kq.a().a(getContext(), R.string.common_word_save), new AnonymousClass_2(this, dialog));
        dialog.b(kq.a().a(getContext(), R.string.common_word_cancel), new AnonymousClass_3(this, dialog));
        dialog.setOnDismissListener(new AnonymousClass_4(this));
        dialog.setOnShowListener(new AnonymousClass_5(this, scroll, selectedButton));
        dialog.show();
    }
    
    private LinearLayout b(String text) {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setLayoutParams(new LinearLayout.LayoutParams(-1, (int) (kp.a(getContext(), 1.0f) * 60.0f)));
        layout.setGravity(19);
        layout.setOrientation(0);
        
        TextView nameView = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, -2);
        lp.weight = 1.0f;
        nameView.setLayoutParams(lp);
        nameView.setText(text);
        layout.addView(nameView);
        
        TextView fontView = new TextView(getContext());
        fontView.setLayoutParams(lp);
        fontView.setText(kq.a().a(getContext(), R.string.common_word_preview));
        
        if (!text.equalsIgnoreCase("default_font")) {
            fontView.setTypeface(Typeface.createFromFile(k.c(text)));
        } else {
            fontView.setTypeface(Typeface.DEFAULT);
        }
        layout.addView(fontView);
        return layout;
    }
    
    public String getKey() {
        return c;
    }
    
    public String getValue() {
        return d;
    }
    
    public void onClick(View v) {
        if (!ki.a()) {
            a();
        }
    }
    
    public void setKey(String key) {
        c = key;
        int id = getResources().getIdentifier(key, "string", getContext().getPackageName());
        if (id > 0) {
            e.setText(kq.a().a(getResources(), id));
            h = R.drawable.abc_96_color;
            if (m.getVisibility() == View.VISIBLE) {
                ((ImageView) findViewById(R.id.img_icon)).setImageResource(h);
                ((TextView) findViewById(R.id.tv_title)).setText(kq.a().a(getContext(), id));
            } else {
                g.setImageResource(h);
            }
        }
    }
    
    public void setOnPropertyValueChangeListener(gi listener) {
        n = listener;
    }
    
    public void setOrientationItem(int i) {
        if (i == 0) {
            l.setVisibility(View.GONE);
            m.setVisibility(View.VISIBLE);
        } else {
            l.setVisibility(View.VISIBLE);
            m.setVisibility(View.GONE);
        }
    }
    
    public void setResourceManager(mb manager) {
        k = manager;
    }
    
    public void setValue(String value) {
        d = value;
        if (value != null && !"default_font".equals(value)) {
            f.setText(value);
        } else {
            f.setText("default_font");
        }
    }
}
