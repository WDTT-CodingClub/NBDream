package kr.co.remote.model.response.community

import kotlinx.serialization.Serializable

@Serializable
internal data class DeleteBulletinResponse(
    val code: Int?,
    val status: String?,
    val message: String?,
    val data: String?,
)


internal fun DeleteBulletinResponse.convertToData() = this.code == 200
