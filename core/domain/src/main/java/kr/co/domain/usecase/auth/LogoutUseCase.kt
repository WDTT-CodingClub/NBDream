package kr.co.domain.usecase.auth

import kr.co.domain.repository.UserRepository
import kr.co.domain.usecase.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogoutUseCase @Inject constructor(
    private val userRepository: UserRepository
) : SuspendUseCase<Unit, Unit>(){
    override suspend fun build(params: Unit?) {

        return userRepository.clearAll()
    }
}