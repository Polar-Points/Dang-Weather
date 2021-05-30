package com.dang.marty.weather.dagger

import com.dang.marty.weather.data.webservice.OpenWeatherApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


/**
 *   Created by Marty Dang on 9/9/20
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */
@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    fun provideMoshi(): Moshi{
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    fun provideHttpClient(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
    }

    @Provides
    fun provideRetrofit(httpClient : OkHttpClient.Builder, moshi : Moshi): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(httpClient.build())
            .baseUrl("https://api.openweathermap.org")
            .build()
    }

    @Provides
    fun provideApi(retrofit: Retrofit): OpenWeatherApiService {
        return retrofit.create(OpenWeatherApiService::class.java)
    }
}