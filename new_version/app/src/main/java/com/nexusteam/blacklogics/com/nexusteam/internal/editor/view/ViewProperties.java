package com.nexusteam.internal.editor.view;

import com.nexusteam.internal.er;
import com.nexusteam.internal.gh;
import com.nexusteam.internal.kp;
import com.nexusteam.internal.kq;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.nexusteam.blacklogics.R;
import java.util.ArrayList;


public class ViewProperties extends RelativeLayout {
    
    /* renamed from: a  reason: collision with root package name */
    private Spinner f1402a;
    /* access modifiers changed from: private */
    public ArrayList<String> b = new ArrayList<>();
    /* access modifiers changed from: private */
    public a c;
    private gh d = null;
    
    public ViewProperties(Context context) {
        super(context);
        a(context);
    }
    
    public ViewProperties(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }
    
    public void a(Context context) {
        kp.a(context, this, R.layout.view_properties);
        ((TextView) findViewById(R.id.btn_editproperties)).setText("T");
        this.f1402a = (Spinner) findViewById(R.id.spn_widget);
        this.c = new a(context, this.b);
        this.f1402a.setAdapter(this.c);
        this.f1402a.setSelection(0);
        this.f1402a.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
            
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                ViewProperties.this.c.a(i);
                ViewProperties.this.a((String) ViewProperties.this.b.get(i));
            }
        });
    }
    
    public void setOnPropertyTargetChangeListener(gh ghVar) {
        this.d = ghVar;
    }
    
    /* access modifiers changed from: private */
    public void a(String str) {
        if (this.d != null) {
            this.d.a(str);
        }
    }
    
    

    public void setWidgetList(ArrayList<String> viewIds) {

        this.b.clear(); 
        

        this.b.addAll(viewIds);
    }
    
    
    public class a extends BaseAdapter {
        
        /* renamed from: a  reason: collision with root package name */
        Context f1404a;
        int b;
        ArrayList<String> c;
        
        public long getItemId(int i) {
            return (long) i;
        }
        
        public a(Context context, ArrayList<String> arrayList) {
            this.f1404a = context;
            this.c = arrayList;
        }
        
        public void a(int i) {
            this.b = i;
        }
        
        public int getCount() {
            if (this.c == null) {
                return 0;
            }
            return this.c.size();
        }
        
        public Object getItem(int i) {
            return this.c.get(i);
        }
        
        private er a(int i, View view, ViewGroup viewGroup, boolean z) {
            er erVar;
            if (view != null) {
                erVar = (er) view;
            } else {
                erVar = new er(this.f1404a);
                erVar.setTextSize(R.dimen.text_size_body_small);
            }
            erVar.a(0, this.c.get(i), z);
            erVar.a(false, -12566464, -12566464);
            return erVar;
        }
        
        public View getView(int i, View view, ViewGroup viewGroup) {
            return a(i, view, viewGroup, false);
        }
        
        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            return a(i, view, viewGroup, this.b == i);
        }
    }
}
