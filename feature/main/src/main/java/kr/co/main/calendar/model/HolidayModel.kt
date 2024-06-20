package kr.co.main.calendar.model

import kr.co.domain.entity.HolidayEntity
import java.time.LocalDate

data class HolidayModel(
    val date: LocalDate,
    val isHoliday: Boolean,
    val type: HolidayEntity.Type,
    val name: String,
)

internal fun HolidayEntity.convert() = HolidayModel(
    date = date,
    isHoliday = isHoliday,
    type = type,
    name = name
)

internal fun HolidayModel.convert() = HolidayEntity(
    date = date,
    isHoliday = isHoliday,
    type = type,
    name = name
)

internal fun filterAndSortHolidays(
    holidays: List<HolidayModel>,
    dateRange: ClosedRange<LocalDate>
) = holidays
    .filter { it.date in dateRange }
    .filter { it.isHoliday }
    .sortedWith(compareBy({ it.date }, { it.type.priority }))

internal fun filterAndSortHolidays(
    holidays: List<HolidayModel>,
    date: LocalDate
) = holidays
    .filter { it.date == date }
    .filter { it.isHoliday }
    .sortedWith(compareBy({ it.date }, { it.type.priority }))
