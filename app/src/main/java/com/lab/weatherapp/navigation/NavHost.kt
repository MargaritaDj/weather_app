package com.lab.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lab.weatherapp.screens.location.LocationScreen
import com.lab.weatherapp.screens.settings.SettingScreen
import com.lab.weatherapp.screens.weather.WeatherScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.Weather.route){
        composable(Routes.Location.route) { LocationScreen().Location() }
        composable(Routes.Weather.route) { WeatherScreen().Weather() }
        composable(Routes.Settings.route) { SettingScreen().Settings() }
    }
}