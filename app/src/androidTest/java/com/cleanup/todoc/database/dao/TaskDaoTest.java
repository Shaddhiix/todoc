package com.cleanup.todoc.database.dao;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.LiveDataTestUtil;
import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private TodocDatabase database;
    private Project[] projects = Project.getAllProjects();
    private Task task1 = new Task(projects[0].getId(), "Task_Test 1", new Date().getTime());
    private Task task2 = new Task(projects[0].getId(), "Task_Test 2", new Date().getTime());
    private Task task3 = new Task(projects[0].getId(), "Task_Test 3", new Date().getTime());
    private Task task4 = new Task(projects[2].getId(), "Task_Test 4", new Date().getTime());
    private Task task5 = new Task(projects[2].getId(), "Task_Test 5", new Date().getTime());

    @Before
    public void initDatabase() throws InterruptedException {
        this.database = Room.inMemoryDatabaseBuilder( InstrumentationRegistry.getContext(),
                        TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
        this.database.projectDAO().insertProject(this.projects);

        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDAO().getAllTasks ());
        assertTrue(tasks.isEmpty());
    }

    @After
    public void closeDatabase() {
        this.database.close();
    }

    @Test
    public void insertAndGetTask() throws InterruptedException {
        List<Project> projects = LiveDataTestUtil.getValue(this.database.projectDAO().getAllProjects());

        this.database.taskDAO().insertTask(this.task1);
        this.database.taskDAO().insertTask(this.task2);

        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDAO().getAllTasks ());
        assertEquals(2, tasks.size());

        this.database.taskDAO().insertTask(this.task3);
        this.database.taskDAO().insertTask(this.task4);
        this.database.taskDAO().insertTask(this.task5);

        tasks = LiveDataTestUtil.getValue(this.database.taskDAO().getAllTasks ());

        assertEquals(5, tasks.size());
        assertEquals(projects.get(0).getId(), tasks.get(0).getProjectId());
        assertEquals(projects.get(0).getId(), tasks.get(1).getProjectId());
        assertEquals(projects.get(0).getId(), tasks.get(2).getProjectId());
        assertEquals(projects.get(2).getId(), tasks.get(3).getProjectId());
        assertEquals(projects.get(2).getId(), tasks.get(4).getProjectId());

        assertEquals(task1.getName(), tasks.get(0).getName());
        assertEquals(task3.getName(), tasks.get(2).getName());
        assertEquals(task5.getName(), tasks.get(4).getName());
        assertEquals(task1.getCreationTimestamp(), tasks.get(0).getCreationTimestamp());
        assertEquals(task3.getCreationTimestamp(), tasks.get(2).getCreationTimestamp());
        assertEquals(task5.getCreationTimestamp(), tasks.get(4).getCreationTimestamp());
    }

    @Test
    public void deleteAndGetTask() throws InterruptedException {
        List<Project> projects = LiveDataTestUtil.getValue(this.database.projectDAO().getAllProjects());

        this.database.taskDAO().insertTask(this.task1);
        this.database.taskDAO().insertTask(this.task2);
        this.database.taskDAO().insertTask(this.task3);
        this.database.taskDAO().insertTask(this.task4);
        this.database.taskDAO().insertTask(this.task5);

        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDAO().getAllTasks ());
        assertEquals(5, tasks.size());
        assertEquals(task2.getName(), tasks.get(1).getName());
        assertEquals(task2.getCreationTimestamp(), tasks.get(1).getCreationTimestamp());

        this.database.taskDAO().deleteTask(tasks.get(1));
        tasks = LiveDataTestUtil.getValue(this.database.taskDAO().getAllTasks ());
        assertEquals(4, tasks.size());
        assertEquals(task3.getName(), tasks.get(1).getName());
        assertEquals(task3.getCreationTimestamp(), tasks.get(1).getCreationTimestamp());
    }
    
}
