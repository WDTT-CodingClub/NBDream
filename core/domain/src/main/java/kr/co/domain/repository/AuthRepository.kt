package kr.co.domain.repository

import kr.co.domain.model.AuthType

interface AuthRepository {

    suspend fun login(type: AuthType, token: String)
}
