package kr.co.data.source.remote

import kr.co.data.model.data.AlarmStatusData

interface AlarmStatusDataSource {
    suspend fun getAlarmStatus(): AlarmStatusData
    suspend fun updateAlarmSettings(commentAlarm: Boolean, scheduleAlarm: Boolean): AlarmStatusData
}