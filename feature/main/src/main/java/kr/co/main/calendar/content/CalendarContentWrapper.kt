package kr.co.main.calendar.content

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kr.co.domain.entity.DiaryEntity
import kr.co.domain.entity.ScheduleEntity


internal sealed class CalendarContent {
    data class ScheduleContent (val schedule: ScheduleEntity) : CalendarContent()
    data class DiaryContent (val diary: DiaryEntity) : CalendarContent()

    companion object{
        fun create(schedule: ScheduleEntity) = ScheduleContent(schedule)
        fun create(diary: DiaryEntity) = DiaryContent(diary)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun CalendarContentWrapper(
    calendarContent: CalendarContent,
    modifier: Modifier = Modifier
) =
    when (calendarContent) {
        is CalendarContent.ScheduleContent ->
            ScheduleContent(
                schedule = calendarContent.schedule,
                modifier = modifier
            )
        is CalendarContent.DiaryContent ->
            DiaryContent(calendarContent.diary,
                modifier = modifier
            )
    }



