package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import com.nexusteam.internal.beans.BlockBean;
import com.nexusteam.internal.beans.EventBean;
import com.nexusteam.internal.beans.HistoryBlockBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class lu {
    private static lu b;

    /* renamed from: a  reason: collision with root package name */
    public String f362a;
    private Map<String, Integer> c = new HashMap();
    private Map<String, ArrayList<HistoryBlockBean>> d = new HashMap();

    public lu(String str) {
        this.f362a = str;
    }

    public static void a() {
        if (b != null) {
            b.f362a = "";
            b.d = null;
            b.c = null;
        }
        b = null;
    }

    public static lu a(String str) {
        if (b == null) {
            synchronized (lu.class) {
                if (b == null || !b.f362a.equals(str)) {
                    b = new lu(str);
                }
            }
        }
        return b;
    }

    private void h(String str) {
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

    private void i(String str) {
        if (!this.c.containsKey(str)) {
            b(str);
        }
        this.c.put(str, Integer.valueOf(this.c.get(str).intValue() + 1));
    }

    private void j(String str) {
        if (!this.c.containsKey(str)) {
            b(str);
        }
        int intValue = this.c.get(str).intValue();
        if (intValue != 0) {
            this.c.put(str, Integer.valueOf(intValue - 1));
        }
    }

    private void a(String str, HistoryBlockBean historyBlockBean) {
        if (!this.d.containsKey(str)) {
            b(str);
        }
        ArrayList arrayList = this.d.get(str);
        arrayList.add(historyBlockBean);
        if (arrayList.size() > 50) {
            arrayList.remove(0);
        } else {
            i(str);
        }
    }

    public void b(String str) {
        this.d.put(str, new ArrayList());
        this.c.put(str, 0);
    }

    public void c(String str) {
        if (this.d.containsKey(str)) {
            this.d.remove(str);
            this.c.remove(str);
        }
    }

    public void a(String str, BlockBean blockBean, int i, int i2, BlockBean blockBean2, BlockBean blockBean3) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(blockBean);
        a(str, (ArrayList<BlockBean>) arrayList, i, i2, blockBean2, blockBean3);
    }

    public void a(String str, ArrayList<BlockBean> arrayList, int i, int i2, BlockBean blockBean, BlockBean blockBean2) {
        HistoryBlockBean historyBlockBean = new HistoryBlockBean();
        historyBlockBean.actionAdd(arrayList, i, i2, blockBean, blockBean2);
        if (!this.d.containsKey(str)) {
            b(str);
        }
        h(str);
        a(str, historyBlockBean);
    }

    public void a(String str, ArrayList<BlockBean> arrayList, ArrayList<BlockBean> arrayList2, int i, int i2, int i3, int i4, BlockBean blockBean, BlockBean blockBean2, BlockBean blockBean3, BlockBean blockBean4) {
        String str2 = str;
        HistoryBlockBean historyBlockBean = new HistoryBlockBean();
        historyBlockBean.actionMove(arrayList, arrayList2, i, i2, i3, i4, blockBean, blockBean2, blockBean3, blockBean4);
        if (!this.d.containsKey(str2)) {
            b(str2);
        }
        h(str2);
        a(str2, historyBlockBean);
    }

    public void a(String str, BlockBean blockBean, BlockBean blockBean2) {
        if (!blockBean.isEqual(blockBean2)) {
            HistoryBlockBean historyBlockBean = new HistoryBlockBean();
            historyBlockBean.actionUpdate(blockBean, blockBean2);
            if (!this.d.containsKey(str)) {
                b(str);
            }
            h(str);
            a(str, historyBlockBean);
        }
    }

    public void b(String str, ArrayList<BlockBean> arrayList, int i, int i2, BlockBean blockBean, BlockBean blockBean2) {
        HistoryBlockBean historyBlockBean = new HistoryBlockBean();
        historyBlockBean.actionRemove(arrayList, i, i2, blockBean, blockBean2);
        if (!this.d.containsKey(str)) {
            b(str);
        }
        h(str);
        a(str, historyBlockBean);
    }

    public boolean d(String str) {
        if (this.c.containsKey(str) && this.c.get(str).intValue() > 0) {
            return true;
        }
        return false;
    }

    public HistoryBlockBean e(String str) {
        if (!d(str)) {
            return null;
        }
        j(str);
        return ((HistoryBlockBean) this.d.get(str).get(this.c.get(str).intValue() - 1)).clone();
    }

    public boolean f(String str) {
        if (this.c.containsKey(str) && this.c.get(str).intValue() < this.d.get(str).size()) {
            return true;
        }
        return false;
    }

    public HistoryBlockBean g(String str) {
        if (!f(str)) {
            return null;
        }
        i(str);
        return ((HistoryBlockBean) this.d.get(str).get((this.c.get(str).intValue() - 1) + 1)).clone();
    }

    public static String a(String str, String str2, String str3) {
        return str + EventBean.SEPARATOR + str2 + EventBean.SEPARATOR + str3;
    }
}
