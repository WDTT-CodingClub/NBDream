package kr.co.domain.usecase

import kr.co.domain.model.AuthType
import kr.co.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(type: AuthType, token: String) {
        authRepository.login(type, token)
    }

}