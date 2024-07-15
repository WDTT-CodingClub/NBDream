package kr.co.domain.repository

import kr.co.domain.entity.AlarmStatusEntity

interface FcmRepository {
    suspend fun updateFcmToken(
        token: String
    )
    suspend fun sendFcmMessage(
        token: String,
        title: String,
        body: String
    )
    suspend fun getAlarmStatus(): AlarmStatusEntity
    suspend fun updateAlarmSettings(
        commentAlarm: Boolean,
        scheduleAlarm: Boolean
    )
}