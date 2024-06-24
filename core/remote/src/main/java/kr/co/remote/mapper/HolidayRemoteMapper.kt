package kr.co.remote.mapper

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.co.common.mapper.Mapper
import kr.co.data.model.data.HolidayData
import kr.co.remote.model.response.calendar.HolidayResponse

internal object HolidayRemoteMapper
    : Mapper<HolidayResponse, Flow<HolidayData>> {
    override fun convert(param: HolidayResponse): Flow<HolidayData> = flow {
        with(param.response.body.items.item) {
            map {
                emit(
                    HolidayData(
                        date = it.date,
                        isHoliday = it.isHoliday == "Y",
                        type = it.dateType,
                        name = it.dateName
                    )
                )
            }
        }
    }
}