package kr.co.domain.repository

import kr.co.domain.entity.type.AuthType

interface AuthRepository {

    suspend fun login(type: AuthType, token: String) : Int

    suspend fun check()
}
