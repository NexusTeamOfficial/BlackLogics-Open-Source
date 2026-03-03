package com.nexusteam.internal.editor.view;
import com.nexusteam.blacklogics.R;

import com.nexusteam.internal.ho;
import com.nexusteam.internal.kd;
import com.nexusteam.internal.ki;
import com.nexusteam.internal.kp;
import com.nexusteam.internal.kq;
import com.nexusteam.internal.ma;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nexusteam.internal.beans.ProjectFileBean;


public class ProjectFileSelector extends LinearLayout implements View.OnClickListener {
    /* access modifiers changed from: private */

    /* renamed from: a  reason: collision with root package name */
    public String f1383a;
    private TextView b;
    private ImageView c;
    /* access modifiers changed from: private */
    public ho d;
    private int e = -1;
    /* access modifiers changed from: private */
    public String f;
    private String g;
    private boolean h;
    /* access modifiers changed from: private */
    public kd i;

    public ProjectFileSelector(Context context) {
        super(context);
        a(context);
    }

    public ProjectFileSelector(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    private void a(Context context) {
        setOrientation(0);
        b(context);
        c(context);
        setGravity(16);
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
        setBackgroundResource(typedValue.resourceId);
        setOnClickListener(this);
        this.e = 0;
        this.f = "main.xml";
        this.g = "MainActivity.java";
        setShownText(this.f);
    }

    public void setScId(String str) {
        this.f1383a = str;
    }

    public void a(Bundle bundle) {
        bundle.putInt("file_selector_current_file_type", this.e);
        bundle.putString("file_selector_current_xml", this.f);
        bundle.putString("file_selector_current_java", this.g);
        bundle.putBoolean("file_selector_is_custom_xml", this.h);
    }

    public void b(Bundle bundle) {
        this.e = bundle.getInt("file_selector_current_file_type");
        this.f = bundle.getString("file_selector_current_xml");
        this.g = bundle.getString("file_selector_current_java");
        this.h = bundle.getBoolean("file_selector_is_custom_xml");
        setFileType(this.e);
    }

    private void b(Context context) {
        this.b = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -1);
        layoutParams.leftMargin = (int) kp.a(context, 8.0f);
        layoutParams.weight = 1.0f;
        this.b.setGravity(19);
        this.b.setLayoutParams(layoutParams);
        addView(this.b);
    }

    private void c(Context context) {
        int a2 = (int) kp.a(context, 24.0f);
        this.c = new ImageView(context);
        this.c.setLayoutParams(new LinearLayout.LayoutParams(a2, a2));
        this.c.setImageResource(R.drawable.ic_arrow_drop_down_grey600_24dp);
        addView(this.c);
    }

    public void setOnSelectedFileChangeListener(ho hoVar) {
        this.d = hoVar;
    }

    public void setFileType(int i2) {
        this.e = i2;
        if (this.e == 0) {
            setShownText(this.f);
        } else {
            setShownText(this.g);
        }
    }

    public int getFileType() {
        return this.e;
    }

    public void setXmlFileName(ProjectFileBean projectFileBean) {
        if (projectFileBean == null) {
            this.f = "main.xml";
            setShownText(this.f);
            return;
        }
        if (projectFileBean.fileType == 0) {
            this.g = projectFileBean.getJavaName();
            this.f = projectFileBean.getXmlName();
            this.h = false;
        } else if (projectFileBean.fileType == 1) {
            this.h = true;
        } else if (projectFileBean.fileType == 2) {
            this.h = true;
        }
        if (this.e == 0) {
            this.d.a(0, projectFileBean);
        } else if (this.e == 1) {
            this.d.a(1, projectFileBean);
        }
        this.f = projectFileBean.getXmlName();
        setShownText(this.f);
    }

    public void setShownText(String str) {
        if (this.e == 1) {
            this.b.setText(str);
        } else if (str.indexOf("_drawer_") == 0) {
            this.b.setText(str.substring(1, str.indexOf(".xml")));
        } else {
            this.b.setText(str);
        }
    }

    public String getFileName() {
        if (this.e == 0) {
            return this.f;
        }
        return this.g;
    }

    public void setJavaFileName(String str) {
        this.g = str;
        setShownText(this.g);
    }

    public void a() {
        ProjectFileBean projectFileBean;
        if (this.d != null) {
            if (this.e == 0) {
                if (!this.f.equals("main.xml") && ma.b(this.f1383a).a(this.f) == null) {
                    setXmlFileName((ProjectFileBean) null);
                }
                projectFileBean = ma.b(this.f1383a).a(this.f);
            } else {
                if (!this.g.equals("MainActivity.java") && ma.b(this.f1383a).b(this.g) == null) {
                    setJavaFileName("MainActivity.java");
                }
                projectFileBean = ma.b(this.f1383a).b(this.g);
            }
            this.d.a(this.e, projectFileBean);
        }
    }

    public void onClick(View view) {
        if (!ki.a()) {
            if (this.e == 0) {
                b();
            } else {
                c();
            }
        }
    }

    public void b() {
      /*  Intent intent = new Intent(getContext(), ViewSelectorActivity.class);
        intent.putExtra("sc_id", this.f1383a);
        intent.putExtra("current_xml", this.f);
        intent.putExtra("is_custom_view", this.h);
        ((Activity) getContext()).startActivityForResult(intent, 263);*/
    }

    public void c() {
        this.i = new kd((Activity) getContext());
        this.i.a(kq.a().a(getContext(), R.string.design_file_selector_title_java));
        this.i.a(R.drawable.java_96);
        View a2 = kp.a(getContext(), R.layout.file_selector_popup_select_java);
        RecyclerView recyclerView = (RecyclerView) a2.findViewById(R.id.file_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), 1, false));
        recyclerView.setAdapter(new a());
        this.i.a(a2);
        this.i.show();
    }

    class a extends RecyclerView.Adapter<a.ViewHolder> {
        public a() {
        }

        /* renamed from: a */
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.file_selector_popup_select_java_list_item, viewGroup, false));
        }

        /* renamed from: a */
        public void onBindViewHolder(ViewHolder aVar, int i) {
            aVar.f1385a.setVisibility(0);
            aVar.b.setVisibility(0);
            ProjectFileBean projectFileBean = ma.b(ProjectFileSelector.this.f1383a).a().get(i);
            String javaName = projectFileBean.getJavaName();
            String xmlName = projectFileBean.getXmlName();
            aVar.f1385a.setText(javaName);
            aVar.b.setText(xmlName);
        }

        public int getItemCount() {
            return ma.b(ProjectFileSelector.this.f1383a).a().size();
        }

        /* renamed from: com.nexusteam.internal.editor.view.ProjectFileSelector$a$a  reason: collision with other inner class name */
        class ViewHolder extends RecyclerView.ViewHolder {

            /* renamed from: a  reason: collision with root package name */
            public TextView f1385a;
            public TextView b;

            public ViewHolder(View view) {
                super(view);
                this.f1385a = (TextView) view.findViewById(R.id.tv_filename);
                this.b = (TextView) view.findViewById(R.id.tv_linked_filename);
                view.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        ProjectFileBean projectFileBean = ma.b(ProjectFileSelector.this.f1383a).a().get(ViewHolder.this.getLayoutPosition());
                        ProjectFileSelector.this.setJavaFileName(projectFileBean.getJavaName());
                        if (projectFileBean.fileType == 0) {
                            String unused = ProjectFileSelector.this.f = projectFileBean.getXmlName();
                        }
                        ProjectFileSelector.this.d.a(1, projectFileBean);
                        ProjectFileSelector.this.i.dismiss();
                    }
                });
            }
        }
    }
}
