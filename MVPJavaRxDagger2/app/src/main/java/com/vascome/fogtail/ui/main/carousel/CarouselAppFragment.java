package com.vascome.fogtail.ui.main.carousel;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vascome.fogtail.R;
import com.vascome.fogtail.api.entities.RecAreaItem;
import com.vascome.fogtail.databinding.RecyclerRefreshableViewFragmentBinding;
import com.vascome.fogtail.models.AppImageLoader;
import com.vascome.fogtail.ui.base.fragments.BaseFragment;
import com.vascome.fogtail.ui.detail.RecAreaItemDetailActivity;
import com.vascome.fogtail.ui.main.collectionbase.CollectionAreaItemListener;
import com.vascome.fogtail.ui.main.collectionbase.CollectionPresenter;
import com.vascome.fogtail.ui.main.collectionbase.ICollectionView;
import com.vascome.fogtail.ui.main.gallery.adapter.GalleryAreaAdapter;
import com.vascome.fogtail.ui.main.table.decorator.BoxSpaceItemDecoration;

import java.util.List;

import javax.inject.Inject;



/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public class CarouselAppFragment extends BaseFragment implements ICollectionView, CollectionAreaItemListener {

    RecyclerRefreshableViewFragmentBinding binding;
    GalleryAreaAdapter galleryAreaAdapter;

    @Inject
    Context context;

    @Inject
    CollectionPresenter presenter;

    @Inject
    AppImageLoader imageLoader;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.recycler_refreshable_view_fragment, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.reloadData();
    }

    @Override
    public void setLoadingIndicator(boolean active) {

        if (getView() == null) {
            return;
        }
        binding.recyclerViewSwipeRefresh.post(() -> binding.recyclerViewSwipeRefresh.setRefreshing(active));
    }

    @Override
    public void showItems(List<RecAreaItem> items) {
        runOnUiThreadIfFragmentAlive(() -> {
            setLoadingIndicator(false);
            binding.itemsLoadingErrorUi.setVisibility(View.GONE);
            binding.recyclerViewSwipeRefresh.setVisibility(View.VISIBLE);
            if (galleryAreaAdapter != null) {
                galleryAreaAdapter.setData(items);
            }
        });
    }

    @Override
    public void showError() {

        runOnUiThreadIfFragmentAlive(() -> {
            setLoadingIndicator(false);
            binding.recyclerViewSwipeRefresh.setVisibility(View.GONE);
            binding.itemsLoadingErrorUi.setVisibility(View.VISIBLE);
        });

    }

    private void initRecyclerView() {

        binding.recyclerView.addItemDecoration(new BoxSpaceItemDecoration((int) getResources().getDimension(R.dimen.list_item_vertical_space_between_items)));
        CarouselLayoutManager llm = new CarouselLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerView.setLayoutManager(llm);
        galleryAreaAdapter = new GalleryAreaAdapter(getActivity().getLayoutInflater(), imageLoader, this);
        binding.recyclerView.setAdapter(galleryAreaAdapter);
        binding.recyclerViewSwipeRefresh.setOnRefreshListener(()->presenter.reloadData());

        presenter.bindView(this);
    }

    @Override
    public void onDestroyView() {
        presenter.unbindView(this);
        binding.unbind();
        super.onDestroyView();
    }

    @Override
    public void onItemClick(RecAreaItem clickedItem) {
        Intent intent = new Intent(context, RecAreaItemDetailActivity.class);
        intent.putExtra("item", clickedItem);
        startActivity(intent);
    }
}
