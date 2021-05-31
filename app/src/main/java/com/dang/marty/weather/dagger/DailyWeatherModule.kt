package com.dang.marty.weather.dagger

import com.dang.marty.weather.presentation.presenter.DailyWeatherPresenter
import com.dang.marty.weather.presentation.presenter.DailyWeatherPresenterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent


@InstallIn(ActivityRetainedComponent::class)
@Module
abstract class DailyWeatherModule {

    @Binds
    abstract fun bindDailyWeatherPresenter(impl: DailyWeatherPresenterImpl): DailyWeatherPresenter
}