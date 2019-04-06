package com.perspikyliator.mytestapp.presentation.screen.movie.view;

import com.perspikyliator.mytestapp.domain.model.Movie;
import com.perspikyliator.mytestapp.presentation.base.BaseView;

public interface MovieView extends BaseView {
    void showMovie(Movie movie);

    void movieLoadError(String message);
}
