package com.perspikyliator.mytestapp.data.di;

import com.perspikyliator.mytestapp.data.api.MovieApiService;
import com.perspikyliator.mytestapp.data.repository.MovieRepositoryImpl;
import com.perspikyliator.mytestapp.domain.MovieRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class DataModule {

    @Provides
    @Singleton
    MovieRepository provideMovieRepository(MovieApiService movieApiService, Realm realm) {
        return new MovieRepositoryImpl(movieApiService, realm);
    }
}
