package com.nexusteam.internal.editor.view;
import com.nexusteam.blacklogics.R;

import com.nexusteam.internal.ex;
import com.nexusteam.internal.fl;
import com.nexusteam.internal.ke;
import com.nexusteam.internal.ki;
import com.nexusteam.internal.kp;
import com.nexusteam.internal.kq;
import com.nexusteam.internal.ma;
import android.content.Context;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nexusteam.internal.beans.EventBean;
import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.internal.beans.ViewBean;
import java.util.ArrayList;
import java.util.Iterator;

public class ViewEvents extends LinearLayout {
    
    /* renamed from: a  reason: collision with root package name */
    private String f1396a;
    private ProjectFileBean b;
    private ViewBean c;
    /* access modifiers changed from: private */
    public ArrayList<EventBean> d;
    private RecyclerView e;
    private fl f;
    
    public ViewEvents(Context context) {
        super(context);
        a(context);
    }
    
    public ViewEvents(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }
    
    private void a(Context context) {
        kp.a(context, this, R.layout.view_events);
        this.d = new ArrayList<>();
        this.e = (RecyclerView) findViewById(R.id.list_events);
        this.e.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(0);
        this.e.setLayoutManager(linearLayoutManager);
        this.e.setAdapter(new a());
        this.e.setItemAnimator(new DefaultItemAnimator());
    }
    
    public void setOnEventClickListener(fl flVar) {
        this.f = flVar;
    }
    
    public void a(String str, ProjectFileBean projectFileBean, ViewBean viewBean) {
        boolean z;
        this.f1396a = str;
        this.b = projectFileBean;
        this.c = viewBean;
        String[] a2 = ex.a(viewBean.getClassInfo());
        this.d.clear();
        if (a2 != null) {
            ArrayList<EventBean> j = ma.a(str).j(projectFileBean.getJavaName());
            for (String str2 : a2) {
                Iterator<EventBean> it = j.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    EventBean next = it.next();
                    if (next.eventType == 1 && viewBean.id.equals(next.targetId) && str2.equals(next.eventName)) {
                        z = true;
                        break;
                    }
                }
                if (!str2.equals("onBindCustomView") || viewBean.type != 9 || (!viewBean.customView.equals("") && !viewBean.customView.equals("none"))) {
                    EventBean eventBean = new EventBean(1, viewBean.type, viewBean.id, str2);
                    eventBean.isSelected = z;
                    this.d.add(eventBean);
                }
            }
        }
        this.e.getAdapter().notifyDataSetChanged();
    }
    
    /* access modifiers changed from: private */
    public void a(int i) {
        EventBean eventBean = this.d.get(i);
        if (!eventBean.isSelected) {
            eventBean.isSelected = true;
            ma.a(this.f1396a).a(this.b.getJavaName(), eventBean);
            this.e.getAdapter().notifyItemChanged(i);
            ke.a(getContext(), (CharSequence) kq.a().a(getContext(), R.string.event_message_new_event), 0).show();
        }
        if (this.f != null) {
            this.f.a(eventBean);
        }
    }
    
    class a extends RecyclerView.Adapter<a.ViewHolder> {
        
        public a() {}
        
        @Override
        public a.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.event_grid_item, parent, false);
            return new ViewHolder(view);
        }
        
        @Override
        public void onBindViewHolder(a.ViewHolder holder, int position) {
            EventBean eventBean = ViewEvents.this.d.get(position);
            
            if (eventBean.isSelected) {
                holder.c.setVisibility(View.GONE);
                ki.a(holder.b, 1);
            } else {
                holder.c.setVisibility(View.VISIBLE);
                ki.a(holder.b, 0);
            }
            
            holder.b.setImageResource(ex.b(eventBean.eventName));
            holder.d.setText(eventBean.eventName);
        }
        
        @Override
        public int getItemCount() {
            return ViewEvents.this.d.size();
        }
        
        class ViewHolder extends RecyclerView.ViewHolder {
            
            LinearLayout container;
            ImageView b;
            ImageView c;
            TextView d;
            
            public ViewHolder(View itemView) {
                super(itemView);
                container = itemView.findViewById(R.id.container);
                b = itemView.findViewById(R.id.img_icon);
                c = itemView.findViewById(R.id.img_used_event);
                d = itemView.findViewById(R.id.tv_title);
                
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewEvents.this.a(getLayoutPosition());
                    }
                });
            }
        }
    }
    
}
