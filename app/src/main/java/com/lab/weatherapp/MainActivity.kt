package com.lab.weatherapp

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue.Companion.Saver
import com.lab.weatherapp.model.LocationName
import com.lab.weatherapp.navigation.AppNavigation
import com.lab.weatherapp.screens.weather.WeatherState
import com.lab.weatherapp.sharedpreference.SharedPreference
import com.lab.weatherapp.ui.theme.WeatherAppTheme
import com.lab.weatherapp.utils.changeColorTheme
import com.lab.weatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel: WeatherViewModel by viewModels()
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        showWindowPermission(viewModel)
        setContent {
            val stateWeather = remember { mutableStateOf(WeatherState()) }
            viewModel.status.observe(this) { state ->
                stateWeather.value = state
            }

            val stateAllLocations = remember { mutableStateOf(listOf<LocationName>()) }
            viewModel.allLocation.observe(this) { allLocation ->
                stateAllLocations.value = allLocation
            }

            changeColorTheme(stateWeather.value, this)
            WeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AppNavigation(stateWeather.value, stateAllLocations.value, viewModel)
                }
            }
        }
    }

    private fun showWindowPermission(viewModel: WeatherViewModel) {
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            viewModel.getWeatherInfoByGPS(SharedPreference(this).getValueLast(), this)
        }
        permissionLauncher.launch(
            arrayOf(
                ACCESS_FINE_LOCATION
            )
        )
    }
}
