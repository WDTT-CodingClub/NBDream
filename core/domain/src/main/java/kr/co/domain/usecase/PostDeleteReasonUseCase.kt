package kr.co.domain.usecase

import kr.co.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostDeleteReasonUseCase @Inject constructor(
    private val userRepository: UserRepository,
): SuspendUseCase<PostDeleteReasonUseCase.Params, Unit>() {
    override suspend fun build(params: Params?) {
        checkNotNull(params)

        userRepository.postReason(params.reason, params.otherReason?: "")
    }

    data class Params(
        val reason: String,
        val otherReason: String?,
    )
}
