package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import com.nexusteam.internal.beans.CollectionBean;
import com.nexusteam.internal.beans.ProjectResourceBean;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ek extends eh {
    public static ek f;
    
    private ek() {
    }
    
    /* access modifiers changed from: protected */
    public void b() {
        this.a = fe.w() + File.separator + "image" + File.separator + "list";
        this.b = fe.w() + File.separator + "image" + File.separator + "data";
    }
    
    public static ek f() {
        if (f == null) {
            synchronized (ek.class) {
                if (f == null) {
                    f = new ek();
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
        String str2;
        if (this.e == null) {
            a();
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = this.e.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            CollectionBean collectionBean = (CollectionBean) it.next();
            if (collectionBean.name.equals(projectResourceBean.resName)) {
                arrayList.add(collectionBean.name);
                break;
            }
        }
        if (arrayList.size() > 0) {
            try {
                throw new jg("duplicate_name");
            } catch(Exception e) {}
        }
        String str3 = projectResourceBean.resName;
        if (projectResourceBean.isNinePatch()) {
            str2 = str3 + ".9.png";
        } else {
            str2 = str3 + ".png";
        }
        String str4 = this.b + File.separator + str2;
        if (projectResourceBean.savedPos == 1) {
            String str5 = projectResourceBean.resFullName;
            if (this.c.a(str5)) {
                try {
                    this.c.c(this.b);
                    kh.a(str5, str4, projectResourceBean.rotate, projectResourceBean.flipHorizontal, projectResourceBean.flipVertical);
                } catch (Exception unused) {
                    try {
                        throw new jg("fail_to_copy");
                    } catch(Exception e) {}
                }
            } else {
                try {
                    throw new jg("file_no_exist");
                } catch(Exception e) {}
            }
        } else {
            String str6 = fe.r() + File.separator + str + File.separator + projectResourceBean.resFullName;
            if (this.c.a(str6)) {
                try {
                    this.c.c(this.b);
                    this.c.a(str6, str4);
                } catch (Exception unused2) {
                    try {
                        throw new jg("fail_to_copy");
                    } catch(Exception e) {}
                }
            } else {
                try {
                    throw new jg("file_no_exist");
                } catch(Exception e) {}
            }
        }
        this.e.add(new CollectionBean(projectResourceBean.resName, str2));
        if (z) {
            d();
        }
    }
    
    public void a(String str, ArrayList<ProjectResourceBean> arrayList, boolean z) {
        String str2;
        String str3;
        String str4;
        if (this.e == null) {
            a();
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it = this.e.iterator();
        while (it.hasNext()) {
            CollectionBean collectionBean = (CollectionBean) it.next();
            Iterator<ProjectResourceBean> it2 = arrayList.iterator();
            while (it2.hasNext()) {
                if (collectionBean.name.equals(it2.next().resName)) {
                    arrayList2.add(collectionBean.name);
                }
            }
        }
        if (arrayList2.size() > 0) {
            jg jgVar = new jg("duplicate_name");
            jgVar.a(arrayList2);
            try {
                throw jgVar;
            } catch(Exception e) {}
            
        }
        ArrayList arrayList3 = new ArrayList();
        Iterator<ProjectResourceBean> it3 = arrayList.iterator();
        while (it3.hasNext()) {
            ProjectResourceBean next = it3.next();
            if (next.savedPos == 0) {
                str4 = fe.r() + File.separator + str + File.separator + next.resFullName;
            } else {
                str4 = next.resFullName;
            }
            if (!this.c.a(str4)) {
                arrayList3.add(next.resName);
            }
        }
        if (arrayList3.size() > 0) {
            jg jgVar2 = new jg("file_no_exist");
            jgVar2.a(arrayList3);
            try {
                throw jgVar2;
            } catch(Exception e) {}
        }
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        Iterator<ProjectResourceBean> it4 = arrayList.iterator();
        while (it4.hasNext()) {
            ProjectResourceBean next2 = it4.next();
            String str5 = next2.resName;
            if (next2.isNinePatch()) {
                str2 = str5 + ".9.png";
            } else {
                str2 = str5 + ".png";
            }
            if (next2.savedPos == 0) {
                str3 = fe.r() + File.separator + str + File.separator + next2.resFullName;
            } else {
                str3 = next2.resFullName;
            }
            String str6 = this.b + File.separator + str2;
            try {
                this.c.c(this.b);
                kh.a(str3, str6, next2.rotate, next2.flipHorizontal, next2.flipVertical);
                this.e.add(new CollectionBean(next2.resName, str2));
                arrayList5.add(str6);
            } catch (Exception unused) {
                arrayList4.add(next2.resName);
            }
        }
        if (arrayList4.size() > 0) {
            jg jgVar3 = new jg("fail_to_copy");
            jgVar3.a(arrayList4);
            if (arrayList5.size() > 0) {
                Iterator it5 = arrayList5.iterator();
                while (it5.hasNext()) {
                    this.c.e((String) it5.next());
                }
            }
            try {
                throw jgVar3;
            } catch(Exception e) {}
        } else if (z) {
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
                this.c.e(this.b + File.separator + collectionBean.data);
                this.e.remove(size);
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
