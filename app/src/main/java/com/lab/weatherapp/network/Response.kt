package com.lab.weatherapp.network
import com.lab.weatherapp.model.Weather

sealed class Response<Weather>(
    val data: Weather? = null,
    val message: String? = null
) {

    class Success<Weather>(message: String?, data: Weather?) :
        Response<Weather>(message = message, data = data)

    class Error<Weather>(message: String?) : Response<Weather>(message = message)

}