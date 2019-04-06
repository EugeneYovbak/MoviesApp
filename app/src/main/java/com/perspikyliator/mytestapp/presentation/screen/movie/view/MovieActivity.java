package com.perspikyliator.mytestapp.presentation.screen.movie.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.perspikyliator.mytestapp.BuildConfig;
import com.perspikyliator.mytestapp.R;
import com.perspikyliator.mytestapp.app.TestApp;
import com.perspikyliator.mytestapp.domain.model.Movie;
import com.perspikyliator.mytestapp.presentation.screen.movie.presenter.MoviePresenter;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieActivity extends Activity implements MovieView {

    public static final String EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID";

    @BindView(R.id.iv_movie)
    ImageView mMovieImageView;
    @BindView(R.id.tv_movie_title)
    TextView mMovieTitleTextView;
    @BindView(R.id.tv_movie_rate)
    TextView mMovieRateTextView;
    @BindView(R.id.tv_movie_overview)
    TextView mMovieOverviewTextView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @Inject
    MoviePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
        TestApp.getDependencyGraph().initMovieComponent().inject(this);
        mPresenter.onAttach(this);

        if (getIntent().hasExtra(EXTRA_MOVIE_ID)) {
            mPresenter.getMovie(getIntent().getIntExtra(EXTRA_MOVIE_ID, 0));
        }
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
        TestApp.getDependencyGraph().releaseMovieComponent();
        super.onDestroy();
    }

    @Override
    public void showMovie(Movie movie) {
        mMovieImageView.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load(BuildConfig.IMAGE_URL + movie.getImage())
                .apply(new RequestOptions().centerCrop())
                .into(mMovieImageView);
        mMovieTitleTextView.setText(movie.getTitle());
        mMovieRateTextView.setText(movie.getRate());
        mMovieOverviewTextView.setText(movie.getOverview());
    }

    @Override
    public void movieLoadError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
