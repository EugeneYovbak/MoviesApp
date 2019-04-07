package com.perspikyliator.mytestapp.app.di;

import com.perspikyliator.mytestapp.data.di.ApiModule;
import com.perspikyliator.mytestapp.data.di.DataModule;
import com.perspikyliator.mytestapp.presentation.screen.favorites.di.FavoritesComponent;
import com.perspikyliator.mytestapp.presentation.screen.home.di.HomeComponent;
import com.perspikyliator.mytestapp.presentation.screen.movie.di.MovieComponent;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, ApiModule.class, DataModule.class})
@Singleton
public interface AppComponent {
    HomeComponent plusHomeComponent();

    MovieComponent plusMovieComponent();

    FavoritesComponent plusFavoritesComponent();
}
