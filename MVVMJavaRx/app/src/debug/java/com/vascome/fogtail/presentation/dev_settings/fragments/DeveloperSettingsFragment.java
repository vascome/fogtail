package com.vascome.fogtail.presentation.dev_settings.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.AnyThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.github.pedrovgs.lynx.LynxActivity;
import com.github.pedrovgs.lynx.LynxConfig;
import com.vascome.fogtail.R;
import com.vascome.fogtail.databinding.FragmentDeveloperSettingsBinding;
import com.vascome.fogtail.presentation.base.fragments.BaseFragment;
import com.vascome.fogtail.presentation.dev_settings.adapters.DeveloperSettingsSpinnerAdapter;
import com.vascome.fogtail.presentation.dev_settings.presenters.DeveloperSettingsPresenter;
import com.vascome.fogtail.presentation.dev_settings.views.DeveloperSettingsView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public class DeveloperSettingsFragment extends BaseFragment implements DeveloperSettingsView {

    @Inject
    DeveloperSettingsPresenter viewModel;

    @Inject
    LynxConfig lynxConfig;

    private FragmentDeveloperSettingsBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_developer_settings, container, false);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.developerSettingsHttpLoggingLevelSpinner
                .setAdapter(new DeveloperSettingsSpinnerAdapter<>(getActivity().getLayoutInflater())
                .setSelectionOptions(HttpLoggingLevel.allValues()));

        binding.developerSettingsStethoSwitch
                .setOnCheckedChangeListener((compoundButton, checked) -> viewModel.changeStethoState(checked));

        binding.developerSettingsTinyDancerSwitch
                .setOnCheckedChangeListener((compoundButton, checked) -> viewModel.changeTinyDancerState(checked));



        binding.developerSettingsHttpLoggingLevelSpinner
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                        viewModel.changeHttpLoggingLevel(
                                ((HttpLoggingLevel) binding.developerSettingsHttpLoggingLevelSpinner.getItemAtPosition(position)).loggingLevel);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

        binding.developerSettingsLeakCanarySwitch
                .setOnCheckedChangeListener((compoundButton, checked) -> viewModel.changeLeakCanaryState(checked));


        binding.bShowLog.setOnClickListener(view1 -> {
            Context context = getActivity();
            context.startActivity(LynxActivity.getIntent(context, lynxConfig));
        });
        viewModel.bindView(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.syncDeveloperSettings();
    }


    @Override
    @AnyThread
    public void changeGitSha(@NonNull String gitSha) {
        runIfFragmentAlive(() -> binding.developerSettingsGitShaTextView.setText(gitSha));
    }

    @Override
    @AnyThread
    public void changeBuildDate(@NonNull String date) {
        runIfFragmentAlive(() -> binding.developerSettingsBuildDateTextView.setText(date));
    }

    @Override
    @AnyThread
    public void changeBuildVersionCode(@NonNull String versionCode) {
        runIfFragmentAlive(() -> binding.developerSettingsBuildVersionCodeTextView.setText(versionCode));
    }

    @Override
    @AnyThread
    public void changeBuildVersionName(@NonNull String versionName) {
        runIfFragmentAlive(() -> binding.developerSettingsBuildVersionNameTextView.setText(versionName));
    }

    @Override
    @AnyThread
    public void changeStethoState(boolean enabled) {
        runIfFragmentAlive(() -> binding.developerSettingsStethoSwitch.setChecked(enabled));
    }

    @Override
    @AnyThread
    public void changeLeakCanaryState(boolean enabled) {
        runIfFragmentAlive(() -> binding.developerSettingsLeakCanarySwitch.setChecked(enabled));
    }

    @Override
    @AnyThread
    public void changeTinyDancerState(boolean enabled) {
        runIfFragmentAlive(() -> binding.developerSettingsTinyDancerSwitch.setChecked(enabled));
    }

    @Override
    @AnyThread
    public void changeHttpLoggingLevel(@NonNull HttpLoggingInterceptor.Level loggingLevel) {
        runIfFragmentAlive(() -> {
            for (int position = 0, count = binding.developerSettingsHttpLoggingLevelSpinner.getCount(); position < count; position++) {
                if (loggingLevel == ((HttpLoggingLevel) binding.developerSettingsHttpLoggingLevelSpinner.getItemAtPosition(position)).loggingLevel) {
                    binding.developerSettingsHttpLoggingLevelSpinner.setSelection(position);
                    return;
                }
            }

            throw new IllegalStateException("Unknown loggingLevel, looks like a serious bug. Passed loggingLevel = " + loggingLevel);
        });
    }

    @Override
    @AnyThread
    public void showMessage(@NonNull String message) {
        runIfFragmentAlive(() -> Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show());
    }

    @Override
    @AnyThread
    public void showAppNeedsToBeRestarted() {
        runIfFragmentAlive(() -> Toast.makeText(getContext(), "To apply new settings app needs to be restarted", Toast.LENGTH_LONG).show());
    }

    @Override
    public void onDestroyView() {
        viewModel.unbindView(this);
        super.onDestroyView();
    }

    private static class HttpLoggingLevel implements DeveloperSettingsSpinnerAdapter.SelectionOption {

        @NonNull
        final HttpLoggingInterceptor.Level loggingLevel;

        HttpLoggingLevel(@NonNull HttpLoggingInterceptor.Level loggingLevel) {
            this.loggingLevel = loggingLevel;
        }

        @NonNull
        @Override
        public String title() {
            return loggingLevel.toString();
        }

        @NonNull
        static List<HttpLoggingLevel> allValues() {
            final HttpLoggingInterceptor.Level[] loggingLevels = HttpLoggingInterceptor.Level.values();
            final List<HttpLoggingLevel> values = new ArrayList<>(loggingLevels.length);
            for (HttpLoggingInterceptor.Level loggingLevel : loggingLevels) {
                values.add(new HttpLoggingLevel(loggingLevel));
            }
            return values;
        }
    }
}
