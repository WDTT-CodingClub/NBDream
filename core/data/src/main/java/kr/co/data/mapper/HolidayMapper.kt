package kr.co.data.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.HolidayResult
import kr.co.domain.entity.HolidayEntity
import kr.co.domain.entity.HolidayEntity.Type
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

    private fun mapHolidayType(dateType: String, isHoliday: Boolean): Type =
        when (dateType) {
            "01" -> if (isHoliday) Type.NATIONAL_HOLIDAY else Type.CONSTITUTION_DAY
            "02" -> Type.ANNIVERSARY
            "03" -> Type.SOLAR_TERM
            else -> Type.ETC
        }
}