package kr.co.data.repository

import kotlinx.coroutines.flow.Flow
import kr.co.domain.repository.SessionRepository
import kr.co.data.source.local.SessionLocalDataSource
import javax.inject.Inject

internal class SessionRepositoryImpl @Inject constructor(
    private val local: SessionLocalDataSource
): SessionRepository {
    override fun fetch(): Flow<String?> {
        return local.fetchUserId()
    }

    override suspend fun save(email: String) {
        local.saveUserId(email)
    }

    override suspend fun clearAll() {
        local.removeAll()
    }
}