package com.perspikyliator.mytestapp.presentation.screen.home.presenter;

import com.perspikyliator.mytestapp.domain.MovieRepository;
import com.perspikyliator.mytestapp.domain.model.Movie;
import com.perspikyliator.mytestapp.domain.model.MovieMeta;
import com.perspikyliator.mytestapp.presentation.screen.home.view.HomeView;

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

import java.util.ArrayList;
import java.util.List;

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
public class HomePresenterTest {

    @Mock
    private MovieRepository mMovieRepository;

    @Mock
    private HomeView mHomeView;

    @InjectMocks
    private HomePresenter mHomePresenter;

    @Rule
    public MockitoRule mMockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        RxJavaPlugins.setIoSchedulerHandler(it -> Schedulers.trampoline());
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(it -> Schedulers.trampoline());
        mHomePresenter.onAttach(mHomeView);
    }

    @Test
    public void getMoviesFirstPageWhenSuccess_returnList() {
        MovieMeta movieMeta = generateMovieMeta(1);
        when(mMovieRepository.getMovies(anyInt())).thenReturn(Single.just(movieMeta));

        mHomePresenter.getMovies();

        Mockito.verify(mHomeView, times(1)).showLoadingIndicator();
        Mockito.verify(mHomeView, times(1)).hideLoadingIndicator();
        Mockito.verify(mHomeView, times(1)).showMovies(movieMeta.getMovies());
        Mockito.verify(mHomeView, never()).showMoreMovies(any());
        Mockito.verify(mHomeView, never()).moviesLoadError(anyString());
    }

    @Test
    public void getMoviesNotFirstPageWhenSuccess_returnList() {
        MovieMeta movieMeta = generateMovieMeta(8);
        when(mMovieRepository.getMovies(anyInt())).thenReturn(Single.just(movieMeta));

        mHomePresenter.getMovies();

        Mockito.verify(mHomeView, times(1)).showLoadingIndicator();
        Mockito.verify(mHomeView, times(1)).hideLoadingIndicator();
        Mockito.verify(mHomeView, times(1)).showMoreMovies(movieMeta.getMovies());
        Mockito.verify(mHomeView, never()).showMovies(any());
    }

    @Test
    public void getMoviesWhenError_returnError() {
        Exception exception = new Exception("Error");
        when(mMovieRepository.getMovies(anyInt())).thenReturn(Single.error(exception));

        mHomePresenter.getMovies();

        Mockito.verify(mHomeView, times(1)).showLoadingIndicator();
        Mockito.verify(mHomeView, times(1)).hideLoadingIndicator();
        Mockito.verify(mHomeView, times(1)).moviesLoadError("Error");
        Mockito.verify(mHomeView, never()).showMoreMovies(any());
        Mockito.verify(mHomeView, never()).showMovies(any());
    }

    @After
    public void cleanUp() {
        mHomePresenter.onDetach();
    }

    private MovieMeta generateMovieMeta(int page) {
        MovieMeta movieMeta = new MovieMeta();
        movieMeta.setPage(page);
        movieMeta.setMovies(generateMovieList());
        return movieMeta;
    }

    private List<Movie> generateMovieList() {
        List<Movie> movieList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            movieList.add(new Movie());
        }
        return movieList;
    }
}
