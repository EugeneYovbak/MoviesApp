package com.perspikyliator.mytestapp.app;

import android.app.Application;

import com.perspikyliator.mytestapp.app.di.DependencyGraph;

public class TestApp extends Application {

    private static DependencyGraph mDependencyGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        mDependencyGraph = new DependencyGraph(this);
    }

    public static DependencyGraph getDependencyGraph() {
        return mDependencyGraph;
    }
}
