package kr.co.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import kr.co.data.model.data.AlarmStatusData
import kr.co.data.source.remote.AlarmStatusDataSource
import kr.co.remote.mapper.AlarmStatusRemoteMapper
import kr.co.remote.model.request.fcm.AlarmUpdateRequest
import kr.co.remote.model.response.fcm.GetAlarmStatusResponse
import javax.inject.Inject

internal class AlarmStatusRemoteDataSourceImpl @Inject constructor(
    private val client: HttpClient
) : AlarmStatusDataSource {
    companion object {
        private const val GET_ALARM_STATUS = "api/alarm/status"
        private const val PUT_ALARM_UPDATE = "api/alarm/update"
    }

    override suspend fun getAlarmStatus(): AlarmStatusData {
        return client.get(GET_ALARM_STATUS).body<GetAlarmStatusResponse>()
            .let(AlarmStatusRemoteMapper::convert)
    }

    override suspend fun updateAlarmSettings(commentAlarm: Boolean, scheduleAlarm: Boolean): AlarmStatusData {
        return client.put(PUT_ALARM_UPDATE) {
            setBody(AlarmUpdateRequest(commentAlarm = commentAlarm, scheduleAlarm = scheduleAlarm))
        }.body<GetAlarmStatusResponse>().let(AlarmStatusRemoteMapper::convert)
    }
}
