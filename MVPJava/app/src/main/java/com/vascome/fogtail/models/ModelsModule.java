package com.vascome.fogtail.models;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.vascome.fogtail.api.FogtailRestApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */
@Module
public class ModelsModule {

    @Provides
    @NonNull
    @Singleton
    public AnalyticsModel provideAnalyticsModel(@NonNull Application app) {
        return new GoogleFirebaseAppAnalytics(app);
    }

    @Provides @NonNull
    public RecAreaItemsModel provideItemsModel(@NonNull FogtailRestApi fogtailRestApi) {
        return new RecAreaItemsModel(fogtailRestApi);
    }

    static class GoogleFirebaseAppAnalytics implements AnalyticsModel {

        @NonNull
        private final Application app;
        private FirebaseAnalytics mFirebaseAnalytics;

        GoogleFirebaseAppAnalytics(@NonNull Application app) {
            this.app = app;
        }

        @Override
        public void init() {
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this.app);
        }

        @Override
        public void sendEvent(@NonNull String eventName) {
            Bundle bundle = new Bundle();
            bundle.putString("name", eventName);
            mFirebaseAnalytics.logEvent("event", bundle);
        }

        @Override
        public void sendError(@NonNull String message, @NonNull Throwable error) {
            Bundle bundle = new Bundle();
            bundle.putString("name", message);
            bundle.putSerializable("error", error);
            mFirebaseAnalytics.logEvent("error_event", bundle);
        }

    }

}
