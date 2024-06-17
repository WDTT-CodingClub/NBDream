package kr.co.data.source.remote

import kr.co.data.model.data.auth.Auth
import kr.co.data.model.type.AuthTypeData

interface AuthRemoteDataSource {
    suspend fun getAuthToken(
        refreshToken: String
    )

    suspend fun login(
        type: AuthTypeData,
        token: String
    ): Auth

    suspend fun register(
        type: AuthTypeData,
        token: String,
    )
}
