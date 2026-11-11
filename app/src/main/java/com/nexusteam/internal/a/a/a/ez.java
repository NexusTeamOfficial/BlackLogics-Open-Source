package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.internal.beans.ViewBean;
import java.util.ArrayList;

public class ez {

    public static ArrayList<ProjectFileBean> a() {
        ArrayList<ProjectFileBean> r0_ArrayList_ProjectFileBean = new ArrayList();
        r0_ArrayList_ProjectFileBean.add(d());
        r0_ArrayList_ProjectFileBean.add(e());
        r0_ArrayList_ProjectFileBean.add(f());
        return r0_ArrayList_ProjectFileBean;
    }

    public static ArrayList<ViewBean> a(String r2_String) {
        int r2i;
        int r0i = r2_String.hashCode();
        if (r0i != -1014430975) {
            if (r0i != -464182078) {
                if (r0i != -230447966) {
                } else if (r2_String.equals("Text Activity")) {
                    r2i = 2;
                    switch(r2i) {
                    case 0:
                        return i();
                    case 1:
                        return j();
                    case 2:
                        return k();
                    }
                    return new ArrayList();
                }
            } else if (r2_String.equals("Empty Activity")) {
                r2i = 0;
                switch(r2i) {
                case 0:
                    return i();
                case 1:
                    return j();
                case 2:
                    return k();
                }
                return new ArrayList();
            }
        } else if (r2_String.equals("Basic Activity")) {
            r2i = 1;
            switch(r2i) {
            case 0:
                return i();
            case 1:
                return j();
            case 2:
                return k();
            }
            return new ArrayList();
        }
        r2i = -1;
        switch(r2i) {
        case 0:
            return i();
        case 1:
            return j();
        case 2:
            return k();
        }
        return new ArrayList();
    }

    public static ArrayList<ProjectFileBean> b() {
        ArrayList<ProjectFileBean> r0_ArrayList_ProjectFileBean = new ArrayList();
        r0_ArrayList_ProjectFileBean.add(g());
        return r0_ArrayList_ProjectFileBean;
    }

    public static ArrayList<ViewBean> b(String r2_String) {
        int r2i;
        if (r2_String.hashCode() != 1424216003) {
        } else if (r2_String.equals("Basic List Item")) {
            r2i = 0;
            if (r2i == 0) {
                return new ArrayList();
            } else {
                return l();
            }
        }
        r2i = -1;
        if (r2i == 0) {
            return l();
        } else {
            return new ArrayList();
        }
    }

    public static ArrayList<ProjectFileBean> c() {
        ArrayList<ProjectFileBean> r0_ArrayList_ProjectFileBean = new ArrayList();
        r0_ArrayList_ProjectFileBean.add(h());
        return r0_ArrayList_ProjectFileBean;
    }

    public static ArrayList<ViewBean> c(String r2_String) {
        int r2i;
        if (r2_String.hashCode() != 920477027) {
        } else if (r2_String.equals("Basic Drawer")) {
            r2i = 0;
            if (r2i == 0) {
                return new ArrayList();
            } else {
                return m();
            }
        }
        r2i = -1;
        if (r2i == 0) {
            return m();
        } else {
            return new ArrayList();
        }
    }

    public static int d(String r3_String) {
        int r3i;
        int r0i = r3_String.hashCode();
        if (r0i != -1014430975) {
            if (r0i != -464182078) {
                if (r0i != -230447966) {
                } else if (r3_String.equals("Text Activity")) {
                    r3i = 2;
                    switch(r3i) {
                    case 0:
                        return R.drawable.activity_preset_4;
                    case 1:
                        return R.drawable.activity_preset_1;
                    case 2:
                        return R.drawable.activity_preset_1;
                    }
                    return -1;
                }
            } else if (r3_String.equals("Empty Activity")) {
                r3i = 0;
                switch(r3i) {
                case 0:
                    return R.drawable.activity_preset_4;
                case 1:
                    return R.drawable.activity_preset_1;
                case 2:
                    return R.drawable.activity_preset_1;
                }
                return -1;
            }
        } else if (r3_String.equals("Basic Activity")) {
            r3i = 1;
            switch(r3i) {
            case 0:
                return R.drawable.activity_preset_4;
            case 1:
                return R.drawable.activity_preset_1;
            case 2:
                return R.drawable.activity_preset_1;
            }
            return -1;
        }
        r3i = -1;
        switch(r3i) {
        case 0:
            return R.drawable.activity_preset_4;
        case 1:
            return R.drawable.activity_preset_1;
        case 2:
            return R.drawable.activity_preset_1;
        }
        return -1;
    }

