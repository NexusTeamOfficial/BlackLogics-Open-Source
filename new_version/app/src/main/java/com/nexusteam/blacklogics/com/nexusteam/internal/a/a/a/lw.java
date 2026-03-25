package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.util.Pair;
import com.nexusteam.internal.beans.BlockBean;
import com.nexusteam.internal.beans.ComponentBean;
import com.nexusteam.internal.beans.EventBean;
import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.internal.beans.ProjectLibraryBean;
import com.nexusteam.internal.beans.ViewBean;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class lw {
    
    /* renamed from: a  reason: collision with root package name */
    public String f364a;
    protected HashMap<String, ArrayList<ViewBean>> b;
    protected HashMap<String, HashMap<String, ArrayList<BlockBean>>> c;
    protected HashMap<String, ArrayList<Pair<Integer, String>>> d;
    protected HashMap<String, ArrayList<Pair<Integer, String>>> e;
    protected HashMap<String, ArrayList<Pair<String, String>>> f;
    protected HashMap<String, ArrayList<ComponentBean>> g;
    protected HashMap<String, ArrayList<EventBean>> h;
    protected HashMap<String, ViewBean> i;
    private kk j = new kk();
    private String a;
    private Gson k = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    
    public lw(String str) {
        a();
        this.f364a = str;
    }
    
    /* access modifiers changed from: protected */
    public void a() {
        if (this.b != null) {
            this.b.clear();
        }
        if (this.c != null) {
            this.c.clear();
        }        
        if (this.d != null) {
            this.d.clear();
        }
        if (this.e != null) {
            this.e.clear();
        }
        if (this.g != null) {
            this.g.clear();
        }
        if (this.h != null) {
            this.h.clear();
        }
        this.b = new HashMap<>();
        this.c = new HashMap<>();
        this.d = new HashMap<>();
        this.e = new HashMap<>();
        this.f = new HashMap<>();
        this.g = new HashMap<>();
        this.h = new HashMap<>();
        this.i = new HashMap<>();
    }
    
    public void b() {
        this.f364a = "";
        a();
    }
    
    public void c() {
        String e2 = fe.e(this.f364a);
        m(e2 + File.separator + Promotion.ACTION_VIEW);
        n(e2 + File.separator + "logic");
    }
    
    public void d() {
        String d2 = fe.d(this.f364a);
        m(d2 + File.separator + Promotion.ACTION_VIEW);
        n(d2 + File.separator + "logic");
        e();
    }
    
    public void e() {
        String e2 = fe.e(this.f364a);
        this.j.e(e2 + File.separator + Promotion.ACTION_VIEW);
        this.j.e(e2 + File.separator + "logic");
    }
    
    private void m(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        a(stringBuffer);
        try {
            this.j.a(str, this.j.g(stringBuffer.toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    
    private void a(StringBuffer stringBuffer) {
        if (this.b != null && this.b.size() > 0) {
            for (Map.Entry next : this.b.entrySet()) {
                ArrayList arrayList = (ArrayList) next.getValue();
                if (arrayList != null && arrayList.size() > 0) {
                    ArrayList<ViewBean> a2 = a((ArrayList<ViewBean>) (ArrayList) next.getValue());
                    String str = "";
                    if (a2 != null && a2.size() > 0) {
                        for (int i2 = 0; i2 < a2.size(); i2++) {
                            ViewBean viewBean = a2.get(i2);
                            viewBean.clearClassInfo();
                            str = str + this.k.toJson(viewBean) + "\n";
                        }
                    }
                    stringBuffer.append("@");
                    stringBuffer.append((String) next.getKey());
                    stringBuffer.append("\n");
                    stringBuffer.append(str);
                    stringBuffer.append("\n");
                }
            }
        }
        if (this.i != null && this.i.size() > 0) {
            for (Map.Entry next2 : this.i.entrySet()) {
                ViewBean viewBean2 = (ViewBean) next2.getValue();
                if (viewBean2 != null) {
                    stringBuffer.append("@");
                    stringBuffer.append(((String) next2.getKey()) + "_fab");
                    stringBuffer.append("\n");
                    stringBuffer.append("" + this.k.toJson(viewBean2) + "\n");
                    stringBuffer.append("\n");
                }
            }
        }
    }
    
    private void n(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        b(stringBuffer);
        try {
            this.j.a(str, this.j.g(stringBuffer.toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    
    private void b(StringBuffer stringBuffer) {
        if (this.d != null && this.d.size() > 0) {
            for (Map.Entry next : this.d.entrySet()) {
                ArrayList arrayList = (ArrayList) next.getValue();
                if (arrayList != null && arrayList.size() > 0) {
                    String str = "";
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        Pair pair = (Pair) it.next();
                        str = str + pair.first + ":" + ((String) pair.second) + "\n";
                    }
                    stringBuffer.append("@");
                    stringBuffer.append(((String) next.getKey()) + EventBean.SEPARATOR + "var");
                    stringBuffer.append("\n");
                    stringBuffer.append(str);
                    stringBuffer.append("\n");
                }
            }
        }
        if (this.e != null && this.e.size() > 0) {
            for (Map.Entry next2 : this.e.entrySet()) {
                ArrayList arrayList2 = (ArrayList) next2.getValue();
                if (arrayList2 != null && arrayList2.size() > 0) {
                    String str2 = "";
                    Iterator it2 = arrayList2.iterator();
                    while (it2.hasNext()) {
                        Pair pair2 = (Pair) it2.next();
                        str2 = str2 + pair2.first + ":" + ((String) pair2.second) + "\n";
                    }
                    stringBuffer.append("@");
                    stringBuffer.append(((String) next2.getKey()) + EventBean.SEPARATOR + "list");
                    stringBuffer.append("\n");
                    stringBuffer.append(str2);
                    stringBuffer.append("\n");
                }
            }
        }
        if (this.f != null && this.f.size() > 0) {
            for (Map.Entry next3 : this.f.entrySet()) {
                ArrayList arrayList3 = (ArrayList) next3.getValue();
                if (arrayList3 != null && arrayList3.size() > 0) {
                    String str3 = "";
                    Iterator it3 = arrayList3.iterator();
                    while (it3.hasNext()) {
                        Pair pair3 = (Pair) it3.next();
                        str3 = str3 + ((String) pair3.first) + ":" + ((String) pair3.second) + "\n";
                    }
                    stringBuffer.append("@");
                    stringBuffer.append(((String) next3.getKey()) + EventBean.SEPARATOR + "func");
                    stringBuffer.append("\n");
                    stringBuffer.append(str3);
                    stringBuffer.append("\n");
                }
            }
        }
        if (this.g != null && this.g.size() > 0) {
            for (Map.Entry next4 : this.g.entrySet()) {
                ArrayList arrayList4 = (ArrayList) next4.getValue();
                if (arrayList4 != null && arrayList4.size() > 0) {
                    String str4 = "";
                    Iterator it4 = arrayList4.iterator();
                    while (it4.hasNext()) {
                        ComponentBean componentBean = (ComponentBean) it4.next();
                        componentBean.clearClassInfo();
                        str4 = str4 + this.k.toJson(componentBean) + "\n";
                    }
                    stringBuffer.append("@");
                    stringBuffer.append(((String) next4.getKey()) + EventBean.SEPARATOR + "components");
                    stringBuffer.append("\n");
                    stringBuffer.append(str4);
                    stringBuffer.append("\n");
                }
            }
        }
        if (this.h != null && this.h.size() > 0) {
            for (Map.Entry next5 : this.h.entrySet()) {
                ArrayList arrayList5 = (ArrayList) next5.getValue();
                if (arrayList5 != null && arrayList5.size() > 0) {
                    String str5 = "";
                    Iterator it5 = arrayList5.iterator();
                    while (it5.hasNext()) {
                        str5 = str5 + this.k.toJson((EventBean) it5.next()) + "\n";
                    }
                    stringBuffer.append("@");
                    stringBuffer.append(((String) next5.getKey()) + EventBean.SEPARATOR + "events");
                    stringBuffer.append("\n");
                    stringBuffer.append(str5);
                    stringBuffer.append("\n");
                }
            }
        }
        if (this.c != null && this.c.size() > 0) {
            for (Map.Entry<String, HashMap<String, ArrayList<BlockBean>>> next6 : this.c.entrySet()) {
                String str6 = next6.getKey();
                HashMap<String, ArrayList<BlockBean>> hashMap = next6.getValue();
                
                if (hashMap != null && hashMap.size() > 0) {
                    for (Map.Entry<String, ArrayList<BlockBean>> entry : hashMap.entrySet()) {
                        ArrayList<BlockBean> arrayList6 = entry.getValue();
                        
                        if (arrayList6 != null && arrayList6.size() > 0) {
                            String str7 = "";
                            for (BlockBean block : arrayList6) {
                                str7 += this.k.toJson(block) + "\n";
                            }
                            
                            stringBuffer.append("@");
                            stringBuffer.append(str6 + EventBean.SEPARATOR + entry.getKey());
                            stringBuffer.append("\n");
                            stringBuffer.append(str7);
                            stringBuffer.append("\n");
                        }
                    }
                }
            }
        }
        
    }
    
    public boolean f() {
        String e2 = fe.e(this.f364a);
        return this.j.a(e2 + File.separator + Promotion.ACTION_VIEW);
    }
    
    public boolean g() {
        String e2 = fe.e(this.f364a);
        return this.j.a(e2 + File.separator + "logic");
    }
    
    public void h() {
        String path = fe.e(this.a);
        path = path + File.separator + "view";
        BufferedReader reader = null;
        
        try {

            byte[] bytes = this.j.f(path);
            

            String data = this.j.b(bytes);
            

            reader = new BufferedReader(new StringReader(data));
            

            this.a(reader);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    
    public void i() {
        String path = fe.e(this.a);
        path = path + File.separator + "logic";
        BufferedReader reader = null;
        
        try {

            byte[] bytes = this.j.f(path);
            

            String data = this.j.b(bytes);
            

            reader = new BufferedReader(new StringReader(data));
            

            this.b(reader);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void j() {
        String path = fe.d(this.a);
        path = path + File.separator + "view";
        

        if (!this.j.a(path)) {
            return;
        }
        
        BufferedReader reader = null;
        
        try {
            byte[] bytes = this.j.f(path);
            String data = this.j.b(bytes);
            reader = new BufferedReader(new StringReader(data));
            this.a(reader);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void a(BufferedReader bufferedReader) {
        try {
            if (this.b != null) {
                this.b.clear();
            }
            if (this.i != null) {
                this.i.clear();
            }
            String str = "";
            StringBuffer stringBuffer = new StringBuffer();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                } else if (readLine.length() > 0) {
                    if (readLine.charAt(0) == '@') {
                        if (str.length() > 0) {
                            a(str, stringBuffer.toString());
                            stringBuffer = new StringBuffer();
                        }
                        str = readLine.substring(1);
                    } else {
                        stringBuffer.append(readLine);
                        stringBuffer.append("\n");
                    }
                }
            }
            if (str.length() > 0 && stringBuffer.length() > 0) {
                a(str, stringBuffer.toString());
            }
        } catch(Exception e) {
            
        }
    }
    
    public void a(String str, String str2) {
        try {
            lx lxVar = new lx(str);
            String a2 = lxVar.a();
            
            switch (lxVar.c()) {
                case DATA_TYPE_VIEW:
                this.b.put(a2, (ArrayList) lxVar.a(str2));
                return;
                
                case DATA_TYPE_FAB:
                ArrayList<ViewBean> list = (ArrayList<ViewBean>) lxVar.a(str2);
                if (list != null && !list.isEmpty()) {
                    this.i.put(a2, list.get(0)); // ✅ FIXED
                }
                return;
                
                default:
                return;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    
    
    public void k() {
        BufferedReader r2_BufferedReader;
        StringBuilder r1_StringBuilder = new StringBuilder();
        r1_StringBuilder.append(fe.d(a));
        r1_StringBuilder.append(File.separator);
        r1_StringBuilder.append("logic");
        String r0_String = r1_StringBuilder.toString();
        if (!j.a(r0_String)) {
        } else {

            try {
                r2_BufferedReader = new BufferedReader(new StringReader(j.b(j.f(r0_String))));
                b(r2_BufferedReader);
                if (r2_BufferedReader != null) {
                    r2_BufferedReader.close();
                }
            } catch (Exception e) {
            }
        }
    }
    
    public void b(BufferedReader bufferedReader) {
        try {
            if (this.d != null) {
                this.d.clear();
            }
            if (this.e != null) {
                this.e.clear();
            }
            if (this.f != null) {
                this.f.clear();
            }
            if (this.g != null) {
                this.g.clear();
            }
            if (this.h != null) {
                this.h.clear();
            }
            if (this.c != null) {
                this.c.clear();
            }
            String str = "";
            StringBuffer stringBuffer = new StringBuffer();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                } else if (readLine.length() > 0) {
                    if (readLine.charAt(0) == '@') {
                        if (str.length() > 0) {
                            b(str, stringBuffer.toString());
                            stringBuffer = new StringBuffer();
                        }
                        str = readLine.substring(1);
                    } else {
                        stringBuffer.append(readLine);
                        stringBuffer.append("\n");
                    }
                }
            }
            if (str.length() > 0 && stringBuffer.length() > 0) {
                b(str, stringBuffer.toString());
            }
        } catch(Exception e) {
            
        }
    }
    
    public void b(String str, String str2) {
        if (str2.length() > 0) {
            try {
                lx lxVar = new lx(str);
                String a2 = lxVar.a();
                switch (lxVar.c()) {
                    case DATA_TYPE_VARIABLE:
                    this.d.put(a2, (ArrayList) lxVar.a(str2));
                    return;
                    case DATA_TYPE_LIST:
                    this.e.put(a2, (ArrayList) lxVar.a(str2));
                    return;
                    case DATA_TYPE_COMPONENT:
                    this.g.put(a2, (ArrayList) lxVar.a(str2));
                    return;
                    case DATA_TYPE_EVENT:
                    this.h.put(a2, (ArrayList) lxVar.a(str2));
                    return;
                    case DATA_TYPE_FUNCTION:
                    this.f.put(a2, (ArrayList) lxVar.a(str2));
                    return;
                    case DATA_TYPE_LOGIC:
                    if (!this.c.containsKey(a2)) {
                        this.c.put(a2, new HashMap());
                    }
                    this.c.get(a2).put(lxVar.b(), (ArrayList) lxVar.a(str2));
                    return;
                    default:
                    return;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
    
    public static ArrayList<ViewBean> a(ArrayList<ViewBean> arrayList) {
        ArrayList<ViewBean> arrayList2 = new ArrayList<>();
        Iterator<ViewBean> it = arrayList.iterator();
        while (it.hasNext()) {
            ViewBean next = it.next();
            if (next.parent.equals("root")) {
                arrayList2.add(next);
            }
        }
        int size = arrayList2.size();
        for (int i2 = 0; i2 < size - 1; i2++) {
            int i3 = 0;
            while (i3 < (size - i2) - 1) {
                int i4 = i3 + 1;
                if (arrayList2.get(i3).index > arrayList2.get(i4).index) {
                    arrayList2.set(i3, arrayList2.get(i4));
                    arrayList2.set(i4, arrayList2.get(i3));
                }
                i3 = i4;
            }
        }
        Iterator<ViewBean> it2 = arrayList.iterator();
        while (it2.hasNext()) {
            ViewBean next2 = it2.next();
            if ((next2.type == 2 || next2.type == 0 || next2.type == 12) && next2.parent.equals("root")) {
                arrayList2.addAll(a(arrayList, next2));
            }
        }
        return arrayList2;
    }
    
    protected static ArrayList<ViewBean> a(ArrayList<ViewBean> arrayList, ViewBean viewBean) {
        ArrayList<ViewBean> arrayList2 = new ArrayList<>();
        Iterator<ViewBean> it = arrayList.iterator();
        while (it.hasNext()) {
            ViewBean next = it.next();
            if (next.parent.equals(viewBean.id)) {
                arrayList2.add(next);
            }
        }
        int size = arrayList2.size();
        for (int i2 = 0; i2 < size - 1; i2++) {
            int i3 = 0;
            while (i3 < (size - i2) - 1) {
                int i4 = i3 + 1;
                if (arrayList2.get(i3).index > arrayList2.get(i4).index) {
                    arrayList2.set(i3, arrayList2.get(i4));
                    arrayList2.set(i4, arrayList2.get(i3));
                }
                i3 = i4;
            }
        }
        Iterator<ViewBean> it2 = arrayList.iterator();
        while (it2.hasNext()) {
            ViewBean next2 = it2.next();
            if (next2.parent.equals(viewBean.id) && (next2.type == 0 || next2.type == 2 || next2.type == 12)) {
                arrayList2.addAll(a(arrayList, next2));
            }
        }
        return arrayList2;
    }
    
    @SuppressWarnings("unchecked")
    public ArrayList<ViewBean> a(String str) {
        Object obj = this.b.get(str);
        if (obj instanceof ArrayList<?>) {
            return (ArrayList<ViewBean>) (ArrayList<?>) obj;
        } else {
            return new ArrayList<>();
        }
    }
    
    
    public ArrayList<ViewBean> a(String str, ViewBean viewBean) {
        ArrayList<ViewBean> arrayList = new ArrayList<>();
        arrayList.add(viewBean);
        arrayList.addAll(a(this.b.get(str), viewBean));
        return arrayList;
    }
    
    public ArrayList<ViewBean> b(String str) {
        ArrayList<ViewBean> arrayList = new ArrayList<>();
        ArrayList arrayList2 = this.b.get(str);
        if (arrayList2 == null) {
            return arrayList;
        }
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            ViewBean viewBean = (ViewBean) it.next();
            if (viewBean.type == 9 && viewBean.customView != null && viewBean.customView.length() > 0 && !viewBean.customView.equals("none")) {
                arrayList.add(viewBean);
            }
        }
        return arrayList;
    }
    
    public ArrayList<Pair<Integer, String>> c(String str, String str2) {
        ArrayList<Pair<Integer, String>> arrayList = new ArrayList<>();
        ArrayList arrayList2 = this.b.get(str);
        if (arrayList2 == null) {
            return arrayList;
        }
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            ViewBean viewBean = (ViewBean) it.next();
            if (str2.equals("CheckBox")) {
                if (viewBean.getClassInfo().a("CompoundButton")) {
                    arrayList.add(new Pair(Integer.valueOf(viewBean.type), viewBean.id));
                }
            } else if (viewBean.getClassInfo().a(str2)) {
                arrayList.add(new Pair(Integer.valueOf(viewBean.type), viewBean.id));
            }
        }
        return arrayList;
    }
    
    public void c(String str) {
        if (!this.i.containsKey(str)) {
            ViewBean viewBean = new ViewBean("_fab", 16);
            viewBean.layout.marginLeft = 16;
            viewBean.layout.marginTop = 16;
            viewBean.layout.marginRight = 16;
            viewBean.layout.marginBottom = 16;
            viewBean.layout.layoutGravity = 85;
            this.i.put(str, viewBean);
        }
    }
    
    public ViewBean d(String str) {
        if (!this.i.containsKey(str)) {
            c(str);
        }
        return this.i.get(str);
    }
    
    public void a(ProjectFileBean projectFileBean) {
        if (this.i.containsKey(projectFileBean.getXmlName())) {
            this.i.remove(projectFileBean.getXmlName());
        }
        k(projectFileBean.getJavaName(), "_fab");
    }
    
    public void b(String str, ViewBean viewBean) {
        if (!this.b.containsKey(str)) {
            this.b.put(str, new ArrayList());
        }
        this.b.get(str).add(viewBean);
    }
    
    public void a(ProjectFileBean projectFileBean, ViewBean viewBean) {
        ArrayList arrayList;
        if (this.b.containsKey(projectFileBean.getXmlName())) {
            ArrayList arrayList2 = this.b.get(projectFileBean.getXmlName());
            int size = arrayList2.size();
            while (true) {
                size--;
                if (size >= 0) {
                    if (((ViewBean) arrayList2.get(size)).id.equals(viewBean.id)) {
                        arrayList2.remove(size);
                        break;
                    }
                } else {
                    break;
                }
            }
            if (projectFileBean.fileType == 0) {
                k(projectFileBean.getJavaName(), viewBean.id);
                a(projectFileBean.getJavaName(), viewBean.getClassInfo(), viewBean.id, true);
            } else if (projectFileBean.fileType == 1) {
                ArrayList arrayList3 = new ArrayList();
                for (Map.Entry next : this.b.entrySet()) {
                    Iterator it = ((ArrayList) next.getValue()).iterator();
                    while (it.hasNext()) {
                        ViewBean viewBean2 = (ViewBean) it.next();
                        if (viewBean2.type == 9 && viewBean2.customView.equals(projectFileBean.fileName)) {
                            String str = (String) next.getKey();
                            arrayList3.add(new Pair(ProjectFileBean.getJavaName(str.substring(0, str.lastIndexOf(".xml"))), viewBean2.id + EventBean.SEPARATOR + "onBindCustomView"));
                        }
                    }
                }
                Iterator it2 = arrayList3.iterator();
                while (it2.hasNext()) {
                    Pair pair = (Pair) it2.next();
                    if (this.c.containsKey(pair.first)) {
                        Map map = this.c.get(pair.first);
                        if (map.containsKey(pair.second) && (arrayList = (ArrayList) map.get(pair.second)) != null && arrayList.size() > 0) {
                            int size2 = arrayList.size();
                            while (true) {
                                size2--;
                                if (size2 < 0) {
                                    break;
                                }
                                BlockBean blockBean = (BlockBean) arrayList.get(size2);
                                hc classInfo = blockBean.getClassInfo();
                                if (classInfo == null || !classInfo.b(viewBean.getClassInfo().a()) || !blockBean.spec.equals(viewBean.id)) {
                                    ArrayList<hc> paramClassInfo = blockBean.getParamClassInfo();
                                    if (paramClassInfo != null && paramClassInfo.size() > 0) {
                                        for (int i2 = 0; i2 < paramClassInfo.size(); i2++) {
                                            hc hcVar = paramClassInfo.get(i2);
                                            if (hcVar != null && viewBean.getClassInfo().a(hcVar) && blockBean.parameters.get(i2).equals(viewBean.id)) {
                                                blockBean.parameters.set(i2, "");
                                            }
                                        }
                                    }
                                } else {
                                    arrayList.remove(size2);
                                }
                            }
                        }
                    }
                }                
            } else if (projectFileBean.fileType == 2) {
                j(projectFileBean.getDrawersJavaName(), viewBean.id);
            }
        }
    }
    
    public void a(String str, hc hcVar, String str2, boolean z) {
        Map map;
        ArrayList arrayList;
        if (this.c.containsKey(str) && (map = this.c.get(str)) != null) {
            for (Map.Entry<String, ArrayList<BlockBean>> entry :
            ((Map<String, ArrayList<BlockBean>>) map).entrySet()) {
                if ((!z || !((String) entry.getKey()).substring(((String) entry.getKey()).lastIndexOf(EventBean.SEPARATOR) + 1).equals("onBindCustomView")) && (arrayList = (ArrayList) entry.getValue()) != null && arrayList.size() > 0) {
                    int size = arrayList.size();
                    while (true) {
                        size--;
                        if (size < 0) {
                            break;
                        }
                        BlockBean blockBean = (BlockBean) arrayList.get(size);
                        hc classInfo = blockBean.getClassInfo();
                        if (classInfo == null || !classInfo.b(hcVar.a()) || !blockBean.spec.equals(str2)) {
                            ArrayList<hc> paramClassInfo = blockBean.getParamClassInfo();
                            if (paramClassInfo != null && paramClassInfo.size() > 0) {
                                for (int i2 = 0; i2 < paramClassInfo.size(); i2++) {
                                    hc hcVar2 = paramClassInfo.get(i2);
                                    if (hcVar2 != null && hcVar.a(hcVar2) && blockBean.parameters.get(i2).equals(str2)) {
                                        blockBean.parameters.set(i2, "");
                                    }
                                }
                            }
                        } else {
                            arrayList.remove(size);
                        }
                    }
                }
            }
        }        
    }
    
    public boolean a(String str, String str2, String str3) {
        Map map = this.c.get(str);
        if (map == null) {
            return false;
        }
        for (Map.Entry<String, ArrayList<BlockBean>> entry :
            ((Map<String, ArrayList<BlockBean>>) map).entrySet()) {
            if (!((String) entry.getKey()).equals(str3)) {
                Iterator it = ((ArrayList) entry.getValue()).iterator();
                while (it.hasNext()) {
                    BlockBean blockBean = (BlockBean) it.next();
                    hc classInfo = blockBean.getClassInfo();
                    if (classInfo != null && classInfo.b() && blockBean.spec.equals(str2)) {
                        return true;
                    }
                    ArrayList<hc> paramClassInfo = blockBean.getParamClassInfo();
                    if (paramClassInfo != null && paramClassInfo.size() > 0) {
                        for (int i2 = 0; i2 < paramClassInfo.size(); i2++) {
                            hc hcVar = paramClassInfo.get(i2);
                            if (hcVar != null && hcVar.b() && blockBean.parameters.get(i2).equals(str2)) {
                                return true;
                            }
                        }
                        continue;
                    }
                }
                continue;
            }
        }        
        return false;
    }
    
    public boolean b(String str, String str2, String str3) {
        Map map = this.c.get(str);
        if (map == null) {
            return false;
        }
        for (Map.Entry<String, ArrayList<BlockBean>> entry :
            ((Map<String, ArrayList<BlockBean>>) map).entrySet()) {
            if (!((String) entry.getKey()).equals(str3)) {
                Iterator it = ((ArrayList) entry.getValue()).iterator();
                while (it.hasNext()) {
                    BlockBean blockBean = (BlockBean) it.next();
                    hc classInfo = blockBean.getClassInfo();
                    if (classInfo != null && classInfo.c() && blockBean.spec.equals(str2)) {
                        return true;
                    }
                    ArrayList<hc> paramClassInfo = blockBean.getParamClassInfo();
                    if (paramClassInfo != null && paramClassInfo.size() > 0) {
                        for (int i2 = 0; i2 < paramClassInfo.size(); i2++) {
                            hc hcVar = paramClassInfo.get(i2);
                            if (hcVar != null && hcVar.c() && blockBean.parameters.get(i2).equals(str2)) {
                                return true;
                            }
                        }
                        continue;
                    }
                }
                continue;
            }
        }    
        return false;
    }
    
    public boolean d(String str, String str2) {
        String str3;
        Map map = this.c.get(str);
        if (map == null) {
            return false;
        }
        for (Map.Entry<String, ArrayList<BlockBean>> entry :
            ((Map<String, ArrayList<BlockBean>>) map).entrySet()) {
            if (!((String) entry.getKey()).equals(str2 + EventBean.SEPARATOR + "moreBlock")) {
                Iterator it = ((ArrayList) entry.getValue()).iterator();
                while (it.hasNext()) {
                    BlockBean blockBean = (BlockBean) it.next();
                    if (blockBean.opCode.equals("definedFunc")) {
                        int indexOf = blockBean.spec.indexOf(" ");
                        if (indexOf > 0) {
                            str3 = blockBean.spec.substring(0, indexOf);
                        } else {
                            str3 = blockBean.spec;
                        }
                        if (str3.equals(str2)) {
                            return true;
                        }
                    }
                }
                continue;
            }
        }
        return false;
    }
    
    public ViewBean e(String str, String str2) {
        ArrayList arrayList = this.b.get(str);
        if (arrayList == null) {
            return null;
        }
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            ViewBean viewBean = (ViewBean) arrayList.get(i2);
            if (str2.equals(viewBean.id)) {
                return viewBean;
            }
        }
        return null;
    }
    
    public ArrayList<String> b(ProjectFileBean projectFileBean) {
        String xmlName = projectFileBean.getXmlName();
        String javaName = projectFileBean.getJavaName();
        ArrayList<String> arrayList = new ArrayList<>();
        Iterator<Pair<Integer, String>> it = e(javaName).iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().second);
        }
        Iterator<Pair<Integer, String>> it2 = f(javaName).iterator();
        while (it2.hasNext()) {
            arrayList.add(it2.next().second);
        }
        Iterator<Pair<String, String>> it3 = h(javaName).iterator();
        while (it3.hasNext()) {
            arrayList.add(it3.next().first);
        }
        Iterator<ViewBean> it4 = a(xmlName).iterator();
        while (it4.hasNext()) {
            arrayList.add(it4.next().id);
        }
        Iterator<ComponentBean> it5 = k(javaName).iterator();
        while (it5.hasNext()) {
            arrayList.add(it5.next().componentId);
        }
        return arrayList;
    }
    
    public void a(String str, int i2, String str2) {
        Pair pair = new Pair(Integer.valueOf(i2), str2);
        if (!this.d.containsKey(str)) {
            this.d.put(str, new ArrayList());
        }
        this.d.get(str).add(pair);
    }
    
    public void f(String str, String str2) {
        ArrayList arrayList;
        if (this.d.containsKey(str) && (arrayList = this.d.get(str)) != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Pair pair = (Pair) it.next();
                if (((String) pair.second).equals(str2)) {
                    arrayList.remove(pair);
                    return;
                }
            }
        }
    }
    
    public ArrayList<Pair<Integer, String>> e(String str) {
        if (this.d.containsKey(str)) {
            return this.d.get(str);
        }
        return new ArrayList<>();
    }
    
    public ArrayList<String> a(String str, int i2) {
        ArrayList arrayList;
        ArrayList<String> arrayList2 = new ArrayList<>();
        if (!this.d.containsKey(str) || (arrayList = this.d.get(str)) == null) {
            return arrayList2;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            if (((Integer) pair.first).intValue() == i2) {
                arrayList2.add((String) pair.second);
            }
        }
        return arrayList2;
    }
    
    public void b(String str, int i2, String str2) {
        Pair pair = new Pair(Integer.valueOf(i2), str2);
        if (!this.e.containsKey(str)) {
            this.e.put(str, new ArrayList());
        }
        this.e.get(str).add(pair);
    }
    
    public void g(String str, String str2) {
        ArrayList arrayList;
        if (this.e.containsKey(str) && (arrayList = this.e.get(str)) != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Pair pair = (Pair) it.next();
                if (((String) pair.second).equals(str2)) {
                    arrayList.remove(pair);
                    return;
                }
            }
        }
    }
    
    public ArrayList<Pair<Integer, String>> f(String str) {
        if (this.e.containsKey(str)) {
            return this.e.get(str);
        }
        return new ArrayList<>();
    }
    
    public ArrayList<String> b(String str, int i2) {
        ArrayList arrayList;
        ArrayList<String> arrayList2 = new ArrayList<>();
        if (!this.e.containsKey(str) || (arrayList = this.e.get(str)) == null) {
            return arrayList2;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            if (((Integer) pair.first).intValue() == i2) {
                arrayList2.add((String) pair.second); // ✅ cast to String
            }
        }
        return arrayList2;
    }
    
    public ArrayList<String> g(String str) {
        ArrayList arrayList;
        ArrayList<String> arrayList2 = new ArrayList<>();
        if (!this.e.containsKey(str) || (arrayList = this.e.get(str)) == null) {
            return arrayList2;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add((String) ((Pair) it.next()).second); // ✅ cast to String
        }
        return arrayList2;
    }
    
    
    public void c(String str, String str2, String str3) {
        Pair pair = new Pair(str2, str3);
        if (!this.f.containsKey(str)) {
            this.f.put(str, new ArrayList());
        }
        this.f.get(str).add(pair);
    }
    
    public void h(String str, String str2) {
        ArrayList arrayList;
        if (this.f.containsKey(str) && (arrayList = this.f.get(str)) != null) {
            Iterator it = arrayList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Pair pair = (Pair) it.next();
                if (((String) pair.first).equals(str2)) {
                    arrayList.remove(pair);
                    break;
                }
            }
            if (this.c.get(str).containsKey(str2 + EventBean.SEPARATOR + "moreBlock")) {
                this.c.get(str).remove(str2 + EventBean.SEPARATOR + "moreBlock");
            }
        }
    }
    
    public ArrayList<Pair<String, String>> h(String str) {
        if (this.f.containsKey(str)) {
            return this.f.get(str);
        }
        return new ArrayList<>();
    }
    
    public String i(String str, String str2) {
        ArrayList arrayList;
        if (!this.f.containsKey(str) || (arrayList = this.f.get(str)) == null) {
            return "";
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            if (((String) pair.first).equals(str2)) {
                return (String) pair.second;
            }
        }
        return "";
    }
    
    public void a(String str, int i2, int i3, String str2, String str3) {
        if (!this.h.containsKey(str)) {
            this.h.put(str, new ArrayList());
        }
        this.h.get(str).add(new EventBean(i2, i3, str2, str3));
    }
    
    public void a(String str, EventBean eventBean) {
        if (!this.h.containsKey(str)) {
            this.h.put(str, new ArrayList());
        }
        this.h.get(str).add(eventBean);
    }
    
    public void i(String str) {
        if (this.h.containsKey(str)) {
            ArrayList arrayList = this.h.get(str);
            int size = arrayList.size();
            while (true) {
                size--;
                if (size < 0) {
                    return;
                }
                if (((EventBean) arrayList.get(size)).eventType == 4) {
                    arrayList.remove(size);
                }
            }
        }
    }
    
    public void j(String str, String str2) {
        if (this.h.containsKey(str)) {
            ArrayList arrayList = this.h.get(str);
            int size = arrayList.size();
            while (true) {
                size--;
                if (size >= 0) {
                    EventBean eventBean = (EventBean) arrayList.get(size);
                    if (eventBean.eventType == 4 && eventBean.targetId.equals(str2)) {
                        arrayList.remove(size);
                    }
                } else {
                    return;
                }
            }
        }
    }
    
    public void k(String str, String str2) {
        ArrayList arrayList;
        if (this.h.containsKey(str) && (arrayList = this.h.get(str)) != null) {
            int size = arrayList.size();
            while (true) {
                size--;
                if (size >= 0) {
                    EventBean eventBean = (EventBean) arrayList.get(size);
                    if (eventBean.targetId.equals(str2)) {
                        arrayList.remove(eventBean);
                        if (!(this.c == null || this.c.get(str) == null)) {
                            if (this.c.get(str).containsKey(eventBean.targetId + EventBean.SEPARATOR + eventBean.eventName)) {
                                this.c.get(str).remove(eventBean.targetId + EventBean.SEPARATOR + eventBean.eventName);
                            }
                        }
                    }
                } else {
                    return;
                }
            }
        }
    }
    
    public void d(String str, String str2, String str3) {
        ArrayList arrayList;
        if (this.h.containsKey(str) && (arrayList = this.h.get(str)) != null) {
            int size = arrayList.size();
            while (true) {
                size--;
                if (size >= 0) {
                    EventBean eventBean = (EventBean) arrayList.get(size);
                    if (eventBean.targetId.equals(str2) && str3.equals(eventBean.eventName)) {
                        arrayList.remove(eventBean);
                        if (!(this.c == null || this.c.get(str) == null)) {
                            if (this.c.get(str).containsKey(eventBean.targetId + EventBean.SEPARATOR + eventBean.eventName)) {
                                this.c.get(str).remove(eventBean.targetId + EventBean.SEPARATOR + eventBean.eventName);
                            }
                        }
                    }
                } else {
                    return;
                }
            }
        }
    }
    
    public ArrayList<EventBean> j(String str) {
        if (!this.h.containsKey(str)) {
            return new ArrayList<>();
        }
        return this.h.get(str);
    }
    
    public ArrayList<EventBean> a(String str, ComponentBean componentBean) {
        if (!this.h.containsKey(str)) {
            return new ArrayList<>();
        }
        ArrayList<EventBean> arrayList = new ArrayList<>();
        Iterator it = this.h.get(str).iterator();
        while (it.hasNext()) {
            EventBean eventBean = (EventBean) it.next();
            if (eventBean.targetType == componentBean.type && eventBean.targetId.equals(componentBean.componentId)) {
                arrayList.add(eventBean);
            }
        }
        return arrayList;
    }
    
    public void a(String str, int i2, String str2, String str3) {
        if (!this.g.containsKey(str)) {
            this.g.put(str, new ArrayList());
        }
        this.g.get(str).add(new ComponentBean(i2, str2, str3));
    }
    
    public void c(String str, int i2, String str2) {
        if (!this.g.containsKey(str)) {
            this.g.put(str, new ArrayList());
        }
        this.g.get(str).add(new ComponentBean(i2, str2));
    }
    
    public void b(String str, ComponentBean componentBean) {
        if (this.g.containsKey(str)) {
            ArrayList arrayList = this.g.get(str);
            if (arrayList.indexOf(componentBean) >= 0) {
                arrayList.remove(componentBean);
                k(str, componentBean.componentId);
                a(str, componentBean.getClassInfo(), componentBean.componentId, false);
            }
        }
    }
    
    public void c(String str, int i2) {
        ArrayList<ComponentBean> f2;
        if (this.g.containsKey(str) && (f2 = f(str, i2)) != null && f2.size() > 0) {
            Iterator<ComponentBean> it = f2.iterator();
            while (it.hasNext()) {
                b(str, it.next());
            }
        }
    }
    
    public ComponentBean d(String str, int i2) {
        if (!this.g.containsKey(str)) {
            return null;
        }
        return (ComponentBean) this.g.get(str).get(i2);
    }
    
    public ArrayList<String> e(String str, int i2) {
        ArrayList arrayList;
        ArrayList<String> arrayList2 = new ArrayList<>();
        if (!this.g.containsKey(str) || (arrayList = this.g.get(str)) == null) {
            return arrayList2;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ComponentBean componentBean = (ComponentBean) it.next();
            if (componentBean.type == i2) {
                arrayList2.add(componentBean.componentId);
            }
        }
        return arrayList2;
    }
    
    public ArrayList<ComponentBean> f(String str, int i2) {
        ArrayList arrayList;
        ArrayList<ComponentBean> arrayList2 = new ArrayList<>();
        if (!this.g.containsKey(str) || (arrayList = this.g.get(str)) == null) {
            return arrayList2;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ComponentBean componentBean = (ComponentBean) it.next();
            if (componentBean.type == i2) {
                arrayList2.add(componentBean);
            }
        }
        return arrayList2;
    }
    
    public ArrayList<ComponentBean> k(String str) {
        if (!this.g.containsKey(str)) {
            return new ArrayList<>();
        }
        return this.g.get(str);
    }
    
    public HashMap<String, ArrayList<BlockBean>> l(String str) {
        if (!this.c.containsKey(str)) {
            return new HashMap<>();
        }
        return this.c.get(str);
    }
    
    public void a(String str, String str2, ArrayList<BlockBean> arrayList) {
        if (!this.c.containsKey(str)) {
            this.c.put(str, new HashMap());
        }
        this.c.get(str).put(str2, arrayList);
    }
    
    public ArrayList<BlockBean> l(String str, String str2) {
        if (!this.c.containsKey(str)) {
            return new ArrayList<>();
        }
        Map map = this.c.get(str);
        if (map == null) {
            return new ArrayList<>();
        }
        if (!map.containsKey(str2)) {
            return new ArrayList<>();
        }
        return (ArrayList) map.get(str2);
    }
    
    public void m(String str, String str2) {
        Map map;
        if (this.c.containsKey(str) && (map = this.c.get(str)) != null && map.containsKey(str2)) {
            map.remove(str2);
        }
    }
    
    public boolean d(String str, int i2, String str2) {
        ArrayList arrayList = this.d.get(str);
        if (arrayList == null) {
            return false;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            if (((Integer) pair.first).intValue() == i2 && ((String) pair.second).equals(str2)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean e(String str, int i2, String str2) {
        ArrayList arrayList = this.e.get(str);
        if (arrayList == null) {
            return false;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            if (((Integer) pair.first).intValue() == i2 && ((String) pair.second).equals(str2)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean f(String str, int i2, String str2) {
        ArrayList arrayList = this.b.get(str);
        if (arrayList == null) {
            return false;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ViewBean viewBean = (ViewBean) it.next();
            if (viewBean.type == i2 && viewBean.id.equals(str2)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean n(String str, String str2) {
        ArrayList arrayList = this.b.get(str);
        if (arrayList == null) {
            return false;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            if (((ViewBean) it.next()).id.equals(str2)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean o(String str, String str2) {
        ArrayList arrayList = this.b.get(str);
        if (arrayList == null) {
            return false;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ViewBean viewBean = (ViewBean) it.next();
            if (viewBean.getClassInfo().a("TextView") && viewBean.id.equals(str2)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean p(String str, String str2) {
        ArrayList arrayList = this.b.get(str);
        if (arrayList == null) {
            return false;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ViewBean viewBean = (ViewBean) it.next();
            if (viewBean.getClassInfo().a("CompoundButton") && viewBean.id.equals(str2)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean g(String str, int i2, String str2) {
        ArrayList arrayList = this.g.get(str);
        if (arrayList == null) {
            return false;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ComponentBean componentBean = (ComponentBean) it.next();
            if (componentBean.type == i2 && componentBean.componentId.equals(str2)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean g(String str, int i2) {
        ArrayList arrayList = this.g.get(str);
        if (arrayList == null) {
            return false;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            if (((ComponentBean) it.next()).type == i2) {
                return true;
            }
        }
        return false;
    }
    
    public void a(ProjectLibraryBean projectLibraryBean) {
        if (!projectLibraryBean.useYn.equals(ProjectLibraryBean.LIB_USE_Y)) {
            for (Map.Entry<String, ArrayList<ComponentBean>> key : this.g.entrySet()) {
                String str = (String) key.getKey();
                c(str, 6);
                c(str, 12);
                c(str, 14);
            }
        }
    }
    
    public void a(ProjectLibraryBean projectLibraryBean, ly lyVar) {
        if (!projectLibraryBean.useYn.equals(ProjectLibraryBean.LIB_USE_Y)) {
            Iterator<ProjectFileBean> it = lyVar.a().iterator();
            while (it.hasNext()) {
                ProjectFileBean next = it.next();
                Iterator<ViewBean> it2 = a(next.getXmlName()).iterator();
                while (it2.hasNext()) {
                    ViewBean next2 = it2.next();
                    if (next2.type == 18) {
                        a(next, next2);
                    }
                }
            }
        }
    }
    
    public void b(ProjectLibraryBean projectLibraryBean, ly lyVar) {
        if (!projectLibraryBean.useYn.equals(ProjectLibraryBean.LIB_USE_Y)) {
            Iterator<ProjectFileBean> it = lyVar.a().iterator();
            while (it.hasNext()) {
                ProjectFileBean next = it.next();
                Iterator<ViewBean> it2 = a(next.getXmlName()).iterator();
                while (it2.hasNext()) {
                    ViewBean next2 = it2.next();
                    if (next2.type == 17) {
                        a(next, next2);
                    }
                }
            }
            Iterator<ProjectFileBean> it3 = lyVar.b().iterator();
            while (it3.hasNext()) {
                ProjectFileBean next3 = it3.next();
                Iterator<ViewBean> it4 = a(next3.getXmlName()).iterator();
                while (it4.hasNext()) {
                    ViewBean next4 = it4.next();
                    if (next4.type == 17) {
                        a(next3, next4);
                    }
                }
            }
            for (Map.Entry<String, ArrayList<ComponentBean>> key : this.g.entrySet()) {
                c((String) key.getKey(), 13);
            }
        }
    }
    
    public void a(mb mbVar) {
        ArrayList<String> m = mbVar.m();
        for (Map.Entry<String, ArrayList<ViewBean>> value : this.b.entrySet()) {
            Iterator it = ((ArrayList) value.getValue()).iterator();
            while (it.hasNext()) {
                ViewBean viewBean = (ViewBean) it.next();
                if (m.indexOf(viewBean.layout.backgroundResource) < 0) {
                    viewBean.layout.backgroundResource = null;
                }
                if (m.indexOf(viewBean.image.resName) < 0) {
                    viewBean.image.resName = "default_image";
                }
            }
        }
        for (Map.Entry<String, ViewBean> value2 : this.i.entrySet()) {
            ViewBean viewBean2 = (ViewBean) value2.getValue();
            if (m.indexOf(viewBean2.image.resName) < 0) {
                viewBean2.image.resName = "NONE";
            }
        }
        for (Map.Entry<String, HashMap<String, ArrayList<BlockBean>>> value3 : this.c.entrySet()) {
            for (Map.Entry<String, ArrayList<BlockBean>> value4 : ((HashMap<String, ArrayList<BlockBean>>) value3.getValue()).entrySet()) {
                Iterator it2 = ((ArrayList) value4.getValue()).iterator();
                while (it2.hasNext()) {
                    BlockBean blockBean = (BlockBean) it2.next();
                    if ("setImage".equals(blockBean.opCode)) {
                        if (m.indexOf(blockBean.parameters.get(1)) < 0) {
                            blockBean.parameters.set(1, "default_image");
                        }
                    } else if ("setBgResource".equals(blockBean.opCode) && m.indexOf(blockBean.parameters.get(1)) < 0) {
                        blockBean.parameters.set(1, "NONE");
                    }
                }
            }
        }    
    }
    
    public void b(mb mbVar) {
        ArrayList<String> n = mbVar.n();
        for (Map.Entry<String, HashMap<String, ArrayList<BlockBean>>> value : this.c.entrySet()) {
            for (Map.Entry<String, ArrayList<BlockBean>> value2 : ((HashMap<String, ArrayList<BlockBean>>) value.getValue()).entrySet()) {
                Iterator it = ((ArrayList) value2.getValue()).iterator();
                while (it.hasNext()) {
                    BlockBean blockBean = (BlockBean) it.next();
                    if (blockBean.opCode.equals("mediaplayerCreate") && n.indexOf(blockBean.parameters.get(1)) < 0) {
                        blockBean.parameters.set(1, "");
                    }
                    if (blockBean.opCode.equals("soundpoolLoad") && n.indexOf(blockBean.parameters.get(1)) < 0) {
                        blockBean.parameters.set(1, "");
                    }
                }
            }
        }        
    }
    
    public void c(mb mbVar) {
        ArrayList<String> o = mbVar.o();
        for (Map.Entry<String, HashMap<String, ArrayList<BlockBean>>> value : this.c.entrySet()) {
            for (Map.Entry<String, ArrayList<BlockBean>> value2 : ((HashMap<String, ArrayList<BlockBean>>) value.getValue()).entrySet()) {
                Iterator it = ((ArrayList) value2.getValue()).iterator();
                while (it.hasNext()) {
                    BlockBean blockBean = (BlockBean) it.next();
                    if ("setTypeface".equals(blockBean.opCode) && o.indexOf(blockBean.parameters.get(1)) < 0) {
                        blockBean.parameters.set(1, "default_font");
                    }
                }
            }
        }        
    }
    
    public void a(ly lyVar) {
        boolean z;
        Iterator<ProjectFileBean> it = lyVar.a().iterator();
        while (it.hasNext()) {
            ProjectFileBean next = it.next();
            if (!next.hasActivityOption(8)) {
                a(next);
            }
            if (!next.hasActivityOption(4)) {
                i(next.getJavaName());
            }
        }
        ArrayList arrayList = new ArrayList();
        for (Map.Entry next2 : this.b.entrySet()) {
            String str = (String) next2.getKey();
            if (!lyVar.c(str)) {
                arrayList.add(str);
            } else {
                Iterator it2 = ((ArrayList) next2.getValue()).iterator();
                while (it2.hasNext()) {
                    ViewBean viewBean = (ViewBean) it2.next();
                    if (viewBean.type == 9 && viewBean.customView != null && viewBean.customView.length() > 0 && !viewBean.customView.equals("none")) {
                        Iterator<ProjectFileBean> it3 = lyVar.b().iterator();
                        boolean z2 = false;
                        while (it3.hasNext()) {
                            if (it3.next().fileName.equals(viewBean.customView)) {
                                z2 = true;
                            }
                        }
                        if (!z2) {
                            viewBean.customView = "";
                        }
                    }
                }
            }
        }
        Iterator it4 = arrayList.iterator();
        while (it4.hasNext()) {
            this.b.remove((String) it4.next());
        }
        ArrayList arrayList2 = new ArrayList();
        for (Map.Entry<String, ArrayList<Pair<Integer, String>>> key : this.d.entrySet()) {
            String str2 = (String) key.getKey();
            if (!lyVar.d(str2)) {
                arrayList2.add(str2);
            }
        }
        Iterator it5 = arrayList2.iterator();
        while (it5.hasNext()) {
            this.d.remove((String) it5.next());
        }
        ArrayList arrayList3 = new ArrayList();
        for (Map.Entry<String, ArrayList<Pair<Integer, String>>> key2 : this.e.entrySet()) {
            String str3 = (String) key2.getKey();
            if (!lyVar.d(str3)) {
                arrayList3.add(str3);
            }
        }
        Iterator it6 = arrayList3.iterator();
        while (it6.hasNext()) {
            this.e.remove((String) it6.next());
        }
        ArrayList arrayList4 = new ArrayList();
        for (Map.Entry<String, ArrayList<Pair<String, String>>> key3 : this.f.entrySet()) {
            String str4 = (String) key3.getKey();
            if (!lyVar.d(str4)) {
                arrayList4.add(str4);
            }
        }
        Iterator it7 = arrayList4.iterator();
        while (it7.hasNext()) {
            this.f.remove((String) it7.next());
        }
        ArrayList arrayList5 = new ArrayList();
        for (Map.Entry<String, ArrayList<ComponentBean>> key4 : this.g.entrySet()) {
            String str5 = (String) key4.getKey();
            if (!lyVar.d(str5)) {
                arrayList5.add(str5);
            }
        }
        Iterator it8 = arrayList5.iterator();
        while (it8.hasNext()) {
            this.g.remove((String) it8.next());
        }
        ArrayList arrayList6 = new ArrayList();
        for (Map.Entry<String, ArrayList<EventBean>> key5 : this.h.entrySet()) {
            String str6 = (String) key5.getKey();
            if (!lyVar.d(str6)) {
                arrayList6.add(str6);
            }
        }
        Iterator it9 = arrayList6.iterator();
        while (it9.hasNext()) {
            this.h.remove((String) it9.next());
        }
        ArrayList arrayList7 = new ArrayList();
        for (Map.Entry next3 : this.c.entrySet()) {
            String str7 = (String) next3.getKey();
            if (!lyVar.d(str7)) {
                arrayList7.add(str7);
            } else {
                for (Map.Entry<String, ArrayList<BlockBean>> value : ((HashMap<String, ArrayList<BlockBean>>) next3.getValue()).entrySet()) {
                    Iterator it10 = ((ArrayList) value.getValue()).iterator();
                    while (it10.hasNext()) {
                        BlockBean blockBean = (BlockBean) it10.next();
                        if (blockBean.opCode.equals("intentSetScreen")) {
                            Iterator<ProjectFileBean> it11 = lyVar.a().iterator();
                            while (true) {
                                if (it11.hasNext()) {
                                    if (it11.next().getActivityName().equals(blockBean.parameters.get(1))) {
                                        z = true;
                                        break;
                                    }
                                } else {
                                    z = false;
                                    break;
                                }
                            }
                            if (!z) {
                                blockBean.parameters.set(1, "");
                            }
                        }
                    }
                }
            }            
        }
        Iterator it12 = arrayList7.iterator();
        while (it12.hasNext()) {
            this.c.remove((String) it12.next());
        }
    }
}
