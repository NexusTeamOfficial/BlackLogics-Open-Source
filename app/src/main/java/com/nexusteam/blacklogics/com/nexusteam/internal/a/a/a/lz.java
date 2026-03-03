package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import com.nexusteam.internal.beans.ProjectLibraryBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;
import java.io.IOException;

public class lz {
    
    /* renamed from: a  reason: collision with root package name */
    public String f370a;
    private kk b = new kk();
    private ProjectLibraryBean c;
    private ProjectLibraryBean d;
    private ProjectLibraryBean e;
    private ProjectLibraryBean f;
    private Gson g = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    
    public lz(String str) {
        this.f370a = str;
        l();
    }
    
    private void l() {
        this.c = new ProjectLibraryBean(0);
        this.d = new ProjectLibraryBean(1);
        this.e = new ProjectLibraryBean(2);
        this.f = new ProjectLibraryBean(3);
    }
    
    public void a() {
        this.f370a = "";
        l();
    }
    
    public void b() {

        l();
        

        String basePath = fe.d(this.f370a);
        String libraryPath = basePath + java.io.File.separator + "library";
        

        if (!this.b.a(libraryPath)) {
            return;
        }
        
        BufferedReader reader = null;
        try {

            byte[] bytes = this.b.f(libraryPath);

            String data = this.b.b(bytes);
            

            reader = new BufferedReader(new StringReader(data));
            

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
        String str = "";
        try {
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
            if (this.c == null) {
                this.c = new ProjectLibraryBean(0);
            }
            if (this.d == null) {
                this.d = new ProjectLibraryBean(1);
            }
            if (this.e == null) {
                this.e = new ProjectLibraryBean(2);
            }
            if (this.f == null) {
                this.f = new ProjectLibraryBean(3);
            }
        } catch (Exception e2) {

        }
    }
    
    public void a(String str, String str2) {
        if (str2.length() > 0) {
            BufferedReader bufferedReader = null;
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new StringReader(str2));
                try {
                    String readLine = bufferedReader2.readLine();
                    if (readLine.charAt(0) != '{') {
                        throw new Exception("invalid view data string");
                    }
                    ProjectLibraryBean projectLibraryBean = (ProjectLibraryBean) this.g.fromJson(readLine, ProjectLibraryBean.class);
                    if (str.equals("firebaseDB")) {
                        this.c = projectLibraryBean;
                    } else if (str.equals("compat")) {
                        this.d = projectLibraryBean;
                    } else if (str.equals("admob")) {
                        this.e = projectLibraryBean;
                    } else if (str.equals("googleMap")) {
                        this.f = projectLibraryBean;
                    }
                    if (bufferedReader2 != null) {
                        try {
                            bufferedReader2.close();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                } catch (Exception e3) {

                    bufferedReader = bufferedReader2;
                    try {

                    } catch (Throwable th) {

                        bufferedReader2 = bufferedReader;
                    }
                } catch (Throwable th2) {

                    if (bufferedReader2 != null) {
                        try {
                            bufferedReader2.close();
                        } catch (Exception e4) {
                            e4.printStackTrace();
                        }
                    }

                }
            } catch (Exception e5) {


            }
        }
    }
    
    public ProjectLibraryBean c() {
        return this.c;
    }
    
    public void a(ProjectLibraryBean projectLibraryBean) {
        this.c = projectLibraryBean;
    }
    
    public ProjectLibraryBean d() {
        return this.d;
    }
    
    public void b(ProjectLibraryBean projectLibraryBean) {
        this.d = projectLibraryBean;
    }
    
    public ProjectLibraryBean e() {
        return this.e;
    }
    
    public void c(ProjectLibraryBean projectLibraryBean) {
        this.e = projectLibraryBean;
    }
    
    public ProjectLibraryBean f() {
        return this.f;
    }
    
    public void d(ProjectLibraryBean projectLibraryBean) {
        this.f = projectLibraryBean;
    }
    
    public void g() {
        String d2 = fe.d(this.f370a);
        a(d2 + File.separator + "library");
        h();
    }
    
    private void a(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        a(stringBuffer);
        try {
            this.b.a(str, this.b.g(stringBuffer.toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    
    private void a(StringBuffer stringBuffer) {
        if (this.c != null) {
            stringBuffer.append("@");
            stringBuffer.append("firebaseDB");
            stringBuffer.append("\n");
            stringBuffer.append(this.g.toJson(this.c, ProjectLibraryBean.class));
            stringBuffer.append("\n");
        }
        if (this.d != null) {
            stringBuffer.append("@");
            stringBuffer.append("compat");
            stringBuffer.append("\n");
            stringBuffer.append(this.g.toJson(this.d, ProjectLibraryBean.class));
            stringBuffer.append("\n");
        }
        if (this.e != null) {
            stringBuffer.append("@");
            stringBuffer.append("admob");
            stringBuffer.append("\n");
            stringBuffer.append(this.g.toJson(this.e, ProjectLibraryBean.class));
            stringBuffer.append("\n");
        }
        if (this.f != null) {
            stringBuffer.append("@");
            stringBuffer.append("googleMap");
            stringBuffer.append("\n");
            stringBuffer.append(this.g.toJson(this.f, ProjectLibraryBean.class));
            stringBuffer.append("\n");
        }
    }
    
    public void h() {
        String e2 = fe.e(this.f370a);
        this.b.e(e2 + File.separator + "library");
    }
    
    public void i() {
        String e2 = fe.e(this.f370a);
        a(e2 + File.separator + "library");
    }
    
    public void j() {

        l();
        

        String basePath = fe.e(this.f370a);
        

        String path = basePath + File.separator + "library";
        
        BufferedReader reader = null;
        
        try {

            byte[] data = this.b.f(path);
            String decoded = this.b.b(data);
            

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
    
    
    public boolean k() {
        String e2 = fe.e(this.f370a);
        return this.b.a(e2 + File.separator + "library");
    }
}
