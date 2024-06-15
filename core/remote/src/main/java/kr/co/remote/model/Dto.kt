package kr.co.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Dto<RESPONSE>(
    @SerialName("code")
    val code: Int,
    @SerialName("status")
    val status: String,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: RESPONSE
)
