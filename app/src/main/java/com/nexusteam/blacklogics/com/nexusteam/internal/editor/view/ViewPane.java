package com.nexusteam.internal.editor.view;

import com.nexusteam.internal.Cif;
import com.nexusteam.internal.hc;
import com.nexusteam.internal.hq;
import com.nexusteam.internal.hr;
import com.nexusteam.internal.hs;
import com.nexusteam.internal.ht;
import com.nexusteam.internal.hu;
import com.nexusteam.internal.hv;
import com.nexusteam.internal.hw;
import com.nexusteam.internal.hx;
import com.nexusteam.internal.hy;
import com.nexusteam.internal.hz;
import com.nexusteam.internal.ia;
import com.nexusteam.internal.ib;
import com.nexusteam.internal.ic;
import com.nexusteam.internal.id;
import com.nexusteam.internal.ie;
import com.nexusteam.internal.ig;
import com.nexusteam.internal.ih;
import com.nexusteam.internal.ii;
import com.nexusteam.internal.ij;
import com.nexusteam.internal.kp;
import com.nexusteam.internal.ks;
import com.nexusteam.internal.mb;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.NinePatchDrawable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.nexusteam.internal.beans.ProjectResourceBean;
import com.nexusteam.internal.beans.ViewBean;
import com.nexusteam.blacklogics.R;
import java.util.ArrayList;

public class ViewPane extends RelativeLayout {
    
    /* renamed from: a  reason: collision with root package name */
    private ViewGroup f1401a = null;
    private int b = 99;
    private ArrayList<Object[]> c = new ArrayList<>();
    private Object[] d = null;
    private TextView e;
    private mb f;
    
    public ViewPane(Context context) {
        super(context);
        a(context);
    }
    
    public ViewPane(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }
    
    private void a(Context context) {
        setBackgroundColor(-1);
        b();
        e();
    }
    
    private void e() {
        this.e = new TextView(getContext());
        this.e.setBackgroundResource(R.drawable.highlight);
        this.e.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        this.e.setVisibility(8);
    }
    
    public View a(ViewBean viewBean) {
        View findViewWithTag = findViewWithTag("_fab");
        if (findViewWithTag != null) {
            return findViewWithTag;
        }
        hx hxVar = new hx(getContext());
        hxVar.setTag("_fab");
        hxVar.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
        hxVar.setFixed(true);
        if (viewBean == null) {
            ViewBean viewBean2 = new ViewBean("_fab", 16);
            viewBean2.layout.marginLeft = 16;
            viewBean2.layout.marginTop = 16;
            viewBean2.layout.marginRight = 16;
            viewBean2.layout.marginBottom = 16;
            viewBean2.layout.layoutGravity = 85;
            hxVar.setBean(viewBean2);
        } else {
            hxVar.setBean(viewBean);
        }
        addView(hxVar);
        a((View) hxVar, hxVar.getBean());
        return hxVar;
    }
    
    public hx getFab() {
        View findViewWithTag = findViewWithTag("_fab");
        if (findViewWithTag == null) {
            return null;
        }
        return (hx) findViewWithTag;
    }
    
    public void a() {
        View findViewWithTag = findViewWithTag("_fab");
        if (findViewWithTag != null) {
            removeView(findViewWithTag);
        }
    }
    
    public void setResourceManager(mb mbVar) {
        this.f = mbVar;
    }
    
    public mb getResourceManager() {
        return this.f;
    }
    
    public void b() {
        ViewBean viewBean = new ViewBean("root", 0);
        viewBean.layout.width = -1;
        viewBean.layout.height = -1;
        viewBean.layout.orientation = 1;
        viewBean.parentType = 0;
        View d2 = d(viewBean);
        ((ia) d2).setFixed(true);
        this.f1401a = (ViewGroup) d2;
        this.f1401a.setBackgroundColor(-1118482);
        addView(d2);
    }
    
    public ViewGroup getRoot() {
        return this.f1401a;
    }
    
    public hq a(String str) {
        View view;
        if (str.charAt(0) == '_') {
            view = findViewWithTag(str);
        } else {
            view = this.f1401a.findViewWithTag(str);
        }
        if (view != null && (view instanceof hq)) {
            return (hq) view;
        }
        return null;
    }
    
