package kr.co.remote.model.response.community

import kotlinx.serialization.Serializable

@Serializable
internal data class DeleteImageResponse(
    val code: Int?,
    val status: String?,
    val message: String?,
    val data: String?,
)


internal fun DeleteImageResponse.convertToData() = this.code == 200
