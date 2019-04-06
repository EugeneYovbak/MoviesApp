package com.perspikyliator.mytestapp.presentation.screen.home.presenter;

import com.perspikyliator.mytestapp.domain.MovieRepository;
import com.perspikyliator.mytestapp.presentation.base.BasePresenter;
import com.perspikyliator.mytestapp.presentation.screen.home.view.HomeView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter extends BasePresenter<HomeView> {

    private MovieRepository mMovieRepository;

    private int mCurrentPage = 0;
    private int mTotalPages = 1;

    @Inject
    public HomePresenter(MovieRepository movieRepository) {
        mMovieRepository = movieRepository;
    }

    public void getMovies() {
        if (mCurrentPage < mTotalPages) {
            mView.showLoadingIndicator();
            mCompositeDisposable.add(
                    mMovieRepository.getMovies(mCurrentPage + 1)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doAfterTerminate(mView::hideLoadingIndicator)
                            .subscribe(movieMeta -> {
                                        mCurrentPage = movieMeta.getPage();
                                        mTotalPages = movieMeta.getPages();
                                        mView.moviesLoadSuccess(movieMeta.getMovies());
                                    },
                                    throwable -> mView.moviesLoadError(throwable.getMessage())
                            )
            );
        }
    }
}