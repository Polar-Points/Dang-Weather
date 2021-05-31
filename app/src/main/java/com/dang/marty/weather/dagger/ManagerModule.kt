package com.dang.marty.weather.dagger

import android.content.Context
import android.location.Geocoder
import android.location.LocationManager
import com.dang.marty.weather.data.repository.LocationDataRepositoryImpl
import com.dang.marty.weather.data.repository.LocationRepository
import com.dang.marty.weather.data.repository.WeatherRepository
import com.dang.marty.weather.data.repository.WeatherRepositoryImpl
import com.dang.marty.weather.data.webservice.OpenWeatherApiService
import com.dang.marty.weather.utils.DefaultSchedulerProvider
import com.dang.marty.weather.utils.SchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@InstallIn(SingletonComponent::class)
@Module
object ManagerModule {

    @Provides
    fun provideWeatherRepository(webservice: OpenWeatherApiService): WeatherRepository {
        return WeatherRepositoryImpl(webservice)
    }

    @Provides
    fun provideLocationRepository(@ApplicationContext context: Context, geocoder: Geocoder, locationManager: LocationManager): LocationRepository {
        return LocationDataRepositoryImpl(context, geocoder, locationManager)
    }

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return DefaultSchedulerProvider()
    }
}