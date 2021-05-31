package com.dang.marty.weather.presentation.presenter

import com.dang.marty.weather.data.repository.LocationRepository
import com.dang.marty.weather.data.repository.WeatherRepository
import com.dang.marty.weather.presentation.model.DataSourceModel
import com.dang.marty.weather.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject


interface DailyWeatherView {
    fun displayWeatherValues(location: String, temperature: String, humidity: String, precipitation: String, windSpeed: String)
}

interface DailyWeatherPresenter {
    fun setView(view: DailyWeatherView)
    fun viewInitialized()
    fun processScrollInput(progress: Int)
    fun onViewDestroyed()
}

class DailyWeatherPresenterImpl @Inject constructor(
    private val weatherRepo: WeatherRepository,
    private val locationRepo: LocationRepository,
    private val scheduler: SchedulerProvider
): DailyWeatherPresenter {

    private lateinit var view: DailyWeatherView

    private var compositeDisposable = CompositeDisposable()
    private lateinit var dataSource: DataSourceModel
    private var location = ""

    override fun setView(view: DailyWeatherView) {
        this.view = view
    }

    override fun viewInitialized() {

        // get latitude and longitude from location repo
            getCoordinatesFromLocationRepo().let { locationMap->
            val latitude = locationMap["latitude"] ?: 35.0
            val longitude = locationMap["longitude"] ?: 35.0
            location = locationRepo.convertCoordinatesToLocation(latitude, longitude)
            getWeather(latitude, longitude, locationRepo.convertCoordinatesToLocation(latitude, longitude))
        }
    }

    override fun processScrollInput(progress: Int) {
        // doesn't start at 0, starts at 1
        val tickValue = progress - 1

            view.displayWeatherValues(
                location =  location,
                temperature = dataSource.currentTempList[tickValue],
                humidity = "Humidity: ${dataSource.humidityList[tickValue]}%",
                precipitation = "Precipitation: ${dataSource.precipitationList[tickValue]}%",
                windSpeed = "Wind: ${dataSource.windList[tickValue]} mph",
            )
    }

    override fun onViewDestroyed() {
        compositeDisposable.dispose()
    }

    private fun getCoordinatesFromLocationRepo(): Map<String, Double> {
        return locationRepo.getCurrentLocationCoordinates()
    }

    private fun getWeather(latitude: Double, longitude: Double, location: String) {

            val disposable = weatherRepo.getCurrentWeather(latitude, longitude)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe( { data ->
                    dataSource = data
                    view.displayWeatherValues(
                        location = location,
                        temperature = dataSource.currentTempList[0],
                        humidity = "Humidity: ${dataSource.humidityList[0]}%",
                        precipitation = "Precipitation: ${dataSource.precipitationList[0]}%",
                        windSpeed = "Wind: ${dataSource.windList[0]} mph",
                    )
                },{ throwable->
                    Timber.d("ERROR %s", throwable)
                })


        compositeDisposable.add(disposable)


//        dateObservable.postValue(dataSource.dateList[0])
//        determineWeatherIcon(dataSource.weatherDescriptionList[0])

    }
}