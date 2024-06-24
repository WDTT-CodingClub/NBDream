package kr.co.main.model.calendar

import kr.co.domain.entity.type.HolidayType
import java.time.LocalDate

internal data class HolidayModel(
    val date: LocalDate,
    val isHoliday: Boolean,
    val type: HolidayType,
    val name: String,
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
