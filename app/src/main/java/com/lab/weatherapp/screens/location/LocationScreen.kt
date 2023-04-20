package com.lab.weatherapp.screens.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lab.weatherapp.MainActivity
import com.lab.weatherapp.R
import com.lab.weatherapp.model.LocationName
import com.lab.weatherapp.navigation.Routes
import com.lab.weatherapp.screens.weather.WeatherState
import com.lab.weatherapp.sharedpreference.SharedPreference
import com.lab.weatherapp.viewmodel.WeatherViewModel
import javax.inject.Inject

class LocationScreen {
    @Composable
    fun Location(viewModel: WeatherViewModel, allLocations: List<LocationName>, navController: NavController) {
        val last = SharedPreference(LocalContext.current).getValueLast()
        val context = LocalContext.current
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clickable {
                        if (ContextCompat.checkSelfPermission(
                                context, Manifest.permission.ACCESS_FINE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED)
                        {
                            Toast.makeText(
                                context,
                                "You did not give permission for this function to be executed.\n" +
                                        "You can change in the system settings.",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            val locationManager =
                                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                            ) {
                                navController.navigate(Routes.Weather.route)
                                viewModel.getWeatherInfoByGPS(last, context)
                            } else {
                                Toast.makeText(
                                    context,
                                    "Turn on the GPS and try again",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    ImageVector.vectorResource(R.drawable.my_location), "my_location",
                    tint = MaterialTheme.colors.onBackground,
                    modifier = Modifier.size(30.dp).padding(start = 10.dp)
                )

                Text(
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 16.sp,
                    maxLines = 1,
                    text = "Your current location",
                    modifier = Modifier.padding(start = 5.dp)
                )
            }

            allLocations.forEach {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clickable {
                            navController.navigate(Routes.Weather.route)
                            viewModel.getWeatherInfoByLocation(it.name)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.LocationOn, "location",
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier.size(35.dp).padding(start = 5.dp)
                    )

                    Text(
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 16.sp,
                        maxLines = 1,
                        text = it.name,
                        modifier = Modifier.weight(85f).padding(start = 5.dp),
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(15f),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        IconButton(onClick = {
                            viewModel.deleteLocation(it.name)
                        }) {
                            Icon(
                                Icons.Default.Close,
                                "close",
                                tint = MaterialTheme.colors.onBackground,
                                modifier = Modifier.size(25.dp).padding(end = 5.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
