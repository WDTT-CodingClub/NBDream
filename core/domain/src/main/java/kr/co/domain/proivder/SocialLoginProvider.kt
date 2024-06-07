package kr.co.domain.proivder

import kr.co.domain.model.AuthType

interface SocialLoginProvider {
    suspend fun login(type: AuthType)

    suspend fun logout(type: AuthType)
}