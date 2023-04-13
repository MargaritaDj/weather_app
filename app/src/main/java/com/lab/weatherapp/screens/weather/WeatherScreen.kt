package com.lab.weatherapp.screens.weather

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lab.weatherapp.R
import androidx.compose.ui.res.colorResource

class WeatherScreen {
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun Weather() {
        val currentPositionList = remember { mutableStateOf(0) }

        val lazyListState: LazyListState = rememberLazyListState()
        val snappingLayout = remember(lazyListState) { SnapLayoutInfoProvider(lazyListState) }
        val layoutInfo = remember { derivedStateOf { lazyListState.layoutInfo } }
        val itemsWeatherNow = listOf<@Composable () -> Unit>(
            { InfoAboutTemp() },
            { InfoAboutDay() })

        LaunchedEffect(lazyListState.isScrollInProgress &&
                remember {
                    derivedStateOf {
                        lazyListState.firstVisibleItemScrollOffset
                    }
                }.value == 0
        ) {
            currentPositionList.value = lazyListState.firstVisibleItemIndex
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            InfoAboutCard("Right now", "Sunny")
            LazyRow(
                state = lazyListState,
                flingBehavior = rememberSnapFlingBehavior(snappingLayout)
            ) {
                itemsIndexed(
                    itemsWeatherNow
                ) { index, item ->
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
    fun InfoAboutCard(name: String, info: String) {
        Text(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 25.dp, bottom = 0.dp),
            color = MaterialTheme.colors.onBackground,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            text = name
        )

        Text(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, bottom = 15.dp, top = 0.dp),
            color = MaterialTheme.colors.onBackground,
            fontSize = 22.sp,
            text = info
        )
    }

    @Composable
    fun InfoAboutTemp() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                ImageVector.vectorResource(R.drawable.sunny),
                null,
                modifier = Modifier
                    .padding(5.dp)
                    .size(150.dp)
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 0.dp),
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 70.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End,
                    text = "-10°"
                )

                Text(
                    modifier = Modifier.padding(top = 0.dp),
                    color = Color.Gray,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    text = "Feels like -15°"
                )
            }
        }
    }

    @Composable
    fun Card(content: @Composable () -> Unit = {}) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 10.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = 20.dp,
            backgroundColor = MaterialTheme.colors.background
        ) {
            content()
        }
    }

    @Composable
    fun CurrentPositionItemList(currentPosition: Int, totalPoints: Int) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            contentAlignment = Alignment.Center
        ) {
            Row {
                for (i in 0 until totalPoints) {
                    Icon(
                        ImageVector.vectorResource(R.drawable.point), null,
                        tint = if (i == currentPosition) Color.Gray else Color.LightGray,
                        modifier = Modifier.size(10.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                }
            }
        }
    }

    @Composable
    fun InfoAboutDay() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
                Row {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(colorResource(R.color.purple_700))
                            .size(30.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            ImageVector.vectorResource(R.drawable.arrow_right_up_icon), null,
                            tint = Color.White,
                            modifier = Modifier
                                .size(25.dp)
                        )
                    }
                    Text(
                        modifier = Modifier
                            .padding(start = 10.dp)
                        ,
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 18.sp,
                        text = "4-9m/s winds from the southwest"
                    )
                }

                Row {

                    Icon(
                        ImageVector.vectorResource(R.drawable.drop_water), null,
                        tint = Color.LightGray, modifier = Modifier.size(30.dp)
                    )

                    Text(
                        modifier = Modifier
                            .padding(start = 10.dp),
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 18.sp,
                        text = "Humidity 47%"
                    )
                }

                Row {

                    Icon(
                        ImageVector.vectorResource(R.drawable.sunset), null,
                        tint = colorResource(R.color.yellow), modifier = Modifier.size(30.dp)
                    )

                    Text(
                        modifier = Modifier
                            .padding(start = 10.dp),
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 18.sp,
                        text = "Sunrise 6:20 -> Sunset 19:33\n13.2 hours of daylight"
                    )
                }
            }
        }
    }
}