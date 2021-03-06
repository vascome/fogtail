package com.vascome.fogtail;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.vascome.fogtail.developer_settings.DeveloperSettingsModel;
import com.vascome.fogtail.di.AppComponent;
import com.vascome.fogtail.di.DaggerAppComponent;
import com.vascome.fogtail.di.appmodules.ApplicationModule;
import com.vascome.fogtail.models.AnalyticsModel;

import timber.log.Timber;

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 fogtail. All rights reserved.
 */

public class FogtailApplication extends Application {
    private AppComponent appComponent;

    // Prevent need in a singleton (global) reference to the application object.
    @NonNull
    public static FogtailApplication get(@NonNull Context context) {
        return (FogtailApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = prepareApplicationComponent().build();

        AnalyticsModel analyticsModel = appComponent.analyticsModel();

        analyticsModel.init();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());

            DeveloperSettingsModel developerSettingModel = appComponent.developerSettingModel();
            developerSettingModel.apply();
        }
    }

    @NonNull
    protected DaggerAppComponent.Builder prepareApplicationComponent() {
        return DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this));
    }

    @NonNull
    public AppComponent appComponent() {
        return appComponent;
    }
}