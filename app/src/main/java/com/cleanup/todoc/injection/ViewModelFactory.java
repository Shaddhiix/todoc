package com.cleanup.todoc.injection;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.repository.ProjectDataRepository;
import com.cleanup.todoc.repository.TaskDataRepository;
import com.cleanup.todoc.viewmodel.TaskViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ViewModelFactory implements ViewModelProvider.Factory {
    
    private TaskDataRepository taskDataRepository;
    private ProjectDataRepository projectDataRepository;
    private Executor executor;
    private static ViewModelFactory factory;
    private static ViewModelFactory getInstance(Context context) {
        if (factory == null) {

            synchronized (ViewModelFactory.class) {

                if (factory == null) {

                    factory = new ViewModelFactory(context);
                }
            }
        }
        return factory;
    }
    private ViewModelFactory(Context context) {
        TodocDatabase database = TodocDatabase.getInstance ( context );
        this.taskDataRepository = new TaskDataRepository (database.taskDAO());
        this.projectDataRepository = new ProjectDataRepository(database.projectDAO());
        this.executor = Executors.newSingleThreadExecutor();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom ( TaskViewModel.class )){
        return (T) new TaskViewModel ( taskDataRepository, projectDataRepository,executor );
        }
        throw new IllegalArgumentException ("Unknown ViewModel class");
    }
}
