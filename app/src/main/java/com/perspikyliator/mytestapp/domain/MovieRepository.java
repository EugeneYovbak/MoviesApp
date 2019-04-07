package com.perspikyliator.mytestapp.domain;

import com.perspikyliator.mytestapp.domain.model.Movie;
import com.perspikyliator.mytestapp.domain.model.MovieMeta;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface MovieRepository {
    Single<MovieMeta> getMovies(int page);

    Single<Movie> getMovie(int movieId);

    Flowable<Boolean> observeMovie(int movieId);

    void saveMovie(Movie movie);

    void removeMovie(int movieId);
}
