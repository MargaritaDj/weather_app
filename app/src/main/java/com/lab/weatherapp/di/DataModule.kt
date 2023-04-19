package com.lab.weatherapp.di

import com.lab.weatherapp.network.WeatherApiService
import com.lab.weatherapp.repository.RepositoryRemote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
}