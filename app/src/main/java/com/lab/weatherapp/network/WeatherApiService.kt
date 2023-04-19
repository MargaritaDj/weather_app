package com.lab.weatherapp.network

import com.lab.weatherapp.model.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("forecast.json?")
    suspend fun getWeather(
        @Query("q") q: String
    ): Weather
}