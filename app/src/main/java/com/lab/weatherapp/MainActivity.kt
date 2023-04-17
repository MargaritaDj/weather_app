package com.lab.weatherapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.lab.weatherapp.navigation.AppNavigation
import com.lab.weatherapp.screens.settings.changeTheme
import com.lab.weatherapp.sharedpreference.SharedPreference
import com.lab.weatherapp.ui.theme.WeatherAppTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeTheme(SharedPreference(this).getValueTheme())
        delegate.applyDayNight()
        supportActionBar?.hide()
        setContent {
            WeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}
