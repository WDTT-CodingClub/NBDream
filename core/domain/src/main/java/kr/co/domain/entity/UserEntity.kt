package kr.co.domain.entity

data class UserEntity(
    val name: String = "말하는 감자",
    val address: String? = null,
    val crops: List<kr.co.domain.entity.DreamCrop> = emptyList(),
    val profileImage: String? = null,
    val alarmSetting: Boolean = true,
    val accessToken: String? = null,
    val refreshToken: String? = null
)