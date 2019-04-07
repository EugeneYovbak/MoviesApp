package com.perspikyliator.mytestapp.presentation.screen.favorites.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.perspikyliator.mytestapp.R;
import com.perspikyliator.mytestapp.app.TestApp;
import com.perspikyliator.mytestapp.domain.model.Movie;
import com.perspikyliator.mytestapp.presentation.screen.favorites.presenter.FavoritesPresenter;
import com.perspikyliator.mytestapp.presentation.screen.home.view.adapter.MovieAdapter;
import com.perspikyliator.mytestapp.presentation.screen.movie.view.MovieActivity;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FavoritesActivity extends Activity implements FavoritesView {

    @BindView(R.id.rv_movies)
    RecyclerView mMoviesRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @Inject
    FavoritesPresenter mPresenter;

    private MovieAdapter mMovieAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);
        TestApp.getDependencyGraph().initFavoritesComponent().inject(this);
        mPresenter.onAttach(this);

        initMovieList();
        mPresenter.getFavoriteMovies();
    }

    private void initMovieList() {
        mMovieAdapter = new MovieAdapter(this::openMovieDetailScreen);
        mMoviesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMoviesRecyclerView.setAdapter(mMovieAdapter);
    }

    private void openMovieDetailScreen(Movie movie) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra(MovieActivity.EXTRA_MOVIE_ID, movie.getId());
        startActivity(intent);
    }

    @OnClick(R.id.iv_back)
    public void onBackButtonPressed() {
        onBackPressed();
    }

    @Override
    public void showLoadingIndicator() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        TestApp.getDependencyGraph().releaseFavoritesComponent();
        super.onDestroy();
    }

    @Override
    public void favoriteMoviesLoadSuccess(List<Movie> movies) {
        mMovieAdapter.setMovieList(movies);
    }

    @Override
    public void favoriteMoviesLoadError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
