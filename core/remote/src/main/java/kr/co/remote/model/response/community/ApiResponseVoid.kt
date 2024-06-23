package kr.co.remote.model.response.community

import kotlinx.serialization.Serializable

@Serializable
internal data class ApiResponseVoid(
    val code: Int?,
    val status: String?,
    val message: String?,
    val data: String?,
)


internal fun ApiResponseVoid.isCode200() = this.code == 200
