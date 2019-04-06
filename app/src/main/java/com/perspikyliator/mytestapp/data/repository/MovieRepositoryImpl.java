package com.perspikyliator.mytestapp.data.repository;

import com.perspikyliator.mytestapp.data.api.MovieApiService;
import com.perspikyliator.mytestapp.domain.MovieRepository;

import io.realm.Realm;

public class MovieRepositoryImpl implements MovieRepository {

    private final MovieApiService mApiService;
    private final Realm mRealm;

    public MovieRepositoryImpl(MovieApiService apiService, Realm realm) {
        mApiService = apiService;
        mRealm = realm;
    }
}
