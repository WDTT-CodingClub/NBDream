package kr.co.main.mapper.calendar

import kr.co.common.mapper.BaseMapper
import kr.co.domain.entity.HolidayEntity
import kr.co.main.model.calendar.HolidayModel

internal object HolidayModelMapper
    : BaseMapper<HolidayEntity, HolidayModel>() {
    override fun toRight(left: HolidayEntity): HolidayModel = with(left) {
        HolidayModel(
            date = date,
            isHoliday = isHoliday,
            type = type,
            name = name
        )
    }

    override fun toLeft(right: HolidayModel): HolidayEntity = with(right) {
        HolidayEntity(
            date = date,
            isHoliday = isHoliday,
            type = type,
            name = name
        )
    }
}