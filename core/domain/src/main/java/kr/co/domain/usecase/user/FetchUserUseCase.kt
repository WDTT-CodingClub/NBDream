package kr.co.domain.usecase.user

import kotlinx.coroutines.flow.Flow
import kr.co.domain.entity.UserEntity
import kr.co.domain.repository.UserRepository
import kr.co.domain.usecase.FlowUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FetchUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) : FlowUseCase<Unit, UserEntity>(){
    override fun build(prams: Unit?): Flow<UserEntity> {
        return userRepository.fetchLocal()
    }
}