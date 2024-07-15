package kr.co.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kr.co.data.model.data.AlarmStatusData
import kr.co.data.source.remote.FcmRemoteDataSource
import kr.co.remote.mapper.AlarmStatusRemoteMapper
import kr.co.remote.model.request.fcm.AlarmUpdateRequest
import kr.co.remote.model.request.fcm.FcmSendRequest
import kr.co.remote.model.request.fcm.FcmTokenRequest
import kr.co.remote.model.response.fcm.GetAlarmStatusResponse
import javax.inject.Inject

internal class FcmRemoteDataSourceImpl @Inject constructor(
    private val client: HttpClient
) : FcmRemoteDataSource {
    companion object {
        private const val POST_FCM_TOKEN = "api/fcm/token"
        private const val POST_FCM_SEND = "api/fcm/send"
        private const val GET_ALARM_STATUS = "api/alarm/status"
        private const val POST_ALARM_UPDATE = "api/alarm/update"
    }

    override suspend fun updateFcmToken(token: String) {
        client.post(POST_FCM_TOKEN) {
            setBody(
                FcmTokenRequest(
                    token = token
                )
            )
        }
    }

    override suspend fun sendFcmMessage(token: String, title: String, body: String) {
        client.post(POST_FCM_SEND) {
            setBody(
                FcmSendRequest(
                    token = token,
                    title = title,
                    body = body
                )
            )
        }
    }

    override suspend fun getAlarmStatus(): AlarmStatusData {
        return client.get(GET_ALARM_STATUS).body<GetAlarmStatusResponse>()
            .let(AlarmStatusRemoteMapper::convert)
    }


    override suspend fun updateAlarmSettings(commentAlarm: Boolean, scheduleAlarm: Boolean) {
        client.post(POST_ALARM_UPDATE) {
            setBody(
                AlarmUpdateRequest(
                    commentAlarm = commentAlarm,
                    scheduleAlarm = scheduleAlarm
                )
            )
        }
    }
}