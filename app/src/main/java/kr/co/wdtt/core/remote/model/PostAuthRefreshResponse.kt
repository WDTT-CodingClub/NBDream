package kr.co.wdtt.core.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostAuthRefreshResponse(
    @SerialName("resultCode")
    val resultCode: Int,
    @SerialName("resultMessage")
    val resultMessage: String,
    @SerialName("resultData")
    val resultData: ResultData
) {
    @Serializable
    data class ResultData(
        @SerialName("accessToken")
        val accessToken: String,
        @SerialName("refreshToken")
        val refreshToken: String
    )
}
