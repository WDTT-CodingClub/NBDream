package kr.co.data.source.local

import kotlinx.coroutines.flow.Flow

interface SessionLocalDataSource {
    suspend fun saveUserId(userId: String)

    fun fetchUserId(): Flow<String?>

    suspend fun updateAccessToken(
        accessToken: String,
    )

    suspend fun updateRefreshToken(
        refreshToken: String,
    )

    suspend fun getAccessToken(): String?

    suspend fun getRefreshToken(): String?

    suspend fun removeAll()
}