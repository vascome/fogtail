package com.vascome.fogtail.ui.main.stack;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vascome.fogtail.FogtailApplication;
import com.vascome.fogtail.R;
import com.vascome.fogtail.api.entities.RecAreaItem;
import com.vascome.fogtail.databinding.StackViewFragmentBinding;
import com.vascome.fogtail.di.ui.main.CollectionComponent;
import com.vascome.fogtail.di.ui.main.stack.DaggerStackFragmentComponent;
import com.vascome.fogtail.models.AppImageLoader;
import com.vascome.fogtail.ui.base.fragments.BaseFragment;
import com.vascome.fogtail.ui.detail.RecAreaItemDetailActivity;
import com.vascome.fogtail.ui.main.collectionbase.CollectionAreaItemListener;
import com.vascome.fogtail.ui.main.collectionbase.CollectionPresenter;
import com.vascome.fogtail.ui.main.collectionbase.ICollectionView;
import com.vascome.fogtail.ui.main.stack.adapter.SwipeStackAdapter;

import java.util.List;

import javax.inject.Inject;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public class StackAppFragment extends BaseFragment implements ICollectionView, CollectionAreaItemListener {

    SwipeStackAdapter swipeStackAdapter;
    StackViewFragmentBinding binding;

    @Inject
    Context context;

    @Inject
    CollectionPresenter presenter;

    @Inject
    AppImageLoader imageLoader;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CollectionComponent collectionComponent = FogtailApplication
                .get(getActivity().getApplicationContext())
                .collectionComponent();
        DaggerStackFragmentComponent.builder()
                .collectionComponent(collectionComponent).build()
                .inject(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.stack_view_fragment, container, false);
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

        runOnUiThreadIfFragmentAlive(() -> binding.itemsLoadingUi.setVisibility(active?VISIBLE:GONE));
    }

    @Override
    public void showItems(List<RecAreaItem> items) {
        runOnUiThreadIfFragmentAlive(() -> {
            setLoadingIndicator(false);
            binding.itemsLoadingErrorUi.setVisibility(GONE);
            binding.swipeStack.setVisibility(VISIBLE);

            if (swipeStackAdapter != null) {
                swipeStackAdapter.setData(items);
            }
        });
    }

    @Override
    public void showError() {

        runOnUiThreadIfFragmentAlive(() -> {
            setLoadingIndicator(false);
            binding.itemsLoadingErrorUi.setVisibility(VISIBLE);
            binding.swipeStack.setVisibility(GONE);
        });

    }

    private void initRecyclerView() {

        swipeStackAdapter = new SwipeStackAdapter(getActivity().getLayoutInflater(), imageLoader, this);
        binding.swipeStack.setAdapter(swipeStackAdapter);

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
