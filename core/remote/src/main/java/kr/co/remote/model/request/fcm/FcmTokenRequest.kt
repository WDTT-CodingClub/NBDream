package kr.co.remote.model.request.fcm

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class FcmTokenRequest(
    @SerialName("token")
    val token: String
)