package kr.co.main.calendar.ui.calendarScreen.calendarScreen.scheduleTab

import FarmWorkCalendar
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import kr.co.main.R
import kr.co.main.calendar.model.CropModel
import kr.co.main.calendar.model.FarmWorkModel
import kr.co.main.calendar.model.HolidayModel
import kr.co.main.calendar.model.ScheduleModel
import kr.co.main.calendar.model.filterAndSortHolidays
import kr.co.main.calendar.ui.common.CalendarCategoryIndicator
import kr.co.main.calendar.ui.common.innerCalendar.InnerCalendar
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import timber.log.Timber
import java.time.LocalDate


@Composable
internal fun ScheduleTab(
    calendarCrop: CropModel?,
    calendarYear: Int,
    calendarMonth: Int,
    navToSearchDiary: (Int, Int, Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ScheduleTabViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val event = viewModel.event

    LaunchedEffect(calendarCrop, calendarYear, calendarMonth) {
        Timber.d("calendarCrop: $calendarCrop, calendarYear: $calendarYear, calendarMonth: $calendarMonth")
        calendarCrop?.let {
            event.setCalendarCrop(it)
        }
        event.setCalendarYear(calendarYear)
        event.setCalendarMonth(calendarMonth)
    }

    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.gray9
    ) {
        Column {
            FarmWorkCalendarCard(
                modifier = Modifier.padding(Paddings.large),
                calenderMonth = state.value.calendarMonth,
                farmWorks = state.value.farmWorks
            )
            InnerCalendarCard(
                modifier = Modifier.padding(Paddings.large),
                calendarCrop = state.value.calendarCrop,
                calendarYear = state.value.calendarYear,
                calendarMonth = state.value.calendarMonth,
                selectedDate = state.value.selectedDate,
                onSelectDate = event::onSelectDate,
                navToDiary = {
                    navToSearchDiary(
                        state.value.calendarCrop!!.type.nameId,
                        state.value.calendarYear,
                        state.value.calendarMonth
                    )
                }
            )

            ScheduleCard(
                modifier = Modifier.padding(Paddings.large),
                selectedDate = state.value.selectedDate,
                holidays = filterAndSortHolidays(
                    holidays = state.value.holidays,
                    date = state.value.selectedDate
                ),
                schedules = state.value.schedules
            )
        }
    }
}

@Composable
private fun FarmWorkCalendarCard(
    calenderMonth: Int,
    farmWorks: List<FarmWorkModel>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        FarmWorkCalendar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Paddings.large),
            calendarMonth = calenderMonth,
            farmWorks = farmWorks
        )
    }
}


@Composable
private fun InnerCalendarCard(
    calendarCrop: CropModel?,
    calendarYear: Int,
    calendarMonth: Int,
    selectedDate: LocalDate,
    onSelectDate: (LocalDate) -> Unit,
    navToDiary: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Paddings.xlarge)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Paddings.large)
                    .padding(bottom = Paddings.large)
            ) {
                CategoryIndicatorList(
                    modifier = Modifier.align(Alignment.CenterStart),
                    crop = calendarCrop
                )
                SearchDiaryButton(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    onClick = navToDiary
                )
            }

            InnerCalendar(
                calendarYear = calendarYear,
                calendarMonth = calendarMonth,
                selectedDate = selectedDate,
                onSelectDate = onSelectDate
            )
        }
    }
}

@Composable
private fun CategoryIndicatorList(
    crop: CropModel?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Paddings.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        crop?.let {
            CategoryIndicatorListItem(
                cropNameId = it.type.nameId,
                cropColor = it.color.color
            )
        }
        CategoryIndicatorListItem(
            cropNameId = R.string.feature_main_calendar_category_all,
            cropColor = Color.Gray.toArgb()
        )
    }
}

@Composable
private fun CategoryIndicatorListItem(
    @StringRes cropNameId: Int,
    @ColorInt cropColor: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CalendarCategoryIndicator(
            modifier = Modifier.padding(end = Paddings.small),
            categoryColor = cropColor
        )
        Text(
            text = stringResource(id = cropNameId),
            style = MaterialTheme.typo.labelM,
            color = MaterialTheme.colors.text1
        )
    }
}

@Composable
private fun SearchDiaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO 영농일지 검색 화면 이동을 위한 임시 버튼
    Button(
        onClick = onClick
    ) {
        Text("영농일지 검색 화면")
    }
}


@Composable
private fun ScheduleCard(
    selectedDate: LocalDate,
    holidays: List<HolidayModel>,
    schedules: List<ScheduleModel>,
    modifier: Modifier = Modifier
) {

}