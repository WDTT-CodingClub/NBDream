package kr.co.domain.usecase.user

import kotlinx.coroutines.flow.first
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

        return userRepository.fetchLocal().first().let {
            it.copy(
                name = params.name?: it.name,
                address = params.address?: it.address,
                profileImage = params.profileImage?: it.profileImage,
                longitude = params.longitude?: it.longitude,
                latitude = params.latitude?: it.latitude,
                crops = params.crops.ifEmpty { it.crops }
            ).let { user ->
                userRepository.update(user)
            }
        }
    }
}