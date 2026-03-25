package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.internal.beans.ProjectLibraryBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ly {
    
    /* renamed from: a  reason: collision with root package name */
    public String f369a;
    private ArrayList<String> b = new ArrayList<>();
    private ArrayList<String> c = new ArrayList<>();
    private ArrayList<ProjectFileBean> d;
    private ArrayList<ProjectFileBean> e;
    private kk f = new kk();
    private Gson g = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    
    public ly(String str) {
        this.f369a = str;
        m();
        l();
    }
    
    public void a(ArrayList<ProjectFileBean> arrayList) {
        this.d = arrayList;
    }
    
    public ArrayList<ProjectFileBean> a() {
        if (this.d == null) {
            this.d = new ArrayList<>();
        }
        return this.d;
    }
    
    public void b(ArrayList<ProjectFileBean> arrayList) {
        this.e = arrayList;
    }
    
    public ArrayList<ProjectFileBean> b() {
        if (this.e == null) {
            this.e = new ArrayList<>();
        }
        return this.e;
    }
    
    private void m() {
        this.d = new ArrayList<>();
        this.e = new ArrayList<>();
        a(0, "main");
    }
    
    public void c() {
        this.f369a = "";
        this.b = new ArrayList<>();
        this.c = new ArrayList<>();
        m();
    }
    
    public void d() {

        m();
        

        String basePath = fe.d(this.f369a);
        

        String path = basePath + File.separator + "file";
        

        if (!this.f.a(path)) {
            return;
        }
        
        BufferedReader reader = null;
        
        try {

            byte[] data = this.f.f(path);
            

            String decoded = this.f.b(data);
            

            reader = new BufferedReader(new StringReader(decoded));
            

            a(reader);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }
    
    
    public void a(BufferedReader bufferedReader) {
        try {
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
            if (str.length() > 0) {
                a(str, stringBuffer.toString());
            }
            l();
        } catch(Exception e) {}
    }
    
    public void a(String str, String str2) {
        if (str.equals("activity")) {
            if (str2.length() > 0) {
                while (true) {
                    int indexOf = str2.indexOf("\n");
                    if (indexOf >= 0 && str2.charAt(0) == '{') {
                        ProjectFileBean projectFileBean = (ProjectFileBean) this.g.fromJson(str2.substring(0, indexOf), ProjectFileBean.class);
                        projectFileBean.setOptionsByTheme();
                        if (projectFileBean.fileName.equals("main")) {
                            ProjectFileBean projectFileBean2 = null;
                            Iterator<ProjectFileBean> it = this.d.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                ProjectFileBean next = it.next();
                                if (next.fileName.equals("main")) {
                                    projectFileBean2 = next;
                                    break;
                                }
                            }
                            if (projectFileBean2 != null) {
                                projectFileBean2.copy(projectFileBean);
                            } else {
                                this.d.add(0, projectFileBean);
                            }
                        } else {
                            this.d.add(projectFileBean);
                        }
                        if (indexOf < str2.length() - 1) {
                            str2 = str2.substring(indexOf + 1);
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                }
            }
        } else if (str.equals("customview") && str2.length() > 0) {
            this.e = new ArrayList<>();
            while (true) {
                int indexOf2 = str2.indexOf("\n");
                if (indexOf2 >= 0 && str2.charAt(0) == '{') {
                    ProjectFileBean projectFileBean3 = (ProjectFileBean) this.g.fromJson(str2.substring(0, indexOf2), ProjectFileBean.class);
                    projectFileBean3.setOptionsByTheme();
                    this.e.add(projectFileBean3);
                    if (indexOf2 < str2.length() - 1) {
                        str2 = str2.substring(indexOf2 + 1);
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
        }
    }
    
    public void e() {
        String d2 = fe.d(this.f369a);
        e(d2 + File.separator + "file");
        f();
    }
    
    private void e(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        a(stringBuffer);
        try {
            this.f.a(str, this.f.g(stringBuffer.toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    
    private void a(StringBuffer stringBuffer) {
        stringBuffer.append("@");
        stringBuffer.append("activity");
        stringBuffer.append("\n");
        if (this.d != null) {
            Iterator<ProjectFileBean> it = this.d.iterator();
            while (it.hasNext()) {
                stringBuffer.append(this.g.toJson(it.next(), ProjectFileBean.class));
                stringBuffer.append("\n");
            }
        }
        stringBuffer.append("@");
        stringBuffer.append("customview");
        stringBuffer.append("\n");
        if (this.e != null) {
            Iterator<ProjectFileBean> it2 = this.e.iterator();
            while (it2.hasNext()) {
                stringBuffer.append(this.g.toJson(it2.next(), ProjectFileBean.class));
                stringBuffer.append("\n");
            }
        }
    }
    
    public void f() {
        String e2 = fe.e(this.f369a);
        this.f.e(e2 + File.separator + "file");
    }
    
    public void g() {
        String e2 = fe.e(this.f369a);
        e(e2 + File.separator + "file");
    }
    
    public boolean h() {
        String e2 = fe.e(this.f369a);
        return this.f.a(e2 + File.separator + "file");
    }
    
    public void i() {
        BufferedReader r2_BufferedReader;
        m();
        StringBuilder r1_StringBuilder = new StringBuilder();
        r1_StringBuilder.append(fe.e(this.f369a));
        r1_StringBuilder.append(File.separator);
        r1_StringBuilder.append("file");
        String r0_String = r1_StringBuilder.toString();

        try {
            r2_BufferedReader = new BufferedReader(new StringReader(f.b(f.f(r0_String))));
            a(r2_BufferedReader);
            if (r2_BufferedReader != null) {
                r2_BufferedReader.close();
            }
        } catch (Exception e) {

        }
        l();
    }
    
    public void a(ProjectFileBean projectFileBean) {
        if (projectFileBean.fileType == 0) {
            this.d.add(projectFileBean);
        } else {
            this.e.add(projectFileBean);
        }
    }
    
    public void a(int i, String str) {
        ProjectFileBean projectFileBean = new ProjectFileBean(i, str);
        if (i == 0) {
            this.d.add(projectFileBean);
        } else {
            this.e.add(projectFileBean);
        }
    }
    
    public void b(int i, String str) {
        if (i == 0) {
            Iterator<ProjectFileBean> it = this.d.iterator();
            while (it.hasNext()) {
                ProjectFileBean next = it.next();
                if (next.fileType == i && next.fileName.equals(str)) {
                    this.d.remove(next);
                    return;
                }
            }
            return;
        }
        Iterator<ProjectFileBean> it2 = this.e.iterator();
        while (it2.hasNext()) {
            ProjectFileBean next2 = it2.next();
            if (next2.fileType == i && next2.fileName.equals(str)) {
                this.e.remove(next2);
                return;
            }
        }
    }
    
    public ProjectFileBean a(String str) {
        Iterator<ProjectFileBean> it = this.d.iterator();
        while (it.hasNext()) {
            ProjectFileBean next = it.next();
            if (next.getXmlName().equals(str)) {
                return next;
            }
        }
        if (this.e == null) {
            return null;
        }
        Iterator<ProjectFileBean> it2 = this.e.iterator();
        while (it2.hasNext()) {
            ProjectFileBean next2 = it2.next();
            if (next2.getXmlName().equals(str)) {
                return next2;
            }
        }
        return null;
    }
    
    public ProjectFileBean b(String str) {
        Iterator<ProjectFileBean> it = this.d.iterator();
        while (it.hasNext()) {
            ProjectFileBean next = it.next();
            if (next.getJavaName().equals(str)) {
                return next;
            }
        }
        return null;
    }
    
    public ArrayList<String> j() {
        return this.b;
    }
    
    public ArrayList<String> k() {
        return this.c;
    }
    
    public boolean c(String str) {
        Iterator<String> it = this.b.iterator();
        while (it.hasNext()) {
            if (str.equals(it.next())) {
                return true;
            }
        }
        return false;
    }
    
    public boolean d(String str) {
        Iterator<String> it = this.c.iterator();
        while (it.hasNext()) {
            if (str.equals(it.next())) {
                return true;
            }
        }
        return false;
    }
    
    public void l() {
        this.b.clear();
        this.c.clear();
        Iterator<ProjectFileBean> it = this.d.iterator();
        while (it.hasNext()) {
            ProjectFileBean next = it.next();
            if (next.fileType == 0) {
                if (next.fileName.equals("main")) {
                    this.b.add(0, next.getXmlName());
                    this.c.add(0, next.getJavaName());
                } else {
                    this.b.add(next.getXmlName());
                    this.c.add(next.getJavaName());
                }
            }
        }
        if (this.e != null) {
            Iterator<ProjectFileBean> it2 = this.e.iterator();
            while (it2.hasNext()) {
                ProjectFileBean next2 = it2.next();
                if (next2.fileType == 1 || next2.fileType == 2) {
                    this.b.add(next2.getXmlName());
                }
            }
        }
    }
    
    public void a(lz lzVar) {
        ProjectLibraryBean d2 = lzVar.d();
        if (d2 == null || !d2.useYn.equals(ProjectLibraryBean.LIB_USE_Y)) {
            Iterator<ProjectFileBean> it = this.d.iterator();
            while (it.hasNext()) {
                ProjectFileBean next = it.next();
                if (next.hasActivityOption(4)) {
                    b(2, next.getDrawerName());
                    next.setActivityOptions(1);
                }
                if (next.hasActivityOption(8)) {
                    next.setActivityOptions(1);
                }
            }
            l();
        }
    }
}
