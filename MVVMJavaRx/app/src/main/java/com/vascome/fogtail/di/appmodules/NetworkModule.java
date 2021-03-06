package com.vascome.fogtail.di.appmodules;

import android.app.Application;
import android.support.annotation.NonNull;

import com.vascome.fogtail.data.network.OkHttpInterceptors;
import com.vascome.fogtail.data.network.OkHttpNetworkInterceptors;

import java.io.File;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

@Module
public class NetworkModule {

    private static final long DISK_CACHE_SIZE = 50*1024*1024; //50Mb

    @Provides
    @NonNull
    @Singleton
    public OkHttpClient provideOkHttpClient(@NonNull Application app,
                                            @OkHttpInterceptors @NonNull List<Interceptor> interceptors,
                                            @OkHttpNetworkInterceptors @NonNull List<Interceptor> networkInterceptors) {
        final OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        for (Interceptor interceptor : interceptors) {
            okHttpBuilder.addInterceptor(interceptor);
        }

        for (Interceptor networkInterceptor : networkInterceptors) {
            okHttpBuilder.addNetworkInterceptor(networkInterceptor);
        }

        File cacheDir = new File(app.getCacheDir(), "http");
        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);

        okHttpBuilder.cache(cache);
        return okHttpBuilder.build();
    }

}