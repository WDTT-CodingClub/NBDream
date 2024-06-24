package kr.co.remote.mapper.calendar

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.calendar.DiaryData
import kr.co.remote.model.response.calendar.DiaryListResponse

internal object DiaryRemoteMapper
    : Mapper<DiaryListResponse.DiaryResponse, DiaryData> {
    override fun convert(param: DiaryListResponse.DiaryResponse): DiaryData =
        with(param) {
            DiaryData(
                id = id,
                date = date,
                holidayList = holidayList.map { HolidayRemoteMapper.convert(it) },
                weatherForecast = weatherForecast,
                workLaborer = workLaborer,
                workHours = workHours,
                workArea = workArea,
                workDescriptions = workDescriptions.map {
                    WorkDescriptionRemoteMapper.convert(it)
                },
                memo = memo
            )
        }

    object WorkDescriptionRemoteMapper
        : Mapper<
            DiaryListResponse.DiaryResponse.WorkDescriptionResponse,
            DiaryData.WorkDescriptionData
            > {
        override fun convert(
            param: DiaryListResponse.DiaryResponse.WorkDescriptionResponse
        ): DiaryData.WorkDescriptionData =
            with(param) {
                DiaryData.WorkDescriptionData(
                    id = id,
                    type = type,
                    description = description
                )
            }
    }
}
