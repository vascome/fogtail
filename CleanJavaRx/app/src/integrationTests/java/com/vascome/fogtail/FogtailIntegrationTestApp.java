package com.vascome.fogtail;

import android.app.Application;
import android.support.annotation.NonNull;

import com.vascome.fogtail.data.api.ApiConfiguration;
import com.vascome.fogtail.di.DaggerAppComponent;
import com.vascome.fogtail.di.appmodules.AnalyticsModule;
import com.vascome.fogtail.di.appmodules.ApiModule;
import com.vascome.fogtail.di.appmodules.DeveloperSettingsModule;
import com.vascome.fogtail.utils.AnalyticsModel;

import static org.mockito.Mockito.mock;

public class FogtailIntegrationTestApp extends FogtailApplication {

    @NonNull
    @Override
    protected DaggerAppComponent.Builder prepareApplicationComponent() {
        return super.prepareApplicationComponent()
                .apiModule(new ApiModule() {
                    @NonNull
                    @Override
                    public ApiConfiguration provideConfiguration() {
                        return () -> "/";
                    }
                })
                .analyticsModule(new AnalyticsModule() {
                    @NonNull
                    @Override
                    public AnalyticsModel provideAnalyticsModel(@NonNull Application app) {
                        return mock(AnalyticsModel.class); // We don't need real analytics in integration tests.
                    }
                })
                .developerSettingsModule(new DeveloperSettingsModule());
    }
}
