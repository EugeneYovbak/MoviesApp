package com.perspikyliator.mytestapp.presentation.screen.home.view;

import com.perspikyliator.mytestapp.domain.model.Movie;
import com.perspikyliator.mytestapp.presentation.base.BaseView;

import java.util.List;

public interface HomeView extends BaseView {
    void showMovies(List<Movie> movies);

    void showMoreMovies(List<Movie> movies);

    void moviesLoadError(String message);
}
