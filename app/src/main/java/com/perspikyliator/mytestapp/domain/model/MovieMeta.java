package com.perspikyliator.mytestapp.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieMeta {

    @SerializedName("page")
    private int page;
    @SerializedName("total_pages")
    private int pages;
    @SerializedName("results")
    private List<Movie> movies;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
