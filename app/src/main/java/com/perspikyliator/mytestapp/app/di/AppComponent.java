package com.perspikyliator.mytestapp.app.di;

import com.perspikyliator.mytestapp.data.di.ApiModule;
import com.perspikyliator.mytestapp.data.di.DataModule;
import com.perspikyliator.mytestapp.data.di.StorageModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, ApiModule.class, DataModule.class, StorageModule.class})
@Singleton
public interface AppComponent {
}
