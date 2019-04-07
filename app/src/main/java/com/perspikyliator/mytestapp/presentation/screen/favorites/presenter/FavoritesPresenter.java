package com.perspikyliator.mytestapp.presentation.screen.favorites.presenter;

import com.perspikyliator.mytestapp.domain.MovieRepository;
import com.perspikyliator.mytestapp.domain.model.Movie;
import com.perspikyliator.mytestapp.presentation.base.BasePresenter;
import com.perspikyliator.mytestapp.presentation.screen.favorites.view.FavoritesView;

import java.util.List;

import javax.inject.Inject;

public class FavoritesPresenter extends BasePresenter<FavoritesView> {

    private MovieRepository mMovieRepository;

    @Inject
    public FavoritesPresenter(MovieRepository movieRepository) {
        mMovieRepository = movieRepository;
    }

    public void getFavoriteMovies() {
        mCompositeDisposable.add(
                mMovieRepository.getFavoriteMovies()
                        .subscribe(
                                this::handleFavoritesLoadSuccess,
                                this::handleFavoritesLoadError
                        )
        );
    }

    private void handleFavoritesLoadSuccess(List<Movie> movieList) {
        mView.favoriteMoviesLoadSuccess(movieList);
    }

    private void handleFavoritesLoadError(Throwable throwable) {
        mView.favoriteMoviesLoadError(throwable.getMessage());
    }
}
