package com.vascome.fogtail;

import com.vascome.fogtail.developer_settings.DeveloperSettingsModel;
import com.vascome.fogtail.di.AppComponent;
import com.vascome.fogtail.di.DaggerAppComponent;
import com.vascome.fogtail.di.appmodules.ApiModule;
import com.vascome.fogtail.models.AnalyticsModel;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import timber.log.Timber;

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 fogtail. All rights reserved.
 */

public class FogtailApplication extends DaggerApplication {

    public AppComponent appComponent;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        appComponent = DaggerAppComponent.builder().application(this).build();
        return appComponent;
    }

    @Inject
    AnalyticsModel analyticsModel;

    @Inject
    DeveloperSettingsModel developerSettingModel;

    @Override
    public void onCreate() {
        super.onCreate();

        analyticsModel.init();

        if (BuildConfig.DEBUG) {

            Timber.plant(new Timber.DebugTree());
            developerSettingModel.apply();
        }
    }

    public AppComponent appComponent() {
        return appComponent;
    }
}