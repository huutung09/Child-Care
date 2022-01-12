package com.nine.childcare;

import android.app.Application;

import com.nine.childcare.manager.DatabaseManager;

public class App extends Application {
    private static App instance;
    private DatabaseManager databaseManager;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        databaseManager = new DatabaseManager(this);
    }

    public static App getInstance() {
        return instance;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}
