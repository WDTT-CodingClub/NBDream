package kr.co.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kr.co.data.model.type.AuthTypeData
import kr.co.data.source.remote.AuthRemoteDataSource
import kr.co.remote.mapper.PostAuthMapper
import kr.co.remote.mapper.type.AuthTypeRemoteMapper
import kr.co.remote.model.request.auth.PostAuthRequest
import kr.co.remote.model.response.auth.PostAuthResponse
import javax.inject.Inject

internal class AuthRemoteDataSourceImpl @Inject constructor(
    private val client: HttpClient
) : AuthRemoteDataSource {

    companion object {
        private const val LOGIN_URL = "/api/auth/social"
        private const val REGISTER_URL = "/auth/social/token"
    }
    override suspend fun getAuthToken(refreshToken: String) {
        TODO("Not yet implemented")
    }

    override suspend fun login(type: AuthTypeData, token: String, remember: Boolean) =
        client.post(LOGIN_URL) {
            setBody(
                PostAuthRequest(
                    type = type.let(AuthTypeRemoteMapper::toLeft),
                    token = token,
                    remember = remember
                )
            )
        }.body<PostAuthResponse>()
            .let(PostAuthMapper::convert)


    override suspend fun register(
        type: AuthTypeData,
        token: String
    ) {
        client.post(REGISTER_URL) {

        }
    }
}