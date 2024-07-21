package kr.co.data.repository

import kr.co.data.mapper.AlarmHistoryListMapper
import kr.co.data.mapper.AlarmStatusMapper
import kr.co.data.source.remote.AlarmStatusDataSource
import kr.co.domain.entity.AlarmHistoryEntity
import kr.co.domain.entity.AlarmStatusEntity
import kr.co.domain.repository.AlarmRepository
import javax.inject.Inject

internal class AlarmRepositoryImpl @Inject constructor(
    private val remote: AlarmStatusDataSource
) : AlarmRepository {
    override suspend fun getAlarmStatus(): AlarmStatusEntity =
        remote.getAlarmStatus().let(AlarmStatusMapper::convert)

    override suspend fun updateAlarmSettings(
        commentAlarm: Boolean,
        scheduleAlarm: Boolean
    ): AlarmStatusEntity = remote.updateAlarmSettings(commentAlarm, scheduleAlarm)
        .let(AlarmStatusMapper::convert)

    override suspend fun getAlarmHistory(): List<AlarmHistoryEntity> =
        remote.getAlarmHistory().let(AlarmHistoryListMapper::convert)

    override suspend fun checkAlarmHistory(id: Long) = remote.checkAlarmHistory(id)

    override suspend fun deleteAlarmHistory(id: Long) = remote.deleteAlarmHistory(id)
}
