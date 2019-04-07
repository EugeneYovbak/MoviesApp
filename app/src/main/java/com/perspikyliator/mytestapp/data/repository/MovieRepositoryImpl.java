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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

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
        return mApiService.getMovie(movieId, Constants.API_KEY)
                .compose(upstream -> upstream.onErrorResumeNext(throwable -> {
                    //TODO It's 00:11 AM... I'm done with how to do it in correct way...
                    return Single.just("")
                            .subscribeOn(AndroidSchedulers.mainThread())
                            .map(it -> {
                                RealmResults<Movie> results = Realm.getDefaultInstance()
                                        .where(Movie.class)
                                        .equalTo(Movie.PRIMARY_KEY, movieId)
                                        .findAllAsync();
                                return results.first();
                            })
                            .subscribeOn(Schedulers.io());
                }));
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
                        realm -> realm.insert(movie)
                );
    }

    @Override
    public void removeMovie(int movieId) {
        Realm.getDefaultInstance()
                .executeTransactionAsync(
                        realm -> realm.where(Movie.class)
                                .equalTo(Movie.PRIMARY_KEY, movieId)
                                .findAll()
                                .deleteAllFromRealm()
                );
    }
}
