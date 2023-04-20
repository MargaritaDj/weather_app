package com.lab.weatherapp.model

import androidx.room.Entity

data class Location(
    val country: String,
    val localtime: String,
    val name: String,
    val region: String,
)