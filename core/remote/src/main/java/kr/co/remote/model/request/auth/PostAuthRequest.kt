package kr.co.remote.model.request.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.co.remote.model.type.AuthTypeRemote

@Serializable
internal data class PostAuthRequest(
    @SerialName(value = "type")
    val type: AuthTypeRemote,

    @SerialName(value = "token")
    val token: String,

    @SerialName(value = "remember")
    val remember: Boolean,
)
