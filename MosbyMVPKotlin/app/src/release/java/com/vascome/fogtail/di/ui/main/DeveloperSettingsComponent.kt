package com.vascome.fogtail.di.presentation.main

import dagger.Subcomponent

/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

@Subcomponent
interface DeveloperSettingsComponent {
    // Hides details of developer settings in the release build type.

    @Subcomponent.Builder
    interface Builder {
        fun build(): DeveloperSettingsComponent
    }
}
