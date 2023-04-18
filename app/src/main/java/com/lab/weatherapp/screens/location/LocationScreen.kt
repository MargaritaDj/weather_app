package com.lab.weatherapp.screens.location

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
import com.lab.weatherapp.R
import com.lab.weatherapp.sharedpreference.SharedPreference
import javax.inject.Inject

class LocationScreen {
    @Composable
    fun Location() {
        val locations = remember {
            mutableStateListOf(
                "Chicago, Illinois",
                "New York, New York",
                "Омск, Омская обл., Россия",
                "Рим, Италия",
                "Краснодар, Краснодарский край, Краснодарский краааай",
                "Chicago, Illinois",
                "New York, New York",
                "Омск, Омская обл., Россия",
                "Рим, Италия",
                "Chicago, Illinois",
                "New York, New York",
                "Омск, Омская обл., Россия",
                "Рим, Италия",
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clickable { },
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

            locations.forEach {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clickable {

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
                        text = it,
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
                            locations.remove(it)
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
