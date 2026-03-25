package com.besome.blacklogics.adapter;

import android.graphics.drawable.Drawable;
import com.besome.blacklogics.*;
import com.nexusteam.blacklogics.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nexusteam.internal.os.layouteditor.util.TheBlockLogicsUtil;
import com.nexusteam.blacklogics.project.CreateProjectManager;
import org.json.JSONObject;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.widget.Filter;
import android.widget.Filterable;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> implements Filterable {
    
    private List<ProjectItem> projectList;
    private List<ProjectItem> projectListFull; // For filtering
    private Context context;
    private OnItemClickListener onItemClickListener;
    
    public interface OnItemClickListener {
        void onItemClick(int position, ProjectItem project);
        void onItemLongClick(int position, ProjectItem project);
        void onEditClick(int position, ProjectItem project);
        void onBackupClick(int position, ProjectItem project);
        void onDeleteClick(int position, ProjectItem project);
        void onExportClick(int position, ProjectItem project);
        void onConfigClick(int position, ProjectItem project);
    }
    
    public static class ProjectItem implements Serializable {
        public String projectName;
        public String packageName;
        public String versionName;
        public String versionCode;
        public String scId;
        public int projectId;
        public File projectDir;
        public Drawable icon;
        public boolean isExpanded = false;
        private static final long serialVersionUID = 1L;
        private transient Context context;
        
        public ProjectItem(Context context, File projectDir) {
            this.projectDir = projectDir;
            this.context = context;
            
            try {

                String dirName = projectDir.getName();
                if (dirName.matches("\\d+")) {
                    this.projectId = Integer.parseInt(dirName);
                } else if (dirName.startsWith("PROJECT")) {
                    this.projectId = Integer.parseInt(dirName.substring(7));
                }
                
                JSONObject config = null;
                

                try {
                    CreateProjectManager manager =
                    new CreateProjectManager(
                    context,
                    "blacklogics@2026".toCharArray()
                    );
                    
                    config = manager.loadEncryptedConfig(projectDir);
                    
                } catch (Exception ignore) {

                    File oldConfig = new File(projectDir, "config");
                    if (oldConfig.exists()) {
                        config = TheBlockLogicsUtil.getProjectConfig(oldConfig);
                    }
                }
                

                if (config != null) {
                    this.projectName =
                    config.optString("projectName", "Untitled");
                    
                    this.packageName =
                    config.optString("packageName", "com.example.unknown");
                    
                    this.versionName =
                    config.optString("versionName", "1.0");
                    
                    this.versionCode =
                    config.optString("versionCode", "1");
                }
                

                File iconFile = new File(projectDir, "icon.png");
                if (iconFile.exists()) {
                    this.icon = Drawable.createFromPath(iconFile.getAbsolutePath());
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }
    
    public static class ProjectViewHolder extends RecyclerView.ViewHolder {
        ImageView appIcon;
        TextView appName;
        TextView appVersion;
        TextView packageName;
        TextView projectId;
        ImageView btnExpand;
        LinearLayout expandedContent;
        ImageView btnEdit;
        ImageView btnBackup;
        ImageView btnDelete;
        ImageView btnExport;
        ImageView btnConfig;
        
        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            appIcon = itemView.findViewById(R.id.app_icon);
            appName = itemView.findViewById(R.id.app_name);
            appVersion = itemView.findViewById(R.id.app_version_version_code);
            packageName = itemView.findViewById(R.id.package_name);
            projectId = itemView.findViewById(R.id.project_id);
            btnExpand = itemView.findViewById(R.id.btn_expand);
            expandedContent = itemView.findViewById(R.id.expanded_content);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnBackup = itemView.findViewById(R.id.btn_backup);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnExport = itemView.findViewById(R.id.export_project);
            btnConfig = itemView.findViewById(R.id.configLayout);
        }
    }
    
    public ProjectAdapter(Context context) {
        this.context = context;
        this.projectList = new ArrayList<>();
        this.projectListFull = new ArrayList<>();
        loadProjects();
    }
    
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    
    private void loadProjects() {
        projectList.clear();
        projectListFull.clear();
        File projectsDir = new File(TheBlockLogicsUtil.mysc);
        if (projectsDir.exists() && projectsDir.isDirectory()) {
            File[] projectDirs = projectsDir.listFiles();
            if (projectDirs != null) {
                for (File projectDir : projectDirs) {
                    if (projectDir.isDirectory() && (projectDir.getName().matches("\\d+") || projectDir.getName().startsWith("PROJECT"))) {
                        ProjectItem item = new ProjectItem(context, projectDir);
                        projectList.add(item);
                        projectListFull.add(item);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
    
    @Override
    public Filter getFilter() {
        return projectFilter;
    }
    
    private Filter projectFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ProjectItem> filteredList = new ArrayList<>();
            
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(projectListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                
                for (ProjectItem item : projectListFull) {
                    if (item.projectName.toLowerCase().contains(filterPattern) ||
                    item.packageName.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            projectList.clear();
            projectList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
    
    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_project, parent, false);
        return new ProjectViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {

        final int finalPosition = position;
        final ProjectItem project = projectList.get(position);
        
        holder.appName.setText(project.projectName);
        holder.appVersion.setText(String.format("%s (%s)", project.versionName, project.versionCode));
        holder.packageName.setText(project.packageName);
        holder.projectId.setText(String.valueOf(project.projectId));
        
        if (project.icon != null) {
            holder.appIcon.setImageDrawable(project.icon);
        } else {
            holder.appIcon.setImageResource(R.mipmap.ic_launcher);
        }
        

        holder.expandedContent.setVisibility(project.isExpanded ? View.VISIBLE : View.GONE);
        holder.btnExpand.setImageResource(project.isExpanded ? R.drawable.ic_expand_less : R.drawable.ic_expand_more);
        

        holder.btnExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                project.isExpanded = !project.isExpanded;
                notifyItemChanged(finalPosition);
            }
        });
        
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(finalPosition, project);
                }
            }
        });
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onEditClick(finalPosition, project);
                }
            }
        });
        
        holder.btnBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onBackupClick(finalPosition, project);
                }
            }
        });
        
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onDeleteClick(finalPosition, project);
                }
            }
        });
        
        holder.btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onExportClick(finalPosition, project);
                }
            }
        });
        
        holder.btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onConfigClick(finalPosition, project);
                }
            }
        });
        

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemLongClick(finalPosition, project);
                }
                return true;
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return projectList.size();
    }
    
    public void refresh() {
        loadProjects();
    }
    
    public ProjectItem getProjectAt(int position) {
        if (position >= 0 && position < projectList.size()) {
            return projectList.get(position);
        }
        return null;
    }
    
    public void removeProject(int position) {
        if (position >= 0 && position < projectList.size()) {
            projectList.remove(position);
            notifyItemRemoved(position);
        }
    }
    
    public void collapseAll() {
        for (ProjectItem item : projectList) {
            item.isExpanded = false;
        }
        notifyDataSetChanged();
    }
}