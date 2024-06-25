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
import kr.co.data.model.data.calendar.ScheduleData
import kr.co.data.source.remote.ScheduleRemoteDataSource
import kr.co.remote.mapper.calendar.ScheduleRemoteMapper
import kr.co.remote.model.Dto
import kr.co.remote.model.request.calendar.PostScheduleRequest
import kr.co.remote.model.request.calendar.UpdateScheduleRequest
import kr.co.remote.model.response.calendar.ScheduleListResponse
import javax.inject.Inject

internal class ScheduleRemoteDataSourceImpl @Inject constructor(
    private val client: HttpClient
) : ScheduleRemoteDataSource {

    override suspend fun fetchList(crop: String, startDate: String): Flow<List<ScheduleData>> =
        client.get(GET_SCHEDULE_LIST_WEEK) {
            parameter("crop", crop)
            parameter("startDate", startDate)
        }
            .body<Dto<ScheduleListResponse>>()
            .data.scheduleList.map {
                ScheduleRemoteMapper.convert(it)
            }
            .let { flowOf(it) }


    override suspend fun fetchList(category: String, year: Int, month: Int): Flow<List<ScheduleData>> =
        client.get(GET_SCHEDULE_LIST_MONTH) {
            parameter("category", category)
            parameter("year", year)
            parameter("month", month)
        }
            .body<Dto<ScheduleListResponse>>()
            .data.scheduleList.map {
                ScheduleRemoteMapper.convert(it)
            }
            .let { flowOf(it) }

    override suspend fun fetchDetail(id: Long): ScheduleData =
        client.get("$GET_SCHEDULE_DETAIL/$id")
            .body<Dto<ScheduleListResponse.ScheduleResponse>>()
            .data
            .let { ScheduleRemoteMapper.convert(it) }

    override suspend fun create(
        category: String,
        title: String,
        startDate: String,
        endDate: String,
        memo: String
    ) {
        client.post(POST_SCHEDULE) {
            setBody(
                PostScheduleRequest(
                    category = category,
                    title = title,
                    startDate = startDate,
                    endDate = endDate,
                    memo = memo
                )
            )
        }
    }

    override suspend fun update(
        id: Long,
        category: String,
        title: String,
        startDate: String,
        endDate: String,
        memo: String
    ) {
        client.put("$PUT_SCHEDULE/$id") {
            setBody(
                UpdateScheduleRequest(
                    id = id,
                    category = category,
                    title = title,
                    startDate = startDate,
                    endDate = endDate,
                    memo = memo
                )
            )
        }
    }

    override suspend fun delete(id: Long) {
        client.delete("$DELETE_SCHEDULE/$id")
    }

    companion object {
        private const val GET_SCHEDULE_LIST_WEEK = "api/calendar/schedule/week"
        private const val GET_SCHEDULE_LIST_MONTH = "api/calendar/schedule/month"
        private const val GET_SCHEDULE_DETAIL = "api/calendar/schedule/detail"
        private const val POST_SCHEDULE = "api/calendar/schedule/register"
        private const val PUT_SCHEDULE = "api/calendar/schedule/update"
        private const val DELETE_SCHEDULE = "api/calendar/schedule/delete"
    }
}