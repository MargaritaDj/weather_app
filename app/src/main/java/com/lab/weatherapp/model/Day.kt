package com.lab.weatherapp.model

data class Day(
    val avghumidity: Double,
    val condition: Condition,
    val daily_chance_of_rain: Int,
    val daily_chance_of_snow: Int,
    val maxtemp_c: Double,
    val maxtemp_f: Double,
    val maxwind_kph: Double,
    val mintemp_c: Double,
    val mintemp_f: Double
)