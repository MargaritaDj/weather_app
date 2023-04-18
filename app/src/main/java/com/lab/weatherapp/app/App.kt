package com.lab.weatherapp.app

import android.app.Application
import com.lab.weatherapp.screens.settings.changeTheme
import com.lab.weatherapp.sharedpreference.SharedPreference
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        changeTheme(SharedPreference(this).getValueTheme())
    }
}