package com.nexusteam.internal;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import com.google.android.material.textfield.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.sdklib.devices.DeviceWriter;
import com.nexusteam.blacklogics.R;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.android.gms.plus.PlusShare;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


public class BlockMakerLayout extends LinearLayout {
    
    /* renamed from: a  reason: collision with root package name */
    Activity f149a;
    RelativeLayout b;
    LinearLayout c;
    TextInputLayout d;
    TextInputLayout e;
    TextInputLayout f;
    EditText g;
    EditText h;
    EditText i;
    LinearLayout j;
    VariableTypeSpinner k;
    ArrayList<Pair<String, String>> l = new ArrayList<>();
    lr m;
    lr n;
    lr o;
    fm p;
    int i2;
    
    public BlockMakerLayout(Activity activity) {
        super(activity);
        a(activity);
    }
    
    public void setFuncNameValidator(ArrayList<String> arrayList) {
        this.o = new lr(this.f149a, this.d, fc.b, fc.c(), arrayList);
    }
    
    private void a(Activity activity) {
        this.f149a = activity;
        LayoutInflater.from(activity).inflate(R.layout.make_block_layout, this);
        this.j = (LinearLayout) findViewById(R.id.var_type_spinner);
        this.b = (RelativeLayout) findViewById(R.id.block_area);
        this.c = (LinearLayout) findViewById(R.id.remove_area);
        this.k = new VariableTypeSpinner(activity);
        this.j.addView(this.k);
        this.d = (TextInputLayout) findViewById(R.id.ti_name);
        this.e = (TextInputLayout) findViewById(R.id.ti_label);
        this.f = (TextInputLayout) findViewById(R.id.ti_variable_name);
        ((TextView) findViewById(R.id.tv_title_blockname)).setText(StringResourceManager.a().a((Context) activity, (int) R.string.logic_editor_more_block_title_name_of_block));
        ((TextView) findViewById(R.id.tv_title_add_variable)).setText(StringResourceManager.a().a((Context) activity, (int) R.string.logic_editor_more_block_title_add_variable));
        this.o = new lr(activity, this.d, fc.b, fc.c(), new ArrayList());
        this.n = new lr(activity, this.e, fc.b, fc.c(), new ArrayList());
        this.m = new lr(activity, this.f, fc.b, fc.c(), new ArrayList());
        this.g = (EditText) findViewById(R.id.ed_name);
        this.h = (EditText) findViewById(R.id.ed_label);
        this.i = (EditText) findViewById(R.id.ed_variable_name);
        this.d.setHint(StringResourceManager.a().a((Context) activity, (int) R.string.logic_editor_more_block_hint_enter_new_block_name));
        this.f.setHint(StringResourceManager.a().a((Context) activity, (int) R.string.logic_editor_more_block_hint_enter_variable_name));
        this.e.setHint(StringResourceManager.a().a((Context) activity, (int) R.string.logic_editor_more_block_hint_enter_block_label));
        this.g.setPrivateImeOptions("defaultInputmode=english;");
        this.h.setPrivateImeOptions("defaultInputmode=english;");
        this.i.setPrivateImeOptions("defaultInputmode=english;");
        this.g.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
            
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
            
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equals("") || BlockMakerLayout.this.o.a()) {
                    BlockMakerLayout.this.a(BlockMakerLayout.this.b, BlockMakerLayout.this.c, BlockMakerLayout.this.p, editable.toString(), BlockMakerLayout.this.l);
                }
            }
        });
        Button button = (Button) findViewById(R.id.add_variable);
        button.setText(StringResourceManager.a().a((Context) activity, (int) R.string.logic_editor_more_block_button_add));
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!ki.a() && BlockMakerLayout.this.m.a() && BlockMakerLayout.this.o.a()) {
                    Pair<String, String> selectedItem = BlockMakerLayout.this.k.getSelectedItem();
                    String str = (String) selectedItem.first;
                    if (((String) selectedItem.second).length() > 0) {
                        str = str + "." + ((String) selectedItem.second);
                    }
                    BlockMakerLayout.this.l.add(new Pair(str, BlockMakerLayout.this.i.getText().toString()));
                    BlockMakerLayout.this.a(BlockMakerLayout.this.b, BlockMakerLayout.this.c, BlockMakerLayout.this.p, BlockMakerLayout.this.g.getText().toString(), BlockMakerLayout.this.l);
                    ArrayList arrayList = new ArrayList(Arrays.asList(fc.c()));
                    Iterator<Pair<String, String>> it = BlockMakerLayout.this.l.iterator();
                    while (it.hasNext()) {
                        Pair next = it.next();
                        if (!((String) next.first).equals("t")) {
                            arrayList.add(next.second);
                        }
                    }
                    BlockMakerLayout.this.m.a((String[]) arrayList.toArray(new String[arrayList.size()]));
                    BlockMakerLayout.this.i.setText("");
                }
            }
        });
        Button button2 = (Button) findViewById(R.id.add_label);
        button2.setText(StringResourceManager.a().a((Context) activity, (int) R.string.logic_editor_more_block_button_add));
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!ki.a() && BlockMakerLayout.this.n.a() && BlockMakerLayout.this.o.a()) {
                    BlockMakerLayout.this.l.add(new Pair("t", BlockMakerLayout.this.h.getText().toString()));
                    BlockMakerLayout.this.a(BlockMakerLayout.this.b, BlockMakerLayout.this.c, BlockMakerLayout.this.p, BlockMakerLayout.this.g.getText().toString(), BlockMakerLayout.this.l);
                    BlockMakerLayout.this.h.setText("");
                }
            }
        });
        this.p = new fm(activity, 0, "", " ", "definedFunc");
        this.b.addView(this.p);
    }
    
    public boolean a() {
        if (this.g.getText().toString().isEmpty()) {
            ke.b(getContext(), (CharSequence) StringResourceManager.a().a(getContext(), (int) R.string.logic_editor_message_name_requied), 0).show();
            return false;
        } else if (this.o.a()) {
            return true;
        } else {
            ke.b(getContext(), (CharSequence) StringResourceManager.a().a(getContext(), (int) R.string.logic_editor_message_name_requied), 0).show();
            return false;
        }
    }
    
    public boolean b() {
        return this.g.getText().toString().isEmpty() && this.l.size() == 0;
    }
    
    public Pair<String, String> getBlockInformation() {
        return new Pair<>(this.g.getText().toString().trim(), this.p.a);
    }
    
    /* access modifiers changed from: private */
    public void a(ViewGroup viewGroup, ViewGroup viewGroup2, fm fmVar, String str, ArrayList<Pair<String, String>> arrayList) {
        ViewGroup viewGroup3 = viewGroup;
        fm fmVar2 = fmVar;
        ArrayList<Pair<String, String>> arrayList2 = arrayList;
        viewGroup.removeAllViews();
        viewGroup3.addView(fmVar2);
        Iterator<Pair<String, String>> it = arrayList.iterator();
        String str2 = str;
        while (it.hasNext()) {
            Pair next = it.next();
            if (((String) next.first).equals("b")) {
                str2 = str2 + " %b." + ((String) next.second);
            } else if (((String) next.first).equals(DeviceWriter.LOCAL_NS)) {
                str2 = str2 + " %d." + ((String) next.second);
            } else if (((String) next.first).equals("s")) {
                str2 = str2 + " %s." + ((String) next.second);
            } else if (((String) next.first).length() <= 2 || ((String) next.first).indexOf(".") < 0) {
                str2 = str2 + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + ((String) next.second);
            } else {
                str2 = str2 + " %" + ((String) next.first) + "." + ((String) next.second);
            }
        }
        fmVar2.setSpec(str2);
        int size = arrayList.size();
        boolean z = false;
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            Pair pair = arrayList2.get(i4);
            if (((String) pair.first).equals("b")) {
                fm fmVar3 = new fm(this.f149a, arrayList2.indexOf(pair) + 1, (String) pair.second, "b", "getArg");
                viewGroup3.addView(fmVar3);
                i2 = i3 + 1;
                fmVar2.a((fo) fmVar2.c.get(i3), fmVar3);
            } else if (((String) pair.first).equals(DeviceWriter.LOCAL_NS)) {
                fm fmVar4 = new fm(this.f149a, arrayList2.indexOf(pair) + 1, (String) pair.second, DeviceWriter.LOCAL_NS, "getArg");
                viewGroup3.addView(fmVar4);
                i2 = i3 + 1;
                fmVar2.a((fo) fmVar2.c.get(i3), fmVar4);
            } else if (((String) pair.first).equals("s")) {
                fm fmVar5 = new fm(this.f149a, arrayList2.indexOf(pair) + 1, (String) pair.second, "s", "getArg");
                viewGroup3.addView(fmVar5);
                i2 = i3 + 1;
                fmVar2.a((fo) fmVar2.c.get(i3), fmVar5);
            } else {
                if (((String) pair.first).length() > 2) {
                    String substring = ((String) pair.first).substring(((String) pair.first).indexOf(".") + 1);
                    fm fmVar6 = new fm(this.f149a, arrayList2.indexOf(pair) + 1, (String) pair.second, et.b(substring), et.a(substring), "getArg");
                    viewGroup3.addView(fmVar6);
                    fmVar2.a((fo) fmVar2.c.get(i3), fmVar6);
                    i3++;
                }
            }
            i3 = i2;
        }
        fmVar.b();
        viewGroup2.removeAllViews();
        int size2 = fmVar2.o.size();
        int i5 = 0;
        while (i5 < size2) {
            View view = fmVar2.o.get(i5);
            int a2 = fmVar2.p.get(i5).equals(PlusShare.KEY_CALL_TO_ACTION_LABEL) ? a((TextView) view) : 0;
            if (view instanceof fm) {
                a2 = ((fm) view).getWidthSum();
            }
            ImageView imageView = new ImageView(this.f149a);
            imageView.setImageResource(R.drawable.ic_remove_grey600_24dp);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            
            imageView.setPadding(z ? 1 : 0, (int) ViewHelper.a((Context) this.f149a, 4.0f), z ? 1 : 0, (int) ViewHelper.a((Context) this.f149a, 4.0f));
            imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (((float) a2) + ViewHelper.a((Context) this.f149a, 4.0f)), -1));
            ViewGroup viewGroup4 = viewGroup2;
            viewGroup4.addView(imageView);
            if (i5 != 0 || this.g.getText().length() <= 0) {
                final ArrayList<Pair<String, String>> arrayList3 = arrayList2;
                final ViewGroup viewGroup5 = viewGroup4;
                final ViewGroup viewGroup6 = viewGroup3;
                final fm fmVar7 = fmVar2;
                final String str3 = str;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int index = viewGroup5.indexOfChild(view);
                        if (BlockMakerLayout.this.g.getText().length() > 0) {
                            arrayList3.remove(index - 1);
                        } else {
                            arrayList3.remove(index);
                        }
                        
                        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(fc.c()));
                        for (Pair<String, String> pair : arrayList3) {
                            if (!pair.first.equals("t")) {
                                arrayList.add(pair.second);
                            }
                        }
                        BlockMakerLayout.this.m.a(arrayList.toArray(new String[0]));
                        BlockMakerLayout.this.a(viewGroup6, viewGroup5, fmVar7, str3, arrayList3);
                    }
                });
            } else {
                imageView.setVisibility(4);
                imageView.setEnabled(z);
            }
            i5++;
            z = false;
        }
    }
    
    private int a(TextView textView) {
        Rect rect = new Rect();
        textView.getPaint().getTextBounds(textView.getText().toString(), 0, textView.getText().length(), rect);
        return rect.width();
    }
}
