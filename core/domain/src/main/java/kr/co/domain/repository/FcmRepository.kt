package kr.co.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.domain.entity.AlarmStatusEntity

interface FcmRepository {
    suspend fun saveFcmToken(token: String)
    suspend fun invalidateFcmToken()
    fun fetchFcmToken(): Flow<String?>
    suspend fun sendFcmMessage(token: String, title: String, body: String)
    suspend fun getAlarmStatus(): AlarmStatusEntity
    suspend fun updateAlarmSettings(commentAlarm: Boolean, scheduleAlarm: Boolean)
}