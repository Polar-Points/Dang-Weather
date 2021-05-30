package com.dang.marty.weather.data.repository

import com.dang.marty.weather.utils.Transformers
import com.dang.marty.weather.data.webservice.OpenWeatherApiService
import com.dang.marty.weather.utils.Keys
import com.dang.marty.weather.presentation.model.DataSourceModel


interface WeatherRepository {
    fun getCurrentWeather(latitude: Double, longitude: Double): DataSourceModel
}
class WeatherRepositoryImpl (
    private  val webservice: OpenWeatherApiService): WeatherRepository {

    override fun getCurrentWeather(latitude: Double, longitude: Double): DataSourceModel {

        //val cacheObject = cache.getWeatherObject()

        // valid cache
//        if(cacheObject != null) {
//            val time = System.currentTimeMillis() - cacheObject.lastTimeAccessed
//            if(60000 >= time) {
//                return Transformers.transformCacheToDataSourceModel(cacheObject)
//            }
//        }

        // make a new network request since cache is old

        val data = webservice.getCurrentWeather(latitude, longitude,"minutely,daily", Keys.API_KEY,"imperial")
        val dataSourceModel = Transformers.transformApiToDataSourceModel(data)

       // cache.deleteCache()
        // cache.updateCache(Transformers.transformDataSourceModelToCache(dataSourceModel))
        return dataSourceModel
    }
}