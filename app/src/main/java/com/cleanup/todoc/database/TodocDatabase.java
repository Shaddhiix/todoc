package com.cleanup.todoc.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.database.dao.ProjectDAO;
import com.cleanup.todoc.database.dao.TaskDAO;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.concurrent.Executors;

@Database ( entities = {Task.class, Project.class}, version = 1, exportSchema = false)

public  abstract class TodocDatabase extends RoomDatabase {
    
    // SINGLETON
    private static volatile TodocDatabase INSTANCE;
    
    // DAO
    public abstract TaskDAO taskDAO();
    public abstract ProjectDAO projectDAO();
    
    // INSTANCE
    public static TodocDatabase getInstance(Context context) {

        if (INSTANCE == null) {

            synchronized (TodocDatabase.class) {

                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    TodocDatabase.class, "MyDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    
    private static Callback prepopulateDatabase() {
        return new Callback () {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate ( db );
                Executors.newSingleThreadExecutor().execute(() -> 
                        INSTANCE.projectDAO ().createProject (new Project
                                (4, "Projet J.O", 0xFFEADAD2)));
            }
        };
    }
}
