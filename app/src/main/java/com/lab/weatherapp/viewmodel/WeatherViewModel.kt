package com.lab.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lab.weatherapp.network.Response
import com.lab.weatherapp.repository.RepositoryRemote
import com.lab.weatherapp.screens.weather.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repositoryRemote: RepositoryRemote
): ViewModel() {
    private val _status = MutableLiveData(WeatherState())
    val status: LiveData<WeatherState> = _status

    init {
        getWeatherInfo("London")
    }

    private fun getWeatherInfo(location: String){
        viewModelScope.launch {
            _status.value = _status.value?.copy(
                weatherInfo = null,
                isLoading = true,
                error = null
            )
            when(val result = repositoryRemote.getWeather(location)){
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
        }
    }
}