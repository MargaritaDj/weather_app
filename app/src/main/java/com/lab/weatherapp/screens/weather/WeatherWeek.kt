package com.lab.weatherapp.screens.weather

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lab.weatherapp.R
import com.lab.weatherapp.model.Condition
import com.lab.weatherapp.model.Description
import com.lab.weatherapp.model.Forecast
import com.lab.weatherapp.model.Hour
import com.lab.weatherapp.sharedpreference.SharedPreference
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeatherWeek(weatherState: WeatherState) {
    val currentPositionList = remember { mutableStateOf(0) }

    val tempList = parsingTimeDays(weatherState.weatherInfo!!.forecast)

    val lazyListState: LazyListState = rememberLazyListState()
    val snappingLayout = remember(lazyListState) { SnapLayoutInfoProvider(lazyListState) }
    val layoutInfo = remember { derivedStateOf { lazyListState.layoutInfo } }
    val itemsWeatherNow = listOf<@Composable () -> Unit>(
        { GraphWeatherWeek(tempList) },
        { InfoWeatherWeek(tempList) })

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
fun GraphWeatherWeek(data: MutableList<TempDay>) {
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
                IconWeather(Description.getIconFromDescription(it.isDay, it.con.text))
                Date(it.date)
                MinTemp(it)
                MaxTemp(it)
            }

            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun Date(date: String) {
    Box(
        Modifier
            .height(40.dp)
            .width(70.dp),
        contentAlignment = Alignment.Center
    ) {
        Row {
            Text(
                color = MaterialTheme.colors.onBackground,
                fontSize = 16.sp,
                text = "${date.substring(3)}."
            )
            Text(
                color = MaterialTheme.colors.onBackground,
                fontSize = 16.sp,
                text = date.substringBefore("-")
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
fun InfoWeatherWeek(data: MutableList<TempDay>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 10.dp, bottom = 10.dp),
        horizontalAlignment = Alignment.Start
    )
    {
        data.forEach {
            Row(
                modifier = Modifier.height(40.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Date(it.date)
                IconWeather(Description.getIconFromDescription(it.isDay, it.con.text))
                Text(
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 14.sp,
                    text = "${it.con.text}. ${it.wind} km/h winds.",
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun ProbabilityPrecipitation(precipitation: Int) {
    Box(
        modifier = Modifier
            .height(40.dp)
            .width(60.dp)
            .clip(RoundedCornerShape(10.dp, 0.dp, 0.dp, 10.dp))
            .background(
                if (isSystemInDarkTheme()) Color.LightGray else
                    Color.LightGray.copy(alpha = 0.6f)
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            color = if (isSystemInDarkTheme()) Color.DarkGray else Color.Gray,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            text = "${precipitation}%",
            modifier = Modifier.padding(start = 5.dp, end = 10.dp)
        )
    }
}

@Composable
fun MinTemp(item: TempDay) {
    val colorTheme = colorResource(SharedPreference(LocalContext.current).getValueColor())

    Box(
        Modifier
            .height(40.dp)
            .width(40.dp)
            .background(colorTheme.copy(alpha = 0.6f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            text = if (SharedPreference(LocalContext.current).getValueData() == "Celsius") {
                "${item.tempMinC}°"
            } else {
                "${item.tempMinF}°"
            }
        )
    }
}

@Composable
fun MaxTemp(item: TempDay) {
    val colorTheme = colorResource(SharedPreference(LocalContext.current).getValueColor())
    Box(
        contentAlignment = Alignment.CenterEnd
    ) {
        Row {
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp)
                    .background(colorTheme)
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(0.dp, 10.dp, 10.dp, 0.dp))
                    .height(40.dp)
                    .fillMaxWidth(item.pos)
                    .background(colorTheme)
            )
        }
        Text(
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            text = if (SharedPreference(LocalContext.current).getValueData() == "Celsius") {
                "${item.tempMaxC}°"
            } else {
                "${item.tempMaxF}°"
            },
            modifier = Modifier.padding(end = 5.dp)
        )

    }
}

fun parsingTimeDays(forecast: Forecast): MutableList<TempDay> {
    val tempMax: MutableList<Int> = mutableListOf()
    for (i in 0..7) {
        tempMax.add(forecast.forecastday[i].day.maxtemp_c.roundToInt())
    }
    val tempMaxSet: MutableSet<Int> = tempMax.sortedDescending().toMutableSet()
    val mapTempPos: MutableMap<Int, Float> = mutableMapOf()

    tempMaxSet.forEach {
        mapTempPos[it] = 0.8f - tempMaxSet.indexOf(it) * 0.1f
    }

    val tempList: MutableList<TempDay> = mutableListOf()

    for (i in 0..7) {
        val osad =
            if (forecast.forecastday[i].day.daily_chance_of_rain != 0) {
                forecast.forecastday[i].day.daily_chance_of_rain
            } else {
                forecast.forecastday[i].day.daily_chance_of_snow
            }

        tempList.add(
            TempDay(
                forecast.forecastday[i].day.mintemp_c.roundToInt(),
                forecast.forecastday[i].day.mintemp_f.roundToInt(),
                forecast.forecastday[i].day.maxtemp_c.roundToInt(),
                forecast.forecastday[i].day.maxtemp_f.roundToInt(),
                mapTempPos[forecast.forecastday[i].day.maxtemp_c.roundToInt()]!!,
                forecast.forecastday[i].date.substring(5),
                forecast.forecastday[i].day.condition,
                1,
                forecast.forecastday[i].day.maxwind_kph.roundToInt(),
                osad
            )
        )
    }

    return tempList
}

data class TempDay(
    val tempMinC: Int, val tempMinF: Int, val tempMaxC: Int, val tempMaxF: Int,
    val pos: Float, val date: String, val con: Condition, val isDay: Int,
    val wind: Int, val osad: Int
)