package com.lab.weatherapp.di

import android.content.Context
import com.lab.weatherapp.database.LocationRoomDatabase
import com.lab.weatherapp.network.WeatherApiService
import com.lab.weatherapp.repository.RepositoryLocal
import com.lab.weatherapp.repository.RepositoryRemote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideRepositoryRemote(weatherApiService: WeatherApiService): RepositoryRemote {
        val repositoryRemote by lazy { RepositoryRemote(weatherApiService) }
        return repositoryRemote
    }

    @Provides
    @Singleton
    fun provideRepositoryLocal(@ApplicationContext context: Context): RepositoryLocal {
        val database by lazy { LocationRoomDatabase.getDatabase(context)}
        val repositoryLocal by lazy { RepositoryLocal(database.locationDao()) }

        return repositoryLocal
    }
}