    public void c() {
        this.f1401a.removeAllViews();
    }
    
    public void a(ViewBean viewBean, int i, int i2) {
        if (this.d != null) {
            View view = (View) this.d[1];
            if (view instanceof LinearLayout) {
                viewBean.preIndex = viewBean.index;
                viewBean.index = ((Integer) this.d[2]).intValue();
                viewBean.preParent = viewBean.parent;
                viewBean.parent = view.getTag().toString();
                viewBean.parentType = 0;
            } else if (view instanceof ii) {
                viewBean.preIndex = viewBean.index;
                viewBean.index = ((Integer) this.d[2]).intValue();
                viewBean.preParent = viewBean.parent;
                viewBean.parent = view.getTag().toString();
                viewBean.parentType = 12;
                viewBean.layout.height = -2;
            } else if (view instanceof hy) {
                viewBean.preIndex = viewBean.index;
                viewBean.index = ((Integer) this.d[2]).intValue();
                viewBean.preParent = viewBean.parent;
                viewBean.parent = view.getTag().toString();
                viewBean.parentType = 2;
                viewBean.layout.width = -2;
            }
        } else {
            viewBean.preIndex = viewBean.index;
            viewBean.preParent = viewBean.parent;
            viewBean.parent = "root";
            viewBean.parentType = 0;
            viewBean.index = -1;
        }
    }
    
