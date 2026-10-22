package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import com.nexusteam.internal.beans.CollectionBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public abstract class eh {

    protected String a;
    protected String b;
    protected kk c;
    protected Gson d;
    protected ArrayList<CollectionBean> e;

    public eh() {
        a();
    }

    protected void a() {
        b();
        this.c = new kk();
        this.d = new GsonBuilder().create();
        c();
    }

    protected abstract void b();

    protected void c() {
        e = new ArrayList<>();
        BufferedReader reader = null;
        try {
            String jsonData = c.h(a);
            reader = new BufferedReader(new StringReader(jsonData));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() == 0) continue;


                CollectionBean bean = d.fromJson(line, CollectionBean.class);


                String fullPath = b + java.io.File.separator + bean.data;
                if (c.a(fullPath)) {
                    e.add(bean);
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    public void d() {
        if (e == null) return;

        StringBuilder builder = new StringBuilder(1024);
        for (CollectionBean bean : e) {
            if (bean == null) continue;
            try {
                builder.append(d.toJson(bean)).append("\n");
            } catch (Exception ignore) {}
        }

        try {
            if (a != null) {
                java.io.File f = new java.io.File(a);
                java.io.File parent = f.getParentFile();
                if (parent != null && !parent.exists()) {
                    parent.mkdirs();
                }
            }
            c.d(a);
            c.b(a, builder.toString());
        } catch (Exception ex) {
            android.util.Log.e("eh", "collection write failed: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void e() {
        if (e != null) {
            e.clear();
            e = null;
        }
    }
}
