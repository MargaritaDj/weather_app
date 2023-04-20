package com.lab.weatherapp.sharedpreference

import android.content.Context
import android.content.SharedPreferences
import com.lab.weatherapp.R
import com.lab.weatherapp.app.App

const val STATE_DATA = "STATE_DATA"
const val STATE_THEME = "STATE_THEME"
const val COLOR_THEME = "COLOR_THEME"
const val LAST_LOCATION = "LAST_LOCATION"

const val DATA_DEFAULT = "Celsius"
const val THEME_DEFAULT = "Light"
const val COLOR_DEFAULT = R.color.blue
const val LAST_LOCATION_DEFAULT = "New York"

class SharedPreference(context: Context) {
    private val sharedPreferences: SharedPreferences? =
        context.getSharedPreferences("APP_SHARED_PREF", Context.MODE_PRIVATE)

    fun saveValueData(value: String){
        sharedPreferences?.edit()?.putString(STATE_DATA, value)?.apply()
    }

    fun saveValueTheme(value: String){
        sharedPreferences?.edit()?.putString(STATE_THEME, value)?.apply()
    }

    fun saveValueColor(value: Int){
        sharedPreferences?.edit()?.putInt(COLOR_THEME, value)?.apply()
    }

    fun saveValueLast(value: String){
        sharedPreferences?.edit()?.putString(LAST_LOCATION, value)?.apply()
    }

    fun getValueData(): String {
        return sharedPreferences?.getString(STATE_DATA, DATA_DEFAULT)!!
    }

    fun getValueTheme(): String{
        return sharedPreferences?.getString(STATE_THEME, THEME_DEFAULT)!!
    }

    fun getValueColor(): Int{
        return sharedPreferences?.getInt(COLOR_THEME, COLOR_DEFAULT)!!
    }

    fun getValueLast(): String{
        return sharedPreferences?.getString(LAST_LOCATION, LAST_LOCATION_DEFAULT)!!
    }

}