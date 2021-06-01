package com.dang.marty.weather.presentation.presenter

import com.dang.marty.weather.presentation.model.SettingsModel
import timber.log.Timber

interface SettingsView {
    fun initializeRecyclerView(updatedData: List<SettingsModel>)
}

interface SettingsPresenter {
    fun setView(view: SettingsView)
    fun viewInitialized()
}
class SettingsPresenterImpl: SettingsPresenter {

    private lateinit var view: SettingsView

    override fun setView(view: SettingsView) {
        this.view = view
    }

    override fun viewInitialized() {
        initialize()
    }

    private fun initialize() {

        val thing = mutableListOf<SettingsModel>()
        val model = SettingsModel(
            title= "Dark Mode",
            clickListener = { title ->
                handleClick(title)
            }
        )
        thing.add(model)

        view.initializeRecyclerView(thing)
    }

    private fun handleClick(title: String) {
        Timber.d("YOO $title")
    }


}