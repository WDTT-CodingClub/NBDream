package kr.co.domain.repository

import kr.co.domain.entity.AlarmStatusEntity

interface AlarmRepository {
    suspend fun getAlarmStatus(): AlarmStatusEntity
    suspend fun updateAlarmSettings(commentAlarm: Boolean, scheduleAlarm: Boolean): AlarmStatusEntity
}