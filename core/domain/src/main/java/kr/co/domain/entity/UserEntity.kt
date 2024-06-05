package kr.co.domain.entity

import kr.co.domain.entity.plzLookThisPakage.DreamCrop

data class UserEntity(
    val name: String = "말하는 감자",
    val address: String? = null,
    val crops: List<DreamCrop> = emptyList(),
    val profileImage: String? = null,
    val alarmSetting: Boolean = true,
    val accessToken: String? = null,
    val refreshToken: String? = null
)