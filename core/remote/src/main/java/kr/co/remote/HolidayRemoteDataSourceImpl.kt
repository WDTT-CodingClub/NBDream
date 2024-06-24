package kr.co.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kr.co.data.model.data.HolidayData
import kr.co.data.source.remote.HolidayRemoteDataSource
import kr.co.remote.mapper.HolidayRemoteMapper
import kr.co.remote.model.Dto
import kr.co.remote.model.response.calendar.HolidayListResponse
import javax.inject.Inject

internal class HolidayRemoteDataSourceImpl @Inject constructor(
    private val client: HttpClient
) : HolidayRemoteDataSource {
    override suspend fun fetchList(year: String, month: String): Flow<List<HolidayData>> =
        flowOf(
            client.get(HOLIDAY) {
                parameter("year", year)
                parameter("month", month)
            }
                .body<Dto<HolidayListResponse>>()
                .let { holidayListResponseDto ->
                    holidayListResponseDto.data.holidayList.map {
                        HolidayRemoteMapper.convert(it)
                    }
                }
        )

    companion object {
        private const val HOLIDAY = "/api/calendar/holidays"
    }
}