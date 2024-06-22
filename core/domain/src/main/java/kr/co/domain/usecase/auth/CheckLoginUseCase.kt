package kr.co.domain.usecase.auth

import kr.co.domain.repository.AuthRepository
import kr.co.domain.usecase.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
): SuspendUseCase<Unit, Unit>(){
    override suspend fun build(params: Unit?) {

        authRepository.check()
    }
}
