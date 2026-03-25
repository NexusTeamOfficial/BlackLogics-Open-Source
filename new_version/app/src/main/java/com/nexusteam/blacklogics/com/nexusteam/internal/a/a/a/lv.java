package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import com.nexusteam.internal.beans.HistoryViewBean;
import com.nexusteam.internal.beans.ViewBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class lv {
    private static lv b;
    
    /* renamed from: a  reason: collision with root package name */
    public String f363a;
    private Map<String, Integer> c = new HashMap();
    private Map<String, ArrayList<HistoryViewBean>> d = new HashMap();
    
    public lv(String str) {
        this.f363a = str;
    }
    
    public static lv a(String str) {
        if (b == null) {
            synchronized (lv.class) {
                if (b == null || !b.f363a.equals(str)) {
                    b = new lv(str);
                }
            }
        }
        return b;
    }
    
    public static void a() {
        if (b != null) {
            b.f363a = "";
            b.d = null;
            b.c = null;
        }
        b = null;
    }
    
    private void g(String str) {
        if (this.c.containsKey(str)) {
            ArrayList arrayList = this.d.get(str);
            int intValue = this.c.get(str).intValue();
            if (arrayList != null) {
                for (int size = arrayList.size(); size > intValue; size--) {
                    arrayList.remove(size - 1);
                }
            }
        }
    }
    
    private void h(String str) {
        if (!this.c.containsKey(str)) {
            b(str);
        }
        this.c.put(str, Integer.valueOf(this.c.get(str).intValue() + 1));
    }
    
    private void i(String str) {
        if (!this.c.containsKey(str)) {
            b(str);
        }
        int intValue = this.c.get(str).intValue();
        if (intValue != 0) {
            this.c.put(str, Integer.valueOf(intValue - 1));
        }
    }
    
    private void a(String str, HistoryViewBean historyViewBean) {
        if (!this.d.containsKey(str)) {
            b(str);
        }
        ArrayList arrayList = this.d.get(str);
        arrayList.add(historyViewBean);
        if (arrayList.size() > 50) {
            arrayList.remove(0);
        } else {
            h(str);
        }
    }
    
    public void b(String str) {
        this.d.put(str, new ArrayList());
        this.c.put(str, 0);
    }
    
    public void a(String str, ViewBean viewBean) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(viewBean);
        a(str, (ArrayList<ViewBean>) arrayList);
    }
    
    public void a(String str, ArrayList<ViewBean> arrayList) {
        HistoryViewBean historyViewBean = new HistoryViewBean();
        historyViewBean.actionAdd(arrayList);
        if (!this.d.containsKey(str)) {
            b(str);
        }
        g(str);
        a(str, historyViewBean);
    }
    
    public void b(String str, ViewBean viewBean) {
        HistoryViewBean historyViewBean = new HistoryViewBean();
        historyViewBean.actionMove(viewBean);
        if (!this.d.containsKey(str)) {
            b(str);
        }
        g(str);
        a(str, historyViewBean);
    }
    
    public void a(String str, ViewBean viewBean, ViewBean viewBean2) {
        if (!viewBean.isEqual(viewBean2)) {
            HistoryViewBean historyViewBean = new HistoryViewBean();
            historyViewBean.actionUpdate(viewBean, viewBean2);
            if (!this.d.containsKey(str)) {
                b(str);
            }
            g(str);
            a(str, historyViewBean);
        }
    }
    
    public void b(String str, ArrayList<ViewBean> arrayList) {
        HistoryViewBean historyViewBean = new HistoryViewBean();
        historyViewBean.actionRemove(arrayList);
        if (!this.d.containsKey(str)) {
            b(str);
        }
        g(str);
        a(str, historyViewBean);
    }
    
    public boolean c(String str) {
        if (this.c.containsKey(str) && this.c.get(str).intValue() > 0) {
            return true;
        }
        return false;
    }
    
    public HistoryViewBean d(String str) {
        if (!c(str)) {
            return null;
        }
        
        i(str);
        
        ArrayList<HistoryViewBean> list = this.d.get(str);
        if (list == null || list.isEmpty()) {
            return null;
        }
        
        int index = this.c.get(str).intValue() - 1;
        if (index < 0 || index >= list.size()) {
            return null;
        }
        
        return list.get(index).clone();
    }
    
    
    public boolean e(String str) {
        if (this.c.containsKey(str) && this.c.get(str).intValue() < this.d.get(str).size()) {
            return true;
        }
        return false;
    }
    
    public HistoryViewBean f(String str) {
        if (!e(str)) {
            return null;
        }
        
        h(str);
        
        ArrayList<HistoryViewBean> list = this.d.get(str);
        if (list == null || list.isEmpty()) {
            return null;
        }
        
        int index = this.c.get(str).intValue();
        if (index < 0 || index >= list.size()) {
            return null;
        }
        
        return list.get(index).clone();
    }
    
}
