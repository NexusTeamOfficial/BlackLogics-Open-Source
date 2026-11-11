package com.nexusteam.blacklogics.activityfile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nexusteam.blacklogics.R;
import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.internal.ma;

import java.util.ArrayList;

public class ViewSelectorActivity extends AppCompatActivity {

    private String scId;
    private ArrayList<ProjectFileBean> files = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scId = getIntent().getStringExtra("sc_id");
        if (scId == null) scId = "";

        RecyclerView rv = new RecyclerView(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(rv);

        loadFiles();
        rv.setAdapter(new Adapter());
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Select screen");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void loadFiles() {
        files.clear();
        try {
            if (ma.b(scId) != null) {
                ArrayList list = ma.b(scId).a();
                if (list != null) {
                    for (Object o : list) {
                        if (o instanceof ProjectFileBean) files.add((ProjectFileBean) o);
                    }
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private void select(ProjectFileBean bean) {
        Intent data = new Intent();
        data.putExtra("project_file", bean);
        data.putExtra("activity_name", bean.getJavaName());
        data.putExtra("layout_name", bean.getXmlName());
        setResult(Activity.RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    class Adapter extends RecyclerView.Adapter<Adapter.VH> {
        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView tv = new TextView(parent.getContext());
            int pad = (int) (16 * getResources().getDisplayMetrics().density);
            tv.setPadding(pad, pad, pad, pad);
            tv.setTextSize(16);
            tv.setLayoutParams(new RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new VH(tv);
        }

        @Override
        public void onBindViewHolder(VH h, int position) {
            final ProjectFileBean bean = files.get(position);
            String type = bean.fileType == 0 ? "Activity"
                    : bean.fileType == 1 ? "CustomView" : "Drawer";
            String java = bean.getJavaName();
            String xml = bean.getXmlName();
            h.tv.setText(type + ": " + (java != null && !java.isEmpty() ? java : bean.fileName)
                    + "  (" + xml + ")");
            h.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    select(bean);
                }
            });
        }

        @Override
        public int getItemCount() {
            return files.size();
        }

        class VH extends RecyclerView.ViewHolder {
            TextView tv;
            VH(TextView v) {
                super(v);
                tv = v;
            }
        }
    }
}
