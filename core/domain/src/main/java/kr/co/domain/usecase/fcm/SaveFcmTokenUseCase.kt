package kr.co.domain.usecase.fcm

import kr.co.domain.repository.FcmRepository
import kr.co.domain.usecase.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveFcmTokenUseCase @Inject constructor(
    private val fcmRepository: FcmRepository
) : SuspendUseCase<String, Unit>() {
    override suspend fun build(params: String?) {
        params?.let { token ->
            fcmRepository.saveFcmToken(token)
        }
    }
}