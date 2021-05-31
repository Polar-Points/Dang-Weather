package com.dang.marty.weather

import com.dang.marty.weather.data.repository.LocationRepository
import com.dang.marty.weather.data.repository.WeatherRepository
import com.dang.marty.weather.presentation.model.DataSourceModel
import com.dang.marty.weather.presentation.presenter.DailyWeatherPresenterImpl
import com.dang.marty.weather.presentation.presenter.DailyWeatherView
import com.dang.marty.weather.utils.SchedulerProvider
import com.dang.marty.weather.utils.TrampolineSchedulerProvider
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.reactivex.Observable
import io.reactivex.internal.schedulers.TrampolineScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.robolectric.RobolectricTestRunner

@RunWith(JUnit4::class)
class DailyWeatherPresentationImplPresenterTest {

    @MockK lateinit var view: DailyWeatherView
    @MockK lateinit var weatherRepo: WeatherRepository
    @MockK lateinit var locationRepo: LocationRepository

    private val scheduler = TrampolineSchedulerProvider()
    lateinit var presenter: DailyWeatherPresenterImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        presenter = DailyWeatherPresenterImpl(weatherRepo, locationRepo, scheduler)
        presenter.setView(view)
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun `verify weather displays correctly`() {
        every { locationRepo.getCurrentLocationCoordinates() } returns returnCoordinateMap()
        every { locationRepo.convertCoordinatesToLocation(0.0, 0.0) } returns "Location"
        every { weatherRepo.getCurrentWeather(0.0, 0.0)} returns Observable.just(createDataSourceModel())

        val locationSlot = slot<String>()
        val temperatureSlot = slot<String>()
        val humiditySlot = slot<String>()
        val precipitationSlot = slot<String>()
        val windSpeedSlot = slot<String>()

        every { view.displayWeatherValues(
            capture(locationSlot),
            capture(temperatureSlot),
            capture(humiditySlot),
            capture(precipitationSlot),
            capture(windSpeedSlot)) } just runs

        presenter.viewInitialized()

        println(windSpeedSlot.captured)

        assert(locationSlot.captured == "Location")
        assert(temperatureSlot.captured == "0")
        assert(humiditySlot.captured == "Humidity: 0%")
        assert(precipitationSlot.captured == "Precipitation: 0%")
        assert(windSpeedSlot.captured == "Wind: 0 mph")
    }

    private fun returnCoordinateMap(): Map<String, Double> {
        val coordinateMap = mutableMapOf<String, Double>()
        coordinateMap["latitude"] = 0.0
        coordinateMap["longitude"] = 0.0
        return coordinateMap
    }

    private fun createDataSourceModel(): DataSourceModel {
        val currentTempList: List<String> = MutableList(24) {"0"}
        val weatherDescriptionList: List<String> = MutableList(24) {"Cloudy"}
        val humidityList: List<String> = MutableList(24) {"0"}
        val precipitationList: List<String> = MutableList(24) {"0"}
        val windList: List<String> = MutableList(24) {"0"}
        val dateList: List<String> = MutableList(24) {"0"}
        val lastTimeAccessed: Long = 0L

        return DataSourceModel(
            currentTempList,
            weatherDescriptionList,
            humidityList,
            precipitationList,
            windList,
            dateList,
            lastTimeAccessed
        )
    }
}