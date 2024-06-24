package kr.co.remote.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.HolidayData
import kr.co.remote.model.response.calendar.HolidayListResponse

internal object HolidayRemoteMapper
    : Mapper<HolidayListResponse.HolidayResponse, HolidayData> {
    override fun convert(param: HolidayListResponse.HolidayResponse): HolidayData =
        with(param) {
            HolidayData(
                date = date,
                isHoliday = isHoliday,
                type = type,
                name = name
            )
        }
}
