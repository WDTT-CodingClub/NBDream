package kr.co.main.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.co.main.home.HomeViewModel.State.WeatherDetail
import kr.co.main.home.HomeViewModel.State.WeatherSimple
import kr.co.main.mapper.home.WeatherSkyMapper
import kr.co.main.model.home.WeatherMetrics
import kr.co.ui.ext.noRippleClickable
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Bell
import kr.co.ui.icon.dreamicon.Bellon
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamTopAppBar
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToNotification: () -> Unit = {},
    navigateToAddress: () -> Unit = {},
    navigateToChat: () -> Unit = {},
    navigateToCalendar: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LifecycleResumeEffect(Unit) {
        viewModel.refresh()

        onPauseOrDispose {  }
    }

    HomeScreen(
        state = state,
        navigateToNotification = navigateToNotification,
        navigateToAddress = navigateToAddress,
        navigateToChat = navigateToChat,
        navigateToCalendar = navigateToCalendar
    )
}

@Composable
private fun HomeScreen(
    state: HomeViewModel.State = HomeViewModel.State(),
    navigateToNotification: () -> Unit = {},
    navigateToAddress: () -> Unit = {},
    navigateToChat: () -> Unit = {},
    navigateToCalendar: () -> Unit = {},
) {
    Surface(
        color = MaterialTheme.colors.background,
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
                    description = state.address.let {
                        if (it.isNullOrBlank()) "농장 주소 설정하러 가기" else it
                    },
                    descriptionAction = navigateToAddress,
                    actions = {
                        IconButton(onClick = navigateToNotification) {
                            Icon(
                                imageVector = if (state.hasCheckedAlarm) DreamIcon.Bell else DreamIcon.Bellon,
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
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            color = MaterialTheme.colors.primary,
                        )
                        .clickable(onClick = navigateToChat)
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
                        modifier = Modifier,
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
                        .padding(24.dp)
                        .animateContentSize(
                            animationSpec = TweenSpec(
                                durationMillis = 500
                            )
                        ),
                ) {
                    if (state.schedules.isEmpty()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .noRippleClickable(onClick = navigateToCalendar)
                                .padding(12.dp)
                                .semantics {
                                    contentDescription = "일정 작성하러 가기"
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                modifier = Modifier.clearAndSetSemantics {},
                                text = "일정을 적어주세요!",
                                style = MaterialTheme.typo.body1,
                                color = MaterialTheme.colors.gray4
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Icon(
                                modifier = Modifier.clearAndSetSemantics {},
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = null,
                                tint = MaterialTheme.colors.grey5
                            )
                        }
                    } else {
                        ScheduleCard(
                            schedules = state.schedules
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

}

@Composable
private fun ScheduleCard(
    schedules: List<HomeViewModel.State.Schedule>
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    schedules.groupBy { it.startDate }.apply {
        firstNotNullOf { (startDate, schedules) ->
                if (schedules.size < 3) {
                    ScheduleContents(
                        startDate = startDate,
                        isToday = true,
                        schedules = schedules
                    )
                } else {
                    ScheduleContents(
                        startDate = startDate,
                        isToday = true,
                        schedules = if (expanded.not()) schedules.take(3) else schedules
                    )
                }
        }
    }.entries.drop(1).forEach { (startDate, schedules) ->

        if (expanded) {
            Spacer(modifier = Modifier.height(48.dp))

            ScheduleContents(
                startDate = startDate,
                schedules = schedules
            )
        }
    }

    Spacer(modifier = Modifier.height(48.dp))

    if (schedules.size > 3) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable(onClick = { expanded = !expanded }),
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
private fun ScheduleContents(
    startDate: LocalDate,
    isToday: Boolean = false,
    schedules: List<HomeViewModel.State.Schedule>,
) {
    var maxWidth by remember {
        mutableIntStateOf(0)
    }
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = MaterialTheme.typo.h4.copy(
                    color = MaterialTheme.colors.gray1
                ).toSpanStyle()
            ) {
                startDate.apply {
                    append(
                        "${monthValue}월 ${dayOfMonth}일 ${
                            dayOfWeek.getDisplayName(
                                TextStyle.SHORT,
                                Locale.KOREAN
                            )
                        } "
                    )
                }
            }
            withStyle(
                style = MaterialTheme.typo.body1.copy(
                    color = MaterialTheme.colors.gray2
                ).toSpanStyle()
            ) {
                append("")
            }
            if (isToday) append(" 오늘")
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
        schedules.forEachIndexed { index, schedule ->
            ScheduleText(
                parentWidth = maxWidth,
                title = schedule.title,
                startDate = schedule.startDate.toString(),
                endDate = schedule.endDate.toString()
            )
            if (index < schedules.lastIndex)
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colors.gray8
                )
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
                Icon(
                    modifier = Modifier
                        .size(
                            width = 104.dp,
                            height = 69.33.dp
                        ),
                    imageVector = todayWeather.weather.let(WeatherSkyMapper::convert),
                    contentDescription = "weather state",
                    tint = Color.Unspecified
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
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                WeatherMetrics.entries.forEachIndexed { index, value ->
                    WeatherState(
                        modifier = Modifier.onGloballyPositioned {
                            weatherColumnHeight = it.size.height - 48
                        },
                        title = value.label,
                        measure = when (value) {
                            WeatherMetrics.Probability -> todayWeather.probability
                            WeatherMetrics.Precipitation -> todayWeather.precipitation
                            WeatherMetrics.Humidity -> todayWeather.humidity
                            WeatherMetrics.Wind -> todayWeather.wind
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
            Column {
                Text(
                    text = "주간 날씨",
                    style = MaterialTheme.typo.h4,
                    color = MaterialTheme.colors.gray1
                )

                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    weatherList?.forEachIndexed { index, it ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = it.day.dayOfWeek.getDisplayName(
                                    TextStyle.FULL,
                                    Locale.KOREAN
                                ),
                                style = MaterialTheme.typo.body1.copy(
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 16.sp,
                                    lineHeight = 20.sp,
                                ),
                                color = MaterialTheme.colors.gray1
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Icon(
                                modifier = Modifier.size(36.dp),
                                imageVector = it.weather.let(WeatherSkyMapper::convert),
                                contentDescription = it.weather,
                                tint = Color.Unspecified
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Text(
                                text = "${it.maxTemperature}°",
                                style = MaterialTheme.typo.body1.copy(
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 16.sp,
                                    lineHeight = 20.em,
                                ),
                                color = MaterialTheme.colors.red
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Text(
                                text = "${it.minTemperature}°",
                                style = MaterialTheme.typo.body1.copy(
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 16.sp,
                                    lineHeight = 20.em,
                                ),
                                color = MaterialTheme.colors.gray4
                            )

                        }
                        if (index != weatherList.lastIndex)
                            HorizontalDivider(
                                thickness = 1.dp,
                                color = MaterialTheme.colors.gray8
                            )
                    }
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