package com.vascome.fogtail;

import android.app.Application;
import android.support.annotation.NonNull;

import com.vascome.fogtail.developer_settings.DeveloperSettingsModule;
import com.vascome.fogtail.models.AnalyticsModel;
import com.vascome.fogtail.models.ModelsModule;

import static org.mockito.Mockito.mock;

public class FogtailUnitTestApp extends FogtailApplication {

    @NonNull
    @Override
    protected DaggerApplicationComponent.Builder prepareApplicationComponent() {
        return super.prepareApplicationComponent()
                .modelsModule(new ModelsModule() {
                    @NonNull
                    @Override
                    public AnalyticsModel provideAnalyticsModel(@NonNull Application app) {
                        return mock(AnalyticsModel.class); // We don't need real analytics in Unit tests.
                    }
                })
                .developerSettingsModule(new DeveloperSettingsModule());
    }
}
