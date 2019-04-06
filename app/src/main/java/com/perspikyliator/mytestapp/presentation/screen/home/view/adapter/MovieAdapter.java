package com.perspikyliator.mytestapp.presentation.screen.home.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.perspikyliator.mytestapp.BuildConfig;
import com.perspikyliator.mytestapp.R;
import com.perspikyliator.mytestapp.domain.model.Movie;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> mMovieList = new ArrayList<>();
    private ItemInteractionListener mItemInteractionListener;

    public MovieAdapter(ItemInteractionListener mItemInteractionListener) {
        this.mItemInteractionListener = mItemInteractionListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(mMovieList.get(position));
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public void setMovieList(List<Movie> movieList) {
        mMovieList.clear();
        mMovieList.addAll(movieList);
        notifyDataSetChanged();
    }

    public void addMovieList(List<Movie> movieList) {
        mMovieList.addAll(movieList);
        notifyItemRangeChanged(mMovieList.size() - movieList.size(), movieList.size());
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_movie)
        ImageView mMovieImageView;
        @BindView(R.id.tv_title)
        TextView mMovieTitleTextView;
        @BindView(R.id.tv_rate)
        TextView mMovieRateTextView;
        @BindView(R.id.tv_overview)
        TextView mMovieOverviewTextView;

        MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Movie movie) {
            Glide.with(itemView.getContext())
                    .load(BuildConfig.IMAGE_URL + movie.getImage())
                    .apply(new RequestOptions().centerCrop())
                    .into(mMovieImageView);
            mMovieTitleTextView.setText(movie.getTitle());
            mMovieRateTextView.setText(movie.getRate());
            mMovieOverviewTextView.setText(movie.getOverview());
            itemView.setOnClickListener(view -> mItemInteractionListener.onMovieClick(movie));
        }
    }

    public interface ItemInteractionListener {
        void onMovieClick(Movie movie);
    }
}
