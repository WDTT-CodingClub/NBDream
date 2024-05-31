package kr.co.wdtt.core.data

import kotlinx.coroutines.flow.Flow
import kr.co.wdtt.core.data.source.local.SessionLocalDataSource
import kr.co.wdtt.core.domain.SessionRepository
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val local: SessionLocalDataSource
): SessionRepository {
    override fun fetch(): Flow<String?> {
        return local.fetchUserId()
    }

    override suspend fun save(userId: String) {
        local.saveUserId(userId)
    }

    override suspend fun clearAll() {
        local.removeAll()
    }
}