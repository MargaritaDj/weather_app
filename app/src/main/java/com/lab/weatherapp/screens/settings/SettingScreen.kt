package com.lab.weatherapp.screens.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lab.weatherapp.R
import com.lab.weatherapp.sharedpreference.SharedPreference
import javax.inject.Inject

class SettingScreen{
    @Composable
    fun Settings() {
        val sharedPreference = SharedPreference(LocalContext.current)
        val stateData = rememberSaveable { mutableStateOf(sharedPreference.getValueData()) }
        val stateTheme = rememberSaveable { mutableStateOf(sharedPreference.getValueTheme()) }

        SaveSettings(stateData, stateTheme)


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Title("DATA SOURCE")
            ItemListData(
                ImageVector.vectorResource(R.drawable.usa), colorResource(R.color.blue),
                "Fahrenheit", stateData
            )
            ItemListData(
                ImageVector.vectorResource(R.drawable.international), colorResource(R.color.green),
                "Celsius", stateData
            )

            Title("THEME")
            ItemListTheme(Icons.Default.Settings, Color.Gray, "Match System", stateTheme)
            ItemListTheme(
                ImageVector.vectorResource(R.drawable.light), colorResource(R.color.yellow),
                "Light", stateTheme
            )
            ItemListTheme(
                ImageVector.vectorResource(R.drawable.point), Color.Black,
                "Dark", stateTheme
            )
        }

    }

    @Composable
    fun Title(title: String) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(start = 15.dp, top = 10.dp, bottom = 10.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                color = MaterialTheme.colors.onBackground,
                fontSize = 16.sp,
                maxLines = 1,
                text = title,
                fontWeight = FontWeight.Bold
            )
        }
    }

    @Composable
    fun ItemListData(icon: ImageVector, color: Color, name: String, state: MutableState<String>) {
        Row(
            modifier = Modifier
                .clickable {
                    state.value = name
                }
        ) {
            RowItem(icon, color, name, state)
        }
    }

    @Composable
    fun ItemListTheme(icon: ImageVector, color: Color, name: String, state: MutableState<String>) {
        Row(
            modifier = Modifier
                .clickable {
                    state.value = name
                    changeTheme(state.value)
                }
        ) {
            RowItem(icon, color, name, state)
        }
    }

    @Composable
    fun RowItem(icon: ImageVector, color: Color, name: String, state: MutableState<String>) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon, null,
                tint = color,
                modifier = Modifier.size(40.dp).padding(start = 20.dp)
            )

            Text(
                color = MaterialTheme.colors.onBackground,
                fontSize = 16.sp,
                maxLines = 1,
                text = name,
                modifier = Modifier.weight(85f).padding(start = 20.dp),
                textAlign = TextAlign.Start
            )

            if (name == state.value) {
                Icon(
                    Icons.Default.Done, null,
                    tint = Color.Gray,
                    modifier = Modifier.weight(15f).padding(end = 20.dp)
                )
            }
        }
    }

    @Composable
    fun SaveSettings(stateData: MutableState<String>, stateTheme: MutableState<String>){
        val sharedPreference = SharedPreference(LocalContext.current)
        sharedPreference.saveValueData(stateData.value)
        sharedPreference.saveValueTheme(stateTheme.value)
    }
}

fun changeTheme(stateTheme: String){
    val theme = when(stateTheme){
        "Dark" -> AppCompatDelegate.MODE_NIGHT_YES
        "Light" -> AppCompatDelegate.MODE_NIGHT_NO
        "Match System" -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        else -> AppCompatDelegate.MODE_NIGHT_NO
    }
    AppCompatDelegate.setDefaultNightMode(theme)
}