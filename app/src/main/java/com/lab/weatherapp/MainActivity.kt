package com.lab.weatherapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.lab.weatherapp.navigation.AppNavigation
import com.lab.weatherapp.screens.weather.WeatherState
import com.lab.weatherapp.ui.theme.WeatherAppTheme
import com.lab.weatherapp.utils.changeColorTheme
import com.lab.weatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel: WeatherViewModel by viewModels()
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContent {
            val stateWeather = remember { mutableStateOf(WeatherState()) }
            viewModel.status.observe(this){ state -> stateWeather.value = state}
            changeColorTheme(stateWeather.value, this)
            WeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AppNavigation(stateWeather.value)
                }
            }
        }
    }
}
