package kr.co.domain.entity

import kr.co.domain.entity.HolidayEntity
import kr.co.domain.entity.ScheduleEntity
import java.time.DayOfWeek

data class CalendarDateEntity (
    val year:Int,
    val month:Int,
    val day: Int,
    val dayOfWeek: DayOfWeek,
    val weekNo: Int,
    var holidayList: List<HolidayEntity> = emptyList(),
    var scheduleList: List<ScheduleEntity> = emptyList(),
    var workDiaryId: String?=null
){
    private val isWorkDiaryAdded: Boolean get()= (workDiaryId != null)
}