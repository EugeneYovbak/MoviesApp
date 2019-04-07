package com.perspikyliator.mytestapp.presentation.screen.movie.presenter;

import com.perspikyliator.mytestapp.domain.MovieRepository;
import com.perspikyliator.mytestapp.domain.model.Movie;
import com.perspikyliator.mytestapp.presentation.screen.movie.view.MovieView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class MoviePresenterTest {

    @Mock
    private MovieRepository mMovieRepository;

    @Mock
    private MovieView mMovieView;

    @InjectMocks
    private MoviePresenter mMoviePresenter;

    @Rule
    public MockitoRule mMockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        RxJavaPlugins.setIoSchedulerHandler(it -> Schedulers.trampoline());
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(it -> Schedulers.trampoline());
        mMoviePresenter.onAttach(mMovieView);
    }

    @Test
    public void getMovieWhenSuccess_returnMovie() {
        Movie movie = generateMovie();
        when(mMovieRepository.getMovie(anyInt())).thenReturn(Single.just(movie));
        when(mMovieRepository.observeMovie(anyInt())).thenReturn(Flowable.just(true));

        mMoviePresenter.getMovie(1);

        Mockito.verify(mMovieView, times(1)).showLoadingIndicator();
        Mockito.verify(mMovieView, times(1)).hideLoadingIndicator();
        Mockito.verify(mMovieView, times(1)).showMovie(movie);
        Mockito.verify(mMovieView, never()).movieLoadError(anyString());
    }

    @Test
    public void getMovieSeveralTimesWhenSuccess_returnMovie() {
        Movie movie = generateMovie();
        when(mMovieRepository.getMovie(anyInt())).thenReturn(Single.just(movie));
        when(mMovieRepository.observeMovie(anyInt())).thenReturn(Flowable.just(true));

        mMoviePresenter.getMovie(1);
        mMoviePresenter.getMovie(2);

        Mockito.verify(mMovieView, times(1)).showLoadingIndicator();
        Mockito.verify(mMovieView, times(1)).hideLoadingIndicator();
        Mockito.verify(mMovieView, times(2)).showMovie(movie);
        Mockito.verify(mMovieView, never()).movieLoadError(anyString());
    }

    @Test
    public void getMovieWhenError_returnError() {
        Exception exception = new Exception("error");
        when(mMovieRepository.getMovie(anyInt())).thenReturn(Single.error(exception));

        mMoviePresenter.getMovie(1);

        Mockito.verify(mMovieView, times(1)).showLoadingIndicator();
        Mockito.verify(mMovieView, times(1)).hideLoadingIndicator();
        Mockito.verify(mMovieView, times(1)).movieLoadError("error");
        Mockito.verify(mMovieView, never()).showMovie(any());
    }

    @Test
    public void changeFavoriteWhenInactive_saveToFavorites() {
        Movie movie = new Movie();
        movie.setFavorite(false);
        mMoviePresenter.mMovie = movie;

        mMoviePresenter.changeFavorite();

        Mockito.verify(mMovieRepository, times(1)).saveMovie(movie);
        Mockito.verify(mMovieRepository, never()).removeMovie(anyInt());
    }

    @Test
    public void changeFavoriteWhenActive_removeFromFavorites() {
        Movie movie = new Movie();
        movie.setId(1);
        movie.setFavorite(true);
        mMoviePresenter.mMovie = movie;

        mMoviePresenter.changeFavorite();

        Mockito.verify(mMovieRepository, times(1)).removeMovie(movie.getId());
        Mockito.verify(mMovieRepository, never()).saveMovie(any());
    }

    @After
    public void cleanUp() {
        mMoviePresenter.onDetach();
    }

    private Movie generateMovie() {
        return new Movie();
    }
}
