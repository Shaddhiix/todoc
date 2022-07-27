package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDAO;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {

    private final TaskDAO taskDao;

    public TaskDataRepository(TaskDAO taskDAO) { this.taskDao = taskDAO; }
    
    public LiveData<List<Task>> getTask() {
        return this.taskDao.getTasks();
    }
    
    public void createTask(Task task) {
        taskDao.insertTask(task);
    }
    
    public void deleteTask(Task task) {
        taskDao.deleteTask(task);
    }
}
