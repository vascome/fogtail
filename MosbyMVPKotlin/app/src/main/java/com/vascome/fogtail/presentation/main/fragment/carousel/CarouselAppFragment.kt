package com.vascome.fogtail.presentation.main.fragment.carousel

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.vascome.fogtail.R
import com.vascome.fogtail.data.network.AppImageLoader
import com.vascome.fogtail.presentation.base.fragments.BaseFragment
import com.vascome.fogtail.presentation.main.CollectionContract
import com.vascome.fogtail.presentation.main.CollectionPresenter
import com.vascome.fogtail.presentation.main.domain.model.RecAreaItem
import com.vascome.fogtail.presentation.main.fragment.gallery.adapter.GalleryAreaAdapter
import com.vascome.fogtail.presentation.main.fragment.table.decorator.BoxSpaceItemDecoration
import com.vascome.fogtail.presentation.main.utils.CollectionAreaItemListener
import kotlinx.android.synthetic.main.recycler_refreshable_view_fragment.*

import javax.inject.Inject


@Suppress("MemberVisibilityCanPrivate")
/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class CarouselAppFragment :
        BaseFragment<CollectionContract.View, CollectionContract.Presenter>(),
        CollectionContract.View,
        CollectionAreaItemListener {

    @Inject
    lateinit var collectionPresenter: CollectionPresenter

    @Inject
    lateinit var appContext: Context

    @Inject
    lateinit var imageLoader: AppImageLoader

    private val galleryAreaAdapter by lazy {
        GalleryAreaAdapter(activity.layoutInflater, imageLoader, this)
    }

    override fun createPresenter(): CollectionContract.Presenter = collectionPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.recycler_refreshable_view_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        presenter.reloadItems()
    }

    override fun setLoadingIndicator(active: Boolean) {
        recyclerView_swipe_refresh.post { recyclerView_swipe_refresh.isRefreshing = active  }
    }

    override fun showItems(items: List<RecAreaItem>) {
        items_loading_error_ui.visibility = View.GONE
        recyclerView_swipe_refresh.visibility = View.VISIBLE
        galleryAreaAdapter.setData(items)
    }

    override fun showError() {

        recyclerView_swipe_refresh.visibility = View.GONE
        items_loading_error_ui.visibility = View.VISIBLE

    }

    private fun initRecyclerView() {

        recyclerView.addItemDecoration(BoxSpaceItemDecoration(resources.getDimension(R.dimen.list_item_vertical_space_between_items).toInt()))
        val llm = CarouselLayoutManager(appContext, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = llm
        recyclerView.adapter = galleryAreaAdapter
        recyclerView_swipe_refresh.setOnRefreshListener { presenter.reloadItems() }
    }

    override fun onDestroyView() {
        presenter.dispose()
        super.onDestroyView()
    }

    override fun onItemClick(clickedItem: RecAreaItem) {
        presenter.openItemDetail(clickedItem)
    }
}
