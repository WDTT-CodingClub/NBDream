package kr.co.wdtt.nbdream.ui.main.calendar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kr.co.wdtt.nbdream.domain.entity.DiaryEntity
import kr.co.wdtt.nbdream.domain.entity.HolidayEntity
import kr.co.wdtt.nbdream.domain.entity.ScheduleEntity

@Composable
fun ScheduleCalendar(
    year:Int,
    month:Int,
    holidays:List<HolidayEntity>,
    schedules:List<ScheduleEntity>,
    diaries:List<DiaryEntity>,
    modifier: Modifier = Modifier
) {
}