package kr.co.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.domain.entity.UserEntity

interface UserRepository {
    suspend fun fetch(): UserEntity

    fun fetchLocal(): Flow<UserEntity>

    suspend fun save(user: UserEntity)

    suspend fun update(user: UserEntity)

    suspend fun delete()

    suspend fun clearAll()
    suspend fun postReason(reason: String, otherReason: String)
}
