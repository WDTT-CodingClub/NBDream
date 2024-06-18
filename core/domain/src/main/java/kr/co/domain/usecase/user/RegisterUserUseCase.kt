package kr.co.domain.usecase.user

import kr.co.domain.entity.UserEntity
import kr.co.domain.repository.UserRepository
import kr.co.domain.usecase.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegisterUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
) : SuspendUseCase<UserEntity, Unit>() {
    override suspend fun build(params: UserEntity?) {
        checkNotNull(params)

        return userRepository.update(params)
    }
}