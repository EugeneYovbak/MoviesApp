package com.perspikyliator.mytestapp.presentation.screen.movie.di;

import com.perspikyliator.mytestapp.domain.MovieRepository;
import com.perspikyliator.mytestapp.presentation.screen.movie.presenter.MoviePresenter;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class MovieModule {

    @Provides
    @MovieScope
    MoviePresenter provideMoviePresenter(MovieRepository movieRepository, Realm realm) {
        return new MoviePresenter(movieRepository, realm);
    }

    @Provides
    @MovieScope
    Realm provideRealm() {
        return Realm.getDefaultInstance();
    }
}
