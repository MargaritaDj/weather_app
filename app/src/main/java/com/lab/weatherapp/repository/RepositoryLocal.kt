package com.lab.weatherapp.repository

import com.lab.weatherapp.database.LocationDao
import com.lab.weatherapp.model.LocationName
import kotlinx.coroutines.flow.Flow

class RepositoryLocal(private val locationDao: LocationDao) {
    val allLocations: Flow<List<LocationName>> = locationDao.getAllLocations()

    suspend fun addLocation(name: String){
        locationDao.insertLocation(LocationName(name))
    }

    suspend fun deleteLocation(name: String) {
        locationDao.deleteByName(name)
    }
}