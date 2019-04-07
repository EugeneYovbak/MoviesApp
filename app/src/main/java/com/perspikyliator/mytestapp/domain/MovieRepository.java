package com.perspikyliator.mytestapp.domain;

import com.perspikyliator.mytestapp.domain.model.Movie;
import com.perspikyliator.mytestapp.domain.model.MovieMeta;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.realm.Realm;
import io.realm.RealmResults;

public interface MovieRepository {
    Single<MovieMeta> getMovies(int page);

    Single<Movie> getMovie(int movieId);

    Flowable<RealmResults<Movie>> observeMovie(Realm realm, int movieId);

    void saveMovie(Realm realm, Movie movie);

    void removeMovie(Realm realm, int movieId);
}
