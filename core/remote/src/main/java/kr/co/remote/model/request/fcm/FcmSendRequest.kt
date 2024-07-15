package kr.co.remote.model.request.fcm

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class FcmSendRequest(
    @SerialName("token")
    val token: String,
    @SerialName("title")
    val title: String,
    @SerialName("body")
    val body: String
)