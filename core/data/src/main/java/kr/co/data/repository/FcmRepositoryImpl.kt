package kr.co.data.repository

import kr.co.data.mapper.AlarmStatusMapper
import kr.co.data.source.remote.FcmRemoteDataSource
import kr.co.domain.entity.AlarmStatusEntity
import kr.co.domain.repository.FcmRepository
import javax.inject.Inject

internal class FcmRepositoryImpl @Inject constructor(
    private val remote: FcmRemoteDataSource
) : FcmRepository {
    override suspend fun updateFcmToken(token: String) =
        remote.updateFcmToken(token)

    override suspend fun sendFcmMessage(token: String, title: String, body: String) =
        remote.sendFcmMessage(token, title, body)

    override suspend fun getAlarmStatus(): AlarmStatusEntity =
        remote.getAlarmStatus().let(AlarmStatusMapper::convert)

    override suspend fun updateAlarmSettings(commentAlarm: Boolean, scheduleAlarm: Boolean) =
        remote.updateAlarmSettings(commentAlarm, scheduleAlarm)
}