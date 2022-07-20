package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDAO;
import com.cleanup.todoc.model.Project;

public class ProjectDataRepository {
    
    private ProjectDAO projectDAO;
    
    public ProjectDataRepository(ProjectDAO projectDAO){
        this.projectDAO = projectDAO;
    }
    
    public LiveData<Project> getProject(long projectId) {
        return this.projectDAO.getProject ( projectId );
    }
}