    private static ProjectFileBean d() {
        return new ProjectFileBean(0, (String) null, "Empty Activity", 0, 0, false, true, false, false);
    }

    public static int e(String r3_String) {
        int r3i;
        if (r3_String.hashCode() != 1424216003) {
        } else if (r3_String.equals("Basic List Item")) {
            r3i = 0;
            if (r3i == 0) {
                return -1;
            } else {
                return R.drawable.activity_preset_1;
            }
        }
        r3i = -1;
        if (r3i == 0) {
            return R.drawable.activity_preset_1;
        } else {
            return -1;
        }
    }

    private static ProjectFileBean e() {
        return new ProjectFileBean(0, (String) null, "Basic Activity", 0, 0, true, false, false, false);
    }

    public static int f(String r3_String) {
        int r3i;
        if (r3_String.hashCode() != 920477027) {
        } else if (r3_String.equals("Basic Drawer")) {
            r3i = 0;
            if (r3i == 0) {
                return -1;
            } else {
                return R.drawable.activity_preset_1;
            }
        }
        r3i = -1;
        if (r3i == 0) {
            return R.drawable.activity_preset_1;
        } else {
            return -1;
        }
    }

    private static ProjectFileBean f() {
        return new ProjectFileBean(0, (String) null, "Text Activity", 0, 0, true, false, false, false);
    }

    private static ProjectFileBean g() {
        return new ProjectFileBean(1, null, "Basic List Item");
    }

    private static ProjectFileBean h() {
        return new ProjectFileBean(2, null, "Basic Drawer");
    }

    private static ArrayList<ViewBean> i() {
        return new ArrayList();
    }

    private static ArrayList<ViewBean> j() {
        return new ArrayList();
    }

    private static ArrayList<ViewBean> k() {
        ArrayList<ViewBean> r0_ArrayList_ViewBean = new ArrayList();
        r0_ArrayList_ViewBean.add(n());
        r0_ArrayList_ViewBean.add(n());
        r0_ArrayList_ViewBean.add(n());
        r0_ArrayList_ViewBean.add(n());
        r0_ArrayList_ViewBean.add(n());
        r0_ArrayList_ViewBean.add(n());
        r0_ArrayList_ViewBean.add(n());
        return r0_ArrayList_ViewBean;
    }

    private static ArrayList<ViewBean> l() {
        ArrayList<ViewBean> r0_ArrayList_ViewBean = new ArrayList();
        r0_ArrayList_ViewBean.add(n());
        r0_ArrayList_ViewBean.add(n());
        r0_ArrayList_ViewBean.add(n());
        r0_ArrayList_ViewBean.add(n());
        r0_ArrayList_ViewBean.add(n());
        r0_ArrayList_ViewBean.add(n());
        r0_ArrayList_ViewBean.add(n());
        return r0_ArrayList_ViewBean;
    }

    private static ArrayList<ViewBean> m() {
        ArrayList<ViewBean> r0_ArrayList_ViewBean = new ArrayList();
        r0_ArrayList_ViewBean.add(n());
        r0_ArrayList_ViewBean.add(n());
        r0_ArrayList_ViewBean.add(n());
        r0_ArrayList_ViewBean.add(n());
        r0_ArrayList_ViewBean.add(n());
        r0_ArrayList_ViewBean.add(n());
        r0_ArrayList_ViewBean.add(n());
        return r0_ArrayList_ViewBean;
    }

    private static ViewBean n() {
        ViewBean r0_ViewBean = new ViewBean("textview1", 4);
        r0_ViewBean.parent = "root";
        r0_ViewBean.index = 0;
        r0_ViewBean.preParentType = 0;
        r0_ViewBean.name = "textview1";
        r0_ViewBean.text.text = "TextView";
        r0_ViewBean.layout.paddingTop = 8;
        r0_ViewBean.layout.paddingBottom = 8;
        r0_ViewBean.layout.paddingLeft = 8;
        r0_ViewBean.layout.paddingRight = 8;
        return r0_ViewBean;
    }
}
