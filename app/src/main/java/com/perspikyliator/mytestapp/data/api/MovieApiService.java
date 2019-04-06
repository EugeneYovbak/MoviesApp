package com.perspikyliator.mytestapp.data.api;


import com.perspikyliator.mytestapp.domain.model.MovieMeta;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApiService {

    @GET("movie/popular")
    Single<MovieMeta> getMovieList(@Query("api_key") String apiKey, @Query("page") int page);
}
