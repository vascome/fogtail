package com.vascome.fogtail.di.ui.detail;

import android.support.annotation.NonNull;

import com.vascome.fogtail.di.FragmentScope;
import com.vascome.fogtail.ui.detail.RecAreaDetailFragment;

import dagger.Component;

/**
 * Created by vasilypopov on 12/8/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

@Component(dependencies = CollectionDetailComponent.class)
@FragmentScope
public interface DetailFragmentComponent {

    void inject(@NonNull RecAreaDetailFragment fragment);
}
