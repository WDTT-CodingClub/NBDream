package kr.co.wdtt.nbdream.domain.entity

data class UserEntity(
    val name: String = "말하는 감자",
    val address: String? = null,
    val crops: List<DreamCrop> = emptyList(),
    val profileImage: String = "",
    val alarmSetting: Boolean = true
)