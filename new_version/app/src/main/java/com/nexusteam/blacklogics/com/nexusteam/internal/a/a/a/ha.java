package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.dx.io.Opcodes;
import com.nexusteam.internal.beans.BlockBean;
import com.nexusteam.internal.beans.EventBean;
import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.internal.beans.ViewBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ha extends LinearLayout implements gi {

    /* renamed from: a  reason: collision with root package name */
    String f247a;
    protected ViewBean b;
    protected ProjectFileBean c;
    protected HashMap<String, View> d = new HashMap<>();
    /* access modifiers changed from: private */
    public boolean e = false;
    private gj f;

    public ha(Context context) {
        super(context);
        a(context);
    }

    private void a(Context context) {
        gz.a().a(context);
    }

    public void setProjectFileBean(ProjectFileBean projectFileBean) {
        this.c = projectFileBean;
    }

    public void a() {
        gz.a().b();
    }

    private void a(ViewBean viewBean, String str) {
        boolean z = !viewBean.getClassInfo().a("AdView");
        boolean z2 = viewBean.id.charAt(0) == '_';
        char c2 = 65535;
        switch (str.hashCode()) {
            case -1949646187:
                if (str.equals("property_background_resource")) {
                    c2 = 22;
                    break;
                }
                break;
            case -1919612745:
                if (str.equals("property_divider_height")) {
                    c2 = ' ';
                    break;
                }
                break;
            case -1667468645:
                if (str.equals("property_layout_width")) {
                    c2 = 1;
                    break;
                }
                break;
            case -1623888455:
                if (str.equals("property_scale_type")) {
                    c2 = 21;
                    break;
                }
                break;
            case -1567696407:
                if (str.equals("property_text_size")) {
                    c2 = 11;
                    break;
                }
                break;
            case -1553367436:
                if (str.equals("property_alpha")) {
                    c2 = 26;
                    break;
                }
                break;
            case -1545963919:
                if (str.equals("property_image")) {
                    c2 = 20;
                    break;
                }
                break;
            case -1543300075:
                if (str.equals("property_lines")) {
                    c2 = 17;
                    break;
                }
                break;
            case -1474767389:
                if (str.equals("property_layout_gravity")) {
                    c2 = 8;
                    break;
                }
                break;
            case -1368558981:
                if (str.equals("property_text_color")) {
                    c2 = 13;
                    break;
                }
                break;
            case -1353621303:
                if (str.equals("property_text_style")) {
                    c2 = 12;
                    break;
                }
                break;
            case -1260841412:
                if (str.equals("property_background_color")) {
                    c2 = 23;
                    break;
                }
                break;
            case -1244048924:
                if (str.equals("property_gravity")) {
                    c2 = 7;
                    break;
                }
                break;
            case -1019734351:
                if (str.equals("property_hint")) {
                    c2 = 14;
                    break;
                }
                break;
            case -1019380393:
                if (str.equals("property_text")) {
                    c2 = 10;
                    break;
                }
                break;
            case -1018178217:
                if (str.equals("property_progress")) {
                    c2 = '$';
                    break;
                }
                break;
            case -864174086:
                if (str.equals("property_max")) {
                    c2 = '#';
                    break;
                }
                break;
            case -782258371:
                if (str.equals("property_checked")) {
                    c2 = '\"';
                    break;
                }
                break;
            case -710204242:
                if (str.equals("property_weight_sum")) {
                    c2 = 6;
                    break;
                }
                break;
            case -584915214:
                if (str.equals("property_layout_height")) {
                    c2 = 2;
                    break;
                }
                break;
            case -576300200:
                if (str.equals("property_margin")) {
                    c2 = 3;
                    break;
                }
                break;
            case -522792099:
                if (str.equals("property_ime_option")) {
                    c2 = 19;
                    break;
                }
                break;
            case -512158157:
                if (str.equals("property_spinner_mode")) {
                    c2 = 31;
                    break;
                }
                break;
            case -420171003:
                if (str.equals("property_rotate")) {
                    c2 = 25;
                    break;
                }
                break;
            case -286582750:
                if (str.equals("property_weight")) {
                    c2 = 9;
                    break;
                }
                break;
            case -78143730:
                if (str.equals("property_progressbar_style")) {
                    c2 = '\'';
                    break;
                }
                break;
            case -56658399:
                if (str.equals("property_single_line")) {
                    c2 = 16;
                    break;
                }
                break;
            case -10402863:
                if (str.equals("property_indeterminate")) {
                    c2 = '(';
                    break;
                }
                break;
            case 20737408:
                if (str.equals("property_translation_x")) {
                    c2 = 27;
                    break;
                }
                break;
            case 20737409:
                if (str.equals("property_translation_y")) {
                    c2 = 28;
                    break;
                }
                break;
            case 235805286:
                if (str.equals("property_orientation")) {
                    c2 = 5;
                    break;
                }
                break;
            case 386320985:
                if (str.equals("property_scale_x")) {
                    c2 = 29;
                    break;
                }
                break;
            case 386320986:
                if (str.equals("property_scale_y")) {
                    c2 = 30;
                    break;
                }
                break;
            case 1096920256:
                if (str.equals("property_first_day_of_week")) {
                    c2 = '%';
                    break;
                }
                break;
            case 1118712953:
                if (str.equals("property_custom_view_listview")) {
                    c2 = '!';
                    break;
                }
                break;
            case 1160800983:
                if (str.equals("property_enabled")) {
                    c2 = 24;
                    break;
                }
                break;
            case 1357596613:
                if (str.equals("property_id")) {
                    c2 = 0;
                    break;
                }
                break;
            case 1618119219:
                if (str.equals("property_ad_size")) {
                    c2 = '&';
                    break;
                }
                break;
            case 1964055463:
                if (str.equals("property_padding")) {
                    c2 = 4;
                    break;
                }
                break;
            case 2101485653:
                if (str.equals("property_hint_color")) {
                    c2 = 15;
                    break;
                }
                break;
            case 2133471033:
                if (str.equals("property_input_type")) {
                    c2 = 18;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                a(str, z2 ? viewBean.id.substring(1) : viewBean.id, z2);
                return;
            case 1:
                a(str, viewBean.layout.width, z);
                return;
            case 2:
                a(str, viewBean.layout.height, z);
                return;
            case 3:
                a(str, viewBean.layout.marginLeft, viewBean.layout.marginTop, viewBean.layout.marginRight, viewBean.layout.marginBottom);
                return;
            case 4:
                a("property_padding", viewBean.layout.paddingLeft, viewBean.layout.paddingTop, viewBean.layout.paddingRight, viewBean.layout.paddingBottom);
                return;
            case 5:
                b(str, viewBean.layout.orientation);
                return;
            case 6:
                a(str, String.valueOf(viewBean.layout.weightSum));
                return;
            case 7:
                d(str, viewBean.layout.gravity);
                return;
            case 8:
                d(str, viewBean.layout.layoutGravity);
                return;
            case 9:
                a(str, String.valueOf(viewBean.layout.weight));
                return;
            case 10:
                a(str, viewBean.text.text);
                return;
            case 11:
                b(str, viewBean.text.textSize);
                return;
            case 12:
                b(str, viewBean.text.textType);
                return;
            case 13:
                e(str, viewBean.text.textColor);
                return;
            case 14:
                a(str, viewBean.text.hint);
                return;
            case 15:
                e(str, viewBean.text.hintColor);
                return;
            case 16:
                a(str, viewBean.text.singleLine);
                return;
            case 17:
                a(str, String.valueOf(viewBean.text.line));
                return;
            case 18:
                b(str, viewBean.text.inputType);
                return;
            case 19:
                b(str, viewBean.text.imeOption);
                return;
            case 20:
                b(str, viewBean.image.resName, true);
                return;
            case 21:
                b(str, viewBean.image.scaleType);
                return;
            case 22:
                b(str, viewBean.layout.backgroundResource, false);
                return;
            case 23:
                e(str, viewBean.layout.backgroundColor);
                return;
            case 24:
                a(str, viewBean.enabled);
                return;
            case 25:
                a(str, String.valueOf(viewBean.image.rotate));
                return;
            case 26:
                a(str, String.valueOf(viewBean.alpha));
                return;
            case 27:
                a(str, String.valueOf(viewBean.translationX));
                return;
            case 28:
                a(str, String.valueOf(viewBean.translationY));
                return;
            case 29:
                a(str, String.valueOf(viewBean.scaleX));
                return;
            case 30:
                a(str, String.valueOf(viewBean.scaleY));
                return;
            case 31:
                b(str, viewBean.spinnerMode);
                return;
            case ' ':
                c(str, viewBean.dividerHeight);
                return;
            case '!':
                d(str, viewBean.customView);
                return;
            case '\"':
                a(str, viewBean.checked);
                return;
            case '#':
                a(str, String.valueOf(viewBean.max));
                return;
            case '$':
                a(str, String.valueOf(viewBean.progress));
                return;
            case '%':
                b(str, viewBean.firstDayOfWeek);
                return;
            case '&':
                b(str, viewBean.adSize);
                return;
            case '\'':
                c(str, viewBean.progressStyle);
                return;
            case '(':
                b(str, viewBean.indeterminate);
                return;
            default:
                return;
        }
    }

    public void setOnPropertyValueChangedListener(gj gjVar) {
        this.f = gjVar;
    }

    public void a(String str, ViewBean viewBean) {
        this.f247a = str;
        this.b = viewBean;
        gz.a().b(this.b.getClassInfo().a());
        removeAllViews();
        if (viewBean.id.equals("_fab")) {
            f(viewBean);
            return;
        }
        if (getOrientation() == 1) {
            a("property_id", viewBean.id);
        }
        e(viewBean);
        b(viewBean);
        c(viewBean);
        d(viewBean);
        if (getOrientation() == 0) {
            a("property_id", viewBean.id);
        }
    }

    public void a(ViewBean viewBean) {
        this.b = viewBean;
        removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        layoutParams.gravity = 3;
        setLayoutParams(layoutParams);
        setGravity(3);
        ArrayList<String> a2 = gz.a().a(viewBean.getClassInfo().a());
        if (a2 == null) {
            c();
            return;
        }
        Iterator<String> it = a2.iterator();
        while (it.hasNext()) {
            a(viewBean, it.next());
        }
    }

    private void c() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        layoutParams.gravity = 17;
        setLayoutParams(layoutParams);
        setGravity(17);
        TextView textView = new TextView(getContext());
        textView.setTextColor(getResources().getColor(R.color.grey));
        textView.setGravity(17);
        textView.setPadding(8, 8, 8, 8);
        textView.setTextSize(2, 12.0f);
        textView.setText(kq.a().a(getContext(), R.string.design_property_recent_message_nodata));
        addView(textView);
    }

    /* access modifiers changed from: protected */
    public void b() {
     /*   Intent intent = new Intent(getContext(), ManageImageActivity.class);
        intent.setFlags(536870912);
        intent.putExtra("sc_id", this.f247a);
        ((Activity) getContext()).startActivityForResult(intent, Opcodes.RSUB_INT);*/
    }

    public void b(ViewBean viewBean) {
        if (getOrientation() == 1) {
            a(kq.a().a(getResources(), R.string.property_header_layout));
        }
        hc classInfo = viewBean.getClassInfo();
        hc parentClassInfo = viewBean.getParentClassInfo();
        a(viewBean, "property_layout_width");
        a(viewBean, "property_layout_height");
        a(viewBean, "property_padding");
        a(viewBean, "property_margin");
        if (classInfo.a("LinearLayout")) {
            a(viewBean, "property_orientation");
            a(viewBean, "property_weight_sum");
            a(viewBean, "property_gravity");
        }
        if (classInfo.a("TextView")) {
            a(viewBean, "property_gravity");
        }
        if (parentClassInfo != null) {
            if (parentClassInfo.a("LinearLayout")) {
                a(viewBean, "property_layout_gravity");
                a(viewBean, "property_weight");
            }
            if (parentClassInfo.a("ScrollView") || parentClassInfo.a("HorizontalScrollView")) {
                a(viewBean, "property_layout_gravity");
            }
        }
    }

    public void c(ViewBean viewBean) {
        hc classInfo = viewBean.getClassInfo();
        if (classInfo.a("TextView")) {
            if (getOrientation() == 1) {
                a(kq.a().a(getResources(), R.string.property_header_text));
            }
            a(viewBean, "property_text");
            a(viewBean, "property_text_size");
            a(viewBean, "property_text_style");
            a(viewBean, "property_text_color");
            if (classInfo.b("EditText")) {
                a(viewBean, "property_hint");
                a(viewBean, "property_hint_color");
                if (getOrientation() == 1) {
                    a(viewBean, "property_single_line");
                }
                a(viewBean, "property_lines");
                a(viewBean, "property_input_type");
                a(viewBean, "property_ime_option");
            }
            if (classInfo.b("TextView")) {
                if (getOrientation() == 1) {
                    a(viewBean, "property_single_line");
                }
                a(viewBean, "property_lines");
            }
        }
    }

    public void d(ViewBean viewBean) {
        hc classInfo = viewBean.getClassInfo();
        viewBean.getParentClassInfo();
        if (getOrientation() == 1) {
            if (classInfo.a("ImageView")) {
                a(kq.a().a(getResources(), R.string.property_header_image), (View.OnClickListener) new View.OnClickListener() {
                    public void onClick(View view) {
                        if (!ki.a() && !ha.this.e) {
                            ha.this.b();
                        }
                    }
                });
                a(viewBean, "property_image");
                a(viewBean, "property_scale_type");
            } else {
                a(kq.a().a(getResources(), R.string.property_header_image));
            }
        } else if (classInfo.a("ImageView")) {
            a(viewBean, "property_image");
            a(viewBean, "property_scale_type");
        }
        if (viewBean.type != 18) {
            a(viewBean, "property_background_resource");
            a(viewBean, "property_background_color");
        }
        if (getOrientation() == 1 && !classInfo.b("LinearLayout") && !classInfo.b("ScrollView") && !classInfo.b("HorizontalScrollView") && !classInfo.b("ListView") && !classInfo.b("FloatingActionButton")) {
            a(viewBean, "property_enabled");
        }
        a(viewBean, "property_rotate");
        a(viewBean, "property_alpha");
        a(viewBean, "property_translation_x");
        a(viewBean, "property_translation_y");
        a(viewBean, "property_scale_x");
        a(viewBean, "property_scale_y");
    }

    public void e(ViewBean viewBean) {
        if (!viewBean.id.equals("_fab")) {
            hc classInfo = viewBean.getClassInfo();
            viewBean.getParentClassInfo();
            if (classInfo.b("Spinner")) {
                a(viewBean, "property_spinner_mode");
            }
            if (classInfo.b("ListView")) {
                a(viewBean, "property_divider_height");
                a(viewBean, "property_custom_view_listview");
            }
            if (classInfo.a("CompoundButton") && getOrientation() == 1) {
                a(viewBean, "property_checked");
            }
            if (classInfo.b("SeekBar")) {
                a(viewBean, "property_max");
                a(viewBean, "property_progress");
            }
            if (classInfo.b("CalendarView")) {
                a(viewBean, "property_first_day_of_week");
            }
            if (classInfo.b("AdView")) {
                a(viewBean, "property_ad_size");
            }
            if (classInfo.b("ProgressBar")) {
                a(viewBean, "property_max");
                a(viewBean, "property_progress");
                a(viewBean, "property_progressbar_style");
                a(viewBean, "property_indeterminate");
            }
        }
    }

    public void f(ViewBean viewBean) {
        if (getOrientation() == 1) {
            a(viewBean, "property_id");
        }
        h(viewBean);
        i(viewBean);
        if (getOrientation() == 0) {
            a(viewBean, "property_id");
        }
    }

    private void h(ViewBean viewBean) {
        viewBean.getClassInfo();
        viewBean.getParentClassInfo();
        if (getOrientation() == 1) {
            a(kq.a().a(getResources(), R.string.property_header_layout));
        }
        a(viewBean, "property_margin");
        a(viewBean, "property_layout_gravity");
    }

    private void i(ViewBean viewBean) {
        viewBean.getClassInfo();
        viewBean.getParentClassInfo();
        if (getOrientation() == 1) {
            a(kq.a().a(getResources(), R.string.property_header_image), (View.OnClickListener) new View.OnClickListener() {
                public void onClick(View view) {
                    if (!ki.a() && !ha.this.e) {
                        ha.this.b();
                    }
                }
            });
        }
        a(viewBean, "property_image");
        a(viewBean, "property_rotate");
        a(viewBean, "property_alpha");
        a(viewBean, "property_translation_x");
        a(viewBean, "property_translation_y");
        a(viewBean, "property_scale_x");
        a(viewBean, "property_scale_y");
    }

    /* access modifiers changed from: protected */
    public void a(String str) {
        gw gwVar = new gw(getContext());
        gwVar.setHeaderName(str);
        addView(gwVar);
    }

    /* access modifiers changed from: protected */
    public void a(String str, View.OnClickListener onClickListener) {
        gw gwVar = new gw(getContext());
        gwVar.setHeaderName(str);
        gwVar.setOnClickListener(onClickListener);
        addView(gwVar);
    }

    /* access modifiers changed from: protected */
    public void a(String str, int i, boolean z) {
        gq gqVar = (gq) this.d.get(str);
        int i2 = z ? 7 : 3;
        if (gqVar == null) {
            gqVar = new gq(getContext(), !this.e);
            gqVar.setOrientationItem(getOrientation());
            gqVar.setItemEnabled(i2);
            gqVar.setKey(str);
            gqVar.setValue(i);
            gqVar.setTag(str);
            gqVar.setOnPropertyValueChangeListener(this);
            this.d.put(str, gqVar);
        } else {
            gqVar.setItemEnabled(i2);
            gqVar.setValue(i);
        }
        addView(gqVar);
    }

    /* access modifiers changed from: protected */
    public void a(String str, String str2) {
        a(str, str2, this.e);
    }

    /* access modifiers changed from: protected */
    public void a(String str, String str2, boolean z) {
        gp gpVar = (gp) this.d.get(str);
        if (gpVar == null) {
            gpVar = new gp(getContext(), !z);
            gpVar.setOrientationItem(getOrientation());
            gpVar.a(this.f247a, this.c);
            gpVar.setKey(str);
            gpVar.setValue(str2);
            gpVar.setTag(str);
            gpVar.setOnPropertyValueChangeListener(this);
            this.d.put(str, gpVar);
        } else {
            gpVar.a(this.f247a, this.c);
            gpVar.setValue(str2);
        }
        addView(gpVar);
    }

    /* access modifiers changed from: protected */
    public void a(String str, int i, int i2, int i3, int i4) {
        go goVar = (go) this.d.get(str);
        if (goVar == null) {
            goVar = new go(getContext(), !this.e);
            goVar.setOrientationItem(getOrientation());
            goVar.setKey(str);
            goVar.a(i, i2, i3, i4);
            goVar.setTag(str);
            goVar.setOnPropertyValueChangeListener(this);
            this.d.put(str, goVar);
        } else {
            goVar.a(i, i2, i3, i4);
        }
        addView(goVar);
    }

    /* access modifiers changed from: protected */
    public void b(String str, String str2, boolean z) {
        gr grVar = (gr) this.d.get(str);
        if (grVar == null) {
            grVar = new gr(getContext(), !this.e, this.f247a, z);
            grVar.setOrientationItem(getOrientation());
            grVar.setKey(str);
            grVar.setValue(str2);
            grVar.setTag(str);
            grVar.setOnPropertyValueChangeListener(this);
            this.d.put(str, grVar);
        } else {
            grVar.setValue(str2);
        }
        addView(grVar);
    }

    /* access modifiers changed from: protected */
    public void a(String str, int i) {
        gy gyVar = (gy) this.d.get(str);
        boolean z = false;
        if (gyVar == null) {
            gyVar = new gy(getContext(), !this.e);
            gyVar.setOrientationItem(getOrientation());
            gyVar.setKey(str);
            if (i == 1) {
                z = true;
            }
            gyVar.setValue(z);
            gyVar.setTag(str);
            gyVar.setOnPropertyValueChangeListener(this);
            this.d.put(str, gyVar);
        } else {
            if (i == 1) {
                z = true;
            }
            gyVar.setValue(z);
        }
        addView(gyVar);
    }

    /* access modifiers changed from: protected */
    public void b(String str, int i) {
        gs gsVar = (gs) this.d.get(str);
        if (gsVar == null) {
            gsVar = new gs(getContext(), !this.e);
            gsVar.setOrientationItem(getOrientation());
            gsVar.setKey(str);
            gsVar.setValue(i);
            gsVar.setTag(str);
            gsVar.setOnPropertyValueChangeListener(this);
            this.d.put(str, gsVar);
        } else {
            gsVar.setValue(i);
        }
        addView(gsVar);
    }

    /* access modifiers changed from: protected */
    public void c(String str, int i) {
        gt gtVar = (gt) this.d.get(str);
        if (gtVar == null) {
            gtVar = new gt(getContext(), !this.e);
            gtVar.setOrientationItem(getOrientation());
            gtVar.setKey(str);
            gtVar.setValue(i);
            gtVar.setTag(str);
            gtVar.setOnPropertyValueChangeListener(this);
            this.d.put(str, gtVar);
        } else {
            gtVar.setValue(i);
        }
        addView(gtVar);
    }

    /* access modifiers changed from: protected */
    public void b(String str, String str2) {
        gv gvVar = (gv) this.d.get(str);
        if (gvVar == null) {
            gvVar = new gv(getContext(), !this.e);
            gvVar.setOrientationItem(getOrientation());
            gvVar.setKey(str);
            gvVar.setValue(str2);
            gvVar.setTag(str);
            gvVar.setOnPropertyValueChangeListener(this);
            this.d.put(str, gvVar);
        } else {
            gvVar.setValue(str2);
        }
        addView(gvVar);
    }

    /* access modifiers changed from: protected */
    public void c(String str, String str2) {
        gu guVar = (gu) this.d.get(str);
        if (guVar == null) {
            guVar = new gu(getContext(), !this.e);
            guVar.setOrientationItem(getOrientation());
            guVar.setKey(str);
            guVar.setValue(str2);
            guVar.setTag(str);
            guVar.setOnPropertyValueChangeListener(this);
            this.d.put(str, guVar);
        } else {
            guVar.setValue(str2);
        }
        addView(guVar);
    }

    /* access modifiers changed from: protected */
    public void d(String str, String str2) {
        gl glVar = (gl) this.d.get(str);
        if (glVar == null) {
            glVar = new gl(getContext(), !this.e);
            glVar.setOrientationItem(getOrientation());
            glVar.setKey(str);
            glVar.setTag(str);
            glVar.setOnPropertyValueChangeListener(this);
            this.d.put(str, glVar);
        }
        glVar.setCustomView(ma.b(this.f247a).b());
        glVar.setValue(str2);
        addView(glVar);
    }

    /* access modifiers changed from: protected */
    public void d(String str, int i) {
        gn gnVar = (gn) this.d.get(str);
        if (gnVar == null) {
            gnVar = new gn(getContext(), !this.e);
            gnVar.setOrientationItem(getOrientation());
            gnVar.setKey(str);
            gnVar.setValue(i);
            gnVar.setTag(str);
            gnVar.setOnPropertyValueChangeListener(this);
            this.d.put(str, gnVar);
        } else {
            gnVar.setValue(i);
        }
        addView(gnVar);
    }

    /* access modifiers changed from: protected */
    public void e(String str, int i) {
        gk gkVar = (gk) this.d.get(str);
        if (gkVar == null) {
            gkVar = new gk(getContext(), !this.e);
            gkVar.setOrientationItem(getOrientation());
            gkVar.setKey(str);
            gkVar.setValue(i);
            gkVar.setTag(str);
            gkVar.setOnPropertyValueChangeListener(this);
            this.d.put(str, gkVar);
        } else {
            gkVar.setValue(i);
        }
        addView(gkVar);
    }

    public void g(ViewBean viewBean) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof gp) {
                gp gpVar = (gp) childAt;
                if (gpVar.getKey().equals("property_id")) {
                    viewBean.preId = viewBean.id;
                    if (viewBean.id.charAt(0) != '_') {
                        viewBean.id = gpVar.getValue().toLowerCase();
                    }
                } else if (gpVar.getKey().equals("property_text")) {
                    viewBean.text.text = gpVar.getValue();
                } else if (gpVar.getKey().equals("property_hint")) {
                    viewBean.text.hint = gpVar.getValue();
                } else if (gpVar.getKey().equals("property_weight")) {
                    viewBean.layout.weight = Integer.valueOf(gpVar.getValue()).intValue();
                } else if (gpVar.getKey().equals("property_weight_sum")) {
                    viewBean.layout.weightSum = Integer.valueOf(gpVar.getValue()).intValue();
                } else if (gpVar.getKey().equals("property_rotate")) {
                    viewBean.image.rotate = Integer.valueOf(gpVar.getValue()).intValue();
                } else if (gpVar.getKey().equals("property_alpha")) {
                    viewBean.alpha = Float.valueOf(gpVar.getValue()).floatValue();
                } else if (gpVar.getKey().equals("property_translation_x")) {
                    viewBean.translationX = Float.valueOf(gpVar.getValue()).floatValue();
                } else if (gpVar.getKey().equals("property_translation_y")) {
                    viewBean.translationY = Float.valueOf(gpVar.getValue()).floatValue();
                } else if (gpVar.getKey().equals("property_scale_x")) {
                    viewBean.scaleX = Float.valueOf(gpVar.getValue()).floatValue();
                } else if (gpVar.getKey().equals("property_scale_y")) {
                    viewBean.scaleY = Float.valueOf(gpVar.getValue()).floatValue();
                } else if (gpVar.getKey().equals("property_lines")) {
                    viewBean.text.line = Integer.valueOf(gpVar.getValue()).intValue();
                } else if (gpVar.getKey().equals("property_max")) {
                    viewBean.max = Integer.valueOf(gpVar.getValue()).intValue();
                } else if (gpVar.getKey().equals("property_progress")) {
                    viewBean.progress = Integer.valueOf(gpVar.getValue()).intValue();
                }
            } else if (childAt instanceof gq) {
                gq gqVar = (gq) childAt;
                if (gqVar.getKey().equals("property_layout_width")) {
                    viewBean.layout.width = gqVar.getValue();
                } else if (gqVar.getKey().equals("property_layout_height")) {
                    viewBean.layout.height = gqVar.getValue();
                }
            } else if (childAt instanceof gs) {
                gs gsVar = (gs) childAt;
                if (gsVar.getKey().equals("property_orientation")) {
                    viewBean.layout.orientation = gsVar.getValue();
                } else if (gsVar.getKey().equals("property_text_style")) {
                    viewBean.text.textType = gsVar.getValue();
                } else if (gsVar.getKey().equals("property_text_size")) {
                    viewBean.text.textSize = gsVar.getValue();
                } else if (gsVar.getKey().equals("property_input_type")) {
                    viewBean.text.inputType = gsVar.getValue();
                } else if (gsVar.getKey().equals("property_ime_option")) {
                    viewBean.text.imeOption = gsVar.getValue();
                } else if (gsVar.getKey().equals("property_spinner_mode")) {
                    viewBean.spinnerMode = gsVar.getValue();
                } else if (gsVar.getKey().equals("property_first_day_of_week")) {
                    viewBean.firstDayOfWeek = gsVar.getValue();
                }
            } else if (childAt instanceof gv) {
                gv gvVar = (gv) childAt;
                if (gvVar.getKey().equals("property_scale_type")) {
                    viewBean.image.scaleType = gvVar.getValue();
                } else if (gvVar.getKey().equals("property_ad_size")) {
                    viewBean.adSize = gvVar.getValue();
                } else if (gvVar.getKey().equals("property_indeterminate")) {
                    viewBean.indeterminate = gvVar.getValue();
                }
            } else if (childAt instanceof gu) {
                gu guVar = (gu) childAt;
                if (guVar.getKey().equals("property_progressbar_style")) {
                    viewBean.progressStyle = guVar.getValue();
                }
            } else if (childAt instanceof gl) {
                gl glVar = (gl) childAt;
                if (glVar.getKey().equals("property_custom_view_listview")) {
                    viewBean.customView = glVar.getValue();
                }
            } else if (childAt instanceof gy) {
                gy gyVar = (gy) childAt;
                if (gyVar.getKey().equals("property_single_line")) {
                    viewBean.text.singleLine = gyVar.getValue() ? 1 : 0;
                } else if (gyVar.getKey().equals("property_enabled")) {
                    viewBean.enabled = gyVar.getValue() ? 1 : 0;
                } else if (gyVar.getKey().equals("property_clickable")) {
                    viewBean.clickable = gyVar.getValue() ? 1 : 0;
                } else if (gyVar.getKey().equals("property_checked")) {
                    viewBean.checked = gyVar.getValue() ? 1 : 0;
                }
            } else if (childAt instanceof gk) {
                gk gkVar = (gk) childAt;
                if (gkVar.getKey().equals("property_text_color")) {
                    viewBean.text.textColor = gkVar.getValue();
                } else if (gkVar.getKey().equals("property_hint_color")) {
                    viewBean.text.hintColor = gkVar.getValue();
                } else if (gkVar.getKey().equals("property_background_color")) {
                    viewBean.layout.backgroundColor = gkVar.getValue();
                }
            } else if (childAt instanceof go) {
                go goVar = (go) childAt;
                if (goVar.getKey().equals("property_margin")) {
                    viewBean.layout.marginLeft = goVar.b;
                    viewBean.layout.marginTop = goVar.c;
                    viewBean.layout.marginRight = goVar.d;
                    viewBean.layout.marginBottom = goVar.e;
                } else if (goVar.getKey().equals("property_padding")) {
                    viewBean.layout.paddingLeft = goVar.b;
                    viewBean.layout.paddingTop = goVar.c;
                    viewBean.layout.paddingRight = goVar.d;
                    viewBean.layout.paddingBottom = goVar.e;
                }
            } else if (childAt instanceof gn) {
                gn gnVar = (gn) childAt;
                if (gnVar.getKey().equals("property_gravity")) {
                    viewBean.layout.gravity = gnVar.getValue();
                } else if (gnVar.getKey().equals("property_layout_gravity")) {
                    viewBean.layout.layoutGravity = gnVar.getValue();
                }
            } else if (childAt instanceof gr) {
                gr grVar = (gr) childAt;
                if (grVar.getKey().equals("property_image")) {
                    viewBean.image.resName = grVar.getValue();
                } else if (grVar.getKey().equals("property_background_resource")) {
                    viewBean.layout.backgroundResource = grVar.getValue();
                }
            } else if (childAt instanceof gt) {
                gt gtVar = (gt) childAt;
                if (gtVar.getKey().equals("property_divider_height")) {
                    viewBean.dividerHeight = gtVar.getValue();
                }
            } else if (childAt instanceof gm) {
                gm gmVar = (gm) childAt;
                if (gmVar.getKey().equals("property_text_font")) {
                    viewBean.text.textFont = gmVar.getValue();
                }
            }
        }
        if (!viewBean.id.equals(viewBean.preId)) {
            Iterator<ViewBean> it = ma.a(this.f247a).a(this.c.getXmlName()).iterator();
            while (it.hasNext()) {
                ViewBean next = it.next();
                if (next.parent.equals(viewBean.preId)) {
                    next.parent = viewBean.id;
                }
            }
            if (this.c.fileType == 0) {
                Iterator<EventBean> it2 = ma.a(this.f247a).j(this.c.getJavaName()).iterator();
                while (it2.hasNext()) {
                    EventBean next2 = it2.next();
                    if (next2.targetId.equals(viewBean.preId)) {
                        next2.targetId = viewBean.id;
                    }
                }
                HashMap<String, ArrayList<BlockBean>> l = ma.a(this.f247a).l(this.c.getJavaName());
                for (String str : ex.a(viewBean.getClassInfo())) {
                    String str2 = viewBean.preId + EventBean.SEPARATOR + str;
                    if (l.containsKey(str2)) {
                        l.put(viewBean.id + EventBean.SEPARATOR + str, l.get(str2));
                        l.remove(str2);
                    }
                }
                ArrayList arrayList = new ArrayList();
                if (l.containsKey("onCreate_initializeLogic")) {
                    arrayList.add("onCreate_initializeLogic");
                }
                Iterator<Pair<String, String>> it3 = ma.a(this.f247a).h(this.c.getJavaName()).iterator();
                while (it3.hasNext()) {
                    String str3 = ((String) it3.next().first) + EventBean.SEPARATOR + "moreBlock";
                    if (l.containsKey(str3)) {
                        arrayList.add(str3);
                    }
                }
                Iterator<EventBean> it4 = ma.a(this.f247a).j(this.c.getJavaName()).iterator();
                while (it4.hasNext()) {
                    EventBean next3 = it4.next();
                    if (!next3.eventName.equals("onBindCustomView")) {
                        String eventKey = next3.getEventKey();
                        if (l.containsKey(eventKey)) {
                            arrayList.add(eventKey);
                        }
                    }
                }
                Iterator it5 = arrayList.iterator();
                while (it5.hasNext()) {
                    ArrayList arrayList2 = l.get((String) it5.next());
                    if (arrayList2 != null) {
                        Iterator it6 = arrayList2.iterator();
                        while (it6.hasNext()) {
                            BlockBean blockBean = (BlockBean) it6.next();
                            hc classInfo = blockBean.getClassInfo();
                            if (classInfo == null || !classInfo.d() || !blockBean.spec.equals(viewBean.preId)) {
                                ArrayList<hc> paramClassInfo = blockBean.getParamClassInfo();
                                if (paramClassInfo != null && paramClassInfo.size() > 0) {
                                    for (int i2 = 0; i2 < paramClassInfo.size(); i2++) {
                                        if (paramClassInfo.get(i2).d() && blockBean.parameters.get(i2).equals(viewBean.preId)) {
                                            blockBean.parameters.set(i2, viewBean.id);
                                        }
                                    }
                                }
                            } else {
                                blockBean.spec = viewBean.id;
                            }
                        }
                    }
                }
            } else {
                ArrayList arrayList3 = new ArrayList();
                Iterator<ProjectFileBean> it7 = ma.b(this.f247a).a().iterator();
                while (it7.hasNext()) {
                    ProjectFileBean next4 = it7.next();
                    Iterator<ViewBean> it8 = ma.a(this.f247a).b(next4.getXmlName()).iterator();
                    while (it8.hasNext()) {
                        ViewBean next5 = it8.next();
                        if (next5.customView.equals(this.c.fileName)) {
                            arrayList3.add(new Pair(next4.getJavaName(), next5.id + EventBean.SEPARATOR + "onBindCustomView"));
                        }
                    }
                }
                Iterator it9 = arrayList3.iterator();
                while (it9.hasNext()) {
                    Pair pair = (Pair) it9.next();
                    ArrayList arrayList4 = ma.a(this.f247a).l((String) pair.first).get(pair.second);
                    if (arrayList4 != null) {
                        Iterator it10 = arrayList4.iterator();
                        while (it10.hasNext()) {
                            BlockBean blockBean2 = (BlockBean) it10.next();
                            hc classInfo2 = blockBean2.getClassInfo();
                            if (classInfo2 == null || !classInfo2.d() || !blockBean2.spec.equals(viewBean.preId)) {
                                ArrayList<hc> paramClassInfo2 = blockBean2.getParamClassInfo();
                                if (paramClassInfo2 != null && paramClassInfo2.size() > 0) {
                                    for (int i3 = 0; i3 < paramClassInfo2.size(); i3++) {
                                        if (paramClassInfo2.get(i3).d() && blockBean2.parameters.get(i3).equals(viewBean.preId)) {
                                            blockBean2.parameters.set(i3, viewBean.id);
                                        }
                                    }
                                }
                            } else {
                                blockBean2.spec = viewBean.id;
                            }
                        }
                    }
                }
            }
        }
        if (viewBean.type == 9) {
            ViewBean e2 = ma.a(this.f247a).e(this.c.getXmlName(), viewBean.preId);
            if (viewBean.customView != null && e2 != null && e2.customView != null && !e2.customView.equals(viewBean.customView)) {
                ArrayList<EventBean> j = ma.a(this.f247a).j(this.c.getJavaName());
                int size = j.size();
                while (true) {
                    size--;
                    if (size < 0) {
                        break;
                    }
                    EventBean eventBean = j.get(size);
                    if (eventBean.targetId.equals(viewBean.id) && eventBean.eventName.equals("onBindCustomView")) {
                        j.remove(eventBean);
                        HashMap<String, ArrayList<BlockBean>> l2 = ma.a(this.f247a).l(this.c.getJavaName());
                        if (l2.containsKey(eventBean.getEventKey())) {
                            l2.remove(eventBean.getEventKey());
                        }
                    }
                }
                if (viewBean.customView.equals("") || viewBean.customView.equals("none")) {
                    for (Map.Entry<String, ArrayList<BlockBean>> value : ma.a(this.f247a).l(this.c.getJavaName()).entrySet()) {
                        Iterator it11 = ((ArrayList) value.getValue()).iterator();
                        while (it11.hasNext()) {
                            BlockBean blockBean3 = (BlockBean) it11.next();
                            if ("listSetCustomViewData".equals(blockBean3.opCode) && viewBean.id.equals(blockBean3.parameters.get(0))) {
                                blockBean3.parameters.set(0, "");
                            }
                        }
                    }
                }
            }
        }
    }

    public void a(String str, Object obj) {
        gz.a().a(this.b.getClassInfo().a(), str);
        if (this.f != null) {
            ViewBean clone = this.b.clone();
            g(this.b);
            if (!this.e) {
                lv.a(this.f247a).a(this.c.getXmlName(), clone, this.b.clone());
                this.f.a(this.b);
            }
        }
    }
}
