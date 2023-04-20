package com.lab.weatherapp.repository

import com.lab.weatherapp.model.Weather
import com.lab.weatherapp.network.Response
import com.lab.weatherapp.network.WeatherApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class RepositoryRemote @Inject constructor(
    private val weatherApiService: WeatherApiService
) {
    suspend fun getWeather(location: String): Response<Weather> =
        withContext(Dispatchers.IO) {
            try{
                val response = weatherApiService.getWeather(location)
                return@withContext Response.Success("Success", response)
            } catch (e: IOException){
                return@withContext Response.Error(e.message)
            }
        }
}