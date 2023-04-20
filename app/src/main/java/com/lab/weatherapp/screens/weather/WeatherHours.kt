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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lab.weatherapp.R
import com.lab.weatherapp.model.Condition
import com.lab.weatherapp.model.Day
import com.lab.weatherapp.model.Description
import com.lab.weatherapp.model.Hour
import com.lab.weatherapp.sharedpreference.SharedPreference
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeatherHours(weatherState: WeatherState) {
    val currentPositionList = remember { mutableStateOf(0) }

    val list: MutableList<Hour> = mutableListOf()
    list.addAll(weatherState.weatherInfo!!.forecast.forecastday[0].hour)
    list.addAll(weatherState.weatherInfo.forecast.forecastday[1].hour)
    val temp = parsingTimeHours(weatherState.weatherInfo.location.localtime, list)

    val lazyListState: LazyListState = rememberLazyListState()
    val snappingLayout = remember(lazyListState) { SnapLayoutInfoProvider(lazyListState) }
    val layoutInfo = remember { derivedStateOf { lazyListState.layoutInfo } }
    val itemsWeatherNow = listOf<@Composable () -> Unit> { GraphWeatherHours(temp) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        InfoAboutCard("Soon", "")
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
    }
}

@Composable
fun GraphWeatherHours(data: List<Temp>) {
    val colorTheme = colorResource(SharedPreference(LocalContext.current).getValueColor())

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .padding(10.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        data.forEach {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp, 10.dp))
                        .fillMaxHeight(it.pos)
                        .width(40.dp)
                        .background(colorTheme),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        text = if(SharedPreference(LocalContext.current).getValueData() == "Celsius"){
                            "${it.tempC}°"
                        } else {
                        "${it.tempF}°"
                    }
                    )
                }

                Text(
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    text = it.time,
                    modifier = Modifier.padding(vertical = 5.dp)
                )

                Image(
                    ImageVector.vectorResource(
                        Description.getIconFromDescription(it.isDay, it.con.text)
                    ),
                    null,
                    modifier = Modifier
                        .size(40.dp)
                )
            }
            Spacer(modifier = Modifier.width(3.dp))
        }
    }
}

fun parsingTimeHours(currentDate: String, hours: List<Hour>): MutableList<Temp> {
    var indexHour = 0
    var time: String
    val currentTime: String = currentDate.substring(11)
    hours.forEach {
        time = it.time.substring(11)
        if(currentDate.substringBefore(" ") == it.time.substringBefore(" ") &&
            currentTime.substringBefore(":") == time.substringBefore(":")){
            indexHour = hours.indexOf(it)
        }
    }

    val list: MutableList<Hour> = mutableListOf()
    val temp: MutableList<Int> = mutableListOf()
    for(i in indexHour..indexHour+6){
        list.add(hours[i])
        temp.add(hours[i].temp_c.roundToInt())
    }

    val tempSet: MutableSet<Int> = temp.sortedDescending().toMutableSet()
    val mapTempPos: MutableMap<Int, Float> = mutableMapOf()

    tempSet.forEach{
        mapTempPos[it] = 0.7f - tempSet.indexOf(it) * 0.1f
    }

    val tempList: MutableList<Temp> = mutableListOf()
    list.forEach {
        tempList.add(
            Temp(it.temp_c.roundToInt(),
                 it.temp_f.roundToInt(),
                mapTempPos[it.temp_c.roundToInt()]!!,
                it.time.substring(11),
                it.condition,
                it.is_day)
        )
    }

    return tempList
}

data class Temp(val tempC: Int, val tempF: Int, val pos: Float, val time: String, val con: Condition,
val isDay: Int)