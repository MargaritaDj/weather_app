package com.lab.weatherapp.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lab.weatherapp.screens.location.SearchLocation
import com.lab.weatherapp.screens.location.StateTopBarLocation

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val stateTopBarLocation = remember { mutableStateOf(StateTopBarLocation.CLOSED) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

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
            BottomNavigation(backgroundColor = MaterialTheme.colors.background) {
                items.forEach {
                    BottomNavigationItem(selected = currentRoute == it.route,
                        icon = {
                            Icon(
                                imageVector =
                                if (it.route == Routes.Weather.route) ImageVector.vectorResource(it.icon as Int)
                                else it.icon as ImageVector,
                                contentDescription = it.label,
                                tint = if (currentRoute == it.route) Color.Blue else Color.LightGray
                            )
                        },

                        label = {
                            Text(
                                text = it.label,
                                color = if (currentRoute == it.route) Color.Blue else Color.LightGray
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
    val it = Routes.Location

    when(stateTopBarLocation.value){
        StateTopBarLocation.CLOSED -> {
            TopAppBar(
                title = {
                    Text(
                        text = it.label,
                        color = Color.Blue,
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
                        Icon(Icons.Default.Add, "add", tint = Color.Blue)
                    }
                }
            )
        }

        StateTopBarLocation.OPENED -> {
            SearchLocation(stateTopBarLocation)
        }
    }
}

@Composable
fun TopBarWeather() {
    TopAppBar(
        title = {
            Text(
                text = "г. Санкт-Петербург",
                color = Color.Blue,
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
      backgroundColor = MaterialTheme.colors.background
    )
}

@Composable
fun TopBarSettings() {
    val it = Routes.Settings

    TopAppBar(
        title = {
            Text(
                text = it.label,
                color = Color.Blue,
                fontSize = 22.sp,
                maxLines = 1
            )
        },
        backgroundColor = MaterialTheme.colors.background
    )
}