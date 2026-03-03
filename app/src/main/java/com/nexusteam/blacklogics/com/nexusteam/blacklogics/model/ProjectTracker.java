
package com.nexusteam.blacklogics.model;

public class ProjectTracker {
    
    private static String currentProjectId;
    private static ProjectTracker instance;
    private ProjectFile projectFile;
    private ProjectSettings projectSettings;
    
    private ProjectTracker() {

    }
    
    public static synchronized ProjectTracker getInstance() {
        if (instance == null) {
            instance = new ProjectTracker();
        }
        return instance;
    }
    
    public static void setScId(String projectId) {
        currentProjectId = projectId;
    }
    
    public static String getScId() {
        return currentProjectId;
    }
    
    public ProjectFile getProjectFile() {
        if (projectFile == null || !projectFile.getProjectId().equals(currentProjectId)) {
            projectFile = new ProjectFile(currentProjectId);
        }
        return projectFile;
    }
    
    public ProjectSettings getProjectSettings() {
        if (projectSettings == null || !projectSettings.sc_id.equals(currentProjectId)) {
            projectSettings = new ProjectSettings(currentProjectId);
        }
        return projectSettings;
    }
    
    public void clear() {
        currentProjectId = null;
        projectFile = null;
        projectSettings = null;
    }
    
    public boolean hasProject() {
        return currentProjectId != null && !currentProjectId.isEmpty();
    }
    
    public static boolean isProjectLoaded() {
        return currentProjectId != null && !currentProjectId.isEmpty();
    }
}