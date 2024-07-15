package kr.co.main.calendar.screen.calendarScreen

import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import kr.co.main.R
import kr.co.main.calendar.common.CalendarCategoryIndicator
import kr.co.main.calendar.common.content.ScheduleContent
import kr.co.main.calendar.screen.calendarScreen.calendar.FarmWorkCalendar
import kr.co.main.calendar.screen.calendarScreen.calendar.ScheduleCalendar
import kr.co.main.model.calendar.CropModel
import kr.co.main.model.calendar.FarmWorkModel
import kr.co.main.model.calendar.HolidayModel
import kr.co.main.model.calendar.ScheduleModel
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import timber.log.Timber
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
internal fun ScheduleTab(
    calendarCrop: CropModel?,
    calendarYear: Int,
    calendarMonth: Int,
    selectedDate: LocalDate,
    onDateSelect: (LocalDate) -> Unit,
    farmWorks: List<FarmWorkModel>,
    holidays: List<HolidayModel>,
    allSchedules: List<ScheduleModel>,
    cropSchedules: List<ScheduleModel>,
    onEditClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            if (calendarCrop == null) {
                FarmWorkCropEmptyCard(
                    modifier = Modifier.padding(Paddings.large),
                )
            } else {
                FarmWorkCalendarCard(
                    modifier = Modifier.padding(Paddings.large),
                    calenderMonth = calendarMonth,
                    farmWorks = farmWorks
                )
            }

            ScheduleCalendarCard(
                modifier = Modifier.padding(Paddings.large),
                calendarCrop = calendarCrop,
                calendarYear = calendarYear,
                calendarMonth = calendarMonth,
                selectedDate = selectedDate,
                onDateSelect = onDateSelect,
                holidays = holidays,
                allSchedules = allSchedules,
                cropSchedules = cropSchedules
            )

            ScheduleCard(
                modifier = Modifier.padding(Paddings.large),
                date = selectedDate,
                schedules =
                allSchedules.filter { selectedDate in (it.startDate..it.endDate) } +
                        cropSchedules.filter { selectedDate in (it.startDate..it.endDate) },
                holidays = holidays.filter { selectedDate == it.date },
                onEditClick = onEditClick
            )
        }
    }
}

@Composable
private fun FarmWorkCropEmptyCard(
    modifier: Modifier = Modifier
) {
    // TODO 클릭 시 온보딩 작물 선택 화면으로 이동
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Paddings.xxlarge),
            text = stringResource(id = R.string.feature_main_calendar_farm_work_crop_empty),
            style = MaterialTheme.typo.body2,
            color = MaterialTheme.colors.gray4,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun FarmWorkCalendarCard(
    calenderMonth: Int,
    farmWorks: List<FarmWorkModel>,
    modifier: Modifier = Modifier
) {
    Timber.d("FarmWorkCalendarCard) farmWorks: $farmWorks")

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
private fun ScheduleCalendarCard(
    calendarCrop: CropModel?,
    calendarYear: Int,
    calendarMonth: Int,
    selectedDate: LocalDate,
    onDateSelect: (LocalDate) -> Unit,
    holidays: List<HolidayModel>,
    allSchedules: List<ScheduleModel>,
    cropSchedules: List<ScheduleModel>,
    modifier: Modifier = Modifier
) {
    Timber.d("ScheduleCalendarCard) year: $calendarYear, month: $calendarMonth, selectedDate: $selectedDate")

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
            CategoryIndicatorList(
                modifier = Modifier.padding(start = Paddings.xlarge),
                crop = calendarCrop
            )
            ScheduleCalendar(
                modifier = Modifier.padding(Paddings.xlarge),
                calendarYear = calendarYear,
                calendarMonth = calendarMonth,
                selectedDate = selectedDate,
                onDateSelect = onDateSelect,
                holidays = holidays,
                allSchedules = allSchedules,
                cropSchedules = cropSchedules
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
        CategoryIndicatorListItem(
            cropNameId = R.string.feature_main_calendar_category_all,
            cropColor = Color.Gray.toArgb()
        )
        crop?.let {
            CategoryIndicatorListItem(
                cropNameId = it.type.nameId,
                cropColor = it.color.color
            )
        }
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
private fun ScheduleCard(
    date: LocalDate,
    schedules: List<ScheduleModel>,
    holidays: List<HolidayModel>,
    onEditClick: (Long) -> Unit,
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
                .padding(Paddings.xlarge)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Paddings.medium)
            ) {
                Text(
                    text = "${date.monthValue}월 ${date.dayOfMonth}일 " +
                            date.dayOfWeek.getDisplayName(
                                TextStyle.SHORT,
                                Locale.KOREAN
                            ),
                    style = MaterialTheme.typo.h4
                )
                if (date == LocalDate.now()) {
                    Text(
                        text = "오늘",
                        style = MaterialTheme.typo.body1,
                        color = MaterialTheme.colors.primary
                    )
                }
                for (holiday in holidays) {
                    holidays.sortedBy { it.type.priority }.forEach {
                        Text(
                            text = it.name,
                            style = MaterialTheme.typo.body1,
                            color =
                            if (it.isHoliday) MaterialTheme.colors.red1
                            else MaterialTheme.colors.text2
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(Paddings.xlarge))
            Column {
                for (schedule in schedules) {
                    ScheduleContent(
                        modifier = Modifier.padding(Paddings.medium),
                        onEditClick = { onEditClick(schedule.id) },
                        schedule = schedule
                    )
                }
            }
        }
    }
}