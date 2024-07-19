package kr.co.remote.mapper.calendar

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.calendar.ScheduleData
import kr.co.remote.model.response.calendar.ScheduleListResponse

internal object ScheduleRemoteMapper
    : Mapper<ScheduleListResponse.ScheduleResponse, ScheduleData> {

    override fun convert(param: ScheduleListResponse.ScheduleResponse): ScheduleData =
        with(param) {
            ScheduleData(
                id = id,
                category = category,
                title = title,
                startDate = startDate,
                endDate = endDate,
                memo = memo,
                alarmOn = alarmOn,
                alarmDateTime = alarmDateTime?:""
            )
        }
}