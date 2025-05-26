package com.besome.blacklogics.adapter;

import android.graphics.drawable.Drawable;
import com.besome.blacklogics.*;
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
		
		public ProjectItem(File projectDir) {
			this.projectDir = projectDir;
			try {
				// Extract numeric project ID from directory name
				String dirName = projectDir.getName();
				if (dirName.matches("\\d+")) {
					this.projectId = Integer.parseInt(dirName);
				} else if (dirName.startsWith("PROJECT")) {
					this.projectId = Integer.parseInt(dirName.substring(7));
				}
				
				File configFile = new File(projectDir, "config");
				if (configFile.exists()) {
					JSONObject config = TheBlockLogicsUtil.getProjectConfig(configFile);
					if (config != null) {
						this.projectName = config.optString("projectName", "Untitled");
						this.packageName = config.optString("packageName", "com.example.unknown");
						this.versionName = config.optString("versionName", "1.0");
						this.versionCode = config.optString("versionCode", "1");
					}
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
		File projectsDir = new File(TheBlockLogicsUtil.projects);
		if (projectsDir.exists() && projectsDir.isDirectory()) {
			File[] projectDirs = projectsDir.listFiles();
			if (projectDirs != null) {
				for (File projectDir : projectDirs) {
					if (projectDir.isDirectory() && (projectDir.getName().matches("\\d+") || projectDir.getName().startsWith("PROJECT"))) {
						ProjectItem item = new ProjectItem(projectDir);
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
		ProjectItem project = projectList.get(position);
		
		holder.appName.setText(project.projectName);
		holder.appVersion.setText(String.format("%s (%s)", project.versionName, project.versionCode));
		holder.packageName.setText(project.packageName);
		holder.projectId.setText(String.valueOf(project.projectId));
		
		if (project.icon != null) {
			holder.appIcon.setImageDrawable(project.icon);
		} else {
			holder.appIcon.setImageResource(R.mipmap.ic_launcher);
		}
		
		// Set expand/collapse state
		holder.expandedContent.setVisibility(project.isExpanded ? View.VISIBLE : View.GONE);
		holder.btnExpand.setImageResource(project.isExpanded ? R.drawable.ic_expand_less : R.drawable.ic_expand_more);
		
		// Set click listeners
		holder.btnExpand.setOnClickListener(v -> {
			project.isExpanded = !project.isExpanded;
			notifyItemChanged(position);
		});
		
		holder.itemView.setOnClickListener(v -> {
			if (onItemClickListener != null) {
				onItemClickListener.onItemClick(position, project);
			}
		});
		
		holder.itemView.setOnLongClickListener(v -> {
			if (onItemClickListener != null) {
				onItemClickListener.onItemLongClick(position, project);
			}
			return true;
		});
		
		holder.btnEdit.setOnClickListener(v -> {
			if (onItemClickListener != null) {
				onItemClickListener.onEditClick(position, project);
			}
		});
		
		holder.btnBackup.setOnClickListener(v -> {
			if (onItemClickListener != null) {
				onItemClickListener.onBackupClick(position, project);
			}
		});
		
		holder.btnDelete.setOnClickListener(v -> {
			if (onItemClickListener != null) {
				onItemClickListener.onDeleteClick(position, project);
			}
		});
        holder.btnExport.setOnClickListener(v -> {
			if (onItemClickListener != null) {
				onItemClickListener.onExportClick(position, project);
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
