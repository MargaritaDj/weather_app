package com.lab.weatherapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import com.lab.weatherapp.R

sealed class Routes(val route: String, val label: String, val icon: Any){
    object Location: Routes("locations", "Locations", Icons.Default.LocationOn)
    object Weather: Routes("weather", "Weather", R.drawable.ic_baseline_cloud_24)
    object Settings: Routes("settings", "Settings", Icons.Default.Settings)
}
