package com.dang.marty.weather.dagger

import com.dang.marty.weather.presentation.presenter.DailyWeatherPresenter
import com.dang.marty.weather.presentation.presenter.DailyWeatherPresenterImpl
import com.marty.dang.polarpointsweatherapp.data.model.WeatherModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import org.mockito.Mockito

@TestInstallIn(components = [SingletonComponent::class], replaces = [DailyWeatherModule::class])
@Module
object MockDailyWeatherModule {

    @Provides
    fun provideDailyWeatherPresenter(impl: DailyWeatherPresenterImpl): DailyWeatherPresenter {
        return Mockito.mock(DailyWeatherPresenter::class.java)
    }

}