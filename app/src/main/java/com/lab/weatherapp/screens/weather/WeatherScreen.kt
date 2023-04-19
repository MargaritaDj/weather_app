package com.lab.weatherapp.screens.weather

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lab.weatherapp.R

class WeatherScreen {
    @Composable
    fun Weather(weatherState: WeatherState) {
        if (weatherState.isLoading) {
            Loading()
        }

        if (weatherState.weatherInfo != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                WeatherDay(weatherState)
                WeatherHours(weatherState)
                WeatherWeek(weatherState)
            }
        }

        if (weatherState.error != null) {
            Error(weatherState.error)
        }
    }
}

@Composable
fun Loading() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(70.dp),
            color = colorResource(R.color.blue)
        )
    }
}

@Composable
fun Error(error: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                ImageVector.vectorResource(R.drawable.error_icon),
                null,
                modifier = Modifier.size(150.dp),
                tint = colorResource(R.color.red)
            )
            Text(
                text = error,
                textAlign = TextAlign.Center,
                color = colorResource(R.color.red),
                fontSize = 18.sp
            )
        }
    }
}