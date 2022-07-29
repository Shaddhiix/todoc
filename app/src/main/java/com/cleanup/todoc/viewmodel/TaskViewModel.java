package com.cleanup.todoc.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectDataRepository;
import com.cleanup.todoc.repository.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {
    
    //REPOSITORY
    private TaskDataRepository taskDataRepository;
    private ProjectDataRepository projectDataRepository;
    private Executor executor;
    
    //DATA
    private LiveData<Project> currentProject;

    public TaskViewModel(TaskDataRepository taskDataRepository, ProjectDataRepository projectDataRepository
            , Executor executor) {
        this.taskDataRepository = taskDataRepository;
        this.projectDataRepository = projectDataRepository;
        this.executor = executor;
    }
    public void init(long projectId) {
        if(this.currentProject != null) {
            return;
        }
        currentProject = projectDataRepository.getProject ( projectId );
    }
    
    /*
    * For Project
    */
    public LiveData<Project> getProject() {
        return this.currentProject;
    }
    
    /*
    * For Task
    */
    public LiveData<List<Task>> getTask() {
        return this.taskDataRepository.getTask();
    }
    
    public void createTask(Task task) {
        executor.execute (() -> {
            this.taskDataRepository.createTask(task);
        });
    }

    public void deleteTask(Task task) {
        executor.execute(() -> {
            taskDataRepository.deleteTask(task);
        });
    }
}
