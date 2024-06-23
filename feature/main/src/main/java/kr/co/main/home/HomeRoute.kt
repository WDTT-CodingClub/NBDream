package kr.co.main.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.co.main.home.HomeViewModel.State.WeatherDetail
import kr.co.main.home.HomeViewModel.State.WeatherSimple
import kr.co.main.model.home.WeatherMetrics
import kr.co.ui.ext.noRippleClickable
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Bell
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamTopAppBar

@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToNotification: () -> Unit = {},
    navigateToChat: () -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        navigateToNotification = navigateToNotification,
        navigateToChat = navigateToChat
    )
}

@Composable
private fun HomeScreen(
    state: HomeViewModel.State = HomeViewModel.State(),
    navigateToNotification: () -> Unit = {},
    navigateToChat: () -> Unit = {},
) {
    var maxWidth by remember {
        mutableIntStateOf(0)
    }

    Surface(
        color = MaterialTheme.colors.gray9,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                DreamTopAppBar(
                    title = "내 농장",
                    description = state.address ?: "산 좋고 물 좋 나만의 농장 1번지",
                    actions = {
                        IconButton(onClick = navigateToNotification) {
                            Icon(
                                imageVector = DreamIcon.Bell,
                                contentDescription = "notification"
                            )
                        }
                    }
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colors.primary,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(
                            horizontal = 24.dp,
                            vertical = 18.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "내 토지 상태 알아보기",
                        style = MaterialTheme.typo.button,
                        color = MaterialTheme.colors.gray10,
                    )
                    Text(
                        modifier = Modifier
                            .noRippleClickable(onClick = navigateToChat),
                        text = "챗봇 연결 >",
                        style = MaterialTheme.typo.body2,
                        color = MaterialTheme.colors.gray10
                    )
                }
            }

            item {
                WeatherCard(
                    todayWeather = state.todayWeather,
                    weatherList = state.weatherList,
                )
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colors.white,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(24.dp),
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = MaterialTheme.typo.h4.copy(
                                    color = MaterialTheme.colors.gray1
                                ).toSpanStyle()
                            ) {
                                append("5월 24일 일 ")
                            }
                            withStyle(
                                style = MaterialTheme.typo.body1.copy(
                                    color = MaterialTheme.colors.gray2
                                ).toSpanStyle()
                            ) {
                                append(" 소만 ")
                            }
                            append(" 오늘")
                        },
                        style = MaterialTheme.typo.body1,
                        color = MaterialTheme.colors.primary
                    )
                    Spacer(modifier = Modifier.height(36.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned {
                                maxWidth = it.size.width
                            },
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        val dummy = listOf(
                            Triple("감자 물 관리 작업", "2024.05.16", "2024.05.21"),
                            Triple("감자 물 관리 작업", "2024.05.16", "2024.05.21"),
                            Triple("감자 물 관리 작업 감자 물 관리 작업", "2024.05.16", "2024.05.21"),
                        )
                        dummy.forEachIndexed { index, value ->
                            ScheduleText(
                                parentWidth = maxWidth,
                                title = value.first,
                                startDate = value.second,
                                endDate = value.third
                            )
                            if (index != dummy.lastIndex)
                                HorizontalDivider(
                                    thickness = 1.dp,
                                    color = MaterialTheme.colors.gray8
                                )
                        }
                    }
                    Spacer(modifier = Modifier.height(48.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "펼쳐보기",
                            style = MaterialTheme.typo.label,
                            color = MaterialTheme.colors.gray3
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = Icons.Filled.KeyboardArrowDown,
                            contentDescription = "expand"
                        )
                    }
                }
            }
        }
    }

}

