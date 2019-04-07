package com.perspikyliator.mytestapp.presentation.screen.movie.presenter;

import com.perspikyliator.mytestapp.domain.MovieRepository;
import com.perspikyliator.mytestapp.domain.model.Movie;
import com.perspikyliator.mytestapp.presentation.base.BasePresenter;
import com.perspikyliator.mytestapp.presentation.screen.movie.view.MovieView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class MoviePresenter extends BasePresenter<MovieView> {

    private MovieRepository mMovieRepository;
    private Realm mRealm;

    private Movie mMovie;

    @Inject
    public MoviePresenter(MovieRepository movieRepository, Realm realm) {
        mMovieRepository = movieRepository;
        mRealm = realm;
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
        observeMovie(mMovie.getId());
    }

    private void handleMovieLoadError(Throwable throwable) {
        mView.movieLoadError(throwable.getMessage());
    }

    private void observeMovie(int movieId) {
        mCompositeDisposable.add(
                mMovieRepository.observeMovie(mRealm, movieId)
                        .subscribe(
                                results -> handleMovieFavoriteChangeSuccess(!results.isEmpty()),
                                this::handleMovieFavoriteChangeError)
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
        if (!mMovie.isFavorite()) {
            mMovieRepository.saveMovie(mRealm, mMovie);
        } else {
            mMovieRepository.removeMovie(mRealm, mMovie.getId());
        }
    }

    @Override
    public void onDetach() {
        mRealm.close();
        super.onDetach();
    }
}
