package kr.co.domain.usecase.fcm

import kotlinx.coroutines.flow.Flow
import kr.co.domain.repository.FcmRepository
import kr.co.domain.usecase.FlowUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FetchFcmTokenUseCase @Inject constructor(
    private val fcmRepository: FcmRepository
) : FlowUseCase<Unit, String?>() {

    override fun build(params: Unit?): Flow<String?> {
        return fcmRepository.fetchFcmToken()
    }
}