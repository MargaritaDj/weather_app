package com.lab.weatherapp.navigation

import android.content.Context
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lab.weatherapp.model.LocationName
import com.lab.weatherapp.screens.location.LocationScreen
import com.lab.weatherapp.screens.settings.SettingScreen
import com.lab.weatherapp.screens.weather.WeatherScreen
import com.lab.weatherapp.screens.weather.WeatherState
import com.lab.weatherapp.viewmodel.WeatherViewModel

@Composable
fun AppNavHost(
    navController: NavHostController, weatherState: WeatherState,
    allLocations: List<LocationName>, viewModel: WeatherViewModel
) {
    NavHost(navController = navController, startDestination = Routes.Weather.route) {
        composable(Routes.Location.route) { LocationScreen().Location(viewModel, allLocations, navController) }
        composable(Routes.Weather.route) { WeatherScreen().Weather(weatherState) }
        composable(Routes.Settings.route) { SettingScreen().Settings() }
    }
}