package com.nexusteam.internal;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.sdklib.devices.DeviceWriter;
import com.nexusteam.blacklogics.R;
import com.google.android.gms.analytics.ecommerce.Promotion;
import java.util.ArrayList;
import java.util.HashMap;


public class VariableTypeSpinner extends LinearLayout {
    
    /* renamed from: a  reason: collision with root package name */
    Activity f154a;
    HashMap<Integer, ArrayList<b>> b;
    ArrayList<b> c;
    ArrayList<b> d;
    ArrayList<b> e;
    RecyclerView f;
    RecyclerView g;
    a h;
    c i;
    LinearLayout j;
    TextView k;
    Dialog l;
    b m;
    
    VariableTypeSpinner(Activity activity) {
        super(activity);
        a(activity);
    }
    
    private void a(Activity activity) {
        this.f154a = activity;
        ViewHelper.a(activity, this, R.layout.var_type_spinner);
        this.k = (TextView) findViewById(R.id.tv_preview);
        this.j = (LinearLayout) ViewHelper.a((Context) activity, (int) R.layout.var_type_spinner_dialog);
        this.f = (RecyclerView) this.j.findViewById(R.id.var_type_category);
        this.g = (RecyclerView) this.j.findViewById(R.id.var_type_list);
        ((TextView) this.j.findViewById(R.id.tv_title)).setText(StringResourceManager.a().a((Context) activity, (int) R.string.logic_editor_more_block_title_add_variable_type));
        this.b = new HashMap<>();
        this.c = new ArrayList<>();
        this.d = new ArrayList<>();
        this.e = new ArrayList<>();
        b();
        c();
        d();
        this.b.put(0, this.c);
        this.b.put(1, this.d);
        this.b.put(2, this.e);
        this.f.setHasFixedSize(true);
        this.f.setLayoutManager(new LinearLayoutManager(activity, 1, false));
        this.h = new a();
        this.h.a(this.b);
        this.f.setAdapter(this.h);
        this.g.setHasFixedSize(true);
        this.g.setLayoutManager(new LinearLayoutManager(activity, 1, false));
        this.i = new c();
        this.g.setAdapter(this.i);
        this.h.b = 0;
        this.i.a(this.b.get(0));
        setPreview(this.c.get(0));
        this.l = new Dialog(activity);
        if (this.j.getParent() != null) {
            ((ViewGroup) this.j.getParent()).removeView(this.j);
        }
        this.l.setContentView(this.j);
        findViewById(R.id.container).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!ki.a()) {
                    VariableTypeSpinner.this.a();
                }
            }
        });
    }
    
    /* access modifiers changed from: private */
    public void setPreview(b bVar) {
        this.m = bVar;
        this.k.setText(a(bVar.f159a, bVar.b));
    }
    
    public Pair<String, String> getSelectedItem() {
        return new Pair<>(this.m.f159a, this.m.b);
    }
    
    /* access modifiers changed from: private */
    public void a() {
        this.l.show();
    }
    
    class b {
        
        /* renamed from: a  reason: collision with root package name */
        public String f159a;
        public String b;
        public int c;
        
        public b(String str, String str2, int i) {
            this.f159a = str;
            this.b = str2;
            this.c = i;
        }
    }
    
    private void b() {
        this.c.add(new b("b", "", R.drawable.ic_true_false_color_48dp));
        this.c.add(new b(DeviceWriter.LOCAL_NS, "", R.drawable.numbers_48));
        this.c.add(new b("s", "", R.drawable.abc_96_color));
        this.c.add(new b("m", "varMap", R.drawable.ic_map_color_48dp));
        this.c.add(new b("m", "listInt", R.drawable.ic_list_color_48dp));
        this.c.add(new b("m", "listStr", R.drawable.ic_list_color_48dp));
        this.c.add(new b("m", "listMap", R.drawable.ic_list_color_48dp));
    }
    
    private void c() {
        this.d.add(new b("m", Promotion.ACTION_VIEW, R.drawable.layout_48));
        this.d.add(new b("m", "textview", R.drawable.widget_text_view));
        this.d.add(new b("m", "imageview", R.drawable.widget_image_view));
        this.d.add(new b("m", "checkbox", R.drawable.widget_check_box));
        this.d.add(new b("m", "switch", R.drawable.widget_switch));
        this.d.add(new b("m", "listview", R.drawable.widget_list_view));
        this.d.add(new b("m", "spinner", R.drawable.widget_spinner));
        this.d.add(new b("m", "webview", R.drawable.widget_web_view));
        this.d.add(new b("m", "seekbar", R.drawable.widget_seek_bar));
        this.d.add(new b("m", "progressbar", R.drawable.widget_progress_bar));
        this.d.add(new b("m", "calendarview", R.drawable.widget_calendar));
    }
    
    private void d() {
        this.e.add(new b("m", "intent", R.drawable.widget_intent));
        this.e.add(new b("m", "file", R.drawable.widget_shared_preference));
        this.e.add(new b("m", "calendar", R.drawable.widget_calendar));
        this.e.add(new b("m", "vibrator", R.drawable.widget_vibrator));
        this.e.add(new b("m", "timer", R.drawable.widget_timer));
        this.e.add(new b("m", "dialog", R.drawable.widget_alertdialog));
        this.e.add(new b("m", "mediaplayer", R.drawable.widget_mediaplayer));
        this.e.add(new b("m", "soundpool", R.drawable.widget_soundpool));
        this.e.add(new b("m", "objectanimator", R.drawable.widget_objectanimator));
        this.e.add(new b("m", "firebase", R.drawable.widget_firebase));
        this.e.add(new b("m", "firebaseauth", R.drawable.widget_firebase));
        this.e.add(new b("m", "firebasestorage", R.drawable.widget_firebase));
        this.e.add(new b("m", "camera", R.drawable.widget_camera));
        this.e.add(new b("m", "filepicker", R.drawable.widget_file));
        this.e.add(new b("m", "requestnetwork", R.drawable.widget_network_request));
        this.e.add(new b("m", "texttospeech", R.drawable.widget_text_to_speech));
        this.e.add(new b("m", "speechtotext", R.drawable.widget_speech_to_text));
        this.e.add(new b("m", "locationmanager", R.drawable.widget_location));
    }
    
    private String a(String var1, String var2) {
        byte var4;
        byte var5;
        label73: {
            int var3 = var1.hashCode();
            var4 = 0;
            if (var3 != 98) {
                if (var3 != 100) {
                    if (var3 == 115 && var1.equals("s")) {
                        var5 = 2;
                        break label73;
                    }
                } else if (var1.equals("d")) {
                    var5 = 1;
                    break label73;
                }
            } else if (var1.equals("b")) {
                var5 = 0;
                break label73;
            }
            
            var5 = -1;
        }
        
        switch(var5) {
            case 0:
            var1 = StringResourceManager.a().a(this.getContext(), R.string.logic_variable_type_boolean);
            break;
            case 1:
            var1 = StringResourceManager.a().a(this.getContext(), R.string.logic_variable_type_number);
            break;
            case 2:
            var1 = StringResourceManager.a().a(this.getContext(), R.string.logic_variable_type_string);
            break;
            default:
            var1 = et.a(var2);
        }
        
        label63: {
            switch(var2.hashCode()) {
                case -823676088:
                if (var2.equals("varInt")) {
                    var5 = var4;
                    break label63;
                }
                break;
                case -823672651:
                if (var2.equals("varMap")) {
                    var5 = 3;
                    break label63;
                }
                break;
                case -823666294:
                if (var2.equals("varStr")) {
                    var5 = 2;
                    break label63;
                }
                break;
                case 181944945:
                if (var2.equals("listInt")) {
                    var5 = 4;
                    break label63;
                }
                break;
                case 181948382:
                if (var2.equals("listMap")) {
                    var5 = 6;
                    break label63;
                }
                break;
                case 181954739:
                if (var2.equals("listStr")) {
                    var5 = 5;
                    break label63;
                }
                break;
                case 235637425:
                if (var2.equals("varBool")) {
                    var5 = 1;
                    break label63;
                }
            }
            
            var5 = -1;
        }
        
        switch(var5) {
            case 0:
            var1 = StringResourceManager.a().a(this.getContext(), R.string.logic_variable_type_number);
            break;
            case 1:
            var1 = StringResourceManager.a().a(this.getContext(), R.string.logic_variable_type_boolean);
            break;
            case 2:
            var1 = StringResourceManager.a().a(this.getContext(), R.string.logic_variable_type_string);
            break;
            case 3:
            var1 = StringResourceManager.a().a(this.getContext(), R.string.logic_variable_type_map);
            break;
            case 4:
            var1 = StringResourceManager.a().a(this.getContext(), R.string.logic_variable_type_list_number);
            break;
            case 5:
            var1 = StringResourceManager.a().a(this.getContext(), R.string.logic_variable_type_list_string);
            break;
            case 6:
            var1 = StringResourceManager.a().a(this.getContext(), R.string.logic_variable_type_list_map);
        }
        
        return var1;
    }
    

    class a extends RecyclerView.Adapter<a.C0001a> { // Reference inner class correctly
        
        HashMap<Integer, ArrayList<b>> f156a;
        public int b = -1;
        
        public a() {}
        
        @Override
        public C0001a onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new C0001a(LayoutInflater.from(VariableTypeSpinner.this.getContext()).inflate(R.layout.var_type_category, viewGroup, false));
        }
        
        @Override
        public void onBindViewHolder(C0001a aVar, int i) {

        }
        
        public int getItemCount() {
            return this.f156a == null ? 0 : this.f156a.size();
        }
        
        public void a(HashMap<Integer, ArrayList<b>> hashMap) {
            this.f156a = hashMap;
        }
        

        class C0001a extends RecyclerView.ViewHolder {
            public LinearLayout f157a;
            public ImageView b;
            public TextView c;
            
            public C0001a(View view) {
                super(view);
                this.f157a = (LinearLayout) view.findViewById(R.id.container);
                this.b = (ImageView) view.findViewById(R.id.icon);
                this.c = (TextView) view.findViewById(R.id.name);
                view.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        a.this.b = getLayoutPosition();
                        VariableTypeSpinner.this.i.a(VariableTypeSpinner.this.b.get(Integer.valueOf(a.this.b)));
                        VariableTypeSpinner.this.i.notifyDataSetChanged();
                        a.this.notifyDataSetChanged();
                    }
                });
            }
        }
    }
    

    class c extends RecyclerView.Adapter<c.ViewHolderA> { // Point to the correct ViewHolder class
        
        ArrayList<b> f160a;
        
        public c() {}
        
        @Override
        public ViewHolderA onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolderA(ViewHelper.a(VariableTypeSpinner.this.getContext(), (int) R.layout.var_type_spinner_item));
        }
        
        @Override
        public void onBindViewHolder(ViewHolderA aVar, int i) {
            b bVar = this.f160a.get(i);
            aVar.f161a.setText(VariableTypeSpinner.this.a(bVar.f159a, bVar.b));
            aVar.b.setImageResource(bVar.c);
        }
        
        public int getItemCount() {
            return this.f160a == null ? 0 : this.f160a.size();
        }
        
        public void a(ArrayList<b> arrayList) {
            this.f160a = arrayList;
        }
        

        class ViewHolderA extends RecyclerView.ViewHolder {
            TextView f161a;
            ImageView b;
            
            public ViewHolderA(View view) {
                super(view);
                this.f161a = (TextView) view.findViewById(R.id.name);
                this.b = (ImageView) view.findViewById(R.id.icon);
                view.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        VariableTypeSpinner.this.setPreview((b) VariableTypeSpinner.this.b.get(Integer.valueOf(VariableTypeSpinner.this.h.b)).get(getLayoutPosition()));
                        VariableTypeSpinner.this.l.hide();
                    }
                });
            }
        }
    }
}
