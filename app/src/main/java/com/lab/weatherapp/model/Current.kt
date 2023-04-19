package com.lab.weatherapp.model

data class Current(
    val condition: Condition,
    val feelslike_c: Double,
    val feelslike_f: Double,
    val humidity: Int,
    val is_day: Int,
    val temp_c: Double,
    val temp_f: Double,
    val wind_dir: String,
    val wind_kph: Double,
)