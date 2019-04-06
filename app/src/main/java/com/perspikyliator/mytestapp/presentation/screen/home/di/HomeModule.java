package com.perspikyliator.mytestapp.presentation.screen.home.di;

import com.perspikyliator.mytestapp.domain.MovieRepository;
import com.perspikyliator.mytestapp.presentation.screen.home.presenter.HomePresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

    @Provides
    @HomeScope
    HomePresenter provideHomePresenter(MovieRepository movieRepository) {
        return new HomePresenter(movieRepository);
    }
}
