package kr.co.domain.repository

interface FcmRepository {
    suspend fun saveFcmToken(token: String)
    suspend fun invalidateFcmToken()
    suspend fun sendFcmMessage(token: String, title: String, body: String)
}