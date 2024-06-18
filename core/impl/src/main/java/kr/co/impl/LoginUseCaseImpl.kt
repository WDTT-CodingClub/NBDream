package kr.co.impl

import kr.co.domain.model.AuthType
import kr.co.domain.repository.AuthRepository
import kr.co.domain.usecase.LoginUseCase
import javax.inject.Inject

internal class LoginUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : LoginUseCase {
    override suspend fun invoke(type: AuthType, token: String) {
        authRepository.login(type, token)
    }

}