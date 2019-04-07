package com.perspikyliator.mytestapp.domain.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Movie extends RealmObject {

    public static final String PRIMARY_KEY = "id";

    @PrimaryKey
    @SerializedName(PRIMARY_KEY)
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("poster_path")
    private String image;
    @SerializedName("overview")
    private String overview;
    @SerializedName("vote_average")
    private String rate;
    @Ignore
    private boolean favorite;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
