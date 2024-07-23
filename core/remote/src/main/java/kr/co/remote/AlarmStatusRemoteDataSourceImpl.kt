package kr.co.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kr.co.data.model.data.AlarmHistoryListData
import kr.co.data.model.data.AlarmStatusData
import kr.co.data.source.remote.AlarmStatusDataSource
import kr.co.remote.mapper.AlarmHistoryListRemoteMapper
import kr.co.remote.mapper.AlarmStatusRemoteMapper
import kr.co.remote.model.request.alarm.AlarmUpdateRequest
import kr.co.remote.model.response.alarm.GetAlarmHistoryListResponse
import kr.co.remote.model.response.alarm.GetAlarmStatusResponse
import javax.inject.Inject

@kotlinx.serialization.Serializable
data class IdListRequest(
    val idList: List<Long>
)

internal class AlarmStatusRemoteDataSourceImpl @Inject constructor(
    private val client: HttpClient
) : AlarmStatusDataSource {
    companion object {
        private const val GET_ALARM_STATUS = "api/alarm/status"
        private const val PUT_ALARM_UPDATE = "api/alarm/update"
        private const val GET_ALARM_HISTORY = "api/alarm/history"
        private const val PUT_ALARM_CHECK = "api/alarm/history/check"
        private const val DELETE_ALARM_HISTORY = "api/alarm/history/delete"
    }

    override suspend fun getAlarmStatus(): AlarmStatusData {
        return client.get(GET_ALARM_STATUS).body<GetAlarmStatusResponse>()
            .let(AlarmStatusRemoteMapper::convert)
    }

    override suspend fun updateAlarmSettings(
        commentAlarm: Boolean,
        scheduleAlarm: Boolean
    ): AlarmStatusData {
        return client.put(PUT_ALARM_UPDATE) {
            setBody(AlarmUpdateRequest(commentAlarm = commentAlarm, scheduleAlarm = scheduleAlarm))
        }.body<GetAlarmStatusResponse>().let(AlarmStatusRemoteMapper::convert)
    }

    override suspend fun getAlarmHistory(): AlarmHistoryListData {
        return client.get(GET_ALARM_HISTORY).body<GetAlarmHistoryListResponse>().let(
            AlarmHistoryListRemoteMapper::convert
        )
    }

    override suspend fun checkAlarmHistory(ids: List<Long>) {
        client.put(PUT_ALARM_CHECK) {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(IdListRequest(idList = ids)))
        }
    }

    override suspend fun deleteAlarmHistory(ids: List<Long>) {
        client.delete(DELETE_ALARM_HISTORY) {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(IdListRequest(idList = ids)))
        }
    }
}
