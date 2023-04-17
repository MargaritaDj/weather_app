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
import com.lab.weatherapp.sharedpreference.SharedPreference

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeatherHours() {
    val currentPositionList = remember { mutableStateOf(0) }

    val lazyListState: LazyListState = rememberLazyListState()
    val snappingLayout = remember(lazyListState) { SnapLayoutInfoProvider(lazyListState) }
    val layoutInfo = remember { derivedStateOf { lazyListState.layoutInfo } }
    val itemsWeatherNow = listOf<@Composable () -> Unit>(
        { GraphWeatherHours() },
        { GraphWeatherHours() })

    ChangeMarkerCurrentPosition(currentPositionList, lazyListState)

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
        CurrentPositionItemList(currentPositionList.value, layoutInfo.value.totalItemsCount)
    }
}

@Composable
fun GraphWeatherHours() {
    data class Temp(val temp: String, val pos: Float, val time: String)
    val colorTheme = colorResource(SharedPreference(LocalContext.current).getValueColor())

    //0.1f - 0.7f, start - 0.7 по убыванию
    val data = listOf(
        Temp("-2°", 0.4f, "2am"),
        Temp("4°", 0.6f, "3am"),
        Temp("5°", 0.7f, "4am"),
        Temp("-10°", 0.2f, "5am"),
        Temp("-3°", 0.3f, "6am"),
        Temp("0°", 0.5f, "7am"),
        Temp("0°", 0.5f, "7am")
    )

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
                        text = it.temp
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
                    ImageVector.vectorResource(R.drawable.rain),
                    null,
                    modifier = Modifier
                        .size(40.dp)
                )
            }
            Spacer(modifier = Modifier.width(3.dp))
        }
    }
}