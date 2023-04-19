package com.lab.weatherapp.screens.weather

import com.lab.weatherapp.model.Weather

data class WeatherState(
    val weatherInfo: Weather? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
