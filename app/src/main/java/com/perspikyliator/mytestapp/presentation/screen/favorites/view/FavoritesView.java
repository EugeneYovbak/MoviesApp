package com.perspikyliator.mytestapp.presentation.screen.favorites.view;

import com.perspikyliator.mytestapp.domain.model.Movie;
import com.perspikyliator.mytestapp.presentation.base.BaseView;

import java.util.List;

public interface FavoritesView extends BaseView {
    void showFavoriteMovies(List<Movie> movies);

    void showEmptyFavorites();

    void favoriteMoviesLoadError(String message);
}
