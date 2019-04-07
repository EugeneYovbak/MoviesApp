package com.perspikyliator.mytestapp.presentation.screen.movie.presenter;

import com.perspikyliator.mytestapp.domain.MovieRepository;
import com.perspikyliator.mytestapp.domain.model.Movie;
import com.perspikyliator.mytestapp.presentation.base.BasePresenter;
import com.perspikyliator.mytestapp.presentation.screen.movie.view.MovieView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MoviePresenter extends BasePresenter<MovieView> {

    private MovieRepository mMovieRepository;

    Movie mMovie;

    @Inject
    public MoviePresenter(MovieRepository movieRepository) {
        mMovieRepository = movieRepository;
    }

    public void getMovie(int movieId) {
        if (mMovie == null) {
            mView.showLoadingIndicator();
            mCompositeDisposable.add(
                    mMovieRepository.getMovie(movieId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doAfterTerminate(mView::hideLoadingIndicator)
                            .subscribe(
                                    this::handleMovieLoadSuccess,
                                    this::handleMovieLoadError
                            )
            );
        } else {
            mView.showMovie(mMovie);
        }
    }

    private void handleMovieLoadSuccess(Movie movie) {
        mMovie = movie;
        mView.showMovie(mMovie);
        observeMovieIsFavorite(mMovie.getId());
    }

    private void handleMovieLoadError(Throwable throwable) {
        mView.movieLoadError(throwable.getMessage());
    }

    private void observeMovieIsFavorite(int movieId) {
        mCompositeDisposable.add(
                mMovieRepository.observeMovie(movieId)
                        .subscribe(
                                this::handleMovieFavoriteChangeSuccess,
                                this::handleMovieFavoriteChangeError
                        )
        );
    }

    private void handleMovieFavoriteChangeSuccess(boolean isFavorite) {
        mMovie.setFavorite(isFavorite);
        mView.changeFavorite(isFavorite);
    }

    private void handleMovieFavoriteChangeError(Throwable throwable) {
        mView.movieLoadError(throwable.getMessage());
    }

    public void changeFavorite() {
        if (mMovie != null) {
            if (!mMovie.isFavorite()) {
                mMovieRepository.saveMovie(mMovie);
            } else {
                mMovieRepository.removeMovie(mMovie.getId());
            }
        }
    }
}
