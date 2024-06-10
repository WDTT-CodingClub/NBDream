package kr.co.domain.model

data class LoginResult(
    val type: AuthType,
    val token: String,
)
