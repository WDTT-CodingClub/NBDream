package kr.co.domain.repository

import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    fun fetch(): Flow<String?>

    suspend fun save(email: String)

    suspend fun clearAll()
}
