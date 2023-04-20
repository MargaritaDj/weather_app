package com.lab.weatherapp.viewmodel

import android.content.Context
import androidx.lifecycle.*
import com.lab.weatherapp.gps.LocationTracker
import com.lab.weatherapp.model.LocationName
import com.lab.weatherapp.network.Response
import com.lab.weatherapp.repository.RepositoryLocal
import com.lab.weatherapp.repository.RepositoryRemote
import com.lab.weatherapp.screens.weather.WeatherState
import com.lab.weatherapp.sharedpreference.SharedPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repositoryRemote: RepositoryRemote,
    private val repositoryLocal: RepositoryLocal
) : ViewModel() {
    private val _status = MutableLiveData(WeatherState())
    val status: LiveData<WeatherState> = _status

    val allLocation: LiveData<List<LocationName>> = repositoryLocal.allLocations.asLiveData()

    private fun getWeatherInfo(location: String) {
        viewModelScope.launch {
            try {
                when (val result = repositoryRemote.getWeather(location)) {
                    is Response.Success -> {
                        _status.value = _status.value?.copy(
                            weatherInfo = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Response.Error -> {
                        _status.value = _status.value?.copy(
                            weatherInfo = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            } catch (ex: Exception) {
                _status.value = _status.value?.copy(
                    weatherInfo = null,
                    isLoading = false,
                    error = ex.message
                )
            }
        }
    }

    fun getWeatherInfoByGPS(default: String, context: Context) {
        viewModelScope.launch {
            _status.value = _status.value?.copy(
                weatherInfo = null,
                isLoading = true,
                error = null
            )
            LocationTracker().getCurrentLocation(context)?.let { loc ->
                getWeatherInfo("${loc.latitude},${loc.longitude}")
                return@launch
            }
            getWeatherInfo(default)
        }
    }

    fun getWeatherInfoByLocation(location: String) {
        _status.value = _status.value?.copy(
            weatherInfo = null,
            isLoading = true,
            error = null
        )
        getWeatherInfo(location)
    }

    fun insertLocation(name: String) {
        viewModelScope.launch {
            repositoryLocal.addLocation(name)
        }
    }

    fun deleteLocation(name: String) {
        viewModelScope.launch {
            repositoryLocal.deleteLocation(name)
        }
    }
}