package kr.co.domain.usecase.auth

import kr.co.domain.entity.type.AuthType
import kr.co.domain.repository.AuthRepository
import kr.co.domain.repository.SessionRepository
import kr.co.domain.repository.UserRepository
import kr.co.domain.usecase.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) : SuspendUseCase<LoginUseCase.Params, Int>() {
    override suspend fun build(params: Params?): Int {
        checkNotNull(params)

        return authRepository.login(params.type, params.token)
    }

    data class Params(
        val type: AuthType,
        val token: String
    )
}