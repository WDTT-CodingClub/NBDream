package kr.co.data.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.HolidayData
import kr.co.domain.entity.HolidayEntity
import kr.co.domain.entity.type.HolidayType
import java.time.LocalDate
import java.time.format.DateTimeFormatter

internal object HolidayMapper :
    Mapper<HolidayData, HolidayEntity> {
    override fun convert(param: HolidayData): HolidayEntity =
        with(param) {
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
}