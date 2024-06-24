package kr.co.data.mapper

import kr.co.common.mapper.BaseMapper
import kr.co.data.model.data.calendar.HolidayData
import kr.co.domain.entity.HolidayEntity
import kr.co.domain.entity.type.HolidayType
import java.time.LocalDate
import java.time.format.DateTimeFormatter

internal object HolidayMapper :
    BaseMapper<HolidayData, HolidayEntity>() {
    override fun toRight(left: HolidayData): HolidayEntity =
        with(left) {
            HolidayEntity(
                date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd")),
                isHoliday = when (isHoliday) {
                    "Y" -> true
                    "N" -> false
                    else -> throw IllegalArgumentException("Unknown isHoliday")
                },
                type = HolidayType.ofValue(type),
                name = name,
            )
        }

    override fun toLeft(right: HolidayEntity): HolidayData =
        with(right){
            HolidayData(
                date = date.format(DateTimeFormatter.ofPattern("yyyyMMdd")),
                isHoliday = if (isHoliday) "Y" else "N",
                type = type.typeString,
                name = name
            )
        }
}