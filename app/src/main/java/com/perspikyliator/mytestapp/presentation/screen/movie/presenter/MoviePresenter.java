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

    private Movie mMovie;

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
    }

    private void handleMovieLoadError(Throwable throwable) {
        mView.movieLoadError(throwable.getMessage());
    }
}
