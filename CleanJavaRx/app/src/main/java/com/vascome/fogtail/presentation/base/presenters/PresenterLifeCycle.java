package com.vascome.fogtail.presentation.base.presenters;

/**
 * Created by vasilypopov on 12/13/17
 * Copyright (c) 2017 CleanJavaRx. All rights reserved.
 */

/**
 * Interface representing a Presenter in a model view presenter (MVP) pattern.
 */
public interface PresenterLifeCycle {
    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onResume() method.
     */
    void resume();

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onPause() method.
     */
    void pause();

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onDestroy() method.
     */
    void destroy();
}
