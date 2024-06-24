package kr.co.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kr.co.data.model.data.calendar.FarmWorkData
import kr.co.data.source.remote.FarmWorkRemoteDataSource
import kr.co.remote.mapper.calendar.FarmWorkRemoteMapper
import kr.co.remote.model.Dto
import kr.co.remote.model.response.calendar.FarmWorkListResponse
import javax.inject.Inject

internal class FarmWorkRemoteDataSourceImpl @Inject constructor(
    private val client: HttpClient
) : FarmWorkRemoteDataSource {

    override suspend fun fetchList(crop: String, month: Int): List<FarmWorkData> =
        client.get(FARM_WORK) {
            parameter("crop", crop)
            parameter("month", month)
        }
            .body<Dto<FarmWorkListResponse>>()
            .let { farmWorkListResponseDto ->
                farmWorkListResponseDto.data.farmWorkList.map {
                    FarmWorkRemoteMapper.convert(it)
                }
            }

    companion object {
        private const val FARM_WORK = "/api/calendar/farm-work"
    }
}