package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import com.nexusteam.internal.beans.BlockBean;
import com.nexusteam.internal.beans.ComponentBean;
import com.nexusteam.internal.beans.EventBean;
import com.nexusteam.internal.beans.ViewBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

public class lx {
    private final String a = "dataParser";
    private String b;
    private String c;
    private DataType d;
    private Gson e;

    public lx(String str) {
        this.e = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        h(str);
    }

    private void h(String str) {
        String trim = str.trim();
        if (trim.contains(".xml")) {
            this.d = DataType.DATA_TYPE_VIEW;
        } else if (trim.contains(".fab")) {
            this.d = DataType.DATA_TYPE_FAB;
        } else if (trim.contains(".var")) {
            this.d = DataType.DATA_TYPE_VARIABLE;
        } else if (trim.contains(".list")) {
            this.d = DataType.DATA_TYPE_LIST;
        } else if (trim.contains(".comp")) {
            this.d = DataType.DATA_TYPE_COMPONENT;
        } else if (trim.contains(".event")) {
            this.d = DataType.DATA_TYPE_EVENT;
        } else if (trim.contains(".func")) {
            this.d = DataType.DATA_TYPE_FUNCTION;
        } else if (trim.contains(".logic")) {
            this.d = DataType.DATA_TYPE_LOGIC;
        }

        if (trim.contains("_")) {
            this.b = trim.substring(0, trim.indexOf('_'));
            int dotIndex = trim.indexOf('.');
            if (dotIndex > trim.indexOf('_')) {
                this.c = trim.substring(trim.indexOf('_') + 1, dotIndex);
            }
        }
    }

    public ArrayList<ViewBean> a(String str) {
        ArrayList<ViewBean> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new StringReader(str))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("{")) {
                    list.add(e.fromJson(line, ViewBean.class));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }
    
    public static ArrayList<ViewBean> a(Gson gson, String str) {
        ArrayList<ViewBean> arrayList = new ArrayList<>();
        BufferedReader bufferedReader = null;
        try {

            BufferedReader bufferedReader2 = new BufferedReader(new StringReader(str));
            while (true) {
                try {
                    String readLine = bufferedReader2.readLine();
                    if (readLine == null) {
                        break;
                    }
                    String trimmedLine = readLine.trim();

                    if (trimmedLine.length() > 0 && trimmedLine.charAt(0) == '{') {

                        arrayList.add((ViewBean) gson.fromJson(readLine, ViewBean.class));
                    }
                } catch (Exception e) {

                    bufferedReader = bufferedReader2;
                    try {

                    } catch (Throwable th) {

                        bufferedReader = bufferedReader;
                    }
                } catch (Throwable th2) {

                    bufferedReader = bufferedReader2;
                }
            }

            try {
                bufferedReader2.close();
            } catch (Exception e2) {

            }
        } catch (Exception e3) {


            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            }
        } catch (Throwable th3) {

            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e5) {
                    e5.printStackTrace();
                }
            }

        }
        return arrayList;
    }
    
    public static ArrayList<BlockBean> b(Gson gson, String str) {
        ArrayList<BlockBean> arrayList = new ArrayList<>();
        BufferedReader bufferedReader = null;
        try {
            BufferedReader bufferedReader2 = new BufferedReader(new StringReader(str));
            while (true) {
                try {
                    String readLine = bufferedReader2.readLine();
                    if (readLine == null) {
                        break;
                    }
                    String trimmedLine = readLine.trim();
                    if (trimmedLine.length() > 0 && trimmedLine.charAt(0) == '{') {
                        arrayList.add((BlockBean) gson.fromJson(readLine, BlockBean.class));
                    }
                } catch (Exception e) {

                    bufferedReader = bufferedReader2;
                    e.printStackTrace();
                } catch (Throwable th) {

                    bufferedReader = bufferedReader2;
                    try {
                        bufferedReader.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }

                }
            }
            try {
                bufferedReader2.close();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        } catch (Exception e4) {

            e4.printStackTrace();
        }
        return arrayList;
    }

    public ArrayList<BlockBean> b(String str) {
        ArrayList<BlockBean> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new StringReader(str))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("{")) {
                    list.add(e.fromJson(line, BlockBean.class));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public ArrayList<String> c(String str) {
        ArrayList<String> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new StringReader(str))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("{")) {
                    list.add(line);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public ArrayList<ComponentBean> f(String str) {
        ArrayList<ComponentBean> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new StringReader(str))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("{")) {
                    ComponentBean comp = e.fromJson(line, ComponentBean.class);
                    comp.initValue();
                    list.add(comp);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public ArrayList<EventBean> g(String str) {
        ArrayList<EventBean> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new StringReader(str))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("{")) {
                    EventBean ev = e.fromJson(line, EventBean.class);
                    ev.initValue();
                    list.add(ev);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public String a() { return this.b; }
    public String b() { return this.c; }
    public DataType c() { return this.d; }
    public Gson d() { return this.e; }
    public String e() { return this.a; }

    public enum DataType {
        DATA_TYPE_VIEW,
        DATA_TYPE_FAB,
        DATA_TYPE_VARIABLE,
        DATA_TYPE_LIST,
        DATA_TYPE_COMPONENT,
        DATA_TYPE_EVENT,
        DATA_TYPE_FUNCTION,
        DATA_TYPE_LOGIC
    }
}
