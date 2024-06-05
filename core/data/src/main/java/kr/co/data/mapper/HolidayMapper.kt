package kr.co.data.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.HolidayResult
import kr.co.domain.entity.HolidayEntity
import kr.co.domain.entity.HolidayEntity.HolidayType
import java.time.LocalDate
import java.time.format.DateTimeFormatter

internal object HolidayMapper :
    Mapper<List<HolidayResult>, List<HolidayEntity>> {
    override fun convert(param: List<HolidayResult>): List<HolidayEntity> =
        param.map {
            HolidayEntity(
                date = LocalDate.parse(it.date.toString(), DateTimeFormatter.ofPattern("yyyyMMdd")),
                isHoliday = it.isHoliday,
                type = mapHolidayType(it.type, it.isHoliday),
                name = it.name,
            )
        }

    private fun mapHolidayType(dateType: String, isHoliday: Boolean): HolidayType =
        when (dateType) {
            "01" -> if (isHoliday) HolidayType.NATIONAL_HOLIDAY else HolidayType.CONSTITUTION_DAY
            "02" -> HolidayType.ANNIVERSARY
            "03" -> HolidayType.SOLAR_TERM
            else -> HolidayType.ETC
        }
}