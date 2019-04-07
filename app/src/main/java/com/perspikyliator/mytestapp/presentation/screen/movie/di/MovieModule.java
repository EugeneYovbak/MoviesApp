package com.perspikyliator.mytestapp.presentation.screen.movie.di;

import com.perspikyliator.mytestapp.domain.MovieRepository;
import com.perspikyliator.mytestapp.presentation.screen.movie.presenter.MoviePresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MovieModule {

    @Provides
    @MovieScope
    MoviePresenter provideMoviePresenter(MovieRepository movieRepository) {
        return new MoviePresenter(movieRepository);
    }
}
