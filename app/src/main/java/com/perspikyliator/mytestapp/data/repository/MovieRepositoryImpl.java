package com.perspikyliator.mytestapp.data.repository;

import com.perspikyliator.mytestapp.app.Constants;
import com.perspikyliator.mytestapp.data.api.MovieApiService;
import com.perspikyliator.mytestapp.domain.MovieRepository;
import com.perspikyliator.mytestapp.domain.model.Movie;
import com.perspikyliator.mytestapp.domain.model.MovieMeta;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.realm.Realm;

public class MovieRepositoryImpl implements MovieRepository {

    private final MovieApiService mApiService;

    public MovieRepositoryImpl(MovieApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Single<MovieMeta> getMovies(int page) {
        return mApiService.getMovieList(page, Constants.API_KEY);
    }

    @Override
    public Single<Movie> getMovie(int movieId) {
        return mApiService.getMovie(movieId, Constants.API_KEY);
    }

    @Override
    public Flowable<Boolean> observeMovie(int movieId) {
        return Realm.getDefaultInstance()
                .where(Movie.class)
                .equalTo(Movie.PRIMARY_KEY, movieId)
                .findAllAsync()
                .asFlowable()
                .map(results -> !results.isEmpty());
    }

    @Override
    public Flowable<List<Movie>> getFavoriteMovies() {
        return Realm.getDefaultInstance()
                .where(Movie.class)
                .findAllAsync()
                .asFlowable()
                .map(ArrayList::new);

    }

    @Override
    public void saveMovie(Movie movie) {
        Realm.getDefaultInstance()
                .executeTransactionAsync(
                        realm1 -> realm1.insert(movie)
                );
    }

    @Override
    public void removeMovie(int movieId) {
        Realm.getDefaultInstance()
                .executeTransactionAsync(
                        realm1 -> realm1.where(Movie.class)
                                .equalTo(Movie.PRIMARY_KEY, movieId)
                                .findAll()
                                .deleteAllFromRealm()
                );
    }
}
