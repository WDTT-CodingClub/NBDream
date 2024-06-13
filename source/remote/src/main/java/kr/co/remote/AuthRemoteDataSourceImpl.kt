package kr.co.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kr.co.data.model.type.AuthTypeData
import kr.co.data.source.remote.AuthRemoteDataSource
import kr.co.remote.mapper.PostAuthMapper
import kr.co.remote.mapper.type.AuthTypeRemoteMapper
import kr.co.remote.model.Dto
import kr.co.remote.model.request.auth.PostAuthTokenRequest
import kr.co.remote.model.response.auth.PostAuthResponse
import kr.co.remote.model.response.auth.PostAuthTokenResponse
import javax.inject.Inject

internal class AuthRemoteDataSourceImpl @Inject constructor(
    private val client: HttpClient
) : AuthRemoteDataSource {

    companion object {
        private const val LOGIN_URL = "/login/oauth"
        private const val REGISTER_URL = "/auth/social/token"
    }

    override suspend fun getAuthToken(refreshToken: String) {
        TODO("Not yet implemented")
    }

    override suspend fun login(type: AuthTypeData, token: String) =
        client.post("$LOGIN_URL/${type.name.lowercase()}") {
            setBody(
                mapOf(
                    "token" to token
                )
            )
        }.body<Dto<PostAuthResponse>>()
            .let {
                PostAuthMapper.convert(it.data)
            }


    override suspend fun register(
        type: AuthTypeData,
        token: String
    ) {
        client.post(REGISTER_URL) {
            setBody(
                PostAuthTokenRequest(
                    type = type.let(AuthTypeRemoteMapper::toLeft),
                    token = token
                )
            )
        }.body<PostAuthTokenResponse>()

    }
}