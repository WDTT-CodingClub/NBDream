package kr.co.remote.model.response.user

import kotlinx.serialization.Serializable

@Serializable
internal data class GetUserResponse(
    val nickname: String,
    val address: String?,
    val profileImageUrl: String?,
    val bjdCode: String?,
    val longitude: Double?,
    val latitude: Double?,
    val crops: List<String>
)