@Composable
private fun WeatherCard(
    todayWeather: WeatherDetail,
    weatherList: List<WeatherSimple>?,
    modifier: Modifier = Modifier,
) {
    var weatherColumnHeight by remember {
        mutableIntStateOf(0)
    }
    var expanded by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.white,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(
                horizontal = 51.dp
            )
            .padding(
                top = 40.dp,
                bottom = 24.dp
            )
            .animateContentSize(
                animationSpec = TweenSpec(
                    durationMillis = 300
                )
            ),
        verticalArrangement = Arrangement.spacedBy(48.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(
                            width = 104.dp,
                            height = 69.33.dp
                        ),
                    painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.img_sunny),
                    contentDescription = "weather state"
                )

                Column(
                    modifier = Modifier
                        .padding(start = 24.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${todayWeather.temperature}°",
                        style = MaterialTheme.typo.weather,
                        color = MaterialTheme.colors.gray1
                    )
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${todayWeather.maxTemperature}°",
                            style = MaterialTheme.typo.body2,
                            color = MaterialTheme.colors.red
                        )
                        Icon(
                            modifier = Modifier
                                .size(8.dp)
                                .rotate(270f),
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "up and down temp",
                            tint = MaterialTheme.colors.red
                        )
                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "${todayWeather.minTemperature}°",
                            style = MaterialTheme.typo.body2,
                            color = MaterialTheme.colors.gray4
                        )
                        Icon(
                            modifier = Modifier
                                .size(8.dp)
                                .rotate(90f),
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "up and down temp",
                            tint = MaterialTheme.colors.gray4
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                WeatherMetrics.entries.forEachIndexed { index, value ->
                    WeatherState(
                        modifier = Modifier.onGloballyPositioned {
                            weatherColumnHeight = it.size.height - 48
                        },
                        title = value.label,
                        measure = when (value) {
                            WeatherMetrics.Probability -> todayWeather.probability ?: 0
                            WeatherMetrics.Precipitation -> todayWeather.precipitation ?: 0
                            WeatherMetrics.Humidity -> todayWeather.humidity ?: 0
                            WeatherMetrics.Wind -> todayWeather.wind ?: 0
                        },
                        unit = value.unit
                    )

                    if (index < 3)
                        VerticalDivider(
                            modifier = Modifier
                                .height(weatherColumnHeight.dp)
                                .padding(vertical = 7.dp),
                            thickness = 1.dp,
                            color = MaterialTheme.colors.gray8
                        )
                }
            }
        }

        if (expanded) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf(1, 2, 3, 4, 5, 6, 7).forEach {
                    Image(
                        modifier = Modifier.width(32.dp),
                        painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.img_sunny),
                        contentDescription = "weather state"
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable(onClick = { expanded = !expanded })
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (!expanded) "펼쳐보기" else "접기",
                style = MaterialTheme.typo.label,
                color = MaterialTheme.colors.gray3
            )

            Spacer(modifier = Modifier.width(4.dp))

            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = if (!expanded) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
                contentDescription = "expand",
                tint = MaterialTheme.colors.gray5
            )
        }
    }
}

@Composable
private fun ScheduleText(
    parentWidth: Int,
    title: String,
    startDate: String,
    endDate: String,
) {
    val textMeasurer = rememberTextMeasurer()

    val measuredWidth = textMeasurer.measure(
        text = title,
        style = MaterialTheme.typo.body1
    ).size.width + textMeasurer.measure(
        text = "$startDate ~ $endDate",
        style = MaterialTheme.typo.body2
    ).size.width

    val shouldWrap = measuredWidth < parentWidth

    Text(
        modifier = Modifier,
        text = buildAnnotatedString {
            withStyle(
                style = MaterialTheme.typo.body1.copy(
                    color = MaterialTheme.colors.gray1
                ).toSpanStyle()
            ) {
                append(title)
            }
            append(if (shouldWrap) "  " else "\n")
            append("$startDate ~ $endDate")
        },
        style = MaterialTheme.typo.body2,
        color = MaterialTheme.colors.gray5
    )
}

@Composable
private fun WeatherState(
    title: String,
    measure: Int,
    unit: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typo.body2,
            color = MaterialTheme.colors.gray4
        )
        Text(
            text = "$measure$unit",
            style = MaterialTheme.typo.body2,
            color = MaterialTheme.colors.gray4
        )
    }
}

@Preview
@Composable
private fun Preview() {
    NBDreamTheme {
        HomeScreen()
    }
}