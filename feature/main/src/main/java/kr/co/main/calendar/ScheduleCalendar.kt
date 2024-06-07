package kr.co.main.calendar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kr.co.domain.entity.DiaryEntity
import kr.co.domain.entity.HolidayEntity
import kr.co.domain.entity.ScheduleEntity

@Composable
internal fun ScheduleCalendar(
    year:Int,
    month:Int,
    holidays:List<HolidayEntity>,
    schedules:List<ScheduleEntity>,
    diaries:List<DiaryEntity>,
    modifier: Modifier = Modifier
) {
}