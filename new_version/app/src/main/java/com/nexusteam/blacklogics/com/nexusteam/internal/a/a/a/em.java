package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import com.nexusteam.internal.beans.CollectionBean;
import com.nexusteam.internal.beans.ProjectResourceBean;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class em extends eh {
    public static em f;
    
    private em() {
    }
    
    /* access modifiers changed from: protected */
    public void b() {
        this.a = fe.w() + File.separator + "sound" + File.separator + "list";
        this.b = fe.w() + File.separator + "sound" + File.separator + "data";
    }
    
    public static em f() {
        if (f == null) {
            synchronized (em.class) {
                if (f == null) {
                    f = new em();
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
        try {
            a(str, projectResourceBean, true);
        } catch(Exception e) {
            
        }
    }
    
    public void a(String str, ProjectResourceBean projectResourceBean, boolean z) throws jg {
        if (this.e == null) {
            a();
        }
        

        for (CollectionBean collectionBean : this.e) {
            if (collectionBean.name.equals(projectResourceBean.resName)) {
                throw new jg("duplicate_name");
            }
        }
        

        String finalName = projectResourceBean.resName;
        if (projectResourceBean.resFullName.contains(".")) {
            finalName += projectResourceBean.resFullName.substring(projectResourceBean.resFullName.lastIndexOf('.'));
        }
        
        String sourcePath;
        String destPath = this.b + File.separator + finalName;
        
        if (projectResourceBean.savedPos == 1) {
            sourcePath = projectResourceBean.resFullName;
            
            if (!this.c.a(sourcePath)) {
                throw new jg("file_no_exist");
            }
        } else {
            sourcePath = fe.s() + File.separator + str + File.separator + projectResourceBean.resFullName;
            
            if (!this.c.a(sourcePath)) {
                throw new jg("file_no_exist");
            }
        }
        

        this.c.c(this.b);
        this.c.a(sourcePath, destPath);
        

        this.e.add(new CollectionBean(projectResourceBean.resName, finalName));
        
        if (z) {
            d();
        }
    }
    
    
    public void a(ProjectResourceBean projectResourceBean, String str, boolean z) {
        if (this.e == null) {
            a();
        }
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
        if (this.e == null) {
            a();
        }
        int size = this.e.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            CollectionBean collectionBean = (CollectionBean) this.e.get(size);
            if (collectionBean.name.equals(str)) {
                this.e.remove(size);
                this.c.e(this.b + File.separator + collectionBean.data);
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
