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
    
    public TaskViewModel(TaskDataRepository taskDataRepository, ProjectDataRepository projectDataRepository
            , Executor executor) {
        this.taskDataRepository = taskDataRepository;
        this.projectDataRepository = projectDataRepository;
        this.executor = executor;
    }
    
    /*
    * For Project
    */
    public LiveData<List<Project>> getAllProjects() {
        return this.projectDataRepository.getAllProjects();
    }
    
    /*
    * For Task
    */
    public LiveData<List<Task>> getAllTasks() {
        return this.taskDataRepository.getAllTasks();
    }
    
    public void insertTask(Task task) {
        executor.execute (() -> {
            this.taskDataRepository.insertTask(task);
        });
    }

    public void deleteTask(Task task) {
        executor.execute(() -> {
            taskDataRepository.deleteTask(task);
        });
    }
}