    public void a(boolean z) {
        this.e.setVisibility(8);
        ViewParent parent = this.e.getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(this.e);
        }
        if (z) {
            this.d = null;
        }
    }
    
    public void a(int i, int i2, int i3, int i4) {
        Object[] a2 = a(i, i2);
        if (a2 == null) {
            a(true);
        } else if (this.d != a2) {
            a(true);
            ViewGroup viewGroup = (ViewGroup) a2[1];
            viewGroup.addView(this.e, ((Integer) a2[2]).intValue());
            if (viewGroup instanceof LinearLayout) {
                this.e.setLayoutParams(new LinearLayout.LayoutParams(i3, i4));
            } else if (viewGroup instanceof FrameLayout) {
                this.e.setLayoutParams(new FrameLayout.LayoutParams(i3, i4));
            } else {
                this.e.setLayoutParams(new RelativeLayout.LayoutParams(i3, i4));
            }
            this.e.setVisibility(0);
            this.d = a2;
        }
    }
    
    public void b(ViewBean viewBean) {
        this.d = null;
        c(viewBean);
        ((hr) this.f1401a).setChildScrollEnabled(false);
    }
    
    public void d() {
        a(true);
        this.c = new ArrayList<>();
        ((hr) this.f1401a).setChildScrollEnabled(true);
    }
    
    public Object[] a(int i, int i2) {
        Object[] objArr = null;
        int i3 = -1;
        for (int i4 = 0; i4 < this.c.size(); i4++) {
            Object[] objArr2 = this.c.get(i4);
            Rect rect = (Rect) objArr2[0];
            if (i >= rect.left && i < rect.right && i2 >= rect.top && i2 < rect.bottom && i3 < ((Integer) objArr2[3]).intValue()) {
                i3 = ((Integer) objArr2[3]).intValue();
                objArr = objArr2;
            }
        }
        return objArr;
    }
    
    public void c(ViewBean viewBean) {
        a(viewBean, (ia) this.f1401a);
    }
    
    
    private void a(ViewBean viewBean, ia layout) {
        int[] location = new int[2];
        layout.getLocationOnScreen(location);
        
        int gravity = layout.getLayoutGravity();
        int horizontalGravity = gravity & 7;
        int verticalGravity = gravity & 112;
        
        int left = location[0];
        int top = location[1];
        
        int right = (int) (left + layout.getWidth() * getScaleX());
        int bottom = (int) (top + layout.getHeight() * getScaleY());
        
        Rect layoutRect = new Rect(left, top, right, bottom);
        int index = b(layout);
        a(layoutRect, layout, -1, index);
        
        int startX = (int) (location[0] + layout.getPaddingLeft() * getScaleX());
        int startY = (int) (location[1] + layout.getPaddingTop() * getScaleY());
        
        int childCount = layout.getChildCount();
        int lastRight = startX;
        int lastBottom = startY;
        int order = 0;
        
        for (int i = 0; i < childCount; i++) {
            View child = layout.getChildAt(i);
            if (child == null) continue;
            if (child.getTag() == null) continue;
            

            if (viewBean != null && viewBean.id != null &&
            viewBean.id.equals(child.getTag())) {
                continue;
            }
            
            if (child.getVisibility() != View.VISIBLE) continue;
            
            int[] childLocation = new int[2];
            child.getLocationOnScreen(childLocation);
            
            if (layout.getOrientation() == LinearLayout.HORIZONTAL) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) child.getLayoutParams();
                int leftMargin = lp.leftMargin;
                int rightMargin = lp.rightMargin;
                
                if (horizontalGravity == Gravity.LEFT) {
                    if (i == 0) {
                        int leftX = (int) (childLocation[0] - leftMargin * getScaleX());
                        int topY = location[1];
                        int bottomY = (int) (topY + layout.getMeasuredHeight() * getScaleY());
                        Rect rect = new Rect(lastRight, topY, leftX, bottomY);
                        a(rect, layout, 0, b(layout) + 1);
                    }
                    int w = (int) ((leftMargin + child.getMeasuredWidth() + rightMargin) * getScaleX());
                    int newRight = lastRight + w;
                    int topY = location[1];
                    int bottomY = (int) (topY + layout.getMeasuredHeight() * getScaleY());
                    Rect rect = new Rect(lastRight, topY, newRight, bottomY);
                    a(rect, layout, order++, b(layout) + 1);
                    lastRight = newRight;
                } else if (horizontalGravity == Gravity.RIGHT) {
                    int leftX = (int) (childLocation[0] - leftMargin * getScaleX());
                    int topY = location[1];
                    int bottomY = (int) (topY + layout.getMeasuredHeight() * getScaleY());
                    Rect rect = new Rect(lastRight, topY, leftX, bottomY);
                    a(rect, layout, order++, b(layout) + 1);
                    lastRight = (int) (childLocation[0]
                    + (child.getMeasuredWidth() + rightMargin) * getScaleX());
                } else {
                    int w = (int) ((leftMargin + child.getMeasuredWidth() + rightMargin) * getScaleX());
                    int newRight = lastRight + w;
                    int topY = location[1];
                    int bottomY = (int) (topY + layout.getMeasuredHeight() * getScaleY());
                    Rect rect = new Rect(lastRight, topY, newRight, bottomY);
                    a(rect, layout, order++, b(layout) + 1);
                    lastRight = newRight;
                }
                
            } else {

                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) child.getLayoutParams();
                int topMargin = lp.topMargin;
                int bottomMargin = lp.bottomMargin;
                
                if (verticalGravity == Gravity.TOP) {
                    if (i == 0) {
                        int childTop = (int) (childLocation[1] - topMargin * getScaleY());
                        int leftX = location[0];
                        int rightX = (int) (leftX + layout.getMeasuredWidth() * getScaleX());
                        Rect rect = new Rect(leftX, lastBottom, rightX, childTop);
                        a(rect, layout, 0, b(layout) + 1);
                    }
                    int h = (int) ((topMargin + child.getMeasuredHeight() + bottomMargin) * getScaleY());
                    int newBottom = lastBottom + h;
                    int leftX = location[0];
                    int rightX = (int) (leftX + layout.getMeasuredWidth() * getScaleX());
                    Rect rect = new Rect(leftX, lastBottom, rightX, newBottom);
                    a(rect, layout, order++, b(layout) + 1);
                    lastBottom = newBottom;
                } else if (verticalGravity == Gravity.BOTTOM) {
                    int childTop = (int) (childLocation[1] - topMargin * getScaleY());
                    int leftX = location[0];
                    int rightX = (int) (leftX + layout.getMeasuredWidth() * getScaleX());
                    Rect rect = new Rect(leftX, lastBottom, rightX, childTop);
                    a(rect, layout, order++, b(layout) + 1);
                    lastBottom = (int) (childLocation[1]
                    + (child.getMeasuredHeight() + bottomMargin) * getScaleY());
                } else {
                    int h = (int) ((topMargin + child.getMeasuredHeight() + bottomMargin) * getScaleY());
                    int newBottom = lastBottom + h;
                    int leftX = location[0];
                    int rightX = (int) (leftX + layout.getMeasuredWidth() * getScaleX());
                    Rect rect = new Rect(leftX, lastBottom, rightX, newBottom);
                    a(rect, layout, order++, b(layout) + 1);
                    lastBottom = newBottom;
                }
            }
            

            if (child instanceof ia) {
                a(viewBean, (ia) child);
            } else if (child instanceof hy || child instanceof ii) {
                a(viewBean, (ViewGroup) child);
            }
        }
    }
    
    
    private void a(ViewBean viewBean, ViewGroup viewGroup) {
        int childCount = viewGroup.getChildCount();
        int i = 0;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = viewGroup.getChildAt(i2);
            if (!(childAt == null || childAt.getTag() == null || ((viewBean != null && viewBean.id != null && childAt.getTag().equals(viewBean.id)) || childAt.getVisibility() != 0))) {
                i++;
                if (childAt instanceof ia) {
                    a(viewBean, (ia) childAt);
                } else if (childAt instanceof hy) {
                    a(viewBean, (ViewGroup) childAt);
                } else if (childAt instanceof ii) {
                    a(viewBean, (ViewGroup) childAt);
                }
            }
        }
        if (i < 1) {
            int[] iArr = new int[2];
            viewGroup.getLocationOnScreen(iArr);
            int i3 = iArr[0];
            int i4 = iArr[1];
            a(new Rect(i3, i4, ((int) (((float) viewGroup.getWidth()) * getScaleX())) + i3, ((int) (((float) viewGroup.getHeight()) * getScaleY())) + i4), (View) viewGroup, -1, b((View) viewGroup));
        }
    }
    
    private int b(View view) {
        int i = 0;
        while (view != null && view != this.f1401a) {
            i++;
            view = (View) view.getParent();
        }
        return i * 2;
    }
    
    private void a(Rect rect, View view, int i, int i2) {
        this.c.add(new Object[]{rect, view, Integer.valueOf(i), Integer.valueOf(i2)});
    }
    
    public View d(ViewBean viewBean) {
        View view;
        switch (viewBean.type) {
            case 0:
            view = new ia(getContext());
            break;
            case 2:
            view = new hy(getContext());
            break;
            case 3:
            view = new ht(getContext());
            break;
            case 4:
            view = new ih(getContext());
            break;
            case 5:
            view = new hw(getContext());
            break;
            case 6:
            view = new hz(getContext());
            break;
            case 7:
            view = new ij(getContext());
            break;
            case 8:
            view = new id(getContext());
            break;
            case 9:
            view = new ib(getContext());
            break;
            case 10:
            view = new Cif(getContext());
            break;
            case 11:
            view = new hv(getContext());
            break;
            case 12:
            view = new ii(getContext());
            break;
            case 13:
            view = new ig(getContext());
            break;
            case 14:
            view = new ie(getContext());
            break;
            case 15:
            view = new hu(getContext());
            break;
            case 17:
            view = new hs(getContext());
            break;
            case 18:
            view = new ic(getContext());
            break;
            default:
            view = null;
            break;
        }
        int i = this.b + 1;
        this.b = i; 
        view.setId(i);
        view.setTag(viewBean.id);
        ((hq) view).setBean(viewBean);
        a(view, viewBean);
        return view;
    }
    
    public void a(View view) {
        ViewBean bean = ((hq) view).getBean();
        ViewGroup viewGroup = (ViewGroup) this.f1401a.findViewWithTag(bean.parent);
        viewGroup.addView(view, bean.index);
        if (viewGroup instanceof hr) {
            ((hr) viewGroup).a();
        }
    }
    
    public void e(ViewBean viewBean) {
        ViewGroup viewGroup = (ViewGroup) this.f1401a.findViewWithTag(viewBean.parent);
        viewGroup.removeView(this.f1401a.findViewWithTag(viewBean.id));
        if (viewGroup instanceof hr) {
            ((hr) viewGroup).a();
        }
    }
    
    public hq f(ViewBean viewBean) {
        View findViewWithTag = this.f1401a.findViewWithTag(viewBean.id);
        if (viewBean.id.charAt(0) == '_') {
            findViewWithTag = findViewWithTag(viewBean.id);
        }
        if (viewBean.preParent != null && viewBean.preParent.length() > 0 && !viewBean.parent.equals(viewBean.preParent)) {
            ViewGroup viewGroup = (ViewGroup) this.f1401a.findViewWithTag(viewBean.preParent);
            viewGroup.removeView(findViewWithTag);
            ((hr) viewGroup).a();
            a(findViewWithTag);
        } else if (viewBean.index != viewBean.preIndex) {
            ((ViewGroup) this.f1401a.findViewWithTag(viewBean.parent)).removeView(findViewWithTag);
            a(findViewWithTag);
        }
        viewBean.preId = "";
        viewBean.preIndex = -1;
        viewBean.preParent = "";
        viewBean.preParentType = -1;
        findViewWithTag.setVisibility(0);
        return (hq) findViewWithTag;
    }
    
    public hq g(ViewBean viewBean) {
        View view;
        if (viewBean.preId != null && viewBean.preId.length() > 0 && !viewBean.preId.equals(viewBean.id)) {
            this.f1401a.findViewWithTag(viewBean.preId).setTag(viewBean.id);
            viewBean.preId = "";
        }
        if (viewBean.id.charAt(0) == '_') {
            view = findViewWithTag(viewBean.id);
        } else {
            view = this.f1401a.findViewWithTag(viewBean.id);
        }
        a(view, viewBean);
        return (hq) view;
    }
    
    private void a(View view, ViewBean viewBean) {
        boolean z = true;
        if (viewBean.id.charAt(0) == '_') {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams.leftMargin = (int) kp.a(getContext(), (float) viewBean.layout.marginLeft);
            layoutParams.topMargin = (int) kp.a(getContext(), (float) viewBean.layout.marginTop);
            layoutParams.rightMargin = (int) kp.a(getContext(), (float) viewBean.layout.marginRight);
            layoutParams.bottomMargin = (int) kp.a(getContext(), (float) viewBean.layout.marginBottom);
            int i = viewBean.layout.layoutGravity;
            if ((i & 3) == 3) {
                layoutParams.addRule(9);
            }
            if ((i & 48) == 48) {
                layoutParams.addRule(10);
            }
            if ((i & 5) == 5) {
                layoutParams.addRule(11);
            }
            if ((i & 80) == 80) {
                layoutParams.addRule(12);
            }
            if ((i & 1) == 1) {
                layoutParams.addRule(14);
            }
            if ((i & 16) == 16) {
                layoutParams.addRule(15);
            }
            if ((i & 17) == 17) {
                layoutParams.addRule(13);
            }
            view.setLayoutParams(layoutParams);
            if (viewBean.getClassInfo().b("FloatingActionButton") && viewBean.image != null && viewBean.image.resName != null && viewBean.image.resName.length() > 0) {
                try {
                    Bitmap decodeFile = BitmapFactory.decodeFile(this.f.b(viewBean.image.resName));
                    int round = Math.round(getResources().getDisplayMetrics().density / 2.0f);
                    ((FloatingActionButton) view).setImageBitmap(Bitmap.createScaledBitmap(decodeFile, decodeFile.getWidth() * round, decodeFile.getHeight() * round, true));
                } catch (Exception unused) {
                }
            }
            view.setRotation((float) viewBean.image.rotate);
            view.setAlpha(viewBean.alpha);
            view.setTranslationX(kp.a(getContext(), viewBean.translationX));
            view.setTranslationY(kp.a(getContext(), viewBean.translationY));
            view.setScaleX(viewBean.scaleX);
            view.setScaleY(viewBean.scaleY);
            view.setVisibility(0);
            return;
        }
        b(view, viewBean);
        view.setRotation((float) viewBean.image.rotate);
        view.setAlpha(viewBean.alpha);
        view.setTranslationX(kp.a(getContext(), viewBean.translationX));
        view.setTranslationY(kp.a(getContext(), viewBean.translationY));
        view.setScaleX(viewBean.scaleX);
        view.setScaleY(viewBean.scaleY);
        if (viewBean.layout.backgroundResource != null) {
            try {
                if (this.f.a(viewBean.layout.backgroundResource) == ProjectResourceBean.PROJECT_RES_TYPE_RESOURCE) {
                    view.setBackgroundResource(getContext().getResources().getIdentifier(viewBean.layout.backgroundResource, "drawable", getContext().getPackageName()));
                } else {
                    String b2 = this.f.b(viewBean.layout.backgroundResource);
                    if (b2.endsWith(".9.png")) {
                        Bitmap a2 = ks.a(b2);
                        byte[] ninePatchChunk = a2.getNinePatchChunk();
                        if (NinePatch.isNinePatchChunk(ninePatchChunk)) {
                            view.setBackground(new NinePatchDrawable(getResources(), a2, ninePatchChunk, new Rect(), (String) null));
                        } else {
                            view.setBackground(new BitmapDrawable(getResources(), b2));
                        }
                    } else {
                        Bitmap decodeFile2 = BitmapFactory.decodeFile(b2);
                        int round2 = Math.round(getResources().getDisplayMetrics().density / 2.0f);
                        view.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(decodeFile2, decodeFile2.getWidth() * round2, decodeFile2.getHeight() * round2, true)));
                    }
                }
            } catch (Exception e2) {
                Log.e("DEBUG", e2.getMessage(), e2);
            }
        }
        hc classInfo = viewBean.getClassInfo();
        if (classInfo.b("LinearLayout")) {
            LinearLayout linearLayout = (LinearLayout) view;
            linearLayout.setOrientation(viewBean.layout.orientation);
            linearLayout.setWeightSum((float) viewBean.layout.weightSum);
            if (view instanceof ia) {
                ((ia) view).setLayoutGravity(viewBean.layout.gravity);
            }
        }
        if (classInfo.a("TextView")) {
            TextView textView = (TextView) view;
            a(textView, viewBean);
            if (!classInfo.b("Button") && !classInfo.b("Switch")) {
                textView.setGravity(viewBean.layout.gravity);
            } else if (viewBean.layout.gravity == 0) {
                textView.setGravity(17);
            } else {
                textView.setGravity(viewBean.layout.gravity);
            }
        }
        if (classInfo.b("EditText")) {
            a((EditText) view, viewBean);
        }
        if (classInfo.b("ImageView")) {
            if (this.f.a(viewBean.image.resName) == ProjectResourceBean.PROJECT_RES_TYPE_RESOURCE) {
                ((ImageView) view).setImageResource(getContext().getResources().getIdentifier(viewBean.image.resName, "drawable", getContext().getPackageName()));
            } else if (viewBean.image.resName.equals("default_image")) {
                ((ImageView) view).setImageResource(R.drawable.default_image);
            } else {
                try {
                    Bitmap decodeFile3 = BitmapFactory.decodeFile(this.f.b(viewBean.image.resName));
                    int round3 = Math.round(getResources().getDisplayMetrics().density / 2.0f);
                    ((ImageView) view).setImageBitmap(Bitmap.createScaledBitmap(decodeFile3, decodeFile3.getWidth() * round3, decodeFile3.getHeight() * round3, true));
                } catch (Exception unused2) {
                    ((ImageView) view).setImageResource(R.drawable.default_image);
                }
            }
            ((ImageView) view).setScaleType(ImageView.ScaleType.valueOf(viewBean.image.scaleType));
        }
        if (classInfo.a("CompoundButton")) {
            CompoundButton compoundButton = (CompoundButton) view;
            if (viewBean.checked == 0) {
                z = false;
            }
            compoundButton.setChecked(z);
        }
        if (classInfo.b("SeekBar")) {
            SeekBar seekBar = (SeekBar) view;
            seekBar.setProgress(viewBean.progress);
            seekBar.setMax(viewBean.max);
        }
        if (classInfo.b("ProgressBar")) {
            ((id) view).setProgressBarStyle(viewBean.progressStyle);
        }
        if (classInfo.b("CalendarView")) {
            ((CalendarView) view).setFirstDayOfWeek(viewBean.firstDayOfWeek);
        }
        if (classInfo.b("AdView")) {
            ((hs) view).setAdSize(viewBean.adSize);
        }
        view.setVisibility(0);
    }
    
    private void b(View view, ViewBean viewBean) {
        int i = viewBean.layout.width;
        int i2 = viewBean.layout.height;
        if (i > 0) {
            i = (int) kp.a(getContext(), (float) viewBean.layout.width);
        }
        if (i2 > 0) {
            i2 = (int) kp.a(getContext(), (float) viewBean.layout.height);
        }
        view.setBackgroundColor(viewBean.layout.backgroundColor);
        if (viewBean.id.equals("root")) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i, i2);
            layoutParams.leftMargin = (int) kp.a(getContext(), (float) viewBean.layout.marginLeft);
            layoutParams.topMargin = (int) kp.a(getContext(), (float) viewBean.layout.marginTop);
            layoutParams.rightMargin = (int) kp.a(getContext(), (float) viewBean.layout.marginRight);
            layoutParams.bottomMargin = (int) kp.a(getContext(), (float) viewBean.layout.marginBottom);
            view.setPadding(viewBean.layout.paddingLeft, viewBean.layout.paddingTop, viewBean.layout.paddingRight, viewBean.layout.paddingBottom);
            view.setLayoutParams(layoutParams);
        } else if (viewBean.parentType == 0) {
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(i, i2);
            layoutParams2.leftMargin = (int) kp.a(getContext(), (float) viewBean.layout.marginLeft);
            layoutParams2.topMargin = (int) kp.a(getContext(), (float) viewBean.layout.marginTop);
            layoutParams2.rightMargin = (int) kp.a(getContext(), (float) viewBean.layout.marginRight);
            layoutParams2.bottomMargin = (int) kp.a(getContext(), (float) viewBean.layout.marginBottom);
            view.setPadding(viewBean.layout.paddingLeft, viewBean.layout.paddingTop, viewBean.layout.paddingRight, viewBean.layout.paddingBottom);
            if (viewBean.layout.layoutGravity != 0) {
                layoutParams2.gravity = viewBean.layout.layoutGravity;
            }
            layoutParams2.weight = (float) viewBean.layout.weight;
            view.setLayoutParams(layoutParams2);
        } else {
            FrameLayout.LayoutParams layoutParams3 = new FrameLayout.LayoutParams(i, i2);
            layoutParams3.leftMargin = (int) kp.a(getContext(), (float) viewBean.layout.marginLeft);
            layoutParams3.topMargin = (int) kp.a(getContext(), (float) viewBean.layout.marginTop);
            layoutParams3.rightMargin = (int) kp.a(getContext(), (float) viewBean.layout.marginRight);
            layoutParams3.bottomMargin = (int) kp.a(getContext(), (float) viewBean.layout.marginBottom);
            view.setPadding(viewBean.layout.paddingLeft, viewBean.layout.paddingTop, viewBean.layout.paddingRight, viewBean.layout.paddingBottom);
            if (viewBean.layout.layoutGravity != 0) {
                layoutParams3.gravity = viewBean.layout.layoutGravity;
            }
            view.setLayoutParams(layoutParams3);
        }
    }
    
    private void a(TextView textView, ViewBean viewBean) {
        String str = viewBean.text.text;
        if (str != null && str.length() > 0 && str.indexOf("\\n") >= 0) {
            str = viewBean.text.text.replaceAll("\\\\n", "\n");
        }
        textView.setText(str);
        textView.setTypeface((Typeface) null, viewBean.text.textType);
        textView.setTextColor(viewBean.text.textColor);
        textView.setTextSize((float) viewBean.text.textSize);
        textView.setLines(viewBean.text.line);
        textView.setSingleLine(viewBean.text.singleLine != 0);
    }
    
    private void a(EditText editText, ViewBean viewBean) {
        editText.setHint(viewBean.text.hint);
        editText.setHintTextColor(viewBean.text.hintColor);
    }
    
    public void updateWidgetPosition(ViewBean bean, int x, int y) {
        View v = d(bean); // find view by bean
        if (v != null) {
            v.setX(x - (v.getWidth() / 2f));
            v.setY(y - (v.getHeight() / 2f));
            v.requestLayout();
        }
    }
    
}
