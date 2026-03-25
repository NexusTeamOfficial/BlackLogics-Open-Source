package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import com.nexusteam.internal.beans.BlockBean;
import com.nexusteam.internal.beans.CollectionBean;
import com.nexusteam.internal.beans.MoreBlockCollectionBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class el extends eh {
    public static el f;
    private Gson g = null;
    
    private el() {
        i();
    }
    
    private void i() {
        this.g = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }
    
    /* access modifiers changed from: protected */
    public void b() {
        this.a = fe.w() + File.separator + "more_block" + File.separator + "list";
    }
    
    public static el f() {
        if (f == null) {
            synchronized (el.class) {
                if (f == null) {
                    f = new el();
                }
            }
        }
        return f;
    }
    
    public ArrayList<MoreBlockCollectionBean> g() {
        if (this.e == null) {
            a();
        }
        if (this.g == null) {
            i();
        }
        ArrayList<MoreBlockCollectionBean> arrayList = new ArrayList<>();
        Iterator it = this.e.iterator();
        while (it.hasNext()) {
            CollectionBean collectionBean = (CollectionBean) it.next();
            arrayList.add(new MoreBlockCollectionBean(collectionBean.name, collectionBean.reserved1, lx.b(this.g, collectionBean.data)));
        }
        return arrayList;
    }
    
    public MoreBlockCollectionBean a(String str) {
        Iterator it = this.e.iterator();
        while (it.hasNext()) {
            CollectionBean collectionBean = (CollectionBean) it.next();
            if (collectionBean.name.equals(str)) {
                return new MoreBlockCollectionBean(collectionBean.name, collectionBean.reserved1, lx.b(this.g, collectionBean.data));
            }
        }
        return null;
    }
    
    public ArrayList<String> h() {
        if (this.e == null) {
            a();
        }
        ArrayList<String> arrayList = new ArrayList<>();
        Iterator it = this.e.iterator();
        while (it.hasNext()) {
            arrayList.add(((CollectionBean) it.next()).name);
        }
        return arrayList;
    }
    
    public void a(String str, String str2, ArrayList<BlockBean> arrayList, boolean z) {
        if (this.e == null) {
            a();
        }
        if (this.g == null) {
            i();
        }
        Iterator it = this.e.iterator();
        while (it.hasNext()) {
            if (((CollectionBean) it.next()).name.equals(str)) {
                try {
                    throw new jg("duplicate_name");
                } catch(Exception a) {
                    
                }
            }
        }
        String str3 = "";
        Iterator<BlockBean> it2 = arrayList.iterator();
        while (it2.hasNext()) {
            str3 = str3 + this.g.toJson(it2.next()) + "\n";
        }
        this.e.add(new CollectionBean(str, str3, str2));
        if (z) {
            d();
        }
    }
    
    @Override
    protected void c() {

        this.e = new ArrayList<>();
        
        BufferedReader reader = null;
        try {

            if (this.c.a(this.a)) {

                String content = this.c.h(this.a);
                
                reader = new BufferedReader(new StringReader(content));
                String line;
                

                while ((line = reader.readLine()) != null) {
                    if (line.length() == 0) continue; // skip empty lines
                    

                    CollectionBean bean = (CollectionBean) this.d.fromJson(line, CollectionBean.class);
                    

                    this.e.add(bean);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception ignored) {
                }
            }
        }
    }
    
    
    public void a(String str, boolean z) {
        int size = this.e.size();
        while (true) {
            size--;
            if (size >= 0) {
                if (((CollectionBean) this.e.get(size)).name.equals(str)) {
                    this.e.remove(size);
                    break;
                }
            } else {
                break;
            }
        }
        if (z) {
            d();
        }
    }
    
    public void a(String str, String str2, boolean z) {
        Iterator it = this.e.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            CollectionBean collectionBean = (CollectionBean) it.next();
            if (collectionBean.name.equals(str)) {
                collectionBean.name = str2;
                break;
            }
        }
        if (z) {
            d();
        }
    }
    
    public void e() {
        super.e();
        f = null;
    }
}
