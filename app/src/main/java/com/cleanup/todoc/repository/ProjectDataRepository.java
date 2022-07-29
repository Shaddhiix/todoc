package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDAO;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectDataRepository {
    
    private final ProjectDAO projectDAO;
    
    public ProjectDataRepository(ProjectDAO projectDAO){
        this.projectDAO = projectDAO;
    }
    
    public LiveData<List<Project>> getAllProjects() {
        return this.projectDAO.getAllProjects();
    }
}
