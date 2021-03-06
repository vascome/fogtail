package com.vascome.fogtail.di;

import com.vascome.fogtail.di.presentation.detail.DetailActivityModule;
import com.vascome.fogtail.di.presentation.main.MainActivityModule;
import com.vascome.fogtail.presentation.detail.RecAreaItemDetailActivity;
import com.vascome.fogtail.presentation.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by vasilypopov on 12/13/17
 * Copyright (c) 2017 CleanJavaRx. All rights reserved.
 */

@Module
public abstract class ActivityBindingModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity mainActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = DetailActivityModule.class)
    abstract RecAreaItemDetailActivity recAreaItemDetailActivity();
}
