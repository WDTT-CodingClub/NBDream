package kr.co.main.calendar.common

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kr.co.main.calendar.model.DiaryModel
import kr.co.main.calendar.model.ScheduleModel


internal sealed class CalendarContent {
    data class ScheduleContent(val schedule: ScheduleModel) : CalendarContent()
    data class DiaryContent(val diary: DiaryModel) : CalendarContent()

    companion object {
        fun create(schedule: ScheduleModel) = ScheduleContent(schedule)
        fun create(diary: DiaryModel) = DiaryContent(diary)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun CalendarContentWrapper(
    calendarContent: CalendarContent,
    modifier: Modifier = Modifier
) = when (calendarContent) {
    is CalendarContent.ScheduleContent ->
        ScheduleContent(
            schedule = calendarContent.schedule,
            modifier = modifier
        )

    is CalendarContent.DiaryContent ->
        DiaryContent(
            calendarContent.diary,
            modifier = modifier
        )
}



