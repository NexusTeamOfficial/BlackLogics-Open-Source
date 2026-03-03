package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import com.nexusteam.internal.beans.CollectionBean;
import com.nexusteam.internal.beans.ProjectResourceBean;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ej extends eh {
    public static ej f;
    
    private ej() {
    }
    
    /* access modifiers changed from: protected */
    public void b() {
        this.a = fe.w() + File.separator + "font" + File.separator + "list";
        this.b = fe.w() + File.separator + "font" + File.separator + "data";
    }
    
    public static ej f() {
        if (f == null) {
            synchronized (ej.class) {
                if (f == null) {
                    f = new ej();
                }
            }
        }
        return f;
    }
    
    public ArrayList<ProjectResourceBean> g() {
        if (this.e == null) {
            a();
        }
        ArrayList<ProjectResourceBean> arrayList = new ArrayList<>();
        Iterator it = this.e.iterator();
        while (it.hasNext()) {
            CollectionBean collectionBean = (CollectionBean) it.next();
            arrayList.add(new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, collectionBean.name, collectionBean.data));
        }
        return arrayList;
    }
    
    public boolean a(String str) {
        Iterator<ProjectResourceBean> it = g().iterator();
        while (it.hasNext()) {
            if (it.next().resName.equals(str)) {
                return true;
            }
        }
        return false;
    }
    
    public ProjectResourceBean b(String str) {
        Iterator<ProjectResourceBean> it = g().iterator();
        while (it.hasNext()) {
            ProjectResourceBean next = it.next();
            if (next.resName.equals(str)) {
                return next;
            }
        }
        return null;
    }
    
    public void a(String str, ProjectResourceBean projectResourceBean) {
        a(str, projectResourceBean, true);
    }
    
    public void a(String str, ProjectResourceBean projectResourceBean, boolean z) {
        if (this.e == null) {
            a();
        }
        
        ArrayList<String> duplicates = new ArrayList<>();
        for (CollectionBean collectionBean : this.e) {
            if (collectionBean.name.equals(projectResourceBean.resName)) {
                duplicates.add(collectionBean.name);
                break;
            }
        }
        
        if (!duplicates.isEmpty()) {
            try {
                throw new jg("duplicate_name");
            } catch (jg e) {
                e.printStackTrace();
            }
        }
        
        String str2 = projectResourceBean.resName;
        if (projectResourceBean.resFullName.contains(".")) {
            str2 = str2 + projectResourceBean.resFullName.substring(projectResourceBean.resFullName.lastIndexOf('.'));
        }
        
        String str4 = this.b + File.separator + str2;
        
        if (projectResourceBean.savedPos == 1) {
            String str5 = projectResourceBean.resFullName;
            if (this.c.a(str5)) {
                this.c.c(this.b);
                this.c.a(str5, str4);
            } else {
                try {
                    throw new jg("file_no_exist");
                } catch (jg e) {
                    e.printStackTrace();
                }
            }
        } else {
            String str6 = fe.t() + File.separator + str + File.separator + projectResourceBean.resFullName;
            if (this.c.a(str6)) {
                this.c.c(this.b);
                this.c.a(str6, str4);
            } else {
                try {
                    throw new jg("file_no_exist");
                } catch (jg e) {
                    e.printStackTrace();
                }
            }
        }
        
        this.e.add(new CollectionBean(projectResourceBean.resName, str2));
        if (z) {
            d();
        }
    }
    
    
    public void a(ProjectResourceBean projectResourceBean, String str, boolean z) {
        int size = this.e.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            CollectionBean collectionBean = (CollectionBean) this.e.get(size);
            if (collectionBean.name.equals(projectResourceBean.resName)) {
                collectionBean.name = str;
                break;
            }
        }
        if (z) {
            d();
        }
    }
    
    public void a(String str, boolean z) {
        int size = this.e.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            CollectionBean collectionBean = (CollectionBean) this.e.get(size);
            if (collectionBean.name.equals(str)) {
                this.e.remove(size);
                String str2 = collectionBean.data;
                this.c.e(this.b + File.separator + str2);
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
