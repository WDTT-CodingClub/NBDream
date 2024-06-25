package kr.co.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kr.co.data.model.data.calendar.DiaryData
import kr.co.data.model.data.calendar.HolidayData
import kr.co.data.source.remote.DiaryRemoteDataSource
import kr.co.remote.mapper.calendar.DiaryRemoteMapper
import kr.co.remote.model.Dto
import kr.co.remote.model.request.calendar.PostDiaryRequest
import kr.co.remote.model.request.calendar.UpdateDiaryRequest
import kr.co.remote.model.response.calendar.DiaryListResponse
import javax.inject.Inject

internal class DiaryRemoteDataSourceImpl @Inject constructor(
    private val client: HttpClient
) : DiaryRemoteDataSource {
    override suspend fun fetchList(
        crop: String,
        year: String,
        month: String
    ): Flow<List<DiaryData>> =
        client.get(GET_DIARY_LIST) {
            parameter("crop", crop)
            parameter("year", year)
            parameter("month", month)
        }
            .body<Dto<DiaryListResponse>>()
            .data.diaryList.map {
                DiaryRemoteMapper.convert(it)
            }
            .let { flowOf(it) }

    override suspend fun fetchDetail(id: Int): DiaryData =
        client.get("$GET_DIARY_DETAIL/$id")
            .body<Dto<DiaryListResponse.DiaryResponse>>()
            .data.let { DiaryRemoteMapper.convert(it) }

    override suspend fun searchList(
        crop: String,
        query: String,
        startDate: String,
        endDate: String
    ): Flow<List<DiaryData>> =
        client.get(SEARCH_DIARY_LIST)
        {
            parameter("crop", crop)
            parameter("query", query)
            parameter("startDate", startDate)
            parameter("endDate", endDate)
        }
            .body<Dto<DiaryListResponse>>()
            .data.diaryList.map {
                DiaryRemoteMapper.convert(it)
            }
            .let { flowOf(it) }

    override suspend fun create(
        crop:String,
        date: String,
        holidayList: List<HolidayData>,
        weatherForecast: String,
        workLaborer: Int,
        workHours: Int,
        workArea: Int,
        workDescriptions: List<DiaryData.WorkDescriptionData>,
        memo: String
    ) {
        client.post(POST_DIARY) {
            setBody(
                PostDiaryRequest(
                    crop = crop,
                    date = date,
                    holidayList = holidayList.map {
                        PostDiaryRequest.HolidayRequest(
                            date = it.date,
                            isHoliday = it.isHoliday,
                            type = it.type,
                            name = it.name
                        )
                    },
                    weatherForecast = weatherForecast,
                    workLaborer = workLaborer,
                    workHours = workHours,
                    workArea = workArea,
                    workDescriptions = workDescriptions.map {
                        PostDiaryRequest.WorkDescriptionRequest(
                            type = it.type,
                            description = it.description
                        )
                    },
                    memo = memo
                )
            )
        }
    }

    override suspend fun update(
        id: Int,
        crop: String,
        date: String,
        holidayList: List<HolidayData>,
        weatherForecast: String,
        workLaborer: Int,
        workHours: Int,
        workArea: Int,
        workDescriptions: List<DiaryData.WorkDescriptionData>,
        memo: String
    ) {
        client.put("$PUT_DIARY/$id") {
            setBody(
                UpdateDiaryRequest(
                    id = id,
                    crop = crop,
                    date = date,
                    holidayList = holidayList.map {
                        PostDiaryRequest.HolidayRequest(
                            date = it.date,
                            isHoliday = it.isHoliday,
                            type = it.type,
                            name = it.name
                        )
                    },
                    weatherForecast = weatherForecast,
                    workLaborer = workLaborer,
                    workHours = workHours,
                    workArea = workArea,
                    workDescriptions = workDescriptions.map {
                        PostDiaryRequest.WorkDescriptionRequest(
                            type = it.type,
                            description = it.description
                        )
                    },
                    memo = memo
                )
            )
        }
    }

    override suspend fun delete(id: Int) {
        client.delete("$DELETE_DIARY/$id")
    }

    companion object {
        private const val GET_DIARY_LIST = "api/calendar/diary"
        private const val SEARCH_DIARY_LIST = "api/calendar/diary/search"
        private const val GET_DIARY_DETAIL = "api/calendar/diary/detail"
        private const val POST_DIARY = "api/calendar/diary/register"
        private const val PUT_DIARY = "api/calendar/diary/update"
        private const val DELETE_DIARY = "api/calendar/diary/delete"
    }
}