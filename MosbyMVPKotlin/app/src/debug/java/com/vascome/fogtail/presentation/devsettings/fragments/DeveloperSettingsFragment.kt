package com.vascome.fogtail.presentation.devsettings.fragments

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.AnyThread
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast

import com.github.pedrovgs.lynx.LynxActivity
import com.github.pedrovgs.lynx.LynxConfig
import com.vascome.fogtail.R
import com.vascome.fogtail.databinding.FragmentDeveloperSettingsBinding
import com.vascome.fogtail.presentation.base.fragments.BaseFragment
import com.vascome.fogtail.presentation.devsettings.adapters.DeveloperSettingsSpinnerAdapter
import com.vascome.fogtail.presentation.devsettings.presenters.DeveloperSettingsPresenter
import com.vascome.fogtail.presentation.devsettings.views.DeveloperSettingsContract

import java.util.ArrayList

import javax.inject.Inject

import okhttp3.logging.HttpLoggingInterceptor

@Suppress("MemberVisibilityCanPrivate")
/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class DeveloperSettingsFragment :
        BaseFragment<DeveloperSettingsContract.View, DeveloperSettingsContract.Presenter>(),
        DeveloperSettingsContract.View {
    override fun createPresenter(): DeveloperSettingsContract.Presenter = devPresenter

    @Inject
    lateinit var devPresenter: DeveloperSettingsPresenter

    @Inject
    lateinit var lynxConfig: LynxConfig



    private lateinit var binding: FragmentDeveloperSettingsBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_developer_settings, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.developerSettingsHttpLoggingLevelSpinner.adapter = DeveloperSettingsSpinnerAdapter<DeveloperSettingsSpinnerAdapter.SelectionOption>(activity.layoutInflater)
                .setSelectionOptions(HttpLoggingLevel.allValues())

        binding.developerSettingsStethoSwitch
                .setOnCheckedChangeListener { _, checked -> presenter.changeStethoState(checked) }

        binding.developerSettingsTinyDancerSwitch
                .setOnCheckedChangeListener { _, checked -> presenter.changeTinyDancerState(checked) }

        binding.developerSettingsHttpLoggingLevelSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View?, position: Int, l: Long) {
                presenter.changeHttpLoggingLevel(
                        (binding.developerSettingsHttpLoggingLevelSpinner.getItemAtPosition(position) as HttpLoggingLevel).loggingLevel)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }
        binding.developerSettingsLeakCanarySwitch
                .setOnCheckedChangeListener { _, checked -> presenter.changeLeakCanaryState(checked) }


        binding.bShowLog.setOnClickListener {
            val context = activity
            context.startActivity(LynxActivity.getIntent(context, lynxConfig))
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.syncDeveloperSettings()
    }

    @AnyThread
    override fun changeGitSha(gitSha: String) {
        runOnUI(Runnable{ binding.developerSettingsGitShaTextView.text = gitSha })
    }

    @AnyThread
    override fun changeBuildDate(date: String) {
        runOnUI(Runnable{ binding.developerSettingsBuildDateTextView.text = date })
    }

    @AnyThread
    override fun changeBuildVersionCode(versionCode: String) {
        runOnUI(Runnable{ binding.developerSettingsBuildVersionCodeTextView.text = versionCode })

    }

    @AnyThread
    override fun changeBuildVersionName(versionName: String) {
        runOnUI(Runnable{ binding.developerSettingsBuildVersionNameTextView.text = versionName })
    }

    @AnyThread
    override fun changeStethoState(enabled: Boolean) {
        runOnUI(Runnable{ binding.developerSettingsStethoSwitch.isChecked = enabled })
    }

    @AnyThread
    override fun changeLeakCanaryState(enabled: Boolean) {
        runOnUI(Runnable{ binding.developerSettingsLeakCanarySwitch.isChecked = enabled })
    }

    @AnyThread
    override fun changeTinyDancerState(enabled: Boolean) {
        runOnUI(Runnable{ binding.developerSettingsTinyDancerSwitch.isChecked = enabled })

    }

    @AnyThread
    override fun changeHttpLoggingLevel(loggingLevel: HttpLoggingInterceptor.Level) {
        runOnUI(Runnable{
            var position = 0
            val count = binding.developerSettingsHttpLoggingLevelSpinner.count
            while (position < count) {
                if (loggingLevel == (binding.developerSettingsHttpLoggingLevelSpinner.getItemAtPosition(position) as? HttpLoggingLevel)?.loggingLevel) {
                    binding.developerSettingsHttpLoggingLevelSpinner.setSelection(position)
                    return@Runnable
                }
                position++
            }

            throw IllegalStateException("Unknown loggingLevel, looks like a serious bug. Passed loggingLevel = " + loggingLevel)
        })
    }

    @AnyThread
    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    @AnyThread
    override fun showAppNeedsToBeRestarted() {
        Toast.makeText(context, "To apply new settings app needs to be restarted", Toast.LENGTH_LONG).show()
    }

    private class HttpLoggingLevel internal constructor(internal val loggingLevel: HttpLoggingInterceptor.Level) : DeveloperSettingsSpinnerAdapter.SelectionOption {

        override fun title(): String {
            return loggingLevel.toString()
        }

        companion object {

            @JvmStatic fun allValues(): List<HttpLoggingLevel> {
                val loggingLevels = HttpLoggingInterceptor.Level.values()
                val values = ArrayList<HttpLoggingLevel>(loggingLevels.size)
                loggingLevels.mapTo(values) { HttpLoggingLevel(it) }
                return values
            }
        }
    }
}
