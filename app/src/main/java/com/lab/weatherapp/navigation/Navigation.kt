package com.lab.weatherapp.navigation

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lab.weatherapp.screens.location.StateTopBarLocation
import com.lab.weatherapp.screens.weather.WeatherState
import com.lab.weatherapp.sharedpreference.SharedPreference
import javax.inject.Inject

@Composable
fun AppNavigation(weatherState: WeatherState) {
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
                        Routes.Weather.route -> TopBarWeather(weatherState)
                        Routes.Location.route -> TopBarLocations(stateTopBarLocation)
                        Routes.Settings.route -> TopBarSettings()
                    }
                }
            }
        },

        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                AppNavHost(navController = navController, weatherState)
            }
        },

        bottomBar = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colors.background,
                elevation = if (isSystemInDarkTheme()) {
                    0.dp
                } else 20.dp
            ) {
                items.forEach {
                    BottomNavigationItem(selected = currentRoute == it.route,
                        icon = {
                            Icon(
                                imageVector =
                                if (it.route == Routes.Weather.route) ImageVector.vectorResource(
                                    it.icon as Int
                                )
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

    when (stateTopBarLocation.value) {
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
                elevation = if (isSystemInDarkTheme()) {
                    0.dp
                } else 20.dp
            )
        }

        StateTopBarLocation.OPENED -> {
            SearchLocation(stateTopBarLocation)
        }
    }
}

@Composable
fun TopBarWeather(weatherState: WeatherState) {
    val sharedPref = SharedPreference(LocalContext.current)
    val colorTheme = colorResource(sharedPref.getValueColor())
    val weather = weatherState.weatherInfo

    TopAppBar(
        title = {
            Text(
                text = if (weather != null) {
                    "${weather.location.name}, ${weather.location.country}"
                } else {
                    sharedPref.getValueLast()
                },
                color = colorTheme,
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        backgroundColor = MaterialTheme.colors.background,
        elevation = if (isSystemInDarkTheme()) {
            0.dp
        } else 20.dp
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
        elevation = if (isSystemInDarkTheme()) {
            0.dp
        } else 20.dp
    )
}

@Composable
fun SearchLocation(stateTopBarLocation: MutableState<StateTopBarLocation>) {
    val stateText = remember { mutableStateOf("") }
    val context = LocalContext.current

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.background
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = stateText.value,
            onValueChange = {
                stateText.value = it
            },

            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = "Search",
                    color = MaterialTheme.colors.onBackground
                )
            },

            singleLine = true,
            leadingIcon = {
                IconButton(
                    onClick = {
                        stateTopBarLocation.value = StateTopBarLocation.CLOSED
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back",
                        tint = MaterialTheme.colors.onBackground
                    )

                }
            },

            trailingIcon = {
                IconButton(
                    onClick = {
                        if (stateText.value.isEmpty()) {
                            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(context, stateText.value, Toast.LENGTH_LONG).show()
                            stateText.value = ""
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search",
                        tint = MaterialTheme.colors.onBackground
                    )
                }
            },

            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
                focusedIndicatorColor = colorResource(
                    SharedPreference(LocalContext.current)
                        .getValueColor()
                )
            )
        )
    }
}