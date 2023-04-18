package com.lab.weatherapp.screens.weather

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

class WeatherScreen  {
    @Composable
    fun Weather() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            WeatherDay()
            WeatherHours()
            WeatherWeek()
        }
    }
}