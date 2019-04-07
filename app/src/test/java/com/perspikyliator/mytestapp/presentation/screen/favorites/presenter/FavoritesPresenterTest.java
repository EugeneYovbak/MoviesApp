package com.perspikyliator.mytestapp.presentation.screen.favorites.presenter;

import com.perspikyliator.mytestapp.domain.MovieRepository;
import com.perspikyliator.mytestapp.domain.model.Movie;
import com.perspikyliator.mytestapp.presentation.screen.favorites.view.FavoritesView;

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

import io.reactivex.Flowable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class FavoritesPresenterTest {

    @Mock
    private MovieRepository mMovieRepository;

    @Mock
    private FavoritesView mFavoritesView;

    @InjectMocks
    private FavoritesPresenter mFavoritesPresenter;

    @Rule
    public MockitoRule mMockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        RxJavaPlugins.setIoSchedulerHandler(it -> Schedulers.trampoline());
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(it -> Schedulers.trampoline());
        mFavoritesPresenter.onAttach(mFavoritesView);
    }

    @Test
    public void getFavoriteMoviesWhenNotEmptyList_returnList() {
        List<Movie> movieList = generateMovieList();
        when(mMovieRepository.getFavoriteMovies()).thenReturn(Flowable.just(movieList));

        mFavoritesPresenter.getFavoriteMovies();

        Mockito.verify(mFavoritesView, times(1)).showFavoriteMovies(movieList);
        Mockito.verify(mFavoritesView, never()).showEmptyFavorites();
        Mockito.verify(mFavoritesView, never()).favoriteMoviesLoadError(anyString());
    }

    @Test
    public void getFavoriteMoviesWhenEmptyList_returnEmptyList() {
        List<Movie> movieList = new ArrayList<>();
        when(mMovieRepository.getFavoriteMovies()).thenReturn(Flowable.just(movieList));

        mFavoritesPresenter.getFavoriteMovies();

        Mockito.verify(mFavoritesView, times(1)).showEmptyFavorites();
        Mockito.verify(mFavoritesView, never()).showFavoriteMovies(any());
        Mockito.verify(mFavoritesView, never()).favoriteMoviesLoadError(anyString());
    }

    @Test
    public void getFavoriteMoviesWhenError_returnError() {
        when(mMovieRepository.getFavoriteMovies()).thenReturn(Flowable.error(new Exception("error")));

        mFavoritesPresenter.getFavoriteMovies();

        Mockito.verify(mFavoritesView, times(1)).favoriteMoviesLoadError(anyString());
        Mockito.verify(mFavoritesView, never()).showFavoriteMovies(any());
        Mockito.verify(mFavoritesView, never()).showEmptyFavorites();
    }

    @After
    public void cleanUp() {
        mFavoritesPresenter.onDetach();
    }

    private List<Movie> generateMovieList() {
        List<Movie> movieList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            movieList.add(new Movie());
        }
        return movieList;
    }
}
