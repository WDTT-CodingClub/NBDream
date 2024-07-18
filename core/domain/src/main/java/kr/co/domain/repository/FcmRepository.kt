package kr.co.domain.repository

import kotlinx.coroutines.flow.Flow

interface FcmRepository {
    suspend fun saveFcmToken(token: String)
    suspend fun invalidateFcmToken()
    fun fetchFcmToken(): Flow<String?>
    suspend fun sendFcmMessage(token: String, title: String, body: String)
}