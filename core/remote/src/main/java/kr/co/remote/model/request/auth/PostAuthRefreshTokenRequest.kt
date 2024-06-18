package kr.co.remote.model.request.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.co.remote.model.type.AuthTypeRemote

@Serializable
internal data class PostAuthRefreshTokenRequest(
    @SerialName(value = "accessToken")
    val accessToken: String,

    @SerialName(value = "refreshToken")
    val refreshToken: String,
)
