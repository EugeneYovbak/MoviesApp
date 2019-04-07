package com.perspikyliator.mytestapp.data.repository;

import com.perspikyliator.mytestapp.app.Constants;
import com.perspikyliator.mytestapp.data.api.MovieApiService;
import com.perspikyliator.mytestapp.domain.MovieRepository;
import com.perspikyliator.mytestapp.domain.model.Movie;
import com.perspikyliator.mytestapp.domain.model.MovieMeta;

import io.reactivex.Flowable;
import io.reactivex.Single;
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
        return mApiService.getMovie(movieId, Constants.API_KEY);
    }

    @Override
    public Flowable<RealmResults<Movie>> observeMovie(Realm realm, int movieId) {
        return realm.where(Movie.class).equalTo(Movie.PRIMARY_KEY, movieId).findAllAsync().asFlowable();
    }

    @Override
    public void saveMovie(Realm realm, Movie movie) {
        realm.executeTransactionAsync(realm1 -> realm1.insert(movie));
    }

    @Override
    public void removeMovie(Realm realm, int movieId) {
        realm.executeTransactionAsync(realm1 -> realm1.where(Movie.class).equalTo(Movie.PRIMARY_KEY, movieId).findAll().deleteAllFromRealm());
    }
}
