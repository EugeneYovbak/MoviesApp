package com.perspikyliator.mytestapp.app.di;

import android.content.Context;

import com.perspikyliator.mytestapp.presentation.screen.home.di.HomeComponent;
import com.perspikyliator.mytestapp.presentation.screen.movie.di.MovieComponent;

public class DependencyGraph {

    private AppComponent mAppComponent;

    private HomeComponent mHomeComponent;
    private MovieComponent mMovieComponent;

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

    public MovieComponent initMovieComponent() {
        mMovieComponent = mAppComponent.plusMovieComponent();
        return mMovieComponent;
    }

    public void releaseMovieComponent() {
        mMovieComponent = null;
    }
}
