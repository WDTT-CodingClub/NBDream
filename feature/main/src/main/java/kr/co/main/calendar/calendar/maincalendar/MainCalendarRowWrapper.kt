package kr.co.main.calendar.calendar.maincalendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Constraints
import kr.co.domain.entity.HolidayEntity
import kr.co.main.calendar.model.DiaryModel
import kr.co.main.calendar.model.ScheduleModel
import java.time.DayOfWeek
import java.time.LocalDate


internal sealed class MainCalendarRowContent{
    abstract val weekStartDate: LocalDate
    abstract val weekEndDate: LocalDate

    data class DateRowContent(
        override val weekStartDate: LocalDate,
        override val weekEndDate: LocalDate,
        val holidays: List<HolidayEntity>
    ): MainCalendarRowContent()

    data class HolidayRowContent(
        override val weekStartDate: LocalDate,
        override val weekEndDate: LocalDate,
        val holidays: List<HolidayEntity>
    ): MainCalendarRowContent()
    data class ScheduleRowContent(
        override val weekStartDate: LocalDate,
        override val weekEndDate: LocalDate,
        val schedules: List<ScheduleModel>
    ): MainCalendarRowContent()
    data class DiaryRowContent(
        override val weekStartDate: LocalDate,
        override val weekEndDate: LocalDate,
        val diaries: List<DiaryModel>
    ): MainCalendarRowContent()

}

@Composable
internal fun MainCalendarRowWrapper(
    mainCalendarRowContent: MainCalendarRowContent,
    modifier: Modifier = Modifier
){
    when(mainCalendarRowContent){
        is MainCalendarRowContent.DateRowContent -> MainCalendarDateRow(
            modifier = modifier,
            weekStartDate = mainCalendarRowContent.weekStartDate,
            weekEndDate = mainCalendarRowContent.weekEndDate,
            holidays = mainCalendarRowContent.holidays
        )
        is MainCalendarRowContent.HolidayRowContent -> MainCalendarHolidayRow(
            modifier = modifier,
            weekStartDate = mainCalendarRowContent.weekStartDate,
            weekEndDate = mainCalendarRowContent.weekEndDate,
            holidays = mainCalendarRowContent.holidays
        )
        is MainCalendarRowContent.ScheduleRowContent -> MainCalendarScheduleRow(
            modifier = modifier,
            weekStartDate = mainCalendarRowContent.weekStartDate,
            weekEndDate = mainCalendarRowContent.weekEndDate,
            schedules = mainCalendarRowContent.schedules
        )
        is MainCalendarRowContent.DiaryRowContent -> MainCalendarDiaryRow(
            modifier = modifier,
            weekStartDate = mainCalendarRowContent.weekStartDate,
            weekEndDate = mainCalendarRowContent.weekEndDate,
            diaries = mainCalendarRowContent.diaries
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
internal fun getXPosition(dayOfWeek: DayOfWeek, constraints: Constraints) =
    when (dayOfWeek) {
        DayOfWeek.SUNDAY -> 0
        DayOfWeek.MONDAY -> constraints.maxWidth / 7
        DayOfWeek.TUESDAY -> constraints.maxWidth / 7 * 2
        DayOfWeek.WEDNESDAY -> constraints.maxWidth / 7 * 3
        DayOfWeek.THURSDAY -> constraints.maxWidth / 7 * 4
        DayOfWeek.FRIDAY -> constraints.maxWidth / 7 * 5
        DayOfWeek.SATURDAY -> constraints.maxWidth / 7 * 6
    }