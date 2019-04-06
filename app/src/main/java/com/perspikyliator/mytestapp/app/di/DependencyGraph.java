package com.perspikyliator.mytestapp.app.di;

import android.content.Context;

public class DependencyGraph {

    private AppComponent mAppComponent;

    public DependencyGraph(Context context) {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(context))
                .build();
    }
}
