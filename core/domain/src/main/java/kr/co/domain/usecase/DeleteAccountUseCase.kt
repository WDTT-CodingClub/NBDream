package kr.co.domain.usecase

import kr.co.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteAccountUseCase @Inject constructor(
    private val userRepository: UserRepository,
): SuspendUseCase<Unit, Unit>(){
    override suspend fun build(params: Unit?) {

        return userRepository.delete()
    }
}
