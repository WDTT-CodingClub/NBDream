package kr.co.data.source.remote

import kr.co.data.model.data.AlarmStatusData

interface FcmRemoteDataSource {
    suspend fun saveFcmToken(token: String)
    suspend fun expireFcmToken()
    suspend fun sendFcmMessage(token: String, title: String, body: String)
    suspend fun getAlarmStatus(): AlarmStatusData
    suspend fun updateAlarmSettings(commentAlarm: Boolean, scheduleAlarm: Boolean)
}