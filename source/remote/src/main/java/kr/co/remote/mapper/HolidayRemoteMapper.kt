package kr.co.remote.mapper

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.co.common.mapper.Mapper
import kr.co.data.model.data.HolidayResult
import kr.co.remote.model.response.HolidayResponse

internal object HolidayRemoteMapper
    : Mapper<HolidayResponse, Flow<HolidayResult>> {
    override fun convert(param: HolidayResponse): Flow<HolidayResult> = flow {
        with(param.response.body.items.item) {
            map {
                emit(
                    HolidayResult(
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