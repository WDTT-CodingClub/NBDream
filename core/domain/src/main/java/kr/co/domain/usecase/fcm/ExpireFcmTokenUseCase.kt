package kr.co.domain.usecase.fcm

import kr.co.domain.repository.FcmRepository
import kr.co.domain.usecase.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpireFcmTokenUseCase @Inject constructor(
    private val fcmRepository: FcmRepository,
) : SuspendUseCase<Unit, Unit>() {
    override suspend fun build(params: Unit?) {
        fcmRepository.invalidateFcmToken()
    }
}