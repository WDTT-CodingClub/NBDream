package kr.co.domain.entity

import kr.co.domain.entity.type.AuthType

data class LoginEntity(
    val type: AuthType,
    val token: String,
)
