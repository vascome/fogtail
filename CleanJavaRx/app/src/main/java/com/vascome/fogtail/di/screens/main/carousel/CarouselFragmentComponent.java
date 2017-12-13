package com.vascome.fogtail.di.screens.main.carousel;

import android.support.annotation.NonNull;

import com.vascome.fogtail.di.FragmentScope;
import com.vascome.fogtail.di.screens.main.basemodule.CollectionFragmentModule;
import com.vascome.fogtail.screens.main.fragment.carousel.CarouselAppFragment;

import dagger.Subcomponent;

/**
 * Created by vasilypopov on 12/8/17
 * Copyright (c) 2017 MVPJavaRx. All rights reserved.
 */

@Subcomponent(modules = { CollectionFragmentModule.class})
@FragmentScope
public interface CarouselFragmentComponent {
    void inject(@NonNull CarouselAppFragment itemsFragment);
}