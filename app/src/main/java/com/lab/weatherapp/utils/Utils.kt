package com.lab.weatherapp.utils

import android.content.Context
import com.lab.weatherapp.screens.weather.WeatherState
import com.lab.weatherapp.R
import com.lab.weatherapp.sharedpreference.SharedPreference

fun changeColorTheme(status: WeatherState, context: Context){
    if(status.weatherInfo != null){
        val temp = status.weatherInfo.current.temp_c
        val temp_fl = temp.toFloat()
        val colorTheme = when{
            temp_fl <= -30 -> R.color.purple
            (temp_fl > -30 && temp_fl <= -20) -> R.color.dark_blue
            (temp_fl > -20 && temp_fl <= -10) -> R.color.medium_blue
            (temp_fl > -10 && temp_fl <= 0) -> R.color.blue
            (temp_fl > 0 && temp_fl <= 10) -> R.color.green
            (temp_fl > 10 && temp_fl <= 20) -> R.color.yellow
            (temp_fl > 20 && temp_fl <= 30) -> R.color.light_yellow
            (temp_fl > 30 && temp_fl < 40) -> R.color.orange
            (temp_fl >= 40) -> R.color.red
            else -> { R.color.blue }
        }
        SharedPreference(context).saveValueColor(colorTheme)
    }
}