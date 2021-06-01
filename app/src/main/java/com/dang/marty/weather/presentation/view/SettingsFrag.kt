package com.dang.marty.weather.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dang.marty.weather.R
import com.dang.marty.weather.databinding.FragmentSettingsBinding
import com.dang.marty.weather.presentation.model.SettingsModel
import com.dang.marty.weather.presentation.presenter.SettingsPresenter
import com.dang.marty.weather.presentation.presenter.SettingsPresenterImpl
import com.dang.marty.weather.presentation.presenter.SettingsView

class SettingsFrag : Fragment(), SettingsView, SettingsAdapterCallbacks {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = SettingsPresenterImpl()
        presenter.setView(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.viewInitialized()
    }


    override fun initializeRecyclerView(updatedData: List<SettingsModel>) {
        binding.apply {
            var adapter = binding.settingsRecyclerView.adapter as SettingsAdapter?
            adapter?.apply {
                data = updatedData
                notifyDataSetChanged()
            } ?: run {
                adapter = SettingsAdapter(updatedData, this@SettingsFrag)
                binding.settingsRecyclerView.adapter = adapter
            }
        }
    }
}