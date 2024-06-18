package kr.co.domain.entity

data class UserEntity(
    val name: String,
    val address: String?,
    val profileImage: String?,
    val longitude: Double?,
    val latitude: Double?,
    val crops: List<String>
)