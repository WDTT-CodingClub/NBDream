package kr.co.remote.model.request.user

import kotlinx.serialization.Serializable

@Serializable
internal data class PutUserRequest(
    val nickname: String?,
    val address: String?,
    val bjdCode: String?,
    val profileImageUrl: String?,
    val longitude: Double?,
    val latitude: Double?,
    val crops: List<String>
)
