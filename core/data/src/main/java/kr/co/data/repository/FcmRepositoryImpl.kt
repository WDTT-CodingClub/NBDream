package kr.co.data.repository

import kotlinx.coroutines.flow.Flow
import kr.co.data.mapper.AlarmStatusMapper
import kr.co.data.source.local.SessionLocalDataSource
import kr.co.data.source.remote.FcmRemoteDataSource
import kr.co.domain.entity.AlarmStatusEntity
import kr.co.domain.repository.FcmRepository
import javax.inject.Inject

internal class FcmRepositoryImpl @Inject constructor(
    private val remote: FcmRemoteDataSource,
    private val session: SessionLocalDataSource
) : FcmRepository {
    override suspend fun saveFcmToken(token: String) {
        session.saveFcmToken(token)
        remote.saveFcmToken(token)
    }

    override suspend fun invalidateFcmToken() {
        session.saveFcmToken("")
        remote.expireFcmToken()
    }

    override fun fetchFcmToken(): Flow<String?> {
        return session.fetchFcmToken()
    }

    override suspend fun sendFcmMessage(token: String, title: String, body: String) {
        remote.sendFcmMessage(token, title, body)
    }

    override suspend fun getAlarmStatus(): AlarmStatusEntity {
        return remote.getAlarmStatus().let(AlarmStatusMapper::convert)
    }

    override suspend fun updateAlarmSettings(commentAlarm: Boolean, scheduleAlarm: Boolean) {
        remote.updateAlarmSettings(commentAlarm, scheduleAlarm)
    }
}