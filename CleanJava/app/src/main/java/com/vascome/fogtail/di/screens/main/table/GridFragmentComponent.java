package com.vascome.fogtail.di.screens.main.table;

import android.support.annotation.NonNull;

import com.vascome.fogtail.di.FragmentScope;
import com.vascome.fogtail.di.screens.main.basemodule.CollectionFragmentModule;
import com.vascome.fogtail.screens.main.fragment.table.GridAppFragment;

import dagger.Subcomponent;

/**
 * Created by vasilypopov on 12/8/17
 * Copyright (c) 2017 MVPJavaRx. All rights reserved.
 */

@Subcomponent(modules = { CollectionFragmentModule.class})
@FragmentScope
public interface GridFragmentComponent {

    void inject(@NonNull GridAppFragment itemsFragment);
}