package com.perspikyliator.mytestapp.presentation.screen.favorites.di;

import com.perspikyliator.mytestapp.domain.MovieRepository;
import com.perspikyliator.mytestapp.presentation.screen.favorites.presenter.FavoritesPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class FavoritesModule {

    @Provides
    @FavoritesScope
    FavoritesPresenter provideFavoritesPresenter(MovieRepository movieRepository) {
        return new FavoritesPresenter(movieRepository);
    }
}
