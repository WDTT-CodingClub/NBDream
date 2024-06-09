package kr.co.wdtt.nbdream.ui.main.calendar.common

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kr.co.wdtt.nbdream.domain.entity.ScheduleEntity
import kr.co.wdtt.nbdream.domain.entity.DiaryEntity


sealed class CalendarContent {
    data class ScheduleContent (val schedule: ScheduleEntity) : CalendarContent()
    data class DiaryContent (val diary: DiaryEntity) : CalendarContent()

    companion object{
        fun create(schedule: ScheduleEntity) = ScheduleContent(schedule)
        fun create(diary: DiaryEntity) = DiaryContent(diary)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarContentWrapper(
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



