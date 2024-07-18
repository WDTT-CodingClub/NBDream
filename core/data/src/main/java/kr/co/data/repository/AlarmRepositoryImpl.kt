package kr.co.data.repository

import kr.co.data.mapper.AlarmStatusMapper
import kr.co.data.source.remote.AlarmStatusDataSource
import kr.co.domain.entity.AlarmStatusEntity
import kr.co.domain.repository.AlarmRepository
import javax.inject.Inject

internal class AlarmRepositoryImpl @Inject constructor(
    private val remote: AlarmStatusDataSource
) : AlarmRepository {
    override suspend fun getAlarmStatus(): AlarmStatusEntity {
        return remote.getAlarmStatus().let(AlarmStatusMapper::convert)
    }

    override suspend fun updateAlarmSettings(commentAlarm: Boolean, scheduleAlarm: Boolean): AlarmStatusEntity {
        return remote.updateAlarmSettings(commentAlarm, scheduleAlarm).let(AlarmStatusMapper::convert)
    }
}