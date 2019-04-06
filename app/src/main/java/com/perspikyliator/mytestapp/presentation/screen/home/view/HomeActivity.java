package com.perspikyliator.mytestapp.presentation.screen.home.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.perspikyliator.mytestapp.R;
import com.perspikyliator.mytestapp.app.TestApp;
import com.perspikyliator.mytestapp.domain.model.Movie;
import com.perspikyliator.mytestapp.presentation.screen.home.presenter.HomePresenter;
import com.perspikyliator.mytestapp.presentation.screen.home.view.adapter.MovieAdapter;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends Activity implements HomeView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_movies)
    RecyclerView mMoviesRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @Inject
    HomePresenter mPresenter;

    private LinearLayoutManager mLinearLayoutManager;
    private MovieAdapter mMovieAdapter;

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 0) {
                int visibleItemCount = mLinearLayoutManager.getChildCount();
                int totalItemCount = mLinearLayoutManager.getItemCount();
                int pastVisibleItems = mLinearLayoutManager.findFirstVisibleItemPosition();

                if (mProgressBar.getVisibility() != View.VISIBLE) {
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        mPresenter.getMovies();
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        TestApp.getDependencyGraph().initHomeComponent().inject(this);
        mPresenter.onAttach(this);

        initMovieList();
        mRefreshLayout.setOnRefreshListener(this);
        mPresenter.getMovies();
    }

    private void initMovieList() {
        mMovieAdapter = new MovieAdapter(movie -> {

        });
        mLinearLayoutManager = new LinearLayoutManager(this);
        mMoviesRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMoviesRecyclerView.addOnScrollListener(mOnScrollListener);
        mMoviesRecyclerView.setAdapter(mMovieAdapter);
    }

    @Override
    public void onRefresh() {
        mRefreshLayout.setRefreshing(true);
        mPresenter.refreshMovieList();
    }

    @Override
    public void showLoadingIndicator() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        mProgressBar.setVisibility(View.GONE);
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMovies(List<Movie> movies) {
        mMovieAdapter.setMovieList(movies);
    }

    @Override
    public void showMoreMovies(List<Movie> movies) {
        mMovieAdapter.addMovieList(movies);
    }

    @Override
    public void moviesLoadError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        TestApp.getDependencyGraph().releaseHomeComponent();
        super.onDestroy();
    }
}
