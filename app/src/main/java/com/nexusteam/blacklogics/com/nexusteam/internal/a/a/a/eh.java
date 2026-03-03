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

    protected String a; // file path or key name
    protected String b; // base directory path
    protected kk c;     // file helper (custom class)
    protected Gson d;   // Gson instance
    protected ArrayList<CollectionBean> e; // collection list

    public eh() {
        a(); // initialize
    }

    /**
     * Initializes file manager and Gson
     */
    protected void a() {
        b(); // abstract: subclass will initialize 'a' and 'b'
        this.c = new kk();
        this.d = new GsonBuilder().create();
        c(); // load data
    }

    /**
     * Must be implemented by subclasses.
     * Used to define what file or directory to load.
     */
    protected abstract void b();

    /**
     * Reads data from file into 'e' (ArrayList<CollectionBean>)
     */
    protected void c() {
        e = new ArrayList<>();
        BufferedReader reader = null;
        try {
            String jsonData = c.h(a); // read JSON string from file (using kk)
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

    /**
     * Writes all CollectionBean objects back to file
     */
    public void d() {
        if (e == null) return;

        StringBuilder builder = new StringBuilder(1024);
        for (CollectionBean bean : e) {
            builder.append(d.toJson(bean)).append("\n");
        }

        try {
            c.d(a); // clear file
            c.b(a, builder.toString()); // write new data
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Clears memory and list
     */
    public void e() {
        if (e != null) {
            e.clear();
            e = null;
        }
    }
}
