package com.lab.weatherapp.navigation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lab.weatherapp.screens.location.SearchLocation
import com.lab.weatherapp.screens.location.StateTopBarLocation
import com.lab.weatherapp.sharedpreference.SharedPreference

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val stateTopBarLocation = remember { mutableStateOf(StateTopBarLocation.CLOSED) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val colorTheme = colorResource(SharedPreference(LocalContext.current).getValueColor())

    val items = listOf(
        Routes.Location,
        Routes.Weather,
        Routes.Settings
    )

    Scaffold(
        topBar = {
            items.forEach {
                if (currentRoute == it.route) {
                    when (it.route) {
                        Routes.Weather.route -> TopBarWeather()
                        Routes.Location.route -> TopBarLocations(stateTopBarLocation)
                        Routes.Settings.route -> TopBarSettings()
                    }
                }
            }
        },

        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                AppNavHost(navController = navController)
            }
        },

        bottomBar = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colors.background,
                elevation = if(isSystemInDarkTheme()) { 0.dp } else 20.dp) {
                items.forEach {
                    BottomNavigationItem(selected = currentRoute == it.route,
                        icon = {
                            Icon(
                                imageVector =
                                if (it.route == Routes.Weather.route) ImageVector.vectorResource(it.icon as Int)
                                else it.icon as ImageVector,
                                contentDescription = it.label,
                                tint = if (currentRoute == it.route) colorTheme else Color.LightGray
                            )
                        },

                        label = {
                            Text(
                                text = it.label,
                                color = if (currentRoute == it.route) colorTheme else Color.LightGray
                            )
                        },

                        onClick = {
                            if (currentRoute != it.route) {
                                navController.navigate(it.route) {
                                    navController.graph.startDestinationRoute?.let { route ->
                                        popUpTo(route) {
                                            saveState = true
                                        }
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    )
}

@Composable
fun TopBarLocations(stateTopBarLocation: MutableState<StateTopBarLocation>) {
    val colorTheme = colorResource(SharedPreference(LocalContext.current).getValueColor())
    val it = Routes.Location

    when(stateTopBarLocation.value){
        StateTopBarLocation.CLOSED -> {
            TopAppBar(
                title = {
                    Text(
                        text = it.label,
                        color = colorTheme,
                        fontSize = 22.sp,
                        maxLines = 1
                    )
                },
                backgroundColor = MaterialTheme.colors.background,
                actions = {
                    IconButton(onClick = {
                        stateTopBarLocation.value = StateTopBarLocation.OPENED
                    })
                    {
                        Icon(Icons.Default.Add, "add", tint = colorTheme)
                    }
                },
                elevation = if(isSystemInDarkTheme()) { 0.dp } else 20.dp
            )
        }

        StateTopBarLocation.OPENED -> {
            SearchLocation(stateTopBarLocation)
        }
    }
}

@Composable
fun TopBarWeather() {
    val colorTheme = colorResource(SharedPreference(LocalContext.current).getValueColor())

    TopAppBar(
        title = {
            Text(
                text = "г. Санкт-Петербург",
                color = colorTheme,
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        backgroundColor = MaterialTheme.colors.background,
        elevation = if(isSystemInDarkTheme()) { 0.dp } else 20.dp
    )
}

@Composable
fun TopBarSettings() {
    val colorTheme = colorResource(SharedPreference(LocalContext.current).getValueColor())
    val it = Routes.Settings

    TopAppBar(
        title = {
            Text(
                text = it.label,
                color = colorTheme,
                fontSize = 22.sp,
                maxLines = 1
            )
        },
        backgroundColor = MaterialTheme.colors.background,
        elevation = if(isSystemInDarkTheme()) { 0.dp } else 20.dp
    )
}