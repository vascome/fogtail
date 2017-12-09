package com.vascome.fogtail;

import android.support.annotation.NonNull;

import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.lang.reflect.Method;

// Custom runner allows us set config in one place instead of setting it in each test class.
public class FogtailRobolectricUnitTestRunner extends RobolectricTestRunner {

    // This value should be changed as soon as Robolectric will support newer api.
    private static final int SDK_EMULATE_LEVEL = 23;

    public FogtailRobolectricUnitTestRunner(@NonNull Class<?> classRun) throws Exception {
        super(classRun);
    }
/*
    @Override
    public Config getConfig(@NonNull Method method) {
        final Config defaultConfig = super.getConfig(method);
        return new Config.Implementation(
                new int[]{SDK_EMULATE_LEVEL},
                defaultConfig.manifest(),
                defaultConfig.qualifiers(),
                defaultConfig.packageName(),
                defaultConfig.abiSplit(),
                defaultConfig.resourceDir(),
                defaultConfig.assetDir(),
                defaultConfig.buildDir(),
                defaultConfig.shadows(),
                defaultConfig.instrumentedPackages(),
                FogtailUnitTestApp.class, // Notice that we override real application class for Unit tests.
                defaultConfig.libraries(),
                defaultConfig.constants() == Void.class ? BuildConfig.class : defaultConfig.constants()
        );
    }
*/
    @NonNull
    public static FogtailApplication fogtailApplication() {
        return (FogtailApplication) RuntimeEnvironment.application;
    }
}
