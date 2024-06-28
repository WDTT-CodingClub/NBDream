package kr.co.domain.usecase.user

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kr.co.domain.entity.UserEntity
import kr.co.domain.repository.UserRepository
import kr.co.domain.usecase.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegisterInfoUseCase @Inject constructor(
    private val userRepository: UserRepository,
) : SuspendUseCase<UserEntity, Unit>() {
    override suspend fun build(params: UserEntity?) {
        checkNotNull(params)

        UserEntity(
            name = params.name,
            profileImage = params.profileImage,
            address = params.address,
            bjdCode = params.bjdCode,
            longitude = params.longitude,
            latitude = params.latitude,
            crops = params.crops
        ).also { user ->
            userRepository.update(user)
        }
    }
}