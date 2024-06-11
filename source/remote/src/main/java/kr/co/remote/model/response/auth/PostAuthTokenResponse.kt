package kr.co.remote.model.response.auth

import kotlinx.serialization.Serializable

@Serializable //상의후 포스트 어스 리스폰 사용
internal data class PostAuthTokenResponse(
    val accessToken: String,
    val refreshToken: String,
)
