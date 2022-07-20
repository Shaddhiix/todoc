package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDAO;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {

    private final TaskDAO taskDao;

    public TaskDataRepository(TaskDAO taskDAO) { this.taskDao = taskDAO; }
    
    public LiveData<List<Task>> getTask(long taskId) {
        return this.taskDao.getTasks ( taskId );
    }
    
    public void createTask(Task task) {
        taskDao.insertTask ( task );
    }
    
    public void deleteTask(long taskId) {
        taskDao.deleteTask ( taskId );
    }
}
