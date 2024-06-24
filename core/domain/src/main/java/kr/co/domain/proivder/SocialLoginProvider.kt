package kr.co.domain.proivder

import kr.co.domain.entity.type.AuthType
import kr.co.domain.entity.LoginEntity

interface SocialLoginProvider {
    suspend fun login(type: AuthType): LoginEntity

    suspend fun logout(type: AuthType)
}