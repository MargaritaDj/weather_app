package com.lab.weatherapp.network

import com.lab.weatherapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class WeatherInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val requestUrl = chain.request().url().newBuilder()
            .addQueryParameter("key", BuildConfig.API_KEY)
            .addQueryParameter("days", BuildConfig.DAYS)
            .build()

        val request = chain.request().newBuilder()
            .url(requestUrl)
            .build()

        return chain.proceed(request)
    }
}