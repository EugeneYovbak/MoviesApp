package com.perspikyliator.mytestapp.app.di;

import android.content.Context;

import com.perspikyliator.mytestapp.presentation.screen.home.di.HomeComponent;

public class DependencyGraph {

    private AppComponent mAppComponent;

    private HomeComponent mHomeComponent;

    public DependencyGraph(Context context) {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(context))
                .build();
    }

    public HomeComponent initHomeComponent() {
        mHomeComponent = mAppComponent.plusHomeComponent();
        return mHomeComponent;
    }

    public void releaseHomeComponent() {
        mHomeComponent = null;
    }
}
