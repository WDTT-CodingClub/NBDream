package kr.co.domain.usecase

import kr.co.domain.model.AuthType

interface LoginUseCase {
    suspend operator fun invoke(type: AuthType, token: String)
}