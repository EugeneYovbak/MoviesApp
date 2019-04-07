package com.perspikyliator.mytestapp.app.di;

import android.content.Context;

import com.perspikyliator.mytestapp.presentation.screen.favorites.di.FavoritesComponent;
import com.perspikyliator.mytestapp.presentation.screen.home.di.HomeComponent;
import com.perspikyliator.mytestapp.presentation.screen.movie.di.MovieComponent;

public class DependencyGraph {

    private AppComponent mAppComponent;

    private HomeComponent mHomeComponent;
    private MovieComponent mMovieComponent;
    private FavoritesComponent mFavoritesComponent;

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

    public FavoritesComponent initFavoritesComponent() {
        mFavoritesComponent = mAppComponent.plusFavoritesComponent();
        return mFavoritesComponent;
    }

    public void releaseFavoritesComponent() {
        mFavoritesComponent = null;
    }
}
