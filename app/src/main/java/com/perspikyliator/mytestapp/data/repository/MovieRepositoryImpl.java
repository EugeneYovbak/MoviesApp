package com.perspikyliator.mytestapp.data.repository;

import com.perspikyliator.mytestapp.app.Constants;
import com.perspikyliator.mytestapp.data.api.MovieApiService;
import com.perspikyliator.mytestapp.domain.MovieRepository;
import com.perspikyliator.mytestapp.domain.model.Movie;
import com.perspikyliator.mytestapp.domain.model.MovieMeta;

import io.reactivex.Single;
import io.realm.Realm;

public class MovieRepositoryImpl implements MovieRepository {

    private final MovieApiService mApiService;
    private final Realm mRealm;

    public MovieRepositoryImpl(MovieApiService apiService, Realm realm) {
        mApiService = apiService;
        mRealm = realm;
    }

    @Override
    public Single<MovieMeta> getMovies(int page) {
        return mApiService.getMovieList(page, Constants.API_KEY);
    }

    @Override
    public Single<Movie> getMovie(int movieId) {
        return mApiService.getMovie(movieId, Constants.API_KEY);
    }
}
