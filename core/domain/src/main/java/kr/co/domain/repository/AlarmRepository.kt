package kr.co.domain.repository

import kr.co.domain.entity.AlarmHistoryEntity
import kr.co.domain.entity.AlarmStatusEntity

interface AlarmRepository {
    suspend fun getAlarmStatus(): AlarmStatusEntity
    suspend fun updateAlarmSettings(commentAlarm: Boolean, scheduleAlarm: Boolean): AlarmStatusEntity
    suspend fun getAlarmHistory(): List<AlarmHistoryEntity>
    suspend fun checkAlarmHistory(ids: List<Long>)
    suspend fun deleteAlarmHistory(ids: List<Long>)
}
