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
    public LiveData<List<Task>> getTask(long projectId) {
        return taskDataRepository.getTask ( projectId );
    }
    
    public void createTask(long id, long projectId, String name,long creationTimestamp) {
        executor.execute ( () -> {
            taskDataRepository.createTask ( new Task (id, projectId, name, creationTimestamp) );
        });
    }

    public void deleteItem(long taskId) {
        executor.execute(() -> taskDataRepository.deleteTask (taskId));
    }
}
