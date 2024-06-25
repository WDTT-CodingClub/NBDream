package kr.co.domain.entity

data class UserEntity(
    val name: String,
    val address: String? = null,
    val bjdCode: String? = null,
    val profileImage: String? = null,
    val longitude: Double? = null,
    val latitude: Double? = null,
    val crops: List<String> = emptyList()
)