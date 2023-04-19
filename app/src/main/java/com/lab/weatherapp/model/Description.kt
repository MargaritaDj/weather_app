package com.lab.weatherapp.model
import com.lab.weatherapp.R

object Description {
    private val mapDescriptionIconDay = mapOf (
        "Cloudy" to R.drawable.cloudy,
        "Patchy rain possible" to R.drawable.rain_and_sun,
        "Thunderstorm" to R.drawable.scatterad_thunderstorm,
        "Snow" to R.drawable.snow,
        "Mist" to R.drawable.wind,
        "Heavy rain" to R.drawable.drizzle_and_sun,
        "Sunny" to R.drawable.sunny,
        "Partly cloudy" to R.drawable.partly_cloudy,
        "Light rain" to R.drawable.rain,
        "sleet" to R.drawable.sleet,
        "fog" to R.drawable.fog,
        "Overcast" to R.drawable.cloudy
    )

    private val mapDescriptionIconNight = mapOf (
        "Cloudy" to R.drawable.cloudy,
        "Patchy rain possible" to R.drawable.rain_night,
        "Thunderstorm" to R.drawable.sever_thunderstorm,
        "Snow" to R.drawable.snow,
        "Mist" to R.drawable.wind,
        "Heavy rain" to R.drawable.drizzle_night,
        "Clear" to R.drawable.clear_night,
        "Partly cloudy" to R.drawable.partly_cloudy_night,
        "Light rain" to R.drawable.rain,
        "sleet" to R.drawable.sleet,
        "fog" to R.drawable.fog,
        "Overcast" to R.drawable.cloudy
    )

    fun getIconFromDescription(isDay: Int, description: String): Int{
        val icon =
            if(isDay == 1 && mapDescriptionIconDay[description] != null){
                mapDescriptionIconDay[description]!!
            } else {
            if(isDay == 0 && mapDescriptionIconNight[description] != null){
                mapDescriptionIconNight[description]!!
            } else {
                R.drawable.cloudy
            }
        }
        return icon
    }
}