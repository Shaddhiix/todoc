package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDAO;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {

    private final TaskDAO taskDao;

    public TaskDataRepository(TaskDAO taskDAO) { taskDao = taskDAO; }
    
    public LiveData<List<Task>> getAllTasks() {
        return taskDao.getAllTasks ();
    }
    
    public void insertTask(Task task) {
        taskDao.insertTask(task);
    }
    
    public void deleteTask(Task task) {
        taskDao.deleteTask(task);
    }
}
