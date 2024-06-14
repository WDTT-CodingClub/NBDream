package kr.co.remote.model.request.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.co.remote.model.type.AuthTypeRemote

@Serializable
internal data class PostAuthTokenRequest(
    @SerialName("type")
    val type: AuthTypeRemote,

    @SerialName("token")
    val token: String
)
