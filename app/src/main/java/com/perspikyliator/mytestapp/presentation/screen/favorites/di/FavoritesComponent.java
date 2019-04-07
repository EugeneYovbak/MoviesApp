package com.perspikyliator.mytestapp.presentation.screen.favorites.di;

import com.perspikyliator.mytestapp.presentation.screen.favorites.view.FavoritesActivity;

import dagger.Subcomponent;

@Subcomponent(modules = {FavoritesModule.class})
@FavoritesScope
public interface FavoritesComponent {
    void inject(FavoritesActivity favoritesActivity);
}
