package kr.co.main.calendar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import kr.co.domain.entity.DiaryEntity
import kr.co.main.calendar.providers.FakeDiaryEntityProvider
import kr.co.ui.DreamCrop
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Alarm
import kr.co.ui.icon.dreamicon.ArrowLeft
import kr.co.ui.icon.dreamicon.Spinner
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.wdtt.nbdream.ui.main.calendar.FarmWorkCalendar
import kr.co.wdtt.nbdream.ui.main.calendar.content.CalendarContent
import kr.co.wdtt.nbdream.ui.main.calendar.content.CalendarContentWrapper


// TODO 재배 작물 목록 비어있을 때 처리

@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val calendarScreenState by viewModel.state.collectAsState()
    val calendarScreenInput = viewModel.input

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CalendarTopBar(
                userCrops = emptyList(),//calendarScreenState.userCrops,
                selectedCrop = null//calendarScreenState.selectedCrop
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                CalendarYearMonth(
                    year = calendarScreenState.year,
                    month = calendarScreenState.month,
                    onMonthSelect = { month: Int ->
                        calendarScreenInput.onSelectMonth(month)
                    }
                )
                HorizontalDivider()

                calendarScreenState.selectedCrop?.let {
                    FarmWorkCalendar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Paddings.xlarge),
                        farmWorks = calendarScreenState.farmWorks
                    )
                    HorizontalDivider()

                    //TODO 일정 캘린더 UI
                    //ScheduleCalendar(year = , month = , holidays = , schedules = , diaries = )

                    DiaryList(diaries = calendarScreenState.diaries)
                }
            }
        }
    }
}

@Composable
private fun CalendarTopBar(
    userCrops: List<DreamCrop>,
    selectedCrop: DreamCrop?,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.padding(vertical = Paddings.medium)) {
        CalendarDropDownTitle(
            modifier = Modifier.align(Alignment.Center),
            userCrops = userCrops,
            selectedCrop = selectedCrop
        )
        // TODO 알람 있는 경우, 초록색 뱃지 표시
        CalendarAlarm(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = Paddings.xlarge),
            newAlarm = true
        )
    }
}

@Composable
private fun CalendarDropDownTitle(
    userCrops: List<DreamCrop>,
    selectedCrop: DreamCrop?,
    modifier: Modifier = Modifier
) {
    // TODO 작물 선택 스피너
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (selectedCrop == null) {
            Text(
                modifier = Modifier,
                text = "stringResource(id = R.string.calendar_no_title)",
                style = MaterialTheme.typo.headerB
            )
        } else {
            Text(
                modifier = Modifier,
                text = "stringResource(id = selectedCrop.cropNameId)" +
                        " " +
                        "stringResource(id = R.string.calendar_title)",
                style = MaterialTheme.typo.headerB
            )
            Image(
                modifier = Modifier.padding(start = Paddings.medium),
                imageVector = DreamIcon.Spinner,
                contentDescription = ""
            )
        }
    }
}

// TODO 상단바에 있는 알람 - 다른 화면에서도 재사용할 수 있도록 바꾸기
@Composable
private fun CalendarAlarm(
    newAlarm: Boolean = false,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        if (newAlarm) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .align(Alignment.TopEnd)
                    .clip(shape = CircleShape)
                    .zIndex(1f)
            )
        }
        Image(
            modifier = Modifier.align(Alignment.Center),
            imageVector = DreamIcon.Alarm,
            contentDescription = ""
        )
    }
}

@Composable
private fun CalendarYearMonth(
    year: Int,
    month: Int,
    onMonthSelect: (month: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Image(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable { onMonthSelect(month - 1) },
            imageVector = DreamIcon.ArrowLeft,
            contentDescription = ""
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "${year}년 ${month}월",
            style = MaterialTheme.typo.header2M,
            color = MaterialTheme.colors.text1
        )
        Image(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable { onMonthSelect(month + 1) },
            imageVector = DreamIcon.ArrowLeft,
            contentDescription = ""
        )
    }
}

@Composable
private fun DiaryList(
    diaries: List<DiaryEntity>,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = Color.LightGray
    ) {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(Paddings.xlarge),
            verticalArrangement = Arrangement.spacedBy(Paddings.medium)
        ) {
            items(diaries, key = { it.id }) {
                DiaryItem(diary = it)
            }
        }
    }
}

@Composable
private fun DiaryItem(
    diary: DiaryEntity,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        CalendarContentWrapper(
            modifier = Modifier.padding(Paddings.xlarge),
            calendarContent = CalendarContent.create(diary)
        )
    }
}

@Preview
@Composable
private fun CalendarTopBarPreview() {
    Surface(modifier = Modifier.fillMaxWidth()) {
        CalendarTopBar(
            userCrops = listOf(
                DreamCrop.POTATO,
                DreamCrop.SWEET_POTATO,
                DreamCrop.TOMATO
            ).sortedBy { it.ranking },
            selectedCrop = DreamCrop.POTATO
        )
    }
}

@Preview
@Composable
private fun CalendarYearMonthPreview() {
    Surface(modifier = Modifier.fillMaxWidth()) {
        CalendarYearMonth(
            year = 2024,
            month = 6,
            onMonthSelect = { _ -> }
        )
    }
}

@Preview
@Composable
private fun DiaryListPreview(
    @PreviewParameter(FakeDiaryEntityProvider::class) diary: DiaryEntity
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        DiaryList(
            diaries = listOf(
                diary.copy(id = "1"),
                diary.copy(id = "2"),
                diary.copy(id = "3"),
                diary.copy(id = "4"),
                diary.copy(id = "5")
            )
        )
    }
}


