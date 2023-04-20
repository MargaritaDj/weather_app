package com.lab.weatherapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations_table")
data class LocationName (
   @PrimaryKey val name: String
)