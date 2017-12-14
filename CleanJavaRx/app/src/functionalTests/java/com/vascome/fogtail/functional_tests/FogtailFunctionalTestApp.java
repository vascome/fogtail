package com.vascome.fogtail.functional_tests;

import android.app.Application;
import android.support.annotation.NonNull;

import com.vascome.fogtail.di.DaggerAppComponent;
import com.vascome.fogtail.FogtailApplication;
import com.vascome.fogtail.data.api.ApiConfiguration;
import com.vascome.fogtail.di.appmodules.ApiModule;
import com.vascome.fogtail.di.appmodules.AnalyticsModule;
import com.vascome.fogtail.utils.AnalyticsModel;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import timber.log.Timber;

public class FogtailFunctionalTestApp extends FogtailApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        appComponent = DaggerAppComponent.builder()
                .application(this)
                .apiModule(new ApiModule() {
                    @NonNull
                    @Override
                    public ApiConfiguration provideConfiguration() {
                        return () -> "https://test/";
                    }
                }).analyticsModule(new AnalyticsModule() {
                    @NonNull
                    @Override
                    public AnalyticsModel provideAnalyticsModel(@NonNull Application app) {
                        // We don't need real analytics in Functional tests, but let's just log it instead!
                        return new AnalyticsModel() {

                            // We'll check that application initializes Analytics before working with it!
                            private volatile boolean isInitialized;

                            @Override
                            public void init() {
                                isInitialized = true;
                                Timber.d("Analytics: initialized.");
                            }

                            @Override
                            public void sendEvent(@NonNull String eventName) {
                                throwIfNotInitialized();
                                Timber.d("Analytics: send event %s", eventName);
                            }

                            @Override
                            public void sendError(@NonNull String message, @NonNull Throwable error) {
                                throwIfNotInitialized();
                                Timber.e(error, message);
                            }

                            private void throwIfNotInitialized() {
                                if (!isInitialized) {
                                    throw new AssertionError("Analytics was not initialized!");
                                }
                            }
                        };
                    }
                }).build();
        return appComponent;
    }
}
