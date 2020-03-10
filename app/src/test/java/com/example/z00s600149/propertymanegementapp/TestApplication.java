package com.example.z00s600149.propertymanegementapp;

import android.app.Application;

public class TestApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        setTheme(R.style.AppTheme); //or just R.style.Theme_AppCompat
    }
}
