package kr.co.domain.proivder

import kr.co.domain.model.AuthType
import kr.co.domain.model.LoginResult

interface SocialLoginProvider {
    suspend fun login(type: AuthType): LoginResult

    suspend fun logout(type: AuthType)
}