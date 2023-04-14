package com.lab.weatherapp.screens.weather

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lab.weatherapp.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeatherWeek() {
    val currentPositionList = remember { mutableStateOf(0) }

    val lazyListState: LazyListState = rememberLazyListState()
    val snappingLayout = remember(lazyListState) { SnapLayoutInfoProvider(lazyListState) }
    val layoutInfo = remember { derivedStateOf { lazyListState.layoutInfo } }
    val itemsWeatherNow = listOf<@Composable () -> Unit>(
        { GraphWeatherWeek() },
        { InfoWeatherWeek() })

    ChangeMarkerCurrentPosition(currentPositionList, lazyListState)

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        InfoAboutCard("This Week", "")
        LazyRow(
            state = lazyListState,
            flingBehavior = rememberSnapFlingBehavior(snappingLayout)
        ) {
            itemsIndexed(
                itemsWeatherNow
            ) { _, item ->
                Box(
                    modifier = Modifier
                        .fillParentMaxWidth()
                ) {
                    Card(item)
                }
            }
        }
        CurrentPositionItemList(currentPositionList.value, layoutInfo.value.totalItemsCount)
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun GraphWeatherWeek() {
    data class TempW(
        val temp: String, val pos: Float, val osad: String,
        val day: String, val date: String
    )

    //0.1f - 0.8f, start - 0.8 по убыванию
    val data = listOf(
        TempW("-2°", 0.4f, "30", "TUE", "9"),
        TempW("4°", 0.6f, "0", "WED", "10"),
        TempW("5°", 0.7f, "0", "THU", "11"),
        TempW("-10°", 0.2f, "40", "FRI", "12"),
        TempW("-3°", 0.3f, "70", "SAT", "13"),
        TempW("0°", 0.5f, "0", "SUN", "14"),
        TempW("0°", 0.5f, "25", "MON", "15"),
        TempW("6°", 0.8f, "25", "TUE", "16")
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 10.dp, bottom = 10.dp),
        horizontalAlignment = Alignment.Start
    )
    {
        data.forEach {
            Row {
                ProbabilityPrecipitation(it.osad)
                IconWeather(R.drawable.partly_cloudy)
                Date(it.day, it.date)
                MinTemp()
                MaxTemp(it.pos, it.temp)
            }

            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun Date(day: String, date: String) {
    Box(
        Modifier
            .height(40.dp)
            .width(70.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                color = MaterialTheme.colors.onBackground,
                fontSize = 14.sp,
                text = day
            )
            Text(
                color = Color.Gray.copy(alpha = 0.8f),
                fontSize = 12.sp,
                text = date
            )
        }
    }
}

@Composable
fun IconWeather(idIcon: Int) {
    Image(
        ImageVector.vectorResource(idIcon),
        null,
        modifier = Modifier
            .size(40.dp)
    )
}

@Composable
fun InfoWeatherWeek() {
    data class TempW(
        val temp: String, val pos: Float, val osad: String,
        val day: String, val date: String
    )

    val data = listOf(
        TempW("-2°", 0.4f, "30", "TUE", "9"),
        TempW("4°", 0.6f, "0", "WED", "10"),
        TempW("5°", 0.7f, "0", "THU", "11"),
        TempW("-10°", 0.2f, "40", "FRI", "12"),
        TempW("-3°", 0.3f, "70", "SAT", "13"),
        TempW("0°", 0.5f, "0", "SUN", "14"),
        TempW("0°", 0.5f, "25", "MON", "15"),
        TempW("6°", 0.8f, "25", "TUE", "16")
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 10.dp, bottom = 10.dp),
        horizontalAlignment = Alignment.Start
    )
    {
        data.forEach {
            Row(
                modifier = Modifier.height(40.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Date(it.day, it.date)
                IconWeather(R.drawable.partly_cloudy)
                Text(
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 14.sp,
                    text = "Partly cloudy. 7m/s winds.",
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun ProbabilityPrecipitation(precipitation: String){
    Box(
        modifier = Modifier
            .height(40.dp)
            .width(60.dp)
            .clip(RoundedCornerShape(10.dp, 0.dp, 0.dp, 10.dp))
            .background(
                if (precipitation == "0") MaterialTheme.colors.background else
                    Color.LightGray.copy(alpha = 0.6f)
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            color = if (precipitation == "0") MaterialTheme.colors.background else Color.Gray,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            text = "${precipitation}%",
            modifier = Modifier.padding(start = 5.dp, end = 10.dp)
        )
    }
}

@Composable
fun MinTemp(){
    Box(
        Modifier
            .height(40.dp)
            .width(40.dp)
            .background(Color.Blue.copy(alpha = 0.6f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            text = "-15°"
        )
    }
}

@Composable
fun MaxTemp(pos: Float, temp: String){
    Box(
        contentAlignment = Alignment.CenterEnd
    ) {
        Row {
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp)
                    .background(Color.Blue)
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(0.dp, 10.dp, 10.dp, 0.dp))
                    .height(40.dp)
                    .fillMaxWidth(pos)
                    .background(Color.Blue)
            )
        }
        Text(
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            text = temp,
            modifier = Modifier.padding(end = 5.dp)
        )

    }
}