package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import com.nexusteam.internal.beans.BlockBean;
import com.nexusteam.internal.beans.BlockCollectionBean;
import com.nexusteam.internal.beans.CollectionBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ei extends eh {
    public static ei f;
    private Gson g = null;

    private ei() {
        i();
    }

    private void i() {
        this.g = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    /* access modifiers changed from: protected */
    public void b() {
        this.a = fe.w() + File.separator + "block" + File.separator + "list";
        this.b = fe.w() + File.separator + "block" + File.separator + "data";
    }

    public static ei f() {
        if (f == null) {
            synchronized (ei.class) {
                if (f == null) {
                    f = new ei();
                }
            }
        }
        return f;
    }

    public ArrayList<BlockCollectionBean> g() {
        if (this.e == null) {
            a();
        }
        if (this.g == null) {
            i();
        }
        ArrayList<BlockCollectionBean> arrayList = new ArrayList<>();
        Iterator it = this.e.iterator();
        while (it.hasNext()) {
            CollectionBean collectionBean = (CollectionBean) it.next();
            arrayList.add(new BlockCollectionBean(collectionBean.name, lx.b(this.g, collectionBean.data)));
        }
        return arrayList;
    }

    public BlockCollectionBean a(String str) {
        Iterator it = this.e.iterator();
        while (it.hasNext()) {
            CollectionBean collectionBean = (CollectionBean) it.next();
            if (collectionBean.name.equals(str)) {
                return new BlockCollectionBean(collectionBean.name, lx.b(this.g, collectionBean.data));
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


    public void a(String str, ArrayList<BlockBean> arrayList, boolean z) throws jg {
        if (this.e == null) {
            a();
        }
        if (this.g == null) {
            i();
        }
        Iterator it = this.e.iterator();
        while (it.hasNext()) {
            if (((CollectionBean) it.next()).name.equals(str)) {
                throw new jg("duplicate_name");
            }
        }

        String str2 = "";
        for (BlockBean block : arrayList) {
            str2 += this.g.toJson(block) + "\n";
        }
        this.e.add(new CollectionBean(str, str2));
        if (z) {
            d();
        }
    }

    /* access modifiers changed from: protected */
    public void c() {
        this.e = new ArrayList<>();
        BufferedReader reader = null;
        try {
            if (this.c.a(this.a)) {
                String content = this.c.h(this.a);
                reader = new BufferedReader(new StringReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.length() == 0) continue;
                    CollectionBean collection = this.d.fromJson(line, CollectionBean.class);
                    this.e.add(collection);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception ignored) {}
            }
        }
    }

    public void a(String str, boolean z) {
        for (int i = this.e.size() - 1; i >= 0; i--) {
            if (this.e.get(i).name.equals(str)) {
                this.e.remove(i);
                break;
            }
        }
        if (z) {
            d();
        }
    }

    public void a(String str, String str2, boolean z) {
        for (CollectionBean collection : this.e) {
            if (collection.name.equals(str)) {
                collection.name = str2;
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
