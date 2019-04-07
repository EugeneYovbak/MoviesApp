package com.perspikyliator.mytestapp.data.di;

import com.perspikyliator.mytestapp.data.api.MovieApiService;
import com.perspikyliator.mytestapp.data.repository.MovieRepositoryImpl;
import com.perspikyliator.mytestapp.domain.MovieRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    @Provides
    @Singleton
    MovieRepository provideMovieRepository(MovieApiService movieApiService) {
        return new MovieRepositoryImpl(movieApiService);
    }
}
