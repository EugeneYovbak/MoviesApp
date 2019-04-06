package com.perspikyliator.mytestapp.data.api;


import com.perspikyliator.mytestapp.domain.model.Movie;
import com.perspikyliator.mytestapp.domain.model.MovieMeta;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiService {

    @GET("movie/popular")
    Single<MovieMeta> getMovieList(@Query("page") int page, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}")
    Single<Movie> getMovie(@Path("movie_id") int movieId, @Query("api_key") String apiKey);
}
