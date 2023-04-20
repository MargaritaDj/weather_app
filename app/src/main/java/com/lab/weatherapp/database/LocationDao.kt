package com.lab.weatherapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lab.weatherapp.model.LocationName
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    @Insert(entity = LocationName::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: LocationName)

    @Query("SELECT * FROM locations_table")
    fun getAllLocations(): Flow<List<LocationName>>

    @Query("DELETE FROM locations_table WHERE name = :name")
    suspend fun deleteByName(name: String)

    @Query("SELECT COUNT(*) FROM locations_table")
    fun getCount(): Int
}