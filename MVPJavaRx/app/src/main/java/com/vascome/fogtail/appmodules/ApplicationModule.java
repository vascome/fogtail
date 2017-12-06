package com.vascome.fogtail.appmodules;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.vascome.fogtail.models.AppImageLoader;
import com.vascome.fogtail.models.PicassoImageLoader;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 fogtail. All rights reserved.
 */
@Module
public class ApplicationModule {
    public static final String MAIN_THREAD_HANDLER = "main_thread_handler";

    @NonNull
    private final Application application;

    public ApplicationModule(@NonNull Application application) {
        this.application = application;
    }

    @Provides
    @NonNull @Singleton
    public Application provideFogtailApp() {
        return application;
    }


    @Provides @NonNull @Singleton
    public TypeAdapterFactory provideTypeAdapterFactory() {
        return EntityTypeAdapterFactory.create();
    }

    @Provides @NonNull @Singleton
    public Gson provideGson(TypeAdapterFactory typeAdapterFactory) {
        return new GsonBuilder()
                .registerTypeAdapterFactory(typeAdapterFactory)
                .create();
    }

    @Provides @NonNull @Named(MAIN_THREAD_HANDLER) @Singleton
    public Handler provideMainThreadHandler() {
        return new Handler(Looper.getMainLooper());
    }

    @Provides @NonNull @Singleton
    public Picasso providePicasso(@NonNull Application application, @NonNull OkHttpClient okHttpClient) {
        return new Picasso.Builder(application)
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();
    }

    @Provides @NonNull @Singleton
    public AppImageLoader provideImageLoader(@NonNull Picasso picasso) {
        return new PicassoImageLoader(picasso);
    }

}
