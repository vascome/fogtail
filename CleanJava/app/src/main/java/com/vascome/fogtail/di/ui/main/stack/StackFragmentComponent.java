package com.vascome.fogtail.di.ui.main.stack;

import android.support.annotation.NonNull;

import com.vascome.fogtail.di.FragmentScope;
import com.vascome.fogtail.di.ui.main.basemodule.CollectionFragmentModule;
import com.vascome.fogtail.ui.main.stack.StackAppFragment;

import dagger.Subcomponent;

/**
 * Created by vasilypopov on 12/8/17
 * Copyright (c) 2017 MVPJavaRx. All rights reserved.
 */

@Subcomponent(modules = { CollectionFragmentModule.class})
@FragmentScope
public interface StackFragmentComponent {
    void inject(@NonNull StackAppFragment itemsFragment);
}
