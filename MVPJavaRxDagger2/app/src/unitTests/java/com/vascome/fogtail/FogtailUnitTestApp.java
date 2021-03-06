package com.vascome.fogtail;


import android.app.Application;
import android.support.annotation.NonNull;

import com.vascome.fogtail.di.DaggerAppComponent;
import com.vascome.fogtail.di.appmodules.DeveloperSettingsModule;
import com.vascome.fogtail.di.appmodules.ModelsModule;
import com.vascome.fogtail.models.AnalyticsModel;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

import static org.mockito.Mockito.mock;

public class FogtailUnitTestApp extends FogtailApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        appComponent = DaggerAppComponent.builder()
                .application(this)
                .modelsModule(new ModelsModule() {
                    @NonNull
                    @Override
                    public AnalyticsModel provideAnalyticsModel(Application application) {
                        return mock(AnalyticsModel.class);
                    }
                })
                .devSettingsModule(new DeveloperSettingsModule())
                .build();
        return appComponent;
    }
}
