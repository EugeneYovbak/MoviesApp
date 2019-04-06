package com.perspikyliator.mytestapp.presentation.screen.movie.di;

import com.perspikyliator.mytestapp.presentation.screen.movie.view.MovieActivity;

import dagger.Subcomponent;

@Subcomponent(modules = {MovieModule.class})
@MovieScope
public interface MovieComponent {
    void inject(MovieActivity movieActivity);
}
