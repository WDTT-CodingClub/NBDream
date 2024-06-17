package kr.co.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
internal data class PostServerImageResponse(
    val code: Int?,
    val status: String?,
    val message: String?,
    val data: String?,
)
