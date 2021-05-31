package com.dang.marty.weather.data.webservice

import com.dang.marty.weather.data.model.CurrentWeatherModel
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *   Created by Marty Dang on 8/9/20
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */

// Create a seperate file and insert your key there

interface OpenWeatherApiService {
    @GET("/data/2.5/onecall")
    fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("exclude") exclude: String,
        @Query("appid") apiKey: String,
        @Query("units") unit: String): Observable<CurrentWeatherModel>
}