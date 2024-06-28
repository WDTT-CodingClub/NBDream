package kr.co.remote.mapper.calendar

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.calendar.HolidayData
import kr.co.remote.model.response.calendar.HolidayResponse

internal object HolidayRemoteMapper
    : Mapper<HolidayResponse, HolidayData> {
    override fun convert(param: HolidayResponse): HolidayData =
        with(param) {
            HolidayData(
                date = date,
                isHoliday = isHoliday,
                type = type,
                name = name
            )
        }
}
