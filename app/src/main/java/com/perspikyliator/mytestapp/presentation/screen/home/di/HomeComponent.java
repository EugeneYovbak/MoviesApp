package com.perspikyliator.mytestapp.presentation.screen.home.di;

import com.perspikyliator.mytestapp.presentation.screen.home.view.HomeActivity;

import dagger.Subcomponent;

@Subcomponent(modules = {HomeModule.class})
@HomeScope
public interface HomeComponent {
    void inject(HomeActivity homeActivity);
}
