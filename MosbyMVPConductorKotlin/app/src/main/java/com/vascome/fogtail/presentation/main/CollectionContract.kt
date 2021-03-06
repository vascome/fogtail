package com.vascome.fogtail.presentation.main

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.vascome.fogtail.presentation.main.domain.model.RecAreaItem

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

interface CollectionContract {

    interface View: MvpView {

        fun setLoadingIndicator(active: Boolean)
        fun showItems(items: List<RecAreaItem>)
        fun showError()
    }

    interface Presenter: MvpPresenter<View> {

        val isInProgress: Boolean
        fun reloadItems()
    }
}