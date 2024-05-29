package kr.co.wdtt.nbdream.domain.entity

data class UserEntity(
    val name: String = "말하는 감자",
    val address: String? = null,
    val crops: List<DreamCrop> = emptyList(),
    val profileImage: String? = null,
    val alarmSetting: Boolean = true,
    val accessToken: String? = null,
    val refreshToken: String? = null
)